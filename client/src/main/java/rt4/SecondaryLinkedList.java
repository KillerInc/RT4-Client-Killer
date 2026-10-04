package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ce")
public final class SecondaryLinkedList {

	@OriginalMember(owner = "client!ce", name = "n", descriptor = "Lclient!rg;")
	private SecondaryNode cursor;

	@OriginalMember(owner = "client!ce", name = "l", descriptor = "Lclient!rg;")
	private final SecondaryNode sentinel = new SecondaryNode();

	@OriginalMember(owner = "client!ce", name = "<init>", descriptor = "()V")
	public SecondaryLinkedList() {
		this.sentinel.secondaryNext = this.sentinel;
		this.sentinel.secondaryPrev = this.sentinel;
	}

	@OriginalMember(owner = "client!gk", name = "a", descriptor = "(Lclient!rg;Lclient!rg;B)V")
	public static void insertAfter(@OriginalArg(0) SecondaryNode after, @OriginalArg(1) SecondaryNode node) {
		if (node.secondaryPrev != null) {
			node.unlinkSecondary();
		}
		node.secondaryPrev = after;
		node.secondaryNext = after.secondaryNext;
		node.secondaryPrev.secondaryNext = node;
		node.secondaryNext.secondaryPrev = node;
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(I)I")
	public final int size() {
		@Pc(3) int count = 0;
		@Pc(7) SecondaryNode node = this.sentinel.secondaryNext;
		while (node != this.sentinel) {
			node = node.secondaryNext;
			count++;
		}
		return count;
	}

	@OriginalMember(owner = "client!ce", name = "b", descriptor = "(B)Lclient!rg;")
	public final SecondaryNode head() {
		@Pc(3) SecondaryNode node = this.sentinel.secondaryNext;
		if (this.sentinel == node) {
			this.cursor = null;
			return null;
		} else {
			this.cursor = node.secondaryNext;
			return node;
		}
	}

	@OriginalMember(owner = "client!ce", name = "b", descriptor = "(I)Lclient!rg;")
	public final SecondaryNode removeHead() {
		@Pc(7) SecondaryNode node = this.sentinel.secondaryNext;
		if (node == this.sentinel) {
			return null;
		} else {
			node.unlinkSecondary();
			return node;
		}
	}

	@OriginalMember(owner = "client!ce", name = "c", descriptor = "(I)Lclient!rg;")
	public final SecondaryNode next() {
		@Pc(2) SecondaryNode node = this.cursor;
		if (node == this.sentinel) {
			this.cursor = null;
			return null;
		} else {
			this.cursor = node.secondaryNext;
			return node;
		}
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(Lclient!rg;B)V")
	public final void addTail(@OriginalArg(0) SecondaryNode node) {
		if (node.secondaryPrev != null) {
			node.unlinkSecondary();
		}
		node.secondaryPrev = this.sentinel.secondaryPrev;
		node.secondaryNext = this.sentinel;
		node.secondaryPrev.secondaryNext = node;
		node.secondaryNext.secondaryPrev = node;
	}

	@OriginalMember(owner = "client!ce", name = "d", descriptor = "(I)V")
	public final void clear() {
		while (true) {
			@Pc(15) SecondaryNode node = this.sentinel.secondaryNext;
			if (this.sentinel == node) {
				this.cursor = null;
				return;
			}
			node.unlinkSecondary();
		}
	}
}
