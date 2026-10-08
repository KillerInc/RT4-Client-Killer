package rt4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Development-time file-level JS5 overrides.
 *
 * The ZIP contains already-encoded RuneScape file bytes. Files are loaded into
 * memory when the ZIP changes, so Windows does not keep the ZIP locked and it
 * can be replaced between edits while the client is running.
 */
public final class CacheOverrideManager {
    private static final Object LOCK = new Object();

    private static final String PROPERTY = "killer.cache.override";
    private static final String ENV = "KILLER_CACHE_OVERRIDE";

    private static final Pattern GENERIC =
        Pattern.compile("^cache/(\\d+)/(\\d+)/(\\d+)\\.dat$");
    private static final Pattern VERBOSE =
        Pattern.compile("^index-(\\d+)/archive-(\\d+)/file-(\\d+)\\.dat$");
    private static final Pattern DECODED_INTERFACES =
        Pattern.compile("^decoded-interfaces/interfaces/(\\d+)/raw/(\\d+)\\.dat$");

    private static final Map<String, byte[]> DATA = new HashMap<>();
    private static final Map<String, String> SOURCES = new HashMap<>();
    private static final Map<String, Integer> GROUP_CAPACITY = new HashMap<>();
    private static final Map<String, Integer> GROUP_HITS = new HashMap<>();
    private static final Set<String> LOGGED_GROUP_HITS = new HashSet<>();

    private static File activeFile;
    private static long activeModified = Long.MIN_VALUE;
    private static long activeLength = Long.MIN_VALUE;
    private static boolean missingAnnounced;

    private CacheOverrideManager() {
    }

    public static void startup() {
        synchronized (LOCK) {
            refreshLocked(true);
        }
    }

    public static boolean isActive() {
        synchronized (LOCK) {
            refreshLocked(false);
            return activeFile != null && !DATA.isEmpty();
        }
    }

    public static boolean hasFile(int archiveId, int groupId, int fileId) {
        if (archiveId < 0 || groupId < 0 || fileId < 0) {
            return false;
        }
        synchronized (LOCK) {
            refreshLocked(false);
            return DATA.containsKey(fileKey(archiveId, groupId, fileId));
        }
    }

    public static boolean hasGroup(int archiveId, int groupId) {
        if (archiveId < 0 || groupId < 0) {
            return false;
        }
        synchronized (LOCK) {
            refreshLocked(false);
            return GROUP_CAPACITY.containsKey(groupKey(archiveId, groupId));
        }
    }

    public static int getGroupCapacity(int archiveId, int groupId, int baseCapacity) {
        if (archiveId < 0 || groupId < 0) {
            return baseCapacity;
        }
        synchronized (LOCK) {
            refreshLocked(false);
            Integer overrideCapacity = GROUP_CAPACITY.get(groupKey(archiveId, groupId));
            return overrideCapacity == null
                ? baseCapacity
                : Math.max(baseCapacity, overrideCapacity);
        }
    }

    public static int getGroupHitCount(int archiveId, int groupId) {
        synchronized (LOCK) {
            Integer count = GROUP_HITS.get(groupKey(archiveId, groupId));
            return count == null ? 0 : count;
        }
    }

    public static byte[] read(int archiveId, int groupId, int fileId) {
        if (archiveId < 0 || groupId < 0 || fileId < 0) {
            return null;
        }

        synchronized (LOCK) {
            refreshLocked(false);
            String key = fileKey(archiveId, groupId, fileId);
            byte[] data = DATA.get(key);
            if (data == null) {
                return null;
            }

            String groupKey = groupKey(archiveId, groupId);
            int hit = GROUP_HITS.containsKey(groupKey)
                ? GROUP_HITS.get(groupKey) + 1
                : 1;
            GROUP_HITS.put(groupKey, hit);

            if (LOGGED_GROUP_HITS.add(groupKey) || (archiveId == 3 && groupId == 744 && (hit <= 5 || hit % 50 == 0))) {
                DisplayDebug.log(
                    "CACHE_OVERRIDE HIT index=" + archiveId
                        + " group=" + groupId
                        + " file=" + fileId
                        + " hit=" + hit
                        + " bytes=" + data.length
                        + " entry=" + SOURCES.get(key)
                );
            }

            return data.clone();
        }
    }

    private static void refreshLocked(boolean forceAnnouncement) {
        File resolved = resolveOverrideFile();
        if (resolved == null) {
            if (activeFile != null) {
                DisplayDebug.log("CACHE_OVERRIDE disabled; previous file disappeared: " + activeFile.getAbsolutePath());
            } else if (forceAnnouncement && !missingAnnounced) {
                DisplayDebug.log(
                    "CACHE_OVERRIDE inactive; no ZIP found. Set -D" + PROPERTY
                        + "=<zip> or " + ENV
                        + ", or place killer-overrides.zip beside the client."
                );
            }
            missingAnnounced = true;
            activeFile = null;
            activeModified = Long.MIN_VALUE;
            activeLength = Long.MIN_VALUE;
            DATA.clear();
            SOURCES.clear();
            GROUP_CAPACITY.clear();
            GROUP_HITS.clear();
            LOGGED_GROUP_HITS.clear();
            return;
        }

        long modified = resolved.lastModified();
        long length = resolved.length();
        if (activeFile != null
            && activeFile.equals(resolved)
            && activeModified == modified
            && activeLength == length
            && !DATA.isEmpty()) {
            return;
        }

        long start = System.nanoTime();
        Map<String, byte[]> newData = new HashMap<>();
        Map<String, String> newSources = new HashMap<>();
        Map<String, Integer> newCapacity = new HashMap<>();

        try (ZipFile zip = new ZipFile(resolved)) {
            java.util.Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                int[] ids = parseEntry(entry.getName());
                if (ids == null) {
                    continue;
                }

                String key = fileKey(ids[0], ids[1], ids[2]);
                byte[] bytes = readAll(zip.getInputStream(entry));
                if (newData.put(key, bytes) != null) {
                    DisplayDebug.log("CACHE_OVERRIDE duplicate key replaced: " + key + " entry=" + entry.getName());
                }
                newSources.put(key, entry.getName());

                String groupKey = groupKey(ids[0], ids[1]);
                int capacity = ids[2] + 1;
                Integer oldCapacity = newCapacity.get(groupKey);
                if (oldCapacity == null || capacity > oldCapacity) {
                    newCapacity.put(groupKey, capacity);
                }
            }
        } catch (Throwable ex) {
            DisplayDebug.log("CACHE_OVERRIDE failed to read ZIP; falling back to normal cache: " + resolved.getAbsolutePath(), ex);
            return;
        }

        DATA.clear();
        DATA.putAll(newData);
        SOURCES.clear();
        SOURCES.putAll(newSources);
        GROUP_CAPACITY.clear();
        GROUP_CAPACITY.putAll(newCapacity);
        GROUP_HITS.clear();
        LOGGED_GROUP_HITS.clear();

        activeFile = resolved;
        activeModified = modified;
        activeLength = length;
        missingAnnounced = false;

        DisplayDebug.log(
            "CACHE_OVERRIDE active file=" + resolved.getAbsolutePath()
                + " zipBytes=" + length
                + " overrideFiles=" + DATA.size()
                + " groups=" + GROUP_CAPACITY.size()
                + " loadMs=" + formatMs(System.nanoTime() - start)
        );
    }

    private static File resolveOverrideFile() {
        String configured = System.getProperty(PROPERTY);
        if (configured == null || configured.trim().isEmpty()) {
            configured = System.getenv(ENV);
        }

        File base = baseDirectory();
        if (configured != null && !configured.trim().isEmpty()) {
            File file = new File(configured.trim());
            if (!file.isAbsolute()) {
                file = new File(base, configured.trim());
            }
            return file.isFile() ? file : null;
        }

        File[] candidates = new File[] {
            new File(base, "killer-overrides.zip"),
            new File(new File(base, "cache-overrides"), "killer-overrides.zip"),
            new File(base, "runescape-interfaces-decoded-compressed.zip"),
            new File(base, "runescape-interfaces-decoded.zip")
        };
        for (File candidate : candidates) {
            if (candidate.isFile()) {
                return candidate;
            }
        }

        File cwd = new File(System.getProperty("user.dir", "."));
        if (!cwd.equals(base)) {
            File candidate = new File(cwd, "killer-overrides.zip");
            if (candidate.isFile()) {
                return candidate;
            }
        }
        return null;
    }

    private static File baseDirectory() {
        String home = System.getProperty("clientHomeOverride");
        return home == null || home.trim().isEmpty()
            ? new File(".")
            : new File(home);
    }

    private static int[] parseEntry(String name) {
        Matcher matcher = GENERIC.matcher(name);
        if (matcher.matches()) {
            return ids(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        matcher = VERBOSE.matcher(name);
        if (matcher.matches()) {
            return ids(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        matcher = DECODED_INTERFACES.matcher(name);
        if (matcher.matches()) {
            return ids("3", matcher.group(1), matcher.group(2));
        }

        return null;
    }

    private static int[] ids(String archive, String group, String file) {
        try {
            return new int[] {
                Integer.parseInt(archive),
                Integer.parseInt(group),
                Integer.parseInt(file)
            };
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static byte[] readAll(InputStream in) throws IOException {
        try (InputStream input = in; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        }
    }

    private static String fileKey(int archiveId, int groupId, int fileId) {
        return archiveId + ":" + groupId + ":" + fileId;
    }

    private static String groupKey(int archiveId, int groupId) {
        return archiveId + ":" + groupId;
    }

    private static String formatMs(long nanos) {
        return String.format(java.util.Locale.ROOT, "%.3f", nanos / 1_000_000.0D);
    }
}
