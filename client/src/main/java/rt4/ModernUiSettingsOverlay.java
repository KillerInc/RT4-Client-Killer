package rt4;

/**
 * Settings bridge shown inside the original RuneScape Graphics Options panel.
 *
 * This intentionally uses the original RT4 font and a matching 2009-era
 * beige/brown control treatment so the new option looks native to the screen.
 */
public final class ModernUiSettingsOverlay {
    private static int titleCenterX;
    private static int titleY;
    private static boolean graphicsOptionsSeen;
    private static boolean dropdownOpen;

    private static final int TEXT = 0x3B2B1B;
    private static final int FIELD = 0x9B8458;
    private static final int FIELD_HI = 0xC7B07B;
    private static final int FIELD_BORDER = 0x4A3A25;
    private static final int FIELD_DARK = 0x6F5B3B;
    private static final int DISABLED = 0x6D6658;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null || component.text == null || component.text.length() == 0) {
            return;
        }
        if (!component.text.toString().contains("Graphics Options")) {
            return;
        }

        titleCenterX = x + component.width / 2;
        titleY = y;
        graphicsOptionsSeen = true;
    }

    public static void render() {
        if (!graphicsOptionsSeen || Fonts.p12Full == null) {
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();
        boolean modern = ModernUiManager.isEnabled();

        int labelX = titleCenterX - 125;
        int rowY = titleY + 334;
        int selectorX = titleCenterX + 18;
        int selectorY = rowY - 15;
        int selectorW = 82;
        int selectorH = 20;

        Fonts.p12Full.renderLeft(JagString.parse("Modern UI:"), labelX, rowY, TEXT, -1);
        drawNativeField(selectorX, selectorY, selectorW, selectorH, true);
        Fonts.p12Full.renderCenter(
            JagString.parse(modern ? "Yes" : "No"),
            selectorX + selectorW / 2 - 5,
            selectorY + 15,
            TEXT,
            -1
        );
        Fonts.p12Full.renderLeft(JagString.parse("v"), selectorX + selectorW - 14, selectorY + 15, TEXT, -1);

        int editorW = 142;
        int editorH = 20;
        int editorX = titleCenterX - editorW / 2;
        int editorY = rowY + 12;

        drawNativeField(editorX, editorY, editorW, editorH, modern);
        Fonts.p12Full.renderCenter(
            JagString.parse("Style Editor"),
            editorX + editorW / 2,
            editorY + 15,
            modern ? TEXT : DISABLED,
            -1
        );

        int popupX = selectorX;
        int popupY = selectorY + selectorH;
        int popupH = selectorH * 2;
        if (dropdownOpen) {
            drawNativeField(popupX, popupY, selectorW, popupH, true);
            drawChoice("No", popupX, popupY, selectorW, selectorH, !modern);
            drawChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern);
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(false);
                return;
            }
            if (contains(mx, my, popupX, popupY + selectorH, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(true);
                return;
            }
            if (!contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
            }
        }

        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
            Mouse.clickButton = 0;
            dropdownOpen = !dropdownOpen;
        } else if (!dropdownOpen && modern && contains(mx, my, editorX, editorY, editorW, editorH)) {
            Mouse.clickButton = 0;
            ModernUiManager.openStyleEditor();
        }
    }

    private static void drawChoice(String text, int x, int y, int width, int height, boolean selected) {
        if (selected) {
            fill(x + 2, y + 2, width - 4, height - 4, FIELD_HI);
        }
        Fonts.p12Full.renderCenter(JagString.parse(text), x + width / 2, y + 15, TEXT, -1);
    }

    private static void drawNativeField(int x, int y, int width, int height, boolean enabled) {
        int fill = enabled ? FIELD : 0x817967;
        int border = enabled ? FIELD_BORDER : 0x5F5A50;
        int highlight = enabled ? FIELD_HI : 0x989184;

        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        hline(x + 1, y + 1, width - 2, highlight);
        hline(x + 1, y + height - 2, width - 2, FIELD_DARK);
    }

    private static boolean contains(int mx, int my, int x, int y, int width, int height) {
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    private static void fill(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, color);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, color);
        }
    }

    private static void outline(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.drawRect(x, y, width, height, color);
        } else {
            SoftwareRaster.drawRect(x, y, width, height, color);
        }
    }

    private static void hline(int x, int y, int width, int color) {
        if (GlRenderer.enabled) {
            GlRaster.drawHorizontalLine(x, y, width, color);
        } else {
            SoftwareRaster.drawHorizontalLine(x, y, width, color);
        }
    }
}
