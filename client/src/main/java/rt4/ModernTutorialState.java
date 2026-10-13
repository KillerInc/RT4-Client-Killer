package rt4;

/**
 * Modern Tutorial Island presentation state supplied by the server.
 *
 * Slot order follows the server's first 14 default tabs:
 * Combat, Stats, Quest, Inventory, Equipment, Prayer, Magic, Lore,
 * Friends, Ignore, Clan, Options, Emotes, Music.
 */
public final class ModernTutorialState {
    public static final int RUNSCRIPT_ID = 65002;
    public static final int TAB_COUNT = 14;

    private static boolean active;
    private static int stage;
    private static int unlockedMask = (1 << TAB_COUNT) - 1;
    private static int highlightedTabOneBased;
    private static boolean complete;

    private ModernTutorialState() {
    }

    public static boolean handleRunScript(Object[] scriptArgs) {
        if (scriptArgs == null
            || scriptArgs.length < 1
            || !(scriptArgs[0] instanceof Integer)
            || ((Integer) scriptArgs[0]) != RUNSCRIPT_ID) {
            return false;
        }

        if (scriptArgs.length < 2 || scriptArgs[1] == null) {
            reset();
            return true;
        }

        parse(scriptArgs[1].toString());
        return true;
    }

    public static boolean isActive() {
        return active && !complete;
    }

    public static int getStage() {
        return stage;
    }

    public static boolean isUnlocked(int zeroBasedSlot) {
        if (!isActive()) {
            return true;
        }
        if (zeroBasedSlot < 0 || zeroBasedSlot >= TAB_COUNT) {
            return false;
        }
        return (unlockedMask & (1 << zeroBasedSlot)) != 0;
    }

    public static int getHighlightedSlot() {
        if (!isActive()
            || highlightedTabOneBased <= 0
            || highlightedTabOneBased > TAB_COUNT) {
            return -1;
        }
        return highlightedTabOneBased - 1;
    }

    public static void reset() {
        active = false;
        stage = 0;
        unlockedMask = (1 << TAB_COUNT) - 1;
        highlightedTabOneBased = 0;
        complete = false;
    }

    private static void parse(String payload) {
        try {
            String[] parts = payload.split("\\|", -1);
            if (parts.length < 6
                || Integer.parseInt(parts[0]) != 1
                || !"STATE".equals(parts[1])) {
                reset();
                return;
            }

            stage = Integer.parseInt(parts[2]);
            unlockedMask = Integer.parseInt(parts[3]);
            highlightedTabOneBased = Integer.parseInt(parts[4]);
            complete = Integer.parseInt(parts[5]) != 0;
            active = true;

            DisplayDebug.log(
                "MODERN_UI tutorial-state"
                    + " stage=" + stage
                    + " unlockedMask=0x"
                    + Integer.toHexString(unlockedMask)
                    + " highlighted="
                    + highlightedTabOneBased
                    + " complete=" + complete
            );
        } catch (Throwable ex) {
            reset();
            DisplayDebug.log(
                "MODERN_UI tutorial-state parse failed",
                ex
            );
        }
    }
}
