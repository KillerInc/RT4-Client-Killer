package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern-owned Shop renderer.
 */
public final class ModernShopUi {
    private ModernShopUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.SHOP;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernShopLayout layout =
            ModernShopLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                interfaceId
            );

        ModernUiRect screen =
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        if (model.inventory != null) {
            ModernUiInputRouter.bind(
                model.inventory,
                layout.gridOrigin(model.inventory),
                layout.gridViewport
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

        ModernShopLayout layout =
            ModernShopLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                interfaceId
            );

        prepareInput(interfaceId, components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            layout.inventoryTray
                ? "Inventory"
                : model.title,
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

        if (!model.info.isEmpty()) {
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                model.info,
                layout.info.x,
                layout.info.y,
                layout.info.width,
                layout.info.height,
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
            drawAction(
                model.actions.get(i),
                layout.actionRect(i, actionCount)
            );
        }

        if (model.inventory != null) {
            setClip(layout.gridViewport);
            ModernItemGridRenderer.render(
                model.inventory,
                layout.gridOrigin(model.inventory)
            );
            resetClip();
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

        Component inventory = null;
        int bestSlots = -1;
        List<Action> actions =
            new ArrayList<>();
        List<String> texts =
            new ArrayList<>();
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();

        for (Component component : flat) {
            if (component.type == 2
                && component.objTypes != null) {
                int slots =
                    Math.max(1, component.baseWidth)
                        * Math.max(1, component.baseHeight);
                if (slots > bestSlots) {
                    bestSlots = slots;
                    inventory = component;
                }
            }

            String text = currentText(component);
            if (!text.isEmpty()
                && !containsIgnoreCase(texts, text)) {
                texts.add(text);
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

        if (actions.size() > 6) {
            actions =
                new ArrayList<>(
                    actions.subList(0, 6)
                );
        }

        String title =
            interfaceId == 621
                ? "Inventory"
                : "Shop";
        String info = "";

        for (String text : texts) {
            if (text.length() <= 36
                && !looksLikeAction(text)) {
                if ("Shop".equals(title)) {
                    title = text;
                } else if (info.isEmpty()) {
                    info = text;
                }
            }
        }

        if (title.isEmpty()) {
            title = "Shop";
        }

        return new Model(
            inventory,
            actions,
            title,
            info
        );
    }

    private static boolean looksLikeAction(
        String value
    ) {
        String normalized =
            value.toLowerCase();
        return normalized.contains("buy")
            || normalized.contains("sell")
            || normalized.equals("close")
            || normalized.equals("select");
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
        private final Component inventory;
        private final List<Action> actions;
        private final String title;
        private final String info;

        private Model(
            Component inventory,
            List<Action> actions,
            String title,
            String info
        ) {
            this.inventory = inventory;
            this.actions = actions;
            this.title = title;
            this.info = info;
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
