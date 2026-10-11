package rt4;

/**
 * Fully Modern-owned inventory side panel.
 *
 * Slot counts/contents/options remain game data. Absolute geometry, panel
 * chrome, slot chrome, text and mouse bounds are Modern-owned.
 */
public final class ModernInventoryPanelUi {
    public static final int INTERFACE_ID = 149;

    private ModernInventoryPanelUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && interfaceId == INTERFACE_ID;
    }

    public static boolean isInventoryComponents(
        Component[] components
    ) {
        if (!ModernGameUi.isInGame()
            || components == null) {
            return false;
        }

        for (Component component : components) {
            if (component != null
                && component.id != -1
                && component.id >>> 16 == INTERFACE_ID) {
                return true;
            }
        }
        return false;
    }

    public static void prepareInput(
        Component[] components
    ) {
        if (!isInventoryComponents(components)) {
            return;
        }

        Component inventory =
            findInventory(components);
        if (inventory == null) {
            return;
        }

        ModernInventoryPanelLayout layout =
            ModernInventoryPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                inventory
            );

        ModernUiInputRouter.bind(
            inventory,
            layout.grid,
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            )
        );
    }

    public static void render(
        Component[] components
    ) {
        Component inventory =
            findInventory(components);
        if (inventory == null) {
            return;
        }

        ModernInventoryPanelLayout layout =
            ModernInventoryPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                inventory
            );

        prepareInput(components);

        drawAsset("game-ui/panel", layout.panel);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            "Inventory",
            layout.title.x,
            layout.title.y,
            layout.title.width,
            layout.title.height,
            ModernUiMetrics.TEXT_GOLD,
            1,
            1,
            ModernUiMetrics.FONT_SECTION,
            false
        );

        renderGrid(inventory, layout.grid);
    }

    private static Component findInventory(
        Component[] components
    ) {
        if (components == null) {
            return null;
        }

        Component best = null;
        int bestSlots = -1;

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if (component.type == 2
                && component.objTypes != null) {
                int slots =
                    Math.max(1, component.baseWidth)
                        * Math.max(1, component.baseHeight);
                if (slots > bestSlots) {
                    bestSlots = slots;
                    best = component;
                }
            }

            Component nested =
                findInventory(component.createdComponents);
            if (nested != null) {
                int slots =
                    Math.max(1, nested.baseWidth)
                        * Math.max(1, nested.baseHeight);
                if (slots > bestSlots) {
                    bestSlots = slots;
                    best = nested;
                }
            }
        }

        return best;
    }

    private static void renderGrid(
        Component component,
        ModernUiRect grid
    ) {
        ModernItemGridRenderer.render(component, grid);
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
