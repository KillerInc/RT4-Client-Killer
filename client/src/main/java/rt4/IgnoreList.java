package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class IgnoreList {
	@OriginalMember(owner = "client!pf", name = "h", descriptor = "[J")
	public static final long[] encodedUsernames = new long[100];
	@OriginalMember(owner = "client!pi", name = "V", descriptor = "[Lclient!na;")
	public static final JagString[] usernames = new JagString[100];
	@OriginalMember(owner = "client!cl", name = "Z", descriptor = "I")
	public static int size = 0;

	@OriginalMember(owner = "client!te", name = "b", descriptor = "(Lclient!na;I)Z")
	public static boolean contains(@OriginalArg(0) JagString username) {
		if (username == null) {
			return false;
		}
		for (@Pc(11) int i = 0; i < size; i++) {
			if (username.equalsIgnoreCase(usernames[i])) {
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!la", name = "a", descriptor = "(IJ)V")
	public static void add(@OriginalArg(1) long encodedUsername) {
		if (encodedUsername == 0L) {
			return;
		}
		if (size >= 100) {
			Chat.add(JagString.EMPTY, 0, LocalizedText.IGNORELISTFULL);
			return;
		}
		@Pc(34) JagString name = Base37.decode37(encodedUsername).toTitleCase();
		@Pc(36) int i;
		for (i = 0; i < size; i++) {
			if (encodedUsernames[i] == encodedUsername) {
				Chat.add(JagString.EMPTY, 0, JagString.concatenate(new JagString[]{name, LocalizedText.IGNORELISTDUPE}));
				return;
			}
		}
		for (i = 0; i < FriendsList.size; i++) {
			if (FriendsList.encodedUsernames[i] == encodedUsername) {
				Chat.add(JagString.EMPTY, 0, JagString.concatenate(new JagString[]{LocalizedText.REMOVESOCIAL2, name, LocalizedText.REMOVEFRIEND}));
				return;
			}
		}
		if (name.strEquals(PlayerList.self.username)) {
			Chat.add(JagString.EMPTY, 0, LocalizedText.IGNORECANTADDSELF);
			return;
		}
		encodedUsernames[size] = encodedUsername;
		usernames[size++] = Base37.decode37(encodedUsername);
		FriendsList.transmitAt = InterfaceList.transmitTimer;
		Protocol.outboundBuffer.p1isaac(ClientProt.IGNORELIST_ADD);
		Protocol.outboundBuffer.p8(encodedUsername);
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(JI)V")
	public static void remove(@OriginalArg(0) long encodedUsername) {
		if (encodedUsername == 0L) {
			return;
		}
		for (@Pc(12) int i = 0; i < size; i++) {
			if (encodedUsernames[i] == encodedUsername) {
				size--;
				for (@Pc(36) int j = i; j < size; j++) {
					encodedUsernames[j] = encodedUsernames[j + 1];
					usernames[j] = usernames[j + 1];
				}
				FriendsList.transmitAt = InterfaceList.transmitTimer;
				Protocol.outboundBuffer.p1isaac(ClientProt.IGNORELIST_DEL);
				Protocol.outboundBuffer.p8(encodedUsername);
				break;
			}
		}
	}
}
