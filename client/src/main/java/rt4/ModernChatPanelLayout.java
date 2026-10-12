package rt4;

/**
 * Declarative layout for the normal in-game chat panel. Quest/NPC dialogue
 * deliberately does not use this layout.
 */
public final class ModernChatPanelLayout {
    public static final int PANEL_WIDTH = 510;
    public static final int PANEL_HEIGHT = 176;
    public static final int ACTION_HEIGHT = 38;
    public static final int FONT_BUTTON_SIZE = 30;

    public final ModernUiRect panel;
    public final ModernUiRect historyPanel;
    public final ModernUiRect title;
    public final ModernUiRect fontDecrease;
    public final ModernUiRect fontIncrease;
    public final ModernUiRect divider;
    public final ModernUiRect messages;
    public final ModernUiRect actions;

    private ModernChatPanelLayout(
        ModernUiRect panel,
        ModernUiRect historyPanel,
        ModernUiRect title,
        ModernUiRect fontDecrease,
        ModernUiRect fontIncrease,
        ModernUiRect divider,
        ModernUiRect messages,
        ModernUiRect actions
    ) {
        this.panel = panel;
        this.historyPanel = historyPanel;
        this.title = title;
        this.fontDecrease = fontDecrease;
        this.fontIncrease = fontIncrease;
        this.divider = divider;
        this.messages = messages;
        this.actions = actions;
    }

    public static ModernChatPanelLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        ModernGameFrameLayout frame =
            ModernGameFrameLayout.create(
                canvasWidth,
                canvasHeight
            );

        int width = Math.min(
            PANEL_WIDTH,
            Math.max(360, canvasWidth - 24)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(140, canvasHeight / 3)
        );

        int x = 12;

        // The layout parent is the entire chat stack so every child remains
        // inside its declared bounds for verification/input clipping. The
        // visible history panel itself still ends above the channel strip.
        int gap = 4;
        int stackHeight = height + gap + ACTION_HEIGHT;
        int y = Math.max(
            12,
            frame.tabBar.y - stackHeight - 8
        );

        ModernUiRect panel =
            new ModernUiRect(
                x,
                y,
                width,
                stackHeight
            );
        ModernUiRect historyPanel =
            new ModernUiRect(
                panel.x,
                panel.y,
                panel.width,
                height
            );
        ModernUiRect fontIncrease =
            new ModernUiRect(
                historyPanel.right() - 10 - FONT_BUTTON_SIZE,
                historyPanel.y + 6,
                FONT_BUTTON_SIZE,
                FONT_BUTTON_SIZE
            );
        ModernUiRect fontDecrease =
            new ModernUiRect(
                fontIncrease.x - 4 - FONT_BUTTON_SIZE,
                fontIncrease.y,
                FONT_BUTTON_SIZE,
                FONT_BUTTON_SIZE
            );
        ModernUiRect title =
            new ModernUiRect(
                historyPanel.x + 12,
                historyPanel.y + 8,
                Math.max(
                    80,
                    fontDecrease.x - historyPanel.x - 20
                ),
                20
            );
        ModernUiRect divider =
            new ModernUiRect(
                historyPanel.x + 12,
                historyPanel.y + 32,
                historyPanel.width - 24,
                4
            );
        ModernUiRect messages =
            new ModernUiRect(
                historyPanel.x + 12,
                historyPanel.y + 42,
                historyPanel.width - 24,
                historyPanel.height - 54
            );
        ModernUiRect actions =
            new ModernUiRect(
                panel.x + 8,
                historyPanel.bottom() + gap,
                panel.width - 16,
                ACTION_HEIGHT
            );

        return new ModernChatPanelLayout(
            panel,
            historyPanel,
            title,
            fontDecrease,
            fontIncrease,
            divider,
            messages,
            actions
        );
    }

    public ModernUiRect messageLine(int index, int count) {
        int lineHeight =
            Math.max(
                18,
                Math.round(
                    ModernUiPreferences.getChatFontSize()
                ) + 4
            );
        int visible =
            Math.max(
                1,
                Math.min(
                    Math.max(1, messages.height / lineHeight),
                    count
                )
            );
        int start =
            messages.bottom() - visible * lineHeight;

        return new ModernUiRect(
            messages.x,
            start + Math.max(0, index) * lineHeight,
            messages.width,
            lineHeight
        );
    }

    public ModernUiRect actionRect(int index, int count) {
        int visible = Math.max(1, Math.min(8, count));
        int gap = 4;
        int width =
            Math.max(
                54,
                (actions.width
                    - Math.max(0, visible - 1) * gap)
                    / visible
            );

        return new ModernUiRect(
            actions.x + Math.max(0, index) * (width + gap),
            actions.y,
            width,
            actions.height
        );
    }
}
