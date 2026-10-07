package rt4;

/**
 * Compatibility shim retained for older call sites.
 *
 * The Style Editor is now rendered inside the game canvas. No desktop window
 * is created.
 */
@Deprecated
public final class StyleEditorWindow {
    private StyleEditorWindow() {
    }

    public static void openWindow() {
        ModernUiStyleEditorOverlay.open();
    }

    public static void closeWindow() {
        ModernUiStyleEditorOverlay.close();
    }
}
