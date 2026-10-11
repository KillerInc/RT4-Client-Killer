package rt4;

/**
 * Shared Modern renderer for item-grid components. Item sprites are game
 * content; slot chrome and geometry are Modern-owned.
 */
public final class ModernItemGridRenderer {
    private ModernItemGridRenderer() {
    }

    public static void render(
        Component component,
        ModernUiRect grid
    ) {
        if (component == null
            || component.objTypes == null
            || grid == null) {
            return;
        }

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

                if (index < component.objTypes.length
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
                        renderItem(
                            component,
                            item,
                            index,
                            x,
                            y
                        );
                    }
                }

                index++;
            }
        }
    }

    private static void renderItem(
        Component component,
        Sprite item,
        int index,
        int x,
        int y
    ) {
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
