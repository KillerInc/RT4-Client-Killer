package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ak")
public abstract class Model extends Entity {

	@OriginalMember(owner = "client!vc", name = "V", descriptor = "[J")
	public static final long[] pickResults = new long[1000];

	@OriginalMember(owner = "client!ak", name = "s", descriptor = "Z")
	public boolean pickable = false;

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "([[III)I")
	public static int interpolateHeight(@OriginalArg(0) int[][] heightMap, @OriginalArg(1) int xFine, @OriginalArg(2) int yFine) {
		@Pc(3) int tileX = xFine >> 7;
		@Pc(7) int tileY = yFine >> 7;
		if (tileX < 0 || tileY < 0 || tileX >= heightMap.length || tileY >= heightMap[0].length) {
			return 0;
		}
		@Pc(27) int xFrac = xFine & 0x7F;
		@Pc(31) int yFrac = yFine & 0x7F;
		@Pc(53) int nearHeight = heightMap[tileX][tileY] * (128 - xFrac) + heightMap[tileX + 1][tileY] * xFrac >> 7;
		@Pc(79) int farHeight = heightMap[tileX][tileY + 1] * (128 - xFrac) + heightMap[tileX + 1][tileY + 1] * xFrac >> 7;
		return nearHeight * (128 - yFrac) + farHeight * yFrac >> 7;
	}

	@OriginalMember(owner = "client!ak", name = "c", descriptor = "()I")
	public abstract int getMaxZ();

	@OriginalMember(owner = "client!ak", name = "b", descriptor = "()I")
	public abstract int getMinY();

	@OriginalMember(owner = "client!ak", name = "d", descriptor = "()Z")
	protected abstract boolean hasAnimationBones();

	@OriginalMember(owner = "client!ak", name = "e", descriptor = "()V")
	public abstract void rotate180();

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!jm;Lclient!ne;Lclient!ne;II[ZZZI[I)V")
	private void applyAnimationBlend(@OriginalArg(0) AnimBase base, @OriginalArg(1) AnimFrame frame1, @OriginalArg(2) AnimFrame frame2, @OriginalArg(3) int tweenDelta, @OriginalArg(4) int tweenLength, @OriginalArg(5) boolean[] mask, @OriginalArg(6) boolean maskValue, @OriginalArg(7) boolean applyVertices, @OriginalArg(8) int partMask, @OriginalArg(9) int[] partLabels) {
		@Pc(5) int idx1;
		if (frame2 == null || tweenDelta == 0) {
			for (idx1 = 0; idx1 < frame1.length; idx1++) {
				@Pc(14) short transformIndex = frame1.indices[idx1];
				if (mask == null || mask[transformIndex] == maskValue || base.types[transformIndex] == 0) {
					@Pc(32) short prevOriginIndex = frame1.prevOriginIndices[idx1];
					@Pc(42) int partBits;
					if (prevOriginIndex != -1) {
						partBits = partMask & base.parts[prevOriginIndex];
						if (partBits == 65535) {
							this.transformBone(0, base.bones[prevOriginIndex], 0, 0, 0, applyVertices);
						} else {
							this.transformMaskedBone(0, base.bones[prevOriginIndex], 0, 0, 0, applyVertices, partBits, partLabels);
						}
					}
					partBits = partMask & base.parts[transformIndex];
					if (partBits == 65535) {
						this.transformBone(base.types[transformIndex], base.bones[transformIndex], frame1.x[idx1], frame1.y[idx1], frame1.z[idx1], applyVertices);
					} else {
						this.transformMaskedBone(base.types[transformIndex], base.bones[transformIndex], frame1.x[idx1], frame1.y[idx1], frame1.z[idx1], applyVertices, partBits, partLabels);
					}
				}
			}
			return;
		}
		idx1 = 0;
		@Pc(136) int idx2 = 0;
		for (@Pc(138) int i = 0; i < base.transforms; i++) {
			@Pc(144) boolean inFrame1 = idx1 < frame1.length && frame1.indices[idx1] == i;
			@Pc(158) boolean inFrame2 = idx2 < frame2.length && frame2.indices[idx2] == i;
			if (inFrame1 || inFrame2) {
				if (mask == null || mask[i] == maskValue || base.types[i] == 0) {
					@Pc(196) short defaultVal = 0;
					@Pc(201) int transformType = base.types[i];
					if (transformType == 3) {
						defaultVal = 128;
					}
					@Pc(213) short x1;
					@Pc(218) short y1;
					@Pc(223) short z1;
					@Pc(228) short prevOrigin1;
					@Pc(233) byte flags1;
					if (inFrame1) {
						x1 = frame1.x[idx1];
						y1 = frame1.y[idx1];
						z1 = frame1.z[idx1];
						prevOrigin1 = frame1.prevOriginIndices[idx1];
						flags1 = frame1.flags[idx1];
						idx1++;
					} else {
						x1 = defaultVal;
						y1 = defaultVal;
						z1 = defaultVal;
						prevOrigin1 = -1;
						flags1 = 0;
					}
					@Pc(252) short x2;
					@Pc(257) short y2;
					@Pc(262) short z2;
					@Pc(267) short prevOrigin2;
					@Pc(272) byte flags2;
					if (inFrame2) {
						x2 = frame2.x[idx2];
						y2 = frame2.y[idx2];
						z2 = frame2.z[idx2];
						prevOrigin2 = frame2.prevOriginIndices[idx2];
						flags2 = frame2.flags[idx2];
						idx2++;
					} else {
						x2 = defaultVal;
						y2 = defaultVal;
						z2 = defaultVal;
						prevOrigin2 = -1;
						flags2 = 0;
					}
					@Pc(294) int blendX;
					@Pc(296) int blendY;
					@Pc(298) int blendZ;
					@Pc(308) int partBits;
					if ((flags1 & 0x2) != 0 || (flags2 & 0x1) != 0) {
						blendX = x1;
						blendY = y1;
						blendZ = z1;
					} else if (transformType == 2) {
						partBits = x2 - x1 & 0x7FF;
						@Pc(314) int deltaY = y2 - y1 & 0x7FF;
						@Pc(320) int deltaZ = z2 - z1 & 0x7FF;
						if (partBits >= 1024) {
							partBits -= 2048;
						}
						if (deltaY >= 1024) {
							deltaY -= 2048;
						}
						if (deltaZ >= 1024) {
							deltaZ -= 2048;
						}
						blendX = x1 + partBits * tweenDelta / tweenLength & 0x7FF;
						blendY = y1 + deltaY * tweenDelta / tweenLength & 0x7FF;
						blendZ = z1 + deltaZ * tweenDelta / tweenLength & 0x7FF;
					} else if (transformType == 7) {
						partBits = x2 - x1 & 0x3F;
						if (partBits >= 32) {
							partBits -= 64;
						}
						blendX = x1 + partBits * tweenDelta / tweenLength & 0x3F;
						blendY = y1 + (y2 - y1) * tweenDelta / tweenLength;
						blendZ = z1 + (z2 - z1) * tweenDelta / tweenLength;
					} else {
						blendX = x1 + (x2 - x1) * tweenDelta / tweenLength;
						blendY = y1 + (y2 - y1) * tweenDelta / tweenLength;
						blendZ = z1 + (z2 - z1) * tweenDelta / tweenLength;
					}
					if (prevOrigin1 != -1) {
						partBits = partMask & base.parts[prevOrigin1];
						if (partBits == 65535) {
							this.transformBone(0, base.bones[prevOrigin1], 0, 0, 0, applyVertices);
						} else {
							this.transformMaskedBone(0, base.bones[prevOrigin1], 0, 0, 0, applyVertices, partBits, partLabels);
						}
					} else if (prevOrigin2 != -1) {
						partBits = partMask & base.parts[prevOrigin2];
						if (partBits == 65535) {
							this.transformBone(0, base.bones[prevOrigin2], 0, 0, 0, applyVertices);
						} else {
							this.transformMaskedBone(0, base.bones[prevOrigin2], 0, 0, 0, applyVertices, partBits, partLabels);
						}
					}
					partBits = partMask & base.parts[i];
					if (partBits == 65535) {
						this.transformBone(transformType, base.bones[i], blendX, blendY, blendZ, applyVertices);
					} else {
						this.transformMaskedBone(transformType, base.bones[i], blendX, blendY, blendZ, applyVertices, partBits, partLabels);
					}
				} else {
					if (inFrame1) {
						idx1++;
					}
					if (inFrame2) {
						idx2++;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ak", name = "b", descriptor = "(I)V")
	public abstract void rotateY(@OriginalArg(0) int angle);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!cl;I)V")
	public final void applyShadowAnimation(@OriginalArg(0) AnimFrameset frameset, @OriginalArg(1) int frameIndex) {
		if (frameIndex == -1 || !this.hasAnimationBones()) {
			return;
		}
		@Pc(12) AnimFrame frame = frameset.frames[frameIndex];
		@Pc(15) AnimBase base = frame.base;
		for (@Pc(17) int i = 0; i < frame.length; i++) {
			@Pc(26) short transformIndex = frame.indices[i];
			if (base.shadow[transformIndex]) {
				if (frame.prevOriginIndices[i] != -1) {
					this.transformShadowBone(0, 0, 0, 0);
				}
				this.transformShadowBone(base.types[transformIndex], frame.x[i], frame.y[i], frame.z[i]);
			}
		}
		this.resetAfterAnimation();
	}

	@OriginalMember(owner = "client!ak", name = "f", descriptor = "()V")
	protected abstract void resetAfterAnimation();

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!cl;ILclient!cl;IIIZ)V")
	public final void applyAnimation(@OriginalArg(0) AnimFrameset frameset1, @OriginalArg(1) int frameIndex1, @OriginalArg(2) AnimFrameset frameset2, @OriginalArg(3) int frameIndex2, @OriginalArg(4) int tweenDelta, @OriginalArg(5) int tweenLength, @OriginalArg(6) boolean applyVertices) {
		if (frameIndex1 == -1 || !this.hasAnimationBones()) {
			return;
		}
		@Pc(12) AnimFrame frame1 = frameset1.frames[frameIndex1];
		@Pc(15) AnimBase base = frame1.base;
		@Pc(17) AnimFrame frame2 = null;
		if (frameset2 != null) {
			frame2 = frameset2.frames[frameIndex2];
			if (frame2.base != base) {
				frame2 = null;
			}
		}
		this.applyAnimationBlend(base, frame1, frame2, tweenDelta, tweenLength, null, false, applyVertices, 65535, null);
		this.resetAfterAnimation();
	}

	@OriginalMember(owner = "client!ak", name = "b", descriptor = "(III)V")
	public abstract void resize(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(ZZZ)Lclient!ak;")
	public abstract Model copyForAnimation(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareBones);

	@OriginalMember(owner = "client!ak", name = "g", descriptor = "()I")
	public abstract int getMaxX();

	@OriginalMember(owner = "client!ak", name = "h", descriptor = "()I")
	public abstract int getMinX();

	@OriginalMember(owner = "client!ak", name = "i", descriptor = "()V")
	public abstract void rotateCounterClockwise();

	@OriginalMember(owner = "client!ak", name = "c", descriptor = "(I)V")
	public abstract void rotateZ(@OriginalArg(0) int angle);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!cl;ILclient!cl;IIIIZ[I)V")
	public final void applyMaskedAnimation(@OriginalArg(0) AnimFrameset frameset1, @OriginalArg(1) int frameIndex1, @OriginalArg(2) AnimFrameset frameset2, @OriginalArg(3) int frameIndex2, @OriginalArg(4) int tweenDelta, @OriginalArg(5) int tweenLength, @OriginalArg(6) int partMask, @OriginalArg(7) boolean applyVertices, @OriginalArg(8) int[] partLabels) {
		if (frameIndex1 == -1 || !this.hasAnimationBones()) {
			return;
		}
		@Pc(12) AnimFrame frame1 = frameset1.frames[frameIndex1];
		@Pc(15) AnimBase base = frame1.base;
		@Pc(17) AnimFrame frame2 = null;
		if (frameset2 != null) {
			frame2 = frameset2.frames[frameIndex2];
			if (frame2.base != base) {
				frame2 = null;
			}
		}
		this.applyAnimationBlend(base, frame1, frame2, tweenDelta, tweenLength, null, false, applyVertices, partMask, partLabels);
		this.resetAfterAnimation();
	}

	@OriginalMember(owner = "client!ak", name = "j", descriptor = "()I")
	public abstract int getLengthXZ();

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIII)V")
	protected abstract void transformShadowBone(@OriginalArg(0) int type, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z);

	@OriginalMember(owner = "client!ak", name = "b", descriptor = "(ZZZ)Lclient!ak;")
	public abstract Model copyForLoc(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareBones);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(I[IIIIZ)V")
	protected abstract void transformBone(@OriginalArg(0) int type, @OriginalArg(1) int[] bones, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) boolean applyVertices);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!cl;ILclient!cl;IIILclient!cl;ILclient!cl;III[ZZ)V")
	public final void applyDualAnimation(@OriginalArg(0) AnimFrameset frameset1, @OriginalArg(1) int frameIndex1, @OriginalArg(2) AnimFrameset tweenFrameset1, @OriginalArg(3) int tweenIndex1, @OriginalArg(4) int tweenDelta1, @OriginalArg(5) int tweenLength1, @OriginalArg(6) AnimFrameset frameset2, @OriginalArg(7) int frameIndex2, @OriginalArg(8) AnimFrameset tweenFrameset2, @OriginalArg(9) int tweenIndex2, @OriginalArg(10) int tweenDelta2, @OriginalArg(11) int tweenLength2, @OriginalArg(12) boolean[] mask, @OriginalArg(13) boolean applyVertices) {
		if (frameIndex1 == -1) {
			return;
		}
		if (mask == null || frameIndex2 == -1) {
			this.applyAnimation(frameset1, frameIndex1, tweenFrameset1, tweenIndex1, tweenDelta1, tweenLength1, applyVertices);
		} else if (this.hasAnimationBones()) {
			@Pc(27) AnimFrame frame1 = frameset1.frames[frameIndex1];
			@Pc(30) AnimBase base = frame1.base;
			@Pc(32) AnimFrame tweenFrame1 = null;
			if (tweenFrameset1 != null) {
				tweenFrame1 = tweenFrameset1.frames[tweenIndex1];
				if (tweenFrame1.base != base) {
					tweenFrame1 = null;
				}
			}
			@Pc(50) AnimFrame frame2 = frameset2.frames[frameIndex2];
			@Pc(52) AnimFrame tweenFrame2 = null;
			if (tweenFrameset2 != null) {
				tweenFrame2 = tweenFrameset2.frames[tweenIndex2];
				if (tweenFrame2.base != base) {
					tweenFrame2 = null;
				}
			}
			this.applyAnimationBlend(base, frame1, tweenFrame1, tweenDelta1, tweenLength1, mask, false, applyVertices, 65535, null);
			this.transformBone(0, new int[0], 0, 0, 0, applyVertices);
			this.applyAnimationBlend(base, frame2, tweenFrame2, tweenDelta2, tweenLength2, mask, true, applyVertices, 65535, null);
			this.resetAfterAnimation();
		}
	}

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIIIIIIJ)V")
	public abstract void setCamera(@OriginalArg(1) int yaw, @OriginalArg(2) int yOffset, @OriginalArg(3) int pitch, @OriginalArg(4) int xOffset, @OriginalArg(5) int zNear, @OriginalArg(6) int zFar, @OriginalArg(7) long pickKey);

	@OriginalMember(owner = "client!ak", name = "c", descriptor = "(ZZZ)Lclient!ak;")
	public abstract Model copyForEntity(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareBones);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "([[IIIIII)V")
	protected final void alignToTerrain(@OriginalArg(0) int[][] heightMap, @OriginalArg(1) int xFine, @OriginalArg(2) int height, @OriginalArg(3) int yFine, @OriginalArg(4) int sizeX, @OriginalArg(5) int sizeY) {
		@Pc(10) int halfNegX = -sizeX / 2;
		@Pc(15) int halfNegY = -sizeY / 2;
		@Pc(24) int heightSW = interpolateHeight(heightMap, xFine + halfNegX, yFine + halfNegY);
		@Pc(28) int halfPosX = sizeX / 2;
		@Pc(33) int halfNegY2 = -sizeY / 2;
		@Pc(42) int heightSE = interpolateHeight(heightMap, xFine + halfPosX, yFine + halfNegY2);
		@Pc(47) int halfNegX2 = -sizeX / 2;
		@Pc(51) int halfPosY = sizeY / 2;
		@Pc(60) int heightNW = interpolateHeight(heightMap, xFine + halfNegX2, yFine + halfPosY);
		@Pc(64) int halfPosX2 = sizeX / 2;
		@Pc(68) int halfPosY2 = sizeY / 2;
		@Pc(77) int heightNE = interpolateHeight(heightMap, xFine + halfPosX2, yFine + halfPosY2);
		@Pc(84) int minSouth = heightSW < heightSE ? heightSW : heightSE;
		@Pc(91) int minNorth = heightNW < heightNE ? heightNW : heightNE;
		@Pc(98) int minEast = heightSE < heightNE ? heightSE : heightNE;
		@Pc(105) int minWest = heightSW < heightNW ? heightSW : heightNW;
		if (sizeY != 0) {
			@Pc(120) int pitchAngle = (int) (Math.atan2(minSouth - minNorth, sizeY) * 325.95D) & 0x7FF;
			if (pitchAngle != 0) {
				this.rotateX(pitchAngle);
			}
		}
		if (sizeX != 0) {
			@Pc(140) int rollAngle = (int) (Math.atan2(minWest - minEast, sizeX) * 325.95D) & 0x7FF;
			if (rollAngle != 0) {
				this.rotateZ(rollAngle);
			}
		}
		@Pc(149) int avgHeight = heightSW + heightNE;
		if (heightSE + heightNW < avgHeight) {
			avgHeight = heightSE + heightNW;
		}
		avgHeight = (avgHeight >> 1) - height;
		if (avgHeight != 0) {
			this.translate(0, avgHeight, 0);
		}
	}

	@OriginalMember(owner = "client!ak", name = "d", descriptor = "(I)V")
	public abstract void rotateX(@OriginalArg(0) int angle);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int pitch, @OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int x, @OriginalArg(4) int z) {
	}

	@OriginalMember(owner = "client!ak", name = "c", descriptor = "(III)V")
	public abstract void translate(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z);

	@OriginalMember(owner = "client!ak", name = "k", descriptor = "()I")
	public abstract int getMinZ();

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	public abstract void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinCameraPitch, @OriginalArg(2) int cosCameraPitch, @OriginalArg(3) int sinCameraYaw, @OriginalArg(4) int cosCameraYaw, @OriginalArg(5) int sceneX, @OriginalArg(6) int sceneZ, @OriginalArg(7) int sceneY, @OriginalArg(8) long key, @OriginalArg(9) int plane, @OriginalArg(10) ParticleSystem particleSystem);

	@OriginalMember(owner = "client!ak", name = "a", descriptor = "(I[IIIIZI[I)V")
	protected abstract void transformMaskedBone(@OriginalArg(0) int type, @OriginalArg(1) int[] bones, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) boolean applyVertices, @OriginalArg(6) int partBits, @OriginalArg(7) int[] partLabels);

	@OriginalMember(owner = "client!ak", name = "l", descriptor = "()V")
	public abstract void rotateClockwise();
}
