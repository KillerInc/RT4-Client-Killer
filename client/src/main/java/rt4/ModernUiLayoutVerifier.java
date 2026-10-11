package rt4;

/**
 * Build-time sanity checks for declarative Modern UI layouts.
 *
 * These checks deliberately use no vanilla Component data. A layout edit that
 * pushes controls outside their screen or creates accidental overlap fails the
 * client build before it reaches a release.
 */
public final class ModernUiLayoutVerifier {
    private static final int[][] VIEWPORTS = {
        {800, 600},
        {1024, 768},
        {1280, 720},
        {1920, 1080},
        {2560, 1440}
    };

    private ModernUiLayoutVerifier() {
    }

    public static void main(String[] args) {
        for (int[] viewport : VIEWPORTS) {
            verifyAudio(viewport[0], viewport[1]);
            verifyGraphics(viewport[0], viewport[1]);
            verifyLogin(viewport[0], viewport[1]);
            verifyTitleMenus(viewport[0], viewport[1]);
            verifyWelcome(viewport[0], viewport[1]);
            verifyGameFrame(viewport[0], viewport[1]);
            verifyMainMenu(viewport[0], viewport[1]);
        }

        if (ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT < 16) {
            fail("dropdown rows are too short for Modern text");
        }

        System.out.println(
            "Modern UI declarative layout verification passed for "
                + VIEWPORTS.length + " representative viewports."
        );
    }

    private static void verifyAudio(int width, int height) {
        ModernAudioOptionsLayout layout =
            ModernAudioOptionsLayout.create(width, height);

        inside(layout.panel, layout.title, "audio title", width, height);
        inside(layout.panel, layout.topDivider, "audio top divider", width, height);
        inside(layout.panel, layout.musicLabel, "audio music label", width, height);
        inside(layout.panel, layout.musicSlider, "audio music slider", width, height);
        inside(layout.panel, layout.effectsLabel, "audio effects label", width, height);
        inside(layout.panel, layout.effectsSlider, "audio effects slider", width, height);
        inside(layout.panel, layout.areaLabel, "audio area label", width, height);
        inside(layout.panel, layout.areaSlider, "audio area slider", width, height);
        inside(layout.panel, layout.lowerDivider, "audio lower divider", width, height);
        inside(layout.panel, layout.monoLabel, "audio mono label", width, height);
        inside(layout.panel, layout.monoToggle, "audio mono toggle", width, height);
        inside(layout.panel, layout.stereoLabel, "audio stereo label", width, height);
        inside(layout.panel, layout.stereoToggle, "audio stereo toggle", width, height);
        inside(layout.panel, layout.mainMenu, "audio main menu", width, height);

        separated(
            layout.musicSlider,
            layout.effectsLabel,
            "audio music/effects"
        );
        separated(
            layout.effectsSlider,
            layout.areaLabel,
            "audio effects/area"
        );
        separated(
            layout.areaSlider,
            layout.lowerDivider,
            "audio area/divider"
        );
        separated(
            layout.monoToggle,
            layout.mainMenu,
            "audio selector/main menu"
        );
    }

    private static void verifyGraphics(int width, int height) {
        ModernGraphicsOptionsLayout layout =
            ModernGraphicsOptionsLayout.create(width, height);
        ModernUiRect panel = layout.panel();

        inside(panel, layout.title(), "graphics title", width, height);
        inside(panel, layout.topDivider(), "graphics top divider", width, height);
        inside(panel, layout.bottomDivider(), "graphics bottom divider", width, height);
        inside(panel, layout.displaySectionLabel(), "graphics display heading", width, height);
        inside(panel, layout.advancedSectionLabel(), "graphics advanced heading", width, height);
        inside(panel, layout.resolution(), "graphics resolution", width, height);
        inside(panel, layout.mainMenu(), "graphics main menu", width, height);
        inside(panel, layout.styleEditor(), "graphics style editor", width, height);

        for (int i = 0; i < 4; i++) {
            inside(
                panel,
                layout.displayModeButton(i),
                "graphics display button " + i,
                width,
                height
            );
            inside(
                panel,
                layout.displayModeDetail(i),
                "graphics display detail " + i,
                width,
                height
            );
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 5; column++) {
                inside(
                    panel,
                    layout.control(column, row),
                    "graphics control " + column + "," + row,
                    width,
                    height
                );
                inside(
                    panel,
                    layout.controlLabel(column, row),
                    "graphics label " + column + "," + row,
                    width,
                    height
                );
            }
        }
    }

    private static void verifyLogin(int width, int height) {
        ModernLoginScreenLayout layout =
            ModernLoginScreenLayout.create(width, height);

        insideScreen(layout.panel, "login panel", width, height);
        inside(layout.panel, layout.title, "login title", width, height);
        inside(layout.panel, layout.world, "login world", width, height);
        inside(layout.panel, layout.divider, "login divider", width, height);
        inside(layout.panel, layout.message, "login message", width, height);
        inside(layout.panel, layout.usernameLabel, "login username label", width, height);
        inside(layout.panel, layout.usernameInput, "login username input", width, height);
        inside(layout.panel, layout.passwordLabel, "login password label", width, height);
        inside(layout.panel, layout.passwordInput, "login password input", width, height);
        inside(layout.panel, layout.loginButton, "login button", width, height);
        inside(layout.panel, layout.mainMenuButton, "login main menu", width, height);

        separated(
            layout.usernameInput,
            layout.passwordLabel,
            "login username/password"
        );
        separated(
            layout.passwordInput,
            layout.loginButton,
            "login password/button"
        );
        separated(
            layout.loginButton,
            layout.mainMenuButton,
            "login buttons"
        );
    }

    private static void verifyTitleMenus(int width, int height) {
        verifyAccountMenu(width, height);
        verifyWorldMenu(width, height);
        verifyTitleDialog(width, height);
    }

    private static void verifyAccountMenu(int width, int height) {
        ModernAccountMenuLayout layout =
            ModernAccountMenuLayout.create(width, height);
        verifyTitleFrame(
            layout,
            "account-menu",
            width,
            height
        );

        for (int i = 0; i < 5; i++) {
            inside(
                layout.panel(),
                layout.fieldRect(i, 5),
                "account field " + i,
                width,
                height
            );
            inside(
                layout.panel(),
                layout.fieldLabelRect(i, 5),
                "account field label " + i,
                width,
                height
            );
        }

        for (int i = 0; i < 6; i++) {
            inside(
                layout.panel(),
                layout.actionRect(i, 6),
                "account action " + i,
                width,
                height
            );
        }

        for (int i = 0; i < 2; i++) {
            ModernUiRect action =
                layout.formActionRect(i, 2, 5);
            inside(
                layout.panel(),
                action,
                "account form action " + i,
                width,
                height
            );
            separated(
                layout.fieldRect(4, 5),
                action,
                "account fields/actions"
            );
        }

        for (int i = 0; i < 3; i++) {
            inside(
                layout.panel(),
                layout.navigationRect(i, 3),
                "account navigation " + i,
                width,
                height
            );
        }
    }

    private static void verifyWorldMenu(int width, int height) {
        ModernWorldSelectLayout layout =
            ModernWorldSelectLayout.create(width, height);
        verifyTitleFrame(
            layout,
            "world-menu",
            width,
            height
        );

        for (int i = 0; i < 27; i++) {
            inside(
                layout.panel(),
                layout.actionRect(i, 27),
                "world action " + i,
                width,
                height
            );
        }

        for (int i = 0; i < 3; i++) {
            inside(
                layout.panel(),
                layout.navigationRect(i, 3),
                "world navigation " + i,
                width,
                height
            );
        }
    }

    private static void verifyTitleDialog(int width, int height) {
        ModernTitleDialogLayout layout =
            ModernTitleDialogLayout.create(width, height);
        verifyTitleFrame(
            layout,
            "title-dialog",
            width,
            height
        );

        for (int i = 0; i < 2; i++) {
            inside(
                layout.panel(),
                layout.actionRect(i, 2),
                "dialog action " + i,
                width,
                height
            );
        }

        for (int i = 0; i < 2; i++) {
            inside(
                layout.panel(),
                layout.navigationRect(i, 2),
                "dialog navigation " + i,
                width,
                height
            );
        }
    }

    private static void verifyTitleFrame(
        ModernTitleScreenLayout layout,
        String name,
        int width,
        int height
    ) {
        insideScreen(
            layout.panel(),
            name + " panel",
            width,
            height
        );
        inside(
            layout.panel(),
            layout.title(),
            name + " title",
            width,
            height
        );
        inside(
            layout.panel(),
            layout.divider(),
            name + " divider",
            width,
            height
        );
        inside(
            layout.panel(),
            layout.body(),
            name + " body",
            width,
            height
        );
    }

    private static void verifyWelcome(int width, int height) {
        ModernWelcomeLayout layout =
            ModernWelcomeLayout.create(width, height);

        insideScreen(layout.panel, "welcome panel", width, height);
        inside(layout.panel, layout.title, "welcome title", width, height);
        inside(layout.panel, layout.divider, "welcome divider", width, height);
        inside(layout.panel, layout.messageArea, "welcome message", width, height);

        for (int i = 0; i < 4; i++) {
            inside(
                layout.panel,
                layout.actionRect(i, 4),
                "welcome action " + i,
                width,
                height
            );
        }
    }

    private static void verifyGameFrame(int width, int height) {
        ModernGameFrameLayout layout =
            ModernGameFrameLayout.create(width, height);

        insideScreen(
            layout.minimapFrame,
            "game minimap frame",
            width,
            height
        );
        inside(
            layout.minimapFrame,
            layout.minimap,
            "game minimap",
            width,
            height
        );
        insideScreen(
            layout.compass,
            "game compass",
            width,
            height
        );

        if (!layout.minimapFrame.contains(
            layout.minimap.centerX(),
            layout.minimap.centerY()
        )) {
            fail(
                "game minimap frame lost minimap center at "
                    + width + "x" + height
            );
        }
    }

    private static void verifyMainMenu(int width, int height) {
        ModernMainMenuLayout layout =
            ModernMainMenuLayout.create(width, height);

        insideScreen(layout.scroll, "main scroll", width, height);
        inside(layout.scroll, layout.content, "main content", width, height);
        inside(layout.content, layout.standardChoice, "main SD choice", width, height);
        inside(layout.content, layout.highChoice, "main HD choice", width, height);

        if (layout.standardChoice.right() > layout.highChoice.x) {
            fail("main SD/HD choices overlap at " + width + "x" + height);
        }

        if (layout.musicSliderTop <= layout.standardChoice.bottom()) {
            fail("main music slider collides with detail choices at "
                + width + "x" + height);
        }
    }

    private static void inside(
        ModernUiRect outer,
        ModernUiRect inner,
        String name,
        int width,
        int height
    ) {
        if (inner.x < outer.x
            || inner.y < outer.y
            || inner.right() > outer.right()
            || inner.bottom() > outer.bottom()) {
            fail(
                name + " escaped parent at " + width + "x" + height
                    + " parent=" + outer + " child=" + inner
            );
        }
    }

    private static void insideScreen(
        ModernUiRect rect,
        String name,
        int width,
        int height
    ) {
        if (rect.x < 0
            || rect.y < 0
            || rect.right() > width
            || rect.bottom() > height) {
            fail(
                name + " escaped viewport " + width + "x" + height
                    + " rect=" + rect
            );
        }
    }

    private static void separated(
        ModernUiRect upper,
        ModernUiRect lower,
        String name
    ) {
        if (upper.bottom() > lower.y) {
            fail(name + " overlaps: " + upper + " / " + lower);
        }
    }

    private static void fail(String message) {
        throw new IllegalStateException(
            "Modern UI layout verification failed: " + message
        );
    }
}
