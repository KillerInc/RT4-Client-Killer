package rt4;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Discovers Modern UI style/add-on ZIP archives.
 *
 * Archives are read in place and are never extracted.
 */
public final class UiStyleRepository {
    private static final Map<String, UiStyleInfo> entries = new LinkedHashMap<>();
    private static final List<String> diagnostics = new ArrayList<>();

    private UiStyleRepository() {
    }

    public static synchronized void refresh() {
        entries.clear();
        diagnostics.clear();

        UiStyleInfo builtIn = UiStyleInfo.killerModern();
        entries.put(builtIn.id, builtIn);

        File directory = ModernUiPreferences.getStylesDirectory();
        if (!directory.exists() && !directory.mkdirs()) {
            diagnostics.add("Unable to create styles folder: " + directory.getAbsolutePath());
            return;
        }

        File[] archives = directory.listFiles(file ->
            file.isFile() && file.getName().toLowerCase(Locale.ROOT).endsWith(".zip")
        );
        if (archives == null) {
            return;
        }

        java.util.Arrays.sort(archives, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
        for (File archive : archives) {
            try {
                UiStyleInfo info = UiStyleInfo.read(archive);
                if (entries.containsKey(info.id)) {
                    diagnostics.add("Ignored " + archive.getName() + ": duplicate id " + info.id);
                    continue;
                }
                entries.put(info.id, info);
            } catch (Exception ex) {
                diagnostics.add("Ignored " + archive.getName() + ": " + ex.getMessage());
            }
        }
    }

    public static synchronized UiStyleInfo get(String id) {
        return entries.get(id);
    }

    public static synchronized UiStyleInfo getEffectiveStyle(String savedId) {
        UiStyleInfo selected = entries.get(savedId);
        if (selected != null && selected.isStyle()) {
            return selected;
        }
        return entries.get("killer-modern");
    }

    public static synchronized List<UiStyleInfo> getStyles() {
        List<UiStyleInfo> result = new ArrayList<>();
        for (UiStyleInfo info : entries.values()) {
            if (info.isStyle()) {
                result.add(info);
            }
        }
        result.sort(Comparator.comparing(info -> info.name, String.CASE_INSENSITIVE_ORDER));
        return Collections.unmodifiableList(result);
    }

    public static synchronized List<UiStyleInfo> getAddons() {
        List<UiStyleInfo> result = new ArrayList<>();
        for (UiStyleInfo info : entries.values()) {
            if (info.isAddon()) {
                result.add(info);
            }
        }
        result.sort(Comparator.comparingInt((UiStyleInfo info) -> info.priority)
            .thenComparing(info -> info.name, String.CASE_INSENSITIVE_ORDER));
        return Collections.unmodifiableList(result);
    }

    public static synchronized List<String> getDiagnostics() {
        return Collections.unmodifiableList(new ArrayList<>(diagnostics));
    }

    public static synchronized boolean requirementsSatisfied(UiStyleInfo addon, String styleId, Set<String> enabledAddons) {
        if (addon.requires == null || addon.requires.trim().isEmpty()) {
            return true;
        }

        String[] requirements = addon.requires.split(",");
        for (String requirement : requirements) {
            String id = requirement.trim();
            int operator = id.indexOf(">=");
            if (operator >= 0) {
                id = id.substring(0, operator).trim();
            }
            if (id.isEmpty()) {
                continue;
            }
            if (!id.equals(styleId) && !id.equals("killer-modern") && !enabledAddons.contains(id)) {
                return false;
            }
        }
        return true;
    }
}
