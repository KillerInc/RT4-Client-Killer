package rt4;

/**
 * Behavior bridge for the Modern UI selector that is injected into the actual
 * Graphics Options component tree by GraphicsOptionsUiInjector.
 *
 * Layout, fonts and closed-control artwork are rendered by the normal
 * Component renderer. This class only owns the Yes/No popup and click actions.
 */
public final class ModernUiSettingsOverlay {
    private static boolean graphicsOptionsSeen;
    private static boolean selectorSeen;
    private static boolean editorSeen;
    private static boolean dropdownOpen;

    private static int selectorX;
    private static int selectorY;
    private static int selectorW;
    private static int selectorH;

    private static int editorX;
    private static int editorY;
    private static int editorW;
    private static int editorH;

    private static final int TEXT = 0x3B2B1B;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
        selectorSeen = false;
        editorSeen = false;
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null) {
            return;
        }

        if (component.text != null
            && component.text.length() > 0
            && component.text.toString().contains("Graphics Options")) {
            graphicsOptionsSeen = true;
        }

        if (component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_HIT) {
            selectorX = x;
            selectorY = y;
            selectorW = Math.max(1, component.width);
            selectorH = Math.max(1, component.height);
            selectorSeen = true;
        } else if (component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT) {
            editorX = x;
            editorY = y;
            editorW = Math.max(1, component.width);
            editorH = Math.max(1, component.height);
            editorSeen = true;
        }
    }

    public static boolean isGraphicsOptionsSeen() {
        return graphicsOptionsSeen;
    }

    public static void render() {
        if (!graphicsOptionsSeen || !selectorSeen) {
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();
        boolean modern = ModernUiManager.isEnabled();

        int popupX = selectorX;
        int popupY = selectorY + selectorH;
        int popupW = selectorW;
        int rowH = Math.max(18, selectorH);
        int popupH = rowH * 2;

        if (dropdownOpen) {
            if (modern) {
                drawModernPopup(popupX, popupY, popupW, popupH);
                drawModernChoice("No", popupX, popupY, popupW, rowH, false);
                drawModernChoice("Yes", popupX, popupY + rowH, popupW, rowH, true);
            } else {
                drawNativePopup(popupX, popupY, popupW, popupH);
                drawNativeChoice("No", popupX, popupY, popupW, rowH, true);
                drawNativeChoice("Yes", popupX, popupY + rowH, popupW, rowH, false);
            }
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, popupW, rowH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(false);
                return;
            }
            if (contains(mx, my, popupX, popupY + rowH, popupW, rowH)) {
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
            return;
        }

        if (modern
            && editorSeen
            && contains(mx, my, editorX, editorY, editorW, editorH)) {
            Mouse.clickButton = 0;
            ModernUiManager.openStyleEditor();
        }
    }

    private static void drawModernPopup(int x, int y, int width, int height) {
        ModernUiImage popup = ModernUiAssetResolver.get("controls/popup", width, height);
        if (popup != null) {
            popup.render(x, y);
        } else {
            ModernUiRenderer.drawMissing("asset:controls/popup", x, y, width, height);
        }
    }

    private static void drawModernChoice(
        String text,
        int x,
        int y,
        int width,
        int height,
        boolean selected
    ) {
        boolean hovered = contains(Mouse.lastMouseX, Mouse.lastMouseY, x, y, width, height);
        String asset = selected
            ? "controls/choice-selected"
            : hovered ? "controls/choice-hover" : null;

        if (asset != null) {
            ModernUiImage row = ModernUiAssetResolver.get(
                asset,
                Math.max(1, width - 4),
                Math.max(1, height - 2)
            );
            if (row != null) {
                row.render(x + 2, y + 1);
            } else {
                ModernUiRenderer.drawMissing(
                    "asset:" + asset,
                    x + 2,
                    y + 1,
                    Math.max(1, width - 4),
                    Math.max(1, height - 2)
                );
            }
        }

        ModernTrueTypeFont.drawCentered(
            text,
            x + width / 2,
            y + Math.min(height - 3, 15),
            selected ? 0xFFF4D1 : 0xE8DDC4,
            12.0F,
            false
        );
    }

    private static void drawNativePopup(int x, int y, int width, int height) {
        fill(x, y, width, height, 0x9B8458);
        outline(x, y, width, height, 0x29251C);
        outline(x + 1, y + 1, width - 2, height - 2, 0x6F5B3B);
        hline(x + 2, y + 2, width - 4, 0xC7B07B);
    }

    private static void drawNativeChoice(
        String text,
        int x,
        int y,
        int width,
        int height,
        boolean selected
    ) {
        if (selected) {
            fillAlpha(x + 2, y + 2, width - 4, height - 4, 0xD1B875, 150);
        }
        hline(x + 2, y + height - 1, width - 4, 0x6F5B3B);
        if (Fonts.p12Full != null) {
            Fonts.p12Full.renderCenter(
                JagString.parse(text),
                x + width / 2,
                y + Math.min(height - 3, 15),
                TEXT,
                -1
            );
        }
    }

    private static boolean contains(
        int mx,
        int my,
        int x,
        int y,
        int width,
        int height
    ) {
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    private static void fill(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, color);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, color);
        }
    }

    private static void fillAlpha(
        int x,
        int y,
        int width,
        int height,
        int color,
        int alpha
    ) {
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
