package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class MapList {
	@OriginalMember(owner = "client!sh", name = "a", descriptor = "Lclient!ih;")
	public static final LinkedList maps = new LinkedList();
	@OriginalMember(owner = "client!ed", name = "D", descriptor = "Lclient!na;")
	public static final JagString DETAILS_GROUP = JagString.parse("details");
	@OriginalMember(owner = "client!je", name = "W", descriptor = "Lclient!ve;")
	public static Js5 archive;
	@OriginalMember(owner = "client!th", name = "p", descriptor = "[Lclient!mm;")
	public static SoftwareSprite[] sprites;
	@OriginalMember(owner = "client!va", name = "G", descriptor = "[Z")
	public static boolean[] visibility;

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "([Lclient!mm;ILclient!ve;)V")
	public static void init(@OriginalArg(0) SoftwareSprite[] mapSprites, @OriginalArg(2) Js5 js5) {
		archive = js5;
		sprites = mapSprites;
		visibility = new boolean[sprites.length];
		maps.clear();
		@Pc(25) int groupId = archive.getGroupId(DETAILS_GROUP);
		@Pc(30) int[] fileIds = archive.getFileIds(groupId);
		for (@Pc(32) int i = 0; i < fileIds.length; i++) {
			maps.addTail(Map.create(new Buffer(archive.fetchFile(groupId, fileIds[i]))));
		}
	}

	@OriginalMember(owner = "client!jk", name = "a", descriptor = "(ILclient!na;)Lclient!bn;")
	public static Map get(@OriginalArg(1) JagString name) {
		for (@Pc(15) Map map = (Map) maps.head(); map != null; map = (Map) maps.next()) {
			if (map.group.strEquals(name)) {
				return map;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(IBI)Lclient!bn;")
	public static Map getContainingSource(@OriginalArg(0) int displayX, @OriginalArg(2) int displayY) {
		for (@Pc(10) Map map = (Map) maps.head(); map != null; map = (Map) maps.next()) {
			if (map.valid && map.containsDisplayCoordinate(displayY, displayX)) {
				return map;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!hb", name = "b", descriptor = "(II)Z")
	public static boolean isMapVisible(@OriginalArg(0) int index) {
		return index >= 0 && visibility.length > index && visibility[index];
	}

	@OriginalMember(owner = "client!ui", name = "d", descriptor = "(II)V")
	public static void toggleMapVisibility(@OriginalArg(0) int index) {
		if (index >= 0 && visibility.length > index) {
			visibility[index] = !visibility[index];
		}
	}
}
