package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!tb")
public final class TextureOpInvert extends TextureOp {

	@OriginalMember(owner = "client!tb", name = "<init>", descriptor = "()V")
	public TextureOpInvert() {
		super(1, false);
	}

	@OriginalMember(owner = "client!tb", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(7) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(21) int[] childOutput = this.getChildMonochromeOutput(0, row);
			for (@Pc(23) int i = 0; i < Texture.width; i++) {
				output[i] = 4096 - childOutput[i];
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!tb", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.monochrome = buffer.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!tb", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(18) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(28) int[][] childOutput = this.getChildColorOutput(row, 0);
			@Pc(32) int[] srcBlue = childOutput[2];
			@Pc(36) int[] srcRed = childOutput[0];
			@Pc(40) int[] srcGreen = childOutput[1];
			@Pc(44) int[] dstRed = output[0];
			@Pc(48) int[] dstGreen = output[1];
			@Pc(52) int[] dstBlue = output[2];
			for (@Pc(54) int i = 0; i < Texture.width; i++) {
				dstRed[i] = 4096 - srcRed[i];
				dstGreen[i] = 4096 - srcGreen[i];
				dstBlue[i] = 4096 - srcBlue[i];
			}
		}
		return output;
	}
}
