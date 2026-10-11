package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern-owned renderer for non-chat in-game modal dialogs.
 *
 * Vanilla components remain only as text/action/state backends.
 */
public final class ModernInGameDialogUi {
    private ModernInGameDialogUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.DIALOG;
    }

    public static boolean isDialogComponents(
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

        ModernInGameDialogLayout layout =
            ModernInGameDialogLayout.create(
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

        int count = Math.min(6, model.actions.size());
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

        ModernInGameDialogLayout layout =
            ModernInGameDialogLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            model.title,
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
            Math.max(1, layout.body.height / 20);
        int lineCount =
            Math.min(maxLines, model.messages.size());

        for (int i = 0; i < lineCount; i++) {
            ModernUiRect row =
                layout.bodyLine(i, lineCount);
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_12,
                model.messages.get(i),
                row.x,
                row.y,
                row.width,
                row.height,
                ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
        }

        int actionCount =
            Math.min(6, model.actions.size());

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
                    ? "audio-options/button-active"
                    : "audio-options/button",
                rect
            );

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.BOLD_12,
                action.label,
                rect.x + 4,
                rect.y,
                Math.max(1, rect.width - 8),
                rect.height,
                active
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                9.0F,
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

        List<TextEntry> texts =
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
            if (!text.isEmpty()) {
                texts.add(
                    new TextEntry(
                        component,
                        text
                    )
                );
            }

            if (component.type != 0
                && isInteractive(component)
                && !seen.containsKey(component)) {
                String label =
                    actionLabel(component, text);
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

        if (actions.size() > 6) {
            actions =
                new ArrayList<>(
                    actions.subList(0, 6)
                );
        }

        String title = "Options";
        for (TextEntry text : texts) {
            if (text.text.length() <= 28
                && !isActionText(
                    text.component,
                    text.text,
                    actions
                )) {
                title = text.text;
                break;
            }
        }

        List<String> messages =
            new ArrayList<>();
        for (TextEntry text : texts) {
            if (text.text.equals(title)
                || isActionText(
                    text.component,
                    text.text,
                    actions
                )) {
                continue;
            }

            if (!containsIgnoreCase(
                messages,
                text.text
            )) {
                messages.add(text.text);
            }
        }

        if (messages.size() > 9) {
            messages =
                new ArrayList<>(
                    messages.subList(0, 9)
                );
        }

        return new Model(
            title,
            messages,
            actions
        );
    }

    private static boolean isActionText(
        Component component,
        String text,
        List<Action> actions
    ) {
        for (Action action : actions) {
            if (action.component == component
                || action.label.equalsIgnoreCase(
                    shorten(text)
                )) {
                return true;
            }
        }
        return false;
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
        if (component == null
            || component.type != 4
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
        if (component.option != null
            && component.option.length() > 0) {
            String option =
                clean(component.option.toString());
            if (!option.isEmpty()
                && !"Ok".equalsIgnoreCase(option)) {
                return shorten(option);
            }
        }

        if (component.ops != null) {
            for (JagString op : component.ops) {
                if (op != null
                    && op.length() > 0) {
                    return shorten(op.toString());
                }
            }
        }

        if (text != null && !text.isEmpty()) {
            return shorten(text);
        }

        if (component.optionBase != null
            && component.optionBase.length() > 0) {
            return shorten(
                component.optionBase.toString()
            );
        }

        return component.buttonType != 0
            ? "Continue"
            : "";
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
        for (String value : values) {
            if (value.equalsIgnoreCase(candidate)) {
                return true;
            }
        }
        return false;
    }

    private static String shorten(
        String value
    ) {
        String clean = clean(value);
        if (clean.length() <= 28) {
            return clean;
        }
        return clean.substring(0, 27) + "…";
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
        private final String title;
        private final List<String> messages;
        private final List<Action> actions;

        private Model(
            String title,
            List<String> messages,
            List<Action> actions
        ) {
            this.title = title;
            this.messages = messages;
            this.actions = actions;
        }
    }

    private static final class TextEntry {
        private final Component component;
        private final String text;

        private TextEntry(
            Component component,
            String text
        ) {
            this.component = component;
            this.text = text;
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
