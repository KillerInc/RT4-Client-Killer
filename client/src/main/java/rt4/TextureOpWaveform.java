package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ag")
public final class TextureOpWaveform extends TextureOp {

	@OriginalMember(owner = "client!ag", name = "S", descriptor = "I")
	private int waveform = 0;

	@OriginalMember(owner = "client!ag", name = "X", descriptor = "I")
	private int shape = 0;

	@OriginalMember(owner = "client!ag", name = "W", descriptor = "I")
	private int frequency = 1;

	@OriginalMember(owner = "client!ag", name = "<init>", descriptor = "()V")
	public TextureOpWaveform() {
		super(0, true);
	}

	@OriginalMember(owner = "client!ag", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(11) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(20) int rowFraction = Texture.heightFractions[row];
			@Pc(26) int halfRow = rowFraction - 2048 >> 1;
			for (@Pc(28) int i = 0; i < Texture.width; i++) {
				@Pc(35) int colFraction = Texture.widthFractions[i];
				@Pc(41) int halfCol = colFraction - 2048 >> 1;
				@Pc(68) int value;
				if (this.shape == 0) {
					value = (colFraction - rowFraction) * this.frequency;
				} else {
					@Pc(58) int distSq = halfCol * halfCol + halfRow * halfRow >> 12;
					value = (int) (Math.sqrt((float) distSq / 4096.0F) * 4096.0D);
					value = (int) ((double) (value * this.frequency) * 3.141592653589793D);
				}
				value -= value & 0xFFFFF000;
				if (this.waveform == 0) {
					value = TextureOp.SINE[value >> 4 & 0xFF] + 4096 >> 1;
				} else if (this.waveform == 2) {
					value -= 2048;
					if (value < 0) {
						value = -value;
					}
					value = 2048 - value << 1;
				}
				output[i] = value;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ag", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		TextureOp.createTrigonometryTables();
	}

	@OriginalMember(owner = "client!ag", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.shape = buffer.g1();
		} else if (opcode == 1) {
			this.waveform = buffer.g1();
		} else if (opcode == 3) {
			this.frequency = buffer.g1();
		}
	}
}
