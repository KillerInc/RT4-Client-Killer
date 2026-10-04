package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!da")
public final class DelayedStateChange extends SecondaryNode {

	@OriginalMember(owner = "client!client", name = "U", descriptor = "Lclient!sc;")
	public static final HashTable changes = new HashTable(16);
	@OriginalMember(owner = "client!la", name = "f", descriptor = "Lclient!ce;")
	public static final SecondaryLinkedList clientQueue = new SecondaryLinkedList();
	@OriginalMember(owner = "client!rh", name = "e", descriptor = "Lclient!ce;")
	public static final SecondaryLinkedList serverQueue = new SecondaryLinkedList();
	@OriginalMember(owner = "client!da", name = "T", descriptor = "I")
	public int intArg2;

	@OriginalMember(owner = "client!da", name = "U", descriptor = "I")
	public int intArg3;

	@OriginalMember(owner = "client!da", name = "V", descriptor = "I")
	public int intArg1;

	@OriginalMember(owner = "client!da", name = "W", descriptor = "Lclient!na;")
	public JagString stringArg;

	@OriginalMember(owner = "client!da", name = "<init>", descriptor = "(II)V")
	public DelayedStateChange(@OriginalArg(0) int type, @OriginalArg(1) int id) {
		this.key = (long) type << 32 | (long) id;
	}

	@OriginalMember(owner = "client!bj", name = "d", descriptor = "(B)V")
	public static void clear() {
		changes.clear();
		clientQueue.clear();
		serverQueue.clear();
	}

	@OriginalMember(owner = "client!pf", name = "a", descriptor = "(III)V")
	public static void setComponentModelRotationSpeedServer(@OriginalArg(0) int speed, @OriginalArg(1) int componentId) {
		@Pc(14) DelayedStateChange change = create(13, componentId);
		change.pushServer();
		change.intArg1 = speed;
	}

	@OriginalMember(owner = "client!pi", name = "a", descriptor = "(Lclient!na;BI)V")
	public static void setVarcStrServer(@OriginalArg(0) JagString value, @OriginalArg(2) int varcId) {
		@Pc(10) DelayedStateChange change = create(2, varcId);
		change.pushServer();
		change.stringArg = value;
	}

	@OriginalMember(owner = "client!mc", name = "c", descriptor = "(III)V")
	public static void setComponentHiddenServer(@OriginalArg(1) int componentId, @OriginalArg(2) int hidden) {
		@Pc(14) DelayedStateChange change = create(7, componentId);
		change.pushServer();
		change.intArg1 = hidden;
	}

	@OriginalMember(owner = "client!ke", name = "c", descriptor = "(III)V")
	public static void updateVarC(@OriginalArg(0) int varcId, @OriginalArg(1) int value) {
		@Pc(8) DelayedStateChange change = create(1, varcId);
		change.pushServer();
		change.intArg1 = value;
	}

	@OriginalMember(owner = "client!ke", name = "a", descriptor = "(IIIBI)V")
	public static void updateComponentModel(@OriginalArg(0) int modelType, @OriginalArg(1) int modelId, @OriginalArg(2) int componentId, @OriginalArg(4) int modelArg) {
		@Pc(8) DelayedStateChange change = create(4, componentId);
		change.pushServer();
		change.intArg3 = modelArg;
		change.intArg2 = modelType;
		change.intArg1 = modelId;
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(III)V")
	public static void setComponentAnimServer(@OriginalArg(0) int componentId, @OriginalArg(1) int animId) {
		@Pc(14) DelayedStateChange change = create(5, componentId);
		change.pushServer();
		change.intArg1 = animId;
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(BIIII)V")
	public static void updateView(@OriginalArg(1) int zoom, @OriginalArg(2) int componentId, @OriginalArg(3) int rotationY, @OriginalArg(4) int rotationX) {
		@Pc(8) DelayedStateChange change = create(8, componentId);
		change.pushServer();
		change.intArg2 = zoom;
		change.intArg1 = rotationX;
		change.intArg3 = rotationY;
	}

	@OriginalMember(owner = "client!kk", name = "a", descriptor = "(IIB)V")
	public static void setColor(@OriginalArg(0) int color, @OriginalArg(1) int componentId) {
		@Pc(4) DelayedStateChange change = create(6, componentId);
		change.pushServer();
		change.intArg1 = color;
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(IIII)V")
	public static void setComponentPositionServer(@OriginalArg(0) int x, @OriginalArg(1) int componentId, @OriginalArg(3) int y) {
		@Pc(18) DelayedStateChange change = create(11, componentId);
		change.pushServer();
		change.intArg3 = y;
		change.intArg1 = x;
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Lclient!na;II)V")
	public static void setComponentTextServer(@OriginalArg(0) JagString text, @OriginalArg(2) int componentId) {
		@Pc(6) DelayedStateChange change = create(3, componentId);
		change.pushServer();
		change.stringArg = text;
	}

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(IB)V")
	public static void setComponentObjClient(@OriginalArg(0) int componentId) {
		@Pc(14) DelayedStateChange change = create(9, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!mc", name = "a", descriptor = "(BI)V")
	public static void setComponentModelAngleClient(@OriginalArg(1) int componentId) {
		@Pc(4) DelayedStateChange change = create(8, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!og", name = "a", descriptor = "(II)V")
	public static void setComponentModelOffsetClient(@OriginalArg(0) int componentId) {
		@Pc(12) DelayedStateChange change = create(10, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "(III)Lclient!da;")
	public static DelayedStateChange create(@OriginalArg(1) int type, @OriginalArg(2) int id) {
		@Pc(13) DelayedStateChange change = (DelayedStateChange) changes.get((long) id | (long) type << 32);
		if (change == null) {
			change = new DelayedStateChange(type, id);
			changes.put(change, change.key);
		}
		return change;
	}

	@OriginalMember(owner = "client!wc", name = "a", descriptor = "(ZI)V")
	public static void setComponentModelClient(@OriginalArg(1) int componentId) {
		@Pc(8) DelayedStateChange change = create(4, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(IB)V")
	public static void setComponentScrollClient(@OriginalArg(0) int componentId) {
		@Pc(12) DelayedStateChange change = create(12, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(B)Lclient!da;")
	public static DelayedStateChange poll() {
		@Pc(10) DelayedStateChange change = (DelayedStateChange) serverQueue.head();
		if (change != null) {
			change.unlink();
			change.unlinkSecondary();
			return change;
		}
		do {
			change = (DelayedStateChange) clientQueue.head();
			if (change == null) {
				return null;
			}
			if (change.getTime() > MonotonicClock.currentTimeMillis()) {
				return null;
			}
			change.unlink();
			change.unlinkSecondary();
		} while ((Long.MIN_VALUE & change.secondaryKey) == 0L);
		return change;
	}

	@OriginalMember(owner = "client!lf", name = "a", descriptor = "(IIIIB)V")
	public static void setComponentModelOffsetServer(@OriginalArg(0) int componentId, @OriginalArg(1) int offsetX, @OriginalArg(2) int offsetZ, @OriginalArg(3) int offsetY) {
		@Pc(8) DelayedStateChange change = create(10, componentId);
		change.pushServer();
		change.intArg3 = offsetZ;
		change.intArg1 = offsetY;
		change.intArg2 = offsetX;
	}

	@OriginalMember(owner = "client!n", name = "a", descriptor = "(II)V")
	public static void setComponentTextClient(@OriginalArg(0) int componentId) {
		@Pc(8) DelayedStateChange change = create(3, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!oi", name = "b", descriptor = "(II)V")
	public static void setComponentAnimClient(@OriginalArg(0) int componentId) {
		@Pc(8) DelayedStateChange change = create(5, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(BIII)V")
	public static void setComponentObjServer(@OriginalArg(1) int componentId, @OriginalArg(2) int count, @OriginalArg(3) int objId) {
		@Pc(12) DelayedStateChange change = create(9, componentId);
		change.pushServer();
		change.intArg1 = objId;
		change.intArg3 = count;
	}

	@OriginalMember(owner = "client!si", name = "b", descriptor = "(IIB)V")
	public static void setComponentScrollServer(@OriginalArg(0) int scrollHeight, @OriginalArg(1) int componentId) {
		@Pc(16) DelayedStateChange change = create(12, componentId);
		change.pushServer();
		change.intArg1 = scrollHeight;
	}

	@OriginalMember(owner = "client!tm", name = "a", descriptor = "(II)V")
	public static void setComponentColorClient(@OriginalArg(0) int componentId) {
		@Pc(16) DelayedStateChange change = create(6, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(II)V")
	public static void setComponentPositionClient(@OriginalArg(1) int componentId) {
		@Pc(17) DelayedStateChange change = create(11, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!ac", name = "a", descriptor = "(BI)V")
	public static void setVarpClient(@OriginalArg(1) int varpId) {
		@Pc(16) DelayedStateChange change = create(1, varpId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!ha", name = "b", descriptor = "(II)V")
	public static void setVarcStrClient(@OriginalArg(1) int varcId) {
		@Pc(8) DelayedStateChange change = create(2, varcId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!he", name = "c", descriptor = "(II)V")
	public static void setComponentHiddenClient(@OriginalArg(1) int componentId) {
		@Pc(12) DelayedStateChange change = create(7, componentId);
		change.pushClient();
	}

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(Z)V")
	public final void pushClient() {
		this.secondaryKey = MonotonicClock.currentTimeMillis() + 500L | Long.MIN_VALUE & this.secondaryKey;
		clientQueue.addTail(this);
	}

	@OriginalMember(owner = "client!da", name = "b", descriptor = "(Z)J")
	public final long getTime() {
		return this.secondaryKey & Long.MAX_VALUE;
	}

	@OriginalMember(owner = "client!da", name = "e", descriptor = "(I)I")
	public final int getType() {
		return (int) (this.key >>> 32 & 0xFFL);
	}

	@OriginalMember(owner = "client!da", name = "f", descriptor = "(B)I")
	public final int getId() {
		return (int) this.key;
	}

	@OriginalMember(owner = "client!da", name = "g", descriptor = "(B)V")
	public final void pushServer() {
		this.secondaryKey |= Long.MIN_VALUE;
		if (this.getTime() == 0L) {
			serverQueue.addTail(this);
		}
	}
}
