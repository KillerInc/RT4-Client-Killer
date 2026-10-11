package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modern-owned world-map chrome and geometry. The actual map renderer remains
 * game content and is drawn inside the Modern viewport.
 */
public final class ModernWorldMapUi {
    private ModernWorldMapUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.WORLD_MAP;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernWorldMapLayout layout =
            ModernWorldMapLayout.create(
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

        if (model.map != null) {
            ModernUiInputRouter.bind(
                model.map,
                layout.mapViewport,
                screen
            );
        }
        if (model.overview != null) {
            ModernUiInputRouter.bind(
                model.overview,
                layout.overview,
                screen
            );
        }

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
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernWorldMapLayout layout =
            ModernWorldMapLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(interfaceId, components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            "World Map",
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

            drawAsset(
                hover
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
                ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                8.5F,
                false
            );
        }

        if (model.map != null) {
            setClip(layout.mapViewport);
            WorldMap.render(
                layout.mapViewport.x,
                layout.mapViewport.y,
                layout.mapViewport.height,
                layout.mapViewport.width
            );
            resetClip();
        }

        if (model.overview != null) {
            drawAsset(
                "controls/popup-row",
                new ModernUiRect(
                    layout.overview.x - 2,
                    layout.overview.y - 2,
                    layout.overview.width + 4,
                    layout.overview.height + 4
                )
            );

            setClip(layout.overview);
            Cs1ScriptRunner.renderWorldMapOverview(
                layout.overview.x,
                layout.overview.height,
                layout.overview.width,
                layout.overview.y
            );
            resetClip();
        }
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

        Component map = null;
        Component overview = null;
        List<Action> actions =
            new ArrayList<>();
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();

        for (Component component : flat) {
            if (component.clientCode == 1400) {
                map = component;
                continue;
            }
            if (component.clientCode == 1401) {
                overview = component;
                continue;
            }

            if (component.type != 0
                && isInteractive(component)
                && !seen.containsKey(component)) {
                String label =
                    actionLabel(component);
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

        if (actions.size() > 8) {
            actions =
                new ArrayList<>(
                    actions.subList(0, 8)
                );
        }

        return new Model(
            map,
            overview,
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

    private static String actionLabel(
        Component component
    ) {
        String text = currentText(component);
        if (!text.isEmpty()) {
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
        private final Component map;
        private final Component overview;
        private final List<Action> actions;

        private Model(
            Component map,
            Component overview,
            List<Action> actions
        ) {
            this.map = map;
            this.overview = overview;
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
