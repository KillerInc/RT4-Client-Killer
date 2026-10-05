package plugin;

import plugin.annotations.PluginMeta;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Metadata for an external Killer Edition client plugin.
 *
 * Modern plugins are JAR files containing META-INF/killer-plugin.properties.
 * The old annotation format remains accepted during migration.
 */
public final class PluginInfo {
    public final String id;
    public final String name;
    public final String author;
    public final String description;
    public final String version;
    public final String mainClass;

    public PluginInfo(String id, String name, String author, String description, String version, String mainClass) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.version = version;
        this.mainClass = mainClass;
    }

    public static PluginInfo load(Properties properties, String fallbackId) {
        String id = value(properties, "ID", fallbackId);
        return new PluginInfo(
            id,
            value(properties, "NAME", id),
            value(properties, "AUTHOR", "Unknown"),
            value(properties, "DESCRIPTION", ""),
            value(properties, "VERSION", "0.0.0"),
            value(properties, "MAIN_CLASS", "")
        );
    }

    public static PluginInfo load(InputStream input, String fallbackId) throws IOException {
        Properties properties = new Properties();
        properties.load(input);
        return load(properties, fallbackId);
    }

    public static PluginInfo loadFromClass(Class<?> clazz) {
        PluginMeta info = clazz.getAnnotation(PluginMeta.class);
        if (info == null) {
            return null;
        }
        return new PluginInfo(
            clazz.getName(),
            clazz.getSimpleName(),
            info.author(),
            info.description(),
            Double.toString(info.version()),
            clazz.getName()
        );
    }

    private static String value(Properties properties, String key, String fallback) {
        String value = properties.getProperty(key);
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PluginInfo)) return false;
        return Objects.equals(id, ((PluginInfo) other).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
