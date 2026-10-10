package rt4;

/**
 * Immutable rectangle owned by the Modern UI layout system.
 *
 * Modern rendering and input both consume these rectangles. Vanilla component
 * geometry must never be copied into a ModernUiRect.
 */
public final class ModernUiRect {
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    public ModernUiRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
    }

    public int right() {
        return x + width;
    }

    public int bottom() {
        return y + height;
    }

    public int centerX() {
        return x + width / 2;
    }

    public int centerY() {
        return y + height / 2;
    }

    public boolean contains(int px, int py) {
        return px >= x && px < right() && py >= y && py < bottom();
    }

    public ModernUiRect inset(int amount) {
        return new ModernUiRect(
            x + amount,
            y + amount,
            Math.max(1, width - amount * 2),
            Math.max(1, height - amount * 2)
        );
    }

    @Override
    public String toString() {
        return x + "," + y + " " + width + "x" + height;
    }
}
