package rt4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Killer Edition font generator.
 *
 * UI scaling remains untouched. Font Scale is independent: it scales only
 * generated font geometry (glyph boxes, offsets, advances, kerning and line
 * height) while the existing UI scaler continues to scale the whole client.
 */
public final class KillerFontGenerator {
    public static final String PLAIN_11 = "/killer-fonts/RuneScape-Plain-11.ttf";
    public static final String PLAIN_12 = "/killer-fonts/RuneScape-Plain-12.ttf";
    public static final String BOLD_12 = "/killer-fonts/RuneScape-Bold-12.ttf";

    private static final Map<String, byte[][]> CACHE = new HashMap<String, byte[][]>();
    private static boolean sessionStarted = false;

    private KillerFontGenerator() {
    }

    public static Font load(
        int fontId,
        Js5 spriteJs5,
        Js5 fontJs5,
        String resource,
        float nativeSize,
        String label
    ) {
        if (!SpriteLoader.decode(spriteJs5, 0, fontId)) {
            log("FAILED", label + ": could not decode original cache glyph sprites; using normal Font.load fallback");
            return Font.load(fontId, spriteJs5, fontJs5);
        }

        byte[] metrics = fontJs5.fetchFile(fontId, 0);
        if (metrics == null) {
            log("FAILED", label + ": cache font metrics were missing");
            SpriteLoader.clear();
            return null;
        }

        double fontScale = readFontScale();
        int[] originalWidths = SpriteLoader.innerWidths;
        int[] originalHeights = SpriteLoader.innerHeights;
        byte[][] originalPixels = SpriteLoader.pixels;

        int[] scaledXOffsets = scaleArray(SpriteLoader.xOffsets, fontScale);
        int[] scaledYOffsets = scaleArray(SpriteLoader.yOffsets, fontScale);
        int[] scaledWidths = scaleDimensions(originalWidths, fontScale);
        int[] scaledHeights = scaleDimensions(originalHeights, fontScale);

        byte[][] replacement = generate(
            resource,
            nativeSize,
            originalWidths,
            originalHeights,
            scaledWidths,
            scaledHeights,
            originalPixels,
            label
        );

        Font font;
        if (replacement == null) {
            font = GlRenderer.enabled
                ? new GlFont(metrics, SpriteLoader.xOffsets, SpriteLoader.yOffsets, originalWidths, originalHeights, originalPixels)
                : new SoftwareFont(metrics, SpriteLoader.xOffsets, SpriteLoader.yOffsets, originalWidths, originalHeights, originalPixels);
            log("FALLBACK", label + ": original cache font installed unscaled");
        } else if (GlRenderer.enabled) {
            font = new GlFont(
                metrics,
                scaledXOffsets,
                scaledYOffsets,
                scaledWidths,
                scaledHeights,
                replacement,
                fontScale
            );
            log("SUCCESS", label + ": generated vector font installed; logical metrics scaled by fontScale=" + fontScale);
        } else {
            font = new SoftwareFont(
                metrics,
                scaledXOffsets,
                scaledYOffsets,
                scaledWidths,
                scaledHeights,
                replacement,
                fontScale
            );
            log("SUCCESS", label + ": generated vector font installed; logical metrics scaled by fontScale=" + fontScale);
        }

        SpriteLoader.clear();
        return font;
    }

    public static SoftwareFont loadSoftware(
        int fontId,
        Js5 spriteJs5,
        Js5 fontJs5,
        String resource,
        float nativeSize,
        String label
    ) {
        if (!SpriteLoader.decode(spriteJs5, 0, fontId)) {
            log("FAILED", label + " software: could not decode original cache glyph sprites; using normal SoftwareFont.load fallback");
            return SoftwareFont.load(fontId, fontJs5, spriteJs5);
        }

        byte[] metrics = fontJs5.fetchFile(fontId, 0);
        if (metrics == null) {
            log("FAILED", label + " software: cache font metrics were missing");
            SpriteLoader.clear();
            return null;
        }

        double fontScale = readFontScale();
        int[] originalWidths = SpriteLoader.innerWidths;
        int[] originalHeights = SpriteLoader.innerHeights;
        byte[][] originalPixels = SpriteLoader.pixels;

        int[] scaledXOffsets = scaleArray(SpriteLoader.xOffsets, fontScale);
        int[] scaledYOffsets = scaleArray(SpriteLoader.yOffsets, fontScale);
        int[] scaledWidths = scaleDimensions(originalWidths, fontScale);
        int[] scaledHeights = scaleDimensions(originalHeights, fontScale);

        byte[][] replacement = generate(
            resource,
            nativeSize,
            originalWidths,
            originalHeights,
            scaledWidths,
            scaledHeights,
            originalPixels,
            label
        );

        SoftwareFont font;
        if (replacement == null) {
            font = new SoftwareFont(
                metrics,
                SpriteLoader.xOffsets,
                SpriteLoader.yOffsets,
                originalWidths,
                originalHeights,
                originalPixels
            );
            log("FALLBACK", label + " software: original cache font installed unscaled");
        } else {
            font = new SoftwareFont(
                metrics,
                scaledXOffsets,
                scaledYOffsets,
                scaledWidths,
                scaledHeights,
                replacement,
                fontScale
            );
            log("SUCCESS", label + " software: generated vector font installed at fontScale=" + fontScale);
        }

        SpriteLoader.clear();
        return font;
    }

    private static synchronized byte[][] generate(
        String resource,
        float nativeSize,
        int[] originalWidths,
        int[] originalHeights,
        int[] targetWidths,
        int[] targetHeights,
        byte[][] originalPixels,
        String label
    ) {
        double uiScale = readUiScale();
        double fontScale = readFontScale();
        String cacheKey = resource + "|ui=" + uiScale + "|font=" + fontScale;

        byte[][] cached = CACHE.get(cacheKey);
        if (cached != null) {
            log("SUCCESS", label + ": reused generated glyph cache for uiScale=" + uiScale + ", fontScale=" + fontScale);
            return cached;
        }

        startSession();

        try (InputStream in = KillerFontGenerator.class.getResourceAsStream(resource)) {
            if (in == null) {
                log("FAILED", label + ": vector resource not found: " + resource);
                return null;
            }

            float rasterSize = (float) (nativeSize * uiScale * fontScale);
            java.awt.Font vector = java.awt.Font
                .createFont(java.awt.Font.TRUETYPE_FONT, in)
                .deriveFont(rasterSize);

            FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
            byte[][] generated = new byte[256][];
            int generatedCount = 0;
            int originalCount = 0;
            int maxSourceWidth = 0;
            int maxSourceHeight = 0;

            for (int i = 0; i < 256; i++) {
                int targetWidth = targetWidths[i];
                int targetHeight = targetHeights[i];

                if (targetWidth <= 0 || targetHeight <= 0) {
                    generated[i] = originalPixels[i];
                    originalCount++;
                    continue;
                }

                char ch = decodeCp1252(i);
                RasterResult raster = rasterizeToExactBox(vector, frc, ch, targetWidth, targetHeight);
                byte[] mask = raster == null ? null : raster.pixels;
                if (raster != null) {
                    if (raster.sourceWidth > maxSourceWidth) {
                        maxSourceWidth = raster.sourceWidth;
                    }
                    if (raster.sourceHeight > maxSourceHeight) {
                        maxSourceHeight = raster.sourceHeight;
                    }
                }
                if (mask == null || !containsInk(mask)) {
                    generated[i] = scaleOriginalMask(
                        originalPixels[i],
                        originalWidths[i],
                        originalHeights[i],
                        targetWidth,
                        targetHeight
                    );
                    originalCount++;
                } else {
                    generated[i] = mask;
                    generatedCount++;
                }
            }

            if (generatedCount == 0) {
                log("FAILED", label + ": generator produced no usable glyphs");
                return null;
            }

            CACHE.put(cacheKey, generated);
            log(
                "SUCCESS",
                label + ": vector source loaded; generated=" + generatedCount
                    + ", original-fallback=" + originalCount
                    + ", logicalSize=" + nativeSize
                    + ", uiScale=" + uiScale
                    + ", fontScale=" + fontScale
                    + ", rasterSize=" + rasterSize
                    + ", maxSourceRaster=" + maxSourceWidth + "x" + maxSourceHeight
                    + ", outputFontScale=" + fontScale
                    + ", output=scaled RT4 glyph boxes"
                    + ", mask=alpha8"
                    + ", rendererAlpha=enabled"
            );
            return generated;
        } catch (Throwable ex) {
            log("FAILED", label + ": " + ex.getClass().getName() + ": " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    private static RasterResult rasterizeToExactBox(
        java.awt.Font font,
        FontRenderContext frc,
        char ch,
        int targetWidth,
        int targetHeight
    ) {
        GlyphVector glyphVector = font.createGlyphVector(frc, new char[]{ch});
        java.awt.Rectangle bounds = glyphVector.getPixelBounds(frc, 0.0F, 0.0F);

        if (bounds.width <= 0 || bounds.height <= 0) {
            return null;
        }

        BufferedImage source = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = source.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawGlyphVector(glyphVector, -bounds.x, -bounds.y);
        } finally {
            g.dispose();
        }

        BufferedImage target = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D out = target.createGraphics();
        try {
            out.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            out.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            double scaleX = (double) targetWidth / (double) bounds.width;
            double scaleY = (double) targetHeight / (double) bounds.height;
            double scale = Math.min(scaleX, scaleY);

            int drawWidth = Math.max(1, Math.min(targetWidth, (int) Math.round(bounds.width * scale)));
            int drawHeight = Math.max(1, Math.min(targetHeight, (int) Math.round(bounds.height * scale)));
            int offsetX = (targetWidth - drawWidth) / 2;
            int offsetY = targetHeight - drawHeight;

            out.drawImage(source, offsetX, offsetY, drawWidth, drawHeight, null);
        } finally {
            out.dispose();
        }

        byte[] mask = new byte[targetWidth * targetHeight];
        int p = 0;
        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int value = target.getRaster().getSample(x, y, 0);
                mask[p++] = (byte) value;
            }
        }

        return new RasterResult(mask, bounds.width, bounds.height);
    }

    private static int[] scaleArray(int[] source, double scale) {
        int[] result = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = (int) Math.round(source[i] * scale);
        }
        return result;
    }

    private static int[] scaleDimensions(int[] source, double scale) {
        int[] result = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            if (source[i] <= 0) {
                result[i] = 0;
            } else {
                result[i] = Math.max(1, (int) Math.round(source[i] * scale));
            }
        }
        return result;
    }

    private static byte[] scaleOriginalMask(
        byte[] source,
        int sourceWidth,
        int sourceHeight,
        int targetWidth,
        int targetHeight
    ) {
        if (source == null || sourceWidth <= 0 || sourceHeight <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return source;
        }
        byte[] result = new byte[targetWidth * targetHeight];
        for (int y = 0; y < targetHeight; y++) {
            int sy = Math.min(sourceHeight - 1, (int) ((long) y * sourceHeight / targetHeight));
            for (int x = 0; x < targetWidth; x++) {
                int sx = Math.min(sourceWidth - 1, (int) ((long) x * sourceWidth / targetWidth));
                result[y * targetWidth + x] = source[sy * sourceWidth + sx] == 0 ? (byte) 0 : (byte) 0xFF;
            }
        }
        return result;
    }

    private static double readUiScale() {
        try {
            double scale = Double.parseDouble(System.getProperty("sun.java2d.uiScale", "1.0"));
            if (Double.isNaN(scale) || Double.isInfinite(scale) || scale <= 0.0D) {
                return 1.0D;
            }
            return Math.max(0.5D, Math.min(4.0D, scale));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
    }

    private static double readFontScale() {
        try {
            double scale = Double.parseDouble(System.getProperty("killerFontScale", "1.0"));
            if (Double.isNaN(scale) || Double.isInfinite(scale) || scale <= 0.0D) {
                return 1.0D;
            }
            return Math.max(0.5D, Math.min(2.0D, scale));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
    }

    private static boolean containsInk(byte[] pixels) {
        for (byte pixel : pixels) {
            if (pixel != 0) {
                return true;
            }
        }
        return false;
    }

    private static char decodeCp1252(int value) {
        if (value >= 128 && value < 160) {
            final char[] extension = {
                '\u20AC', '\u0000', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021',
                '\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017D', '\u0000',
                '\u0000', '\u2018', '\u2019', '\u201C', '\u201D', '\u2022', '\u2013', '\u2014',
                '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\u0000', '\u017E', '\u0178'
            };
            char ch = extension[value - 128];
            return ch == '\u0000' ? '?' : ch;
        }
        return (char) value;
    }

    private static final class RasterResult {
        private final byte[] pixels;
        private final int sourceWidth;
        private final int sourceHeight;

        private RasterResult(byte[] pixels, int sourceWidth, int sourceHeight) {
            this.pixels = pixels;
            this.sourceWidth = sourceWidth;
            this.sourceHeight = sourceHeight;
        }
    }

    private static synchronized void startSession() {
        if (sessionStarted) {
            return;
        }
        sessionStarted = true;
        log(
            "START",
            "Killer font generator session; UI scaling remains unchanged; "
                + "supersample=true; mask=alpha8; rendererAlpha=enabled"
                + "; uiScale=" + readUiScale()
                + "; fontScale=" + readFontScale()
        );
    }

    private static synchronized void log(String status, String message) {
        String line = "[" + timestamp() + "] [" + status + "] " + message;
        System.out.println("[KillerFont] " + status + " " + message);

        PrintWriter writer = null;
        try {
            File logFile = getLogFile();
            File parent = logFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            writer = new PrintWriter(new FileWriter(logFile, true));
            writer.println(line);
        } catch (Throwable ignored) {
            // Console output above remains available if file logging fails.
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static File getLogFile() {
        String home = System.getProperty("clientHomeOverride");
        if (home == null || home.trim().isEmpty()) {
            home = System.getProperty("user.home") + File.separator + "2009scape";
        }
        return new File(new File(home, "logs"), "killer-font.log");
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
