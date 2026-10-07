package rt4;

/**
 * Settings bridge shown inside Graphics Options.
 *
 * In Standard mode it is drawn with the existing settings renderer. In Modern
 * mode the control text/icons use the new TrueType/vector pipeline.
 */
public final class ModernUiSettingsOverlay {
    private static int titleCenterX;
    private static int titleY;
    private static boolean graphicsOptionsSeen;
    private static boolean dropdownOpen;

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
        if (!graphicsOptionsSeen) {
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();
        boolean modern = ModernUiManager.isEnabled();
        if (!modern && Fonts.p12Full == null) {
            return;
        }

        int labelX = titleCenterX - 125;
        int rowY = titleY + 334;
        int selectorX = titleCenterX + 18;
        int selectorY = rowY - 15;
        int selectorW = 82;
        int selectorH = 20;

        drawTextLeft("Modern UI:", labelX, rowY, 0xFFFFFF, modern);
        drawButton(selectorX, selectorY, selectorW, selectorH, true);
        drawSelectorValue(modern ? "Yes" : "No", selectorX, selectorY, selectorW, selectorH, modern);

        int editorW = 142;
        int editorH = 20;
        int editorX = titleCenterX - editorW / 2;
        int editorY = rowY + 12;
        boolean editorEnabled = modern;
        drawButton(editorX, editorY, editorW, editorH, editorEnabled);
        drawTextCentered(
            "Style Editor",
            editorX + editorW / 2,
            editorY + 15,
            editorEnabled ? 0xFFFFFF : 0x777777,
            modern
        );

        // The popup intentionally overlays the Style Editor button just like
        // an ordinary combo box popup. Its hit testing takes precedence.
        int popupX = selectorX;
        int popupY = selectorY + selectorH;
        int popupH = selectorH * 2;
        if (dropdownOpen) {
            drawButton(popupX, popupY, selectorW, popupH, true);
            drawChoice("No", popupX, popupY, selectorW, selectorH, !modern, modern);
            drawChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern, modern);
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        int mouseX = Mouse.clickX;
        int mouseY = Mouse.clickY;

        if (dropdownOpen) {
            if (contains(mouseX, mouseY, popupX, popupY, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(false);
                return;
            }
            if (contains(mouseX, mouseY, popupX, popupY + selectorH, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(true);
                return;
            }
            if (!contains(mouseX, mouseY, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
            }
        }

        if (contains(mouseX, mouseY, selectorX, selectorY, selectorW, selectorH)) {
            Mouse.clickButton = 0;
            dropdownOpen = !dropdownOpen;
        } else if (!dropdownOpen && editorEnabled && contains(mouseX, mouseY, editorX, editorY, editorW, editorH)) {
            Mouse.clickButton = 0;
            ModernUiManager.openStyleEditor();
        }
    }

    private static void drawSelectorValue(String value, int x, int y, int width, int height, boolean modern) {
        drawTextCentered(value, x + width / 2 - 5, y + 15, 0xFFFFFF, modern);

        if (modern) {
            ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
            if (arrow != null) {
                arrow.render(x + width - 15, y + 7);
            } else {
                ModernTrueTypeFont.draw("v", x + width - 14, y + 14, 0xFFFFFF, 9.0F, false);
            }
        } else {
            Fonts.p12Full.renderLeft(JagString.parse("v"), x + width - 15, y + 15, 0xFFFFFF, 0);
        }
    }

    private static void drawChoice(String text, int x, int y, int width, int height, boolean selected, boolean modern) {
        if (selected) {
            if (GlRenderer.enabled) {
                GlRaster.fillRectAlpha(x + 1, y + 1, width - 2, height - 2, 0x6E5A38, 220);
            } else {
                SoftwareRaster.fillRectAlpha(x + 1, y + 1, width - 2, height - 2, 0x6E5A38, 220);
            }
        }
        drawTextCentered(text, x + width / 2, y + 15, 0xFFFFFF, modern);
    }

    private static void drawTextLeft(String text, int x, int baselineY, int color, boolean modern) {
        if (modern) {
            ModernTrueTypeFont.draw(text, x, baselineY, color, 12.0F, true);
        } else {
            Fonts.p12Full.renderLeft(JagString.parse(text), x, baselineY, color, 0);
        }
    }

    private static void drawTextCentered(String text, int centerX, int baselineY, int color, boolean modern) {
        if (modern) {
            ModernTrueTypeFont.drawCentered(text, centerX, baselineY, color, 11.0F, true);
        } else {
            Fonts.p12Full.renderCenter(JagString.parse(text), centerX, baselineY, color, 0);
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
