package rt4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Metadata read from style.info at the root of a Modern UI style archive.
 */
public final class UiStyleInfo {
    public final String id;
    public final String name;
    public final String version;
    public final String author;
    public final String description;
    public final String type;
    public final String renderer;
    public final String base;
    public final String requires;
    public final String provides;
    public final int priority;
    public final java.io.File archive;
    public final boolean builtIn;

    private UiStyleInfo(
        String id,
        String name,
        String version,
        String author,
        String description,
        String type,
        String renderer,
        String base,
        String requires,
        String provides,
        int priority,
        java.io.File archive,
        boolean builtIn
    ) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
        this.type = type;
        this.renderer = renderer;
        this.base = base;
        this.requires = requires;
        this.provides = provides;
        this.priority = priority;
        this.archive = archive;
        this.builtIn = builtIn;
    }

    public static UiStyleInfo killerModern() {
        return new UiStyleInfo(
            "killer-modern",
            "Killer Modern UI",
            "development",
            "KillerInc",
            "Built-in modern renderer base style.",
            "style",
            "killer-modern",
            "none",
            "",
            "windows,icons,fonts,textures",
            0,
            null,
            true
        );
    }

    public static UiStyleInfo read(java.io.File archive) throws IOException {
        try (ZipFile zip = new ZipFile(archive)) {
            ZipEntry entry = zip.getEntry("style.info");
            if (entry == null || entry.isDirectory()) {
                throw new IOException("style.info is missing from the archive root");
            }

            Properties p = new Properties();
            try (InputStream input = zip.getInputStream(entry)) {
                p.load(input);
            }

            String id = required(p, "id");
            String name = required(p, "name");
            String type = p.getProperty("type", "style").trim().toLowerCase(Locale.ROOT);
            if (!type.equals("style") && !type.equals("addon")) {
                throw new IOException("type must be style or addon");
            }

            String renderer = p.getProperty("renderer", "killer-modern").trim();
            if (!renderer.equals("killer-modern") && !renderer.equals("modern")) {
                throw new IOException("unsupported renderer: " + renderer);
            }

            int priority = 100;
            try {
                priority = Integer.parseInt(p.getProperty("priority", type.equals("addon") ? "200" : "100").trim());
            } catch (NumberFormatException ex) {
                throw new IOException("priority must be an integer");
            }

            return new UiStyleInfo(
                id,
                name,
                p.getProperty("version", "1.0").trim(),
                p.getProperty("author", "Unknown").trim(),
                p.getProperty("description", "").trim(),
                type,
                renderer,
                p.getProperty("base", type.equals("style") ? "killer-modern" : "").trim(),
                p.getProperty("requires", "").trim(),
                p.getProperty("provides", "").trim(),
                priority,
                archive,
                false
            );
        }
    }

    public boolean isAddon() {
        return "addon".equals(type);
    }

    public boolean isStyle() {
        return "style".equals(type);
    }

    private static String required(Properties p, String key) throws IOException {
        String value = p.getProperty(key, "").trim();
        if (value.isEmpty()) {
            throw new IOException(key + " is required");
        }
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
