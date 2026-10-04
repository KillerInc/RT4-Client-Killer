package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mi")
public final class TextureOpMonochromeFill extends TextureOp {

	@OriginalMember(owner = "client!mi", name = "bb", descriptor = "I")
	private int fillValue;

	@OriginalMember(owner = "client!mi", name = "<init>", descriptor = "(I)V")
	private TextureOpMonochromeFill(@OriginalArg(0) int value) {
		super(0, true);
		this.fillValue = 4096;
		this.fillValue = value;
	}

	@OriginalMember(owner = "client!mi", name = "<init>", descriptor = "()V")
	public TextureOpMonochromeFill() {
		this(4096);
	}

	@OriginalMember(owner = "client!mi", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.fillValue = (buffer.g1() << 12) / 255;
		}
	}

	@OriginalMember(owner = "client!mi", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(17) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			ArrayUtils.fill(output, 0, Texture.width, this.fillValue);
		}
		return output;
	}
}
