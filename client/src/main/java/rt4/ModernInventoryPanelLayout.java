package rt4;

/**
 * Declarative layout for the in-game inventory panel.
 */
public final class ModernInventoryPanelLayout {
    public static final int PANEL_WIDTH = 220;
    public static final int PANEL_HEIGHT = 310;
    public static final int GAP_BELOW_MINIMAP = 14;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect grid;

    private ModernInventoryPanelLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect grid
    ) {
        this.panel = panel;
        this.title = title;
        this.grid = grid;
    }

    public static ModernInventoryPanelLayout create(
        int canvasWidth,
        int canvasHeight,
        Component inventory
    ) {
        int x = Math.max(
            8,
            canvasWidth - ModernGameFrameLayout.EDGE - PANEL_WIDTH
        );

        ModernGameFrameLayout frame =
            ModernGameFrameLayout.create(
                canvasWidth,
                canvasHeight
            );

        int preferredY =
            frame.minimapFrame.bottom()
                + GAP_BELOW_MINIMAP;
        int y = Math.min(
            preferredY,
            Math.max(
                8,
                canvasHeight - PANEL_HEIGHT - 8
            )
        );
        y = Math.max(8, y);

        ModernUiRect panel =
            new ModernUiRect(
                x,
                y,
                PANEL_WIDTH,
                PANEL_HEIGHT
            );

        int columns =
            inventory == null
                ? 4
                : Math.max(1, inventory.baseWidth);
        int rows =
            inventory == null
                ? 7
                : Math.max(1, inventory.baseHeight);
        int marginX =
            inventory == null
                ? 4
                : inventory.invMarginX;
        int marginY =
            inventory == null
                ? 4
                : inventory.invMarginY;

        int gridWidth =
            columns * 32
                + Math.max(0, columns - 1) * marginX;
        int gridHeight =
            rows * 32
                + Math.max(0, rows - 1) * marginY;

        int maxGridWidth = panel.width - 24;
        int maxGridHeight = panel.height - 60;
        gridWidth = Math.min(gridWidth, maxGridWidth);
        gridHeight = Math.min(gridHeight, maxGridHeight);

        ModernUiRect title =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 10,
                panel.width - 24,
                22
            );

        ModernUiRect grid =
            new ModernUiRect(
                panel.centerX() - gridWidth / 2,
                panel.y + 50,
                gridWidth,
                gridHeight
            );

        return new ModernInventoryPanelLayout(
            panel,
            title,
            grid
        );
    }
}
