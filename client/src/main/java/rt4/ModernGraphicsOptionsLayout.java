package rt4;

/**
 * Declarative geometry for the Modern Graphics Options screen.
 *
 * This class is the only place that decides where Graphics Options controls
 * live. Rendering and input both consume these rectangles.
 */
public final class ModernGraphicsOptionsLayout {
    public final int panelX;
    public final int panelY;
    public final int panelWidth;
    public final int panelHeight;
    public final int centerX;

    private ModernGraphicsOptionsLayout(
        int panelX,
        int panelY,
        int panelWidth,
        int panelHeight
    ) {
        this.panelX = panelX;
        this.panelY = panelY;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.centerX = panelX + panelWidth / 2;
    }

    public static ModernGraphicsOptionsLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int panelWidth = ModernUiMetrics.GRAPHICS_PANEL_WIDTH;
        int panelHeight = ModernUiMetrics.GRAPHICS_PANEL_HEIGHT;
        int panelX = Math.max(
            8,
            (canvasWidth - panelWidth) / 2
        );
        int panelY = Math.max(
            8,
            (canvasHeight - panelHeight) / 2
        );

        return new ModernGraphicsOptionsLayout(
            panelX,
            panelY,
            panelWidth,
            panelHeight
        );
    }

    public ModernUiRect panel() {
        return new ModernUiRect(
            panelX,
            panelY,
            panelWidth,
            panelHeight
        );
    }

    public ModernUiRect title() {
        return new ModernUiRect(
            panelX,
            panelY + 22,
            panelWidth,
            22
        );
    }

    public ModernUiRect topDivider() {
        return new ModernUiRect(
            panelX + ModernUiMetrics.GRAPHICS_PANEL_INSET,
            panelY + 146,
            panelWidth - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );
    }

    public ModernUiRect bottomDivider() {
        return new ModernUiRect(
            panelX + ModernUiMetrics.GRAPHICS_PANEL_INSET,
            panelY + 360,
            panelWidth - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );
    }

    public ModernUiRect displaySectionLabel() {
        return new ModernUiRect(
            panelX + 48,
            panelY + 40,
            180,
            18
        );
    }

    public ModernUiRect advancedSectionLabel() {
        return new ModernUiRect(
            panelX + 48,
            panelY + 160,
            180,
            18
        );
    }

    public int displayModeCenter(int index) {
        return centerX - 225
            + Math.max(0, Math.min(3, index)) * 150;
    }

    public ModernUiRect displayModeDetail(int index) {
        return new ModernUiRect(
            displayModeCenter(index) - 65,
            panelY + 96,
            130,
            34
        );
    }

    public ModernUiRect controlLabel(int column, int row) {
        ModernUiRect control = control(column, row);
        return new ModernUiRect(
            control.x - 8,
            labelY(row),
            control.width + 16,
            16
        );
    }

    public ModernUiRect control(int column, int row) {
        int center =
            centerX
                + ModernUiMetrics.ADVANCED_FIRST_COLUMN_OFFSET
                + column
                    * ModernUiMetrics.ADVANCED_COLUMN_SPACING;
        return new ModernUiRect(
            ModernUiMetrics.centeredX(
                center,
                ModernUiMetrics.CONTROL_WIDTH
            ),
            panelY
                + ModernUiMetrics.ADVANCED_CONTROL_Y_OFFSET
                + row
                    * ModernUiMetrics.ADVANCED_ROW_SPACING,
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
    }

    public int labelY(int row) {
        return panelY
            + ModernUiMetrics.ADVANCED_LABEL_Y_OFFSET
            + row * ModernUiMetrics.ADVANCED_ROW_SPACING;
    }

    public ModernUiRect displayModeButton(int index) {
        int center = displayModeCenter(index);
        return new ModernUiRect(
            ModernUiMetrics.centeredX(
                center,
                ModernUiMetrics.DISPLAY_BUTTON_WIDTH
            ),
            panelY
                + ModernUiMetrics.DISPLAY_BUTTON_Y_OFFSET
                + 36,
            ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
            ModernUiMetrics.DISPLAY_BUTTON_HEIGHT
        );
    }

    public ModernUiRect resolution() {
        return new ModernUiRect(
            ModernUiMetrics.centeredX(
                centerX + 225,
                ModernUiMetrics.CONTROL_WIDTH
            ),
            panelY + 128,
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
    }

    public ModernUiRect mainMenu() {
        return new ModernUiRect(
            ModernUiMetrics.centeredX(
                centerX,
                ModernUiMetrics.NAV_BUTTON_WIDTH
            ),
            panelY + ModernUiMetrics.NAV_BUTTON_Y_OFFSET,
            ModernUiMetrics.NAV_BUTTON_WIDTH,
            ModernUiMetrics.NAV_BUTTON_HEIGHT
        );
    }

    public ModernUiRect styleEditor() {
        return new ModernUiRect(
            panelX + panelWidth
                - ModernUiMetrics.GRAPHICS_PANEL_INSET
                - ModernUiMetrics.CONTROL_WIDTH,
            panelY + ModernUiMetrics.NAV_BUTTON_Y_OFFSET,
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.NAV_BUTTON_HEIGHT
        );
    }
}
