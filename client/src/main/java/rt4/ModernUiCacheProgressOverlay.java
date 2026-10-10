package rt4;

/**
 * Small top-left progress display for background Modern UI vector-cache work.
 * Rendering is intentionally cheap and does not depend on vector assets.
 */
public final class ModernUiCacheProgressOverlay {
    private ModernUiCacheProgressOverlay() {
    }

    public static void render() {
        if (!ModernVectorCacheManager.isBuildProgressVisible()) {
            return;
        }

        int percent = ModernVectorCacheManager.getBuildProgressPercent();

        int x = 8;
        int y = 8;
        int width = 210;
        int height = 42;
        int padding = 8;
        int barX = x + padding;
        int barY = y + 27;
        int barWidth = width - padding * 2;
        int barHeight = 7;
        int fillWidth = barWidth * percent / 100;

        if (GlRenderer.enabled) {
            GlRaster.fillRectAlpha(x, y, width, height, 0x17130F, 220);
            GlRaster.drawRect(x, y, width, height, 0xB59A68);
            GlRaster.fillRect(barX, barY, barWidth, barHeight, 0x332A20);
            if (fillWidth > 0) {
                GlRaster.fillRect(
                    barX,
                    barY,
                    fillWidth,
                    barHeight,
                    0xB59A68
                );
            }
            GlRaster.drawRect(
                barX,
                barY,
                barWidth,
                barHeight,
                0xD8C08D
            );
        } else {
            SoftwareRaster.fillRectAlpha(x, y, width, height, 0x17130F, 220);
            SoftwareRaster.drawRect(x, y, width, height, 0xB59A68);
            SoftwareRaster.fillRect(
                barX,
                barY,
                barWidth,
                barHeight,
                0x332A20
            );
            if (fillWidth > 0) {
                SoftwareRaster.fillRect(
                    barX,
                    barY,
                    fillWidth,
                    barHeight,
                    0xB59A68
                );
            }
            SoftwareRaster.drawRect(
                barX,
                barY,
                barWidth,
                barHeight,
                0xD8C08D
            );
        }

        String message = "Building UI cache " + percent + "%";
        if (Fonts.p12Full != null) {
            Fonts.p12Full.renderLeft(
                JagString.parse(message),
                x + padding,
                y + 18,
                0xFFFFFF,
                0
            );
        } else {
            ModernTrueTypeFont.draw(
                message,
                x + padding,
                y + 18,
                0xFFFFFF,
                12.0F,
                true
            );
        }
    }
}
