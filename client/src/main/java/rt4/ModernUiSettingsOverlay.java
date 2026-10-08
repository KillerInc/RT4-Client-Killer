package rt4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Killer Edition behavior layer for the Modern UI controls added to Graphics
 * Options. Vanilla interface processing remains authoritative and untouched.
 */
public final class ModernUiSettingsOverlay {
    private static boolean graphicsOptionsSeen;
    private static boolean selectorSeen;
    private static boolean editorSeen;
    private static boolean dropdownOpen;

    private static int selectorX;
    private static int selectorY;
    private static int selectorRight;
    private static int selectorBottom;

    private static int editorX;
    private static int editorY;
    private static int editorW;
    private static int editorH;

    // Base-game dropdown state. The four display/definition modes can each
    // expose a native resolution popup. Cache scripts do not reliably close
    // the previous popup when another mode is selected, so we normalize that
    // state here without replacing the vanilla dropdown contents.
    private static Component[] dropdownInterfaceComponents;
    private static final Set<Component> knownNativePopups =
        Collections.newSetFromMap(new IdentityHashMap<Component, Boolean>());
    private static final Map<Component, Boolean> previousPopupVisible =
        new IdentityHashMap<>();
    private static Component lastDisplayModePopup;
    private static boolean nativeDropdownOpen;

    private static final int TEXT = 0x3B2B1B;

    private ModernUiSettingsOverlay() {
    }

    public static void beginFrame() {
        graphicsOptionsSeen = false;
        selectorSeen = false;
        editorSeen = false;

        selectorX = Integer.MAX_VALUE;
        selectorY = Integer.MAX_VALUE;
        selectorRight = Integer.MIN_VALUE;
        selectorBottom = Integer.MIN_VALUE;

        refreshNativeDropdownState();
    }

    /**
     * Called after the vanilla interface hook queue has finished for the tick.
     * This catches a newly opened resolution popup immediately, before the
     * next frame is rendered.
     */
    public static void afterInterfaceScripts() {
        refreshNativeDropdownState();
    }

    public static void observeComponent(Component component, int x, int y) {
        if (component == null) {
            return;
        }

        if (component.text != null
            && component.text.length() > 0
            && component.text.toString().contains("Graphics Options")) {
            graphicsOptionsSeen = true;
        }

        int code = component.clientCode;
        if (code == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_HIT
            || code == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_PIECE
            || code == GraphicsOptionsUiInjector.CLIENT_CODE_VALUE_TEXT) {
            includeSelectorBounds(
                x,
                y,
                Math.max(1, component.width),
                Math.max(1, component.height)
            );
        } else if (code == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT) {
            editorX = x;
            editorY = y;
            editorW = Math.max(1, component.width);
            editorH = Math.max(1, component.height);
            editorSeen = true;
        }
    }

    public static boolean isGraphicsOptionsSeen() {
        return graphicsOptionsSeen;
    }

    /**
     * Runs after the original RT4 interface processing in client.mainUpdate().
     * Reads Mouse's normal click snapshot but deliberately never changes it.
     */
    public static void processInput() {
        if (!ModernUiManager.isSupportedDisplayMode()) {
            dropdownOpen = false;
            return;
        }
        if (nativeDropdownOpen) {
            // A vanilla popup owns the foreground until it closes. This keeps
            // the injected Modern UI selector from accepting clicks through it.
            dropdownOpen = false;
            return;
        }
        if (!graphicsOptionsSeen || !selectorSeen || Mouse.clickButton != 1) {
            return;
        }

        int selectorW = selectorWidth();
        int selectorH = selectorHeight();
        int popupX = selectorX;
        int popupY = selectorBottom;
        int popupW = selectorW;
        int rowH = Math.max(18, selectorH);

        int mx = Mouse.clickX;
        int my = Mouse.clickY;

        if (dropdownOpen) {
            if (contains(mx, my, popupX, popupY, popupW, rowH)) {
                dropdownOpen = false;
                ModernUiManager.setEnabled(false);
                return;
            }

            if (contains(mx, my, popupX, popupY + rowH, popupW, rowH)) {
                dropdownOpen = false;
                ModernUiManager.setEnabled(true);
                return;
            }

            if (!contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
                dropdownOpen = false;
                return;
            }
        }

        if (contains(mx, my, selectorX, selectorY, selectorW, selectorH)) {
            dropdownOpen = !dropdownOpen;
            return;
        }

        if (ModernUiManager.isEnabled()
            && editorSeen
            && contains(mx, my, editorX, editorY, editorW, editorH)) {
            ModernUiManager.openStyleEditor();
        }
    }

    public static void render() {
        if (!ModernUiManager.isSupportedDisplayMode()
            || nativeDropdownOpen
            || !graphicsOptionsSeen
            || !selectorSeen) {
            // Native dropdown popups are rendered by the cache after their
            // controls. Do not paint our selector on top of an open popup.
            dropdownOpen = false;
            return;
        }

        ModernUiManager.initialize();

        int selectorW = selectorWidth();
        int selectorH = selectorHeight();

        // Standard UI continues to draw the injected copy through the original
        // renderer. Modern mode covers only our injected copy with pack art.
        if (ModernUiManager.isEnabled()) {
            drawModernClosedSelector(
                selectorX,
                selectorY,
                selectorW,
                selectorH
            );
        }

        if (!dropdownOpen) {
            return;
        }

        int popupX = selectorX;
        int popupY = selectorBottom;
        int popupW = selectorW;
        int rowH = Math.max(18, selectorH);
        int popupH = rowH * 2;

        if (ModernUiManager.isEnabled()) {
            drawModernPopup(popupX, popupY, popupW, popupH);
            drawModernChoice("No", popupX, popupY, popupW, rowH, false);
            drawModernChoice("Yes", popupX, popupY + rowH, popupW, rowH, true);
        } else {
            drawNativePopup(popupX, popupY, popupW, popupH);
            drawNativeChoice("No", popupX, popupY, popupW, rowH, true);
            drawNativeChoice("Yes", popupX, popupY + rowH, popupW, rowH, false);
        }
    }

    private static void refreshNativeDropdownState() {
        nativeDropdownOpen = false;

        int interfaceId = InterfaceList.topLevelInterface;
        if (interfaceId < 0
            || InterfaceList.components == null
            || interfaceId >= InterfaceList.components.length) {
            clearNativeDropdownTracking();
            return;
        }

        Component[] components = InterfaceList.components[interfaceId];
        if (components == null
            || !GraphicsOptionsUiInjector.isGraphicsOptionsActive(components)) {
            clearNativeDropdownTracking();
            return;
        }

        if (dropdownInterfaceComponents != components) {
            dropdownInterfaceComponents = components;
            knownNativePopups.clear();
            previousPopupVisible.clear();
            lastDisplayModePopup = null;
        }

        List<NativePopupCandidate> candidates = new ArrayList<>();
        collectNativePopupCandidates(
            components,
            -1,
            0,
            0,
            candidates
        );

        List<NativePopupCandidate> visibleDisplayModePopups =
            new ArrayList<>();
        NativePopupCandidate newlyOpened = null;

        for (NativePopupCandidate candidate : candidates) {
            boolean visible = !candidate.component.hidden;

            if (!visible) {
                // Popup containers ship hidden in the cache. Once a component
                // has been observed hidden we can safely recognize it later
                // without confusing normal Graphics Options layout containers.
                knownNativePopups.add(candidate.component);
            }

            boolean recognized =
                knownNativePopups.contains(candidate.component)
                    || candidate.valueCount >= 4;

            if (recognized && visible) {
                nativeDropdownOpen = true;
            }

            // The four definition/display-mode resolution lists contain many
            // rows. Enforce exclusivity only on those; normal Advanced Options
            // dropdowns keep their vanilla scripts and contents untouched.
            if (recognized && visible && candidate.valueCount >= 4) {
                visibleDisplayModePopups.add(candidate);

                Boolean wasVisible =
                    previousPopupVisible.get(candidate.component);
                if (wasVisible != null && !wasVisible.booleanValue()) {
                    if (newlyOpened == null
                        || popupDistanceToClick(candidate)
                            < popupDistanceToClick(newlyOpened)) {
                        newlyOpened = candidate;
                    }
                }
            }
        }

        if (visibleDisplayModePopups.size() > 1) {
            NativePopupCandidate keep = newlyOpened;

            if (keep == null && lastDisplayModePopup != null) {
                for (NativePopupCandidate candidate
                    : visibleDisplayModePopups) {
                    if (candidate.component == lastDisplayModePopup) {
                        keep = candidate;
                        break;
                    }
                }
            }

            if (keep == null) {
                for (NativePopupCandidate candidate
                    : visibleDisplayModePopups) {
                    if (keep == null
                        || popupDistanceToClick(candidate)
                            < popupDistanceToClick(keep)) {
                        keep = candidate;
                    }
                }
            }

            for (NativePopupCandidate candidate
                : visibleDisplayModePopups) {
                if (candidate != keep) {
                    candidate.component.hidden = true;
                    InterfaceList.redraw(candidate.component);
                    DisplayDebug.log(
                        "GRAPHICS_OPTIONS closed stale display-mode dropdown"
                            + " id=" + candidate.component.id
                    );
                }
            }

            if (keep != null) {
                lastDisplayModePopup = keep.component;
            }
        } else if (visibleDisplayModePopups.size() == 1) {
            lastDisplayModePopup =
                visibleDisplayModePopups.get(0).component;
        } else {
            lastDisplayModePopup = null;
        }

        previousPopupVisible.clear();
        nativeDropdownOpen = false;
        for (NativePopupCandidate candidate : candidates) {
            boolean visible = !candidate.component.hidden;
            previousPopupVisible.put(candidate.component, visible);

            if ((knownNativePopups.contains(candidate.component)
                    || candidate.valueCount >= 4)
                && visible) {
                nativeDropdownOpen = true;
            }
        }
    }

    private static void clearNativeDropdownTracking() {
        dropdownInterfaceComponents = null;
        knownNativePopups.clear();
        previousPopupVisible.clear();
        lastDisplayModePopup = null;
        nativeDropdownOpen = false;
    }

    private static int popupDistanceToClick(
        NativePopupCandidate candidate
    ) {
        int cx = candidate.x + candidate.component.width / 2;
        int cy = candidate.y;
        return Math.abs(Mouse.clickX - cx)
            + Math.abs(Mouse.clickY - cy);
    }

    private static void collectNativePopupCandidates(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<NativePopupCandidate> out
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

            if (component.type == 0) {
                PopupTextStats stats = new PopupTextStats();
                collectPopupTextStats(
                    components,
                    component.id,
                    stats,
                    0
                );
                if (component.createdComponents != null) {
                    collectPopupTextStats(
                        component.createdComponents,
                        component.id,
                        stats,
                        0
                    );
                }

                int width = Math.max(1, component.width);
                int height = Math.max(1, component.height);

                if (width >= 48
                    && width <= 220
                    && height >= 28
                    && height <= 380
                    && stats.valueCount >= 2
                    && !stats.hasGraphicsLabel) {
                    out.add(
                        new NativePopupCandidate(
                            component,
                            x,
                            y,
                            stats.valueCount
                        )
                    );
                }

                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collectNativePopupCandidates(
                    components,
                    component.id,
                    childX,
                    childY,
                    out
                );
                if (component.createdComponents != null) {
                    collectNativePopupCandidates(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        out
                    );
                }
            }
        }
    }

    private static void collectPopupTextStats(
        Component[] components,
        int layer,
        PopupTextStats stats,
        int depth
    ) {
        if (components == null || depth > 4) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }

            if (component.type == 4
                && component.text != null
                && component.text.length() > 0) {
                String text = normalizeText(component.text.toString());
                if (!text.isEmpty()) {
                    if (isGraphicsOptionsLabel(text)) {
                        stats.hasGraphicsLabel = true;
                    } else {
                        stats.valueCount++;
                    }
                }
            }

            if (component.type == 0) {
                collectPopupTextStats(
                    components,
                    component.id,
                    stats,
                    depth + 1
                );
                if (component.createdComponents != null) {
                    collectPopupTextStats(
                        component.createdComponents,
                        component.id,
                        stats,
                        depth + 1
                    );
                }
            }
        }
    }

    private static boolean isGraphicsOptionsLabel(String text) {
        return text.equals("graphics options")
            || text.equals("display modes")
            || text.equals("advanced options")
            || text.equals("brightness")
            || text.equals("visible levels")
            || text.equals("remove roofs")
            || text.equals("ground decoration")
            || text.equals("texture detail")
            || text.equals("idle animations")
            || text.equals("flickering effects")
            || text.equals("ground textures")
            || text.equals("character shadows")
            || text.equals("scenery shadows")
            || text.equals("lighting detail")
            || text.equals("water detail")
            || text.equals("fog")
            || text.equals("anti-aliasing")
            || text.equals("modern ui")
            || text.equals("style editor")
            || text.equals("main menu")
            || text.equals("standard detail")
            || text.equals("(small)")
            || text.equals("(fullscreen)")
            || text.startsWith("high detail");
    }

    private static String normalizeText(String text) {
        if (text == null) {
            return "";
        }

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
                continue;
            }
            if (!insideTag) {
                out.append(
                    ch == '\n' || ch == '\r' || ch == '\t'
                        ? ' '
                        : ch
                );
            }
        }

        return out.toString()
            .trim()
            .toLowerCase(java.util.Locale.ROOT)
            .replaceAll("\\s+", " ");
    }

    private static final class PopupTextStats {
        private int valueCount;
        private boolean hasGraphicsLabel;
    }

    private static final class NativePopupCandidate {
        private final Component component;
        private final int x;
        private final int y;
        private final int valueCount;

        private NativePopupCandidate(
            Component component,
            int x,
            int y,
            int valueCount
        ) {
            this.component = component;
            this.x = x;
            this.y = y;
            this.valueCount = valueCount;
        }
    }

    private static void includeSelectorBounds(int x, int y, int width, int height) {
        selectorX = Math.min(selectorX, x);
        selectorY = Math.min(selectorY, y);
        selectorRight = Math.max(selectorRight, x + width);
        selectorBottom = Math.max(selectorBottom, y + height);
        selectorSeen = true;
    }

    private static int selectorWidth() {
        return Math.max(1, selectorRight - selectorX);
    }

    private static int selectorHeight() {
        return Math.max(1, selectorBottom - selectorY);
    }

    private static void drawModernClosedSelector(
        int x,
        int y,
        int width,
        int height
    ) {
        ModernUiImage field = ModernUiAssetResolver.get(
            "controls/dropdown",
            width,
            height
        );
        if (field != null) {
            field.render(x, y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:controls/dropdown",
                x,
                y,
                width,
                height
            );
        }

        ModernUiImage arrow = ModernUiAssetResolver.get("icons/dropdown", 9, 6);
        int arrowX = x + width - 15;
        int arrowY = y + Math.max(4, (height - 6) / 2);
        if (arrow != null) {
            arrow.render(arrowX, arrowY);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:icons/dropdown",
                arrowX,
                arrowY,
                9,
                6
            );
        }

        ModernTrueTypeFont.draw(
            "Yes",
            x + 5,
            y + Math.min(height - 3, 14),
            0xE8DDC4,
            11.0F,
            false
        );
    }

    private static void drawModernPopup(int x, int y, int width, int height) {
        ModernUiImage popup = ModernUiAssetResolver.get("controls/popup", width, height);
        if (popup != null) {
            popup.render(x, y);
        } else {
            ModernUiRenderer.drawMissing("asset:controls/popup", x, y, width, height);
        }
    }

    private static void drawModernChoice(
        String text,
        int x,
        int y,
        int width,
        int height,
        boolean selected
    ) {
        boolean hovered = contains(Mouse.lastMouseX, Mouse.lastMouseY, x, y, width, height);
        String asset = selected
            ? "controls/choice-selected"
            : hovered ? "controls/choice-hover" : null;

        if (asset != null) {
            ModernUiImage row = ModernUiAssetResolver.get(
                asset,
                Math.max(1, width - 4),
                Math.max(1, height - 2)
            );
            if (row != null) {
                row.render(x + 2, y + 1);
            } else {
                ModernUiRenderer.drawMissing(
                    "asset:" + asset,
                    x + 2,
                    y + 1,
                    Math.max(1, width - 4),
                    Math.max(1, height - 2)
                );
            }
        }

        ModernTrueTypeFont.drawCentered(
            text,
            x + width / 2,
            y + Math.min(height - 3, 15),
            selected ? 0xFFF4D1 : 0xE8DDC4,
            12.0F,
            false
        );
    }

    private static void drawNativePopup(int x, int y, int width, int height) {
        fill(x, y, width, height, 0x9B8458);
        outline(x, y, width, height, 0x29251C);
        outline(x + 1, y + 1, width - 2, height - 2, 0x6F5B3B);
        hline(x + 2, y + 2, width - 4, 0xC7B07B);
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
        hline(x + 2, y + height - 1, width - 4, 0x6F5B3B);
        if (Fonts.p12Full != null) {
            Fonts.p12Full.renderCenter(
                JagString.parse(text),
                x + width / 2,
                y + Math.min(height - 3, 15),
                TEXT,
                -1
            );
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

    private static void outline(int x, int y, int width, int height, int color) {
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
}
