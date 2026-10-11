package rt4;

/**
 * Declarative layout for the remaining title/login menus.
 *
 * This is intentionally generic: Create Account steps, world switching and
 * small confirmation/info screens all consume the same Modern geometry while
 * their original Components remain action/state backends only.
 */
public final class ModernTitleMenuLayout {
    public static final int PANEL_WIDTH = 560;
    public static final int PANEL_HEIGHT = 500;
    public static final int PANEL_VERTICAL_BIAS = 52;

    public static final int BUTTON_WIDTH = 210;
    public static final int BUTTON_HEIGHT = 30;
    public static final int FIELD_WIDTH = 260;
    public static final int FIELD_HEIGHT = 34;

    public final ModernUiRect panel;
    public final ModernUiRect logo;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect body;

    private ModernTitleMenuLayout(
        ModernUiRect panel,
        ModernUiRect logo,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect body
    ) {
        this.panel = panel;
        this.logo = logo;
        this.title = title;
        this.divider = divider;
        this.body = body;
    }

    public static ModernTitleMenuLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int panelWidth = Math.min(
            PANEL_WIDTH,
            Math.max(360, canvasWidth - 32)
        );
        int panelHeight = Math.min(
            PANEL_HEIGHT,
            Math.max(390, canvasHeight - 24)
        );

        int panelX = Math.max(
            8,
            (canvasWidth - panelWidth) / 2
        );
        int panelY =
            (canvasHeight - panelHeight) / 2
                + PANEL_VERTICAL_BIAS;
        panelY = Math.max(
            8,
            Math.min(
                panelY,
                Math.max(8, canvasHeight - panelHeight - 8)
            )
        );

        ModernUiRect panel =
            new ModernUiRect(
                panelX,
                panelY,
                panelWidth,
                panelHeight
            );

        int logoWidth =
            ModernMainMenuLayout.logoWidthForCanvas(canvasWidth);
        int logoHeight =
            ModernMainMenuLayout.logoHeightForWidth(logoWidth);
        ModernUiRect logo =
            new ModernUiRect(
                panel.centerX() - logoWidth / 2,
                Math.max(8, panelY - logoHeight - 18),
                logoWidth,
                logoHeight
            );

        ModernUiRect title =
            new ModernUiRect(
                panelX + 24,
                panelY + 20,
                panelWidth - 48,
                28
            );

        ModernUiRect divider =
            new ModernUiRect(
                panelX + 24,
                panelY + 58,
                panelWidth - 48,
                4
            );

        ModernUiRect body =
            new ModernUiRect(
                panelX + 34,
                panelY + 72,
                panelWidth - 68,
                126
            );

        return new ModernTitleMenuLayout(
            panel,
            logo,
            title,
            divider,
            body
        );
    }

    public ModernUiRect bodyLine(int index, int count) {
        int visible = Math.max(1, Math.min(7, count));
        int lineHeight = 18;
        int total = visible * lineHeight;
        int startY =
            body.y + Math.max(0, (body.height - total) / 2);

        return new ModernUiRect(
            body.x,
            startY + Math.max(0, index) * lineHeight,
            body.width,
            lineHeight
        );
    }

    public ModernUiRect fieldRect(int index, int count) {
        int fieldCount = Math.max(1, Math.min(5, count));
        int spacing = 48;
        int total =
            FIELD_HEIGHT
                + Math.max(0, fieldCount - 1) * spacing;
        int startY =
            panel.y + 205
                + Math.max(
                    0,
                    (panel.bottom() - 70 - (panel.y + 205) - total) / 2
                );

        return new ModernUiRect(
            panel.centerX() - FIELD_WIDTH / 2,
            startY + Math.max(0, index) * spacing,
            FIELD_WIDTH,
            FIELD_HEIGHT
        );
    }

    public ModernUiRect fieldLabelRect(int index, int count) {
        ModernUiRect field = fieldRect(index, count);
        return new ModernUiRect(
            field.x - 20,
            field.y - 19,
            field.width + 40,
            17
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count,
        boolean worldGrid
    ) {
        if (worldGrid) {
            int columns = 3;
            int width =
                Math.max(
                    130,
                    Math.min(
                        160,
                        (panel.width - 76) / columns
                    )
                );
            int gap = 10;
            int usedWidth =
                columns * width + (columns - 1) * gap;
            int startX =
                panel.centerX() - usedWidth / 2;
            int row = Math.max(0, index) / columns;
            int column = Math.max(0, index) % columns;
            return new ModernUiRect(
                startX + column * (width + gap),
                panel.y + 216 + row * 38,
                width,
                BUTTON_HEIGHT
            );
        }

        int actionCount = Math.max(1, Math.min(6, count));
        int spacing = 42;
        int total =
            BUTTON_HEIGHT
                + Math.max(0, actionCount - 1) * spacing;
        int availableTop = panel.y + 220;
        int availableBottom = panel.bottom() - 24;
        int startY =
            availableTop
                + Math.max(
                    0,
                    (availableBottom - availableTop - total) / 2
                );

        return new ModernUiRect(
            panel.centerX() - BUTTON_WIDTH / 2,
            startY + Math.max(0, index) * spacing,
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
    }

    public ModernUiRect navigationRect(int index, int count) {
        int width = 150;
        int gap = 12;
        int total =
            count * width + Math.max(0, count - 1) * gap;
        int startX = panel.centerX() - total / 2;

        return new ModernUiRect(
            startX + index * (width + gap),
            panel.bottom() - 48,
            width,
            30
        );
    }
}
