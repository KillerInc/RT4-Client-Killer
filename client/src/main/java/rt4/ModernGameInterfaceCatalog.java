package rt4;

/**
 * Known RT4/530 in-game interface groups. Classification is intentionally
 * isolated from rendering so each category can gain its own Modern layout
 * without coupling unrelated screens.
 */
public final class ModernGameInterfaceCatalog {
    public enum Kind {
        TOP_LEVEL,
        INVENTORY,
        EQUIPMENT,
        STATS,
        PRAYER,
        MAGIC,
        OPTIONS,
        EMOTES,
        FRIENDS,
        IGNORE,
        CLAN,
        BANK,
        SHOP,
        TRADE,
        CHAT,
        QUEST_CHAT,
        DIALOG,
        WORLD_MAP,
        GRAND_EXCHANGE,
        GRAND_EXCHANGE_SEARCH,
        CHARACTER_SETUP,
        OTHER
    }

    private ModernGameInterfaceCatalog() {
    }

    public static Kind kind(int interfaceId) {
        switch (interfaceId) {
            case 548:
            case 549:
            case 746:
                return Kind.TOP_LEVEL;
            case 149:
                return Kind.INVENTORY;
            case 387:
                return Kind.EQUIPMENT;
            case 320:
                return Kind.STATS;
            case 271:
                return Kind.PRAYER;
            case 192:
            case 193:
            case 388:
            case 430:
                return Kind.MAGIC;
            case 261:
                return Kind.OPTIONS;
            case 464:
                return Kind.EMOTES;
            case 550:
                return Kind.FRIENDS;
            case 551:
                return Kind.IGNORE;
            case 589:
                return Kind.CLAN;
            case 762:
            case 763:
            case 767:
                return Kind.BANK;
            case 620:
            case 621:
                return Kind.SHOP;
            case 334:
            case 335:
            case 336:
            case 626:
            case 637:
            case 639:
                return Kind.TRADE;
            // 137/173 carry actual chat content. 752/754/757 are chatbox
            // infrastructure (input/filter/auxiliary layers) and must not be
            // rendered as independent Modern chat windows.
            case 137:
            case 173:
                return Kind.CHAT;
            // Cache chatbox dialogue families. These are the classic
            // NPC/player/option dialogue interfaces used by the server.
            // Modern UI renders them in the separate movable Quest Chat
            // window rather than treating them as the normal chat panel.
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 210:
            case 211:
            case 212:
            case 213:
            // Dialogue option interfaces (2-5 choices).
            case 228:
            case 230:
            case 232:
            case 234:
            case 241:
            case 242:
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 248:
            case 314:
                return Kind.QUEST_CHAT;
            // The server's actual Grand Exchange item search interface.
            case 389:
                return Kind.GRAND_EXCHANGE_SEARCH;
            case 771:
                return Kind.CHARACTER_SETUP;
            case 755:
                return Kind.WORLD_MAP;
            case 105:
            case 107:
            case 109:
            case 642:
            case 643:
            case 644:
            case 645:
                return Kind.GRAND_EXCHANGE;
            default:
                return Kind.OTHER;
        }
    }
}
