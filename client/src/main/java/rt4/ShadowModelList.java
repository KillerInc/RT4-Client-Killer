package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class ShadowModelList {
	@OriginalMember(owner = "client!di", name = "I", descriptor = "Lclient!n;")
	public static final SoftLruHashTable SHADOWS = new SoftLruHashTable(32);

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(II)V")
	public static void clean() {
		SHADOWS.clean(5);
	}

	@OriginalMember(owner = "client!ug", name = "b", descriptor = "(B)V")
	public static void removeSoft() {
		SHADOWS.removeSoft();
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(IZLclient!tk;IIIIILclient!ak;IIIIB)Lclient!ak;")
	public static Model getOrCreateShadow(@OriginalArg(0) int innerAlpha, @OriginalArg(1) boolean adjustForAngle, @OriginalArg(2) SeqType seq, @OriginalArg(3) int fineX, @OriginalArg(4) int innerColor, @OriginalArg(5) int fineZ, @OriginalArg(6) int outerColor, @OriginalArg(7) int radius, @OriginalArg(8) Model model, @OriginalArg(9) int angle, @OriginalArg(10) int frame, @OriginalArg(11) int centerHeight, @OriginalArg(12) int outerAlpha) {
		@Pc(23) long hashKey = ((long) innerColor << 48) + (long) (radius + (innerAlpha << 16) + (outerAlpha << 24)) + ((long) outerColor << 32);
		@Pc(33) Model shadowModel = (Model) SHADOWS.get(hashKey);
		@Pc(109) int maxZ;
		@Pc(115) int maxX;
		@Pc(126) int minZ;
		@Pc(130) int i;
		@Pc(162) int v;
		if (shadowModel == null) {
			@Pc(41) byte segmentCount;
			if (radius == 1) {
				segmentCount = 9;
			} else if (radius == 2) {
				segmentCount = 12;
			} else if (radius == 3) {
				segmentCount = 15;
			} else if (radius == 4) {
				segmentCount = 18;
			} else {
				segmentCount = 21;
			}
			@Pc(83) int[] ringRadii = new int[]{64, 96, 128};
			@Pc(103) RawModel rawModel = new RawModel(segmentCount * 3 + 1, -segmentCount + segmentCount * 3 * 2, 0);
			maxZ = rawModel.addOrReuseGroundVertex(0, 0);
			@Pc(113) int[][] ringVertices = new int[3][segmentCount];
			for (maxX = 0; maxX < 3; maxX++) {
				minZ = ringRadii[maxX];
				i = ringRadii[maxX];
				for (@Pc(132) int seg = 0; seg < segmentCount; seg++) {
					@Pc(141) int segAngle = (seg << 11) / segmentCount;
					@Pc(152) int z = fineZ + MathUtils.cos[segAngle] * i >> 16;
					v = fineX + MathUtils.sin[segAngle] * minZ >> 16;
					ringVertices[maxX][seg] = rawModel.addOrReuseGroundVertex(v, z);
				}
			}
			for (maxX = 0; maxX < 3; maxX++) {
				minZ = (maxX * 256 + 128) / 3;
				i = 256 - minZ;
				@Pc(207) byte alpha = (byte) (outerAlpha * minZ + innerAlpha * i >> 8);
				@Pc(252) short color = (short) (((outerColor & 0x7F) * i + (innerColor & 0x7F) * minZ & 0x7F00) + (i * (outerColor & 0x380) + minZ * (innerColor & 0x380) & 0x38000) + (minZ * (innerColor & 0xFC00) + (outerColor & 0xFC00) * i & 0xFC0000) >> 8);
				for (v = 0; v < segmentCount; v++) {
					if (maxX == 0) {
						rawModel.addTriangle(maxZ, ringVertices[0][(v + 1) % segmentCount], ringVertices[0][v], color, alpha);
					} else {
						rawModel.addTriangle(ringVertices[maxX - 1][v], ringVertices[maxX - 1][(v + 1) % segmentCount], ringVertices[maxX][(v + 1) % segmentCount], color, alpha);
						rawModel.addTriangle(ringVertices[maxX - 1][v], ringVertices[maxX][(v + 1) % segmentCount], ringVertices[maxX][v], color, alpha);
					}
				}
			}
			shadowModel = rawModel.createModel(64, 768, -50, -10, -50);
			SHADOWS.put(shadowModel, hashKey);
		}
		@Pc(367) int halfExtent = radius * 64 - 1;
		@Pc(376) int minX = -halfExtent;
		@Pc(379) int boundsMinZ = -halfExtent;
		@Pc(381) int boundsMaxX = halfExtent;
		@Pc(384) int modelMinX = model.getMinX();
		@Pc(386) AnimFrameset animFrameset = null;
		maxX = model.getMaxX();
		minZ = model.getMinZ();
		i = model.getMaxZ();
		if (seq != null) {
			@Pc(403) int frameId = seq.frames[frame];
			animFrameset = SeqTypeList.getAnimFrameset(frameId >> 16);
			frame = frameId & 0xFFFF;
		}
		maxZ = halfExtent;
		if (adjustForAngle) {
			if (angle > 1664 || angle < 384) {
				boundsMinZ -= 128;
			}
			if (angle > 1152 && angle < 1920) {
				boundsMaxX = halfExtent + 128;
			}
			if (angle > 640 && angle < 1408) {
				maxZ = halfExtent + 128;
			}
			if (angle > 128 && angle < 896) {
				minX -= 128;
			}
		}
		if (maxZ < i) {
			i = maxZ;
		}
		if (minX > modelMinX) {
			modelMinX = minX;
		}
		if (minZ < boundsMinZ) {
			minZ = boundsMinZ;
		}
		if (boundsMaxX < maxX) {
			maxX = boundsMaxX;
		}
		if (animFrameset == null) {
			shadowModel = shadowModel.copyForAnimation(true, true, true);
			shadowModel.resize((maxX - modelMinX) / 2, 128, (i - minZ) / 2);
			shadowModel.translate((modelMinX + maxX) / 2, 0, (minZ + i) / 2);
		} else {
			shadowModel = shadowModel.copyForAnimation(!animFrameset.isAlphaTransformed(frame), !animFrameset.isColorTransformed(frame), true);
			shadowModel.resize((maxX - modelMinX) / 2, 128, (i - minZ) / 2);
			shadowModel.translate((modelMinX + maxX) / 2, 0, (minZ + i) / 2);
			shadowModel.applyShadowAnimation(animFrameset, frame);
		}
		if (angle != 0) {
			shadowModel.rotateY(angle);
		}
		if (GlRenderer.enabled) {
			@Pc(650) GlModel glModel = (GlModel) shadowModel;
			if (SceneGraph.getTileHeight(Player.plane, fineX + modelMinX, minZ + fineZ) != centerHeight || SceneGraph.getTileHeight(Player.plane, maxX + fineX, fineZ - -i) != centerHeight) {
				for (v = 0; v < glModel.vertexCount; v++) {
					glModel.vertexY[v] += SceneGraph.getTileHeight(Player.plane, glModel.vertexX[v] + fineX, fineZ + glModel.vertexZ[v]) - centerHeight;
				}
				glModel.bounds.valid = false;
				glModel.vertexBuffer.valid = false;
			}
		} else {
			@Pc(574) SoftwareModel swModel = (SoftwareModel) shadowModel;
			if (SceneGraph.getTileHeight(Player.plane, fineX + modelMinX, fineZ - -minZ) != centerHeight || centerHeight != SceneGraph.getTileHeight(Player.plane, fineX + maxX, fineZ - -i)) {
				for (v = 0; v < swModel.vertexCount; v++) {
					swModel.vertexY[v] += SceneGraph.getTileHeight(Player.plane, fineX + swModel.vertexX[v], fineZ + swModel.vertexZ[v]) - centerHeight;
				}
				swModel.boundsValid = false;
			}
		}
		return shadowModel;
	}

	@OriginalMember(owner = "client!hb", name = "a", descriptor = "(Z)V")
	public static void clear() {
		SHADOWS.clear();
	}
}
