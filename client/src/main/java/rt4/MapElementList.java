package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!se")
public final class MapElementList {

	@OriginalMember(owner = "client!se", name = "i", descriptor = "I")
	public final int count;

	@OriginalMember(owner = "client!se", name = "j", descriptor = "[Lclient!na;")
	public final JagString[] names;

	@OriginalMember(owner = "client!se", name = "d", descriptor = "[S")
	public final short[] coordY;

	@OriginalMember(owner = "client!se", name = "n", descriptor = "[I")
	public final int[] colors;

	@OriginalMember(owner = "client!se", name = "u", descriptor = "[B")
	public final byte[] flags;

	@OriginalMember(owner = "client!se", name = "q", descriptor = "[S")
	public final short[] coordX;

	@OriginalMember(owner = "client!se", name = "<init>", descriptor = "(I)V")
	public MapElementList(@OriginalArg(0) int count) {
		this.count = count;
		this.names = new JagString[this.count];
		this.coordY = new short[this.count];
		this.colors = new int[this.count];
		this.flags = new byte[this.count];
		this.coordX = new short[this.count];
	}

	@OriginalMember(owner = "client!la", name = "a", descriptor = "(ILclient!na;Lclient!ve;)Lclient!se;")
	public static MapElementList create(@OriginalArg(1) JagString name, @OriginalArg(2) Js5 archive) {
		@Pc(10) int groupId = archive.getGroupId(name);
		if (groupId == -1) {
			return new MapElementList(0);
		}
		@Pc(29) int[] fileIds = archive.getFileIds(groupId);
		@Pc(35) MapElementList list = new MapElementList(fileIds.length);
		for (@Pc(37) int i = 0; i < list.count; i++) {
			@Pc(56) Buffer buf = new Buffer(archive.fetchFile(groupId, fileIds[i]));
			list.names[i] = buf.gjstr();
			list.flags[i] = buf.g1b();
			list.coordX[i] = (short) buf.g2();
			list.coordY[i] = (short) buf.g2();
			list.colors[i] = buf.g4();
		}
		return list;
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(IB)Z")
	public final boolean isTextLabel(@OriginalArg(0) int index) {
		return (this.flags[index] & 0x8) != 0;
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(II)Z")
	public final boolean isMinimapLabelVisible(@OriginalArg(0) int index) {
		return (this.flags[index] & 0x4) != 0;
	}

	@OriginalMember(owner = "client!se", name = "b", descriptor = "(II)I")
	public final int getLabelSize(@OriginalArg(0) int index) {
		return this.flags[index] & 0x3;
	}

	@OriginalMember(owner = "client!se", name = "c", descriptor = "(II)Z")
	public final boolean isVisible(@OriginalArg(0) int index) {
		return (this.flags[index] & 0x10) == 0;
	}
}
