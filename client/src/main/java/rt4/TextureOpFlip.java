package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ej")
public final class TextureOpFlip extends TextureOp {

	@OriginalMember(owner = "client!ej", name = "P", descriptor = "Z")
	private boolean flipHorizontal = true;

	@OriginalMember(owner = "client!ej", name = "Y", descriptor = "Z")
	private boolean flipVertical = true;

	@OriginalMember(owner = "client!ej", name = "<init>", descriptor = "()V")
	public TextureOpFlip() {
		super(1, false);
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.flipHorizontal = buf.g1() == 1;
		} else if (opcode == 1) {
			this.flipVertical = buf.g1() == 1;
		} else if (opcode == 2) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!ej", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(20) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(39) int[][] srcColor = this.getChildColorOutput(this.flipVertical ? Texture.heightMask - row : row, 0);
			@Pc(43) int[] srcR = srcColor[0];
			@Pc(47) int[] srcB = srcColor[2];
			@Pc(51) int[] srcG = srcColor[1];
			@Pc(55) int[] destG = output[1];
			@Pc(59) int[] destB = output[2];
			@Pc(63) int[] destR = output[0];
			@Pc(68) int col;
			if (this.flipHorizontal) {
				for (col = 0; col < Texture.width; col++) {
					destR[col] = srcR[Texture.widthMask - col];
					destG[col] = srcG[Texture.widthMask - col];
					destB[col] = srcB[Texture.widthMask - col];
				}
			} else {
				for (col = 0; col < Texture.width; col++) {
					destR[col] = srcR[col];
					destG[col] = srcG[col];
					destB[col] = srcB[col];
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(15) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(38) int[] srcRow = this.getChildMonochromeOutput(0, this.flipVertical ? Texture.heightMask - row : row);
			if (this.flipHorizontal) {
				for (@Pc(51) int col = 0; col < Texture.width; col++) {
					output[col] = srcRow[Texture.widthMask - col];
				}
			} else {
				ArrayUtils.copy(srcRow, 0, output, 0, Texture.width);
			}
		}
		return output;
	}
}
