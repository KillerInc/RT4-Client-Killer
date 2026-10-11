package rt4;

/**
 * Declarative geometry for the Modern world-map window.
 */
public final class ModernWorldMapLayout {
    public static final int EDGE = 18;
    public static final int TOOLBAR_HEIGHT = 34;
    public static final int OVERVIEW_SIZE = 150;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect divider;
    public final ModernUiRect toolbar;
    public final ModernUiRect mapViewport;
    public final ModernUiRect overview;

    private ModernWorldMapLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect divider,
        ModernUiRect toolbar,
        ModernUiRect mapViewport,
        ModernUiRect overview
    ) {
        this.panel = panel;
        this.title = title;
        this.divider = divider;
        this.toolbar = toolbar;
        this.mapViewport = mapViewport;
        this.overview = overview;
    }

    public static ModernWorldMapLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int width =
            Math.max(
                480,
                canvasWidth - EDGE * 2
            );
        int height =
            Math.max(
                360,
                canvasHeight - EDGE * 2
            );
        width = Math.min(
            width,
            Math.max(1, canvasWidth - 16)
        );
        height = Math.min(
            height,
            Math.max(1, canvasHeight - 16)
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
                panel.x + 16,
                panel.y + 10,
                panel.width - 32,
                24
            );
        ModernUiRect divider =
            new ModernUiRect(
                panel.x + 16,
                panel.y + 42,
                panel.width - 32,
                4
            );
        ModernUiRect toolbar =
            new ModernUiRect(
                panel.x + 14,
                panel.y + 52,
                panel.width - 28,
                TOOLBAR_HEIGHT
            );
        ModernUiRect map =
            new ModernUiRect(
                panel.x + 14,
                panel.y + 96,
                panel.width - 28,
                panel.height - 110
            );

        int overviewSize =
            Math.min(
                OVERVIEW_SIZE,
                Math.max(90, map.height / 3)
            );
        ModernUiRect overview =
            new ModernUiRect(
                map.right() - overviewSize - 8,
                map.y + 8,
                overviewSize,
                overviewSize
            );

        return new ModernWorldMapLayout(
            panel,
            title,
            divider,
            toolbar,
            map,
            overview
        );
    }

    public ModernUiRect actionRect(
        int index,
        int count
    ) {
        int visible = Math.max(1, Math.min(8, count));
        int gap = 6;
        int width =
            Math.max(
                56,
                (toolbar.width
                    - Math.max(0, visible - 1) * gap)
                    / visible
            );

        return new ModernUiRect(
            toolbar.x + Math.max(0, index) * (width + gap),
            toolbar.y,
            width,
            toolbar.height
        );
    }
}
