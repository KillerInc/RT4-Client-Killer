package rt4;

/**
 * Declarative geometry for the permanent Modern in-game HUD.
 *
 * Dynamic game content (scene, minimap, items) is data/content, while every
 * frame/chrome rectangle is owned here.
 */
public final class ModernGameFrameLayout {
    public static final int EDGE = 12;
    public static final int MINIMAP_SIZE = 176;
    public static final int MINIMAP_FRAME_PAD = 6;
    public static final int COMPASS_SIZE = 42;

    public final ModernUiRect minimap;
    public final ModernUiRect minimapFrame;
    public final ModernUiRect compass;

    private ModernGameFrameLayout(
        ModernUiRect minimap,
        ModernUiRect minimapFrame,
        ModernUiRect compass
    ) {
        this.minimap = minimap;
        this.minimapFrame = minimapFrame;
        this.compass = compass;
    }

    public static ModernGameFrameLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int mapSize = Math.min(
            MINIMAP_SIZE,
            Math.max(132, Math.min(canvasWidth, canvasHeight) / 3)
        );

        int mapX = Math.max(
            EDGE,
            canvasWidth - EDGE - mapSize
        );
        int mapY = EDGE;

        ModernUiRect minimap = new ModernUiRect(
            mapX,
            mapY,
            mapSize,
            mapSize
        );

        ModernUiRect minimapFrame = new ModernUiRect(
            mapX - MINIMAP_FRAME_PAD,
            mapY - MINIMAP_FRAME_PAD,
            mapSize + MINIMAP_FRAME_PAD * 2,
            mapSize + MINIMAP_FRAME_PAD * 2
        );

        ModernUiRect compass = new ModernUiRect(
            Math.max(
                4,
                mapX - COMPASS_SIZE / 2 + 4
            ),
            Math.max(
                4,
                mapY - COMPASS_SIZE / 2 + 4
            ),
            COMPASS_SIZE,
            COMPASS_SIZE
        );

        return new ModernGameFrameLayout(
            minimap,
            minimapFrame,
            compass
        );
    }
}
