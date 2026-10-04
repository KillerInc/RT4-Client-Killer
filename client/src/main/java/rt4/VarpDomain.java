package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;

public class VarpDomain {
	@OriginalMember(owner = "client!gj", name = "q", descriptor = "[I")
	public static final int[] updatedVarps = new int[32];
	@OriginalMember(owner = "client!ic", name = "e", descriptor = "[I")
	public static final int[] varp = new int[3500];
	@OriginalMember(owner = "client!ah", name = "j", descriptor = "[I")
	public static final int[] activeVarps = new int[3500];
	@OriginalMember(owner = "client!uj", name = "s", descriptor = "Lclient!na;")
	public static final JagString NULL_STRING = JagString.parse("null");
	@OriginalMember(owner = "client!ea", name = "s", descriptor = "[I")
	public static final int[] BIT_MASKS = new int[32];
	@OriginalMember(owner = "client!fi", name = "n", descriptor = "I")
	public static int updatedVarpsWriterIndex = 0;
	@OriginalMember(owner = "client!qc", name = "K", descriptor = "Lclient!sc;")
	public static HashTable pendingVarpTimers = new HashTable(16);
	@OriginalMember(owner = "client!ge", name = "m", descriptor = "I")
	public static int chatEffectsDisabled = 0;
	@OriginalMember(owner = "client!jb", name = "n", descriptor = "I")
	public static int mouseButtons = 0;
	@OriginalMember(owner = "client!oe", name = "b", descriptor = "I")
	public static int inserting = 0;

	static {
		@Pc(10) int value = 2;
		for (@Pc(12) int i = 0; i < 32; i++) {
			BIT_MASKS[i] = value - 1;
			value += value;
		}
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(BII)V")
	public static void set(@OriginalArg(1) int value, @OriginalArg(2) int id) {
		PluginRepository.OnVarpUpdate(id, value);

		if (id > varp.length) return;

		varp[id] = value;
		@Pc(20) LongNode node = (LongNode) pendingVarpTimers.get(id);
		if (node == null) {
			node = new LongNode(4611686018427387905L);
			pendingVarpTimers.put(node, id);
		} else if (node.value != 4611686018427387905L) {
			node.value = MonotonicClock.currentTimeMillis() + 500L | 0x4000000000000000L;
		}
	}

	@OriginalMember(owner = "client!aj", name = "i", descriptor = "(I)V")
	public static void reset() {
		for (@Pc(3) int i = 0; i < VarpTypeList.count; i++) {
			@Pc(19) VarpType type = VarpTypeList.get(i);
			if (type != null && type.clientCode == 0) {
				varp[i] = 0;
				activeVarps[i] = 0;
			}
		}
		pendingVarpTimers = new HashTable(16);
	}

	@OriginalMember(owner = "client!li", name = "a", descriptor = "(III)V")
	public static void setVarp(@OriginalArg(0) int id, @OriginalArg(2) int value) {
		activeVarps[id] = value;
		@Pc(21) LongNode node = (LongNode) pendingVarpTimers.get(id);
		if (node == null) {
			node = new LongNode(MonotonicClock.currentTimeMillis() + 500L);
			pendingVarpTimers.put(node, id);
		} else {
			node.value = MonotonicClock.currentTimeMillis() + 500L;
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(II)I")
	public static int getVarbit(@OriginalArg(1) int id) {
		@Pc(13) VarbitType type = VarbitTypeList.get(id);
		@Pc(16) int baseVar = type.baseVar;
		@Pc(19) int endBit = type.endBit;
		@Pc(22) int startBit = type.startBit;
		@Pc(29) int mask = BIT_MASKS[endBit - startBit];
		return activeVarps[baseVar] >> startBit & mask;
	}

	@OriginalMember(owner = "client!qg", name = "a", descriptor = "(IZI)V")
	public static void setVarbitClient(@OriginalArg(0) int id, @OriginalArg(2) int value) {
		@Pc(7) VarbitType type = VarbitTypeList.get(id);
		@Pc(10) int endBit = type.endBit;
		@Pc(16) int startBit = type.startBit;
		@Pc(19) int baseVar = type.baseVar;
		@Pc(25) int mask = BIT_MASKS[endBit - startBit];
		if (value < 0 || value > mask) {
			value = 0;
		}
		mask <<= startBit;
		setVarp(baseVar, mask & value << startBit | activeVarps[baseVar] & ~mask);
	}

	@OriginalMember(owner = "client!wd", name = "a", descriptor = "(BII)V")
	public static void setVarbit(@OriginalArg(1) int value, @OriginalArg(2) int id) {
		@Pc(14) VarbitType type = VarbitTypeList.get(id);
		@Pc(17) int baseVar = type.baseVar;
		@Pc(20) int endBit = type.endBit;
		@Pc(23) int startBit = type.startBit;
		@Pc(29) int mask = BIT_MASKS[endBit - startBit];
		if (value < 0 || mask < value) {
			value = 0;
		}
		mask <<= startBit;
		set(value << startBit & mask | ~mask & varp[baseVar], baseVar);
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(II)V")
	public static void refreshMagicVarp(@OriginalArg(1) int id) {
		InterfaceList.redrawActiveInterfaces();
		AreaSoundManager.updateMulti();
		@Pc(17) int clientCode = VarpTypeList.get(id).clientCode;
		if (clientCode == 0) {
			return;
		}
		@Pc(25) int value = activeVarps[id];
		if (clientCode == 6) {
			chatEffectsDisabled = value;
		}
		if (clientCode == 5) {
			mouseButtons = value;
		}
		if (clientCode == 9) {
			inserting = value;
		}
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(ZI)I")
	public static int poll(@OriginalArg(0) boolean first) {
		@Pc(4) long now = MonotonicClock.currentTimeMillis();
		for (@Pc(28) LongNode node = first ? (LongNode) pendingVarpTimers.head() : (LongNode) pendingVarpTimers.next(); node != null; node = (LongNode) pendingVarpTimers.next()) {
			if ((node.value & 0x3FFFFFFFFFFFFFFFL) < now) {
				if ((node.value & 0x4000000000000000L) != 0L) {
					@Pc(58) int id = (int) node.key;
					activeVarps[id] = varp[id];
					node.unlink();
					return id;
				}
				node.unlink();
			}
		}
		return -1;
	}
}
