package rt4;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Killer UI text engine.
 *
 * Replacement for every visible RT4 text path. It parses Jagex markup first,
 * then renders the selected RuneScape TTF with Java2D into an ARGB sprite
 * composited through the existing software/OpenGL sprite paths. The 3D world
 * renderer is untouched; screen-space text over that world uses this class.
 */
public final class KillerUiText {
    public static final int PLAIN_11 = 0;
    public static final int PLAIN_12 = 1;
    public static final int BOLD_12 = 2;
    public static final int QUILL_8 = 3;
    public static final int QUILL = 4;
    public static final int QUILL_CAPS = 5;
    public static final int FAIRY = 6;
    public static final int FAIRY_LARGE = 7;
    public static final int BARBARIAN_ASSAULT = 8;
    public static final int SUROK = 9;

    private static final int FONT_STYLE_COUNT = 10;

    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_WAVE = 1;
    public static final int EFFECT_WAVE2 = 2;
    public static final int EFFECT_SHAKE = 3;
    public static final int EFFECT_RAINBOW = 4;

    private static final String PLAIN_11_RESOURCE = "/killer-fonts/RuneScape-Plain-11.ttf";
    private static final String PLAIN_12_RESOURCE = "/killer-fonts/RuneScape-Plain-12.ttf";
    private static final String BOLD_12_RESOURCE = "/killer-fonts/RuneScape-Bold-12.ttf";
    private static final String QUILL_8_RESOURCE = "/killer-fonts/RuneScape-Quill-8.ttf";
    private static final String QUILL_RESOURCE = "/killer-fonts/RuneScape-Quill.ttf";
    private static final String QUILL_CAPS_RESOURCE = "/killer-fonts/RuneScape-Quill-Caps.ttf";
    private static final String FAIRY_RESOURCE = "/killer-fonts/RuneScape-Fairy.ttf";
    private static final String FAIRY_LARGE_RESOURCE = "/killer-fonts/RuneScape-Fairy-Large.ttf";
    private static final String BARBARIAN_ASSAULT_RESOURCE = "/killer-fonts/RuneScape-Barbarian-Assault.ttf";
    private static final String SUROK_RESOURCE = "/killer-fonts/RuneScape-Surok.ttf";

    private static final Charset WINDOWS_1252 = Charset.forName("windows-1252");
    private static final FontRenderContext FRC = new FontRenderContext(null, true, true);

    private static final java.awt.Font[] BASE_FONTS = new java.awt.Font[FONT_STYLE_COUNT];
    private static final java.awt.Font[] SCALED_FONTS = new java.awt.Font[FONT_STYLE_COUNT];
    private static final Map<Integer, Integer> CACHE_FONT_STYLES = new HashMap<Integer, Integer>();
    private static final Map<Integer, String> CACHE_FONT_NAMES = new HashMap<Integer, String>();
    private static double loadedScale = -1.0D;

    private static final int CACHE_LIMIT = 320;
    private static final LinkedHashMap<String, RenderedText> CACHE =
        new LinkedHashMap<String, RenderedText>(CACHE_LIMIT + 1, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, RenderedText> eldest) {
                return size() > CACHE_LIMIT;
            }
        };

    private KillerUiText() {
    }

    public static synchronized void clearCache() {
        CACHE.clear();
        loadedScale = -1.0D;
        for (int i = 0; i < SCALED_FONTS.length; i++) {
            SCALED_FONTS[i] = null;
        }
    }

    public static void forbidStockRenderer(String operation) {
        KillerUiLog.write("FATAL stockTextRendererUsed operation=" + operation);
        throw new IllegalStateException(
            "Stock RT4 text renderer is forbidden by Killer UI rewrite: " + operation
        );
    }

    public static java.awt.Font getAwtFont(int style) {
        return getFont(style);
    }

    public static void drawAwtCentered(
        java.awt.Graphics graphics,
        JagString text,
        int style,
        int centerX,
        int baselineY,
        int color
    ) {
        if (graphics == null || text == null) {
            return;
        }

        java.awt.Font font = getFont(style);
        String plain = plainText(text);
        graphics.setFont(font);
        graphics.setColor(new Color(color & 0xFFFFFF));

        if (graphics instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            GlyphVector gv = font.createGlyphVector(FRC, plain);
            int width = (int) Math.ceil(gv.getLogicalBounds().getBounds2D().getWidth());
            g2.drawGlyphVector(gv, centerX - width / 2, baselineY);
        } else {
            java.awt.FontMetrics metrics = graphics.getFontMetrics(font);
            graphics.drawString(plain, centerX - metrics.stringWidth(plain) / 2, baselineY);
        }
    }

    public static int styleForFontId(int fontId) {
        Integer registered = CACHE_FONT_STYLES.get(fontId);
        if (registered != null) {
            return registered;
        }
        if (fontId == Sprites.p11FullId) {
            return PLAIN_11;
        }
        if (fontId == Sprites.p12FullId) {
            return PLAIN_12;
        }
        if (fontId == Sprites.b12FullId) {
            return BOLD_12;
        }

        String knownName = CACHE_FONT_NAMES.get(fontId);
        KillerUiLog.write(
            "FATAL unsupportedUiFontId fontId=" + fontId
                + " cacheName=" + (knownName == null ? "UNKNOWN" : knownName)
        );
        throw new IllegalStateException(
            "Unsupported Killer text font id " + fontId
                + (knownName == null ? "" : " (" + knownName + ")")
        );
    }

    public static int getParagraphLineCount(int fontId, JagString text, int width) {
        int style = styleForFontId(fontId);
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE, 0);
        return layout(parsed, Math.max(1, width), 0).size();
    }

    public static int getMaxLineWidth(int fontId, JagString text, int width) {
        int style = styleForFontId(fontId);
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE, 0);
        List<Line> lines = layout(parsed, Math.max(1, width), 0);
        int max = 0;
        for (Line line : lines) {
            max = Math.max(max, line.width);
        }
        return max;
    }

    public static synchronized void verifyReady() {
        KillerUiLog.start();
        ensureFonts();

        for (int style = 0; style < FONT_STYLE_COUNT; style++) {
            if (BASE_FONTS[style] == null || SCALED_FONTS[style] == null) {
                KillerUiLog.write("FATAL uiTextEngineVerificationFailed style=" + style);
                throw new IllegalStateException("Killer UI text engine failed startup verification for style " + style);
            }
        }

        KillerUiLog.once(
            "verified",
            "VERIFY uiTextEngine=READY allRequiredTtfLoaded=true supportedTtfCount=" + FONT_STYLE_COUNT + " fallback=false"
        );
    }

    public static synchronized void registerCacheFonts(Js5 provider) {
        if (provider == null) {
            KillerUiLog.write("FATAL registerCacheFonts provider=null");
            throw new IllegalStateException("Killer UI font registry requires the font JS5 provider");
        }

        CACHE_FONT_STYLES.clear();
        CACHE_FONT_NAMES.clear();

        // Core RuneScape UI fonts with exact RuneStar vector equivalents.
        registerCacheFont(provider, "p11_full", PLAIN_11);
        registerCacheFont(provider, "p12_full", PLAIN_12);
        registerCacheFont(provider, "b12_full", BOLD_12);
        registerCacheFont(provider, "q8_full", QUILL_8);
        registerCacheFont(provider, "quill_oblique_large", QUILL);
        registerCacheFont(provider, "quill_caps_large", QUILL_CAPS);
        registerCacheFont(provider, "lunar_alphabet", FAIRY);
        registerCacheFont(provider, "lunar_alphabet_lrg", FAIRY_LARGE);
        registerCacheFont(provider, "barbassault_font", BARBARIAN_ASSAULT);
        registerCacheFont(provider, "surok_font", SUROK);

        // Additional Jagex UI fonts present in the 634/667-era caches.
        // These are deliberate compatibility mappings into the replacement
        // TTF engine. None of them call Component.getFont() or the stock
        // bitmap renderer.
        registerCacheFont(provider, "friendslist_font", PLAIN_11);
        registerCacheFont(provider, "tutorial_font", PLAIN_12);
        registerCacheFont(provider, "welcome_font_small", PLAIN_11);
        registerCacheFont(provider, "welcome_font_large", BOLD_12);
        registerCacheFont(provider, "tutorial_font_big", BOLD_12);
        registerCacheFont(provider, "menu_font_small", PLAIN_11);
        registerCacheFont(provider, "tzhaar_numbers", BOLD_12);
        registerCacheFont(provider, "verdana_11pt_regular", PLAIN_11);
        registerCacheFont(provider, "verdana_11pt_bold", BOLD_12);
        registerCacheFont(provider, "verdana_13pt_regular", PLAIN_12);
        registerCacheFont(provider, "verdana_13pt_bold", BOLD_12);
        registerCacheFont(provider, "verdana_15pt_regular", BOLD_12);

        // The 667 cache contains additional font archives whose cache names
        // are not all known. Register their documented numeric IDs explicitly
        // so they stay inside KillerUiText instead of causing repeated CTDs.
        registerCompatibilityFontId(1591, PLAIN_12, "font_1591");
        registerCompatibilityFontId(2244, PLAIN_12, "font_2244");
        registerCompatibilityFontId(2710, PLAIN_12, "font_2710");
        registerCompatibilityFontId(3237, PLAIN_12, "font_3237");
        registerCompatibilityFontId(3794, BOLD_12, "font_3794");
        registerCompatibilityFontId(5419, PLAIN_12, "font_5419");
        registerCompatibilityFontId(5631, PLAIN_12, "font_5631");
        registerCompatibilityFontId(13120, PLAIN_12, "font_13120");
        registerCompatibilityFontId(13121, PLAIN_12, "font_13121");

        KillerUiLog.write(
            "FONT_REGISTRY supportedCacheFontIds=" + CACHE_FONT_STYLES.size()
                + " strictUnknownFontPolicy=true stockBitmapFallback=false"
        );
    }

    private static void registerCacheFont(Js5 provider, String cacheName, int style) {
        int id = provider.getGroupId(JagString.parse(cacheName));
        if (id < 0) {
            KillerUiLog.write("FONT_NOT_PRESENT cacheName=" + cacheName);
            return;
        }

        CACHE_FONT_STYLES.put(id, style);
        CACHE_FONT_NAMES.put(id, cacheName);
        KillerUiLog.write(
            "FONT_MAP fontId=" + id + " cacheName=" + cacheName
                + " style=" + style + " resource=" + resourceForStyle(style)
        );
    }

    private static void registerCompatibilityFontId(int id, int style, String cacheName) {
        if (CACHE_FONT_STYLES.containsKey(id)) {
            return;
        }
        CACHE_FONT_STYLES.put(id, style);
        CACHE_FONT_NAMES.put(id, cacheName);
        KillerUiLog.write(
            "FONT_COMPAT_MAP fontId=" + id + " cacheName=" + cacheName
                + " style=" + style + " resource=" + resourceForStyle(style)
        );
    }


    public static int styleForComponent(Component component) {
        if (component == null) {
            KillerUiLog.write("FATAL componentStyle=null component");
            throw new IllegalStateException("Killer UI text requested for a null component");
        }

        Integer registered = CACHE_FONT_STYLES.get(component.font);
        if (registered != null) {
            String mappedName = CACHE_FONT_NAMES.get(component.font);
            KillerUiLog.once(
                "font-use-" + component.font,
                "FONT_USED fontId=" + component.font
                    + " cacheName=" + (mappedName == null ? "UNKNOWN" : mappedName)
                    + " style=" + registered
                    + " resource=" + resourceForStyle(registered)
                    + " component=" + component.id
            );
            return registered;
        }

        // These remain as a safety check for normal startup ordering. They are
        // not a visual fallback: they route to the same replacement TTF engine.
        if (component.font == Sprites.p11FullId) {
            return PLAIN_11;
        }
        if (component.font == Sprites.p12FullId) {
            return PLAIN_12;
        }
        if (component.font == Sprites.b12FullId) {
            return BOLD_12;
        }

        String knownName = CACHE_FONT_NAMES.get(component.font);
        KillerUiLog.write(
            "FATAL unsupportedUiFont component=" + component.id + " fontId=" + component.font
                + " cacheName=" + (knownName == null ? "UNKNOWN" : knownName)
                + " p11Id=" + Sprites.p11FullId
                + " p12Id=" + Sprites.p12FullId
                + " b12Id=" + Sprites.b12FullId
        );
        throw new IllegalStateException(
            "Unsupported UI font id " + component.font
                + (knownName == null ? "" : " (" + knownName + ")")
                + " on component " + component.id
        );
    }

    /**
     * Notify the TTF engine that the original cache fonts are available for
     * metrics-only calibration. Their render methods remain hard-disabled.
     */
    public static synchronized void notifyLegacyMetricsReady() {
        synchronized (CACHE) {
            CACHE.clear();
        }
        KillerUiLog.once(
            "legacy-metrics-ready",
            "METRICS_ONLY legacyCacheFonts=READY"
                + " p11LineHeight=" + (Fonts.p11Full == null ? -1 : Fonts.p11Full.lineHeight)
                + " p12LineHeight=" + (Fonts.p12Full == null ? -1 : Fonts.p12Full.lineHeight)
                + " b12LineHeight=" + (Fonts.b12Full == null ? -1 : Fonts.b12Full.lineHeight)
                + " stockRendering=false"
        );
    }

    private static Font legacyMetricsForStyle(int style) {
        if (style == PLAIN_11) {
            return Fonts.p11Full;
        }
        if (style == PLAIN_12) {
            return Fonts.p12Full;
        }
        if (style == BOLD_12) {
            return Fonts.b12Full;
        }
        return null;
    }

    private static Font legacyMetricsForComponent(Component component) {
        Font metrics = component.getFont(Sprites.nameIcons);
        if (metrics == null) {
            KillerUiLog.write(
                "FATAL legacyMetricFontUnavailable component=" + component.id + " fontId=" + component.font
            );
            throw new IllegalStateException(
                "Could not load cache metrics for UI font " + component.font + " on component " + component.id
            );
        }

        KillerUiLog.once(
            "metrics-font-" + component.font,
            "METRICS_ONLY fontId=" + component.font
                + " cacheName=" + CACHE_FONT_NAMES.get(component.font)
                + " lineHeight=" + metrics.lineHeight
                + " stockRendering=false"
        );
        return metrics;
    }

    private static double metricScaleForStyle(int style) {
        int nativeSize = nativeSizeForStyle(style);
        return (double) KillerUi.fontTarget(nativeSize) / (double) nativeSize;
    }

    private static int scaleLegacyMetric(int value, int style) {
        if (value == 0) {
            return 0;
        }
        return Math.max(1, (int) Math.round((double) value * metricScaleForStyle(style)));
    }

    public static int lineHeight(int style) {
        return lineHeight(style, legacyMetricsForStyle(style));
    }

    private static int lineHeight(int style, Font legacyMetrics) {
        if (legacyMetrics != null && legacyMetrics.lineHeight > 0) {
            return scaleLegacyMetric(legacyMetrics.lineHeight, style);
        }

        java.awt.Font font = getFont(style);
        LineMetrics metrics = font.getLineMetrics("Ag", FRC);
        return Math.max(1, (int) Math.ceil(metrics.getHeight()));
    }

    public static int ascent(int style) {
        return ascent(style, legacyMetricsForStyle(style));
    }

    private static int ascent(int style, Font legacyMetrics) {
        java.awt.Font font = getFont(style);
        LineMetrics metrics = font.getLineMetrics("Ag", FRC);
        int targetHeight = lineHeight(style, legacyMetrics);
        double rawHeight = metrics.getHeight();
        if (rawHeight <= 0.0D) {
            return Math.max(1, targetHeight);
        }

        // Keep the TTF's ascent/descent ratio, but force its logical line box
        // to the exact cache-font line height at scale 1.0.
        return Math.max(1, Math.min(
            targetHeight,
            (int) Math.round((double) targetHeight * (double) metrics.getAscent() / rawHeight)
        ));
    }

    public static int measureWidth(JagString text, int style) {
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE, 0);
        List<Line> lines = layout(parsed, Integer.MAX_VALUE / 4, 0);
        int max = 0;
        for (Line line : lines) {
            if (line.width > max) {
                max = line.width;
            }
        }
        return max;
    }

    public static int measureParagraphHeight(JagString text, int style, int width, int vpadding) {
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE, 0);
        List<Line> lines = layout(parsed, Math.max(1, width), KillerUi.px(vpadding));
        int height = 0;
        for (Line line : lines) {
            height += line.height;
        }
        if (lines.size() > 1) {
            height += (lines.size() - 1) * KillerUi.px(vpadding);
        }
        return height;
    }

    public static void drawComponent(
        JagString text,
        Component component,
        int x,
        int y,
        int color,
        int shadow
    ) {
        if (component == null || text == null || component.width <= 0 || component.height <= 0) {
            return;
        }

        KillerUiLog.once("component-text", "ROUTE componentText=KillerUiText");
        int style = styleForComponent(component);
        Font legacyMetrics = legacyMetricsForComponent(component);

        ParsedText parsed = parse(
            text,
            style,
            color,
            shadow,
            256,
            EFFECT_NONE,
            0,
            legacyMetrics
        );

        int legacyLineHeight = lineHeight(style, legacyMetrics);
        int topPadding = scaleLegacyMetric(legacyMetrics.killerParagraphTopPadding(), style);
        int bottomPadding = scaleLegacyMetric(legacyMetrics.killerParagraphBottomPadding(), style);
        int lineSpacing = component.vpadding == 0 ? legacyLineHeight : component.vpadding;

        int wrapWidth = component.width;
        if (component.height < topPadding + bottomPadding + lineSpacing
            && component.height < lineSpacing + lineSpacing) {
            // Matches the stock paragraph renderer: a short single-line
            // component does not wrap simply because TTF bearings differ.
            wrapWidth = Integer.MAX_VALUE / 4;
        }

        List<Line> lines = layout(parsed, Math.max(1, wrapWidth), 0);
        int firstBaseline;

        if (component.valign == 0) {
            firstBaseline = topPadding;
        } else if (component.valign == 1) {
            firstBaseline = topPadding
                + (component.height - topPadding - bottomPadding
                    - (lines.size() - 1) * lineSpacing) / 2;
        } else if (component.valign == 2) {
            firstBaseline = component.height - bottomPadding
                - (lines.size() - 1) * lineSpacing;
        } else {
            int distributed = (
                component.height - topPadding - bottomPadding
                    - (lines.size() - 1) * lineSpacing
            ) / (lines.size() + 1);
            if (distributed < 0) {
                distributed = 0;
            }
            firstBaseline = topPadding + distributed;
            lineSpacing += distributed;
        }

        boolean animated = parsed.effect != EFFECT_NONE;
        String key = null;
        RenderedText rendered = null;
        if (!animated) {
            key = cacheKey(
                text,
                style,
                component.font,
                component.width,
                component.height,
                color,
                shadow,
                256,
                component.halign,
                component.valign,
                component.vpadding
            );
            synchronized (CACHE) {
                rendered = CACHE.get(key);
            }
        }

        if (rendered == null) {
            rendered = rasterizeBaselines(
                parsed,
                lines,
                component.width,
                component.height,
                firstBaseline,
                component.halign,
                lineSpacing
            );
            if (!animated && key != null) {
                synchronized (CACHE) {
                    CACHE.put(key, rendered);
                }
            }
        }

        rendered.render(x, y);
    }

    public static void drawLeft(JagString text, int style, int x, int baselineY, int color, int shadow) {
        KillerUiLog.once("left-text", "ROUTE leftAlignedUiText=KillerUiText");
        int h = lineHeight(style) + KillerUi.px(4);
        draw(text, style, x, baselineY - ascent(style), Math.max(1, measureWidth(text, style) + KillerUi.px(4)),
            h, color, shadow, 256, 0, 0, 0, EFFECT_NONE);
    }

    public static void drawCenter(JagString text, int style, int centerX, int baselineY, int color, int shadow) {
        KillerUiLog.once("center-text", "ROUTE centeredUiText=KillerUiText");
        int width = measureWidth(text, style);
        int h = lineHeight(style) + KillerUi.px(4);
        draw(text, style, centerX - width / 2, baselineY - ascent(style), Math.max(1, width + KillerUi.px(4)),
            h, color, shadow, 256, 1, 0, 0, EFFECT_NONE);
    }

    public static void drawRight(JagString text, int style, int rightX, int baselineY, int color, int shadow) {
        KillerUiLog.once("right-text", "ROUTE rightAlignedUiText=KillerUiText");
        int width = measureWidth(text, style);
        int h = lineHeight(style) + KillerUi.px(4);
        draw(text, style, rightX - width, baselineY - ascent(style), Math.max(1, width + KillerUi.px(4)),
            h, color, shadow, 256, 2, 0, 0, EFFECT_NONE);
    }

    public static void drawWave(JagString text, int style, int centerX, int baselineY, int color, int shadow) {
        drawBaselineEffect(text, style, centerX, baselineY, color, shadow, EFFECT_WAVE, 0);
    }

    public static void drawWave2(JagString text, int style, int centerX, int baselineY, int color, int shadow) {
        drawBaselineEffect(text, style, centerX, baselineY, color, shadow, EFFECT_WAVE2, 0);
    }

    public static void drawShake(JagString text, int style, int centerX, int baselineY, int color, int shadow, int amplitude) {
        drawBaselineEffect(text, style, centerX, baselineY, color, shadow, EFFECT_SHAKE, amplitude);
    }

    private static void drawBaselineEffect(
        JagString text,
        int style,
        int centerX,
        int baselineY,
        int color,
        int shadow,
        int effect,
        int effectParam
    ) {
        int pad = Math.max(KillerUi.px(8), 8);
        int textWidth = Math.max(1, measureWidth(text, style));
        int h = lineHeight(style) + pad * 2;
        int w = textWidth + pad * 2;
        draw(
            text,
            style,
            centerX - w / 2,
            baselineY - ascent(style) - pad,
            w,
            h,
            color,
            shadow,
            256,
            1,
            0,
            0,
            effect,
            effectParam
        );
    }

    public static void drawSoftwareLeft(
        JagString text,
        int style,
        int x,
        int baselineY,
        int color,
        int shadow
    ) {
        if (text == null) {
            return;
        }

        ParsedText parsed = parse(text, style, color, shadow, 256, EFFECT_NONE, 0);
        int width = Math.max(1, measureWidth(text, style) + KillerUi.px(4));
        int height = lineHeight(style) + KillerUi.px(4);
        List<Line> lines = layout(parsed, width, 0);
        RenderedText rendered = rasterize(parsed, lines, width, height, 0, 0);
        rendered.renderSoftware(x, baselineY - ascent(style));
    }

    public static int splitExplicitLines(JagString text, JagString[] output) {
        if (text == null || output == null || output.length == 0) {
            return 0;
        }

        String raw = toJavaString(text);
        String[] parts = raw.split("(?i)<br>", -1);
        int count = Math.min(parts.length, output.length);
        for (int i = 0; i < count; i++) {
            output[i] = JagString.of(parts[i]);
        }
        return count;
    }

    public static int measureWidthSized(JagString text, int style, int nativeSize) {
        java.awt.Font font = getSizedFont(style, nativeSize);
        String plain = plainText(text);
        GlyphVector gv = font.createGlyphVector(FRC, plain);
        return Math.max(0, (int) Math.ceil(gv.getLogicalBounds().getBounds2D().getWidth()));
    }

    public static int lineHeightSized(int style, int nativeSize) {
        java.awt.Font font = getSizedFont(style, nativeSize);
        return Math.max(1, (int) Math.ceil(font.getLineMetrics("Ag", FRC).getHeight()));
    }

    public static int ascentSized(int style, int nativeSize) {
        java.awt.Font font = getSizedFont(style, nativeSize);
        return Math.max(1, (int) Math.ceil(font.getLineMetrics("Ag", FRC).getAscent()));
    }

    public static void drawCenterSized(
        JagString text,
        int style,
        int nativeSize,
        int centerX,
        int baselineY,
        int color,
        int shadow
    ) {
        if (text == null) {
            return;
        }

        java.awt.Font font = getSizedFont(style, nativeSize);
        String plain = plainText(text);
        int pad = Math.max(2, KillerUi.px(2));
        int width = Math.max(1, measureWidthSized(text, style, nativeSize) + pad * 2);
        int height = lineHeightSized(style, nativeSize) + pad * 2;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            GlyphVector gv = font.createGlyphVector(FRC, plain);
            int x = pad;
            int y = pad + ascentSized(style, nativeSize);
            if (shadow >= 0) {
                g.setColor(new Color(shadow & 0xFFFFFF));
                g.drawGlyphVector(gv, x + KillerUi.px(1), y + KillerUi.px(1));
            }
            g.setColor(new Color(color & 0xFFFFFF));
            g.drawGlyphVector(gv, x, y);
        } finally {
            g.dispose();
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        RenderedText rendered = new RenderedText(width, height, pixels, new ArrayList<IconPlacement>());
        rendered.render(centerX - width / 2, baselineY - ascentSized(style, nativeSize) - pad);
    }

    public static void drawCenterSizedSoftware(
        JagString text,
        int style,
        int nativeSize,
        int centerX,
        int baselineY,
        int color,
        int shadow
    ) {
        if (text == null) {
            return;
        }

        java.awt.Font font = getSizedFont(style, nativeSize);
        String plain = plainText(text);
        int pad = Math.max(2, KillerUi.px(2));
        int width = Math.max(1, measureWidthSized(text, style, nativeSize) + pad * 2);
        int height = lineHeightSized(style, nativeSize) + pad * 2;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            GlyphVector gv = font.createGlyphVector(FRC, plain);
            int x = pad;
            int y = pad + ascentSized(style, nativeSize);
            if (shadow >= 0) {
                g.setColor(new Color(shadow & 0xFFFFFF));
                g.drawGlyphVector(gv, x + KillerUi.px(1), y + KillerUi.px(1));
            }
            g.setColor(new Color(color & 0xFFFFFF));
            g.drawGlyphVector(gv, x, y);
        } finally {
            g.dispose();
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        RenderedText rendered = new RenderedText(width, height, pixels, new ArrayList<IconPlacement>());
        rendered.renderSoftware(centerX - width / 2, baselineY - ascentSized(style, nativeSize) - pad);
    }

    private static java.awt.Font getSizedFont(int style, int nativeSize) {
        ensureFonts();
        if (style < 0 || style >= BASE_FONTS.length || BASE_FONTS[style] == null) {
            KillerUiLog.write("FATAL invalidSizedFontStyle=" + style);
            throw new IllegalArgumentException("Invalid Killer text style " + style);
        }
        return BASE_FONTS[style].deriveFont((float) KillerUi.fontTarget(nativeSize));
    }

    private static String plainText(JagString text) {
        if (text == null) {
            return "";
        }
        String raw = toJavaString(text);
        raw = raw.replace("<lt>", "<").replace("<gt>", ">")
            .replace("<nbsp>", "\u00A0").replace("<shy>", "\u00AD")
            .replace("<times>", "\u00D7").replace("<euro>", "\u20AC")
            .replace("<copy>", "\u00A9").replace("<reg>", "\u00AE");
        return raw.replaceAll("<[^>]*>", "");
    }

    public static void drawWavy(
        JagString text,
        int style,
        int x,
        int y,
        int width,
        int height,
        int color,
        int shadow,
        int halign,
        int valign
    ) {
        KillerUiLog.once("wavy-text", "ROUTE animatedUiText=KillerUiText");
        draw(text, style, x, y, width, height, color, shadow, 256, halign, valign, 0, EFFECT_WAVE);
    }

    public static void draw(
        JagString text,
        int style,
        int x,
        int y,
        int width,
        int height,
        int color,
        int shadow,
        int alpha,
        int halign,
        int valign,
        int vpadding,
        int effect
    ) {
        draw(text, style, x, y, width, height, color, shadow, alpha, halign, valign, vpadding, effect, 0);
    }

    public static void draw(
        JagString text,
        int style,
        int x,
        int y,
        int width,
        int height,
        int color,
        int shadow,
        int alpha,
        int halign,
        int valign,
        int vpadding,
        int effect,
        int effectParam
    ) {
        drawWithMetrics(
            text,
            style,
            x,
            y,
            width,
            height,
            color,
            shadow,
            alpha,
            halign,
            valign,
            vpadding,
            effect,
            effectParam,
            legacyMetricsForStyle(style),
            -1000 - style
        );
    }

    private static void drawWithMetrics(
        JagString text,
        int style,
        int x,
        int y,
        int width,
        int height,
        int color,
        int shadow,
        int alpha,
        int halign,
        int valign,
        int vpadding,
        int effect,
        int effectParam,
        Font legacyMetrics,
        int metricKey
    ) {
        if (text == null || width <= 0 || height <= 0) {
            return;
        }

        KillerUiLog.start();
        KillerUiLog.once(
            GlRenderer.enabled ? "renderer-gl" : "renderer-software",
            "COMPOSITOR=" + (GlRenderer.enabled ? "OPENGL" : "SOFTWARE")
        );

        ParsedText parsed = parse(text, style, color, shadow, alpha, effect, effectParam, legacyMetrics);
        int padding = KillerUi.px(vpadding);
        List<Line> lines = layout(parsed, width, padding);

        int contentHeight = 0;
        for (Line line : lines) {
            contentHeight += line.height;
        }
        if (lines.size() > 1) {
            contentHeight += (lines.size() - 1) * padding;
        }

        int startY = 0;
        if (valign == 1) {
            startY = Math.max(0, (height - contentHeight) / 2);
        } else if (valign == 2) {
            startY = Math.max(0, height - contentHeight);
        }

        boolean animated = parsed.effect != EFFECT_NONE;
        String key = null;
        RenderedText rendered;

        if (!animated) {
            key = cacheKey(text, style, metricKey, width, height, color, shadow, alpha, halign, valign, vpadding);
            synchronized (CACHE) {
                rendered = CACHE.get(key);
            }
        } else {
            rendered = null;
        }

        if (rendered == null) {
            rendered = rasterize(parsed, lines, width, height, startY, halign);
            if (!animated && key != null) {
                synchronized (CACHE) {
                    CACHE.put(key, rendered);
                }
            }
        }

        rendered.render(x, y);
    }

    private static RenderedText rasterizeBaselines(
        ParsedText parsed,
        List<Line> lines,
        int width,
        int height,
        int firstBaseline,
        int halign,
        int lineSpacing
    ) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        List<IconPlacement> icons = new ArrayList<IconPlacement>();

        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int baseline = firstBaseline;
            int charIndex = 0;

            for (Line line : lines) {
                int lineX = 0;
                if (halign == 1) {
                    lineX = Math.max(0, (width - line.width) / 2);
                } else if (halign == 2) {
                    lineX = Math.max(0, width - line.width);
                }

                charIndex = drawLine(g, icons, parsed, line, lineX, baseline, charIndex);
                baseline += lineSpacing;
            }
        } finally {
            g.dispose();
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        return new RenderedText(width, height, pixels, icons);
    }

    private static RenderedText rasterize(
        ParsedText parsed,
        List<Line> lines,
        int width,
        int height,
        int startY,
        int halign
    ) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        List<IconPlacement> icons = new ArrayList<IconPlacement>();

        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int y = startY;
            int charIndex = 0;

            for (Line line : lines) {
                int lineX = 0;
                if (halign == 1) {
                    lineX = Math.max(0, (width - line.width) / 2);
                } else if (halign == 2) {
                    lineX = Math.max(0, width - line.width);
                }

                int baseline = y + line.ascent;
                charIndex = drawLine(g, icons, parsed, line, lineX, baseline, charIndex);
                y += line.height + parsed.linePadding;
            }
        } finally {
            g.dispose();
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        return new RenderedText(width, height, pixels, icons);
    }

    private static int drawLine(
        Graphics2D g,
        List<IconPlacement> icons,
        ParsedText parsed,
        Line line,
        int lineX,
        int baseline,
        int charIndex
    ) {
        int x = lineX;

        for (Token token : line.tokens) {
            if (token.kind == Token.ICON) {
                if (token.iconIndex >= 0 && Sprites.nameIcons != null
                    && token.iconIndex < Sprites.nameIcons.length
                    && Sprites.nameIcons[token.iconIndex] != null) {
                    IndexedSprite icon = Sprites.nameIcons[token.iconIndex];
                    icons.add(new IconPlacement(
                        token.iconIndex,
                        x,
                        baseline - icon.innerHeight,
                        token.style.alpha
                    ));
                }
                x += token.advance;
                continue;
            }

            if (token.kind != Token.CHARACTER) {
                continue;
            }

            char ch = token.ch;
            java.awt.Font font = getFont(token.style.fontStyle);
            int waveY = effectYOffset(parsed.effect, parsed.effectParam, charIndex);
            int waveX = effectXOffset(parsed.effect, parsed.effectParam, charIndex);
            int drawX = x + waveX;
            int drawY = baseline + waveY;

            int rgb = token.style.color;
            if (parsed.effect == EFFECT_RAINBOW) {
                rgb = rainbowColor(charIndex);
            }

            GlyphVector gv = font.createGlyphVector(FRC, new char[]{ch});

            if (token.style.shadow >= 0) {
                setComposite(g, token.style.alpha);
                g.setColor(new Color(token.style.shadow & 0xFFFFFF));
                g.drawGlyphVector(gv, drawX + KillerUi.px(1), drawY + KillerUi.px(1));
            }

            setComposite(g, token.style.alpha);
            g.setColor(new Color(rgb & 0xFFFFFF));
            g.drawGlyphVector(gv, drawX, drawY);

            if (token.style.underline >= 0) {
                g.setColor(new Color(token.style.underline & 0xFFFFFF));
                g.drawLine(drawX, drawY + KillerUi.px(1), drawX + token.advance, drawY + KillerUi.px(1));
            }

            if (token.style.strike >= 0) {
                g.setColor(new Color(token.style.strike & 0xFFFFFF));
                int strikeY = drawY - Math.max(1, line.ascent / 3);
                g.drawLine(drawX, strikeY, drawX + token.advance, strikeY);
            }

            x += token.advance;
            charIndex++;
        }

        return charIndex;
    }

    private static void setComposite(Graphics2D g, int alpha) {
        int a = Math.max(0, Math.min(256, alpha));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) a / 256.0F));
    }

    private static int effectYOffset(int effect, int effectParam, int index) {
        double tick = (double) client.loop;
        double scale = KillerUi.scale();
        if (effect == EFFECT_WAVE) {
            return (int) Math.round(Math.sin((double) index / 2.0D + tick / 5.0D) * 5.0D * scale);
        }
        if (effect == EFFECT_WAVE2) {
            return (int) Math.round(Math.sin((double) index / 3.0D + tick / 5.0D) * 5.0D * scale);
        }
        if (effect == EFFECT_SHAKE) {
            double wave = 7.0D - (double) effectParam / 8.0D;
            if (wave < 0.0D) {
                wave = 0.0D;
            }
            return (int) Math.round(Math.sin((double) index / 1.5D + tick) * wave * scale);
        }
        return 0;
    }

    private static int effectXOffset(int effect, int effectParam, int index) {
        if (effect == EFFECT_WAVE2) {
            return (int) Math.round(
                Math.sin((double) index / 5.0D + (double) client.loop / 5.0D) * 5.0D * KillerUi.scale()
            );
        }
        return 0;
    }

    private static int rainbowColor(int index) {
        float hue = ((float) ((client.loop * 3 + index * 18) % 360)) / 360.0F;
        return Color.HSBtoRGB(hue, 0.85F, 1.0F) & 0xFFFFFF;
    }

    private static List<Line> layout(ParsedText parsed, int maxWidth, int padding) {
        List<Line> lines = new ArrayList<Line>();
        Line current = new Line();

        List<Token> word = new ArrayList<Token>();
        int wordWidth = 0;

        for (Token token : parsed.tokens) {
            if (token.kind == Token.BREAK) {
                current = flushWord(lines, current, word, wordWidth, maxWidth, parsed.fontStyle, parsed.legacyMetrics);
                word.clear();
                wordWidth = 0;

                current.finish(parsed.fontStyle, parsed.legacyMetrics);
                lines.add(current);
                current = new Line();
                continue;
            }

            if (token.kind == Token.CHARACTER && token.ch == ' ') {
                current = flushWord(lines, current, word, wordWidth, maxWidth, parsed.fontStyle);
                word.clear();
                wordWidth = 0;

                if (!current.tokens.isEmpty()) {
                    if (current.width + token.advance <= maxWidth) {
                        current.add(token, parsed.legacyMetrics);
                    } else {
                        current.finish(parsed.fontStyle);
                        lines.add(current);
                        current = new Line();
                    }
                }
                continue;
            }

            word.add(token);
            wordWidth += token.advance;
        }

        current = flushWord(lines, current, word, wordWidth, maxWidth, parsed.fontStyle);

        if (!current.tokens.isEmpty() || lines.isEmpty()) {
            current.finish(parsed.fontStyle);
            lines.add(current);
        }

        parsed.linePadding = padding;
        return lines;
    }

    private static Line flushWord(
        List<Line> lines,
        Line current,
        List<Token> word,
        int wordWidth,
        int maxWidth,
        int fallbackStyle,
        Font legacyMetrics
    ) {
        if (word.isEmpty()) {
            return current;
        }

        if (!current.tokens.isEmpty() && current.width + wordWidth > maxWidth) {
            current.finish(fallbackStyle, legacyMetrics);
            lines.add(current);
            current = new Line();
        }

        for (Token token : word) {
            if (current.width + token.advance > maxWidth && !current.tokens.isEmpty()) {
                current.finish(fallbackStyle);
                lines.add(current);
                current = new Line();
            }
            current.add(token, legacyMetrics);
        }

        return current;
    }

    private static ParsedText parse(
        JagString input,
        int fontStyle,
        int baseColor,
        int baseShadow,
        int baseAlpha,
        int requestedEffect,
        int requestedEffectParam
    ) {
        return parse(
            input,
            fontStyle,
            baseColor,
            baseShadow,
            baseAlpha,
            requestedEffect,
            requestedEffectParam,
            legacyMetricsForStyle(fontStyle)
        );
    }

    private static ParsedText parse(
        JagString input,
        int fontStyle,
        int baseColor,
        int baseShadow,
        int baseAlpha,
        int requestedEffect,
        int requestedEffectParam,
        Font legacyMetrics
    ) {
        String text = toJavaString(input);
        int effect = requestedEffect;

        PrefixResult prefix = parsePrefix(text, baseColor, effect);
        text = prefix.text;
        baseColor = prefix.color;
        effect = prefix.effect;

        StyleState base = new StyleState(fontStyle, baseColor, baseShadow, baseAlpha);
        StyleState state = base.copy();
        List<Token> tokens = new ArrayList<Token>();

        StringBuilder plain = new StringBuilder();
        int i = 0;

        while (i < text.length()) {
            char ch = text.charAt(i);
            if (ch != '<') {
                plain.append(ch);
                i++;
                continue;
            }

            int close = text.indexOf('>', i + 1);
            if (close < 0) {
                plain.append(ch);
                i++;
                continue;
            }

            flushPlain(tokens, plain, state);
            String tag = text.substring(i + 1, close);
            String lower = tag.toLowerCase();

            if ("lt".equals(lower)) {
                tokens.add(Token.character('<', state.copy()));
            } else if ("gt".equals(lower)) {
                tokens.add(Token.character('>', state.copy()));
            } else if ("nbsp".equals(lower)) {
                tokens.add(Token.character('\u00A0', state.copy()));
            } else if ("shy".equals(lower)) {
                tokens.add(Token.character('\u00AD', state.copy()));
            } else if ("times".equals(lower)) {
                tokens.add(Token.character('\u00D7', state.copy()));
            } else if ("euro".equals(lower)) {
                tokens.add(Token.character('\u20AC', state.copy()));
            } else if ("copy".equals(lower)) {
                tokens.add(Token.character('\u00A9', state.copy()));
            } else if ("reg".equals(lower)) {
                tokens.add(Token.character('\u00AE', state.copy()));
            } else if ("br".equals(lower)) {
                tokens.add(Token.lineBreak());
                state = base.copy();
            } else if (lower.startsWith("img=")) {
                try {
                    int index = Integer.parseInt(lower.substring(4));
                    if (Sprites.nameIcons != null && index >= 0 && index < Sprites.nameIcons.length
                        && Sprites.nameIcons[index] != null) {
                        tokens.add(Token.icon(index, Sprites.nameIcons[index].innerWidth, state.copy()));
                    }
                } catch (NumberFormatException ignored) {
                }
            } else if (lower.startsWith("col=")) {
                state.color = parseHex(lower.substring(4), state.color);
            } else if ("/col".equals(lower)) {
                state.color = base.color;
            } else if (lower.startsWith("trans=")) {
                state.alpha = clamp(parseInt(lower.substring(6), state.alpha), 0, 256);
            } else if ("/trans".equals(lower)) {
                state.alpha = base.alpha;
            } else if ("str".equals(lower)) {
                state.strike = 0x800000;
            } else if (lower.startsWith("str=")) {
                state.strike = parseHex(lower.substring(4), 0x800000);
            } else if ("/str".equals(lower)) {
                state.strike = -1;
            } else if ("u".equals(lower)) {
                state.underline = 0;
            } else if (lower.startsWith("u=")) {
                state.underline = parseHex(lower.substring(2), 0);
            } else if ("/u".equals(lower)) {
                state.underline = -1;
            } else if ("shad".equals(lower)) {
                state.shadow = 0;
            } else if (lower.startsWith("shad=")) {
                state.shadow = parseHex(lower.substring(5), 0);
            } else if ("/shad".equals(lower)) {
                state.shadow = base.shadow;
            }
            // Unknown tags intentionally disappear, matching the old UI parser's
            // treatment of unsupported markup rather than printing raw markup.

            i = close + 1;
        }

        flushPlain(tokens, plain, state);

        int previousChar = 0;
        for (Token token : tokens) {
            if (token.kind == Token.CHARACTER) {
                token.advance = charAdvance(token.ch, token.style.fontStyle, previousChar, legacyMetrics);
                previousChar = token.ch;
            } else {
                previousChar = 0;
            }
        }

        return new ParsedText(tokens, fontStyle, effect, requestedEffectParam, legacyMetrics);
    }

    private static PrefixResult parsePrefix(String text, int color, int effect) {
        String lower = text.toLowerCase();

        String[] colorNames = {"yellow:", "red:", "green:", "cyan:", "purple:", "white:"};
        int[] colors = {0xFFFF00, 0xFF0000, 0x00FF00, 0x00FFFF, 0xFF00FF, 0xFFFFFF};

        for (int i = 0; i < colorNames.length; i++) {
            if (lower.startsWith(colorNames[i])) {
                color = colors[i];
                text = text.substring(colorNames[i].length());
                lower = text.toLowerCase();
                break;
            }
        }

        if (lower.startsWith("wave2:")) {
            effect = EFFECT_WAVE2;
            text = text.substring(6);
        } else if (lower.startsWith("wave:")) {
            effect = EFFECT_WAVE;
            text = text.substring(5);
        } else if (lower.startsWith("shake:")) {
            effect = EFFECT_SHAKE;
            text = text.substring(6);
        } else if (lower.startsWith("rainbow:")) {
            effect = EFFECT_RAINBOW;
            text = text.substring(8);
        } else if (lower.startsWith("flash1:") || lower.startsWith("flash2:") || lower.startsWith("flash3:")
            || lower.startsWith("glow1:") || lower.startsWith("glow2:") || lower.startsWith("glow3:")) {
            int split = text.indexOf(':');
            effect = EFFECT_RAINBOW;
            text = split >= 0 ? text.substring(split + 1) : text;
        }

        return new PrefixResult(text, color, effect);
    }

    private static void flushPlain(List<Token> tokens, StringBuilder plain, StyleState state) {
        if (plain.length() == 0) {
            return;
        }
        for (int i = 0; i < plain.length(); i++) {
            tokens.add(Token.character(plain.charAt(i), state.copy()));
        }
        plain.setLength(0);
    }

    private static String toJavaString(JagString text) {
        if (text == null || text.length == 0) {
            return "";
        }
        return new String(text.chars, 0, text.length, WINDOWS_1252);
    }

    private static int charAdvance(char ch, int style, int previousChar, Font legacyMetrics) {
        if (legacyMetrics != null) {
            int advance = legacyMetrics.killerGlyphAdvance(ch);
            if (previousChar != 0) {
                advance += legacyMetrics.killerKerning(previousChar, ch);
            }
            return Math.max(0, (int) Math.round((double) advance * metricScaleForStyle(style)));
        }

        java.awt.Font font = getFont(style);
        GlyphVector gv = font.createGlyphVector(FRC, new char[]{ch});
        return Math.max(0, (int) Math.round(gv.getGlyphMetrics(0).getAdvanceX()));
    }

    private static synchronized java.awt.Font getFont(int style) {
        ensureFonts();
        if (style < 0 || style >= SCALED_FONTS.length) {
            KillerUiLog.write("FATAL invalidUiFontStyle=" + style);
            throw new IllegalArgumentException("Invalid Killer UI font style " + style);
        }
        if (SCALED_FONTS[style] == null) {
            KillerUiLog.write("FATAL uiFontNotLoaded style=" + style);
            throw new IllegalStateException("Killer UI font style " + style + " was not loaded");
        }
        return SCALED_FONTS[style];
    }

    private static void ensureFonts() {
        double currentScale = KillerUi.scale();
        if (loadedScale == currentScale && SCALED_FONTS[0] != null) {
            return;
        }

        loadBaseFont(PLAIN_11, PLAIN_11_RESOURCE);
        loadBaseFont(PLAIN_12, PLAIN_12_RESOURCE);
        loadBaseFont(BOLD_12, BOLD_12_RESOURCE);
        loadBaseFont(QUILL_8, QUILL_8_RESOURCE);
        loadBaseFont(QUILL, QUILL_RESOURCE);
        loadBaseFont(QUILL_CAPS, QUILL_CAPS_RESOURCE);
        loadBaseFont(FAIRY, FAIRY_RESOURCE);
        loadBaseFont(FAIRY_LARGE, FAIRY_LARGE_RESOURCE);
        loadBaseFont(BARBARIAN_ASSAULT, BARBARIAN_ASSAULT_RESOURCE);
        loadBaseFont(SUROK, SUROK_RESOURCE);

        for (int style = 0; style < FONT_STYLE_COUNT; style++) {
            SCALED_FONTS[style] = BASE_FONTS[style].deriveFont((float) KillerUi.fontTarget(nativeSizeForStyle(style)));
        }

        KillerUiLog.once(
            "font-targets-" + currentScale,
            "TTF_TARGETS p11=" + KillerUi.fontTarget(11)
                + " p12=" + KillerUi.fontTarget(12)
                + " b12=" + KillerUi.fontTarget(12)
                + " q8=" + KillerUi.fontTarget(12)
                + " quill=" + KillerUi.fontTarget(24)
                + " quillCaps=" + KillerUi.fontTarget(48)
                + " fairy=" + KillerUi.fontTarget(24)
                + " fairyLarge=" + KillerUi.fontTarget(48)
                + " barbAssault=" + KillerUi.fontTarget(24)
                + " surok=" + KillerUi.fontTarget(12)
        );
        loadedScale = currentScale;
        synchronized (CACHE) {
            CACHE.clear();
        }
    }

    private static int nativeSizeForStyle(int style) {
        switch (style) {
            case PLAIN_11:
                return 11;
            case PLAIN_12:
            case BOLD_12:
            case QUILL_8:
            case SUROK:
                return 12;
            case QUILL:
            case FAIRY:
            case BARBARIAN_ASSAULT:
                return 24;
            case QUILL_CAPS:
            case FAIRY_LARGE:
                return 48;
            default:
                KillerUiLog.write("FATAL unknownNativeFontSize style=" + style);
                throw new IllegalArgumentException("Unknown Killer UI font style " + style);
        }
    }

    private static String resourceForStyle(int style) {
        switch (style) {
            case PLAIN_11:
                return PLAIN_11_RESOURCE;
            case PLAIN_12:
                return PLAIN_12_RESOURCE;
            case BOLD_12:
                return BOLD_12_RESOURCE;
            case QUILL_8:
                return QUILL_8_RESOURCE;
            case QUILL:
                return QUILL_RESOURCE;
            case QUILL_CAPS:
                return QUILL_CAPS_RESOURCE;
            case FAIRY:
                return FAIRY_RESOURCE;
            case FAIRY_LARGE:
                return FAIRY_LARGE_RESOURCE;
            case BARBARIAN_ASSAULT:
                return BARBARIAN_ASSAULT_RESOURCE;
            case SUROK:
                return SUROK_RESOURCE;
            default:
                return "UNKNOWN";
        }
    }

    private static void loadBaseFont(int style, String resource) {
        if (BASE_FONTS[style] != null) {
            return;
        }

        try (InputStream in = KillerUiText.class.getResourceAsStream(resource)) {
            if (in == null) {
                KillerUiLog.write("FATAL missingTtf style=" + style + " resource=" + resource);
                throw new IllegalStateException("Missing required Killer UI TTF resource: " + resource);
            }

            BASE_FONTS[style] = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, in);
            KillerUiLog.once(
                "font-" + style,
                "TTF_LOADED style=" + style + " resource=" + resource
                    + " family=" + BASE_FONTS[style].getFamily()
                    + " name=" + BASE_FONTS[style].getFontName()
            );
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            KillerUiLog.write(
                "FATAL ttfLoadFailure style=" + style + " resource=" + resource
                    + " error=" + ex.getClass().getName() + ": " + ex.getMessage()
            );
            throw new IllegalStateException("Could not load required Killer UI TTF: " + resource, ex);
        }
    }

    private static int parseHex(String text, int fallback) {
        try {
            return Integer.parseInt(text, 16) & 0xFFFFFF;
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    private static int parseInt(String text, int fallback) {
        try {
            return Integer.parseInt(text);
        } catch (RuntimeException ignored) {
            return fallback;
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static String cacheKey(
        JagString text,
        int style,
        int metricKey,
        int width,
        int height,
        int color,
        int shadow,
        int alpha,
        int halign,
        int valign,
        int vpadding
    ) {
        return KillerUi.scale() + "|" + style + "|" + metricKey + "|" + width + "|" + height + "|" + color + "|" + shadow
            + "|" + alpha + "|" + halign + "|" + valign + "|" + vpadding + "|" + toJavaString(text);
    }

    private static final class ParsedText {
        final List<Token> tokens;
        final int fontStyle;
        final int effect;
        final int effectParam;
        final Font legacyMetrics;
        int linePadding;

        ParsedText(List<Token> tokens, int fontStyle, int effect, int effectParam, Font legacyMetrics) {
            this.tokens = tokens;
            this.fontStyle = fontStyle;
            this.effect = effect;
            this.effectParam = effectParam;
            this.legacyMetrics = legacyMetrics;
        }
    }

    private static final class PrefixResult {
        final String text;
        final int color;
        final int effect;

        PrefixResult(String text, int color, int effect) {
            this.text = text;
            this.color = color;
            this.effect = effect;
        }
    }

    private static final class StyleState {
        final int fontStyle;
        int color;
        int shadow;
        int alpha;
        int underline = -1;
        int strike = -1;

        StyleState(int fontStyle, int color, int shadow, int alpha) {
            this.fontStyle = fontStyle;
            this.color = color;
            this.shadow = shadow;
            this.alpha = alpha;
        }

        StyleState copy() {
            StyleState copy = new StyleState(this.fontStyle, this.color, this.shadow, this.alpha);
            copy.underline = this.underline;
            copy.strike = this.strike;
            return copy;
        }
    }

    private static final class Token {
        static final int CHARACTER = 0;
        static final int ICON = 1;
        static final int BREAK = 2;

        final int kind;
        final char ch;
        final int iconIndex;
        final StyleState style;
        int advance;

        private Token(int kind, char ch, int iconIndex, int advance, StyleState style) {
            this.kind = kind;
            this.ch = ch;
            this.iconIndex = iconIndex;
            this.advance = advance;
            this.style = style;
        }

        static Token character(char ch, StyleState style) {
            return new Token(CHARACTER, ch, -1, 0, style);
        }

        static Token icon(int index, int width, StyleState style) {
            return new Token(ICON, '\0', index, width, style);
        }

        static Token lineBreak() {
            return new Token(BREAK, '\0', -1, 0, null);
        }
    }

    private static final class Line {
        final List<Token> tokens = new ArrayList<Token>();
        int width;
        int height;
        int ascent;

        void add(Token token, Font legacyMetrics) {
            this.tokens.add(token);
            this.width += token.advance;

            if (token.kind == Token.CHARACTER) {
                int tokenHeight = lineHeight(token.style.fontStyle, legacyMetrics);
                int tokenAscent = ascent(token.style.fontStyle, legacyMetrics);
                if (tokenHeight > this.height) {
                    this.height = tokenHeight;
                }
                if (tokenAscent > this.ascent) {
                    this.ascent = tokenAscent;
                }
            } else if (token.kind == Token.ICON && Sprites.nameIcons != null
                && token.iconIndex >= 0 && token.iconIndex < Sprites.nameIcons.length
                && Sprites.nameIcons[token.iconIndex] != null) {
                IndexedSprite icon = Sprites.nameIcons[token.iconIndex];
                if (icon.innerHeight > this.height) {
                    this.height = icon.innerHeight;
                }
                if (icon.innerHeight > this.ascent) {
                    this.ascent = icon.innerHeight;
                }
            }
        }

        void finish(int fallbackStyle, Font legacyMetrics) {
            if (this.height <= 0) {
                this.height = lineHeight(fallbackStyle, legacyMetrics);
            }
            if (this.ascent <= 0) {
                this.ascent = ascent(fallbackStyle, legacyMetrics);
            }
        }
    }

    private static final class IconPlacement {
        final int index;
        final int x;
        final int y;
        final int alpha;

        IconPlacement(int index, int x, int y, int alpha) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.alpha = alpha;
        }
    }

    private static final class RenderedText {
        final int width;
        final int height;
        final int[] pixels;
        final List<IconPlacement> icons;
        SoftwareAlphaSprite softwareSprite;
        GlAlphaSprite glSprite;

        RenderedText(int width, int height, int[] pixels, List<IconPlacement> icons) {
            this.width = width;
            this.height = height;
            this.pixels = pixels;
            this.icons = icons;
        }

        void renderSoftware(int x, int y) {
            if (this.softwareSprite == null) {
                this.softwareSprite = new SoftwareAlphaSprite(
                    this.width,
                    this.height,
                    0,
                    0,
                    this.width,
                    this.height,
                    this.pixels
                );
            }
            this.softwareSprite.render(x, y);
        }

        void render(int x, int y) {
            if (GlRenderer.enabled) {
                if (this.glSprite == null) {
                    this.glSprite = new GlAlphaSprite(
                        this.width,
                        this.height,
                        0,
                        0,
                        this.width,
                        this.height,
                        this.pixels
                    );
                }
                this.glSprite.render(x, y);
            } else {
                if (this.softwareSprite == null) {
                    this.softwareSprite = new SoftwareAlphaSprite(
                        this.width,
                        this.height,
                        0,
                        0,
                        this.width,
                        this.height,
                        this.pixels
                    );
                }
                this.softwareSprite.render(x, y);
            }

            if (Sprites.nameIcons == null) {
                return;
            }

            for (IconPlacement placement : this.icons) {
                if (placement.index < 0 || placement.index >= Sprites.nameIcons.length) {
                    continue;
                }
                IndexedSprite icon = Sprites.nameIcons[placement.index];
                if (icon == null) {
                    continue;
                }
                if (placement.alpha >= 256) {
                    icon.renderTransparent(x + placement.x, y + placement.y);
                } else {
                    icon.renderAlpha(x + placement.x, y + placement.y, placement.alpha);
                }
            }
        }
    }
}
