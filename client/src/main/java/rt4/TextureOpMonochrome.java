package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fn")
public final class TextureOpMonochrome extends TextureOp {

	@OriginalMember(owner = "client!fn", name = "<init>", descriptor = "()V")
	public TextureOpMonochrome() {
		super(1, true);
	}

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(30) int[][] childOutput = this.getChildColorOutput(row, 0);
			@Pc(34) int[] redChannel = childOutput[0];
			@Pc(38) int[] blueChannel = childOutput[2];
			@Pc(42) int[] greenChannel = childOutput[1];
			for (@Pc(44) int i = 0; i < Texture.width; i++) {
				output[i] = (blueChannel[i] + redChannel[i] + greenChannel[i]) / 3;
			}
		}
		return output;
	}
}
