package rt4;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!wh")
public final class MidiTrackState {

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "I")
	public int sustainRate;

	@OriginalMember(owner = "client!wh", name = "b", descriptor = "[B")
	public byte[] attackEnvelope;

	@OriginalMember(owner = "client!wh", name = "e", descriptor = "I")
	public int vibratoDepth;

	@OriginalMember(owner = "client!wh", name = "f", descriptor = "I")
	public int attackRate;

	@OriginalMember(owner = "client!wh", name = "h", descriptor = "I")
	public int vibratoDelay;

	@OriginalMember(owner = "client!wh", name = "k", descriptor = "I")
	public int releaseRate;

	@OriginalMember(owner = "client!wh", name = "p", descriptor = "[B")
	public byte[] releaseEnvelope;

	@OriginalMember(owner = "client!wh", name = "q", descriptor = "I")
	public int vibratoSpeed;

	@OriginalMember(owner = "client!wh", name = "r", descriptor = "I")
	public int decayRate;
}
