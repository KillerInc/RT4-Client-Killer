package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!vj")
public final class PcmResampler {

	@OriginalMember(owner = "client!vj", name = "k", descriptor = "I")
	private int outputRate;

	@OriginalMember(owner = "client!vj", name = "i", descriptor = "[[I")
	private int[][] filterCoefficients;

	@OriginalMember(owner = "client!vj", name = "e", descriptor = "I")
	private int inputRate;

	@OriginalMember(owner = "client!vj", name = "<init>", descriptor = "(II)V")
	public PcmResampler(@OriginalArg(0) int srcRate, @OriginalArg(1) int destRate) {
		if (destRate != srcRate) {
			@Pc(12) int divisor = gcd(destRate, srcRate);
			@Pc(16) int upFactor = destRate / divisor;
			this.outputRate = upFactor;
			@Pc(23) int downFactor = srcRate / divisor;
			this.filterCoefficients = new int[downFactor][14];
			this.inputRate = downFactor;
			for (@Pc(33) int phase = 0; phase < downFactor; phase++) {
				@Pc(41) int[] coeffs = this.filterCoefficients[phase];
				@Pc(49) double center = (double) phase / (double) downFactor + 6.0D;
				@Pc(55) double gain = (double) upFactor / (double) downFactor;
				@Pc(63) int lo = (int) Math.floor(center + 1.0D - 7.0D);
				@Pc(69) int hi = (int) Math.ceil(center + 7.0D);
				if (lo < 0) {
					lo = 0;
				}
				if (hi > 14) {
					hi = 14;
				}
				while (lo < hi) {
					@Pc(91) double x = ((double) lo - center) * 3.141592653589793D;
					@Pc(93) double sinc = gain;
					if (-1.0E-4D > x || x > 1.0E-4D) {
						sinc = gain * (Math.sin(x) / x);
					}
					sinc *= Math.cos(((double) lo - center) * 0.2243994752564138D) * 0.46D + 0.54D;
					coeffs[lo] = (int) Math.floor(sinc * 65536.0D + 0.5D);
					lo++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!og", name = "a", descriptor = "(III)I")
	public static int gcd(@OriginalArg(1) int a, @OriginalArg(2) int b) {
		if (a > GlobalConfig.AUDIO_SAMPLE_RATE) {
			b = a;
			a = GlobalConfig.AUDIO_SAMPLE_RATE;
		}
		while (a != 0) {
			@Pc(21) int remainder = b % a;
			b = a;
			a = remainder;
		}
		return b;
	}

	@OriginalMember(owner = "client!vj", name = "a", descriptor = "([BB)[B")
	public final byte[] resample(@OriginalArg(0) byte[] samples) {
		if (this.filterCoefficients != null) {
			@Pc(31) int outLen = (int) ((long) samples.length * (long) this.outputRate / (long) this.inputRate) + 14;
			@Pc(34) int[] accum = new int[outLen];
			@Pc(36) int outPos = 0;
			@Pc(38) int phase = 0;
			@Pc(40) int i;
			for (i = 0; i < samples.length; i++) {
				@Pc(53) int[] coeffs = this.filterCoefficients[phase];
				@Pc(57) byte sample = samples[i];
				@Pc(59) int j;
				for (j = 0; j < 14; j++) {
					accum[outPos + j] += coeffs[j] * sample;
				}
				phase += this.outputRate;
				j = phase / this.inputRate;
				outPos += j;
				phase -= j * this.inputRate;
			}
			samples = new byte[outLen];
			for (i = 0; i < outLen; i++) {
				@Pc(119) int clamped = accum[i] + 32768 >> 16;
				if (clamped < -128) {
					samples[i] = -128;
				} else if (clamped <= 127) {
					samples[i] = (byte) clamped;
				} else {
					samples[i] = 127;
				}
			}
		}
		return samples;
	}

	@OriginalMember(owner = "client!vj", name = "a", descriptor = "(IB)I")
	public final int scaleRate(@OriginalArg(0) int rate) {
		if (this.filterCoefficients != null) {
			rate = (int) ((long) this.outputRate * (long) rate / (long) this.inputRate);
		}
		return rate;
	}

	@OriginalMember(owner = "client!vj", name = "a", descriptor = "(ZI)I")
	public final int scalePosition(@OriginalArg(1) int position) {
		if (this.filterCoefficients != null) {
			position = (int) ((long) this.outputRate * (long) position / (long) this.inputRate) + 6;
		}
		return position;
	}
}
