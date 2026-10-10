package rt4;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Persistent, viewport-scoped cache for Modern UI SVG rasterizations.
 *
 * SVG work never runs on the game/render thread. Requests are queued onto one
 * daemon worker, which either loads a validated PNG from disk or rasterizes
 * the SVG and stores it for later launches.
 */
public final class ModernVectorCacheManager {
    private static final int CACHE_SCHEMA_VERSION = 1;
    private static final int MAX_VIEWPORT_CACHES = 3;
    private static final long VIEWPORT_SETTLE_MS = 900L;
    private static final long FAILURE_RETRY_MS = 2500L;

    private static final Object STATE_LOCK = new Object();

    private static final ScheduledExecutorService WORKER =
        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "ModernVectorCache");
                thread.setDaemon(true);
                thread.setPriority(Thread.NORM_PRIORITY - 1);
                return thread;
            }
        });

    private static final ConcurrentMap<String, BufferedImage> readyImages =
        new ConcurrentHashMap<>();
    private static final Set<String> inFlight =
        Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private static final ConcurrentMap<String, Long> failedUntil =
        new ConcurrentHashMap<>();
    private static final Set<String> declinedViewports =
        Collections.synchronizedSet(new HashSet<String>());

    private static volatile boolean started;
    private static volatile boolean firstViewportSeen;
    private static volatile String currentViewport = "";
    private static volatile int currentViewportWidth = -1;
    private static volatile int currentViewportHeight = -1;
    private static volatile String persistentViewport = "";
    private static volatile long viewportSerial;
    private static volatile boolean promptVisible;

    private ModernVectorCacheManager() {
    }

    public static void startup() {
        if (started) {
            return;
        }
        synchronized (STATE_LOCK) {
            if (started) {
                return;
            }
            started = true;
        }

        WORKER.execute(new Runnable() {
            @Override
            public void run() {
                File root = getCacheRoot();
                if (!root.exists() && !root.mkdirs()) {
                    DisplayDebug.log(
                        "VECTOR_CACHE startup unable to create root="
                            + root.getAbsolutePath()
                    );
                    return;
                }

                removeInvalidViewportCaches();
                pruneViewportCaches();

                DisplayDebug.log(
                    "VECTOR_CACHE startup thread="
                        + Thread.currentThread().getName()
                        + " root=" + root.getAbsolutePath()
                        + " schema=" + CACHE_SCHEMA_VERSION
                        + " maxViewports=" + MAX_VIEWPORT_CACHES
                );
            }
        });
    }

    /**
     * Called when the actual RT4 3D viewport changes, not merely the AWT frame.
     */
    public static void onViewportChanged(int width, int height) {
        if (width < 1 || height < 1) {
            return;
        }
        startup();

        final String key = viewportKey(width, height);
        final String previous = currentViewport;
        if (key.equals(previous)) {
            return;
        }

        currentViewport = key;
        currentViewportWidth = width;
        currentViewportHeight = height;
        persistentViewport = "";
        readyImages.clear();
        failedUntil.clear();

        final long serial = ++viewportSerial;
        final boolean initial = !firstViewportSeen;
        firstViewportSeen = true;

        DisplayDebug.log(
            "VECTOR_CACHE viewport "
                + (initial ? "initial" : "changed")
                + " old=" + (previous.isEmpty() ? "<none>" : previous)
                + " new=" + key
                + " settleMs=" + (initial ? 0 : VIEWPORT_SETTLE_MS)
        );

        if (initial) {
            scheduleViewportEvaluation(key, width, height, true, serial, 0L);
            return;
        }

        if (!ModernUiPreferences.isEnabled()) {
            DisplayDebug.log(
                "VECTOR_CACHE viewport change deferred because Modern UI is disabled"
                    + " viewport=" + key
            );
            return;
        }

        scheduleViewportEvaluation(
            key,
            width,
            height,
            false,
            serial,
            VIEWPORT_SETTLE_MS
        );
    }

    /**
     * Re-evaluates the current viewport when Modern UI is switched on after a
     * size change that happened while Standard UI was active.
     */
    public static void onModernUiEnabled() {
        startup();

        final String key = currentViewport;
        final int width = currentViewportWidth;
        final int height = currentViewportHeight;
        if (key.isEmpty() || width < 1 || height < 1) {
            return;
        }

        final long serial = ++viewportSerial;
        scheduleViewportEvaluation(key, width, height, true, serial, 0L);
    }

    /**
     * Returns a ready vector image immediately or queues disk-load/raster work
     * on the cache worker and returns null for this frame.
     */
    public static BufferedImage getOrQueue(
        String logicalPath,
        int width,
        int height,
        byte[] svgBytes
    ) {
        if (logicalPath == null
            || logicalPath.isEmpty()
            || width < 1
            || height < 1
            || svgBytes == null
            || svgBytes.length == 0) {
            return null;
        }

        startup();

        final String viewport = currentViewport;
        final String assetHash = sha256(svgBytes);
        final String spec = assetSpec(logicalPath, width, height);
        final String requestKey =
            (viewport.isEmpty() ? "<transient>" : viewport)
                + "|" + spec + "|" + assetHash;

        BufferedImage ready = readyImages.remove(requestKey);
        if (ready != null) {
            return ready;
        }

        Long retryAt = failedUntil.get(requestKey);
        if (retryAt != null && retryAt > System.currentTimeMillis()) {
            return null;
        }

        if (!inFlight.add(requestKey)) {
            return null;
        }

        final byte[] svgCopy = svgBytes.clone();
        WORKER.execute(new Runnable() {
            @Override
            public void run() {
                long startedAt = System.nanoTime();
                try {
                    BufferedImage image = null;
                    boolean persistent =
                        !viewport.isEmpty()
                            && viewport.equals(persistentViewport)
                            && viewport.equals(currentViewport);

                    if (persistent) {
                        image = loadValidatedImage(
                            viewport,
                            spec,
                            assetHash,
                            width,
                            height
                        );
                    }

                    if (image == null && persistent) {
                        image = loadReusableImageFromOtherViewport(
                            viewport,
                            spec,
                            assetHash,
                            width,
                            height
                        );
                    }

                    boolean rasterized = false;
                    if (image == null) {
                        image = ModernSvgRasterizer.rasterize(
                            svgCopy,
                            width,
                            height
                        );
                        rasterized = true;
                    }

                    if (image != null
                        && persistent
                        && viewport.equals(currentViewport)
                        && viewport.equals(persistentViewport)) {
                        storeValidatedImage(
                            viewport,
                            spec,
                            assetHash,
                            image
                        );
                    }

                    if (viewport.equals(currentViewport)
                        || viewport.isEmpty() && currentViewport.isEmpty()) {
                        readyImages.put(requestKey, image);
                    }

                    if (rasterized) {
                        DisplayDebug.log(
                            "VECTOR_CACHE rasterized path=" + logicalPath
                                + " size=" + width + "x" + height
                                + " viewport="
                                + (viewport.isEmpty() ? "<transient>" : viewport)
                                + " persistent=" + persistent
                                + " ms=" + millisSince(startedAt)
                        );
                    }
                } catch (Throwable ex) {
                    failedUntil.put(
                        requestKey,
                        System.currentTimeMillis() + FAILURE_RETRY_MS
                    );
                    DisplayDebug.log(
                        "VECTOR_CACHE asset failed path=" + logicalPath
                            + " size=" + width + "x" + height
                            + " viewport="
                            + (viewport.isEmpty() ? "<transient>" : viewport)
                            + ": " + ex,
                        ex
                    );
                } finally {
                    inFlight.remove(requestKey);
                }
            }
        });

        return null;
    }

    private static void scheduleViewportEvaluation(
        final String key,
        final int width,
        final int height,
        final boolean initial,
        final long serial,
        long delayMs
    ) {
        WORKER.schedule(new Runnable() {
            @Override
            public void run() {
                if (serial != viewportSerial || !key.equals(currentViewport)) {
                    return;
                }
                evaluateViewport(key, width, height, initial, serial);
            }
        }, delayMs, TimeUnit.MILLISECONDS);
    }

    private static void evaluateViewport(
        final String key,
        final int width,
        final int height,
        final boolean initial,
        final long serial
    ) {
        if (!key.equals(currentViewport) || serial != viewportSerial) {
            return;
        }

        CacheState state = inspectViewportCache(key, width, height);
        if (state == CacheState.VALID) {
            activateViewportCache(key, width, height, "existing-cache");
            return;
        }

        if (initial) {
            activateViewportCache(
                key,
                width,
                height,
                state == CacheState.OUTDATED
                    ? "startup-outdated-cache"
                    : "startup-new-cache"
            );
            return;
        }

        if (declinedViewports.contains(key)) {
            DisplayDebug.log(
                "VECTOR_CACHE persistence disabled for viewport=" + key
                    + " decision=previously-declined"
            );
            return;
        }

        requestViewportBuildPrompt(key, width, height, state, serial);
    }

    private static void requestViewportBuildPrompt(
        final String key,
        final int width,
        final int height,
        final CacheState state,
        final long serial
    ) {
        synchronized (STATE_LOCK) {
            if (promptVisible) {
                return;
            }
            promptVisible = true;
        }

        DisplayDebug.log(
            "VECTOR_CACHE prompt viewport=" + key
                + " state=" + state.name().toLowerCase()
        );

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int result = JOptionPane.NO_OPTION;
                try {
                    Component parent =
                        GameShell.fullScreenFrame != null
                            ? GameShell.fullScreenFrame
                            : GameShell.frame;
                    String reason =
                        state == CacheState.OUTDATED
                            ? "The saved vector cache for this size is outdated."
                            : "There is no saved vector cache for this size.";
                    result = JOptionPane.showConfirmDialog(
                        parent,
                        "Modern UI viewport changed to "
                            + width + " x " + height + ".\n"
                            + reason + "\n\n"
                            + "Build a cache for this size in the background?\n"
                            + "Up to " + MAX_VIEWPORT_CACHES
                            + " viewport caches are kept.",
                        "Modern UI Vector Cache",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                } catch (Throwable ex) {
                    DisplayDebug.log(
                        "VECTOR_CACHE prompt failed viewport=" + key
                            + ": " + ex,
                        ex
                    );
                }

                final boolean accepted = result == JOptionPane.YES_OPTION;
                synchronized (STATE_LOCK) {
                    promptVisible = false;
                }

                WORKER.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (serial != viewportSerial
                            || !key.equals(currentViewport)) {
                            scheduleCurrentViewportAfterPrompt();
                            return;
                        }

                        if (accepted) {
                            DisplayDebug.log(
                                "VECTOR_CACHE prompt decision=build viewport="
                                    + key
                            );
                            activateViewportCache(
                                key,
                                width,
                                height,
                                "user-approved"
                            );
                        } else {
                            declinedViewports.add(key);
                            persistentViewport = "";
                            DisplayDebug.log(
                                "VECTOR_CACHE prompt decision=skip viewport="
                                    + key
                            );
                        }
                    }
                });
            }
        });
    }

    private static void scheduleCurrentViewportAfterPrompt() {
        String key = currentViewport;
        int width = currentViewportWidth;
        int height = currentViewportHeight;
        if (key.isEmpty()
            || width < 1
            || height < 1
            || !ModernUiPreferences.isEnabled()) {
            return;
        }

        long serial = ++viewportSerial;
        scheduleViewportEvaluation(
            key,
            width,
            height,
            false,
            serial,
            VIEWPORT_SETTLE_MS
        );
    }

    private static void activateViewportCache(
        String key,
        int width,
        int height,
        String reason
    ) {
        File dir = viewportDirectory(key);
        if (!dir.exists() && !dir.mkdirs()) {
            persistentViewport = "";
            DisplayDebug.log(
                "VECTOR_CACHE cannot create viewport cache=" + dir.getAbsolutePath()
            );
            return;
        }

        Properties manifest = new Properties();
        manifest.setProperty(
            "schema",
            Integer.toString(CACHE_SCHEMA_VERSION)
        );
        manifest.setProperty("viewportWidth", Integer.toString(width));
        manifest.setProperty("viewportHeight", Integer.toString(height));
        manifest.setProperty(
            "lastUsed",
            Long.toString(System.currentTimeMillis())
        );
        manifest.setProperty(
            "renderer",
            "ModernSvgRasterizer"
        );
        saveProperties(
            new File(dir, "cache.properties"),
            manifest,
            "OSRS Client Killer Edition - Modern UI vector cache"
        );

        File assets = new File(dir, "assets.properties");
        if (!assets.isFile()) {
            saveProperties(
                assets,
                new Properties(),
                "Modern UI vector cache asset hashes"
            );
        }

        persistentViewport = key;
        declinedViewports.remove(key);
        pruneViewportCaches();

        DisplayDebug.log(
            "VECTOR_CACHE active viewport=" + key
                + " reason=" + reason
                + " thread=" + Thread.currentThread().getName()
        );
    }

    private static CacheState inspectViewportCache(
        String key,
        int width,
        int height
    ) {
        File dir = viewportDirectory(key);
        if (!dir.isDirectory()) {
            return CacheState.MISSING;
        }

        Properties manifest = loadProperties(
            new File(dir, "cache.properties")
        );
        int schema = parseInt(manifest.getProperty("schema"), -1);
        int storedWidth =
            parseInt(manifest.getProperty("viewportWidth"), -1);
        int storedHeight =
            parseInt(manifest.getProperty("viewportHeight"), -1);

        if (schema != CACHE_SCHEMA_VERSION
            || storedWidth != width
            || storedHeight != height) {
            DisplayDebug.log(
                "VECTOR_CACHE invalid viewport=" + key
                    + " storedSchema=" + schema
                    + " currentSchema=" + CACHE_SCHEMA_VERSION
                    + " storedSize=" + storedWidth + "x" + storedHeight
            );
            deleteRecursively(dir);
            return CacheState.OUTDATED;
        }

        return CacheState.VALID;
    }

    private static BufferedImage loadValidatedImage(
        String viewport,
        String spec,
        String assetHash,
        int width,
        int height
    ) {
        File dir = viewportDirectory(viewport);
        Properties assets = loadProperties(
            new File(dir, "assets.properties")
        );
        String storedHash = assets.getProperty(spec);
        if (!assetHash.equals(storedHash)) {
            if (storedHash != null && !storedHash.isEmpty()) {
                DisplayDebug.log(
                    "VECTOR_CACHE asset-version-change viewport=" + viewport
                        + " spec=" + spec
                        + " old=" + shortHash(storedHash)
                        + " new=" + shortHash(assetHash)
                );
            }
            return null;
        }

        File png = new File(dir, cacheFileName(spec));
        if (!png.isFile()) {
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(png);
            if (image == null
                || image.getWidth() != width
                || image.getHeight() != height) {
                return null;
            }

            touchManifest(dir);
            DisplayDebug.log(
                "VECTOR_CACHE hit viewport=" + viewport
                    + " spec=" + spec
            );
            return image;
        } catch (IOException ex) {
            DisplayDebug.log(
                "VECTOR_CACHE corrupt viewport=" + viewport
                    + " spec=" + spec + ": " + ex.getMessage()
            );
            return null;
        }
    }

    private static BufferedImage loadReusableImageFromOtherViewport(
        String targetViewport,
        String spec,
        String assetHash,
        int width,
        int height
    ) {
        File[] dirs = listViewportDirectories();
        for (File dir : dirs) {
            if (dir.getName().equals(targetViewport)) {
                continue;
            }

            Properties manifest = loadProperties(
                new File(dir, "cache.properties")
            );
            if (parseInt(manifest.getProperty("schema"), -1)
                != CACHE_SCHEMA_VERSION) {
                continue;
            }

            Properties assets = loadProperties(
                new File(dir, "assets.properties")
            );
            if (!assetHash.equals(assets.getProperty(spec))) {
                continue;
            }

            File png = new File(dir, cacheFileName(spec));
            if (!png.isFile()) {
                continue;
            }

            try {
                BufferedImage image = ImageIO.read(png);
                if (image != null
                    && image.getWidth() == width
                    && image.getHeight() == height) {
                    DisplayDebug.log(
                        "VECTOR_CACHE reused viewport=" + dir.getName()
                            + " -> " + targetViewport
                            + " spec=" + spec
                    );
                    return image;
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    private static void storeValidatedImage(
        String viewport,
        String spec,
        String assetHash,
        BufferedImage image
    ) throws IOException {
        File dir = viewportDirectory(viewport);
        if (!dir.isDirectory() && !dir.mkdirs()) {
            throw new IOException(
                "cannot create viewport cache " + dir.getAbsolutePath()
            );
        }

        File target = new File(dir, cacheFileName(spec));
        File temporary = new File(dir, target.getName() + ".tmp");

        if (!ImageIO.write(image, "png", temporary)) {
            throw new IOException("PNG writer unavailable");
        }

        try {
            Files.move(
                temporary.toPath(),
                target.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
            );
        } catch (IOException atomicMoveFailed) {
            Files.move(
                temporary.toPath(),
                target.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );
        }

        Properties assets = loadProperties(
            new File(dir, "assets.properties")
        );
        assets.setProperty(spec, assetHash);
        saveProperties(
            new File(dir, "assets.properties"),
            assets,
            "Modern UI vector cache asset hashes"
        );
        touchManifest(dir);
    }

    private static void touchManifest(File dir) {
        File file = new File(dir, "cache.properties");
        Properties manifest = loadProperties(file);
        manifest.setProperty(
            "schema",
            Integer.toString(CACHE_SCHEMA_VERSION)
        );
        manifest.setProperty(
            "lastUsed",
            Long.toString(System.currentTimeMillis())
        );
        saveProperties(
            file,
            manifest,
            "OSRS Client Killer Edition - Modern UI vector cache"
        );
    }

    private static void removeInvalidViewportCaches() {
        for (File dir : listViewportDirectories()) {
            Properties manifest = loadProperties(
                new File(dir, "cache.properties")
            );
            int schema = parseInt(
                manifest.getProperty("schema"),
                -1
            );
            if (schema != CACHE_SCHEMA_VERSION) {
                DisplayDebug.log(
                    "VECTOR_CACHE remove outdated viewport="
                        + dir.getName()
                        + " storedSchema=" + schema
                        + " currentSchema=" + CACHE_SCHEMA_VERSION
                );
                deleteRecursively(dir);
            }
        }
    }

    private static void pruneViewportCaches() {
        List<File> dirs = new ArrayList<>();
        Collections.addAll(dirs, listViewportDirectories());

        dirs.sort(new Comparator<File>() {
            @Override
            public int compare(File left, File right) {
                long leftUsed = manifestLastUsed(left);
                long rightUsed = manifestLastUsed(right);
                return Long.compare(rightUsed, leftUsed);
            }
        });

        for (int i = MAX_VIEWPORT_CACHES; i < dirs.size(); i++) {
            File dir = dirs.get(i);
            if (dir.getName().equals(persistentViewport)) {
                continue;
            }
            DisplayDebug.log(
                "VECTOR_CACHE evict viewport=" + dir.getName()
                    + " lastUsed=" + manifestLastUsed(dir)
            );
            deleteRecursively(dir);
        }
    }

    private static long manifestLastUsed(File dir) {
        Properties manifest = loadProperties(
            new File(dir, "cache.properties")
        );
        try {
            return Long.parseLong(
                manifest.getProperty(
                    "lastUsed",
                    Long.toString(dir.lastModified())
                )
            );
        } catch (Exception ignored) {
            return dir.lastModified();
        }
    }

    private static File[] listViewportDirectories() {
        File root = getCacheRoot();
        File[] dirs = root.listFiles(new java.io.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory()
                    && file.getName().matches("\\d+x\\d+");
            }
        });
        return dirs == null ? new File[0] : dirs;
    }

    private static File getCacheRoot() {
        return new File(
            ModernUiPreferences.getUiRootDirectory(),
            "cache" + File.separator + "vector"
        );
    }

    private static File viewportDirectory(String viewport) {
        return new File(getCacheRoot(), viewport);
    }

    private static String viewportKey(int width, int height) {
        return width + "x" + height;
    }

    private static String assetSpec(
        String logicalPath,
        int width,
        int height
    ) {
        return logicalPath + "|" + width + "x" + height;
    }

    private static String cacheFileName(String spec) {
        return sha256(spec.getBytes(StandardCharsets.UTF_8)) + ".png";
    }

    private static Properties loadProperties(File file) {
        Properties properties = new Properties();
        if (!file.isFile()) {
            return properties;
        }
        try (FileInputStream input = new FileInputStream(file)) {
            properties.load(input);
        } catch (IOException ex) {
            DisplayDebug.log(
                "VECTOR_CACHE unable to read " + file.getAbsolutePath()
                    + ": " + ex.getMessage()
            );
        }
        return properties;
    }

    private static void saveProperties(
        File file,
        Properties properties,
        String comment
    ) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            DisplayDebug.log(
                "VECTOR_CACHE unable to create directory="
                    + parent.getAbsolutePath()
            );
            return;
        }

        File temporary = new File(file.getParentFile(), file.getName() + ".tmp");
        try (FileOutputStream output = new FileOutputStream(temporary)) {
            properties.store(output, comment);
        } catch (IOException ex) {
            DisplayDebug.log(
                "VECTOR_CACHE unable to write " + file.getAbsolutePath()
                    + ": " + ex.getMessage()
            );
            return;
        }

        try {
            Files.move(
                temporary.toPath(),
                file.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
            );
        } catch (IOException atomicMoveFailed) {
            try {
                Files.move(
                    temporary.toPath(),
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException ex) {
                DisplayDebug.log(
                    "VECTOR_CACHE unable to replace "
                        + file.getAbsolutePath()
                        + ": " + ex.getMessage()
                );
            }
        }
    }

    private static void deleteRecursively(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteRecursively(child);
            }
        }
        if (!file.delete()) {
            DisplayDebug.log(
                "VECTOR_CACHE unable to delete " + file.getAbsolutePath()
            );
        }
    }

    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static String sha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            StringBuilder builder = new StringBuilder(hash.length * 2);
            for (byte value : hash) {
                builder.append(
                    String.format("%02x", value & 0xFF)
                );
            }
            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("SHA-256 unavailable", ex);
        }
    }

    private static String shortHash(String hash) {
        return hash == null || hash.length() <= 12
            ? String.valueOf(hash)
            : hash.substring(0, 12);
    }

    private static String millisSince(long startedAt) {
        return String.format(
            java.util.Locale.ROOT,
            "%.3f",
            (System.nanoTime() - startedAt) / 1_000_000.0D
        );
    }

    private enum CacheState {
        MISSING,
        VALID,
        OUTDATED
    }
}
