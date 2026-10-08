package rt4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Adds the Modern UI selector to the actual Graphics Options component tree.
 *
 * Anti-aliasing is read-only reference material. We calculate its final
 * rendered geometry, clone its label/value/sprite pieces, then re-parent those
 * copies to the interface root at equivalent absolute coordinates in the
 * unused column. The original AA components are never changed.
 */
public final class GraphicsOptionsUiInjector {
    public static final int CLIENT_CODE_SELECTOR_HIT = 1901;
    public static final int CLIENT_CODE_STYLE_HIT = 1902;
    public static final int CLIENT_CODE_VALUE_TEXT = 1903;
    public static final int CLIENT_CODE_STYLE_TEXT = 1904;
    public static final int CLIENT_CODE_SELECTOR_PIECE = 1905;
    public static final int CLIENT_CODE_LABEL_TEXT = 1906;

    private static final int COLUMN_DELTA_X = 130;
    private static final int STYLE_DELTA_Y = 30;

    private static int activeGraphicsOptionsInterfaceId = -1;
    private static final Set<Integer> previousVisibleDropdownGroups =
        new HashSet<>();
    private static final Set<Integer> suppressedNativeDropdownComponents =
        new HashSet<>();
    private static int lastVisibleDropdownGroup = -1;
    private static boolean nativeDropdownOpen;

    private GraphicsOptionsUiInjector() {
    }

    public static void inject(int interfaceId) {
        Component[] original = InterfaceList.components[interfaceId];
        if (original == null || original.length == 0) {
            return;
        }

        List<LayoutEntry> entries = new ArrayList<>();
        collectVisibleLayout(original, -1, 0, 0, entries);

        boolean active = hasGraphicsOptionsSignature(entries);
        if (!active) {
            if (activeGraphicsOptionsInterfaceId == interfaceId) {
                activeGraphicsOptionsInterfaceId = -1;
                suppressedNativeDropdownComponents.clear();
                nativeDropdownOpen = false;
            }
            if (alreadyInjected(original)) {
                InterfaceList.components[interfaceId] = removeInjected(original);
            }
            return;
        }

        activeGraphicsOptionsInterfaceId = interfaceId;

        // Modern UI is a scalable renderer and is only exposed for the two
        // scalable HD display modes: resizable HD and fullscreen HD.
        if (!ModernUiManager.isSupportedDisplayMode()) {
            if (alreadyInjected(original)) {
                InterfaceList.components[interfaceId] = removeInjected(original);
                DisplayDebug.log(
                    "MODERN_UI removed Graphics Options selector for display mode="
                        + DisplayMode.getWindowMode()
                );
            }
            return;
        }

        if (alreadyInjected(original)) {
            return;
        }

        LayoutEntry graphicsTitle = findText(entries, "graphics options");
        LayoutEntry antiLabel = findText(entries, "anti-alias");
        if (graphicsTitle == null || antiLabel == null) {
            return;
        }

        int aaCenterX = antiLabel.x + Math.max(1, antiLabel.component.width) / 2;
        int aaLabelY = antiLabel.y;

        List<LayoutEntry> selectorSprites = new ArrayList<>();
        LayoutEntry valueText = null;

        // First find the visible AA value text. This is the most reliable
        // vertical anchor for the CLOSED selector row.
        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component == antiLabel.component || component.type != 4) {
                continue;
            }

            int width = Math.max(1, component.width);
            int centerX = entry.x + width / 2;
            int dx = Math.abs(centerX - aaCenterX);
            int dy = entry.y - aaLabelY;

            if (dx <= 72
                && dy >= 5 && dy <= 44
                && component.text != null
                && component.text.length() > 0) {
                if (valueText == null
                    || Math.abs(entry.y - (aaLabelY + 18))
                        < Math.abs(valueText.y - (aaLabelY + 18))) {
                    valueText = entry;
                }
            }
        }

        if (valueText != null) {
            int valueHeight = Math.max(1, valueText.component.height);
            int rowCenterY = valueText.y + valueHeight / 2;

            // Copy only the sprites that actually belong to the CLOSED AA
            // selector row. They must be vertically centered on the value
            // text and fully contained in that row band. This deliberately
            // rejects the stray decorative/popup pieces that were being
            // cloned underneath the Modern UI selector.
            for (LayoutEntry entry : entries) {
                Component component = entry.component;
                if (component.type != 5
                    || component.width <= 0 || component.height <= 0
                    || component.width > 180 || component.height > 24) {
                    continue;
                }

                int centerX = entry.x + Math.max(1, component.width) / 2;
                int dx = Math.abs(centerX - aaCenterX);

                int spriteCenterY = entry.y + component.height / 2;
                int centerDy = Math.abs(spriteCenterY - rowCenterY);

                int allowedTop = valueText.y - 6;
                int allowedBottom = valueText.y + Math.max(18, valueHeight) + 6;
                int spriteBottom = entry.y + component.height;

                if (dx <= 78
                    && centerDy <= 7
                    && entry.y >= allowedTop
                    && spriteBottom <= allowedBottom) {
                    selectorSprites.add(entry);
                }
            }
        }

        if (selectorSprites.isEmpty() || valueText == null) {
            DisplayDebug.log(
                "MODERN_UI Graphics Options clone source incomplete"
                    + " interface=" + interfaceId
                    + ", sprites=" + selectorSprites.size()
                    + ", valueText=" + (valueText != null)
            );
            logNearby(entries, aaCenterX, aaLabelY);
            return;
        }

        selectorSprites.sort(
            Comparator.comparingInt((LayoutEntry e) -> e.y)
                .thenComparingInt(e -> e.x)
        );

        List<Component> added = new ArrayList<>();

        // Root-level copies use final rendered positions, so no nested parent
        // can clip the new sixth-column control.
        Component label = cloneAtRoot(
            antiLabel,
            antiLabel.x + COLUMN_DELTA_X,
            antiLabel.y
        );
        label.clientCode = CLIENT_CODE_LABEL_TEXT;
        label.text = JagString.parse("Modern UI");
        label.activeText = label.text;
        added.add(label);

        Component selectorHit = null;
        int selectorHitArea = -1;

        for (LayoutEntry source : selectorSprites) {
            Component clone = cloneAtRoot(
                source,
                source.x + COLUMN_DELTA_X,
                source.y
            );
            clone.clientCode = CLIENT_CODE_SELECTOR_PIECE;
            added.add(clone);

            int area = Math.max(1, clone.width) * Math.max(1, clone.height);
            if (area > selectorHitArea) {
                selectorHit = clone;
                selectorHitArea = area;
            }
        }

        if (selectorHit != null) {
            selectorHit.clientCode = CLIENT_CODE_SELECTOR_HIT;
        }

        Component value = cloneAtRoot(
            valueText,
            valueText.x + COLUMN_DELTA_X,
            valueText.y
        );
        value.clientCode = CLIENT_CODE_VALUE_TEXT;
        value.text = JagString.parse(ModernUiManager.isEnabled() ? "Yes" : "No");
        value.activeText = value.text;
        added.add(value);

        // Style Editor is intentionally NOT another clone of AA's sprite
        // assembly. Those dropdown cap/arrow pieces are meaningless on a
        // button and were the extra ornaments visible in the menu.
        // Reuse only AA's text geometry as a real component/hitbox. Modern
        // mode draws the button artwork from the .uipack behind this text.
        Component styleText = cloneAtRoot(
            valueText,
            valueText.x + COLUMN_DELTA_X,
            valueText.y + STYLE_DELTA_Y
        );
        styleText.clientCode = CLIENT_CODE_STYLE_TEXT;
        styleText.text = JagString.parse("Style Editor");
        styleText.activeText = styleText.text;
        added.add(styleText);

        Component[] expanded = Arrays.copyOf(original, original.length + added.size());
        int child = original.length;
        for (Component component : added) {
            component.id = (interfaceId << 16) | child;
            component.createdComponentId = -1;
            expanded[child++] = component;
        }
        InterfaceList.components[interfaceId] = expanded;

        DisplayDebug.log(
            "MODERN_UI inserted root-level Graphics Options controls"
                + " interface=" + interfaceId
                + ", aaCenter=" + aaCenterX
                + ", targetCenter=" + (aaCenterX + COLUMN_DELTA_X)
                + ", selectorParts=" + selectorSprites.size()
                + ", added=" + added.size()
        );
        for (LayoutEntry source : selectorSprites) {
            DisplayDebug.log(
                "MODERN_UI selector clone source"
                    + " id=" + source.component.id
                    + " xy=" + source.x + "," + source.y
                    + " size=" + source.component.width + "x" + source.component.height
            );
        }
    }

    private static Component cloneAtRoot(
        LayoutEntry source,
        int absoluteX,
        int absoluteY
    ) {
        Component clone = cloneComponent(source.component);
        prepareClone(clone);

        clone.overlayer = -1;

        // Because this copy is now a root component, x/y and baseX/baseY are
        // all expressed directly in interface coordinates.
        clone.baseX = absoluteX;
        clone.baseY = absoluteY;
        clone.x = absoluteX;
        clone.y = absoluteY;
        clone.xMode = 0;
        clone.yMode = 0;

        return clone;
    }

    /**
     * Normalizes the real cache-driven Graphics Options dropdowns.
     *
     * The popup rows are not one hidden container and they are not separate
     * child interfaces. The cache toggles several text/sprite pieces under a
     * shared overlayer. We therefore identify visible popup groups from their
     * actual rendered component geometry and hide the stale popup rows when a
     * different dropdown opens.
     */
    public static void normalizeNativeDropdowns() {
        suppressedNativeDropdownComponents.clear();
        nativeDropdownOpen = false;

        int interfaceId = activeGraphicsOptionsInterfaceId;
        if (InterfaceList.components == null
            || interfaceId < 0
            || interfaceId >= InterfaceList.components.length) {
            previousVisibleDropdownGroups.clear();
            lastVisibleDropdownGroup = -1;
            return;
        }

        Component[] components = InterfaceList.components[interfaceId];
        if (components == null || !isGraphicsOptionsActive(components)) {
            previousVisibleDropdownGroups.clear();
            lastVisibleDropdownGroup = -1;
            return;
        }

        List<LayoutEntry> entries = new ArrayList<>();
        collectVisibleLayout(components, -1, 0, 0, entries);

        Map<Integer, DropdownGroup> groups =
            findVisibleDropdownGroups(entries);

        if (groups.isEmpty()) {
            previousVisibleDropdownGroups.clear();
            lastVisibleDropdownGroup = -1;
            return;
        }

        int keep = -1;

        // Prefer the group that became visible this tick.
        for (DropdownGroup group : groups.values()) {
            if (!previousVisibleDropdownGroups.contains(group.groupKey)) {
                if (keep == -1
                    || group.distanceToClick()
                        < groups.get(keep).distanceToClick()) {
                    keep = group.groupKey;
                }
            }
        }

        // Otherwise retain the group that was already authoritative.
        if (keep == -1 && groups.containsKey(lastVisibleDropdownGroup)) {
            keep = lastVisibleDropdownGroup;
        }

        // Final fallback: the popup closest to the user's last click.
        if (keep == -1) {
            for (DropdownGroup group : groups.values()) {
                if (keep == -1
                    || group.distanceToClick()
                        < groups.get(keep).distanceToClick()) {
                    keep = group.groupKey;
                }
            }
        }

        // Exactly one native popup is allowed to own the foreground. Stale
        // popup state may still exist in the cache scripts, but every visual
        // and hit-test component belonging to those stale popups is suppressed.
        if (keep != -1) {
            nativeDropdownOpen = true;
            for (DropdownGroup group : groups.values()) {
                if (group.groupKey != keep) {
                    suppressDropdownGroup(entries, group, false);
                }
            }
        }

        previousVisibleDropdownGroups.clear();
        if (keep != -1) {
            previousVisibleDropdownGroups.add(keep);
            lastVisibleDropdownGroup = keep;
        } else {
            lastVisibleDropdownGroup = -1;
        }
    }

    public static boolean isNativeDropdownOpen() {
        return nativeDropdownOpen;
    }

    public static boolean shouldSuppressNativeDropdownComponent(
        Component component
    ) {
        return component != null
            && suppressedNativeDropdownComponents.contains(component.id);
    }

    private static Map<Integer, DropdownGroup> findVisibleDropdownGroups(
        List<LayoutEntry> entries
    ) {
        List<LayoutEntry> candidates = new ArrayList<>();

        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component == null
                || component.hidden
                || component.type != 4
                || component.clientCode >= CLIENT_CODE_SELECTOR_HIT
                    && component.clientCode <= CLIENT_CODE_LABEL_TEXT
                || component.text == null
                || component.text.length() == 0
                || component.width > 220
                || component.height > 36) {
                continue;
            }

            String text =
                normalizeDropdownText(component.text.toString());

            if (text.isEmpty() || isGraphicsOptionsPageLabel(text)) {
                continue;
            }

            candidates.add(entry);
        }

        // Popup lists are vertical runs of short values with almost identical
        // X centers and roughly one text-line of Y separation. Closed values
        // in the normal settings grid are ~60px apart vertically, so they do
        // not form a run. This works whether Jagex attached the popup pieces
        // to a dedicated overlayer or directly to the page.
        candidates.sort(
            Comparator.comparingInt(
                (LayoutEntry e) ->
                    e.x + Math.max(1, e.component.width) / 2
            ).thenComparingInt(e -> e.y)
        );

        Map<Integer, DropdownGroup> groups = new HashMap<>();
        Set<Component> assigned = new HashSet<>();

        for (LayoutEntry seed : candidates) {
            if (assigned.contains(seed.component)) {
                continue;
            }

            int seedCenter =
                seed.x + Math.max(1, seed.component.width) / 2;

            List<LayoutEntry> column = new ArrayList<>();
            for (LayoutEntry candidate : candidates) {
                if (assigned.contains(candidate.component)) {
                    continue;
                }

                int center =
                    candidate.x
                        + Math.max(1, candidate.component.width) / 2;
                if (Math.abs(center - seedCenter) <= 24) {
                    column.add(candidate);
                }
            }

            column.sort(Comparator.comparingInt(e -> e.y));

            int seedIndex = column.indexOf(seed);
            if (seedIndex < 0) {
                continue;
            }

            int first = seedIndex;
            while (first > 0) {
                LayoutEntry prev = column.get(first - 1);
                LayoutEntry cur = column.get(first);
                int gap = cur.y - prev.y;
                if (gap < 0 || gap > 24) {
                    break;
                }
                first--;
            }

            int last = seedIndex;
            while (last + 1 < column.size()) {
                LayoutEntry cur = column.get(last);
                LayoutEntry next = column.get(last + 1);
                int gap = next.y - cur.y;
                if (gap < 0 || gap > 24) {
                    break;
                }
                last++;
            }

            if (last - first + 1 < 2) {
                assigned.add(seed.component);
                continue;
            }

            DropdownGroup group = new DropdownGroup();
            for (int i = first; i <= last; i++) {
                LayoutEntry entry = column.get(i);
                group.include(entry);
                assigned.add(entry.component);
            }

            if (group.maxY - group.minY < 8
                || group.maxY - group.minY > 360
                || group.maxX - group.minX > 220) {
                continue;
            }

            group.finishKey();
            groups.put(group.groupKey, group);
        }

        return groups;
    }

    private static void suppressDropdownGroup(
        List<LayoutEntry> entries,
        DropdownGroup group,
        boolean persistHidden
    ) {
        // The text rows tell us which popup is open, but the tan background,
        // border and arrow pieces can begin at the selector's Y and extend
        // below it. Suppress by structure + overlap, not by component top Y.
        int closedRowBottom = group.minY + 18;
        int popupBodyTop = group.minY + 8;
        int popupLeft = group.minX - 24;
        int popupRight = group.maxX + 24;
        int popupBottom = group.maxY + 12;

        Set<Integer> groupParents = new HashSet<>();
        for (LayoutEntry entry : group.entries) {
            if (entry.component != null
                && entry.component.overlayer != -1) {
                groupParents.add(entry.component.overlayer);
            }
        }

        int suppressed = 0;
        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component == null
                || component.clientCode >= CLIENT_CODE_SELECTOR_HIT
                    && component.clientCode <= CLIENT_CODE_LABEL_TEXT) {
                continue;
            }

            int width = Math.max(1, component.width);
            int height = Math.max(1, component.height);
            int right = entry.x + width;
            int bottom = entry.y + height;

            boolean horizontalOverlap =
                right >= popupLeft && entry.x <= popupRight;
            boolean popupBodyOverlap =
                bottom > popupBodyTop && entry.y <= popupBottom;

            if (!horizontalOverlap || !popupBodyOverlap) {
                continue;
            }

            boolean directRow = group.entries.contains(entry);
            boolean relatedByParent =
                belongsToDropdownParents(component, groupParents);

            if (!directRow && !relatedByParent) {
                continue;
            }

            // Preserve only compact pieces belonging to the normal closed
            // selector row. A tall background that starts on this row but
            // extends into the popup body is popup chrome and must disappear.
            boolean compactClosedRowPiece =
                entry.y <= group.minY + 7
                    && bottom <= closedRowBottom
                    && height <= 24
                    && !directRow;

            if (compactClosedRowPiece) {
                continue;
            }

            // Preserve the first text row itself; Modern rendering supplies
            // the closed selector box/value. All following rows are popup.
            if (directRow && entry.y <= group.minY + 7) {
                continue;
            }

            if (suppressedNativeDropdownComponents.add(component.id)) {
                suppressed++;
            }

            if (persistHidden && !component.hidden) {
                component.hidden = true;
                InterfaceList.redraw(component);
            }
        }

        if (suppressed > 0) {
            DisplayDebug.log(
                "GRAPHICS_OPTIONS suppress stale dropdown group="
                    + group.groupKey
                    + " components=" + suppressed
                    + " persistent=" + persistHidden
                    + " anchorY=" + group.minY
            );
        }
    }

    private static boolean belongsToDropdownParents(
        Component component,
        Set<Integer> groupParents
    ) {
        if (component == null || groupParents.isEmpty()) {
            return false;
        }

        int parentId = component.overlayer;
        int depth = 0;
        while (parentId != -1 && depth++ < 8) {
            if (groupParents.contains(parentId)) {
                return true;
            }

            Component parent = InterfaceList.getComponent(parentId);
            if (parent == null || parent.overlayer == parentId) {
                break;
            }
            parentId = parent.overlayer;
        }

        return false;
    }

    public static void closeAllNativeDropdowns() {
        int interfaceId = activeGraphicsOptionsInterfaceId;
        if (InterfaceList.components == null
            || interfaceId < 0
            || interfaceId >= InterfaceList.components.length) {
            nativeDropdownOpen = false;
            return;
        }

        Component[] components = InterfaceList.components[interfaceId];
        if (components == null || !isGraphicsOptionsActive(components)) {
            nativeDropdownOpen = false;
            return;
        }

        List<LayoutEntry> entries = new ArrayList<>();
        collectVisibleLayout(components, -1, 0, 0, entries);
        Map<Integer, DropdownGroup> groups =
            findVisibleDropdownGroups(entries);

        suppressedNativeDropdownComponents.clear();
        for (DropdownGroup group : groups.values()) {
            suppressDropdownGroup(entries, group, true);
        }

        previousVisibleDropdownGroups.clear();
        lastVisibleDropdownGroup = -1;
        nativeDropdownOpen = false;
    }

    public static boolean shouldSuppressHiddenComponent(
        Component component
    ) {
        if (component == null) {
            return false;
        }

        if (shouldSuppressNativeDropdownComponent(component)) {
            return true;
        }

        if (!component.hidden) {
            return false;
        }

        int interfaceId = component.id >>> 16;
        return interfaceId == activeGraphicsOptionsInterfaceId;
    }

    private static boolean isGraphicsOptionsPageLabel(String text) {
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

    private static String normalizeDropdownText(String text) {
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
            .toLowerCase(Locale.ROOT)
            .replaceAll("\\s+", " ");
    }

    private static final class DropdownGroup {
        private int groupKey = -1;
        private int minX = Integer.MAX_VALUE;
        private int minY = Integer.MAX_VALUE;
        private int maxX = Integer.MIN_VALUE;
        private int maxY = Integer.MIN_VALUE;
        private final List<LayoutEntry> entries = new ArrayList<>();

        private void include(LayoutEntry entry) {
            entries.add(entry);
            minX = Math.min(minX, entry.x);
            minY = Math.min(minY, entry.y);
            maxX = Math.max(
                maxX,
                entry.x + Math.max(1, entry.component.width)
            );
            maxY = Math.max(
                maxY,
                entry.y + Math.max(1, entry.component.height)
            );
        }

        private void finishKey() {
            LayoutEntry top = null;
            for (LayoutEntry entry : entries) {
                if (top == null
                    || entry.y < top.y
                    || entry.y == top.y
                        && entry.x < top.x) {
                    top = entry;
                }
            }
            groupKey = top == null ? -1 : top.component.id;
        }

        private int distanceToClick() {
            int cx = (minX + maxX) / 2;
            int cy = minY;
            return Math.abs(Mouse.clickX - cx)
                + Math.abs(Mouse.clickY - cy);
        }
    }

    public static boolean isGraphicsOptionsActive(Component[] components) {
        List<LayoutEntry> entries = new ArrayList<>();
        collectVisibleLayout(components, -1, 0, 0, entries);
        return hasGraphicsOptionsSignature(entries);
    }

    private static void collectVisibleLayout(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<LayoutEntry> out
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }

            // Mirror the vanilla visibility rules. Most importantly, do not
            // walk children of hidden non-IF3 containers; those are inactive
            // menu pages stored in the same interface archive.
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
            out.add(new LayoutEntry(component, x, y));

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;

                collectVisibleLayout(
                    components,
                    component.id,
                    childX,
                    childY,
                    out
                );

                if (component.createdComponents != null) {
                    collectVisibleLayout(
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

    private static boolean hasGraphicsOptionsSignature(List<LayoutEntry> entries) {
        boolean graphicsOptions = false;
        boolean displayModes = false;
        boolean advancedOptions = false;
        boolean antiAliasing = false;

        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component.type != 4
                || component.text == null
                || component.text.length() == 0) {
                continue;
            }

            String text = component.text.toString()
                .trim()
                .toLowerCase(Locale.ROOT);

            if (text.equals("graphics options")) {
                graphicsOptions = true;
            } else if (text.equals("display modes")) {
                displayModes = true;
            } else if (text.equals("advanced options")) {
                advancedOptions = true;
            } else if (text.equals("anti-aliasing")) {
                antiAliasing = true;
            }
        }

        return graphicsOptions
            && displayModes
            && advancedOptions
            && antiAliasing;
    }

    private static Component[] removeInjected(Component[] components) {
        int kept = 0;
        for (Component component : components) {
            if (!isInjected(component)) {
                kept++;
            }
        }

        if (kept == components.length) {
            return components;
        }

        Component[] cleaned = new Component[kept];
        int index = 0;
        for (Component component : components) {
            if (!isInjected(component)) {
                cleaned[index++] = component;
            }
        }

        DisplayDebug.log(
            "MODERN_UI removed inactive Graphics Options controls"
        );
        return cleaned;
    }

    public static boolean isModernUiControl(Component component) {
        return isInjected(component);
    }

    private static boolean isInjected(Component component) {
        if (component == null) {
            return false;
        }

        int code = component.clientCode;
        return code == CLIENT_CODE_SELECTOR_HIT
            || code == CLIENT_CODE_STYLE_HIT
            || code == CLIENT_CODE_VALUE_TEXT
            || code == CLIENT_CODE_STYLE_TEXT
            || code == CLIENT_CODE_SELECTOR_PIECE
            || code == CLIENT_CODE_LABEL_TEXT;
    }

    private static boolean alreadyInjected(Component[] components) {
        for (Component component : components) {
            if (isInjected(component)) {
                return true;
            }
        }
        return false;
    }

    private static LayoutEntry findText(
        List<LayoutEntry> entries,
        String textFragment
    ) {
        String needle = textFragment.toLowerCase(Locale.ROOT);
        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component.type != 4
                || component.text == null
                || component.text.length() == 0) {
                continue;
            }

            if (component.text.toString().toLowerCase(Locale.ROOT).contains(needle)) {
                return entry;
            }
        }
        return null;
    }

    private static void logNearby(
        List<LayoutEntry> entries,
        int centerX,
        int labelY
    ) {
        int logged = 0;
        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            int componentCenter = entry.x + Math.max(1, component.width) / 2;

            if (Math.abs(componentCenter - centerX) > 100
                || entry.y < labelY - 12
                || entry.y > labelY + 58) {
                continue;
            }

            String text = component.text == null ? "" : component.text.toString();
            DisplayDebug.log(
                "MODERN_UI AA reference"
                    + " id=" + component.id
                    + " parent=" + component.overlayer
                    + " type=" + component.type
                    + " xy=" + entry.x + "," + entry.y
                    + " size=" + component.width + "x" + component.height
                    + (text.isEmpty() ? "" : " text='" + text + "'")
            );

            if (++logged >= 24) {
                break;
            }
        }
    }

    private static void prepareClone(Component component) {
        // Visual/layout fields are copied. All behavior belonging to
        // Anti-aliasing is removed from the copy; AA itself is untouched.
        component.onLoad = null;
        component.onStatTransmit = null;
        component.onVarcTransmit = null;
        component.onClickRepeat = null;
        component.onDrag = null;
        component.onInvTransmit = null;
        component.onWidgetsOpenClose = null;
        component.onHold = null;
        component.onScroll = null;
        component.onUse = null;
        component.onDialogAbort = null;
        component.onKey = null;
        component.onVarcstrTransmit = null;
        component.onDragRelease = null;
        component.onRelease = null;
        component.onMouseOver = null;
        component.onMouseRepeat = null;
        component.onMouseLeave = null;
        component.onVarpTransmit = null;
        component.onDragStart = null;
        component.onUseWith = null;
        component.onClanTransmit = null;
        component.onOptionClick = null;
        component.onMiscTransmit = null;
        component.onStockTransmit = null;
        component.onTimer = null;
        component.onFriendTransmit = null;
        component.onMsg = null;

        component.cs1Scripts = null;
        component.cs1ComparisonOpcodes = null;
        component.cs1ComparisonOperands = null;
        component.varpTriggers = null;
        component.inventoryTriggers = null;
        component.statTriggers = null;
        component.varcTriggers = null;
        component.varcstrTriggers = null;
        component.ops = null;
        component.properties = Component.DEFAULT_SERVER_ACTIVE_PROPERTIES;
        component.buttonType = 0;
        component.option = Component.EMPTY_STRING;
        component.optionBase = Component.EMPTY_STRING;
        component.optionCircumfix = Component.EMPTY_STRING;
        component.optionSuffix = Component.EMPTY_STRING;
        component.clientCode = 0;
    }

    private static Component cloneComponent(Component source) {
        Component clone = new Component();
        try {
            for (Field field : Component.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                field.set(clone, field.get(source));
            }
            return clone;
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(
                "Unable to clone Graphics Options component",
                ex
            );
        }
    }

    private static final class LayoutEntry {
        private final Component component;
        private final int x;
        private final int y;

        private LayoutEntry(Component component, int x, int y) {
            this.component = component;
            this.x = x;
            this.y = y;
        }
    }
}
