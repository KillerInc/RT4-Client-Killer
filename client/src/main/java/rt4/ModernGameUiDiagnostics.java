package rt4;

import java.util.HashSet;
import java.util.Set;

/**
 * Low-volume in-game UI discovery diagnostics. One line per interface per
 * process is enough to identify the next Modern screen without noisy logs.
 */
public final class ModernGameUiDiagnostics {
    private static final Set<Integer> logged =
        new HashSet<>();

    private ModernGameUiDiagnostics() {
    }

    public static synchronized void observe(
        int interfaceId,
        Component[] components
    ) {
        if (!ModernGameUi.isInGame()
            || components == null
            || !logged.add(interfaceId)) {
            return;
        }

        int[] types = new int[10];
        int clientCodes = 0;
        int images = 0;
        int inventories = 0;
        int models = 0;
        int texts = 0;
        int scrollContainers = 0;

        for (Component component : components) {
            if (component == null) {
                continue;
            }

            if (component.type >= 0
                && component.type < types.length) {
                types[component.type]++;
            }
            if (component.clientCode != 0) {
                clientCodes++;
            }
            if (component.type == 5) {
                images++;
            } else if (component.type == 2) {
                inventories++;
            } else if (component.type == 6) {
                models++;
            } else if (component.type == 4
                || component.type == 7
                || component.type == 8) {
                texts++;
            }
            if (component.type == 0
                && component.scrollMaxV > component.height) {
                scrollContainers++;
            }
        }

        DisplayDebug.log(
            "MODERN_UI ingame interface=" + interfaceId
                + " kind="
                + ModernGameInterfaceCatalog.kind(interfaceId)
                + " components=" + components.length
                + " images=" + images
                + " inventories=" + inventories
                + " models=" + models
                + " texts=" + texts
                + " clientCodes=" + clientCodes
                + " scrollContainers=" + scrollContainers
        );
    }

    public static synchronized void clear() {
        logged.clear();
    }
}
