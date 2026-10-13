package rt4;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Modern-owned normal chat panel.
 *
 * Quest/NPC dialogue is intentionally excluded; it has its own movable window.
 */
public final class ModernChatPanelUi {
    private static final int SCROLLBAR_WIDTH = 14;
    private static int scrollLinesFromBottom;

    private ModernChatPanelUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.CHAT;
    }

    public static boolean isChatComponents(
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

        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        float chatFontSize =
            ModernUiPreferences.getChatFontSize();
        int chatLineHeight =
            Math.max(
                18,
                Math.round(chatFontSize) + 4
            );
        int baseTextWidth =
            Math.max(1, layout.messages.width - 8);
        List<String> wrappedForScroll =
            wrapMessages(
                model.messages,
                baseTextWidth,
                chatFontSize
            );
        int maxLines =
            Math.max(
                1,
                layout.messages.height / chatLineHeight
            );
        boolean overflow =
            wrappedForScroll.size() > maxLines;
        if (overflow) {
            wrappedForScroll =
                wrapMessages(
                    model.messages,
                    Math.max(
                        1,
                        baseTextWidth
                            - SCROLLBAR_WIDTH - 6
                    ),
                    chatFontSize
                );
            maxLines =
                Math.max(
                    1,
                    layout.messages.height
                        / chatLineHeight
                );
        }
        int maxScroll =
            Math.max(
                0,
                wrappedForScroll.size() - maxLines
            );
        scrollLinesFromBottom =
            Math.max(
                0,
                Math.min(
                    scrollLinesFromBottom,
                    maxScroll
                )
            );

        if (overflow
            && layout.historyPanel.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            )
            && MouseWheel.wheelRotation != 0) {
            scrollLinesFromBottom =
                Math.max(
                    0,
                    Math.min(
                        maxScroll,
                        scrollLinesFromBottom
                            + MouseWheel.wheelRotation
                                * -3
                    )
                );
            MouseWheel.wheelRotation = 0;
            InterfaceList.fullRedrawAllInterfaces();
            GameShell.fullRedraw = true;
        }

        ModernUiInputRouter.blockWorldInput(layout.historyPanel);
        ModernUiInputRouter.registerModernRegion(
            "chat-font-decrease",
            layout.fontDecrease,
            layout.historyPanel
        );
        ModernUiInputRouter.registerModernRegion(
            "chat-font-increase",
            layout.fontIncrease,
            layout.historyPanel
        );

        if (Mouse.clickButton == 1) {
            if (layout.fontDecrease.contains(
                    Mouse.clickX,
                    Mouse.clickY
                )) {
                ModernUiPreferences.adjustChatFontSize(-1.0F);
                Mouse.clickButton = 0;
                MiniMenu.clickProcessingState = 0;
                InterfaceList.fullRedrawAllInterfaces();
                GameShell.fullRedraw = true;
            } else if (layout.fontIncrease.contains(
                    Mouse.clickX,
                    Mouse.clickY
                )) {
                ModernUiPreferences.adjustChatFontSize(1.0F);
                Mouse.clickButton = 0;
                MiniMenu.clickProcessingState = 0;
                InterfaceList.fullRedrawAllInterfaces();
                GameShell.fullRedraw = true;
            }
        }

        ModernUiRect screen =
            new ModernUiRect(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

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
        Model model = discover(components);
        if (model == null) {
            return;
        }

        ModernChatPanelLayout layout =
            ModernChatPanelLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        prepareInput(components);

        drawAsset("game-ui/panel", layout.historyPanel);
        drawAsset("audio-options/divider", layout.divider);

        drawFontButton(
            layout.fontDecrease,
            "-",
            layout.fontDecrease.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            )
        );
        drawFontButton(
            layout.fontIncrease,
            "+",
            layout.fontIncrease.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            )
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            "Chat",
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

        float chatFontSize =
            ModernUiPreferences.getChatFontSize();
        int chatLineHeight =
            Math.max(
                18,
                Math.round(chatFontSize) + 4
            );
        int textWidth =
            Math.max(1, layout.messages.width - 8);

        List<String> wrappedMessages =
            wrapMessages(
                model.messages,
                textWidth,
                chatFontSize
            );

        int maxLines =
            Math.max(
                1,
                layout.messages.height / chatLineHeight
            );
        boolean overflow =
            wrappedMessages.size() > maxLines;
        if (overflow) {
            textWidth =
                Math.max(
                    1,
                    textWidth
                        - SCROLLBAR_WIDTH - 6
                );
            wrappedMessages =
                wrapMessages(
                    model.messages,
                    textWidth,
                    chatFontSize
                );
        }

        int maxScroll =
            Math.max(
                0,
                wrappedMessages.size() - maxLines
            );
        scrollLinesFromBottom =
            Math.max(
                0,
                Math.min(
                    scrollLinesFromBottom,
                    maxScroll
                )
            );
        int start =
            Math.max(
                0,
                wrappedMessages.size()
                    - maxLines
                    - scrollLinesFromBottom
            );
        int count =
            Math.min(
                maxLines,
                wrappedMessages.size() - start
            );

        if (overflow) {
            drawScrollbar(
                layout.messages,
                start,
                maxLines,
                wrappedMessages.size()
            );
        }

        for (int i = 0; i < count; i++) {
            ModernUiRect row =
                layout.messageLine(i, count);
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                wrappedMessages.get(start + i),
                row.x + 4,
                row.y,
                Math.max(
                    1,
                    textWidth
                ),
                row.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0,
                1,
                chatFontSize,
                false
            );
        }

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
            boolean active =
                Cs1ScriptRunner.isTrue(
                    action.component
                );

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

        List<String> messages =
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
            if (!text.isEmpty()
                && !containsIgnoreCase(
                    messages,
                    text
                )) {
                messages.add(text);
            }

            if (component.type != 0
                && isInteractive(component)
                && !seen.containsKey(component)) {
                String label =
                    actionLabel(
                        component,
                        text
                    );
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

        // The normal chat log is runtime state, not static component text.
        // Add the live Chat ring-buffer newest-last so the Modern panel remains
        // useful even when interface 752 itself contains only chatbox chrome.
        List<String> liveMessages = new ArrayList<>();
        int liveCount = Math.min(Chat.size, Chat.messages.length);
        for (int i = liveCount - 1; i >= 0; i--) {
            JagString message = Chat.messages[i];
            if (message == null || message.length() == 0) {
                continue;
            }

            String body = clean(message.toString());
            JagString nameValue = Chat.names[i];
            String name =
                nameValue == null
                    ? ""
                    : clean(nameValue.toString());

            String line =
                name.isEmpty()
                    ? body
                    : name + ": " + body;
            if (!line.isEmpty()
                && !containsIgnoreCase(liveMessages, line)) {
                liveMessages.add(line);
            }
        }

        if (!liveMessages.isEmpty()) {
            messages = liveMessages;
        }

        if (messages.size() > 30) {
            messages =
                new ArrayList<>(
                    messages.subList(
                        messages.size() - 30,
                        messages.size()
                    )
                );
        }

        return new Model(
            messages,
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
        String text
    ) {
        if (text != null && !text.isEmpty()) {
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

    private static List<String> wrapMessages(
        List<String> messages,
        int width,
        float fontSize
    ) {
        List<String> wrapped = new ArrayList<>();
        if (messages == null || messages.isEmpty()) {
            return wrapped;
        }

        for (String message : messages) {
            String cleanMessage = clean(message);
            if (cleanMessage.isEmpty()) {
                continue;
            }

            StringBuilder line = new StringBuilder();
            for (String word : cleanMessage.split(" ")) {
                String candidate =
                    line.length() == 0
                        ? word
                        : line + " " + word;

                if (line.length() > 0
                    && ModernTrueTypeFont.getWidth(
                        ModernUiFontRegistry.PLAIN_11,
                        candidate,
                        fontSize
                    ) > width) {
                    wrapped.add(line.toString());
                    line.setLength(0);
                    line.append(word);
                } else {
                    if (line.length() > 0) {
                        line.append(' ');
                    }
                    line.append(word);
                }

                // Extremely long single tokens still need to stay inside the
                // window. Split them by character only when word wrapping
                // cannot fit the token on an empty line.
                if (line.length() > 0
                    && ModernTrueTypeFont.getWidth(
                        ModernUiFontRegistry.PLAIN_11,
                        line.toString(),
                        fontSize
                    ) > width) {
                    String value = line.toString();
                    line.setLength(0);
                    StringBuilder part = new StringBuilder();
                    for (int i = 0; i < value.length(); i++) {
                        char ch = value.charAt(i);
                        String test = part.toString() + ch;
                        if (part.length() > 0
                            && ModernTrueTypeFont.getWidth(
                                ModernUiFontRegistry.PLAIN_11,
                                test,
                                fontSize
                            ) > width) {
                            wrapped.add(part.toString());
                            part.setLength(0);
                        }
                        part.append(ch);
                    }
                    line.append(part);
                }
            }

            if (line.length() > 0) {
                wrapped.add(line.toString());
            }
        }

        return wrapped;
    }

    private static boolean containsIgnoreCase(
        List<String> values,
        String candidate
    ) {
        String normalized =
            candidate.toLowerCase(
                Locale.ROOT
            );
        for (String value : values) {
            if (value.toLowerCase(
                Locale.ROOT
            ).equals(normalized)) {
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

    private static void drawScrollbar(
        ModernUiRect viewport,
        int startLine,
        int visibleLines,
        int totalLines
    ) {
        if (totalLines <= visibleLines) {
            return;
        }

        ModernUiRect track =
            new ModernUiRect(
                viewport.right() - SCROLLBAR_WIDTH,
                viewport.y,
                SCROLLBAR_WIDTH,
                viewport.height
            );
        int thumbHeight =
            Math.max(
                24,
                track.height
                    * visibleLines
                    / Math.max(1, totalLines)
            );
        int maxStart =
            Math.max(
                1,
                totalLines - visibleLines
            );
        int travel =
            Math.max(0, track.height - thumbHeight);
        int thumbY =
            track.y
                + travel * startLine / maxStart;
        ModernUiRect thumb =
            new ModernUiRect(
                track.x,
                thumbY,
                track.width,
                Math.min(
                    thumbHeight,
                    track.bottom() - thumbY
                )
            );

        drawAsset(
            "game-ui/scrollbar-track",
            track
        );
        drawAsset(
            "game-ui/scrollbar-thumb",
            thumb
        );
    }

    private static void drawFontButton(
        ModernUiRect rect,
        String label,
        boolean hover
    ) {
        drawAsset(
            hover
                ? "controls/button-active"
                : "controls/button",
            rect
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            label,
            rect.x,
            rect.y - 1,
            rect.width,
            rect.height,
            ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            Math.max(
                18.0F,
                Math.min(
                    24.0F,
                    ModernUiPreferences.getChatFontSize()
                )
            ),
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
        private final List<String> messages;
        private final List<Action> actions;

        private Model(
            List<String> messages,
            List<Action> actions
        ) {
            this.messages = messages;
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
