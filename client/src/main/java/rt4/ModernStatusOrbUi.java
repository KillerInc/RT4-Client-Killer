package rt4;

import java.util.ArrayList;
import java.util.List;

/**
 * Modern status orbs for the top-stat HP, Prayer and Run interfaces.
 */
public final class ModernStatusOrbUi {
    public static final int HITPOINTS_INTERFACE = 748;
    public static final int PRAYER_INTERFACE = 749;
    public static final int RUN_INTERFACE = 750;

    private ModernStatusOrbUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && indexFor(interfaceId) >= 0;
    }

    public static boolean isStatusComponents(
        Component[] components
    ) {
        return interfaceIdOf(components) >= 0;
    }

    public static void prepareInput(
        Component[] components
    ) {
        int interfaceId = interfaceIdOf(components);
        int index = indexFor(interfaceId);
        if (!ModernGameUi.isInGame() || index < 0) {
            return;
        }

        Component action =
            findPrimaryAction(components);
        if (action == null) {
            return;
        }

        ModernStatusOrbLayout layout =
            ModernStatusOrbLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                index
            );

        ModernUiInputRouter.bind(
            action,
            layout.orb,
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            )
        );
    }

    public static void render(
        int interfaceId,
        Component[] components
    ) {
        int index = indexFor(interfaceId);
        if (index < 0) {
            return;
        }

        ModernStatusOrbLayout layout =
            ModernStatusOrbLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                index
            );

        prepareInput(components);

        boolean active =
            isActive(components);
        boolean hover =
            layout.orb.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            );

        drawAsset(
            active || hover
                ? "game-ui/orb-active"
                : "game-ui/orb",
            layout.orb
        );

        String label =
            interfaceId == HITPOINTS_INTERFACE
                ? "HP"
                : interfaceId == PRAYER_INTERFACE
                    ? "PR"
                    : "RUN";
        String value =
            findValue(components);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            label,
            layout.orb.x,
            layout.orb.y + 7,
            layout.orb.width,
            16,
            active
                ? ModernUiMetrics.TEXT_GOLD
                : ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            9.0F,
            false
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            value,
            layout.orb.x,
            layout.orb.y + 23,
            layout.orb.width,
            20,
            ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            12.0F,
            false
        );
    }

    private static int indexFor(int interfaceId) {
        if (interfaceId == HITPOINTS_INTERFACE) {
            return 0;
        }
        if (interfaceId == PRAYER_INTERFACE) {
            return 1;
        }
        if (interfaceId == RUN_INTERFACE) {
            return 2;
        }
        return -1;
    }

    private static int interfaceIdOf(
        Component[] components
    ) {
        if (components == null) {
            return -1;
        }

        for (Component component : components) {
            if (component == null || component.id == -1) {
                continue;
            }

            int interfaceId =
                component.id >>> 16;
            if (indexFor(interfaceId) >= 0) {
                return interfaceId;
            }
        }
        return -1;
    }

    private static Component findPrimaryAction(
        Component[] components
    ) {
        if (components == null) {
            return null;
        }

        Component fallback = null;
        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if (isInteractive(component)) {
                if (component.type != 0) {
                    return component;
                }
                fallback = component;
            }

            Component nested =
                findPrimaryAction(
                    component.createdComponents
                );
            if (nested != null) {
                return nested;
            }
        }
        return fallback;
    }

    private static boolean isActive(
        Component[] components
    ) {
        if (components == null) {
            return false;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }
            if (isInteractive(component)
                && Cs1ScriptRunner.isTrue(component)) {
                return true;
            }

            if (isActive(component.createdComponents)) {
                return true;
            }
        }
        return false;
    }

    private static String findValue(
        Component[] components
    ) {
        List<String> values = new ArrayList<>();
        collectText(components, values);

        for (String value : values) {
            String clean = clean(value);
            if (looksNumeric(clean)) {
                return clean;
            }
        }

        return "--";
    }

    private static void collectText(
        Component[] components,
        List<String> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if ((component.type == 4
                || component.type == 8)
                && component.text != null
                && component.text.length() > 0) {
                JagString value = component.text;
                if (Cs1ScriptRunner.isTrue(component)
                    && component.activeText != null
                    && component.activeText.length() > 0) {
                    value = component.activeText;
                }
                if (!component.if3 && value != null) {
                    value =
                        Cs1ScriptRunner.interpolate(
                            component,
                            value
                        );
                }
                if (value != null) {
                    out.add(value.toString());
                }
            }

            collectText(
                component.createdComponents,
                out
            );
        }
    }

    private static boolean looksNumeric(
        String value
    ) {
        if (value.isEmpty() || value.length() > 7) {
            return false;
        }

        boolean digit = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch >= '0' && ch <= '9') {
                digit = true;
                continue;
            }
            if (ch != '%' && ch != '/') {
                return false;
            }
        }
        return digit;
    }

    private static String clean(String text) {
        if (text == null) {
            return "";
        }
        return text
            .replaceAll("<[^>]+>", "")
            .trim();
    }

    private static boolean isInteractive(
        Component component
    ) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
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
