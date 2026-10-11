package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern-owned normal chat panel.
 *
 * Quest/NPC dialogue is intentionally excluded; it has its own movable window.
 */
public final class ModernChatPanelUi {
    private ModernChatPanelUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.CHAT;
    }

    public static boolean isChatComponents(
        Component[] components
    ) {
        return handles(interfaceIdOf(components));
    }

    public static void prepareInput(
        Component[] components
    ) {
        Model model = discover(components);
        if (model == null) {
            return;
        }

        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        ModernUiRect screen =
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        int count = Math.min(8, model.actions.size());
        for (int i = 0; i < count; i++) {
            ModernUiInputRouter.bind(
                model.actions.get(i).component,
                layout.actionRect(i, count),
                screen
            );
        }
    }

    public static void render(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(components);
        if (model == null) {
            return;
        }

        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            "Chat",
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

        int maxLines =
            Math.max(
                1,
                layout.messages.height / 18
            );
        int start =
            Math.max(
                0,
                model.messages.size() - maxLines
            );
        int count =
            model.messages.size() - start;

        for (int i = 0; i < count; i++) {
            ModernUiRect row =
                layout.messageLine(i, count);
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                model.messages.get(start + i),
                row.x + 4,
                row.y,
                Math.max(1, row.width - 8),
                row.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
        }

        int actionCount =
            Math.min(8, model.actions.size());
        for (int i = 0; i < actionCount; i++) {
            Action action = model.actions.get(i);
            ModernUiRect rect =
                layout.actionRect(i, actionCount);
            boolean hover =
                rect.contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY
                );
            boolean active =
                Cs1ScriptRunner.isTrue(
                    action.component
                );

            drawAsset(
                active || hover
                    ? "controls/button-active"
                    : "controls/button",
                rect
            );

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                action.label,
                rect.x + 3,
                rect.y,
                Math.max(1, rect.width - 6),
                rect.height,
                active
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                8.5F,
                false
            );
        }
    }

    private static Model discover(
        Component[] components
    ) {
        int interfaceId = interfaceIdOf(components);
        if (!handles(interfaceId)) {
            return null;
        }

        List<Component> flat =
            new ArrayList<>();
        collect(components, flat);

        List<String> messages =
            new ArrayList<>();
        List<Action> actions =
            new ArrayList<>();
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();

        for (Component component : flat) {
            if (component == null) {
                continue;
            }

            String text = currentText(component);
            if (!text.isEmpty()
                && !containsIgnoreCase(
                    messages,
                    text
                )) {
                messages.add(text);
            }

            if (component.type != 0
                && isInteractive(component)
                && !seen.containsKey(component)) {
                String label =
                    actionLabel(
                        component,
                        text
                    );
                if (!label.isEmpty()) {
                    actions.add(
                        new Action(
                            component,
                            label
                        )
                    );
                    seen.put(
                        component,
                        Boolean.TRUE
                    );
                }
            }
        }

        if (messages.size() > 30) {
            messages =
                new ArrayList<>(
                    messages.subList(
                        messages.size() - 30,
                        messages.size()
                    )
                );
        }

        return new Model(
            messages,
            actions
        );
    }

    private static void collect(
        Component[] components,
        List<Component> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }
            if (component.if3
                && InterfaceList.isHidden(component)) {
                continue;
            }

            out.add(component);

            if (component.createdComponents != null) {
                collect(
                    component.createdComponents,
                    out
                );
            }
        }
    }

    private static String currentText(
        Component component
    ) {
        if (component.type != 4
            && component.type != 7
            && component.type != 8) {
            return "";
        }

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

        return clean(
            value == null
                ? ""
                : value.toString()
        );
    }

    private static String actionLabel(
        Component component,
        String text
    ) {
        if (text != null && !text.isEmpty()) {
            return shorten(text);
        }

        if (component.optionBase != null
            && component.optionBase.length() > 0) {
            return shorten(
                component.optionBase.toString()
            );
        }
        if (component.option != null
            && component.option.length() > 0) {
            return shorten(
                component.option.toString()
            );
        }
        if (component.ops != null) {
            for (JagString op : component.ops) {
                if (op != null
                    && op.length() > 0) {
                    return shorten(op.toString());
                }
            }
        }

        return "";
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

    private static boolean containsIgnoreCase(
        List<String> values,
        String candidate
    ) {
        String normalized =
            candidate.toLowerCase(
                Locale.ROOT
            );
        for (String value : values) {
            if (value.toLowerCase(
                Locale.ROOT
            ).equals(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static String shorten(
        String value
    ) {
        String clean = clean(value);
        if (clean.length() <= 18) {
            return clean;
        }
        return clean.substring(0, 17) + "…";
    }

    private static String clean(
        String value
    ) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder out =
            new StringBuilder(value.length());
        boolean tag = false;

        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '<') {
                tag = true;
                continue;
            }
            if (ch == '>' && tag) {
                tag = false;
                out.append(' ');
                continue;
            }
            if (!tag) {
                out.append(
                    ch == '\n'
                        || ch == '\r'
                        || ch == '\t'
                            ? ' '
                            : ch
                );
            }
        }

        return out.toString()
            .replaceAll("\\s+", " ")
            .trim();
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

    private static final class Model {
        private final List<String> messages;
        private final List<Action> actions;

        private Model(
            List<String> messages,
            List<Action> actions
        ) {
            this.messages = messages;
            this.actions = actions;
        }
    }

    private static final class Action {
        private final Component component;
        private final String label;

        private Action(
            Component component,
            String label
        ) {
            this.component = component;
            this.label = label == null
                ? ""
                : label;
        }
    }
}
