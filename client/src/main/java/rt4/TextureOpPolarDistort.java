package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ke")
public final class TextureOpPolarDistort extends TextureOp {

	@OriginalMember(owner = "client!ke", name = "V", descriptor = "I")
	private int amplitude = 32768;

	@OriginalMember(owner = "client!ke", name = "<init>", descriptor = "()V")
	public TextureOpPolarDistort() {
		super(3, false);
	}

	@OriginalMember(owner = "client!ke", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(17) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(28) int[] angleInput = this.getChildMonochromeOutput(1, row);
			@Pc(34) int[] radiusInput = this.getChildMonochromeOutput(2, row);
			@Pc(38) int[] destB = output[2];
			@Pc(42) int[] destG = output[1];
			@Pc(46) int[] destR = output[0];
			for (@Pc(48) int col = 0; col < Texture.width; col++) {
				@Pc(61) int angle = angleInput[col] * 255 >> 12 & 0xFF;
				@Pc(70) int radius = radiusInput[col] * this.amplitude >> 12;
				@Pc(78) int dx = radius * TextureOp.COSINE[angle] >> 12;
				@Pc(86) int dy = TextureOp.SINE[angle] * radius >> 12;
				@Pc(94) int srcX = (dx >> 12) + col & Texture.widthMask;
				@Pc(103) int srcY = Texture.heightMask & row + (dy >> 12);
				@Pc(109) int[][] srcColor = this.getChildColorOutput(srcY, 0);
				destR[col] = srcColor[0][srcX];
				destG[col] = srcColor[1][srcX];
				destB[col] = srcColor[2][srcX];
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ke", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(29) int[] angleInput = this.getChildMonochromeOutput(1, row);
			@Pc(35) int[] radiusInput = this.getChildMonochromeOutput(2, row);
			for (@Pc(37) int col = 0; col < Texture.width; col++) {
				@Pc(49) int radius = this.amplitude * radiusInput[col] >> 12;
				@Pc(57) int angle = angleInput[col] >> 4 & 0xFF;
				@Pc(65) int dx = TextureOp.COSINE[angle] * radius >> 12;
				@Pc(73) int dy = TextureOp.SINE[angle] * radius >> 12;
				@Pc(81) int srcX = Texture.widthMask & (dx >> 12) + col;
				@Pc(89) int srcY = Texture.heightMask & (dy >> 12) + row;
				@Pc(95) int[] srcRow = this.getChildMonochromeOutput(0, srcY);
				output[col] = srcRow[srcX];
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ke", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.amplitude = buf.g2() << 4;
		} else if (opcode == 1) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!ke", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		TextureOp.createTrigonometryTables();
	}
}
