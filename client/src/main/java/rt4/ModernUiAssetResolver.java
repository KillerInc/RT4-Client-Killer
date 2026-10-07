package rt4;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Layered Modern UI asset resolver.
 *
 * Search order:
 *  1. ui/overrides
 *  2. enabled add-ons, highest priority first
 *  3. selected custom style
 *  4. selected style's base chain
 *  5. built-in Killer Modern UI resources
 *
 * There is intentionally no JS5/Index-8 fallback.
 */
public final class ModernUiAssetResolver {
    private static final String BUILT_IN_PACK_RESOURCE = "/ui/packs/KillerModernUI.uipack";
    private static final int MAX_ASSET_BYTES = 16 * 1024 * 1024;
    private static final int MAX_CACHE = 256;

    private static final LinkedHashMap<CacheKey, ModernUiImage> imageCache =
        new LinkedHashMap<CacheKey, ModernUiImage>(64, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<CacheKey, ModernUiImage> eldest) {
                if (size() <= MAX_CACHE) {
                    return false;
                }
                eldest.getValue().dispose();
                return true;
            }
        };

    private static final Map<String, String> resolvedSources = new LinkedHashMap<>();

    private ModernUiAssetResolver() {
    }

    public static ModernUiImage get(String logicalPath, int width, int height) {
        String path = normalize(logicalPath);
        if (path == null || width < 1 || height < 1) {
            return null;
        }

        CacheKey key = new CacheKey(path, width, height, ModernUiManager.getReloadGeneration());
        synchronized (imageCache) {
            ModernUiImage cached = imageCache.get(key);
            if (cached != null) {
                return cached;
            }
        }

        try {
            ResolvedBytes resolved = resolve(path);
            if (resolved == null) {
                return null;
            }

            BufferedImage image;
            if (resolved.name.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                image = ModernSvgRasterizer.rasterize(resolved.bytes, width, height);
            } else {
                image = ImageIO.read(new ByteArrayInputStream(resolved.bytes));
                if (image == null) {
                    throw new IllegalArgumentException("unsupported image");
                }
                if (image.getWidth() != width || image.getHeight() != height) {
                    image = resize(image, width, height);
                }
            }

            ModernUiImage modern = new ModernUiImage(image);
            synchronized (imageCache) {
                imageCache.put(key, modern);
                resolvedSources.put(path, resolved.source);
            }
            return modern;
        } catch (Exception ex) {
            DisplayDebug.log("MODERN_UI asset failed " + path + ": " + ex.getMessage());
            return null;
        }
    }

    public static byte[] getBytes(String logicalPath) {
        String path = normalizeExact(logicalPath);
        if (path == null) {
            return null;
        }

        try {
            ResolvedBytes resolved = resolveCandidates(new String[] {path});
            if (resolved == null) {
                return null;
            }
            synchronized (imageCache) {
                resolvedSources.put(path, resolved.source);
            }
            return resolved.bytes;
        } catch (Exception ex) {
            DisplayDebug.log("MODERN_UI asset failed " + path + ": " + ex.getMessage());
            return null;
        }
    }

    public static String getResolvedSource(String logicalPath) {
        synchronized (imageCache) {
            return resolvedSources.get(normalize(logicalPath));
        }
    }

    public static void clear() {
        synchronized (imageCache) {
            for (ModernUiImage image : imageCache.values()) {
                image.dispose();
            }
            imageCache.clear();
            resolvedSources.clear();
        }
    }

    private static ResolvedBytes resolve(String logicalPath) throws Exception {
        return resolveCandidates(new String[] {logicalPath + ".svg", logicalPath + ".png"});
    }

    private static ResolvedBytes resolveCandidates(String[] candidates) throws Exception {
        File overrides = new File(ModernUiPreferences.getUiRootDirectory(), "overrides");
        for (String candidate : candidates) {
            File file = new File(overrides, candidate);
            if (file.isFile() && isInside(overrides, file)) {
                return new ResolvedBytes(candidate, readFile(file), "User Overrides");
            }
        }

        UiStyleInfo selected = UiStyleRepository.getEffectiveStyle(ModernUiPreferences.getStyleId());
        Set<String> enabled = ModernUiPreferences.getEnabledAddons();

        List<UiStyleInfo> addons = new ArrayList<>();
        for (UiStyleInfo addon : UiStyleRepository.getAddons()) {
            if (enabled.contains(addon.id)
                && UiStyleRepository.requirementsSatisfied(addon, selected.id, enabled)) {
                addons.add(addon);
            }
        }
        addons.sort(Comparator.comparingInt((UiStyleInfo info) -> info.priority).reversed());

        for (UiStyleInfo addon : addons) {
            ResolvedBytes result = readArchiveCandidates(addon, candidates);
            if (result != null) {
                return result;
            }
        }

        java.util.HashSet<String> visited = new java.util.HashSet<>();
        UiStyleInfo current = selected;
        while (current != null && visited.add(current.id)) {
            if (!current.builtIn) {
                ResolvedBytes result = readArchiveCandidates(current, candidates);
                if (result != null) {
                    return result;
                }
            }

            if (current.base == null || current.base.isEmpty() || current.base.equals("none")) {
                break;
            }
            current = UiStyleRepository.get(current.base);
        }

        ResolvedBytes builtIn = readBuiltInPackCandidates(candidates);
        if (builtIn != null) {
            return builtIn;
        }

        return null;
    }

    private static ResolvedBytes readBuiltInPackCandidates(String[] candidates) throws Exception {
        InputStream resource = ModernUiAssetResolver.class.getResourceAsStream(BUILT_IN_PACK_RESOURCE);
        if (resource == null) {
            throw new IllegalStateException("built-in UI pack is missing: " + BUILT_IN_PACK_RESOURCE);
        }

        java.util.HashSet<String> wanted = new java.util.HashSet<>(java.util.Arrays.asList(candidates));
        try (ZipInputStream zip = new ZipInputStream(resource)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory() || !wanted.contains(entry.getName())) {
                    continue;
                }
                if (entry.getSize() > MAX_ASSET_BYTES) {
                    throw new IllegalArgumentException(entry.getName() + " is too large");
                }
                return new ResolvedBytes(
                    entry.getName(),
                    readStream(zip, MAX_ASSET_BYTES),
                    "Killer Modern UI (" + BUILT_IN_PACK_RESOURCE + ")"
                );
            }
        }
        return null;
    }

    private static ResolvedBytes readArchiveCandidates(UiStyleInfo info, String[] candidates) throws Exception {
        if (info.archive == null || !info.archive.isFile()) {
            return null;
        }

        try (ZipFile zip = new ZipFile(info.archive)) {
            for (String candidate : candidates) {
                ZipEntry entry = zip.getEntry(candidate);
                if (entry == null || entry.isDirectory()) {
                    continue;
                }
                if (entry.getSize() > MAX_ASSET_BYTES) {
                    throw new IllegalArgumentException(candidate + " is too large");
                }
                try (InputStream input = zip.getInputStream(entry)) {
                    return new ResolvedBytes(candidate, readStream(input, MAX_ASSET_BYTES), info.name);
                }
            }
        }
        return null;
    }

    private static byte[] readFile(File file) throws Exception {
        if (file.length() > MAX_ASSET_BYTES) {
            throw new IllegalArgumentException(file.getName() + " is too large");
        }
        try (InputStream input = new FileInputStream(file)) {
            return readStream(input, MAX_ASSET_BYTES);
        }
    }

    private static byte[] readStream(InputStream input, int limit) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int total = 0;
        int read;
        while ((read = input.read(buffer)) != -1) {
            total += read;
            if (total > limit) {
                throw new IllegalArgumentException("asset exceeds size limit");
            }
            output.write(buffer, 0, read);
        }
        return output.toByteArray();
    }

    private static BufferedImage resize(BufferedImage source, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return result;
    }

    private static boolean isInside(File root, File file) throws Exception {
        String rootPath = root.getCanonicalPath() + File.separator;
        return file.getCanonicalPath().startsWith(rootPath);
    }

    private static String normalize(String value) {
        String path = normalizeExact(value);
        if (path == null) {
            return null;
        }
        if (path.endsWith(".svg") || path.endsWith(".png")) {
            path = path.substring(0, path.length() - 4);
        }
        return path;
    }

    private static String normalizeExact(String value) {
        if (value == null) {
            return null;
        }
        String path = value.replace('\\', '/').trim();
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.isEmpty() || path.contains("..") || path.contains(":")) {
            return null;
        }
        return path;
    }

    private static final class ResolvedBytes {
        private final String name;
        private final byte[] bytes;
        private final String source;

        private ResolvedBytes(String name, byte[] bytes, String source) {
            this.name = name;
            this.bytes = bytes;
            this.source = source;
        }
    }

    private static final class CacheKey {
        private final String path;
        private final int width;
        private final int height;
        private final int generation;

        private CacheKey(String path, int width, int height, int generation) {
            this.path = path;
            this.width = width;
            this.height = height;
            this.generation = generation;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof CacheKey)) {
                return false;
            }
            CacheKey key = (CacheKey) other;
            return width == key.width && height == key.height && generation == key.generation && path.equals(key.path);
        }

        @Override
        public int hashCode() {
            int result = path.hashCode();
            result = 31 * result + width;
            result = 31 * result + height;
            result = 31 * result + generation;
            return result;
        }
    }
}
