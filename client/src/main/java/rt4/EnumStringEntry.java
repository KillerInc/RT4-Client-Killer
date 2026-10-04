package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!hb")
public final class EnumStringEntry extends Node {

	@OriginalMember(owner = "client!hb", name = "y", descriptor = "Lclient!na;")
	public final JagString value;

	@OriginalMember(owner = "client!hb", name = "<init>", descriptor = "(Lclient!na;I)V")
	public EnumStringEntry(@OriginalArg(0) JagString value, @OriginalArg(1) int unused) {
		this.value = value;
	}
}
