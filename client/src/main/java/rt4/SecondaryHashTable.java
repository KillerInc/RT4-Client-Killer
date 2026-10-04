package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!rm")
public final class SecondaryHashTable {

	@OriginalMember(owner = "client!rm", name = "e", descriptor = "[Lclient!rg;")
	private final SecondaryNode[] buckets;

	@OriginalMember(owner = "client!rm", name = "<init>", descriptor = "(I)V")
	public SecondaryHashTable(@OriginalArg(0) int size) {
		this.buckets = new SecondaryNode[size];
		for (@Pc(7) int i = 0; i < size; i++) {
			@Pc(23) SecondaryNode sentinel = this.buckets[i] = new SecondaryNode();
			sentinel.secondaryPrev = sentinel;
			sentinel.secondaryNext = sentinel;
		}
	}
}
