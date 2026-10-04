package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.api.API;

public class SceneGraph {

	@OriginalMember(owner = "client!bb", name = "g", descriptor = "[[[B")
	public static final byte[][][] renderFlags = new byte[4][104][104];
	@OriginalMember(owner = "client!mi", name = "Y", descriptor = "[[[Lclient!ih;")
	public static final LinkedList[][][] objStacks = new LinkedList[4][104][104];
	@OriginalMember(owner = "client!te", name = "B", descriptor = "[I")
	public static final int[] WALL_DECO_ROT_SIZE_Y_DIR = new int[]{0, -1, 0, 1};
	@OriginalMember(owner = "client!fb", name = "q", descriptor = "[I")
	public static final int[] WALL_OFFSET_Y = new int[]{-1, -1, 1, 1};
	@OriginalMember(owner = "client!j", name = "O", descriptor = "[I")
	public static final int[] WALL_OFFSET_X = new int[]{1, -1, -1, 1};
	@OriginalMember(owner = "client!vl", name = "e", descriptor = "[I")
	public static final int[] WALL_ROTATION_TYPE1 = new int[]{1, 2, 4, 8};
	@OriginalMember(owner = "client!pg", name = "T", descriptor = "[I")
	public static final int[] WALL_DRAW_FLAGS = new int[]{76, 8, 137, 4, 0, 1, 38, 2, 19};
	@OriginalMember(owner = "client!rj", name = "U", descriptor = "Lclient!ih;")
	public static final LinkedList projectiles = new LinkedList();
	@OriginalMember(owner = "client!hk", name = "W", descriptor = "Lclient!ih;")
	public static final LinkedList spotanims = new LinkedList();
	@OriginalMember(owner = "client!ec", name = "B", descriptor = "[[I")
	public static final int[][] OVERLAY_SHAPE_VERTICES = new int[][]{new int[0], {128, 0, 128, 128, 0, 128}, {0, 0, 128, 0, 128, 128, 64, 128}, {0, 128, 0, 0, 128, 0, 64, 128}, {0, 0, 64, 128, 0, 128}, {128, 128, 64, 128, 128, 0}, {64, 0, 128, 0, 128, 128, 64, 128}, {128, 0, 128, 128, 0, 128, 0, 64, 64, 0}, {0, 0, 64, 0, 0, 64}, {0, 0, 128, 0, 128, 128, 64, 96, 32, 64}, {0, 128, 0, 0, 32, 64, 64, 96, 128, 128}, {0, 128, 0, 0, 32, 32, 96, 32, 128, 0, 128, 128}};
	@OriginalMember(owner = "client!ck", name = "d", descriptor = "[I")
	public static final int[] WALL_DECO_ROT_SIZE_X_DIR = new int[]{1, 0, -1, 0};
	@OriginalMember(owner = "client!ka", name = "t", descriptor = "[I")
	public static final int[] WALL_SCENERY_DIR_TYPE1 = new int[]{0, 0, 2, 0, 0, 2, 1, 1, 0};
	@OriginalMember(owner = "client!uj", name = "A", descriptor = "[I")
	public static final int[] WALL_SCENERY_DIR_TYPE2 = new int[]{2, 0, 0, 2, 0, 0, 0, 4, 4};
	@OriginalMember(owner = "client!gm", name = "gb", descriptor = "[I")
	public static final int[] WALL_SCENERY_DIR_TYPE3 = new int[]{0, 4, 4, 8, 0, 0, 8, 0, 0};
	@OriginalMember(owner = "client!kd", name = "sb", descriptor = "[I")
	public static final int[] WALL_SCENERY_DIR_TYPE4 = new int[]{1, 1, 0, 0, 0, 8, 0, 0, 8};
	@OriginalMember(owner = "client!hb", name = "t", descriptor = "[I")
	public static final int[] WALL_UNCULL_FLAGS = new int[]{160, 192, 80, 96, 0, 144, 80, 48, 160};
	@OriginalMember(owner = "client!km", name = "Rc", descriptor = "[I")
	public static final int[] WALL_VISIBILITY_FLAGS = new int[]{19, 55, 38, 155, 255, 110, 137, 205, 76};
	@OriginalMember(owner = "client!fh", name = "U", descriptor = "[[Z")
	public static final boolean[][] OVERLAY_TRIANGLE_FLAGS = new boolean[][]{new boolean[0], {true, false, true}, {true, false, false, true}, {false, false, true, true}, {true, true, false}, {false, true, true}, {true, false, false, true}, {false, false, false, true, true}, {false, true, true}, {true, false, true, true, true}, {false, true, true, true, true}, {false, true, true, true, true, false}};
	@OriginalMember(owner = "client!fg", name = "d", descriptor = "[I")
	public static final int[] projectedX = new int[6];
	@OriginalMember(owner = "client!fg", name = "l", descriptor = "[I")
	public static final int[] screenY = new int[6];
	@OriginalMember(owner = "client!fg", name = "m", descriptor = "[I")
	public static final int[] screenX = new int[6];
	@OriginalMember(owner = "client!fg", name = "r", descriptor = "[I")
	public static final int[] projectedZ = new int[6];
	@OriginalMember(owner = "client!fg", name = "t", descriptor = "[I")
	public static final int[] projectedY = new int[6];
	@OriginalMember(owner = "client!ah", name = "p", descriptor = "Lclient!ih;")
	public static final LinkedList tileQueue = new LinkedList();
	@OriginalMember(owner = "client!sh", name = "i", descriptor = "[[I")
	public static final int[][] UNDERLAY_SHAPE_VERTICES = new int[][]{{0, 128, 0, 0, 128, 0, 128, 128}, {0, 128, 0, 0, 128, 0}, {0, 0, 64, 128, 0, 128}, {128, 128, 64, 128, 128, 0}, {0, 0, 128, 0, 128, 128, 64, 128}, {0, 128, 0, 0, 128, 0, 64, 128}, {64, 128, 0, 128, 0, 0, 64, 0}, {0, 0, 64, 0, 0, 64}, {128, 0, 128, 128, 0, 128, 0, 64, 64, 0}, {0, 128, 0, 0, 32, 64, 64, 96, 128, 128}, {0, 0, 128, 0, 128, 128, 64, 96, 32, 64}, {0, 0, 128, 0, 96, 32, 32, 32}};
	@OriginalMember(owner = "client!gf", name = "S", descriptor = "[I")
	public static final int[] SHAPE_BLEND_VERTEX_COUNT = new int[]{0, 2, 2, 2, 1, 1, 2, 2, 1, 3, 1, 1};
	@OriginalMember(owner = "client!kc", name = "s", descriptor = "[I")
	public static final int[] overlayEdgeLookup = new int[]{1, 1, 1, 1, 4, 1, 1, 5, 6, 1, 5, 0, 7, 0, 4, 1, 7, 2, 1, 1, 6, 1, 1, 3, 6, 1, 7, 0, 0, 6, 7, 0, 1, 7, 6, 1, 1, 1, 5, 4, 3, 2, 1, 1, 0, 4, 1, 5};
	@OriginalMember(owner = "client!wi", name = "hb", descriptor = "[[Z")
	public static final boolean[][] tileEdgeMasks = new boolean[][]{{true, true, true}, {false, false}, {false, true}, {true, false}, {false, true, true}, {true, false, true}, {false, true, false}, {true, false, false}};
	@OriginalMember(owner = "client!kc", name = "o", descriptor = "[[[Lclient!bj;")
	public static Tile[][][] tiles;

	@OriginalMember(owner = "client!gj", name = "m", descriptor = "[[[I")
	public static int[][][] tileHeights;
	@OriginalMember(owner = "client!id", name = "i", descriptor = "[[[I")
	public static int[][][] surfaceTileHeights;
	@OriginalMember(owner = "client!tg", name = "g", descriptor = "[[[B")
	public static byte[][][] tileOverlays;
	@OriginalMember(owner = "client!n", name = "h", descriptor = "[[[B")
	public static byte[][][] tileShapes;
	@OriginalMember(owner = "client!ac", name = "e", descriptor = "[[[B")
	public static byte[][][] tileAngles;
	@OriginalMember(owner = "client!ui", name = "eb", descriptor = "[[[B")
	public static byte[][][] tileUnderlays;
	@OriginalMember(owner = "client!em", name = "t", descriptor = "[[[I")
	public static int[][][] occludeFlags;
	@OriginalMember(owner = "client!ka", name = "r", descriptor = "[I")
	public static int[] rowCount;
	@OriginalMember(owner = "client!lg", name = "k", descriptor = "I")
	public static int firstVisibleLevel = 99;
	@OriginalMember(owner = "client!ug", name = "d", descriptor = "[I")
	public static int[] rowChroma;
	@OriginalMember(owner = "client!l", name = "l", descriptor = "[I")
	public static int[] rowSaturation;
	@OriginalMember(owner = "client!s", name = "i", descriptor = "[I")
	public static int[] rowWeightedHue;
	@OriginalMember(owner = "client!jd", name = "d", descriptor = "[[[B")
	public static byte[][][] shadowmap;
	@OriginalMember(owner = "client!wk", name = "v", descriptor = "[I")
	public static int[] rowLightness;
	@OriginalMember(owner = "client!ub", name = "h", descriptor = "[Lclient!pe;")
	public static Occluder[] activeOccluders;
	@OriginalMember(owner = "client!rh", name = "k", descriptor = "I")
	public static int activeOccluderCount = 0;
	@OriginalMember(owner = "client!bl", name = "T", descriptor = "I")
	public static int sceneryLen = 0;
	@OriginalMember(owner = "client!pm", name = "cb", descriptor = "[[[Lclient!bj;")
	public static Tile[][][] surfaceGroundTiles;
	@OriginalMember(owner = "client!client", name = "kb", descriptor = "[[Lclient!hg;")
	public static GlTile[][] surfaceHdTiles;
	@OriginalMember(owner = "client!wh", name = "c", descriptor = "[[[Lclient!bj;")
	public static Tile[][][] underWaterGroundTiles;
	@OriginalMember(owner = "client!pk", name = "R", descriptor = "[[Lclient!hg;")
	public static GlTile[][] underWaterHdTiles;
	@OriginalMember(owner = "client!hc", name = "O", descriptor = "[Lclient!pe;")
	public static Occluder[] occluders;
	@OriginalMember(owner = "client!ma", name = "i", descriptor = "I")
	public static int width;
	@OriginalMember(owner = "client!hk", name = "Y", descriptor = "I")
	public static int length;
	@OriginalMember(owner = "client!cd", name = "s", descriptor = "I")
	public static int occluderCount;
	@OriginalMember(owner = "client!tk", name = "D", descriptor = "[Lclient!ec;")
	public static Scenery[] scenery;
	@OriginalMember(owner = "client!c", name = "bb", descriptor = "[Lclient!ec;")
	public static Scenery[] sceneryBuffer;
	@OriginalMember(owner = "client!gf", name = "O", descriptor = "[[[I")
	public static int[][][] underwaterTileHeights;
	@OriginalMember(owner = "client!oj", name = "E", descriptor = "[[Lclient!hg;")
	public static GlTile[][] underwaterHdTiles;
	@OriginalMember(owner = "client!jm", name = "r", descriptor = "I")
	public static int levels;
	@OriginalMember(owner = "client!sm", name = "e", descriptor = "[[[B")
	public static byte[][][] occlusionHeights;
	@OriginalMember(owner = "client!wi", name = "db", descriptor = "I")
	public static int visibility;
	@OriginalMember(owner = "client!f", name = "ab", descriptor = "[[I")
	public static int[][] tileWaterData;
	@OriginalMember(owner = "client!la", name = "i", descriptor = "[[[I")
	public static int[][][] occlusionCache;
	@OriginalMember(owner = "client!dl", name = "h", descriptor = "[[Z")
	public static boolean[][] visibleTiles;
	@OriginalMember(owner = "client!ha", name = "k", descriptor = "[[Z")
	public static boolean[][] visibilityPoints;
	@OriginalMember(owner = "client!og", name = "b", descriptor = "I")
	public static int currentChunkY;
	@OriginalMember(owner = "client!ja", name = "q", descriptor = "I")
	public static int currentChunkX;
	@OriginalMember(owner = "client!gm", name = "R", descriptor = "I")
	public static int lightnessJitter = (int) (Math.random() * 17.0D) - 8;
	@OriginalMember(owner = "client!ok", name = "c", descriptor = "I")
	public static int hueJitter = (int) (Math.random() * 33.0D) - 16;
	@OriginalMember(owner = "client!rj", name = "R", descriptor = "I")
	public static int cameraY;
	@OriginalMember(owner = "client!pi", name = "U", descriptor = "I")
	public static int cameraTileY;
	@OriginalMember(owner = "client!sk", name = "mb", descriptor = "I")
	public static int sinYaw;
	@OriginalMember(owner = "client!bl", name = "X", descriptor = "I")
	public static int lastUnderwaterFogRange = -1;
	@OriginalMember(owner = "client!aj", name = "Z", descriptor = "[I")
	public static int[] roofGroupMaxHeight;
	@OriginalMember(owner = "client!jg", name = "a", descriptor = "I")
	public static int cosPitch;
	@OriginalMember(owner = "client!ma", name = "z", descriptor = "I")
	public static int lastUnderwaterColor = -1;
	@OriginalMember(owner = "client!ig", name = "i", descriptor = "I")
	public static int sinPitch;
	@OriginalMember(owner = "client!k", name = "l", descriptor = "[I")
	public static int[] roofGroupMinX;
	@OriginalMember(owner = "client!ta", name = "o", descriptor = "[I")
	public static int[] roofGroupMaxZ;
	@OriginalMember(owner = "client!qk", name = "c", descriptor = "[I")
	public static int[] roofGroupMinZ;
	@OriginalMember(owner = "client!hh", name = "p", descriptor = "[I")
	public static int[] roofGroupMaxX;
	@OriginalMember(owner = "client!ml", name = "K", descriptor = "I")
	public static int cameraZ;
	@OriginalMember(owner = "client!nd", name = "s", descriptor = "I")
	public static int cameraTileX;
	@OriginalMember(owner = "client!lj", name = "B", descriptor = "I")
	public static int cameraX;
	@OriginalMember(owner = "client!tb", name = "Q", descriptor = "I")
	public static int startLevel = 0;
	@OriginalMember(owner = "client!bc", name = "Z", descriptor = "I")
	public static int drawCycle;
	@OriginalMember(owner = "client!rc", name = "p", descriptor = "I")
	public static int visibleTileCount = 0;
	@OriginalMember(owner = "client!gn", name = "d", descriptor = "Z")
	public static boolean levelsHidden = false;
	@OriginalMember(owner = "client!gg", name = "Z", descriptor = "I")
	public static int cosYaw;
	@OriginalMember(owner = "client!sj", name = "u", descriptor = "Z")
	public static boolean dynamicMapRegion = false;
	@OriginalMember(owner = "client!gf", name = "R", descriptor = "I")
	public static int centralZoneX;
	@OriginalMember(owner = "client!eb", name = "u", descriptor = "I")
	public static int centralZoneY;
	@OriginalMember(owner = "client!dc", name = "ab", descriptor = "I")
	public static int centralPlane = 0;

	@OriginalMember(owner = "client!km", name = "f", descriptor = "(I)Z")
	public static boolean allLevelsAreVisible() {
		return GlRenderer.enabled || Preferences.allLevelsVisible;
	}

	@OriginalMember(owner = "client!ql", name = "a", descriptor = "(IIII)I")
	public static int getTileHeight(@OriginalArg(0) int level, @OriginalArg(2) int xFine, @OriginalArg(3) int yFine) {
		if (tileHeights == null) {
			return 0;
		}
		@Pc(12) int x = xFine >> 7;
		@Pc(16) int y = yFine >> 7;
		if (x < 0 || y < 0 || x > 103 || y > 103) {
			return 0;
		}
		@Pc(36) int xFine2 = xFine & 0x7F;
		@Pc(40) int yFine2 = yFine & 0x7F;
		@Pc(42) int virtualLevel = level;
		if (level < 3 && (renderFlags[1][x][y] & 0x2) == 2) {
			virtualLevel = level + 1;
		}
		@Pc(91) int heightZ0 = xFine2 * tileHeights[virtualLevel][x + 1][y + 1] + tileHeights[virtualLevel][x][y + 1] * (128 - xFine2) >> 7;
		@Pc(118) int heightZ1 = xFine2 * tileHeights[virtualLevel][x + 1][y] + (128 - xFine2) * tileHeights[virtualLevel][x][y] >> 7;
		return yFine2 * heightZ0 + (128 - yFine2) * heightZ1 >> 7;
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(III)Lclient!jh;")
	public static Wall removeWall(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		} else {
			@Pc(14) Wall wall = tile.wall;
			tile.wall = null;
			return wall;
		}
	}

	@OriginalMember(owner = "client!fc", name = "a", descriptor = "()V")
	public static void clear() {
		@Pc(3) int i;
		@Pc(9) int x;
		@Pc(14) int y;
		if (surfaceGroundTiles != null) {
			for (i = 0; i < surfaceGroundTiles.length; i++) {
				for (x = 0; x < width; x++) {
					for (y = 0; y < length; y++) {
						surfaceGroundTiles[i][x][y] = null;
					}
				}
			}
		}
		surfaceHdTiles = null;
		if (underWaterGroundTiles != null) {
			for (i = 0; i < underWaterGroundTiles.length; i++) {
				for (x = 0; x < width; x++) {
					for (y = 0; y < length; y++) {
						underWaterGroundTiles[i][x][y] = null;
					}
				}
			}
		}
		underWaterHdTiles = null;
		occluderCount = 0;
		if (occluders != null) {
			for (i = 0; i < occluderCount; i++) {
				occluders[i] = null;
			}
		}
		if (scenery != null) {
			for (i = 0; i < sceneryLen; i++) {
				scenery[i] = null;
			}
			sceneryLen = 0;
		}
		if (sceneryBuffer != null) {
			for (i = 0; i < sceneryBuffer.length; i++) {
				sceneryBuffer[i] = null;
			}
		}
	}

	@OriginalMember(owner = "client!vf", name = "a", descriptor = "(III)Lclient!jh;")
	public static Wall getWall(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null ? null : tile.wall;
	}

	@OriginalMember(owner = "client!gj", name = "a", descriptor = "(III)Lclient!df;")
	public static WallDecor getWallDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null ? null : tile.wallDecor;
	}

	@OriginalMember(owner = "client!kf", name = "b", descriptor = "(III)Lclient!ec;")
	public static Scenery getScenery(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		}
		for (@Pc(13) int i = 0; i < tile.sceneryLen; i++) {
			@Pc(22) Scenery scenery = tile.scenery[i];
			if ((scenery.key >> 29 & 0x3L) == 2L && scenery.xMin == x && scenery.yMin == y) {
				return scenery;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "(III)Lclient!bm;")
	public static GroundDecor getGroundDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null || tile.groundDecor == null ? null : tile.groundDecor;
	}

	@OriginalMember(owner = "client!v", name = "a", descriptor = "(IIIJ)Z")
	public static boolean isLocValid(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) long key) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return false;
		} else if (tile.wall != null && tile.wall.key == key) {
			return true;
		} else if (tile.wallDecor != null && tile.wallDecor.key == key) {
			return true;
		} else if (tile.groundDecor != null && tile.groundDecor.key == key) {
			return true;
		} else {
			for (@Pc(46) int i = 0; i < tile.sceneryLen; i++) {
				if (tile.scenery[i].key == key) {
					return true;
				}
			}
			return false;
		}
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(ZI)V")
	public static void load(@OriginalArg(0) boolean underwater) {
		rowWeightedHue = new int[104];
		rowSaturation = new int[104];
		firstVisibleLevel = 99;
		rowChroma = new int[104];
		@Pc(14) byte plane;
		if (underwater) {
			plane = 1;
		} else {
			plane = 4;
		}
		tileShapes = new byte[plane][104][104];
		rowCount = new int[104];
		occludeFlags = new int[plane][105][105];
		shadowmap = new byte[plane][105][105];
		tileOverlays = new byte[plane][104][104];
		rowLightness = new int[104];
		tileAngles = new byte[plane][104][104];
		tileUnderlays = new byte[plane][104][104];
	}

	@OriginalMember(owner = "client!ib", name = "b", descriptor = "(I)V")
	public static void unload() {
		rowChroma = null;
		occludeFlags = null;
		rowCount = null;
		tileShapes = null;
		tileAngles = null;
		shadowmap = null;
		tileOverlays = null;
		tileUnderlays = null;
		rowSaturation = null;
		rowWeightedHue = null;
		rowLightness = null;
	}

	@OriginalMember(owner = "client!um", name = "c", descriptor = "(III)Z")
	public static boolean isOccluded(@OriginalArg(0) int x, @OriginalArg(1) int z, @OriginalArg(2) int y) {
		for (@Pc(1) int i = 0; i < activeOccluderCount; i++) {
			@Pc(8) Occluder occluder = activeOccluders[i];
			@Pc(17) int delta;
			@Pc(29) int boundMin1;
			@Pc(39) int boundMax1;
			@Pc(49) int boundMin2;
			@Pc(59) int boundMax2;
			if (occluder.direction == 1) {
				delta = occluder.minX - x;
				if (delta > 0) {
					boundMin1 = occluder.minY + (occluder.nearYSlope * delta >> 8);
					boundMax1 = occluder.maxY + (occluder.farYSlope * delta >> 8);
					boundMin2 = occluder.minZ + (occluder.nearZSlope * delta >> 8);
					boundMax2 = occluder.maxZ + (occluder.farZSlope * delta >> 8);
					if (y >= boundMin1 && y <= boundMax1 && z >= boundMin2 && z <= boundMax2) {
						return true;
					}
				}
			} else if (occluder.direction == 2) {
				delta = x - occluder.minX;
				if (delta > 0) {
					boundMin1 = occluder.minY + (occluder.nearYSlope * delta >> 8);
					boundMax1 = occluder.maxY + (occluder.farYSlope * delta >> 8);
					boundMin2 = occluder.minZ + (occluder.nearZSlope * delta >> 8);
					boundMax2 = occluder.maxZ + (occluder.farZSlope * delta >> 8);
					if (y >= boundMin1 && y <= boundMax1 && z >= boundMin2 && z <= boundMax2) {
						return true;
					}
				}
			} else if (occluder.direction == 3) {
				delta = occluder.minY - y;
				if (delta > 0) {
					boundMin1 = occluder.minX + (occluder.farXSlope * delta >> 8);
					boundMax1 = occluder.maxX + (occluder.nearXSlope * delta >> 8);
					boundMin2 = occluder.minZ + (occluder.nearZSlope * delta >> 8);
					boundMax2 = occluder.maxZ + (occluder.farZSlope * delta >> 8);
					if (x >= boundMin1 && x <= boundMax1 && z >= boundMin2 && z <= boundMax2) {
						return true;
					}
				}
			} else if (occluder.direction == 4) {
				delta = y - occluder.minY;
				if (delta > 0) {
					boundMin1 = occluder.minX + (occluder.farXSlope * delta >> 8);
					boundMax1 = occluder.maxX + (occluder.nearXSlope * delta >> 8);
					boundMin2 = occluder.minZ + (occluder.nearZSlope * delta >> 8);
					boundMax2 = occluder.maxZ + (occluder.farZSlope * delta >> 8);
					if (x >= boundMin1 && x <= boundMax1 && z >= boundMin2 && z <= boundMax2) {
						return true;
					}
				}
			} else if (occluder.direction == 5) {
				delta = z - occluder.minZ;
				if (delta > 0) {
					boundMin1 = occluder.minX + (occluder.farXSlope * delta >> 8);
					boundMax1 = occluder.maxX + (occluder.nearXSlope * delta >> 8);
					boundMin2 = occluder.minY + (occluder.nearYSlope * delta >> 8);
					boundMax2 = occluder.maxY + (occluder.farYSlope * delta >> 8);
					if (x >= boundMin1 && x <= boundMax1 && y >= boundMin2 && y <= boundMax2) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!bj", name = "a", descriptor = "(III)J")
	public static long getGroundDecorKey(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null || tile.groundDecor == null ? 0L : tile.groundDecor.key;
	}

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(Z)V")
	public static void setUnderwater(@OriginalArg(0) boolean underwater) {
		if (underwater) {
			tiles = underWaterGroundTiles;
			tileHeights = underwaterTileHeights;
			underwaterHdTiles = underWaterHdTiles;
		} else {
			tiles = surfaceGroundTiles;
			tileHeights = surfaceTileHeights;
			underwaterHdTiles = surfaceHdTiles;
		}
		levels = tiles.length;
	}

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(IIZLclient!wa;IIBII)V")
	public static void readTile(@OriginalArg(0) int baseX, @OriginalArg(1) int baseY, @OriginalArg(2) boolean underwater, @OriginalArg(3) Buffer buffer, @OriginalArg(4) int y, @OriginalArg(5) int x, @OriginalArg(7) int angle, @OriginalArg(8) int level) {
		@Pc(32) int opcode;
		if (x < 0 || x >= 104 || y < 0 || y >= 104) {
			while (true) {
				opcode = buffer.g1();
				if (opcode == 0) {
					break;
				}
				if (opcode == 1) {
					buffer.g1();
					break;
				}
				if (opcode <= 49) {
					buffer.g1();
				}
			}
			return;
		}
		if (!underwater) {
			renderFlags[level][x][y] = 0;
		}
		while (true) {
			opcode = buffer.g1();
			if (opcode == 0) {
				if (underwater) {
					tileHeights[0][x][y] = surfaceTileHeights[0][x][y];
				} else if (level == 0) {
					tileHeights[0][x][y] = -PerlinNoise.getTileHeight(y + baseY + 556238, baseX + x + 932731) * 8;
				} else {
					tileHeights[level][x][y] = tileHeights[level - 1][x][y] - 240;
				}
				break;
			}
			if (opcode == 1) {
				@Pc(111) int height = buffer.g1();
				if (underwater) {
					tileHeights[0][x][y] = surfaceTileHeights[0][x][y] + height * 8;
				} else {
					if (height == 1) {
						height = 0;
					}
					if (level == 0) {
						tileHeights[0][x][y] = -height * 8;
					} else {
						tileHeights[level][x][y] = tileHeights[level - 1][x][y] - height * 8;
					}
				}
				break;
			}
			if (opcode <= 49) {
				tileOverlays[level][x][y] = buffer.g1b();
				tileShapes[level][x][y] = (byte) ((opcode - 2) / 4);
				tileAngles[level][x][y] = (byte) (opcode + angle - 2 & 0x3);
			} else if (opcode > 81) {
				tileUnderlays[level][x][y] = (byte) (opcode - 81);
			} else if (!underwater) {
				renderFlags[level][x][y] = (byte) (opcode - 49);
			}
		}
	}

	@OriginalMember(owner = "client!hd", name = "a", descriptor = "(IIIIIIII)V")
	public static void addLoc(@OriginalArg(0) int level, @OriginalArg(2) int orientation, @OriginalArg(3) int shape, @OriginalArg(4) int y, @OriginalArg(5) int layer, @OriginalArg(6) int x, @OriginalArg(7) int seqId) {
		if (x < 0 || y < 0 || x >= 103 || y >= 103) {
			return;
		}
		@Pc(38) int locId;
		if (layer == 0) {
			@Pc(28) Wall wall = getWall(level, x, y);
			if (wall != null) {
				locId = Integer.MAX_VALUE & (int) (wall.key >>> 32);
				if (shape == 2) {
					wall.primary = new Loc(locId, 2, orientation + 4, level, x, y, seqId, false, wall.primary);
					wall.secondary = new Loc(locId, 2, orientation + 1 & 0x3, level, x, y, seqId, false, wall.secondary);
				} else {
					wall.primary = new Loc(locId, shape, orientation, level, x, y, seqId, false, wall.primary);
				}
			}
		}
		if (layer == 1) {
			@Pc(106) WallDecor wallDecor = getWallDecor(level, x, y);
			if (wallDecor != null) {
				locId = (int) (wallDecor.key >>> 32) & Integer.MAX_VALUE;
				if (shape == 4 || shape == 5) {
					wallDecor.primary = new Loc(locId, 4, orientation, level, x, y, seqId, false, wallDecor.primary);
				} else if (shape == 6) {
					wallDecor.primary = new Loc(locId, 4, orientation + 4, level, x, y, seqId, false, wallDecor.primary);
				} else if (shape == 7) {
					wallDecor.primary = new Loc(locId, 4, (orientation + 2 & 0x3) + 4, level, x, y, seqId, false, wallDecor.primary);
				} else if (shape == 8) {
					wallDecor.primary = new Loc(locId, 4, orientation + 4, level, x, y, seqId, false, wallDecor.primary);
					wallDecor.secondary = new Loc(locId, 4, (orientation + 2 & 0x3) + 4, level, x, y, seqId, false, wallDecor.secondary);
				}
			}
		}
		if (layer == 2) {
			if (shape == 11) {
				shape = 10;
			}
			@Pc(255) Scenery scenery = getScenery(level, x, y);
			if (scenery != null) {
				scenery.entity = new Loc((int) (scenery.key >>> 32) & Integer.MAX_VALUE, shape, orientation, level, x, y, seqId, false, scenery.entity);
			}
		}
		if (layer == 3) {
			@Pc(290) GroundDecor groundDecor = getGroundDecor(level, x, y);
			if (groundDecor != null) {
				groundDecor.entity = new Loc(Integer.MAX_VALUE & (int) (groundDecor.key >>> 32), 22, orientation, level, x, y, seqId, false, groundDecor.entity);
			}
		}
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(IIIILclient!th;JZ)V")
	public static void setGroundDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int zFine, @OriginalArg(4) Entity entity, @OriginalArg(5) long key, @OriginalArg(6) boolean flat) {
		if (entity == null) {
			return;
		}
		@Pc(6) GroundDecor groundDecor = new GroundDecor();
		groundDecor.entity = entity;
		groundDecor.xFine = x * 128 + 64;
		groundDecor.yFine = y * 128 + 64;
		groundDecor.zFine = zFine;
		groundDecor.key = key;
		groundDecor.flat = flat;
		if (tiles[level][x][y] == null) {
			tiles[level][x][y] = new Tile(level, x, y);
		}
		tiles[level][x][y].groundDecor = groundDecor;
	}

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "(IB)I")
	public static int getLocCollisionMask(@OriginalArg(0) int shapeRotation) {
		@Pc(11) int shape = shapeRotation & 0x3F;
		@Pc(17) int rotation = shapeRotation >> 6 & 0x3;
		if (shape == 18) {
			if (rotation == 0) {
				return 1;
			}
			if (rotation == 1) {
				return 2;
			}
			if (rotation == 2) {
				return 4;
			}
			if (rotation == 3) {
				return 8;
			}
		} else if (shape == 19 || shape == 21) {
			if (rotation == 0) {
				return 16;
			}
			if (rotation == 1) {
				return 32;
			}
			if (rotation == 2) {
				return 64;
			}
			if (rotation == 3) {
				return 128;
			}
		}
		return 0;
	}

	@OriginalMember(owner = "client!vf", name = "a", descriptor = "(IIIILclient!th;Lclient!th;IIJ)V")
	public static void setWall(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int zFine, @OriginalArg(4) Entity primary, @OriginalArg(5) Entity secondary, @OriginalArg(6) int primaryFlags, @OriginalArg(7) int secondaryFlags, @OriginalArg(8) long key) {
		if (primary == null && secondary == null) {
			return;
		}
		@Pc(8) Wall wall = new Wall();
		wall.key = key;
		wall.xFine = x * 128 + 64;
		wall.yFine = y * 128 + 64;
		wall.zFine = zFine;
		wall.primary = primary;
		wall.secondary = secondary;
		wall.primaryFlags = primaryFlags;
		wall.secondaryFlags = secondaryFlags;
		for (@Pc(42) int level0 = level; level0 >= 0; level0--) {
			if (tiles[level0][x][y] == null) {
				tiles[level0][x][y] = new Tile(level0, x, y);
			}
		}
		tiles[level][x][y].wall = wall;
	}

	@OriginalMember(owner = "client!cd", name = "a", descriptor = "(IIIIZ)V")
	public static void init(@OriginalArg(3) int tileVisibility, @OriginalArg(4) boolean hasUnderWaterMap) {
		width = 104;
		length = 104;
		visibility = tileVisibility;
		surfaceGroundTiles = new Tile[4][width][length];
		surfaceTileHeights = new int[4][width + 1][length + 1];
		if (GlRenderer.enabled) {
			surfaceHdTiles = new GlTile[4][];
		}
		if (hasUnderWaterMap) {
			underWaterGroundTiles = new Tile[1][width][length];
			tileWaterData = new int[width][length];
			underwaterTileHeights = new int[1][width + 1][length + 1];
			if (GlRenderer.enabled) {
				underWaterHdTiles = new GlTile[1][];
			}
		} else {
			underWaterGroundTiles = null;
			tileWaterData = null;
			underwaterTileHeights = null;
			underWaterHdTiles = null;
		}
		setUnderwater(false);
		occluders = new Occluder[500];
		occluderCount = 0;
		activeOccluders = new Occluder[500];
		activeOccluderCount = 0;
		occlusionCache = new int[4][width + 1][length + 1];
		scenery = new Scenery[5000];
		sceneryLen = 0;
		sceneryBuffer = new Scenery[100];
		visibleTiles = new boolean[visibility + visibility + 1][visibility + visibility + 1];
		visibilityPoints = new boolean[visibility + visibility + 2][visibility + visibility + 2];
		occlusionHeights = new byte[4][width][length];
	}

	@OriginalMember(owner = "client!vj", name = "a", descriptor = "(III)J")
	public static long getWallKey(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null || tile.wall == null ? 0L : tile.wall.key;
	}

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(III)J")
	public static long getWallDecorKey(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		return tile == null || tile.wallDecor == null ? 0L : tile.wallDecor.key;
	}

	@OriginalMember(owner = "client!cl", name = "a", descriptor = "(III)J")
	public static long getSceneryKey(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return 0L;
		}
		for (@Pc(13) int i = 0; i < tile.sceneryLen; i++) {
			@Pc(22) Scenery scenery = tile.scenery[i];
			if ((scenery.key >> 29 & 0x3L) == 2L && scenery.xMin == x && scenery.yMin == y) {
				return scenery.key;
			}
		}
		return 0L;
	}

	@OriginalMember(owner = "client!g", name = "a", descriptor = "(III)Lclient!df;")
	public static WallDecor removeWallDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		} else {
			@Pc(14) WallDecor wallDecor = tile.wallDecor;
			tile.wallDecor = null;
			return wallDecor;
		}
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(III)Lclient!ec;")
	public static Scenery removeScenery(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		}
		for (@Pc(13) int i = 0; i < tile.sceneryLen; i++) {
			@Pc(22) Scenery scenery = tile.scenery[i];
			if ((scenery.key >> 29 & 0x3L) == 2L && scenery.xMin == x && scenery.yMin == y) {
				removeScenery(scenery);
				return scenery;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(III)Lclient!bm;")
	public static GroundDecor removeGroundDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		} else {
			@Pc(14) GroundDecor groundDecor = tile.groundDecor;
			tile.groundDecor = null;
			return groundDecor;
		}
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(Lclient!ec;)V")
	public static void removeScenery(@OriginalArg(0) Scenery scenery) {
		for (@Pc(2) int x = scenery.xMin; x <= scenery.xMax; x++) {
			for (@Pc(9) int y = scenery.yMin; y <= scenery.yMax; y++) {
				@Pc(22) Tile tile = tiles[scenery.level][x][y];
				if (tile != null) {
					@Pc(26) int i;
					for (i = 0; i < tile.sceneryLen; i++) {
						if (tile.scenery[i] == scenery) {
							tile.sceneryLen--;
							for (@Pc(44) int j = i; j < tile.sceneryLen; j++) {
								tile.scenery[j] = tile.scenery[j + 1];
								tile.interiorFlags[j] = tile.interiorFlags[j + 1];
							}
							tile.scenery[tile.sceneryLen] = null;
							break;
						}
					}
					tile.allInteriorFlags = 0;
					for (i = 0; i < tile.sceneryLen; i++) {
						tile.allInteriorFlags |= tile.interiorFlags[i];
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!di", name = "a", descriptor = "([Lclient!mj;ZI)V")
	public static void buildScene(@OriginalArg(0) CollisionMap[] collisionMaps, @OriginalArg(1) boolean underwater) {
		@Pc(10) int level;
		@Pc(15) int x;
		if (!underwater) {
			for (level = 0; level < 4; level++) {
				for (x = 0; x < 104; x++) {
					for (@Pc(22) int y = 0; y < 104; y++) {
						if ((renderFlags[level][x][y] & 0x1) == 1) {
							@Pc(43) int transformedLevel = level;
							if ((renderFlags[1][x][y] & 0x2) == 2) {
								transformedLevel = level - 1;
							}
							if (transformedLevel >= 0) {
								collisionMaps[transformedLevel].flagTile(y, x);
							}
						}
					}
				}
			}
			hueJitter += (int) (Math.random() * 5.0D) - 2;
			if (hueJitter < -16) {
				hueJitter = -16;
			}
			if (hueJitter > 16) {
				hueJitter = 16;
			}
			lightnessJitter += (int) (Math.random() * 5.0D) - 2;
			if (lightnessJitter < -8) {
				lightnessJitter = -8;
			}
			if (lightnessJitter > 8) {
				lightnessJitter = 8;
			}
		}
		@Pc(128) byte levels;
		if (underwater) {
			levels = 1;
		} else {
			levels = 4;
		}
		level = lightnessJitter >> 2 << 10;
		@Pc(142) int[][] underlayColors = new int[104][104];
		@Pc(146) int[][] tileLightness = new int[104][104];
		x = hueJitter >> 1;
		@Pc(152) int currentLevel;
		@Pc(168) int i;
		@Pc(173) int j;
		@Pc(178) int k;
		@Pc(194) int overlay;
		@Pc(200) int m;
		@Pc(202) int n;
		@Pc(209) int p;
		@Pc(349) int z;
		@Pc(234) int q;
		@Pc(254) int r;
		@Pc(267) int s;
		for (currentLevel = 0; currentLevel < levels; currentLevel++) {
			@Pc(159) byte[][] shadowData = shadowmap[currentLevel];
			@Pc(273) int t;
			@Pc(326) int v;
			@Pc(332) int w;
			@Pc(322) int u;
			if (!GlRenderer.enabled) {
				i = (int) Math.sqrt(5100.0D);
				j = i * 768 >> 8;
				for (k = 1; k < 103; k++) {
					for (overlay = 1; overlay < 103; overlay++) {
						p = tileHeights[currentLevel][overlay][k + 1] - tileHeights[currentLevel][overlay][k - 1];
						n = tileHeights[currentLevel][overlay + 1][k] - tileHeights[currentLevel][overlay - 1][k];
						z = (int) Math.sqrt(n * n + p * p + 65536);
						s = (p << 8) / z;
						r = -65536 / z;
						q = (n << 8) / z;
						t = (shadowData[overlay][k] >> 1) + (shadowData[overlay][k - 1] >> 2) + (shadowData[overlay - -1][k] >> 3) + (shadowData[overlay - 1][k] >> 2) + (shadowData[overlay][k + 1] >> 3);
						m = (s * -50 + q * -50 + r * -10) / j + 74;
						tileLightness[overlay][k] = m - t;
					}
				}
			} else if (Preferences.highDetailLighting) {
				for (i = 1; i < 103; i++) {
					for (j = 1; j < 103; j++) {
						overlay = (shadowData[j + 1][i] >> 3) + (shadowData[j - 1][i] >> 2) + (shadowData[j][i + -1] >> 2) + (shadowData[j][i + 1] >> 3) + (shadowData[j][i] >> 1);
						tileLightness[j][i] = 74 - overlay;
					}
				}
			} else {
				i = (int) FogManager.light0Position[0];
				j = (int) FogManager.light0Position[1];
				k = (int) FogManager.light0Position[2];
				overlay = (int) Math.sqrt(j * j + i * i + k * k);
				m = overlay * 1024 >> 8;
				for (n = 1; n < 103; n++) {
					for (p = 1; p < 103; p++) {
						q = tileHeights[currentLevel][p + 1][n] - tileHeights[currentLevel][p - 1][n];
						r = tileHeights[currentLevel][p][n + 1] - tileHeights[currentLevel][p][n - 1];
						s = (int) Math.sqrt(q * q + r * r + 65536);
						t = (q << 8) / s;
						u = (shadowData[p][n + 1] >> 3) + (shadowData[p][n - 1] >> 2) + (shadowData[p - 1][n] >> 2) + (shadowData[p + 1][n] >> 3) + (shadowData[p][n] >> 1);
						v = -65536 / s;
						w = (r << 8) / s;
						z = (k * w + i * t + v * j) / m + 96;
						tileLightness[p][n] = z - (int) ((float) u * 1.7F);
					}
				}
			}
			for (i = 0; i < 104; i++) {
				rowWeightedHue[i] = 0;
				rowSaturation[i] = 0;
				rowLightness[i] = 0;
				rowChroma[i] = 0;
				rowCount[i] = 0;
			}
			for (i = -5; i < 104; i++) {
				for (j = 0; j < 104; j++) {
					k = i + 5;
					@Pc(729) int unused;
					if (k < 104) {
						overlay = tileUnderlays[currentLevel][k][j] & 0xFF;
						if (overlay > 0) {
							@Pc(693) FluType type = FluTypeList.get(overlay - 1);
							rowWeightedHue[j] += type.weightedHue;
							rowSaturation[j] += type.saturation;
							rowLightness[j] += type.lightness;
							rowChroma[j] += type.chroma;
							unused = rowCount[j]++;
						}
					}
					overlay = i - 5;
					if (overlay >= 0) {
						m = tileUnderlays[currentLevel][overlay][j] & 0xFF;
						if (m > 0) {
							@Pc(758) FluType fluType2 = FluTypeList.get(m - 1);
							rowWeightedHue[j] -= fluType2.weightedHue;
							rowSaturation[j] -= fluType2.saturation;
							rowLightness[j] -= fluType2.lightness;
							rowChroma[j] -= fluType2.chroma;
							unused = rowCount[j]--;
						}
					}
				}
				if (i >= 0) {
					j = 0;
					overlay = 0;
					k = 0;
					m = 0;
					n = 0;
					for (p = -5; p < 104; p++) {
						z = p + 5;
						if (z < 104) {
							k += rowSaturation[z];
							n += rowCount[z];
							j += rowWeightedHue[z];
							m += rowChroma[z];
							overlay += rowLightness[z];
						}
						q = p - 5;
						if (q >= 0) {
							k -= rowSaturation[q];
							m -= rowChroma[q];
							j -= rowWeightedHue[q];
							n -= rowCount[q];
							overlay -= rowLightness[q];
						}
						if (p >= 0 && n > 0) {
							underlayColors[i][p] = ColorUtils.packHsl(overlay / n, k / n, j * 256 / m);
						}
					}
				}
			}
			for (i = 1; i < 103; i++) {
				tileLoop:
				for (j = 1; j < 103; j++) {
					if (underwater || allLevelsAreVisible() || (renderFlags[0][i][j] & 0x2) != 0 || (renderFlags[currentLevel][i][j] & 0x10) == 0 && getRenderLevel(j, i, currentLevel) == centralPlane) {
						if (firstVisibleLevel > currentLevel) {
							firstVisibleLevel = currentLevel;
						}
						k = tileUnderlays[currentLevel][i][j] & 0xFF;
						overlay = tileOverlays[currentLevel][i][j] & 0xFF;
						if (k > 0 || overlay > 0) {
							n = tileHeights[currentLevel][i + 1][j];
							m = tileHeights[currentLevel][i][j];
							z = tileHeights[currentLevel][i][j + 1];
							p = tileHeights[currentLevel][i + 1][j + 1];
							if (currentLevel > 0) {
								@Pc(1067) boolean occludesUnder = k != 0 || tileShapes[currentLevel][i][j] == 0;
								if (overlay > 0 && !FloTypeList.get(overlay - 1).occludeUnderlay) {
									occludesUnder = false;
								}
								if (occludesUnder && m == n && m == p && z == m) {
									occludeFlags[currentLevel][i][j] |= 0x4;
								}
							}
							if (k <= 0) {
								q = -1;
								r = 0;
							} else {
								q = underlayColors[i][j];
								s = (q & 0x7F) + x;
								if (s < 0) {
									s = 0;
								} else if (s > 127) {
									s = 127;
								}
								t = (q & 0x380) + (q + level & 0xFC00) + s;
								r = Rasteriser.palette[ColorUtils.multiplyLightnessSafe(96, t)];
							}
							s = tileLightness[i][j];
							w = tileLightness[i][j + 1];
							t = tileLightness[i + 1][j];
							v = tileLightness[i + 1][j + 1];
							if (overlay == 0) {
								setTile(currentLevel, i, j, 0, 0, -1, m, n, p, z, ColorUtils.multiplyLightnessSafe(s, q), ColorUtils.multiplyLightnessSafe(t, q), ColorUtils.multiplyLightnessSafe(v, q), ColorUtils.multiplyLightnessSafe(w, q), 0, 0, 0, 0, r, 0);
								if (GlRenderer.enabled && currentLevel > 0 && q != -1 && FluTypeList.get(k - 1).blockShadow) {
									ShadowManager.addFloorShadow(0, 0, true, false, i, j, m - tileHeights[0][i][j], -tileHeights[0][i + 1][j] + n, p - tileHeights[0][i + 1][j + 1], z - tileHeights[0][i][j + 1]);
								}
								if (GlRenderer.enabled && !underwater && tileWaterData != null && currentLevel == 0) {
									for (u = i - 1; u <= i + 1; u++) {
										for (@Pc(1794) int ny = j - 1; ny <= j + 1; ny++) {
											if ((u != i || j != ny) && u >= 0 && u < 104 && ny >= 0 && ny < 104) {
												@Pc(1834) int adjOverlay = tileOverlays[currentLevel][u][ny] & 0xFF;
												if (adjOverlay != 0) {
													@Pc(1842) FloType adjFloType = FloTypeList.get(adjOverlay - 1);
													if (adjFloType.texture != -1 && Rasteriser.textureProvider.getMaterialType(adjFloType.texture) == MaterialManager.WATER) {
														tileWaterData[i][j] = adjFloType.waterColor + (adjFloType.waterOpacity << 24);
														continue tileLoop;
													}
												}
											}
										}
									}
								}
							} else {
								u = tileShapes[currentLevel][i][j] + 1;
								@Pc(1242) byte overlayAngle = tileAngles[currentLevel][i][j];
								@Pc(1248) FloType overlayType = FloTypeList.get(overlay - 1);
								@Pc(1301) int overlayBaseColor;
								@Pc(1353) int overlayRgb;
								@Pc(1288) int texture;
								if (GlRenderer.enabled && !underwater && tileWaterData != null && currentLevel == 0) {
									if (overlayType.texture != -1 && Rasteriser.textureProvider.getMaterialType(overlayType.texture) == MaterialManager.WATER) {
										tileWaterData[i][j] = (overlayType.waterOpacity << 24) + overlayType.waterColor;
									} else {
										adjacentWaterSearch:
										for (texture = i - 1; texture <= i + 1; texture++) {
											for (overlayBaseColor = j - 1; overlayBaseColor <= j + 1; overlayBaseColor++) {
												if ((i != texture || overlayBaseColor != j) && texture >= 0 && texture < 104 && overlayBaseColor >= 0 && overlayBaseColor < 104) {
													overlayRgb = tileOverlays[currentLevel][texture][overlayBaseColor] & 0xFF;
													if (overlayRgb != 0) {
														@Pc(1366) FloType adjFloType2 = FloTypeList.get(overlayRgb - 1);
														if (adjFloType2.texture != -1 && Rasteriser.textureProvider.getMaterialType(adjFloType2.texture) == MaterialManager.WATER) {
															tileWaterData[i][j] = adjFloType2.waterColor + (adjFloType2.waterOpacity << 24);
															break adjacentWaterSearch;
														}
													}
												}
											}
										}
									}
								}
								texture = overlayType.texture;
								if (texture >= 0 && !Rasteriser.textureProvider.isTextureRepeating(texture)) {
									texture = -1;
								}
								@Pc(1458) int adjustedHsl;
								@Pc(1429) int adjustedHue;
								if (texture >= 0) {
									overlayBaseColor = -1;
									overlayRgb = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(Rasteriser.textureProvider.getAverageColor(texture), 96)];
								} else if (overlayType.baseColor == -1) {
									overlayBaseColor = -2;
									overlayRgb = 0;
								} else {
									overlayBaseColor = overlayType.baseColor;
									adjustedHue = x + (overlayBaseColor & 0x7F);
									if (adjustedHue < 0) {
										adjustedHue = 0;
									} else if (adjustedHue > 127) {
										adjustedHue = 127;
									}
									adjustedHsl = (overlayBaseColor & 0x380) + ((overlayBaseColor + level & 0xFC00) + adjustedHue);
									overlayRgb = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(adjustedHsl, 96)];
								}
								if (overlayType.secondaryColor >= 0) {
									adjustedHue = overlayType.secondaryColor;
									adjustedHsl = x + (adjustedHue & 0x7F);
									if (adjustedHsl < 0) {
										adjustedHsl = 0;
									} else if (adjustedHsl > 127) {
										adjustedHsl = 127;
									}
									@Pc(1529) int secondaryHsl = (adjustedHue & 0x380) + ((adjustedHue + level & 0xFC00) + adjustedHsl);
									overlayRgb = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(secondaryHsl, 96)];
								}
								setTile(currentLevel, i, j, u, overlayAngle, texture, m, n, p, z, ColorUtils.multiplyLightnessSafe(s, q), ColorUtils.multiplyLightnessSafe(t, q), ColorUtils.multiplyLightnessSafe(v, q), ColorUtils.multiplyLightnessSafe(w, q), ColorUtils.multiplyLightnessGrayscale(overlayBaseColor, s), ColorUtils.multiplyLightnessGrayscale(overlayBaseColor, t), ColorUtils.multiplyLightnessGrayscale(overlayBaseColor, v), ColorUtils.multiplyLightnessGrayscale(overlayBaseColor, w), r, overlayRgb);
								if (GlRenderer.enabled && currentLevel > 0) {
									ShadowManager.addFloorShadow(u, overlayAngle, overlayBaseColor == -2 || !overlayType.castsShadow, q == -1 || !FluTypeList.get(k - 1).blockShadow, i, j, m - tileHeights[0][i][j], n - tileHeights[0][i + 1][j], p - tileHeights[0][i + 1][j + 1], -tileHeights[0][i][j + 1] + z);
								}
							}
						}
					}
				}
			}
			if (GlRenderer.enabled) {
				@Pc(1888) float[][] normalsY = new float[105][105];
				@Pc(1892) int[][] levelHeights = tileHeights[currentLevel];
				@Pc(1896) float[][] normalsX = new float[105][105];
				@Pc(1900) float[][] normalsZ = new float[105][105];
				m = 1;
				while (true) {
					if (m > 103) {
						@Pc(2025) GlTile[] plainTiles;
						if (underwater) {
							plainTiles = buildGlPlainTiles(renderFlags, tileShapes[currentLevel], tileUnderlays[currentLevel], tileLightness, normalsX, tileWaterData, tileOverlays[currentLevel], tileAngles[currentLevel], normalsY, currentLevel, normalsZ, underlayColors, tileHeights[currentLevel], surfaceTileHeights[0]);
							setUnderwaterHdTiles(currentLevel, plainTiles);
							break;
						}
						plainTiles = buildGlPlainTiles(renderFlags, tileShapes[currentLevel], tileUnderlays[currentLevel], tileLightness, normalsX, null, tileOverlays[currentLevel], tileAngles[currentLevel], normalsY, currentLevel, normalsZ, underlayColors, tileHeights[currentLevel], null);
						@Pc(2049) GlTile[] shapedTiles = buildGlShapedTiles(normalsX, normalsY, tileHeights[currentLevel], currentLevel, normalsZ, tileAngles[currentLevel], tileLightness, tileShapes[currentLevel], tileUnderlays[currentLevel], tileOverlays[currentLevel], renderFlags);
						@Pc(2057) GlTile[] allTiles = new GlTile[plainTiles.length + shapedTiles.length];
						for (z = 0; z < plainTiles.length; z++) {
							allTiles[z] = plainTiles[z];
						}
						for (z = 0; z < shapedTiles.length; z++) {
							allTiles[plainTiles.length + z] = shapedTiles[z];
						}
						setUnderwaterHdTiles(currentLevel, allTiles);
						buildGlLighting(normalsZ, tileUnderlays[currentLevel], tileAngles[currentLevel], LightingManager.lights, currentLevel, LightingManager.lightCount, normalsX, tileShapes[currentLevel], tileOverlays[currentLevel], tileHeights[currentLevel], normalsY);
						break;
					}
					for (n = 1; n <= 103; n++) {
						z = levelHeights[n][m + 1] - levelHeights[n][m - 1];
						p = levelHeights[n + 1][m] - levelHeights[n - 1][m];
						@Pc(1962) float normalLen = (float) Math.sqrt(p * p + z * z + 65536);
						normalsY[n][m] = (float) p / normalLen;
						normalsX[n][m] = -256.0F / normalLen;
						normalsZ[n][m] = (float) z / normalLen;
					}
					m++;
				}
			}
			tileUnderlays[currentLevel] = null;
			tileOverlays[currentLevel] = null;
			tileShapes[currentLevel] = null;
			tileAngles[currentLevel] = null;
			shadowmap[currentLevel] = null;
		}
		mergeSceneNormals();
		if (underwater) {
			return;
		}
		@Pc(2204) int col;
		for (currentLevel = 0; currentLevel < 104; currentLevel++) {
			for (col = 0; col < 104; col++) {
				if ((renderFlags[1][currentLevel][col] & 0x2) == 2) {
					bridgeTile(currentLevel, col);
				}
			}
		}
		for (currentLevel = 0; currentLevel < 4; currentLevel++) {
			for (col = 0; col <= 104; col++) {
				for (i = 0; i <= 104; i++) {
					if ((occludeFlags[currentLevel][i][col] & 0x1) != 0) {
						m = currentLevel;
						for (j = col; j > 0 && (occludeFlags[currentLevel][i][j - 1] & 0x1) != 0; j--) {
						}
						overlay = currentLevel;
						for (k = col; k < 104 && (occludeFlags[currentLevel][i][k + 1] & 0x1) != 0; k++) {
						}
						expandLevelDown:
						while (overlay > 0) {
							for (n = j; n <= k; n++) {
								if ((occludeFlags[overlay - 1][i][n] & 0x1) == 0) {
									break expandLevelDown;
								}
							}
							overlay--;
						}
						expandLevelUp:
						while (m < 3) {
							for (n = j; n <= k; n++) {
								if ((occludeFlags[m + 1][i][n] & 0x1) == 0) {
									break expandLevelUp;
								}
							}
							m++;
						}
						n = (m + 1 - overlay) * (-j + (k - -1));
						if (n >= 8) {
							z = tileHeights[m][i][j] - 240;
							q = tileHeights[overlay][i][j];
							Occluder.add(1, i * 128, i * 128, j * 128, k * 128 + 128, z, q);
							for (r = overlay; r <= m; r++) {
								for (s = j; s <= k; s++) {
									occludeFlags[r][i][s] &= 0xFFFFFFFE;
								}
							}
						}
					}
					if ((occludeFlags[currentLevel][i][col] & 0x2) != 0) {
						for (j = i; j > 0 && (occludeFlags[currentLevel][j - 1][col] & 0x2) != 0; j--) {
						}
						m = currentLevel;
						overlay = currentLevel;
						for (k = i; k < 104 && (occludeFlags[currentLevel][k + 1][col] & 0x2) != 0; k++) {
						}
						expandLevelDown:
						while (overlay > 0) {
							for (n = j; n <= k; n++) {
								if ((occludeFlags[overlay - 1][n][col] & 0x2) == 0) {
									break expandLevelDown;
								}
							}
							overlay--;
						}
						expandLevelUp:
						while (m < 3) {
							for (n = j; n <= k; n++) {
								if ((occludeFlags[m + 1][n][col] & 0x2) == 0) {
									break expandLevelUp;
								}
							}
							m++;
						}
						n = (k + 1 - j) * (-overlay + m - -1);
						if (n >= 8) {
							z = tileHeights[m][j][col] - 240;
							q = tileHeights[overlay][j][col];
							Occluder.add(2, j * 128, k * 128 + 128, col * 128, col * 128, z, q);
							for (r = overlay; r <= m; r++) {
								for (s = j; s <= k; s++) {
									occludeFlags[r][s][col] &= 0xFFFFFFFD;
								}
							}
						}
					}
					if ((occludeFlags[currentLevel][i][col] & 0x4) != 0) {
						j = i;
						k = i;
						for (overlay = col; overlay > 0 && (occludeFlags[currentLevel][i][overlay - 1] & 0x4) != 0; overlay--) {
						}
						for (m = col; m < 104 && (occludeFlags[currentLevel][i][m + 1] & 0x4) != 0; m++) {
						}
						expandMinX:
						while (j > 0) {
							for (n = overlay; n <= m; n++) {
								if ((occludeFlags[currentLevel][j - 1][n] & 0x4) == 0) {
									break expandMinX;
								}
							}
							j--;
						}
						expandMaxX:
						while (k < 104) {
							for (n = overlay; n <= m; n++) {
								if ((occludeFlags[currentLevel][k + 1][n] & 0x4) == 0) {
									break expandMaxX;
								}
							}
							k++;
						}
						if ((k + 1 - j) * (m - (overlay - 1)) >= 4) {
							n = tileHeights[currentLevel][j][overlay];
							Occluder.add(4, j * 128, k * 128 + 128, overlay * 128, m * 128 + 128, n, n);
							for (p = j; p <= k; p++) {
								for (z = overlay; z <= m; z++) {
									occludeFlags[currentLevel][p][z] &= 0xFFFFFFFB;
								}
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(I[Lclient!hg;)V")
	public static void setUnderwaterHdTiles(@OriginalArg(0) int level, @OriginalArg(1) GlTile[] hdTiles) {
		underwaterHdTiles[level] = hdTiles;
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(IIIIIIIIIIIIIIIIIIII)V")
	public static void setTile(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int shape, @OriginalArg(4) int angle, @OriginalArg(5) int texture, @OriginalArg(6) int heightSW, @OriginalArg(7) int heightSE, @OriginalArg(8) int heightNE, @OriginalArg(9) int heightNW, @OriginalArg(10) int underlayColorSW, @OriginalArg(11) int underlayColorSE, @OriginalArg(12) int underlayColorNE, @OriginalArg(13) int underlayColorNW, @OriginalArg(14) int overlayColorSW, @OriginalArg(15) int overlayColorSE, @OriginalArg(16) int overlayColorNE, @OriginalArg(17) int overlayColorNW, @OriginalArg(18) int underlayRgb, @OriginalArg(19) int overlayRgb) {
		@Pc(12) PlainTile tile;
		@Pc(14) int level0;
		if (shape == 0) {
			tile = new PlainTile(underlayColorSW, underlayColorSE, underlayColorNE, underlayColorNW, -1, underlayRgb, false);
			for (level0 = level; level0 >= 0; level0--) {
				if (tiles[level0][x][y] == null) {
					tiles[level0][x][y] = new Tile(level0, x, y);
				}
			}
			tiles[level][x][y].plainTile = tile;
		} else if (shape == 1) {
			tile = new PlainTile(overlayColorSW, overlayColorSE, overlayColorNE, overlayColorNW, texture, overlayRgb, heightSW == heightSE && heightSW == heightNE && heightSW == heightNW);
			for (level0 = level; level0 >= 0; level0--) {
				if (tiles[level0][x][y] == null) {
					tiles[level0][x][y] = new Tile(level0, x, y);
				}
			}
			tiles[level][x][y].plainTile = tile;
		} else {
			@Pc(134) ShapedTile shapedTile = new ShapedTile(shape, angle, texture, x, y, heightSW, heightSE, heightNE, heightNW, underlayColorSW, underlayColorSE, underlayColorNE, underlayColorNW, overlayColorSW, overlayColorSE, overlayColorNE, overlayColorNW, underlayRgb, overlayRgb);
			for (level0 = level; level0 >= 0; level0--) {
				if (tiles[level0][x][y] == null) {
					tiles[level0][x][y] = new Tile(level0, x, y);
				}
			}
			tiles[level][x][y].shapedTile = shapedTile;
		}
	}

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "(III)V")
	public static void mergeSceneNormals() {
		for (@Pc(1) int level = 0; level < levels; level++) {
			for (@Pc(6) int x = 0; x < width; x++) {
				for (@Pc(11) int y = 0; y < length; y++) {
					@Pc(22) Tile tile = tiles[level][x][y];
					if (tile != null) {
						@Pc(27) Wall wall = tile.wall;
						if (wall != null && wall.primary.canMerge()) {
							mergeEntityNormals(wall.primary, level, x, y, 1, 1);
							if (wall.secondary != null && wall.secondary.canMerge()) {
								mergeEntityNormals(wall.secondary, level, x, y, 1, 1);
								wall.primary.mergeNormals(wall.secondary, 0, 0, 0, false);
								wall.secondary = wall.secondary.createModel();
							}
							wall.primary = wall.primary.createModel();
						}
						for (@Pc(83) int i = 0; i < tile.sceneryLen; i++) {
							@Pc(92) Scenery s = tile.scenery[i];
							if (s != null && s.entity.canMerge()) {
								mergeEntityNormals(s.entity, level, x, y, s.xMax + 1 - s.xMin, s.yMax - s.yMin + 1);
								s.entity = s.entity.createModel();
							}
						}
						@Pc(131) GroundDecor groundDecor = tile.groundDecor;
						if (groundDecor != null && groundDecor.entity.canMerge()) {
							mergeGroundDecorNormals(groundDecor.entity, level, x, y);
							groundDecor.entity = groundDecor.entity.createModel();
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(IIIIIIIILclient!th;IZJ)Z")
	public static boolean addSceneryEntity(@OriginalArg(0) int level, @OriginalArg(1) int xMin, @OriginalArg(2) int yMin, @OriginalArg(3) int xSize, @OriginalArg(4) int ySize, @OriginalArg(5) int xFine, @OriginalArg(6) int yFine, @OriginalArg(7) int zFine, @OriginalArg(8) Entity entity, @OriginalArg(9) int orientation, @OriginalArg(10) boolean temporary, @OriginalArg(11) long key) {
		@Pc(6) boolean isUnderwater = tileHeights == underwaterTileHeights;
		@Pc(8) int waterData = 0;
		@Pc(17) int x;
		for (@Pc(10) int tx = xMin; tx < xMin + xSize; tx++) {
			for (x = yMin; x < yMin + ySize; x++) {
				if (tx < 0 || x < 0 || tx >= width || x >= length) {
					return false;
				}
				@Pc(42) Tile tile = tiles[level][tx][x];
				if (tile != null && tile.sceneryLen >= 5) {
					return false;
				}
			}
		}
		@Pc(58) Scenery s = new Scenery();
		s.key = key;
		s.level = level;
		s.xFine = xFine;
		s.yFine = yFine;
		s.zFine = zFine;
		s.entity = entity;
		s.orientation = orientation;
		s.xMin = xMin;
		s.yMin = yMin;
		s.xMax = xMin + xSize - 1;
		s.yMax = yMin + ySize - 1;
		@Pc(108) int y;
		for (x = xMin; x < xMin + xSize; x++) {
			for (y = yMin; y < yMin + ySize; y++) {
				@Pc(115) int flags = 0;
				if (x > xMin) {
					flags++;
				}
				if (x < xMin + xSize - 1) {
					flags += 4;
				}
				if (y > yMin) {
					flags += 8;
				}
				if (y < yMin + ySize - 1) {
					flags += 2;
				}
				for (@Pc(141) int lvl = level; lvl >= 0; lvl--) {
					if (tiles[lvl][x][y] == null) {
						tiles[lvl][x][y] = new Tile(lvl, x, y);
					}
				}
				@Pc(174) Tile tile = tiles[level][x][y];
				tile.scenery[tile.sceneryLen] = s;
				tile.interiorFlags[tile.sceneryLen] = flags;
				tile.allInteriorFlags |= flags;
				tile.sceneryLen++;
				if (isUnderwater && tileWaterData[x][y] != 0) {
					waterData = tileWaterData[x][y];
				}
			}
		}
		if (isUnderwater && waterData != 0) {
			for (x = xMin; x < xMin + xSize; x++) {
				for (y = yMin; y < yMin + ySize; y++) {
					if (tileWaterData[x][y] == 0) {
						tileWaterData[x][y] = waterData;
					}
				}
			}
		}
		if (temporary) {
			scenery[sceneryLen++] = s;
		}
		return true;
	}

	@OriginalMember(owner = "client!dg", name = "a", descriptor = "(IIIIILclient!th;IJZ)Z")
	public static boolean add(@OriginalArg(0) int level, @OriginalArg(1) int xFine, @OriginalArg(2) int yFine, @OriginalArg(3) int zFine, @OriginalArg(4) int radius, @OriginalArg(5) Entity entity, @OriginalArg(6) int orientation, @OriginalArg(7) long key, @OriginalArg(8) boolean extendWithOrientation) {
		if (entity == null) {
			return true;
		}
		@Pc(7) int xMin = xFine - radius;
		@Pc(11) int yMin = yFine - radius;
		@Pc(15) int xMax = xFine + radius;
		@Pc(19) int yMax = yFine + radius;
		if (extendWithOrientation) {
			if (orientation > 640 && orientation < 1408) {
				yMax += 128;
			}
			if (orientation > 1152 && orientation < 1920) {
				xMax += 128;
			}
			if (orientation > 1664 || orientation < 384) {
				yMin -= 128;
			}
			if (orientation > 128 && orientation < 896) {
				xMin -= 128;
			}
		}
		xMin /= 128;
		yMin /= 128;
		xMax /= 128;
		yMax /= 128;
		return addSceneryEntity(level, xMin, yMin, xMax + 1 - xMin, yMax - yMin + 1, xFine, yFine, zFine, entity, orientation, true, key);
	}

	@OriginalMember(owner = "client!dg", name = "a", descriptor = "(IIIIIILclient!mj;)V")
	public static void addGroundDecorEntity(@OriginalArg(1) int y, @OriginalArg(2) int x, @OriginalArg(3) int level, @OriginalArg(4) int layer, @OriginalArg(5) int transformedLevel, @OriginalArg(6) CollisionMap collision) {
		@Pc(9) long key = 0L;
		if (layer == 0) {
			key = getWallKey(level, x, y);
		} else if (layer == 1) {
			key = getWallDecorKey(level, x, y);
		} else if (layer == 2) {
			key = getSceneryKey(level, x, y);
		} else if (layer == 3) {
			key = getGroundDecorKey(level, x, y);
		}
		@Pc(57) int shape = (int) key >> 14 & 0x1F;
		@Pc(70) int id = (int) (key >>> 32) & Integer.MAX_VALUE;
		@Pc(74) LocType type = LocTypeList.get(id);
		if (type.hasAreaSound()) {
			AreaSoundManager.remove(x, type, y, level);
		}
		@Pc(92) int angle = (int) key >> 20 & 0x3;
		if (key == 0L) {
			return;
		}
		@Pc(100) Entity primaryEntity = null;
		@Pc(102) Entity secondaryEntity = null;
		if (layer == 0) {
			@Pc(110) Wall wall = removeWall(level, x, y);
			if (wall != null) {
				primaryEntity = wall.primary;
				secondaryEntity = wall.secondary;
			}
			if (type.blockwalk != 0) {
				collision.unflagWall(angle, type.blockrange, y, shape, x);
			}
		} else if (layer == 1) {
			@Pc(233) WallDecor wallDecor = removeWallDecor(level, x, y);
			if (wallDecor != null) {
				primaryEntity = wallDecor.primary;
				secondaryEntity = wallDecor.secondary;
			}
		} else if (layer == 2) {
			@Pc(148) Scenery scenery = removeScenery(level, x, y);
			if (scenery != null) {
				primaryEntity = scenery.entity;
			}
			if (type.blockwalk != 0 && type.width + x < 104 && type.width + y < 104 && x + type.length < 104 && y + type.length < 104) {
				collision.unflagScenery(x, type.width, type.blockrange, angle, type.length, y);
			}
		} else if (layer == 3) {
			@Pc(211) GroundDecor groundDecor = removeGroundDecor(level, x, y);
			if (groundDecor != null) {
				primaryEntity = groundDecor.entity;
			}
			if (type.blockwalk == 1) {
				collision.unflagGroundDecor(y, x);
			}
		}
		if (!GlRenderer.enabled || !type.castshadow) {
			return;
		}
		if (shape == 2) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, 0, angle + 4, 0, shape, x, y, transformedLevel);
			}
			if (secondaryEntity instanceof Loc) {
				((Loc) secondaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, 0, angle + 1 & 0x3, 0, shape, x, y, transformedLevel);
			}
		} else if (shape == 5) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, WALL_DECO_ROT_SIZE_Y_DIR[angle] * 8, angle, WALL_DECO_ROT_SIZE_X_DIR[angle] * 8, 4, x, y, transformedLevel);
			}
		} else if (shape == 6) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, WALL_OFFSET_Y[angle] * 8, angle + 4, WALL_OFFSET_X[angle] * 8, 4, x, y, transformedLevel);
			}
		} else if (shape == 7) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, 0, (angle + 2 & 0x3) + 4, 0, 4, x, y, transformedLevel);
			}
		} else if (shape == 8) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, WALL_OFFSET_Y[angle] * 8, angle + 4, WALL_OFFSET_X[angle] * 8, 4, x, y, transformedLevel);
			}
			if (secondaryEntity instanceof Loc) {
				((Loc) secondaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, WALL_OFFSET_Y[angle] * 8, (angle + 2 & 0x3) + 4, WALL_OFFSET_X[angle] * 8, 4, x, y, transformedLevel);
			}
		} else if (shape == 11) {
			if (primaryEntity instanceof Loc) {
				((Loc) primaryEntity).resetShadow();
			} else {
				Loc.buildStaticShadow(type, 0, angle + 4, 0, 10, x, y, transformedLevel);
			}
		} else if (primaryEntity instanceof Loc) {
			((Loc) primaryEntity).resetShadow();
		} else {
			Loc.buildStaticShadow(type, 0, angle, 0, shape, x, y, transformedLevel);
		}
	}

	@OriginalMember(owner = "client!sd", name = "c", descriptor = "(II)V")
	public static void bridgeTile(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		@Pc(7) Tile groundTile = tiles[0][x][y];
		for (@Pc(9) int level = 0; level < 3; level++) {
			@Pc(30) Tile tile = tiles[level][x][y] = tiles[level + 1][x][y];
			if (tile != null) {
				tile.drawLevel--;
				for (@Pc(40) int i = 0; i < tile.sceneryLen; i++) {
					@Pc(49) Scenery s = tile.scenery[i];
					if ((s.key >> 29 & 0x3L) == 2L && s.xMin == x && s.yMin == y) {
						s.level--;
					}
				}
			}
		}
		if (tiles[0][x][y] == null) {
			tiles[0][x][y] = new Tile(0, x, y);
		}
		tiles[0][x][y].linkedTile = groundTile;
		tiles[3][x][y] = null;
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(IIIIII)Z")
	public static boolean isAreaVisible(@OriginalArg(0) int level, @OriginalArg(1) int xMin, @OriginalArg(2) int xMax, @OriginalArg(3) int yMin, @OriginalArg(4) int yMax, @OriginalArg(5) int height) {
		@Pc(16) int xFine;
		@Pc(20) int yFine;
		if (xMin != xMax || yMin != yMax) {
			for (xFine = xMin; xFine <= xMax; xFine++) {
				for (yFine = yMin; yFine <= yMax; yFine++) {
					if (occlusionCache[level][xFine][yFine] == -drawCycle) {
						return false;
					}
				}
			}
			xFine = (xMin << 7) + 1;
			yFine = (yMin << 7) + 2;
			@Pc(156) int z = tileHeights[level][xMin][yMin] + height;
			if (!isOccluded(xFine, z, yFine)) {
				return false;
			}
			@Pc(169) int xFineMax = (xMax << 7) - 1;
			if (!isOccluded(xFineMax, z, yFine)) {
				return false;
			}
			@Pc(182) int yFineMax = (yMax << 7) - 1;
			if (!isOccluded(xFine, z, yFineMax)) {
				return false;
			} else return isOccluded(xFineMax, z, yFineMax);
		} else if (isTileOccluded(level, xMin, yMin)) {
			xFine = xMin << 7;
			yFine = yMin << 7;
			return isOccluded(xFine + 1, tileHeights[level][xMin][yMin] + height, yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][xMin + 1][yMin] + height, yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][xMin + 1][yMin + 1] + height, yFine + 128 - 1) && isOccluded(xFine + 1, tileHeights[level][xMin][yMin + 1] + height, yFine + 128 - 1);
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!hc", name = "a", descriptor = "(IIIILclient!th;Lclient!th;IIIIJ)V")
	public static void setWallDecor(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int orientation, @OriginalArg(4) Entity primary, @OriginalArg(5) Entity secondary, @OriginalArg(6) int flags, @OriginalArg(7) int zFine, @OriginalArg(8) int xOffset, @OriginalArg(9) int yOffset, @OriginalArg(10) long key) {
		if (primary == null) {
			return;
		}
		@Pc(6) WallDecor wallDecor = new WallDecor();
		wallDecor.key = key;
		wallDecor.xFine = x * 128 + 64;
		wallDecor.yFine = y * 128 + 64;
		wallDecor.orientation = orientation;
		wallDecor.primary = primary;
		wallDecor.secondary = secondary;
		wallDecor.flags = flags;
		wallDecor.zFine = zFine;
		wallDecor.xOffset = xOffset;
		wallDecor.yOffset = yOffset;
		for (@Pc(46) int level0 = level; level0 >= 0; level0--) {
			if (tiles[level0][x][y] == null) {
				tiles[level0][x][y] = new Tile(level0, x, y);
			}
		}
		tiles[level][x][y].wallDecor = wallDecor;
	}

	@OriginalMember(owner = "client!pb", name = "b", descriptor = "(III)Lclient!jj;")
	public static ObjStackEntity removeObjStack(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return null;
		} else {
			@Pc(14) ObjStackEntity entity = tile.objStack;
			tile.objStack = null;
			return entity;
		}
	}

	@OriginalMember(owner = "client!ub", name = "a", descriptor = "(Lclient!bj;Z)V")
	public static void renderTile(@OriginalArg(0) Tile startTile, @OriginalArg(1) boolean checkInterior) {
		tileQueue.addTail(startTile);
		while (true) {
			@Pc(8) Tile tile;
			@Pc(18) int tileX;
			@Pc(21) int tileY;
			@Pc(24) int drawLevel;
			@Pc(27) int level;
			@Pc(31) Tile[][] levelTiles;
			@Pc(65) int i;
			@Pc(115) int bestIndex;
			@Pc(894) int flags;
			@Pc(899) int distY;
			@Pc(904) int distMax;
			@Pc(153) Tile adjacentTile;
			@Pc(1332) int k;
			do {
				do {
					do {
						do {
							do {
								do {
									while (true) {
										// 1690:1697 waterData; 2028:2032 sceneryCount
										@Pc(44) int var9;
										// 1691:1694 waterColor; 1817:1852 wallDirIndex; 1970:1971 sceneryIdx; 2030:2081 sceneryBufferLen
										@Pc(48) int var10;
										// 1881:1888 decorDirection; 2090:2092 bestDeltaX
										@Pc(907) int var17;
										// 1883:1893 rotatedX; 2091:2092 bestDeltaY
										@Pc(916) int var18;
										// 1760:1769 linkedWall; 1977:2019 wall
										@Pc(363) Wall var22;
										// 1784:1909 groundVisible; 1969:1976 allSceneryDrawn
										@Pc(469) boolean var24;
										// 1772:1777 linkedScenery; 2033:2074 scenery
										@Pc(425) Scenery var25;
										// 1942:1962 neighborTile; 2037:2056 coveredTile
										@Pc(1179) Tile var32;
										while (true) {
											do {
												tile = (Tile) tileQueue.removeHead();
												if (tile == null) {
													return;
												}
											} while (!tile.checkedBelowLevel);
											tileX = tile.x;
											tileY = tile.y;
											drawLevel = tile.drawLevel;
											level = tile.level;
											levelTiles = tiles[drawLevel];
											@Pc(33) float depthLayer = 0.0F;
											if (GlRenderer.enabled) {
												if (underwaterTileHeights == tileHeights) {
													var9 = tileWaterData[tileX][tileY];
													var10 = var9 & 0xFFFFFF;
													if (var10 != lastUnderwaterColor) {
														lastUnderwaterColor = var10;
														WaterMaterialRenderer.setUnderwaterColor(var10);
														FogManager.setFogColor(WaterMaterialRenderer.getWaterFogColor());
													}
													i = var9 >>> 24 << 3;
													if (i != lastUnderwaterFogRange) {
														lastUnderwaterFogRange = i;
														MaterialManager.setUnderwaterFogRange(i);
													}
													bestIndex = surfaceTileHeights[0][tileX][tileY] + surfaceTileHeights[0][tileX + 1][tileY] + surfaceTileHeights[0][tileX][tileY + 1] + surfaceTileHeights[0][tileX + 1][tileY + 1] >> 2;
													MaterialManager.setMaterial(-bestIndex, 3);
													depthLayer = 201.5F;
													GlRenderer.setDepthLayer(depthLayer);
												} else {
													depthLayer = 201.5F - (float) (level + 1) * 50.0F;
													GlRenderer.setDepthLayer(depthLayer);
												}
											}
											if (!tile.visible) {
												break;
											}
											if (checkInterior) {
												if (drawLevel > 0) {
													adjacentTile = tiles[drawLevel - 1][tileX][tileY];
													if (adjacentTile != null && adjacentTile.checkedBelowLevel) {
														continue;
													}
												}
												if (tileX <= cameraTileX && tileX > LightingManager.visibleMinX) {
													adjacentTile = levelTiles[tileX - 1][tileY];
													if (adjacentTile != null && adjacentTile.checkedBelowLevel && (adjacentTile.visible || (tile.allInteriorFlags & 0x1) == 0)) {
														continue;
													}
												}
												if (tileX >= cameraTileX && tileX < LightingManager.visibleMaxX - 1) {
													adjacentTile = levelTiles[tileX + 1][tileY];
													if (adjacentTile != null && adjacentTile.checkedBelowLevel && (adjacentTile.visible || (tile.allInteriorFlags & 0x4) == 0)) {
														continue;
													}
												}
												if (tileY <= cameraTileY && tileY > LightingManager.visibleMinY) {
													adjacentTile = levelTiles[tileX][tileY - 1];
													if (adjacentTile != null && adjacentTile.checkedBelowLevel && (adjacentTile.visible || (tile.allInteriorFlags & 0x8) == 0)) {
														continue;
													}
												}
												if (tileY >= cameraTileY && tileY < LightingManager.visibleMaxY - 1) {
													adjacentTile = levelTiles[tileX][tileY + 1];
													if (adjacentTile != null && adjacentTile.checkedBelowLevel && (adjacentTile.visible || (tile.allInteriorFlags & 0x2) == 0)) {
														continue;
													}
												}
											} else {
												checkInterior = true;
											}
											tile.visible = false;
											if (tile.linkedTile != null) {
												adjacentTile = tile.linkedTile;
												if (GlRenderer.enabled) {
													GlRenderer.setDepthLayer(201.5F - (float) (adjacentTile.level + 1) * 50.0F);
												}
												if (adjacentTile.plainTile == null) {
													if (adjacentTile.shapedTile != null) {
														drawShapedTile(adjacentTile.shapedTile, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, 0, isTileOccluded(0, tileX, tileY));
													}
												} else
													drawPlainTile(adjacentTile.plainTile, 0, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, isTileOccluded(0, tileX, tileY));
												var22 = adjacentTile.wall;
												if (var22 != null) {
													if (GlRenderer.enabled) {
														if ((var22.primaryFlags & tile.wallDrawFlags) == 0) {
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
														} else {
															LightingManager.updateLightsForWallPiece(var22.primaryFlags, cameraX, cameraZ, cameraY, level, tileX, tileY);
														}
													}
													var22.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, var22.xFine - cameraX, var22.zFine - cameraZ, var22.yFine - cameraY, var22.key, drawLevel, null);
												}
												for (i = 0; i < adjacentTile.sceneryLen; i++) {
													var25 = adjacentTile.scenery[i];
													if (var25 != null) {
														if (GlRenderer.enabled) {
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
														}
														var25.entity.render(var25.orientation, sinPitch, cosPitch, sinYaw, cosYaw, var25.xFine - cameraX, var25.zFine - cameraZ, var25.yFine - cameraY, var25.key, drawLevel, null);
													}
												}
												if (GlRenderer.enabled) {
													GlRenderer.setDepthLayer(depthLayer);
												}
											}
											var24 = false;
											if (tile.plainTile == null) {
												if (tile.shapedTile != null) {
													if (isTileOccluded(level, tileX, tileY)) {
														drawShapedTile(tile.shapedTile, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, level, true);
													} else {
														var24 = true;
														drawShapedTile(tile.shapedTile, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, level, false);
													}
												}
											} else if (isTileOccluded(level, tileX, tileY)) {
												drawPlainTile(tile.plainTile, level, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, true);
											} else {
												var24 = true;
												if (tile.plainTile.colorNE != 12345678 || MiniMenu.walkPending && drawLevel <= MiniMenu.walkDestPlane) {
													drawPlainTile(tile.plainTile, level, sinPitch, cosPitch, sinYaw, cosYaw, tileX, tileY, false);
												}
											}
											if (var24) {
												@Pc(549) GroundDecor groundDecor = tile.groundDecor;
												if (groundDecor != null && (groundDecor.key & 0x80000000L) != 0L) {
													if (GlRenderer.enabled && groundDecor.flat) {
														GlRenderer.setDepthLayer(depthLayer + 50.0F - 1.5F);
													}
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													groundDecor.entity.render(0, sinPitch, cosPitch, sinYaw, cosYaw, groundDecor.xFine - cameraX, groundDecor.zFine - cameraZ, groundDecor.yFine - cameraY, groundDecor.key, drawLevel, null);
													if (GlRenderer.enabled && groundDecor.flat) {
														GlRenderer.setDepthLayer(depthLayer);
													}
												}
											}
											var10 = 0;
											i = 0;
											@Pc(616) Wall wall = tile.wall;
											@Pc(619) WallDecor wallDecor = tile.wallDecor;
											if (wall != null || wallDecor != null) {
												if (cameraTileX == tileX) {
													var10++;
												} else if (cameraTileX < tileX) {
													var10 += 2;
												}
												if (cameraTileY == tileY) {
													var10 += 3;
												} else if (cameraTileY > tileY) {
													var10 += 6;
												}
												i = WALL_VISIBILITY_FLAGS[var10];
												tile.wallDrawFlags = WALL_DRAW_FLAGS[var10];
											}
											if (wall != null) {
												if ((wall.primaryFlags & WALL_UNCULL_FLAGS[var10]) == 0) {
													tile.sceneryDrawFlags = 0;
												} else if (wall.primaryFlags == 16) {
													tile.sceneryDrawFlags = 3;
													tile.sceneryDrawnDirFlags = WALL_SCENERY_DIR_TYPE1[var10];
													tile.scenerySkipDirFlags = 3 - tile.sceneryDrawnDirFlags;
												} else if (wall.primaryFlags == 32) {
													tile.sceneryDrawFlags = 6;
													tile.sceneryDrawnDirFlags = WALL_SCENERY_DIR_TYPE2[var10];
													tile.scenerySkipDirFlags = 6 - tile.sceneryDrawnDirFlags;
												} else if (wall.primaryFlags == 64) {
													tile.sceneryDrawFlags = 12;
													tile.sceneryDrawnDirFlags = WALL_SCENERY_DIR_TYPE3[var10];
													tile.scenerySkipDirFlags = 12 - tile.sceneryDrawnDirFlags;
												} else {
													tile.sceneryDrawFlags = 9;
													tile.sceneryDrawnDirFlags = WALL_SCENERY_DIR_TYPE4[var10];
													tile.scenerySkipDirFlags = 9 - tile.sceneryDrawnDirFlags;
												}
												if ((wall.primaryFlags & i) != 0 && !isWallVisible(level, tileX, tileY, wall.primaryFlags)) {
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													wall.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wall.xFine - cameraX, wall.zFine - cameraZ, wall.yFine - cameraY, wall.key, drawLevel, null);
												}
												if ((wall.secondaryFlags & i) != 0 && !isWallVisible(level, tileX, tileY, wall.secondaryFlags)) {
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													wall.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wall.xFine - cameraX, wall.zFine - cameraZ, wall.yFine - cameraY, wall.key, drawLevel, null);
												}
											}
											if (wallDecor != null && !isTileVisible(level, tileX, tileY, wallDecor.primary.getMinY())) {
												if (GlRenderer.enabled) {
													GlRenderer.setDepthLayer(depthLayer - 0.5F);
												}
												if ((wallDecor.flags & i) != 0) {
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													wallDecor.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wallDecor.xFine + wallDecor.xOffset - cameraX, wallDecor.orientation - cameraZ, wallDecor.yFine + wallDecor.yOffset - cameraY, wallDecor.key, drawLevel, null);
												} else if (wallDecor.flags == 256) {
													flags = wallDecor.xFine - cameraX;
													distY = wallDecor.orientation - cameraZ;
													distMax = wallDecor.yFine - cameraY;
													var17 = wallDecor.zFine;
													if (var17 == 1 || var17 == 2) {
														var18 = -flags;
													} else {
														var18 = flags;
													}
													@Pc(928) int rotatedY;
													if (var17 == 2 || var17 == 3) {
														rotatedY = -distMax;
													} else {
														rotatedY = distMax;
													}
													if (rotatedY < var18) {
														if (GlRenderer.enabled) {
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
														}
														wallDecor.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, flags + wallDecor.xOffset, distY, distMax + wallDecor.yOffset, wallDecor.key, drawLevel, null);
													} else if (wallDecor.secondary != null) {
														if (GlRenderer.enabled) {
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
														}
														wallDecor.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, flags, distY, distMax, wallDecor.key, drawLevel, null);
													}
												}
												if (GlRenderer.enabled) {
													GlRenderer.setDepthLayer(depthLayer);
												}
											}
											if (var24) {
												@Pc(1001) GroundDecor groundDecor2 = tile.groundDecor;
												if (groundDecor2 != null && (groundDecor2.key & 0x80000000L) == 0L) {
													if (GlRenderer.enabled && groundDecor2.flat) {
														GlRenderer.setDepthLayer(depthLayer + 50.0F - 1.5F);
													}
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													groundDecor2.entity.render(0, sinPitch, cosPitch, sinYaw, cosYaw, groundDecor2.xFine - cameraX, groundDecor2.zFine - cameraZ, groundDecor2.yFine - cameraY, groundDecor2.key, drawLevel, null);
													if (GlRenderer.enabled && groundDecor2.flat) {
														GlRenderer.setDepthLayer(depthLayer);
													}
												}
												@Pc(1064) ObjStackEntity objStack = tile.objStack;
												if (objStack != null && objStack.heightOffset == 0) {
													if (GlRenderer.enabled) {
														LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
													}
													if (objStack.secondary != null) {
														objStack.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack.xFine - cameraX, objStack.zFine - cameraZ, objStack.yFine - cameraY, objStack.key, drawLevel, null);
													}
													if (objStack.tertiary != null) {
														objStack.tertiary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack.xFine - cameraX, objStack.zFine - cameraZ, objStack.yFine - cameraY, objStack.key, drawLevel, null);
													}
													if (objStack.primary != null) {
														objStack.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack.xFine - cameraX, objStack.zFine - cameraZ, objStack.yFine - cameraY, objStack.key, drawLevel, null);
													}
												}
											}
											flags = tile.allInteriorFlags;
											if (flags != 0) {
												if (tileX < cameraTileX && (flags & 0x4) != 0) {
													var32 = levelTiles[tileX + 1][tileY];
													if (var32 != null && var32.checkedBelowLevel) {
														tileQueue.addTail(var32);
													}
												}
												if (tileY < cameraTileY && (flags & 0x2) != 0) {
													var32 = levelTiles[tileX][tileY + 1];
													if (var32 != null && var32.checkedBelowLevel) {
														tileQueue.addTail(var32);
													}
												}
												if (tileX > cameraTileX && (flags & 0x1) != 0) {
													var32 = levelTiles[tileX - 1][tileY];
													if (var32 != null && var32.checkedBelowLevel) {
														tileQueue.addTail(var32);
													}
												}
												if (tileY > cameraTileY && (flags & 0x8) != 0) {
													var32 = levelTiles[tileX][tileY - 1];
													if (var32 != null && var32.checkedBelowLevel) {
														tileQueue.addTail(var32);
													}
												}
											}
											break;
										}
										if (tile.sceneryDrawFlags != 0) {
											var24 = true;
											for (var10 = 0; var10 < tile.sceneryLen; var10++) {
												if (tile.scenery[var10].drawCycle != drawCycle && (tile.interiorFlags[var10] & tile.sceneryDrawFlags) == tile.sceneryDrawnDirFlags) {
													var24 = false;
													break;
												}
											}
											if (var24) {
												var22 = tile.wall;
												if (!isWallVisible(level, tileX, tileY, var22.primaryFlags)) {
													if (GlRenderer.enabled) {
														wallLighting:
														{
															if ((var22.key & 0xFC000L) == 16384L) {
																i = var22.xFine - cameraX;
																bestIndex = var22.yFine - cameraY;
																k = (int) (var22.key >> 20 & 0x3L);
																if (k == 0) {
																	i -= 64;
																	bestIndex += 64;
																	if (bestIndex < i && tileX > 0 && tileY < length - 1) {
																		LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX - 1, tileY + 1);
																		break wallLighting;
																	}
																} else if (k == 1) {
																	i += 64;
																	bestIndex += 64;
																	if (bestIndex < -i && tileX < width - 1 && tileY < length - 1) {
																		LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX + 1, tileY + 1);
																		break wallLighting;
																	}
																} else if (k == 2) {
																	i += 64;
																	bestIndex -= 64;
																	if (bestIndex > i && tileX < width - 1 && tileY > 0) {
																		LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX + 1, tileY - 1);
																		break wallLighting;
																	}
																} else if (k == 3) {
																	i -= 64;
																	bestIndex -= 64;
																	if (bestIndex > -i && tileX > 0 && tileY > 0) {
																		LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX - 1, tileY - 1);
																		break wallLighting;
																	}
																}
															}
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
														}
													}
													var22.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, var22.xFine - cameraX, var22.zFine - cameraZ, var22.yFine - cameraY, var22.key, drawLevel, null);
												}
												tile.sceneryDrawFlags = 0;
											}
										}
										if (!tile.hasUpdated) {
											break;
										}
										try {
											var9 = tile.sceneryLen;
											tile.hasUpdated = false;
											var10 = 0;
											sceneryLoop:
											for (i = 0; i < var9; i++) {
												var25 = tile.scenery[i];
												if (var25.drawCycle != drawCycle) {
													for (k = var25.xMin; k <= var25.xMax; k++) {
														for (flags = var25.yMin; flags <= var25.yMax; flags++) {
															var32 = levelTiles[k][flags];
															if (var32.visible) {
																tile.hasUpdated = true;
																continue sceneryLoop;
															}
															if (var32.sceneryDrawFlags != 0) {
																distMax = 0;
																if (k > var25.xMin) {
																	distMax++;
																}
																if (k < var25.xMax) {
																	distMax += 4;
																}
																if (flags > var25.yMin) {
																	distMax += 8;
																}
																if (flags < var25.yMax) {
																	distMax += 2;
																}
																if ((distMax & var32.sceneryDrawFlags) == tile.scenerySkipDirFlags) {
																	tile.hasUpdated = true;
																	continue sceneryLoop;
																}
															}
														}
													}
													sceneryBuffer[var10++] = var25;
													k = cameraTileX - var25.xMin;
													flags = var25.xMax - cameraTileX;
													if (flags > k) {
														k = flags;
													}
													distY = cameraTileY - var25.yMin;
													distMax = var25.yMax - cameraTileY;
													if (distMax > distY) {
														var25.drawPriority = k + distMax;
													} else {
														var25.drawPriority = k + distY;
													}
												}
											}
											while (var10 > 0) {
												i = -50;
												bestIndex = -1;
												for (k = 0; k < var10; k++) {
													@Pc(1628) Scenery candidate = sceneryBuffer[k];
													if (candidate.drawCycle != drawCycle) {
														if (candidate.drawPriority > i) {
															i = candidate.drawPriority;
															bestIndex = k;
														} else if (candidate.drawPriority == i) {
															distY = candidate.xFine - cameraX;
															distMax = candidate.yFine - cameraY;
															var17 = sceneryBuffer[bestIndex].xFine - cameraX;
															var18 = sceneryBuffer[bestIndex].yFine - cameraY;
															if (distY * distY + distMax * distMax > var17 * var17 + var18 * var18) {
																bestIndex = k;
															}
														}
													}
												}
												if (bestIndex == -1) {
													break;
												}
												@Pc(1697) Scenery selected = sceneryBuffer[bestIndex];
												selected.drawCycle = drawCycle;
												if (!isAreaVisible(level, selected.xMin, selected.xMax, selected.yMin, selected.yMax, selected.entity.getMinY())) {
													if (GlRenderer.enabled) {
														if ((selected.key & 0xFC000L) == 147456L) {
															LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
															flags = selected.xFine - cameraX;
															distY = selected.yFine - cameraY;
															distMax = (int) (selected.key >> 20 & 0x3L);
															if (distMax == 1 || distMax == 3) {
																if (distY > -flags) {
																	LightingManager.pruneInactiveLights(drawLevel, tileX, tileY - 1, tileX - 1, tileY);
																} else {
																	LightingManager.pruneInactiveLights(drawLevel, tileX, tileY + 1, tileX + 1, tileY);
																}
															} else if (distY > flags) {
																LightingManager.pruneInactiveLights(drawLevel, tileX, tileY - 1, tileX + 1, tileY);
															} else {
																LightingManager.pruneInactiveLights(drawLevel, tileX, tileY + 1, tileX - 1, tileY);
															}
														} else {
															LightingManager.updateLightsForRegion(cameraX, cameraZ, cameraY, drawLevel, selected.xMin, selected.yMin, selected.xMax, selected.yMax);
														}
													}
													selected.entity.render(selected.orientation, sinPitch, cosPitch, sinYaw, cosYaw, selected.xFine - cameraX, selected.zFine - cameraZ, selected.yFine - cameraY, selected.key, drawLevel, null);
												}
												for (flags = selected.xMin; flags <= selected.xMax; flags++) {
													for (distY = selected.yMin; distY <= selected.yMax; distY++) {
														@Pc(1863) Tile neighborTile = levelTiles[flags][distY];
														if (neighborTile.sceneryDrawFlags != 0) {
															tileQueue.addTail(neighborTile);
														} else if ((flags != tileX || distY != tileY) && neighborTile.checkedBelowLevel) {
															tileQueue.addTail(neighborTile);
														}
													}
												}
											}
											if (!tile.hasUpdated) {
												break;
											}
										} catch (@Pc(1895) Exception ex) {
											tile.hasUpdated = false;
											break;
										}
									}
								} while (!tile.checkedBelowLevel);
							} while (tile.sceneryDrawFlags != 0);
							if (tileX > cameraTileX || tileX <= LightingManager.visibleMinX) {
								break;
							}
							adjacentTile = levelTiles[tileX - 1][tileY];
						} while (adjacentTile != null && adjacentTile.checkedBelowLevel);
						if (tileX < cameraTileX || tileX >= LightingManager.visibleMaxX - 1) {
							break;
						}
						adjacentTile = levelTiles[tileX + 1][tileY];
					} while (adjacentTile != null && adjacentTile.checkedBelowLevel);
					if (tileY > cameraTileY || tileY <= LightingManager.visibleMinY) {
						break;
					}
					adjacentTile = levelTiles[tileX][tileY - 1];
				} while (adjacentTile != null && adjacentTile.checkedBelowLevel);
				if (tileY < cameraTileY || tileY >= LightingManager.visibleMaxY - 1) {
					break;
				}
				adjacentTile = levelTiles[tileX][tileY + 1];
			} while (adjacentTile != null && adjacentTile.checkedBelowLevel);
			tile.checkedBelowLevel = false;
			visibleTileCount--;
			@Pc(1999) ObjStackEntity objStack2 = tile.objStack;
			if (objStack2 != null && objStack2.heightOffset != 0) {
				if (GlRenderer.enabled) {
					LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
				}
				if (objStack2.secondary != null) {
					objStack2.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack2.xFine - cameraX, objStack2.zFine - cameraZ - objStack2.heightOffset, objStack2.yFine - cameraY, objStack2.key, drawLevel, null);
				}
				if (objStack2.tertiary != null) {
					objStack2.tertiary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack2.xFine - cameraX, objStack2.zFine - cameraZ - objStack2.heightOffset, objStack2.yFine - cameraY, objStack2.key, drawLevel, null);
				}
				if (objStack2.primary != null) {
					objStack2.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, objStack2.xFine - cameraX, objStack2.zFine - cameraZ - objStack2.heightOffset, objStack2.yFine - cameraY, objStack2.key, drawLevel, null);
				}
			}
			if (tile.wallDrawFlags != 0) {
				@Pc(2109) WallDecor wallDecor2 = tile.wallDecor;
				if (wallDecor2 != null && !isTileVisible(level, tileX, tileY, wallDecor2.primary.getMinY())) {
					if ((wallDecor2.flags & tile.wallDrawFlags) != 0) {
						if (GlRenderer.enabled) {
							LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
						}
						wallDecor2.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wallDecor2.xFine + wallDecor2.xOffset - cameraX, wallDecor2.orientation - cameraZ, wallDecor2.yFine + wallDecor2.yOffset - cameraY, wallDecor2.key, drawLevel, null);
					} else if (wallDecor2.flags == 256) {
						i = wallDecor2.xFine - cameraX;
						bestIndex = wallDecor2.orientation - cameraZ;
						k = wallDecor2.yFine - cameraY;
						flags = wallDecor2.zFine;
						if (flags == 1 || flags == 2) {
							distY = -i;
						} else {
							distY = i;
						}
						if (flags == 2 || flags == 3) {
							distMax = -k;
						} else {
							distMax = k;
						}
						if (distMax >= distY) {
							if (GlRenderer.enabled) {
								LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
							}
							wallDecor2.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, i + wallDecor2.xOffset, bestIndex, k + wallDecor2.yOffset, wallDecor2.key, drawLevel, null);
						} else if (wallDecor2.secondary != null) {
							if (GlRenderer.enabled) {
								LightingManager.updateLightsForTile(cameraX, cameraZ, cameraY, drawLevel, tileX, tileY);
							}
							wallDecor2.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, i, bestIndex, k, wallDecor2.key, drawLevel, null);
						}
					}
				}
				@Pc(2275) Wall wall2 = tile.wall;
				if (wall2 != null) {
					if ((wall2.secondaryFlags & tile.wallDrawFlags) != 0 && !isWallVisible(level, tileX, tileY, wall2.secondaryFlags)) {
						if (GlRenderer.enabled) {
							LightingManager.updateLightsForWallPiece(wall2.secondaryFlags, cameraX, cameraZ, cameraY, level, tileX, tileY);
						}
						wall2.secondary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wall2.xFine - cameraX, wall2.zFine - cameraZ, wall2.yFine - cameraY, wall2.key, drawLevel, null);
					}
					if ((wall2.primaryFlags & tile.wallDrawFlags) != 0 && !isWallVisible(level, tileX, tileY, wall2.primaryFlags)) {
						if (GlRenderer.enabled) {
							LightingManager.updateLightsForWallPiece(wall2.primaryFlags, cameraX, cameraZ, cameraY, level, tileX, tileY);
						}
						wall2.primary.render(0, sinPitch, cosPitch, sinYaw, cosYaw, wall2.xFine - cameraX, wall2.zFine - cameraZ, wall2.yFine - cameraY, wall2.key, drawLevel, null);
					}
				}
			}
			@Pc(2388) Tile nextTile;
			if (drawLevel < levels - 1) {
				nextTile = tiles[drawLevel + 1][tileX][tileY];
				if (nextTile != null && nextTile.checkedBelowLevel) {
					tileQueue.addTail(nextTile);
				}
			}
			if (tileX < cameraTileX) {
				nextTile = levelTiles[tileX + 1][tileY];
				if (nextTile != null && nextTile.checkedBelowLevel) {
					tileQueue.addTail(nextTile);
				}
			}
			if (tileY < cameraTileY) {
				nextTile = levelTiles[tileX][tileY + 1];
				if (nextTile != null && nextTile.checkedBelowLevel) {
					tileQueue.addTail(nextTile);
				}
			}
			if (tileX > cameraTileX) {
				nextTile = levelTiles[tileX - 1][tileY];
				if (nextTile != null && nextTile.checkedBelowLevel) {
					tileQueue.addTail(nextTile);
				}
			}
			if (tileY > cameraTileY) {
				nextTile = levelTiles[tileX][tileY - 1];
				if (nextTile != null && nextTile.checkedBelowLevel) {
					tileQueue.addTail(nextTile);
				}
			}
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(IIII)Z")
	public static boolean isTileVisible(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int height) {
		if (isTileOccluded(level, x, y)) {
			@Pc(10) int xFine = x << 7;
			@Pc(14) int yFine = y << 7;
			return isOccluded(xFine + 1, tileHeights[level][x][y] + height, yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][x + 1][y] + height, yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][x + 1][y + 1] + height, yFine + 128 - 1) && isOccluded(xFine + 1, tileHeights[level][x][y + 1] + height, yFine + 128 - 1);
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!vd", name = "b", descriptor = "(IIII)Z")
	public static boolean isWallVisible(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int wallFlags) {
		if (!isTileOccluded(level, x, y)) {
			return false;
		}
		@Pc(10) int xFine = x << 7;
		@Pc(14) int yFine = y << 7;
		@Pc(24) int tileHeight = tileHeights[level][x][y] - 1;
		@Pc(28) int midHeight = tileHeight - 120;
		@Pc(32) int topHeight = tileHeight - 230;
		@Pc(36) int peakHeight = tileHeight - 238;
		if (wallFlags < 16) {
			if (wallFlags == 1) {
				if (xFine > cameraX) {
					if (!isOccluded(xFine, tileHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine, tileHeight, yFine + 128)) {
						return false;
					}
				}
				if (level > 0) {
					if (!isOccluded(xFine, midHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine, midHeight, yFine + 128)) {
						return false;
					}
				}
				if (!isOccluded(xFine, topHeight, yFine)) {
					return false;
				}
				return isOccluded(xFine, topHeight, yFine + 128);
			}
			if (wallFlags == 2) {
				if (yFine < cameraY) {
					if (!isOccluded(xFine, tileHeight, yFine + 128)) {
						return false;
					}
					if (!isOccluded(xFine + 128, tileHeight, yFine + 128)) {
						return false;
					}
				}
				if (level > 0) {
					if (!isOccluded(xFine, midHeight, yFine + 128)) {
						return false;
					}
					if (!isOccluded(xFine + 128, midHeight, yFine + 128)) {
						return false;
					}
				}
				if (!isOccluded(xFine, topHeight, yFine + 128)) {
					return false;
				}
				return isOccluded(xFine + 128, topHeight, yFine + 128);
			}
			if (wallFlags == 4) {
				if (xFine < cameraX) {
					if (!isOccluded(xFine + 128, tileHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine + 128, tileHeight, yFine + 128)) {
						return false;
					}
				}
				if (level > 0) {
					if (!isOccluded(xFine + 128, midHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine + 128, midHeight, yFine + 128)) {
						return false;
					}
				}
				if (!isOccluded(xFine + 128, topHeight, yFine)) {
					return false;
				}
				return isOccluded(xFine + 128, topHeight, yFine + 128);
			}
			if (wallFlags == 8) {
				if (yFine > cameraY) {
					if (!isOccluded(xFine, tileHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine + 128, tileHeight, yFine)) {
						return false;
					}
				}
				if (level > 0) {
					if (!isOccluded(xFine, midHeight, yFine)) {
						return false;
					}
					if (!isOccluded(xFine + 128, midHeight, yFine)) {
						return false;
					}
				}
				if (!isOccluded(xFine, topHeight, yFine)) {
					return false;
				}
				return isOccluded(xFine + 128, topHeight, yFine);
			}
		}
		if (!isOccluded(xFine + 64, peakHeight, yFine + 64)) {
			return false;
		} else if (wallFlags == 16) {
			return isOccluded(xFine, topHeight, yFine + 128);
		} else if (wallFlags == 32) {
			return isOccluded(xFine + 128, topHeight, yFine + 128);
		} else if (wallFlags == 64) {
			return isOccluded(xFine + 128, topHeight, yFine);
		} else if (wallFlags == 128) {
			return isOccluded(xFine, topHeight, yFine);
		} else {
			return true;
		}
	}

	@OriginalMember(owner = "client!p", name = "a", descriptor = "(IZIZLclient!mj;IIIBII)V")
	public static void addLoc(@OriginalArg(0) int currentPlane, @OriginalArg(1) boolean lowmem, @OriginalArg(2) int plane, @OriginalArg(3) boolean highmem, @OriginalArg(4) CollisionMap map, @OriginalArg(5) int locIndex, @OriginalArg(6) int locType, @OriginalArg(7) int x, @OriginalArg(9) int y, @OriginalArg(10) int orientation) {
		if (lowmem && !allLevelsAreVisible() && (renderFlags[0][x][y] & 0x2) == 0) {
			if ((renderFlags[plane][x][y] & 0x10) != 0 || getRenderLevel(y, x, plane) != centralPlane) {
				return;
			}
		}

		if (plane < firstVisibleLevel) {
			firstVisibleLevel = plane;
		}

		@Pc(62) LocType loc = LocTypeList.get(locIndex);
		if (GlRenderer.enabled && loc.render) {
			return;
		}

		@Pc(84) int width;
		@Pc(81) int length;
		if (orientation == 1 || orientation == 3) {
			length = loc.width;
			width = loc.length;
		} else {
			width = loc.width;
			length = loc.length;
		}

		@Pc(103) int west;
		@Pc(112) int east;
		if (x + width <= 104) {
			west = x + (width >> 1);
			east = x + (width + 1 >> 1);
		} else {
			east = x + 1;
			west = x;
		}

		@Pc(129) int south;
		@Pc(133) int north;
		if (length + y > 104) {
			south = y;
			north = y + 1;
		} else {
			south = (length >> 1) + y;
			north = y + (length + 1 >> 1);
		}

		@Pc(153) int[][] currentHeightmap = tileHeights[currentPlane];
		@Pc(165) int xFine = (width << 6) + (x << 7);
		@Pc(173) int yFine = (length << 6) + (y << 7);
		@Pc(199) int averageY = currentHeightmap[west][north] + currentHeightmap[east][south] + currentHeightmap[west][south] + currentHeightmap[east][north] >> 2;

		@Pc(201) int diffAverageY = 0;
		@Pc(213) int[][] heightmap;
		if (GlRenderer.enabled && currentPlane != 0) {
			heightmap = tileHeights[0];
			diffAverageY = averageY - (heightmap[east][north] + heightmap[east][south] + heightmap[west][south] + heightmap[west][north] >> 2);
		}
		heightmap = null;

		@Pc(261) long bitset = x | 0x40000000 | (long) y << 7 | (long) locType << 14 | (long) orientation << 20;

		if (highmem) {
			heightmap = surfaceTileHeights[0];
		} else if (currentPlane < 3) {
			heightmap = tileHeights[currentPlane + 1];
		}

		if (loc.interactable == 0 || highmem) {
			bitset |= Long.MIN_VALUE;
		}

		if (loc.supportitems == 1) {
			bitset |= 0x400000L;
		}

		if (loc.hasanimation) {
			bitset |= Integer.MIN_VALUE;
		}

		if (loc.hasAreaSound()) {
			AreaSoundManager.add(y, loc, orientation, null, x, plane, null);
		}

		@Pc(330) boolean castsShadow = loc.castshadow & !highmem;
		bitset |= (long) locIndex << 32;
		@Pc(387) Entity entity;
		@Pc(403) LocEntity locEntity;
		@Pc(1226) int walloff;
		@Pc(1889) long wallKey;
		@Pc(1934) Entity decorEntity;
		@Pc(1950) LocEntity decorLocEntity;

		if (locType == LocType.GROUNDDECOR) {
			if (Preferences.showGroundDecorations || loc.interactable != 0 || loc.blockwalk == 1 || loc.forcedecor) {
				if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
					locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, LocType.GROUNDDECOR, averageY, heightmap, lowmem, null, castsShadow, yFine);
					if (GlRenderer.enabled && castsShadow) {
						ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
					}
					entity = locEntity.model;
				} else {
					entity = new Loc(locIndex, LocType.GROUNDDECOR, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
				}

				setGroundDecor(plane, x, y, averageY, entity, bitset, loc.clipped);

				if (loc.blockwalk == 1 && map != null) {
					map.flagGroundDecor(x, y);
				}
			}
		} else if (locType == LocType.CENTREPIECE_STRAIGHT || locType == LocType.CENTREPIECE_DIAGONAL) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(locType == LocType.CENTREPIECE_DIAGONAL ? orientation + 4 : orientation, xFine, currentHeightmap, LocType.CENTREPIECE_STRAIGHT, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, LocType.CENTREPIECE_STRAIGHT, locType == LocType.CENTREPIECE_DIAGONAL ? orientation + 4 : orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}

			if (entity != null) {
				@Pc(531) boolean added = addEntity(plane, x, y, averageY, width, length, entity, bitset);
				if (loc.active && added && lowmem) {
					@Pc(541) int shadowSize = 15;
					if (entity instanceof Model) {
						shadowSize = ((Model) entity).getLengthXZ() / 4;
						if (shadowSize > 30) {
							shadowSize = 30;
						}
					}
					for (@Pc(560) int sx = 0; sx <= width; sx++) {
						for (@Pc(565) int sy = 0; sy <= length; sy++) {
							if (shadowmap[plane][x + sx][sy + y] < shadowSize) {
								shadowmap[plane][x + sx][y + sy] = (byte) shadowSize;
							}
						}
					}
				}
			}

			if (loc.blockwalk != 0 && map != null) {
				map.flagScenery(x, loc.blockrange, y, width, length);
			}
		} else if (locType >= LocType.ROOF_STRAIGHT) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, locType, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, locType, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}

			addEntity(plane, x, y, averageY, 1, 1, entity, bitset);
			if (lowmem && locType <= LocType.ROOF_FLAT && locType != LocType.ROOF_DIAGONAL_WITH_ROOFEDGE && plane > 0) {
				occludeFlags[plane][x][y] |= 0x4;
			}

			if (loc.blockwalk != 0 && map != null) {
				map.flagScenery(x, loc.blockrange, y, width, length);
			}
		} else if (locType == LocType.WALL_STRAIGHT) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, LocType.WALL_STRAIGHT, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, LocType.WALL_STRAIGHT, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}

			setWall(plane, x, y, averageY, entity, null, WALL_ROTATION_TYPE1[orientation], 0, bitset);

			if (lowmem) {
				if (orientation == 0) {
					if (loc.active) {
						shadowmap[plane][x][y] = 50;
						shadowmap[plane][x][y + 1] = 50;
					}

					if (loc.occlude) {
						occludeFlags[plane][x][y] |= 0x1;
					}
				} else if (orientation == 1) {
					if (loc.active) {
						shadowmap[plane][x][y + 1] = 50;
						shadowmap[plane][x + 1][y + 1] = 50;
					}

					if (loc.occlude) {
						occludeFlags[plane][x][y + 1] |= 0x2;
					}
				} else if (orientation == 2) {
					if (loc.active) {
						shadowmap[plane][x + 1][y] = 50;
						shadowmap[plane][x + 1][y + 1] = 50;
					}

					if (loc.occlude) {
						occludeFlags[plane][x + 1][y] |= 0x1;
					}
				} else if (orientation == 3) {
					if (loc.active) {
						shadowmap[plane][x][y] = 50;
						shadowmap[plane][x + 1][y] = 50;
					}

					if (loc.occlude) {
						occludeFlags[plane][x][y] |= 0x2;
					}
				}
			}

			if (loc.blockwalk != 0 && map != null) {
				map.flagWall(orientation, locType, loc.blockrange, y, x);
			}

			if (loc.walloff != 16) {
				setWallDecoration(plane, x, y, loc.walloff);
			}
		} else if (locType == LocType.WALL_DIAGONALCORNER) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, LocType.WALL_DIAGONALCORNER, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, LocType.WALL_DIAGONALCORNER, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}

			setWall(plane, x, y, averageY, entity, null, LoginManager.WALL_ROTATION_FLAGS[orientation], 0, bitset);
			if (loc.active && lowmem) {
				if (orientation == 0) {
					shadowmap[plane][x][y + 1] = 50;
				} else if (orientation == 1) {
					shadowmap[plane][x + 1][y + 1] = 50;
				} else if (orientation == 2) {
					shadowmap[plane][x + 1][y] = 50;
				} else if (orientation == 3) {
					shadowmap[plane][x][y] = 50;
				}
			}

			if (loc.blockwalk != 0 && map != null) {
				map.flagWall(orientation, locType, loc.blockrange, y, x);
			}
		} else if (locType == LocType.WALL_L) {
			walloff = orientation + 1 & 0x3;
			@Pc(1269) Entity secondaryWallEntity;
			@Pc(1254) Entity primaryWallEntity;
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				@Pc(1287) LocEntity wallLocEntity = loc.getStaticEntity(orientation + 4, xFine, currentHeightmap, LocType.WALL_L, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(wallLocEntity.sprite, xFine, diffAverageY, yFine);
				}
				primaryWallEntity = wallLocEntity.model;
				wallLocEntity = loc.getStaticEntity(walloff, xFine, currentHeightmap, LocType.WALL_L, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(wallLocEntity.sprite, xFine, diffAverageY, yFine);
				}
				secondaryWallEntity = wallLocEntity.model;
			} else {
				primaryWallEntity = new Loc(locIndex, LocType.WALL_L, orientation + 4, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
				secondaryWallEntity = new Loc(locIndex, LocType.WALL_L, walloff, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWall(plane, x, y, averageY, primaryWallEntity, secondaryWallEntity, WALL_ROTATION_TYPE1[orientation], WALL_ROTATION_TYPE1[walloff], bitset);
			if (loc.occlude && lowmem) {
				if (orientation == 0) {
					occludeFlags[plane][x][y] |= 0x1;
					occludeFlags[plane][x][y + 1] |= 0x2;
				} else if (orientation == 1) {
					occludeFlags[plane][x][y + 1] |= 0x2;
					occludeFlags[plane][x + 1][y] |= 0x1;
				} else if (orientation == 2) {
					occludeFlags[plane][x + 1][y] |= 0x1;
					occludeFlags[plane][x][y] |= 0x2;
				} else if (orientation == 3) {
					occludeFlags[plane][x][y] |= 0x2;
					occludeFlags[plane][x][y] |= 0x1;
				}
			}
			if (loc.blockwalk != 0 && map != null) {
				map.flagWall(orientation, locType, loc.blockrange, y, x);
			}
			if (loc.walloff != 16) {
				setWallDecoration(plane, x, y, loc.walloff);
			}
		} else if (locType == LocType.WALL_SQUARECORNER) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, LocType.WALL_SQUARECORNER, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, LocType.WALL_SQUARECORNER, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWall(plane, x, y, averageY, entity, null, LoginManager.WALL_ROTATION_FLAGS[orientation], 0, bitset);
			if (loc.active && lowmem) {
				if (orientation == 0) {
					shadowmap[plane][x][y + 1] = 50;
				} else if (orientation == 1) {
					shadowmap[plane][x + 1][y + 1] = 50;
				} else if (orientation == 2) {
					shadowmap[plane][x + 1][y] = 50;
				} else if (orientation == 3) {
					shadowmap[plane][x][y] = 50;
				}
			}
			if (loc.blockwalk != 0 && map != null) {
				map.flagWall(orientation, locType, loc.blockrange, y, x);
			}
		} else if (locType == LocType.WALL_DIAGONAL) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, locType, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, locType, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			addEntity(plane, x, y, averageY, 1, 1, entity, bitset);
			if (loc.blockwalk != 0 && map != null) {
				map.flagScenery(x, loc.blockrange, y, width, length);
			}
			if (loc.walloff != 16) {
				setWallDecoration(plane, x, y, loc.walloff);
			}
		} else if (locType == LocType.WALLDECOR_STRAIGHT_XOFFSET) {
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				locEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(locEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = locEntity.model;
			} else {
				entity = new Loc(locIndex, 4, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWallDecor(plane, x, y, averageY, entity, null, WALL_ROTATION_TYPE1[orientation], 0, 0, 0, bitset);
		} else if (locType == LocType.WALLDECOR_STRAIGHT_YOFFSET) {
			walloff = 16;
			wallKey = getWallKey(plane, x, y);
			if (wallKey != 0L) {
				walloff = LocTypeList.get(Integer.MAX_VALUE & (int) (wallKey >>> 32)).walloff;
			}
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				decorLocEntity = loc.getStaticEntity(orientation, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(decorLocEntity.sprite, xFine - WALL_DECO_ROT_SIZE_X_DIR[orientation] * 8, diffAverageY, yFine - WALL_DECO_ROT_SIZE_Y_DIR[orientation] * 8);
				}
				decorEntity = decorLocEntity.model;
			} else {
				decorEntity = new Loc(locIndex, 4, orientation, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWallDecor(plane, x, y, averageY, decorEntity, null, WALL_ROTATION_TYPE1[orientation], 0, walloff * WALL_DECO_ROT_SIZE_X_DIR[orientation], WALL_DECO_ROT_SIZE_Y_DIR[orientation] * walloff, bitset);
		} else if (locType == LocType.WALLDECOR_DIAGONAL_XOFFSET) {
			walloff = 8;
			wallKey = getWallKey(plane, x, y);
			if (wallKey != 0L) {
				walloff = LocTypeList.get(Integer.MAX_VALUE & (int) (wallKey >>> 32)).walloff / 2;
			}
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				decorLocEntity = loc.getStaticEntity(orientation + 4, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(decorLocEntity.sprite, xFine - WALL_OFFSET_X[orientation] * 8, diffAverageY, yFine - WALL_OFFSET_Y[orientation] * 8);
				}
				decorEntity = decorLocEntity.model;
			} else {
				decorEntity = new Loc(locIndex, 4, orientation + 4, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWallDecor(plane, x, y, averageY, decorEntity, null, 256, orientation, walloff * WALL_OFFSET_X[orientation], walloff * WALL_OFFSET_Y[orientation], bitset);
		} else if (locType == LocType.WALLDECOR_DIAGONAL_YOFFSET) {
			@Pc(2137) int oppositeOrientation = orientation + 2 & 0x3;
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				@Pc(2183) LocEntity oppositeLocEntity = loc.getStaticEntity(oppositeOrientation + 4, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(oppositeLocEntity.sprite, xFine, diffAverageY, yFine);
				}
				entity = oppositeLocEntity.model;
			} else {
				entity = new Loc(locIndex, 4, oppositeOrientation + 4, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWallDecor(plane, x, y, averageY, entity, null, 256, oppositeOrientation, 0, 0, bitset);
		} else if (locType == LocType.WALLDECOR_DIAGONAL_BOTH) {
			walloff = 8;
			wallKey = getWallKey(plane, x, y);
			if (wallKey != 0L) {
				walloff = LocTypeList.get(Integer.MAX_VALUE & (int) (wallKey >>> 32)).walloff / 2;
			}
			@Pc(2244) int oppositeOrientation2 = orientation + 2 & 0x3;
			@Pc(2289) Entity secondaryDecorEntity;
			if (loc.anim == -1 && loc.multiLocs == null && !loc.dynamic) {
				@Pc(2297) int yOff = WALL_OFFSET_Y[orientation] * 8;
				@Pc(2303) int xOff = WALL_OFFSET_X[orientation] * 8;
				@Pc(2319) LocEntity bothLocEntity = loc.getStaticEntity(orientation + 4, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(bothLocEntity.sprite, xFine - xOff, diffAverageY, yFine - yOff);
				}
				decorEntity = bothLocEntity.model;
				bothLocEntity = loc.getStaticEntity(oppositeOrientation2 + 4, xFine, currentHeightmap, 4, averageY, heightmap, lowmem, null, castsShadow, yFine);
				if (GlRenderer.enabled && castsShadow) {
					ShadowManager.addObjectShadow(bothLocEntity.sprite, xFine - xOff, diffAverageY, yFine - yOff);
				}
				secondaryDecorEntity = bothLocEntity.model;
			} else {
				decorEntity = new Loc(locIndex, 4, orientation + 4, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
				secondaryDecorEntity = new Loc(locIndex, 4, oppositeOrientation2 + 4, currentPlane, x, y, loc.anim, loc.allowrandomizedanimation, null);
			}
			setWallDecor(plane, x, y, averageY, decorEntity, secondaryDecorEntity, 256, orientation, walloff * WALL_OFFSET_X[orientation], WALL_OFFSET_Y[orientation] * walloff, bitset);
		}
	}

	@OriginalMember(owner = "client!ch", name = "c", descriptor = "(I)V")
	public static void checkPlaneChange() {
		if (!allLevelsAreVisible() && centralPlane != Player.plane) {
			LoginManager.loadRegion(Player.plane, centralZoneY, centralZoneX, PlayerList.self.movementQueueY[0], false, PlayerList.self.movementQueueX[0]);
		} else if (Player.plane != LightingManager.minimapRenderedPlane && MiniMap.renderMap(Player.plane)) {
			LightingManager.minimapRenderedPlane = Player.plane;
			ScriptRunner.updateRoofRemovalMode();
		}
	}

	@OriginalMember(owner = "client!jk", name = "a", descriptor = "(IZ[BII[Lclient!mj;)V")
	public static void readLocs(@OriginalArg(0) int baseX, @OriginalArg(1) boolean highmem, @OriginalArg(2) byte[] src, @OriginalArg(3) int baseY, @OriginalArg(5) CollisionMap[] maps) {
		@Pc(10) Buffer b = new Buffer(src);

		@Pc(12) int locIndex = -1;
		while (true) {
			@Pc(16) int locOffset = b.gVarSmart();
			if (locOffset == 0) {
				return;
			}
			locIndex += locOffset;

			@Pc(27) int position = 0;
			while (true) {
				@Pc(31) int posOffset = b.gsmarts();
				if (posOffset == 0) {
					break;
				}
				position += posOffset - 1;

				@Pc(46) int y = position & 0x3F;
				@Pc(56) int x = position >> 6 & 0x3F;
				@Pc(50) int plane = position >> 12;

				@Pc(60) int flags = b.g1();
				@Pc(64) int type = flags >> 2;
				@Pc(68) int orientation = flags & 0x3;

				@Pc(72) int tileX = x + baseX;
				@Pc(76) int tileY = y + baseY;
				if (tileX > 0 && tileY > 0 && tileX < 103 && tileY < 103) {
					@Pc(90) CollisionMap map = null;
					if (!highmem) {
						@Pc(95) int markingPlane = plane;
						// might be used with bridges?
						if ((renderFlags[1][tileX][tileY] & 0x2) == 2) {
							markingPlane--;
						}
						if (markingPlane >= 0) {
							map = maps[markingPlane];
						}
					}

					addLoc(plane, !highmem, plane, highmem, map, locIndex, type, tileX, tileY, orientation);
				}
			}
		}
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(Lclient!th;III)V")
	public static void mergeGroundDecorNormals(@OriginalArg(0) Entity entity, @OriginalArg(1) int level, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		@Pc(12) Tile neighbor;
		if (x < width) {
			neighbor = tiles[level][x + 1][y];
			if (neighbor != null && neighbor.groundDecor != null && neighbor.groundDecor.entity.canMerge()) {
				entity.mergeNormals(neighbor.groundDecor.entity, 128, 0, 0, true);
			}
		}
		if (y < width) {
			neighbor = tiles[level][x][y + 1];
			if (neighbor != null && neighbor.groundDecor != null && neighbor.groundDecor.entity.canMerge()) {
				entity.mergeNormals(neighbor.groundDecor.entity, 0, 0, 128, true);
			}
		}
		if (x < width && y < length) {
			neighbor = tiles[level][x + 1][y + 1];
			if (neighbor != null && neighbor.groundDecor != null && neighbor.groundDecor.entity.canMerge()) {
				entity.mergeNormals(neighbor.groundDecor.entity, 128, 0, 128, true);
			}
		}
		if (x < width && y > 0) {
			neighbor = tiles[level][x + 1][y - 1];
			if (neighbor != null && neighbor.groundDecor != null && neighbor.groundDecor.entity.canMerge()) {
				entity.mergeNormals(neighbor.groundDecor.entity, 128, 0, -128, true);
			}
		}
	}

	@OriginalMember(owner = "client!mf", name = "a", descriptor = "(IIIII[[[B[I[I[I[I[IIBII)V")
	public static void setPlainTile(@OriginalArg(0) int camX, @OriginalArg(1) int camZ, @OriginalArg(2) int camY, @OriginalArg(3) int pitch, @OriginalArg(4) int yaw, @OriginalArg(5) byte[][][] occlusionData, @OriginalArg(6) int[] roofMaxHeight, @OriginalArg(7) int[] roofMinX, @OriginalArg(8) int[] roofMaxX, @OriginalArg(9) int[] roofMaxZ, @OriginalArg(10) int[] roofMinZ, @OriginalArg(11) int occlusionLevel, @OriginalArg(12) byte occlusionFlag, @OriginalArg(13) int entityMinX, @OriginalArg(14) int entityMinY) {
		if (camX < 0) {
			camX = 0;
		} else if (camX >= width * 128) {
			camX = width * 128 - 1;
		}
		if (camY < 0) {
			camY = 0;
		} else if (camY >= length * 128) {
			camY = length * 128 - 1;
		}
		sinPitch = MathUtils.sin[pitch];
		cosPitch = MathUtils.cos[pitch];
		sinYaw = MathUtils.sin[yaw];
		cosYaw = MathUtils.cos[yaw];
		cameraX = camX;
		cameraZ = camZ;
		cameraY = camY;
		cameraTileX = camX / 128;
		cameraTileY = camY / 128;
		LightingManager.visibleMinX = cameraTileX - visibility;
		if (LightingManager.visibleMinX < 0) {
			LightingManager.visibleMinX = 0;
		}
		LightingManager.visibleMinY = cameraTileY - visibility;
		if (LightingManager.visibleMinY < 0) {
			LightingManager.visibleMinY = 0;
		}
		LightingManager.visibleMaxX = cameraTileX + visibility;
		if (LightingManager.visibleMaxX > width) {
			LightingManager.visibleMaxX = width;
		}
		LightingManager.visibleMaxY = cameraTileY + visibility;
		if (LightingManager.visibleMaxY > length) {
			LightingManager.visibleMaxY = length;
		}
		@Pc(99) short viewDistance;
		if (GlRenderer.enabled) {
			viewDistance = (short) GlobalConfig.VIEW_DISTANCE;
		} else {
			viewDistance = 3500;
		}
		@Pc(104) int vx;
		@Pc(113) int vy;
		for (vx = 0; vx < visibility + visibility + 2; vx++) {
			for (vy = 0; vy < visibility + visibility + 2; vy++) {
				@Pc(130) int dx = (vx - visibility << 7) - (cameraX & 0x7F);
				@Pc(140) int dy = (vy - visibility << 7) - (cameraY & 0x7F);
				@Pc(146) int tx = cameraTileX + vx - visibility;
				@Pc(152) int ty = cameraTileY + vy - visibility;
				if (tx >= 0 && ty >= 0 && tx < width && ty < length) {
					@Pc(176) int highZ;
					if (underwaterTileHeights == null) {
						highZ = surfaceTileHeights[0][tx][ty] + 128 - cameraZ;
					} else {
						highZ = underwaterTileHeights[0][tx][ty] + 128 - cameraZ;
					}
					@Pc(201) int lowZ = surfaceTileHeights[3][tx][ty] - cameraZ - 1000;
					visibilityPoints[vx][vy] = projectToScreen(dx, lowZ, highZ, dy, viewDistance);
				} else {
					visibilityPoints[vx][vy] = false;
				}
			}
		}
		for (vx = 0; vx < visibility + visibility + 1; vx++) {
			for (vy = 0; vy < visibility + visibility + 1; vy++) {
				visibleTiles[vx][vy] = visibilityPoints[vx][vy] || visibilityPoints[vx + 1][vy] || visibilityPoints[vx][vy + 1] || visibilityPoints[vx + 1][vy + 1];
			}
		}
		roofGroupMaxHeight = roofMaxHeight;
		roofGroupMinX = roofMinX;
		roofGroupMaxX = roofMaxX;
		roofGroupMaxZ = roofMaxZ;
		roofGroupMinZ = roofMinZ;
		buildActiveOccluders();
		if (underWaterGroundTiles != null) {
			setUnderwater(true);
			setShapedTile(camX, camZ, camY, null, 0, (byte) 0, entityMinX, entityMinY);
			if (GlRenderer.enabled) {
				MaterialManager.renderingUnderwater = false;
				MaterialManager.setMaterial(0, 0);
				FogManager.setFogColor(null);
				LightingManager.resetActiveLights();
			}
			setUnderwater(false);
		}
		setShapedTile(camX, camZ, camY, occlusionData, occlusionLevel, occlusionFlag, entityMinX, entityMinY);
	}

	@OriginalMember(owner = "client!uc", name = "a", descriptor = "(III[[[BIBII)V")
	public static void setShapedTile(@OriginalArg(0) int camX, @OriginalArg(1) int camZ, @OriginalArg(2) int camY, @OriginalArg(3) byte[][][] occlusionData, @OriginalArg(4) int occlusionLevel, @OriginalArg(5) byte occlusionFlag, @OriginalArg(6) int entityMinX, @OriginalArg(7) int entityMinY) {
		drawCycle++;
		visibleTileCount = 0;
		@Pc(9) int minX = entityMinX - 16;
		@Pc(13) int maxX = entityMinX + 16;
		@Pc(17) int minY = entityMinY - 16;
		@Pc(21) int maxY = entityMinY + 16;
		@Pc(32) int x;
		@Pc(37) int y;
		@Pc(183) int farX;
		for (@Pc(23) int lvl = startLevel; lvl < levels; lvl++) {
			@Pc(30) Tile[][] levelTiles = tiles[lvl];
			for (x = LightingManager.visibleMinX; x < LightingManager.visibleMaxX; x++) {
				for (y = LightingManager.visibleMinY; y < LightingManager.visibleMaxY; y++) {
					@Pc(46) Tile tile = levelTiles[x][y];
					if (tile != null) {
						if (visibleTiles[x + visibility - cameraTileX][y + visibility - cameraTileY] && (occlusionData == null || lvl < occlusionLevel || occlusionData[lvl][x][y] != occlusionFlag)) {
							tile.visible = true;
							tile.checkedBelowLevel = true;
							tile.hasUpdated = tile.sceneryLen > 0;
							visibleTileCount++;
						} else {
							tile.visible = false;
							tile.checkedBelowLevel = false;
							tile.sceneryDrawFlags = 0;
							if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
								if (tile.wall != null) {
									@Pc(103) Wall wall = tile.wall;
									wall.primary.updateModel(0, lvl, wall.zFine, wall.xFine, wall.yFine);
									if (wall.secondary != null) {
										wall.secondary.updateModel(0, lvl, wall.zFine, wall.xFine, wall.yFine);
									}
								}
								if (tile.wallDecor != null) {
									@Pc(134) WallDecor wallDecor = tile.wallDecor;
									wallDecor.primary.updateModel(wallDecor.zFine, lvl, wallDecor.orientation, wallDecor.xFine, wallDecor.yFine);
									if (wallDecor.secondary != null) {
										wallDecor.secondary.updateModel(wallDecor.zFine, lvl, wallDecor.orientation, wallDecor.xFine, wallDecor.yFine);
									}
								}
								if (tile.groundDecor != null) {
									@Pc(167) GroundDecor groundDecor = tile.groundDecor;
									groundDecor.entity.updateModel(0, lvl, groundDecor.zFine, groundDecor.xFine, groundDecor.yFine);
								}
								if (tile.scenery != null) {
									for (farX = 0; farX < tile.sceneryLen; farX++) {
										@Pc(192) Scenery s = tile.scenery[farX];
										s.entity.updateModel(s.orientation, lvl, s.zFine, s.xFine, s.yFine);
									}
								}
							}
						}
					}
				}
			}
		}
		@Pc(240) boolean isUnderwater = tileHeights == underwaterTileHeights;
		if (GlRenderer.enabled) {
			@Pc(244) GL2 gl = GlRenderer.gl;
			gl.glPushMatrix();
			gl.glTranslatef((float) -camX, (float) -camZ, (float) -camY);
			if (isUnderwater) {
				UnderwaterMaterialRenderer.applyFogFade();
				MaterialManager.setMaterial(-1, 3);
				MaterialManager.renderingUnderwater = true;
				UnderwaterMaterialRenderer.enableSecondaryTexCoordArray();
				lastUnderwaterColor = -1;
				lastUnderwaterFogRange = -1;
				for (x = 0; x < underwaterHdTiles[0].length; x++) {
					@Pc(285) GlTile hdTile = underwaterHdTiles[0][x];
					@Pc(294) float depth = 251.5F - (hdTile.blend ? 1.0F : 0.5F);
					if (hdTile.underwaterColor != lastUnderwaterColor) {
						lastUnderwaterColor = hdTile.underwaterColor;
						WaterMaterialRenderer.setUnderwaterColor(hdTile.underwaterColor);
						FogManager.setFogColor(WaterMaterialRenderer.getWaterFogColor());
					}
					hdTile.renderTiles(tiles, depth, false);
				}
				UnderwaterMaterialRenderer.disableSecondaryTexCoordArray();
			} else {
				x = startLevel;
				while (true) {
					if (x >= levels) {
						LightingManager.renderLightMeshes(cameraTileX, cameraTileY, tiles);
						break;
					}
					for (y = 0; y < underwaterHdTiles[x].length; y++) {
						@Pc(336) GlTile hdTile = underwaterHdTiles[x][y];
						@Pc(350) float depth = 201.5F - (float) x * 50.0F - (hdTile.blend ? 1.0F : 0.5F);
						if (hdTile.texture != -1 && Rasteriser.textureProvider.getMaterialType(hdTile.texture) == MaterialManager.WATER && Preferences.highWaterDetail) {
							WaterMaterialRenderer.setUnderwaterColor(hdTile.underwaterColor);
						}
						hdTile.renderTiles(tiles, depth, false);
					}
					if (x == 0 && Preferences.sceneryShadowsType > 0) {
						GlRenderer.setDepthLayer(101.5F);
						ShadowManager.renderShadowMapGL(cameraTileX, cameraTileY, visibility, camZ, visibleTiles, tileHeights[0]);
					}
					x++;
				}
			}
			gl.glPopMatrix();
		}
		@Pc(434) int nearY;
		@Pc(438) int farY;
		@Pc(450) Tile tile;
		@Pc(399) int lvl;
		@Pc(406) Tile[][] levelTiles;
		@Pc(415) int nearX;
		@Pc(428) int dy;
		for (lvl = startLevel; lvl < levels; lvl++) {
			levelTiles = tiles[lvl];
			for (y = -visibility; y <= 0; y++) {
				nearX = cameraTileX + y;
				farX = cameraTileX - y;
				if (nearX >= LightingManager.visibleMinX || farX < LightingManager.visibleMaxX) {
					for (dy = -visibility; dy <= 0; dy++) {
						nearY = cameraTileY + dy;
						farY = cameraTileY - dy;
						if (nearX >= LightingManager.visibleMinX) {
							if (nearY >= LightingManager.visibleMinY) {
								tile = levelTiles[nearX][nearY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
							if (farY < LightingManager.visibleMaxY) {
								tile = levelTiles[nearX][farY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
						}
						if (farX < LightingManager.visibleMaxX) {
							if (nearY >= LightingManager.visibleMinY) {
								tile = levelTiles[farX][nearY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
							if (farY < LightingManager.visibleMaxY) {
								tile = levelTiles[farX][farY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
						}
						if (visibleTileCount == 0) {
							if (!isUnderwater) {
								MiniMenu.walkPending = false;
							}
							return;
						}
					}
				}
			}
		}
		for (lvl = startLevel; lvl < levels; lvl++) {
			levelTiles = tiles[lvl];
			for (y = -visibility; y <= 0; y++) {
				nearX = cameraTileX + y;
				farX = cameraTileX - y;
				if (nearX >= LightingManager.visibleMinX || farX < LightingManager.visibleMaxX) {
					for (dy = -visibility; dy <= 0; dy++) {
						nearY = cameraTileY + dy;
						farY = cameraTileY - dy;
						if (nearX >= LightingManager.visibleMinX) {
							if (nearY >= LightingManager.visibleMinY) {
								tile = levelTiles[nearX][nearY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
							if (farY < LightingManager.visibleMaxY) {
								tile = levelTiles[nearX][farY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
						}
						if (farX < LightingManager.visibleMaxX) {
							if (nearY >= LightingManager.visibleMinY) {
								tile = levelTiles[farX][nearY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
							if (farY < LightingManager.visibleMaxY) {
								tile = levelTiles[farX][farY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
						}
						if (visibleTileCount == 0) {
							if (!isUnderwater) {
								MiniMenu.walkPending = false;
							}
							return;
						}
					}
				}
			}
		}
		MiniMenu.walkPending = false;
	}

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "(I)V")
	public static void setBaseLevel(@OriginalArg(0) int level) {
		startLevel = level;
		for (@Pc(3) int x = 0; x < width; x++) {
			for (@Pc(8) int y = 0; y < length; y++) {
				if (tiles[level][x][y] == null) {
					tiles[level][x][y] = new Tile(level, x, y);
				}
			}
		}
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(IIIILclient!th;JLclient!th;Lclient!th;)V")
	public static void setObjStack(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int zFine, @OriginalArg(4) Entity primary, @OriginalArg(5) long key, @OriginalArg(6) Entity secondary, @OriginalArg(7) Entity tertiary) {
		@Pc(3) ObjStackEntity entity = new ObjStackEntity();
		entity.primary = primary;
		entity.xFine = x * 128 + 64;
		entity.yFine = y * 128 + 64;
		entity.zFine = zFine;
		entity.key = key;
		entity.secondary = secondary;
		entity.tertiary = tertiary;
		@Pc(34) int maxSupportHeight = 0;
		@Pc(42) Tile tile = tiles[level][x][y];
		if (tile != null) {
			for (@Pc(46) int i = 0; i < tile.sceneryLen; i++) {
				@Pc(55) Scenery scenery = tile.scenery[i];
				if ((scenery.key & 0x400000L) == 4194304L) {
					@Pc(66) int minY = scenery.entity.getMinY();
					if (minY != -32768 && minY < maxSupportHeight) {
						maxSupportHeight = minY;
					}
				}
			}
		}
		entity.heightOffset = -maxSupportHeight;
		if (tiles[level][x][y] == null) {
			tiles[level][x][y] = new Tile(level, x, y);
		}
		tiles[level][x][y].objStack = entity;
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(Lclient!th;IIIII)V")
	public static void mergeEntityNormals(@OriginalArg(0) Entity entity, @OriginalArg(1) int level, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int xSize, @OriginalArg(5) int ySize) {
		@Pc(1) boolean sameLevel = true;
		@Pc(3) int startX = x;
		@Pc(7) int endX = x + xSize;
		@Pc(11) int startY = y - 1;
		@Pc(15) int endY = y + ySize;
		for (@Pc(17) int lvl = level; lvl <= level + 1; lvl++) {
			if (lvl != levels) {
				for (@Pc(28) int tx = startX; tx <= endX; tx++) {
					if (tx >= 0 && tx < width) {
						for (@Pc(39) int ty = startY; ty <= endY; ty++) {
							if (ty >= 0 && ty < length && (!sameLevel || tx >= endX || ty >= endY || ty < y && tx != x)) {
								@Pc(71) Tile tile = tiles[lvl][tx][ty];
								if (tile != null) {
									@Pc(158) int heightDelta = (tileHeights[lvl][tx][ty] + tileHeights[lvl][tx + 1][ty] + tileHeights[lvl][tx][ty + 1] + tileHeights[lvl][tx + 1][ty + 1]) / 4 - (tileHeights[level][x][y] + tileHeights[level][x + 1][y] + tileHeights[level][x][y + 1] + tileHeights[level][x + 1][y + 1]) / 4;
									@Pc(161) Wall wall = tile.wall;
									if (wall != null) {
										if (wall.primary.canMerge()) {
											entity.mergeNormals(wall.primary, (tx - x) * 128 + (1 - xSize) * 64, heightDelta, (ty - y) * 128 + (1 - ySize) * 64, sameLevel);
										}
										if (wall.secondary != null && wall.secondary.canMerge()) {
											entity.mergeNormals(wall.secondary, (tx - x) * 128 + (1 - xSize) * 64, heightDelta, (ty - y) * 128 + (1 - ySize) * 64, sameLevel);
										}
									}
									for (@Pc(232) int i = 0; i < tile.sceneryLen; i++) {
										@Pc(241) Scenery s = tile.scenery[i];
										if (s != null && s.entity.canMerge() && (tx == s.xMin || tx == startX) && (ty == s.yMin || ty == startY)) {
											@Pc(270) int sWidth = s.xMax + 1 - s.xMin;
											@Pc(278) int sLength = s.yMax + 1 - s.yMin;
											entity.mergeNormals(s.entity, (s.xMin - x) * 128 + (sWidth - xSize) * 64, heightDelta, (s.yMin - y) * 128 + (sLength - ySize) * 64, sameLevel);
										}
									}
								}
							}
						}
					}
				}
				startX--;
				sameLevel = false;
			}
		}
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(IIII)V")
	public static void setWallDecoration(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int walloff) {
		@Pc(7) Tile tile = tiles[level][x][y];
		if (tile == null) {
			return;
		}
		@Pc(13) WallDecor wallDecor = tile.wallDecor;
		if (wallDecor != null) {
			wallDecor.xOffset = wallDecor.xOffset * walloff / 16;
			wallDecor.yOffset = wallDecor.yOffset * walloff / 16;
		}
	}

	@OriginalMember(owner = "client!s", name = "a", descriptor = "([[F[[B[[B[Lclient!gi;II[[F[[B[[B[[II[[F)V")
	public static void buildGlLighting(@OriginalArg(0) float[][] normalsX, @OriginalArg(1) byte[][] overlays, @OriginalArg(2) byte[][] rotations, @OriginalArg(3) Light[] lights, @OriginalArg(4) int level, @OriginalArg(5) int lightCount, @OriginalArg(6) float[][] normalsZ, @OriginalArg(7) byte[][] shapes, @OriginalArg(8) byte[][] underlays, @OriginalArg(9) int[][] heights, @OriginalArg(11) float[][] normalsY) {
		for (@Pc(7) int i = 0; i < lightCount; i++) {
			@Pc(18) Light light = lights[i];
			if (light.level == level) {
				@Pc(24) int spanOffset = 0;
				@Pc(28) LightMesh lightMesh = new LightMesh();
				@Pc(37) int minX = (light.x >> 7) - light.radius;
				@Pc(46) int minY = (light.y >> 7) - light.radius;
				if (minY < 0) {
					spanOffset = -minY;
					minY = 0;
				}
				@Pc(65) int maxY = light.radius + (light.y >> 7);
				if (maxY > 103) {
					maxY = 103;
				}
				@Pc(72) int y;
				@Pc(84) short span;
				@Pc(90) int startX;
				@Pc(99) int endX;
				@Pc(114) int x;
				@Pc(133) int overlayId;
				@Pc(328) boolean rightOpen;
				@Pc(355) int adjEnd;
				for (y = minY; y <= maxY; y++) {
					span = light.spanProfile[spanOffset];
					startX = minX + (span >> 8);
					endX = startX + (span & 0xFF) - 1;
					if (endX > 103) {
						endX = 103;
					}
					if (startX < 0) {
						startX = 0;
					}
					for (x = startX; x <= endX; x++) {
						@Pc(125) int underlayId = overlays[x][y] & 0xFF;
						overlayId = underlays[x][y] & 0xFF;
						@Pc(135) boolean hasOverlay = false;
						@Pc(151) FloType floType;
						@Pc(176) int[] underlayVerts;
						@Pc(234) int[] overlayVerts;
						if (underlayId == 0) {
							if (overlayId == 0) {
								continue;
							}
							floType = FloTypeList.get(overlayId - 1);
							if (floType.baseColor == -1) {
								continue;
							}
							if (shapes[x][y] != 0) {
								underlayVerts = UNDERLAY_SHAPE_VERTICES[shapes[x][y]];
								lightMesh.indexCapacity += ((underlayVerts.length >> 1) - 2) * 3;
								lightMesh.vertexCapacity += underlayVerts.length >> 1;
								continue;
							}
						} else if (overlayId != 0) {
							floType = FloTypeList.get(overlayId - 1);
							@Pc(224) byte shape;
							if (floType.baseColor == -1) {
								shape = shapes[x][y];
								if (shape != 0) {
									overlayVerts = OVERLAY_SHAPE_VERTICES[shape];
									lightMesh.indexCapacity += ((overlayVerts.length >> 1) - 2) * 3;
									lightMesh.vertexCapacity += overlayVerts.length >> 1;
								}
								continue;
							}
							shape = shapes[x][y];
							if (shape != 0) {
								hasOverlay = true;
							}
						}
						@Pc(275) Scenery scenery = getScenery(level, x, y);
						if (scenery != null) {
							@Pc(287) int locShape = (int) (scenery.key >> 14) & 0x3F;
							if (locShape == 9) {
								overlayVerts = null;
								@Pc(302) int orientation = (int) (scenery.key >> 20) & 0x3;
								@Pc(315) boolean leftOpen;
								@Pc(343) short adjSpan;
								@Pc(349) int adjStart;
								if ((orientation & 0x1) == 0) {
									rightOpen = endX >= x + 1;
									leftOpen = x - 1 >= startX;
									if (!leftOpen && y + 1 <= maxY) {
										adjSpan = light.spanProfile[spanOffset + 1];
										adjStart = minX + (adjSpan >> 8);
										adjEnd = adjStart + (adjSpan & 0xFF);
										leftOpen = adjStart < x && x < adjEnd;
									}
									if (!rightOpen && y - 1 >= minY) {
										adjSpan = light.spanProfile[spanOffset - 1];
										adjStart = minX + (adjSpan >> 8);
										adjEnd = adjStart + (adjSpan & 0xFF);
										rightOpen = x > adjStart && x < adjEnd;
									}
									if (leftOpen && rightOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[0];
									} else if (leftOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[1];
									} else if (rightOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[1];
									}
								} else {
									leftOpen = startX <= x - 1;
									rightOpen = x + 1 <= endX;
									if (!leftOpen && y - 1 >= minY) {
										adjSpan = light.spanProfile[spanOffset - 1];
										adjStart = (adjSpan >> 8) + minX;
										adjEnd = adjStart + (adjSpan & 0xFF);
										leftOpen = adjStart < x && x < adjEnd;
									}
									if (!rightOpen && y + 1 <= maxY) {
										adjSpan = light.spanProfile[spanOffset + 1];
										adjStart = (adjSpan >> 8) + minX;
										adjEnd = adjStart + (adjSpan & 0xFF);
										rightOpen = adjStart < x && adjEnd > x;
									}
									if (leftOpen && rightOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[0];
									} else if (leftOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[1];
									} else if (rightOpen) {
										overlayVerts = UNDERLAY_SHAPE_VERTICES[1];
									}
								}
								if (overlayVerts != null) {
									lightMesh.indexCapacity += (overlayVerts.length >> 1) * 3 - 6;
									lightMesh.vertexCapacity += overlayVerts.length >> 1;
								}
								continue;
							}
						}
						if (hasOverlay) {
							overlayVerts = OVERLAY_SHAPE_VERTICES[shapes[x][y]];
							underlayVerts = UNDERLAY_SHAPE_VERTICES[shapes[x][y]];
							lightMesh.indexCapacity += ((underlayVerts.length >> 1) - 2) * 3;
							lightMesh.indexCapacity += ((overlayVerts.length >> 1) - 2) * 3;
							lightMesh.vertexCapacity += underlayVerts.length >> 1;
							lightMesh.vertexCapacity += overlayVerts.length >> 1;
						} else {
							underlayVerts = UNDERLAY_SHAPE_VERTICES[0];
							lightMesh.indexCapacity += ((underlayVerts.length >> 1) - 2) * 3;
							lightMesh.vertexCapacity += underlayVerts.length >> 1;
						}
					}
					spanOffset++;
				}
				spanOffset = 0;
				lightMesh.allocate();
				if ((light.y >> 7) - light.radius < 0) {
					spanOffset = light.radius - (light.y >> 7);
				}
				for (y = minY; y <= maxY; y++) {
					span = light.spanProfile[spanOffset];
					startX = (span >> 8) + minX;
					endX = (span & 0xFF) + startX - 1;
					if (endX > 103) {
						endX = 103;
					}
					if (startX < 0) {
						startX = 0;
					}
					for (x = startX; x <= endX; x++) {
						@Pc(775) int underlayId2 = underlays[x][y] & 0xFF;
						overlayId = overlays[x][y] & 0xFF;
						@Pc(789) byte rotation = rotations[x][y];
						@Pc(791) boolean hasOverlay2 = false;
						@Pc(805) FloType floType2;
						if (overlayId == 0) {
							if (underlayId2 == 0) {
								continue;
							}
							floType2 = FloTypeList.get(underlayId2 - 1);
							if (floType2.baseColor == -1) {
								continue;
							}
							if (shapes[x][y] != 0) {
								applyLightToTile(normalsX, heights, x, normalsZ, y, UNDERLAY_SHAPE_VERTICES[shapes[x][y]], lightMesh, light, normalsY, rotations[x][y]);
								continue;
							}
						} else if (underlayId2 != 0) {
							floType2 = FloTypeList.get(underlayId2 - 1);
							if (floType2.baseColor == -1) {
								applyLightToTile(normalsX, heights, x, normalsZ, y, OVERLAY_SHAPE_VERTICES[shapes[x][y]], lightMesh, light, normalsY, rotations[x][y]);
								continue;
							}
							@Pc(815) byte shape2 = shapes[x][y];
							if (shape2 != 0) {
								hasOverlay2 = true;
							}
						}
						@Pc(899) Scenery scenery2 = getScenery(level, x, y);
						if (scenery2 != null) {
							@Pc(911) int locShape2 = (int) (scenery2.key >> 14) & 0x3F;
							if (locShape2 == 9) {
								@Pc(917) int[] shapeVerts = null;
								@Pc(925) int orientation2 = (int) (scenery2.key >> 20) & 0x3;
								@Pc(973) int adjEnd2;
								@Pc(947) boolean rightOpen2;
								@Pc(961) short adjSpan2;
								if ((orientation2 & 0x1) == 0) {
									rightOpen = x - 1 >= startX;
									rightOpen2 = endX >= x + 1;
									if (!rightOpen && maxY >= y + 1) {
										adjSpan2 = light.spanProfile[spanOffset + 1];
										adjEnd = (adjSpan2 >> 8) + minX;
										adjEnd2 = (adjSpan2 & 0xFF) + adjEnd;
										rightOpen = x > adjEnd && adjEnd2 > x;
									}
									if (!rightOpen2 && y - 1 >= minY) {
										adjSpan2 = light.spanProfile[spanOffset - 1];
										adjEnd = minX + (adjSpan2 >> 8);
										adjEnd2 = (adjSpan2 & 0xFF) + adjEnd;
										rightOpen2 = adjEnd < x && adjEnd2 > x;
									}
									if (rightOpen && rightOpen2) {
										shapeVerts = UNDERLAY_SHAPE_VERTICES[0];
									} else if (rightOpen) {
										shapeVerts = UNDERLAY_SHAPE_VERTICES[1];
										rotation = 1;
									} else if (rightOpen2) {
										shapeVerts = UNDERLAY_SHAPE_VERTICES[1];
										rotation = 3;
									}
								} else {
									rightOpen = x - 1 >= startX;
									rightOpen2 = endX >= x + 1;
									if (!rightOpen && minY <= y - 1) {
										adjSpan2 = light.spanProfile[spanOffset - 1];
										adjEnd = minX + (adjSpan2 >> 8);
										adjEnd2 = (adjSpan2 & 0xFF) + adjEnd;
										rightOpen = x > adjEnd && adjEnd2 > x;
									}
									if (!rightOpen2 && maxY >= y + 1) {
										adjSpan2 = light.spanProfile[spanOffset + 1];
										adjEnd = minX + (adjSpan2 >> 8);
										adjEnd2 = (adjSpan2 & 0xFF) + adjEnd;
										rightOpen2 = x > adjEnd && adjEnd2 > x;
									}
									if (rightOpen && rightOpen2) {
										shapeVerts = UNDERLAY_SHAPE_VERTICES[0];
									} else if (rightOpen) {
										rotation = 0;
										shapeVerts = UNDERLAY_SHAPE_VERTICES[1];
									} else if (rightOpen2) {
										shapeVerts = UNDERLAY_SHAPE_VERTICES[1];
										rotation = 2;
									}
								}
								if (shapeVerts != null) {
									applyLightToTile(normalsX, heights, x, normalsZ, y, shapeVerts, lightMesh, light, normalsY, rotation);
								}
								continue;
							}
						}
						if (hasOverlay2) {
							applyLightToTile(normalsX, heights, x, normalsZ, y, OVERLAY_SHAPE_VERTICES[shapes[x][y]], lightMesh, light, normalsY, rotations[x][y]);
							applyLightToTile(normalsX, heights, x, normalsZ, y, UNDERLAY_SHAPE_VERTICES[shapes[x][y]], lightMesh, light, normalsY, rotations[x][y]);
						} else {
							applyLightToTile(normalsX, heights, x, normalsZ, y, UNDERLAY_SHAPE_VERTICES[0], lightMesh, light, normalsY, rotation);
						}
					}
					spanOffset++;
				}
				if (lightMesh.vertexCount > 0 && lightMesh.indexCount > 0) {
					lightMesh.upload();
					light.mesh = lightMesh;
				}
			}
		}
	}

	@OriginalMember(owner = "client!pi", name = "a", descriptor = "([[[B[[B[[B[[I[[F[[I[[B[[B[[FI[[F[[I[[I[[II)[Lclient!hg;")
	public static GlTile[] buildGlPlainTiles(@OriginalArg(0) byte[][][] renderFlags3D, @OriginalArg(1) byte[][] overlayShapes, @OriginalArg(2) byte[][] underlays, @OriginalArg(3) int[][] tileColors, @OriginalArg(4) float[][] normalsX, @OriginalArg(5) int[][] waterColors, @OriginalArg(6) byte[][] overlays, @OriginalArg(7) byte[][] shapes, @OriginalArg(8) float[][] normalsY, @OriginalArg(9) int level, @OriginalArg(10) float[][] normalsZ, @OriginalArg(11) int[][] colorsNE, @OriginalArg(12) int[][] colorsSE, @OriginalArg(13) int[][] waterTileColors) {
		@Pc(9) int[][] textureKeys = new int[105][105];
		@Pc(16) int tx;
		for (@Pc(11) int x = 1; x <= 103; x++) {
			for (tx = 1; tx <= 103; tx++) {
				@Pc(25) byte underlayId = underlays[x][tx];
				if (underlayId == 0) {
					underlayId = underlays[x - 1][tx];
				}
				if (underlayId == 0) {
					underlayId = underlays[x][tx - 1];
				}
				if (underlayId == 0) {
					underlayId = underlays[x - 1][tx - 1];
				}
				if (underlayId != 0) {
					@Pc(77) FluType fluType = FluTypeList.get((underlayId & 0xFF) - 1);
					textureKeys[x][tx] = (fluType.texture + 1 << 16) + fluType.textureScale;
				}
			}
		}
		@Pc(103) HashTable tileTable = new HashTable(128);
		@Pc(155) int renderLevel;
		@Pc(161) int waterColor;
		@Pc(169) int texKeyNE;
		@Pc(112) int ty;
		for (tx = 1; tx <= 102; tx++) {
			for (ty = 1; ty <= 102; ty++) {
				if (underlays[tx][ty] != 0) {
					@Pc(135) int[] shapeVerts;
					if (overlays[tx][ty] == 0) {
						shapeVerts = UNDERLAY_SHAPE_VERTICES[0];
					} else {
						shapeVerts = OVERLAY_SHAPE_VERTICES[overlayShapes[tx][ty]];
						if (shapeVerts.length == 0) {
							continue;
						}
					}
					renderLevel = 0;
					waterColor = textureKeys[tx][ty];
					texKeyNE = textureKeys[tx + 1][ty];
					if (waterColors != null) {
						renderLevel = waterColors[tx][ty] & 0xFFFFFF;
					}
					@Pc(188) long hashNE = (long) renderLevel | (long) texKeyNE << 32;
					@Pc(196) int texKeyNW = textureKeys[tx][ty + 1];
					@Pc(206) int texKeySE = textureKeys[tx + 1][ty + 1];
					@Pc(214) long hashNW = (long) texKeyNW << 32 | (long) renderLevel;
					@Pc(219) int vertCount = shapeVerts.length / 2;
					@Pc(227) long hashSW = (long) renderLevel | (long) waterColor << 32;
					@Pc(233) GlTile glTile = (GlTile) tileTable.get(hashSW);
					if (glTile == null) {
						glTile = new GlTile((waterColor >> 16) - 1, (float) (waterColor & 0xFFFF), false, waterTileColors != null, renderLevel);
						tileTable.put(glTile, hashSW);
					}
					glTile.faceCapacity++;
					glTile.vertexCapacity += vertCount;
					if (hashNE != hashSW) {
						glTile = (GlTile) tileTable.get(hashNE);
						if (glTile == null) {
							glTile = new GlTile((texKeyNE >> 16) - 1, (float) (texKeyNE & 0xFFFF), false, waterTileColors != null, renderLevel);
							tileTable.put(glTile, hashNE);
						}
						glTile.faceCapacity++;
						glTile.vertexCapacity += vertCount;
					}
					@Pc(340) long hashSE = (long) texKeySE << 32 | (long) renderLevel;
					if (hashSE != hashSW && hashSE != hashNE) {
						glTile = (GlTile) tileTable.get(hashSE);
						if (glTile == null) {
							glTile = new GlTile((texKeySE >> 16) - 1, (float) (texKeySE & 0xFFFF), false, waterTileColors != null, renderLevel);
							tileTable.put(glTile, hashSE);
						}
						glTile.vertexCapacity += vertCount;
						glTile.faceCapacity++;
					}
					if (hashNW != hashSW && hashNE != hashNW && hashNW != hashSE) {
						glTile = (GlTile) tileTable.get(hashNW);
						if (glTile == null) {
							glTile = new GlTile((texKeyNW >> 16) - 1, (float) (texKeyNW & 0xFFFF), false, waterTileColors != null, renderLevel);
							tileTable.put(glTile, hashNW);
						}
						glTile.faceCapacity++;
						glTile.vertexCapacity += vertCount;
					}
				}
			}
		}
		@Pc(493) GlTile iter;
		for (iter = (GlTile) tileTable.head(); iter != null; iter = (GlTile) tileTable.next()) {
			iter.allocateBuffers();
		}
		for (tx = 1; tx <= 102; tx++) {
			for (ty = 1; ty <= 102; ty++) {
				@Pc(524) byte underlay = underlays[tx][ty];
				if (underlay != 0) {
					if ((renderFlags3D[level][tx][ty] & 0x8) != 0) {
						renderLevel = 0;
					} else if ((renderFlags3D[1][tx][ty] & 0x2) == 2 && level > 0) {
						renderLevel = level - 1;
					} else {
						renderLevel = level;
					}
					waterColor = 0;
					@Pc(574) boolean[] triFlags = null;
					texKeyNE = 128;
					if (waterColors != null) {
						texKeyNE = waterColors[tx][ty] >>> 24 << 3;
						waterColor = waterColors[tx][ty] & 0xFFFFFF;
					}
					@Pc(655) int keySW;
					@Pc(712) int keySE;
					@Pc(614) int[] verts;
					@Pc(628) byte rotation;
					@Pc(678) int keyNE;
					@Pc(754) int keyNW;
					if (overlays[tx][ty] == 0) {
						keySW = underlay == underlays[tx - 1][ty - 1] ? 1 : -1;
						verts = UNDERLAY_SHAPE_VERTICES[0];
						keyNE = underlay == underlays[tx + 1][ty - 1] ? 1 : -1;
						if (underlays[tx][ty - 1] == underlay) {
							keyNE++;
							keySW++;
						} else {
							keySW--;
							keyNE--;
						}
						keySE = underlay == underlays[tx + 1][ty + 1] ? 1 : -1;
						if (underlay == underlays[tx + 1][ty]) {
							keySE++;
							keyNE++;
						} else {
							keyNE--;
							keySE--;
						}
						keyNW = underlay == underlays[tx - 1][ty + 1] ? 1 : -1;
						if (underlays[tx][ty + 1] == underlay) {
							keyNW++;
							keySE++;
						} else {
							keySE--;
							keyNW--;
						}
						if (underlays[tx - 1][ty] == underlay) {
							keyNW++;
							keySW++;
						} else {
							keyNW--;
							keySW--;
						}
						@Pc(789) int diffSWNE = keySW - keySE;
						@Pc(794) int diffNESW = keyNE - keyNW;
						if (diffNESW < 0) {
							diffNESW = -diffNESW;
						}
						if (diffSWNE < 0) {
							diffSWNE = -diffSWNE;
						}
						rotation = (byte) (diffNESW <= diffSWNE ? 0 : 1);
						shapes[tx][ty] = rotation;
					} else {
						verts = OVERLAY_SHAPE_VERTICES[overlayShapes[tx][ty]];
						triFlags = OVERLAY_TRIANGLE_FLAGS[overlayShapes[tx][ty]];
						rotation = shapes[tx][ty];
						if (verts.length == 0) {
							continue;
						}
					}
					keySW = textureKeys[tx][ty];
					keyNE = textureKeys[tx + 1][ty];
					keySE = textureKeys[tx + 1][ty + 1];
					@Pc(861) long tilHashSW = (long) keySW << 32 | (long) waterColor;
					@Pc(869) long tilHashNE = (long) keyNE << 32 | (long) waterColor;
					@Pc(877) long tilHashSE = (long) keySE << 32 | (long) waterColor;
					@Pc(883) int colorSW = colorsNE[tx][ty];
					keyNW = textureKeys[tx][ty + 1];
					@Pc(901) int colorNE = colorsNE[tx + 1][ty + 1];
					@Pc(909) int colorSE = colorsNE[tx + 1][ty];
					@Pc(917) long tilHashNW = (long) waterColor | (long) keyNW << 32;
					@Pc(925) int colorNW = colorsNE[tx][ty + 1];
					@Pc(931) int lightSW = tileColors[tx][ty];
					@Pc(939) int lightSE = tileColors[tx + 1][ty];
					@Pc(949) int lightNE = tileColors[tx + 1][ty + 1];
					@Pc(957) int lightNW = tileColors[tx][ty + 1];
					@Pc(963) int texNE = (keyNE >> 16) - 1;
					@Pc(969) int texSW = (keySW >> 16) - 1;
					@Pc(975) int texSE = (keySE >> 16) - 1;
					@Pc(981) GlTile target = (GlTile) tileTable.get(tilHashSW);
					addGlTileVertices(waterTileColors, keySW <= keySW, getTexturedColor(texSW, colorSW, lightSW), target, verts, ty, renderLevel, tx, keySW <= keySE, normalsY, keyNW >= keySW, normalsX, texKeyNE, getTexturedColor(texSW, colorNW, lightNW), getTexturedColor(texSW, colorNE, lightNE), keySW <= keyNE, colorsSE, normalsZ, rotation, getTexturedColor(texSW, colorSE, lightSE), triFlags);
					@Pc(1050) int texNW = (keyNW >> 16) - 1;
					if (tilHashNE != tilHashSW) {
						target = (GlTile) tileTable.get(tilHashNE);
						addGlTileVertices(waterTileColors, keyNE <= keySW, getTexturedColor(texNE, colorSW, lightSW), target, verts, ty, renderLevel, tx, keySE >= keyNE, normalsY, keyNE <= keyNW, normalsX, texKeyNE, getTexturedColor(texNE, colorNW, lightNW), getTexturedColor(texNE, colorNE, lightNE), keyNE <= keyNE, colorsSE, normalsZ, rotation, getTexturedColor(texNE, colorSE, lightSE), triFlags);
					}
					if (tilHashSE != tilHashSW && tilHashSE != tilHashNE) {
						target = (GlTile) tileTable.get(tilHashSE);
						addGlTileVertices(waterTileColors, keySW >= keySE, getTexturedColor(texSE, colorSW, lightSW), target, verts, ty, renderLevel, tx, keySE <= keySE, normalsY, keySE <= keyNW, normalsX, texKeyNE, getTexturedColor(texSE, colorNW, lightNW), getTexturedColor(texSE, colorNE, lightNE), keyNE >= keySE, colorsSE, normalsZ, rotation, getTexturedColor(texSE, colorSE, lightSE), triFlags);
					}
					if (tilHashNW != tilHashSW && tilHashNW != tilHashNE && tilHashNW != tilHashSE) {
						target = (GlTile) tileTable.get(tilHashNW);
						addGlTileVertices(waterTileColors, keyNW <= keySW, getTexturedColor(texNW, colorSW, lightSW), target, verts, ty, renderLevel, tx, keyNW <= keySE, normalsY, keyNW >= keyNW, normalsX, texKeyNE, getTexturedColor(texNW, colorNW, lightNW), getTexturedColor(texNW, colorNE, lightNE), keyNE >= keyNW, colorsSE, normalsZ, rotation, getTexturedColor(texNW, colorSE, lightSE), triFlags);
					}
				}
			}
		}
		for (iter = (GlTile) tileTable.head(); iter != null; iter = (GlTile) tileTable.next()) {
			if (iter.vertexCount == 0) {
				iter.unlink();
			} else {
				iter.uploadVertexData();
			}
		}
		tx = tileTable.size();
		@Pc(1348) GlTile[] result = new GlTile[tx];
		tileTable.toArray(result);
		@Pc(1358) long[] keys = new long[tx];
		for (renderLevel = 0; renderLevel < tx; renderLevel++) {
			keys[renderLevel] = result[renderLevel].key;
		}
		ArrayUtils.sort(keys, result);
		return result;
	}

	@OriginalMember(owner = "client!ec", name = "a", descriptor = "([[IZILclient!hg;[IIIIZ[[FZI[[FIIIZ[[I[[FBI[Z)V")
	public static void addGlTileVertices(@OriginalArg(0) int[][] waterTileColors, @OriginalArg(1) boolean swInRange, @OriginalArg(2) int colorSW, @OriginalArg(3) GlTile glTile, @OriginalArg(4) int[] vertices, @OriginalArg(5) int y, @OriginalArg(6) int renderLevel, @OriginalArg(7) int x, @OriginalArg(8) boolean neInRange, @OriginalArg(9) float[][] normalsY, @OriginalArg(10) boolean nwInRange, @OriginalArg(12) float[][] normalsX, @OriginalArg(13) int texScale, @OriginalArg(14) int colorNW, @OriginalArg(15) int colorNE, @OriginalArg(16) boolean seInRange, @OriginalArg(17) int[][] heights, @OriginalArg(18) float[][] normalsZ, @OriginalArg(19) byte rotation, @OriginalArg(20) int colorSE, @OriginalArg(21) boolean[] triFlags) {
		@Pc(11) int packedSW = (colorSW << 8) + (swInRange ? 255 : 0);
		@Pc(25) int packedNE = (neInRange ? 255 : 0) + (colorNE << 8);
		@Pc(31) int[] vertIndices = new int[vertices.length / 2];
		@Pc(41) int packedNW = (nwInRange ? 255 : 0) + (colorNW << 8);
		@Pc(51) int packedSE = (colorSE << 8) + (seInRange ? 255 : 0);
		for (@Pc(53) int i = 0; i < vertIndices.length; i++) {
			@Pc(67) int vx = vertices[i + i];
			@Pc(80) int[][] heightsToUse = waterTileColors == null || triFlags == null || !triFlags[i] ? heights : waterTileColors;
			@Pc(88) int vy = vertices[i + i + 1];
			vertIndices[i] = computeGlTileVertex(packedNW, (float) texScale, packedSW, packedSE, waterTileColors, heightsToUse, x, normalsZ, packedNE, rotation, false, glTile, normalsY, y, vx, normalsX, vy);
		}
		glTile.addFace(renderLevel, x, y, vertIndices, null, false);
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(IIIIIIII)V")
	public static void addLocModel(@OriginalArg(0) int locIndex, @OriginalArg(1) int x, @OriginalArg(2) int plane, @OriginalArg(3) int orientation, @OriginalArg(4) int y, @OriginalArg(6) int locType, @OriginalArg(7) int layer) {
		if (x < 1 || y < 1 || x > 102 || y > 102) {
			return;
		}
		@Pc(39) int transformedPlane;
		if (!allLevelsAreVisible() && (renderFlags[0][x][y] & 0x2) == 0) {
			transformedPlane = plane;
			if ((renderFlags[plane][x][y] & 0x8) != 0) {
				transformedPlane = 0;
			}
			if (transformedPlane != centralPlane) {
				return;
			}
		}
		transformedPlane = plane;
		if (plane < 3 && (renderFlags[1][x][y] & 0x2) == 2) {
			transformedPlane = plane + 1;
		}
		addGroundDecorEntity(y, x, plane, layer, transformedPlane, PathFinder.collisionMaps[plane]);
		if (locIndex >= 0) {
			@Pc(92) boolean savedShowGroundDecor = Preferences.showGroundDecorations;
			Preferences.showGroundDecorations = true;
			addLoc(transformedPlane, false, plane, false, PathFinder.collisionMaps[plane], locIndex, locType, x, y, orientation);
			Preferences.showGroundDecorations = savedShowGroundDecor;
		}
	}

	@OriginalMember(owner = "client!af", name = "a", descriptor = "(IIIIIILclient!th;IJ)Z")
	public static boolean addEntity(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int zFine, @OriginalArg(4) int xSize, @OriginalArg(5) int ySize, @OriginalArg(6) Entity entity, @OriginalArg(8) long key) {
		if (entity == null) {
			return true;
		} else {
			@Pc(11) int xFine = x * 128 + xSize * 64;
			@Pc(19) int yFine = y * 128 + ySize * 64;
			return addSceneryEntity(level, x, y, xSize, ySize, xFine, yFine, zFine, entity, 0, false, key);
		}
	}

	@OriginalMember(owner = "client!ac", name = "a", descriptor = "(IIII)I")
	public static int getRenderLevel(@OriginalArg(0) int y, @OriginalArg(1) int x, @OriginalArg(3) int plane) {
		if ((renderFlags[plane][x][y] & 0x8) == 0) {
			return plane <= 0 || (renderFlags[1][x][y] & 0x2) == 0 ? plane : plane - 1;
		} else {
			return 0;
		}
	}

	@OriginalMember(owner = "client!ke", name = "a", descriptor = "(Lclient!rh;IIIIIIIZ)V")
	public static void drawPlainTile(@OriginalArg(0) PlainTile tile, @OriginalArg(1) int level, @OriginalArg(2) int sinPitch, @OriginalArg(3) int cosPitch, @OriginalArg(4) int cosYaw, @OriginalArg(5) int sinYaw, @OriginalArg(6) int tileX, @OriginalArg(7) int tileY, @OriginalArg(8) boolean glEnabled) {
		@Pc(6) int xSW;
		@Pc(7) int x0 = xSW = (tileX << 7) - cameraX;
		@Pc(14) int ySW;
		@Pc(15) int y0 = ySW = (tileY << 7) - cameraY;
		@Pc(20) int xNE;
		@Pc(21) int x1 = xNE = x0 + 128;
		@Pc(26) int yNW;
		@Pc(27) int y1 = yNW = y0 + 128;
		@Pc(37) int heightSW = tileHeights[level][tileX][tileY] - cameraZ;
		@Pc(49) int heightSE = tileHeights[level][tileX + 1][tileY] - cameraZ;
		@Pc(63) int heightNE = tileHeights[level][tileX + 1][tileY + 1] - cameraZ;
		@Pc(75) int heightNW = tileHeights[level][tileX][tileY + 1] - cameraZ;
		@Pc(85) int temp = y0 * cosYaw + x0 * sinYaw >> 16;
		@Pc(95) int rotYSW = y0 * sinYaw - x0 * cosYaw >> 16;
		@Pc(97) int rotXSW = temp;
		@Pc(107) int screenZSW = heightSW * cosPitch - rotYSW * sinPitch >> 16;
		@Pc(117) int depthSW = heightSW * sinPitch + rotYSW * cosPitch >> 16;
		@Pc(119) int savedZSW = screenZSW;
		if (depthSW < 50) {
			return;
		}
		temp = ySW * cosYaw + x1 * sinYaw >> 16;
		@Pc(143) int rotYSE = ySW * sinYaw - x1 * cosYaw >> 16;
		x1 = temp;
		temp = heightSE * cosPitch - rotYSE * sinPitch >> 16;
		@Pc(165) int depthSE = heightSE * sinPitch + rotYSE * cosPitch >> 16;
		heightSE = temp;
		if (depthSE < 50) {
			return;
		}
		temp = y1 * cosYaw + xNE * sinYaw >> 16;
		y1 = y1 * sinYaw - xNE * cosYaw >> 16;
		@Pc(193) int rotXNE = temp;
		temp = heightNE * cosPitch - y1 * sinPitch >> 16;
		y1 = heightNE * sinPitch + y1 * cosPitch >> 16;
		heightNE = temp;
		if (y1 < 50) {
			return;
		}
		temp = yNW * cosYaw + xSW * sinYaw >> 16;
		@Pc(239) int rotYNW = yNW * sinYaw - xSW * cosYaw >> 16;
		@Pc(241) int rotXNW = temp;
		temp = heightNW * cosPitch - rotYNW * sinPitch >> 16;
		@Pc(261) int depthNW = heightNW * sinPitch + rotYNW * cosPitch >> 16;
		if (depthNW < 50) {
			return;
		}
		@Pc(275) int screenXSW = Rasteriser.centerX + (rotXSW << 9) / depthSW;
		@Pc(283) int screenYSW = Rasteriser.centerY + (savedZSW << 9) / depthSW;
		@Pc(291) int screenXSE = Rasteriser.centerX + (x1 << 9) / depthSE;
		@Pc(299) int screenYSE = Rasteriser.centerY + (heightSE << 9) / depthSE;
		@Pc(307) int screenXNE = Rasteriser.centerX + (rotXNE << 9) / y1;
		@Pc(315) int screenYNE = Rasteriser.centerY + (heightNE << 9) / y1;
		@Pc(323) int screenXNW = Rasteriser.centerX + (rotXNW << 9) / depthNW;
		@Pc(331) int screenYNW = Rasteriser.centerY + (temp << 9) / depthNW;
		Rasteriser.alpha = 0;
		@Pc(475) int avgColor;
		if ((screenXNE - screenXNW) * (screenYSE - screenYNW) - (screenYNE - screenYNW) * (screenXSE - screenXNW) > 0) {
			if (MiniMenu.walkPending && isRectVisible(MiniMenu.walkDestTileX + Rasteriser.centerX, MiniMenu.walkDestTileY + Rasteriser.centerY, screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE)) {
				MiniMenu.clickTileX = tileX;
				MiniMenu.clickTileY = tileY;
			}
			if (API.IsRoofVisibilityPicking() && isRectVisible(API.GetRoofVisibilityPickScreenX() + Rasteriser.centerX, API.GetRoofVisibilityPickScreenY() + Rasteriser.centerY, screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE)) {
				API.ReportRoofVisibilityTile(tileX, tileY, level);
			}
			if (!GlRenderer.enabled && !glEnabled) {
				Rasteriser.testX = screenXNE < 0 || screenXNW < 0 || screenXSE < 0 || screenXNE > Rasteriser.width || screenXNW > Rasteriser.width || screenXSE > Rasteriser.width;
				if (tile.textureId == -1) {
					if (tile.colorNE != 12345678) {
						Rasteriser.fillGouraudTriangle(screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE, tile.colorNE, tile.colorNW, tile.colorSE);
					}
				} else if (!Preferences.manyGroundTextures) {
					avgColor = Rasteriser.textureProvider.getAverageColor(tile.textureId);
					Rasteriser.fillGouraudTriangle(screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE, ColorUtils.multiplyLightness3(avgColor, tile.colorNE), ColorUtils.multiplyLightness3(avgColor, tile.colorNW), ColorUtils.multiplyLightness3(avgColor, tile.colorSE));
				} else if (tile.flat) {
					Rasteriser.fillTexturedTriangle(screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE, tile.colorNE, tile.colorNW, tile.colorSE, rotXSW, x1, rotXNW, savedZSW, heightSE, temp, depthSW, depthSE, depthNW, tile.textureId);
				} else {
					Rasteriser.fillTexturedTriangle(screenYNE, screenYNW, screenYSE, screenXNE, screenXNW, screenXSE, tile.colorNE, tile.colorNW, tile.colorSE, rotXNE, rotXNW, x1, heightNE, temp, heightSE, y1, depthNW, depthSE, tile.textureId);
				}
			}
		}
		if ((screenXSW - screenXSE) * (screenYNW - screenYSE) - (screenYSW - screenYSE) * (screenXNW - screenXSE) <= 0) {
			return;
		}
		if (MiniMenu.walkPending && isRectVisible(MiniMenu.walkDestTileX + Rasteriser.centerX, MiniMenu.walkDestTileY + Rasteriser.centerY, screenYSW, screenYSE, screenYNW, screenXSW, screenXSE, screenXNW)) {
			MiniMenu.clickTileX = tileX;
			MiniMenu.clickTileY = tileY;
		}
		if (API.IsRoofVisibilityPicking() && isRectVisible(API.GetRoofVisibilityPickScreenX() + Rasteriser.centerX, API.GetRoofVisibilityPickScreenY() + Rasteriser.centerY, screenYSW, screenYSE, screenYNW, screenXSW, screenXSE, screenXNW)) {
			API.ReportRoofVisibilityTile(tileX, tileY, level);
		}
		if (GlRenderer.enabled || glEnabled) {
			return;
		}
		Rasteriser.testX = screenXSW < 0 || screenXSE < 0 || screenXNW < 0 || screenXSW > Rasteriser.width || screenXSE > Rasteriser.width || screenXNW > Rasteriser.width;
		if (tile.textureId == -1) {
			if (tile.colorSW != 12345678) {
				Rasteriser.fillGouraudTriangle(screenYSW, screenYSE, screenYNW, screenXSW, screenXSE, screenXNW, tile.colorSW, tile.colorSE, tile.colorNW);
			}
		} else if (Preferences.manyGroundTextures) {
			Rasteriser.fillTexturedTriangle(screenYSW, screenYSE, screenYNW, screenXSW, screenXSE, screenXNW, tile.colorSW, tile.colorSE, tile.colorNW, rotXSW, x1, rotXNW, savedZSW, heightSE, temp, depthSW, depthSE, depthNW, tile.textureId);
		} else {
			avgColor = Rasteriser.textureProvider.getAverageColor(tile.textureId);
			Rasteriser.fillGouraudTriangle(screenYSW, screenYSE, screenYNW, screenXSW, screenXSE, screenXNW, ColorUtils.multiplyLightness3(avgColor, tile.colorSW), ColorUtils.multiplyLightness3(avgColor, tile.colorSE), ColorUtils.multiplyLightness3(avgColor, tile.colorNW));
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(III)Z")
	public static boolean isTileOccluded(@OriginalArg(0) int level, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) int cachedResult = occlusionCache[level][x][y];
		if (cachedResult == -drawCycle) {
			return false;
		} else if (cachedResult == drawCycle) {
			return true;
		} else {
			@Pc(22) int xFine = x << 7;
			@Pc(26) int yFine = y << 7;
			if (isOccluded(xFine + 1, tileHeights[level][x][y], yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][x + 1][y], yFine + 1) && isOccluded(xFine + 128 - 1, tileHeights[level][x + 1][y + 1], yFine + 128 - 1) && isOccluded(xFine + 1, tileHeights[level][x][y + 1], yFine + 128 - 1)) {
				occlusionCache[level][x][y] = drawCycle;
				return true;
			} else {
				occlusionCache[level][x][y] = -drawCycle;
				return false;
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIII)Z")
	public static boolean projectToScreen(@OriginalArg(0) int xOffset, @OriginalArg(1) int zNear, @OriginalArg(2) int zFar, @OriginalArg(3) int yOffset, @OriginalArg(4) int maxDepth) {
		@Pc(9) int rotatedX = yOffset * sinYaw + xOffset * cosYaw >> 16;
		@Pc(19) int rotatedY = yOffset * cosYaw - xOffset * sinYaw >> 16;
		@Pc(29) int nearDepth = zNear * sinPitch + rotatedY * cosPitch >> 16;
		@Pc(39) int nearScreenZ = zNear * cosPitch - rotatedY * sinPitch >> 16;
		if (nearDepth < 1) {
			nearDepth = 1;
		}
		@Pc(50) int nearScreenX = (rotatedX << 9) / nearDepth;
		@Pc(56) int nearScreenY = (nearScreenZ << 9) / nearDepth;
		@Pc(66) int farDepth = zFar * sinPitch + rotatedY * cosPitch >> 16;
		@Pc(76) int farScreenZ = zFar * cosPitch - rotatedY * sinPitch >> 16;
		if (farDepth < 1) {
			farDepth = 1;
		}
		@Pc(87) int farScreenX = (rotatedX << 9) / farDepth;
		@Pc(93) int farScreenY = (farScreenZ << 9) / farDepth;
		if (nearDepth < 50 && farDepth < 50) {
			return false;
		} else if (nearDepth > maxDepth && farDepth > maxDepth) {
			return false;
		} else if (nearScreenX < Rasteriser.screenLowerX && farScreenX < Rasteriser.screenLowerX) {
			return false;
		} else if (nearScreenX > Rasteriser.screenUpperX && farScreenX > Rasteriser.screenUpperX) {
			return false;
		} else if (nearScreenY < Rasteriser.screenLowerY && farScreenY < Rasteriser.screenLowerY) {
			return false;
		} else {
			return nearScreenY <= Rasteriser.screenUpperY || farScreenY <= Rasteriser.screenUpperY;
		}
	}

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "([[F[[II[[FI[ILclient!fj;BLclient!gi;[[FI)V")
	public static void applyLightToTile(@OriginalArg(0) float[][] normalsX, @OriginalArg(1) int[][] heights, @OriginalArg(2) int x, @OriginalArg(3) float[][] normalsZ, @OriginalArg(4) int y, @OriginalArg(5) int[] vertices, @OriginalArg(6) LightMesh lightMesh, @OriginalArg(8) Light light, @OriginalArg(9) float[][] normalsY, @OriginalArg(10) int rotation) {
		@Pc(7) int[] indices = new int[vertices.length / 2];
		for (@Pc(13) int i = 0; i < indices.length; i++) {
			@Pc(27) int vx = vertices[i + i];
			@Pc(35) int vy = vertices[i + i + 1];
			@Pc(53) int tmp;
			if (rotation == 1) {
				tmp = vx;
				vx = vy;
				vy = 128 - tmp;
			} else if (rotation == 2) {
				vy = 128 - vy;
				vx = 128 - vx;
			} else if (rotation == 3) {
				tmp = vx;
				vx = 128 - vy;
				vy = tmp;
			}
			@Pc(123) float normalZ;
			@Pc(107) float normalX;
			@Pc(115) float normalY;
			if (vx == 0 && vy == 0) {
				normalZ = normalsZ[x][y];
				normalY = normalsY[x][y];
				normalX = normalsX[x][y];
			} else if (vx == 128 && vy == 0) {
				normalX = normalsX[x + 1][y];
				normalY = normalsY[x + 1][y];
				normalZ = normalsZ[x + 1][y];
			} else if (vx == 128 && vy == 128) {
				normalZ = normalsZ[x + 1][y + 1];
				normalY = normalsY[x + 1][y + 1];
				normalX = normalsX[x + 1][y + 1];
			} else if (vx == 0 && vy == 128) {
				normalX = normalsX[x][y + 1];
				normalZ = normalsZ[x][y + 1];
				normalY = normalsY[x][y + 1];
			} else {
				normalY = normalsY[x][y];
				normalX = normalsX[x][y];
				@Pc(187) float xFrac = (float) vx / 128.0F;
				normalZ = normalsZ[x][y];
				@Pc(208) float lerpNZ = normalZ + (normalsZ[x + 1][y] - normalZ) * xFrac;
				@Pc(222) float lerpNX = normalX + xFrac * (normalsX[x + 1][y] - normalX);
				@Pc(237) float lerpNY = normalY + (normalsY[x + 1][y] - normalY) * xFrac;
				@Pc(245) float ny01 = normalsY[x][y + 1];
				@Pc(261) float lerpNYNext = ny01 + (normalsY[x + 1][y + 1] - ny01) * xFrac;
				@Pc(269) float nz01 = normalsZ[x][y + 1];
				@Pc(274) float yFrac = (float) vy / 128.0F;
				normalY = lerpNY + (lerpNYNext - lerpNY) * yFrac;
				@Pc(291) float nx01 = normalsX[x][y + 1];
				@Pc(307) float lerpNXNext = nx01 + (normalsX[x + 1][y + 1] - nx01) * xFrac;
				@Pc(324) float lerpNZNext = nz01 + (normalsZ[x + 1][y + 1] - nz01) * xFrac;
				normalZ = lerpNZ + (lerpNZNext - lerpNZ) * yFrac;
				normalX = lerpNX + (lerpNXNext - lerpNX) * yFrac;
			}
			@Pc(393) int xFine = (x << 7) + vx;
			@Pc(400) int yFine = (y << 7) + vy;
			@Pc(408) int height = interpolateHeight(vx, y, heights, x, vy);
			indices[i] = lightMesh.addVertex(light, xFine, height, yFine, normalY, normalZ, normalX);
		}
		lightMesh.addFan(indices);
	}

	@OriginalMember(owner = "client!jj", name = "a", descriptor = "()V")
	public static void buildActiveOccluders() {
		activeOccluderCount = 0;
		occluderLoop:
		for (@Pc(3) int i = 0; i < occluderCount; i++) {
			@Pc(10) Occluder occluder = occluders[i];
			@Pc(14) int offset;
			if (roofGroupMaxHeight != null) {
				for (offset = 0; offset < roofGroupMaxHeight.length; offset++) {
					if (roofGroupMaxHeight[offset] != -1000000 && (occluder.minZ <= roofGroupMaxHeight[offset] || occluder.maxZ <= roofGroupMaxHeight[offset]) && (occluder.minX <= roofGroupMaxX[offset] || occluder.maxX <= roofGroupMaxX[offset]) && (occluder.minX >= roofGroupMinX[offset] || occluder.maxX >= roofGroupMinX[offset]) && (occluder.minY <= roofGroupMaxZ[offset] || occluder.maxY <= roofGroupMaxZ[offset]) && (occluder.minY >= roofGroupMinZ[offset] || occluder.maxY >= roofGroupMinZ[offset])) {
						continue occluderLoop;
					}
				}
			}
			@Pc(115) int rangeMin;
			@Pc(126) int rangeMax;
			@Pc(158) int distance;
			@Pc(137) boolean visible;
			if (occluder.type == 1) {
				offset = occluder.minTileX + visibility - cameraTileX;
				if (offset >= 0 && offset <= visibility + visibility) {
					rangeMin = occluder.minTileY + visibility - cameraTileY;
					if (rangeMin < 0) {
						rangeMin = 0;
					}
					rangeMax = occluder.maxTileY + visibility - cameraTileY;
					if (rangeMax > visibility + visibility) {
						rangeMax = visibility + visibility;
					}
					visible = false;
					while (rangeMin <= rangeMax) {
						if (visibleTiles[offset][rangeMin++]) {
							visible = true;
							break;
						}
					}
					if (visible) {
						distance = cameraX - occluder.minX;
						if (distance > 32) {
							occluder.direction = 1;
						} else {
							if (distance >= -32) {
								continue;
							}
							occluder.direction = 2;
							distance = -distance;
						}
						occluder.nearYSlope = (occluder.minY - cameraY << 8) / distance;
						occluder.farYSlope = (occluder.maxY - cameraY << 8) / distance;
						occluder.nearZSlope = (occluder.minZ - cameraZ << 8) / distance;
						occluder.farZSlope = (occluder.maxZ - cameraZ << 8) / distance;
						activeOccluders[activeOccluderCount++] = occluder;
					}
				}
			} else if (occluder.type == 2) {
				offset = occluder.minTileY + visibility - cameraTileY;
				if (offset >= 0 && offset <= visibility + visibility) {
					rangeMin = occluder.minTileX + visibility - cameraTileX;
					if (rangeMin < 0) {
						rangeMin = 0;
					}
					rangeMax = occluder.maxTileX + visibility - cameraTileX;
					if (rangeMax > visibility + visibility) {
						rangeMax = visibility + visibility;
					}
					visible = false;
					while (rangeMin <= rangeMax) {
						if (visibleTiles[rangeMin++][offset]) {
							visible = true;
							break;
						}
					}
					if (visible) {
						distance = cameraY - occluder.minY;
						if (distance > 32) {
							occluder.direction = 3;
						} else {
							if (distance >= -32) {
								continue;
							}
							occluder.direction = 4;
							distance = -distance;
						}
						occluder.farXSlope = (occluder.minX - cameraX << 8) / distance;
						occluder.nearXSlope = (occluder.maxX - cameraX << 8) / distance;
						occluder.nearZSlope = (occluder.minZ - cameraZ << 8) / distance;
						occluder.farZSlope = (occluder.maxZ - cameraZ << 8) / distance;
						activeOccluders[activeOccluderCount++] = occluder;
					}
				}
			} else if (occluder.type == 4) {
				offset = occluder.minZ - cameraZ;
				if (offset > 128) {
					rangeMin = occluder.minTileY + visibility - cameraTileY;
					if (rangeMin < 0) {
						rangeMin = 0;
					}
					rangeMax = occluder.maxTileY + visibility - cameraTileY;
					if (rangeMax > visibility + visibility) {
						rangeMax = visibility + visibility;
					}
					if (rangeMin <= rangeMax) {
						@Pc(408) int colMin = occluder.minTileX + visibility - cameraTileX;
						if (colMin < 0) {
							colMin = 0;
						}
						distance = occluder.maxTileX + visibility - cameraTileX;
						if (distance > visibility + visibility) {
							distance = visibility + visibility;
						}
						@Pc(430) boolean found = false;
						visibilitySearch:
						for (@Pc(432) int col = colMin; col <= distance; col++) {
							for (@Pc(437) int row = rangeMin; row <= rangeMax; row++) {
								if (visibleTiles[col][row]) {
									found = true;
									break visibilitySearch;
								}
							}
						}
						if (found) {
							occluder.direction = 5;
							occluder.farXSlope = (occluder.minX - cameraX << 8) / offset;
							occluder.nearXSlope = (occluder.maxX - cameraX << 8) / offset;
							occluder.nearYSlope = (occluder.minY - cameraY << 8) / offset;
							occluder.farYSlope = (occluder.maxY - cameraY << 8) / offset;
							activeOccluders[activeOccluderCount++] = occluder;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IIIIIIII)Z")
	public static boolean isRectVisible(@OriginalArg(0) int pointX, @OriginalArg(1) int pointY, @OriginalArg(2) int y0, @OriginalArg(3) int y1, @OriginalArg(4) int y2, @OriginalArg(5) int x0, @OriginalArg(6) int x1, @OriginalArg(7) int x2) {
		if (pointY < y0 && pointY < y1 && pointY < y2) {
			return false;
		} else if (pointY > y0 && pointY > y1 && pointY > y2) {
			return false;
		} else if (pointX < x0 && pointX < x1 && pointX < x2) {
			return false;
		} else if (pointX > x0 && pointX > x1 && pointX > x2) {
			return false;
		} else {
			@Pc(59) int cross0 = (pointY - y0) * (x1 - x0) - (pointX - x0) * (y1 - y0);
			@Pc(75) int cross1 = (pointY - y2) * (x0 - x2) - (pointX - x2) * (y0 - y2);
			@Pc(91) int cross2 = (pointY - y1) * (x2 - x1) - (pointX - x1) * (y2 - y1);
			return cross0 * cross2 > 0 && cross2 * cross1 > 0;
		}
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(Lclient!fg;IIIIIIZ)V")
	public static void drawShapedTile(@OriginalArg(0) ShapedTile tile, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int cosYaw, @OriginalArg(4) int sinYaw, @OriginalArg(5) int tileX, @OriginalArg(6) int tileY, int level, @OriginalArg(7) boolean glEnabled) {
		@Pc(3) int vertexCount = tile.vertexX.length;
		@Pc(5) int i;
		@Pc(15) int dx;
		@Pc(22) int dz;
		@Pc(29) int dy;
		@Pc(39) int rotX;
		for (i = 0; i < vertexCount; i++) {
			dx = tile.vertexX[i] - cameraX;
			dz = tile.vertexY[i] - cameraZ;
			dy = tile.vertexZ[i] - cameraY;
			rotX = dy * cosYaw + dx * sinYaw >> 16;
			@Pc(49) int rotY = dy * sinYaw - dx * cosYaw >> 16;
			@Pc(61) int scrZ = dz * cosPitch - rotY * sinPitch >> 16;
			@Pc(71) int depth = dz * sinPitch + rotY * cosPitch >> 16;
			if (depth < 50) {
				return;
			}
			if (tile.triangleTextureId != null) {
				projectedX[i] = rotX;
				projectedY[i] = scrZ;
				projectedZ[i] = depth;
			}
			screenX[i] = Rasteriser.centerX + (rotX << 9) / depth;
			screenY[i] = Rasteriser.centerY + (scrZ << 9) / depth;
		}
		Rasteriser.alpha = 0;
		vertexCount = tile.triangleVertexA.length;
		for (i = 0; i < vertexCount; i++) {
			dx = tile.triangleVertexA[i];
			dz = tile.triangleVertexB[i];
			dy = tile.triangleVertexC[i];
			rotX = screenX[dx];
			@Pc(148) int sx1 = screenX[dz];
			@Pc(152) int sx2 = screenX[dy];
			@Pc(156) int sy0 = screenY[dx];
			@Pc(160) int sy1 = screenY[dz];
			@Pc(164) int sy2 = screenY[dy];
			if ((rotX - sx1) * (sy2 - sy1) - (sy0 - sy1) * (sx2 - sx1) > 0) {
				if (MiniMenu.walkPending && isRectVisible(MiniMenu.walkDestTileX + Rasteriser.centerX, MiniMenu.walkDestTileY + Rasteriser.centerY, sy0, sy1, sy2, rotX, sx1, sx2)) {
					MiniMenu.clickTileX = tileX;
					MiniMenu.clickTileY = tileY;
				}
				if (API.IsRoofVisibilityPicking() && isRectVisible(API.GetRoofVisibilityPickScreenX() + Rasteriser.centerX, API.GetRoofVisibilityPickScreenY() + Rasteriser.centerY, sy0, sy1, sy2, rotX, sx1, sx2)) {
					API.ReportRoofVisibilityTile(tileX, tileY, level);
				}
				if (!GlRenderer.enabled && !glEnabled) {
					Rasteriser.testX = rotX < 0 || sx1 < 0 || sx2 < 0 || rotX > Rasteriser.width || sx1 > Rasteriser.width || sx2 > Rasteriser.width;
					if (tile.triangleTextureId == null || tile.triangleTextureId[i] == -1) {
						if (tile.triangleColorA[i] != 12345678) {
							Rasteriser.fillGouraudTriangle(sy0, sy1, sy2, rotX, sx1, sx2, tile.triangleColorA[i], tile.triangleColorB[i], tile.triangleColorC[i]);
						}
					} else if (!Preferences.manyGroundTextures) {
						@Pc(373) int avgColor = Rasteriser.textureProvider.getAverageColor(tile.triangleTextureId[i]);
						Rasteriser.fillGouraudTriangle(sy0, sy1, sy2, rotX, sx1, sx2, ColorUtils.multiplyLightness3(avgColor, tile.triangleColorA[i]), ColorUtils.multiplyLightness3(avgColor, tile.triangleColorB[i]), ColorUtils.multiplyLightness3(avgColor, tile.triangleColorC[i]));
					} else if (tile.flat) {
						Rasteriser.fillTexturedTriangle(sy0, sy1, sy2, rotX, sx1, sx2, tile.triangleColorA[i], tile.triangleColorB[i], tile.triangleColorC[i], projectedX[0], projectedX[1], projectedX[3], projectedY[0], projectedY[1], projectedY[3], projectedZ[0], projectedZ[1], projectedZ[3], tile.triangleTextureId[i]);
					} else {
						Rasteriser.fillTexturedTriangle(sy0, sy1, sy2, rotX, sx1, sx2, tile.triangleColorA[i], tile.triangleColorB[i], tile.triangleColorC[i], projectedX[dx], projectedX[dz], projectedX[dy], projectedY[dx], projectedY[dz], projectedY[dy], projectedZ[dx], projectedZ[dz], projectedZ[dy], tile.triangleTextureId[i]);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!a", name = "a", descriptor = "([[F[[F[[II[[F[[B[[II[[B[[B[[B[[[B)[Lclient!hg;")
	public static GlTile[] buildGlShapedTiles(@OriginalArg(0) float[][] normalsX, @OriginalArg(1) float[][] normalsZ, @OriginalArg(2) int[][] heights, @OriginalArg(3) int level, @OriginalArg(4) float[][] normalsY, @OriginalArg(5) byte[][] rotations, @OriginalArg(6) int[][] tileColors, @OriginalArg(8) byte[][] shapes, @OriginalArg(9) byte[][] underlays, @OriginalArg(10) byte[][] overlays, @OriginalArg(11) byte[][][] renderFlags3D) {
		@Pc(10) HashTable tileTable = new HashTable(128);
		@Pc(12) int x;
		@Pc(17) int y;
		@Pc(30) int underlayId;
		@Pc(38) int overlayId;
		@Pc(131) int edgeS;
		@Pc(168) int idx;
		@Pc(143) int overlayN;
		@Pc(163) int overlayW;
		@Pc(153) int overlayS;
		@Pc(190) int overlayE;
		@Pc(180) int overlayNW;
		@Pc(214) int overlaySW;
		@Pc(202) int overlaySE;
		@Pc(226) int overlayNE;
		@Pc(274) byte neighborShape;
		@Pc(299) int edgeLookup2;
		@Pc(317) int k;
		@Pc(127) int neighborCount;
		@Pc(133) int edgeW;
		@Pc(777) int j;
		@Pc(1035) int n;
		@Pc(1055) boolean[] blendMaskN;
		@Pc(1068) boolean[] blendMaskS;
		@Pc(1086) boolean[] blendMaskW;
		for (x = 1; x <= 102; x++) {
			for (y = 1; y <= 102; y++) {
				underlayId = underlays[x][y] & 0xFF;
				overlayId = overlays[x][y] & 0xFF;
				if (overlayId != 0) {
					@Pc(50) FloType floType = FloTypeList.get(overlayId - 1);
					if (floType.baseColor == -1) {
						continue;
					}
					@Pc(61) GlTile glTile = getOrCreateGlTile(tileTable, floType);
					@Pc(67) byte shape = shapes[x][y];
					@Pc(71) int[] shapeVerts = UNDERLAY_SHAPE_VERTICES[shape];
					glTile.vertexCapacity += shapeVerts.length / 2;
					glTile.faceCapacity++;
					if (floType.blendTexture && underlayId != 0) {
						glTile.vertexCapacity += SHAPE_BLEND_VERTEX_COUNT[shape];
					}
				}
				if ((underlays[x][y] & 0xFF) != 0 || overlayId != 0 && shapes[x][y] == 0) {
					neighborCount = 0;
					@Pc(129) int edgeN = 0;
					edgeS = 0;
					edgeW = 0;
					overlayN = overlays[x][y + 1] & 0xFF;
					overlayS = overlays[x][y - 1] & 0xFF;
					overlayW = overlays[x - 1][y] & 0xFF;
					@Pc(166) int[] neighborIds = new int[8];
					idx = 0;
					overlayNW = overlays[x - 1][y + 1] & 0xFF;
					overlayE = overlays[x + 1][y] & 0xFF;
					overlaySE = overlays[x + 1][y - 1] & 0xFF;
					overlaySW = overlays[x - 1][y - 1] & 0xFF;
					overlayNE = overlays[x + 1][y + 1] & 0xFF;
					@Pc(242) FloType neighborFlo;
					@Pc(264) byte neighborRot;
					@Pc(287) int edgeLookup1;
					if (overlayNW != 0 && overlayId != overlayNW) {
						neighborFlo = FloTypeList.get(overlayNW - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							neighborRot = rotations[x - 1][y + 1];
							neighborShape = shapes[x - 1][y + 1];
							edgeLookup1 = overlayEdgeLookup[neighborShape * 4 + (neighborRot + 2 & 0x3)];
							edgeLookup2 = overlayEdgeLookup[(neighborRot + 3 & 0x3) + neighborShape * 4];
							if (!tileEdgeMasks[edgeLookup2][1] || !tileEdgeMasks[edgeLookup1][0]) {
								for (k = 0; k < 8; k++) {
									if (k == 0) {
										neighborCount++;
										neighborIds[0] = overlayNW;
										break;
									}
									if (overlayNW == neighborIds[k]) {
										break;
									}
								}
							}
						}
					}
					if (overlaySW != 0 && overlaySW != overlayId) {
						neighborFlo = FloTypeList.get(overlaySW - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							neighborRot = rotations[x - 1][y - 1];
							neighborShape = shapes[x - 1][y - 1];
							edgeLookup1 = overlayEdgeLookup[neighborShape * 4 + (neighborRot & 0x3)];
							edgeLookup2 = overlayEdgeLookup[(neighborRot + 3 & 0x3) + neighborShape * 4];
							if (!tileEdgeMasks[edgeLookup1][1] || !tileEdgeMasks[edgeLookup2][0]) {
								for (k = 0; k < 8; k++) {
									if (neighborCount == k) {
										neighborIds[neighborCount++] = overlaySW;
										break;
									}
									if (neighborIds[k] == overlaySW) {
										break;
									}
								}
							}
						}
					}
					if (overlaySE != 0 && overlayId != overlaySE) {
						neighborFlo = FloTypeList.get(overlaySE - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							neighborRot = rotations[x + 1][y - 1];
							neighborShape = shapes[x + 1][y - 1];
							edgeLookup2 = overlayEdgeLookup[neighborShape * 4 + (neighborRot + 1 & 0x3)];
							edgeLookup1 = overlayEdgeLookup[neighborShape * 4 + (neighborRot & 0x3)];
							if (!tileEdgeMasks[edgeLookup2][1] || !tileEdgeMasks[edgeLookup1][0]) {
								for (k = 0; k < 8; k++) {
									if (neighborCount == k) {
										neighborIds[neighborCount++] = overlaySE;
										break;
									}
									if (overlaySE == neighborIds[k]) {
										break;
									}
								}
							}
						}
					}
					if (overlayNE != 0 && overlayNE != overlayId) {
						neighborFlo = FloTypeList.get(overlayNE - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							neighborRot = rotations[x + 1][y + 1];
							neighborShape = shapes[x + 1][y + 1];
							edgeLookup2 = overlayEdgeLookup[neighborShape * 4 + (neighborRot + 1 & 0x3)];
							edgeLookup1 = overlayEdgeLookup[neighborShape * 4 + (neighborRot + 2 & 0x3)];
							if (!tileEdgeMasks[edgeLookup1][1] || !tileEdgeMasks[edgeLookup2][0]) {
								for (k = 0; k < 8; k++) {
									if (neighborCount == k) {
										neighborIds[neighborCount++] = overlayNE;
										break;
									}
									if (overlayNE == neighborIds[k]) {
										break;
									}
								}
							}
						}
					}
					if (overlayN != 0 && overlayId != overlayN) {
						neighborFlo = FloTypeList.get(overlayN - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							edgeN = overlayEdgeLookup[shapes[x][y + 1] * 4 + (rotations[x][y + 1] + 2 & 0x3)];
							for (j = 0; j < 8; j++) {
								if (neighborCount == j) {
									neighborIds[neighborCount++] = overlayN;
									break;
								}
								if (neighborIds[j] == overlayN) {
									break;
								}
							}
						}
					}
					if (overlayW != 0 && overlayId != overlayW) {
						neighborFlo = FloTypeList.get(overlayW - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							edgeW = overlayEdgeLookup[(rotations[x - 1][y] + 3 & 0x3) + shapes[x - 1][y] * 4];
							for (j = 0; j < 8; j++) {
								if (neighborCount == j) {
									neighborIds[neighborCount++] = overlayW;
									break;
								}
								if (overlayW == neighborIds[j]) {
									break;
								}
							}
						}
					}
					if (overlayS != 0 && overlayId != overlayS) {
						neighborFlo = FloTypeList.get(overlayS - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							edgeS = overlayEdgeLookup[(rotations[x][y - 1] & 0x3) + shapes[x][y - 1] * 4];
							for (j = 0; j < 8; j++) {
								if (j == neighborCount) {
									neighborIds[neighborCount++] = overlayS;
									break;
								}
								if (overlayS == neighborIds[j]) {
									break;
								}
							}
						}
					}
					if (overlayE != 0 && overlayId != overlayE) {
						neighborFlo = FloTypeList.get(overlayE - 1);
						if (neighborFlo.blendTexture && neighborFlo.baseColor != -1) {
							idx = overlayEdgeLookup[(rotations[x + 1][y] + 1 & 0x3) + shapes[x + 1][y] * 4];
							for (j = 0; j < 8; j++) {
								if (neighborCount == j) {
									neighborIds[neighborCount++] = overlayE;
									break;
								}
								if (neighborIds[j] == overlayE) {
									break;
								}
							}
						}
					}
					for (n = 0; n < neighborCount; n++) {
						j = neighborIds[n];
						blendMaskN = tileEdgeMasks[overlayW == j ? edgeW : 0];
						blendMaskS = tileEdgeMasks[j == overlayS ? edgeS : 0];
						@Pc(1077) boolean[] edgeMask = tileEdgeMasks[overlayN == j ? edgeN : 0];
						blendMaskW = tileEdgeMasks[overlayE == j ? idx : 0];
						@Pc(1092) FloType neighborFlo2 = FloTypeList.get(j - 1);
						@Pc(1097) GlTile neighborTile = getOrCreateGlTile(tileTable, neighborFlo2);
						neighborTile.vertexCapacity += 5;
						neighborTile.vertexCapacity += edgeMask.length - 2;
						neighborTile.vertexCapacity += blendMaskN.length - 2;
						neighborTile.vertexCapacity += blendMaskS.length - 2;
						neighborTile.vertexCapacity += blendMaskW.length - 2;
						neighborTile.faceCapacity++;
					}
				}
			}
		}
		@Pc(1161) GlTile iter;
		for (iter = (GlTile) tileTable.head(); iter != null; iter = (GlTile) tileTable.next()) {
			iter.allocateBuffers();
		}
		for (x = 1; x <= 102; x++) {
			for (y = 1; y <= 102; y++) {
				overlayId = underlays[x][y] & 0xFF;
				neighborCount = overlays[x][y] & 0xFF;
				if ((renderFlags3D[level][x][y] & 0x8) != 0) {
					underlayId = 0;
				} else if ((renderFlags3D[1][x][y] & 0x2) == 2 && level > 0) {
					underlayId = level - 1;
				} else {
					underlayId = level;
				}
				if (neighborCount != 0) {
					@Pc(1250) FloType floType2 = FloTypeList.get(neighborCount - 1);
					if (floType2.baseColor == -1) {
						continue;
					}
					@Pc(1261) GlTile glTile2 = getOrCreateGlTile(tileTable, floType2);
					@Pc(1267) byte shape2 = shapes[x][y];
					@Pc(1273) byte rotation2 = rotations[x][y];
					idx = getTexturedColor(floType2.texture, floType2.baseColor, tileColors[x][y]);
					overlayN = getTexturedColor(floType2.texture, floType2.baseColor, tileColors[x + 1][y]);
					overlayW = getTexturedColor(floType2.texture, floType2.baseColor, tileColors[x + 1][y + 1]);
					overlayS = getTexturedColor(floType2.texture, floType2.baseColor, tileColors[x][y + 1]);
					addGlPlainTile(idx, heights, normalsZ, x, normalsX, overlayN, rotation2, underlayId, overlayW, overlayId != 0 && floType2.blendTexture, shape2, y, normalsY, overlayS, glTile2);
				}
				if ((underlays[x][y] & 0xFF) != 0 || neighborCount != 0 && shapes[x][y] == 0) {
					@Pc(1382) int[] blendIds = new int[8];
					edgeW = 0;
					@Pc(1386) int blendCount = 0;
					edgeS = 0;
					idx = 0;
					overlayW = overlays[x][y + 1] & 0xFF;
					overlayN = 0;
					overlayS = overlays[x - 1][y] & 0xFF;
					overlayNW = overlays[x + 1][y] & 0xFF;
					overlayE = overlays[x][y - 1] & 0xFF;
					overlaySW = overlays[x - 1][y + 1] & 0xFF;
					overlaySE = overlays[x - 1][y - 1] & 0xFF;
					overlayNE = overlays[x + 1][y - 1] & 0xFF;
					n = overlays[x + 1][y + 1] & 0xFF;
					@Pc(1527) byte blendShape;
					@Pc(1496) FloType blendFlo;
					@Pc(1571) int blendEdge;
					if (overlaySW == 0 || overlaySW == neighborCount) {
						overlaySW = 0;
					} else {
						blendFlo = FloTypeList.get(overlaySW - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							neighborShape = rotations[x - 1][y + 1];
							blendShape = shapes[x - 1][y + 1];
							edgeLookup2 = overlayEdgeLookup[blendShape * 4 + (neighborShape + 2 & 0x3)];
							k = overlayEdgeLookup[blendShape * 4 + (neighborShape + 3 & 0x3)];
							if (tileEdgeMasks[k][1] && tileEdgeMasks[edgeLookup2][0]) {
								overlaySW = 0;
							} else {
								for (blendEdge = 0; blendEdge < 8; blendEdge++) {
									if (blendEdge == 0) {
										blendCount++;
										blendIds[0] = overlaySW;
										break;
									}
									if (blendIds[blendEdge] == overlaySW) {
										break;
									}
								}
							}
						} else {
							overlaySW = 0;
						}
					}
					if (overlaySE == 0 || neighborCount == overlaySE) {
						overlaySE = 0;
					} else {
						blendFlo = FloTypeList.get(overlaySE - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							neighborShape = rotations[x - 1][y - 1];
							blendShape = shapes[x - 1][y - 1];
							edgeLookup2 = overlayEdgeLookup[(neighborShape & 0x3) + blendShape * 4];
							k = overlayEdgeLookup[(neighborShape + 3 & 0x3) + blendShape * 4];
							if (tileEdgeMasks[edgeLookup2][1] && tileEdgeMasks[k][0]) {
								overlaySE = 0;
							} else {
								for (blendEdge = 0; blendEdge < 8; blendEdge++) {
									if (blendEdge == blendCount) {
										blendIds[blendCount++] = overlaySE;
										break;
									}
									if (overlaySE == blendIds[blendEdge]) {
										break;
									}
								}
							}
						} else {
							overlaySE = 0;
						}
					}
					if (overlayNE == 0 || overlayNE == neighborCount) {
						overlayNE = 0;
					} else {
						blendFlo = FloTypeList.get(overlayNE - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							neighborShape = rotations[x + 1][y - 1];
							blendShape = shapes[x + 1][y - 1];
							k = overlayEdgeLookup[(neighborShape + 1 & 0x3) + blendShape * 4];
							edgeLookup2 = overlayEdgeLookup[blendShape * 4 + (neighborShape & 0x3)];
							if (tileEdgeMasks[k][1] && tileEdgeMasks[edgeLookup2][0]) {
								overlayNE = 0;
							} else {
								for (blendEdge = 0; blendEdge < 8; blendEdge++) {
									if (blendEdge == blendCount) {
										blendIds[blendCount++] = overlayNE;
										break;
									}
									if (blendIds[blendEdge] == overlayNE) {
										break;
									}
								}
							}
						} else {
							overlayNE = 0;
						}
					}
					if (n == 0 || neighborCount == n) {
						n = 0;
					} else {
						blendFlo = FloTypeList.get(n - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							blendShape = shapes[x + 1][y + 1];
							neighborShape = rotations[x + 1][y + 1];
							edgeLookup2 = overlayEdgeLookup[(neighborShape + 2 & 0x3) + blendShape * 4];
							k = overlayEdgeLookup[(neighborShape + 1 & 0x3) + blendShape * 4];
							if (tileEdgeMasks[edgeLookup2][1] && tileEdgeMasks[k][0]) {
								n = 0;
							} else {
								for (blendEdge = 0; blendEdge < 8; blendEdge++) {
									if (blendEdge == blendCount) {
										blendIds[blendCount++] = n;
										break;
									}
									if (blendIds[blendEdge] == n) {
										break;
									}
								}
							}
						} else {
							n = 0;
						}
					}
					@Pc(2003) int m;
					if (overlayW != 0 && overlayW != neighborCount) {
						blendFlo = FloTypeList.get(overlayW - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							edgeW = overlayEdgeLookup[shapes[x][y + 1] * 4 + (rotations[x][y + 1] + 2 & 0x3)];
							for (m = 0; m < 8; m++) {
								if (m == blendCount) {
									blendIds[blendCount++] = overlayW;
									break;
								}
								if (blendIds[m] == overlayW) {
									break;
								}
							}
						}
					}
					if (overlayS != 0 && neighborCount != overlayS) {
						blendFlo = FloTypeList.get(overlayS - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							edgeS = overlayEdgeLookup[(rotations[x - 1][y] + 3 & 0x3) + shapes[x - 1][y] * 4];
							for (m = 0; m < 8; m++) {
								if (blendCount == m) {
									blendIds[blendCount++] = overlayS;
									break;
								}
								if (overlayS == blendIds[m]) {
									break;
								}
							}
						}
					}
					if (overlayE != 0 && overlayE != neighborCount) {
						blendFlo = FloTypeList.get(overlayE - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							idx = overlayEdgeLookup[(rotations[x][y - 1] & 0x3) + shapes[x][y - 1] * 4];
							for (m = 0; m < 8; m++) {
								if (blendCount == m) {
									blendIds[blendCount++] = overlayE;
									break;
								}
								if (overlayE == blendIds[m]) {
									break;
								}
							}
						}
					}
					if (overlayNW != 0 && overlayNW != neighborCount) {
						blendFlo = FloTypeList.get(overlayNW - 1);
						if (blendFlo.blendTexture && blendFlo.baseColor != -1) {
							overlayN = overlayEdgeLookup[shapes[x + 1][y] * 4 + (rotations[x + 1][y] + 1 & 0x3)];
							for (m = 0; m < 8; m++) {
								if (m == blendCount) {
									blendIds[blendCount++] = overlayNW;
									break;
								}
								if (blendIds[m] == overlayNW) {
									break;
								}
							}
						}
					}
					for (j = 0; j < blendCount; j++) {
						m = blendIds[j];
						blendMaskN = tileEdgeMasks[m == overlayW ? edgeW : 0];
						blendMaskS = tileEdgeMasks[overlayS == m ? edgeS : 0];
						blendMaskW = tileEdgeMasks[m == overlayE ? idx : 0];
						@Pc(2318) boolean[] blendMaskE = tileEdgeMasks[m == overlayNW ? overlayN : 0];
						@Pc(2324) FloType blendFloType = FloTypeList.get(m - 1);
						@Pc(2329) GlTile blendTile = getOrCreateGlTile(tileTable, blendFloType);
						@Pc(2345) int blendColorSW = getTexturedColor(blendFloType.texture, blendFloType.baseColor, tileColors[x][y]) << 8 | 0xFF;
						@Pc(2365) int blendColorSE = getTexturedColor(blendFloType.texture, blendFloType.baseColor, tileColors[x + 1][y]) << 8 | 0xFF;
						@Pc(2385) int blendColorNE = getTexturedColor(blendFloType.texture, blendFloType.baseColor, tileColors[x + 1][y + 1]) << 8 | 0xFF;
						@Pc(2403) int blendColorNW = getTexturedColor(blendFloType.texture, blendFloType.baseColor, tileColors[x][y + 1]) << 8 | 0xFF;
						@Pc(2422) boolean excludeSW = m != overlaySE && blendMaskW[0] && blendMaskS[1];
						@Pc(2441) boolean excludeNE = m != n && blendMaskN[0] && blendMaskE[1];
						@Pc(2456) boolean excludeNW = overlaySW != m && blendMaskS[0] && blendMaskN[1];
						@Pc(2463) int totalVerts1 = blendMaskN.length + 6 - 2;
						@Pc(2482) boolean excludeSE = m != overlayNE && blendMaskE[0] && blendMaskW[1];
						@Pc(2489) int totalVerts2 = totalVerts1 + blendMaskS.length - 2;
						@Pc(2496) int totalVerts3 = totalVerts2 + blendMaskW.length - 2;
						@Pc(2503) int totalVerts = totalVerts3 + blendMaskE.length - 2;
						@Pc(2524) int centerVtx = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, true, blendTile, normalsZ, y, 64, normalsX, 64);
						@Pc(2527) int[] fanIndices = new int[totalVerts];
						@Pc(2529) byte pos = 0;
						@Pc(2550) int vtxNW = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, excludeNW, blendTile, normalsZ, y, 0, normalsX, 128);
						@Pc(2571) int vtxNE = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, excludeNE, blendTile, normalsZ, y, 128, normalsX, 128);
						@Pc(2592) int vtxSW = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, excludeSW, blendTile, normalsZ, y, 0, normalsX, 0);
						@Pc(2613) int vtxSE = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, excludeSE, blendTile, normalsZ, y, 128, normalsX, 0);
						@Pc(2616) int fanIdx = pos + 1;
						fanIndices[0] = centerVtx;
						@Pc(2621) int fanPos = fanIdx + 1;
						fanIndices[1] = vtxNE;
						if (blendMaskN.length > 2) {
							fanPos++;
							fanIndices[2] = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, blendMaskN[2], blendTile, normalsZ, y, 64, normalsX, 128);
						}
						fanIndices[fanPos++] = vtxNW;
						if (blendMaskS.length > 2) {
							fanIndices[fanPos++] = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, blendMaskS[2], blendTile, normalsZ, y, 0, normalsX, 64);
						}
						fanIndices[fanPos++] = vtxSW;
						if (blendMaskW.length > 2) {
							fanIndices[fanPos++] = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, blendMaskW[2], blendTile, normalsZ, y, 64, normalsX, 0);
						}
						fanIndices[fanPos++] = vtxSE;
						if (blendMaskE.length > 2) {
							fanIndices[fanPos++] = computeGlTileVertex(blendColorNW, 0.0F, blendColorSW, blendColorSE, null, heights, x, normalsY, blendColorNE, 0, blendMaskE[2], blendTile, normalsZ, y, 128, normalsX, 64);
						}
						fanIndices[fanPos++] = vtxNE;
						blendTile.addFace(underlayId, x, y, fanIndices, null, true);
					}
				}
			}
		}
		for (iter = (GlTile) tileTable.head(); iter != null; iter = (GlTile) tileTable.next()) {
			if (iter.vertexCount == 0) {
				iter.unlink();
			} else {
				iter.uploadVertexData();
			}
		}
		x = tileTable.size();
		@Pc(2823) GlTile[] result = new GlTile[x];
		@Pc(2826) long[] keys = new long[x];
		tileTable.toArray(result);
		for (overlayId = 0; overlayId < x; overlayId++) {
			keys[overlayId] = result[overlayId].key;
		}
		ArrayUtils.sort(keys, result);
		return result;
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(Lclient!sc;ZLclient!wl;)Lclient!hg;")
	public static GlTile getOrCreateGlTile(@OriginalArg(0) HashTable table, @OriginalArg(2) FloType floType) {
		@Pc(23) long hashKey = ((long) floType.texture + 1 << 16) + floType.textureScale + ((long) floType.textureBrightness << 56) + ((long) floType.waterColor << 32);
		@Pc(38) GlTile tile = (GlTile) table.get(hashKey);
		if (tile == null) {
			tile = new GlTile(floType.texture, (float) floType.textureScale, true, false, floType.waterColor);
			table.put(tile, hashKey);
		}
		return tile;
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IIBI)I")
	public static int getTexturedColor(@OriginalArg(0) int texture, @OriginalArg(1) int color, @OriginalArg(3) int lightness) {
		@Pc(19) int rgb = Rasteriser.palette[ColorUtils.multiplyLightness2(color, lightness)];
		if (texture > 0) {
			@Pc(31) int brightness = Rasteriser.textureProvider.getTextureBrightness(texture & 0xFFFF);
			@Pc(49) int grayscale;
			@Pc(73) int green;
			if (brightness != 0) {
				if (lightness < 0) {
					grayscale = 0;
				} else if (lightness > 127) {
					grayscale = 16777215;
				} else {
					grayscale = lightness * 131586;
				}
				if (brightness == 256) {
					rgb = grayscale;
				} else {
					green = 256 - brightness;
					rgb = ((grayscale & 0xFF00) * brightness + green * (rgb & 0xFF00) & 0xFF0000) + (brightness * (grayscale & 0xFF00FF) + ((rgb & 0xFF00FF) * green) & 0xFF00FF00) >> 8;
				}
			}
			grayscale = Rasteriser.textureProvider.getTextureSpeed(texture & 0xFFFF);
			if (grayscale != 0) {
				grayscale += 256;
				@Pc(125) int red = (rgb >> 16 & 0xFF) * grayscale;
				if (red > 65535) {
					red = 65535;
				}
				green = (rgb >> 8 & 0xFF) * grayscale;
				if (green > 65535) {
					green = 65535;
				}
				@Pc(150) int blue = grayscale * (rgb & 0xFF);
				if (blue > 65535) {
					blue = 65535;
				}
				rgb = (blue >> 8) + (green & 0xFF00) + (red << 8 & 0xFF001F);
			}
		}
		return rgb;
	}

	@OriginalMember(owner = "client!eh", name = "a", descriptor = "(I[[I[[FI[[FIBIIBZBI[[FILclient!hg;)V")
	public static void addGlPlainTile(@OriginalArg(0) int colorSW, @OriginalArg(1) int[][] heights, @OriginalArg(2) float[][] normalsX, @OriginalArg(3) int x, @OriginalArg(4) float[][] normalsZ, @OriginalArg(5) int colorSE, @OriginalArg(6) byte rotation, @OriginalArg(7) int y, @OriginalArg(8) int colorNE, @OriginalArg(10) boolean blendTexture, @OriginalArg(11) byte shape, @OriginalArg(12) int colorNW, @OriginalArg(13) float[][] normalsY, @OriginalArg(14) int colorBase, @OriginalArg(15) GlTile glTile) {
		@Pc(11) int packedSW = (colorSW << 8) + 255;
		@Pc(17) int packedSE = (colorSE << 8) + 255;
		@Pc(23) int packedNE = (colorNE << 8) + 255;
		@Pc(29) int packedNW = (colorBase << 8) + 255;
		@Pc(33) int[] shapeVerts = UNDERLAY_SHAPE_VERTICES[shape];
		@Pc(39) int[] vertIndices = new int[shapeVerts.length >> 1];
		@Pc(41) int idx;
		for (idx = 0; idx < vertIndices.length; idx++) {
			vertIndices[idx] = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, false, glTile, normalsX, colorNW, shapeVerts[idx + idx], normalsZ, shapeVerts[idx + idx + 1]);
		}
		@Pc(87) int[] blendIndices = null;
		if (blendTexture) {
			@Pc(191) int vertex2;
			if (shape == 1) {
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 64, normalsZ, 128);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 64);
				blendIndices = new int[]{vertex2, idx, vertIndices[2], idx, vertIndices[0], vertIndices[2]};
			} else if (shape == 2) {
				blendIndices = new int[6];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 128);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 64, normalsZ, 0);
				blendIndices[2] = idx;
				blendIndices[0] = vertIndices[0];
				blendIndices[5] = vertIndices[0];
				blendIndices[3] = idx;
				blendIndices[1] = vertex2;
				blendIndices[4] = vertIndices[1];
			} else if (shape == 3) {
				blendIndices = new int[6];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 128);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 64, normalsZ, 0);
				blendIndices[4] = vertex2;
				blendIndices[1] = vertIndices[1];
				blendIndices[0] = vertIndices[2];
				blendIndices[3] = idx;
				blendIndices[2] = idx;
				blendIndices[5] = vertIndices[2];
			} else if (shape == 4) {
				blendIndices = new int[3];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 128);
				blendIndices[0] = vertIndices[3];
				blendIndices[2] = vertIndices[0];
				blendIndices[1] = idx;
			} else if (shape == 5) {
				blendIndices = new int[3];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 128);
				blendIndices[1] = idx;
				blendIndices[0] = vertIndices[2];
				blendIndices[2] = vertIndices[3];
			} else if (shape == 6) {
				blendIndices = new int[6];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 0);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 128);
				blendIndices[1] = idx;
				blendIndices[0] = vertIndices[3];
				blendIndices[2] = vertex2;
				blendIndices[4] = vertIndices[0];
				blendIndices[3] = vertex2;
				blendIndices[5] = vertIndices[3];
			} else if (shape == 7) {
				blendIndices = new int[6];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 128);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 0);
				blendIndices[3] = idx;
				blendIndices[2] = idx;
				blendIndices[0] = vertIndices[1];
				blendIndices[4] = vertIndices[2];
				blendIndices[1] = vertex2;
				blendIndices[5] = vertIndices[1];
			} else if (shape == 8) {
				blendIndices = new int[3];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 0);
				blendIndices[2] = vertIndices[4];
				blendIndices[0] = vertIndices[3];
				blendIndices[1] = idx;
			} else if (shape == 9) {
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 64);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 96, normalsZ, 32);
				@Pc(715) int vertex3 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 64, normalsZ, 0);
				blendIndices = new int[]{vertex2, idx, vertIndices[4], vertex2, vertIndices[4], vertIndices[3], vertex2, vertIndices[3], vertIndices[2], vertex2, vertIndices[2], vertIndices[1], vertex2, vertIndices[1], vertex3};
			} else if (shape == 10) {
				blendIndices = new int[9];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 128);
				blendIndices[0] = vertIndices[2];
				blendIndices[8] = vertIndices[0];
				blendIndices[1] = idx;
				blendIndices[4] = idx;
				blendIndices[2] = vertIndices[3];
				blendIndices[7] = idx;
				blendIndices[3] = vertIndices[3];
				blendIndices[5] = vertIndices[4];
				blendIndices[6] = vertIndices[4];
			} else if (shape == 11) {
				blendIndices = new int[12];
				idx = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 0, normalsZ, 64);
				vertex2 = computeGlTileVertex(packedNW, 0.0F, packedSW, packedSE, null, heights, x, normalsY, packedNE, rotation, true, glTile, normalsX, colorNW, 128, normalsZ, 64);
				blendIndices[5] = idx;
				blendIndices[1] = idx;
				blendIndices[8] = idx;
				blendIndices[0] = vertIndices[3];
				blendIndices[2] = vertIndices[0];
				blendIndices[11] = vertex2;
				blendIndices[6] = vertIndices[2];
				blendIndices[7] = vertex2;
				blendIndices[10] = vertIndices[1];
				blendIndices[3] = vertIndices[3];
				blendIndices[4] = vertIndices[2];
				blendIndices[9] = vertIndices[2];
			}
		}
		glTile.addFace(y, x, colorNW, vertIndices, blendIndices, false);
	}

	@OriginalMember(owner = "client!ql", name = "a", descriptor = "(IFII[[I[[II[[FIBIZLclient!hg;[[FII[[FI)I")
	public static int computeGlTileVertex(@OriginalArg(0) int colorNW, @OriginalArg(1) float depthScale, @OriginalArg(2) int colorSW, @OriginalArg(3) int colorSE, @OriginalArg(4) int[][] underwaterHeights, @OriginalArg(5) int[][] heights, @OriginalArg(6) int x, @OriginalArg(7) float[][] normalsY, @OriginalArg(8) int colorNE, @OriginalArg(10) int rotation, @OriginalArg(11) boolean isBlendVertex, @OriginalArg(12) GlTile glTile, @OriginalArg(13) float[][] normalsX, @OriginalArg(14) int y, @OriginalArg(15) int vx, @OriginalArg(16) float[][] normalsZ, @OriginalArg(17) int vy) {
		@Pc(20) int tmp;
		if (rotation == 1) {
			tmp = vx;
			vx = vy;
			vy = 128 - tmp;
		} else if (rotation == 2) {
			vy = 128 - vy;
			vx = 128 - vx;
		} else if (rotation == 3) {
			tmp = vx;
			vx = 128 - vy;
			vy = tmp;
		}
		@Pc(66) float normalZ;
		@Pc(72) float normalY;
		@Pc(80) int vertexColor;
		@Pc(78) float normalX;
		if (vx == 0 && vy == 0) {
			normalZ = normalsZ[x][y];
			normalY = normalsY[x][y];
			normalX = normalsX[x][y];
			vertexColor = colorSW;
		} else if (vx == 128 && vy == 0) {
			vertexColor = colorSE;
			normalZ = normalsZ[x + 1][y];
			normalY = normalsY[x + 1][y];
			normalX = normalsX[x + 1][y];
		} else if (vx == 128 && vy == 128) {
			normalY = normalsY[x + 1][y + 1];
			normalX = normalsX[x + 1][y + 1];
			normalZ = normalsZ[x + 1][y + 1];
			vertexColor = colorNE;
		} else if (vx == 0 && vy == 128) {
			normalY = normalsY[x][y + 1];
			normalZ = normalsZ[x][y + 1];
			normalX = normalsX[x][y + 1];
			vertexColor = colorNW;
		} else {
			normalX = normalsX[x][y];
			normalZ = normalsZ[x][y];
			@Pc(219) float yFrac = (float) vy / 128.0F;
			@Pc(224) float xFrac = (float) vx / 128.0F;
			@Pc(238) float lerpNX = normalX + (normalsX[x + 1][y] - normalX) * xFrac;
			@Pc(253) float lerpNZ = normalZ + (normalsZ[x + 1][y] - normalZ) * xFrac;
			@Pc(261) float nx01 = normalsX[x][y + 1];
			@Pc(269) float nz01 = normalsZ[x][y + 1];
			@Pc(286) float lerpNZNext = nz01 + (normalsZ[x + 1][y + 1] - nz01) * xFrac;
			normalY = normalsY[x][y];
			normalZ = lerpNZ + yFrac * (lerpNZNext - lerpNZ);
			@Pc(309) float ny01 = normalsY[x][y + 1];
			@Pc(326) float lerpNXNext = nx01 + (normalsX[x + 1][y + 1] - nx01) * xFrac;
			normalX = lerpNX + yFrac * (lerpNXNext - lerpNX);
			@Pc(352) float lerpNYNext = ny01 + (normalsY[x + 1][y + 1] - ny01) * xFrac;
			@Pc(367) float lerpNY = normalY + (normalsY[x + 1][y] - normalY) * xFrac;
			normalY = lerpNY + (lerpNYNext - lerpNY) * yFrac;
			@Pc(382) int smoothA = getSmoothedHeight(vx, colorSW, colorSE);
			@Pc(388) int smoothB = getSmoothedHeight(vx, colorNW, colorNE);
			vertexColor = getSmoothedHeight(vy, smoothA, smoothB);
		}
		@Pc(405) int yFine = vy + (y << 7);
		@Pc(413) int height = interpolateHeight(vx, y, heights, x, vy);
		@Pc(420) int xFine = (x << 7) + vx;
		return glTile.addVertex(xFine, height, yFine, normalX, normalZ, normalY, isBlendVertex ? vertexColor & 0xFFFFFF00 : vertexColor, underwaterHeights == null ? 0.0F : (float) (height - interpolateHeight(vx, y, underwaterHeights, x, vy)) / depthScale);
	}

	@OriginalMember(owner = "client!oj", name = "a", descriptor = "(IBI[[III)I")
	public static int interpolateHeight(@OriginalArg(0) int xFrac, @OriginalArg(2) int y, @OriginalArg(3) int[][] heights, @OriginalArg(4) int x, @OriginalArg(5) int yFrac) {
		@Pc(25) int xLerp = xFrac * heights[x + 1][y] + (128 - xFrac) * heights[x][y] >> 7;
		@Pc(52) int xLerpNext = heights[x][y + 1] * (128 - xFrac) + heights[x + 1][y + 1] * xFrac >> 7;
		return xLerp * (128 - yFrac) + yFrac * xLerpNext >> 7;
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(IIII)I")
	public static int getSmoothedHeight(@OriginalArg(1) int frac, @OriginalArg(2) int colorA, @OriginalArg(3) int colorB) {
		if (colorA == colorB) {
			return colorA;
		} else {
			@Pc(17) int invFrac = 128 - frac;
			@Pc(50) int highBits = frac * (colorB >>> 7 & 0x1FE01FE) + invFrac * (colorA >>> 7 & 0x1FE01FE) & 0xFF00FF00;
			@Pc(65) int lowBits = invFrac * (colorA & 0xFF00FF) + (colorB & 0xFF00FF) * frac & 0xFF00FF00;
			return highBits + (lowBits >> 7);
		}
	}

	@OriginalMember(owner = "client!bm", name = "a", descriptor = "(IBIIII)V")
	public static void clearTerrainRegion(@OriginalArg(0) int level, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int width, @OriginalArg(5) int height) {
		@Pc(3) int i;
		@Pc(10) int j;
		for (i = x; i <= width + x; i++) {
			for (j = y; j <= height + y; j++) {
				if (j >= 0 && j < 104 && i >= 0 && i < 104) {
					shadowmap[level][j][i] = 127;
				}
			}
		}
		for (i = x; i < width + x; i++) {
			for (j = y; j < y + height; j++) {
				if (j >= 0 && j < 104 && i >= 0 && i < 104) {
					tileHeights[level][j][i] = level <= 0 ? 0 : tileHeights[level - 1][j][i];
				}
			}
		}
		if (y > 0 && y < 104) {
			for (i = x + 1; i < x + width; i++) {
				if (i >= 0 && i < 104) {
					tileHeights[level][y][i] = tileHeights[level][y - 1][i];
				}
			}
		}
		if (x > 0 && x < 104) {
			for (i = y + 1; i < y + height; i++) {
				if (i >= 0 && i < 104) {
					tileHeights[level][i][x] = tileHeights[level][i][x - 1];
				}
			}
		}
		if (y < 0 || x < 0 || y >= 104 || x >= 104) {
			return;
		}
		if (level == 0) {
			if (y > 0 && tileHeights[level][y - 1][x] != 0) {
				tileHeights[level][y][x] = tileHeights[level][y - 1][x];
			} else if (x > 0 && tileHeights[level][y][x - 1] != 0) {
				tileHeights[level][y][x] = tileHeights[level][y][x - 1];
			} else if (y > 0 && x > 0 && tileHeights[level][y - 1][x - 1] != 0) {
				tileHeights[level][y][x] = tileHeights[level][y - 1][x - 1];
			}
		} else if (y > 0 && tileHeights[level - 1][y - 1][x] != tileHeights[level][y - 1][x]) {
			tileHeights[level][y][x] = tileHeights[level][y - 1][x];
		} else if (x > 0 && tileHeights[level][y][x - 1] != tileHeights[level - 1][y][x - 1]) {
			tileHeights[level][y][x] = tileHeights[level][y][x - 1];
		} else if (y > 0 && x > 0 && tileHeights[level][y - 1][x - 1] != tileHeights[level - 1][y - 1][x - 1]) {
			tileHeights[level][y][x] = tileHeights[level][y - 1][x - 1];
		}
	}

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "([Lclient!mj;ZIIIII[B)V")
	public static void readTerrain(@OriginalArg(0) CollisionMap[] collisionMaps, @OriginalArg(1) boolean underwater, @OriginalArg(2) int baseX, @OriginalArg(3) int baseY, @OriginalArg(5) int tileX, @OriginalArg(6) int tileY, @OriginalArg(7) byte[] src) {
		@Pc(14) int x;
		@Pc(21) int y;
		if (!underwater) {
			for (@Pc(9) int level = 0; level < 4; level++) {
				for (x = 0; x < 64; x++) {
					for (y = 0; y < 64; y++) {
						if (tileX + x > 0 && x + tileX < 103 && baseY + y > 0 && y + baseY < 103) {
							collisionMaps[level].flags[x + tileX][baseY + y] &= 0xFEFFFFFF;
						}
					}
				}
			}
		}
		@Pc(95) Buffer buffer = new Buffer(src);
		@Pc(99) byte levelCount;
		if (underwater) {
			levelCount = 1;
		} else {
			levelCount = 4;
		}
		@Pc(117) int z;
		for (x = 0; x < levelCount; x++) {
			for (y = 0; y < 64; y++) {
				for (z = 0; z < 64; z++) {
					readTile(baseX, tileY, underwater, buffer, z + baseY, tileX + y, 0, x);
				}
			}
		}
		@Pc(146) boolean hasOcclusion = false;
		@Pc(243) int startX;
		@Pc(188) int endX;
		@Pc(190) int startY;
		@Pc(194) int endY;
		while (buffer.offset < buffer.data.length) {
			y = buffer.g1();
			if (y != 129) {
				buffer.offset--;
				break;
			}
			for (z = 0; z < 4; z++) {
				@Pc(168) byte occlusionType = buffer.g1b();
				if (occlusionType == 0) {
					startX = tileX;
					if (tileX < 0) {
						startX = 0;
					} else if (tileX >= 104) {
						startX = 104;
					}
					startY = baseY;
					if (baseY < 0) {
						startY = 0;
					} else if (baseY >= 104) {
						startY = 104;
					}
					endX = tileX + 64;
					endY = baseY + 64;
					if (endY < 0) {
						endY = 0;
					} else if (endY >= 104) {
						endY = 104;
					}
					if (endX < 0) {
						endX = 0;
					} else if (endX >= 104) {
						endX = 104;
					}
					while (startX < endX) {
						while (startY < endY) {
							occlusionHeights[z][startX][startY] = 0;
							startY++;
						}
						startX++;
					}
				} else if (occlusionType == 1) {
					for (startX = 0; startX < 64; startX += 4) {
						for (endX = 0; endX < 64; endX += 4) {
							@Pc(305) byte heightVal = buffer.g1b();
							for (endY = startX + tileX; endY < tileX + startX + 4; endY++) {
								for (@Pc(320) int py = baseY + endX; py < baseY + endX + 4; py++) {
									if (endY >= 0 && endY < 104 && py >= 0 && py < 104) {
										occlusionHeights[z][endY][py] = heightVal;
									}
								}
							}
						}
					}
				} else if (occlusionType == 2 && z > 0) {
					endX = tileX + 64;
					startY = baseY;
					endY = baseY + 64;
					if (endX < 0) {
						endX = 0;
					} else if (endX >= 104) {
						endX = 104;
					}
					if (baseY < 0) {
						startY = 0;
					} else if (baseY >= 104) {
						startY = 104;
					}
					if (endY < 0) {
						endY = 0;
					} else if (endY >= 104) {
						endY = 104;
					}
					startX = tileX;
					if (tileX < 0) {
						startX = 0;
					} else if (tileX >= 104) {
						startX = 104;
					}
					while (endX > startX) {
						while (startY < endY) {
							occlusionHeights[z][startX][startY] = occlusionHeights[z - 1][startX][startY];
							startY++;
						}
						startX++;
					}
				}
			}
			hasOcclusion = true;
		}
		@Pc(515) int count;
		if (GlRenderer.enabled && !underwater) {
			@Pc(490) Environment environment = null;
			parseEnvironment:
			while (true) {
				readEntry:
				do {
					while (buffer.offset < buffer.data.length) {
						z = buffer.g1();
						if (z != 0) {
							if (z != 1) {
								throw new IllegalStateException();
							}
							count = buffer.g1();
							continue readEntry;
						}
						environment = new Environment(buffer);
					}
					if (environment == null) {
						environment = new Environment();
					}
					for (z = 0; z < 8; z++) {
						for (count = 0; count < 8; count++) {
							startX = z + (tileX >> 3);
							endX = (baseY >> 3) + count;
							if (startX >= 0 && startX < 13 && endX >= 0 && endX < 13) {
								FogManager.chunksAtmosphere[startX][endX] = environment;
							}
						}
					}
					break parseEnvironment;
				} while (count <= 0);
				int regionX = (tileX + Camera.originX) >> 6;
				int regionY = (baseY + Camera.originY) >> 6;
				int regionId = (regionX << 8) + regionY;
				for (startX = 0; startX < count; startX++) {
					@Pc(529) Light lightObj = new Light(buffer);
					if (lightObj.animationPreset == 31) {
						@Pc(541) LightType lightType = LightTypeList.get(buffer.g2());
						lightObj.setFlickerParams(lightType.flickerType, lightType.flickerSpeed, lightType.alphaMin, lightType.alphaMax);
					}
					lightObj.matchesStaticLightOverride = LightingManager.matchesStaticLightOverride(regionId, lightObj.x, lightObj.y, lightObj.level);
					lightObj.y += baseY << 7;
					lightObj.x += tileX << 7;
					endY = lightObj.y >> 7;
					startY = lightObj.x >> 7;
					if (startY >= 0 && endY >= 0 && startY < 104 && endY < 104) {
						lightObj.onBridge = (renderFlags[1][startY][endY] & 0x2) != 0;
						lightObj.y = tileHeights[lightObj.level][startY][endY] - lightObj.y;
						LightingManager.addLight(lightObj);
					}
				}
			}
		}
		if (hasOcclusion) {
			return;
		}
		for (y = 0; y < 4; y++) {
			for (z = 0; z < 16; z++) {
				for (count = 0; count < 16; count++) {
					startX = (tileX >> 2) + z;
					endX = count + (baseY >> 2);
					if (startX >= 0 && startX < 26 && endX >= 0 && endX < 26) {
						occlusionHeights[y][startX][endX] = 0;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!tm", name = "a", descriptor = "(III[Lclient!mj;IB[BIIIZ)V")
	public static void readDynamicTerrain(@OriginalArg(0) int rotation, @OriginalArg(1) int tileX, @OriginalArg(2) int level, @OriginalArg(3) CollisionMap[] collisionMaps, @OriginalArg(4) int tileY, @OriginalArg(6) byte[] src, @OriginalArg(7) int srcLevel, @OriginalArg(8) int srcChunkY, @OriginalArg(9) int srcChunkX, @OriginalArg(10) boolean underwater) {
		@Pc(17) int j;
		if (!underwater) {
			for (@Pc(10) int i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					if (tileX + i > 0 && i + tileX < 103 && j + tileY > 0 && tileY + j < 103) {
						collisionMaps[level].flags[i + tileX][j + tileY] &= 0xFEFFFFFF;
					}
				}
			}
		}
		@Pc(87) byte levelCount;
		if (underwater) {
			levelCount = 1;
		} else {
			levelCount = 4;
		}
		@Pc(96) Buffer buffer = new Buffer(src);
		@Pc(103) int k;
		@Pc(108) int l;
		for (j = 0; j < levelCount; j++) {
			for (k = 0; k < 64; k++) {
				for (l = 0; l < 64; l++) {
					if (srcLevel == j && srcChunkX <= k && srcChunkX + 8 > k && srcChunkY <= l && l < srcChunkY + 8) {
						readTile(0, 0, underwater, buffer, rotateY(rotation, k & 0x7, l & 0x7) + tileY, rotateX(rotation, l & 0x7, k & 0x7) + tileX, rotation, level);
					} else {
						readTile(0, 0, underwater, buffer, -1, -1, 0, 0);
					}
				}
			}
		}
		@Pc(232) int ix;
		@Pc(417) int tx;
		@Pc(255) int px;
		@Pc(266) int py;
		@Pc(316) int rx;
		while (buffer.data.length > buffer.offset) {
			k = buffer.g1();
			if (k != 129) {
				buffer.offset--;
				break;
			}
			for (l = 0; l < 4; l++) {
				@Pc(223) byte occType = buffer.g1b();
				@Pc(237) int endX;
				if (occType == 0) {
					if (l <= srcLevel) {
						endX = tileX + 7;
						ix = tileX;
						px = tileY + 7;
						if (px < 0) {
							px = 0;
						} else if (px >= 104) {
							px = 104;
						}
						if (endX < 0) {
							endX = 0;
						} else if (endX >= 104) {
							endX = 104;
						}
						tx = tileY;
						if (tileY < 0) {
							tx = 0;
						} else if (tileY >= 104) {
							tx = 104;
						}
						if (tileX < 0) {
							ix = 0;
						} else if (tileX >= 104) {
							ix = 104;
						}
						while (endX > ix) {
							while (tx < px) {
								occlusionHeights[level][ix][tx] = 0;
								tx++;
							}
							ix++;
						}
					}
				} else if (occType == 1) {
					for (ix = 0; ix < 64; ix += 4) {
						for (endX = 0; endX < 64; endX += 4) {
							@Pc(246) byte occHeight = buffer.g1b();
							if (l <= srcLevel) {
								for (px = ix; px < ix + 4; px++) {
									for (py = endX; py < endX + 4; py++) {
										if (px >= srcChunkX && px < srcChunkX + 8 && py >= srcChunkY && srcChunkY + 8 > srcChunkY) {
											rx = tileX + rotateX(rotation, py & 0x7, px & 0x7);
											@Pc(328) int ry = rotateY(rotation, px & 0x7, py & 0x7) + tileY;
											if (rx >= 0 && rx < 104 && ry >= 0 && ry < 104) {
												occlusionHeights[level][rx][ry] = occHeight;
											}
										}
									}
								}
							}
						}
					}
				} else if (occType == 2) {
				}
			}
		}
		@Pc(497) int lightCount;
		if (GlRenderer.enabled && !underwater) {
			@Pc(472) Environment environment = null;
			parseEnvironment:
			while (true) {
				readEntry:
				do {
					while (buffer.data.length > buffer.offset) {
						l = buffer.g1();
						if (l != 0) {
							if (l != 1) {
								throw new IllegalStateException();
							}
							lightCount = buffer.g1();
							continue readEntry;
						}
						environment = new Environment(buffer);
					}
					if (environment == null) {
						environment = new Environment();
					}
					FogManager.chunksAtmosphere[tileX >> 3][tileY >> 3] = environment;
					break parseEnvironment;
				} while (lightCount <= 0);
				for (ix = 0; ix < lightCount; ix++) {
					@Pc(517) Light lightObj = new Light(buffer);
					if (lightObj.animationPreset == 31) {
						@Pc(529) LightType lightType = LightTypeList.get(buffer.g2());
						lightObj.setFlickerParams(lightType.flickerType, lightType.flickerSpeed, lightType.alphaMin, lightType.alphaMax);
					}
					tx = lightObj.x >> 7;
					px = lightObj.y >> 7;
					if (srcLevel == lightObj.level && tx >= srcChunkX && srcChunkX + 8 > tx && srcChunkY <= px && srcChunkY + 8 > px) {
						py = rotateXFine(rotation, lightObj.x & 0x3FF, lightObj.y & 0x3FF) + (tileX << 7);
						rx = rotateYFine(lightObj.x & 0x3FF, rotation, lightObj.y & 0x3FF) + (tileY << 7);
						lightObj.x = py;
						lightObj.y = rx;
						tx = lightObj.x >> 7;
						px = lightObj.y >> 7;
						if (tx >= 0 && px >= 0 && tx < 104 && px < 104) {
							lightObj.onBridge = (renderFlags[1][tx][px] & 0x2) != 0;
							lightObj.y = tileHeights[lightObj.level][tx][px] - lightObj.y;
							LightingManager.addLight(lightObj);
						}
					}
				}
			}
		}
		k = tileX + 7;
		l = tileY + 7;
		for (lightCount = tileX; lightCount < k; lightCount++) {
			for (ix = tileY; ix < l; ix++) {
				occlusionHeights[level][lightCount][ix] = 0;
			}
		}
	}

	@OriginalMember(owner = "client!rg", name = "a", descriptor = "(IZII)I")
	public static int rotateX(@OriginalArg(0) int rotation, @OriginalArg(2) int y, @OriginalArg(3) int x) {
		@Pc(3) int angle = rotation & 0x3;
		if (angle == 0) {
			return x;
		} else if (angle == 1) {
			return y;
		} else if (angle == 2) {
			return 7 - x;
		} else {
			return 7 - y;
		}
	}

	@OriginalMember(owner = "client!qg", name = "a", descriptor = "(IBII)I")
	public static int rotateY(@OriginalArg(0) int rotation, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		@Pc(3) int angle = rotation & 0x3;
		if (angle == 0) {
			return y;
		} else if (angle == 1) {
			return 7 - x;
		} else if (angle == 2) {
			return 7 - y;
		} else {
			return x;
		}
	}

	@OriginalMember(owner = "client!qi", name = "a", descriptor = "(IIBI)I")
	public static int rotateXFine(@OriginalArg(0) int rotation, @OriginalArg(1) int x, @OriginalArg(3) int y) {
		@Pc(3) int angle = rotation & 0x3;
		if (angle == 0) {
			return x;
		} else if (angle == 1) {
			return y;
		} else if (angle == 2) {
			return 1023 - x;
		} else {
			return 1023 - y;
		}
	}

	@OriginalMember(owner = "client!ol", name = "a", descriptor = "(IIZI)I")
	public static int rotateYFine(@OriginalArg(0) int x, @OriginalArg(1) int rotation, @OriginalArg(3) int y) {
		@Pc(3) int angle = rotation & 0x3;
		if (angle == 0) {
			return y;
		} else if (angle == 1) {
			return 1023 - x;
		} else if (angle == 2) {
			return 1023 - y;
		} else {
			return x;
		}
	}
}
