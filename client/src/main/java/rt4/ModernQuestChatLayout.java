package rt4;

/**
 * Declarative geometry for the movable quest/NPC dialogue window.
 */
public final class ModernQuestChatLayout {
    public static final int WIDTH = 540;
    public static final int HEIGHT = 260;
    public static final int HEADER_HEIGHT = 44;
    public static final int ACTION_HEIGHT = 42;
    public static final int PORTRAIT_WIDTH = 136;
    public static final int PORTRAIT_HEIGHT = 136;
    public static final int PORTRAIT_GAP = 16;
    public static final int OPTION_HEIGHT = 28;
    public static final int OPTION_GAP = 4;

    public final ModernUiRect panel;
    public final ModernUiRect header;
    public final ModernUiRect divider;
    public final ModernUiRect body;
    public final ModernUiRect actions;
    public final ModernUiRect portrait;

    private ModernQuestChatLayout(
        ModernUiRect panel,
        ModernUiRect header,
        ModernUiRect divider,
        ModernUiRect body,
        ModernUiRect actions,
        ModernUiRect portrait
    ) {
        this.panel = panel;
        this.header = header;
        this.divider = divider;
        this.body = body;
        this.actions = actions;
        this.portrait = portrait;
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
                panel.y + 8,
                panel.width - 20,
                HEADER_HEIGHT
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 12,
                header.bottom() + 4,
                panel.width - 24,
                4
            );
        ModernUiRect actions =
            new ModernUiRect(
                panel.x + 16,
                panel.bottom() - ACTION_HEIGHT - 14,
                panel.width - 32,
                ACTION_HEIGHT
            );
        int bodyY = divider.bottom() + 8;
        ModernUiRect body =
            new ModernUiRect(
                panel.x + 18,
                bodyY,
                panel.width - 36,
                Math.max(40, actions.y - bodyY - 8)
            );
        ModernUiRect portrait =
            new ModernUiRect(
                body.x,
                body.y
                    + Math.max(
                        0,
                        (body.height - PORTRAIT_HEIGHT) / 2
                    ),
                PORTRAIT_WIDTH,
                PORTRAIT_HEIGHT
            );

        return new ModernQuestChatLayout(
            panel,
            header,
            divider,
            body,
            actions,
            portrait
        );
    }

    public ModernUiRect contentBody(boolean hasPortrait) {
        if (!hasPortrait) {
            return body;
        }

        int x = portrait.right() + PORTRAIT_GAP;
        int right = panel.right() - 18;
        return new ModernUiRect(
            x,
            body.y,
            Math.max(120, right - x),
            body.height
        );
    }

    public ModernUiRect actionArea(boolean hasPortrait) {
        if (!hasPortrait) {
            return actions;
        }

        ModernUiRect content = contentBody(true);
        return new ModernUiRect(
            content.x,
            actions.y,
            content.width,
            actions.height
        );
    }

    public ModernUiRect bodyLine(
        int index,
        int count,
        boolean hasPortrait
    ) {
        ModernUiRect content = contentBody(hasPortrait);
        int lineHeight = 20;
        int visible = Math.max(
            1,
            Math.min(
                Math.max(1, content.height / lineHeight),
                count
            )
        );
        int total = visible * lineHeight;
        int start =
            content.y
                + Math.max(
                    0,
                    (content.height - total) / 2
                );

        return new ModernUiRect(
            content.x,
            start + Math.max(0, index) * lineHeight,
            content.width,
            lineHeight
        );
    }

    /**
     * Compatibility geometry used by cache warmup/layout verification.
     * Runtime Quest Chat rendering uses the portrait/link-aware overload.
     */
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
            actions.x
                + Math.max(0, index)
                    * (width + gap),
            actions.y,
            width,
            actions.height
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count,
        boolean hasPortrait,
        boolean linkStyle
    ) {
        ModernUiRect content = contentBody(hasPortrait);
        ModernUiRect actionArea = actionArea(hasPortrait);

        if (linkStyle) {
            int visible = Math.max(1, Math.min(5, count));
            int totalHeight =
                visible * OPTION_HEIGHT
                    + Math.max(0, visible - 1) * OPTION_GAP;
            int availableTop = content.y;
            int availableBottom = panel.bottom() - 14;
            int startY =
                availableTop
                    + Math.max(
                        0,
                        (availableBottom
                            - availableTop
                            - totalHeight) / 2
                    );

            return new ModernUiRect(
                content.x + 4,
                startY
                    + Math.max(0, index)
                        * (OPTION_HEIGHT + OPTION_GAP),
                Math.max(1, content.width - 8),
                OPTION_HEIGHT
            );
        }

        int width =
            Math.min(
                234,
                Math.max(120, actionArea.width - 8)
            );
        return new ModernUiRect(
            actionArea.x
                + Math.max(
                    0,
                    (actionArea.width - width) / 2
                ),
            actionArea.y,
            width,
            actionArea.height
        );
    }
}
