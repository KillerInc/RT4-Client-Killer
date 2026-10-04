package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!vd")
public class FractalNoiseGenerator extends PerlinNoiseGenerator {

	@OriginalMember(owner = "client!vd", name = "x", descriptor = "I")
	private int accumulator;

	@OriginalMember(owner = "client!vd", name = "H", descriptor = "[B")
	private byte[] output;

	@OriginalMember(owner = "client!vd", name = "J", descriptor = "I")
	private int writePos;

	@OriginalMember(owner = "client!vd", name = "G", descriptor = "[I")
	private final int[] octaveWeights = new int[this.octaveCount];

	@OriginalMember(owner = "client!vd", name = "<init>", descriptor = "(IIIIIF)V")
	protected FractalNoiseGenerator(@OriginalArg(0) int seed, @OriginalArg(1) int width, @OriginalArg(2) int height, @OriginalArg(3) int octaves, @OriginalArg(4) int amplitude, @OriginalArg(5) float persistence) {
		super(seed, width, height, octaves, amplitude);
		for (@Pc(15) int i = 0; i < this.octaveCount; i++) {
			this.octaveWeights[i] = (short) (Math.pow(persistence, i) * 4096.0D);
		}
	}

	@OriginalMember(owner = "client!vd", name = "a", descriptor = "(III)V")
	@Override
	protected final void accumulate(@OriginalArg(0) int sample, @OriginalArg(1) int octave) {
		this.accumulator += sample * this.octaveWeights[octave] >> 12;
	}

	@OriginalMember(owner = "client!vd", name = "a", descriptor = "(I)V")
	@Override
	protected final void resetState() {
		this.writePos = 0;
		this.accumulator = 0;
	}

	@OriginalMember(owner = "client!vd", name = "a", descriptor = "(IB)V")
	protected void writeSample(@OriginalArg(0) int index, @OriginalArg(1) byte value) {
		this.output[this.writePos++] = (byte) ((value >> 1 & 0x7F) + 127);
	}

	@OriginalMember(owner = "client!vd", name = "a", descriptor = "(B)V")
	@Override
	protected final void finishSample() {
		this.accumulator = Math.abs(this.accumulator);
		if (this.accumulator >= 4096) {
			this.accumulator = 4095;
		}
		this.writeSample(this.writePos++, (byte) (this.accumulator >> 4));
		this.accumulator = 0;
	}
}
