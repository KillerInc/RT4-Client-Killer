package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!n")
public final class SoftLruHashTable {

	@OriginalMember(owner = "client!n", name = "k", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList queue = new SecondaryLinkedList();

	@OriginalMember(owner = "client!n", name = "m", descriptor = "I")
	private final int capacity;

	@OriginalMember(owner = "client!n", name = "g", descriptor = "I")
	private int available;

	@OriginalMember(owner = "client!n", name = "n", descriptor = "Lclient!sc;")
	private final HashTable table;

	@OriginalMember(owner = "client!n", name = "<init>", descriptor = "(I)V")
	public SoftLruHashTable(@OriginalArg(0) int capacity) {
		this.capacity = capacity;
		@Pc(11) int bucketCount;
		for (bucketCount = 1; bucketCount + bucketCount < capacity; bucketCount += bucketCount) {
		}
		this.available = capacity;
		this.table = new HashTable(bucketCount);
	}

	@OriginalMember(owner = "client!n", name = "a", descriptor = "(BLjava/lang/Object;J)V")
	public final void put(@OriginalArg(1) Object value, @OriginalArg(2) long key) {
		this.remove(key);
		if (this.available == 0) {
			@Pc(26) ReferenceNode evicted = (ReferenceNode) this.queue.removeHead();
			evicted.unlink();
			evicted.unlinkSecondary();
		} else {
			this.available--;
		}
		@Pc(44) HardReferenceNode node = new HardReferenceNode(value);
		this.table.put(node, key);
		this.queue.addTail(node);
		node.secondaryKey = 0L;
	}

	@OriginalMember(owner = "client!n", name = "a", descriptor = "(JB)V")
	public final void remove(@OriginalArg(0) long key) {
		@Pc(6) ReferenceNode node = (ReferenceNode) this.table.get(key);
		if (node != null) {
			node.unlink();
			node.unlinkSecondary();
			this.available++;
		}
	}

	@OriginalMember(owner = "client!n", name = "a", descriptor = "(I)I")
	public final int size() {
		@Pc(10) int count = 0;
		for (@Pc(16) ReferenceNode node = (ReferenceNode) this.queue.head(); node != null; node = (ReferenceNode) this.queue.next()) {
			if (!node.isSoft()) {
				count++;
			}
		}
		return count;
	}

	@OriginalMember(owner = "client!n", name = "c", descriptor = "(II)V")
	public final void clean(@OriginalArg(1) int maxAge) {
		if (ReferenceNodeFactory.SOFT_REFERENCE_NODE_FACTORY == null) {
			return;
		}
		for (@Pc(9) ReferenceNode node = (ReferenceNode) this.queue.head(); node != null; node = (ReferenceNode) this.queue.next()) {
			if (node.isSoft()) {
				if (node.get() == null) {
					node.unlink();
					node.unlinkSecondary();
					this.available++;
				}
			} else if (++node.secondaryKey > (long) maxAge) {
				@Pc(33) ReferenceNode softNode = ReferenceNodeFactory.SOFT_REFERENCE_NODE_FACTORY.create(node);
				this.table.put(softNode, node.key);
				SecondaryLinkedList.insertAfter(node, softNode);
				node.unlink();
				node.unlinkSecondary();
			}
		}
	}

	@OriginalMember(owner = "client!n", name = "b", descriptor = "(B)V")
	public final void removeSoft() {
		for (@Pc(7) ReferenceNode node = (ReferenceNode) this.queue.head(); node != null; node = (ReferenceNode) this.queue.next()) {
			if (node.isSoft()) {
				node.unlink();
				node.unlinkSecondary();
				this.available++;
			}
		}
	}

	@OriginalMember(owner = "client!n", name = "c", descriptor = "(I)V")
	public final void clear() {
		this.queue.clear();
		this.table.clear();
		this.available = this.capacity;
	}

	@OriginalMember(owner = "client!n", name = "b", descriptor = "(JB)Ljava/lang/Object;")
	public final Object get(@OriginalArg(0) long key) {
		@Pc(12) ReferenceNode node = (ReferenceNode) this.table.get(key);
		if (node == null) {
			return null;
		}
		@Pc(27) Object value = node.get();
		if (value == null) {
			node.unlink();
			node.unlinkSecondary();
			this.available++;
			return null;
		}
		if (node.isSoft()) {
			@Pc(53) HardReferenceNode hardNode = new HardReferenceNode(value);
			this.table.put(hardNode, node.key);
			this.queue.addTail(hardNode);
			hardNode.secondaryKey = 0L;
			node.unlink();
			node.unlinkSecondary();
		} else {
			this.queue.addTail(node);
			node.secondaryKey = 0L;
		}
		return value;
	}
}
