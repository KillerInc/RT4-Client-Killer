package rt4;

/**
 * Shared contract for independent Modern title-screen menu layouts.
 * Implementations own their geometry; renderer/input only consume rectangles.
 */
public interface ModernTitleScreenLayout {
    ModernUiRect panel();
    ModernUiRect logo();
    ModernUiRect title();
    ModernUiRect divider();
    ModernUiRect body();
    ModernUiRect bodyLine(int index, int count);
    ModernUiRect fieldRect(int index, int count);
    ModernUiRect fieldLabelRect(int index, int count);
    ModernUiRect actionRect(int index, int count);
    ModernUiRect navigationRect(int index, int count);
}
