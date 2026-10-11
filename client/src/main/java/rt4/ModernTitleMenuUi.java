package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern renderer for all remaining title/login menus that are not one of the
 * dedicated Main Menu, Login, Graphics Options or Audio Options screens.
 *
 * The legacy tree is used only to discover semantic text plus action/state
 * backends. Geometry is completely owned by ModernTitleMenuLayout.
 */
public final class ModernTitleMenuUi {
    private static boolean logged;

    private ModernTitleMenuUi() {
    }

    public static boolean isActive(Component[] components) {
        return discover(components, 0, 0) != null;
    }

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        Model model = discover(components, parentX, parentY);
        if (model == null) {
            return;
        }

        ModernTitleMenuLayout layout =
            ModernTitleMenuLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        ModernUiRect clip = new ModernUiRect(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        for (int i = 0; i < model.fields.size(); i++) {
            Field field = model.fields.get(i);
            ModernUiInputRouter.bind(
                field.component,
                layout.fieldRect(i, model.fields.size()),
                clip
            );
        }

        for (int i = 0; i < model.actions.size(); i++) {
            Action action = model.actions.get(i);
            ModernUiInputRouter.bind(
                action.component,
                layout.actionRect(
                    i,
                    model.actions.size(),
                    model.worldGrid
                ),
                clip
            );
        }

        for (int i = 0; i < model.navigation.size(); i++) {
            Action action = model.navigation.get(i);
            ModernUiInputRouter.bind(
                action.component,
                layout.navigationRect(
                    i,
                    model.navigation.size()
                ),
                clip
            );
        }
    }

    public static void render(
        Component[] components,
        int parentX,
        int parentY
    ) {
        Model model = discover(components, parentX, parentY);
        if (model == null) {
            return;
        }

        ModernTitleMenuLayout layout =
            ModernTitleMenuLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components, parentX, parentY);

        if (!logged) {
            logged = true;
            DisplayDebug.log(
                "MODERN_UI title-menu fallback active"
                    + " title=" + model.title
                    + " body=" + model.body.size()
                    + " fields=" + model.fields.size()
                    + " actions=" + model.actions.size()
                    + " navigation=" + model.navigation.size()
                    + " worldGrid=" + model.worldGrid
            );
        }

        drawAsset("main-menu/logo", layout.logo);
        drawAsset("audio-options/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        drawCentered(
            model.title,
            layout.title,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_TITLE,
            ModernUiMetrics.TEXT_GOLD
        );

        int bodyCount = Math.min(7, model.body.size());
        for (int i = 0; i < bodyCount; i++) {
            drawCentered(
                model.body.get(i),
                layout.bodyLine(i, bodyCount),
                ModernUiFontRegistry.PLAIN_11,
                ModernUiMetrics.FONT_CONTROL,
                ModernUiMetrics.TEXT_PRIMARY
            );
        }

        for (int i = 0; i < model.fields.size(); i++) {
            Field field = model.fields.get(i);
            ModernUiRect fieldRect =
                layout.fieldRect(i, model.fields.size());
            ModernUiRect labelRect =
                layout.fieldLabelRect(i, model.fields.size());

            drawCentered(
                field.label,
                labelRect,
                ModernUiFontRegistry.BOLD_12,
                ModernUiMetrics.FONT_LABEL,
                ModernUiMetrics.TEXT_PRIMARY
            );

            boolean hover = fieldRect.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            );
            drawAsset(
                hover
                    ? "controls/button-active"
                    : "controls/button",
                fieldRect
            );

            String value =
                field.password
                    ? mask(field.value)
                    : field.value;
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_12,
                value,
                fieldRect.x + 10,
                fieldRect.y,
                Math.max(1, fieldRect.width - 20),
                fieldRect.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                ModernUiMetrics.FONT_DROPDOWN,
                false
            );
        }

        for (int i = 0; i < model.actions.size(); i++) {
            Action action = model.actions.get(i);
            drawButton(
                layout.actionRect(
                    i,
                    model.actions.size(),
                    model.worldGrid
                ),
                action.label
            );
        }

        for (int i = 0; i < model.navigation.size(); i++) {
            Action action = model.navigation.get(i);
            drawButton(
                layout.navigationRect(
                    i,
                    model.navigation.size()
                ),
                action.label
            );
        }
    }

    private static Model discover(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!ModernUiManager.isEnabled()
            || client.gameState != 10
            || components == null) {
            return null;
        }

        if (GraphicsOptionsUiInjector.isGraphicsOptionsActive(components)
            || ModernAudioOptionsUi.isAudioOptionsActive(components)
            || ModernLoginScreenUi.isLoginScreenActive(components)) {
            return null;
        }

        List<Entry> entries = new ArrayList<>();
        collect(
            components,
            -1,
            parentX,
            parentY,
            entries
        );

        if (entries.isEmpty()) {
            return null;
        }

        List<TextEntry> texts = new ArrayList<>();
        for (Entry entry : entries) {
            if (!entry.text.isEmpty()) {
                texts.add(
                    new TextEntry(
                        entry.component,
                        entry.rect,
                        display(entry.text)
                    )
                );
            }
        }

        if (texts.isEmpty()) {
            return null;
        }

        if (isMainMenu(texts)) {
            return null;
        }

        int worldLabels = 0;
        boolean worldKeyword = false;
        boolean accountKeyword = false;

        for (TextEntry text : texts) {
            String normalized = normalize(text.text);
            if (isWorldLabel(normalized)) {
                worldLabels++;
            }
            if (normalized.contains("select a world")
                || normalized.contains("choose a world")
                || normalized.contains("world select")
                || normalized.contains("world list")) {
                worldKeyword = true;
            }
            if (normalized.contains("create account")
                || normalized.contains("creating an account")
                || normalized.contains("choose a username")
                || normalized.contains("display name")
                || normalized.contains("date of birth")
                || normalized.contains("country")
                || normalized.contains("terms and conditions")
                || normalized.contains("privacy policy")) {
                accountKeyword = true;
            }
        }

        boolean worldGrid =
            worldKeyword || worldLabels >= 2;

        String title =
            worldGrid
                ? "Select a World"
                : accountKeyword
                    ? "Create Account"
                    : chooseTitle(texts);

        Map<Component, Boolean> claimed =
            new IdentityHashMap<>();

        List<Field> fields = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        List<Action> navigation = new ArrayList<>();

        for (Entry entry : entries) {
            Component component = entry.component;
            if (!isInteractive(component)
                || component.type == 0
                || claimed.containsKey(component)) {
                continue;
            }

            TextEntry nearest =
                nearestText(texts, entry.rect);
            String label =
                entry.text.isEmpty()
                    ? nearest == null
                        ? ""
                        : nearest.text
                    : display(entry.text);
            String normalized = normalize(label);

            if (looksLikeField(component, label)) {
                String fieldLabel =
                    label.isEmpty()
                        ? fieldLabelForIndex(fields.size())
                        : cleanFieldLabel(label);
                fields.add(
                    new Field(
                        component,
                        fieldLabel,
                        fieldValue(component, entry.text),
                        normalize(fieldLabel).contains("password")
                    )
                );
                claimed.put(component, Boolean.TRUE);
                continue;
            }

            if (label.isEmpty()) {
                continue;
            }

            if (isNavigationLabel(normalized)) {
                navigation.add(
                    new Action(component, normalizeButtonLabel(label))
                );
                claimed.put(component, Boolean.TRUE);
                continue;
            }

            if (isActionLabel(normalized)
                || worldGrid && isWorldLabel(normalized)
                || component.buttonType != 0) {
                actions.add(
                    new Action(component, normalizeButtonLabel(label))
                );
                claimed.put(component, Boolean.TRUE);
            }
        }

        // Text widgets themselves often own the button action in the title
        // interface. Add any unclaimed interactive labels here.
        for (TextEntry text : texts) {
            if (!isInteractive(text.component)
                || claimed.containsKey(text.component)) {
                continue;
            }

            String normalized = normalize(text.text);
            if (isNavigationLabel(normalized)) {
                navigation.add(
                    new Action(
                        text.component,
                        normalizeButtonLabel(text.text)
                    )
                );
                claimed.put(text.component, Boolean.TRUE);
            } else if (isActionLabel(normalized)
                || worldGrid && isWorldLabel(normalized)) {
                actions.add(
                    new Action(
                        text.component,
                        normalizeButtonLabel(text.text)
                    )
                );
                claimed.put(text.component, Boolean.TRUE);
            }
        }

        if (actions.isEmpty()
            && navigation.isEmpty()
            && fields.isEmpty()) {
            return null;
        }

        dedupeActions(actions);
        dedupeActions(navigation);

        if (worldGrid && actions.size() > 27) {
            actions =
                new ArrayList<>(actions.subList(0, 27));
        }
        if (!worldGrid && actions.size() > 6) {
            actions =
                new ArrayList<>(actions.subList(0, 6));
        }
        if (navigation.size() > 3) {
            navigation =
                new ArrayList<>(navigation.subList(0, 3));
        }
        if (fields.size() > 5) {
            fields =
                new ArrayList<>(fields.subList(0, 5));
        }

        List<String> body = new ArrayList<>();
        for (TextEntry text : texts) {
            String value = cleanBodyText(text.text);
            String normalized = normalize(value);

            if (value.isEmpty()
                || normalize(title).equals(normalized)
                || isNavigationLabel(normalized)
                || isActionLabel(normalized)
                || worldGrid && isWorldLabel(normalized)
                || isFieldLabel(normalized)) {
                continue;
            }

            if (!containsIgnoreCase(body, value)) {
                body.add(value);
            }
            if (body.size() >= 7) {
                break;
            }
        }

        return new Model(
            title,
            body,
            fields,
            actions,
            navigation,
            worldGrid
        );
    }

    private static boolean isMainMenu(List<TextEntry> texts) {
        boolean login = false;
        boolean create = false;
        boolean graphics = false;
        boolean audio = false;
        boolean quit = false;

        for (TextEntry text : texts) {
            String normalized = normalize(text.text);
            login |= normalized.equals("log in")
                || normalized.equals("login");
            create |= normalized.equals("create account");
            graphics |= normalized.equals("graphics options");
            audio |= normalized.equals("audio options")
                || normalized.equals("music options");
            quit |= normalized.equals("quit");
        }

        return login
            && create
            && graphics
            && audio
            && quit;
    }

    private static String chooseTitle(List<TextEntry> texts) {
        for (TextEntry text : texts) {
            String value = cleanBodyText(text.text);
            String normalized = normalize(value);
            if (value.length() >= 3
                && value.length() <= 52
                && !isFieldLabel(normalized)
                && !isNavigationLabel(normalized)
                && !isActionLabel(normalized)
                && !isWorldLabel(normalized)) {
                return value;
            }
        }
        return "RuneScape";
    }

    private static boolean looksLikeField(
        Component component,
        String label
    ) {
        String normalized = normalize(label);
        if (isFieldLabel(normalized)) {
            return component.buttonType == 0
                || component.hasEventHandlers;
        }

        return label.isEmpty()
            && component.buttonType == 0
            && component.hasEventHandlers
            && component.width >= 60
            && component.height >= 10
            && component.height <= 80;
    }

    private static boolean isFieldLabel(String text) {
        return text.equals("username")
            || text.equals("username:")
            || text.equals("password")
            || text.equals("password:")
            || text.contains("display name")
            || text.equals("name")
            || text.equals("name:")
            || text.contains("email")
            || text.contains("day")
            || text.contains("month")
            || text.contains("year")
            || text.contains("date of birth")
            || text.contains("country");
    }

    private static String fieldLabelForIndex(int index) {
        return index == 0
            ? "Entry"
            : "Entry " + (index + 1);
    }

    private static String cleanFieldLabel(String value) {
        String cleaned = cleanBodyText(value);
        if (cleaned.endsWith(":")) {
            cleaned =
                cleaned.substring(0, cleaned.length() - 1);
        }
        return cleaned;
    }

    private static boolean isNavigationLabel(String text) {
        return text.equals("main menu")
            || text.equals("back")
            || text.equals("cancel")
            || text.equals("close")
            || text.equals("return");
    }

    private static boolean isActionLabel(String text) {
        return text.equals("continue")
            || text.equals("next")
            || text.equals("select")
            || text.equals("confirm")
            || text.equals("accept")
            || text.equals("agree")
            || text.equals("submit")
            || text.equals("create")
            || text.equals("create account")
            || text.equals("check availability")
            || text.equals("check name")
            || text.equals("try again")
            || text.equals("ok")
            || text.equals("yes")
            || text.equals("no")
            || text.startsWith("world ");
    }

    private static boolean isWorldLabel(String text) {
        if (!text.startsWith("world ")) {
            return false;
        }
        if (text.length() <= 6) {
            return false;
        }
        for (int i = 6; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= '0' && ch <= '9') {
                return true;
            }
            if (ch != ' ' && ch != '-' && ch != ':') {
                break;
            }
        }
        return false;
    }

    private static String normalizeButtonLabel(String value) {
        String cleaned = cleanBodyText(value);
        if (cleaned.length() > 28) {
            cleaned = cleaned.substring(0, 28);
        }
        return cleaned;
    }

    private static String fieldValue(
        Component component,
        String current
    ) {
        if (current != null && !current.isEmpty()) {
            String cleaned = cleanBodyText(current);
            if (!isFieldLabel(normalize(cleaned))) {
                return cleaned;
            }
        }

        if (component.activeText != null
            && component.activeText.length() > 0) {
            return cleanBodyText(
                component.activeText.toString()
            );
        }

        return "";
    }

    private static TextEntry nearestText(
        List<TextEntry> texts,
        ModernUiRect rect
    ) {
        TextEntry best = null;
        int bestScore = Integer.MAX_VALUE;

        for (TextEntry text : texts) {
            int dx =
                Math.abs(
                    text.rect.centerX() - rect.centerX()
                );
            int dy =
                Math.abs(
                    text.rect.centerY() - rect.centerY()
                );

            if (dx > 240 || dy > 90) {
                continue;
            }

            int score = dx + dy * 2;
            if (score < bestScore) {
                bestScore = score;
                best = text;
            }
        }
        return best;
    }

    private static void dedupeActions(List<Action> actions) {
        Map<Component, Boolean> seen =
            new IdentityHashMap<>();
        for (int i = actions.size() - 1; i >= 0; i--) {
            Action action = actions.get(i);
            if (seen.containsKey(action.component)) {
                actions.remove(i);
            } else {
                seen.put(action.component, Boolean.TRUE);
            }
        }
    }

    private static boolean containsIgnoreCase(
        List<String> values,
        String value
    ) {
        String normalized = normalize(value);
        for (String existing : values) {
            if (normalize(existing).equals(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInteractive(Component component) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
    }

    private static void collect(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<Entry> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null
                || component.overlayer != layer) {
                continue;
            }
            if (component.if3
                && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
            ModernUiRect rect =
                new ModernUiRect(
                    x,
                    y,
                    Math.max(1, component.width),
                    Math.max(1, component.height)
                );

            out.add(
                new Entry(
                    component,
                    rect,
                    currentText(component)
                )
            );

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collect(
                    components,
                    component.id,
                    childX,
                    childY,
                    out
                );
                if (component.createdComponents != null) {
                    collect(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        out
                    );
                }
            }
        }
    }

    private static String currentText(Component component) {
        if (component.type != 4
            && component.type != 8) {
            return "";
        }

        JagString current = component.text;
        if (Cs1ScriptRunner.isTrue(component)
            && component.activeText != null
            && component.activeText.length() > 0) {
            current = component.activeText;
        }
        if (!component.if3 && current != null) {
            current = Cs1ScriptRunner.interpolate(
                component,
                current
            );
        }
        return current == null
            ? ""
            : current.toString();
    }

    private static String mask(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder out =
            new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            out.append('*');
        }
        return out.toString();
    }

    private static String cleanBodyText(String text) {
        String value = display(text);
        value = value.replaceAll("\\s+", " ").trim();
        return value;
    }

    private static String display(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(text.length());
        boolean insideTag = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                insideTag = true;
                continue;
            }
            if (ch == '>' && insideTag) {
                insideTag = false;
                out.append(' ');
                continue;
            }
            if (!insideTag) {
                out.append(
                    ch == '\n' || ch == '\r' || ch == '\t'
                        ? ' '
                        : ch
                );
            }
        }
        return out.toString().trim();
    }

    private static String normalize(String text) {
        return cleanBodyText(text)
            .toLowerCase(Locale.ROOT);
    }

    private static void drawButton(
        ModernUiRect rect,
        String label
    ) {
        boolean hover = rect.contains(
            Mouse.lastMouseX,
            Mouse.lastMouseY
        );
        drawAsset(
            hover
                ? "audio-options/button-active"
                : "audio-options/button",
            rect
        );
        drawCentered(
            label,
            rect,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY
        );
    }

    private static void drawCentered(
        String text,
        ModernUiRect rect,
        String font,
        float size,
        int color
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text == null ? "" : text,
            rect.x,
            rect.y,
            rect.width,
            rect.height,
            color,
            1,
            1,
            size,
            false
        );
    }

    private static void drawAsset(
        String path,
        ModernUiRect rect
    ) {
        ModernUiImage image = ModernUiAssetResolver.get(
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
        private final List<String> body;
        private final List<Field> fields;
        private final List<Action> actions;
        private final List<Action> navigation;
        private final boolean worldGrid;

        private Model(
            String title,
            List<String> body,
            List<Field> fields,
            List<Action> actions,
            List<Action> navigation,
            boolean worldGrid
        ) {
            this.title = title;
            this.body = body;
            this.fields = fields;
            this.actions = actions;
            this.navigation = navigation;
            this.worldGrid = worldGrid;
        }
    }

    private static final class Entry {
        private final Component component;
        private final ModernUiRect rect;
        private final String text;

        private Entry(
            Component component,
            ModernUiRect rect,
            String text
        ) {
            this.component = component;
            this.rect = rect;
            this.text = text == null ? "" : text;
        }
    }

    private static final class TextEntry {
        private final Component component;
        private final ModernUiRect rect;
        private final String text;

        private TextEntry(
            Component component,
            ModernUiRect rect,
            String text
        ) {
            this.component = component;
            this.rect = rect;
            this.text = text;
        }
    }

    private static final class Field {
        private final Component component;
        private final String label;
        private final String value;
        private final boolean password;

        private Field(
            Component component,
            String label,
            String value,
            boolean password
        ) {
            this.component = component;
            this.label = label;
            this.value = value == null ? "" : value;
            this.password = password;
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
