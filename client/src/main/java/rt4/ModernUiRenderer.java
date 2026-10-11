package rt4;

import plugin.PluginRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Independent Modern UI renderer.
 *
 * Legacy Component objects are retained only as state/action backends for
 * rebuilt screens. Modern layout, rendering and input geometry are owned by
 * the Modern UI classes. Visuals do not call Component.getSprite or
 * Component.getFont. Missing visual implementations use high-visibility pink
 * diagnostics.
 */
public final class ModernUiRenderer {
    private static final Set<String> loggedMissing = new HashSet<>();
    private static int graphicsOptionsDepth;
    private static int audioOptionsDepth;
    private static int loginScreenDepth;
    private static int titleMenuDepth;
    private static int welcomeDepth;
    private static int inGameTopLevelDepth;
    private static int graphicsOptionsTitleCenterX;
    private static int graphicsOptionsTitleY;
    private static boolean graphicsOptionsBrightnessRendered;
    private static final List<UiRect> graphicsOptionsDropdownRects = new ArrayList<>();
    private static UiRect graphicsOptionsBrightnessRect;

    private static int mainMenuDepth;
    private static MainMenuLayout mainMenuLayout;
    private static int suppressDiagnosticsDepth;
    private static boolean loginUiActivated;

    private ModernUiRenderer() {
    }

    public static void renderInterface(
        int interfaceId,
        int clipLeft,
        int clipRight,
        int parentX,
        int rectangle,
        int clipBottom,
        int clipTop,
        int parentY
    ) {
        if (!InterfaceList.load(interfaceId)) {
            markDirty(rectangle);

            // The login interface can be requested for a frame or two while
            // its JS5 group is still arriving. Log that condition, but never
            // paint a giant pink bootstrap rectangle over the game scene.
            if (interfaceId == LoginManager.loginScreenId
                && !loginUiActivated) {
                suppressDiagnosticsDepth++;
                drawMissing(
                    "interface:" + interfaceId,
                    parentX + 8,
                    parentY + 8,
                    220,
                    36
                );
                suppressDiagnosticsDepth--;
                return;
            }

            drawMissing(
                "interface:" + interfaceId,
                parentX + 8,
                parentY + 8,
                220,
                36
            );
            return;
        }

        Component[] loadedComponents = InterfaceList.components[interfaceId];

        ModernGameUiDiagnostics.observe(
            interfaceId,
            loadedComponents
        );

        if (ModernInventoryPanelUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernInventoryPanelUi.render(
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        if (ModernStatusOrbUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernStatusOrbUi.render(
                interfaceId,
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        if (ModernSidebarPanelUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernSidebarPanelUi.render(
                interfaceId,
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        if (ModernChatPanelUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernChatPanelUi.render(
                interfaceId,
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        if (ModernQuestChatUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernQuestChatUi.render(
                interfaceId,
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        if (ModernInGameDialogUi.handles(interfaceId)) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernInGameDialogUi.render(
                interfaceId,
                loadedComponents
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return;
        }

        boolean inGameTopLevel =
            ModernGameUi.isTopLevel(interfaceId);
        if (inGameTopLevel) {
            ModernGameUi.prepare(loadedComponents);
            inGameTopLevelDepth++;
        }

        boolean graphicsOptions =
            GraphicsOptionsUiInjector.isGraphicsOptionsActive(loadedComponents);
        boolean audioOptions =
            ModernAudioOptionsUi.isAudioOptionsActive(loadedComponents);
        boolean loginScreen =
            interfaceId == LoginManager.loginScreenId
                && ModernLoginScreenUi.isLoginScreenActive(
                    loadedComponents
                );

        MainMenuLayout detectedMainMenu =
            interfaceId == LoginManager.loginScreenId
                && !loginScreen
                ? analyzeMainMenu(loadedComponents, parentX, parentY)
                : null;

        boolean titleMenu =
            interfaceId == LoginManager.loginScreenId
                && !loginScreen
                && detectedMainMenu == null
                && !graphicsOptions
                && !audioOptions
                && ModernTitleMenuUi.isActive(loadedComponents);

        boolean welcome =
            ModernWelcomeUi.isActive(loadedComponents);
        boolean mainMenu = detectedMainMenu != null;

        if (mainMenu
            || graphicsOptions
            || audioOptions
            || loginScreen
            || titleMenu) {
            loginUiActivated = true;
        }

        boolean suppressStartupDiagnostics =
            interfaceId == LoginManager.loginScreenId
                && !loginUiActivated
                && !mainMenu
                && !graphicsOptions
                && !audioOptions
                && !loginScreen
                && !titleMenu;

        int oldTitleCenterX = graphicsOptionsTitleCenterX;
        int oldTitleY = graphicsOptionsTitleY;
        boolean oldBrightnessRendered = graphicsOptionsBrightnessRendered;
        List<UiRect> oldDropdownRects = new ArrayList<>(graphicsOptionsDropdownRects);
        UiRect oldBrightnessRect = graphicsOptionsBrightnessRect;
        MainMenuLayout oldMainMenuLayout = mainMenuLayout;

        if (suppressStartupDiagnostics) {
            suppressDiagnosticsDepth++;
        }

        if (mainMenu) {
            mainMenuDepth++;
            mainMenuLayout = detectedMainMenu;
        }

        if (graphicsOptions) {
            // Modern Graphics Options owns its full visual layout. The RT4
            // component tree remains loaded only as a state/event backend.
            graphicsOptionsDepth++;
        }

        if (audioOptions) {
            // Audio Options uses the original components only for their
            // scripts/input state. All visible chrome is Modern vector UI.
            audioOptionsDepth++;
        }

        if (loginScreen) {
            // Username/password login is a complete Modern-owned surface.
            // Vanilla components remain loaded only for scripts and actions.
            loginScreenDepth++;
        }

        if (titleMenu) {
            // Remaining title/account/world screens use one Modern-owned
            // fallback surface. Vanilla is behavior/state only.
            titleMenuDepth++;
        }

        if (welcome) {
            welcomeDepth++;
        }

        renderComponents(
            loadedComponents,
            -1,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom,
            parentX,
            parentY,
            rectangle
        );

        if (loginScreen) {
            setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
            ModernLoginScreenUi.render(
                loadedComponents,
                parentX,
                parentY
            );
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            loginScreenDepth--;
        }

        if (titleMenu) {
            setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
            ModernTitleMenuUi.render(
                loadedComponents,
                parentX,
                parentY
            );
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            titleMenuDepth--;
        }

        if (welcome) {
            setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
            ModernWelcomeUi.render(
                loadedComponents,
                parentX,
                parentY
            );
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            welcomeDepth--;
        }

        if (mainMenu) {
            // Permanent UI pass: everything in the Modern main menu is
            // composited after the complete legacy login component tree.
            // This keeps the scene fade/transition confined to the background.
            renderPermanentMainMenuUi();
            setClip(clipLeft, clipTop, clipRight, clipBottom);
        }

        if (audioOptions) {
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            ModernAudioOptionsUi.render(
                interfaceId,
                loadedComponents,
                parentX,
                parentY
            );
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            audioOptionsDepth--;
        }

        if (graphicsOptions) {
            renderPermanentGraphicsOptionsUi(
                loadedComponents,
                parentX,
                parentY
            );
            setClip(clipLeft, clipTop, clipRight, clipBottom);

            graphicsOptionsDepth--;
            graphicsOptionsTitleCenterX = oldTitleCenterX;
            graphicsOptionsTitleY = oldTitleY;
            graphicsOptionsBrightnessRendered = oldBrightnessRendered;
            graphicsOptionsDropdownRects.clear();
            graphicsOptionsDropdownRects.addAll(oldDropdownRects);
            graphicsOptionsBrightnessRect = oldBrightnessRect;
        }

        if (mainMenu) {
            mainMenuDepth--;
            mainMenuLayout = oldMainMenuLayout;
        }

        if (inGameTopLevel) {
            setClip(
                0,
                0,
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            ModernGameUi.renderChrome();
            setClip(clipLeft, clipTop, clipRight, clipBottom);
            inGameTopLevelDepth--;
        }

        if (suppressStartupDiagnostics) {
            suppressDiagnosticsDepth--;
        }
    }

    private static void renderComponents(
        Component[] components,
        int layer,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom,
        int parentX,
        int parentY,
        int parentRectangle
    ) {
        setClip(clipLeft, clipTop, clipRight, clipBottom);

        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            if (component.clientCode > 0) {
                Cs1ScriptRunner.applyClientCode(component);
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
            ModernUiRect gameBounds =
                ModernGameUi.bounds(component);
            if (gameBounds != null) {
                x = gameBounds.x;
                y = gameBounds.y;
            }

            int rectangle = parentRectangle;
            if (rectangle == -1 && InterfaceList.rectangles < InterfaceList.rectangleX.length) {
                rectangle = InterfaceList.rectangles++;
                InterfaceList.rectangleX[rectangle] = x;
                InterfaceList.rectangleY[rectangle] = y;
                InterfaceList.rectangleWidth[rectangle] = component.width;
                InterfaceList.rectangleHeight[rectangle] = component.height;
            }

            component.rectangleLoop = client.loop;
            component.rectangle = rectangle;

            int left = Math.max(clipLeft, x);
            int top = Math.max(clipTop, y);
            int renderWidth =
                gameBounds == null
                    ? Math.max(1, component.width)
                    : gameBounds.width;
            int renderHeight =
                gameBounds == null
                    ? Math.max(1, component.height)
                    : gameBounds.height;
            int right = Math.min(
                clipRight,
                x + renderWidth
            );
            int bottom = Math.min(
                clipBottom,
                y + renderHeight
            );
            if (right <= left || bottom <= top) {
                continue;
            }

            if (component.clientCode != 0 && renderClientComponent(component, x, y, rectangle, clipLeft, clipTop, clipRight, clipBottom)) {
                continue;
            }

            if (component.type == 0) {
                renderComponents(
                    components,
                    component.id,
                    left,
                    top,
                    right,
                    bottom,
                    x - component.scrollX,
                    y - component.scrollY,
                    rectangle
                );

                if (component.createdComponents != null) {
                    renderComponents(
                        component.createdComponents,
                        component.id,
                        left,
                        top,
                        right,
                        bottom,
                        x - component.scrollX,
                        y - component.scrollY,
                        rectangle
                    );
                }

                ComponentPointer open = (ComponentPointer) InterfaceList.openInterfaces.get(component.id);
                if (open != null) {
                    renderInterface(open.interfaceId, left, right, x, rectangle, bottom, top, y);
                }

                if (client.gameState == 30
                    && component.scrollMaxV > component.height) {
                    renderModernScrollbar(
                        component,
                        x,
                        y
                    );
                }

                setClip(clipLeft, clipTop, clipRight, clipBottom);
                continue;
            }

            renderComponentVisual(component, x, y);

            if (rectangle >= 0 && rectangle < InterfaceList.rectangleRedraw.length) {
                InterfaceList.rectangleRedraw[rectangle] = true;
            }
        }
    }

    private static boolean renderClientComponent(
        Component component,
        int x,
        int y,
        int rectangle,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (component.clientCode == 1337 || component.clientCode == 1403 && GlRenderer.enabled) {
            InterfaceList.gameViewportComponent = component;
            InterfaceList.viewportX = y;
            Cs1ScriptRunner.gameSceneTooltipX = x;
            ScriptRunner.renderGameScene(component.height, component.clientCode == 1403, x, component.width, y);
            setClip(clipLeft, clipTop, clipRight, clipBottom);

            // Scene rendering belongs to the background pass only. The
            // complete Modern login UI is composited after this interface's
            // legacy scene/fade component pass has finished.
            return true;
        }

        if (component.clientCode == 1405) {
            // Plugin draw hook is not a legacy UI fallback. Plugins must opt in
            // to Modern UI compatibility independently.
            PluginRepository.Draw();
            return true;
        }

        if (component.clientCode == 1338
            || component.clientCode == 1339) {
            if (ModernGameUi.renderClientComponent(
                component,
                rectangle
            )) {
                setClip(
                    clipLeft,
                    clipTop,
                    clipRight,
                    clipBottom
                );
                return true;
            }

            drawMissing(
                component.clientCode == 1338
                    ? "minimap"
                    : "compass",
                x,
                y,
                component.width,
                component.height
            );
            return true;
        }
        if (component.clientCode == 1400) {
            WorldMap.render(
                x,
                y,
                component.height,
                component.width
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return true;
        }
        if (component.clientCode == 1401) {
            Cs1ScriptRunner.renderWorldMapOverview(
                x,
                component.height,
                component.width,
                y
            );
            setClip(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            return true;
        }
        if (component.clientCode == 1402) {
            if (mainMenuDepth == 0
                && loginScreenDepth == 0
                && titleMenuDepth == 0
                && graphicsOptionsDepth == 0
                && audioOptionsDepth == 0) {
                drawMissing(
                    "login-flames",
                    x,
                    y,
                    component.width,
                    component.height
                );
            }
            return true;
        }
        if (component.clientCode == 1406) {
            Cs1ScriptRunner.tooltipRenderX = x;
            Cs1ScriptRunner.tooltipRenderY = y;
            return true;
        }
        return false;
    }

    private static void renderComponentVisual(Component component, int x, int y) {
        if (graphicsOptionsDepth > 0
            || audioOptionsDepth > 0
            || loginScreenDepth > 0
            || titleMenuDepth > 0
            || welcomeDepth > 0
            || inGameTopLevelDepth > 0) {
            // Rebuilt Modern screens never paint cache-era UI components.
            // Their vanilla trees remain available only as state/action
            // backends while the game scene stays in the background pass.
            return;
        }

        if (mainMenuDepth > 0
            && isManagedMainMenuComponent(component)) {
            // Main Menu is also a permanent Modern-owned surface. The cache
            // component is retained only as an action/state backend.
            return;
        }

        switch (component.type) {
            case 2:
                renderInventory(component, x, y);
                break;
            case 3:
                renderRectangle(component, x, y);
                break;
            case 4:
                renderText(component, x, y);
                break;
            case 5:
                renderImage(component, x, y);
                break;
            case 6:
                if (!ModernModelContentRenderer.render(
                    component,
                    x,
                    y
                )) {
                    drawMissing(
                        "model:" + component.id,
                        x,
                        y,
                        component.width,
                        component.height
                    );
                }
                break;
            case 7:
                renderItemText(component, x, y);
                break;
            case 8:
                renderText(component, x, y);
                break;
            case 9:
                drawOutline(x, y, component.width, component.height, safeColor(component.color));
                break;
            default:
                drawMissing("component-type-" + component.type + ":" + component.id, x, y, component.width, component.height);
                break;
        }
    }

    private static void renderRectangle(Component component, int x, int y) {
        if (graphicsOptionsDepth > 0) {
            // Cache rectangles are part of the 2009 skin. Modern Graphics
            // Options supplies its own chrome and keeps only their state/text.
            return;
        }

        int color = safeColor(component.color);
        int alpha = 256 - (component.alpha & 0xFF);
        if (component.filled) {
            if (GlRenderer.enabled) {
                if (component.alpha == 0) {
                    GlRaster.fillRect(x, y, component.width, component.height, color);
                } else {
                    GlRaster.fillRectAlpha(x, y, component.width, component.height, color, alpha);
                }
            } else if (component.alpha == 0) {
                SoftwareRaster.fillRect(x, y, component.width, component.height, color);
            } else {
                SoftwareRaster.fillRectAlpha(x, y, component.width, component.height, color, alpha);
            }
        } else {
            drawOutline(x, y, component.width, component.height, color);
        }
    }

    private static void renderText(Component component, int x, int y) {
        if (graphicsOptionsDepth > 0
            && component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT) {
            // The injected Modern UI selector value is owned by the Killer
            // Edition overlay. Suppress only that synthetic copy here.
            return;
        }

        JagString display = component.text;
        int color = component.color;

        if (Cs1ScriptRunner.isTrue(component)) {
            color = component.activeColor;
            if (component.activeText != null && component.activeText.length() > 0) {
                display = component.activeText;
            }
        }

        if (component.if3 && component.objId != -1) {
            ObjType object = ObjTypeList.get(component.objId);
            if (object != null && object.name != null) {
                display = object.name;
            }
        }

        if (Cs1ScriptRunner.pleaseWaitComponent == component) {
            display = LocalizedText.PLEASEWAIT;
        }

        if (!component.if3 && display != null) {
            display = Cs1ScriptRunner.interpolate(component, display);
        }

        String text = display == null ? "" : display.toString();

        if (mainMenuDepth > 0
            && normalizeGraphicsOptionsText(text).equals("music volume")) {
            renderMainMenuMusicLabel(component, text, x, y, color);
            return;
        }

        if (mainMenuDepth > 0 && isManagedMainMenuText(text)) {
            ensureMainMenuBackdrop();
            renderMainMenuText(component, text, x, y);
            return;
        }

        boolean syntheticKillerText =
            component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT
                || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT
                || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_LABEL_TEXT;

        String fontAsset =
            component.font == -1
                ? ModernUiFontRegistry.DEFAULT
                : ModernUiFontRegistry.resolveAsset(component.font);

        if (!syntheticKillerText
            && graphicsOptionsDepth <= 0
            && component.font != -1
            && fontAsset == null) {
            if (client.gameState == 30) {
                fontAsset = ModernUiFontRegistry.DEFAULT;
            } else {
                drawMissingLegacyGlyphs(
                    component,
                    text,
                    x,
                    y,
                    12.0F
                );
                return;
            }
        }

        if (fontAsset == null) {
            fontAsset = ModernUiFontRegistry.DEFAULT;
        }

        if (graphicsOptionsDepth > 0) {
            if (component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT) {
                int buttonWidth = Math.max(
                    ModernUiMetrics.CONTROL_WIDTH,
                    Math.max(1, component.width)
                );
                int buttonHeight = ModernUiMetrics.CONTROL_HEIGHT;
                int buttonX = x + (component.width - buttonWidth) / 2;
                int buttonY = y + (component.height - buttonHeight) / 2;
                ModernUiImage button = ModernUiAssetResolver.get(
                    "controls/button",
                    buttonWidth,
                    buttonHeight
                );
                if (button != null) {
                    button.render(buttonX, buttonY);
                } else {
                    drawMissing(
                        "asset:controls/button",
                        buttonX,
                        buttonY,
                        buttonWidth,
                        buttonHeight
                    );
                }

                ModernTrueTypeFont.drawInBox(
                    fontAsset,
                    text,
                    buttonX,
                    buttonY,
                    buttonWidth,
                    buttonHeight,
                    ModernUiMetrics.TEXT_PRIMARY,
                    1,
                    1,
                    ModernUiMetrics.FONT_BUTTON,
                    false
                );
                return;
            }

            if (normalizeGraphicsOptionsText(text).equals("main menu")) {
                int buttonX = ModernUiMetrics.centeredX(
                    graphicsOptionsTitleCenterX,
                    ModernUiMetrics.NAV_BUTTON_WIDTH
                );
                int buttonY =
                    graphicsOptionsTitleY
                        + ModernUiMetrics.NAV_BUTTON_Y_OFFSET;

                ModernTrueTypeFont.drawInBox(
                    ModernUiFontRegistry.PLAIN_12,
                    text,
                    buttonX,
                    buttonY,
                    ModernUiMetrics.NAV_BUTTON_WIDTH,
                    ModernUiMetrics.NAV_BUTTON_HEIGHT,
                    ModernUiMetrics.TEXT_PRIMARY,
                    1,
                    1,
                    ModernUiMetrics.FONT_BUTTON,
                    false
                );
                return;
            }

            if (isGraphicsOptionsDropdownValue(component, text, y)) {
                int controlHeight = ModernUiMetrics.CONTROL_HEIGHT;
                int controlY = y + (component.height - controlHeight) / 2;
                int controlWidth = graphicsOptionsControlWidth(component);
                int controlX =
                    x + (component.width - controlWidth) / 2;

                drawModernControlBox(
                    controlX,
                    controlY,
                    controlWidth,
                    controlHeight,
                    true,
                    false
                );

                ModernTrueTypeFont.drawInBox(
                    fontAsset,
                    text,
                    controlX + ModernUiMetrics.CONTROL_TEXT_PAD_X,
                    controlY,
                    Math.max(
                        1,
                        controlWidth
                            - ModernUiMetrics.CONTROL_ARROW_RESERVED
                            - ModernUiMetrics.CONTROL_TEXT_PAD_X
                    ),
                    controlHeight,
                    ModernUiMetrics.TEXT_PRIMARY,
                    0,
                    1,
                    ModernUiMetrics.FONT_DROPDOWN,
                    false
                );
                return;
            }

            ModernTrueTypeFont.drawInBox(
                fontAsset,
                text,
                x,
                y,
                component.width,
                component.height,
                ModernUiMetrics.TEXT_PRIMARY,
                component.halign,
                component.valign,
                graphicsOptionsFontSize(text),
                false
            );
            return;
        }

        ModernTrueTypeFont.drawInBox(
            fontAsset,
            text,
            x,
            y,
            component.width,
            component.height,
            safeColor(color),
            component.halign,
            component.valign,
            ModernUiMetrics.FONT_LABEL,
            component.shadowed
        );
    }

    private static boolean isGraphicsOptionsDropdownValue(
        Component component,
        String text,
        int y
    ) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        if (component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT
            || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT) {
            return false;
        }

        int width = Math.max(1, component.width);
        int height = Math.max(1, component.height);
        if (width > 190 || height > 32) {
            return false;
        }

        int relativeY = y - graphicsOptionsTitleY;
        if (relativeY < 65 || relativeY > 350) {
            return false;
        }

        return !isGraphicsOptionsLabel(text);
    }

    private static int graphicsOptionsControlWidth(Component component) {
        return ModernUiMetrics.CONTROL_WIDTH;
    }

    private static boolean isGraphicsOptionsLabel(String text) {
        String normalized = normalizeGraphicsOptionsText(text);

        if (normalized.isEmpty()) {
            return true;
        }

        if (normalized.equals("graphics options")
            || normalized.equals("display modes")
            || normalized.equals("advanced options")
            || normalized.equals("brightness")
            || normalized.equals("visible levels")
            || normalized.equals("remove roofs")
            || normalized.equals("ground decoration")
            || normalized.equals("texture detail")
            || normalized.equals("idle animations")
            || normalized.equals("flickering effects")
            || normalized.equals("ground textures")
            || normalized.equals("character shadows")
            || normalized.equals("scenery shadows")
            || normalized.equals("lighting detail")
            || normalized.equals("water detail")
            || normalized.equals("fog")
            || normalized.equals("anti-aliasing")
            || normalized.equals("modern ui")
            || normalized.equals("style editor")
            || normalized.equals("main menu")
            || normalized.equals("standard detail")
            || normalized.equals("(small)")
            || normalized.equals("(fullscreen)")) {
            return true;
        }

        return normalized.startsWith("high detail");
    }

    private static void drawMissingLegacyGlyphs(
        Component component,
        String text,
        int x,
        int y,
        float size
    ) {
        int interfaceId = component.id >>> 16;
        int childId = component.id & 0xFFFF;
        String key =
            "font:" + component.font
                + ":component:" + interfaceId + "/" + childId;

        if (loggedMissing.add(key)) {
            DisplayDebug.log(
                "MODERN_UI MISSING LEGACY FONT"
                    + " fontId=" + component.font
                    + " interface=" + interfaceId
                    + " child=" + childId
                    + " type=" + component.type
                    + " bounds=" + x + "," + y
                    + " " + component.width + "x" + component.height
                    + " text='" + sanitizeDiagnosticText(text) + "'"
            );
        }

        String visible = stripLegacyFormattingForDiagnostics(text);
        if (visible.isEmpty()) {
            drawMissingGlyphCell(x, y, Math.max(8, component.width), Math.max(10, component.height));
            return;
        }

        int glyphHeight = Math.max(
            9,
            ModernTrueTypeFont.getLineHeight(
                ModernUiFontRegistry.DEFAULT,
                size
            )
        );
        int lineHeight = glyphHeight + 2;

        String[] lines = visible.split("\n", -1);
        int totalHeight = Math.max(lineHeight, lines.length * lineHeight);

        int top = y;
        if (component.valign == 1) {
            top = y + Math.max(0, (component.height - totalHeight) / 2);
        } else if (component.valign == 2) {
            top = y + Math.max(0, component.height - totalHeight);
        }

        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            String line = lines[lineIndex];
            int lineWidth = Math.max(
                1,
                ModernTrueTypeFont.getWidth(
                    ModernUiFontRegistry.DEFAULT,
                    line,
                    size
                )
            );

            int drawX = x;
            if (component.halign == 1) {
                drawX = x + (component.width - lineWidth) / 2;
            } else if (component.halign == 2) {
                drawX = x + component.width - lineWidth;
            }

            int drawY = top + lineIndex * lineHeight;
            int slots = Math.max(1, line.length());

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    int slotLeft = drawX + Math.round((float) i * lineWidth / slots);
                    int slotRight = drawX + Math.round((float) (i + 1) * lineWidth / slots);
                    drawMissingGlyphCell(
                        slotLeft,
                        drawY,
                        Math.max(3, slotRight - slotLeft - 1),
                        glyphHeight
                    );
                }
            }
        }

        // Diagnostic readability only: use the exact same box/alignment logic
        // as normal Modern TTF rendering. Pink remains underneath, so this is
        // visibly a diagnostic and never a silent legacy-font substitution.
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.DEFAULT,
            visible,
            x,
            y,
            component.width,
            component.height,
            0x00FFFF,
            component.halign,
            component.valign,
            size,
            true
        );
    }

    private static void drawMissingGlyphCell(
        int x,
        int y,
        int width,
        int height
    ) {
        int left = Math.max(0, x);
        int top = Math.max(0, y);
        int right = Math.min(GameShell.canvasWidth, x + Math.max(1, width));
        int bottom = Math.min(GameShell.canvasHeight, y + Math.max(1, height));

        if (right <= left || bottom <= top) {
            return;
        }

        int drawWidth = right - left;
        int drawHeight = bottom - top;
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(left, top, drawWidth, drawHeight, 0x4C1733, 220);
            GlRaster.drawRect(left, top, drawWidth, drawHeight, 0xFF44AA);
        } else {
            SoftwareRaster.fillRectAlpha(left, top, drawWidth, drawHeight, 0x4C1733, 220);
            SoftwareRaster.drawRect(left, top, drawWidth, drawHeight, 0xFF44AA);
        }
    }

    private static String stripLegacyFormattingForDiagnostics(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length();) {
            if (text.regionMatches(true, i, "<br>", 0, 4)) {
                out.append('\n');
                i += 4;
                continue;
            }

            if (text.charAt(i) == '<') {
                int end = text.indexOf('>', i + 1);
                if (end >= 0) {
                    String tag = text.substring(i + 1, end).trim();
                    if (tag.regionMatches(true, 0, "img=", 0, 4)) {
                        // Preserve one visible diagnostic cell for an inline
                        // legacy image/glyph token instead of silently
                        // deleting it like ordinary formatting markup.
                        out.append('\u25A1');
                    }
                    i = end + 1;
                    continue;
                }
            }

            out.append(text.charAt(i++));
        }
        return out.toString();
    }

    private static String sanitizeDiagnosticText(String text) {
        if (text == null) {
            return "";
        }
        String clean = text.replace('\n', ' ').replace('\r', ' ');
        if (clean.length() > 120) {
            return clean.substring(0, 117) + "...";
        }
        return clean;
    }

    private static String normalizeGraphicsOptionsText(String text) {
        StringBuilder out = new StringBuilder(text.length());
        boolean insideTag = false;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                insideTag = true;
                continue;
            }
            if (ch == '>' && insideTag) {
                insideTag = false;
                out.append(' ');
                continue;
            }
            if (!insideTag) {
                out.append(ch == '\n' || ch == '\r' || ch == '\t' ? ' ' : ch);
            }
        }

        return out.toString()
            .trim()
            .toLowerCase(java.util.Locale.ROOT)
            .replaceAll("\\s+", " ");
    }

    private static float graphicsOptionsFontSize(String text) {
        String normalized = normalizeGraphicsOptionsText(text);

        if (normalized.equals("graphics options")) {
            return ModernUiMetrics.FONT_TITLE;
        }
        if (normalized.equals("display modes")
            || normalized.equals("advanced options")) {
            return ModernUiMetrics.FONT_SECTION;
        }
        if (normalized.equals("standard detail")
            || normalized.startsWith("high detail")
            || normalized.equals("(small)")
            || normalized.equals("(fullscreen)")) {
            return ModernUiMetrics.FONT_CONTROL;
        }
        if (normalized.equals("main menu")) {
            return ModernUiMetrics.FONT_BUTTON;
        }
        return ModernUiMetrics.FONT_LABEL;
    }

    private static void renderImage(Component component, int x, int y) {
        if (component.if3 && component.objId != -1) {
            Sprite item = Inv.getObjectSprite(
                component.outlineThickness,
                component.objId,
                component.objDrawText,
                component.objCount,
                component.shadowColor
            );
            if (item != null) {
                int width = component.width > 0 ? component.width : item.width;
                int height = component.height > 0 ? component.height : item.height;
                item.renderResized(x, y, width, height);
                return;
            }
        }

        // Deliberately do not call component.getSprite(). Modern mode has no
        // Index-8/legacy UI sprite fallback.
        if (mainMenuDepth > 0 && renderMainMenuImage(component, x, y)) {
            return;
        }
        if (graphicsOptionsDepth > 0) {
            renderGraphicsOptionsImageFallback(component, x, y);
            return;
        }

        String assetKey = componentAssetKey(component);
        ModernUiImage image = ModernUiAssetResolver.get(
            assetKey,
            Math.max(1, component.width),
            Math.max(1, component.height)
        );
        if (image != null) {
            image.render(x, y);
            return;
        }
        drawMissing("asset:" + assetKey, x, y, component.width, component.height);
    }

    private static void renderInventory(Component component, int x, int y) {
        if (component.objTypes == null || component.objCounts == null) {
            drawMissing("inventory:" + component.id, x, y, component.width, component.height);
            return;
        }

        int index = 0;
        for (int row = 0; row < component.baseHeight; row++) {
            for (int column = 0; column < component.baseWidth; column++) {
                int slotX = x + column * (component.invMarginX + 32);
                int slotY = y + row * (component.invMarginY + 32);
                if (index < 20 && component.invOffsetX != null && component.invOffsetY != null) {
                    slotX += component.invOffsetX[index];
                    slotY += component.invOffsetY[index];
                }

                ModernUiRect slotRect =
                    new ModernUiRect(
                        slotX,
                        slotY,
                        32,
                        32
                    );
                boolean hover =
                    Mouse.lastMouseX >= slotX
                        && Mouse.lastMouseX < slotX + 32
                        && Mouse.lastMouseY >= slotY
                        && Mouse.lastMouseY < slotY + 32;
                drawAsset(
                    hover
                        ? "game-ui/slot-hover"
                        : "game-ui/slot",
                    slotRect
                );
                if (index < component.objTypes.length && component.objTypes[index] > 0) {
                    int objectId = component.objTypes[index] - 1;
                    int count = index < component.objCounts.length ? component.objCounts[index] : 1;
                    Sprite item = Inv.getObjectSprite(1, objectId, component.objDrawText, count, 3153952);
                    if (item != null) {
                        item.render(slotX, slotY);
                    }
                }
                index++;
            }
        }
    }

    private static void renderItemText(Component component, int x, int y) {
        if (component.objTypes == null || component.objCounts == null) {
            return;
        }

        int index = 0;
        for (int row = 0; row < component.baseHeight; row++) {
            for (int column = 0; column < component.baseWidth; column++) {
                if (index < component.objTypes.length && component.objTypes[index] > 0) {
                    ObjType object = ObjTypeList.get(component.objTypes[index] - 1);
                    if (object != null && object.name != null) {
                        int tx = x + column * (component.invMarginX + 115);
                        int ty = y + row * (component.invMarginY + 12);
                        String fontAsset =
                            component.font == -1
                                ? ModernUiFontRegistry.DEFAULT
                                : ModernUiFontRegistry.resolveAsset(component.font);
                        if (fontAsset == null
                            && client.gameState == 30) {
                            fontAsset =
                                ModernUiFontRegistry.DEFAULT;
                        }

                        if (fontAsset == null) {
                            drawMissingLegacyGlyphs(
                                component,
                                object.name.toString(),
                                tx,
                                ty,
                                ModernUiMetrics.FONT_CONTROL
                            );
                        } else {
                            ModernTrueTypeFont.draw(
                                fontAsset,
                                object.name.toString(),
                                tx,
                                ty + 11,
                                safeColor(component.color),
                                ModernUiMetrics.FONT_CONTROL,
                                component.shadowed
                            );
                        }
                    }
                }
                index++;
            }
        }
    }

    private static void renderModernScrollbar(
        Component component,
        int x,
        int y
    ) {
        int trackHeight = Math.max(24, component.height);
        ModernUiRect track = new ModernUiRect(
            x + component.width - 12,
            y,
            12,
            trackHeight
        );
        drawAsset(
            "game-ui/scrollbar-track",
            track
        );

        int thumbHeight =
            component.scrollMaxV <= 0
                ? trackHeight
                : component.height
                    * Math.max(1, component.height - 4)
                    / component.scrollMaxV;
        thumbHeight = Math.max(
            18,
            Math.min(trackHeight, thumbHeight)
        );

        int travel =
            Math.max(0, trackHeight - thumbHeight - 4);
        int maxScroll =
            Math.max(
                1,
                component.scrollMaxV - component.height
            );
        int thumbY =
            y + 2
                + travel
                    * Math.max(0, component.scrollY)
                    / maxScroll;

        drawAsset(
            "game-ui/scrollbar-thumb",
            new ModernUiRect(
                track.x + 1,
                thumbY,
                10,
                thumbHeight
            )
        );
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
            drawMissing(
                "asset:" + path,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    public static void drawMissing(String key, int x, int y, int width, int height) {
        if (loggedMissing.add(key)) {
            DisplayDebug.log(
                "MODERN_UI MISSING " + key
                    + " bounds=" + x + "," + y
                    + " " + width + "x" + height
            );
        }

        if (suppressDiagnosticsDepth > 0) {
            return;
        }

        int requestedWidth = Math.max(width, 24);
        int requestedHeight = Math.max(height, 18);

        int left = Math.max(0, x);
        int top = Math.max(0, y);
        int right = Math.min(GameShell.canvasWidth, x + requestedWidth);
        int bottom = Math.min(GameShell.canvasHeight, y + requestedHeight);

        if (right <= left || bottom <= top) {
            return;
        }

        int drawWidth = right - left;
        int drawHeight = bottom - top;

        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(left, top, drawWidth, drawHeight, 0x4C1733, 210);
            GlRaster.drawRect(left, top, drawWidth, drawHeight, 0xFF44AA);
        } else {
            SoftwareRaster.fillRectAlpha(left, top, drawWidth, drawHeight, 0x4C1733, 210);
            SoftwareRaster.drawRect(left, top, drawWidth, drawHeight, 0xFF44AA);
        }

        String label = "[MISSING " + key + "]";
        ModernTrueTypeFont.draw(
            label,
            left + 3,
            top + Math.min(drawHeight - 3, 14),
            0xFFFFFF,
            10.0F,
            true
        );
    }

    private static void drawOutline(int x, int y, int width, int height, int color) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (GlRenderer.enabled) {
            GlRaster.drawRect(x, y, width, height, color);
        } else {
            SoftwareRaster.drawRect(x, y, width, height, color);
        }
    }

    private static int safeColor(int color) {
        return color < 0 ? 0xFFFFFF : color & 0xFFFFFF;
    }

    private static void setClip(int left, int top, int right, int bottom) {
        if (GlRenderer.enabled) {
            GlRaster.setClip(left, top, right, bottom);
        } else {
            SoftwareRaster.setClip(left, top, right, bottom);
            Rasteriser.prepare();
        }
    }

    private static void markDirty(int rectangle) {
        if (rectangle < 0) {
            for (int i = 0; i < InterfaceList.rectangleDirty.length; i++) {
                InterfaceList.rectangleDirty[i] = true;
            }
        } else if (rectangle < InterfaceList.rectangleDirty.length) {
            InterfaceList.rectangleDirty[rectangle] = true;
        }
    }


    private static MainMenuLayout analyzeMainMenu(
        Component[] components,
        int parentX,
        int parentY
    ) {
        MainMenuLayout layout = new MainMenuLayout();
        collectMainMenuEntries(
            components,
            -1,
            parentX,
            parentY,
            layout
        );

        if (!layout.hasText("log in")
            || !layout.hasText("create account")
            || !layout.hasText("graphics options")
            || !layout.hasText("audio options")) {
            return null;
        }

        layout.finish();
        return layout;
    }

    public static void prepareMainMenuInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (!ModernUiManager.isEnabled()) {
            return;
        }

        MainMenuLayout layout =
            analyzeMainMenu(components, parentX, parentY);
        if (layout == null || layout.contentBounds == null) {
            return;
        }

        ModernUiRect clip = new ModernUiRect(
            layout.contentBounds.x,
            layout.contentBounds.y,
            layout.contentBounds.width,
            layout.contentBounds.height
        );

        for (MainMenuTextEntry entry : layout.texts) {
            if (entry.component == null
                || !isMainMenuActionText(entry.text)) {
                continue;
            }

            int targetY = layout.textTargetY(entry.text);
            int y =
                targetY == Integer.MIN_VALUE
                    ? entry.rect.y
                    : targetY;
            int targetX = layout.textTargetX(
                entry.text,
                entry.component.width
            );
            int x =
                targetX == Integer.MIN_VALUE
                    ? entry.rect.x
                    : targetX;
            int buttonX =
                x + (entry.component.width
                    - ModernUiMetrics.MAIN_MENU_BUTTON_WIDTH) / 2;
            int buttonY =
                y + (entry.component.height
                    - ModernUiMetrics.MAIN_MENU_BUTTON_HEIGHT) / 2;

            ModernUiInputRouter.bind(
                entry.component,
                new ModernUiRect(
                    buttonX,
                    buttonY,
                    ModernUiMetrics.MAIN_MENU_BUTTON_WIDTH,
                    ModernUiMetrics.MAIN_MENU_BUTTON_HEIGHT
                ),
                clip
            );
        }

        bindMainMenuChoiceSide(
            layout,
            true,
            layout.standardChoice,
            clip
        );
        bindMainMenuChoiceSide(
            layout,
            false,
            layout.highChoice,
            clip
        );

        if (layout.musicSlider != null
            && !layout.musicSliderComponentIds.isEmpty()) {
            List<UiRect> sourceRects = new ArrayList<>();
            for (MainMenuImageEntry image : layout.images) {
                if (layout.musicSliderComponentIds.contains(
                    image.componentId
                )) {
                    sourceRects.add(image.rect);
                }
            }
            UiRect sourceUnion = unionRects(sourceRects);
            if (sourceUnion != null) {
                for (MainMenuImageEntry image : layout.images) {
                    if (!layout.musicSliderComponentIds.contains(
                        image.componentId
                    )) {
                        continue;
                    }

                    int x =
                        layout.musicSlider.x
                            + (image.rect.x - sourceUnion.x)
                                * layout.musicSlider.width
                                / Math.max(1, sourceUnion.width);
                    int y =
                        layout.musicSlider.y
                            + (image.rect.y - sourceUnion.y)
                                * layout.musicSlider.height
                                / Math.max(1, sourceUnion.height);
                    int width = Math.max(
                        1,
                        image.rect.width
                            * layout.musicSlider.width
                            / Math.max(1, sourceUnion.width)
                    );
                    int height = Math.max(
                        1,
                        image.rect.height
                            * layout.musicSlider.height
                            / Math.max(1, sourceUnion.height)
                    );

                    ModernUiInputRouter.bind(
                        image.component,
                        new ModernUiRect(x, y, width, height),
                        clip
                    );
                }
            }
        }
    }

    private static void bindMainMenuChoiceSide(
        MainMenuLayout layout,
        boolean leftSide,
        UiRect target,
        ModernUiRect clip
    ) {
        if (target == null) {
            return;
        }

        List<MainMenuImageEntry> entries = new ArrayList<>();
        List<UiRect> sourceRects = new ArrayList<>();
        for (MainMenuImageEntry image : layout.images) {
            if (!layout.choiceComponentIds.contains(image.componentId)) {
                continue;
            }

            boolean imageLeft =
                rectCenterX(image.rect) < layout.centerX;
            if (imageLeft != leftSide) {
                continue;
            }

            entries.add(image);
            sourceRects.add(image.rect);
        }

        UiRect sourceUnion = unionRects(sourceRects);
        if (sourceUnion == null) {
            Component fallback =
                leftSide
                    ? layout.standardChoiceComponent
                    : layout.highChoiceComponent;
            if (fallback != null) {
                ModernUiInputRouter.bind(
                    fallback,
                    toModernRect(target),
                    clip
                );
            }
            return;
        }

        for (MainMenuImageEntry image : entries) {
            int x =
                target.x
                    + (image.rect.x - sourceUnion.x)
                        * target.width
                        / Math.max(1, sourceUnion.width);
            int y =
                target.y
                    + (image.rect.y - sourceUnion.y)
                        * target.height
                        / Math.max(1, sourceUnion.height);
            int width = Math.max(
                1,
                image.rect.width * target.width
                    / Math.max(1, sourceUnion.width)
            );
            int height = Math.max(
                1,
                image.rect.height * target.height
                    / Math.max(1, sourceUnion.height)
            );

            ModernUiInputRouter.bind(
                image.component,
                new ModernUiRect(x, y, width, height),
                clip
            );
        }
    }

    private static boolean isMainMenuActionText(String text) {
        String normalized = normalizeGraphicsOptionsText(text);
        return normalized.equals("log in")
            || normalized.equals("login")
            || normalized.equals("create account")
            || normalized.equals("graphics options")
            || normalized.equals("audio options")
            || normalized.equals("music options")
            || normalized.equals("quit")
            || normalized.startsWith("world ");
    }

    private static ModernUiRect toModernRect(UiRect rect) {
        return new ModernUiRect(
            rect.x,
            rect.y,
            rect.width,
            rect.height
        );
    }

    private static void collectMainMenuEntries(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        MainMenuLayout layout
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
            UiRect rect = new UiRect(
                x,
                y,
                Math.max(1, component.width),
                Math.max(1, component.height)
            );

            if (component.type == 4 || component.type == 8) {
                JagString display = component.text;
                if (Cs1ScriptRunner.isTrue(component)
                    && component.activeText != null
                    && component.activeText.length() > 0) {
                    display = component.activeText;
                }
                String text =
                    display == null
                        ? ""
                        : normalizeGraphicsOptionsText(display.toString());
                if (!text.isEmpty()) {
                    layout.texts.add(
                        new MainMenuTextEntry(
                            component,
                            component.id,
                            text,
                            rect
                        )
                    );
                }
            } else if (component.type == 5) {
                layout.images.add(
                    new MainMenuImageEntry(component, component.id, rect)
                );
            }

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collectMainMenuEntries(
                    components,
                    component.id,
                    childX,
                    childY,
                    layout
                );
                if (component.createdComponents != null) {
                    collectMainMenuEntries(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        layout
                    );
                }
            }
        }
    }

    private static boolean isManagedMainMenuText(String text) {
        String normalized = normalizeGraphicsOptionsText(text);
        if (normalized.isEmpty()) {
            return false;
        }

        return normalized.equals("log in")
            || normalized.equals("login")
            || normalized.equals("create account")
            || normalized.equals("graphics options")
            || normalized.equals("audio options")
            || normalized.equals("music options")
            || normalized.equals("quit")
            || normalized.equals("standard detail")
            || normalized.equals("high detail")
            || normalized.contains("existing user")
            || normalized.contains("new user")
            || normalized.contains("click to switch")
            || normalized.startsWith("world ");
    }

    private static boolean isManagedMainMenuComponent(
        Component component
    ) {
        if (component == null || mainMenuLayout == null) {
            return false;
        }

        if ((component.type == 4 || component.type == 8)
            && component.text != null) {
            String normalized = normalizeGraphicsOptionsText(
                component.text.toString()
            );
            return isManagedMainMenuText(normalized)
                || normalized.equals("music volume");
        }

        int id = component.id;
        return id == mainMenuLayout.bodyComponentId
            || id == mainMenuLayout.logoComponentId
            || mainMenuLayout.frameComponentIds.contains(id)
            || mainMenuLayout.choiceComponentIds.contains(id)
            || mainMenuLayout.musicSliderComponentIds.contains(id);
    }

    private static void renderMainMenuBackdrop(
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (mainMenuLayout == null) {
            return;
        }

        setClip(clipLeft, clipTop, clipRight, clipBottom);

        drawMainMenuAsset(
            "main-menu/scroll",
            mainMenuLayout.scroll
        );

        if (mainMenuLayout.logo != null) {
            drawMainMenuAsset(
                "main-menu/logo",
                mainMenuLayout.logo
            );

            int logoCenter = mainMenuLayout.logo.x
                + mainMenuLayout.logo.width / 2;

            ModernTrueTypeFont.drawCentered(
                ModernUiFontRegistry.PLAIN_11,
                "Killer Edition",
                logoCenter,
                mainMenuLayout.logo.y
                    + mainMenuLayout.logo.height
                    + 22,
                0xE4D2A2,
                ModernUiMetrics.FONT_MAIN_MENU_EDITION,
                true
            );
        }
    }

    private static void ensureMainMenuBackdrop() {
        // Intentionally deferred. The complete Modern main menu is rendered
        // once, at the end of the login interface pass, after the legacy
        // background fade/transition components have finished drawing.
    }

    private static void renderPermanentMainMenuUi() {
        if (mainMenuLayout == null) {
            return;
        }

        // Layer 1 is already complete at this point:
        // 3D login scene + legacy scene transition/fade.
        // Layer 2 begins here and is never part of that fade.
        renderMainMenuBackdrop(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        UiRect bounds = mainMenuLayout.contentBounds;
        if (bounds != null) {
            setClip(
                Math.max(0, bounds.x),
                Math.max(0, bounds.y),
                Math.min(GameShell.canvasWidth, bounds.x + bounds.width),
                Math.min(GameShell.canvasHeight, bounds.y + bounds.height)
            );
        } else {
            setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
        }

        for (MainMenuTextEntry entry : mainMenuLayout.texts) {
            if (entry.component == null) {
                continue;
            }

            String text = getCurrentMainMenuText(entry.component);
            String normalized = normalizeGraphicsOptionsText(text);
            int x = mainMenuLayout.textTargetX(
                normalized,
                entry.component.width
            );
            int y = mainMenuLayout.textTargetY(normalized);
            if (x == Integer.MIN_VALUE) {
                x = entry.rect.x;
            }
            if (y == Integer.MIN_VALUE) {
                y = entry.rect.y;
            }

            if (normalized.equals("music volume")) {
                renderMainMenuMusicLabel(
                    entry.component,
                    text,
                    x,
                    y,
                    entry.component.color
                );
            } else if (isManagedMainMenuText(normalized)) {
                renderMainMenuText(
                    entry.component,
                    text,
                    x,
                    y
                );
            }
        }

        renderMainMenuChoiceBackgrounds();
        renderMainMenuChoiceIcons();
        renderMainMenuMusicSlider();

        setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
    }

    private static String getCurrentMainMenuText(Component component) {
        if (component == null) {
            return "";
        }

        JagString display = component.text;
        if (Cs1ScriptRunner.isTrue(component)
            && component.activeText != null
            && component.activeText.length() > 0) {
            display = component.activeText;
        }

        if (!component.if3 && display != null) {
            display = Cs1ScriptRunner.interpolate(component, display);
        }

        return display == null ? "" : display.toString();
    }

    private static void renderMainMenuMusicLabel(
        Component component,
        String text,
        int x,
        int y,
        int color
    ) {
        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            text,
            x,
            y - 4,
            component.width,
            Math.max(22, component.height + 8),
            safeColor(color),
            component.halign,
            1,
            ModernUiMetrics.FONT_LABEL,
            component.shadowed
        );
    }

    private static void renderMainMenuChoiceBackgrounds() {
        if (mainMenuLayout == null) {
            return;
        }

        drawMainMenuChoiceBackground(
            mainMenuLayout.standardChoiceComponent,
            mainMenuLayout.standardChoice
        );
        drawMainMenuChoiceBackground(
            mainMenuLayout.highChoiceComponent,
            mainMenuLayout.highChoice
        );
    }

    private static void drawMainMenuChoiceBackground(
        Component component,
        UiRect rect
    ) {
        if (component == null
            || rect == null
            || rect.width <= 0
            || rect.height <= 0) {
            return;
        }

        String asset =
            Cs1ScriptRunner.isTrue(component)
                ? "main-menu/choice-active"
                : "main-menu/choice";
        ModernUiImage image = ModernUiAssetResolver.get(
            asset,
            rect.width,
            rect.height
        );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            drawMissing(
                "asset:" + asset,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    private static void drawMainMenuAsset(
        String asset,
        UiRect rect
    ) {
        if (rect == null || rect.width <= 0 || rect.height <= 0) {
            return;
        }

        ModernUiImage image = ModernUiAssetResolver.get(
            asset,
            rect.width,
            rect.height
        );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            drawMissing(
                "asset:" + asset,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    private static void renderMainMenuText(
        Component component,
        String text,
        int x,
        int y
    ) {
        String normalized = normalizeGraphicsOptionsText(text);
        boolean subtitle =
            normalized.contains("existing user")
                || normalized.contains("new user")
                || normalized.contains("click to switch");
        boolean detail =
            normalized.equals("standard detail")
                || normalized.equals("high detail");

        String fontAsset;
        float size;
        int color;

        if (subtitle) {
            fontAsset = ModernUiFontRegistry.PLAIN_11;
            size = ModernUiMetrics.FONT_MAIN_MENU_SUBTITLE;
            color = ModernUiMetrics.TEXT_PARCHMENT;
        } else if (detail) {
            fontAsset = ModernUiFontRegistry.PLAIN_11;
            size = ModernUiMetrics.FONT_MAIN_MENU_DETAIL;
            color = 0x5A351C;
        } else if (normalized.equals("log in")
            || normalized.equals("create account")
            || normalized.startsWith("world ")) {
            fontAsset = ModernUiFontRegistry.BOLD_12;
            size = normalized.startsWith("world ")
                ? ModernUiMetrics.FONT_CONTROL
                : ModernUiMetrics.FONT_MAIN_MENU_BUTTON;
            color = ModernUiMetrics.TEXT_GOLD;
        } else {
            fontAsset = ModernUiFontRegistry.PLAIN_12;
            size = ModernUiMetrics.FONT_LABEL;
            color = ModernUiMetrics.TEXT_PRIMARY;
        }

        if (!subtitle && !detail) {
            int buttonWidth = ModernUiMetrics.MAIN_MENU_BUTTON_WIDTH;
            int buttonHeight = ModernUiMetrics.MAIN_MENU_BUTTON_HEIGHT;
            int buttonX = x + (component.width - buttonWidth) / 2;
            int buttonY = y + (component.height - buttonHeight) / 2;

            boolean hover =
                Mouse.lastMouseX >= buttonX
                    && Mouse.lastMouseX < buttonX + buttonWidth
                    && Mouse.lastMouseY >= buttonY
                    && Mouse.lastMouseY < buttonY + buttonHeight;

            String asset =
                hover
                    ? "main-menu/button-active"
                    : "main-menu/button";

            ModernUiImage button = ModernUiAssetResolver.get(
                asset,
                buttonWidth,
                buttonHeight
            );
            if (button != null) {
                button.render(buttonX, buttonY);
            } else {
                drawMissing(
                    "asset:" + asset,
                    buttonX,
                    buttonY,
                    buttonWidth,
                    buttonHeight
                );
            }

            ModernTrueTypeFont.drawInBox(
                fontAsset,
                text,
                buttonX,
                buttonY,
                buttonWidth,
                buttonHeight,
                color,
                1,
                1,
                size,
                component.shadowed
            );
            return;
        }

        ModernTrueTypeFont.drawInBox(
            fontAsset,
            text,
            x,
            y,
            component.width,
            component.height,
            color,
            component.halign,
            component.valign,
            size,
            component.shadowed
        );
    }

    private static boolean renderMainMenuImage(
        Component component,
        int x,
        int y
    ) {
        if (mainMenuLayout == null) {
            return false;
        }

        int id = component.id;
        UiRect rect = new UiRect(
            x,
            y,
            Math.max(1, component.width),
            Math.max(1, component.height)
        );

        if (id == mainMenuLayout.bodyComponentId
            || id == mainMenuLayout.logoComponentId
            || mainMenuLayout.frameComponentIds.contains(id)) {
            // The complete scalable frame/logo system was drawn once behind
            // the component tree. These exact legacy pieces are now replaced.
            return true;
        }

        if (mainMenuLayout.musicSliderComponentIds.contains(id)) {
            // The original click/script components remain live, but their
            // cache sprites are completely replaced by the vector slider.
            return true;
        }

        if (mainMenuLayout.choiceComponentIds.contains(id)) {
            boolean active = Cs1ScriptRunner.isTrue(component);
            String asset =
                active
                    ? "main-menu/choice-active"
                    : "main-menu/choice";
            ModernUiImage image = ModernUiAssetResolver.get(
                asset,
                rect.width,
                rect.height
            );
            if (image != null) {
                image.render(rect.x, rect.y);
            } else {
                drawMissing(
                    "asset:" + asset,
                    rect.x,
                    rect.y,
                    rect.width,
                    rect.height
                );
            }
            return true;
        }

        return false;
    }

    private static void renderMainMenuChoiceIcons() {
        if (mainMenuLayout == null) {
            return;
        }

        drawMainMenuChoiceIcon(
            "main-menu/sd-icon",
            mainMenuLayout.standardChoice,
            123,
            80
        );
        drawMainMenuChoiceIcon(
            "main-menu/hd-icon",
            mainMenuLayout.highChoice,
            1,
            1
        );
    }

    private static void drawMainMenuChoiceIcon(
        String asset,
        UiRect rect,
        int aspectWidth,
        int aspectHeight
    ) {
        if (rect == null || rect.width <= 0 || rect.height <= 0) {
            return;
        }

        int maxHeight = Math.max(12, rect.height * 2 / 3);
        int maxWidth = Math.max(12, rect.width - 10);
        int iconHeight = maxHeight;
        int iconWidth = Math.max(
            1,
            iconHeight * aspectWidth / Math.max(1, aspectHeight)
        );

        if (iconWidth > maxWidth) {
            iconWidth = maxWidth;
            iconHeight = Math.max(
                1,
                iconWidth * aspectHeight / Math.max(1, aspectWidth)
            );
        }

        int iconX = rect.x + (rect.width - iconWidth) / 2;
        int iconY = rect.y + (rect.height - iconHeight) / 2;

        ModernUiImage image = ModernUiAssetResolver.get(
            asset,
            iconWidth,
            iconHeight
        );
        if (image != null) {
            image.render(iconX, iconY);
        } else {
            drawMissing(
                "asset:" + asset,
                iconX,
                iconY,
                iconWidth,
                iconHeight
            );
        }
    }

    private static void renderMainMenuMusicSlider() {
        if (mainMenuLayout == null || mainMenuLayout.musicSlider == null) {
            return;
        }

        UiRect rect = mainMenuLayout.musicSlider;
        int trackHeight = Math.min(
            ModernUiMetrics.SLIDER_HEIGHT,
            Math.max(12, rect.height - 6)
        );
        int trackY = rect.y + (rect.height - trackHeight) / 2;

        ModernUiImage track = ModernUiAssetResolver.get(
            "main-menu/music-volume-track",
            rect.width,
            trackHeight
        );
        if (track != null) {
            track.render(rect.x, trackY);
        } else {
            drawMissing(
                "asset:main-menu/music-volume-track",
                rect.x,
                trackY,
                rect.width,
                trackHeight
            );
        }

        int volume = Preferences.musicVolume;
        if (volume < 0) {
            volume = 0;
        } else if (volume > 255) {
            volume = 255;
        }

        int knobWidth = 20;
        int knobHeight = Math.min(26, Math.max(20, rect.height));
        int left = rect.x + 10;
        int right = rect.x + rect.width - 10;
        int knobCenterX = left + (right - left) * volume / 255;
        int knobX = knobCenterX - knobWidth / 2;
        int knobY = rect.y + (rect.height - knobHeight) / 2;

        ModernUiImage knob = ModernUiAssetResolver.get(
            "main-menu/music-volume-knob",
            knobWidth,
            knobHeight
        );
        if (knob != null) {
            knob.render(knobX, knobY);
        } else {
            drawMissing(
                "asset:main-menu/music-volume-knob",
                knobX,
                knobY,
                knobWidth,
                knobHeight
            );
        }
    }

    private static UiRect unionRects(List<UiRect> rects) {
        if (rects == null || rects.isEmpty()) {
            return null;
        }

        int left = Integer.MAX_VALUE;
        int top = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MIN_VALUE;

        for (UiRect rect : rects) {
            left = Math.min(left, rect.x);
            top = Math.min(top, rect.y);
            right = Math.max(right, rect.x + rect.width);
            bottom = Math.max(bottom, rect.y + rect.height);
        }

        return new UiRect(
            left,
            top,
            Math.max(1, right - left),
            Math.max(1, bottom - top)
        );
    }

    private static int rectArea(UiRect rect) {
        return rect == null ? 0 : rect.width * rect.height;
    }

    private static int rectCenterX(UiRect rect) {
        return rect.x + rect.width / 2;
    }

    private static int rectCenterY(UiRect rect) {
        return rect.y + rect.height / 2;
    }

    private static boolean overlapsY(UiRect rect, int top, int bottom) {
        return rect != null
            && rect.y < bottom
            && rect.y + rect.height > top;
    }

    private static final class MainMenuLayout {
        private final List<MainMenuTextEntry> texts = new ArrayList<>();
        private final List<MainMenuImageEntry> images = new ArrayList<>();
        private final Set<Integer> frameComponentIds = new HashSet<>();
        private final Set<Integer> choiceComponentIds = new HashSet<>();
        private final Set<Integer> musicSliderComponentIds = new HashSet<>();

        private UiRect body;
        private UiRect scroll;
        private UiRect contentBounds;
        private UiRect header;
        private UiRect footer;
        private UiRect leftEdge;
        private UiRect rightEdge;
        private UiRect logo;
        private UiRect standardChoice;
        private UiRect highChoice;
        private Component standardChoiceComponent;
        private Component highChoiceComponent;
        private UiRect musicSlider;

        private int bodyComponentId = -1;
        private int logoComponentId = -1;
        private int centerX;
        private ModernMainMenuLayout modernLayout;

        private boolean hasText(String wanted) {
            for (MainMenuTextEntry entry : texts) {
                if (entry.text.equals(wanted)) {
                    return true;
                }
            }
            return false;
        }

        private MainMenuTextEntry findText(String wanted) {
            for (MainMenuTextEntry entry : texts) {
                if (entry.text.equals(wanted)) {
                    return entry;
                }
            }
            return null;
        }

        private void finish() {
            List<UiRect> managedTextRects = new ArrayList<>();
            MainMenuTextEntry graphics = findText("graphics options");

            for (MainMenuTextEntry entry : texts) {
                if (isManagedMainMenuText(entry.text)) {
                    managedTextRects.add(entry.rect);
                }
            }

            UiRect content = unionRects(managedTextRects);
            if (content == null) {
                content = new UiRect(
                    GameShell.canvasWidth / 2 - 90,
                    GameShell.canvasHeight / 2 - 120,
                    180,
                    240
                );
            }

            // From this point onward, all visible geometry comes from the
            // declarative Modern layout. The legacy content union above is
            // retained only to discover which cache Components own actions.
            modernLayout = ModernMainMenuLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );
            centerX = modernLayout.centerX;
            scroll = new UiRect(
                modernLayout.scroll.x,
                modernLayout.scroll.y,
                modernLayout.scroll.width,
                modernLayout.scroll.height
            );
            contentBounds = new UiRect(
                modernLayout.content.x,
                modernLayout.content.y,
                modernLayout.content.width,
                modernLayout.content.height
            );

            MainMenuImageEntry bestBody = null;
            int bestBodyArea = 0;
            for (MainMenuImageEntry image : images) {
                UiRect rect = image.rect;
                boolean containsCenter =
                    centerX >= rect.x - 8
                        && centerX <= rect.x + rect.width + 8;
                boolean coversTextBand =
                    rect.y <= content.y + 28
                        && rect.y + rect.height
                            >= content.y + content.height - 28;
                if (containsCenter
                    && coversTextBand
                    && rect.width >= 120
                    && rect.height >= 120
                    && rectArea(rect) > bestBodyArea) {
                    bestBody = image;
                    bestBodyArea = rectArea(rect);
                }
            }

            if (bestBody != null) {
                bodyComponentId = bestBody.componentId;
                frameComponentIds.add(bestBody.componentId);
                body = bestBody.rect;
            } else {
                int width = Math.max(190, content.width + 48);
                int height = Math.max(220, content.height + 38);
                body = new UiRect(
                    centerX - width / 2,
                    content.y - 18,
                    width,
                    height
                );
            }

            MainMenuImageEntry bestLogo = null;
            int bestLogoArea = 0;
            for (MainMenuImageEntry image : images) {
                UiRect rect = image.rect;
                if (rect == body) {
                    continue;
                }

                int dx = Math.abs(rectCenterX(rect) - centerX);
                if (dx <= Math.max(120, body.width)
                    && rect.y + rect.height < body.y - 25
                    && rect.width >= 180
                    && rect.height >= 50
                    && rectArea(rect) > bestLogoArea) {
                    bestLogo = image;
                    bestLogoArea = rectArea(rect);
                }
            }

            if (bestLogo != null) {
                logoComponentId = bestLogo.componentId;
            }

            logo = new UiRect(
                modernLayout.logo.x,
                modernLayout.logo.y,
                modernLayout.logo.width,
                modernLayout.logo.height
            );

            List<UiRect> headerParts = new ArrayList<>();
            List<UiRect> footerParts = new ArrayList<>();

            int bodyBottom = body.y + body.height;
            for (MainMenuImageEntry image : images) {
                if (image.componentId == bodyComponentId
                    || image.componentId == logoComponentId) {
                    continue;
                }

                UiRect rect = image.rect;
                int dx = Math.abs(rectCenterX(rect) - centerX);

                if (dx <= body.width
                    && rect.y < body.y + 18
                    && rect.y + rect.height > body.y - 55) {
                    frameComponentIds.add(image.componentId);
                    headerParts.add(rect);
                    continue;
                }

                if (dx <= body.width
                    && rect.y < bodyBottom + 55
                    && rect.y + rect.height > bodyBottom - 18) {
                    frameComponentIds.add(image.componentId);
                    footerParts.add(rect);
                    continue;
                }

                boolean nearLeft =
                    Math.abs(
                        rectCenterX(rect) - body.x
                    ) <= 24;
                boolean nearRight =
                    Math.abs(
                        rectCenterX(rect) - (body.x + body.width)
                    ) <= 24;

                if ((nearLeft || nearRight)
                    && rect.width <= 42
                    && rect.height >= body.height / 3
                    && overlapsY(rect, body.y, bodyBottom)) {
                    frameComponentIds.add(image.componentId);
                }
            }

            UiRect headerUnion = unionRects(headerParts);
            UiRect footerUnion = unionRects(footerParts);

            header =
                headerUnion == null
                    ? new UiRect(
                        body.x - 34,
                        body.y - 30,
                        body.width + 68,
                        30
                    )
                    : headerUnion;

            footer =
                footerUnion == null
                    ? new UiRect(
                        body.x - 34,
                        bodyBottom,
                        body.width + 68,
                        30
                    )
                    : footerUnion;

            leftEdge = new UiRect(
                body.x - 10,
                body.y,
                10,
                body.height
            );
            rightEdge = new UiRect(
                body.x + body.width,
                body.y,
                10,
                body.height
            );

            MainMenuTextEntry music = findText("music volume");
            if (music != null) {
                int usableWidth =
                    contentBounds == null
                        ? scroll.width
                        : contentBounds.width;
                int sliderWidth = Math.min(
                    190,
                    Math.max(160, usableWidth * 45 / 100)
                );
                int sliderHeight = 24;
                musicSlider = new UiRect(
                    centerX - sliderWidth / 2,
                    music.rect.y + music.rect.height + 1,
                    sliderWidth,
                    sliderHeight
                );

                UiRect capture = new UiRect(
                    musicSlider.x - 12,
                    musicSlider.y - 5,
                    musicSlider.width + 24,
                    musicSlider.height + 10
                );

                for (MainMenuImageEntry image : images) {
                    if (image.componentId == bodyComponentId
                        || image.componentId == logoComponentId
                        || frameComponentIds.contains(image.componentId)
                        || choiceComponentIds.contains(image.componentId)) {
                        continue;
                    }

                    UiRect rect = image.rect;
                    if (rect.width <= 64
                        && rect.height <= 48
                        && intersects(
                            capture,
                            rect.x,
                            rect.y,
                            rect.width,
                            rect.height
                        )) {
                        musicSliderComponentIds.add(image.componentId);
                    }
                }
            }

            MainMenuTextEntry standard = findText("standard detail");
            MainMenuTextEntry high = findText("high detail");
            if (standard != null || high != null) {
                int choiceTop =
                    Math.min(
                        standard == null
                            ? Integer.MAX_VALUE
                            : standard.rect.y - 48,
                        high == null
                            ? Integer.MAX_VALUE
                            : high.rect.y - 48
                    );
                int choiceBottom =
                    Math.max(
                        standard == null
                            ? Integer.MIN_VALUE
                            : standard.rect.y + standard.rect.height + 12,
                        high == null
                            ? Integer.MIN_VALUE
                            : high.rect.y + high.rect.height + 12
                    );

                MainMenuImageEntry leftChoice = null;
                MainMenuImageEntry rightChoice = null;
                int leftArea = 0;
                int rightArea = 0;

                for (MainMenuImageEntry image : images) {
                    if (image.componentId == bodyComponentId
                        || image.componentId == logoComponentId
                        || frameComponentIds.contains(image.componentId)) {
                        continue;
                    }

                    UiRect rect = image.rect;
                    if (!overlapsY(rect, choiceTop, choiceBottom)
                        || rect.width < 32
                        || rect.width > 120
                        || rect.height < 18
                        || rect.height > 70) {
                        continue;
                    }

                    int area = rectArea(rect);
                    if (rectCenterX(rect) < centerX) {
                        if (area > leftArea) {
                            leftChoice = image;
                            leftArea = area;
                        }
                    } else if (area > rightArea) {
                        rightChoice = image;
                        rightArea = area;
                    }
                }

                UiRect leftChoiceRect =
                    leftChoice == null ? null : leftChoice.rect;
                UiRect rightChoiceRect =
                    rightChoice == null ? null : rightChoice.rect;

                standardChoice = leftChoiceRect;
                highChoice = rightChoiceRect;
                standardChoiceComponent =
                    leftChoice == null ? null : leftChoice.component;
                highChoiceComponent =
                    rightChoice == null ? null : rightChoice.component;

                for (MainMenuImageEntry image : images) {
                    if (intersects(
                            leftChoiceRect,
                            image.rect.x,
                            image.rect.y,
                            image.rect.width,
                            image.rect.height
                        )
                        || intersects(
                            rightChoiceRect,
                            image.rect.x,
                            image.rect.y,
                            image.rect.width,
                            image.rect.height
                        )) {
                        choiceComponentIds.add(image.componentId);
                    }
                }
            }

            applyModernVerticalLayout();
        }

        private int textTargetX(
            String normalized,
            int componentWidth
        ) {
            if (contentBounds == null || normalized == null) {
                return Integer.MIN_VALUE;
            }

            int width = Math.max(1, componentWidth);
            if (normalized.equals("standard detail")
                && standardChoice != null) {
                return standardChoice.x
                    + (standardChoice.width - width) / 2;
            }
            if (normalized.equals("high detail")
                && highChoice != null) {
                return highChoice.x
                    + (highChoice.width - width) / 2;
            }

            // All other Modern main-menu text is centered from the Modern
            // layout rather than inheriting cache-era X coordinates.
            return centerX - width / 2;
        }

        private int textTargetY(String normalized) {
            if (modernLayout == null || normalized == null) {
                return Integer.MIN_VALUE;
            }
            return modernLayout.textY(normalized);
        }

        private void applyModernVerticalLayout() {
            if (contentBounds == null) {
                return;
            }

            if (modernLayout != null) {
                if (standardChoice != null) {
                    standardChoice = new UiRect(
                        modernLayout.standardChoice.x,
                        modernLayout.standardChoice.y,
                        modernLayout.standardChoice.width,
                        modernLayout.standardChoice.height
                    );
                }
                if (highChoice != null) {
                    highChoice = new UiRect(
                        modernLayout.highChoice.x,
                        modernLayout.highChoice.y,
                        modernLayout.highChoice.width,
                        modernLayout.highChoice.height
                    );
                }
            }

            if (musicSlider != null) {
                int targetTop =
                    modernLayout == null
                        ? contentBounds.y + 322
                        : modernLayout.musicSliderTop;
                musicSlider = new UiRect(
                    musicSlider.x,
                    targetTop,
                    musicSlider.width,
                    musicSlider.height
                );
            }
        }
    }

    private static final class MainMenuTextEntry {
        private final Component component;
        private final int componentId;
        private final String text;
        private final UiRect rect;

        private MainMenuTextEntry(
            Component component,
            int componentId,
            String text,
            UiRect rect
        ) {
            this.component = component;
            this.componentId = componentId;
            this.text = text;
            this.rect = rect;
        }
    }

    private static final class MainMenuImageEntry {
        private final Component component;
        private final int componentId;
        private final UiRect rect;

        private MainMenuImageEntry(
            Component component,
            int componentId,
            UiRect rect
        ) {
            this.component = component;
            this.componentId = componentId;
            this.rect = rect;
        }
    }

    private static void collectGraphicsOptionsReplacementRegions(
        Component[] components,
        int layer,
        int parentX,
        int parentY
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;

            if (component.type == 4) {
                JagString display = component.text;
                if (Cs1ScriptRunner.isTrue(component)
                    && component.activeText != null
                    && component.activeText.length() > 0) {
                    display = component.activeText;
                }
                if (!component.if3 && display != null) {
                    display = Cs1ScriptRunner.interpolate(component, display);
                }

                String text = display == null ? "" : display.toString();
                if (isGraphicsOptionsDropdownValue(component, text, y)) {
                    int controlHeight = ModernUiMetrics.CONTROL_HEIGHT;
                    int controlY = y + (component.height - controlHeight) / 2;
                    int controlWidth = graphicsOptionsControlWidth(component);
                    int controlX =
                        x + (component.width - controlWidth) / 2;
                    graphicsOptionsDropdownRects.add(
                        new UiRect(
                            controlX,
                            controlY,
                            controlWidth,
                            controlHeight
                        )
                    );
                } else if (normalizeGraphicsOptionsText(text).equals("brightness")) {
                    graphicsOptionsBrightnessRect = new UiRect(
                        x - 18,
                        y + 14,
                        Math.max(140, component.width + 36),
                        ModernUiMetrics.SLIDER_HEIGHT
                    );
                }
            }

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collectGraphicsOptionsReplacementRegions(
                    components,
                    component.id,
                    childX,
                    childY
                );
                if (component.createdComponents != null) {
                    collectGraphicsOptionsReplacementRegions(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY
                    );
                }
            }
        }
    }

    private static boolean intersects(UiRect rect, int x, int y, int width, int height) {
        return rect != null
            && x < rect.x + rect.width
            && x + width > rect.x
            && y < rect.y + rect.height
            && y + height > rect.y;
    }

    private static boolean insideCompletedDropdown(int x, int y, int width, int height) {
        for (UiRect rect : graphicsOptionsDropdownRects) {
            if (intersects(rect, x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    private static void renderGraphicsOptionsImageFallback(
        Component component,
        int x,
        int y
    ) {
        if (component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_HIT
            || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_PIECE) {
            // These are Killer Edition synthetic native pieces. The Modern
            // selector overlay is their complete replacement.
            return;
        }

        String assetKey = "graphics-options/" + componentAssetKey(component);
        int width = Math.max(1, component.width);
        int height = Math.max(1, component.height);

        // A completed Modern dropdown owns the legacy sprite fragments that
        // overlap its exact semantic control rectangle.
        if (insideCompletedDropdown(x, y, width, height)) {
            return;
        }

        // Brightness has a complete Modern slider replacement. Only sprite
        // fragments overlapping that explicit object rectangle are suppressed.
        if (intersects(graphicsOptionsBrightnessRect, x, y, width, height)) {
            if (!graphicsOptionsBrightnessRendered) {
                drawModernBrightness(
                    graphicsOptionsBrightnessRect.x,
                    graphicsOptionsBrightnessRect.y,
                    graphicsOptionsBrightnessRect.width,
                    graphicsOptionsBrightnessRect.height
                );
                graphicsOptionsBrightnessRendered = true;
            }
            return;
        }

        // Graphics Options now has complete Modern chrome for its panel,
        // display-mode controls, dropdowns, brightness slider and navigation.
        // Remaining cache sprites are decorative fragments of the 2009 skin,
        // so suppress them instead of surfacing missing-asset diagnostics.
        ModernUiImage image = ModernUiAssetResolver.get(
            assetKey,
            width,
            height
        );
        if (image != null) {
            image.render(x, y);
        }
    }

    private static GraphicsOptionsAnchor findGraphicsOptionsAnchor(
        Component[] components,
        int layer,
        int parentX,
        int parentY
    ) {
        if (components == null) {
            return null;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;

            if (component.type == 4
                && component.text != null
                && component.text.length() > 0
                && normalizeGraphicsOptionsText(
                    component.text.toString()
                ).equals("graphics options")) {
                return new GraphicsOptionsAnchor(
                    x + component.width / 2,
                    y
                );
            }

            if (component.type == 0) {
                GraphicsOptionsAnchor child = findGraphicsOptionsAnchor(
                    components,
                    component.id,
                    x - component.scrollX,
                    y - component.scrollY
                );
                if (child != null) {
                    return child;
                }

                if (component.createdComponents != null) {
                    child = findGraphicsOptionsAnchor(
                        component.createdComponents,
                        component.id,
                        x - component.scrollX,
                        y - component.scrollY
                    );
                    if (child != null) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    private static void renderPermanentGraphicsOptionsUi(
        Component[] components,
        int parentX,
        int parentY
    ) {
        // Full Modern-owned rendering. Components supply values, popup state
        // and CS2 behavior only; none of their legacy visuals are painted.
        setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
        ModernGraphicsOptionsUi.render(components);
    }

    private static void renderGraphicsOptionsForegroundComponents(
        Component[] components,
        int layer,
        int parentX,
        int parentY
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;

                renderGraphicsOptionsForegroundComponents(
                    components,
                    component.id,
                    childX,
                    childY
                );

                if (component.createdComponents != null) {
                    renderGraphicsOptionsForegroundComponents(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY
                    );
                }
                continue;
            }

            // The permanent pass intentionally contains only Modern UI visual
            // primitives. Viewports, plugins and other client components stay
            // below this layer with the animated login scene.
            if (component.type == 4) {
                renderText(component, x, y);
            }
        }
    }

    private static void renderGraphicsOptionsBackdrop(
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (graphicsOptionsTitleCenterX == 0) {
            return;
        }

        setClip(clipLeft, clipTop, clipRight, clipBottom);

        int width = ModernUiMetrics.GRAPHICS_PANEL_WIDTH;
        int height = ModernUiMetrics.GRAPHICS_PANEL_HEIGHT;
        int x = ModernUiMetrics.centeredX(
            graphicsOptionsTitleCenterX,
            width
        );
        int y =
            graphicsOptionsTitleY
                - ModernUiMetrics.GRAPHICS_PANEL_Y_OFFSET;

        ModernUiImage panel = ModernUiAssetResolver.get(
            "graphics-options/panel",
            width,
            height
        );
        if (panel != null) {
            panel.render(x, y);
        } else {
            drawMissing("asset:graphics-options/panel", x, y, width, height);
        }

        ModernUiImage divider = ModernUiAssetResolver.get(
            "graphics-options/divider",
            width - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );
        if (divider != null) {
            divider.render(
                x + ModernUiMetrics.GRAPHICS_PANEL_INSET,
                graphicsOptionsTitleY
                    + ModernUiMetrics.GRAPHICS_TOP_DIVIDER_Y_OFFSET
            );
            divider.render(
                x + ModernUiMetrics.GRAPHICS_PANEL_INSET,
                graphicsOptionsTitleY
                    + ModernUiMetrics.GRAPHICS_BOTTOM_DIVIDER_Y_OFFSET
            );
        } else {
            drawMissing(
                "asset:graphics-options/divider",
                x + ModernUiMetrics.GRAPHICS_PANEL_INSET,
                graphicsOptionsTitleY + 114,
                width - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
                4
            );
            drawMissing(
                "asset:graphics-options/divider",
                x + ModernUiMetrics.GRAPHICS_PANEL_INSET,
                graphicsOptionsTitleY + 324,
                width - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
                4
            );
        }

        // The legacy SD/HD lettering is part of cache sprites. Modern mode
        // draws its own vector-backed display-mode buttons and TrueType labels.
        int[] centers = {
            graphicsOptionsTitleCenterX - 225,
            graphicsOptionsTitleCenterX - 75,
            graphicsOptionsTitleCenterX + 75,
            graphicsOptionsTitleCenterX + 225
        };
        String[] labels = {"SD", "HD", "HD", "HD"};
        int buttonY =
            graphicsOptionsTitleY
                + ModernUiMetrics.DISPLAY_BUTTON_Y_OFFSET;

        int activeDisplayMode = DisplayMode.getWindowMode();
        for (int i = 0; i < centers.length; i++) {
            int buttonX = ModernUiMetrics.centeredX(
                centers[i],
                ModernUiMetrics.DISPLAY_BUTTON_WIDTH
            );
            boolean active = activeDisplayMode == i;
            drawModernControlBox(
                buttonX,
                buttonY,
                ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
                ModernUiMetrics.DISPLAY_BUTTON_HEIGHT,
                false,
                active
            );
            ModernTrueTypeFont.drawInBox(
                ModernUiFontRegistry.BOLD_12,
                labels[i],
                buttonX,
                buttonY,
                ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
                ModernUiMetrics.DISPLAY_BUTTON_HEIGHT,
                active
                    ? ModernUiMetrics.TEXT_ACCENT
                    : ModernUiMetrics.TEXT_PRIMARY,
                1,
                1,
                ModernUiMetrics.FONT_BUTTON,
                true
            );
        }

        // Main Menu is text-driven in the cache. The Modern renderer owns
        // this navigation control's complete visual geometry.
        drawModernControlBox(
            ModernUiMetrics.centeredX(
                graphicsOptionsTitleCenterX,
                ModernUiMetrics.NAV_BUTTON_WIDTH
            ),
            graphicsOptionsTitleY + ModernUiMetrics.NAV_BUTTON_Y_OFFSET,
            ModernUiMetrics.NAV_BUTTON_WIDTH,
            ModernUiMetrics.NAV_BUTTON_HEIGHT,
            false,
            false
        );
    }

    private static void drawModernControlBox(
        int x,
        int y,
        int width,
        int height,
        boolean dropdown,
        boolean active
    ) {
        String asset;
        if (dropdown) {
            asset = "controls/dropdown";
        } else if (active) {
            asset = "controls/button-active";
        } else {
            asset = "controls/button";
        }

        ModernUiImage chrome = ModernUiAssetResolver.get(
            asset,
            width,
            height
        );
        if (chrome != null) {
            chrome.render(x, y);
        } else {
            drawMissing("asset:" + asset, x, y, width, height);
        }

        if (dropdown && width >= 22) {
            ModernUiImage arrow = ModernUiAssetResolver.get(
                "icons/dropdown",
                ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
                ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
            );
            int arrowX =
                x + width - ModernUiMetrics.DROPDOWN_ARROW_WIDTH - 6;
            int arrowY =
                y + Math.max(
                    4,
                    (height - ModernUiMetrics.DROPDOWN_ARROW_HEIGHT) / 2
                );
            if (arrow != null) {
                arrow.render(arrowX, arrowY);
            } else {
                drawMissing(
                    "asset:icons/dropdown",
                    arrowX,
                    arrowY,
                    ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
                    ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
                );
            }
        }
    }

    private static void drawModernBrightness(
        int x,
        int y,
        int width,
        int height
    ) {
        int selected = Preferences.brightness;
        if (selected < 1) {
            selected = 1;
        } else if (selected > 4) {
            selected = 4;
        }

        ModernUiImage track = ModernUiAssetResolver.get(
            "controls/slider-track",
            width,
            Math.max(8, height)
        );
        if (track != null) {
            track.render(x, y);
        } else {
            drawMissing("asset:controls/slider-track", x, y, width, Math.max(8, height));
        }

        int left = x + 10;
        int right = x + width - 10;
        int centerY = y + height / 2;
        int knobX =
            left + (right - left) * (selected - 1) / 3
                - ModernUiMetrics.SLIDER_KNOB_WIDTH / 2;

        ModernUiImage knob = ModernUiAssetResolver.get(
            "controls/slider-knob",
            ModernUiMetrics.SLIDER_KNOB_WIDTH,
            ModernUiMetrics.SLIDER_KNOB_HEIGHT
        );
        if (knob != null) {
            knob.render(
                knobX,
                centerY - ModernUiMetrics.SLIDER_KNOB_HEIGHT / 2
            );
        } else {
            drawMissing(
                "asset:controls/slider-knob",
                knobX,
                centerY - ModernUiMetrics.SLIDER_KNOB_HEIGHT / 2,
                ModernUiMetrics.SLIDER_KNOB_WIDTH,
                ModernUiMetrics.SLIDER_KNOB_HEIGHT
            );
        }
    }

    private static void fillAlpha(
        int x,
        int y,
        int width,
        int height,
        int color,
        int alpha
    ) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, color, alpha);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, color, alpha);
        }
    }

    private static void hline(int x, int y, int width, int color) {
        if (width <= 0) {
            return;
        }
        if (GlRenderer.enabled) {
            GlRaster.drawHorizontalLine(x, y, width, color);
        } else {
            SoftwareRaster.drawHorizontalLine(x, y, width, color);
        }
    }

    private static final class UiRect {
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private UiRect(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    private static final class GraphicsOptionsAnchor {
        private final int centerX;
        private final int y;

        private GraphicsOptionsAnchor(int centerX, int y) {
            this.centerX = centerX;
            this.y = y;
        }
    }

    private static void drawMissingBackdrop(
        String key,
        int x,
        int y,
        int width,
        int height
    ) {
        drawMissing(key, x, y, width, height);
    }

    private static String componentAssetKey(Component component) {
        int interfaceId = component.id >>> 16;
        int childId = component.id & 0xFFFF;
        return "components/" + interfaceId + "/" + childId;
    }

    public static void clearCaches() {
        loggedMissing.clear();
        ModernUiInputRouter.clear();
        ModernGameUiDiagnostics.clear();
        ModernUiAssetResolver.clear();
        ModernTrueTypeFont.clear();
    }
}
