package rt4;

/**
 * Declarative Modern layout for the post-login welcome/community screen.
 */
public final class ModernWelcomeLayout {
    public static final int PANEL_WIDTH = 620;
    public static final int PANEL_HEIGHT = 420;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect messageArea;

    private ModernWelcomeLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect messageArea
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.messageArea = messageArea;
    }

    public static ModernWelcomeLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(420, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(320, canvasHeight - 32)
        );

        ModernUiRect panel = new ModernUiRect(
            Math.max(8, (canvasWidth - width) / 2),
            Math.max(8, (canvasHeight - height) / 2),
            width,
            height
        );

        ModernUiRect title = new ModernUiRect(
            panel.x + 30,
            panel.y + 22,
            panel.width - 60,
            28
        );
        ModernUiRect divider = new ModernUiRect(
            panel.x + 24,
            panel.y + 62,
            panel.width - 48,
            4
        );
        ModernUiRect messageArea = new ModernUiRect(
            panel.x + 42,
            panel.y + 82,
            panel.width - 84,
            panel.height - 170
        );

        return new ModernWelcomeLayout(
            panel,
            title,
            divider,
            messageArea
        );
    }

    public ModernUiRect messageLine(int index, int count) {
        int visible = Math.max(1, Math.min(10, count));
        int lineHeight = 20;
        int total = visible * lineHeight;
        int startY =
            messageArea.y
                + Math.max(0, (messageArea.height - total) / 2);

        return new ModernUiRect(
            messageArea.x,
            startY + index * lineHeight,
            messageArea.width,
            lineHeight
        );
    }

    public ModernUiRect actionRect(int index, int count) {
        int width = 150;
        int gap = 12;
        int total =
            count * width + Math.max(0, count - 1) * gap;
        int startX = panel.centerX() - total / 2;

        return new ModernUiRect(
            startX + index * (width + gap),
            panel.bottom() - 56,
            width,
            32
        );
    }
}
