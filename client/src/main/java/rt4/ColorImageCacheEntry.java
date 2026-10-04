package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!qi")
public final class ColorImageCacheEntry extends Node {

	@OriginalMember(owner = "client!qi", name = "s", descriptor = "I")
	public final int index;

	@OriginalMember(owner = "client!qi", name = "q", descriptor = "I")
	public final int row;

	@OriginalMember(owner = "client!qi", name = "<init>", descriptor = "(II)V")
	public ColorImageCacheEntry(@OriginalArg(0) int row, @OriginalArg(1) int index) {
		this.index = index;
		this.row = row;
	}
}
