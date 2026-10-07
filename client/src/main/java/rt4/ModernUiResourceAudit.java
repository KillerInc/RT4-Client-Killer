package rt4;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Development-only inventory of legacy resources that the Modern UI may need
 * to classify. This reads JS5 index metadata only; it never decodes or renders
 * every sprite at startup.
 *
 * Archive 8 contains sprite/glyph source groups used by the client.
 * Archive 13 contains font data keyed by the same legacy font IDs.
 *
 * Work is intentionally split into small batches on the render thread so the
 * audit cannot stall login or introduce a second thread into the vanilla JS5
 * cache system.
 */
public final class ModernUiResourceAudit {
    private static final int GROUPS_PER_TICK = 64;
    private static final SimpleDateFormat FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static boolean started;
    private static boolean finished;
    private static int spriteCursor;
    private static int fontCursor;
    private static int spriteGroups;
    private static int fontGroups;
    private static int spriteUnclassified;
    private static int fontUnmapped;

    private ModernUiResourceAudit() {
    }

    public static void tick() {
        if (finished || !ModernUiPreferences.isEnabled()) {
            return;
        }

        Js5 sprites = client.js5Archive8;
        Js5 fonts = client.js5Archive13;
        if (sprites == null || fonts == null) {
            return;
        }

        int spriteCapacity = sprites.capacity();
        int fontCapacity = fonts.capacity();
        if (spriteCapacity <= 0 || fontCapacity <= 0) {
            return;
        }

        if (!started) {
            started = true;
            append(
                "============================================================\n"
                    + "OSRS Client Killer Edition - Modern UI Resource Audit\n"
                    + "Started: " + FORMAT.format(new Date()) + "\n"
                    + "Sprite archive: JS5 Index 8, capacity=" + spriteCapacity + "\n"
                    + "Font-data archive: JS5 Index 13, capacity=" + fontCapacity + "\n"
                    + "NOTE: metadata inventory only; resources are not force-decoded.\n"
                    + "============================================================\n"
            );
            DisplayDebug.log(
                "MODERN_UI resource audit started"
                    + " spriteCapacity=" + spriteCapacity
                    + " fontCapacity=" + fontCapacity
                    + " report=" + getReportFile().getAbsolutePath()
            );
        }

        int budget = GROUPS_PER_TICK;
        while (budget > 0 && spriteCursor < spriteCapacity) {
            auditSpriteGroup(sprites, spriteCursor++);
            budget--;
        }

        while (budget > 0 && spriteCursor >= spriteCapacity && fontCursor < fontCapacity) {
            auditFontGroup(fonts, fontCursor++);
            budget--;
        }

        if (spriteCursor >= spriteCapacity && fontCursor >= fontCapacity) {
            finished = true;
            append(
                "============================================================\n"
                    + "SUMMARY spriteGroups=" + spriteGroups
                    + " spriteUnclassified=" + spriteUnclassified
                    + " fontGroups=" + fontGroups
                    + " fontUnmapped=" + fontUnmapped + "\n"
                    + "Completed: " + FORMAT.format(new Date()) + "\n"
                    + "============================================================\n"
            );
            DisplayDebug.log(
                "MODERN_UI resource audit complete"
                    + " spriteGroups=" + spriteGroups
                    + " spriteUnclassified=" + spriteUnclassified
                    + " fontGroups=" + fontGroups
                    + " fontUnmapped=" + fontUnmapped
                    + " report=" + getReportFile().getAbsolutePath()
            );
        }
    }

    private static void auditSpriteGroup(Js5 archive, int group) {
        int files = archive.getGroupCapacity(group);
        if (files <= 0) {
            return;
        }

        spriteGroups++;
        String name = knownSpriteName(group);
        String classification;

        if (group == Sprites.p11FullId
            || group == Sprites.p12FullId
            || group == Sprites.b12FullId) {
            classification = "LEGACY_FONT_GLYPHS";
        } else {
            classification = "UNCLASSIFIED";
            spriteUnclassified++;
        }

        append(
            "RESOURCE kind=sprite"
                + " archive=8"
                + " group=" + group
                + " files=" + files
                + (name == null ? "" : " name=\"" + name + "\"")
                + " modern=" + classification
                + "\n"
        );
    }

    private static void auditFontGroup(Js5 archive, int group) {
        int files = archive.getGroupCapacity(group);
        if (files <= 0) {
            return;
        }

        fontGroups++;
        String name = knownFontName(group);
        boolean mapped =
            group == Sprites.p11FullId
                || group == Sprites.p12FullId
                || group == Sprites.b12FullId;

        if (!mapped) {
            fontUnmapped++;
        }

        append(
            "RESOURCE kind=font-data"
                + " archive=13"
                + " group=" + group
                + " files=" + files
                + (name == null ? "" : " name=\"" + name + "\"")
                + " modern=" + (mapped ? "TTF_MAPPED" : "UNMAPPED")
                + "\n"
        );
    }

    private static String knownFontName(int group) {
        if (group == Sprites.p11FullId) return "p11_full";
        if (group == Sprites.p12FullId) return "p12_full";
        if (group == Sprites.b12FullId) return "b12_full";
        return null;
    }

    private static String knownSpriteName(int group) {
        String font = knownFontName(group);
        if (font != null) return font;
        if (group == Sprites.mapfunctionId) return "mapfunction";
        if (group == Sprites.hitmarksId) return "hitmarks";
        if (group == Sprites.hitbarId) return "hitbar_default";
        if (group == Sprites.headiconsPkId) return "headicons_pk";
        if (group == Sprites.headiconsPrayerId) return "headicons_prayer";
        if (group == Sprites.hintHeadId) return "hint_headicons";
        if (group == Sprites.hintMapMarkId) return "hint_mapmarkers";
        if (group == Sprites.mapflagId) return "mapflag";
        if (group == Sprites.crossId) return "cross";
        if (group == Sprites.mapdotsId) return "mapdots";
        if (group == Sprites.scrollbarId) return "scrollbar";
        if (group == Sprites.nameIconsId) return "name_icons";
        if (group == Sprites.floorShadowsId) return "floorshadows";
        if (group == Sprites.compassId) return "compass";
        if (group == Sprites.hintMapEdgeId) return "hint_mapedge";
        return null;
    }

    private static File getReportFile() {
        String home = System.getProperty("clientHomeOverride");
        File base =
            home == null || home.trim().isEmpty()
                ? new File(".")
                : new File(home);
        File logs = new File(base, "logs");
        if (!logs.exists()) {
            logs.mkdirs();
        }
        return new File(logs, "modern-ui-resource-audit.log");
    }

    private static void append(String text) {
        File file = getReportFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            out.print(text);
        } catch (Throwable ex) {
            DisplayDebug.log(
                "MODERN_UI resource audit write failed file="
                    + file.getAbsolutePath(),
                ex
            );
        }
    }
}
