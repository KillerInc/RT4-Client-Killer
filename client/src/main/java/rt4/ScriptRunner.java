package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;
import plugin.api.API;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public final class ScriptRunner {

	@OriginalMember(owner = "client!ab", name = "j", descriptor = "[Lclient!na;")
	public static final JagString[] stringStack = new JagString[1000];
	@OriginalMember(owner = "client!uj", name = "t", descriptor = "[I")
	public static final int[] intStack = new int[1000];
	@OriginalMember(owner = "client!fl", name = "Q", descriptor = "Lclient!na;")
	public static final JagString EMPTY_STRING = JagString.parse("");
	@OriginalMember(owner = "client!bb", name = "A", descriptor = "Lclient!na;")
	public static final JagString DOUBLE_COLON = JagString.parse("::");
	@OriginalMember(owner = "client!be", name = "ib", descriptor = "Lclient!na;")
	public static final JagString EVENT_OPBASE = JagString.parse("event_opbase");
	@OriginalMember(owner = "client!da", name = "O", descriptor = "Lclient!na;")
	public static final JagString ERROR_VIA = JagString.parse("(U0a )2 via: ");
	@OriginalMember(owner = "client!fl", name = "H", descriptor = "Lclient!na;")
	public static final JagString IMG0 = JagString.parse("<img=0>");
	@OriginalMember(owner = "client!nd", name = "b", descriptor = "Lclient!na;")
	public static final JagString CLIENTSCRIPT_ERROR = JagString.parse("Clientscript error in: ");
	@OriginalMember(owner = "client!hm", name = "R", descriptor = "Lclient!na;")
	public static final JagString IMG1 = JagString.parse("<img=1>");
	@OriginalMember(owner = "client!hn", name = "K", descriptor = "Ljava/util/Calendar;")
	public static final Calendar aCalendar2 = Calendar.getInstance();
	@OriginalMember(owner = "client!kk", name = "m", descriptor = "Lclient!na;")
	public static final JagString CS_ERROR = JagString.parse("Clientscript error )2 check log for details");
	@OriginalMember(owner = "client!fe", name = "nc", descriptor = "[Lclient!hj;")
	public static final GoSubFrame[] callStack = new GoSubFrame[50];
	@OriginalMember(owner = "client!ee", name = "j", descriptor = "[I")
	public static final int[] globalArrayLengths = new int[5];
	@OriginalMember(owner = "client!oe", name = "i", descriptor = "[[I")
	public static final int[][] globalArrays = new int[5][5000];
	@OriginalMember(owner = "client!rl", name = "eb", descriptor = "Lclient!na;")
	public static final JagString ERROR_IN = JagString.parse("(U0a )2 in: ");
	@OriginalMember(owner = "client!fe", name = "I", descriptor = "Lclient!na;")
	public static final JagString SPACE = JagString.parse(" ");
	@OriginalMember(owner = "client!dc", name = "M", descriptor = "Lclient!na;")
	public static final JagString SLASH = JagString.parse(")4");
	@OriginalMember(owner = "client!he", name = "gb", descriptor = "Lclient!na;")
	public static final JagString WWW = JagString.parse("www");
	@OriginalMember(owner = "client!e", name = "Tc", descriptor = "Lclient!na;")
	public static final JagString WWW_QA = JagString.parse("www)2wtqa");
	@OriginalMember(owner = "client!lk", name = "J", descriptor = "Lclient!na;")
	public static final JagString PARAM_SETTINGS = JagString.parse(")4p=");
	@OriginalMember(owner = "client!en", name = "x", descriptor = "Lclient!na;")
	public static final JagString HTTP_PREFIX = JagString.parse("http:)4)4");
	@OriginalMember(owner = "client!gf", name = "I", descriptor = "Lclient!na;")
	public static final JagString DOMAIN_LANG = JagString.parse(")3runescape)3com)4l=");
	@OriginalMember(owner = "client!v", name = "a", descriptor = "Lclient!na;")
	public static final JagString PARAM_AFFILIATE = JagString.parse(")4a=");
	@OriginalMember(owner = "client!ch", name = "C", descriptor = "[[I")
	public static final int[][] tileEntityCounts = new int[104][104];
	@OriginalMember(owner = "client!n", name = "e", descriptor = "Lclient!na;")
	public static final JagString DASH = JagString.parse(")2");
	@OriginalMember(owner = "client!je", name = "U", descriptor = "Lclient!na;")
	public static final JagString SHOWING_VIDEO_AD = JagString.parse("showingVideoAd");
	@OriginalMember(owner = "client!ob", name = "p", descriptor = "Lclient!na;")
	public static final JagString ERROR_NONEXISTENT_GOSUB = JagString.parse("(U0a )2 non)2existant gosub script)2num: ");
	@OriginalMember(owner = "client!af", name = "m", descriptor = "Lclient!na;")
	public static final JagString BR_TAG = JagString.parse("<br>");
	@OriginalMember(owner = "client!jh", name = "n", descriptor = "Lclient!bd;")
	public static QuickChatPhrase activePhrase;
	@OriginalMember(owner = "client!wf", name = "j", descriptor = "Lclient!be;")
	public static Component staticActiveComponent1;
	@OriginalMember(owner = "client!sg", name = "i", descriptor = "Lclient!be;")
	public static Component staticActiveComponent2;
	@OriginalMember(owner = "client!og", name = "g", descriptor = "[Lclient!na;")
	public static JagString[] stringLocals;
	@OriginalMember(owner = "client!rh", name = "a", descriptor = "[I")
	public static int[] intLocals;
	@OriginalMember(owner = "client!km", name = "ad", descriptor = "I")
	public static int fp = 0;
	@OriginalMember(owner = "client!od", name = "g", descriptor = "S")
	public static short nearZoom = 256;
	@OriginalMember(owner = "client!an", name = "db", descriptor = "S")
	public static short farZoom = 205;
	@OriginalMember(owner = "client!mc", name = "tb", descriptor = "S")
	public static short minZoom = 1;
	@OriginalMember(owner = "client!ac", name = "k", descriptor = "S")
	public static short maxZoom = 32767;
	@OriginalMember(owner = "client!nc", name = "n", descriptor = "I")
	public static int viewportWidth = 0;
	@OriginalMember(owner = "client!tm", name = "i", descriptor = "I")
	public static int viewportHeight = 0;
	@OriginalMember(owner = "client!bn", name = "eb", descriptor = "I")
	public static int viewportTop = 0;
	@OriginalMember(owner = "client!ah", name = "n", descriptor = "I")
	public static int viewportLeft = 0;
	@OriginalMember(owner = "client!sc", name = "p", descriptor = "I")
	public static int viewportZoom = 0;
	@OriginalMember(owner = "client!kd", name = "yb", descriptor = "S")
	public static short maxFov = 32767;
	@OriginalMember(owner = "client!ee", name = "f", descriptor = "S")
	public static short minFov = 1;
	@OriginalMember(owner = "client!kk", name = "j", descriptor = "I")
	public static int renderCycle = 0;
	@OriginalMember(owner = "client!bi", name = "jb", descriptor = "Z")
	public static boolean loadingScene = true;
	@OriginalMember(owner = "client!wb", name = "c", descriptor = "I")
	public static int scriptCursorId = -1;
	@OriginalMember(owner = "client!k", name = "m", descriptor = "Z")
	public static boolean neverRemoveRoofs = false;
	@OriginalMember(owner = "client!vk", name = "f", descriptor = "[[[B")
	public static byte[][][] roofVisibility;
	@OriginalMember(owner = "client!vg", name = "b", descriptor = "S")
	public static short minViewDistance = 256;
	@OriginalMember(owner = "client!vg", name = "c", descriptor = "Z")
	public static boolean glSceneNeedsRender = false;
	@OriginalMember(owner = "client!lj", name = "z", descriptor = "[I")
	public static int[] roofGroupMinX = new int[8];
	@OriginalMember(owner = "client!vl", name = "i", descriptor = "[I")
	public static int[] roofGroupMaxX = new int[8];
	@OriginalMember(owner = "client!tk", name = "K", descriptor = "[I")
	public static int[] roofGroupMinZ = new int[8];
	@OriginalMember(owner = "client!e", name = "xc", descriptor = "[I")
	public static int[] roofGroupMaxZ = new int[8];
	@OriginalMember(owner = "client!ge", name = "k", descriptor = "[I")
	public static int[] roofGroupMaxHeight = new int[8];
	@OriginalMember(owner = "client!nm", name = "W", descriptor = "Lclient!na;")
	public static JagString url;
	@OriginalMember(owner = "client!bf", name = "B", descriptor = "I")
	public static int screenY = -1;
	@OriginalMember(owner = "client!pb", name = "rb", descriptor = "S")
	public static short maxViewDistance = 320;
	@OriginalMember(owner = "client!fc", name = "a", descriptor = "I")
	public static int screenX = -1;
	@OriginalMember(owner = "client!em", name = "w", descriptor = "I")
	public static int interfaceMouseY;
	@OriginalMember(owner = "client!me", name = "nb", descriptor = "I")
	public static int interfaceMouseX;

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(IIIIIZ)V")
	public static void calculateViewportBounds(@OriginalArg(0) int width, @OriginalArg(2) int top, @OriginalArg(3) int height, @OriginalArg(4) int left, @OriginalArg(5) boolean fillBackground) {
		if (width < 1) {
			width = 1;
		}
		if (height < 1) {
			height = 1;
		}
		if (GlRenderer.enabled) {
			@Pc(25) int heightDelta = height - 334;
			if (heightDelta < 0) {
				heightDelta = 0;
			} else if (heightDelta > 100) {
				heightDelta = 100;
			}
			@Pc(51) int zoom = heightDelta * (farZoom - nearZoom) / 100 + nearZoom;
			if (minZoom > zoom) {
				zoom = minZoom;
			} else if (maxZoom < zoom) {
				zoom = maxZoom;
			}
			@Pc(73) int fov = zoom * height * 512 / (width * 334);
			@Pc(115) int adjustedDim;
			@Pc(122) int padding;
			@Pc(86) short fovLimit;
			if (fov < minFov) {
				fovLimit = minFov;
				zoom = width * 334 * fovLimit / (height * 512);
				if (maxZoom < zoom) {
					zoom = maxZoom;
					adjustedDim = height * 512 * zoom / (fovLimit * 334);
					padding = (width - adjustedDim) / 2;
					if (fillBackground) {
						GlRaster.resetClipRegion();
						GlRaster.fillRect(left, top, padding, height, 0);
						GlRaster.fillRect(width + left - padding, top, padding, height, 0);
					}
					left += padding;
					width -= padding * 2;
				}
			} else if (maxFov < fov) {
				fovLimit = maxFov;
				zoom = fovLimit * width * 334 / (height * 512);
				if (minZoom > zoom) {
					zoom = minZoom;
					adjustedDim = fovLimit * width * 334 / (zoom * 512);
					padding = (height - adjustedDim) / 2;
					if (fillBackground) {
						GlRaster.resetClipRegion();
						GlRaster.fillRect(left, top, width, padding, 0);
						GlRaster.fillRect(left, top + height - padding, width, padding, 0);
					}
					height -= padding * 2;
					top += padding;
				}
			}
			viewportZoom = zoom * height / 334;
		}
		viewportWidth = (short) width;
		viewportHeight = (short) height;
		viewportTop = top;
		viewportLeft = left;
	}

	@OriginalMember(owner = "client!ui", name = "a", descriptor = "(IIZIII)V")
	public static void renderGameScene(@OriginalArg(1) int height, @OriginalArg(2) boolean minimapOnly, @OriginalArg(3) int left, @OriginalArg(4) int width, @OriginalArg(5) int top) {
		renderCycle++;
		clearTileEntityCounts();
		if (!minimapOnly) {
			addPlayersToScene(true);
			addNpcsToScene(true);
			addPlayersToScene(false);
		}
		addNpcsToScene(false);
		if (!minimapOnly) {
			updateSceneProjectiles();
		}
		updateSceneSpotAnims();
		if (GlRenderer.enabled) {
			calculateViewportBounds(width, top, height, left, true);
			left = viewportLeft;
			top = viewportTop;
			width = viewportWidth;
			height = viewportHeight;
		}
		@Pc(59) int savedRenderX;
		@Pc(57) int savedRenderZ;
		if (Camera.cameraType == 1) {
			savedRenderZ = Camera.yawDrift + (int) Camera.yawTarget & 0x7FF;
			savedRenderX = (int) Camera.pitchTarget;
			if (savedRenderX < Camera.maxPitchDistance / 256) {
				savedRenderX = Camera.maxPitchDistance / 256;
			}
			if (Camera.customCameraActive[4] && Camera.cameraAmplitude[4] + 128 > savedRenderX) {
				savedRenderX = Camera.cameraAmplitude[4] + 128;
			}
			Camera.calculateRenderPosition(Camera.cameraX, height, SceneGraph.getTileHeight(Player.plane, PlayerList.self.xFine, PlayerList.self.yFine) - 50, Camera.ZOOM - -(savedRenderX * 3), savedRenderZ, Camera.cameraY, savedRenderX);
		}
		savedRenderZ = Camera.renderZ;
		savedRenderX = Camera.renderX;
		@Pc(121) int savedRenderY = Camera.renderY;
		@Pc(123) int savedCameraPitch = Camera.cameraPitch;
		@Pc(125) int savedCameraYaw = Camera.cameraYaw;
		@Pc(127) int i;
		@Pc(171) int shakeOffset;
		for (i = 0; i < 5; i++) {
			if (Camera.customCameraActive[i]) {
				shakeOffset = (int) ((double) -Camera.cameraJitter[i] + (double) (Camera.cameraJitter[i] * 2 + 1) * Math.random() + Math.sin((double) Protocol.cameraShakePhase[i] * ((double) Camera.cameraFrequency[i] / 100.0D)) * (double) Camera.cameraAmplitude[i]);
				if (i == 3) {
					Camera.cameraYaw = shakeOffset + Camera.cameraYaw & 0x7FF;
				}
				if (i == 4) {
					Camera.cameraPitch += shakeOffset;
					if (Camera.cameraPitch < 128) {
						Camera.cameraPitch = 128;
					}
					if (Camera.cameraPitch > 383) {
						Camera.cameraPitch = 383;
					}
				}
				if (i == 2) {
					Camera.renderY += shakeOffset;
				}
				if (i == 1) {
					Camera.renderZ += shakeOffset;
				}
				if (i == 0) {
					Camera.renderX += shakeOffset;
				}
			}
		}
		updateRoofVisibilityGroups();
		if (GlRenderer.enabled) {
			GlRaster.setClip(left, top, left + width, top - -height);
			@Pc(248) float pitchDegrees = (float) Camera.cameraPitch * 0.17578125F;
			@Pc(253) float yawDegrees = (float) Camera.cameraYaw * 0.17578125F;
			if (Camera.cameraType == 3) {
				pitchDegrees = Camera.pitchRadians * 360.0F / 6.2831855F;
				yawDegrees = Camera.yawRadians * 360.0F / 6.2831855F;
			}
			GlRenderer.setupPerspectiveView(left, top, width, height, width / 2 + left, top - -(height / 2), pitchDegrees, yawDegrees, viewportZoom, viewportZoom);
		} else {
			SoftwareRaster.setClip(left, top, width + left, height + top);
			Rasteriser.prepare();
		}
		if (Cs1ScriptRunner.isMenuOpen || interfaceMouseX < left || interfaceMouseX >= width + left || top > interfaceMouseY || height + top <= interfaceMouseY) {
			RawModel.allowInput = false;
			MiniMenu.pickResultCount = 0;
		} else {
			RawModel.allowInput = true;
			MiniMenu.pickResultCount = 0;
			shakeOffset = Rasteriser.screenUpperX;
			@Pc(344) int screenLowerY = Rasteriser.screenLowerY;
			i = Rasteriser.screenLowerX;
			GlModel.pickScreenX = i + (shakeOffset - i) * (-left + interfaceMouseX) / width;
			@Pc(361) int screenUpperY = Rasteriser.screenUpperY;
			RawModel.pickScreenY = (screenUpperY - screenLowerY) * (interfaceMouseY - top) / height + screenLowerY;
		}
		client.audioLoop();
		@Pc(387) byte roofCycle = getRoofRemovalMode() == 2 ? (byte) renderCycle : 1;
		if (GlRenderer.enabled) {
			GlRenderer.restoreLighting();
			GlRenderer.setDepthTestEnabled(true);
			GlRenderer.setFogEnabled(true);
			if (client.gameState == 10) {
				shakeOffset = FogManager.updateAtmosphere(Protocol.sceneDelta, Camera.renderY >> 10, Preferences.brightness, Camera.renderX >> 10);
			} else {
				shakeOffset = FogManager.updateAtmosphere(Protocol.sceneDelta, PlayerList.self.movementQueueY[0] >> 3, Preferences.brightness, PlayerList.self.movementQueueX[0] >> 3);
			}
			LightingManager.updateAllLightAnimations(client.loop, !Preferences.flickeringEffectsOn);
			GlRenderer.clearColorAndDepthBuffers(shakeOffset);
			MaterialManager.setCameraTransform(Camera.cameraPitch, Camera.renderY, Camera.renderZ, Camera.renderX, Camera.cameraYaw);
			GlRenderer.animationClock = client.loop;
			SceneGraph.setPlainTile(Camera.renderX, Camera.renderZ, Camera.renderY, Camera.cameraPitch, Camera.cameraYaw, roofVisibility, roofGroupMaxHeight, roofGroupMinX, roofGroupMaxX, roofGroupMaxZ, roofGroupMinZ, Player.plane + 1, roofCycle, PlayerList.self.xFine >> 7, PlayerList.self.yFine >> 7);
			glSceneNeedsRender = true;
			LightingManager.resetActiveLights();
			MaterialManager.setCameraTransform(0, 0, 0, 0, 0);
			client.audioLoop();
			clearSceneScenery();
			drawOverheads(top, width, left, viewportZoom, height, viewportZoom);
			MiniMap.renderHeadHints(width, left, height, viewportZoom, viewportZoom, top);
		} else {
			SoftwareRaster.fillRect(left, top, width, height, 0);
			SceneGraph.setPlainTile(Camera.renderX, Camera.renderZ, Camera.renderY, Camera.cameraPitch, Camera.cameraYaw, roofVisibility, roofGroupMaxHeight, roofGroupMinX, roofGroupMaxX, roofGroupMaxZ, roofGroupMinZ, Player.plane + 1, roofCycle, PlayerList.self.xFine >> 7, PlayerList.self.yFine >> 7);
			client.audioLoop();
			clearSceneScenery();
			drawOverheads(top, width, left, 256, height, 256);
			MiniMap.renderHeadHints(width, left, height, 256, 256, top);
		}
		((Js5GlTextureProvider) Rasteriser.textureProvider).resetAnimatedTextures(Protocol.sceneDelta);
		Player.renderCross(width, top, height, left);
		Camera.cameraPitch = savedCameraPitch;
		Camera.renderY = savedRenderY;
		Camera.renderZ = savedRenderZ;
		Camera.renderX = savedRenderX;
		Camera.cameraYaw = savedCameraYaw;
		if (loadingScene && client.js5NetQueue.getUrgentRequestCount() == 0) {
			loadingScene = false;
		}
		if (loadingScene) {
			if (GlRenderer.enabled) {
				GlRaster.fillRect(left, top, width, height, 0);
			} else {
				SoftwareRaster.fillRect(left, top, width, height, 0);
			}
			Fonts.drawTextOnScreen(false, LocalizedText.LOADING);
		}
		if (!minimapOnly && !loadingScene && !Cs1ScriptRunner.isMenuOpen && left <= interfaceMouseX && width + left > interfaceMouseX && top <= interfaceMouseY && height + top > interfaceMouseY) {
			MiniMenu.addEntries(top, width, height, left, interfaceMouseY, interfaceMouseX);
		}
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(IIIIIII)V")
	public static void drawOverheads(@OriginalArg(0) int top, @OriginalArg(1) int width, @OriginalArg(2) int left, @OriginalArg(3) int zoomH, @OriginalArg(4) int height, @OriginalArg(5) int zoomV) {
		OverheadChat.size = 0;
		@Pc(5) int entityIndex;
		@Pc(642) int spriteHeight;
		@Pc(74) int yOffset;
		@Pc(265) int overheadHeight;
		@Pc(310) int baseHeight;
		@Pc(359) int posY;
		@Pc(639) int fillWidth;
		for (entityIndex = -1; entityIndex < PlayerList.size + NpcList.size; entityIndex++) {
			@Pc(17) PathingEntity entity;
			if (entityIndex == -1) {
				entity = PlayerList.self;
			} else if (PlayerList.size > entityIndex) {
				entity = PlayerList.players[PlayerList.ids[entityIndex]];
			} else {
				entity = NpcList.npcs[NpcList.ids[entityIndex - PlayerList.size]];
			}
			if (entity != null && entity.isVisible()) {
				setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, entity.getModelHeight() + 15, width >> 1);
				if (entityIndex >= PlayerList.size) {
					PluginRepository.NPCOverheadDraw((Npc) entity,left + screenX, top + screenY);
				} else {
					PluginRepository.PlayerOverheadDraw((Player) entity,left + screenX, top + screenY);
				}

				@Pc(58) NpcType npcType;
				if (entity instanceof Npc) {
					npcType = ((Npc) entity).type;
					if (npcType.multiNpcs != null) {
						npcType = npcType.getMultiNpc();
					}
					if (npcType == null) {
						continue;
					}
				}
				@Pc(161) int markerIndex;
				if (entityIndex >= PlayerList.size) {
					npcType = ((Npc) entity).type;
					if (npcType.multiNpcs != null) {
						npcType = npcType.getMultiNpc();
					}
					if (npcType.headicon >= 0 && Sprites.headiconPrayers.length > npcType.headicon) {
						if (npcType.iconHeight == -1) {
							overheadHeight = entity.getModelHeight() + 15;
						} else {
							overheadHeight = npcType.iconHeight + 15;
						}
						setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, overheadHeight, width >> 1);
						if (screenX > -1) {
							Sprites.headiconPrayers[npcType.headicon].render(left + screenX - 12, top + -30 - -screenY);
						}
					}
					@Pc(308) MapMarker[] npcHintMarkers = MiniMap.hintMapMarkers;
					for (baseHeight = 0; baseHeight < npcHintMarkers.length; baseHeight++) {
						@Pc(322) MapMarker npcMarker = npcHintMarkers[baseHeight];
						if (npcMarker != null && npcMarker.type == 1 && npcMarker.actorTargetId == NpcList.ids[entityIndex - PlayerList.size] && client.loop % 20 < 10) {
							if (npcType.iconHeight == -1) {
								posY = entity.getModelHeight() + 15;
							} else {
								posY = npcType.iconHeight + 15;
							}
							setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, posY, width >> 1);
							if (screenX > -1) {
								Sprites.headhints[npcMarker.arrowSpriteId].render(left + screenX - 12, screenY + -28 + top);
							}
						}
					}
				} else {
					yOffset = 30;
					@Pc(77) Player player = (Player) entity;
					if (player.skullHeadIcon != -1 || player.prayerHeadIcon != -1) {
						setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, entity.getModelHeight() + 15, width >> 1);
						if (screenX > -1) {
							if (player.skullHeadIcon != -1) {
								Sprites.headiconPks[player.skullHeadIcon].render(screenX + left - 12, top + -30 + screenY);
								yOffset += 25;
							}
							if (player.prayerHeadIcon != -1) {
								Sprites.headiconPrayers[player.prayerHeadIcon].render(left + screenX - 12, top - (-screenY + yOffset));
								yOffset += 25;
							}
						}
					}
					if (entityIndex >= 0) {
						@Pc(159) MapMarker[] playerHintMarkers = MiniMap.hintMapMarkers;
						for (markerIndex = 0; markerIndex < playerHintMarkers.length; markerIndex++) {
							@Pc(173) MapMarker playerMarker = playerHintMarkers[markerIndex];
							if (playerMarker != null && playerMarker.type == 10 && PlayerList.ids[entityIndex] == playerMarker.actorTargetId) {
								setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, entity.getModelHeight() + 15, width >> 1);
								if (screenX > -1) {
									Sprites.headhints[playerMarker.arrowSpriteId].render(left + screenX - 12, top + (screenY - yOffset));
								}
							}
						}
					}
				}
				if (entity.chatMessage != null && (entityIndex >= PlayerList.size || Chat.publicFilter == 0 || Chat.publicFilter == 3 || Chat.publicFilter == 1 && FriendsList.contains(((Player) entity).username))) {
					setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, entity.getModelHeight(), width >> 1);
					if (screenX > -1 && OverheadChat.size < OverheadChat.CAPACITY) {
						OverheadChat.textHalfWidths[OverheadChat.size] = Fonts.b12Full.getStringWidth(entity.chatMessage) / 2;
						OverheadChat.textHeights[OverheadChat.size] = Fonts.b12Full.lineHeight;
						OverheadChat.screenX[OverheadChat.size] = screenX;
						OverheadChat.screenY[OverheadChat.size] = screenY;
						OverheadChat.colors[OverheadChat.size] = entity.chatColor;
						OverheadChat.effects[OverheadChat.size] = entity.chatEffect;
						OverheadChat.loops[OverheadChat.size] = entity.chatLoops;
						OverheadChat.messages[OverheadChat.size] = entity.chatMessage;
						OverheadChat.size++;
					}
				}
				if (entity.hitpointsBarVisibleUntil > client.loop) {
					@Pc(508) Sprite emptyBarSprite = Sprites.hitbars[0];
					@Pc(512) Sprite filledBarSprite = Sprites.hitbars[1];
					if (entity instanceof Npc) {
						@Pc(518) Npc npc = (Npc) entity;
						@Pc(528) Sprite[] customBarSprites = (Sprite[]) HitBarList.hitBars.get(npc.type.hitBarId);
						if (customBarSprites == null) {
							customBarSprites = SpriteLoader.loadAlphaSprites(npc.type.hitBarId, client.js5Archive8);
							if (customBarSprites != null) {
								HitBarList.hitBars.put(customBarSprites, npc.type.hitBarId);
							}
						}
						if (customBarSprites != null && customBarSprites.length == 2) {
							filledBarSprite = customBarSprites[1];
							emptyBarSprite = customBarSprites[0];
						}
						@Pc(571) NpcType hitBarNpcType = npc.type;
						if (hitBarNpcType.iconHeight == -1) {
							baseHeight = entity.getModelHeight();
						} else {
							baseHeight = hitBarNpcType.iconHeight;
						}
					} else {
						baseHeight = entity.getModelHeight();
					}
					setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, emptyBarSprite.height + baseHeight + 10, width >> 1);
					if (screenX > -1) {
						markerIndex = screenX + left - (emptyBarSprite.width >> 1);
						posY = screenY + top - 3;
						emptyBarSprite.render(markerIndex, posY);
						fillWidth = emptyBarSprite.width * entity.hitpointsBar / 255;
						spriteHeight = emptyBarSprite.height;
						if (GlRenderer.enabled) {
							GlRaster.setClipRegion(markerIndex, posY, markerIndex + fillWidth, posY + spriteHeight);
						} else {
							SoftwareRaster.shrinkClip(markerIndex, posY, markerIndex + fillWidth, spriteHeight + posY);
						}
						filledBarSprite.render(markerIndex, posY);
						if (GlRenderer.enabled) {
							GlRaster.setClip(left, top, width + left, top - -height);
						} else {
							SoftwareRaster.setClip(left, top, width + left, height + top);
						}
					}
				}
				for (yOffset = 0; yOffset < 4; yOffset++) {
					if (entity.hitVisibleUntil[yOffset] > client.loop) {
						if (entity instanceof Npc) {
							@Pc(725) Npc hitNpc = (Npc) entity;
							@Pc(728) NpcType hitNpcType = hitNpc.type;
							if (hitNpcType.iconHeight == -1) {
								overheadHeight = entity.getModelHeight() / 2;
							} else {
								overheadHeight = hitNpcType.iconHeight / 2;
							}
						} else {
							overheadHeight = entity.getModelHeight() / 2;
						}
						setOverheadScreenCoordinateOffsets(height >> 1, zoomH, entity, zoomV, overheadHeight, width >> 1);
						if (screenX > -1) {
							if (yOffset == 1) {
								screenY -= 20;
							}
							if (yOffset == 2) {
								screenY -= 10;
								screenX -= 15;
							}
							if (yOffset == 3) {
								screenY -= 10;
								screenX += 15;
							}
							Sprites.hitmarks[entity.hitTypes[yOffset]].render(left + screenX - 12, top + screenY - 12);
							Fonts.p11Full.renderCenter(JagString.parseInt(entity.hitDamages[yOffset]), screenX + left - 1, screenY + 3 + top, 16777215, 0);
						}
					}
				}
			}
		}
		for (entityIndex = 0; entityIndex < OverheadChat.size; entityIndex++) {
			yOffset = OverheadChat.screenY[entityIndex];
			@Pc(859) int chatScreenX = OverheadChat.screenX[entityIndex];
			baseHeight = OverheadChat.textHeights[entityIndex];
			overheadHeight = OverheadChat.textHalfWidths[entityIndex];
			@Pc(869) boolean hasOverlap = true;
			while (hasOverlap) {
				hasOverlap = false;
				for (posY = 0; posY < entityIndex; posY++) {
					if (OverheadChat.screenY[posY] - OverheadChat.textHeights[posY] < yOffset + 2 && yOffset - baseHeight < OverheadChat.screenY[posY] - -2 && chatScreenX - overheadHeight < OverheadChat.screenX[posY] + OverheadChat.textHalfWidths[posY] && OverheadChat.screenX[posY] - OverheadChat.textHalfWidths[posY] < overheadHeight + chatScreenX && OverheadChat.screenY[posY] - OverheadChat.textHeights[posY] < yOffset) {
						yOffset = OverheadChat.screenY[posY] - OverheadChat.textHeights[posY];
						hasOverlap = true;
					}
				}
			}
			screenX = OverheadChat.screenX[entityIndex];
			screenY = OverheadChat.screenY[entityIndex] = yOffset;
			@Pc(962) JagString chatMessage = OverheadChat.messages[entityIndex];
			if (VarpDomain.chatEffectsDisabled == 0) {
				fillWidth = 16776960;
				if (OverheadChat.colors[entityIndex] < 6) {
					fillWidth = OverheadChat.COLORS[OverheadChat.colors[entityIndex]];
				}
				if (OverheadChat.colors[entityIndex] == 6) {
					fillWidth = renderCycle % 20 >= 10 ? 16776960 : 16711680;
				}
				if (OverheadChat.colors[entityIndex] == 7) {
					fillWidth = renderCycle % 20 < 10 ? 255 : 65535;
				}
				if (OverheadChat.colors[entityIndex] == 8) {
					fillWidth = renderCycle % 20 >= 10 ? 8454016 : 45056;
				}
				if (OverheadChat.colors[entityIndex] == 9) {
					spriteHeight = 150 - OverheadChat.loops[entityIndex];
					if (spriteHeight < 50) {
						fillWidth = spriteHeight * 1280 + 16711680;
					} else if (spriteHeight < 100) {
						fillWidth = 16776960 + 16384000 - spriteHeight * 327680;
					} else if (spriteHeight < 150) {
						fillWidth = spriteHeight * 5 + 65280 - 500;
					}
				}
				if (OverheadChat.colors[entityIndex] == 10) {
					spriteHeight = 150 - OverheadChat.loops[entityIndex];
					if (spriteHeight < 50) {
						fillWidth = spriteHeight * 5 + 16711680;
					} else if (spriteHeight < 100) {
						fillWidth = 16711935 - (spriteHeight - 50) * 327680;
					} else if (spriteHeight < 150) {
						fillWidth = spriteHeight * 327680 + 255 + 500 - spriteHeight * 5 - 32768000;
					}
				}
				if (OverheadChat.colors[entityIndex] == 11) {
					spriteHeight = 150 - OverheadChat.loops[entityIndex];
					if (spriteHeight < 50) {
						fillWidth = 16777215 - spriteHeight * 327685;
					} else if (spriteHeight < 100) {
						fillWidth = spriteHeight * 327685 + 65280 - 16384250;
					} else if (spriteHeight < 150) {
						fillWidth = 16777215 + 32768000 - spriteHeight * 327680;
					}
				}
				if (OverheadChat.effects[entityIndex] == 0) {
					Fonts.b12Full.renderCenter(chatMessage, screenX + left, top + screenY, fillWidth, 0);
				}
				if (OverheadChat.effects[entityIndex] == 1) {
					Fonts.b12Full.renderWave(chatMessage, left + screenX, screenY + top, fillWidth, renderCycle);
				}
				if (OverheadChat.effects[entityIndex] == 2) {
					Fonts.b12Full.renderWave2(chatMessage, left + screenX, top - -screenY, fillWidth, renderCycle);
				}
				if (OverheadChat.effects[entityIndex] == 3) {
					Fonts.b12Full.renderShake(chatMessage, left + screenX, screenY + top, fillWidth, renderCycle, 150 - OverheadChat.loops[entityIndex]);
				}
				if (OverheadChat.effects[entityIndex] == 4) {
					spriteHeight = (150 - OverheadChat.loops[entityIndex]) * (Fonts.b12Full.getStringWidth(chatMessage) + 100) / 150;
					if (GlRenderer.enabled) {
						GlRaster.setClipRegion(screenX + left - 50, top, screenX + left + 50, height + top);
					} else {
						SoftwareRaster.shrinkClip(left + screenX - 50, top, screenX + left + 50, height + top);
					}
					Fonts.b12Full.renderLeft(chatMessage, left + screenX + 50 - spriteHeight, top + screenY, fillWidth, 0);
					if (GlRenderer.enabled) {
						GlRaster.setClip(left, top, width + left, height + top);
					} else {
						SoftwareRaster.setClip(left, top, left + width, top + height);
					}
				}
				if (OverheadChat.effects[entityIndex] == 5) {
					@Pc(1372) int slideOffset = 0;
					spriteHeight = 150 - OverheadChat.loops[entityIndex];
					if (GlRenderer.enabled) {
						GlRaster.setClipRegion(left, screenY + top - Fonts.b12Full.lineHeight - 1, width + left, top + screenY + 5);
					} else {
						SoftwareRaster.shrinkClip(left, screenY + top - Fonts.b12Full.lineHeight - 1, left + width, screenY + top + 5);
					}
					if (spriteHeight < 25) {
						slideOffset = spriteHeight - 25;
					} else if (spriteHeight > 125) {
						slideOffset = spriteHeight - 125;
					}
					Fonts.b12Full.renderCenter(chatMessage, screenX + left, slideOffset + top + screenY, fillWidth, 0);
					if (GlRenderer.enabled) {
						GlRaster.setClip(left, top, left + width, top + height);
					} else {
						SoftwareRaster.setClip(left, top, left + width, top + height);
					}
				}
			} else {
				Fonts.b12Full.renderCenter(chatMessage, left + screenX, top + screenY, 16776960, 0);
			}
		}
	}

	@OriginalMember(owner = "client!rb", name = "a", descriptor = "(I)V")
	public static void clearTileEntityCounts() {
		for (@Pc(7) int x = 0; x < 104; x++) {
			for (@Pc(14) int y = 0; y < 104; y++) {
				tileEntityCounts[x][y] = 0;
			}
		}
	}

	@OriginalMember(owner = "client!cn", name = "b", descriptor = "(ZI)V")
	public static void addPlayersToScene(@OriginalArg(0) boolean selfOnly) {
		@Pc(3) int count = PlayerList.size;
		if (LoginManager.mapFlagX == PlayerList.self.xFine >> 7 && PlayerList.self.yFine >> 7 == LoginManager.mapFlagY) {
			LoginManager.mapFlagX = 0;
		}
		if (selfOnly) {
			count = 1;
		}
		@Pc(28) int i;
		@Pc(39) Player player;
		@Pc(82) int entitySize;
		@Pc(182) int maxTileX;
		@Pc(200) int maxTileY;
		@Pc(214) int endTileX;
		@Pc(223) int endTileY;
		@Pc(106) int prevCount;
		for (i = 0; i < count; i++) {
			if (selfOnly) {
				player = PlayerList.self;
			} else {
				player = PlayerList.players[PlayerList.ids[i]];
			}
			if (player != null && player.isVisible()) {
				@Pc(55) int size = player.getSize();
				@Pc(77) int tileX;
				if (size == 1) {
					if ((player.xFine & 0x7F) == 64 && (player.yFine & 0x7F) == 64) {
						tileX = player.xFine >> 7;
						entitySize = player.yFine >> 7;
						if (tileX >= 0 && tileX < 104 && entitySize >= 0 && entitySize < 104) {
							prevCount = tileEntityCounts[tileX][entitySize]++;
						}
					}
				} else if (((size & 0x1) != 0 || (player.xFine & 0x7F) == 0 && (player.yFine & 0x7F) == 0) && ((size & 0x1) != 1 || (player.xFine & 0x7F) == 64 && (player.yFine & 0x7F) == 64)) {
					tileX = player.xFine - size * 64 >> 7;
					entitySize = player.yFine - size * 64 >> 7;
					maxTileX = player.getSize() + tileX;
					if (maxTileX > 104) {
						maxTileX = 104;
					}
					if (tileX < 0) {
						tileX = 0;
					}
					maxTileY = entitySize + player.getSize();
					if (entitySize < 0) {
						entitySize = 0;
					}
					if (maxTileY > 104) {
						maxTileY = 104;
					}
					for (endTileX = tileX; endTileX < maxTileX; endTileX++) {
						for (endTileY = entitySize; endTileY < maxTileY; endTileY++) {
							prevCount = tileEntityCounts[endTileX][endTileY]++;
						}
					}
				}
			}
		}
		nextPlayer:
		for (i = 0; i < count; i++) {
			@Pc(272) long sceneKey;
			if (selfOnly) {
				player = PlayerList.self;
				sceneKey = 8791798054912L;
			} else {
				player = PlayerList.players[PlayerList.ids[i]];
				sceneKey = (long) PlayerList.ids[i] << 32;
			}
			if (player != null && player.isVisible()) {
				player.lowDetail = (Preferences.manyIdleAnimations && PlayerList.size > 200 || PlayerList.size > 50) && !selfOnly && player.movementSeqId == player.getBasType().idleAnimationId;
				entitySize = player.getSize();
				if (entitySize == 1) {
					if ((player.xFine & 0x7F) == 64 && (player.yFine & 0x7F) == 64) {
						maxTileX = player.xFine >> 7;
						maxTileY = player.yFine >> 7;
						if (maxTileX < 0 || maxTileX >= 104 || maxTileY < 0 || maxTileY >= 104) {
							continue;
						}
						if (tileEntityCounts[maxTileX][maxTileY] > 1) {
							prevCount = tileEntityCounts[maxTileX][maxTileY]--;
							continue;
						}
					}
				} else if ((entitySize & 0x1) == 0 && (player.xFine & 0x7F) == 0 && (player.yFine & 0x7F) == 0 || (entitySize & 0x1) == 1 && (player.xFine & 0x7F) == 64 && (player.yFine & 0x7F) == 0) {
					maxTileX = player.xFine - entitySize * 64 >> 7;
					endTileX = entitySize + maxTileX;
					maxTileY = player.yFine - entitySize * 64 >> 7;
					if (endTileX > 104) {
						endTileX = 104;
					}
					if (maxTileX < 0) {
						maxTileX = 0;
					}
					endTileY = entitySize + maxTileY;
					if (maxTileY < 0) {
						maxTileY = 0;
					}
					@Pc(468) boolean allCrowded = true;
					if (endTileY > 104) {
						endTileY = 104;
					}
					@Pc(476) int tx;
					@Pc(485) int ty;
					for (tx = maxTileX; tx < endTileX; tx++) {
						for (ty = maxTileY; ty < endTileY; ty++) {
							if (tileEntityCounts[tx][ty] <= 1) {
								allCrowded = false;
								break;
							}
						}
					}
					if (allCrowded) {
						tx = maxTileX;
						while (true) {
							if (tx >= endTileX) {
								continue nextPlayer;
							}
							for (ty = maxTileY; ty < endTileY; ty++) {
								prevCount = tileEntityCounts[tx][ty]--;
							}
							tx++;
						}
					}
				}
				if (player.attachment == null || client.loop < player.attachmentSetAt || player.attachmentResetAt <= client.loop) {
					player.tileHeight = SceneGraph.getTileHeight(Player.plane, player.xFine, player.yFine);
					SceneGraph.add(Player.plane, player.xFine, player.yFine, player.tileHeight, (entitySize - 1) * 64 + 60, player, player.currentAngle, sceneKey, player.seqStretches);
				} else {
					player.lowDetail = false;
					player.tileHeight = SceneGraph.getTileHeight(Player.plane, player.xFine, player.yFine);
					addAttachedEntityToScene(Player.plane, player.xFine, player.yFine, player.tileHeight, player, player.currentAngle, sceneKey, player.atachmentX0, player.attachmentY0, player.attachmentX1, player.attachmentY1);
				}
			}
		}
	}

	@OriginalMember(owner = "client!nk", name = "c", descriptor = "(IZ)V")
	public static void addNpcsToScene(@OriginalArg(1) boolean topRenderPriority) {
		@Pc(7) int i;
		@Pc(16) Npc npc;
		@Pc(107) int entitySize;
		@Pc(113) int tileX;
		@Pc(133) int tileY;
		@Pc(149) int maxTileX;
		@Pc(158) int maxTileY;
		@Pc(171) int prevCount;
		for (i = 0; i < NpcList.size; i++) {
			npc = NpcList.npcs[NpcList.ids[i]];
			if (npc != null && npc.isVisible() && npc.type.toprenderpriority == topRenderPriority && npc.type.isMultiNpcValid()) {
				@Pc(42) int size = npc.getSize();
				@Pc(97) int minTileX;
				if (size == 1) {
					if ((npc.xFine & 0x7F) == 64 && (npc.yFine & 0x7F) == 64) {
						minTileX = npc.xFine >> 7;
						entitySize = npc.yFine >> 7;
						if (minTileX >= 0 && minTileX < 104 && entitySize >= 0 && entitySize < 104) {
							prevCount = tileEntityCounts[minTileX][entitySize]++;
						}
					}
				} else if (((size & 0x1) != 0 || (npc.xFine & 0x7F) == 0 && (npc.yFine & 0x7F) == 0) && ((size & 0x1) != 1 || (npc.xFine & 0x7F) == 64 && (npc.yFine & 0x7F) == 64)) {
					minTileX = npc.xFine - size * 64 >> 7;
					entitySize = npc.yFine - size * 64 >> 7;
					tileX = npc.getSize() + minTileX;
					if (minTileX < 0) {
						minTileX = 0;
					}
					if (tileX > 104) {
						tileX = 104;
					}
					tileY = entitySize + npc.getSize();
					if (entitySize < 0) {
						entitySize = 0;
					}
					if (tileY > 104) {
						tileY = 104;
					}
					for (maxTileX = minTileX; maxTileX < tileX; maxTileX++) {
						for (maxTileY = entitySize; maxTileY < tileY; maxTileY++) {
							prevCount = tileEntityCounts[maxTileX][maxTileY]++;
						}
					}
				}
			}
		}
		nextNpc:
		for (i = 0; i < NpcList.size; i++) {
			npc = NpcList.npcs[NpcList.ids[i]];
			@Pc(262) long sceneKey = (long) NpcList.ids[i] << 32 | 0x20000000L;
			if (npc != null && npc.isVisible() && npc.type.toprenderpriority == topRenderPriority && npc.type.isMultiNpcValid()) {
				entitySize = npc.getSize();
				if (entitySize == 1) {
					if ((npc.xFine & 0x7F) == 64 && (npc.yFine & 0x7F) == 64) {
						tileX = npc.xFine >> 7;
						tileY = npc.yFine >> 7;
						if (tileX < 0 || tileX >= 104 || tileY < 0 || tileY >= 104) {
							continue;
						}
						if (tileEntityCounts[tileX][tileY] > 1) {
							prevCount = tileEntityCounts[tileX][tileY]--;
							continue;
						}
					}
				} else if ((entitySize & 0x1) == 0 && (npc.xFine & 0x7F) == 0 && (npc.yFine & 0x7F) == 0 || (entitySize & 0x1) == 1 && (npc.xFine & 0x7F) == 64 && (npc.yFine & 0x7F) == 64) {
					tileX = npc.xFine - entitySize * 64 >> 7;
					tileY = npc.yFine - entitySize * 64 >> 7;
					maxTileY = tileY + entitySize;
					if (tileY < 0) {
						tileY = 0;
					}
					@Pc(368) boolean allCrowded = true;
					maxTileX = tileX + entitySize;
					if (maxTileY > 104) {
						maxTileY = 104;
					}
					if (tileX < 0) {
						tileX = 0;
					}
					if (maxTileX > 104) {
						maxTileX = 104;
					}
					@Pc(396) int tx;
					@Pc(401) int ty;
					for (tx = tileX; tx < maxTileX; tx++) {
						for (ty = tileY; ty < maxTileY; ty++) {
							if (tileEntityCounts[tx][ty] <= 1) {
								allCrowded = false;
								break;
							}
						}
					}
					if (allCrowded) {
						tx = tileX;
						while (true) {
							if (tx >= maxTileX) {
								continue nextNpc;
							}
							for (ty = tileY; ty < maxTileY; ty++) {
								prevCount = tileEntityCounts[tx][ty]--;
							}
							tx++;
						}
					}
				}
				if (!npc.type.interactive) {
					sceneKey |= Long.MIN_VALUE;
				}
				npc.tileHeight = SceneGraph.getTileHeight(Player.plane, npc.xFine, npc.yFine);
				SceneGraph.add(Player.plane, npc.xFine, npc.yFine, npc.tileHeight, entitySize * 64 + 60 - 64, npc, npc.currentAngle, sceneKey, npc.seqStretches);
			}
		}
	}

	@OriginalMember(owner = "client!pk", name = "i", descriptor = "(I)V")
	public static void updateSceneProjectiles() {
		for (@Pc(16) ProjAnimNode node = (ProjAnimNode) SceneGraph.projectiles.head(); node != null; node = (ProjAnimNode) SceneGraph.projectiles.next()) {
			@Pc(21) ProjAnim projAnim = node.value;
			if (Player.plane != projAnim.currentPlane || projAnim.lastCycle < client.loop) {
				node.unlink();
			} else if (client.loop >= projAnim.firstCycle) {
				if (projAnim.targetIndex > 0) {
					@Pc(54) Npc targetNpc = NpcList.npcs[projAnim.targetIndex - 1];
					if (targetNpc != null && targetNpc.xFine >= 0 && targetNpc.xFine < 13312 && targetNpc.yFine >= 0 && targetNpc.yFine < 13312) {
						projAnim.setTarget(targetNpc.yFine, client.loop, SceneGraph.getTileHeight(projAnim.currentPlane, targetNpc.xFine, targetNpc.yFine) - projAnim.baseZ, targetNpc.xFine);
					}
				}
				if (projAnim.targetIndex < 0) {
					@Pc(102) int targetIndex = -projAnim.targetIndex - 1;
					@Pc(107) Player targetPlayer;
					if (PlayerList.selfId == targetIndex) {
						targetPlayer = PlayerList.self;
					} else {
						targetPlayer = PlayerList.players[targetIndex];
					}
					if (targetPlayer != null && targetPlayer.xFine >= 0 && targetPlayer.xFine < 13312 && targetPlayer.yFine >= 0 && targetPlayer.yFine < 13312) {
						projAnim.setTarget(targetPlayer.yFine, client.loop, SceneGraph.getTileHeight(projAnim.currentPlane, targetPlayer.xFine, targetPlayer.yFine) - projAnim.baseZ, targetPlayer.xFine);
					}
				}
				projAnim.update(Protocol.sceneDelta);
				SceneGraph.add(Player.plane, (int) projAnim.x, (int) projAnim.y, (int) projAnim.z, 60, projAnim, projAnim.yaw, -1L, false);
			}
		}
	}

	@OriginalMember(owner = "client!u", name = "a", descriptor = "(Z)V")
	public static void updateSceneSpotAnims() {
		for (@Pc(9) SpotAnimNode node = (SpotAnimNode) SceneGraph.spotanims.head(); node != null; node = (SpotAnimNode) SceneGraph.spotanims.next()) {
			@Pc(15) SpotAnim spotAnim = node.spotAnim;
			if (spotAnim.plane != Player.plane || spotAnim.finished) {
				node.unlink();
			} else if (spotAnim.endLoop <= client.loop) {
				spotAnim.advanceAnimation(Protocol.sceneDelta);
				if (spotAnim.finished) {
					node.unlink();
				} else {
					SceneGraph.add(spotAnim.plane, spotAnim.posX, spotAnim.posY, spotAnim.z, 60, spotAnim, 0, -1L, false);
				}
			}
		}
	}

	@OriginalMember(owner = "client!wa", name = "o", descriptor = "(I)V")
	public static void updateRoofRemovalMode() {
		@Pc(8) int mode = getRoofRemovalMode();
		if (mode == 0) {
			roofVisibility = null;
			allocateRoofVisibilityGroupArrays(0);
		} else if (mode == 1) {
			fillRoofVisibility((byte) 0);
			allocateRoofVisibilityGroupArrays(512);
			buildAllRoofVisibilityGroups();
		} else {
			fillRoofVisibility((byte) (renderCycle - 4 & 0xFF));
			allocateRoofVisibilityGroupArrays(API.GetRoofVisibilityGroupLimit());
		}
	}

	@OriginalMember(owner = "client!tc", name = "a", descriptor = "(B)I")
	public static int getRoofRemovalMode() {
		return getBaseRoofMode();
	}

	private static int getBaseRoofMode() {
		if (neverRemoveRoofs) {
			return 0;
		} else if (SceneGraph.allLevelsAreVisible()) {
			return Preferences.removeRoofsSelectively ? 2 : 1;
		} else {
			return 1;
		}
	}

	public static boolean canUseSelectiveRoofHiding() {
		return getBaseRoofMode() == 2;
	}


	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(IIB)Lclient!ce;")
	public static SecondaryLinkedList findMapsAtCoordinate(@OriginalArg(0) int displayX, @OriginalArg(1) int displayY) {
		@Pc(9) SecondaryLinkedList result = new SecondaryLinkedList();
		for (@Pc(14) Map map = (Map) MapList.maps.head(); map != null; map = (Map) MapList.maps.next()) {
			if (map.valid && map.containsDisplayCoordinate(displayY, displayX)) {
				result.addTail(map);
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(BB)V")
	public static void fillRoofVisibility(@OriginalArg(0) byte value) {
		if (roofVisibility == null) {
			roofVisibility = new byte[4][104][104];
		}
		for (@Pc(20) int plane = 0; plane < 4; plane++) {
			for (@Pc(25) int x = 0; x < 104; x++) {
				for (@Pc(32) int y = 0; y < 104; y++) {
					roofVisibility[plane][x][y] = value;
				}
			}
		}
	}

	@OriginalMember(owner = "client!sm", name = "a", descriptor = "(II)V")
	public static void allocateRoofVisibilityGroupArrays(@OriginalArg(0) int size) {
		roofGroupMinX = new int[size];
		roofGroupMaxX = new int[size];
		roofGroupMinZ = new int[size];
		roofGroupMaxZ = new int[size];
		roofGroupMaxHeight = new int[size];
	}

	@OriginalMember(owner = "client!ke", name = "f", descriptor = "(B)V")
	public static void buildAllRoofVisibilityGroups() {
		@Pc(7) int groupId = 0;
		for (@Pc(23) int x = 0; x < 104; x++) {
			for (@Pc(30) int y = 0; y < 104; y++) {
				if (floodFillRoofVisibilityGroup(true, x, y, SceneGraph.tiles, groupId)) {
					groupId++;
				}
				if (groupId >= 512) {
					return;
				}
			}
		}
	}

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(BZII[[[Lclient!bj;I)Z")
	public static boolean floodFillRoofVisibilityGroup(@OriginalArg(1) boolean fillAll, @OriginalArg(2) int startX, @OriginalArg(3) int startY, @OriginalArg(4) Tile[][][] tiles, @OriginalArg(5) int groupId) {
		return floodFillRoofVisibilityGroup(fillAll, startX, startY, tiles, groupId, Player.plane);
	}


	public static boolean hideRoofAt(int sceneX, int sceneY, int group, int plane) {
		return floodFillRoofVisibilityGroup(false, sceneX, sceneY, SceneGraph.tiles, group, plane);
	}

	private static boolean floodFillRoofVisibilityGroup(boolean fillAll, int startX, int startY, Tile[][][] tiles, int groupId, int plane) {
		if (plane < 0 || plane >= 3) {
			return false;
		}
		@Pc(14) byte fillValue = fillAll ? 1 : (byte) (renderCycle & 0xFF);
		if (fillValue == roofVisibility[plane][startX][startY]) {
			return false;
		} else if ((SceneGraph.renderFlags[plane][startX][startY] & API.TILE_FLAG_UNDER_ROOF) == 0) {
			return false;
		} else {
			API.BeginRoofVisibilityGroup(groupId);
			@Pc(47) int queueHead = 0;
			@Pc(49) byte queueStart = 0;
			PathFinder.queueX[0] = startX;
			@Pc(69) int queueTail = queueStart + 1;
			PathFinder.queueY[0] = startY;
			roofVisibility[plane][startX][startY] = fillValue;
			while (queueHead != queueTail) {
				@Pc(94) int wallFlag1 = PathFinder.queueX[queueHead] >> 16 & 0xFF;
				@Pc(102) int wallFlag2 = PathFinder.queueX[queueHead] >> 24 & 0xFF;
				@Pc(108) int tileX = PathFinder.queueX[queueHead] & 0xFFFF;
				@Pc(116) int wallFlag3 = PathFinder.queueY[queueHead] >> 16 & 0xFF;
				@Pc(122) int tileY = PathFinder.queueY[queueHead] & 0xFFFF;
				queueHead = queueHead + 1 & 0xFFF;
				@Pc(130) boolean isOutdoors = false;
				@Pc(132) boolean hasRoofAbove = false;
				if ((SceneGraph.renderFlags[plane][tileX][tileY] & API.TILE_FLAG_UNDER_ROOF) == 0) {
					isOutdoors = true;
				}
				@Pc(150) int upperPlane;
				@Pc(191) int sceneryIndex;
				nextPlane:
				for (upperPlane = plane + 1; upperPlane <= 3; upperPlane++) {
					if ((SceneGraph.renderFlags[upperPlane][tileX][tileY] & 0x8) == 0) {
						@Pc(227) int collisionMask;
						@Pc(358) int locIdWithRotation;
						if (isOutdoors && tiles[upperPlane][tileX][tileY] != null) {
							if (tiles[upperPlane][tileX][tileY].wall != null) {
								sceneryIndex = SceneGraph.getLocCollisionMask(wallFlag1);
								if (tiles[upperPlane][tileX][tileY].wall.primaryFlags == sceneryIndex || tiles[upperPlane][tileX][tileY].wall.secondaryFlags == sceneryIndex) {
									continue;
								}
								if (wallFlag2 != 0) {
									collisionMask = SceneGraph.getLocCollisionMask(wallFlag2);
									if (collisionMask == tiles[upperPlane][tileX][tileY].wall.primaryFlags || tiles[upperPlane][tileX][tileY].wall.secondaryFlags == collisionMask) {
										continue;
									}
								}
								if (wallFlag3 != 0) {
									collisionMask = SceneGraph.getLocCollisionMask(wallFlag3);
									if (collisionMask == tiles[upperPlane][tileX][tileY].wall.primaryFlags || collisionMask == tiles[upperPlane][tileX][tileY].wall.secondaryFlags) {
										continue;
									}
								}
							}
							if (tiles[upperPlane][tileX][tileY].scenery != null) {
								for (sceneryIndex = 0; sceneryIndex < tiles[upperPlane][tileX][tileY].sceneryLen; sceneryIndex++) {
									collisionMask = (int) (tiles[upperPlane][tileX][tileY].scenery[sceneryIndex].key >> 14 & 0x3FL);
									if (collisionMask == 21) {
										collisionMask = 19;
									}
									@Pc(352) int locRotation = (int) (tiles[upperPlane][tileX][tileY].scenery[sceneryIndex].key >> 20 & 0x3L);
									locIdWithRotation = collisionMask | locRotation << 6;
									if (locIdWithRotation == wallFlag1 || wallFlag2 != 0 && locIdWithRotation == wallFlag2 || wallFlag3 != 0 && wallFlag3 == locIdWithRotation) {
										continue nextPlane;
									}
								}
							}
						}
						hasRoofAbove = true;
						@Pc(395) Tile upperTile = tiles[upperPlane][tileX][tileY];
						if (upperTile != null && upperTile.sceneryLen > 0) {
							for (collisionMask = 0; collisionMask < upperTile.sceneryLen; collisionMask++) {
								@Pc(418) Scenery sceneryObj = upperTile.scenery[collisionMask];
								if (sceneryObj.xMax != sceneryObj.xMin || sceneryObj.yMax != sceneryObj.yMin) {
									for (locIdWithRotation = sceneryObj.xMin; locIdWithRotation <= sceneryObj.xMax; locIdWithRotation++) {
										for (@Pc(450) int markY = sceneryObj.yMin; markY <= sceneryObj.yMax; markY++) {
											roofVisibility[upperPlane][locIdWithRotation][markY] = fillValue;
										}
									}
								}
							}
						}
						roofVisibility[upperPlane][tileX][tileY] = fillValue;
					}
				}
				if (hasRoofAbove) {
					if (SceneGraph.tileHeights[plane + 1][tileX][tileY] > roofGroupMaxHeight[groupId]) {
						roofGroupMaxHeight[groupId] = SceneGraph.tileHeights[plane + 1][tileX][tileY];
					}
					upperPlane = tileX << 7;
					if (upperPlane < roofGroupMinX[groupId]) {
						roofGroupMinX[groupId] = upperPlane;
					} else if (roofGroupMaxX[groupId] < upperPlane) {
						roofGroupMaxX[groupId] = upperPlane;
					}
					sceneryIndex = tileY << 7;
					if (roofGroupMinZ[groupId] > sceneryIndex) {
						roofGroupMinZ[groupId] = sceneryIndex;
					} else if (roofGroupMaxZ[groupId] < sceneryIndex) {
						roofGroupMaxZ[groupId] = sceneryIndex;
					}
					API.AddRoofVisibilityGroupTile(groupId, plane, tileX, tileY);
				}
				if (!isOutdoors) {
					if (tileX >= 1 && roofVisibility[plane][tileX - 1][tileY] != fillValue) {
						PathFinder.queueX[queueTail] = tileX - 1 | 0x120000 | 0xD3000000;
						PathFinder.queueY[queueTail] = tileY | 0x130000;
						queueTail = queueTail + 1 & 0xFFF;
						roofVisibility[plane][tileX - 1][tileY] = fillValue;
					}
					tileY++;
					if (tileY < 104) {
						if (tileX - 1 >= 0 && fillValue != roofVisibility[plane][tileX - 1][tileY] && (SceneGraph.renderFlags[plane][tileX][tileY] & API.TILE_FLAG_UNDER_ROOF) == 0 && (SceneGraph.renderFlags[plane][tileX - 1][tileY - 1] & API.TILE_FLAG_UNDER_ROOF) == 0) {
							PathFinder.queueX[queueTail] = 0x52000000 | 0x120000 | tileX - 1;
							PathFinder.queueY[queueTail] = tileY | 0x130000;
							roofVisibility[plane][tileX - 1][tileY] = fillValue;
							queueTail = queueTail + 1 & 0xFFF;
						}
						if (fillValue != roofVisibility[plane][tileX][tileY]) {
							PathFinder.queueX[queueTail] = tileX | 0x13000000 | 0x520000;
							PathFinder.queueY[queueTail] = tileY | 0x530000;
							queueTail = queueTail + 1 & 0xFFF;
							roofVisibility[plane][tileX][tileY] = fillValue;
						}
						if (tileX + 1 < 104 && roofVisibility[plane][tileX + 1][tileY] != fillValue && (SceneGraph.renderFlags[plane][tileX][tileY] & API.TILE_FLAG_UNDER_ROOF) == 0 && (SceneGraph.renderFlags[plane][tileX + 1][tileY - 1] & API.TILE_FLAG_UNDER_ROOF) == 0) {
							PathFinder.queueX[queueTail] = 0x92000000 | 0x520000 | tileX + 1;
							PathFinder.queueY[queueTail] = tileY | 0x530000;
							roofVisibility[plane][tileX + 1][tileY] = fillValue;
							queueTail = queueTail + 1 & 0xFFF;
						}
					}
					tileY--;
					if (tileX + 1 < 104 && fillValue != roofVisibility[plane][tileX + 1][tileY]) {
						PathFinder.queueX[queueTail] = tileX + 1 | 0x920000 | 0x53000000;
						PathFinder.queueY[queueTail] = tileY | 0x930000;
						roofVisibility[plane][tileX + 1][tileY] = fillValue;
						queueTail = queueTail + 1 & 0xFFF;
					}
					tileY--;
					if (tileY >= 0) {
						if (tileX - 1 >= 0 && roofVisibility[plane][tileX - 1][tileY] != fillValue && (SceneGraph.renderFlags[plane][tileX][tileY] & API.TILE_FLAG_UNDER_ROOF) == 0 && (SceneGraph.renderFlags[plane][tileX - 1][tileY + 1] & API.TILE_FLAG_UNDER_ROOF) == 0) {
							PathFinder.queueX[queueTail] = tileX - 1 | 0xD20000 | 0x12000000;
							PathFinder.queueY[queueTail] = tileY | 0xD30000;
							roofVisibility[plane][tileX - 1][tileY] = fillValue;
							queueTail = queueTail + 1 & 0xFFF;
						}
						if (fillValue != roofVisibility[plane][tileX][tileY]) {
							PathFinder.queueX[queueTail] = tileX | 0xD20000 | 0x93000000;
							PathFinder.queueY[queueTail] = tileY | 0xD30000;
							queueTail = queueTail + 1 & 0xFFF;
							roofVisibility[plane][tileX][tileY] = fillValue;
						}
						if (tileX + 1 < 104 && roofVisibility[plane][tileX + 1][tileY] != fillValue && (SceneGraph.renderFlags[plane][tileX][tileY] & API.TILE_FLAG_UNDER_ROOF) == 0 && (SceneGraph.renderFlags[plane][tileX + 1][tileY + 1] & API.TILE_FLAG_UNDER_ROOF) == 0) {
							PathFinder.queueX[queueTail] = tileX + 1 | 0xD2000000 | 0x920000;
							PathFinder.queueY[queueTail] = tileY | 0x930000;
							roofVisibility[plane][tileX + 1][tileY] = fillValue;
							queueTail = queueTail + 1 & 0xFFF;
						}
					}
				}
			}
			if (roofGroupMaxHeight[groupId] != -1000000) {
				roofGroupMaxHeight[groupId] += 10;
				roofGroupMinX[groupId] -= 50;
				roofGroupMaxX[groupId] += 50;
				roofGroupMaxZ[groupId] += 50;
				roofGroupMinZ[groupId] -= 50;
			}
			return true;
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(Lclient!na;BZ)V")
	public static void openUrl(@OriginalArg(0) JagString targetUrl, @OriginalArg(2) boolean newWindow) {
		if (!newWindow) {
			try {
				GameShell.instance.getAppletContext().showDocument(targetUrl.toRelativeUrl(GameShell.instance.getCodeBase()), "_top");
			} catch (@Pc(22) Exception ex) {
			}
			return;
		}
		if (GlRenderer.enabled && GameShell.openWindowJavaScript) {
			try {
				BrowserControl.call(GameShell.signLink.applet, "openjs", new Object[]{targetUrl.toRelativeUrl(GameShell.instance.getCodeBase()).toString()});
				return;
			} catch (@Pc(48) Throwable ex) {
			}
		}
		try {
			GameShell.instance.getAppletContext().showDocument(targetUrl.toRelativeUrl(GameShell.instance.getCodeBase()), "_blank");
		} catch (@Pc(59) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!og", name = "a", descriptor = "(BIILclient!fe;III)V")
	public static void setOverheadScreenCoordinateOffsets(@OriginalArg(1) int halfHeight, @OriginalArg(2) int zoomH, @OriginalArg(3) PathingEntity entity, @OriginalArg(4) int zoomV, @OriginalArg(5) int overheadHeight, @OriginalArg(6) int halfWidth) {
		projectToScreen(halfWidth, zoomH, entity.yFine, overheadHeight, halfHeight, entity.xFine, zoomV);
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(IIIIIIBI)V")
	public static void projectToScreen(@OriginalArg(0) int halfWidth, @OriginalArg(1) int zoomH, @OriginalArg(2) int yFine, @OriginalArg(3) int projHeight, @OriginalArg(4) int halfHeight, @OriginalArg(5) int xFine, @OriginalArg(7) int zoomV) {
		if (xFine < 128 || yFine < 128 || xFine > 13056 || yFine > 13056) {
			screenY = -1;
			screenX = -1;
			return;
		}
		@Pc(38) int heightAboveTile = SceneGraph.getTileHeight(Player.plane, xFine, yFine) - projHeight;
		@Pc(42) int dy = yFine - Camera.renderY;
		@Pc(46) int dz = heightAboveTile - Camera.renderZ;
		@Pc(50) int dx = xFine - Camera.renderX;
		@Pc(54) int sinPitch = MathUtils.sin[Camera.cameraPitch];
		@Pc(58) int cosPitch = MathUtils.cos[Camera.cameraPitch];
		@Pc(62) int sinYaw = MathUtils.sin[Camera.cameraYaw];
		@Pc(66) int cosYaw = MathUtils.cos[Camera.cameraYaw];
		@Pc(76) int rotatedX = dx * cosYaw + sinYaw * dy >> 16;
		@Pc(87) int rotatedZ = dy * cosYaw - sinYaw * dx >> 16;
		@Pc(89) int screenDx = rotatedX;
		@Pc(99) int screenDy = cosPitch * dz - rotatedZ * sinPitch >> 16;
		@Pc(113) int depth = rotatedZ * cosPitch + dz * sinPitch >> 16;
		if (depth < 50) {
			screenY = -1;
			screenX = -1;
		} else if (GlRenderer.enabled) {
			@Pc(150) int scaledHalfWidth = zoomH * 512 >> 8;
			screenX = scaledHalfWidth * screenDx / depth + halfWidth;
			@Pc(164) int scaledHalfHeight = zoomV * 512 >> 8;
			screenY = scaledHalfHeight * screenDy / depth + halfHeight;
		} else {
			screenX = (screenDx << 9) / depth + halfWidth;
			screenY = (screenDy << 9) / depth + halfHeight;
		}
	}

	@OriginalMember(owner = "client!ed", name = "b", descriptor = "(II)Lclient!ba;")
	public static World getWorld(@OriginalArg(1) int worldId) {
		return WorldList.loaded && worldId >= WorldList.minId && worldId <= WorldList.maxId ? WorldList.worlds[worldId - WorldList.minId] : null;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "()V")
	public static void clearSceneScenery() {
		for (@Pc(1) int i = 0; i < SceneGraph.sceneryLen; i++) {
			@Pc(8) Scenery scenery = SceneGraph.scenery[i];
			SceneGraph.removeScenery(scenery);
			SceneGraph.scenery[i] = null;
		}
		SceneGraph.sceneryLen = 0;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(Z)Lclient!na;")
	public static JagString buildSettingsUrl() {
		@Pc(8) JagString domain = WWW;
		@Pc(10) JagString settingsParam = JagString.EMPTY;
		if (client.modeWhere != 0) {
			domain = WWW_QA;
		}
		if (client.settings != null) {
			settingsParam = JagString.concatenate(new JagString[]{PARAM_SETTINGS, client.settings});
		}
		return JagString.concatenate(new JagString[]{HTTP_PREFIX, domain, DOMAIN_LANG, JagString.parseInt(client.language), PARAM_AFFILIATE, JagString.parseInt(client.affiliate), settingsParam, SLASH});
	}

	@OriginalMember(owner = "client!ol", name = "a", descriptor = "(IIIILclient!th;IJIIII)Z")
	public static boolean addAttachedEntityToScene(@OriginalArg(0) int plane, @OriginalArg(1) int xFine, @OriginalArg(2) int yFine, @OriginalArg(3) int tileHeight, @OriginalArg(4) Entity entity, @OriginalArg(5) int angle, @OriginalArg(6) long key, @OriginalArg(7) int x0, @OriginalArg(8) int y0, @OriginalArg(9) int x1, @OriginalArg(10) int y1) {
		return entity == null || SceneGraph.addSceneryEntity(plane, x0, y0, x1 + 1 - x0, y1 - y0 + 1, xFine, yFine, tileHeight, entity, angle, true, key);
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(I)Z")
	public static boolean isShowingVideoAd() {
		if (client.objectTag) {
			try {
				return !((Boolean) SHOWING_VIDEO_AD.browserControlCall(GameShell.signLink.applet));
			} catch (@Pc(21) Throwable ex) {
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!uh", name = "f", descriptor = "(I)V")
	public static void updateRoofVisibilityGroups() {
		API.DisableRoofVisibilityIfExpired();
		if (getRoofRemovalMode() != 2) {
			return;
		}
		API.EnsureRoofVisibilityBuffers();
		boolean roofVisibilityActive = API.IsRoofVisibilityActive();
		@Pc(27) byte staleValue = (byte) (renderCycle - 4 & 0xFF);
		@Pc(31) int column = renderCycle % 104;
		@Pc(33) int i;
		@Pc(40) int cameraTileY;
		for (i = 0; i < 4; i++) {
			for (cameraTileY = 0; cameraTileY < 104; cameraTileY++) {
				roofVisibility[i][column][cameraTileY] = staleValue;
			}
		}
		if (Player.plane == 3) {
			return;
		}
		for (i = 0; i < roofGroupMaxHeight.length; i++) {
			roofGroupMaxHeight[i] = -1000000;
			roofGroupMinX[i] = 1000000;
			roofGroupMaxX[i] = 0;
			roofGroupMinZ[i] = 1000000;
			roofGroupMaxZ[i] = 0;
		}
		if (Camera.cameraType != 1) {
			i = SceneGraph.getTileHeight(Player.plane, Camera.renderX, Camera.renderY);
			if (i - Camera.renderZ < 800 && (SceneGraph.renderFlags[Player.plane][Camera.renderX >> 7][Camera.renderY >> 7] & API.TILE_FLAG_UNDER_ROOF) != 0) {
				floodFillRoofVisibilityGroup(false, Camera.renderX >> 7, Camera.renderY >> 7, SceneGraph.tiles, 1);
			}
			return;
		}
		if ((SceneGraph.renderFlags[Player.plane][PlayerList.self.xFine >> 7][PlayerList.self.yFine >> 7] & API.TILE_FLAG_UNDER_ROOF) != 0) {
			floodFillRoofVisibilityGroup(false, PlayerList.self.xFine >> 7, PlayerList.self.yFine >> 7, SceneGraph.tiles, 0);
		}
		if (roofVisibilityActive) {
			API.ApplyRoofVisibilityRequests();
		}
		if (Camera.cameraPitch >= 310) {
			return;
		}
		@Pc(135) int playerTileY = PlayerList.self.yFine >> 7;
		cameraTileY = Camera.renderY >> 7;
		@Pc(146) int deltaY;
		if (cameraTileY < playerTileY) {
			deltaY = playerTileY - cameraTileY;
		} else {
			deltaY = cameraTileY - playerTileY;
		}
		i = Camera.renderX >> 7;
		@Pc(162) int playerTileX = PlayerList.self.xFine >> 7;
		@Pc(174) int deltaX;
		if (playerTileX > i) {
			deltaX = playerTileX - i;
		} else {
			deltaX = i - playerTileX;
		}
		@Pc(192) int stepFraction;
		@Pc(186) int accumulator;
		if (deltaX <= deltaY) {
			accumulator = 32768;
			stepFraction = deltaX * 65536 / deltaY;
			while (cameraTileY != playerTileY) {
				if (cameraTileY < playerTileY) {
					cameraTileY++;
				} else if (cameraTileY > playerTileY) {
					cameraTileY--;
				}
				if ((SceneGraph.renderFlags[Player.plane][i][cameraTileY] & API.TILE_FLAG_UNDER_ROOF) != 0) {
					floodFillRoofVisibilityGroup(false, i, cameraTileY, SceneGraph.tiles, 1);
					break;
				}
				accumulator += stepFraction;
				if (accumulator >= 65536) {
					if (playerTileX > i) {
						i++;
					} else if (playerTileX < i) {
						i--;
					}
					accumulator -= 65536;
					if ((SceneGraph.renderFlags[Player.plane][i][cameraTileY] & API.TILE_FLAG_UNDER_ROOF) != 0) {
						floodFillRoofVisibilityGroup(false, i, cameraTileY, SceneGraph.tiles, 1);
						break;
					}
				}
			}
			return;
		}
		accumulator = 32768;
		stepFraction = deltaY * 65536 / deltaX;
		while (playerTileX != i) {
			if (playerTileX > i) {
				i++;
			} else if (i > playerTileX) {
				i--;
			}
			if ((SceneGraph.renderFlags[Player.plane][i][cameraTileY] & API.TILE_FLAG_UNDER_ROOF) != 0) {
				floodFillRoofVisibilityGroup(false, i, cameraTileY, SceneGraph.tiles, 1);
				break;
			}
			accumulator += stepFraction;
			if (accumulator >= 65536) {
				if (cameraTileY < playerTileY) {
					cameraTileY++;
				} else if (playerTileY < cameraTileY) {
					cameraTileY--;
				}
				accumulator -= 65536;
				if ((SceneGraph.renderFlags[Player.plane][i][cameraTileY] & API.TILE_FLAG_UNDER_ROOF) != 0) {
					floodFillRoofVisibilityGroup(false, i, cameraTileY, SceneGraph.tiles, 1);
					break;
				}
			}
		}
	}

	@OriginalMember(owner = "client!lf", name = "a", descriptor = "(I)V")
	public static void refreshGameStateAfterSettingsChange() {
		if (client.gameState == 10 && GlRenderer.enabled) {
			client.setGameState(28);
		}
		if (client.gameState == 30) {
			client.setGameState(25);
		}
	}

	@OriginalMember(owner = "client!sf", name = "b", descriptor = "(B)V")
	public static void layoutMiniMenu() {
		@Pc(16) int menuWidth = Fonts.b12Full.getStringWidth(LocalizedText.CHOOSE_OPTION);
		@Pc(18) int menuHeight;
		@Pc(27) int menuX;
		for (menuHeight = 0; menuHeight < MiniMenu.size; menuHeight++) {
			menuX = Fonts.b12Full.getStringWidth(MiniMenu.getOp(menuHeight));
			if (menuX > menuWidth) {
				menuWidth = menuX;
			}
		}
		menuHeight = MiniMenu.size * 15 + 21;
		@Pc(43) int menuY = interfaceMouseY;
		menuWidth += 8;
		menuX = interfaceMouseX - menuWidth / 2;
		if (menuY + menuHeight > GameShell.canvasHeight) {
			menuY = GameShell.canvasHeight - menuHeight;
		}
		if (GameShell.canvasWidth < menuX + menuWidth) {
			menuX = GameShell.canvasWidth - menuWidth;
		}
		if (menuX < 0) {
			menuX = 0;
		}
		if (menuY < 0) {
			menuY = 0;
		}
		if (MiniMenu.clickProcessingState == 1) {
			if (interfaceMouseX == Mouse.lastHandledClickX && Mouse.lastHandledClickY == interfaceMouseY) {
				InterfaceList.menuHeight = MiniMenu.size * 15 + (InterfaceList.useStyledMenu ? 26 : 22);
				MiniMenu.clickProcessingState = 0;
				InterfaceList.menuY = menuY;
				InterfaceList.menuX = menuX;
				Cs1ScriptRunner.isMenuOpen = true;
				InterfaceList.menuWidth = menuWidth;
			}
		} else if (interfaceMouseX == Mouse.clickX && interfaceMouseY == Mouse.clickY) {
			InterfaceList.menuX = menuX;
			MiniMenu.clickProcessingState = 0;
			InterfaceList.menuWidth = menuWidth;
			InterfaceList.menuY = menuY;
			InterfaceList.menuHeight = (InterfaceList.useStyledMenu ? 26 : 22) + MiniMenu.size * 15;
			Cs1ScriptRunner.isMenuOpen = true;
		} else {
			Mouse.lastHandledClickY = Mouse.clickY;
			Mouse.lastHandledClickX = Mouse.clickX;
			MiniMenu.clickProcessingState = 1;
		}
	}

	@OriginalMember(owner = "client!gn", name = "b", descriptor = "(Z)V")
	public static void forceRedrawAllRectangles() {
		for (@Pc(11) int i = 0; i < 100; i++) {
			InterfaceList.rectangleDirty[i] = true;
		}
	}

	public static class Cs2Opcodes {
		// Component setters
		public static final int setPosition = 1000;
		public static final int setSize = 1001;
		public static final int setHidden = 1003;
		public static final int setAspect = 1004;
		public static final int setNoClickThrough = 1005;

		public static final int setScrollPos = 1100;
		public static final int setTrans = 1103;
		public static final int setLineWid = 1104;
		public static final int setSprite = 1105;
		public static final int set2DAngle = 1106;
		public static final int setSpriteTiling = 1107;
		public static final int setModel = 1108;
		public static final int set3DRotation = 1109;
		public static final int setAnimation = 1110;
		public static final int setModelOrthog = 1111;
		public static final int setText = 1112;
		public static final int setFont = 1113;
		public static final int setTextAlignment = 1114;
		public static final int setTextAntiMacro = 1115;
		public static final int setOutlineThickness = 1116;
		public static final int setShadowColor = 1117;
		public static final int setVFlip = 1118;
		public static final int setHFlip = 1119;
		public static final int setScrollMax = 1120;
		public static final int setAlpha = 1122;
		public static final int set3DViewDistance = 1123;

		public static final int setItem = 1200;
		public static final int setNpcHead = 1201;
		public static final int setPlayerHead = 1202;
		public static final int setPlayerFull = 1204;
		public static final int setItemNoNum = 1205;

		// Component properties
		public static final int getX = 1500;
		public static final int getY = 1501;
		public static final int getWidth = 1502;
		public static final int getHeight = 1503;
		public static final int getHidden = 1504;
		public static final int getLayer = 1505;

		// Bit utilities
		public static final int addPercent = 4007;
		public static final int flagBit = 4008;
		public static final int unflagBit = 4009;
		public static final int isBitFlagged = 4010;
		public static final int mod = 4011;
		public static final int pow = 4012;
		public static final int root = 4013;
		public static final int bitAnd = 4014;
		public static final int bitOr = 4015;
		public static final int min = 4016;
		public static final int max = 4017;
		public static final int multiplyDivide = 4018;

		// String utilities
		public static final int concatInt = 4100;
		public static final int concatString = 4101;
		public static final int concatSignedInt = 4102;
		public static final int toLowerStr = 4103;
		public static final int timeToStr = 4104;
		public static final int strForGender = 4105;
		public static final int parseInt = 4106;
		public static final int compare = 4107;
		public static final int getLineCount = 4108;
		public static final int getMaxLineWidth = 4109;
		public static final int chooseString = 4110;
		public static final int escape = 4111;
		public static final int concatChar = 4112;
		public static final int isValidChar = 4113;
		public static final int isAlphaNumeric = 4114;
		public static final int isLetter = 4115;
		public static final int isDigit = 4116;
		public static final int length = 4117;
		public static final int substr = 4118;
		public static final int removeTags = 4119;
		public static final int indexOfChar = 4120;
		public static final int indexOfStr = 4121;
		public static final int toLower = 4122;
		public static final int toUpper = 4123;
		public static final int formatNumber = 4124;

		// Objs
		public static final int itemIsMembers = 4207;
		public static final int itemAttribute = 4208;
		public static final int searchItem = 4210;

		// Params
		public static final int getLocParam = 4400;
		public static final int getStructParam = 4500;

		// Privacy
		public static final int getPublicChatSetting = 5000;
		public static final int setChatSettings = 5001;
		public static final int getPrivateChatSetting = 5005;

		// Ads
		public static final int canShowVideoAd = 6405;
		public static final int isShowingVideoAd = 6406;

		// Worlds
		public static final int getFirstWorldData = 6501;
		public static final int getNextWorldData = 6502;
		public static final int setWorldHost = 6503;
		public static final int setLastWorld = 6504;
		public static final int getLastWorld = 6505;
		public static final int sortWorldList = 6507;

		public static final int setChild = 200;
		public static final int setChild2 = 201;
		public static final int setBaseIdkit = 403;
		public static final int setBaseColor = 404;
		public static final int setFemale = 410;
		public static final int setRGB = 1101;
		public static final int setFilled = 1102;
		public static final int setContextMenuOption = 1300;
		public static final int setDraggable = 1301;
		public static final int setDragRenderBehaviour = 1302;
		public static final int setDragDeadZone = 1303;
		public static final int setDragDeadTime = 1304;
		public static final int setOptionBase = 1305;
		public static final int setTargetVerb = 1306;
		public static final int hookMousePress = 1400;
		public static final int hookDraggedOver = 1401;
		public static final int hookMouseRelease = 1402;
		public static final int hookMouseEnter = 1403;
		public static final int hookMouseExit = 1404;
		public static final int hookDragStart = 1405;
		public static final int hookUseWith = 1406;
		public static final int hookVARP = 1407;
		public static final int hookFrame = 1408;
		public static final int hookOptionClick = 1409;
		public static final int hookDragRelease = 1410;
		public static final int hookDrag = 1411;
		public static final int hookMouseHover = 1412;
		public static final int hookContainer = 1414;
		public static final int hookSkill = 1415;
		public static final int hookOnUse = 1416;
		public static final int hookScroll = 1417;
		public static final int hookMsg = 1418;
		public static final int hookKey = 1419;
		public static final int hookFriendList = 1420;
		public static final int hookClanList = 1421;
		public static final int hookMiscData = 1422;
		public static final int hookDialogAbort = 1423;
		public static final int hookWidgetsOpenClose = 1424;
		public static final int hookGEUpdate = 1425;
		public static final int hookMinimapUnlock = 1426;
		public static final int hookResize = 1427;
		public static final int hookVARC = 1428;
		public static final int hookSTRING = 1429;
		public static final int getScrollX = 1600;
		public static final int getScrollY = 1601;
		public static final int getText = 1602;
		public static final int getScrollMaxH = 1603;
		public static final int getScrollMaxV = 1604;
		public static final int get3DDistance = 1605;
		public static final int getRotateX = 1606;
		public static final int getRotateZ = 1607;
		public static final int getRotateY = 1608;
		public static final int getAlpha = 1609;
		public static final int getModelXOffset = 1610;
		public static final int getModelYOffset = 1611;
		public static final int getSpriteId = 1612;
		public static final int getItemId = 1700;
		public static final int getItemAmt = 1701;
		public static final int getChildId = 1702;
		public static final int getTargetMask = 1800;
		public static final int getOption = 1801;
		public static final int getOptionBase = 1802;
		public static final int getX2 = 2500;
		public static final int getY2 = 2501;
		public static final int getWidth2 = 2502;
		public static final int getHeight2 = 2503;
		public static final int isHidden2 = 2504;
		public static final int getLayer2 = 2505;
		public static final int getScrollX2 = 2600;
		public static final int getScrollY2 = 2601;
		public static final int getText2 = 2602;
		public static final int getScrollMaxH2 = 2603;
		public static final int getScrollMaxV2 = 2604;
		public static final int get3DDistance2 = 2605;
		public static final int getRotateX2 = 2606;
		public static final int getRotateZ2 = 2607;
		public static final int getRotateY2 = 2608;
		public static final int getAlpha2 = 2609;
		public static final int getModelXOffset2 = 2610;
		public static final int getModelYOffset2 = 2611;
		public static final int getSpriteId2 = 2612;
		public static final int getItemId2 = 2700;
		public static final int getItemAmt2 = 2701;
		public static final int hasChild = 2702;
		public static final int nextChild = 2703;
		public static final int hasChildModal = 2704;
		public static final int hasChildOverlay = 2705;
		public static final int getTargetMask2 = 2800;
		public static final int getOption2 = 2801;
		public static final int getOptionBase2 = 2802;
		public static final int msg = 3100;
		public static final int animateSelf = 3101;
		public static final int sendIntegerInput = 3104;
		public static final int sendNameInput = 3105;
		public static final int sendStringInput = 3106;
		public static final int clickPlayerOption = 3107;
		public static final int playSoundEffect = 3200;
		public static final int playMusic = 3201;
		public static final int playMusicEffect = 3202;
		public static final int getClientCycle = 3300;
		public static final int getItemIdInSlot = 3301;
		public static final int getItemAmtInSlot = 3302;
		public static final int getItemAmtInContainer = 3303;
		public static final int getItemContainerLength = 3304;
		public static final int getSkillCurrentLvl = 3305;
		public static final int getSkillActualLvl = 3306;
		public static final int getSkillXp = 3307;
		public static final int getMyLocation = 3308;
		public static final int x = 3309;
		public static final int z = 3310;
		public static final int y = 3311;
		public static final int isMember = 3312;
		public static final int getItemIdInInpsectingSlot = 3313;
		public static final int getItemAmtInInspectingSlot = 3314;
		public static final int getItemAmtInInspectingContainer = 3315;
		public static final int getClientRights = 3316;
		public static final int getSystemUpdateTimer = 3317;
		public static final int getWorldId = 3318;
		public static final int getRunEnergy = 3321;
		public static final int getPlayerWeight = 3322;
		public static final int hasMoreThen5Blackmarks = 3323;
		public static final int getBlackmarks = 3324;
		public static final int isSiteSettingsMembers = 3325;
		public static final int getMyCombat = 3326;
		public static final int IsFemale = 3327;
		public static final int mapQuickchat = 3329;
		public static final int getContainerFreeSlots = 3330;
		public static final int getContainerIntAttrCountIgnoreStacks = 3331;
		public static final int getContainerIntAttrCount = 3332;
		public static final int getLoginTypeRelatedMethod3333 = 3333;
		public static final int getLanguage = 3335;
		public static final int moveCoord = 3336;
		public static final int getAffid = 3337;
		public static final int datamap = 3400;
		public static final int datamap2 = 3408;
		public static final int datamapContainsValue = 3409;
		public static final int datamapContainsValue2 = 3410;
		public static final int datamapSize = 3411;
		public static final int getFriendCount = 3600;
		public static final int getFriendName = 3601;
		public static final int getFriendWorld = 3602;
		public static final int getFriendRank = 3603;
		public static final int isFriend = 3609;
		public static final int getFriendWorldName = 3610;
		public static final int clanChatDisplayName = 3611;
		public static final int clanGetChatCount = 3612;
		public static final int clanChatUsername = 3613;
		public static final int clanChatUserWorld = 3614;
		public static final int clanChatUserRank = 3615;
		public static final int getIgnoreCount = 3621;
		public static final int getIgnoreName = 3622;
		public static final int getGEIsSelling = 3903;
		public static final int getGEItem = 3904;
		public static final int getGEItemPrice = 3905;
		public static final int getGEItemAmt = 3906;
		public static final int getGEItemAmtTransfered = 3907;
		public static final int getGECashTransfered = 3908;
		public static final int getGENotStarted = 3910;
		public static final int getGEIsStatus2 = 3911;
		public static final int getGEIsDone = 3912;
		public static final int getGEIsStatus1 = 3913;
		public static final int add = 4000;
		public static final int subtract = 4001;
		public static final int multiply = 4002;
		public static final int divide = 4003;
		public static final int rndExcl = 4004;
		public static final int rnd = 4005;
		public static final int getItemName = 4200;
		public static final int getItemGroundOption = 4201;
		public static final int getItemOption = 4202;
		public static final int getItemValue = 4203;
		public static final int itemIsStackable = 4204;
		public static final int getNotedItem = 4205;
		public static final int getRealItem = 4206;
		public static final int nextSearchResult = 4211;
		public static final int getNpcAttribute = 4300;
		public static final int getTradeSetting = 5016;
		public static final int isHoldingAlt = 5100;
		public static final int isHoldingCtrl = 5101;
		public static final int isHoldingShift = 5102;
		public static final int setWorldmapZoom = 5200;
		public static final int getWorldmapZoom = 5201;
		public static final int loadDungeonmap = 5205;
		public static final int getDungeonmap = 5206;
		public static final int getDungeonMapName = 5207;
		public static final int getDungeonmapCenter = 5210;
		public static final int setPositionInMap = 5214;
		public static final int dungeonmapContains = 5215;
		public static final int getMapDefaultZoom = 5218;
		public static final int getDisplayMode = 5306;
		public static final int getLastIp5419 = 5419;
		public static final int cameraMoveTo = 5500;
		public static final int cameraPointAt = 5501;
		public static final int cameraUnlock = 5503;
		public static final int cameraGetVrot = 5505;
		public static final int cameraGetHrot = 5506;
		public static final int directlogin = 5600;
		public static final int sendRequestAccount = 5604;
		public static final int sendCreateAccount = 5605;
		public static final int getGameloginRc = 5607;
		public static final int getWorldswitchTimer = 5608;
		public static final int getAccountCreateRC = 5609;
		public static final int getDetailedRC = 5611;
		public static final int getMinute = 6300;
		public static final int getCurrentDaysSinceLaunch = 6301;
		public static final int getDaysSinceLaunch = 6302;
		public static final int getYear = 6303;
		public static final int isLeapYear = 6304;
		public static final int setNoOptions = 1307;
		public static final int sendCloseWidgetPacket = 3103;
		public static final int skipLoginstage10 = 5601;
		public static final int resetRCs = 5602;
		public static final int resetAccountCreateRC = 5606;
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "(BILclient!jl;)V")
	public static void run(@OriginalArg(1) int maxCycles, @OriginalArg(2) HookRequest request) {
		@Pc(4) Object[] arguments = request.arguments;
		@Pc(10) int sid = (Integer) arguments[0];
		@Pc(14) ClientScript script = ClientScriptList.get(sid);
		if (script == null) {
			return;
		}
		fp = 0;
		@Pc(26) int ssp = 0;
		@Pc(28) int isp = 0;
		@Pc(30) int pc = -1;
		@Pc(33) int[] intOperands = script.intOperands;
		@Pc(36) int[] opcodes = script.opcodes;
		@Pc(44) byte op = -1;
		@Pc(58) int cycles;
		try {
			intLocals = new int[script.intLocals];
			@Pc(50) int intLocalIndex = 0;
			stringLocals = new JagString[script.stringLocals];
			@Pc(56) int stringLocalIndex = 0;
			@Pc(77) int id;
			@Pc(194) JagString value;
			for (cycles = 1; cycles < arguments.length; cycles++) {
				if (arguments[cycles] instanceof Integer) {
					id = (Integer) arguments[cycles];
					if (id == 0x80000001) {
						id = request.mouseX;
					}
					if (id == 0x80000002) {
						id = request.mouseY;
					}
					if (id == 0x80000003) {
						id = request.source == null ? -1 : request.source.id;
					}
					if (id == 0x80000004) {
						id = request.op;
					}
					if (id == 0x80000005) {
						id = request.source == null ? -1 : request.source.createdComponentId;
					}
					if (id == 0x80000006) {
						id = request.target == null ? -1 : request.target.id;
					}
					if (id == 0x80000007) {
						id = request.target == null ? -1 : request.target.createdComponentId;
					}
					if (id == 0x80000008) {
						id = request.keyCode;
					}
					if (id == 0x80000009) {
						id = request.keyChar;
					}
					intLocals[intLocalIndex++] = id;
				} else if (arguments[cycles] instanceof JagString) {
					value = (JagString) arguments[cycles];
					if (value.strEquals(EVENT_OPBASE)) {
						value = request.opBase;
					}
					stringLocals[stringLocalIndex++] = value;
				}
			}
			cycles = 0;
			nextOp:
			while (true) {
				cycles++;
				if (maxCycles < cycles) {
					throw new RuntimeException("slow");
				}
				pc++;
				@Pc(226) int opcode = opcodes[pc];
				@Pc(803) int int3;
				@Pc(652) int argIndex;
				@Pc(809) int int1;
				@Pc(609) JagString string;
				if (opcode < 100) {
					if (opcode == 0) {
						intStack[isp++] = intOperands[pc];
						continue;
					}
					if (opcode == 1) {
						id = intOperands[pc];
						intStack[isp++] = VarpDomain.activeVarps[id];
						continue;
					}
					if (opcode == 2) {
						id = intOperands[pc];
						isp--;
						VarpDomain.setVarp(id, intStack[isp]);
						continue;
					}
					if (opcode == 3) {
						stringStack[ssp++] = script.stringOperands[pc];
						continue;
					}
					if (opcode == 6) {
						pc += intOperands[pc];
						continue;
					}
					if (opcode == 7) {
						isp -= 2;
						if (intStack[isp] != intStack[isp + 1]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 8) {
						isp -= 2;
						if (intStack[isp + 1] == intStack[isp]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 9) {
						isp -= 2;
						if (intStack[isp] < intStack[isp + 1]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 10) {
						isp -= 2;
						if (intStack[isp + 1] < intStack[isp]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 21) {
						if (fp == 0) {
							return;
						}
						@Pc(423) GoSubFrame frame = callStack[--fp];
						script = frame.script;
						intLocals = frame.intLocals;
						opcodes = script.opcodes;
						pc = frame.pc;
						stringLocals = frame.stringLocals;
						intOperands = script.intOperands;
						continue;
					}
					if (opcode == 25) {
						id = intOperands[pc];
						intStack[isp++] = VarpDomain.getVarbit(id);
						continue;
					}
					if (opcode == 27) {
						id = intOperands[pc];
						isp--;
						VarpDomain.setVarbitClient(id, intStack[isp]);
						continue;
					}
					if (opcode == 31) {
						isp -= 2;
						if (intStack[isp + 1] >= intStack[isp]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 32) {
						isp -= 2;
						if (intStack[isp] >= intStack[isp + 1]) {
							pc += intOperands[pc];
						}
						continue;
					}
					if (opcode == 33) {
						intStack[isp++] = intLocals[intOperands[pc]];
						continue;
					}
					@Pc(555) int local;
					if (opcode == 34) {
						local = intOperands[pc];
						isp--;
						intLocals[local] = intStack[isp];
						continue;
					}
					if (opcode == 35) {
						stringStack[ssp++] = stringLocals[intOperands[pc]];
						continue;
					}
					if (opcode == 36) {
						local = intOperands[pc];
						ssp--;
						stringLocals[local] = stringStack[ssp];
						continue;
					}
					if (opcode == 37) {
						id = intOperands[pc];
						ssp -= id;
						string = JagString.concatenateRange(ssp, id, stringStack);
						stringStack[ssp++] = string;
						continue;
					}
					if (opcode == 38) {
						isp--;
						continue;
					}
					if (opcode == 39) {
						ssp--;
						continue;
					}
					if (opcode == 40) {
						id = intOperands[pc];
						@Pc(642) ClientScript gosubScript = ClientScriptList.get(id);
						@Pc(646) int[] gosubIntLocals = new int[gosubScript.intLocals];
						@Pc(650) JagString[] gosubStringLocals = new JagString[gosubScript.stringLocals];
						for (argIndex = 0; argIndex < gosubScript.intArgs; argIndex++) {
							gosubIntLocals[argIndex] = intStack[argIndex + isp - gosubScript.intArgs];
						}
						for (argIndex = 0; argIndex < gosubScript.stringArgs; argIndex++) {
							gosubStringLocals[argIndex] = stringStack[argIndex + ssp - gosubScript.stringArgs];
						}
						isp -= gosubScript.intArgs;
						ssp -= gosubScript.stringArgs;
						@Pc(705) GoSubFrame gosubFrame = new GoSubFrame();
						gosubFrame.stringLocals = stringLocals;
						gosubFrame.intLocals = intLocals;
						gosubFrame.pc = pc;
						gosubFrame.script = script;
						if (fp >= callStack.length) {
							throw new RuntimeException();
						}
						script = gosubScript;
						pc = -1;
						callStack[fp++] = gosubFrame;
						intLocals = gosubIntLocals;
						intOperands = gosubScript.intOperands;
						opcodes = gosubScript.opcodes;
						stringLocals = gosubStringLocals;
						continue;
					}
					if (opcode == 42) {
						intStack[isp++] = VarcDomain.varcs[intOperands[pc]];
						continue;
					}
					if (opcode == 43) {
						id = intOperands[pc];
						isp--;
						VarcDomain.varcs[id] = intStack[isp];
						DelayedStateChange.setVarpClient(id);
						continue;
					}
					if (opcode == 44) {
						id = intOperands[pc] >> 16;
						isp--;
						int3 = intStack[isp];
						int1 = intOperands[pc] & 0xFFFF;
						if (int3 >= 0 && int3 <= 5000) {
							globalArrayLengths[id] = int3;
							@Pc(828) byte defaultValue = -1;
							if (int1 == 105) {
								defaultValue = 0;
							}
							argIndex = 0;
							while (true) {
								if (int3 <= argIndex) {
									continue nextOp;
								}
								globalArrays[id][argIndex] = defaultValue;
								argIndex++;
							}
						}
						throw new RuntimeException();
					}
					if (opcode == 45) {
						id = intOperands[pc];
						isp--;
						int1 = intStack[isp];
						if (int1 >= 0 && int1 < globalArrayLengths[id]) {
							intStack[isp++] = globalArrays[id][int1];
							continue;
						}
						throw new RuntimeException();
					}
					if (opcode == 46) {
						id = intOperands[pc];
						isp -= 2;
						int1 = intStack[isp];
						if (int1 >= 0 && int1 < globalArrayLengths[id]) {
							globalArrays[id][int1] = intStack[isp + 1];
							continue;
						}
						throw new RuntimeException();
					}
					if (opcode == 47) {
						value = VarcDomain.varcstrs[intOperands[pc]];
						if (value == null) {
							value = VarpDomain.NULL_STRING;
						}
						stringStack[ssp++] = value;
						continue;
					}
					if (opcode == 48) {
						id = intOperands[pc];
						ssp--;
						VarcDomain.varcstrs[id] = stringStack[ssp];
						DelayedStateChange.setVarcStrClient(id);
						continue;
					}
					if (opcode == 51) {
						@Pc(992) HashTable switchTable = script.switchTables[intOperands[pc]];
						isp--;
						@Pc(1002) IntNode switchEntry = (IntNode) switchTable.get(intStack[isp]);
						if (switchEntry != null) {
							pc += switchEntry.value;
						}
						continue;
					}
				}
				@Pc(1020) boolean useActiveComponent1;
				useActiveComponent1 = intOperands[pc] == 1;
				@Pc(1182) Component component;
				@Pc(1052) int int2;
				@Pc(1063) Component parentComponent;
				@Pc(1087) int j;
				@Pc(1256) Component targetComponent;
				if (opcode < 300) {
					if (opcode == 100) {
						isp -= 3;
						int1 = intStack[isp];
						int3 = intStack[isp + 1];
						int2 = intStack[isp + 2];
						if (int3 != 0) {
							parentComponent = InterfaceList.getComponent(int1);
							if (parentComponent.createdComponents == null) {
								parentComponent.createdComponents = new Component[int2 + 1];
							}
							if (int2 >= parentComponent.createdComponents.length) {
								@Pc(1085) Component[] expandedArray = new Component[int2 + 1];
								for (j = 0; j < parentComponent.createdComponents.length; j++) {
									expandedArray[j] = parentComponent.createdComponents[j];
								}
								parentComponent.createdComponents = expandedArray;
							}
							if (int2 > 0 && parentComponent.createdComponents[int2 - 1] == null) {
								throw new RuntimeException("Gap at:" + (int2 - 1));
							}
							@Pc(1137) Component createdComponent = new Component();
							createdComponent.if3 = true;
							createdComponent.createdComponentId = int2;
							createdComponent.overlayer = createdComponent.id = parentComponent.id;
							createdComponent.type = int3;
							parentComponent.createdComponents[int2] = createdComponent;
							if (useActiveComponent1) {
								staticActiveComponent1 = createdComponent;
							} else {
								staticActiveComponent2 = createdComponent;
							}
							InterfaceList.redraw(parentComponent);
							continue;
						}
						throw new RuntimeException();
					}
					@Pc(1204) Component ownerComponent;
					if (opcode == 101) {
						component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
						if (component.createdComponentId == -1) {
							if (!useActiveComponent1) {
								throw new RuntimeException("Tried to cc_delete static active-component!");
							}
							throw new RuntimeException("Tried to .cc_delete static .active-component!");
						}
						ownerComponent = InterfaceList.getComponent(component.id);
						ownerComponent.createdComponents[component.createdComponentId] = null;
						InterfaceList.redraw(ownerComponent);
						continue;
					}
					if (opcode == 102) {
						isp--;
						component = InterfaceList.getComponent(intStack[isp]);
						component.createdComponents = null;
						InterfaceList.redraw(component);
						continue;
					}
					if (opcode == Cs2Opcodes.setChild) {
						isp -= 2;
						int1 = intStack[isp];
						int3 = intStack[isp + 1];
						targetComponent = InterfaceList.getComponent(int1, int3);
						if (targetComponent != null && int3 != -1) {
							intStack[isp++] = 1;
							if (useActiveComponent1) {
								staticActiveComponent1 = targetComponent;
							} else {
								staticActiveComponent2 = targetComponent;
							}
							continue;
						}
						intStack[isp++] = 0;
						continue;
					}
					if (opcode == Cs2Opcodes.setChild2) {
						isp--;
						int1 = intStack[isp];
						ownerComponent = InterfaceList.getComponent(int1);
						if (ownerComponent == null) {
							intStack[isp++] = 0;
						} else {
							intStack[isp++] = 1;
							if (useActiveComponent1) {
								staticActiveComponent1 = ownerComponent;
							} else {
								staticActiveComponent2 = ownerComponent;
							}
						}
						continue;
					}
				} else {
					@Pc(12388) boolean boolResult;
					if (opcode < 500) {
						if (opcode == Cs2Opcodes.setBaseIdkit) {
							isp -= 2;
							int3 = intStack[isp + 1];
							int1 = intStack[isp];
							for (int2 = 0; int2 < PlayerAppearance.MALE_FEATURES.length; int2++) {
								if (int1 == PlayerAppearance.MALE_FEATURES[int2]) {
									PlayerList.self.appearance.setIdentikit(int2, int3);
									continue nextOp;
								}
							}
							int2 = 0;
							while (true) {
								if (int2 >= PlayerAppearance.FEMALE_FEATURES.length) {
									continue nextOp;
								}
								if (int1 == PlayerAppearance.FEMALE_FEATURES[int2]) {
									PlayerList.self.appearance.setIdentikit(int2, int3);
									continue nextOp;
								}
								int2++;
							}
						}
						if (opcode == Cs2Opcodes.setBaseColor) {
							isp -= 2;
							int1 = intStack[isp];
							int3 = intStack[isp + 1];
							PlayerList.self.appearance.setColor(int1, int3);
							continue;
						}
						if (opcode == Cs2Opcodes.setFemale) {
							isp--;
							boolResult = intStack[isp] != 0;
							PlayerList.self.appearance.setGender(boolResult);
							continue;
						}
					} else {
						@Pc(1552) boolean boolValue;
						if ((opcode < 1000 || opcode >= 1100) && (opcode < 2000 || opcode >= 2100)) {
							@Pc(2522) JagString str1;
							if (opcode >= 1100 && opcode < 1200 || !(opcode < 2100 || opcode >= 2200)) {
								if (opcode < 2000) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
								} else {
									opcode -= 1000;
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
								}
								if (opcode == Cs2Opcodes.setScrollPos) {
									isp -= 2;
									component.scrollX = intStack[isp];
									if (component.scrollX > component.scrollMaxH - component.width) {
										component.scrollX = component.scrollMaxH - component.width;
									}
									if (component.scrollX < 0) {
										component.scrollX = 0;
									}
									component.scrollY = intStack[isp + 1];
									if (component.scrollY > component.scrollMaxV - component.height) {
										component.scrollY = component.scrollMaxV - component.height;
									}
									if (component.scrollY < 0) {
										component.scrollY = 0;
									}
									InterfaceList.redraw(component);
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentScrollClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setRGB) {
									isp--;
									component.color = intStack[isp];
									InterfaceList.redraw(component);
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentColorClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setFilled) {
									isp--;
									component.filled = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setTrans) {
									isp--;
									component.alpha = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setLineWid) {
									isp--;
									component.lineWidth = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setSprite) {
									isp--;
									component.spriteId = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.set2DAngle) {
									isp--;
									component.angle2d = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setSpriteTiling) {
									isp--;
									component.spriteTiling = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setModel) {
									component.modelType = 1;
									isp--;
									component.modelId = intStack[isp];
									InterfaceList.redraw(component);
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.set3DRotation) {
									isp -= 6;
									component.modelXOffset = intStack[isp];
									component.modelZOffset = intStack[isp + 1];
									component.modelXAngle = intStack[isp + 2];
									component.modelYAngle = intStack[isp + 3];
									component.modelYOffset = intStack[isp + 4];
									component.modelZoom = intStack[isp + 5];
									InterfaceList.redraw(component);
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelAngleClient(component.id);
										DelayedStateChange.setComponentModelOffsetClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setAnimation) {
									isp--;
									int3 = intStack[isp];
									if (component.modelSeqId != int3) {
										component.modelSeqId = int3;
										component.seqFrame = 0;
										component.seqCycle = 0;
										component.seqNextFrame = 1;
										InterfaceList.redraw(component);
									}
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentAnimClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setModelOrthog) {
									isp--;
									component.modelOrtho = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setText) {
									ssp--;
									str1 = stringStack[ssp];
									if (!str1.strEquals(component.text)) {
										component.text = str1;
										InterfaceList.redraw(component);
									}
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentTextClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setFont) {
									isp--;
									component.font = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setTextAlignment) {
									isp -= 3;
									component.halign = intStack[isp];
									component.valign = intStack[isp + 1];
									component.vpadding = intStack[isp + 2];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setTextAntiMacro) {
									isp--;
									component.shadowed = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setOutlineThickness) {
									isp--;
									component.outlineThickness = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setShadowColor) {
									isp--;
									component.shadowColor = intStack[isp];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setVFlip) {
									isp--;
									component.vFlip = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setHFlip) {
									isp--;
									component.hFlip = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setScrollMax) {
									isp -= 2;
									component.scrollMaxH = intStack[isp];
									component.scrollMaxV = intStack[isp + 1];
									InterfaceList.redraw(component);
									if (component.type == 0) {
										InterfaceList.layoutComponent(component, false);
									}
									continue;
								}
								if (opcode == 1121) {
									isp -= 2;
									component.modelViewAngle = (short) intStack[isp];
									component.modelNearClip = (short) intStack[isp + 1];
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.setAlpha) {
									isp--;
									component.hasAlpha = intStack[isp] == 1;
									InterfaceList.redraw(component);
									continue;
								}
								if (opcode == Cs2Opcodes.set3DViewDistance) {
									isp--;
									component.modelZoom = intStack[isp];
									InterfaceList.redraw(component);
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelAngleClient(component.id);
									}
									continue;
								}
							} else if (opcode >= 1200 && opcode < 1300 || !(opcode < 2200 || opcode >= 2300)) {
								if (opcode < 2000) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
								} else {
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
									opcode -= 1000;
								}
								InterfaceList.redraw(component);
								if (opcode == Cs2Opcodes.setItem || opcode == Cs2Opcodes.setItemNoNum) {
									isp -= 2;
									int2 = intStack[isp + 1];
									int3 = intStack[isp];
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentObjClient(component.id);
										DelayedStateChange.setComponentModelAngleClient(component.id);
										DelayedStateChange.setComponentModelOffsetClient(component.id);
									}
									if (int3 == -1) {
										component.modelId = -1;
										component.modelType = 1;
										component.objId = -1;
									} else {
										component.objId = int3;
										component.objCount = int2;
										@Pc(13416) ObjType objType = ObjTypeList.get(int3);
										component.modelYOffset = objType.zAngle2D;
										component.modelXOffset = objType.xOffset2D;
										component.modelXAngle = objType.xAngle2D;
										component.modelZOffset = objType.yOffset2D;
										component.modelYAngle = objType.yAngle2D;
										component.modelZoom = objType.zoom2d;
										if (component.modelViewportWidth > 0) {
											component.modelZoom = component.modelZoom * 32 / component.modelViewportWidth;
										} else if (component.baseWidth > 0) {
											component.modelZoom = component.modelZoom * 32 / component.baseWidth;
										}
										component.objDrawText = opcode != Cs2Opcodes.setItemNoNum;
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setNpcHead) {
									component.modelType = 2;
									isp--;
									component.modelId = intStack[isp];
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelClient(component.id);
									}
									continue;
								}
								if (opcode == 1202) {
									component.modelType = 3;
									component.modelId = PlayerList.self.appearance.getAppearanceHash();
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setPlayerHead) {
									component.modelType = 6;
									isp--;
									component.modelId = intStack[isp];
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelClient(component.id);
									}
									continue;
								}
								if (opcode == Cs2Opcodes.setPlayerFull) {
									component.modelType = 5;
									isp--;
									component.modelId = intStack[isp];
									if (component.createdComponentId == -1) {
										DelayedStateChange.setComponentModelClient(component.id);
									}
									continue;
								}
							} else if (opcode >= 1300 && opcode < 1400 || opcode >= 2300 && opcode < 2400) {
								if (opcode >= 2000) {
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
									opcode -= 1000;
								} else {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
								}
								if (opcode == Cs2Opcodes.setContextMenuOption) {
									isp--;
									int3 = intStack[isp] - 1;
									if (int3 >= 0 && int3 <= 9) {
										ssp--;
										component.setOp(stringStack[ssp], int3);
										continue;
									}
									ssp--;
									continue;
								}
								if (opcode == Cs2Opcodes.setDraggable) {
									isp -= 2;
									int2 = intStack[isp + 1];
									int3 = intStack[isp];
									component.dragParent = InterfaceList.getComponent(int3, int2);
									continue;
								}
								if (opcode == Cs2Opcodes.setDragRenderBehaviour) {
									isp--;
									component.dragRenderBehavior = intStack[isp] == 1;
									continue;
								}
								if (opcode == Cs2Opcodes.setDragDeadZone) {
									isp--;
									component.dragDeadzone = intStack[isp];
									continue;
								}
								if (opcode == Cs2Opcodes.setDragDeadTime) {
									isp--;
									component.dragDeadtime = intStack[isp];
									continue;
								}
								if (opcode == Cs2Opcodes.setOptionBase) {
									ssp--;
									component.optionBase = stringStack[ssp];
									continue;
								}
								if (opcode == Cs2Opcodes.setTargetVerb) {
									ssp--;
									component.optionCircumfix = stringStack[ssp];
									continue;
								}
								if (opcode == Cs2Opcodes.setNoOptions) {
									component.ops = null;
									continue;
								}
								if (opcode == 1308) {
									isp--;
									component.defaultTargetCursor = intStack[isp];
									isp--;
									component.targetCursor = intStack[isp];
									continue;
								}
								if (opcode == 1309) {
									isp--;
									int3 = intStack[isp];
									isp--;
									int2 = intStack[isp];
									if (int2 >= 1 && int2 <= 10) {
										component.setDragTarget(int2 - 1, int3);
									}
									continue;
								}
							} else {
								@Pc(4859) int c;
								if (opcode >= 1400 && opcode < 1500 || opcode >= 2400 && opcode < 2500) {
									if (opcode < 2000) {
										component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
									} else {
										opcode -= 1000;
										isp--;
										component = InterfaceList.getComponent(intStack[isp]);
									}
									@Pc(12937) int[] triggers = null;
									ssp--;
									str1 = stringStack[ssp];
									if (str1.length() > 0 && str1.charAt(str1.length() - 1) == 89) {
										isp--;
										argIndex = intStack[isp];
										if (argIndex > 0) {
											triggers = new int[argIndex];
											while (argIndex-- > 0) {
												isp--;
												triggers[argIndex] = intStack[isp];
											}
										}
										str1 = str1.substring(str1.length() - 1, 0);
									}
									@Pc(13000) Object[] hookArgs = new Object[str1.length() + 1];
									for (c = hookArgs.length - 1; c >= 1; c--) {
										if (str1.charAt(c - 1) == 115) {
											ssp--;
											hookArgs[c] = stringStack[ssp];
										} else {
											isp--;
											hookArgs[c] = intStack[isp];
										}
									}
									isp--;
									c = intStack[isp];
									if (c == -1) {
										hookArgs = null;
									} else {
										hookArgs[0] = c;
									}
									component.hasEventHandlers = true;
									if (opcode == Cs2Opcodes.hookMousePress) {
										component.onClickRepeat = hookArgs;
									} else if (opcode == Cs2Opcodes.hookDraggedOver) {
										component.onHold = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMouseRelease) {
										component.onRelease = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMouseEnter) {
										component.onMouseOver = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMouseExit) {
										component.onMouseLeave = hookArgs;
									} else if (opcode == Cs2Opcodes.hookDragStart) {
										component.onDragStart = hookArgs;
									} else if (opcode == Cs2Opcodes.hookUseWith) {
										component.onUseWith = hookArgs;
									} else if (opcode == Cs2Opcodes.hookVARP) {
										component.varpTriggers = triggers;
										component.onVarpTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookFrame) {
										component.onTimer = hookArgs;
									} else if (opcode == Cs2Opcodes.hookOptionClick) {
										component.onOptionClick = hookArgs;
									} else if (opcode == Cs2Opcodes.hookDragRelease) {
										component.onDragRelease = hookArgs;
									} else if (opcode == Cs2Opcodes.hookDrag) {
										component.onDrag = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMouseHover) {
										component.onMouseRepeat = hookArgs;
									} else if (opcode == Cs2Opcodes.hookContainer) {
										component.inventoryTriggers = triggers;
										component.onInvTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookSkill) {
										component.statTriggers = triggers;
										component.onStatTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookOnUse) {
										component.onUse = hookArgs;
									} else if (opcode == Cs2Opcodes.hookScroll) {
										component.onScroll = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMsg) {
										component.onMsg = hookArgs;
									} else if (opcode == Cs2Opcodes.hookKey) {
										component.onKey = hookArgs;
									} else if (opcode == Cs2Opcodes.hookFriendList) {
										component.onFriendTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookClanList) {
										component.onClanTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMiscData) {
										component.onMiscTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookDialogAbort) {
										component.onDialogAbort = hookArgs;
									} else if (opcode == Cs2Opcodes.hookWidgetsOpenClose) {
										component.onWidgetsOpenClose = hookArgs;
									} else if (opcode == Cs2Opcodes.hookGEUpdate) { // if_setonstocktransmit
										component.onStockTransmit = hookArgs;
									} else if (opcode == Cs2Opcodes.hookMinimapUnlock) {
										component.onMinimapUnlock = hookArgs;
									} else if (opcode == Cs2Opcodes.hookResize) {
										component.onResize = hookArgs;
									} else if (opcode == Cs2Opcodes.hookVARC) {
										component.onVarcTransmit = hookArgs;
										component.varcTriggers = triggers;
									} else if (opcode == Cs2Opcodes.hookSTRING) {
										component.varcstrTriggers = triggers;
										component.onVarcstrTransmit = hookArgs;
									}
									continue;
								}
								if (opcode < 1600) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
									if (opcode == Cs2Opcodes.getX) {
										intStack[isp++] = component.x;
										continue;
									}
									if (opcode == Cs2Opcodes.getY) {
										intStack[isp++] = component.y;
										continue;
									}
									if (opcode == Cs2Opcodes.getWidth) {
										intStack[isp++] = component.width;
										continue;
									}
									if (opcode == Cs2Opcodes.getHeight) {
										intStack[isp++] = component.height;
										continue;
									}
									if (opcode == Cs2Opcodes.getHidden) {
										intStack[isp++] = component.hidden ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getLayer) {
										intStack[isp++] = component.overlayer;
										continue;
									}
								} else if (opcode < 1700) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
									if (opcode == Cs2Opcodes.getScrollX) {
										intStack[isp++] = component.scrollX;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollY) {
										intStack[isp++] = component.scrollY;
										continue;
									}
									if (opcode == Cs2Opcodes.getText) {
										stringStack[ssp++] = component.text;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollMaxH) {
										intStack[isp++] = component.scrollMaxH;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollMaxV) {
										intStack[isp++] = component.scrollMaxV;
										continue;
									}
									if (opcode == Cs2Opcodes.get3DDistance) {
										intStack[isp++] = component.modelZoom;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateX) {
										intStack[isp++] = component.modelXAngle;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateZ) {
										intStack[isp++] = component.modelYOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateY) {
										intStack[isp++] = component.modelYAngle;
										continue;
									}
									if (opcode == Cs2Opcodes.getAlpha) {
										intStack[isp++] = component.alpha;
										continue;
									}
									if (opcode == Cs2Opcodes.getModelXOffset) {
										intStack[isp++] = component.modelXOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getModelYOffset) {
										intStack[isp++] = component.modelZOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getSpriteId) {
										intStack[isp++] = component.spriteId;
										continue;
									}
								} else if (opcode < 1800) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
									if (opcode == Cs2Opcodes.getItemId) {
										intStack[isp++] = component.objId;
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmt) {
										if (component.objId == -1) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = component.objCount;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.getChildId) {
										intStack[isp++] = component.createdComponentId;
										continue;
									}
								} else if (opcode < 1900) {
									component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
									if (opcode == Cs2Opcodes.getTargetMask) {
										intStack[isp++] = InterfaceList.getServerActiveProperties(component).getTargetMask();
										continue;
									}
									if (opcode == Cs2Opcodes.getOption) {
										isp--;
										int3 = intStack[isp];
										int3--;
										if (component.ops != null && int3 < component.ops.length && component.ops[int3] != null) {
											stringStack[ssp++] = component.ops[int3];
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == Cs2Opcodes.getOptionBase) {
										if (component.optionBase == null) {
											stringStack[ssp++] = EMPTY_STRING;
										} else {
											stringStack[ssp++] = component.optionBase;
										}
										continue;
									}
								} else if (opcode < 2600) {
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
									if (opcode == Cs2Opcodes.getX2) {
										intStack[isp++] = component.x;
										continue;
									}
									if (opcode == Cs2Opcodes.getY2) {
										intStack[isp++] = component.y;
										continue;
									}
									if (opcode == Cs2Opcodes.getWidth2) {
										intStack[isp++] = component.width;
										continue;
									}
									if (opcode == Cs2Opcodes.getHeight2) {
										intStack[isp++] = component.height;
										continue;
									}
									if (opcode == Cs2Opcodes.isHidden2) {
										intStack[isp++] = component.hidden ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getLayer2) {
										intStack[isp++] = component.overlayer;
										continue;
									}
								} else if (opcode < 2700) {
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
									if (opcode == Cs2Opcodes.getScrollX2) {
										intStack[isp++] = component.scrollX;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollY2) {
										intStack[isp++] = component.scrollY;
										continue;
									}
									if (opcode == Cs2Opcodes.getText2) {
										stringStack[ssp++] = component.text;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollMaxH2) {
										intStack[isp++] = component.scrollMaxH;
										continue;
									}
									if (opcode == Cs2Opcodes.getScrollMaxV2) {
										intStack[isp++] = component.scrollMaxV;
										continue;
									}
									if (opcode == Cs2Opcodes.get3DDistance2) {
										intStack[isp++] = component.modelZoom;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateX2) {
										intStack[isp++] = component.modelXAngle;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateZ2) {
										intStack[isp++] = component.modelYOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getRotateY2) {
										intStack[isp++] = component.modelYAngle;
										continue;
									}
									if (opcode == Cs2Opcodes.getAlpha2) {
										intStack[isp++] = component.alpha;
										continue;
									}
									if (opcode == Cs2Opcodes.getModelXOffset2) {
										intStack[isp++] = component.modelXOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getModelYOffset2) {
										intStack[isp++] = component.modelZOffset;
										continue;
									}
									if (opcode == Cs2Opcodes.getSpriteId2) {
										intStack[isp++] = component.spriteId;
										continue;
									}
								} else if (opcode < 2800) {
									if (opcode == Cs2Opcodes.getItemId2) {
										isp--;
										component = InterfaceList.getComponent(intStack[isp]);
										intStack[isp++] = component.objId;
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmt2) {
										isp--;
										component = InterfaceList.getComponent(intStack[isp]);
										if (component.objId == -1) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = component.objCount;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.hasChild) {
										isp--;
										int1 = intStack[isp];
										@Pc(12566) ComponentPointer componentPointer = (ComponentPointer) InterfaceList.openInterfaces.get(int1);
										if (componentPointer == null) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = 1;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.nextChild) {
										isp--;
										component = InterfaceList.getComponent(intStack[isp]);
										if (component.createdComponents == null) {
											intStack[isp++] = 0;
											continue;
										}
										int3 = component.createdComponents.length;
										for (int2 = 0; int2 < component.createdComponents.length; int2++) {
											if (component.createdComponents[int2] == null) {
												int3 = int2;
												break;
											}
										}
										intStack[isp++] = int3;
										continue;
									}
									if (opcode == Cs2Opcodes.hasChildModal || opcode == Cs2Opcodes.hasChildOverlay) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										@Pc(12663) ComponentPointer componentPointer2 = (ComponentPointer) InterfaceList.openInterfaces.get(int1);
										if (componentPointer2 != null && componentPointer2.interfaceId == int3) {
											intStack[isp++] = 1;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
								} else if (opcode < 2900) {
									isp--;
									component = InterfaceList.getComponent(intStack[isp]);
									if (opcode == Cs2Opcodes.getTargetMask2) {
										intStack[isp++] = InterfaceList.getServerActiveProperties(component).getTargetMask();
										continue;
									}
									if (opcode == Cs2Opcodes.getOption2) {
										isp--;
										int3 = intStack[isp];
										int3--;
										if (component.ops != null && component.ops.length > int3 && component.ops[int3] != null) {
											stringStack[ssp++] = component.ops[int3];
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == Cs2Opcodes.getOptionBase2) {
										if (component.optionBase == null) {
											stringStack[ssp++] = EMPTY_STRING;
										} else {
											stringStack[ssp++] = component.optionBase;
										}
										continue;
									}
								} else if (opcode < 3200) {
									if (opcode == Cs2Opcodes.msg) {
										ssp--;
										string = stringStack[ssp];
										Chat.add(EMPTY_STRING, 0, string);
										continue;
									}
									if (opcode == Cs2Opcodes.animateSelf) {
										isp -= 2;
										Player.animate(intStack[isp + 1], intStack[isp], PlayerList.self);
										continue;
									}
									if (opcode == Cs2Opcodes.sendCloseWidgetPacket) {
										ClientProt.closeWidget();
										continue;
									}
									if (opcode == Cs2Opcodes.sendIntegerInput) {
										ssp--;
										string = stringStack[ssp];
										int3 = 0;
										if (string.isInt()) {
											int3 = string.parseInt();
										}
										Protocol.outboundBuffer.p1isaac(ClientProt.RESUME_P_COUNTDIALOG);
										Protocol.outboundBuffer.p4(int3);
										continue;
									}
									if (opcode == Cs2Opcodes.sendNameInput) {
										ssp--;
										string = stringStack[ssp];
										Protocol.outboundBuffer.p1isaac(ClientProt.RESUME_P_NAMEDIALOG);
										Protocol.outboundBuffer.p8(string.encode37());
										continue;
									}
									if (opcode == Cs2Opcodes.sendStringInput) {
										ssp--;
										string = stringStack[ssp];
										Protocol.outboundBuffer.p1isaac(ClientProt.RESUME_P_STRINGDIALOG);
										Protocol.outboundBuffer.p1(string.length() + 1);
										Protocol.outboundBuffer.pjstr(string);
										continue;
									}
									if (opcode == Cs2Opcodes.clickPlayerOption) {
										isp--;
										int1 = intStack[isp];
										ssp--;
										str1 = stringStack[ssp];
										ClientProt.clickPlayerOption(int1, str1);
										continue;
									}
									if (opcode == 3108) {
										isp -= 3;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										int2 = intStack[isp + 2];
										parentComponent = InterfaceList.getComponent(int2);
										Cs1ScriptRunner.startComponentDrag(int3, int1, parentComponent);
										continue;
									}
									if (opcode == 3109) {
										isp -= 2;
										int1 = intStack[isp];
										targetComponent = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
										int3 = intStack[isp + 1];
										Cs1ScriptRunner.startComponentDrag(int3, int1, targetComponent);
										continue;
									}
									if (opcode == 3110) {
										isp--;
										int1 = intStack[isp];
										Protocol.outboundBuffer.p1isaac(111);
										Protocol.outboundBuffer.p2(int1);
										continue;
									}
								} else if (opcode < 3300) {
									if (opcode == Cs2Opcodes.playSoundEffect) {
										isp -= 3;
										SoundPlayer.play(intStack[isp + 1], intStack[isp], intStack[isp + 2]);
										continue;
									}
									if (opcode == Cs2Opcodes.playMusic) {
										isp--;
										MusicPlayer.playSong(intStack[isp]);
										continue;
									}
									if (opcode == Cs2Opcodes.playMusicEffect) {
										isp -= 2;
										MusicPlayer.playJingle(intStack[isp + 1], intStack[isp]);
										continue;
									}
								} else if (opcode < 3400) {
									if (opcode == Cs2Opcodes.getClientCycle) {
										intStack[isp++] = client.loop;
										continue;
									}
									if (opcode == Cs2Opcodes.getItemIdInSlot) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = Inv.getItemType(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmtInSlot) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = Inv.getItemCount(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmtInContainer) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = Inv.getSlotTotal(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getItemContainerLength) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = InvTypeList.get(int1).size;
										continue;
									}
									if (opcode == Cs2Opcodes.getSkillCurrentLvl) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = PlayerSkillXpTable.boostedLevels[int1];
										continue;
									}
									if (opcode == Cs2Opcodes.getSkillActualLvl) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = PlayerSkillXpTable.baseLevels[int1];
										continue;
									}
									if (opcode == Cs2Opcodes.getSkillXp) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = PlayerSkillXpTable.experience[int1];
										continue;
									}
									if (opcode == Cs2Opcodes.getMyLocation) {
										int1 = Player.plane;
										int3 = Camera.originX + (PlayerList.self.xFine >> 7);
										int2 = (PlayerList.self.yFine >> 7) + Camera.originY;
										intStack[isp++] = (int1 << 28) - (-(int3 << 14) - int2);
										continue;
									}
									if (opcode == Cs2Opcodes.x) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = int1 >> 14 & 0x3FFF;
										continue;
									}
									if (opcode == Cs2Opcodes.z) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = int1 >> 28;
										continue;
									}
									if (opcode == Cs2Opcodes.y) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = int1 & 0x3FFF;
										continue;
									}
									if (opcode == Cs2Opcodes.isMember) {
										intStack[isp++] = LoginManager.mapMembers ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getItemIdInInpsectingSlot) {
										isp -= 2;
										int1 = intStack[isp] + 32768;
										int3 = intStack[isp + 1];
										intStack[isp++] = Inv.getItemType(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmtInInspectingSlot) {
										isp -= 2;
										int1 = intStack[isp] + 32768;
										int3 = intStack[isp + 1];
										intStack[isp++] = Inv.getItemCount(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getItemAmtInInspectingContainer) {
										isp -= 2;
										int1 = intStack[isp] + 32768;
										int3 = intStack[isp + 1];
										intStack[isp++] = Inv.getSlotTotal(int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getClientRights) {
										if (LoginManager.staffModLevel < 2) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = LoginManager.staffModLevel;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.getSystemUpdateTimer) {
										intStack[isp++] = Player.rebootTimer;
										continue;
									}
									if (opcode == Cs2Opcodes.getWorldId) {
										intStack[isp++] = Player.worldId;
										continue;
									}
									if (opcode == Cs2Opcodes.getRunEnergy) {
										intStack[isp++] = Player.runEnergy;
										continue;
									}
									if (opcode == Cs2Opcodes.getPlayerWeight) {
										intStack[isp++] = Player.weight;
										continue;
									}
									if (opcode == Cs2Opcodes.hasMoreThen5Blackmarks) {
										if (LoginManager.blackmarks >= 5 && LoginManager.blackmarks <= 9) {
											intStack[isp++] = 1;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getBlackmarks) {
										if (LoginManager.blackmarks >= 5 && LoginManager.blackmarks <= 9) {
											intStack[isp++] = LoginManager.blackmarks;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == Cs2Opcodes.isSiteSettingsMembers) {
										intStack[isp++] = LoginManager.playerMember ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getMyCombat) {
										intStack[isp++] = PlayerList.self.combatLevel;
										continue;
									}
									if (opcode == Cs2Opcodes.IsFemale) {
										intStack[isp++] = PlayerList.self.appearance.gender ? 1 : 0;
										continue;
									}
									if (opcode == 3328) {
										intStack[isp++] = LoginManager.playerUnderage && !LoginManager.parentalChatConsent ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.mapQuickchat) {
										intStack[isp++] = LoginManager.mapQuickChat ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getContainerFreeSlots) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = Inv.getFreeSpace(int1);
										continue;
									}
									if (opcode == Cs2Opcodes.getContainerIntAttrCountIgnoreStacks) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = Inv.getTotalParam(false, int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getContainerIntAttrCount) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = Inv.getTotalParam(true, int1, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getLoginTypeRelatedMethod3333) {
										intStack[isp++] = LoginManager.loginType;
										continue;
									}
									if (opcode == Cs2Opcodes.getLanguage) {
										intStack[isp++] = client.language;
										continue;
									}
									if (opcode == Cs2Opcodes.moveCoord) {
										isp -= 4;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										int1 += int3 << 14;
										argIndex = intStack[isp + 3];
										int2 = intStack[isp + 2];
										int1 += int2 << 28;
										int1 += argIndex;
										intStack[isp++] = int1;
										continue;
									}
									if (opcode == Cs2Opcodes.getAffid) {
										intStack[isp++] = client.affiliate;
										continue;
									}
								} else if (opcode < 3500) {
									@Pc(3422) EnumType enumType;
									if (opcode == Cs2Opcodes.datamap) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										enumType = EnumTypeList.get(int1);
										if (enumType.valueType == 115) {
										}
										stringStack[ssp++] = enumType.getString(int3);
										continue;
									}
									if (opcode == Cs2Opcodes.datamap2) {
										isp -= 4;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										argIndex = intStack[isp + 3];
										int2 = intStack[isp + 2];
										@Pc(3469) EnumType enumType2 = EnumTypeList.get(int2);
										if (enumType2.keyType == int1 && enumType2.valueType == int3) {
											if (int3 == 115) {
												stringStack[ssp++] = enumType2.getString(argIndex);
											} else {
												intStack[isp++] = enumType2.getInt(argIndex);
											}
											continue;
										}
										throw new RuntimeException("C3408-1");
									}
									if (opcode == Cs2Opcodes.datamapContainsValue) {
										isp -= 3;
										int3 = intStack[isp + 1];
										int2 = intStack[isp + 2];
										int1 = intStack[isp];
										if (int3 == -1) {
											throw new RuntimeException("C3409-2");
										}
										@Pc(3549) EnumType enumType3 = EnumTypeList.get(int3);
										if (enumType3.valueType != int1) {
											throw new RuntimeException("C3409-1");
										}
										intStack[isp++] = enumType3.containsValue(int2) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.datamapContainsValue2) {
										isp--;
										int1 = intStack[isp];
										ssp--;
										str1 = stringStack[ssp];
										if (int1 == -1) {
											throw new RuntimeException("C3410-2");
										}
										enumType = EnumTypeList.get(int1);
										if (enumType.valueType != 115) {
											throw new RuntimeException("C3410-1");
										}
										intStack[isp++] = enumType.containsValue(str1) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.datamapSize) {
										isp--;
										int1 = intStack[isp];
										@Pc(3645) EnumType enumType4 = EnumTypeList.get(int1);
										intStack[isp++] = enumType4.table.size();
										continue;
									}
								} else if (opcode < 3700) {
									if (opcode == Cs2Opcodes.getFriendCount) {
										if (FriendsList.state == 0) {
											intStack[isp++] = -2;
										} else if (FriendsList.state == 1) {
											intStack[isp++] = -1;
										} else {
											intStack[isp++] = FriendsList.size;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.getFriendName) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state == 2 && int1 < FriendsList.size) {
											stringStack[ssp++] = FriendsList.usernames[int1];
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == Cs2Opcodes.getFriendWorld) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state == 2 && FriendsList.size > int1) {
											intStack[isp++] = FriendsList.worlds[int1];
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getFriendRank) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state == 2 && FriendsList.size > int1) {
											intStack[isp++] = FriendsList.ranks[int1];
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == 3604) {
										isp--;
										int3 = intStack[isp];
										ssp--;
										string = stringStack[ssp];
										FriendsList.setRank(string, int3);
										continue;
									}
									if (opcode == 3605) {
										ssp--;
										string = stringStack[ssp];
										FriendsList.add(string.encode37());
										continue;
									}
									if (opcode == 3606) {
										ssp--;
										string = stringStack[ssp];
										FriendsList.remove(string.encode37());
										continue;
									}
									if (opcode == 3607) {
										ssp--;
										string = stringStack[ssp];
										IgnoreList.add(string.encode37());
										continue;
									}
									if (opcode == 3608) {
										ssp--;
										string = stringStack[ssp];
										IgnoreList.remove(string.encode37());
										continue;
									}
									if (opcode == Cs2Opcodes.isFriend) {
										ssp--;
										string = stringStack[ssp];
										if (string.startsWith(IMG0) || string.startsWith(IMG1)) {
											string = string.substring(7);
										}
										intStack[isp++] = FriendsList.contains(string) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getFriendWorldName) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state == 2 && FriendsList.size > int1) {
											stringStack[ssp++] = FriendsList.worldNames[int1];
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == Cs2Opcodes.clanChatDisplayName) {
										if (ClanChat.name == null) {
											stringStack[ssp++] = EMPTY_STRING;
										} else {
											stringStack[ssp++] = ClanChat.name.toTitleCase();
										}
										continue;
									}
									if (opcode == Cs2Opcodes.clanGetChatCount) {
										if (ClanChat.name == null) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = ClanChat.size;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.clanChatUsername) {
										isp--;
										int1 = intStack[isp];
										if (ClanChat.name != null && ClanChat.size > int1) {
											stringStack[ssp++] = ClanChat.members[int1].username.toTitleCase();
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == Cs2Opcodes.clanChatUserWorld) {
										isp--;
										int1 = intStack[isp];
										if (ClanChat.name != null && int1 < ClanChat.size) {
											intStack[isp++] = ClanChat.members[int1].world;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == Cs2Opcodes.clanChatUserRank) {
										isp--;
										int1 = intStack[isp];
										if (ClanChat.name != null && ClanChat.size > int1) {
											intStack[isp++] = ClanChat.members[int1].rank;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == 3616) {
										intStack[isp++] = ClanChat.minKick;
										continue;
									}
									if (opcode == 3617) {
										ssp--;
										string = stringStack[ssp];
										ClanChat.kick(string);
										continue;
									}
									if (opcode == 3618) {
										intStack[isp++] = ClanChat.rank;
										continue;
									}
									if (opcode == 3619) {
										ssp--;
										string = stringStack[ssp];
										ClanChat.join(string.encode37());
										continue;
									}
									if (opcode == 3620) {
										ClanChat.leave();
										continue;
									}
									if (opcode == Cs2Opcodes.getIgnoreCount) {
										if (FriendsList.state == 0) {
											intStack[isp++] = -1;
										} else {
											intStack[isp++] = IgnoreList.size;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.getIgnoreName) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state != 0 && IgnoreList.size > int1) {
											stringStack[ssp++] = Base37.decode37(IgnoreList.encodedUsernames[int1]).toTitleCase();
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == 3623) {
										ssp--;
										string = stringStack[ssp];
										if (string.startsWith(IMG0) || string.startsWith(IMG1)) {
											string = string.substring(7);
										}
										intStack[isp++] = IgnoreList.contains(string) ? 1 : 0;
										continue;
									}
									if (opcode == 3624) {
										isp--;
										int1 = intStack[isp];
										if (ClanChat.members != null && ClanChat.size > int1 && ClanChat.members[int1].username.equalsIgnoreCase(PlayerList.self.username)) {
											intStack[isp++] = 1;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == 3625) {
										if (ClanChat.owner == null) {
											stringStack[ssp++] = EMPTY_STRING;
										} else {
											stringStack[ssp++] = ClanChat.owner.toTitleCase();
										}
										continue;
									}
									if (opcode == 3626) {
										isp--;
										int1 = intStack[isp];
										if (ClanChat.name != null && ClanChat.size > int1) {
											stringStack[ssp++] = ClanChat.members[int1].worldName;
											continue;
										}
										stringStack[ssp++] = EMPTY_STRING;
										continue;
									}
									if (opcode == 3627) {
										isp--;
										int1 = intStack[isp];
										if (FriendsList.state == 2 && int1 >= 0 && int1 < FriendsList.size) {
											intStack[isp++] = FriendsList.sameGame[int1] ? 1 : 0;
											continue;
										}
										intStack[isp++] = 0;
										continue;
									}
									if (opcode == 3628) {
										ssp--;
										string = stringStack[ssp];
										if (string.startsWith(IMG0) || string.startsWith(IMG1)) {
											string = string.substring(7);
										}
										intStack[isp++] = FriendsList.indexOf(string);
										continue;
									}
									if (opcode == 3629) {
										intStack[isp++] = client.country;
										continue;
									}
								} else if (opcode < 4000) {
									if (opcode == Cs2Opcodes.getGEIsSelling) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].getType();
										continue;
									}
									if (opcode == Cs2Opcodes.getGEItem) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].item;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEItemPrice) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].price;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEItemAmt) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].count;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEItemAmtTransfered) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].completedCount;
										continue;
									}
									if (opcode == Cs2Opcodes.getGECashTransfered) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = StockMarketManager.offers[int1].completedGold;
										continue;
									}
									if (opcode == Cs2Opcodes.getGENotStarted) {
										isp--;
										int1 = intStack[isp];
										int3 = StockMarketManager.offers[int1].getStatus();
										intStack[isp++] = int3 == 0 ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEIsStatus2) {
										isp--;
										int1 = intStack[isp];
										int3 = StockMarketManager.offers[int1].getStatus();
										intStack[isp++] = int3 == 2 ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEIsDone) {
										isp--;
										int1 = intStack[isp];
										int3 = StockMarketManager.offers[int1].getStatus();
										intStack[isp++] = int3 == 5 ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.getGEIsStatus1) {
										isp--;
										int1 = intStack[isp];
										int3 = StockMarketManager.offers[int1].getStatus();
										intStack[isp++] = int3 == 1 ? 1 : 0;
										continue;
									}
								} else if (opcode < 4100) {
									if (opcode == Cs2Opcodes.add) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int3 + int1;
										continue;
									}
									if (opcode == Cs2Opcodes.subtract) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int1 - int3;
										continue;
									}
									if (opcode == Cs2Opcodes.multiply) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int3 * int1;
										continue;
									}
									if (opcode == Cs2Opcodes.divide) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int1 / int3;
										continue;
									}
									if (opcode == Cs2Opcodes.rndExcl) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = (int) ((double) int1 * Math.random());
										continue;
									}
									if (opcode == Cs2Opcodes.rnd) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = (int) (Math.random() * (double) (int1 + 1));
										continue;
									}
									if (opcode == 4006) {
										isp -= 5;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										argIndex = intStack[isp + 3];
										int2 = intStack[isp + 2];
										c = intStack[isp + 4];
										intStack[isp++] = (int3 - int1) * (c + -int2) / (argIndex - int2) + int1;
										continue;
									}
									@Pc(4899) long longVal2;
									@Pc(4892) long longVal1;
									if (opcode == Cs2Opcodes.addPercent) {
										isp -= 2;
										longVal1 = intStack[isp];
										longVal2 = intStack[isp + 1];
										intStack[isp++] = (int) (longVal1 * longVal2 / 100L + longVal1);
										continue;
									}
									if (opcode == Cs2Opcodes.flagBit) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int1 | 0x1 << int3;
										continue;
									}
									if (opcode == Cs2Opcodes.unflagBit) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = -(0x1 << int3) - 1 & int1;
										continue;
									}
									if (opcode == Cs2Opcodes.isBitFlagged) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = (int1 & 0x1 << int3) == 0 ? 0 : 1;
										continue;
									}
									if (opcode == Cs2Opcodes.mod) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = int1 % int3;
										continue;
									}
									if (opcode == Cs2Opcodes.pow) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										if (int1 == 0) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = (int) Math.pow(int1, int3);
										}
										continue;
									}
									if (opcode == Cs2Opcodes.root) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										if (int1 == 0) {
											intStack[isp++] = 0;
										} else if (int3 == 0) {
											intStack[isp++] = Integer.MAX_VALUE;
										} else {
											intStack[isp++] = (int) Math.pow(int1, 1.0D / (double) int3);
										}
										continue;
									}
									if (opcode == Cs2Opcodes.bitAnd) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = int3 & int1;
										continue;
									}
									if (opcode == Cs2Opcodes.bitOr) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int1 | int3;
										continue;
									}
									if (opcode == Cs2Opcodes.min) {
										isp -= 2;
										int1 = intStack[isp];
										int3 = intStack[isp + 1];
										intStack[isp++] = int1 < int3 ? int1 : int3;
										continue;
									}
									if (opcode == Cs2Opcodes.max) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										intStack[isp++] = int3 >= int1 ? int3 : int1;
										continue;
									}
									if (opcode == Cs2Opcodes.multiplyDivide) {
										isp -= 3;
										longVal1 = intStack[isp];
										longVal2 = intStack[isp + 1];
										@Pc(5251) long longVal3 = intStack[isp + 2];
										intStack[isp++] = (int) (longVal1 * longVal3 / longVal2);
										continue;
									}
								} else if (opcode >= 4200) {
									@Pc(5294) ParamType paramType;
									if (opcode < 4300) {
										if (opcode == Cs2Opcodes.getItemName) {
											isp--;
											int1 = intStack[isp];
											stringStack[ssp++] = ObjTypeList.get(int1).name;
											continue;
										}
										@Pc(11269) ObjType itemType;
										if (opcode == Cs2Opcodes.getItemGroundOption) {
											isp -= 2;
											int1 = intStack[isp];
											int3 = intStack[isp + 1];
											itemType = ObjTypeList.get(int1);
											if (int3 >= 1 && int3 <= 5 && itemType.ops[int3 - 1] != null) {
												stringStack[ssp++] = itemType.ops[int3 - 1];
												continue;
											}
											stringStack[ssp++] = EMPTY_STRING;
											continue;
										}
										if (opcode == Cs2Opcodes.getItemOption) {
											isp -= 2;
											int1 = intStack[isp];
											int3 = intStack[isp + 1];
											itemType = ObjTypeList.get(int1);
											if (int3 >= 1 && int3 <= 5 && itemType.iops[int3 - 1] != null) {
												stringStack[ssp++] = itemType.iops[int3 - 1];
												continue;
											}
											stringStack[ssp++] = EMPTY_STRING;
											continue;
										}
										if (opcode == Cs2Opcodes.getItemValue) {
											isp--;
											int1 = intStack[isp];
											intStack[isp++] = ObjTypeList.get(int1).cost;
											continue;
										}
										if (opcode == Cs2Opcodes.itemIsStackable) {
											isp--;
											int1 = intStack[isp];
											intStack[isp++] = ObjTypeList.get(int1).stackable == 1 ? 1 : 0;
											continue;
										}
										@Pc(11417) ObjType certType;
										if (opcode == Cs2Opcodes.getNotedItem) {
											isp--;
											int1 = intStack[isp];
											certType = ObjTypeList.get(int1);
											if (certType.certtemplate == -1 && certType.certlink >= 0) {
												intStack[isp++] = certType.certlink;
												continue;
											}
											intStack[isp++] = int1;
											continue;
										}
										if (opcode == Cs2Opcodes.getRealItem) {
											isp--;
											int1 = intStack[isp];
											certType = ObjTypeList.get(int1);
											if (certType.certtemplate >= 0 && certType.certlink >= 0) {
												intStack[isp++] = certType.certlink;
												continue;
											}
											intStack[isp++] = int1;
											continue;
										}
										if (opcode == Cs2Opcodes.itemIsMembers) {
											isp--;
											int1 = intStack[isp];
											intStack[isp++] = ObjTypeList.get(int1).members ? 1 : 0;
											continue;
										}
										if (opcode == Cs2Opcodes.itemAttribute) {
											isp -= 2;
											int1 = intStack[isp];
											int3 = intStack[isp + 1];
											paramType = ParamTypeList.get(int3);
											if (paramType.isString()) {
												stringStack[ssp++] = ObjTypeList.get(int1).getParam(paramType.defaultString, int3);
											} else {
												intStack[isp++] = ObjTypeList.get(int1).getParam(paramType.defaultInt, int3);
											}
											continue;
										}
										if (opcode == Cs2Opcodes.searchItem) {
											ssp--;
											string = stringStack[ssp];
											isp--;
											int3 = intStack[isp];
											Find.search(int3 == 1, string);
											intStack[isp++] = Find.index;
											continue;
										}
										if (opcode == Cs2Opcodes.nextSearchResult) {
											if (Find.results != null && Find.size < Find.index) {
												intStack[isp++] = Find.results[Find.size++] & 0xFFFF;
												continue;
											}
											intStack[isp++] = -1;
											continue;
										}
										if (opcode == 4212) {
											Find.size = 0;
											continue;
										}
									} else if (opcode < 4400) {
										if (opcode == Cs2Opcodes.getNpcAttribute) {
											isp -= 2;
											int1 = intStack[isp];
											int3 = intStack[isp + 1];
											paramType = ParamTypeList.get(int3);
											if (paramType.isString()) {
												stringStack[ssp++] = NpcTypeList.get(int1).getParam(int3, paramType.defaultString);
											} else {
												intStack[isp++] = NpcTypeList.get(int1).getParam(int3, paramType.defaultInt);
											}
											continue;
										}
									} else if (opcode >= 4500) {
										if (opcode >= 4600) {
											if (opcode < 5100) {
												if (opcode == Cs2Opcodes.getPublicChatSetting) {
													intStack[isp++] = Chat.publicFilter;
													continue;
												}
												if (opcode == Cs2Opcodes.setChatSettings) {
													isp -= 3;
													Chat.publicFilter = intStack[isp];
													Chat.privateFilter = intStack[isp + 1];
													Chat.tradeFilter = intStack[isp + 2];
													Protocol.outboundBuffer.p1isaac(ClientProt.SET_CHATFILTERSETTINGS);
													Protocol.outboundBuffer.p1(Chat.publicFilter);
													Protocol.outboundBuffer.p1(Chat.privateFilter);
													Protocol.outboundBuffer.p1(Chat.tradeFilter);
													continue;
												}
												if (opcode == 5002) {
													ssp--;
													string = stringStack[ssp];
													isp -= 2;
													int3 = intStack[isp];
													int2 = intStack[isp + 1];
													Protocol.outboundBuffer.p1isaac(ClientProt.BUG_REPORT);
													Protocol.outboundBuffer.p8(string.encode37());
													Protocol.outboundBuffer.p1(int3 - 1);
													Protocol.outboundBuffer.p1(int2);
													continue;
												}
												if (opcode == 5003) {
													str1 = null;
													isp--;
													int1 = intStack[isp];
													if (int1 < 100) {
														str1 = Chat.messages[int1];
													}
													if (str1 == null) {
														str1 = EMPTY_STRING;
													}
													stringStack[ssp++] = str1;
													continue;
												}
												if (opcode == 5004) {
													isp--;
													int1 = intStack[isp];
													int3 = -1;
													if (int1 < 100 && Chat.messages[int1] != null) {
														int3 = Chat.types[int1];
													}
													intStack[isp++] = int3;
													continue;
												}
												if (opcode == Cs2Opcodes.getPrivateChatSetting) {
													intStack[isp++] = Chat.privateFilter;
													continue;
												}
												if (opcode == 5008) {
													ssp--;
													string = stringStack[ssp];
													if (!string.startsWith(DOUBLE_COLON)) {
														if (LoginManager.staffModLevel == 0 && (LoginManager.playerUnderage && !LoginManager.parentalChatConsent || LoginManager.mapQuickChat)) {
															continue;
														}
														str1 = string.toLowerCase();
														@Pc(5555) byte chatColor = 0;
														if (str1.startsWith(LocalizedText.STABLE_CHATCOL0)) {
															chatColor = 0;
															string = string.substring(LocalizedText.STABLE_CHATCOL0.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL1)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL1.length());
															chatColor = 1;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL2)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL2.length());
															chatColor = 2;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL3)) {
															chatColor = 3;
															string = string.substring(LocalizedText.STABLE_CHATCOL3.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL4)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL4.length());
															chatColor = 4;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL5)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL5.length());
															chatColor = 5;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL6)) {
															chatColor = 6;
															string = string.substring(LocalizedText.STABLE_CHATCOL6.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL7)) {
															chatColor = 7;
															string = string.substring(LocalizedText.STABLE_CHATCOL7.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL8)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL8.length());
															chatColor = 8;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL9)) {
															chatColor = 9;
															string = string.substring(LocalizedText.STABLE_CHATCOL9.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL10)) {
															chatColor = 10;
															string = string.substring(LocalizedText.STABLE_CHATCOL10.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATCOL11)) {
															string = string.substring(LocalizedText.STABLE_CHATCOL11.length());
															chatColor = 11;
														} else if (client.language != 0) {
															if (str1.startsWith(LocalizedText.CHATCOL0)) {
																chatColor = 0;
																string = string.substring(LocalizedText.CHATCOL0.length());
															} else if (str1.startsWith(LocalizedText.CHATCOL1)) {
																string = string.substring(LocalizedText.CHATCOL1.length());
																chatColor = 1;
															} else if (str1.startsWith(LocalizedText.CHATCOL2)) {
																string = string.substring(LocalizedText.CHATCOL2.length());
																chatColor = 2;
															} else if (str1.startsWith(LocalizedText.CHATCOL3)) {
																string = string.substring(LocalizedText.CHATCOL3.length());
																chatColor = 3;
															} else if (str1.startsWith(LocalizedText.CHATCOL4)) {
																string = string.substring(LocalizedText.CHATCOL4.length());
																chatColor = 4;
															} else if (str1.startsWith(LocalizedText.CHATCOL5)) {
																chatColor = 5;
																string = string.substring(LocalizedText.CHATCOL5.length());
															} else if (str1.startsWith(LocalizedText.CHATCOL6)) {
																string = string.substring(LocalizedText.CHATCOL6.length());
																chatColor = 6;
															} else if (str1.startsWith(LocalizedText.CHATCOL7)) {
																chatColor = 7;
																string = string.substring(LocalizedText.CHATCOL7.length());
															} else if (str1.startsWith(LocalizedText.CHATCOL8)) {
																chatColor = 8;
																string = string.substring(LocalizedText.CHATCOL8.length());
															} else if (str1.startsWith(LocalizedText.CHATCOL9)) {
																chatColor = 9;
																string = string.substring(LocalizedText.CHATCOL9.length());
															} else if (str1.startsWith(LocalizedText.CHATCOL10)) {
																string = string.substring(LocalizedText.CHATCOL10.length());
																chatColor = 10;
															} else if (str1.startsWith(LocalizedText.CHATCOL11)) {
																string = string.substring(LocalizedText.CHATCOL11.length());
																chatColor = 11;
															}
														}
														@Pc(5943) byte chatEffect = 0;
														str1 = string.toLowerCase();
														if (str1.startsWith(LocalizedText.STABLE_CHATEFFECT1)) {
															string = string.substring(LocalizedText.STABLE_CHATEFFECT1.length());
															chatEffect = 1;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATEFFECT2)) {
															chatEffect = 2;
															string = string.substring(LocalizedText.STABLE_CHATEFFECT2.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATEFFECT3)) {
															string = string.substring(LocalizedText.STABLE_CHATEFFECT3.length());
															chatEffect = 3;
														} else if (str1.startsWith(LocalizedText.STABLE_CHATEFFECT4)) {
															chatEffect = 4;
															string = string.substring(LocalizedText.STABLE_CHATEFFECT4.length());
														} else if (str1.startsWith(LocalizedText.STABLE_CHATEFFECTC5)) {
															chatEffect = 5;
															string = string.substring(LocalizedText.STABLE_CHATEFFECTC5.length());
														} else if (client.language != 0) {
															if (str1.startsWith(LocalizedText.CHATEFFECT1)) {
																string = string.substring(LocalizedText.CHATEFFECT1.length());
																chatEffect = 1;
															} else if (str1.startsWith(LocalizedText.CHATEFFECT2)) {
																chatEffect = 2;
																string = string.substring(LocalizedText.CHATEFFECT2.length());
															} else if (str1.startsWith(LocalizedText.CHATEFFECT3)) {
																chatEffect = 3;
																string = string.substring(LocalizedText.CHATEFFECT3.length());
															} else if (str1.startsWith(LocalizedText.CHATEFFECT4)) {
																chatEffect = 4;
																string = string.substring(LocalizedText.CHATEFFECT4.length());
															} else if (str1.startsWith(LocalizedText.CHATEFFECT5)) {
																string = string.substring(LocalizedText.CHATEFFECT5.length());
																chatEffect = 5;
															}
														}
														Protocol.outboundBuffer.p1isaac(237);
														Protocol.outboundBuffer.p1(0);
														c = Protocol.outboundBuffer.offset;
														Protocol.outboundBuffer.p1(chatColor);
														Protocol.outboundBuffer.p1(chatEffect);
														WordPack.encode(Protocol.outboundBuffer, string);
														Protocol.outboundBuffer.psize1(Protocol.outboundBuffer.offset - c);
														continue;
													}
													Cheat.execute(string);
													continue;
												}
												if (opcode == 5009) {
													ssp -= 2;
													str1 = stringStack[ssp + 1];
													string = stringStack[ssp];
													if (LoginManager.staffModLevel != 0 || (!LoginManager.playerUnderage || LoginManager.parentalChatConsent) && !LoginManager.mapQuickChat) {
														Protocol.outboundBuffer.p1isaac(201);
														Protocol.outboundBuffer.p1(0);
														int2 = Protocol.outboundBuffer.offset;
														Protocol.outboundBuffer.p8(string.encode37());
														WordPack.encode(Protocol.outboundBuffer, str1);
														Protocol.outboundBuffer.psize1(Protocol.outboundBuffer.offset - int2);
													}
													continue;
												}
												if (opcode == 5010) {
													isp--;
													int1 = intStack[isp];
													str1 = null;
													if (int1 < 100) {
														str1 = Chat.names[int1];
													}
													if (str1 == null) {
														str1 = EMPTY_STRING;
													}
													stringStack[ssp++] = str1;
													continue;
												}
												if (opcode == 5011) {
													isp--;
													int1 = intStack[isp];
													str1 = null;
													if (int1 < 100) {
														str1 = Chat.clans[int1];
													}
													if (str1 == null) {
														str1 = EMPTY_STRING;
													}
													stringStack[ssp++] = str1;
													continue;
												}
												if (opcode == 5012) {
													isp--;
													int1 = intStack[isp];
													int3 = -1;
													if (int1 < 100) {
														int3 = Chat.phraseIds[int1];
													}
													intStack[isp++] = int3;
													continue;
												}
												if (opcode == 5015) {
													if (PlayerList.self == null || PlayerList.self.username == null) {
														string = Player.usernameInput;
													} else {
														string = PlayerList.self.getName();
													}
													stringStack[ssp++] = string;
													continue;
												}
												if (opcode == Cs2Opcodes.getTradeSetting) {
													intStack[isp++] = Chat.tradeFilter;
													continue;
												}
												if (opcode == 5017) {
													intStack[isp++] = Chat.size;
													continue;
												}
												if (opcode == 5050) {
													isp--;
													int1 = intStack[isp];
													stringStack[ssp++] = QuickChatCatTypeList.get(int1).description;
													continue;
												}
												@Pc(6378) QuickChatCatType quickChatCat;
												if (opcode == 5051) {
													isp--;
													int1 = intStack[isp];
													quickChatCat = QuickChatCatTypeList.get(int1);
													if (quickChatCat.subcategories == null) {
														intStack[isp++] = 0;
													} else {
														intStack[isp++] = quickChatCat.subcategories.length;
													}
													continue;
												}
												if (opcode == 5052) {
													isp -= 2;
													int1 = intStack[isp];
													int3 = intStack[isp + 1];
													@Pc(6416) QuickChatCatType quickChatCat2 = QuickChatCatTypeList.get(int1);
													argIndex = quickChatCat2.subcategories[int3];
													intStack[isp++] = argIndex;
													continue;
												}
												if (opcode == 5053) {
													isp--;
													int1 = intStack[isp];
													quickChatCat = QuickChatCatTypeList.get(int1);
													if (quickChatCat.phrases == null) {
														intStack[isp++] = 0;
													} else {
														intStack[isp++] = quickChatCat.phrases.length;
													}
													continue;
												}
												if (opcode == 5054) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													intStack[isp++] = QuickChatCatTypeList.get(int1).phrases[int3];
													continue;
												}
												if (opcode == 5055) {
													isp--;
													int1 = intStack[isp];
													stringStack[ssp++] = QuickChatPhraseTypeList.get(int1).getText();
													continue;
												}
												if (opcode == 5056) {
													isp--;
													int1 = intStack[isp];
													@Pc(6527) QuickChatPhraseType phraseType = QuickChatPhraseTypeList.get(int1);
													if (phraseType.automaticResponses == null) {
														intStack[isp++] = 0;
													} else {
														intStack[isp++] = phraseType.automaticResponses.length;
													}
													continue;
												}
												if (opcode == 5057) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													intStack[isp++] = QuickChatPhraseTypeList.get(int1).automaticResponses[int3];
													continue;
												}
												if (opcode == 5058) {
													activePhrase = new QuickChatPhrase();
													isp--;
													activePhrase.id = intStack[isp];
													activePhrase.type = QuickChatPhraseTypeList.get(activePhrase.id);
													activePhrase.values = new int[activePhrase.type.getDynamicCommandCount()];
													continue;
												}
												if (opcode == 5059) {
													Protocol.outboundBuffer.p1isaac(167);
													Protocol.outboundBuffer.p1(0);
													int1 = Protocol.outboundBuffer.offset;
													Protocol.outboundBuffer.p1(0);
													Protocol.outboundBuffer.p2(activePhrase.id);
													activePhrase.type.encodeMessage(Protocol.outboundBuffer, activePhrase.values);
													Protocol.outboundBuffer.psize1(Protocol.outboundBuffer.offset - int1);
													continue;
												}
												if (opcode == 5060) {
													ssp--;
													string = stringStack[ssp];
													Protocol.outboundBuffer.p1isaac(178);
													Protocol.outboundBuffer.p1(0);
													int3 = Protocol.outboundBuffer.offset;
													Protocol.outboundBuffer.p8(string.encode37());
													Protocol.outboundBuffer.p2(activePhrase.id);
													activePhrase.type.encodeMessage(Protocol.outboundBuffer, activePhrase.values);
													Protocol.outboundBuffer.psize1(Protocol.outboundBuffer.offset - int3);
													continue;
												}
												if (opcode == 5061) {
													Protocol.outboundBuffer.p1isaac(167);
													Protocol.outboundBuffer.p1(0);
													int1 = Protocol.outboundBuffer.offset;
													Protocol.outboundBuffer.p1(1);
													Protocol.outboundBuffer.p2(activePhrase.id);
													activePhrase.type.encodeMessage(Protocol.outboundBuffer, activePhrase.values);
													Protocol.outboundBuffer.psize1(Protocol.outboundBuffer.offset - int1);
													continue;
												}
												if (opcode == 5062) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													intStack[isp++] = QuickChatCatTypeList.get(int1).subcategoryShortcuts[int3];
													continue;
												}
												if (opcode == 5063) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													intStack[isp++] = QuickChatCatTypeList.get(int1).phraseShortcuts[int3];
													continue;
												}
												if (opcode == 5064) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													if (int3 == -1) {
														intStack[isp++] = -1;
													} else {
														intStack[isp++] = QuickChatCatTypeList.get(int1).getSubcategoryByShortcut(int3);
													}
													continue;
												}
												if (opcode == 5065) {
													isp -= 2;
													int1 = intStack[isp];
													int3 = intStack[isp + 1];
													if (int3 == -1) {
														intStack[isp++] = -1;
													} else {
														intStack[isp++] = QuickChatCatTypeList.get(int1).getPhraseByShortcut(int3);
													}
													continue;
												}
												if (opcode == 5066) {
													isp--;
													int1 = intStack[isp];
													intStack[isp++] = QuickChatPhraseTypeList.get(int1).getDynamicCommandCount();
													continue;
												}
												if (opcode == 5067) {
													isp -= 2;
													int3 = intStack[isp + 1];
													int1 = intStack[isp];
													int2 = QuickChatPhraseTypeList.get(int1).getDynamicCommand(int3);
													intStack[isp++] = int2;
													continue;
												}
												if (opcode == 5068) {
													isp -= 2;
													int1 = intStack[isp];
													int3 = intStack[isp + 1];
													activePhrase.values[int1] = int3;
													continue;
												}
												if (opcode == 5069) {
													isp -= 2;
													int1 = intStack[isp];
													int3 = intStack[isp + 1];
													activePhrase.values[int1] = int3;
													continue;
												}
												if (opcode == 5070) {
													isp -= 3;
													int1 = intStack[isp];
													int2 = intStack[isp + 2];
													int3 = intStack[isp + 1];
													@Pc(6996) QuickChatPhraseType type = QuickChatPhraseTypeList.get(int1);
													if (type.getDynamicCommand(int3) != 0) {
														throw new RuntimeException("bad command");
													}
													intStack[isp++] = type.getDynamicCommandParam(int2, int3);
													continue;
												}
												if (opcode == 5071) {
													ssp--;
													string = stringStack[ssp];
													isp--;
													boolValue = intStack[isp] == 1;
													Find.findQuickChatPhrases(boolValue, string);
													intStack[isp++] = Find.index;
													continue;
												}
												if (opcode == 5072) {
													if (Find.results != null && Find.size < Find.index) {
														intStack[isp++] = Find.results[Find.size++] & 0xFFFF;
														continue;
													}
													intStack[isp++] = -1;
													continue;
												}
												if (opcode == 5073) {
													Find.size = 0;
													continue;
												}
											} else if (opcode < 5200) {
												if (opcode == Cs2Opcodes.isHoldingAlt) {
													if (Keyboard.pressedKeys[Keyboard.KEY_ALT]) {
														intStack[isp++] = 1;
													} else {
														intStack[isp++] = 0;
													}
													continue;
												}
												if (opcode == Cs2Opcodes.isHoldingCtrl) {
													if (Keyboard.pressedKeys[Keyboard.KEY_CTRL]) {
														intStack[isp++] = 1;
													} else {
														intStack[isp++] = 0;
													}
													continue;
												}
												if (opcode == Cs2Opcodes.isHoldingShift) {
													if (Keyboard.pressedKeys[Keyboard.KEY_SHIFT]) {
														intStack[isp++] = 1;
													} else {
														intStack[isp++] = 0;
													}
													continue;
												}
											} else {
												@Pc(7566) boolean found;
												if (opcode < 5300) {
													if (opcode == Cs2Opcodes.setWorldmapZoom) {
														isp--;
														WorldMap.setTargetZoom(intStack[isp]);
														continue;
													}
													if (opcode == Cs2Opcodes.getWorldmapZoom) {
														intStack[isp++] = WorldMap.getTargetZoom();
														continue;
													}
													if (opcode == 5202) {
														isp--;
														WorldMap.highlightMapElement(intStack[isp]);
														continue;
													}
													if (opcode == 5203) {
														ssp--;
														WorldMap.panToLabelByPrefix(stringStack[ssp]);
														continue;
													}
													if (opcode == 5204) {
														stringStack[ssp - 1] = WorldMap.getLabelTextByPrefix(stringStack[ssp - 1]);
														continue;
													}
													if (opcode == Cs2Opcodes.loadDungeonmap) {
														ssp--;
														WorldMap.switchMap(stringStack[ssp]);
														continue;
													}
													if (opcode == Cs2Opcodes.getDungeonmap) {
														isp--;
														int1 = intStack[isp];
														@Pc(7264) Map sourceMap = MapList.getContainingSource(int1 >> 14 & 0x3FFF, int1 & 0x3FFF);
														if (sourceMap == null) {
															stringStack[ssp++] = EMPTY_STRING;
														} else {
															stringStack[ssp++] = sourceMap.group;
														}
														continue;
													}
													@Pc(7293) Map map;
													if (opcode == Cs2Opcodes.getDungeonMapName) {
														ssp--;
														map = MapList.get(stringStack[ssp]);
														if (map != null && map.name != null) {
															stringStack[ssp++] = map.name;
															continue;
														}
														stringStack[ssp++] = EMPTY_STRING;
														continue;
													}
													if (opcode == 5208) {
														intStack[isp++] = WorldMap.viewportWidth;
														intStack[isp++] = WorldMap.viewportHeight;
														continue;
													}
													if (opcode == 5209) {
														intStack[isp++] = WorldMap.originX + WorldMap.viewX;
														intStack[isp++] = WorldMap.originY + WorldMap.length - WorldMap.viewY - 1;
														continue;
													}
													if (opcode == Cs2Opcodes.getDungeonmapCenter) {
														map = WorldMap.getCurrentMap();
														if (map == null) {
															intStack[isp++] = 0;
															intStack[isp++] = 0;
														} else {
															intStack[isp++] = map.originX * 64;
															intStack[isp++] = map.originY * 64;
														}
														continue;
													}
													if (opcode == 5211) {
														map = WorldMap.getCurrentMap();
														if (map == null) {
															intStack[isp++] = 0;
															intStack[isp++] = 0;
														} else {
															intStack[isp++] = map.displayMaxZ - map.displayMinX;
															intStack[isp++] = map.displayMinZ - map.displayMaxX;
														}
														continue;
													}
													if (opcode == 5212) {
														int1 = WorldMap.getFirstVisibleLabel();
														int2 = 0;
														if (int1 == -1) {
															str1 = EMPTY_STRING;
														} else {
															str1 = WorldMap.labels.names[int1];
															int2 = WorldMap.labels.getLabelSize(int1);
														}
														str1 = str1.replaceAll(SPACE, BR_TAG);
														stringStack[ssp++] = str1;
														intStack[isp++] = int2;
														continue;
													}
													if (opcode == 5213) {
														int2 = 0;
														int1 = WorldMap.getNextVisibleLabel();
														if (int1 == -1) {
															str1 = EMPTY_STRING;
														} else {
															str1 = WorldMap.labels.names[int1];
															int2 = WorldMap.labels.getLabelSize(int1);
														}
														str1 = str1.replaceAll(SPACE, BR_TAG);
														stringStack[ssp++] = str1;
														intStack[isp++] = int2;
														continue;
													}
													if (opcode == Cs2Opcodes.setPositionInMap) {
														isp--;
														int1 = intStack[isp];
														WorldMap.panToCoords(int1 >> 14 & 0x3FFF, int1 & 0x3FFF);
														continue;
													}
													if (opcode == Cs2Opcodes.dungeonmapContains) {
														isp--;
														int1 = intStack[isp];
														ssp--;
														str1 = stringStack[ssp];
														found = false;
														@Pc(7577) SecondaryLinkedList mapsAtCoord = findMapsAtCoordinate(int1 >> 14 & 0x3FFF, int1 & 0x3FFF);
														for (@Pc(7582) Map mapEntry = (Map) mapsAtCoord.head(); mapEntry != null; mapEntry = (Map) mapsAtCoord.next()) {
															if (mapEntry.group.equalsIgnoreCase(str1)) {
																found = true;
																break;
															}
														}
														if (found) {
															intStack[isp++] = 1;
														} else {
															intStack[isp++] = 0;
														}
														continue;
													}
													if (opcode == 5216) {
														isp--;
														int1 = intStack[isp];
														MapList.toggleMapVisibility(int1);
														continue;
													}
													if (opcode == 5217) {
														isp--;
														int1 = intStack[isp];
														if (MapList.isMapVisible(int1)) {
															intStack[isp++] = 1;
														} else {
															intStack[isp++] = 0;
														}
														continue;
													}
													if (opcode == Cs2Opcodes.getMapDefaultZoom) {
														map = WorldMap.getCurrentMap();
														if (map == null) {
															intStack[isp++] = -1;
														} else {
															intStack[isp++] = map.defaultZoom;
														}
														continue;
													}
													if (opcode == 5219) {
														ssp--;
														WorldMap.panToLabelByExactName(stringStack[ssp]);
														continue;
													}
													if (opcode == 5220) {
														intStack[isp++] = WorldMap.loadPercentage == 100 ? 1 : 0;
														continue;
													}
												} else if (opcode < 5400) {
													if (opcode == 5300) {
														isp -= 2;
														int3 = intStack[isp + 1];
														int1 = intStack[isp];
														DisplayMode.setWindowMode(false, 3, int1, int3);
														intStack[isp++] = GameShell.fullScreenFrame == null ? 0 : 1;
														continue;
													}
													if (opcode == 5301) {
														if (GameShell.fullScreenFrame != null) {
															DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
														}
														continue;
													}
													if (opcode == 5302) {
														@Pc(7780) DisplayMode[] displayModes = DisplayMode.getDisplayModes();
														intStack[isp++] = displayModes.length;
														continue;
													}
													if (opcode == 5303) {
														isp--;
														int1 = intStack[isp];
														@Pc(7800) DisplayMode[] displayModes = DisplayMode.getDisplayModes();
														intStack[isp++] = displayModes[int1].width;
														intStack[isp++] = displayModes[int1].height;
														continue;
													}
													if (opcode == 5305) {
														int3 = Preferences.fullScreenHeight;
														int1 = Preferences.fullScreenWidth;
														int2 = -1;
														@Pc(7833) DisplayMode[] displayModes = DisplayMode.getDisplayModes();
														for (c = 0; c < displayModes.length; c++) {
															@Pc(7843) DisplayMode displayMode = displayModes[c];
															if (int1 == displayMode.width && displayMode.height == int3) {
																int2 = c;
																break;
															}
														}
														intStack[isp++] = int2;
														continue;
													}
													if (opcode == Cs2Opcodes.getDisplayMode) {
														intStack[isp++] = DisplayMode.getWindowMode();
														continue;
													}
													if (opcode == 5307) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0 || int1 > 2) {
															int1 = 0;
														}
														// Gets called on every mode change.
														DisplayMode.setWindowMode(false, int1, -1, -1);
														continue;
													}
													if (opcode == 5308) {
														intStack[isp++] = Preferences.favoriteWorlds;
														continue;
													}
													if (opcode == 5309) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0 || int1 > 2) {
															int1 = 0;
														}
														Preferences.favoriteWorlds = int1;
														Preferences.write(GameShell.signLink);
														continue;
													}
												} else if (opcode < 5500) {
													if (opcode == 5400) {
														ssp -= 2;
														string = stringStack[ssp];
														str1 = stringStack[ssp + 1];
														isp--;
														int2 = intStack[isp];
														Protocol.outboundBuffer.p1isaac(117);
														Protocol.outboundBuffer.p1(Buffer.gjstrlen(string) + Buffer.gjstrlen(str1) + 1);
														Protocol.outboundBuffer.pjstr(string);
														Protocol.outboundBuffer.pjstr(str1);
														Protocol.outboundBuffer.p1(int2);
														continue;
													}
													if (opcode == 5401) {
														isp -= 2;
														client.scriptRecolorPalette[intStack[isp]] = (short) ColorUtils.rgbToHsl(intStack[isp + 1]);
														ObjTypeList.clearModels();
														ObjTypeList.clearSprites();
														NpcTypeList.clearModels();
														NpcTypeList.clearHeadModels();
														forceRedrawAllRectangles();
														continue;
													}
													if (opcode == 5405) {
														isp -= 2;
														int1 = intStack[isp];
														int3 = intStack[isp + 1];
														if (int1 >= 0 && int1 < 2) {
															Camera.cameraPathData[int1] = new int[int3 << 1][4];
														}
														continue;
													}
													if (opcode == 5406) {
														isp -= 7;
														int1 = intStack[isp];
														int3 = intStack[isp + 1] << 1;
														argIndex = intStack[isp + 3];
														int2 = intStack[isp + 2];
														c = intStack[isp + 4];
														@Pc(8108) int speed = intStack[isp + 6];
														j = intStack[isp + 5];
														if (int1 >= 0 && int1 < 2 && Camera.cameraPathData[int1] != null && int3 >= 0 && Camera.cameraPathData[int1].length > int3) {
															Camera.cameraPathData[int1][int3] = new int[]{(int2 >> 14 & 0x3FFF) * 128, argIndex, (int2 & 0x3FFF) * 128, speed};
															Camera.cameraPathData[int1][int3 + 1] = new int[]{(c >> 14 & 0x3FFF) * 128, j, (c & 0x3FFF) * 128};
														}
														continue;
													}
													if (opcode == 5407) {
														isp--;
														int1 = Camera.cameraPathData[intStack[isp]].length >> 1;
														intStack[isp++] = int1;
														continue;
													}
													if (opcode == 5411) {
														if (GameShell.fullScreenFrame != null) {
															DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
														}
														if (GameShell.frame == null) {
															openUrl(buildSettingsUrl(), false);
														} else {
															System.exit(0);
														}
														continue;
													}
													if (opcode == Cs2Opcodes.getLastIp5419) {
														string = EMPTY_STRING;
														if (Player.lastLogAddress != null) {
															string = JagString.formatIp(Player.lastLogAddress.intArg2);
															if (Player.lastLogAddress.result != null) {
																@Pc(8281) byte[] ipBytes = ((String) Player.lastLogAddress.result).getBytes(StandardCharsets.ISO_8859_1);
																string = JagString.decodeString(ipBytes, ipBytes.length, 0);
															}
														}
														stringStack[ssp++] = string;
														continue;
													}
													if (opcode == 5420) {
														intStack[isp++] = SignLink.clientMode == 3 ? 1 : 0;
														continue;
													}
													if (opcode == 5421) {
														if (GameShell.fullScreenFrame != null) {
															DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
														}
														isp--;
														boolValue = intStack[isp] == 1;
														ssp--;
														string = stringStack[ssp];
														@Pc(8356) JagString fullUrl = JagString.concatenate(new JagString[]{buildSettingsUrl(), string});
														if (GameShell.frame != null || boolValue && SignLink.clientMode != 3 && SignLink.osName.startsWith("win") && !client.haveIe6) {
															Protocol.newTab = boolValue;
															url = fullUrl;
															Protocol.openUrlRequest = GameShell.signLink.openUrl(new String(fullUrl.toByteArray(), StandardCharsets.ISO_8859_1));
															continue;
														}
														openUrl(fullUrl, boolValue);
														continue;
													}
													if (opcode == 5422) {
														isp--;
														int2 = intStack[isp];
														ssp -= 2;
														str1 = stringStack[ssp + 1];
														string = stringStack[ssp];
														if (string.length() > 0) {
															if (PlayerList.playerNames == null) {
																PlayerList.playerNames = new JagString[PlayerList.nameSlotCounts[client.game]];
															}
															PlayerList.playerNames[int2] = string;
														}
														if (str1.length() > 0) {
															if (PlayerList.playerNames2 == null) {
																PlayerList.playerNames2 = new JagString[PlayerList.nameSlotCounts[client.game]];
															}
															PlayerList.playerNames2[int2] = str1;
														}
														continue;
													}
													if (opcode == 5423) {
														ssp--;
														stringStack[ssp].print();
														continue;
													}
													if (opcode == 5424) {
														isp -= 11;
														LoginManager.menuFillColor = intStack[isp];
														LoginManager.menuFillTransparency = intStack[isp + 1];
														LoginManager.menuHighlightColor = intStack[isp + 2];
														LoginManager.menuHighlightTransparency = intStack[isp + 3];
														LoginManager.menuHeaderFillSpriteId = intStack[isp + 4];
														LoginManager.menuHeaderEdgeSpriteId = intStack[isp + 5];
														LoginManager.menuBottomFillSpriteId = intStack[isp + 6];
														LoginManager.menuSideFillSpriteId = intStack[isp + 7];
														LoginManager.menuBottomEdgeSpriteId = intStack[isp + 8];
														LoginManager.menuTextColor = intStack[isp + 9];
														LoginManager.menuHighlightTextColor = intStack[isp + 10];
														client.js5Archive8.isFileReady(LoginManager.menuHeaderFillSpriteId);
														client.js5Archive8.isFileReady(LoginManager.menuHeaderEdgeSpriteId);
														client.js5Archive8.isFileReady(LoginManager.menuBottomFillSpriteId);
														client.js5Archive8.isFileReady(LoginManager.menuSideFillSpriteId);
														client.js5Archive8.isFileReady(LoginManager.menuBottomEdgeSpriteId);
														InterfaceList.useStyledMenu = true;
														continue;
													}
													if (opcode == 5425) {
														LoginManager.clearLoginScreenSprites();
														InterfaceList.useStyledMenu = false;
														continue;
													}
													if (opcode == 5426) {
														isp--;
														scriptCursorId = intStack[isp];
														continue;
													}
													if (opcode == 5427) {
														isp -= 2;
														MiniMap.useCursor = intStack[isp];
														MiniMap.examineCursor = intStack[isp + 1];
														continue;
													}
												} else if (opcode < 5600) {
													if (opcode == Cs2Opcodes.cameraMoveTo) {
														isp -= 4;
														int1 = intStack[isp];
														argIndex = intStack[isp + 3];
														int2 = intStack[isp + 2];
														int3 = intStack[isp + 1];
														Camera.setLockedPosition(false, int2, int3, argIndex, (int1 & 0x3FFF) - Camera.originY, (int1 >> 14 & 0x3FFF) - Camera.originX);
														continue;
													}
													if (opcode == Cs2Opcodes.cameraPointAt) {
														isp -= 4;
														int3 = intStack[isp + 1];
														int1 = intStack[isp];
														argIndex = intStack[isp + 3];
														int2 = intStack[isp + 2];
														Camera.setLockedLookAt(int3, (int1 & 0x3FFF) - Camera.originY, int2, (int1 >> 14 & 0x3FFF) - Camera.originX, argIndex);
														continue;
													}
													if (opcode == 5502) {
														isp -= 6;
														int1 = intStack[isp];
														if (int1 >= 2) {
															throw new RuntimeException();
														}
														Camera.movePathId = int1;
														int3 = intStack[isp + 1];
														if (Camera.cameraPathData[Camera.movePathId].length >> 1 <= int3 + 1) {
															throw new RuntimeException();
														}
														Camera.moveSplineIndex = int3;
														Camera.splineProgress = 0;
														Camera.moveSplineSpeedStart = intStack[isp + 2];
														Camera.moveSplineSpeedEnd = intStack[isp + 3];
														int2 = intStack[isp + 4];
														if (int2 >= 2) {
															throw new RuntimeException();
														}
														Camera.lookAtPathId = int2;
														argIndex = intStack[isp + 5];
														if (Camera.cameraPathData[Camera.lookAtPathId].length >> 1 <= argIndex + 1) {
															throw new RuntimeException();
														}
														Camera.lookAtSplineIndex = argIndex;
														Camera.cameraType = 3;
														continue;
													}
													if (opcode == Cs2Opcodes.cameraUnlock) {
														Camera.resetCameraEffects();
														continue;
													}
													if (opcode == 5504) {
														isp -= 2;
														Camera.pitchTarget = intStack[isp];
														Camera.yawTarget = intStack[isp + 1];
														if (Camera.cameraType == 2) {
															Camera.cameraYaw = (int) Camera.yawTarget;
															Camera.cameraPitch = (int) Camera.pitchTarget;
														}
														Camera.clampCameraAngle();
														continue;
													}
													if (opcode == Cs2Opcodes.cameraGetVrot) {
														intStack[isp++] = (int) Camera.pitchTarget;
														continue;
													}
													if (opcode == Cs2Opcodes.cameraGetHrot) {
														intStack[isp++] = (int) Camera.yawTarget;
														continue;
													}
												} else if (opcode < 5700) {
													if (opcode == Cs2Opcodes.directlogin) {
														ssp -= 2;
														string = stringStack[ssp];
														str1 = stringStack[ssp + 1];
														isp--;
														int2 = intStack[isp];
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0 && WorldList.step == 0) {
															LoginManager.startLogin(string, str1, int2);
														}
														continue;
													}
													if (opcode == Cs2Opcodes.skipLoginstage10) {
														LoginManager.continueDelayedLogin();
														continue;
													}
													if (opcode == Cs2Opcodes.resetRCs) {
														if (LoginManager.step == 0) {
															LoginManager.reply = -2;
														}
														continue;
													}
													if (opcode == 5603) {
														isp -= 4;
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0 && WorldList.step == 0) {
															CreateManager.checkInfo(intStack[isp + 2], intStack[isp + 3], intStack[isp], intStack[isp + 1]);
														}
														continue;
													}
													if (opcode == Cs2Opcodes.sendRequestAccount) {
														ssp--;
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0 && WorldList.step == 0) {
															CreateManager.checkName(stringStack[ssp].encode37());
														}
														continue;
													}
													if (opcode == Cs2Opcodes.sendCreateAccount) {
														isp -= 4;
														ssp -= 2;
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0 && WorldList.step == 0) {
															CreateManager.createAccount(intStack[isp], intStack[isp + 3], intStack[isp + 1], stringStack[ssp + 1], stringStack[ssp].encode37(), intStack[isp + 2]);
														}
														continue;
													}
													if (opcode == Cs2Opcodes.resetAccountCreateRC) {
														if (CreateManager.step == 0) {
															CreateManager.reply = -2;
														}
														continue;
													}
													if (opcode == Cs2Opcodes.getGameloginRc) {
														intStack[isp++] = LoginManager.reply;
														continue;
													}
													if (opcode == Cs2Opcodes.getWorldswitchTimer) {
														intStack[isp++] = LoginManager.hopTime;
														continue;
													}
													if (opcode == Cs2Opcodes.getAccountCreateRC) {
														intStack[isp++] = CreateManager.reply;
														continue;
													}
													if (opcode == 5610) {
														for (int1 = 0; int1 < 5; int1++) {
															stringStack[ssp++] = CreateManager.suggestedNames.length > int1 ? CreateManager.suggestedNames[int1].toTitleCase() : EMPTY_STRING;
														}
														CreateManager.suggestedNames = null;
														continue;
													}
													if (opcode == Cs2Opcodes.getDetailedRC) {
														intStack[isp++] = LoginManager.disallowResult;
														continue;
													}
												} else if (opcode < 6100) {
													if (opcode == 6001) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 1) {
															int1 = 1;
														}
														if (int1 > 4) {
															int1 = 4;
														}
														Preferences.brightness = int1;
														if (!GlRenderer.enabled || !Preferences.highDetailLighting) {
															if (Preferences.brightness == 1) {
																Rasteriser.setBrightness(0.9F);
															}
															if (Preferences.brightness == 2) {
																Rasteriser.setBrightness(0.8F);
															}
															if (Preferences.brightness == 3) {
																Rasteriser.setBrightness(0.7F);
															}
															if (Preferences.brightness == 4) {
																Rasteriser.setBrightness(0.6F);
															}
														}
														if (GlRenderer.enabled) {
															FogManager.setInstantFade();
															if (!Preferences.highDetailLighting) {
																refreshGameStateAfterSettingsChange();
															}
														}
														ObjTypeList.clearSprites();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6002) {
														isp--;
														Preferences.setAllVisibleLevels(intStack[isp] == 1);
														LocTypeList.clear();
														refreshGameStateAfterSettingsChange();
														updateRoofRemovalMode();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6003) {
														isp--;
														Preferences.removeRoofsSelectively = intStack[isp] == 1;
														updateRoofRemovalMode();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6005) {
														isp--;
														Preferences.showGroundDecorations = intStack[isp] == 1;
														refreshGameStateAfterSettingsChange();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6006) {
														isp--;
														Preferences.highDetailTextures = intStack[isp] == 1;
														((Js5GlTextureProvider) Rasteriser.textureProvider).setLowDetail(!Preferences.highDetailTextures);
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6007) {
														isp--;
														Preferences.manyIdleAnimations = intStack[isp] == 1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6008) {
														isp--;
														Preferences.flickeringEffectsOn = intStack[isp] == 1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6009) {
														isp--;
														Preferences.manyGroundTextures = intStack[isp] == 1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6010) {
														isp--;
														Preferences.characterShadowsOn = intStack[isp] == 1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6011) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0 || int1 > 2) {
															int1 = 0;
														}
														Preferences.sceneryShadowsType = int1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6012) {
														if (GlRenderer.enabled) {
															MaterialManager.setMaterial(0, 0);
														}
														isp--;
														Preferences.highDetailLighting = intStack[isp] == 1;
														if (GlRenderer.enabled && Preferences.highDetailLighting) {
															Rasteriser.setBrightness(0.7F);
														} else {
															if (Preferences.brightness == 1) {
																Rasteriser.setBrightness(0.9F);
															}
															if (Preferences.brightness == 2) {
																Rasteriser.setBrightness(0.8F);
															}
															if (Preferences.brightness == 3) {
																Rasteriser.setBrightness(0.7F);
															}
															if (Preferences.brightness == 4) {
																Rasteriser.setBrightness(0.6F);
															}
														}
														refreshGameStateAfterSettingsChange();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6014) {
														isp--;
														Preferences.highWaterDetail = intStack[isp] == 1;
														if (GlRenderer.enabled) {
															refreshGameStateAfterSettingsChange();
														}
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6015) {
														isp--;
														Preferences.fogEnabled = intStack[isp] == 1;
														if (GlRenderer.enabled) {
															FogManager.setInstantFade();
														}
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6016) {
														isp--;
														int1 = intStack[isp];
														if (GlRenderer.enabled) {
															GameShell.replaceCanvas = true;
														}
														if (int1 < 0 || int1 > 2) {
															int1 = 0;
														}
														Preferences.antiAliasingMode = int1;
														continue;
													}
													if (opcode == 6017) {
														isp--;
														Preferences.stereo = intStack[isp] == 1;
														client.reinitAudio();
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6018) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0) {
															int1 = 0;
														}
														if (int1 > 127) {
															int1 = 127;
														}
														Preferences.soundEffectVolume = int1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6019) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0) {
															int1 = 0;
														}
														if (int1 > 255) {
															int1 = 255;
														}
														if (int1 != Preferences.musicVolume) {
															if (Preferences.musicVolume == 0 && MusicPlayer.groupId != -1) {
																MidiPlayer.playImmediate(client.js5Archive6, MusicPlayer.groupId, int1);
																MidiPlayer.jingle = false;
															} else if (int1 == 0) {
																MidiPlayer.stop();
																MidiPlayer.jingle = false;
															} else {
																MidiPlayer.setVolume(int1);
															}
															Preferences.musicVolume = int1;
														}
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6020) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0) {
															int1 = 0;
														}
														if (int1 > 127) {
															int1 = 127;
														}
														Preferences.ambientSoundsVolume = int1;
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														continue;
													}
													if (opcode == 6021) {
														isp--;
														neverRemoveRoofs = intStack[isp] == 1;
														updateRoofRemovalMode();
														continue;
													}
													if (opcode == 6023) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0) {
															int1 = 0;
														}
														if (int1 > 2) {
															int1 = 2;
														}
														boolValue = false;
														if (GameShell.maxMemory < 96) {
															boolValue = true;
															int1 = 0;
														}
														Preferences.setParticles(int1);
														Preferences.write(GameShell.signLink);
														Preferences.sentToServer = false;
														intStack[isp++] = boolValue ? 0 : 1;
														continue;
													}
													if (opcode == 6024) {
														isp--;
														int1 = intStack[isp];
														if (int1 < 0 || int1 > 2) {
															int1 = 0;
														}
														Preferences.windowMode = int1;
														Preferences.write(GameShell.signLink);
														continue;
													}
													if (opcode == 6028) {
														isp--;
														Preferences.cursorsEnabled = intStack[isp] != 0;
														Preferences.write(GameShell.signLink);
														continue;
													}
												} else if (opcode < 6200) {
													if (opcode == 6101) {
														intStack[isp++] = Preferences.brightness;
														continue;
													}
													if (opcode == 6102) {
														intStack[isp++] = SceneGraph.allLevelsAreVisible() ? 1 : 0;
														continue;
													}
													if (opcode == 6103) {
														intStack[isp++] = Preferences.removeRoofsSelectively ? 1 : 0;
														continue;
													}
													if (opcode == 6105) {
														intStack[isp++] = Preferences.showGroundDecorations ? 1 : 0;
														continue;
													}
													if (opcode == 6106) {
														intStack[isp++] = Preferences.highDetailTextures ? 1 : 0;
														continue;
													}
													if (opcode == 6107) {
														intStack[isp++] = Preferences.manyIdleAnimations ? 1 : 0;
														continue;
													}
													if (opcode == 6108) {
														intStack[isp++] = Preferences.flickeringEffectsOn ? 1 : 0;
														continue;
													}
													if (opcode == 6109) {
														intStack[isp++] = Preferences.manyGroundTextures ? 1 : 0;
														continue;
													}
													if (opcode == 6110) {
														intStack[isp++] = Preferences.characterShadowsOn ? 1 : 0;
														continue;
													}
													if (opcode == 6111) {
														intStack[isp++] = Preferences.sceneryShadowsType;
														continue;
													}
													if (opcode == 6112) {
														intStack[isp++] = Preferences.highDetailLighting ? 1 : 0;
														continue;
													}
													if (opcode == 6114) {
														intStack[isp++] = Preferences.highWaterDetail ? 1 : 0;
														continue;
													}
													if (opcode == 6115) {
														intStack[isp++] = Preferences.fogEnabled ? 1 : 0;
														continue;
													}
													if (opcode == 6116) {
														intStack[isp++] = Preferences.antiAliasingMode;
														continue;
													}
													if (opcode == 6117) {
														intStack[isp++] = Preferences.stereo ? 1 : 0;
														continue;
													}
													if (opcode == 6118) {
														intStack[isp++] = Preferences.soundEffectVolume;
														continue;
													}
													if (opcode == 6119) {
														intStack[isp++] = Preferences.musicVolume;
														continue;
													}
													if (opcode == 6120) {
														intStack[isp++] = Preferences.ambientSoundsVolume;
														continue;
													}
													if (opcode == 6121) {
														if (GlRenderer.enabled) {
															intStack[isp++] = GlRenderer.arbMultisampleSupported ? 1 : 0;
														} else {
															intStack[isp++] = 0;
														}
														continue;
													}
													if (opcode == 6123) {
														intStack[isp++] = Preferences.getParticleSetting();
														continue;
													}
													if (opcode == 6124) {
														intStack[isp++] = Preferences.windowMode;
														continue;
													}
													if (opcode == 6128) {
														intStack[isp++] = Preferences.cursorsEnabled ? 1 : 0;
														continue;
													}
												} else if (opcode < 6300) {
													if (opcode == 6200) {
														isp -= 2;
														nearZoom = (short) intStack[isp];
														if (nearZoom <= 0) {
															nearZoom = 256;
														}
														farZoom = (short) intStack[isp + 1];
														if (farZoom <= 0) {
															farZoom = 205;
														}
														continue;
													}
													if (opcode == 6201) {
														isp -= 2;
														minViewDistance = (short) intStack[isp];
														if (minViewDistance <= 0) {
															minViewDistance = 256;
														}
														maxViewDistance = (short) intStack[isp + 1];
														if (maxViewDistance <= 0) {
															maxViewDistance = 320;
														}
														continue;
													}
													if (opcode == 6202) {
														isp -= 4;
														minZoom = (short) intStack[isp];
														if (minZoom <= 0) {
															minZoom = 1;
														}
														maxZoom = (short) intStack[isp + 1];
														if (maxZoom <= 0) {
															maxZoom = 32767;
														} else if (minZoom > maxZoom) {
															maxZoom = minZoom;
														}
														minFov = (short) intStack[isp + 2];
														if (minFov <= 0) {
															minFov = 1;
														}
														maxFov = (short) intStack[isp + 3];
														if (maxFov <= 0) {
															maxFov = 32767;
														} else if (maxFov < minFov) {
															maxFov = minFov;
														}
														continue;
													}
													if (opcode == 6203) {
														calculateViewportBounds(InterfaceList.gameViewportComponent.width, 0, InterfaceList.gameViewportComponent.height, 0, false);
														intStack[isp++] = viewportWidth;
														intStack[isp++] = viewportHeight;
														continue;
													}
													if (opcode == 6204) {
														intStack[isp++] = minViewDistance;
														intStack[isp++] = maxViewDistance;
														continue;
													}
													if (opcode == 6205) {
														intStack[isp++] = nearZoom;
														intStack[isp++] = farZoom;
														continue;
													}
												} else if (opcode < 6400) {
													if (opcode == Cs2Opcodes.getMinute) {
														intStack[isp++] = (int) (MonotonicClock.currentTimeMillis() / 60000L);
														continue;
													}
													if (opcode == Cs2Opcodes.getCurrentDaysSinceLaunch) {
														intStack[isp++] = (int) (MonotonicClock.currentTimeMillis() / 86400000L) - 11745;
														continue;
													}
													if (opcode == Cs2Opcodes.getDaysSinceLaunch) {
														isp -= 3;
														int2 = intStack[isp + 2];
														int3 = intStack[isp + 1];
														int1 = intStack[isp];
														aCalendar2.clear();
														aCalendar2.set(Calendar.HOUR_OF_DAY, 12);
														aCalendar2.set(int2, int3, int1);
														intStack[isp++] = (int) (aCalendar2.getTime().getTime() / 86400000L) - 11745;
														continue;
													}
													if (opcode == Cs2Opcodes.getYear) {
														aCalendar2.clear();
														aCalendar2.setTime(new Date(MonotonicClock.currentTimeMillis()));
														intStack[isp++] = aCalendar2.get(Calendar.YEAR);
														continue;
													}
													if (opcode == Cs2Opcodes.isLeapYear) {
														boolValue = true;
														isp--;
														int1 = intStack[isp];
														if (int1 < 0) {
															boolValue = (int1 + 1) % 4 == 0;
														} else if (int1 < 1582) {
															boolValue = int1 % 4 == 0;
														} else if (int1 % 4 != 0) {
															boolValue = false;
														} else if (int1 % 100 != 0) {
															boolValue = true;
														} else if (int1 % 400 != 0) {
															boolValue = false;
														}
														intStack[isp++] = boolValue ? 1 : 0;
														continue;
													}
												} else if (opcode < 6500) {
													if (opcode == Cs2Opcodes.canShowVideoAd) {
														intStack[isp++] = client.showVideoAd() ? 1 : 0;
														continue;
													}
													if (opcode == Cs2Opcodes.isShowingVideoAd) {
														intStack[isp++] = isShowingVideoAd() ? 1 : 0;
														continue;
													}
												} else if (opcode < 6600) {
													if (opcode == 6500) {
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0) {
															intStack[isp++] = WorldList.fetch() == -1 ? 0 : 1;
															continue;
														}
														intStack[isp++] = 1;
														continue;
													}
													@Pc(10247) WorldInfo worldInfo;
													@Pc(10191) World world;
													if (opcode == Cs2Opcodes.getFirstWorldData) {
														world = WorldList.getFirstWorld();
														if (world == null) {
															intStack[isp++] = -1;
															intStack[isp++] = 0;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
														} else {
															intStack[isp++] = world.id;
															intStack[isp++] = world.flags;
															stringStack[ssp++] = world.activity;
															worldInfo = world.getWorldInfo();
															intStack[isp++] = worldInfo.flag;
															stringStack[ssp++] = worldInfo.name;
															intStack[isp++] = world.players;
														}
														continue;
													}
													if (opcode == Cs2Opcodes.getNextWorldData) {
														world = WorldList.getNextWorld();
														if (world == null) {
															intStack[isp++] = -1;
															intStack[isp++] = 0;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
														} else {
															intStack[isp++] = world.id;
															intStack[isp++] = world.flags;
															stringStack[ssp++] = world.activity;
															worldInfo = world.getWorldInfo();
															intStack[isp++] = worldInfo.flag;
															stringStack[ssp++] = worldInfo.name;
															intStack[isp++] = world.players;
														}
														continue;
													}
													if (opcode == Cs2Opcodes.setWorldHost) {
														isp--;
														int1 = intStack[isp];
														if (client.gameState == 10 && LoginManager.hopStep == 0 && LoginManager.step == 0 && CreateManager.step == 0) {
															intStack[isp++] = WorldList.hopWorld(int1) ? 1 : 0;
															continue;
														}
														intStack[isp++] = 0;
														continue;
													}
													if (opcode == Cs2Opcodes.setLastWorld) {
														isp--;
														Preferences.lastWorldId = intStack[isp];
														Preferences.write(GameShell.signLink);
														continue;
													}
													if (opcode == Cs2Opcodes.getLastWorld) {
														intStack[isp++] = Preferences.lastWorldId;
														continue;
													}
													if (opcode == 6506) {
														isp--;
														int1 = intStack[isp];
														@Pc(10440) World selectedWorld = getWorld(int1);
														if (selectedWorld == null) {
															intStack[isp++] = -1;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
															stringStack[ssp++] = EMPTY_STRING;
															intStack[isp++] = 0;
														} else {
															intStack[isp++] = selectedWorld.flags;
															stringStack[ssp++] = selectedWorld.activity;
															@Pc(10458) WorldInfo selectedWorldInfo = selectedWorld.getWorldInfo();
															intStack[isp++] = selectedWorldInfo.flag;
															stringStack[ssp++] = selectedWorldInfo.name;
															intStack[isp++] = selectedWorld.players;
														}
														continue;
													}
													if (opcode == Cs2Opcodes.sortWorldList) {
														isp -= 4;
														int2 = intStack[isp + 2];
														int1 = intStack[isp];
														found = intStack[isp + 3] == 1;
														boolValue = intStack[isp + 1] == 1;
														WorldList.sortWorldList(int2, boolValue, int1, found);
														continue;
													}
												} else if (opcode < 6700) {
													if (opcode == 6600) {
														isp--;
														Preferences.keyboardCameraEnabled = intStack[isp] == 1;
														Preferences.write(GameShell.signLink);
														continue;
													}
													if (opcode == 6601) {
														intStack[isp++] = Preferences.keyboardCameraEnabled ? 1 : 0;
														continue;
													}
												}
											}
										} else if (opcode == Cs2Opcodes.getStructParam) {
											isp -= 2;
											int1 = intStack[isp];
											int3 = intStack[isp + 1];
											paramType = ParamTypeList.get(int3);
											if (paramType.isString()) {
												stringStack[ssp++] = StructTypeList.get(int1).getParam(paramType.defaultString, int3);
											} else {
												intStack[isp++] = StructTypeList.get(int1).getParam(int3, paramType.defaultInt);
											}
											continue;
										}
									} else if (opcode == Cs2Opcodes.getLocParam) {
										isp -= 2;
										int3 = intStack[isp + 1];
										int1 = intStack[isp];
										paramType = ParamTypeList.get(int3);
										if (paramType.isString()) {
											stringStack[ssp++] = LocTypeList.get(int1).getParam(paramType.defaultString, int3);
										} else {
											intStack[isp++] = LocTypeList.get(int1).getParam(paramType.defaultInt, int3);
										}
										continue;
									}
								} else {
									if (opcode == Cs2Opcodes.concatInt) {
										ssp--;
										string = stringStack[ssp];
										isp--;
										int3 = intStack[isp];
										stringStack[ssp++] = JagString.concatenate(new JagString[]{string, JagString.parseInt(int3)});
										continue;
									}
									if (opcode == Cs2Opcodes.concatString) {
										ssp -= 2;
										str1 = stringStack[ssp + 1];
										string = stringStack[ssp];
										stringStack[ssp++] = JagString.concatenate(new JagString[]{string, str1});
										continue;
									}
									if (opcode == Cs2Opcodes.concatSignedInt) {
										ssp--;
										string = stringStack[ssp];
										isp--;
										int3 = intStack[isp];
										stringStack[ssp++] = JagString.concatenate(new JagString[]{string, JagString.parseIntTrue(int3)});
										continue;
									}
									if (opcode == Cs2Opcodes.toLowerStr) {
										ssp--;
										string = stringStack[ssp];
										stringStack[ssp++] = string.toLowerCase();
										continue;
									}
									if (opcode == Cs2Opcodes.timeToStr) {
										isp--;
										int1 = intStack[isp];
										@Pc(11770) long millis = (long) int1 * 86400000L + 1014768000000L;
										aCalendar2.setTime(new Date(millis));
										argIndex = aCalendar2.get(Calendar.DATE);
										c = aCalendar2.get(Calendar.MONTH);
										j = aCalendar2.get(Calendar.YEAR);
										stringStack[ssp++] = JagString.concatenate(new JagString[]{JagString.parseInt(argIndex), DASH, DateUtil.SCRIPT_MONTHS[c], DASH, JagString.parseInt(j)});
										continue;
									}
									if (opcode == Cs2Opcodes.strForGender) {
										ssp -= 2;
										str1 = stringStack[ssp + 1];
										string = stringStack[ssp];
										if (PlayerList.self.appearance != null && PlayerList.self.appearance.gender) {
											stringStack[ssp++] = str1;
											continue;
										}
										stringStack[ssp++] = string;
										continue;
									}
									if (opcode == Cs2Opcodes.parseInt) {
										isp--;
										int1 = intStack[isp];
										stringStack[ssp++] = JagString.parseInt(int1);
										continue;
									}
									if (opcode == Cs2Opcodes.compare) {
										ssp -= 2;
										intStack[isp++] = stringStack[ssp].compare(stringStack[ssp + 1]);
										continue;
									}
									if (opcode == Cs2Opcodes.getLineCount) {
										ssp--;
										string = stringStack[ssp];
										isp -= 2;
										int2 = intStack[isp + 1];
										int3 = intStack[isp];
										intStack[isp++] = FontMetricsList.get(int2).getParagraphLineCount(string, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.getMaxLineWidth) {
										isp -= 2;
										ssp--;
										string = stringStack[ssp];
										int2 = intStack[isp + 1];
										int3 = intStack[isp];
										intStack[isp++] = FontMetricsList.get(int2).getMaxLineWidth(string, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.chooseString) {
										ssp -= 2;
										string = stringStack[ssp];
										str1 = stringStack[ssp + 1];
										isp--;
										if (intStack[isp] == 1) {
											stringStack[ssp++] = string;
										} else {
											stringStack[ssp++] = str1;
										}
										continue;
									}
									if (opcode == Cs2Opcodes.escape) {
										ssp--;
										string = stringStack[ssp];
										stringStack[ssp++] = Font.escape(string);
										continue;
									}
									if (opcode == Cs2Opcodes.concatChar) {
										ssp--;
										string = stringStack[ssp];
										isp--;
										int3 = intStack[isp];
										if (int3 == -1) {
											throw new RuntimeException("null char");
										}
										stringStack[ssp++] = string.concatChar(int3);
										continue;
									}
									if (opcode == Cs2Opcodes.isValidChar) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.isValidChar(int1) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.isAlphaNumeric) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.isLetterOrDigit(int1) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.isLetter) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.isLetter(int1) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.isDigit) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.isDigit(int1) ? 1 : 0;
										continue;
									}
									if (opcode == Cs2Opcodes.length) {
										ssp--;
										string = stringStack[ssp];
										if (string == null) {
											intStack[isp++] = 0;
										} else {
											intStack[isp++] = string.length();
										}
										continue;
									}
									if (opcode == Cs2Opcodes.substr) {
										isp -= 2;
										ssp--;
										string = stringStack[ssp];
										int3 = intStack[isp];
										int2 = intStack[isp + 1];
										stringStack[ssp++] = string.substring(int2, int3);
										continue;
									}
									if (opcode == Cs2Opcodes.removeTags) {
										ssp--;
										string = stringStack[ssp];
										str1 = JagString.allocate(string.length());
										@Pc(12220) boolean inTag = false;
										for (argIndex = 0; argIndex < string.length(); argIndex++) {
											c = string.charAt(argIndex);
											if (c == 60) {
												inTag = true;
											} else if (c == 62) {
												inTag = false;
											} else if (!inTag) {
												str1.append(c);
											}
										}
										str1.compact();
										stringStack[ssp++] = str1;
										continue;
									}
									if (opcode == Cs2Opcodes.indexOfChar) {
										isp -= 2;
										ssp--;
										string = stringStack[ssp];
										int3 = intStack[isp];
										int2 = intStack[isp + 1];
										intStack[isp++] = string.indexOf(int3, int2);
										continue;
									}
									if (opcode == Cs2Opcodes.indexOfStr) {
										ssp -= 2;
										string = stringStack[ssp];
										str1 = stringStack[ssp + 1];
										isp--;
										int2 = intStack[isp];
										intStack[isp++] = string.indexOf(str1, int2);
										continue;
									}
									if (opcode == Cs2Opcodes.toLower) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.toLowerCase(int1);
										continue;
									}
									if (opcode == Cs2Opcodes.toUpper) {
										isp--;
										int1 = intStack[isp];
										intStack[isp++] = CharUtils.toUpperCase(int1);
										continue;
									}
									if (opcode == Cs2Opcodes.formatNumber) {
										isp--;
										boolResult = intStack[isp] != 0;
										isp--;
										int3 = intStack[isp];
										stringStack[ssp++] = StringUtils.formatNumber(client.language, boolResult, 0, int3);
										continue;
									}
								}
							}
						} else {
							if (opcode < 2000) {
								component = useActiveComponent1 ? staticActiveComponent1 : staticActiveComponent2;
							} else {
								isp--;
								component = InterfaceList.getComponent(intStack[isp]);
								opcode -= 1000;
							}
							if (opcode == Cs2Opcodes.setPosition) {
								isp -= 4;
								component.baseX = intStack[isp];
								component.baseY = intStack[isp + 1];
								int2 = intStack[isp + 3];
								if (int2 < 0) {
									int2 = 0;
								} else if (int2 > 5) {
									int2 = 5;
								}
								int3 = intStack[isp + 2];
								if (int3 < 0) {
									int3 = 0;
								} else if (int3 > 5) {
									int3 = 5;
								}
								component.xMode = (byte) int2;
								component.yMode = (byte) int3;
								InterfaceList.redraw(component);
								InterfaceList.update(component);
								if (component.createdComponentId == -1) {
									DelayedStateChange.setComponentPositionClient(component.id);
								}
								continue;
							}
							if (opcode == Cs2Opcodes.setSize) {
								isp -= 4;
								component.baseWidth = intStack[isp];
								component.baseHeight = intStack[isp + 1];
								component.modelViewportWidth = 0;
								component.modelViewportHeight = 0;
								int3 = intStack[isp + 2];
								int2 = intStack[isp + 3];
								if (int2 < 0) {
									int2 = 0;
								} else if (int2 > 4) {
									int2 = 4;
								}
								component.dynamicHeightValue = (byte) int2;
								if (int3 < 0) {
									int3 = 0;
								} else if (int3 > 4) {
									int3 = 4;
								}
								component.dynamicWidthValue = (byte) int3;
								InterfaceList.redraw(component);
								InterfaceList.update(component);
								if (component.type == 0) {
									InterfaceList.layoutComponent(component, false);
								}
								continue;
							}
							if (opcode == Cs2Opcodes.setHidden) {
								isp--;
								boolValue = intStack[isp] == 1;
								if (boolValue != component.hidden) {
									component.hidden = boolValue;
									InterfaceList.redraw(component);
								}
								if (component.createdComponentId == -1) {
									DelayedStateChange.setComponentHiddenClient(component.id);
								}
								continue;
							}
							if (opcode == Cs2Opcodes.setAspect) {
								isp -= 2;
								component.aspectWidth = intStack[isp];
								component.aspectHeight = intStack[isp + 1];
								InterfaceList.redraw(component);
								InterfaceList.update(component);
								if (component.type == 0) {
									InterfaceList.layoutComponent(component, false);
								}
								continue;
							}
							if (opcode == Cs2Opcodes.setNoClickThrough) {
								isp--;
								component.noClickThrough = intStack[isp] == 1;
								continue;
							}
						}
					}
				}
				throw new IllegalStateException();
			}
		} catch (@Pc(14378) Exception ex) {
			if (script.name == null) {
				if (client.modeWhere != 0) {
					Chat.add(EMPTY_STRING, 0, CS_ERROR);
				}
				TracingException.report("CS2 - scr:" + script.key + " op:" + op, ex);
			} else {
				@Pc(14385) JagString str = JagString.allocate(30);
				str.appendString(ERROR_IN).appendString(script.name);
				for (cycles = fp - 1; cycles >= 0; cycles--) {
					str.appendString(ERROR_VIA).appendString(callStack[cycles].script.name);
				}
				if (op == 40) {
					cycles = intOperands[pc];
					str.appendString(ERROR_NONEXISTENT_GOSUB).appendString(JagString.parseInt(cycles));
				}
				if (client.modeWhere != 0) {
					Chat.add(EMPTY_STRING, 0, JagString.concatenate(new JagString[]{CLIENTSCRIPT_ERROR, script.name}));
				}
				TracingException.report("CS2 - scr:" + script.key + " op:" + op + new String(str.toByteArray()), ex);
			}
		}
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(ILclient!jl;)V")
	public static void run(@OriginalArg(1) HookRequest request) {
		run(200000, request);
	}

}
