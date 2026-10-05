package rt4;

/**
 * Central scale/layout policy for the Killer scalable interface rewrite.
 *
 * This affects interface geometry only. The game scene, minimap scene, compass
 * and other world-rendered surfaces stay on the original RT4 coordinate path.
 */
public final class KillerUi {
    private static final double MIN_SCALE = 0.5D;
    private static final double MAX_SCALE = 2.0D;

    private KillerUi() {
    }

    private static double readScale(String property) {
        try {
            double value = Double.parseDouble(System.getProperty(property, "1.0"));
            if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0.0D) {
                return 1.0D;
            }
            return Math.max(MIN_SCALE, Math.min(MAX_SCALE, value));
        } catch (NumberFormatException ignored) {
            return 1.0D;
        }
    }

    /**
     * UI/layout geometry scale. Font Scale must never affect this value.
     */
    public static double scale() {
        return readScale("sun.java2d.uiScale");
    }

    /**
     * Extra text-only multiplier selected in the launcher.
     */
    public static double textScale() {
        return readScale("killerFontScale");
    }

    /**
     * UI Scale is allowed to scale text as part of scaling the whole UI.
     * Font Scale then applies an additional text-only multiplier.
     */
    public static double effectiveTextScale() {
        return scale() * textScale();
    }

    public static int px(int value) {
        if (value == 0) {
            return 0;
        }
        int sign = value < 0 ? -1 : 1;
        int magnitude = Math.abs(value);
        return sign * Math.max(1, (int) Math.round((double) magnitude * scale()));
    }

    /**
     * Convert a rendered/scaled UI coordinate back into the native 1.0
     * coordinate space used by cache scripts. This prevents CS2 interfaces
     * from reading a scaled size and then feeding it back through a setter
     * that scales the value a second time.
     */
    public static int logicalPx(int value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.round((double) value / scale());
    }

    public static int scriptGeometry(Component component, int value) {
        return isWorldSurface(component) ? value : logicalPx(value);
    }

    public static int fontTarget(int nativeSize) {
        return Math.max(1, (int) Math.floor((double) nativeSize * effectiveTextScale() + 0.000001D));
    }

    public static int inventorySlotSize() {
        return px(32);
    }

    public static int inventoryTextCellWidth() {
        return px(115);
    }

    public static int inventoryTextCellHeight() {
        return px(12);
    }

    public static int scrollbarWidth() {
        return px(16);
    }

    public static int scrollbarArrowHeight() {
        return px(16);
    }

    public static int menuPadding() {
        return px(4);
    }

    public static int menuRowHeight() {
        return Math.max(px(15), KillerUiText.lineHeight(KillerUiText.BOLD_12) + px(4));
    }

    public static int menuHeaderHeight() {
        return Math.max(px(20), KillerUiText.lineHeight(KillerUiText.BOLD_12) + px(7));
    }

    public static boolean isWorldSurface(Component component) {
        if (component == null) {
            return false;
        }

        // Only the 3D game scene stays in native world coordinates.
        // Minimap/compass are UI surfaces: their frame, viewport and hit mask
        // must scale with the rest of the interface.
        return component.clientCode == 1337
            || component.clientCode == 1403;
    }

    /**
     * Scale cache-authored UI coordinates once, immediately after decode.
     * Ratio/count fields are intentionally not scaled.
     */
    public static void scaleDecodedComponent(Component component) {
        if (component == null || isWorldSurface(component)) {
            return;
        }

        if (component.yMode <= 2) {
            component.baseX = px(component.baseX);
        }
        if (component.xMode <= 2) {
            component.baseY = px(component.baseY);
        }

        // 0 = fixed pixels, 1 = parent minus pixel margin.
        // 2 = 14-bit ratio, 3 = inventory/list element count, 4 = aspect.
        if (component.dynamicWidthValue <= 1) {
            component.baseWidth = px(component.baseWidth);
        }
        if (component.dynamicHeightValue <= 1) {
            component.baseHeight = px(component.baseHeight);
        }

        component.vpadding = px(component.vpadding);
        component.lineWidth = Math.max(1, px(component.lineWidth));

        if (component.scrollMaxH != 0) {
            component.scrollMaxH = px(component.scrollMaxH);
        }
        if (component.scrollMaxV != 0) {
            component.scrollMaxV = px(component.scrollMaxV);
        }

        component.invMarginX = px(component.invMarginX);
        component.invMarginY = px(component.invMarginY);

        if (component.invOffsetX != null) {
            for (int i = 0; i < component.invOffsetX.length; i++) {
                component.invOffsetX[i] = px(component.invOffsetX[i]);
            }
        }
        if (component.invOffsetY != null) {
            for (int i = 0; i < component.invOffsetY.length; i++) {
                component.invOffsetY[i] = px(component.invOffsetY[i]);
            }
        }

        // Model preview offsets belong to UI preview widgets, not the world scene.
        component.modelOriginX = px(component.modelOriginX);
        component.modelOriginY = px(component.modelOriginY);
        component.modelXOffset = px(component.modelXOffset);
        component.modelYOffset = px(component.modelYOffset);
        component.modelZOffset = px(component.modelZOffset);
        if (component.modelViewportWidth > 0) {
            component.modelViewportWidth = px(component.modelViewportWidth);
        }
        if (component.modelViewportHeight > 0) {
            component.modelViewportHeight = px(component.modelViewportHeight);
        }
    }

    public static int scaledGridWidth(Component component) {
        return component.baseWidth * inventorySlotSize()
            + (component.baseWidth - 1) * component.invMarginX;
    }

    public static int scaledGridHeight(Component component) {
        return component.baseHeight * inventorySlotSize()
            + (component.baseHeight - 1) * component.invMarginY;
    }

    public static int scaledTextGridWidth(Component component) {
        return component.baseWidth * inventoryTextCellWidth()
            + (component.baseWidth - 1) * component.invMarginX;
    }

    public static int scaledTextGridHeight(Component component) {
        return component.baseHeight * inventoryTextCellHeight()
            + (component.baseHeight - 1) * component.invMarginY;
    }

    /**
     * Recognize the bottom-left chat frame without depending on a cache
     * interface id. 2009-era gameframes use a roughly 519x165 logical
     * container for the chat box/tabs.
     */
    public static boolean isBottomChatPanel(Component component, int parentW, int parentH) {
        if (component == null || scale() <= 1.000001D || component.type != 0) {
            return false;
        }

        int logicalW = Math.abs(logicalPx(component.width));
        int logicalH = Math.abs(logicalPx(component.height));
        int logicalX = logicalPx(component.x);
        int bottomGap = parentH - (component.y + component.height);

        return logicalW >= 490 && logicalW <= 545
            && logicalH >= 145 && logicalH <= 185
            && logicalX >= -8 && logicalX <= 24
            && bottomGap >= -px(4) && bottomGap <= px(10)
            && parentW >= component.width
            && parentH >= component.height;
    }

    /**
     * The original gameframe leaves a small native gap below the chat area.
     * Once the entire interface is scaled, keeping that gap unscaled makes
     * the chat sit visibly too low. Apply only the additional scale delta so
     * the 1.0 layout remains byte-for-byte unchanged.
     */
    public static int bottomChatLift() {
        return Math.max(0, px(12) - 12);
    }

    /**
     * Text rows inside the bottom chat area are single native rows that need
     * to become a bottom-anchored flow when scaled text wraps.
     */
    public static boolean isBottomChatText(Component component, int screenY, int availableWidth) {
        if (component == null || scale() <= 1.000001D) {
            return false;
        }

        return component.type == 4
            && component.halign == 0
            && component.height > 0
            && component.height <= px(22)
            && component.width >= px(240)
            && availableWidth >= px(180)
            && screenY >= GameShell.canvasHeight / 2;
    }

}
