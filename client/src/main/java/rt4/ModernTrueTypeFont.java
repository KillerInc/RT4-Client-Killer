package rt4;

import com.jogamp.opengl.GL2;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TrueType text renderer used exclusively by Modern UI.
 *
 * Text is rasterized from TTF/vector font outlines at the requested size.
 * It never calls the legacy RT4 bitmap Font/Sprite font pipeline.
 *
 * The first implementation caches complete text masks. Once the new UI is
 * feature complete this cache can be replaced by a glyph atlas without
 * changing callers.
 */
public final class ModernTrueTypeFont {
    private static final int MAX_CACHE_ENTRIES = 384;

    private static final Map<String, java.awt.Font> baseFonts = new LinkedHashMap<>();
    private static int cacheContextId = -1;

    private static final LinkedHashMap<TextKey, TextMask> cache =
        new LinkedHashMap<TextKey, TextMask>(128, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<TextKey, TextMask> eldest) {
                if (size() <= MAX_CACHE_ENTRIES) {
                    return false;
                }
                eldest.getValue().disposeGlTexture();
                return true;
            }
        };

    private ModernTrueTypeFont() {
    }

    public static void clear() {
        synchronized (cache) {
            for (TextMask mask : cache.values()) {
                mask.disposeGlTexture();
            }
            cache.clear();
            baseFonts.clear();
        }
    }

    public static void draw(String text, int x, int baselineY, int rgb, float size, boolean shadow) {
        draw(ModernUiFontRegistry.DEFAULT, text, x, baselineY, rgb, size, shadow);
    }

    public static void draw(
        String fontAsset,
        String text,
        int x,
        int baselineY,
        int rgb,
        float size,
        boolean shadow
    ) {
        if (text == null || text.isEmpty()) {
            return;
        }

        String clean = cleanMarkup(text);
        if (clean.isEmpty()) {
            return;
        }

        int pixelSize = Math.max(7, Math.round(size * ModernUiPreferences.getTextScale()));
        if (shadow) {
            drawMask(fontAsset, clean, x + 1, baselineY + 1, 0x000000, pixelSize);
        }
        drawMask(fontAsset, clean, x, baselineY, rgb, pixelSize);
    }

    public static void drawCentered(String text, int centerX, int baselineY, int rgb, float size, boolean shadow) {
        drawCentered(ModernUiFontRegistry.DEFAULT, text, centerX, baselineY, rgb, size, shadow);
    }

    public static void drawCentered(
        String fontAsset,
        String text,
        int centerX,
        int baselineY,
        int rgb,
        float size,
        boolean shadow
    ) {
        int width = getWidth(fontAsset, text, size);
        draw(fontAsset, text, centerX - width / 2, baselineY, rgb, size, shadow);
    }

    public static int getWidth(String text, float size) {
        return getWidth(ModernUiFontRegistry.DEFAULT, text, size);
    }

    public static int getWidth(String fontAsset, String text, float size) {
        String clean = cleanMarkup(text == null ? "" : text);
        if (clean.isEmpty()) {
            return 0;
        }
        int pixelSize = Math.max(7, Math.round(size * ModernUiPreferences.getTextScale()));
        return getMask(fontAsset, clean, pixelSize).width;
    }

    public static int getLineHeight(float size) {
        return getLineHeight(ModernUiFontRegistry.DEFAULT, size);
    }

    public static int getLineHeight(String fontAsset, float size) {
        int pixelSize = Math.max(7, Math.round(size * ModernUiPreferences.getTextScale()));
        return getMask(fontAsset, "Ag", pixelSize).height;
    }

    public static void drawInBox(
        String text,
        int x,
        int y,
        int width,
        int height,
        int rgb,
        int horizontalAlign,
        int verticalAlign,
        float size,
        boolean shadow
    ) {
        drawInBox(
            ModernUiFontRegistry.DEFAULT,
            text,
            x,
            y,
            width,
            height,
            rgb,
            horizontalAlign,
            verticalAlign,
            size,
            shadow
        );
    }

    public static void drawInBox(
        String fontAsset,
        String text,
        int x,
        int y,
        int width,
        int height,
        int rgb,
        int horizontalAlign,
        int verticalAlign,
        float size,
        boolean shadow
    ) {
        if (width <= 0 || height <= 0 || text == null) {
            return;
        }

        String clean = cleanMarkup(text);
        int pixelSize = Math.max(7, Math.round(size * ModernUiPreferences.getTextScale()));
        java.util.List<String> lines = wrap(fontAsset, clean, width, pixelSize);
        int lineHeight = Math.max(1, getMask(fontAsset, "Ag", pixelSize).height + 2);
        int totalHeight = lines.size() * lineHeight;

        int top = y;
        if (verticalAlign == 1) {
            top = y + Math.max(0, (height - totalHeight) / 2);
        } else if (verticalAlign == 2) {
            top = y + Math.max(0, height - totalHeight);
        }

        int baseline = top + getMask(fontAsset, "Ag", pixelSize).ascent;
        for (String line : lines) {
            int drawX = x;
            int lineWidth = getMask(fontAsset, line.isEmpty() ? " " : line, pixelSize).width;
            if (horizontalAlign == 1) {
                drawX = x + (width - lineWidth) / 2;
            } else if (horizontalAlign == 2) {
                drawX = x + width - lineWidth;
            }

            if (shadow) {
                drawMask(fontAsset, line, drawX + 1, baseline + 1, 0x000000, pixelSize);
            }
            drawMask(fontAsset, line, drawX, baseline, rgb, pixelSize);
            baseline += lineHeight;
            if (baseline - top > height + lineHeight) {
                break;
            }
        }
    }

    private static java.util.List<String> wrap(String fontAsset, String text, int width, int pixelSize) {
        java.util.List<String> out = new java.util.ArrayList<>();
        if (text.isEmpty()) {
            out.add("");
            return out;
        }

        for (String paragraph : text.split("\\n", -1)) {
            if (paragraph.isEmpty()) {
                out.add("");
                continue;
            }

            StringBuilder line = new StringBuilder();
            for (String word : paragraph.split(" ")) {
                String candidate = line.length() == 0 ? word : line + " " + word;
                if (line.length() > 0 && getMask(fontAsset, candidate, pixelSize).width > width) {
                    out.add(line.toString());
                    line.setLength(0);
                    line.append(word);
                } else {
                    if (line.length() > 0) {
                        line.append(' ');
                    }
                    line.append(word);
                }
            }
            out.add(line.toString());
        }
        return out;
    }

    private static String cleanMarkup(String text) {
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length();) {
            if (text.regionMatches(true, i, "<br>", 0, 4)) {
                out.append('\n');
                i += 4;
                continue;
            }
            if (text.charAt(i) == '<') {
                int end = text.indexOf('>', i + 1);
                if (end >= 0) {
                    i = end + 1;
                    continue;
                }
            }
            out.append(text.charAt(i++));
        }
        return out.toString();
    }

    private static void drawMask(String fontAsset, String text, int x, int baselineY, int rgb, int pixelSize) {
        if (text == null || text.isEmpty()) {
            return;
        }

        TextMask mask = getMask(fontAsset, text, pixelSize);
        int top = baselineY - mask.ascent;
        if (GlRenderer.enabled) {
            drawGl(mask, x, top, rgb);
        } else {
            drawSoftware(mask, x, top, rgb);
        }
    }

    private static TextMask getMask(String fontAsset, String text, int pixelSize) {
        if (GlRenderer.enabled && cacheContextId != GlCleaner.contextId) {
            synchronized (cache) {
                cache.clear();
                cacheContextId = GlCleaner.contextId;
            }
        }

        TextKey key = new TextKey(fontAsset, text, pixelSize);
        synchronized (cache) {
            TextMask mask = cache.get(key);
            if (mask != null) {
                return mask;
            }
            mask = createMask(fontAsset, text, pixelSize);
            cache.put(key, mask);
            return mask;
        }
    }

    private static TextMask createMask(String fontAsset, String text, int pixelSize) {
        java.awt.Font font = getBaseFont(fontAsset).deriveFont(java.awt.Font.PLAIN, (float) pixelSize);

        BufferedImage measure = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D mg = measure.createGraphics();
        configureGraphics(mg);
        mg.setFont(font);
        FontMetrics fm = mg.getFontMetrics();
        int width = Math.max(1, fm.stringWidth(text));
        int height = Math.max(1, fm.getHeight());
        int ascent = fm.getAscent();
        mg.dispose();

        BufferedImage image = new BufferedImage(width + 2, height + 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        configureGraphics(g);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(text, 1, ascent + 1);
        g.dispose();

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        byte[] alpha = new byte[imageWidth * imageHeight];
        int[] argb = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
        for (int i = 0; i < argb.length; i++) {
            alpha[i] = (byte) (argb[i] >>> 24);
        }

        return new TextMask(imageWidth, imageHeight, ascent + 1, alpha);
    }

    private static void configureGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    private static java.awt.Font getBaseFont(String fontAsset) {
        String asset =
            fontAsset == null || fontAsset.trim().isEmpty()
                ? ModernUiFontRegistry.DEFAULT
                : fontAsset;

        synchronized (cache) {
            java.awt.Font cached = baseFonts.get(asset);
            if (cached != null) {
                return cached;
            }
        }

        byte[] bytes = ModernUiAssetResolver.getBytes(asset);
        if (bytes == null || bytes.length == 0) {
            throw new IllegalStateException(
                "Modern UI requires " + asset
                    + "; no legacy or system-font fallback is allowed"
            );
        }

        try (InputStream input = new ByteArrayInputStream(bytes)) {
            java.awt.Font loaded =
                java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, input);
            synchronized (cache) {
                baseFonts.put(asset, loaded);
            }
            DisplayDebug.log(
                "MODERN_UI loaded TTF " + asset + " from "
                    + ModernUiAssetResolver.getResolvedSource(asset)
            );
            return loaded;
        } catch (Exception ex) {
            throw new IllegalStateException(
                "Modern UI TTF load failed asset=" + asset,
                ex
            );
        }
    }

    private static void drawGl(TextMask mask, int x, int y, int rgb) {
        mask.ensureGlTexture();

        GL2 gl = GlRenderer.gl;
        GlRenderer.begin2DModulateAlt();
        GlRenderer.setTextureId(mask.textureId);

        float r = (float) (rgb >> 16 & 0xFF) / 255.0F;
        float g = (float) (rgb >> 8 & 0xFF) / 255.0F;
        float b = (float) (rgb & 0xFF) / 255.0F;
        gl.glColor4f(r, g, b, 1.0F);

        float top = GlRenderer.canvasHeight - y;
        float bottom = top - mask.height;
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glTexCoord2f(1.0F, 0.0F);
        gl.glVertex2f(x + mask.width, top);
        gl.glTexCoord2f(0.0F, 0.0F);
        gl.glVertex2f(x, top);
        gl.glTexCoord2f(0.0F, 1.0F);
        gl.glVertex2f(x, bottom);
        gl.glTexCoord2f(1.0F, 1.0F);
        gl.glVertex2f(x + mask.width, bottom);
        gl.glEnd();

        gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void drawSoftware(TextMask mask, int x, int y, int rgb) {
        int srcR = rgb >> 16 & 0xFF;
        int srcG = rgb >> 8 & 0xFF;
        int srcB = rgb & 0xFF;

        for (int sy = 0; sy < mask.height; sy++) {
            int dy = y + sy;
            if (dy < SoftwareRaster.clipTop || dy >= SoftwareRaster.clipBottom) {
                continue;
            }
            for (int sx = 0; sx < mask.width; sx++) {
                int dx = x + sx;
                if (dx < SoftwareRaster.clipLeft || dx >= SoftwareRaster.clipRight) {
                    continue;
                }

                int alpha = mask.alpha[sx + sy * mask.width] & 0xFF;
                if (alpha == 0) {
                    continue;
                }

                int index = dx + dy * SoftwareRaster.width;
                int dst = SoftwareRaster.pixels[index];
                int inv = 255 - alpha;

                int r = (srcR * alpha + (dst >> 16 & 0xFF) * inv) / 255;
                int g = (srcG * alpha + (dst >> 8 & 0xFF) * inv) / 255;
                int b = (srcB * alpha + (dst & 0xFF) * inv) / 255;
                SoftwareRaster.pixels[index] = r << 16 | g << 8 | b;
            }
        }
    }

    private static final class TextKey {
        private final String fontAsset;
        private final String text;
        private final int size;

        private TextKey(String fontAsset, String text, int size) {
            this.fontAsset =
                fontAsset == null || fontAsset.trim().isEmpty()
                    ? ModernUiFontRegistry.DEFAULT
                    : fontAsset;
            this.text = text;
            this.size = size;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof TextKey)) {
                return false;
            }
            TextKey key = (TextKey) other;
            return size == key.size
                && fontAsset.equals(key.fontAsset)
                && text.equals(key.text);
        }

        @Override
        public int hashCode() {
            int result = fontAsset.hashCode();
            result = 31 * result + text.hashCode();
            return 31 * result + size;
        }
    }

    private static final class TextMask {
        private final int width;
        private final int height;
        private final int ascent;
        private final byte[] alpha;
        private int textureId = -1;
        private int textureContextId = -1;

        private TextMask(int width, int height, int ascent, byte[] alpha) {
            this.width = width;
            this.height = height;
            this.ascent = ascent;
            this.alpha = alpha;
        }

        private void ensureGlTexture() {
            if (textureId != -1 && textureContextId == GlCleaner.contextId) {
                return;
            }

            GL2 gl = GlRenderer.gl;
            int[] ids = new int[1];
            gl.glGenTextures(1, ids, 0);
            textureId = ids[0];
            textureContextId = GlCleaner.contextId;

            ByteBuffer rgba = ByteBuffer.allocateDirect(width * height * 4);
            for (byte value : alpha) {
                rgba.put((byte) 0xFF);
                rgba.put((byte) 0xFF);
                rgba.put((byte) 0xFF);
                rgba.put(value);
            }
            rgba.flip();

            GlRenderer.setTextureId(textureId);
            gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
            gl.glTexImage2D(
                GL2.GL_TEXTURE_2D,
                0,
                GL2.GL_RGBA,
                width,
                height,
                0,
                GL2.GL_RGBA,
                GL2.GL_UNSIGNED_BYTE,
                rgba
            );
        }

        private void disposeGlTexture() {
            if (textureId == -1 || !GlRenderer.enabled || textureContextId != GlCleaner.contextId) {
                textureId = -1;
                return;
            }
            int[] ids = {textureId};
            GlRenderer.gl.glDeleteTextures(1, ids, 0);
            textureId = -1;
        }
    }
}
