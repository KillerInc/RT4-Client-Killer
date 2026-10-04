package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!pb")
public final class LocType {

	// Loc Shapes
	public static final int WALL_STRAIGHT = 0;
	public static final int WALL_DIAGONALCORNER = 1;
	public static final int WALL_L = 2;
	public static final int WALL_SQUARECORNER = 3;
	public static final int WALL_DIAGONAL = 9;

	public static final int WALLDECOR_STRAIGHT_XOFFSET = 4;
	public static final int WALLDECOR_STRAIGHT_YOFFSET = 5;
	public static final int WALLDECOR_DIAGONAL_XOFFSET = 6;
	public static final int WALLDECOR_DIAGONAL_YOFFSET = 7;
	public static final int WALLDECOR_DIAGONAL_BOTH = 8;

	public static final int ROOF_STRAIGHT = 12;
	public static final int ROOF_DIAGONAL_WITH_ROOFEDGE = 13;
	public static final int ROOF_DIAGONAL = 14;
	public static final int ROOF_L_CONCAVE = 15;
	public static final int ROOF_L_CONVEX = 16;
	public static final int ROOF_FLAT = 17;

	public static final int ROOFEDGE_STRAIGHT = 18;
	public static final int ROOFEDGE_DIAGONALCORNER = 19;
	public static final int ROOFEDGE_L = 20;
	public static final int ROOFEDGE_SQUARECORNER = 21;

	public static final int CENTREPIECE_STRAIGHT = 10;
	public static final int CENTREPIECE_DIAGONAL = 11;
	public static final int GROUNDDECOR = 22;

	@OriginalMember(owner = "client!wf", name = "o", descriptor = "[Lclient!gb;")
	public static final RawModel[] tempModels = new RawModel[4];

	@OriginalMember(owner = "client!ni", name = "n", descriptor = "Lclient!sm;")
	public static LocEntity tempLocEntity = new LocEntity();

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "[S")
	private short[] retex_s;

	@OriginalMember(owner = "client!pb", name = "b", descriptor = "[S")
	private short[] recol_s;

	@OriginalMember(owner = "client!pb", name = "n", descriptor = "[I")
	private int[] shapes;

	@OriginalMember(owner = "client!pb", name = "v", descriptor = "[S")
	private short[] retex_d;

	@OriginalMember(owner = "client!pb", name = "B", descriptor = "Lclient!sc;")
	private HashTable params;

	@OriginalMember(owner = "client!pb", name = "H", descriptor = "[S")
	private short[] recol_d;

	@OriginalMember(owner = "client!pb", name = "P", descriptor = "[B")
	public byte[] recol_p;

	@OriginalMember(owner = "client!pb", name = "X", descriptor = "[I")
	private int[] models;

	@OriginalMember(owner = "client!pb", name = "db", descriptor = "[I")
	public int[] multiLocs;

	@OriginalMember(owner = "client!pb", name = "hb", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!pb", name = "wb", descriptor = "[I")
	public int[] bgsounds;

	@OriginalMember(owner = "client!pb", name = "e", descriptor = "I")
	public int width = 1;

	@OriginalMember(owner = "client!pb", name = "i", descriptor = "Z")
	public boolean forcedecor = false;

	@OriginalMember(owner = "client!pb", name = "l", descriptor = "I")
	public int length = 1;

	@OriginalMember(owner = "client!pb", name = "C", descriptor = "Z")
	public boolean clipped = false;

	@OriginalMember(owner = "client!pb", name = "u", descriptor = "I")
	private int ambient = 0;

	@OriginalMember(owner = "client!gg", name = "W", descriptor = "Lclient!na;")
	public static final JagString DEFAULT_NAME = JagString.parse("null");
	@OriginalMember(owner = "client!pb", name = "E", descriptor = "Lclient!na;")
	public JagString name = DEFAULT_NAME;

	@OriginalMember(owner = "client!pb", name = "D", descriptor = "Z")
	public boolean castshadow = true;

	@OriginalMember(owner = "client!pb", name = "t", descriptor = "I")
	public int cursor1Op = -1;

	@OriginalMember(owner = "client!pb", name = "R", descriptor = "I")
	public int bgsoundmax = 0;

	@OriginalMember(owner = "client!pb", name = "S", descriptor = "I")
	public int mapscene = -1;

	@OriginalMember(owner = "client!pb", name = "G", descriptor = "B")
	private byte hillskewType = 0;

	@OriginalMember(owner = "client!pb", name = "r", descriptor = "Z")
	public boolean members = false;

	@OriginalMember(owner = "client!pb", name = "T", descriptor = "I")
	public int cursor1 = -1;

	@OriginalMember(owner = "client!pb", name = "w", descriptor = "I")
	private int xoff = 0;

	@OriginalMember(owner = "client!pb", name = "W", descriptor = "I")
	public int bgsoundmin = 0;

	@OriginalMember(owner = "client!pb", name = "h", descriptor = "I")
	public int mapfunction = -1;

	@OriginalMember(owner = "client!pb", name = "L", descriptor = "Z")
	public boolean dynamic = false;

	@OriginalMember(owner = "client!pb", name = "Y", descriptor = "I")
	public int cursor2Op = -1;

	@OriginalMember(owner = "client!pb", name = "A", descriptor = "S")
	public short hillskewAmount = -1;

	@OriginalMember(owner = "client!pb", name = "g", descriptor = "I")
	private int resizez = 128;

	@OriginalMember(owner = "client!pb", name = "z", descriptor = "[Lclient!na;")
	public JagString[] ops = new JagString[5];

	@OriginalMember(owner = "client!pb", name = "d", descriptor = "I")
	private int resizex = 128;

	@OriginalMember(owner = "client!pb", name = "s", descriptor = "Z")
	public boolean allowrandomizedanimation = true;

	@OriginalMember(owner = "client!pb", name = "o", descriptor = "I")
	private int resizey = 128;

	@OriginalMember(owner = "client!pb", name = "y", descriptor = "Z")
	public boolean breakroutefinding = false;

	@OriginalMember(owner = "client!pb", name = "kb", descriptor = "I")
	public int interactable = -1;

	@OriginalMember(owner = "client!pb", name = "lb", descriptor = "Z")
	public boolean render = false;

	@OriginalMember(owner = "client!pb", name = "fb", descriptor = "Z")
	public boolean active = true;

	@OriginalMember(owner = "client!pb", name = "nb", descriptor = "I")
	private int multiLocVarp = -1;

	@OriginalMember(owner = "client!pb", name = "bb", descriptor = "I")
	public int cursor2 = -1;

	@OriginalMember(owner = "client!pb", name = "pb", descriptor = "I")
	public int blocksides = 0;

	@OriginalMember(owner = "client!pb", name = "m", descriptor = "Z")
	public boolean blockrange = true;

	@OriginalMember(owner = "client!pb", name = "qb", descriptor = "I")
	private int zoff = 0;

	@OriginalMember(owner = "client!pb", name = "c", descriptor = "I")
	public int mapSceneAngleOffset = 0;

	@OriginalMember(owner = "client!pb", name = "jb", descriptor = "I")
	public int walloff = 16;

	@OriginalMember(owner = "client!pb", name = "tb", descriptor = "Z")
	public boolean mapSceneRotated = false;

	@OriginalMember(owner = "client!pb", name = "N", descriptor = "I")
	private int yoff = 0;

	@OriginalMember(owner = "client!pb", name = "k", descriptor = "I")
	public int bgsoundrange = 0;

	@OriginalMember(owner = "client!pb", name = "p", descriptor = "I")
	private int contrast = 0;

	@OriginalMember(owner = "client!pb", name = "mb", descriptor = "I")
	public int anim = -1;

	@OriginalMember(owner = "client!pb", name = "I", descriptor = "Z")
	public boolean hasanimation = false;

	@OriginalMember(owner = "client!pb", name = "O", descriptor = "I")
	public int bgsound = -1;

	@OriginalMember(owner = "client!pb", name = "ub", descriptor = "I")
	public int blockwalk = 2;

	@OriginalMember(owner = "client!pb", name = "sb", descriptor = "Z")
	private boolean mirror = false;

	@OriginalMember(owner = "client!pb", name = "gb", descriptor = "I")
	private int multiLocVarbit = -1;

	@OriginalMember(owner = "client!pb", name = "yb", descriptor = "I")
	public int supportitems = -1;

	@OriginalMember(owner = "client!pb", name = "zb", descriptor = "Z")
	private boolean computeVertexColors = false;

	@OriginalMember(owner = "client!pb", name = "Ab", descriptor = "Z")
	public boolean occlude = false;

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(II)Z")
	public boolean isReady(@OriginalArg(1) int shape) {
		if (this.shapes != null) {
			for (@Pc(18) int i = 0; i < this.shapes.length; i++) {
				if (shape == this.shapes[i]) {
					return LocTypeList.modelsArchive.isFileReady(0, this.models[i] & 0xFFFF);
				}
			}
			return true;
		} else if (this.models == null) {
			return true;
		} else if (shape == LocType.CENTREPIECE_STRAIGHT) {
			@Pc(71) boolean ready = true;
			for (@Pc(73) int i = 0; i < this.models.length; i++) {
				ready &= LocTypeList.modelsArchive.isFileReady(0, this.models[i] & 0xFFFF);
			}
			return ready;
		} else {
			return true;
		}
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(I)Lclient!pb;")
	public LocType getMultiLoc() {
		@Pc(26) int varpValue = -1;
		if (this.multiLocVarbit != -1) {
			varpValue = VarpDomain.getVarbit(this.multiLocVarbit);
		} else if (this.multiLocVarp != -1) {
			varpValue = VarpDomain.activeVarps[this.multiLocVarp];
		}
		if (varpValue < 0 || varpValue >= this.multiLocs.length - 1 || this.multiLocs[varpValue] == -1) {
			@Pc(84) int defaultLocId = this.multiLocs[this.multiLocs.length - 1];
			return defaultLocId == -1 ? null : LocTypeList.get(defaultLocId);
		} else {
			return LocTypeList.get(this.multiLocs[varpValue]);
		}
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(III)Lclient!gb;")
	private RawModel getRawModel(@OriginalArg(0) int rotation, @OriginalArg(1) int shape) {
		@Pc(7) RawModel rawModel = null;
		@Pc(10) boolean mirrored = this.mirror;
		if (shape == 2 && rotation > 3) {
			mirrored = !mirrored;
		}
		@Pc(46) int modelCount;
		@Pc(48) int i;
		if (this.shapes == null) {
			if (shape != 10) {
				return null;
			}
			if (this.models == null) {
				return null;
			}
			modelCount = this.models.length;
			for (i = 0; i < modelCount; i++) {
				@Pc(60) int modelId = this.models[i];
				if (mirrored) {
					modelId += 65536;
				}
				rawModel = (RawModel) LocTypeList.rawModelCache.get(modelId);
				if (rawModel == null) {
					rawModel = RawModel.create(LocTypeList.modelsArchive, modelId & 0xFFFF);
					if (rawModel == null) {
						return null;
					}
					if (mirrored) {
						rawModel.negateZAndReverseFaces();
					}
					LocTypeList.rawModelCache.put(rawModel, modelId);
				}
				if (modelCount > 1) {
					tempModels[i] = rawModel;
				}
			}
			if (modelCount > 1) {
				rawModel = new RawModel(tempModels, modelCount);
			}
		} else {
			modelCount = -1;
			for (i = 0; i < this.shapes.length; i++) {
				if (shape == this.shapes[i]) {
					modelCount = i;
					break;
				}
			}
			if (modelCount == -1) {
				return null;
			}
			i = this.models[modelCount];
			if (mirrored) {
				i += 65536;
			}
			rawModel = (RawModel) LocTypeList.rawModelCache.get(i);
			if (rawModel == null) {
				rawModel = RawModel.create(LocTypeList.modelsArchive, i & 0xFFFF);
				if (rawModel == null) {
					return null;
				}
				if (mirrored) {
					rawModel.negateZAndReverseFaces();
				}
				LocTypeList.rawModelCache.put(rawModel, i);
			}
		}
		@Pc(211) boolean resized;
		resized = this.resizex != 128 || this.resizey != 128 || this.resizez != 128;
		@Pc(230) boolean translated;
		translated = this.xoff != 0 || this.yoff != 0 || this.zoff != 0;
		@Pc(265) RawModel copy = new RawModel(rawModel, rotation == 0 && !resized && !translated, this.recol_s == null, this.retex_s == null, true);
		if (shape == 4 && rotation > 3) {
			copy.rotate256();
			copy.translate(45, 0, -45);
		}
		@Pc(285) int rotationQuadrant = rotation & 0x3;
		if (rotationQuadrant == 1) {
			copy.swapXz();
		} else if (rotationQuadrant == 2) {
			copy.negateXz();
		} else if (rotationQuadrant == 3) {
			copy.rotateCounterClockwiseXY();
		}
		@Pc(315) int j;
		if (this.recol_s != null) {
			for (j = 0; j < this.recol_s.length; j++) {
				if (this.recol_p == null || this.recol_p.length <= j) {
					copy.recolor(this.recol_s[j], this.recol_d[j]);
				} else {
					copy.recolor(this.recol_s[j], client.locRecolorPalette[this.recol_p[j] & 0xFF]);
				}
			}
		}
		if (this.retex_s != null) {
			for (j = 0; j < this.retex_s.length; j++) {
				copy.retexture(this.retex_s[j], this.retex_d[j]);
			}
		}
		if (resized) {
			copy.resize(this.resizex, this.resizey, this.resizez);
		}
		if (translated) {
			copy.translate(this.xoff, this.yoff, this.zoff);
		}
		return copy;
	}

	@OriginalMember(owner = "client!pb", name = "c", descriptor = "(I)V")
	public void postDecode() {
		if (this.interactable == -1) {
			this.interactable = 0;
			if (this.models != null && (this.shapes == null || this.shapes[0] == LocType.CENTREPIECE_STRAIGHT)) {
				this.interactable = 1;
			}
			for (@Pc(30) int i = 0; i < 5; i++) {
				if (this.ops[i] != null) {
					this.interactable = 1;
					break;
				}
			}
		}
		if (this.supportitems == -1) {
			this.supportitems = this.blockwalk == 0 ? 0 : 1;
		}
	}

	@OriginalMember(owner = "client!pb", name = "d", descriptor = "(I)Z")
	public boolean hasAreaSound() {
		if (this.multiLocs == null) {
			return this.bgsound != -1 || this.bgsounds != null;
		}
		for (@Pc(44) int i = 0; i < this.multiLocs.length; i++) {
			if (this.multiLocs[i] != -1) {
				@Pc(70) LocType type = LocTypeList.get(this.multiLocs[i]);
				if (type.bgsound != -1 || type.bgsounds != null) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(IIB)I")
	public int getParam(@OriginalArg(0) int defaultValue, @OriginalArg(1) int key) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(21) IntNode value = (IntNode) this.params.get(key);
			return value == null ? defaultValue : value.value;
		}
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(ILclient!wa;)V")
	public void decode(@OriginalArg(1) Buffer buffer) {
		while (true) {
			@Pc(9) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(buffer, opcode);
		}
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(Lclient!wa;II)V")
	private void decode(@OriginalArg(0) Buffer buffer, @OriginalArg(1) int opcode) {
		@Pc(18) int count;
		@Pc(38) int len;
		if (opcode == 1) {
			count = buffer.g1();
			if (count > 0) {
				if (this.models == null || SceneGraph.levelsHidden) {
					this.shapes = new int[count];
					this.models = new int[count];
					for (len = 0; len < count; len++) {
						this.models[len] = buffer.g2();
						this.shapes[len] = buffer.g1();
					}
				} else {
					buffer.offset += count * 3;
				}
			}
		} else if (opcode == 2) {
			this.name = buffer.gjstr();
		} else if (opcode == 5) {
			count = buffer.g1();
			if (count > 0) {
				if (this.models == null || SceneGraph.levelsHidden) {
					this.models = new int[count];
					this.shapes = null;
					for (len = 0; len < count; len++) {
						this.models[len] = buffer.g2();
					}
				} else {
					buffer.offset += count * 2;
				}
			}
		} else if (opcode == 14) {
			this.width = buffer.g1();
		} else if (opcode == 15) {
			this.length = buffer.g1();
		} else if (opcode == 17) {
			this.blockwalk = 0;
			this.blockrange = false;
		} else if (opcode == 18) {
			this.blockrange = false;
		} else if (opcode == 19) {
			this.interactable = buffer.g1();
		} else if (opcode == 21) {
			this.hillskewType = 1;
		} else if (opcode == 22) { // sharelight
			this.computeVertexColors = true;
		} else if (opcode == 23) {
			this.occlude = true;
		} else if (opcode == 24) {
			this.anim = buffer.g2();
			if (this.anim == 65535) {
				this.anim = -1;
			}
		} else if (opcode == 27) {
			this.blockwalk = 1;
		} else if (opcode == 28) {
			this.walloff = buffer.g1();
		} else if (opcode == 29) {
			this.ambient = buffer.g1b();
		} else if (opcode == 39) {
			this.contrast = buffer.g1b() * 5;
		} else if (opcode >= 30 && opcode < 35) {
			this.ops[opcode - 30] = buffer.gjstr();
			if (this.ops[opcode - 30].equalsIgnoreCase(LocalizedText.HIDDEN)) {
				this.ops[opcode - 30] = null;
			}
		} else if (opcode == 40) {
			count = buffer.g1();
			this.recol_s = new short[count];
			this.recol_d = new short[count];
			for (len = 0; len < count; len++) {
				this.recol_s[len] = (short) buffer.g2();
				this.recol_d[len] = (short) buffer.g2();
			}
		} else if (opcode == 41) {
			count = buffer.g1();
			this.retex_d = new short[count];
			this.retex_s = new short[count];
			for (len = 0; len < count; len++) {
				this.retex_s[len] = (short) buffer.g2();
				this.retex_d[len] = (short) buffer.g2();
			}
		} else if (opcode == 42) {
			count = buffer.g1();
			this.recol_p = new byte[count];
			for (len = 0; len < count; len++) {
				this.recol_p[len] = buffer.g1b();
			}
		} else if (opcode == 60) {
			this.mapfunction = buffer.g2();
		} else if (opcode == 62) {
			this.mirror = true;
		} else if (opcode == 64) {
			this.active = false;
		} else if (opcode == 65) {
			this.resizex = buffer.g2();
		} else if (opcode == 66) {
			this.resizey = buffer.g2();
		} else if (opcode == 67) {
			this.resizez = buffer.g2();
		} else if (opcode == 69) {
			this.blocksides = buffer.g1();
		} else if (opcode == 70) {
			this.xoff = buffer.g2b();
		} else if (opcode == 71) {
			this.yoff = buffer.g2b();
		} else if (opcode == 72) {
			this.zoff = buffer.g2b();
		} else if (opcode == 73) {
			this.forcedecor = true;
		} else if (opcode == 74) {
			this.breakroutefinding = true;
		} else if (opcode == 75) {
			this.supportitems = buffer.g1();
		} else if (opcode == 77 || opcode == 92) {
			count = -1;
			this.multiLocVarbit = buffer.g2();
			if (this.multiLocVarbit == 65535) {
				this.multiLocVarbit = -1;
			}
			this.multiLocVarp = buffer.g2();
			if (this.multiLocVarp == 65535) {
				this.multiLocVarp = -1;
			}
			if (opcode == 92) {
				count = buffer.g2();
				if (count == 65535) {
					count = -1;
				}
			}
			len = buffer.g1();
			this.multiLocs = new int[len + 2];
			for (@Pc(790) int i = 0; i <= len; i++) {
				this.multiLocs[i] = buffer.g2();
				if (this.multiLocs[i] == 65535) {
					this.multiLocs[i] = -1;
				}
			}
			this.multiLocs[len + 1] = count;
		} else if (opcode == 78) {
			this.bgsound = buffer.g2();
			this.bgsoundrange = buffer.g1();
		} else if (opcode == 79) {
			this.bgsoundmin = buffer.g2(); // interval
			this.bgsoundmax = buffer.g2(); // interval
			this.bgsoundrange = buffer.g1();

			count = buffer.g1();
			this.bgsounds = new int[count];
			for (len = 0; len < count; len++) {
				this.bgsounds[len] = buffer.g2();
			}
		} else if (opcode == 81) { // sethillskew
			this.hillskewType = 2;
			this.hillskewAmount = (short) (buffer.g1() * 256);
		} else if (opcode == 82) {
			this.render = true;
		} else if (opcode == 88) {
			this.castshadow = false;
		} else if (opcode == 89) {
			this.allowrandomizedanimation = false;
		} else if (opcode == 90) {
			this.clipped = true;
		} else if (opcode == 91) {
			this.members = true;
		} else if (opcode == 93) {
			this.hillskewType = 3;
			this.hillskewAmount = (short) buffer.g2();
		} else if (opcode == 94) {
			this.hillskewType = 4;
		} else if (opcode == 95) {
			this.hillskewType = 5;
		} else if (opcode == 96) {
			this.hasanimation = true;
		} else if (opcode == 97) {
			this.mapSceneRotated = true;
		} else if (opcode == 98) {
			this.dynamic = true;
		} else if (opcode == 99) {
			this.cursor1Op = buffer.g1();
			this.cursor1 = buffer.g2();
		} else if (opcode == 100) {
			this.cursor2Op = buffer.g1();
			this.cursor2 = buffer.g2();
		} else if (opcode == 101) {
			this.mapSceneAngleOffset = buffer.g1();
		} else if (opcode == 102) {
			this.mapscene = buffer.g2();
		} else if (opcode == 249) {
			count = buffer.g1();
			if (this.params == null) {
				len = IntUtils.clp2(count);
				this.params = new HashTable(len);
			}
			for (len = 0; len < count; len++) {
				@Pc(576) boolean isString = buffer.g1() == 1;
				@Pc(580) int key = buffer.g3();
				@Pc(589) Node value;
				if (isString) {
					value = new StringNode(buffer.gjstr());
				} else {
					value = new IntNode(buffer.g4());
				}
				this.params.put(value, key);
			}
		}
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(Z)Z")
	public boolean isReady() {
		if (this.models == null) {
			return true;
		}
		@Pc(13) boolean ready = true;
		for (@Pc(15) int i = 0; i < this.models.length; i++) {
			ready &= LocTypeList.modelsArchive.isFileReady(0, this.models[i] & 0xFFFF);
		}
		return ready;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(IZZI)Lclient!td;")
	private GlModel getGlModel(@OriginalArg(0) int rotation, @OriginalArg(1) boolean animated, @OriginalArg(3) int shape) {
		@Pc(10) int ambientIntensity = this.ambient + 64;
		@Pc(17) int diffuseIntensity = this.contrast * 5 + 768;
		@Pc(79) GlModel glModel;
		@Pc(24) int modelIndex;
		@Pc(177) int i;
		if (this.shapes == null) {
			if (shape != 10) {
				return null;
			}
			if (this.models == null) {
				return null;
			}
			modelIndex = this.models.length;
			if (modelIndex == 0) {
				return null;
			}
			@Pc(135) long cacheKey = 0L;
			for (@Pc(137) int j = 0; j < modelIndex; j++) {
				cacheKey = (long) this.models[j] + cacheKey * 67783L;
			}
			if (animated) {
				cacheKey = ~cacheKey;
			}
			glModel = (GlModel) LocTypeList.rawModelCache.get(cacheKey);
			if (glModel == null) {
				@Pc(175) RawModel rawModel = null;
				for (i = 0; i < modelIndex; i++) {
					rawModel = RawModel.create(LocTypeList.modelsArchive, this.models[i] & 0xFFFF);
					if (rawModel == null) {
						return null;
					}
					if (modelIndex > 1) {
						tempModels[i] = rawModel;
					}
				}
				if (modelIndex > 1) {
					rawModel = new RawModel(tempModels, modelIndex);
				}
				glModel = new GlModel(rawModel, ambientIntensity, diffuseIntensity, animated);
				LocTypeList.rawModelCache.put(glModel, cacheKey);
			}
		} else {
			modelIndex = -1;
			@Pc(26) int modelId;
			for (modelId = 0; modelId < this.shapes.length; modelId++) {
				if (this.shapes[modelId] == shape) {
					modelIndex = modelId;
					break;
				}
			}
			if (modelIndex == -1) {
				return null;
			}
			modelId = this.models[modelIndex];
			if (animated) {
				modelId += 65536;
			}
			glModel = (GlModel) LocTypeList.rawModelCache.get(modelId);
			if (glModel == null) {
				@Pc(90) RawModel rawModel = RawModel.create(LocTypeList.modelsArchive, modelId & 0xFFFF);
				if (rawModel == null) {
					return null;
				}
				glModel = new GlModel(rawModel, ambientIntensity, diffuseIntensity, animated);
				LocTypeList.rawModelCache.put(glModel, modelId);
			}
		}
		@Pc(236) boolean mirrored = this.mirror;
		if (shape == 2 && rotation > 3) {
			mirrored = !mirrored;
		}
		@Pc(264) boolean noVerticalTransform = this.resizey == 128 && this.yoff == 0;
		@Pc(294) boolean noTransform = rotation == 0 && this.resizex == 128 && this.resizez == 128 && this.xoff == 0 && this.zoff == 0 && !mirrored;
		@Pc(351) GlModel copy = glModel.deepCopy(noTransform, noVerticalTransform, this.recol_s == null, glModel.getAmbientIntensity() == ambientIntensity, rotation == 0 && !mirrored, true, diffuseIntensity == glModel.getDiffuseIntensity(), !mirrored, this.retex_s == null);
		if (mirrored) {
			copy.mirrorZ();
		}
		if (shape == 4 && rotation > 3) {
			copy.rotate45Degrees();
			copy.translate(45, 0, -45);
		}
		@Pc(374) int rotationQuadrant = rotation & 0x3;
		if (rotationQuadrant == 1) {
			copy.rotateCounterClockwiseAll();
		} else if (rotationQuadrant == 2) {
			copy.rotate180All();
		} else if (rotationQuadrant == 3) {
			copy.rotateClockwiseAll();
		}
		if (this.recol_s != null) {
			for (i = 0; i < this.recol_s.length; i++) {
				copy.recolor(this.recol_s[i], this.recol_d[i]);
			}
		}
		if (this.retex_s != null) {
			for (i = 0; i < this.retex_s.length; i++) {
				copy.retexture(this.retex_s[i], this.retex_d[i]);
			}
		}
		if (this.resizex != 128 || this.resizey != 128 || this.resizez != 128) {
			copy.resize(this.resizex, this.resizey, this.resizez);
		}
		if (this.xoff != 0 || this.yoff != 0 || this.zoff != 0) {
			copy.translate(this.xoff, this.yoff, this.zoff);
		}
		if (ambientIntensity != copy.getAmbientIntensity()) {
			copy.setAmbientIntensity(ambientIntensity);
		}
		if (copy.getDiffuseIntensity() != diffuseIntensity) {
			copy.setDiffuseIntensity(diffuseIntensity);
		}
		return copy;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(II[[III[[IZLclient!ek;BZI)Lclient!sm;")
	public LocEntity getStaticEntity(@OriginalArg(0) int rotation, @OriginalArg(1) int tileX, @OriginalArg(2) int[][] heightmap, @OriginalArg(3) int shape, @OriginalArg(4) int tileY, @OriginalArg(5) int[][] overlayHeightmap, @OriginalArg(6) boolean shareLight, @OriginalArg(7) SoftwareIndexedSprite shadowSprite, @OriginalArg(9) boolean castShadow, @OriginalArg(10) int plane) {
		@Pc(29) long cacheKey;
		if (GlRenderer.enabled) {
			if (this.shapes == null) {
				cacheKey = (this.id << 10) + rotation;
			} else {
				cacheKey = rotation + (this.id << 10) + (shape << 3);
			}
			@Pc(225) LocEntity entity = (LocEntity) LocTypeList.staticEntityCache.get(cacheKey);
			@Pc(235) GlModel glModel;
			@Pc(265) SoftwareIndexedSprite shadow;
			if (entity == null) {
				glModel = this.getGlModel(rotation, false, shape);
				if (glModel == null) {
					tempLocEntity.model = null;
					tempLocEntity.sprite = null;
					return tempLocEntity;
				}
				if (shape == 10 && rotation > 3) {
					glModel.rotateY(256);
				}
				if (castShadow) {
					shadow = glModel.projectShadow(shadowSprite);
				} else {
					shadow = null;
				}
				entity = new LocEntity();
				entity.model = glModel;
				entity.sprite = shadow;
				LocTypeList.staticEntityCache.put(entity, cacheKey);
			} else {
				glModel = (GlModel) entity.model;
				shadow = entity.sprite;
			}
			@Pc(298) boolean mergeVertexColors = this.computeVertexColors & shareLight;
			@Pc(330) GlModel copy = glModel.deepCopy(this.hillskewType != 3, this.hillskewType == 0, true, true, true, !mergeVertexColors, true, true, true);
			if (this.hillskewType != 0) {
				copy.alignToTerrain(this.hillskewType, this.hillskewAmount, glModel, heightmap, overlayHeightmap, tileX, tileY, plane);
			}
			copy.uploadBuffers(this.interactable == 0 && !this.dynamic, true, true, this.interactable == 0, true, false);
			tempLocEntity.model = copy;
			copy.mergeable = mergeVertexColors;
			tempLocEntity.sprite = shadow;
			return tempLocEntity;
		}
		if (this.shapes == null) {
			cacheKey = (this.id << 10) + rotation;
		} else {
			cacheKey = (shape << 3) + ((this.id << 10) + rotation);
		}
		@Pc(50) boolean sharedLight;
		if (shareLight && this.computeVertexColors) {
			cacheKey |= Long.MIN_VALUE;
			sharedLight = true;
		} else {
			sharedLight = false;
		}
		@Pc(60) Entity cachedEntity = (Entity) LocTypeList.staticEntityCache.get(cacheKey);
		if (cachedEntity == null) {
			@Pc(69) RawModel rawModel = this.getRawModel(rotation, shape);
			if (rawModel == null) {
				tempLocEntity.model = null;
				return tempLocEntity;
			}
			rawModel.resetBones();
			if (shape == 10 && rotation > 3) {
				rawModel.rotate256();
			}
			if (sharedLight) {
				rawModel.ambient = (short) (this.ambient + 64);
				cachedEntity = rawModel;
				rawModel.contrast = (short) (this.contrast * 5 + 768);
				rawModel.calculateNormals();
			} else {
				cachedEntity = new SoftwareModel(rawModel, this.ambient + 64, this.contrast * 5 + 768, -50, -10, -50);
			}
			LocTypeList.staticEntityCache.put(cachedEntity, cacheKey);
		}
		if (sharedLight) {
			cachedEntity = ((RawModel) cachedEntity).shallowCopy();
		}
		if (this.hillskewType != 0) {
			if (cachedEntity instanceof SoftwareModel) {
				cachedEntity = ((SoftwareModel) cachedEntity).alignToTerrain(this.hillskewType, this.hillskewAmount, heightmap, overlayHeightmap, tileX, tileY, plane, true);
			} else if (cachedEntity instanceof RawModel) {
				cachedEntity = ((RawModel) cachedEntity).placeOnTerrain(this.hillskewType, this.hillskewAmount, heightmap, overlayHeightmap, tileX, tileY, plane);
			}
		}
		tempLocEntity.model = cachedEntity;
		return tempLocEntity;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(IILclient!ek;ILclient!tk;I[[IZII[[IIII)Lclient!sm;")
	public LocEntity getAnimatedEntity(@OriginalArg(0) int plane, @OriginalArg(1) int tileX, @OriginalArg(2) SoftwareIndexedSprite shadowSprite, @OriginalArg(3) int tileY, @OriginalArg(4) SeqType seqType, @OriginalArg(5) int rotation, @OriginalArg(6) int[][] heightmap, @OriginalArg(7) boolean castShadow, @OriginalArg(8) int currentFrame, @OriginalArg(10) int[][] overlayHeightmap, @OriginalArg(11) int delayClock, @OriginalArg(12) int shape, @OriginalArg(13) int tweenFrame) {
		@Pc(30) long cacheKey;
		if (!GlRenderer.enabled) {
			if (this.shapes == null) {
				cacheKey = (this.id << 10) + rotation;
			} else {
				cacheKey = rotation + (this.id << 10) + (shape << 3);
			}
			@Pc(195) SoftwareModel softModel = (SoftwareModel) LocTypeList.animatedEntityCache.get(cacheKey);
			if (softModel == null) {
				@Pc(204) RawModel rawModel = this.getRawModel(rotation, shape);
				if (rawModel == null) {
					return null;
				}
				softModel = new SoftwareModel(rawModel, this.ambient + 64, this.contrast * 5 + 768, -50, -10, -50);
				LocTypeList.animatedEntityCache.put(softModel, cacheKey);
			}
			@Pc(234) boolean copied = false;
			if (seqType != null) {
				copied = true;
				softModel = (SoftwareModel) seqType.animateLocSoftware(currentFrame, delayClock, softModel, rotation, tweenFrame);
			}
			if (shape == 10 && rotation > 3) {
				if (!copied) {
					copied = true;
					softModel = (SoftwareModel) softModel.copyForLoc(true, true, true);
				}
				softModel.rotateY(256);
			}
			if (this.hillskewType != 0) {
				if (!copied) {
					softModel = (SoftwareModel) softModel.copyForLoc(true, true, true);
				}
				softModel = softModel.alignToTerrain(this.hillskewType, this.hillskewAmount, heightmap, overlayHeightmap, tileX, tileY, plane, false);
			}
			tempLocEntity.model = softModel;
			return tempLocEntity;
		}
		if (this.shapes == null) {
			cacheKey = rotation + (this.id << 10);
		} else {
			cacheKey = (shape << 3) + ((this.id << 10) + rotation);
		}
		@Pc(46) GlModel cachedModel = (GlModel) LocTypeList.animatedEntityCache.get(cacheKey);
		if (cachedModel == null) {
			cachedModel = this.getGlModel(rotation, true, shape);
			if (cachedModel == null) {
				return null;
			}
			cachedModel.createBones();
			cachedModel.uploadBuffers(false, false, false, false, false, true);
			LocTypeList.animatedEntityCache.put(cachedModel, cacheKey);
		}
		@Pc(80) boolean copied = false;
		@Pc(82) GlModel glModel = cachedModel;
		if (seqType != null) {
			glModel = (GlModel) seqType.animateLocGl(delayClock, currentFrame, tweenFrame, rotation, cachedModel);
			copied = true;
		}
		if (shape == 10 && rotation > 3) {
			if (!copied) {
				glModel = (GlModel) glModel.copyForLoc(true, true, true);
				copied = true;
			}
			glModel.rotateY(256);
		}
		if (castShadow) {
			tempLocEntity.sprite = glModel.projectShadow(shadowSprite);
		} else {
			tempLocEntity.sprite = null;
		}
		if (this.hillskewType != 0) {
			if (!copied) {
				glModel = (GlModel) glModel.copyForLoc(true, true, true);
			}
			glModel.alignToTerrain(this.hillskewType, this.hillskewAmount, cachedModel, heightmap, overlayHeightmap, tileX, tileY, plane);
		}
		tempLocEntity.model = glModel;
		return tempLocEntity;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(Lclient!na;II)Lclient!na;")
	public JagString getParam(@OriginalArg(0) JagString defaultValue, @OriginalArg(2) int key) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(26) StringNode value = (StringNode) this.params.get(key);
			return value == null ? defaultValue : value.value;
		}
	}
}
