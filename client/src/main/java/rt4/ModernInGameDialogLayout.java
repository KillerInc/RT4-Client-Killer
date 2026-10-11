package rt4;

/**
 * Declarative geometry for non-chat in-game dialogs such as tutorial setup
 * screens. Quest dialogue deliberately uses ModernQuestChatLayout instead.
 */
public final class ModernInGameDialogLayout {
    public static final int PANEL_WIDTH = 520;
    public static final int PANEL_HEIGHT = 340;
    public static final int ACTION_WIDTH = 180;
    public static final int ACTION_HEIGHT = 42;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect body;
    public final ModernUiRect actionArea;

    private ModernInGameDialogLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect body,
        ModernUiRect actionArea
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.body = body;
        this.actionArea = actionArea;
    }

    public static ModernInGameDialogLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(380, canvasWidth - 24)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(280, canvasHeight - 24)
        );

        ModernUiRect panel =
            new ModernUiRect(
                Math.max(8, (canvasWidth - width) / 2),
                Math.max(8, (canvasHeight - height) / 2),
                width,
                height
            );

        ModernUiRect title =
            new ModernUiRect(
                panel.x + 18,
                panel.y + 16,
                panel.width - 36,
                26
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 18,
                panel.y + 50,
                panel.width - 36,
                4
            );
        ModernUiRect body =
            new ModernUiRect(
                panel.x + 24,
                panel.y + 64,
                panel.width - 48,
                panel.height - 156
            );
        ModernUiRect actionArea =
            new ModernUiRect(
                panel.x + 18,
                panel.bottom() - 82,
                panel.width - 36,
                66
            );

        return new ModernInGameDialogLayout(
            panel,
            title,
            divider,
            body,
            actionArea
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
        int visible = Math.max(1, Math.min(6, count));
        int columns = visible <= 2 ? visible : 3;
        int rows =
            (visible + columns - 1) / columns;
        int gap = 10;
        int width = Math.min(
            ACTION_WIDTH,
            Math.max(
                100,
                (actionArea.width
                    - Math.max(0, columns - 1) * gap)
                    / columns
            )
        );
        int totalWidth =
            columns * width
                + Math.max(0, columns - 1) * gap;
        int startX =
            actionArea.centerX() - totalWidth / 2;
        int row = Math.max(0, index) / columns;
        int column = Math.max(0, index) % columns;
        int rowGap = 6;
        int height =
            Math.min(
                ACTION_HEIGHT,
                Math.max(
                    28,
                    (actionArea.height
                        - Math.max(0, rows - 1) * rowGap)
                        / rows
                )
            );

        return new ModernUiRect(
            startX + column * (width + gap),
            actionArea.y + row * (height + rowGap),
            width,
            height
        );
    }
}
