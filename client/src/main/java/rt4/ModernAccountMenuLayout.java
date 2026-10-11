package rt4;

/**
 * Geometry only for Create Account / account setup screens.
 */
public final class ModernAccountMenuLayout
    implements ModernTitleScreenLayout {

    public static final int PANEL_WIDTH = 560;
    public static final int PANEL_HEIGHT = 500;

    private final ModernUiRect panel;
    private final ModernUiRect logo;
    private final ModernUiRect title;
    private final ModernUiRect divider;
    private final ModernUiRect body;

    private ModernAccountMenuLayout(
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

    public static ModernAccountMenuLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(380, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(410, canvasHeight - 24)
        );
        int x = Math.max(8, (canvasWidth - width) / 2);
        int y = Math.max(
            8,
            Math.min(
                (canvasHeight - height) / 2 + 52,
                Math.max(8, canvasHeight - height - 8)
            )
        );

        ModernUiRect panel =
            new ModernUiRect(x, y, width, height);
        ModernUiRect logo = logo(panel, canvasWidth);
        ModernUiRect title =
            new ModernUiRect(x + 24, y + 20, width - 48, 28);
        ModernUiRect divider =
            new ModernUiRect(x + 24, y + 58, width - 48, 4);
        ModernUiRect body =
            new ModernUiRect(x + 34, y + 72, width - 68, 112);

        return new ModernAccountMenuLayout(
            panel,
            logo,
            title,
            divider,
            body
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
        return line(body, index, count, 18, 7);
    }

    @Override
    public ModernUiRect fieldRect(int index, int count) {
        int width = 260;
        int height = 34;
        int spacing = 52;
        int visible = Math.max(1, Math.min(5, count));
        int total = height + (visible - 1) * spacing;
        int start =
            panel.y + 205
                + Math.max(
                    0,
                    (panel.bottom() - 78 - (panel.y + 205) - total)
                        / 2
                );

        return new ModernUiRect(
            panel.centerX() - width / 2,
            start + index * spacing,
            width,
            height
        );
    }

    @Override
    public ModernUiRect fieldLabelRect(int index, int count) {
        ModernUiRect field = fieldRect(index, count);
        return new ModernUiRect(
            field.x - 18,
            field.y - 19,
            field.width + 36,
            17
        );
    }

    @Override
    public ModernUiRect actionRect(int index, int count) {
        int width = 210;
        int height = 30;
        int spacing = 42;
        int visible = Math.max(1, Math.min(6, count));
        int total = height + (visible - 1) * spacing;
        int start =
            panel.y + 220
                + Math.max(
                    0,
                    (panel.bottom() - 72 - (panel.y + 220) - total)
                        / 2
                );

        return new ModernUiRect(
            panel.centerX() - width / 2,
            start + index * spacing,
            width,
            height
        );
    }

    @Override
    public ModernUiRect navigationRect(int index, int count) {
        return navigation(panel, index, count);
    }

    private static ModernUiRect logo(
        ModernUiRect panel,
        int canvasWidth
    ) {
        int width =
            ModernMainMenuLayout.logoWidthForCanvas(canvasWidth);
        int height =
            ModernMainMenuLayout.logoHeightForWidth(width);
        return new ModernUiRect(
            panel.centerX() - width / 2,
            Math.max(8, panel.y - height - 18),
            width,
            height
        );
    }

    private static ModernUiRect line(
        ModernUiRect area,
        int index,
        int count,
        int height,
        int max
    ) {
        int visible = Math.max(1, Math.min(max, count));
        int start =
            area.y + Math.max(
                0,
                (area.height - visible * height) / 2
            );
        return new ModernUiRect(
            area.x,
            start + index * height,
            area.width,
            height
        );
    }

    static ModernUiRect navigation(
        ModernUiRect panel,
        int index,
        int count
    ) {
        int width = 150;
        int gap = 12;
        int total =
            count * width + Math.max(0, count - 1) * gap;
        int start = panel.centerX() - total / 2;
        return new ModernUiRect(
            start + index * (width + gap),
            panel.bottom() - 48,
            width,
            30
        );
    }
}
