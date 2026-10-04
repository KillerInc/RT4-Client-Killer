package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class MsiTypeList {
	@OriginalMember(owner = "client!sk", name = "hb", descriptor = "Lclient!n;")
	public static final SoftLruHashTable types = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!rl", name = "P", descriptor = "Lclient!n;")
	public static final SoftLruHashTable sprites = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!hj", name = "m", descriptor = "Lclient!ve;")
	public static Js5 archive;
	@OriginalMember(owner = "client!uf", name = "r", descriptor = "Lclient!ve;")
	public static Js5 spritesArchive;
	@OriginalMember(owner = "client!vk", name = "o", descriptor = "I")
	public static int blueDelta;
	@OriginalMember(owner = "client!gl", name = "e", descriptor = "I")
	public static int greenDelta;
	@OriginalMember(owner = "client!nk", name = "E", descriptor = "I")
	public static int redDelta;

	@OriginalMember(owner = "client!da", name = "c", descriptor = "(II)Lclient!aa;")
	public static MsiType get(@OriginalArg(0) int id) {
		@Pc(10) MsiType type = (MsiType) types.get(id);
		if (type != null) {
			return type;
		}
		@Pc(20) byte[] data = archive.fetchFile(34, id);
		type = new MsiType();
		if (data != null) {
			type.decode(new Buffer(data), id);
		}
		types.put(type, id);
		return type;
	}

	@OriginalMember(owner = "client!og", name = "a", descriptor = "(Lclient!ve;Lclient!ve;B)V")
	public static void init(@OriginalArg(0) Js5 js5, @OriginalArg(1) Js5 spriteJs5) {
		spritesArchive = spriteJs5;
		@Pc(12) int randA = (int) (Math.random() * 21.0D) - 10;
		archive = js5;
		@Pc(21) int randB = (int) (Math.random() * 21.0D) - 10;
		archive.getGroupCapacity(34);
		@Pc(33) int randC = (int) (Math.random() * 21.0D) - 10;
		@Pc(40) int randBase = (int) (Math.random() * 41.0D) - 20;
		blueDelta = randBase + randB;
		greenDelta = randA + randBase;
		redDelta = randBase + randC;
	}

	@OriginalMember(owner = "client!qg", name = "h", descriptor = "(I)V")
	public static void clear() {
		types.clear();
		sprites.clear();
	}

	@OriginalMember(owner = "client!vl", name = "b", descriptor = "(I)V")
	public static void removeSoft() {
		types.removeSoft();
		sprites.removeSoft();
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(II)V")
	public static void clean() {
		types.clean(5);
		sprites.clean(5);
	}
}
