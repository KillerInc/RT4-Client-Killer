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

    private static final int COLUMN_DELTA_X = 130;
    private static final int STYLE_DELTA_Y = 30;

    private GraphicsOptionsUiInjector() {
    }

    public static void inject(int interfaceId) {
        Component[] original = InterfaceList.components[interfaceId];
        if (original == null || original.length == 0 || alreadyInjected(original)) {
            return;
        }

        List<LayoutEntry> entries = new ArrayList<>();
        collectLayout(original, -1, 0, 0, entries);

        LayoutEntry graphicsTitle = findText(entries, "graphics options");
        LayoutEntry antiLabel = findText(entries, "anti-alias");
        if (graphicsTitle == null || antiLabel == null) {
            return;
        }

        int aaCenterX = antiLabel.x + Math.max(1, antiLabel.component.width) / 2;
        int aaLabelY = antiLabel.y;

        List<LayoutEntry> selectorSprites = new ArrayList<>();
        LayoutEntry valueText = null;

        for (LayoutEntry entry : entries) {
            Component component = entry.component;
            if (component == antiLabel.component) {
                continue;
            }

            int width = Math.max(1, component.width);
            int centerX = entry.x + width / 2;
            int dx = Math.abs(centerX - aaCenterX);
            int dy = entry.y - aaLabelY;

            if (component.type == 5
                && dx <= 78
                && dy >= 6 && dy <= 48
                && component.width > 0 && component.height > 0
                && component.width <= 180 && component.height <= 40) {
                selectorSprites.add(entry);
                continue;
            }

            if (component.type == 4
                && dx <= 72
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

    private static void collectLayout(
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

            int x = parentX + component.x;
            int y = parentY + component.y;
            out.add(new LayoutEntry(component, x, y));

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;

                collectLayout(components, component.id, childX, childY, out);

                if (component.createdComponents != null) {
                    collectLayout(
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

    private static boolean alreadyInjected(Component[] components) {
        for (Component component : components) {
            if (component != null
                && (component.clientCode == CLIENT_CODE_SELECTOR_HIT
                    || component.clientCode == CLIENT_CODE_VALUE_TEXT
                    || component.clientCode == CLIENT_CODE_STYLE_TEXT)) {
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
