package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ml")
public final class EnumType extends SecondaryNode {

	@OriginalMember(owner = "client!ml", name = "N", descriptor = "I")
	public int valueType;

	@OriginalMember(owner = "client!ml", name = "V", descriptor = "I")
	public int keyType;

	@OriginalMember(owner = "client!ml", name = "X", descriptor = "Lclient!sc;")
	public HashTable table;

	@OriginalMember(owner = "client!ml", name = "bb", descriptor = "Lclient!sc;")
	private HashTable inverseTable;

	@OriginalMember(owner = "client!ml", name = "cb", descriptor = "I")
	private int defaultInt;

	@OriginalMember(owner = "client!gn", name = "v", descriptor = "Lclient!na;")
	public static final JagString NULL = JagString.parse("null");
	@OriginalMember(owner = "client!ml", name = "Z", descriptor = "Lclient!na;")
	private JagString defaultString = NULL;

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(ILclient!wa;B)V")
	private void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 1) {
			this.keyType = buf.g1();
		} else if (opcode == 2) {
			this.valueType = buf.g1();
		} else if (opcode == 3) {
			this.defaultString = buf.gjstr();
		} else if (opcode == 4) {
			this.defaultInt = buf.g4();
		} else if (opcode == 5 || opcode == 6) {
			@Pc(41) int size = buf.g2();
			this.table = new HashTable(IntUtils.clp2(size));
			for (@Pc(51) int i = 0; i < size; i++) {
				@Pc(58) int key = buf.g4();
				@Pc(70) Node node;
				if (opcode == 5) {
					node = new StringNode(buf.gjstr());
				} else {
					node = new IntNode(buf.g4());
				}
				this.table.put(node, key);
			}
		}
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(IB)Lclient!na;")
	public final JagString getString(@OriginalArg(0) int key) {
		if (this.table == null) {
			return this.defaultString;
		} else {
			@Pc(26) StringNode node = (StringNode) this.table.get(key);
			return node == null ? this.defaultString : node.value;
		}
	}

	@OriginalMember(owner = "client!ml", name = "b", descriptor = "(Lclient!na;I)Z")
	public final boolean containsValue(@OriginalArg(0) JagString value) {
		if (this.table == null) {
			return false;
		}
		if (this.inverseTable == null) {
			this.inverseStrings();
		}
		for (@Pc(38) EnumStringEntry node = (EnumStringEntry) this.inverseTable.get(value.longHashCode()); node != null; node = (EnumStringEntry) this.inverseTable.nextWithKey()) {
			if (node.value.strEquals(value)) {
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!ml", name = "d", descriptor = "(I)V")
	private void inverseStrings() {
		this.inverseTable = new HashTable(this.table.getBucketCount());
		for (@Pc(22) StringNode node = (StringNode) this.table.head(); node != null; node = (StringNode) this.table.next()) {
			@Pc(36) EnumStringEntry entry = new EnumStringEntry(node.value, (int) node.key);
			this.inverseTable.put(entry, node.value.longHashCode());
		}
	}

	@OriginalMember(owner = "client!ml", name = "c", descriptor = "(II)I")
	public final int getInt(@OriginalArg(1) int key) {
		if (this.table == null) {
			return this.defaultInt;
		} else {
			@Pc(18) IntNode node = (IntNode) this.table.get(key);
			return node == null ? this.defaultInt : node.value;
		}
	}

	@OriginalMember(owner = "client!ml", name = "d", descriptor = "(II)Z")
	public final boolean containsValue(@OriginalArg(1) int value) {
		if (this.table == null) {
			return false;
		}
		if (this.inverseTable == null) {
			this.inverseInts();
		}
		@Pc(34) IntNode node = (IntNode) this.inverseTable.get(value);
		return node != null;
	}

	@OriginalMember(owner = "client!ml", name = "e", descriptor = "(I)V")
	private void inverseInts() {
		this.inverseTable = new HashTable(this.table.getBucketCount());
		for (@Pc(24) IntNode node = (IntNode) this.table.head(); node != null; node = (IntNode) this.table.next()) {
			@Pc(34) IntNode inverseNode = new IntNode((int) node.key);
			this.inverseTable.put(inverseNode, node.value);
		}
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(Lclient!wa;I)V")
	public final void decode(@OriginalArg(0) Buffer buf) {
		while (true) {
			@Pc(9) int opcode = buf.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(opcode, buf);
		}
	}
}
