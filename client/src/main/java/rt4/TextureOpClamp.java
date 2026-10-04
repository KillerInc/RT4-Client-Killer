package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!aj")
public final class TextureOpClamp extends TextureOp {

	@OriginalMember(owner = "client!aj", name = "V", descriptor = "I")
	private int lowerBound = 0;

	@OriginalMember(owner = "client!aj", name = "ab", descriptor = "I")
	private int upperBound = 4096;

	@OriginalMember(owner = "client!aj", name = "<init>", descriptor = "()V")
	public TextureOpClamp() {
		super(1, false);
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.lowerBound = buf.g2();
		} else if (opcode == 1) {
			this.upperBound = buf.g2();
		} else if (opcode == 2) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!aj", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(22) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(32) int[][] input = this.getChildColorOutput(row, 0);
			@Pc(36) int[] srcG = input[1];
			@Pc(40) int[] srcB = input[2];
			@Pc(44) int[] srcR = input[0];
			@Pc(48) int[] destR = output[0];
			@Pc(52) int[] destG = output[1];
			@Pc(56) int[] destB = output[2];
			for (@Pc(58) int i = 0; i < Texture.width; i++) {
				@Pc(69) int g = srcG[i];
				@Pc(73) int r = srcR[i];
				@Pc(77) int b = srcB[i];
				if (this.lowerBound > r) {
					destR[i] = this.lowerBound;
				} else if (r > this.upperBound) {
					destR[i] = this.upperBound;
				} else {
					destR[i] = r;
				}
				if (this.lowerBound > g) {
					destG[i] = this.lowerBound;
				} else if (g <= this.upperBound) {
					destG[i] = g;
				} else {
					destG[i] = this.upperBound;
				}
				if (b < this.lowerBound) {
					destB[i] = this.lowerBound;
				} else if (this.upperBound >= b) {
					destB[i] = b;
				} else {
					destB[i] = this.upperBound;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(29) int[] input = this.getChildMonochromeOutput(0, row);
			for (@Pc(31) int i = 0; i < Texture.width; i++) {
				@Pc(38) int value = input[i];
				if (this.lowerBound > value) {
					output[i] = this.lowerBound;
				} else if (this.upperBound >= value) {
					output[i] = value;
				} else {
					output[i] = this.upperBound;
				}
			}
		}
		return output;
	}
}
