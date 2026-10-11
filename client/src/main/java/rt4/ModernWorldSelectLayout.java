package rt4;

/**
 * Geometry only for the title-screen world selector.
 */
public final class ModernWorldSelectLayout
    implements ModernTitleScreenLayout {

    public static final int PANEL_WIDTH = 620;
    public static final int PANEL_HEIGHT = 500;
    public static final int ACTION_HEIGHT = 28;

    private final ModernUiRect panel;
    private final ModernUiRect logo;
    private final ModernUiRect title;
    private final ModernUiRect divider;
    private final ModernUiRect body;

    private ModernWorldSelectLayout(
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

    public static ModernWorldSelectLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(420, canvasWidth - 24)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(410, canvasHeight - 24)
        );
        int x = Math.max(8, (canvasWidth - width) / 2);
        int y = Math.max(
            8,
            Math.min(
                (canvasHeight - height) / 2 + 46,
                Math.max(8, canvasHeight - height - 8)
            )
        );

        ModernUiRect panel =
            new ModernUiRect(x, y, width, height);
        int logoWidth =
            ModernMainMenuLayout.logoWidthForCanvas(canvasWidth);
        int logoHeight =
            ModernMainMenuLayout.logoHeightForWidth(logoWidth);
        ModernUiRect logo =
            new ModernUiRect(
                panel.centerX() - logoWidth / 2,
                Math.max(8, y - logoHeight - 18),
                logoWidth,
                logoHeight
            );

        return new ModernWorldSelectLayout(
            panel,
            logo,
            new ModernUiRect(x + 24, y + 20, width - 48, 28),
            new ModernUiRect(x + 24, y + 58, width - 48, 4),
            new ModernUiRect(x + 34, y + 70, width - 68, 42)
        );
    }

    @Override
    public ModernUiRect panel() {
        return panel;
    }

    @Override
    public ModernUiRect logo() {
        return logo;
    }

    @Override
    public ModernUiRect title() {
        return title;
    }

    @Override
    public ModernUiRect divider() {
        return divider;
    }

    @Override
    public ModernUiRect body() {
        return body;
    }

    @Override
    public ModernUiRect bodyLine(int index, int count) {
        return new ModernUiRect(
            body.x,
            body.y + index * 18,
            body.width,
            18
        );
    }

    @Override
    public ModernUiRect fieldRect(int index, int count) {
        return new ModernUiRect(
            panel.centerX() - 130,
            panel.y + 118 + index * 48,
            260,
            34
        );
    }

    @Override
    public ModernUiRect fieldLabelRect(int index, int count) {
        ModernUiRect field = fieldRect(index, count);
        return new ModernUiRect(
            field.x,
            field.y - 18,
            field.width,
            16
        );
    }

    @Override
    public ModernUiRect actionRect(int index, int count) {
        int columns = 3;
        int gap = 10;
        int width =
            Math.max(
                130,
                Math.min(
                    170,
                    (panel.width - 72 - gap * 2) / columns
                )
            );
        int used =
            columns * width + (columns - 1) * gap;
        int startX = panel.centerX() - used / 2;
        int row = index / columns;
        int column = index % columns;

        return new ModernUiRect(
            startX + column * (width + gap),
            panel.y + 122 + row * 36,
            width,
            ACTION_HEIGHT
        );
    }

    @Override
    public ModernUiRect navigationRect(int index, int count) {
        return ModernAccountMenuLayout.navigation(
            panel,
            index,
            count
        );
    }
}
