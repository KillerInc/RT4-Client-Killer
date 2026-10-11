package rt4;

/**
 * Independent declarative geometry for Modern trade screens.
 */
public final class ModernTradeLayout {
    public static final int PANEL_WIDTH = 760;
    public static final int PANEL_HEIGHT = 500;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect leftPane;
    public final ModernUiRect rightPane;
    public final ModernUiRect messageArea;
    public final ModernUiRect actions;

    private ModernTradeLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect leftPane,
        ModernUiRect rightPane,
        ModernUiRect messageArea,
        ModernUiRect actions
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.leftPane = leftPane;
        this.rightPane = rightPane;
        this.messageArea = messageArea;
        this.actions = actions;
    }

    public static ModernTradeLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(500, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(360, canvasHeight - 48)
        );

        ModernUiRect panel =
            new ModernUiRect(
                Math.max(8, (canvasWidth - width) / 2),
                Math.max(8, (canvasHeight - height) / 2),
                width,
                height
            );

        ModernUiRect title =
            new ModernUiRect(
                panel.x + 18,
                panel.y + 12,
                panel.width - 36,
                26
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 18,
                panel.y + 46,
                panel.width - 36,
                4
            );

        int gap = 14;
        int paneWidth =
            (panel.width - 52 - gap) / 2;
        ModernUiRect leftPane =
            new ModernUiRect(
                panel.x + 20,
                panel.y + 64,
                paneWidth,
                panel.height - 144
            );
        ModernUiRect rightPane =
            new ModernUiRect(
                leftPane.right() + gap,
                leftPane.y,
                paneWidth,
                leftPane.height
            );

        ModernUiRect messageArea =
            new ModernUiRect(
                panel.x + 28,
                panel.y + 70,
                panel.width - 56,
                panel.height - 158
            );
        ModernUiRect actions =
            new ModernUiRect(
                panel.x + 20,
                panel.bottom() - 54,
                panel.width - 40,
                34
            );

        return new ModernTradeLayout(
            panel,
            title,
            divider,
            leftPane,
            rightPane,
            messageArea,
            actions
        );
    }

    public ModernUiRect pane(
        int index
    ) {
        return index <= 0
            ? leftPane
            : rightPane;
    }

    public ModernUiRect gridOrigin(
        Component inventory,
        int index
    ) {
        ModernUiRect pane = pane(index);

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
            pane.centerX() - width / 2,
            pane.y + 26,
            width,
            height
        );
    }

    public ModernUiRect paneTitle(
        int index
    ) {
        ModernUiRect pane = pane(index);
        return new ModernUiRect(
            pane.x + 6,
            pane.y + 2,
            pane.width - 12,
            20
        );
    }

    public ModernUiRect messageLine(
        int index,
        int count
    ) {
        int lineHeight = 20;
        int visible = Math.max(
            1,
            Math.min(
                Math.max(1, messageArea.height / lineHeight),
                count
            )
        );
        int total = visible * lineHeight;
        int start =
            messageArea.y
                + Math.max(
                    0,
                    (messageArea.height - total) / 2
                );

        return new ModernUiRect(
            messageArea.x,
            start + Math.max(0, index) * lineHeight,
            messageArea.width,
            lineHeight
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(6, count));
        int gap = 8;
        int width =
            Math.max(
                84,
                (actions.width
                    - Math.max(0, visible - 1) * gap)
                    / visible
            );

        return new ModernUiRect(
            actions.x + Math.max(0, index) * (width + gap),
            actions.y,
            width,
            actions.height
        );
    }
}
