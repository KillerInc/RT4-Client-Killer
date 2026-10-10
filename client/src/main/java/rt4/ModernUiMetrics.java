package rt4;

/**
 * Shared Modern UI design metrics.
 *
 * Keep visual dimensions and typography here so renderers, overlays and the
 * vector-cache warmup plan use the same values. These are logical pixels at
 * the default Modern UI scale.
 */
public final class ModernUiMetrics {
    public static final int TEXT_PRIMARY = 0xE8DDC4;
    public static final int TEXT_ACCENT = 0xFFF4D1;
    public static final int TEXT_MUTED = 0xAFA184;

    public static final float FONT_TITLE = 16.0F;
    public static final float FONT_SECTION = 13.0F;
    public static final float FONT_LABEL = 12.0F;
    public static final float FONT_CONTROL = 11.0F;
    public static final float FONT_BUTTON = 13.0F;
    public static final float FONT_MAIN_MENU_BUTTON = 13.0F;
    public static final float FONT_MAIN_MENU_SUBTITLE = 13.0F;
    public static final float FONT_MAIN_MENU_DETAIL = 12.0F;

    public static final int GRAPHICS_PANEL_WIDTH = 690;
    public static final int GRAPHICS_PANEL_HEIGHT = 385;
    public static final int GRAPHICS_PANEL_INSET = 18;

    public static final int DISPLAY_BUTTON_WIDTH = 96;
    public static final int DISPLAY_BUTTON_HEIGHT = 28;
    public static final int DISPLAY_BUTTON_Y_OFFSET = 22;

    public static final int CONTROL_WIDTH = 112;
    public static final int CONTROL_HEIGHT = 22;
    public static final int CONTROL_TEXT_PAD_X = 6;
    public static final int CONTROL_ARROW_RESERVED = 20;

    public static final int NAV_BUTTON_WIDTH = 156;
    public static final int NAV_BUTTON_HEIGHT = 28;
    public static final int NAV_BUTTON_Y_OFFSET = 318;

    public static final int SLIDER_HEIGHT = 20;
    public static final int SLIDER_KNOB_WIDTH = 13;
    public static final int SLIDER_KNOB_HEIGHT = 18;

    public static final int DROPDOWN_ARROW_WIDTH = 9;
    public static final int DROPDOWN_ARROW_HEIGHT = 6;

    public static final int MAIN_MENU_BUTTON_WIDTH = 160;
    public static final int MAIN_MENU_BUTTON_HEIGHT = 28;

    private ModernUiMetrics() {
    }

    public static int centeredX(int centerX, int width) {
        return centerX - width / 2;
    }

    public static int centeredY(int centerY, int height) {
        return centerY - height / 2;
    }
}
