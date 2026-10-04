package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.IOException;

public class ClientProt {

	public static final int CLIENT_CHEAT = 44;
	public static final int CLAN_JOINCHAT_LEAVECHAT = 104;
	public static final int CLAN_KICKUSER = 162; // or CLANCHANNEL_KICKUSER
	public static final int FRIENDLIST_ADD = 120;
	public static final int FRIENDLIST_DEL = 57;
	public static final int FRIEND_SETRANK = 188;
	public static final int IGNORELIST_ADD = 34;
	public static final int IGNORELIST_DEL = 213;
	public static final int WINDOW_STATUS = 243; // assumed
	public static final int TRANSMITVAR_VERIFYID = 177;
	public static final int EVENT_MOUSE_MOVE = 123;
	public static final int EVENT_MOUSE_CLICK = 75;
	public static final int EVENT_CAMERA_POSITION = 21;
	public static final int EVENT_APPLET_FOCUS = 22;
	public static final int RESUME_P_COUNTDIALOG = 23;
	public static final int RESUME_P_NAMEDIALOG = 244;
	public static final int RESUME_P_STRINGDIALOG = 65;
	public static final int SET_CHATFILTERSETTINGS = 157;
	public static final int BUG_REPORT = 99;
	public static final int SOUND_SONGEND = 137;
	public static final int MOVE_GAMECLICK = 215;
	public static final int MOVE_MINIMAPCLICK = 39;
	public static final int CLOSE_MODAL = 184;
	public static final int NO_TIMEOUT = 93; // assumed

	@OriginalMember(owner = "client!vg", name = "a", descriptor = "(Lclient!na;IIBI)V")
	public static void sendButtonClick(@OriginalArg(0) JagString opBase, @OriginalArg(1) int childId, @OriginalArg(2) int op, @OriginalArg(4) int componentId) {
		@Pc(8) Component component = InterfaceList.getComponent(componentId, childId);
		if (component == null) {
			return;
		}
		if (component.onOptionClick != null) {
			@Pc(19) HookRequest hookRequest = new HookRequest();
			hookRequest.arguments = component.onOptionClick;
			hookRequest.source = component;
			hookRequest.opBase = opBase;
			hookRequest.op = op;
			ScriptRunner.run(hookRequest);
		}
		@Pc(37) boolean sendToServer = true;
		if (component.clientCode > 0) {
			sendToServer = MiniMenu.handleSpecialButtonAction(component);
		}
		if (!sendToServer || !InterfaceList.getServerActiveProperties(component).isButtonEnabled(op - 1)) {
			return;
		}
		if (op == 1) {
			Protocol.outboundBuffer.p1isaac(155);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 2) {
			Protocol.outboundBuffer.p1isaac(196);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 3) {
			Protocol.outboundBuffer.p1isaac(124);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 4) {
			Protocol.outboundBuffer.p1isaac(199);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 5) {
			Protocol.outboundBuffer.p1isaac(234);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 6) {
			Protocol.outboundBuffer.p1isaac(168);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 7) {
			Protocol.outboundBuffer.p1isaac(166);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 8) {
			Protocol.outboundBuffer.p1isaac(64);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 9) {
			Protocol.outboundBuffer.p1isaac(53);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
		if (op == 10) {
			Protocol.outboundBuffer.p1isaac(9);
			Protocol.outboundBuffer.p4(componentId);
			Protocol.outboundBuffer.p2(childId);
		}
	}

	@OriginalMember(owner = "client!pi", name = "c", descriptor = "(III)V")
	public static void sendMovePacket(@OriginalArg(1) int pathLength, @OriginalArg(2) int moveType) {
		@Pc(13) int clampedLength = pathLength;
		if (pathLength > 25) {
			clampedLength = 25;
		}
		pathLength--;
		@Pc(23) int startX = PathFinder.queueX[pathLength];
		@Pc(27) int startY = PathFinder.queueY[pathLength];
		if (moveType == 0) {
			Protocol.outboundBuffer.p1isaac(ClientProt.MOVE_GAMECLICK);
			Protocol.outboundBuffer.p1(clampedLength + clampedLength + 3);
		}
		if (moveType == 1) {
			Protocol.outboundBuffer.p1isaac(ClientProt.MOVE_MINIMAPCLICK);
			Protocol.outboundBuffer.p1(clampedLength + clampedLength + 3 + 14);
		}
		if (moveType == 2) {
			Protocol.outboundBuffer.p1isaac(77);
			Protocol.outboundBuffer.p1(clampedLength + clampedLength + 3);
		}
		Protocol.outboundBuffer.p1add(Keyboard.pressedKeys[Keyboard.KEY_CTRL] ? 1 : 0);
		Protocol.outboundBuffer.p2(Camera.originX + startX);
		Protocol.outboundBuffer.p2add(Camera.originY + startY);
		LoginManager.mapFlagY = PathFinder.queueY[0];
		LoginManager.mapFlagX = PathFinder.queueX[0];
		for (@Pc(126) int i = 1; i < clampedLength; i++) {
			pathLength--;
			Protocol.outboundBuffer.p1add(PathFinder.queueX[pathLength] - startX);
			Protocol.outboundBuffer.p1sub(PathFinder.queueY[pathLength] - startY);
		}
	}

	@OriginalMember(owner = "client!mc", name = "f", descriptor = "(B)V")
	public static void closeWidget() {
		Protocol.outboundBuffer.p1isaac(ClientProt.CLOSE_MODAL);
		for (@Pc(18) ComponentPointer pointer = (ComponentPointer) InterfaceList.openInterfaces.head(); pointer != null; pointer = (ComponentPointer) InterfaceList.openInterfaces.next()) {
			if (pointer.type == 0) {
				InterfaceList.closeInterface(true, pointer);
			}
		}
		if (Cs1ScriptRunner.pleaseWaitComponent != null) {
			InterfaceList.redraw(Cs1ScriptRunner.pleaseWaitComponent);
			Cs1ScriptRunner.pleaseWaitComponent = null;
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(IILclient!na;)V")
	public static void clickPlayerOption(@OriginalArg(0) int option, @OriginalArg(2) JagString playerName) {
		@Pc(7) JagString formattedName = playerName.toBase37Name().toTitleCase();
		@Pc(13) boolean found = false;
		for (@Pc(15) int i = 0; i < PlayerList.size; i++) {
			@Pc(28) Player player = PlayerList.players[PlayerList.ids[i]];
			if (player != null && player.username != null && player.username.equalsIgnoreCase(formattedName)) {
				found = true;
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				if (option == 1) {
					Protocol.outboundBuffer.p1isaac(68);
					Protocol.outboundBuffer.ip2add(PlayerList.ids[i]);
				} else if (option == 4) {
					Protocol.outboundBuffer.p1isaac(180);
					Protocol.outboundBuffer.ip2add(PlayerList.ids[i]);
				} else if (option == 5) {
					Protocol.outboundBuffer.p1isaac(4);
					Protocol.outboundBuffer.ip2(PlayerList.ids[i]);
				} else if (option == 6) {
					Protocol.outboundBuffer.p1isaac(133);
					Protocol.outboundBuffer.ip2(PlayerList.ids[i]);
				} else if (option == 7) {
					Protocol.outboundBuffer.p1isaac(114);
					Protocol.outboundBuffer.ip2add(PlayerList.ids[i]);
				}
				break;
			}
		}
		if (!found) {
			Chat.add(JagString.EMPTY, 0, JagString.concatenate(new JagString[]{LocalizedText.UNABLETOFIND, formattedName}));
		}
	}

	@OriginalMember(owner = "client!ej", name = "i", descriptor = "(I)V")
	public static void sendWindowDetails() {
		Protocol.outboundBuffer.p1isaac(ClientProt.WINDOW_STATUS);
		Protocol.outboundBuffer.p1(DisplayMode.getWindowMode());
		Protocol.outboundBuffer.p2(GameShell.canvasWidth);
		Protocol.outboundBuffer.p2(GameShell.canvasHeight);
		Protocol.outboundBuffer.p1(Preferences.antiAliasingMode);
	}

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(BZ)V")
	public static void ping(@OriginalArg(1) boolean force) {
		client.audioLoop();
		if (client.gameState != 30 && client.gameState != 25) {
			return;
		}
		Protocol.ticksSinceWrite++;
		if (Protocol.ticksSinceWrite < 50 && !force) {
			return;
		}
		Protocol.ticksSinceWrite = 0;
		if (!LoginManager.pingFailed && Protocol.socket != null) {
			Protocol.outboundBuffer.p1isaac(ClientProt.NO_TIMEOUT);
			try {
				Protocol.socket.write(Protocol.outboundBuffer.data, Protocol.outboundBuffer.offset);
				Protocol.outboundBuffer.offset = 0;
			} catch (@Pc(53) IOException ex) {
				LoginManager.pingFailed = true;
			}
		}
		client.audioLoop();
	}

}
