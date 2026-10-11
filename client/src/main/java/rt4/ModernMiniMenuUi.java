package rt4;

/**
 * Modern renderer for the right-click menu and action tooltip.
 *
 * Menu selection geometry remains owned by MiniMenu so action ordering and
 * click behavior are unchanged; only the visual surface/font is replaced.
 */
public final class ModernMiniMenuUi {
    private ModernMiniMenuUi() {
    }

    public static void render() {
        int x = InterfaceList.menuX;
        int y = InterfaceList.menuY;
        int width = Math.max(1, InterfaceList.menuWidth);
        int height = Math.max(1, InterfaceList.menuHeight);

        drawAsset(
            "game-ui/context-menu",
            new ModernUiRect(x, y, width, height)
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            LocalizedText.CHOOSE_OPTION.toString(),
            x + 6,
            y + 2,
            Math.max(1, width - 12),
            18,
            ModernUiMetrics.TEXT_GOLD,
            0,
            1,
            ModernUiMetrics.FONT_LABEL,
            false
        );

        int base = InterfaceList.useStyledMenu ? 35 : 31;
        int mouseX = Mouse.lastMouseX;
        int mouseY = Mouse.lastMouseY;

        for (int i = 0; i < MiniMenu.size; i++) {
            int baseline =
                (MiniMenu.size - i - 1) * 15 + y + base;
            ModernUiRect row = new ModernUiRect(
                x + 3,
                baseline - 13,
                Math.max(1, width - 6),
                16
            );

            boolean hover =
                mouseX > x
                    && mouseX < x + width
                    && mouseY > baseline - 13
                    && mouseY < baseline + 3;

            if (hover) {
                drawAsset(
                    "controls/choice-hover",
                    row
                );
            }

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_12,
                strip(MiniMenu.getOp(i).toString()),
                row.x + 4,
                row.y,
                Math.max(1, row.width - 8),
                row.height,
                hover
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
        }

        InterfaceList.forceRedrawScreen(
            x,
            y,
            height,
            width
        );
    }

    public static void renderTooltip(
        Component component,
        int y,
        int x
    ) {
        if (MiniMenu.size < 2
            && MiniMenu.itemTargetMode == 0
            && !MiniMenu.isTargeting) {
            return;
        }

        String text =
            strip(MiniMenu.getTooltipText().toString());
        int width = Math.max(
            1,
            ModernTrueTypeFont.getWidth(
                ModernUiFontRegistry.BOLD_12,
                text,
                ModernUiMetrics.FONT_CONTROL
            ) + 10
        );

        if (component == null) {
            ModernTrueTypeFont.draw(
                ModernUiFontRegistry.BOLD_12,
                text,
                x + 4,
                y + 13,
                ModernUiMetrics.TEXT_PRIMARY,
                ModernUiMetrics.FONT_CONTROL,
                true
            );
            InterfaceList.redrawScreen(
                x + 2,
                width,
                y,
                18
            );
            return;
        }

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            text,
            x,
            y,
            component.width,
            component.height,
            ModernUiMetrics.TEXT_PRIMARY,
            component.halign,
            component.valign,
            ModernUiMetrics.FONT_CONTROL,
            true
        );
        InterfaceList.redrawScreen(
            x,
            component.width,
            y,
            component.height
        );
    }

    private static String strip(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out =
            new StringBuilder(text.length());
        boolean tag = false;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                tag = true;
                continue;
            }
            if (ch == '>' && tag) {
                tag = false;
                continue;
            }
            if (!tag) {
                out.append(ch);
            }
        }

        return out.toString();
    }

    private static void drawAsset(
        String path,
        ModernUiRect rect
    ) {
        ModernUiImage image =
            ModernUiAssetResolver.get(
                path,
                rect.width,
                rect.height
            );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + path,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }
}
