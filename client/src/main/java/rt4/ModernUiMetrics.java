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
    public static final int TEXT_PARCHMENT = 0x5A351C;
    public static final int TEXT_GOLD = 0xF1D68A;

    public static final int SURFACE_DARK = 0x17130F;
    public static final int SURFACE_TRACK = 0x332A20;
    public static final int BORDER = 0xB59A68;
    public static final int BORDER_LIGHT = 0xD8C08D;

    public static final float FONT_TITLE = 16.0F;
    public static final float FONT_SECTION = 13.0F;
    public static final float FONT_LABEL = 12.0F;
    public static final float FONT_CONTROL = 11.0F;
    public static final float FONT_DROPDOWN = 13.0F;
    public static final float FONT_BUTTON = 13.0F;
    public static final float FONT_MAIN_MENU_BUTTON = 13.0F;
    public static final float FONT_MAIN_MENU_SUBTITLE = 13.0F;
    public static final float FONT_MAIN_MENU_DETAIL = 12.0F;
    public static final float FONT_MAIN_MENU_EDITION = 33.0F;

    public static final int GRAPHICS_PANEL_WIDTH = 730;
    public static final int GRAPHICS_PANEL_HEIGHT = 420;
    public static final int GRAPHICS_PANEL_INSET = 22;
    public static final int GRAPHICS_PANEL_Y_OFFSET = 38;
    public static final int GRAPHICS_TOP_DIVIDER_Y_OFFSET = 114;
    public static final int GRAPHICS_BOTTOM_DIVIDER_Y_OFFSET = 318;

    public static final int DISPLAY_BUTTON_WIDTH = 104;
    public static final int DISPLAY_BUTTON_HEIGHT = 30;
    public static final int DISPLAY_BUTTON_Y_OFFSET = 22;

    public static final int CONTROL_WIDTH = 116;
    public static final int CONTROL_HEIGHT = 24;
    public static final int CONTROL_TEXT_PAD_X = 6;
    public static final int CONTROL_ARROW_RESERVED = 20;
    public static final int DROPDOWN_POPUP_ROW_HEIGHT = 18;

    public static final int NAV_BUTTON_WIDTH = 168;
    public static final int NAV_BUTTON_HEIGHT = 30;
    public static final int NAV_BUTTON_Y_OFFSET = 374;

    public static final int ADVANCED_COLUMN_SPACING = 130;
    public static final int ADVANCED_FIRST_COLUMN_OFFSET = -260;
    public static final int ADVANCED_LABEL_Y_OFFSET = 184;
    public static final int ADVANCED_CONTROL_Y_OFFSET = 202;
    public static final int ADVANCED_ROW_SPACING = 60;

    public static final int SLIDER_HEIGHT = 20;
    public static final int SLIDER_KNOB_WIDTH = 13;
    public static final int SLIDER_KNOB_HEIGHT = 18;

    public static final int DROPDOWN_ARROW_WIDTH = 9;
    public static final int DROPDOWN_ARROW_HEIGHT = 6;

    public static final int MAIN_MENU_BUTTON_WIDTH = 160;
    public static final int MAIN_MENU_BUTTON_HEIGHT = 28;

    public static final int AUDIO_PANEL_WIDTH = 420;
    public static final int AUDIO_PANEL_HEIGHT = 350;
    public static final int AUDIO_PANEL_INSET = 22;

    public static final int AUDIO_SLIDER_WIDTH = 156;
    public static final int AUDIO_SLIDER_CONTAINER_HEIGHT = 24;
    public static final int AUDIO_SLIDER_HEIGHT = 20;
    public static final int AUDIO_SLIDER_KNOB_WIDTH = 15;
    public static final int AUDIO_SLIDER_KNOB_HEIGHT = 20;

    public static final int AUDIO_TOGGLE_SIZE = 24;
    public static final int AUDIO_BUTTON_WIDTH = 168;
    public static final int AUDIO_BUTTON_HEIGHT = 30;

    private ModernUiMetrics() {
    }

    public static int centeredX(int centerX, int width) {
        return centerX - width / 2;
    }

    public static int centeredY(int centerY, int height) {
        return centerY - height / 2;
    }
}
