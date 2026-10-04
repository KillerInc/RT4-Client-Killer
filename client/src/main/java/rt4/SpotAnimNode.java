package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!bk")
public final class SpotAnimNode extends SecondaryNode {

	@OriginalMember(owner = "client!bk", name = "M", descriptor = "Lclient!bh;")
	public final SpotAnim spotAnim;

	@OriginalMember(owner = "client!bk", name = "<init>", descriptor = "(Lclient!bh;)V")
	public SpotAnimNode(@OriginalArg(0) SpotAnim spotAnim) {
		this.spotAnim = spotAnim;
	}
}
