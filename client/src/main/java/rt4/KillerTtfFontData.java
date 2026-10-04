package rt4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class KillerTtfFontData {
    public static final String PLAIN_11 = "/killer-fonts/RuneScape-Plain-11.ttf";
    public static final String PLAIN_12 = "/killer-fonts/RuneScape-Plain-12.ttf";
    public static final String BOLD_12 = "/killer-fonts/RuneScape-Bold-12.ttf";

    public final int[] glyphWidths;
    public final byte[] kerning;
    public final int lineHeight;
    public final int[] xOffsets;
    public final int[] yOffsets;
    public final int[] innerWidths;
    public final int[] innerHeights;
    public final byte[][] alphaPixels;

    private static boolean sessionStarted = false;

    private KillerTtfFontData(
        int[] glyphWidths,
        byte[] kerning,
        int lineHeight,
        int[] xOffsets,
        int[] yOffsets,
        int[] innerWidths,
        int[] innerHeights,
        byte[][] alphaPixels
    ) {
        this.glyphWidths = glyphWidths;
        this.kerning = kerning;
        this.lineHeight = lineHeight;
        this.xOffsets = xOffsets;
        this.yOffsets = yOffsets;
        this.innerWidths = innerWidths;
        this.innerHeights = innerHeights;
        this.alphaPixels = alphaPixels;
    }

    public static KillerTtfFontData load(String resource, float nativeSize, String label) {
        startSession();

        double fontScale = readFontScale();
        float logicalSize = (float) (nativeSize * fontScale);

        try (InputStream in = KillerTtfFontData.class.getResourceAsStream(resource)) {
            if (in == null) {
                log("FAILED", label + ": TTF resource not found: " + resource);
                return null;
            }

            java.awt.Font base = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, in);
            Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
            attributes.put(TextAttribute.SIZE, Float.valueOf(logicalSize));
            attributes.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
            java.awt.Font font = base.deriveFont(attributes);

            FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
            LineMetrics lineMetrics = font.getLineMetrics("Ag", frc);
            int lineHeight = Math.max(1, Math.round(lineMetrics.getAscent()));

            int[] glyphWidths = new int[256];
            int[] xOffsets = new int[256];
            int[] yOffsets = new int[256];
            int[] innerWidths = new int[256];
            int[] innerHeights = new int[256];
            byte[][] alphaPixels = new byte[256][];

            int rendered = 0;
            int empty = 0;
            int maxWidth = 0;
            int maxHeight = 0;

            for (int i = 0; i < 256; i++) {
                char ch = decodeCp1252(i);
                GlyphVector gv = font.createGlyphVector(frc, new char[]{ch});
                GlyphMetrics gm = gv.getGlyphMetrics(0);
                java.awt.Rectangle bounds = gv.getPixelBounds(frc, 0.0F, 0.0F);

                glyphWidths[i] = Math.max(0, Math.round(gm.getAdvanceX()));
                xOffsets[i] = bounds.x;
                yOffsets[i] = lineHeight + bounds.y;
                innerWidths[i] = Math.max(0, bounds.width);
                innerHeights[i] = Math.max(0, bounds.height);

                if (bounds.width <= 0 || bounds.height <= 0) {
                    alphaPixels[i] = new byte[0];
                    empty++;
                    continue;
                }

                if (bounds.width > maxWidth) {
                    maxWidth = bounds.width;
                }
                if (bounds.height > maxHeight) {
                    maxHeight = bounds.height;
                }

                BufferedImage image = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D g = image.createGraphics();
                try {
                    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g.setColor(Color.WHITE);
                    g.setFont(font);
                    g.drawGlyphVector(gv, -bounds.x, -bounds.y);
                } finally {
                    g.dispose();
                }

                byte[] pixels = new byte[bounds.width * bounds.height];
                int p = 0;
                for (int y = 0; y < bounds.height; y++) {
                    for (int x = 0; x < bounds.width; x++) {
                        pixels[p++] = (byte) image.getRaster().getSample(x, y, 0);
                    }
                }
                alphaPixels[i] = pixels;
                rendered++;
            }

            byte[] kerning = buildKerning(font, frc, glyphWidths);

            log(
                "SUCCESS",
                label
                    + ": source=" + resource
                    + ", nativeSize=" + nativeSize
                    + ", fontScale=" + fontScale
                    + ", logicalSize=" + logicalSize
                    + ", glyphsRendered=" + rendered
                    + ", emptyGlyphs=" + empty
                    + ", maxGlyph=" + maxWidth + "x" + maxHeight
                    + ", lineHeight=" + lineHeight
                    + ", metrics=TTF"
                    + ", kerning=TTF"
                    + ", renderer=DIRECT_TTF"
            );

            return new KillerTtfFontData(
                glyphWidths,
                kerning,
                lineHeight,
                xOffsets,
                yOffsets,
                innerWidths,
                innerHeights,
                alphaPixels
            );
        } catch (Throwable ex) {
            log("FAILED", label + ": " + ex.getClass().getName() + ": " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] buildKerning(java.awt.Font font, FontRenderContext frc, int[] glyphWidths) {
        byte[] kerning = new byte[65536];

        char[] pair = new char[2];
        for (int left = 0; left < 256; left++) {
            pair[0] = decodeCp1252(left);
            for (int right = 0; right < 256; right++) {
                pair[1] = decodeCp1252(right);

                GlyphVector gv = font.layoutGlyphVector(
                    frc,
                    pair,
                    0,
                    2,
                    java.awt.Font.LAYOUT_LEFT_TO_RIGHT
                );

                int pairAdvance = (int) Math.round(gv.getGlyphPosition(gv.getNumGlyphs()).getX());
                int delta = pairAdvance - glyphWidths[left] - glyphWidths[right];

                if (delta < -128) {
                    delta = -128;
                } else if (delta > 127) {
                    delta = 127;
                }
                kerning[(left << 8) | right] = (byte) delta;
            }
        }
        return kerning;
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

    public static double readFontScale() {
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

    public static void logRenderer(String status, String message) {
        log(status, message);
    }

    private static synchronized void startSession() {
        if (sessionStarted) {
            return;
        }
        sessionStarted = true;
        log(
            "START",
            "Direct TTF font session; uiScale="
                + System.getProperty("sun.java2d.uiScale", "1.0")
                + "; fontScale=" + readFontScale()
                + "; RT4 text parser/effects retained"
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
