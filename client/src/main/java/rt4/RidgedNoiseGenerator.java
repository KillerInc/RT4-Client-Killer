package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!we")
public class RidgedNoiseGenerator extends PerlinNoiseGenerator {

	@OriginalMember(owner = "client!we", name = "B", descriptor = "I")
	private int weight;

	@OriginalMember(owner = "client!we", name = "D", descriptor = "I")
	private int signal;

	@OriginalMember(owner = "client!we", name = "F", descriptor = "[B")
	private byte[] output;

	@OriginalMember(owner = "client!we", name = "J", descriptor = "I")
	private int writePos;

	@OriginalMember(owner = "client!we", name = "L", descriptor = "I")
	private int accumulator;

	@OriginalMember(owner = "client!we", name = "A", descriptor = "I")
	private final int decay;

	@OriginalMember(owner = "client!we", name = "G", descriptor = "I")
	private final int threshold;

	@OriginalMember(owner = "client!we", name = "z", descriptor = "I")
	private final int initialWeight;

	@OriginalMember(owner = "client!we", name = "u", descriptor = "I")
	private int currentWeight;

	@OriginalMember(owner = "client!we", name = "<init>", descriptor = "(IIIIIFFF)V")
	protected RidgedNoiseGenerator(@OriginalArg(0) int seed, @OriginalArg(1) int octaves, @OriginalArg(2) int freqX, @OriginalArg(3) int freqY, @OriginalArg(4) int freqZ, @OriginalArg(5) float gain, @OriginalArg(6) float threshold, @OriginalArg(7) float decay) {
		super(seed, octaves, freqX, freqY, freqZ);
		this.decay = (int) (decay * 4096.0F);
		this.threshold = (int) (threshold * 4096.0F);
		this.currentWeight = this.initialWeight = (int) (Math.pow(0.5D, -gain) * 4096.0D);
	}

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(IB)V")
	protected void writeSample(@OriginalArg(0) int index, @OriginalArg(1) byte value) {
		this.output[index] = value;
	}

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(B)V")
	@Override
	protected final void finishSample() {
		this.currentWeight = this.initialWeight;
		this.accumulator >>= 0x4;
		if (this.accumulator < 0) {
			this.accumulator = 0;
		} else if (this.accumulator > 255) {
			this.accumulator = 255;
		}
		this.writeSample(this.writePos++, (byte) this.accumulator);
		this.accumulator = 0;
	}

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(III)V")
	@Override
	protected final void accumulate(@OriginalArg(0) int noise, @OriginalArg(1) int octave) {
		if (octave == 0) {
			this.weight = 4096;
			this.signal = this.threshold - (noise >= 0 ? noise : -noise);
			this.signal = this.signal * this.signal >> 12;
			this.accumulator = this.signal;
			return;
		}
		this.weight = this.decay * this.signal >> 12;
		if (this.weight < 0) {
			this.weight = 0;
		} else if (this.weight > 4096) {
			this.weight = 4096;
		}
		this.signal = this.threshold - (noise >= 0 ? noise : -noise);
		this.signal = this.signal * this.signal >> 12;
		this.signal = this.signal * this.weight >> 12;
		this.accumulator += this.currentWeight * this.signal >> 12;
		this.currentWeight = this.initialWeight * this.currentWeight >> 12;
	}

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(I)V")
	@Override
	protected final void resetState() {
		this.writePos = 0;
		this.accumulator = 0;
	}
}
