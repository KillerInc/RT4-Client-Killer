package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nl")
public final class SynthFilter {

	@OriginalMember(owner = "client!nl", name = "f", descriptor = "[[I")
	public static final int[][] coefficients = new int[2][8];
	@OriginalMember(owner = "client!nl", name = "b", descriptor = "[[F")
	public static final float[][] floatingCoefficients = new float[2][8];
	@OriginalMember(owner = "client!nl", name = "d", descriptor = "F")
	public static float floatingInverseA0;
	@OriginalMember(owner = "client!nl", name = "g", descriptor = "I")
	public static int inverseA0;
	@OriginalMember(owner = "client!nl", name = "e", descriptor = "[I")
	public final int[] pairs = new int[2];

	@OriginalMember(owner = "client!nl", name = "c", descriptor = "[[[I")
	private final int[][][] gain = new int[2][2][4];

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "[[[I")
	private final int[][][] octaves = new int[2][2][4];

	@OriginalMember(owner = "client!nl", name = "h", descriptor = "[I")
	private final int[] inverseGain = new int[2];

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(F)F")
	public static float getOctavePhase(@OriginalArg(0) float pitch) {
		@Pc(7) float freq = (float) Math.pow(2.0D, pitch) * 32.703197F;
		return freq * 3.1415927F / 11025.0F;
	}

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(Lclient!wa;Lclient!ff;)V")
	public final void decode(@OriginalArg(0) Buffer buf, @OriginalArg(1) SynthEnvelope envelope) {
		@Pc(3) int pairByte = buf.g1();
		this.pairs[0] = pairByte >> 4;
		this.pairs[1] = pairByte & 0xF;
		if (pairByte == 0) {
			this.inverseGain[0] = this.inverseGain[1] = 0;
			return;
		}
		this.inverseGain[0] = buf.g2();
		this.inverseGain[1] = buf.g2();
		@Pc(37) int flags = buf.g1();
		@Pc(39) int channel;
		@Pc(44) int pair;
		for (channel = 0; channel < 2; channel++) {
			for (pair = 0; pair < this.pairs[channel]; pair++) {
				this.octaves[channel][0][pair] = buf.g2();
				this.gain[channel][0][pair] = buf.g2();
			}
		}
		for (channel = 0; channel < 2; channel++) {
			for (pair = 0; pair < this.pairs[channel]; pair++) {
				if ((flags & 0x1 << channel * 4 << pair) == 0) {
					this.octaves[channel][1][pair] = this.octaves[channel][0][pair];
					this.gain[channel][1][pair] = this.gain[channel][0][pair];
				} else {
					this.octaves[channel][1][pair] = buf.g2();
					this.gain[channel][1][pair] = buf.g2();
				}
			}
		}
		if (flags != 0 || this.inverseGain[1] != this.inverseGain[0]) {
			envelope.decodeStages(buf);
		}
	}

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(IF)I")
	public final int compute(@OriginalArg(0) int channel, @OriginalArg(1) float t) {
		@Pc(20) float amplitude;
		if (channel == 0) {
			amplitude = (float) this.inverseGain[0] + (float) (this.inverseGain[1] - this.inverseGain[0]) * t;
			@Pc(24) float db = amplitude * 0.0030517578F;
			floatingInverseA0 = (float) Math.pow(0.1D, db / 20.0F);
			inverseA0 = (int) (floatingInverseA0 * 65536.0F);
		}
		if (this.pairs[channel] == 0) {
			return 0;
		}
		amplitude = this.getAmplitude(channel, 0, t);
		floatingCoefficients[channel][0] = -2.0F * amplitude * (float) Math.cos(this.getInterpolatedPhase(channel, 0, t));
		floatingCoefficients[channel][1] = amplitude * amplitude;
		@Pc(77) int i;
		for (i = 1; i < this.pairs[channel]; i++) {
			amplitude = this.getAmplitude(channel, i, t);
			@Pc(102) float coeff = -2.0F * amplitude * (float) Math.cos(this.getInterpolatedPhase(channel, i, t));
			@Pc(106) float ampSq = amplitude * amplitude;
			floatingCoefficients[channel][i * 2 + 1] = floatingCoefficients[channel][i * 2 - 1] * ampSq;
			floatingCoefficients[channel][i * 2] = floatingCoefficients[channel][i * 2 - 1] * coeff + floatingCoefficients[channel][i * 2 - 2] * ampSq;
			for (@Pc(162) int j = i * 2 - 1; j >= 2; j--) {
				floatingCoefficients[channel][j] += floatingCoefficients[channel][j - 1] * coeff + floatingCoefficients[channel][j - 2] * ampSq;
			}
			floatingCoefficients[channel][1] += floatingCoefficients[channel][0] * coeff + ampSq;
			floatingCoefficients[channel][0] += coeff;
		}
		if (channel == 0) {
			for (i = 0; i < this.pairs[0] * 2; i++) {
				floatingCoefficients[0][i] *= floatingInverseA0;
			}
		}
		for (i = 0; i < this.pairs[channel] * 2; i++) {
			coefficients[channel][i] = (int) (floatingCoefficients[channel][i] * 65536.0F);
		}
		return this.pairs[channel] * 2;
	}

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(IIF)F")
	private float getAmplitude(@OriginalArg(0) int channel, @OriginalArg(1) int pair, @OriginalArg(2) float t) {
		@Pc(30) float interpolated = (float) this.gain[channel][0][pair] + t * (float) (this.gain[channel][1][pair] - this.gain[channel][0][pair]);
		@Pc(34) float db = interpolated * 0.0015258789F;
		return 1.0F - (float) Math.pow(10.0D, -db / 20.0F);
	}

	@OriginalMember(owner = "client!nl", name = "b", descriptor = "(IIF)F")
	private float getInterpolatedPhase(@OriginalArg(0) int channel, @OriginalArg(1) int pair, @OriginalArg(2) float t) {
		@Pc(30) float interpolated = (float) this.octaves[channel][0][pair] + t * (float) (this.octaves[channel][1][pair] - this.octaves[channel][0][pair]);
		@Pc(34) float octave = interpolated * 1.2207031E-4F;
		return getOctavePhase(octave);
	}
}
