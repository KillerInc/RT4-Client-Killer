package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!sa")
public final class TextureOpDiamond extends TextureOp {

	@OriginalMember(owner = "client!sa", name = "T", descriptor = "I")
	private int borderWidth = 585;

	@OriginalMember(owner = "client!sa", name = "<init>", descriptor = "()V")
	public TextureOpDiamond() {
		super(0, true);
	}

	@OriginalMember(owner = "client!sa", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(28) int y = Texture.heightFractions[row];
			for (@Pc(30) int col = 0; col < Texture.width; col++) {
				@Pc(41) int x = Texture.widthFractions[col];
				@Pc(76) int dist;
				if (x > this.borderWidth && 4096 - this.borderWidth > x && 2048 - this.borderWidth < y && y < this.borderWidth + 2048) {
					dist = 2048 - x;
					dist = dist < 0 ? -dist : dist;
					dist <<= 0xC;
					dist /= 2048 - this.borderWidth;
					output[col] = 4096 - dist;
				} else if (x > 2048 - this.borderWidth && x < this.borderWidth + 2048) {
					dist = y - 2048;
					dist = dist >= 0 ? dist : -dist;
					dist -= this.borderWidth;
					dist <<= 0xC;
					output[col] = dist / (2048 - this.borderWidth);
				} else if (y < this.borderWidth || 4096 - this.borderWidth < y) {
					dist = x - 2048;
					@Pc(188) int absDist = dist < 0 ? -dist : dist;
					@Pc(193) int borderDist = absDist - this.borderWidth;
					@Pc(197) int scaled = borderDist << 12;
					output[col] = scaled / (2048 - this.borderWidth);
				} else if (this.borderWidth <= x && x <= 4096 - this.borderWidth) {
					output[col] = 0;
				} else {
					dist = 2048 - y;
					dist = dist < 0 ? -dist : dist;
					dist <<= 0xC;
					dist /= 2048 - this.borderWidth;
					output[col] = 4096 - dist;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!sa", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.borderWidth = buf.g2();
		}
	}
}
