package rt4;

/**
 * Small bridge rendered only inside the legacy Graphics Options window.
 *
 * This intentionally uses the existing renderer because it belongs to
 * Standard UI. The Modern UI renderer itself will not use legacy sprite/font
 * fallbacks.
 */
public final class ModernUiSettingsOverlay {
    private static int titleCenterX;
    private static int titleY;
    private static boolean graphicsOptionsSeen;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null || component.text == null || component.text.length() == 0) {
            return;
        }

        String text = component.text.toString();
        if (!text.contains("Graphics Options")) {
            return;
        }

        titleCenterX = x + component.width / 2;
        titleY = y;
        graphicsOptionsSeen = true;
    }

    public static void render() {
        if (!graphicsOptionsSeen || Fonts.p12Full == null) {
            return;
        }

        ModernUiManager.initialize();

        int labelX = titleCenterX - 125;
        int rowY = titleY + 334;
        int selectorX = titleCenterX + 18;
        int selectorY = rowY - 15;
        int selectorW = 82;
        int selectorH = 20;

        Fonts.p12Full.renderLeft(JagString.parse("Modern UI:"), labelX, rowY, 0xFFFFFF, 0);
        drawButton(selectorX, selectorY, selectorW, selectorH, true);
        Fonts.p12Full.renderCenter(
            JagString.parse(ModernUiManager.isEnabled() ? "Yes  v" : "No  v"),
            selectorX + selectorW / 2,
            selectorY + 15,
            0xFFFFFF,
            0
        );

        int editorW = 142;
        int editorH = 20;
        int editorX = titleCenterX - editorW / 2;
        int editorY = rowY + 12;
        boolean editorEnabled = ModernUiManager.isEnabled();
        drawButton(editorX, editorY, editorW, editorH, editorEnabled);
        Fonts.p12Full.renderCenter(
            JagString.parse("Style Editor"),
            editorX + editorW / 2,
            editorY + 15,
            editorEnabled ? 0xFFFFFF : 0x777777,
            0
        );

        if (Mouse.clickButton == 1) {
            if (contains(Mouse.clickX, Mouse.clickY, selectorX, selectorY, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                ModernUiManager.setEnabled(!ModernUiManager.isEnabled());
            } else if (editorEnabled && contains(Mouse.clickX, Mouse.clickY, editorX, editorY, editorW, editorH)) {
                Mouse.clickButton = 0;
                ModernUiManager.openStyleEditor();
            }
        }
    }

    private static void drawButton(int x, int y, int width, int height, boolean enabled) {
        int fill = enabled ? 0x3A3024 : 0x282828;
        int border = enabled ? 0xB59A68 : 0x555555;
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, fill);
            GlRaster.drawRect(x, y, width, height, border);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, fill);
            SoftwareRaster.drawRect(x, y, width, height, border);
        }
    }

    private static boolean contains(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
