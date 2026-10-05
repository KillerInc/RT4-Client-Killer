package plugin;

import plugin.api.API;
import plugin.api.MiniMenuEntry;
import rt4.*;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * External plugin loader for OSRS Client Killer Edition.
 *
 * Preferred format: one plugin per JAR in the configured plugins directory.
 * The JAR contains META-INF/killer-plugin.properties with ID, NAME, VERSION
 * and MAIN_CLASS. A manifest Killer-Plugin-Class attribute can supply
 * MAIN_CLASS instead.
 *
 * The old loose-class layout remains readable as a compatibility bridge, but
 * the modern launcher installs and updates JAR plugins only.
 */
public final class PluginRepository {
    private static final String MODERN_METADATA = "META-INF/killer-plugin.properties";
    private static final String MANIFEST_MAIN_CLASS = "Killer-Plugin-Class";

    private static final Map<PluginInfo, Plugin> loadedPlugins = new LinkedHashMap<>();
    private static final List<URLClassLoader> pluginClassLoaders = new ArrayList<>();
    public static HashMap<String, Object> pluginStorage = new HashMap<>();

    public static int lastMiniMenu;
    private static boolean shutdownHookInstalled;

    private PluginRepository() {}

    public static synchronized void registerPlugin(PluginInfo info, Plugin plugin) {
        loadedPlugins.put(info, plugin);
    }

    public static synchronized void reloadPlugins() {
        detachRegisteredInputListeners();

        Iterator<Map.Entry<PluginInfo, Plugin>> iterator = loadedPlugins.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PluginInfo, Plugin> entry = iterator.next();
            try {
                if (entry.getValue().OnPluginsReloaded()) {
                    continue;
                }
                entry.getValue()._shutDown();
            } catch (Throwable ex) {
                reportPluginFailure(entry.getKey(), "shutdown", ex);
            }
            iterator.remove();
        }

        closeClassLoaders();
        SaveStorage();
        Init();
    }

    public static synchronized void Init() {
        if (GlobalJsonConfig.instance == null) {
            return;
        }

        File pluginsDirectory = resolvePluginDirectory();
        if (!pluginsDirectory.exists() && !pluginsDirectory.mkdirs()) {
            System.err.println("Unable to create plugin directory " + pluginsDirectory.getAbsolutePath());
            return;
        }

        loadStorage(pluginsDirectory);
        installShutdownHookOnce();

        File[] jars = pluginsDirectory.listFiles(
            file -> file.isFile() && file.getName().toLowerCase(Locale.ROOT).endsWith(".jar")
        );
        if (jars != null) {
            Arrays.sort(jars, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
            for (File jar : jars) {
                loadJarPlugin(jar);
            }
        }

        File[] legacyDirectories = pluginsDirectory.listFiles(File::isDirectory);
        if (legacyDirectories != null) {
            Arrays.sort(legacyDirectories, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
            for (File directory : legacyDirectories) {
                loadLegacyPlugin(directory, pluginsDirectory);
            }
        }
    }

    private static File resolvePluginDirectory() {
        File configured = new File(GlobalJsonConfig.instance.pluginsFolder);
        if (configured.isAbsolute()) {
            return configured;
        }

        String home = System.getProperty("clientHomeOverride");
        if (home != null && !home.trim().isEmpty()) {
            return new File(home, GlobalJsonConfig.instance.pluginsFolder);
        }
        return configured;
    }

    private static void loadJarPlugin(File jarFile) {
        URLClassLoader loader = null;
        try {
            Properties properties = new Properties();
            String fallbackId = stripExtension(jarFile.getName());

            try (JarFile jar = new JarFile(jarFile)) {
                java.util.jar.JarEntry metadata = jar.getJarEntry(MODERN_METADATA);
                if (metadata == null) {
                    System.err.println("Skipping " + jarFile.getName() + ": missing " + MODERN_METADATA);
                    return;
                }
                try (InputStream input = jar.getInputStream(metadata)) {
                    properties.load(input);
                }

                if (properties.getProperty("MAIN_CLASS") == null) {
                    Manifest manifest = jar.getManifest();
                    if (manifest != null) {
                        Attributes attributes = manifest.getMainAttributes();
                        String mainClass = attributes.getValue(MANIFEST_MAIN_CLASS);
                        if (mainClass != null) {
                            properties.setProperty("MAIN_CLASS", mainClass);
                        }
                    }
                }
            }

            PluginInfo info = PluginInfo.load(properties, fallbackId);
            if (info.mainClass.isEmpty()) {
                System.err.println("Skipping " + jarFile.getName() + ": plugin main class is not declared");
                return;
            }
            if (containsPluginId(info.id)) {
                System.err.println("Skipping duplicate plugin id " + info.id + " from " + jarFile.getName());
                return;
            }

            loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, PluginRepository.class.getClassLoader());
            Class<?> clazz = Class.forName(info.mainClass, true, loader);
            if (!Plugin.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException(info.mainClass + " does not extend plugin.Plugin");
            }

            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
            plugin._startUp();
            registerPlugin(info, plugin);
            pluginClassLoaders.add(loader);
            loader = null;

            System.out.println(
                "Loaded plugin " + info.name + " " + info.version + " (" + info.id + ") from " + jarFile.getName()
            );
        } catch (Throwable ex) {
            System.err.println("Unable to load plugin JAR " + jarFile.getAbsolutePath());
            ex.printStackTrace();
        } finally {
            if (loader != null) {
                try {
                    loader.close();
                } catch (IOException ignored) {}
            }
        }
    }

    private static void loadLegacyPlugin(File directory, File pluginsDirectory) {
        File pluginClass = new File(directory, "plugin.class");
        if (!pluginClass.isFile()) {
            return;
        }

        URLClassLoader loader = null;
        try {
            loader = new URLClassLoader(
                new URL[]{pluginsDirectory.toURI().toURL()},
                PluginRepository.class.getClassLoader()
            );
            Class<?> clazz = Class.forName(directory.getName() + ".plugin", true, loader);
            PluginInfo info = PluginInfo.loadFromClass(clazz);

            if (info == null) {
                Properties properties = new Properties();
                File oldMetadata = new File(directory, "plugin.properties");
                if (!oldMetadata.isFile()) {
                    System.err.println("Skipping legacy plugin " + directory.getName() + ": no metadata");
                    return;
                }
                try (InputStream input = Files.newInputStream(oldMetadata.toPath())) {
                    properties.load(input);
                }
                properties.setProperty("MAIN_CLASS", clazz.getName());
                info = PluginInfo.load(properties, "legacy." + directory.getName());
            }

            if (containsPluginId(info.id)) {
                return;
            }

            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
            plugin._startUp();
            registerPlugin(info, plugin);
            pluginClassLoaders.add(loader);
            loader = null;
            System.out.println("Loaded legacy plugin " + directory.getName());
        } catch (Throwable ex) {
            System.err.println("Unable to load legacy plugin " + directory.getAbsolutePath());
            ex.printStackTrace();
        } finally {
            if (loader != null) {
                try {
                    loader.close();
                } catch (IOException ignored) {}
            }
        }
    }

    private static boolean containsPluginId(String id) {
        for (PluginInfo info : loadedPlugins.keySet()) {
            if (info.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static String stripExtension(String name) {
        int dot = name.lastIndexOf('.');
        return dot <= 0 ? name : name.substring(0, dot);
    }

    private static void detachRegisteredInputListeners() {
        if (GameShell.canvas != null) {
            for (KeyAdapter key : API.registeredKeyListeners) {
                GameShell.canvas.removeKeyListener(key);
            }
            for (MouseAdapter mouse : API.registeredMouseListeners) {
                GameShell.canvas.removeMouseListener(mouse);
                GameShell.canvas.removeMouseMotionListener(mouse);
            }
            for (MouseWheelListener wheel : API.registeredWheelListeners) {
                GameShell.canvas.removeMouseWheelListener(wheel);
            }
        }
        API.registeredWheelListeners.clear();
        API.registeredMouseListeners.clear();
        API.registeredKeyListeners.clear();
    }

    private static void closeClassLoaders() {
        for (URLClassLoader loader : pluginClassLoaders) {
            try {
                loader.close();
            } catch (IOException ignored) {}
        }
        pluginClassLoaders.clear();
    }

    private static void installShutdownHookOnce() {
        if (shutdownHookInstalled) {
            return;
        }
        shutdownHookInstalled = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (PluginRepository.class) {
                for (Map.Entry<PluginInfo, Plugin> entry : loadedPlugins.entrySet()) {
                    try {
                        entry.getValue()._shutDown();
                    } catch (Throwable ex) {
                        reportPluginFailure(entry.getKey(), "shutdown", ex);
                    }
                }
                SaveStorage();
                closeClassLoaders();
            }
        }, "killer-plugin-shutdown"));
    }

    @SuppressWarnings("unchecked")
    private static void loadStorage(File pluginsDirectory) {
        File storage = new File(pluginsDirectory, "plsto");
        if (!storage.isFile()) {
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(storage))) {
            Object data = input.readObject();
            if (data instanceof HashMap) {
                pluginStorage = (HashMap<String, Object>) data;
            }
        } catch (Exception ex) {
            System.err.println("Unable to load plugin storage: " + ex.getMessage());
        }
    }

    private static void reportPluginFailure(PluginInfo info, String phase, Throwable ex) {
        System.err.println("Plugin " + info.id + " failed during " + phase + ": " + ex);
        ex.printStackTrace();
    }

    private static List<Plugin> snapshot() {
        return new ArrayList<>(loadedPlugins.values());
    }

    public static void Update() {
        for (Plugin plugin : snapshot()) {
            try { plugin.Update(); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void Draw() {
        for (Plugin plugin : snapshot()) {
            try { plugin._draw(); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void LateDraw() {
        for (Plugin plugin : snapshot()) {
            try { plugin._lateDraw(); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void NPCOverheadDraw(Npc npc, int screenX, int screenY) {
        for (Plugin plugin : snapshot()) {
            try { plugin.NPCOverheadDraw(npc, screenX, screenY); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void PlayerOverheadDraw(Player player, int screenX, int screenY) {
        for (Plugin plugin : snapshot()) {
            try { plugin.PlayerOverheadDraw(player, screenX, screenY); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void ProcessCommand(JagString commandStr) {
        String[] tokens = commandStr.toString().split(" ");
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
        for (Plugin plugin : snapshot()) {
            try { plugin.ProcessCommand(tokens[0], args); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void ComponentDraw(int componentIndex, Component component, int screenX, int screenY) {
        for (Plugin plugin : snapshot()) {
            try { plugin.ComponentDraw(componentIndex, component, screenX, screenY); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnVarpUpdate(int id, int value) {
        for (Plugin plugin : snapshot()) {
            try { plugin.OnVarpUpdate(id, value); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnXPUpdate(int skill, int xp) {
        for (Plugin plugin : snapshot()) {
            try { plugin.OnXPUpdate(skill, xp); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnLogout() {
        for (Plugin plugin : snapshot()) {
            try { plugin.OnLogout(); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void DrawMiniMenu(MiniMenuEntry entry) {
        for (Plugin plugin : snapshot()) {
            try { plugin.DrawMiniMenu(entry); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnMiniMenuCreate() {
        API.customMiniMenuIndex = 0;
        MiniMenuEntry[] entries = API.GetMiniMenuEntries();
        for (Plugin plugin : snapshot()) {
            try { plugin.OnMiniMenuCreate(entries); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnLogin() {
        for (Plugin plugin : snapshot()) {
            try { plugin.OnLogin(); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static void OnKillingBlowNPC(int npcId, int x, int z) {
        for (Plugin plugin : snapshot()) {
            try { plugin.OnKillingBlowNPC(npcId, x, z); } catch (Throwable ex) { ex.printStackTrace(); }
        }
    }

    public static synchronized void SaveStorage() {
        if (GlobalJsonConfig.instance == null || !pluginStorage.containsKey("_keystoreDirty")) {
            return;
        }

        pluginStorage.remove("_keystoreDirty");
        File directory = resolvePluginDirectory();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(directory, "plsto")))) {
            output.writeObject(pluginStorage);
        } catch (Exception ex) {
            System.err.println("Unable to save plugin storage: " + ex.getMessage());
        }
    }
}
