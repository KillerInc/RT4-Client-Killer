package rt4;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Exports a readable loose copy of the built-in Killer Modern UI archive.
 *
 * IMPORTANT: this directory is NOT part of asset resolution. It is only a
 * development/reference mirror so testers can inspect the baked pack.
 * /ui/packs/KillerModernUI.uipack inside the client JAR remains authoritative.
 */
public final class ModernUiDevelopmentMirror {
    private static final String PACK_RESOURCE = "/ui/packs/KillerModernUI.uipack";
    private static boolean synced;

    private ModernUiDevelopmentMirror() {
    }

    public static synchronized void sync() {
        if (synced) {
            return;
        }
        synced = true;

        File targetRoot = new File(ModernUiPreferences.getUiRootDirectory(), "killer-modern");
        if (!targetRoot.exists() && !targetRoot.mkdirs()) {
            DisplayDebug.log("MODERN_UI unable to create development mirror " + targetRoot.getAbsolutePath());
            return;
        }

        try (InputStream input = ModernUiDevelopmentMirror.class.getResourceAsStream(PACK_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("built-in UI pack is missing: " + PACK_RESOURCE);
            }

            try (ZipInputStream zip = new ZipInputStream(input)) {
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }

                    File target = safeTarget(targetRoot, entry.getName());
                    File parent = target.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Could not create " + parent);
                    }
                    Files.copy(zip, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            writeReadme(targetRoot);
            DisplayDebug.log(
                "MODERN_UI development asset mirror synced from " + PACK_RESOURCE
                    + " to " + targetRoot.getAbsolutePath()
            );
        } catch (Exception ex) {
            DisplayDebug.log("MODERN_UI development asset mirror failed: " + ex.getMessage());
        }
    }

    private static File safeTarget(File root, String relative) throws Exception {
        File target = new File(root, relative);
        String rootPath = root.getCanonicalPath() + File.separator;
        String targetPath = target.getCanonicalPath();
        if (!targetPath.startsWith(rootPath)) {
            throw new IllegalArgumentException("Unsafe built-in UI resource path: " + relative);
        }
        return target;
    }

    private static void writeReadme(File targetRoot) throws Exception {
        File readme = new File(targetRoot, "_README.txt");
        String text =
            "OSRS Client Killer Edition - Killer Modern UI development mirror\r\n"
            + "\r\n"
            + "This folder is a loose reference copy exported from the authoritative built-in archive:\r\n"
            + "  /ui/packs/KillerModernUI.uipack inside osrs-client-killer.jar\r\n"
            + "\r\n"
            + "THE RENDERER DOES NOT LOAD ASSETS FROM THIS FOLDER.\r\n"
            + "\r\n"
            + "To test personal asset changes, place matching paths under ui\\overrides\\.\r\n"
            + "To install distributable styles/add-ons, place .uipack archives under ui\\styles\\.\r\n"
            + "Legacy .zip style archives are still accepted for compatibility.\r\n";

        Files.write(readme.toPath(), text.getBytes(StandardCharsets.UTF_8));
    }
}
