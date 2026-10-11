package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern-owned renderer for the 530 Grand Exchange interface family.
 *
 * Cached components are retained only for live text/object state and actions.
 * All window geometry, chrome, controls and hitboxes are Modern-owned.
 */
public final class ModernGrandExchangeUi {
    private static final int[] MAIN_SLOT_COMPONENTS = {
        18, 34, 50, 69, 88, 107
    };
    private static final int[] MAIN_BUY_COMPONENTS = {
        30, 46, 62, 81, 100, 119
    };
    private static final int[] MAIN_SELL_COMPONENTS = {
        31, 47, 63, 82, 101, 120
    };
    private static final int[] COLLECTION_COMPONENTS = {
        18, 23, 28, 36, 44, 52
    };

    private ModernGrandExchangeUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.GRAND_EXCHANGE;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        boolean sidePanel = isSidePanel(interfaceId);
        ModernGrandExchangeLayout layout =
            ModernGrandExchangeLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                sidePanel
            );
        ModernUiRect screen = new ModernUiRect(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        for (int i = 0; i < model.inventories.size(); i++) {
            Component inventory = model.inventories.get(i);
            ModernUiInputRouter.bind(
                inventory,
                layout.inventoryGrid(inventory),
                layout.content
            );
        }

        if (interfaceId == 105) {
            for (int i = 0; i < 6; i++) {
                bindChild(
                    model.byChild,
                    MAIN_SLOT_COMPONENTS[i],
                    layout.offerCard(i),
                    screen
                );
                bindChild(
                    model.byChild,
                    MAIN_BUY_COMPONENTS[i],
                    layout.offerAction(i, 0),
                    screen
                );
                bindChild(
                    model.byChild,
                    MAIN_SELL_COMPONENTS[i],
                    layout.offerAction(i, 1),
                    screen
                );
            }
        } else if (interfaceId == 109) {
            for (int i = 0; i < 6; i++) {
                bindChild(
                    model.byChild,
                    COLLECTION_COMPONENTS[i],
                    layout.offerCard(i),
                    screen
                );
            }
        }

        int actionCount = Math.min(8, model.otherActions.size());
        for (int i = 0; i < actionCount; i++) {
            ModernUiInputRouter.bind(
                model.otherActions.get(i).component,
                layout.actionRect(i, actionCount),
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

        boolean sidePanel = isSidePanel(interfaceId);
        ModernGrandExchangeLayout layout =
            ModernGrandExchangeLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                sidePanel
            );

        prepareInput(interfaceId, components);

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            title(interfaceId, model.title),
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

        String info = info(interfaceId, model);
        if (!info.isEmpty()) {
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                info,
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

        if (interfaceId == 105 || interfaceId == 109) {
            renderOfferCards(interfaceId, model, layout);
        } else if (!model.inventories.isEmpty()) {
            for (Component inventory : model.inventories) {
                setClip(layout.content);
                ModernItemGridRenderer.render(
                    inventory,
                    layout.inventoryGrid(inventory)
                );
                resetClip();
            }
        } else {
            renderTextBody(model, layout);
        }

        int actionCount = Math.min(8, model.otherActions.size());
        for (int i = 0; i < actionCount; i++) {
            Action action = model.otherActions.get(i);
            drawAction(
                action,
                layout.actionRect(i, actionCount)
            );
        }
    }

    private static void renderOfferCards(
        int interfaceId,
        Model model,
        ModernGrandExchangeLayout layout
    ) {
        int[] slots =
            interfaceId == 109
                ? COLLECTION_COMPONENTS
                : MAIN_SLOT_COMPONENTS;

        for (int i = 0; i < 6; i++) {
            ModernUiRect card = layout.offerCard(i);
            boolean hover =
                card.contains(Mouse.lastMouseX, Mouse.lastMouseY);

            drawAsset(
                hover ? "game-ui/tile-hover" : "game-ui/tile",
                card
            );

            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.BOLD_11,
                interfaceId == 109
                    ? "Collection " + (i + 1)
                    : "Offer " + (i + 1),
                card.x + 8,
                card.y + 5,
                card.width - 16,
                20,
                ModernUiMetrics.TEXT_GOLD,
                0,
                1,
                9.0F,
                false
            );

            Component slot = model.byChild.get(slots[i]);
            renderObjectContent(
                slot,
                new ModernUiRect(
                    card.x + 8,
                    card.y + 28,
                    36,
                    36
                )
            );

            if (interfaceId == 105) {
                Component buy =
                    model.byChild.get(MAIN_BUY_COMPONENTS[i]);
                Component sell =
                    model.byChild.get(MAIN_SELL_COMPONENTS[i]);
                drawNamedAction(
                    buy,
                    "Buy",
                    layout.offerAction(i, 0)
                );
                drawNamedAction(
                    sell,
                    "Sell",
                    layout.offerAction(i, 1)
                );
            }
        }
    }

    private static void renderTextBody(
        Model model,
        ModernGrandExchangeLayout layout
    ) {
        List<String> lines = new ArrayList<>();
        for (String text : model.texts) {
            if (!sameText(text, model.title)
                && !looksLikeChrome(text)
                && !containsIgnoreCase(lines, text)) {
                lines.add(text);
            }
        }

        int count = Math.min(
            Math.max(1, layout.content.height / 20),
            lines.size()
        );
        for (int i = 0; i < count; i++) {
            ModernUiRect row =
                layout.textLine(i, count);
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                lines.get(i),
                row.x,
                row.y,
                row.width,
                row.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
        }

        int objectIndex = 0;
        for (Component object : model.objects) {
            if (objectIndex >= 8) {
                break;
            }
            int columns = 4;
            int cell = 42;
            int x =
                layout.content.right()
                    - columns * cell
                    + (objectIndex % columns) * cell;
            int y =
                layout.content.bottom()
                    - 2 * cell
                    + (objectIndex / columns) * cell;
            renderObjectContent(
                object,
                new ModernUiRect(x, y, 36, 36)
            );
            objectIndex++;
        }
    }

    private static void renderObjectContent(
        Component component,
        ModernUiRect rect
    ) {
        if (component == null || rect == null) {
            return;
        }

        if (component.objId != -1) {
            Sprite item = Inv.getObjectSprite(
                1,
                component.objId,
                true,
                Math.max(1, component.objCount),
                3153952
            );
            if (item != null) {
                item.render(rect.x, rect.y);
            }
        } else if (component.type == 6) {
            ModernModelContentRenderer.render(
                component,
                rect.x,
                rect.y
            );
        }
    }

    private static void drawNamedAction(
        Component component,
        String label,
        ModernUiRect rect
    ) {
        if (component == null) {
            return;
        }
        boolean hover =
            rect.contains(Mouse.lastMouseX, Mouse.lastMouseY);
        drawAsset(
            hover ? "controls/button-active" : "controls/button",
            rect
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_11,
            label,
            rect.x + 3,
            rect.y,
            rect.width - 6,
            rect.height,
            ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            8.5F,
            false
        );
    }

    private static void drawAction(
        Action action,
        ModernUiRect rect
    ) {
        boolean hover =
            rect.contains(Mouse.lastMouseX, Mouse.lastMouseY);
        drawAsset(
            hover ? "controls/button-active" : "controls/button",
            rect
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_11,
            action.label,
            rect.x + 3,
            rect.y,
            rect.width - 6,
            rect.height,
            hover
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
        if (!handles(interfaceId) || components == null) {
            return null;
        }

        List<Component> flat = new ArrayList<>();
        collect(components, flat);

        List<Component> inventories = new ArrayList<>();
        List<Component> objects = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        Map<Integer, Component> byChild = new java.util.HashMap<>();
        Map<Component, Boolean> seen = new IdentityHashMap<>();

        for (Component component : flat) {
            int child = component.id & 0xFFFF;
            byChild.put(child, component);

            if (component.type == 2
                && component.objTypes != null) {
                inventories.add(component);
            }
            if (component.objId != -1
                || component.type == 6) {
                objects.add(component);
            }

            String text = currentText(component);
            if (!text.isEmpty()
                && !containsIgnoreCase(texts, text)) {
                texts.add(text);
            }

            if (isInteractive(component)
                && !seen.containsKey(component)
                && !isReservedSlotAction(interfaceId, child)) {
                String label = actionLabel(component, text);
                if (!label.isEmpty()) {
                    actions.add(new Action(component, label));
                    seen.put(component, Boolean.TRUE);
                }
            }
        }

        String title = defaultTitle(interfaceId);
        for (String text : texts) {
            if (text.length() <= 44
                && (text.toLowerCase(Locale.ROOT).contains("exchange")
                    || text.toLowerCase(Locale.ROOT).contains("history")
                    || text.toLowerCase(Locale.ROOT).contains("item set")
                    || text.toLowerCase(Locale.ROOT).contains("cost"))) {
                title = text;
                break;
            }
        }

        return new Model(
            title,
            texts,
            inventories,
            objects,
            actions,
            byChild
        );
    }

    private static boolean isReservedSlotAction(
        int interfaceId,
        int child
    ) {
        if (interfaceId == 105) {
            return contains(MAIN_SLOT_COMPONENTS, child)
                || contains(MAIN_BUY_COMPONENTS, child)
                || contains(MAIN_SELL_COMPONENTS, child);
        }
        return interfaceId == 109
            && contains(COLLECTION_COMPONENTS, child);
    }

    private static boolean contains(
        int[] values,
        int candidate
    ) {
        for (int value : values) {
            if (value == candidate) {
                return true;
            }
        }
        return false;
    }

    private static void bindChild(
        Map<Integer, Component> byChild,
        int child,
        ModernUiRect rect,
        ModernUiRect clip
    ) {
        Component component = byChild.get(child);
        if (component != null) {
            ModernUiInputRouter.bind(component, rect, clip);
        }
    }

    private static boolean isSidePanel(int interfaceId) {
        return interfaceId == 107 || interfaceId == 644;
    }

    private static String defaultTitle(int interfaceId) {
        switch (interfaceId) {
            case 107:
                return "Grand Exchange - Inventory";
            case 109:
                return "Grand Exchange - Collection Box";
            case 389:
                return "Grand Exchange Item Search";
            case 642:
                return "Grand Exchange - Guide Prices";
            case 643:
                return "Grand Exchange - Offer History";
            case 644:
                return "Grand Exchange - Inventory";
            case 645:
                return "Grand Exchange Item Sets";
            default:
                return "Grand Exchange";
        }
    }

    private static String title(
        int interfaceId,
        String discovered
    ) {
        return discovered == null || discovered.isEmpty()
            ? defaultTitle(interfaceId)
            : discovered;
    }

    private static String info(
        int interfaceId,
        Model model
    ) {
        for (String text : model.texts) {
            if (!sameText(text, model.title)
                && text.length() > 12
                && !looksLikeChrome(text)) {
                return text;
            }
        }
        if (interfaceId == 105) {
            return "Manage your current offers.";
        }
        if (interfaceId == 109) {
            return "Collect completed items and coins.";
        }
        return "";
    }

    private static boolean looksLikeChrome(String value) {
        String text =
            value == null
                ? ""
                : value.toLowerCase(Locale.ROOT);
        return text.equals("close")
            || text.equals("select")
            || text.equals("offer")
            || text.equals("examine")
            || text.equals("buy")
            || text.equals("sell");
    }

    private static String currentText(Component component) {
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
            value = Cs1ScriptRunner.interpolate(component, value);
        }
        return clean(value == null ? "" : value.toString());
    }

    private static String actionLabel(
        Component component,
        String text
    ) {
        if (component.ops != null) {
            for (JagString op : component.ops) {
                if (op != null && op.length() > 0) {
                    return shorten(op.toString());
                }
            }
        }
        if (component.option != null
            && component.option.length() > 0) {
            return shorten(component.option.toString());
        }
        if (component.optionBase != null
            && component.optionBase.length() > 0) {
            return shorten(component.optionBase.toString());
        }
        return shorten(text);
    }

    private static boolean isInteractive(Component component) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || component.ops != null
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
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
                collect(component.createdComponents, out);
            }
        }
    }

    private static boolean containsIgnoreCase(
        List<String> values,
        String candidate
    ) {
        for (String value : values) {
            if (sameText(value, candidate)) {
                return true;
            }
        }
        return false;
    }

    private static boolean sameText(String a, String b) {
        return a != null
            && b != null
            && a.equalsIgnoreCase(b);
    }

    private static String shorten(String value) {
        String clean = clean(value);
        if (clean.length() <= 24) {
            return clean;
        }
        return clean.substring(0, 23) + "…";
    }

    private static String clean(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(value.length());
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
                    ch == '\n' || ch == '\r' || ch == '\t'
                        ? ' '
                        : ch
                );
            }
        }
        return out.toString()
            .replaceAll("\\s+", " ")
            .trim();
    }

    private static void setClip(ModernUiRect rect) {
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
        private final String title;
        private final List<String> texts;
        private final List<Component> inventories;
        private final List<Component> objects;
        private final List<Action> otherActions;
        private final Map<Integer, Component> byChild;

        private Model(
            String title,
            List<String> texts,
            List<Component> inventories,
            List<Component> objects,
            List<Action> otherActions,
            Map<Integer, Component> byChild
        ) {
            this.title = title;
            this.texts = texts;
            this.inventories = inventories;
            this.objects = objects;
            this.otherActions = otherActions;
            this.byChild = byChild;
        }
    }

    private static final class Action {
        private final Component component;
        private final String label;

        private Action(Component component, String label) {
            this.component = component;
            this.label = label == null ? "" : label;
        }
    }
}
