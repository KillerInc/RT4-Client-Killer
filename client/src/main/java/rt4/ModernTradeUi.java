package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern-owned trade renderer for offer and confirmation stages.
 */
public final class ModernTradeUi {
    private ModernTradeUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.TRADE;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernTradeLayout layout =
            ModernTradeLayout.create(
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

        int invCount =
            Math.min(2, model.inventories.size());
        for (int i = 0; i < invCount; i++) {
            Component inventory =
                model.inventories.get(i);
            ModernUiInputRouter.bind(
                inventory,
                layout.gridOrigin(inventory, i),
                layout.pane(i)
            );
        }

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
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernTradeLayout layout =
            ModernTradeLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(interfaceId, components);

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

        int invCount =
            Math.min(2, model.inventories.size());

        if (invCount > 0) {
            for (int i = 0; i < 2; i++) {
                ModernUiRect pane = layout.pane(i);
                drawAsset("controls/popup-row", pane);

                ModernTrueTypeFont.drawInBox(
                    ModernUiFontRegistry.BOLD_12,
                    i == 0 ? "Your offer" : "Their offer",
                    layout.paneTitle(i).x,
                    layout.paneTitle(i).y,
                    layout.paneTitle(i).width,
                    layout.paneTitle(i).height,
                    ModernUiMetrics.TEXT_PRIMARY,
                    1,
                    1,
                    ModernUiMetrics.FONT_LABEL,
                    false
                );

                if (i < invCount) {
                    Component inventory =
                        model.inventories.get(i);
                    setClip(pane);
                    ModernItemGridRenderer.render(
                        inventory,
                        layout.gridOrigin(inventory, i)
                    );
                    resetClip();
                }
            }
        } else {
            int maxLines =
                Math.max(
                    1,
                    layout.messageArea.height / 20
                );
            int count =
                Math.min(
                    maxLines,
                    model.messages.size()
                );

            for (int i = 0; i < count; i++) {
                ModernUiRect row =
                    layout.messageLine(i, count);
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
        }

        int actionCount =
            Math.min(6, model.actions.size());
        for (int i = 0; i < actionCount; i++) {
            drawAction(
                model.actions.get(i),
                layout.actionRect(i, actionCount)
            );
        }
    }

    private static void drawAction(
        Action action,
        ModernUiRect rect
    ) {
        boolean hover =
            rect.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            );
        boolean active =
            Cs1ScriptRunner.isTrue(action.component);

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

    private static Model discover(
        int interfaceId,
        Component[] components
    ) {
        if (!handles(interfaceId)
            || components == null) {
            return null;
        }

        List<Component> flat =
            new ArrayList<>();
        collect(components, flat);

        List<Component> inventories =
            new ArrayList<>();
        List<Action> actions =
            new ArrayList<>();
        List<String> messages =
            new ArrayList<>();
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();

        for (Component component : flat) {
            if (component.type == 2
                && component.objTypes != null) {
                inventories.add(component);
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
                && component.type != 2
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

        inventories.sort(
            (a, b) ->
                Integer.compare(
                    Math.max(1, b.baseWidth)
                        * Math.max(1, b.baseHeight),
                    Math.max(1, a.baseWidth)
                        * Math.max(1, a.baseHeight)
                )
        );

        if (inventories.size() > 2) {
            inventories =
                new ArrayList<>(
                    inventories.subList(0, 2)
                );
        }
        if (actions.size() > 6) {
            actions =
                new ArrayList<>(
                    actions.subList(0, 6)
                );
        }

        String title = "Trade";
        for (String message : messages) {
            String lower =
                message.toLowerCase();
            if (message.length() <= 48
                && (lower.contains("trade")
                    || lower.contains("trading"))) {
                title = message;
                break;
            }
        }

        List<String> body =
            new ArrayList<>();
        for (String message : messages) {
            if (message.equals(title)
                || looksLikeAction(message)) {
                continue;
            }
            body.add(message);
            if (body.size() >= 12) {
                break;
            }
        }

        return new Model(
            inventories,
            actions,
            body,
            title
        );
    }

    private static boolean looksLikeAction(
        String value
    ) {
        String lower = value.toLowerCase();
        return lower.equals("accept")
            || lower.equals("decline")
            || lower.equals("confirm")
            || lower.equals("cancel")
            || lower.equals("close");
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
        return shorten(text);
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

    private static void setClip(
        ModernUiRect rect
    ) {
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
        private final List<Component> inventories;
        private final List<Action> actions;
        private final List<String> messages;
        private final String title;

        private Model(
            List<Component> inventories,
            List<Action> actions,
            List<String> messages,
            String title
        ) {
            this.inventories = inventories;
            this.actions = actions;
            this.messages = messages;
            this.title = title;
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
