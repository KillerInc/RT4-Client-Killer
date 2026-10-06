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

    private static File getLogFile() {
        String home = System.getProperty("clientHomeOverride");
        File base = home == null || home.trim().isEmpty() ? new File(".") : new File(home);
        File logs = new File(base, "logs");
        if (!logs.exists()) logs.mkdirs();
        return new File(logs, "display-debug.log");
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
        synchronized (LOCK) {
            try (PrintWriter out = new PrintWriter(new FileWriter(getLogFile(), true))) {
                writeHeader(out);
                out.println("[" + FORMAT.format(new Date()) + "][" + Thread.currentThread().getName() + "] " + message);
            } catch (Throwable ignored) {}
        }
    }

    public static void log(String message, Throwable throwable) {
        synchronized (LOCK) {
            try (PrintWriter out = new PrintWriter(new FileWriter(getLogFile(), true))) {
                writeHeader(out);
                out.println("[" + FORMAT.format(new Date()) + "][" + Thread.currentThread().getName() + "] " + message);
                if (throwable != null) throwable.printStackTrace(out);
            } catch (Throwable ignored) {}
        }
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
