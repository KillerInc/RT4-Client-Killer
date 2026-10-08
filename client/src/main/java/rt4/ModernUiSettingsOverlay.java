package rt4;

/**
 * Killer Edition behavior layer for the Modern UI controls added to Graphics
 * Options. Vanilla interface processing remains authoritative and untouched.
 */
public final class ModernUiSettingsOverlay {
    private static boolean graphicsOptionsSeen;
    private static boolean selectorSeen;
    private static boolean editorSeen;
    private static boolean dropdownOpen;

    private static int selectorX;
    private static int selectorY;
    private static int selectorRight;
    private static int selectorBottom;

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

        selectorX = Integer.MAX_VALUE;
        selectorY = Integer.MAX_VALUE;
        selectorRight = Integer.MIN_VALUE;
        selectorBottom = Integer.MIN_VALUE;

        if (ModernUiManager.isSupportedDisplayMode()) {
            GraphicsOptionsUiInjector.normalizeNativeDropdowns();
        } else {
            dropdownOpen = false;
        }
    }

    public static void afterInterfaceScripts() {
        if (ModernUiManager.isSupportedDisplayMode()) {
            GraphicsOptionsUiInjector.normalizeNativeDropdowns();
        }
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

        int code = component.clientCode;
        if (code == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_HIT
            || code == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_PIECE
            || code == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT) {
            // The cache-defined Killer selector only exists while Graphics
            // Options is visible, so seeing any of its pieces is a stronger
            // signal than depending on the non-interactive title component
            // being visited by the input walker.
            graphicsOptionsSeen = true;
            includeSelectorBounds(
                x,
                y,
                Math.max(1, component.width),
                Math.max(1, component.height)
            );
        } else if (code == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT) {
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

    /**
     * Runs after the original RT4 interface processing in client.mainUpdate().
     * Reads Mouse's normal click snapshot but deliberately never changes it.
     */
    public static void processInput() {
        if (!ModernUiManager.isSupportedDisplayMode()) {
            dropdownOpen = false;
            return;
        }
        if (!graphicsOptionsSeen || !selectorSeen || Mouse.clickButton != 1) {
            if (GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
                dropdownOpen = false;
            }
            return;
        }

        int selectorW = selectorWidth();
        int selectorH = selectorHeight();
        int popupX = selectorX;
        int popupY = selectorBottom;
        int popupW = selectorW;
        int rowH = Math.max(18, selectorH);

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        // The Modern selector is allowed to take ownership when explicitly
        // clicked. Close any vanilla popup first so our popup never stacks on
        // top of a base-game dropdown.
        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)
            && GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
            GraphicsOptionsUiInjector.closeAllNativeDropdowns();
            dropdownOpen = true;
            DisplayDebug.log(
                "MODERN_UI selector opened after closing native dropdown"
                    + " x=" + selectorX + " y=" + selectorY
                    + " w=" + selectorW + " h=" + selectorH
            );
            return;
        }

        if (GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
            dropdownOpen = false;
            return;
        }

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, popupW, rowH)) {
                dropdownOpen = false;
                DisplayDebug.log("MODERN_UI selector chose enabled=false");
                ModernUiManager.setEnabled(false);
                return;
            }

            if (contains(mx, my, popupX, popupY + rowH, popupW, rowH)) {
                dropdownOpen = false;
                DisplayDebug.log("MODERN_UI selector chose enabled=true");
                ModernUiManager.setEnabled(true);
                return;
            }

            if (!contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
                return;
            }
        }

        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
            dropdownOpen = !dropdownOpen;
            DisplayDebug.log(
                "MODERN_UI selector popup " + (dropdownOpen ? "opened" : "closed")
                    + " x=" + selectorX + " y=" + selectorY
                    + " w=" + selectorW + " h=" + selectorH
            );
            return;
        }

        if (ModernUiManager.isEnabled()
            && editorSeen
            && contains(mx, my, editorX, editorY, editorW, editorH)) {
            ModernUiManager.openStyleEditor();
        }
    }

    public static void render() {
        if (!ModernUiManager.isSupportedDisplayMode()
            || GraphicsOptionsUiInjector.isNativeDropdownOpen()
            || !graphicsOptionsSeen
            || !selectorSeen) {
            // Native dropdown popups are rendered by the cache after their
            // controls. Do not paint our selector on top of an open popup.
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();

        int selectorW = selectorWidth();
        int selectorH = selectorHeight();

        // Standard UI is now rendered entirely by the same native RT4
        // sprite pieces as the surrounding Graphics Options dropdowns.
        // Only Modern mode covers that cache-defined control with pack art.
        if (ModernUiManager.isEnabled()) {
            drawModernClosedSelector(
                selectorX,
                selectorY,
                selectorW,
                selectorH
            );
        }

        if (!dropdownOpen) {
            return;
        }

        int popupX = selectorX;
        int popupY = selectorBottom;
        int popupW = selectorW;
        int rowH = Math.max(18, selectorH);
        int popupH = rowH * 2;

        if (ModernUiManager.isEnabled()) {
            drawModernPopup(popupX, popupY, popupW, popupH);
            drawModernChoice("No", popupX, popupY, popupW, rowH, false);
            drawModernChoice("Yes", popupX, popupY + rowH, popupW, rowH, true);
        } else {
            drawNativePopup(popupX, popupY, popupW, popupH);
            drawNativeChoice("No", popupX, popupY, popupW, rowH, true);
            drawNativeChoice("Yes", popupX, popupY + rowH, popupW, rowH, false);
        }
    }

    private static void includeSelectorBounds(int x, int y, int width, int height) {
        selectorX = Math.min(selectorX, x);
        selectorY = Math.min(selectorY, y);
        selectorRight = Math.max(selectorRight, x + width);
        selectorBottom = Math.max(selectorBottom, y + height);
        selectorSeen = true;
    }

    private static int selectorWidth() {
        return Math.max(1, selectorRight - selectorX);
    }

    private static int selectorHeight() {
        return Math.max(1, selectorBottom - selectorY);
    }

    private static void drawModernClosedSelector(
        int x,
        int y,
        int width,
        int height
    ) {
        ModernUiImage field = ModernUiAssetResolver.get(
            "controls/dropdown",
            width,
            height
        );
        if (field != null) {
            field.render(x, y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:controls/dropdown",
                x,
                y,
                width,
                height
            );
        }

        ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
        int arrowX = x + width - 15;
        int arrowY = y + Math.max(4, (height - 6) / 2);
        if (arrow != null) {
            arrow.render(arrowX, arrowY);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:icons/dropdown",
                arrowX,
                arrowY,
                9,
                6
            );
        }

        ModernTrueTypeFont.draw(
            "Yes",
            x + 5,
            y + Math.min(height - 3, 14),
            0xE8DDC4,
            11.0F,
            false
        );
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
