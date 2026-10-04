package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!pg")
public final class TextureOpEmboss extends TextureOp {

	@OriginalMember(owner = "client!pg", name = "U", descriptor = "I")
	private int bumpStrength = 4096;

	@OriginalMember(owner = "client!pg", name = "<init>", descriptor = "()V")
	public TextureOpEmboss() {
		super(1, true);
	}

	@OriginalMember(owner = "client!pg", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(34) int[] prevRow = this.getChildMonochromeOutput(0, row - 1 & Texture.heightMask);
			@Pc(40) int[] currRow = this.getChildMonochromeOutput(0, row);
			@Pc(50) int[] nextRow = this.getChildMonochromeOutput(0, Texture.heightMask & row + 1);
			for (@Pc(52) int i = 0; i < Texture.width; i++) {
				@Pc(67) int dy = (nextRow[i] - prevRow[i]) * this.bumpStrength;
				@Pc(87) int dx = this.bumpStrength * (currRow[Texture.widthMask & i + 1] - currRow[i - 1 & Texture.widthMask]);
				@Pc(91) int dxNorm = dx >> 12;
				@Pc(95) int dyNorm = dy >> 12;
				@Pc(101) int dxSq = dxNorm * dxNorm >> 12;
				@Pc(107) int dySq = dyNorm * dyNorm >> 12;
				@Pc(121) int magnitude = (int) (Math.sqrt((float) (dySq + dxSq + 4096) / 4096.0F) * 4096.0D);
				@Pc(130) int lighting = magnitude == 0 ? 0 : 16777216 / magnitude;
				output[i] = 4096 - lighting;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!pg", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.bumpStrength = buffer.g2();
		}
	}
}
