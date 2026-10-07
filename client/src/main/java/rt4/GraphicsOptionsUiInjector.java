package rt4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Adds the Modern UI selector to the real Graphics Options component tree.
 *
 * The original menu is cache/JS5 driven rather than authored in Java. Instead
 * of drawing an overlay with guessed coordinates, this runs immediately after
 * an interface group is decoded and clones the existing Anti-aliasing control.
 * The result is a normal Component set laid out and rendered exactly like the
 * surrounding controls.
 */
public final class GraphicsOptionsUiInjector {
    public static final int CLIENT_CODE_SELECTOR_HIT = 1901;
    public static final int CLIENT_CODE_STYLE_HIT = 1902;
    public static final int CLIENT_CODE_VALUE_TEXT = 1903;
    public static final int CLIENT_CODE_STYLE_TEXT = 1904;

    private static final int COLUMN_DELTA_X = 130;
    private static final int STYLE_DELTA_Y = 32;

    private GraphicsOptionsUiInjector() {
    }

    public static void inject(int interfaceId) {
        Component[] original = InterfaceList.components[interfaceId];
        if (original == null || original.length == 0 || alreadyInjected(original)) {
            return;
        }

        Component antiLabel = findText(original, "Anti-aliasing");
        if (antiLabel == null) {
            return;
        }

        int parent = antiLabel.overlayer;
        int labelCenter = antiLabel.baseX + Math.max(1, antiLabel.baseWidth) / 2;
        int labelY = antiLabel.baseY;

        List<Component> selectorSprites = new ArrayList<>();
        Component valueText = null;

        for (Component component : original) {
            if (component == null || component.overlayer != parent) {
                continue;
            }

            int width = Math.max(1, component.baseWidth);
            int center = component.baseX + width / 2;
            if (Math.abs(center - labelCenter) > 72) {
                continue;
            }

            int dy = component.baseY - labelY;
            if (component.type == 5 && dy >= 7 && dy <= 48
                && component.baseWidth > 0 && component.baseHeight > 0
                && component.baseWidth <= 180 && component.baseHeight <= 40) {
                selectorSprites.add(component);
                continue;
            }

            if (component.type == 4
                && component != antiLabel
                && dy >= 5 && dy <= 45
                && component.text != null
                && component.text.length() > 0) {
                if (valueText == null
                    || Math.abs(component.baseY - (labelY + 18))
                        < Math.abs(valueText.baseY - (labelY + 18))) {
                    valueText = component;
                }
            }
        }

        if (selectorSprites.isEmpty() || valueText == null) {
            DisplayDebug.log(
                "MODERN_UI Graphics Options injection skipped: Anti-aliasing assembly incomplete"
                    + " interface=" + interfaceId
                    + ", sprites=" + selectorSprites.size()
                    + ", valueText=" + (valueText != null)
            );
            return;
        }

        selectorSprites.sort(
            Comparator.comparingInt((Component c) -> c.baseY)
                .thenComparingInt(c -> c.baseX)
        );

        List<Component> added = new ArrayList<>();

        Component label = cloneComponent(antiLabel);
        prepareClone(label);
        shift(label, COLUMN_DELTA_X, 0);
        label.text = JagString.parse("Modern UI");
        label.activeText = label.text;
        added.add(label);

        Component widestSelector = null;
        for (Component source : selectorSprites) {
            Component clone = cloneComponent(source);
            prepareClone(clone);
            shift(clone, COLUMN_DELTA_X, 0);
            added.add(clone);

            if (widestSelector == null || clone.baseWidth > widestSelector.baseWidth) {
                widestSelector = clone;
            }
        }
        if (widestSelector != null) {
            widestSelector.clientCode = CLIENT_CODE_SELECTOR_HIT;
        }

        Component value = cloneComponent(valueText);
        prepareClone(value);
        shift(value, COLUMN_DELTA_X, 0);
        value.clientCode = CLIENT_CODE_VALUE_TEXT;
        value.text = JagString.parse(ModernUiManager.isEnabled() ? "Yes" : "No");
        value.activeText = value.text;
        added.add(value);

        // Style Editor uses the same field body and text metrics, but sits on a
        // separate row with an 8-12 px visual gap instead of colliding with the
        // selector above it. Tiny dropdown caps/arrow pieces are intentionally
        // not cloned because this is a button, not another selector.
        Component styleHit = null;
        for (Component source : selectorSprites) {
            if (source.baseWidth < 60 || source.baseHeight < 12) {
                continue;
            }
            Component clone = cloneComponent(source);
            prepareClone(clone);
            shift(clone, COLUMN_DELTA_X, STYLE_DELTA_Y);
            added.add(clone);
            if (styleHit == null || clone.baseWidth > styleHit.baseWidth) {
                styleHit = clone;
            }
        }
        if (styleHit != null) {
            styleHit.clientCode = CLIENT_CODE_STYLE_HIT;
        }

        Component styleText = cloneComponent(valueText);
        prepareClone(styleText);
        shift(styleText, COLUMN_DELTA_X, STYLE_DELTA_Y);
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
            "MODERN_UI inserted Graphics Options components into interface=" + interfaceId
                + ", clonedFrom=Anti-aliasing"
                + ", selectorParts=" + selectorSprites.size()
                + ", added=" + added.size()
        );
    }

    private static boolean alreadyInjected(Component[] components) {
        for (Component component : components) {
            if (component != null
                && (component.clientCode == CLIENT_CODE_SELECTOR_HIT
                    || component.clientCode == CLIENT_CODE_VALUE_TEXT
                    || component.clientCode == CLIENT_CODE_STYLE_HIT
                    || component.clientCode == CLIENT_CODE_STYLE_TEXT)) {
                return true;
            }
        }
        return false;
    }

    private static Component findText(Component[] components, String text) {
        for (Component component : components) {
            if (component != null
                && component.type == 4
                && component.text != null
                && component.text.length() > 0
                && text.equals(component.text.toString())) {
                return component;
            }
        }
        return null;
    }

    private static void shift(Component component, int dx, int dy) {
        component.baseX += dx;
        component.x += dx;
        component.baseY += dy;
        component.y += dy;
    }

    private static void prepareClone(Component component) {
        // The clone inherits visuals/layout only. Existing Anti-aliasing
        // scripts must never change anti-aliasing when our new control is used.
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
            throw new IllegalStateException("Unable to clone Graphics Options component", ex);
        }
    }
}
