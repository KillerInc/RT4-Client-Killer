package rt4;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Single source of truth for Modern UI interaction geometry.
 *
 * Modern renderers register the exact rectangles they draw. InterfaceList then
 * routes the original RT4 Component event/script backend through those same
 * rectangles instead of relying on the obsolete cache-era coordinates.
 *
 * Component identity is used deliberately: runtime-created components can
 * share the same packed id while still representing different controls.
 */
public final class ModernUiHitboxRegistry {
    private static final Map<Component, Hitbox> hitboxes =
        new IdentityHashMap<>();

    private ModernUiHitboxRegistry() {
    }

    public static synchronized void registerAbsolute(
        Component component,
        int sourceX,
        int sourceY,
        int targetX,
        int targetY,
        int targetWidth,
        int targetHeight,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (component == null
            || targetWidth < 1
            || targetHeight < 1) {
            return;
        }

        hitboxes.put(
            component,
            new Hitbox(
                targetX - sourceX,
                targetY - sourceY,
                targetWidth,
                targetHeight,
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            )
        );
    }

    public static synchronized void registerOffset(
        Component component,
        int offsetX,
        int offsetY,
        int width,
        int height,
        int clipLeft,
        int clipTop,
        int clipRight,
        int clipBottom
    ) {
        if (component == null) {
            return;
        }

        hitboxes.put(
            component,
            new Hitbox(
                offsetX,
                offsetY,
                width,
                height,
                clipLeft,
                clipTop,
                clipRight,
                clipBottom
            )
        );
    }

    public static synchronized boolean has(Component component) {
        return component != null
            && ModernUiManager.isEnabled()
            && hitboxes.containsKey(component);
    }

    public static synchronized int adjustX(Component component, int x) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null ? x : x + hitbox.offsetX;
    }

    public static synchronized int adjustY(Component component, int y) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null ? y : y + hitbox.offsetY;
    }

    public static synchronized int width(Component component, int fallback) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.width <= 0
            ? fallback
            : hitbox.width;
    }

    public static synchronized int height(Component component, int fallback) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.height <= 0
            ? fallback
            : hitbox.height;
    }

    public static synchronized int clipLeft(
        Component component,
        int fallback
    ) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.clipLeft == Integer.MIN_VALUE
            ? fallback
            : hitbox.clipLeft;
    }

    public static synchronized int clipTop(
        Component component,
        int fallback
    ) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.clipTop == Integer.MIN_VALUE
            ? fallback
            : hitbox.clipTop;
    }

    public static synchronized int clipRight(
        Component component,
        int fallback
    ) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.clipRight == Integer.MIN_VALUE
            ? fallback
            : hitbox.clipRight;
    }

    public static synchronized int clipBottom(
        Component component,
        int fallback
    ) {
        Hitbox hitbox = hitboxes.get(component);
        return hitbox == null || hitbox.clipBottom == Integer.MIN_VALUE
            ? fallback
            : hitbox.clipBottom;
    }

    public static synchronized void clear() {
        hitboxes.clear();
    }

    private static final class Hitbox {
        private final int offsetX;
        private final int offsetY;
        private final int width;
        private final int height;
        private final int clipLeft;
        private final int clipTop;
        private final int clipRight;
        private final int clipBottom;

        private Hitbox(
            int offsetX,
            int offsetY,
            int width,
            int height,
            int clipLeft,
            int clipTop,
            int clipRight,
            int clipBottom
        ) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.width = width;
            this.height = height;
            this.clipLeft = clipLeft;
            this.clipTop = clipTop;
            this.clipRight = clipRight;
            this.clipBottom = clipBottom;
        }
    }
}
