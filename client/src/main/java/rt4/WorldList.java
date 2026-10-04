package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.IOException;
import java.net.Socket;

public class WorldList {
	@OriginalMember(owner = "client!nd", name = "x", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_SETTINGS = JagString.parse(")4p=");
	@OriginalMember(owner = "client!ja", name = "s", descriptor = "Lclient!na;")
	public static final JagString HTTP_PROTOCOL = JagString.parse("http:)4)4");
	@OriginalMember(owner = "client!wk", name = "x", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_LANGUAGE = JagString.parse(")4l=");
	@OriginalMember(owner = "client!rc", name = "G", descriptor = "Lclient!na;")
	public static final JagString EMPTY_STRING = JagString.parse("");
	@OriginalMember(owner = "client!ob", name = "o", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_AFFILIATE = JagString.parse(")4a=");
	@OriginalMember(owner = "client!l", name = "d", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_JAVA_FLAG = JagString.parse(")4j");
	@OriginalMember(owner = "client!cg", name = "e", descriptor = "Lclient!na;")
	public static final JagString TRUE_STRING = JagString.parse("1");
	@OriginalMember(owner = "client!vd", name = "F", descriptor = "Lclient!na;")
	public static final JagString FALSE_STRING = JagString.parse("0");
	@OriginalMember(owner = "client!em", name = "u", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_JAVASCRIPT = JagString.parse(")1o");
	@OriginalMember(owner = "client!q", name = "a", descriptor = "Lclient!na;")
	public static final JagString URL_PARAM_ADVERT = JagString.parse(")1a2)1m");
	@OriginalMember(owner = "client!ch", name = "x", descriptor = "Lclient!na;")
	public static final JagString PORT_SEPARATOR = JagString.parse(":");
	@OriginalMember(owner = "client!ii", name = "e", descriptor = "Lclient!na;")
	public static final JagString NO_ACTIVITY = JagString.parse(")2");
	@OriginalMember(owner = "client!gi", name = "c", descriptor = "I")
	public static int step = 0;
	@OriginalMember(owner = "client!be", name = "kc", descriptor = "J")
	public static long openTime = 0L;
	@OriginalMember(owner = "client!sk", name = "gb", descriptor = "J")
	public static long closeTime = 0L;
	@OriginalMember(owner = "client!en", name = "A", descriptor = "Z")
	public static boolean loaded = false;
	@OriginalMember(owner = "client!jb", name = "y", descriptor = "I")
	public static int bufferLen = 0;
	@OriginalMember(owner = "client!si", name = "cb", descriptor = "[B")
	public static byte[] buffer;
	@OriginalMember(owner = "client!lb", name = "u", descriptor = "I")
	public static int bufferOff = 0;
	@OriginalMember(owner = "client!hm", name = "fb", descriptor = "[Lclient!ba;")
	public static World[] sorted = new World[0];
	@OriginalMember(owner = "client!ic", name = "n", descriptor = "I")
	public static int size;
	@OriginalMember(owner = "client!gf", name = "T", descriptor = "I")
	public static int checksum;
	@OriginalMember(owner = "client!qh", name = "e", descriptor = "[Lclient!ee;")
	public static WorldInfo[] countries;
	@OriginalMember(owner = "client!bi", name = "R", descriptor = "I")
	public static int minId;
	@OriginalMember(owner = "client!ni", name = "q", descriptor = "I")
	public static int maxId;
	@OriginalMember(owner = "client!pl", name = "a", descriptor = "[Lclient!ba;")
	public static World[] worlds;
	@OriginalMember(owner = "client!k", name = "t", descriptor = "I")
	public static int errors = 0;
	@OriginalMember(owner = "client!ea", name = "w", descriptor = "I")
	public static int worldPos = 1;

	@OriginalMember(owner = "client!ql", name = "b", descriptor = "(I)I")
	public static int fetch() {
		try {
			if (step == 0) {
				if (MonotonicClock.currentTimeMillis() - 5000L < closeTime) {
					return 0;
				}
				if (GlobalJsonConfig.instance != null) {
					client.worldListHostname = GlobalJsonConfig.instance.ip_management;
					client.worldListPort = GlobalJsonConfig.instance.wl_port;
				}
				Protocol.socketRequest = GameShell.signLink.openSocket(client.worldListHostname, client.worldListPort);
				openTime = MonotonicClock.currentTimeMillis();
				step = 1;
			}
			if (openTime + 30000L < MonotonicClock.currentTimeMillis()) {
				return close(1000);
			}
			@Pc(82) int available;
			@Pc(124) int response;
			if (step == 1) {
				if (Protocol.socketRequest.status == 2) {
					return close(1001);
				}
				if (Protocol.socketRequest.status != 1) {
					return -1;
				}
				Protocol.socket = new BufferedSocket((Socket) Protocol.socketRequest.result, GameShell.signLink);
				Protocol.outboundBuffer.offset = 0;
				Protocol.socketRequest = null;
				available = 0;
				if (loaded) {
					available = checksum;
				}
				Protocol.outboundBuffer.p1(255);
				Protocol.outboundBuffer.p4(available);
				Protocol.socket.write(Protocol.outboundBuffer.data, Protocol.outboundBuffer.offset);
				if (client.musicChannel != null) {
					client.musicChannel.pauseConsumptionCheck();
				}
				if (client.soundChannel != null) {
					client.soundChannel.pauseConsumptionCheck();
				}
				response = Protocol.socket.read();
				if (client.musicChannel != null) {
					client.musicChannel.pauseConsumptionCheck();
				}
				if (client.soundChannel != null) {
					client.soundChannel.pauseConsumptionCheck();
				}
				if (response != 0) {
					return close(response);
				}
				step = 2;
			}
			if (step == 2) {
				if (Protocol.socket.available() < 2) {
					return -1;
				}
				bufferLen = Protocol.socket.read();
				bufferLen <<= 0x8;
				bufferLen += Protocol.socket.read();
				step = 3;
				bufferOff = 0;
				buffer = new byte[bufferLen];
			}
			if (step != 3) {
				return -1;
			}
			available = Protocol.socket.available();
			if (available < 1) {
				return -1;
			}
			if (available > bufferLen - bufferOff) {
				available = bufferLen - bufferOff;
			}
			Protocol.socket.read(bufferOff, available, buffer);
			bufferOff += available;
			if (bufferOff < bufferLen) {
				return -1;
			} else if (decode(buffer)) {
				sorted = new World[size];
				response = 0;
				for (@Pc(240) int worldId = minId; worldId <= maxId; worldId++) {
					@Pc(247) World world = ScriptRunner.getWorld(worldId);
					if (world != null) {
						sorted[response++] = world;
					}
				}
				Protocol.socket.close();
				Protocol.socket = null;
				errors = 0;
				step = 0;
				buffer = null;
				closeTime = MonotonicClock.currentTimeMillis();
				return 0;
			} else {
				return close(1002);
			}
		} catch (@Pc(277) IOException ignored) {
			return close(1003);
		}
	}

	@OriginalMember(owner = "client!an", name = "a", descriptor = "(BI)I")
	public static int close(@OriginalArg(1) int errorCode) {
		if (Protocol.socket != null) {
			Protocol.socket.close();
			Protocol.socket = null;
		}
		errors++;
		if (errors > 4) {
			step = 0;
			errors = 0;
			return errorCode;
		}
		step = 0;
		if (client.worldListPort == client.worldListDefaultPort) {
			client.worldListPort = client.worldListAlternatePort;
		} else {
			client.worldListPort = client.worldListDefaultPort;
		}
		return -1;
	}

	@OriginalMember(owner = "client!hi", name = "a", descriptor = "(Lclient!wa;I)V")
	public static void decodeWorlds(@OriginalArg(0) Buffer buffer) {
		@Pc(9) int countryCount = buffer.gsmarts();
		countries = new WorldInfo[countryCount];
		@Pc(14) int i;
		for (i = 0; i < countryCount; i++) {
			countries[i] = new WorldInfo();
			countries[i].flag = buffer.gsmarts();
			countries[i].name = buffer.gjstr2();
		}
		minId = buffer.gsmarts();
		maxId = buffer.gsmarts();
		size = buffer.gsmarts();
		worlds = new World[maxId + 1 - minId];
		for (i = 0; i < size; i++) {
			@Pc(77) int offset = buffer.gsmarts();
			@Pc(85) World world = worlds[offset] = new World();
			world.country = buffer.g1();
			world.flags = buffer.g4();
			world.id = offset + minId;
			world.activity = buffer.gjstr2();
			world.hostname = buffer.gjstr2();
		}
		checksum = buffer.g4();
		loaded = true;
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(I[B)Z")
	public static boolean decode(@OriginalArg(1) byte[] data) {
		@Pc(13) Buffer buf = new Buffer(data);
		@Pc(17) int version = buf.g1();
		if (version != 1) {
			return false;
		}
		@Pc(33) boolean hasWorlds = buf.g1() == 1;
		if (hasWorlds) {
			decodeWorlds(buf);
		}
		decodePlayers(buf);
		return true;
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(Lclient!wa;I)V")
	public static void decodePlayers(@OriginalArg(0) Buffer buf) {
		for (@Pc(7) int i = 0; i < size; i++) {
			@Pc(18) int index = buf.gsmarts();
			@Pc(22) int players = buf.g2();
			if (players == 65535) {
				players = -1;
			}
			if (worlds[index] != null) {
				worlds[index].players = players;
			}
		}
	}

	@OriginalMember(owner = "client!h", name = "a", descriptor = "(I)Lclient!ba;")
	public static World getNextWorld() {
		return sorted.length > worldPos ? sorted[worldPos++] : null;
	}

	@OriginalMember(owner = "client!sh", name = "a", descriptor = "(IZBIZ)V")
	public static void sortWorldList(@OriginalArg(0) int primaryField, @OriginalArg(1) boolean primaryReverse, @OriginalArg(3) int secondaryField, @OriginalArg(4) boolean secondaryReverse) {
		quicksort(primaryField, secondaryField, sorted.length - 1, secondaryReverse, 0, primaryReverse);
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(IIIZIZZ)V")
	public static void quicksort(@OriginalArg(0) int primaryField, @OriginalArg(1) int secondaryField, @OriginalArg(2) int high, @OriginalArg(3) boolean secondaryReverse, @OriginalArg(4) int low, @OriginalArg(5) boolean primaryReverse) {
		if (high <= low) {
			return;
		}
		@Pc(13) int mid = (high + low) / 2;
		@Pc(15) int storeIndex = low;
		@Pc(19) World pivot = sorted[mid];
		sorted[mid] = sorted[high];
		sorted[high] = pivot;
		for (@Pc(31) int i = low; i < high; i++) {
			if (compareWorlds(pivot, sorted[i], primaryField, secondaryField, secondaryReverse, primaryReverse) <= 0) {
				@Pc(53) World temp = sorted[i];
				sorted[i] = sorted[storeIndex];
				sorted[storeIndex++] = temp;
			}
		}
		sorted[high] = sorted[storeIndex];
		sorted[storeIndex] = pivot;
		quicksort(primaryField, secondaryField, storeIndex - 1, secondaryReverse, low, primaryReverse);
		quicksort(primaryField, secondaryField, high, secondaryReverse, storeIndex + 1, primaryReverse);
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(B)Lclient!ba;")
	public static World getFirstWorld() {
		worldPos = 0;
		return getNextWorld();
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(IB)Z")
	public static boolean hopWorld(@OriginalArg(0) int worldId) {
		@Pc(3) World world = ScriptRunner.getWorld(worldId);
		if (world == null) {
			return false;
		} else if (SignLink.clientMode == 1 || SignLink.clientMode == 2 || client.modeWhere == 2) {
			@Pc(31) byte[] hostnameBytes = world.hostname.toByteArray();
			client.hostname = new String(hostnameBytes, 0, hostnameBytes.length);
			Player.worldId = world.id;
			if (client.modeWhere != 0) {
				client.defaultPort = Player.worldId + 43594; // 40000;
				client.port = client.defaultPort;
				client.alternatePort = Player.worldId + 43594; // 50000;
			}
			return true;
		} else {
			@Pc(62) JagString portStr = EMPTY_STRING;
			if (client.modeWhere != 0) {
				portStr = JagString.concatenate(new JagString[]{PORT_SEPARATOR, JagString.parseInt(world.id + 7000)});
			}
			@Pc(89) JagString settingsStr = EMPTY_STRING;
			if (client.settings != null) {
				settingsStr = JagString.concatenate(new JagString[]{URL_PARAM_SETTINGS, client.settings});
			}
			@Pc(182) JagString url = JagString.concatenate(new JagString[]{HTTP_PROTOCOL, world.hostname, portStr, URL_PARAM_LANGUAGE, JagString.parseInt(client.language), URL_PARAM_AFFILIATE, JagString.parseInt(client.affiliate), settingsStr, URL_PARAM_JAVA_FLAG, client.objectTag ? TRUE_STRING : FALSE_STRING, URL_PARAM_JAVASCRIPT, client.javaScript ? TRUE_STRING : FALSE_STRING, URL_PARAM_ADVERT, client.advertSuppressed ? TRUE_STRING : FALSE_STRING});
			try {
				client.instance.getAppletContext().showDocument(url.toUrl(), "_self");
				return true;
			} catch (@Pc(191) Exception ex) {
				return false;
			}
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!ba;Lclient!ba;IIIZZ)I")
	public static int compareWorlds(@OriginalArg(0) World a, @OriginalArg(1) World b, @OriginalArg(3) int primaryField, @OriginalArg(4) int secondaryField, @OriginalArg(5) boolean secondaryReverse, @OriginalArg(6) boolean primaryReverse) {
		@Pc(8) int result = compareWorldField(b, secondaryField, a, primaryReverse);
		if (result != 0) {
			return primaryReverse ? -result : result;
		} else if (primaryField == -1) {
			return 0;
		} else {
			@Pc(42) int fallback = compareWorldField(b, primaryField, a, secondaryReverse);
			return secondaryReverse ? -fallback : fallback;
		}
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(Lclient!ba;IILclient!ba;Z)I")
	public static int compareWorldField(@OriginalArg(0) World a, @OriginalArg(1) int field, @OriginalArg(3) World b, @OriginalArg(4) boolean reverse) {
		if (field == 1) {
			@Pc(11) int playersA = a.players;
			@Pc(14) int playersB = b.players;
			if (!reverse) {
				if (playersB == -1) {
					playersB = 2001;
				}
				if (playersA == -1) {
					playersA = 2001;
				}
			}
			return playersA - playersB;
		} else if (field == 2) {
			return a.getWorldInfo().name.compare(b.getWorldInfo().name);
		} else if (field == 3) {
			if (a.activity.strEquals(NO_ACTIVITY)) {
				if (b.activity.strEquals(NO_ACTIVITY)) {
					return 0;
				} else if (reverse) {
					return -1;
				} else {
					return 1;
				}
			} else if (b.activity.strEquals(NO_ACTIVITY)) {
				return reverse ? 1 : -1;
			} else {
				return a.activity.compare(b.activity);
			}
		} else if (field == 4) {
			return a.isLootShare() ? (b.isLootShare() ? 0 : 1) : b.isLootShare() ? -1 : 0;
		} else if (field == 5) {
			return a.isQuickChat() ? (b.isQuickChat() ? 0 : 1) : (b.isQuickChat() ? -1 : 0);
		} else if (field == 6) {
			return a.isPvp() ? (b.isPvp() ? 0 : 1) : (b.isPvp() ? -1 : 0);
		} else if (field == 7) {
			return a.isMembers() ? (b.isMembers() ? 0 : 1) : (b.isMembers() ? -1 : 0);
		} else {
			return a.id - b.id;
		}
	}
}
