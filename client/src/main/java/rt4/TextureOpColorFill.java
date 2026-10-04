package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fm")
public final class TextureOpColorFill extends TextureOp {

	@OriginalMember(owner = "client!fm", name = "P", descriptor = "I")
	private int red;

	@OriginalMember(owner = "client!fm", name = "X", descriptor = "I")
	private int green;

	@OriginalMember(owner = "client!fm", name = "Z", descriptor = "I")
	private int blue;

	@OriginalMember(owner = "client!fm", name = "<init>", descriptor = "(I)V")
	private TextureOpColorFill(@OriginalArg(0) int color) {
		super(0, false);
		this.setColor(color);
	}

	@OriginalMember(owner = "client!fm", name = "<init>", descriptor = "()V")
	public TextureOpColorFill() {
		this(0);
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(BI)V")
	private void setColor(@OriginalArg(1) int color) {
		this.green = color >> 4 & 0xFF0;
		this.blue = (color & 0xFF) << 4;
		this.red = color >> 12 & 0xFF0;
	}

	@OriginalMember(owner = "client!fm", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(22) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(31) int[] redChannel = output[0];
			@Pc(35) int[] greenChannel = output[1];
			@Pc(39) int[] blueChannel = output[2];
			for (@Pc(41) int i = 0; i < Texture.width; i++) {
				redChannel[i] = this.red;
				greenChannel[i] = this.green;
				blueChannel[i] = this.blue;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.setColor(buffer.g3());
		}
	}
}
