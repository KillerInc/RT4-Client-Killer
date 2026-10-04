package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!te")
public final class MidiNoteStream extends PcmStream {

	@OriginalMember(owner = "client!te", name = "w", descriptor = "Lclient!ih;")
	public final LinkedList notes = new LinkedList();

	@OriginalMember(owner = "client!te", name = "K", descriptor = "Lclient!ei;")
	public final MixerPcmStream mixer = new MixerPcmStream();

	@OriginalMember(owner = "client!te", name = "E", descriptor = "Lclient!va;")
	private final MidiPcmStream parent;

	@OriginalMember(owner = "client!te", name = "<init>", descriptor = "(Lclient!va;)V")
	public MidiNoteStream(@OriginalArg(0) MidiPcmStream parent) {
		this.parent = parent;
	}

	@OriginalMember(owner = "client!te", name = "d", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream nextSubStream() {
		@Pc(9) MidiNote note;
		do {
			note = (MidiNote) this.notes.next();
			if (note == null) {
				return null;
			}
		} while (note.stream == null);
		return note.stream;
	}

	@OriginalMember(owner = "client!te", name = "b", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream firstSubStream() {
		@Pc(9) MidiNote note = (MidiNote) this.notes.head();
		if (note == null) {
			return null;
		} else if (note.stream == null) {
			return this.nextSubStream();
		} else {
			return note.stream;
		}
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "(Lclient!mf;II)V")
	private void skip(@OriginalArg(0) MidiNote instrument, @OriginalArg(2) int len) {
		if ((this.parent.channelFlags[instrument.channel] & 0x4) != 0 && instrument.releaseTimer < 0) {
			@Pc(27) int detuneStep = this.parent.channelDetuneRatio[instrument.channel] / AudioChannel.sampleRate;
			@Pc(37) int samplesUntilRetrigger = (detuneStep + 1048575 - instrument.portamentoPos) / detuneStep;
			instrument.portamentoPos = detuneStep * len + instrument.portamentoPos & 0xFFFFF;
			if (len >= samplesUntilRetrigger) {
				if (this.parent.channelSampleOffset[instrument.channel] == 0) {
					instrument.stream = SoundPcmStream.create(instrument.sound, instrument.stream.getAbsoluteRate(), instrument.stream.getVolume(), instrument.stream.getPan());
				} else {
					instrument.stream = SoundPcmStream.create(instrument.sound, instrument.stream.getAbsoluteRate(), 0, instrument.stream.getPan());
					this.parent.applyLoopStartOffset(instrument, instrument.instrument.notePitchOffset[instrument.noteKey] < 0);
				}
				if (instrument.instrument.notePitchOffset[instrument.noteKey] < 0) {
					instrument.stream.setLoops(-1);
				}
				len = instrument.portamentoPos / detuneStep;
			}
		}
		instrument.stream.skip(len);
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "()I")
	@Override
	public final int getActiveChannelCount() {
		return 0;
	}

	@OriginalMember(owner = "client!te", name = "c", descriptor = "(I)V")
	@Override
	public final void skip(@OriginalArg(0) int len) {
		this.mixer.skip(len);
		for (@Pc(15) MidiNote note = (MidiNote) this.notes.head(); note != null; note = (MidiNote) this.notes.next()) {
			if (!this.parent.isNoteFinished(note)) {
				@Pc(27) int len2 = len;
				do {
					if (len2 <= note.retriggerDelay) {
						this.skip(note, len2);
						note.retriggerDelay -= len2;
						break;
					}
					this.skip(note, note.retriggerDelay);
					len2 -= note.retriggerDelay;
				} while (!this.parent.advanceNoteEnvelope(len2, 0, note, null));
			}
		}
	}

	@OriginalMember(owner = "client!te", name = "b", descriptor = "([III)V")
	@Override
	public final void read(@OriginalArg(0) int[] buf, @OriginalArg(1) int offset, @OriginalArg(2) int len) {
		this.mixer.read(buf, offset, len);
		for (@Pc(17) MidiNote note = (MidiNote) this.notes.head(); note != null; note = (MidiNote) this.notes.next()) {
			if (!this.parent.isNoteFinished(note)) {
				@Pc(29) int remaining = len;
				@Pc(31) int pos = offset;
				do {
					if (remaining <= note.retriggerDelay) {
						this.readNoteWithRetrigger(buf, note, pos, remaining, pos + remaining);
						note.retriggerDelay -= remaining;
						break;
					}
					this.readNoteWithRetrigger(buf, note, pos, note.retriggerDelay, remaining + pos);
					remaining -= note.retriggerDelay;
					pos += note.retriggerDelay;
				} while (!this.parent.advanceNoteEnvelope(remaining, pos, note, buf));
			}
		}
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "([ILclient!mf;IIIB)V")
	private void readNoteWithRetrigger(@OriginalArg(0) int[] buf, @OriginalArg(1) MidiNote note, @OriginalArg(2) int offset, @OriginalArg(3) int len, @OriginalArg(4) int end) {
		if ((this.parent.channelFlags[note.channel] & 0x4) != 0 && note.releaseTimer < 0) {
			@Pc(26) int detuneStep = this.parent.channelDetuneRatio[note.channel] / AudioChannel.sampleRate;
			while (true) {
				@Pc(36) int samplesUntilRetrigger = (detuneStep + 1048575 - note.portamentoPos) / detuneStep;
				if (len < samplesUntilRetrigger) {
					note.portamentoPos += len * detuneStep;
					break;
				}
				len -= samplesUntilRetrigger;
				note.stream.read(buf, offset, samplesUntilRetrigger);
				@Pc(55) int fadeLen = AudioChannel.sampleRate / 100;
				@Pc(58) SoundPcmStream prevStream = note.stream;
				@Pc(62) int maxFadeLen = 262144 / detuneStep;
				if (maxFadeLen < fadeLen) {
					fadeLen = maxFadeLen;
				}
				note.portamentoPos += detuneStep * samplesUntilRetrigger - 1048576;
				if (this.parent.channelSampleOffset[note.channel] == 0) {
					note.stream = SoundPcmStream.create(note.sound, prevStream.getAbsoluteRate(), prevStream.getVolume(), prevStream.getPan());
				} else {
					note.stream = SoundPcmStream.create(note.sound, prevStream.getAbsoluteRate(), 0, prevStream.getPan());
					this.parent.applyLoopStartOffset(note, note.instrument.notePitchOffset[note.noteKey] < 0);
					note.stream.fadeToVolume(fadeLen, prevStream.getVolume());
				}
				if (note.instrument.notePitchOffset[note.noteKey] < 0) {
					note.stream.setLoops(-1);
				}
				offset += samplesUntilRetrigger;
				prevStream.fadeOutAndRelease(fadeLen);
				prevStream.read(buf, offset, end - offset);
				if (prevStream.isTransitioning()) {
					this.mixer.addSubStream(prevStream);
				}
			}
		}
		note.stream.read(buf, offset, len);
	}
}
