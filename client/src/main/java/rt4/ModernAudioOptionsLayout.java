package rt4;

/**
 * Declarative geometry for the Modern Audio Options screen.
 *
 * Nothing in this class reads vanilla Component coordinates. Editing the
 * numbers here moves rendering and input together.
 */
public final class ModernAudioOptionsLayout {
    public static final int PANEL_VERTICAL_BIAS = 70;

    public final ModernUiRect panel;
    public final ModernUiRect title;
    public final ModernUiRect topDivider;

    public final ModernUiRect musicLabel;
    public final ModernUiRect musicSlider;
    public final ModernUiRect effectsLabel;
    public final ModernUiRect effectsSlider;
    public final ModernUiRect areaLabel;
    public final ModernUiRect areaSlider;

    public final ModernUiRect lowerDivider;
    public final ModernUiRect monoLabel;
    public final ModernUiRect monoToggle;
    public final ModernUiRect stereoLabel;
    public final ModernUiRect stereoToggle;
    public final ModernUiRect mainMenu;

    private ModernAudioOptionsLayout(
        ModernUiRect panel,
        ModernUiRect title,
        ModernUiRect topDivider,
        ModernUiRect musicLabel,
        ModernUiRect musicSlider,
        ModernUiRect effectsLabel,
        ModernUiRect effectsSlider,
        ModernUiRect areaLabel,
        ModernUiRect areaSlider,
        ModernUiRect lowerDivider,
        ModernUiRect monoLabel,
        ModernUiRect monoToggle,
        ModernUiRect stereoLabel,
        ModernUiRect stereoToggle,
        ModernUiRect mainMenu
    ) {
        this.panel = panel;
        this.title = title;
        this.topDivider = topDivider;
        this.musicLabel = musicLabel;
        this.musicSlider = musicSlider;
        this.effectsLabel = effectsLabel;
        this.effectsSlider = effectsSlider;
        this.areaLabel = areaLabel;
        this.areaSlider = areaSlider;
        this.lowerDivider = lowerDivider;
        this.monoLabel = monoLabel;
        this.monoToggle = monoToggle;
        this.stereoLabel = stereoLabel;
        this.stereoToggle = stereoToggle;
        this.mainMenu = mainMenu;
    }

    public static ModernAudioOptionsLayout create(
        int canvasWidth,
        int canvasHeight
    ) {
        int panelWidth = ModernUiMetrics.AUDIO_PANEL_WIDTH;
        int panelHeight = ModernUiMetrics.AUDIO_PANEL_HEIGHT;
        int panelX = Math.max(8, (canvasWidth - panelWidth) / 2);
        panelX = Math.min(
            panelX,
            Math.max(8, canvasWidth - panelWidth - 8)
        );

        int panelY =
            (canvasHeight - panelHeight) / 2 + PANEL_VERTICAL_BIAS;
        panelY = Math.max(
            8,
            Math.min(
                panelY,
                Math.max(8, canvasHeight - panelHeight - 8)
            )
        );

        ModernUiRect panel =
            new ModernUiRect(panelX, panelY, panelWidth, panelHeight);
        int centerX = panel.centerX();

        ModernUiRect title =
            new ModernUiRect(panelX, panelY + 20, panelWidth, 28);
        ModernUiRect topDivider =
            new ModernUiRect(
                panelX + ModernUiMetrics.AUDIO_PANEL_INSET,
                panelY + 58,
                panelWidth - ModernUiMetrics.AUDIO_PANEL_INSET * 2,
                4
            );

        ModernUiRect musicLabel =
            new ModernUiRect(centerX - 130, panelY + 70, 260, 18);
        ModernUiRect musicSlider =
            slider(centerX, panelY + 92);

        ModernUiRect effectsLabel =
            new ModernUiRect(centerX - 130, panelY + 132, 260, 18);
        ModernUiRect effectsSlider =
            slider(centerX, panelY + 154);

        ModernUiRect areaLabel =
            new ModernUiRect(centerX - 130, panelY + 194, 260, 18);
        ModernUiRect areaSlider =
            slider(centerX, panelY + 216);

        ModernUiRect lowerDivider =
            new ModernUiRect(
                panelX + ModernUiMetrics.AUDIO_PANEL_INSET,
                panelY + 250,
                panelWidth - ModernUiMetrics.AUDIO_PANEL_INSET * 2,
                4
            );

        int choiceOffset = 45;
        int labelWidth = 90;
        ModernUiRect monoLabel =
            new ModernUiRect(
                centerX - choiceOffset - labelWidth / 2,
                panelY + 270,
                labelWidth,
                18
            );
        ModernUiRect stereoLabel =
            new ModernUiRect(
                centerX + choiceOffset - labelWidth / 2,
                panelY + 270,
                labelWidth,
                18
            );

        ModernUiRect monoToggle =
            new ModernUiRect(
                centerX - choiceOffset
                    - ModernUiMetrics.AUDIO_TOGGLE_SIZE / 2,
                panelY + 292,
                ModernUiMetrics.AUDIO_TOGGLE_SIZE,
                ModernUiMetrics.AUDIO_TOGGLE_SIZE
            );
        ModernUiRect stereoToggle =
            new ModernUiRect(
                centerX + choiceOffset
                    - ModernUiMetrics.AUDIO_TOGGLE_SIZE / 2,
                panelY + 292,
                ModernUiMetrics.AUDIO_TOGGLE_SIZE,
                ModernUiMetrics.AUDIO_TOGGLE_SIZE
            );

        ModernUiRect mainMenu =
            new ModernUiRect(
                centerX - ModernUiMetrics.AUDIO_BUTTON_WIDTH / 2,
                panelY + 336,
                ModernUiMetrics.AUDIO_BUTTON_WIDTH,
                ModernUiMetrics.AUDIO_BUTTON_HEIGHT
            );

        return new ModernAudioOptionsLayout(
            panel,
            title,
            topDivider,
            musicLabel,
            musicSlider,
            effectsLabel,
            effectsSlider,
            areaLabel,
            areaSlider,
            lowerDivider,
            monoLabel,
            monoToggle,
            stereoLabel,
            stereoToggle,
            mainMenu
        );
    }

    private static ModernUiRect slider(int centerX, int y) {
        return new ModernUiRect(
            centerX - ModernUiMetrics.AUDIO_SLIDER_WIDTH / 2,
            y,
            ModernUiMetrics.AUDIO_SLIDER_WIDTH,
            ModernUiMetrics.AUDIO_SLIDER_CONTAINER_HEIGHT
        );
    }
}
