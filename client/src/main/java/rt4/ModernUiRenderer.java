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
            drawMissing("interface:" + interfaceId, parentX + 8, parentY + 8, 220, 36);
            return;
        }

        Component[] loadedComponents = InterfaceList.components[interfaceId];
        boolean graphicsOptions =
            GraphicsOptionsUiInjector.isGraphicsOptionsActive(loadedComponents);
        int oldTitleCenterX = graphicsOptionsTitleCenterX;
        int oldTitleY = graphicsOptionsTitleY;
        boolean oldBrightnessRendered = graphicsOptionsBrightnessRendered;
        List<UiRect> oldDropdownRects = new ArrayList<>(graphicsOptionsDropdownRects);
        UiRect oldBrightnessRect = graphicsOptionsBrightnessRect;

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

        if (graphicsOptions) {
            graphicsOptionsDepth--;
            graphicsOptionsTitleCenterX = oldTitleCenterX;
            graphicsOptionsTitleY = oldTitleY;
            graphicsOptionsBrightnessRendered = oldBrightnessRendered;
            graphicsOptionsDropdownRects.clear();
            graphicsOptionsDropdownRects.addAll(oldDropdownRects);
            graphicsOptionsBrightnessRect = oldBrightnessRect;
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

            if (component.clientCode > 0) {
                Cs1ScriptRunner.applyClientCode(component);
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
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
            ModernUiSettingsOverlay.observeComponent(component, x, y);

            int left = Math.max(clipLeft, x);
            int top = Math.max(clipTop, y);
            int right = Math.min(clipRight, x + Math.max(1, component.width));
            int bottom = Math.min(clipBottom, y + Math.max(1, component.height));
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

        boolean syntheticKillerText =
            component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT
                || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT
                || component.clientCode == GraphicsOptionsUiInjector.CLIENT_CODE_LABEL_TEXT;

        String fontAsset =
            component.font == -1
                ? ModernUiFontRegistry.DEFAULT
                : ModernUiFontRegistry.resolveAsset(component.font);

        if (!syntheticKillerText
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
                ModernUiImage button = ModernUiAssetResolver.get(
                    "controls/button",
                    Math.max(1, component.width),
                    Math.max(20, component.height)
                );
                int buttonY = y - Math.max(0, (20 - component.height) / 2);
                if (button != null) {
                    button.render(x, buttonY);
                } else {
                    drawMissing(
                        "asset:controls/button",
                        x,
                        buttonY,
                        Math.max(1, component.width),
                        Math.max(20, component.height)
                    );
                }

                ModernTrueTypeFont.drawInBox(
                    fontAsset,
                    text,
                    x,
                    buttonY,
                    component.width,
                    Math.max(20, component.height),
                    0xE8DDC4,
                    component.halign,
                    1,
                    11.0F,
                    false
                );
                return;
            }

            if (isGraphicsOptionsDropdownValue(component, text, y)) {
                int controlHeight = Math.max(20, component.height + 6);
                int controlY = y - Math.max(2, (controlHeight - component.height) / 2);

                drawModernControlBox(
                    x,
                    controlY,
                    Math.max(1, component.width),
                    controlHeight,
                    true,
                    false
                );

                ModernTrueTypeFont.drawInBox(
                    fontAsset,
                    text,
                    x + 4,
                    controlY,
                    Math.max(1, component.width - 22),
                    controlHeight,
                    0xE8DDC4,
                    component.halign,
                    1,
                    11.0F,
                    false
                );
                return;
            }

            ModernTrueTypeFont.drawInBox(
                text,
                x,
                y,
                component.width,
                component.height,
                0xE8DDC4,
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
        if (width < 60 || width > 190 || height > 32) {
            return false;
        }

        int relativeY = y - graphicsOptionsTitleY;
        if (relativeY < 65 || relativeY > 350) {
            return false;
        }

        return !isGraphicsOptionsLabel(text);
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
            Math.round(size * ModernUiPreferences.getTextScale())
        );
        int advance = Math.max(6, Math.round(glyphHeight * 0.62F));
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
            int lineWidth = Math.max(advance, line.length() * advance);

            int drawX = x;
            if (component.halign == 1) {
                drawX = x + (component.width - lineWidth) / 2;
            } else if (component.halign == 2) {
                drawX = x + component.width - lineWidth;
            }

            int drawY = top + lineIndex * lineHeight;
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    drawMissingGlyphCell(
                        drawX + i * advance,
                        drawY,
                        Math.max(5, advance - 1),
                        glyphHeight
                    );
                }
            }
        }
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
            return 16.0F;
        }
        if (normalized.equals("display modes")
            || normalized.equals("advanced options")) {
            return 13.0F;
        }
        if (normalized.equals("standard detail")
            || normalized.startsWith("high detail")
            || normalized.equals("(small)")
            || normalized.equals("(fullscreen)")) {
            return 11.0F;
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
                    int controlHeight = Math.max(20, component.height + 6);
                    int controlY = y - Math.max(2, (controlHeight - component.height) / 2);
                    graphicsOptionsDropdownRects.add(
                        new UiRect(
                            x,
                            controlY,
                            Math.max(1, component.width),
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

        // Everything else must either have an explicit Modern asset mapping
        // or remain visibly pink. No size/position heuristics are allowed to
        // hide unfinished legacy artwork.
        ModernUiImage image = ModernUiAssetResolver.get(
            assetKey,
            width,
            height
        );
        if (image != null) {
            image.render(x, y);
            return;
        }

        drawMissing(
            "asset:" + assetKey,
            x,
            y,
            width,
            height
        );
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

            int x = parentX + component.x;
            int y = parentY + component.y;

            if (component.text != null
                && component.text.length() > 0
                && component.text.toString().contains("Graphics Options")) {
                return new GraphicsOptionsAnchor(x + component.width / 2, y);
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

        int x = graphicsOptionsTitleCenterX - 345;
        int y = graphicsOptionsTitleY - 34;
        int width = 690;
        int height = 385;

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
            width - 36,
            4
        );
        if (divider != null) {
            divider.render(x + 18, graphicsOptionsTitleY + 114);
            divider.render(x + 18, graphicsOptionsTitleY + 324);
        } else {
            drawMissing(
                "asset:graphics-options/divider",
                x + 18,
                graphicsOptionsTitleY + 114,
                width - 36,
                4
            );
            drawMissing(
                "asset:graphics-options/divider",
                x + 18,
                graphicsOptionsTitleY + 324,
                width - 36,
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
        int buttonY = graphicsOptionsTitleY + 20;

        for (int i = 0; i < centers.length; i++) {
            int buttonX = centers[i] - 42;
            boolean active = (!GlRenderer.enabled && i == 0)
                || (GlRenderer.enabled && i == 2);
            drawModernControlBox(buttonX, buttonY, 84, 40, false, active);
            ModernTrueTypeFont.drawCentered(
                ModernUiFontRegistry.BOLD_12,
                labels[i],
                centers[i],
                buttonY + 27,
                active ? 0xFFF4D1 : 0xE8DDC4,
                18.0F,
                true
            );
        }

        // Main Menu is text-driven in the cache, so give it Modern UI chrome
        // without changing its click/script behavior.
        drawModernControlBox(
            graphicsOptionsTitleCenterX - 78,
            graphicsOptionsTitleY + 318,
            156,
            28,
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
                9,
                6
            );
            int arrowX = x + width - 15;
            int arrowY = y + Math.max(4, (height - 6) / 2);
            if (arrow != null) {
                arrow.render(arrowX, arrowY);
            } else {
                drawMissing("asset:icons/dropdown", arrowX, arrowY, 9, 6);
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
        int knobX = left + (right - left) * (selected - 1) / 3 - 6;

        ModernUiImage knob = ModernUiAssetResolver.get(
            "controls/slider-knob",
            13,
            18
        );
        if (knob != null) {
            knob.render(knobX, centerY - 9);
        } else {
            drawMissing("asset:controls/slider-knob", knobX, centerY - 9, 13, 18);
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
        ModernUiAssetResolver.clear();
        ModernTrueTypeFont.clear();
    }
}
