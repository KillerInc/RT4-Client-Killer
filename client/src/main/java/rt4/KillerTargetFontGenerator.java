package rt4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class KillerTargetFontGenerator {
    public static final String PLAIN_11 = "/killer-fonts/RuneScape-Plain-11.ttf";
    public static final String PLAIN_12 = "/killer-fonts/RuneScape-Plain-12.ttf";
    public static final String BOLD_12 = "/killer-fonts/RuneScape-Bold-12.ttf";

    private static final int SUPERSAMPLE = 2;
    private static final int BINARY_THRESHOLD = 96;
    private static boolean sessionStarted = false;

    private KillerTargetFontGenerator() {
    }

    public static GeneratedFont generate(String resource, int nativeSize, String label) {
        startSession();

        double fontScale = readFontScale();
        int targetSize = Math.max(1, (int) Math.floor(nativeSize * fontScale + 0.000001D));
        int rasterSize = Math.max(targetSize, targetSize * SUPERSAMPLE);

        try (InputStream in = KillerTargetFontGenerator.class.getResourceAsStream(resource)) {
            if (in == null) {
                log("FAILED", label + ": TTF resource not found: " + resource);
                return null;
            }

            java.awt.Font base = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, in);
            java.awt.Font logicalFont = base.deriveFont((float) targetSize);
            java.awt.Font rasterFont = base.deriveFont((float) rasterSize);

            FontRenderContext logicalFrc = new FontRenderContext(new AffineTransform(), true, true);
            FontRenderContext rasterFrc = new FontRenderContext(new AffineTransform(), true, true);

            LineMetrics lineMetrics = logicalFont.getLineMetrics("Ag", logicalFrc);
            int lineHeight = Math.max(1, Math.round(lineMetrics.getAscent()));

            int[] xOffsets = new int[256];
            int[] yOffsets = new int[256];
            int[] innerWidths = new int[256];
            int[] innerHeights = new int[256];
            int[] advances = new int[256];
            byte[][] pixels = new byte[256][];

            int rendered = 0;
            int empty = 0;
            int maxLogicalWidth = 0;
            int maxLogicalHeight = 0;
            int maxRasterWidth = 0;
            int maxRasterHeight = 0;

            for (int i = 0; i < 256; i++) {
                char ch = decodeCp1252(i);

                GlyphVector logicalGlyph = logicalFont.createGlyphVector(logicalFrc, new char[]{ch});
                GlyphMetrics logicalMetrics = logicalGlyph.getGlyphMetrics(0);
                java.awt.Rectangle logicalBounds = logicalGlyph.getPixelBounds(logicalFrc, 0.0F, 0.0F);

                advances[i] = clampByteUnsigned(Math.round(logicalMetrics.getAdvanceX()));
                xOffsets[i] = logicalBounds.x;
                yOffsets[i] = lineHeight + logicalBounds.y;
                innerWidths[i] = Math.max(0, logicalBounds.width);
                innerHeights[i] = Math.max(0, logicalBounds.height);

                if (innerWidths[i] > maxLogicalWidth) {
                    maxLogicalWidth = innerWidths[i];
                }
                if (innerHeights[i] > maxLogicalHeight) {
                    maxLogicalHeight = innerHeights[i];
                }

                if (logicalBounds.width <= 0 || logicalBounds.height <= 0) {
                    pixels[i] = new byte[0];
                    empty++;
                    continue;
                }

                GlyphVector rasterGlyph = rasterFont.createGlyphVector(rasterFrc, new char[]{ch});
                java.awt.Rectangle rasterBounds = rasterGlyph.getPixelBounds(rasterFrc, 0.0F, 0.0F);

                if (rasterBounds.width <= 0 || rasterBounds.height <= 0) {
                    pixels[i] = new byte[logicalBounds.width * logicalBounds.height];
                    empty++;
                    continue;
                }

                if (rasterBounds.width > maxRasterWidth) {
                    maxRasterWidth = rasterBounds.width;
                }
                if (rasterBounds.height > maxRasterHeight) {
                    maxRasterHeight = rasterBounds.height;
                }

                BufferedImage source = new BufferedImage(
                    rasterBounds.width,
                    rasterBounds.height,
                    BufferedImage.TYPE_BYTE_GRAY
                );
                Graphics2D sg = source.createGraphics();
                try {
                    sg.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                    );
                    sg.setRenderingHint(
                        RenderingHints.KEY_FRACTIONALMETRICS,
                        RenderingHints.VALUE_FRACTIONALMETRICS_ON
                    );
                    sg.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                    );
                    sg.setColor(Color.WHITE);
                    sg.setFont(rasterFont);
                    sg.drawGlyphVector(
                        rasterGlyph,
                        -rasterBounds.x,
                        -rasterBounds.y
                    );
                } finally {
                    sg.dispose();
                }

                BufferedImage target = new BufferedImage(
                    logicalBounds.width,
                    logicalBounds.height,
                    BufferedImage.TYPE_BYTE_GRAY
                );
                Graphics2D tg = target.createGraphics();
                try {
                    tg.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC
                    );
                    tg.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                    );
                    tg.drawImage(
                        source,
                        0,
                        0,
                        logicalBounds.width,
                        logicalBounds.height,
                        null
                    );
                } finally {
                    tg.dispose();
                }

                byte[] mask = new byte[logicalBounds.width * logicalBounds.height];
                int p = 0;
                for (int y = 0; y < logicalBounds.height; y++) {
                    for (int x = 0; x < logicalBounds.width; x++) {
                        int value = target.getRaster().getSample(x, y, 0);
                        mask[p++] = value >= BINARY_THRESHOLD ? (byte) 1 : (byte) 0;
                    }
                }

                pixels[i] = mask;
                rendered++;
            }

            byte[] metrics = new byte[257];
            for (int i = 0; i < 256; i++) {
                metrics[i] = (byte) clampByteUnsigned(advances[i]);
            }
            metrics[256] = (byte) clampByteUnsigned(lineHeight);

            log(
                "SUCCESS",
                label
                    + ": source=" + resource
                    + ", nativeSize=" + nativeSize
                    + ", fontScale=" + fontScale
                    + ", targetSize=" + targetSize
                    + ", supersample=" + SUPERSAMPLE + "x"
                    + ", rasterSize=" + rasterSize
                    + ", logicalMaxGlyph=" + maxLogicalWidth + "x" + maxLogicalHeight
                    + ", rasterMaxGlyph=" + maxRasterWidth + "x" + maxRasterHeight
                    + ", lineHeight=" + lineHeight
                    + ", glyphsRendered=" + rendered
                    + ", emptyGlyphs=" + empty
                    + ", output=RT4_STOCK_FONT"
                    + ", mask=binary"
                    + ", threshold=" + BINARY_THRESHOLD
            );

            return new GeneratedFont(
                metrics,
                xOffsets,
                yOffsets,
                innerWidths,
                innerHeights,
                pixels,
                targetSize,
                rasterSize
            );
        } catch (Throwable ex) {
            log("FAILED", label + ": " + ex.getClass().getName() + ": " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    private static int clampByteUnsigned(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
    }

    private static double readFontScale() {
        try {
            double scale = Double.parseDouble(System.getProperty("killerFontScale", "1.0"));
            if (Double.isNaN(scale) || Double.isInfinite(scale) || scale <= 0.0D) {
                return 1.0D;
            }
            return Math.max(0.5D, Math.min(4.0D, scale));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
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

    private static synchronized void startSession() {
        if (sessionStarted) {
            return;
        }
        sessionStarted = true;
        log(
            "START",
            "Target-size TTF generator; fontScale=" + readFontScale()
                + "; uiScale=" + System.getProperty("sun.java2d.uiScale", "1.0")
                + "; supersample=" + SUPERSAMPLE + "x"
                + "; renderer=RT4_STOCK"
                + "; parserEffects=RT4_STOCK"
        );
    }

    public static void logInstall(String status, String message) {
        log(status, message);
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

    public static final class GeneratedFont {
        public final byte[] metrics;
        public final int[] xOffsets;
        public final int[] yOffsets;
        public final int[] innerWidths;
        public final int[] innerHeights;
        public final byte[][] pixels;
        public final int targetSize;
        public final int rasterSize;

        private GeneratedFont(
            byte[] metrics,
            int[] xOffsets,
            int[] yOffsets,
            int[] innerWidths,
            int[] innerHeights,
            byte[][] pixels,
            int targetSize,
            int rasterSize
        ) {
            this.metrics = metrics;
            this.xOffsets = xOffsets;
            this.yOffsets = yOffsets;
            this.innerWidths = innerWidths;
            this.innerHeights = innerHeights;
            this.pixels = pixels;
            this.targetSize = targetSize;
            this.rasterSize = rasterSize;
        }
    }
}
