package rt4;

/**
 * Renders dynamic 3D model content inside Modern UI without using legacy UI
 * sprites/fonts/chrome. Component model fields remain the state/data backend.
 */
public final class ModernModelContentRenderer {
    private ModernModelContentRenderer() {
    }

    public static boolean render(
        Component component,
        int x,
        int y
    ) {
        if (component == null) {
            return false;
        }

        boolean active =
            Cs1ScriptRunner.isTrue(component);
        int sequenceId =
            active
                ? component.activeModelSeqId
                : component.modelSeqId;

        Model model = null;
        int verticalOffset = 0;

        if (component.objId != -1) {
            ObjType obj = ObjTypeList.get(component.objId);
            if (obj != null) {
                obj = obj.getCountVariant(component.objCount);
                SeqType sequence =
                    sequenceId == -1
                        ? null
                        : SeqTypeList.get(sequenceId);
                model = obj.getModel(
                    component.seqNextFrame,
                    component.seqCycle,
                    sequence,
                    1,
                    component.seqFrame
                );
                if (model != null) {
                    verticalOffset =
                        -model.getMinY() / 2;
                }
            }
        } else if (component.modelType == 5) {
            if (component.modelId == -1) {
                model =
                    PlayerAppearance.DEFAULT.getBodyModel(
                        null,
                        -1,
                        null,
                        null,
                        0,
                        -1,
                        0,
                        -1,
                        -1
                    );
            } else {
                int playerIndex =
                    component.modelId & 0x7FF;
                if (playerIndex == PlayerList.selfId) {
                    playerIndex = 2047;
                }

                Player player =
                    PlayerList.players[playerIndex];
                SeqType sequence =
                    sequenceId == -1
                        ? null
                        : SeqTypeList.get(sequenceId);

                if (player != null
                    && (int) player.username.encode37() << 11
                        == (component.modelId & 0xFFFFF800)) {
                    model =
                        player.appearance.getBodyModel(
                            null,
                            -1,
                            null,
                            sequence,
                            0,
                            -1,
                            0,
                            component.seqFrame,
                            0
                        );
                }
            }
        } else if (sequenceId == -1) {
            model =
                component.getModel(
                    -1,
                    null,
                    -1,
                    0,
                    active,
                    PlayerList.self == null
                        ? PlayerAppearance.DEFAULT
                        : PlayerList.self.appearance
                );
        } else {
            SeqType sequence =
                SeqTypeList.get(sequenceId);
            model =
                component.getModel(
                    component.seqNextFrame,
                    sequence,
                    component.seqFrame,
                    component.seqCycle,
                    active,
                    PlayerList.self == null
                        ? PlayerAppearance.DEFAULT
                        : PlayerList.self.appearance
                );
        }

        if (model == null) {
            return false;
        }

        int scaleX =
            component.modelViewportWidth > 0
                ? component.width * 256
                    / component.modelViewportWidth
                : 256;
        int scaleY =
            component.modelViewportHeight > 0
                ? component.height * 256
                    / component.modelViewportHeight
                : 256;

        int centerX =
            x
                + component.width / 2
                + (scaleX * component.modelOriginX >> 8);
        int centerY =
            y
                + component.height / 2
                + (scaleY * component.modelOriginY >> 8);

        int sin =
            MathUtils.sin[component.modelXAngle]
                * component.modelZoom >> 16;
        int cos =
            component.modelZoom
                * MathUtils.cos[component.modelXAngle] >> 16;

        if (GlRenderer.enabled) {
            if (component.modelOrtho) {
                GlRenderer.setupModelPreview(
                    centerX,
                    centerY,
                    component.modelZoom,
                    component.modelViewAngle,
                    scaleX,
                    scaleY
                );
            } else {
                GlRenderer.setFullscreenCamera(
                    centerX,
                    centerY,
                    scaleX,
                    scaleY
                );
                GlRenderer.setDepthBias(
                    component.modelNearClip,
                    (float) component.modelViewAngle * 1.5F
                );
            }

            GlRenderer.restoreLighting();
            GlRenderer.setDepthTestEnabled(true);
            GlRenderer.setFogEnabled(false);
            FogManager.init(Preferences.brightness);

            if (ScriptRunner.glSceneNeedsRender) {
                GlRaster.resetClipRegion();
                GlRenderer.clearDepthBuffer();
                ScriptRunner.glSceneNeedsRender = false;
            }

            if (component.modelTransparent) {
                GlRenderer.disableDepthMask();
            }

            if (component.if3) {
                model.setCamera(
                    component.modelYAngle,
                    component.modelYOffset,
                    component.modelXAngle,
                    component.modelXOffset,
                    component.modelZOffset
                        + sin
                        + verticalOffset,
                    component.modelZOffset + cos,
                    -1L
                );
            } else {
                model.setCamera(
                    component.modelYAngle,
                    0,
                    component.modelXAngle,
                    0,
                    sin,
                    cos,
                    -1L
                );
            }

            if (component.modelTransparent) {
                GlRenderer.enableDepthMask();
            }
        } else {
            Rasteriser.setBounds(
                centerX,
                centerY
            );

            if (!component.if3) {
                model.setCamera(
                    component.modelYAngle,
                    0,
                    component.modelXAngle,
                    0,
                    sin,
                    cos,
                    -1L
                );
            } else if (component.modelOrtho) {
                ((SoftwareModel) model).renderOnInterface(
                    component.modelYAngle,
                    component.modelYOffset,
                    component.modelXAngle,
                    component.modelXOffset,
                    component.modelZOffset
                        + verticalOffset
                        + sin,
                    cos + component.modelZOffset,
                    component.modelZoom
                );
            } else {
                model.setCamera(
                    component.modelYAngle,
                    component.modelYOffset,
                    component.modelXAngle,
                    component.modelXOffset,
                    component.modelZOffset
                        + sin
                        + verticalOffset,
                    component.modelZOffset + cos,
                    -1L
                );
            }

            Rasteriser.prepareOffsets();
        }

        return true;
    }
}
