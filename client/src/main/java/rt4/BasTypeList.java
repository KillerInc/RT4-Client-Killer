package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class BasTypeList {
	@OriginalMember(owner = "client!vf", name = "a", descriptor = "Lclient!n;")
	public static final SoftLruHashTable types = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!nd", name = "p", descriptor = "Lclient!ve;")
	public static Js5 archive;

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(BLclient!ve;)V")
	public static void init(@OriginalArg(1) Js5 js5) {
		archive = js5;
	}

	@OriginalMember(owner = "client!hb", name = "a", descriptor = "(ZI)Lclient!ck;")
	public static BasType get(@OriginalArg(1) int id) {
		@Pc(10) BasType type = (BasType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(21) byte[] data = archive.fetchFile(32, id);
		type = new BasType();
		if (data != null) {
			type.decode(new Buffer(data));
		}
		type.postDecode();
		types.put(type, id);
		return type;
	}

	@OriginalMember(owner = "client!jk", name = "e", descriptor = "(B)V")
	public static void clear() {
		types.clear();
	}

	@OriginalMember(owner = "client!bi", name = "c", descriptor = "(II)V")
	public static void clean() {
		types.clean(5);
	}

	@OriginalMember(owner = "client!di", name = "d", descriptor = "(I)V")
	public static void removeSoft() {
		types.removeSoft();
	}
}
