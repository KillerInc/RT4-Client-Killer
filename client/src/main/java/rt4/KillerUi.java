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

        // Scene viewport, alternate scene viewport, minimap and compass.
        return component.clientCode == 1337
            || component.clientCode == 1403
            || component.clientCode == 1338
            || component.clientCode == 1339;
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
}
