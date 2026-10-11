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
            case 137:
            case 173:
            case 752:
            case 754:
            case 757:
                return Kind.CHAT;
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
            case 214:
            case 215:
            case 216:
            case 217:
            case 218:
            case 219:
            case 220:
            case 241:
            case 242:
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 248:
            case 314:
            case 389:
                return Kind.QUEST_CHAT;
            case 771:
                return Kind.DIALOG;
            case 755:
                return Kind.WORLD_MAP;
            default:
                return Kind.OTHER;
        }
    }
}
