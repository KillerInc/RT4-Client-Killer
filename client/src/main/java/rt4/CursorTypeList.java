package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class CursorTypeList {
	@OriginalMember(owner = "client!ge", name = "i", descriptor = "Lclient!n;")
	public static final SoftLruHashTable types = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!ah", name = "i", descriptor = "Lclient!n;")
	public static final SoftLruHashTable sprites = new SoftLruHashTable(2);
	@OriginalMember(owner = "client!mc", name = "Z", descriptor = "Lclient!ve;")
	public static Js5 archive;
	@OriginalMember(owner = "client!tk", name = "j", descriptor = "Lclient!ve;")
	public static Js5 spritesArchive;

	@OriginalMember(owner = "client!u", name = "a", descriptor = "(BLclient!ve;Lclient!ve;)V")
	public static void init(@OriginalArg(1) Js5 cursorArchive, @OriginalArg(2) Js5 spriteArchive) {
		archive = cursorArchive;
		spritesArchive = spriteArchive;
	}

	@OriginalMember(owner = "client!qg", name = "d", descriptor = "(II)Lclient!ia;")
	public static CursorType get(@OriginalArg(0) int id) {
		@Pc(10) CursorType type = (CursorType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(20) byte[] data = archive.fetchFile(33, id);
		type = new CursorType();
		if (data != null) {
			type.decode(new Buffer(data), id);
		}
		types.put(type, id);
		return type;
	}

	@OriginalMember(owner = "client!an", name = "i", descriptor = "(I)V")
	public static void clear() {
		types.clear();
		sprites.clear();
	}

	@OriginalMember(owner = "client!c", name = "d", descriptor = "(II)V")
	public static void clean() {
		types.clean(5);
		sprites.clean(5);
	}

	@OriginalMember(owner = "client!ca", name = "a", descriptor = "(Z)V")
	public static void removeSoft() {
		types.removeSoft();
		sprites.removeSoft();
	}
}
