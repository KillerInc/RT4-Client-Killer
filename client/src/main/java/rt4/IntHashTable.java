package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!jg")
public final class IntHashTable {

	@OriginalMember(owner = "client!jg", name = "f", descriptor = "[I")
	private final int[] table;

	@OriginalMember(owner = "client!jg", name = "<init>", descriptor = "([I)V")
	public IntHashTable(@OriginalArg(0) int[] keys) {
		@Pc(5) int capacity;
		for (capacity = 1; capacity <= (keys.length >> 1) + keys.length; capacity <<= 0x1) {
		}
		this.table = new int[capacity + capacity];
		@Pc(28) int i;
		for (i = 0; i < capacity + capacity; i++) {
			this.table[i] = -1;
		}
		i = 0;
		while (keys.length > i) {
			@Pc(55) int slot;
			for (slot = capacity - 1 & keys[i]; this.table[slot + slot + 1] != -1; slot = capacity - 1 & slot + 1) {
			}
			this.table[slot + slot] = keys[i];
			this.table[slot + slot + 1] = i++;
		}
	}

	@OriginalMember(owner = "client!jg", name = "a", descriptor = "(II)I")
	public final int get(@OriginalArg(0) int key) {
		@Pc(16) int mask = (this.table.length >> 1) - 1;
		@Pc(20) int slot = mask & key;
		while (true) {
			@Pc(29) int value = this.table[slot + slot + 1];
			if (value == -1) {
				return -1;
			}
			if (key == this.table[slot + slot]) {
				return value;
			}
			slot = slot + 1 & mask;
		}
	}
}
