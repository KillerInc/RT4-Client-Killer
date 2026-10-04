package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mh")
public final class TextureOpWaveInterference extends TextureOp {

	@OriginalMember(owner = "client!mh", name = "P", descriptor = "I")
	private int offsetY1 = 0;

	@OriginalMember(owner = "client!mh", name = "T", descriptor = "I")
	private int waveFrequency = 12288;

	@OriginalMember(owner = "client!mh", name = "bb", descriptor = "I")
	private int offsetY2 = 2048;

	@OriginalMember(owner = "client!mh", name = "U", descriptor = "I")
	private int offsetX2 = 0;

	@OriginalMember(owner = "client!mh", name = "ib", descriptor = "I")
	private int offsetX1 = 2048;

	@OriginalMember(owner = "client!mh", name = "Q", descriptor = "I")
	private int waveAmplitude = 4096;

	@OriginalMember(owner = "client!mh", name = "kb", descriptor = "I")
	private int waveDivisor = 8192;

	@OriginalMember(owner = "client!mh", name = "<init>", descriptor = "()V")
	public TextureOpWaveInterference() {
		super(0, true);
	}

	@OriginalMember(owner = "client!mh", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.offsetX1 = buf.g2();
		} else if (opcode == 1) {
			this.offsetY1 = buf.g2();
		} else if (opcode == 2) {
			this.offsetX2 = buf.g2();
		} else if (opcode == 3) {
			this.offsetY2 = buf.g2();
		} else if (opcode == 4) {
			this.waveFrequency = buf.g2();
		} else if (opcode == 5) {
			this.waveAmplitude = buf.g2();
		} else if (opcode == 6) {
			this.waveDivisor = buf.g2();
		}
	}

	@OriginalMember(owner = "client!mh", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(30) int y = Texture.heightFractions[row] - 2048;
			for (@Pc(32) int col = 0; col < Texture.width; col++) {
				@Pc(40) int y1 = y + this.offsetY1;
				@Pc(46) int x = Texture.widthFractions[col] - 2048;
				@Pc(51) int x1 = this.offsetX1 + x;
				@Pc(62) int y1w = y1 < -2048 ? y1 + 4096 : y1;
				@Pc(71) int y1c = y1w > 2048 ? y1w - 4096 : y1w;
				@Pc(76) int x2 = x + this.offsetX2;
				@Pc(85) int x1w = x1 < -2048 ? x1 + 4096 : x1;
				@Pc(96) int x1c = x1w <= 2048 ? x1w : x1w - 4096;
				@Pc(107) int x2w = x2 >= -2048 ? x2 : x2 + 4096;
				@Pc(118) int x2c = x2w > 2048 ? x2w - 4096 : x2w;
				@Pc(124) int y2 = y + this.offsetY2;
				@Pc(133) int y2w = y2 < -2048 ? y2 + 4096 : y2;
				@Pc(144) int y2c = y2w > 2048 ? y2w - 4096 : y2w;
				output[col] = this.isInsidePrimaryWave(x1c, y1c) || this.isInsideSecondaryWave(x2c, y2c) ? 4096 : 0;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!mh", name = "a", descriptor = "(BII)Z")
	private boolean isInsideSecondaryWave(@OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(12) int phase = this.waveFrequency * (x + y) >> 12;
		@Pc(27) int cosVal = TextureOp.COSINE[phase * 255 >> 12 & 0xFF];
		@Pc(34) int scaled = (cosVal << 12) / this.waveFrequency;
		@Pc(41) int divided = (scaled << 12) / this.waveDivisor;
		@Pc(48) int bound = divided * this.waveAmplitude >> 12;
		return bound > y - x && -bound < y - x;
	}

	@OriginalMember(owner = "client!mh", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		TextureOp.createTrigonometryTables();
	}

	@OriginalMember(owner = "client!mh", name = "b", descriptor = "(IIB)Z")
	private boolean isInsidePrimaryWave(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		@Pc(8) int phase = (y - x) * this.waveFrequency >> 12;
		@Pc(31) int cosVal = TextureOp.COSINE[phase * 255 >> 12 & 0xFF];
		@Pc(38) int scaled = (cosVal << 12) / this.waveFrequency;
		@Pc(45) int divided = (scaled << 12) / this.waveDivisor;
		@Pc(52) int bound = divided * this.waveAmplitude >> 12;
		return y + x < bound && -bound < y + x;
	}
}
