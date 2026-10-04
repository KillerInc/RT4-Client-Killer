package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class QuickChatPhraseTypeList {
	@OriginalMember(owner = "client!ud", name = "P", descriptor = "Lclient!gn;")
	public static final LruHashTable types = new LruHashTable(64);
	@OriginalMember(owner = "client!vf", name = "n", descriptor = "Lclient!ve;")
	public static Js5 archive2;
	@OriginalMember(owner = "client!id", name = "j", descriptor = "Lclient!of;")
	public static QuickChatCommandDecoder decoder = null;
	@OriginalMember(owner = "client!te", name = "y", descriptor = "Lclient!ve;")
	public static Js5 archive1;
	@OriginalMember(owner = "client!le", name = "e", descriptor = "I")
	public static int archive1PhraseCount = 0;
	@OriginalMember(owner = "client!ck", name = "C", descriptor = "I")
	public static int archive2PhraseCount = 0;

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(Lclient!ve;ILclient!ve;Lclient!of;)V")
	public static void init(@OriginalArg(0) Js5 js5Archive2, @OriginalArg(2) Js5 js5Archive1, @OriginalArg(3) QuickChatCommandDecoder commandDecoder) {
		archive2 = js5Archive2;
		decoder = commandDecoder;
		archive1 = js5Archive1;
		if (archive1 != null) {
			archive1PhraseCount = archive1.getGroupCapacity(1);
		}
		if (archive2 != null) {
			archive2PhraseCount = archive2.getGroupCapacity(1);
		}
	}

	@OriginalMember(owner = "client!sj", name = "b", descriptor = "(II)Lclient!cb;")
	public static QuickChatPhraseType get(@OriginalArg(1) int id) {
		@Pc(10) QuickChatPhraseType quickChatPhrase = (QuickChatPhraseType) types.get(id);
		if (quickChatPhrase != null) {
			return quickChatPhrase;
		}
		@Pc(27) byte[] data;
		if (id < 32768) {
			data = archive1.fetchFile(1, id);
		} else {
			data = archive2.fetchFile(1, id & 0x7FFF);
		}
		quickChatPhrase = new QuickChatPhraseType();
		if (data != null) {
			quickChatPhrase.decode(new Buffer(data));
		}
		if (id >= 32768) {
			quickChatPhrase.postDecode();
		}
		types.put(quickChatPhrase, id);
		return quickChatPhrase;
	}

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "([IJIZ)Lclient!na;")
	public static JagString formatDynamicValue(@OriginalArg(0) int[] params, @OriginalArg(1) long value, @OriginalArg(2) int commandType) {
		if (decoder != null) {
			@Pc(17) JagString decoded = decoder.decode(commandType, params, value);
			if (decoded != null) {
				return decoded;
			}
		}
		return JagString.parseLong(value);
	}
}
