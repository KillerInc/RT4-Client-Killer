package rt4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Persistent preferences for the new UI renderer.
 *
 * Kept separate from the revision-530 binary preferences file so style IDs,
 * add-on IDs and future renderer options are not constrained by the legacy
 * preference format. Disabling Modern UI never clears these values.
 */
public final class ModernUiPreferences {
    private static final Object LOCK = new Object();
    private static final String SETTINGS_FILE = "modern-ui.properties";

    private static boolean loaded;
    private static boolean enabled;
    private static String styleId = "killer-modern";
    private static float uiScale = 1.0F;
    private static float textScale = 1.0F;
    private static float iconScale = 1.0F;
    private static final LinkedHashSet<String> enabledAddons = new LinkedHashSet<>();

    private ModernUiPreferences() {
    }

    public static void load() {
        synchronized (LOCK) {
            if (loaded) {
                return;
            }
            loaded = true;

            File file = getSettingsFile();
            if (!file.isFile()) {
                return;
            }

            Properties properties = new Properties();
            try (FileInputStream input = new FileInputStream(file)) {
                properties.load(input);
                enabled = Boolean.parseBoolean(properties.getProperty("enabled", "false"));
                styleId = sanitizeId(properties.getProperty("style", "killer-modern"), "killer-modern");
                uiScale = parseScale(properties.getProperty("uiScale"), 1.0F);
                textScale = parseScale(properties.getProperty("textScale"), 1.0F);
                iconScale = parseScale(properties.getProperty("iconScale"), 1.0F);

                enabledAddons.clear();
                String addonList = properties.getProperty("addons", "");
                for (String entry : addonList.split(",")) {
                    String id = sanitizeId(entry, "");
                    if (!id.isEmpty()) {
                        enabledAddons.add(id);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Unable to read Modern UI settings: " + ex.getMessage());
            }
        }
    }

    public static void save() {
        synchronized (LOCK) {
            load();

            File root = getUiRootDirectory();
            if (!root.exists() && !root.mkdirs()) {
                System.err.println("Unable to create UI settings directory " + root.getAbsolutePath());
                return;
            }

            Properties properties = new Properties();
            properties.setProperty("enabled", Boolean.toString(enabled));
            properties.setProperty("style", styleId);
            properties.setProperty("uiScale", Float.toString(uiScale));
            properties.setProperty("textScale", Float.toString(textScale));
            properties.setProperty("iconScale", Float.toString(iconScale));
            properties.setProperty("addons", String.join(",", enabledAddons));

            File target = getSettingsFile();
            File temporary = new File(root, SETTINGS_FILE + ".tmp");
            try (FileOutputStream output = new FileOutputStream(temporary)) {
                properties.store(output, "OSRS Client Killer Edition - Modern UI");
            } catch (IOException ex) {
                System.err.println("Unable to write Modern UI settings: " + ex.getMessage());
                return;
            }

            try {
                Files.move(
                    temporary.toPath(),
                    target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                );
            } catch (IOException atomicMoveFailed) {
                try {
                    Files.move(temporary.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.err.println("Unable to replace Modern UI settings: " + ex.getMessage());
                }
            }
        }
    }

    public static File getUiRootDirectory() {
        String home = System.getProperty("clientHomeOverride");
        File base = home == null || home.trim().isEmpty() ? new File(".") : new File(home);
        return new File(base, "ui");
    }

    public static File getStylesDirectory() {
        return new File(getUiRootDirectory(), "styles");
    }

    private static File getSettingsFile() {
        return new File(getUiRootDirectory(), SETTINGS_FILE);
    }

    public static boolean isEnabled() {
        load();
        return enabled;
    }

    public static void setEnabled(boolean value) {
        load();
        enabled = value;
        save();
    }

    public static String getStyleId() {
        load();
        return styleId;
    }

    public static void setStyleId(String value) {
        load();
        styleId = sanitizeId(value, "killer-modern");
        save();
    }

    public static float getUiScale() {
        load();
        return uiScale;
    }

    public static void setUiScale(float value) {
        load();
        uiScale = clampScale(value);
        save();
    }

    public static float getTextScale() {
        load();
        return textScale;
    }

    public static void setTextScale(float value) {
        load();
        textScale = clampScale(value);
        save();
    }

    public static float getIconScale() {
        load();
        return iconScale;
    }

    public static void setIconScale(float value) {
        load();
        iconScale = clampScale(value);
        save();
    }

    public static Set<String> getEnabledAddons() {
        load();
        synchronized (LOCK) {
            return Collections.unmodifiableSet(new LinkedHashSet<>(enabledAddons));
        }
    }

    public static void setAddonEnabled(String id, boolean value) {
        load();
        String cleanId = sanitizeId(id, "");
        if (cleanId.isEmpty()) {
            return;
        }
        if (value) {
            enabledAddons.add(cleanId);
        } else {
            enabledAddons.remove(cleanId);
        }
        save();
    }

    private static String sanitizeId(String value, String fallback) {
        if (value == null) {
            return fallback;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? fallback : trimmed;
    }

    private static float parseScale(String value, float fallback) {
        try {
            return clampScale(Float.parseFloat(value));
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static float clampScale(float value) {
        if (value < 0.50F) {
            return 0.50F;
        }
        if (value > 4.00F) {
            return 4.00F;
        }
        return value;
    }
}
