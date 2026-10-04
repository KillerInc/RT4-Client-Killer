package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ih")
public final class LinkedList {

	@OriginalMember(owner = "client!ih", name = "p", descriptor = "Lclient!ab;")
	private Node iteratorCursor;

	@OriginalMember(owner = "client!ih", name = "m", descriptor = "Lclient!ab;")
	public final Node sentinel = new Node();

	@OriginalMember(owner = "client!ih", name = "<init>", descriptor = "()V")
	public LinkedList() {
		this.sentinel.previousNode = this.sentinel;
		this.sentinel.nextNode = this.sentinel;
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(I)V")
	public final void clear() {
		while (true) {
			@Pc(5) Node node = this.sentinel.nextNode;
			if (node == this.sentinel) {
				this.iteratorCursor = null;
				return;
			}
			node.unlink();
		}
	}

	@OriginalMember(owner = "client!ih", name = "b", descriptor = "(I)Lclient!ab;")
	public final Node tail() {
		@Pc(7) Node node = this.sentinel.previousNode;
		if (this.sentinel == node) {
			this.iteratorCursor = null;
			return null;
		} else {
			this.iteratorCursor = node.previousNode;
			return node;
		}
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(ZLclient!ab;)V")
	public final void addTail(@OriginalArg(1) Node node) {
		if (node.previousNode != null) {
			node.unlink();
		}
		node.nextNode = this.sentinel;
		node.previousNode = this.sentinel.previousNode;
		node.previousNode.nextNode = node;
		node.nextNode.previousNode = node;
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(ILclient!ab;)V")
	public final void addHead(@OriginalArg(1) Node node) {
		if (node.previousNode != null) {
			node.unlink();
		}
		node.nextNode = this.sentinel.nextNode;
		node.previousNode = this.sentinel;
		node.previousNode.nextNode = node;
		node.nextNode.previousNode = node;
	}

	@OriginalMember(owner = "client!ih", name = "d", descriptor = "(I)Lclient!ab;")
	public final Node prev() {
		@Pc(13) Node node = this.iteratorCursor;
		if (this.sentinel == node) {
			this.iteratorCursor = null;
			return null;
		} else {
			this.iteratorCursor = node.previousNode;
			return node;
		}
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(B)Lclient!ab;")
	public final Node removeHead() {
		@Pc(3) Node node = this.sentinel.nextNode;
		if (this.sentinel == node) {
			return null;
		} else {
			node.unlink();
			return node;
		}
	}

	@OriginalMember(owner = "client!ih", name = "e", descriptor = "(I)Lclient!ab;")
	public final Node next() {
		@Pc(12) Node node = this.iteratorCursor;
		if (node == this.sentinel) {
			this.iteratorCursor = null;
			return null;
		} else {
			this.iteratorCursor = node.nextNode;
			return node;
		}
	}

	@OriginalMember(owner = "client!ih", name = "f", descriptor = "(I)Lclient!ab;")
	public final Node head() {
		@Pc(3) Node node = this.sentinel.nextNode;
		if (this.sentinel == node) {
			this.iteratorCursor = null;
			return null;
		} else {
			this.iteratorCursor = node.nextNode;
			return node;
		}
	}
}
