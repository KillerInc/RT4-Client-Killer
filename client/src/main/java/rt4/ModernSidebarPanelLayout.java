package rt4;

/**
 * Declarative layout for Modern in-game sidebar tabs.
 *
 * Inventory keeps its dedicated layout. The remaining tab interfaces use this
 * common shell while each interface supplies only content/state/action data.
 */
public final class ModernSidebarPanelLayout {
    public static final int PANEL_WIDTH = 240;
    public static final int PANEL_HEIGHT = 360;
    public static final int GAP_BELOW_MINIMAP = 14;

    public static final int TILE_SIZE = 46;
    public static final int TILE_GAP = 6;
    public static final int TILE_COLUMNS = 4;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect content;

    private ModernSidebarPanelLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect content
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.content = content;
    }

    public static ModernSidebarPanelLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        ModernGameFrameLayout frame =
            ModernGameFrameLayout.create(
                canvasWidth,
                canvasHeight
            );

        int panelWidth = Math.min(
            PANEL_WIDTH,
            Math.max(196, canvasWidth - 16)
        );
        int panelHeight = Math.min(
            PANEL_HEIGHT,
            Math.max(260, canvasHeight - 16)
        );

        int x = Math.max(
            8,
            canvasWidth
                - ModernGameFrameLayout.EDGE
                - panelWidth
        );

        int preferredY =
            frame.minimapFrame.bottom()
                + GAP_BELOW_MINIMAP;
        int y = Math.min(
            preferredY,
            Math.max(
                8,
                canvasHeight - panelHeight - 8
            )
        );
        y = Math.max(8, y);

        ModernUiRect panel =
            new ModernUiRect(
                x,
                y,
                panelWidth,
                panelHeight
            );
        ModernUiRect title =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 10,
                panel.width - 24,
                22
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 38,
                panel.width - 24,
                4
            );
        ModernUiRect content =
            new ModernUiRect(
                panel.x + 10,
                panel.y + 48,
                panel.width - 20,
                panel.height - 58
            );

        return new ModernSidebarPanelLayout(
            panel,
            title,
            divider,
            content
        );
    }

    public ModernUiRect tile(int index) {
        int columns = Math.max(
            1,
            Math.min(
                TILE_COLUMNS,
                (content.width + TILE_GAP)
                    / (TILE_SIZE + TILE_GAP)
            )
        );
        int usedWidth =
            columns * TILE_SIZE
                + Math.max(0, columns - 1) * TILE_GAP;
        int startX =
            content.centerX() - usedWidth / 2;

        int row = Math.max(0, index) / columns;
        int column = Math.max(0, index) % columns;

        return new ModernUiRect(
            startX + column * (TILE_SIZE + TILE_GAP),
            content.y + row * (TILE_SIZE + TILE_GAP),
            TILE_SIZE,
            TILE_SIZE
        );
    }

    public int visibleTileCapacity() {
        int columns = Math.max(
            1,
            Math.min(
                TILE_COLUMNS,
                (content.width + TILE_GAP)
                    / (TILE_SIZE + TILE_GAP)
            )
        );
        int rows = Math.max(
            1,
            (content.height + TILE_GAP)
                / (TILE_SIZE + TILE_GAP)
        );
        return columns * rows;
    }

    public ModernUiRect listRow(int index) {
        int height = 24;
        return new ModernUiRect(
            content.x,
            content.y + Math.max(0, index) * height,
            content.width,
            height
        );
    }

    public ModernUiRect inventoryGrid(Component inventory) {
        if (inventory == null) {
            return content;
        }

        int columns = Math.max(1, inventory.baseWidth);
        int rows = Math.max(1, inventory.baseHeight);
        int width =
            columns * 32
                + Math.max(0, columns - 1)
                    * inventory.invMarginX;
        int height =
            rows * 32
                + Math.max(0, rows - 1)
                    * inventory.invMarginY;

        width = Math.min(width, content.width);
        height = Math.min(height, content.height);

        return new ModernUiRect(
            content.centerX() - width / 2,
            content.y,
            width,
            height
        );
    }
}
