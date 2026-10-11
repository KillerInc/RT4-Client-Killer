package rt4;

/**
 * Independent Modern layout for shops and the player's shop inventory.
 */
public final class ModernShopLayout {
    public static final int EDGE = 16;
    public static final int GAP = 14;
    public static final int INVENTORY_WIDTH = 236;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect info;
    public final ModernUiRect gridViewport;
    public final ModernUiRect toolbar;
    public final boolean inventoryTray;

    private ModernShopLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect info,
        ModernUiRect gridViewport,
        ModernUiRect toolbar,
        boolean inventoryTray
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.info = info;
        this.gridViewport = gridViewport;
        this.toolbar = toolbar;
        this.inventoryTray = inventoryTray;
    }

    public static ModernShopLayout create(
        int canvasWidth,
        int canvasHeight,
        int interfaceId
    ) {
        boolean tray = interfaceId == 621;

        if (tray) {
            int width = Math.min(
                INVENTORY_WIDTH,
                Math.max(200, canvasWidth / 3)
            );
            int height = Math.min(
                350,
                Math.max(280, canvasHeight - 120)
            );
            ModernUiRect panel =
                new ModernUiRect(
                    Math.max(EDGE, canvasWidth - EDGE - width),
                    Math.max(EDGE, (canvasHeight - height) / 2),
                    width,
                    height
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
            ModernUiRect info =
                new ModernUiRect(
                    panel.x + 12,
                    panel.y + 48,
                    panel.width - 24,
                    20
                );
            ModernUiRect grid =
                new ModernUiRect(
                    panel.x + 10,
                    panel.y + 76,
                    panel.width - 20,
                    panel.height - 158
                );
            ModernUiRect toolbar =
                new ModernUiRect(
                    panel.x + 10,
                    panel.bottom() - 74,
                    panel.width - 20,
                    64
                );
            return new ModernShopLayout(
                panel,
                title,
                divider,
                info,
                grid,
                toolbar,
                true
            );
        }

        int reserved =
            INVENTORY_WIDTH + GAP + EDGE * 2;
        int width = Math.min(
            650,
            Math.max(460, canvasWidth - reserved)
        );
        int height = Math.min(
            500,
            Math.max(340, canvasHeight - 90)
        );
        ModernUiRect panel =
            new ModernUiRect(
                EDGE,
                Math.max(EDGE, (canvasHeight - height) / 2),
                width,
                height
            );
        ModernUiRect title =
            new ModernUiRect(
                panel.x + 16,
                panel.y + 12,
                panel.width - 32,
                24
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 16,
                panel.y + 44,
                panel.width - 32,
                4
            );
        ModernUiRect info =
            new ModernUiRect(
                panel.x + 16,
                panel.y + 54,
                panel.width - 32,
                24
            );
        ModernUiRect toolbar =
            new ModernUiRect(
                panel.x + 14,
                panel.bottom() - 78,
                panel.width - 28,
                64
            );
        ModernUiRect grid =
            new ModernUiRect(
                panel.x + 16,
                panel.y + 88,
                panel.width - 32,
                panel.height - 180
            );

        return new ModernShopLayout(
            panel,
            title,
            divider,
            info,
            grid,
            toolbar,
            false
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(6, count));
        int columns = Math.min(3, visible);
        int rows =
            (visible + columns - 1) / columns;
        int gap = 6;
        int width =
            Math.max(
                1,
                (toolbar.width
                    - Math.max(0, columns - 1) * gap)
                    / columns
            );
        int height =
            rows <= 1
                ? Math.min(30, toolbar.height)
                : Math.max(
                    1,
                    (toolbar.height - gap) / 2
                );
        int row = Math.max(0, index) / columns;
        int column = Math.max(0, index) % columns;
        int usedHeight =
            rows <= 1
                ? height
                : height * 2 + gap;
        int startY =
            toolbar.y
                + Math.max(
                    0,
                    (toolbar.height - usedHeight) / 2
                );

        return new ModernUiRect(
            toolbar.x + column * (width + gap),
            startY + row * (height + gap),
            width,
            height
        );
    }

    public ModernUiRect gridOrigin(
        Component inventory
    ) {
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

        int width =
            columns * 32
                + Math.max(0, columns - 1) * marginX;
        int height =
            rows * 32
                + Math.max(0, rows - 1) * marginY;

        return new ModernUiRect(
            gridViewport.centerX() - width / 2,
            gridViewport.y,
            width,
            height
        );
    }
}
