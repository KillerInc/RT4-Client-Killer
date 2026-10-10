package rt4;

import java.util.Locale;

public final class ClientPerformanceDiagnostics {
    private static final long REPORT_INTERVAL_MS = 5000L;
    private static final long STALL_NS = 100_000_000L;
    private static final long UI_STALL_NS = 25_000_000L;

    private static long reportStarted = System.currentTimeMillis();

    private static long logicTotal;
    private static long logicMax;
    private static int logicCount;

    private static long renderTotal;
    private static long renderMax;
    private static int renderCount;

    private static long uiTotal;
    private static long uiMax;
    private static int uiCount;

    // Actual in-game 3D viewport after RT4 applies its viewport/FOV bounds.
    // Kept separately from the outer Canvas because the scene may render into
    // only part of the client window.
    private static volatile int gameRenderLeft = -1;
    private static volatile int gameRenderTop = -1;
    private static volatile int gameRenderWidth = -1;
    private static volatile int gameRenderHeight = -1;

    private ClientPerformanceDiagnostics() {
    }

    public static void recordGameRenderSize(
        int left,
        int top,
        int width,
        int height
    ) {
        if (left == gameRenderLeft
            && top == gameRenderTop
            && width == gameRenderWidth
            && height == gameRenderHeight) {
            return;
        }

        gameRenderLeft = left;
        gameRenderTop = top;
        gameRenderWidth = width;
        gameRenderHeight = height;

        int mode;
        try {
            mode = DisplayMode.getWindowMode();
        } catch (Throwable ignored) {
            mode = -1;
        }

        DisplayDebug.log(
            "GAME_RENDER_SIZE viewport=" + width + "x" + height
                + " origin=" + left + "," + top
                + " canvas=" + GameShell.canvasWidth + "x" + GameShell.canvasHeight
                + " frame=" + GameShell.frameWidth + "x" + GameShell.frameHeight
                + " mode=" + DisplayDebug.modeName(mode)
                + " renderer=" + (GlRenderer.enabled ? "GL" : "SOFTWARE")
        );

        ModernVectorCacheManager.onViewportChanged(width, height);
    }

    public static synchronized void recordLogic(long nanos) {
        logicTotal += nanos;
        logicCount++;
        if (nanos > logicMax) {
            logicMax = nanos;
        }
        if (nanos >= STALL_NS) {
            DisplayDebug.log("PERF_STALL phase=logic ms=" + ms(nanos) + " gameState=" + client.gameState);
        }
        maybeReport();
    }

    public static synchronized void recordRender(long nanos) {
        renderTotal += nanos;
        renderCount++;
        if (nanos > renderMax) {
            renderMax = nanos;
        }
        if (nanos >= STALL_NS) {
            DisplayDebug.log("PERF_STALL phase=render ms=" + ms(nanos) + " gameState=" + client.gameState);
        }
        maybeReport();
    }

    public static synchronized void recordInterfaceRender(int interfaceId, long nanos) {
        uiTotal += nanos;
        uiCount++;
        if (nanos > uiMax) {
            uiMax = nanos;
        }
        if (nanos >= UI_STALL_NS) {
            DisplayDebug.log(
                "PERF_UI_STALL interface=" + interfaceId
                    + " ms=" + ms(nanos)
                    + " modernUi=" + ModernUiManager.isEnabled()
            );
        }
    }

    private static void maybeReport() {
        long now = System.currentTimeMillis();
        long elapsed = now - reportStarted;
        if (elapsed < REPORT_INTERVAL_MS) {
            return;
        }

        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();

        int mode;
        try {
            mode = DisplayMode.getWindowMode();
        } catch (Throwable ignored) {
            mode = -1;
        }

        DisplayDebug.log(
            "PERF intervalMs=" + elapsed
                + " fps=" + String.format(Locale.ROOT, "%.1f", GameShell.framesPerSecond)
                + " logicCount=" + logicCount
                + " logicAvgMs=" + averageMs(logicTotal, logicCount)
                + " logicMaxMs=" + ms(logicMax)
                + " renderCount=" + renderCount
                + " renderAvgMs=" + averageMs(renderTotal, renderCount)
                + " renderMaxMs=" + ms(renderMax)
                + " uiCalls=" + uiCount
                + " uiAvgMs=" + averageMs(uiTotal, uiCount)
                + " uiMaxMs=" + ms(uiMax)
                + " memoryUsedMiB=" + mib(used)
                + " memoryCommittedMiB=" + mib(runtime.totalMemory())
                + " memoryMaxMiB=" + mib(runtime.maxMemory())
                + " gameState=" + client.gameState
                + " topInterface=" + InterfaceList.topLevelInterface
                + " canvas=" + GameShell.canvasWidth + "x" + GameShell.canvasHeight
                + " gameViewport=" + gameRenderWidth + "x" + gameRenderHeight
                + "@" + gameRenderLeft + "," + gameRenderTop
                + " mode=" + DisplayDebug.modeName(mode)
                + " renderer=" + (GlRenderer.enabled ? "GL" : "SOFTWARE")
                + " modernUi=" + ModernUiManager.isEnabled()
                + " cacheOverride=" + CacheOverrideManager.isActive()
        );

        logicTotal = 0L;
        logicMax = 0L;
        logicCount = 0;
        renderTotal = 0L;
        renderMax = 0L;
        renderCount = 0;
        uiTotal = 0L;
        uiMax = 0L;
        uiCount = 0;
        reportStarted = now;
    }

    private static String averageMs(long total, int count) {
        return count == 0 ? "0.000" : ms(total / count);
    }

    private static String ms(long nanos) {
        return String.format(Locale.ROOT, "%.3f", nanos / 1_000_000.0D);
    }

    private static long mib(long bytes) {
        return bytes / (1024L * 1024L);
    }
}
