package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.api.API;

import java.nio.ByteBuffer;

@OriginalClass("client!td")
public final class GlModel extends Model {

	@OriginalMember(owner = "client!tf", name = "G", descriptor = "Z")
	public static boolean arbVboSupported;

	@OriginalMember(owner = "client!td", name = "D", descriptor = "Lclient!wa;")
	public static Buffer tempBuffer = new Buffer(10000);

	@OriginalMember(owner = "client!td", name = "bb", descriptor = "Lclient!td;")
	public static GlModel entityCopyScratch = new GlModel();

	@OriginalMember(owner = "client!td", name = "jb", descriptor = "Lclient!td;")
	public static GlModel entityCopyTarget = new GlModel();

	@OriginalMember(owner = "client!td", name = "mb", descriptor = "Lclient!td;")
	public static GlModel animCopyScratch = new GlModel();

	@OriginalMember(owner = "client!td", name = "nb", descriptor = "Lclient!td;")
	public static GlModel animCopyTarget = new GlModel();

	@OriginalMember(owner = "client!td", name = "ob", descriptor = "Lclient!td;")
	public static GlModel locCopyScratch = new GlModel();

	@OriginalMember(owner = "client!td", name = "pb", descriptor = "Lclient!td;")
	public static GlModel locCopyTarget = new GlModel();

	@OriginalMember(owner = "client!td", name = "qb", descriptor = "[I")
	public static int[] pickVertexScreenY = new int[1];

	@OriginalMember(owner = "client!td", name = "ub", descriptor = "[I")
	public static int[] pickVertexScreenX = new int[1];

	@OriginalMember(owner = "client!td", name = "Bb", descriptor = "Z")
	public static boolean originValid = false;

	@OriginalMember(owner = "client!td", name = "Ab", descriptor = "F")
	public static float sphericalU;

	@OriginalMember(owner = "client!td", name = "G", descriptor = "[J")
	public static long[] aLongArray10;

	@OriginalMember(owner = "client!td", name = "T", descriptor = "Ljava/nio/ByteBuffer;")
	public static ByteBuffer aByteBuffer9;

	@OriginalMember(owner = "client!td", name = "rb", descriptor = "F")
	public static float planarV;

	@OriginalMember(owner = "client!td", name = "sb", descriptor = "I")
	public static int originX;

	@OriginalMember(owner = "client!td", name = "tb", descriptor = "F")
	public static float sphericalV;

	@OriginalMember(owner = "client!td", name = "vb", descriptor = "I")
	public static int originZ;

	@OriginalMember(owner = "client!td", name = "wb", descriptor = "F")
	public static float cylindricalU;

	@OriginalMember(owner = "client!td", name = "xb", descriptor = "F")
	public static float planarU;

	@OriginalMember(owner = "client!td", name = "yb", descriptor = "I")
	public static int originY;

	@OriginalMember(owner = "client!td", name = "zb", descriptor = "F")
	public static float cylindricalV;

	@OriginalMember(owner = "client!lk", name = "V", descriptor = "I")
	public static int pickScreenX = 0;

	@OriginalMember(owner = "client!td", name = "t", descriptor = "[S")
	private short[] triangleColors;

	@OriginalMember(owner = "client!td", name = "v", descriptor = "[S")
	private short[] normalX;

	@OriginalMember(owner = "client!td", name = "w", descriptor = "[S")
	private short[] triangleVertexA;

	@OriginalMember(owner = "client!td", name = "x", descriptor = "Lclient!vi;")
	private GlVertexBufferObject interleavedVbo;

	@OriginalMember(owner = "client!td", name = "y", descriptor = "[S")
	private short[] triangleSources;

	@OriginalMember(owner = "client!td", name = "z", descriptor = "Lclient!ql;")
	private GlBuffer colorBuffer;

	@OriginalMember(owner = "client!td", name = "A", descriptor = "Lclient!ql;")
	private GlBuffer normalsBuffer;

	@OriginalMember(owner = "client!td", name = "B", descriptor = "[B")
	private byte[] triangleAlpha;

	@OriginalMember(owner = "client!td", name = "C", descriptor = "Lclient!ql;")
	private GlBuffer texCoordBuffer;

	@OriginalMember(owner = "client!td", name = "E", descriptor = "S")
	private short contrast;

	@OriginalMember(owner = "client!td", name = "F", descriptor = "[B")
	private byte[] triangleBones;

	@OriginalMember(owner = "client!td", name = "H", descriptor = "[I")
	public int[] vertexX;

	@OriginalMember(owner = "client!td", name = "J", descriptor = "[F")
	private float[] vertexS;

	@OriginalMember(owner = "client!td", name = "K", descriptor = "[[I")
	private int[][] boneVertices;

	@OriginalMember(owner = "client!td", name = "L", descriptor = "[S")
	private short[] normalY;

	@OriginalMember(owner = "client!td", name = "M", descriptor = "[S")
	private short[] triangleTextures;

	@OriginalMember(owner = "client!td", name = "N", descriptor = "[S")
	private short[] vertexLookup;

	@OriginalMember(owner = "client!td", name = "O", descriptor = "[[I")
	private int[][] boneTriangles;

	@OriginalMember(owner = "client!td", name = "P", descriptor = "[S")
	private short[] triangleVertexB;

	@OriginalMember(owner = "client!td", name = "Q", descriptor = "[S")
	private short[] triangleVertexC;

	@OriginalMember(owner = "client!td", name = "R", descriptor = "[S")
	private short[] vertexSources;

	@OriginalMember(owner = "client!td", name = "U", descriptor = "Lclient!ae;")
	public GlBoundingBox bounds;

	@OriginalMember(owner = "client!td", name = "W", descriptor = "[S")
	private short[] normalZ;

	@OriginalMember(owner = "client!td", name = "X", descriptor = "[I")
	private int[] vertexOffsets;

	@OriginalMember(owner = "client!td", name = "Y", descriptor = "Lclient!ql;")
	public GlBuffer vertexBuffer;

	@OriginalMember(owner = "client!td", name = "Z", descriptor = "[I")
	private int[] textureGroupOffsets;

	@OriginalMember(owner = "client!td", name = "ab", descriptor = "[S")
	private short[] normalMagnitude;

	@OriginalMember(owner = "client!td", name = "cb", descriptor = "Lclient!de;")
	private GlModelRecoloring recoloring;

	@OriginalMember(owner = "client!td", name = "db", descriptor = "[I")
	private int[] vertexBones;

	@OriginalMember(owner = "client!td", name = "eb", descriptor = "[I")
	public int[] vertexY;

	@OriginalMember(owner = "client!td", name = "fb", descriptor = "Lclient!ql;")
	private GlBuffer indexBuffer;

	@OriginalMember(owner = "client!td", name = "gb", descriptor = "[F")
	private float[] vertexT;

	@OriginalMember(owner = "client!td", name = "hb", descriptor = "[I")
	public int[] vertexZ;

	@OriginalMember(owner = "client!td", name = "ib", descriptor = "S")
	private short ambient;

	@OriginalMember(owner = "client!td", name = "u", descriptor = "Z")
	public boolean mergeable = false;

	@OriginalMember(owner = "client!td", name = "I", descriptor = "I")
	public int vertexCount = 0;

	@OriginalMember(owner = "client!td", name = "S", descriptor = "I")
	private int uniqueVertexCount = 0;

	@OriginalMember(owner = "client!td", name = "V", descriptor = "B")
	private byte updateFlags = 0;

	@OriginalMember(owner = "client!td", name = "kb", descriptor = "B")
	private byte deferredReleaseFlags = 0;

	@OriginalMember(owner = "client!td", name = "lb", descriptor = "I")
	private int triangleCount = 0;

	@OriginalMember(owner = "client!td", name = "<init>", descriptor = "()V")
	public GlModel() {
	}

	@OriginalMember(owner = "client!td", name = "<init>", descriptor = "(Lclient!gb;IIZ)V")
	public GlModel(@OriginalArg(0) RawModel model, @OriginalArg(1) int ambience, @OriginalArg(2) int contrast, @OriginalArg(3) boolean sortByPriority) {
		@Pc(23) int[] triOrder = new int[model.triangleCount];
		this.vertexOffsets = new int[model.vertexCount + 1];
		for (@Pc(32) int i = 0; i < model.triangleCount; i++) {
			if ((model.triangleInfo == null || model.triangleInfo[i] != 2) && (model.triangleTextures == null || model.triangleTextures[i] == -1 || !Rasteriser.textureProvider.isTextureFlipped(model.triangleTextures[i] & 0xFFFF))) {
				triOrder[this.triangleCount++] = i;
				this.vertexOffsets[model.triangleVertexA[i]]++;
				this.vertexOffsets[model.triangleVertexB[i]]++;
				this.vertexOffsets[model.triangleVertexC[i]]++;
			}
		}
		@Pc(115) long[] sortKeys = new long[this.triangleCount];
		@Pc(117) int j;
		@Pc(125) int k;
		@Pc(127) int sortKey;
		@Pc(226) int offset;
		for (j = 0; j < this.triangleCount; j++) {
			k = triOrder[j];
			sortKey = 0;
			@Pc(129) byte texGroup = 0;
			@Pc(131) int materialType = 0;
			@Pc(133) int animationType = 0;
			@Pc(135) short texture = -1;
			if (model.triangleTextures != null) {
				texture = model.triangleTextures[k];
				if (texture != -1) {
					materialType = Rasteriser.textureProvider.getMaterialType(texture & 0xFFFF);
					animationType = Rasteriser.textureProvider.getAnimationType(texture & 0xFFFF);
				}
			}
			@Pc(182) boolean hasTransparency = model.triangleAlpha != null && model.triangleAlpha[k] != 0 || texture != -1 && !Rasteriser.textureProvider.isOpaque(texture & 0xFFFF);
			if ((sortByPriority || hasTransparency) && model.trianglePriorities != null) {
				sortKey += model.trianglePriorities[k] << 17;
			}
			if (hasTransparency) {
				sortKey += 65536;
			}
			sortKey += (materialType & 0xFF) << 8;
			sortKey += animationType & 0xFF;
			offset = texGroup + ((texture & 0xFFFF) << 16);
			@Pc(232) int packedIdx = offset + (j & 0xFFFF);
			sortKeys[j] = ((long) sortKey << 32) + (long) packedIdx;
		}
		ArrayUtils.sort(sortKeys, triOrder);
		this.vertexCount = model.vertexCount;
		this.vertexX = model.vertexX;
		this.vertexY = model.vertexY;
		this.vertexZ = model.vertexZ;
		this.vertexBones = model.vertexBones;
		this.vertexSources = model.vertexSources;
		j = this.triangleCount * 3;
		this.normalX = new short[j];
		this.normalY = new short[j];
		this.normalZ = new short[j];
		this.normalMagnitude = new short[j];
		this.vertexS = new float[j];
		this.vertexT = new float[j];
		this.triangleColors = new short[this.triangleCount];
		this.triangleAlpha = new byte[this.triangleCount];
		this.triangleVertexA = new short[this.triangleCount];
		this.triangleVertexB = new short[this.triangleCount];
		this.triangleVertexC = new short[this.triangleCount];
		this.triangleTextures = new short[this.triangleCount];
		if (model.triangleBones != null) {
			this.triangleBones = new byte[this.triangleCount];
		}
		if (model.triangleSources != null) {
			this.triangleSources = new short[this.triangleCount];
		}
		this.bounds = new GlBoundingBox();
		this.vertexBuffer = new GlBuffer();
		this.colorBuffer = new GlBuffer();
		if (Preferences.highDetailLighting) {
			this.normalsBuffer = new GlBuffer();
		}
		this.texCoordBuffer = new GlBuffer();
		this.indexBuffer = new GlBuffer();
		this.ambient = (short) ambience;
		this.contrast = (short) contrast;
		this.vertexLookup = new short[j];
		aLongArray10 = new long[j];
		k = 0;
		for (sortKey = 0; sortKey < model.vertexCount; sortKey++) {
			offset = this.vertexOffsets[sortKey];
			this.vertexOffsets[sortKey] = k;
			k += offset;
		}
		this.vertexOffsets[model.vertexCount] = k;
		@Pc(426) int[] centerX = null;
		@Pc(428) int[] centerY = null;
		@Pc(430) int[] centerZ = null;
		@Pc(433) float[][] matrices = null;
		@Pc(553) int vz;
		@Pc(439) int n;
		@Pc(683) float scaleU;
		@Pc(714) float scaleW;
		@Pc(685) float scaleV;
		if (model.triangleTextureIndex != null) {
			n = model.texturedCount;
			@Pc(442) int[] minBoundsX = new int[n];
			@Pc(445) int[] maxBoundsX = new int[n];
			@Pc(448) int[] minBoundsY = new int[n];
			@Pc(451) int[] maxBoundsY = new int[n];
			@Pc(454) int[] minBoundsZ = new int[n];
			@Pc(457) int[] maxBoundsZ = new int[n];
			@Pc(459) int m;
			for (m = 0; m < n; m++) {
				minBoundsX[m] = Integer.MAX_VALUE;
				maxBoundsX[m] = -2147483647;
				minBoundsY[m] = Integer.MAX_VALUE;
				maxBoundsY[m] = -2147483647;
				minBoundsZ[m] = Integer.MAX_VALUE;
				maxBoundsZ[m] = -2147483647;
			}
			for (m = 0; m < this.triangleCount; m++) {
				@Pc(498) int origTriIdx = triOrder[m];
				if (model.triangleTextureIndex[origTriIdx] != -1) {
					@Pc(511) int texGroupIdx = model.triangleTextureIndex[origTriIdx] & 0xFF;
					for (@Pc(513) int v = 0; v < 3; v++) {
						@Pc(523) int vertIdx;
						if (v == 0) {
							vertIdx = model.triangleVertexA[origTriIdx];
						} else if (v == 1) {
							vertIdx = model.triangleVertexB[origTriIdx];
						} else {
							vertIdx = model.triangleVertexC[origTriIdx];
						}
						@Pc(543) int vx = model.vertexX[vertIdx];
						@Pc(548) int vy = model.vertexY[vertIdx];
						vz = model.vertexZ[vertIdx];
						if (vx < minBoundsX[texGroupIdx]) {
							minBoundsX[texGroupIdx] = vx;
						}
						if (vx > maxBoundsX[texGroupIdx]) {
							maxBoundsX[texGroupIdx] = vx;
						}
						if (vy < minBoundsY[texGroupIdx]) {
							minBoundsY[texGroupIdx] = vy;
						}
						if (vy > maxBoundsY[texGroupIdx]) {
							maxBoundsY[texGroupIdx] = vy;
						}
						if (vz < minBoundsZ[texGroupIdx]) {
							minBoundsZ[texGroupIdx] = vz;
						}
						if (vz > maxBoundsZ[texGroupIdx]) {
							maxBoundsZ[texGroupIdx] = vz;
						}
					}
				}
			}
			centerX = new int[n];
			centerY = new int[n];
			centerZ = new int[n];
			matrices = new float[n][];
			for (m = 0; m < n; m++) {
				@Pc(633) byte textureType = model.textureTypes[m];
				if (textureType > 0) {
					centerX[m] = (minBoundsX[m] + maxBoundsX[m]) / 2;
					centerY[m] = (minBoundsY[m] + maxBoundsY[m]) / 2;
					centerZ[m] = (minBoundsZ[m] + maxBoundsZ[m]) / 2;
					if (textureType == 1) {
						@Pc(679) short scaleXRaw = model.texturesScaleX[m];
						if (scaleXRaw == 0) {
							scaleU = 1.0F;
							scaleV = 1.0F;
						} else if (scaleXRaw > 0) {
							scaleU = 1.0F;
							scaleV = (float) scaleXRaw / 1024.0F;
						} else {
							scaleV = 1.0F;
							scaleU = (float) -scaleXRaw / 1024.0F;
						}
						scaleW = 64.0F / (float) (model.texturesScaleY[m] & 0xFFFF);
					} else if (textureType == 2) {
						scaleU = 64.0F / (float) (model.texturesScaleX[m] & 0xFFFF);
						scaleW = 64.0F / (float) (model.texturesScaleY[m] & 0xFFFF);
						scaleV = 64.0F / (float) (model.texturesScaleZ[m] & 0xFFFF);
					} else {
						scaleU = (float) model.texturesScaleX[m] / 1024.0F;
						scaleW = (float) model.texturesScaleY[m] / 1024.0F;
						scaleV = (float) model.texturesScaleZ[m] / 1024.0F;
					}
					matrices[m] = buildTextureTransformMatrix(model.textureFacesP[m], model.textureFacesM[m], model.textureFacesN[m], model.textureRotationY[m] & 0xFF, scaleU, scaleW, scaleV);
				}
			}
		}
		@Pc(817) int color;
		@Pc(822) short tex;
		for (n = 0; n < this.triangleCount; n++) {
			@Pc(810) int origIdx = triOrder[n];
			color = model.triangleColors[origIdx] & 0xFFFF;
			if (model.triangleTextures == null) {
				tex = -1;
			} else {
				tex = model.triangleTextures[origIdx];
			}
			@Pc(833) int texIdx;
			if (model.triangleTextureIndex == null) {
				texIdx = -1;
			} else {
				texIdx = model.triangleTextureIndex[origIdx];
			}
			@Pc(844) int alpha;
			if (model.triangleAlpha == null) {
				alpha = 0;
			} else {
				alpha = model.triangleAlpha[origIdx] & 0xFF;
			}
			@Pc(854) float uA = 0.0F;
			@Pc(856) float vA = 0.0F;
			@Pc(858) float uB = 0.0F;
			scaleU = 0.0F;
			scaleW = 0.0F;
			scaleV = 0.0F;
			@Pc(866) byte wrapB = 0;
			@Pc(868) byte wrapC = 0;
			vz = 0;
			@Pc(902) byte texType;
			@Pc(919) int vertC;
			@Pc(1280) int cy;
			if (tex != -1) {
				if (texIdx == -1) {
					uA = 0.0F;
					vA = 1.0F;
					uB = 1.0F;
					scaleU = 1.0F;
					wrapB = 1;
					scaleW = 0.0F;
					scaleV = 0.0F;
					wrapC = 2;
				} else {
					texIdx &= 0xFF;
					texType = model.textureTypes[texIdx];
					@Pc(909) int vertA;
					@Pc(914) int vertB;
					@Pc(952) float pz;
					@Pc(960) float edgeAx;
					@Pc(968) float edgeAy;
					@Pc(1048) float dBz;
					@Pc(1056) float dCx;
					@Pc(1064) float dCy;
					@Pc(1072) float dCz;
					@Pc(1080) float crossX;
					@Pc(1088) float crossY;
					if (texType == 0) {
						vertA = model.triangleVertexA[origIdx];
						vertB = model.triangleVertexB[origIdx];
						vertC = model.triangleVertexC[origIdx];
						@Pc(924) short faceP = model.textureFacesP[texIdx];
						@Pc(929) short faceM = model.textureFacesM[texIdx];
						@Pc(934) short faceN = model.textureFacesN[texIdx];
						@Pc(940) float px = (float) model.vertexX[faceP];
						@Pc(946) float py = (float) model.vertexY[faceP];
						pz = model.vertexZ[faceP];
						edgeAx = (float) model.vertexX[faceM] - px;
						edgeAy = (float) model.vertexY[faceM] - py;
						@Pc(976) float edgeAz = (float) model.vertexZ[faceM] - pz;
						@Pc(984) float edgeBx = (float) model.vertexX[faceN] - px;
						@Pc(992) float edgeBy = (float) model.vertexY[faceN] - py;
						@Pc(1000) float edgeBz = (float) model.vertexZ[faceN] - pz;
						@Pc(1008) float dAx = (float) model.vertexX[vertA] - px;
						@Pc(1016) float dAy = (float) model.vertexY[vertA] - py;
						@Pc(1024) float dAz = (float) model.vertexZ[vertA] - pz;
						@Pc(1032) float dBx = (float) model.vertexX[vertB] - px;
						@Pc(1040) float dBy = (float) model.vertexY[vertB] - py;
						dBz = (float) model.vertexZ[vertB] - pz;
						dCx = (float) model.vertexX[vertC] - px;
						dCy = (float) model.vertexY[vertC] - py;
						dCz = (float) model.vertexZ[vertC] - pz;
						crossX = edgeAy * edgeBz - edgeAz * edgeBy;
						crossY = edgeAz * edgeBx - edgeAx * edgeBz;
						@Pc(1096) float crossZ = edgeAx * edgeBy - edgeAy * edgeBx;
						@Pc(1104) float invRowX = edgeBy * crossZ - edgeBz * crossY;
						@Pc(1112) float invRowY = edgeBz * crossX - edgeBx * crossZ;
						@Pc(1120) float invRowZ = edgeBx * crossY - edgeBy * crossX;
						@Pc(1134) float invDet = 1.0F / (invRowX * edgeAx + invRowY * edgeAy + invRowZ * edgeAz);
						uA = (invRowX * dAx + invRowY * dAy + invRowZ * dAz) * invDet;
						uB = (invRowX * dBx + invRowY * dBy + invRowZ * dBz) * invDet;
						scaleW = (invRowX * dCx + invRowY * dCy + invRowZ * dCz) * invDet;
						@Pc(1184) float invRow2X = edgeAy * crossZ - edgeAz * crossY;
						@Pc(1192) float invRow2Y = edgeAz * crossX - edgeAx * crossZ;
						@Pc(1200) float invRow2Z = edgeAx * crossY - edgeAy * crossX;
						@Pc(1214) float invDet2 = 1.0F / (invRow2X * edgeBx + invRow2Y * edgeBy + invRow2Z * edgeBz);
						vA = (invRow2X * dAx + invRow2Y * dAy + invRow2Z * dAz) * invDet2;
						scaleU = (invRow2X * dBx + invRow2Y * dBy + invRow2Z * dBz) * invDet2;
						scaleV = (invRow2X * dCx + invRow2Y * dCy + invRow2Z * dCz) * invDet2;
					} else {
						vertA = model.triangleVertexA[origIdx];
						vertB = model.triangleVertexB[origIdx];
						vertC = model.triangleVertexC[origIdx];
						@Pc(1276) int cx = centerX[texIdx];
						cy = centerY[texIdx];
						@Pc(1284) int cz = centerZ[texIdx];
						@Pc(1288) float[] matrix = matrices[texIdx];
						@Pc(1293) byte direction = model.textureDirection[texIdx];
						pz = (float) model.textureSpeed[texIdx] / 256.0F;
						if (texType == 1) {
							edgeAx = (float) (model.texturesScaleZ[texIdx] & 0xFFFF) / 1024.0F;
							calculateCylindricalUV(model.vertexX[vertA], model.vertexY[vertA], model.vertexZ[vertA], cx, cy, cz, matrix, edgeAx, direction, pz);
							uA = cylindricalU;
							vA = cylindricalV;
							calculateCylindricalUV(model.vertexX[vertB], model.vertexY[vertB], model.vertexZ[vertB], cx, cy, cz, matrix, edgeAx, direction, pz);
							uB = cylindricalU;
							scaleU = cylindricalV;
							calculateCylindricalUV(model.vertexX[vertC], model.vertexY[vertC], model.vertexZ[vertC], cx, cy, cz, matrix, edgeAx, direction, pz);
							scaleW = cylindricalU;
							scaleV = cylindricalV;
							edgeAy = edgeAx / 2.0F;
							if ((direction & 0x1) == 0) {
								if (uB - uA > edgeAy) {
									uB -= edgeAx;
									wrapB = 1;
								} else if (uA - uB > edgeAy) {
									uB += edgeAx;
									wrapB = 2;
								}
								if (scaleW - uA > edgeAy) {
									scaleW -= edgeAx;
									wrapC = 1;
								} else if (uA - scaleW > edgeAy) {
									scaleW += edgeAx;
									wrapC = 2;
								}
							} else {
								if (scaleU - vA > edgeAy) {
									scaleU -= edgeAx;
									wrapB = 1;
								} else if (vA - scaleU > edgeAy) {
									scaleU += edgeAx;
									wrapB = 2;
								}
								if (scaleV - vA > edgeAy) {
									scaleV -= edgeAx;
									wrapC = 1;
								} else if (vA - scaleV > edgeAy) {
									scaleV += edgeAx;
									wrapC = 2;
								}
							}
						} else if (texType == 2) {
							edgeAx = (float) model.textureTransU[texIdx] / 256.0F;
							edgeAy = (float) model.textureTransV[texIdx] / 256.0F;
							@Pc(1525) int abx = model.vertexX[vertB] - model.vertexX[vertA];
							@Pc(1535) int aby = model.vertexY[vertB] - model.vertexY[vertA];
							@Pc(1545) int abz = model.vertexZ[vertB] - model.vertexZ[vertA];
							@Pc(1555) int acx = model.vertexX[vertC] - model.vertexX[vertA];
							@Pc(1565) int acy = model.vertexY[vertC] - model.vertexY[vertA];
							@Pc(1575) int acz = model.vertexZ[vertC] - model.vertexZ[vertA];
							@Pc(1583) int normalX = aby * acz - acy * abz;
							@Pc(1591) int normalY = abz * acx - acz * abx;
							@Pc(1599) int normalZ = abx * acy - acx * aby;
							dBz = 64.0F / (float) (model.texturesScaleX[texIdx] & 0xFFFF);
							dCx = 64.0F / (float) (model.texturesScaleY[texIdx] & 0xFFFF);
							dCy = 64.0F / (float) (model.texturesScaleZ[texIdx] & 0xFFFF);
							dCz = ((float) normalX * matrix[0] + (float) normalY * matrix[1] + (float) normalZ * matrix[2]) / dBz;
							crossX = ((float) normalX * matrix[3] + (float) normalY * matrix[4] + (float) normalZ * matrix[5]) / dCx;
							crossY = ((float) normalX * matrix[6] + (float) normalY * matrix[7] + (float) normalZ * matrix[8]) / dCy;
							vz = getDominantAxis(dCz, crossX, crossY);
							calculatePlanarUV(model.vertexX[vertA], model.vertexY[vertA], model.vertexZ[vertA], cx, cy, cz, vz, matrix, direction, pz, edgeAx, edgeAy);
							uA = planarU;
							vA = planarV;
							calculatePlanarUV(model.vertexX[vertB], model.vertexY[vertB], model.vertexZ[vertB], cx, cy, cz, vz, matrix, direction, pz, edgeAx, edgeAy);
							uB = planarU;
							scaleU = planarV;
							calculatePlanarUV(model.vertexX[vertC], model.vertexY[vertC], model.vertexZ[vertC], cx, cy, cz, vz, matrix, direction, pz, edgeAx, edgeAy);
							scaleW = planarU;
							scaleV = planarV;
						} else if (texType == 3) {
							calculateSphericalUV(model.vertexX[vertA], model.vertexY[vertA], model.vertexZ[vertA], cx, cy, cz, matrix, direction, pz);
							uA = sphericalU;
							vA = sphericalV;
							calculateSphericalUV(model.vertexX[vertB], model.vertexY[vertB], model.vertexZ[vertB], cx, cy, cz, matrix, direction, pz);
							uB = sphericalU;
							scaleU = sphericalV;
							calculateSphericalUV(model.vertexX[vertC], model.vertexY[vertC], model.vertexZ[vertC], cx, cy, cz, matrix, direction, pz);
							scaleW = sphericalU;
							scaleV = sphericalV;
							if ((direction & 0x1) == 0) {
								if (uB - uA > 0.5F) {
									uB--;
									wrapB = 1;
								} else if (uA - uB > 0.5F) {
									uB++;
									wrapB = 2;
								}
								if (scaleW - uA > 0.5F) {
									scaleW--;
									wrapC = 1;
								} else if (uA - scaleW > 0.5F) {
									scaleW++;
									wrapC = 2;
								}
							} else {
								if (scaleU - vA > 0.5F) {
									scaleU--;
									wrapB = 1;
								} else if (vA - scaleU > 0.5F) {
									scaleU++;
									wrapB = 2;
								}
								if (scaleV - vA > 0.5F) {
									scaleV--;
									wrapC = 1;
								} else if (vA - scaleV > 0.5F) {
									scaleV++;
									wrapC = 2;
								}
							}
						}
					}
				}
			}
			model.calculateNormals();
			if (model.triangleInfo == null) {
				texType = 0;
			} else {
				texType = model.triangleInfo[origIdx];
			}
			if (texType == 0) {
				@Pc(1994) long normalKey = (long) (texIdx << 2) + ((long) (vz << 24) + (long) (color << 8) + (long) alpha << 32);
				vertC = model.triangleVertexA[origIdx];
				@Pc(2004) VertexNormal normalA = model.vertexNormals[vertC];
				this.triangleVertexA[n] = this.findOrCreateVertex(model, vertC, normalKey, normalA.x, normalA.y, normalA.z, normalA.magnitude, uA, vA);
				cy = model.triangleVertexB[origIdx];
				@Pc(2033) VertexNormal normalB = model.vertexNormals[cy];
				this.triangleVertexB[n] = this.findOrCreateVertex(model, cy, normalKey + (long) wrapB, normalB.x, normalB.y, normalB.z, normalB.magnitude, uB, scaleU);
				@Pc(2060) int vertIdxC = model.triangleVertexC[origIdx];
				@Pc(2065) VertexNormal normalC = model.vertexNormals[vertIdxC];
				this.triangleVertexC[n] = this.findOrCreateVertex(model, vertIdxC, normalKey + (long) wrapC, normalC.x, normalC.y, normalC.z, normalC.magnitude, scaleW, scaleV);
			} else if (texType == 1) {
				@Pc(2096) TriangleNormal triNormal = model.triangleNormals[origIdx];
				@Pc(2137) long flatKey = (long) ((texIdx << 2) + (triNormal.x > 0 ? 1024 : 2048) + (triNormal.y + 256 << 12) + (triNormal.z + 256 << 22)) + ((long) (vz << 24) + (long) (color << 8) + (long) alpha << 32);
				this.triangleVertexA[n] = this.findOrCreateVertex(model, model.triangleVertexA[origIdx], flatKey, triNormal.x, triNormal.y, triNormal.z, 0, uA, vA);
				this.triangleVertexB[n] = this.findOrCreateVertex(model, model.triangleVertexB[origIdx], flatKey + (long) wrapB, triNormal.x, triNormal.y, triNormal.z, 0, uB, scaleU);
				this.triangleVertexC[n] = this.findOrCreateVertex(model, model.triangleVertexC[origIdx], flatKey + (long) wrapC, triNormal.x, triNormal.y, triNormal.z, 0, scaleW, scaleV);
			}
			if (model.triangleTextures == null) {
				this.triangleTextures[n] = -1;
			} else {
				this.triangleTextures[n] = model.triangleTextures[origIdx];
			}
			if (this.triangleBones != null) {
				this.triangleBones[n] = (byte) model.triangleBones[origIdx];
			}
			this.triangleColors[n] = model.triangleColors[origIdx];
			if (model.triangleAlpha != null) {
				this.triangleAlpha[n] = model.triangleAlpha[origIdx];
			}
			if (model.triangleSources != null) {
				this.triangleSources[n] = model.triangleSources[origIdx];
			}
		}
		n = 0;
		@Pc(2271) short lastTexture = -10000;
		for (color = 0; color < this.triangleCount; color++) {
			tex = this.triangleTextures[color];
			if (tex != lastTexture) {
				n++;
				lastTexture = tex;
			}
		}
		this.textureGroupOffsets = new int[n + 1];
		n = 0;
		lastTexture = -10000;
		for (color = 0; color < this.triangleCount; color++) {
			tex = this.triangleTextures[color];
			if (tex != lastTexture) {
				this.textureGroupOffsets[n++] = color;
				lastTexture = tex;
			}
		}
		this.textureGroupOffsets[n] = this.triangleCount;
		aLongArray10 = null;
		this.normalX = ArrayUtils.copyOf(this.normalX, this.uniqueVertexCount);
		this.normalY = ArrayUtils.copyOf(this.normalY, this.uniqueVertexCount);
		this.normalZ = ArrayUtils.copyOf(this.normalZ, this.uniqueVertexCount);
		this.normalMagnitude = ArrayUtils.copyOf(this.normalMagnitude, this.uniqueVertexCount);
		this.vertexS = ArrayUtils.copyOf(this.vertexS, this.uniqueVertexCount);
		this.vertexT = ArrayUtils.copyOf(this.vertexT, this.uniqueVertexCount);
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(FFF)I")
	public static int getDominantAxis(@OriginalArg(0) float x, @OriginalArg(1) float y, @OriginalArg(2) float z) {
		@Pc(8) float absX = x < 0.0F ? -x : x;
		@Pc(17) float absY = y < 0.0F ? -y : y;
		@Pc(26) float absZ = z < 0.0F ? -z : z;
		if (absY > absX && absY > absZ) {
			return y > 0.0F ? 0 : 1;
		} else if (absZ > absX && absZ > absY) {
			return z > 0.0F ? 2 : 3;
		} else if (x > 0.0F) {
			return 4;
		} else {
			return 5;
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIII[FFIF)V")
	public static void calculateCylindricalUV(@OriginalArg(0) int vx, @OriginalArg(1) int vy, @OriginalArg(2) int vz, @OriginalArg(3) int cx, @OriginalArg(4) int cy, @OriginalArg(5) int cz, @OriginalArg(6) float[] matrix, @OriginalArg(7) float scale, @OriginalArg(8) int direction, @OriginalArg(9) float speed) {
		@Pc(3) int dx = vx - cx;
		@Pc(7) int dy = vy - cy;
		@Pc(11) int dz = vz - cz;
		@Pc(32) float tx = (float) dx * matrix[0] + (float) dy * matrix[1] + (float) dz * matrix[2];
		@Pc(53) float ty = (float) dx * matrix[3] + (float) dy * matrix[4] + (float) dz * matrix[5];
		@Pc(74) float tz = (float) dx * matrix[6] + (float) dy * matrix[7] + (float) dz * matrix[8];
		@Pc(85) float u = (float) Math.atan2(tx, tz) / 6.2831855F + 0.5F;
		if (scale != 1.0F) {
			u *= scale;
		}
		@Pc(99) float v = ty + speed + 0.5F;
		@Pc(104) float temp;
		if (direction == 1) {
			temp = u;
			u = -v;
			v = temp;
		} else if (direction == 2) {
			u = -u;
			v = -v;
		} else if (direction == 3) {
			temp = u;
			u = v;
			v = -temp;
		}
		cylindricalU = u;
		cylindricalV = v;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ISIB)I")
	public static int packColorToRGBA(@OriginalArg(0) int color, @OriginalArg(1) short texture, @OriginalArg(2) int lightness, @OriginalArg(3) byte alpha) {
		@Pc(5) int rgb = Rasteriser.palette[ColorUtils.multiplyLightness2(color, lightness)];
		if (texture != -1) {
			@Pc(15) int brightness = Rasteriser.textureProvider.getTextureBrightness(texture & 0xFFFF);
			@Pc(21) int white;
			@Pc(44) int blended;
			if (brightness != 0) {
				if (lightness < 0) {
					white = 0;
				} else if (lightness > 127) {
					white = 16777215;
				} else {
					white = lightness * 131586;
				}
				if (brightness == 256) {
					rgb = white;
				} else {
					blended = 256 - brightness;
					rgb = ((white & 0xFF00FF) * brightness + (rgb & 0xFF00FF) * blended & 0xFF00FF00) + ((white & 0xFF00) * brightness + (rgb & 0xFF00) * blended & 0xFF0000) >> 8;
				}
			}
			white = Rasteriser.textureProvider.getTextureSpeed(texture & 0xFFFF);
			if (white != 0) {
				white += 256;
				@Pc(92) int red = (rgb >> 16 & 0xFF) * white;
				if (red > 65535) {
					red = 65535;
				}
				blended = (rgb >> 8 & 0xFF) * white;
				if (blended > 65535) {
					blended = 65535;
				}
				@Pc(116) int blue = (rgb & 0xFF) * white;
				if (blue > 65535) {
					blue = 65535;
				}
				rgb = ((red & 0xFF00) << 8) + (blended & 0xFF00) + (blue >> 8);
			}
		}
		return (rgb << 8) + (255 - (alpha & 0xFF));
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIFFF)[F")
	public static float[] buildTextureTransformMatrix(@OriginalArg(0) int faceP, @OriginalArg(1) int faceM, @OriginalArg(2) int faceN, @OriginalArg(3) int rotation, @OriginalArg(4) float scaleX, @OriginalArg(5) float scaleY, @OriginalArg(6) float scaleZ) {
		@Pc(2) float[] yawMatrix = new float[9];
		@Pc(5) float[] result = new float[9];
		@Pc(13) float cos = (float) Math.cos((float) rotation * 0.024543693F);
		@Pc(21) float sin = (float) Math.sin((float) rotation * 0.024543693F);
		yawMatrix[0] = cos;
		yawMatrix[1] = 0.0F;
		yawMatrix[2] = sin;
		yawMatrix[3] = 0.0F;
		yawMatrix[4] = 1.0F;
		yawMatrix[5] = 0.0F;
		yawMatrix[6] = -sin;
		yawMatrix[7] = 0.0F;
		yawMatrix[8] = cos;
		@Pc(65) float[] tiltMatrix = new float[9];
		@Pc(67) float nz = 1.0F;
		@Pc(69) float nx = 0.0F;
		@Pc(74) float ny = (float) faceM / 32767.0F;
		@Pc(84) float sinTilt = -((float) Math.sqrt(1.0F - ny * ny));
		@Pc(88) float oneMinusCos = 1.0F - ny;
		@Pc(99) float magnitude = (float) Math.sqrt(faceP * faceP + faceN * faceN);
		if (magnitude == 0.0F && ny == 0.0F) {
			result = yawMatrix;
		} else {
			if (magnitude != 0.0F) {
				nz = (float) -faceN / magnitude;
				nx = (float) faceP / magnitude;
			}
			tiltMatrix[0] = ny + nz * nz * oneMinusCos;
			tiltMatrix[1] = nx * sinTilt;
			tiltMatrix[2] = nx * nz * oneMinusCos;
			tiltMatrix[3] = -nx * sinTilt;
			tiltMatrix[4] = ny;
			tiltMatrix[5] = nz * sinTilt;
			tiltMatrix[6] = nz * nx * oneMinusCos;
			tiltMatrix[7] = -nz * sinTilt;
			tiltMatrix[8] = ny + nx * nx * oneMinusCos;
			result[0] = yawMatrix[0] * tiltMatrix[0] + yawMatrix[1] * tiltMatrix[3] + yawMatrix[2] * tiltMatrix[6];
			result[1] = yawMatrix[0] * tiltMatrix[1] + yawMatrix[1] * tiltMatrix[4] + yawMatrix[2] * tiltMatrix[7];
			result[2] = yawMatrix[0] * tiltMatrix[2] + yawMatrix[1] * tiltMatrix[5] + yawMatrix[2] * tiltMatrix[8];
			result[3] = yawMatrix[3] * tiltMatrix[0] + yawMatrix[4] * tiltMatrix[3] + yawMatrix[5] * tiltMatrix[6];
			result[4] = yawMatrix[3] * tiltMatrix[1] + yawMatrix[4] * tiltMatrix[4] + yawMatrix[5] * tiltMatrix[7];
			result[5] = yawMatrix[3] * tiltMatrix[2] + yawMatrix[4] * tiltMatrix[5] + yawMatrix[5] * tiltMatrix[8];
			result[6] = yawMatrix[6] * tiltMatrix[0] + yawMatrix[7] * tiltMatrix[3] + yawMatrix[8] * tiltMatrix[6];
			result[7] = yawMatrix[6] * tiltMatrix[1] + yawMatrix[7] * tiltMatrix[4] + yawMatrix[8] * tiltMatrix[7];
			result[8] = yawMatrix[6] * tiltMatrix[2] + yawMatrix[7] * tiltMatrix[5] + yawMatrix[8] * tiltMatrix[8];
		}
		result[0] *= scaleX;
		result[1] *= scaleX;
		result[2] *= scaleX;
		result[3] *= scaleY;
		result[4] *= scaleY;
		result[5] *= scaleY;
		result[6] *= scaleZ;
		result[7] *= scaleZ;
		result[8] *= scaleZ;
		return result;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIII[FIF)V")
	public static void calculateSphericalUV(@OriginalArg(0) int vx, @OriginalArg(1) int vy, @OriginalArg(2) int vz, @OriginalArg(3) int cx, @OriginalArg(4) int cy, @OriginalArg(5) int cz, @OriginalArg(6) float[] matrix, @OriginalArg(7) int direction, @OriginalArg(8) float speed) {
		@Pc(3) int dx = vx - cx;
		@Pc(7) int dy = vy - cy;
		@Pc(11) int dz = vz - cz;
		@Pc(32) float tx = (float) dx * matrix[0] + (float) dy * matrix[1] + (float) dz * matrix[2];
		@Pc(53) float ty = (float) dx * matrix[3] + (float) dy * matrix[4] + (float) dz * matrix[5];
		@Pc(74) float tz = (float) dx * matrix[6] + (float) dy * matrix[7] + (float) dz * matrix[8];
		@Pc(89) float magnitude = (float) Math.sqrt(tx * tx + ty * ty + tz * tz);
		@Pc(100) float u = (float) Math.atan2(tx, tz) / 6.2831855F + 0.5F;
		@Pc(113) float v = (float) Math.asin(ty / magnitude) / 3.1415927F + speed + 0.5F;
		@Pc(118) float temp;
		if (direction == 1) {
			temp = u;
			u = -v;
			v = temp;
		} else if (direction == 2) {
			u = -u;
			v = -v;
		} else if (direction == 3) {
			temp = u;
			u = v;
			v = -temp;
		}
		sphericalU = u;
		sphericalV = v;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIIII[FIFFF)V")
	public static void calculatePlanarUV(@OriginalArg(0) int vx, @OriginalArg(1) int vy, @OriginalArg(2) int vz, @OriginalArg(3) int cx, @OriginalArg(4) int cy, @OriginalArg(5) int cz, @OriginalArg(6) int axis, @OriginalArg(7) float[] matrix, @OriginalArg(8) int direction, @OriginalArg(9) float speed, @OriginalArg(10) float transU, @OriginalArg(11) float transV) {
		@Pc(3) int dx = vx - cx;
		@Pc(7) int dy = vy - cy;
		@Pc(11) int dz = vz - cz;
		@Pc(32) float tx = (float) dx * matrix[0] + (float) dy * matrix[1] + (float) dz * matrix[2];
		@Pc(53) float ty = (float) dx * matrix[3] + (float) dy * matrix[4] + (float) dz * matrix[5];
		@Pc(74) float tz = (float) dx * matrix[6] + (float) dy * matrix[7] + (float) dz * matrix[8];
		@Pc(82) float u;
		@Pc(89) float v;
		if (axis == 0) {
			u = tx + speed + 0.5F;
			v = transV + 0.5F - tz;
		} else if (axis == 1) {
			u = tx + speed + 0.5F;
			v = tz + transV + 0.5F;
		} else if (axis == 2) {
			u = speed + 0.5F - tx;
			v = transU + 0.5F - ty;
		} else if (axis == 3) {
			u = tx + speed + 0.5F;
			v = transU + 0.5F - ty;
		} else if (axis == 4) {
			u = tz + transV + 0.5F;
			v = transU + 0.5F - ty;
		} else {
			u = transV + 0.5F - tz;
			v = transU + 0.5F - ty;
		}
		@Pc(177) float temp;
		if (direction == 1) {
			temp = u;
			u = -v;
			v = temp;
		} else if (direction == 2) {
			u = -u;
			v = -v;
		} else if (direction == 3) {
			temp = u;
			u = v;
			v = -temp;
		}
		planarU = u;
		planarV = v;
	}

	@OriginalMember(owner = "client!td", name = "v", descriptor = "()V")
	public static void initCopyTargets() {
		entityCopyScratch = new GlModel();
		entityCopyTarget = new GlModel();
		animCopyScratch = new GlModel();
		animCopyTarget = new GlModel();
		locCopyScratch = new GlModel();
		locCopyTarget = new GlModel();
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public final Model copyForAnimation(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		return this.copyToTarget(shareAlpha, shareColors, shareNormals, animCopyTarget, animCopyScratch);
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinCameraPitch, @OriginalArg(2) int cosCameraPitch, @OriginalArg(3) int sinCameraYaw, @OriginalArg(4) int cosCameraYaw, @OriginalArg(5) int sceneX, @OriginalArg(6) int sceneY, @OriginalArg(7) int sceneZ, @OriginalArg(8) long key, @OriginalArg(9) int plane, @OriginalArg(10) ParticleSystem particleSystem) {
		if (this.uniqueVertexCount == 0) {
			return;
		}
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		@Pc(13) short cylinderRadius = this.bounds.cylinderRadius;
		@Pc(17) short minY = this.bounds.minY;
		@Pc(21) short maxY = this.bounds.maxY;
		@Pc(31) int rotatedZ = sceneZ * cosCameraYaw - sceneX * sinCameraYaw >> 16;
		@Pc(41) int projectedDepth = sceneY * sinCameraPitch + rotatedZ * cosCameraPitch >> 16;
		@Pc(53) int nearClip = projectedDepth + (cylinderRadius * cosCameraPitch + maxY * sinCameraPitch >> 16);
		if (nearClip <= 50) {
			return;
		}
		@Pc(70) int farClip = projectedDepth + (-cylinderRadius * cosCameraPitch + minY * sinCameraPitch >> 16);
		if (farClip >= GlobalConfig.VIEW_DISTANCE) {
			return;
		}
		@Pc(84) int projectedX = sceneZ * sinCameraYaw + sceneX * cosCameraYaw >> 16;
		@Pc(90) int rightEdge = projectedX + cylinderRadius << 9;
		if (rightEdge / nearClip <= Rasteriser.screenLowerX) {
			return;
		}
		@Pc(102) int leftEdge = projectedX - cylinderRadius << 9;
		if (leftEdge / nearClip >= Rasteriser.screenUpperX) {
			return;
		}
		@Pc(118) int projectedY = sceneY * cosCameraPitch - rotatedZ * sinCameraPitch >> 16;
		@Pc(132) int topEdge = projectedY + (cylinderRadius * sinCameraPitch + maxY * cosCameraPitch >> 16) << 9;
		if (topEdge / nearClip <= Rasteriser.screenLowerY) {
			return;
		}
		@Pc(153) int bottomEdge = projectedY + (-cylinderRadius * sinCameraPitch + minY * cosCameraPitch >> 16) << 9;
		if (bottomEdge / nearClip >= Rasteriser.screenUpperY) {
			return;
		}
		@Pc(161) int sinYaw = 0;
		@Pc(163) int cosYaw = 0;
		if (yaw != 0) {
			sinYaw = MathUtils.sin[yaw];
			cosYaw = MathUtils.cos[yaw];
		}
		@Pc(165) boolean roofVisibilityLocPick = API.IsRoofVisibilityLocPickable(key);
		@Pc(168) boolean miniMenuPick = key > 0L;
		if ((miniMenuPick || roofVisibilityLocPick) && RawModel.allowInput && farClip > 0) {
			@Pc(187) int minScreenX;
			@Pc(191) int maxScreenX;
			if (projectedX > 0) {
				minScreenX = leftEdge / nearClip;
				maxScreenX = rightEdge / farClip;
			} else {
				minScreenX = leftEdge / farClip;
				maxScreenX = rightEdge / nearClip;
			}
			@Pc(206) int minScreenY;
			@Pc(210) int maxScreenY;
			if (projectedY > 0) {
				minScreenY = bottomEdge / nearClip;
				maxScreenY = topEdge / farClip;
			} else {
				minScreenY = bottomEdge / farClip;
				maxScreenY = topEdge / nearClip;
			}
			if (pickScreenX >= minScreenX && pickScreenX <= maxScreenX && RawModel.pickScreenY >= minScreenY && RawModel.pickScreenY <= maxScreenY) {
				minScreenX = 999999;
				maxScreenX = -999999;
				minScreenY = 999999;
				maxScreenY = -999999;
				@Pc(243) short boundsMinX = this.bounds.minX;
				@Pc(247) short boundsMaxX = this.bounds.maxX;
				@Pc(251) short boundsMinZ = this.bounds.minZ;
				@Pc(255) short boundsMaxZ = this.bounds.maxZ;
				@Pc(290) int[] cornersX = new int[]{boundsMinX, boundsMaxX, boundsMinX, boundsMaxX, boundsMinX, boundsMaxX, boundsMinX, boundsMaxX};
				@Pc(325) int[] cornersZ = new int[]{boundsMinZ, boundsMinZ, boundsMaxZ, boundsMaxZ, boundsMinZ, boundsMinZ, boundsMaxZ, boundsMaxZ};
				@Pc(360) int[] cornersY = new int[]{minY, minY, minY, minY, maxY, maxY, maxY, maxY};
				@Pc(362) int ci;
				@Pc(369) int cx;
				@Pc(373) int cy;
				@Pc(377) int cz;
				@Pc(389) int temp;
				@Pc(465) int screenX;
				@Pc(471) int screenY;
				for (ci = 0; ci < 8; ci++) {
					cx = cornersX[ci];
					cy = cornersY[ci];
					cz = cornersZ[ci];
					if (yaw != 0) {
						temp = cz * sinYaw + cx * cosYaw >> 16;
						cz = cz * cosYaw - cx * sinYaw >> 16;
						cx = temp;
					}
					cx += sceneX;
					cy += sceneY;
					cz += sceneZ;
					temp = cz * sinCameraYaw + cx * cosCameraYaw >> 16;
					cz = cz * cosCameraYaw - cx * sinCameraYaw >> 16;
					cx = temp;
					temp = cy * cosCameraPitch - cz * sinCameraPitch >> 16;
					cz = cy * sinCameraPitch + cz * cosCameraPitch >> 16;
					if (cz > 0) {
						screenX = (cx << 9) / cz;
						screenY = (temp << 9) / cz;
						if (screenX < minScreenX) {
							minScreenX = screenX;
						}
						if (screenX > maxScreenX) {
							maxScreenX = screenX;
						}
						if (screenY < minScreenY) {
							minScreenY = screenY;
						}
						if (screenY > maxScreenY) {
							maxScreenY = screenY;
						}
					}
				}
				if (pickScreenX >= minScreenX && pickScreenX <= maxScreenX && RawModel.pickScreenY >= minScreenY && RawModel.pickScreenY <= maxScreenY) {
					if (this.pickable) {
						if (miniMenuPick) {
							Model.pickResults[MiniMenu.pickResultCount++] = key;
						}
						if (roofVisibilityLocPick) {
							API.ReportRoofVisibilityLoc(key, plane);
						}
					} else {
						if (this.vertexX == null || this.vertexY == null || this.vertexZ == null || this.vertexOffsets == null || this.vertexLookup == null || this.triangleVertexA == null || this.triangleVertexB == null || this.triangleVertexC == null) {
							if (roofVisibilityLocPick) {
								API.ReportRoofVisibilityLoc(key, plane);
							}
						} else {
							if (pickVertexScreenX.length < this.uniqueVertexCount) {
								pickVertexScreenX = new int[this.uniqueVertexCount];
								pickVertexScreenY = new int[this.uniqueVertexCount];
							}
							ci = 0;
							processRoofCheck:
							while (true) {
								if (ci >= this.vertexCount) {
									ci = 0;
									while (true) {
										if (ci >= this.triangleCount) {
											break processRoofCheck;
										}
										@Pc(698) short triA = this.triangleVertexA[ci];
										@Pc(703) short triB = this.triangleVertexB[ci];
										@Pc(708) short triC = this.triangleVertexC[ci];
										if (this.pointWithinTriangle(pickScreenX, RawModel.pickScreenY, pickVertexScreenY[triA], pickVertexScreenY[triB], pickVertexScreenY[triC], pickVertexScreenX[triA], pickVertexScreenX[triB], pickVertexScreenX[triC])) {
											if (miniMenuPick) {
												Model.pickResults[MiniMenu.pickResultCount++] = key;
											}
											if (roofVisibilityLocPick) {
												API.ReportRoofVisibilityLoc(key, plane);
											}
											break processRoofCheck;
										}
										ci++;
									}
								}
								cx = this.vertexX[ci];
								cy = this.vertexY[ci];
								cz = this.vertexZ[ci];
								if (yaw != 0) {
									temp = cz * sinYaw + cx * cosYaw >> 16;
									cz = cz * cosYaw - cx * sinYaw >> 16;
									cx = temp;
								}
								cx += sceneX;
								cy += sceneY;
								cz += sceneZ;
								temp = cz * sinCameraYaw + cx * cosCameraYaw >> 16;
								cz = cz * cosCameraYaw - cx * sinCameraYaw >> 16;
								cx = temp;
								temp = cy * cosCameraPitch - cz * sinCameraPitch >> 16;
								cz = cy * sinCameraPitch + cz * cosCameraPitch >> 16;
								if (cz < 50) {
									break;
								}
								screenX = (cx << 9) / cz;
								screenY = (temp << 9) / cz;
								@Pc(652) int offsetStart = this.vertexOffsets[ci];
								@Pc(659) int offsetEnd = this.vertexOffsets[ci + 1];
								for (@Pc(661) int oi = offsetStart; oi < offsetEnd; oi++) {
									@Pc(671) int uniqueIdx = this.vertexLookup[oi] - 1;
									if (uniqueIdx == -1) {
										break;
									}
									pickVertexScreenX[uniqueIdx] = screenX;
									pickVertexScreenY[uniqueIdx] = screenY;
								}
								ci++;
							}
						}
					}
				}
			}
		}
		@Pc(744) GL2 gl = GlRenderer.gl;
		gl.glPushMatrix();
		gl.glTranslatef((float) sceneX, (float) sceneY, (float) sceneZ);
		gl.glRotatef((float) yaw * 0.17578125F, 0.0F, 1.0F, 0.0F);
		this.ensureBuffersAndDraw();
		gl.glRotatef((float) -yaw * 0.17578125F, 0.0F, 1.0F, 0.0F);
		gl.glTranslatef((float) -sceneX, (float) -sceneY, (float) -sceneZ);
		gl.glPopMatrix();
	}

	@OriginalMember(owner = "client!td", name = "m", descriptor = "()V")
	public final void rotateClockwiseAll() {
		if (this.normalX == null) {
			this.rotateClockwise();
			return;
		}
		@Pc(7) int i;
		for (i = 0; i < this.vertexCount; i++) {
			@Pc(16) int temp = this.vertexZ[i];
			this.vertexZ[i] = this.vertexX[i];
			this.vertexX[i] = -temp;
		}
		for (i = 0; i < this.uniqueVertexCount; i++) {
			@Pc(43) short tempN = this.normalZ[i];
			this.normalZ[i] = this.normalX[i];
			this.normalX[i] = (short) -tempN;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "n", descriptor = "()I")
	public final int getAmbientIntensity() {
		return this.ambient;
	}

	@OriginalMember(owner = "client!td", name = "c", descriptor = "(I)V")
	@Override
	public final void rotateZ(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newX = this.vertexY[i] * sinAngle + this.vertexX[i] * cosAngle >> 16;
			this.vertexY[i] = this.vertexY[i] * cosAngle - this.vertexX[i] * sinAngle >> 16;
			this.vertexX[i] = newX;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "g", descriptor = "()I")
	@Override
	public final int getMaxX() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.maxX;
	}

	@OriginalMember(owner = "client!td", name = "c", descriptor = "()I")
	@Override
	public final int getMaxZ() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.maxZ;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "()Z")
	@Override
	public final boolean canMerge() {
		return this.mergeable && this.vertexX != null && this.normalX != null;
	}

	@OriginalMember(owner = "client!td", name = "k", descriptor = "()I")
	@Override
	public final int getMinZ() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.minZ;
	}

	@OriginalMember(owner = "client!td", name = "c", descriptor = "(III)V")
	@Override
	public final void translate(@OriginalArg(0) int dx, @OriginalArg(1) int dy, @OriginalArg(2) int dz) {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] += dx;
			this.vertexY[i] += dy;
			this.vertexZ[i] += dz;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "b", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public final Model copyForLoc(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		return this.copyToTarget(shareAlpha, shareColors, shareNormals, locCopyTarget, locCopyScratch);
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(Lclient!th;IIIZ)V")
	@Override
	public final void mergeNormals(@OriginalArg(0) Entity other, @OriginalArg(1) int offsetX, @OriginalArg(2) int offsetY, @OriginalArg(3) int offsetZ, @OriginalArg(4) boolean recolor) {
		@Pc(2) GlModel otherModel = (GlModel) other;
		if (this.triangleCount == 0 || otherModel.triangleCount == 0) {
			return;
		}
		@Pc(12) int otherVertexCount = otherModel.vertexCount;
		@Pc(15) int[] otherVertexX = otherModel.vertexX;
		@Pc(18) int[] otherVertexY = otherModel.vertexY;
		@Pc(21) int[] otherVertexZ = otherModel.vertexZ;
		@Pc(24) short[] otherNormalX = otherModel.normalX;
		@Pc(27) short[] otherNormalY = otherModel.normalY;
		@Pc(30) short[] otherNormalZ = otherModel.normalZ;
		@Pc(33) short[] otherNormalMag = otherModel.normalMagnitude;
		@Pc(40) short[] recolorSrc;
		@Pc(44) short[] recolorDst;
		@Pc(48) short[] retextureSrc;
		@Pc(52) short[] retextureDst;
		if (this.recoloring == null) {
			recolorSrc = null;
			recolorDst = null;
			retextureSrc = null;
			retextureDst = null;
		} else {
			recolorSrc = this.recoloring.recolorSrc;
			recolorDst = this.recoloring.recolorDst;
			retextureSrc = this.recoloring.retextureSrc;
			retextureDst = this.recoloring.retextureDst;
		}
		@Pc(68) short[] otherRecolorSrc;
		@Pc(72) short[] otherRecolorDst;
		@Pc(76) short[] otherRetextureSrc;
		@Pc(80) short[] otherRetextureDst;
		if (otherModel.recoloring == null) {
			otherRecolorSrc = null;
			otherRecolorDst = null;
			otherRetextureSrc = null;
			otherRetextureDst = null;
		} else {
			otherRecolorSrc = otherModel.recoloring.recolorSrc;
			otherRecolorDst = otherModel.recoloring.recolorDst;
			otherRetextureSrc = otherModel.recoloring.retextureSrc;
			otherRetextureDst = otherModel.recoloring.retextureDst;
		}
		@Pc(92) int[] otherOffsets = otherModel.vertexOffsets;
		@Pc(95) short[] otherLookup = otherModel.vertexLookup;
		if (!otherModel.bounds.valid) {
			otherModel.calculateBounds();
		}
		@Pc(105) short otherMinY = otherModel.bounds.minY;
		@Pc(109) short otherMaxY = otherModel.bounds.maxY;
		@Pc(113) short otherMinX = otherModel.bounds.minX;
		@Pc(117) short otherMaxX = otherModel.bounds.maxX;
		@Pc(121) short otherMinZ = otherModel.bounds.minZ;
		@Pc(125) short otherMaxZ = otherModel.bounds.maxZ;
		for (@Pc(127) int i = 0; i < this.vertexCount; i++) {
			@Pc(138) int dy = this.vertexY[i] - offsetY;
			if (dy >= otherMinY && dy <= otherMaxY) {
				@Pc(152) int dx = this.vertexX[i] - offsetX;
				if (dx >= otherMinX && dx <= otherMaxX) {
					@Pc(166) int dz = this.vertexZ[i] - offsetZ;
					if (dz >= otherMinZ && dz <= otherMaxZ) {
						@Pc(175) int thisUniqueIdx = -1;
						@Pc(180) int startOff = this.vertexOffsets[i];
						@Pc(187) int endOff = this.vertexOffsets[i + 1];
						@Pc(189) int j;
						for (j = startOff; j < endOff; j++) {
							thisUniqueIdx = this.vertexLookup[j] - 1;
							if (thisUniqueIdx == -1 || this.normalMagnitude[thisUniqueIdx] != 0) {
								break;
							}
						}
						if (thisUniqueIdx != -1) {
							for (j = 0; j < otherVertexCount; j++) {
								if (dx == otherVertexX[j] && dz == otherVertexZ[j] && dy == otherVertexY[j]) {
									@Pc(237) int otherUniqueIdx = -1;
									startOff = otherOffsets[j];
									endOff = otherOffsets[j + 1];
									for (@Pc(249) int k = startOff; k < endOff; k++) {
										otherUniqueIdx = otherLookup[k] - 1;
										if (otherUniqueIdx == -1 || otherNormalMag[otherUniqueIdx] != 0) {
											break;
										}
									}
									if (otherUniqueIdx != -1) {
										if (recolorSrc == null) {
											this.recoloring = new GlModelRecoloring();
											recolorSrc = this.recoloring.recolorSrc = ArrayUtils.copyOfNullable(this.normalX);
											recolorDst = this.recoloring.recolorDst = ArrayUtils.copyOfNullable(this.normalY);
											retextureSrc = this.recoloring.retextureSrc = ArrayUtils.copyOfNullable(this.normalZ);
											retextureDst = this.recoloring.retextureDst = ArrayUtils.copyOfNullable(this.normalMagnitude);
										}
										if (otherRecolorSrc == null) {
											@Pc(325) GlModelRecoloring otherRecoloring = otherModel.recoloring = new GlModelRecoloring();
											otherRecolorSrc = otherRecoloring.recolorSrc = ArrayUtils.copyOfNullable(otherNormalX);
											otherRecolorDst = otherRecoloring.recolorDst = ArrayUtils.copyOfNullable(otherNormalY);
											otherRetextureSrc = otherRecoloring.retextureSrc = ArrayUtils.copyOfNullable(otherNormalZ);
											otherRetextureDst = otherRecoloring.retextureDst = ArrayUtils.copyOfNullable(otherNormalMag);
										}
										@Pc(358) short nx = this.normalX[thisUniqueIdx];
										@Pc(363) short ny = this.normalY[thisUniqueIdx];
										@Pc(368) short nz = this.normalZ[thisUniqueIdx];
										@Pc(373) short nm = this.normalMagnitude[thisUniqueIdx];
										startOff = otherOffsets[j];
										endOff = otherOffsets[j + 1];
										@Pc(385) int ri;
										@Pc(394) int uniqueIdx;
										for (ri = startOff; ri < endOff; ri++) {
											uniqueIdx = otherLookup[ri] - 1;
											if (uniqueIdx == -1) {
												break;
											}
											if (otherRetextureDst[uniqueIdx] != 0) {
												otherRecolorSrc[uniqueIdx] += nx;
												otherRecolorDst[uniqueIdx] += ny;
												otherRetextureSrc[uniqueIdx] += nz;
												otherRetextureDst[uniqueIdx] += nm;
											}
										}
										nx = otherNormalX[otherUniqueIdx];
										ny = otherNormalY[otherUniqueIdx];
										nz = otherNormalZ[otherUniqueIdx];
										nm = otherNormalMag[otherUniqueIdx];
										startOff = this.vertexOffsets[i];
										endOff = this.vertexOffsets[i + 1];
										for (ri = startOff; ri < endOff; ri++) {
											uniqueIdx = this.vertexLookup[ri] - 1;
											if (uniqueIdx == -1) {
												break;
											}
											if (retextureDst[uniqueIdx] != 0) {
												recolorSrc[uniqueIdx] += nx;
												recolorDst[uniqueIdx] += ny;
												retextureSrc[uniqueIdx] += nz;
												retextureDst[uniqueIdx] += nm;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIIIIJ)V")
	@Override
	public final void setCamera(@OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int pitch, @OriginalArg(4) int offsetX, @OriginalArg(5) int offsetY, @OriginalArg(6) int offsetZ, @OriginalArg(7) long key) {
		if (this.uniqueVertexCount == 0) {
			return;
		}
		@Pc(5) GL2 gl = GlRenderer.gl;
		gl.glPushMatrix();
		if (pitch != 0) {
			gl.glRotatef((float) pitch * 0.17578125F, 1.0F, 0.0F, 0.0F);
		}
		gl.glTranslatef((float) offsetX, (float) offsetY, (float) offsetZ);
		if (yaw != 0) {
			gl.glRotatef((float) yaw * 0.17578125F, 0.0F, 1.0F, 0.0F);
		}
		if (roll != 0) {
			gl.glRotatef((float) -roll * 0.17578125F, 0.0F, 0.0F, 1.0F);
		}
		this.ensureBuffersAndDraw();
		gl.glPopMatrix();
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(Lclient!gb;IJIIIIFF)S")
	private short findOrCreateVertex(@OriginalArg(0) RawModel model, @OriginalArg(1) int vertexIndex, @OriginalArg(2) long key, @OriginalArg(3) int normalX, @OriginalArg(4) int normalY, @OriginalArg(5) int normalZ, @OriginalArg(6) int normalMagnitude, @OriginalArg(7) float u, @OriginalArg(8) float v) {
		@Pc(4) int startOffset = this.vertexOffsets[vertexIndex];
		@Pc(11) int endOffset = this.vertexOffsets[vertexIndex + 1];
		@Pc(13) int insertIdx = 0;
		for (@Pc(15) int i = startOffset; i < endOffset; i++) {
			@Pc(23) short existing = this.vertexLookup[i];
			if (existing == 0) {
				insertIdx = i;
				break;
			}
			if (aLongArray10[i] == key) {
				return (short) (existing - 1);
			}
		}
		this.vertexLookup[insertIdx] = (short) (this.uniqueVertexCount + 1);
		aLongArray10[insertIdx] = key;
		this.normalX[this.uniqueVertexCount] = (short) normalX;
		this.normalY[this.uniqueVertexCount] = (short) normalY;
		this.normalZ[this.uniqueVertexCount] = (short) normalZ;
		this.normalMagnitude[this.uniqueVertexCount] = (short) normalMagnitude;
		this.vertexS[this.uniqueVertexCount] = u;
		this.vertexT[this.uniqueVertexCount] = v;
		return (short) this.uniqueVertexCount++;
	}

	@OriginalMember(owner = "client!td", name = "b", descriptor = "(I)V")
	@Override
	public final void rotateY(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newX = this.vertexZ[i] * sinAngle + this.vertexX[i] * cosAngle >> 16;
			this.vertexZ[i] = this.vertexZ[i] * cosAngle - this.vertexX[i] * sinAngle >> 16;
			this.vertexX[i] = newX;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(I[IIIIZI[I)V")
	@Override
	protected final void transformMaskedBone(@OriginalArg(0) int transformType, @OriginalArg(1) int[] labels, @OriginalArg(2) int transformX, @OriginalArg(3) int transformY, @OriginalArg(4) int transformZ, @OriginalArg(5) boolean delayed, @OriginalArg(6) int mask, @OriginalArg(7) int[] matrix) {
		@Pc(2) int labelCount = labels.length;
		@Pc(18) int count;
		@Pc(26) int i;
		@Pc(45) int j;
		@Pc(53) int vertIdx;
		@Pc(33) int rz;
		if (transformType == 0) {
			transformX <<= 0x4;
			transformY <<= 0x4;
			transformZ <<= 0x4;
			count = 0;
			originX = 0;
			originY = 0;
			originZ = 0;
			for (i = 0; i < labelCount; i++) {
				rz = labels[i];
				if (rz < this.boneVertices.length) {
					@Pc(43) int[] boneVerts = this.boneVertices[rz];
					for (j = 0; j < boneVerts.length; j++) {
						vertIdx = boneVerts[j];
						if (this.vertexSources == null || (mask & this.vertexSources[vertIdx]) != 0) {
							originX += this.vertexX[vertIdx];
							originY += this.vertexY[vertIdx];
							originZ += this.vertexZ[vertIdx];
							count++;
						}
					}
				}
			}
			if (count > 0) {
				originX = originX / count + transformX;
				originY = originY / count + transformY;
				originZ = originZ / count + transformZ;
				originValid = true;
			} else {
				originX = transformX;
				originY = transformY;
				originZ = transformZ;
			}
			return;
		}
		@Pc(228) int[] verts;
		@Pc(230) int k;
		if (transformType == 1) {
			if (matrix != null) {
				count = matrix[0] * transformX + matrix[1] * transformY + matrix[2] * transformZ + 16384 >> 15;
				i = matrix[3] * transformX + matrix[4] * transformY + matrix[5] * transformZ + 16384 >> 15;
				rz = matrix[6] * transformX + matrix[7] * transformY + matrix[8] * transformZ + 16384 >> 15;
				transformX = count;
				transformY = i;
				transformZ = rz;
			}
			transformX <<= 0x4;
			transformY <<= 0x4;
			transformZ <<= 0x4;
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					verts = this.boneVertices[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						if (this.vertexSources == null || (mask & this.vertexSources[j]) != 0) {
							this.vertexX[j] += transformX;
							this.vertexY[j] += transformY;
							this.vertexZ[j] += transformZ;
						}
					}
				}
			}
			return;
		}
		@Pc(343) int rx;
		@Pc(365) int ry;
		@Pc(387) int rResult;
		@Pc(428) int sinAngle;
		@Pc(434) int cosAngle;
		@Pc(440) int rotTemp;
		@Pc(446) int sinZ;
		@Pc(454) int cosTimeSinZ;
		@Pc(462) int sinTimesZ;
		@Pc(615) int relY;
		@Pc(640) int relZ;
		@Pc(644) int pivotX;
		@Pc(652) int pivotZ;
		@Pc(657) int mi;
		@Pc(662) int mj;
		@Pc(667) int sum;
		@Pc(789) int[] finalMatrix;
		@Pc(791) int fi;
		@Pc(796) int fj;
		@Pc(801) int fk;
		@Pc(803) int li;
		@Pc(929) int boneId;
		if (transformType == 2) {
			if (matrix == null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneVertices.length) {
						verts = this.boneVertices[i];
						for (k = 0; k < verts.length; k++) {
							j = verts[k];
							if (this.vertexSources == null || (mask & this.vertexSources[j]) != 0) {
								this.vertexX[j] -= originX;
								this.vertexY[j] -= originY;
								this.vertexZ[j] -= originZ;
								if (transformZ != 0) {
									vertIdx = MathUtils.sin[transformZ];
									rx = MathUtils.cos[transformZ];
									ry = this.vertexY[j] * vertIdx + this.vertexX[j] * rx + 32767 >> 16;
									this.vertexY[j] = this.vertexY[j] * rx + 32767 - this.vertexX[j] * vertIdx >> 16;
									this.vertexX[j] = ry;
								}
								if (transformX != 0) {
									vertIdx = MathUtils.sin[transformX];
									rx = MathUtils.cos[transformX];
									ry = this.vertexY[j] * rx + 32767 - this.vertexZ[j] * vertIdx >> 16;
									this.vertexZ[j] = this.vertexY[j] * vertIdx + this.vertexZ[j] * rx + 32767 >> 16;
									this.vertexY[j] = ry;
								}
								if (transformY != 0) {
									vertIdx = MathUtils.sin[transformY];
									rx = MathUtils.cos[transformY];
									ry = this.vertexZ[j] * vertIdx + this.vertexX[j] * rx + 32767 >> 16;
									this.vertexZ[j] = this.vertexZ[j] * rx + 32767 - this.vertexX[j] * vertIdx >> 16;
									this.vertexX[j] = ry;
								}
								this.vertexX[j] += originX;
								this.vertexY[j] += originY;
								this.vertexZ[j] += originZ;
							}
						}
					}
				}
				if (delayed && this.normalX != null) {
					for (count = 0; count < labelCount; count++) {
						i = labels[count];
						if (i < this.boneVertices.length) {
							verts = this.boneVertices[i];
							for (k = 0; k < verts.length; k++) {
								j = verts[k];
								if (this.vertexSources == null || (mask & this.vertexSources[j]) != 0) {
									vertIdx = this.vertexOffsets[j];
									rx = this.vertexOffsets[j + 1];
									for (ry = vertIdx; ry < rx; ry++) {
										rResult = this.vertexLookup[ry] - 1;
										if (rResult == -1) {
											break;
										}
										if (transformZ != 0) {
											sinAngle = MathUtils.sin[transformZ];
											cosAngle = MathUtils.cos[transformZ];
											rotTemp = this.normalY[rResult] * sinAngle + this.normalX[rResult] * cosAngle + 32767 >> 16;
											this.normalY[rResult] = (short) (this.normalY[rResult] * cosAngle + 32767 - this.normalX[rResult] * sinAngle >> 16);
											this.normalX[rResult] = (short) rotTemp;
										}
										if (transformX != 0) {
											sinAngle = MathUtils.sin[transformX];
											cosAngle = MathUtils.cos[transformX];
											rotTemp = this.normalY[rResult] * cosAngle + 32767 - this.normalZ[rResult] * sinAngle >> 16;
											this.normalZ[rResult] = (short) (this.normalY[rResult] * sinAngle + this.normalZ[rResult] * cosAngle + 32767 >> 16);
											this.normalY[rResult] = (short) rotTemp;
										}
										if (transformY != 0) {
											sinAngle = MathUtils.sin[transformY];
											cosAngle = MathUtils.cos[transformY];
											rotTemp = this.normalZ[rResult] * sinAngle + this.normalX[rResult] * cosAngle + 32767 >> 16;
											this.normalZ[rResult] = (short) (this.normalZ[rResult] * cosAngle + 32767 - this.normalX[rResult] * sinAngle >> 16);
											this.normalX[rResult] = (short) rotTemp;
										}
									}
								}
							}
						}
					}
					if (this.normalsBuffer != null) {
						this.normalsBuffer.valid = false;
					}
				}
			} else {
				count = matrix[9] << 4;
				i = matrix[10] << 4;
				rz = matrix[11] << 4;
				k = matrix[12] << 4;
				j = matrix[13] << 4;
				vertIdx = matrix[14] << 4;
				if (originValid) {
					rx = matrix[0] * originX + matrix[3] * originY + matrix[6] * originZ + 16384 >> 15;
					ry = matrix[1] * originX + matrix[4] * originY + matrix[7] * originZ + 16384 >> 15;
					rResult = matrix[2] * originX + matrix[5] * originY + matrix[8] * originZ + 16384 >> 15;
					rx += k;
					ry += j;
					rResult += vertIdx;
					originX = rx;
					originY = ry;
					originZ = rResult;
					originValid = false;
				}
				@Pc(410) int[] rotMatrix = new int[9];
				ry = MathUtils.cos[transformX] >> 1;
				rResult = MathUtils.sin[transformX] >> 1;
				sinAngle = MathUtils.cos[transformY] >> 1;
				cosAngle = MathUtils.sin[transformY] >> 1;
				rotTemp = MathUtils.cos[transformZ] >> 1;
				sinZ = MathUtils.sin[transformZ] >> 1;
				cosTimeSinZ = rResult * rotTemp + 16384 >> 15;
				sinTimesZ = rResult * sinZ + 16384 >> 15;
				rotMatrix[0] = sinAngle * rotTemp + cosAngle * sinTimesZ + 16384 >> 15;
				rotMatrix[1] = -sinAngle * sinZ + cosAngle * cosTimeSinZ + 16384 >> 15;
				rotMatrix[2] = cosAngle * ry + 16384 >> 15;
				rotMatrix[3] = ry * sinZ + 16384 >> 15;
				rotMatrix[4] = ry * rotTemp + 16384 >> 15;
				rotMatrix[5] = -rResult;
				rotMatrix[6] = -cosAngle * rotTemp + sinAngle * sinTimesZ + 16384 >> 15;
				rotMatrix[7] = cosAngle * sinZ + sinAngle * cosTimeSinZ + 16384 >> 15;
				rotMatrix[8] = sinAngle * ry + 16384 >> 15;
				@Pc(590) int relX = rotMatrix[0] * -originX + rotMatrix[1] * -originY + rotMatrix[2] * -originZ + 16384 >> 15;
				relY = rotMatrix[3] * -originX + rotMatrix[4] * -originY + rotMatrix[5] * -originZ + 16384 >> 15;
				relZ = rotMatrix[6] * -originX + rotMatrix[7] * -originY + rotMatrix[8] * -originZ + 16384 >> 15;
				pivotX = relX + originX;
				@Pc(648) int pivotY = relY + originY;
				pivotZ = relZ + originZ;
				@Pc(655) int[] composedMatrix = new int[9];
				for (mi = 0; mi < 3; mi++) {
					for (mj = 0; mj < 3; mj++) {
						sum = 0;
						for (@Pc(669) int mk = 0; mk < 3; mk++) {
							sum += rotMatrix[mi * 3 + mk] * matrix[mj * 3 + mk];
						}
						composedMatrix[mi * 3 + mj] = sum + 16384 >> 15;
					}
				}
				mi = rotMatrix[0] * k + rotMatrix[1] * j + rotMatrix[2] * vertIdx + 16384 >> 15;
				mj = rotMatrix[3] * k + rotMatrix[4] * j + rotMatrix[5] * vertIdx + 16384 >> 15;
				sum = rotMatrix[6] * k + rotMatrix[7] * j + rotMatrix[8] * vertIdx + 16384 >> 15;
				mi += pivotX;
				mj += pivotY;
				sum += pivotZ;
				finalMatrix = new int[9];
				for (fi = 0; fi < 3; fi++) {
					for (fj = 0; fj < 3; fj++) {
						fk = 0;
						for (li = 0; li < 3; li++) {
							fk += matrix[fi * 3 + li] * composedMatrix[fj + li * 3];
						}
						finalMatrix[fi * 3 + fj] = fk + 16384 >> 15;
					}
				}
				fi = matrix[0] * mi + matrix[1] * mj + matrix[2] * sum + 16384 >> 15;
				fj = matrix[3] * mi + matrix[4] * mj + matrix[5] * sum + 16384 >> 15;
				fk = matrix[6] * mi + matrix[7] * mj + matrix[8] * sum + 16384 >> 15;
				fi += count;
				fj += i;
				fk += rz;
				for (li = 0; li < labelCount; li++) {
					boneId = labels[li];
					if (boneId < this.boneVertices.length) {
						@Pc(939) int[] boneVertices = this.boneVertices[boneId];
						for (@Pc(941) int bi = 0; bi < boneVertices.length; bi++) {
							@Pc(949) int vi = boneVertices[bi];
							if (this.vertexSources == null || (mask & this.vertexSources[vi]) != 0) {
								@Pc(991) int newX = finalMatrix[0] * this.vertexX[vi] + finalMatrix[1] * this.vertexY[vi] + finalMatrix[2] * this.vertexZ[vi] + 16384 >> 15;
								@Pc(1022) int newY = finalMatrix[3] * this.vertexX[vi] + finalMatrix[4] * this.vertexY[vi] + finalMatrix[5] * this.vertexZ[vi] + 16384 >> 15;
								@Pc(1053) int newZ = finalMatrix[6] * this.vertexX[vi] + finalMatrix[7] * this.vertexY[vi] + finalMatrix[8] * this.vertexZ[vi] + 16384 >> 15;
								@Pc(1057) int finalX = newX + fi;
								@Pc(1061) int finalY = newY + fj;
								@Pc(1065) int finalZ = newZ + fk;
								this.vertexX[vi] = finalX;
								this.vertexY[vi] = finalY;
								this.vertexZ[vi] = finalZ;
							}
						}
					}
				}
			}
		} else if (transformType == 3) {
			if (matrix == null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneVertices.length) {
						verts = this.boneVertices[i];
						for (k = 0; k < verts.length; k++) {
							j = verts[k];
							if (this.vertexSources == null || (mask & this.vertexSources[j]) != 0) {
								this.vertexX[j] -= originX;
								this.vertexY[j] -= originY;
								this.vertexZ[j] -= originZ;
								this.vertexX[j] = this.vertexX[j] * transformX >> 7;
								this.vertexY[j] = this.vertexY[j] * transformY >> 7;
								this.vertexZ[j] = this.vertexZ[j] * transformZ >> 7;
								this.vertexX[j] += originX;
								this.vertexY[j] += originY;
								this.vertexZ[j] += originZ;
							}
						}
					}
				}
			} else {
				count = matrix[9] << 4;
				i = matrix[10] << 4;
				rz = matrix[11] << 4;
				k = matrix[12] << 4;
				j = matrix[13] << 4;
				vertIdx = matrix[14] << 4;
				if (originValid) {
					rx = matrix[0] * originX + matrix[3] * originY + matrix[6] * originZ + 16384 >> 15;
					ry = matrix[1] * originX + matrix[4] * originY + matrix[7] * originZ + 16384 >> 15;
					rResult = matrix[2] * originX + matrix[5] * originY + matrix[8] * originZ + 16384 >> 15;
					rx += k;
					ry += j;
					rResult += vertIdx;
					originX = rx;
					originY = ry;
					originZ = rResult;
					originValid = false;
				}
				rx = transformX << 15 >> 7;
				ry = transformY << 15 >> 7;
				rResult = transformZ << 15 >> 7;
				sinAngle = rx * -originX + 16384 >> 15;
				cosAngle = ry * -originY + 16384 >> 15;
				rotTemp = rResult * -originZ + 16384 >> 15;
				sinZ = sinAngle + originX;
				cosTimeSinZ = cosAngle + originY;
				sinTimesZ = rotTemp + originZ;
				@Pc(1783) int[] scaleMatrix = new int[]{rx * matrix[0] + 16384 >> 15, rx * matrix[3] + 16384 >> 15, rx * matrix[6] + 16384 >> 15, ry * matrix[1] + 16384 >> 15, ry * matrix[4] + 16384 >> 15, ry * matrix[7] + 16384 >> 15, rResult * matrix[2] + 16384 >> 15, rResult * matrix[5] + 16384 >> 15, rResult * matrix[8] + 16384 >> 15};
				relY = rx * k + 16384 >> 15;
				relZ = ry * j + 16384 >> 15;
				pivotX = rResult * vertIdx + 16384 >> 15;
				@Pc(1919) int scalePivotX = relY + sinZ;
				@Pc(1923) int scalePivotY = relZ + cosTimeSinZ;
				@Pc(1927) int scalePivotZ = pivotX + sinTimesZ;
				@Pc(1930) int[] scaleComposed = new int[9];
				@Pc(1937) int si;
				for (pivotZ = 0; pivotZ < 3; pivotZ++) {
					for (si = 0; si < 3; si++) {
						mi = 0;
						for (mj = 0; mj < 3; mj++) {
							mi += matrix[pivotZ * 3 + mj] * scaleMatrix[si + mj * 3];
						}
						scaleComposed[pivotZ * 3 + si] = mi + 16384 >> 15;
					}
				}
				pivotZ = matrix[0] * scalePivotX + matrix[1] * scalePivotY + matrix[2] * scalePivotZ + 16384 >> 15;
				si = matrix[3] * scalePivotX + matrix[4] * scalePivotY + matrix[5] * scalePivotZ + 16384 >> 15;
				mi = matrix[6] * scalePivotX + matrix[7] * scalePivotY + matrix[8] * scalePivotZ + 16384 >> 15;
				pivotZ += count;
				si += i;
				mi += rz;
				for (mj = 0; mj < labelCount; mj++) {
					sum = labels[mj];
					if (sum < this.boneVertices.length) {
						finalMatrix = this.boneVertices[sum];
						for (fi = 0; fi < finalMatrix.length; fi++) {
							fj = finalMatrix[fi];
							if (this.vertexSources == null || (mask & this.vertexSources[fj]) != 0) {
								fk = scaleComposed[0] * this.vertexX[fj] + scaleComposed[1] * this.vertexY[fj] + scaleComposed[2] * this.vertexZ[fj] + 16384 >> 15;
								li = scaleComposed[3] * this.vertexX[fj] + scaleComposed[4] * this.vertexY[fj] + scaleComposed[5] * this.vertexZ[fj] + 16384 >> 15;
								boneId = scaleComposed[6] * this.vertexX[fj] + scaleComposed[7] * this.vertexY[fj] + scaleComposed[8] * this.vertexZ[fj] + 16384 >> 15;
								@Pc(2198) int resultX = fk + pivotZ;
								@Pc(2202) int resultY = li + si;
								@Pc(2206) int resultZ = boneId + mi;
								this.vertexX[fj] = resultX;
								this.vertexY[fj] = resultY;
								this.vertexZ[fj] = resultZ;
							}
						}
					}
				}
			}
		} else if (transformType == 5) {
			if (this.boneTriangles != null && this.triangleAlpha != null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneTriangles.length) {
						verts = this.boneTriangles[i];
						for (k = 0; k < verts.length; k++) {
							j = verts[k];
							if (this.triangleSources == null || (mask & this.triangleSources[j]) != 0) {
								vertIdx = (this.triangleAlpha[j] & 0xFF) + transformX * 8;
								if (vertIdx < 0) {
									vertIdx = 0;
								} else if (vertIdx > 255) {
									vertIdx = 255;
								}
								this.triangleAlpha[j] = (byte) vertIdx;
							}
						}
						if (verts.length > 0) {
							this.colorBuffer.valid = false;
						}
					}
				}
			}
		} else if (transformType == 7 && this.boneTriangles != null) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneTriangles.length) {
					verts = this.boneTriangles[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						if (this.triangleSources == null || (mask & this.triangleSources[j]) != 0) {
							vertIdx = this.triangleColors[j] & 0xFFFF;
							rx = vertIdx >> 10 & 0x3F;
							ry = vertIdx >> 7 & 0x7;
							rResult = vertIdx & 0x7F;
							@Pc(2518) int newHue = rx + transformX & 0x3F;
							ry += transformY / 4;
							if (ry < 0) {
								ry = 0;
							} else if (ry > 7) {
								ry = 7;
							}
							rResult += transformZ;
							if (rResult < 0) {
								rResult = 0;
							} else if (rResult > 127) {
								rResult = 127;
							}
							this.triangleColors[j] = (short) (newHue << 10 | ry << 7 | rResult);
						}
					}
					if (verts.length > 0) {
						this.colorBuffer.valid = false;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.minY;
	}

	@OriginalMember(owner = "client!td", name = "o", descriptor = "()V")
	public final void createBones() {
		@Pc(5) int[] counts;
		@Pc(7) int maxBone;
		@Pc(24) int count;
		@Pc(9) int i;
		@Pc(20) int boneId;
		if (this.vertexBones != null) {
			counts = new int[256];
			maxBone = 0;
			for (i = 0; i < this.vertexCount; i++) {
				boneId = this.vertexBones[i] & 0xFF;
				count = counts[boneId]++;
				if (boneId > maxBone) {
					maxBone = boneId;
				}
			}
			this.boneVertices = new int[maxBone + 1][];
			for (i = 0; i <= maxBone; i++) {
				this.boneVertices[i] = new int[counts[i]];
				counts[i] = 0;
			}
			i = 0;
			while (i < this.vertexCount) {
				boneId = this.vertexBones[i] & 0xFF;
				this.boneVertices[boneId][counts[boneId]++] = i++;
			}
			this.vertexBones = null;
		}
		if (this.triangleBones == null) {
			return;
		}
		counts = new int[256];
		maxBone = 0;
		for (i = 0; i < this.triangleCount; i++) {
			boneId = this.triangleBones[i] & 0xFF;
			count = counts[boneId]++;
			if (boneId > maxBone) {
				maxBone = boneId;
			}
		}
		this.boneTriangles = new int[maxBone + 1][];
		for (i = 0; i <= maxBone; i++) {
			this.boneTriangles[i] = new int[counts[i]];
			counts[i] = 0;
		}
		i = 0;
		while (i < this.triangleCount) {
			boneId = this.triangleBones[i] & 0xFF;
			this.boneTriangles[boneId][counts[boneId]++] = i++;
		}
		this.triangleBones = null;
	}

	@OriginalMember(owner = "client!td", name = "e", descriptor = "(I)V")
	public final void setDiffuseIntensity(@OriginalArg(0) int value) {
		this.contrast = (short) value;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "p", descriptor = "()V")
	public final void rotate180All() {
		if (this.normalX == null) {
			this.rotate180();
			return;
		}
		@Pc(7) int i;
		for (i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = -this.vertexX[i];
			this.vertexZ[i] = -this.vertexZ[i];
		}
		for (i = 0; i < this.uniqueVertexCount; i++) {
			this.normalX[i] = (short) -this.normalX[i];
			this.normalZ[i] = (short) -this.normalZ[i];
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "b", descriptor = "(III)V")
	@Override
	public final void resize(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ) {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = this.vertexX[i] * scaleX >> 7;
			this.vertexY[i] = this.vertexY[i] * scaleY >> 7;
			this.vertexZ[i] = this.vertexZ[i] * scaleZ >> 7;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "c", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public final Model copyForEntity(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		return this.copyToTarget(shareAlpha, shareColors, shareNormals, entityCopyTarget, entityCopyScratch);
	}

	@OriginalMember(owner = "client!td", name = "e", descriptor = "()V")
	@Override
	public final void rotate180() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = -this.vertexX[i];
			this.vertexZ[i] = -this.vertexZ[i];
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "q", descriptor = "()V")
	private void buildIndexBuffer() {
		if (tempBuffer.data.length < this.uniqueVertexCount * 12) {
			tempBuffer = new Buffer((this.uniqueVertexCount + 100) * 12);
		} else {
			tempBuffer.offset = 0;
		}
		@Pc(25) int i;
		if (GlRenderer.bigEndian) {
			for (i = 0; i < this.triangleCount; i++) {
				tempBuffer.p4(this.triangleVertexA[i]);
				tempBuffer.p4(this.triangleVertexB[i]);
				tempBuffer.p4(this.triangleVertexC[i]);
			}
		} else {
			for (i = 0; i < this.triangleCount; i++) {
				tempBuffer.ip4(this.triangleVertexA[i]);
				tempBuffer.ip4(this.triangleVertexB[i]);
				tempBuffer.ip4(this.triangleVertexC[i]);
			}
		}
		if (!GlRenderer.arbVboSupported) {
			@Pc(115) ByteBuffer directBuffer = ByteBuffer.allocateDirect(tempBuffer.offset);
			directBuffer.put(tempBuffer.data, 0, tempBuffer.offset);
			directBuffer.flip();
			this.indexBuffer.valid = true;
			this.indexBuffer.buffer = directBuffer;
			this.indexBuffer.vbo = null;
			return;
		}
		@Pc(88) GlVertexBufferObject vbo = new GlVertexBufferObject();
		@Pc(95) ByteBuffer buffer = ByteBuffer.wrap(tempBuffer.data, 0, tempBuffer.offset);
		vbo.setArrayBuffer(buffer);
		this.indexBuffer.valid = true;
		this.indexBuffer.buffer = null;
		this.indexBuffer.vbo = vbo;
	}

	@OriginalMember(owner = "client!td", name = "f", descriptor = "(I)V")
	public final void setAmbientIntensity(@OriginalArg(0) int value) {
		this.ambient = (short) value;
		this.colorBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "d", descriptor = "()Z")
	@Override
	protected final boolean hasAnimationBones() {
		if (this.boneVertices == null) {
			return false;
		}
		for (@Pc(6) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] <<= 0x4;
			this.vertexY[i] <<= 0x4;
			this.vertexZ[i] <<= 0x4;
		}
		originX = 0;
		originY = 0;
		originZ = 0;
		return true;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(SS)V")
	public final void retexture(@OriginalArg(0) short oldTexture, @OriginalArg(1) short newTexture) {
		@Pc(1) int i;
		for (i = 0; i < this.triangleCount; i++) {
			if (this.triangleTextures[i] == oldTexture) {
				this.triangleTextures[i] = newTexture;
			}
		}
		i = 0;
		@Pc(22) int oldSpeed = 0;
		if (oldTexture != -1) {
			i = Rasteriser.textureProvider.getTextureBrightness(oldTexture & 0xFFFF);
			oldSpeed = Rasteriser.textureProvider.getTextureSpeed(oldTexture & 0xFFFF);
		}
		@Pc(41) int newBrightness = 0;
		@Pc(43) int newSpeed = 0;
		if (newTexture != -1) {
			newBrightness = Rasteriser.textureProvider.getTextureBrightness(newTexture & 0xFFFF);
			newSpeed = Rasteriser.textureProvider.getTextureSpeed(newTexture & 0xFFFF);
		}
		if (i != newBrightness || oldSpeed != newSpeed) {
			this.colorBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "s", descriptor = "()V")
	private void calculateBounds() {
		@Pc(1) int minX = 32767;
		@Pc(3) int minY = 32767;
		@Pc(5) int minZ = 32767;
		@Pc(7) int maxX = -32768;
		@Pc(9) int maxY = -32768;
		@Pc(11) int maxZ = -32768;
		@Pc(13) int maxCylinderRadiusSquared = 0;
		@Pc(15) int maxSphereRadiusSquared = 0;
		for (@Pc(17) int i = 0; i < this.vertexCount; i++) {
			@Pc(26) int x = this.vertexX[i];
			@Pc(31) int y = this.vertexY[i];
			@Pc(36) int z = this.vertexZ[i];
			if (x < minX) {
				minX = x;
			}
			if (x > maxX) {
				maxX = x;
			}
			if (y < minY) {
				minY = y;
			}
			if (y > maxY) {
				maxY = y;
			}
			if (z < minZ) {
				minZ = z;
			}
			if (z > maxZ) {
				maxZ = z;
			}
			@Pc(74) int cylinderRadiusSquared = x * x + z * z;
			if (cylinderRadiusSquared > maxCylinderRadiusSquared) {
				maxCylinderRadiusSquared = cylinderRadiusSquared;
			}
			int sphereRadiusSquared = x * x + z * z + y * y;
			if (sphereRadiusSquared > maxSphereRadiusSquared) {
				maxSphereRadiusSquared = sphereRadiusSquared;
			}
		}
		this.bounds.minX = (short) minX;
		this.bounds.maxX = (short) maxX;
		this.bounds.minY = (short) minY;
		this.bounds.maxY = (short) maxY;
		this.bounds.minZ = (short) minZ;
		this.bounds.maxZ = (short) maxZ;
		this.bounds.cylinderRadius = (short) (Math.sqrt(maxCylinderRadiusSquared) + 0.99D);
		Math.sqrt(maxSphereRadiusSquared);
		this.bounds.valid = true;
	}

	@OriginalMember(owner = "client!td", name = "b", descriptor = "(SS)V")
	public final void recolor(@OriginalArg(0) short oldColor, @OriginalArg(1) short newColor) {
		for (@Pc(1) int i = 0; i < this.triangleCount; i++) {
			if (this.triangleColors[i] == oldColor) {
				this.triangleColors[i] = newColor;
			}
		}
		this.colorBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IILclient!td;[[I[[IIII)V")
	public final void alignToTerrain(@OriginalArg(0) int type, @OriginalArg(1) int blendFactor, @OriginalArg(2) GlModel model, @OriginalArg(3) int[][] tileHeights, @OriginalArg(4) int[][] renderHeights, @OriginalArg(5) int sceneX, @OriginalArg(6) int baseHeight, @OriginalArg(7) int sceneZ) {
		if (!model.bounds.valid) {
			model.calculateBounds();
		}
		@Pc(11) int minX = sceneX + model.bounds.minX;
		@Pc(17) int maxX = sceneX + model.bounds.maxX;
		@Pc(23) int minZ = sceneZ + model.bounds.minZ;
		@Pc(29) int maxZ = sceneZ + model.bounds.maxZ;
		if ((type == 1 || type == 2 || type == 3 || type == 5) && (minX < 0 || maxX + 128 >> 7 >= tileHeights.length || minZ < 0 || maxZ + 128 >> 7 >= tileHeights[0].length)) {
			return;
		}
		if (type == 4 || type == 5) {
			if (renderHeights == null) {
				return;
			}
			if (minX < 0 || maxX + 128 >> 7 >= renderHeights.length || minZ < 0 || maxZ + 128 >> 7 >= renderHeights[0].length) {
				return;
			}
		} else {
			minX >>= 0x7;
			maxX = maxX + 127 >> 7;
			minZ >>= 0x7;
			maxZ = maxZ + 127 >> 7;
			if (tileHeights[minX][minZ] == baseHeight && tileHeights[maxX][minZ] == baseHeight && tileHeights[minX][maxZ] == baseHeight && tileHeights[maxX][maxZ] == baseHeight) {
				return;
			}
		}
		@Pc(150) int i;
		@Pc(161) int wx;
		@Pc(168) int wz;
		@Pc(172) int fracX;
		@Pc(176) int fracZ;
		@Pc(180) int tileX;
		@Pc(184) int tileZ;
		@Pc(206) int heightNW;
		@Pc(232) int heightSW;
		@Pc(244) int heightInterp;
		if (type == 1) {
			for (i = 0; i < this.vertexCount; i++) {
				wx = this.vertexX[i] + sceneX;
				wz = this.vertexZ[i] + sceneZ;
				fracX = wx & 0x7F;
				fracZ = wz & 0x7F;
				tileX = wx >> 7;
				tileZ = wz >> 7;
				heightNW = tileHeights[tileX][tileZ] * (128 - fracX) + tileHeights[tileX + 1][tileZ] * fracX >> 7;
				heightSW = tileHeights[tileX][tileZ + 1] * (128 - fracX) + tileHeights[tileX + 1][tileZ + 1] * fracX >> 7;
				heightInterp = heightNW * (128 - fracZ) + heightSW * fracZ >> 7;
				this.vertexY[i] = this.vertexY[i] + heightInterp - baseHeight;
			}
		} else {
			@Pc(362) int heightSW2;
			@Pc(374) int interpHeight;
			if (type == 2) {
				@Pc(266) short minBoundsY = model.bounds.minY;
				for (wx = 0; wx < this.vertexCount; wx++) {
					wz = (this.vertexY[wx] << 16) / minBoundsY;
					if (wz < blendFactor) {
						fracX = this.vertexX[wx] + sceneX;
						fracZ = this.vertexZ[wx] + sceneZ;
						tileX = fracX & 0x7F;
						tileZ = fracZ & 0x7F;
						heightNW = fracX >> 7;
						heightSW = fracZ >> 7;
						heightInterp = tileHeights[heightNW][heightSW] * (128 - tileX) + tileHeights[heightNW + 1][heightSW] * tileX >> 7;
						heightSW2 = tileHeights[heightNW][heightSW + 1] * (128 - tileX) + tileHeights[heightNW + 1][heightSW + 1] * tileX >> 7;
						interpHeight = heightInterp * (128 - tileZ) + heightSW2 * tileZ >> 7;
						this.vertexY[wx] += (interpHeight - baseHeight) * (blendFactor - wz) / blendFactor;
					}
				}
			} else if (type == 3) {
				i = (blendFactor & 0xFF) * 4;
				wx = (blendFactor >> 8 & 0xFF) * 4;
				this.alignToTerrain(tileHeights, sceneX, baseHeight, sceneZ, i, wx);
			} else if (type == 4) {
				i = model.bounds.maxY - model.bounds.minY;
				for (wx = 0; wx < this.vertexCount; wx++) {
					wz = this.vertexX[wx] + sceneX;
					fracX = this.vertexZ[wx] + sceneZ;
					fracZ = wz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = wz >> 7;
					heightNW = fracX >> 7;
					heightSW = renderHeights[tileZ][heightNW] * (128 - fracZ) + renderHeights[tileZ + 1][heightNW] * fracZ >> 7;
					heightInterp = renderHeights[tileZ][heightNW + 1] * (128 - fracZ) + renderHeights[tileZ + 1][heightNW + 1] * fracZ >> 7;
					heightSW2 = heightSW * (128 - tileX) + heightInterp * tileX >> 7;
					this.vertexY[wx] = this.vertexY[wx] + heightSW2 + i - baseHeight;
				}
			} else if (type == 5) {
				i = model.bounds.maxY - model.bounds.minY;
				for (wx = 0; wx < this.vertexCount; wx++) {
					wz = this.vertexX[wx] + sceneX;
					fracX = this.vertexZ[wx] + sceneZ;
					fracZ = wz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = wz >> 7;
					heightNW = fracX >> 7;
					heightSW = tileHeights[tileZ][heightNW] * (128 - fracZ) + tileHeights[tileZ + 1][heightNW] * fracZ >> 7;
					heightInterp = tileHeights[tileZ][heightNW + 1] * (128 - fracZ) + tileHeights[tileZ + 1][heightNW + 1] * fracZ >> 7;
					heightSW2 = heightSW * (128 - tileX) + heightInterp * tileX >> 7;
					heightSW = renderHeights[tileZ][heightNW] * (128 - fracZ) + renderHeights[tileZ + 1][heightNW] * fracZ >> 7;
					heightInterp = renderHeights[tileZ][heightNW + 1] * (128 - fracZ) + renderHeights[tileZ + 1][heightNW + 1] * fracZ >> 7;
					interpHeight = heightSW * (128 - tileX) + heightInterp * tileX >> 7;
					@Pc(716) int heightDiff = heightSW2 - interpHeight;
					this.vertexY[wx] = ((this.vertexY[wx] << 8) / i * heightDiff >> 8) - (baseHeight - heightSW2);
				}
			}
		}
		this.vertexBuffer.valid = false;
		this.bounds.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ZZZZZZZ)V")
	public final void uploadBuffers(@OriginalArg(0) boolean releaseVertices, @OriginalArg(1) boolean releaseColors, @OriginalArg(2) boolean releaseNormals, @OriginalArg(4) boolean buildIndex, @OriginalArg(5) boolean releaseBones, @OriginalArg(6) boolean pack) {
		if (this.updateFlags != 0) {
			throw new IllegalArgumentException();
		} else if (this.uniqueVertexCount != 0) {
			if (pack) {
				@Pc(26) boolean needsColorUpdate = !this.colorBuffer.valid && (releaseColors || releaseNormals && !Preferences.highDetailLighting);
				this.packInterleavedVertexData(false, !this.vertexBuffer.valid && releaseVertices, needsColorUpdate, this.normalsBuffer != null && !this.normalsBuffer.valid && releaseNormals, !this.texCoordBuffer.valid);
				if (!this.indexBuffer.valid && buildIndex && releaseColors) {
					this.buildIndexBuffer();
				}
			}
			if (releaseVertices) {
				if (this.vertexBuffer.valid) {
					if (!this.bounds.valid) {
						this.calculateBounds();
					}
					this.vertexX = null;
					this.vertexY = null;
					this.vertexZ = null;
					this.vertexLookup = null;
					this.vertexOffsets = null;
				} else {
					this.deferredReleaseFlags = (byte) (this.deferredReleaseFlags | 0x1);
				}
			}
			if (releaseColors) {
				if (this.colorBuffer.valid) {
					this.triangleColors = null;
					this.triangleAlpha = null;
				} else {
					this.deferredReleaseFlags = (byte) (this.deferredReleaseFlags | 0x2);
				}
			}
			if (releaseNormals && Preferences.highDetailLighting) {
				if (this.normalsBuffer.valid) {
					this.normalX = null;
					this.normalY = null;
					this.normalZ = null;
					this.normalMagnitude = null;
				} else {
					this.deferredReleaseFlags = (byte) (this.deferredReleaseFlags | 0x4);
				}
			}
			if (this.texCoordBuffer.valid) {
				this.vertexS = null;
				this.vertexT = null;
			} else {
				this.deferredReleaseFlags = (byte) (this.deferredReleaseFlags | 0x8);
			}
			if (buildIndex && releaseColors) {
				if (this.indexBuffer.valid && this.colorBuffer.valid) {
					this.triangleVertexA = null;
					this.triangleVertexB = null;
					this.triangleVertexC = null;
				} else {
					this.deferredReleaseFlags = (byte) (this.deferredReleaseFlags | 0x10);
				}
			}
			if (releaseBones) {
				this.vertexBones = null;
				this.triangleBones = null;
				this.boneVertices = null;
				this.boneTriangles = null;
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ZZZZZ)V")
	private void packInterleavedVertexData(@OriginalArg(0) boolean useVbo, @OriginalArg(1) boolean packVertices, @OriginalArg(2) boolean packColors, @OriginalArg(3) boolean packNormals, @OriginalArg(4) boolean packTexCoords) {
		@Pc(1) int stride = 0;
		if (packVertices) {
			this.vertexBuffer.pointer = 0;
			stride += 12;
		}
		if (packColors) {
			this.colorBuffer.pointer = stride;
			stride += 4;
		}
		if (packNormals) {
			this.normalsBuffer.pointer = stride;
			stride += 12;
		}
		if (packTexCoords) {
			this.texCoordBuffer.pointer = stride;
			stride += 8;
		}
		if (stride == 0) {
			return;
		}
		if (tempBuffer.data.length < this.uniqueVertexCount * stride) {
			tempBuffer = new Buffer((this.uniqueVertexCount + 100) * stride);
		} else {
			tempBuffer.offset = 0;
		}
		@Pc(60) int i;
		@Pc(71) int bitsX;
		@Pc(78) int bitsY;
		@Pc(85) int bitsZ;
		@Pc(90) int startOff;
		@Pc(97) int endOff;
		@Pc(99) int oi;
		if (packVertices) {
			@Pc(109) int uniqueIdx;
			if (GlRenderer.bigEndian) {
				for (i = 0; i < this.vertexCount; i++) {
					bitsX = Float.floatToRawIntBits((float) this.vertexX[i]);
					bitsY = Float.floatToRawIntBits((float) this.vertexY[i]);
					bitsZ = Float.floatToRawIntBits((float) this.vertexZ[i]);
					startOff = this.vertexOffsets[i];
					endOff = this.vertexOffsets[i + 1];
					for (oi = startOff; oi < endOff; oi++) {
						uniqueIdx = this.vertexLookup[oi] - 1;
						if (uniqueIdx == -1) {
							break;
						}
						tempBuffer.offset = uniqueIdx * stride;
						tempBuffer.p4(bitsX);
						tempBuffer.p4(bitsY);
						tempBuffer.p4(bitsZ);
					}
				}
			} else {
				for (i = 0; i < this.vertexCount; i++) {
					bitsX = Float.floatToRawIntBits((float) this.vertexX[i]);
					bitsY = Float.floatToRawIntBits((float) this.vertexY[i]);
					bitsZ = Float.floatToRawIntBits((float) this.vertexZ[i]);
					startOff = this.vertexOffsets[i];
					endOff = this.vertexOffsets[i + 1];
					for (oi = startOff; oi < endOff; oi++) {
						uniqueIdx = this.vertexLookup[oi] - 1;
						if (uniqueIdx == -1) {
							break;
						}
						tempBuffer.offset = uniqueIdx * stride;
						tempBuffer.ip4(bitsX);
						tempBuffer.ip4(bitsY);
						tempBuffer.ip4(bitsZ);
					}
				}
			}
		}
		if (packColors) {
			if (Preferences.highDetailLighting) {
				for (i = 0; i < this.triangleCount; i++) {
					bitsX = packColorToRGBA(this.triangleColors[i], this.triangleTextures[i], this.ambient, this.triangleAlpha[i]);
					tempBuffer.offset = this.colorBuffer.pointer + this.triangleVertexA[i] * stride;
					tempBuffer.p4(bitsX);
					tempBuffer.offset = this.colorBuffer.pointer + this.triangleVertexB[i] * stride;
					tempBuffer.p4(bitsX);
					tempBuffer.offset = this.colorBuffer.pointer + this.triangleVertexC[i] * stride;
					tempBuffer.p4(bitsX);
				}
			} else {
				i = (int) FogManager.light0Position[0];
				bitsX = (int) FogManager.light0Position[1];
				bitsY = (int) FogManager.light0Position[2];
				bitsZ = (int) Math.sqrt(i * i + bitsX * bitsX + bitsY * bitsY);
				startOff = (int) ((float) this.ambient * 1.3F);
				endOff = this.contrast * bitsZ >> 8;
				for (oi = 0; oi < this.triangleCount; oi++) {
					@Pc(270) short vertA = this.triangleVertexA[oi];
					@Pc(275) short magA = this.normalMagnitude[vertA];
					@Pc(281) int lightnessA;
					if (magA < 0) {
						lightnessA = -magA - 1;
					} else {
						if (magA == 0) {
							lightnessA = startOff + (i * this.normalX[vertA] + bitsX * this.normalY[vertA] + bitsY * this.normalZ[vertA]) / (endOff + endOff / 2);
						} else {
							lightnessA = startOff + (i * this.normalX[vertA] + bitsX * this.normalY[vertA] + bitsY * this.normalZ[vertA]) / (endOff * magA);
						}
						if (lightnessA < 0) {
							lightnessA = 0;
						} else if (lightnessA > 16384) {
							lightnessA = 16384;
						}
						this.normalMagnitude[vertA] = (short) (-lightnessA - 1);
					}
					@Pc(364) short vertB = this.triangleVertexB[oi];
					@Pc(369) short magB = this.normalMagnitude[vertB];
					@Pc(375) int lightnessB;
					if (magB < 0) {
						lightnessB = -magB - 1;
					} else {
						if (magB == 0) {
							lightnessB = startOff + (i * this.normalX[vertB] + bitsX * this.normalY[vertB] + bitsY * this.normalZ[vertB]) / (endOff + endOff / 2);
						} else {
							lightnessB = startOff + (i * this.normalX[vertB] + bitsX * this.normalY[vertB] + bitsY * this.normalZ[vertB]) / (endOff * magB);
						}
						if (lightnessB < 0) {
							lightnessB = 0;
						} else if (lightnessB > 16384) {
							lightnessB = 16384;
						}
						this.normalMagnitude[vertB] = (short) (-lightnessB - 1);
					}
					@Pc(458) short vertC = this.triangleVertexC[oi];
					@Pc(463) short magC = this.normalMagnitude[vertC];
					@Pc(469) int lightnessC;
					if (magC < 0) {
						lightnessC = -magC - 1;
					} else {
						if (magC == 0) {
							lightnessC = startOff + (i * this.normalX[vertC] + bitsX * this.normalY[vertC] + bitsY * this.normalZ[vertC]) / (endOff + endOff / 2);
						} else {
							lightnessC = startOff + (i * this.normalX[vertC] + bitsX * this.normalY[vertC] + bitsY * this.normalZ[vertC]) / (endOff * magC);
						}
						if (lightnessC < 0) {
							lightnessC = 0;
						} else if (lightnessC > 16384) {
							lightnessC = 16384;
						}
						this.normalMagnitude[vertC] = (short) (-lightnessC - 1);
					}
					@Pc(562) int colorA = packColorToRGBA(this.triangleColors[oi], this.triangleTextures[oi], lightnessA, this.triangleAlpha[oi]);
					@Pc(577) int colorB = packColorToRGBA(this.triangleColors[oi], this.triangleTextures[oi], lightnessB, this.triangleAlpha[oi]);
					@Pc(592) int colorC = packColorToRGBA(this.triangleColors[oi], this.triangleTextures[oi], lightnessC, this.triangleAlpha[oi]);
					tempBuffer.offset = this.colorBuffer.pointer + vertA * stride;
					tempBuffer.p4(colorA);
					tempBuffer.offset = this.colorBuffer.pointer + vertB * stride;
					tempBuffer.p4(colorB);
					tempBuffer.offset = this.colorBuffer.pointer + vertC * stride;
					tempBuffer.p4(colorC);
				}
				this.normalX = null;
				this.normalY = null;
				this.normalZ = null;
			}
		}
		if (packNormals) {
			@Pc(723) float normalScale = 3.0F / (float) this.contrast;
			@Pc(734) float normalScaleZero = 3.0F / (float) (this.contrast + this.contrast / 2);
			tempBuffer.offset = this.normalsBuffer.pointer;
			@Pc(752) short magnitude;
			@Pc(790) float adjustedScale;
			if (GlRenderer.bigEndian) {
				for (bitsY = 0; bitsY < this.uniqueVertexCount; bitsY++) {
					magnitude = this.normalMagnitude[bitsY];
					if (magnitude == 0) {
						tempBuffer.pFloat((float) this.normalX[bitsY] * normalScaleZero);
						tempBuffer.pFloat((float) this.normalY[bitsY] * normalScaleZero);
						tempBuffer.pFloat((float) this.normalZ[bitsY] * normalScaleZero);
					} else {
						adjustedScale = normalScale / (float) magnitude;
						tempBuffer.pFloat((float) this.normalX[bitsY] * adjustedScale);
						tempBuffer.pFloat((float) this.normalY[bitsY] * adjustedScale);
						tempBuffer.pFloat((float) this.normalZ[bitsY] * adjustedScale);
					}
					tempBuffer.offset += stride - 12;
				}
			} else {
				for (bitsY = 0; bitsY < this.uniqueVertexCount; bitsY++) {
					magnitude = this.normalMagnitude[bitsY];
					if (magnitude == 0) {
						tempBuffer.gFloat((float) this.normalX[bitsY] * normalScaleZero);
						tempBuffer.gFloat((float) this.normalY[bitsY] * normalScaleZero);
						tempBuffer.gFloat((float) this.normalZ[bitsY] * normalScaleZero);
					} else {
						adjustedScale = normalScale / (float) magnitude;
						tempBuffer.gFloat((float) this.normalX[bitsY] * adjustedScale);
						tempBuffer.gFloat((float) this.normalY[bitsY] * adjustedScale);
						tempBuffer.gFloat((float) this.normalZ[bitsY] * adjustedScale);
					}
					tempBuffer.offset += stride - 12;
				}
			}
		}
		if (packTexCoords) {
			tempBuffer.offset = this.texCoordBuffer.pointer;
			if (GlRenderer.bigEndian) {
				for (i = 0; i < this.uniqueVertexCount; i++) {
					tempBuffer.pFloat(this.vertexS[i]);
					tempBuffer.pFloat(this.vertexT[i]);
					tempBuffer.offset += stride - 8;
				}
			} else {
				for (i = 0; i < this.uniqueVertexCount; i++) {
					tempBuffer.gFloat(this.vertexS[i]);
					tempBuffer.gFloat(this.vertexT[i]);
					tempBuffer.offset += stride - 8;
				}
			}
		}
		tempBuffer.offset = stride * this.uniqueVertexCount;
		@Pc(1007) ByteBuffer byteBuffer;
		if (useVbo) {
			if (arbVboSupported) {
				byteBuffer = ByteBuffer.wrap(tempBuffer.data, 0, tempBuffer.offset);
				if (this.interleavedVbo == null) {
					this.interleavedVbo = new GlVertexBufferObject(true);
					this.interleavedVbo.setArrayBuffer(byteBuffer);
				} else {
					this.interleavedVbo.updateArrayBuffer(byteBuffer);
				}
				if (packVertices) {
					this.vertexBuffer.valid = true;
					this.vertexBuffer.buffer = null;
					this.vertexBuffer.vbo = this.interleavedVbo;
					this.vertexBuffer.stride = stride;
				}
				if (packColors) {
					this.colorBuffer.valid = true;
					this.colorBuffer.buffer = null;
					this.colorBuffer.vbo = this.interleavedVbo;
					this.colorBuffer.stride = stride;
				}
				if (packNormals) {
					this.normalsBuffer.valid = true;
					this.normalsBuffer.buffer = null;
					this.normalsBuffer.vbo = this.interleavedVbo;
					this.normalsBuffer.stride = stride;
				}
				if (packTexCoords) {
					this.texCoordBuffer.valid = true;
					this.texCoordBuffer.buffer = null;
					this.texCoordBuffer.vbo = this.interleavedVbo;
					this.texCoordBuffer.stride = stride;
				}
			} else {
				if (aByteBuffer9 == null || aByteBuffer9.capacity() < tempBuffer.offset) {
					aByteBuffer9 = ByteBuffer.allocateDirect(tempBuffer.offset + stride * 100);
				} else {
					aByteBuffer9.clear();
				}
				aByteBuffer9.put(tempBuffer.data, 0, tempBuffer.offset);
				aByteBuffer9.flip();
				if (packVertices) {
					this.vertexBuffer.valid = true;
					this.vertexBuffer.buffer = aByteBuffer9;
					this.vertexBuffer.vbo = null;
					this.vertexBuffer.stride = stride;
				}
				if (packColors) {
					this.colorBuffer.valid = true;
					this.colorBuffer.buffer = aByteBuffer9;
					this.vertexBuffer.vbo = null;
					this.colorBuffer.stride = stride;
				}
				if (packNormals) {
					this.normalsBuffer.valid = true;
					this.normalsBuffer.buffer = aByteBuffer9;
					this.normalsBuffer.vbo = null;
					this.normalsBuffer.stride = stride;
				}
				if (packTexCoords) {
					this.texCoordBuffer.valid = true;
					this.texCoordBuffer.buffer = aByteBuffer9;
					this.texCoordBuffer.vbo = null;
					this.texCoordBuffer.stride = stride;
				}
			}
		} else if (GlRenderer.arbVboSupported) {
			@Pc(1211) GlVertexBufferObject vbo = new GlVertexBufferObject();
			@Pc(1218) ByteBuffer wrappedBuffer = ByteBuffer.wrap(tempBuffer.data, 0, tempBuffer.offset);
			vbo.setArrayBuffer(wrappedBuffer);
			if (packVertices) {
				this.vertexBuffer.valid = true;
				this.vertexBuffer.buffer = null;
				this.vertexBuffer.vbo = vbo;
				this.vertexBuffer.stride = stride;
			}
			if (packColors) {
				this.colorBuffer.valid = true;
				this.colorBuffer.buffer = null;
				this.colorBuffer.vbo = vbo;
				this.colorBuffer.stride = stride;
			}
			if (packNormals) {
				this.normalsBuffer.valid = true;
				this.normalsBuffer.buffer = null;
				this.normalsBuffer.vbo = vbo;
				this.normalsBuffer.stride = stride;
			}
			if (packTexCoords) {
				this.texCoordBuffer.valid = true;
				this.texCoordBuffer.buffer = null;
				this.texCoordBuffer.vbo = vbo;
				this.texCoordBuffer.stride = stride;
			}
		} else {
			byteBuffer = ByteBuffer.allocateDirect(tempBuffer.offset);
			byteBuffer.put(tempBuffer.data, 0, tempBuffer.offset);
			byteBuffer.flip();
			if (packVertices) {
				this.vertexBuffer.valid = true;
				this.vertexBuffer.buffer = byteBuffer;
				this.vertexBuffer.vbo = null;
				this.vertexBuffer.stride = stride;
			}
			if (packColors) {
				this.colorBuffer.valid = true;
				this.colorBuffer.buffer = byteBuffer;
				this.vertexBuffer.vbo = null;
				this.colorBuffer.stride = stride;
			}
			if (packNormals) {
				this.normalsBuffer.valid = true;
				this.normalsBuffer.buffer = byteBuffer;
				this.normalsBuffer.vbo = null;
				this.normalsBuffer.stride = stride;
			}
			if (packTexCoords) {
				this.texCoordBuffer.valid = true;
				this.texCoordBuffer.buffer = byteBuffer;
				this.texCoordBuffer.vbo = null;
				this.texCoordBuffer.stride = stride;
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ZZZLclient!td;Lclient!td;)Lclient!ak;")
	private Model copyToTarget(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals, @OriginalArg(3) GlModel target, @OriginalArg(4) GlModel scratch) {
		target.vertexCount = this.vertexCount;
		target.uniqueVertexCount = this.uniqueVertexCount;
		target.triangleCount = this.triangleCount;
		target.ambient = this.ambient;
		target.contrast = this.contrast;
		target.updateFlags = (byte) ((shareAlpha && shareColors ? 0 : 2) | 0x1 | (shareNormals ? 0 : 4));
		if (target.vertexX == null || target.vertexX.length < this.vertexCount) {
			target.vertexX = new int[this.vertexCount + 100];
			target.vertexY = new int[this.vertexCount + 100];
			target.vertexZ = new int[this.vertexCount + 100];
		}
		@Pc(69) int i;
		for (i = 0; i < this.vertexCount; i++) {
			target.vertexX[i] = this.vertexX[i];
			target.vertexY[i] = this.vertexY[i];
			target.vertexZ[i] = this.vertexZ[i];
		}
		if (target.vertexBuffer == null) {
			target.vertexBuffer = new GlBuffer();
		}
		target.vertexBuffer.valid = false;
		if (target.bounds == null) {
			target.bounds = new GlBoundingBox();
		}
		target.bounds.valid = false;
		if (shareAlpha) {
			target.triangleAlpha = this.triangleAlpha;
		} else {
			if (scratch.triangleAlpha == null || scratch.triangleAlpha.length < this.triangleCount) {
				scratch.triangleAlpha = new byte[this.triangleCount + 100];
			}
			target.triangleAlpha = scratch.triangleAlpha;
			for (i = 0; i < this.triangleCount; i++) {
				target.triangleAlpha[i] = this.triangleAlpha[i];
			}
		}
		if (shareColors) {
			target.triangleColors = this.triangleColors;
		} else {
			if (scratch.triangleColors == null || scratch.triangleColors.length < this.triangleCount) {
				scratch.triangleColors = new short[this.triangleCount + 100];
			}
			target.triangleColors = scratch.triangleColors;
			for (i = 0; i < this.triangleCount; i++) {
				target.triangleColors[i] = this.triangleColors[i];
			}
		}
		if (shareAlpha && shareColors) {
			target.colorBuffer = this.colorBuffer;
		} else {
			if (scratch.colorBuffer == null) {
				scratch.colorBuffer = new GlBuffer();
			}
			target.colorBuffer = scratch.colorBuffer;
			target.colorBuffer.valid = false;
		}
		if (shareNormals || this.normalX == null) {
			target.normalX = this.normalX;
			target.normalY = this.normalY;
			target.normalZ = this.normalZ;
			target.normalMagnitude = this.normalMagnitude;
			target.normalsBuffer = this.normalsBuffer;
		} else {
			if (scratch.normalX == null || scratch.normalX.length < this.uniqueVertexCount) {
				scratch.normalX = new short[this.uniqueVertexCount + 100];
				scratch.normalY = new short[this.uniqueVertexCount + 100];
				scratch.normalZ = new short[this.uniqueVertexCount + 100];
				scratch.normalMagnitude = new short[this.uniqueVertexCount + 100];
			}
			target.normalX = scratch.normalX;
			target.normalY = scratch.normalY;
			target.normalZ = scratch.normalZ;
			target.normalMagnitude = scratch.normalMagnitude;
			for (i = 0; i < this.uniqueVertexCount; i++) {
				target.normalX[i] = this.normalX[i];
				target.normalY[i] = this.normalY[i];
				target.normalZ[i] = this.normalZ[i];
				target.normalMagnitude[i] = this.normalMagnitude[i];
			}
			if (Preferences.highDetailLighting) {
				if (scratch.normalsBuffer == null) {
					scratch.normalsBuffer = new GlBuffer();
				}
				target.normalsBuffer = scratch.normalsBuffer;
				target.normalsBuffer.valid = false;
			} else {
				target.normalsBuffer = null;
			}
		}
		target.vertexS = this.vertexS;
		target.vertexT = this.vertexT;
		target.vertexBones = this.vertexBones;
		target.boneVertices = this.boneVertices;
		target.triangleVertexA = this.triangleVertexA;
		target.triangleVertexB = this.triangleVertexB;
		target.triangleVertexC = this.triangleVertexC;
		target.triangleTextures = this.triangleTextures;
		target.triangleBones = this.triangleBones;
		target.boneTriangles = this.boneTriangles;
		target.texCoordBuffer = this.texCoordBuffer;
		target.indexBuffer = this.indexBuffer;
		target.textureGroupOffsets = this.textureGroupOffsets;
		target.vertexLookup = this.vertexLookup;
		target.vertexOffsets = this.vertexOffsets;
		target.pickable = this.pickable;
		target.vertexSources = this.vertexSources;
		target.triangleSources = this.triangleSources;
		return target;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(III)Lclient!th;")
	@Override
	public final Entity createModel() {
		this.mergeable = false;
		if (this.recoloring != null) {
			this.normalX = this.recoloring.recolorSrc;
			this.normalY = this.recoloring.recolorDst;
			this.normalZ = this.recoloring.retextureSrc;
			this.normalMagnitude = this.recoloring.retextureDst;
			this.recoloring = null;
		}
		return this;
	}

	@OriginalMember(owner = "client!td", name = "t", descriptor = "()I")
	public final int getDiffuseIntensity() {
		return this.contrast;
	}

	@OriginalMember(owner = "client!td", name = "u", descriptor = "()V")
	public final void rotateCounterClockwiseAll() {
		if (this.normalX == null) {
			this.rotateCounterClockwise();
			return;
		}
		@Pc(7) int i;
		for (i = 0; i < this.vertexCount; i++) {
			@Pc(16) int temp = this.vertexX[i];
			this.vertexX[i] = this.vertexZ[i];
			this.vertexZ[i] = -temp;
		}
		for (i = 0; i < this.uniqueVertexCount; i++) {
			@Pc(43) short tempN = this.normalX[i];
			this.normalX[i] = this.normalZ[i];
			this.normalZ[i] = (short) -tempN;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(ZZZZZZZZZZZ)Lclient!td;")
	public final GlModel deepCopy(@OriginalArg(0) boolean shareXZ, @OriginalArg(1) boolean shareY, @OriginalArg(2) boolean shareColors, @OriginalArg(4) boolean shareAlpha, @OriginalArg(5) boolean shareNormals, @OriginalArg(6) boolean shareNormalsValid, @OriginalArg(7) boolean shareColorBuffer, @OriginalArg(9) boolean shareTriangles, @OriginalArg(10) boolean shareTextures) {
		@Pc(3) GlModel model = new GlModel();
		model.vertexCount = this.vertexCount;
		model.uniqueVertexCount = this.uniqueVertexCount;
		model.triangleCount = this.triangleCount;
		if (shareXZ) {
			model.vertexX = this.vertexX;
			model.vertexZ = this.vertexZ;
		} else {
			model.vertexX = ArrayUtils.copyOfNullable(this.vertexX);
			model.vertexZ = ArrayUtils.copyOfNullable(this.vertexZ);
		}
		if (shareY) {
			model.vertexY = this.vertexY;
		} else {
			model.vertexY = ArrayUtils.copyOfNullable(this.vertexY);
		}
		if (shareXZ && shareY) {
			model.vertexBuffer = this.vertexBuffer;
			model.bounds = this.bounds;
		} else {
			model.vertexBuffer = new GlBuffer();
			model.bounds = new GlBoundingBox();
		}
		if (shareColors) {
			model.triangleColors = this.triangleColors;
		} else {
			model.triangleColors = ArrayUtils.copyOfNullable(this.triangleColors);
		}
		model.triangleAlpha = this.triangleAlpha;
		if (shareColors && shareAlpha && (shareColorBuffer && shareNormals || Preferences.highDetailLighting)) {
			model.colorBuffer = this.colorBuffer;
		} else {
			model.colorBuffer = new GlBuffer();
		}
		if (shareNormals) {
			model.normalX = this.normalX;
			model.normalY = this.normalY;
			model.normalZ = this.normalZ;
			model.normalMagnitude = this.normalMagnitude;
		} else {
			model.normalX = ArrayUtils.copyOfNullable(this.normalX);
			model.normalY = ArrayUtils.copyOfNullable(this.normalY);
			model.normalZ = ArrayUtils.copyOfNullable(this.normalZ);
			model.normalMagnitude = ArrayUtils.copyOfNullable(this.normalMagnitude);
		}
		if (!Preferences.highDetailLighting) {
			model.normalsBuffer = null;
		} else if (shareNormals && shareNormalsValid && shareColorBuffer) {
			model.normalsBuffer = this.normalsBuffer;
		} else {
			model.normalsBuffer = new GlBuffer();
		}
		model.vertexS = this.vertexS;
		model.vertexT = this.vertexT;
		model.texCoordBuffer = this.texCoordBuffer;
		if (shareTriangles) {
			model.triangleVertexA = this.triangleVertexA;
			model.triangleVertexB = this.triangleVertexB;
			model.triangleVertexC = this.triangleVertexC;
			model.indexBuffer = this.indexBuffer;
		} else {
			model.triangleVertexA = ArrayUtils.copyOfNullable(this.triangleVertexA);
			model.triangleVertexB = ArrayUtils.copyOfNullable(this.triangleVertexB);
			model.triangleVertexC = ArrayUtils.copyOfNullable(this.triangleVertexC);
			model.indexBuffer = new GlBuffer();
		}
		if (shareTextures) {
			model.triangleTextures = this.triangleTextures;
		} else {
			model.triangleTextures = ArrayUtils.copyOfNullable(this.triangleTextures);
		}
		model.vertexBones = this.vertexBones;
		model.boneVertices = this.boneVertices;
		model.triangleBones = this.triangleBones;
		model.boneTriangles = this.boneTriangles;
		model.textureGroupOffsets = this.textureGroupOffsets;
		model.vertexLookup = this.vertexLookup;
		model.vertexOffsets = this.vertexOffsets;
		model.ambient = this.ambient;
		model.contrast = this.contrast;
		model.vertexSources = this.vertexSources;
		model.triangleSources = this.triangleSources;
		return model;
	}

	@OriginalMember(owner = "client!td", name = "i", descriptor = "()V")
	@Override
	public final void rotateCounterClockwise() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			@Pc(10) int temp = this.vertexX[i];
			this.vertexX[i] = this.vertexZ[i];
			this.vertexZ[i] = -temp;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIIIIIII)Z")
	private boolean pointWithinTriangle(@OriginalArg(0) int pointX, @OriginalArg(1) int pointY, @OriginalArg(2) int y0, @OriginalArg(3) int y1, @OriginalArg(4) int y2, @OriginalArg(5) int x0, @OriginalArg(6) int x1, @OriginalArg(7) int x2) {
		if (pointY < y0 && pointY < y1 && pointY < y2) {
			return false;
		} else if (pointY > y0 && pointY > y1 && pointY > y2) {
			return false;
		} else if (pointX < x0 && pointX < x1 && pointX < x2) {
			return false;
		} else {
			return pointX <= x0 || pointX <= x1 || pointX <= x2;
		}
	}

	@OriginalMember(owner = "client!td", name = "j", descriptor = "()I")
	@Override
	public final int getLengthXZ() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.cylinderRadius;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(I[IIIIZ)V")
	@Override
	protected final void transformBone(@OriginalArg(0) int transformType, @OriginalArg(1) int[] labels, @OriginalArg(2) int transformX, @OriginalArg(3) int transformY, @OriginalArg(4) int transformZ, @OriginalArg(5) boolean delayed) {
		@Pc(2) int labelCount = labels.length;
		@Pc(18) int count;
		@Pc(26) int i;
		@Pc(45) int j;
		@Pc(53) int vertIdx;
		@Pc(8) int scaledX;
		@Pc(12) int scaledY;
		@Pc(16) int scaledZ;
		if (transformType == 0) {
			scaledX = transformX << 4;
			scaledY = transformY << 4;
			scaledZ = transformZ << 4;
			count = 0;
			originX = 0;
			originY = 0;
			originZ = 0;
			for (i = 0; i < labelCount; i++) {
				@Pc(33) int label = labels[i];
				if (label < this.boneVertices.length) {
					@Pc(43) int[] boneVerts = this.boneVertices[label];
					for (j = 0; j < boneVerts.length; j++) {
						vertIdx = boneVerts[j];
						originX += this.vertexX[vertIdx];
						originY += this.vertexY[vertIdx];
						originZ += this.vertexZ[vertIdx];
						count++;
					}
				}
			}
			if (count > 0) {
				originX = originX / count + scaledX;
				originY = originY / count + scaledY;
				originZ = originZ / count + scaledZ;
			} else {
				originX = scaledX;
				originY = scaledY;
				originZ = scaledZ;
			}
			return;
		}
		@Pc(141) int[] verts;
		@Pc(143) int k;
		if (transformType == 1) {
			scaledX = transformX << 4;
			scaledY = transformY << 4;
			scaledZ = transformZ << 4;
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					verts = this.boneVertices[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						this.vertexX[j] += scaledX;
						this.vertexY[j] += scaledY;
						this.vertexZ[j] += scaledZ;
					}
				}
			}
			return;
		}
		@Pc(246) int cosAngle;
		@Pc(264) int temp;
		@Pc(484) int lookupIdx;
		if (transformType == 2) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					verts = this.boneVertices[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						this.vertexX[j] -= originX;
						this.vertexY[j] -= originY;
						this.vertexZ[j] -= originZ;
						if (transformZ != 0) {
							vertIdx = MathUtils.sin[transformZ];
							cosAngle = MathUtils.cos[transformZ];
							temp = this.vertexY[j] * vertIdx + this.vertexX[j] * cosAngle + 32767 >> 16;
							this.vertexY[j] = this.vertexY[j] * cosAngle + 32767 - this.vertexX[j] * vertIdx >> 16;
							this.vertexX[j] = temp;
						}
						if (transformX != 0) {
							vertIdx = MathUtils.sin[transformX];
							cosAngle = MathUtils.cos[transformX];
							temp = this.vertexY[j] * cosAngle + 32767 - this.vertexZ[j] * vertIdx >> 16;
							this.vertexZ[j] = this.vertexY[j] * vertIdx + this.vertexZ[j] * cosAngle + 32767 >> 16;
							this.vertexY[j] = temp;
						}
						if (transformY != 0) {
							vertIdx = MathUtils.sin[transformY];
							cosAngle = MathUtils.cos[transformY];
							temp = this.vertexZ[j] * vertIdx + this.vertexX[j] * cosAngle + 32767 >> 16;
							this.vertexZ[j] = this.vertexZ[j] * cosAngle + 32767 - this.vertexX[j] * vertIdx >> 16;
							this.vertexX[j] = temp;
						}
						this.vertexX[j] += originX;
						this.vertexY[j] += originY;
						this.vertexZ[j] += originZ;
					}
				}
			}
			if (delayed && this.normalX != null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneVertices.length) {
						verts = this.boneVertices[i];
						for (k = 0; k < verts.length; k++) {
							j = verts[k];
							vertIdx = this.vertexOffsets[j];
							cosAngle = this.vertexOffsets[j + 1];
							for (temp = vertIdx; temp < cosAngle; temp++) {
								lookupIdx = this.vertexLookup[temp] - 1;
								if (lookupIdx == -1) {
									break;
								}
								@Pc(494) int sinAngle;
								@Pc(498) int cosAngle2;
								@Pc(516) int rotResult;
								if (transformZ != 0) {
									sinAngle = MathUtils.sin[transformZ];
									cosAngle2 = MathUtils.cos[transformZ];
									rotResult = this.normalY[lookupIdx] * sinAngle + this.normalX[lookupIdx] * cosAngle2 + 32767 >> 16;
									this.normalY[lookupIdx] = (short) (this.normalY[lookupIdx] * cosAngle2 + 32767 - this.normalX[lookupIdx] * sinAngle >> 16);
									this.normalX[lookupIdx] = (short) rotResult;
								}
								if (transformX != 0) {
									sinAngle = MathUtils.sin[transformX];
									cosAngle2 = MathUtils.cos[transformX];
									rotResult = this.normalY[lookupIdx] * cosAngle2 + 32767 - this.normalZ[lookupIdx] * sinAngle >> 16;
									this.normalZ[lookupIdx] = (short) (this.normalY[lookupIdx] * sinAngle + this.normalZ[lookupIdx] * cosAngle2 + 32767 >> 16);
									this.normalY[lookupIdx] = (short) rotResult;
								}
								if (transformY != 0) {
									sinAngle = MathUtils.sin[transformY];
									cosAngle2 = MathUtils.cos[transformY];
									rotResult = this.normalZ[lookupIdx] * sinAngle + this.normalX[lookupIdx] * cosAngle2 + 32767 >> 16;
									this.normalZ[lookupIdx] = (short) (this.normalZ[lookupIdx] * cosAngle2 + 32767 - this.normalX[lookupIdx] * sinAngle >> 16);
									this.normalX[lookupIdx] = (short) rotResult;
								}
							}
						}
					}
				}
				if (this.normalsBuffer != null) {
					this.normalsBuffer.valid = false;
				}
			}
		} else if (transformType == 3) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					verts = this.boneVertices[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						this.vertexX[j] -= originX;
						this.vertexY[j] -= originY;
						this.vertexZ[j] -= originZ;
						this.vertexX[j] = this.vertexX[j] * transformX >> 7;
						this.vertexY[j] = this.vertexY[j] * transformY >> 7;
						this.vertexZ[j] = this.vertexZ[j] * transformZ >> 7;
						this.vertexX[j] += originX;
						this.vertexY[j] += originY;
						this.vertexZ[j] += originZ;
					}
				}
			}
		} else if (transformType == 5) {
			if (this.boneTriangles != null && this.triangleAlpha != null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneTriangles.length) {
						verts = this.boneTriangles[i];
						for (k = 0; k < verts.length; k++) {
							j = verts[k];
							vertIdx = (this.triangleAlpha[j] & 0xFF) + transformX * 8;
							if (vertIdx < 0) {
								vertIdx = 0;
							} else if (vertIdx > 255) {
								vertIdx = 255;
							}
							this.triangleAlpha[j] = (byte) vertIdx;
						}
						if (verts.length > 0) {
							this.colorBuffer.valid = false;
						}
					}
				}
			}
		} else if (transformType == 7 && this.boneTriangles != null) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneTriangles.length) {
					verts = this.boneTriangles[i];
					for (k = 0; k < verts.length; k++) {
						j = verts[k];
						vertIdx = this.triangleColors[j] & 0xFFFF;
						cosAngle = vertIdx >> 10 & 0x3F;
						temp = vertIdx >> 7 & 0x7;
						lookupIdx = vertIdx & 0x7F;
						@Pc(932) int newHue = cosAngle + transformX & 0x3F;
						temp += transformY / 4;
						if (temp < 0) {
							temp = 0;
						} else if (temp > 7) {
							temp = 7;
						}
						lookupIdx += transformZ;
						if (lookupIdx < 0) {
							lookupIdx = 0;
						} else if (lookupIdx > 127) {
							lookupIdx = 127;
						}
						this.triangleColors[j] = (short) (newHue << 10 | temp << 7 | lookupIdx);
					}
					if (verts.length > 0) {
						this.colorBuffer.valid = false;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(IIII)V")
	@Override
	protected final void transformShadowBone(@OriginalArg(0) int transformType, @OriginalArg(1) int transformX, @OriginalArg(2) int transformY, @OriginalArg(3) int transformZ) {
		@Pc(3) int i;
		@Pc(11) int sinAngle;
		if (transformType == 0) {
			i = 0;
			originX = 0;
			originY = 0;
			originZ = 0;
			for (sinAngle = 0; sinAngle < this.vertexCount; sinAngle++) {
				originX += this.vertexX[sinAngle];
				originY += this.vertexY[sinAngle];
				originZ += this.vertexZ[sinAngle];
				i++;
			}
			if (i > 0) {
				originX = originX / i + transformX;
				originY = originY / i + transformY;
				originZ = originZ / i + transformZ;
			} else {
				originX = transformX;
				originY = transformY;
				originZ = transformZ;
			}
		} else if (transformType == 1) {
			for (i = 0; i < this.vertexCount; i++) {
				this.vertexX[i] += transformX;
				this.vertexY[i] += transformY;
				this.vertexZ[i] += transformZ;
			}
		} else {
			@Pc(146) int cosAngle;
			@Pc(164) int temp;
			if (transformType == 2) {
				for (i = 0; i < this.vertexCount; i++) {
					this.vertexX[i] -= originX;
					this.vertexY[i] -= originY;
					this.vertexZ[i] -= originZ;
					if (transformZ != 0) {
						sinAngle = MathUtils.sin[transformZ];
						cosAngle = MathUtils.cos[transformZ];
						temp = this.vertexY[i] * sinAngle + this.vertexX[i] * cosAngle + 32767 >> 16;
						this.vertexY[i] = this.vertexY[i] * cosAngle + 32767 - this.vertexX[i] * sinAngle >> 16;
						this.vertexX[i] = temp;
					}
					if (transformX != 0) {
						sinAngle = MathUtils.sin[transformX];
						cosAngle = MathUtils.cos[transformX];
						temp = this.vertexY[i] * cosAngle + 32767 - this.vertexZ[i] * sinAngle >> 16;
						this.vertexZ[i] = this.vertexY[i] * sinAngle + this.vertexZ[i] * cosAngle + 32767 >> 16;
						this.vertexY[i] = temp;
					}
					if (transformY != 0) {
						sinAngle = MathUtils.sin[transformY];
						cosAngle = MathUtils.cos[transformY];
						temp = this.vertexZ[i] * sinAngle + this.vertexX[i] * cosAngle + 32767 >> 16;
						this.vertexZ[i] = this.vertexZ[i] * cosAngle + 32767 - this.vertexX[i] * sinAngle >> 16;
						this.vertexX[i] = temp;
					}
					this.vertexX[i] += originX;
					this.vertexY[i] += originY;
					this.vertexZ[i] += originZ;
				}
			} else if (transformType == 3) {
				for (i = 0; i < this.vertexCount; i++) {
					this.vertexX[i] -= originX;
					this.vertexY[i] -= originY;
					this.vertexZ[i] -= originZ;
					this.vertexX[i] = this.vertexX[i] * transformX / 128;
					this.vertexY[i] = this.vertexY[i] * transformY / 128;
					this.vertexZ[i] = this.vertexZ[i] * transformZ / 128;
					this.vertexX[i] += originX;
					this.vertexY[i] += originY;
					this.vertexZ[i] += originZ;
				}
			} else if (transformType == 5) {
				for (i = 0; i < this.triangleCount; i++) {
					sinAngle = (this.triangleAlpha[i] & 0xFF) + transformX * 8;
					if (sinAngle < 0) {
						sinAngle = 0;
					} else if (sinAngle > 255) {
						sinAngle = 255;
					}
					this.triangleAlpha[i] = (byte) sinAngle;
				}
				this.colorBuffer.valid = false;
			} else if (transformType == 7) {
				for (i = 0; i < this.triangleCount; i++) {
					sinAngle = this.triangleColors[i] & 0xFFFF;
					cosAngle = sinAngle >> 10 & 0x3F;
					temp = sinAngle >> 7 & 0x7;
					@Pc(496) int lightness = sinAngle & 0x7F;
					@Pc(502) int newHue = cosAngle + transformX & 0x3F;
					temp += transformY / 4;
					if (temp < 0) {
						temp = 0;
					} else if (temp > 7) {
						temp = 7;
					}
					lightness += transformZ;
					if (lightness < 0) {
						lightness = 0;
					} else if (lightness > 127) {
						lightness = 127;
					}
					this.triangleColors[i] = (short) (newHue << 10 | temp << 7 | lightness);
				}
				this.colorBuffer.valid = false;
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "h", descriptor = "()I")
	@Override
	public final int getMinX() {
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		return this.bounds.minX;
	}

	@OriginalMember(owner = "client!td", name = "w", descriptor = "()V")
	private void ensureBuffersAndDraw() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		if (this.triangleCount == 0) {
			return;
		}
		if (this.updateFlags != 0) {
			this.packInterleavedVertexData(true, !this.vertexBuffer.valid && (this.updateFlags & 0x1) != 0, !this.colorBuffer.valid && (this.updateFlags & 0x2) != 0, this.normalsBuffer != null && !this.normalsBuffer.valid && (this.updateFlags & 0x4) != 0, false);
		}
		this.packInterleavedVertexData(false, !this.vertexBuffer.valid, !this.colorBuffer.valid, this.normalsBuffer != null && !this.normalsBuffer.valid, !this.texCoordBuffer.valid);
		if (!this.indexBuffer.valid) {
			this.buildIndexBuffer();
		}
		if (this.deferredReleaseFlags != 0) {
			if ((this.deferredReleaseFlags & 0x1) != 0) {
				this.vertexX = null;
				this.vertexY = null;
				this.vertexZ = null;
				this.vertexLookup = null;
				this.vertexOffsets = null;
			}
			if ((this.deferredReleaseFlags & 0x2) != 0) {
				this.triangleColors = null;
				this.triangleAlpha = null;
			}
			if ((this.deferredReleaseFlags & 0x4) != 0) {
				this.normalX = null;
				this.normalY = null;
				this.normalZ = null;
				this.normalMagnitude = null;
			}
			if ((this.deferredReleaseFlags & 0x8) != 0) {
				this.vertexS = null;
				this.vertexT = null;
			}
			if ((this.deferredReleaseFlags & 0x10) != 0) {
				this.triangleVertexA = null;
				this.triangleVertexB = null;
				this.triangleVertexC = null;
			}
			this.deferredReleaseFlags = 0;
		}
		@Pc(172) GlVertexBufferObject lastVbo = null;
		if (this.vertexBuffer.vbo != null) {
			this.vertexBuffer.vbo.bindArray();
			lastVbo = this.vertexBuffer.vbo;
			gl.glVertexPointer(3, GL2.GL_FLOAT, this.vertexBuffer.stride, this.vertexBuffer.pointer);
		}
		if (this.colorBuffer.vbo != null) {
			if (lastVbo != this.colorBuffer.vbo) {
				this.colorBuffer.vbo.bindArray();
				lastVbo = this.colorBuffer.vbo;
			}
			gl.glColorPointer(4, GL2.GL_UNSIGNED_BYTE, this.colorBuffer.stride, this.colorBuffer.pointer);
		}
		if (Preferences.highDetailLighting && this.normalsBuffer.vbo != null) {
			if (lastVbo != this.normalsBuffer.vbo) {
				this.normalsBuffer.vbo.bindArray();
				lastVbo = this.normalsBuffer.vbo;
			}
			gl.glNormalPointer(GL2.GL_FLOAT, this.normalsBuffer.stride, this.normalsBuffer.pointer);
		}
		if (this.texCoordBuffer.vbo != null) {
			if (lastVbo != this.texCoordBuffer.vbo) {
				this.texCoordBuffer.vbo.bindArray();
				lastVbo = this.texCoordBuffer.vbo;
			}
			gl.glTexCoordPointer(2, GL2.GL_FLOAT, this.texCoordBuffer.stride, this.texCoordBuffer.pointer);
		}
		if (this.indexBuffer.vbo != null) {
			this.indexBuffer.vbo.bindElementArray();
		}
		if (this.vertexBuffer.vbo == null || this.colorBuffer.vbo == null || Preferences.highDetailLighting && this.normalsBuffer.vbo == null || this.texCoordBuffer.vbo == null) {
			if (GlRenderer.arbVboSupported) {
				gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			}
			if (this.vertexBuffer.vbo == null) {
				this.vertexBuffer.buffer.position(this.vertexBuffer.pointer);
				gl.glVertexPointer(3, GL2.GL_FLOAT, this.vertexBuffer.stride, this.vertexBuffer.buffer);
			}
			if (this.colorBuffer.vbo == null) {
				this.colorBuffer.buffer.position(this.colorBuffer.pointer);
				gl.glColorPointer(4, GL2.GL_UNSIGNED_BYTE, this.colorBuffer.stride, this.colorBuffer.buffer);
			}
			if (Preferences.highDetailLighting && this.normalsBuffer.vbo == null) {
				this.normalsBuffer.buffer.position(this.normalsBuffer.pointer);
				gl.glNormalPointer(GL2.GL_FLOAT, this.normalsBuffer.stride, this.normalsBuffer.buffer);
			}
			if (this.texCoordBuffer.vbo == null) {
				this.texCoordBuffer.buffer.position(this.texCoordBuffer.pointer);
				gl.glTexCoordPointer(2, GL2.GL_FLOAT, this.texCoordBuffer.stride, this.texCoordBuffer.buffer);
			}
		}
		if (this.indexBuffer.vbo == null && GlRenderer.arbVboSupported) {
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		@Pc(417) int groupCount = this.textureGroupOffsets.length - 1;
		for (@Pc(419) int i = 0; i < groupCount; i++) {
			@Pc(427) int startOffset = this.textureGroupOffsets[i];
			@Pc(434) int endOffset = this.textureGroupOffsets[i + 1];
			@Pc(439) short texture = this.triangleTextures[startOffset];
			if (texture == -1) {
				GlRenderer.setTextureId(-1);
				MaterialManager.setMaterial(0, 0);
			} else {
				Rasteriser.textureProvider.bindTexture(texture & 0xFFFF);
			}
			if (this.indexBuffer.vbo == null) {
				this.indexBuffer.buffer.position(startOffset * 12);
				gl.glDrawElements(GL2.GL_TRIANGLES, (endOffset - startOffset) * 3, GL2.GL_UNSIGNED_INT, this.indexBuffer.buffer);
			} else {
				gl.glDrawElements(GL2.GL_TRIANGLES, (endOffset - startOffset) * 3, GL2.GL_UNSIGNED_INT, startOffset * 12L);
			}
		}
	}

	@OriginalMember(owner = "client!td", name = "f", descriptor = "()V")
	@Override
	protected final void resetAfterAnimation() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = this.vertexX[i] + 7 >> 4;
			this.vertexY[i] = this.vertexY[i] + 7 >> 4;
			this.vertexZ[i] = this.vertexZ[i] + 7 >> 4;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "x", descriptor = "()V")
	public final void mirrorZ() {
		@Pc(1) int i;
		for (i = 0; i < this.vertexCount; i++) {
			this.vertexZ[i] = -this.vertexZ[i];
		}
		if (this.normalZ != null) {
			for (i = 0; i < this.uniqueVertexCount; i++) {
				this.normalZ[i] = (short) -this.normalZ[i];
			}
		}
		for (i = 0; i < this.triangleCount; i++) {
			@Pc(48) short temp = this.triangleVertexA[i];
			this.triangleVertexA[i] = this.triangleVertexC[i];
			this.triangleVertexC[i] = temp;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
		this.indexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "d", descriptor = "(I)V")
	@Override
	public final void rotateX(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newY = this.vertexY[i] * cosAngle - this.vertexZ[i] * sinAngle >> 16;
			this.vertexZ[i] = this.vertexY[i] * sinAngle + this.vertexZ[i] * cosAngle >> 16;
			this.vertexY[i] = newY;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}

	@OriginalMember(owner = "client!td", name = "g", descriptor = "(I)V")
	public final void rotate45Degrees() {
		if (this.normalX == null) {
			this.rotateY(256);
			return;
		}
		@Pc(10) int sinAngle = MathUtils.sin[256];
		@Pc(14) int cosAngle = MathUtils.cos[256];
		@Pc(16) int i;
		@Pc(36) int newX;
		for (i = 0; i < this.vertexCount; i++) {
			newX = this.vertexZ[i] * sinAngle + this.vertexX[i] * cosAngle >> 16;
			this.vertexZ[i] = this.vertexZ[i] * cosAngle - this.vertexX[i] * sinAngle >> 16;
			this.vertexX[i] = newX;
		}
		for (i = 0; i < this.uniqueVertexCount; i++) {
			newX = this.normalZ[i] * sinAngle + this.normalX[i] * cosAngle >> 16;
			this.normalZ[i] = (short) (this.normalZ[i] * cosAngle - this.normalX[i] * sinAngle >> 16);
			this.normalX[i] = (short) newX;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.valid = false;
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "(Lclient!ek;)Lclient!ek;")
	public final SoftwareIndexedSprite projectShadow(@OriginalArg(0) SoftwareIndexedSprite sprite) {
		if (this.uniqueVertexCount == 0) {
			return null;
		}
		if (!this.bounds.valid) {
			this.calculateBounds();
		}
		@Pc(26) int minX;
		@Pc(40) int maxX;
		if (FogManager.lightX > 0) {
			minX = this.bounds.minX - (this.bounds.maxY * FogManager.lightX >> 8) >> 3;
			maxX = this.bounds.maxX - (this.bounds.minY * FogManager.lightX >> 8) >> 3;
		} else {
			minX = this.bounds.minX - (this.bounds.minY * FogManager.lightX >> 8) >> 3;
			maxX = this.bounds.maxX - (this.bounds.maxY * FogManager.lightX >> 8) >> 3;
		}
		@Pc(85) int minZ;
		@Pc(99) int maxZ;
		if (FogManager.lightZ > 0) {
			minZ = this.bounds.minZ - (this.bounds.maxY * FogManager.lightZ >> 8) >> 3;
			maxZ = this.bounds.maxZ - (this.bounds.minY * FogManager.lightZ >> 8) >> 3;
		} else {
			minZ = this.bounds.minZ - (this.bounds.minY * FogManager.lightZ >> 8) >> 3;
			maxZ = this.bounds.maxZ - (this.bounds.maxY * FogManager.lightZ >> 8) >> 3;
		}
		@Pc(134) int width = maxX + 1 - minX;
		@Pc(140) int height = maxZ + 1 - minZ;
		@Pc(151) SoftwareIndexedSprite shadow;
		if (sprite == null || sprite.pixels.length < width * height) {
			shadow = new SoftwareIndexedSprite(width, height, 0);
		} else {
			shadow = sprite;
			sprite.innerWidth = sprite.width = width;
			sprite.innerHeight = sprite.height = height;
			sprite.clear();
		}
		shadow.xOffset = minX;
		shadow.yOffset = minZ;
		if (pickVertexScreenX.length < this.uniqueVertexCount) {
			pickVertexScreenX = new int[this.uniqueVertexCount];
			pickVertexScreenY = new int[this.uniqueVertexCount];
		}
		@Pc(194) int i;
		@Pc(246) int endOff;
		@Pc(248) int oi;
		@Pc(258) int uniqueIdx;
		for (i = 0; i < this.vertexCount; i++) {
			@Pc(216) int projX = (this.vertexX[i] - (this.vertexY[i] * FogManager.lightX >> 8) >> 3) - minX;
			@Pc(234) int projZ = (this.vertexZ[i] - (this.vertexY[i] * FogManager.lightZ >> 8) >> 3) - minZ;
			@Pc(239) int startOff = this.vertexOffsets[i];
			endOff = this.vertexOffsets[i + 1];
			for (oi = startOff; oi < endOff; oi++) {
				uniqueIdx = this.vertexLookup[oi] - 1;
				if (uniqueIdx == -1) {
					break;
				}
				pickVertexScreenX[uniqueIdx] = projX;
				pickVertexScreenY[uniqueIdx] = projZ;
			}
		}
		for (i = 0; i < this.triangleCount; i++) {
			if (this.triangleAlpha[i] <= 128) {
				@Pc(292) short triA = this.triangleVertexA[i];
				@Pc(297) short triB = this.triangleVertexB[i];
				@Pc(302) short triC = this.triangleVertexC[i];
				endOff = pickVertexScreenX[triA];
				oi = pickVertexScreenX[triB];
				uniqueIdx = pickVertexScreenX[triC];
				@Pc(318) int zA = pickVertexScreenY[triA];
				@Pc(322) int zB = pickVertexScreenY[triB];
				@Pc(326) int zC = pickVertexScreenY[triC];
				if ((endOff - oi) * (zB - zC) - (zB - zA) * (uniqueIdx - oi) > 0) {
					Rasteriser.fillSpriteTriangle(shadow.pixels, zA, zB, zC, endOff, oi, uniqueIdx, width);
				}
			}
		}
		return shadow;
	}

	@OriginalMember(owner = "client!td", name = "l", descriptor = "()V")
	@Override
	public final void rotateClockwise() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			@Pc(10) int temp = this.vertexZ[i];
			this.vertexZ[i] = this.vertexX[i];
			this.vertexX[i] = -temp;
		}
		this.bounds.valid = false;
		this.vertexBuffer.valid = false;
	}
}
