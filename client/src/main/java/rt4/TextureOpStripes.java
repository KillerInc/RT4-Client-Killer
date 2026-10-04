package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!d")
public final class TextureOpStripes extends TextureOp {

	@OriginalMember(owner = "client!d", name = "U", descriptor = "[I")
	private int[] stripeStarts;

	@OriginalMember(owner = "client!d", name = "W", descriptor = "[I")
	private int[] stripeEnds;

	@OriginalMember(owner = "client!d", name = "Q", descriptor = "I")
	private int direction = 0;

	@OriginalMember(owner = "client!d", name = "P", descriptor = "I")
	private int stripeCount = 10;

	@OriginalMember(owner = "client!d", name = "bb", descriptor = "I")
	private int stripeWidth = 2048;

	@OriginalMember(owner = "client!d", name = "<init>", descriptor = "()V")
	public TextureOpStripes() {
		super(0, true);
	}

	@OriginalMember(owner = "client!d", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.initStripes();
	}

	@OriginalMember(owner = "client!d", name = "h", descriptor = "(I)V")
	private void initStripes() {
		@Pc(7) int offset = 0;
		this.stripeEnds = new int[this.stripeCount + 1];
		@Pc(23) int stripeStep = 4096 / this.stripeCount;
		this.stripeStarts = new int[this.stripeCount + 1];
		@Pc(37) int stripeSpan = this.stripeWidth * stripeStep >> 12;
		for (@Pc(39) int i = 0; i < this.stripeCount; i++) {
			this.stripeStarts[i] = offset;
			this.stripeEnds[i] = offset + stripeSpan;
			offset += stripeStep;
		}
		this.stripeStarts[this.stripeCount] = 4096;
		this.stripeEnds[this.stripeCount] = this.stripeEnds[0] + 4096;
	}

	@OriginalMember(owner = "client!d", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.stripeCount = buf.g1();
		} else if (opcode == 1) {
			this.stripeWidth = buf.g2();
		} else if (opcode == 2) {
			this.direction = buf.g1();
		}
	}

	@OriginalMember(owner = "client!d", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(28) int y = Texture.heightFractions[row];
			@Pc(36) int i;
			if (this.direction == 0) {
				@Pc(34) short value = 0;
				for (i = 0; i < this.stripeCount; i++) {
					if (this.stripeStarts[i] <= y && y < this.stripeStarts[i + 1]) {
						if (y < this.stripeEnds[i]) {
							value = 4096;
						}
						break;
					}
				}
				ArrayUtils.fill(output, 0, Texture.width, value);
			} else {
				for (@Pc(85) int col = 0; col < Texture.width; col++) {
					@Pc(96) int x = Texture.widthFractions[col];
					i = 0;
					@Pc(101) int coord = this.direction;
					if (coord == 1) {
						i = x;
					} else if (coord == 2) {
						i = (x + y - 4096 >> 1) + 2048;
					} else if (coord == 3) {
						i = (x - y >> 1) + 2048;
					}
					@Pc(143) short value = 0;
					for (coord = 0; coord < this.stripeCount; coord++) {
						if (this.stripeStarts[coord] <= i && i < this.stripeStarts[coord + 1]) {
							if (i < this.stripeEnds[coord]) {
								value = 4096;
							}
							break;
						}
					}
					output[col] = value;
				}
			}
		}
		return output;
	}
}
