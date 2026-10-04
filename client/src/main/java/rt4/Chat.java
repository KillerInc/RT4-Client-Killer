package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class Chat {
	@OriginalMember(owner = "client!ca", name = "eb", descriptor = "[I")
	public static final int[] types = new int[100];
	@OriginalMember(owner = "client!fb", name = "l", descriptor = "[Lclient!na;")
	public static final JagString[] clans = new JagString[100];
	@OriginalMember(owner = "client!sj", name = "q", descriptor = "[Lclient!na;")
	public static final JagString[] messages = new JagString[100];
	@OriginalMember(owner = "client!th", name = "l", descriptor = "[I")
	public static final int[] phraseIds = new int[100];
	@OriginalMember(owner = "client!mc", name = "Y", descriptor = "[Lclient!na;")
	public static final JagString[] names = new JagString[100];
	@OriginalMember(owner = "client!t", name = "w", descriptor = "[J")
	public static final long[] recentMessages = new long[100];
	@OriginalMember(owner = "client!dm", name = "u", descriptor = "I")
	public static int transmitAt = 0;
	@OriginalMember(owner = "client!f", name = "Z", descriptor = "I")
	public static int size = 0;
	@OriginalMember(owner = "client!gk", name = "i", descriptor = "I")
	public static int tradeFilter = 0;
	@OriginalMember(owner = "client!dm", name = "m", descriptor = "I")
	public static int privateFilter = 0;
	@OriginalMember(owner = "client!ej", name = "U", descriptor = "I")
	public static int publicFilter = 0;
	@OriginalMember(owner = "client!ug", name = "e", descriptor = "I")
	public static int messageCounter = 0;

	@OriginalMember(owner = "client!md", name = "a", descriptor = "(IILclient!na;Lclient!na;BLclient!na;)V")
	public static void add(@OriginalArg(0) int phraseId, @OriginalArg(1) int type, @OriginalArg(2) JagString message, @OriginalArg(3) JagString clan, @OriginalArg(5) JagString name) {
		for (@Pc(14) int i = 99; i > 0; i--) {
			types[i] = types[i - 1];
			names[i] = names[i - 1];
			messages[i] = messages[i - 1];
			clans[i] = clans[i - 1];
			phraseIds[i] = phraseIds[i - 1];
		}
		size++;
		types[0] = type;
		names[0] = name;
		transmitAt = InterfaceList.transmitTimer;
		phraseIds[0] = phraseId;
		messages[0] = message;
		clans[0] = clan;
	}

	@OriginalMember(owner = "client!i", name = "a", descriptor = "(Lclient!na;ILclient!na;I)V")
	public static void add(@OriginalArg(0) JagString name, @OriginalArg(1) int type, @OriginalArg(2) JagString message) {
		add(-1, type, message, null, name);
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(ILclient!na;Lclient!na;Lclient!na;I)V")
	public static void addClanChannelMessage(@OriginalArg(1) JagString message, @OriginalArg(2) JagString name, @OriginalArg(3) JagString clan) {
		add(-1, 9, message, clan, name);
	}
}
