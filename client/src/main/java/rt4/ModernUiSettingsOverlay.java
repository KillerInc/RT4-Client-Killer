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
            GraphicsOptionsUiInjector.syncModernSelectorNativeVisuals();
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
            GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
            return;
        }

        if (!graphicsOptionsSeen || !selectorSeen) {
            dropdownOpen = false;
            GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
            return;
        }

        boolean modernStyle = ModernUiManager.isEnabled();
        if (modernStyle) {
            selectorX = ModernGraphicsOptionsUi.getModernSelectorX();
            selectorY = ModernGraphicsOptionsUi.getModernSelectorY();
            selectorRight =
                selectorX + ModernGraphicsOptionsUi.getModernSelectorWidth();
            selectorBottom =
                selectorY + ModernGraphicsOptionsUi.getModernSelectorHeight();

            editorX = ModernGraphicsOptionsUi.getStyleEditorX();
            editorY = ModernGraphicsOptionsUi.getStyleEditorY();
            editorW = ModernGraphicsOptionsUi.getStyleEditorWidth();
            editorH = ModernGraphicsOptionsUi.getStyleEditorHeight();
        }

        int selectorW = selectorWidth();
        int selectorH = selectorHeight();

        int popupX =
            modernStyle ? selectorX : selectorX + 10;
        int popupY =
            modernStyle
                ? selectorY + selectorH - 1
                : selectorBottom - 2;
        int popupW =
            modernStyle ? selectorW : Math.max(1, selectorW - 20);
        int rowH =
            modernStyle
                ? ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
                : 15;

        if (!modernStyle) {
            GraphicsOptionsUiInjector.setModernNativeClosedHover(
                contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY,
                    selectorX,
                    selectorY,
                    selectorW,
                    selectorH
                )
            );

            int hoveredRow = -1;
            if (dropdownOpen) {
                if (contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY,
                    popupX,
                    popupY,
                    popupW,
                    rowH
                )) {
                    hoveredRow = 0;
                } else if (contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY,
                    popupX,
                    popupY + rowH,
                    popupW,
                    rowH
                )) {
                    hoveredRow = 1;
                }
            }
            GraphicsOptionsUiInjector.setModernNativeDropdownHover(hoveredRow);
        }

        if (Mouse.clickButton != 1) {
            if (GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
                dropdownOpen = false;
                if (!modernStyle) {
                    GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
                }
            }
            return;
        }

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        // Vanilla gets first crack at the click. Afterwards, enforce the same
        // single-open / click-away behavior used by the Modern UI selector.
        GraphicsOptionsUiInjector.autoCloseNativeDropdownsForClick(mx, my);

        // A vanilla popup and Modern UI popup are mutually exclusive. Close
        // only the two stock popup containers; their CS2-created glyphs remain
        // intact for the next time that vanilla dropdown opens.
        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)
            && GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
            GraphicsOptionsUiInjector.closeAllNativeDropdowns();
            dropdownOpen = true;
            if (!modernStyle) {
                GraphicsOptionsUiInjector.setModernNativeDropdownOpen(true);
            }
            DisplayDebug.log(
                "MODERN_UI selector opened after closing native dropdown"
                    + " x=" + selectorX + " y=" + selectorY
                    + " w=" + selectorW + " h=" + selectorH
            );
            return;
        }

        if (GraphicsOptionsUiInjector.isNativeDropdownOpen()) {
            dropdownOpen = false;
            if (!modernStyle) {
                GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
            }
            return;
        }

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, popupW, rowH)) {
                dropdownOpen = false;
                if (!modernStyle) {
                    GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
                }
                DisplayDebug.log("MODERN_UI selector chose enabled=false");
                ModernUiManager.setEnabled(false);
                return;
            }

            if (contains(mx, my, popupX, popupY + rowH, popupW, rowH)) {
                dropdownOpen = false;
                if (!modernStyle) {
                    GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
                }
                DisplayDebug.log("MODERN_UI selector chose enabled=true");
                ModernUiManager.setEnabled(true);
                return;
            }

            if (!contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
                if (!modernStyle) {
                    GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
                }
                return;
            }
        }

        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
            dropdownOpen = !dropdownOpen;
            if (!modernStyle) {
                GraphicsOptionsUiInjector.setModernNativeDropdownOpen(
                    dropdownOpen
                );
            }
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
            dropdownOpen = false;
            GraphicsOptionsUiInjector.setModernNativeDropdownOpen(false);
            return;
        }

        ModernUiManager.initialize();

        // Visual ownership is split cleanly:
        // - Standard mode: native RT4 cache/runtime rendering.
        // - Modern mode: ModernGraphicsOptionsUi.
        // This class is input/state only.
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

        ModernUiImage arrow = ModernUiAssetResolver.get(
            "icons/dropdown",
            ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
            ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
        );
        int arrowX =
            x + width - ModernUiMetrics.DROPDOWN_ARROW_WIDTH - 6;
        int arrowY =
            y + Math.max(
                4,
                (height - ModernUiMetrics.DROPDOWN_ARROW_HEIGHT) / 2
            );
        if (arrow != null) {
            arrow.render(arrowX, arrowY);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:icons/dropdown",
                arrowX,
                arrowY,
                ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
                ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
            );
        }

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_11,
            "On",
            x + ModernUiMetrics.CONTROL_TEXT_PAD_X,
            y,
            Math.max(
                1,
                width
                    - ModernUiMetrics.CONTROL_ARROW_RESERVED
                    - ModernUiMetrics.CONTROL_TEXT_PAD_X
            ),
            height,
            ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_DROPDOWN,
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

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            text,
            x + ModernUiMetrics.CONTROL_TEXT_PAD_X,
            y,
            Math.max(
                1,
                width - ModernUiMetrics.CONTROL_TEXT_PAD_X * 2
            ),
            height,
            selected
                ? ModernUiMetrics.TEXT_ACCENT
                : ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_DROPDOWN,
            false
        );
    }

    public static boolean isDropdownOpen() {
        return dropdownOpen;
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

}
