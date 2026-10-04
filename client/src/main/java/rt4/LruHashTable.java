package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!gn")
public final class LruHashTable {

	@OriginalMember(owner = "client!gn", name = "l", descriptor = "Lclient!rg;")
	private SecondaryNode sentinel = new SecondaryNode();

	@OriginalMember(owner = "client!gn", name = "s", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList queue = new SecondaryLinkedList();

	@OriginalMember(owner = "client!gn", name = "u", descriptor = "I")
	private int available;

	@OriginalMember(owner = "client!gn", name = "r", descriptor = "I")
	private final int capacity;

	@OriginalMember(owner = "client!gn", name = "q", descriptor = "Lclient!sc;")
	private final HashTable table;

	@OriginalMember(owner = "client!gn", name = "<init>", descriptor = "(I)V")
	public LruHashTable(@OriginalArg(0) int capacity) {
		@Pc(13) int bucketCount = 1;
		this.available = capacity;
		while (capacity > bucketCount + bucketCount) {
			bucketCount += bucketCount;
		}
		this.capacity = capacity;
		this.table = new HashTable(bucketCount);
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(JI)Lclient!rg;")
	public final SecondaryNode get(@OriginalArg(0) long key) {
		@Pc(16) SecondaryNode node = (SecondaryNode) this.table.get(key);
		if (node != null) {
			this.queue.addTail(node);
		}
		return node;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(I)Lclient!ab;")
	public final Node head() {
		return this.table.head();
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(Lclient!rg;JB)V")
	public final void put(@OriginalArg(0) SecondaryNode node, @OriginalArg(1) long key) {
		if (this.available == 0) {
			@Pc(14) SecondaryNode evicted = this.queue.removeHead();
			evicted.unlink();
			evicted.unlinkSecondary();
			if (this.sentinel == evicted) {
				evicted = this.queue.removeHead();
				evicted.unlink();
				evicted.unlinkSecondary();
			}
		} else {
			this.available--;
		}
		this.table.put(node, key);
		this.queue.addTail(node);
	}

	@OriginalMember(owner = "client!gn", name = "b", descriptor = "(I)Lclient!ab;")
	public final Node next() {
		return this.table.next();
	}

	@OriginalMember(owner = "client!gn", name = "c", descriptor = "(I)V")
	public final void clear() {
		this.queue.clear();
		this.table.clear();
		this.sentinel = new SecondaryNode();
		this.available = this.capacity;
	}
}
