package rt4;

/**
 * Declarative geometry for the Modern login form.
 *
 * The original login interface is used only to discover action/state
 * components. No vanilla component coordinate is used to place Modern UI.
 */
public final class ModernLoginScreenLayout {
    public static final int PANEL_WIDTH = 420;
    public static final int PANEL_HEIGHT = 360;
    public static final int PANEL_VERTICAL_BIAS = 65;

    public static final int INPUT_WIDTH = 260;
    public static final int INPUT_HEIGHT = 34;
    public static final int BUTTON_WIDTH = 168;
    public static final int BUTTON_HEIGHT = 30;

    public final ModernUiRect panel;
    public final ModernUiRect logo;
    public final ModernUiRect title;
    public final ModernUiRect world;
    public final ModernUiRect divider;
    public final ModernUiRect message;
    public final ModernUiRect usernameLabel;
    public final ModernUiRect usernameInput;
    public final ModernUiRect passwordLabel;
    public final ModernUiRect passwordInput;
    public final ModernUiRect loginButton;
    public final ModernUiRect mainMenuButton;

    private ModernLoginScreenLayout(
        ModernUiRect panel,
        ModernUiRect logo,
        ModernUiRect title,
        ModernUiRect world,
        ModernUiRect divider,
        ModernUiRect message,
        ModernUiRect usernameLabel,
        ModernUiRect usernameInput,
        ModernUiRect passwordLabel,
        ModernUiRect passwordInput,
        ModernUiRect loginButton,
        ModernUiRect mainMenuButton
    ) {
        this.panel = panel;
        this.logo = logo;
        this.title = title;
        this.world = world;
        this.divider = divider;
        this.message = message;
        this.usernameLabel = usernameLabel;
        this.usernameInput = usernameInput;
        this.passwordLabel = passwordLabel;
        this.passwordInput = passwordInput;
        this.loginButton = loginButton;
        this.mainMenuButton = mainMenuButton;
    }

    public static ModernLoginScreenLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int panelX = Math.max(
            8,
            (canvasWidth - PANEL_WIDTH) / 2
        );
        panelX = Math.min(
            panelX,
            Math.max(8, canvasWidth - PANEL_WIDTH - 8)
        );

        int panelY =
            (canvasHeight - PANEL_HEIGHT) / 2
                + PANEL_VERTICAL_BIAS;
        panelY = Math.max(
            8,
            Math.min(
                panelY,
                Math.max(8, canvasHeight - PANEL_HEIGHT - 8)
            )
        );

        ModernUiRect panel = new ModernUiRect(
            panelX,
            panelY,
            PANEL_WIDTH,
            PANEL_HEIGHT
        );
        int centerX = panel.centerX();

        int logoWidth =
            ModernMainMenuLayout.logoWidthForCanvas(canvasWidth);
        int logoHeight =
            ModernMainMenuLayout.logoHeightForWidth(logoWidth);
        ModernUiRect logo = new ModernUiRect(
            centerX - logoWidth / 2,
            Math.max(8, panelY - logoHeight - 22),
            logoWidth,
            logoHeight
        );

        ModernUiRect title =
            centered(centerX, panelY + 20, 300, 26);
        ModernUiRect world =
            centered(centerX, panelY + 47, 220, 18);
        ModernUiRect divider = new ModernUiRect(
            panelX + 22,
            panelY + 69,
            PANEL_WIDTH - 44,
            4
        );
        ModernUiRect message =
            centered(centerX, panelY + 80, 330, 22);

        ModernUiRect usernameLabel =
            centered(centerX, panelY + 110, 220, 18);
        ModernUiRect usernameInput =
            centered(centerX, panelY + 132, INPUT_WIDTH, INPUT_HEIGHT);

        ModernUiRect passwordLabel =
            centered(centerX, panelY + 179, 220, 18);
        ModernUiRect passwordInput =
            centered(centerX, panelY + 201, INPUT_WIDTH, INPUT_HEIGHT);

        ModernUiRect loginButton =
            centered(centerX, panelY + 252, BUTTON_WIDTH, BUTTON_HEIGHT);
        ModernUiRect mainMenuButton =
            centered(centerX, panelY + 298, BUTTON_WIDTH, BUTTON_HEIGHT);

        return new ModernLoginScreenLayout(
            panel,
            logo,
            title,
            world,
            divider,
            message,
            usernameLabel,
            usernameInput,
            passwordLabel,
            passwordInput,
            loginButton,
            mainMenuButton
        );
    }

    private static ModernUiRect centered(
        int centerX,
        int y,
        int width,
        int height
    ) {
        return new ModernUiRect(
            centerX - width / 2,
            y,
            width,
            height
        );
    }
}
