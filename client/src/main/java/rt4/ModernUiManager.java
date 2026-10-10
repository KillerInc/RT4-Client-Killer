package rt4;

/**
 * Entry point for Modern UI state. Renderer selection and style selection are
 * deliberately independent: switching Modern UI off preserves the last style,
 * add-ons, scales and future window layout settings.
 *
 * Style Editor callbacks can arrive on Swing's EDT. Resource destruction and
 * interface relayout are therefore queued and performed by the game render
 * thread through processPendingReload().
 */
public final class ModernUiManager {
    private static boolean initialized;
    private static int reloadGeneration;

    private static volatile boolean reloadRequested;
    private static volatile String pendingReloadReason = "UI settings changed";
    private static volatile long reloadNoticeUntil;
    private static volatile String reloadNotice = "";

    private ModernUiManager() {
    }

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        ModernUiPreferences.load();
        ModernVectorCacheManager.startup();
        ModernUiDevelopmentMirror.sync();
        UiStyleRepository.refresh();
        DisplayDebug.log(
            "MODERN_UI init savedEnabled=" + ModernUiPreferences.isEnabled()
                + ", effectiveEnabled=" + isEnabled()
                + ", displayMode=" + DisplayMode.getWindowMode()
                + ", savedStyle=" + ModernUiPreferences.getStyleId()
                + ", effectiveStyle=" + getEffectiveStyle().id
        );
    }

    public static boolean isSupportedDisplayMode() {
        // The Modern renderer is intentionally available only for the two
        // scalable HD modes: resizable HD (2) and fullscreen HD (3).
        return DisplayMode.getWindowMode() >= 2;
    }

    public static boolean isEnabled() {
        initialize();
        return ModernUiPreferences.isEnabled()
            && isSupportedDisplayMode();
    }

    public static void setEnabled(boolean enabled) {
        initialize();

        if (enabled && !isSupportedDisplayMode()) {
            DisplayDebug.log(
                "MODERN_UI enable ignored: unsupported display mode="
                    + DisplayMode.getWindowMode()
            );
            return;
        }

        if (ModernUiPreferences.isEnabled() == enabled) {
            return;
        }

        DisplayDebug.log("MODERN_UI requested enabled=" + enabled);
        ModernUiPreferences.setEnabled(enabled);
        if (enabled) {
            UiStyleRepository.refresh();
            ModernVectorCacheManager.onModernUiEnabled();
        } else {
            ModernUiStyleEditorOverlay.close();
        }
        requestReload(enabled ? "Enabling Modern UI" : "Restoring Standard UI");
    }

    public static UiStyleInfo getEffectiveStyle() {
        initializeWithoutStyleLookup();
        return UiStyleRepository.getEffectiveStyle(ModernUiPreferences.getStyleId());
    }

    public static void refreshStyles() {
        initialize();
        UiStyleRepository.refresh();
        requestReload("Reloading UI styles");
    }

    public static int getReloadGeneration() {
        initialize();
        return reloadGeneration;
    }

    public static void selectStyle(String id) {
        initialize();
        ModernUiPreferences.setStyleId(id);
        DisplayDebug.log("MODERN_UI requested style=" + id);
        requestReload("Loading UI style: " + id);
    }

    public static void setAddonEnabled(String id, boolean enabled) {
        initialize();
        ModernUiPreferences.setAddonEnabled(id, enabled);
        DisplayDebug.log("MODERN_UI requested addon " + id + "=" + enabled);
        requestReload("Reloading UI add-ons");
    }

    public static void setUiScale(float scale) {
        ModernUiPreferences.setUiScale(scale);
        requestReload("Applying UI scale");
    }

    public static void setTextScale(float scale) {
        ModernUiPreferences.setTextScale(scale);
        requestReload("Applying text scale");
    }

    public static void setIconScale(float scale) {
        ModernUiPreferences.setIconScale(scale);
        requestReload("Applying icon scale");
    }

    public static void openStyleEditor() {
        initialize();
        if (!isEnabled()) {
            return;
        }
        ModernUiStyleEditorOverlay.open();
    }

    public static void requestReload(String reason) {
        pendingReloadReason = reason == null || reason.trim().isEmpty() ? "Reloading UI" : reason;
        reloadRequested = true;
    }

    /**
     * Must be called from the game/render thread.
     */
    public static void processPendingReload() {
        initialize();

        if (ModernUiPreferences.isEnabled()) {
            ModernUiResourceAudit.tick();
        }

        if (!reloadRequested) {
            return;
        }

        reloadRequested = false;
        String reason = pendingReloadReason;
        reloadGeneration++;

        int gameStateBefore = client.gameState;
        int interfaceBefore = InterfaceList.topLevelInterface;
        int npcCountBefore = NpcList.size;
        String playerBefore = PlayerList.self == null || PlayerList.self.username == null
            ? "<login/null>"
            : PlayerList.self.username.toString();

        ModernUiRenderer.clearCaches();
        ScriptRunner.forceRedrawAllRectangles();
        GameShell.fullRedraw = true;

        reloadNotice = reason;
        reloadNoticeUntil = MonotonicClock.currentTimeMillis() + 650L;
        DisplayDebug.log(
            "MODERN_UI reload complete generation=" + reloadGeneration
                + ", savedEnabled=" + ModernUiPreferences.isEnabled()
                + ", effectiveEnabled=" + isEnabled()
                + ", displayMode=" + DisplayMode.getWindowMode()
                + ", style=" + ModernUiPreferences.getStyleId()
                + ", reason=" + reason
                + ", gameState=" + gameStateBefore + "->" + client.gameState
                + ", topInterface=" + interfaceBefore + "->" + InterfaceList.topLevelInterface
                + ", npcCount=" + npcCountBefore + "->" + NpcList.size
                + ", player=" + playerBefore
        );
    }

    public static boolean isReloadNoticeActive() {
        return MonotonicClock.currentTimeMillis() < reloadNoticeUntil;
    }

    public static String getReloadNotice() {
        return reloadNotice;
    }

    private static void initializeWithoutStyleLookup() {
        if (initialized) {
            return;
        }
        initialized = true;
        ModernUiPreferences.load();
        ModernVectorCacheManager.startup();
        UiStyleRepository.refresh();
    }
}
