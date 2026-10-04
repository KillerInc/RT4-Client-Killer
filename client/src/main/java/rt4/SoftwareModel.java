package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.api.API;

@OriginalClass("client!w")
public final class SoftwareModel extends Model {

	@OriginalMember(owner = "client!w", name = "nb", descriptor = "Lclient!w;")
	public static final SoftwareModel locCopyTarget = new SoftwareModel();

	@OriginalMember(owner = "client!w", name = "ab", descriptor = "Lclient!w;")
	public static final SoftwareModel entityCopyTarget = new SoftwareModel();

	@OriginalMember(owner = "client!w", name = "eb", descriptor = "Lclient!w;")
	public static final SoftwareModel animCopyTarget = new SoftwareModel();

	@OriginalMember(owner = "client!w", name = "pb", descriptor = "[Z")
	public static final boolean[] projectTriangle = new boolean[4096];

	@OriginalMember(owner = "client!w", name = "qb", descriptor = "[I")
	public static final int[] priorityTriangleCounts = new int[12];

	@OriginalMember(owner = "client!w", name = "rb", descriptor = "[I")
	public static final int[] clippedScreenX = new int[10];

	@OriginalMember(owner = "client!w", name = "sb", descriptor = "[I")
	public static final int[] vertexDepth = new int[4096];

	@OriginalMember(owner = "client!w", name = "tb", descriptor = "[I")
	public static final int[] projectSceneZ = new int[4096];

	@OriginalMember(owner = "client!w", name = "ub", descriptor = "[I")
	public static final int[] normalTrianglePriority = new int[4096];

	@OriginalMember(owner = "client!w", name = "xb", descriptor = "[I")
	public static final int[] clippedScreenY = new int[10];

	@OriginalMember(owner = "client!w", name = "Ab", descriptor = "[I")
	public static final int[] projectSceneY = new int[4096];

	@OriginalMember(owner = "client!w", name = "Eb", descriptor = "[I")
	public static final int[] vertexScreenY = new int[4096];

	@OriginalMember(owner = "client!w", name = "Ib", descriptor = "[I")
	public static final int[] clippedLightness = new int[10];

	@OriginalMember(owner = "client!w", name = "Jb", descriptor = "[Z")
	public static final boolean[] testTriangleX = new boolean[4096];

	@OriginalMember(owner = "client!w", name = "Kb", descriptor = "[[I")
	public static final int[][] priorityTriangles = new int[12][4096];

	@OriginalMember(owner = "client!w", name = "Nb", descriptor = "[I")
	public static final int[] highTrianglePriority = new int[4096];

	@OriginalMember(owner = "client!w", name = "Pb", descriptor = "[I")
	public static final int[] vertexScreenX = new int[4096];

	@OriginalMember(owner = "client!w", name = "Qb", descriptor = "[I")
	public static final int[] lowTrianglePriority = new int[12];

	@OriginalMember(owner = "client!w", name = "Ub", descriptor = "[I")
	public static final int[] projectSceneX = new int[4096];

	@OriginalMember(owner = "client!w", name = "Wb", descriptor = "[I")
	public static final int[] deferredTriangles = new int[8192];

	@OriginalMember(owner = "client!w", name = "vb", descriptor = "[[I")
	public static int[][] overflowBucketTriangles;

	@OriginalMember(owner = "client!w", name = "Ob", descriptor = "Z")
	public static boolean useDepthSort = false;

	@OriginalMember(owner = "client!w", name = "Lb", descriptor = "[I")
	public static int[] locCopyFaceColorA = new int[1];

	@OriginalMember(owner = "client!w", name = "Vb", descriptor = "[S")
	public static short[] locCopyColors = new short[1];

	@OriginalMember(owner = "client!w", name = "Fb", descriptor = "[B")
	public static byte[] locCopyAlpha = new byte[1];

	@OriginalMember(owner = "client!w", name = "Tb", descriptor = "[I")
	public static int[] locCopyFaceColorB = new int[1];

	@OriginalMember(owner = "client!w", name = "wb", descriptor = "[I")
	public static int[] locCopyFaceColorC = new int[1];

	@OriginalMember(owner = "client!w", name = "Mb", descriptor = "I")
	public static int originZ;

	@OriginalMember(owner = "client!w", name = "Gb", descriptor = "I")
	public static int originY;

	@OriginalMember(owner = "client!w", name = "Rb", descriptor = "I")
	public static int originX;

	@OriginalMember(owner = "client!w", name = "Db", descriptor = "[I")
	public static int[] triangleDepths;

	@OriginalMember(owner = "client!w", name = "Bb", descriptor = "[I")
	public static int[] depthTriangles;

	@OriginalMember(owner = "client!w", name = "yb", descriptor = "[[I")
	public static int[][] bucketTriangles;

	@OriginalMember(owner = "client!w", name = "Sb", descriptor = "[I")
	public static int[] bucketCounts;

	@OriginalMember(owner = "client!w", name = "Hb", descriptor = "[I")
	public static int[] overflowBucketCounts;

	@OriginalMember(owner = "client!w", name = "cb", descriptor = "[I")
	public static int[] entityCopyFaceColorC = new int[1];

	@OriginalMember(owner = "client!w", name = "db", descriptor = "[I")
	public static int[] entityCopyFaceColorA = new int[1];

	@OriginalMember(owner = "client!w", name = "fb", descriptor = "[B")
	public static byte[] entityCopyAlpha = new byte[1];

	@OriginalMember(owner = "client!w", name = "hb", descriptor = "[I")
	public static int[] entityCopyFaceColorB = new int[1];

	@OriginalMember(owner = "client!w", name = "ib", descriptor = "[S")
	public static short[] entityCopyColors = new short[1];

	@OriginalMember(owner = "client!w", name = "jb", descriptor = "[I")
	public static int[] animCopyFaceColorC = new int[1];

	@OriginalMember(owner = "client!w", name = "kb", descriptor = "[S")
	public static short[] animCopyColors = new short[1];

	@OriginalMember(owner = "client!w", name = "lb", descriptor = "[I")
	public static int[] animCopyFaceColorA = new int[1];

	@OriginalMember(owner = "client!w", name = "mb", descriptor = "[B")
	public static byte[] animCopyAlpha = new byte[1];

	@OriginalMember(owner = "client!w", name = "ob", descriptor = "[I")
	public static int[] animCopyFaceColorB = new int[1];

	@OriginalMember(owner = "client!w", name = "zb", descriptor = "Z")
	public static boolean originValid = false;

	@OriginalMember(owner = "client!w", name = "Cb", descriptor = "I")
	public static int overflowBucketCount = 0;

	@OriginalMember(owner = "client!w", name = "t", descriptor = "[S")
	private short[] triangleColors;

	@OriginalMember(owner = "client!w", name = "u", descriptor = "[[I")
	private int[][] boneVertices;

	@OriginalMember(owner = "client!w", name = "v", descriptor = "[S")
	private short[] triangleSources;

	@OriginalMember(owner = "client!w", name = "w", descriptor = "[[I")
	private int[][] boneTriangles;

	@OriginalMember(owner = "client!w", name = "x", descriptor = "S")
	private short minX;

	@OriginalMember(owner = "client!w", name = "y", descriptor = "[I")
	private int[] faceColorB;

	@OriginalMember(owner = "client!w", name = "A", descriptor = "[I")
	private int[] triangleVertexB;

	@OriginalMember(owner = "client!w", name = "C", descriptor = "S")
	private short maxX;

	@OriginalMember(owner = "client!w", name = "D", descriptor = "S")
	private short minZ;

	@OriginalMember(owner = "client!w", name = "E", descriptor = "[B")
	private byte[] trianglePriorities;

	@OriginalMember(owner = "client!w", name = "F", descriptor = "[I")
	private int[] textureFacesN;

	@OriginalMember(owner = "client!w", name = "G", descriptor = "[I")
	private int[] textureFacesP;

	@OriginalMember(owner = "client!w", name = "H", descriptor = "[I")
	public int[] vertexY;

	@OriginalMember(owner = "client!w", name = "I", descriptor = "S")
	private short lengthXZ;

	@OriginalMember(owner = "client!w", name = "J", descriptor = "[I")
	public int[] vertexX;

	@OriginalMember(owner = "client!w", name = "K", descriptor = "S")
	private short lengthXYZ;

	@OriginalMember(owner = "client!w", name = "L", descriptor = "[I")
	private int[] triangleVertexC;

	@OriginalMember(owner = "client!w", name = "M", descriptor = "S")
	private short maxZ;

	@OriginalMember(owner = "client!w", name = "O", descriptor = "[I")
	private int[] textureFacesM;

	@OriginalMember(owner = "client!w", name = "Q", descriptor = "S")
	private short maxY;

	@OriginalMember(owner = "client!w", name = "R", descriptor = "[S")
	private short[] vertexSources;

	@OriginalMember(owner = "client!w", name = "S", descriptor = "S")
	private short minY;

	@OriginalMember(owner = "client!w", name = "T", descriptor = "[I")
	public int[] vertexZ;

	@OriginalMember(owner = "client!w", name = "U", descriptor = "[I")
	private int[] triangleInfo;

	@OriginalMember(owner = "client!w", name = "W", descriptor = "[I")
	private int[] faceColorA;

	@OriginalMember(owner = "client!w", name = "X", descriptor = "[B")
	private byte[] triangleTextureIndex;

	@OriginalMember(owner = "client!w", name = "Z", descriptor = "[I")
	private int[] triangleVertexA;

	@OriginalMember(owner = "client!w", name = "bb", descriptor = "[B")
	private byte[] triangleAlpha;

	@OriginalMember(owner = "client!w", name = "gb", descriptor = "[S")
	private short[] triangleTextures;

	@OriginalMember(owner = "client!w", name = "z", descriptor = "B")
	private byte priority = 0;

	@OriginalMember(owner = "client!w", name = "V", descriptor = "Z")
	public boolean boundsValid = false;

	@OriginalMember(owner = "client!w", name = "P", descriptor = "I")
	public int vertexCount = 0;

	@OriginalMember(owner = "client!w", name = "N", descriptor = "I")
	private int triangleCount = 0;

	@OriginalMember(owner = "client!w", name = "Y", descriptor = "I")
	private int texturedFaceCount = 0;

	@OriginalMember(owner = "client!w", name = "B", descriptor = "Z")
	private boolean colorsModified = false;

	static {
		if (useDepthSort) {
			triangleDepths = new int[4096];
			depthTriangles = new int[4096];
		} else {
			bucketCounts = new int[1600];
			bucketTriangles = new int[1600][64];
			overflowBucketCounts = new int[32];
			overflowBucketTriangles = new int[32][512];
		}
	}

	@OriginalMember(owner = "client!w", name = "<init>", descriptor = "()V")
	public SoftwareModel() {
	}

	@OriginalMember(owner = "client!w", name = "<init>", descriptor = "(Lclient!gb;IIIII)V")
	public SoftwareModel(@OriginalArg(0) RawModel model, @OriginalArg(1) int ambience, @OriginalArg(2) int contrast, @OriginalArg(3) int lightX, @OriginalArg(4) int lightY, @OriginalArg(5) int lightZ) {
		model.calculateNormals();
		model.createBones();
		this.vertexCount = model.vertexCount;
		this.vertexX = model.vertexX;
		this.vertexY = model.vertexY;
		this.vertexZ = model.vertexZ;
		this.triangleCount = model.triangleCount;
		this.triangleVertexA = model.triangleVertexA;
		this.triangleVertexB = model.triangleVertexB;
		this.triangleVertexC = model.triangleVertexC;
		this.trianglePriorities = model.trianglePriorities;
		this.triangleAlpha = model.triangleAlpha;
		this.priority = model.priority;
		this.triangleColors = model.triangleColors;
		this.boneVertices = model.boneVertices;
		this.boneTriangles = model.boneTriangles;
		this.triangleSources = model.triangleSources;
		this.vertexSources = model.vertexSources;
		@Pc(102) int lightMagnitude = (int) Math.sqrt(lightX * lightX + lightY * lightY + lightZ * lightZ);
		@Pc(108) int contrastLight = contrast * lightMagnitude >> 8;
		this.faceColorA = new int[this.triangleCount];
		this.faceColorB = new int[this.triangleCount];
		this.triangleInfo = new int[this.triangleCount];
		@Pc(133) int i;
		if (model.triangleTextures == null) {
			this.triangleTextures = null;
		} else {
			this.triangleTextures = new short[this.triangleCount];
			for (i = 0; i < this.triangleCount; i++) {
				@Pc(142) short texture = model.triangleTextures[i];
				if (texture != -1 && Rasteriser.textureProvider.isTextureRepeating(texture)) {
					this.triangleTextures[i] = texture;
				} else {
					this.triangleTextures[i] = -1;
				}
			}
		}
		if (model.texturedCount > 0 && model.triangleTextureIndex != null) {
			@Pc(177) int[] texRefCounts = new int[model.texturedCount];
			@Pc(179) int texIdx;
			for (texIdx = 0; texIdx < this.triangleCount; texIdx++) {
				if (model.triangleTextureIndex[texIdx] != -1) {
					texRefCounts[model.triangleTextureIndex[texIdx] & 0xFF]++;
				}
			}
			this.texturedFaceCount = 0;
			for (texIdx = 0; texIdx < model.texturedCount; texIdx++) {
				if (texRefCounts[texIdx] > 0 && model.textureTypes[texIdx] == 0) {
					this.texturedFaceCount++;
				}
			}
			this.textureFacesP = new int[this.texturedFaceCount];
			this.textureFacesM = new int[this.texturedFaceCount];
			this.textureFacesN = new int[this.texturedFaceCount];
			texIdx = 0;
			@Pc(248) int j;
			for (j = 0; j < model.texturedCount; j++) {
				if (texRefCounts[j] > 0 && model.textureTypes[j] == 0) {
					this.textureFacesP[texIdx] = model.textureFacesP[j] & 0xFFFF;
					this.textureFacesM[texIdx] = model.textureFacesM[j] & 0xFFFF;
					this.textureFacesN[texIdx] = model.textureFacesN[j] & 0xFFFF;
					texRefCounts[j] = texIdx++;
				} else {
					texRefCounts[j] = -1;
				}
			}
			this.triangleTextureIndex = new byte[this.triangleCount];
			for (j = 0; j < this.triangleCount; j++) {
				if (model.triangleTextureIndex[j] == -1) {
					this.triangleTextureIndex[j] = -1;
				} else {
					this.triangleTextureIndex[j] = (byte) texRefCounts[model.triangleTextureIndex[j] & 0xFF];
					if (this.triangleTextureIndex[j] == -1 && this.triangleTextures != null) {
						this.triangleTextures[j] = -1;
					}
				}
			}
		}
		for (i = 0; i < this.triangleCount; i++) {
			@Pc(366) byte renderType;
			if (model.triangleInfo == null) {
				renderType = 0;
			} else {
				renderType = model.triangleInfo[i];
			}
			@Pc(377) byte alpha;
			if (model.triangleAlpha == null) {
				alpha = 0;
			} else {
				alpha = model.triangleAlpha[i];
			}
			@Pc(388) short textureId;
			if (this.triangleTextures == null) {
				textureId = -1;
			} else {
				textureId = this.triangleTextures[i];
			}
			if (alpha == -2) {
				renderType = 3;
			}
			if (alpha == -1) {
				renderType = 2;
			}
			@Pc(435) VertexNormal normal;
			@Pc(468) int lightness;
			@Pc(614) TriangleNormal faceNormal;
			if (textureId == -1) {
				if (renderType == 0) {
					@Pc(416) int color = model.triangleColors[i] & 0xFFFF;
					if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexA[i]] == null) {
						normal = model.vertexNormals[this.triangleVertexA[i]];
					} else {
						normal = model.mergedNormals[this.triangleVertexA[i]];
					}
					lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude) << 17;
					this.faceColorA[i] = lightness | ColorUtils.multiplyLightness2(color, lightness >> 17);
					if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexB[i]] == null) {
						normal = model.vertexNormals[this.triangleVertexB[i]];
					} else {
						normal = model.mergedNormals[this.triangleVertexB[i]];
					}
					lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude) << 17;
					this.faceColorB[i] = lightness | ColorUtils.multiplyLightness2(color, lightness >> 17);
					if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexC[i]] == null) {
						normal = model.vertexNormals[this.triangleVertexC[i]];
					} else {
						normal = model.mergedNormals[this.triangleVertexC[i]];
					}
					lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude) << 17;
					this.triangleInfo[i] = lightness | ColorUtils.multiplyLightness2(color, lightness >> 17);
				} else if (renderType == 1) {
					faceNormal = model.triangleNormals[i];
					lightness = ambience + (lightX * faceNormal.x + lightY * faceNormal.y + lightZ * faceNormal.z) / (contrastLight + contrastLight / 2) << 17;
					this.faceColorA[i] = lightness | ColorUtils.multiplyLightness2(model.triangleColors[i] & 0xFFFF, lightness >> 17);
					this.triangleInfo[i] = -1;
				} else if (renderType == 3) {
					this.faceColorA[i] = 128;
					this.triangleInfo[i] = -1;
				} else {
					this.triangleInfo[i] = -2;
				}
			} else if (renderType == 0) {
				if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexA[i]] == null) {
					normal = model.vertexNormals[this.triangleVertexA[i]];
				} else {
					normal = model.mergedNormals[this.triangleVertexA[i]];
				}
				lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude);
				this.faceColorA[i] = ColorUtils.clampLightness(lightness);
				if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexB[i]] == null) {
					normal = model.vertexNormals[this.triangleVertexB[i]];
				} else {
					normal = model.mergedNormals[this.triangleVertexB[i]];
				}
				lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude);
				this.faceColorB[i] = ColorUtils.clampLightness(lightness);
				if (model.mergedNormals == null || model.mergedNormals[this.triangleVertexC[i]] == null) {
					normal = model.vertexNormals[this.triangleVertexC[i]];
				} else {
					normal = model.mergedNormals[this.triangleVertexC[i]];
				}
				lightness = ambience + (lightX * normal.x + lightY * normal.y + lightZ * normal.z) / (contrastLight * normal.magnitude);
				this.triangleInfo[i] = ColorUtils.clampLightness(lightness);
			} else if (renderType == 1) {
				faceNormal = model.triangleNormals[i];
				lightness = ambience + (lightX * faceNormal.x + lightY * faceNormal.y + lightZ * faceNormal.z) / (contrastLight + contrastLight / 2);
				this.faceColorA[i] = ColorUtils.clampLightness(lightness);
				this.triangleInfo[i] = -1;
			} else {
				this.triangleInfo[i] = -2;
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "<init>", descriptor = "([Lclient!w;I)V")
	private SoftwareModel(@OriginalArg(0) SoftwareModel[] models, @OriginalArg(1) int count) {
		@Pc(21) boolean hasPriorities = false;
		@Pc(23) boolean hasAlpha = false;
		@Pc(25) boolean hasTextures = false;
		@Pc(27) boolean hasTextureIndex = false;
		this.vertexCount = 0;
		this.triangleCount = 0;
		this.texturedFaceCount = 0;
		this.priority = -1;
		@Pc(45) int i;
		@Pc(52) SoftwareModel subModel;
		for (i = 0; i < count; i++) {
			subModel = models[i];
			if (subModel != null) {
				this.vertexCount += subModel.vertexCount;
				this.triangleCount += subModel.triangleCount;
				this.texturedFaceCount += subModel.texturedFaceCount;
				if (subModel.trianglePriorities == null) {
					if (this.priority == -1) {
						this.priority = subModel.priority;
					}
					if (this.priority != subModel.priority) {
						hasPriorities = true;
					}
				} else {
					hasPriorities = true;
				}
				hasAlpha |= subModel.triangleAlpha != null;
				hasTextures |= subModel.triangleTextures != null;
				hasTextureIndex |= subModel.triangleTextureIndex != null;
			}
		}
		this.vertexX = new int[this.vertexCount];
		this.vertexY = new int[this.vertexCount];
		this.vertexZ = new int[this.vertexCount];
		this.triangleVertexA = new int[this.triangleCount];
		this.triangleVertexB = new int[this.triangleCount];
		this.triangleVertexC = new int[this.triangleCount];
		this.faceColorA = new int[this.triangleCount];
		this.faceColorB = new int[this.triangleCount];
		this.triangleInfo = new int[this.triangleCount];
		if (hasPriorities) {
			this.trianglePriorities = new byte[this.triangleCount];
		}
		if (hasAlpha) {
			this.triangleAlpha = new byte[this.triangleCount];
		}
		if (hasTextures) {
			this.triangleTextures = new short[this.triangleCount];
		}
		if (hasTextureIndex) {
			this.triangleTextureIndex = new byte[this.triangleCount];
		}
		if (this.texturedFaceCount > 0) {
			this.textureFacesP = new int[this.texturedFaceCount];
			this.textureFacesM = new int[this.texturedFaceCount];
			this.textureFacesN = new int[this.texturedFaceCount];
		}
		this.triangleColors = new short[this.triangleCount];
		this.vertexCount = 0;
		this.triangleCount = 0;
		this.texturedFaceCount = 0;
		for (i = 0; i < count; i++) {
			subModel = models[i];
			if (subModel != null) {
				@Pc(251) int j;
				for (j = 0; j < subModel.triangleCount; j++) {
					this.triangleVertexA[this.triangleCount] = subModel.triangleVertexA[j] + this.vertexCount;
					this.triangleVertexB[this.triangleCount] = subModel.triangleVertexB[j] + this.vertexCount;
					this.triangleVertexC[this.triangleCount] = subModel.triangleVertexC[j] + this.vertexCount;
					this.faceColorA[this.triangleCount] = subModel.faceColorA[j];
					this.faceColorB[this.triangleCount] = subModel.faceColorB[j];
					this.triangleInfo[this.triangleCount] = subModel.triangleInfo[j];
					this.triangleColors[this.triangleCount] = subModel.triangleColors[j];
					if (hasPriorities) {
						if (subModel.trianglePriorities == null) {
							this.trianglePriorities[this.triangleCount] = subModel.priority;
						} else {
							this.trianglePriorities[this.triangleCount] = subModel.trianglePriorities[j];
						}
					}
					if (hasAlpha && subModel.triangleAlpha != null) {
						this.triangleAlpha[this.triangleCount] = subModel.triangleAlpha[j];
					}
					if (hasTextures) {
						if (subModel.triangleTextures == null) {
							this.triangleTextures[this.triangleCount] = -1;
						} else {
							this.triangleTextures[this.triangleCount] = subModel.triangleTextures[j];
						}
					}
					if (hasTextureIndex) {
						if (subModel.triangleTextureIndex == null || subModel.triangleTextureIndex[j] == -1) {
							this.triangleTextureIndex[this.triangleCount] = -1;
						} else {
							this.triangleTextureIndex[this.triangleCount] = (byte) (subModel.triangleTextureIndex[j] + this.texturedFaceCount);
						}
					}
					this.triangleCount++;
				}
				for (j = 0; j < subModel.texturedFaceCount; j++) {
					this.textureFacesP[this.texturedFaceCount] = subModel.textureFacesP[j] + this.vertexCount;
					this.textureFacesM[this.texturedFaceCount] = subModel.textureFacesM[j] + this.vertexCount;
					this.textureFacesN[this.texturedFaceCount] = subModel.textureFacesN[j] + this.vertexCount;
					this.texturedFaceCount++;
				}
				for (j = 0; j < subModel.vertexCount; j++) {
					this.vertexX[this.vertexCount] = subModel.vertexX[j];
					this.vertexY[this.vertexCount] = subModel.vertexY[j];
					this.vertexZ[this.vertexCount] = subModel.vertexZ[j];
					this.vertexCount++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "m", descriptor = "()V")
	public static void enableDepthSortMode() {
		useDepthSort = true;
		triangleDepths = new int[4096];
		depthTriangles = new int[4096];
		bucketCounts = null;
		bucketTriangles = null;
		overflowBucketCounts = null;
		overflowBucketTriangles = null;
	}

	@OriginalMember(owner = "client!w", name = "o", descriptor = "()V")
	public static void enableBucketSortMode() {
		useDepthSort = false;
		triangleDepths = null;
		depthTriangles = null;
		bucketCounts = new int[1600];
		bucketTriangles = new int[1600][64];
		overflowBucketCounts = new int[32];
		overflowBucketTriangles = new int[32][512];
	}

	@OriginalMember(owner = "client!w", name = "e", descriptor = "(I)V")
	private void drawTriangle(@OriginalArg(0) int index) {
		if (projectTriangle[index]) {
			this.drawProjectedTriangle(index);
			return;
		}
		@Pc(12) int a = this.triangleVertexA[index];
		@Pc(17) int b = this.triangleVertexB[index];
		@Pc(22) int c = this.triangleVertexC[index];
		Rasteriser.testX = testTriangleX[index];
		if (this.triangleAlpha == null) {
			Rasteriser.alpha = 0;
		} else {
			Rasteriser.alpha = this.triangleAlpha[index] & 0xFF;
		}

		if (this.triangleTextures != null && this.triangleTextures[index] != -1) {
			@Pc(141) int tA;
			@Pc(146) int tB;
			@Pc(151) int tC;
			if (this.triangleTextureIndex == null || this.triangleTextureIndex[index] == -1) {
				tA = a;
				tB = b;
				tC = c;
			} else {
				@Pc(136) int texIdx = this.triangleTextureIndex[index] & 0xFF;
				tA = this.textureFacesP[texIdx];
				tB = this.textureFacesM[texIdx];
				tC = this.textureFacesN[texIdx];
			}
			if (this.triangleInfo[index] == -1) {
				Rasteriser.fillTexturedAlphaTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], this.faceColorA[index], this.faceColorA[index], this.faceColorA[index], projectSceneX[tA], projectSceneX[tB], projectSceneX[tC], projectSceneY[tA], projectSceneY[tB], projectSceneY[tC], projectSceneZ[tA], projectSceneZ[tB], projectSceneZ[tC], this.triangleTextures[index]);
			} else {
				Rasteriser.fillTexturedAlphaTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], this.faceColorA[index], this.faceColorB[index], this.triangleInfo[index], projectSceneX[tA], projectSceneX[tB], projectSceneX[tC], projectSceneY[tA], projectSceneY[tB], projectSceneY[tC], projectSceneZ[tA], projectSceneZ[tB], projectSceneZ[tC], this.triangleTextures[index]);
			}
		} else if (this.triangleInfo[index] == -1) {
			Rasteriser.fillTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], Rasteriser.palette[this.faceColorA[index] & 0xFFFF]);
		} else {
			Rasteriser.fillGouraudTriangle(vertexScreenY[a], vertexScreenY[b], vertexScreenY[c], vertexScreenX[a], vertexScreenX[b], vertexScreenX[c], this.faceColorA[index] & 0xFFFF, this.faceColorB[index] & 0xFFFF, this.triangleInfo[index] & 0xFFFF);
		}
	}

	@OriginalMember(owner = "client!w", name = "e", descriptor = "()V")
	@Override
	public void rotate180() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = -this.vertexX[i];
			this.vertexZ[i] = -this.vertexZ[i];
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "n", descriptor = "()V")
	private void recalculateTriangleColors() {
		for (@Pc(1) int i = 0; i < this.triangleCount; i++) {
			@Pc(15) short texture = this.triangleTextures == null ? -1 : this.triangleTextures[i];
			if (texture == -1) {
				@Pc(25) int color = this.triangleColors[i] & 0xFFFF;
				@Pc(38) int packed;
				if (this.triangleInfo[i] == -1) {
					packed = this.faceColorA[i] & 0xFFFE0000;
					this.faceColorA[i] = packed | ColorUtils.multiplyLightness2(color, packed >> 17);
				} else if (this.triangleInfo[i] != -2) {
					packed = this.faceColorA[i] & 0xFFFE0000;
					this.faceColorA[i] = packed | ColorUtils.multiplyLightness2(color, packed >> 17);
					packed = this.faceColorB[i] & 0xFFFE0000;
					this.faceColorB[i] = packed | ColorUtils.multiplyLightness2(color, packed >> 17);
					packed = this.triangleInfo[i] & 0xFFFE0000;
					this.triangleInfo[i] = packed | ColorUtils.multiplyLightness2(color, packed >> 17);
				}
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "k", descriptor = "()I")
	@Override
	public int getMinZ() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minZ;
	}

	@OriginalMember(owner = "client!w", name = "b", descriptor = "()I")
	@Override
	public int getMinY() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minY;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(ZZLclient!w;[B[S[I[I[I)Lclient!ak;")
	private Model copy(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) SoftwareModel model, @OriginalArg(3) byte[] alphaBuffer, @OriginalArg(4) short[] colorsBuffer, @OriginalArg(5) int[] faceColorABuffer, @OriginalArg(6) int[] faceColorBBuffer, @OriginalArg(7) int[] infoBuffer) {
		model.vertexCount = this.vertexCount;
		model.triangleCount = this.triangleCount;
		model.texturedFaceCount = this.texturedFaceCount;
		if (model.vertexX == null || model.vertexX.length < this.vertexCount) {
			model.vertexX = new int[this.vertexCount + 100];
			model.vertexY = new int[this.vertexCount + 100];
			model.vertexZ = new int[this.vertexCount + 100];
		}
		@Pc(43) int i;
		for (i = 0; i < this.vertexCount; i++) {
			model.vertexX[i] = this.vertexX[i];
			model.vertexY[i] = this.vertexY[i];
			model.vertexZ[i] = this.vertexZ[i];
		}
		if (shareAlpha) {
			model.triangleAlpha = this.triangleAlpha;
		} else {
			model.triangleAlpha = alphaBuffer;
			if (this.triangleAlpha == null) {
				for (i = 0; i < this.triangleCount; i++) {
					model.triangleAlpha[i] = 0;
				}
			} else {
				for (i = 0; i < this.triangleCount; i++) {
					model.triangleAlpha[i] = this.triangleAlpha[i];
				}
			}
		}
		if (shareColors) {
			model.triangleColors = this.triangleColors;
			model.faceColorA = this.faceColorA;
			model.faceColorB = this.faceColorB;
			model.triangleInfo = this.triangleInfo;
		} else {
			model.triangleColors = colorsBuffer;
			model.faceColorA = faceColorABuffer;
			model.faceColorB = faceColorBBuffer;
			model.triangleInfo = infoBuffer;
			for (i = 0; i < this.triangleCount; i++) {
				model.triangleColors[i] = this.triangleColors[i];
				model.faceColorA[i] = this.faceColorA[i];
				model.faceColorB[i] = this.faceColorB[i];
				model.triangleInfo[i] = this.triangleInfo[i];
			}
		}
		model.triangleVertexA = this.triangleVertexA;
		model.triangleVertexB = this.triangleVertexB;
		model.triangleVertexC = this.triangleVertexC;
		model.trianglePriorities = this.trianglePriorities;
		model.triangleTextureIndex = this.triangleTextureIndex;
		model.triangleTextures = this.triangleTextures;
		model.priority = this.priority;
		model.textureFacesP = this.textureFacesP;
		model.textureFacesM = this.textureFacesM;
		model.textureFacesN = this.textureFacesN;
		model.boneVertices = this.boneVertices;
		model.boneTriangles = this.boneTriangles;
		model.vertexSources = this.vertexSources;
		model.triangleSources = this.triangleSources;
		model.pickable = this.pickable;
		model.boundsValid = false;
		return model;
	}

	@OriginalMember(owner = "client!w", name = "d", descriptor = "(I)V")
	@Override
	public void rotateX(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newY = this.vertexY[i] * cosAngle - this.vertexZ[i] * sinAngle >> 16;
			this.vertexZ[i] = this.vertexY[i] * sinAngle + this.vertexZ[i] * cosAngle >> 16;
			this.vertexY[i] = newY;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "d", descriptor = "()Z")
	@Override
	protected boolean hasAnimationBones() {
		if (this.boneVertices == null) {
			return false;
		} else {
			originX = 0;
			originY = 0;
			originZ = 0;
			return true;
		}
	}

	@OriginalMember(owner = "client!w", name = "c", descriptor = "(I)V")
	@Override
	public void rotateZ(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newX = this.vertexY[i] * sinAngle + this.vertexX[i] * cosAngle >> 16;
			this.vertexY[i] = this.vertexY[i] * cosAngle - this.vertexX[i] * sinAngle >> 16;
			this.vertexX[i] = newX;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinCameraPitch, @OriginalArg(2) int cosCameraPitch, @OriginalArg(3) int sinCameraYaw, @OriginalArg(4) int cosCameraYaw, @OriginalArg(5) int sceneX, @OriginalArg(6) int sceneZ, @OriginalArg(7) int sceneY, @OriginalArg(8) long key, @OriginalArg(9) int plane, @OriginalArg(10) ParticleSystem particleSystem) {
		if (!this.boundsValid) {
			this.calculateBounds();
		}

		@Pc(14) int a = sceneY * cosCameraYaw - sceneX * sinCameraYaw >> 16;
		@Pc(24) int b = sceneZ * sinCameraPitch + a * cosCameraPitch >> 16;
		@Pc(38) int c = b + (this.lengthXZ * cosCameraPitch + this.maxY * sinCameraPitch >> 16);
		@Pc(53) int d = b + (-this.lengthXZ * cosCameraPitch + this.minY * sinCameraPitch >> 16);
		if (c <= 50 || d >= 3500) { // near and far clip
			return;
		}

		@Pc(71) int e = sceneY * sinCameraYaw + sceneX * cosCameraYaw >> 16;
		@Pc(78) int minScreenX = e + this.lengthXZ << 9;
		if (minScreenX / c <= Rasteriser.screenLowerX) {
			return;
		}
		@Pc(91) int maxScreenX = e - this.lengthXZ << 9;
		if (maxScreenX / c >= Rasteriser.screenUpperX) {
			return;
		}
		@Pc(107) int f = sceneZ * cosCameraPitch - a * sinCameraPitch >> 16;
		@Pc(123) int minScreenY = f + (this.lengthXZ * sinCameraPitch + this.maxY * cosCameraPitch >> 16) << 9;
		if (minScreenY / c <= Rasteriser.screenLowerY) {
			return;
		}
		@Pc(146) int maxScreenY = f + (-this.lengthXZ * sinCameraPitch + this.minY * cosCameraPitch >> 16) << 9;
		if (maxScreenY / c >= Rasteriser.screenUpperY) {
			return;
		}
		@Pc(154) boolean project1 = false;
		@Pc(161) boolean project2 = d <= 50;
		@Pc(170) boolean project = project2 || this.texturedFaceCount > 0;

		@Pc(172) int cx = Rasteriser.centerX;
		@Pc(174) int cy = Rasteriser.centerY;

		@Pc(176) int yawsin = 0;
		@Pc(178) int yawcos = 0;
		if (yaw != 0) {
			yawsin = MathUtils.sin[yaw];
			yawcos = MathUtils.cos[yaw];
		}
		@Pc(190) boolean needsPicking = false;
		@Pc(204) int v;
		@Pc(223) int x;
		@Pc(208) int y;
		@Pc(227) int z;
		@Pc(229) boolean roofVisibilityLocPick = API.IsRoofVisibilityLocPickable(key);
		@Pc(232) boolean miniMenuPick = key > 0L;
		if ((miniMenuPick || roofVisibilityLocPick) && RawModel.allowInput && d > 0) {
			if (e > 0) {
				v = maxScreenX / c;
				y = minScreenX / d;
			} else {
				v = maxScreenX / d;
				y = minScreenX / c;
			}
			if (f > 0) {
				x = maxScreenY / c;
				z = minScreenY / d;
			} else {
				x = maxScreenY / d;
				z = minScreenY / c;
			}
			if (GlModel.pickScreenX >= v && GlModel.pickScreenX <= y && RawModel.pickScreenY >= x && RawModel.pickScreenY <= z) {
				v = 999999;
				y = -999999;
				x = 999999;
				z = -999999;
				@Pc(299) int[] cornersX = new int[]{this.minX, this.maxX, this.minX, this.maxX, this.minX, this.maxX, this.minX, this.maxX};
				@Pc(342) int[] cornersZ = new int[]{this.minZ, this.minZ, this.maxZ, this.maxZ, this.minZ, this.minZ, this.maxZ, this.maxZ};
				@Pc(385) int[] cornersY = new int[]{this.minY, this.minY, this.minY, this.minY, this.maxY, this.maxY, this.maxY, this.maxY};
				for (@Pc(387) int ci = 0; ci < 8; ci++) {
					@Pc(394) int bx = cornersX[ci];
					@Pc(398) int by = cornersY[ci];
					@Pc(402) int bz = cornersZ[ci];
					@Pc(414) int temp;
					if (yaw != 0) {
						temp = bz * yawsin + bx * yawcos >> 16;
						bz = bz * yawcos - bx * yawsin >> 16;
						bx = temp;
					}
					bx += sceneX;
					by += sceneZ;
					bz += sceneY;
					temp = bz * sinCameraYaw + bx * cosCameraYaw >> 16;
					bz = bz * cosCameraYaw - bx * sinCameraYaw >> 16;
					bx = temp;
					temp = by * cosCameraPitch - bz * sinCameraPitch >> 16;
					bz = by * sinCameraPitch + bz * cosCameraPitch >> 16;
					if (bz > 0) {
						@Pc(490) int sx = (bx << 9) / bz;
						@Pc(496) int sy = (temp << 9) / bz;
						if (sx < v) {
							v = sx;
						}
						if (sx > y) {
							y = sx;
						}
						if (sy < x) {
							x = sy;
						}
						if (sy > z) {
							z = sy;
						}
					}
				}
				if (GlModel.pickScreenX >= v && GlModel.pickScreenX <= y && RawModel.pickScreenY >= x && RawModel.pickScreenY <= z) {
					if (this.pickable) {
						if (miniMenuPick) {
							Model.pickResults[MiniMenu.pickResultCount++] = key;
						}
						if (roofVisibilityLocPick) {
							API.ReportRoofVisibilityLoc(key, plane);
						}
					} else {
						needsPicking = true;
					}
				}
			}
		}
		for (v = 0; v < this.vertexCount; v++) {
			x = this.vertexX[v];
			y = this.vertexY[v];
			z = this.vertexZ[v];
			@Pc(577) int w;
			if (yaw != 0) {
				w = z * yawsin + x * yawcos >> 16;
				z = z * yawcos - x * yawsin >> 16;
				x = w;
			}
			x += sceneX;
			y += sceneZ;
			z += sceneY;
			w = z * sinCameraYaw + x * cosCameraYaw >> 16;
			z = z * cosCameraYaw - x * sinCameraYaw >> 16;
			x = w;
			w = y * cosCameraPitch - z * sinCameraPitch >> 16;
			z = y * sinCameraPitch + z * cosCameraPitch >> 16;
			vertexDepth[v] = z - b;
			if (z >= 50) {
				vertexScreenX[v] = cx + (x << 9) / z;
				vertexScreenY[v] = cy + (w << 9) / z;
			} else {
				vertexScreenX[v] = -5000;
				project1 = true;
			}
			if (project) {
				projectSceneX[v] = x;
				projectSceneY[v] = w;
				projectSceneZ[v] = z;
			}
		}
		try {
			this.draw(project1, needsPicking, key, b - d, c - d + 2, plane, particleSystem);
		} catch (@Pc(713) Exception ex) {
			ex.printStackTrace();
		}
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(II[[I[[IIIIZ)Lclient!w;")
	public SoftwareModel alignToTerrain(@OriginalArg(0) int type, @OriginalArg(1) int blendFactor, @OriginalArg(2) int[][] tileHeights, @OriginalArg(3) int[][] renderHeights, @OriginalArg(4) int sceneX, @OriginalArg(5) int baseHeight, @OriginalArg(6) int sceneZ, @OriginalArg(7) boolean makeCopy) {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		@Pc(9) int worldMinX = sceneX + this.minX;
		@Pc(14) int worldMaxX = sceneX + this.maxX;
		@Pc(19) int worldMinZ = sceneZ + this.minZ;
		@Pc(24) int worldMaxZ = sceneZ + this.maxZ;
		if ((type == 1 || type == 2 || type == 3 || type == 5) && (worldMinX < 0 || worldMaxX + 128 >> 7 >= tileHeights.length || worldMinZ < 0 || worldMaxZ + 128 >> 7 >= tileHeights[0].length)) {
			return this;
		}
		if (type == 4 || type == 5) {
			if (renderHeights == null) {
				return this;
			}
			if (worldMinX < 0 || worldMaxX + 128 >> 7 >= renderHeights.length || worldMinZ < 0 || worldMaxZ + 128 >> 7 >= renderHeights[0].length) {
				return this;
			}
		} else {
			worldMinX >>= 0x7;
			worldMaxX = worldMaxX + 127 >> 7;
			worldMinZ >>= 0x7;
			worldMaxZ = worldMaxZ + 127 >> 7;
			if (tileHeights[worldMinX][worldMinZ] == baseHeight && tileHeights[worldMaxX][worldMinZ] == baseHeight && tileHeights[worldMinX][worldMaxZ] == baseHeight && tileHeights[worldMaxX][worldMaxZ] == baseHeight) {
				return this;
			}
		}
		@Pc(150) SoftwareModel result;
		if (makeCopy) {
			result = new SoftwareModel();
			result.vertexCount = this.vertexCount;
			result.triangleCount = this.triangleCount;
			result.texturedFaceCount = this.texturedFaceCount;
			result.triangleVertexA = this.triangleVertexA;
			result.triangleVertexB = this.triangleVertexB;
			result.triangleVertexC = this.triangleVertexC;
			result.faceColorA = this.faceColorA;
			result.faceColorB = this.faceColorB;
			result.triangleInfo = this.triangleInfo;
			result.trianglePriorities = this.trianglePriorities;
			result.triangleTextureIndex = this.triangleTextureIndex;
			result.triangleTextures = this.triangleTextures;
			result.triangleColors = this.triangleColors;
			result.triangleAlpha = this.triangleAlpha;
			result.priority = this.priority;
			result.textureFacesP = this.textureFacesP;
			result.textureFacesM = this.textureFacesM;
			result.textureFacesN = this.textureFacesN;
			result.boneVertices = this.boneVertices;
			result.boneTriangles = this.boneTriangles;
			result.vertexSources = this.vertexSources;
			result.triangleSources = this.triangleSources;
			result.pickable = this.pickable;
			if (type == 3) {
				result.vertexX = ArrayUtils.copyOfNullable(this.vertexX);
				result.vertexY = ArrayUtils.copyOfNullable(this.vertexY);
				result.vertexZ = ArrayUtils.copyOfNullable(this.vertexZ);
			} else {
				result.vertexX = this.vertexX;
				result.vertexY = new int[result.vertexCount];
				result.vertexZ = this.vertexZ;
			}
		} else {
			result = this;
		}
		@Pc(285) int v;
		@Pc(296) int wx;
		@Pc(303) int wz;
		@Pc(307) int fracX;
		@Pc(311) int fracZ;
		@Pc(315) int tileX;
		@Pc(319) int tileZ;
		@Pc(341) int heightA;
		@Pc(367) int heightB;
		@Pc(379) int height;
		if (type == 1) {
			for (v = 0; v < result.vertexCount; v++) {
				wx = this.vertexX[v] + sceneX;
				wz = this.vertexZ[v] + sceneZ;
				fracX = wx & 0x7F;
				fracZ = wz & 0x7F;
				tileX = wx >> 7;
				tileZ = wz >> 7;
				heightA = tileHeights[tileX][tileZ] * (128 - fracX) + tileHeights[tileX + 1][tileZ] * fracX >> 7;
				heightB = tileHeights[tileX][tileZ + 1] * (128 - fracX) + tileHeights[tileX + 1][tileZ + 1] * fracX >> 7;
				height = heightA * (128 - fracZ) + heightB * fracZ >> 7;
				result.vertexY[v] = this.vertexY[v] + height - baseHeight;
			}
		} else {
			@Pc(506) int interpHeight;
			if (type == 2) {
				for (v = 0; v < result.vertexCount; v++) {
					wx = (this.vertexY[v] << 16) / this.minY;
					if (wx < blendFactor) {
						wz = this.vertexX[v] + sceneX;
						fracX = this.vertexZ[v] + sceneZ;
						fracZ = wz & 0x7F;
						tileX = fracX & 0x7F;
						tileZ = wz >> 7;
						heightA = fracX >> 7;
						heightB = tileHeights[tileZ][heightA] * (128 - fracZ) + tileHeights[tileZ + 1][heightA] * fracZ >> 7;
						height = tileHeights[tileZ][heightA + 1] * (128 - fracZ) + tileHeights[tileZ + 1][heightA + 1] * fracZ >> 7;
						interpHeight = heightB * (128 - tileX) + height * tileX >> 7;
						result.vertexY[v] = this.vertexY[v] + (interpHeight - baseHeight) * (blendFactor - wx) / blendFactor;
					} else {
						result.vertexY[v] = this.vertexY[v];
					}
				}
			} else if (type == 3) {
				v = (blendFactor & 0xFF) * 4;
				wx = (blendFactor >> 8 & 0xFF) * 4;
				result.alignToTerrain(tileHeights, sceneX, baseHeight, sceneZ, v, wx);
			} else if (type == 4) {
				v = this.maxY - this.minY;
				for (wx = 0; wx < this.vertexCount; wx++) {
					wz = this.vertexX[wx] + sceneX;
					fracX = this.vertexZ[wx] + sceneZ;
					fracZ = wz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = wz >> 7;
					heightA = fracX >> 7;
					heightB = renderHeights[tileZ][heightA] * (128 - fracZ) + renderHeights[tileZ + 1][heightA] * fracZ >> 7;
					height = renderHeights[tileZ][heightA + 1] * (128 - fracZ) + renderHeights[tileZ + 1][heightA + 1] * fracZ >> 7;
					interpHeight = heightB * (128 - tileX) + height * tileX >> 7;
					result.vertexY[wx] = this.vertexY[wx] + interpHeight + v - baseHeight;
				}
			} else if (type == 5) {
				v = this.maxY - this.minY;
				for (wx = 0; wx < this.vertexCount; wx++) {
					wz = this.vertexX[wx] + sceneX;
					fracX = this.vertexZ[wx] + sceneZ;
					fracZ = wz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = wz >> 7;
					heightA = fracX >> 7;
					heightB = tileHeights[tileZ][heightA] * (128 - fracZ) + tileHeights[tileZ + 1][heightA] * fracZ >> 7;
					height = tileHeights[tileZ][heightA + 1] * (128 - fracZ) + tileHeights[tileZ + 1][heightA + 1] * fracZ >> 7;
					interpHeight = heightB * (128 - tileX) + height * tileX >> 7;
					heightB = renderHeights[tileZ][heightA] * (128 - fracZ) + renderHeights[tileZ + 1][heightA] * fracZ >> 7;
					height = renderHeights[tileZ][heightA + 1] * (128 - fracZ) + renderHeights[tileZ + 1][heightA + 1] * fracZ >> 7;
					@Pc(849) int renderHeight = heightB * (128 - tileX) + height * tileX >> 7;
					@Pc(853) int heightDiff = interpHeight - renderHeight;
					result.vertexY[wx] = ((this.vertexY[wx] << 8) / v * heightDiff >> 8) - (baseHeight - interpHeight);
				}
			}
		}
		result.boundsValid = false;
		return result;
	}

	@OriginalMember(owner = "client!w", name = "i", descriptor = "()V")
	@Override
	public void rotateCounterClockwise() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			@Pc(10) int temp = this.vertexX[i];
			this.vertexX[i] = this.vertexZ[i];
			this.vertexZ[i] = -temp;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(IIIIIIIJ)V")
	@Override
	public void setCamera(@OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int pitch, @OriginalArg(4) int offsetX, @OriginalArg(5) int offsetY, @OriginalArg(6) int offsetZ, @OriginalArg(7) long key) {
		try {
			if (!this.boundsValid) {
				this.calculateBounds();
			}
			@Pc(6) int cx = Rasteriser.centerX;
			@Pc(8) int cy = Rasteriser.centerY;
			@Pc(12) int sinZero = MathUtils.sin[0];
			@Pc(16) int cosZero = MathUtils.cos[0];
			@Pc(20) int sinYaw = MathUtils.sin[yaw];
			@Pc(24) int cosYaw = MathUtils.cos[yaw];
			@Pc(28) int sinRoll = MathUtils.sin[roll];
			@Pc(32) int cosRoll = MathUtils.cos[roll];
			@Pc(36) int sinPitch = MathUtils.sin[pitch];
			@Pc(40) int cosPitch = MathUtils.cos[pitch];
			@Pc(50) int cameraDist = offsetY * sinPitch + offsetZ * cosPitch >> 16;
			for (@Pc(52) int v = 0; v < this.vertexCount; v++) {
				@Pc(61) int x = this.vertexX[v];
				@Pc(66) int y = this.vertexY[v];
				@Pc(71) int z = this.vertexZ[v];
				@Pc(83) int temp;
				if (roll != 0) {
					temp = y * sinRoll + x * cosRoll >> 16;
					y = y * cosRoll - x * sinRoll >> 16;
					x = temp;
				}
				if (yaw != 0) {
					temp = z * sinYaw + x * cosYaw >> 16;
					z = z * cosYaw - x * sinYaw >> 16;
					x = temp;
				}
				x += offsetX;
				y += offsetY;
				z += offsetZ;
				temp = y * cosPitch - z * sinPitch >> 16;
				z = y * sinPitch + z * cosPitch >> 16;
				vertexDepth[v] = z - cameraDist;
				vertexScreenX[v] = cx + (x << 9) / z;
				vertexScreenY[v] = cy + (temp << 9) / z;
				if (this.texturedFaceCount > 0) {
					projectSceneX[v] = x;
					projectSceneY[v] = temp;
					projectSceneZ[v] = z;
				}
			}
			this.draw(false, key >= 0L || API.IsRoofVisibilityLocPickable(key), key, this.lengthXYZ, this.lengthXYZ << 1, Player.plane, null);
		} catch (@Pc(240) RuntimeException ex) {
		}
	}

	@OriginalMember(owner = "client!w", name = "g", descriptor = "(I)V")
	private void drawProjectedTriangle(@OriginalArg(0) int index) {
		@Pc(1) int cx = Rasteriser.centerX;
		@Pc(3) int cy = Rasteriser.centerY;
		@Pc(5) int clipCount = 0;
		@Pc(10) int vertA = this.triangleVertexA[index];
		@Pc(15) int vertB = this.triangleVertexB[index];
		@Pc(20) int vertC = this.triangleVertexC[index];
		@Pc(24) int depthA = projectSceneZ[vertA];
		@Pc(28) int depthB = projectSceneZ[vertB];
		@Pc(32) int depthC = projectSceneZ[vertC];
		if (this.triangleAlpha == null) {
			Rasteriser.alpha = 0;
		} else {
			Rasteriser.alpha = this.triangleAlpha[index] & 0xFF;
		}
		@Pc(75) int xA;
		@Pc(79) int xB;
		@Pc(86) int xC;
		@Pc(99) int yA;
		if (depthA >= 50) {
			clippedScreenX[0] = vertexScreenX[vertA];
			clippedScreenY[0] = vertexScreenY[vertA];
			clipCount++;
			clippedLightness[0] = this.faceColorA[index] & 0xFFFF;
		} else {
			xA = projectSceneX[vertA];
			xB = projectSceneY[vertA];
			xC = this.faceColorA[index] & 0xFFFF;
			if (depthC >= 50) {
				yA = (50 - depthA) * MathUtils.reciprical16[depthC - depthA];
				clippedScreenX[0] = cx + (xA + ((projectSceneX[vertC] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[0] = cy + (xB + ((projectSceneY[vertC] - xB) * yA >> 16) << 9) / 50;
				clipCount++;
				clippedLightness[0] = xC + (((this.triangleInfo[index] & 0xFFFF) - xC) * yA >> 16);
			}
			if (depthB >= 50) {
				yA = (50 - depthA) * MathUtils.reciprical16[depthB - depthA];
				clippedScreenX[clipCount] = cx + (xA + ((projectSceneX[vertB] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[clipCount] = cy + (xB + ((projectSceneY[vertB] - xB) * yA >> 16) << 9) / 50;
				clippedLightness[clipCount++] = xC + (((this.faceColorB[index] & 0xFFFF) - xC) * yA >> 16);
			}
		}
		if (depthB >= 50) {
			clippedScreenX[clipCount] = vertexScreenX[vertB];
			clippedScreenY[clipCount] = vertexScreenY[vertB];
			clippedLightness[clipCount++] = this.faceColorB[index] & 0xFFFF;
		} else {
			xA = projectSceneX[vertB];
			xB = projectSceneY[vertB];
			xC = this.faceColorB[index] & 0xFFFF;
			if (depthA >= 50) {
				yA = (50 - depthB) * MathUtils.reciprical16[depthA - depthB];
				clippedScreenX[clipCount] = cx + (xA + ((projectSceneX[vertA] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[clipCount] = cy + (xB + ((projectSceneY[vertA] - xB) * yA >> 16) << 9) / 50;
				clippedLightness[clipCount++] = xC + (((this.faceColorA[index] & 0xFFFF) - xC) * yA >> 16);
			}
			if (depthC >= 50) {
				yA = (50 - depthB) * MathUtils.reciprical16[depthC - depthB];
				clippedScreenX[clipCount] = cx + (xA + ((projectSceneX[vertC] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[clipCount] = cy + (xB + ((projectSceneY[vertC] - xB) * yA >> 16) << 9) / 50;
				clippedLightness[clipCount++] = xC + (((this.triangleInfo[index] & 0xFFFF) - xC) * yA >> 16);
			}
		}
		if (depthC >= 50) {
			clippedScreenX[clipCount] = vertexScreenX[vertC];
			clippedScreenY[clipCount] = vertexScreenY[vertC];
			clippedLightness[clipCount++] = this.triangleInfo[index] & 0xFFFF;
		} else {
			xA = projectSceneX[vertC];
			xB = projectSceneY[vertC];
			xC = this.triangleInfo[index] & 0xFFFF;
			if (depthB >= 50) {
				yA = (50 - depthC) * MathUtils.reciprical16[depthB - depthC];
				clippedScreenX[clipCount] = cx + (xA + ((projectSceneX[vertB] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[clipCount] = cy + (xB + ((projectSceneY[vertB] - xB) * yA >> 16) << 9) / 50;
				clippedLightness[clipCount++] = xC + (((this.faceColorB[index] & 0xFFFF) - xC) * yA >> 16);
			}
			if (depthA >= 50) {
				yA = (50 - depthC) * MathUtils.reciprical16[depthA - depthC];
				clippedScreenX[clipCount] = cx + (xA + ((projectSceneX[vertA] - xA) * yA >> 16) << 9) / 50;
				clippedScreenY[clipCount] = cy + (xB + ((projectSceneY[vertA] - xB) * yA >> 16) << 9) / 50;
				clippedLightness[clipCount++] = xC + (((this.faceColorA[index] & 0xFFFF) - xC) * yA >> 16);
			}
		}
		xA = clippedScreenX[0];
		xB = clippedScreenX[1];
		xC = clippedScreenX[2];
		yA = clippedScreenY[0];
		@Pc(614) int yB = clippedScreenY[1];
		@Pc(618) int yC = clippedScreenY[2];
		Rasteriser.testX = false;
		@Pc(709) int texVertA;
		@Pc(714) int texVertB;
		@Pc(719) int texVertC;
		@Pc(704) int texIdx;
		if (clipCount == 3) {
			if (xA < 0 || xB < 0 || xC < 0 || xA > Rasteriser.width || xB > Rasteriser.width || xC > Rasteriser.width) {
				Rasteriser.testX = true;
			}
			if (this.triangleTextures != null && this.triangleTextures[index] != -1) {
				if (this.triangleTextureIndex == null || this.triangleTextureIndex[index] == -1) {
					texVertA = vertA;
					texVertB = vertB;
					texVertC = vertC;
				} else {
					texIdx = this.triangleTextureIndex[index] & 0xFF;
					texVertA = this.textureFacesP[texIdx];
					texVertB = this.textureFacesM[texIdx];
					texVertC = this.textureFacesN[texIdx];
				}
				if (this.triangleInfo[index] == -1) {
					Rasteriser.fillTexturedAlphaTriangle(yA, yB, yC, xA, xB, xC, this.faceColorA[index], this.faceColorA[index], this.faceColorA[index], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], this.triangleTextures[index]);
				} else {
					Rasteriser.fillTexturedAlphaTriangle(yA, yB, yC, xA, xB, xC, clippedLightness[0], clippedLightness[1], clippedLightness[2], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], this.triangleTextures[index]);
				}
			} else if (this.triangleInfo[index] == -1) {
				Rasteriser.fillTriangle(yA, yB, yC, xA, xB, xC, Rasteriser.palette[this.faceColorA[index] & 0xFFFF]);
			} else {
				Rasteriser.fillGouraudTriangle(yA, yB, yC, xA, xB, xC, clippedLightness[0], clippedLightness[1], clippedLightness[2]);
			}
		}
		if (clipCount != 4) {
			return;
		}
		if (xA < 0 || xB < 0 || xC < 0 || xA > Rasteriser.width || xB > Rasteriser.width || xC > Rasteriser.width || clippedScreenX[3] < 0 || clippedScreenX[3] > Rasteriser.width) {
			Rasteriser.testX = true;
		}
		if (this.triangleTextures != null && this.triangleTextures[index] != -1) {
			if (this.triangleTextureIndex == null || this.triangleTextureIndex[index] == -1) {
				texVertA = vertA;
				texVertB = vertB;
				texVertC = vertC;
			} else {
				texIdx = this.triangleTextureIndex[index] & 0xFF;
				texVertA = this.textureFacesP[texIdx];
				texVertB = this.textureFacesM[texIdx];
				texVertC = this.textureFacesN[texIdx];
			}
			@Pc(984) short texture = this.triangleTextures[index];
			if (this.triangleInfo[index] == -1) {
				Rasteriser.fillTexturedAlphaTriangle(yA, yB, yC, xA, xB, xC, this.faceColorA[index], this.faceColorA[index], this.faceColorA[index], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], texture);
				Rasteriser.fillTexturedAlphaTriangle(yA, yC, clippedScreenY[3], xA, xC, clippedScreenX[3], this.faceColorA[index], this.faceColorA[index], this.faceColorA[index], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], texture);
			} else {
				Rasteriser.fillTexturedAlphaTriangle(yA, yB, yC, xA, xB, xC, clippedLightness[0], clippedLightness[1], clippedLightness[2], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], texture);
				Rasteriser.fillTexturedAlphaTriangle(yA, yC, clippedScreenY[3], xA, xC, clippedScreenX[3], clippedLightness[0], clippedLightness[2], clippedLightness[3], projectSceneX[texVertA], projectSceneX[texVertB], projectSceneX[texVertC], projectSceneY[texVertA], projectSceneY[texVertB], projectSceneY[texVertC], projectSceneZ[texVertA], projectSceneZ[texVertB], projectSceneZ[texVertC], texture);
			}
		} else if (this.triangleInfo[index] == -1) {
			texVertA = Rasteriser.palette[this.faceColorA[index] & 0xFFFF];
			Rasteriser.fillTriangle(yA, yB, yC, xA, xB, xC, texVertA);
			Rasteriser.fillTriangle(yA, yC, clippedScreenY[3], xA, xC, clippedScreenX[3], texVertA);
		} else {
			Rasteriser.fillGouraudTriangle(yA, yB, yC, xA, xB, xC, clippedLightness[0], clippedLightness[1], clippedLightness[2]);
			Rasteriser.fillGouraudTriangle(yA, yC, clippedScreenY[3], xA, xC, clippedScreenX[3], clippedLightness[0], clippedLightness[2], clippedLightness[3]);
		}
	}

	@OriginalMember(owner = "client!w", name = "c", descriptor = "()I")
	@Override
	public int getMaxZ() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.maxZ;
	}

	@OriginalMember(owner = "client!w", name = "b", descriptor = "(III)V")
	@Override
	public void resize(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ) {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] = this.vertexX[i] * scaleX / 128;
			this.vertexY[i] = this.vertexY[i] * scaleY / 128;
			this.vertexZ[i] = this.vertexZ[i] * scaleZ / 128;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public Model copyForAnimation(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		if (!shareAlpha && animCopyAlpha.length < this.triangleCount) {
			animCopyAlpha = new byte[this.triangleCount + 100];
		}
		if (!shareColors && animCopyColors.length < this.triangleCount) {
			animCopyFaceColorA = new int[this.triangleCount + 100];
			animCopyFaceColorB = new int[this.triangleCount + 100];
			animCopyFaceColorC = new int[this.triangleCount + 100];
			animCopyColors = new short[this.triangleCount + 100];
		}
		return this.copy(shareAlpha, shareColors, animCopyTarget, animCopyAlpha, animCopyColors, animCopyFaceColorA, animCopyFaceColorB, animCopyFaceColorC);
	}

	@OriginalMember(owner = "client!w", name = "j", descriptor = "()I")
	@Override
	public int getLengthXZ() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.lengthXZ;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(Lclient!ak;)Lclient!ak;")
	public Model mergeWith(@OriginalArg(0) Model source) {
		return new SoftwareModel(new SoftwareModel[]{this, (SoftwareModel) source}, 2);
	}

	@OriginalMember(owner = "client!w", name = "g", descriptor = "()I")
	@Override
	public int getMaxX() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.maxX;
	}

	@OriginalMember(owner = "client!w", name = "f", descriptor = "()V")
	@Override
	protected void resetAfterAnimation() {
		if (this.colorsModified) {
			this.recalculateTriangleColors();
			this.colorsModified = false;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "b", descriptor = "(I)V")
	@Override
	public void rotateY(@OriginalArg(0) int angle) {
		@Pc(3) int sinAngle = MathUtils.sin[angle];
		@Pc(7) int cosAngle = MathUtils.cos[angle];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int newX = this.vertexZ[i] * sinAngle + this.vertexX[i] * cosAngle >> 16;
			this.vertexZ[i] = this.vertexZ[i] * cosAngle - this.vertexX[i] * sinAngle >> 16;
			this.vertexX[i] = newX;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(I[IIIIZ)V")
	@Override
	protected void transformBone(@OriginalArg(0) int transformType, @OriginalArg(1) int[] labels, @OriginalArg(2) int transformX, @OriginalArg(3) int transformY, @OriginalArg(4) int transformZ, @OriginalArg(5) boolean delayed) {
		@Pc(2) int labelCount = labels.length;
		@Pc(6) int count;
		@Pc(14) int i;
		@Pc(33) int vertIdx;
		@Pc(41) int sinAngle;
		if (transformType == 0) {
			count = 0;
			originX = 0;
			originY = 0;
			originZ = 0;
			for (i = 0; i < labelCount; i++) {
				@Pc(21) int label = labels[i];
				if (label < this.boneVertices.length) {
					@Pc(31) int[] vertices = this.boneVertices[label];
					for (vertIdx = 0; vertIdx < vertices.length; vertIdx++) {
						sinAngle = vertices[vertIdx];
						originX += this.vertexX[sinAngle];
						originY += this.vertexY[sinAngle];
						originZ += this.vertexZ[sinAngle];
						count++;
					}
				}
			}
			if (count > 0) {
				originX = originX / count + transformX;
				originY = originY / count + transformY;
				originZ = originZ / count + transformZ;
			} else {
				originX = transformX;
				originY = transformY;
				originZ = transformZ;
			}
			return;
		}
		@Pc(117) int[] boneVerts;
		@Pc(119) int k;
		if (transformType == 1) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					boneVerts = this.boneVertices[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						this.vertexX[vertIdx] += transformX;
						this.vertexY[vertIdx] += transformY;
						this.vertexZ[vertIdx] += transformZ;
					}
				}
			}
			return;
		}
		@Pc(222) int cosAngle;
		@Pc(240) int temp;
		if (transformType == 2) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					boneVerts = this.boneVertices[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						this.vertexX[vertIdx] -= originX;
						this.vertexY[vertIdx] -= originY;
						this.vertexZ[vertIdx] -= originZ;
						if (transformZ != 0) {
							sinAngle = MathUtils.sin[transformZ];
							cosAngle = MathUtils.cos[transformZ];
							temp = this.vertexY[vertIdx] * sinAngle + this.vertexX[vertIdx] * cosAngle + 32767 >> 16;
							this.vertexY[vertIdx] = this.vertexY[vertIdx] * cosAngle + 32767 - this.vertexX[vertIdx] * sinAngle >> 16;
							this.vertexX[vertIdx] = temp;
						}
						if (transformX != 0) {
							sinAngle = MathUtils.sin[transformX];
							cosAngle = MathUtils.cos[transformX];
							temp = this.vertexY[vertIdx] * cosAngle + 32767 - this.vertexZ[vertIdx] * sinAngle >> 16;
							this.vertexZ[vertIdx] = this.vertexY[vertIdx] * sinAngle + this.vertexZ[vertIdx] * cosAngle + 32767 >> 16;
							this.vertexY[vertIdx] = temp;
						}
						if (transformY != 0) {
							sinAngle = MathUtils.sin[transformY];
							cosAngle = MathUtils.cos[transformY];
							temp = this.vertexZ[vertIdx] * sinAngle + this.vertexX[vertIdx] * cosAngle + 32767 >> 16;
							this.vertexZ[vertIdx] = this.vertexZ[vertIdx] * cosAngle + 32767 - this.vertexX[vertIdx] * sinAngle >> 16;
							this.vertexX[vertIdx] = temp;
						}
						this.vertexX[vertIdx] += originX;
						this.vertexY[vertIdx] += originY;
						this.vertexZ[vertIdx] += originZ;
					}
				}
			}
		} else if (transformType == 3) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					boneVerts = this.boneVertices[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						this.vertexX[vertIdx] -= originX;
						this.vertexY[vertIdx] -= originY;
						this.vertexZ[vertIdx] -= originZ;
						this.vertexX[vertIdx] = this.vertexX[vertIdx] * transformX / 128;
						this.vertexY[vertIdx] = this.vertexY[vertIdx] * transformY / 128;
						this.vertexZ[vertIdx] = this.vertexZ[vertIdx] * transformZ / 128;
						this.vertexX[vertIdx] += originX;
						this.vertexY[vertIdx] += originY;
						this.vertexZ[vertIdx] += originZ;
					}
				}
			}
		} else if (transformType == 5) {
			if (this.boneTriangles != null && this.triangleAlpha != null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneTriangles.length) {
						boneVerts = this.boneTriangles[i];
						for (k = 0; k < boneVerts.length; k++) {
							vertIdx = boneVerts[k];
							sinAngle = (this.triangleAlpha[vertIdx] & 0xFF) + transformX * 8;
							if (sinAngle < 0) {
								sinAngle = 0;
							} else if (sinAngle > 255) {
								sinAngle = 255;
							}
							this.triangleAlpha[vertIdx] = (byte) sinAngle;
						}
					}
				}
			}
		} else if (transformType == 7 && this.boneTriangles != null) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneTriangles.length) {
					boneVerts = this.boneTriangles[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						sinAngle = this.triangleColors[vertIdx] & 0xFFFF;
						cosAngle = sinAngle >> 10 & 0x3F;
						temp = sinAngle >> 7 & 0x7;
						@Pc(652) int lightness = sinAngle & 0x7F;
						@Pc(658) int newHue = cosAngle + transformX & 0x3F;
						temp += transformY;
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
						this.triangleColors[vertIdx] = (short) (newHue << 10 | temp << 7 | lightness);
					}
					this.colorsModified = true;
				}
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(IIIIIIII)Z")
	private boolean pointWithinTriangle(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int yA, @OriginalArg(3) int yB, @OriginalArg(4) int yC, @OriginalArg(5) int xA, @OriginalArg(6) int xB, @OriginalArg(7) int xC) {
		if (y < yA && y < yB && y < yC) {
			return false;
		} else if (y > yA && y > yB && y > yC) {
			return false;
		} else if (x < xA && x < xB && x < xC) {
			return false;
		} else {
			return x <= xA || x <= xB || x <= xC;
		}
	}

	@OriginalMember(owner = "client!w", name = "c", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public Model copyForEntity(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		if (!shareAlpha && entityCopyAlpha.length < this.triangleCount) {
			entityCopyAlpha = new byte[this.triangleCount + 100];
		}
		if (!shareColors && entityCopyColors.length < this.triangleCount) {
			entityCopyFaceColorA = new int[this.triangleCount + 100];
			entityCopyFaceColorB = new int[this.triangleCount + 100];
			entityCopyFaceColorC = new int[this.triangleCount + 100];
			entityCopyColors = new short[this.triangleCount + 100];
		}
		return this.copy(shareAlpha, shareColors, entityCopyTarget, entityCopyAlpha, entityCopyColors, entityCopyFaceColorA, entityCopyFaceColorB, entityCopyFaceColorC);
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(ZZJIILclient!ga;)V")
	private void draw(@OriginalArg(0) boolean projected, @OriginalArg(1) boolean picking, @OriginalArg(2) long key, @OriginalArg(3) int depthOffset, @OriginalArg(4) int maxDepth, int roofHidePlane, @OriginalArg(5) ParticleSystem particleSystem) {
		if (maxDepth >= 1600) {
			return;
		}
		@Pc(5) int sortCount = 0;
		@Pc(7) int deferredCount = 0;
		@Pc(11) int i;
		if (!useDepthSort) {
			for (i = 0; i < 1600; i++) {
				bucketCounts[i] = 0;
			}
			for (i = 0; i < 32; i++) {
				overflowBucketCounts[i] = 0;
			}
			overflowBucketCount = 0;
		}
		@Pc(51) int vertA;
		@Pc(56) int vertB;
		@Pc(61) int pri;
		@Pc(65) int screenXA;
		@Pc(69) int screenXB;
		@Pc(88) int depth;
		@Pc(92) int p;
		@Pc(96) int n;
		@Pc(104) int m;
		for (i = 0; i < this.triangleCount; i++) {
			if (this.triangleInfo[i] != -2) {
				vertA = this.triangleVertexA[i];
				vertB = this.triangleVertexB[i];
				pri = this.triangleVertexC[i];
				screenXA = vertexScreenX[vertA];
				screenXB = vertexScreenX[vertB];
				@Pc(73) int screenXC = vertexScreenX[pri];
				if (projected && (screenXA == -5000 || screenXB == -5000 || screenXC == -5000)) {
					depth = projectSceneX[vertA];
					p = projectSceneX[vertB];
					n = projectSceneX[pri];
					@Pc(100) int sceneYA = projectSceneY[vertA];
					m = projectSceneY[vertB];
					@Pc(108) int sceneYC = projectSceneY[pri];
					@Pc(112) int sceneZA = projectSceneZ[vertA];
					@Pc(116) int sceneZB = projectSceneZ[vertB];
					@Pc(120) int sceneZC = projectSceneZ[pri];
					depth -= p;
					@Pc(128) int dx2 = n - p;
					@Pc(132) int dy1 = sceneYA - m;
					@Pc(136) int dy2 = sceneYC - m;
					@Pc(140) int dz1 = sceneZA - sceneZB;
					@Pc(144) int dz2 = sceneZC - sceneZB;
					@Pc(152) int normalX = dy1 * dz2 - dz1 * dy2;
					@Pc(160) int normalY = dz1 * dx2 - depth * dz2;
					@Pc(168) int normalZ = depth * dy2 - dy1 * dx2;
					if (p * normalX + m * normalY + sceneZB * normalZ > 0) {
						projectTriangle[i] = true;
						if (useDepthSort) {
							triangleDepths[sortCount] = (vertexDepth[vertA] + vertexDepth[vertB] + vertexDepth[pri]) / 3;
							depthTriangles[sortCount++] = i;
						} else {
							@Pc(224) int bucketIdx = (vertexDepth[vertA] + vertexDepth[vertB] + vertexDepth[pri]) / 3 + depthOffset;
							if (bucketCounts[bucketIdx] < 64) {
								bucketTriangles[bucketIdx][bucketCounts[bucketIdx]++] = i;
							} else {
								@Pc(247) int overflow = bucketCounts[bucketIdx];
								if (overflow == 64) {
									if (overflowBucketCount == 512) {
										continue;
									}
									bucketCounts[bucketIdx] = overflow = overflowBucketCount++ + 65;
								}
								overflow -= 65;
								overflowBucketTriangles[overflow][overflowBucketCounts[overflow]++] = i;
							}
						}
					}
				} else {
					if (picking && this.pointWithinTriangle(GlModel.pickScreenX + Rasteriser.centerX, RawModel.pickScreenY + Rasteriser.centerY, vertexScreenY[vertA], vertexScreenY[vertB], vertexScreenY[pri], screenXA, screenXB, screenXC)) {
						if (key >= 0L) {
							Model.pickResults[MiniMenu.pickResultCount++] = key;
						}
						if (API.IsRoofVisibilityLocPickable(key)) {
							API.ReportRoofVisibilityLoc(key, roofHidePlane);
						}
						picking = false;
					}
					if ((screenXA - screenXB) * (vertexScreenY[pri] - vertexScreenY[vertB]) - (vertexScreenY[vertA] - vertexScreenY[vertB]) * (screenXC - screenXB) > 0) {
						projectTriangle[i] = false;
						testTriangleX[i] = screenXA < 0 || screenXB < 0 || screenXC < 0 || screenXA > Rasteriser.width || screenXB > Rasteriser.width || screenXC > Rasteriser.width;
						if (useDepthSort) {
							triangleDepths[sortCount] = (vertexDepth[vertA] + vertexDepth[vertB] + vertexDepth[pri]) / 3;
							depthTriangles[sortCount++] = i;
						} else {
							depth = (vertexDepth[vertA] + vertexDepth[vertB] + vertexDepth[pri]) / 3 + depthOffset;
							if (bucketCounts[depth] < 64) {
								bucketTriangles[depth][bucketCounts[depth]++] = i;
							} else {
								p = bucketCounts[depth];
								if (p == 64) {
									if (overflowBucketCount == 512) {
										continue;
									}
									bucketCounts[depth] = p = overflowBucketCount++ + 65;
								}
								p -= 65;
								overflowBucketTriangles[p][overflowBucketCounts[p]++] = i;
							}
						}
					}
				}
			}
		}
		if (useDepthSort) {
			ArrayUtils.sort(0, sortCount - 1, triangleDepths, depthTriangles);
			if (this.trianglePriorities == null) {
				for (i = 0; i < sortCount; i++) {
					this.drawTriangle(depthTriangles[i]);
				}
				return;
			}
			for (i = 0; i < 12; i++) {
				priorityTriangleCounts[i] = 0;
				lowTrianglePriority[i] = 0;
			}
			for (i = 0; i < sortCount; i++) {
				vertA = depthTriangles[i];
				vertB = triangleDepths[i];
				@Pc(523) byte t = this.trianglePriorities[vertA];
				screenXA = priorityTriangleCounts[t]++;
				priorityTriangles[t][screenXA] = vertA;
				if (t < 10) {
					lowTrianglePriority[t] += vertB;
				} else if (t == 10) {
					normalTrianglePriority[screenXA] = vertB;
				} else {
					highTrianglePriority[screenXA] = vertB;
				}
			}
		} else {
			@Pc(590) int[] triBuffer;
			if (this.trianglePriorities == null) {
				for (i = maxDepth - 1; i >= 0; i--) {
					vertA = bucketCounts[i];
					if (vertA > 0) {
						vertB = vertA > 64 ? 64 : vertA;
						triBuffer = bucketTriangles[i];
						for (screenXA = 0; screenXA < vertB; screenXA++) {
							screenXB = triBuffer[screenXA];
							if (screenXB < 65536) {
								this.drawTriangle(triBuffer[screenXA]);
							}
						}
					}
					if (vertA > 64) {
						vertB = bucketCounts[i] - 64 - 1;
						triBuffer = overflowBucketTriangles[vertB];
						for (screenXA = 0; screenXA < overflowBucketCounts[vertB]; screenXA++) {
							screenXB = triBuffer[screenXA];
							if (screenXB < 65536) {
								this.drawTriangle(triBuffer[screenXA]);
							}
						}
					}
				}
				return;
			}
			for (i = 0; i < 12; i++) {
				priorityTriangleCounts[i] = 0;
				lowTrianglePriority[i] = 0;
			}
			for (i = maxDepth - 1; i >= 0; i--) {
				vertA = bucketCounts[i];
				@Pc(704) byte triPriority;
				if (vertA > 0) {
					if (vertA > 64) {
						vertB = 64;
					} else {
						vertB = vertA;
					}
					triBuffer = bucketTriangles[i];
					for (screenXA = 0; screenXA < vertB; screenXA++) {
						screenXB = triBuffer[screenXA];
						if (screenXB < 65536) {
							triPriority = this.trianglePriorities[screenXB];
							depth = priorityTriangleCounts[triPriority]++;
							priorityTriangles[triPriority][depth] = screenXB;
							if (triPriority < 10) {
								lowTrianglePriority[triPriority] += i;
							} else if (triPriority == 10) {
								normalTrianglePriority[depth] = i;
							} else {
								highTrianglePriority[depth] = i;
							}
						} else {
							deferredTriangles[deferredCount++] = (screenXB >> 16) - 1;
						}
					}
				}
				if (vertA > 64) {
					vertB = bucketCounts[i] - 64 - 1;
					triBuffer = overflowBucketTriangles[vertB];
					for (screenXA = 0; screenXA < overflowBucketCounts[vertB]; screenXA++) {
						screenXB = triBuffer[screenXA];
						if (screenXB < 65536) {
							triPriority = this.trianglePriorities[screenXB];
							depth = priorityTriangleCounts[triPriority]++;
							priorityTriangles[triPriority][depth] = screenXB;
							if (triPriority < 10) {
								lowTrianglePriority[triPriority] += i;
							} else if (triPriority == 10) {
								normalTrianglePriority[depth] = i;
							} else {
								highTrianglePriority[depth] = i;
							}
						} else {
							deferredTriangles[deferredCount++] = (screenXB >> 16) - 1;
						}
					}
				}
			}
		}
		int minPriority = 0;
		if (priorityTriangleCounts[1] > 0 || priorityTriangleCounts[2] > 0) {
			minPriority = (lowTrianglePriority[1] + lowTrianglePriority[2]) / (priorityTriangleCounts[1] + priorityTriangleCounts[2]);
		}
		int halfPriority = 0;
		if (priorityTriangleCounts[3] > 0 || priorityTriangleCounts[4] > 0) {
			halfPriority = (lowTrianglePriority[3] + lowTrianglePriority[4]) / (priorityTriangleCounts[3] + priorityTriangleCounts[4]);
		}
		int maxPriority = 0;
		if (priorityTriangleCounts[6] > 0 || priorityTriangleCounts[8] > 0) {
			maxPriority = (lowTrianglePriority[6] + lowTrianglePriority[8]) / (priorityTriangleCounts[6] + priorityTriangleCounts[8]);
		}
		int t = 0;
		int priorityTriangleCount = priorityTriangleCounts[10];
		@Pc(928) int[] triangles = priorityTriangles[10];
		@Pc(930) int[] priorities = normalTrianglePriority;
		if (priorityTriangleCount == 0) {
			t = 0;
			priorityTriangleCount = priorityTriangleCounts[11];
			triangles = priorityTriangles[11];
			priorities = highTrianglePriority;
		}
		if (priorityTriangleCount > 0) {
			pri = priorities[0];
		} else {
			pri = -1000;
		}
		for (p = 0; p < 10; p++) {
			while (p == 0 && pri > minPriority) {
				this.drawTriangle(triangles[t++]);
				if (t == priorityTriangleCount && triangles != priorityTriangles[11]) {
					t = 0;
					priorityTriangleCount = priorityTriangleCounts[11];
					triangles = priorityTriangles[11];
					priorities = highTrianglePriority;
				}
				if (t < priorityTriangleCount) {
					pri = priorities[t];
				} else {
					pri = -1000;
				}
			}
			while (p == 3 && pri > halfPriority) {
				this.drawTriangle(triangles[t++]);
				if (t == priorityTriangleCount && triangles != priorityTriangles[11]) {
					t = 0;
					priorityTriangleCount = priorityTriangleCounts[11];
					triangles = priorityTriangles[11];
					priorities = highTrianglePriority;
				}
				if (t < priorityTriangleCount) {
					pri = priorities[t];
				} else {
					pri = -1000;
				}
			}
			while (p == 5 && pri > maxPriority) {
				this.drawTriangle(triangles[t++]);
				if (t == priorityTriangleCount && triangles != priorityTriangles[11]) {
					t = 0;
					priorityTriangleCount = priorityTriangleCounts[11];
					triangles = priorityTriangles[11];
					priorities = highTrianglePriority;
				}
				if (t < priorityTriangleCount) {
					pri = priorities[t];
				} else {
					pri = -1000;
				}
			}
			n = priorityTriangleCounts[p];
			@Pc(1096) int[] tris = priorityTriangles[p];
			for (m = 0; m < n; m++) {
				this.drawTriangle(tris[m]);
			}
		}
		while (pri != -1000) {
			this.drawTriangle(triangles[t++]);
			if (t == priorityTriangleCount && triangles != priorityTriangles[11]) {
				t = 0;
				triangles = priorityTriangles[11];
				priorityTriangleCount = priorityTriangleCounts[11];
				priorities = highTrianglePriority;
			}
			if (t < priorityTriangleCount) {
				pri = priorities[t];
			} else {
				pri = -1000;
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "l", descriptor = "()V")
	@Override
	public void rotateClockwise() {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			@Pc(10) int temp = this.vertexZ[i];
			this.vertexZ[i] = this.vertexX[i];
			this.vertexX[i] = -temp;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "b", descriptor = "(IIIIIIII)V")
	public void renderOnInterface(@OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int pitch, @OriginalArg(4) int offsetX, @OriginalArg(5) int offsetY, @OriginalArg(6) int offsetZ, @OriginalArg(7) int zoom) {
		try {
			if (!this.boundsValid) {
				this.calculateBounds();
			}
			@Pc(6) int cx = Rasteriser.centerX;
			@Pc(8) int cy = Rasteriser.centerY;
			@Pc(12) int sinZero = MathUtils.sin[0];
			@Pc(16) int cosZero = MathUtils.cos[0];
			@Pc(20) int sinYaw = MathUtils.sin[yaw];
			@Pc(24) int cosYaw = MathUtils.cos[yaw];
			@Pc(28) int sinRoll = MathUtils.sin[roll];
			@Pc(32) int cosRoll = MathUtils.cos[roll];
			@Pc(36) int sinPitch = MathUtils.sin[pitch];
			@Pc(40) int cosPitch = MathUtils.cos[pitch];
			@Pc(50) int cameraDist = offsetY * sinPitch + offsetZ * cosPitch >> 16;
			for (@Pc(52) int v = 0; v < this.vertexCount; v++) {
				@Pc(61) int x = this.vertexX[v];
				@Pc(66) int y = this.vertexY[v];
				@Pc(71) int z = this.vertexZ[v];
				@Pc(83) int temp;
				if (roll != 0) {
					temp = y * sinRoll + x * cosRoll >> 16;
					y = y * cosRoll - x * sinRoll >> 16;
					x = temp;
				}
				if (yaw != 0) {
					temp = z * sinYaw + x * cosYaw >> 16;
					z = z * cosYaw - x * sinYaw >> 16;
					x = temp;
				}
				x += offsetX;
				y += offsetY;
				z += offsetZ;
				temp = y * cosPitch - z * sinPitch >> 16;
				z = y * sinPitch + z * cosPitch >> 16;
				vertexDepth[v] = z - cameraDist;
				vertexScreenX[v] = cx + (x << 9) / zoom;
				vertexScreenY[v] = cy + (temp << 9) / zoom;
				if (this.texturedFaceCount > 0) {
					projectSceneX[v] = x;
					projectSceneY[v] = temp;
					projectSceneZ[v] = z;
				}
			}
			this.draw(false, false, 0L, this.lengthXYZ, this.lengthXYZ << 1, Player.plane, null);
		} catch (@Pc(234) RuntimeException ex) {
		}
	}

	@OriginalMember(owner = "client!w", name = "p", descriptor = "()V")
	private void calculateBounds() {
		@Pc(1) int minX = 32767;
		@Pc(3) int minY = 32767;
		@Pc(5) int minZ = 32767;
		@Pc(7) int maxX = -32768;
		@Pc(9) int maxY = -32768;
		@Pc(11) int maxZ = -32768;
		@Pc(13) int maxRadiusXZ = 0;
		@Pc(15) int maxRadiusXYZ = 0;
		for (@Pc(17) int v = 0; v < this.vertexCount; v++) {
			@Pc(26) int x = this.vertexX[v];
			@Pc(31) int y = this.vertexY[v];
			@Pc(36) int z = this.vertexZ[v];
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
			@Pc(74) int radiusSq = x * x + z * z;
			if (radiusSq > maxRadiusXZ) {
				maxRadiusXZ = radiusSq;
			}
			radiusSq += y * y;
			if (radiusSq > maxRadiusXYZ) {
				maxRadiusXYZ = radiusSq;
			}
		}
		this.minX = (short) minX;
		this.maxX = (short) maxX;
		this.minY = (short) minY;
		this.maxY = (short) maxY;
		this.minZ = (short) minZ;
		this.maxZ = (short) maxZ;
		this.lengthXZ = (short) (Math.sqrt(maxRadiusXZ) + 0.99D);
		this.lengthXYZ = (short) (Math.sqrt(maxRadiusXYZ) + 0.99D);
		this.boundsValid = true;
	}

	@OriginalMember(owner = "client!w", name = "h", descriptor = "()I")
	@Override
	public int getMinX() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minX;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(I[IIIIZI[I)V")
	@Override
	protected void transformMaskedBone(@OriginalArg(0) int transformType, @OriginalArg(1) int[] labels, @OriginalArg(2) int transformX, @OriginalArg(3) int transformY, @OriginalArg(4) int transformZ, @OriginalArg(5) boolean delayed, @OriginalArg(6) int mask, @OriginalArg(7) int[] matrix) {
		@Pc(2) int labelCount = labels.length;
		@Pc(6) int count;
		@Pc(14) int i;
		@Pc(33) int vertIdx;
		@Pc(41) int value;
		@Pc(21) int label;
		if (transformType == 0) {
			count = 0;
			originX = 0;
			originY = 0;
			originZ = 0;
			for (i = 0; i < labelCount; i++) {
				label = labels[i];
				if (label < this.boneVertices.length) {
					@Pc(31) int[] vertices = this.boneVertices[label];
					for (vertIdx = 0; vertIdx < vertices.length; vertIdx++) {
						value = vertices[vertIdx];
						if (this.vertexSources == null || (mask & this.vertexSources[value]) != 0) {
							originX += this.vertexX[value];
							originY += this.vertexY[value];
							originZ += this.vertexZ[value];
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
		@Pc(204) int[] boneVerts;
		@Pc(206) int k;
		if (transformType == 1) {
			if (matrix != null) {
				count = matrix[0] * transformX + matrix[1] * transformY + matrix[2] * transformZ + 16384 >> 15;
				i = matrix[3] * transformX + matrix[4] * transformY + matrix[5] * transformZ + 16384 >> 15;
				label = matrix[6] * transformX + matrix[7] * transformY + matrix[8] * transformZ + 16384 >> 15;
				transformX = count;
				transformY = i;
				transformZ = label;
			}
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneVertices.length) {
					boneVerts = this.boneVertices[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						if (this.vertexSources == null || (mask & this.vertexSources[vertIdx]) != 0) {
							this.vertexX[vertIdx] += transformX;
							this.vertexY[vertIdx] += transformY;
							this.vertexZ[vertIdx] += transformZ;
						}
					}
				}
			}
			return;
		}
		@Pc(307) int cosAngle;
		@Pc(329) int rotA;
		@Pc(351) int rotB;
		@Pc(392) int cosY;
		@Pc(398) int sinY;
		@Pc(404) int cosZ;
		@Pc(410) int sinZ;
		@Pc(418) int sinXcosZ;
		@Pc(426) int sinXsinZ;
		@Pc(579) int py;
		@Pc(604) int pz;
		@Pc(608) int tx;
		@Pc(616) int tz;
		@Pc(621) int ox;
		@Pc(626) int oy;
		@Pc(631) int oz;
		@Pc(753) int[] boneVerts2;
		@Pc(755) int m;
		@Pc(760) int vi;
		@Pc(765) int rx;
		@Pc(767) int ry;
		@Pc(893) int rz;
		if (transformType == 2) {
			if (matrix == null) {
				for (count = 0; count < labelCount; count++) {
					i = labels[count];
					if (i < this.boneVertices.length) {
						boneVerts = this.boneVertices[i];
						for (k = 0; k < boneVerts.length; k++) {
							vertIdx = boneVerts[k];
							if (this.vertexSources == null || (mask & this.vertexSources[vertIdx]) != 0) {
								this.vertexX[vertIdx] -= originX;
								this.vertexY[vertIdx] -= originY;
								this.vertexZ[vertIdx] -= originZ;
								if (transformZ != 0) {
									value = MathUtils.sin[transformZ];
									cosAngle = MathUtils.cos[transformZ];
									rotA = this.vertexY[vertIdx] * value + this.vertexX[vertIdx] * cosAngle + 32767 >> 16;
									this.vertexY[vertIdx] = this.vertexY[vertIdx] * cosAngle + 32767 - this.vertexX[vertIdx] * value >> 16;
									this.vertexX[vertIdx] = rotA;
								}
								if (transformX != 0) {
									value = MathUtils.sin[transformX];
									cosAngle = MathUtils.cos[transformX];
									rotA = this.vertexY[vertIdx] * cosAngle + 32767 - this.vertexZ[vertIdx] * value >> 16;
									this.vertexZ[vertIdx] = this.vertexY[vertIdx] * value + this.vertexZ[vertIdx] * cosAngle + 32767 >> 16;
									this.vertexY[vertIdx] = rotA;
								}
								if (transformY != 0) {
									value = MathUtils.sin[transformY];
									cosAngle = MathUtils.cos[transformY];
									rotA = this.vertexZ[vertIdx] * value + this.vertexX[vertIdx] * cosAngle + 32767 >> 16;
									this.vertexZ[vertIdx] = this.vertexZ[vertIdx] * cosAngle + 32767 - this.vertexX[vertIdx] * value >> 16;
									this.vertexX[vertIdx] = rotA;
								}
								this.vertexX[vertIdx] += originX;
								this.vertexY[vertIdx] += originY;
								this.vertexZ[vertIdx] += originZ;
							}
						}
					}
				}
			} else {
				count = matrix[9];
				i = matrix[10];
				label = matrix[11];
				k = matrix[12];
				vertIdx = matrix[13];
				value = matrix[14];
				if (originValid) {
					cosAngle = matrix[0] * originX + matrix[3] * originY + matrix[6] * originZ + 16384 >> 15;
					rotA = matrix[1] * originX + matrix[4] * originY + matrix[7] * originZ + 16384 >> 15;
					rotB = matrix[2] * originX + matrix[5] * originY + matrix[8] * originZ + 16384 >> 15;
					cosAngle += k;
					rotA += vertIdx;
					rotB += value;
					originX = cosAngle;
					originY = rotA;
					originZ = rotB;
					originValid = false;
				}
				@Pc(374) int[] rotMatrix = new int[9];
				rotA = MathUtils.cos[transformX] >> 1;
				rotB = MathUtils.sin[transformX] >> 1;
				cosY = MathUtils.cos[transformY] >> 1;
				sinY = MathUtils.sin[transformY] >> 1;
				cosZ = MathUtils.cos[transformZ] >> 1;
				sinZ = MathUtils.sin[transformZ] >> 1;
				sinXcosZ = rotB * cosZ + 16384 >> 15;
				sinXsinZ = rotB * sinZ + 16384 >> 15;
				rotMatrix[0] = cosY * cosZ + sinY * sinXsinZ + 16384 >> 15;
				rotMatrix[1] = -cosY * sinZ + sinY * sinXcosZ + 16384 >> 15;
				rotMatrix[2] = sinY * rotA + 16384 >> 15;
				rotMatrix[3] = rotA * sinZ + 16384 >> 15;
				rotMatrix[4] = rotA * cosZ + 16384 >> 15;
				rotMatrix[5] = -rotB;
				rotMatrix[6] = -sinY * cosZ + cosY * sinXsinZ + 16384 >> 15;
				rotMatrix[7] = sinY * sinZ + cosY * sinXcosZ + 16384 >> 15;
				rotMatrix[8] = cosY * rotA + 16384 >> 15;
				@Pc(554) int px = rotMatrix[0] * -originX + rotMatrix[1] * -originY + rotMatrix[2] * -originZ + 16384 >> 15;
				py = rotMatrix[3] * -originX + rotMatrix[4] * -originY + rotMatrix[5] * -originZ + 16384 >> 15;
				pz = rotMatrix[6] * -originX + rotMatrix[7] * -originY + rotMatrix[8] * -originZ + 16384 >> 15;
				tx = px + originX;
				@Pc(612) int ty = py + originY;
				tz = pz + originZ;
				@Pc(619) int[] combinedMatrix = new int[9];
				for (ox = 0; ox < 3; ox++) {
					for (oy = 0; oy < 3; oy++) {
						oz = 0;
						for (@Pc(633) int l = 0; l < 3; l++) {
							oz += rotMatrix[ox * 3 + l] * matrix[oy * 3 + l];
						}
						combinedMatrix[ox * 3 + oy] = oz + 16384 >> 15;
					}
				}
				ox = rotMatrix[0] * k + rotMatrix[1] * vertIdx + rotMatrix[2] * value + 16384 >> 15;
				oy = rotMatrix[3] * k + rotMatrix[4] * vertIdx + rotMatrix[5] * value + 16384 >> 15;
				oz = rotMatrix[6] * k + rotMatrix[7] * vertIdx + rotMatrix[8] * value + 16384 >> 15;
				ox += tx;
				oy += ty;
				oz += tz;
				boneVerts2 = new int[9];
				for (m = 0; m < 3; m++) {
					for (vi = 0; vi < 3; vi++) {
						rx = 0;
						for (ry = 0; ry < 3; ry++) {
							rx += matrix[m * 3 + ry] * combinedMatrix[vi + ry * 3];
						}
						boneVerts2[m * 3 + vi] = rx + 16384 >> 15;
					}
				}
				m = matrix[0] * ox + matrix[1] * oy + matrix[2] * oz + 16384 >> 15;
				vi = matrix[3] * ox + matrix[4] * oy + matrix[5] * oz + 16384 >> 15;
				rx = matrix[6] * ox + matrix[7] * oy + matrix[8] * oz + 16384 >> 15;
				m += count;
				vi += i;
				rx += label;
				for (ry = 0; ry < labelCount; ry++) {
					rz = labels[ry];
					if (rz < this.boneVertices.length) {
						@Pc(903) int[] vertArray = this.boneVertices[rz];
						for (@Pc(905) int n = 0; n < vertArray.length; n++) {
							@Pc(913) int vIdx = vertArray[n];
							if (this.vertexSources == null || (mask & this.vertexSources[vIdx]) != 0) {
								@Pc(955) int newX = boneVerts2[0] * this.vertexX[vIdx] + boneVerts2[1] * this.vertexY[vIdx] + boneVerts2[2] * this.vertexZ[vIdx] + 16384 >> 15;
								@Pc(986) int newY = boneVerts2[3] * this.vertexX[vIdx] + boneVerts2[4] * this.vertexY[vIdx] + boneVerts2[5] * this.vertexZ[vIdx] + 16384 >> 15;
								@Pc(1017) int newZ = boneVerts2[6] * this.vertexX[vIdx] + boneVerts2[7] * this.vertexY[vIdx] + boneVerts2[8] * this.vertexZ[vIdx] + 16384 >> 15;
								@Pc(1021) int finalX = newX + m;
								@Pc(1025) int finalY = newY + vi;
								@Pc(1029) int finalZ = newZ + rx;
								this.vertexX[vIdx] = finalX;
								this.vertexY[vIdx] = finalY;
								this.vertexZ[vIdx] = finalZ;
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
						boneVerts = this.boneVertices[i];
						for (k = 0; k < boneVerts.length; k++) {
							vertIdx = boneVerts[k];
							if (this.vertexSources == null || (mask & this.vertexSources[vertIdx]) != 0) {
								this.vertexX[vertIdx] -= originX;
								this.vertexY[vertIdx] -= originY;
								this.vertexZ[vertIdx] -= originZ;
								this.vertexX[vertIdx] = this.vertexX[vertIdx] * transformX / 128;
								this.vertexY[vertIdx] = this.vertexY[vertIdx] * transformY / 128;
								this.vertexZ[vertIdx] = this.vertexZ[vertIdx] * transformZ / 128;
								this.vertexX[vertIdx] += originX;
								this.vertexY[vertIdx] += originY;
								this.vertexZ[vertIdx] += originZ;
							}
						}
					}
				}
			} else {
				count = matrix[9];
				i = matrix[10];
				label = matrix[11];
				k = matrix[12];
				vertIdx = matrix[13];
				value = matrix[14];
				if (originValid) {
					cosAngle = matrix[0] * originX + matrix[3] * originY + matrix[6] * originZ + 16384 >> 15;
					rotA = matrix[1] * originX + matrix[4] * originY + matrix[7] * originZ + 16384 >> 15;
					rotB = matrix[2] * originX + matrix[5] * originY + matrix[8] * originZ + 16384 >> 15;
					cosAngle += k;
					rotA += vertIdx;
					rotB += value;
					originX = cosAngle;
					originY = rotA;
					originZ = rotB;
					originValid = false;
				}
				cosAngle = transformX << 15 >> 7;
				rotA = transformY << 15 >> 7;
				rotB = transformZ << 15 >> 7;
				cosY = cosAngle * -originX + 16384 >> 15;
				sinY = rotA * -originY + 16384 >> 15;
				cosZ = rotB * -originZ + 16384 >> 15;
				sinZ = cosY + originX;
				sinXcosZ = sinY + originY;
				sinXsinZ = cosZ + originZ;
				@Pc(1481) int[] scaleMatrix = new int[]{cosAngle * matrix[0] + 16384 >> 15, cosAngle * matrix[3] + 16384 >> 15, cosAngle * matrix[6] + 16384 >> 15, rotA * matrix[1] + 16384 >> 15, rotA * matrix[4] + 16384 >> 15, rotA * matrix[7] + 16384 >> 15, rotB * matrix[2] + 16384 >> 15, rotB * matrix[5] + 16384 >> 15, rotB * matrix[8] + 16384 >> 15};
				py = cosAngle * k + 16384 >> 15;
				pz = rotA * vertIdx + 16384 >> 15;
				tx = rotB * value + 16384 >> 15;
				@Pc(1617) int offX = py + sinZ;
				@Pc(1621) int offY = pz + sinXcosZ;
				@Pc(1625) int offZ = tx + sinXsinZ;
				@Pc(1628) int[] resultMatrix = new int[9];
				@Pc(1635) int m2;
				for (tz = 0; tz < 3; tz++) {
					for (m2 = 0; m2 < 3; m2++) {
						ox = 0;
						for (oy = 0; oy < 3; oy++) {
							ox += matrix[tz * 3 + oy] * scaleMatrix[m2 + oy * 3];
						}
						resultMatrix[tz * 3 + m2] = ox + 16384 >> 15;
					}
				}
				tz = matrix[0] * offX + matrix[1] * offY + matrix[2] * offZ + 16384 >> 15;
				m2 = matrix[3] * offX + matrix[4] * offY + matrix[5] * offZ + 16384 >> 15;
				ox = matrix[6] * offX + matrix[7] * offY + matrix[8] * offZ + 16384 >> 15;
				tz += count;
				m2 += i;
				ox += label;
				for (oy = 0; oy < labelCount; oy++) {
					oz = labels[oy];
					if (oz < this.boneVertices.length) {
						boneVerts2 = this.boneVertices[oz];
						for (m = 0; m < boneVerts2.length; m++) {
							vi = boneVerts2[m];
							if (this.vertexSources == null || (mask & this.vertexSources[vi]) != 0) {
								rx = resultMatrix[0] * this.vertexX[vi] + resultMatrix[1] * this.vertexY[vi] + resultMatrix[2] * this.vertexZ[vi] + 16384 >> 15;
								ry = resultMatrix[3] * this.vertexX[vi] + resultMatrix[4] * this.vertexY[vi] + resultMatrix[5] * this.vertexZ[vi] + 16384 >> 15;
								rz = resultMatrix[6] * this.vertexX[vi] + resultMatrix[7] * this.vertexY[vi] + resultMatrix[8] * this.vertexZ[vi] + 16384 >> 15;
								@Pc(1896) int outX = rx + tz;
								@Pc(1900) int outY = ry + m2;
								@Pc(1904) int outZ = rz + ox;
								this.vertexX[vi] = outX;
								this.vertexY[vi] = outY;
								this.vertexZ[vi] = outZ;
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
						boneVerts = this.boneTriangles[i];
						for (k = 0; k < boneVerts.length; k++) {
							vertIdx = boneVerts[k];
							if (this.triangleSources == null || (mask & this.triangleSources[vertIdx]) != 0) {
								value = (this.triangleAlpha[vertIdx] & 0xFF) + transformX * 8;
								if (value < 0) {
									value = 0;
								} else if (value > 255) {
									value = 255;
								}
								this.triangleAlpha[vertIdx] = (byte) value;
							}
						}
					}
				}
			}
		} else if (transformType == 7 && this.boneTriangles != null) {
			for (count = 0; count < labelCount; count++) {
				i = labels[count];
				if (i < this.boneTriangles.length) {
					boneVerts = this.boneTriangles[i];
					for (k = 0; k < boneVerts.length; k++) {
						vertIdx = boneVerts[k];
						if (this.triangleSources == null || (mask & this.triangleSources[vertIdx]) != 0) {
							value = this.triangleColors[vertIdx] & 0xFFFF;
							cosAngle = value >> 10 & 0x3F;
							rotA = value >> 7 & 0x7;
							rotB = value & 0x7F;
							@Pc(2209) int newHue = cosAngle + transformX & 0x3F;
							rotA += transformY;
							if (rotA < 0) {
								rotA = 0;
							} else if (rotA > 7) {
								rotA = 7;
							}
							rotB += transformZ;
							if (rotB < 0) {
								rotB = 0;
							} else if (rotB > 127) {
								rotB = 127;
							}
							this.triangleColors[vertIdx] = (short) (newHue << 10 | rotA << 7 | rotB);
						}
					}
					this.colorsModified = true;
				}
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(IIII)V")
	@Override
	protected void transformShadowBone(@OriginalArg(0) int transformType, @OriginalArg(1) int transformX, @OriginalArg(2) int transformY, @OriginalArg(3) int transformZ) {
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
			} else if (transformType == 7) {
				for (i = 0; i < this.triangleCount; i++) {
					sinAngle = this.triangleColors[i] & 0xFFFF;
					cosAngle = sinAngle >> 10 & 0x3F;
					temp = sinAngle >> 7 & 0x7;
					@Pc(492) int lightness = sinAngle & 0x7F;
					@Pc(498) int newHue = cosAngle + transformX & 0x3F;
					temp += transformY;
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
				this.colorsModified = true;
			}
		}
	}

	@OriginalMember(owner = "client!w", name = "c", descriptor = "(III)V")
	@Override
	public void translate(@OriginalArg(0) int dx, @OriginalArg(1) int dy, @OriginalArg(2) int dz) {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			this.vertexX[i] += dx;
			this.vertexY[i] += dy;
			this.vertexZ[i] += dz;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!w", name = "b", descriptor = "(ZZZ)Lclient!ak;")
	@Override
	public Model copyForLoc(@OriginalArg(0) boolean shareAlpha, @OriginalArg(1) boolean shareColors, @OriginalArg(2) boolean shareNormals) {
		if (!shareAlpha && locCopyAlpha.length < this.triangleCount) {
			locCopyAlpha = new byte[this.triangleCount + 100];
		}
		if (!shareColors && locCopyColors.length < this.triangleCount) {
			locCopyFaceColorA = new int[this.triangleCount + 100];
			locCopyFaceColorB = new int[this.triangleCount + 100];
			locCopyFaceColorC = new int[this.triangleCount + 100];
			locCopyColors = new short[this.triangleCount + 100];
		}
		return this.copy(shareAlpha, shareColors, locCopyTarget, locCopyAlpha, locCopyColors, locCopyFaceColorA, locCopyFaceColorB, locCopyFaceColorC);
	}
}
