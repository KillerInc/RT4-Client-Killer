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
        int index = 0;
        int columns = Math.max(1, component.baseWidth);
        int rows = Math.max(1, component.baseHeight);

        for (int row = 0; row < rows; row++) {
            for (int column = 0;
                column < columns;
                column++) {
                int x =
                    grid.x
                        + column
                            * (component.invMarginX + 32);
                int y =
                    grid.y
                        + row
                            * (component.invMarginY + 32);

                if (index < 20
                    && component.invOffsetX != null
                    && component.invOffsetY != null) {
                    x += component.invOffsetX[index];
                    y += component.invOffsetY[index];
                }

                ModernUiRect slot =
                    new ModernUiRect(x, y, 32, 32);
                boolean hover =
                    slot.contains(
                        Mouse.lastMouseX,
                        Mouse.lastMouseY
                    );

                drawAsset(
                    hover
                        ? "game-ui/slot-hover"
                        : "game-ui/slot",
                    slot
                );

                if (component.objTypes != null
                    && index < component.objTypes.length
                    && component.objTypes[index] > 0) {
                    int objectId =
                        component.objTypes[index] - 1;
                    int count =
                        component.objCounts != null
                            && index < component.objCounts.length
                                ? component.objCounts[index]
                                : 1;
                    Sprite item =
                        Inv.getObjectSprite(
                            1,
                            objectId,
                            component.objDrawText,
                            count,
                            3153952
                        );
                    if (item != null) {
                        if (component
                                == InterfaceList.clickedInventoryComponent
                            && index
                                == InterfaceList.mouseOverInventoryObjectIndex) {
                            int dragX =
                                Mouse.lastMouseX
                                    - InterfaceList
                                        .clickedInventoryComponentX;
                            int dragY =
                                Mouse.lastMouseY
                                    - InterfaceList
                                        .clickedInventoryComponentY;

                            if (Math.abs(dragX) < 5) {
                                dragX = 0;
                            }
                            if (Math.abs(dragY) < 5) {
                                dragY = 0;
                            }
                            if (InterfaceList
                                    .clickedInventoryComponentCycle < 5) {
                                dragX = 0;
                                dragY = 0;
                            }

                            item.renderAlpha(
                                x + dragX,
                                y + dragY,
                                128
                            );
                        } else if (component
                                == MiniMenu.pressedInventoryComponent
                            && index
                                == MiniMenu.pressedSlotIndex) {
                            item.renderAlpha(x, y, 128);
                        } else {
                            item.render(x, y);
                        }
                    }
                }

                index++;
            }
        }
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
