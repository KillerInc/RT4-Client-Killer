package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fg")
public final class ShapedTile {

	@OriginalMember(owner = "client!fg", name = "i", descriptor = "[[I")
	public static final int[][] VERTEX_INDICES = new int[][]{{1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 2, 6}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 13, 14}};
	@OriginalMember(owner = "client!fg", name = "j", descriptor = "[[I")
	public static final int[][] TRIANGLE_INDICES = new int[][]{{0, 1, 2, 3, 0, 0, 1, 3}, {1, 1, 2, 3, 1, 0, 1, 3}, {0, 1, 2, 3, 1, 0, 1, 3}, {0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3}, {0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4}, {0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4}, {0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3}, {0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3}, {0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5}, {0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5}, {0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3}, {1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3}, {1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5}};
	@OriginalMember(owner = "client!fg", name = "s", descriptor = "Z")
	public boolean flat = true;

	@OriginalMember(owner = "client!fg", name = "a", descriptor = "I")
	public final int shape;

	@OriginalMember(owner = "client!fg", name = "b", descriptor = "I")
	public final int rotation;

	@OriginalMember(owner = "client!fg", name = "p", descriptor = "I")
	public final int textureId;

	@OriginalMember(owner = "client!fg", name = "k", descriptor = "I")
	public final int flatColor;

	@OriginalMember(owner = "client!fg", name = "q", descriptor = "[I")
	public final int[] vertexX;

	@OriginalMember(owner = "client!fg", name = "e", descriptor = "[I")
	public final int[] vertexY;

	@OriginalMember(owner = "client!fg", name = "h", descriptor = "[I")
	public final int[] vertexZ;

	@OriginalMember(owner = "client!fg", name = "n", descriptor = "[I")
	public final int[] triangleVertexA;

	@OriginalMember(owner = "client!fg", name = "g", descriptor = "[I")
	public final int[] triangleVertexB;

	@OriginalMember(owner = "client!fg", name = "c", descriptor = "[I")
	public final int[] triangleVertexC;

	@OriginalMember(owner = "client!fg", name = "o", descriptor = "[I")
	public final int[] triangleColorA;

	@OriginalMember(owner = "client!fg", name = "v", descriptor = "[I")
	public final int[] triangleColorB;

	@OriginalMember(owner = "client!fg", name = "u", descriptor = "[I")
	public final int[] triangleColorC;

	@OriginalMember(owner = "client!fg", name = "f", descriptor = "[I")
	public int[] triangleTextureId;

	@OriginalMember(owner = "client!fg", name = "<init>", descriptor = "(IIIIIIIIIIIIIIIIIII)V")
	public ShapedTile(@OriginalArg(0) int shape, @OriginalArg(1) int rotation, @OriginalArg(2) int overlayTexture, @OriginalArg(3) int tileX, @OriginalArg(4) int tileY, @OriginalArg(5) int heightSW, @OriginalArg(6) int heightSE, @OriginalArg(7) int heightNE, @OriginalArg(8) int heightNW, @OriginalArg(9) int colorSW, @OriginalArg(10) int colorSE, @OriginalArg(11) int colorNE, @OriginalArg(12) int colorNW, @OriginalArg(13) int overlayColorSW, @OriginalArg(14) int overlayColorSE, @OriginalArg(15) int overlayColorNE, @OriginalArg(16) int overlayColorNW, @OriginalArg(17) int textureId, @OriginalArg(18) int flatColor) {
		if (heightSW != heightSE || heightSW != heightNE || heightSW != heightNW) {
			this.flat = false;
		}
		this.shape = shape;
		this.rotation = rotation;
		this.textureId = textureId;
		this.flatColor = flatColor;
		@Pc(30) short tileSize = 128;
		@Pc(34) int halfTile = tileSize / 2;
		@Pc(38) int quarterTile = tileSize / 4;
		@Pc(44) int threeQuarterTile = tileSize * 3 / 4;
		@Pc(48) int[] vertexIndexDef = VERTEX_INDICES[shape];
		@Pc(51) int vertexCount = vertexIndexDef.length;
		this.vertexX = new int[vertexCount];
		this.vertexY = new int[vertexCount];
		this.vertexZ = new int[vertexCount];
		@Pc(66) int[] underlayVertexColors = new int[vertexCount];
		@Pc(69) int[] overlayVertexColors = new int[vertexCount];
		@Pc(73) int baseFineX = tileX * tileSize;
		@Pc(77) int baseFineZ = tileY * tileSize;
		@Pc(86) int vertexCode;
		@Pc(142) int vx;
		@Pc(144) int vz;
		@Pc(146) int vy;
		@Pc(148) int vColor;
		@Pc(150) int vOverlayColor;
		for (@Pc(79) int v = 0; v < vertexCount; v++) {
			vertexCode = vertexIndexDef[v];
			if ((vertexCode & 0x1) == 0 && vertexCode <= 8) {
				vertexCode = (vertexCode - rotation - rotation - 1 & 0x7) + 1;
			}
			if (vertexCode > 8 && vertexCode <= 12) {
				vertexCode = (vertexCode - rotation - 9 & 0x3) + 9;
			}
			if (vertexCode > 12 && vertexCode <= 16) {
				vertexCode = (vertexCode - rotation - 13 & 0x3) + 13;
			}
			if (vertexCode == 1) {
				vx = baseFineX;
				vz = baseFineZ;
				vy = heightSW;
				vColor = colorSW;
				vOverlayColor = overlayColorSW;
			} else if (vertexCode == 2) {
				vx = baseFineX + halfTile;
				vz = baseFineZ;
				vy = heightSW + heightSE >> 1;
				vColor = colorSW + colorSE >> 1;
				vOverlayColor = overlayColorSW + overlayColorSE >> 1;
			} else if (vertexCode == 3) {
				vx = baseFineX + tileSize;
				vz = baseFineZ;
				vy = heightSE;
				vColor = colorSE;
				vOverlayColor = overlayColorSE;
			} else if (vertexCode == 4) {
				vx = baseFineX + tileSize;
				vz = baseFineZ + halfTile;
				vy = heightSE + heightNE >> 1;
				vColor = colorSE + colorNE >> 1;
				vOverlayColor = overlayColorSE + overlayColorNE >> 1;
			} else if (vertexCode == 5) {
				vx = baseFineX + tileSize;
				vz = baseFineZ + tileSize;
				vy = heightNE;
				vColor = colorNE;
				vOverlayColor = overlayColorNE;
			} else if (vertexCode == 6) {
				vx = baseFineX + halfTile;
				vz = baseFineZ + tileSize;
				vy = heightNE + heightNW >> 1;
				vColor = colorNE + colorNW >> 1;
				vOverlayColor = overlayColorNE + overlayColorNW >> 1;
			} else if (vertexCode == 7) {
				vx = baseFineX;
				vz = baseFineZ + tileSize;
				vy = heightNW;
				vColor = colorNW;
				vOverlayColor = overlayColorNW;
			} else if (vertexCode == 8) {
				vx = baseFineX;
				vz = baseFineZ + halfTile;
				vy = heightNW + heightSW >> 1;
				vColor = colorNW + colorSW >> 1;
				vOverlayColor = overlayColorNW + overlayColorSW >> 1;
			} else if (vertexCode == 9) {
				vx = baseFineX + halfTile;
				vz = baseFineZ + quarterTile;
				vy = heightSW + heightSE >> 1;
				vColor = colorSW + colorSE >> 1;
				vOverlayColor = overlayColorSW + overlayColorSE >> 1;
			} else if (vertexCode == 10) {
				vx = baseFineX + threeQuarterTile;
				vz = baseFineZ + halfTile;
				vy = heightSE + heightNE >> 1;
				vColor = colorSE + colorNE >> 1;
				vOverlayColor = overlayColorSE + overlayColorNE >> 1;
			} else if (vertexCode == 11) {
				vx = baseFineX + halfTile;
				vz = baseFineZ + threeQuarterTile;
				vy = heightNE + heightNW >> 1;
				vColor = colorNE + colorNW >> 1;
				vOverlayColor = overlayColorNE + overlayColorNW >> 1;
			} else if (vertexCode == 12) {
				vx = baseFineX + quarterTile;
				vz = baseFineZ + halfTile;
				vy = heightNW + heightSW >> 1;
				vColor = colorNW + colorSW >> 1;
				vOverlayColor = overlayColorNW + overlayColorSW >> 1;
			} else if (vertexCode == 13) {
				vx = baseFineX + quarterTile;
				vz = baseFineZ + quarterTile;
				vy = heightSW;
				vColor = colorSW;
				vOverlayColor = overlayColorSW;
			} else if (vertexCode == 14) {
				vx = baseFineX + threeQuarterTile;
				vz = baseFineZ + quarterTile;
				vy = heightSE;
				vColor = colorSE;
				vOverlayColor = overlayColorSE;
			} else if (vertexCode == 15) {
				vx = baseFineX + threeQuarterTile;
				vz = baseFineZ + threeQuarterTile;
				vy = heightNE;
				vColor = colorNE;
				vOverlayColor = overlayColorNE;
			} else {
				vx = baseFineX + quarterTile;
				vz = baseFineZ + threeQuarterTile;
				vy = heightNW;
				vColor = colorNW;
				vOverlayColor = overlayColorNW;
			}
			this.vertexX[v] = vx;
			this.vertexY[v] = vy;
			this.vertexZ[v] = vz;
			underlayVertexColors[v] = vColor;
			overlayVertexColors[v] = vOverlayColor;
		}
		@Pc(534) int[] triangleIndexDef = TRIANGLE_INDICES[shape];
		vertexCode = triangleIndexDef.length / 4;
		this.triangleVertexA = new int[vertexCode];
		this.triangleVertexB = new int[vertexCode];
		this.triangleVertexC = new int[vertexCode];
		this.triangleColorA = new int[vertexCode];
		this.triangleColorB = new int[vertexCode];
		this.triangleColorC = new int[vertexCode];
		if (overlayTexture != -1) {
			this.triangleTextureId = new int[vertexCode];
		}
		vx = 0;
		for (vz = 0; vz < vertexCode; vz++) {
			vy = triangleIndexDef[vx];
			vColor = triangleIndexDef[vx + 1];
			vOverlayColor = triangleIndexDef[vx + 2];
			@Pc(599) int vertC = triangleIndexDef[vx + 3];
			vx += 4;
			if (vColor < 4) {
				vColor = vColor - rotation & 0x3;
			}
			if (vOverlayColor < 4) {
				vOverlayColor = vOverlayColor - rotation & 0x3;
			}
			if (vertC < 4) {
				vertC = vertC - rotation & 0x3;
			}
			this.triangleVertexA[vz] = vColor;
			this.triangleVertexB[vz] = vOverlayColor;
			this.triangleVertexC[vz] = vertC;
			if (vy == 0) {
				this.triangleColorA[vz] = underlayVertexColors[vColor];
				this.triangleColorB[vz] = underlayVertexColors[vOverlayColor];
				this.triangleColorC[vz] = underlayVertexColors[vertC];
				if (this.triangleTextureId != null) {
					this.triangleTextureId[vz] = -1;
				}
			} else {
				this.triangleColorA[vz] = overlayVertexColors[vColor];
				this.triangleColorB[vz] = overlayVertexColors[vOverlayColor];
				this.triangleColorC[vz] = overlayVertexColors[vertC];
				if (this.triangleTextureId != null) {
					this.triangleTextureId[vz] = overlayTexture;
				}
			}
		}
		vz = heightSW;
		vy = heightSE;
		if (heightSE < heightSW) {
			vz = heightSE;
		}
		if (heightSE > heightSE) {
			vy = heightSE;
		}
		if (heightNE < vz) {
			vz = heightNE;
		}
		if (heightNE > heightSE) {
			vy = heightNE;
		}
		if (heightNW < vz) {
		}
		if (heightNW > vy) {
		}
	}
}
