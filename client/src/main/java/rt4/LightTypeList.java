package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class LightTypeList {
	@OriginalMember(owner = "client!rm", name = "d", descriptor = "Lclient!n;")
	public static final SoftLruHashTable types = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!gl", name = "a", descriptor = "Lclient!ve;")
	public static Js5 archive;

	@OriginalMember(owner = "client!id", name = "a", descriptor = "(Lclient!ve;B)V")
	public static void init(@OriginalArg(0) Js5 js5) {
		archive = js5;
	}

	@OriginalMember(owner = "client!la", name = "a", descriptor = "(II)Lclient!ic;")
	public static LightType get(@OriginalArg(1) int id) {
		@Pc(10) LightType type = (LightType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(26) byte[] data = archive.fetchFile(31, id);
		type = new LightType();
		if (data != null) {
			type.decode(new Buffer(data), id);
		}
		types.put(type, id);
		return type;
	}

	@OriginalMember(owner = "client!c", name = "c", descriptor = "(II)V")
	public static void clean() {
		types.clean(5);
	}

	@OriginalMember(owner = "client!gd", name = "b", descriptor = "(I)V")
	public static void clear() {
		types.clear();
	}

	@OriginalMember(owner = "client!hd", name = "a", descriptor = "(I)V")
	public static void removeSoft() {
		types.removeSoft();
	}
}
