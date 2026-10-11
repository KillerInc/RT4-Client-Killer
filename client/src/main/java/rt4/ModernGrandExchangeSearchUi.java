package rt4;

import java.util.ArrayList;
import java.util.List;

/**
 * Modern-owned Grand Exchange item-search surface (interface 389).
 *
 * The original CS2 scripts still own keyboard search state and result creation;
 * Modern owns all visible geometry, chrome and result hitboxes.
 */
public final class ModernGrandExchangeSearchUi {
    private static final int INTERFACE_ID = 389;
    private static final int TITLE_CHILD = 6;
    private static final int INSTRUCTION_CHILD = 7;
    private static final int SEARCH_CHILD = 9;
    private static final int CLOSE_CHILD = 10;

    private ModernGrandExchangeSearchUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && interfaceId == INTERFACE_ID
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.GRAND_EXCHANGE_SEARCH;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        Model model = discover(interfaceId, components);
        if (model == null) {
            return;
        }

        ModernGrandExchangeSearchLayout layout =
            ModernGrandExchangeSearchLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        ModernUiInputRouter.blockWorldInput(layout.panel);
        ModernUiRect screen = new ModernUiRect(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        if (model.search != null) {
            ModernUiInputRouter.bind(
                model.search,
                layout.searchField,
                screen
            );
        }
        if (model.close != null) {
            ModernUiInputRouter.bind(
                model.close,
                layout.closeButton,
                screen
            );
        }

        int visible = Math.min(
            layout.visibleResultCapacity(),
            model.results.size()
        );
        for (int i = 0; i < visible; i++) {
            ModernUiInputRouter.bind(
                model.results.get(i),
                layout.resultRow(i),
                layout.results
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

        prepareInput(interfaceId, components);

        ModernGrandExchangeSearchLayout layout =
            ModernGrandExchangeSearchLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            model.title.isEmpty()
                ? "Grand Exchange Item Search"
                : model.title,
            layout.title.x,
            layout.title.y,
            layout.title.width,
            layout.title.height,
            ModernUiMetrics.TEXT_GOLD,
            0,
            1,
            ModernUiMetrics.FONT_SECTION,
            false
        );

        drawAsset(
            "controls/button",
            layout.searchField
        );
        String query = model.query;
        if (query.isEmpty()) {
            query = "Type an item name…";
        }
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            query,
            layout.searchField.x + 12,
            layout.searchField.y,
            layout.searchField.width - 24,
            layout.searchField.height,
            model.query.isEmpty()
                ? ModernUiMetrics.TEXT_PARCHMENT
                : ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_CONTROL,
            false
        );

        boolean closeHover =
            layout.closeButton.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            );
        drawAsset(
            closeHover
                ? "controls/button-active"
                : "controls/button",
            layout.closeButton
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            "×",
            layout.closeButton.x,
            layout.closeButton.y,
            layout.closeButton.width,
            layout.closeButton.height,
            ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            12.0F,
            false
        );

        if (model.results.isEmpty()) {
            String message =
                model.instruction.isEmpty()
                    ? "Start typing to search for an item."
                    : model.instruction;
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                message,
                layout.results.x + 8,
                layout.results.y + 12,
                layout.results.width - 16,
                Math.min(70, layout.results.height),
                ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
            return;
        }

        int visible = Math.min(
            layout.visibleResultCapacity(),
            model.results.size()
        );
        for (int i = 0; i < visible; i++) {
            Component result = model.results.get(i);
            ModernUiRect row = layout.resultRow(i);
            boolean hover =
                row.contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY
                );
            drawAsset(
                hover
                    ? "game-ui/tile-hover"
                    : "game-ui/tile",
                row
            );
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_12,
                currentText(result),
                row.x + 10,
                row.y,
                row.width - 20,
                row.height,
                hover
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_CONTROL,
                false
            );
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

        List<Component> flat = new ArrayList<>();
        collect(components, flat);

        Component search = null;
        Component close = null;
        String title = "";
        String instruction = "";
        List<Component> results = new ArrayList<>();

        for (Component component : flat) {
            int child = component.id & 0xFFFF;
            if (child == TITLE_CHILD) {
                title = currentText(component);
            } else if (child == INSTRUCTION_CHILD) {
                instruction = currentText(component);
            } else if (child == SEARCH_CHILD) {
                search = component;
            } else if (child == CLOSE_CHILD) {
                close = component;
            }

            if (component.type == 4
                && isInteractive(component)
                && child != SEARCH_CHILD
                && child != CLOSE_CHILD
                && child != TITLE_CHILD
                && child != INSTRUCTION_CHILD) {
                String text = currentText(component);
                if (!text.isEmpty()
                    && !sameText(text, title)
                    && !sameText(text, instruction)) {
                    results.add(component);
                }
            }
        }

        return new Model(
            title,
            instruction,
            cleanSearchText(currentText(search)),
            search,
            close,
            results
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
                collect(component.createdComponents, out);
            }
        }
    }

    private static boolean isInteractive(
        Component component
    ) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || component.ops != null
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
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
            value = Cs1ScriptRunner.interpolate(component, value);
        }

        return clean(value == null ? "" : value.toString());
    }

    private static String cleanSearchText(String value) {
        String cleaned = clean(value);
        while (cleaned.endsWith("*")) {
            cleaned =
                cleaned.substring(
                    0,
                    cleaned.length() - 1
                ).trim();
        }
        return cleaned;
    }

    private static boolean sameText(String a, String b) {
        return a != null
            && b != null
            && a.equalsIgnoreCase(b);
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
        private final String instruction;
        private final String query;
        private final Component search;
        private final Component close;
        private final List<Component> results;

        private Model(
            String title,
            String instruction,
            String query,
            Component search,
            Component close,
            List<Component> results
        ) {
            this.title = title;
            this.instruction = instruction;
            this.query = query;
            this.search = search;
            this.close = close;
            this.results = results;
        }
    }
}
