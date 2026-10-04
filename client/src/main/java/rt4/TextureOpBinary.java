package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ca")
public final class TextureOpBinary extends TextureOp {

	@OriginalMember(owner = "client!ca", name = "T", descriptor = "I")
	private int maxValue = 4096;

	@OriginalMember(owner = "client!ca", name = "P", descriptor = "I")
	private int minValue = 0;

	@OriginalMember(owner = "client!ca", name = "<init>", descriptor = "()V")
	public TextureOpBinary() {
		super(1, true);
	}

	@OriginalMember(owner = "client!ca", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(29) int[] childOutput = this.getChildMonochromeOutput(0, row);
			for (@Pc(31) int i = 0; i < Texture.width; i++) {
				@Pc(38) int value = childOutput[i];
				output[i] = this.minValue <= value && value <= this.maxValue ? 4096 : 0;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ca", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.minValue = buffer.g2();
		} else if (opcode == 1) {
			this.maxValue = buffer.g2();
		}
	}
}
