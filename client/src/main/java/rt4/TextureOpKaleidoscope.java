package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!je")
public final class TextureOpKaleidoscope extends TextureOp {

	@OriginalMember(owner = "client!hd", name = "i", descriptor = "I")
	public static int mappedY = 0;
	@OriginalMember(owner = "client!vl", name = "l", descriptor = "I")
	public static int mappedX = 0;

	@OriginalMember(owner = "client!je", name = "<init>", descriptor = "()V")
	public TextureOpKaleidoscope() {
		super(1, false);
	}

	@OriginalMember(owner = "client!je", name = "a", descriptor = "(IBI)V")
	private void mapPolarSector(@OriginalArg(0) int row, @OriginalArg(2) int col) {
		@Pc(13) int x = Texture.widthFractions[col];
		@Pc(17) int y = Texture.heightFractions[row];
		@Pc(28) float angle = (float) Math.atan2(x - 2048, y - 2048);
		if ((double) angle >= -3.141592653589793D && -2.356194490192345D >= (double) angle) {
			mappedY = row;
			mappedX = col;
		} else if ((double) angle <= -1.5707963267948966D && -2.356194490192345D <= (double) angle) {
			mappedX = row;
			mappedY = col;
		} else if ((double) angle <= -0.7853981633974483D && (double) angle >= -1.5707963267948966D) {
			mappedX = Texture.width - row;
			mappedY = col;
		} else if (angle <= 0.0F && (double) angle >= -0.7853981633974483D) {
			mappedX = col;
			mappedY = Texture.height - row;
		} else if (angle >= 0.0F && (double) angle <= 0.7853981633974483D) {
			mappedX = Texture.width - col;
			mappedY = Texture.height - row;
		} else if ((double) angle >= 0.7853981633974483D && (double) angle <= 1.5707963267948966D) {
			mappedX = Texture.width - row;
			mappedY = Texture.height - col;
		} else if ((double) angle >= 1.5707963267948966D && (double) angle <= 2.356194490192345D) {
			mappedY = Texture.height - col;
			mappedX = row;
		} else if ((double) angle >= 2.356194490192345D && (double) angle <= 3.141592653589793D) {
			mappedX = Texture.width - col;
			mappedY = row;
		}
		mappedX &= Texture.widthMask;
		mappedY &= Texture.heightMask;
	}

	@OriginalMember(owner = "client!je", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!je", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			for (@Pc(26) int i = 0; i < Texture.width; i++) {
				this.mapPolarSector(row, i);
				@Pc(40) int[] input = this.getChildMonochromeOutput(0, mappedY);
				output[i] = input[mappedX];
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!je", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(15) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(28) int[] destR = output[0];
			@Pc(32) int[] destB = output[2];
			@Pc(36) int[] destG = output[1];
			for (@Pc(38) int i = 0; i < Texture.width; i++) {
				this.mapPolarSector(row, i);
				@Pc(52) int[][] input = this.getChildColorOutput(mappedY, 0);
				destR[i] = input[0][mappedX];
				destG[i] = input[1][mappedX];
				destB[i] = input[2][mappedX];
			}
		}
		return output;
	}
}
