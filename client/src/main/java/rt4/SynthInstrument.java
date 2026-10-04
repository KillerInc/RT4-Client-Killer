package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!pj")
public final class SynthInstrument {

	@OriginalMember(owner = "client!pj", name = "o", descriptor = "[I")
	public static final int[] samples = new int[GlobalConfig.AUDIO_SAMPLE_RATE * 10];
	@OriginalMember(owner = "client!pj", name = "p", descriptor = "[I")
	public static final int[] oscillatorIntervalRanges = new int[5];
	@OriginalMember(owner = "client!pj", name = "q", descriptor = "[I")
	public static final int[] oscillatorMinIntervals = new int[5];
	@OriginalMember(owner = "client!pj", name = "r", descriptor = "[I")
	public static final int[] oscillatorTimes = new int[5];
	@OriginalMember(owner = "client!pj", name = "s", descriptor = "[I")
	public static final int[] oscillatorStartSamples = new int[5];
	@OriginalMember(owner = "client!pj", name = "t", descriptor = "[I")
	public static final int[] scaledOscillatorAmplitudes = new int[5];
	@OriginalMember(owner = "client!pj", name = "k", descriptor = "[I")
	private static final int[] NOISE = new int[32768];

	@OriginalMember(owner = "client!pj", name = "h", descriptor = "[I")
	private static final int[] SINE;

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "Lclient!ff;")
	private SynthEnvelope amplitudeModulationAmplitudeEnvelope;

	@OriginalMember(owner = "client!pj", name = "b", descriptor = "Lclient!ff;")
	private SynthEnvelope gateClosedPhaseEnvelope;

	@OriginalMember(owner = "client!pj", name = "d", descriptor = "Lclient!ff;")
	private SynthEnvelope amplitudeEnvelope;

	@OriginalMember(owner = "client!pj", name = "e", descriptor = "Lclient!ff;")
	private SynthEnvelope gateOpenPhaseEnvelope;

	@OriginalMember(owner = "client!pj", name = "f", descriptor = "Lclient!ff;")
	private SynthEnvelope phaseEnvelope;

	@OriginalMember(owner = "client!pj", name = "i", descriptor = "Lclient!ff;")
	private SynthEnvelope filterEnvelope;

	@OriginalMember(owner = "client!pj", name = "l", descriptor = "Lclient!ff;")
	private SynthEnvelope amplitudeModulationEnvelope;

	@OriginalMember(owner = "client!pj", name = "w", descriptor = "Lclient!nl;")
	private SynthFilter filter;

	@OriginalMember(owner = "client!pj", name = "x", descriptor = "Lclient!ff;")
	private SynthEnvelope phaseModulationAmplitudeEnvelope;

	@OriginalMember(owner = "client!pj", name = "y", descriptor = "Lclient!ff;")
	private SynthEnvelope phaseModulationEnvelope;

	@OriginalMember(owner = "client!pj", name = "c", descriptor = "I")
	public int length = 500;

	@OriginalMember(owner = "client!pj", name = "g", descriptor = "I")
	private int reverbDelay = 0;

	@OriginalMember(owner = "client!pj", name = "j", descriptor = "I")
	public int start = 0;

	@OriginalMember(owner = "client!pj", name = "m", descriptor = "[I")
	private final int[] harmonicVolume = new int[]{0, 0, 0, 0, 0};

	@OriginalMember(owner = "client!pj", name = "n", descriptor = "[I")
	private final int[] harmonicDelay = new int[]{0, 0, 0, 0, 0};

	@OriginalMember(owner = "client!pj", name = "u", descriptor = "I")
	private int reverbVolume = 100;

	@OriginalMember(owner = "client!pj", name = "v", descriptor = "[I")
	private final int[] harmonicSemitone = new int[]{0, 0, 0, 0, 0};

	static {
		@Pc(7) Random rand = new Random(0L);
		@Pc(9) int i;
		for (i = 0; i < 32768; i++) {
			NOISE[i] = (rand.nextInt() & 0x2) - 1;
		}
		SINE = new int[32768];
		for (i = 0; i < 32768; i++) {
			SINE[i] = (int) (Math.sin((double) i / 5215.1903D) * 16384.0D);
		}
	}

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "(III)I")
	private int evaluateWaveform(@OriginalArg(0) int phase, @OriginalArg(1) int amplitude, @OriginalArg(2) int waveform) {
		if (waveform == 1) {
			return (phase & 0x7FFF) < 16384 ? amplitude : -amplitude;
		} else if (waveform == 2) {
			return SINE[phase & 0x7FFF] * amplitude >> 14;
		} else if (waveform == 3) {
			return ((phase & 0x7FFF) * amplitude >> 14) - amplitude;
		} else if (waveform == 4) {
			return NOISE[phase / 2607 & 0x7FFF] * amplitude;
		} else {
			return 0;
		}
	}

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "(II)[I")
	public final int[] getSamples(@OriginalArg(0) int sampleCount, @OriginalArg(1) int duration) {
		ArrayUtils.clear(samples, 0, sampleCount);
		if (duration < 10) {
			return samples;
		}
		@Pc(16) double sampleDuration = (double) sampleCount / ((double) duration + 0.0D);
		this.phaseEnvelope.reset();
		this.amplitudeEnvelope.reset();
		@Pc(24) int phaseModRange = 0;
		@Pc(26) int phaseModMin = 0;
		@Pc(28) int phaseModPhase = 0;
		if (this.phaseModulationEnvelope != null) {
			this.phaseModulationEnvelope.reset();
			this.phaseModulationAmplitudeEnvelope.reset();
			phaseModRange = (int) ((double) (this.phaseModulationEnvelope.maxInterval - this.phaseModulationEnvelope.minInterval) * 32.768D / sampleDuration);
			phaseModMin = (int) ((double) this.phaseModulationEnvelope.minInterval * 32.768D / sampleDuration);
		}
		@Pc(63) int ampModRange = 0;
		@Pc(65) int ampModMin = 0;
		@Pc(67) int ampModPhase = 0;
		if (this.amplitudeModulationEnvelope != null) {
			this.amplitudeModulationEnvelope.reset();
			this.amplitudeModulationAmplitudeEnvelope.reset();
			ampModRange = (int) ((double) (this.amplitudeModulationEnvelope.maxInterval - this.amplitudeModulationEnvelope.minInterval) * 32.768D / sampleDuration);
			ampModMin = (int) ((double) this.amplitudeModulationEnvelope.minInterval * 32.768D / sampleDuration);
		}
		@Pc(102) int i;
		for (i = 0; i < 5; i++) {
			if (this.harmonicVolume[i] != 0) {
				oscillatorTimes[i] = 0;
				oscillatorStartSamples[i] = (int) ((double) this.harmonicDelay[i] * sampleDuration);
				scaledOscillatorAmplitudes[i] = (this.harmonicVolume[i] << 14) / 100;
				oscillatorIntervalRanges[i] = (int) ((double) (this.phaseEnvelope.maxInterval - this.phaseEnvelope.minInterval) * 32.768D * Math.pow(1.0057929410678534D, this.harmonicSemitone[i]) / sampleDuration);
				oscillatorMinIntervals[i] = (int) ((double) this.phaseEnvelope.minInterval * 32.768D / sampleDuration);
			}
		}
		@Pc(185) int phaseLevel;
		@Pc(190) int ampLevel;
		@Pc(198) int modLevel;
		@Pc(203) int modAmpLevel;
		for (i = 0; i < sampleCount; i++) {
			phaseLevel = this.phaseEnvelope.nextLevel(sampleCount);
			ampLevel = this.amplitudeEnvelope.nextLevel(sampleCount);
			if (this.phaseModulationEnvelope != null) {
				modLevel = this.phaseModulationEnvelope.nextLevel(sampleCount);
				modAmpLevel = this.phaseModulationAmplitudeEnvelope.nextLevel(sampleCount);
				phaseLevel += this.evaluateWaveform(phaseModPhase, modAmpLevel, this.phaseModulationEnvelope.wavetable) >> 1;
				phaseModPhase += (modLevel * phaseModRange >> 16) + phaseModMin;
			}
			if (this.amplitudeModulationEnvelope != null) {
				modLevel = this.amplitudeModulationEnvelope.nextLevel(sampleCount);
				modAmpLevel = this.amplitudeModulationAmplitudeEnvelope.nextLevel(sampleCount);
				ampLevel = ampLevel * ((this.evaluateWaveform(ampModPhase, modAmpLevel, this.amplitudeModulationEnvelope.wavetable) >> 1) + 32768) >> 15;
				ampModPhase += (modLevel * ampModRange >> 16) + ampModMin;
			}
			for (modLevel = 0; modLevel < 5; modLevel++) {
				if (this.harmonicVolume[modLevel] != 0) {
					modAmpLevel = i + oscillatorStartSamples[modLevel];
					if (modAmpLevel < sampleCount) {
						samples[modAmpLevel] += this.evaluateWaveform(oscillatorTimes[modLevel], ampLevel * scaledOscillatorAmplitudes[modLevel] >> 15, this.phaseEnvelope.wavetable);
						oscillatorTimes[modLevel] += (phaseLevel * oscillatorIntervalRanges[modLevel] >> 16) + oscillatorMinIntervals[modLevel];
					}
				}
			}
		}
		@Pc(356) int gateLevel;
		if (this.gateClosedPhaseEnvelope != null) {
			this.gateClosedPhaseEnvelope.reset();
			this.gateOpenPhaseEnvelope.reset();
			i = 0;
			@Pc(341) boolean gateClosed = true;
			for (modLevel = 0; modLevel < sampleCount; modLevel++) {
				modAmpLevel = this.gateClosedPhaseEnvelope.nextLevel(sampleCount);
				gateLevel = this.gateOpenPhaseEnvelope.nextLevel(sampleCount);
				if (gateClosed) {
					phaseLevel = this.gateClosedPhaseEnvelope.minInterval + ((this.gateClosedPhaseEnvelope.maxInterval - this.gateClosedPhaseEnvelope.minInterval) * modAmpLevel >> 8);
				} else {
					phaseLevel = this.gateClosedPhaseEnvelope.minInterval + ((this.gateClosedPhaseEnvelope.maxInterval - this.gateClosedPhaseEnvelope.minInterval) * gateLevel >> 8);
				}
				i += 256;
				if (i >= phaseLevel) {
					i = 0;
					gateClosed = !gateClosed;
				}
				if (gateClosed) {
					samples[modLevel] = 0;
				}
			}
		}
		if (this.reverbDelay > 0 && this.reverbVolume > 0) {
			i = (int) ((double) this.reverbDelay * sampleDuration);
			for (phaseLevel = i; phaseLevel < sampleCount; phaseLevel++) {
				samples[phaseLevel] += samples[phaseLevel - i] * this.reverbVolume / 100;
			}
		}
		if (this.filter.pairs[0] > 0 || this.filter.pairs[1] > 0) {
			this.filterEnvelope.reset();
			i = this.filterEnvelope.nextLevel(sampleCount + 1);
			phaseLevel = this.filter.compute(0, (float) i / 65536.0F);
			ampLevel = this.filter.compute(1, (float) i / 65536.0F);
			if (sampleCount >= phaseLevel + ampLevel) {
				modLevel = 0;
				modAmpLevel = ampLevel;
				if (ampLevel > sampleCount - phaseLevel) {
					modAmpLevel = sampleCount - phaseLevel;
				}
				@Pc(523) int j;
				while (modLevel < modAmpLevel) {
					gateLevel = (int) ((long) samples[modLevel + phaseLevel] * (long) SynthFilter.inverseA0 >> 16);
					for (j = 0; j < phaseLevel; j++) {
						gateLevel += (int) ((long) samples[modLevel + phaseLevel - j - 1] * (long) SynthFilter.coefficients[0][j] >> 16);
					}
					for (j = 0; j < modLevel; j++) {
						gateLevel -= (int) ((long) samples[modLevel - j - 1] * (long) SynthFilter.coefficients[1][j] >> 16);
					}
					samples[modLevel] = gateLevel;
					i = this.filterEnvelope.nextLevel(sampleCount + 1);
					modLevel++;
				}
				modAmpLevel = 128;
				while (true) {
					if (modAmpLevel > sampleCount - phaseLevel) {
						modAmpLevel = sampleCount - phaseLevel;
					}
					while (modLevel < modAmpLevel) {
						gateLevel = (int) ((long) samples[modLevel + phaseLevel] * (long) SynthFilter.inverseA0 >> 16);
						for (j = 0; j < phaseLevel; j++) {
							gateLevel += (int) ((long) samples[modLevel + phaseLevel - j - 1] * (long) SynthFilter.coefficients[0][j] >> 16);
						}
						for (j = 0; j < ampLevel; j++) {
							gateLevel -= (int) ((long) samples[modLevel - j - 1] * (long) SynthFilter.coefficients[1][j] >> 16);
						}
						samples[modLevel] = gateLevel;
						i = this.filterEnvelope.nextLevel(sampleCount + 1);
						modLevel++;
					}
					if (modLevel >= sampleCount - phaseLevel) {
						while (modLevel < sampleCount) {
							gateLevel = 0;
							for (j = modLevel + phaseLevel - sampleCount; j < phaseLevel; j++) {
								gateLevel += (int) ((long) samples[modLevel + phaseLevel - j - 1] * (long) SynthFilter.coefficients[0][j] >> 16);
							}
							for (j = 0; j < ampLevel; j++) {
								gateLevel -= (int) ((long) samples[modLevel - j - 1] * (long) SynthFilter.coefficients[1][j] >> 16);
							}
							samples[modLevel] = gateLevel;
							this.filterEnvelope.nextLevel(sampleCount + 1);
							modLevel++;
						}
						break;
					}
					phaseLevel = this.filter.compute(0, (float) i / 65536.0F);
					ampLevel = this.filter.compute(1, (float) i / 65536.0F);
					modAmpLevel += 128;
				}
			}
		}
		for (i = 0; i < sampleCount; i++) {
			if (samples[i] < -32768) {
				samples[i] = -32768;
			}
			if (samples[i] > 32767) {
				samples[i] = 32767;
			}
		}
		return samples;
	}

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "(Lclient!wa;)V")
	public final void decode(@OriginalArg(0) Buffer buffer) {
		this.phaseEnvelope = new SynthEnvelope();
		this.phaseEnvelope.decode(buffer);
		this.amplitudeEnvelope = new SynthEnvelope();
		this.amplitudeEnvelope.decode(buffer);
		@Pc(21) int check = buffer.g1();
		if (check != 0) {
			buffer.offset--;
			this.phaseModulationEnvelope = new SynthEnvelope();
			this.phaseModulationEnvelope.decode(buffer);
			this.phaseModulationAmplitudeEnvelope = new SynthEnvelope();
			this.phaseModulationAmplitudeEnvelope.decode(buffer);
		}
		check = buffer.g1();
		if (check != 0) {
			buffer.offset--;
			this.amplitudeModulationEnvelope = new SynthEnvelope();
			this.amplitudeModulationEnvelope.decode(buffer);
			this.amplitudeModulationAmplitudeEnvelope = new SynthEnvelope();
			this.amplitudeModulationAmplitudeEnvelope.decode(buffer);
		}
		check = buffer.g1();
		if (check != 0) {
			buffer.offset--;
			this.gateClosedPhaseEnvelope = new SynthEnvelope();
			this.gateClosedPhaseEnvelope.decode(buffer);
			this.gateOpenPhaseEnvelope = new SynthEnvelope();
			this.gateOpenPhaseEnvelope.decode(buffer);
		}
		for (@Pc(109) int i = 0; i < 10; i++) {
			@Pc(116) int volume = buffer.gsmarts();
			if (volume == 0) {
				break;
			}
			this.harmonicVolume[i] = volume;
			this.harmonicSemitone[i] = buffer.gsmart();
			this.harmonicDelay[i] = buffer.gsmarts();
		}
		this.reverbDelay = buffer.gsmarts();
		this.reverbVolume = buffer.gsmarts();
		this.length = buffer.g2();
		this.start = buffer.g2();
		this.filter = new SynthFilter();
		this.filterEnvelope = new SynthEnvelope();
		this.filter.decode(buffer, this.filterEnvelope);
	}
}
