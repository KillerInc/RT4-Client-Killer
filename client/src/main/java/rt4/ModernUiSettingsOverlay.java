package rt4;

import java.util.ArrayList;
import java.util.List;

/**
 * Settings bridge shown inside Graphics Options.
 *
 * Standard UI mode clones the complete existing RT4 Anti-aliasing selector
 * assembly (all sprite pieces) into the unused sixth slot. This keeps the new
 * control visually native instead of approximating the cache artwork.
 *
 * Modern UI mode uses the new TrueType/vector control treatment.
 */
public final class ModernUiSettingsOverlay {
    private static int titleCenterX;
    private static int titleY;
    private static boolean graphicsOptionsSeen;
    private static boolean dropdownOpen;

    private static final List<NativePiece> nativeSpritePieces = new ArrayList<>();
    private static boolean nativeCloneLogged;

    private static final int TEXT = 0x3B2B1B;
    private static final int DISABLED = 0x6D6658;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
        nativeSpritePieces.clear();
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null) {
            return;
        }

        // Collect all small sprite components for this frame. Once the
        // Graphics Options title position is known, render() filters these
        // down to the Anti-aliasing selector's exact source rectangle.
        if (component.type == 5
            && component.width > 0 && component.height > 0
            && component.width <= 180 && component.height <= 40) {
            nativeSpritePieces.add(new NativePiece(component, x, y));
        }

        if (component.text != null
            && component.text.length() > 0
            && component.text.toString().contains("Graphics Options")) {
            titleCenterX = x + component.width / 2;
            titleY = y;
            graphicsOptionsSeen = true;
        }
    }

    public static boolean isGraphicsOptionsSeen() {
        return graphicsOptionsSeen;
    }

    public static void render() {
        if (!graphicsOptionsSeen) {
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();
        boolean modern = ModernUiManager.isEnabled();

        // Existing Anti-aliasing control is the fourth column on the bottom
        // advanced-options row. Modern UI occupies the unused fifth/sixth slot
        // immediately to its right.
        int sourceCenterX = titleCenterX + 125;
        int targetCenterX = titleCenterX + 255;

        int labelBaseline = titleY + 270;
        int selectorW = 116;
        int selectorH = 20;
        int selectorX = targetCenterX - selectorW / 2;
        int selectorY = titleY + 279;

        if (modern) {
            ModernTrueTypeFont.drawCentered(
                "Modern UI",
                targetCenterX,
                labelBaseline,
                0xE4D2A3,
                11.0F,
                true
            );
            drawModernField(selectorX, selectorY, selectorW, selectorH, true);
            ModernTrueTypeFont.drawCentered(
                "Yes",
                targetCenterX - 5,
                selectorY + 15,
                0xFFF2CF,
                11.0F,
                false
            );
            drawModernArrow(selectorX + selectorW - 16, selectorY + 7);
        } else {
            if (Fonts.p12Full == null) {
                return;
            }

            Fonts.p12Full.renderCenter(
                JagString.parse("Modern UI"),
                targetCenterX,
                labelBaseline,
                TEXT,
                -1
            );

            if (!drawNativeSelectorAssembly(sourceCenterX, targetCenterX, selectorY)) {
                drawNativeFallback(selectorX, selectorY, selectorW, selectorH, true, true);
            }

            Fonts.p12Full.renderCenter(
                JagString.parse("No"),
                targetCenterX - 5,
                selectorY + 15,
                TEXT,
                -1
            );
        }

        int editorW = 116;
        int editorH = 20;
        int editorX = targetCenterX - editorW / 2;
        int editorY = selectorY + 24;

        if (modern) {
            drawModernField(editorX, editorY, editorW, editorH, true);
            ModernTrueTypeFont.drawCentered(
                "Style Editor",
                targetCenterX,
                editorY + 15,
                0xFFF2CF,
                10.0F,
                true
            );
        } else {
            // Button is not a dropdown, so use a native-looking field without
            // arrow/cap decoration.
            drawNativeFallback(editorX, editorY, editorW, editorH, false, false);
            Fonts.p12Full.renderCenter(
                JagString.parse("Style Editor"),
                targetCenterX,
                editorY + 15,
                DISABLED,
                -1
            );
        }

        int popupX = selectorX;
        int popupY = selectorY + selectorH;
        int popupH = selectorH * 2;

        if (dropdownOpen) {
            if (modern) {
                drawModernField(popupX, popupY, selectorW, popupH, true);
                drawModernChoice("No", popupX, popupY, selectorW, selectorH, !modern);
                drawModernChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern);
            } else {
                drawNativeFallback(popupX, popupY, selectorW, popupH, true, false);
                drawNativeChoice("No", popupX, popupY, selectorW, selectorH, !modern);
                drawNativeChoice("Yes", popupX, popupY + selectorH, selectorW, selectorH, modern);
            }
        }

        if (Mouse.clickButton != 1) {
            return;
        }

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(false);
                return;
            }
            if (contains(mx, my, popupX, popupY + selectorH, selectorW, selectorH)) {
                Mouse.clickButton = 0;
                dropdownOpen = false;
                ModernUiManager.setEnabled(true);
                return;
            }
            if (!contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
            }
        }

        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
            Mouse.clickButton = 0;
            dropdownOpen = !dropdownOpen;
        } else if (!dropdownOpen
            && modern
            && contains(mx, my, editorX, editorY, editorW, editorH)) {
            Mouse.clickButton = 0;
            ModernUiManager.openStyleEditor();
        }
    }

    /**
     * Replays the complete sprite assembly already used by Anti-aliasing.
     */
    private static boolean drawNativeSelectorAssembly(
        int sourceCenterX,
        int targetCenterX,
        int targetY
    ) {
        int sourceFieldLeft = sourceCenterX - 65;
        int sourceFieldRight = sourceCenterX + 70;
        int sourceTop = titleY + 276;
        int sourceBottom = titleY + 304;

        int deltaX = targetCenterX - sourceCenterX;
        int deltaY = targetY - (titleY + 279);

        int drawn = 0;
        for (NativePiece piece : nativeSpritePieces) {
            if (piece.x + piece.component.width < sourceFieldLeft
                || piece.x > sourceFieldRight
                || piece.y + piece.component.height < sourceTop
                || piece.y > sourceBottom) {
                continue;
            }

            try {
                Sprite sprite = piece.component.getSprite(false);
                if (sprite == null) {
                    continue;
                }

                sprite.render(piece.x + deltaX, piece.y + deltaY);
                drawn++;
            } catch (Exception ignored) {
            }
        }

        if (drawn > 0 && !nativeCloneLogged) {
            nativeCloneLogged = true;
            DisplayDebug.log("MODERN_UI cloned " + drawn + " native Graphics Options sprite pieces");
        }
        return drawn > 0;
    }

    private static void drawNativeFallback(
        int x,
        int y,
        int width,
        int height,
        boolean enabled,
        boolean withDropdownDecoration
    ) {
        int fill = enabled ? 0x9B8458 : 0x817967;
        int border = enabled ? 0x29251C : 0x5F5A50;
        int highlight = enabled ? 0xC7B07B : 0x989184;

        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        outline(x + 1, y + 1, width - 2, height - 2, 0x6F5B3B);
        hline(x + 2, y + 2, width - 4, highlight);

        if (withDropdownDecoration) {
            drawFallbackSideCaps(x, y, width, height);
            drawFallbackDownArrow(x + width - 15, y + 7);
        }
    }

    private static void drawFallbackSideCaps(int x, int y, int width, int height) {
        int midY = y + height / 2;
        int dark = 0x3B3224;
        int light = 0xB29A67;

        int left = x - 7;
        hline(left + 3, midY - 4, 2, dark);
        hline(left + 2, midY - 3, 3, dark);
        hline(left + 1, midY - 2, 4, dark);
        hline(left, midY - 1, 5, dark);
        hline(left + 1, midY, 4, light);
        hline(left + 2, midY + 1, 3, light);
        hline(left + 3, midY + 2, 2, light);

        int right = x + width + 2;
        hline(right, midY - 4, 2, dark);
        hline(right, midY - 3, 3, dark);
        hline(right, midY - 2, 4, dark);
        hline(right, midY - 1, 5, dark);
        hline(right, midY, 4, light);
        hline(right, midY + 1, 3, light);
        hline(right, midY + 2, 2, light);
    }

    private static void drawFallbackDownArrow(int x, int y) {
        int color = 0x4A2318;
        hline(x, y, 9, color);
        hline(x + 1, y + 1, 7, color);
        hline(x + 2, y + 2, 5, color);
        hline(x + 3, y + 3, 3, color);
        hline(x + 4, y + 4, 1, color);
    }

    private static void drawNativeChoice(
        String text,
        int x,
        int y,
        int width,
        int height,
        boolean selected
    ) {
        if (selected) {
            fillAlpha(x + 2, y + 2, width - 4, height - 4, 0xD1B875, 150);
        }
        Fonts.p12Full.renderCenter(
            JagString.parse(text),
            x + width / 2,
            y + 15,
            TEXT,
            -1
        );
    }

    private static void drawModernField(
        int x,
        int y,
        int width,
        int height,
        boolean enabled
    ) {
        int fill = enabled ? 0x3A3022 : 0x29251F;
        int border = enabled ? 0xA58956 : 0x514A40;
        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        hline(x + 1, y + 1, width - 2, enabled ? 0x594A33 : 0x37322C);
    }

    private static void drawModernChoice(
        String text,
        int x,
        int y,
        int width,
        int height,
        boolean selected
    ) {
        if (selected) {
            fillAlpha(x + 2, y + 2, width - 4, height - 4, 0x806741, 220);
        }
        ModernTrueTypeFont.drawCentered(
            text,
            x + width / 2,
            y + 15,
            0xFFF2CF,
            10.0F,
            false
        );
    }

    private static void drawModernArrow(int x, int y) {
        ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
        if (arrow != null) {
            arrow.render(x, y);
        } else {
            ModernTrueTypeFont.draw("v", x, y + 6, 0xE7D4A5, 9.0F, false);
        }
    }

    private static boolean contains(
        int mx,
        int my,
        int x,
        int y,
        int width,
        int height
    ) {
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    private static void fill(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, color);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, color);
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
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, color, alpha);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, color, alpha);
        }
    }

    private static void outline(
        int x,
        int y,
        int width,
        int height,
        int color
    ) {
        if (GlRenderer.enabled) {
            GlRaster.drawRect(x, y, width, height, color);
        } else {
            SoftwareRaster.drawRect(x, y, width, height, color);
        }
    }

    private static void hline(int x, int y, int width, int color) {
        if (GlRenderer.enabled) {
            GlRaster.drawHorizontalLine(x, y, width, color);
        } else {
            SoftwareRaster.drawHorizontalLine(x, y, width, color);
        }
    }

    private static final class NativePiece {
        private final Component component;
        private final int x;
        private final int y;

        private NativePiece(Component component, int x, int y) {
            this.component = component;
            this.x = x;
            this.y = y;
        }
    }
}
