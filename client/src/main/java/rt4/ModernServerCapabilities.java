package rt4;

/**
 * Announces optional Killer client capabilities without changing the 530 login
 * handshake. The existing command packet is used as a silent capability
 * carrier; stock clients never send this token.
 */
public final class ModernServerCapabilities {
    private static final JagString QUEST_CHAT_CAPABILITY =
        JagString.parse("::killer_cap modern_quest_chat 1");

    private static boolean questChatAdvertised;

    private ModernServerCapabilities() {
    }

    public static void tick() {
        if (client.gameState != 30) {
            questChatAdvertised = false;
            ModernQuestChatServerState.reset();
            return;
        }

        if (!ModernUiManager.isEnabled()
            || questChatAdvertised
            || Protocol.socket == null) {
            return;
        }

        Cheat.sendCheatPacket(
            QUEST_CHAT_CAPABILITY
        );
        questChatAdvertised = true;

        DisplayDebug.log(
            "MODERN_UI server capability advertised"
                + " modern_quest_chat=1"
        );
    }
}
