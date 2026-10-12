package rt4;

/**
 * Modern, vector-font actor name overlay.
 *
 * This deliberately bypasses the legacy bitmap overhead-text renderer. It is
 * drawn from the same world-to-screen projection already used for overhead
 * icons so it follows actors correctly in both software and GL render paths.
 */
public final class ModernEntityNameOverlay {
    private static final int PLAYER_COLOR = 0xFFFFFF;
    private static final int NPC_COLOR = 0xFFFF00;
    private static final float BASE_TEXT_SIZE = 13.0F;
    private static final int HOVER_RADIUS_X = 48;
    private static final int HOVER_RADIUS_Y = 34;

    private ModernEntityNameOverlay() {
    }

    public static void draw(
        PathingEntity entity,
        NpcType resolvedNpcType,
        int screenX,
        int screenY,
        int viewportLeft,
        int viewportTop
    ) {
        if (!ModernGameUi.isInGame() || entity == null || screenX < 0) {
            return;
        }

        ModernUiPreferences.NameDisplayMode mode;
        String name;
        int color;

        if (entity instanceof Player) {
            mode = ModernUiPreferences.getPlayerNameMode();
            Player player = (Player) entity;
            if (player.username == null) {
                return;
            }
            name = player.getName().toString();
            color = PLAYER_COLOR;
        } else if (entity instanceof Npc) {
            mode = ModernUiPreferences.getNpcNameMode();
            NpcType npcType = resolvedNpcType;
            if (npcType == null || npcType.name == null || npcType.name == NpcType.DEFAULT_NAME) {
                return;
            }
            name = npcType.name.toString();
            color = NPC_COLOR;
        } else {
            return;
        }

        if (mode == ModernUiPreferences.NameDisplayMode.OFF || name == null || name.isEmpty()) {
            return;
        }

        int x = viewportLeft + screenX;
        int baselineY = viewportTop + screenY - 4;

        if (mode == ModernUiPreferences.NameDisplayMode.HOVER) {
            int dx = Math.abs(Mouse.lastMouseX - x);
            int dy = Math.abs(Mouse.lastMouseY - baselineY);
            if (dx > HOVER_RADIUS_X || dy > HOVER_RADIUS_Y) {
                return;
            }
        }

        float size = BASE_TEXT_SIZE * ModernUiPreferences.getTextScale();
        ModernTrueTypeFont.drawCentered(
            ModernUiFontRegistry.BOLD_12,
            name,
            x,
            baselineY,
            color,
            size,
            true
        );
    }
}
