package rt4;

/**
 * Modern-owned geometry for HP/Prayer/Run status controls.
 */
public final class ModernStatusOrbLayout {
    public static final int SIZE = 52;
    public static final int GAP = 6;

    public final ModernUiRect orb;

    private ModernStatusOrbLayout(ModernUiRect orb) {
        this.orb = orb;
    }

    public static ModernStatusOrbLayout create(
        int canvasWidth,
        int canvasHeight,
        int index
    ) {
        ModernGameFrameLayout frame =
            ModernGameFrameLayout.create(
                canvasWidth,
                canvasHeight
            );

        int x =
            Math.max(
                8,
                frame.minimap.x - SIZE - 8
            );
        int y =
            frame.minimap.y
                + 22
                + Math.max(0, index) * (SIZE + GAP);

        y = Math.min(
            y,
            Math.max(8, canvasHeight - SIZE - 8)
        );

        return new ModernStatusOrbLayout(
            new ModernUiRect(
                x,
                y,
                SIZE,
                SIZE
            )
        );
    }
}
