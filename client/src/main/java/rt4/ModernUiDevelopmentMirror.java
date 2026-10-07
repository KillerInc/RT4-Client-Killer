package rt4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Exports a readable loose copy of the built-in Killer Modern UI resources.
 *
 * IMPORTANT: this directory is NOT part of asset resolution. It is only a
 * development/reference mirror so testers can inspect the built-in files.
 * The bundled JAR resources remain authoritative.
 */
public final class ModernUiDevelopmentMirror {
    private static final String RESOURCE_ROOT = "ui/killer-modern/";
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

        try {
            URL codeSource = ModernUiDevelopmentMirror.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();

            if ("file".equalsIgnoreCase(codeSource.getProtocol())) {
                File source = new File(codeSource.toURI());
                if (source.isFile()) {
                    syncFromJar(source, targetRoot);
                } else if (source.isDirectory()) {
                    syncFromDirectory(source.toPath().resolve(RESOURCE_ROOT), targetRoot.toPath());
                }
            }

            writeReadme(targetRoot);
            DisplayDebug.log("MODERN_UI development asset mirror synced to " + targetRoot.getAbsolutePath());
        } catch (Exception ex) {
            DisplayDebug.log("MODERN_UI development asset mirror failed: " + ex.getMessage());
        }
    }

    private static void syncFromJar(File jarFile, File targetRoot) throws Exception {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || !name.startsWith(RESOURCE_ROOT)) {
                    continue;
                }

                String relative = name.substring(RESOURCE_ROOT.length());
                if (relative.isEmpty()) {
                    continue;
                }

                File target = safeTarget(targetRoot, relative);
                File parent = target.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Could not create " + parent);
                }

                try (InputStream input = jar.getInputStream(entry)) {
                    Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    private static void syncFromDirectory(Path sourceRoot, Path targetRoot) throws Exception {
        if (!Files.isDirectory(sourceRoot)) {
            return;
        }

        try (java.util.stream.Stream<Path> stream = Files.walk(sourceRoot)) {
            stream.filter(Files::isRegularFile).forEach(source -> {
                try {
                    Path relative = sourceRoot.relativize(source);
                    File target = safeTarget(targetRoot.toFile(), relative.toString());
                    File parent = target.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Could not create " + parent);
                    }
                    Files.copy(source, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
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
            + "This folder is a loose reference copy of the built-in UI assets bundled in osrs-client-killer.jar.\r\n"
            + "It is refreshed from the JAR when the client starts.\r\n"
            + "\r\n"
            + "THE RENDERER DOES NOT LOAD ASSETS FROM THIS FOLDER.\r\n"
            + "\r\n"
            + "To test personal asset changes, place matching paths under ui\\overrides\\.\r\n"
            + "To build distributable styles/add-ons, use ZIP archives under ui\\styles\\.\r\n"
            + "The built-in style will eventually be baked into KillerModernUI.uipack.\r\n";

        Files.write(readme.toPath(), text.getBytes(StandardCharsets.UTF_8));
    }
}
