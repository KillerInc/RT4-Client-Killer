package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!rj")
public final class TextureOpNoise extends TextureOp {

	@OriginalMember(owner = "client!rj", name = "<init>", descriptor = "()V")
	public TextureOpNoise() {
		super(0, true);
	}

	@OriginalMember(owner = "client!rj", name = "c", descriptor = "(III)I")
	private int hashNoise(@OriginalArg(0) int x, @OriginalArg(2) int y) {
		@Pc(19) int hash = y + x * 57;
		@Pc(25) int shifted = hash ^ hash << 1;
		return 4096 - (shifted * (shifted * shifted * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE) / 262144;
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(15) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(27) int rowFraction = Texture.heightFractions[row];
			for (@Pc(29) int i = 0; i < Texture.width; i++) {
				output[i] = this.hashNoise(rowFraction, Texture.widthFractions[i]) % 4096;
			}
		}
		return output;
	}
}
