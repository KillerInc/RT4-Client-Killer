package rt4;

/**
 * Compact Modern geometry for the Grand Exchange item search chatbox.
 *
 * This intentionally does not reuse the full GE window. The stock interface
 * opens as a chatbox/search overlay and Modern keeps that interaction compact.
 */
public final class ModernGrandExchangeSearchLayout {
    public static final int PANEL_WIDTH = 560;
    public static final int PANEL_HEIGHT = 330;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect searchField;
    public final ModernUiRect results;
    public final ModernUiRect closeButton;

    private ModernGrandExchangeSearchLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect searchField,
        ModernUiRect results,
        ModernUiRect closeButton
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.searchField = searchField;
        this.results = results;
        this.closeButton = closeButton;
    }

    public static ModernGrandExchangeSearchLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(420, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(250, canvasHeight - 48)
        );

        ModernUiRect panel = new ModernUiRect(
            Math.max(8, (canvasWidth - width) / 2),
            Math.max(8, canvasHeight - height - 24),
            width,
            height
        );
        ModernUiRect title = new ModernUiRect(
            panel.x + 18,
            panel.y + 12,
            panel.width - 72,
            26
        );
        ModernUiRect closeButton = new ModernUiRect(
            panel.right() - 44,
            panel.y + 10,
            28,
            28
        );
        ModernUiRect divider = new ModernUiRect(
            panel.x + 18,
            panel.y + 46,
            panel.width - 36,
            4
        );
        ModernUiRect searchField = new ModernUiRect(
            panel.x + 24,
            panel.y + 60,
            panel.width - 48,
            38
        );
        ModernUiRect results = new ModernUiRect(
            panel.x + 24,
            searchField.bottom() + 10,
            panel.width - 48,
            panel.bottom() - searchField.bottom() - 28
        );

        return new ModernGrandExchangeSearchLayout(
            panel,
            title,
            divider,
            searchField,
            results,
            closeButton
        );
    }

    public int visibleResultCapacity() {
        return Math.max(1, results.height / 28);
    }

    public ModernUiRect resultRow(
        int index
    ) {
        int height = 26;
        int gap = 2;
        return new ModernUiRect(
            results.x,
            results.y + Math.max(0, index) * (height + gap),
            results.width,
            height
        );
    }
}
