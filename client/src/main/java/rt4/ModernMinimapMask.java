package rt4;

/**
 * Reusable rectangular software mask for Modern minimap/compass content.
 * The Modern vector frame supplies the visible chrome; this mask contains no
 * cache-era artwork.
 */
public final class ModernMinimapMask {
    private static int cachedWidth = -1;
    private static int cachedHeight = -1;
    private static int[] starts;
    private static int[] widths;

    private ModernMinimapMask() {
    }

    public static synchronized int[][] full(
        int width,
        int height
    ) {
        width = Math.max(1, width);
        height = Math.max(1, height);

        if (cachedWidth != width
            || cachedHeight != height
            || starts == null
            || widths == null) {
            cachedWidth = width;
            cachedHeight = height;
            starts = new int[height];
            widths = new int[height];

            for (int y = 0; y < height; y++) {
                starts[y] = 0;
                widths[y] = width;
            }
        }

        return new int[][] {starts, widths};
    }
}
