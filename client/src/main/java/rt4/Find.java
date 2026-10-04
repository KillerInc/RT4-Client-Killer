package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class Find {
	@OriginalMember(owner = "client!nf", name = "c", descriptor = "[S")
	public static short[] results;
	@OriginalMember(owner = "client!fe", name = "x", descriptor = "I")
	public static int size;
	@OriginalMember(owner = "client!ii", name = "l", descriptor = "I")
	public static int index;

	@OriginalMember(owner = "client!bn", name = "a", descriptor = "(BZLclient!na;)V")
	public static void findQuickChatPhrases(@OriginalArg(1) boolean useArchive2, @OriginalArg(2) JagString query) {
		@Pc(9) JagString lowerQuery = query.toLowerCase();
		@Pc(11) int count = 0;
		@Pc(22) short[] matches = new short[16];
		@Pc(28) int startId = useArchive2 ? 32768 : 0;
		@Pc(36) int endId = (useArchive2 ? QuickChatPhraseTypeList.archive2PhraseCount : QuickChatPhraseTypeList.archive1PhraseCount) + startId;
		for (@Pc(38) int id = startId; id < endId; id++) {
			@Pc(45) QuickChatPhraseType phrase = QuickChatPhraseTypeList.get(id);
			if (phrase.searchable && phrase.getText().toLowerCase().indexOf(lowerQuery) != -1) {
				if (count >= 50) {
					index = -1;
					results = null;
					return;
				}
				if (count >= matches.length) {
					@Pc(79) short[] expanded = new short[matches.length * 2];
					for (@Pc(81) int i = 0; i < count; i++) {
						expanded[i] = matches[i];
					}
					matches = expanded;
				}
				matches[count++] = (short) id;
			}
		}
		results = matches;
		index = count;
		size = 0;
		@Pc(113) JagString[] names = new JagString[index];
		for (@Pc(115) int i = 0; i < index; i++) {
			names[i] = QuickChatPhraseTypeList.get(matches[i]).getText();
		}
		sortResults(names, results);
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(ZLclient!na;I)V")
	public static void search(@OriginalArg(0) boolean stockMarketOnly, @OriginalArg(1) JagString query) {
		@Pc(8) short[] matches = new short[16];
		@Pc(12) JagString lowerQuery = query.toLowerCase();
		@Pc(14) int count = 0;
		for (@Pc(16) int id = 0; id < ObjTypeList.capacity; id++) {
			@Pc(27) ObjType objType = ObjTypeList.get(id);
			if ((!stockMarketOnly || objType.stockMarket) && objType.certtemplate == -1 && objType.lentTemplate == -1 && objType.dummyItem == 0 && objType.name.toLowerCase().indexOf(lowerQuery) != -1) {
				if (count >= 250) {
					results = null;
					index = -1;
					return;
				}
				if (count >= matches.length) {
					@Pc(83) short[] expanded = new short[matches.length * 2];
					for (@Pc(85) int i = 0; i < count; i++) {
						expanded[i] = matches[i];
					}
					matches = expanded;
				}
				matches[count++] = (short) id;
			}
		}
		results = matches;
		size = 0;
		index = count;
		@Pc(117) JagString[] names = new JagString[index];
		for (@Pc(119) int i = 0; i < index; i++) {
			names[i] = ObjTypeList.get(matches[i]).name;
		}
		sortResults(names, results);
	}

	@OriginalMember(owner = "client!qg", name = "a", descriptor = "([Lclient!na;[SI)V")
	public static void sortResults(@OriginalArg(0) JagString[] names, @OriginalArg(1) short[] ids) {
		quicksort(ids, names.length - 1, names, 0);
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "([SI[Lclient!na;II)V")
	public static void quicksort(@OriginalArg(0) short[] ids, @OriginalArg(1) int right, @OriginalArg(2) JagString[] names, @OriginalArg(4) int left) {
		if (right <= left) {
			return;
		}
		@Pc(14) int storeIdx = left;
		@Pc(21) int mid = (left + right) / 2;
		@Pc(25) JagString pivot = names[mid];
		names[mid] = names[right];
		names[right] = pivot;
		@Pc(39) short pivotId = ids[mid];
		ids[mid] = ids[right];
		ids[right] = pivotId;
		for (@Pc(51) int i = left; i < right; i++) {
			if (pivot == null || names[i] != null && names[i].compareTo(pivot) < (i & 0x1)) {
				@Pc(80) JagString tempName = names[i];
				names[i] = names[storeIdx];
				names[storeIdx] = tempName;
				@Pc(94) short tempId = ids[i];
				ids[i] = ids[storeIdx];
				ids[storeIdx++] = tempId;
			}
		}
		names[right] = names[storeIdx];
		names[storeIdx] = pivot;
		ids[right] = ids[storeIdx];
		ids[storeIdx] = pivotId;
		quicksort(ids, storeIdx - 1, names, left);
		quicksort(ids, right, names, storeIdx + 1);
	}
}
