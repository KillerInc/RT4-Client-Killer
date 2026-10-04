package rt4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Runtime audit log for the Killer scalable UI rewrite.
 *
 * This is intentionally separate from the old killer-font.log so a test run
 * cannot be confused with the previous font-generator implementation.
 */
public final class KillerUiLog {
    private static final Set<String> ONCE = new HashSet<String>();
    private static File logFile;
    private static boolean initialized;

    private KillerUiLog() {
    }

    public static synchronized void start() {
        if (initialized) {
            return;
        }
        initialized = true;

        try {
            File home = resolveClientHome();
            File logs = new File(home, "logs");
            if (!logs.exists()) {
                logs.mkdirs();
            }

            logFile = new File(logs, "killer-ui.log");
            if (logFile.exists()) {
                logFile.delete();
            }

            write("START Killer UI Rewrite");
            write("fontScale=" + KillerUi.scale());
            write("p11Target=" + KillerUi.fontTarget(11));
            write("p12Target=" + KillerUi.fontTarget(12));
            write("uiTextEngine=KillerUiText");
            write("fallbackPolicy=DISABLED_FAIL_HARD");
            write("stockRt4FontRole=WORLD_SCENE_ONLY");
            write("worldRenderer=UNCHANGED");
        } catch (Throwable ignored) {
            logFile = null;
        }
    }

    public static synchronized void write(String message) {
        if (!initialized) {
            start();
        }
        if (logFile == null) {
            return;
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(logFile, true);
            writer.write(timestamp());
            writer.write(" ");
            writer.write(message);
            writer.write(System.lineSeparator());
        } catch (IOException ignored) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static synchronized void once(String key, String message) {
        if (ONCE.add(key)) {
            write(message);
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    private static File resolveClientHome() {
        String override = System.getProperty("clientHomeOverride");
        if (override != null && !override.trim().isEmpty()) {
            return new File(override);
        }

        String userHome = System.getProperty("user.home", ".");
        String os = System.getProperty("os.name", "").toLowerCase();

        if (os.startsWith("windows")) {
            return new File(userHome, "2009scape");
        }
        if (os.startsWith("mac")) {
            return new File(userHome, "Library/Application Support/2009scape");
        }

        String xdg = System.getenv("XDG_DATA_HOME");
        if (xdg != null && !xdg.trim().isEmpty()) {
            return new File(xdg, "2009scape");
        }
        return new File(userHome, ".local/share/2009scape");
    }
}
