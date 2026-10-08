package rt4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
     * Graphics Options dropdowns are child interfaces, not hidden containers
     * in the page itself. When RuneScape opens one child popup, close any
     * previous child popup attached to the same Graphics Options interface.
     */
    public static void onSubInterfaceOpened(
        int parentComponentId,
        ComponentPointer opened
    ) {
        if (opened == null) {
            return;
        }

        int parentInterfaceId = parentComponentId >>> 16;
        if (parentInterfaceId != activeGraphicsOptionsInterfaceId) {
            return;
        }

        if (InterfaceList.components == null
            || parentInterfaceId < 0
            || parentInterfaceId >= InterfaceList.components.length
            || InterfaceList.components[parentInterfaceId] == null
            || !isGraphicsOptionsActive(
                InterfaceList.components[parentInterfaceId]
            )) {
            return;
        }

        List<ComponentPointer> stale = new ArrayList<>();
        HashTableIterator iter =
            new HashTableIterator(InterfaceList.openInterfaces);

        for (ComponentPointer ptr =
                 (ComponentPointer) iter.first();
             ptr != null;
             ptr = (ComponentPointer) iter.next()) {
            if (ptr == opened) {
                continue;
            }

            int otherParentComponentId = (int) ptr.key;
            if ((otherParentComponentId >>> 16)
                == activeGraphicsOptionsInterfaceId) {
                stale.add(ptr);
            }
        }

        for (ComponentPointer ptr : stale) {
            DisplayDebug.log(
                "GRAPHICS_OPTIONS closing previous dropdown child"
                    + " parent=" + ptr.key
                    + " interface=" + ptr.interfaceId
                    + " for new parent=" + parentComponentId
                    + " interface=" + opened.interfaceId
            );
            InterfaceList.closeInterface(true, ptr);
        }
    }

    /**
     * Exact popup state used by the Modern UI selector. A native Graphics
     * Options dropdown is open whenever a child interface is attached to a
     * component belonging to the active Graphics Options page.
     */
    public static boolean isNativeDropdownOpen() {
        if (activeGraphicsOptionsInterfaceId < 0) {
            return false;
        }

        HashTableIterator iter =
            new HashTableIterator(InterfaceList.openInterfaces);

        for (ComponentPointer ptr =
                 (ComponentPointer) iter.first();
             ptr != null;
             ptr = (ComponentPointer) iter.next()) {
            int parentComponentId = (int) ptr.key;
            if ((parentComponentId >>> 16)
                == activeGraphicsOptionsInterfaceId) {
                return true;
            }
        }

        return false;
    }

    public static boolean isGraphicsOptionsDropdownInterface(
        int interfaceId
    ) {
        if (activeGraphicsOptionsInterfaceId < 0) {
            return false;
        }

        HashTableIterator iter =
            new HashTableIterator(InterfaceList.openInterfaces);

        for (ComponentPointer ptr =
                 (ComponentPointer) iter.first();
             ptr != null;
             ptr = (ComponentPointer) iter.next()) {
            int parentComponentId = (int) ptr.key;
            if ((parentComponentId >>> 16)
                    == activeGraphicsOptionsInterfaceId
                && ptr.interfaceId == interfaceId) {
                return true;
            }
        }

        return false;
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
