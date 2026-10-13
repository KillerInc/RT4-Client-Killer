package rt4;

/**
 * Announces optional Killer client capabilities without changing the 530 login
 * handshake. The existing command packet is used as a silent capability
 * carrier; stock clients never send this token.
 */
public final class ModernServerCapabilities {
    private static final JagString QUEST_CHAT_CAPABILITY =
        JagString.parse("::killer_cap modern_quest_chat 1");
    private static final JagString TUTORIAL_UI_CAPABILITY =
        JagString.parse("::killer_cap modern_tutorial_ui 1");

    private static boolean questChatAdvertised;
    private static boolean tutorialUiAdvertised;

    private ModernServerCapabilities() {
    }

    public static void tick() {
        if (client.gameState != 30) {
            questChatAdvertised = false;
            tutorialUiAdvertised = false;
            ModernQuestChatServerState.reset();
            ModernTutorialState.reset();
            return;
        }

        if (!ModernUiManager.isEnabled()
            || Protocol.socket == null) {
            return;
        }

        if (!questChatAdvertised) {
            Cheat.sendCheatPacket(
                QUEST_CHAT_CAPABILITY
            );
            questChatAdvertised = true;

            DisplayDebug.log(
                "MODERN_UI server capability advertised"
                    + " modern_quest_chat=1"
            );
        }

        if (!tutorialUiAdvertised) {
            Cheat.sendCheatPacket(
                TUTORIAL_UI_CAPABILITY
            );
            tutorialUiAdvertised = true;

            DisplayDebug.log(
                "MODERN_UI server capability advertised"
                    + " modern_tutorial_ui=1"
            );
        }
    }
}
