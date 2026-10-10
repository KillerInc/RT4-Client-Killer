package rt4;

import plugin.PluginRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Independent Modern UI renderer.
 *
 * Existing Component objects are currently used only as UI state/layout input
 * while the migration is in progress. Visuals do not call Component.getSprite
 * or Component.getFont. Missing visual implementations use high-visibility pink diagnostics.
 */
public final class ModernUiRenderer {
    private static final Set<String> loggedMissing = new HashSet<>();
    private static int graphicsOptionsDepth;
    private static int graphicsOptionsTitleCenterX;
    private static int graphicsOptionsTitleY;
    private static boolean graphicsOptionsBrightnessRendered;
    private static final List<UiRect> graphicsOptionsDropdownRects = new ArrayList<>();
    private static UiRect graphicsOptionsBrightnessRect;

    private static int mainMenuDepth;
    private static MainMenuLayout mainMenuLayout;
    private static int suppressDiagnosticsDepth;
    private static boolean loginUiActivated;
    private static UiRect lastMainMenuContentBounds;

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
        boolean graphicsOptions =
            GraphicsOptionsUiInjector.isGraphicsOptionsActive(loadedComponents);

        MainMenuLayout detectedMainMenu =
            interfaceId == LoginManager.loginScreenId
                ? analyzeMainMenu(loadedComponents, parentX, parentY)
                : null;
        boolean mainMenu = detectedMainMenu != null;

        if (mainMenu || graphicsOptions) {
            loginUiActivated = true;
        }

        boolean suppressStartupDiagnostics =
            interfaceId == LoginManager.loginScreenId
                && !loginUiActivated
                && !mainMenu
                && !graphicsOptions;

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
            GraphicsOptionsAnchor anchor = findGraphicsOptionsAnchor(
                loadedComponents,
                -1,
                parentX,
                parentY
            );
            if (anchor != null) {
                graphicsOptionsTitleCenterX = anchor.centerX;
                graphicsOptionsTitleY = anchor.y;
            }
            graphicsOptionsDepth++;
            graphicsOptionsBrightnessRendered = false;
            graphicsOptionsDropdownRects.clear();
            graphicsOptionsBrightnessRect = null;
            collectGraphicsOptionsReplacementRegions(
                loadedComponents,
                -1,
                parentX,
                parentY
            );
            renderGraphicsOptionsBackdrop(
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
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

        if (mainMenu) {
            // Permanent UI pass: everything in the Modern main menu is
            // composited after the complete legacy login component tree.
            // This keeps the scene fade/transition confined to the background.
            renderPermanentMainMenuUi();
            setClip(clipLeft, clipTop, clipRight, clipBottom);
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
            if (mainMenuDepth > 0) {
                y = adjustMainMenuComponentY(component, y);
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

            int effectiveClipLeft = clipLeft;
            int effectiveClipTop = clipTop;
            int effectiveClipRight = clipRight;
            int effectiveClipBottom = clipBottom;

            if (mainMenuDepth > 0
                && mainMenuLayout != null
                && usesMainMenuContentBounds(component)) {
                UiRect bounds = mainMenuLayout.contentBounds;
                if (bounds != null) {
                    effectiveClipLeft = Math.max(0, bounds.x);
                    effectiveClipTop = Math.max(0, bounds.y);
                    effectiveClipRight = Math.min(
                        GameShell.canvasWidth,
                        bounds.x + bounds.width
                    );
                    effectiveClipBottom = Math.min(
                        GameShell.canvasHeight,
                        bounds.y + bounds.height
                    );
                }
            }

            int left = Math.max(effectiveClipLeft, x);
            int top = Math.max(effectiveClipTop, y);
            int right = Math.min(
                effectiveClipRight,
                x + Math.max(1, component.width)
            );
            int bottom = Math.min(
                effectiveClipBottom,
                y + Math.max(1, component.height)
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

                setClip(clipLeft, clipTop, clipRight, clipBottom);
                continue;
            }

            if (mainMenuDepth > 0
                && mainMenuLayout != null
                && usesMainMenuContentBounds(component)
                && mainMenuLayout.contentBounds != null) {
                UiRect bounds = mainMenuLayout.contentBounds;
                setClip(
                    Math.max(0, bounds.x),
                    Math.max(0, bounds.y),
                    Math.min(GameShell.canvasWidth, bounds.x + bounds.width),
                    Math.min(GameShell.canvasHeight, bounds.y + bounds.height)
                );
            }

            renderComponentVisual(component, x, y);

            if (mainMenuDepth > 0 && usesMainMenuContentBounds(component)) {
                setClip(clipLeft, clipTop, clipRight, clipBottom);
            }

            if (rectangle >= 0 && rectangle < InterfaceList.rectangleRedraw.length) {
                InterfaceList.rectangleRedraw[rectangle] = true;
            }
        }
    }

    public static int adjustMainMenuComponentY(
        Component component,
        int y
    ) {
        if (!usesMainMenuContentBounds(component) || component.type != 4) {
            return y;
        }
        return y + mainMenuTextYOffset(component);
    }

    public static boolean usesMainMenuContentBounds(Component component) {
        if (component == null
            || lastMainMenuContentBounds == null
            || !ModernUiManager.isEnabled()) {
            return false;
        }

        int interfaceId = component.id >>> 16;
        if (interfaceId != LoginManager.loginScreenId) {
            return false;
        }

        if (component.type != 4 || component.text == null) {
            return false;
        }

        String normalized = normalizeGraphicsOptionsText(
            component.text.toString()
        );
        return isManagedMainMenuText(normalized)
            || normalized.equals("music volume");
    }

    public static int getMainMenuContentLeft(int fallback) {
        return lastMainMenuContentBounds == null
            ? fallback
            : Math.max(0, lastMainMenuContentBounds.x);
    }

    public static int getMainMenuContentTop(int fallback) {
        return lastMainMenuContentBounds == null
            ? fallback
            : Math.max(0, lastMainMenuContentBounds.y);
    }

    public static int getMainMenuContentRight(int fallback) {
        return lastMainMenuContentBounds == null
            ? fallback
            : Math.min(
                GameShell.canvasWidth,
                lastMainMenuContentBounds.x
                    + lastMainMenuContentBounds.width
            );
    }

    public static int getMainMenuContentBottom(int fallback) {
        return lastMainMenuContentBounds == null
            ? fallback
            : Math.min(
                GameShell.canvasHeight,
                lastMainMenuContentBounds.y
                    + lastMainMenuContentBounds.height
            );
    }

    private static int mainMenuTextYOffset(Component component) {
        if (component == null || component.text == null) {
            return 0;
        }

        String normalized = normalizeGraphicsOptionsText(
            component.text.toString()
        );

        // These offsets are part of the Modern main-menu layout, not cosmetic
        // render-only nudges. InterfaceList uses the same transform for input.
        if (normalized.equals("graphics options")) {
            return -12;
        }
        if (normalized.equals("audio options")) {
            return -6;
        }
        if (normalized.equals("quit")) {
            return 14;
        }
        return 0;
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

        if (component.clientCode == 1338) {
            drawMissing("minimap", x, y, component.width, component.height);
            return true;
        }
        if (component.clientCode == 1339) {
            drawMissing("compass", x, y, component.width, component.height);
            return true;
        }
        if (component.clientCode == 1400) {
            drawMissing("world-map", x, y, component.width, component.height);
            return true;
        }
        if (component.clientCode == 1401) {
            drawMissing("world-map-overview", x, y, component.width, component.height);
            return true;
        }
        if (component.clientCode == 1402) {
            drawMissing("login-flames", x, y, component.width, component.height);
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
                drawMissing("model:" + component.id, x, y, component.width, component.height);
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
            drawMissingLegacyGlyphs(component, text, x, y, 12.0F);
            return;
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
                    component.halign,
                    1,
                    ModernUiMetrics.FONT_CONTROL,
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
                    controlX + 4,
                    controlY,
                    Math.max(1, controlWidth - 22),
                    controlHeight,
                    ModernUiMetrics.TEXT_PRIMARY,
                    component.halign,
                    1,
                    ModernUiMetrics.FONT_CONTROL,
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
            12.0F,
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
            return 13.0F;
        }
        return 12.0F;
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

                drawOutline(slotX, slotY, 32, 32, 0x5B5140);
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
                        if (fontAsset == null) {
                            drawMissingLegacyGlyphs(
                                component,
                                object.name.toString(),
                                tx,
                                ty,
                                11.0F
                            );
                        } else {
                            ModernTrueTypeFont.draw(
                                fontAsset,
                                object.name.toString(),
                                tx,
                                ty + 11,
                                safeColor(component.color),
                                11.0F,
                                component.shadowed
                            );
                        }
                    }
                }
                index++;
            }
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
                33.0F,
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
            int y = adjustMainMenuComponentY(
                entry.component,
                entry.rect.y
            );

            if (normalized.equals("music volume")) {
                renderMainMenuMusicLabel(
                    entry.component,
                    text,
                    entry.rect.x,
                    y,
                    entry.component.color
                );
            } else if (isManagedMainMenuText(normalized)) {
                renderMainMenuText(
                    entry.component,
                    text,
                    entry.rect.x,
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
            color = 0x5A351C;
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
            color = 0xF1D68A;
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
                Mouse.lastMouseX >= x
                    && Mouse.lastMouseX < x + component.width
                    && Mouse.lastMouseY >= y
                    && Mouse.lastMouseY < y + component.height;

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
        int trackHeight = Math.min(18, Math.max(12, rect.height - 6));
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

            centerX =
                graphics == null
                    ? rectCenterX(content)
                    : rectCenterX(graphics.rect);

            // Give the vector parchment a little more breathing room around
            // the existing login controls without moving the control layout.
            // Keep the parchment and its derived inner content bounds in the
            // same scale system. This is 95% of the previous main-menu size.
            int scrollWidth = Math.max(552, content.width + 307);
            int scrollHeight = Math.max(582, content.height + 230);
            scroll = new UiRect(
                centerX - scrollWidth / 2,
                content.y - 161,
                scrollWidth,
                scrollHeight
            );

            // The usable menu area is derived from the parchment itself.
            // Future scaling/resizing therefore changes the outer artwork and
            // the interaction/render bounds together instead of preserving the
            // obsolete 2009 cache container dimensions.
            int contentInsetX = Math.max(42, scroll.width * 12 / 100);
            int contentInsetTop = Math.max(58, scroll.height * 12 / 100);
            int contentInsetBottom = Math.max(48, scroll.height * 10 / 100);
            contentBounds = new UiRect(
                scroll.x + contentInsetX,
                scroll.y + contentInsetTop,
                Math.max(1, scroll.width - contentInsetX * 2),
                Math.max(
                    1,
                    scroll.height - contentInsetTop - contentInsetBottom
                )
            );
            lastMainMenuContentBounds = contentBounds;

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

            // Keep the 2008 vector logo prominent in resizable mode.
            // The upper cap prevents it from overwhelming smaller layouts.
            int logoWidth = Math.min(
                620,
                Math.max(400, GameShell.canvasWidth * 54 / 100)
            );
            logoWidth = Math.min(
                logoWidth,
                Math.max(240, GameShell.canvasWidth - 40)
            );
            int logoHeight = Math.max(
                90,
                logoWidth * 500 / 1445
            );
            int logoBottom = scroll.y - 24;
            logo = new UiRect(
                centerX - logoWidth / 2,
                Math.max(8, logoBottom - logoHeight),
                logoWidth,
                logoHeight
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
                        y + 12,
                        Math.max(90, component.width + 36),
                        24
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
        // As with the main menu, the entire Modern Graphics Options surface is
        // a foreground layer. Any login-scene fade/transition has already been
        // drawn by the legacy component pass before we reach this point.
        graphicsOptionsBrightnessRendered = false;
        renderGraphicsOptionsBackdrop(
            0,
            0,
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );

        if (graphicsOptionsBrightnessRect != null) {
            drawModernBrightness(
                graphicsOptionsBrightnessRect.x,
                graphicsOptionsBrightnessRect.y,
                graphicsOptionsBrightnessRect.width,
                graphicsOptionsBrightnessRect.height
            );
            graphicsOptionsBrightnessRendered = true;
        }

        setClip(0, 0, GameShell.canvasWidth, GameShell.canvasHeight);
        renderGraphicsOptionsForegroundComponents(
            components,
            -1,
            parentX,
            parentY
        );
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
        int y = graphicsOptionsTitleY - 34;

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
                graphicsOptionsTitleY + 114
            );
            divider.render(
                x + ModernUiMetrics.GRAPHICS_PANEL_INSET,
                graphicsOptionsTitleY + 324
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
            ModernTrueTypeFont.drawCentered(
                ModernUiFontRegistry.BOLD_12,
                labels[i],
                centers[i],
                buttonY + 19,
                active
                    ? ModernUiMetrics.TEXT_ACCENT
                    : ModernUiMetrics.TEXT_PRIMARY,
                ModernUiMetrics.FONT_BUTTON,
                true
            );
        }

        // Main Menu is text-driven in the cache, so give it Modern UI chrome
        // without changing its click/script behavior.
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
        lastMainMenuContentBounds = null;
        ModernUiAssetResolver.clear();
        ModernTrueTypeFont.clear();
    }
}
