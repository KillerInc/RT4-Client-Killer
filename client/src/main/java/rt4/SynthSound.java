package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!sl")
public final class SynthSound {

	@OriginalMember(owner = "client!sl", name = "b", descriptor = "[Lclient!pj;")
	private final SynthInstrument[] instruments = new SynthInstrument[10];

	@OriginalMember(owner = "client!sl", name = "c", descriptor = "I")
	private int start;

	@OriginalMember(owner = "client!sl", name = "a", descriptor = "I")
	private int end;

	@OriginalMember(owner = "client!sl", name = "<init>", descriptor = "(Lclient!wa;)V")
	public SynthSound(@OriginalArg(0) Buffer buf) {
		for (@Pc(7) int i = 0; i < 10; i++) {
			@Pc(14) int type = buf.g1();
			if (type != 0) {
				buf.offset--;
				this.instruments[i] = new SynthInstrument();
				this.instruments[i].decode(buf);
			}
		}
		this.start = buf.g2();
		this.end = buf.g2();
	}

	@OriginalMember(owner = "client!sl", name = "a", descriptor = "(Lclient!ve;II)Lclient!sl;")
	public static SynthSound create(@OriginalArg(0) Js5 js5, @OriginalArg(1) int archive, @OriginalArg(2) int file) {
		@Pc(5) byte[] data = js5.fetchFile(archive, file);
		return data == null ? null : new SynthSound(new Buffer(data));
	}

	@OriginalMember(owner = "client!sl", name = "a", descriptor = "()[B")
	private byte[] getSamples() {
		@Pc(1) int totalLen = 0;
		@Pc(3) int i;
		for (i = 0; i < 10; i++) {
			if (this.instruments[i] != null && this.instruments[i].length + this.instruments[i].start > totalLen) {
				totalLen = this.instruments[i].length + this.instruments[i].start;
			}
		}
		if (totalLen == 0) {
			return new byte[0];
		}
		i = totalLen * GlobalConfig.AUDIO_SAMPLE_RATE / 1000;
		@Pc(52) byte[] output = new byte[i];
		for (@Pc(54) int j = 0; j < 10; j++) {
			if (this.instruments[j] != null) {
				@Pc(72) int sampleLen = this.instruments[j].length * GlobalConfig.AUDIO_SAMPLE_RATE / 1000;
				@Pc(82) int sampleStart = this.instruments[j].start * GlobalConfig.AUDIO_SAMPLE_RATE / 1000;
				@Pc(94) int[] samples = this.instruments[j].getSamples(sampleLen, this.instruments[j].length);
				for (@Pc(96) int k = 0; k < sampleLen; k++) {
					@Pc(111) int mixed = output[k + sampleStart] + (samples[k] >> 8);
					if ((mixed + 128 & 0xFFFFFF00) != 0) {
						mixed = mixed >> 31 ^ 0x7F;
					}
					output[k + sampleStart] = (byte) mixed;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!sl", name = "b", descriptor = "()Lclient!kj;")
	public final PcmSound toPcmSound() {
		@Pc(2) byte[] samples = this.getSamples();
		return new PcmSound(GlobalConfig.AUDIO_SAMPLE_RATE, samples, this.start * GlobalConfig.AUDIO_SAMPLE_RATE / 1000, this.end * GlobalConfig.AUDIO_SAMPLE_RATE / 1000);
	}

	@OriginalMember(owner = "client!sl", name = "c", descriptor = "()I")
	public final int getStart() {
		@Pc(1) int minTick = 9999999;
		@Pc(3) int i;
		for (i = 0; i < 10; i++) {
			if (this.instruments[i] != null && this.instruments[i].start / 20 < minTick) {
				minTick = this.instruments[i].start / 20;
			}
		}
		if (this.start < this.end && this.start / 20 < minTick) {
			minTick = this.start / 20;
		}
		if (minTick == 9999999 || minTick == 0) {
			return 0;
		}
		for (i = 0; i < 10; i++) {
			if (this.instruments[i] != null) {
				this.instruments[i].start -= minTick * 20;
			}
		}
		if (this.start < this.end) {
			this.start -= minTick * 20;
			this.end -= minTick * 20;
		}
		return minTick;
	}
}
