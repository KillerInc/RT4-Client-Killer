package rt4;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maps the RT4 cache font IDs requested by Components to the equivalent
 * scalable RuneScape TTF assets used by Modern UI.
 *
 * The mapping is captured while JS5 Index 8 still has its group-name table.
 * Vanilla discards that table later during normal startup, so Modern UI stores
 * only the resolved integer IDs and never changes the JS5 lifecycle.
 */
public final class ModernUiFontRegistry {
    public static final String PLAIN_11 = "fonts/RuneScape-Plain-11.ttf";
    public static final String PLAIN_12 = "fonts/RuneScape-Plain-12.ttf";
    public static final String BOLD_12 = "fonts/RuneScape-Bold-12.ttf";
    public static final String QUILL_8 = "fonts/RuneScape-Quill-8.ttf";
    public static final String QUILL = "fonts/RuneScape-Quill.ttf";
    public static final String QUILL_CAPS = "fonts/RuneScape-Quill-Caps.ttf";
    public static final String FAIRY = "fonts/RuneScape-Fairy.ttf";
    public static final String FAIRY_LARGE = "fonts/RuneScape-Fairy-Large.ttf";
    public static final String BARBARIAN_ASSAULT = "fonts/RuneScape-Barbarian-Assault.ttf";
    public static final String SUROK = "fonts/RuneScape-Surok.ttf";

    public static final String DEFAULT = PLAIN_12;

    private static final Definition[] DEFINITIONS = {
        new Definition("p11_full", PLAIN_11),
        new Definition("p12_full", PLAIN_12),
        new Definition("b12_full", BOLD_12),
        new Definition("q8_full", QUILL_8),
        new Definition("quill_oblique_large", QUILL),
        new Definition("quill_caps_large", QUILL_CAPS),
        new Definition("lunar_alphabet", FAIRY),
        new Definition("lunar_alphabet_lrg", FAIRY_LARGE),
        new Definition("barbassault_font", BARBARIAN_ASSAULT),
        new Definition("surok_font", SUROK)
    };

    private static final Map<Integer, Definition> BY_ID = new LinkedHashMap<>();
    private static boolean captured;

    private ModernUiFontRegistry() {
    }

    public static synchronized void capture(Js5 spriteArchive) {
        if (captured || spriteArchive == null) {
            return;
        }

        for (Definition definition : DEFINITIONS) {
            int id;
            try {
                id = spriteArchive.getGroupId(JagString.parse(definition.cacheName));
            } catch (Throwable ex) {
                id = -1;
            }

            if (id < 0) {
                DisplayDebug.log(
                    "MODERN_UI font cache name not present name="
                        + definition.cacheName
                        + " ttf=" + definition.assetPath
                );
                continue;
            }

            BY_ID.put(id, definition);
            DisplayDebug.log(
                "MODERN_UI font mapped"
                    + " cache=" + definition.cacheName
                    + " id=" + id
                    + " ttf=" + definition.assetPath
            );
        }

        captured = true;
        DisplayDebug.log(
            "MODERN_UI font registry captured mapped=" + BY_ID.size()
        );
    }

    public static String resolveAsset(int legacyFontId) {
        Definition definition = BY_ID.get(legacyFontId);
        if (definition != null) {
            return definition.assetPath;
        }

        // These three IDs are already resolved by vanilla Sprites.init and
        // remain safe fallback identities if capture ran unusually early.
        if (legacyFontId == Sprites.p11FullId) return PLAIN_11;
        if (legacyFontId == Sprites.p12FullId) return PLAIN_12;
        if (legacyFontId == Sprites.b12FullId) return BOLD_12;
        return null;
    }

    public static String resolveCacheName(int legacyFontId) {
        Definition definition = BY_ID.get(legacyFontId);
        if (definition != null) {
            return definition.cacheName;
        }
        if (legacyFontId == Sprites.p11FullId) return "p11_full";
        if (legacyFontId == Sprites.p12FullId) return "p12_full";
        if (legacyFontId == Sprites.b12FullId) return "b12_full";
        return null;
    }

    public static boolean isMapped(int legacyFontId) {
        return resolveAsset(legacyFontId) != null;
    }

    private static final class Definition {
        private final String cacheName;
        private final String assetPath;

        private Definition(String cacheName, String assetPath) {
            this.cacheName = cacheName;
            this.assetPath = assetPath;
        }
    }
}
