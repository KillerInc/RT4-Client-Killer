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
            GlRaster.fillRectAlpha(x, y, width, height, 0x17130F, 230);
            GlRaster.drawRect(x, y, width, height, 0xB59A68);
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, 0x17130F, 230);
            SoftwareRaster.drawRect(x, y, width, height, 0xB59A68);
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
                0xFFFFFF,
                12.0F,
                true
            );
        } else if (Fonts.p12Full != null) {
            Fonts.p12Full.renderCenter(
                JagString.parse("Please wait - " + message),
                x + width / 2,
                y + 32,
                0xFFFFFF,
                0
            );
        }
    }
}
