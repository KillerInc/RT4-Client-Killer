package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!dc")
public final class Loc extends Entity {

	@OriginalMember(owner = "client!kf", name = "h", descriptor = "[I")
	public static final int[] LAYERS = new int[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};

	@OriginalMember(owner = "client!ci", name = "q", descriptor = "Lclient!ek;")
	public static SoftwareIndexedSprite sprite1 = null;

	@OriginalMember(owner = "client!dc", name = "U", descriptor = "Lclient!ga;")
	private ParticleSystem particles;

	@OriginalMember(owner = "client!dc", name = "x", descriptor = "I")
	private int shadowX = 0;

	@OriginalMember(owner = "client!dc", name = "t", descriptor = "Z")
	private boolean hasMultiLoc = false;

	@OriginalMember(owner = "client!dc", name = "s", descriptor = "I")
	private int shadowElevation = 0;

	@OriginalMember(owner = "client!dc", name = "B", descriptor = "Z")
	private boolean shadowDirty = true;

	@OriginalMember(owner = "client!dc", name = "Q", descriptor = "Lclient!ek;")
	private SoftwareIndexedSprite sprite2 = null;

	@OriginalMember(owner = "client!dc", name = "T", descriptor = "I")
	private final int MIN_Y = -32768;

	@OriginalMember(owner = "client!dc", name = "eb", descriptor = "I")
	private int shadowY = 0;

	@OriginalMember(owner = "client!dc", name = "gb", descriptor = "I")
	private int lastLocTypeId = -1;

	@OriginalMember(owner = "client!dc", name = "ib", descriptor = "I")
	private int lastSeqFrame = -1;

	@OriginalMember(owner = "client!dc", name = "H", descriptor = "I")
	private final int level;

	@OriginalMember(owner = "client!dc", name = "w", descriptor = "I")
	private final int orientation;

	@OriginalMember(owner = "client!dc", name = "N", descriptor = "I")
	private final int tileX;

	@OriginalMember(owner = "client!dc", name = "L", descriptor = "I")
	private final int shape;

	@OriginalMember(owner = "client!dc", name = "C", descriptor = "I")
	private final int locId;

	@OriginalMember(owner = "client!dc", name = "D", descriptor = "I")
	private final int tileY;

	@OriginalMember(owner = "client!dc", name = "u", descriptor = "Lclient!tk;")
	private SeqType seq;

	@OriginalMember(owner = "client!dc", name = "y", descriptor = "I")
	private int seqFrame;

	@OriginalMember(owner = "client!dc", name = "I", descriptor = "I")
	private int seqNextFrame;

	@OriginalMember(owner = "client!dc", name = "bb", descriptor = "I")
	private int seqFrameProgress;

	@OriginalMember(owner = "client!dc", name = "fb", descriptor = "I")
	private int seqStartLoop;

	@OriginalMember(owner = "client!dc", name = "<init>", descriptor = "(IIIIIIIZLclient!th;)V")
	public Loc(@OriginalArg(0) int locId, @OriginalArg(1) int shape, @OriginalArg(2) int orientation, @OriginalArg(3) int level, @OriginalArg(4) int tileX, @OriginalArg(5) int tileY, @OriginalArg(6) int seqId, @OriginalArg(7) boolean randomizeAnim, @OriginalArg(8) Entity previous) {
		this.level = level;
		this.orientation = orientation;
		this.tileX = tileX;
		this.shape = shape;
		this.locId = locId;
		this.tileY = tileY;
		@Pc(67) LocType locType;
		if (GlRenderer.enabled && previous != null) {
			if (previous instanceof Loc) {
				((Loc) previous).resetShadow();
			} else {
				locType = LocTypeList.get(this.locId);
				if (locType.multiLocs != null) {
					locType = locType.getMultiLoc();
				}
				if (locType != null) {
					buildStaticShadow(locType, 0, this.orientation, 0, this.shape, this.tileX, this.tileY, this.level);
				}
			}
		}
		if (seqId != -1) {
			this.seq = SeqTypeList.get(seqId);
			this.seqFrame = 0;
			if (this.seq.frames.length <= 1) {
				this.seqNextFrame = 0;
			} else {
				this.seqNextFrame = 1;
			}
			this.seqFrameProgress = 1;
			this.seqStartLoop = client.loop - 1;
			if (this.seq.exactmove == 0 && previous != null && previous instanceof Loc) {
				@Pc(142) Loc prevLoc = (Loc) previous;
				if (this.seq == prevLoc.seq) {
					this.seqFrame = prevLoc.seqFrame;
					this.seqStartLoop = prevLoc.seqStartLoop;
					this.seqFrameProgress = prevLoc.seqFrameProgress;
					this.seqNextFrame = prevLoc.seqNextFrame;
					return;
				}
			}
			if (randomizeAnim && this.seq.replayoff != -1) {
				this.seqFrame = (int) (Math.random() * (double) this.seq.frames.length);
				this.seqNextFrame = this.seqFrame + 1;
				if (this.seqNextFrame >= this.seq.frames.length) {
					this.seqNextFrame -= this.seq.replayoff;
					if (this.seqNextFrame < 0 || this.seqNextFrame >= this.seq.frames.length) {
						this.seqNextFrame = -1;
					}
				}
				this.seqFrameProgress = (int) (Math.random() * (double) this.seq.frameDelay[this.seqFrame]) + 1;
				this.seqStartLoop = client.loop - this.seqFrameProgress;
			}
		}
		if (GlRenderer.enabled && previous != null) {
			this.getOrBuildEntity(true);
		}
		if (previous == null) {
			locType = LocTypeList.get(this.locId);
			if (locType.multiLocs != null) {
				this.hasMultiLoc = true;
			}
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(Lclient!pb;BIIIIIII)V")
	public static void buildStaticShadow(@OriginalArg(0) LocType locType, @OriginalArg(2) int offsetY, @OriginalArg(3) int orientation, @OriginalArg(4) int offsetX, @OriginalArg(5) int shape, @OriginalArg(6) int tileX, @OriginalArg(7) int tileY, @OriginalArg(8) int level) {
		@Pc(5) int effectiveOrientation = orientation & 0x3;
		@Pc(28) int effectiveWidth;
		@Pc(31) int effectiveLength;
		if (effectiveOrientation == 1 || effectiveOrientation == 3) {
			effectiveWidth = locType.length;
			effectiveLength = locType.width;
		} else {
			effectiveLength = locType.length;
			effectiveWidth = locType.width;
		}
		@Pc(53) int sampleY2;
		@Pc(51) int sampleY1;
		if (tileY + effectiveLength > 104) {
			sampleY1 = tileY + 1;
			sampleY2 = tileY;
		} else {
			sampleY2 = tileY + (effectiveLength >> 1);
			sampleY1 = tileY + (effectiveLength + 1 >> 1);
		}
		@Pc(80) int centerFineX = (tileX << 7) + (effectiveWidth << 6);
		@Pc(88) int centerFineY = (tileY << 7) + (effectiveLength << 6);
		@Pc(96) int sampleX1;
		@Pc(100) int sampleX2;
		if (tileX + effectiveWidth > 104) {
			sampleX1 = tileX;
			sampleX2 = tileX + 1;
		} else {
			sampleX1 = tileX + (effectiveWidth >> 1);
			sampleX2 = (effectiveWidth + 1 >> 1) + tileX;
		}
		@Pc(120) int[][] heightmap = SceneGraph.tileHeights[level];
		@Pc(122) int elevation = 0;
		@Pc(148) int avgHeight = heightmap[sampleX1][sampleY1] + heightmap[sampleX1][sampleY2] + heightmap[sampleX2][sampleY2] + heightmap[sampleX2][sampleY1] >> 2;
		@Pc(158) int[][] groundHeightmap;
		if (level != 0) {
			groundHeightmap = SceneGraph.tileHeights[0];
			elevation = avgHeight - (groundHeightmap[sampleX1][sampleY1] + groundHeightmap[sampleX2][sampleY2] + groundHeightmap[sampleX1][sampleY2] + groundHeightmap[sampleX2][sampleY1] >> 2);
		}
		groundHeightmap = null;
		if (level < 3) {
			groundHeightmap = SceneGraph.tileHeights[level + 1];
		}
		@Pc(215) LocEntity locEntity = locType.getStaticEntity(orientation, centerFineX, heightmap, shape, avgHeight, groundHeightmap, false, null, true, centerFineY);
		ShadowManager.removeObjectShadow(locEntity.sprite, centerFineX - offsetX, elevation, centerFineY - offsetY);
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int pitch, @OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int centerFineX, @OriginalArg(4) int centerFineY) {
		if (GlRenderer.enabled) {
			this.getOrBuildEntity(true);
		} else {
			this.advanceAnimation(centerFineY, centerFineX);
		}
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int pitch, @OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int cameraX, @OriginalArg(4) int cameraY, @OriginalArg(5) int cameraZ, @OriginalArg(6) int sinPitch, @OriginalArg(7) int cosPitch, @OriginalArg(8) long uid, @OriginalArg(9) int key, @OriginalArg(10) ParticleSystem particleSystem) {
		@Pc(3) Entity entity = this.getEntity();
		if (entity != null) {
			entity.render(pitch, yaw, roll, cameraX, cameraY, cameraZ, sinPitch, cosPitch, uid, key, this.particles);
		}
	}

	@OriginalMember(owner = "client!dc", name = "c", descriptor = "(I)V")
	public final void resetShadow() {
		if (this.sprite2 != null) {
			ShadowManager.removeObjectShadow(this.sprite2, this.shadowX, this.shadowElevation, this.shadowY);
		}
		this.lastLocTypeId = -1;
		this.lastSeqFrame = -1;
		this.sprite2 = null;
	}

	@OriginalMember(owner = "client!dc", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() {
	}

	@OriginalMember(owner = "client!dc", name = "b", descriptor = "(III)V")
	private void advanceAnimation(@OriginalArg(0) int soundFineY, @OriginalArg(1) int soundFineX) {
		if (this.seq == null) {
			return;
		}
		@Pc(10) int elapsed = client.loop - this.seqStartLoop;
		if (elapsed > 100 && this.seq.replayoff > 0) {
			@Pc(29) int replayStart = this.seq.frames.length - this.seq.replayoff;
			while (this.seqFrame < replayStart && this.seq.frameDelay[this.seqFrame] < elapsed) {
				elapsed -= this.seq.frameDelay[this.seqFrame];
				this.seqFrame++;
			}
			if (this.seqFrame >= replayStart) {
				@Pc(77) int replayDuration = 0;
				for (@Pc(79) int i = replayStart; i < this.seq.frames.length; i++) {
					replayDuration += this.seq.frameDelay[i];
				}
				elapsed %= replayDuration;
			}
			this.seqNextFrame = this.seqFrame + 1;
			if (this.seqNextFrame >= this.seq.frames.length) {
				this.seqNextFrame -= this.seq.replayoff;
				if (this.seqNextFrame < 0 || this.seq.frames.length <= this.seqNextFrame) {
					this.seqNextFrame = -1;
				}
			}
		}
		while (elapsed > this.seq.frameDelay[this.seqFrame]) {
			SoundPlayer.playSeqSound(soundFineY, this.seq, soundFineX, false, this.seqFrame);
			elapsed -= this.seq.frameDelay[this.seqFrame];
			this.seqFrame++;
			if (this.seq.frames.length <= this.seqFrame) {
				this.seqFrame -= this.seq.replayoff;
				if (this.seqFrame < 0 || this.seq.frames.length <= this.seqFrame) {
					this.seq = null;
					break;
				}
			}
			this.seqNextFrame = this.seqFrame + 1;
			if (this.seq.frames.length <= this.seqNextFrame) {
				this.seqNextFrame -= this.seq.replayoff;
				if (this.seqNextFrame < 0 || this.seqNextFrame >= this.seq.frames.length) {
					this.seqNextFrame = -1;
				}
			}
		}
		this.seqFrameProgress = elapsed;
		this.seqStartLoop = client.loop - elapsed;
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(ZI)Lclient!th;")
	private Entity getOrBuildEntity(@OriginalArg(0) boolean updateShadow) {
		@Pc(12) boolean isUnderwater = SceneGraph.surfaceTileHeights != SceneGraph.tileHeights;
		@Pc(19) LocType locType = LocTypeList.get(this.locId);
		@Pc(22) int originalAnim = locType.anim;
		if (locType.multiLocs != null) {
			locType = locType.getMultiLoc();
		}
		if (locType == null) {
			if (GlRenderer.enabled && !isUnderwater) {
				this.resetShadow();
			}
			return null;
		}
		@Pc(69) int animId;
		if (client.game != 0 && this.hasMultiLoc && (this.seq == null || this.seq != null && this.seq.id != locType.anim)) {
			animId = locType.anim;
			if (locType.anim == -1) {
				animId = originalAnim;
			}
			if (animId == -1) {
				this.seq = null;
			} else {
				this.seq = SeqTypeList.get(animId);
			}
			if (this.seq != null) {
				if (locType.allowrandomizedanimation && this.seq.replayoff != -1) {
					this.seqFrame = (int) (Math.random() * (double) this.seq.frames.length);
					this.seqStartLoop -= (int) (Math.random() * (double) this.seq.frameDelay[this.seqFrame]);
				} else {
					this.seqFrame = 0;
					this.seqStartLoop = client.loop - 1;
				}
			}
		}
		animId = this.orientation & 0x3;
		@Pc(160) int effectiveWidth;
		@Pc(157) int effectiveLength;
		if (animId == 1 || animId == 3) {
			effectiveLength = locType.width;
			effectiveWidth = locType.length;
		} else {
			effectiveWidth = locType.width;
			effectiveLength = locType.length;
		}
		@Pc(178) int sampleX2 = this.tileX + (effectiveWidth + 1 >> 1);
		@Pc(185) int sampleX1 = (effectiveWidth >> 1) + this.tileX;
		@Pc(192) int sampleY1 = (effectiveLength >> 1) + this.tileY;
		@Pc(201) int sampleY2 = (effectiveLength + 1 >> 1) + this.tileY;
		this.advanceAnimation(sampleY1 * 128, sampleX1 * 128);
		@Pc(256) boolean needsShadowUpdate = !isUnderwater && locType.castshadow && (locType.id != this.lastLocTypeId || (this.seqFrame != this.lastSeqFrame || this.seq != null && (this.seq.updateShadows || SeqType.applyTweening) && this.seqFrame != this.seqNextFrame) && Preferences.sceneryShadowsType >= 2);
		if (updateShadow && !needsShadowUpdate) {
			return null;
		}
		@Pc(267) int[][] heightmap = SceneGraph.tileHeights[this.level];
		@Pc(293) int avgHeight = heightmap[sampleX2][sampleY2] + heightmap[sampleX1][sampleY2] + heightmap[sampleX1][sampleY1] + heightmap[sampleX2][sampleY1] >> 2;
		@Pc(302) int centerFineX = (effectiveWidth << 6) + (this.tileX << 7);
		@Pc(311) int centerFineY = (effectiveLength << 6) + (this.tileY << 7);
		@Pc(314) int[][] aboveHeightmap = null;
		if (isUnderwater) {
			aboveHeightmap = SceneGraph.surfaceTileHeights[0];
		} else if (this.level < 3) {
			aboveHeightmap = SceneGraph.tileHeights[this.level + 1];
		}
		if (GlRenderer.enabled && needsShadowUpdate) {
			ShadowManager.removeObjectShadow(this.sprite2, this.shadowX, this.shadowElevation, this.shadowY);
		}
		@Pc(356) boolean isNewShadow = this.sprite2 == null;
		@Pc(389) LocEntity locEntity;
		if (this.seq == null) {
			locEntity = locType.getStaticEntity(this.orientation, centerFineX, heightmap, this.shape, avgHeight, aboveHeightmap, false, isNewShadow ? sprite1 : this.sprite2, needsShadowUpdate, centerFineY);
		} else {
			locEntity = locType.getAnimatedEntity(centerFineY, centerFineX, isNewShadow ? sprite1 : this.sprite2, avgHeight, this.seq, this.orientation, heightmap, needsShadowUpdate, this.seqFrame, aboveHeightmap, this.seqNextFrame, this.shape, this.seqFrameProgress);
		}
		if (locEntity == null) {
			return null;
		}
		if (GlRenderer.enabled && needsShadowUpdate) {
			if (isNewShadow) {
				sprite1 = locEntity.sprite;
			}
			@Pc(429) int elevation = 0;
			if (this.level != 0) {
				@Pc(439) int[][] groundHeightmap = SceneGraph.tileHeights[0];
				elevation = avgHeight - (groundHeightmap[sampleX2][sampleY1] + groundHeightmap[sampleX1][sampleY1] + groundHeightmap[sampleX1][sampleY2] + groundHeightmap[sampleX2][sampleY2] >> 2);
			}
			@Pc(471) SoftwareIndexedSprite shadowSprite = locEntity.sprite;
			if (this.shadowDirty && ShadowManager.isObjectInShadow(shadowSprite, centerFineX, elevation, centerFineY)) {
				this.shadowDirty = false;
			}
			if (!this.shadowDirty) {
				ShadowManager.addObjectShadow(shadowSprite, centerFineX, elevation, centerFineY);
				this.sprite2 = shadowSprite;
				this.shadowY = centerFineY;
				if (isNewShadow) {
					sprite1 = null;
				}
				this.shadowElevation = elevation;
				this.shadowX = centerFineX;
			}
			this.lastLocTypeId = locType.id;
			this.lastSeqFrame = this.seqFrame;
		}
		return locEntity.model;
	}

	@OriginalMember(owner = "client!dc", name = "d", descriptor = "(I)Lclient!th;")
	public final Entity getEntity() {
		return this.getOrBuildEntity(false);
	}

	@OriginalMember(owner = "client!dc", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.MIN_Y;
	}
}
