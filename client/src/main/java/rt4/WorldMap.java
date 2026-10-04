package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;

public class WorldMap {
	@OriginalMember(owner = "client!nc", name = "e", descriptor = "Lclient!na;")
	public static final JagString UNDERLAY = JagString.parse("underlay");
	@OriginalMember(owner = "client!vj", name = "m", descriptor = "Lclient!na;")
	public static final JagString LABELS = JagString.parse("_labels");
	@OriginalMember(owner = "client!ac", name = "m", descriptor = "Lclient!na;")
	public static final JagString OVERLAY = JagString.parse("overlay");
	@OriginalMember(owner = "client!fm", name = "gb", descriptor = "Lclient!na;")
	public static final JagString OVERLAY2 = JagString.parse("overlay2");
	@OriginalMember(owner = "client!df", name = "c", descriptor = "Lclient!na;")
	public static final JagString LOC = JagString.parse("loc");
	@OriginalMember(owner = "client!vk", name = "h", descriptor = "I")
	public static final int randomLightOffset = (int) (Math.random() * 33.0D) - 16;
	@OriginalMember(owner = "client!kd", name = "rb", descriptor = "I")
	public static final int randomHueOffset = (int) (Math.random() * 17.0D) - 8;
	@OriginalMember(owner = "client!lf", name = "c", descriptor = "Lclient!ih;")
	public static final LinkedList mapElements = new LinkedList();
	@OriginalMember(owner = "client!he", name = "db", descriptor = "Lclient!na;")
	public static final JagString EMPTY_STRING = JagString.parse("");
	@OriginalMember(owner = "client!hm", name = "T", descriptor = "Lclient!na;")
	public static final JagString SPACE = JagString.parse(" ");
	@OriginalMember(owner = "client!pm", name = "Y", descriptor = "Lclient!na;")
	public static final JagString LINE_BREAK = JagString.parse("<br>");
	@OriginalMember(owner = "client!nj", name = "h", descriptor = "Lclient!ih;")
	public static final LinkedList highlightedElements = new LinkedList();
	@OriginalMember(owner = "client!di", name = "q", descriptor = "[Lclient!na;")
	public static final JagString[] labelLines = new JagString[5];
	@OriginalMember(owner = "client!wa", name = "ub", descriptor = "Lclient!bn;")
	public static Map currentMap;
	@OriginalMember(owner = "client!dc", name = "O", descriptor = "I")
	public static int loadPercentage = 0;
	@OriginalMember(owner = "client!gj", name = "r", descriptor = "F")
	public static float zoom;
	@OriginalMember(owner = "client!km", name = "uc", descriptor = "F")
	public static float targetZoom;
	@OriginalMember(owner = "client!mh", name = "S", descriptor = "I")
	public static int originX;
	@OriginalMember(owner = "client!aa", name = "j", descriptor = "I")
	public static int originY;
	@OriginalMember(owner = "client!oi", name = "m", descriptor = "I")
	public static int length;
	@OriginalMember(owner = "client!dl", name = "e", descriptor = "I")
	public static int width;
	@OriginalMember(owner = "client!lf", name = "b", descriptor = "[I")
	public static int[] overlayColors;
	@OriginalMember(owner = "client!wb", name = "l", descriptor = "Lclient!fd;")
	public static WorldMapFont font26;
	@OriginalMember(owner = "client!mj", name = "n", descriptor = "Lclient!fd;")
	public static WorldMapFont font30;
	@OriginalMember(owner = "client!kc", name = "C", descriptor = "Lclient!fd;")
	public static WorldMapFont font22;
	@OriginalMember(owner = "client!qh", name = "d", descriptor = "Lclient!fd;")
	public static WorldMapFont font19;
	@OriginalMember(owner = "client!kc", name = "n", descriptor = "Lclient!fd;")
	public static WorldMapFont font17;
	@OriginalMember(owner = "client!nf", name = "d", descriptor = "Lclient!fd;")
	public static WorldMapFont font14;
	@OriginalMember(owner = "client!ma", name = "q", descriptor = "Lclient!fd;")
	public static WorldMapFont font12;
	@OriginalMember(owner = "client!we", name = "v", descriptor = "Lclient!fd;")
	public static WorldMapFont font11;
	@OriginalMember(owner = "client!bc", name = "W", descriptor = "I")
	public static int viewX;
	@OriginalMember(owner = "client!rj", name = "P", descriptor = "I")
	public static int panTargetY = -1;
	@OriginalMember(owner = "client!lc", name = "l", descriptor = "I")
	public static int panTargetX = -1;
	@OriginalMember(owner = "client!cd", name = "u", descriptor = "I")
	public static int viewY;
	@OriginalMember(owner = "client!qh", name = "a", descriptor = "Lclient!se;")
	public static MapElementList labels;
	@OriginalMember(owner = "client!ck", name = "J", descriptor = "[[[B")
	public static byte[][][] overlayIds;
	@OriginalMember(owner = "client!fi", name = "m", descriptor = "[[[B")
	public static byte[][][] wallData;
	@OriginalMember(owner = "client!hb", name = "v", descriptor = "[[[B")
	public static byte[][][] overlayShapes;
	@OriginalMember(owner = "client!si", name = "R", descriptor = "[[[B")
	public static byte[][][] secondaryOverlayIds;
	@OriginalMember(owner = "client!jl", name = "I", descriptor = "[[[B")
	public static byte[][][] secondaryOverlayShapes;
	@OriginalMember(owner = "client!eh", name = "g", descriptor = "[[[I")
	public static int[][][] scenery;
	@OriginalMember(owner = "client!gj", name = "i", descriptor = "[[[I")
	public static int[][][] underlayColors;
	@OriginalMember(owner = "client!uc", name = "d", descriptor = "[[[I")
	public static int[][][] blendedUnderlayColors;
	@OriginalMember(owner = "client!bn", name = "N", descriptor = "Lclient!be;")
	public static Component component;
	@OriginalMember(owner = "client!mc", name = "Q", descriptor = "Lclient!na;")
	public static JagString previousMapName;
	@OriginalMember(owner = "client!fi", name = "j", descriptor = "Lclient!qf;")
	public static Sprite overviewSprite;
	@OriginalMember(owner = "client!mc", name = "S", descriptor = "Lclient!mm;")
	public static SoftwareSprite renderBuffer;
	@OriginalMember(owner = "client!ha", name = "o", descriptor = "I")
	public static int viewportWidth;
	@OriginalMember(owner = "client!cm", name = "c", descriptor = "I")
	public static int viewportHeight;
	@OriginalMember(owner = "client!sm", name = "m", descriptor = "I")
	public static int labelIterIndex;
	@OriginalMember(owner = "client!qf", name = "S", descriptor = "I")
	public static int highlightPulseCount;
	@OriginalMember(owner = "client!al", name = "e", descriptor = "I")
	public static int highlightedMapFunction;

	@OriginalMember(owner = "client!pa", name = "d", descriptor = "(I)V")
	public static void load() {
		if (currentMap == null) {
			return;
		}

		if (loadPercentage < 10) {
			if (!MapList.archive.isGroupReady(currentMap.group)) {
				loadPercentage = client.js5Archive23.getPercentageComplete(currentMap.group) / 10;
				return;
			}

			client.clearScene();
			loadPercentage = 10;
		}

		if (loadPercentage == 10) {
			originX = currentMap.displayMinX >> 6 << 6;
			originY = currentMap.displayMaxX >> 6 << 6;
			length = (currentMap.displayMinZ >> 6 << 6) + 64 - originY;
			width = (currentMap.displayMaxZ >> 6 << 6) + 64 - originX;
			if (currentMap.defaultZoom == 37) {
				zoom = 3.0F;
				targetZoom = 3.0F;
			} else if (currentMap.defaultZoom == 50) {
				zoom = 4.0F;
				targetZoom = 4.0F;
			} else if (currentMap.defaultZoom == 75) {
				zoom = 6.0F;
				targetZoom = 6.0F;
			} else if (currentMap.defaultZoom == 100) {
				zoom = 8.0F;
				targetZoom = 8.0F;
			} else if (currentMap.defaultZoom == 200) {
				zoom = 16.0F;
				targetZoom = 16.0F;
			} else {
				zoom = 8.0F;
				targetZoom = 8.0F;
			}

			@Pc(144) int playerX = (PlayerList.self.xFine >> 7) + Camera.originX - originX;
			@Pc(153) int viewXInit = playerX + (int) (Math.random() * 10.0D) - 5;
			@Pc(168) int playerY = originY + length - Camera.originY - (PlayerList.self.yFine >> 7) - 1;
			@Pc(177) int viewYInit = playerY + (int) (Math.random() * 10.0D) - 5;
			if (viewXInit >= 0 && width > viewXInit && viewYInit >= 0 && viewYInit < length) {
				viewX = viewXInit;
				viewY = viewYInit;
			} else {
				viewY = originY + length - currentMap.originY * 64 - 1;
				viewX = currentMap.originX * 64 - originX;
			}

			clampViewPosition();
			overlayColors = new int[FloTypeList.capacity + 1];
			@Pc(235) int length = WorldMap.length >> 6;
			@Pc(239) int width = WorldMap.width >> 6;
			overlayShapes = new byte[width][length][];
			@Pc(249) int lightJitter = SceneGraph.lightnessJitter >> 2 << 10;
			wallData = new byte[width][length][];
			underlayColors = new int[width][length][];
			overlayIds = new byte[width][length][];
			blendedUnderlayColors = new int[width][length][];
			secondaryOverlayIds = new byte[width][length][];
			@Pc(273) int hueJitter = SceneGraph.hueJitter >> 1;
			secondaryOverlayShapes = new byte[width][length][];
			scenery = new int[width][length][];
			loadOverlayColors(hueJitter, lightJitter);
			loadPercentage = 20;
		} else if (loadPercentage == 20) {
			readUnderlay(new Buffer(MapList.archive.fetchFile(UNDERLAY, currentMap.group)));
			loadPercentage = 30;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 30) {
			readOverlay(new Buffer(MapList.archive.fetchFile(OVERLAY, currentMap.group)));
			loadPercentage = 40;
			GameShell.resetTimer();
		} else if (loadPercentage == 40) {
			readOverlay2(new Buffer(MapList.archive.fetchFile(OVERLAY2, currentMap.group)));
			loadPercentage = 50;
			GameShell.resetTimer();
		} else if (loadPercentage == 50) {
			readLocs(new Buffer(MapList.archive.fetchFile(LOC, currentMap.group)));
			loadPercentage = 60;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 60) {
			if (MapList.archive.isGroupNameValid(JagString.concatenate(new JagString[]{currentMap.group, LABELS}))) {
				if (!MapList.archive.isGroupReady(JagString.concatenate(new JagString[]{currentMap.group, LABELS}))) {
					return;
				}
				labels = MapElementList.create(JagString.concatenate(new JagString[]{currentMap.group, LABELS}), MapList.archive);
			} else {
				labels = new MapElementList(0);
			}
			loadPercentage = 70;
			GameShell.resetTimer();
		} else if (loadPercentage == 70) {
			font11 = new WorldMapFont(11, true, GameShell.canvas);
			loadPercentage = 73;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 73) {
			font12 = new WorldMapFont(12, true, GameShell.canvas);
			loadPercentage = 76;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 76) {
			font14 = new WorldMapFont(14, true, GameShell.canvas);
			loadPercentage = 79;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 79) {
			font17 = new WorldMapFont(17, true, GameShell.canvas);
			loadPercentage = 82;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 82) {
			font19 = new WorldMapFont(19, true, GameShell.canvas);
			loadPercentage = 85;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 85) {
			font22 = new WorldMapFont(22, true, GameShell.canvas);
			loadPercentage = 88;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else if (loadPercentage == 88) {
			font26 = new WorldMapFont(26, true, GameShell.canvas);
			loadPercentage = 91;
			ClientProt.ping(true);
			GameShell.resetTimer();
		} else {
			font30 = new WorldMapFont(30, true, GameShell.canvas);
			loadPercentage = 100;
			ClientProt.ping(true);
			GameShell.resetTimer();
			System.gc();
		}
	}

	@OriginalMember(owner = "client!cn", name = "e", descriptor = "(B)V")
	public static void clampViewPosition() {
		if (viewX < 0) {
			panTargetY = -1;
			viewX = 0;
			panTargetX = -1;
		}
		if (viewX > width) {
			panTargetY = -1;
			viewX = width;
			panTargetX = -1;
		}
		if (viewY < 0) {
			panTargetX = -1;
			panTargetY = -1;
			viewY = 0;
		}
		if (length < viewY) {
			viewY = length;
			panTargetY = -1;
			panTargetX = -1;
		}
	}

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(BLclient!wa;)V")
	public static void readUnderlay(@OriginalArg(1) Buffer data) {
		@Pc(13) int lightOffset = randomLightOffset >> 1;
		@Pc(19) int hueOffset = randomHueOffset >> 2 << 10;
		@Pc(23) byte[][] underlays = new byte[width][length];
		@Pc(33) int chunkY;
		@Pc(102) int x;
		@Pc(114) int y;
		while (data.offset < data.data.length) {
			@Pc(31) int subChunkX = 0;
			chunkY = 0;
			@Pc(35) boolean hasSubChunk = false;
			if (data.g1() == 1) {
				chunkY = data.g1();
				subChunkX = data.g1();
				hasSubChunk = true;
			}
			@Pc(57) int chunkIdX = data.g1();
			@Pc(61) int chunkIdY = data.g1();
			@Pc(68) int baseX = chunkIdX * 64 - originX;
			@Pc(78) int baseY = length + originY - chunkIdY * 64 - 1;
			if (baseX >= 0 && baseY - 63 >= 0 && width > baseX + 63 && length > baseY) {
				for (x = 0; x < 64; x++) {
					@Pc(112) byte[] underlay = underlays[baseX + x];
					for (y = 0; y < 64; y++) {
						if (!hasSubChunk || x >= chunkY * 8 && chunkY * 8 + 8 > x && y >= subChunkX * 8 && y < subChunkX * 8 + 8) {
							underlay[baseY - y] = data.g1b();
						}
					}
				}
			} else if (hasSubChunk) {
				data.offset += 64;
			} else {
				data.offset += 4096;
			}
		}
		@Pc(175) int mapWidth = width;
		chunkY = length;
		@Pc(180) int[] satAccum = new int[chunkY];
		@Pc(183) int[] hueAccum = new int[chunkY];
		@Pc(186) int[] lightAccum = new int[chunkY];
		@Pc(189) int[] chromaAccum = new int[chunkY];
		@Pc(192) int[] countAccum = new int[chunkY];
		for (x = -5; x < mapWidth; x++) {
			@Pc(225) int right;
			@Pc(293) int left;
			for (@Pc(203) int row = 0; row < chunkY; row++) {
				y = x + 5;
				@Pc(272) int unused;
				if (mapWidth > y) {
					right = underlays[y][row] & 0xFF;
					if (right > 0) {
						@Pc(236) FluType fluType = FluTypeList.get(right - 1);
						hueAccum[row] += fluType.weightedHue;
						satAccum[row] += fluType.saturation;
						lightAccum[row] += fluType.lightness;
						chromaAccum[row] += fluType.chroma;
						unused = countAccum[row]++;
					}
				}
				right = x - 5;
				if (right >= 0) {
					left = underlays[right][row] & 0xFF;
					if (left > 0) {
						@Pc(302) FluType fluType2 = FluTypeList.get(left - 1);
						hueAccum[row] -= fluType2.weightedHue;
						satAccum[row] -= fluType2.saturation;
						lightAccum[row] -= fluType2.lightness;
						chromaAccum[row] -= fluType2.chroma;
						unused = countAccum[row]--;
					}
				}
			}
			if (x >= 0) {
				@Pc(355) int[][] colorChunks = blendedUnderlayColors[x >> 6];
				y = 0;
				right = 0;
				@Pc(361) int sumChroma = 0;
				@Pc(363) int sumCount = 0;
				left = 0;
				for (@Pc(367) int col = -5; col < chunkY; col++) {
					@Pc(378) int addIdx = col + 5;
					if (chunkY > addIdx) {
						sumCount += countAccum[addIdx];
						right += satAccum[addIdx];
						left += lightAccum[addIdx];
						y += hueAccum[addIdx];
						sumChroma += chromaAccum[addIdx];
					}
					@Pc(415) int subIdx = col - 5;
					if (subIdx >= 0) {
						left -= lightAccum[subIdx];
						sumChroma -= chromaAccum[subIdx];
						y -= hueAccum[subIdx];
						sumCount -= countAccum[subIdx];
						right -= satAccum[subIdx];
					}
					if (col >= 0 && sumCount > 0) {
						@Pc(462) int[] colorChunk = colorChunks[col >> 6];
						@Pc(480) int hsl = sumChroma == 0 ? 0 : ColorUtils.packHsl(left / sumCount, right / sumCount, y * 256 / sumChroma);
						if (underlays[x][col] != 0) {
							if (colorChunk == null) {
								colorChunk = colorChunks[col >> 6] = new int[4096];
							}
							@Pc(519) int adjustedLight = lightOffset + (hsl & 0x7F);
							if (adjustedLight < 0) {
								adjustedLight = 0;
							} else if (adjustedLight > 127) {
								adjustedLight = 127;
							}
							@Pc(541) int finalHsl = adjustedLight + (hsl & 0x380) + (hsl + hueOffset & 0xFC00);
							colorChunk[((col & 0x3F) << 6) + (x & 0x3F)] = Rasteriser.palette[ColorUtils.multiplyLightnessSafe(96, finalHsl)];
						} else if (colorChunk != null) {
							colorChunk[((col & 0x3F) << 6) + (x & 0x3F)] = 0;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(Lclient!wa;Z)V")
	public static void readOverlay(@OriginalArg(0) Buffer data) {
		nextChunk:
		while (true) {
			if (data.offset < data.data.length) {
				@Pc(22) int subChunkX = 0;
				@Pc(24) boolean hasSubChunk = false;
				@Pc(26) int subChunkY = 0;
				if (data.g1() == 1) {
					hasSubChunk = true;
					subChunkX = data.g1();
					subChunkY = data.g1();
				}
				@Pc(46) int chunkIdX = data.g1();
				@Pc(50) int chunkIdY = data.g1();
				@Pc(62) int baseY = originY + length - chunkIdY * 64 - 1;
				@Pc(69) int baseX = chunkIdX * 64 - originX;
				@Pc(147) byte overlayId;
				@Pc(91) int x;
				if (baseX >= 0 && baseY - 63 >= 0 && baseX + 63 < width && baseY < length) {
					x = baseX >> 6;
					@Pc(95) int chunkX = baseY >> 6;
					@Pc(97) int col = 0;
					while (true) {
						if (col >= 64) {
							continue nextChunk;
						}
						for (@Pc(104) int row = 0; row < 64; row++) {
							if (!hasSubChunk || col >= subChunkX * 8 && col < subChunkX * 8 + 8 && row >= subChunkY * 8 && row < subChunkY * 8 + 8) {
								overlayId = data.g1b();
								if (overlayId != 0) {
									if (overlayIds[x][chunkX] == null) {
										overlayIds[x][chunkX] = new byte[4096];
									}
									overlayIds[x][chunkX][col + (63 - row << 6)] = overlayId;
									@Pc(186) byte shape = data.g1b();
									if (overlayShapes[x][chunkX] == null) {
										overlayShapes[x][chunkX] = new byte[4096];
									}
									overlayShapes[x][chunkX][col + (63 - row << 6)] = shape;
								}
							}
						}
						col++;
					}
				}
				x = 0;
				while (true) {
					if ((hasSubChunk ? 64 : 4096) <= x) {
						continue nextChunk;
					}
					overlayId = data.g1b();
					if (overlayId != 0) {
						data.offset++;
					}
					x++;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(ILclient!wa;)V")
	public static void readOverlay2(@OriginalArg(1) Buffer data) {
		nextChunk:
		while (true) {
			if (data.offset < data.data.length) {
				@Pc(23) int subChunkX = 0;
				@Pc(25) boolean hasSubChunk = false;
				@Pc(27) int subChunkY = 0;
				if (data.g1() == 1) {
					hasSubChunk = true;
					subChunkX = data.g1();
					subChunkY = data.g1();
				}
				@Pc(46) int chunkIdX = data.g1();
				@Pc(50) int chunkIdY = data.g1();
				@Pc(57) int baseX = chunkIdX * 64 - originX;
				@Pc(68) int baseY = length + originY - chunkIdY * 64 - 1;
				@Pc(146) byte overlayId;
				@Pc(96) int x;
				if (baseX >= 0 && baseY - 63 >= 0 && width > baseX + 63 && baseY < length) {
					x = baseX >> 6;
					@Pc(100) int chunkY = baseY >> 6;
					@Pc(102) int col = 0;
					while (true) {
						if (col >= 64) {
							continue nextChunk;
						}
						for (@Pc(107) int row = 0; row < 64; row++) {
							if (!hasSubChunk || subChunkX * 8 <= col && subChunkX * 8 + 8 > col && row >= subChunkY * 8 && subChunkY * 8 + 8 > row) {
								overlayId = data.g1b();
								if (overlayId != 0) {
									if (secondaryOverlayIds[x][chunkY] == null) {
										secondaryOverlayIds[x][chunkY] = new byte[4096];
									}
									secondaryOverlayIds[x][chunkY][(63 - row << 6) + col] = overlayId;
									@Pc(182) byte shape = data.g1b();
									if (secondaryOverlayShapes[x][chunkY] == null) {
										secondaryOverlayShapes[x][chunkY] = new byte[4096];
									}
									secondaryOverlayShapes[x][chunkY][col + (63 - row << 6)] = shape;
								}
							}
						}
						col++;
					}
				}
				x = 0;
				while (true) {
					if (x >= (hasSubChunk ? 64 : 4096)) {
						continue nextChunk;
					}
					overlayId = data.g1b();
					if (overlayId != 0) {
						data.offset++;
					}
					x++;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!nc", name = "a", descriptor = "(BLclient!wa;)V")
	public static void readLocs(@OriginalArg(1) Buffer data) {
		nextChunk:
		while (true) {
			if (data.data.length > data.offset) {
				@Pc(17) boolean hasSubChunk = false;
				@Pc(19) int subChunkX = 0;
				@Pc(21) int subChunkY = 0;
				if (data.g1() == 1) {
					subChunkX = data.g1();
					hasSubChunk = true;
					subChunkY = data.g1();
				}
				@Pc(42) int chunkIdX = data.g1();
				@Pc(46) int chunkIdY = data.g1();
				@Pc(53) int baseX = chunkIdX * 64 - originX;
				@Pc(65) int baseY = originY + length - chunkIdY * 64 - 1;
				@Pc(84) int chunkX;
				@Pc(95) int chunkY;
				if (baseX >= 0 && baseY - 63 >= 0 && width > baseX + 63 && baseY < length) {
					chunkX = baseX >> 6;
					chunkY = baseY >> 6;
					@Pc(150) int col = 0;
					while (true) {
						if (col >= 64) {
							continue nextChunk;
						}
						for (@Pc(155) int row = 0; row < 64; row++) {
							if (!hasSubChunk || subChunkX * 8 <= col && col < subChunkX * 8 + 8 && row >= subChunkY * 8 && row < subChunkY * 8 + 8) {
								@Pc(202) int flags = data.g1();
								if (flags != 0) {
									@Pc(214) int id;
									if ((flags & 0x1) == 1) {
										id = data.g1();
										if (wallData[chunkX][chunkY] == null) {
											wallData[chunkX][chunkY] = new byte[4096];
										}
										wallData[chunkX][chunkY][col + (63 - row << 6)] = (byte) id;
									}
									if ((flags & 0x2) == 2) {
										id = data.g3();
										if (scenery[chunkX][chunkY] == null) {
											scenery[chunkX][chunkY] = new int[4096];
										}
										scenery[chunkX][chunkY][(63 - row << 6) + col] = id;
									}
									if ((flags & 0x4) == 4) {
										id = data.g3();
										if (underlayColors[chunkX][chunkY] == null) {
											underlayColors[chunkX][chunkY] = new int[4096];
										}
										id--;
										@Pc(312) LocType locType = LocTypeList.get(id);
										if (locType.multiLocs != null) {
											locType = locType.getMultiLoc();
											if (locType == null || locType.mapfunction == -1) {
												continue;
											}
										}
										underlayColors[chunkX][chunkY][(63 - row << 6) + col] = locType.id + 1;
										@Pc(353) MapElement element = new MapElement();
										element.id = locType.mapfunction;
										element.mapX = baseX;
										element.mapY = baseY;
										mapElements.addTail(element);
									}
								}
							}
						}
						col++;
					}
				}
				chunkX = 0;
				while (true) {
					if (chunkX >= (hasSubChunk ? 64 : 4096)) {
						continue nextChunk;
					}
					chunkY = data.g1();
					if (chunkY != 0) {
						if ((chunkY & 0x1) == 1) {
							data.offset++;
						}
						if ((chunkY & 0x2) == 2) {
							data.offset += 2;
						}
						if ((chunkY & 0x4) == 4) {
							data.offset += 3;
						}
					}
					chunkX++;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "(IIIIIIIIIII)V")
	public static void renderMapTiles(@OriginalArg(0) int dstY, @OriginalArg(2) int srcBottom, @OriginalArg(3) int srcTop, @OriginalArg(4) int srcRight, @OriginalArg(5) int dstX, @OriginalArg(6) int srcLeft, @OriginalArg(7) int scaleY, @OriginalArg(8) int scaleX) {
		@Pc(9) int tilesX = srcRight - srcLeft;
		@Pc(14) int tilesY = srcBottom - srcTop;
		if (width > srcRight) {
			tilesX++;
		}
		if (length > srcBottom) {
			tilesY++;
		}
		@Pc(32) int tx;
		@Pc(47) int pixelLeft;
		@Pc(57) int pixelRight;
		@Pc(62) int tileW;
		@Pc(71) int chunkX;
		@Pc(104) int bgColor;
		@Pc(145) int pixelTop;
		@Pc(157) int pixelBottom;
		@Pc(162) int tileH;
		@Pc(211) int ty;
		@Pc(222) int mapScene;
		@Pc(233) int mapSceneId;
		@Pc(254) int angle;
		@Pc(270) int localX;
		@Pc(276) int tileIdx;
		@Pc(312) int color;
		@Pc(372) int ovlColor;
		@Pc(185) int[][] chunk2d;
		for (tx = 0; tx < tilesX; tx++) {
			pixelLeft = tx * scaleX >> 16;
			pixelRight = (tx + 1) * scaleX >> 16;
			tileW = pixelRight - pixelLeft;
			if (tileW > 0) {
				chunkX = tx + srcLeft >> 6;
				if (chunkX >= 0 && blendedUnderlayColors.length - 1 >= chunkX) {
					pixelLeft += dstX;
					chunk2d = blendedUnderlayColors[chunkX];
					@Pc(189) byte[][] ovlIds = overlayIds[chunkX];
					@Pc(193) byte[][] ovlShapes = overlayShapes[chunkX];
					@Pc(197) byte[][] walls = wallData[chunkX];
					@Pc(201) byte[][] secOvlShapes = secondaryOverlayShapes[chunkX];
					pixelRight += dstX;
					@Pc(209) byte[][] secOvlIds = secondaryOverlayIds[chunkX];
					for (ty = 0; ty < tilesY; ty++) {
						mapScene = scaleY * ty >> 16;
						mapSceneId = (ty + 1) * scaleY >> 16;
						@Pc(238) int subTileH = mapSceneId - mapScene;
						if (subTileH > 0) {
							mapSceneId += dstY;
							angle = srcTop + ty >> 6;
							@Pc(260) int localY = srcTop + ty & 0x3F;
							mapScene += dstY;
							localX = tx + srcLeft & 0x3F;
							tileIdx = (localY << 6) + localX;
							if (angle < 0 || chunk2d.length - 1 < angle || chunk2d[angle] == null) {
								if (currentMap.backgroundColor != -1) {
									color = currentMap.backgroundColor;
								} else if ((ty + srcTop & 0x4) == (srcLeft + tx & 0x4)) {
									color = overlayColors[FloType.waterOverlayId + 1];
								} else {
									color = 4936552;
								}
								if (angle < 0 || angle > chunk2d.length - 1) {
									if (color == 0) {
										color = 1;
									}
									SoftwareRaster.fillRect(pixelLeft, mapScene, tileW, subTileH, color);
									continue;
								}
							} else {
								color = chunk2d[angle][tileIdx];
							}
							ovlColor = ovlIds[angle] == null ? 0 : overlayColors[ovlIds[angle][tileIdx] & 0xFF];
							if (color == 0) {
								color = 1;
							}
							@Pc(395) int secOvlColor = secOvlIds[angle] == null ? 0 : overlayColors[secOvlIds[angle][tileIdx] & 0xFF];
							@Pc(437) int shapeType;
							if (ovlColor == 0 && secOvlColor == 0) {
								SoftwareRaster.fillRect(pixelLeft, mapScene, tileW, subTileH, color);
							} else {
								@Pc(433) byte shapeData;
								if (ovlColor != 0) {
									if (ovlColor == -1) {
										ovlColor = 1;
									}
									shapeData = ovlShapes[angle] == null ? 0 : ovlShapes[angle][tileIdx];
									shapeType = shapeData & 0xFC;
									if (shapeType == 0 || tileW <= 1 || subTileH <= 1) {
										SoftwareRaster.fillRect(pixelLeft, mapScene, tileW, subTileH, ovlColor);
									} else {
										fillOverlayShape(SoftwareRaster.pixels, ovlColor, pixelLeft, shapeData & 0x3, color, shapeType >> 2, subTileH, tileW, mapScene, true);
									}
								}
								if (secOvlColor != 0) {
									if (secOvlColor == -1) {
										secOvlColor = color;
									}
									shapeData = secOvlShapes[angle][tileIdx];
									shapeType = shapeData & 0xFC;
									if (shapeType == 0 || tileW <= 1 || subTileH <= 1) {
										SoftwareRaster.fillRect(pixelLeft, mapScene, tileW, subTileH, secOvlColor);
									}
									fillOverlayShape(SoftwareRaster.pixels, secOvlColor, pixelLeft, shapeData & 0x3, 0, shapeType >> 2, subTileH, tileW, mapScene, ovlColor == 0);
								}
							}
							if (walls[angle] != null) {
								@Pc(546) int wallType = walls[angle][tileIdx] & 0xFF;
								if (wallType != 0) {
									if (tileW == 1) {
										shapeType = pixelLeft;
									} else {
										shapeType = pixelRight - 1;
									}
									@Pc(569) int wallBottom;
									if (subTileH == 1) {
										wallBottom = mapScene;
									} else {
										wallBottom = mapSceneId - 1;
									}
									@Pc(575) int wallColor = 13421772;
									if (wallType >= 5 && wallType <= 8 || wallType >= 13 && wallType <= 16 || wallType >= 21 && wallType <= 24 || wallType == 27 || wallType == 28) {
										wallColor = 13369344;
										wallType -= 4;
									}
									if (wallType == 1) {
										SoftwareRaster.drawVerticalLine(pixelLeft, mapScene, subTileH, wallColor);
									} else if (wallType == 2) {
										SoftwareRaster.drawHorizontalLine(pixelLeft, mapScene, tileW, wallColor);
									} else if (wallType == 3) {
										SoftwareRaster.drawVerticalLine(shapeType, mapScene, subTileH, wallColor);
									} else if (wallType == 4) {
										SoftwareRaster.drawHorizontalLine(pixelLeft, wallBottom, tileW, wallColor);
									} else if (wallType == 9) {
										SoftwareRaster.drawVerticalLine(pixelLeft, mapScene, subTileH, 16777215);
										SoftwareRaster.drawHorizontalLine(pixelLeft, mapScene, tileW, wallColor);
									} else if (wallType == 10) {
										SoftwareRaster.drawVerticalLine(shapeType, mapScene, subTileH, 16777215);
										SoftwareRaster.drawHorizontalLine(pixelLeft, mapScene, tileW, wallColor);
									} else if (wallType == 11) {
										SoftwareRaster.drawVerticalLine(shapeType, mapScene, subTileH, 16777215);
										SoftwareRaster.drawHorizontalLine(pixelLeft, wallBottom, tileW, wallColor);
									} else if (wallType == 12) {
										SoftwareRaster.drawVerticalLine(pixelLeft, mapScene, subTileH, 16777215);
										SoftwareRaster.drawHorizontalLine(pixelLeft, wallBottom, tileW, wallColor);
									} else if (wallType == 17) {
										SoftwareRaster.drawHorizontalLine(pixelLeft, mapScene, 1, wallColor);
									} else if (wallType == 18) {
										SoftwareRaster.drawHorizontalLine(shapeType, mapScene, 1, wallColor);
									} else if (wallType == 19) {
										SoftwareRaster.drawHorizontalLine(shapeType, wallBottom, 1, wallColor);
									} else if (wallType == 20) {
										SoftwareRaster.drawHorizontalLine(pixelLeft, wallBottom, 1, wallColor);
									} else {
										@Pc(705) int dy;
										if (wallType == 25) {
											for (dy = 0; dy < subTileH; dy++) {
												SoftwareRaster.drawHorizontalLine(dy + pixelLeft, -dy + wallBottom, 1, wallColor);
											}
										} else if (wallType == 26) {
											for (dy = 0; dy < subTileH; dy++) {
												SoftwareRaster.drawHorizontalLine(dy + pixelLeft, mapScene + dy, 1, wallColor);
											}
										}
									}
								}
							}
						}
					}
				} else {
					pixelLeft += dstX;
					for (@Pc(90) int fy = 0; fy < tilesY; fy++) {
						if (currentMap.backgroundColor != -1) {
							bgColor = currentMap.backgroundColor;
						} else if ((tx + srcLeft & 0x4) == (fy + srcTop & 0x4)) {
							bgColor = overlayColors[FloType.waterOverlayId + 1];
						} else {
							bgColor = 0x4b5368;
						}
						if (bgColor == 0) {
							bgColor = 1;
						}
						pixelTop = (scaleY * fy >> 16) + dstY;
						pixelBottom = dstY + ((fy + 1) * scaleY >> 16);
						tileH = pixelBottom - pixelTop;
						SoftwareRaster.fillRect(pixelLeft, pixelTop, tileW, tileH, bgColor);
					}
				}
			}
		}
		for (tx = -2; tx < tilesX + 2; tx++) {
			pixelLeft = tx * scaleX >> 16;
			pixelRight = scaleX * (tx + 1) >> 16;
			tileW = pixelRight - pixelLeft;
			if (tileW > 0) {
				pixelLeft += dstX;
				chunkX = srcLeft + tx >> 6;
				if (chunkX >= 0 && scenery.length - 1 >= chunkX) {
					chunk2d = scenery[chunkX];
					for (bgColor = -2; bgColor < tilesY + 2; bgColor++) {
						pixelTop = bgColor * scaleY >> 16;
						pixelBottom = (bgColor + 1) * scaleY >> 16;
						tileH = pixelBottom - pixelTop;
						if (tileH > 0) {
							pixelTop += dstY;
							@Pc(931) int chunkY = bgColor + srcTop >> 6;
							if (chunkY >= 0 && chunkY <= chunk2d.length - 1) {
								ty = ((srcTop + bgColor & 0x3F) << 6) + (tx + srcLeft & 0x3F);
								if (chunk2d[chunkY] != null) {
									mapScene = chunk2d[chunkY][ty];
									mapSceneId = mapScene & 0x3FFF;
									if (mapSceneId != 0) {
										angle = mapScene >> 14 & 0x3;
										@Pc(998) MsiType type = MsiTypeList.get(mapSceneId - 1);
										@Pc(1003) SoftwareIndexedSprite sprite = type.getSprite(angle);
										if (sprite != null) {
											tileIdx = tileH * sprite.height / 4;
											localX = tileW * sprite.width / 4;
											if (type.stretchToTile) {
												color = mapScene >> 16 & 0xF;
												ovlColor = mapScene >> 20 & 0xF;
												if ((angle & 0x1) == 1) {
													angle = color;
													color = ovlColor;
													ovlColor = angle;
												}
												localX = tileW * color;
												tileIdx = tileH * ovlColor;
											}
											if (localX != 0 && tileIdx != 0) {
												if (type.tintColor == 0) {
													sprite.renderScaled(pixelLeft, pixelTop + tileH - tileIdx, localX, tileIdx);
												} else {
													sprite.renderScaledTinted(pixelLeft, pixelTop + tileH - tileIdx, localX, tileIdx, type.tintColor);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(IZ)V")
	public static void clear(@OriginalArg(1) boolean saveName) {
		overlayShapes = null;
		underlayColors = null;
		component = null;
		overlayIds = null;
		overlayColors = null;
		secondaryOverlayShapes = null;
		if (saveName && currentMap != null) {
			previousMapName = currentMap.group;
		} else {
			previousMapName = null;
		}
		wallData = null;
		secondaryOverlayIds = null;
		scenery = null;
		blendedUnderlayColors = null;
		loadPercentage = 0;
		currentMap = null;
		mapElements.clear();
		labels = null;
		panTargetY = -1;
		font22 = null;
		font30 = null;
		font12 = null;
		font26 = null;
		font11 = null;
		font14 = null;
		font17 = null;
		font19 = null;
		overviewSprite = null;
		panTargetX = -1;
		renderBuffer = null;
	}

	@OriginalMember(owner = "client!je", name = "a", descriptor = "(IIIII)V")
	public static void setViewFromMousePosition(@OriginalArg(1) int compW, @OriginalArg(2) int mouseY, @OriginalArg(3) int mouseX, @OriginalArg(4) int compH) {
		viewX = width * mouseX / compW;
		viewY = length * mouseY / compH;
		panTargetX = -1;
		panTargetY = -1;
		clampViewPosition();
	}

	@OriginalMember(owner = "client!wi", name = "d", descriptor = "(II)V")
	public static void setViewY(@OriginalArg(1) int y) {
		panTargetY = -1;
		panTargetY = -1;
		viewY = y;
		clampViewPosition();
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(II)V")
	public static void setViewX(@OriginalArg(0) int x) {
		panTargetY = -1;
		panTargetX = -1;
		viewX = x;
		clampViewPosition();
	}

	@OriginalMember(owner = "client!hc", name = "d", descriptor = "(I)I")
	public static int getTargetZoom() {
		if ((double) targetZoom == 3.0D) {
			return 37;
		} else if ((double) targetZoom == 4.0D) {
			return 50;
		} else if ((double) targetZoom == 6.0D) {
			return 75;
		} else if ((double) targetZoom == 8.0D) {
			return 100;
		} else {
			return 200;
		}
	}

	@OriginalMember(owner = "client!hb", name = "b", descriptor = "(Lclient!na;I)V")
	public static void switchMap(@OriginalArg(0) JagString name) {
		clear(false);
		setMapByName(name);
	}

	@OriginalMember(owner = "client!kf", name = "a", descriptor = "(Lclient!na;I)V")
	public static void setMapByName(@OriginalArg(0) JagString name) {
		for (@Pc(15) Map map = (Map) MapList.maps.head(); map != null; map = (Map) MapList.maps.next()) {
			if (map.group.strEquals(name)) {
				currentMap = map;
				return;
			}
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(IB)V")
	public static void setTargetZoom(@OriginalArg(0) int zoomLevel) {
		panTargetY = -1;
		if (zoomLevel == 37) {
			targetZoom = 3.0F;
		} else if (zoomLevel == 50) {
			targetZoom = 4.0F;
		} else if (zoomLevel == 75) {
			targetZoom = 6.0F;
		} else if (zoomLevel == 100) {
			targetZoom = 8.0F;
		} else if (zoomLevel == 200) {
			targetZoom = 16.0F;
		}
		panTargetY = -1;
	}

	@OriginalMember(owner = "client!af", name = "b", descriptor = "(B)V")
	public static void reset() {
		clear(false);
		System.gc();
		client.setGameState(25);
	}

	@OriginalMember(owner = "client!bb", name = "a", descriptor = "(I)V")
	public static void updateZoomAndScroll() {
		if (zoom < targetZoom) {
			zoom = (float) ((double) zoom + (double) zoom / 30.0D);
			if (targetZoom < zoom) {
				zoom = targetZoom;
			}
			clampViewPosition();
		} else if (targetZoom < zoom) {
			zoom = (float) ((double) zoom - (double) zoom / 30.0D);
			if (targetZoom > zoom) {
				zoom = targetZoom;
			}
			clampViewPosition();
		}
		if (panTargetX == -1 || panTargetY == -1) {
			return;
		}
		@Pc(60) int dx = panTargetX - viewX;
		if (dx < 2 || dx > 2) {
			dx >>= 0x4;
		}
		@Pc(78) int dy = panTargetY - viewY;
		if (dy < 2 || dy > 2) {
			dy >>= 0x4;
		}
		viewY -= -dy;
		viewX += dx;
		if (dx == 0 && dy == 0) {
			panTargetX = -1;
			panTargetY = -1;
		}
		clampViewPosition();
	}

	@OriginalMember(owner = "client!lb", name = "d", descriptor = "(B)V")
	public static void restorePreviousMap() {
		if (previousMapName != null) {
			switchMap(previousMapName);
			previousMapName = null;
		}
	}

	@OriginalMember(owner = "client!va", name = "c", descriptor = "(BI)V")
	public static void highlightMapElement(@OriginalArg(1) int mapFunction) {
		highlightedMapFunction = mapFunction;
		Cs1ScriptRunner.mapHighlightPulseCounter = 20;
		highlightPulseCount = 3;
	}

	@OriginalMember(owner = "client!ab", name = "a", descriptor = "(Lclient!na;I)V")
	public static void panToLabelByPrefix(@OriginalArg(0) JagString prefix) {
		@Pc(9) int idx = findLabelByPrefix(prefix);
		if (idx != -1) {
			panToCoords(labels.coordX[idx], labels.coordY[idx]);
		}
	}

	@OriginalMember(owner = "client!rc", name = "a", descriptor = "(Lclient!na;Z)Lclient!na;")
	public static JagString getLabelTextByPrefix(@OriginalArg(0) JagString prefix) {
		@Pc(12) int idx = findLabelByPrefix(prefix);
		return idx == -1 ? EMPTY_STRING : labels.names[idx].replaceAll(SPACE, LINE_BREAK);
	}

	@OriginalMember(owner = "client!rg", name = "d", descriptor = "(B)Lclient!bn;")
	public static Map getCurrentMap() {
		return currentMap;
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(B)I")
	public static int getFirstVisibleLabel() {
		labelIterIndex = 0;
		return getNextVisibleLabel();
	}

	@OriginalMember(owner = "client!je", name = "j", descriptor = "(I)I")
	public static int getNextVisibleLabel() {
		if (labels == null) {
			return -1;
		}
		while (labelIterIndex < labels.count) {
			if (labels.isVisible(labelIterIndex)) {
				return labelIterIndex++;
			}
			labelIterIndex++;
		}
		return -1;
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(BII)V")
	public static void panToCoords(@OriginalArg(1) int coordX, @OriginalArg(2) int coordY) {
		panTargetX = coordX - originX;
		@Pc(24) int viewLeft = panTargetX - (int) ((float) component.width / zoom);
		@Pc(33) int viewRight = panTargetX + (int) ((float) component.width / zoom);
		if (viewLeft < 0) {
			panTargetX = (int) ((float) component.width / zoom);
		}
		panTargetY = length + originY - coordY - 1;
		@Pc(61) int viewBottom = (int) ((float) component.height / zoom) + panTargetY;
		@Pc(70) int viewTop = panTargetY - (int) ((float) component.height / zoom);
		if (viewRight > width) {
			panTargetX = width - (int) ((float) component.width / zoom);
		}
		if (viewTop < 0) {
			panTargetY = (int) ((float) component.height / zoom);
		}
		if (length < viewBottom) {
			panTargetY = length - (int) ((float) component.height / zoom);
		}
	}

	@OriginalMember(owner = "client!dh", name = "a", descriptor = "(Lclient!na;I)V")
	public static void panToLabelByExactName(@OriginalArg(0) JagString name) {
		@Pc(7) int idx = findLabelByExactName(name);
		if (idx != -1) {
			panToCoords(labels.coordX[idx], labels.coordY[idx]);
		}
	}

	@OriginalMember(owner = "client!hc", name = "a", descriptor = "(Lclient!na;Z)I")
	public static int findLabelByExactName(@OriginalArg(0) JagString name) {
		if (labels == null || name.length() == 0) {
			return -1;
		}
		for (@Pc(20) int i = 0; i < labels.count; i++) {
			if (labels.names[i].replaceAll(SPACE, LINE_BREAK).strEquals(name)) {
				return i;
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "([IIIIIIIIIZB)V")
	public static void fillOverlayShape(@OriginalArg(0) int[] pixels, @OriginalArg(1) int overlayColor, @OriginalArg(2) int x, @OriginalArg(3) int rotation, @OriginalArg(4) int underlayColor, @OriginalArg(5) int shape, @OriginalArg(6) int h, @OriginalArg(7) int w, @OriginalArg(8) int y, @OriginalArg(9) boolean fillUnderlay) {
		@Pc(7) int clippedX = x;
		if (SoftwareRaster.clipRight <= x) {
			return;
		}
		if (x < SoftwareRaster.clipLeft) {
			clippedX = SoftwareRaster.clipLeft;
		}
		@Pc(30) int right = w + x;
		if (SoftwareRaster.clipLeft >= right) {
			return;
		}
		if (SoftwareRaster.clipRight < right) {
			right = SoftwareRaster.clipRight;
		}
		@Pc(43) int clippedY = y;
		if (SoftwareRaster.clipBottom <= y) {
			return;
		}
		@Pc(56) int bottom = y + h;
		if (y < SoftwareRaster.clipTop) {
			clippedY = SoftwareRaster.clipTop;
		}
		if (bottom <= SoftwareRaster.clipTop) {
			return;
		}
		@Pc(79) int offset = clippedX + SoftwareRaster.width * clippedY;
		if (shape == 9) {
			rotation = rotation + 1 & 0x3;
			shape = 1;
		}
		@Pc(99) int stride = clippedX + SoftwareRaster.width - right;
		clippedY -= y;
		@Pc(108) int adjH = h - clippedY;
		if (SoftwareRaster.clipBottom < bottom) {
			bottom = SoftwareRaster.clipBottom;
		}
		if (shape == 10) {
			rotation = rotation + 3 & 0x3;
			shape = 1;
		}
		clippedX -= x;
		@Pc(136) int adjW = w - clippedX;
		if (shape == 11) {
			rotation = rotation + 3 & 0x3;
			shape = 8;
		}
		right -= x;
		@Pc(157) int clippedW = w - right;
		bottom -= y;
		@Pc(165) int clippedH = h - bottom;
		@Pc(175) int row;
		@Pc(184) int col;
		if (shape == 1) {
			if (rotation == 0) {
				for (row = clippedY; row < bottom; row++) {
					for (col = clippedX; col < right; col++) {
						if (col <= row) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 1) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = clippedX; col < right; col++) {
						if (row >= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 2) {
				for (row = clippedY; row < bottom; row++) {
					for (col = clippedX; col < right; col++) {
						if (col >= row) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 3) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = clippedX; col < right; col++) {
						if (col >= row) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			}
		} else if (shape == 2) {
			if (rotation == 0) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = clippedX; col < right; col++) {
						if (row >> 1 >= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 1) {
				for (row = clippedY; row < bottom; row++) {
					for (col = clippedX; col < right; col++) {
						if (offset >= 0 && offset < pixels.length) {
							if (row << 1 <= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						} else {
							offset++;
						}
					}
					offset += stride;
				}
			} else if (rotation == 2) {
				for (row = clippedY; row < bottom; row++) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (row >> 1 >= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 3) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (row << 1 <= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			}
		} else if (shape == 3) {
			if (rotation == 0) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (row >> 1 >= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 1) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = clippedX; col < right; col++) {
						if (col >= row << 1) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 2) {
				for (row = clippedY; row < bottom; row++) {
					for (col = clippedX; col < right; col++) {
						if (col <= row >> 1) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 3) {
				for (row = clippedY; row < bottom; row++) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (row << 1 <= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			}
		} else if (shape == 4) {
			if (rotation == 0) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = clippedX; col < right; col++) {
						if (row >> 1 <= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 1) {
				for (row = clippedY; row < bottom; row++) {
					for (col = clippedX; col < right; col++) {
						if (row << 1 >= col) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 2) {
				for (row = clippedY; row < bottom; row++) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (col >= row >> 1) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			} else if (rotation == 3) {
				for (row = adjH - 1; row >= clippedH; row--) {
					for (col = adjW - 1; col >= clippedW; col--) {
						if (col <= row << 1) {
							pixels[offset] = overlayColor;
						} else if (fillUnderlay) {
							pixels[offset] = underlayColor;
						}
						offset++;
					}
					offset += stride;
				}
			}
		} else if (shape != 5) {
			if (shape == 6) {
				if (rotation == 0) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (col <= w / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 1) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (row <= h / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 2) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (col >= w / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 3) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (row >= h / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
			}
			if (shape == 7) {
				if (rotation == 0) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (col <= row - h / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 1) {
					for (row = adjH - 1; row >= clippedH; row--) {
						for (col = clippedX; col < right; col++) {
							if (row - h / 2 >= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 2) {
					for (row = adjH - 1; row >= clippedH; row--) {
						for (col = adjW - 1; col >= clippedW; col--) {
							if (col <= row - h / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 3) {
					for (row = clippedY; row < bottom; row++) {
						for (col = adjW - 1; col >= clippedW; col--) {
							if (row - h / 2 >= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
			}
			if (shape == 8) {
				if (rotation == 0) {
					for (row = clippedY; row < bottom; row++) {
						for (col = clippedX; col < right; col++) {
							if (row - h / 2 <= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 1) {
					for (row = adjH - 1; row >= clippedH; row--) {
						for (col = clippedX; col < right; col++) {
							if (row - h / 2 <= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 2) {
					for (row = adjH - 1; row >= clippedH; row--) {
						for (col = adjW - 1; col >= clippedW; col--) {
							if (col >= row - h / 2) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
				if (rotation == 3) {
					for (row = clippedY; row < bottom; row++) {
						for (col = adjW - 1; col >= clippedW; col--) {
							if (row - h / 2 <= col) {
								pixels[offset] = overlayColor;
							} else if (fillUnderlay) {
								pixels[offset] = underlayColor;
							}
							offset++;
						}
						offset += stride;
					}
					return;
				}
			}
		} else if (rotation == 0) {
			for (row = adjH - 1; row >= clippedH; row--) {
				for (col = adjW - 1; col >= clippedW; col--) {
					if (row >> 1 <= col) {
						pixels[offset] = overlayColor;
					} else if (fillUnderlay) {
						pixels[offset] = underlayColor;
					}
					offset++;
				}
				offset += stride;
			}
		} else if (rotation == 1) {
			for (row = adjH - 1; row >= clippedH; row--) {
				for (col = clippedX; col < right; col++) {
					if (col <= row << 1) {
						pixels[offset] = overlayColor;
					} else if (fillUnderlay) {
						pixels[offset] = underlayColor;
					}
					offset++;
				}
				offset += stride;
			}
		} else if (rotation == 2) {
			for (row = clippedY; row < bottom; row++) {
				for (col = clippedX; col < right; col++) {
					if (col >= row >> 1) {
						pixels[offset] = overlayColor;
					} else if (fillUnderlay) {
						pixels[offset] = underlayColor;
					}
					offset++;
				}
				offset += stride;
			}
		} else if (rotation == 3) {
			for (row = clippedY; row < bottom; row++) {
				for (col = adjW - 1; col >= clippedW; col--) {
					if (row << 1 >= col) {
						pixels[offset] = overlayColor;
					} else if (fillUnderlay) {
						pixels[offset] = underlayColor;
					}
					offset++;
				}
				offset += stride;
			}
		}
	}

	@OriginalMember(owner = "client!rg", name = "a", descriptor = "(IIIIIIIII)V")
	public static void renderMapViewport(@OriginalArg(0) int dstRight, @OriginalArg(1) int dstTop, @OriginalArg(3) int srcRight, @OriginalArg(4) int srcBottom, @OriginalArg(5) int dstLeft, @OriginalArg(6) int srcTop, @OriginalArg(7) int dstBottom, @OriginalArg(8) int srcLeft) {
		@Pc(7) int rangeX = srcRight - srcLeft;
		@Pc(16) int scaleX = (dstRight - dstLeft << 16) / rangeX;
		@Pc(21) int rangeY = srcTop - srcBottom;
		@Pc(30) int scaleY = (dstBottom - dstTop << 16) / rangeY;
		renderMapTiles(dstTop, srcTop, srcBottom, srcRight, dstLeft, srcLeft, scaleY, scaleX);
	}

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "(IIIII)V")
	public static void render(@OriginalArg(0) int dstX, @OriginalArg(2) int dstY, @OriginalArg(3) int dstW, @OriginalArg(4) int dstH) {
		if (loadPercentage < 100) {
			load();
		}
		if (GlRenderer.enabled) {
			GlRaster.setClip(dstX, dstY, dstX + dstH, dstW + dstY);
		} else {
			SoftwareRaster.setClip(dstX, dstY, dstX + dstH, dstW + dstY);
		}
		@Pc(50) int centerX;
		@Pc(61) int centerY;
		if (loadPercentage < 100) {
			centerX = dstX + dstH / 2;
			centerY = dstW / 2 + dstY - 18 - 20;
			if (GlRenderer.enabled) {
				GlRaster.fillRect(dstX, dstY, dstH, dstW, 0);
				GlRaster.drawRect(centerX - 152, centerY, 304, 34, 9179409);
				GlRaster.drawRect(centerX - 151, centerY + 1, 302, 32, 0);
				GlRaster.fillRect(centerX - 150, centerY + 2, loadPercentage * 3, 30, 9179409);
				GlRaster.fillRect(centerX + loadPercentage * 3 - 150, centerY - -2, 300 - loadPercentage * 3, 30, 0);
			} else {
				SoftwareRaster.fillRect(dstX, dstY, dstH, dstW, 0);
				SoftwareRaster.drawRect(centerX - 152, centerY, 304, 34, 9179409);
				SoftwareRaster.drawRect(centerX - 151, centerY + 1, 302, 32, 0);
				SoftwareRaster.fillRect(centerX - 150, centerY + 2, loadPercentage * 3, 30, 9179409);
				SoftwareRaster.fillRect(loadPercentage * 3 + centerX - 150, centerY - -2, 300 - loadPercentage * 3, 30, 0);
			}
			Fonts.b12Full.renderCenter(LocalizedText.LOADINGDOTDOTDOT, centerX, centerY + 20, 16777215, -1);
			return;
		}
		viewportHeight = (int) ((float) (dstW * 2) / zoom);
		Cs1ScriptRunner.worldMapViewportX = viewX - (int) ((float) dstH / zoom);
		@Pc(211) int srcLeft = viewX - (int) ((float) dstH / zoom);
		centerX = viewY - (int) ((float) dstW / zoom);
		Cs1ScriptRunner.worldMapViewportY = viewY - (int) ((float) dstW / zoom);
		@Pc(236) int srcBottom = viewY + (int) ((float) dstW / zoom);
		centerY = (int) ((float) dstH / zoom) + viewX;
		viewportWidth = (int) ((float) (dstH * 2) / zoom);
		if (GlRenderer.enabled) {
			if (renderBuffer == null || renderBuffer.width != dstH || renderBuffer.height != dstW) {
				renderBuffer = null;
				renderBuffer = new SoftwareSprite(dstH, dstW);
			}
			SoftwareRaster.setSize(renderBuffer.pixels, dstH, dstW);
			renderMapViewport(dstH, 0, centerY, centerX, 0, srcBottom, dstW, srcLeft);
			renderMapIcons(dstH, 0, centerY, srcBottom, dstW, 0, srcLeft, centerX);
			renderMapLabels(0, 0, srcLeft, dstH, srcBottom, centerX, centerY, dstW);
			GlRaster.drawPixels(renderBuffer.pixels, dstX, dstY, dstH, dstW);
			SoftwareRaster.pixels = null;
		} else {
			renderMapViewport(dstH + dstX, dstY, centerY, centerX, dstX, srcBottom, dstY + dstW, srcLeft);
			renderMapIcons(dstX + dstH, dstX, centerY, srcBottom, dstW + dstY, dstY, srcLeft, centerX);
			renderMapLabels(dstX, dstY, srcLeft, dstX + dstH, srcBottom, centerX, centerY, dstW + dstY);
		}
		if (highlightPulseCount > 0) {
			Cs1ScriptRunner.mapHighlightPulseCounter--;
			if (Cs1ScriptRunner.mapHighlightPulseCounter == 0) {
				Cs1ScriptRunner.mapHighlightPulseCounter = 20;
				highlightPulseCount--;
			}
		}

		if (Cheat.displayFps) {
			@Pc(405) int debugY = dstY + dstW - 8;
			@Pc(412) int debugX = dstX + dstH - 5;
			Fonts.p12Full.renderRight(JagString.concatenate(new JagString[]{Cheat.DEBUG_FPS, JagString.parseInt((int) GameShell.framesPerSecond)}), debugX, debugY, 16776960, -1);
			@Pc(434) Runtime runtime = Runtime.getRuntime();
			@Pc(443) int memory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L / 1024L);
			@Pc(445) int color = 16776960;
			@Pc(446) int nextLine = debugY - 15;
			if (memory > 95) {
				color = 16711680;
			}
			Fonts.p12Full.renderRight(JagString.concatenate(new JagString[]{Cheat.DEBUG_MEMORY, JagString.parseInt(memory), Cheat.DEBUG_MEMORY_UNIT}), debugX, nextLine, color, -1);
			debugY = nextLine - 15;
		}
	}

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(IIIIIIIII)V")
	public static void renderMapIcons(@OriginalArg(0) int dstRight, @OriginalArg(1) int dstLeft, @OriginalArg(2) int srcRight, @OriginalArg(3) int srcBottom, @OriginalArg(4) int dstBottom, @OriginalArg(5) int dstTop, @OriginalArg(7) int srcLeft, @OriginalArg(8) int srcTop) {
		@Pc(13) int rangeX = srcRight - srcLeft;
		@Pc(17) int rangeY = srcBottom - srcTop;
		@Pc(26) int scaleX = (dstRight - dstLeft << 16) / rangeX;
		@Pc(35) int scaleY = (dstBottom - dstTop << 16) / rangeY;
		renderMapIconSprites(dstLeft, srcBottom, srcRight, scaleY, srcLeft, scaleX, srcTop, dstTop);
	}

	@OriginalMember(owner = "client!sm", name = "a", descriptor = "(IIIIIIIIIII)V")
	public static void renderMapIconSprites(@OriginalArg(0) int dstLeft, @OriginalArg(1) int srcBottom, @OriginalArg(2) int srcRight, @OriginalArg(3) int scaleY, @OriginalArg(4) int srcLeft, @OriginalArg(8) int scaleX, @OriginalArg(9) int srcTop, @OriginalArg(10) int dstTop) {
		@Pc(9) int rangeX = srcRight - srcLeft;
		@Pc(11) int highlightAlpha = -1;
		if (highlightPulseCount > 0) {
			if (Cs1ScriptRunner.mapHighlightPulseCounter <= 10) {
				highlightAlpha = Cs1ScriptRunner.mapHighlightPulseCounter * 5;
			} else {
				highlightAlpha = 50 - (Cs1ScriptRunner.mapHighlightPulseCounter - 10) * 5;
			}
		}
		@Pc(39) int rangeY = srcBottom - srcTop;
		@Pc(43) int marginX = 983040 / scaleX;
		@Pc(47) int marginY = 983040 / scaleY;
		for (@Pc(50) int tx = -marginX; tx < rangeX + marginX; tx++) {
			@Pc(65) int pixelLeft = tx * scaleX >> 16;
			@Pc(75) int pixelRight = scaleX * (tx + 1) >> 16;
			@Pc(80) int tileW = pixelRight - pixelLeft;
			if (tileW > 0) {
				@Pc(91) int chunkX = srcLeft + tx >> 6;
				pixelLeft += dstLeft;
				if (chunkX >= 0 && chunkX <= underlayColors.length - 1) {
					@Pc(116) int[][] locChunks = underlayColors[chunkX];
					for (@Pc(119) int ty = -marginY; ty < rangeY + marginY; ty++) {
						@Pc(136) int pixelBottom = scaleY * (ty + 1) >> 16;
						@Pc(144) int pixelTop = ty * scaleY >> 16;
						@Pc(149) int tileH = pixelBottom - pixelTop;
						if (tileH > 0) {
							pixelTop += dstTop;
							@Pc(163) int chunkY = srcTop + ty >> 6;
							if (chunkY >= 0 && chunkY <= locChunks.length - 1 && locChunks[chunkY] != null) {
								@Pc(203) int tileIdx = (tx + srcLeft & 0x3F) + ((srcTop + ty & 0x3F) << 6);
								@Pc(209) int locId = locChunks[chunkY][tileIdx];
								if (locId != 0) {
									@Pc(222) LocType locType = LocTypeList.get(locId - 1);
									if (!MapList.visibility[locType.mapfunction]) {
										if (highlightAlpha != -1 && locType.mapfunction == highlightedMapFunction) {
											@Pc(243) MapElement element = new MapElement();
											element.mapX = pixelLeft;
											element.mapY = pixelTop;
											element.id = locType.mapfunction;
											highlightedElements.addTail(element);
										} else {
											MapList.sprites[locType.mapfunction].render(pixelLeft - 7, pixelTop + -7);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for (@Pc(285) MapElement element = (MapElement) highlightedElements.head(); element != null; element = (MapElement) highlightedElements.next()) {
			SoftwareRaster.fillCircleAlpha(element.mapX, element.mapY, 15, highlightAlpha);
			SoftwareRaster.fillCircleAlpha(element.mapX, element.mapY, 13, highlightAlpha);
			SoftwareRaster.fillCircleAlpha(element.mapX, element.mapY, 11, highlightAlpha);
			SoftwareRaster.fillCircleAlpha(element.mapX, element.mapY, 9, highlightAlpha);
			MapList.sprites[element.id].render(element.mapX - 7, element.mapY + -7);
		}
		highlightedElements.clear();
	}

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(III)V")
	public static void loadOverlayColors(@OriginalArg(1) int hueJitter, @OriginalArg(2) int lightJitter) {
		for (@Pc(11) int i = 0; i < FloTypeList.capacity; i++) {
			@Pc(18) FloType floType = FloTypeList.get(i);
			if (floType != null) {
				@Pc(24) int texture = floType.texture;
				if (texture >= 0 && !Rasteriser.textureProvider.isTextureRepeating(texture)) {
					texture = -1;
				}
				@Pc(53) int color;
				@Pc(66) int hsl;
				@Pc(72) int adjustedLight;
				@Pc(95) int finalHsl;
				if (floType.secondaryColor >= 0) {
					hsl = floType.secondaryColor;
					adjustedLight = (hsl & 0x7F) + hueJitter;
					if (adjustedLight < 0) {
						adjustedLight = 0;
					} else if (adjustedLight > 127) {
						adjustedLight = 127;
					}
					finalHsl = (hsl & 0x380) + (lightJitter + hsl & 0xFC00) + adjustedLight;
					color = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(finalHsl, 96)];
				} else if (texture >= 0) {
					color = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(Rasteriser.textureProvider.getAverageColor(texture), 96)];
				} else if (floType.baseColor == -1) {
					color = -1;
				} else {
					hsl = floType.baseColor;
					adjustedLight = hueJitter + (hsl & 0x7F);
					if (adjustedLight < 0) {
						adjustedLight = 0;
					} else if (adjustedLight > 127) {
						adjustedLight = 127;
					}
					finalHsl = adjustedLight + (hsl & 0x380) + (hsl + lightJitter & 0xFC00);
					color = Rasteriser.palette[ColorUtils.multiplyLightnessGrayscale(finalHsl, 96)];
				}
				overlayColors[i + 1] = color;
			}
		}
	}

	@OriginalMember(owner = "client!ni", name = "a", descriptor = "(ILclient!na;)I")
	public static int findLabelByPrefix(@OriginalArg(1) JagString prefix) {
		if (labels == null || prefix.length() == 0) {
			return -1;
		}
		for (@Pc(20) int i = 0; i < labels.count; i++) {
			if (labels.names[i].replaceAll(SPACE, LINE_BREAK).startsWithIgnoreCase(prefix)) {
				return i;
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(BIIIIIIII)V")
	public static void renderMapLabels(@OriginalArg(1) int dstLeft, @OriginalArg(2) int dstTop, @OriginalArg(3) int srcLeft, @OriginalArg(4) int dstRight, @OriginalArg(5) int srcBottom, @OriginalArg(6) int srcTop, @OriginalArg(7) int srcRight, @OriginalArg(8) int dstBottom) {
		for (@Pc(11) int i = 0; i < labels.count; i++) {
			if (labels.isTextLabel(i)) {
				@Pc(32) int mapX = labels.coordX[i] - originX;
				@Pc(43) int mapY = originY + length - labels.coordY[i] - 1;
				@Pc(59) int screenX = dstLeft + (dstRight - dstLeft) * (mapX - srcLeft) / (srcRight - srcLeft);
				@Pc(64) int labelSize = labels.getLabelSize(i);
				@Pc(80) int screenY = (dstBottom - dstTop) * (mapY - srcTop) / (srcBottom - srcTop) + dstTop;
				@Pc(82) int color = 16777215;
				@Pc(84) WorldMapFont font = null;
				if (labelSize == 0) {
					if ((double) zoom == 3.0D) {
						font = font11;
					}
					if ((double) zoom == 4.0D) {
						font = font12;
					}
					if ((double) zoom == 6.0D) {
						font = font14;
					}
					if ((double) zoom >= 8.0D) {
						font = font17;
					}
				}
				if (labelSize == 1) {
					if ((double) zoom == 3.0D) {
						font = font14;
					}
					if ((double) zoom == 4.0D) {
						font = font17;
					}
					if ((double) zoom == 6.0D) {
						font = font19;
					}
					if ((double) zoom >= 8.0D) {
						font = font22;
					}
				}
				if (labelSize == 2) {
					if ((double) zoom == 3.0D) {
						font = font19;
					}
					color = 16755200;
					if ((double) zoom == 4.0D) {
						font = font22;
					}
					if ((double) zoom == 6.0D) {
						font = font26;
					}
					if ((double) zoom >= 8.0D) {
						font = font30;
					}
				}
				if (labels.colors[i] != -1) {
					color = labels.colors[i];
				}
				if (font != null) {
					@Pc(211) int lineCount = Fonts.p11Full.splitParagraph(labels.names[i], null, labelLines);
					screenY -= font.getLineHeight() * (lineCount - 1) / 2;
					screenY += font.getAscent() / 2;
					for (@Pc(231) int line = 0; line < lineCount; line++) {
						@Pc(242) JagString text = labelLines[line];
						if (lineCount - 1 > line) {
							text.setLength(text.length() - 4);
						}
						font.renderStringCenter(text, screenX, screenY, color);
						screenY += font.getLineHeight();
					}
				}
			}
		}
	}
}
