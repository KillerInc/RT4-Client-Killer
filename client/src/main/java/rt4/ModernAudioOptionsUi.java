package rt4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Complete Modern rendering for the login Audio Options surface.
 *
 * The original RT4 components remain loaded and interactive so their CS2
 * scripts continue to own preference changes. This class replaces only the
 * legacy sprites/fonts with scalable vector artwork and TrueType text.
 */
public final class ModernAudioOptionsUi {
    private static final Set<Integer> loggedInterfaces = new HashSet<>();

    private ModernAudioOptionsUi() {
    }

    public static boolean isAudioOptionsActive(Component[] components) {
        Layout layout = analyze(components, 0, 0);
        return layout != null;
    }

    public static void render(
        int interfaceId,
        Component[] components,
        int parentX,
        int parentY
    ) {
        Layout layout = analyze(components, parentX, parentY);
        if (layout == null) {
            return;
        }

        if (loggedInterfaces.add(interfaceId)) {
            DisplayDebug.log(
                "MODERN_UI audio-options active interface=" + interfaceId
                    + " panel=" + layout.panel.x + "," + layout.panel.y
                    + " " + layout.panel.width + "x" + layout.panel.height
                    + " music=" + rectString(layout.musicSlider)
                    + " effects=" + rectString(layout.effectsSlider)
                    + " area=" + rectString(layout.areaSlider)
                    + " mono=" + rectString(layout.monoToggle)
                    + " stereo=" + rectString(layout.stereoToggle)
                    + " mainMenu=" + rectString(layout.mainMenuButton)
            );
        }

        drawAsset(
            "audio-options/panel",
            layout.panel.x,
            layout.panel.y,
            layout.panel.width,
            layout.panel.height
        );

        drawAsset(
            "audio-options/divider",
            layout.panel.x + ModernUiMetrics.AUDIO_PANEL_INSET,
            layout.panel.y + 58,
            layout.panel.width
                - ModernUiMetrics.AUDIO_PANEL_INSET * 2,
            4
        );

        int choiceDividerY = Math.min(
            layout.monoLabel.rect.y,
            layout.stereoLabel.rect.y
        ) + 4;
        drawAsset(
            "audio-options/divider",
            layout.panel.x + ModernUiMetrics.AUDIO_PANEL_INSET,
            choiceDividerY,
            layout.panel.width
                - ModernUiMetrics.AUDIO_PANEL_INSET * 2,
            4
        );

        drawCentered(
            display(layout.title.text),
            layout.panel.x,
            layout.panel.y + 28,
            layout.panel.width,
            28,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_TITLE,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        drawVolume(
            layout.musicLabel,
            layout.musicSlider,
            Preferences.musicVolume,
            255,
            -4
        );
        drawVolume(
            layout.effectsLabel,
            layout.effectsSlider,
            Preferences.soundEffectVolume,
            127,
            2
        );
        drawVolume(
            layout.areaLabel,
            layout.areaSlider,
            Preferences.ambientSoundsVolume,
            127,
            8
        );

        drawChoice(
            layout.monoLabel,
            layout.monoToggle,
            !Preferences.stereo,
            24
        );
        drawChoice(
            layout.stereoLabel,
            layout.stereoToggle,
            Preferences.stereo,
            24
        );

        Rect mainMenuVisual = new Rect(
            layout.mainMenuButton.x,
            layout.mainMenuButton.y + 36,
            layout.mainMenuButton.width,
            layout.mainMenuButton.height
        );
        boolean mainMenuHover =
            contains(mainMenuVisual, Mouse.lastMouseX, Mouse.lastMouseY);
        drawAsset(
            mainMenuHover
                ? "audio-options/button-active"
                : "audio-options/button",
            mainMenuVisual.x,
            mainMenuVisual.y,
            mainMenuVisual.width,
            mainMenuVisual.height
        );
        drawCentered(
            display(layout.mainMenu.text),
            mainMenuVisual.x,
            mainMenuVisual.y,
            mainMenuVisual.width,
            mainMenuVisual.height,
            ModernUiFontRegistry.PLAIN_12,
            ModernUiMetrics.FONT_BUTTON,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );
    }

    private static void drawVolume(
        TextEntry label,
        Rect slider,
        int value,
        int max,
        int yOffset
    ) {
        drawCentered(
            display(label.text),
            slider.x - 80,
            label.rect.y - 5 + yOffset,
            slider.width + 160,
            Math.max(18, label.rect.height),
            ModernUiFontRegistry.BOLD_12,
            ModernUiMetrics.FONT_LABEL,
            ModernUiMetrics.TEXT_PRIMARY,
            false
        );

        int trackY =
            slider.y
                + yOffset
                + (slider.height - ModernUiMetrics.AUDIO_SLIDER_HEIGHT) / 2;
        drawAsset(
            "audio-options/slider-track",
            slider.x,
            trackY,
            slider.width,
            ModernUiMetrics.AUDIO_SLIDER_HEIGHT
        );

        int clamped = Math.max(0, Math.min(max, value));
        int left = slider.x + 10;
        int right = slider.x + slider.width - 10;
        int centerX =
            max <= 0
                ? left
                : left + (right - left) * clamped / max;
        int knobX =
            centerX
                - ModernUiMetrics.AUDIO_SLIDER_KNOB_WIDTH / 2;
        int knobY =
            slider.y
                + yOffset
                + (slider.height
                    - ModernUiMetrics.AUDIO_SLIDER_KNOB_HEIGHT) / 2;

        drawAsset(
            "audio-options/slider-knob",
            knobX,
            knobY,
            ModernUiMetrics.AUDIO_SLIDER_KNOB_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_KNOB_HEIGHT
        );
    }

    private static void drawChoice(
        TextEntry label,
        Rect toggle,
        boolean selected,
        int yOffset
    ) {
        drawCentered(
            display(label.text),
            toggle.x - 42,
            label.rect.y + yOffset,
            toggle.width + 84,
            Math.max(18, label.rect.height),
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
            toggle.x,
            toggle.y + yOffset,
            toggle.width,
            toggle.height
        );
    }

    private static Layout analyze(
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

        int centerX =
            (rectCenterX(music.rect)
                + rectCenterX(effects.rect)
                + rectCenterX(area.rect)) / 3;

        int panelWidth = ModernUiMetrics.AUDIO_PANEL_WIDTH;
        int panelHeight = ModernUiMetrics.AUDIO_PANEL_HEIGHT;
        int panelX = centerX - panelWidth / 2;
        int panelY = title.rect.y - 40;

        panelX = Math.max(
            8,
            Math.min(
                panelX,
                Math.max(8, GameShell.canvasWidth - panelWidth - 8)
            )
        );
        panelY = Math.max(
            8,
            Math.min(
                panelY,
                Math.max(8, GameShell.canvasHeight - panelHeight - 8)
            )
        );

        Rect musicRow = findImageRowBelow(
            music,
            effects,
            images,
            centerX
        );
        Rect effectsRow = findImageRowBelow(
            effects,
            area,
            images,
            centerX
        );
        Rect areaRow = findImageRowBelow(
            area,
            mono,
            images,
            centerX
        );

        Rect musicSlider = sliderRect(music, musicRow, centerX);
        Rect effectsSlider = sliderRect(effects, effectsRow, centerX);
        Rect areaSlider = sliderRect(area, areaRow, centerX);

        Rect monoHit = findChoiceImage(
            mono,
            mainMenu,
            images
        );
        Rect stereoHit = findChoiceImage(
            stereo,
            mainMenu,
            images
        );

        Rect monoToggle = toggleRect(mono, monoHit);
        Rect stereoToggle = toggleRect(stereo, stereoHit);

        int mainMenuY =
            mainMenu.rect.y
                + (mainMenu.rect.height
                    - ModernUiMetrics.AUDIO_BUTTON_HEIGHT) / 2;
        Rect mainMenuButton = new Rect(
            centerX - ModernUiMetrics.AUDIO_BUTTON_WIDTH / 2,
            mainMenuY,
            ModernUiMetrics.AUDIO_BUTTON_WIDTH,
            ModernUiMetrics.AUDIO_BUTTON_HEIGHT
        );

        return new Layout(
            new Rect(panelX, panelY, panelWidth, panelHeight),
            title,
            music,
            effects,
            area,
            mono,
            stereo,
            mainMenu,
            musicSlider,
            effectsSlider,
            areaSlider,
            monoToggle,
            stereoToggle,
            mainMenuButton
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
            Rect rect = new Rect(
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
            String normalized = normalize(entry.text);
            if (kind.matches(normalized)) {
                return entry;
            }
        }
        return null;
    }

    private static Rect findImageRowBelow(
        TextEntry label,
        TextEntry next,
        List<ImageEntry> images,
        int centerX
    ) {
        int minY = label.rect.y + Math.max(6, label.rect.height - 2);
        int maxY = Math.max(
            minY + 18,
            next.rect.y - 4
        );

        List<Rect> candidates = new ArrayList<>();
        for (ImageEntry image : images) {
            Rect rect = image.rect;
            int centerY = rectCenterY(rect);

            if (centerY < minY || centerY >= maxY) {
                continue;
            }
            if (rect.width < 4
                || rect.width > 55
                || rect.height < 4
                || rect.height > 48) {
                continue;
            }
            if (Math.abs(rectCenterX(rect) - centerX) > 120) {
                continue;
            }
            candidates.add(rect);
        }

        Rect union = union(candidates);
        if (union == null
            || union.width < 70
            || union.width > 240
            || union.height > 58) {
            return null;
        }
        return union;
    }

    private static Rect findChoiceImage(
        TextEntry label,
        TextEntry mainMenu,
        List<ImageEntry> images
    ) {
        Rect best = null;
        int bestDistance = Integer.MAX_VALUE;
        int labelCenter = rectCenterX(label.rect);
        int minY = label.rect.y + Math.max(4, label.rect.height - 2);
        int maxY = Math.max(minY + 18, mainMenu.rect.y - 4);

        for (ImageEntry image : images) {
            Rect rect = image.rect;
            if (rect.width < 12
                || rect.width > 70
                || rect.height < 12
                || rect.height > 70) {
                continue;
            }
            int centerY = rectCenterY(rect);
            if (centerY < minY || centerY >= maxY) {
                continue;
            }
            int dx = Math.abs(rectCenterX(rect) - labelCenter);
            if (dx > 55) {
                continue;
            }
            int dy = Math.max(0, rect.y - minY);
            int distance = dx * 2 + dy;
            if (distance < bestDistance) {
                bestDistance = distance;
                best = rect;
            }
        }
        return best;
    }

    private static Rect sliderRect(
        TextEntry label,
        Rect legacyRow,
        int fallbackCenterX
    ) {
        int centerX =
            legacyRow == null
                ? fallbackCenterX
                : rectCenterX(legacyRow);
        int centerY =
            legacyRow == null
                ? label.rect.y + label.rect.height + 15
                : rectCenterY(legacyRow);

        return new Rect(
            centerX - ModernUiMetrics.AUDIO_SLIDER_WIDTH / 2,
            centerY - ModernUiMetrics.AUDIO_SLIDER_CONTAINER_HEIGHT / 2,
            ModernUiMetrics.AUDIO_SLIDER_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_CONTAINER_HEIGHT
        );
    }

    private static Rect toggleRect(
        TextEntry label,
        Rect legacyHit
    ) {
        int centerX =
            legacyHit == null
                ? rectCenterX(label.rect)
                : rectCenterX(legacyHit);
        int centerY =
            legacyHit == null
                ? label.rect.y + label.rect.height + 20
                : rectCenterY(legacyHit);

        return new Rect(
            centerX - ModernUiMetrics.AUDIO_TOGGLE_SIZE / 2,
            centerY - ModernUiMetrics.AUDIO_TOGGLE_SIZE / 2,
            ModernUiMetrics.AUDIO_TOGGLE_SIZE,
            ModernUiMetrics.AUDIO_TOGGLE_SIZE
        );
    }

    private static Rect union(List<Rect> rects) {
        if (rects == null || rects.isEmpty()) {
            return null;
        }

        int left = Integer.MAX_VALUE;
        int top = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MIN_VALUE;

        for (Rect rect : rects) {
            left = Math.min(left, rect.x);
            top = Math.min(top, rect.y);
            right = Math.max(right, rect.x + rect.width);
            bottom = Math.max(bottom, rect.y + rect.height);
        }

        return new Rect(
            left,
            top,
            Math.max(1, right - left),
            Math.max(1, bottom - top)
        );
    }

    private static int rectCenterX(Rect rect) {
        return rect.x + rect.width / 2;
    }

    private static int rectCenterY(Rect rect) {
        return rect.y + rect.height / 2;
    }

    private static boolean contains(
        Rect rect,
        int x,
        int y
    ) {
        return rect != null
            && x >= rect.x
            && x < rect.x + rect.width
            && y >= rect.y
            && y < rect.y + rect.height;
    }

    private static String rectString(Rect rect) {
        if (rect == null) {
            return "<none>";
        }
        return rect.x + "," + rect.y
            + " " + rect.width + "x" + rect.height;
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
                out.append(ch == '\n' || ch == '\r' || ch == '\t' ? ' ' : ch);
            }
        }
        return out.toString().trim().replaceAll("\\s+", " ");
    }

    private static String normalize(String text) {
        return display(text)
            .toLowerCase(java.util.Locale.ROOT);
    }

    private static void drawCentered(
        String text,
        int x,
        int y,
        int width,
        int height,
        String font,
        float size,
        int color,
        boolean shadow
    ) {
        ModernTrueTypeFont.drawInBox(
            font,
            text,
            x,
            y,
            width,
            height,
            color,
            1,
            1,
            size,
            shadow
        );
    }

    private static void drawAsset(
        String path,
        int x,
        int y,
        int width,
        int height
    ) {
        ModernUiImage image = ModernUiAssetResolver.get(
            path,
            Math.max(1, width),
            Math.max(1, height)
        );
        if (image != null) {
            image.render(x, y);
        } else {
            ModernUiRenderer.drawMissing(
                "asset:" + path,
                x,
                y,
                Math.max(1, width),
                Math.max(1, height)
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

    private static final class Layout {
        private final Rect panel;
        private final TextEntry title;
        private final TextEntry musicLabel;
        private final TextEntry effectsLabel;
        private final TextEntry areaLabel;
        private final TextEntry monoLabel;
        private final TextEntry stereoLabel;
        private final TextEntry mainMenu;
        private final Rect musicSlider;
        private final Rect effectsSlider;
        private final Rect areaSlider;
        private final Rect monoToggle;
        private final Rect stereoToggle;
        private final Rect mainMenuButton;

        private Layout(
            Rect panel,
            TextEntry title,
            TextEntry musicLabel,
            TextEntry effectsLabel,
            TextEntry areaLabel,
            TextEntry monoLabel,
            TextEntry stereoLabel,
            TextEntry mainMenu,
            Rect musicSlider,
            Rect effectsSlider,
            Rect areaSlider,
            Rect monoToggle,
            Rect stereoToggle,
            Rect mainMenuButton
        ) {
            this.panel = panel;
            this.title = title;
            this.musicLabel = musicLabel;
            this.effectsLabel = effectsLabel;
            this.areaLabel = areaLabel;
            this.monoLabel = monoLabel;
            this.stereoLabel = stereoLabel;
            this.mainMenu = mainMenu;
            this.musicSlider = musicSlider;
            this.effectsSlider = effectsSlider;
            this.areaSlider = areaSlider;
            this.monoToggle = monoToggle;
            this.stereoToggle = stereoToggle;
            this.mainMenuButton = mainMenuButton;
        }
    }

    private static final class TextEntry {
        private final Component component;
        private final String text;
        private final Rect rect;

        private TextEntry(
            Component component,
            String text,
            Rect rect
        ) {
            this.component = component;
            this.text = text;
            this.rect = rect;
        }
    }

    private static final class ImageEntry {
        private final Component component;
        private final Rect rect;

        private ImageEntry(
            Component component,
            Rect rect
        ) {
            this.component = component;
            this.rect = rect;
        }
    }

    private static final class Rect {
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private Rect(
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
