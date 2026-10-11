package rt4;

/**
 * Independent declarative geometry for the Modern bank.
 *
 * Interface 762/767 use the main bank window. Interface 763 is the player's
 * inventory tray and has its own geometry so either side can be edited without
 * moving the other.
 */
public final class ModernBankLayout {
    public static final int EDGE = 16;
    public static final int GAP = 14;
    public static final int INVENTORY_WIDTH = 236;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect toolbar;
    public final ModernUiRect gridViewport;
    public final boolean inventoryTray;

    private ModernBankLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect toolbar,
        ModernUiRect gridViewport,
        boolean inventoryTray
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.toolbar = toolbar;
        this.gridViewport = gridViewport;
        this.inventoryTray = inventoryTray;
    }

    public static ModernBankLayout create(
        int canvasWidth,
        int canvasHeight,
        int interfaceId
    ) {
        boolean tray = interfaceId == 763;

        if (tray) {
            int width = Math.min(
                INVENTORY_WIDTH,
                Math.max(200, canvasWidth / 3)
            );
            int height = Math.min(
                360,
                Math.max(280, canvasHeight - 120)
            );
            ModernUiRect panel = new ModernUiRect(
                Math.max(EDGE, canvasWidth - EDGE - width),
                Math.max(EDGE, (canvasHeight - height) / 2),
                width,
                height
            );
            ModernUiRect title = new ModernUiRect(
                panel.x + 12,
                panel.y + 10,
                panel.width - 24,
                22
            );
            ModernUiRect divider = new ModernUiRect(
                panel.x + 12,
                panel.y + 38,
                panel.width - 24,
                4
            );
            ModernUiRect toolbar = new ModernUiRect(
                panel.x + 10,
                panel.bottom() - 44,
                panel.width - 20,
                30
            );
            ModernUiRect grid = new ModernUiRect(
                panel.x + 10,
                panel.y + 50,
                panel.width - 20,
                panel.height - 104
            );
            return new ModernBankLayout(
                panel,
                title,
                divider,
                toolbar,
                grid,
                true
            );
        }

        int reserved =
            INVENTORY_WIDTH + GAP + EDGE * 2;
        int width = Math.min(
            720,
            Math.max(480, canvasWidth - reserved)
        );
        int height = Math.min(
            560,
            Math.max(360, canvasHeight - 80)
        );
        ModernUiRect panel = new ModernUiRect(
            EDGE,
            Math.max(EDGE, (canvasHeight - height) / 2),
            width,
            height
        );
        ModernUiRect title = new ModernUiRect(
            panel.x + 16,
            panel.y + 12,
            panel.width - 32,
            24
        );
        ModernUiRect divider = new ModernUiRect(
            panel.x + 16,
            panel.y + 44,
            panel.width - 32,
            4
        );
        ModernUiRect toolbar = new ModernUiRect(
            panel.x + 14,
            panel.y + 56,
            panel.width - 28,
            32
        );
        ModernUiRect grid = new ModernUiRect(
            panel.x + 16,
            panel.y + 100,
            panel.width - 40,
            panel.height - 118
        );

        return new ModernBankLayout(
            panel,
            title,
            divider,
            toolbar,
            grid,
            false
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(8, count));
        int gap = 6;
        int width = Math.max(
            64,
            (toolbar.width
                - Math.max(0, visible - 1) * gap)
                / visible
        );

        return new ModernUiRect(
            toolbar.x + Math.max(0, index) * (width + gap),
            toolbar.y,
            width,
            toolbar.height
        );
    }

    public ModernUiRect gridOrigin(
        Component inventory,
        int scrollY
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
            gridViewport.y - Math.max(0, scrollY),
            width,
            height
        );
    }

    public ModernUiRect scrollbarTrack() {
        return new ModernUiRect(
            gridViewport.right() + 4,
            gridViewport.y,
            12,
            gridViewport.height
        );
    }
}
