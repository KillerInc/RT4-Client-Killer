package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!lk")
public final class StructType extends SecondaryNode {

	@OriginalMember(owner = "client!lk", name = "I", descriptor = "Lclient!sc;")
	private HashTable params;

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(IIB)I")
	public final int getParam(@OriginalArg(0) int paramId, @OriginalArg(1) int defaultValue) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(29) IntNode node = (IntNode) this.params.get(paramId);
			return node == null ? defaultValue : node.value;
		}
	}

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(Lclient!wa;IB)V")
	private void decode(@OriginalArg(0) Buffer buf, @OriginalArg(1) int opcode) {
		if (opcode != 249) {
			return;
		}
		@Pc(17) int count = buf.g1();
		@Pc(25) int i;
		if (this.params == null) {
			i = IntUtils.clp2(count);
			this.params = new HashTable(i);
		}
		for (i = 0; i < count; i++) {
			@Pc(45) boolean isString = buf.g1() == 1;
			@Pc(49) int key = buf.g3();
			@Pc(58) Node node;
			if (isString) {
				node = new StringNode(buf.gjstr());
			} else {
				node = new IntNode(buf.g4());
			}
			this.params.put(node, key);
		}
	}

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(Lclient!na;BI)Lclient!na;")
	public final JagString getParam(@OriginalArg(0) JagString defaultValue, @OriginalArg(2) int paramId) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(16) StringNode node = (StringNode) this.params.get(paramId);
			return node == null ? defaultValue : node.value;
		}
	}

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(ILclient!wa;)V")
	public final void decode(@OriginalArg(1) Buffer buf) {
		while (true) {
			@Pc(5) int opcode = buf.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(buf, opcode);
		}
	}
}
