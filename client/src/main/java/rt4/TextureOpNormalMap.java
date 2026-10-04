package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!an")
public final class TextureOpNormalMap extends TextureOp {

	@OriginalMember(owner = "client!an", name = "R", descriptor = "I")
	private int strength = 4096;

	@OriginalMember(owner = "client!an", name = "bb", descriptor = "Z")
	private boolean biased = true;

	@OriginalMember(owner = "client!an", name = "<init>", descriptor = "()V")
	public TextureOpNormalMap() {
		super(1, false);
	}

	@OriginalMember(owner = "client!an", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.strength = buf.g2();
		} else if (opcode == 1) {
			this.biased = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!an", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(11) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(25) int[] prevRow = this.getChildMonochromeOutput(0, Texture.heightMask & row - 1);
			@Pc(31) int[] curRow = this.getChildMonochromeOutput(0, row);
			@Pc(41) int[] nextRow = this.getChildMonochromeOutput(0, row + 1 & Texture.heightMask);
			@Pc(45) int[] destR = output[0];
			@Pc(49) int[] destG = output[1];
			@Pc(53) int[] destB = output[2];
			for (@Pc(55) int col = 0; col < Texture.width; col++) {
				@Pc(70) int dy = this.strength * (nextRow[col] - prevRow[col]);
				@Pc(90) int dx = this.strength * (curRow[col + 1 & Texture.widthMask] - curRow[Texture.widthMask & col - 1]);
				@Pc(94) int dyNorm = dy >> 12;
				@Pc(98) int dxNorm = dx >> 12;
				@Pc(104) int dySq = dyNorm * dyNorm >> 12;
				@Pc(110) int dxSq = dxNorm * dxNorm >> 12;
				@Pc(124) int length = (int) (Math.sqrt((float) (dxSq + dySq + 4096) / 4096.0F) * 4096.0D);
				@Pc(131) int normalX;
				@Pc(133) int normalY;
				@Pc(129) int normalZ;
				if (length == 0) {
					normalZ = 0;
					normalX = 0;
					normalY = 0;
				} else {
					normalZ = 16777216 / length;
					normalY = dy / length;
					normalX = dx / length;
				}
				if (this.biased) {
					normalY = (normalY >> 1) + 2048;
					normalZ = (normalZ >> 1) + 2048;
					normalX = (normalX >> 1) + 2048;
				}
				destR[col] = normalX;
				destG[col] = normalY;
				destB[col] = normalZ;
			}
		}
		return output;
	}
}
