package rt4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.GLReadBufferUtil;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

@OriginalClass("client!client")
public final class client extends GameShell {

	@OriginalMember(owner = "client!dk", name = "j", descriptor = "[Lclient!en;")
	public static final BufferedFile[] cacheIndexes = new BufferedFile[28];
	@OriginalMember(owner = "client!wa", name = "Eb", descriptor = "[Lclient!bg;")
	public static final Js5CachedResourceProvider[] js5Providers = new Js5CachedResourceProvider[28];
	@OriginalMember(owner = "client!d", name = "S", descriptor = "Ljava/util/Random;")
	public static final Random aRandom1 = new Random();
	@OriginalMember(owner = "client!nh", name = "fb", descriptor = "[I")
	public static final int[] JS5_ARCHIVE_WEIGHTS = new int[]{4, 4, 1, 2, 6, 4, 2, 49, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	@OriginalMember(owner = "client!si", name = "gb", descriptor = "Lclient!na;")
	public static final JagString LOADING_PERCENT_PREFIX = JagString.parse("<br>(X");
	@OriginalMember(owner = "client!sg", name = "e", descriptor = "Lclient!na;")
	public static final JagString SETTINGS = JagString.parse("settings");
	@OriginalMember(owner = "client!km", name = "Mc", descriptor = "Lclient!na;")
	public static final JagString LOGINSCREEN = JagString.parse("loginscreen");
	@OriginalMember(owner = "client!qk", name = "a", descriptor = "Lclient!na;")
	public static final JagString ZERO_PERCENT = JagString.parse("0(U");
	@OriginalMember(owner = "client!uh", name = "Y", descriptor = "Lclient!na;")
	public static final JagString HUFFMAN_GROUP = JagString.parse("huffman");
	@OriginalMember(owner = "client!nb", name = "n", descriptor = "Lclient!na;")
	public static final JagString DETAILS = JagString.parse("details");
	@OriginalMember(owner = "client!qk", name = "b", descriptor = "Lclient!na;")
	public static final JagString BROWSER_REFRESH_CMD = JagString.parse("tbrefresh");
	@OriginalMember(owner = "client!al", name = "r", descriptor = "Lclient!na;")
	public static final JagString BROWSER_SHOW_AD_CMD = JagString.parse("showVideoAd");
	@OriginalMember(owner = "client!a", name = "e", descriptor = "Lclient!na;")
	public static JagString TITLE_SONG = JagString.parse("scape main");
	@OriginalMember(owner = "client!jm", name = "A", descriptor = "Lclient!na;")
	static final JagString DEFAULT_STATUS_TEXT = JagString.parse("");
	@OriginalMember(owner = "client!jm", name = "z", descriptor = "Lclient!na;")
	public static JagString mainLoadSecondaryText = DEFAULT_STATUS_TEXT;
	@OriginalMember(owner = "client!sg", name = "k", descriptor = "Lclient!ve;")
	public static Js5 js5Archive23;
	@OriginalMember(owner = "client!pb", name = "Q", descriptor = "I")
	public static int worldListId = 1;
	@OriginalMember(owner = "client!gj", name = "b", descriptor = "I")
	public static int modeWhere = 0;
	@OriginalMember(owner = "client!gg", name = "U", descriptor = "I")
	public static int modeWhat = 0;
	@OriginalMember(owner = "client!ud", name = "S", descriptor = "Z")
	public static boolean advertSuppressed = false;
	@OriginalMember(owner = "client!lb", name = "v", descriptor = "I")
	public static int language = 0;
	@OriginalMember(owner = "client!t", name = "x", descriptor = "Z")
	public static boolean javaScript = false;
	@OriginalMember(owner = "client!lk", name = "U", descriptor = "Z")
	public static boolean objectTag = false;
	@OriginalMember(owner = "client!vk", name = "n", descriptor = "I")
	public static int game = 0;
	@OriginalMember(owner = "client!wk", name = "w", descriptor = "I")
	public static int country;
	@OriginalMember(owner = "client!od", name = "n", descriptor = "Z")
	public static boolean haveIe6 = false;
	@OriginalMember(owner = "client!qi", name = "r", descriptor = "I")
	public static int affiliate = 0;
	@OriginalMember(owner = "client!dk", name = "h", descriptor = "Lclient!na;")
	public static JagString settings = null;
	@OriginalMember(owner = "client!rh", name = "j", descriptor = "Lclient!client;")
	public static client instance;
	@OriginalMember(owner = "client!ba", name = "D", descriptor = "Lclient!vh;")
	public static AudioChannel musicChannel;
	@OriginalMember(owner = "client!fk", name = "q", descriptor = "Lclient!uc;")
	public static MouseWheel mouseWheel;
	@OriginalMember(owner = "client!lh", name = "s", descriptor = "Lclient!vh;")
	public static AudioChannel soundChannel;
	@OriginalMember(owner = "client!id", name = "l", descriptor = "Lclient!jb;")
	public static Js5NetQueue js5NetQueue;
	@OriginalMember(owner = "client!gm", name = "T", descriptor = "Lclient!k;")
	public static Js5CacheQueue js5CacheQueue;
	@OriginalMember(owner = "client!nj", name = "f", descriptor = "Lclient!en;")
	public static BufferedFile cacheData;
	@OriginalMember(owner = "client!pf", name = "f", descriptor = "Lclient!en;")
	public static BufferedFile cacheMasterIndex;
	@OriginalMember(owner = "client!jg", name = "c", descriptor = "Lclient!en;")
	public static BufferedFile uid;
	@OriginalMember(owner = "client!tl", name = "d", descriptor = "I")
	public static int gameState = 0;
	@OriginalMember(owner = "client!id", name = "f", descriptor = "Z")
	public static boolean clean = false;
	@OriginalMember(owner = "client!bl", name = "P", descriptor = "I")
	public static int js5ConnectDelay = 0;
	@OriginalMember(owner = "client!wc", name = "c", descriptor = "I")
	public static int defaultPort;
	@OriginalMember(owner = "client!ee", name = "g", descriptor = "I")
	public static int alternatePort;
	@OriginalMember(owner = "client!ra", name = "s", descriptor = "I")
	public static int port;
	@OriginalMember(owner = "client!ud", name = "K", descriptor = "I")
	public static int worldListDefaultPort;
	@OriginalMember(owner = "client!r", name = "f", descriptor = "I")
	public static int worldListPort;
	@OriginalMember(owner = "client!v", name = "f", descriptor = "Lclient!al;")
	public static Js5MasterIndex js5MasterIndex;
	@OriginalMember(owner = "client!re", name = "B", descriptor = "Lclient!ve;")
	public static Js5 js5Archive0;
	@OriginalMember(owner = "client!ud", name = "J", descriptor = "Lclient!ve;")
	public static Js5 js5Archive1;
	@OriginalMember(owner = "client!wf", name = "g", descriptor = "Lclient!ve;")
	public static Js5 js5Archive2;
	@OriginalMember(owner = "client!dc", name = "z", descriptor = "Lclient!ve;")
	public static Js5 js5Archive3;
	@OriginalMember(owner = "client!uc", name = "c", descriptor = "Lclient!ve;")
	public static Js5 js5Archive4;
	@OriginalMember(owner = "client!ca", name = "Z", descriptor = "Lclient!ve;")
	public static Js5 js5Archive5;
	@OriginalMember(owner = "client!kc", name = "w", descriptor = "Lclient!ve;")
	public static Js5 js5Archive6;
	@OriginalMember(owner = "client!vl", name = "g", descriptor = "Lclient!ve;")
	public static Js5 js5Archive7;
	@OriginalMember(owner = "client!ra", name = "K", descriptor = "Lclient!ve;")
	public static Js5 js5Archive8;
	@OriginalMember(owner = "client!pk", name = "Z", descriptor = "Lclient!ve;")
	public static Js5 js5Archive9;
	@OriginalMember(owner = "client!ol", name = "U", descriptor = "Lclient!ve;")
	public static Js5 js5Archive10;
	@OriginalMember(owner = "client!rg", name = "z", descriptor = "Lclient!ve;")
	public static Js5 js5Archive11;
	@OriginalMember(owner = "client!bf", name = "s", descriptor = "Lclient!ve;")
	public static Js5 js5Archive12;
	@OriginalMember(owner = "client!ve", name = "l", descriptor = "Lclient!ve;")
	public static Js5 js5Archive13;
	@OriginalMember(owner = "client!kl", name = "r", descriptor = "Lclient!ve;")
	public static Js5 js5Archive14;
	@OriginalMember(owner = "client!km", name = "Oc", descriptor = "Lclient!ve;")
	public static Js5 js5Archive15;
	@OriginalMember(owner = "client!wl", name = "s", descriptor = "Lclient!ve;")
	public static Js5 js5Archive16;
	@OriginalMember(owner = "client!km", name = "Nc", descriptor = "Lclient!ve;")
	public static Js5 js5Archive17;
	@OriginalMember(owner = "client!nj", name = "l", descriptor = "Lclient!ve;")
	public static Js5 js5Archive18;
	@OriginalMember(owner = "client!ni", name = "k", descriptor = "Lclient!ve;")
	public static Js5 js5Archive19;
	@OriginalMember(owner = "client!ui", name = "cb", descriptor = "Lclient!ve;")
	public static Js5 js5Archive20;
	@OriginalMember(owner = "client!jh", name = "p", descriptor = "Lclient!ve;")
	public static Js5 js5Archive21;
	@OriginalMember(owner = "client!mf", name = "W", descriptor = "Lclient!ve;")
	public static Js5 js5Archive22;
	@OriginalMember(owner = "client!uj", name = "J", descriptor = "Lclient!ve;")
	public static Js5 js5Archive24;
	@OriginalMember(owner = "client!cd", name = "B", descriptor = "Lclient!ve;")
	public static Js5 js5Archive25;
	@OriginalMember(owner = "client!nd", name = "t", descriptor = "Lclient!ve;")
	public static Js5 js5Archive26;
	@OriginalMember(owner = "client!sf", name = "b", descriptor = "Lclient!ve;")
	public static Js5 js5Archive27;
	@OriginalMember(owner = "client!qc", name = "P", descriptor = "I")
	public static int mainLoadPercentage = 10;
	@OriginalMember(owner = "client!nc", name = "j", descriptor = "I")
	public static int mainLoadState = 0;
	@OriginalMember(owner = "client!li", name = "l", descriptor = "Lclient!ge;")
	public static Cache masterCache;
	@OriginalMember(owner = "client!li", name = "v", descriptor = "Lclient!va;")
	public static MidiPcmStream musicStream;
	@OriginalMember(owner = "client!qi", name = "C", descriptor = "Lclient!ei;")
	public static MixerPcmStream soundStream;
	@OriginalMember(owner = "client!ef", name = "p", descriptor = "Lclient!vj;")
	public static PcmResampler resampler;
	@OriginalMember(owner = "client!t", name = "F", descriptor = "I")
	public static int js5PrevErrors = 0;
	@OriginalMember(owner = "client!ld", name = "k", descriptor = "Ljava/lang/String;")
	public static String worldListHostname;
	@OriginalMember(owner = "client!hi", name = "g", descriptor = "I")
	public static int worldListAlternatePort;
	@OriginalMember(owner = "client!em", name = "v", descriptor = "Ljava/lang/String;")
	public static String hostname;
	@OriginalMember(owner = "client!vc", name = "db", descriptor = "[S")
	public static short[] scriptRecolorPalette;
	@OriginalMember(owner = "client!f", name = "T", descriptor = "[S")
	public static short[] locRecolorPalette = new short[256];
	@OriginalMember(owner = "client!sm", name = "l", descriptor = "[S")
	public static short[] npcRecolorPalette = new short[256];
	@OriginalMember(owner = "client!vc", name = "bb", descriptor = "[S")
	public static short[] objRecolorPalette = new short[256];
	@OriginalMember(owner = "client!cm", name = "f", descriptor = "Lsignlink!im;")
	public static PrivilegedRequest js5SocketRequest;
	@OriginalMember(owner = "client!qk", name = "g", descriptor = "Lclient!ma;")
	public static BufferedSocket js5Socket;
	@OriginalMember(owner = "client!ac", name = "c", descriptor = "I")
	public static int js5ConnectState = 0;
	@OriginalMember(owner = "client!rj", name = "Y", descriptor = "J")
	public static long js5ConnectTime;
	@OriginalMember(owner = "client!nm", name = "Y", descriptor = "J")
	public static long firstGc = 0L;
	@OriginalMember(owner = "client!mj", name = "A", descriptor = "J")
	public static long prevGc = 0L;
	@OriginalMember(owner = "client!gj", name = "d", descriptor = "I")
	public static int loop = 0;
	@OriginalMember(owner = "client!wj", name = "e", descriptor = "Lclient!na;")
	public static JagString mainLoadPrimaryText = null;
	@OriginalMember(owner = "client!sj", name = "p", descriptor = "I")
	public static int peakMapFilesMissing = 1;
	@OriginalMember(owner = "client!cn", name = "B", descriptor = "I")
	public static int peakLocModelsMissing = 1;
	@OriginalMember(owner = "client!ah", name = "t", descriptor = "I")
	public static int startupClientMode;

	@OriginalMember(owner = "client!client", name = "main", descriptor = "([Ljava/lang/String;)V")
	public static void main(@OriginalArg(0) String[] args) {
    try {
      String configPath = GlobalConfig.EXTENDED_CONFIG_PATH;
      boolean helpRequested = false;

      for (int i = 0; i < args.length; i++) {
        if ("--config".equals(args[i]) && i + 1 < args.length) {
          configPath = args[i + 1];
          i++; // Skip next argument since it's the config file path
        } else if ("--help".equals(args[i])) {
          helpRequested = true;
        }
      }

      if (helpRequested) {
        System.out.println("Usage: java path-to-jar.jra [--config <path>] [--help]");
        System.out.println("Custom Options:");
        System.out.println("  --config <path>  Path to the configuration file.");
        System.out.println("  --help               Display this help message.");
        System.out.println("\nDefault Arguments:");
        System.out.println("  worldListId: Identifier for the world list, usually an integer.");
        System.out.println("  modeWhat: Operational mode, where 'live' is normal operation, 'rc' for release candidate, and 'wip' for work-in-progress.");
        System.out.println("  language: Language setting, like 'english' or 'german'.");
        System.out.println("  game: Game version identifier, e.g., 'game0' or 'game1'.");
        System.out.println("\nThese arguments are kept for authentic purposes, reflecting original application parameters.");
        return;
      }

      System.out.println("Loading config path " + configPath);
      GlobalJsonConfig.load(configPath);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
		try {
			if (args.length != 4) {
				args = new String[4];
				args[0] = "1";
				args[1] = "live";
				args[2] = "english";
				args[3] = "game0";
				// Static131.method2577("argument count");
			}
			@Pc(15) int languageId = -1;
			worldListId = Integer.parseInt(args[0]);
			if (GlobalJsonConfig.instance != null) {
				worldListId = GlobalJsonConfig.instance.world;
			}
			modeWhere = 2;
			if (args[1].equals("live")) {
				modeWhat = 0;
			} else if (args[1].equals("rc")) {
				modeWhat = 1;
			} else if (args[1].equals("wip")) {
				modeWhat = 2;
			} else {
				printUsage("modewhat");
			}
			advertSuppressed = false;
			try {
				@Pc(63) byte[] langBytes = args[2].getBytes(StandardCharsets.ISO_8859_1);
				languageId = LangUtils.getLanguageId(JagString.decodeString(langBytes, langBytes.length, 0));
			} catch (@Pc(74) Exception ignored) {
			}
			if (languageId != -1) {
				language = languageId;
			} else if (args[2].equals("english")) {
				language = 0;
			} else if (args[2].equals("german")) {
				language = 1;
			} else {
				printUsage("language");
			}
			LocalizedText.setLanguage(language);
			javaScript = false;
			objectTag = false;
			if (args[3].equals("game0")) {
				game = 0;
			} else if (args[3].equals("game1")) {
				game = 1;
			} else {
				printUsage("game");
			}
			country = 0;
			haveIe6 = false;
			affiliate = 0;
			settings = JagString.EMPTY;
			@Pc(146) client c = new client();
			instance = c;
			c.startApplication(modeWhat + 32, "runescape");
			GameShell.frame.setLocationRelativeTo(null);
			GameShell.frame.setSize(1024, 768); // set a reasonable size by default
		} catch (@Pc(167) Exception ex) {
			TracingException.report(null, ex);
		}
	}

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(Ljava/lang/String;B)V")
	public static void printUsage(@OriginalArg(0) String reason) {
		System.out.println("Bad " + reason + ", Usage: worldid, <live/rc/wip>, <english/german>, <game0/game1>");
		System.exit(1);
	}

	@OriginalMember(owner = "client!re", name = "a", descriptor = "(I)V")
	public static void clean() {
		FloTypeList.clean();
		FluTypeList.clean();
		IdkTypeList.clean();
		LocTypeList.clean();
		NpcTypeList.clean();
		ObjTypeList.clean();
		SeqTypeList.clean();
		SpotAnimTypeList.clean();
		VarbitTypeList.clean();
		VarpTypeList.clean();
		BasTypeList.clean();
		MsiTypeList.clean();
		LightTypeList.clean();
		CursorTypeList.clean();
		PlayerAppearance.clean();
		Component.clean();
		HintArrowManager.clean();
		ShadowModelList.clean();
		HitBarList.hitBars.clean(5);
		FontMetricsList.fontMetrics.clean(5);
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(II)V")
	public static void setGameState(@OriginalArg(0) int state) {
		if(state == 30) {
			PluginRepository.OnLogin();
		}
		if (gameState == state) {
			return;
		}
		if (gameState == 0) {
			LoadingBarAwt.clear();
		}
		if (state == 40) {
			LoginManager.clear();
		}
		@Pc(37) boolean enteringTitleScreen = state == 5 || state == 10 || state == 28;
		if (state != 40 && Protocol.previousSocket != null) {
			Protocol.previousSocket.close();
			Protocol.previousSocket = null;
		}
		if (state == 25 || state == 28) {
			LoginManager.missingLocModelCount = 0;
			peakMapFilesMissing = 1;
			LoginManager.loadingScreenState = 0;
			peakLocModelsMissing = 1;
			LoginManager.mapFilesMissingCount = 0;
			WorldMap.clear(true);
		}
		if (state == 25 || state == 10) {
			topBannerRefresh();
		}
		if (state == 5) {
			TitleScreen.load(js5Archive8);
		} else {
			TitleScreen.clear();
		}
		@Pc(106) boolean wasTitleScreen = gameState == 5 || gameState == 10 || gameState == 28;
		if (wasTitleScreen != enteringTitleScreen) {
			if (enteringTitleScreen) {
				MusicPlayer.groupId = MusicPlayer.titleSong;
				if (Preferences.musicVolume == 0) {
					MidiPlayer.playFadeOut();
				} else {
					MidiPlayer.playFadeOut(MusicPlayer.titleSong, js5Archive6, Preferences.musicVolume);
				}
				js5NetQueue.writeLoggedIn(false);
			} else {
				MidiPlayer.playFadeOut();
				js5NetQueue.writeLoggedIn(true);
			}
		}
		if (GlRenderer.enabled && (state == 25 || state == 28 || state == 40)) {
			GlRenderer.draw();
		}
		gameState = state;
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(ZZZIZ)Lclient!ve;")
	public static Js5 createJs5(@OriginalArg(0) boolean discardPacked, @OriginalArg(1) boolean prefetchAll, @OriginalArg(2) boolean discardUnpacked, @OriginalArg(3) int archive) {
		@Pc(7) Cache cache = null;
		if (cacheData != null) {
			cache = new Cache(archive, cacheData, cacheIndexes[archive], 1000000);
		}
		js5Providers[archive] = js5MasterIndex.getResourceProvider(archive, masterCache, cache);
		if (prefetchAll) {
			js5Providers[archive].prefetchAll();
		}
		return new Js5(js5Providers[archive], discardPacked, discardUnpacked);
	}

	@OriginalMember(owner = "client!je", name = "h", descriptor = "(I)V")
	public static void unloadSoft() {
		FloTypeList.removeSoft();
		FluTypeList.removeSoft();
		IdkTypeList.removeSoft();
		LocTypeList.removeSoft();
		NpcTypeList.removeSoft();
		ObjTypeList.removeSoft();
		SeqTypeList.removeSoft();
		SpotAnimTypeList.removeSoft();
		VarbitTypeList.removeSoft();
		VarpTypeList.removeSoft();
		BasTypeList.removeSoft();
		MsiTypeList.removeSoft();
		LightTypeList.removeSoft();
		CursorTypeList.removeSoft();
		PlayerAppearance.removeSoft();
		Component.removeSoft();
		HintArrowManager.removeSoft();
		ShadowModelList.removeSoft();
		HitBarList.hitBars.removeSoft();
		FontMetricsList.fontMetrics.removeSoft();
	}

	@OriginalMember(owner = "client!rj", name = "f", descriptor = "(B)V")
	public static void unload() {
		FloTypeList.clear();
		FluTypeList.clear();
		IdkTypeList.clear();
		LocTypeList.clear();
		NpcTypeList.clear();
		ObjTypeList.clear();
		SeqTypeList.clear();
		SpotAnimTypeList.clear();
		VarbitTypeList.clear();
		VarpTypeList.clear();
		BasTypeList.clear();
		MsiTypeList.clear();
		LightTypeList.clear();
		CursorTypeList.clear();
		PlayerAppearance.clear();
		Component.clear();
		if (modeWhat != 0) {
			for (@Pc(54) int i = 0; i < Player.glPaddingBuffers.length; i++) {
				Player.glPaddingBuffers[i] = null;
			}
			Player.glPaddingCount = 0;
		}
		HintArrowManager.clear();
		ShadowModelList.clear();
		FontMetricsList.fontMetrics.clear();
		if (!GlRenderer.enabled) {
			((Js5GlTextureProvider) Rasteriser.textureProvider).clear();
		}
		ClientScriptList.scripts.clear();
		js5Archive0.discardUnpacked();
		js5Archive1.discardUnpacked();
		js5Archive3.discardUnpacked();
		js5Archive4.discardUnpacked();
		js5Archive5.discardUnpacked();
		js5Archive6.discardUnpacked();
		js5Archive7.discardUnpacked();
		js5Archive8.discardUnpacked();
		js5Archive10.discardUnpacked();
		js5Archive11.discardUnpacked();
		js5Archive12.discardUnpacked();
		HitBarList.hitBars.clear();
	}

	@OriginalMember(owner = "client!id", name = "b", descriptor = "(I)V")
	public static void audioLoop() {
		if (soundChannel != null) {
			soundChannel.loop();
		}
		if (musicChannel != null) {
			musicChannel.loop();
		}
	}

	@OriginalMember(owner = "client!la", name = "a", descriptor = "(Lclient!wa;Z)V")
	public static void writeUid(@OriginalArg(0) Buffer buffer) {
		@Pc(15) byte[] uidBytes = new byte[24];
		if (uid != null) {
			try {
				uid.seek(0L);
				uid.read(uidBytes);
				@Pc(28) int i;
				for (i = 0; i < 24 && uidBytes[i] == 0; i++) {
				}
				if (i >= 24) {
					throw new IOException();
				}
			} catch (@Pc(55) Exception ignored) {
				for (@Pc(57) int j = 0; j < 24; j++) {
					uidBytes[j] = -1;
				}
			}
		}
		buffer.pdata(uidBytes, 24);
	}
	
	public void saveScreenshot(String filename, String... subfolders) {
		String homeDirOverride = System.getProperty("clientHomeOverride");
		String homeDir = null;
		String osNameRaw = "";
		String osName = "";
		try {
			osNameRaw = System.getProperty("os.name");
		} catch (Exception ignored) {
			osNameRaw = "Unknown";
		}
		osName = osNameRaw.toLowerCase();
		if (homeDirOverride != null) {
			homeDir = homeDirOverride;
		} else {
			try {
				if (homeDir == null)
					homeDir = System.getProperty("user.home") + File.separatorChar;

				if (osName.startsWith("linux")) {
					String xdgHome = System.getenv("XDG_DATA_HOME");

					if (xdgHome != null) {
						homeDir = xdgHome + "/2009scape/";
					} else {
						homeDir += ".local/share/2009scape/";
					}
				} else if (osName.startsWith("mac")) {
					homeDir += "Library/Application Support/2009scape/";
				} else if (osName.startsWith("windows")) {
					homeDir += "2009scape\\";
				}
			} catch (Exception ex) {
			}
		}
		
		String subfolderPath = String.join(File.separator, subfolders);
		if (!subfolderPath.isEmpty()) {
			subfolderPath += File.separator;
		}
		
		File outputFolder = new File(homeDir + File.separatorChar + "screenshots" + File.separatorChar + subfolderPath);
		if (!outputFolder.exists()){
			outputFolder.mkdirs();
		}
		
		try {
			Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
			if (window == null) {
				return;
			}
			Point point = window.getLocationOnScreen();
			int x = (int) point.getX();
			int y = (int) point.getY();
			int w = window.getWidth();
			int h = window.getHeight();
			Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
			Rectangle captureSize = new Rectangle(x, y, w, h);
			BufferedImage image = robot.createScreenCapture(captureSize);
			File outputFile = new File(outputFolder, filename);
			ImageIO.write(image, "png", outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "(Z)V")
	public static void reloadResourcesOnDisplayModeChange() {
		SceneGraph.clear();
		MiniMap.sprite = null;
		LightingManager.minimapRenderedPlane = -1;
		unload();
		DeadClass.cache.clear();
		LocType.tempLocEntity = new LocEntity();
		((Js5GlTextureProvider) Rasteriser.textureProvider).clear();
		LightingManager.lightCount = 0;
		LightingManager.lights = new Light[255];
		GlModel.initCopyTargets();
		ShadowManager.destroy();
		Sprites.clear();
		WorldMap.clear(false);
		TitleScreen.clear();
		for (@Pc(39) int i = 0; i < 2048; i++) {
			@Pc(46) Player player = PlayerList.players[i];
			if (player != null) {
				player.attachment = null;
			}
		}
		if (GlRenderer.enabled) {
			ShadowManager.init();
			ParticleSystem.load();
		}
		Fonts.load(js5Archive13, js5Archive8);
		Sprites.load(js5Archive8);
		LoginManager.menuBottomEdgeSprite = null;
		LoginManager.menuHeaderFillSprite = null;
		LoginManager.menuSideFillSprite = null;
		LoginManager.menuBottomFillSprite = null;
		LoginManager.menuHeaderEdgeSprite = null;
		if (gameState == 5) {
			TitleScreen.load(js5Archive8);
		}
		if (gameState == 10) {
			InterfaceList.resetToLoginScreen(false);
		}
		if (gameState == 30) {
			setGameState(25);
		}
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(Z)V")
	public static void resetGameSessionState() {
		Protocol.mouseIdleSamples = 0;
		Protocol.prevFocus = true;
		Mouse.prevClickTime = 0L;
		MouseRecorder.instance.samples = 0;
		focus = true;
		ReflectionCheck.clear();
		Protocol.opcode4 = -1;
		Protocol.opcode3 = -1;
		Protocol.opcode = -1;
		Protocol.logoutOnDisconnectTimer = 0;
		Player.rebootTimer = 0;
		Protocol.outboundBuffer.offset = 0;
		Protocol.opcode2 = -1;
		LoginManager.ticksSinceLastPacket = 0;
		Protocol.inboundBuffer.offset = 0;
		@Pc(3506) int i;
		for (i = 0; i < MiniMap.hintMapMarkers.length; i++) {
			MiniMap.hintMapMarkers[i] = null;
		}
		MiniMenu.size = 0;
		Cs1ScriptRunner.isMenuOpen = false;
		Mouse.setIdleLoops(0);
		for (i = 0; i < 100; i++) {
			Chat.messages[i] = null;
		}
		MiniMenu.itemTargetMode = 0;
		Camera.cameraOffsetX = (int) (Math.random() * 100.0D) - 50;
		LoginManager.mapFlagY = 0;
		Camera.yawTarget = (int) (Math.random() * 20.0D) - 10 & 0x7FF;
		LightingManager.minimapRenderedPlane = -1;
		PlayerList.size = 0;
		MiniMap.state = 0;
		Camera.cameraOffsetY = (int) (Math.random() * 110.0D) - 55;
		MiniMenu.isTargeting = false;
		MiniMap.zoomOffset = (int) (Math.random() * 30.0D) - 20;
		SoundPlayer.size = 0;
		LoginManager.mapFlagX = 0;
		MiniMap.compassAngleOffset = (int) (Math.random() * 120.0D) - 60;
		Chat.size = 0;
		Camera.yawDrift = (int) (Math.random() * 80.0D) - 40;
		NpcList.size = 0;
		for (i = 0; i < 2048; i++) {
			PlayerList.players[i] = null;
			PlayerList.appearanceCache[i] = null;
		}
		for (i = 0; i < 32768; i++) {
			NpcList.npcs[i] = null;
		}
		PlayerList.self = PlayerList.players[2047] = new Player();
		SceneGraph.projectiles.clear();
		SceneGraph.spotanims.clear();
		if (SceneGraph.objStacks != null) {
			for (i = 0; i < 4; i++) {
				for (@Pc(3663) int x = 0; x < 104; x++) {
					for (@Pc(3670) int y = 0; y < 104; y++) {
						SceneGraph.objStacks[i][x][y] = null;
					}
				}
			}
		}
		ChangeLocRequest.queue = new LinkedList();
		FriendsList.state = 0;
		FriendsList.size = 0;
		VarpDomain.reset();
		DelayedStateChange.clear();
		Camera.lockedMinStep = 0;
		Camera.lockedAngleSpeed = 0;
		Camera.lockedLookAtY = 0;
		Camera.lockedTargetHeight = 0;
		Camera.lockedTargetY = 0;
		Camera.lockedTargetX = 0;
		Camera.lockedLookAtHeight = 0;
		Camera.lockedLookAtX = 0;
		Camera.lockedMoveSpeed = 0;
		Camera.lockedMinMoveStep = 0;
		for (i = 0; i < VarcDomain.varcs.length; i++) {
			VarcDomain.varcs[i] = -1;
		}
		if (InterfaceList.topLevelInterface != -1) {
			InterfaceList.unload(InterfaceList.topLevelInterface);
		}
		for (@Pc(3755) ComponentPointer pointer = (ComponentPointer) InterfaceList.openInterfaces.head(); pointer != null; pointer = (ComponentPointer) InterfaceList.openInterfaces.next()) {
			InterfaceList.closeInterface(true, pointer);
		}
		InterfaceList.topLevelInterface = -1;
		InterfaceList.openInterfaces = new HashTable(8);
		InterfaceList.reset();
		Cs1ScriptRunner.pleaseWaitComponent = null;
		Cs1ScriptRunner.isMenuOpen = false;
		MiniMenu.size = 0;
		PlayerAppearance.DEFAULT.set(new int[]{0, 0, 0, 0, 0}, -1, false, null, -1);
		for (i = 0; i < 8; i++) {
			Player.options[i] = null;
			Player.secondaryOptions[i] = false;
			Player.cursors[i] = -1;
		}
		Inv.clear();
		ScriptRunner.loadingScene = true;
		for (i = 0; i < 100; i++) {
			InterfaceList.rectangleDirty[i] = true;
		}
		ClanChat.size = 0;
		ClanChat.members = null;
		ClanChat.name = null;
		for (i = 0; i < 6; i++) {
			StockMarketManager.offers[i] = new StockMarketOffer();
		}
		for (i = 0; i < 25; i++) {
			PlayerSkillXpTable.boostedLevels[i] = 0;
			PlayerSkillXpTable.baseLevels[i] = 0;
			PlayerSkillXpTable.experience[i] = 0;
		}
		if (GlRenderer.enabled) {
			FogManager.setInstantFade();
		}
		Protocol.cameraPositionChanged = true;
		Protocol.verifyId = 0;
		MiniMenu.walkText = LocalizedText.WALKHERE;
		ScriptRunner.neverRemoveRoofs = false;
		scriptRecolorPalette = locRecolorPalette = npcRecolorPalette = objRecolorPalette = new short[256];
		LoginManager.clearLoginScreenSprites();
		InterfaceList.useStyledMenu = false;
		ClientProt.sendWindowDetails();
	}

	@OriginalMember(owner = "client!rc", name = "d", descriptor = "(I)V")
	public static void reinitAudio() {
		if (musicChannel != null) {
			musicChannel.quit();
		}
		if (soundChannel != null) {
			soundChannel.quit();
		}
		AudioChannel.init(Preferences.stereo);
		musicChannel = AudioChannel.create(GlobalConfig.AUDIO_SAMPLE_RATE, signLink, canvas, 0);
		musicChannel.setStream(musicStream);
		soundChannel = AudioChannel.create(2048, signLink, canvas, 1);
		soundChannel.setStream(soundStream);
	}

	@OriginalMember(owner = "client!ag", name = "j", descriptor = "(I)V")
	public static void clearScene() {
		SceneGraph.clear();
		for (@Pc(9) int i = 0; i < 4; i++) {
			PathFinder.collisionMaps[i].clear();
		}
		System.gc();
	}

	@OriginalMember(owner = "client!jj", name = "a", descriptor = "(Z)V")
	public static void topBannerRefresh() {
		if (!advertSuppressed && modeWhere != 2) {
			try {
				BROWSER_REFRESH_CMD.browserControlCall(instance);
			} catch (@Pc(26) Throwable ignored) {
			}
		}
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(Z)Z")
	public static boolean showVideoAd() {
		if (objectTag) {
			try {
				BROWSER_SHOW_AD_CMD.browserControlCall(signLink.applet);
				return true;
			} catch (@Pc(14) Throwable ignored) {
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!client", name = "f", descriptor = "(I)V")
	@Override
	protected final void mainRedraw() {
		if(DisplayMode.resizableSD && !GlRenderer.enabled){
			GameShell.fullRedraw = true;
			InterfaceList.fullRedrawAllInterfaces();
		}
		if (gameState == 1000) {
			return;
		}
		@Pc(15) boolean songStarted = MidiPlayer.tryStartLoadedSong();
		if (songStarted && MidiPlayer.jingle && musicChannel != null) {
			musicChannel.forceClose();
		}
		if ((gameState == 30 || gameState == 10) && (GameShell.replaceCanvas || DisplayMode.canvasReplaceTime != 0L && DisplayMode.canvasReplaceTime < MonotonicClock.currentTimeMillis())) {
			DisplayMode.setWindowMode(GameShell.replaceCanvas, DisplayMode.getWindowMode(), Preferences.fullScreenWidth, Preferences.fullScreenHeight);
		}
		@Pc(80) int w;
		@Pc(84) int h;
		if (GameShell.fullScreenFrame == null) {
			@Pc(65) Container container;
			if (GameShell.fullScreenFrame != null) {
				container = GameShell.fullScreenFrame;
			} else if (GameShell.frame == null) {
				container = GameShell.signLink.applet;
			} else {
				container = GameShell.frame;
			}
			w = container.getSize().width;
			h = container.getSize().height;
			if (container == GameShell.frame) {
				@Pc(90) Insets insets = GameShell.frame.getInsets();
				w -= insets.right + insets.left;
				h -= insets.top + insets.bottom;
			}
			if (w != GameShell.frameWidth || h != GameShell.frameHeight) {
				GameShell.updateCanvasSize();
				DisplayMode.canvasReplaceTime = MonotonicClock.currentTimeMillis() + 500L;
			}
		}
		/**
		 *  If the game is running in fullscreen mode and focus is lost, by alt tab or ctrl alt tab, the client exits
		 *  fullscreen mode and enters window mode.
		 *  This line can also be used to launch back into full screen when out of focus and refocused by sending mode 3
		 *  with the right width and height, by Preference.width and Preference.height for exmaple.
		 */
		if (GameShell.fullScreenFrame != null && !GameShell.focus && (gameState == 30 || gameState == 10)) {
			DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
		}
		@Pc(158) boolean fullRedraw = false;
		if (GameShell.fullRedraw) {
			fullRedraw = true;
			GameShell.fullRedraw = false;
		}
		if (fullRedraw) {
			GameShell.paintFrameLetterbox(); // Creates a black background for SD Mode gameplay frame to render on top of.
		}
		if (GlRenderer.enabled) {
			for (w = 0; w < 100; w++) {
				InterfaceList.rectangleDirty[w] = true;
			}
		}
		if (gameState == 0) {
			LoadingBarAwt.render(null, fullRedraw, mainLoadSecondaryText, mainLoadPercentage);
		} else if (gameState == 5) {
			LoadingBar.render(false, Fonts.b12Full);
		} else if (gameState == 10) {
			InterfaceList.updateLoginScreen();
		} else if (gameState == 25 || gameState == 28) {
			if (LoginManager.loadingScreenState == 1) {
				if (peakMapFilesMissing < LoginManager.mapFilesMissingCount) {
					peakMapFilesMissing = LoginManager.mapFilesMissingCount;
				}
				w = (peakMapFilesMissing - LoginManager.mapFilesMissingCount) * 50 / peakMapFilesMissing;
				Fonts.drawTextOnScreen(false, JagString.concatenate(new JagString[]{LocalizedText.LOADING, LOADING_PERCENT_PREFIX, JagString.parseInt(w), Cs1ScriptRunner.CACHE_STAT_SUFFIX}));
			} else if (LoginManager.loadingScreenState == 2) {
				if (peakLocModelsMissing < LoginManager.missingLocModelCount) {
					peakLocModelsMissing = LoginManager.missingLocModelCount;
				}
				w = (peakLocModelsMissing - LoginManager.missingLocModelCount) * 50 / peakLocModelsMissing + 50;
				Fonts.drawTextOnScreen(false, JagString.concatenate(new JagString[]{LocalizedText.LOADING, LOADING_PERCENT_PREFIX, JagString.parseInt(w), Cs1ScriptRunner.CACHE_STAT_SUFFIX}));
			} else {
				Fonts.drawTextOnScreen(false, LocalizedText.LOADING);
			}
		} else if (gameState == 30) {
			LoginManager.processInterface();
		} else if (gameState == 40) {
			Fonts.drawTextOnScreen(false, JagString.concatenate(new JagString[]{LocalizedText.CONLOST, JagString.LINE_BREAK, LocalizedText.ATTEMPT_TO_REESTABLISH}));
		}
		if (GlRenderer.enabled && gameState != 0) {
			GlRenderer.swapBuffers();
			for (w = 0; w < InterfaceList.rectangles; w++) {
				InterfaceList.rectangleRedraw[w] = false;
			}
		} else {
			@Pc(388) Graphics graphics;
			if ((gameState == 30 || gameState == 10) && Cheat.rectDebug == 0 && !fullRedraw) {
				try {
					graphics = GameShell.canvas.getGraphics();
					for (h = 0; h < InterfaceList.rectangles; h++) {
						if (InterfaceList.rectangleRedraw[h]) {
							SoftwareRaster.frameBuffer.drawAt(InterfaceList.rectangleWidth[h], InterfaceList.rectangleX[h], InterfaceList.rectangleHeight[h], graphics, InterfaceList.rectangleY[h]);
							InterfaceList.rectangleRedraw[h] = false;
						}
					}
				} catch (@Pc(423) Exception ignored) {
					GameShell.canvas.repaint();
				}
			} else if (gameState != 0) {
				try {
					graphics = GameShell.canvas.getGraphics();
					SoftwareRaster.frameBuffer.draw(graphics);
					for (h = 0; h < InterfaceList.rectangles; h++) {
						InterfaceList.rectangleRedraw[h] = false;
					}
				} catch (@Pc(453) Exception ignored) {
					GameShell.canvas.repaint();
				}
			}
		}
		if (clean) {
			clean();
		}
		if (Preferences.safeMode && gameState == 10 && InterfaceList.topLevelInterface != -1) {
			Preferences.safeMode = false;
			Preferences.write(GameShell.signLink);
		}
		PluginRepository.LateDraw();
	}

	@OriginalMember(owner = "client!client", name = "c", descriptor = "(B)V")
	@Override
	protected final void mainQuit() {
		if (GlRenderer.enabled) {
			GlRenderer.quit();
		}
		if (GameShell.fullScreenFrame != null) {
			DisplayMode.exitFullScreen(GameShell.fullScreenFrame, GameShell.signLink);
			GameShell.fullScreenFrame = null;
		}
		if (GameShell.signLink != null) {
			GameShell.signLink.unloadGlNatives(this.getClass());
		}
		if (MouseRecorder.instance != null) {
			MouseRecorder.instance.running = false;
		}
		MouseRecorder.instance = null;
		if (Protocol.socket != null) {
			Protocol.socket.close();
			Protocol.socket = null;
		}
		Keyboard.stop(GameShell.canvas);
		Mouse.stop(GameShell.canvas);
		if (mouseWheel != null) {
			mouseWheel.stop(GameShell.canvas);
		}
		Keyboard.quit();
		Mouse.quit();
		mouseWheel = null;
		if (musicChannel != null) {
			musicChannel.quit();
		}
		if (soundChannel != null) {
			soundChannel.quit();
		}
		js5NetQueue.quit();
		js5CacheQueue.quit();
		try {
			if (cacheData != null) {
				cacheData.close();
			}
			if (cacheIndexes != null) {
				for (@Pc(95) int i = 0; i < cacheIndexes.length; i++) {
					if (cacheIndexes[i] != null) {
						cacheIndexes[i].close();
					}
				}
			}
			if (cacheMasterIndex != null) {
				cacheMasterIndex.close();
			}
			if (uid != null) {
				uid.close();
			}
		} catch (@Pc(129) IOException ignored) {
		}
	}

	@OriginalMember(owner = "client!client", name = "init", descriptor = "()V")
	@Override
	public final void init() {
		if (!this.isHostnameValid()) {
			return;
		}
		worldListId = Integer.parseInt(this.getParameter("worldid"));
		modeWhere = Integer.parseInt(this.getParameter("modewhere"));
		if (modeWhere < 0 || modeWhere > 1) {
			modeWhere = 0;
		}
		modeWhat = Integer.parseInt(this.getParameter("modewhat"));
		if (modeWhat < 0 || modeWhat > 2) {
			modeWhat = 0;
		}
		@Pc(50) String advertParam = this.getParameter("advertsuppressed");
		advertSuppressed = advertParam != null && advertParam.equals("1");
		try {
			language = Integer.parseInt(this.getParameter("lang"));
		} catch (@Pc(69) Exception ignored) {
			language = 0;
		}
		LocalizedText.setLanguage(language);
		@Pc(78) String objectTagParam = this.getParameter("objecttag");
		javaScript = objectTagParam != null && objectTagParam.equals("1");
		@Pc(94) String jsParam = this.getParameter("js");
		objectTag = jsParam != null && jsParam.equals("1");
		@Pc(111) String gameParam = this.getParameter("game");
		if (gameParam != null && gameParam.equals("1")) {
			game = 1;
		} else {
			game = 0;
		}
		try {
			affiliate = Integer.parseInt(this.getParameter("affid"));
		} catch (@Pc(130) Exception ignored) {
			affiliate = 0;
		}
		settings = SETTINGS.fromParameters(this);
		if (settings == null) {
			settings = JagString.EMPTY;
		}
		@Pc(146) String countryParam = this.getParameter("country");
		if (countryParam != null) {
			try {
				country = Integer.parseInt(countryParam);
			} catch (@Pc(153) Exception ignored) {
				country = 0;
			}
		}
		@Pc(159) String ie6Param = this.getParameter("haveie6");
		haveIe6 = ie6Param != null && ie6Param.equals("1");
		instance = this;
		this.startApplet(modeWhat + 32);
	}

	@OriginalMember(owner = "client!client", name = "g", descriptor = "(I)V")
	@Override
	protected final void mainInit() {
		GameShell.updateCanvasSize();
		js5CacheQueue = new Js5CacheQueue();
		js5NetQueue = new Js5NetQueue();

		if (modeWhat != 0) {
			Player.glPaddingBuffers = new byte[50][];
		}

		Preferences.read(GameShell.signLink);

		if (modeWhere == 0) {
			worldListHostname = GlobalConfig.DEFAULT_HOSTNAME; // this.getCodeBase().getHost();
			worldListAlternatePort = GlobalConfig.ALTERNATE_PORT + 1;
			worldListDefaultPort = GlobalConfig.DEFAULT_PORT + 1;
		} else if (modeWhere == 1) {
			worldListHostname = GlobalConfig.DEFAULT_HOSTNAME; // this.getCodeBase().getHost();
			worldListAlternatePort = GlobalConfig.ALTERNATE_PORT + worldListId;
			worldListDefaultPort = GlobalConfig.DEFAULT_PORT + worldListId;
		} else if (modeWhere == 2) {
			worldListHostname = GlobalConfig.DEFAULT_HOSTNAME; // "127.0.0.1";
			worldListAlternatePort = GlobalConfig.ALTERNATE_PORT + worldListId;
			worldListDefaultPort = GlobalConfig.DEFAULT_PORT + worldListId;
		}

		if (GlobalJsonConfig.instance != null) {
			worldListHostname = GlobalJsonConfig.instance.ip_address;
			worldListAlternatePort = GlobalJsonConfig.instance.server_port + worldListId;
			worldListDefaultPort = GlobalJsonConfig.instance.server_port;
		}

		if (game == 1) {
			Cheat.shiftClick = true;
			FogManager.defaultLightColorRgb = 16777215;
			FogManager.defaulFogColorRgb = 0;
			PlayerAppearance.destinationBodyColors = PlayerAppearance.GAME1_DESTINATION_BODY_COLORS;
			PlayerAppearance.destinationSkinColors = PlayerAppearance.GAME1_DESTINATION_SKIN_COLORS;
			PlayerAppearance.sourceBodyColors = PlayerAppearance.GAME1_SOURCE_BODY_COLORS;
			PlayerAppearance.sourceSkinColors = PlayerAppearance.GAME1_SOURCE_SKIN_COLORS;
		} else {
			PlayerAppearance.destinationBodyColors = PlayerAppearance.GAME0_DESTINATION_BODY_COLORS;
			PlayerAppearance.sourceSkinColors = PlayerAppearance.GAME0_SOURCE_SKIN_COLORS;
			PlayerAppearance.destinationSkinColors = PlayerAppearance.GAME0_DESTINATION_SKIN_COLORS;
			PlayerAppearance.sourceBodyColors = PlayerAppearance.GAME0_SOURCE_BODY_COLORS;
		}

		alternatePort = worldListAlternatePort;
		defaultPort = worldListDefaultPort;
		hostname = worldListHostname;
		worldListPort = worldListDefaultPort;
		scriptRecolorPalette = locRecolorPalette = npcRecolorPalette = objRecolorPalette = new short[256];
		port = worldListPort;
		if ((SignLink.clientMode == 3 && modeWhere != 2) || GlobalConfig.SELECT_DEFAULT_WORLD) {
			Player.worldId = worldListId;
		}

		Keyboard.init();
		Keyboard.start(GameShell.canvas);
		Mouse.start(GameShell.canvas);
		mouseWheel = MouseWheel.create();
		if (mouseWheel != null) {
			mouseWheel.start(GameShell.canvas);
		}
		startupClientMode = SignLink.clientMode;
		try {
			if (GameShell.signLink.cacheData != null) {
				cacheData = new BufferedFile(GameShell.signLink.cacheData, 5200, 0);
				for (@Pc(162) int i = 0; i < 28; i++) {
					cacheIndexes[i] = new BufferedFile(GameShell.signLink.cacheIndexes[i], 6000, 0);
				}
				cacheMasterIndex = new BufferedFile(GameShell.signLink.cacheMasterIndex, 6000, 0);
				masterCache = new Cache(255, cacheData, cacheMasterIndex, 500000);
				uid = new BufferedFile(GameShell.signLink.uid, 24, 0);
				GameShell.signLink.cacheIndexes = null;
				GameShell.signLink.cacheMasterIndex = null;
				GameShell.signLink.uid = null;
				GameShell.signLink.cacheData = null;
			}
		} catch (@Pc(220) IOException ex) {
			uid = null;
			cacheData = null;
			cacheMasterIndex = null;
			masterCache = null;
		}
		mainLoadPrimaryText = LocalizedText.GAME0_LOADING;
		if (modeWhere != 0) {
			//Cheat.displayFps = true;
		}
		PluginRepository.Init();
	}

	@OriginalMember(owner = "client!client", name = "c", descriptor = "(I)V")
	@Override
	protected final void reset() {
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(ZI)V")
	private void setJs5Response(@OriginalArg(1) int response) {
		js5NetQueue.errors++;
		js5SocketRequest = null;
		js5NetQueue.response = response;
		js5Socket = null;
		js5ConnectState = 0;
	}

	@OriginalMember(owner = "client!client", name = "d", descriptor = "(B)V")
	private void mainUpdate() {
		for (InterfaceList.keyQueueSize = 0; Keyboard.nextKey() && InterfaceList.keyQueueSize < 128; InterfaceList.keyQueueSize++) {
			InterfaceList.keyCodes[InterfaceList.keyQueueSize] = Keyboard.keyCode;
			InterfaceList.keyChars[InterfaceList.keyQueueSize] = Keyboard.keyChar;
		}
		Protocol.sceneDelta++;
		if (InterfaceList.topLevelInterface != -1) {
			InterfaceList.processSubInterface(0, 0, 0, GameShell.canvasWidth, InterfaceList.topLevelInterface, 0, GameShell.canvasHeight);
		}
		InterfaceList.transmitTimer++;
		if (GlRenderer.enabled) {
			nextNpc:
			for (@Pc(57) int n = 0; n < 32768; n++) {
				@Pc(66) Npc npc = NpcList.npcs[n];
				if (npc != null) {
					@Pc(73) byte properties = npc.type.loginscreenproperties;
					if ((properties & 0x2) > 0 && npc.movementQueueSize == 0 && Math.random() * 1000.0D < 10.0D) {
						@Pc(98) int dx = (int) Math.round(Math.random() * 2.0D - 1.0D);
						@Pc(106) int dy = (int) Math.round(Math.random() * 2.0D - 1.0D);
						if (dx != 0 || dy != 0) {
							npc.movementQueueSpeed[0] = 1;
							npc.movementQueueX[0] = dx + (npc.xFine >> 7);
							npc.movementQueueY[0] = dy + (npc.yFine >> 7);
							PathFinder.collisionMaps[Player.plane].unflagScenery(npc.xFine >> 7, npc.getSize(), false, 0, npc.getSize(), npc.yFine >> 7);
							if (npc.movementQueueX[0] >= 0 && npc.movementQueueX[0] <= 104 - npc.getSize() && npc.movementQueueY[0] >= 0 && npc.movementQueueY[0] <= 104 - npc.getSize() && PathFinder.collisionMaps[Player.plane].isPathClear(npc.yFine >> 7, npc.movementQueueY[0], npc.movementQueueX[0], npc.xFine >> 7)) {
								if (npc.getSize() > 1) {
									for (@Pc(226) int tileX = npc.movementQueueX[0]; npc.movementQueueX[0] + npc.getSize() > tileX; tileX++) {
										for (@Pc(246) int tileY = npc.movementQueueY[0]; npc.movementQueueY[0] + npc.getSize() > tileY; tileY++) {
											if ((PathFinder.collisionMaps[Player.plane].flags[tileX][tileY] & 0x12401FF) != 0) {
												continue nextNpc;
											}
										}
									}
								}
								npc.movementQueueSize = 1;
							}
						}
					}
					NpcList.processMovement(npc);
					NpcList.processFacing(npc);
					NpcList.processAnimations(npc);
					PathFinder.collisionMaps[Player.plane].flagScenery(npc.xFine >> 7, false, npc.yFine >> 7, npc.getSize(), npc.getSize());
				}
			}
		}
		if (!GlRenderer.enabled) {
			Flames.update();
		} else if (LoginManager.step == 0 && CreateManager.step == 0) {
			if (Camera.cameraType == 2) {
				Camera.updateLockedCamera();
			} else {
				Camera.updateLoginScreenCamera();
			}
			if (Camera.renderX >> 7 < 14 || Camera.renderX >> 7 >= 90 || Camera.renderY >> 7 < 14 || Camera.renderY >> 7 >= 90) {
				LoginManager.setupLoadingScreenRegion();
			}
		}
		while (true) {
			@Pc(374) HookRequest priorityRequest;
			@Pc(379) Component prioritySource;
			@Pc(387) Component priorityComponent;
			do {
				priorityRequest = (HookRequest) InterfaceList.highPriorityRequests.removeHead();
				if (priorityRequest == null) {
					while (true) {
						do {
							priorityRequest = (HookRequest) InterfaceList.mediumPriorityRequests.removeHead();
							if (priorityRequest == null) {
								while (true) {
									do {
										priorityRequest = (HookRequest) InterfaceList.lowPriorityRequests.removeHead();
										if (priorityRequest == null) {
											if (Cs1ScriptRunner.draggedComponent != null) {
												Cs1ScriptRunner.updateComponentDrag();
											}
											if (Protocol.openUrlRequest != null && Protocol.openUrlRequest.status == 1) {
												if (Protocol.openUrlRequest.result != null) {
													ScriptRunner.openUrl(ScriptRunner.url, Protocol.newTab);
												}
												Protocol.newTab = false;
												ScriptRunner.url = null;
												Protocol.openUrlRequest = null;
											}
											if (loop % 1500 == 0) {
												topBannerRefresh();
											}
											return;
										}
										prioritySource = priorityRequest.source;
										if (prioritySource.createdComponentId < 0) {
											break;
										}
										priorityComponent = InterfaceList.getComponent(prioritySource.overlayer);
									} while (priorityComponent == null || priorityComponent.createdComponents == null || priorityComponent.createdComponents.length <= prioritySource.createdComponentId || prioritySource != priorityComponent.createdComponents[prioritySource.createdComponentId]);
									ScriptRunner.run(priorityRequest);
								}
							}
							prioritySource = priorityRequest.source;
							if (prioritySource.createdComponentId < 0) {
								break;
							}
							priorityComponent = InterfaceList.getComponent(prioritySource.overlayer);
						} while (priorityComponent == null || priorityComponent.createdComponents == null || prioritySource.createdComponentId >= priorityComponent.createdComponents.length || prioritySource != priorityComponent.createdComponents[prioritySource.createdComponentId]);
						ScriptRunner.run(priorityRequest);
					}
				}
				prioritySource = priorityRequest.source;
				if (prioritySource.createdComponentId < 0) {
					break;
				}
				priorityComponent = InterfaceList.getComponent(prioritySource.overlayer);
			} while (priorityComponent == null || priorityComponent.createdComponents == null || priorityComponent.createdComponents.length <= prioritySource.createdComponentId || prioritySource != priorityComponent.createdComponents[prioritySource.createdComponentId]);
			ScriptRunner.run(priorityRequest);
		}
	}

	@OriginalMember(owner = "client!client", name = "d", descriptor = "(Z)V")
	private void js5NetworkLoop() {
		@Pc(3) boolean idle = js5NetQueue.loop();
		if (!idle) {
			this.js5Connect();
		}
	}

	@OriginalMember(owner = "client!client", name = "h", descriptor = "(I)V")
	private void js5Connect() {
		if (js5PrevErrors < js5NetQueue.errors) {
			js5ConnectDelay = 5 * 50 * (js5NetQueue.errors - 1);
			if (defaultPort == port) {
				port = alternatePort;
			} else {
				port = defaultPort;
			}
			if (js5ConnectDelay > 3000) {
				js5ConnectDelay = 3000;
			}
			if (js5NetQueue.errors >= 2 && js5NetQueue.response == 6) {
				this.error("js5connect_outofdate");
				gameState = 1000;
				return;
			}
			if (js5NetQueue.errors >= 4 && js5NetQueue.response == -1) {
				this.error("js5crc");
				gameState = 1000;
				return;
			}
			if (js5NetQueue.errors >= 4 && (gameState == 0 || gameState == 5)) {
				if (js5NetQueue.response == 7 || js5NetQueue.response == 9) {
					this.error("js5connect_full");
				} else if (js5NetQueue.response > 0) {
					this.error("js5connect");
				} else {
					this.error("js5io");
				}
				gameState = 1000;
				return;
			}
		}
		js5PrevErrors = js5NetQueue.errors;
		if (js5ConnectDelay > 0) {
			js5ConnectDelay--;
			return;
		}
		try {
			if (js5ConnectState == 0) {
				if (GlobalJsonConfig.instance != null) {
					hostname = GlobalJsonConfig.instance.ip_management;
					port = GlobalJsonConfig.instance.server_port + worldListId;
				}
				js5SocketRequest = GameShell.signLink.openSocket(hostname, port);
				js5ConnectState++;
			}
			if (js5ConnectState == 1) {
				if (js5SocketRequest.status == 2) {
					this.setJs5Response(1000);
					return;
				}
				if (js5SocketRequest.status == 1) {
					js5ConnectState++;
				}
			}
			if (js5ConnectState == 2) {
				js5Socket = new BufferedSocket((Socket) js5SocketRequest.result, GameShell.signLink);
				@Pc(194) Buffer buffer = new Buffer(5);
				buffer.p1(15);
				buffer.p4(530);
				js5Socket.write(buffer.data, 5);
				js5ConnectState++;
				js5ConnectTime = MonotonicClock.currentTimeMillis();
			}
			if (js5ConnectState == 3) {
				if (gameState == 0 || gameState == 5 || js5Socket.available() > 0) {
					@Pc(258) int response = js5Socket.read();
					if (response != 0) {
						this.setJs5Response(response);
						return;
					}
					js5ConnectState++;
				} else if (MonotonicClock.currentTimeMillis() - js5ConnectTime > 30000L) {
					this.setJs5Response(1001);
					return;
				}
			}
			if (js5ConnectState == 4) {
				@Pc(296) boolean loggedOut = gameState == 5 || gameState == 10 || gameState == 28;
				js5NetQueue.start(!loggedOut, js5Socket);
				js5Socket = null;
				js5SocketRequest = null;
				js5ConnectState = 0;
			}
		} catch (@Pc(315) IOException ex) {
			this.setJs5Response(1002);
		}
	}

	@OriginalMember(owner = "client!client", name = "i", descriptor = "(I)V")
	private void mainLoad() {
		if (!Preferences.safeMode) {
			noSafeMode:
			while (true) {
				do {
					if (!Keyboard.nextKey()) {
						break noSafeMode;
					}
				} while (Keyboard.keyChar != 115 && Keyboard.keyChar != 83);
				Preferences.safeMode = true;
			}
		}

		@Pc(43) int i;
		if (mainLoadState == 0) {
			@Pc(34) Runtime runtime = Runtime.getRuntime();
			i = (int) (0L / 1024L);
			@Pc(46) long now = MonotonicClock.currentTimeMillis();
			if (firstGc == 0L) {
				firstGc = now;
			}
			if (i > 16384 && now - firstGc < 5000L) {
				if (now - prevGc > 1000L) {
					System.gc();
					prevGc = now;
				}
				mainLoadPercentage = 5;
				mainLoadSecondaryText = LocalizedText.MAINLOAD0;
			} else {
				mainLoadSecondaryText = LocalizedText.MAINLOAD0B;
				mainLoadState = 10;
				mainLoadPercentage = 5;
			}
			return;
		}
		@Pc(98) int percentage;
		if (mainLoadState == 10) {
			LightingManager.initLightGrid();
			for (percentage = 0; percentage < 4; percentage++) {
				PathFinder.collisionMaps[percentage] = new CollisionMap(104, 104);
			}
			mainLoadPercentage = 10;
			mainLoadState = 30;
			mainLoadSecondaryText = LocalizedText.MAINLOAD10B;
		} else if (mainLoadState == 30) {
			if (js5MasterIndex == null) {
				js5MasterIndex = new Js5MasterIndex(js5NetQueue, js5CacheQueue);
			}
			if (js5MasterIndex.isReady()) {
				js5Archive0 = createJs5(false, true, true, 0);
				js5Archive1 = createJs5(false, true, true, 1);
				js5Archive2 = createJs5(true, true, false, 2);
				js5Archive3 = createJs5(false, true, true, 3);
				js5Archive4 = createJs5(false, true, true, 4);
				js5Archive5 = createJs5(true, true, true, 5);
				js5Archive6 = createJs5(true, false, true, 6);
				js5Archive7 = createJs5(false, true, true, 7);
				js5Archive8 = createJs5(false, true, true, 8);
				js5Archive9 = createJs5(false, true, true, 9);
				js5Archive10 = createJs5(false, true, true, 10);
				js5Archive11 = createJs5(false, true, true, 11);
				js5Archive12 = createJs5(false, true, true, 12);
				js5Archive13 = createJs5(false, true, true, 13);
				js5Archive14 = createJs5(false, false, true, 14);
				js5Archive15 = createJs5(false, true, true, 15);
				js5Archive16 = createJs5(false, true, true, 16);
				js5Archive17 = createJs5(false, true, true, 17);
				js5Archive18 = createJs5(false, true, true, 18);
				js5Archive19 = createJs5(false, true, true, 19);
				js5Archive20 = createJs5(false, true, true, 20);
				js5Archive21 = createJs5(false, true, true, 21);
				js5Archive22 = createJs5(false, true, true, 22);
				js5Archive23 = createJs5(true, true, true, 23);
				js5Archive24 = createJs5(false, true, true, 24);
				js5Archive25 = createJs5(false, true, true, 25);
				js5Archive26 = createJs5(true, true, true, 26);
				js5Archive27 = createJs5(false, true, true, 27);
				mainLoadPercentage = 15;
				mainLoadSecondaryText = LocalizedText.MAINLOAD30B;
				mainLoadState = 40;
			} else {
				mainLoadSecondaryText = LocalizedText.MAINLOAD30;
				mainLoadPercentage = 12;
			}
		} else if (mainLoadState == 40) {
			percentage = 0;
			for (i = 0; i < 28; i++) {
				percentage += js5Providers[i].getIndexPercentageComplete() * JS5_ARCHIVE_WEIGHTS[i] / 100;
			}
			if (percentage == 100) {
				mainLoadPercentage = 20;
				mainLoadSecondaryText = LocalizedText.MAINLOAD40B;
				Sprites.init(js5Archive8);
				TitleScreen.init(js5Archive8);
				Flames.init(js5Archive8);
				mainLoadState = 45;
			} else {
				if (percentage != 0) {
					mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.CHECKING_FOR_UPDATES, JagString.parseInt(percentage), JagString.PERCENT_SIGN});
				}
				mainLoadPercentage = 20;
			}
		} else if (mainLoadState == 45) {
			AudioChannel.init(Preferences.stereo);
			musicStream = new MidiPcmStream();
			musicStream.init();
			musicChannel = AudioChannel.create(GlobalConfig.AUDIO_SAMPLE_RATE, GameShell.signLink, GameShell.canvas, 0);
			musicChannel.setStream(musicStream);
			MidiPlayer.init(musicStream, js5Archive15, js5Archive14, js5Archive4);
			soundChannel = AudioChannel.create(2048, GameShell.signLink, GameShell.canvas, 1);
			soundStream = new MixerPcmStream();
			soundChannel.setStream(soundStream);
			resampler = new PcmResampler(GlobalConfig.AUDIO_SAMPLE_RATE, AudioChannel.sampleRate);
			MusicPlayer.titleSong = js5Archive6.getGroupId(TITLE_SONG);
			mainLoadPercentage = 30;
			mainLoadState = 50;
			mainLoadSecondaryText = LocalizedText.MAINLOAD45B;
		} else if (mainLoadState == 50) {
			percentage = Fonts.getReady(js5Archive8, js5Archive13);
			i = Fonts.getTotal();
			if (percentage >= i) {
				mainLoadSecondaryText = LocalizedText.MAINLOAD50B;
				mainLoadPercentage = 35;
				mainLoadState = 60;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD50, JagString.parseInt(percentage * 100 / i), JagString.PERCENT_SIGN});
				mainLoadPercentage = 35;
			}
		} else if (mainLoadState == 60) {
			percentage = TitleScreen.getReady(js5Archive8);
			i = TitleScreen.getTotal();
			if (i <= percentage) {
				mainLoadSecondaryText = LocalizedText.MAINLOAD60B;
				mainLoadState = 65;
				mainLoadPercentage = 40;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD60, JagString.parseInt(percentage * 100 / i), JagString.PERCENT_SIGN});
				mainLoadPercentage = 40;
			}
		} else if (mainLoadState == 65) {
			Fonts.load(js5Archive13, js5Archive8);
			mainLoadPercentage = 45;
			mainLoadSecondaryText = LocalizedText.MAINLOAD65B;
			setGameState(5);
			mainLoadState = 70;
		} else if (mainLoadState == 70) {
			js5Archive2.fetchAll();
			percentage = js5Archive2.getPercentageComplete();
			js5Archive16.fetchAll();
			percentage += js5Archive16.getPercentageComplete();
			js5Archive17.fetchAll();
			percentage += js5Archive17.getPercentageComplete();
			js5Archive18.fetchAll();
			percentage += js5Archive18.getPercentageComplete();
			js5Archive19.fetchAll();
			percentage += js5Archive19.getPercentageComplete();
			js5Archive20.fetchAll();
			percentage += js5Archive20.getPercentageComplete();
			js5Archive21.fetchAll();
			percentage += js5Archive21.getPercentageComplete();
			js5Archive22.fetchAll();
			percentage += js5Archive22.getPercentageComplete();
			js5Archive24.fetchAll();
			percentage += js5Archive24.getPercentageComplete();
			js5Archive25.fetchAll();
			percentage += js5Archive25.getPercentageComplete();
			js5Archive27.fetchAll();
			percentage += js5Archive27.getPercentageComplete();
			if (percentage >= 1100) {
				ParamTypeList.init(js5Archive2);
				FloTypeList.init(js5Archive2);
				FluTypeList.init(js5Archive2);
				IdkTypeList.init(js5Archive7, js5Archive2);
				LocTypeList.init(js5Archive16, js5Archive7);
				NpcTypeList.init(js5Archive7, js5Archive18);
				ObjTypeList.init(js5Archive19, Fonts.p11FullSoftware, js5Archive7);
				StructTypeList.init(js5Archive2);
				SeqTypeList.init(js5Archive1, js5Archive20, js5Archive0);
				BasTypeList.init(js5Archive2);
				SpotAnimTypeList.init(js5Archive7, js5Archive21);
				VarbitTypeList.init(js5Archive22);
				VarpTypeList.init(js5Archive2);
				InterfaceList.init(js5Archive13, js5Archive8, js5Archive3, js5Archive7);
				InvTypeList.init(js5Archive2);
				EnumTypeList.init(js5Archive17);
				QuickChatPhraseTypeList.init(js5Archive25, js5Archive24, new Js5QuickChatCommandDecoder());
				QuickChatCatTypeList.init(js5Archive25, js5Archive24);
				LightTypeList.init(js5Archive2);
				CursorTypeList.init(js5Archive2, js5Archive8);
				MsiTypeList.init(js5Archive2, js5Archive8);
				mainLoadPercentage = 50;
				mainLoadSecondaryText = LocalizedText.MAINLOAD70B;
				Equipment.init();
				mainLoadState = 80;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD70, JagString.parseInt(percentage / 11), JagString.PERCENT_SIGN});
				mainLoadPercentage = 50;
			}
		} else if (mainLoadState == 80) {
			percentage = Sprites.getReady(js5Archive8);
			i = Sprites.total();
			if (i > percentage) {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD80, JagString.parseInt(percentage * 100 / i), JagString.PERCENT_SIGN});
				mainLoadPercentage = 60;
			} else {
				Sprites.load(js5Archive8);
				mainLoadState = 90;
				mainLoadPercentage = 60;
				mainLoadSecondaryText = LocalizedText.MAINLOAD80B;
			}
		} else if (mainLoadState == 90) {
			if (js5Archive26.fetchAll()) {
				@Pc(951) Js5GlTextureProvider textureProvider = new Js5GlTextureProvider(js5Archive9, js5Archive26, js5Archive8, 20, !Preferences.highDetailTextures);
				Rasteriser.unpackTextures(textureProvider);
				if (Preferences.brightness == 1) {
					Rasteriser.setBrightness(0.9F);
				}
				if (Preferences.brightness == 2) {
					Rasteriser.setBrightness(0.8F);
				}
				if (Preferences.brightness == 3) {
					Rasteriser.setBrightness(0.7F);
				}
				if (Preferences.brightness == 4) {
					Rasteriser.setBrightness(0.6F);
				}
				mainLoadSecondaryText = LocalizedText.MAINLOAD90B;
				mainLoadState = 100;
				mainLoadPercentage = 70;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD90, JagString.parseInt(js5Archive26.getPercentageComplete()), JagString.PERCENT_SIGN});
				mainLoadPercentage = 70;
			}
		} else if (mainLoadState == 100) {
			if (Flames.isReady(js5Archive8)) {
				mainLoadState = 110;
			}
		} else if (mainLoadState == 110) {
			MouseRecorder.instance = new MouseRecorder();
			GameShell.signLink.startThread(10, MouseRecorder.instance);
			mainLoadSecondaryText = LocalizedText.MAINLOAD110B;
			mainLoadPercentage = 75;
			mainLoadState = 120;
		} else if (mainLoadState == 120) {
			if (js5Archive10.isFileReady(JagString.EMPTY, HUFFMAN_GROUP)) {
				@Pc(1060) HuffmanCodec codec = new HuffmanCodec(js5Archive10.fetchFile(JagString.EMPTY, HUFFMAN_GROUP));
				WordPack.init(codec);
				mainLoadSecondaryText = LocalizedText.MAINLOAD120B;
				mainLoadState = 130;
				mainLoadPercentage = 80;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD120, ZERO_PERCENT});
				mainLoadPercentage = 80;
			}
		} else if (mainLoadState == 130) {
			if (!js5Archive3.fetchAll()) {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD130, JagString.parseInt(js5Archive3.getPercentageComplete() * 3 / 4), JagString.PERCENT_SIGN});
				mainLoadPercentage = 85;
			} else if (!js5Archive12.fetchAll()) {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD130, JagString.parseInt(js5Archive12.getPercentageComplete() / 10 + 75), JagString.PERCENT_SIGN});
				mainLoadPercentage = 85;
			} else if (!js5Archive13.fetchAll()) {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD130, JagString.parseInt(js5Archive13.getPercentageComplete() / 20 + 85), JagString.PERCENT_SIGN});
				mainLoadPercentage = 85;
			} else if (js5Archive23.isGroupReady(DETAILS)) {
				MapList.init(Sprites.mapfunctions, js5Archive23);
				mainLoadPercentage = 95;
				mainLoadSecondaryText = LocalizedText.MAINLOAD130B;
				mainLoadState = 135;
			} else {
				mainLoadSecondaryText = JagString.concatenate(new JagString[]{LocalizedText.MAINLOAD130, JagString.parseInt(js5Archive23.getPercentageComplete(DETAILS) / 10 + 90), JagString.PERCENT_SIGN});
				mainLoadPercentage = 85;
			}
		} else if (mainLoadState == 135) {
			percentage = WorldList.fetch();
			if (percentage == -1) {
				mainLoadPercentage = 95;
				mainLoadSecondaryText = LocalizedText.MAINLOAD135;
			} else if (percentage == 7 || percentage == 9) {
				this.error("worldlistfull");
				setGameState(1000);
			} else if (WorldList.loaded) {
				mainLoadSecondaryText = LocalizedText.MAINLOAD135B;
				mainLoadState = 140;
				mainLoadPercentage = 96;
			} else {
				this.error("worldlistio_" + percentage);
				setGameState(1000);
			}
		} else if (mainLoadState == 140) {
			LoginManager.loginScreenId = js5Archive3.getGroupId(LOGINSCREEN);
			js5Archive5.discardNames(false);
			js5Archive6.discardNames(true);
			js5Archive8.discardNames(true);
			js5Archive13.discardNames(true);
			js5Archive10.discardNames(true);
			js5Archive3.discardNames(true);
			mainLoadPercentage = 97;
			mainLoadSecondaryText = LocalizedText.MAINLOAD140;
			mainLoadState = 150;
			clean = true;
		} else if (mainLoadState == 150) {
			MaterialManager.initNoiseTextures();
			if (Preferences.safeMode) {
				Preferences.windowMode = 0;
				Preferences.antiAliasingMode = 0;
				Preferences.favoriteWorlds = 0;
				Preferences.buildArea = 0;
			}
			Preferences.safeMode = true;
			Preferences.write(GameShell.signLink);
			DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
			mainLoadPercentage = 100;
			mainLoadState = 160;
			mainLoadSecondaryText = LocalizedText.MAINLOAD150B;
		} else if (mainLoadState == 160) {
			InterfaceList.resetToLoginScreen(true);
		}
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(B)V")
	@Override
	protected final void mainLoop() {
		if (gameState == 1000) {
			return;
		}
		loop++;
		if (loop % 1000 == 1) {
			@Pc(24) GregorianCalendar gregorianCalendar = new GregorianCalendar();
			MiniMenu.gregorianDateSeed = gregorianCalendar.get(Calendar.HOUR_OF_DAY) * 600 + gregorianCalendar.get(Calendar.MINUTE) * 10 + gregorianCalendar.get(Calendar.SECOND) / 6;
			aRandom1.setSeed(MiniMenu.gregorianDateSeed);
			PluginRepository.Update();
		}
		this.js5NetworkLoop();
		if (js5MasterIndex != null) {
			js5MasterIndex.processResourceProviders();
		}
		MidiPlayer.loop();
		audioLoop();
		Keyboard.loop();
		Mouse.loop();
		if (GlRenderer.enabled) {
			GlCleaner.process();
		}
		if (mouseWheel != null) {
			@Pc(75) int wheelRotation = mouseWheel.getRotation();
			MouseWheel.wheelRotation = wheelRotation;
		}
		if (gameState == 0) {
			this.mainLoad();
			GameShell.resetTimer();
		} else if (gameState == 5) {
			this.mainLoad();
			GameShell.resetTimer();
		} else if (gameState == 25 || gameState == 28) {
			LoginManager.rebuildMap();
		}
		if (gameState == 10) {
			this.mainUpdate();
			CreateManager.loop();
			LoginManager.loopAuto();
			LoginManager.loop();
		} else if (gameState == 30) {
			Protocol.loop();
		} else if (gameState == 40) {
			LoginManager.loop();
			if (LoginManager.reply != -3) {
				if (LoginManager.reply == 15) {
					LoginManager.reconnect();
				} else if (LoginManager.reply != 2) {
					LoginManager.processLogout();
				}
			}
		}
	}
}
