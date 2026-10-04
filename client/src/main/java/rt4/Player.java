package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!e")
public final class Player extends PathingEntity {

	@OriginalMember(owner = "client!mj", name = "d", descriptor = "[Lclient!na;")
	public static final JagString[] options = new JagString[8];
	@OriginalMember(owner = "client!pg", name = "ab", descriptor = "[I")
	public static final int[] cursors = new int[8];
	@OriginalMember(owner = "client!a", name = "f", descriptor = "[Z")
	public static final boolean[] secondaryOptions = new boolean[8];
	@OriginalMember(owner = "client!pa", name = "P", descriptor = "Lclient!na;")
	public static JagString password = JagString.EMPTY;
	@OriginalMember(owner = "client!pa", name = "S", descriptor = "Lclient!na;")
	public static JagString usernameInput = JagString.EMPTY;
	@OriginalMember(owner = "client!ba", name = "w", descriptor = "I")
	public static int inTutorialIsland = 0;
	@OriginalMember(owner = "client!ee", name = "b", descriptor = "I")
	public static int plane;
	@OriginalMember(owner = "client!bb", name = "E", descriptor = "I")
	public static int runEnergy = 0;
	@OriginalMember(owner = "client!ug", name = "o", descriptor = "I")
	public static int weight = 0;
	@OriginalMember(owner = "client!jl", name = "H", descriptor = "I")
	public static int worldId = -1;
	@OriginalMember(owner = "client!em", name = "B", descriptor = "I")
	public static int rebootTimer = 0;
	@OriginalMember(owner = "client!hm", name = "gb", descriptor = "J")
	public static long name37;
	@OriginalMember(owner = "client!sm", name = "k", descriptor = "Lsignlink!im;")
	public static PrivilegedRequest lastLogAddress;
	@OriginalMember(owner = "client!ib", name = "l", descriptor = "I")
	public static int glPaddingCount = 0;
	@OriginalMember(owner = "client!ea", name = "r", descriptor = "[[B")
	public static byte[][] glPaddingBuffers;
	@OriginalMember(owner = "client!e", name = "Bc", descriptor = "Lclient!hh;")
	public PlayerAppearance appearance;

	@OriginalMember(owner = "client!e", name = "Mc", descriptor = "Lclient!na;")
	public JagString username;

	@OriginalMember(owner = "client!e", name = "tc", descriptor = "I")
	public int prayerHeadIcon = -1;

	@OriginalMember(owner = "client!e", name = "wc", descriptor = "I")
	private int titleIndex = 0;

	@OriginalMember(owner = "client!e", name = "uc", descriptor = "I")
	public int team = 0;

	@OriginalMember(owner = "client!e", name = "yc", descriptor = "I")
	public int combatLevel = 0;

	@OriginalMember(owner = "client!e", name = "Fc", descriptor = "I")
	public int combatLevelWithSummoning = 0;

	@OriginalMember(owner = "client!e", name = "Cc", descriptor = "I")
	public int walkSound = -1;

	@OriginalMember(owner = "client!e", name = "qc", descriptor = "I")
	public int idleSound = -1;

	@OriginalMember(owner = "client!e", name = "Pc", descriptor = "I")
	public int soundRadius = 0;

	@OriginalMember(owner = "client!e", name = "Hc", descriptor = "I")
	public int crawlSound = -1;

	@OriginalMember(owner = "client!e", name = "Sc", descriptor = "I")
	public int combatRange = -1;

	@OriginalMember(owner = "client!e", name = "Oc", descriptor = "Z")
	public boolean lowDetail = false;

	@OriginalMember(owner = "client!e", name = "Xc", descriptor = "I")
	public int skill = 0;

	@OriginalMember(owner = "client!e", name = "Vc", descriptor = "I")
	public int skullHeadIcon = -1;

	@OriginalMember(owner = "client!e", name = "Wc", descriptor = "I")
	public int runSound = -1;

	@OriginalMember(owner = "client!pa", name = "a", descriptor = "(IIILclient!e;)V")
	public static void animate(@OriginalArg(1) int delay, @OriginalArg(2) int seqId, @OriginalArg(3) Player player) {
		if (seqId == player.seqId && seqId != -1) {
			@Pc(89) SeqType seqType = SeqTypeList.get(seqId);
			@Pc(92) int exactmove = seqType.exactmove;
			if (exactmove == 1) {
				player.seqDelay = delay;
				player.seqDelayClock = 0;
				player.seqNextFrame = 1;
				player.seqFrame = 0;
				player.seqReplayCount = 0;
				SoundPlayer.playSeqSound(player.yFine, seqType, player.xFine, PlayerList.self == player, player.seqFrame);
			}
			if (exactmove == 2) {
				player.seqReplayCount = 0;
			}
		} else if (seqId == -1 || player.seqId == -1 || SeqTypeList.get(seqId).priority >= SeqTypeList.get(player.seqId).priority) {
			player.seqNextFrame = 1;
			player.seqFrame = 0;
			player.seqDelay = delay;
			player.seqMovementSteps = player.movementQueueSize;
			player.seqReplayCount = 0;
			player.seqDelayClock = 0;
			player.seqId = seqId;
			if (player.seqId != -1) {
				SoundPlayer.playSeqSound(player.yFine, SeqTypeList.get(player.seqId), player.xFine, player == PlayerList.self, player.seqFrame);
			}
		}
	}

	@OriginalMember(owner = "client!ci", name = "a", descriptor = "([I[ILclient!e;B[I)V")
	public static void applySlotAnimations(@OriginalArg(0) int[] delays, @OriginalArg(1) int[] seqIds, @OriginalArg(2) Player player, @OriginalArg(4) int[] masks) {
		for (@Pc(7) int i = 0; i < seqIds.length; i++) {
			@Pc(20) int seqId = seqIds[i];
			@Pc(24) int mask = masks[i];
			@Pc(28) int delay = delays[i];
			@Pc(30) int slot = 0;
			while (mask != 0 && slot < player.slotAnimations.length) {
				if ((mask & 0x1) != 0) {
					if (seqId == -1) {
						player.slotAnimations[slot] = null;
					} else {
						@Pc(68) SeqType seqType = SeqTypeList.get(seqId);
						@Pc(71) int exactmove = seqType.exactmove;
						@Pc(76) SlotAnimation slotAnim = player.slotAnimations[slot];
						if (slotAnim != null) {
							if (seqId == slotAnim.seqId) {
								if (exactmove == 0) {
									slotAnim = player.slotAnimations[slot] = null;
								} else if (exactmove == 1) {
									slotAnim.replayCount = 0;
									slotAnim.nextFrame = 1;
									slotAnim.currentFrame = 0;
									slotAnim.startDelay = delay;
									slotAnim.delayClock = 0;
									SoundPlayer.playSeqSound(player.yFine, seqType, player.xFine, player == PlayerList.self, 0);
								} else if (exactmove == 2) {
									slotAnim.replayCount = 0;
								}
							} else if (seqType.priority >= SeqTypeList.get(slotAnim.seqId).priority) {
								slotAnim = player.slotAnimations[slot] = null;
							}
						}
						if (slotAnim == null) {
							slotAnim = player.slotAnimations[slot] = new SlotAnimation();
							slotAnim.seqId = seqId;
							slotAnim.nextFrame = 1;
							slotAnim.delayClock = 0;
							slotAnim.startDelay = delay;
							slotAnim.currentFrame = 0;
							slotAnim.replayCount = 0;
							SoundPlayer.playSeqSound(player.yFine, seqType, player.xFine, player == PlayerList.self, 0);
						}
					}
				}
				slot++;
				mask >>>= 0x1;
			}
		}
	}

	@OriginalMember(owner = "client!la", name = "a", descriptor = "(ILclient!e;)I")
	public static int getSound(@OriginalArg(1) Player player) {
		@Pc(14) int sound = player.walkSound;
		@Pc(18) BasType basType = player.getBasType();
		if (basType.idleAnimationId == player.movementSeqId) {
			sound = player.idleSound;
		} else if (basType.runAnimationId == player.movementSeqId || player.movementSeqId == basType.runFullTurnAnimationId || player.movementSeqId == basType.runCWTurnAnimationId || basType.runCCWTurnAnimationId == player.movementSeqId) {
			sound = player.runSound;
		} else if (player.movementSeqId == basType.slowWalkAnimationId || player.movementSeqId == basType.slowWalkFullTurnAnimationId || player.movementSeqId == basType.slowWalkCWTurnAnimationId || player.movementSeqId == basType.slowWalkCCWTurnAnimationId) {
			sound = player.crawlSound;
		}
		return sound;
	}

	@OriginalMember(owner = "client!um", name = "a", descriptor = "(Z)V")
	public static void setTutorialIsland() {
		inTutorialIsland = 0;
		@Pc(17) int tileX = Camera.originX + (PlayerList.self.xFine >> 7);
		@Pc(25) int tileY = (PlayerList.self.yFine >> 7) + Camera.originY;
		if (tileX >= 3053 && tileX <= 3156 && tileY >= 3056 && tileY <= 3136) {
			inTutorialIsland = 1;
		}
		if (tileX >= 3072 && tileX <= 3118 && tileY >= 9492 && tileY <= 9535) {
			inTutorialIsland = 1;
		}
		if (inTutorialIsland == 1 && tileX >= 3139 && tileX <= 3199 && tileY >= 3008 && tileY <= 3062) {
			inTutorialIsland = 0;
		}
	}

	@OriginalMember(owner = "client!rg", name = "a", descriptor = "(Lclient!e;I)V")
	public static void updateAreaSound(@OriginalArg(0) Player player) {
		@Pc(12) AreaSound areaSound = (AreaSound) AreaSoundManager.playerSounds.get(player.username.encode37());
		if (areaSound == null) {
			AreaSoundManager.add(player.movementQueueY[0], null, 0, null, player.movementQueueX[0], plane, player);
		} else {
			areaSound.update();
		}
	}

	@OriginalMember(owner = "client!bf", name = "c", descriptor = "(I)V")
	public static void invalidateGlModels() {
		if (!GlRenderer.enabled || LoginManager.glModelsInvalidated) {
			return;
		}
		@Pc(14) Tile[][][] allTiles = SceneGraph.tiles;
		for (@Pc(22) int p = 0; p < allTiles.length; p++) {
			@Pc(30) Tile[][] planeTiles = allTiles[p];
			for (@Pc(32) int x = 0; x < planeTiles.length; x++) {
				for (@Pc(42) int y = 0; y < planeTiles[x].length; y++) {
					@Pc(54) Tile tile = planeTiles[x][y];
					if (tile != null) {
						@Pc(71) GlModel glModel;
						if (tile.groundDecor != null && tile.groundDecor.entity instanceof GlModel) {
							glModel = (GlModel) tile.groundDecor.entity;
							if ((tile.groundDecor.key & Long.MIN_VALUE) == 0L) {
								glModel.uploadBuffers(false, true, true, false, true, true);
							} else {
								glModel.uploadBuffers(true, true, true, true, true, true);
							}
						}
						if (tile.wallDecor != null) {
							if (tile.wallDecor.primary instanceof GlModel) {
								glModel = (GlModel) tile.wallDecor.primary;
								if ((tile.wallDecor.key & Long.MIN_VALUE) == 0L) {
									glModel.uploadBuffers(false, true, true, false, true, true);
								} else {
									glModel.uploadBuffers(true, true, true, true, true, true);
								}
							}
							if (tile.wallDecor.secondary instanceof GlModel) {
								glModel = (GlModel) tile.wallDecor.secondary;
								if ((Long.MIN_VALUE & tile.wallDecor.key) == 0L) {
									glModel.uploadBuffers(false, true, true, false, true, true);
								} else {
									glModel.uploadBuffers(true, true, true, true, true, true);
								}
							}
						}
						if (tile.wall != null) {
							if (tile.wall.primary instanceof GlModel) {
								glModel = (GlModel) tile.wall.primary;
								if ((tile.wall.key & Long.MIN_VALUE) == 0L) {
									glModel.uploadBuffers(false, true, true, false, true, true);
								} else {
									glModel.uploadBuffers(true, true, true, true, true, true);
								}
							}
							if (tile.wall.secondary instanceof GlModel) {
								glModel = (GlModel) tile.wall.secondary;
								if ((Long.MIN_VALUE & tile.wall.key) == 0L) {
									glModel.uploadBuffers(false, true, true, false, true, true);
								} else {
									glModel.uploadBuffers(true, true, true, true, true, true);
								}
							}
						}
						for (@Pc(270) int i = 0; i < tile.sceneryLen; i++) {
							if (tile.scenery[i].entity instanceof GlModel) {
								@Pc(293) GlModel sceneryModel = (GlModel) tile.scenery[i].entity;
								if ((Long.MIN_VALUE & tile.scenery[i].key) == 0L) {
									sceneryModel.uploadBuffers(false, true, true, false, true, true);
								} else {
									sceneryModel.uploadBuffers(true, true, true, true, true, true);
								}
							}
						}
					}
				}
			}
		}
		LoginManager.glModelsInvalidated = true;
	}

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(IIIIB)V")
	public static void renderCross(@OriginalArg(0) int unused0, @OriginalArg(1) int unused1, @OriginalArg(2) int unused2, @OriginalArg(3) int unused3) {
		if (Cross.type == 1) {
			Sprites.crosses[Cross.milliseconds / 100].render(Cross.x - 8, Cross.y + -8);
		}
		if (Cross.type == 2) {
			Sprites.crosses[Cross.milliseconds / 100 + 4].render(Cross.x - 8, Cross.y + -8);
		}
		setTutorialIsland();
	}

	@OriginalMember(owner = "client!e", name = "c", descriptor = "(B)I")
	@Override
	public final int getSize() {
		return this.appearance == null || this.appearance.npcId == -1 ? super.getSize() : NpcTypeList.get(this.appearance.npcId).size;
	}

	@OriginalMember(owner = "client!e", name = "b", descriptor = "(I)I")
	@Override
	public final int getBasId() {
		return this.basTypeId;
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(ILclient!wa;)V")
	public final void decodeAppearance(@OriginalArg(1) Buffer buf) {
		buf.offset = 0;
		@Pc(20) int flags = buf.g1();
		@Pc(22) int npcId = -1;
		@Pc(26) int gender = flags & 0x1;
		@Pc(37) boolean showSkillLevel = (flags & 0x4) != 0;
		@Pc(41) int prevSize = super.getSize();
		@Pc(44) int[] identikit = new int[12];
		this.setSize((flags >> 3 & 0x7) + 1);
		this.titleIndex = flags >> 6 & 0x3;
		this.xFine += (this.getSize() - prevSize) * 64;
		this.yFine += (this.getSize() - prevSize) * 64;
		this.skullHeadIcon = buf.g1b();
		this.prayerHeadIcon = buf.g1b();
		this.team = 0;
		@Pc(111) int hi;
		@Pc(127) int lo;
		@Pc(134) int combined;
		@Pc(175) int teamId;
		for (@Pc(102) int i = 0; i < 12; i++) {
			hi = buf.g1();
			if (hi == 0) {
				identikit[i] = 0;
			} else {
				lo = buf.g1();
				combined = (hi << 8) + lo;
				if (i == 0 && combined == 65535) {
					npcId = buf.g2();
					this.team = buf.g1();
					break;
				}
				if (combined >= 32768) {
					combined = Equipment.objIds[combined - 32768];
					identikit[i] = combined | 0x40000000;
					teamId = ObjTypeList.get(combined).team;
					if (teamId != 0) {
						this.team = teamId;
					}
				} else {
					identikit[i] = combined - 256 | Integer.MIN_VALUE;
				}
			}
		}
		@Pc(197) int[] colors = new int[5];
		for (hi = 0; hi < 5; hi++) {
			lo = buf.g1();
			if (lo < 0 || lo >= PlayerAppearance.destinationBodyColors[hi].length) {
				lo = 0;
			}
			colors[hi] = lo;
		}
		this.basTypeId = buf.g2();
		@Pc(236) long name37 = buf.g8();
		this.username = Base37.decode37(name37).toTitleCase();
		this.combatLevel = buf.g1();
		if (showSkillLevel) {
			this.skill = buf.g2();
			this.combatLevelWithSummoning = this.combatLevel;
			this.combatRange = -1;
		} else {
			this.skill = 0;
			this.combatLevelWithSummoning = buf.g1();
			this.combatRange = buf.g1();
			if (this.combatRange == 255) {
				this.combatRange = -1;
			}
		}
		combined = this.soundRadius;
		this.soundRadius = buf.g1();
		if (this.soundRadius == 0) {
			AreaSoundManager.remove(this);
		} else {
			@Pc(309) int oldCrawlSound = this.crawlSound;
			@Pc(312) int oldWalkSound = this.walkSound;
			@Pc(315) int oldRunSound = this.runSound;
			teamId = this.idleSound;
			this.idleSound = buf.g2();
			this.crawlSound = buf.g2();
			this.walkSound = buf.g2();
			this.runSound = buf.g2();
			if (this.soundRadius != combined || this.idleSound != teamId || this.crawlSound != oldCrawlSound || oldWalkSound != this.walkSound || this.runSound != oldRunSound) {
				updateAreaSound(this);
			}
		}
		if (this.appearance == null) {
			this.appearance = new PlayerAppearance();
		}
		teamId = this.appearance.npcId;
		this.appearance.set(colors, npcId, gender == 1, identikit, this.basTypeId);
		if (teamId != npcId) {
			this.xFine = this.movementQueueX[0] * 128 + this.getSize() * 64;
			this.yFine = this.movementQueueY[0] * 128 + this.getSize() * 64;
		}
		if (this.particleSystem != null) {
			this.particleSystem.update();
		}
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinYaw, @OriginalArg(2) int cosYaw, @OriginalArg(3) int sinPitch, @OriginalArg(4) int cosPitch, @OriginalArg(5) int cameraX, @OriginalArg(6) int cameraY, @OriginalArg(7) int cameraZ, @OriginalArg(8) long key, @OriginalArg(9) int brightness, @OriginalArg(10) ParticleSystem particles) {
		if (this.appearance == null) {
			return;
		}
		@Pc(25) SeqType seq = this.seqId != -1 && this.seqDelay == 0 ? SeqTypeList.get(this.seqId) : null;
		@Pc(54) SeqType movementSeq = this.movementSeqId == -1 || this.lowDetail || this.movementSeqId == this.getBasType().idleAnimationId && seq != null ? null : SeqTypeList.get(this.movementSeqId);
		@Pc(76) Model bodyModel = this.appearance.getBodyModel(this.slotAnimations, this.seqNextFrame, movementSeq, seq, this.movementSeqDelayClock, this.movementSeqNextFrame, this.seqDelayClock, this.seqFrame, this.movementSeqFrame);
		@Pc(79) int cacheSize = PlayerAppearance.getModelCacheSize();
		if (GlRenderer.enabled && GameShell.maxMemory < 96 && cacheSize > 50) {
			invalidateGlModels();
		}
		@Pc(102) int paddingTarget;
		if (client.modeWhat != 0 && cacheSize < 50) {
			paddingTarget = 50 - cacheSize;
			while (glPaddingCount < paddingTarget) {
				glPaddingBuffers[glPaddingCount] = new byte[102400];
				glPaddingCount++;
			}
			while (glPaddingCount > paddingTarget) {
				glPaddingCount--;
				glPaddingBuffers[glPaddingCount] = null;
			}
		}
		if (bodyModel == null) {
			return;
		}
		this.minY = bodyModel.getMinY();
		@Pc(184) Model shadowModel;
		if (Preferences.characterShadowsOn && (this.appearance.npcId == -1 || NpcTypeList.get(this.appearance.npcId).hasshadow)) {
			shadowModel = ShadowModelList.getOrCreateShadow(160, this.seqStretches, movementSeq == null ? seq : movementSeq, this.xFine, 0, this.yFine, 0, 1, bodyModel, yaw, movementSeq == null ? this.seqFrame : this.movementSeqFrame, this.tileHeight, 240);
			if (GlRenderer.enabled) {
				@Pc(188) float projDist = GlRenderer.getProjectionDistance();
				@Pc(190) float depthBias = GlRenderer.getDepthBias();
				GlRenderer.disableDepthMask();
				GlRenderer.setDepthBias(projDist, depthBias - 150.0F);
				shadowModel.render(0, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, -1L, brightness, null);
				GlRenderer.enableDepthMask();
				GlRenderer.setDepthBias(projDist, depthBias);
			} else {
				shadowModel.render(0, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, -1L, brightness, null);
			}
		}
		if (PlayerList.self == this) {
			for (paddingTarget = MiniMap.hintMapMarkers.length - 1; paddingTarget >= 0; paddingTarget--) {
				@Pc(245) MapMarker marker = MiniMap.hintMapMarkers[paddingTarget];
				if (marker != null && marker.playerModelId != -1) {
					@Pc(291) int dx;
					@Pc(302) int dy;
					if (marker.type == 1 && marker.actorTargetId >= 0 && NpcList.npcs.length > marker.actorTargetId) {
						@Pc(278) Npc npc = NpcList.npcs[marker.actorTargetId];
						if (npc != null) {
							dx = npc.xFine / 32 - PlayerList.self.xFine / 32;
							dy = npc.yFine / 32 - PlayerList.self.yFine / 32;
							this.renderHintArrow(null, dy, bodyModel, dx, cameraX, brightness, yaw, cameraZ, cosPitch, sinPitch, sinYaw, marker.playerModelId, cosYaw, cameraY);
						}
					}
					if (marker.type == 2) {
						@Pc(340) int targetDx = (marker.targetX - Camera.originX) * 4 + 2 - PlayerList.self.xFine / 32;
						dx = (marker.targetY - Camera.originY) * 4 + 2 - PlayerList.self.yFine / 32;
						this.renderHintArrow(null, dx, bodyModel, targetDx, cameraX, brightness, yaw, cameraZ, cosPitch, sinPitch, sinYaw, marker.playerModelId, cosYaw, cameraY);
					}
					if (marker.type == 10 && marker.actorTargetId >= 0 && PlayerList.players.length > marker.actorTargetId) {
						@Pc(395) Player target = PlayerList.players[marker.actorTargetId];
						if (target != null) {
							dx = target.xFine / 32 - PlayerList.self.xFine / 32;
							dy = target.yFine / 32 - PlayerList.self.yFine / 32;
							this.renderHintArrow(null, dy, bodyModel, dx, cameraX, brightness, yaw, cameraZ, cosPitch, sinPitch, sinYaw, marker.playerModelId, cosYaw, cameraY);
						}
					}
				}
			}
		}
		this.applyBodyLean(bodyModel);
		this.alignToTerrain(bodyModel, yaw);
		shadowModel = null;
		if (!this.lowDetail && this.spotAnimId != -1 && this.spotAnimFrame != -1) {
			@Pc(471) SpotAnimType spotAnimType = SpotAnimTypeList.get(this.spotAnimId);
			shadowModel = spotAnimType.constructModel(this.spotAnimNextFrame, this.spotAnimFrame, this.spotAnimDelayClock);
			if (shadowModel != null) {
				shadowModel.translate(0, -this.spotAnimY, 0);
				if (spotAnimType.alignToTerrain) {
					if (PathingEntity.terrainPitchAngle != 0) {
						shadowModel.rotateX(PathingEntity.terrainPitchAngle);
					}
					if (PathingEntity.terrainRollAngle != 0) {
						shadowModel.rotateZ(PathingEntity.terrainRollAngle);
					}
					if (PathingEntity.terrainYOffset != 0) {
						shadowModel.translate(0, PathingEntity.terrainYOffset, 0);
					}
				}
			}
		}
		@Pc(515) Model attachmentModel = null;
		if (!this.lowDetail && this.attachment != null) {
			if (client.loop >= this.attachmentResetAt) {
				this.attachment = null;
			}
			if (this.attachmentSetAt <= client.loop && this.attachmentResetAt > client.loop) {
				if (this.attachment instanceof Loc) {
					attachmentModel = (Model) ((Loc) this.attachment).getEntity();
				} else {
					attachmentModel = (Model) this.attachment;
				}
				attachmentModel.translate(this.attachmentXFine - this.xFine, this.attachmentY + -this.tileHeight, this.attachmentYFine - this.yFine);
				if (this.targetAngle == 512) {
					attachmentModel.rotateClockwise();
				} else if (this.targetAngle == 1024) {
					attachmentModel.rotate180();
				} else if (this.targetAngle == 1536) {
					attachmentModel.rotateCounterClockwise();
				}
			}
		}
		if (GlRenderer.enabled) {
			bodyModel.pickable = true;
			bodyModel.render(yaw, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, key, brightness, this.particleSystem);
			if (shadowModel != null) {
				shadowModel.pickable = true;
				shadowModel.render(yaw, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, key, brightness, this.particleSystem);
			}
		} else {
			if (shadowModel != null) {
				bodyModel = ((SoftwareModel) bodyModel).mergeWith(shadowModel);
			}
			if (attachmentModel != null) {
				bodyModel = ((SoftwareModel) bodyModel).mergeWith(attachmentModel);
			}
			bodyModel.pickable = true;
			bodyModel.render(yaw, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, key, brightness, this.particleSystem);
		}
		if (attachmentModel == null) {
			return;
		}
		if (this.targetAngle == 512) {
			attachmentModel.rotateCounterClockwise();
		} else if (this.targetAngle == 1024) {
			attachmentModel.rotate180();
		} else if (this.targetAngle == 1536) {
			attachmentModel.rotateClockwise();
		}
		attachmentModel.translate(this.xFine - this.attachmentXFine, -this.attachmentY + this.tileHeight, this.yFine - this.attachmentYFine);
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(Lclient!ga;ILclient!ak;IIIIIIIIIIII)V")
	private void renderHintArrow(@OriginalArg(0) ParticleSystem particles, @OriginalArg(1) int dy, @OriginalArg(2) Model bodyModel, @OriginalArg(3) int dx, @OriginalArg(4) int cameraX, @OriginalArg(5) int brightness, @OriginalArg(7) int yaw, @OriginalArg(8) int cameraZ, @OriginalArg(9) int cosPitch, @OriginalArg(10) int sinPitch, @OriginalArg(11) int sinYaw, @OriginalArg(12) int modelId, @OriginalArg(13) int cosYaw, @OriginalArg(14) int cameraY) {
		@Pc(12) int distSq = dx * dx + dy * dy;
		if (distSq < 16 || distSq > 360000) {
			return;
		}
		@Pc(34) int angle = (int) (Math.atan2(dx, dy) * 325.949D) & 0x7FF;
		@Pc(46) Model arrowModel = HintArrowManager.getModel(angle, this.yFine, modelId, this.xFine, bodyModel, this.tileHeight);
		if (arrowModel == null) {
			return;
		}
		if (!GlRenderer.enabled) {
			arrowModel.render(0, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, -1L, brightness, particles);
			return;
		}
		@Pc(52) float projDist = GlRenderer.getProjectionDistance();
		@Pc(54) float depthBias = GlRenderer.getDepthBias();
		GlRenderer.disableDepthMask();
		GlRenderer.setDepthBias(projDist, depthBias - 150.0F);
		arrowModel.render(0, sinYaw, cosYaw, sinPitch, cosPitch, cameraX, cameraY, cameraZ, -1L, brightness, particles);
		GlRenderer.enableDepthMask();
		GlRenderer.setDepthBias(projDist, depthBias);
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(B)Z")
	@Override
	public final boolean isVisible() {
		return this.appearance != null;
	}

	@OriginalMember(owner = "client!e", name = "e", descriptor = "(I)Lclient!na;")
	public final JagString getName() {
		@Pc(2) JagString name = this.username;
		if (PlayerList.playerNames != null) {
			name = JagString.concatenate(new JagString[]{PlayerList.playerNames[this.titleIndex], name});
		}
		if (PlayerList.playerNames2 != null) {
			name = JagString.concatenate(new JagString[]{name, PlayerList.playerNames2[this.titleIndex]});
		}
		return name;
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int yaw, @OriginalArg(1) int sinYaw, @OriginalArg(2) int cosYaw, @OriginalArg(3) int sinPitch, @OriginalArg(4) int cosPitch) {
	}

	@OriginalMember(owner = "client!e", name = "a", descriptor = "(BIZI)V")
	public final void teleport(@OriginalArg(1) int tileX, @OriginalArg(2) boolean teleporting, @OriginalArg(3) int tileY) {
		super.teleport(this.getSize(), tileX, tileY, teleporting);
	}

	@OriginalMember(owner = "client!e", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() {
	}

	@OriginalMember(owner = "client!e", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.minY;
	}
}
