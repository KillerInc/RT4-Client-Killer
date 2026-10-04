package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class LocTypeList {
	@OriginalMember(owner = "client!oe", name = "j", descriptor = "Lclient!n;")
	public static final SoftLruHashTable types = new SoftLruHashTable(64);
	@OriginalMember(owner = "client!nf", name = "f", descriptor = "Lclient!n;")
	public static final SoftLruHashTable rawModelCache = new SoftLruHashTable(500);
	@OriginalMember(owner = "client!he", name = "fb", descriptor = "Lclient!n;")
	public static final SoftLruHashTable staticEntityCache = new SoftLruHashTable(30);
	@OriginalMember(owner = "client!vf", name = "l", descriptor = "Lclient!n;")
	public static final SoftLruHashTable animatedEntityCache = new SoftLruHashTable(50);
	@OriginalMember(owner = "client!cg", name = "c", descriptor = "Z")
	public static boolean allowMembers;
	@OriginalMember(owner = "client!lg", name = "g", descriptor = "Lclient!ve;")
	public static Js5 locsArchive;
	@OriginalMember(owner = "client!jg", name = "j", descriptor = "Lclient!ve;")
	public static Js5 modelsArchive;

	@OriginalMember(owner = "client!wc", name = "a", descriptor = "(II)Lclient!pb;")
	public static LocType get(@OriginalArg(1) int id) {
		@Pc(15) LocType loc = (LocType) types.get(id);
		if (loc != null) {
			return loc;
		}
		@Pc(30) byte[] data = locsArchive.fetchFile(getGroupId(id), getFileId(id));
		loc = new LocType();
		loc.id = id;
		if (data != null) {
			loc.decode(new Buffer(data));
		}
		loc.postDecode();
		if (!allowMembers && loc.members) {
			loc.ops = null;
		}
		if (loc.breakroutefinding) {
			loc.blockwalk = 0;
			loc.blockrange = false;
		}
		types.put(loc, id);
		return loc;
	}

	@OriginalMember(owner = "client!ui", name = "c", descriptor = "(II)I")
	public static int getGroupId(@OriginalArg(0) int id) {
		return id >>> 8;
	}

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(IB)I")
	public static int getFileId(@OriginalArg(0) int id) {
		return id & 0xFF;
	}

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(ILclient!ve;Lclient!ve;Z)V")
	public static void init(@OriginalArg(1) Js5 locsJs5, @OriginalArg(2) Js5 modelsJs5) {
		allowMembers = true;
		modelsArchive = modelsJs5;
		locsArchive = locsJs5;
	}

	@OriginalMember(owner = "client!oe", name = "b", descriptor = "(I)V")
	public static void removeSoft() {
		types.removeSoft();
		rawModelCache.removeSoft();
		staticEntityCache.removeSoft();
		animatedEntityCache.removeSoft();
	}

	@OriginalMember(owner = "client!va", name = "b", descriptor = "(II)V")
	public static void clean() {
		types.clean(5);
		rawModelCache.clean(5);
		staticEntityCache.clean(5);
		animatedEntityCache.clean(5);
	}

	@OriginalMember(owner = "client!hb", name = "c", descriptor = "(I)V")
	public static void clear() {
		types.clear();
		rawModelCache.clear();
		staticEntityCache.clear();
		animatedEntityCache.clear();
	}

	@OriginalMember(owner = "client!pe", name = "a", descriptor = "(BZ)V")
	public static void setAllowMembers(@OriginalArg(1) boolean allow) {
		if (allow != allowMembers) {
			allowMembers = allow;
			clear();
		}
	}
}
