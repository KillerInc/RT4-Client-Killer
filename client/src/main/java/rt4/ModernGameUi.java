package rt4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
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
    private static final List<Component> tabs =
        new ArrayList<>();

    private static final String[] TAB_ICON_ASSETS = {
        "game-ui/tabs/combat",
        "game-ui/tabs/stats",
        "game-ui/tabs/quests",
        "game-ui/tabs/inventory",
        "game-ui/tabs/equipment",
        "game-ui/tabs/prayer",
        "game-ui/tabs/magic",
        "game-ui/tabs/lore",
        "game-ui/tabs/friends",
        "game-ui/tabs/ignore",
        "game-ui/tabs/clan",
        "game-ui/tabs/options",
        "game-ui/tabs/emotes",
        "game-ui/tabs/music"
    };

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
            || !isTopLevelComponents(components)
            || preparedLoop == client.loop) {
            return;
        }

        preparedLoop = client.loop;
        renderBounds.clear();
        tabs.clear();
        layout = ModernGameFrameLayout.create(
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        List<TabCandidate> candidates =
            new ArrayList<>();
        discover(
            components,
            -1,
            0,
            0,
            candidates
        );
        selectTabRow(candidates);
    }

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!isInGame()
            || components == null
            || !isTopLevelComponents(components)) {
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

        int count =
            ModernGameFrameLayout.TAB_COUNT;
        for (int i = 0; i < count; i++) {
            ModernUiRect slot =
                layout.tabSlot(i);

            // Every Modern tab slot owns its rectangle even while locked.
            // This prevents world clicks from passing through empty tutorial
            // slots and prevents a hidden/locked tab from opening.
            ModernUiInputRouter.blockWorldInput(slot);
            ModernUiInputRouter.registerModernRegion(
                "game-tab-" + i,
                slot,
                screen
            );

            boolean unlocked =
                ModernTutorialState.isUnlocked(i);
            Component component =
                i < tabs.size()
                    ? tabs.get(i)
                    : null;

            if (unlocked && component != null) {
                ModernUiInputRouter.bind(
                    component,
                    slot,
                    screen
                );
            } else if (Mouse.clickButton == 1
                && slot.contains(
                    Mouse.clickX,
                    Mouse.clickY
                )) {
                Mouse.clickButton = 0;
                MiniMenu.clickProcessingState = 0;
            }
        }
    }

    private static boolean isTopLevelComponents(
        Component[] components
    ) {
        if (components == null
            || InterfaceList.topLevelInterface < 0) {
            return false;
        }

        for (Component component : components) {
            if (component != null
                && component.id != -1
                && component.id >>> 16
                    == InterfaceList.topLevelInterface) {
                return true;
            }
        }
        return false;
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

        int count =
            ModernGameFrameLayout.TAB_COUNT;
        for (int i = 0; i < count; i++) {
            Component component =
                i < tabs.size()
                    ? tabs.get(i)
                    : null;
            renderTab(
                i,
                component,
                layout.tabSlot(i)
            );
        }
    }

    private static void discover(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<TabCandidate> candidates
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null
                || component.overlayer != layer) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;

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
            } else if (component.clientCode == 0
                && component.type == 5
                && isInteractive(component)) {
                candidates.add(
                    new TabCandidate(
                        component,
                        x,
                        y
                    )
                );
            }

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                discover(
                    components,
                    component.id,
                    childX,
                    childY,
                    candidates
                );
                if (component.createdComponents != null) {
                    discover(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        candidates
                    );
                }
            }
        }
    }

    private static void selectTabRow(
        List<TabCandidate> candidates
    ) {
        if (candidates.isEmpty()) {
            return;
        }

        List<TabCandidate> best =
            new ArrayList<>();

        for (TabCandidate seed : candidates) {
            List<TabCandidate> row =
                new ArrayList<>();
            for (TabCandidate candidate : candidates) {
                if (Math.abs(
                    candidate.y - seed.y
                ) <= 8) {
                    row.add(candidate);
                }
            }
            if (row.size() > best.size()) {
                best = row;
            }
        }

        Collections.sort(
            best,
            Comparator.comparingInt(
                candidate -> candidate.x
            )
        );

        int count = Math.min(
            ModernGameFrameLayout.TAB_COUNT,
            best.size()
        );
        for (int i = 0; i < count; i++) {
            tabs.add(best.get(i).component);
        }
    }

    private static void renderTab(
        int index,
        Component component,
        ModernUiRect rect
    ) {
        boolean unlocked =
            ModernTutorialState.isUnlocked(index);
        boolean active =
            unlocked
                && component != null
                && Cs1ScriptRunner.isTrue(component);
        boolean hover =
            unlocked
                && rect.contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY
                );

        drawAsset(
            active || hover
                ? "game-ui/slot-hover"
                : "game-ui/slot",
            rect
        );

        // Vanilla Tutorial Island removes unavailable tabs entirely. Keep the
        // Modern strip geometry stable, but mirror that behavior by hiding the
        // icon and refusing input until the server unlocks the slot.
        if (unlocked
            && index >= 0
            && index < TAB_ICON_ASSETS.length) {
            int iconSize =
                Math.max(
                    16,
                    Math.min(
                        24,
                        rect.width - 8
                    )
                );
            ModernUiRect iconRect =
                new ModernUiRect(
                    rect.centerX() - iconSize / 2,
                    rect.centerY() - iconSize / 2,
                    iconSize,
                    iconSize
                );
            drawAsset(
                TAB_ICON_ASSETS[index],
                iconRect
            );
        }

        if (ModernTutorialState.getHighlightedSlot()
                == index
            && unlocked) {
            int bouncePhase =
                client.loop % 20;
            int bounce =
                bouncePhase < 10
                    ? bouncePhase / 2
                    : (20 - bouncePhase) / 2;
            ModernUiRect arrow =
                new ModernUiRect(
                    rect.centerX() - 11,
                    rect.y - 20 - bounce,
                    22,
                    18
                );
            drawAsset(
                "game-ui/tabs/tutorial-arrow",
                arrow
            );
        }
    }

    private static boolean isInteractive(
        Component component
    ) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
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

    private static final class TabCandidate {
        private final Component component;
        private final int x;
        private final int y;

        private TabCandidate(
            Component component,
            int x,
            int y
        ) {
            this.component = component;
            this.x = x;
            this.y = y;
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
