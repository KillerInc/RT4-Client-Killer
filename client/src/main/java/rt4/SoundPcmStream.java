package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!b")
public final class SoundPcmStream extends PcmStream {

	@OriginalMember(owner = "client!b", name = "v", descriptor = "I")
	public int volumeDelta;

	@OriginalMember(owner = "client!b", name = "y", descriptor = "I")
	public int leftVolumeDelta;

	@OriginalMember(owner = "client!b", name = "z", descriptor = "I")
	public int currentVolume;

	@OriginalMember(owner = "client!b", name = "C", descriptor = "I")
	private int loopCount;

	@OriginalMember(owner = "client!b", name = "D", descriptor = "I")
	private int fadeRemaining;

	@OriginalMember(owner = "client!b", name = "E", descriptor = "I")
	public int rightVolume;

	@OriginalMember(owner = "client!b", name = "G", descriptor = "I")
	public int rightVolumeDelta;

	@OriginalMember(owner = "client!b", name = "H", descriptor = "I")
	public int leftVolume;

	@OriginalMember(owner = "client!b", name = "w", descriptor = "I")
	private final int start;

	@OriginalMember(owner = "client!b", name = "F", descriptor = "I")
	private final int end;

	@OriginalMember(owner = "client!b", name = "A", descriptor = "Z")
	private final boolean pingPongLoop;

	@OriginalMember(owner = "client!b", name = "t", descriptor = "I")
	private int sampleRate;

	@OriginalMember(owner = "client!b", name = "u", descriptor = "I")
	private int volume;

	@OriginalMember(owner = "client!b", name = "B", descriptor = "I")
	private int pan;

	@OriginalMember(owner = "client!b", name = "x", descriptor = "I")
	public int samplePosition;

	@OriginalMember(owner = "client!b", name = "<init>", descriptor = "(Lclient!kj;II)V")
	public SoundPcmStream(@OriginalArg(0) PcmSound sound, @OriginalArg(1) int sampleRate, @OriginalArg(2) int volume) {
		this.sound = sound;
		this.start = sound.start;
		this.end = sound.end;
		this.pingPongLoop = sound.pingPongLoop;
		this.sampleRate = sampleRate;
		this.volume = volume;
		this.pan = 8192;
		this.samplePosition = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!b", name = "<init>", descriptor = "(Lclient!kj;III)V")
	public SoundPcmStream(@OriginalArg(0) PcmSound sound, @OriginalArg(1) int sampleRate, @OriginalArg(2) int volume, @OriginalArg(3) int pan) {
		this.sound = sound;
		this.start = sound.start;
		this.end = sound.end;
		this.pingPongLoop = sound.pingPongLoop;
		this.sampleRate = sampleRate;
		this.volume = volume;
		this.pan = pan;
		this.samplePosition = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(Lclient!kj;II)Lclient!b;")
	public static SoundPcmStream create(@OriginalArg(0) PcmSound sound, @OriginalArg(2) int volume) {
		return sound.samples == null || sound.samples.length == 0 ? null : new SoundPcmStream(sound, (int) ((long) sound.rate * 256L * (long) 100 / (AudioChannel.sampleRate * 100L)), volume << 6);
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(I[B[IIIIIIIILclient!b;)I")
	public static int mixForwardStereo(@OriginalArg(1) byte[] samples, @OriginalArg(2) int[] output, @OriginalArg(3) int samplePos, @OriginalArg(4) int outPos, @OriginalArg(5) int leftVol, @OriginalArg(6) int rightVol, @OriginalArg(8) int maxOutPos, @OriginalArg(9) int sampleEnd, @OriginalArg(10) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		@Pc(11) int scaledLeftVol = leftVol << 2;
		@Pc(15) int scaledRightVol = rightVol << 2;
		@Pc(22) int limit;
		if ((limit = outPos + endSampleIdx - samplePos) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		limit -= 6;
		@Pc(43) byte sample;
		@Pc(46) int outIdx;
		while (outPos < limit) {
			@Pc(41) int sampleIdx1 = samplePos + 1;
			sample = samples[samplePos];
			outIdx = outPos + 1;
			output[outPos] += sample * scaledLeftVol;
			@Pc(56) int outIdx2 = outIdx + 1;
			output[outIdx] += sample * scaledRightVol;
			@Pc(66) int sampleIdx2 = sampleIdx1 + 1;
			@Pc(68) byte sample2 = samples[sampleIdx1];
			@Pc(71) int outIdx3 = outIdx2 + 1;
			output[outIdx2] += sample2 * scaledLeftVol;
			@Pc(81) int outIdx4 = outIdx3 + 1;
			output[outIdx3] += sample2 * scaledRightVol;
			@Pc(91) int sampleIdx3 = sampleIdx2 + 1;
			@Pc(93) byte sample3 = samples[sampleIdx2];
			@Pc(96) int outIdx5 = outIdx4 + 1;
			output[outIdx4] += sample3 * scaledLeftVol;
			@Pc(106) int outIdx6 = outIdx5 + 1;
			output[outIdx5] += sample3 * scaledRightVol;
			samplePos = sampleIdx3 + 1;
			@Pc(118) byte sample4 = samples[sampleIdx3];
			@Pc(121) int outIdx7 = outIdx6 + 1;
			output[outIdx6] += sample4 * scaledLeftVol;
			outPos = outIdx7 + 1;
			output[outIdx7] += sample4 * scaledRightVol;
		}
		limit += 6;
		while (outPos < limit) {
			sample = samples[samplePos++];
			outIdx = outPos + 1;
			output[outPos] += sample * scaledLeftVol;
			outPos = outIdx + 1;
			output[outIdx] += sample * scaledRightVol;
		}
		stream.samplePosition = samplePos << 8;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(II[B[IIIIIIIIIILclient!b;II)I")
	public static int mixForwardStereoResampledFading(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int leftVol, @OriginalArg(7) int rightVol, @OriginalArg(8) int leftVolDelta, @OriginalArg(9) int rightVolDelta, @OriginalArg(11) int maxOutPos, @OriginalArg(12) int sampleEnd, @OriginalArg(13) SoundPcmStream stream, @OriginalArg(14) int rate, @OriginalArg(15) int lastSample) {
		stream.currentVolume -= stream.volumeDelta * outPos;
		@Pc(23) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 257) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		@Pc(46) byte sample;
		@Pc(65) int outIdxR;
		@Pc(62) int interpolated;
		@Pc(64) int outIdxL;
		while (outPos < limit) {
			@Pc(42) int sampleIdx = samplePos >> 8;
			sample = samples[sampleIdx];
			interpolated = (sample << 8) + (samples[sampleIdx + 1] - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			leftVol += leftVolDelta;
			@Pc(80) int rightIdx = outIdxR;
			outPos = outIdxR + 1;
			output[rightIdx] += interpolated * rightVol >> 6;
			rightVol += rightVolDelta;
			samplePos += rate;
		}
		if (rate == 0 || (limit = (outPos >> 1) + (sampleEnd + rate - samplePos - 1) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		limit <<= 0x1;
		while (outPos < limit) {
			sample = samples[samplePos >> 8];
			interpolated = (sample << 8) + (lastSample - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			leftVol += leftVolDelta;
			outIdxL = outIdxR;
			outPos = outIdxR + 1;
			output[outIdxL] += interpolated * rightVol >> 6;
			rightVol += rightVolDelta;
			samplePos += rate;
		}
		outIdxR = outPos >> 1;
		stream.currentVolume += stream.volumeDelta * outIdxR;
		stream.leftVolume = leftVol;
		stream.rightVolume = rightVol;
		stream.samplePosition = samplePos;
		return outIdxR;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(II[B[IIIIIIIILclient!b;II)I")
	public static int mixForwardMonoResampledFading(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int vol, @OriginalArg(7) int volDelta, @OriginalArg(9) int maxOutPos, @OriginalArg(10) int sampleEnd, @OriginalArg(11) SoundPcmStream stream, @OriginalArg(12) int rate, @OriginalArg(13) int lastSample) {
		stream.leftVolume -= stream.leftVolumeDelta * outPos;
		stream.rightVolume -= stream.rightVolumeDelta * outPos;
		@Pc(32) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 257) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		@Pc(47) byte sample;
		@Pc(49) int outIdx;
		while (outPos < limit) {
			@Pc(43) int sampleIdx = samplePos >> 8;
			sample = samples[sampleIdx];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (samples[sampleIdx + 1] - sample) * (samplePos & 0xFF)) * vol >> 6;
			vol += volDelta;
			samplePos += rate;
		}
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 1) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		while (outPos < limit) {
			sample = samples[samplePos >> 8];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (lastSample - sample) * (samplePos & 0xFF)) * vol >> 6;
			vol += volDelta;
			samplePos += rate;
		}
		stream.leftVolume += stream.leftVolumeDelta * outPos;
		stream.rightVolume += stream.rightVolumeDelta * outPos;
		stream.currentVolume = vol;
		stream.samplePosition = samplePos;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(II[B[IIIIIIILclient!b;II)I")
	public static int mixBackwardMonoResampled(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int vol, @OriginalArg(8) int maxOutPos, @OriginalArg(9) int sampleEnd, @OriginalArg(10) SoundPcmStream stream, @OriginalArg(11) int rate, @OriginalArg(12) int lastSample) {
		@Pc(14) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate + 256 - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		@Pc(33) int outIdx;
		while (outPos < limit) {
			@Pc(25) int sampleIdx = samplePos >> 8;
			@Pc(31) byte sample = samples[sampleIdx - 1];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (samples[sampleIdx] - sample) * (samplePos & 0xFF)) * vol >> 6;
			samplePos += rate;
		}
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += ((lastSample << 8) + (samples[samplePos >> 8] - lastSample) * (samplePos & 0xFF)) * vol >> 6;
			samplePos += rate;
		}
		stream.samplePosition = samplePos;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "([B[IIIIIIILclient!b;)I")
	public static int mixForwardMono(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] output, @OriginalArg(2) int samplePos, @OriginalArg(3) int outPos, @OriginalArg(4) int vol, @OriginalArg(6) int maxOutPos, @OriginalArg(7) int sampleEnd, @OriginalArg(8) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		@Pc(11) int scaledVol = vol << 2;
		@Pc(18) int limit;
		if ((limit = outPos + endSampleIdx - samplePos) > maxOutPos) {
			limit = maxOutPos;
		}
		limit -= 3;
		@Pc(28) int outIdx;
		while (outPos < limit) {
			outIdx = outPos++;
			@Pc(31) int curOut = output[outIdx];
			@Pc(34) int sampleIdx1 = samplePos + 1;
			output[outIdx] = curOut + samples[samplePos] * scaledVol;
			@Pc(41) int outIdx2 = outPos++;
			@Pc(44) int curOut3 = output[outIdx2];
			@Pc(47) int sampleIdx3 = sampleIdx1 + 1;
			output[outIdx2] = curOut3 + samples[sampleIdx1] * scaledVol;
			@Pc(54) int outIdx4 = outPos++;
			@Pc(57) int curOut5 = output[outIdx4];
			@Pc(60) int sampleIdx5 = sampleIdx3 + 1;
			output[outIdx4] = curOut5 + samples[sampleIdx3] * scaledVol;
			@Pc(67) int outIdx6 = outPos++;
			@Pc(70) int curOut7 = output[outIdx6];
			samplePos = sampleIdx5 + 1;
			output[outIdx6] = curOut7 + samples[sampleIdx5] * scaledVol;
		}
		limit += 3;
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += samples[samplePos++] * scaledVol;
		}
		stream.samplePosition = samplePos << 8;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(I[B[IIIIIIIIIILclient!b;)I")
	public static int mixForwardStereoFading(@OriginalArg(1) byte[] samples, @OriginalArg(2) int[] output, @OriginalArg(3) int samplePos, @OriginalArg(4) int outPos, @OriginalArg(5) int leftVol, @OriginalArg(6) int rightVol, @OriginalArg(7) int leftVolDelta, @OriginalArg(8) int rightVolDelta, @OriginalArg(10) int maxOutPos, @OriginalArg(11) int sampleEnd, @OriginalArg(12) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		leftVol <<= 0x2;
		rightVol <<= 0x2;
		@Pc(19) int scaledLeftVolDelta = leftVolDelta << 2;
		@Pc(23) int scaledRightVolDelta = rightVolDelta << 2;
		@Pc(30) int limit;
		if ((limit = outPos + endSampleIdx - samplePos) > maxOutPos) {
			limit = maxOutPos;
		}
		stream.currentVolume += stream.volumeDelta * (limit - outPos);
		outPos <<= 0x1;
		limit <<= 0x1;
		limit -= 6;
		@Pc(62) byte sample;
		@Pc(65) int outIdxL;
		while (outPos < limit) {
			@Pc(60) int sampleIdx1 = samplePos + 1;
			sample = samples[samplePos];
			outIdxL = outPos + 1;
			output[outPos] += sample * leftVol;
			@Pc(76) int nextLeftVol = leftVol + scaledLeftVolDelta;
			@Pc(79) int outIdxL2 = outIdxL + 1;
			output[outIdxL] += sample * rightVol;
			@Pc(90) int nextRightVol = rightVol + scaledRightVolDelta;
			@Pc(93) int sampleIdx2 = sampleIdx1 + 1;
			@Pc(95) byte sampleIdx3 = samples[sampleIdx1];
			@Pc(98) int outIdxR2 = outIdxL2 + 1;
			output[outIdxL2] += sampleIdx3 * nextLeftVol;
			@Pc(109) int nextLeftVol3 = nextLeftVol + scaledLeftVolDelta;
			@Pc(112) int outIdxL4 = outIdxR2 + 1;
			output[outIdxR2] += sampleIdx3 * nextRightVol;
			@Pc(123) int nextRightVol3 = nextRightVol + scaledRightVolDelta;
			@Pc(126) int sampleIdx4 = sampleIdx2 + 1;
			@Pc(128) byte sampleIdx5 = samples[sampleIdx2];
			@Pc(131) int outIdxR4 = outIdxL4 + 1;
			output[outIdxL4] += sampleIdx5 * nextLeftVol3;
			@Pc(142) int nextLeftVol5 = nextLeftVol3 + scaledLeftVolDelta;
			@Pc(145) int outIdxL6 = outIdxR4 + 1;
			output[outIdxR4] += sampleIdx5 * nextRightVol3;
			@Pc(156) int nextRightVol5 = nextRightVol3 + scaledRightVolDelta;
			samplePos = sampleIdx4 + 1;
			@Pc(161) byte sample5 = samples[sampleIdx4];
			@Pc(164) int outIdxR6 = outIdxL6 + 1;
			output[outIdxL6] += sample5 * nextLeftVol5;
			leftVol = nextLeftVol5 + scaledLeftVolDelta;
			outPos = outIdxR6 + 1;
			output[outIdxR6] += sample5 * nextRightVol5;
			rightVol = nextRightVol5 + scaledRightVolDelta;
		}
		limit += 6;
		while (outPos < limit) {
			sample = samples[samplePos++];
			outIdxL = outPos + 1;
			output[outPos] += sample * leftVol;
			leftVol += scaledLeftVolDelta;
			outPos = outIdxL + 1;
			output[outIdxL] += sample * rightVol;
			rightVol += scaledRightVolDelta;
		}
		stream.leftVolume = leftVol >> 2;
		stream.rightVolume = rightVol >> 2;
		stream.samplePosition = samplePos << 8;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "([B[IIIIIIIILclient!b;)I")
	public static int mixBackwardMonoFading(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] output, @OriginalArg(2) int samplePos, @OriginalArg(3) int outPos, @OriginalArg(4) int vol, @OriginalArg(5) int volDelta, @OriginalArg(7) int maxOutPos, @OriginalArg(8) int sampleEnd, @OriginalArg(9) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		vol <<= 0x2;
		@Pc(15) int scaledVolDelta = volDelta << 2;
		@Pc(24) int limit;
		if ((limit = outPos + samplePos + 1 - endSampleIdx) > maxOutPos) {
			limit = maxOutPos;
		}
		stream.leftVolume += stream.leftVolumeDelta * (limit - outPos);
		stream.rightVolume += stream.rightVolumeDelta * (limit - outPos);
		limit -= 3;
		@Pc(56) int outIdx;
		while (outPos < limit) {
			outIdx = outPos++;
			@Pc(59) int curOut = output[outIdx];
			@Pc(62) int sampleIdx1 = samplePos - 1;
			output[outIdx] = curOut + samples[samplePos] * vol;
			@Pc(71) int nextVol2 = vol + scaledVolDelta;
			@Pc(73) int outIdx2 = outPos++;
			@Pc(76) int curOut2 = output[outIdx2];
			@Pc(79) int sampleIdx2 = sampleIdx1 - 1;
			output[outIdx2] = curOut2 + samples[sampleIdx1] * nextVol2;
			@Pc(88) int nextVol4 = nextVol2 + scaledVolDelta;
			@Pc(90) int outIdx3 = outPos++;
			@Pc(93) int curOut3 = output[outIdx3];
			@Pc(96) int sampleIdx3 = sampleIdx2 - 1;
			output[outIdx3] = curOut3 + samples[sampleIdx2] * nextVol4;
			@Pc(105) int nextVol6 = nextVol4 + scaledVolDelta;
			@Pc(107) int outIdx4 = outPos++;
			@Pc(110) int curOut4 = output[outIdx4];
			samplePos = sampleIdx3 - 1;
			output[outIdx4] = curOut4 + samples[sampleIdx3] * nextVol6;
			vol = nextVol6 + scaledVolDelta;
		}
		limit += 3;
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += samples[samplePos--] * vol;
			vol += scaledVolDelta;
		}
		stream.currentVolume = vol >> 2;
		stream.samplePosition = samplePos << 8;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "([B[IIIIIIIILclient!b;)I")
	public static int mixForwardMonoFading(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] output, @OriginalArg(2) int samplePos, @OriginalArg(3) int outPos, @OriginalArg(4) int vol, @OriginalArg(5) int volDelta, @OriginalArg(7) int maxOutPos, @OriginalArg(8) int sampleEnd, @OriginalArg(9) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		vol <<= 0x2;
		@Pc(15) int scaledVolDelta = volDelta << 2;
		@Pc(22) int limit;
		if ((limit = outPos + endSampleIdx - samplePos) > maxOutPos) {
			limit = maxOutPos;
		}
		stream.leftVolume += stream.leftVolumeDelta * (limit - outPos);
		stream.rightVolume += stream.rightVolumeDelta * (limit - outPos);
		limit -= 3;
		@Pc(54) int outIdx;
		while (outPos < limit) {
			outIdx = outPos++;
			@Pc(57) int curOut = output[outIdx];
			@Pc(60) int sampleIdx1 = samplePos + 1;
			output[outIdx] = curOut + samples[samplePos] * vol;
			@Pc(69) int nextVol = vol + scaledVolDelta;
			@Pc(71) int outIdx2 = outPos++;
			@Pc(74) int curOut2 = output[outIdx2];
			@Pc(77) int sampleIdx2 = sampleIdx1 + 1;
			output[outIdx2] = curOut2 + samples[sampleIdx1] * nextVol;
			@Pc(86) int nextVol2 = nextVol + scaledVolDelta;
			@Pc(88) int outIdx3 = outPos++;
			@Pc(91) int curOut3 = output[outIdx3];
			@Pc(94) int sampleIdx3 = sampleIdx2 + 1;
			output[outIdx3] = curOut3 + samples[sampleIdx2] * nextVol2;
			@Pc(103) int nextVol3 = nextVol2 + scaledVolDelta;
			@Pc(105) int outIdx4 = outPos++;
			@Pc(108) int curOut4 = output[outIdx4];
			samplePos = sampleIdx3 + 1;
			output[outIdx4] = curOut4 + samples[sampleIdx3] * nextVol3;
			vol = nextVol3 + scaledVolDelta;
		}
		limit += 3;
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += samples[samplePos++] * vol;
			vol += scaledVolDelta;
		}
		stream.currentVolume = vol >> 2;
		stream.samplePosition = samplePos << 8;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(Lclient!kj;III)Lclient!b;")
	public static SoundPcmStream create(@OriginalArg(0) PcmSound sound, @OriginalArg(1) int sampleRate, @OriginalArg(2) int volume, @OriginalArg(3) int pan) {
		return sound.samples == null || sound.samples.length == 0 ? null : new SoundPcmStream(sound, sampleRate, volume, pan);
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(II[B[IIIIIIIILclient!b;II)I")
	public static int mixForwardStereoResampled(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int leftVol, @OriginalArg(7) int rightVol, @OriginalArg(9) int maxOutPos, @OriginalArg(10) int sampleEnd, @OriginalArg(11) SoundPcmStream stream, @OriginalArg(12) int rate, @OriginalArg(13) int lastSample) {
		@Pc(14) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 257) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		@Pc(37) byte sample;
		@Pc(56) int outIdxR;
		@Pc(53) int interpolated;
		@Pc(55) int outIdxL;
		while (outPos < limit) {
			@Pc(33) int sampleIdx = samplePos >> 8;
			sample = samples[sampleIdx];
			interpolated = (sample << 8) + (samples[sampleIdx + 1] - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			@Pc(67) int rightIdx = outIdxR;
			outPos = outIdxR + 1;
			output[rightIdx] += interpolated * rightVol >> 6;
			samplePos += rate;
		}
		if (rate == 0 || (limit = (outPos >> 1) + (sampleEnd + rate - samplePos - 1) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		limit <<= 0x1;
		while (outPos < limit) {
			sample = samples[samplePos >> 8];
			interpolated = (sample << 8) + (lastSample - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			outIdxL = outIdxR;
			outPos = outIdxR + 1;
			output[outIdxL] += interpolated * rightVol >> 6;
			samplePos += rate;
		}
		stream.samplePosition = samplePos;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(II[B[IIIIIIILclient!b;II)I")
	public static int mixForwardMonoResampled(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int vol, @OriginalArg(8) int maxOutPos, @OriginalArg(9) int sampleEnd, @OriginalArg(10) SoundPcmStream stream, @OriginalArg(11) int rate, @OriginalArg(12) int lastSample) {
		@Pc(14) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 257) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		@Pc(29) byte sample;
		@Pc(31) int outIdx;
		while (outPos < limit) {
			@Pc(25) int sampleIdx = samplePos >> 8;
			sample = samples[sampleIdx];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (samples[sampleIdx + 1] - sample) * (samplePos & 0xFF)) * vol >> 6;
			samplePos += rate;
		}
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos - 1) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		while (outPos < limit) {
			sample = samples[samplePos >> 8];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (lastSample - sample) * (samplePos & 0xFF)) * vol >> 6;
			samplePos += rate;
		}
		stream.samplePosition = samplePos;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "e", descriptor = "(II)I")
	public static int calculateRightVolume(@OriginalArg(0) int volume, @OriginalArg(1) int pan) {
		return pan < 0 ? -volume : (int) ((double) volume * Math.sqrt((double) pan * 1.220703125E-4D) + 0.5D);
	}

	@OriginalMember(owner = "client!b", name = "d", descriptor = "(II)I")
	public static int calculateLeftVolume(@OriginalArg(0) int volume, @OriginalArg(1) int pan) {
		return pan < 0 ? volume : (int) ((double) volume * Math.sqrt((double) (16384 - pan) * 1.220703125E-4D) + 0.5D);
	}

	@OriginalMember(owner = "client!b", name = "d", descriptor = "(II[B[IIIIIIIILclient!b;II)I")
	public static int mixBackwardStereoResampled(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int leftVol, @OriginalArg(7) int rightVol, @OriginalArg(9) int maxOutPos, @OriginalArg(10) int sampleEnd, @OriginalArg(11) SoundPcmStream stream, @OriginalArg(12) int rate, @OriginalArg(13) int lastSample) {
		@Pc(14) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate + 256 - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		@Pc(56) int outIdxR;
		@Pc(53) int interpolated;
		@Pc(55) int outIdxL;
		while (outPos < limit) {
			@Pc(33) int sampleIdx = samplePos >> 8;
			@Pc(39) byte sample = samples[sampleIdx - 1];
			interpolated = (sample << 8) + (samples[sampleIdx] - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			@Pc(67) int rightIdx = outIdxR;
			outPos = outIdxR + 1;
			output[rightIdx] += interpolated * rightVol >> 6;
			samplePos += rate;
		}
		if (rate == 0 || (limit = (outPos >> 1) + (sampleEnd + rate - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		limit <<= 0x1;
		while (outPos < limit) {
			interpolated = (lastSample << 8) + (samples[samplePos >> 8] - lastSample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			outIdxL = outIdxR;
			outPos = outIdxR + 1;
			output[outIdxL] += interpolated * rightVol >> 6;
			samplePos += rate;
		}
		stream.samplePosition = samplePos;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "([B[IIIIIIILclient!b;)I")
	public static int mixBackwardMono(@OriginalArg(0) byte[] samples, @OriginalArg(1) int[] output, @OriginalArg(2) int samplePos, @OriginalArg(3) int outPos, @OriginalArg(4) int vol, @OriginalArg(6) int maxOutPos, @OriginalArg(7) int sampleEnd, @OriginalArg(8) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		@Pc(11) int scaledVol = vol << 2;
		@Pc(20) int limit;
		if ((limit = outPos + samplePos + 1 - endSampleIdx) > maxOutPos) {
			limit = maxOutPos;
		}
		limit -= 3;
		@Pc(30) int outIdx;
		while (outPos < limit) {
			outIdx = outPos++;
			@Pc(33) int curOut = output[outIdx];
			@Pc(36) int sampleIdx1 = samplePos - 1;
			output[outIdx] = curOut + samples[samplePos] * scaledVol;
			@Pc(43) int outIdx2 = outPos++;
			@Pc(46) int curOut2 = output[outIdx2];
			@Pc(49) int sampleIdx2 = sampleIdx1 - 1;
			output[outIdx2] = curOut2 + samples[sampleIdx1] * scaledVol;
			@Pc(56) int outIdx3 = outPos++;
			@Pc(59) int curOut3 = output[outIdx3];
			@Pc(62) int sampleIdx3 = sampleIdx2 - 1;
			output[outIdx3] = curOut3 + samples[sampleIdx2] * scaledVol;
			@Pc(69) int outIdx4 = outPos++;
			@Pc(72) int curOut4 = output[outIdx4];
			samplePos = sampleIdx3 - 1;
			output[outIdx4] = curOut4 + samples[sampleIdx3] * scaledVol;
		}
		limit += 3;
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += samples[samplePos--] * scaledVol;
		}
		stream.samplePosition = samplePos << 8;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "c", descriptor = "(II[B[IIIIIIIILclient!b;II)I")
	public static int mixBackwardMonoResampledFading(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int vol, @OriginalArg(7) int volDelta, @OriginalArg(9) int maxOutPos, @OriginalArg(10) int sampleEnd, @OriginalArg(11) SoundPcmStream stream, @OriginalArg(12) int rate, @OriginalArg(13) int lastSample) {
		stream.leftVolume -= stream.leftVolumeDelta * outPos;
		stream.rightVolume -= stream.rightVolumeDelta * outPos;
		@Pc(32) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate + 256 - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		@Pc(51) int outIdx;
		while (outPos < limit) {
			@Pc(43) int sampleIdx = samplePos >> 8;
			@Pc(49) byte sample = samples[sampleIdx - 1];
			outIdx = outPos++;
			output[outIdx] += ((sample << 8) + (samples[sampleIdx] - sample) * (samplePos & 0xFF)) * vol >> 6;
			vol += volDelta;
			samplePos += rate;
		}
		if (rate == 0 || (limit = outPos + (sampleEnd + rate - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		while (outPos < limit) {
			outIdx = outPos++;
			output[outIdx] += ((lastSample << 8) + (samples[samplePos >> 8] - lastSample) * (samplePos & 0xFF)) * vol >> 6;
			vol += volDelta;
			samplePos += rate;
		}
		stream.leftVolume += stream.leftVolumeDelta * outPos;
		stream.rightVolume += stream.rightVolumeDelta * outPos;
		stream.currentVolume = vol;
		stream.samplePosition = samplePos;
		return outPos;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(I[B[IIIIIIIILclient!b;)I")
	public static int mixBackwardStereo(@OriginalArg(1) byte[] samples, @OriginalArg(2) int[] output, @OriginalArg(3) int samplePos, @OriginalArg(4) int outPos, @OriginalArg(5) int leftVol, @OriginalArg(6) int rightVol, @OriginalArg(8) int maxOutPos, @OriginalArg(9) int sampleEnd, @OriginalArg(10) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		@Pc(11) int scaledLeftVol = leftVol << 2;
		@Pc(15) int scaledRightVol = rightVol << 2;
		@Pc(24) int limit;
		if ((limit = outPos + samplePos + 1 - endSampleIdx) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		limit -= 6;
		@Pc(45) byte sample;
		@Pc(48) int outIdxL;
		while (outPos < limit) {
			@Pc(43) int sampleIdx1 = samplePos - 1;
			sample = samples[samplePos];
			outIdxL = outPos + 1;
			output[outPos] += sample * scaledLeftVol;
			@Pc(58) int outIdxL2 = outIdxL + 1;
			output[outIdxL] += sample * scaledRightVol;
			@Pc(68) int sampleIdx2 = sampleIdx1 - 1;
			@Pc(70) byte sample2 = samples[sampleIdx1];
			@Pc(73) int outIdxR = outIdxL2 + 1;
			output[outIdxL2] += sample2 * scaledLeftVol;
			@Pc(83) int outIdxR2 = outIdxR + 1;
			output[outIdxR] += sample2 * scaledRightVol;
			@Pc(93) int sampleIdx3 = sampleIdx2 - 1;
			@Pc(95) byte sample3 = samples[sampleIdx2];
			@Pc(98) int outIdxL3 = outIdxR2 + 1;
			output[outIdxR2] += sample3 * scaledLeftVol;
			@Pc(108) int outIdxL4 = outIdxL3 + 1;
			output[outIdxL3] += sample3 * scaledRightVol;
			samplePos = sampleIdx3 - 1;
			@Pc(120) byte sample4 = samples[sampleIdx3];
			@Pc(123) int outIdxR3 = outIdxL4 + 1;
			output[outIdxL4] += sample4 * scaledLeftVol;
			outPos = outIdxR3 + 1;
			output[outIdxR3] += sample4 * scaledRightVol;
		}
		limit += 6;
		while (outPos < limit) {
			sample = samples[samplePos--];
			outIdxL = outPos + 1;
			output[outPos] += sample * scaledLeftVol;
			outPos = outIdxL + 1;
			output[outIdxL] += sample * scaledRightVol;
		}
		stream.samplePosition = samplePos << 8;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(II[B[IIIIIIIIIILclient!b;II)I")
	public static int mixBackwardStereoResampledFading(@OriginalArg(2) byte[] samples, @OriginalArg(3) int[] output, @OriginalArg(4) int samplePos, @OriginalArg(5) int outPos, @OriginalArg(6) int leftVol, @OriginalArg(7) int rightVol, @OriginalArg(8) int leftVolDelta, @OriginalArg(9) int rightVolDelta, @OriginalArg(11) int maxOutPos, @OriginalArg(12) int sampleEnd, @OriginalArg(13) SoundPcmStream stream, @OriginalArg(14) int rate, @OriginalArg(15) int lastSample) {
		stream.currentVolume -= stream.volumeDelta * outPos;
		@Pc(23) int limit;
		if (rate == 0 || (limit = outPos + (sampleEnd + rate + 256 - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		outPos <<= 0x1;
		limit <<= 0x1;
		@Pc(65) int outIdxR;
		@Pc(62) int interpolated;
		@Pc(64) int outIdxL;
		while (outPos < limit) {
			@Pc(42) int sampleIdx = samplePos >> 8;
			@Pc(48) byte sample = samples[sampleIdx - 1];
			interpolated = (sample << 8) + (samples[sampleIdx] - sample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			leftVol += leftVolDelta;
			@Pc(80) int rightIdx = outIdxR;
			outPos = outIdxR + 1;
			output[rightIdx] += interpolated * rightVol >> 6;
			rightVol += rightVolDelta;
			samplePos += rate;
		}
		if (rate == 0 || (limit = (outPos >> 1) + (sampleEnd + rate - samplePos) / rate) > maxOutPos) {
			limit = maxOutPos;
		}
		limit <<= 0x1;
		while (outPos < limit) {
			interpolated = (lastSample << 8) + (samples[samplePos >> 8] - lastSample) * (samplePos & 0xFF);
			outIdxL = outPos;
			outIdxR = outPos + 1;
			output[outIdxL] += interpolated * leftVol >> 6;
			leftVol += leftVolDelta;
			outIdxL = outIdxR;
			outPos = outIdxR + 1;
			output[outIdxL] += interpolated * rightVol >> 6;
			rightVol += rightVolDelta;
			samplePos += rate;
		}
		outIdxR = outPos >> 1;
		stream.currentVolume += stream.volumeDelta * outIdxR;
		stream.leftVolume = leftVol;
		stream.rightVolume = rightVol;
		stream.samplePosition = samplePos;
		return outIdxR;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(I[B[IIIIIIIIIILclient!b;)I")
	public static int mixBackwardStereoFading(@OriginalArg(1) byte[] samples, @OriginalArg(2) int[] output, @OriginalArg(3) int samplePos, @OriginalArg(4) int outPos, @OriginalArg(5) int leftVol, @OriginalArg(6) int rightVol, @OriginalArg(7) int leftVolDelta, @OriginalArg(8) int rightVolDelta, @OriginalArg(10) int maxOutPos, @OriginalArg(11) int sampleEnd, @OriginalArg(12) SoundPcmStream stream) {
		samplePos >>= 0x8;
		@Pc(7) int endSampleIdx = sampleEnd >> 8;
		leftVol <<= 0x2;
		rightVol <<= 0x2;
		@Pc(19) int scaledLeftVolDelta = leftVolDelta << 2;
		@Pc(23) int scaledRightVolDelta = rightVolDelta << 2;
		@Pc(32) int limit;
		if ((limit = outPos + samplePos + 1 - endSampleIdx) > maxOutPos) {
			limit = maxOutPos;
		}
		stream.currentVolume += stream.volumeDelta * (limit - outPos);
		outPos <<= 0x1;
		limit <<= 0x1;
		limit -= 6;
		@Pc(64) byte sample;
		@Pc(67) int outIdxL;
		while (outPos < limit) {
			@Pc(62) int sampleIdx1 = samplePos - 1;
			sample = samples[samplePos];
			outIdxL = outPos + 1;
			output[outPos] += sample * leftVol;
			@Pc(78) int nextLeftVol = leftVol + scaledLeftVolDelta;
			@Pc(81) int outIdxR = outIdxL + 1;
			output[outIdxL] += sample * rightVol;
			@Pc(92) int nextRightVol = rightVol + scaledRightVolDelta;
			@Pc(95) int sampleIdx2 = sampleIdx1 - 1;
			@Pc(97) byte sample2 = samples[sampleIdx1];
			@Pc(100) int outIdxR2 = outIdxR + 1;
			output[outIdxR] += sample2 * nextLeftVol;
			@Pc(111) int nextLeftVol2 = nextLeftVol + scaledLeftVolDelta;
			@Pc(114) int outIdxL3 = outIdxR2 + 1;
			output[outIdxR2] += sample2 * nextRightVol;
			@Pc(125) int nextRightVol2 = nextRightVol + scaledRightVolDelta;
			@Pc(128) int sampleIdx3 = sampleIdx2 - 1;
			@Pc(130) byte sample3 = samples[sampleIdx2];
			@Pc(133) int outIdxR3 = outIdxL3 + 1;
			output[outIdxL3] += sample3 * nextLeftVol2;
			@Pc(144) int nextLeftVol3 = nextLeftVol2 + scaledLeftVolDelta;
			@Pc(147) int outIdxL4 = outIdxR3 + 1;
			output[outIdxR3] += sample3 * nextRightVol2;
			@Pc(158) int nextRightVol3 = nextRightVol2 + scaledRightVolDelta;
			samplePos = sampleIdx3 - 1;
			@Pc(163) byte sample5 = samples[sampleIdx3];
			@Pc(166) int outIdxR5 = outIdxL4 + 1;
			output[outIdxL4] += sample5 * nextLeftVol3;
			leftVol = nextLeftVol3 + scaledLeftVolDelta;
			outPos = outIdxR5 + 1;
			output[outIdxR5] += sample5 * nextRightVol3;
			rightVol = nextRightVol3 + scaledRightVolDelta;
		}
		limit += 6;
		while (outPos < limit) {
			sample = samples[samplePos--];
			outIdxL = outPos + 1;
			output[outPos] += sample * leftVol;
			leftVol += scaledLeftVolDelta;
			outPos = outIdxL + 1;
			output[outIdxL] += sample * rightVol;
			rightVol += scaledRightVolDelta;
		}
		stream.leftVolume = leftVol >> 2;
		stream.rightVolume = rightVol >> 2;
		stream.samplePosition = samplePos << 8;
		return outPos >> 1;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "([III)V")
	@Override
	public final synchronized void read(@OriginalArg(0) int[] buffer, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		if (this.volume == 0 && this.fadeRemaining == 0) {
			this.skip(length);
			return;
		}
		@Pc(13) PcmSound sound = (PcmSound) this.sound;
		@Pc(18) int loopStart = this.start << 8;
		@Pc(23) int loopEnd = this.end << 8;
		@Pc(29) int sampleLength = sound.samples.length << 8;
		@Pc(33) int loopLength = loopEnd - loopStart;
		if (loopLength <= 0) {
			this.loopCount = 0;
		}
		@Pc(40) int pos = offset;
		@Pc(44) int endPos = length + offset;
		if (this.samplePosition < 0) {
			if (this.sampleRate <= 0) {
				this.cancelTransition();
				this.unlink();
				return;
			}
			this.samplePosition = 0;
		}
		if (this.samplePosition >= sampleLength) {
			if (this.sampleRate >= 0) {
				this.cancelTransition();
				this.unlink();
				return;
			}
			this.samplePosition = sampleLength - 1;
		}
		if (this.loopCount >= 0) {
			if (this.loopCount > 0) {
				if (this.pingPongLoop) {
					loopDone:
					{
						if (this.sampleRate < 0) {
							pos = this.readBackward(buffer, offset, loopStart, endPos, sound.samples[this.start]);
							if (this.samplePosition >= loopStart) {
								return;
							}
							this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
							if (--this.loopCount == 0) {
								break loopDone;
							}
						}
						do {
							pos = this.readForward(buffer, pos, loopEnd, endPos, sound.samples[this.end - 1]);
							if (this.samplePosition < loopEnd) {
								return;
							}
							this.samplePosition = loopEnd + loopEnd - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
							if (--this.loopCount == 0) {
								break;
							}
							pos = this.readBackward(buffer, pos, loopStart, endPos, sound.samples[this.start]);
							if (this.samplePosition >= loopStart) {
								return;
							}
							this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
						} while (--this.loopCount != 0);
					}
				} else {
					@Pc(417) int loopSkips;
					if (this.sampleRate < 0) {
						while (true) {
							pos = this.readBackward(buffer, pos, loopStart, endPos, sound.samples[this.end - 1]);
							if (this.samplePosition >= loopStart) {
								return;
							}
							loopSkips = (loopEnd - this.samplePosition - 1) / loopLength;
							if (loopSkips >= this.loopCount) {
								this.samplePosition += loopLength * this.loopCount;
								this.loopCount = 0;
								break;
							}
							this.samplePosition += loopLength * loopSkips;
							this.loopCount -= loopSkips;
						}
					} else {
						while (true) {
							pos = this.readForward(buffer, pos, loopEnd, endPos, sound.samples[this.start]);
							if (this.samplePosition < loopEnd) {
								return;
							}
							loopSkips = (this.samplePosition - loopStart) / loopLength;
							if (loopSkips >= this.loopCount) {
								this.samplePosition -= loopLength * this.loopCount;
								this.loopCount = 0;
								break;
							}
							this.samplePosition -= loopLength * loopSkips;
							this.loopCount -= loopSkips;
						}
					}
				}
			}
			if (this.sampleRate < 0) {
				this.readBackward(buffer, pos, 0, endPos, 0);
				if (this.samplePosition < 0) {
					this.samplePosition = -1;
					this.cancelTransition();
					this.unlink();
				}
			} else {
				this.readForward(buffer, pos, sampleLength, endPos, 0);
				if (this.samplePosition >= sampleLength) {
					this.samplePosition = sampleLength;
					this.cancelTransition();
					this.unlink();
				}
			}
		} else if (this.pingPongLoop) {
			if (this.sampleRate < 0) {
				pos = this.readBackward(buffer, offset, loopStart, endPos, sound.samples[this.start]);
				if (this.samplePosition >= loopStart) {
					return;
				}
				this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
			}
			while (true) {
				pos = this.readForward(buffer, pos, loopEnd, endPos, sound.samples[this.end - 1]);
				if (this.samplePosition < loopEnd) {
					return;
				}
				this.samplePosition = loopEnd + loopEnd - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
				pos = this.readBackward(buffer, pos, loopStart, endPos, sound.samples[this.start]);
				if (this.samplePosition >= loopStart) {
					return;
				}
				this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
			}
		} else if (this.sampleRate < 0) {
			while (true) {
				pos = this.readBackward(buffer, pos, loopStart, endPos, sound.samples[this.end - 1]);
				if (this.samplePosition >= loopStart) {
					return;
				}
				this.samplePosition = loopEnd - (loopEnd - 1 - this.samplePosition) % loopLength - 1;
			}
		} else {
			while (true) {
				pos = this.readForward(buffer, pos, loopEnd, endPos, sound.samples[this.start]);
				if (this.samplePosition < loopEnd) {
					return;
				}
				this.samplePosition = loopStart + (this.samplePosition - loopStart) % loopLength;
			}
		}
	}

	@OriginalMember(owner = "client!b", name = "e", descriptor = "()Z")
	private boolean applyVolumeTransition() {
		@Pc(2) int targetVolume = this.volume;
		@Pc(10) int targetLeftVol;
		@Pc(8) int targetRightVol;
		if (targetVolume == Integer.MIN_VALUE) {
			targetRightVol = 0;
			targetLeftVol = 0;
			targetVolume = 0;
		} else {
			targetLeftVol = calculateLeftVolume(targetVolume, this.pan);
			targetRightVol = calculateRightVolume(targetVolume, this.pan);
		}
		if (this.currentVolume != targetVolume || this.leftVolume != targetLeftVol || this.rightVolume != targetRightVol) {
			if (this.currentVolume < targetVolume) {
				this.volumeDelta = 1;
				this.fadeRemaining = targetVolume - this.currentVolume;
			} else if (this.currentVolume > targetVolume) {
				this.volumeDelta = -1;
				this.fadeRemaining = this.currentVolume - targetVolume;
			} else {
				this.volumeDelta = 0;
			}
			if (this.leftVolume < targetLeftVol) {
				this.leftVolumeDelta = 1;
				if (this.fadeRemaining == 0 || this.fadeRemaining > targetLeftVol - this.leftVolume) {
					this.fadeRemaining = targetLeftVol - this.leftVolume;
				}
			} else if (this.leftVolume > targetLeftVol) {
				this.leftVolumeDelta = -1;
				if (this.fadeRemaining == 0 || this.fadeRemaining > this.leftVolume - targetLeftVol) {
					this.fadeRemaining = this.leftVolume - targetLeftVol;
				}
			} else {
				this.leftVolumeDelta = 0;
			}
			if (this.rightVolume < targetRightVol) {
				this.rightVolumeDelta = 1;
				if (this.fadeRemaining == 0 || this.fadeRemaining > targetRightVol - this.rightVolume) {
					this.fadeRemaining = targetRightVol - this.rightVolume;
				}
			} else if (this.rightVolume > targetRightVol) {
				this.rightVolumeDelta = -1;
				if (this.fadeRemaining == 0 || this.fadeRemaining > this.rightVolume - targetRightVol) {
					this.fadeRemaining = this.rightVolume - targetRightVol;
				}
			} else {
				this.rightVolumeDelta = 0;
			}
			return false;
		} else if (this.volume == Integer.MIN_VALUE) {
			this.volume = 0;
			this.currentVolume = this.leftVolume = this.rightVolume = 0;
			this.unlink();
			return true;
		} else {
			this.recalculateChannelVolumes();
			return false;
		}
	}

	@OriginalMember(owner = "client!b", name = "d", descriptor = "(I)V")
	public final synchronized void fadeOutAndRelease(@OriginalArg(0) int duration) {
		if (duration == 0) {
			this.resetVolume();
			this.unlink();
		} else if (this.leftVolume == 0 && this.rightVolume == 0) {
			this.fadeRemaining = 0;
			this.volume = 0;
			this.currentVolume = 0;
			this.unlink();
		} else {
			@Pc(31) int maxDuration = -this.currentVolume;
			if (this.currentVolume > maxDuration) {
				maxDuration = this.currentVolume;
			}
			if (-this.leftVolume > maxDuration) {
				maxDuration = -this.leftVolume;
			}
			if (this.leftVolume > maxDuration) {
				maxDuration = this.leftVolume;
			}
			if (-this.rightVolume > maxDuration) {
				maxDuration = -this.rightVolume;
			}
			if (this.rightVolume > maxDuration) {
				maxDuration = this.rightVolume;
			}
			if (duration > maxDuration) {
				duration = maxDuration;
			}
			this.fadeRemaining = duration;
			this.volume = Integer.MIN_VALUE;
			this.volumeDelta = -this.currentVolume / duration;
			this.leftVolumeDelta = -this.leftVolume / duration;
			this.rightVolumeDelta = -this.rightVolume / duration;
		}
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "([IIIII)I")
	private int readForward(@OriginalArg(0) int[] output, @OriginalArg(1) int outPos, @OriginalArg(2) int sampleEnd, @OriginalArg(3) int maxOutPos, @OriginalArg(4) int boundarySample) {
		while (true) {
			if (this.fadeRemaining > 0) {
				@Pc(7) int fadeEnd = outPos + this.fadeRemaining;
				if (fadeEnd > maxOutPos) {
					fadeEnd = maxOutPos;
				}
				this.fadeRemaining += outPos;
				if (this.sampleRate == 256 && (this.samplePosition & 0xFF) == 0) {
					if (AudioChannel.stereo) {
						outPos = mixForwardStereoFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, this.leftVolumeDelta, this.rightVolumeDelta, fadeEnd, sampleEnd, this);
					} else {
						outPos = mixForwardMonoFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, this.volumeDelta, fadeEnd, sampleEnd, this);
					}
				} else if (AudioChannel.stereo) {
					outPos = mixForwardStereoResampledFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, this.leftVolumeDelta, this.rightVolumeDelta, fadeEnd, sampleEnd, this, this.sampleRate, boundarySample);
				} else {
					outPos = mixForwardMonoResampledFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, this.volumeDelta, fadeEnd, sampleEnd, this, this.sampleRate, boundarySample);
				}
				this.fadeRemaining -= outPos;
				if (this.fadeRemaining != 0) {
					return outPos;
				}
				if (!this.applyVolumeTransition()) {
					continue;
				}
				return maxOutPos;
			}
			if (this.sampleRate == 256 && (this.samplePosition & 0xFF) == 0) {
				if (AudioChannel.stereo) {
					return mixForwardStereo(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, maxOutPos, sampleEnd, this);
				}
				return mixForwardMono(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, maxOutPos, sampleEnd, this);
			}
			if (AudioChannel.stereo) {
				return mixForwardStereoResampled(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, maxOutPos, sampleEnd, this, this.sampleRate, boundarySample);
			}
			return mixForwardMonoResampled(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, maxOutPos, sampleEnd, this, this.sampleRate, boundarySample);
		}
	}

	@OriginalMember(owner = "client!b", name = "e", descriptor = "(I)V")
	public final synchronized void setVolume(@OriginalArg(0) int volume) {
		this.setVolumeAndPan(volume << 6, this.getPan());
	}

	@OriginalMember(owner = "client!b", name = "c", descriptor = "()I")
	@Override
	public final int getEffectiveVolume() {
		@Pc(6) int effectiveVol = this.currentVolume * 3 >> 6;
		effectiveVol = (effectiveVol ^ effectiveVol >> 31) + (effectiveVol >>> 31);
		if (this.loopCount == 0) {
			effectiveVol -= effectiveVol * this.samplePosition / (((PcmSound) this.sound).samples.length << 8);
		} else if (this.loopCount >= 0) {
			effectiveVol -= effectiveVol * this.start / ((PcmSound) this.sound).samples.length;
		}
		return effectiveVol > 255 ? 255 : effectiveVol;
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "()I")
	@Override
	public final int getActiveChannelCount() {
		return this.volume == 0 && this.fadeRemaining == 0 ? 0 : 1;
	}

	@OriginalMember(owner = "client!b", name = "f", descriptor = "()I")
	public final synchronized int getVolume() {
		return this.volume == Integer.MIN_VALUE ? 0 : this.volume;
	}

	@OriginalMember(owner = "client!b", name = "c", descriptor = "(I)V")
	@Override
	public final synchronized void skip(@OriginalArg(0) int length) {
		if (this.fadeRemaining > 0) {
			if (length >= this.fadeRemaining) {
				if (this.volume == Integer.MIN_VALUE) {
					this.volume = 0;
					this.currentVolume = this.leftVolume = this.rightVolume = 0;
					this.unlink();
					length = this.fadeRemaining;
				}
				this.fadeRemaining = 0;
				this.recalculateChannelVolumes();
			} else {
				this.currentVolume += this.volumeDelta * length;
				this.leftVolume += this.leftVolumeDelta * length;
				this.rightVolume += this.rightVolumeDelta * length;
				this.fadeRemaining -= length;
			}
		}
		@Pc(71) PcmSound sound = (PcmSound) this.sound;
		@Pc(76) int loopStart = this.start << 8;
		@Pc(81) int loopEnd = this.end << 8;
		@Pc(87) int sampleLength = sound.samples.length << 8;
		@Pc(91) int loopLength = loopEnd - loopStart;
		if (loopLength <= 0) {
			this.loopCount = 0;
		}
		if (this.samplePosition < 0) {
			if (this.sampleRate <= 0) {
				this.cancelTransition();
				this.unlink();
				return;
			}
			this.samplePosition = 0;
		}
		if (this.samplePosition >= sampleLength) {
			if (this.sampleRate >= 0) {
				this.cancelTransition();
				this.unlink();
				return;
			}
			this.samplePosition = sampleLength - 1;
		}
		this.samplePosition += this.sampleRate * length;
		if (this.loopCount >= 0) {
			if (this.loopCount > 0) {
				if (this.pingPongLoop) {
					loopDone:
					{
						if (this.sampleRate < 0) {
							if (this.samplePosition >= loopStart) {
								return;
							}
							this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
							if (--this.loopCount == 0) {
								break loopDone;
							}
						}
						do {
							if (this.samplePosition < loopEnd) {
								return;
							}
							this.samplePosition = loopEnd + loopEnd - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
							if (--this.loopCount == 0) {
								break;
							}
							if (this.samplePosition >= loopStart) {
								return;
							}
							this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
							this.sampleRate = -this.sampleRate;
						} while (--this.loopCount != 0);
					}
				} else {
					loopDone:
					{
						@Pc(362) int loopSkips;
						if (this.sampleRate < 0) {
							if (this.samplePosition >= loopStart) {
								return;
							}
							loopSkips = (loopEnd - this.samplePosition - 1) / loopLength;
							if (loopSkips >= this.loopCount) {
								this.samplePosition += loopLength * this.loopCount;
								this.loopCount = 0;
								break loopDone;
							}
							this.samplePosition += loopLength * loopSkips;
							this.loopCount -= loopSkips;
						} else if (this.samplePosition >= loopEnd) {
							loopSkips = (this.samplePosition - loopStart) / loopLength;
							if (loopSkips >= this.loopCount) {
								this.samplePosition -= loopLength * this.loopCount;
								this.loopCount = 0;
								break loopDone;
							}
							this.samplePosition -= loopLength * loopSkips;
							this.loopCount -= loopSkips;
						} else {
							return;
						}
						return;
					}
				}
			}
			if (this.sampleRate < 0) {
				if (this.samplePosition < 0) {
					this.samplePosition = -1;
					this.cancelTransition();
					this.unlink();
				}
			} else if (this.samplePosition >= sampleLength) {
				this.samplePosition = sampleLength;
				this.cancelTransition();
				this.unlink();
			}
		} else if (this.pingPongLoop) {
			if (this.sampleRate < 0) {
				if (this.samplePosition >= loopStart) {
					return;
				}
				this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
			}
			while (this.samplePosition >= loopEnd) {
				this.samplePosition = loopEnd + loopEnd - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
				if (this.samplePosition >= loopStart) {
					return;
				}
				this.samplePosition = loopStart + loopStart - this.samplePosition - 1;
				this.sampleRate = -this.sampleRate;
			}
		} else if (this.sampleRate < 0) {
			if (this.samplePosition >= loopStart) {
				return;
			}
			this.samplePosition = loopEnd - (loopEnd - 1 - this.samplePosition) % loopLength - 1;
		} else if (this.samplePosition >= loopEnd) {
			this.samplePosition = loopStart + (this.samplePosition - loopStart) % loopLength;
		} else {
			return;
		}
	}

	@OriginalMember(owner = "client!b", name = "f", descriptor = "(I)V")
	public final synchronized void setLoops(@OriginalArg(0) int loops) {
		this.loopCount = loops;
	}

	@OriginalMember(owner = "client!b", name = "g", descriptor = "(I)V")
	private synchronized void resetVolume() {
		this.setVolumeAndPan(0, this.getPan());
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "(II)V")
	public final synchronized void fadeToVolume(@OriginalArg(0) int volume, @OriginalArg(1) int pan) {
		this.fadeToVolumeAndPan(volume, pan, this.getPan());
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream firstSubStream() {
		return null;
	}

	@OriginalMember(owner = "client!b", name = "h", descriptor = "(I)V")
	public final synchronized void setSamplePosition(@OriginalArg(0) int position) {
		@Pc(7) int maxPosition = ((PcmSound) this.sound).samples.length << 8;
		if (position < -1) {
			position = -1;
		}
		if (position > maxPosition) {
			position = maxPosition;
		}
		this.samplePosition = position;
	}

	@OriginalMember(owner = "client!b", name = "b", descriptor = "([IIIII)I")
	private int readBackward(@OriginalArg(0) int[] output, @OriginalArg(1) int outPos, @OriginalArg(2) int sampleEnd, @OriginalArg(3) int maxOutPos, @OriginalArg(4) int boundarySample) {
		while (true) {
			if (this.fadeRemaining > 0) {
				@Pc(7) int fadeEnd = outPos + this.fadeRemaining;
				if (fadeEnd > maxOutPos) {
					fadeEnd = maxOutPos;
				}
				this.fadeRemaining += outPos;
				if (this.sampleRate == -256 && (this.samplePosition & 0xFF) == 0) {
					if (AudioChannel.stereo) {
						outPos = mixBackwardStereoFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, this.leftVolumeDelta, this.rightVolumeDelta, fadeEnd, sampleEnd, this);
					} else {
						outPos = mixBackwardMonoFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, this.volumeDelta, fadeEnd, sampleEnd, this);
					}
				} else if (AudioChannel.stereo) {
					outPos = mixBackwardStereoResampledFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, this.leftVolumeDelta, this.rightVolumeDelta, fadeEnd, sampleEnd, this, this.sampleRate, boundarySample);
				} else {
					outPos = mixBackwardMonoResampledFading(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, this.volumeDelta, fadeEnd, sampleEnd, this, this.sampleRate, boundarySample);
				}
				this.fadeRemaining -= outPos;
				if (this.fadeRemaining != 0) {
					return outPos;
				}
				if (!this.applyVolumeTransition()) {
					continue;
				}
				return maxOutPos;
			}
			if (this.sampleRate == -256 && (this.samplePosition & 0xFF) == 0) {
				if (AudioChannel.stereo) {
					return mixBackwardStereo(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, maxOutPos, sampleEnd, this);
				}
				return mixBackwardMono(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, maxOutPos, sampleEnd, this);
			}
			if (AudioChannel.stereo) {
				return mixBackwardStereoResampled(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.leftVolume, this.rightVolume, maxOutPos, sampleEnd, this, this.sampleRate, boundarySample);
			}
			return mixBackwardMonoResampled(((PcmSound) this.sound).samples, output, this.samplePosition, outPos, this.currentVolume, maxOutPos, sampleEnd, this, this.sampleRate, boundarySample);
		}
	}

	@OriginalMember(owner = "client!b", name = "g", descriptor = "()I")
	public final synchronized int getAbsoluteRate() {
		return this.sampleRate < 0 ? -this.sampleRate : this.sampleRate;
	}

	@OriginalMember(owner = "client!b", name = "h", descriptor = "()V")
	private void cancelTransition() {
		if (this.fadeRemaining == 0) {
			return;
		}
		if (this.volume == Integer.MIN_VALUE) {
			this.volume = 0;
		}
		this.fadeRemaining = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!b", name = "c", descriptor = "(II)V")
	private synchronized void setVolumeAndPan(@OriginalArg(0) int volume, @OriginalArg(1) int pan) {
		this.volume = volume;
		this.pan = pan;
		this.fadeRemaining = 0;
		this.recalculateChannelVolumes();
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(Z)V")
	public final synchronized void reverseDirection() {
		this.sampleRate = (this.sampleRate ^ this.sampleRate >> 31) + (this.sampleRate >>> 31);
		this.sampleRate = -this.sampleRate;
	}

	@OriginalMember(owner = "client!b", name = "i", descriptor = "(I)V")
	public final synchronized void setRate(@OriginalArg(0) int rate) {
		if (this.sampleRate < 0) {
			this.sampleRate = -rate;
		} else {
			this.sampleRate = rate;
		}
	}

	@OriginalMember(owner = "client!b", name = "i", descriptor = "()Z")
	public final boolean isOutOfBounds() {
		return this.samplePosition < 0 || this.samplePosition >= ((PcmSound) this.sound).samples.length << 8;
	}

	@OriginalMember(owner = "client!b", name = "j", descriptor = "()Z")
	public final boolean isTransitioning() {
		return this.fadeRemaining != 0;
	}

	@OriginalMember(owner = "client!b", name = "k", descriptor = "()V")
	private void recalculateChannelVolumes() {
		this.currentVolume = this.volume;
		this.leftVolume = calculateLeftVolume(this.volume, this.pan);
		this.rightVolume = calculateRightVolume(this.volume, this.pan);
	}

	@OriginalMember(owner = "client!b", name = "a", descriptor = "(III)V")
	public final synchronized void fadeToVolumeAndPan(@OriginalArg(0) int duration, @OriginalArg(1) int targetVolume, @OriginalArg(2) int targetPan) {
		if (duration == 0) {
			this.setVolumeAndPan(targetVolume, targetPan);
			return;
		}
		@Pc(10) int targetRight = calculateLeftVolume(targetVolume, targetPan);
		@Pc(14) int targetLeft = calculateRightVolume(targetVolume, targetPan);
		if (this.leftVolume == targetRight && this.rightVolume == targetLeft) {
			this.fadeRemaining = 0;
			return;
		}
		@Pc(31) int maxDelta = targetVolume - this.currentVolume;
		if (this.currentVolume - targetVolume > maxDelta) {
			maxDelta = this.currentVolume - targetVolume;
		}
		if (targetRight - this.leftVolume > maxDelta) {
			maxDelta = targetRight - this.leftVolume;
		}
		if (this.leftVolume - targetRight > maxDelta) {
			maxDelta = this.leftVolume - targetRight;
		}
		if (targetLeft - this.rightVolume > maxDelta) {
			maxDelta = targetLeft - this.rightVolume;
		}
		if (this.rightVolume - targetLeft > maxDelta) {
			maxDelta = this.rightVolume - targetLeft;
		}
		if (duration > maxDelta) {
			duration = maxDelta;
		}
		this.fadeRemaining = duration;
		this.volume = targetVolume;
		this.pan = targetPan;
		this.volumeDelta = (targetVolume - this.currentVolume) / duration;
		this.leftVolumeDelta = (targetRight - this.leftVolume) / duration;
		this.rightVolumeDelta = (targetLeft - this.rightVolume) / duration;
	}

	@OriginalMember(owner = "client!b", name = "l", descriptor = "()I")
	public final synchronized int getPan() {
		return this.pan < 0 ? -1 : this.pan;
	}

	@OriginalMember(owner = "client!b", name = "d", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream nextSubStream() {
		return null;
	}
}
