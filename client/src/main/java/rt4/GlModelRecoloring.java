package rt4;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!de")
public final class GlModelRecoloring {

	@OriginalMember(owner = "client!de", name = "a", descriptor = "[S")
	public short[] retextureDst;

	@OriginalMember(owner = "client!de", name = "b", descriptor = "[S")
	public short[] recolorSrc;

	@OriginalMember(owner = "client!de", name = "c", descriptor = "[S")
	public short[] retextureSrc;

	@OriginalMember(owner = "client!de", name = "d", descriptor = "[S")
	public short[] recolorDst;
}
