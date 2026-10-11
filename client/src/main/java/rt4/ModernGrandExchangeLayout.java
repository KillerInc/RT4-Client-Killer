package rt4;

/**
 * Independent declarative geometry for the Modern Grand Exchange family.
 *
 * Vanilla GE components provide state/actions only. No cache-era component
 * coordinates are used here.
 */
public final class ModernGrandExchangeLayout {
    public static final int PANEL_WIDTH = 820;
    public static final int PANEL_HEIGHT = 560;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect info;
    public final ModernUiRect content;
    public final ModernUiRect footer;

    private ModernGrandExchangeLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect info,
        ModernUiRect content,
        ModernUiRect footer
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.info = info;
        this.content = content;
        this.footer = footer;
    }

    public static ModernGrandExchangeLayout create(
        int canvasWidth,
        int canvasHeight,
        boolean sidePanel
    ) {
        int targetWidth = sidePanel ? 330 : PANEL_WIDTH;
        int targetHeight = sidePanel ? 500 : PANEL_HEIGHT;

        int width = Math.min(
            targetWidth,
            Math.max(sidePanel ? 250 : 500, canvasWidth - 32)
        );
        int height = Math.min(
            targetHeight,
            Math.max(sidePanel ? 330 : 380, canvasHeight - 48)
        );

        int x = sidePanel
            ? Math.max(8, canvasWidth - width - 18)
            : Math.max(8, (canvasWidth - width) / 2);
        int y = Math.max(8, (canvasHeight - height) / 2);

        ModernUiRect panel = new ModernUiRect(x, y, width, height);
        ModernUiRect title = new ModernUiRect(
            panel.x + 18,
            panel.y + 12,
            panel.width - 36,
            26
        );
        ModernUiRect divider = new ModernUiRect(
            panel.x + 18,
            panel.y + 46,
            panel.width - 36,
            4
        );
        ModernUiRect info = new ModernUiRect(
            panel.x + 22,
            panel.y + 56,
            panel.width - 44,
            sidePanel ? 34 : 42
        );
        ModernUiRect footer = new ModernUiRect(
            panel.x + 20,
            panel.bottom() - 54,
            panel.width - 40,
            34
        );
        ModernUiRect content = new ModernUiRect(
            panel.x + 20,
            info.bottom() + 8,
            panel.width - 40,
            Math.max(80, footer.y - info.bottom() - 18)
        );

        return new ModernGrandExchangeLayout(
            panel,
            title,
            divider,
            info,
            content,
            footer
        );
    }

    public ModernUiRect offerCard(int index) {
        int columns = content.width >= 620 ? 3 : 2;
        int rows = 3;
        int gap = 10;
        int width =
            (content.width - gap * (columns - 1)) / columns;
        int height =
            (content.height - gap * (rows - 1)) / rows;

        int column = Math.max(0, index) % columns;
        int row = Math.max(0, index) / columns;

        return new ModernUiRect(
            content.x + column * (width + gap),
            content.y + row * (height + gap),
            width,
            height
        );
    }

    public ModernUiRect offerAction(
        int offerIndex,
        int actionIndex
    ) {
        ModernUiRect card = offerCard(offerIndex);
        int gap = 6;
        int width = Math.max(42, (card.width - 18 - gap) / 2);
        return new ModernUiRect(
            card.x + 6 + actionIndex * (width + gap),
            card.bottom() - 34,
            width,
            26
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(8, count));
        int columns = visible <= 4 ? visible : 4;
        int rows = (visible + columns - 1) / columns;
        int gap = 7;
        int height = rows <= 1 ? footer.height : 28;
        int width =
            Math.max(
                70,
                (footer.width - gap * (columns - 1)) / columns
            );
        int column = Math.max(0, index) % columns;
        int row = Math.max(0, index) / columns;

        int totalHeight = rows * height + Math.max(0, rows - 1) * 5;
        int y = footer.y - Math.max(0, totalHeight - footer.height);

        return new ModernUiRect(
            footer.x + column * (width + gap),
            y + row * (height + 5),
            width,
            height
        );
    }

    public ModernUiRect textLine(
        int index,
        int count
    ) {
        int lineHeight = 20;
        int visible = Math.max(
            1,
            Math.min(
                Math.max(1, content.height / lineHeight),
                count
            )
        );
        return new ModernUiRect(
            content.x + 8,
            content.y + 4 + Math.max(0, index) * lineHeight,
            content.width - 16,
            lineHeight
        );
    }

    public ModernUiRect inventoryGrid(Component inventory) {
        int columns = inventory == null ? 4 : Math.max(1, inventory.baseWidth);
        int rows = inventory == null ? 7 : Math.max(1, inventory.baseHeight);
        int marginX = inventory == null ? 4 : inventory.invMarginX;
        int marginY = inventory == null ? 4 : inventory.invMarginY;
        int width =
            columns * 32 + Math.max(0, columns - 1) * marginX;
        int height =
            rows * 32 + Math.max(0, rows - 1) * marginY;

        return new ModernUiRect(
            content.centerX() - width / 2,
            content.y + 8,
            width,
            height
        );
    }
}
