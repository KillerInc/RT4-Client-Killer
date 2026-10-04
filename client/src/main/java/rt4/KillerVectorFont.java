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
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Generates the bitmap glyph data expected by the RT4 font renderer from
 * RuneStar's vector versions of the original RuneScape fonts.
 *
 * This is deliberately separate from UI/window scaling. The resulting
 * GlyphSet is consumed by the normal SoftwareFont/GlFont renderers.
 */
public final class KillerVectorFont {
    private static final Charset CP1252 = Charset.forName("windows-1252");
    private static final float BASE_PIXEL_SIZE = 16.0F;

    private static GlyphSet plain11Cache;
    private static GlyphSet plain12Cache;
    private static GlyphSet bold12Cache;

    public static final String PLAIN_11 = "/killer-fonts/RuneScape-Plain-11.ttf";
    public static final String PLAIN_12 = "/killer-fonts/RuneScape-Plain-12.ttf";
    public static final String BOLD_12 = "/killer-fonts/RuneScape-Bold-12.ttf";

    private KillerVectorFont() {
    }

    public static double getTextScale() {
        String raw = System.getProperty("killerTextScale", "1.0");
        try {
            double scale = Double.parseDouble(raw);
            if (Double.isNaN(scale) || Double.isInfinite(scale)) {
                return 1.0D;
            }
            return Math.max(0.5D, Math.min(4.0D, scale));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
    }

    public static Font create(byte[] metricsData, String resource) {
        GlyphSet glyphs = getOrGenerate(resource);
        if (glyphs == null) {
            return null;
        }
        return GlRenderer.enabled
            ? new GlFont(metricsData, glyphs)
            : new SoftwareFont(metricsData, glyphs);
    }

    public static SoftwareFont createSoftware(byte[] metricsData, String resource) {
        GlyphSet glyphs = getOrGenerate(resource);
        return glyphs == null ? null : new SoftwareFont(metricsData, glyphs);
    }

    private static synchronized GlyphSet getOrGenerate(String resource) {
        if (PLAIN_11.equals(resource) && plain11Cache != null) {
            return plain11Cache;
        }
        if (PLAIN_12.equals(resource) && plain12Cache != null) {
            return plain12Cache;
        }
        if (BOLD_12.equals(resource) && bold12Cache != null) {
            return bold12Cache;
        }

        GlyphSet generated = generate(resource, getTextScale());
        if (PLAIN_11.equals(resource)) {
            plain11Cache = generated;
        } else if (PLAIN_12.equals(resource)) {
            plain12Cache = generated;
        } else if (BOLD_12.equals(resource)) {
            bold12Cache = generated;
        }
        return generated;
    }

    private static GlyphSet generate(String resource, double scale) {
        try (InputStream in = KillerVectorFont.class.getResourceAsStream(resource)) {
            if (in == null) {
                return null;
            }

            System.out.println("[KillerFont] Generating " + resource + " at text scale " + scale);
            java.awt.Font vector = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, in)
                .deriveFont((float) (BASE_PIXEL_SIZE * scale));

            FontRenderContext frc = new FontRenderContext(new AffineTransform(), false, false);
            LineMetrics lineMetrics = vector.getLineMetrics("Ag", frc);
            int lineHeight = Math.max(1, Math.round(lineMetrics.getAscent()));

            int[] xOffsets = new int[256];
            int[] yOffsets = new int[256];
            int[] widths = new int[256];
            int[] heights = new int[256];
            int[] advances = new int[256];
            byte[][] pixels = new byte[256][];

            for (int i = 0; i < 256; i++) {
                char ch = cp1252Char(i);
                GlyphVector glyphVector = vector.createGlyphVector(frc, new char[]{ch});
                java.awt.Rectangle bounds = glyphVector.getPixelBounds(frc, 0.0F, 0.0F);
                GlyphMetrics metrics = glyphVector.getGlyphMetrics(0);

                int width = Math.max(0, bounds.width);
                int height = Math.max(0, bounds.height);

                xOffsets[i] = bounds.x;
                yOffsets[i] = lineHeight + bounds.y;
                widths[i] = width;
                heights[i] = height;
                advances[i] = Math.max(0, Math.round(metrics.getAdvanceX()));

                if (width == 0 || height == 0) {
                    pixels[i] = new byte[0];
                    continue;
                }

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D g = image.createGraphics();
                try {
                    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
                    g.setColor(Color.WHITE);
                    g.setFont(vector);
                    g.drawGlyphVector(glyphVector, -bounds.x, -bounds.y);
                } finally {
                    g.dispose();
                }

                byte[] mask = new byte[width * height];
                int p = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        mask[p++] = (byte) ((image.getRGB(x, y) & 0xFF) == 0 ? 0 : 1);
                    }
                }
                pixels[i] = mask;
            }

            return new GlyphSet(xOffsets, yOffsets, widths, heights, advances, pixels, lineHeight);
        } catch (Throwable ex) {
            System.err.println("[KillerFont] Vector font generation failed for " + resource + ": " + ex);
            return null;
        }
    }

    private static char cp1252Char(int value) {
        if (value < 128 || value >= 160) {
            return (char) value;
        }

        byte[] one = new byte[]{(byte) value};
        String decoded = new String(one, CP1252);
        char ch = decoded.charAt(0);
        return ch == '\uFFFD' ? '?' : ch;
    }

    public static final class GlyphSet {
        public final int[] xOffsets;
        public final int[] yOffsets;
        public final int[] widths;
        public final int[] heights;
        public final int[] advances;
        public final byte[][] pixels;
        public final int lineHeight;

        private GlyphSet(
            int[] xOffsets,
            int[] yOffsets,
            int[] widths,
            int[] heights,
            int[] advances,
            byte[][] pixels,
            int lineHeight
        ) {
            this.xOffsets = xOffsets;
            this.yOffsets = yOffsets;
            this.widths = widths;
            this.heights = heights;
            this.advances = advances;
            this.pixels = pixels;
            this.lineHeight = lineHeight;
        }
    }
}
