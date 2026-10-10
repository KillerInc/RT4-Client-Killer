package rt4;

import java.util.Locale;

public final class UiDiagnostics {
    private UiDiagnostics() {
    }

    public static void onInterfaceLoaded(int interfaceId, Component[] components, long nanos) {
        int count = 0;
        int if3 = 0;
        int hidden = 0;
        int[] types = new int[16];

        if (components != null) {
            for (Component component : components) {
                if (component == null) {
                    continue;
                }
                count++;
                if (component.if3) {
                    if3++;
                }
                if (component.hidden) {
                    hidden++;
                }
                if (component.type >= 0 && component.type < types.length) {
                    types[component.type]++;
                }
            }
        }

        int overrideHits = CacheOverrideManager.getGroupHitCount(3, interfaceId);
        double ms = nanos / 1_000_000.0D;
        if (interfaceId == 744 || overrideHits > 0 || ms >= 5.0D) {
            DisplayDebug.log(
                "UI_LOAD interface=" + interfaceId
                    + " components=" + count
                    + " if3=" + if3
                    + " hidden=" + hidden
                    + " overrideHits=" + overrideHits
                    + " loadMs=" + String.format(Locale.ROOT, "%.3f", ms)
                    + " types=" + typeSummary(types)
            );
        }

        if (interfaceId == 744 && components != null) {
            StringBuilder detail = new StringBuilder(16384);
            detail.append("UI_GRAPHICS_OPTIONS_COMPONENT_DUMP interface=744 count=").append(count);
            for (int i = 0; i < components.length; i++) {
                Component c = components[i];
                if (c == null) {
                    continue;
                }
                detail.append("\n  child=").append(i)
                    .append(" id=").append(c.id)
                    .append(" type=").append(c.type)
                    .append(" if3=").append(c.if3)
                    .append(" hidden=").append(c.hidden)
                    .append(" parent=").append(c.overlayer)
                    .append(" clientCode=").append(c.clientCode)
                    .append(" base=").append(c.baseX).append(',').append(c.baseY)
                    .append(" size=").append(c.baseWidth).append('x').append(c.baseHeight)
                    .append(" laidOut=").append(c.x).append(',').append(c.y)
                    .append('/').append(c.width).append('x').append(c.height)
                    .append(" font=").append(c.font)
                    .append(" sprite=").append(c.spriteId);
                if (c.text != null && c.text.length() > 0) {
                    detail.append(" text=\"").append(safe(c.text.toString())).append('\"');
                }
            }
            DisplayDebug.log(detail.toString());
        }
    }

    public static void onComponentDecodeFailure(
        int interfaceId,
        int childId,
        byte[] data,
        Throwable throwable
    ) {
        DisplayDebug.log(
            "UI_DECODE_FAILURE interface=" + interfaceId
                + " child=" + childId
                + " bytes=" + (data == null ? -1 : data.length)
                + " prefix=" + hexPrefix(data, 24),
            throwable
        );
    }

    public static void onHiddenChange(Component component, boolean oldValue, boolean newValue) {
        if (!isGraphicsOptionsComponent(component)) {
            return;
        }
        DisplayDebug.log(
            "UI_STATE setHidden id=" + component.id
                + " child=" + (component.id & 0xFFFF)
                + " type=" + component.type
                + " parent=" + component.overlayer
                + " old=" + oldValue
                + " new=" + newValue
                + " text=\"" + safe(component.text == null ? "" : component.text.toString()) + "\""
        );
    }

    public static void onVFlipChange(
        Component component,
        boolean oldValue,
        boolean newValue
    ) {
        if (!isGraphicsOptionsComponent(component) || oldValue == newValue) {
            return;
        }
        DisplayDebug.log(
            "UI_STATE setVFlip id=" + component.id
                + " child=" + (component.id & 0xFFFF)
                + " type=" + component.type
                + " parent=" + component.overlayer
                + " sprite=" + component.spriteId
                + " old=" + oldValue
                + " new=" + newValue
        );
    }

    public static void onTextChange(Component component, JagString oldValue, JagString newValue) {
        if (!isGraphicsOptionsComponent(component)) {
            return;
        }
        DisplayDebug.log(
            "UI_STATE setText id=" + component.id
                + " child=" + (component.id & 0xFFFF)
                + " type=" + component.type
                + " parent=" + component.overlayer
                + " old=\"" + safe(oldValue == null ? "" : oldValue.toString()) + "\""
                + " new=\"" + safe(newValue == null ? "" : newValue.toString()) + "\""
        );
    }

    public static void logGraphicsOptionsClick() {
        if (Mouse.clickButton == 0) {
            return;
        }

        int interfaceId = InterfaceList.topLevelInterface;
        if (interfaceId < 0
            || InterfaceList.components == null
            || interfaceId >= InterfaceList.components.length) {
            return;
        }

        Component[] components = InterfaceList.components[interfaceId];
        if (!hasGraphicsOptionsTitle(components)) {
            return;
        }

        DisplayDebug.log(
            "UI_CLICK graphicsOptions interface=" + interfaceId
                + " button=" + Mouse.clickButton
                + " click=" + Mouse.clickX + "," + Mouse.clickY
                + " mouse=" + Mouse.lastMouseX + "," + Mouse.lastMouseY
                + " displayMode=" + DisplayDebug.modeName(DisplayMode.getWindowMode())
                + " renderer=" + (GlRenderer.enabled ? "GL" : "SOFTWARE")
                + " modernUi=" + ModernUiManager.isEnabled()
        );
    }

    private static boolean isGraphicsOptionsComponent(Component component) {
        return component != null && (component.id >>> 16) == 744;
    }

    private static boolean hasGraphicsOptionsTitle(Component[] components) {
        if (components == null) {
            return false;
        }
        for (Component component : components) {
            if (component == null || component.text == null || component.text.length() == 0) {
                continue;
            }
            String text = component.text.toString().trim().toLowerCase(Locale.ROOT);
            if (text.contains("graphics options")) {
                return true;
            }
        }
        return false;
    }

    private static String typeSummary(int[] counts) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 0) {
                continue;
            }
            if (out.length() > 0) {
                out.append(',');
            }
            out.append(i).append(':').append(counts[i]);
        }
        return out.toString();
    }

    private static String safe(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t");
        return text.length() <= 120 ? text : text.substring(0, 117) + "...";
    }

    private static String hexPrefix(byte[] data, int max) {
        if (data == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder();
        int limit = Math.min(max, data.length);
        for (int i = 0; i < limit; i++) {
            if (i > 0) {
                out.append(' ');
            }
            int value = data[i] & 0xFF;
            if (value < 16) {
                out.append('0');
            }
            out.append(Integer.toHexString(value).toUpperCase(Locale.ROOT));
        }
        return out.toString();
    }
}
