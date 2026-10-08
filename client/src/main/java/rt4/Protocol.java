package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Protocol {
	@OriginalMember(owner = "client!eg", name = "e", descriptor = "Lclient!i;")
	public static final Packet inboundBuffer = new Packet(65536);
	@OriginalMember(owner = "client!wb", name = "f", descriptor = "Lclient!wa;")
	public static final Buffer chatBuffer = new Buffer(new byte[5000]);
	@OriginalMember(owner = "client!ag", name = "P", descriptor = "Lclient!i;")
	public static final Packet outboundBuffer = new Packet(5000);
	@OriginalMember(owner = "client!eb", name = "p", descriptor = "[I")
	public static final int[] removedIds = new int[1000];
	@OriginalMember(owner = "client!dh", name = "d", descriptor = "[I")
	public static final int[] extendedIds = new int[2048];
	@OriginalMember(owner = "client!ta", name = "z", descriptor = "[I")
	public static final int[] PACKET_LENGTHS = new int[]{-1, 0, 8, 0, 2, 0, 0, 0, 0, 12, 0, 1, 0, 3, 7, 0, 15, 6, 0, 0, 4, 7, -2, -1, 2, 0, 2, 8, 0, 0, 0, 0, -2, 5, 0, 0, 8, 3, 6, 0, 0, 0, -1, 0, -1, 0, 0, 6, -2, 0, 12, 0, 0, 0, -1, -2, 10, 0, 0, 0, 3, 0, -1, 0, 0, 5, 6, 0, 0, 8, -1, -1, 0, 8, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 6, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, -2, 0, 0, 0, 0, 0, 12, 2, 0, -2, -2, 20, 0, 0, 10, 0, 15, 0, -1, 0, 8, -2, 0, 0, 0, 8, 0, 12, 0, 0, 7, 0, 0, 0, 0, 0, -1, -1, 0, 4, 5, 0, 0, 0, 6, 0, 0, 0, 0, 8, 9, 0, 0, 0, 2, -1, 0, -2, 0, 4, 14, 0, 0, 0, 24, 0, -2, 5, 0, 0, 0, 10, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 2, 1, 0, 0, 2, -1, 1, 0, 0, 0, 0, 14, 0, 0, 0, 0, 10, 5, 0, 0, 0, 0, 0, -2, 0, 0, 9, 0, 0, 8, 0, 0, 0, 0, -2, 6, 0, 0, 0, -2, 0, 3, 0, 1, 7, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 3, 0, 0};
	@OriginalMember(owner = "client!tl", name = "f", descriptor = "Lclient!na;")
	public static final JagString ASSIST = JagString.parse(":assist:");
	@OriginalMember(owner = "client!pl", name = "f", descriptor = "Lclient!na;")
	public static final JagString TRADEREQ = JagString.parse(":tradereq:");
	@OriginalMember(owner = "client!lb", name = "s", descriptor = "Lclient!na;")
	public static final JagString CLAN = JagString.parse(":clan:");
	@OriginalMember(owner = "client!ij", name = "a", descriptor = "Lclient!na;")
	public static final JagString DUELFRIEND = JagString.parse(":duelfriend:");
	@OriginalMember(owner = "client!km", name = "Sc", descriptor = "Lclient!na;")
	public static final JagString TRADE = JagString.parse(":trade:");
	@OriginalMember(owner = "client!pb", name = "x", descriptor = "[[[I")
	public static final int[][][] buildAreaChunks = new int[4][13][13];
	@OriginalMember(owner = "client!fc", name = "f", descriptor = "Lclient!na;")
	public static final JagString IMG0 = JagString.parse("<img=0>");
	@OriginalMember(owner = "client!en", name = "h", descriptor = "Lclient!na;")
	public static final JagString CHALREQ = JagString.parse(":chalreq:");
	@OriginalMember(owner = "client!ef", name = "f", descriptor = "Lclient!na;")
	public static final JagString DUELSTAKE = JagString.parse(":duelstake:");
	@OriginalMember(owner = "client!wd", name = "e", descriptor = "Lclient!na;")
	public static final JagString COOKIE_PREFIX_KEY = JagString.parse("cookieprefix");
	@OriginalMember(owner = "client!u", name = "g", descriptor = "Lclient!na;")
	public static final JagString COOKIE_HOST_KEY = JagString.parse("cookiehost");
	@OriginalMember(owner = "client!lc", name = "m", descriptor = "Lclient!na;")
	public static final JagString COOKIE_SETTINGS_PREFIX = JagString.parse("settings=");
	@OriginalMember(owner = "client!wh", name = "n", descriptor = "Lclient!na;")
	public static final JagString COOKIE_PATH_DOMAIN = JagString.parse("; version=1; path=)4; domain=");
	@OriginalMember(owner = "client!tm", name = "d", descriptor = "Lclient!na;")
	public static final JagString COOKIE_EXPIRE_IMMEDIATELY = JagString.parse("; Expires=Thu)1 01)2Jan)21970 00:00:00 GMT; Max)2Age=0");
	@OriginalMember(owner = "client!vg", name = "h", descriptor = "Lclient!na;")
	public static final JagString COOKIE_EXPIRES_PREFIX = JagString.parse("; Expires=");
	@OriginalMember(owner = "client!s", name = "g", descriptor = "Lclient!na;")
	public static final JagString COOKIE_SET_JS = JagString.parse("document)3cookie=(R");
	@OriginalMember(owner = "client!sc", name = "f", descriptor = "Lclient!na;")
	public static final JagString COOKIE_SUFFIX = JagString.parse("(R");
	@OriginalMember(owner = "client!fb", name = "i", descriptor = "Lclient!na;")
	public static final JagString COOKIE_MAX_AGE_PREFIX = JagString.parse("; Max)2Age=");
	@OriginalMember(owner = "client!ch", name = "z", descriptor = "[I")
	public static final int[] cameraShakePhase = new int[5];
	@OriginalMember(owner = "client!pg", name = "db", descriptor = "Lclient!na;")
	public static final JagString ASSISTREQ = JagString.parse(":assistreq:");
	@OriginalMember(owner = "client!rj", name = "ab", descriptor = "Lclient!na;")
	public static final JagString CLAN_REQUEST_SUFFIX = JagString.parse(":clanreq:");
	@OriginalMember(owner = "client!na", name = "cb", descriptor = "Lclient!na;")
	public static final JagString ALLY_REQUEST_SUFFIX = JagString.parse(":allyreq:");
	@OriginalMember(owner = "client!dh", name = "i", descriptor = "Lclient!na;")
	public static final JagString IMG1 = JagString.parse("<img=1>");
	@OriginalMember(owner = "client!jk", name = "B", descriptor = "Lclient!ma;")
	public static BufferedSocket socket;
	@OriginalMember(owner = "client!fl", name = "C", descriptor = "Lsignlink!im;")
	public static PrivilegedRequest socketRequest;
	@OriginalMember(owner = "client!pe", name = "a", descriptor = "I")
	public static int verifyId = 0;
	@OriginalMember(owner = "client!na", name = "l", descriptor = "I")
	public static int opcode = 0;
	@OriginalMember(owner = "client!sc", name = "o", descriptor = "I")
	public static int length = 0;
	@OriginalMember(owner = "client!jb", name = "m", descriptor = "I")
	public static int extendedCount = 0;
	@OriginalMember(owner = "client!tg", name = "h", descriptor = "I")
	public static int removedCount = 0;
	@OriginalMember(owner = "client!qi", name = "t", descriptor = "I")
	public static int mouseIdleSamples = 0;
	@OriginalMember(owner = "client!ck", name = "eb", descriptor = "Z")
	public static boolean verifyIdChanged = false;
	@OriginalMember(owner = "client!dm", name = "q", descriptor = "I")
	public static int opcode4 = 0;
	@OriginalMember(owner = "client!af", name = "k", descriptor = "I")
	public static int opcode3 = 0;
	@OriginalMember(owner = "client!sj", name = "t", descriptor = "I")
	public static int opcode2 = 0;
	@OriginalMember(owner = "client!bj", name = "r", descriptor = "I")
	public static int cameraYOffsetDelta = 2;
	@OriginalMember(owner = "client!dg", name = "h", descriptor = "Lclient!be;")
	public static Component tooltipComponent;
	@OriginalMember(owner = "client!ld", name = "i", descriptor = "I")
	public static int cameraJitterTimer = 0;
	@OriginalMember(owner = "client!kf", name = "l", descriptor = "I")
	public static int tooltipTimer = 0;
	@OriginalMember(owner = "client!pm", name = "ab", descriptor = "Z")
	public static boolean cameraPositionChanged = true;
	@OriginalMember(owner = "client!vl", name = "k", descriptor = "I")
	public static int logoutOnDisconnectTimer = 0;
	@OriginalMember(owner = "client!t", name = "l", descriptor = "Lclient!ma;")
	public static BufferedSocket previousSocket;
	@OriginalMember(owner = "client!od", name = "i", descriptor = "I")
	public static int sceneDelta = 0;
	@OriginalMember(owner = "client!bf", name = "G", descriptor = "I")
	public static int cameraSendCooldown = 0;
	@OriginalMember(owner = "client!pb", name = "ab", descriptor = "I")
	public static int viewportWalkState = 0;
	@OriginalMember(owner = "client!fe", name = "R", descriptor = "Z")
	public static boolean prevFocus = true;
	@OriginalMember(owner = "client!rm", name = "c", descriptor = "I")
	public static int yawDriftDelta = 1;
	@OriginalMember(owner = "client!cj", name = "n", descriptor = "Lsignlink!im;")
	public static PrivilegedRequest openUrlRequest;
	@OriginalMember(owner = "client!na", name = "W", descriptor = "Z")
	public static boolean newTab;
	@OriginalMember(owner = "client!kd", name = "ob", descriptor = "I")
	public static int ticksSinceWrite = 0;

	@OriginalMember(owner = "client!g", name = "b", descriptor = "(B)V")
	public static void readZonePacket() {
		if (opcode == ServerProt.LOC_DEL) {
			int packed = inboundBuffer.g1neg();
			int rotation = packed & 0x3;
			int shape = packed >> 2;
			int layer = Loc.LAYERS[shape];
			int coords = inboundBuffer.g1();
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int tileY = (coords & 0x7) + SceneGraph.currentChunkY;
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				ChangeLocRequest.push(Player.plane, tileY, rotation, tileX, -1, -1, layer, shape, 0);
			}
		} else if (opcode == ServerProt.OBJ_REVEAL) {
			int objId = inboundBuffer.ig2();
			int coords = inboundBuffer.g1();
			int tileY = (coords & 0x7) + SceneGraph.currentChunkY;
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int amount = inboundBuffer.g2add();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				@Pc(122) ObjStack obj = new ObjStack();
				obj.amount = amount;
				obj.type = objId;
				if (SceneGraph.objStacks[Player.plane][tileX][tileY] == null) {
					SceneGraph.objStacks[Player.plane][tileX][tileY] = new LinkedList();
				}
				SceneGraph.objStacks[Player.plane][tileX][tileY].addTail(new ObjStackNode(obj));
				spawnGroundObject(tileY, tileX);
			}
		} else if (opcode == ServerProt.MAP_PROJANIM_3) {
			int coords = inboundBuffer.g1();
			int srcX = SceneGraph.currentChunkX * 2 + (coords >> 4 & 0xF);
			int srcY = (coords & 0xF) + SceneGraph.currentChunkY * 2;
			int dstX = srcX + inboundBuffer.g1b();
			int dstY = inboundBuffer.g1b() + srcY;
			int targetId = inboundBuffer.g2b();
			int spotAnimId = inboundBuffer.g2();
			int srcHeight = inboundBuffer.g1() * 4;
			int dstHeight = inboundBuffer.g1() * 4;
			int startDelay = inboundBuffer.g2();
			int endDelay = inboundBuffer.g2();
			int startAngle = inboundBuffer.g1();
			if (startAngle == 255) {
				startAngle = -1;
			}
			int arc = inboundBuffer.g1();
			if (srcX >= 0 && srcY >= 0 && srcX < 208 && srcY < 208 && dstX >= 0 && dstY >= 0 && dstX < 208 && dstY < 208 && spotAnimId != 65535) {
				dstY *= 64;
				dstX = dstX * 64;
				srcY = srcY * 64;
				srcX = srcX * 64;
				ProjAnim proj = new ProjAnim(spotAnimId, Player.plane, srcX, srcY, SceneGraph.getTileHeight(Player.plane, srcX, srcY) - srcHeight, client.loop + startDelay, endDelay + client.loop, startAngle, arc, targetId, dstHeight);
				proj.setTarget(dstY, client.loop + startDelay, -dstHeight + SceneGraph.getTileHeight(Player.plane, dstX, dstY), dstX);
				SceneGraph.projectiles.addTail(new ProjAnimNode(proj));
			}
		} else if (opcode == ServerProt.SPOTANIM_SPECIFIC) {
			int coords = inboundBuffer.g1();
			int tileX = SceneGraph.currentChunkX + (coords >> 4 & 0x7);
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int spotAnimId = inboundBuffer.g2();
			int height = inboundBuffer.g1();
			int delay = inboundBuffer.g2();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				tileX = tileX * 128 + 64;
				tileY = tileY * 128 + 64;
				@Pc(427) SpotAnim spotAnim = new SpotAnim(spotAnimId, Player.plane, tileX, tileY, SceneGraph.getTileHeight(Player.plane, tileX, tileY) - height, delay, client.loop);
				SceneGraph.spotanims.addTail(new SpotAnimNode(spotAnim));
			}
		} else if (opcode == ServerProt.LOC_ADD) {
			int packed = inboundBuffer.g1add();
			int shape = packed >> 2;
			int rotation = packed & 0x3;
			int layer = Loc.LAYERS[shape];
			int coords = inboundBuffer.g1();
			int tileX = SceneGraph.currentChunkX + (coords >> 4 & 0x7);
			int tileY = (coords & 0x7) + SceneGraph.currentChunkY;
			int locId = inboundBuffer.g2add();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				ChangeLocRequest.push(Player.plane, tileY, rotation, tileX, -1, locId, layer, shape, 0);
			}
		} else if (opcode == ServerProt.LOC_ANIM) {
			int coords = inboundBuffer.g1sub();
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int packed = inboundBuffer.g1sub();
			int shape = packed >> 2;
			int rotation = packed & 0x3;
			int layer = Loc.LAYERS[shape];
			int seqId = inboundBuffer.ig2();
			if (seqId == 65535) {
				seqId = -1;
			}
			SceneGraph.addLoc(Player.plane, rotation, shape, tileY, layer, tileX, seqId);
		} else if (opcode == ServerProt.LOC_ADD_CHANGE) {
			int packed = inboundBuffer.g1();
			int shape = packed >> 2;
			int rotation = packed & 0x3;
			int coords = inboundBuffer.g1();
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int tileY = (coords & 0x7) + SceneGraph.currentChunkY;
			@Pc(605) byte xFine = inboundBuffer.g1badd();
			@Pc(609) byte yFine = inboundBuffer.g1badd();
			@Pc(613) byte zFine = inboundBuffer.g1bsub();
			int locId = inboundBuffer.g2add();
			int seqId = inboundBuffer.ig2();
			@Pc(625) byte tileAlign = inboundBuffer.g1b();
			int duration = inboundBuffer.g2();
			int ambience = inboundBuffer.ig2badd();
			if (!GlRenderer.enabled) {
				AttachLocRequest.push(tileAlign, duration, ambience, seqId, tileY, zFine, rotation, xFine, tileX, shape, yFine, locId);
			}
		} else if (opcode == ServerProt.OBJ_COUNT) {
			int coords = inboundBuffer.g1();
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int objId = inboundBuffer.g2();
			int oldAmount = inboundBuffer.g2();
			int newAmount = inboundBuffer.g2();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				@Pc(710) LinkedList stack = SceneGraph.objStacks[Player.plane][tileX][tileY];
				if (stack != null) {
					for (@Pc(718) ObjStackNode node = (ObjStackNode) stack.head(); node != null; node = (ObjStackNode) stack.next()) {
						@Pc(723) ObjStack obj = node.value;
						if ((objId & 0x7FFF) == obj.type && oldAmount == obj.amount) {
							obj.amount = newAmount;
							break;
						}
					}
					spawnGroundObject(tileY, tileX);
				}
			}
		} else if (opcode == ServerProt.OBJ_ADD) {
			int ownerId = inboundBuffer.ig2add();
			int coords = inboundBuffer.g1neg();
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int amount = inboundBuffer.ig2();
			int objId = inboundBuffer.ig2();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104 && PlayerList.selfId != ownerId) {
				@Pc(812) ObjStack obj = new ObjStack();
				obj.amount = amount;
				obj.type = objId;
				if (SceneGraph.objStacks[Player.plane][tileX][tileY] == null) {
					SceneGraph.objStacks[Player.plane][tileX][tileY] = new LinkedList();
				}
				SceneGraph.objStacks[Player.plane][tileX][tileY].addTail(new ObjStackNode(obj));
				spawnGroundObject(tileY, tileX);
			}
		} else if (opcode == ServerProt.MAP_PROJANIM_2) {
			int coords = inboundBuffer.g1();
			int srcX = SceneGraph.currentChunkX + (coords >> 4 & 0x7);
			int srcY = (coords & 0x7) + SceneGraph.currentChunkY;
			int dstX = srcX + inboundBuffer.g1b();
			int dstY = inboundBuffer.g1b() + srcY;
			int targetId = inboundBuffer.g2b();
			int spotAnimId = inboundBuffer.g2();
			int srcHeight = inboundBuffer.g1() * 4;
			int dstHeight = inboundBuffer.g1() * 4;
			int startDelay = inboundBuffer.g2();
			int endDelay = inboundBuffer.g2();
			int startAngle = inboundBuffer.g1();
			int arc = inboundBuffer.g1();
			if (startAngle == 255) {
				startAngle = -1;
			}
			if (srcX >= 0 && srcY >= 0 && srcX < 104 && srcY < 104 && dstX >= 0 && dstY >= 0 && dstX < 104 && dstY < 104 && spotAnimId != 65535) {
				dstY = dstY * 128 + 64;
				srcY = srcY * 128 + 64;
				srcX = srcX * 128 + 64;
				dstX = dstX * 128 + 64;
				ProjAnim proj = new ProjAnim(spotAnimId, Player.plane, srcX, srcY, SceneGraph.getTileHeight(Player.plane, srcX, srcY) - srcHeight, startDelay + client.loop, endDelay + client.loop, startAngle, arc, targetId, dstHeight);
				proj.setTarget(dstY, client.loop + startDelay, SceneGraph.getTileHeight(Player.plane, dstX, dstY) - dstHeight, dstX);
				SceneGraph.projectiles.addTail(new ProjAnimNode(proj));
			}
		} else if (opcode == ServerProt.MAP_PROJANIM) {
			int coords = inboundBuffer.g1();
			int srcY = SceneGraph.currentChunkY * 2 + (coords & 0xF);
			int srcX = SceneGraph.currentChunkX * 2 + (coords >> 4 & 0xF);
			int dstX = inboundBuffer.g1b() + srcX;
			int dstY = inboundBuffer.g1b() + srcY;
			int targetId = inboundBuffer.g2b();
			int srcTargetId = inboundBuffer.g2b();
			int spotAnimId = inboundBuffer.g2();
			int srcHeight = inboundBuffer.g1b();
			int dstHeight = inboundBuffer.g1() * 4;
			int startDelay = inboundBuffer.g2();
			int endDelay = inboundBuffer.g2();
			int startAngle = inboundBuffer.g1();
			int arc = inboundBuffer.g1();
			if (startAngle == 255) {
				startAngle = -1;
			}
			if (srcX >= 0 && srcY >= 0 && srcX < 208 && srcY < 208 && dstX >= 0 && dstY >= 0 && dstX < 208 && dstY < 208 && spotAnimId != 65535) {
				dstX = dstX * 64;
				srcX *= 64;
				dstY *= 64;
				srcY *= 64;
				if (targetId != 0) {
					@Pc(1194) int slotIdx;
					@Pc(1198) PathingEntity entity;
					@Pc(1184) int entityData;
					@Pc(1188) int entityIdx;
					if (targetId >= 0) {
						entityData = targetId - 1;
						entityIdx = entityData & 0x7FF;
						slotIdx = entityData >> 11 & 0xF;
						entity = NpcList.npcs[entityIdx];
					} else {
						entityData = -targetId - 1;
						slotIdx = entityData >> 11 & 0xF;
						entityIdx = entityData & 0x7FF;
						if (PlayerList.selfId == entityIdx) {
							entity = PlayerList.self;
						} else {
							entity = PlayerList.players[entityIdx];
						}
					}
					if (entity != null) {
						@Pc(1232) BasType basType = entity.getBasType();
						if (basType.modelRotateTranslate != null && basType.modelRotateTranslate[slotIdx] != null) {
							entityIdx = basType.modelRotateTranslate[slotIdx][0];
							srcHeight -= basType.modelRotateTranslate[slotIdx][1];
							@Pc(1264) int zOffset = basType.modelRotateTranslate[slotIdx][2];
							@Pc(1269) int sinAngle = MathUtils.sin[entity.currentAngle];
							@Pc(1274) int cosAngle = MathUtils.cos[entity.currentAngle];
							@Pc(1284) int rotatedX = entityIdx * cosAngle + zOffset * sinAngle >> 16;
							@Pc(1295) int rotatedZ = cosAngle * zOffset - entityIdx * sinAngle >> 16;
							srcY += rotatedZ;
							srcX += rotatedX;
						}
					}
				}
				@Pc(1331) ProjAnim proj = new ProjAnim(spotAnimId, Player.plane, srcX, srcY, SceneGraph.getTileHeight(Player.plane, srcX, srcY) - srcHeight, startDelay + client.loop, endDelay + client.loop, startAngle, arc, srcTargetId, dstHeight);
				proj.setTarget(dstY, startDelay + client.loop, -dstHeight + SceneGraph.getTileHeight(Player.plane, dstX, dstY), dstX);
				SceneGraph.projectiles.addTail(new ProjAnimNode(proj));
			}
		} else if (opcode == ServerProt.SOUND_AREA) {
			int coords = inboundBuffer.g1();
			int tileX = SceneGraph.currentChunkX + (coords >> 4 & 0x7);
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int soundId = inboundBuffer.g2();
			if (soundId == 65535) {
				soundId = -1;
			}
			int packed = inboundBuffer.g1();
			int radius = packed >> 4 & 0xF;
			int delay = inboundBuffer.g1();
			int loops = packed & 0x7;
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				int range = radius + 1;
				if (PlayerList.self.movementQueueX[0] >= tileX - range && range + tileX >= PlayerList.self.movementQueueX[0] && PlayerList.self.movementQueueY[0] >= tileY - range && PlayerList.self.movementQueueY[0] <= range + tileY && Preferences.ambientSoundsVolume != 0 && loops > 0 && SoundPlayer.size < 50 && soundId != -1) {
					SoundPlayer.ids[SoundPlayer.size] = soundId;
					SoundPlayer.loops[SoundPlayer.size] = loops;
					SoundPlayer.delays[SoundPlayer.size] = delay;
					SoundPlayer.sounds[SoundPlayer.size] = null;
					SoundPlayer.positions[SoundPlayer.size] = radius + (tileX << 16) + (tileY << 8);
					SoundPlayer.size++;
				}
			}
		} else if (opcode == ServerProt.OBJ_DEL) {
			int coords = inboundBuffer.g1sub();
			int tileY = SceneGraph.currentChunkY + (coords & 0x7);
			int tileX = (coords >> 4 & 0x7) + SceneGraph.currentChunkX;
			int objId = inboundBuffer.g2();
			if (tileX >= 0 && tileY >= 0 && tileX < 104 && tileY < 104) {
				@Pc(1565) LinkedList stack = SceneGraph.objStacks[Player.plane][tileX][tileY];
				if (stack != null) {
					for (@Pc(1572) ObjStackNode node = (ObjStackNode) stack.head(); node != null; node = (ObjStackNode) stack.next()) {
						if (node.value.type == (objId & 0x7FFF)) {
							node.unlink();
							break;
						}
					}
					if (stack.head() == null) {
						SceneGraph.objStacks[Player.plane][tileX][tileY] = null;
					}
					spawnGroundObject(tileY, tileX);
				}
			}
		}
	}

	@OriginalMember(owner = "client!g", name = "a", descriptor = "(IZ)V")
	public static void readRebuildPacket(@OriginalArg(1) boolean dynamic) {
		SceneGraph.dynamicMapRegion = dynamic;
		// localTileX
		@Pc(13) int var1;
		// 430:451 regionCount; 452:473 regionIndex; 480:547 zoneX
		@Pc(20) int var2;
		// 432:434 xteaIdx; 437:547 plane
		@Pc(26) int var3;
		// 433:434 keyIdx; 438:476 zoneX; 482:547 localTileY
		@Pc(31) int var4;
		// 439:476 zoneY; 485:496 level; 498:516 regionCount; 517:541 regionIndex
		@Pc(60) int var5;
		// 440:476 localTileY; 486:494 chunkX; 500:502 xteaIdx; 505:547 zoneY
		@Pc(64) int var6;
		// 457:471 gridX; 488:492 hasChunk; 519:520 chunkX
		@Pc(138) int var7;
		// 458:471 gridY; 520:523 chunkY
		@Pc(151) int var8;
		// packedCoord
		@Pc(169) int var9;
		if (!SceneGraph.dynamicMapRegion) {
			var1 = inboundBuffer.g2add();
			var2 = (length - inboundBuffer.offset) / 16;
			LoginManager.regionsXteaKeys = new int[var2][4];
			for (var3 = 0; var3 < var2; var3++) {
				for (var4 = 0; var4 < 4; var4++) {
					LoginManager.regionsXteaKeys[var3][var4] = inboundBuffer.mg4();
				}
			}
			var3 = inboundBuffer.g1sub();
			var4 = inboundBuffer.g2();
			var5 = inboundBuffer.g2add();
			var6 = inboundBuffer.g2add();
			LoginManager.regionBitPacked = new int[var2];
			LoginManager.mapFilesBuffer = new byte[var2][];
			LoginManager.npcSpawnsFilesBuffer = null;
			LoginManager.underWaterMapFileIds = new int[var2];
			LoginManager.locationMapFilesBuffer = new byte[var2][];
			LoginManager.underWaterLocationsMapFilesBuffer = new byte[var2][];
			LoginManager.npcSpawnsFileIds = null;
			LoginManager.mapFileIds = new int[var2];
			LoginManager.underWaterMapFilesBuffer = new byte[var2][];
			LoginManager.locationsMapFileIds = new int[var2];
			LoginManager.underWaterLocationsMapFileIds = new int[var2];
			var2 = 0;
			@Pc(100) boolean isBlacklisted = (var4 / 8 == 48 || var4 / 8 == 49) && var5 / 8 == 48;
			if (var4 / 8 == 48 && var5 / 8 == 148) {
				isBlacklisted = true;
			}
			for (var7 = (var4 - 6) / 8; var7 <= (var4 + 6) / 8; var7++) {
				for (var8 = (var5 - 6) / 8; var8 <= (var5 + 6) / 8; var8++) {
					var9 = (var7 << 8) + var8;
					if (isBlacklisted && (var8 == 49 || var8 == 149 || var8 == 147 || var7 == 50 || var7 == 49 && var8 == 47)) {
						LoginManager.regionBitPacked[var2] = var9;
						LoginManager.mapFileIds[var2] = -1;
						LoginManager.locationsMapFileIds[var2] = -1;
						LoginManager.underWaterMapFileIds[var2] = -1;
						LoginManager.underWaterLocationsMapFileIds[var2] = -1;
					} else {
						LoginManager.regionBitPacked[var2] = var9;
						LoginManager.mapFileIds[var2] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.MAP_PREFIX, JagString.parseInt(var7), LoginManager.UNDERSCORE, JagString.parseInt(var8)}));
						LoginManager.locationsMapFileIds[var2] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.LOC_PREFIX, JagString.parseInt(var7), LoginManager.UNDERSCORE, JagString.parseInt(var8)}));
						LoginManager.underWaterMapFileIds[var2] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.UNDERWATER_MAP_PREFIX, JagString.parseInt(var7), LoginManager.UNDERSCORE, JagString.parseInt(var8)}));
						LoginManager.underWaterLocationsMapFileIds[var2] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.UNDERWATER_LOC_PREFIX, JagString.parseInt(var7), LoginManager.UNDERSCORE, JagString.parseInt(var8)}));
					}
					var2++;
				}
			}
			LoginManager.loadRegion(var3, var5, var4, var6, false, var1);
			return;
		}
		var1 = inboundBuffer.ig2add();
		var2 = inboundBuffer.ig2add();
		var3 = inboundBuffer.g1sub();
		var4 = inboundBuffer.ig2add();
		inboundBuffer.accessBits();
		@Pc(391) int k;
		for (var5 = 0; var5 < 4; var5++) {
			for (var6 = 0; var6 < 13; var6++) {
				for (k = 0; k < 13; k++) {
					var7 = inboundBuffer.gBits(1);
					if (var7 == 1) {
						buildAreaChunks[var5][var6][k] = inboundBuffer.gBits(26);
					} else {
						buildAreaChunks[var5][var6][k] = -1;
					}
				}
			}
		}
		inboundBuffer.accessBytes();
		var5 = (length - inboundBuffer.offset) / 16;
		LoginManager.regionsXteaKeys = new int[var5][4];
		for (var6 = 0; var6 < var5; var6++) {
			for (k = 0; k < 4; k++) {
				LoginManager.regionsXteaKeys[var6][k] = inboundBuffer.mg4();
			}
		}
		var6 = inboundBuffer.g2();
		LoginManager.underWaterLocationsMapFileIds = new int[var5];
		LoginManager.locationsMapFileIds = new int[var5];
		LoginManager.mapFileIds = new int[var5];
		LoginManager.underWaterLocationsMapFilesBuffer = new byte[var5][];
		LoginManager.npcSpawnsFileIds = null;
		LoginManager.underWaterMapFileIds = new int[var5];
		LoginManager.locationMapFilesBuffer = new byte[var5][];
		LoginManager.mapFilesBuffer = new byte[var5][];
		LoginManager.regionBitPacked = new int[var5];
		LoginManager.npcSpawnsFilesBuffer = null;
		LoginManager.underWaterMapFilesBuffer = new byte[var5][];
		var5 = 0;
		for (k = 0; k < 4; k++) {
			for (var7 = 0; var7 < 13; var7++) {
				for (var8 = 0; var8 < 13; var8++) {
					var9 = buildAreaChunks[k][var7][var8];
					if (var9 != -1) {
						@Pc(555) int cx = var9 >> 14 & 0x3FF;
						@Pc(561) int cy = var9 >> 3 & 0x7FF;
						@Pc(571) int regionId = cy / 8 + (cx / 8 << 8);
						@Pc(573) int m;
						for (m = 0; m < var5; m++) {
							if (regionId == LoginManager.regionBitPacked[m]) {
								regionId = -1;
								break;
							}
						}
						if (regionId != -1) {
							LoginManager.regionBitPacked[var5] = regionId;
							@Pc(609) int rcy = regionId & 0xFF;
							m = regionId >> 8 & 0xFF;
							LoginManager.mapFileIds[var5] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.MAP_PREFIX, JagString.parseInt(m), LoginManager.UNDERSCORE, JagString.parseInt(rcy)}));
							LoginManager.locationsMapFileIds[var5] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.LOC_PREFIX, JagString.parseInt(m), LoginManager.UNDERSCORE, JagString.parseInt(rcy)}));
							LoginManager.underWaterMapFileIds[var5] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.UNDERWATER_MAP_PREFIX, JagString.parseInt(m), LoginManager.UNDERSCORE, JagString.parseInt(rcy)}));
							LoginManager.underWaterLocationsMapFileIds[var5] = client.js5Archive5.getGroupId(JagString.concatenate(new JagString[]{LoginManager.UNDERWATER_LOC_PREFIX, JagString.parseInt(m), LoginManager.UNDERSCORE, JagString.parseInt(rcy)}));
							var5++;
						}
					}
				}
			}
		}
		LoginManager.loadRegion(var3, var6, var2, var4, false, var1);
	}

	@OriginalMember(owner = "client!gk", name = "a", descriptor = "(IIBLclient!e;)V")
	public static void readExtendedPlayerInfo(@OriginalArg(0) int flags, @OriginalArg(1) int id, @OriginalArg(3) Player player) {
		// 559:596 chatColorEffect; 609:611 hitDamage; 616:621 seqId; 624:627 appearanceLen; 661:663 hitDamage; 666:670 slotAnimCount; 682:692 spotAnimId
		@Pc(13) int int1;
		// 560:599 chatType; 610:611 hitType; 620:621 seqDelay; 662:663 hitType; 686:696 spotAnimData
		@Pc(17) int int2;
		// 562:606 savedOffset; 699:701 spotAnimSeqId
		@Pc(24) int savedOffset;
		if ((flags & 0x80) != 0) {
			int1 = inboundBuffer.ig2();
			int2 = inboundBuffer.g1();
			@Pc(21) int chatLen = inboundBuffer.g1();
			savedOffset = inboundBuffer.offset;
			@Pc(35) boolean isQuickChat = (int1 & 0x8000) != 0;
			if (player.username != null && player.appearance != null) {
				@Pc(48) long username37 = player.username.encode37();
				@Pc(50) boolean ignore = false;
				if (int2 <= 1) {
					if (!isQuickChat && (LoginManager.playerUnderage && !LoginManager.parentalChatConsent || LoginManager.mapQuickChat)) {
						ignore = true;
					} else {
						for (@Pc(69) int idx = 0; idx < IgnoreList.size; idx++) {
							if (IgnoreList.encodedUsernames[idx] == username37) {
								ignore = true;
								break;
							}
						}
					}
				}
				if (!ignore && Player.inTutorialIsland == 0) {
					chatBuffer.offset = 0;
					inboundBuffer.igdata(chatLen, chatBuffer.data);
					chatBuffer.offset = 0;
					@Pc(106) int phraseId = -1;
					@Pc(127) JagString message;
					if (isQuickChat) {
						@Pc(112) QuickChatPhrase phrase = QuickChatPhraseType.decodePhrase(chatBuffer);
						int1 &= 0x7FFF;
						phraseId = phrase.id;
						message = phrase.type.decodeMessage(chatBuffer);
					} else {
						message = Font.escape(formatChatMessage(chatBuffer).encodeMessage());
					}
					player.chatMessage = message.trim();
					player.chatEffect = int1 & 0xFF;
					player.chatLoops = 150;
					player.chatColor = int1 >> 8;
					if (int2 == 2) {
						Chat.add(phraseId, isQuickChat ? 17 : 1, message, null, JagString.concatenate(new JagString[]{IMG1, player.getName()}));
					} else if (int2 == 1) {
						Chat.add(phraseId, isQuickChat ? 17 : 1, message, null, JagString.concatenate(new JagString[]{IMG0, player.getName()}));
					} else {
						Chat.add(phraseId, isQuickChat ? 17 : 2, message, null, player.getName());
					}
				}
			}
			inboundBuffer.offset = savedOffset + chatLen;
		}
		if ((flags & 0x1) != 0) {
			int1 = inboundBuffer.gsmarts();
			int2 = inboundBuffer.g1add();
			player.addHit(int2, client.loop, int1);
			player.hitpointsBarVisibleUntil = client.loop + 300;
			player.hitpointsBar = inboundBuffer.g1sub();
		}
		if ((flags & 0x8) != 0) {
			int1 = inboundBuffer.g2();
			if (int1 == 65535) {
				int1 = -1;
			}
			int2 = inboundBuffer.g1();
			Player.animate(int2, int1, player);
		}
		if ((flags & 0x4) != 0) {
			int1 = inboundBuffer.g1add();
			@Pc(309) byte[] bytes = new byte[int1];
			@Pc(314) Buffer buffer = new Buffer(bytes);
			inboundBuffer.gdata(int1, bytes);
			PlayerList.appearanceCache[id] = buffer;
			player.decodeAppearance(buffer);
		}
		if ((flags & 0x2) != 0) {
			player.faceEntity = inboundBuffer.g2add();
			if (player.faceEntity == 65535) {
				player.faceEntity = -1;
			}
		}
		if ((flags & 0x400) != 0) {
			player.forceMoveStartX = inboundBuffer.g1neg();
			player.forceMoveStartY = inboundBuffer.g1();
			player.forceMoveDestX = inboundBuffer.g1add();
			player.forceMoveDestY = inboundBuffer.g1();
			player.forceMoveCyclesToStart = inboundBuffer.ig2() + client.loop;
			player.forceMoveCyclesToDest = inboundBuffer.ig2() + client.loop;
			player.forceMoveDirection = inboundBuffer.g1neg();
			player.movementQueueSize = 1;
			player.seqMovementSteps = 0;
		}
		if ((flags & 0x20) != 0) {
			player.chatMessage = inboundBuffer.gjstr();
			if (player.chatMessage.charAt(0) == 126) {
				player.chatMessage = player.chatMessage.substring(1);
				Chat.add(player.getName(), 2, player.chatMessage);
			} else if (player == PlayerList.self) {
				Chat.add(player.getName(), 2, player.chatMessage);
			}
			player.chatEffect = 0;
			player.chatColor = 0;
			player.chatLoops = 150;
		}
		if ((flags & 0x200) != 0) {
			int1 = inboundBuffer.gsmarts();
			int2 = inboundBuffer.g1sub();
			player.addHit(int2, client.loop, int1);
		}
		if ((flags & 0x800) != 0) {
			int1 = inboundBuffer.g1neg();
			@Pc(502) int[] seqIds = new int[int1];
			@Pc(505) int[] delays = new int[int1];
			@Pc(508) int[] slotMasks = new int[int1];
			for (@Pc(510) int i = 0; i < int1; i++) {
				@Pc(521) int seqId = inboundBuffer.ig2();
				if (seqId == 65535) {
					seqId = -1;
				}
				seqIds[i] = seqId;
				delays[i] = inboundBuffer.g1add();
				slotMasks[i] = inboundBuffer.g2();
			}
			Player.applySlotAnimations(delays, seqIds, player, slotMasks);
		}
		if ((flags & 0x100) != 0) {
			int1 = inboundBuffer.ig2();
			if (int1 == 65535) {
				int1 = -1;
			}
			int2 = inboundBuffer.mg4();
			@Pc(573) boolean shouldApply = int1 == -1 || player.spotAnimId == -1 || SeqTypeList.get(SpotAnimTypeList.get(int1).seqId).priority >= SeqTypeList.get(SpotAnimTypeList.get(player.spotAnimId).seqId).priority;
			if (shouldApply) {
				player.spotAnimStart = (int2 & 0xFFFF) + client.loop;
				player.spotAnimDelayClock = 0;
				player.spotAnimFrame = 0;
				player.spotAnimId = int1;
				if (player.spotAnimStart > client.loop) {
					player.spotAnimFrame = -1;
				}
				player.spotAnimY = int2 >> 16;
				player.spotAnimNextFrame = 1;
				if (player.spotAnimId != -1 && client.loop == player.spotAnimStart) {
					savedOffset = SpotAnimTypeList.get(player.spotAnimId).seqId;
					if (savedOffset != -1) {
						@Pc(663) SeqType seq = SeqTypeList.get(savedOffset);
						if (seq != null && seq.frames != null) {
							SoundPlayer.playSeqSound(player.yFine, seq, player.xFine, player == PlayerList.self, 0);
						}
					}
				}
			}
		}
		if ((flags & 0x40) != 0) {
			player.faceX = inboundBuffer.g2();
			player.faceY = inboundBuffer.ig2add();
		}
	}

	@OriginalMember(owner = "client!fb", name = "b", descriptor = "(B)V")
	public static void readPlayerInfoPacket() {
		extendedCount = 0;
		removedCount = 0;
		readSelfPlayerInfo();
		readPlayerInfo();
		readNewPlayerInfo();
		readExtendedPlayerInfo();
		@Pc(23) int i;
		for (i = 0; i < removedCount; i++) {
			@Pc(30) int playerId = removedIds[i];
			if (client.loop != PlayerList.players[playerId].lastSeenLoop) {
				if (PlayerList.players[playerId].soundRadius > 0) {
					AreaSoundManager.remove(PlayerList.players[playerId]);
				}
				PlayerList.players[playerId] = null;
			}
		}
		if (length != inboundBuffer.offset) {
			throw new RuntimeException("gpp1 pos:" + inboundBuffer.offset + " psize:" + length);
		}
		for (i = 0; i < PlayerList.size; i++) {
			if (PlayerList.players[PlayerList.ids[i]] == null) {
				throw new RuntimeException("gpp2 pos:" + i + " size:" + PlayerList.size);
			}
		}
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(B)V")
	public static void readSelfPlayerInfo() {
		inboundBuffer.accessBits();
		@Pc(11) int upated = inboundBuffer.gBits(1);
		if (upated == 0) {
			return;
		}
		@Pc(23) int type = inboundBuffer.gBits(2);
		if (type == 0) {
			extendedIds[extendedCount++] = 2047;
			return;
		}
		@Pc(54) int dir;
		@Pc(64) int hasExtended;
		if (type == 1) {
			dir = inboundBuffer.gBits(3);
			PlayerList.self.move(1, dir);
			hasExtended = inboundBuffer.gBits(1);
			if (hasExtended == 1) {
				extendedIds[extendedCount++] = 2047;
			}
		} else if (type == 2) {
			if (inboundBuffer.gBits(1) == 1) {
				dir = inboundBuffer.gBits(3);
				PlayerList.self.move(2, dir);
				hasExtended = inboundBuffer.gBits(3);
				PlayerList.self.move(2, hasExtended);
			} else {
				dir = inboundBuffer.gBits(3);
				PlayerList.self.move(0, dir);
			}
			dir = inboundBuffer.gBits(1);
			if (dir == 1) {
				extendedIds[extendedCount++] = 2047;
			}
		} else if (type == 3) {
			dir = inboundBuffer.gBits(7);
			hasExtended = inboundBuffer.gBits(1);
			Player.plane = inboundBuffer.gBits(2);
			@Pc(163) int hasExt = inboundBuffer.gBits(1);
			if (hasExt == 1) {
				extendedIds[extendedCount++] = 2047;
			}
			@Pc(181) int x = inboundBuffer.gBits(7);
			PlayerList.self.teleport(x, hasExtended == 1, dir);
		}
	}

	@OriginalMember(owner = "client!dc", name = "b", descriptor = "(Z)V")
	public static void readPlayerInfo() {
		@Pc(6) int count = inboundBuffer.gBits(8);
		@Pc(20) int i;
		if (PlayerList.size > count) {
			for (i = count; i < PlayerList.size; i++) {
				removedIds[removedCount++] = PlayerList.ids[i];
			}
		}
		if (count > PlayerList.size) {
			throw new RuntimeException("gppov1");
		}
		PlayerList.size = 0;
		for (i = 0; i < count; i++) {
			@Pc(75) int playerId = PlayerList.ids[i];
			@Pc(79) Player player = PlayerList.players[playerId];
			@Pc(84) int updated = inboundBuffer.gBits(1);
			if (updated == 0) {
				PlayerList.ids[PlayerList.size++] = playerId;
				player.lastSeenLoop = client.loop;
			} else {
				@Pc(107) int moveType = inboundBuffer.gBits(2);
				if (moveType == 0) {
					PlayerList.ids[PlayerList.size++] = playerId;
					player.lastSeenLoop = client.loop;
					extendedIds[extendedCount++] = playerId;
				} else {
					@Pc(153) int dir;
					@Pc(163) int hasExtended;
					if (moveType == 1) {
						PlayerList.ids[PlayerList.size++] = playerId;
						player.lastSeenLoop = client.loop;
						dir = inboundBuffer.gBits(3);
						player.move(1, dir);
						hasExtended = inboundBuffer.gBits(1);
						if (hasExtended == 1) {
							extendedIds[extendedCount++] = playerId;
						}
					} else if (moveType == 2) {
						PlayerList.ids[PlayerList.size++] = playerId;
						player.lastSeenLoop = client.loop;
						if (inboundBuffer.gBits(1) == 1) {
							dir = inboundBuffer.gBits(3);
							player.move(2, dir);
							hasExtended = inboundBuffer.gBits(3);
							player.move(2, hasExtended);
						} else {
							dir = inboundBuffer.gBits(3);
							player.move(0, dir);
						}
						dir = inboundBuffer.gBits(1);
						if (dir == 1) {
							extendedIds[extendedCount++] = playerId;
						}
					} else if (moveType == 3) {
						removedIds[removedCount++] = playerId;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(I)V")
	public static void readNewPlayerInfo() {
		while (true) {
			if (inboundBuffer.availableBits(length) >= 11) {
				@Pc(20) int playerId = inboundBuffer.gBits(11);
				if (playerId != 2047) {
					@Pc(27) boolean isNew = false;
					if (PlayerList.players[playerId] == null) {
						PlayerList.players[playerId] = new Player();
						isNew = true;
						if (PlayerList.appearanceCache[playerId] != null) {
							PlayerList.players[playerId].decodeAppearance(PlayerList.appearanceCache[playerId]);
						}
					}
					PlayerList.ids[PlayerList.size++] = playerId;
					@Pc(65) Player player = PlayerList.players[playerId];
					player.lastSeenLoop = client.loop;
					@Pc(73) int hasExtended = inboundBuffer.gBits(1);
					if (hasExtended == 1) {
						extendedIds[extendedCount++] = playerId;
					}
					@Pc(92) int dy = inboundBuffer.gBits(5);
					@Pc(99) int angle = PathingEntity.ANGLES[inboundBuffer.gBits(3)];
					if (dy > 15) {
						dy -= 32;
					}
					if (isNew) {
						player.targetAngle = player.currentAngle = angle;
					}
					@Pc(116) int discardWalk = inboundBuffer.gBits(1);
					@Pc(121) int dx = inboundBuffer.gBits(5);
					if (dx > 15) {
						dx -= 32;
					}
					player.teleport(dy + PlayerList.self.movementQueueX[0], discardWalk == 1, PlayerList.self.movementQueueY[0] + dx);
					continue;
				}
			}
			inboundBuffer.accessBytes();
			return;
		}
	}

	@OriginalMember(owner = "client!tm", name = "a", descriptor = "(I)V")
	public static void readExtendedPlayerInfo() {
		for (@Pc(7) int i = 0; i < extendedCount; i++) {
			@Pc(31) int id = extendedIds[i];
			@Pc(35) Player player = PlayerList.players[id];
			@Pc(39) int flags = inboundBuffer.g1();
			if ((flags & 0x10) != 0) {
				flags += inboundBuffer.g1() << 8;
			}
			readExtendedPlayerInfo(flags, id, player);
		}
	}

	@OriginalMember(owner = "client!ac", name = "a", descriptor = "(B)Z")
	public static boolean readPacketInternal() throws IOException {
		if (socket == null) {
			return false;
		}

		@Pc(14) int available = socket.available();
		if (available == 0) {
			return false;
		}

		if (opcode == -1) {
			available--;
			socket.read(0, 1, inboundBuffer.data);
			inboundBuffer.offset = 0;
			opcode = inboundBuffer.g1isaac();
			length = PACKET_LENGTHS[opcode];
		}

		if (length == -1) {
			if (available <= 0) {
				return false;
			}
			socket.read(0, 1, inboundBuffer.data);
			available--;
			length = inboundBuffer.data[0] & 0xFF;
		} else if (length == -2) {
			if (available <= 1) {
				return false;
			}
			available -= 2;
			socket.read(0, 2, inboundBuffer.data);
			inboundBuffer.offset = 0;
			length = inboundBuffer.g2();
		}

		if (length > available) {
			return false;
		}

		inboundBuffer.offset = 0;
		socket.read(0, length, inboundBuffer.data);
		opcode4 = opcode3;
		opcode3 = opcode2;
		opcode2 = opcode;
		LoginManager.ticksSinceLastPacket = 0;

		if (opcode == ServerProt.VARP_SMALL) {
			int id = inboundBuffer.g2add();
			@Pc(137) byte value = inboundBuffer.g1bneg();
			VarpDomain.set(value, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.RUN_CS2) {
			int tracknum = inboundBuffer.g2();
			JagString argTypes = inboundBuffer.gjstr();
			@Pc(163) Object[] scriptArgs = new Object[argTypes.length() + 1];
			for (int i = argTypes.length() - 1; i >= 0; i--) {
				if (argTypes.charAt(i) == 115) {
					scriptArgs[i + 1] = inboundBuffer.gjstr();
				} else {
					scriptArgs[i + 1] = inboundBuffer.g4();
				}
			}
			scriptArgs[0] = inboundBuffer.g4();
			setVerifyId(tracknum);
			@Pc(226) HookRequest request = new HookRequest();
			request.arguments = scriptArgs;
			ScriptRunner.run(request);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_GAME) {
			@Pc(245) JagString message = inboundBuffer.gjstr();
			if (message.endsWith(TRADEREQ)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (IgnoreList.encodedUsernames[i] == name37) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					Chat.add(name, 4, LocalizedText.TRADEREQ);
				}
			} else if (message.endsWith(CHALREQ)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (IgnoreList.encodedUsernames[i] == name37) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					JagString detail = message.substring(message.length() - 9, message.indexOf(JagString.COLON) + 1);
					Chat.add(name, 8, detail);
				}
			} else if (message.endsWith(ASSISTREQ)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (name37 == IgnoreList.encodedUsernames[i]) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					Chat.add(name, 10, JagString.EMPTY);
				}
			} else if (message.endsWith(CLAN)) {
				JagString name = message.substring(message.indexOf(CLAN), 0);
				Chat.add(JagString.EMPTY, 11, name);
			} else if (message.endsWith(TRADE)) {
				JagString name = message.substring(message.indexOf(TRADE), 0);
				if (Player.inTutorialIsland == 0) {
					Chat.add(JagString.EMPTY, 12, name);
				}
			} else if (message.endsWith(ASSIST)) {
				JagString name = message.substring(message.indexOf(ASSIST), 0);
				if (Player.inTutorialIsland == 0) {
					Chat.add(JagString.EMPTY, 13, name);
				}
			} else if (message.endsWith(DUELSTAKE)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (name37 == IgnoreList.encodedUsernames[i]) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					Chat.add(name, 14, JagString.EMPTY);
				}
			} else if (message.endsWith(DUELFRIEND)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (IgnoreList.encodedUsernames[i] == name37) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					Chat.add(name, 15, JagString.EMPTY);
				}
			} else if (message.endsWith(CLAN_REQUEST_SUFFIX)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (name37 == IgnoreList.encodedUsernames[i]) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					Chat.add(name, 16, JagString.EMPTY);
				}
			} else if (message.endsWith(ALLY_REQUEST_SUFFIX)) {
				JagString name = message.substring(message.indexOf(JagString.COLON), 0);
				long name37 = name.encode37();
				boolean ignored = false;
				for (int i = 0; i < IgnoreList.size; i++) {
					if (IgnoreList.encodedUsernames[i] == name37) {
						ignored = true;
						break;
					}
				}
				if (!ignored && Player.inTutorialIsland == 0) {
					JagString detail = message.substring(message.length() - 9, message.indexOf(JagString.COLON) + 1);
					Chat.add(name, 21, detail);
				}
			} else {
				Chat.add(JagString.EMPTY, 0, message);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETTEXT3) {
			int id = inboundBuffer.ig2();
			int tracknum = inboundBuffer.g2add();
			JagString value = inboundBuffer.gjstr();
			setVerifyId(tracknum);
			DelayedStateChange.setVarcStrServer(value, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_ZONE_PARTIAL_ENCLOSED) {
			SceneGraph.currentChunkY = inboundBuffer.g1add();
			SceneGraph.currentChunkX = inboundBuffer.g1sub();
			while (length > inboundBuffer.offset) {
				opcode = inboundBuffer.g1();
				readZonePacket();
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CLEAR_MINIMAP_FLAG) {
			opcode = -1;
			LoginManager.mapFlagX = 0;
			return true;
		} else if (opcode == ServerProt.IF_SETSCROLLPOS) {
			int id = inboundBuffer.mg4();
			int pos = inboundBuffer.ig2();
			int tracknum = inboundBuffer.g2();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentScrollServer(pos, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CLAN_QUICK_CHAT) {
			long name37 = inboundBuffer.g8();
			inboundBuffer.g1b();
			long clan37 = inboundBuffer.g8();
			int top = inboundBuffer.g2();
			int bot = inboundBuffer.g3();
			int rights = inboundBuffer.g1();
			int quickchatId = inboundBuffer.g2();

			@Pc(910) boolean ignored = false;
			@Pc(922) long messageId = ((long) top << 32) + bot;
			@Pc(924) int n = 0;

			check:
			while (true) {
				if (n < 100) {
					if (messageId != Chat.recentMessages[n]) {
						n++;
						continue;
					}
					ignored = true;
					break;
				}
				if (rights <= 1) {
					for (n = 0; n < IgnoreList.size; n++) {
						if (IgnoreList.encodedUsernames[n] == name37) {
							ignored = true;
							break check;
						}
					}
				}
				break;
			}
			if (!ignored && Player.inTutorialIsland == 0) {
				Chat.recentMessages[Chat.messageCounter] = messageId;
				Chat.messageCounter = (Chat.messageCounter + 1) % 100;
				@Pc(999) JagString message = QuickChatPhraseTypeList.get(quickchatId).decodeMessage(inboundBuffer);
				if (rights == 2 || rights == 3) {
					Chat.add(quickchatId, 20, message, Base37.decode37(clan37).toTitleCase(), JagString.concatenate(new JagString[]{IMG1, Base37.decode37(name37).toTitleCase()}));
				} else if (rights == 1) {
					Chat.add(quickchatId, 20, message, Base37.decode37(clan37).toTitleCase(), JagString.concatenate(new JagString[]{IMG0, Base37.decode37(name37).toTitleCase()}));
				} else {
					Chat.add(quickchatId, 20, message, Base37.decode37(clan37).toTitleCase(), Base37.decode37(name37).toTitleCase());
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.JOIN_CLAN_CHAT) {
			ClanChat.transmitAt = InterfaceList.transmitTimer;
			long owner37 = inboundBuffer.g8();
			if (owner37 == 0L) {
				ClanChat.owner = null;
				opcode = -1;
				ClanChat.name = null;
				ClanChat.members = null;
				ClanChat.size = 0;
				return true;
			}

			long name37 = inboundBuffer.g8();
			ClanChat.name = Base37.decode37(name37);
			ClanChat.owner = Base37.decode37(owner37);
			ClanChat.minKick = inboundBuffer.g1b();
			int clanSize = inboundBuffer.g1();
			if (clanSize == 255) {
				opcode = -1;
				return true;
			}

			ClanChat.size = clanSize;
			@Pc(1158) ClanMember[] members = new ClanMember[100];
			for (int i = 0; i < ClanChat.size; i++) {
				members[i] = new ClanMember();
				members[i].key = inboundBuffer.g8();
				members[i].username = Base37.decode37(members[i].key);
				members[i].world = inboundBuffer.g2();
				members[i].rank = inboundBuffer.g1b();
				members[i].worldName = inboundBuffer.gjstr();
				if (members[i].key == Player.name37) {
					ClanChat.rank = members[i].rank;
				}
			}

			int count = ClanChat.size;
			while (count > 0) {
				boolean sorted = true;
				count--;
				for (int i = 0; i < count; i++) {
					if (members[i].username.compareTo(members[i + 1].username) > 0) {
						sorted = false;
						@Pc(1279) ClanMember member = members[i];
						members[i] = members[i + 1];
						members[i + 1] = member;
					}
				}
				if (sorted) {
					break;
				}
			}
			ClanChat.members = members;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.LAST_LOGIN_INFO) {
			int ip32 = inboundBuffer.img4();
			Player.lastLogAddress = GameShell.signLink.getReverseDns(ip32);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.PLAYER_INFO) {
			readPlayerInfoPacket();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETTEXT2) {
			int tracknum = inboundBuffer.g2();
			JagString text = inboundBuffer.gjstr();
			int id = inboundBuffer.ig2add();
			setVerifyId(tracknum);
			DelayedStateChange.setVarcStrServer(text, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CHAT_FILTER_SETTINGS) {
			Chat.publicFilter = inboundBuffer.g1();
			Chat.privateFilter = inboundBuffer.g1();
			Chat.tradeFilter = inboundBuffer.g1();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SET_INTERACTION) {
			int cursor = inboundBuffer.ig2add();
			if (cursor == 65535) {
				cursor = -1;
			}
			int top = inboundBuffer.g1();
			int optId = inboundBuffer.g1();
			JagString option = inboundBuffer.gjstr();
			if (optId >= 1 && optId <= 8) {
				if (option.equalsIgnoreCase(MiniMenu.NULL)) {
					option = null;
				}
				Player.options[optId - 1] = option;
				Player.cursors[optId - 1] = cursor;
				Player.secondaryOptions[optId - 1] = top == 0;
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.VARP_LARGE) {
			int value = inboundBuffer.g4();
			int id = inboundBuffer.g2add();
			VarpDomain.set(value, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETHIDE) {
			int parent = inboundBuffer.g1neg();
			int tracknum = inboundBuffer.g2();
			int reset = inboundBuffer.ig4();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentHiddenServer(reset, parent);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_OPENSUB) {
			int parent = inboundBuffer.ig2add();
			int reset = inboundBuffer.g1add();
			int tracknum = inboundBuffer.ig2add();
			setVerifyId(tracknum);
			if (reset == 2) {
				WorldMap.reset();
			}
			InterfaceList.topLevelInterface = parent;
			InterfaceList.resetAnimations(parent);
			InterfaceList.layoutTopLevel(false);
			InterfaceList.runOnLoadScripts(InterfaceList.topLevelInterface);
			for (int i = 0; i < 100; i++) {
				InterfaceList.rectangleDirty[i] = true;
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CLIENT_SETVARC_LARGE) {
			int tracknum = inboundBuffer.ig2add();
			int value = inboundBuffer.g4();
			int id = inboundBuffer.g2add();
			setVerifyId(tracknum);
			DelayedStateChange.updateVarC(id, value);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_QUICKCHAT_PRIVATE_ECHO) {
			long name37 = inboundBuffer.g8();
			int chatId = inboundBuffer.g2();
			JagString message = QuickChatPhraseTypeList.get(chatId).decodeMessage(inboundBuffer);
			Chat.add(chatId, 19, message, null, Base37.decode37(name37).toTitleCase());
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_UID192) {
			writeRandom(inboundBuffer);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.RESET_CLIENT_VARCACHE) {
			VarpDomain.reset();
			InterfaceList.redrawActiveInterfaces();
			VarpDomain.updatedVarpsWriterIndex += 32;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CAM_LOOKAT) {
			int tracknum = inboundBuffer.g2();
			int tx = inboundBuffer.g1();
			int tz = inboundBuffer.g1();
			int cy = inboundBuffer.g2();
			int step = inboundBuffer.g1();
			int dur = inboundBuffer.g1();
			setVerifyId(tracknum);
			Camera.setLockedLookAt(cy, tz, step, tx, dur);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETANIM) {
			int id = inboundBuffer.mg4();
			int value = inboundBuffer.ig2b();
			int tracknum = inboundBuffer.g2add();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentAnimServer(id, value);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.WIDGETSTRUCT_SETTING) {
			int value = inboundBuffer.ig2add();
			int parent = inboundBuffer.ig4();
			int tracknum = inboundBuffer.g2add();
			int end = inboundBuffer.ig2();
			if (end == 65535) {
				end = -1;
			}
			int start = inboundBuffer.g2add();
			if (start == 65535) {
				start = -1;
			}
			setVerifyId(tracknum);
			ServerActiveProperties properties;
			for (int slot = start; slot <= end; slot++) {
				long ptr = (long) slot + ((long) parent << 32);
				ServerActiveProperties prev = (ServerActiveProperties) InterfaceList.properties.get(ptr);
				if (prev != null) {
					properties = new ServerActiveProperties(prev.events, value);
					prev.unlink();
				} else if (slot == -1) {
					properties = new ServerActiveProperties(InterfaceList.getComponent(parent).properties.events, value);
				} else {
					properties = new ServerActiveProperties(0, value);
				}
				InterfaceList.properties.put(properties, ptr);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SPOTANIM_ENTITY) {
			int delay = inboundBuffer.g2();
			int height = inboundBuffer.ig2();
			int target = inboundBuffer.img4();
			int gfxId = inboundBuffer.ig2add();
			if (target >> 30 == 0) {
				@Pc(1994) SeqType seq;
				if (target >> 29 != 0) {
					int npcId = target & 0xFFFF;
					@Pc(1894) Npc npc = NpcList.npcs[npcId];
					if (npc != null) {
						if (gfxId == 65535) {
							gfxId = -1;
						}
						boolean animated = gfxId == -1 || npc.spotAnimId == -1 || SeqTypeList.get(SpotAnimTypeList.get(gfxId).seqId).priority >= SeqTypeList.get(SpotAnimTypeList.get(npc.spotAnimId).seqId).priority;
						if (animated) {
							npc.spotAnimDelayClock = 0;
							npc.spotAnimId = gfxId;
							npc.spotAnimStart = client.loop + delay;
							npc.spotAnimFrame = 0;
							if (npc.spotAnimStart > client.loop) {
								npc.spotAnimFrame = -1;
							}
							npc.spotAnimY = height;
							npc.spotAnimNextFrame = 1;
							if (npc.spotAnimId != -1 && client.loop == npc.spotAnimStart) {
								int seqId = SpotAnimTypeList.get(npc.spotAnimId).seqId;
								if (seqId != -1) {
									seq = SeqTypeList.get(seqId);
									if (seq.frames != null) {
										SoundPlayer.playSeqSound(npc.yFine, seq, npc.xFine, false, 0);
									}
								}
							}
						}
					}
				} else if (target >> 28 != 0) {
					int playerId = target & 0xFFFF;
					@Pc(2033) Player player;
					if (PlayerList.selfId == playerId) {
						player = PlayerList.self;
					} else {
						player = PlayerList.players[playerId];
					}
					if (player != null) {
						if (gfxId == 65535) {
							gfxId = -1;
						}
						boolean animated = gfxId == -1 || player.spotAnimId == -1 || SeqTypeList.get(SpotAnimTypeList.get(gfxId).seqId).priority >= SeqTypeList.get(SpotAnimTypeList.get(player.spotAnimId).seqId).priority;
						if (animated) {
							player.spotAnimStart = delay + client.loop;
							player.spotAnimY = height;
							player.spotAnimId = gfxId;
							player.spotAnimNextFrame = 1;
							player.spotAnimDelayClock = 0;
							player.spotAnimFrame = 0;
							if (player.spotAnimStart > client.loop) {
								player.spotAnimFrame = -1;
							}
							if (player.spotAnimId != -1 && player.spotAnimStart == client.loop) {
								int seqId = SpotAnimTypeList.get(player.spotAnimId).seqId;
								if (seqId != -1) {
									seq = SeqTypeList.get(seqId);
									if (seq.frames != null) {
										SoundPlayer.playSeqSound(player.yFine, seq, player.xFine, player == PlayerList.self, 0);
									}
								}
							}
						}
					}
				}
			} else {
				int plane = target >> 28 & 0x3;
				int posX = (target >> 14 & 0x3FFF) - Camera.originX;
				int posY = (target & 0x3FFF) - Camera.originY;
				if (posX >= 0 && posY >= 0 && posX < 104 && posY < 104) {
					posY = posY * 128 + 64;
					posX = posX * 128 + 64;
					@Pc(2241) SpotAnim spotAnim = new SpotAnim(gfxId, plane, posX, posY, SceneGraph.getTileHeight(plane, posX, posY) - height, delay, client.loop);
					SceneGraph.spotanims.addTail(new SpotAnimNode(spotAnim));
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.INTERFACE_ANIMATE_ROTATE) {
			int ptr = inboundBuffer.mg4();
			int tracknum = inboundBuffer.g2add();
			int pitchStep = inboundBuffer.g2();
			int yawStep = inboundBuffer.g2add();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentModelRotationSpeedServer(yawStep + (pitchStep << 16), ptr);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_STAT) {
			InterfaceList.redrawActiveInterfaces();
			int level = inboundBuffer.g1add();
			int xp = inboundBuffer.img4();
			int skill = inboundBuffer.g1();
			PlayerSkillXpTable.experience[skill] = xp;
			PlayerSkillXpTable.boostedLevels[skill] = level;
			PlayerSkillXpTable.baseLevels[skill] = 1;
			for (int i = 0; i < 98; i++) {
				if (PlayerSkillXpTable.xpLevelLookup[i] <= xp) {
					PlayerSkillXpTable.baseLevels[skill] = i + 2;
				}
			}
			PluginRepository.OnXPUpdate(skill, xp);
			PlayerSkillXpTable.updatedStats[PlayerSkillXpTable.updatedStatsWriterIndex++ & 0x1F] = skill;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MAP_PROJANIM || opcode == ServerProt.MAP_PROJANIM_3 || opcode == ServerProt.SOUND_AREA || opcode == ServerProt.OBJ_COUNT || opcode == ServerProt.LOC_ADD_CHANGE || opcode == ServerProt.OBJ_ADD || opcode == ServerProt.SPOTANIM_SPECIFIC || opcode == ServerProt.MAP_PROJANIM_2 || opcode == ServerProt.OBJ_DEL || opcode == ServerProt.OBJ_REVEAL || opcode == ServerProt.LOC_ANIM || opcode == ServerProt.LOC_DEL || opcode == ServerProt.LOC_ADD) {
			readZonePacket();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_CLOSESUB) {
			int tracknum = inboundBuffer.g2();
			int id = inboundBuffer.g4();
			setVerifyId(tracknum);
			@Pc(2441) ComponentPointer pointer = (ComponentPointer) InterfaceList.openInterfaces.get(id);
			if (pointer != null) {
				InterfaceList.closeInterface(true, pointer);
			}
			if (Cs1ScriptRunner.pleaseWaitComponent != null) {
				InterfaceList.redraw(Cs1ScriptRunner.pleaseWaitComponent);
				Cs1ScriptRunner.pleaseWaitComponent = null;
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CAM_FORCEANGLE) {
			int yaw = inboundBuffer.ig2();
			int tracknum = inboundBuffer.g2();
			int pitch = inboundBuffer.g2();
			setVerifyId(tracknum);
			Camera.yawTarget = yaw;
			Camera.pitchTarget = pitch;
			if (Camera.cameraType == 2) {
				Camera.cameraPitch = (int) Camera.pitchTarget;
				Camera.cameraYaw = (int) Camera.yawTarget;
			}
			Camera.clampCameraAngle();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETANGLE) {
			int pitch = inboundBuffer.g2();
			int tracknum = inboundBuffer.g2add();
			int scale = inboundBuffer.ig2add();
			int yaw = inboundBuffer.ig2add();
			int ptr = inboundBuffer.g4();
			setVerifyId(tracknum);
			DelayedStateChange.updateView(scale, ptr, yaw, pitch);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_ZONE_FULL_FOLLOWS) {
			SceneGraph.currentChunkX = inboundBuffer.g1();
			SceneGraph.currentChunkY = inboundBuffer.g1neg();
			for (int x = SceneGraph.currentChunkX; x < SceneGraph.currentChunkX + 8; x++) {
				for (int y = SceneGraph.currentChunkY; y < SceneGraph.currentChunkY + 8; y++) {
					if (SceneGraph.objStacks[Player.plane][x][y] != null) {
						SceneGraph.objStacks[Player.plane][x][y] = null;
						spawnGroundObject(y, x);
					}
				}
			}
			for (@Pc(2604) ChangeLocRequest loc = (ChangeLocRequest) ChangeLocRequest.queue.head(); loc != null; loc = (ChangeLocRequest) ChangeLocRequest.queue.next()) {
				if (loc.x >= SceneGraph.currentChunkX && SceneGraph.currentChunkX + 8 > loc.x && loc.y >= SceneGraph.currentChunkY && loc.y < SceneGraph.currentChunkY + 8 && loc.level == Player.plane) {
					loc.resetLoops = 0;
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_INV_CLEAR) {
			int id = inboundBuffer.mg4();
			@Pc(2666) Component component = InterfaceList.getComponent(id);
			for (int i = 0; i < component.objTypes.length; i++) {
				component.objTypes[i] = -1;
				component.objTypes[i] = 0;
			}
			InterfaceList.redraw(component);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETMODEL) {
			int id = inboundBuffer.ig4();
			int tracknum = inboundBuffer.ig2add();
			int modelId = inboundBuffer.g2add();
			if (modelId == 65535) {
				modelId = -1;
			}
			setVerifyId(tracknum);
			DelayedStateChange.updateComponentModel(-1, 1, id, modelId);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SET_MINIMAP_STATE) {
			MiniMap.state = inboundBuffer.g1();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.TELEPORT_LOCAL_PLAYER) {
			int pos1 = inboundBuffer.g1sub();
			int flags = inboundBuffer.g1add();
			int pos2 = inboundBuffer.g1();
			Player.plane = flags >> 1;
			PlayerList.self.teleport(pos1, (flags & 0x1) == 1, pos2);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_FRIENDLIST) {
			long name37 = inboundBuffer.g8();
			int worldId = inboundBuffer.g2();
			int x = inboundBuffer.g1();
			boolean ignored = true;
			if (name37 < 0L) {
				name37 &= Long.MAX_VALUE;
				ignored = false;
			}
			JagString worldName = JagString.EMPTY;
			if (worldId > 0) {
				worldName = inboundBuffer.gjstr();
			}
			@Pc(2834) JagString name = Base37.decode37(name37).toTitleCase();
			for (int i = 0; i < FriendsList.size; i++) {
				if (name37 == FriendsList.encodedUsernames[i]) {
					if (worldId != FriendsList.worlds[i]) {
						FriendsList.worlds[i] = worldId;
						if (worldId > 0) {
							Chat.add(JagString.EMPTY, 5, JagString.concatenate(new JagString[]{name, LocalizedText.FRIENDLOGIN}));
						}
						if (worldId == 0) {
							Chat.add(JagString.EMPTY, 5, JagString.concatenate(new JagString[]{name, LocalizedText.FRIENDLOGOUT}));
						}
					}
					FriendsList.worldNames[i] = worldName;
					FriendsList.ranks[i] = x;
					name = null;
					FriendsList.sameGame[i] = ignored;
					break;
				}
			}
			if (name != null && FriendsList.size < 200) {
				FriendsList.encodedUsernames[FriendsList.size] = name37;
				FriendsList.usernames[FriendsList.size] = name;
				FriendsList.worlds[FriendsList.size] = worldId;
				FriendsList.worldNames[FriendsList.size] = worldName;
				FriendsList.ranks[FriendsList.size] = x;
				FriendsList.sameGame[FriendsList.size] = ignored;
				FriendsList.size++;
			}
			FriendsList.transmitAt = InterfaceList.transmitTimer;
			int friendCount = FriendsList.size;
			while (friendCount > 0) {
				friendCount--;
				@Pc(2961) boolean sorting = true;
				for (int i = 0; i < friendCount; i++) {
					if (FriendsList.worlds[i] != Player.worldId && Player.worldId == FriendsList.worlds[i + 1] || FriendsList.worlds[i] == 0 && FriendsList.worlds[i + 1] != 0) {
						sorting = false;
						int tmpWorld = FriendsList.worlds[i];
						FriendsList.worlds[i] = FriendsList.worlds[i + 1];
						FriendsList.worlds[i + 1] = tmpWorld;
						JagString tmpWorldName = FriendsList.worldNames[i];
						FriendsList.worldNames[i] = FriendsList.worldNames[i + 1];
						FriendsList.worldNames[i + 1] = tmpWorldName;
						JagString tmpUsername = FriendsList.usernames[i];
						FriendsList.usernames[i] = FriendsList.usernames[i + 1];
						FriendsList.usernames[i + 1] = tmpUsername;
						@Pc(3056) long tmpEncoded = FriendsList.encodedUsernames[i];
						FriendsList.encodedUsernames[i] = FriendsList.encodedUsernames[i + 1];
						FriendsList.encodedUsernames[i + 1] = tmpEncoded;
						@Pc(3074) int tmpRank = FriendsList.ranks[i];
						FriendsList.ranks[i] = FriendsList.ranks[i + 1];
						FriendsList.ranks[i + 1] = tmpRank;
						@Pc(3092) boolean tmpSameGame = FriendsList.sameGame[i];
						FriendsList.sameGame[i] = FriendsList.sameGame[i + 1];
						FriendsList.sameGame[i + 1] = tmpSameGame;
					}
				}
				if (sorting) {
					break;
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SET_WALK_TEXT) {
			if (length == 0) {
				MiniMenu.walkText = LocalizedText.WALKHERE;
			} else {
				MiniMenu.walkText = inboundBuffer.gjstr();
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.FORCE_VARP_REFRESH) {
			for (int i = 0; i < VarpDomain.activeVarps.length; i++) {
				if (VarpDomain.varp[i] != VarpDomain.activeVarps[i]) {
					VarpDomain.activeVarps[i] = VarpDomain.varp[i];
					VarpDomain.refreshMagicVarp(i);
					VarpDomain.updatedVarps[VarpDomain.updatedVarpsWriterIndex++ & 0x1F] = i;
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CAMERA_DETACH) {
			int tracknum = inboundBuffer.g2();
			int type = inboundBuffer.g1();
			int modelId = inboundBuffer.g1();
			int counter = inboundBuffer.g2();
			int speed = inboundBuffer.g1();
			int accel = inboundBuffer.g1();
			setVerifyId(tracknum);
			Camera.setLockedPosition(true, speed, counter, accel, modelId, type);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_QUICKCHAT_PRIVATE) {
			long name = inboundBuffer.g8();
			int top = inboundBuffer.g2();
			int bot = inboundBuffer.g3();
			int rights = inboundBuffer.g1();
			int chatId = inboundBuffer.g2();

			@Pc(3263) boolean ignore = false;
			@Pc(3270) long messageId = ((long) top << 32) + bot;
			@Pc(3272) int ix = 0;

			check:
			while (true) {
				if (ix < 100) {
					if (messageId != Chat.recentMessages[ix]) {
						ix++;
						continue;
					}
					ignore = true;
					break;
				}
				if (rights <= 1) {
					for (ix = 0; ix < IgnoreList.size; ix++) {
						if (name == IgnoreList.encodedUsernames[ix]) {
							ignore = true;
							break check;
						}
					}
				}
				break;
			}
			if (!ignore && Player.inTutorialIsland == 0) {
				Chat.recentMessages[Chat.messageCounter] = messageId;
				Chat.messageCounter = (Chat.messageCounter + 1) % 100;
				JagString message = QuickChatPhraseTypeList.get(chatId).decodeMessage(inboundBuffer);
				if (rights == 2) {
					Chat.add(chatId, 18, message, null, JagString.concatenate(new JagString[]{IMG1, Base37.decode37(name).toTitleCase()}));
				} else if (rights == 1) {
					Chat.add(chatId, 18, message, null, JagString.concatenate(new JagString[]{IMG0, Base37.decode37(name).toTitleCase()}));
				} else {
					Chat.add(chatId, 18, message, null, Base37.decode37(name).toTitleCase());
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SWITCH_WIDGET) {
			int source = inboundBuffer.img4();
			int tracknum = inboundBuffer.g2add();
			int target = inboundBuffer.img4();
			setVerifyId(tracknum);
			@Pc(3449) ComponentPointer src = (ComponentPointer) InterfaceList.openInterfaces.get(source);
			ComponentPointer tgt = (ComponentPointer) InterfaceList.openInterfaces.get(target);
			if (tgt != null) {
				InterfaceList.closeInterface(src == null || tgt.interfaceId != src.interfaceId, tgt);
			}
			if (src != null) {
				src.unlink();
				InterfaceList.openInterfaces.put(src, target);
			}
			@Pc(3490) Component component = InterfaceList.getComponent(source);
			if (component != null) {
				InterfaceList.redraw(component);
			}
			component = InterfaceList.getComponent(target);
			if (component != null) {
				InterfaceList.redraw(component);
				InterfaceList.layoutComponent(component, true);
			}
			if (InterfaceList.topLevelInterface != -1) {
				InterfaceList.runScripts(1, InterfaceList.topLevelInterface);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CAM_SHAKE) {
			int tracknum = inboundBuffer.g2();
			int cameraId = inboundBuffer.g1();
			int jitter = inboundBuffer.g1();
			int amplitude = inboundBuffer.g1();
			int frequency = inboundBuffer.g1();
			int shake4 = inboundBuffer.g2();
			setVerifyId(tracknum);
			Camera.customCameraActive[cameraId] = true;
			Camera.cameraJitter[cameraId] = jitter;
			Camera.cameraAmplitude[cameraId] = amplitude;
			Camera.cameraFrequency[cameraId] = frequency;
			cameraShakePhase[cameraId] = shake4;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETCOLOUR) {
			int id = inboundBuffer.img4();
			int tracknum = inboundBuffer.g2add();
			int color = inboundBuffer.ig2add();
			setVerifyId(tracknum);
			DelayedStateChange.setColor(color, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_REBOOT_TIMER) {
			Player.rebootTimer = inboundBuffer.g2() * 30;
			opcode = -1;
			InterfaceList.miscTransmitAt = InterfaceList.transmitTimer;
			return true;
		} else if (opcode == ServerProt.REFLECTION_CHEAT_CHECK) {
			ReflectionCheck.push(GameShell.signLink, inboundBuffer, length);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CLIENT_SETVARC_SMALL) {
			int tracknum = inboundBuffer.ig2();
			int value = inboundBuffer.g1neg();
			int id = inboundBuffer.ig2add();
			setVerifyId(tracknum);
			DelayedStateChange.updateVarC(id, value);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_RUNENERGY) {
			InterfaceList.redrawActiveInterfaces();
			Player.runEnergy = inboundBuffer.g1();
			InterfaceList.miscTransmitAt = InterfaceList.transmitTimer;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.GAME_FRAME_UNK) {
			if (InterfaceList.topLevelInterface != -1) {
				InterfaceList.runScripts(0, InterfaceList.topLevelInterface);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.DELETE_INVENTORY) {
			int id = inboundBuffer.ig2();
			Inv.delete(id);
			Inv.updatedInventories[Inv.updatedInventoriesWriterIndex++ & 0x1F] = id & 0x7FFF;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.NPC_ANIM_SPECIFIC) {
			int npcId = inboundBuffer.ig2();
			int value = inboundBuffer.g1sub();
			int seqId = inboundBuffer.g2();
			@Pc(3766) Npc npc = NpcList.npcs[npcId];
			if (npc != null) {
				animateNpc(value, seqId, npc);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_RUNWEIGHT) {
			InterfaceList.redrawActiveInterfaces();
			Player.weight = inboundBuffer.g2b();
			InterfaceList.miscTransmitAt = InterfaceList.transmitTimer;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_PRIVATE_ECHO) {
			long name37 = inboundBuffer.g8();
			JagString message = Font.escape(formatChatMessage(inboundBuffer).encodeMessage());
			Chat.add(Base37.decode37(name37).toTitleCase(), 6, message);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.URL_OPEN) {
			if (GameShell.fullScreenFrame != null) {
				DisplayMode.setWindowMode(false, Preferences.favoriteWorlds, -1, -1);
			}
			@Pc(3848) byte[] data = new byte[length];
			inboundBuffer.gBytesIsaac(data, length);
			JagString url = JagString.decodeString(data, length, 0);
			if (GameShell.frame == null && (SignLink.clientMode == 3 || !SignLink.osName.startsWith("win") || client.haveIe6)) {
				ScriptRunner.openUrl(url, true);
			} else {
				ScriptRunner.url = url;
				newTab = true;
				openUrlRequest = GameShell.signLink.openUrl(new String(url.toByteArray(), StandardCharsets.ISO_8859_1));
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.GENERATE_CHAT_HEAD_FROM_BODY) {
			int tracknum = inboundBuffer.g2add();
			int id = inboundBuffer.mg4();
			int value1 = inboundBuffer.ig2add();
			int value2 = inboundBuffer.ig2();
			int value3 = inboundBuffer.ig2add();
			setVerifyId(tracknum);
			DelayedStateChange.updateComponentModel(value1, 7, id, value2 << 16 | value3);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.VARBIT_SMALL) {
			int value = inboundBuffer.g1add();
			int id = inboundBuffer.ig2();
			VarpDomain.setVarbit(value, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_OPENTOP) {
			int type = inboundBuffer.g1();
			int pointer = inboundBuffer.mg4();
			int tracknum = inboundBuffer.g2add();
			int component = inboundBuffer.g2();
			setVerifyId(tracknum);
			ComponentPointer ptr = (ComponentPointer) InterfaceList.openInterfaces.get(pointer);
			if (ptr != null) {
				InterfaceList.closeInterface(ptr.interfaceId != component, ptr);
			}
			openTopLevelInterface(component, pointer, type);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.RESET_ANIMS) {
			for (int i = 0; i < PlayerList.players.length; i++) {
				if (PlayerList.players[i] != null) {
					PlayerList.players[i].seqId = -1;
				}
			}
			for (int i = 0; i < NpcList.npcs.length; i++) {
				if (NpcList.npcs[i] != null) {
					NpcList.npcs[i].seqId = -1;
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.HINT_ARROW) {
			int flags = inboundBuffer.g1();
			@Pc(4084) MapMarker mapMarker = new MapMarker();
			int slot = flags >> 6;
			mapMarker.type = flags & 0x3F;
			mapMarker.arrowSpriteId = inboundBuffer.g1();
			if (mapMarker.arrowSpriteId >= 0 && mapMarker.arrowSpriteId < Sprites.headhints.length) {
				if (mapMarker.type == 1 || mapMarker.type == 10) {
					mapMarker.actorTargetId = inboundBuffer.g2();
					inboundBuffer.offset += 3;
				} else if (mapMarker.type >= 2 && mapMarker.type <= 6) {
					if (mapMarker.type == 2) {
						mapMarker.targetXFine = 64;
						mapMarker.targetYFine = 64;
					}
					if (mapMarker.type == 3) {
						mapMarker.targetXFine = 0;
						mapMarker.targetYFine = 64;
					}
					if (mapMarker.type == 4) {
						mapMarker.targetXFine = 128;
						mapMarker.targetYFine = 64;
					}
					if (mapMarker.type == 5) {
						mapMarker.targetXFine = 64;
						mapMarker.targetYFine = 0;
					}
					if (mapMarker.type == 6) {
						mapMarker.targetXFine = 64;
						mapMarker.targetYFine = 128;
					}
					mapMarker.type = 2;
					mapMarker.targetX = inboundBuffer.g2();
					mapMarker.targetY = inboundBuffer.g2();
					mapMarker.targetHeight = inboundBuffer.g1();
				}
				mapMarker.playerModelId = inboundBuffer.g2();
				if (mapMarker.playerModelId == 65535) {
					mapMarker.playerModelId = -1;
				}
				MiniMap.hintMapMarkers[slot] = mapMarker;
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_IGNORELIST) {
			IgnoreList.size = length / 8;
			for (int i = 0; i < IgnoreList.size; i++) {
				IgnoreList.encodedUsernames[i] = inboundBuffer.g8();
				IgnoreList.usernames[i] = Base37.decode37(IgnoreList.encodedUsernames[i]);
			}
			FriendsList.transmitAt = InterfaceList.transmitTimer;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.NPC_INFO) {
			readNpcPacket();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETPOSITION) {
			int tracknum = inboundBuffer.g2add();
			int ptr = inboundBuffer.ig4();
			int x = inboundBuffer.g2b();
			int y = inboundBuffer.g2badd();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentPositionServer(x, ptr, y);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.LOC_ANIM_SPECIFIC) {
			int slot = inboundBuffer.g1sub();
			int type = slot >> 2;
			int rotation = slot & 0x3;
			int type2 = Loc.LAYERS[type];
			int seqId = inboundBuffer.g2();
			int pos = inboundBuffer.g4();
			if (seqId == 65535) {
				seqId = -1;
			}
			int y = pos & 0x3FFF;
			int x = pos >> 14 & 0x3FFF;
			x -= Camera.originX;
			y -= Camera.originY;
			int plane = pos >> 28 & 0x3;
			SceneGraph.addLoc(plane, rotation, type, y, type2, x, seqId);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_PRIVATE) {
			long name37 = inboundBuffer.g8();
			int top = inboundBuffer.g2();
			int bot = inboundBuffer.g3();
			int rights = inboundBuffer.g1();
			@Pc(4425) boolean ignored = false;
			@Pc(4431) long messageId = bot + ((long) top << 32);
			int ix = 0;
			check:
			while (true) {
				if (ix >= 100) {
					if (rights <= 1) {
						if (LoginManager.playerUnderage && !LoginManager.parentalChatConsent || LoginManager.mapQuickChat) {
							ignored = true;
						} else {
							for (ix = 0; ix < IgnoreList.size; ix++) {
								if (name37 == IgnoreList.encodedUsernames[ix]) {
									ignored = true;
									break check;
								}
							}
						}
					}
					break;
				}
				if (messageId == Chat.recentMessages[ix]) {
					ignored = true;
					break;
				}
				ix++;
			}
			if (!ignored && Player.inTutorialIsland == 0) {
				Chat.recentMessages[Chat.messageCounter] = messageId;
				Chat.messageCounter = (Chat.messageCounter + 1) % 100;
				@Pc(4518) JagString message = Font.escape(formatChatMessage(inboundBuffer).encodeMessage());
				if (rights == 2 || rights == 3) {
					Chat.add(JagString.concatenate(new JagString[]{IMG1, Base37.decode37(name37).toTitleCase()}), 7, message);
				} else if (rights == 1) {
					Chat.add(JagString.concatenate(new JagString[]{IMG0, Base37.decode37(name37).toTitleCase()}), 7, message);
				} else {
					Chat.add(Base37.decode37(name37).toTitleCase(), 3, message);
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MESSAGE_CLANCHANNEL) {
			long name37 = inboundBuffer.g8();
			inboundBuffer.g1b();
			long chat37 = inboundBuffer.g8();
			int top = inboundBuffer.g2();
			int bot = inboundBuffer.g3();
			@Pc(4626) long messageId = ((long) top << 32) + bot;
			int rights = inboundBuffer.g1();
			@Pc(4632) boolean ignored = false;
			@Pc(4634) int ix = 0;
			check:
			while (true) {
				if (ix >= 100) {
					if (rights <= 1) {
						if (LoginManager.playerUnderage && !LoginManager.parentalChatConsent || LoginManager.mapQuickChat) {
							ignored = true;
						} else {
							for (ix = 0; ix < IgnoreList.size; ix++) {
								if (IgnoreList.encodedUsernames[ix] == name37) {
									ignored = true;
									break check;
								}
							}
						}
					}
					break;
				}
				if (Chat.recentMessages[ix] == messageId) {
					ignored = true;
					break;
				}
				ix++;
			}
			if (!ignored && Player.inTutorialIsland == 0) {
				Chat.recentMessages[Chat.messageCounter] = messageId;
				Chat.messageCounter = (Chat.messageCounter + 1) % 100;
				JagString message = Font.escape(formatChatMessage(inboundBuffer).encodeMessage());
				if (rights == 2 || rights == 3) {
					Chat.addClanChannelMessage(message, JagString.concatenate(new JagString[]{IMG1, Base37.decode37(name37).toTitleCase()}), Base37.decode37(chat37).toTitleCase());
				} else if (rights == 1) {
					Chat.addClanChannelMessage(message, JagString.concatenate(new JagString[]{IMG0, Base37.decode37(name37).toTitleCase()}), Base37.decode37(chat37).toTitleCase());
				} else {
					Chat.addClanChannelMessage(message, Base37.decode37(name37).toTitleCase(), Base37.decode37(chat37).toTitleCase());
				}
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.REBUILD_REGION) {
			readRebuildPacket(true);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SYNTH_SOUND) {
			int trackId = inboundBuffer.g2();
			int volume = inboundBuffer.g1();
			if (trackId == 65535) {
				trackId = -1;
			}
			int delay = inboundBuffer.g2();
			SoundPlayer.play(volume, trackId, delay);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETPLAYERHEAD) {
			int tracknum = inboundBuffer.ig2add();
			int id = inboundBuffer.img4();
			setVerifyId(tracknum);
			int set = 0;
			if (PlayerList.self.appearance != null) {
				set = PlayerList.self.appearance.getAppearanceHash();
			}
			DelayedStateChange.updateComponentModel(-1, 3, id, set);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.IF_SETTEXT1) {
			int id = inboundBuffer.mg4();
			JagString text = inboundBuffer.gjstr();
			int tracknum = inboundBuffer.g2add();
			setVerifyId(tracknum);
			DelayedStateChange.setComponentTextServer(text, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.VARBIT_LARGE) {
			int value = inboundBuffer.ig4();
			int id = inboundBuffer.ig2add();
			VarpDomain.setVarbit(value, id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_INV_PARTIAL) {
			int componentHash = inboundBuffer.g4();
			int containerId = inboundBuffer.g2();
			if (componentHash < -70000) {
				containerId += 32768;
			}
			Component component;
			if (componentHash < 0) {
				component = null;
			} else {
				component = InterfaceList.getComponent(componentHash);
			}
			while (inboundBuffer.offset < length) {
				int slot = inboundBuffer.gsmarts();
				int amount = inboundBuffer.g2();
				int id = 0;
				if (amount != 0) {
					id = inboundBuffer.g1();
					if (id == 255) {
						id = inboundBuffer.g4();
					}
				}
				if (component != null && slot >= 0 && component.objTypes.length > slot) {
					component.objTypes[slot] = amount;
					component.objCounts[slot] = id;
				}
				Inv.updateContainer(amount - 1, slot, id, containerId);
			}
			if (component != null) {
				InterfaceList.redraw(component);
			}
			InterfaceList.redrawActiveInterfaces();
			Inv.updatedInventories[Inv.updatedInventoriesWriterIndex++ & 0x1F] = containerId & 0x7FFF;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.CAM_RESET) {
			int tracknum = inboundBuffer.g2();
			setVerifyId(tracknum);
			Camera.resetCameraEffects();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.LOGOUT) {
			LoginManager.processLogout();
			opcode = -1;
			return false;
		} else if (opcode == ServerProt.GRAND_EXCHANGE_OFFERS) {
			int offer = inboundBuffer.g1();
			if (inboundBuffer.g1() == 0) {
				StockMarketManager.offers[offer] = new StockMarketOffer();
			} else {
				inboundBuffer.offset--;
				StockMarketManager.offers[offer] = new StockMarketOffer(inboundBuffer);
			}
			opcode = -1;
			StockMarketManager.transmitAt = InterfaceList.transmitTimer;
			return true;
		} else if (opcode == ServerProt.IF_SETNPCHEAD) {
			int npcId = inboundBuffer.g2add();
			int id = inboundBuffer.ig4();
			if (npcId == 65535) {
				npcId = -1;
			}
			int tracknum = inboundBuffer.ig2();
			setVerifyId(tracknum);
			DelayedStateChange.updateComponentModel(-1, 2, id, npcId);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.REBUILD_NORMAL) {
			readRebuildPacket(false);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SET_INTERFACE_SETTINGS) {
			int tracknum = inboundBuffer.ig2();
			int end = inboundBuffer.ig2();
			if (end == 65535) {
				end = -1;
			}
			int pointer = inboundBuffer.g4();
			int start = inboundBuffer.g2add();
			int accessMask = inboundBuffer.img4();
			if (start == 65535) {
				start = -1;
			}
			setVerifyId(tracknum);
			for (int slot = start; slot <= end; slot++) {
				long key = ((long) pointer << 32) + ((long) slot);
				ServerActiveProperties properties = (ServerActiveProperties) InterfaceList.properties.get(key);
				ServerActiveProperties target;
				if (properties != null) {
					target = new ServerActiveProperties(accessMask, properties.targetParam);
					properties.unlink();
				} else if (slot == -1) {
					target = new ServerActiveProperties(accessMask, InterfaceList.getComponent(pointer).properties.targetParam);
				} else {
					target = new ServerActiveProperties(accessMask, -1);
				}
				InterfaceList.properties.put(target, key);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.FRIENDLIST_LOADED) {
			FriendsList.state = inboundBuffer.g1();
			FriendsList.transmitAt = InterfaceList.transmitTimer;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_CLAN) {
			long name37 = inboundBuffer.g8();
			int worldId = inboundBuffer.g2();
			@Pc(5325) byte rights = inboundBuffer.g1b();
			boolean ignored = (Long.MIN_VALUE & name37) != 0L;
			if (ignored) {
				if (ClanChat.size == 0) {
					opcode = -1;
					return true;
				}
				name37 &= Long.MAX_VALUE;
				int i;
				for (i = 0; ClanChat.size > i && (name37 != ClanChat.members[i].key || worldId != ClanChat.members[i].world); i++) {
				}
				if (i < ClanChat.size) {
					while (ClanChat.size - 1 > i) {
						ClanChat.members[i] = ClanChat.members[i + 1];
						i++;
					}
					ClanChat.size--;
					ClanChat.members[ClanChat.size] = null;
				}
			} else {
				JagString worldName = inboundBuffer.gjstr();
				@Pc(5347) ClanMember member = new ClanMember();
				member.key = name37;
				member.username = Base37.decode37(member.key);
				member.rank = rights;
				member.worldName = worldName;
				member.world = worldId;
				int n;
				for (n = ClanChat.size - 1; n >= 0; n--) {
					int m = ClanChat.members[n].username.compareTo(member.username);
					if (m == 0) {
						ClanChat.members[n].world = worldId;
						ClanChat.members[n].rank = rights;
						ClanChat.members[n].worldName = worldName;
						if (name37 == Player.name37) {
							ClanChat.rank = rights;
						}
						ClanChat.transmitAt = InterfaceList.transmitTimer;
						opcode = -1;
						return true;
					}
					if (m < 0) {
						break;
					}
				}
				if (ClanChat.members.length <= ClanChat.size) {
					opcode = -1;
					return true;
				}
				for (int i = ClanChat.size - 1; i > n; i--) {
					ClanChat.members[i + 1] = ClanChat.members[i];
				}
				if (ClanChat.size == 0) {
					ClanChat.members = new ClanMember[100];
				}
				ClanChat.members[n + 1] = member;
				if (Player.name37 == name37) {
					ClanChat.rank = rights;
				}
				ClanChat.size++;
			}
			opcode = -1;
			ClanChat.transmitAt = InterfaceList.transmitTimer;
			return true;
		} else if (opcode == ServerProt.IF_SETOBJECT) {
			int slot = inboundBuffer.g4();
			int id = inboundBuffer.mg4();
			int itemId = inboundBuffer.ig2add();
			if (itemId == 65535) {
				itemId = -1;
			}
			int tracknum = inboundBuffer.ig2();
			setVerifyId(tracknum);
			@Pc(5603) Component component = InterfaceList.getComponent(id);
			@Pc(5615) ObjType objType;
			if (component.if3) {
				DelayedStateChange.setComponentObjServer(id, slot, itemId);
				objType = ObjTypeList.get(itemId);
				DelayedStateChange.updateView(objType.zoom2d, id, objType.yAngle2D, objType.xAngle2D);
				DelayedStateChange.setComponentModelOffsetServer(id, objType.zAngle2D, objType.yOffset2D, objType.xOffset2D);
			} else if (itemId == -1) {
				component.modelType = 0;
				opcode = -1;
				return true;
			} else {
				objType = ObjTypeList.get(itemId);
				component.modelXAngle = objType.xAngle2D;
				component.modelZoom = objType.zoom2d * 100 / slot;
				component.modelType = 4;
				component.modelId = itemId;
				component.modelYAngle = objType.yAngle2D;
				InterfaceList.redraw(component);
			}
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_INV_FULL) {
			int componentHash = inboundBuffer.g4();
			int containerId = inboundBuffer.g2();
			if (componentHash < -70000) {
				containerId += 32768;
			}
			Component component;
			if (componentHash >= 0) {
				component = InterfaceList.getComponent(componentHash);
			} else {
				component = null;
			}
			if (component != null) {
				for (int i = 0; i < component.objTypes.length; i++) {
					component.objTypes[i] = 0;
					component.objCounts[i] = 0;
				}
			}
			Inv.clearContainer(containerId);
			int total = inboundBuffer.g2();
			for (int slot = 0; slot < total; slot++) {
				int amount = inboundBuffer.g1sub();
				if (amount == 255) {
					amount = inboundBuffer.g4();
				}
				int itemId = inboundBuffer.g2();
				if (component != null && slot < component.objTypes.length) {
					component.objTypes[slot] = itemId;
					component.objCounts[slot] = amount;
				}
				Inv.updateContainer(itemId - 1, slot, amount, containerId);
			}
			if (component != null) {
				InterfaceList.redraw(component);
			}
			InterfaceList.redrawActiveInterfaces();
			Inv.updatedInventories[Inv.updatedInventoriesWriterIndex++ & 0x1F] = containerId & 0x7FFF;
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.SET_SETTINGS_STRING) {
			saveSettingsCookie(inboundBuffer.gjstr());
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.UPDATE_ZONE_PARTIAL_FOLLOWS) {
			SceneGraph.currentChunkX = inboundBuffer.g1neg();
			SceneGraph.currentChunkY = inboundBuffer.g1();
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MIDI_SONG) {
			int id = inboundBuffer.ig2add();
			if (id == 65535) {
				id = -1;
			}
			MusicPlayer.playSong(id);
			opcode = -1;
			return true;
		} else if (opcode == ServerProt.MIDI_JINGLE) {
			int volume = inboundBuffer.ig3();
			int id = inboundBuffer.ig2();
			if (id == 65535) {
				id = -1;
			}
			MusicPlayer.playJingle(volume, id);
			opcode = -1;
			return true;
		} else {
			TracingException.report("T1 - " + opcode + "," + opcode3 + "," + opcode4 + " - " + length, null);
			LoginManager.processLogout();
			return true;
		}
	}

	@OriginalMember(owner = "client!an", name = "h", descriptor = "(I)Z")
	public static boolean readPacket() {
		try {
			return readPacketInternal();
		} catch (@Pc(14) IOException ex) {
			ex.printStackTrace();
			handleConnectionLost();
			return true;
		} catch (@Pc(19) Exception ex) {
			ex.printStackTrace();
			@Pc(61) String errorMsg = "T2 - " + opcode + "," + opcode3 + "," + opcode4 + " - " + length + "," + (Camera.originX + PlayerList.self.movementQueueX[0]) + "," + (PlayerList.self.movementQueueY[0] + Camera.originY) + " - ";
			for (@Pc(63) int i = 0; i < length && i < 50; i++) {
				errorMsg = errorMsg + inboundBuffer.data[i] + ",";
			}
			TracingException.report(errorMsg, ex);
			LoginManager.processLogout();
			return true;
		}
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(Z)V")
	public static void loop() {
		// todo: consolidate/rename static classes
		if (logoutOnDisconnectTimer > 0) {
			logoutOnDisconnectTimer--;
		}
		if (Player.rebootTimer > 1) {
			Player.rebootTimer--;
			InterfaceList.miscTransmitAt = InterfaceList.transmitTimer;
		}
		if (LoginManager.pingFailed) {
			LoginManager.pingFailed = false;
			handleConnectionLost();
			return;
		}
		for (@Pc(34) int i = 0; i < 100 && readPacket(); i++) {
		}
		if (client.gameState != 30) {
			return;
		}
		ReflectionCheck.loop(outboundBuffer);
		@Pc(60) Object mouseRecorder = MouseRecorder.instance.lock;
		@Pc(86) int offset;
		@Pc(79) int type;
		@Pc(88) int i;
		@Pc(106) int y;
		@Pc(111) int x;
		@Pc(182) int dx;
		@Pc(189) int dy;
		synchronized (mouseRecorder) {
			if (!MouseRecorder.enabled) {
				MouseRecorder.instance.samples = 0;
			} else if (Mouse.clickButton != 0 || MouseRecorder.instance.samples >= 40) {
				outboundBuffer.p1isaac(ClientProt.EVENT_MOUSE_MOVE);
				type = 0;
				outboundBuffer.p1(0);
				offset = outboundBuffer.offset;
				for (i = 0; MouseRecorder.instance.samples > i && outboundBuffer.offset - offset < 240; i++) {
					type++;
					y = MouseRecorder.instance.y[i];
					x = MouseRecorder.instance.x[i];
					if (y < 0) {
						y = 0;
					} else if (y > 65534) {
						y = 65534;
					}
					if (x < 0) {
						x = 0;
					} else if (x > 65534) {
						x = 65534;
					}
					@Pc(142) boolean outsideWindow = false;
					if (MouseRecorder.instance.y[i] == -1 && MouseRecorder.instance.x[i] == -1) {
						outsideWindow = true;
						y = -1;
						x = -1;
					}
					if (MouseRecorder.mouseRecorderPrevX != x || y != MouseRecorder.mouseRecorderPrevY) {
						dx = x - MouseRecorder.mouseRecorderPrevX;
						MouseRecorder.mouseRecorderPrevX = x;
						dy = y - MouseRecorder.mouseRecorderPrevY;
						MouseRecorder.mouseRecorderPrevY = y;
						if (mouseIdleSamples < 8 && dx >= -32 && dx <= 31 && dy >= -32 && dy <= 31) {
							dy += 32;
							dx += 32;
							outboundBuffer.p2(dy + (mouseIdleSamples << 12) + (dx << 6));
							mouseIdleSamples = 0;
						} else if (mouseIdleSamples < 32 && dx >= -128 && dx <= 127 && dy >= -128 && dy <= 127) {
							outboundBuffer.p1(mouseIdleSamples + 128);
							dy += 128;
							dx += 128;
							outboundBuffer.p2((dx << 8) + dy);
							mouseIdleSamples = 0;
						} else if (mouseIdleSamples < 32) {
							outboundBuffer.p1(mouseIdleSamples + 192);
							if (outsideWindow) {
								outboundBuffer.p4(Integer.MIN_VALUE);
							} else {
								outboundBuffer.p4(x | y << 16);
							}
							mouseIdleSamples = 0;
						} else {
							outboundBuffer.p2(mouseIdleSamples + 57344);
							if (outsideWindow) {
								outboundBuffer.p4(Integer.MIN_VALUE);
							} else {
								outboundBuffer.p4(x | y << 16);
							}
							mouseIdleSamples = 0;
						}
					} else if (mouseIdleSamples < 2047) {
						mouseIdleSamples++;
					}
				}
				outboundBuffer.psize1(outboundBuffer.offset - offset);
				if (MouseRecorder.instance.samples > type) {
					MouseRecorder.instance.samples -= type;
					for (i = 0; i < MouseRecorder.instance.samples; i++) {
						MouseRecorder.instance.x[i] = MouseRecorder.instance.x[type + i];
						MouseRecorder.instance.y[i] = MouseRecorder.instance.y[type + i];
					}
				} else {
					MouseRecorder.instance.samples = 0;
				}
			}
		}
		if (Mouse.clickButton != 0) {
			@Pc(411) long loops = (Mouse.clickTime - Mouse.prevClickTime) / 50L;
			type = Mouse.clickY;
			if (type < 0) {
				type = 0;
			} else if (type > 65535) {
				type = 65535;
			}
			if (loops > 32767L) {
				loops = 32767L;
			}
			i = Mouse.clickX;
			Mouse.prevClickTime = Mouse.clickTime;
			@Pc(437) byte button = 0;
			if (i < 0) {
				i = 0;
			} else if (i > 65535) {
				i = 65535;
			}
			x = (int) loops;
			if (Mouse.clickButton == 2) {
				button = 1;
			}
			outboundBuffer.p1isaac(ClientProt.EVENT_MOUSE_CLICK);
			outboundBuffer.ip2add(button << 15 | x);
			outboundBuffer.mp4(i | type << 16);
		}
		if (cameraSendCooldown > 0) {
			cameraSendCooldown--;
		}
		if (Preferences.keyboardCameraEnabled) {
			for (i = 0; i < InterfaceList.keyQueueSize; i++) {
				offset = InterfaceList.keyCodes[i];
				if (offset == 98 || offset == 99 || offset == 96 || offset == 97) {
					cameraPositionChanged = true;
					break;
				}
			}
		} else if (Keyboard.pressedKeys[Keyboard.KEY_LEFT] || Keyboard.pressedKeys[Keyboard.KEY_RIGHT] || Keyboard.pressedKeys[Keyboard.KEY_UP] || Keyboard.pressedKeys[Keyboard.KEY_DOWN]) {
			cameraPositionChanged = true;
		}
		if (cameraPositionChanged && cameraSendCooldown <= 0) {
			cameraSendCooldown = 20;
			cameraPositionChanged = false;
			outboundBuffer.p1isaac(ClientProt.EVENT_CAMERA_POSITION);
			outboundBuffer.p2add((int) Camera.pitchTarget);
			outboundBuffer.ip2((int) Camera.yawTarget);
		}
		if (GameShell.focus && !prevFocus) {
			prevFocus = true;
			outboundBuffer.p1isaac(ClientProt.EVENT_APPLET_FOCUS);
			outboundBuffer.p1(1);
		}
		if (!GameShell.focus && prevFocus) {
			prevFocus = false;
			outboundBuffer.p1isaac(ClientProt.EVENT_APPLET_FOCUS);
			outboundBuffer.p1(0);
		}
		if (!Preferences.sentToServer) {
			outboundBuffer.p1isaac(98);
			outboundBuffer.p4(Preferences.toInt());
			Preferences.sentToServer = true;
		}
		SceneGraph.checkPlaneChange();
		if (client.gameState != 30) {
			return;
		}
		ChangeLocRequest.loop();
		AttachLocRequest.loop();
		SoundPlayer.loop();
		LoginManager.ticksSinceLastPacket++;
		if (LoginManager.ticksSinceLastPacket > 750) {
			handleConnectionLost();
			return;
		}
		PlayerList.processAllPlayers();
		NpcList.processAllNpcs();
		OverheadChat.loop();
		if (WorldMap.component != null) {
			WorldMap.updateZoomAndScroll();
		}
		// VarpDomain
		for (i = VarpDomain.poll(true); i != -1; i = VarpDomain.poll(false)) {
			VarpDomain.refreshMagicVarp(i);
			VarpDomain.updatedVarps[VarpDomain.updatedVarpsWriterIndex++ & 0x1F] = i;
		}
		@Pc(782) int modelId;
		// DelayedStateChange
		for (@Pc(709) DelayedStateChange change = DelayedStateChange.poll(); change != null; change = DelayedStateChange.poll()) {
			type = change.getType();
			i = change.getId();
			if (type == 1) {
				VarcDomain.varcs[i] = change.intArg1;
				VarcDomain.updatedVarcs[VarcDomain.updatedVarcsWriterIndex++ & 0x1F] = i;
			} else if (type == 2) {
				VarcDomain.varcstrs[i] = change.stringArg;
				VarcDomain.updatedVarcstrs[VarcDomain.updatedVarcstrsWriterIndex++ & 0x1F] = i;
			} else {
				@Pc(773) Component component;
				if (type == 3) {
					component = InterfaceList.getComponent(i);
					if (!change.stringArg.strEquals(component.text)) {
						component.text = change.stringArg;
						InterfaceList.redraw(component);
					}
				} else if (type == 4) {
					component = InterfaceList.getComponent(i);
					x = change.intArg1;
					dx = change.intArg2;
					modelId = change.intArg3;
					if (component.modelType != x || component.modelId != modelId || dx != component.modelFrameCycle) {
						component.modelId = modelId;
						component.modelFrameCycle = dx;
						component.modelType = x;
						InterfaceList.redraw(component);
					}
				} else if (type == 5) {
					component = InterfaceList.getComponent(i);
					if (component.modelSeqId != change.intArg1 || change.intArg1 == -1) {
						component.seqNextFrame = 1;
						component.seqCycle = 0;
						component.modelSeqId = change.intArg1;
						component.seqFrame = 0;
						InterfaceList.redraw(component);
					}
				} else if (type == 6) {
					y = change.intArg1;
					x = y >> 10 & 0x1F;
					dx = y & 0x1F;
					modelId = y >> 5 & 0x1F;
					@Pc(1189) Component comp = InterfaceList.getComponent(i);
					dy = (dx << 3) + (modelId << 11) + (x << 19);
					if (dy != comp.color) {
						comp.color = dy;
						InterfaceList.redraw(comp);
					}
				} else if (type == 7) {
					component = InterfaceList.getComponent(i);
					@Pc(1145) boolean hidden = change.intArg1 == 1;
					if (component != null && hidden != component.hidden) {
						component.hidden = hidden;
						InterfaceList.redraw(component);
					}
				} else if (type == 8) {
					component = InterfaceList.getComponent(i);
					if (change.intArg1 != component.modelXAngle || component.modelYAngle != change.intArg3 || change.intArg2 != component.modelZoom) {
						component.modelXAngle = change.intArg1;
						component.modelZoom = change.intArg2;
						component.modelYAngle = change.intArg3;
						if (component.objId != -1) {
							if (component.modelViewportWidth > 0) {
								component.modelZoom = component.modelZoom * 32 / component.modelViewportWidth;
							} else if (component.baseWidth > 0) {
								component.modelZoom = component.modelZoom * 32 / component.baseWidth;
							}
						}
						InterfaceList.redraw(component);
					}
				} else if (type == 9) {
					component = InterfaceList.getComponent(i);
					if (change.intArg1 != component.objId || component.objCount != change.intArg3) {
						component.objId = change.intArg1;
						component.objCount = change.intArg3;
						InterfaceList.redraw(component);
					}
				} else if (type == 10) {
					component = InterfaceList.getComponent(i);
					if (component.modelXOffset != change.intArg1 || change.intArg3 != component.modelZOffset || component.modelYOffset != change.intArg2) {
						component.modelZOffset = change.intArg3;
						component.modelYOffset = change.intArg2;
						component.modelXOffset = change.intArg1;
						InterfaceList.redraw(component);
					}
				} else if (type == 11) {
					component = InterfaceList.getComponent(i);
					component.x = component.baseX = change.intArg1;
					component.yMode = 0;
					component.xMode = 0;
					component.y = component.baseY = change.intArg3;
					InterfaceList.redraw(component);
				} else if (type == 12) {
					component = InterfaceList.getComponent(i);
					int scrollY = change.intArg1;
					if (component != null && component.type == 0) {
						if (scrollY > component.scrollMaxV - component.height) {
							scrollY = component.scrollMaxV - component.height;
						}
						if (scrollY < 0) {
							scrollY = 0;
						}
						if (scrollY != component.scrollY) {
							component.scrollY = scrollY;
							InterfaceList.redraw(component);
						}
					}
				} else if (type == 13) {
					component = InterfaceList.getComponent(i);
					component.modelRotationSpeed = change.intArg1;
				}
			}
		}
		if (Cross.type != 0) {
			Cross.milliseconds += 20;
			if (Cross.milliseconds >= 400) {
				Cross.type = 0;
			}
		}
		sceneDelta++;
		if (MiniMenu.pressedInventoryComponent != null) {
			MiniMenu.itemInteractionTick++;
			if (MiniMenu.itemInteractionTick >= 15) {
				InterfaceList.redraw(MiniMenu.pressedInventoryComponent);
				MiniMenu.pressedInventoryComponent = null;
			}
		}

		@Pc(1361) Component component;
		if (InterfaceList.clickedInventoryComponent != null) {
			InterfaceList.redraw(InterfaceList.clickedInventoryComponent);
			if (InterfaceList.clickedInventoryComponentX + 5 < Mouse.lastMouseX || Mouse.lastMouseX < InterfaceList.clickedInventoryComponentX - 5 || InterfaceList.clickedInventoryComponentY + 5 < Mouse.lastMouseY || InterfaceList.clickedInventoryComponentY - 5 > Mouse.lastMouseY) {
				InterfaceList.draggingClickedInventoryObject = true;
			}
			InterfaceList.clickedInventoryComponentCycle++;

			if (Mouse.pressedButton == 0) {
				if (InterfaceList.draggingClickedInventoryObject && InterfaceList.clickedInventoryComponentCycle >= 5) {
					if (InterfaceList.clickedInventoryComponent == InterfaceList.mouseOverInventoryInterface && InterfaceList.mouseOverInventoryObjectIndex != MiniMenu.clickedInventoryIndex) {
						component = InterfaceList.clickedInventoryComponent;
						@Pc(1363) byte inserting = 0;

						if (VarpDomain.inserting == 1 && component.clientCode == 206) {
							inserting = 1;
						}

						if (component.objTypes[MiniMenu.clickedInventoryIndex] <= 0) {
							inserting = 0;
						}

						if (InterfaceList.getServerActiveProperties(component).isObjReplaceEnabled()) {
							int newIndex = InterfaceList.mouseOverInventoryObjectIndex;
							int currentIndex = MiniMenu.clickedInventoryIndex;
							component.objTypes[currentIndex] = component.objTypes[newIndex];
							component.objCounts[currentIndex] = component.objCounts[newIndex];
							component.objTypes[newIndex] = -1;
							component.objCounts[newIndex] = 0;
						} else if (inserting == 1) {
							int currentIndex = MiniMenu.clickedInventoryIndex;
							int newIndex = InterfaceList.mouseOverInventoryObjectIndex;
							while (currentIndex != newIndex) {
								if (currentIndex > newIndex) {
									component.swapObjs(currentIndex - 1, currentIndex);
									currentIndex--;
								} else {
									component.swapObjs(currentIndex + 1, currentIndex);
									currentIndex++;
								}
							}
						} else {
							component.swapObjs(MiniMenu.clickedInventoryIndex, InterfaceList.mouseOverInventoryObjectIndex);
						}
						outboundBuffer.p1isaac(231);
						outboundBuffer.p2(InterfaceList.mouseOverInventoryObjectIndex);
						outboundBuffer.ip4(InterfaceList.clickedInventoryComponent.id);
						outboundBuffer.p2add(MiniMenu.clickedInventoryIndex);
						outboundBuffer.p1sub(inserting);
					}
				} else if ((VarpDomain.mouseButtons == 1 || MiniMenu.isComponentOptionAction(MiniMenu.size - 1)) && MiniMenu.size > 2) {
					ScriptRunner.layoutMiniMenu();
				} else if (MiniMenu.size > 0) {
					MiniMenu.processClick();
				}
				Mouse.clickButton = 0;
				MiniMenu.itemInteractionTick = 10;
				InterfaceList.clickedInventoryComponent = null;
			}
		}
		InterfaceList.dragParentFound = false;
		InterfaceList.dragTargetComponent = null;
		InterfaceList.dragSourceFound = false;
		InterfaceList.keyQueueSize = 0;
		component = InterfaceList.hoveredComponent;
		InterfaceList.hoveredComponent = null;
		@Pc(1508) Component prevTooltip = tooltipComponent;
		tooltipComponent = null;
		while (Keyboard.nextKey() && InterfaceList.keyQueueSize < 128) {
			InterfaceList.keyCodes[InterfaceList.keyQueueSize] = Keyboard.keyCode;
			InterfaceList.keyChars[InterfaceList.keyQueueSize] = Keyboard.keyChar;
			InterfaceList.keyQueueSize++;
		}
		WorldMap.component = null;
		if (InterfaceList.topLevelInterface != -1) {
			InterfaceList.processSubInterface(0, 0, 0, GameShell.canvasWidth, InterfaceList.topLevelInterface, 0, GameShell.canvasHeight);
		}
		InterfaceList.transmitTimer++;
		while (true) {
			@Pc(1569) Component priorityComponent;
			@Pc(1560) Component prioritySource;
			@Pc(1555) HookRequest priorityRequest;
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
											if (WorldMap.component == null) {
												InterfaceList.worldMapDragState = 0;
											}
											if (Cs1ScriptRunner.draggedComponent != null) {
												Cs1ScriptRunner.updateComponentDrag();
											}
											if (LoginManager.staffModLevel > 0 && Keyboard.pressedKeys[Keyboard.KEY_CTRL] && Keyboard.pressedKeys[Keyboard.KEY_SHIFT] && MouseWheel.wheelRotation != 0) {
												y = Player.plane - MouseWheel.wheelRotation;
												if (y < 0) {
													y = 0;
												} else if (y > 3) {
													y = 3;
												}
												// Cheat
												Cheat.teleport(PlayerList.self.movementQueueX[0] + Camera.originX, PlayerList.self.movementQueueY[0] + Camera.originY, y);
											}
											if (LoginManager.staffModLevel > 0 && Keyboard.pressedKeys[Keyboard.KEY_CTRL] && Keyboard.pressedKeys[Keyboard.KEY_SHIFT]) {
												if (MiniMenu.clickTileX != -1) {
													Cheat.teleport(Camera.originX + MiniMenu.clickTileX, Camera.originY - -MiniMenu.clickTileY, Player.plane);
												}
												viewportWalkState = 0;
												MiniMenu.minimapWalkState = 0;
											} else if (MiniMenu.minimapWalkState == 2) {
												if (MiniMenu.clickTileX != -1) {
													outboundBuffer.p1isaac(131);
													outboundBuffer.mp4(MiniMenu.targetInterfaceId);
													outboundBuffer.p2add(Camera.originX + MiniMenu.clickTileX);
													outboundBuffer.ip2add(MiniMenu.targetChildId);
													outboundBuffer.p2add(MiniMenu.clickTileY + Camera.originY);
													Cross.type = 1;
													Cross.milliseconds = 0;
													Cross.y = Mouse.clickY;
													Cross.x = Mouse.clickX;
												}
												MiniMenu.minimapWalkState = 0;
											} else if (viewportWalkState == 2) {
												if (MiniMenu.clickTileX != -1) {
													outboundBuffer.p1isaac(179);
													outboundBuffer.p2(Camera.originY + MiniMenu.clickTileY);
													outboundBuffer.p2(MiniMenu.clickTileX + Camera.originX);
													Cross.milliseconds = 0;
													Cross.type = 1;
													Cross.x = Mouse.clickX;
													Cross.y = Mouse.clickY;
												}
												viewportWalkState = 0;
											} else if (MiniMenu.clickTileX != -1 && MiniMenu.minimapWalkState == 0 && viewportWalkState == 0) {
												@Pc(1871) boolean pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, true, 0, MiniMenu.clickTileX, 0, 0, 0, MiniMenu.clickTileY, PlayerList.self.movementQueueX[0]);
												if (pathFound) {
													Cross.y = Mouse.clickY;
													Cross.milliseconds = 0;
													Cross.x = Mouse.clickX;
													Cross.type = 1;
												}
											}
											MiniMenu.clickTileX = -1;
											processDefaultAction();
											if (InterfaceList.hoveredComponent != component) {
												if (component != null) {
													InterfaceList.redraw(component);
												}
												if (InterfaceList.hoveredComponent != null) {
													InterfaceList.redraw(InterfaceList.hoveredComponent);
												}
											}
											if (prevTooltip != tooltipComponent && Cs1ScriptRunner.TOOLTIP_DISPLAY_DELAY == tooltipTimer) {
												if (prevTooltip != null) {
													InterfaceList.redraw(prevTooltip);
												}
												if (tooltipComponent != null) {
													InterfaceList.redraw(tooltipComponent);
												}
											}
											if (tooltipComponent == null) {
												if (tooltipTimer > 0) {
													tooltipTimer--;
												}
											} else if (tooltipTimer < Cs1ScriptRunner.TOOLTIP_DISPLAY_DELAY) {
												tooltipTimer++;
												if (Cs1ScriptRunner.TOOLTIP_DISPLAY_DELAY == tooltipTimer) {
													InterfaceList.redraw(tooltipComponent);
												}
											}
											if (Camera.cameraType == 1) {
												Camera.updatePlayerCamera();
											} else if (Camera.cameraType == 2) {
												Camera.updateLockedCamera();
											} else {
												Camera.updateLoginScreenCamera();
											}
											for (y = 0; y < 5; y++) {
												@Pc(2001) int phase = cameraShakePhase[y]++;
											}
											y = Mouse.getIdleLoops();
											x = Keyboard.getIdleLoops();
											if (y > 15000 && x > 15000) {
												logoutOnDisconnectTimer = 250;
												Mouse.setIdleLoops(14500);
												outboundBuffer.p1isaac(245);
											}
											if (openUrlRequest != null && openUrlRequest.status == 1) {
												if (openUrlRequest.result != null) {
													ScriptRunner.openUrl(ScriptRunner.url, newTab);
												}
												ScriptRunner.url = null;
												openUrlRequest = null;
												newTab = false;
											}
											ticksSinceWrite++;
											MiniMap.jitterTimer++;
											cameraJitterTimer++;
											if (cameraJitterTimer > 500) {
												cameraJitterTimer = 0;
												modelId = (int) (Math.random() * 8.0D);
												if ((modelId & 0x4) == 4) {
													Camera.yawDrift += yawDriftDelta;
												}
												if ((modelId & 0x2) == 2) {
													Camera.cameraOffsetY += cameraYOffsetDelta;
												}
												if ((modelId & 0x1) == 1) {
													Camera.cameraOffsetX += Camera.offsetXDelta;
												}
											}
											if (MiniMap.jitterTimer > 500) {
												MiniMap.jitterTimer = 0;
												modelId = (int) (Math.random() * 8.0D);
												if ((modelId & 0x1) == 1) {
													MiniMap.compassAngleOffset += MiniMap.angleJitterStep;
												}
												if ((modelId & 0x2) == 2) {
													MiniMap.zoomOffset += MiniMap.zoomJitterStep;
												}
											}
											if (Camera.cameraOffsetX < -50) {
												Camera.offsetXDelta = 2;
											}
											if (MiniMap.compassAngleOffset < -60) {
												MiniMap.angleJitterStep = 2;
											}
											if (MiniMap.zoomOffset < -20) {
												MiniMap.zoomJitterStep = 1;
											}
											if (Camera.cameraOffsetY < -55) {
												cameraYOffsetDelta = 2;
											}
											if (Camera.cameraOffsetY > 55) {
												cameraYOffsetDelta = -2;
											}
											if (Camera.yawDrift < -40) {
												yawDriftDelta = 1;
											}
											if (Camera.cameraOffsetX > 50) {
												Camera.offsetXDelta = -2;
											}
											if (Camera.yawDrift > 40) {
												yawDriftDelta = -1;
											}
											if (MiniMap.zoomOffset > 10) {
												MiniMap.zoomJitterStep = -1;
											}
											if (MiniMap.compassAngleOffset > 60) {
												MiniMap.angleJitterStep = -2;
											}
											if (ticksSinceWrite > 50) {
												outboundBuffer.p1isaac(93);
											}
											if (verifyIdChanged) {
												transmitVerifyId();
												verifyIdChanged = false;
											}
											try {
												if (socket != null && outboundBuffer.offset > 0) {
													socket.write(outboundBuffer.data, outboundBuffer.offset);
													ticksSinceWrite = 0;
													outboundBuffer.offset = 0;
												}
											} catch (@Pc(2266) IOException ioEx) {
												handleConnectionLost();
											}
											return;
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
						} while (priorityComponent == null || priorityComponent.createdComponents == null || priorityComponent.createdComponents.length <= prioritySource.createdComponentId || priorityComponent.createdComponents[prioritySource.createdComponentId] != prioritySource);
						ScriptRunner.run(priorityRequest);
					}
				}
				prioritySource = priorityRequest.source;
				if (prioritySource.createdComponentId < 0) {
					break;
				}
				priorityComponent = InterfaceList.getComponent(prioritySource.overlayer);
			} while (priorityComponent == null || priorityComponent.createdComponents == null || prioritySource.createdComponentId >= priorityComponent.createdComponents.length || priorityComponent.createdComponents[prioritySource.createdComponentId] != prioritySource);
			ScriptRunner.run(priorityRequest);
		}
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(I)V")
	public static void npcCombat() {
		for (@Pc(3) int i = 0; i < extendedCount; i++) {
			@Pc(10) int extendedId = extendedIds[i];
			@Pc(14) Npc npc = NpcList.npcs[extendedId];
			@Pc(18) int flags = inboundBuffer.g1();
			@Pc(43) int val1;
			@Pc(47) int val2;

			//idk
			if ((flags & 0x8) != 0) {
				flags += inboundBuffer.g1() << 8;
			}

			boolean hasHitsplat = (flags & 0x40) != 0;
			if (hasHitsplat) {
				val1 = inboundBuffer.g1(); // Hit value
				val2 = inboundBuffer.g1neg(); // Color
				npc.addHit(val2, client.loop, val1);
				npc.hitpointsBarVisibleUntil = client.loop + 300;
				npc.hitpointsBar = inboundBuffer.g1sub();
			}

			boolean hasSecondaryHitsplat = (flags & 0x2) != 0;
			if (hasSecondaryHitsplat) {
				val1 = inboundBuffer.g1neg(); // Hit value
				val2 = inboundBuffer.g1sub(); // Color
				npc.addHit(val2, client.loop, val1);
			}

			boolean hasAnimation = (flags & 0x10) != 0;
			if (hasAnimation) {
				val1 = inboundBuffer.g2(); // Animation ID
				val2 = inboundBuffer.g1(); // Sequence
				if (val1 == 65535) {
					val1 = -1;
				}
				animateNpc(val2, val1, npc);
			}

			boolean hasFaceEntity = (flags & 0x4) != 0;
			if (hasFaceEntity) {
				npc.faceEntity = inboundBuffer.g2add();
				if (npc.faceEntity == 65535) {
					npc.faceEntity = -1;
				}
			}

			boolean isKillingBlow = (flags & 0x80) != 0;
			if (isKillingBlow) {
				PluginRepository.OnKillingBlowNPC(npc.type.id,npc.movementQueueX[0],npc.movementQueueY[0]);
				val1 = inboundBuffer.g2add();
				if (val1 == 65535) {
					val1 = -1;
				}
				val2 = inboundBuffer.ig4();
				@Pc(147) boolean shouldApply = val1 == -1 || npc.spotAnimId == -1 || SeqTypeList.get(SpotAnimTypeList.get(val1).seqId).priority >= SeqTypeList.get(SpotAnimTypeList.get(npc.spotAnimId).seqId).priority;
				if (shouldApply) {
					npc.spotAnimId = val1;
					npc.spotAnimStart = (val2 & 0xFFFF) + client.loop;
					npc.spotAnimDelayClock = 0;
					npc.spotAnimFrame = 0;
					npc.spotAnimY = val2 >> 16;
					npc.spotAnimNextFrame = 1;
					if (npc.spotAnimStart > client.loop) {
						npc.spotAnimFrame = -1;
					}
					if (npc.spotAnimId != -1 && npc.spotAnimStart == client.loop) {
						@Pc(227) int seqId = SpotAnimTypeList.get(npc.spotAnimId).seqId;
						if (seqId != -1) {
							@Pc(236) SeqType seqType = SeqTypeList.get(seqId);
							if (seqType.frames != null) {
								SoundPlayer.playSeqSound(npc.yFine, seqType, npc.xFine, false, 0);
							}
						}
					}
				}
			}

			boolean hasSound = (flags & 0x1) != 0;
			if (hasSound) {
				if (npc.type.hasAreaSound()) {
					AreaSoundManager.remove(npc);
				}
				npc.setNpcType(NpcTypeList.get(inboundBuffer.ig2()));
				npc.setSize(npc.type.size);
				npc.basTypeId = npc.type.bastypeid;
				if (npc.type.hasAreaSound()) {
					AreaSoundManager.add(npc.movementQueueY[0], null, 0, npc, npc.movementQueueX[0], Player.plane, null);
				}
			}

			boolean hasChatMessage = (flags & 0x20) != 0;
			if (hasChatMessage) {
				npc.chatMessage = inboundBuffer.gjstr();
				npc.chatLoops = 100;
			}
			if ((flags & 0x100) != 0) {
				val1 = inboundBuffer.g1neg();
				@Pc(331) int[] seqIds = new int[val1];
				@Pc(334) int[] delays = new int[val1];
				@Pc(337) int[] slotMasks = new int[val1];
				for (@Pc(339) int i1 = 0; i1 < val1; i1++) {
					@Pc(350) int seqId = inboundBuffer.ig2();
					if (seqId == 65535) {
						seqId = -1;
					}
					seqIds[i1] = seqId;
					delays[i1] = inboundBuffer.g1sub();
					slotMasks[i1] = inboundBuffer.g2();
				}
				updateNpcSlotAnimations(slotMasks, npc, delays, seqIds);
			}

			boolean hasFaceLocation = (flags & 0x200) != 0;
			if (hasFaceLocation) {
				npc.faceX = inboundBuffer.g2add();
				npc.faceY = inboundBuffer.g2();
			}
		}
	}

	@OriginalMember(owner = "client!gm", name = "h", descriptor = "(I)V")
	public static void readNpcPacket() {
		extendedCount = 0;
		removedCount = 0;
		clearAreaNPCs();
		loadAreaNPCs();
		npcCombat();
		@Pc(19) int i;
		for (i = 0; i < removedCount; i++) {
			@Pc(30) int removedId = removedIds[i];
			if (NpcList.npcs[removedId].lastSeenLoop != client.loop) {
				if (NpcList.npcs[removedId].type.hasAreaSound()) {
					AreaSoundManager.remove(NpcList.npcs[removedId]);
				}
				NpcList.npcs[removedId].setNpcType(null);
				NpcList.npcs[removedId] = null;
			}
		}
		if (length != inboundBuffer.offset) {
			throw new RuntimeException("gnp1 pos:" + inboundBuffer.offset + " psize:" + length);
		}
		for (i = 0; i < NpcList.size; i++) {
			if (NpcList.npcs[NpcList.ids[i]] == null) {
				throw new RuntimeException("gnp2 pos:" + i + " size:" + NpcList.size);
			}
		}
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(B)V")
	public static void clearAreaNPCs() {
		inboundBuffer.accessBits();
		@Pc(13) int npcsInArea = inboundBuffer.gBits(8);
		@Pc(22) int i;
		if (NpcList.size > npcsInArea) {
			for (i = npcsInArea; i < NpcList.size; i++) {
				removedIds[removedCount++] = NpcList.ids[i];
			}
		}
		if (NpcList.size < npcsInArea) {
			throw new RuntimeException("gnpov1");
		}
		NpcList.size = 0;
		for (i = 0; i < npcsInArea; i++) {
			@Pc(61) int id = NpcList.ids[i];
			@Pc(65) Npc npc = NpcList.npcs[id];
			@Pc(70) int updated = inboundBuffer.gBits(1);
			if (updated == 0) {
				NpcList.ids[NpcList.size++] = id;
				npc.lastSeenLoop = client.loop;
			} else {
				@Pc(92) int moveType = inboundBuffer.gBits(2);
				if (moveType == 0) {
					NpcList.ids[NpcList.size++] = id;
					npc.lastSeenLoop = client.loop;
					extendedIds[extendedCount++] = id;
				} else {
					@Pc(139) int dir;
					@Pc(149) int hasExtended;
					if (moveType == 1) {
						NpcList.ids[NpcList.size++] = id;
						npc.lastSeenLoop = client.loop;
						dir = inboundBuffer.gBits(3);
						npc.move(1, dir);
						hasExtended = inboundBuffer.gBits(1);
						if (hasExtended == 1) {
							extendedIds[extendedCount++] = id;
						}
					} else if (moveType == 2) {
						NpcList.ids[NpcList.size++] = id;
						npc.lastSeenLoop = client.loop;
						if (inboundBuffer.gBits(1) == 1) {
							dir = inboundBuffer.gBits(3);
							npc.move(2, dir);
							hasExtended = inboundBuffer.gBits(3);
							npc.move(2, hasExtended);
						} else {
							dir = inboundBuffer.gBits(3);
							npc.move(0, dir);
						}
						dir = inboundBuffer.gBits(1);
						if (dir == 1) {
							extendedIds[extendedCount++] = id;
						}
					} else if (moveType == 3) {
						removedIds[removedCount++] = id;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(I)V")
	public static void loadAreaNPCs() {
		while (true) {
			if (inboundBuffer.availableBits(length) >= 27) {
				@Pc(14) int npcIndex = inboundBuffer.gBits(15);
				if (npcIndex != 32767) {
					@Pc(19) boolean isNew = false;
					if (NpcList.npcs[npcIndex] == null) {
						isNew = true;
						NpcList.npcs[npcIndex] = new Npc();
					}
					@Pc(37) Npc npc = NpcList.npcs[npcIndex];
					NpcList.ids[NpcList.size++] = npcIndex;
					npc.lastSeenLoop = client.loop;
					if (npc.type != null && npc.type.hasAreaSound()) {
						AreaSoundManager.remove(npc);
					}
					@Pc(66) int discardWalk = inboundBuffer.gBits(1);
					@Pc(73) int angle = PathingEntity.ANGLES[inboundBuffer.gBits(3)];
					if (isNew) {
						npc.targetAngle = npc.currentAngle = angle;
					}
					@Pc(86) int hasExtended = inboundBuffer.gBits(1);
					if (hasExtended == 1) {
						extendedIds[extendedCount++] = npcIndex;
					}
					@Pc(105) int dy = inboundBuffer.gBits(5);
					npc.setNpcType(NpcTypeList.get(inboundBuffer.gBits(14)));
					if (dy > 15) {
						dy -= 32;
					}
					@Pc(124) int dx = inboundBuffer.gBits(5);
					if (dx > 15) {
						dx -= 32;
					}
					npc.setSize(npc.type.size);
					npc.basTypeId = npc.type.bastypeid;
					npc.turnSpeed = npc.type.rotationspeed;
					if (npc.turnSpeed == 0) {
						npc.currentAngle = 0;
					}
					npc.teleport(npc.getSize(), PlayerList.self.movementQueueX[0] + dx, dy + PlayerList.self.movementQueueY[0], discardWalk == 1);
					if (npc.type.hasAreaSound()) {
						AreaSoundManager.add(npc.movementQueueY[0], null, 0, npc, npc.movementQueueX[0], Player.plane, null);
					}
					continue;
				}
			}
			inboundBuffer.accessBytes();
			return;
		}
	}

	@OriginalMember(owner = "client!uc", name = "a", descriptor = "(IB)Z")
	public static boolean setVerifyId(@OriginalArg(0) int id) {
		verifyId = id + 1 & 0xFFFF;
		verifyIdChanged = true;
		return true;
	}

	@OriginalMember(owner = "client!fk", name = "c", descriptor = "(I)V")
	public static void transmitVerifyId() {
		outboundBuffer.p1isaac(ClientProt.TRANSMITVAR_VERIFYID);
		outboundBuffer.p2(verifyId);
	}

	@OriginalMember(owner = "client!wc", name = "a", descriptor = "(Lclient!wa;I)V")
	public static void writeRandom(@OriginalArg(0) Buffer buffer) {
		if (client.uid != null) {
			try {
				client.uid.seek(0L);
				client.uid.write(buffer.data, buffer.offset, 24);
			} catch (@Pc(16) Exception ex) {
			}
		}
		buffer.offset += 24;
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(Z)V")
	public static void handleConnectionLost() {
		if (logoutOnDisconnectTimer > 0) {
			LoginManager.processLogout();
		} else {
			previousSocket = socket;
			socket = null;
			client.setGameState(40);
		}
	}

	@OriginalMember(owner = "client!fc", name = "a", descriptor = "(Lclient!wa;I)Lclient!na;")
	public static JagString formatChatMessage(@OriginalArg(0) Buffer buffer) {
		return decodeHuffmanMessage(buffer);
	}

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(Lclient!wa;II)Lclient!na;")
	public static JagString decodeHuffmanMessage(@OriginalArg(0) Buffer buffer) {
		try {
			@Pc(7) int msgLen = buffer.gsmarts();
			if (msgLen > 32767) {
				msgLen = 32767;
			}
			@Pc(15) byte[] decoded = new byte[msgLen];
			buffer.offset += WordPack.codec.decode(0, msgLen, decoded, buffer.data, buffer.offset);
			return JagString.decodeString(decoded, msgLen, 0);
		} catch (@Pc(47) Exception ex) {
			return WordPack.CABBAGE;
		}
	}

	@OriginalMember(owner = "client!mi", name = "a", descriptor = "([IBLclient!km;[I[I)V")
	public static void updateNpcSlotAnimations(@OriginalArg(0) int[] seqIds, @OriginalArg(2) Npc npc, @OriginalArg(3) int[] delays, @OriginalArg(4) int[] masks) {
		for (@Pc(3) int i = 0; i < masks.length; i++) {
			@Pc(15) int seqId = masks[i];
			@Pc(19) int mask = seqIds[i];
			@Pc(23) int delay = delays[i];
			for (@Pc(25) int slot = 0; mask != 0 && npc.slotAnimations.length > slot; slot++) {
				if ((mask & 0x1) != 0) {
					if (seqId == -1) {
						npc.slotAnimations[slot] = null;
					} else {
						@Pc(60) SeqType seq = SeqTypeList.get(seqId);
						@Pc(65) SlotAnimation slotAnim = npc.slotAnimations[slot];
						@Pc(68) int exactMove = seq.exactmove;
						if (slotAnim != null) {
							if (seqId == slotAnim.seqId) {
								if (exactMove == 0) {
									slotAnim = npc.slotAnimations[slot] = null;
								} else if (exactMove == 1) {
									slotAnim.currentFrame = 0;
									slotAnim.replayCount = 0;
									slotAnim.nextFrame = 1;
									slotAnim.delayClock = 0;
									slotAnim.startDelay = delay;
									SoundPlayer.playSeqSound(npc.yFine, seq, npc.xFine, false, 0);
								} else if (exactMove == 2) {
									slotAnim.replayCount = 0;
								}
							} else if (seq.priority >= SeqTypeList.get(slotAnim.seqId).priority) {
								slotAnim = npc.slotAnimations[slot] = null;
							}
						}
						if (slotAnim == null) {
							slotAnim = npc.slotAnimations[slot] = new SlotAnimation();
							slotAnim.nextFrame = 1;
							slotAnim.delayClock = 0;
							slotAnim.startDelay = delay;
							slotAnim.seqId = seqId;
							slotAnim.replayCount = 0;
							slotAnim.currentFrame = 0;
							SoundPlayer.playSeqSound(npc.yFine, seq, npc.xFine, false, 0);
						}
					}
				}
				mask >>>= 0x1;
			}
		}
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(IIILclient!km;)V")
	public static void animateNpc(@OriginalArg(0) int delay, @OriginalArg(1) int animationId, @OriginalArg(3) Npc npc) {
		if (npc.seqId == animationId && animationId != -1) {
			@Pc(10) SeqType seqType = SeqTypeList.get(animationId);
			@Pc(13) int exactMove = seqType.exactmove;
			if (exactMove == 1) {
				npc.seqNextFrame = 1;
				npc.seqFrame = 0;
				npc.seqDelayClock = 0;
				npc.seqReplayCount = 0;
				npc.seqDelay = delay;
				SoundPlayer.playSeqSound(npc.yFine, seqType, npc.xFine, false, npc.seqFrame);
			}
			if (exactMove == 2) {
				npc.seqReplayCount = 0;
			}
		} else if (animationId == -1 || npc.seqId == -1 || SeqTypeList.get(animationId).priority >= SeqTypeList.get(npc.seqId).priority) {
			npc.seqDelayClock = 0;
			npc.seqId = animationId;
			npc.seqNextFrame = 1;
			npc.seqReplayCount = 0;
			npc.seqDelay = delay;
			npc.seqMovementSteps = npc.movementQueueSize;
			npc.seqFrame = 0;
			if (npc.seqId != -1) {
				SoundPlayer.playSeqSound(npc.yFine, SeqTypeList.get(npc.seqId), npc.xFine, false, npc.seqFrame);
			}
		}
	}

	@OriginalMember(owner = "client!sj", name = "a", descriptor = "(ILclient!na;)V")
	public static void saveSettingsCookie(@OriginalArg(1) JagString value) {
		client.settings = value;
		if (GameShell.signLink.applet == null) {
			return;
		}
		try {
			@Pc(17) JagString prefix = COOKIE_PREFIX_KEY.fromParameters(GameShell.signLink.applet);
			@Pc(23) JagString host = COOKIE_HOST_KEY.fromParameters(GameShell.signLink.applet);
			@Pc(48) JagString cookie = JagString.concatenate(new JagString[]{prefix, COOKIE_SETTINGS_PREFIX, value, COOKIE_PATH_DOMAIN, host});
			if (value.length() == 0) {
				cookie = JagString.concatenate(new JagString[]{cookie, COOKIE_EXPIRE_IMMEDIATELY});
			} else {
				cookie = JagString.concatenate(new JagString[]{cookie, COOKIE_EXPIRES_PREFIX, DateUtil.getDateString(MonotonicClock.currentTimeMillis() + 94608000000L), COOKIE_MAX_AGE_PREFIX, JagString.parseLong(94608000L)});
			}
			JagString.concatenate(new JagString[]{COOKIE_SET_JS, cookie, COOKIE_SUFFIX}).evalInBrowser(GameShell.signLink.applet);
		} catch (@Pc(124) Throwable t) {
		}
	}

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "(IBI)V")
	public static void spawnGroundObject(@OriginalArg(0) int tileX, @OriginalArg(2) int tileY) {
		@Pc(9) LinkedList stack = SceneGraph.objStacks[Player.plane][tileY][tileX];
		if (stack == null) {
			SceneGraph.removeObjStack(Player.plane, tileY, tileX);
			return;
		}
		@Pc(28) int bestValue = -99999999;
		@Pc(30) ObjStackNode bestNode = null;
		@Pc(35) ObjStackNode node;
		for (node = (ObjStackNode) stack.head(); node != null; node = (ObjStackNode) stack.next()) {
			@Pc(44) ObjType objType = ObjTypeList.get(node.value.type);
			@Pc(47) int value = objType.cost;
			if (objType.stackable == 1) {
				value *= node.value.amount + 1;
			}
			if (bestValue < value) {
				bestValue = value;
				bestNode = node;
			}
		}
		if (bestNode == null) {
			SceneGraph.removeObjStack(Player.plane, tileY, tileX);
			return;
		}
		stack.addHead(bestNode);
		@Pc(89) ObjStack secondObj = null;
		@Pc(91) ObjStack thirdObj = null;
		for (node = (ObjStackNode) stack.head(); node != null; node = (ObjStackNode) stack.next()) {
			@Pc(103) ObjStack obj = node.value;
			if (obj.type != bestNode.value.type) {
				if (secondObj == null) {
					secondObj = obj;
				}
				if (obj.type != secondObj.type && thirdObj == null) {
					thirdObj = obj;
				}
			}
		}
		@Pc(152) long key = (tileX << 7) + tileY + 1610612736;
		SceneGraph.setObjStack(Player.plane, tileY, tileX, SceneGraph.getTileHeight(Player.plane, tileY * 128 + 64, tileX * 128 + 64), bestNode.value, key, secondObj, thirdObj);
	}

	@OriginalMember(owner = "client!dh", name = "a", descriptor = "(IIII)Lclient!wk;")
	public static ComponentPointer openTopLevelInterface(@OriginalArg(1) int interfaceId, @OriginalArg(2) int parentComponentId, @OriginalArg(3) int type) {
		@Pc(9) ComponentPointer ptr = new ComponentPointer();
		ptr.type = type;
		ptr.interfaceId = interfaceId;
		InterfaceList.openInterfaces.put(ptr, parentComponentId);
		InterfaceList.resetAnimations(interfaceId);
		@Pc(28) Component parent = InterfaceList.getComponent(parentComponentId);
		if (parent != null) {
			InterfaceList.redraw(parent);
		}
		if (Cs1ScriptRunner.pleaseWaitComponent != null) {
			InterfaceList.redraw(Cs1ScriptRunner.pleaseWaitComponent);
			Cs1ScriptRunner.pleaseWaitComponent = null;
		}
		@Pc(45) int menuSize = MiniMenu.size;
		@Pc(53) int maxWidth;
		for (maxWidth = 0; maxWidth < menuSize; maxWidth++) {
			if (InterfaceList.isInterfaceAction(MiniMenu.actions[maxWidth])) {
				MiniMenu.remove(maxWidth);
			}
		}
		if (MiniMenu.size == 1) {
			Cs1ScriptRunner.isMenuOpen = false;
			InterfaceList.redrawScreen(InterfaceList.menuX, InterfaceList.menuWidth, InterfaceList.menuY, InterfaceList.menuHeight);
		} else {
			InterfaceList.redrawScreen(InterfaceList.menuX, InterfaceList.menuWidth, InterfaceList.menuY, InterfaceList.menuHeight);
			maxWidth = Fonts.b12Full.getStringWidth(LocalizedText.CHOOSE_OPTION);
			for (@Pc(95) int j = 0; j < MiniMenu.size; j++) {
				@Pc(104) int opWidth = Fonts.b12Full.getStringWidth(MiniMenu.getOp(j));
				if (opWidth > maxWidth) {
					maxWidth = opWidth;
				}
			}
			InterfaceList.menuWidth = maxWidth + 8;
			InterfaceList.menuHeight = MiniMenu.size * 15 + (InterfaceList.useStyledMenu ? 26 : 22);
		}
		if (parent != null) {
			InterfaceList.layoutComponent(parent, false);
		}
		InterfaceList.runOnLoadScripts(interfaceId);
		if (InterfaceList.topLevelInterface != -1) {
			InterfaceList.runScripts(1, InterfaceList.topLevelInterface);
		}
		return ptr;
	}

	@OriginalMember(owner = "client!ah", name = "b", descriptor = "(I)V")
	public static void processDefaultAction() {
		if (InterfaceList.clickedInventoryComponent != null || Cs1ScriptRunner.draggedComponent != null) {
			return;
		}
		@Pc(20) int button = Mouse.clickButton;
		@Pc(93) int mouseY;
		@Pc(99) int menuW;
		if (!Cs1ScriptRunner.isMenuOpen) {
			if (button == 1 && MiniMenu.size > 0) {
				@Pc(37) short action = MiniMenu.actions[MiniMenu.size - 1];
				if (action == 25 || action == 23 || action == 48 || action == 7 || action == 13 || action == 47 || action == 5 || action == 43 || action == 35 || action == 58 || action == 22 || action == 1006) {
					mouseY = MiniMenu.intArgs1[MiniMenu.size - 1];
					menuW = MiniMenu.intArgs2[MiniMenu.size - 1];
					@Pc(103) Component comp = InterfaceList.getComponent(menuW);
					@Pc(106) ServerActiveProperties props = InterfaceList.getServerActiveProperties(comp);
					if (props.isObjSwapEnabled() || props.isObjReplaceEnabled()) {
						InterfaceList.clickedInventoryComponentCycle = 0;
						InterfaceList.draggingClickedInventoryObject = false;
						if (InterfaceList.clickedInventoryComponent != null) {
							InterfaceList.redraw(InterfaceList.clickedInventoryComponent);
						}
						InterfaceList.clickedInventoryComponent = InterfaceList.getComponent(menuW);
						InterfaceList.clickedInventoryComponentX = Mouse.clickX;
						InterfaceList.clickedInventoryComponentY = Mouse.clickY;
						InterfaceList.mouseOverInventoryObjectIndex = mouseY;
						InterfaceList.redraw(InterfaceList.clickedInventoryComponent);
						return;
					}
				}
			}
			if (button == 1 && (VarpDomain.mouseButtons == 1 && MiniMenu.size > 2 || MiniMenu.isComponentOptionAction(MiniMenu.size - 1))) {
				button = 2;
			}
			if (button == 2 && MiniMenu.size > 0 || MiniMenu.clickProcessingState == 1) {
				ScriptRunner.layoutMiniMenu();
			}
			if (button == 1 && MiniMenu.size > 0 || MiniMenu.clickProcessingState == 2) {
				MiniMenu.processClick();
			}
			return;
		}
		@Pc(204) int menuX;
		if (button != 1) {
			mouseY = Mouse.lastMouseY;
			menuX = Mouse.lastMouseX;
			if (menuX < InterfaceList.menuX - 10 || menuX > InterfaceList.menuWidth + InterfaceList.menuX + 10 || InterfaceList.menuY - 10 > mouseY || mouseY > InterfaceList.menuHeight + InterfaceList.menuY + 10) {
				Cs1ScriptRunner.isMenuOpen = false;
				InterfaceList.redrawScreen(InterfaceList.menuX, InterfaceList.menuWidth, InterfaceList.menuY, InterfaceList.menuHeight);
			}
		}
		if (button != 1) {
			return;
		}
		menuX = InterfaceList.menuX;
		mouseY = InterfaceList.menuY;
		menuW = InterfaceList.menuWidth;
		@Pc(265) int clickX = Mouse.clickX;
		@Pc(267) int clickY = Mouse.clickY;
		@Pc(269) int selectedIdx = -1;
		for (@Pc(271) int idx = 0; idx < MiniMenu.size; idx++) {
			@Pc(289) int entryY;
			if (InterfaceList.useStyledMenu) {
				entryY = (MiniMenu.size - idx - 1) * 15 + mouseY + 35;
			} else {
				entryY = (MiniMenu.size - idx - 1) * 15 + mouseY + 31;
			}
			if (clickX > menuX && menuX + menuW > clickX && entryY - 13 < clickY && entryY + 3 > clickY) {
				selectedIdx = idx;
			}
		}
		if (selectedIdx != -1) {
			MiniMenu.doAction(selectedIdx);
		}
		Cs1ScriptRunner.isMenuOpen = false;
		InterfaceList.redrawScreen(InterfaceList.menuX, InterfaceList.menuWidth, InterfaceList.menuY, InterfaceList.menuHeight);
	}
}
