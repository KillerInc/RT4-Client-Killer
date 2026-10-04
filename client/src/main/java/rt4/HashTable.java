package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!sc")
public final class HashTable {

	@OriginalMember(owner = "client!sc", name = "q", descriptor = "Lclient!ab;")
	private Node getCursor;

	@OriginalMember(owner = "client!sc", name = "u", descriptor = "J")
	private long lastGetKey;

	@OriginalMember(owner = "client!sc", name = "C", descriptor = "Lclient!ab;")
	private Node iteratorCursor;

	@OriginalMember(owner = "client!sc", name = "F", descriptor = "I")
	private int iteratorIndex = 0;

	@OriginalMember(owner = "client!sc", name = "c", descriptor = "[Lclient!ab;")
	public final Node[] nodes;

	@OriginalMember(owner = "client!sc", name = "h", descriptor = "I")
	public final int bucketCount;

	@OriginalMember(owner = "client!sc", name = "<init>", descriptor = "(I)V")
	public HashTable(@OriginalArg(0) int size) {
		this.nodes = new Node[size];
		this.bucketCount = size;
		for (@Pc(13) int i = 0; i < size; i++) {
			@Pc(25) Node node = this.nodes[i] = new Node();
			node.previousNode = node;
			node.nextNode = node;
		}
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(I)V")
	public final void clear() {
		for (@Pc(5) int i = 0; i < this.bucketCount; i++) {
			@Pc(14) Node sentinel = this.nodes[i];
			while (true) {
				@Pc(17) Node node = sentinel.nextNode;
				if (sentinel == node) {
					break;
				}
				node.unlink();
			}
		}
		this.iteratorCursor = null;
		this.getCursor = null;
	}

	@OriginalMember(owner = "client!sc", name = "c", descriptor = "(I)Lclient!ab;")
	public final Node head() {
		this.iteratorIndex = 0;
		return this.next();
	}

	@OriginalMember(owner = "client!sc", name = "d", descriptor = "(I)Lclient!ab;")
	public final Node next() {
		@Pc(24) Node node;
		if (this.iteratorIndex > 0 && this.iteratorCursor != this.nodes[this.iteratorIndex - 1]) {
			node = this.iteratorCursor;
			this.iteratorCursor = node.nextNode;
			return node;
		}
		do {
			if (this.iteratorIndex >= this.bucketCount) {
				return null;
			}
			node = this.nodes[this.iteratorIndex++].nextNode;
		} while (this.nodes[this.iteratorIndex - 1] == node);
		this.iteratorCursor = node.nextNode;
		return node;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(ILclient!ab;J)V")
	public final void put(@OriginalArg(1) Node node, @OriginalArg(2) long key) {
		if (node.previousNode != null) {
			node.unlink();
		}
		@Pc(21) Node bucket = this.nodes[(int) (key & (long) (this.bucketCount - 1))];
		node.nextNode = bucket;
		node.key = key;
		node.previousNode = bucket.previousNode;
		node.previousNode.nextNode = node;
		node.nextNode.previousNode = node;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(JI)Lclient!ab;")
	public final Node get(@OriginalArg(0) long key) {
		this.lastGetKey = key;
		@Pc(24) Node bucket = this.nodes[(int) (key & (long) (this.bucketCount - 1))];
		for (this.getCursor = bucket.nextNode; this.getCursor != bucket; this.getCursor = this.getCursor.nextNode) {
			if (key == this.getCursor.key) {
				@Pc(46) Node found = this.getCursor;
				this.getCursor = this.getCursor.nextNode;
				return found;
			}
		}
		this.getCursor = null;
		return null;
	}

	@OriginalMember(owner = "client!sc", name = "e", descriptor = "(I)I")
	public final int size() {
		@Pc(15) int count = 0;
		for (@Pc(17) int i = 0; i < this.bucketCount; i++) {
			@Pc(26) Node sentinel = this.nodes[i];
			@Pc(29) Node node = sentinel.nextNode;
			while (node != sentinel) {
				node = node.nextNode;
				count++;
			}
		}
		return count;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "([Lclient!ab;I)I")
	public final int toArray(@OriginalArg(0) Node[] dest) {
		@Pc(13) int count = 0;
		for (@Pc(15) int i = 0; i < this.bucketCount; i++) {
			@Pc(24) Node sentinel = this.nodes[i];
			for (@Pc(27) Node node = sentinel.nextNode; node != sentinel; node = node.nextNode) {
				dest[count++] = node;
			}
		}
		return count;
	}

	@OriginalMember(owner = "client!sc", name = "f", descriptor = "(I)Lclient!ab;")
	public final Node nextWithKey() {
		if (this.getCursor == null) {
			return null;
		}
		@Pc(23) Node bucket = this.nodes[(int) (this.lastGetKey & (long) (this.bucketCount - 1))];
		while (bucket != this.getCursor) {
			if (this.getCursor.key == this.lastGetKey) {
				@Pc(45) Node found = this.getCursor;
				this.getCursor = this.getCursor.nextNode;
				return found;
			}
			this.getCursor = this.getCursor.nextNode;
		}
		this.getCursor = null;
		return null;
	}

	@OriginalMember(owner = "client!sc", name = "g", descriptor = "(I)I")
	public final int getBucketCount() {
		return this.bucketCount;
	}
}
