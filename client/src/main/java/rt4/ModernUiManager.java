package rt4;

/**
 * Entry point for Modern UI state. Renderer selection and style selection are
 * deliberately independent: switching Modern UI off preserves the last style,
 * add-ons, scales and future window layout settings.
 */
public final class ModernUiManager {
    private static boolean initialized;
    private static int reloadGeneration;

    private ModernUiManager() {
    }

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        ModernUiPreferences.load();
        UiStyleRepository.refresh();
        DisplayDebug.log(
            "MODERN_UI init enabled=" + ModernUiPreferences.isEnabled()
                + ", savedStyle=" + ModernUiPreferences.getStyleId()
                + ", effectiveStyle=" + getEffectiveStyle().id
        );
    }

    public static boolean isEnabled() {
        initialize();
        return ModernUiPreferences.isEnabled();
    }

    public static void setEnabled(boolean enabled) {
        initialize();
        if (ModernUiPreferences.isEnabled() == enabled) {
            return;
        }

        DisplayDebug.log("MODERN_UI switching enabled=" + enabled);
        ModernUiPreferences.setEnabled(enabled);
        reloadGeneration++;

        if (enabled) {
            UiStyleRepository.refresh();
        } else {
            StyleEditorWindow.closeWindow();
        }

        ModernUiRenderer.clearCaches();
        InterfaceList.layoutTopLevel(true);
        InterfaceList.fullRedrawAllInterfaces();
    }

    public static UiStyleInfo getEffectiveStyle() {
        initializeWithoutStyleLookup();
        return UiStyleRepository.getEffectiveStyle(ModernUiPreferences.getStyleId());
    }

    public static void refreshStyles() {
        initialize();
        UiStyleRepository.refresh();
        reloadGeneration++;
        DisplayDebug.log("MODERN_UI styles refreshed generation=" + reloadGeneration);
    }

    public static int getReloadGeneration() {
        initialize();
        return reloadGeneration;
    }

    public static void selectStyle(String id) {
        initialize();
        ModernUiPreferences.setStyleId(id);
        reloadGeneration++;
        DisplayDebug.log("MODERN_UI style=" + id + ", generation=" + reloadGeneration);
    }

    public static void setAddonEnabled(String id, boolean enabled) {
        initialize();
        ModernUiPreferences.setAddonEnabled(id, enabled);
        reloadGeneration++;
        DisplayDebug.log("MODERN_UI addon " + id + "=" + enabled + ", generation=" + reloadGeneration);
    }

    public static void setUiScale(float scale) {
        ModernUiPreferences.setUiScale(scale);
        reloadGeneration++;
    }

    public static void setTextScale(float scale) {
        ModernUiPreferences.setTextScale(scale);
        reloadGeneration++;
    }

    public static void setIconScale(float scale) {
        ModernUiPreferences.setIconScale(scale);
        reloadGeneration++;
    }

    public static void openStyleEditor() {
        initialize();
        if (!isEnabled()) {
            return;
        }
        StyleEditorWindow.openWindow();
    }

    private static void initializeWithoutStyleLookup() {
        if (initialized) {
            return;
        }
        initialized = true;
        ModernUiPreferences.load();
        UiStyleRepository.refresh();
    }
}
