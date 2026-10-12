package rt4;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern quest/NPC dialogue window.
 *
 * Unlike the normal chat panel this window is intentionally movable. Vanilla
 * dialogue components supply text/actions only; their original coordinates and
 * chrome are never rendered.
 */
public final class ModernQuestChatUi {
    private static int windowX = Integer.MIN_VALUE;
    private static int windowY = Integer.MIN_VALUE;
    private static boolean dragging;
    private static int dragOffsetX;
    private static int dragOffsetY;
    private static boolean handCursorActive;

    private ModernQuestChatUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.QUEST_CHAT;
    }

    public static boolean isQuestChatComponents(
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

        ModernQuestChatLayout layout =
            currentLayout();

        if (Mouse.clickButton == 1
            && layout.header.contains(
                Mouse.clickX,
                Mouse.clickY
            )) {
            dragging = true;
            dragOffsetX =
                Mouse.clickX - layout.panel.x;
            dragOffsetY =
                Mouse.clickY - layout.panel.y;
        }

        if (dragging) {
            if (Mouse.pressedButton == 1) {
                windowX =
                    Mouse.lastMouseX - dragOffsetX;
                windowY =
                    Mouse.lastMouseY - dragOffsetY;
                layout = currentLayout();
            } else {
                dragging = false;
            }
        }

        ModernUiInputRouter.blockWorldInput(layout.panel);
        ModernUiInputRouter.registerModernRegion(
            "quest-chat-drag",
            layout.header,
            layout.panel
        );

        ModernUiRect screen =
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        boolean hasPortrait = hasPortrait(model);
        int count = Math.min(5, model.actions.size());
        boolean linkStyle = count > 1;
        for (int i = 0; i < count; i++) {
            ModernUiInputRouter.bind(
                model.actions.get(i).component,
                layout.actionRect(
                    i,
                    count,
                    hasPortrait,
                    linkStyle
                ),
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

        ModernQuestChatLayout layout =
            currentLayout();

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        boolean hasPortrait = hasPortrait(model);
        if (hasPortrait) {
            ModernModelContentRenderer.render(
                model.portraitComponent,
                layout.portrait
            );
        }

        ModernTrueTypeFont.drawInBox(
            fontFor(model.title.component),
            model.title.text,
            layout.header.x,
            layout.header.y,
            layout.header.width,
            layout.header.height,
            colorFor(model.title.component, ModernUiMetrics.TEXT_GOLD),
            1,
            1,
            sizeFor(model.title.component, ModernUiMetrics.FONT_SECTION),
            false
        );

        ModernUiRect contentBody =
            layout.contentBody(hasPortrait);
        int maxLines =
            Math.max(1, contentBody.height / 20);
        int count =
            Math.min(maxLines, model.messages.size());
        for (int i = 0; i < count; i++) {
            ModernUiRect row =
                layout.bodyLine(
                    i,
                    count,
                    hasPortrait
                );
            TextEntry message = model.messages.get(i);
            ModernTrueTypeFont.drawInBox(
                fontFor(message.component),
                message.text,
                row.x,
                row.y,
                row.width,
                row.height,
                colorFor(message.component, ModernUiMetrics.TEXT_PRIMARY),
                1,
                1,
                sizeFor(message.component, ModernUiMetrics.FONT_CONTROL),
                false
            );
        }

        int actionCount =
            Math.min(5, model.actions.size());
        boolean linkStyle = actionCount > 1;
        boolean anyLinkHover = false;

        for (int i = 0; i < actionCount; i++) {
            Action action = model.actions.get(i);
            ModernUiRect rect =
                layout.actionRect(
                    i,
                    actionCount,
                    hasPortrait,
                    linkStyle
                );
            boolean hover =
                rect.contains(
                    Mouse.lastMouseX,
                    Mouse.lastMouseY
                );

            if (linkStyle) {
                anyLinkHover |= hover;

                int bulletSize = 14;
                ModernUiRect bullet =
                    new ModernUiRect(
                        rect.x + 4,
                        rect.y
                            + Math.max(
                                0,
                                (rect.height - bulletSize) / 2
                            ),
                        bulletSize,
                        bulletSize
                    );
                drawAsset(
                    hover
                        ? "controls/quest-option-bullet-hover"
                        : "controls/quest-option-bullet",
                    bullet
                );

                int textX =
                    bullet.right() + 8;
                ModernTrueTypeFont.drawInBox(
                    ModernUiFontRegistry.BOLD_12,
                    action.label,
                    textX,
                    rect.y,
                    Math.max(
                        1,
                        rect.right() - textX - 6
                    ),
                    rect.height,
                    hover
                        ? 0xFFF0A0
                        : ModernUiMetrics.TEXT_GOLD,
                    0,
                    1,
                    15.0F,
                    false
                );
                continue;
            }

            drawAsset(
                hover
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
                ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                15.0F,
                false
            );
        }

        updateChoiceCursor(
            linkStyle && anyLinkHover
        );
    }

    public static void resetPosition() {
        windowX = Integer.MIN_VALUE;
        windowY = Integer.MIN_VALUE;
        dragging = false;
        updateChoiceCursor(false);
    }

    private static ModernQuestChatLayout currentLayout() {
        ModernQuestChatLayout layout =
            ModernQuestChatLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                windowX,
                windowY
            );
        windowX = layout.panel.x;
        windowY = layout.panel.y;
        return layout;
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

        Component portraitComponent =
            findPortraitComponent(flat);

        List<TextEntry> textEntries =
            new ArrayList<>();
        List<Action> actions =
            new ArrayList<>();
        Map<Component, Boolean> actionComponents =
            new IdentityHashMap<>();

        for (Component component : flat) {
            String text = currentText(component);
            if (!text.isEmpty()) {
                textEntries.add(
                    new TextEntry(
                        component,
                        text
                    )
                );
            }

            if (component.type != 0
                && isInteractive(component)) {
                String label =
                    actionLabel(component, text);
                if (!label.isEmpty()
                    && !actionComponents.containsKey(
                        component
                    )) {
                    actions.add(
                        new Action(
                            component,
                            label
                        )
                    );
                    actionComponents.put(
                        component,
                        Boolean.TRUE
                    );
                }
            }
        }

        if (actions.size() > 5) {
            actions =
                new ArrayList<>(
                    actions.subList(0, 5)
                );
        }

        TextEntry title =
            new TextEntry(null, "Dialogue");
        for (TextEntry entry : textEntries) {
            String text = entry.text;
            if (text.length() <= 32
                && !looksLikeInstruction(text)
                && !matchesAction(
                    actions,
                    entry.component,
                    text
                )) {
                title = entry;
                break;
            }
        }

        List<TextEntry> messages =
            new ArrayList<>();
        for (TextEntry entry : textEntries) {
            String text = entry.text;
            if (entry == title
                || matchesAction(
                    actions,
                    entry.component,
                    text
                )) {
                continue;
            }

            if (!containsIgnoreCaseEntries(
                messages,
                text
            )) {
                messages.add(entry);
            }
        }

        if (messages.isEmpty()
            && !textEntries.isEmpty()
            && !"Dialogue".equals(title.text)) {
            messages.add(title);
            title = new TextEntry(null, "Dialogue");
        }

        // When the server has explicitly negotiated Modern Quest Chat, use
        // its semantic payload for presentation. Keep the hidden legacy
        // components only as action targets so dialogue progression remains
        // fully server-authoritative and vanilla-compatible.
        if (ModernQuestChatServerState.isActive()) {
            String serverTitle =
                clean(
                    ModernQuestChatServerState.getSpeakerName()
                );
            if (!serverTitle.isEmpty()) {
                title =
                    new TextEntry(
                        title.component,
                        serverTitle
                    );
            }

            List<String> serverLines =
                ModernQuestChatServerState.getLines();
            if (!serverLines.isEmpty()) {
                List<TextEntry> semanticMessages =
                    new ArrayList<>();
                for (int i = 0;
                    i < serverLines.size();
                    i++) {
                    Component styleSource =
                        i < messages.size()
                            ? messages.get(i).component
                            : null;
                    semanticMessages.add(
                        new TextEntry(
                            styleSource,
                            clean(serverLines.get(i))
                        )
                    );
                }
                messages = semanticMessages;
            }

            List<String> serverOptions =
                ModernQuestChatServerState.getOptions();
            if (!serverOptions.isEmpty()) {
                List<Action> semanticActions =
                    new ArrayList<>();
                int optionCount =
                    Math.min(
                        serverOptions.size(),
                        actions.size()
                    );
                for (int i = 0;
                    i < optionCount;
                    i++) {
                    semanticActions.add(
                        new Action(
                            actions.get(i).component,
                            clean(serverOptions.get(i))
                        )
                    );
                }
                actions = semanticActions;
            } else if (
                ModernQuestChatServerState.canContinue()
            ) {
                if (!actions.isEmpty()) {
                    Action target = actions.get(0);
                    actions =
                        new ArrayList<>();
                    actions.add(
                        new Action(
                            target.component,
                            "Click here to continue"
                        )
                    );
                }
            } else {
                actions = new ArrayList<>();
            }
        }

        return new Model(
            title,
            messages,
            actions,
            portraitComponent
        );
    }

    private static boolean hasPortrait(
        Model model
    ) {
        if (model == null
            || model.portraitComponent == null) {
            return false;
        }

        if (ModernQuestChatServerState.isActive()) {
            int speakerType =
                ModernQuestChatServerState.getSpeakerType();
            return speakerType == 1
                || speakerType == 2;
        }

        Component component =
            model.portraitComponent;
        return component.objId == -1;
    }

    private static Component findPortraitComponent(
        List<Component> components
    ) {
        if (components == null) {
            return null;
        }

        for (Component component : components) {
            if (component == null
                || component.type != 6) {
                continue;
            }

            if (component.objId != -1) {
                continue;
            }

            return component;
        }
        return null;
    }

    private static void updateChoiceCursor(
        boolean hand
    ) {
        if (handCursorActive == hand
            || GameShell.canvas == null) {
            return;
        }

        handCursorActive = hand;
        GameShell.canvas.setCursor(
            Cursor.getPredefinedCursor(
                hand
                    ? Cursor.HAND_CURSOR
                    : Cursor.DEFAULT_CURSOR
            )
        );
    }

    private static boolean matchesAction(
        List<Action> actions,
        Component component,
        String text
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

    private static boolean looksLikeInstruction(
        String text
    ) {
        String normalized =
            text.toLowerCase(Locale.ROOT);
        return normalized.contains("click")
            || normalized.contains("select")
            || normalized.contains("continue")
            || normalized.contains("press ")
            || normalized.length() > 24;
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
                    return shorten(
                        op.toString()
                    );
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

    private static boolean containsIgnoreCaseEntries(
        List<TextEntry> values,
        String candidate
    ) {
        for (TextEntry value : values) {
            if (value.text.equalsIgnoreCase(candidate)) {
                return true;
            }
        }
        return false;
    }

    private static String fontFor(Component component) {
        if (component != null && component.font >= 0) {
            String mapped =
                ModernUiFontRegistry.resolveAsset(component.font);
            if (mapped != null) {
                return mapped;
            }
        }

        // Vanilla tutorial/quest prose uses the q8/quill family. Use the
        // scalable equivalent when a component has no resolved font id.
        return ModernUiFontRegistry.QUILL_8;
    }

    private static int colorFor(
        Component component,
        int fallback
    ) {
        if (component == null) {
            return fallback;
        }

        boolean active = Cs1ScriptRunner.isTrue(component);
        int color =
            active && component.activeColor != 0
                ? component.activeColor
                : component.color;

        return color == 0 && fallback != 0
            ? fallback
            : color & 0xFFFFFF;
    }

    private static float sizeFor(
        Component component,
        float fallback
    ) {
        final float questChatBoost = 6.0F;

        if (component == null || component.font < 0) {
            return fallback + questChatBoost;
        }

        String cacheName =
            ModernUiFontRegistry.resolveCacheName(
                component.font
            );
        if ("q8_full".equals(cacheName)) {
            return 14.0F + questChatBoost;
        }
        if ("p11_full".equals(cacheName)) {
            return 11.0F + questChatBoost;
        }
        if ("p12_full".equals(cacheName)
            || "b12_full".equals(cacheName)) {
            return 12.0F + questChatBoost;
        }
        return fallback + questChatBoost;
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
        private final TextEntry title;
        private final List<TextEntry> messages;
        private final List<Action> actions;
        private final Component portraitComponent;

        private Model(
            TextEntry title,
            List<TextEntry> messages,
            List<Action> actions,
            Component portraitComponent
        ) {
            this.title = title;
            this.messages = messages;
            this.actions = actions;
            this.portraitComponent = portraitComponent;
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
