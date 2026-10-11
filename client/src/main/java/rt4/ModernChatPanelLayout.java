package rt4;

/**
 * Declarative layout for the normal in-game chat panel. Quest/NPC dialogue
 * deliberately does not use this layout.
 */
public final class ModernChatPanelLayout {
    public static final int PANEL_WIDTH = 510;
    public static final int PANEL_HEIGHT = 176;
    public static final int ACTION_HEIGHT = 26;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect messages;
    public final ModernUiRect actions;

    private ModernChatPanelLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect messages,
        ModernUiRect actions
    ) {
        this.panel = panel;
        this.title = title;
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
        int y = Math.max(
            12,
            frame.tabBar.y - height - 8
        );

        ModernUiRect panel =
            new ModernUiRect(x, y, width, height);
        ModernUiRect title =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 8,
                panel.width - 24,
                20
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 32,
                panel.width - 24,
                4
            );
        ModernUiRect messages =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 42,
                panel.width - 24,
                panel.height - 78
            );
        ModernUiRect actions =
            new ModernUiRect(
                panel.x + 8,
                panel.bottom() - ACTION_HEIGHT - 8,
                panel.width - 16,
                ACTION_HEIGHT
            );

        return new ModernChatPanelLayout(
            panel,
            title,
            divider,
            messages,
            actions
        );
    }

    public ModernUiRect messageLine(int index, int count) {
        int lineHeight = 18;
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
