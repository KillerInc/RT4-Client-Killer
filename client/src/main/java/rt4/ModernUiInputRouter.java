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
