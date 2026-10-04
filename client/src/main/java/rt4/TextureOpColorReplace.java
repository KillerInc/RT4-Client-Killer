package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!sk")
public final class TextureOpColorReplace extends TextureOp {

	@OriginalMember(owner = "client!sk", name = "Y", descriptor = "I")
	private int greenMultiplier = 4096;

	@OriginalMember(owner = "client!sk", name = "X", descriptor = "I")
	private int blueMultiplier = 4096;

	@OriginalMember(owner = "client!sk", name = "fb", descriptor = "I")
	private int redMultiplier = 4096;

	@OriginalMember(owner = "client!sk", name = "W", descriptor = "I")
	private int tolerance = 409;

	@OriginalMember(owner = "client!sk", name = "V", descriptor = "[I")
	private final int[] targetColor = new int[3];

	@OriginalMember(owner = "client!sk", name = "<init>", descriptor = "()V")
	public TextureOpColorReplace() {
		super(1, false);
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.tolerance = buf.g2();
		} else if (opcode == 1) {
			this.blueMultiplier = buf.g2();
		} else if (opcode == 2) {
			this.greenMultiplier = buf.g2();
		} else if (opcode == 3) {
			this.redMultiplier = buf.g2();
		} else if (opcode == 4) {
			@Pc(65) int packed = buf.g3();
			this.targetColor[2] = packed >> 12 & 0x0;
			this.targetColor[1] = packed >> 4 & 0xFF0;
			this.targetColor[0] = (packed & 0xFF0000) << 4;
		}
	}

	@OriginalMember(owner = "client!sk", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(15) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(25) int[][] input = this.getChildColorOutput(row, 0);
			@Pc(29) int[] srcR = input[0];
			@Pc(33) int[] srcG = input[1];
			@Pc(37) int[] srcB = input[2];
			@Pc(41) int[] destR = output[0];
			@Pc(45) int[] destG = output[1];
			@Pc(49) int[] destB = output[2];
			for (@Pc(51) int i = 0; i < Texture.width; i++) {
				@Pc(58) int r = srcR[i];
				@Pc(66) int diff = r - this.targetColor[0];
				if (diff < 0) {
					diff = -diff;
				}
				if (this.tolerance < diff) {
					destR[i] = r;
					destG[i] = srcG[i];
					destB[i] = srcB[i];
				} else {
					@Pc(100) int g = srcG[i];
					diff = g - this.targetColor[1];
					if (diff < 0) {
						diff = -diff;
					}
					if (diff > this.tolerance) {
						destR[i] = r;
						destG[i] = g;
						destB[i] = srcB[i];
					} else {
						@Pc(141) int b = srcB[i];
						diff = b - this.targetColor[2];
						if (diff < 0) {
							diff = -diff;
						}
						if (diff <= this.tolerance) {
							destR[i] = this.redMultiplier * r >> 12;
							destG[i] = this.greenMultiplier * g >> 12;
							destB[i] = this.blueMultiplier * b >> 12;
						} else {
							destR[i] = r;
							destG[i] = g;
							destB[i] = b;
						}
					}
				}
			}
		}
		return output;
	}
}
