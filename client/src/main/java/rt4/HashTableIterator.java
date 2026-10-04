package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!l")
public final class HashTableIterator {

	@OriginalMember(owner = "client!l", name = "a", descriptor = "Lclient!ab;")
	private Node cursor;

	@OriginalMember(owner = "client!l", name = "c", descriptor = "I")
	private int bucketIndex = 0;

	@OriginalMember(owner = "client!l", name = "e", descriptor = "Lclient!sc;")
	private final HashTable table;

	@OriginalMember(owner = "client!l", name = "<init>", descriptor = "(Lclient!sc;)V")
	public HashTableIterator(@OriginalArg(0) HashTable table) {
		this.table = table;
	}

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(I)Lclient!ab;")
	public final Node next() {
		@Pc(30) Node node;
		if (this.bucketIndex > 0 && this.table.nodes[this.bucketIndex - 1] != this.cursor) {
			node = this.cursor;
			this.cursor = node.nextNode;
			return node;
		}
		do {
			if (this.table.bucketCount <= this.bucketIndex) {
				return null;
			}
			node = this.table.nodes[this.bucketIndex++].nextNode;
		} while (node == this.table.nodes[this.bucketIndex - 1]);
		this.cursor = node.nextNode;
		return node;
	}

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(B)Lclient!ab;")
	public final Node first() {
		this.bucketIndex = 0;
		return this.next();
	}
}
