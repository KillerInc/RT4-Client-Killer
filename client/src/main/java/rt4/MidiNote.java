package rt4;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!mf")
public final class MidiNote extends Node {

	@OriginalMember(owner = "client!mf", name = "s", descriptor = "I")
	public int attackEnvelopeIndex;

	@OriginalMember(owner = "client!mf", name = "t", descriptor = "I")
	public int pitchModifier;

	@OriginalMember(owner = "client!mf", name = "u", descriptor = "I")
	public int pan;

	@OriginalMember(owner = "client!mf", name = "w", descriptor = "Lclient!wh;")
	public MidiTrackState trackState;

	@OriginalMember(owner = "client!mf", name = "y", descriptor = "I")
	public int releaseTimer;

	@OriginalMember(owner = "client!mf", name = "z", descriptor = "Lclient!b;")
	public SoundPcmStream stream;

	@OriginalMember(owner = "client!mf", name = "A", descriptor = "I")
	public int vibratoPhase;

	@OriginalMember(owner = "client!mf", name = "B", descriptor = "Lclient!kj;")
	public PcmSound sound;

	@OriginalMember(owner = "client!mf", name = "C", descriptor = "I")
	public int basePitch;

	@OriginalMember(owner = "client!mf", name = "D", descriptor = "I")
	public int attackEnvelopePos;

	@OriginalMember(owner = "client!mf", name = "E", descriptor = "I")
	public int retriggerDelay;

	@OriginalMember(owner = "client!mf", name = "F", descriptor = "I")
	public int volumeGain;

	@OriginalMember(owner = "client!mf", name = "G", descriptor = "I")
	public int channel;

	@OriginalMember(owner = "client!mf", name = "H", descriptor = "I")
	public int tickCount;

	@OriginalMember(owner = "client!mf", name = "I", descriptor = "I")
	public int portamentoPos;

	@OriginalMember(owner = "client!mf", name = "J", descriptor = "I")
	public int exclusiveGroup;

	@OriginalMember(owner = "client!mf", name = "L", descriptor = "I")
	public int releaseEnvelopeIndex;

	@OriginalMember(owner = "client!mf", name = "N", descriptor = "I")
	public int noteKey;

	@OriginalMember(owner = "client!mf", name = "Q", descriptor = "I")
	public int pitchDelta;

	@OriginalMember(owner = "client!mf", name = "R", descriptor = "I")
	public int sustainTimer;

	@OriginalMember(owner = "client!mf", name = "V", descriptor = "Lclient!jk;")
	public MidiInstrument instrument;

	@OriginalMember(owner = "client!mf", name = "d", descriptor = "(I)V")
	public final void release() {
		this.trackState = null;
		this.sound = null;
		this.stream = null;
		this.instrument = null;
	}
}
