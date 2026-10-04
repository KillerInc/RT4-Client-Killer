package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!gb")
public final class RawModel extends Entity {

	@OriginalMember(owner = "client!gb", name = "V", descriptor = "[I")
	public static final int[] SIN = MathUtils.sin;

	@OriginalMember(owner = "client!gb", name = "N", descriptor = "[I")
	public static final int[] COS = MathUtils.cos;

	@OriginalMember(owner = "client!gb", name = "L", descriptor = "[I")
	public static final int[] mergedVertexTagsA = new int[10000];

	@OriginalMember(owner = "client!gb", name = "Z", descriptor = "[I")
	public static final int[] mergedVertexTagsB = new int[10000];

	@OriginalMember(owner = "client!gb", name = "S", descriptor = "I")
	public static int mergeGeneration = 0;

	@OriginalMember(owner = "client!ck", name = "K", descriptor = "I")
	public static int pickScreenY = 0;

	@OriginalMember(owner = "client!d", name = "db", descriptor = "Z")
	public static boolean allowInput = false;

	@OriginalMember(owner = "client!gb", name = "s", descriptor = "[B")
	public byte[] triangleAlpha;

	@OriginalMember(owner = "client!gb", name = "t", descriptor = "S")
	private short maxY;

	@OriginalMember(owner = "client!gb", name = "u", descriptor = "[B")
	public byte[] textureRotationY;

	@OriginalMember(owner = "client!gb", name = "v", descriptor = "[S")
	public short[] textureFacesM;

	@OriginalMember(owner = "client!gb", name = "w", descriptor = "[I")
	public int[] triangleBones;

	@OriginalMember(owner = "client!gb", name = "z", descriptor = "S")
	private short minX;

	@OriginalMember(owner = "client!gb", name = "A", descriptor = "[S")
	public short[] texturesScaleZ;

	@OriginalMember(owner = "client!gb", name = "B", descriptor = "[B")
	public byte[] textureTransU;

	@OriginalMember(owner = "client!gb", name = "D", descriptor = "S")
	private short minY;

	@OriginalMember(owner = "client!gb", name = "E", descriptor = "[S")
	public short[] triangleSources;

	@OriginalMember(owner = "client!gb", name = "F", descriptor = "[[I")
	public int[][] boneTriangles;

	@OriginalMember(owner = "client!gb", name = "G", descriptor = "[B")
	public byte[] textureTypes;

	@OriginalMember(owner = "client!gb", name = "H", descriptor = "[S")
	public short[] triangleTextures;

	@OriginalMember(owner = "client!gb", name = "I", descriptor = "[B")
	public byte[] triangleInfo;

	@OriginalMember(owner = "client!gb", name = "J", descriptor = "[I")
	public int[] vertexBones;

	@OriginalMember(owner = "client!gb", name = "M", descriptor = "I")
	public int texturedCount;

	@OriginalMember(owner = "client!gb", name = "O", descriptor = "[I")
	public int[] triangleVertexC;

	@OriginalMember(owner = "client!gb", name = "P", descriptor = "[I")
	public int[] triangleVertexA;

	@OriginalMember(owner = "client!gb", name = "Q", descriptor = "[B")
	public byte[] triangleTextureIndex;

	@OriginalMember(owner = "client!gb", name = "R", descriptor = "[B")
	public byte[] textureDirection;

	@OriginalMember(owner = "client!gb", name = "T", descriptor = "[B")
	public byte[] textureTransV;

	@OriginalMember(owner = "client!gb", name = "U", descriptor = "[S")
	public short[] triangleColors;

	@OriginalMember(owner = "client!gb", name = "W", descriptor = "[Lclient!hd;")
	public VertexNormal[] vertexNormals;

	@OriginalMember(owner = "client!gb", name = "X", descriptor = "S")
	private short maxX;

	@OriginalMember(owner = "client!gb", name = "Y", descriptor = "S")
	private short maxZ;

	@OriginalMember(owner = "client!gb", name = "ab", descriptor = "S")
	public short contrast;

	@OriginalMember(owner = "client!gb", name = "bb", descriptor = "[B")
	public byte[] textureSpeed;

	@OriginalMember(owner = "client!gb", name = "cb", descriptor = "[I")
	public int[] triangleVertexB;

	@OriginalMember(owner = "client!gb", name = "db", descriptor = "S")
	public short ambient;

	@OriginalMember(owner = "client!gb", name = "eb", descriptor = "S")
	private short minZ;

	@OriginalMember(owner = "client!gb", name = "fb", descriptor = "[I")
	public int[] vertexY;

	@OriginalMember(owner = "client!gb", name = "gb", descriptor = "[S")
	public short[] texturesScaleY;

	@OriginalMember(owner = "client!gb", name = "hb", descriptor = "[Lclient!hd;")
	public VertexNormal[] mergedNormals;

	@OriginalMember(owner = "client!gb", name = "ib", descriptor = "[S")
	public short[] textureFacesP;

	@OriginalMember(owner = "client!gb", name = "jb", descriptor = "[I")
	public int[] vertexX;

	@OriginalMember(owner = "client!gb", name = "kb", descriptor = "[Lclient!qj;")
	public TriangleNormal[] triangleNormals;

	@OriginalMember(owner = "client!gb", name = "mb", descriptor = "[S")
	public short[] texturesScaleX;

	@OriginalMember(owner = "client!gb", name = "nb", descriptor = "[B")
	public byte[] trianglePriorities;

	@OriginalMember(owner = "client!gb", name = "ob", descriptor = "[[I")
	public int[][] boneVertices;

	@OriginalMember(owner = "client!gb", name = "pb", descriptor = "[S")
	public short[] textureFacesN;

	@OriginalMember(owner = "client!gb", name = "qb", descriptor = "[I")
	public int[] vertexZ;

	@OriginalMember(owner = "client!gb", name = "rb", descriptor = "[S")
	public short[] vertexSources;

	@OriginalMember(owner = "client!gb", name = "C", descriptor = "Z")
	private boolean boundsValid = false;

	@OriginalMember(owner = "client!gb", name = "y", descriptor = "I")
	public int triangleCount = 0;

	@OriginalMember(owner = "client!gb", name = "x", descriptor = "B")
	public byte priority = 0;

	@OriginalMember(owner = "client!gb", name = "lb", descriptor = "I")
	public int vertexCount = 0;

	@OriginalMember(owner = "client!gb", name = "<init>", descriptor = "()V")
	private RawModel() {
	}

	@OriginalMember(owner = "client!gb", name = "<init>", descriptor = "([B)V")
	public RawModel(@OriginalArg(0) byte[] src) {
		if (src[src.length - 1] == -1 && src[src.length - 2] == -1) {
			this.decodeNew(src);
		} else {
			this.decodeOld(src);
		}
	}

	@OriginalMember(owner = "client!gb", name = "<init>", descriptor = "(III)V")
	public RawModel(@OriginalArg(0) int vertexCount, @OriginalArg(1) int triangleCount, @OriginalArg(2) int texturedTriangleCount) {
		this.vertexX = new int[vertexCount];
		this.vertexY = new int[vertexCount];
		this.vertexZ = new int[vertexCount];
		this.vertexBones = new int[vertexCount];
		this.triangleVertexA = new int[triangleCount];
		this.triangleVertexB = new int[triangleCount];
		this.triangleVertexC = new int[triangleCount];
		this.triangleInfo = new byte[triangleCount];
		this.trianglePriorities = new byte[triangleCount];
		this.triangleAlpha = new byte[triangleCount];
		this.triangleColors = new short[triangleCount];
		this.triangleTextures = new short[triangleCount];
		this.triangleTextureIndex = new byte[triangleCount];
		this.triangleBones = new int[triangleCount];
	}

	@OriginalMember(owner = "client!gb", name = "<init>", descriptor = "([Lclient!gb;I)V")
	public RawModel(@OriginalArg(0) RawModel[] models, @OriginalArg(1) int count) {
		@Pc(15) boolean keepInfo = false;
		@Pc(17) boolean keepPriorities = false;
		@Pc(19) boolean keepAlpha = false;
		@Pc(21) boolean keepBones = false;
		@Pc(23) boolean keepTextures = false;
		@Pc(25) boolean keepTextureIndex = false;

		this.vertexCount = 0;
		this.triangleCount = 0;
		this.texturedCount = 0;
		this.priority = -1;

		@Pc(43) int i;
		for (i = 0; i < count; i++) {
			@Pc(50) RawModel model = models[i];
			if (model != null) {
				this.vertexCount += model.vertexCount;
				this.triangleCount += model.triangleCount;
				this.texturedCount += model.texturedCount;
				if (model.trianglePriorities == null) {
					if (this.priority == -1) {
						this.priority = model.priority;
					}
					if (this.priority != model.priority) {
						keepPriorities = true;
					}
				} else {
					keepPriorities = true;
				}
				keepInfo |= model.triangleInfo != null;
				keepAlpha |= model.triangleAlpha != null;
				keepBones |= model.triangleBones != null;
				keepTextures |= model.triangleTextures != null;
				keepTextureIndex |= model.triangleTextureIndex != null;
			}
		}

		this.vertexX = new int[this.vertexCount];
		this.vertexY = new int[this.vertexCount];
		this.vertexZ = new int[this.vertexCount];

		this.vertexBones = new int[this.vertexCount];
		this.vertexSources = new short[this.vertexCount];

		this.triangleVertexA = new int[this.triangleCount];
		this.triangleVertexB = new int[this.triangleCount];
		this.triangleVertexC = new int[this.triangleCount];

		if (keepInfo) {
			this.triangleInfo = new byte[this.triangleCount];
		}

		if (keepPriorities) {
			this.trianglePriorities = new byte[this.triangleCount];
		}

		if (keepAlpha) {
			this.triangleAlpha = new byte[this.triangleCount];
		}

		if (keepBones) {
			this.triangleBones = new int[this.triangleCount];
		}

		if (keepTextures) {
			this.triangleTextures = new short[this.triangleCount];
		}

		if (keepTextureIndex) {
			this.triangleTextureIndex = new byte[this.triangleCount];
		}

		this.triangleColors = new short[this.triangleCount];
		this.triangleSources = new short[this.triangleCount];

		if (this.texturedCount > 0) {
			this.textureTypes = new byte[this.texturedCount];
			this.textureFacesP = new short[this.texturedCount];
			this.textureFacesM = new short[this.texturedCount];
			this.textureFacesN = new short[this.texturedCount];
			this.texturesScaleX = new short[this.texturedCount];
			this.texturesScaleY = new short[this.texturedCount];
			this.texturesScaleZ = new short[this.texturedCount];
			this.textureRotationY = new byte[this.texturedCount];
			this.textureDirection = new byte[this.texturedCount];
			this.textureSpeed = new byte[this.texturedCount];
			this.textureTransU = new byte[this.texturedCount];
			this.textureTransV = new byte[this.texturedCount];
		}

		this.vertexCount = 0;
		this.triangleCount = 0;
		this.texturedCount = 0;

		for (i = 0; i < count; i++) {
			@Pc(323) short sourceBit = (short) (0x1 << i);
			@Pc(327) RawModel other = models[i];

			if (other != null) {
				@Pc(331) int t;
				for (t = 0; t < other.triangleCount; t++) {
					if (keepInfo && other.triangleInfo != null) {
						this.triangleInfo[this.triangleCount] = other.triangleInfo[t];
					}

					if (keepPriorities) {
						if (other.trianglePriorities == null) {
							this.trianglePriorities[this.triangleCount] = other.priority;
						} else {
							this.trianglePriorities[this.triangleCount] = other.trianglePriorities[t];
						}
					}

					if (keepAlpha && other.triangleAlpha != null) {
						this.triangleAlpha[this.triangleCount] = other.triangleAlpha[t];
					}

					if (keepBones && other.triangleBones != null) {
						this.triangleBones[this.triangleCount] = other.triangleBones[t];
					}

					if (keepTextures) {
						if (other.triangleTextures == null) {
							this.triangleTextures[this.triangleCount] = -1;
						} else {
							this.triangleTextures[this.triangleCount] = other.triangleTextures[t];
						}
					}

					if (keepTextureIndex) {
						if (other.triangleTextureIndex == null || other.triangleTextureIndex[t] == -1) {
							this.triangleTextureIndex[this.triangleCount] = -1;
						} else {
							this.triangleTextureIndex[this.triangleCount] = (byte) (other.triangleTextureIndex[t] + this.texturedCount);
						}
					}

					this.triangleColors[this.triangleCount] = other.triangleColors[t];
					this.triangleSources[this.triangleCount] = sourceBit;
					this.triangleVertexA[this.triangleCount] = this.addVertex(other, other.triangleVertexA[t], sourceBit);
					this.triangleVertexB[this.triangleCount] = this.addVertex(other, other.triangleVertexB[t], sourceBit);
					this.triangleVertexC[this.triangleCount] = this.addVertex(other, other.triangleVertexC[t], sourceBit);
					this.triangleCount++;
				}

				for (t = 0; t < other.texturedCount; t++) {
					@Pc(530) byte type = this.textureTypes[this.texturedCount] = other.textureTypes[t];
					if (type == 0) {
						this.textureFacesP[this.texturedCount] = (short) this.addVertex(other, other.textureFacesP[t], sourceBit);
						this.textureFacesM[this.texturedCount] = (short) this.addVertex(other, other.textureFacesM[t], sourceBit);
						this.textureFacesN[this.texturedCount] = (short) this.addVertex(other, other.textureFacesN[t], sourceBit);
					} else if (type >= 1 && type <= 3) {
						this.textureFacesP[this.texturedCount] = other.textureFacesP[t];
						this.textureFacesM[this.texturedCount] = other.textureFacesM[t];
						this.textureFacesN[this.texturedCount] = other.textureFacesN[t];
						this.texturesScaleX[this.texturedCount] = other.texturesScaleX[t];
						this.texturesScaleY[this.texturedCount] = other.texturesScaleY[t];
						this.texturesScaleZ[this.texturedCount] = other.texturesScaleZ[t];
						this.textureRotationY[this.texturedCount] = other.textureRotationY[t];
						this.textureDirection[this.texturedCount] = other.textureDirection[t];
						this.textureSpeed[this.texturedCount] = other.textureSpeed[t];
					} else if (type == 2) {
						this.textureTransU[this.texturedCount] = other.textureTransU[t];
						this.textureTransV[this.texturedCount] = other.textureTransV[t];
					}
					this.texturedCount++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "<init>", descriptor = "(Lclient!gb;ZZZZ)V")
	public RawModel(@OriginalArg(0) RawModel other, @OriginalArg(1) boolean reuseVertices, @OriginalArg(2) boolean reuseColors, @OriginalArg(3) boolean reuseTextures, @OriginalArg(4) boolean reuseNormals) {
		this.vertexCount = other.vertexCount;
		this.triangleCount = other.triangleCount;
		this.texturedCount = other.texturedCount;

		if (reuseVertices) {
			this.vertexX = other.vertexX;
			this.vertexY = other.vertexY;
			this.vertexZ = other.vertexZ;
		} else {
			// otherwise, copy them

			this.vertexX = new int[this.vertexCount];
			this.vertexY = new int[this.vertexCount];
			this.vertexZ = new int[this.vertexCount];

			System.arraycopy(other.vertexX, 0, this.vertexX, 0, this.vertexCount);
			System.arraycopy(other.vertexY, 0, this.vertexY, 0, this.vertexCount);
			System.arraycopy(other.vertexZ, 0, this.vertexZ, 0, this.vertexCount);
		}

		if (reuseColors) {
			this.triangleColors = other.triangleColors;
		} else {
			this.triangleColors = new short[this.triangleCount];
			System.arraycopy(other.triangleColors, 0, this.triangleColors, 0, this.triangleCount);
		}

		if (reuseTextures || other.triangleTextures == null) {
			this.triangleTextures = other.triangleTextures;
		} else {
			this.triangleTextures = new short[this.triangleCount];
			System.arraycopy(other.triangleTextures, 0, this.triangleTextures, 0, this.triangleCount);
		}

		this.triangleAlpha = other.triangleAlpha;
		this.triangleVertexA = other.triangleVertexA;
		this.triangleVertexB = other.triangleVertexB;
		this.triangleVertexC = other.triangleVertexC;
		this.triangleInfo = other.triangleInfo;
		this.trianglePriorities = other.trianglePriorities;
		this.triangleTextureIndex = other.triangleTextureIndex;
		this.priority = other.priority;
		this.textureTypes = other.textureTypes;
		this.textureFacesP = other.textureFacesP;
		this.textureFacesM = other.textureFacesM;
		this.textureFacesN = other.textureFacesN;
		this.texturesScaleX = other.texturesScaleX;
		this.texturesScaleY = other.texturesScaleY;
		this.texturesScaleZ = other.texturesScaleZ;
		this.textureRotationY = other.textureRotationY;
		this.textureDirection = other.textureDirection;
		this.textureSpeed = other.textureSpeed;
		this.textureTransU = other.textureTransU;
		this.textureTransV = other.textureTransV;
		this.vertexBones = other.vertexBones;
		this.triangleBones = other.triangleBones;
		this.boneVertices = other.boneVertices;
		this.boneTriangles = other.boneTriangles;
		this.vertexNormals = other.vertexNormals;
		this.triangleNormals = other.triangleNormals;
		this.mergedNormals = other.mergedNormals;
		this.ambient = other.ambient;
		this.contrast = other.contrast;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "([[III)I")
	public static int interpolateHeight(@OriginalArg(0) int[][] heightmap, @OriginalArg(1) int fineX, @OriginalArg(2) int fineZ) {
		@Pc(3) int tileX = fineX >> 7;
		@Pc(7) int tileZ = fineZ >> 7;
		if (tileX < 0 || tileZ < 0 || tileX >= heightmap.length || tileZ >= heightmap[0].length) {
			return 0;
		}
		@Pc(27) int fracX = fineX & 0x7F;
		@Pc(31) int fracZ = fineZ & 0x7F;
		@Pc(53) int topInterp = heightmap[tileX][tileZ] * (128 - fracX) + heightmap[tileX + 1][tileZ] * fracX >> 7;
		@Pc(79) int bottomInterp = heightmap[tileX][tileZ + 1] * (128 - fracX) + heightmap[tileX + 1][tileZ + 1] * fracX >> 7;
		return topInterp * (128 - fracZ) + bottomInterp * fracZ >> 7;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(Lclient!ve;II)Lclient!gb;")
	public static RawModel create(@OriginalArg(0) Js5 archive, @OriginalArg(1) int id) {
		@Pc(5) byte[] data = archive.fetchFile(id, 0);
		return data == null ? null : new RawModel(data);
	}

	@OriginalMember(owner = "client!gb", name = "c", descriptor = "()V")
	public final void negateXz() {
		for (@Pc(1) int v = 0; v < this.vertexCount; v++) {
			this.vertexX[v] = -this.vertexX[v];
			this.vertexZ[v] = -this.vertexZ[v];
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "e", descriptor = "()V")
	public final void swapXz() {
		for (@Pc(1) int v = 0; v < this.vertexCount; v++) {
			@Pc(10) int temp = this.vertexX[v];
			this.vertexX[v] = this.vertexZ[v];
			this.vertexZ[v] = -temp;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "(I)V")
	private void rotate(@OriginalArg(0) int theta) {
		@Pc(3) int sin = SIN[theta];
		@Pc(7) int cos = COS[theta];
		for (@Pc(9) int v = 0; v < this.vertexCount; v++) {
			@Pc(29) int temp = this.vertexY[v] * sin + this.vertexX[v] * cos >> 16;
			this.vertexY[v] = this.vertexY[v] * cos - this.vertexX[v] * sin >> 16;
			this.vertexX[v] = temp;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "f", descriptor = "()V")
	private void calculateBounds() {
		if (this.boundsValid) {
			return;
		}

		this.boundsValid = true;

		@Pc(8) int minX = 32767;
		@Pc(10) int minY = 32767;
		@Pc(12) int minZ = 32767;
		@Pc(14) int maxX = -32768;
		@Pc(16) int maxY = -32768;
		@Pc(18) int maxZ = -32768;

		for (@Pc(20) int v = 0; v < this.vertexCount; v++) {
			@Pc(29) int x = this.vertexX[v];
			@Pc(34) int y = this.vertexY[v];
			@Pc(39) int z = this.vertexZ[v];

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
		}

		this.minX = (short) minX;
		this.maxX = (short) maxX;
		this.minY = (short) minY;
		this.maxY = (short) maxY;
		this.minZ = (short) minZ;
		this.maxZ = (short) maxZ;
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "(III)V")
	public final void resize(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z) {
		for (@Pc(1) int v = 0; v < this.vertexCount; v++) {
			this.vertexX[v] = this.vertexX[v] * x / 128;
			this.vertexY[v] = this.vertexY[v] * y / 128;
			this.vertexZ[v] = this.vertexZ[v] * z / 128;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(III)Lclient!th;")
	@Override
	public final Entity createModel() {
		return this.createModel(this.ambient, this.contrast, -50, -10, -50);
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(Lclient!gb;IS)I")
	private int addVertex(@OriginalArg(0) RawModel m, @OriginalArg(1) int vertex, @OriginalArg(2) short source) {
		@Pc(4) int x = m.vertexX[vertex];
		@Pc(9) int y = m.vertexY[vertex];
		@Pc(14) int z = m.vertexZ[vertex];

		for (@Pc(16) int i = 0; i < this.vertexCount; i++) {
			if (x == this.vertexX[i] && y == this.vertexY[i] && z == this.vertexZ[i]) {
				this.vertexSources[i] |= source;
				return i;
			}
		}

		this.vertexX[this.vertexCount] = x;
		this.vertexY[this.vertexCount] = y;
		this.vertexZ[this.vertexCount] = z;
		this.vertexSources[this.vertexCount] = source;

		if (m.vertexBones != null) {
			this.vertexBones[this.vertexCount] = m.vertexBones[vertex];
		}

		return this.vertexCount++;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "([[IIIIII)V")
	private void alignToTerrain(@OriginalArg(0) int[][] heightmap, @OriginalArg(1) int centerX, @OriginalArg(2) int baseHeight, @OriginalArg(3) int centerZ, @OriginalArg(4) int sizeX, @OriginalArg(5) int sizeZ) {
		@Pc(10) int negHalfX = -sizeX / 2;
		@Pc(15) int negHalfZ = -sizeZ / 2;
		@Pc(24) int heightSW = interpolateHeight(heightmap, centerX + negHalfX, centerZ + negHalfZ);
		@Pc(28) int halfX = sizeX / 2;
		@Pc(33) int negHalfZ2 = -sizeZ / 2;
		@Pc(42) int heightSE = interpolateHeight(heightmap, centerX + halfX, centerZ + negHalfZ2);
		@Pc(47) int negHalfX2 = -sizeX / 2;
		@Pc(51) int halfZ = sizeZ / 2;
		@Pc(60) int heightNW = interpolateHeight(heightmap, centerX + negHalfX2, centerZ + halfZ);
		@Pc(64) int halfX2 = sizeX / 2;
		@Pc(68) int halfZ2 = sizeZ / 2;
		@Pc(77) int heightNE = interpolateHeight(heightmap, centerX + halfX2, centerZ + halfZ2);
		@Pc(84) int minSouth = heightSW < heightSE ? heightSW : heightSE;
		@Pc(91) int minNorth = heightNW < heightNE ? heightNW : heightNE;
		@Pc(98) int minEast = heightSE < heightNE ? heightSE : heightNE;
		@Pc(105) int minWest = heightSW < heightNW ? heightSW : heightNW;
		if (sizeZ != 0) {
			@Pc(120) int rollAngle = (int) (Math.atan2(minSouth - minNorth, sizeZ) * 325.95D) & 0x7FF;
			if (rollAngle != 0) {
				this.rotateX(rollAngle);
			}
		}
		if (sizeX != 0) {
			@Pc(140) int pitchAngle = (int) (Math.atan2(minWest - minEast, sizeX) * 325.95D) & 0x7FF;
			if (pitchAngle != 0) {
				this.rotate(pitchAngle);
			}
		}
		@Pc(149) int avgHeight = heightSW + heightNE;
		if (heightSE + heightNW < avgHeight) {
			avgHeight = heightSE + heightNW;
		}
		avgHeight = (avgHeight >> 1) - baseHeight;
		if (avgHeight != 0) {
			this.translate(0, avgHeight, 0);
		}
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int sinYaw, @OriginalArg(4) int cosYaw, @OriginalArg(5) int offsetX, @OriginalArg(6) int offsetY, @OriginalArg(7) int offsetZ, @OriginalArg(8) long key, @OriginalArg(9) int bitset, @OriginalArg(10) ParticleSystem particleSystem) {
	}

	@OriginalMember(owner = "client!gb", name = "g", descriptor = "()V")
	public final void calculateNormals() {
		if (this.vertexNormals != null) {
			return;
		}

		this.vertexNormals = new VertexNormal[this.vertexCount];

		@Pc(10) int i;
		for (i = 0; i < this.vertexCount; i++) {
			this.vertexNormals[i] = new VertexNormal();
		}

		for (i = 0; i < this.triangleCount; i++) {
			@Pc(34) int vA = this.triangleVertexA[i];
			@Pc(39) int vB = this.triangleVertexB[i];
			@Pc(44) int vC = this.triangleVertexC[i];
			@Pc(54) int dxAB = this.vertexX[vB] - this.vertexX[vA];
			@Pc(64) int dyAB = this.vertexY[vB] - this.vertexY[vA];
			@Pc(74) int dzAB = this.vertexZ[vB] - this.vertexZ[vA];
			@Pc(84) int dxAC = this.vertexX[vC] - this.vertexX[vA];
			@Pc(94) int dyAC = this.vertexY[vC] - this.vertexY[vA];
			@Pc(104) int dzAC = this.vertexZ[vC] - this.vertexZ[vA];
			@Pc(112) int nx = dyAB * dzAC - dyAC * dzAB;
			@Pc(120) int ny = dzAB * dxAC - dzAC * dxAB;

			@Pc(128) int nz;
			for (nz = dxAB * dyAC - dxAC * dyAB; nx > 8192 || ny > 8192 || nz > 8192 || nx < -8192 || ny < -8192 || nz < -8192; nz >>= 0x1) {
				nx >>= 0x1;
				ny >>= 0x1;
			}

			@Pc(174) int length = (int) Math.sqrt(nx * nx + ny * ny + nz * nz);
			if (length <= 0) {
				length = 1;
			}

			nx = nx * 256 / length;
			ny = ny * 256 / length;
			nz = nz * 256 / length;

			@Pc(201) byte info;
			if (this.triangleInfo == null) {
				info = 0;
			} else {
				info = this.triangleInfo[i];
			}

			if (info == 0) {
				@Pc(214) VertexNormal n1 = this.vertexNormals[vA];
				n1.x += nx;
				n1.y += ny;
				n1.z += nz;
				n1.magnitude++;
				@Pc(243) VertexNormal n2 = this.vertexNormals[vB];
				n2.x += nx;
				n2.y += ny;
				n2.z += nz;
				n2.magnitude++;
				@Pc(272) VertexNormal n3 = this.vertexNormals[vC];
				n3.x += nx;
				n3.y += ny;
				n3.z += nz;
				n3.magnitude++;
			} else if (info == 1) {
				if (this.triangleNormals == null) {
					this.triangleNormals = new TriangleNormal[this.triangleCount];
				}

				@Pc(317) TriangleNormal n = this.triangleNormals[i] = new TriangleNormal();
				n.x = nx;
				n.y = ny;
				n.z = nz;
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(SS)V")
	public final void retexture(@OriginalArg(0) short src, @OriginalArg(1) short dest) {
		if (this.triangleTextures == null) {
			return;
		}

		for (@Pc(5) int i = 0; i < this.triangleCount; i++) {
			if (this.triangleTextures[i] == src) {
				this.triangleTextures[i] = dest;
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(II[[I[[IIIIZZ)Lclient!gb;")
	public final RawModel placeOnTerrain(@OriginalArg(0) int orientation, @OriginalArg(1) int sizeParam, @OriginalArg(2) int[][] heightmap, @OriginalArg(3) int[][] heightmap2, @OriginalArg(4) int fineX, @OriginalArg(5) int baseHeight, @OriginalArg(6) int fineZ) {
		this.calculateBounds();
		@Pc(6) int westEdge = fineX + this.minX;
		@Pc(11) int eastEdge = fineX + this.maxX;
		@Pc(16) int southEdge = fineZ + this.minZ;
		@Pc(21) int northEdge = fineZ + this.maxZ;
		if ((orientation == 1 || orientation == 2 || orientation == 3 || orientation == 5) && (westEdge < 0 || eastEdge + 128 >> 7 >= heightmap.length || southEdge < 0 || northEdge + 128 >> 7 >= heightmap[0].length)) {
			return this;
		}
		if (orientation == 4 || orientation == 5) {
			if (heightmap2 == null) {
				return this;
			}
			if (westEdge < 0 || eastEdge + 128 >> 7 >= heightmap2.length || southEdge < 0 || northEdge + 128 >> 7 >= heightmap2[0].length) {
				return this;
			}
		} else {
			westEdge >>= 0x7;
			eastEdge = eastEdge + 127 >> 7;
			southEdge >>= 0x7;
			northEdge = northEdge + 127 >> 7;
			if (heightmap[westEdge][southEdge] == baseHeight && heightmap[eastEdge][southEdge] == baseHeight && heightmap[westEdge][northEdge] == baseHeight && heightmap[eastEdge][northEdge] == baseHeight) {
				return this;
			}
		}

		@Pc(147) RawModel m = new RawModel();
		m.vertexCount = this.vertexCount;
		m.triangleCount = this.triangleCount;
		m.texturedCount = this.texturedCount;
		m.triangleVertexA = this.triangleVertexA;
		m.triangleVertexB = this.triangleVertexB;
		m.triangleVertexC = this.triangleVertexC;
		m.triangleInfo = this.triangleInfo;
		m.trianglePriorities = this.trianglePriorities;
		m.triangleAlpha = this.triangleAlpha;
		m.triangleTextureIndex = this.triangleTextureIndex;
		m.triangleColors = this.triangleColors;
		m.triangleTextures = this.triangleTextures;
		m.priority = this.priority;
		m.textureTypes = this.textureTypes;
		m.textureFacesP = this.textureFacesP;
		m.textureFacesM = this.textureFacesM;
		m.textureFacesN = this.textureFacesN;
		m.texturesScaleX = this.texturesScaleX;
		m.texturesScaleY = this.texturesScaleY;
		m.texturesScaleZ = this.texturesScaleZ;
		m.textureRotationY = this.textureRotationY;
		m.textureDirection = this.textureDirection;
		m.textureSpeed = this.textureSpeed;
		m.textureTransU = this.textureTransU;
		m.textureTransV = this.textureTransV;
		m.vertexBones = this.vertexBones;
		m.triangleBones = this.triangleBones;
		m.boneVertices = this.boneVertices;
		m.boneTriangles = this.boneTriangles;
		m.ambient = this.ambient;
		m.contrast = this.contrast;
		m.vertexNormals = this.vertexNormals;
		m.triangleNormals = this.triangleNormals;
		m.mergedNormals = this.mergedNormals;
		if (orientation == 3) {
			m.vertexX = ArrayUtils.copyOfNullable(this.vertexX);
			m.vertexY = ArrayUtils.copyOfNullable(this.vertexY);
			m.vertexZ = ArrayUtils.copyOfNullable(this.vertexZ);
		} else {
			m.vertexX = this.vertexX;
			m.vertexY = new int[m.vertexCount];
			m.vertexZ = this.vertexZ;
		}
		@Pc(326) int v;
		@Pc(337) int vx;
		@Pc(344) int vz;
		@Pc(348) int fracX;
		@Pc(352) int fracZ;
		@Pc(356) int tileX;
		@Pc(360) int tileZ;
		@Pc(382) int topInterp;
		@Pc(408) int bottomInterp;
		@Pc(420) int interpHeight;
		if (orientation == 1) {
			for (v = 0; v < m.vertexCount; v++) {
				vx = this.vertexX[v] + fineX;
				vz = this.vertexZ[v] + fineZ;
				fracX = vx & 0x7F;
				fracZ = vz & 0x7F;
				tileX = vx >> 7;
				tileZ = vz >> 7;
				topInterp = heightmap[tileX][tileZ] * (128 - fracX) + heightmap[tileX + 1][tileZ] * fracX >> 7;
				bottomInterp = heightmap[tileX][tileZ + 1] * (128 - fracX) + heightmap[tileX + 1][tileZ + 1] * fracX >> 7;
				interpHeight = topInterp * (128 - fracZ) + bottomInterp * fracZ >> 7;
				m.vertexY[v] = this.vertexY[v] + interpHeight - baseHeight;
			}
		} else {
			@Pc(547) int finalHeight;
			if (orientation == 2) {
				for (v = 0; v < m.vertexCount; v++) {
					vx = (this.vertexY[v] << 16) / this.minY;
					if (vx < sizeParam) {
						vz = this.vertexX[v] + fineX;
						fracX = this.vertexZ[v] + fineZ;
						fracZ = vz & 0x7F;
						tileX = fracX & 0x7F;
						tileZ = vz >> 7;
						topInterp = fracX >> 7;
						bottomInterp = heightmap[tileZ][topInterp] * (128 - fracZ) + heightmap[tileZ + 1][topInterp] * fracZ >> 7;
						interpHeight = heightmap[tileZ][topInterp + 1] * (128 - fracZ) + heightmap[tileZ + 1][topInterp + 1] * fracZ >> 7;
						finalHeight = bottomInterp * (128 - tileX) + interpHeight * tileX >> 7;
						m.vertexY[v] = this.vertexY[v] + (finalHeight - baseHeight) * (sizeParam - vx) / sizeParam;
					} else {
						m.vertexY[v] = this.vertexY[v];
					}
				}
			} else if (orientation == 3) {
				v = (sizeParam & 0xFF) * 4;
				vx = (sizeParam >> 8 & 0xFF) * 4;
				this.alignToTerrain(heightmap, fineX, baseHeight, fineZ, v, vx);
			} else if (orientation == 4) {
				v = this.maxY - this.minY;
				for (vx = 0; vx < this.vertexCount; vx++) {
					vz = this.vertexX[vx] + fineX;
					fracX = this.vertexZ[vx] + fineZ;
					fracZ = vz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = vz >> 7;
					topInterp = fracX >> 7;
					bottomInterp = heightmap2[tileZ][topInterp] * (128 - fracZ) + heightmap2[tileZ + 1][topInterp] * fracZ >> 7;
					interpHeight = heightmap2[tileZ][topInterp + 1] * (128 - fracZ) + heightmap2[tileZ + 1][topInterp + 1] * fracZ >> 7;
					finalHeight = bottomInterp * (128 - tileX) + interpHeight * tileX >> 7;
					m.vertexY[vx] = this.vertexY[vx] + finalHeight + v - baseHeight;
				}
			} else if (orientation == 5) {
				v = this.maxY - this.minY;
				for (vx = 0; vx < this.vertexCount; vx++) {
					vz = this.vertexX[vx] + fineX;
					fracX = this.vertexZ[vx] + fineZ;
					fracZ = vz & 0x7F;
					tileX = fracX & 0x7F;
					tileZ = vz >> 7;
					topInterp = fracX >> 7;
					bottomInterp = heightmap[tileZ][topInterp] * (128 - fracZ) + heightmap[tileZ + 1][topInterp] * fracZ >> 7;
					interpHeight = heightmap[tileZ][topInterp + 1] * (128 - fracZ) + heightmap[tileZ + 1][topInterp + 1] * fracZ >> 7;
					finalHeight = bottomInterp * (128 - tileX) + interpHeight * tileX >> 7;
					bottomInterp = heightmap2[tileZ][topInterp] * (128 - fracZ) + heightmap2[tileZ + 1][topInterp] * fracZ >> 7;
					interpHeight = heightmap2[tileZ][topInterp + 1] * (128 - fracZ) + heightmap2[tileZ + 1][topInterp + 1] * fracZ >> 7;
					@Pc(890) int bridgeHeight = bottomInterp * (128 - tileX) + interpHeight * tileX >> 7;
					@Pc(894) int heightDiff = finalHeight - bridgeHeight;
					m.vertexY[vx] = ((this.vertexY[vx] << 8) / v * heightDiff >> 8) - (baseHeight - finalHeight);
				}
			}
		}
		this.boundsValid = false;
		return m;
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "(IIIII)Lclient!w;")
	public final SoftwareModel createSoftwareModel(@OriginalArg(0) int ambient, @OriginalArg(1) int contrast) {
		return new SoftwareModel(this, ambient, contrast, -50, -10, -50);
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int yaw, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) int bitset) {
	}

	@OriginalMember(owner = "client!gb", name = "c", descriptor = "(III)V")
	public final void translate(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z) {
		for (@Pc(1) int v = 0; v < this.vertexCount; v++) {
			this.vertexX[v] += x;
			this.vertexY[v] += y;
			this.vertexZ[v] += z;
		}

		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "h", descriptor = "()V")
	public final void negateZAndReverseFaces() {
		@Pc(1) int i;
		for (i = 0; i < this.vertexCount; i++) {
			this.vertexZ[i] = -this.vertexZ[i];
		}
		for (i = 0; i < this.triangleCount; i++) {
			@Pc(27) int temp = this.triangleVertexA[i];
			this.triangleVertexA[i] = this.triangleVertexC[i];
			this.triangleVertexC[i] = temp;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "()Z")
	@Override
	public final boolean canMerge() {
		return true;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "([B)V")
	private void decodeNew(@OriginalArg(0) byte[] src) {
		// originally Jagex had obhead, obface1-5, obpoint1-5, obvertex1-2, and obaxis... might be possibly to simplify
		// these buffer names to that again. But they've also consolidated some

		@Pc(4) Buffer buffer1 = new Buffer(src);
		@Pc(9) Buffer buffer2 = new Buffer(src);
		@Pc(14) Buffer buffer3 = new Buffer(src);
		@Pc(19) Buffer buffer4 = new Buffer(src);
		@Pc(24) Buffer buffer5 = new Buffer(src);
		@Pc(29) Buffer buffer6 = new Buffer(src);
		@Pc(34) Buffer buffer7 = new Buffer(src);
		buffer1.offset = src.length - 23;

		@Pc(44) int vertexCount = buffer1.g2();
		@Pc(48) int triangleCount = buffer1.g2();
		@Pc(52) int texturedCount = buffer1.g1();

		@Pc(56) int hasInfo = buffer1.g1();
		@Pc(65) boolean hasTriangleInfo = (hasInfo & 0x1) == 1;
		@Pc(74) boolean hasParticleEmitters = (hasInfo & 0x2) == 2; // not used in 530

		@Pc(78) int priority = buffer1.g1();
		@Pc(82) int hasAlpha = buffer1.g1();
		@Pc(86) int hasTriangleBones = buffer1.g1();
		@Pc(90) int hasTextures = buffer1.g1();
		@Pc(94) int hasVertexBones = buffer1.g1();

		@Pc(98) int dxDataLength = buffer1.g2();
		@Pc(102) int dyDataLength = buffer1.g2();
		@Pc(106) int dzDataLength = buffer1.g2();
		@Pc(110) int vertexIndexDataLength = buffer1.g2();
		@Pc(114) int triangleTextureDataLength = buffer1.g2();

		@Pc(116) int simpleTextureFaceCount = 0;
		@Pc(118) int complexTextureFaceCount = 0;
		@Pc(120) int cubeTextureFaceCount = 0;

		if (texturedCount > 0) {
			this.textureTypes = new byte[texturedCount];
			buffer1.offset = 0;

			for (int i = 0; i < texturedCount; i++) {
				@Pc(143) byte type = this.textureTypes[i] = buffer1.g1b();
				if (type == 0) {
					simpleTextureFaceCount++;
				} else if (type >= 1 && type <= 3) {
					complexTextureFaceCount++;
				}
				if (type == 2) {
					cubeTextureFaceCount++;
				}
			}
		}

		int offset = texturedCount + vertexCount;

		@Pc(169) int triangleInfoDataOffset = offset;
		if (hasTriangleInfo) {
			offset += triangleCount;
		}

		@Pc(177) int triangleTypeDataOffset = offset;
		offset += triangleCount;

		@Pc(183) int trianglePriorityDataOffset = offset;
		if (priority == 255) {
			offset += triangleCount;
		}

		@Pc(192) int triangleBonesDataOffset = offset;
		if (hasTriangleBones == 1) {
			offset += triangleCount;
		}

		@Pc(201) int vertexBonesDataOffset = offset;
		if (hasVertexBones == 1) {
			offset += vertexCount;
		}

		@Pc(210) int triangleAlphaDataOffset = offset;
		if (hasAlpha == 1) {
			offset += triangleCount;
		}

		@Pc(219) int vertexIndexDataOffset = offset;
		offset += vertexIndexDataLength;

		@Pc(225) int triangleTexturesDataOffset = offset;
		if (hasTextures == 1) {
			offset += triangleCount * 2;
		}

		@Pc(236) int triangleTextureIndexDataOffset = offset;
		offset += triangleTextureDataLength;

		@Pc(242) int triangleColorDataOffset = offset;
		offset += triangleCount * 2;

		@Pc(250) int dxDataOffset = offset;
		offset += dxDataLength;

		@Pc(256) int dyDataOffset = offset;
		offset += dyDataLength;

		@Pc(262) int dzDataOffset = offset;
		offset += dzDataLength;

		@Pc(268) int simplePmnDataOffset = offset;
		offset += simpleTextureFaceCount * 6;

		@Pc(276) int complexPmnDataOffset = offset;
		offset += complexTextureFaceCount * 6;

		@Pc(284) int complexScaleDataOffset = offset;
		offset += complexTextureFaceCount * 6;

		@Pc(292) int complexRotationDataOffset = offset;
		offset += complexTextureFaceCount;

		@Pc(298) int cube1DataOffset = offset;
		offset += complexTextureFaceCount;

		@Pc(304) int cube2DataOffset = offset;
		offset += complexTextureFaceCount + cubeTextureFaceCount * 2;

		this.vertexCount = vertexCount;
		this.triangleCount = triangleCount;
		this.texturedCount = texturedCount;

		this.vertexX = new int[vertexCount];
		this.vertexY = new int[vertexCount];
		this.vertexZ = new int[vertexCount];

		this.triangleVertexA = new int[triangleCount];
		this.triangleVertexB = new int[triangleCount];
		this.triangleVertexC = new int[triangleCount];

		if (hasVertexBones == 1) {
			this.vertexBones = new int[vertexCount];
		}

		if (hasTriangleInfo) {
			this.triangleInfo = new byte[triangleCount];
		}

		if (priority == 255) {
			this.trianglePriorities = new byte[triangleCount];
		} else {
			this.priority = (byte) priority;
		}

		if (hasAlpha == 1) {
			this.triangleAlpha = new byte[triangleCount];
		}

		if (hasTriangleBones == 1) {
			this.triangleBones = new int[triangleCount];
		}

		if (hasTextures == 1) {
			this.triangleTextures = new short[triangleCount];
		}

		if (hasTextures == 1 && texturedCount > 0) {
			this.triangleTextureIndex = new byte[triangleCount];
		}

		this.triangleColors = new short[triangleCount];

		if (texturedCount > 0) {
			this.textureFacesP = new short[texturedCount];
			this.textureFacesM = new short[texturedCount];
			this.textureFacesN = new short[texturedCount];

			if (complexTextureFaceCount > 0) {
				this.texturesScaleX = new short[complexTextureFaceCount];
				this.texturesScaleY = new short[complexTextureFaceCount];
				this.texturesScaleZ = new short[complexTextureFaceCount];
				this.textureRotationY = new byte[complexTextureFaceCount];
				this.textureDirection = new byte[complexTextureFaceCount];
				this.textureSpeed = new byte[complexTextureFaceCount];
			}

			if (cubeTextureFaceCount > 0) {
				this.textureTransU = new byte[cubeTextureFaceCount];
				this.textureTransV = new byte[cubeTextureFaceCount];
			}
		}

		buffer1.offset = texturedCount;
		buffer2.offset = dxDataOffset;
		buffer3.offset = dyDataOffset;
		buffer4.offset = dzDataOffset;
		buffer5.offset = vertexBonesDataOffset;

		@Pc(473) int prevVertexX = 0;
		@Pc(475) int prevVertexY = 0;
		@Pc(477) int prevVertexZ = 0;

		for (int v = 0; v < vertexCount; v++) {
			int flags = buffer1.g1();

			int dx = 0;
			if ((flags & 0x1) != 0) {
				dx = buffer2.gsmart();
			}

			int dy = 0;
			if ((flags & 0x2) != 0) {
				dy = buffer3.gsmart();
			}

			int dz = 0;
			if ((flags & 0x4) != 0) {
				dz = buffer4.gsmart();
			}

			this.vertexX[v] = prevVertexX + dx;
			this.vertexY[v] = prevVertexY + dy;
			this.vertexZ[v] = prevVertexZ + dz;

			prevVertexX = this.vertexX[v];
			prevVertexY = this.vertexY[v];
			prevVertexZ = this.vertexZ[v];

			if (hasVertexBones == 1) {
				this.vertexBones[v] = buffer5.g1();
			}
		}

		buffer1.offset = triangleColorDataOffset;
		buffer2.offset = triangleInfoDataOffset;
		buffer3.offset = trianglePriorityDataOffset;
		buffer4.offset = triangleAlphaDataOffset;
		buffer5.offset = triangleBonesDataOffset;
		buffer6.offset = triangleTexturesDataOffset;
		buffer7.offset = triangleTextureIndexDataOffset;

		for (int t = 0; t < triangleCount; t++) {
			this.triangleColors[t] = (short) buffer1.g2();

			if (hasTriangleInfo) {
				this.triangleInfo[t] = buffer2.g1b();
			}

			if (priority == 255) {
				this.trianglePriorities[t] = buffer3.g1b();
			}

			if (hasAlpha == 1) {
				this.triangleAlpha[t] = buffer4.g1b();
			}

			if (hasTriangleBones == 1) {
				this.triangleBones[t] = buffer5.g1();
			}

			if (hasTextures == 1) {
				this.triangleTextures[t] = (short) (buffer6.g2() - 1);
			}

			if (this.triangleTextureIndex != null) {
				if (this.triangleTextures[t] == -1) {
					this.triangleTextureIndex[t] = -1;
				} else {
					this.triangleTextureIndex[t] = (byte) (buffer7.g1() - 1);
				}
			}
		}

		buffer1.offset = vertexIndexDataOffset;
		buffer2.offset = triangleTypeDataOffset;

		int a = 0;
		int b = 0;
		int c = 0;
		int last = 0;

		for (int t = 0; t < triangleCount; t++) {
			int type = buffer2.g1();
			if (type == 1) {
				a = buffer1.gsmart() + last;
				b = buffer1.gsmart() + a;
				c = buffer1.gsmart() + b;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 2) {
				b = c;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 3) {
				a = c;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 4) {
				@Pc(803) int b0 = a;
				a = b;
				b = b0;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b0;
				this.triangleVertexC[t] = c;
			}
		}

		buffer1.offset = simplePmnDataOffset;
		buffer2.offset = complexPmnDataOffset;
		buffer3.offset = complexScaleDataOffset;
		buffer4.offset = complexRotationDataOffset;
		buffer5.offset = cube1DataOffset;
		buffer6.offset = cube2DataOffset;

		for (int t = 0; t < texturedCount; t++) {
			int type = this.textureTypes[t] & 0xFF;
			if (type == 0) {
				this.textureFacesP[t] = (short) buffer1.g2();
				this.textureFacesM[t] = (short) buffer1.g2();
				this.textureFacesN[t] = (short) buffer1.g2();
			} else if (type == 1) {
				this.textureFacesP[t] = (short) buffer2.g2();
				this.textureFacesM[t] = (short) buffer2.g2();
				this.textureFacesN[t] = (short) buffer2.g2();
				this.texturesScaleX[t] = (short) buffer3.g2();
				this.texturesScaleY[t] = (short) buffer3.g2();
				this.texturesScaleZ[t] = (short) buffer3.g2();
				this.textureRotationY[t] = buffer4.g1b();
				this.textureDirection[t] = buffer5.g1b();
				this.textureSpeed[t] = buffer6.g1b();
			} else if (type == 2) {
				this.textureFacesP[t] = (short) buffer2.g2();
				this.textureFacesM[t] = (short) buffer2.g2();
				this.textureFacesN[t] = (short) buffer2.g2();
				this.texturesScaleX[t] = (short) buffer3.g2();
				this.texturesScaleY[t] = (short) buffer3.g2();
				this.texturesScaleZ[t] = (short) buffer3.g2();
				this.textureRotationY[t] = buffer4.g1b();
				this.textureDirection[t] = buffer5.g1b();
				this.textureSpeed[t] = buffer6.g1b();
				this.textureTransU[t] = buffer6.g1b();
				this.textureTransV[t] = buffer6.g1b();
			} else if (type == 3) {
				this.textureFacesP[t] = (short) buffer2.g2();
				this.textureFacesM[t] = (short) buffer2.g2();
				this.textureFacesN[t] = (short) buffer2.g2();
				this.texturesScaleX[t] = (short) buffer3.g2();
				this.texturesScaleY[t] = (short) buffer3.g2();
				this.texturesScaleZ[t] = (short) buffer3.g2();
				this.textureRotationY[t] = buffer4.g1b();
				this.textureDirection[t] = buffer5.g1b();
				this.textureSpeed[t] = buffer6.g1b();
			}
		}

		if (hasParticleEmitters) {
			buffer1.offset = offset;

			int particleEmittersLen = buffer1.g1();
			if (particleEmittersLen > 0) {
				buffer1.offset += particleEmittersLen * 4;
			}

			int particleEffectorsLen = buffer1.g1();
			if (particleEffectorsLen > 0) {
				buffer1.offset += particleEffectorsLen * 4;
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "i", descriptor = "()Lclient!gb;")
	public final RawModel shallowCopy() {
		@Pc(3) RawModel m = new RawModel();
		if (this.triangleInfo != null) {
			m.triangleInfo = new byte[this.triangleCount];
			System.arraycopy(this.triangleInfo, 0, m.triangleInfo, 0, this.triangleCount);
		}

		m.vertexCount = this.vertexCount;
		m.triangleCount = this.triangleCount;
		m.texturedCount = this.texturedCount;
		m.vertexX = this.vertexX;
		m.vertexY = this.vertexY;
		m.vertexZ = this.vertexZ;
		m.triangleVertexA = this.triangleVertexA;
		m.triangleVertexB = this.triangleVertexB;
		m.triangleVertexC = this.triangleVertexC;
		m.trianglePriorities = this.trianglePriorities;
		m.triangleAlpha = this.triangleAlpha;
		m.triangleTextureIndex = this.triangleTextureIndex;
		m.triangleColors = this.triangleColors;
		m.triangleTextures = this.triangleTextures;
		m.priority = this.priority;
		m.textureTypes = this.textureTypes;
		m.textureFacesP = this.textureFacesP;
		m.textureFacesM = this.textureFacesM;
		m.textureFacesN = this.textureFacesN;
		m.texturesScaleX = this.texturesScaleX;
		m.texturesScaleY = this.texturesScaleY;
		m.texturesScaleZ = this.texturesScaleZ;
		m.textureRotationY = this.textureRotationY;
		m.textureDirection = this.textureDirection;
		m.textureSpeed = this.textureSpeed;
		m.textureTransU = this.textureTransU;
		m.textureTransV = this.textureTransV;
		m.vertexBones = this.vertexBones;
		m.triangleBones = this.triangleBones;
		m.boneVertices = this.boneVertices;
		m.boneTriangles = this.boneTriangles;
		m.vertexNormals = this.vertexNormals;
		m.triangleNormals = this.triangleNormals;
		m.ambient = this.ambient;
		m.contrast = this.contrast;
		return m;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(IIIBSB)I")
	public final int addTriangle(@OriginalArg(0) int vertexA, @OriginalArg(1) int vertexB, @OriginalArg(2) int vertexC, @OriginalArg(4) short color, @OriginalArg(5) byte alpha) {
		this.triangleVertexA[this.triangleCount] = vertexA;
		this.triangleVertexB[this.triangleCount] = vertexB;
		this.triangleVertexC[this.triangleCount] = vertexC;
		this.triangleInfo[this.triangleCount] = 1;
		this.triangleTextureIndex[this.triangleCount] = -1;
		this.triangleColors[this.triangleCount] = color;
		this.triangleTextures[this.triangleCount] = -1;
		this.triangleAlpha[this.triangleCount] = alpha;
		return this.triangleCount++;
	}

	@OriginalMember(owner = "client!gb", name = "c", descriptor = "(I)V")
	private void rotateX(@OriginalArg(0) int angle) {
		@Pc(3) int sin = SIN[angle];
		@Pc(7) int cos = COS[angle];
		for (@Pc(9) int v = 0; v < this.vertexCount; v++) {
			@Pc(29) int temp = this.vertexY[v] * cos - this.vertexZ[v] * sin >> 16;
			this.vertexZ[v] = this.vertexY[v] * sin + this.vertexZ[v] * cos >> 16;
			this.vertexY[v] = temp;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "j", descriptor = "()V")
	private void invalidate() {
		this.vertexNormals = null;
		this.mergedNormals = null;
		this.triangleNormals = null;
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!gb", name = "c", descriptor = "(IIIII)Lclient!ak;")
	public final Model createModel(@OriginalArg(0) int ambient, @OriginalArg(1) int contrast, @OriginalArg(2) int lightX, @OriginalArg(3) int lightY, @OriginalArg(4) int lightZ) {
		if (GlRenderer.enabled) {
			@Pc(9) GlModel model = new GlModel(this, ambient, contrast, true);
			model.createBones();
			return model;
		} else {
			return new SoftwareModel(this, ambient, contrast, lightX, lightY, lightZ);
		}
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}

		return this.minY;
	}

	@OriginalMember(owner = "client!gb", name = "k", descriptor = "()V")
	public final void resetBones() {
		this.vertexBones = null;
		this.triangleBones = null;
		this.boneVertices = null;
		this.boneTriangles = null;
	}

	@OriginalMember(owner = "client!gb", name = "a", descriptor = "(Lclient!th;IIIZ)V")
	@Override
	public final void mergeNormals(@OriginalArg(0) Entity other, @OriginalArg(1) int offsetX, @OriginalArg(2) int offsetY, @OriginalArg(3) int offsetZ, @OriginalArg(4) boolean markTriangles) {
		@Pc(2) RawModel m = (RawModel) other;
		m.calculateBounds();
		m.calculateNormals();
		mergeGeneration++;
		@Pc(12) int mergeCount = 0;
		@Pc(15) int[] otherVertexX = m.vertexX;
		@Pc(18) int otherVertexCount = m.vertexCount;
		@Pc(20) int i;
		for (i = 0; i < this.vertexCount; i++) {
			@Pc(29) VertexNormal normal = this.vertexNormals[i];
			if (normal.magnitude != 0) {
				@Pc(40) int adjustedY = this.vertexY[i] - offsetY;
				if (adjustedY >= m.minY && adjustedY <= m.maxY) {
					@Pc(56) int adjustedX = this.vertexX[i] - offsetX;
					if (adjustedX >= m.minX && adjustedX <= m.maxX) {
						@Pc(72) int adjustedZ = this.vertexZ[i] - offsetZ;
						if (adjustedZ >= m.minZ && adjustedZ <= m.maxZ) {
							for (@Pc(83) int j = 0; j < otherVertexCount; j++) {
								@Pc(91) VertexNormal otherNormal = m.vertexNormals[j];
								if (adjustedX == otherVertexX[j] && adjustedZ == m.vertexZ[j] && adjustedY == m.vertexY[j] && otherNormal.magnitude != 0) {
									if (this.mergedNormals == null) {
										this.mergedNormals = new VertexNormal[this.vertexCount];
									}
									if (m.mergedNormals == null) {
										m.mergedNormals = new VertexNormal[otherVertexCount];
									}
									@Pc(131) VertexNormal merged = this.mergedNormals[i];
									if (merged == null) {
										merged = this.mergedNormals[i] = new VertexNormal(normal);
									}
									@Pc(148) VertexNormal otherMerged = m.mergedNormals[j];
									if (otherMerged == null) {
										otherMerged = m.mergedNormals[j] = new VertexNormal(otherNormal);
									}
									merged.x += otherNormal.x;
									merged.y += otherNormal.y;
									merged.z += otherNormal.z;
									merged.magnitude += otherNormal.magnitude;
									otherMerged.x += normal.x;
									otherMerged.y += normal.y;
									otherMerged.z += normal.z;
									otherMerged.magnitude += normal.magnitude;
									mergeCount++;
									mergedVertexTagsA[i] = mergeGeneration;
									mergedVertexTagsB[j] = mergeGeneration;
								}
							}
						}
					}
				}
			}
		}
		if (mergeCount < 3 || !markTriangles) {
			return;
		}
		for (i = 0; i < this.triangleCount; i++) {
			if (mergedVertexTagsA[this.triangleVertexA[i]] == mergeGeneration && mergedVertexTagsA[this.triangleVertexB[i]] == mergeGeneration && mergedVertexTagsA[this.triangleVertexC[i]] == mergeGeneration) {
				if (this.triangleInfo == null) {
					this.triangleInfo = new byte[this.triangleCount];
				}
				this.triangleInfo[i] = 2;
			}
		}
		for (i = 0; i < m.triangleCount; i++) {
			if (mergedVertexTagsB[m.triangleVertexA[i]] == mergeGeneration && mergedVertexTagsB[m.triangleVertexB[i]] == mergeGeneration && mergedVertexTagsB[m.triangleVertexC[i]] == mergeGeneration) {
				if (m.triangleInfo == null) {
					m.triangleInfo = new byte[m.triangleCount];
				}
				m.triangleInfo[i] = 2;
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "d", descriptor = "(I)V")
	public final void rotate256() {
		@Pc(3) int sin = SIN[256];
		@Pc(7) int cos = COS[256];
		for (@Pc(9) int i = 0; i < this.vertexCount; i++) {
			@Pc(29) int y = this.vertexZ[i] * sin + this.vertexX[i] * cos >> 16;
			this.vertexZ[i] = this.vertexZ[i] * cos - this.vertexX[i] * sin >> 16;
			this.vertexX[i] = y;
		}
		this.invalidate();
	}

	@OriginalMember(owner = "client!gb", name = "l", descriptor = "()V")
	public final void createBones() {
		@Pc(5) int[] bonesLen;
		@Pc(7) int maxBone;
		@Pc(22) int temp;
		@Pc(9) int i;
		@Pc(18) int bone;
		if (this.vertexBones != null) {
			bonesLen = new int[256];
			maxBone = 0;
			for (i = 0; i < this.vertexCount; i++) {
				bone = this.vertexBones[i];
				temp = bonesLen[bone]++;
				if (bone > maxBone) {
					maxBone = bone;
				}
			}
			this.boneVertices = new int[maxBone + 1][];
			for (i = 0; i <= maxBone; i++) {
				this.boneVertices[i] = new int[bonesLen[i]];
				bonesLen[i] = 0;
			}
			i = 0;
			while (i < this.vertexCount) {
				bone = this.vertexBones[i];
				this.boneVertices[bone][bonesLen[bone]++] = i++;
			}
			this.vertexBones = null;
		}
		if (this.triangleBones == null) {
			return;
		}
		bonesLen = new int[256];
		maxBone = 0;
		for (i = 0; i < this.triangleCount; i++) {
			bone = this.triangleBones[i];
			temp = bonesLen[bone]++;
			if (bone > maxBone) {
				maxBone = bone;
			}
		}
		this.boneTriangles = new int[maxBone + 1][];
		for (i = 0; i <= maxBone; i++) {
			this.boneTriangles[i] = new int[bonesLen[i]];
			bonesLen[i] = 0;
		}
		i = 0;
		while (i < this.triangleCount) {
			bone = this.triangleBones[i];
			this.boneTriangles[bone][bonesLen[bone]++] = i++;
		}
		this.triangleBones = null;
	}

	@OriginalMember(owner = "client!gb", name = "d", descriptor = "(III)V")
	public final void rotateXYZ(@OriginalArg(0) int rotX, @OriginalArg(1) int rotY, @OriginalArg(2) int rotZ) {
		@Pc(5) int sin;
		@Pc(9) int cos;
		@Pc(11) int v;
		@Pc(31) int temp;
		if (rotZ != 0) {
			sin = SIN[rotZ];
			cos = COS[rotZ];
			for (v = 0; v < this.vertexCount; v++) {
				temp = this.vertexY[v] * sin + this.vertexX[v] * cos >> 16;
				this.vertexY[v] = this.vertexY[v] * cos - this.vertexX[v] * sin >> 16;
				this.vertexX[v] = temp;
			}
		}
		if (rotX != 0) {
			sin = SIN[rotX];
			cos = COS[rotX];
			for (v = 0; v < this.vertexCount; v++) {
				temp = this.vertexY[v] * cos - this.vertexZ[v] * sin >> 16;
				this.vertexZ[v] = this.vertexY[v] * sin + this.vertexZ[v] * cos >> 16;
				this.vertexY[v] = temp;
			}
		}
		if (rotY == 0) {
			return;
		}
		sin = SIN[rotY];
		cos = COS[rotY];
		for (v = 0; v < this.vertexCount; v++) {
			temp = this.vertexZ[v] * sin + this.vertexX[v] * cos >> 16;
			this.vertexZ[v] = this.vertexZ[v] * cos - this.vertexX[v] * sin >> 16;
			this.vertexX[v] = temp;
		}
	}

	@OriginalMember(owner = "client!gb", name = "e", descriptor = "(III)I")
	public final int addOrReuseGroundVertex(@OriginalArg(0) int x, @OriginalArg(2) int z) {
		for (@Pc(1) int i = 0; i < this.vertexCount; i++) {
			if (this.vertexX[i] == x && this.vertexY[i] == 0 && this.vertexZ[i] == z) {
				return i;
			}
		}
		this.vertexX[this.vertexCount] = x;
		this.vertexY[this.vertexCount] = 0;
		this.vertexZ[this.vertexCount] = z;
		return this.vertexCount++;
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "(SS)V")
	public final void recolor(@OriginalArg(0) short src, @OriginalArg(1) short dest) {
		for (@Pc(1) int i = 0; i < this.triangleCount; i++) {
			if (this.triangleColors[i] == src) {
				this.triangleColors[i] = dest;
			}
		}
	}

	@OriginalMember(owner = "client!gb", name = "b", descriptor = "([B)V")
	private void decodeOld(@OriginalArg(0) byte[] src) {
		@Pc(1) boolean hasTriangleInfo = false;
		@Pc(3) boolean hasTextures = false;

		@Pc(8) Buffer buffer1 = new Buffer(src);
		@Pc(13) Buffer buffer2 = new Buffer(src);
		@Pc(18) Buffer buffer3 = new Buffer(src);
		@Pc(23) Buffer buffer4 = new Buffer(src);
		@Pc(28) Buffer buffer5 = new Buffer(src);
		buffer1.offset = src.length - 18;

		@Pc(38) int vertexCount = buffer1.g2();
		@Pc(42) int triangleCount = buffer1.g2();
		@Pc(46) int texturedCount = buffer1.g1();

		@Pc(50) int hasInfo = buffer1.g1();
		@Pc(54) int hasPriorities = buffer1.g1();
		@Pc(58) int hasAlpha = buffer1.g1();
		@Pc(62) int hasTriangleBones = buffer1.g1();
		@Pc(66) int hasVertexBones = buffer1.g1();

		@Pc(70) int dxDataLength = buffer1.g2();
		@Pc(74) int dyDataLength = buffer1.g2();
		@Pc(78) int dzDataLength = buffer1.g2();
		@Pc(82) int vertexIndexDataLength = buffer1.g2();

		@Pc(90) int offset = vertexCount;

		@Pc(92) int triangleTypeDataOffset = offset;
		offset += triangleCount;

		@Pc(98) int trianglePriorityDataOffset = offset;
		if (hasPriorities == 255) {
			offset += triangleCount;
		}

		@Pc(107) int triangleBonesDataOffset = offset;
		if (hasTriangleBones == 1) {
			offset += triangleCount;
		}

		@Pc(116) int triangleInfoDataOffset = offset;
		if (hasInfo == 1) {
			offset += triangleCount;
		}

		@Pc(125) int vertexBonesOffset = offset;
		if (hasVertexBones == 1) {
			offset += vertexCount;
		}

		@Pc(134) int triangleAlphaDataOffset = offset;
		if (hasAlpha == 1) {
			offset += triangleCount;
		}

		@Pc(143) int vertexIndexDataOffset = offset;
		offset += vertexIndexDataLength;

		@Pc(149) int triangleColorDataOffset = offset;
		offset += triangleCount * 2;

		@Pc(157) int pmnDataOffset = offset;
		offset += texturedCount * 6;

		@Pc(165) int dxDataOffset = offset;
		offset += dxDataLength;

		@Pc(171) int dyDataOffset = offset;
		offset += dyDataLength;

		@Pc(171) int dzDataOffset = offset;
		// offset += dzDataLength;

		this.vertexCount = vertexCount;
		this.triangleCount = triangleCount;
		this.texturedCount = texturedCount;

		this.vertexX = new int[vertexCount];
		this.vertexY = new int[vertexCount];
		this.vertexZ = new int[vertexCount];

		this.triangleVertexA = new int[triangleCount];
		this.triangleVertexB = new int[triangleCount];
		this.triangleVertexC = new int[triangleCount];

		if (texturedCount > 0) {
			this.textureTypes = new byte[texturedCount];
			this.textureFacesP = new short[texturedCount];
			this.textureFacesM = new short[texturedCount];
			this.textureFacesN = new short[texturedCount];
		}

		if (hasVertexBones == 1) {
			this.vertexBones = new int[vertexCount];
		}

		if (hasInfo == 1) {
			this.triangleInfo = new byte[triangleCount];
			this.triangleTextureIndex = new byte[triangleCount];
			this.triangleTextures = new short[triangleCount];
		}

		if (hasPriorities == 255) {
			this.trianglePriorities = new byte[triangleCount];
		} else {
			this.priority = (byte) hasPriorities;
		}

		if (hasAlpha == 1) {
			this.triangleAlpha = new byte[triangleCount];
		}

		if (hasTriangleBones == 1) {
			this.triangleBones = new int[triangleCount];
		}

		this.triangleColors = new short[triangleCount];

		buffer1.offset = 0;
		buffer2.offset = dxDataOffset;
		buffer3.offset = dyDataOffset;
		buffer4.offset = dzDataOffset;
		buffer5.offset = vertexBonesOffset;

		@Pc(301) int prevVertexX = 0;
		@Pc(303) int prevVertexY = 0;
		@Pc(305) int prevVertexZ = 0;

		for (int v = 0; v < vertexCount; v++) {
			int flags = buffer1.g1();

			int dx = 0;
			if ((flags & 0x1) != 0) {
				dx = buffer2.gsmart();
			}

			int dy = 0;
			if ((flags & 0x2) != 0) {
				dy = buffer3.gsmart();
			}

			int dz = 0;
			if ((flags & 0x4) != 0) {
				dz = buffer4.gsmart();
			}

			this.vertexX[v] = prevVertexX + dx;
			this.vertexY[v] = prevVertexY + dy;
			this.vertexZ[v] = prevVertexZ + dz;

			prevVertexX = this.vertexX[v];
			prevVertexY = this.vertexY[v];
			prevVertexZ = this.vertexZ[v];

			if (hasVertexBones == 1) {
				this.vertexBones[v] = buffer5.g1();
			}
		}

		buffer1.offset = triangleColorDataOffset;
		buffer2.offset = triangleInfoDataOffset;
		buffer3.offset = trianglePriorityDataOffset;
		buffer4.offset = triangleAlphaDataOffset;
		buffer5.offset = triangleBonesDataOffset;

		for (int t = 0; t < triangleCount; t++) {
			this.triangleColors[t] = (short) buffer1.g2();

			if (hasInfo == 1) {
				int flags = buffer2.g1();
				if ((flags & 0x1) == 1) {
					this.triangleInfo[t] = 1;
					hasTriangleInfo = true;
				} else {
					this.triangleInfo[t] = 0;
				}

				if ((flags & 0x2) == 2) {
					this.triangleTextureIndex[t] = (byte) (flags >> 2);
					this.triangleTextures[t] = this.triangleColors[t];
					this.triangleColors[t] = 127;
					if (this.triangleTextures[t] != -1) {
						hasTextures = true;
					}
				} else {
					this.triangleTextureIndex[t] = -1;
					this.triangleTextures[t] = -1;
				}
			}

			if (hasPriorities == 255) {
				this.trianglePriorities[t] = buffer3.g1b();
			}

			if (hasAlpha == 1) {
				this.triangleAlpha[t] = buffer4.g1b();
			}

			if (hasTriangleBones == 1) {
				this.triangleBones[t] = buffer5.g1();
			}
		}

		buffer1.offset = vertexIndexDataOffset;
		buffer2.offset = triangleTypeDataOffset;

		int a = 0;
		int b = 0;
		int c = 0;
		int last = 0;

		for (int t = 0; t < triangleCount; t++) {
			int type = buffer2.g1();
			if (type == 1) {
				a = buffer1.gsmart() + last;
				b = buffer1.gsmart() + a;
				c = buffer1.gsmart() + b;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 2) {
				b = c;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 3) {
				a = c;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b;
				this.triangleVertexC[t] = c;
			} else if (type == 4) {
				int b0 = a;
				a = b;
				b = b0;
				c = buffer1.gsmart() + last;
				last = c;
				this.triangleVertexA[t] = a;
				this.triangleVertexB[t] = b0;
				this.triangleVertexC[t] = c;
			}
		}

		buffer1.offset = pmnDataOffset;

		for (int t = 0; t < texturedCount; t++) {
			this.textureTypes[t] = 0;
			this.textureFacesP[t] = (short) buffer1.g2();
			this.textureFacesM[t] = (short) buffer1.g2();
			this.textureFacesN[t] = (short) buffer1.g2();
		}

		if (this.triangleTextureIndex != null) {
			@Pc(721) boolean hasFaceTextures = false;
			for (int i = 0; i < triangleCount; i++) {
				int index = this.triangleTextureIndex[i] & 0xFF;
				if (index != 255) {
					if ((this.textureFacesP[index] & 0xFFFF) == this.triangleVertexA[i] && (this.textureFacesM[index] & 0xFFFF) == this.triangleVertexB[i] && (this.textureFacesN[index] & 0xFFFF) == this.triangleVertexC[i]) {
						this.triangleTextureIndex[i] = -1;
					} else {
						hasFaceTextures = true;
					}
				}
			}

			if (!hasFaceTextures) {
				this.triangleTextureIndex = null;
			}
		}

		if (!hasTextures) {
			this.triangleTextures = null;
		}

		if (!hasTriangleInfo) {
			this.triangleInfo = null;
		}
	}

	@OriginalMember(owner = "client!gb", name = "m", descriptor = "()V")
	public final void rotateCounterClockwiseXY() {
		for (@Pc(1) int v = 0; v < this.vertexCount; v++) {
			@Pc(10) int temp = this.vertexZ[v];
			this.vertexZ[v] = this.vertexX[v];
			this.vertexX[v] = -temp;
		}
		this.invalidate();
	}
}
