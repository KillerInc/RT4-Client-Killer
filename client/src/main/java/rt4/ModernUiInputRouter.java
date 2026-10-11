package rt4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bridges Modern-owned interaction rectangles to the original RT4 component
 * action/state backend.
 *
 * Important: this class never moves a Component and never changes parent/child
 * layout. InterfaceList asks it only for the event rectangle of a component.
 * Child recursion continues to use the untouched vanilla geometry. This keeps
 * Modern UI editing isolated from the cache-era widget tree.
 */
public final class ModernUiInputRouter {
    private static final Map<Component, Binding> bindings =
        new IdentityHashMap<>();
    private static final List<Blocker> blockers =
        new ArrayList<>();
    private static final List<DebugRegion> debugRegions =
        new ArrayList<>();
    private static final Map<Component, Integer> directHandledLoops =
        new IdentityHashMap<>();
    private static final Map<Component, Integer> menuPreparedLoops =
        new IdentityHashMap<>();
    private static int prepareSerial;
    private static int activePrepareSerial;

    private static final int DEBUG_FILL = 0x77CFFF;
    private static final int DEBUG_BORDER = 0xA8E4FF;
    private static boolean debugHitboxes;
    private static boolean f8WasDown;

    private ModernUiInputRouter() {
    }

    public static synchronized void bind(
        Component component,
        ModernUiRect bounds,
        ModernUiRect clip
    ) {
        if (component == null || bounds == null) {
            return;
        }
        bindings.put(
            component,
            new Binding(
                bounds,
                clip == null ? bounds : clip,
                client.loop,
                activePrepareSerial
            )
        );
    }

    public static synchronized void beginFrame() {
        bindings.clear();
        blockers.clear();
        debugRegions.clear();
        directHandledLoops.clear();
        menuPreparedLoops.clear();
        activePrepareSerial = 0;
    }

    public static synchronized void registerModernRegion(
        String label,
        ModernUiRect bounds,
        ModernUiRect clip
    ) {
        if (bounds == null || !ModernUiManager.isEnabled()) {
            return;
        }
        debugRegions.add(
            new DebugRegion(
                label == null ? "" : label,
                bounds,
                clip == null ? bounds : clip,
                client.loop
            )
        );
    }

    public static synchronized void blockWorldInput(
        ModernUiRect bounds
    ) {
        if (bounds == null || !ModernUiManager.isEnabled()) {
            return;
        }

        for (int i = blockers.size() - 1; i >= 0; i--) {
            Blocker blocker = blockers.get(i);
            if (blocker == null || blocker.loop != client.loop) {
                blockers.remove(i);
            }
        }

        for (Blocker blocker : blockers) {
            if (blocker != null
                && blocker.loop == client.loop
                && blocker.bounds.x == bounds.x
                && blocker.bounds.y == bounds.y
                && blocker.bounds.width == bounds.width
                && blocker.bounds.height == bounds.height) {
                return;
            }
        }

        blockers.add(
            new Blocker(
                bounds,
                client.loop
            )
        );
    }

    public static synchronized boolean isWorldInputBlocked(
        int x,
        int y
    ) {
        if (!ModernUiManager.isEnabled()) {
            return false;
        }

        for (Blocker blocker : blockers) {
            if (blocker != null
                && blocker.loop == client.loop
                && blocker.bounds.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean isOverBoundControl(
        int x,
        int y
    ) {
        if (!ModernUiManager.isEnabled()) {
            return false;
        }

        for (Binding binding : bindings.values()) {
            if (binding == null || binding.loop != client.loop) {
                continue;
            }

            ModernUiRect bounds = binding.bounds;
            ModernUiRect clip = binding.clip;
            if (bounds == null || !bounds.contains(x, y)) {
                continue;
            }
            if (clip == null || clip.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean has(Component component) {
        Binding binding = bindings.get(component);
        return binding != null
            && binding.loop == client.loop
            && ModernUiManager.isEnabled();
    }

    public static synchronized ModernUiRect bounds(Component component) {
        Binding binding = bindings.get(component);
        if (binding == null
            || binding.loop != client.loop
            || !ModernUiManager.isEnabled()) {
            return null;
        }
        return binding.bounds;
    }

    public static synchronized ModernUiRect clip(Component component) {
        Binding binding = bindings.get(component);
        if (binding == null
            || binding.loop != client.loop
            || !ModernUiManager.isEnabled()) {
            return null;
        }
        return binding.clip;
    }

    public static void prepare(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!ModernUiManager.isEnabled() || components == null) {
            return;
        }

        final int serial;
        synchronized (ModernUiInputRouter.class) {
            serial = ++prepareSerial;
            activePrepareSerial = serial;
        }

        // Each screen adapter discovers only action/state components. Geometry
        // always comes from the corresponding Modern layout.
        ModernLoginScreenUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernTitleMenuUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernAudioOptionsUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernGraphicsOptionsUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernWelcomeUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernGameUi.prepareInput(
            components,
            parentX,
            parentY
        );
        ModernInventoryPanelUi.prepareInput(
            components
        );
        ModernSidebarPanelUi.prepareInput(
            components
        );
        ModernChatPanelUi.prepareInput(
            components
        );
        ModernQuestChatUi.prepareInput(
            components
        );
        ModernCharacterSetupUi.prepareInput(
            interfaceIdOf(components),
            components
        );
        ModernInGameDialogUi.prepareInput(
            components
        );
        int interfaceId =
            interfaceIdOf(components);
        ModernBankUi.prepareInput(
            interfaceId,
            components
        );
        ModernShopUi.prepareInput(
            interfaceId,
            components
        );
        ModernTradeUi.prepareInput(
            interfaceId,
            components
        );
        ModernWorldMapUi.prepareInput(
            interfaceId,
            components
        );
        ModernGrandExchangeSearchUi.prepareInput(
            interfaceId,
            components
        );
        ModernGrandExchangeUi.prepareInput(
            interfaceId,
            components
        );
        ModernStatusOrbUi.prepareInput(
            components
        );
        ModernUiRenderer.prepareMainMenuInput(
            components,
            parentX,
            parentY
        );

        processPreparedInput(serial);

        synchronized (ModernUiInputRouter.class) {
            if (activePrepareSerial == serial) {
                activePrepareSerial = 0;
            }
        }
    }

    private static void processPreparedInput(
        int serial
    ) {
        List<Map.Entry<Component, Binding>> prepared =
            new ArrayList<>();

        synchronized (ModernUiInputRouter.class) {
            for (Map.Entry<Component, Binding> entry
                    : bindings.entrySet()) {
                Binding binding = entry.getValue();
                if (binding != null
                    && binding.loop == client.loop
                    && binding.prepareSerial == serial) {
                    prepared.add(entry);
                }
            }
        }

        // More specific Modern rectangles win when controls overlap.
        prepared.sort(
            Comparator.comparingInt(
                entry -> area(effectiveBounds(entry.getValue()))
            )
        );

        // Build in-game component menus from Modern geometry too.
        // Inventory/item actions, right-click menus and other multi-op
        // controls must not need the cache-era component rectangle to be
        // discoverable. Title/login screens keep their original non-menu
        // action paths.
        if (client.gameState == 30) {
            for (Map.Entry<Component, Binding> entry : prepared) {
                Component component = entry.getKey();
                Binding binding = entry.getValue();
                ModernUiRect hit = effectiveBounds(binding);
                if (component == null
                    || hit == null
                    || component.clientCode != 0
                    || !hit.contains(
                        Mouse.lastMouseX,
                        Mouse.lastMouseY
                    )) {
                    continue;
                }

                MiniMenu.addComponentEntries(
                    Mouse.lastMouseY - binding.bounds.y,
                    Mouse.lastMouseX - binding.bounds.x,
                    component
                );
                synchronized (ModernUiInputRouter.class) {
                    menuPreparedLoops.put(
                        component,
                        client.loop
                    );
                }
            }
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        for (Map.Entry<Component, Binding> entry : prepared) {
            Component component = entry.getKey();
            Binding binding = entry.getValue();
            ModernUiRect hit = effectiveBounds(binding);
            if (component == null
                || hit == null
                || !hit.contains(
                    Mouse.clickX,
                    Mouse.clickY
                )) {
                continue;
            }

            if (dispatchBoundPrimaryClick(
                    component,
                    hit
                )) {
                DisplayDebug.log(
                    "MODERN_UI RECT_CLICK"
                        + " interface="
                            + (component.id >>> 16)
                        + " component="
                            + (component.id & 0xFFFF)
                        + " rect="
                            + hit.x + "," + hit.y
                            + "," + hit.width + "x"
                            + hit.height
                );
                Mouse.clickButton = 0;
                MiniMenu.clickProcessingState = 0;
                return;
            }
        }
    }

    public static synchronized boolean isMenuPrepared(
        Component component
    ) {
        Integer loop = menuPreparedLoops.get(component);
        return loop != null && loop == client.loop;
    }

    private static ModernUiRect effectiveBounds(
        Binding binding
    ) {
        if (binding == null || binding.bounds == null) {
            return null;
        }

        ModernUiRect bounds = binding.bounds;
        ModernUiRect clip = binding.clip;
        if (clip == null) {
            return bounds;
        }

        int left = Math.max(bounds.x, clip.x);
        int top = Math.max(bounds.y, clip.y);
        int right = Math.min(
            bounds.right(),
            clip.right()
        );
        int bottom = Math.min(
            bounds.bottom(),
            clip.bottom()
        );

        if (right <= left || bottom <= top) {
            return null;
        }
        return new ModernUiRect(
            left,
            top,
            right - left,
            bottom - top
        );
    }

    private static int area(ModernUiRect rect) {
        if (rect == null) {
            return Integer.MAX_VALUE;
        }
        long area =
            (long) rect.width * (long) rect.height;
        return area > Integer.MAX_VALUE
            ? Integer.MAX_VALUE
            : (int) area;
    }

    private static int interfaceIdOf(
        Component[] components
    ) {
        if (components == null) {
            return -1;
        }
        for (Component component : components) {
            if (component != null
                && component.id != -1) {
                return component.id >>> 16;
            }
        }
        return -1;
    }

    public static void updateDebugToggle() {
        boolean down =
            Keyboard.pressedKeys.length > 8
                && Keyboard.pressedKeys[8];
        boolean requested =
            Keyboard.consumeModernF8ToggleRequest();

        if (requested || down && !f8WasDown) {
            debugHitboxes = !debugHitboxes;
            GameShell.fullRedraw = true;
            InterfaceList.fullRedrawAllInterfaces();
            DisplayDebug.log(
                "MODERN_UI hitbox debug "
                    + (debugHitboxes ? "enabled" : "disabled")
                    + " via F8"
            );
        }

        f8WasDown = down;
    }

    public static boolean isDebugHitboxesEnabled() {
        return debugHitboxes;
    }

    public static boolean dispatchBoundPrimaryClick(
        Component component,
        ModernUiRect bounds
    ) {
        if (component == null
            || bounds == null
            || Mouse.clickButton != 1
            || !bounds.contains(Mouse.clickX, Mouse.clickY)) {
            return false;
        }

        Integer handledLoop =
            directHandledLoops.get(component);
        if (handledLoop != null
            && handledLoop == client.loop) {
            return true;
        }

        int opIndex = -1;
        JagString opBase = JagString.EMPTY;
        if (component.ops != null) {
            for (int i = 0; i < component.ops.length; i++) {
                JagString op = component.ops[i];
                if (op == null || op.trim().length() == 0) {
                    continue;
                }
                if (opIndex != -1) {
                    return false;
                }
                opIndex = i;
                opBase = op;
            }
        }

        boolean localClick = false;

        // IF3 "onclick" handlers are stored as onClickRepeat in this client.
        // Mouse-style setup cards use onclick rather than onop, so invoke the
        // same client script the vanilla traversal would schedule.
        if (component.onClickRepeat != null) {
            HookRequest clickHook = new HookRequest();
            clickHook.arguments = component.onClickRepeat;
            clickHook.source = component;
            int localX = Mouse.clickX - bounds.x;
            int localY = Mouse.clickY - bounds.y;
            clickHook.mouseX =
                localX * Math.max(1, component.width)
                    / Math.max(1, bounds.width);
            clickHook.mouseY =
                localY * Math.max(1, component.height)
                    / Math.max(1, bounds.height);
            ScriptRunner.run(clickHook);
            localClick = true;
        }

        // Use the original button dispatcher for normal ops. It already runs
        // onOptionClick scripts, handles special client actions, validates
        // server-active properties and sends the correct packet where needed.
        if (opIndex != -1) {
            ClientProt.sendButtonClick(
                opBase,
                component.createdComponentId,
                opIndex + 1,
                component.id
            );
            localClick = true;
        }

        if (!localClick) {
            return false;
        }

        directHandledLoops.put(
            component,
            client.loop
        );
        MiniMenu.clickProcessingState = 0;

        DisplayDebug.log(
            "MODERN_UI BOUND_DIRECT_CLICK"
                + " interface=" + (component.id >>> 16)
                + " component=" + (component.id & 0xFFFF)
                + " op=" + (opIndex + 1)
                + " onclick=" + (component.onClickRepeat != null)
                + " onop=" + (component.onOptionClick != null)
                + " text=" + opBase.toString()
        );

        return true;
    }

    public static boolean dispatchPrimaryClick(
        Component component
    ) {
        ModernUiRect modernBounds = bounds(component);
        if (modernBounds == null) {
            return false;
        }
        return dispatchBoundPrimaryClick(
            component,
            modernBounds
        );
    }

    public static void logDebugClick(Component component) {
        if (!debugHitboxes
            || component == null
            || !has(component)) {
            return;
        }

        StringBuilder ops = new StringBuilder();
        if (component.ops != null) {
            for (int i = 0; i < component.ops.length; i++) {
                JagString op = component.ops[i];
                if (op == null || op.length() == 0) {
                    continue;
                }
                if (ops.length() > 0) {
                    ops.append(" | ");
                }
                ops.append(i + 1)
                    .append(":")
                    .append(op.toString());
            }
        }

        ModernUiRect bounds = bounds(component);
        ModernUiRect clip = clip(component);

        DisplayDebug.log(
            "MODERN_UI HITBOX_CLICK"
                + " interface=" + (component.id >>> 16)
                + " component=" + (component.id & 0xFFFF)
                + " id=" + component.id
                + " type=" + component.type
                + " clientCode=" + component.clientCode
                + " buttonType=" + component.buttonType
                + " ops=[" + ops + "]"
                + " onOptionClick=" + (component.onOptionClick != null)
                + " onClickRepeat=" + (component.onClickRepeat != null)
                + " onHold=" + (component.onHold != null)
                + " hasEventHandlers=" + component.hasEventHandlers
                + (bounds == null
                    ? ""
                    : " bounds="
                        + bounds.x + "," + bounds.y
                        + "," + bounds.width + "x" + bounds.height)
                + (clip == null
                    ? ""
                    : " clip="
                        + clip.x + "," + clip.y
                        + "," + clip.width + "x" + clip.height)
        );
    }

    public static synchronized void renderDebugOverlay() {
        if (!debugHitboxes || !ModernUiManager.isEnabled()) {
            return;
        }

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

        for (Binding binding : bindings.values()) {
            if (binding == null || binding.loop != client.loop) {
                continue;
            }

            ModernUiRect bounds = binding.bounds;
            ModernUiRect clip = binding.clip;
            if (bounds == null) {
                continue;
            }

            int left = Math.max(0, bounds.x);
            int top = Math.max(0, bounds.y);
            int right = Math.min(
                GameShell.canvasWidth,
                bounds.right()
            );
            int bottom = Math.min(
                GameShell.canvasHeight,
                bounds.bottom()
            );

            if (clip != null) {
                left = Math.max(left, clip.x);
                top = Math.max(top, clip.y);
                right = Math.min(right, clip.right());
                bottom = Math.min(bottom, clip.bottom());
            }

            int width = right - left;
            int height = bottom - top;
            if (width <= 0 || height <= 0) {
                continue;
            }

            if (GlRenderer.enabled) {
                GlRaster.fillRectAlpha(
                    left,
                    top,
                    width,
                    height,
                    DEBUG_FILL,
                    56
                );
                GlRaster.drawRectAlpha(
                    left,
                    top,
                    width,
                    height,
                    DEBUG_BORDER,
                    220
                );
            } else {
                SoftwareRaster.fillRectAlpha(
                    left,
                    top,
                    width,
                    height,
                    DEBUG_FILL,
                    56
                );
                SoftwareRaster.drawRectAlpha(
                    left,
                    top,
                    width,
                    height,
                    DEBUG_BORDER,
                    220
                );
            }
        }

        for (DebugRegion region : debugRegions) {
            if (region == null || region.loop != client.loop) {
                continue;
            }

            ModernUiRect bounds = region.bounds;
            ModernUiRect clip = region.clip;
            int left = Math.max(0, bounds.x);
            int top = Math.max(0, bounds.y);
            int right = Math.min(
                GameShell.canvasWidth,
                bounds.right()
            );
            int bottom = Math.min(
                GameShell.canvasHeight,
                bounds.bottom()
            );
            if (clip != null) {
                left = Math.max(left, clip.x);
                top = Math.max(top, clip.y);
                right = Math.min(right, clip.right());
                bottom = Math.min(bottom, clip.bottom());
            }

            int width = right - left;
            int height = bottom - top;
            if (width <= 0 || height <= 0) {
                continue;
            }

            if (GlRenderer.enabled) {
                GlRaster.fillRectAlpha(
                    left, top, width, height,
                    DEBUG_FILL, 56
                );
                GlRaster.drawRectAlpha(
                    left, top, width, height,
                    DEBUG_BORDER, 220
                );
            } else {
                SoftwareRaster.fillRectAlpha(
                    left, top, width, height,
                    DEBUG_FILL, 56
                );
                SoftwareRaster.drawRectAlpha(
                    left, top, width, height,
                    DEBUG_BORDER, 220
                );
            }
        }
    }

    public static synchronized void clear() {
        bindings.clear();
        blockers.clear();
        debugRegions.clear();
        directHandledLoops.clear();
        menuPreparedLoops.clear();
        activePrepareSerial = 0;
    }

    private static final class DebugRegion {
        private final String label;
        private final ModernUiRect bounds;
        private final ModernUiRect clip;
        private final int loop;

        private DebugRegion(
            String label,
            ModernUiRect bounds,
            ModernUiRect clip,
            int loop
        ) {
            this.label = label;
            this.bounds = bounds;
            this.clip = clip;
            this.loop = loop;
        }
    }

    private static final class Blocker {
        private final ModernUiRect bounds;
        private final int loop;

        private Blocker(
            ModernUiRect bounds,
            int loop
        ) {
            this.bounds = bounds;
            this.loop = loop;
        }
    }

    private static final class Binding {
        private final ModernUiRect bounds;
        private final ModernUiRect clip;
        private final int loop;
        private final int prepareSerial;

        private Binding(
            ModernUiRect bounds,
            ModernUiRect clip,
            int loop,
            int prepareSerial
        ) {
            this.bounds = bounds;
            this.clip = clip;
            this.loop = loop;
            this.prepareSerial = prepareSerial;
        }
    }
}
