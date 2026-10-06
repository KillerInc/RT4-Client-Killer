package rt4;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DisplayDebug {
    private static final Object LOCK = new Object();
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static boolean headerWritten;

    private DisplayDebug() {}

    private static File getBaseDirectory() {
        String home = System.getProperty("clientHomeOverride");
        return home == null || home.trim().isEmpty() ? new File(".") : new File(home);
    }

    private static File[] getLogFiles() {
        File base = getBaseDirectory();
        File logs = new File(base, "logs");
        if (!logs.exists()) logs.mkdirs();
        return new File[] {
            new File(base, "display-debug.log"),
            new File(logs, "display-debug.log")
        };
    }

    private static void writeHeader(PrintWriter out) {
        if (headerWritten) return;
        headerWritten = true;
        out.println("============================================================");
        out.println("OSRS Client Killer Edition - Display/HD diagnostic session");
        out.println("Started: " + FORMAT.format(new Date()));
        out.println("java.version=" + System.getProperty("java.version"));
        out.println("java.vendor=" + System.getProperty("java.vendor"));
        out.println("java.vm.name=" + System.getProperty("java.vm.name"));
        out.println("os.name=" + System.getProperty("os.name"));
        out.println("os.version=" + System.getProperty("os.version"));
        out.println("os.arch=" + System.getProperty("os.arch"));
        out.println("sun.java2d.opengl=" + System.getProperty("sun.java2d.opengl"));
        out.println("clientHomeOverride=" + System.getProperty("clientHomeOverride"));
        out.println("============================================================");
    }

    public static void log(String message) {
        log(message, null);
    }

    public static void log(String message, Throwable throwable) {
        synchronized (LOCK) {
            String line = "[" + FORMAT.format(new Date()) + "][" + Thread.currentThread().getName() + "] " + message;
            boolean wroteAny = false;

            for (File file : getLogFiles()) {
                try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
                    writeHeader(out);
                    out.println(line);
                    if (throwable != null) throwable.printStackTrace(out);
                    wroteAny = true;
                } catch (Throwable ex) {
                    System.err.println("[DisplayDebug] Failed writing " + file.getAbsolutePath() + ": " + ex);
                }
            }

            if (!wroteAny) {
                System.err.println("[DisplayDebug] " + line);
                if (throwable != null) throwable.printStackTrace(System.err);
            }
        }
    }

    public static void startup() {
        File base = getBaseDirectory();
        log("CLIENT STARTUP DIAGNOSTIC ACTIVE; base=" + base.getAbsolutePath()
            + ", user.dir=" + System.getProperty("user.dir")
            + ", class=" + DisplayDebug.class.getProtectionDomain().getCodeSource().getLocation());
    }

    public static String canvasInfo(Canvas canvas) {
        if (canvas == null) return "canvas=null";
        StringBuilder text = new StringBuilder();
        text.append("canvas=").append(canvas.getWidth()).append("x").append(canvas.getHeight());
        text.append(", displayable=").append(canvas.isDisplayable());
        text.append(", visible=").append(canvas.isVisible());
        text.append(", ignoreRepaint=").append(canvas.getIgnoreRepaint());
        try {
            GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
            if (gc == null) {
                text.append(", graphicsConfig=null");
            } else {
                Rectangle bounds = gc.getBounds();
                GraphicsDevice device = gc.getDevice();
                text.append(", gcBounds=").append(bounds.width).append("x").append(bounds.height)
                    .append("@").append(bounds.x).append(",").append(bounds.y);
                if (device != null) {
                    java.awt.DisplayMode mode = device.getDisplayMode();
                    text.append(", device=").append(device.getIDstring());
                    if (mode != null) {
                        text.append(", desktopMode=").append(mode.getWidth()).append("x").append(mode.getHeight())
                            .append("x").append(mode.getBitDepth()).append("@").append(mode.getRefreshRate());
                    }
                }
            }
        } catch (Throwable ex) {
            text.append(", graphicsInfoError=").append(ex);
        }
        return text.toString();
    }

    public static String modeName(int mode) {
        switch (mode) {
            case 0: return "SD_FIXED";
            case 1: return "HD_FIXED";
            case 2: return "HD_RESIZABLE";
            case 3: return "FULLSCREEN";
            default: return "UNKNOWN(" + mode + ")";
        }
    }
}
