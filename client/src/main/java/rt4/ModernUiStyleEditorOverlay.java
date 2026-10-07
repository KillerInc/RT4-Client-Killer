package rt4;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * In-game Modern UI style editor.
 *
 * This is deliberately rendered inside the game canvas. It never creates a
 * JFrame/JDialog and it blocks clicks from reaching the underlying RT4
 * interface while open.
 */
public final class ModernUiStyleEditorOverlay {
    private static boolean open;
    private static boolean styleDropdownOpen;
    private static boolean uiScaleDropdownOpen;
    private static boolean textScaleDropdownOpen;
    private static boolean iconScaleDropdownOpen;

    private static final float[] SCALE_VALUES = {
        0.50F, 0.75F, 0.90F, 1.00F, 1.10F, 1.25F,
        1.50F, 1.75F, 2.00F, 2.50F, 3.00F, 4.00F
    };

    private ModernUiStyleEditorOverlay() {
    }

    public static boolean isOpen() {
        return open;
    }

    public static void open() {
        if (!ModernUiManager.isEnabled()) {
            return;
        }
        open = true;
        closeDropdowns();
        ScriptRunner.forceRedrawAllRectangles();
        GameShell.fullRedraw = true;
        DisplayDebug.log("MODERN_UI in-game Style Editor opened");
    }

    public static void close() {
        if (!open) {
            return;
        }
        open = false;
        closeDropdowns();
        ScriptRunner.forceRedrawAllRectangles();
        GameShell.fullRedraw = true;
        DisplayDebug.log("MODERN_UI in-game Style Editor closed");
    }

    public static void render() {
        if (!open) {
            return;
        }
        if (!ModernUiManager.isEnabled()) {
            close();
            return;
        }

        int canvasW = GameShell.canvasWidth;
        int canvasH = GameShell.canvasHeight;

        fillAlpha(0, 0, canvasW, canvasH, 0x000000, 120);

        int width = Math.min(760, Math.max(560, canvasW - 80));
        int height = Math.min(600, Math.max(430, canvasH - 70));
        int x = (canvasW - width) / 2;
        int y = (canvasH - height) / 2;

        fill(x, y, width, height, 0x2B241B);
        outline(x, y, width, height, 0x8C744A);
        outline(x + 1, y + 1, width - 2, height - 2, 0x17120D);

        fill(x + 2, y + 2, width - 4, 32, 0x4A3B28);
        ModernTrueTypeFont.draw("Style Editor", x + 16, y + 23, 0xF2D99B, 16.0F, true);

        int closeX = x + width - 30;
        int closeY = y + 7;
        drawOsrsButton(closeX, closeY, 20, 20, true);
        ModernTrueTypeFont.drawCentered("X", closeX + 10, closeY + 15, 0xF4E7C0, 11.0F, true);

        int left = x + 18;
        int right = x + width - 18;
        int fieldX = x + 150;
        int fieldW = right - fieldX;

        int rowY = y + 56;
        ModernTrueTypeFont.draw("Active Style:", left, rowY + 15, 0xE4D2A3, 12.0F, true);
        drawField(fieldX, rowY, fieldW, 22);
        UiStyleInfo effective = ModernUiManager.getEffectiveStyle();
        ModernTrueTypeFont.draw(effective.name, fieldX + 7, rowY + 16, 0xFFF2CF, 12.0F, false);
        drawDownArrow(fieldX + fieldW - 16, rowY + 8);

        rowY += 34;
        ModernTrueTypeFont.draw("UI Scale:", left, rowY + 15, 0xE4D2A3, 12.0F, true);
        drawScaleField(fieldX, rowY, fieldW, ModernUiPreferences.getUiScale());

        rowY += 34;
        ModernTrueTypeFont.draw("Text Scale:", left, rowY + 15, 0xE4D2A3, 12.0F, true);
        drawScaleField(fieldX, rowY, fieldW, ModernUiPreferences.getTextScale());

        rowY += 34;
        ModernTrueTypeFont.draw("Icon Scale:", left, rowY + 15, 0xE4D2A3, 12.0F, true);
        drawScaleField(fieldX, rowY, fieldW, ModernUiPreferences.getIconScale());

        int contentTop = rowY + 42;
        int bottomButtonsY = y + height - 40;
        int contentHeight = bottomButtonsY - contentTop - 10;
        int gap = 10;
        int columnW = (width - 36 - gap) / 2;

        int addonX = left;
        int diagX = left + columnW + gap;

        drawPanel(addonX, contentTop, columnW, contentHeight, "UI Add-ons");
        drawPanel(diagX, contentTop, columnW, contentHeight, "Style Information / Diagnostics");

        renderAddons(addonX + 10, contentTop + 30, columnW - 20, contentHeight - 40, effective);
        renderDiagnostics(diagX + 10, contentTop + 30, columnW - 20, contentHeight - 40, effective);

        int buttonW = 118;
        int buttonH = 22;
        int buttonGap = 8;
        int totalButtons = buttonW * 4 + buttonGap * 3;
        int buttonX = x + (width - totalButtons) / 2;

        drawOsrsButton(buttonX, bottomButtonsY, buttonW, buttonH, true);
        drawOsrsButton(buttonX + (buttonW + buttonGap), bottomButtonsY, buttonW, buttonH, true);
        drawOsrsButton(buttonX + (buttonW + buttonGap) * 2, bottomButtonsY, buttonW, buttonH, true);
        drawOsrsButton(buttonX + (buttonW + buttonGap) * 3, bottomButtonsY, buttonW, buttonH, true);

        ModernTrueTypeFont.drawCentered("Open Styles Folder", buttonX + buttonW / 2, bottomButtonsY + 16, 0xF1E4BF, 10.0F, true);
        ModernTrueTypeFont.drawCentered("Reload Styles", buttonX + (buttonW + buttonGap) + buttonW / 2, bottomButtonsY + 16, 0xF1E4BF, 10.0F, true);
        ModernTrueTypeFont.drawCentered("Validate Styles", buttonX + (buttonW + buttonGap) * 2 + buttonW / 2, bottomButtonsY + 16, 0xF1E4BF, 10.0F, true);
        ModernTrueTypeFont.drawCentered("Close", buttonX + (buttonW + buttonGap) * 3 + buttonW / 2, bottomButtonsY + 16, 0xF1E4BF, 10.0F, true);

        renderDropdowns(x, y, width, fieldX, fieldW);

        if (Mouse.clickButton == 1) {
            handleClick(
                Mouse.clickX,
                Mouse.clickY,
                x,
                y,
                width,
                height,
                fieldX,
                fieldW,
                buttonX,
                bottomButtonsY,
                buttonW,
                buttonH,
                buttonGap,
                addonX,
                contentTop,
                columnW,
                contentHeight,
                effective
            );
            Mouse.clickButton = 0;
        }
    }

    private static void handleClick(
        int mouseX,
        int mouseY,
        int x,
        int y,
        int width,
        int height,
        int fieldX,
        int fieldW,
        int buttonX,
        int buttonsY,
        int buttonW,
        int buttonH,
        int buttonGap,
        int addonX,
        int contentTop,
        int columnW,
        int contentHeight,
        UiStyleInfo effective
    ) {
        if (contains(mouseX, mouseY, x + width - 30, y + 7, 20, 20)) {
            close();
            return;
        }

        int styleY = y + 56;
        int uiY = styleY + 34;
        int textY = uiY + 34;
        int iconY = textY + 34;

        if (styleDropdownOpen && handleStyleDropdown(mouseX, mouseY, fieldX, styleY + 22, fieldW)) {
            return;
        }
        if (uiScaleDropdownOpen && handleScaleDropdown(mouseX, mouseY, fieldX, uiY + 22, fieldW, 0)) {
            return;
        }
        if (textScaleDropdownOpen && handleScaleDropdown(mouseX, mouseY, fieldX, textY + 22, fieldW, 1)) {
            return;
        }
        if (iconScaleDropdownOpen && handleScaleDropdown(mouseX, mouseY, fieldX, iconY + 22, fieldW, 2)) {
            return;
        }

        if (contains(mouseX, mouseY, fieldX, styleY, fieldW, 22)) {
            closeDropdowns();
            styleDropdownOpen = true;
            return;
        }
        if (contains(mouseX, mouseY, fieldX, uiY, fieldW, 22)) {
            closeDropdowns();
            uiScaleDropdownOpen = true;
            return;
        }
        if (contains(mouseX, mouseY, fieldX, textY, fieldW, 22)) {
            closeDropdowns();
            textScaleDropdownOpen = true;
            return;
        }
        if (contains(mouseX, mouseY, fieldX, iconY, fieldW, 22)) {
            closeDropdowns();
            iconScaleDropdownOpen = true;
            return;
        }

        closeDropdowns();

        Set<String> enabled = ModernUiPreferences.getEnabledAddons();
        List<UiStyleInfo> addons = UiStyleRepository.getAddons();
        int addonRowY = contentTop + 32;
        for (UiStyleInfo addon : addons) {
            boolean requirementsOk = UiStyleRepository.requirementsSatisfied(addon, effective.id, enabled);
            if (requirementsOk && contains(mouseX, mouseY, addonX + 10, addonRowY, columnW - 20, 22)) {
                ModernUiManager.setAddonEnabled(addon.id, !enabled.contains(addon.id));
                return;
            }
            addonRowY += 24;
            if (addonRowY > contentTop + contentHeight - 24) {
                break;
            }
        }

        int step = buttonW + buttonGap;
        if (contains(mouseX, mouseY, buttonX, buttonsY, buttonW, buttonH)) {
            openStylesFolder();
        } else if (contains(mouseX, mouseY, buttonX + step, buttonsY, buttonW, buttonH)) {
            ModernUiManager.refreshStyles();
        } else if (contains(mouseX, mouseY, buttonX + step * 2, buttonsY, buttonW, buttonH)) {
            UiStyleRepository.refresh();
            ScriptRunner.forceRedrawAllRectangles();
        } else if (contains(mouseX, mouseY, buttonX + step * 3, buttonsY, buttonW, buttonH)) {
            close();
        }
    }

    private static boolean handleStyleDropdown(int mouseX, int mouseY, int x, int y, int width) {
        List<UiStyleInfo> styles = UiStyleRepository.getStyles();
        int row = y;
        for (UiStyleInfo info : styles) {
            if (contains(mouseX, mouseY, x, row, width, 22)) {
                styleDropdownOpen = false;
                ModernUiManager.selectStyle(info.id);
                return true;
            }
            row += 22;
        }
        return false;
    }

    private static boolean handleScaleDropdown(int mouseX, int mouseY, int x, int y, int width, int kind) {
        int row = y;
        for (float value : SCALE_VALUES) {
            if (contains(mouseX, mouseY, x, row, width, 20)) {
                closeDropdowns();
                if (kind == 0) {
                    ModernUiManager.setUiScale(value);
                } else if (kind == 1) {
                    ModernUiManager.setTextScale(value);
                } else {
                    ModernUiManager.setIconScale(value);
                }
                return true;
            }
            row += 20;
        }
        return false;
    }

    private static void renderDropdowns(int x, int y, int width, int fieldX, int fieldW) {
        int styleY = y + 56;
        int uiY = styleY + 34;
        int textY = uiY + 34;
        int iconY = textY + 34;

        if (styleDropdownOpen) {
            List<UiStyleInfo> styles = UiStyleRepository.getStyles();
            int row = styleY + 22;
            fill(fieldX, row, fieldW, Math.max(22, styles.size() * 22), 0x3A3022);
            outline(fieldX, row, fieldW, Math.max(22, styles.size() * 22), 0xA58956);
            for (UiStyleInfo info : styles) {
                ModernTrueTypeFont.draw(info.name, fieldX + 7, row + 16, 0xFFF2CF, 11.0F, false);
                row += 22;
            }
        }

        if (uiScaleDropdownOpen) {
            renderScaleDropdown(fieldX, uiY + 22, fieldW);
        } else if (textScaleDropdownOpen) {
            renderScaleDropdown(fieldX, textY + 22, fieldW);
        } else if (iconScaleDropdownOpen) {
            renderScaleDropdown(fieldX, iconY + 22, fieldW);
        }
    }

    private static void renderScaleDropdown(int x, int y, int width) {
        int totalH = SCALE_VALUES.length * 20;
        fill(x, y, width, totalH, 0x3A3022);
        outline(x, y, width, totalH, 0xA58956);
        int row = y;
        for (float value : SCALE_VALUES) {
            ModernTrueTypeFont.draw(formatScale(value), x + 7, row + 15, 0xFFF2CF, 10.0F, false);
            row += 20;
        }
    }

    private static void renderAddons(int x, int y, int width, int height, UiStyleInfo effective) {
        Set<String> enabled = ModernUiPreferences.getEnabledAddons();
        List<UiStyleInfo> addons = UiStyleRepository.getAddons();

        if (addons.isEmpty()) {
            ModernTrueTypeFont.draw("No add-on style archives installed.", x, y + 14, 0xBFAF8C, 10.0F, false);
            return;
        }

        int rowY = y;
        for (UiStyleInfo addon : addons) {
            if (rowY + 22 > y + height) {
                break;
            }
            boolean ok = UiStyleRepository.requirementsSatisfied(addon, effective.id, enabled);
            boolean on = enabled.contains(addon.id);

            outline(x, rowY, 18, 18, ok ? 0xA58956 : 0x554A39);
            if (on) {
                fill(x + 4, rowY + 4, 10, 10, 0xC9A45C);
            }
            ModernTrueTypeFont.draw(
                addon.name + "  " + addon.version,
                x + 26,
                rowY + 14,
                ok ? 0xE9D7AA : 0x756A57,
                10.0F,
                false
            );
            rowY += 24;
        }
    }

    private static void renderDiagnostics(int x, int y, int width, int height, UiStyleInfo effective) {
        int line = y + 13;
        int step = 15;

        line = drawInfoLine(effective.name, x, line, width, height, y, 0xF0DDAE, step);
        line = drawInfoLine("ID: " + effective.id, x, line, width, height, y, 0xCDBF9F, step);
        line = drawInfoLine("Version: " + effective.version, x, line, width, height, y, 0xCDBF9F, step);
        line = drawInfoLine("Author: " + effective.author, x, line, width, height, y, 0xCDBF9F, step);
        line = drawInfoLine("Base: " + effective.base, x, line, width, height, y, 0xCDBF9F, step);

        if (!effective.description.isEmpty()) {
            line += 5;
            line = drawInfoLine(effective.description, x, line, width, height, y, 0xBDAE8C, step);
        }

        List<String> diagnostics = UiStyleRepository.getDiagnostics();
        if (!diagnostics.isEmpty()) {
            line += 5;
            line = drawInfoLine("Archive diagnostics:", x, line, width, height, y, 0xF0DDAE, step);
            for (String diagnostic : diagnostics) {
                line = drawInfoLine("- " + diagnostic, x, line, width, height, y, 0xD4AA80, step);
            }
        }
    }

    private static int drawInfoLine(String text, int x, int line, int width, int height, int top, int color, int step) {
        if (line <= top + height) {
            ModernTrueTypeFont.draw(text, x, line, color, 9.0F, false);
        }
        return line + step;
    }

    private static void drawPanel(int x, int y, int width, int height, String title) {
        fill(x, y, width, height, 0x201B15);
        outline(x, y, width, height, 0x6C5A3D);
        ModernTrueTypeFont.draw(title, x + 8, y + 18, 0xE8D3A3, 11.0F, true);
        line(x + 8, y + 24, width - 16, 0x56472F);
    }

    private static void drawScaleField(int x, int y, int width, float value) {
        drawField(x, y, width, 22);
        ModernTrueTypeFont.draw(formatScale(value), x + 7, y + 16, 0xFFF2CF, 12.0F, false);
        drawDownArrow(x + width - 16, y + 8);
    }

    private static void drawField(int x, int y, int width, int height) {
        fill(x, y, width, height, 0x3A3022);
        outline(x, y, width, height, 0xA58956);
        line(x + 1, y + 1, width - 2, 0x594A33);
    }

    private static void drawDownArrow(int x, int y) {
        ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
        if (arrow != null) {
            arrow.render(x, y);
        } else {
            ModernTrueTypeFont.draw("v", x, y + 6, 0xE7D4A5, 9.0F, false);
        }
    }

    private static void drawOsrsButton(int x, int y, int width, int height, boolean enabled) {
        int fill = enabled ? 0x5E4C31 : 0x302A22;
        int border = enabled ? 0xA88A57 : 0x4B443A;
        fill(x, y, width, height, fill);
        outline(x, y, width, height, border);
        line(x + 1, y + 1, width - 2, enabled ? 0x806741 : 0x3D372F);
    }

    private static String formatScale(float value) {
        return Math.round(value * 100.0F) + "%";
    }

    private static void openStylesFolder() {
        File folder = ModernUiPreferences.getStylesDirectory();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(folder);
            }
        } catch (Exception ex) {
            DisplayDebug.log("MODERN_UI unable to open styles folder: " + ex.getMessage());
        }
    }

    private static void closeDropdowns() {
        styleDropdownOpen = false;
        uiScaleDropdownOpen = false;
        textScaleDropdownOpen = false;
        iconScaleDropdownOpen = false;
    }

    private static boolean contains(int mx, int my, int x, int y, int width, int height) {
        return mx >= x && my >= y && mx < x + width && my < y + height;
    }

    private static void fill(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.fillRect(x, y, width, height, color);
        } else {
            SoftwareRaster.fillRect(x, y, width, height, color);
        }
    }

    private static void fillAlpha(int x, int y, int width, int height, int color, int alpha) {
        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, color, alpha);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, color, alpha);
        }
    }

    private static void outline(int x, int y, int width, int height, int color) {
        if (GlRenderer.enabled) {
            GlRaster.drawRect(x, y, width, height, color);
        } else {
            SoftwareRaster.drawRect(x, y, width, height, color);
        }
    }

    private static void line(int x, int y, int width, int color) {
        if (GlRenderer.enabled) {
            GlRaster.drawHorizontalLine(x, y, width, color);
        } else {
            SoftwareRaster.drawHorizontalLine(x, y, width, color);
        }
    }
}
