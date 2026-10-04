package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!bl")
public final class TextureOpInterpolate extends TextureOp {

	@OriginalMember(owner = "client!bl", name = "<init>", descriptor = "()V")
	public TextureOpInterpolate() {
		super(3, false);
	}

	@OriginalMember(owner = "client!bl", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(16) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(27) int[] mask = this.getChildMonochromeOutput(2, row);
			@Pc(33) int[][] inputA = this.getChildColorOutput(row, 0);
			@Pc(39) int[][] inputB = this.getChildColorOutput(row, 1);
			@Pc(43) int[] destB = output[2];
			@Pc(47) int[] destG = output[1];
			@Pc(51) int[] srcAR = inputA[0];
			@Pc(55) int[] srcAG = inputA[1];
			@Pc(59) int[] destR = output[0];
			@Pc(63) int[] srcBR = inputB[0];
			@Pc(67) int[] srcAB = inputA[2];
			@Pc(71) int[] srcBB = inputB[2];
			@Pc(75) int[] srcBG = inputB[1];
			for (@Pc(77) int col = 0; col < Texture.width; col++) {
				@Pc(88) int blend = mask[col];
				if (blend == 4096) {
					destR[col] = srcAR[col];
					destG[col] = srcAG[col];
					destB[col] = srcAB[col];
				} else if (blend == 0) {
					destR[col] = srcBR[col];
					destG[col] = srcBG[col];
					destB[col] = srcBB[col];
				} else {
					@Pc(99) int invBlend = 4096 - blend;
					destR[col] = invBlend * srcBR[col] + blend * srcAR[col] >> 12;
					destG[col] = invBlend * srcBG[col] + srcAG[col] * blend >> 12;
					destB[col] = srcBB[col] * invBlend + srcAB[col] * blend >> 12;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!bl", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!bl", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(11) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(30) int[] inputA = this.getChildMonochromeOutput(0, row);
			@Pc(36) int[] inputB = this.getChildMonochromeOutput(1, row);
			@Pc(42) int[] mask = this.getChildMonochromeOutput(2, row);
			for (@Pc(44) int col = 0; col < Texture.width; col++) {
				@Pc(55) int blend = mask[col];
				if (blend == 4096) {
					output[col] = inputA[col];
				} else if (blend == 0) {
					output[col] = inputB[col];
				} else {
					output[col] = blend * inputA[col] + (4096 - blend) * inputB[col] >> 12;
				}
			}
		}
		return output;
	}
}
