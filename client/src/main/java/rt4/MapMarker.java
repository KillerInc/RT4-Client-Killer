package rt4;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!nc")
public final class MapMarker {

	@OriginalMember(owner = "client!nc", name = "b", descriptor = "I")
	public int targetXFine;

	@OriginalMember(owner = "client!nc", name = "c", descriptor = "I")
	public int targetY;

	@OriginalMember(owner = "client!nc", name = "f", descriptor = "I")
	public int targetYFine;

	@OriginalMember(owner = "client!nc", name = "g", descriptor = "I")
	public int arrowSpriteId;

	@OriginalMember(owner = "client!nc", name = "i", descriptor = "I")
	public int targetHeight;

	@OriginalMember(owner = "client!nc", name = "l", descriptor = "I")
	public int targetX;

	@OriginalMember(owner = "client!nc", name = "p", descriptor = "I")
	public int actorTargetId;

	@OriginalMember(owner = "client!nc", name = "q", descriptor = "I")
	public int type;

	@OriginalMember(owner = "client!nc", name = "k", descriptor = "I")
	public int playerModelId = -1;
}
