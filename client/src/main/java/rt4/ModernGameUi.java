package rt4;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Permanent in-game Modern HUD bridge.
 *
 * Components are discovered only by semantic client code. Rendering and input
 * use ModernGameFrameLayout geometry; cache-era coordinates are ignored.
 */
public final class ModernGameUi {
    private static final Map<Component, ModernUiRect> renderBounds =
        new IdentityHashMap<>();

    private static int preparedLoop = -1;
    private static ModernGameFrameLayout layout;

    private ModernGameUi() {
    }

    public static boolean isInGame() {
        return ModernUiManager.isEnabled()
            && client.gameState == 30;
    }

    public static boolean isTopLevel(int interfaceId) {
        return isInGame()
            && interfaceId == InterfaceList.topLevelInterface;
    }

    public static void prepare(
        Component[] components
    ) {
        if (!isInGame()
            || components == null
            || preparedLoop == client.loop) {
            return;
        }

        preparedLoop = client.loop;
        renderBounds.clear();
        layout = ModernGameFrameLayout.create(
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        discover(components, -1);
    }

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!isInGame() || components == null) {
            return;
        }

        prepare(components);

        ModernUiRect screen = new ModernUiRect(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        for (Map.Entry<Component, ModernUiRect> entry
            : renderBounds.entrySet()) {
            ModernUiInputRouter.bind(
                entry.getKey(),
                entry.getValue(),
                screen
            );
        }
    }

    public static ModernUiRect bounds(Component component) {
        if (component == null || preparedLoop != client.loop) {
            return null;
        }
        return renderBounds.get(component);
    }

    public static boolean renderClientComponent(
        Component component,
        int rectangle
    ) {
        ModernUiRect bounds = bounds(component);
        if (bounds == null) {
            return false;
        }

        if (component.clientCode == 1338) {
            MiniMap.renderModern(
                rectangle,
                bounds.y,
                bounds.x,
                bounds.width,
                bounds.height,
                component
            );
            return true;
        }

        if (component.clientCode == 1339) {
            renderCompass(
                component,
                bounds,
                rectangle
            );
            return true;
        }

        return false;
    }

    public static void renderChrome() {
        if (!isInGame() || layout == null) {
            return;
        }

        drawAsset(
            "game-ui/minimap-frame",
            layout.minimapFrame
        );
        drawAsset(
            "game-ui/compass-frame",
            layout.compass
        );
    }

    private static void discover(
        Component[] components,
        int layer
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null
                || component.overlayer != layer) {
                continue;
            }

            if (component.clientCode == 1338) {
                renderBounds.put(
                    component,
                    layout.minimap
                );
            } else if (component.clientCode == 1339) {
                renderBounds.put(
                    component,
                    layout.compass
                );
            }

            if (component.type == 0) {
                discover(
                    components,
                    component.id
                );
                if (component.createdComponents != null) {
                    discover(
                        component.createdComponents,
                        component.id
                    );
                }
            }
        }
    }

    private static void renderCompass(
        Component component,
        ModernUiRect bounds,
        int rectangle
    ) {
        setClip(bounds);

        if (MiniMap.state < 3
            && Sprites.compass != null) {
            if (GlRenderer.enabled
                && Sprites.compass instanceof GlSprite) {
                ((GlSprite) Sprites.compass).renderRotatedRect(
                    bounds.x,
                    bounds.y,
                    bounds.width,
                    bounds.height,
                    Sprites.compass.width / 2,
                    Sprites.compass.height / 2,
                    (int) Camera.yawTarget,
                    256
                );
            } else if (Sprites.compass instanceof SoftwareSprite) {
                int[][] mask =
                    ModernMinimapMask.full(
                        bounds.width,
                        bounds.height
                    );
                ((SoftwareSprite) Sprites.compass).renderRotated(
                    bounds.x,
                    bounds.y,
                    bounds.width,
                    bounds.height,
                    Sprites.compass.width / 2,
                    Sprites.compass.height / 2,
                    (int) Camera.yawTarget,
                    mask[0],
                    mask[1]
                );
            }
        }

        if (rectangle >= 0
            && rectangle < InterfaceList.rectangleRedraw.length) {
            InterfaceList.rectangleRedraw[rectangle] = true;
        }

        resetClip();
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

    private static void setClip(ModernUiRect rect) {
        if (GlRenderer.enabled) {
            GlRaster.setClip(
                rect.x,
                rect.y,
                rect.right(),
                rect.bottom()
            );
        } else {
            SoftwareRaster.setClip(
                rect.x,
                rect.y,
                rect.right(),
                rect.bottom()
            );
            Rasteriser.prepare();
        }
    }

    private static void resetClip() {
        if (GlRenderer.enabled) {
            GlRaster.setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        } else {
            SoftwareRaster.setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            Rasteriser.prepare();
        }
    }
}
