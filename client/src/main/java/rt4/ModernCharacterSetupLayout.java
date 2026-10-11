package rt4;

/**
 * Independent declarative layout for the four-page Modern character setup.
 *
 * Interface 771 is retained only as the appearance/action backend. No cached
 * coordinates are used by this layout.
 */
public final class ModernCharacterSetupLayout {
    public static final int PANEL_WIDTH = 760;
    public static final int PANEL_HEIGHT = 520;
    public static final int TAB_COUNT = 4;

    public final ModernUiRect panel;
    public final ModernUiRect header;
    public final ModernUiRect title;
    public final ModernUiRect tabs;
    public final ModernUiRect notice;
    public final ModernUiRect divider;
    public final ModernUiRect content;
    public final ModernUiRect footer;

    public final ModernUiRect leftPane;
    public final ModernUiRect centerPane;
    public final ModernUiRect rightPane;

    private ModernCharacterSetupLayout(
        ModernUiRect panel,
        ModernUiRect header,
        ModernUiRect title,
        ModernUiRect tabs,
        ModernUiRect notice,
        ModernUiRect divider,
        ModernUiRect content,
        ModernUiRect footer,
        ModernUiRect leftPane,
        ModernUiRect centerPane,
        ModernUiRect rightPane
    ) {
        this.panel = panel;
        this.header = header;
        this.title = title;
        this.tabs = tabs;
        this.notice = notice;
        this.divider = divider;
        this.content = content;
        this.footer = footer;
        this.leftPane = leftPane;
        this.centerPane = centerPane;
        this.rightPane = rightPane;
    }

    public static ModernCharacterSetupLayout create(
        int canvasWidth,
        int canvasHeight,
        int preferredX,
        int preferredY
    ) {
        int width = Math.min(
            PANEL_WIDTH,
            Math.max(600, canvasWidth - 32)
        );
        int height = Math.min(
            PANEL_HEIGHT,
            Math.max(430, canvasHeight - 48)
        );

        int x =
            preferredX == Integer.MIN_VALUE
                ? Math.max(8, (canvasWidth - width) / 2)
                : preferredX;
        int y =
            preferredY == Integer.MIN_VALUE
                ? Math.max(8, (canvasHeight - height) / 2)
                : preferredY;

        x = Math.max(8, Math.min(x, Math.max(8, canvasWidth - width - 8)));
        y = Math.max(8, Math.min(y, Math.max(8, canvasHeight - height - 8)));

        ModernUiRect panel = new ModernUiRect(x, y, width, height);
        ModernUiRect header = new ModernUiRect(
            panel.x + 14,
            panel.y + 10,
            panel.width - 28,
            42
        );
        ModernUiRect tabs = new ModernUiRect(
            header.x,
            header.y,
            188,
            header.height
        );
        ModernUiRect title = new ModernUiRect(
            tabs.right() + 12,
            header.y,
            128,
            header.height
        );
        ModernUiRect notice = new ModernUiRect(
            title.right() + 8,
            header.y,
            Math.max(120, header.right() - title.right() - 8),
            header.height
        );
        ModernUiRect divider = new ModernUiRect(
            panel.x + 18,
            header.bottom() + 5,
            panel.width - 36,
            4
        );
        ModernUiRect footer = new ModernUiRect(
            panel.x + 20,
            panel.bottom() - 54,
            panel.width - 40,
            34
        );
        ModernUiRect content = new ModernUiRect(
            panel.x + 20,
            divider.bottom() + 10,
            panel.width - 40,
            footer.y - divider.bottom() - 20
        );

        int gap = 12;
        int leftWidth = Math.max(150, content.width * 27 / 100);
        int centerWidth = Math.max(160, content.width * 30 / 100);
        int rightWidth = content.width - leftWidth - centerWidth - gap * 2;

        ModernUiRect leftPane = new ModernUiRect(
            content.x,
            content.y,
            leftWidth,
            content.height
        );
        ModernUiRect centerPane = new ModernUiRect(
            leftPane.right() + gap,
            content.y,
            centerWidth,
            content.height
        );
        ModernUiRect rightPane = new ModernUiRect(
            centerPane.right() + gap,
            content.y,
            Math.max(150, rightWidth),
            content.height
        );

        return new ModernCharacterSetupLayout(
            panel,
            header,
            title,
            tabs,
            notice,
            divider,
            content,
            footer,
            leftPane,
            centerPane,
            rightPane
        );
    }

    public ModernUiRect tab(int index) {
        int gap = 4;
        int width = (tabs.width - gap * (TAB_COUNT - 1)) / TAB_COUNT;
        return new ModernUiRect(
            tabs.x + Math.max(0, index) * (width + gap),
            tabs.y,
            width,
            tabs.height
        );
    }

    public ModernUiRect choiceCard(int index) {
        int gap = 24;
        int width = Math.min(230, (content.width - gap - 80) / 2);
        int height = Math.min(190, content.height - 70);
        int total = width * 2 + gap;
        int startX = content.centerX() - total / 2;
        return new ModernUiRect(
            startX + Math.max(0, index) * (width + gap),
            content.y + 36,
            width,
            height
        );
    }

    public ModernUiRect previewCard(boolean head) {
        ModernUiRect pane = head ? leftPane : centerPane;
        return new ModernUiRect(
            pane.x + 8,
            pane.y + 30,
            pane.width - 16,
            Math.max(150, pane.height - (head ? 118 : 54))
        );
    }

    public ModernUiRect headSelector(int row) {
        ModernUiRect preview = previewCard(true);
        return new ModernUiRect(
            leftPane.x + 10,
            preview.bottom() + 8 + Math.max(0, row) * 34,
            leftPane.width - 20,
            28
        );
    }

    public ModernUiRect bodySelector(int row) {
        int count = 5;
        int gap = 7;
        int h = Math.min(
            36,
            (leftPane.height - 36 - gap * (count - 1)) / count
        );
        return new ModernUiRect(
            leftPane.x + 6,
            leftPane.y + 30 + Math.max(0, row) * (h + gap),
            leftPane.width - 12,
            h
        );
    }

    public ModernUiRect selectorArrow(ModernUiRect selector, boolean next) {
        int size = Math.min(28, selector.height);
        return new ModernUiRect(
            next
                ? selector.right() - size
                : selector.x,
            selector.y,
            size,
            selector.height
        );
    }

    public ModernUiRect selectorLabel(ModernUiRect selector) {
        int pad = Math.min(30, selector.height + 2);
        return new ModernUiRect(
            selector.x + pad,
            selector.y,
            Math.max(1, selector.width - pad * 2),
            selector.height
        );
    }

    public ModernUiRect paletteTabs(int count) {
        return new ModernUiRect(
            rightPane.x + 6,
            rightPane.y + 30,
            rightPane.width - 12,
            30
        );
    }

    public ModernUiRect paletteTab(int index, int count) {
        ModernUiRect area = paletteTabs(count);
        int gap = 5;
        int width = (area.width - gap * Math.max(0, count - 1)) / Math.max(1, count);
        return new ModernUiRect(
            area.x + Math.max(0, index) * (width + gap),
            area.y,
            width,
            area.height
        );
    }

    public ModernUiRect paletteArea() {
        ModernUiRect tabs = paletteTabs(3);
        return new ModernUiRect(
            rightPane.x + 10,
            tabs.bottom() + 10,
            rightPane.width - 20,
            Math.max(110, rightPane.bottom() - tabs.bottom() - 20)
        );
    }

    public ModernUiRect swatch(int index, int count) {
        ModernUiRect area = paletteArea();
        int columns = count <= 8 ? 4 : 6;
        int rows = Math.max(1, (count + columns - 1) / columns);
        int gap = 5;
        int width = Math.min(
            34,
            Math.max(22, (area.width - gap * (columns - 1)) / columns)
        );
        int height = Math.min(
            28,
            Math.max(20, (area.height - gap * (rows - 1)) / rows)
        );
        int totalWidth = columns * width + gap * (columns - 1);
        int x0 = area.centerX() - totalWidth / 2;
        int col = Math.max(0, index) % columns;
        int row = Math.max(0, index) / columns;
        return new ModernUiRect(
            x0 + col * (width + gap),
            area.y + row * (height + gap),
            width,
            height
        );
    }

    public ModernUiRect footerButton(int index, int count) {
        int visible = Math.max(1, count);
        int gap = 10;
        int width = Math.min(
            150,
            (footer.width - gap * (visible - 1)) / visible
        );
        int total = visible * width + gap * (visible - 1);
        int start = footer.centerX() - total / 2;
        return new ModernUiRect(
            start + Math.max(0, index) * (width + gap),
            footer.y,
            width,
            footer.height
        );
    }
}
