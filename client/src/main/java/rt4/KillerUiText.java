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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Killer UI text engine.
 *
 * UI-only replacement for RT4's cache-font text path. It parses the text
 * markup first, then renders the selected RuneScape TTF with Java2D into an
 * ARGB sprite which is composited through the existing software/OpenGL sprite
 * paths. World/scene text does not use this class.
 */
public final class KillerUiText {
    public static final int PLAIN_11 = 0;
    public static final int PLAIN_12 = 1;
    public static final int BOLD_12 = 2;

    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_WAVE = 1;
    public static final int EFFECT_WAVE2 = 2;
    public static final int EFFECT_SHAKE = 3;
    public static final int EFFECT_RAINBOW = 4;

    private static final String PLAIN_11_RESOURCE = "/killer-fonts/RuneScape-Plain-11.ttf";
    private static final String PLAIN_12_RESOURCE = "/killer-fonts/RuneScape-Plain-12.ttf";
    private static final String BOLD_12_RESOURCE = "/killer-fonts/RuneScape-Bold-12.ttf";

    private static final Charset WINDOWS_1252 = Charset.forName("windows-1252");
    private static final FontRenderContext FRC = new FontRenderContext(null, true, true);

    private static final java.awt.Font[] BASE_FONTS = new java.awt.Font[3];
    private static final java.awt.Font[] SCALED_FONTS = new java.awt.Font[3];
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

    public static synchronized void verifyReady() {
        KillerUiLog.start();
        ensureFonts();

        if (BASE_FONTS[PLAIN_11] == null || BASE_FONTS[PLAIN_12] == null || BASE_FONTS[BOLD_12] == null
            || SCALED_FONTS[PLAIN_11] == null || SCALED_FONTS[PLAIN_12] == null || SCALED_FONTS[BOLD_12] == null) {
            KillerUiLog.write("FATAL uiTextEngineVerificationFailed");
            throw new IllegalStateException("Killer UI text engine failed startup verification");
        }

        KillerUiLog.once("verified", "VERIFY uiTextEngine=READY allRequiredTtfLoaded=true fallback=false");
    }

    public static int styleForComponent(Component component) {
        if (component == null) {
            KillerUiLog.write("FATAL componentStyle=null component");
            throw new IllegalStateException("Killer UI text requested for a null component");
        }
        if (component.font == Sprites.p11FullId) {
            return PLAIN_11;
        }
        if (component.font == Sprites.p12FullId) {
            return PLAIN_12;
        }
        if (component.font == Sprites.b12FullId) {
            return BOLD_12;
        }

        // Jagex's login/game-menu interface uses a separate small menu font.
        // It is explicitly supported by the replacement UI engine rather than
        // falling back to Component.getFont()/the stock bitmap renderer.
        if (component.font == 591) {
            KillerUiLog.once(
                "font-alias-591",
                "FONT_MAP fontId=591 cacheName=menu_font_small -> RuneScape-Plain-11.ttf"
            );
            return PLAIN_11;
        }

        KillerUiLog.write(
            "FATAL unsupportedUiFont component=" + component.id + " fontId=" + component.font
                + " p11Id=" + Sprites.p11FullId
                + " p12Id=" + Sprites.p12FullId
                + " b12Id=" + Sprites.b12FullId
        );
        throw new IllegalStateException(
            "Unsupported UI font id " + component.font + " on component " + component.id
        );
    }

    public static int lineHeight(int style) {
        java.awt.Font font = getFont(style);
        LineMetrics metrics = font.getLineMetrics("Ag", FRC);
        return Math.max(1, (int) Math.ceil(metrics.getHeight()));
    }

    public static int ascent(int style) {
        java.awt.Font font = getFont(style);
        return Math.max(1, (int) Math.ceil(font.getLineMetrics("Ag", FRC).getAscent()));
    }

    public static int measureWidth(JagString text, int style) {
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE);
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
        ParsedText parsed = parse(text, style, 0xFFFFFF, -1, 256, EFFECT_NONE);
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
        if (component == null) {
            return;
        }
        KillerUiLog.once("component-text", "ROUTE componentText=KillerUiText");
        draw(
            text,
            styleForComponent(component),
            x,
            y,
            component.width,
            component.height,
            color,
            shadow,
            256,
            component.halign,
            component.valign,
            component.vpadding,
            EFFECT_NONE
        );
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
        if (text == null || width <= 0 || height <= 0) {
            return;
        }

        KillerUiLog.start();
        KillerUiLog.once(
            GlRenderer.enabled ? "renderer-gl" : "renderer-software",
            "COMPOSITOR=" + (GlRenderer.enabled ? "OPENGL" : "SOFTWARE")
        );

        ParsedText parsed = parse(text, style, color, shadow, alpha, effect);
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
            key = cacheKey(text, style, width, height, color, shadow, alpha, halign, valign, vpadding);
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
                    int waveY = effectYOffset(parsed.effect, charIndex);
                    int waveX = effectXOffset(parsed.effect, charIndex);
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

                y += line.height + parsed.linePadding;
            }
        } finally {
            g.dispose();
        }

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        return new RenderedText(width, height, pixels, icons);
    }

    private static void setComposite(Graphics2D g, int alpha) {
        int a = Math.max(0, Math.min(256, alpha));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) a / 256.0F));
    }

    private static int effectYOffset(int effect, int index) {
        double t = (double) client.loop / 5.0D;
        if (effect == EFFECT_WAVE) {
            return (int) Math.round(Math.sin((double) index / 2.0D + t) * KillerUi.px(2));
        }
        if (effect == EFFECT_WAVE2) {
            return (int) Math.round(Math.sin((double) index / 1.5D + t) * KillerUi.px(3));
        }
        if (effect == EFFECT_SHAKE) {
            int seed = index * 1103515245 + client.loop * 12345;
            return ((seed >>> 16) & 3) - 1;
        }
        return 0;
    }

    private static int effectXOffset(int effect, int index) {
        if (effect == EFFECT_SHAKE) {
            int seed = index * 214013 + client.loop * 2531011;
            return ((seed >>> 17) & 3) - 1;
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
                current = flushWord(lines, current, word, wordWidth, maxWidth, parsed.fontStyle);
                word.clear();
                wordWidth = 0;

                current.finish(parsed.fontStyle);
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
                        current.add(token);
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
        int fallbackStyle
    ) {
        if (word.isEmpty()) {
            return current;
        }

        if (!current.tokens.isEmpty() && current.width + wordWidth > maxWidth) {
            current.finish(fallbackStyle);
            lines.add(current);
            current = new Line();
        }

        for (Token token : word) {
            if (current.width + token.advance > maxWidth && !current.tokens.isEmpty()) {
                current.finish(fallbackStyle);
                lines.add(current);
                current = new Line();
            }
            current.add(token);
        }

        return current;
    }

    private static ParsedText parse(
        JagString input,
        int fontStyle,
        int baseColor,
        int baseShadow,
        int baseAlpha,
        int requestedEffect
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

        for (Token token : tokens) {
            if (token.kind == Token.CHARACTER) {
                token.advance = charAdvance(token.ch, token.style.fontStyle);
            }
        }

        return new ParsedText(tokens, fontStyle, effect);
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

    private static int charAdvance(char ch, int style) {
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

        loadBaseFont(PLAIN_11, PLAIN_11_RESOURCE, java.awt.Font.PLAIN);
        loadBaseFont(PLAIN_12, PLAIN_12_RESOURCE, java.awt.Font.PLAIN);
        loadBaseFont(BOLD_12, BOLD_12_RESOURCE, java.awt.Font.BOLD);

        SCALED_FONTS[PLAIN_11] = BASE_FONTS[PLAIN_11].deriveFont((float) KillerUi.fontTarget(11));
        SCALED_FONTS[PLAIN_12] = BASE_FONTS[PLAIN_12].deriveFont((float) KillerUi.fontTarget(12));
        SCALED_FONTS[BOLD_12] = BASE_FONTS[BOLD_12].deriveFont((float) KillerUi.fontTarget(12));
        KillerUiLog.once(
            "font-targets-" + currentScale,
            "TTF_TARGETS p11=" + KillerUi.fontTarget(11)
                + " p12=" + KillerUi.fontTarget(12)
                + " b12=" + KillerUi.fontTarget(12)
        );
        loadedScale = currentScale;
        synchronized (CACHE) {
            CACHE.clear();
        }
    }

    private static void loadBaseFont(int style, String resource, int fallbackStyle) {
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
        int width,
        int height,
        int color,
        int shadow,
        int alpha,
        int halign,
        int valign,
        int vpadding
    ) {
        return KillerUi.scale() + "|" + style + "|" + width + "|" + height + "|" + color + "|" + shadow
            + "|" + alpha + "|" + halign + "|" + valign + "|" + vpadding + "|" + toJavaString(text);
    }

    private static final class ParsedText {
        final List<Token> tokens;
        final int fontStyle;
        final int effect;
        int linePadding;

        ParsedText(List<Token> tokens, int fontStyle, int effect) {
            this.tokens = tokens;
            this.fontStyle = fontStyle;
            this.effect = effect;
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

        void add(Token token) {
            this.tokens.add(token);
            this.width += token.advance;

            if (token.kind == Token.CHARACTER) {
                int tokenHeight = lineHeight(token.style.fontStyle);
                int tokenAscent = ascent(token.style.fontStyle);
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

        void finish(int fallbackStyle) {
            if (this.height <= 0) {
                this.height = lineHeight(fallbackStyle);
            }
            if (this.ascent <= 0) {
                this.ascent = ascent(fallbackStyle);
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
