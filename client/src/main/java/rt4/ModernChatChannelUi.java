package rt4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern chat channel selector bar.
 *
 * The legacy chatbox exposes generic "Ok" text for several controls, so the
 * Modern renderer supplies the semantic channel labels while preserving the
 * original interactive components and their backend actions.
 */
public final class ModernChatChannelUi {
    private static final int CONTROL_INTERFACE = 754;
    private static final int SUPPORT_INTERFACE = 752;

    private static final String[] LABELS = {
        "All", "Game", "Public", "Private", "Clan", "Trade", "Assist"
    };

    private ModernChatChannelUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.CHAT_CONTROLS;
    }

    public static void prepareInput(Component[] components) {
        if (!handles(interfaceIdOf(components))) {
            return;
        }

        List<Component> controls = discoverControls();
        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        ModernUiRect screen =
            new ModernUiRect(
                0, 0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        int count = Math.min(LABELS.length, controls.size());
        for (int i = 0; i < count; i++) {
            ModernUiInputRouter.bind(
                controls.get(i),
                layout.actionRect(i, LABELS.length),
                screen
            );
        }
    }

    public static void render(int interfaceId, Component[] components) {
        if (!handles(interfaceId)) {
            return;
        }

        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        List<Component> controls = discoverControls();
        int count = LABELS.length;

        for (int i = 0; i < count; i++) {
            ModernUiRect rect =
                layout.actionRect(i, count);
            boolean hover =
                rect.contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY
                );

            drawAsset(
                hover
                    ? "controls/button-active"
                    : "controls/button",
                rect
            );

            String status = statusFor(i);
            int labelHeight = status.isEmpty()
                ? rect.height
                : Math.max(12, rect.height / 2);

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.BOLD_12,
                LABELS[i],
                rect.x + 2,
                rect.y,
                Math.max(1, rect.width - 4),
                labelHeight,
                ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                8.3F,
                false
            );

            if (!status.isEmpty()) {
                ModernTrueTypeFont.drawInBox(
                    ModernUiFontRegistry.PLAIN_11,
                    status,
                    rect.x + 2,
                    rect.y + labelHeight - 1,
                    Math.max(1, rect.width - 4),
                    rect.height - labelHeight + 1,
                    ModernUiMetrics.TEXT_GOLD,
                    1,
                    1,
                    7.4F,
                    false
                );
            }
        }
    }

    private static String statusFor(int index) {
        switch (index) {
            case 0:
                return "";
            case 1:
                return "On";
            case 2:
                return filterName(Chat.publicFilter);
            case 3:
                return filterName(Chat.privateFilter);
            case 4:
                return "On";
            case 5:
                return filterName(Chat.tradeFilter);
            case 6:
                return "On";
            default:
                return "";
        }
    }

    private static String filterName(int value) {
        switch (value) {
            case 0:
                return "On";
            case 1:
                return "Friends";
            case 2:
                return "Off";
            case 3:
                return "Hide";
            default:
                return "On";
        }
    }

    private static List<Component> discoverControls() {
        List<Component> out = new ArrayList<>();
        Map<Component, Boolean> seen = new IdentityHashMap<>();

        collectInterface(SUPPORT_INTERFACE, out, seen);
        collectInterface(CONTROL_INTERFACE, out, seen);

        out.sort(
            Comparator.comparingInt(
                (Component component) -> component.x
            ).thenComparingInt(
                component -> component.y
            )
        );

        if (out.size() > LABELS.length) {
            return new ArrayList<>(
                out.subList(0, LABELS.length)
            );
        }
        return out;
    }

    private static void collectInterface(
        int interfaceId,
        List<Component> out,
        Map<Component, Boolean> seen
    ) {
        if (interfaceId < 0
            || interfaceId >= InterfaceList.components.length
            || !InterfaceList.interfaceLoaded[interfaceId]) {
            return;
        }

        Component[] components =
            InterfaceList.components[interfaceId];
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null
                || component.type == 0
                || seen.containsKey(component)
                || !isInteractive(component)) {
                continue;
            }

            out.add(component);
            seen.put(component, Boolean.TRUE);
        }
    }

    private static boolean isInteractive(Component component) {
        return component.buttonType != 0
            || component.hasEventHandlers
            || component.clientCode != 0
            || InterfaceList.getServerActiveProperties(
                component
            ).events != 0;
    }

    private static int interfaceIdOf(Component[] components) {
        if (components == null) {
            return -1;
        }

        for (Component component : components) {
            if (component != null && component.id != -1) {
                return component.id >>> 16;
            }
        }
        return -1;
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
