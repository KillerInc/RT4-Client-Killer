package rt4;

/**
 * Declarative geometry for the movable quest/NPC dialogue window.
 */
public final class ModernQuestChatLayout {
    public static final int WIDTH = 540;
    public static final int HEIGHT = 230;
    public static final int HEADER_HEIGHT = 32;
    public static final int ACTION_HEIGHT = 30;

    public final ModernUiRect panel;
    public final ModernUiRect header;
    public final ModernUiRect divider;
    public final ModernUiRect body;
    public final ModernUiRect actions;

    private ModernQuestChatLayout(
        ModernUiRect panel,
        ModernUiRect header,
        ModernUiRect divider,
        ModernUiRect body,
        ModernUiRect actions
    ) {
        this.panel = panel;
        this.header = header;
        this.divider = divider;
        this.body = body;
        this.actions = actions;
    }

    public static ModernQuestChatLayout create(
        int canvasWidth,
        int canvasHeight,
        int requestedX,
        int requestedY
    ) {
        int width = Math.min(
            WIDTH,
            Math.max(380, canvasWidth - 24)
        );
        int height = Math.min(
            HEIGHT,
            Math.max(190, canvasHeight - 24)
        );

        int defaultX =
            Math.max(12, (canvasWidth - width) / 2);
        int defaultY =
            Math.max(
                12,
                canvasHeight - height - 90
            );

        int x =
            requestedX == Integer.MIN_VALUE
                ? defaultX
                : requestedX;
        int y =
            requestedY == Integer.MIN_VALUE
                ? defaultY
                : requestedY;

        x = Math.max(
            8,
            Math.min(
                x,
                Math.max(8, canvasWidth - width - 8)
            )
        );
        y = Math.max(
            8,
            Math.min(
                y,
                Math.max(8, canvasHeight - height - 8)
            )
        );

        ModernUiRect panel =
            new ModernUiRect(x, y, width, height);
        ModernUiRect header =
            new ModernUiRect(
                panel.x + 10,
                panel.y + 6,
                panel.width - 20,
                HEADER_HEIGHT
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 12,
                panel.y + 41,
                panel.width - 24,
                4
            );
        ModernUiRect body =
            new ModernUiRect(
                panel.x + 18,
                panel.y + 52,
                panel.width - 36,
                panel.height - 104
            );
        ModernUiRect actions =
            new ModernUiRect(
                panel.x + 16,
                panel.bottom() - ACTION_HEIGHT - 12,
                panel.width - 32,
                ACTION_HEIGHT
            );

        return new ModernQuestChatLayout(
            panel,
            header,
            divider,
            body,
            actions
        );
    }

    public ModernUiRect bodyLine(
        int index,
        int count
    ) {
        int lineHeight = 20;
        int visible = Math.max(
            1,
            Math.min(
                Math.max(1, body.height / lineHeight),
                count
            )
        );
        int total = visible * lineHeight;
        int start =
            body.y
                + Math.max(
                    0,
                    (body.height - total) / 2
                );

        return new ModernUiRect(
            body.x,
            start + Math.max(0, index) * lineHeight,
            body.width,
            lineHeight
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(5, count));
        int gap = 8;
        int width =
            Math.max(
                82,
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
