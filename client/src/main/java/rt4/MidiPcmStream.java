package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!va")
public final class MidiPcmStream extends PcmStream {

	@OriginalMember(owner = "client!va", name = "Kb", descriptor = "Z")
	private boolean loop;

	@OriginalMember(owner = "client!va", name = "Lb", descriptor = "J")
	private long currentTimeMicros;

	@OriginalMember(owner = "client!va", name = "Mb", descriptor = "I")
	private int currentTick;

	@OriginalMember(owner = "client!va", name = "Nb", descriptor = "I")
	private int currentTrack;

	@OriginalMember(owner = "client!va", name = "Ob", descriptor = "J")
	private long elapsedTimeMicros;

	@OriginalMember(owner = "client!va", name = "Qb", descriptor = "Lclient!rf;")
	private Song nextSong;

	@OriginalMember(owner = "client!va", name = "Rb", descriptor = "I")
	private int crossfadeTick;

	@OriginalMember(owner = "client!va", name = "Sb", descriptor = "Z")
	private boolean crossfadeReset;

	@OriginalMember(owner = "client!va", name = "z", descriptor = "[I")
	private final int[] channelPan = new int[16];

	@OriginalMember(owner = "client!va", name = "D", descriptor = "[I")
	private final int[] defaultProgram = new int[16];

	@OriginalMember(owner = "client!va", name = "F", descriptor = "[I")
	private final int[] channelModulation = new int[16];

	@OriginalMember(owner = "client!va", name = "B", descriptor = "[I")
	private final int[] channelPitchBend = new int[16];

	@OriginalMember(owner = "client!va", name = "ab", descriptor = "I")
	private final int TEMPO_SCALE = 1000000;

	@OriginalMember(owner = "client!va", name = "cb", descriptor = "[[Lclient!mf;")
	private final MidiNote[][] exclusiveGroupNotes = new MidiNote[16][128];

	@OriginalMember(owner = "client!va", name = "kb", descriptor = "[I")
	private final int[] channelProgram = new int[16];

	@OriginalMember(owner = "client!va", name = "C", descriptor = "[I")
	private final int[] channelRpnNrpn = new int[16];

	@OriginalMember(owner = "client!va", name = "U", descriptor = "[I")
	public final int[] channelDetuneRatio = new int[16];

	@OriginalMember(owner = "client!va", name = "x", descriptor = "[I")
	private final int[] channelVolume = new int[16];

	@OriginalMember(owner = "client!va", name = "ib", descriptor = "[I")
	private final int[] channelExpression = new int[16];

	@OriginalMember(owner = "client!va", name = "tb", descriptor = "[I")
	public final int[] channelFlags = new int[16];

	@OriginalMember(owner = "client!va", name = "mb", descriptor = "[I")
	private final int[] channelVolumeScalar = new int[16];

	@OriginalMember(owner = "client!va", name = "Cb", descriptor = "[I")
	private final int[] channelDetuneValue = new int[16];

	@OriginalMember(owner = "client!va", name = "N", descriptor = "[I")
	private final int[] channelBankSelect = new int[16];

	@OriginalMember(owner = "client!va", name = "Bb", descriptor = "[I")
	public final int[] channelSampleOffset = new int[16];

	@OriginalMember(owner = "client!va", name = "bb", descriptor = "[[Lclient!mf;")
	private final MidiNote[][] activeNotes = new MidiNote[16][128];

	@OriginalMember(owner = "client!va", name = "Eb", descriptor = "I")
	private int volume = 256;

	@OriginalMember(owner = "client!va", name = "H", descriptor = "[I")
	private final int[] channelPitchBendRange = new int[16];

	@OriginalMember(owner = "client!va", name = "Z", descriptor = "[I")
	private final int[] channelPortamentoTime = new int[16];

	@OriginalMember(owner = "client!va", name = "K", descriptor = "Lclient!ki;")
	private final MidiDecoder midiDecoder = new MidiDecoder();

	@OriginalMember(owner = "client!va", name = "Pb", descriptor = "Lclient!te;")
	private final MidiNoteStream noteStream = new MidiNoteStream(this);

	@OriginalMember(owner = "client!va", name = "P", descriptor = "Lclient!sc;")
	private final HashTable instrumentCache = new HashTable(128);

	@OriginalMember(owner = "client!va", name = "<init>", descriptor = "()V")
	public MidiPcmStream() {
		this.initChannelVolumeScalars();
		this.stopAllNotes(true);
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(Lclient!rf;ILclient!ve;Lclient!le;I)Z")
	public final synchronized boolean isSongReady(@OriginalArg(0) Song song, @OriginalArg(2) Js5 js5, @OriginalArg(3) SoundBank soundBank) {
		song.createPrograms();
		@Pc(5) boolean ready = true;
		@Pc(20) int[] sampleRates = new int[]{GlobalConfig.AUDIO_SAMPLE_RATE};
		for (@Pc(34) ByteArrayNode programNode = (ByteArrayNode) song.programs.head(); programNode != null; programNode = (ByteArrayNode) song.programs.next()) {
			@Pc(40) int programId = (int) programNode.key;
			@Pc(48) MidiInstrument instrument = (MidiInstrument) this.instrumentCache.get(programId);
			if (instrument == null) {
				instrument = MidiInstrument.loadInstrument(js5, programId);
				if (instrument == null) {
					ready = false;
					continue;
				}
				this.instrumentCache.put(instrument, programId);
			}
			if (!instrument.resolveSounds(sampleRates, soundBank, programNode.value)) {
				ready = false;
			}
		}
		if (ready) {
			song.releasePrograms();
		}
		return ready;
	}

	@OriginalMember(owner = "client!va", name = "d", descriptor = "(B)V")
	public final synchronized void releaseInstruments() {
		for (@Pc(15) MidiInstrument instrument = (MidiInstrument) this.instrumentCache.head(); instrument != null; instrument = (MidiInstrument) this.instrumentCache.next()) {
			instrument.freeSoundTable();
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(III)V")
	private void resetPercussionChannel() {
		this.defaultProgram[9] = 128;
		this.channelBankSelect[9] = 128;
		this.programChange(128, 9);
	}

	@OriginalMember(owner = "client!va", name = "d", descriptor = "(I)Z")
	public final synchronized boolean isValid() {
		return this.midiDecoder.isValid();
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(ZLclient!rf;ZB)V")
	private synchronized void loadSong(@OriginalArg(0) boolean looping, @OriginalArg(1) Song song, @OriginalArg(2) boolean reset) {
		this.stopPlayback(reset);
		this.midiDecoder.init(song.midiBytes);
		this.loop = looping;
		this.elapsedTimeMicros = 0L;
		@Pc(24) int trackCount = this.midiDecoder.getTrackCount();
		for (@Pc(26) int i = 0; i < trackCount; i++) {
			this.midiDecoder.loadTrackPosition(i);
			this.midiDecoder.addDeltaTime(i);
			this.midiDecoder.saveTrackPosition(i);
		}
		this.currentTrack = this.midiDecoder.getNextTrack();
		this.currentTick = this.midiDecoder.times[this.currentTrack];
		this.currentTimeMicros = this.midiDecoder.getTimeMillis(this.currentTick);
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(III)V")
	private void setPitchBend(@OriginalArg(0) int channel, @OriginalArg(1) int value) {
		this.channelPitchBend[channel] = value;
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "()I")
	@Override
	public final synchronized int getActiveChannelCount() {
		return 0;
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(IZI)V")
	private void setChannelPressure(@OriginalArg(0) int channel, @OriginalArg(2) int pressure) {
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(BII)V")
	public final synchronized void init() {
		this.resetPercussionChannel();
	}

	@OriginalMember(owner = "client!va", name = "c", descriptor = "(II)V")
	private void resetAllControllers(@OriginalArg(1) int channel) {
		if (channel < 0) {
			for (@Pc(10) int i = 0; i < 16; i++) {
				this.resetAllControllers(i);
			}
			return;
		}
		this.channelVolume[channel] = 12800;
		this.channelPan[channel] = 8192;
		this.channelExpression[channel] = 16383;
		this.channelPitchBend[channel] = 8192;
		this.channelModulation[channel] = 0;
		this.channelPortamentoTime[channel] = 8192;
		this.releaseOrphanedPortamentoNotes(channel);
		this.releaseCustomHoldPedal(channel);
		this.channelFlags[channel] = 0;
		this.channelRpnNrpn[channel] = 32767;
		this.channelPitchBendRange[channel] = 256;
		this.channelSampleOffset[channel] = 0;
		this.setDetune(channel, 8192);
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(BI)V")
	private void allSoundOff(@OriginalArg(1) int channel) {
		for (@Pc(20) MidiNote note = (MidiNote) this.noteStream.notes.head(); note != null; note = (MidiNote) this.noteStream.notes.next()) {
			if (channel < 0 || note.channel == channel) {
				if (note.stream != null) {
					note.stream.fadeOutAndRelease(AudioChannel.sampleRate / 100);
					if (note.stream.isTransitioning()) {
						this.noteStream.mixer.addSubStream(note.stream);
					}
					note.release();
				}
				if (note.releaseTimer < 0) {
					this.activeNotes[note.channel][note.noteKey] = null;
				}
				note.unlink();
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(BII)V")
	private void setDetune(@OriginalArg(1) int channel, @OriginalArg(2) int value) {
		this.channelDetuneValue[channel] = value;
		this.channelDetuneRatio[channel] = (int) (Math.pow(2.0D, (double) value * 5.4931640625E-4D) * 2097152.0D + 0.5D);
	}

	@OriginalMember(owner = "client!va", name = "c", descriptor = "(III)V")
	private synchronized void initChannelVolumeScalars() {
		for (@Pc(5) int i = 0; i < 16; i++) {
			this.channelVolumeScalar[i] = 256;
		}
	}

	@OriginalMember(owner = "client!va", name = "d", descriptor = "(III)V")
	private void programChange(@OriginalArg(1) int program, @OriginalArg(2) int channel) {
		if (this.channelProgram[channel] != program) {
			this.channelProgram[channel] = program;
			for (@Pc(21) int i = 0; i < 128; i++) {
				this.exclusiveGroupNotes[channel][i] = null;
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "e", descriptor = "(I)V")
	public final synchronized void clearInstruments() {
		for (@Pc(7) MidiInstrument instrument = (MidiInstrument) this.instrumentCache.head(); instrument != null; instrument = (MidiInstrument) this.instrumentCache.next()) {
			instrument.unlink();
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(IIII)V")
	private void noteOn(@OriginalArg(0) int velocity, @OriginalArg(1) int channel, @OriginalArg(3) int key) {
		this.noteOff(key, 64, channel);
		if ((this.channelFlags[channel] & 0x2) != 0) {
			for (@Pc(28) MidiNote portaNote = (MidiNote) this.noteStream.notes.tail(); portaNote != null; portaNote = (MidiNote) this.noteStream.notes.prev()) {
				if (channel == portaNote.channel && portaNote.releaseTimer < 0) {
					this.activeNotes[channel][portaNote.noteKey] = null;
					this.activeNotes[channel][key] = portaNote;
					@Pc(72) int currentPitch = portaNote.basePitch + (portaNote.pitchModifier * portaNote.pitchDelta >> 12);
					portaNote.pitchModifier = 4096;
					portaNote.basePitch += key - portaNote.noteKey << 8;
					portaNote.pitchDelta = currentPitch - portaNote.basePitch;
					portaNote.noteKey = key;
					return;
				}
			}
		}
		@Pc(118) MidiInstrument instrument = (MidiInstrument) this.instrumentCache.get(this.channelProgram[channel]);
		if (instrument == null) {
			return;
		}
		@Pc(126) PcmSound sound = instrument.sounds[key];
		if (sound == null) {
			return;
		}
		@Pc(133) MidiNote note = new MidiNote();
		note.sound = sound;
		note.instrument = instrument;
		note.channel = channel;
		note.trackState = instrument.trackStates[key];
		note.exclusiveGroup = instrument.noteExclusiveGroup[key];
		note.noteKey = key;
		note.volumeGain = instrument.noteVolume[key] * velocity * velocity * instrument.volumeMultiplier + 1024 >> 11;
		note.pan = instrument.notePan[key] & 0xFF;
		note.basePitch = (key << 8) - (instrument.notePitchOffset[key] & 0x7FFF);
		note.releaseTimer = -1;
		note.attackEnvelopePos = 0;
		note.releaseEnvelopeIndex = 0;
		note.sustainTimer = 0;
		note.attackEnvelopeIndex = 0;
		if (this.channelSampleOffset[channel] == 0) {
			note.stream = SoundPcmStream.create(sound, this.calculatePitch(note), this.calculateVolume(note), this.calculatePan(note));
		} else {
			note.stream = SoundPcmStream.create(sound, this.calculatePitch(note), 0, this.calculatePan(note));
			this.applyLoopStartOffset(note, instrument.notePitchOffset[key] < 0);
		}
		if (instrument.notePitchOffset[key] < 0) {
			note.stream.setLoops(-1);
		}
		if (note.exclusiveGroup >= 0) {
			@Pc(289) MidiNote prevNote = this.exclusiveGroupNotes[channel][note.exclusiveGroup];
			if (prevNote != null && prevNote.releaseTimer < 0) {
				this.activeNotes[channel][prevNote.noteKey] = null;
				prevNote.releaseTimer = 0;
			}
			this.exclusiveGroupNotes[channel][note.exclusiveGroup] = note;
		}
		this.noteStream.notes.addTail(note);
		this.activeNotes[channel][key] = note;
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(BI)V")
	private void dispatchMidiEvent(@OriginalArg(1) int event) {
		@Pc(9) int status = event & 0xF0;
		@Pc(20) int channel;
		@Pc(32) int data1;
		@Pc(26) int data2;
		if (status == 128) {
			channel = event & 0xF;
			data2 = event >> 16 & 0x7F;
			data1 = event >> 8 & 0x7F;
			this.noteOff(data1, data2, channel);
		} else if (status == 144) {
			data1 = event >> 8 & 0x7F;
			channel = event & 0xF;
			data2 = event >> 16 & 0x7F;
			if (data2 > 0) {
				this.noteOn(data2, channel, data1);
			} else {
				this.noteOff(data1, 64, channel);
			}
		} else if (status == 160) {
			channel = event & 0xF;
			data1 = event >> 8 & 0x7F;
			data2 = event >> 16 & 0x7F;
			this.setPolyphonicAftertouch(data2, data1, channel);
		} else if (status == 176) {
			data1 = event >> 8 & 0x7F;
			channel = event & 0xF;
			data2 = event >> 16 & 0x7F;
			if (data1 == 0) {
				this.channelBankSelect[channel] = (data2 << 14) + (this.channelBankSelect[channel] & 0xFFE03FFF);
			}
			if (data1 == 32) {
				this.channelBankSelect[channel] = (this.channelBankSelect[channel] & 0xFFFFC07F) + (data2 << 7);
			}
			if (data1 == 1) {
				this.channelModulation[channel] = (data2 << 7) + (this.channelModulation[channel] & 0xFFFFC07F);
			}
			if (data1 == 33) {
				this.channelModulation[channel] = (this.channelModulation[channel] & 0xFFFFFF80) + data2;
			}
			if (data1 == 5) {
				this.channelPortamentoTime[channel] = (this.channelPortamentoTime[channel] & 0xFFFFC07F) + (data2 << 7);
			}
			if (data1 == 37) {
				this.channelPortamentoTime[channel] = data2 + (this.channelPortamentoTime[channel] & 0xFFFFFF80);
			}
			if (data1 == 7) {
				this.channelVolume[channel] = (data2 << 7) + (this.channelVolume[channel] & 0xFFFFC07F);
			}
			if (data1 == 39) {
				this.channelVolume[channel] = (this.channelVolume[channel] & 0xFFFFFF80) + data2;
			}
			if (data1 == 10) {
				this.channelPan[channel] = (this.channelPan[channel] & 0xFFFFC07F) + (data2 << 7);
			}
			if (data1 == 42) {
				this.channelPan[channel] = data2 + (this.channelPan[channel] & 0xFFFFFF80);
			}
			if (data1 == 11) {
				this.channelExpression[channel] = (this.channelExpression[channel] & 0xFFFFC07F) + (data2 << 7);
			}
			if (data1 == 43) {
				this.channelExpression[channel] = data2 + (this.channelExpression[channel] & 0xFFFFFF80);
			}
			if (data1 == 64) {
				if (data2 >= 64) {
					this.channelFlags[channel] |= 0x1;
				} else {
					this.channelFlags[channel] &= 0xFFFFFFFE;
				}
			}
			if (data1 == 65) {
				if (data2 < 64) {
					this.releaseOrphanedPortamentoNotes(channel);
					this.channelFlags[channel] &= 0xFFFFFFFD;
				} else {
					this.channelFlags[channel] |= 0x2;
				}
			}
			if (data1 == 99) {
				this.channelRpnNrpn[channel] = (data2 << 7) + (this.channelRpnNrpn[channel] & 0x7F);
			}
			if (data1 == 98) {
				this.channelRpnNrpn[channel] = (this.channelRpnNrpn[channel] & 0x3F80) + data2;
			}
			if (data1 == 101) {
				this.channelRpnNrpn[channel] = (data2 << 7) + (this.channelRpnNrpn[channel] & 0x7F) + 16384;
			}
			if (data1 == 100) {
				this.channelRpnNrpn[channel] = data2 + (this.channelRpnNrpn[channel] & 0x3F80) + 16384;
			}
			if (data1 == 120) {
				this.allSoundOff(channel);
			}
			if (data1 == 121) {
				this.resetAllControllers(channel);
			}
			if (data1 == 123) {
				this.allNotesOff(channel);
			}
			@Pc(522) int rpn;
			if (data1 == 6) {
				rpn = this.channelRpnNrpn[channel];
				if (rpn == 16384) {
					this.channelPitchBendRange[channel] = (this.channelPitchBendRange[channel] & 0xFFFFC07F) + (data2 << 7);
				}
			}
			if (data1 == 38) {
				rpn = this.channelRpnNrpn[channel];
				if (rpn == 16384) {
					this.channelPitchBendRange[channel] = (this.channelPitchBendRange[channel] & 0xFFFFFF80) + data2;
				}
			}
			if (data1 == 16) {
				this.channelSampleOffset[channel] = (this.channelSampleOffset[channel] & 0xFFFFC07F) + (data2 << 7);
			}
			if (data1 == 48) {
				this.channelSampleOffset[channel] = (this.channelSampleOffset[channel] & 0xFFFFFF80) + data2;
			}
			if (data1 == 81) {
				if (data2 >= 64) {
					this.channelFlags[channel] |= 0x4;
				} else {
					this.releaseCustomHoldPedal(channel);
					this.channelFlags[channel] &= 0xFFFFFFFB;
				}
			}
			if (data1 == 17) {
				this.setDetune(channel, (data2 << 7) + (this.channelDetuneValue[channel] & 0xFFFFC07F));
			}
			if (data1 == 49) {
				this.setDetune(channel, (this.channelDetuneValue[channel] & 0xFFFFFF80) + data2);
			}
		} else if (status == 192) {
			data1 = event >> 8 & 0x7F;
			channel = event & 0xF;
			this.programChange(this.channelBankSelect[channel] + data1, channel);
		} else if (status == 208) {
			channel = event & 0xF;
			data1 = event >> 8 & 0x7F;
			this.setChannelPressure(channel, data1);
		} else if (status == 224) {
			channel = event & 0xF;
			data1 = (event >> 9 & 0x3F80) + ((event & 0x7FBE) >> 8);
			this.setPitchBend(channel, data1);
		} else {
			status = event & 0xFF;
			if (status == 255) {
				this.stopAllNotes(true);
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "d", descriptor = "(II)V")
	private void allNotesOff(@OriginalArg(1) int channel) {
		for (@Pc(12) MidiNote note = (MidiNote) this.noteStream.notes.head(); note != null; note = (MidiNote) this.noteStream.notes.next()) {
			if ((channel < 0 || channel == note.channel) && note.releaseTimer < 0) {
				this.activeNotes[note.channel][note.noteKey] = null;
				note.releaseTimer = 0;
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(ZLclient!rf;I)V")
	public final synchronized void playSong(@OriginalArg(0) boolean looping, @OriginalArg(1) Song song) {
		this.loadSong(looping, song, true);
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "([III)V")
	@Override
	public final synchronized void read(@OriginalArg(0) int[] buffer, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		if (this.midiDecoder.isValid()) {
			@Pc(18) int tickLength = this.midiDecoder.division * this.TEMPO_SCALE / AudioChannel.sampleRate;
			do {
				@Pc(28) long endTime = this.elapsedTimeMicros + (long) length * (long) tickLength;
				if (this.currentTimeMicros - endTime >= 0L) {
					this.elapsedTimeMicros = endTime;
					break;
				}
				@Pc(59) int samplesToAdvance = (int) ((this.currentTimeMicros + (long) tickLength - this.elapsedTimeMicros - 1L) / (long) tickLength);
				this.elapsedTimeMicros += (long) tickLength * (long) samplesToAdvance;
				this.noteStream.read(buffer, offset, samplesToAdvance);
				length -= samplesToAdvance;
				offset += samplesToAdvance;
				this.advanceSequencer();
			} while (this.midiDecoder.isValid());
		}
		this.noteStream.read(buffer, offset, length);
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(IILclient!mf;B[I)Z")
	public final boolean advanceNoteEnvelope(@OriginalArg(0) int samplesToRead, @OriginalArg(1) int bufferOffset, @OriginalArg(2) MidiNote note, @OriginalArg(4) int[] buffer) {
		note.retriggerDelay = AudioChannel.sampleRate / 100;
		if (note.releaseTimer >= 0 && (note.stream == null || note.stream.isOutOfBounds())) {
			note.release();
			note.unlink();
			if (note.exclusiveGroup > 0 && note == this.exclusiveGroupNotes[note.channel][note.exclusiveGroup]) {
				this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] = null;
			}
			return true;
		}
		@Pc(54) int portamento = note.pitchModifier;
		if (portamento > 0) {
			portamento -= (int) (Math.pow(2.0D, (double) this.channelPortamentoTime[note.channel] * 4.921259842519685E-4D) * 16.0D + 0.5D);
			if (portamento < 0) {
				portamento = 0;
			}
			note.pitchModifier = portamento;
		}
		note.stream.setRate(this.calculatePitch(note));
		@Pc(103) MidiTrackState state = note.trackState;
		note.vibratoPhase += state.vibratoSpeed;
		note.tickCount++;
		@Pc(134) double pitchFactor = (double) ((note.noteKey - 60 << 8) + (note.pitchModifier * note.pitchDelta >> 12)) * 5.086263020833333E-6D;
		@Pc(136) boolean shouldRelease = false;
		if (state.decayRate > 0) {
			if (state.sustainRate > 0) {
				note.sustainTimer += (int) (Math.pow(2.0D, pitchFactor * (double) state.sustainRate) * 128.0D + 0.5D);
			} else {
				note.sustainTimer += 128;
			}
			if (note.sustainTimer * state.decayRate >= 819200) {
				shouldRelease = true;
			}
		}
		if (state.attackEnvelope != null) {
			if (state.attackRate <= 0) {
				note.attackEnvelopePos += 128;
			} else {
				note.attackEnvelopePos += (int) (Math.pow(2.0D, (double) state.attackRate * pitchFactor) * 128.0D + 0.5D);
			}
			while (note.attackEnvelopeIndex < state.attackEnvelope.length - 2 && note.attackEnvelopePos > (state.attackEnvelope[note.attackEnvelopeIndex + 2] & 0xFF) << 8) {
				note.attackEnvelopeIndex += 2;
			}
			if (state.attackEnvelope.length - 2 == note.attackEnvelopeIndex && state.attackEnvelope[note.attackEnvelopeIndex + 1] == 0) {
				shouldRelease = true;
			}
		}
		if (note.releaseTimer >= 0 && state.releaseEnvelope != null && (this.channelFlags[note.channel] & 0x1) == 0 && (note.exclusiveGroup < 0 || this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] != note)) {
			if (state.releaseRate > 0) {
				note.releaseTimer += (int) (Math.pow(2.0D, (double) state.releaseRate * pitchFactor) * 128.0D + 0.5D);
			} else {
				note.releaseTimer += 128;
			}
			while (state.releaseEnvelope.length - 2 > note.releaseEnvelopeIndex && (state.releaseEnvelope[note.releaseEnvelopeIndex + 2] & 0xFF) << 8 < note.releaseTimer) {
				note.releaseEnvelopeIndex += 2;
			}
			if (note.releaseEnvelopeIndex == state.releaseEnvelope.length - 2) {
				shouldRelease = true;
			}
		}
		if (!shouldRelease) {
			note.stream.fadeToVolumeAndPan(note.retriggerDelay, this.calculateVolume(note), this.calculatePan(note));
			return false;
		}
		note.stream.fadeOutAndRelease(note.retriggerDelay);
		if (buffer == null) {
			note.stream.skip(samplesToRead);
		} else {
			note.stream.read(buffer, bufferOffset, samplesToRead);
		}
		if (note.stream.isTransitioning()) {
			this.noteStream.mixer.addSubStream(note.stream);
		}
		note.release();
		if (note.releaseTimer >= 0) {
			note.unlink();
			if (note.exclusiveGroup > 0 && this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] == note) {
				this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] = null;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "()Lclient!qb;")
	@Override
	public final synchronized PcmStream firstSubStream() {
		return this.noteStream;
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(BIII)V")
	private void noteOff(@OriginalArg(1) int key, @OriginalArg(2) int velocity, @OriginalArg(3) int channel) {
		@Pc(12) MidiNote note = this.activeNotes[channel][key];
		if (note == null) {
			return;
		}
		this.activeNotes[channel][key] = null;
		if ((this.channelFlags[channel] & 0x2) == 0) {
			note.releaseTimer = 0;
			return;
		}
		for (@Pc(44) MidiNote other = (MidiNote) this.noteStream.notes.head(); other != null; other = (MidiNote) this.noteStream.notes.next()) {
			if (other.channel == note.channel && other.releaseTimer < 0 && other != note) {
				note.releaseTimer = 0;
				break;
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "f", descriptor = "(I)V")
	private void advanceSequencer() {
		@Pc(8) int track = this.currentTrack;
		@Pc(11) int tick = this.currentTick;
		@Pc(20) long timeMicros = this.currentTimeMicros;
		if (this.nextSong != null && tick == this.crossfadeTick) {
			this.loadSong(this.loop, this.nextSong, this.crossfadeReset);
			this.advanceSequencer();
			return;
		}
		while (this.currentTick == tick) {
			while (tick == this.midiDecoder.times[track]) {
				this.midiDecoder.loadTrackPosition(track);
				@Pc(64) int event = this.midiDecoder.getNextEvent(track);
				if (event == 1) {
					this.midiDecoder.loadEndOfTrackPosition();
					this.midiDecoder.saveTrackPosition(track);
					if (this.midiDecoder.hasNextTrack()) {
						if (this.nextSong != null) {
							this.playSong(this.loop, this.nextSong);
							this.advanceSequencer();
							return;
						}
						if (!this.loop || tick == 0) {
							this.stopAllNotes(true);
							this.midiDecoder.release();
							return;
						}
						this.midiDecoder.setStartMillis(timeMicros);
					}
					break;
				}
				if ((event & 0x80) != 0) {
					this.dispatchMidiEvent(event);
				}
				this.midiDecoder.addDeltaTime(track);
				this.midiDecoder.saveTrackPosition(track);
			}
			track = this.midiDecoder.getNextTrack();
			tick = this.midiDecoder.times[track];
			timeMicros = this.midiDecoder.getTimeMillis(tick);
		}
		this.currentTrack = track;
		this.currentTimeMicros = timeMicros;
		this.currentTick = tick;
		if (this.nextSong != null && tick > this.crossfadeTick) {
			this.currentTrack = -1;
			this.currentTick = this.crossfadeTick;
			this.currentTimeMicros = this.midiDecoder.getTimeMillis(this.currentTick);
		}
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(IIII)V")
	private void setPolyphonicAftertouch(@OriginalArg(0) int pressure, @OriginalArg(1) int key, @OriginalArg(3) int channel) {
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(ILclient!mf;)I")
	private int calculatePan(@OriginalArg(1) MidiNote note) {
		@Pc(5) int pan = this.channelPan[note.channel];
		return pan < 8192 ? note.pan * pan + 32 >> 6 : 16384 - ((128 - note.pan) * (-pan + 16384) + 32 >> 6);
	}

	@OriginalMember(owner = "client!va", name = "c", descriptor = "(I)V")
	@Override
	public final synchronized void skip(@OriginalArg(0) int length) {
		if (this.midiDecoder.isValid()) {
			@Pc(15) int tickLength = this.midiDecoder.division * this.TEMPO_SCALE / AudioChannel.sampleRate;
			do {
				@Pc(25) long endTime = this.elapsedTimeMicros + (long) length * (long) tickLength;
				if (this.currentTimeMicros - endTime >= 0L) {
					this.elapsedTimeMicros = endTime;
					break;
				}
				@Pc(57) int samplesToSkip = (int) (((long) tickLength + this.currentTimeMicros - this.elapsedTimeMicros - 1L) / (long) tickLength);
				length -= samplesToSkip;
				this.elapsedTimeMicros += (long) samplesToSkip * (long) tickLength;
				this.noteStream.skip(samplesToSkip);
				this.advanceSequencer();
			} while (this.midiDecoder.isValid());
		}
		this.noteStream.skip(length);
	}

	@OriginalMember(owner = "client!va", name = "e", descriptor = "(II)V")
	private void releaseCustomHoldPedal(@OriginalArg(0) int channel) {
		if ((this.channelFlags[channel] & 0x4) == 0) {
			return;
		}
		for (@Pc(24) MidiNote note = (MidiNote) this.noteStream.notes.head(); note != null; note = (MidiNote) this.noteStream.notes.next()) {
			if (note.channel == channel) {
				note.portamentoPos = 0;
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(BLclient!mf;)I")
	private int calculatePitch(@OriginalArg(1) MidiNote note) {
		@Pc(6) MidiTrackState state = note.trackState;
		@Pc(17) int pitch = (note.pitchDelta * note.pitchModifier >> 12) + note.basePitch;
		pitch += this.channelPitchBendRange[note.channel] * (this.channelPitchBend[note.channel] - 8192) >> 12;
		@Pc(62) int result;
		if (state.vibratoSpeed > 0 && (state.vibratoDepth > 0 || this.channelModulation[note.channel] > 0)) {
			result = state.vibratoDepth << 2;
			@Pc(67) int delay = state.vibratoDelay << 1;
			if (delay > note.tickCount) {
				result = note.tickCount * result / delay;
			}
			result += this.channelModulation[note.channel] >> 7;
			@Pc(102) double vibratoSin = Math.sin((double) (note.vibratoPhase & 0x1FF) * 0.01227184630308513D);
			pitch += (int) ((double) result * vibratoSin);
		}
		result = (int) ((double) (note.sound.rate * 256) * Math.pow(2.0D, (double) pitch * 3.255208333333333E-4D) / (double) AudioChannel.sampleRate + 0.5D);
		return result >= 1 ? result : 1;
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(Z)I")
	public final int getVolume() {
		return this.volume;
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(ZB)V")
	private void stopAllNotes(@OriginalArg(0) boolean forceSoundOff) {
		if (forceSoundOff) {
			this.allSoundOff(-1);
		} else {
			this.allNotesOff(-1);
		}
		this.resetAllControllers(-1);
		@Pc(29) int i;
		for (i = 0; i < 16; i++) {
			this.channelProgram[i] = this.defaultProgram[i];
		}
		for (i = 0; i < 16; i++) {
			this.channelBankSelect[i] = this.defaultProgram[i] & 0xFFFFFF80;
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(Lclient!mf;ZB)V")
	public final void applyLoopStartOffset(@OriginalArg(0) MidiNote note, @OriginalArg(1) boolean isPingPong) {
		@Pc(8) int sampleLen = note.sound.samples.length;
		@Pc(27) int offset;
		if (isPingPong && note.sound.pingPongLoop) {
			@Pc(37) int mirroredLen = sampleLen + sampleLen - note.sound.start;
			sampleLen <<= 0x8;
			offset = (int) ((long) mirroredLen * (long) this.channelSampleOffset[note.channel] >> 6);
			if (offset >= sampleLen) {
				note.stream.reverseDirection();
				offset = sampleLen + sampleLen - offset - 1;
			}
		} else {
			offset = (int) ((long) sampleLen * (long) this.channelSampleOffset[note.channel] >> 6);
		}
		note.stream.setSamplePosition(offset);
	}

	@OriginalMember(owner = "client!va", name = "f", descriptor = "(II)V")
	private void releaseOrphanedPortamentoNotes(@OriginalArg(0) int channel) {
		if ((this.channelFlags[channel] & 0x2) == 0) {
			return;
		}
		for (@Pc(20) MidiNote note = (MidiNote) this.noteStream.notes.head(); note != null; note = (MidiNote) this.noteStream.notes.next()) {
			if (channel == note.channel && this.activeNotes[channel][note.noteKey] == null && note.releaseTimer < 0) {
				note.releaseTimer = 0;
			}
		}
	}

	@OriginalMember(owner = "client!va", name = "a", descriptor = "(Lclient!mf;I)Z")
	public final boolean isNoteFinished(@OriginalArg(0) MidiNote note) {
		if (note.stream != null) {
			return false;
		}
		if (note.releaseTimer >= 0) {
			note.unlink();
			if (note.exclusiveGroup > 0 && this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] == note) {
				this.exclusiveGroupNotes[note.channel][note.exclusiveGroup] = null;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!va", name = "f", descriptor = "(B)V")
	public final synchronized void reset() {
		this.stopPlayback(true);
	}

	@OriginalMember(owner = "client!va", name = "g", descriptor = "(II)V")
	public final synchronized void setVolume(@OriginalArg(1) int vol) {
		this.volume = vol;
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(ZB)V")
	private synchronized void stopPlayback(@OriginalArg(0) boolean reset) {
		this.midiDecoder.release();
		this.nextSong = null;
		this.stopAllNotes(reset);
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(BLclient!mf;)I")
	private int calculateVolume(@OriginalArg(1) MidiNote note) {
		if (this.channelVolumeScalar[note.channel] == 0) {
			return 0;
		}
		@Pc(21) MidiTrackState state = note.trackState;
		@Pc(37) int vol = this.channelVolume[note.channel] * this.channelExpression[note.channel] + 4096 >> 13;
		@Pc(45) int volSquared = vol * vol + 16384 >> 15;
		@Pc(54) int gainVol = note.volumeGain * volSquared + 16384 >> 15;
		@Pc(63) int masterVol = gainVol * this.volume + 128 >> 8;
		vol = this.channelVolumeScalar[note.channel] * masterVol + 128 >> 8;
		if (state.decayRate > 0) {
			vol = (int) (Math.pow(0.5D, (double) note.sustainTimer * 1.953125E-5D * (double) state.decayRate) * (double) vol + 0.5D);
		}
		@Pc(105) int envPos;
		@Pc(113) int envValue;
		@Pc(143) int prevKeyframe;
		@Pc(133) int nextKeyframe;
		if (state.attackEnvelope != null) {
			envPos = note.attackEnvelopePos;
			envValue = state.attackEnvelope[note.attackEnvelopeIndex + 1];
			if (state.attackEnvelope.length - 2 > note.attackEnvelopeIndex) {
				nextKeyframe = (state.attackEnvelope[note.attackEnvelopeIndex + 2] & 0xFF) << 8;
				prevKeyframe = (state.attackEnvelope[note.attackEnvelopeIndex] & 0xFF) << 8;
				envValue += (state.attackEnvelope[note.attackEnvelopeIndex + 3] - envValue) * (envPos - prevKeyframe) / (nextKeyframe - prevKeyframe);
			}
			vol = envValue * vol + 32 >> 6;
		}
		if (note.releaseTimer > 0 && state.releaseEnvelope != null) {
			envPos = note.releaseTimer;
			envValue = state.releaseEnvelope[note.releaseEnvelopeIndex + 1];
			if (state.releaseEnvelope.length - 2 > note.releaseEnvelopeIndex) {
				prevKeyframe = (state.releaseEnvelope[note.releaseEnvelopeIndex] & 0xFF) << 8;
				nextKeyframe = (state.releaseEnvelope[note.releaseEnvelopeIndex + 2] & 0xFF) << 8;
				envValue += (envPos - prevKeyframe) * (-envValue + state.releaseEnvelope[note.releaseEnvelopeIndex + 3]) / (nextKeyframe - prevKeyframe);
			}
			vol = vol * envValue + 32 >> 6;
		}
		return vol;
	}

	@OriginalMember(owner = "client!va", name = "d", descriptor = "()Lclient!qb;")
	@Override
	public final synchronized PcmStream nextSubStream() {
		return null;
	}
}
