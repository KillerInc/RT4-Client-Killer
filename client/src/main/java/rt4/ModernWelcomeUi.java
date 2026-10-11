package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern replacement for the 2009Scape welcome/community menu.
 *
 * The original interface continues to own link/actions; Modern UI owns every
 * visible pixel and every interaction rectangle.
 */
public final class ModernWelcomeUi {
    private static boolean logged;

    private ModernWelcomeUi() {
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

        ModernWelcomeLayout layout =
            ModernWelcomeLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        ModernUiRect clip = new ModernUiRect(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        for (int i = 0; i < model.actions.size(); i++) {
            ModernUiInputRouter.bind(
                model.actions.get(i).component,
                layout.actionRect(i, model.actions.size()),
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

        ModernWelcomeLayout layout =
            ModernWelcomeLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components, parentX, parentY);

        if (!logged) {
            logged = true;
            DisplayDebug.log(
                "MODERN_UI welcome-screen active"
                    + " messages=" + model.messages.size()
                    + " actions=" + model.actions.size()
            );
        }

        drawAsset("audio-options/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        drawCentered(
            model.title,
            layout.title,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_TITLE,
            ModernUiMetrics.TEXT_GOLD
        );

        int count = Math.min(10, model.messages.size());
        for (int i = 0; i < count; i++) {
            drawCentered(
                model.messages.get(i),
                layout.messageLine(i, count),
                ModernUiFontRegistry.PLAIN_11,
                ModernUiMetrics.FONT_CONTROL,
                ModernUiMetrics.TEXT_PRIMARY
            );
        }

        for (int i = 0; i < model.actions.size(); i++) {
            ModernUiRect rect =
                layout.actionRect(i, model.actions.size());
            boolean hover =
                rect.contains(
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
                model.actions.get(i).label,
                rect,
                ModernUiFontRegistry.BOLD_12,
                ModernUiMetrics.FONT_BUTTON,
                ModernUiMetrics.TEXT_PRIMARY
            );
        }
    }

    private static Model discover(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!ModernUiManager.isEnabled()
            || components == null
            || client.gameState == 10) {
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

        boolean welcome = false;
        boolean discord = false;
        boolean credits = false;
        boolean github = false;
        String title = "Welcome to 2009Scape";

        for (Entry entry : entries) {
            String normalized = normalize(entry.text);
            if (normalized.contains("welcome to 2009scape")
                || normalized.contains("welcome to runescape")) {
                welcome = true;
                if (!entry.text.isEmpty()) {
                    title = display(entry.text);
                }
            }
            discord |= normalized.contains("discord");
            credits |= normalized.equals("credits")
                || normalized.contains("credits");
            github |= normalized.contains("github");
        }

        int communityMatches =
            (discord ? 1 : 0)
                + (credits ? 1 : 0)
                + (github ? 1 : 0);

        if (!welcome || communityMatches < 2) {
            return null;
        }

        List<Action> actions = new ArrayList<>();
        Map<Component, Boolean> claimed =
            new IdentityHashMap<>();

        for (Entry entry : entries) {
            if (!isInteractive(entry.component)
                || claimed.containsKey(entry.component)) {
                continue;
            }

            String label = display(entry.text);
            String normalized = normalize(label);

            if (normalized.contains("discord")
                || normalized.contains("github")
                || normalized.contains("credits")
                || normalized.equals("continue")
                || normalized.equals("close")
                || normalized.equals("play")) {
                actions.add(
                    new Action(
                        entry.component,
                        cleanButtonLabel(label)
                    )
                );
                claimed.put(entry.component, Boolean.TRUE);
            }
        }

        // Often the visible label and clickable container are separate.
        for (Entry text : entries) {
            String normalized = normalize(text.text);
            if (!(normalized.contains("discord")
                || normalized.contains("github")
                || normalized.contains("credits")
                || normalized.equals("continue")
                || normalized.equals("close")
                || normalized.equals("play"))) {
                continue;
            }

            if (isInteractive(text.component)
                && !claimed.containsKey(text.component)) {
                actions.add(
                    new Action(
                        text.component,
                        cleanButtonLabel(text.text)
                    )
                );
                claimed.put(text.component, Boolean.TRUE);
                continue;
            }

            Entry nearest =
                nearestInteractive(entries, text.rect);
            if (nearest != null
                && !claimed.containsKey(nearest.component)) {
                actions.add(
                    new Action(
                        nearest.component,
                        cleanButtonLabel(text.text)
                    )
                );
                claimed.put(nearest.component, Boolean.TRUE);
            }
        }

        if (actions.size() > 4) {
            actions =
                new ArrayList<>(actions.subList(0, 4));
        }

        List<String> messages = new ArrayList<>();
        for (Entry entry : entries) {
            String value = cleanBody(entry.text);
            String normalized = normalize(value);

            if (value.isEmpty()
                || normalize(title).equals(normalized)
                || normalized.contains("discord")
                || normalized.contains("github")
                || normalized.contains("credits")
                || normalized.equals("continue")
                || normalized.equals("close")
                || normalized.equals("play")) {
                continue;
            }

            if (!containsIgnoreCase(messages, value)) {
                messages.add(value);
            }
            if (messages.size() >= 10) {
                break;
            }
        }

        return new Model(
            title,
            messages,
            actions
        );
    }

    private static Entry nearestInteractive(
        List<Entry> entries,
        ModernUiRect target
    ) {
        Entry best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Entry entry : entries) {
            if (!isInteractive(entry.component)
                || entry.component.type == 0) {
                continue;
            }

            int dx =
                Math.abs(
                    entry.rect.centerX()
                        - target.centerX()
                );
            int dy =
                Math.abs(
                    entry.rect.centerY()
                        - target.centerY()
                );

            if (dx > 220 || dy > 80) {
                continue;
            }

            int score = dx + dy * 2;
            if (score < bestScore) {
                bestScore = score;
                best = entry;
            }
        }

        return best;
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

    private static boolean isInteractive(Component component) {
        return component != null
            && (component.buttonType != 0
                || component.hasEventHandlers
                || component.clientCode != 0
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
    }

    private static String cleanButtonLabel(String value) {
        String cleaned = cleanBody(value);
        if (cleaned.length() > 28) {
            cleaned = cleaned.substring(0, 28);
        }
        return cleaned;
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

    private static String cleanBody(String text) {
        return display(text)
            .replaceAll("\\s+", " ")
            .trim();
    }

    private static String display(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out =
            new StringBuilder(text.length());
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
                    ch == '\n'
                        || ch == '\r'
                        || ch == '\t'
                            ? ' '
                            : ch
                );
            }
        }

        return out.toString().trim();
    }

    private static String normalize(String text) {
        return cleanBody(text)
            .toLowerCase(Locale.ROOT);
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

    private static final class Action {
        private final Component component;
        private final String label;

        private Action(Component component, String label) {
            this.component = component;
            this.label = label == null ? "" : label;
        }
    }
}
