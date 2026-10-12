package rt4;

/**
 * Uses otherwise-unclaimed mouse-wheel input as camera pitch/zoom control.
 * Modern scrollable windows consume the wheel first by setting
 * MouseWheel.wheelRotation to zero.
 */
public final class ModernWorldMouseWheel {
    private ModernWorldMouseWheel() {
    }

    public static void process() {
        if (!ModernUiManager.isEnabled()
            || client.gameState != 30
            || MouseWheel.wheelRotation == 0) {
            return;
        }

        if (ModernUiInputRouter.isWorldInputBlocked(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            )) {
            return;
        }

        int rotation =
            MouseWheel.wheelRotation;
        MouseWheel.wheelRotation = 0;

        if (rotation < 0) {
            for (int i = 0; i < -rotation; i++) {
                // Exact Up-arrow camera step.
                Camera.pitchTarget += 47;
            }
        } else {
            for (int i = 0; i < rotation; i++) {
                // Exact Down-arrow camera step.
                Camera.pitchTarget -= 17;
            }
        }

        Camera.clampCameraAngle();
    }
}
