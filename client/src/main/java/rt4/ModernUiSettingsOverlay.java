package rt4;

/**
 * Settings bridge shown inside Graphics Options.
 *
 * Standard UI mode intentionally reuses the original RT4 font and, when
 * available, a real dropdown sprite from the Graphics Options interface.
 * Modern UI mode uses the new TrueType/vector control treatment.
 */
public final class ModernUiSettingsOverlay {
    private static int titleCenterX;
    private static int titleY;
    private static boolean graphicsOptionsSeen;
    private static boolean dropdownOpen;

    private static Component nativeDropdownSource;

    private static final int TEXT = 0x3B2B1B;
    private static final int DISABLED = 0x6D6658;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null) {
            return;
        }

        if (component.text != null && component.text.length() > 0
            && component.text.toString().contains("Graphics Options")) {
            titleCenterX = x + component.width / 2;
            titleY = y;
            graphicsOptionsSeen = true;
            return;
        }

        // Capture one of the existing advanced-option dropdown backgrounds so
        // Standard mode's new control really uses the original cache artwork.
        if (graphicsOptionsSeen
            && nativeDropdownSource == null
            && component.type == 5
            && component.width >= 80 && component.width <= 150
            && component.height >= 14 && component.height <= 28
            && y >= titleY + 245 && y <= titleY + 315) {
            nativeDropdownSource = component;
            DisplayDebug.log(
                "MODERN_UI captured native Graphics Options field sprite component=" + component.id
                    + " size=" + component.width + "x" + component.height
            );
        }
    }

    public static boolean isGraphicsOptionsSeen() {
        return graphicsOptionsSeen;
    }

    public static void render() {
        if (!graphicsOptionsSeen) {
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();
        boolean modern = ModernUiManager.isEnabled();

        // Sixth slot on the bottom Advanced Options row, immediately to the
        // right of Anti-aliasing. This keeps the Main Menu/Back area clear.
        int centerX = titleCenterX + 255;
        int labelBaseline = titleY + 270;
        int selectorW = 116;
        int selectorH = 20;
        int selectorX = centerX - selectorW / 2;
        int selectorY = titleY + 279;

        if (modern) {
            ModernTrueTypeFont.drawCentered("Modern UI", centerX, labelBaseline, 0xE4D2A3, 11.0F, true);
            drawModernField(selectorX, selectorY, selectorW, selectorH, true);
            ModernTrueTypeFont.drawCentered(
                "Yes",
                centerX - 5,
                selectorY + 15,
                0xFFF2CF,
                11.0F,
                false
            );
            drawModernArrow(selectorX + selectorW - 16, selectorY + 7);
        } else {
            if (Fonts.p12Full == null) {
                return;
            }
            Fonts.p12Full.renderCenter(JagString.parse("Modern UI"), centerX, labelBaseline, TEXT, -1);
            drawNativeField(selectorX, selectorY, selectorW, selectorH, true);
            Fonts.p12Full.renderCenter(JagString.parse("No"), centerX - 5, selectorY + 15, TEXT, -1);
            drawNativeArrows(selectorX, selectorY, selectorW, selectorH);
        }

        int editorW = 116;
        int editorH = 20;
        int editorX = centerX - editorW / 2;
        int editorY = selectorY + 24;

        if (modern) {
            drawModernField(editorX, editorY, editorW, editorH, true);
            ModernTrueTypeFont.drawCentered(
                "Style Editor",
                centerX,
                editorY + 15,
                0xFFF2CF,
                10.0F,
                true
            );
        } else {
            drawNativeField(editorX, editorY, editorW, editorH, false);
            Fonts.p12Full.renderCenter(
                JagString.parse("Style Editor"),
                centerX,
                editorY + 15,
                DISABLED,
                -1
            );
        }

        int popupX = selectorX;
        int popupY = selectorY + selectorH;
        int popupH = selectorH * 2;

        if (dropdownOpen) {
            if (modern) {
                drawModernField(popupX, popupY, selectorW, popupH, true);
                drawModernChoice("No", popupX, popupY, selectorW, selectorH, !modern);
                drawModernChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern);
            } else {
                drawNativeField(popupX, popupY, selectorW, popupH, true);
                drawNativeChoice("No", popupX, popupY, selectorW, selectorH, !modern);
                drawNativeChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern);
                drawNativeArrows(popupX, popupY, selectorW, selectorH);
            }
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

    private static void drawNativeField(int x, int y, int width, int height, boolean enabled) {
        Sprite sprite = null;
        if (nativeDropdownSource != null) {
            try {
                sprite = nativeDropdownSource.getSprite(false);
            } catch (Exception ignored) {
            }
        }

        if (sprite != null) {
            sprite.renderResized(x, y, width, height);
            if (!enabled) {
                fillAlpha(x, y, width, height, 0x857E70, 150);
            }
            return;
        }

        // Fallback only if the source sprite has not been encountered yet.
        int fill = enabled ? 0x9B8458 : 0x817967;
        int border = enabled ? 0x4A3A25 : 0x5F5A50;
        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        hline(x + 1, y + 1, width - 2, enabled ? 0xC7B07B : 0x989184);
        hline(x + 1, y + height - 2, width - 2, 0x6F5B3B);
    }

    private static void drawNativeArrows(int x, int y, int width, int height) {
        int midY = y + height / 2;

        // Match the small beveled arrow caps used by the RT4 graphics menu.
        int leftX = x - 7;
        int rightX = x + width + 1;
        int dark = 0x4A3A25;
        int light = 0xB7A06C;

        hline(leftX + 2, midY - 3, 3, dark);
        hline(leftX + 1, midY - 2, 4, dark);
        hline(leftX, midY - 1, 5, dark);
        hline(leftX + 1, midY, 4, light);
        hline(leftX + 2, midY + 1, 3, light);

        hline(rightX, midY - 3, 3, dark);
        hline(rightX, midY - 2, 4, dark);
        hline(rightX, midY - 1, 5, dark);
        hline(rightX, midY, 4, light);
        hline(rightX, midY + 1, 3, light);

        // Classic small down-arrow inside the right side of the field.
        int ax = x + width - 15;
        int ay = y + 7;
        hline(ax, ay, 9, dark);
        hline(ax + 1, ay + 1, 7, dark);
        hline(ax + 2, ay + 2, 5, dark);
        hline(ax + 3, ay + 3, 3, dark);
        hline(ax + 4, ay + 4, 1, dark);
    }

    private static void drawNativeChoice(String text, int x, int y, int width, int height, boolean selected) {
        if (selected) {
            fillAlpha(x + 2, y + 2, width - 4, height - 4, 0xD1B875, 150);
        }
        Fonts.p12Full.renderCenter(JagString.parse(text), x + width / 2, y + 15, TEXT, -1);
    }

    private static void drawModernField(int x, int y, int width, int height, boolean enabled) {
        int fill = enabled ? 0x3A3022 : 0x29251F;
        int border = enabled ? 0xA58956 : 0x514A40;
        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        hline(x + 1, y + 1, width - 2, enabled ? 0x594A33 : 0x37322C);
    }

    private static void drawModernChoice(String text, int x, int y, int width, int height, boolean selected) {
        if (selected) {
            fillAlpha(x + 2, y + 2, width - 4, height - 4, 0x806741, 220);
        }
        ModernTrueTypeFont.drawCentered(text, x + width / 2, y + 15, 0xFFF2CF, 10.0F, false);
    }

    private static void drawModernArrow(int x, int y) {
        ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
        if (arrow != null) {
            arrow.render(x, y);
        } else {
            ModernTrueTypeFont.draw("v", x, y + 6, 0xE7D4A5, 9.0F, false);
        }
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

    private static void fillAlpha(int x, int y, int width, int height, int color, int alpha) {
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, color, alpha);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, color, alpha);
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
