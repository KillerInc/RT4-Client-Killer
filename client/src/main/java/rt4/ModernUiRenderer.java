package rt4;

import plugin.PluginRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Independent Modern UI renderer.
 *
 * Existing Component objects are currently used only as UI state/layout input
 * while the migration is in progress. Visuals do not call Component.getSprite
 * or Component.getFont. Missing visual implementations are drawn explicitly.
 */
public final class ModernUiRenderer {
    private static final Set<String> loggedMissing = new HashSet<>();
    private static int graphicsOptionsDepth;
    private static int graphicsOptionsTitleCenterX;
    private static int graphicsOptionsTitleY;

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
        boolean graphicsOptions = containsGraphicsOptionsText(loadedComponents);
        int oldTitleCenterX = graphicsOptionsTitleCenterX;
        int oldTitleY = graphicsOptionsTitleY;

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

        ModernTrueTypeFont.drawInBox(
            display == null ? "" : display.toString(),
            x,
            y,
            component.width,
            component.height,
            graphicsOptionsDepth > 0 ? 0xE2E5E9 : safeColor(color),
            component.halign,
            component.valign,
            12.0F,
            component.shadowed
        );
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
                        ModernTrueTypeFont.draw(
                            object.name.toString(),
                            tx,
                            ty + 11,
                            safeColor(component.color),
                            11.0F,
                            component.shadowed
                        );
                    }
                }
                index++;
            }
        }
    }

    public static void drawMissing(String key, int x, int y, int width, int height) {
        if (loggedMissing.add(key)) {
            DisplayDebug.log("MODERN_UI MISSING " + key);
        }

        width = Math.max(width, 24);
        height = Math.max(height, 18);
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, 0x4C1733, 210);
            GlRaster.drawRect(x, y, width, height, 0xFF44AA);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, 0x4C1733, 210);
            SoftwareRaster.drawRect(x, y, width, height, 0xFF44AA);
        }

        String label = "[MISSING " + key + "]";
        ModernTrueTypeFont.draw(label, x + 3, y + Math.min(height - 3, 14), 0xFFFFFF, 10.0F, true);
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

    private static void renderGraphicsOptionsImageFallback(Component component, int x, int y) {
        String key = "graphics-options/" + componentAssetKey(component);
        if (loggedMissing.add(key)) {
            DisplayDebug.log("MODERN_UI replaced " + key + " with modern chrome");
        }

        int width = Math.max(1, component.width);
        int height = Math.max(1, component.height);
        int centerX = x + width / 2;
        int relativeY = y - graphicsOptionsTitleY;

        // The old parchment frame is composed from many large/long sprite
        // slices. Do not turn those slices into rectangular placeholders.
        if (width >= 165 || height >= 55 || relativeY < -20) {
            return;
        }

        // Display-mode image buttons are real controls, not frame slices.
        if (relativeY >= 10 && relativeY <= 90
            && width >= 42 && width <= 92
            && height >= 24 && height <= 52) {
            drawModernControlBox(x, y, width, height, false);
            return;
        }

        // Brightness occupies the first advanced-options column.
        if (relativeY >= 140 && relativeY <= 195
            && Math.abs(centerX - (graphicsOptionsTitleCenterX - 260)) <= 30
            && width >= 70 && width <= 150
            && height >= 12 && height <= 30) {
            drawModernBrightness(x, y, width, height);
            return;
        }

        // Native selectors are assemblies of a central field plus tiny caps
        // and arrows. Replace only the central field; tiny pieces fall through
        // and disappear, preventing the blocky brown mosaic seen previously.
        if (relativeY >= 85 && relativeY <= 325
            && width >= 70 && width <= 155
            && height >= 14 && height <= 30) {
            drawModernControlBox(x, y, width, height, true);
            return;
        }

        // Tiny decorative pieces and remaining parchment fragments are
        // intentionally omitted. Modern UI never falls back to Index-8 art.
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

        fillAlpha(x, y, width, height, 0x111419, 218);
        drawOutline(x, y, width, height, 0x8F8058);
        drawOutline(x + 1, y + 1, width - 2, height - 2, 0x343941);

        // Clean section rules replace the parchment strips.
        hline(x + 18, graphicsOptionsTitleY + 116, width - 36, 0x4B515A);
        hline(x + 18, graphicsOptionsTitleY + 326, width - 36, 0x4B515A);

        // The SD/HD lettering was baked into legacy sprites. Recreate it as
        // vector text so the display mode row is fully Modern UI as well.
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
            drawModernControlBox(buttonX, buttonY, 84, 40, false);
            if (active) {
                drawOutline(buttonX + 2, buttonY + 2, 80, 36, 0xD0B66D);
            }
            ModernTrueTypeFont.drawCentered(
                labels[i],
                centers[i],
                buttonY + 27,
                active ? 0xFFFFFF : 0xE0E3E7,
                18.0F,
                true
            );
        }
    }

    private static void drawModernControlBox(
        int x,
        int y,
        int width,
        int height,
        boolean dropdown
    ) {
        fillAlpha(x, y, width, height, 0x171A1F, 240);
        drawOutline(x, y, width, height, 0x9C8958);
        hline(x + 1, y + 1, width - 2, 0x373C44);

        if (dropdown && width >= 22) {
            ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
            if (arrow != null) {
                arrow.render(x + width - 15, y + Math.max(4, (height - 6) / 2));
            }
        }
    }

    private static void drawModernBrightness(
        int x,
        int y,
        int width,
        int height
    ) {
        int lineY = y + height / 2;
        int left = x + 10;
        int right = x + width - 10;
        hline(left, lineY, Math.max(1, right - left), 0x69717C);

        int levels = 4;
        int selected = Preferences.brightness;
        if (selected < 1) {
            selected = 1;
        } else if (selected > levels) {
            selected = levels;
        }

        for (int i = 0; i < levels; i++) {
            int tickX = left + (right - left) * i / (levels - 1);
            fillAlpha(tickX - 2, lineY - 2, 5, 5, 0x8B939E, 255);
            if (i == selected - 1) {
                drawOutline(tickX - 4, lineY - 4, 9, 9, 0xD0B66D);
            }
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

    private static final class GraphicsOptionsAnchor {
        private final int centerX;
        private final int y;

        private GraphicsOptionsAnchor(int centerX, int y) {
            this.centerX = centerX;
            this.y = y;
        }
    }

    private static void drawMissingBackdrop(String key, int x, int y, int width, int height) {
        int tile = 28;
        for (int row = 0; row < height; row += tile) {
            for (int col = 0; col < width; col += tile) {
                boolean alternate = ((row / tile) + (col / tile) & 1) != 0;
                int color = alternate ? 0xFF00A8 : 0x171717;
                int drawW = Math.min(tile, width - col);
                int drawH = Math.min(tile, height - row);
                if (GlRenderer.enabled) {
                    GlRaster.fillRectAlpha(x + col, y + row, drawW, drawH, color, 220);
                } else {
                    SoftwareRaster.fillRectAlpha(x + col, y + row, drawW, drawH, color, 220);
                }
            }
        }

        if (GlRenderer.enabled) {
            GlRaster.drawRect(x, y, width, height, 0xFF66CC);
            GlRaster.drawRect(x + 1, y + 1, width - 2, height - 2, 0xFFFFFF);
        } else {
            SoftwareRaster.drawRect(x, y, width, height, 0xFF66CC);
            SoftwareRaster.drawRect(x + 1, y + 1, width - 2, height - 2, 0xFFFFFF);
        }

        int centerX = x + width / 2;
        int centerY = y + height / 2;
        ModernTrueTypeFont.drawCentered(
            "MISSING MODERN UI ASSET",
            centerX,
            centerY - 4,
            0xFFFFFF,
            18.0F,
            true
        );
        ModernTrueTypeFont.drawCentered(
            key,
            centerX,
            centerY + 18,
            0xFFFFFF,
            10.0F,
            true
        );
    }

    private static boolean containsGraphicsOptionsText(Component[] components) {
        if (components == null) {
            return false;
        }
        for (Component component : components) {
            if (component == null) {
                continue;
            }
            if (component.text != null
                && component.text.length() > 0
                && component.text.toString().contains("Graphics Options")) {
                return true;
            }
            if (component.createdComponents != null && containsGraphicsOptionsText(component.createdComponents)) {
                return true;
            }
        }
        return false;
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
