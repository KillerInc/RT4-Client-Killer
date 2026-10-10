package rt4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Modern-owned Audio Options screen.
 *
 * Vanilla components are inspected only to locate state/action backends.
 * Their positions are never used for Modern rendering. All visible geometry
 * comes from ModernAudioOptionsLayout, and the same rectangles are registered
 * with ModernUiInputRouter for input.
 */
public final class ModernAudioOptionsUi {
    private static final Set<Integer> loggedInterfaces = new HashSet<>();

    private ModernAudioOptionsUi() {
    }

    public static boolean isAudioOptionsActive(Component[] components) {
        return discover(components, 0, 0) != null;
    }

    public static void prepareInput(
        Component[] components,
        int parentX,
        int parentY
    ) {
        Backend backend = discover(components, parentX, parentY);
        if (backend == null) {
            return;
        }

        ModernAudioOptionsLayout layout =
            ModernAudioOptionsLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        ModernUiRect clip = layout.panel;

        bindSliderRow(
            backend.images,
            backend.musicRow,
            layout.musicSlider,
            clip
        );
        bindSliderRow(
            backend.images,
            backend.effectsRow,
            layout.effectsSlider,
            clip
        );
        bindSliderRow(
            backend.images,
            backend.areaRow,
            layout.areaSlider,
            clip
        );

        if (backend.monoHit != null) {
            ModernUiInputRouter.bind(
                backend.monoHit.component,
                layout.monoToggle,
                clip
            );
        }
        if (backend.stereoHit != null) {
            ModernUiInputRouter.bind(
                backend.stereoHit.component,
                layout.stereoToggle,
                clip
            );
        }

        ModernUiInputRouter.bind(
            backend.mainMenu.component,
            layout.mainMenu,
            clip
        );
    }

    public static void render(
        int interfaceId,
        Component[] components,
        int parentX,
        int parentY
    ) {
        Backend backend = discover(components, parentX, parentY);
        if (backend == null) {
            return;
        }

        ModernAudioOptionsLayout layout =
            ModernAudioOptionsLayout.create(
                GameShell.canvasWidth,
                GameShell.canvasHeight
            );

        // Rendering can happen before the next input pass after a screen
        // transition. Register the same canonical geometry here too.
        prepareInput(components, parentX, parentY);

        if (loggedInterfaces.add(interfaceId)) {
            DisplayDebug.log(
                "MODERN_UI audio-options declarative"
                    + " interface=" + interfaceId
                    + " panel=" + layout.panel
                    + " music=" + layout.musicSlider
                    + " effects=" + layout.effectsSlider
                    + " area=" + layout.areaSlider
                    + " mono=" + layout.monoToggle
                    + " stereo=" + layout.stereoToggle
                    + " mainMenu=" + layout.mainMenu
            );
        }

        drawAsset("audio-options/panel", layout.panel);
        drawAsset("audio-options/divider", layout.topDivider);
        drawAsset("audio-options/divider", layout.lowerDivider);

        drawCentered(
            display(backend.title.text),
            layout.title,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_TITLE,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        drawVolume(
            display(backend.music.text),
            layout.musicLabel,
            layout.musicSlider,
            Preferences.musicVolume,
            255
        );
        drawVolume(
            display(backend.effects.text),
            layout.effectsLabel,
            layout.effectsSlider,
            Preferences.soundEffectVolume,
            127
        );
        drawVolume(
            display(backend.area.text),
            layout.areaLabel,
            layout.areaSlider,
            Preferences.ambientSoundsVolume,
            127
        );

        drawChoice(
            display(backend.mono.text),
            layout.monoLabel,
            layout.monoToggle,
            !Preferences.stereo
        );
        drawChoice(
            display(backend.stereo.text),
            layout.stereoLabel,
            layout.stereoToggle,
            Preferences.stereo
        );

        boolean mainMenuHover =
            layout.mainMenu.contains(
                Mouse.lastMouseX,
                Mouse.lastMouseY
            );
        drawAsset(
            mainMenuHover
                ? "audio-options/button-active"
                : "audio-options/button",
            layout.mainMenu
        );
        drawCentered(
            display(backend.mainMenu.text),
            layout.mainMenu,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );
    }

    private static void drawVolume(
        String label,
        ModernUiRect labelRect,
        ModernUiRect slider,
        int value,
        int max
    ) {
        drawCentered(
            label,
            labelRect,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_LABEL,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        int trackY =
            slider.y
                + (slider.height
                    - ModernUiMetrics.AUDIO_SLIDER_HEIGHT) / 2;
        ModernUiRect track = new ModernUiRect(
            slider.x,
            trackY,
            slider.width,
            ModernUiMetrics.AUDIO_SLIDER_HEIGHT
        );
        drawAsset("audio-options/slider-track", track);

        int clamped = Math.max(0, Math.min(max, value));
        int left = slider.x + 10;
        int right = slider.right() - 10;
        int centerX =
            max <= 0
                ? left
                : left + (right - left) * clamped / max;
        ModernUiRect knob = new ModernUiRect(
            centerX - ModernUiMetrics.AUDIO_SLIDER_KNOB_WIDTH / 2,
            slider.y
                + (slider.height
                    - ModernUiMetrics.AUDIO_SLIDER_KNOB_HEIGHT) / 2,
            ModernUiMetrics.AUDIO_SLIDER_KNOB_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_KNOB_HEIGHT
        );
        drawAsset("audio-options/slider-knob", knob);
    }

    private static void drawChoice(
        String label,
        ModernUiRect labelRect,
        ModernUiRect toggle,
        boolean selected
    ) {
        drawCentered(
            label,
            labelRect,
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_LABEL,
            selected
                ? ModernUiMetrics.TEXT_GOLD
                : ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        drawAsset(
            selected
                ? "audio-options/toggle-on"
                : "audio-options/toggle-off",
            toggle
        );
    }

    private static void bindSliderRow(
        List<ImageEntry> images,
        LegacyRect sourceRow,
        ModernUiRect target,
        ModernUiRect clip
    ) {
        if (sourceRow == null || target == null) {
            return;
        }

        for (ImageEntry image : images) {
            LegacyRect source = image.rect;
            if (!intersects(source, sourceRow)) {
                continue;
            }
            if (source.width > 60 || source.height > 50) {
                continue;
            }

            int relativeX = source.x - sourceRow.x;
            int relativeY = source.y - sourceRow.y;

            int x =
                target.x
                    + relativeX * target.width
                        / Math.max(1, sourceRow.width);
            int y =
                target.y
                    + relativeY * target.height
                        / Math.max(1, sourceRow.height);
            int width = Math.max(
                1,
                source.width * target.width
                    / Math.max(1, sourceRow.width)
            );
            int height = Math.max(
                1,
                source.height * target.height
                    / Math.max(1, sourceRow.height)
            );

            ModernUiInputRouter.bind(
                image.component,
                new ModernUiRect(x, y, width, height),
                clip
            );
        }
    }

    private static Backend discover(
        Component[] components,
        int parentX,
        int parentY
    ) {
        List<TextEntry> texts = new ArrayList<>();
        List<ImageEntry> images = new ArrayList<>();
        collect(
            components,
            -1,
            parentX,
            parentY,
            texts,
            images
        );

        TextEntry title = find(texts, TextKind.TITLE);
        TextEntry music = find(texts, TextKind.MUSIC);
        TextEntry effects = find(texts, TextKind.EFFECTS);
        TextEntry area = find(texts, TextKind.AREA);
        TextEntry mono = find(texts, TextKind.MONO);
        TextEntry stereo = find(texts, TextKind.STEREO);
        TextEntry mainMenu = find(texts, TextKind.MAIN_MENU);

        if (title == null
            || music == null
            || effects == null
            || area == null
            || mono == null
            || stereo == null
            || mainMenu == null) {
            return null;
        }

        // Legacy geometry is used only to discover which original Components
        // own the actions. It never feeds the Modern layout.
        int legacyCenterX =
            (centerX(music.rect)
                + centerX(effects.rect)
                + centerX(area.rect)) / 3;

        LegacyRect musicRow = findImageRowBelow(
            music,
            effects,
            images,
            legacyCenterX
        );
        LegacyRect effectsRow = findImageRowBelow(
            effects,
            area,
            images,
            legacyCenterX
        );
        LegacyRect areaRow = findImageRowBelow(
            area,
            mono,
            images,
            legacyCenterX
        );

        ImageEntry monoHit =
            findChoiceImage(mono, mainMenu, images);
        ImageEntry stereoHit =
            findChoiceImage(stereo, mainMenu, images);

        return new Backend(
            title,
            music,
            effects,
            area,
            mono,
            stereo,
            mainMenu,
            images,
            musicRow,
            effectsRow,
            areaRow,
            monoHit,
            stereoHit
        );
    }

    private static void collect(
        Component[] components,
        int layer,
        int parentX,
        int parentY,
        List<TextEntry> texts,
        List<ImageEntry> images
    ) {
        if (components == null) {
            return;
        }

        for (Component component : components) {
            if (component == null || component.overlayer != layer) {
                continue;
            }
            if (component.if3 && InterfaceList.isHidden(component)) {
                continue;
            }
            if (component.type == 0
                && !component.if3
                && InterfaceList.isHidden(component)
                && InterfaceList.hoveredComponent != component) {
                continue;
            }

            int x = parentX + component.x;
            int y = parentY + component.y;
            LegacyRect rect = new LegacyRect(
                x,
                y,
                Math.max(1, component.width),
                Math.max(1, component.height)
            );

            if (component.type == 4 || component.type == 8) {
                JagString current = component.text;
                if (Cs1ScriptRunner.isTrue(component)
                    && component.activeText != null
                    && component.activeText.length() > 0) {
                    current = component.activeText;
                }
                if (!component.if3 && current != null) {
                    current = Cs1ScriptRunner.interpolate(
                        component,
                        current
                    );
                }
                if (current != null && current.length() > 0) {
                    texts.add(
                        new TextEntry(
                            component,
                            current.toString(),
                            rect
                        )
                    );
                }
            } else if (component.type == 5) {
                images.add(new ImageEntry(component, rect));
            }

            if (component.type == 0) {
                int childX = x - component.scrollX;
                int childY = y - component.scrollY;
                collect(
                    components,
                    component.id,
                    childX,
                    childY,
                    texts,
                    images
                );
                if (component.createdComponents != null) {
                    collect(
                        component.createdComponents,
                        component.id,
                        childX,
                        childY,
                        texts,
                        images
                    );
                }
            }
        }
    }

    private static TextEntry find(
        List<TextEntry> entries,
        TextKind kind
    ) {
        for (TextEntry entry : entries) {
            if (kind.matches(normalize(entry.text))) {
                return entry;
            }
        }
        return null;
    }

    private static LegacyRect findImageRowBelow(
        TextEntry label,
        TextEntry next,
        List<ImageEntry> images,
        int centerX
    ) {
        int minY =
            label.rect.y + Math.max(6, label.rect.height - 2);
        int maxY =
            Math.max(minY + 18, next.rect.y - 4);

        List<LegacyRect> candidates = new ArrayList<>();
        for (ImageEntry image : images) {
            LegacyRect rect = image.rect;
            int centerY = centerY(rect);
            if (centerY < minY || centerY >= maxY) {
                continue;
            }
            if (rect.width < 4
                || rect.width > 55
                || rect.height < 4
                || rect.height > 48) {
                continue;
            }
            if (Math.abs(centerX(rect) - centerX) > 120) {
                continue;
            }
            candidates.add(rect);
        }

        LegacyRect union = union(candidates);
        if (union == null
            || union.width < 70
            || union.width > 240
            || union.height > 58) {
            return null;
        }
        return union;
    }

    private static ImageEntry findChoiceImage(
        TextEntry label,
        TextEntry mainMenu,
        List<ImageEntry> images
    ) {
        ImageEntry best = null;
        int bestDistance = Integer.MAX_VALUE;
        int labelCenter = centerX(label.rect);
        int minY =
            label.rect.y + Math.max(4, label.rect.height - 2);
        int maxY = Math.max(minY + 18, mainMenu.rect.y - 4);

        for (ImageEntry image : images) {
            LegacyRect rect = image.rect;
            if (rect.width < 12
                || rect.width > 70
                || rect.height < 12
                || rect.height > 70) {
                continue;
            }
            int cy = centerY(rect);
            if (cy < minY || cy >= maxY) {
                continue;
            }
            int dx = Math.abs(centerX(rect) - labelCenter);
            if (dx > 55) {
                continue;
            }
            int dy = Math.max(0, rect.y - minY);
            int distance = dx * 2 + dy;
            if (distance < bestDistance) {
                bestDistance = distance;
                best = image;
            }
        }
        return best;
    }

    private static LegacyRect union(List<LegacyRect> rects) {
        if (rects == null || rects.isEmpty()) {
            return null;
        }

        int left = Integer.MAX_VALUE;
        int top = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MIN_VALUE;

        for (LegacyRect rect : rects) {
            left = Math.min(left, rect.x);
            top = Math.min(top, rect.y);
            right = Math.max(right, rect.x + rect.width);
            bottom = Math.max(bottom, rect.y + rect.height);
        }

        return new LegacyRect(
            left,
            top,
            Math.max(1, right - left),
            Math.max(1, bottom - top)
        );
    }

    private static boolean intersects(
        LegacyRect a,
        LegacyRect b
    ) {
        return a != null
            && b != null
            && a.x < b.x + b.width
            && a.x + a.width > b.x
            && a.y < b.y + b.height
            && a.y + a.height > b.y;
    }

    private static int centerX(LegacyRect rect) {
        return rect.x + rect.width / 2;
    }

    private static int centerY(LegacyRect rect) {
        return rect.y + rect.height / 2;
    }

    private static String display(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder(text.length());
        boolean insideTag = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                insideTag = true;
                continue;
            }
            if (ch == '>' && insideTag) {
                insideTag = false;
                out.append(' ');
                continue;
            }
            if (!insideTag) {
                out.append(
                    ch == '\n' || ch == '\r' || ch == '\t'
                        ? ' '
                        : ch
                );
            }
        }
        return out.toString().trim().replaceAll("\\s+", " ");
    }

    private static String normalize(String text) {
        return display(text).toLowerCase(java.util.Locale.ROOT);
    }

    private static void drawCentered(
        String text,
        ModernUiRect rect,
        String font,
        float size,
        int color,
        boolean shadow
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text,
            rect.x,
            rect.y,
            rect.width,
            rect.height,
            color,
            1,
            1,
            size,
            shadow
        );
    }

    private static void drawAsset(
        String path,
        ModernUiRect rect
    ) {
        ModernUiImage image = ModernUiAssetResolver.get(
            path,
            rect.width,
            rect.height
        );
        if (image != null) {
            image.render(rect.x, rect.y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + path,
                rect.x,
                rect.y,
                rect.width,
                rect.height
            );
        }
    }

    private enum TextKind {
        TITLE {
            @Override
            boolean matches(String text) {
                return text.equals("audio options");
            }
        },
        MUSIC {
            @Override
            boolean matches(String text) {
                return text.equals("music volume");
            }
        },
        EFFECTS {
            @Override
            boolean matches(String text) {
                return text.equals("sound effects volume")
                    || text.equals("sound effect volume");
            }
        },
        AREA {
            @Override
            boolean matches(String text) {
                return text.equals("area sounds volume")
                    || text.equals("area sound volume")
                    || text.equals("ambient sounds volume")
                    || text.equals("ambient sound volume");
            }
        },
        MONO {
            @Override
            boolean matches(String text) {
                return text.equals("mono");
            }
        },
        STEREO {
            @Override
            boolean matches(String text) {
                return text.equals("stereo");
            }
        },
        MAIN_MENU {
            @Override
            boolean matches(String text) {
                return text.equals("main menu");
            }
        };

        abstract boolean matches(String text);
    }

    private static final class Backend {
        private final TextEntry title;
        private final TextEntry music;
        private final TextEntry effects;
        private final TextEntry area;
        private final TextEntry mono;
        private final TextEntry stereo;
        private final TextEntry mainMenu;
        private final List<ImageEntry> images;
        private final LegacyRect musicRow;
        private final LegacyRect effectsRow;
        private final LegacyRect areaRow;
        private final ImageEntry monoHit;
        private final ImageEntry stereoHit;

        private Backend(
            TextEntry title,
            TextEntry music,
            TextEntry effects,
            TextEntry area,
            TextEntry mono,
            TextEntry stereo,
            TextEntry mainMenu,
            List<ImageEntry> images,
            LegacyRect musicRow,
            LegacyRect effectsRow,
            LegacyRect areaRow,
            ImageEntry monoHit,
            ImageEntry stereoHit
        ) {
            this.title = title;
            this.music = music;
            this.effects = effects;
            this.area = area;
            this.mono = mono;
            this.stereo = stereo;
            this.mainMenu = mainMenu;
            this.images = images;
            this.musicRow = musicRow;
            this.effectsRow = effectsRow;
            this.areaRow = areaRow;
            this.monoHit = monoHit;
            this.stereoHit = stereoHit;
        }
    }

    private static final class TextEntry {
        private final Component component;
        private final String text;
        private final LegacyRect rect;

        private TextEntry(
            Component component,
            String text,
            LegacyRect rect
        ) {
            this.component = component;
            this.text = text;
            this.rect = rect;
        }
    }

    private static final class ImageEntry {
        private final Component component;
        private final LegacyRect rect;

        private ImageEntry(
            Component component,
            LegacyRect rect
        ) {
            this.component = component;
            this.rect = rect;
        }
    }

    private static final class LegacyRect {
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private LegacyRect(
            int x,
            int y,
            int width,
            int height
        ) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
