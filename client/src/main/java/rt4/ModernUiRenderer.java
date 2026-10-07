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

        renderComponents(
            InterfaceList.components[interfaceId],
            -1,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom,
            parentX,
            parentY,
            rectangle
        );
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
            safeColor(color),
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
