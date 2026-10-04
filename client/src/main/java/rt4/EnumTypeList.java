package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class EnumTypeList {
	@OriginalMember(owner = "client!lj", name = "p", descriptor = "Lclient!gn;")
	public static final LruHashTable types = new LruHashTable(128);
	@OriginalMember(owner = "client!gk", name = "e", descriptor = "Lclient!ve;")
	public static Js5 archive;

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(Lclient!ve;I)V")
	public static void init(@OriginalArg(0) Js5 js5) {
		archive = js5;
	}

	@OriginalMember(owner = "client!ui", name = "a", descriptor = "(IZ)Lclient!ml;")
	public static EnumType get(@OriginalArg(0) int id) {
		@Pc(10) EnumType type = (EnumType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(24) byte[] data = archive.fetchFile(getGroupId(id), getFileId(id));
		type = new EnumType();
		if (data != null) {
			type.decode(new Buffer(data));
		}
		types.put(type, id);
		return type;
	}

	@OriginalMember(owner = "client!i", name = "e", descriptor = "(BI)I")
	public static int getFileId(@OriginalArg(1) int id) {
		return id & 0xFF;
	}

	@OriginalMember(owner = "client!hi", name = "a", descriptor = "(BI)I")
	public static int getGroupId(@OriginalArg(1) int id) {
		return id >>> 8;
	}
}
