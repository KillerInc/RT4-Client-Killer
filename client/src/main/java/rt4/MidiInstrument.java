package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!jk")
public final class MidiInstrument extends Node {

	@OriginalMember(owner = "client!jk", name = "r", descriptor = "[B")
	public final byte[] notePan = new byte[128];

	@OriginalMember(owner = "client!jk", name = "s", descriptor = "[I")
	private int[] soundLookup = new int[128];

	@OriginalMember(owner = "client!jk", name = "u", descriptor = "I")
	public final int volumeMultiplier;

	@OriginalMember(owner = "client!jk", name = "w", descriptor = "[B")
	public final byte[] noteExclusiveGroup;

	@OriginalMember(owner = "client!jk", name = "C", descriptor = "[B")
	public final byte[] noteVolume = new byte[128];

	@OriginalMember(owner = "client!jk", name = "D", descriptor = "[Lclient!kj;")
	public final PcmSound[] sounds = new PcmSound[128];

	@OriginalMember(owner = "client!jk", name = "H", descriptor = "[S")
	public final short[] notePitchOffset = new short[128];

	@OriginalMember(owner = "client!jk", name = "I", descriptor = "[Lclient!wh;")
	public final MidiTrackState[] trackStates = new MidiTrackState[128];

	@OriginalMember(owner = "client!jk", name = "<init>", descriptor = "([B)V")
	public MidiInstrument(@OriginalArg(0) byte[] data) {
		@Pc(29) int runLength = 0;
		this.noteExclusiveGroup = new byte[128];
		@Pc(38) Buffer buffer = new Buffer(data);
		while (buffer.data[runLength + buffer.offset] != 0) {
			runLength++;
		}
		@Pc(55) byte[] exclusiveGroupRuns = new byte[runLength];
		@Pc(57) int i;
		for (i = 0; i < runLength; i++) {
			exclusiveGroupRuns[i] = buffer.g1b();
		}
		buffer.offset++;
		runLength++;
		i = buffer.offset;
		buffer.offset += runLength;
		@Pc(91) int panRunLength;
		for (panRunLength = 0; buffer.data[buffer.offset + panRunLength] != 0; panRunLength++) {
		}
		@Pc(106) byte[] panRuns = new byte[panRunLength];
		@Pc(108) int j;
		for (j = 0; j < panRunLength; j++) {
			panRuns[j] = buffer.g1b();
		}
		buffer.offset++;
		panRunLength++;
		@Pc(133) int trackMapRunLength = 0;
		j = buffer.offset;
		buffer.offset += panRunLength;
		while (buffer.data[trackMapRunLength + buffer.offset] != 0) {
			trackMapRunLength++;
		}
		@Pc(159) byte[] trackMapRuns = new byte[trackMapRunLength];
		for (@Pc(161) int k = 0; k < trackMapRunLength; k++) {
			trackMapRuns[k] = buffer.g1b();
		}
		buffer.offset++;
		trackMapRunLength++;
		@Pc(187) byte[] trackMapping = new byte[trackMapRunLength];
		@Pc(194) int trackStateCount;
		@Pc(206) int n;
		if (trackMapRunLength <= 1) {
			trackStateCount = trackMapRunLength;
		} else {
			trackStateCount = 2;
			trackMapping[1] = 1;
			@Pc(204) int groupId = 1;
			for (n = 2; n < trackMapRunLength; n++) {
				@Pc(217) int value = buffer.g1();
				if (value == 0) {
					groupId = trackStateCount++;
				} else {
					if (value <= groupId) {
						value--;
					}
					groupId = value;
				}
				trackMapping[n] = (byte) groupId;
			}
		}
		@Pc(242) MidiTrackState[] states = new MidiTrackState[trackStateCount];
		for (n = 0; n < states.length; n++) {
			@Pc(256) MidiTrackState state = states[n] = new MidiTrackState();
			@Pc(260) int size = buffer.g1();
			if (size > 0) {
				state.attackEnvelope = new byte[size * 2];
			}
			size = buffer.g1();
			if (size > 0) {
				state.releaseEnvelope = new byte[size * 2 + 2];
				state.releaseEnvelope[1] = 64;
			}
		}
		n = buffer.g1();
		@Pc(311) byte[] volumeModifiers = n > 0 ? new byte[n * 2] : null;
		n = buffer.g1();
		@Pc(327) byte[] panModifiers = n > 0 ? new byte[n * 2] : null;
		@Pc(329) int volumeRunLength;
		for (volumeRunLength = 0; buffer.data[volumeRunLength + buffer.offset] != 0; volumeRunLength++) {
		}
		@Pc(346) byte[] volumeRuns = new byte[volumeRunLength];
		@Pc(348) int accumulator;
		for (accumulator = 0; accumulator < volumeRunLength; accumulator++) {
			volumeRuns[accumulator] = buffer.g1b();
		}
		buffer.offset++;
		volumeRunLength++;
		accumulator = 0;
		@Pc(375) int remaining;
		for (remaining = 0; remaining < 128; remaining++) {
			accumulator += buffer.g1();
			this.notePitchOffset[remaining] = (short) accumulator;
		}
		accumulator = 0;
		for (remaining = 0; remaining < 128; remaining++) {
			accumulator += buffer.g1();
			this.notePitchOffset[remaining] = (short) (this.notePitchOffset[remaining] + (accumulator << 8));
		}
		remaining = 0;
		@Pc(428) int runIndex = 0;
		@Pc(430) int soundId = 0;
		@Pc(432) int note;
		for (note = 0; note < 128; note++) {
			if (remaining == 0) {
				if (volumeRuns.length > runIndex) {
					remaining = volumeRuns[runIndex++];
				} else {
					remaining = -1;
				}
				soundId = buffer.gVarInt();
			}
			this.notePitchOffset[note] = (short) (this.notePitchOffset[note] + ((soundId - 1 & 0x2) << 14));
			this.soundLookup[note] = soundId;
			remaining--;
		}
		remaining = 0;
		note = 0;
		runIndex = 0;
		@Pc(496) int exclusiveGroup;
		for (exclusiveGroup = 0; exclusiveGroup < 128; exclusiveGroup++) {
			if (this.soundLookup[exclusiveGroup] != 0) {
				if (remaining == 0) {
					note = buffer.data[i++] - 1;
					if (exclusiveGroupRuns.length > runIndex) {
						remaining = exclusiveGroupRuns[runIndex++];
					} else {
						remaining = -1;
					}
				}
				remaining--;
				this.noteExclusiveGroup[exclusiveGroup] = (byte) note;
			}
		}
		remaining = 0;
		runIndex = 0;
		exclusiveGroup = 0;
		for (@Pc(550) int noteIdx = 0; noteIdx < 128; noteIdx++) {
			if (this.soundLookup[noteIdx] != 0) {
				if (remaining == 0) {
					exclusiveGroup = buffer.data[j++] + 16 << 2;
					if (runIndex < panRuns.length) {
						remaining = panRuns[runIndex++];
					} else {
						remaining = -1;
					}
				}
				remaining--;
				this.notePan[noteIdx] = (byte) exclusiveGroup;
			}
		}
		runIndex = 0;
		remaining = 0;
		@Pc(609) MidiTrackState currentState = null;
		@Pc(611) int noteIdx2;
		for (noteIdx2 = 0; noteIdx2 < 128; noteIdx2++) {
			if (this.soundLookup[noteIdx2] != 0) {
				if (remaining == 0) {
					currentState = states[trackMapping[runIndex]];
					if (runIndex >= trackMapRuns.length) {
						remaining = -1;
					} else {
						remaining = trackMapRuns[runIndex++];
					}
				}
				this.trackStates[noteIdx2] = currentState;
				remaining--;
			}
		}
		remaining = 0;
		runIndex = 0;
		noteIdx2 = 0;
		@Pc(664) int noteIdx3;
		for (noteIdx3 = 0; noteIdx3 < 128; noteIdx3++) {
			if (remaining == 0) {
				if (runIndex < volumeRuns.length) {
					remaining = volumeRuns[runIndex++];
				} else {
					remaining = -1;
				}
				if (this.soundLookup[noteIdx3] > 0) {
					noteIdx2 = buffer.g1() + 1;
				}
			}
			remaining--;
			this.noteVolume[noteIdx3] = (byte) noteIdx2;
		}
		this.volumeMultiplier = buffer.g1() + 1;
		@Pc(729) MidiTrackState state;
		@Pc(734) int envIdx;
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.attackEnvelope != null) {
				for (envIdx = 1; envIdx < state.attackEnvelope.length; envIdx += 2) {
					state.attackEnvelope[envIdx] = buffer.g1b();
				}
			}
			if (state.releaseEnvelope != null) {
				for (envIdx = 3; envIdx < state.releaseEnvelope.length - 2; envIdx += 2) {
					state.releaseEnvelope[envIdx] = buffer.g1b();
				}
			}
		}
		if (volumeModifiers != null) {
			for (noteIdx3 = 1; noteIdx3 < volumeModifiers.length; noteIdx3 += 2) {
				volumeModifiers[noteIdx3] = buffer.g1b();
			}
		}
		if (panModifiers != null) {
			for (noteIdx3 = 1; noteIdx3 < panModifiers.length; noteIdx3 += 2) {
				panModifiers[noteIdx3] = buffer.g1b();
			}
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.releaseEnvelope != null) {
				accumulator = 0;
				for (envIdx = 2; envIdx < state.releaseEnvelope.length; envIdx += 2) {
					accumulator -= -buffer.g1() - 1;
					state.releaseEnvelope[envIdx] = (byte) accumulator;
				}
			}
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.attackEnvelope != null) {
				accumulator = 0;
				for (envIdx = 2; envIdx < state.attackEnvelope.length; envIdx += 2) {
					accumulator = accumulator + buffer.g1() + 1;
					state.attackEnvelope[envIdx] = (byte) accumulator;
				}
			}
		}
		@Pc(995) byte nextNote;
		@Pc(1014) int interpolation;
		@Pc(1016) int noteKey;
		@Pc(1031) int scaledValue;
		@Pc(1066) int noteKey2;
		@Pc(954) byte prevNote;
		if (volumeModifiers != null) {
			accumulator = buffer.g1();
			volumeModifiers[0] = (byte) accumulator;
			for (noteIdx3 = 2; noteIdx3 < volumeModifiers.length; noteIdx3 += 2) {
				accumulator = accumulator + buffer.g1() + 1;
				volumeModifiers[noteIdx3] = (byte) accumulator;
			}
			prevNote = volumeModifiers[0];
			@Pc(958) byte prevValue = volumeModifiers[1];
			for (envIdx = 0; envIdx < prevNote; envIdx++) {
				this.noteVolume[envIdx] = (byte) (prevValue * this.noteVolume[envIdx] + 32 >> 6);
			}
			envIdx = 2;
			while (envIdx < volumeModifiers.length) {
				nextNote = volumeModifiers[envIdx];
				@Pc(1001) byte nextValue = volumeModifiers[envIdx + 1];
				envIdx += 2;
				interpolation = (nextNote - prevNote) * prevValue + (nextNote - prevNote) / 2;
				for (noteKey = prevNote; noteKey < nextNote; noteKey++) {
					scaledValue = floorDiv(interpolation, nextNote - prevNote);
					interpolation += nextValue - prevValue;
					this.noteVolume[noteKey] = (byte) (scaledValue * this.noteVolume[noteKey] + 32 >> 6);
				}
				prevValue = nextValue;
				prevNote = nextNote;
			}
			for (noteKey2 = prevNote; noteKey2 < 128; noteKey2++) {
				this.noteVolume[noteKey2] = (byte) (this.noteVolume[noteKey2] * prevValue + 32 >> 6);
			}
		}
		if (panModifiers != null) {
			accumulator = buffer.g1();
			panModifiers[0] = (byte) accumulator;
			for (noteIdx3 = 2; noteIdx3 < panModifiers.length; noteIdx3 += 2) {
				accumulator = accumulator + buffer.g1() + 1;
				panModifiers[noteIdx3] = (byte) accumulator;
			}
			prevNote = panModifiers[0];
			@Pc(1133) int prevPan = panModifiers[1] << 1;
			for (envIdx = 0; envIdx < prevNote; envIdx++) {
				noteKey2 = prevPan + (this.notePan[envIdx] & 0xFF);
				if (noteKey2 < 0) {
					noteKey2 = 0;
				}
				if (noteKey2 > 128) {
					noteKey2 = 128;
				}
				this.notePan[envIdx] = (byte) noteKey2;
			}
			envIdx = 2;
			@Pc(1207) int nextPan;
			while (envIdx < panModifiers.length) {
				nextNote = panModifiers[envIdx];
				interpolation = (nextNote - prevNote) * prevPan + (nextNote - prevNote) / 2;
				nextPan = panModifiers[envIdx + 1] << 1;
				envIdx += 2;
				for (noteKey = prevNote; noteKey < nextNote; noteKey++) {
					scaledValue = floorDiv(interpolation, nextNote - prevNote);
					interpolation += nextPan - prevPan;
					@Pc(1237) int adjustedPan = scaledValue + (this.notePan[noteKey] & 0xFF);
					if (adjustedPan < 0) {
						adjustedPan = 0;
					}
					if (adjustedPan > 128) {
						adjustedPan = 128;
					}
					this.notePan[noteKey] = (byte) adjustedPan;
				}
				prevNote = nextNote;
				prevPan = nextPan;
			}
			for (noteKey2 = prevNote; noteKey2 < 128; noteKey2++) {
				nextPan = (this.notePan[noteKey2] & 0xFF) + prevPan;
				if (nextPan < 0) {
					nextPan = 0;
				}
				if (nextPan > 128) {
					nextPan = 128;
				}
				this.notePan[noteKey2] = (byte) nextPan;
			}
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			states[noteIdx3].decayRate = buffer.g1();
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.attackEnvelope != null) {
				state.attackRate = buffer.g1();
			}
			if (state.releaseEnvelope != null) {
				state.releaseRate = buffer.g1();
			}
			if (state.decayRate > 0) {
				state.sustainRate = buffer.g1();
			}
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			states[noteIdx3].vibratoSpeed = buffer.g1();
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.vibratoSpeed > 0) {
				state.vibratoDepth = buffer.g1();
			}
		}
		for (noteIdx3 = 0; noteIdx3 < trackStateCount; noteIdx3++) {
			state = states[noteIdx3];
			if (state.vibratoDepth > 0) {
				state.vibratoDelay = buffer.g1();
			}
		}
	}

	@OriginalMember(owner = "client!sa", name = "c", descriptor = "(III)I")
	public static int floorDiv(@OriginalArg(0) int numerator, @OriginalArg(2) int denominator) {
		@Pc(12) int sign = numerator >>> 31;
		return (numerator + sign) / denominator - sign;
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(ILclient!ve;I)Lclient!jk;")
	public static MidiInstrument loadInstrument(@OriginalArg(1) Js5 js5, @OriginalArg(2) int fileId) {
		@Pc(9) byte[] data = js5.fetchFile(fileId);
		return data == null ? null : new MidiInstrument(data);
	}

	@OriginalMember(owner = "client!jk", name = "d", descriptor = "(B)V")
	public final void freeSoundTable() {
		this.soundLookup = null;
	}

	@OriginalMember(owner = "client!jk", name = "a", descriptor = "(I[ILclient!le;[B)Z")
	public final boolean resolveSounds(@OriginalArg(1) int[] pendingIds, @OriginalArg(2) SoundBank soundBank, @OriginalArg(3) byte[] filter) {
		@Pc(8) int lastSoundId = 0;
		@Pc(10) PcmSound sound = null;
		@Pc(16) boolean allResolved = true;
		for (@Pc(18) int note = 0; note < 128; note++) {
			if (filter == null || filter[note] != 0) {
				@Pc(35) int soundId = this.soundLookup[note];
				if (soundId != 0) {
					if (lastSoundId != soundId) {
						lastSoundId = soundId--;
						if ((soundId & 0x1) == 0) {
							sound = soundBank.getSynthSound(soundId >> 2, pendingIds);
						} else {
							sound = soundBank.getVorbisSound(soundId >> 2, pendingIds);
						}
						if (sound == null) {
							allResolved = false;
						}
					}
					if (sound != null) {
						this.sounds[note] = sound;
						this.soundLookup[note] = 0;
					}
				}
			}
		}
		return allResolved;
	}
}
