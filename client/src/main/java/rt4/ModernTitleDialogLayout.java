package rt4;

/**
 * Geometry only for small remaining title-screen notices/confirmations.
 */
public final class ModernTitleDialogLayout
    implements ModernTitleScreenLayout {

    public static final int PANEL_WIDTH = 500;
    public static final int PANEL_HEIGHT = 360;
    public static final int ACTION_WIDTH = 180;
    public static final int ACTION_HEIGHT = 30;

    private final ModernUiRect panel;
    private final ModernUiRect logo;
    private final ModernUiRect title;
    private final ModernUiRect divider;
    private final ModernUiRect body;

    private ModernTitleDialogLayout(
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

    public static ModernTitleDialogLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(360, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(300, canvasHeight - 32)
        );
        int x = Math.max(8, (canvasWidth - width) / 2);
        int y = Math.max(
            8,
            (canvasHeight - height) / 2 + 40
        );
        y = Math.min(
            y,
            Math.max(8, canvasHeight - height - 8)
        );

        ModernUiRect panel =
            new ModernUiRect(x, y, width, height);
        int logoWidth =
            ModernMainMenuLayout.logoWidthForCanvas(canvasWidth);
        int logoHeight =
            ModernMainMenuLayout.logoHeightForWidth(logoWidth);

        return new ModernTitleDialogLayout(
            panel,
            new ModernUiRect(
                panel.centerX() - logoWidth / 2,
                Math.max(8, y - logoHeight - 18),
                logoWidth,
                logoHeight
            ),
            new ModernUiRect(x + 24, y + 20, width - 48, 28),
            new ModernUiRect(x + 24, y + 58, width - 48, 4),
            new ModernUiRect(x + 34, y + 78, width - 68, 118)
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
        int visible = Math.max(1, Math.min(6, count));
        int h = 19;
        int start =
            body.y + Math.max(
                0,
                (body.height - visible * h) / 2
            );
        return new ModernUiRect(
            body.x,
            start + index * h,
            body.width,
            h
        );
    }

    @Override
    public ModernUiRect fieldRect(int index, int count) {
        return new ModernUiRect(
            panel.centerX() - 130,
            panel.y + 205 + index * 46,
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
        int width = ACTION_WIDTH;
        int gap = 12;
        int visible = Math.max(1, Math.min(2, count));
        int total =
            visible * width + (visible - 1) * gap;
        int start = panel.centerX() - total / 2;
        return new ModernUiRect(
            start + index * (width + gap),
            panel.bottom() - 92,
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
