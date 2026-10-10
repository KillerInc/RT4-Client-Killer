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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
    private static final Set<String> missingPaths = new java.util.HashSet<>();

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

            // Missing resources are stable for the current reload generation.
            // Without this negative cache every frame re-opened/scanned style
            // archives for the same absent component assets.
            if (missingPaths.contains(path)) {
                return null;
            }
        }

        try {
            ResolvedBytes resolved = resolve(path);
            if (resolved == null) {
                logMissingOnce(path);
                return null;
            }

            BufferedImage image;
            if (resolved.name.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                // SVG disk lookup, rasterization and persistence are handled
                // entirely by the dedicated vector-cache worker. A miss is
                // allowed to return null for a frame while the worker builds
                // the correctly-sized raster in the background.
                image = ModernVectorCacheManager.getOrQueue(
                    path,
                    width,
                    height,
                    resolved.bytes
                );
                if (image == null) {
                    return null;
                }
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

    /**
     * Builds the complete effective Modern UI vector set for the current
     * scalable canvas. This runs on the ModernVectorCache worker, so archive
     * discovery, hashing and SVG raster work never block the game thread.
     */
    static void prebuildCompleteVectorCache(
        int canvasWidth,
        int canvasHeight
    ) {
        long startedAt = System.nanoTime();

        try {
            LinkedHashMap<String, List<WarmupSize>> plan =
                new LinkedHashMap<>();

            addKnownWarmupVariants(plan, canvasWidth, canvasHeight);

            // Discover SVGs supplied by the built-in pack, selected style,
            // enabled add-ons and user overrides. New mod files therefore join
            // the cache automatically without requiring another hard-coded
            // path here.
            Set<String> discovered = discoverEffectiveVectorPaths();
            for (String path : discovered) {
                if (!plan.containsKey(path)) {
                    plan.put(path, new ArrayList<WarmupSize>());
                }
            }

            LinkedHashMap<String, ResolvedBytes> resolvedByPath =
                new LinkedHashMap<>();

            // Resolve each logical vector once. If a style/add-on replaces a
            // built-in SVG with a PNG, it is intentionally excluded from the
            // vector cache.
            for (Map.Entry<String, List<WarmupSize>> entry : plan.entrySet()) {
                String path = entry.getKey();
                ResolvedBytes resolved = resolve(path);
                if (resolved == null
                    || !resolved.name.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                    continue;
                }

                resolvedByPath.put(path, resolved);

                int[] intrinsic = parseSvgIntrinsicSize(resolved.bytes);
                if (intrinsic != null) {
                    addWarmupSize(
                        entry.getValue(),
                        intrinsic[0],
                        intrinsic[1]
                    );
                }
            }

            int total = 0;
            for (Map.Entry<String, ResolvedBytes> entry
                : resolvedByPath.entrySet()) {
                List<WarmupSize> sizes = plan.get(entry.getKey());
                if (sizes != null) {
                    total += sizes.size();
                }
            }

            String viewport = canvasWidth + "x" + canvasHeight;
            ModernVectorCacheManager.beginPlannedBuild(
                viewport,
                total,
                "complete-modern-ui"
            );

            int queued = 0;
            for (Map.Entry<String, ResolvedBytes> entry
                : resolvedByPath.entrySet()) {
                String path = entry.getKey();
                ResolvedBytes resolved = entry.getValue();
                List<WarmupSize> sizes = plan.get(path);
                if (sizes == null) {
                    continue;
                }

                for (WarmupSize size : sizes) {
                    ModernVectorCacheManager.queuePrebuildAsset(
                        path,
                        size.width,
                        size.height,
                        resolved.bytes
                    );
                    queued++;
                }
            }

            DisplayDebug.log(
                "VECTOR_CACHE complete-plan viewport=" + viewport
                    + " vectorPaths=" + resolvedByPath.size()
                    + " variants=" + queued
                    + " discoverMs="
                    + String.format(
                        Locale.ROOT,
                        "%.3f",
                        (System.nanoTime() - startedAt) / 1_000_000.0D
                    )
            );
        } catch (Throwable ex) {
            DisplayDebug.log(
                "VECTOR_CACHE complete-plan failed viewport="
                    + canvasWidth + "x" + canvasHeight
                    + ": " + ex,
                ex
            );
            ModernVectorCacheManager.finishPlannedBuildOnFailure();
        }
    }

    private static void addKnownWarmupVariants(
        Map<String, List<WarmupSize>> plan,
        int canvasWidth,
        int canvasHeight
    ) {
        // Graphics Options - current exact renderer sizes.
        addWarmupSpec(
            plan,
            "graphics-options/panel",
            ModernUiMetrics.GRAPHICS_PANEL_WIDTH,
            ModernUiMetrics.GRAPHICS_PANEL_HEIGHT
        );
        addWarmupSpec(
            plan,
            "graphics-options/divider",
            ModernUiMetrics.GRAPHICS_PANEL_WIDTH
                - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );

        addWarmupSpec(
            plan,
            "controls/button",
            ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
            ModernUiMetrics.DISPLAY_BUTTON_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/button",
            ModernUiMetrics.NAV_BUTTON_WIDTH,
            ModernUiMetrics.NAV_BUTTON_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/button",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/button",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.NAV_BUTTON_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/button-active",
            ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
            ModernUiMetrics.DISPLAY_BUTTON_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/button-active",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
        addWarmupSpec(plan, "controls/button-disabled", 116, 20);

        addWarmupSpec(
            plan,
            "controls/dropdown",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
        // The injected Modern UI selector keeps its cache-defined width.
        addWarmupSpec(plan, "controls/dropdown", 116, 22);
        addWarmupSpec(plan, "controls/dropdown", 128, 22);
        addWarmupSpec(plan, "controls/dropdown-disabled", 116, 20);

        addWarmupSpec(
            plan,
            "controls/popup",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT * 2
        );
        addWarmupSpec(
            plan,
            "controls/popup",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT * 3
        );
        addWarmupSpec(
            plan,
            "controls/choice-hover",
            ModernUiMetrics.CONTROL_WIDTH - 4,
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT - 2
        );
        addWarmupSpec(
            plan,
            "controls/choice-selected",
            ModernUiMetrics.CONTROL_WIDTH - 4,
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT - 2
        );
        addWarmupSpec(
            plan,
            "controls/slider-track",
            164,
            ModernUiMetrics.SLIDER_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/slider-track",
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.SLIDER_HEIGHT
        );
        addWarmupSpec(
            plan,
            "controls/slider-knob",
            ModernUiMetrics.SLIDER_KNOB_WIDTH,
            ModernUiMetrics.SLIDER_KNOB_HEIGHT
        );
        addWarmupSpec(
            plan,
            "icons/dropdown",
            ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
            ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
        );
        addWarmupSpec(plan, "icons/dropdown", 12, 8);

        // Main menu. Most cache components are fixed-size; the logo follows
        // the same viewport-dependent formula as ModernUiRenderer.
        int logoWidth = Math.min(
            620,
            Math.max(400, canvasWidth * 54 / 100)
        );
        logoWidth = Math.min(
            logoWidth,
            Math.max(240, canvasWidth - 40)
        );
        int logoHeight = Math.max(
            90,
            logoWidth * 500 / 1445
        );

        addWarmupSpec(plan, "main-menu/scroll", 552, 582);
        addWarmupSpec(plan, "main-menu/logo", logoWidth, logoHeight);
        addWarmupSpec(
            plan,
            "main-menu/button",
            ModernUiMetrics.MAIN_MENU_BUTTON_WIDTH,
            ModernUiMetrics.MAIN_MENU_BUTTON_HEIGHT
        );
        addWarmupSpec(plan, "main-menu/button", 140, 24);
        addWarmupSpec(
            plan,
            "main-menu/button-active",
            ModernUiMetrics.MAIN_MENU_BUTTON_WIDTH,
            ModernUiMetrics.MAIN_MENU_BUTTON_HEIGHT
        );
        addWarmupSpec(plan, "main-menu/button-active", 140, 24);
        addWarmupSpec(plan, "main-menu/choice", 95, 62);
        addWarmupSpec(plan, "main-menu/choice", 68, 38);
        addWarmupSpec(plan, "main-menu/choice-active", 95, 62);
        addWarmupSpec(plan, "main-menu/choice-active", 68, 38);
        addWarmupSpec(plan, "main-menu/music-volume-track", 110, 18);
        addWarmupSpec(plan, "main-menu/music-volume-track", 180, 18);
        addWarmupSpec(plan, "main-menu/music-volume-knob", 20, 26);
        addWarmupSpec(plan, "main-menu/sd-icon", 63, 41);
        addWarmupSpec(plan, "main-menu/sd-icon", 123, 80);
        addWarmupSpec(plan, "main-menu/hd-icon", 41, 41);
        addWarmupSpec(plan, "main-menu/hd-icon", 24, 24);

        // Still-supported frame assets. They are included even while the
        // current main-menu composition primarily uses scroll.svg.
        addWarmupSpec(plan, "main-menu/panel", 220, 250);
        addWarmupSpec(plan, "main-menu/header", 300, 32);
        addWarmupSpec(plan, "main-menu/footer", 300, 32);
        addWarmupSpec(plan, "main-menu/edge", 12, 220);

        // Audio Options. These exact sizes match ModernAudioOptionsUi so the
        // complete login UI vector cache is ready before this screen opens.
        addWarmupSpec(
            plan,
            "audio-options/panel",
            ModernUiMetrics.AUDIO_PANEL_WIDTH,
            ModernUiMetrics.AUDIO_PANEL_HEIGHT
        );
        addWarmupSpec(
            plan,
            "audio-options/divider",
            ModernUiMetrics.AUDIO_PANEL_WIDTH
                - ModernUiMetrics.AUDIO_PANEL_INSET * 2,
            4
        );
        addWarmupSpec(
            plan,
            "audio-options/slider-track",
            ModernUiMetrics.AUDIO_SLIDER_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_HEIGHT
        );
        addWarmupSpec(
            plan,
            "audio-options/slider-knob",
            ModernUiMetrics.AUDIO_SLIDER_KNOB_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_KNOB_HEIGHT
        );
        addWarmupSpec(
            plan,
            "audio-options/toggle-off",
            ModernUiMetrics.AUDIO_TOGGLE_SIZE,
            ModernUiMetrics.AUDIO_TOGGLE_SIZE
        );
        addWarmupSpec(
            plan,
            "audio-options/toggle-on",
            ModernUiMetrics.AUDIO_TOGGLE_SIZE,
            ModernUiMetrics.AUDIO_TOGGLE_SIZE
        );
        addWarmupSpec(
            plan,
            "audio-options/button",
            ModernUiMetrics.AUDIO_BUTTON_WIDTH,
            ModernUiMetrics.AUDIO_BUTTON_HEIGHT
        );
        addWarmupSpec(
            plan,
            "audio-options/button-active",
            ModernUiMetrics.AUDIO_BUTTON_WIDTH,
            ModernUiMetrics.AUDIO_BUTTON_HEIGHT
        );
    }

    private static void addWarmupSpec(
        Map<String, List<WarmupSize>> plan,
        String path,
        int width,
        int height
    ) {
        if (width < 1 || height < 1) {
            return;
        }
        List<WarmupSize> sizes = plan.get(path);
        if (sizes == null) {
            sizes = new ArrayList<>();
            plan.put(path, sizes);
        }
        addWarmupSize(sizes, width, height);
    }

    private static void addWarmupSize(
        List<WarmupSize> sizes,
        int width,
        int height
    ) {
        if (width < 1 || height < 1) {
            return;
        }
        for (WarmupSize size : sizes) {
            if (size.width == width && size.height == height) {
                return;
            }
        }
        sizes.add(new WarmupSize(width, height));
    }

    private static Set<String> discoverEffectiveVectorPaths()
        throws Exception {
        LinkedHashSet<String> paths = new LinkedHashSet<>();

        collectBuiltInVectorPaths(paths);

        File overrides = new File(
            ModernUiPreferences.getUiRootDirectory(),
            "overrides"
        );
        collectDirectoryVectorPaths(overrides, overrides, paths);

        UiStyleInfo selected = UiStyleRepository.getEffectiveStyle(
            ModernUiPreferences.getStyleId()
        );
        Set<String> enabled = ModernUiPreferences.getEnabledAddons();

        List<UiStyleInfo> addons = new ArrayList<>();
        for (UiStyleInfo addon : UiStyleRepository.getAddons()) {
            if (enabled.contains(addon.id)
                && UiStyleRepository.requirementsSatisfied(
                    addon,
                    selected.id,
                    enabled
                )) {
                addons.add(addon);
            }
        }
        addons.sort(
            Comparator.comparingInt(
                (UiStyleInfo info) -> info.priority
            ).reversed()
        );
        for (UiStyleInfo addon : addons) {
            collectArchiveVectorPaths(addon, paths);
        }

        java.util.HashSet<String> visited = new java.util.HashSet<>();
        UiStyleInfo current = selected;
        while (current != null && visited.add(current.id)) {
            collectArchiveVectorPaths(current, paths);
            if (current.base == null
                || current.base.isEmpty()
                || current.base.equals("none")) {
                break;
            }
            current = UiStyleRepository.get(current.base);
        }

        return paths;
    }

    private static void collectBuiltInVectorPaths(Set<String> paths)
        throws Exception {
        InputStream resource =
            ModernUiAssetResolver.class.getResourceAsStream(
                BUILT_IN_PACK_RESOURCE
            );
        if (resource == null) {
            return;
        }

        try (ZipInputStream zip = new ZipInputStream(resource)) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                String name = entry.getName().replace('\\', '/');
                if (name.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                    paths.add(name.substring(0, name.length() - 4));
                }
            }
        }
    }

    private static void collectArchiveVectorPaths(
        UiStyleInfo info,
        Set<String> paths
    ) throws Exception {
        if (info == null
            || info.builtIn
            || info.archive == null
            || !info.archive.isFile()) {
            return;
        }

        try (ZipFile zip = new ZipFile(info.archive)) {
            java.util.Enumeration<? extends ZipEntry> entries =
                zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                String name = entry.getName().replace('\\', '/');
                if (name.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                    paths.add(name.substring(0, name.length() - 4));
                }
            }
        }
    }

    private static void collectDirectoryVectorPaths(
        File root,
        File directory,
        Set<String> paths
    ) throws Exception {
        if (root == null
            || directory == null
            || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                collectDirectoryVectorPaths(root, file, paths);
                continue;
            }
            if (!file.getName().toLowerCase(Locale.ROOT).endsWith(".svg")) {
                continue;
            }
            String rootPath = root.getCanonicalPath();
            String filePath = file.getCanonicalPath();
            if (!filePath.startsWith(rootPath)) {
                continue;
            }
            String relative = filePath.substring(rootPath.length())
                .replace('\\', '/');
            while (relative.startsWith("/")) {
                relative = relative.substring(1);
            }
            if (relative.toLowerCase(Locale.ROOT).endsWith(".svg")) {
                paths.add(
                    relative.substring(0, relative.length() - 4)
                );
            }
        }
    }

    private static int[] parseSvgIntrinsicSize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        String text = new String(bytes, StandardCharsets.UTF_8);
        String lower = text.toLowerCase(Locale.ROOT);
        int svgStart = lower.indexOf("<svg");
        if (svgStart < 0) {
            return null;
        }
        int tagEnd = text.indexOf('>', svgStart);
        if (tagEnd < 0) {
            return null;
        }
        String root = text.substring(svgStart, tagEnd + 1);

        Double width = parseSvgNumberAttribute(root, "width");
        Double height = parseSvgNumberAttribute(root, "height");
        if (width != null && height != null
            && width > 0.0D && height > 0.0D) {
            return new int[] {
                Math.max(1, (int) Math.round(width)),
                Math.max(1, (int) Math.round(height))
            };
        }

        String viewBox = parseSvgStringAttribute(root, "viewBox");
        if (viewBox == null) {
            return null;
        }
        String[] parts = viewBox.trim().split("[\\s,]+");
        if (parts.length != 4) {
            return null;
        }
        try {
            double viewWidth = Double.parseDouble(parts[2]);
            double viewHeight = Double.parseDouble(parts[3]);
            if (viewWidth <= 0.0D || viewHeight <= 0.0D) {
                return null;
            }
            return new int[] {
                Math.max(1, (int) Math.round(viewWidth)),
                Math.max(1, (int) Math.round(viewHeight))
            };
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static Double parseSvgNumberAttribute(
        String tag,
        String attribute
    ) {
        String value = parseSvgStringAttribute(tag, attribute);
        if (value == null) {
            return null;
        }
        value = value.trim().toLowerCase(Locale.ROOT);
        if (value.endsWith("px")) {
            value = value.substring(0, value.length() - 2).trim();
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static String parseSvgStringAttribute(
        String tag,
        String attribute
    ) {
        String lower = tag.toLowerCase(Locale.ROOT);
        String wanted = attribute.toLowerCase(Locale.ROOT) + "=";
        int index = lower.indexOf(wanted);
        if (index < 0) {
            return null;
        }

        int valueStart = index + wanted.length();
        while (valueStart < tag.length()
            && Character.isWhitespace(tag.charAt(valueStart))) {
            valueStart++;
        }
        if (valueStart >= tag.length()) {
            return null;
        }

        char quote = tag.charAt(valueStart);
        if (quote != '\'' && quote != '"') {
            return null;
        }
        int valueEnd = tag.indexOf(quote, valueStart + 1);
        if (valueEnd < 0) {
            return null;
        }
        return tag.substring(valueStart + 1, valueEnd);
    }

    public static byte[] getBytes(String logicalPath) {
        String path = normalizeExact(logicalPath);
        if (path == null) {
            return null;
        }

        try {
            ResolvedBytes resolved = resolveCandidates(new String[] {path});
            if (resolved == null) {
                logMissingOnce(path);
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
            missingPaths.clear();
        }
    }

    private static void logMissingOnce(String path) {
        synchronized (imageCache) {
            if (!missingPaths.add(path)) {
                return;
            }
        }
        DisplayDebug.log(
            "MODERN_UI RESOURCE MISSING path=\"" + path + "\""
                + " style=" + ModernUiPreferences.getStyleId()
        );
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

    private static final class WarmupSize {
        private final int width;
        private final int height;

        private WarmupSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
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
