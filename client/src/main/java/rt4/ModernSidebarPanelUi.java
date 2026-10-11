package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern-owned renderer for the standard in-game sidebar tabs.
 *
 * Cache components are state/action/content backends only. Panel geometry,
 * control geometry, hover state and hitboxes are Modern-owned.
 */
public final class ModernSidebarPanelUi {
    private ModernSidebarPanelUi() {
    }

    public static boolean handles(int interfaceId) {
        if (!ModernGameUi.isInGame()) {
            return false;
        }

        ModernGameInterfaceCatalog.Kind kind =
            ModernGameInterfaceCatalog.kind(interfaceId);

        return kind == ModernGameInterfaceCatalog.Kind.EQUIPMENT
            || kind == ModernGameInterfaceCatalog.Kind.STATS
            || kind == ModernGameInterfaceCatalog.Kind.PRAYER
            || kind == ModernGameInterfaceCatalog.Kind.MAGIC
            || kind == ModernGameInterfaceCatalog.Kind.OPTIONS
            || kind == ModernGameInterfaceCatalog.Kind.EMOTES
            || kind == ModernGameInterfaceCatalog.Kind.FRIENDS
            || kind == ModernGameInterfaceCatalog.Kind.IGNORE
            || kind == ModernGameInterfaceCatalog.Kind.CLAN;
    }

    public static boolean isSidebarComponents(
        Component[] components
    ) {
        int interfaceId = interfaceIdOf(components);
        return handles(interfaceId);
    }

    public static void prepareInput(
        Component[] components
    ) {
        Model model = discover(components);
        if (model == null) {
            return;
        }

        ModernSidebarPanelLayout layout =
            ModernSidebarPanelLayout.create(
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

        if (model.inventory != null) {
            ModernUiInputRouter.bind(
                model.inventory,
                layout.inventoryGrid(model.inventory),
                screen
            );
            return;
        }

        int capacity = layout.visibleTileCapacity();
        int count = Math.min(capacity, model.actions.size());
        for (int i = 0; i < count; i++) {
            ModernUiInputRouter.bind(
                model.actions.get(i).component,
                layout.tile(i),
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

        ModernSidebarPanelLayout layout =
            ModernSidebarPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            titleFor(model.kind),
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

        if (model.inventory != null) {
            ModernItemGridRenderer.render(
                model.inventory,
                layout.inventoryGrid(model.inventory)
            );
            return;
        }

        int capacity = layout.visibleTileCapacity();
        int actionCount =
            Math.min(capacity, model.actions.size());

        if (actionCount > 0) {
            for (int i = 0; i < actionCount; i++) {
                drawAction(
                    model.actions.get(i),
                    layout.tile(i)
                );
            }
        } else {
            renderTextList(model.texts, layout);
        }
    }

    private static void renderTextList(
        List<String> texts,
        ModernSidebarPanelLayout layout
    ) {
        int maxRows =
            Math.max(1, layout.content.height / 24);
        int count = Math.min(maxRows, texts.size());

        for (int i = 0; i < count; i++) {
            ModernUiRect row = layout.listRow(i);
            if ((i & 1) != 0) {
                drawAsset("controls/popup-row", row);
            }

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                texts.get(i),
                row.x + 6,
                row.y,
                Math.max(1, row.width - 12),
                row.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
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
                ? "game-ui/slot-hover"
                : "game-ui/slot",
            rect
        );

        Sprite sprite = null;
        if (action.component.type == 5) {
            sprite =
                action.component.getSprite(active);
        }

        if (sprite != null) {
            int max = rect.width - 10;
            int width = Math.max(1, sprite.width);
            int height = Math.max(1, sprite.height);
            if (width > max || height > max) {
                float scale =
                    Math.min(
                        max / (float) width,
                        max / (float) height
                    );
                width = Math.max(1, (int) (width * scale));
                height = Math.max(1, (int) (height * scale));
                sprite.renderResized(
                    rect.centerX() - width / 2,
                    rect.centerY() - height / 2,
                    width,
                    height
                );
            } else {
                sprite.render(
                    rect.centerX() - width / 2,
                    rect.centerY() - height / 2
                );
            }
        } else {
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                action.label,
                rect.x + 3,
                rect.y + 3,
                Math.max(1, rect.width - 6),
                Math.max(1, rect.height - 6),
                active
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                8.0F,
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

        ModernGameInterfaceCatalog.Kind kind =
            ModernGameInterfaceCatalog.kind(interfaceId);

        List<Component> flat = new ArrayList<>();
        collect(components, flat);

        Component inventory = null;
        int inventorySlots = -1;
        List<Action> actions = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();

        for (Component component : flat) {
            if (component == null) {
                continue;
            }

            if (component.type == 2
                && component.objTypes != null) {
                int slots =
                    Math.max(1, component.baseWidth)
                        * Math.max(1, component.baseHeight);
                if (slots > inventorySlots) {
                    inventorySlots = slots;
                    inventory = component;
                }
            }

            String text = currentText(component);
            if (!text.isEmpty()
                && !containsIgnoreCase(texts, text)
                && texts.size() < 24) {
                texts.add(text);
            }

            if (component.type != 0
                && isInteractive(component)
                && !seen.containsKey(component)) {
                actions.add(
                    new Action(
                        component,
                        actionLabel(component, text)
                    )
                );
                seen.put(component, Boolean.TRUE);
            }
        }

        return new Model(
            interfaceId,
            kind,
            inventory,
            actions,
            texts
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
        String currentText
    ) {
        if (currentText != null
            && !currentText.isEmpty()) {
            return shorten(currentText);
        }

        if (component.optionBase != null
            && component.optionBase.length() > 0) {
            return shorten(
                clean(component.optionBase.toString())
            );
        }

        if (component.option != null
            && component.option.length() > 0) {
            return shorten(
                clean(component.option.toString())
            );
        }

        if (component.ops != null) {
            for (JagString op : component.ops) {
                if (op != null && op.length() > 0) {
                    return shorten(
                        clean(op.toString())
                    );
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
            if (component != null && component.id != -1) {
                return component.id >>> 16;
            }
        }
        return -1;
    }

    private static String titleFor(
        ModernGameInterfaceCatalog.Kind kind
    ) {
        if (kind == ModernGameInterfaceCatalog.Kind.EQUIPMENT) {
            return "Equipment";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.STATS) {
            return "Skills";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.PRAYER) {
            return "Prayer";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.MAGIC) {
            return "Magic";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.OPTIONS) {
            return "Options";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.EMOTES) {
            return "Emotes";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.FRIENDS) {
            return "Friends";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.IGNORE) {
            return "Ignore";
        }
        if (kind == ModernGameInterfaceCatalog.Kind.CLAN) {
            return "Clan";
        }
        return "Panel";
    }

    private static boolean containsIgnoreCase(
        List<String> values,
        String candidate
    ) {
        String normalized =
            candidate.toLowerCase(Locale.ROOT);
        for (String value : values) {
            if (value.toLowerCase(Locale.ROOT)
                .equals(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static String shorten(String value) {
        if (value == null) {
            return "";
        }
        String clean = clean(value);
        if (clean.length() <= 18) {
            return clean;
        }
        return clean.substring(0, 17) + "…";
    }

    private static String clean(String value) {
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
        private final int interfaceId;
        private final ModernGameInterfaceCatalog.Kind kind;
        private final Component inventory;
        private final List<Action> actions;
        private final List<String> texts;

        private Model(
            int interfaceId,
            ModernGameInterfaceCatalog.Kind kind,
            Component inventory,
            List<Action> actions,
            List<String> texts
        ) {
            this.interfaceId = interfaceId;
            this.kind = kind;
            this.inventory = inventory;
            this.actions = actions;
            this.texts = texts;
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
            this.label = label == null ? "" : label;
        }
    }
}
