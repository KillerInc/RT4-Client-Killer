package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class StructTypeList {
	@OriginalMember(owner = "client!sk", name = "bb", descriptor = "Lclient!gn;")
	public static final LruHashTable types = new LruHashTable(64);
	@OriginalMember(owner = "client!bm", name = "e", descriptor = "Lclient!ve;")
	public static Js5 archive;

	@OriginalMember(owner = "client!eh", name = "a", descriptor = "(Lclient!ve;I)V")
	public static void init(@OriginalArg(0) Js5 js5) {
		archive = js5;
	}

	@OriginalMember(owner = "client!jj", name = "a", descriptor = "(BI)Lclient!lk;")
	public static StructType get(@OriginalArg(1) int id) {
		@Pc(10) StructType type = (StructType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(26) byte[] data = archive.fetchFile(26, id);
		type = new StructType();
		if (data != null) {
			type.decode(new Buffer(data));
		}
		types.put(type, id);
		return type;
	}
}
