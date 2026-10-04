package rt4;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!ub")
public final class SlotAnimation {

	@OriginalMember(owner = "client!ub", name = "b", descriptor = "I")
	public int seqId;

	@OriginalMember(owner = "client!ub", name = "d", descriptor = "I")
	public int nextFrame;

	@OriginalMember(owner = "client!ub", name = "f", descriptor = "I")
	public int currentFrame;

	@OriginalMember(owner = "client!ub", name = "g", descriptor = "I")
	public int replayCount;

	@OriginalMember(owner = "client!ub", name = "l", descriptor = "I")
	public int delayClock;

	@OriginalMember(owner = "client!ub", name = "q", descriptor = "I")
	public int startDelay;
}
