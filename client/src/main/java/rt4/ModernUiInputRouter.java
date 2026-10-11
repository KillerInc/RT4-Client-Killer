package rt4;

import java.util.IdentityHashMap;
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
                client.loop
            )
        );
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

        if (down && !f8WasDown) {
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
    }

    public static synchronized void clear() {
        bindings.clear();
    }

    private static final class Binding {
        private final ModernUiRect bounds;
        private final ModernUiRect clip;
        private final int loop;

        private Binding(
            ModernUiRect bounds,
            ModernUiRect clip,
            int loop
        ) {
            this.bounds = bounds;
            this.clip = clip;
            this.loop = loop;
        }
    }
}
