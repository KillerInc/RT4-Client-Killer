package rt4;

public final class KillerTextScale {
    private static final double SCALE = readScale();

    private KillerTextScale() {}

    public static boolean isEnabled() {
        return Math.abs(SCALE - 1.0D) > 0.001D;
    }

    public static int scale(int value) {
        if (!isEnabled() || value == 0) return value;
        return (int) Math.round(value * SCALE);
    }

    public static byte scaleSignedByte(byte value) {
        if (!isEnabled() || value == 0) return value;
        int scaled = (int) Math.round(value * SCALE);
        if (scaled < -128) scaled = -128;
        if (scaled > 127) scaled = 127;
        return (byte) scaled;
    }

    public static void scaleLoadedFontSprites() {
        if (!isEnabled() || SpriteLoader.pixels == null) return;

        for (int i = 0; i < SpriteLoader.pixels.length; i++) {
            int oldWidth = SpriteLoader.innerWidths[i];
            int oldHeight = SpriteLoader.innerHeights[i];
            int newWidth = Math.max(1, scale(oldWidth));
            int newHeight = Math.max(1, scale(oldHeight));

            byte[] source = SpriteLoader.pixels[i];
            if (source != null && oldWidth > 0 && oldHeight > 0) {
                byte[] scaled = new byte[newWidth * newHeight];
                for (int y = 0; y < newHeight; y++) {
                    int sourceY = Math.min(oldHeight - 1, (int) (y / SCALE));
                    for (int x = 0; x < newWidth; x++) {
                        int sourceX = Math.min(oldWidth - 1, (int) (x / SCALE));
                        scaled[y * newWidth + x] = source[sourceY * oldWidth + sourceX];
                    }
                }
                SpriteLoader.pixels[i] = scaled;
            }

            SpriteLoader.xOffsets[i] = scale(SpriteLoader.xOffsets[i]);
            SpriteLoader.yOffsets[i] = scale(SpriteLoader.yOffsets[i]);
            SpriteLoader.innerWidths[i] = newWidth;
            SpriteLoader.innerHeights[i] = newHeight;
        }
    }

    private static double readScale() {
        try {
            double parsed = Double.parseDouble(System.getProperty("killerTextScale", "1.0"));
            if (Double.isNaN(parsed) || Double.isInfinite(parsed)) return 1.0D;
            return Math.max(0.5D, Math.min(4.0D, parsed));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
    }
}
