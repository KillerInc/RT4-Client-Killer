package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class MiniMap {
	@OriginalMember(owner = "client!ke", name = "T", descriptor = "[[I")
	public static final int[][] tileShapeMasks = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
	@OriginalMember(owner = "client!wc", name = "h", descriptor = "[[I")
	public static final int[][] tileShapeRotations = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
	@OriginalMember(owner = "client!qc", name = "ab", descriptor = "[I")
	public static final int[] locId = new int[1000];
	@OriginalMember(owner = "client!lf", name = "d", descriptor = "[I")
	public static final int[] locX = new int[1000];
	@OriginalMember(owner = "client!he", name = "eb", descriptor = "[I")
	public static final int[] locY = new int[1000];
	@OriginalMember(owner = "client!ld", name = "b", descriptor = "[Lclient!nc;")
	public static final MapMarker[] hintMapMarkers = new MapMarker[4];
	@OriginalMember(owner = "client!ld", name = "d", descriptor = "Lclient!na;")
	public static final JagString HIDDEN_OPTION_TEXT = JagString.parse("Hidden)2use");
	@OriginalMember(owner = "client!ef", name = "j", descriptor = "Lclient!mm;")
	public static SoftwareSprite softwareSprite;
	@OriginalMember(owner = "client!ha", name = "i", descriptor = "Lclient!qf;")
	public static Sprite sprite;
	@OriginalMember(owner = "client!ug", name = "m", descriptor = "I")
	public static int locs = 0;
	@OriginalMember(owner = "client!wb", name = "d", descriptor = "I")
	public static int state = 0;
	@OriginalMember(owner = "client!we", name = "w", descriptor = "I")
	public static int zoomOffset = 0;
	@OriginalMember(owner = "client!ej", name = "W", descriptor = "I")
	public static int compassAngleOffset = 0;
	@OriginalMember(owner = "client!vg", name = "d", descriptor = "I")
	public static int angleJitterStep = 2;
	@OriginalMember(owner = "client!oe", name = "n", descriptor = "I")
	public static int zoomJitterStep = 1;
	@OriginalMember(owner = "client!gi", name = "H", descriptor = "I")
	public static int jitterTimer = 0;
	@OriginalMember(owner = "client!nf", name = "i", descriptor = "I")
	public static int useCursor = -1;
	@OriginalMember(owner = "client!se", name = "h", descriptor = "I")
	public static int examineCursor = -1;
	@OriginalMember(owner = "client!sd", name = "R", descriptor = "I")
	public static int selectedComponentId;

	@OriginalMember(owner = "client!ma", name = "a", descriptor = "([IIIIII)V")
	public static void renderTile(@OriginalArg(0) int[] pixels, @OriginalArg(1) int index, @OriginalArg(3) int plane, @OriginalArg(4) int tileX, @OriginalArg(5) int tileY) {
		@Pc(7) Tile tile = SceneGraph.tiles[plane][tileX][tileY];
		if (tile == null) {
			return;
		}
		@Pc(13) PlainTile plainTile = tile.plainTile;
		@Pc(23) int shape;
		if (plainTile != null) {
			@Pc(18) int flatColor = plainTile.flatColor;
			if (flatColor != 0) {
				for (shape = 0; shape < 4; shape++) {
					pixels[index] = flatColor;
					pixels[index + 1] = flatColor;
					pixels[index + 2] = flatColor;
					pixels[index + 3] = flatColor;
					index += 512;
				}
			}
			return;
		}
		@Pc(58) ShapedTile shapedTile = tile.shapedTile;
		if (shapedTile == null) {
			return;
		}
		shape = shapedTile.shape;
		@Pc(67) int rotation = shapedTile.rotation;
		@Pc(70) int textureColor = shapedTile.textureId;
		@Pc(73) int shapeColor = shapedTile.flatColor;
		@Pc(77) int[] shapeMask = tileShapeMasks[shape];
		@Pc(81) int[] rotationMap = tileShapeRotations[rotation];
		@Pc(83) int maskIndex = 0;
		@Pc(87) int row;
		if (textureColor != 0) {
			for (row = 0; row < 4; row++) {
				pixels[index] = shapeMask[rotationMap[maskIndex++]] == 0 ? textureColor : shapeColor;
				pixels[index + 1] = shapeMask[rotationMap[maskIndex++]] == 0 ? textureColor : shapeColor;
				pixels[index + 2] = shapeMask[rotationMap[maskIndex++]] == 0 ? textureColor : shapeColor;
				pixels[index + 3] = shapeMask[rotationMap[maskIndex++]] == 0 ? textureColor : shapeColor;
				index += 512;
			}
			return;
		}
		for (row = 0; row < 4; row++) {
			if (shapeMask[rotationMap[maskIndex++]] != 0) {
				pixels[index] = shapeColor;
			}
			if (shapeMask[rotationMap[maskIndex++]] != 0) {
				pixels[index + 1] = shapeColor;
			}
			if (shapeMask[rotationMap[maskIndex++]] != 0) {
				pixels[index + 2] = shapeColor;
			}
			if (shapeMask[rotationMap[maskIndex++]] != 0) {
				pixels[index + 3] = shapeColor;
			}
			index += 512;
		}
	}

	@OriginalMember(owner = "client!kl", name = "b", descriptor = "(II)Z")
	public static boolean renderMap(@OriginalArg(1) int plane) {
		@Pc(35) int pixelCount;
		@Pc(37) int i;
		@Pc(76) int pixelOffset;
		@Pc(80) int j;
		if (softwareSprite == null) {
			if (GlRenderer.enabled || sprite == null) {
				softwareSprite = new SoftwareSprite(512, 512);
			} else {
				softwareSprite = (SoftwareSprite) sprite;
			}
			@Pc(32) int[] pixelData = softwareSprite.pixels;
			pixelCount = pixelData.length;
			for (i = 0; i < pixelCount; i++) {
				pixelData[i] = 1;
			}
			for (i = 1; i < 103; i++) {
				pixelOffset = 4 * 512 * (103 - i) + 24628;
				for (j = 1; j < 103; j++) {
					if ((SceneGraph.renderFlags[plane][j][i] & 0x18) == 0) {
						renderTile(pixelData, pixelOffset, plane, j, i);
					}
					if (plane < 3 && (SceneGraph.renderFlags[plane + 1][j][i] & 0x8) != 0) {
						renderTile(pixelData, pixelOffset, plane + 1, j, i);
					}
					pixelOffset += 4;
				}
			}
			locs = 0;
			for (i = 0; i < 104; i++) {
				for (pixelOffset = 0; pixelOffset < 104; pixelOffset++) {
					@Pc(169) long groundDecorKey = SceneGraph.getGroundDecorKey(Player.plane, i + 0, pixelOffset);
					if (groundDecorKey != 0L) {
						@Pc(184) LocType locType = LocTypeList.get((int) (groundDecorKey >>> 32) & Integer.MAX_VALUE);
						@Pc(187) int mapFunction = locType.mapfunction;
						@Pc(194) int adjustedX;
						if (locType.multiLocs != null) {
							for (adjustedX = 0; adjustedX < locType.multiLocs.length; adjustedX++) {
								if (locType.multiLocs[adjustedX] != -1) {
									@Pc(216) LocType multiLocType = LocTypeList.get(locType.multiLocs[adjustedX]);
									if (multiLocType.mapfunction >= 0) {
										mapFunction = multiLocType.mapfunction;
										break;
									}
								}
							}
						}
						if (mapFunction >= 0) {
							@Pc(237) int adjustedY = pixelOffset;
							adjustedX = i;
							if (mapFunction != 22 && mapFunction != 29 && mapFunction != 34 && mapFunction != 36 && mapFunction != 46 && mapFunction != 47 && mapFunction != 48) {
								@Pc(269) int[][] collisionFlags = PathFinder.collisionMaps[Player.plane].flags;
								for (@Pc(271) int step = 0; step < 10; step++) {
									@Pc(281) int direction = (int) (Math.random() * 4.0D);
									if (direction == 0 && adjustedX > 0 && i - 3 < adjustedX && (collisionFlags[adjustedX - 1][adjustedY] & 0x12C0108) == 0) {
										adjustedX--;
									}
									if (direction == 1 && adjustedX < 103 && i + 3 > adjustedX && (collisionFlags[adjustedX + 1][adjustedY] & 0x12C0180) == 0) {
										adjustedX++;
									}
									if (direction == 2 && adjustedY > 0 && pixelOffset - 3 < adjustedY && (collisionFlags[adjustedX][adjustedY - 1] & 0x12C0102) == 0) {
										adjustedY--;
									}
									if (direction == 3 && adjustedY < 103 && adjustedY < pixelOffset + 3 && (collisionFlags[adjustedX][adjustedY + 1] & 0x12C0120) == 0) {
										adjustedY++;
									}
								}
							}
							locId[locs] = locType.id;
							locX[locs] = adjustedX;
							locY[locs] = adjustedY;
							locs++;
						}
					}
				}
			}
		}
		softwareSprite.makeTarget();
		@Pc(455) int fogColor = ((int) (Math.random() * 20.0D) + 238 - 10 << 8) + ((int) (Math.random() * 20.0D) + 238 - 10 << 16) + (int) (Math.random() * 20.0D) + 228;
		pixelCount = (int) (Math.random() * 20.0D) + 238 - 10 << 16;
		for (i = 1; i < 103; i++) {
			for (pixelOffset = 1; pixelOffset < 103; pixelOffset++) {
				if ((SceneGraph.renderFlags[plane][pixelOffset][i] & 0x18) == 0 && !renderMapTileFeatures(pixelOffset, fogColor, i, pixelCount, plane)) {
					if (GlRenderer.enabled) {
						SoftwareRaster.pixels = null;
					} else {
						SoftwareRaster.frameBuffer.makeTarget();
					}
					return false;
				}
				if (plane < 3 && (SceneGraph.renderFlags[plane + 1][pixelOffset][i] & 0x8) != 0 && !renderMapTileFeatures(pixelOffset, fogColor, i, pixelCount, plane + 1)) {
					if (GlRenderer.enabled) {
						SoftwareRaster.pixels = null;
					} else {
						SoftwareRaster.frameBuffer.makeTarget();
					}
					return false;
				}
			}
		}
		if (GlRenderer.enabled) {
			@Pc(576) int[] glPixels = softwareSprite.pixels;
			pixelOffset = glPixels.length;
			for (j = 0; j < pixelOffset; j++) {
				if (glPixels[j] == 0) {
					glPixels[j] = 1;
				}
			}
			sprite = new GlSprite(softwareSprite);
		} else {
			sprite = softwareSprite;
		}
		if (GlRenderer.enabled) {
			SoftwareRaster.pixels = null;
		} else {
			SoftwareRaster.frameBuffer.makeTarget();
		}
		softwareSprite = null;
		return true;
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(IBIILclient!be;)V")
	public static void render(@OriginalArg(0) int mapAngle, @OriginalArg(2) int y, @OriginalArg(3) int x, @OriginalArg(4) Component component) {
		client.audioLoop();
		if (GlRenderer.enabled) {
			GlRaster.setClip(x, y, x + component.width, y + component.height);
		} else {
			SoftwareRaster.setClip(x, y, x + component.width, y + component.height);
		}
		if (state != 2 && state != 5 && sprite != null) {
			@Pc(48) int totalAngle = compassAngleOffset + (int) Camera.yawTarget & 0x7FF;
			@Pc(57) int playerMapX = PlayerList.self.xFine / 32 + 48;
			@Pc(67) int playerMapY = 464 - PlayerList.self.yFine / 32;
			if (GlRenderer.enabled) {
				((GlSprite) sprite).renderRotatedTransparent(x, y, component.width, component.height, playerMapX, playerMapY, totalAngle, zoomOffset + 256, (GlSprite) component.getSprite(false));
			} else {
				((SoftwareSprite) sprite).renderRotated(x, y, component.width, component.height, playerMapX, playerMapY, totalAngle, zoomOffset + 256, component.clickMaskStart, component.clickMaskWidth);
			}
			@Pc(146) int i;
			@Pc(181) int j;
			@Pc(150) int sinAngle;
			@Pc(154) int cosAngle;
			@Pc(231) int screenDX;
			@Pc(200) int screenDY;
			@Pc(239) int labelWidth;
			@Pc(271) int labelColor;
			if (LoginManager.mapElementList != null) {
				for (@Pc(117) int k = 0; k < LoginManager.mapElementList.count; k++) {
					if (LoginManager.mapElementList.isMinimapLabelVisible(k)) {
						i = (LoginManager.mapElementList.coordX[k] - Camera.originX) * 4 + 2 - PlayerList.self.xFine / 32;
						sinAngle = MathUtils.sin[totalAngle];
						cosAngle = MathUtils.cos[totalAngle];
						@Pc(156) Font labelFont = Fonts.p11Full;
						@Pc(164) int scaledSin = sinAngle * 256 / (zoomOffset + 256);
						j = (LoginManager.mapElementList.coordY[k] - Camera.originY) * 4 + 2 - PlayerList.self.yFine / 32;
						@Pc(189) int scaledCos = cosAngle * 256 / (zoomOffset + 256);
						screenDY = j * scaledCos - i * scaledSin >> 16;
						if (LoginManager.mapElementList.getLabelSize(k) == 1) {
							labelFont = Fonts.p12Full;
						}
						if (LoginManager.mapElementList.getLabelSize(k) == 2) {
							labelFont = Fonts.b12Full;
						}
						screenDX = scaledSin * j + scaledCos * i >> 16;
						labelWidth = labelFont.getMaxLineWidth(LoginManager.mapElementList.names[k], 100);
						@Pc(245) int labelX = screenDX - labelWidth / 2;
						if (labelX >= -component.width && labelX <= component.width && screenDY >= -component.height && screenDY <= component.height) {
							labelColor = 16777215;
							if (LoginManager.mapElementList.colors[k] != -1) {
								labelColor = LoginManager.mapElementList.colors[k];
							}
							if (GlRenderer.enabled) {
								GlFont.setLineMask((GlSprite) component.getSprite(false));
							} else {
								SoftwareRaster.setLineMasks(component.clickMaskStart, component.clickMaskWidth);
							}
							labelFont.renderParagraphAlpha(LoginManager.mapElementList.names[k], x + labelX + component.width / 2, y + component.height / 2 + -screenDY, labelWidth, 50, labelColor, 0, 1, 0, 0);
							if (GlRenderer.enabled) {
								GlFont.clearLineMask();
							} else {
								SoftwareRaster.clearLineMasks();
							}
						}
					}
				}
			}
			for (i = 0; i < locs; i++) {
				j = locX[i] * 4 + 2 - PlayerList.self.xFine / 32;
				sinAngle = locY[i] * 4 + 2 - PlayerList.self.yFine / 32;
				@Pc(382) LocType locType = LocTypeList.get(locId[i]);
				if (locType.multiLocs != null) {
					locType = locType.getMultiLoc();
					if (locType == null || locType.mapfunction == -1) {
						continue;
					}
				}
				renderMapIcon(component, Sprites.mapfuncs[locType.mapfunction], sinAngle, j, y, x);
			}
			for (i = 0; i < 104; i++) {
				for (j = 0; j < 104; j++) {
					@Pc(439) LinkedList objStack = SceneGraph.objStacks[Player.plane][i][j];
					if (objStack != null) {
						cosAngle = i * 4 + 2 - PlayerList.self.xFine / 32;
						screenDX = j * 4 + 2 - PlayerList.self.yFine / 32;
						renderMapIcon(component, Sprites.mapdots[0], screenDX, cosAngle, y, x);
					}
				}
			}
			for (i = 0; i < NpcList.size; i++) {
				@Pc(498) Npc npc = NpcList.npcs[NpcList.ids[i]];
				if (npc != null && npc.isVisible()) {
					@Pc(507) NpcType npcType = npc.type;
					if (npcType != null && npcType.multiNpcs != null) {
						npcType = npcType.getMultiNpc();
					}
					if (npcType != null && npcType.minimapdisplay && npcType.interactive) {
						cosAngle = npc.xFine / 32 - PlayerList.self.xFine / 32;
						screenDX = npc.yFine / 32 - PlayerList.self.yFine / 32;
						if (npcType.minimapmarkerobjectentry == -1) {
							renderMapIcon(component, Sprites.mapdots[1], screenDX, cosAngle, y, x);
						} else {
							renderMapIcon(component, Sprites.mapfuncs[npcType.minimapmarkerobjectentry], screenDX, cosAngle, y, x);
						}
					}
				}
			}
			for (i = 0; i < PlayerList.size; i++) {
				@Pc(591) Player player = PlayerList.players[PlayerList.ids[i]];
				if (player != null && player.isVisible()) {
					cosAngle = player.yFine / 32 - PlayerList.self.yFine / 32;
					sinAngle = player.xFine / 32 - PlayerList.self.xFine / 32;
					@Pc(624) long encodedName = player.username.encode37();
					@Pc(626) boolean isFriend = false;
					for (labelWidth = 0; labelWidth < FriendsList.size; labelWidth++) {
						if (encodedName == FriendsList.encodedUsernames[labelWidth] && FriendsList.worlds[labelWidth] != 0) {
							isFriend = true;
							break;
						}
					}
					@Pc(660) boolean isClanMember = false;
					for (labelColor = 0; labelColor < ClanChat.size; labelColor++) {
						if (encodedName == ClanChat.members[labelColor].key) {
							isClanMember = true;
							break;
						}
					}
					@Pc(682) boolean isTeammate = PlayerList.self.team != 0 && player.team != 0 && player.team == PlayerList.self.team;
					if (isFriend) {
						renderMapIcon(component, Sprites.mapdots[3], cosAngle, sinAngle, y, x);
					} else if (isClanMember) {
						renderMapIcon(component, Sprites.mapdots[5], cosAngle, sinAngle, y, x);
					} else if (isTeammate) {
						renderMapIcon(component, Sprites.mapdots[4], cosAngle, sinAngle, y, x);
					} else {
						renderMapIcon(component, Sprites.mapdots[2], cosAngle, sinAngle, y, x);
					}
				}
			}
			@Pc(756) MapMarker[] markers = hintMapMarkers;
			for (j = 0; j < markers.length; j++) {
				@Pc(770) MapMarker marker = markers[j];
				if (marker != null && marker.type != 0 && client.loop % 20 < 10) {
					if (marker.type == 1 && marker.actorTargetId >= 0 && marker.actorTargetId < NpcList.npcs.length) {
						@Pc(804) Npc hintNpc = NpcList.npcs[marker.actorTargetId];
						if (hintNpc != null) {
							screenDX = hintNpc.xFine / 32 - PlayerList.self.xFine / 32;
							screenDY = hintNpc.yFine / 32 - PlayerList.self.yFine / 32;
							renderHintMarker(marker.arrowSpriteId, y, x, screenDX, screenDY, component);
						}
					}
					if (marker.type == 2) {
						cosAngle = (marker.targetX - Camera.originX) * 4 + 2 - PlayerList.self.xFine / 32;
						screenDX = (-Camera.originY + marker.targetY) * 4 + 2 - PlayerList.self.yFine / 32;
						renderHintMarker(marker.arrowSpriteId, y, x, cosAngle, screenDX, component);
					}
					if (marker.type == 10 && marker.actorTargetId >= 0 && PlayerList.players.length > marker.actorTargetId) {
						@Pc(905) Player hintPlayer = PlayerList.players[marker.actorTargetId];
						if (hintPlayer != null) {
							screenDY = hintPlayer.yFine / 32 - PlayerList.self.yFine / 32;
							screenDX = hintPlayer.xFine / 32 - PlayerList.self.xFine / 32;
							renderHintMarker(marker.arrowSpriteId, y, x, screenDX, screenDY, component);
						}
					}
				}
			}
			if (LoginManager.mapFlagX != 0) {
				i = LoginManager.mapFlagX * 4 + 2 - PlayerList.self.xFine / 32;
				j = LoginManager.mapFlagY * 4 + 2 - PlayerList.self.yFine / 32;
				renderMapIcon(component, Sprites.mapflags, j, i, y, x);
			}
			if (GlRenderer.enabled) {
				GlRaster.fillRect(x + component.width / 2 - 1, y + -1 - -(component.height / 2), 3, 3, 16777215);
			} else {
				SoftwareRaster.fillRect(component.width / 2 + x - 1, component.height / 2 + -1 + y, 3, 3, 16777215);
			}
		} else if (GlRenderer.enabled) {
			@Pc(1041) Sprite bgSprite = component.getSprite(false);
			if (bgSprite != null) {
				bgSprite.render(x, y);
			}
		} else {
			SoftwareRaster.clearMaskedRegion(x, y, component.clickMaskStart, component.clickMaskWidth);
		}
		InterfaceList.rectangleRedraw[mapAngle] = true;
	}

	@OriginalMember(owner = "client!em", name = "a", descriptor = "(Lclient!be;Lclient!qf;IIIBI)V")
	public static void renderMapIcon(@OriginalArg(0) Component component, @OriginalArg(1) Sprite icon, @OriginalArg(2) int dx, @OriginalArg(3) int dy, @OriginalArg(4) int offsetY, @OriginalArg(6) int offsetX) {
		if (icon == null) {
			return;
		}
		@Pc(21) int distSq = dy * dy + dx * dx;
		@Pc(27) int mapAngle = compassAngleOffset + (int) Camera.yawTarget & 0x7FF;
		@Pc(39) int maxRadius = Math.max(component.width / 2, component.height / 2) + 10;
		if (maxRadius * maxRadius < distSq) {
			return;
		}
		@Pc(50) int sinRaw = MathUtils.sin[mapAngle];
		@Pc(58) int scaledSin = sinRaw * 256 / (zoomOffset + 256);
		@Pc(62) int cosRaw = MathUtils.cos[mapAngle];
		@Pc(70) int scaledCos = cosRaw * 256 / (zoomOffset + 256);
		@Pc(81) int screenX = scaledSin * dx + dy * scaledCos >> 16;
		@Pc(92) int screenY = scaledCos * dx - dy * scaledSin >> 16;
		if (GlRenderer.enabled) {
			((GlSprite) icon).renderClipped(component.width / 2 + offsetX + screenX - icon.innerWidth / 2, component.height / 2 + offsetY - (screenY + icon.innerHeight / 2), (GlSprite) component.getSprite(false));
		} else {
			((SoftwareSprite) icon).drawClipped(component.width / 2 + offsetX + screenX - icon.innerWidth / 2, -(icon.innerHeight / 2) + component.height / 2 + offsetY + -screenY, component.clickMaskStart, component.clickMaskWidth);
		}
	}

	@OriginalMember(owner = "client!hi", name = "a", descriptor = "(IIIIILclient!be;Z)V")
	public static void renderHintMarker(@OriginalArg(0) int hintId, @OriginalArg(1) int offsetY, @OriginalArg(2) int offsetX, @OriginalArg(3) int dy, @OriginalArg(4) int dx, @OriginalArg(5) Component component) {
		@Pc(13) int distSq = dy * dy + dx * dx;
		if (distSq > 360000) {
			return;
		}
		@Pc(30) int minRadius = Math.min(component.width / 2, component.height / 2);
		if (minRadius * minRadius >= distSq) {
			renderMapIcon(component, Sprites.mapmarkhints[hintId], dx, dy, offsetY, offsetX);
			return;
		}
		minRadius -= 10;
		@Pc(58) int mapAngle = compassAngleOffset + (int) Camera.yawTarget & 0x7FF;
		@Pc(62) int cosRaw = MathUtils.cos[mapAngle];
		@Pc(66) int sinRaw = MathUtils.sin[mapAngle];
		@Pc(74) int scaledSin = sinRaw * 256 / (zoomOffset + 256);
		@Pc(82) int scaledCos = cosRaw * 256 / (zoomOffset + 256);
		@Pc(93) int screenX = dx * scaledSin + scaledCos * dy >> 16;
		@Pc(104) int screenY = dx * scaledCos - scaledSin * dy >> 16;
		@Pc(110) double angle = Math.atan2(screenX, screenY);
		@Pc(117) int edgeX = (int) (Math.sin(angle) * (double) minRadius);
		@Pc(124) int edgeY = (int) (Math.cos(angle) * (double) minRadius);
		if (GlRenderer.enabled) {
			((GlSprite) Sprites.hintMapEdge[hintId]).renderRotatedFixed((component.width / 2 + offsetX + edgeX) * 16, (component.height / 2 + offsetY - edgeY) * 16, (int) (angle * 10430.378D));
		} else {
			((SoftwareSprite) Sprites.hintMapEdge[hintId]).drawRotatedFixed(edgeX + component.width / 2 + offsetX - 10, component.height / 2 + -10 + offsetY + -edgeY, angle);
		}
	}

	@OriginalMember(owner = "client!hi", name = "a", descriptor = "(Lclient!be;B)Lclient!na;")
	public static JagString getTargetVerb(@OriginalArg(0) Component component) {
		if (InterfaceList.getServerActiveProperties(component).getTargetMask() == 0) {
			return null;
		} else if (component.optionCircumfix == null || component.optionCircumfix.trim().length() == 0) {
			return Cheat.qaOpTest ? HIDDEN_OPTION_TEXT : null;
		} else {
			return component.optionCircumfix;
		}
	}

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(ILclient!pb;ZIIII)Z")
	public static boolean renderScenery(@OriginalArg(0) int tileX, @OriginalArg(1) LocType locType, @OriginalArg(5) int tileY, @OriginalArg(6) int rotation) {
		@Pc(10) MsiType msiType = MsiTypeList.get(locType.mapscene);
		if (msiType.spriteId == -1) {
			return true;
		}
		if (locType.mapSceneRotated) {
			@Pc(24) int adjustedRotation = rotation + locType.mapSceneAngleOffset;
			rotation = adjustedRotation & 0x3;
		} else {
			rotation = 0;
		}
		@Pc(42) SoftwareIndexedSprite indexedSprite = msiType.getSprite(rotation);
		if (indexedSprite == null) {
			return false;
		}
		@Pc(49) int width = locType.width;
		@Pc(52) int length = locType.length;
		if ((rotation & 0x1) == 1) {
			width = locType.length;
			length = locType.width;
		}
		@Pc(66) int drawWidth = indexedSprite.innerWidth;
		@Pc(69) int drawHeight = indexedSprite.innerHeight;
		if (msiType.stretchToTile) {
			drawHeight = length * 4;
			drawWidth = width * 4;
		}
		if (msiType.tintColor == 0) {
			indexedSprite.renderScaled(tileX * 4 + 48, (-length + -tileY + 104) * 4 + 48, drawWidth, drawHeight);
		} else {
			indexedSprite.renderScaledTinted(tileX * 4 + 48, (-length + -tileY + 104) * 4 + 48, drawWidth, drawHeight, msiType.tintColor);
		}
		return true;
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IIIZIII)V")
	public static void renderHeadHints(@OriginalArg(0) int viewWidth, @OriginalArg(1) int viewX, @OriginalArg(2) int viewHeight, @OriginalArg(4) int zoomFactor, @OriginalArg(5) int viewDepth, @OriginalArg(6) int viewY) {
		@Pc(3) int i = 0;
		@Pc(5) MapMarker[] markers = hintMapMarkers;
		while (markers.length > i) {
			@Pc(17) MapMarker marker = markers[i];
			if (marker != null && marker.type == 2) {
				ScriptRunner.projectToScreen(viewWidth >> 1, viewDepth, (marker.targetY - Camera.originY << 7) + marker.targetYFine, marker.targetHeight * 2, viewHeight >> 1, marker.targetXFine + (marker.targetX - Camera.originX << 7), zoomFactor);
				if (ScriptRunner.screenX > -1 && client.loop % 20 < 10) {
					Sprites.headhints[marker.arrowSpriteId].render(viewX + ScriptRunner.screenX - 12, viewY + -28 - -ScriptRunner.screenY);
				}
			}
			i++;
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IIIIIIIZ)Z")
	public static boolean renderMapTileFeatures(@OriginalArg(1) int tileX, @OriginalArg(2) int fogColor, @OriginalArg(3) int tileY, @OriginalArg(4) int waterColor, @OriginalArg(6) int plane) {
		@Pc(14) long locKey = SceneGraph.getWallKey(plane, tileX + 0, tileY);
		@Pc(28) int locRotation;
		@Pc(35) int locShape;
		@Pc(42) int locId;
		@Pc(46) LocType locType;
		@Pc(65) int wallColor;
		@Pc(75) int[] pixels;
		@Pc(90) int pixelOffset;
		if (locKey != 0L) {
			locRotation = (int) locKey >> 20 & 0x3;
			locShape = (int) locKey >> 14 & 0x1F;
			locId = Integer.MAX_VALUE & (int) (locKey >>> 32);
			locType = LocTypeList.get(locId);
			if (locType.mapscene == -1) {
				wallColor = fogColor;
				if (locKey > 0L) {
					wallColor = waterColor;
				}
				pixels = SoftwareRaster.pixels;
				pixelOffset = (52736 - tileY * 512) * 4 + tileX * 4 + 24624;
				if (locShape == 0 || locShape == 2) {
					if (locRotation == 0) {
						pixels[pixelOffset] = wallColor;
						pixels[pixelOffset + 512] = wallColor;
						pixels[pixelOffset + 1024] = wallColor;
						pixels[pixelOffset + 1536] = wallColor;
					} else if (locRotation == 1) {
						pixels[pixelOffset] = wallColor;
						pixels[pixelOffset + 1] = wallColor;
						pixels[pixelOffset + 2] = wallColor;
						pixels[pixelOffset + 3] = wallColor;
					} else if (locRotation == 2) {
						pixels[pixelOffset + 3] = wallColor;
						pixels[pixelOffset + 3 + 512] = wallColor;
						pixels[pixelOffset + 3 + 1024] = wallColor;
						pixels[pixelOffset + 3 + 1536] = wallColor;
					} else if (locRotation == 3) {
						pixels[pixelOffset + 1536] = wallColor;
						pixels[pixelOffset + 1536 + 1] = wallColor;
						pixels[pixelOffset + 1538] = wallColor;
						pixels[pixelOffset + 3 + 1536] = wallColor;
					}
				}
				if (locShape == 3) {
					if (locRotation == 0) {
						pixels[pixelOffset] = wallColor;
					} else if (locRotation == 1) {
						pixels[pixelOffset + 3] = wallColor;
					} else if (locRotation == 2) {
						pixels[pixelOffset + 3 + 1536] = wallColor;
					} else if (locRotation == 3) {
						pixels[pixelOffset + 1536] = wallColor;
					}
				}
				if (locShape == 2) {
					if (locRotation == 3) {
						pixels[pixelOffset] = wallColor;
						pixels[pixelOffset + 512] = wallColor;
						pixels[pixelOffset + 1024] = wallColor;
						pixels[pixelOffset + 1536] = wallColor;
					} else if (locRotation == 0) {
						pixels[pixelOffset] = wallColor;
						pixels[pixelOffset + 1] = wallColor;
						pixels[pixelOffset + 2] = wallColor;
						pixels[pixelOffset + 3] = wallColor;
					} else if (locRotation == 1) {
						pixels[pixelOffset + 3] = wallColor;
						pixels[pixelOffset + 512 + 3] = wallColor;
						pixels[pixelOffset + 1024 + 3] = wallColor;
						pixels[pixelOffset + 1536 + 3] = wallColor;
					} else if (locRotation == 2) {
						pixels[pixelOffset + 1536] = wallColor;
						pixels[pixelOffset + 1536 + 1] = wallColor;
						pixels[pixelOffset + 1536 + 2] = wallColor;
						pixels[pixelOffset + 1539] = wallColor;
					}
				}
			} else if (!renderScenery(tileX, locType, tileY, locRotation)) {
				return false;
			}
		}
		locKey = SceneGraph.getSceneryKey(plane, tileX + 0, tileY);
		if (locKey != 0L) {
			locRotation = (int) locKey >> 20 & 0x3;
			locShape = (int) locKey >> 14 & 0x1F;
			locId = (int) (locKey >>> 32) & Integer.MAX_VALUE;
			locType = LocTypeList.get(locId);
			if (locType.mapscene == -1) {
				if (locShape == 9) {
					wallColor = 15658734;
					if (locKey > 0L) {
						wallColor = 15597568;
					}
					pixelOffset = tileX * 4 + (103 - tileY) * 2048 + 24624;
					pixels = SoftwareRaster.pixels;
					if (locRotation == 0 || locRotation == 2) {
						pixels[pixelOffset + 1536] = wallColor;
						pixels[pixelOffset + 1025] = wallColor;
						pixels[pixelOffset + 512 + 2] = wallColor;
						pixels[pixelOffset + 3] = wallColor;
					} else {
						pixels[pixelOffset] = wallColor;
						pixels[pixelOffset + 512 + 1] = wallColor;
						pixels[pixelOffset + 1024 + 2] = wallColor;
						pixels[pixelOffset + 1536 + 3] = wallColor;
					}
				}
			} else if (!renderScenery(tileX, locType, tileY, locRotation)) {
				return false;
			}
		}
		locKey = SceneGraph.getGroundDecorKey(plane, tileX + 0, tileY);
		if (locKey != 0L) {
			locRotation = (int) locKey >> 20 & 0x3;
			locShape = (int) (locKey >>> 32) & Integer.MAX_VALUE;
			@Pc(586) LocType decorLocType = LocTypeList.get(locShape);
			return decorLocType.mapscene == -1 || renderScenery(tileX, decorLocType, tileY, locRotation);
		}
		return true;
	}
}
