package rt4;

import org.openrs2.deob.annotation.OriginalMember;

// Has no usages.

public final class DeadClass {

	@OriginalMember(owner = "client!ul", name = "I", descriptor = "Lclient!gn;")
	public static final LruHashTable cache = new LruHashTable(4);

	@OriginalMember(owner = "client!ck", name = "b", descriptor = "Lclient!na;")
	private static final JagString DISCARD_TEXT = JagString.parse("Discard");
	@OriginalMember(owner = "client!ck", name = "o", descriptor = "Lclient!na;")
	private static final JagString LOADED_WORLD_LIST_TEXT = JagString.parse("Loaded world list data");
	@OriginalMember(owner = "client!cn", name = "L", descriptor = "I")
	public static double yawAccel = 0;

	@OriginalMember(owner = "client!rc", name = "R", descriptor = "Z")
	private static boolean unknownDeadBoolean;
}
