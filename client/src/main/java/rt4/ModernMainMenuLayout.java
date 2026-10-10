package rt4;

/**
 * Declarative Modern main-menu geometry.
 *
 * This layout is canvas-relative and never reads the vanilla interface tree.
 * The old Components are used only as state/action backends by
 * ModernUiRenderer.
 */
public final class ModernMainMenuLayout {
    public static final int SCROLL_WIDTH = 552;
    public static final int SCROLL_HEIGHT = 582;
    public static final int SCROLL_VERTICAL_BIAS = 0;

    public final int centerX;
    public final ModernUiRect scroll;
    public final ModernUiRect content;
    public final ModernUiRect logo;

    public final int choiceTop;
    public final int musicSliderTop;

    private ModernMainMenuLayout(
        int centerX,
        ModernUiRect scroll,
        ModernUiRect content,
        ModernUiRect logo,
        int choiceTop,
        int musicSliderTop
    ) {
        this.centerX = centerX;
        this.scroll = scroll;
        this.content = content;
        this.logo = logo;
        this.choiceTop = choiceTop;
        this.musicSliderTop = musicSliderTop;
    }

    public static ModernMainMenuLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int centerX = canvasWidth / 2;

        int scrollWidth = Math.min(
            SCROLL_WIDTH,
            Math.max(320, canvasWidth - 16)
        );
        int scrollHeight = Math.min(
            SCROLL_HEIGHT,
            Math.max(420, canvasHeight - 16)
        );

        int scrollX = Math.max(
            8,
            centerX - scrollWidth / 2
        );
        int scrollY =
            (canvasHeight - scrollHeight) / 2
                + SCROLL_VERTICAL_BIAS;
        scrollY = Math.max(
            8,
            Math.min(
                scrollY,
                Math.max(8, canvasHeight - scrollHeight - 8)
            )
        );

        ModernUiRect scroll = new ModernUiRect(
            scrollX,
            scrollY,
            scrollWidth,
            scrollHeight
        );

        int contentInsetX =
            Math.max(42, scroll.width * 12 / 100);
        int contentInsetTop =
            Math.max(58, scroll.height * 12 / 100);
        int contentInsetBottom =
            Math.max(48, scroll.height * 10 / 100);

        ModernUiRect content = new ModernUiRect(
            scroll.x + contentInsetX,
            scroll.y + contentInsetTop,
            Math.max(1, scroll.width - contentInsetX * 2),
            Math.max(
                1,
                scroll.height
                    - contentInsetTop
                    - contentInsetBottom
            )
        );

        int logoWidth = Math.min(
            620,
            Math.max(400, canvasWidth * 54 / 100)
        );
        logoWidth = Math.min(
            logoWidth,
            Math.max(240, canvasWidth - 40)
        );
        int logoHeight = Math.max(
            90,
            logoWidth * 500 / 1445
        );
        int logoBottom = scroll.y - 24;

        ModernUiRect logo = new ModernUiRect(
            centerX - logoWidth / 2,
            Math.max(8, logoBottom - logoHeight),
            logoWidth,
            logoHeight
        );

        return new ModernMainMenuLayout(
            centerX,
            scroll,
            content,
            logo,
            content.y + 190,
            content.y + 322
        );
    }

    public int textY(String normalized) {
        if (normalized == null) {
            return Integer.MIN_VALUE;
        }

        int top = content.y;
        if (normalized.equals("log in")
            || normalized.equals("login")) {
            return top + 56;
        }
        if (normalized.contains("existing user")) {
            return top + 77;
        }
        if (normalized.equals("create account")) {
            return top + 102;
        }
        if (normalized.contains("new user")) {
            return top + 123;
        }
        if (normalized.startsWith("world ")) {
            return top + 148;
        }
        if (normalized.contains("click to switch")) {
            return top + 169;
        }
        if (normalized.equals("standard detail")
            || normalized.equals("high detail")) {
            return top + 220;
        }
        if (normalized.equals("graphics options")) {
            return top + 252;
        }
        if (normalized.equals("audio options")
            || normalized.equals("music options")) {
            return top + 282;
        }
        if (normalized.equals("music volume")) {
            return top + 309;
        }
        if (normalized.equals("quit")) {
            return top + 354;
        }
        return Integer.MIN_VALUE;
    }
}
