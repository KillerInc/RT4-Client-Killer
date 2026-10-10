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

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        if (components == null
            || !GraphicsOptionsUiInjector.isGraphicsOptionsActive(
                components
            )) {
            return;
        }

        ModernGraphicsOptionsLayout layout = layout();
        ModernUiRect clip = new ModernUiRect(
            layout.panelX,
            layout.panelY,
            layout.panelWidth,
            layout.panelHeight
        );

        // Closed dropdowns: bind only the action container. The vanilla
        // component position is irrelevant; the Modern control rectangle is
        // authoritative.
        for (ControlSpec spec : CONTROLS) {
            if (spec.closedChild < 0) {
                continue;
            }
            Component value =
                directChild(components, spec.closedChild);
            Component action =
                value == null
                    ? null
                    : componentById(
                        components,
                        value.overlayer
                    );
            if (action == null) {
                action = value;
            }
            if (action != null) {
                ModernUiInputRouter.bind(
                    action,
                    toModern(layout.control(spec.column, spec.row)),
                    clip
                );
            }

            if (spec.popupBodyChild >= 0
                && isPopupOpen(components, spec.popupBodyChild)) {
                bindPopupRows(
                    components,
                    spec.popupBodyChild,
                    layout.control(spec.column, spec.row),
                    clip
                );
            }
        }

        Component resolutionValue =
            directChild(components, RESOLUTION_VALUE_CHILD);
        Component resolutionAction =
            resolutionValue == null
                ? null
                : componentById(
                    components,
                    resolutionValue.overlayer
                );
        if (resolutionAction == null) {
            resolutionAction = resolutionValue;
        }
        if (resolutionAction != null) {
            ModernUiInputRouter.bind(
                resolutionAction,
                toModern(layout.resolution()),
                clip
            );
        }

        if (isPopupOpen(components, RESOLUTION_POPUP_BODY_CHILD)) {
            bindResolutionPopupRows(
                components,
                layout.resolution(),
                clip
            );
        }

        Component mainMenu =
            findTextComponent(components, "main menu");
        if (mainMenu != null) {
            ModernUiInputRouter.bind(
                mainMenu,
                toModern(layout.mainMenu()),
                clip
            );
        }

        bindDisplayModeActions(
            components,
            parentX,
            parentY,
            layout,
            clip
        );
        bindBrightnessActions(
            components,
            parentX,
            parentY,
            layout.control(0, 0),
            clip
        );
    }

    public static void render(Component[] components) {
        ModernGraphicsOptionsLayout layout = layout();

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
            ModernUiRect rect = layout.control(spec.column, spec.row);
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

        ModernUiRect mainMenu = layout.mainMenu();
        drawButton(mainMenu, false);
        drawCenteredText(
            "Main Menu",
            mainMenu,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        ModernUiRect styleEditor = layout.styleEditor();
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

    public static int closedDropdownParentAt(
        Component[] components,
        int x,
        int y
    ) {
        ModernGraphicsOptionsLayout layout = layout();

        for (ControlSpec spec : CONTROLS) {
            if (spec.closedChild < 0
                || !layout.control(spec.column, spec.row).contains(x, y)) {
                continue;
            }
            Component value =
                directChild(components, spec.closedChild);
            return value == null ? -1 : value.overlayer;
        }

        if (layout.resolution().contains(x, y)) {
            Component value =
                directChild(components, RESOLUTION_VALUE_CHILD);
            return value == null ? -1 : value.overlayer;
        }

        return -1;
    }

    public static boolean isInsideOpenDropdown(
        Component[] components,
        int x,
        int y
    ) {
        ModernGraphicsOptionsLayout layout = layout();

        if (isPopupOpen(components, RESOLUTION_POPUP_BODY_CHILD)) {
            int rows = uniqueResolutionCount();
            ModernUiRect control = layout.resolution();
            ModernUiRect popup = new ModernUiRect(
                control.x,
                control.bottom() - 1,
                control.width,
                Math.max(
                    1,
                    rows * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
                )
            );
            return popup.contains(x, y);
        }

        for (ControlSpec spec : CONTROLS) {
            if (spec.popupBodyChild < 0
                || !isPopupOpen(components, spec.popupBodyChild)) {
                continue;
            }

            int rows =
                popupTextEntries(
                    components,
                    spec.popupBodyChild
                ).size();
            ModernUiRect control =
                layout.control(spec.column, spec.row);
            ModernUiRect popup = new ModernUiRect(
                control.x,
                control.bottom() - 1,
                control.width,
                Math.max(
                    1,
                    rows * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
                )
            );
            return popup.contains(x, y);
        }

        return false;
    }

    private static int uniqueResolutionCount() {
        DisplayMode[] modes = DisplayMode.getDisplayModes();
        if (modes == null) {
            return 0;
        }

        Set<String> seen = new HashSet<>();
        for (DisplayMode mode : modes) {
            if (mode != null) {
                seen.add(mode.width + "x" + mode.height);
            }
        }
        return seen.size();
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

    private static void renderDisplayModes(
        ModernGraphicsOptionsLayout layout,
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
        int buttonY =
            layout.panelY
                + ModernUiMetrics.DISPLAY_BUTTON_Y_OFFSET
                + 36;

        for (int i = 0; i < centers.length; i++) {
            ModernUiRect button = layout.displayModeButton(i);
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

        ModernUiRect resolution = layout.resolution();
        String resolutionValue = valueFor(
            components,
            RESOLUTION_VALUE_CHILD,
            fallbackResolution()
        );
        drawDropdown(resolution, resolutionValue);
    }

    private static void renderOpenNativePopup(
        Component[] components,
        ModernGraphicsOptionsLayout layout
    ) {
        if (isPopupOpen(components, RESOLUTION_POPUP_BODY_CHILD)) {
            ModernUiRect resolution = layout.resolution();
            drawResolutionPopup(
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

            ModernUiRect rect = layout.control(spec.column, spec.row);
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

    private static void renderModernSelectorPopup(ModernGraphicsOptionsLayout layout) {
        if (!ModernUiSettingsOverlay.isDropdownOpen()) {
            return;
        }

        ModernUiRect control = layout.control(4, 2);
        String[] options = {"Off", "On"};
        int popupY = control.y + control.height - 1;
        drawPopupSurface(
            control.x,
            popupY,
            control.width,
            options.length
        );

        String selected = ModernUiManager.isEnabled() ? "On" : "Off";
        for (int i = 0; i < options.length; i++) {
            ModernUiRect row = new ModernUiRect(
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

    private static void drawResolutionPopup(
        ModernUiRect control,
        String selected
    ) {
        DisplayMode[] modes = DisplayMode.getDisplayModes();
        if (modes == null || modes.length == 0) {
            return;
        }

        List<String> options = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (DisplayMode mode : modes) {
            if (mode == null) {
                continue;
            }
            String option = mode.width + " x " + mode.height;
            if (seen.add(option)) {
                options.add(option);
            }
        }
        if (options.isEmpty()) {
            return;
        }

        int rowHeight =
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT;
        int popupY = control.y + control.height - 1;
        int popupHeight = options.size() * rowHeight;

        List<ModernUiRect> rows = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            rows.add(
                new ModernUiRect(
                    control.x,
                    popupY + i * rowHeight,
                    control.width,
                    rowHeight
                )
            );
        }

        drawPopupSurfaceExact(
            control.x,
            popupY,
            control.width,
            popupHeight,
            rows
        );

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            drawPopupRow(
                rows.get(i),
                option,
                normalize(option).equals(normalize(selected))
            );
        }
    }

    private static void drawPopupFromComponent(
        Component[] components,
        int popupBodyChild,
        ModernUiRect control,
        String selected
    ) {
        List<TextEntry> entries =
            popupTextEntries(components, popupBodyChild);
        if (entries.isEmpty()) {
            return;
        }

        int rowHeight =
            ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT;
        int popupY = control.y + control.height - 1;

        drawPopupSurface(
            control.x,
            popupY,
            control.width,
            entries.size()
        );

        for (int i = 0; i < entries.size(); i++) {
            ModernUiRect row = new ModernUiRect(
                control.x,
                popupY + i * rowHeight,
                control.width,
                rowHeight
            );
            String text = entries.get(i).text;
            drawPopupRow(
                row,
                text,
                normalize(text).equals(normalize(selected))
            );
        }
    }

    private static void drawPopupSurface(
        int x,
        int y,
        int width,
        int rowCount
    ) {
        int rows = Math.max(1, rowCount);
        int rowHeight = ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT;
        int height = rows * rowHeight;
        List<ModernUiRect> rowRects = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            rowRects.add(
                new ModernUiRect(
                    x,
                    y + i * rowHeight,
                    width,
                    rowHeight
                )
            );
        }
        drawPopupSurfaceExact(
            x,
            y,
            width,
            height,
            rowRects
        );
    }

    private static void drawPopupSurfaceExact(
        int x,
        int y,
        int width,
        int height,
        List<ModernUiRect> rows
    ) {
        drawAsset(
            "controls/popup",
            x,
            y,
            width,
            Math.max(1, height)
        );

        // Paint every row with an opaque fixed-height surface. This prevents
        // the underlying Graphics Options controls/labels from showing
        // through long dropdowns while keeping the outer popup frame intact.
        for (ModernUiRect row : rows) {
            drawAsset(
                "controls/popup-row",
                x + 1,
                row.y,
                Math.max(1, width - 2),
                Math.max(1, row.height)
            );
        }

        for (int i = 1; i < rows.size(); i++) {
            ModernUiRect row = rows.get(i);
            drawAsset(
                "controls/popup-divider",
                x + 2,
                row.y - 1,
                Math.max(1, width - 4),
                2
            );
        }
    }

    private static void drawPopupRow(
        ModernUiRect row,
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

    private static void drawBrightness(ModernUiRect rect) {
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

    private static void drawDropdown(ModernUiRect rect, String value) {
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

    private static void drawButton(ModernUiRect rect, boolean active) {
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
        ModernUiRect rect,
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
                        component,
                        component.x,
                        y,
                        component.text.toString()
                    )
                );
            }

            collectTextEntries(component.createdComponents, y, out);
        }
    }

    private static void bindPopupRows(
        Component[] components,
        int popupBodyChild,
        ModernUiRect control,
        ModernUiRect clip
    ) {
        List<TextEntry> entries =
            popupTextEntries(components, popupBodyChild);
        if (entries.isEmpty()) {
            return;
        }

        int popupY = control.y + control.height - 1;
        for (int i = 0; i < entries.size(); i++) {
            TextEntry entry = entries.get(i);
            ModernUiInputRouter.bind(
                entry.component,
                new ModernUiRect(
                    control.x,
                    popupY
                        + i * ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT,
                    control.width,
                    ModernUiMetrics.DROPDOWN_POPUP_ROW_HEIGHT
                ),
                clip
            );
        }
    }

    private static void bindResolutionPopupRows(
        Component[] components,
        ModernUiRect control,
        ModernUiRect clip
    ) {
        List<TextEntry> entries =
            popupTextEntries(
                components,
                RESOLUTION_POPUP_BODY_CHILD
            );
        if (entries.isEmpty()) {
            return;
        }

        int popupY = control.y + control.height - 1;
        int row = 0;
        for (DisplayMode mode : DisplayMode.getDisplayModes()) {
            if (mode == null) {
                continue;
            }
            String wanted =
                normalize(mode.width + " x " + mode.height);
            TextEntry backend = null;
            for (TextEntry entry : entries) {
                if (normalize(entry.text).equals(wanted)) {
                    backend = entry;
                    break;
                }
            }
            if (backend == null) {
                continue;
            }

            ModernUiInputRouter.bind(
                backend.component,
                new ModernUiRect(
                    control.x,
                    popupY
                        + row
                            * ModernUiMetrics
                                .DROPDOWN_POPUP_ROW_HEIGHT,
                    control.width,
                    ModernUiMetrics
                        .DROPDOWN_POPUP_ROW_HEIGHT
                ),
                clip
            );
            row++;
        }
    }

    private static void bindDisplayModeActions(
        Component[] components,
        int parentX,
        int parentY,
        ModernGraphicsOptionsLayout layout,
        ModernUiRect clip
    ) {
        List<ComponentEntry> entries = new ArrayList<>();
        collectComponentEntries(
            components,
            -1,
            parentX,
            parentY,
            entries
        );

        ComponentEntry displayLabel =
            findTextEntry(entries, "display modes");
        ComponentEntry advancedLabel =
            findTextEntry(entries, "advanced options");
        if (displayLabel == null || advancedLabel == null) {
            return;
        }

        int bandTop = displayLabel.y + 10;
        int bandBottom = advancedLabel.y - 4;
        List<ComponentEntry> candidates = new ArrayList<>();

        for (ComponentEntry entry : entries) {
            Component component = entry.component;
            if (!isInteractive(component)
                || component.type == 0) {
                continue;
            }
            int width = Math.max(1, component.width);
            int height = Math.max(1, component.height);
            int cy = entry.y + height / 2;
            if (cy < bandTop || cy >= bandBottom
                || width < 20 || width > 180
                || height < 12 || height > 80) {
                continue;
            }
            candidates.add(entry);
        }

        candidates.sort(
            Comparator.comparingInt(
                entry ->
                    entry.x
                        + Math.max(
                            1,
                            entry.component.width
                        ) / 2
            )
        );

        List<ComponentEntry> selected = new ArrayList<>();
        for (ComponentEntry candidate : candidates) {
            int cx =
                candidate.x
                    + Math.max(1, candidate.component.width) / 2;
            if (selected.isEmpty()) {
                selected.add(candidate);
                continue;
            }
            ComponentEntry previous =
                selected.get(selected.size() - 1);
            int previousCx =
                previous.x
                    + Math.max(1, previous.component.width) / 2;
            if (Math.abs(cx - previousCx) > 24) {
                selected.add(candidate);
            }
        }

        if (selected.size() < 4) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            ModernUiInputRouter.bind(
                selected.get(i).component,
                toModern(layout.displayModeButton(i)),
                clip
            );
        }
    }

    private static void bindBrightnessActions(
        Component[] components,
        int parentX,
        int parentY,
        ModernUiRect target,
        ModernUiRect clip
    ) {
        List<ComponentEntry> entries = new ArrayList<>();
        collectComponentEntries(
            components,
            -1,
            parentX,
            parentY,
            entries
        );
        ComponentEntry label =
            findTextEntry(entries, "brightness");
        if (label == null) {
            return;
        }

        List<ComponentEntry> sliderParts = new ArrayList<>();
        for (ComponentEntry entry : entries) {
            Component component = entry.component;
            if (!isInteractive(component)
                || component.type == 0
                || component.width > 60
                || component.height > 50) {
                continue;
            }

            int cx = entry.x + Math.max(1, component.width) / 2;
            int cy = entry.y + Math.max(1, component.height) / 2;
            int labelCx =
                label.x
                    + Math.max(1, label.component.width) / 2;
            if (Math.abs(cx - labelCx) <= 100
                && cy >= label.y + 8
                && cy <= label.y + 55) {
                sliderParts.add(entry);
            }
        }

        if (sliderParts.isEmpty()) {
            return;
        }

        int sourceLeft = Integer.MAX_VALUE;
        int sourceTop = Integer.MAX_VALUE;
        int sourceRight = Integer.MIN_VALUE;
        int sourceBottom = Integer.MIN_VALUE;
        for (ComponentEntry entry : sliderParts) {
            sourceLeft = Math.min(sourceLeft, entry.x);
            sourceTop = Math.min(sourceTop, entry.y);
            sourceRight = Math.max(
                sourceRight,
                entry.x + Math.max(1, entry.component.width)
            );
            sourceBottom = Math.max(
                sourceBottom,
                entry.y + Math.max(1, entry.component.height)
            );
        }

        int sourceWidth = Math.max(1, sourceRight - sourceLeft);
        int sourceHeight = Math.max(1, sourceBottom - sourceTop);

        for (ComponentEntry entry : sliderParts) {
            int x =
                target.x
                    + (entry.x - sourceLeft) * target.width
                        / sourceWidth;
            int y =
                target.y
                    + (entry.y - sourceTop) * target.height
                        / sourceHeight;
            int width = Math.max(
                1,
                Math.max(1, entry.component.width)
                    * target.width / sourceWidth
            );
            int height = Math.max(
                1,
                Math.max(1, entry.component.height)
                    * target.height / sourceHeight
            );
            ModernUiInputRouter.bind(
                entry.component,
                new ModernUiRect(x, y, width, height),
                clip
            );
        }
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

    private static ComponentEntry findTextEntry(
        List<ComponentEntry> entries,
        String text
    ) {
        String wanted = normalize(text);
        for (ComponentEntry entry : entries) {
            Component component = entry.component;
            if (component.type == 4
                && component.text != null
                && normalize(component.text.toString()).equals(wanted)) {
                return entry;
            }
        }
        return null;
    }

    private static Component findTextComponent(
        Component[] components,
        String text
    ) {
        List<ComponentEntry> entries = new ArrayList<>();
        collectComponentEntries(
            components,
            -1,
            0,
            0,
            entries
        );
        ComponentEntry entry = findTextEntry(entries, text);
        return entry == null ? null : entry.component;
    }

    private static boolean isInteractive(Component component) {
        return component != null
            && (component.hasEventHandlers
                || component.clientCode != 0
                || InterfaceList.getServerActiveProperties(
                    component
                ).events != 0);
    }

    private static Component componentById(
        Component[] components,
        int id
    ) {
        if (components == null || id == -1) {
            return null;
        }
        int child = id & 0xFFFF;
        if (child >= 0 && child < components.length) {
            Component direct = components[child];
            if (direct != null && direct.id == id) {
                return direct;
            }
        }
        for (Component component : components) {
            if (component == null) {
                continue;
            }
            if (component.id == id) {
                return component;
            }
            Component nested =
                componentById(component.createdComponents, id);
            if (nested != null) {
                return nested;
            }
        }
        return null;
    }

    private static ModernUiRect toModern(ModernUiRect rect) {
        return rect;
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

    private static ModernGraphicsOptionsLayout layout() {
        return ModernGraphicsOptionsLayout.create(
            GameShell.canvasWidth,
            GameShell.canvasHeight
        );
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
        private final Component component;
        private final int x;
        private final int y;
        private final String text;

        private TextEntry(
            Component component,
            int x,
            int y,
            String text
        ) {
            this.component = component;
            this.x = x;
            this.y = y;
            this.text = text;
        }
    }
}
