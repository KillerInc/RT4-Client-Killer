package rt4;

/**
 * Short non-blocking status overlay shown after an old/new UI or style change.
 * It is rendered with the active UI's text path and never resets game state.
 */
public final class ModernUiReloadOverlay {
    private ModernUiReloadOverlay() {
    }

    public static void render() {
        if (!ModernUiManager.isReloadNoticeActive()) {
            return;
        }

        int width = 300;
        int height = 54;
        int x = (GameShell.canvasWidth - width) / 2;
        int y = (GameShell.canvasHeight - height) / 2;

        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, ModernUiMetrics.SURFACE_DARK, 230);
            GlRaster.drawRect(x, y, width, height, ModernUiMetrics.BORDER);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, ModernUiMetrics.SURFACE_DARK, 230);
            SoftwareRaster.drawRect(x, y, width, height, ModernUiMetrics.BORDER);
        }

        String message = ModernUiManager.getReloadNotice();
        if (message == null || message.isEmpty()) {
            message = "Reloading UI...";
        }

        if (ModernUiManager.isEnabled()) {
            ModernTrueTypeFont.drawCentered(
                "Please wait - " + message,
                x + width / 2,
                y + 32,
                ModernUiMetrics.TEXT_PRIMARY,
                ModernUiMetrics.FONT_LABEL,
                true
            );
        } else if (Fonts.p12Full != null) {
            Fonts.p12Full.renderCenter(
                JagString.parse("Please wait - " + message),
                x + width / 2,
                y + 32,
                ModernUiMetrics.TEXT_PRIMARY,
                0
            );
        }
    }
}
