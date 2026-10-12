package rt4;

/**
 * Small in-game hotkey bridge. Hotkeys reuse the currently loaded vanilla
 * component actions so they stay behaviorally identical to clicking the
 * original control.
 */
public final class ModernGameHotkeys {
    private ModernGameHotkeys() {
    }

    public static void process() {
        if (!Keyboard.consumeModernF12LogoutRequest()) {
            return;
        }

        if (client.gameState != 30) {
            return;
        }

        Component logout = findLogoutToggle();
        if (logout == null) {
            DisplayDebug.log(
                "MODERN_UI F12 logout toggle not found"
            );
            return;
        }

        activateToggle(logout);

        DisplayDebug.log(
            "MODERN_UI F12 logout toggle"
                + " interface=" + (logout.id >>> 16)
                + " component=" + (logout.id & 0xFFFF)
                + " buttonType=" + logout.buttonType
        );
    }

    private static Component findLogoutToggle() {
        Component[][] all = InterfaceList.components;
        boolean[] loaded = InterfaceList.interfaceLoaded;
        if (all == null || loaded == null) {
            return null;
        }

        int count = Math.min(all.length, loaded.length);
        for (int interfaceId = 0; interfaceId < count; interfaceId++) {
            if (!loaded[interfaceId]) {
                continue;
            }

            Component[] components = all[interfaceId];
            Component found = findLogoutToggle(components);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private static Component findLogoutToggle(
        Component[] components
    ) {
        if (components == null) {
            return null;
        }

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if ((component.buttonType == 4
                    || component.buttonType == 5)
                && looksLikeLogout(component)
                && hasVarpToggle(component)) {
                return component;
            }

            Component found =
                findLogoutToggle(component.createdComponents);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private static boolean looksLikeLogout(
        Component component
    ) {
        return containsLogout(component.option)
            || containsLogout(component.optionBase)
            || containsLogout(component.optionSuffix)
            || containsLogout(component.text)
            || containsLogout(component.activeText);
    }

    private static boolean containsLogout(
        JagString value
    ) {
        if (value == null || value.length() == 0) {
            return false;
        }

        String text =
            value.toString()
                .toLowerCase()
                .replace(" ", "")
                .replace("-", "");
        return text.contains("logout");
    }

    private static boolean hasVarpToggle(
        Component component
    ) {
        return component.cs1Scripts != null
            && component.cs1Scripts.length > 0
            && component.cs1Scripts[0] != null
            && component.cs1Scripts[0].length > 1
            && component.cs1Scripts[0][0] == 5;
    }

    private static void activateToggle(
        Component component
    ) {
        Protocol.outboundBuffer.p1isaac(10);
        Protocol.outboundBuffer.p4(component.id);

        int varpIndex = component.cs1Scripts[0][1];
        if (varpIndex < 0
            || varpIndex >= VarpDomain.activeVarps.length) {
            return;
        }

        if (component.buttonType == 4) {
            VarpDomain.activeVarps[varpIndex] =
                1 - VarpDomain.activeVarps[varpIndex];
        } else {
            int target =
                component.cs1ComparisonOperands != null
                    && component.cs1ComparisonOperands.length > 0
                    ? component.cs1ComparisonOperands[0]
                    : 1;
            VarpDomain.activeVarps[varpIndex] = target;
        }

        VarpDomain.refreshMagicVarp(varpIndex);
        InterfaceList.fullRedrawAllInterfaces();
        GameShell.fullRedraw = true;
    }
}
