package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mg")
public final class TextureOpColorMultiply extends TextureOp {

	@OriginalMember(owner = "client!mg", name = "R", descriptor = "I")
	private int greenMultiplier = 4096;

	@OriginalMember(owner = "client!mg", name = "Z", descriptor = "I")
	private int redMultiplier = 4096;

	@OriginalMember(owner = "client!mg", name = "X", descriptor = "I")
	private int blueMultiplier = 4096;

	@OriginalMember(owner = "client!mg", name = "<init>", descriptor = "()V")
	public TextureOpColorMultiply() {
		super(1, false);
	}

	@OriginalMember(owner = "client!mg", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.redMultiplier = buffer.g2();
		} else if (opcode == 1) {
			this.greenMultiplier = buffer.g2();
		} else if (opcode == 2) {
			this.blueMultiplier = buffer.g2();
		}
	}

	@OriginalMember(owner = "client!mg", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(16) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(27) int[][] childOutput = this.getChildColorOutput(row, 0);
			@Pc(31) int[] srcRed = childOutput[0];
			@Pc(35) int[] srcGreen = childOutput[1];
			@Pc(39) int[] srcBlue = childOutput[2];
			@Pc(43) int[] dstGreen = output[1];
			@Pc(47) int[] dstRed = output[0];
			@Pc(51) int[] dstBlue = output[2];
			for (@Pc(53) int i = 0; i < Texture.width; i++) {
				@Pc(64) int red = srcRed[i];
				@Pc(68) int green = srcGreen[i];
				@Pc(72) int blue = srcBlue[i];
				if (red == blue && green == blue) {
					dstRed[i] = this.redMultiplier * red >> 12;
					dstGreen[i] = blue * this.greenMultiplier >> 12;
					dstBlue[i] = green * this.blueMultiplier >> 12;
				} else {
					dstRed[i] = this.redMultiplier;
					dstGreen[i] = this.greenMultiplier;
					dstBlue[i] = this.blueMultiplier;
				}
			}
		}
		return output;
	}
}
