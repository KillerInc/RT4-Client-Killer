package rt4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Complete Modern rendering for Graphics Options.
 *
 * RT4 Components remain an invisible state/event backend so the original CS2
 * behavior stays authoritative. No legacy sprite, rectangle or bitmap-font
 * visual is used by this class.
 */
public final class ModernGraphicsOptionsUi {
    private static boolean hitboxesLogged;
    private static final int RESOLUTION_VALUE_CHILD = 283;
    private static final int RESOLUTION_POPUP_BODY_CHILD = 285;

    private static final ControlSpec[] CONTROLS = {
        new ControlSpec("Brightness", 0, 0, -1, -1),
        new ControlSpec("Visible levels", 1, 0, 293, 295),
        new ControlSpec("Remove roofs", 2, 0, 290, 292),
        new ControlSpec("Ground decoration", 3, 0, 287, 289),
        new ControlSpec("Texture detail", 4, 0, 108, 110),

        new ControlSpec("Idle animations", 0, 1, 17, 19),
        new ControlSpec("Flickering effects", 1, 1, 42, 44),
        new ControlSpec("Ground textures", 2, 1, 64, 66),
        new ControlSpec("Character shadows", 3, 1, 86, 88),
        new ControlSpec("Scenery shadows", 4, 1, 100, 102),

        new ControlSpec("Lighting detail", 0, 2, 9, 11),
        new ControlSpec("Water detail", 1, 2, 34, 36),
        new ControlSpec("Fog", 2, 2, 56, 58),
        new ControlSpec("Anti-aliasing", 3, 2, 78, 80),
        new ControlSpec("Modern UI", 4, 2, -2, -2)
    };

    private ModernGraphicsOptionsUi() {
    }

    public static void render(Component[] components) {
        Layout layout = layout();
        registerCanonicalHitboxes(components, layout);

        drawAsset(
            "graphics-options/panel",
            layout.panelX,
            layout.panelY,
            layout.panelWidth,
            layout.panelHeight
        );

        drawAsset(
            "graphics-options/divider",
            layout.panelX + ModernUiMetrics.GRAPHICS_PANEL_INSET,
            layout.panelY + 146,
            layout.panelWidth
                - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );
        drawAsset(
            "graphics-options/divider",
            layout.panelX + ModernUiMetrics.GRAPHICS_PANEL_INSET,
            layout.panelY + 360,
            layout.panelWidth
                - ModernUiMetrics.GRAPHICS_PANEL_INSET * 2,
            4
        );

        drawCenteredLabel(
            "Graphics Options",
            layout.panelX,
            layout.panelY + 22,
            layout.panelWidth,
            22,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_TITLE
        );

        drawLeftLabel(
            "Display modes",
            layout.panelX + 48,
            layout.panelY + 40,
            180,
            18,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_SECTION
        );

        renderDisplayModes(layout, components);

        drawLeftLabel(
            "Advanced options",
            layout.panelX + 48,
            layout.panelY + 160,
            180,
            18,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_SECTION
        );

        for (ControlSpec spec : CONTROLS) {
            Rect rect = layout.control(spec.column, spec.row);
            drawCenteredLabel(
                spec.label,
                rect.x - 8,
                layout.labelY(spec.row),
                rect.width + 16,
                16,
                ModernUiFontRegistry.BOLD_12,
                ModernUiMetrics.FONT_LABEL
            );

            if (spec.closedChild == -1) {
                drawBrightness(rect);
                continue;
            }

            String value;
            if (spec.closedChild == -2) {
                value = ModernUiManager.isEnabled() ? "On" : "Off";
            } else {
                value = valueFor(
                    components,
                    spec.closedChild,
                    fallbackValue(spec.label)
                );
            }

            drawDropdown(rect, value);
        }

        Rect mainMenu = layout.mainMenu();
        drawButton(mainMenu, false);
        drawCenteredText(
            "Main Menu",
            mainMenu,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        Rect styleEditor = layout.styleEditor();
        drawButton(styleEditor, false);
        drawCenteredText(
            "Style Editor",
            styleEditor,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        renderOpenNativePopup(components, layout);
        renderModernSelectorPopup(layout);
    }

    public static int getModernSelectorX() {
        return layout().control(4, 2).x;
    }

    public static int getModernSelectorY() {
        return layout().control(4, 2).y;
    }

    public static int getModernSelectorWidth() {
        return ModernUiMetrics.CONTROL_WIDTH;
    }

    public static int getModernSelectorHeight() {
        return ModernUiMetrics.CONTROL_HEIGHT;
    }

    public static int getStyleEditorX() {
        return layout().styleEditor().x;
    }

    public static int getStyleEditorY() {
        return layout().styleEditor().y;
    }

    public static int getStyleEditorWidth() {
        return layout().styleEditor().width;
    }

    public static int getStyleEditorHeight() {
        return layout().styleEditor().height;
    }

    private static Rect displayModeButton(
        Layout layout,
        int index
    ) {
        int center =
            layout.centerX - 225 + Math.max(0, Math.min(3, index)) * 150;
        int buttonY =
            layout.panelY
                + ModernUiMetrics.DISPLAY_BUTTON_Y_OFFSET
                + 36;
        return new Rect(
            ModernUiMetrics.centeredX(
                center,
                ModernUiMetrics.DISPLAY_BUTTON_WIDTH
            ),
            buttonY,
            ModernUiMetrics.DISPLAY_BUTTON_WIDTH,
            ModernUiMetrics.DISPLAY_BUTTON_HEIGHT
        );
    }

    private static Rect resolutionControl(Layout layout) {
        int fullscreenCenter = layout.centerX + 225;
        return new Rect(
            ModernUiMetrics.centeredX(
                fullscreenCenter,
                ModernUiMetrics.CONTROL_WIDTH
            ),
            layout.panelY + 128,
            ModernUiMetrics.CONTROL_WIDTH,
            ModernUiMetrics.CONTROL_HEIGHT
        );
    }

    private static void registerCanonicalHitboxes(
        Component[] components,
        Layout layout
    ) {
        if (components == null) {
            return;
        }

        List<ComponentEntry> entries = new ArrayList<>();
        collectComponentEntries(
            components,
            -1,
            0,
            0,
            entries
        );

        int clipLeft = Math.max(0, layout.panelX);
        int clipTop = Math.max(0, layout.panelY);
        int clipRight = Math.min(
            GameShell.canvasWidth,
            layout.panelX + layout.panelWidth
        );
        int clipBottom = Math.min(
            GameShell.canvasHeight,
            layout.panelY + layout.panelHeight
        );

        int registered = 0;

        // Every advanced dropdown is bound directly to the Rect used to draw
        // it. The old cache component remains only the CS2 action backend.
        for (ControlSpec spec : CONTROLS) {
            if (spec.closedChild < 0) {
                continue;
            }

            ComponentEntry closed =
                findEntryByChild(entries, spec.closedChild);
            Rect control = layout.control(spec.column, spec.row);
            if (closed != null) {
                registerEntry(
                    closed,
                    control,
                    clipLeft,
                    clipTop,
                    clipRight,
                    clipBottom
                );
                registered++;
            }

            if (spec.popupBodyChild >= 0) {
                registered += registerPopupHitboxes(
                    components,
                    entries,
                    spec.popupBodyChild,
                    control,
                    clipLeft,
                    clipTop,
                    clipRight,
                    clipBottom
                );
            }
        }

        Rect resolution = resolutionControl(layout);
        ComponentEntry resolutionClosed =
            findEntryByChild(entries, RESOLUTION_VALUE_CHILD);
        if (resolutionClosed != null) {
            registerEntry(
                resolutionClosed,
                resolution,
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            registered++;
        }
        registered += registerPopupHitboxes(
            components,
            entries,
            RESOLUTION_POPUP_BODY_CHILD,
            resolution,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom
        );

        // The injected Modern selector and Style Editor are already processed
        // by ModernUiSettingsOverlay using these same Rects. Register their
        // component backends too so no stale native region remains active.
        for (ComponentEntry entry : entries) {
            if (entry.component.clientCode
                == GraphicsOptionsUiInjector.CLIENT_CODE_SELECTOR_HIT) {
                registerEntry(
                    entry,
                    layout.control(4, 2),
                    clipLeft,
                    clipTop,
                    clipRight,
                    clipBottom
                );
                registered++;
            } else if (entry.component.clientCode
                == GraphicsOptionsUiInjector.CLIENT_CODE_STYLE_TEXT) {
                registerEntry(
                    entry,
                    layout.styleEditor(),
                    clipLeft,
                    clipTop,
                    clipRight,
                    clipBottom
                );
                registered++;
            }
        }

        registered += registerDisplayModeHitboxes(
            entries,
            layout,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom
        );

        registered += registerBrightnessHitbox(
            entries,
            layout,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom
        );

        ComponentEntry mainMenuLabel =
            findTextEntry(entries, "main menu");
        ComponentEntry mainMenuTarget =
            nearestInteractiveEntry(
                entries,
                mainMenuLabel,
                180,
                70
            );
        if (mainMenuTarget != null) {
            registerEntry(
                mainMenuTarget,
                layout.mainMenu(),
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            registered++;
        }

        if (!hitboxesLogged) {
            hitboxesLogged = true;
            DisplayDebug.log(
                "MODERN_UI Graphics Options canonical hitboxes active"
                    + " controls=" + registered
            );
        }
    }

    private static int registerPopupHitboxes(
        Component[] components,
        List<ComponentEntry> entries,
        int popupBodyChild,
        Rect control,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        Component body = directChild(components, popupBodyChild);
        if (body == null || InterfaceList.isHidden(body)) {
            return 0;
        }

        List<TextEntry> popupEntries =
            popupTextEntries(components, popupBodyChild);
        if (popupEntries.isEmpty()) {
            return 0;
        }

        int popupHeight =
            popupEntries.size()
                * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT;
        Rect popup = new Rect(
            control.x,
            control.y + control.height - 1,
            control.width,
            popupHeight
        );

        int count = 0;
        ComponentEntry bodyEntry =
            findEntryByChild(entries, popupBodyChild);
        if (bodyEntry != null) {
            registerEntry(
                bodyEntry,
                popup,
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            count++;
        }

        ComponentEntry hitEntry =
            findEntryByChild(entries, popupBodyChild - 1);
        if (hitEntry != null) {
            registerEntry(
                hitEntry,
                popup,
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            count++;
        }
        return count;
    }

    private static int registerDisplayModeHitboxes(
        List<ComponentEntry> entries,
        Layout layout,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        ComponentEntry displayLabel =
            findTextEntry(entries, "display modes");
        ComponentEntry advancedLabel =
            findTextEntry(entries, "advanced options");
        if (displayLabel == null || advancedLabel == null) {
            return 0;
        }

        int bandTop = displayLabel.y + 12;
        int bandBottom = advancedLabel.y - 4;
        List<ComponentEntry> candidates = new ArrayList<>();

        for (ComponentEntry entry : entries) {
            Component component = entry.component;
            if (!isInteractive(component)) {
                continue;
            }

            int width = Math.max(1, component.width);
            int height = Math.max(1, component.height);
            int centerY = entry.y + height / 2;
            if (centerY < bandTop || centerY >= bandBottom
                || width < 20 || width > 190
                || height < 12 || height > 80) {
                continue;
            }

            int child = component.id & 0xFFFF;
            if (child == RESOLUTION_VALUE_CHILD
                || child == RESOLUTION_POPUP_BODY_CHILD
                || child == RESOLUTION_POPUP_BODY_CHILD - 1) {
                continue;
            }

            candidates.add(entry);
        }

        candidates.sort(
            Comparator.comparingInt(
                entry -> entry.x + Math.max(1, entry.component.width) / 2
            )
        );

        List<ComponentEntry> selected = new ArrayList<>();
        for (ComponentEntry candidate : candidates) {
            int center =
                candidate.x + Math.max(1, candidate.component.width) / 2;
            if (selected.isEmpty()) {
                selected.add(candidate);
                continue;
            }

            ComponentEntry previous = selected.get(selected.size() - 1);
            int previousCenter =
                previous.x + Math.max(1, previous.component.width) / 2;
            if (Math.abs(center - previousCenter) <= 24) {
                int area =
                    Math.max(1, candidate.component.width)
                        * Math.max(1, candidate.component.height);
                int previousArea =
                    Math.max(1, previous.component.width)
                        * Math.max(1, previous.component.height);
                if (area > previousArea) {
                    selected.set(selected.size() - 1, candidate);
                }
            } else {
                selected.add(candidate);
            }
        }

        if (selected.size() > 4) {
            // Choose the four candidates closest to the Modern display-mode
            // centers, preserving left-to-right order.
            List<ComponentEntry> matched = new ArrayList<>();
            Set<ComponentEntry> used = new HashSet<>();
            for (int i = 0; i < 4; i++) {
                Rect target = displayModeButton(layout, i);
                int targetCenter = target.x + target.width / 2;
                ComponentEntry best = null;
                int bestDistance = Integer.MAX_VALUE;
                for (ComponentEntry candidate : selected) {
                    if (used.contains(candidate)) {
                        continue;
                    }
                    int center =
                        candidate.x
                            + Math.max(1, candidate.component.width) / 2;
                    int distance = Math.abs(center - targetCenter);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        best = candidate;
                    }
                }
                if (best != null) {
                    used.add(best);
                    matched.add(best);
                }
            }
            matched.sort(
                Comparator.comparingInt(
                    entry -> entry.x
                        + Math.max(1, entry.component.width) / 2
                )
            );
            selected = matched;
        }

        if (selected.size() < 4) {
            return 0;
        }

        int count = 0;
        selected.sort(
            Comparator.comparingInt(
                entry -> entry.x + Math.max(1, entry.component.width) / 2
            )
        );
        for (int i = 0; i < 4; i++) {
            registerEntry(
                selected.get(i),
                displayModeButton(layout, i),
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            );
            count++;
        }
        return count;
    }

    private static int registerBrightnessHitbox(
        List<ComponentEntry> entries,
        Layout layout,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        ComponentEntry label = findTextEntry(entries, "brightness");
        if (label == null) {
            return 0;
        }

        int labelCenter =
            label.x + Math.max(1, label.component.width) / 2;
        ComponentEntry best = null;
        int bestScore = Integer.MAX_VALUE;
        for (ComponentEntry entry : entries) {
            if (!isInteractive(entry.component)) {
                continue;
            }
            int width = Math.max(1, entry.component.width);
            int height = Math.max(1, entry.component.height);
            if (width > 220 || height > 70) {
                continue;
            }
            int centerX = entry.x + width / 2;
            int centerY = entry.y + height / 2;
            int dy = centerY - (label.y + 28);
            if (dy < -12 || dy > 38) {
                continue;
            }
            int dx = Math.abs(centerX - labelCenter);
            if (dx > 100) {
                continue;
            }
            int score = dx * 2 + Math.abs(dy);
            if (score < bestScore) {
                bestScore = score;
                best = entry;
            }
        }

        if (best == null) {
            return 0;
        }

        registerEntry(
            best,
            layout.control(0, 0),
            clipLeft,
            clipTop,
            clipRight,
            clipBottom
        );
        return 1;
    }

    private static void registerEntry(
        ComponentEntry source,
        Rect target,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (source == null || target == null) {
            return;
        }
        ModernUiHitboxRegistry.registerAbsolute(
            source.component,
            source.x,
            source.y,
            target.x,
            target.y,
            target.width,
            target.height,
            clipLeft,
            clipTop,
            clipRight,
            clipBottom
        );
    }

    private static boolean isInteractive(Component component) {
        if (component == null) {
            return false;
        }
        return component.hasEventHandlers
            || component.clientCode != 0
            || InterfaceList.getServerActiveProperties(component).events != 0;
    }

    private static ComponentEntry findEntryByChild(
        List<ComponentEntry> entries,
        int childId
    ) {
        for (ComponentEntry entry : entries) {
            if ((entry.component.id & 0xFFFF) == childId) {
                return entry;
            }
        }
        return null;
    }

    private static ComponentEntry findTextEntry(
        List<ComponentEntry> entries,
        String wanted
    ) {
        String target = normalize(wanted);
        for (ComponentEntry entry : entries) {
            Component component = entry.component;
            if (component.type != 4
                || component.text == null
                || component.text.length() == 0) {
                continue;
            }
            if (normalize(component.text.toString()).equals(target)) {
                return entry;
            }
        }
        return null;
    }

    private static ComponentEntry nearestInteractiveEntry(
        List<ComponentEntry> entries,
        ComponentEntry anchor,
        int maxDx,
        int maxDy
    ) {
        if (anchor == null) {
            return null;
        }
        if (isInteractive(anchor.component)) {
            return anchor;
        }

        int anchorX =
            anchor.x + Math.max(1, anchor.component.width) / 2;
        int anchorY =
            anchor.y + Math.max(1, anchor.component.height) / 2;
        ComponentEntry best = null;
        int bestScore = Integer.MAX_VALUE;

        for (ComponentEntry entry : entries) {
            if (!isInteractive(entry.component)) {
                continue;
            }
            int width = Math.max(1, entry.component.width);
            int height = Math.max(1, entry.component.height);
            if (width > 260 || height > 100) {
                continue;
            }
            int centerX = entry.x + width / 2;
            int centerY = entry.y + height / 2;
            int dx = Math.abs(centerX - anchorX);
            int dy = Math.abs(centerY - anchorY);
            if (dx > maxDx || dy > maxDy) {
                continue;
            }
            int score = dx + dy * 2;
            if (score < bestScore) {
                bestScore = score;
                best = entry;
            }
        }
        return best;
    }

    private static void collectComponentEntries(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<ComponentEntry> out
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
            out.add(new ComponentEntry(component, x, y));

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collectComponentEntries(
                    components,
                    component.id,
                    childX,
                    childY,
                    out
                );
                if (component.createdComponents != null) {
                    collectComponentEntries(
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

    private static void renderDisplayModes(
        Layout layout,
        Component[] components
    ) {
        int[] centers = {
            layout.centerX - 225,
            layout.centerX - 75,
            layout.centerX + 75,
            layout.centerX + 225
        };
        String[] labels = {"SD", "HD", "HD", "HD"};
        String[] details = {
            "Standard detail",
            "High detail\n(small)",
            "High detail",
            "High detail\n(fullscreen)"
        };

        int active = DisplayMode.getWindowMode();

        for (int i = 0; i < centers.length; i++) {
            Rect button = displayModeButton(layout, i);
            drawButton(button, active == i);
            drawCenteredText(
                labels[i],
                button,
                ModernUiFontRegistry.BOLD_12,
                ModernUiMetrics.FONT_BUTTON,
                active == i
                    ? ModernUiMetrics.TEXT_ACCENT
                    : ModernUiMetrics.TEXT_PRIMARY,
                true
            );

            drawCenteredLabel(
                details[i],
                centers[i] - 65,
                layout.panelY + 96,
                130,
                34,
                ModernUiFontRegistry.PLAIN_11,
                ModernUiMetrics.FONT_CONTROL
            );
        }

        Rect resolution = resolutionControl(layout);
        String resolutionValue = valueFor(
            components,
            RESOLUTION_VALUE_CHILD,
            fallbackResolution()
        );
        drawDropdown(resolution, resolutionValue);
    }

    private static void renderOpenNativePopup(
        Component[] components,
        Layout layout
    ) {
        if (isPopupOpen(components, RESOLUTION_POPUP_BODY_CHILD)) {
            Rect resolution = resolutionControl(layout);
            drawPopupFromComponent(
                components,
                RESOLUTION_POPUP_BODY_CHILD,
                resolution,
                valueFor(
                    components,
                    RESOLUTION_VALUE_CHILD,
                    fallbackResolution()
                )
            );
            return;
        }

        for (ControlSpec spec : CONTROLS) {
            if (spec.popupBodyChild < 0
                || !isPopupOpen(components, spec.popupBodyChild)) {
                continue;
            }

            Rect rect = layout.control(spec.column, spec.row);
            String selected = valueFor(
                components,
                spec.closedChild,
                fallbackValue(spec.label)
            );
            drawPopupFromComponent(
                components,
                spec.popupBodyChild,
                rect,
                selected
            );
            return;
        }
    }

    private static void renderModernSelectorPopup(Layout layout) {
        if (!ModernUiSettingsOverlay.isDropdownOpen()) {
            return;
        }

        Rect control = layout.control(4, 2);
        String[] options = {"Off", "On"};
        int popupY = control.y + control.height - 1;
        int popupHeight =
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT * options.length;

        drawAsset(
            "controls/popup",
            control.x,
            popupY,
            control.width,
            popupHeight
        );

        String selected = ModernUiManager.isEnabled() ? "On" : "Off";
        for (int i = 0; i < options.length; i++) {
            Rect row = new Rect(
                control.x,
                popupY
                    + i * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT,
                control.width,
                ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
            );
            drawPopupRow(
                row,
                options[i],
                options[i].equals(selected)
            );
        }
    }

    private static void drawPopupFromComponent(
        Component[] components,
        int popupBodyChild,
        Rect control,
        String selected
    ) {
        List<TextEntry> entries =
            popupTextEntries(components, popupBodyChild);
        if (entries.isEmpty()) {
            return;
        }

        int popupY = control.y + control.height - 1;
        int popupHeight =
            entries.size() * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT;
        drawAsset(
            "controls/popup",
            control.x,
            popupY,
            control.width,
            popupHeight
        );

        for (int i = 0; i < entries.size(); i++) {
            Rect row = new Rect(
                control.x,
                popupY
                    + i * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT,
                control.width,
                ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
            );
            String text = entries.get(i).text;
            drawPopupRow(
                row,
                text,
                normalize(text).equals(normalize(selected))
            );
        }
    }

    private static void drawPopupRow(
        Rect row,
        String text,
        boolean selected
    ) {
        boolean hovered =
            Mouse.lastMouseX >= row.x
                && Mouse.lastMouseX < row.x + row.width
                && Mouse.lastMouseY >= row.y
                && Mouse.lastMouseY < row.y + row.height;

        if (selected || hovered) {
            drawAsset(
                selected
                    ? "controls/choice-selected"
                    : "controls/choice-hover",
                row.x + 2,
                row.y + 1,
                Math.max(1, row.width - 4),
                Math.max(1, row.height - 2)
            );
        }

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            text,
            row.x + ModernUiMetrics.CONTROL_TEXT_PAD_X,
            row.y,
            Math.max(
                1,
                row.width
                    - ModernUiMetrics.CONTROL_TEXT_PAD_X * 2
            ),
            row.height,
            selected
                ? ModernUiMetrics.TEXT_ACCENT
                : ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_DROPDOWN,
            false
        );
    }

    private static void drawBrightness(Rect rect) {
        ModernUiImage track = ModernUiAssetResolver.get(
            "controls/slider-track",
            rect.width,
            ModernUiMetrics.SLIDER_HEIGHT
        );
        int trackY =
            rect.y
                + (rect.height - ModernUiMetrics.SLIDER_HEIGHT) / 2;
        if (track != null) {
            track.render(rect.x, trackY);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:controls/slider-track",
                rect.x,
                trackY,
                rect.width,
                ModernUiMetrics.SLIDER_HEIGHT
            );
        }

        int selected = Preferences.brightness;
        if (selected < 1) {
            selected = 1;
        } else if (selected > 4) {
            selected = 4;
        }

        int left = rect.x + 10;
        int right = rect.x + rect.width - 10;
        int knobX =
            left
                + (right - left) * (selected - 1) / 3
                - ModernUiMetrics.SLIDER_KNOB_WIDTH / 2;
        int knobY =
            rect.y
                + (rect.height
                    - ModernUiMetrics.SLIDER_KNOB_HEIGHT) / 2;

        drawAsset(
            "controls/slider-knob",
            knobX,
            knobY,
            ModernUiMetrics.SLIDER_KNOB_WIDTH,
            ModernUiMetrics.SLIDER_KNOB_HEIGHT
        );
    }

    private static void drawDropdown(Rect rect, String value) {
        drawAsset(
            "controls/dropdown",
            rect.x,
            rect.y,
            rect.width,
            rect.height
        );

        int arrowX =
            rect.x + rect.width
                - ModernUiMetrics.DROPDOWN_ARROW_WIDTH - 6;
        int arrowY =
            rect.y
                + (rect.height
                    - ModernUiMetrics.DROPDOWN_ARROW_HEIGHT) / 2;
        drawAsset(
            "icons/dropdown",
            arrowX,
            arrowY,
            ModernUiMetrics.DROPDOWN_ARROW_WIDTH,
            ModernUiMetrics.DROPDOWN_ARROW_HEIGHT
        );

        ModernTrueTypeFont.drawInBox(
            ModernUiFontRegistry.PLAIN_12,
            value == null ? "" : value,
            rect.x + ModernUiMetrics.CONTROL_TEXT_PAD_X,
            rect.y,
            Math.max(
                1,
                rect.width
                    - ModernUiMetrics.CONTROL_ARROW_RESERVED
                    - ModernUiMetrics.CONTROL_TEXT_PAD_X
            ),
            rect.height,
            ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            ModernUiMetrics.FONT_DROPDOWN,
            false
        );
    }

    private static void drawButton(Rect rect, boolean active) {
        drawAsset(
            active ? "controls/button-active" : "controls/button",
            rect.x,
            rect.y,
            rect.width,
            rect.height
        );
    }

    private static void drawCenteredText(
        String text,
        Rect rect,
        String font,
        float size,
        int color,
        boolean shadow
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text,
            rect.x,
            rect.y,
            rect.width,
            rect.height,
            color,
            1,
            1,
            size,
            shadow
        );
    }

    private static void drawCenteredLabel(
        String text,
        int x,
        int y,
        int width,
        int height,
        String font,
        float size
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text,
            x,
            y,
            width,
            height,
            ModernUiMetrics.TEXT_PRIMARY,
            1,
            1,
            size,
            false
        );
    }

    private static void drawLeftLabel(
        String text,
        int x,
        int y,
        int width,
        int height,
        String font,
        float size
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text,
            x,
            y,
            width,
            height,
            ModernUiMetrics.TEXT_PRIMARY,
            0,
            1,
            size,
            false
        );
    }

    private static void drawAsset(
        String asset,
        int x,
        int y,
        int width,
        int height
    ) {
        ModernUiImage image = ModernUiAssetResolver.get(
            asset,
            Math.max(1, width),
            Math.max(1, height)
        );
        if (image != null) {
            image.render(x, y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + asset,
                x,
                y,
                Math.max(1, width),
                Math.max(1, height)
            );
        }
    }

    private static boolean isPopupOpen(
        Component[] components,
        int childId
    ) {
        Component component = directChild(components, childId);
        return component != null && !InterfaceList.isHidden(component);
    }

    private static String valueFor(
        Component[] components,
        int childId,
        String fallback
    ) {
        String value = findTextByChildId(components, childId);
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }

    private static String findTextByChildId(
        Component[] components,
        int childId
    ) {
        if (components == null) {
            return null;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if ((component.id & 0xFFFF) == childId
                && component.type == 4
                && component.text != null
                && component.text.length() > 0) {
                return component.text.toString();
            }

            String nested =
                findTextByChildId(component.createdComponents, childId);
            if (nested != null && !nested.trim().isEmpty()) {
                return nested;
            }
        }
        return null;
    }

    private static List<TextEntry> popupTextEntries(
        Component[] components,
        int popupBodyChild
    ) {
        Component body = directChild(components, popupBodyChild);
        List<TextEntry> entries = new ArrayList<>();
        if (body == null) {
            return entries;
        }

        collectTextEntries(body.createdComponents, 0, entries);
        entries.sort(
            Comparator.comparingInt((TextEntry entry) -> entry.y)
                .thenComparingInt(entry -> entry.x)
        );

        List<TextEntry> unique = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (TextEntry entry : entries) {
            String normalized = normalize(entry.text);
            if (!normalized.isEmpty() && seen.add(normalized)) {
                unique.add(entry);
            }
        }
        return unique;
    }

    private static void collectTextEntries(
        Component[] components,
        int parentY,
        List<TextEntry> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            int y = parentY + component.y;
            if (component.type == 4
                && component.text != null
                && component.text.length() > 0) {
                out.add(
                    new TextEntry(
                        component.x,
                        y,
                        component.text.toString()
                    )
                );
            }

            collectTextEntries(component.createdComponents, y, out);
        }
    }

    private static Component directChild(
        Component[] components,
        int childId
    ) {
        if (components == null
            || childId < 0
            || childId >= components.length) {
            return null;
        }
        Component component = components[childId];
        if (component == null
            || (component.id & 0xFFFF) != childId) {
            return null;
        }
        return component;
    }

    private static String fallbackValue(String label) {
        if (label.equals("Visible levels")) {
            return Preferences.allLevelsVisible
                ? "Always 'All'"
                : "Normal";
        }
        if (label.equals("Remove roofs")) {
            return Preferences.removeRoofsSelectively
                ? "Selectively"
                : "Always";
        }
        if (label.equals("Ground decoration")) {
            return Preferences.showGroundDecorations ? "On" : "Off";
        }
        if (label.equals("Texture detail")) {
            return Preferences.highDetailTextures ? "High" : "Low";
        }
        if (label.equals("Idle animations")) {
            return Preferences.manyIdleAnimations ? "Many" : "Few";
        }
        if (label.equals("Flickering effects")) {
            return Preferences.flickeringEffectsOn ? "On" : "Off";
        }
        if (label.equals("Ground textures")) {
            return Preferences.manyGroundTextures
                ? "Always 'Many'"
                : "Few";
        }
        if (label.equals("Character shadows")) {
            return Preferences.characterShadowsOn ? "On" : "Off";
        }
        if (label.equals("Scenery shadows")) {
            if (Preferences.sceneryShadowsType >= 2) {
                return "Dynamic";
            }
            if (Preferences.sceneryShadowsType == 1) {
                return "Static";
            }
            return "Off";
        }
        if (label.equals("Lighting detail")) {
            return Preferences.highDetailLighting ? "High" : "Low";
        }
        if (label.equals("Water detail")) {
            return Preferences.highWaterDetail ? "High" : "Low";
        }
        if (label.equals("Fog")) {
            return Preferences.fogEnabled ? "On" : "Off";
        }
        if (label.equals("Anti-aliasing")) {
            return Preferences.antiAliasingMode <= 0
                ? "Off"
                : Preferences.antiAliasingMode + "x";
        }
        return "";
    }

    private static String fallbackResolution() {
        int width = Preferences.fullScreenWidth;
        int height = Preferences.fullScreenHeight;
        if (width <= 0 || height <= 0) {
            width = GameShell.canvasWidth;
            height = GameShell.canvasHeight;
        }
        return width + " x " + height;
    }

    private static String normalize(String text) {
        return text == null
            ? ""
            : text.replace((char) 96, (char) 39)
                .trim()
                .toLowerCase(java.util.Locale.ROOT);
    }

    private static Layout layout() {
        int panelWidth = ModernUiMetrics.GRAPHICS_PANEL_WIDTH;
        int panelHeight = ModernUiMetrics.GRAPHICS_PANEL_HEIGHT;
        int panelX = Math.max(
            8,
            (GameShell.canvasWidth - panelWidth) / 2
        );
        int panelY = Math.max(
            8,
            (GameShell.canvasHeight - panelHeight) / 2
        );
        return new Layout(panelX, panelY, panelWidth, panelHeight);
    }

    private static final class Layout {
        private final int panelX;
        private final int panelY;
        private final int panelWidth;
        private final int panelHeight;
        private final int centerX;

        private Layout(
            int panelX,
            int panelY,
            int panelWidth,
            int panelHeight
        ) {
            this.panelX = panelX;
            this.panelY = panelY;
            this.panelWidth = panelWidth;
            this.panelHeight = panelHeight;
            this.centerX = panelX + panelWidth / 2;
        }

        private Rect control(int column, int row) {
            int center =
                centerX
                    + ModernUiMetrics.ADVANCED_FIRST_COLUMN_OFFSET
                    + column
                        * ModernUiMetrics.ADVANCED_COLUMN_SPACING;
            return new Rect(
                ModernUiMetrics.centeredX(
                    center,
                    ModernUiMetrics.CONTROL_WIDTH
                ),
                panelY
                    + ModernUiMetrics.ADVANCED_CONTROL_Y_OFFSET
                    + row
                        * ModernUiMetrics.ADVANCED_ROW_SPACING,
                ModernUiMetrics.CONTROL_WIDTH,
                ModernUiMetrics.CONTROL_HEIGHT
            );
        }

        private int labelY(int row) {
            return panelY
                + ModernUiMetrics.ADVANCED_LABEL_Y_OFFSET
                + row
                    * ModernUiMetrics.ADVANCED_ROW_SPACING;
        }

        private Rect mainMenu() {
            return new Rect(
                ModernUiMetrics.centeredX(
                    centerX,
                    ModernUiMetrics.NAV_BUTTON_WIDTH
                ),
                panelY + ModernUiMetrics.NAV_BUTTON_Y_OFFSET,
                ModernUiMetrics.NAV_BUTTON_WIDTH,
                ModernUiMetrics.NAV_BUTTON_HEIGHT
            );
        }

        private Rect styleEditor() {
            return new Rect(
                panelX + panelWidth
                    - ModernUiMetrics.GRAPHICS_PANEL_INSET
                    - ModernUiMetrics.CONTROL_WIDTH,
                panelY + ModernUiMetrics.NAV_BUTTON_Y_OFFSET,
                ModernUiMetrics.CONTROL_WIDTH,
                ModernUiMetrics.NAV_BUTTON_HEIGHT
            );
        }
    }

    private static final class Rect {
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private Rect(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    private static final class ControlSpec {
        private final String label;
        private final int column;
        private final int row;
        private final int closedChild;
        private final int popupBodyChild;

        private ControlSpec(
            String label,
            int column,
            int row,
            int closedChild,
            int popupBodyChild
        ) {
            this.label = label;
            this.column = column;
            this.row = row;
            this.closedChild = closedChild;
            this.popupBodyChild = popupBodyChild;
        }
    }

    private static final class ComponentEntry {
        private final Component component;
        private final int x;
        private final int y;

        private ComponentEntry(Component component, int x, int y) {
            this.component = component;
            this.x = x;
            this.y = y;
        }
    }

    private static final class TextEntry {
        private final int x;
        private final int y;
        private final String text;

        private TextEntry(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }
    }
}
