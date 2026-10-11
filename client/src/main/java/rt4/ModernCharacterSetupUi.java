package rt4;

import java.util.ArrayList;
import java.util.List;

/**
 * Modern-owned renderer for the four-page character setup wizard (interface
 * 771). Cached widgets are retained only for state, CS2 actions and live model
 * content. All visible geometry/chrome/hitboxes are Modern-owned.
 */
public final class ModernCharacterSetupUi {
    private static final int INTERFACE_ID = 771;
    private static final int[] TAB_COMPONENTS = {14, 16, 18, 20};
    private static final String[] TAB_TITLES = {"Mouse", "Gender", "Head", "Body"};
    private static final String[] TAB_ICONS = {
        "game-ui/setup-mouse",
        "game-ui/setup-gender",
        "game-ui/setup-head",
        "game-ui/setup-body"
    };

    private static final int[] HEAD_HAIR_COLOURS = range(100, 124);
    private static final int[] HEAD_SKIN_COLOURS = range(151, 158);
    private static final int[] BODY_TORSO_COLOURS = range(189, 217);
    private static final int[] BODY_LEG_COLOURS = range(248, 276);
    private static final int[] BODY_FEET_COLOURS = range(307, 312);

    private static final int[] HAIR_COLOURS = {
        0x300000, 0xB03838, 0x9F2800, 0xC88080, 0xCB823E,
        0xCDAD53, 0xC06840, 0x50321E, 0x302008, 0x885F29,
        0x60B060, 0x008300, 0x183028, 0x181890, 0x008383,
        0x68A8F8, 0xBE00C0, 0x9860B0, 0x581890, 0x2F2A2A,
        0x76756C, 0xD6D1D1, 0x989878, 0x484038, 0x101010
    };
    private static final int[] SKIN_COLOURS = {
        0xBB8F6A, 0xA47F5D, 0x9E7A49, 0x8E6E42,
        0x79582A, 0x644E24, 0x4C3D09, 0xD5A985
    };
    private static final int[] TORSO_COLOURS = {
        0x300000, 0xB03838, 0x752F3B, 0xC88080, 0xA17067, 0x9D642F,
        0x8B3B2A, 0xC06840, 0xAA9045, 0x7B6E32, 0xA3970D, 0x829472,
        0x60B060, 0x2D723E, 0x1C6135, 0x183028, 0x181890, 0x2D5272,
        0x6088B0, 0x728C94, 0x83537B, 0x9860B0, 0x581890, 0x3B3B4E,
        0xADA481, 0x989878, 0x484038, 0x262323, 0x101010
    };
    private static final int[] LEG_COLOURS = {
        0x183028, 0x300000, 0xB03838, 0x752F3B, 0xC88080, 0xA17067,
        0x9D642F, 0x8B3B2A, 0xC06840, 0xAA9045, 0x7B6E32, 0xA3970D,
        0x829472, 0x60B060, 0x2D723E, 0x1C6135, 0x181890, 0x2D5272,
        0x6088B0, 0x728C94, 0x83537B, 0x9860B0, 0x581890, 0x3B3B4E,
        0xADA481, 0x989878, 0x484038, 0x262323, 0x101010
    };
    private static final int[] FEET_COLOURS = {
        0x584028, 0x404000, 0x686048,
        0x302828, 0x684820, 0x585850
    };

    private static int windowX = Integer.MIN_VALUE;
    private static int windowY = Integer.MIN_VALUE;
    private static boolean dragging;
    private static int dragOffsetX;
    private static int dragOffsetY;
    private static int lastRenderedLoop = -1000;

    private ModernCharacterSetupUi() {
    }

    public static boolean handles(int interfaceId) {
        return ModernGameUi.isInGame()
            && interfaceId == INTERFACE_ID
            && ModernGameInterfaceCatalog.kind(interfaceId)
                == ModernGameInterfaceCatalog.Kind.CHARACTER_SETUP;
    }

    public static void processModernInputEarly() {
        if (!ModernUiManager.isEnabled()
            || client.gameState != 30
            || client.loop - lastRenderedLoop > 2
            || INTERFACE_ID < 0
            || INTERFACE_ID >= InterfaceList.components.length) {
            dragging = false;
            return;
        }

        Component[] components =
            InterfaceList.components[INTERFACE_ID];
        if (components == null) {
            dragging = false;
            return;
        }

        Model model = discover(components);
        ModernCharacterSetupLayout layout = currentLayout();

        ModernUiRect dragHandle =
            new ModernUiRect(
                layout.title.x,
                layout.header.y,
                layout.header.right() - layout.title.x,
                layout.header.height
            );

        if (Mouse.clickButton == 1
            && dragHandle.contains(Mouse.clickX, Mouse.clickY)) {
            dragging = true;
            dragOffsetX = Mouse.clickX - layout.panel.x;
            dragOffsetY = Mouse.clickY - layout.panel.y;
            Mouse.clickButton = 0;
            MiniMenu.clickProcessingState = 0;
        }

        if (dragging) {
            if (Mouse.pressedButton == 1) {
                windowX = Mouse.lastMouseX - dragOffsetX;
                windowY = Mouse.lastMouseY - dragOffsetY;
                layout = currentLayout();
            } else {
                dragging = false;
            }
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        boolean handled = false;

        for (int i = 0; i < TAB_COMPONENTS.length && !handled; i++) {
            handled =
                activateModernRect(
                    model,
                    TAB_COMPONENTS[i],
                    layout.tab(i)
                );
        }

        if (!handled) {
            if (model.overlay == 26) {
                handled =
                    activateModernRect(
                        model, 362,
                        layout.footerButton(0, 2)
                    )
                    || activateModernRect(
                        model, 360,
                        layout.footerButton(1, 2)
                    );
            } else if (model.overlay == 28) {
                handled =
                    activateModernRect(
                        model, 377,
                        layout.footerButton(0, 2)
                    )
                    || activateModernRect(
                        model, 378,
                        layout.footerButton(1, 2)
                    )
                    || activateModernRect(
                        model, 381,
                        overlayToggle(layout)
                    );
            } else if (model.overlay == 29) {
                handled =
                    activateModernRect(
                        model, 394,
                        layout.footerButton(0, 2)
                    )
                    || activateModernRect(
                        model, 395,
                        layout.footerButton(1, 2)
                    )
                    || activateModernRect(
                        model, 398,
                        overlayToggle(layout)
                    );
            } else {
                switch (model.tab) {
                    case 0:
                        handled =
                            activateModernRect(
                                model, 37,
                                layout.choiceCard(0)
                            )
                            || activateModernRect(
                                model, 40,
                                layout.choiceCard(1)
                            )
                            || activateModernRect(
                                model, 46,
                                layout.footerButton(0, 1)
                            );
                        break;
                    case 1:
                        handled =
                            activateModernRect(
                                model, 49,
                                layout.choiceCard(0)
                            )
                            || activateModernRect(
                                model, 52,
                                layout.choiceCard(1)
                            )
                            || activateModernRect(
                                model, 56,
                                layout.footerButton(0, 2)
                            )
                            || activateModernRect(
                                model, 58,
                                layout.footerButton(1, 2)
                            );
                        break;
                    case 2:
                        handled =
                            processHeadModernClick(model, layout);
                        break;
                    case 3:
                        handled =
                            processBodyModernClick(model, layout);
                        break;
                    default:
                        break;
                }
            }
        }

        if (handled) {
            Mouse.clickButton = 0;
            MiniMenu.clickProcessingState = 0;
            return;
        }

        if (layout.panel.contains(Mouse.clickX, Mouse.clickY)) {
            DisplayDebug.log(
                "MODERN_UI CHARACTER_UNMATCHED_CLICK"
                    + " tab=" + model.tab
                    + " overlay=" + model.overlay
                    + " click=" + Mouse.clickX + "," + Mouse.clickY
                    + " panel="
                    + layout.panel.x + "," + layout.panel.y
                    + "," + layout.panel.width + "x"
                    + layout.panel.height
            );
            Mouse.clickButton = 0;
            MiniMenu.clickProcessingState = 0;
        }
    }

    private static boolean processHeadModernClick(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        ModernUiRect hair = layout.headSelector(0);
        ModernUiRect beard = layout.headSelector(1);

        if (activateModernRect(
                model, 92,
                layout.selectorArrow(hair, false)
            )
            || activateModernRect(
                model, 93,
                layout.selectorArrow(hair, true)
            )
            || activateModernRect(
                model, 97,
                layout.selectorArrow(beard, false)
            )
            || activateModernRect(
                model, 98,
                layout.selectorArrow(beard, true)
            )
            || activateModernRect(
                model, 62,
                layout.paletteTab(0, 2)
            )
            || activateModernRect(
                model, 61,
                layout.paletteTab(1, 2)
            )
            || activateModernRect(
                model, 171,
                layout.footerButton(0, 3)
            )
            || activateModernRect(
                model, 169,
                layout.footerButton(1, 3)
            )
            || activateModernRect(
                model, 167,
                layout.footerButton(2, 3)
            )) {
            return true;
        }

        int[] palette =
            isVisible(model, 150)
                ? HEAD_SKIN_COLOURS
                : HEAD_HAIR_COLOURS;
        for (int i = 0; i < palette.length; i++) {
            if (activateModernRect(
                    model,
                    palette[i],
                    layout.swatch(i, palette.length)
                )) {
                return true;
            }
        }
        return false;
    }

    private static boolean processBodyModernClick(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        int[][] pairs = {
            {341,342}, {345,346}, {349,350}, {353,354}, {357,358}
        };
        for (int i = 0; i < pairs.length; i++) {
            ModernUiRect selector = layout.bodySelector(i);
            if (activateModernRect(
                    model,
                    pairs[i][0],
                    layout.selectorArrow(selector, false)
                )
                || activateModernRect(
                    model,
                    pairs[i][1],
                    layout.selectorArrow(selector, true)
                )) {
                return true;
            }
        }

        if (activateModernRect(
                model, 178,
                layout.paletteTab(0, 3)
            )
            || activateModernRect(
                model, 176,
                layout.paletteTab(1, 3)
            )
            || activateModernRect(
                model, 174,
                layout.paletteTab(2, 3)
            )
            || activateModernRect(
                model, 323,
                layout.footerButton(0, 3)
            )
            || activateModernRect(
                model, 321,
                layout.footerButton(1, 3)
            )
            || activateModernRect(
                model, 319,
                layout.footerButton(2, 3)
            )) {
            return true;
        }

        int[] palette = activeBodyPalette(model);
        for (int i = 0; i < palette.length; i++) {
            if (activateModernRect(
                    model,
                    palette[i],
                    layout.swatch(i, palette.length)
                )) {
                return true;
            }
        }
        return false;
    }

    private static boolean activateModernRect(
        Model model,
        int child,
        ModernUiRect bounds
    ) {
        if (bounds == null
            || !bounds.contains(Mouse.clickX, Mouse.clickY)) {
            return false;
        }

        Component component = model.get(child);
        if (component == null) {
            DisplayDebug.log(
                "MODERN_UI CHARACTER_CLICK_NO_COMPONENT"
                    + " child=" + child
                    + " click=" + Mouse.clickX + "," + Mouse.clickY
                    + " rect="
                    + bounds.x + "," + bounds.y
                    + "," + bounds.width + "x" + bounds.height
            );
            return false;
        }

        boolean handled =
            ModernUiInputRouter.dispatchBoundPrimaryClick(
                component,
                bounds
            );

        DisplayDebug.log(
            "MODERN_UI CHARACTER_RECT_ATTEMPT"
                + " child=" + child
                + " handled=" + handled
                + " click=" + Mouse.clickX + "," + Mouse.clickY
                + " rect="
                + bounds.x + "," + bounds.y
                + "," + bounds.width + "x" + bounds.height
        );
        return handled;
    }

    public static void prepareInput(
        int interfaceId,
        Component[] components
    ) {
        if (!handles(interfaceId) || components == null) {
            return;
        }

        Model model = discover(components);
        ModernCharacterSetupLayout layout = currentLayout();

        ModernUiRect dragHandle =
            new ModernUiRect(
                layout.title.x,
                layout.header.y,
                layout.header.right() - layout.title.x,
                layout.header.height
            );
        if (Mouse.clickButton == 1
            && dragHandle.contains(Mouse.clickX, Mouse.clickY)) {
            dragging = true;
            dragOffsetX = Mouse.clickX - layout.panel.x;
            dragOffsetY = Mouse.clickY - layout.panel.y;
        }

        if (dragging) {
            if (Mouse.pressedButton == 1) {
                windowX = Mouse.lastMouseX - dragOffsetX;
                windowY = Mouse.lastMouseY - dragOffsetY;
                layout = currentLayout();
            } else {
                dragging = false;
            }
        }

        dragHandle =
            new ModernUiRect(
                layout.title.x,
                layout.header.y,
                layout.header.right() - layout.title.x,
                layout.header.height
            );
        ModernUiInputRouter.blockWorldInput(layout.panel);
        ModernUiInputRouter.registerModernRegion(
            "character-setup-drag",
            dragHandle,
            layout.panel
        );

        ModernUiRect screen = new ModernUiRect(
            0, 0, GameShell.canvasWidth, GameShell.canvasHeight
        );

        for (int i = 0; i < TAB_COMPONENTS.length; i++) {
            bind(model, TAB_COMPONENTS[i], layout.tab(i), screen);
        }

        if (model.overlay == 26) {
            bind(model, 362, layout.footerButton(0, 2), screen);
            bind(model, 360, layout.footerButton(1, 2), screen);
            return;
        }
        if (model.overlay == 28) {
            bind(model, 377, layout.footerButton(0, 2), screen);
            bind(model, 378, layout.footerButton(1, 2), screen);
            bind(model, 381, overlayToggle(layout), screen);
            return;
        }
        if (model.overlay == 29) {
            bind(model, 394, layout.footerButton(0, 2), screen);
            bind(model, 395, layout.footerButton(1, 2), screen);
            bind(model, 398, overlayToggle(layout), screen);
            return;
        }

        switch (model.tab) {
            case 0:
                bind(model, 37, layout.choiceCard(0), screen);
                bind(model, 40, layout.choiceCard(1), screen);
                bind(model, 46, layout.footerButton(0, 1), screen);
                break;
            case 1:
                bind(model, 49, layout.choiceCard(0), screen);
                bind(model, 52, layout.choiceCard(1), screen);
                bind(model, 56, layout.footerButton(0, 2), screen);
                bind(model, 58, layout.footerButton(1, 2), screen);
                break;
            case 2:
                bindHeadInput(model, layout, screen);
                break;
            case 3:
                bindBodyInput(model, layout, screen);
                break;
            default:
                break;
        }
    }

    private static void bindHeadInput(
        Model model,
        ModernCharacterSetupLayout layout,
        ModernUiRect screen
    ) {
        ModernUiRect hair = layout.headSelector(0);
        ModernUiRect beard = layout.headSelector(1);
        bind(model, 92, layout.selectorArrow(hair, false), screen);
        bind(model, 93, layout.selectorArrow(hair, true), screen);
        bind(model, 97, layout.selectorArrow(beard, false), screen);
        bind(model, 98, layout.selectorArrow(beard, true), screen);

        bind(model, 62, layout.paletteTab(0, 2), screen);
        bind(model, 61, layout.paletteTab(1, 2), screen);

        int[] palette =
            isVisible(model, 150)
                ? HEAD_SKIN_COLOURS
                : HEAD_HAIR_COLOURS;
        for (int i = 0; i < palette.length; i++) {
            bind(model, palette[i], layout.swatch(i, palette.length), screen);
        }

        bind(model, 171, layout.footerButton(0, 3), screen);
        bind(model, 169, layout.footerButton(1, 3), screen);
        bind(model, 167, layout.footerButton(2, 3), screen);
    }

    private static void bindBodyInput(
        Model model,
        ModernCharacterSetupLayout layout,
        ModernUiRect screen
    ) {
        int[][] pairs = {
            {341,342}, {345,346}, {349,350}, {353,354}, {357,358}
        };
        for (int i = 0; i < pairs.length; i++) {
            ModernUiRect selector = layout.bodySelector(i);
            bind(model, pairs[i][0], layout.selectorArrow(selector, false), screen);
            bind(model, pairs[i][1], layout.selectorArrow(selector, true), screen);
        }

        bind(model, 178, layout.paletteTab(0, 3), screen);
        bind(model, 176, layout.paletteTab(1, 3), screen);
        bind(model, 174, layout.paletteTab(2, 3), screen);

        int[] palette = activeBodyPalette(model);
        for (int i = 0; i < palette.length; i++) {
            bind(model, palette[i], layout.swatch(i, palette.length), screen);
        }

        bind(model, 323, layout.footerButton(0, 3), screen);
        bind(model, 321, layout.footerButton(1, 3), screen);
        bind(model, 319, layout.footerButton(2, 3), screen);
    }

    public static void render(
        int interfaceId,
        Component[] components
    ) {
        if (!handles(interfaceId) || components == null) {
            return;
        }

        lastRenderedLoop = client.loop;

        Model model = discover(components);
        ModernCharacterSetupLayout layout = currentLayout();

        drawAsset("game-ui/panel", layout.panel);
        drawAsset("audio-options/divider", layout.divider);

        renderTabs(model, layout);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            TAB_TITLES[model.tab],
            layout.title.x,
            layout.title.y,
            layout.title.width,
            layout.title.height,
            ModernUiMetrics.TEXT_GOLD,
            1, 1,
            ModernUiMetrics.FONT_SECTION,
            false
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_11,
            "All options selected here can be changed later.",
            layout.notice.x,
            layout.notice.y,
            layout.notice.width,
            layout.notice.height,
            ModernUiMetrics.TEXT_PRIMARY,
            1, 1,
            7.5F,
            false
        );

        if (model.overlay != -1) {
            renderOverlay(model, layout);
            return;
        }

        switch (model.tab) {
            case 0:
                renderMouse(model, layout);
                break;
            case 1:
                renderGender(model, layout);
                break;
            case 2:
                renderHead(model, layout);
                break;
            case 3:
                renderBody(model, layout);
                break;
            default:
                break;
        }
    }

    private static void renderTabs(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        for (int i = 0; i < TAB_TITLES.length; i++) {
            ModernUiRect tab = layout.tab(i);
            boolean hover = tab.contains(Mouse.lastMouseX, Mouse.lastMouseY);
            drawAsset(
                i == model.tab || hover
                    ? "controls/icon-button-active"
                    : "controls/icon-button",
                tab
            );

            ModernUiRect icon = new ModernUiRect(
                tab.centerX() - 15,
                tab.centerY() - 15,
                30,
                30
            );
            drawAsset(TAB_ICONS[i], icon);
        }
    }

    private static void renderMouse(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        drawPrompt("Please select your style of mouse.", layout);

        renderChoiceCard(
            layout.choiceCard(0),
            "game-ui/setup-mouse-two",
            "2 Button",
            "For a mouse with at least 2 buttons.",
            isSelectedChoice(model, 37)
        );
        renderChoiceCard(
            layout.choiceCard(1),
            "game-ui/setup-mouse-one",
            "1 Button",
            "For a mouse with only 1 button.",
            isSelectedChoice(model, 40)
        );
        drawFooterButton(layout.footerButton(0, 1), "Next");
    }

    private static void renderGender(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        drawPrompt("Please select your character gender.", layout);
        renderChoiceCard(
            layout.choiceCard(0),
            "game-ui/setup-male",
            "Male",
            "Use a male character appearance.",
            isSelectedChoice(model, 49)
        );
        renderChoiceCard(
            layout.choiceCard(1),
            "game-ui/setup-female",
            "Female",
            "Use a female character appearance.",
            isSelectedChoice(model, 52)
        );
        drawFooterButton(layout.footerButton(0, 2), "Back");
        drawFooterButton(layout.footerButton(1, 2), "Next");
    }

    private static void renderHead(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        drawPane(layout.leftPane, "Live Preview");
        drawPane(layout.rightPane, "Colour");

        ModernUiRect preview = layout.previewCard(true);
        drawAsset("game-ui/tile", preview);

        Component modelComponent = model.get(79);
        if (modelComponent != null) {
            setClip(preview);
            ModernModelContentRenderer.render(
                modelComponent,
                new ModernUiRect(
                    preview.x + 12,
                    preview.y + 8,
                    preview.width - 24,
                    preview.height - 16
                )
            );
            resetClip();
        }

        drawSelector(layout.headSelector(0), "Hair");
        drawSelector(layout.headSelector(1), "Facial Hair");

        boolean skin = isVisible(model, 150);
        drawPaletteTabs(
            layout,
            new String[] {"Hair", "Skin"},
            skin ? 1 : 0
        );
        int[] palette = skin ? HEAD_SKIN_COLOURS : HEAD_HAIR_COLOURS;
        renderPalette(layout, palette, skin ? SKIN_COLOURS : HAIR_COLOURS);

        drawFooterButton(layout.footerButton(0, 3), "Back");
        drawFooterButton(layout.footerButton(1, 3), "Randomise");
        drawFooterButton(layout.footerButton(2, 3), "Next");
    }

    private static void renderBody(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        drawPane(layout.leftPane, "Features");
        drawPane(layout.centerPane, "Live Preview");
        drawPane(layout.rightPane, "Colour");

        String[] labels = {"Torso", "Arms", "Wrists", "Legs", "Feet"};
        for (int i = 0; i < labels.length; i++) {
            drawSelector(layout.bodySelector(i), labels[i]);
        }

        ModernUiRect preview = layout.previewCard(false);
        drawAsset("game-ui/tile", preview);
        Component modelComponent = model.get(339);
        if (modelComponent != null) {
            setClip(preview);
            ModernModelContentRenderer.render(
                modelComponent,
                new ModernUiRect(
                    preview.x + 12,
                    preview.y + 8,
                    preview.width - 24,
                    preview.height - 16
                )
            );
            resetClip();
        }

        int active =
            isVisible(model, 306)
                ? 2
                : isVisible(model, 247)
                    ? 1
                    : 0;
        drawPaletteTabs(
            layout,
            new String[] {"Torso", "Legs", "Feet"},
            active
        );
        renderPalette(layout, activeBodyPalette(model), activeBodyColours(model));

        drawFooterButton(layout.footerButton(0, 3), "Back");
        drawFooterButton(layout.footerButton(1, 3), "Randomise");
        drawFooterButton(layout.footerButton(2, 3), "Confirm");
    }

    private static void renderOverlay(
        Model model,
        ModernCharacterSetupLayout layout
    ) {
        drawAsset("game-ui/tile", layout.content);

        String title;
        String body;
        String yes;
        String no;

        if (model.overlay == 26) {
            title = "Confirm Character";
            body = "Do you want to accept these character settings?";
            yes = "Yes";
            no = "No";
        } else {
            title = "Randomise Settings?";
            body =
                model.overlay == 28
                    ? "Randomise your character head settings?"
                    : "Randomise your character body settings?";
            yes = "Randomise";
            no = "Cancel";
        }

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            title,
            layout.content.x + 24,
            layout.content.y + 60,
            layout.content.width - 48,
            28,
            ModernUiMetrics.TEXT_GOLD,
            1, 1,
            ModernUiMetrics.FONT_SECTION,
            false
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            body,
            layout.content.x + 32,
            layout.content.y + 104,
            layout.content.width - 64,
            50,
            ModernUiMetrics.TEXT_PRIMARY,
            1, 1,
            ModernUiMetrics.FONT_CONTROL,
            false
        );

        if (model.overlay == 28 || model.overlay == 29) {
            ModernUiRect toggle = overlayToggle(layout);
            drawAsset("controls/button", toggle);
            Component toggleComponent =
                model.get(model.overlay == 28 ? 381 : 398);
            boolean selected =
                toggleComponent != null
                    && Cs1ScriptRunner.isTrue(toggleComponent);
            if (selected) {
                fillRect(
                    toggle.x + 10,
                    toggle.centerY() - 5,
                    10,
                    10,
                    ModernUiMetrics.TEXT_GOLD
                );
            }
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.PLAIN_11,
                "Don't ask me this again",
                toggle.x + 30,
                toggle.y,
                toggle.width - 36,
                toggle.height,
                ModernUiMetrics.TEXT_PRIMARY,
                0, 1, 8.5F, false
            );
        }

        drawFooterButton(layout.footerButton(0, 2), yes);
        drawFooterButton(layout.footerButton(1, 2), no);
    }

    private static ModernUiRect overlayToggle(
        ModernCharacterSetupLayout layout
    ) {
        return new ModernUiRect(
            layout.content.centerX() - 120,
            layout.content.bottom() - 54,
            240,
            28
        );
    }

    private static void drawPrompt(
        String text,
        ModernCharacterSetupLayout layout
    ) {
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            text,
            layout.content.x,
            layout.content.y,
            layout.content.width,
            28,
            0x65C85A,
            1, 1,
            ModernUiMetrics.FONT_CONTROL,
            false
        );
    }

    private static void renderChoiceCard(
        ModernUiRect rect,
        String iconAsset,
        String title,
        String description,
        boolean selected
    ) {
        boolean hover = rect.contains(Mouse.lastMouseX, Mouse.lastMouseY);
        drawAsset(
            selected
                ? "game-ui/tile-selected"
                : hover
                    ? "game-ui/tile-hover"
                    : "game-ui/tile",
            rect
        );

        int iconSize = Math.min(72, rect.height / 2);
        ModernUiRect icon = new ModernUiRect(
            rect.centerX() - iconSize / 2,
            rect.y + 18,
            iconSize,
            iconSize
        );
        drawAsset(iconAsset, icon);

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            title,
            rect.x + 8,
            icon.bottom() + 8,
            rect.width - 16,
            24,
            ModernUiMetrics.TEXT_PRIMARY,
            1, 1,
            ModernUiMetrics.FONT_CONTROL,
            false
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_11,
            description,
            rect.x + 12,
            icon.bottom() + 34,
            rect.width - 24,
            Math.max(30, rect.bottom() - icon.bottom() - 42),
            ModernUiMetrics.TEXT_PARCHMENT,
            1, 1,
            8.5F,
            false
        );
    }

    private static void drawPane(
        ModernUiRect rect,
        String label
    ) {
        drawAsset("game-ui/tile", rect);
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            label,
            rect.x + 8,
            rect.y + 4,
            rect.width - 16,
            22,
            ModernUiMetrics.TEXT_GOLD,
            1, 1,
            9.0F,
            false
        );
    }

    private static void drawSelector(
        ModernUiRect rect,
        String label
    ) {
        drawAsset("controls/button", rect);
        ModernUiRect left =
            currentLayout().selectorArrow(rect, false);
        ModernUiRect right =
            currentLayout().selectorArrow(rect, true);

        ModernUiRect leftIcon =
            new ModernUiRect(
                left.centerX() - 8,
                left.centerY() - 8,
                16,
                16
            );
        drawAsset("game-ui/setup-arrow-left", leftIcon);
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_11,
            label,
            rect.x + left.width,
            rect.y,
            rect.width - left.width - right.width,
            rect.height,
            ModernUiMetrics.TEXT_GOLD,
            1, 1, 8.5F, false
        );
        ModernUiRect rightIcon =
            new ModernUiRect(
                right.centerX() - 8,
                right.centerY() - 8,
                16,
                16
            );
        drawAsset("game-ui/setup-arrow-right", rightIcon);
    }

    private static void drawPaletteTabs(
        ModernCharacterSetupLayout layout,
        String[] labels,
        int active
    ) {
        for (int i = 0; i < labels.length; i++) {
            ModernUiRect rect = layout.paletteTab(i, labels.length);
            drawAsset(
                i == active
                    ? "controls/button-active"
                    : "controls/button",
                rect
            );
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.BOLD_12,
                labels[i],
                rect.x,
                rect.y,
                rect.width,
                rect.height,
                i == active
                    ? ModernUiMetrics.TEXT_GOLD
                    : ModernUiMetrics.TEXT_PRIMARY,
                1, 1, 8.0F, false
            );
        }
    }

    private static void renderPalette(
        ModernCharacterSetupLayout layout,
        int[] components,
        int[] colours
    ) {
        for (int i = 0; i < components.length; i++) {
            ModernUiRect rect = layout.swatch(i, components.length);
            boolean hover = rect.contains(Mouse.lastMouseX, Mouse.lastMouseY);
            drawAsset(
                hover ? "game-ui/tile-hover" : "game-ui/tile",
                rect
            );
            fillRect(
                rect.x + 5,
                rect.y + 5,
                Math.max(1, rect.width - 10),
                Math.max(1, rect.height - 10),
                colours[i % colours.length]
            );
        }
    }

    private static void drawFooterButton(
        ModernUiRect rect,
        String label
    ) {
        boolean hover = rect.contains(Mouse.lastMouseX, Mouse.lastMouseY);
        drawAsset(
            hover ? "controls/button-active" : "controls/button",
            rect
        );
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.BOLD_12,
            label,
            rect.x + 4,
            rect.y,
            rect.width - 8,
            rect.height,
            hover
                ? ModernUiMetrics.TEXT_GOLD
                : ModernUiMetrics.TEXT_PRIMARY,
            1, 1, 15.0F, false
        );
    }

    private static boolean isSelectedChoice(
        Model model,
        int child
    ) {
        if (child == 37) {
            return VarpDomain.activeVarps.length > 170
                && VarpDomain.activeVarps[170] != 1;
        }
        if (child == 40) {
            return VarpDomain.activeVarps.length > 170
                && VarpDomain.activeVarps[170] == 1;
        }
        if (child == 49) {
            return VarcDomain.varcs.length > 196
                && VarcDomain.varcs[196] != 1;
        }
        if (child == 52) {
            return VarcDomain.varcs.length > 196
                && VarcDomain.varcs[196] == 1;
        }
        return false;
    }

    private static int[] activeBodyPalette(Model model) {
        if (isVisible(model, 306)) {
            return BODY_FEET_COLOURS;
        }
        if (isVisible(model, 247)) {
            return BODY_LEG_COLOURS;
        }
        return BODY_TORSO_COLOURS;
    }

    private static int[] activeBodyColours(Model model) {
        if (isVisible(model, 306)) {
            return FEET_COLOURS;
        }
        if (isVisible(model, 247)) {
            return LEG_COLOURS;
        }
        return TORSO_COLOURS;
    }

    private static Model discover(Component[] components) {
        Component[] byChild = new Component[404];
        collect(components, byChild);

        int tab = 0;
        for (int i = 0; i < 4; i++) {
            Component page = get(byChild, 22 + i);
            if (page != null && !page.hidden) {
                tab = i;
                break;
            }
        }

        Component title = get(byChild, 13);
        if (title != null) {
            String value = currentText(title).toLowerCase();
            if (value.contains("gender")) tab = 1;
            else if (value.contains("head")) tab = 2;
            else if (value.contains("body")) tab = 3;
            else if (value.contains("mouse")) tab = 0;
        }

        int overlay = -1;
        for (int id : new int[] {26, 28, 29}) {
            Component component = get(byChild, id);
            if (component != null && !component.hidden) {
                overlay = id;
                break;
            }
        }

        return new Model(byChild, tab, overlay);
    }

    private static void collect(
        Component[] components,
        Component[] byChild
    ) {
        if (components == null) {
            return;
        }
        for (Component component : components) {
            if (component == null) {
                continue;
            }
            int child = component.id & 0xFFFF;
            if (child >= 0 && child < byChild.length) {
                byChild[child] = component;
            }
            if (component.createdComponents != null) {
                collect(component.createdComponents, byChild);
            }
        }
    }

    private static Component get(
        Component[] components,
        int child
    ) {
        return child >= 0 && child < components.length
            ? components[child]
            : null;
    }

    private static void bind(
        Model model,
        int child,
        ModernUiRect bounds,
        ModernUiRect clip
    ) {
        Component component = model.get(child);
        if (component != null) {
            ModernUiInputRouter.bind(component, bounds, clip);
        }
    }

    private static boolean isVisible(
        Model model,
        int child
    ) {
        Component component = model.get(child);
        return component != null && !component.hidden;
    }

    private static String currentText(
        Component component
    ) {
        if (component == null || component.text == null) {
            return "";
        }
        JagString text = component.text;
        if (Cs1ScriptRunner.isTrue(component)
            && component.activeText != null
            && component.activeText.length() > 0) {
            text = component.activeText;
        }
        if (!component.if3 && text != null) {
            text = Cs1ScriptRunner.interpolate(component, text);
        }
        return text == null ? "" : text.toString();
    }

    private static ModernCharacterSetupLayout currentLayout() {
        ModernCharacterSetupLayout layout =
            ModernCharacterSetupLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight,
                windowX,
                windowY
            );
        windowX = layout.panel.x;
        windowY = layout.panel.y;
        return layout;
    }

    public static void resetPosition() {
        windowX = Integer.MIN_VALUE;
        windowY = Integer.MIN_VALUE;
        dragging = false;
    }

    private static int[] range(int start, int end) {
        int[] values = new int[end - start + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = start + i;
        }
        return values;
    }

    private static void fillRect(
        int x,
        int y,
        int width,
        int height,
        int color
    ) {
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, color);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, color);
        }
    }

    private static void setClip(ModernUiRect rect) {
        if (GlRenderer.enabled) {
            GlRaster.setClip(
                rect.x,
                rect.y,
                rect.right(),
                rect.bottom()
            );
        } else {
            SoftwareRaster.setClip(
                rect.x,
                rect.y,
                rect.right(),
                rect.bottom()
            );
            Rasteriser.prepare();
        }
    }

    private static void resetClip() {
        if (GlRenderer.enabled) {
            GlRaster.setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
        } else {
            SoftwareRaster.setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            Rasteriser.prepare();
        }
    }

    private static void drawAsset(
        String path,
        ModernUiRect rect
    ) {
        ModernUiImage image =
            ModernUiAssetResolver.get(
                path,
                rect.width,
                rect.height
            );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + path,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    private static final class Model {
        private final Component[] byChild;
        private final int tab;
        private final int overlay;

        private Model(
            Component[] byChild,
            int tab,
            int overlay
        ) {
            this.byChild = byChild;
            this.tab = Math.max(0, Math.min(3, tab));
            this.overlay = overlay;
        }

        private Component get(int child) {
            return ModernCharacterSetupUi.get(byChild, child);
        }
    }
}
