package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!be")
public final class Component {

	@OriginalMember(owner = "client!pf", name = "b", descriptor = "Lclient!n;")
	public static final SoftLruHashTable sprites = new SoftLruHashTable(200);

	@OriginalMember(owner = "client!jk", name = "z", descriptor = "Lclient!n;")
	public static final SoftLruHashTable models = new SoftLruHashTable(50);

	@OriginalMember(owner = "client!gn", name = "i", descriptor = "Lclient!n;")
	public static final SoftLruHashTable fonts = new SoftLruHashTable(20);

	@OriginalMember(owner = "client!rc", name = "C", descriptor = "Z")
	public static boolean loadFailed = false;

	@OriginalMember(owner = "client!be", name = "b", descriptor = "[Ljava/lang/Object;")
	public Object[] onFriendTransmit;

	@OriginalMember(owner = "client!be", name = "d", descriptor = "[Ljava/lang/Object;")
	public Object[] onStatTransmit;

	@OriginalMember(owner = "client!be", name = "e", descriptor = "[Ljava/lang/Object;")
	public Object[] onLoad;

	@OriginalMember(owner = "client!be", name = "g", descriptor = "[Ljava/lang/Object;")
	public Object[] onVarcTransmit;

	@OriginalMember(owner = "client!be", name = "k", descriptor = "[Ljava/lang/Object;")
	public Object[] onClickRepeat;

	@OriginalMember(owner = "client!be", name = "p", descriptor = "[Ljava/lang/Object;")
	public Object[] onDrag;

	@OriginalMember(owner = "client!be", name = "q", descriptor = "[Lclient!na;")
	public JagString[] ops;

	@OriginalMember(owner = "client!be", name = "s", descriptor = "[Lclient!na;")
	public JagString[] invOptions;

	@OriginalMember(owner = "client!be", name = "t", descriptor = "[Ljava/lang/Object;")
	public Object[] onInvTransmit;

	@OriginalMember(owner = "client!be", name = "u", descriptor = "[I")
	public int[] inventoryTriggers;

	@OriginalMember(owner = "client!be", name = "v", descriptor = "[Ljava/lang/Object;")
	public Object[] onWidgetsOpenClose;

	@OriginalMember(owner = "client!be", name = "x", descriptor = "Z")
	public boolean vFlip;

	@OriginalMember(owner = "client!be", name = "z", descriptor = "[Ljava/lang/Object;")
	public Object[] onHold;

	@OriginalMember(owner = "client!be", name = "E", descriptor = "[Ljava/lang/Object;")
	public Object[] onScroll;

	@OriginalMember(owner = "client!be", name = "G", descriptor = "[I")
	public int[] varcstrTriggers;

	@OriginalMember(owner = "client!be", name = "I", descriptor = "I")
	public int type;

	@OriginalMember(owner = "client!be", name = "V", descriptor = "[I")
	public int[] invSprite;

	@OriginalMember(owner = "client!be", name = "X", descriptor = "Z")
	public boolean hFlip;

	@OriginalMember(owner = "client!be", name = "Z", descriptor = "I")
	public int modelId;

	@OriginalMember(owner = "client!be", name = "bb", descriptor = "[Ljava/lang/Object;")
	public Object[] onUse;

	@OriginalMember(owner = "client!be", name = "fb", descriptor = "[Ljava/lang/Object;")
	public Object[] onDialogAbort;

	@OriginalMember(owner = "client!be", name = "gb", descriptor = "[I")
	public int[] clickMaskStart;

	@OriginalMember(owner = "client!be", name = "kb", descriptor = "[I")
	public int[] varcTriggers;

	@OriginalMember(owner = "client!be", name = "qb", descriptor = "[Ljava/lang/Object;")
	public Object[] onMinimapUnlock;

	@OriginalMember(owner = "client!be", name = "tb", descriptor = "[Ljava/lang/Object;")
	public Object[] onKey;

	@OriginalMember(owner = "client!be", name = "ub", descriptor = "[Ljava/lang/Object;")
	public Object[] onVarcstrTransmit;

	@OriginalMember(owner = "client!be", name = "Db", descriptor = "[Ljava/lang/Object;")
	public Object[] onDragRelease;

	@OriginalMember(owner = "client!be", name = "Fb", descriptor = "[B")
	public byte[] keyModifiers;

	@OriginalMember(owner = "client!be", name = "Jb", descriptor = "[Ljava/lang/Object;")
	public Object[] onResize;

	@OriginalMember(owner = "client!be", name = "Nb", descriptor = "[Ljava/lang/Object;")
	public Object[] onRelease;

	@OriginalMember(owner = "client!be", name = "Xb", descriptor = "[Ljava/lang/Object;")
	public Object[] onMouseOver;

	@OriginalMember(owner = "client!be", name = "Yb", descriptor = "[I")
	public int[] dragTargets;

	@OriginalMember(owner = "client!be", name = "dc", descriptor = "[I")
	public int[] objTypes;

	@OriginalMember(owner = "client!be", name = "fc", descriptor = "[Ljava/lang/Object;")
	public Object[] onMsg;

	@OriginalMember(owner = "client!be", name = "lc", descriptor = "[Lclient!be;")
	public Component[] createdComponents;

	@OriginalMember(owner = "client!be", name = "mc", descriptor = "[B")
	public byte[] keyCodes;

	@OriginalMember(owner = "client!be", name = "rc", descriptor = "[Ljava/lang/Object;")
	public Object[] onStockTransmit;

	@OriginalMember(owner = "client!be", name = "tc", descriptor = "[Ljava/lang/Object;")
	public Object[] onTimer;

	@OriginalMember(owner = "client!be", name = "yc", descriptor = "[I")
	public int[] invOffsetX;

	@OriginalMember(owner = "client!be", name = "Ac", descriptor = "[I")
	public int[] statTriggers;

	@OriginalMember(owner = "client!be", name = "Bc", descriptor = "[I")
	public int[] cs1ComparisonOpcodes;

	@OriginalMember(owner = "client!be", name = "Cc", descriptor = "[Ljava/lang/Object;")
	public Object[] onMouseRepeat;

	@OriginalMember(owner = "client!be", name = "Ic", descriptor = "[Ljava/lang/Object;")
	public Object[] onMouseLeave;

	@OriginalMember(owner = "client!be", name = "Jc", descriptor = "[Ljava/lang/Object;")
	public Object[] onVarpTransmit;

	@OriginalMember(owner = "client!be", name = "Nc", descriptor = "[I")
	public int[] varpTriggers;

	@OriginalMember(owner = "client!be", name = "Tc", descriptor = "[I")
	public int[] clickMaskWidth;

	@OriginalMember(owner = "client!be", name = "Xc", descriptor = "[Ljava/lang/Object;")
	public Object[] onDragStart;

	@OriginalMember(owner = "client!be", name = "ad", descriptor = "[[I")
	public int[][] cs1Scripts;

	@OriginalMember(owner = "client!be", name = "bd", descriptor = "[I")
	public int[] keyRepeatDelays;

	@OriginalMember(owner = "client!be", name = "cd", descriptor = "[I")
	public int[] invOffsetY;

	@OriginalMember(owner = "client!be", name = "gd", descriptor = "[Ljava/lang/Object;")
	public Object[] onUseWith;

	@OriginalMember(owner = "client!be", name = "kd", descriptor = "[I")
	public int[] cs1ComparisonOperands;

	@OriginalMember(owner = "client!be", name = "nd", descriptor = "[I")
	public int[] keyRepeatTimers;

	@OriginalMember(owner = "client!be", name = "qd", descriptor = "[Ljava/lang/Object;")
	public Object[] onClanTransmit;

	@OriginalMember(owner = "client!be", name = "rd", descriptor = "[Ljava/lang/Object;")
	public Object[] onOptionClick;

	@OriginalMember(owner = "client!be", name = "sd", descriptor = "[Ljava/lang/Object;")
	public Object[] onMiscTransmit;

	@OriginalMember(owner = "client!be", name = "wd", descriptor = "[I")
	public int[] objCounts;

	@OriginalMember(owner = "client!be", name = "H", descriptor = "Z")
	public boolean spriteTiling = false;

	@OriginalMember(owner = "client!be", name = "f", descriptor = "I")
	public int aspectHeight = 1;

	@OriginalMember(owner = "client!be", name = "R", descriptor = "I")
	public int height = 0;

	@OriginalMember(owner = "client!be", name = "S", descriptor = "I")
	public int halign = 0;

	@OriginalMember(owner = "client!be", name = "j", descriptor = "I")
	public int modelZoom = 100;

	@OriginalMember(owner = "client!be", name = "h", descriptor = "B")
	public byte xMode = 0;

	@OriginalMember(owner = "client!be", name = "jb", descriptor = "I")
	public int y = 0;

	@OriginalMember(owner = "client!be", name = "nb", descriptor = "I")
	public int dragDeadzone = 0;

	@OriginalMember(owner = "client!be", name = "W", descriptor = "I")
	public int activeModelSeqId = -1;

	@OriginalMember(owner = "client!be", name = "o", descriptor = "S")
	public short modelNearClip = 3000;

	@OriginalMember(owner = "client!be", name = "D", descriptor = "I")
	public int modelXAngle = 0;

	@OriginalMember(owner = "client!be", name = "Eb", descriptor = "I")
	public int modelOriginY = 0;

	@OriginalMember(owner = "client!be", name = "A", descriptor = "Z")
	public boolean modelOrtho = false;

	@OriginalMember(owner = "client!be", name = "zb", descriptor = "Z")
	public boolean filled = false;

	@OriginalMember(owner = "client!be", name = "y", descriptor = "I")
	public int dragDeadtime = 0;

	@OriginalMember(owner = "client!be", name = "hb", descriptor = "I")
	public int scrollY = 0;

	@OriginalMember(owner = "client!be", name = "xb", descriptor = "I")
	public int spriteId = -1;

	@OriginalMember(owner = "client!be", name = "eb", descriptor = "I")
	public int vpadding = 0;

	@OriginalMember(owner = "client!be", name = "a", descriptor = "Z")
	public boolean hidden = false;

	@OriginalMember(owner = "client!be", name = "Zb", descriptor = "I")
	public int lineWidth = 1;

	@OriginalMember(owner = "client!be", name = "Mb", descriptor = "I")
	public int defaultTargetCursor = -1;

	@OriginalMember(owner = "client!be", name = "O", descriptor = "I")
	public int createdComponentId = -1;

	@OriginalMember(owner = "client!be", name = "J", descriptor = "Z")
	public boolean dragging = false;

	@OriginalMember(owner = "client!ob", name = "e", descriptor = "Lclient!na;")
	public static final JagString EMPTY_STRING = JagString.parse("");
	@OriginalMember(owner = "client!be", name = "Sb", descriptor = "Lclient!na;")
	public JagString optionSuffix = EMPTY_STRING;

	@OriginalMember(owner = "client!be", name = "i", descriptor = "Z")
	public boolean mouseOver = false;

	@OriginalMember(owner = "client!be", name = "yb", descriptor = "I")
	public int valign = 0;

	@OriginalMember(owner = "client!be", name = "lb", descriptor = "I")
	public int hoverOverlayer = -1;

	@OriginalMember(owner = "client!be", name = "m", descriptor = "Z")
	public boolean lineFlipped = false;

	@OriginalMember(owner = "client!be", name = "pc", descriptor = "I")
	public int targetCursor = -1;

	@OriginalMember(owner = "client!be", name = "Qb", descriptor = "B")
	public byte dynamicHeightValue = 0;

	@OriginalMember(owner = "client!be", name = "bc", descriptor = "I")
	public int scrollMaxV = 0;

	@OriginalMember(owner = "client!be", name = "Y", descriptor = "Z")
	public boolean dragRenderBehavior = false;

	@OriginalMember(owner = "client!be", name = "ob", descriptor = "Z")
	public boolean shadowed = false;

	@OriginalMember(owner = "client!be", name = "cb", descriptor = "I")
	public int rectangleLoop = -1;

	@OriginalMember(owner = "client!be", name = "jc", descriptor = "I")
	public int seqNextFrame = 1;

	@OriginalMember(owner = "client!be", name = "Cb", descriptor = "I")
	public int overColor = 0;

	@OriginalMember(owner = "client!be", name = "Hb", descriptor = "Z")
	public boolean if3 = false;

	@OriginalMember(owner = "client!di", name = "F", descriptor = "Lclient!bf;")
	public static final ServerActiveProperties DEFAULT_SERVER_ACTIVE_PROPERTIES = new ServerActiveProperties(0, -1);

	@OriginalMember(owner = "client!be", name = "gc", descriptor = "Lclient!bf;")
	public ServerActiveProperties properties = DEFAULT_SERVER_ACTIVE_PROPERTIES;

	@OriginalMember(owner = "client!be", name = "cc", descriptor = "I")
	public int activeColor = 0;

	@OriginalMember(owner = "client!be", name = "Gb", descriptor = "Lclient!na;")
	public JagString text = EMPTY_STRING;

	@OriginalMember(owner = "client!be", name = "n", descriptor = "I")
	public int width = 0;

	@OriginalMember(owner = "client!be", name = "Wb", descriptor = "I")
	public int scrollX = 0;

	@OriginalMember(owner = "client!be", name = "sb", descriptor = "Z")
	public boolean noClickThrough = false;

	@OriginalMember(owner = "client!be", name = "U", descriptor = "I")
	private int activeModelId = -1;

	@OriginalMember(owner = "client!be", name = "N", descriptor = "I")
	public int overlayer = -1;

	@OriginalMember(owner = "client!be", name = "pb", descriptor = "I")
	public int aspectWidth = 1;

	@OriginalMember(owner = "client!be", name = "Q", descriptor = "I")
	public int objId = -1;

	@OriginalMember(owner = "client!be", name = "vb", descriptor = "I")
	public int activeOverColor = 0;

	@OriginalMember(owner = "client!be", name = "nc", descriptor = "I")
	public int modelZOffset = 0;

	@OriginalMember(owner = "client!be", name = "Dc", descriptor = "Lclient!na;")
	public JagString optionBase = EMPTY_STRING;

	@OriginalMember(owner = "client!be", name = "Lc", descriptor = "I")
	public int updatedVarcsReaderIndex = 0;

	@OriginalMember(owner = "client!be", name = "w", descriptor = "I")
	public int baseWidth = 0;

	@OriginalMember(owner = "client!be", name = "Mc", descriptor = "I")
	public int invMarginX = 0;

	@OriginalMember(owner = "client!be", name = "Ib", descriptor = "I")
	public int lastTransmitTimer = -1;

	@OriginalMember(owner = "client!be", name = "c", descriptor = "Z")
	public boolean hasAlpha = false;

	@OriginalMember(owner = "client!be", name = "F", descriptor = "I")
	public int modelViewportWidth = 0;

	@OriginalMember(owner = "client!be", name = "wb", descriptor = "I")
	public int alpha = 0;

	@OriginalMember(owner = "client!be", name = "hc", descriptor = "I")
	public int modelXOffset = 0;

	@OriginalMember(owner = "client!be", name = "Ub", descriptor = "Lclient!na;")
	public JagString optionCircumfix = EMPTY_STRING;

	@OriginalMember(owner = "client!be", name = "Lb", descriptor = "I")
	public int modelRotationSpeed = 0;

	@OriginalMember(owner = "client!be", name = "r", descriptor = "Lclient!na;")
	public JagString activeText = EMPTY_STRING;

	@OriginalMember(owner = "client!be", name = "Pc", descriptor = "I")
	public int outlineThickness = 0;

	@OriginalMember(owner = "client!be", name = "oc", descriptor = "I")
	public int modelFrameCycle = -1;

	@OriginalMember(owner = "client!be", name = "Rb", descriptor = "I")
	public int updatedVarcstrsReaderIndex = 0;

	@OriginalMember(owner = "client!be", name = "ic", descriptor = "I")
	public int modelOriginX = 0;

	@OriginalMember(owner = "client!be", name = "Sc", descriptor = "I")
	public int invMarginY = 0;

	@OriginalMember(owner = "client!be", name = "Tb", descriptor = "I")
	public int baseHeight = 0;

	@OriginalMember(owner = "client!be", name = "Fc", descriptor = "I")
	public int id = -1;

	@OriginalMember(owner = "client!be", name = "Yc", descriptor = "I")
	public int activeSpriteId = -1;

	@OriginalMember(owner = "client!be", name = "zc", descriptor = "B")
	public byte yMode = 0;

	@OriginalMember(owner = "client!be", name = "qc", descriptor = "I")
	public int seqCycle = 0;

	@OriginalMember(owner = "client!be", name = "uc", descriptor = "I")
	public int font = -1;

	@OriginalMember(owner = "client!be", name = "Pb", descriptor = "I")
	public int scrollMaxH = 0;

	@OriginalMember(owner = "client!be", name = "ec", descriptor = "I")
	public int updatedInventoriesReaderIndex = 0;

	@OriginalMember(owner = "client!be", name = "Vc", descriptor = "S")
	public short modelViewAngle = 0;

	@OriginalMember(owner = "client!be", name = "ed", descriptor = "I")
	public int angle2d = 0;

	@OriginalMember(owner = "client!be", name = "id", descriptor = "I")
	public int modelSeqId = -1;

	@OriginalMember(owner = "client!be", name = "Rc", descriptor = "Lclient!na;")
	public JagString option = LocalizedText.OK;

	@OriginalMember(owner = "client!be", name = "Gc", descriptor = "I")
	public int modelYOffset = 0;

	@OriginalMember(owner = "client!be", name = "vc", descriptor = "I")
	public int objCount = 0;

	@OriginalMember(owner = "client!be", name = "Uc", descriptor = "I")
	public int rectangle = -1;

	@OriginalMember(owner = "client!be", name = "K", descriptor = "I")
	public int clientCode = 0;

	@OriginalMember(owner = "client!be", name = "Oc", descriptor = "I")
	public int shadowColor = 0;

	@OriginalMember(owner = "client!be", name = "fd", descriptor = "Lclient!be;")
	public Component dragParent = null;

	@OriginalMember(owner = "client!be", name = "od", descriptor = "I")
	public int updatedStatsReaderIndex = 0;

	@OriginalMember(owner = "client!be", name = "ab", descriptor = "I")
	public int modelType = 1;

	@OriginalMember(owner = "client!be", name = "md", descriptor = "Z")
	public boolean modelTransparent = false;

	@OriginalMember(owner = "client!be", name = "hd", descriptor = "B")
	public byte dynamicWidthValue = 0;

	@OriginalMember(owner = "client!be", name = "Wc", descriptor = "I")
	private int activeModelType = 1;

	@OriginalMember(owner = "client!be", name = "pd", descriptor = "I")
	public int modelViewportHeight = 0;

	@OriginalMember(owner = "client!be", name = "ld", descriptor = "I")
	public int modelYAngle = 0;

	@OriginalMember(owner = "client!be", name = "T", descriptor = "Z")
	public boolean hasEventHandlers = false;

	@OriginalMember(owner = "client!be", name = "vd", descriptor = "I")
	public int baseX = 0;

	@OriginalMember(owner = "client!be", name = "jd", descriptor = "I")
	public int x = 0;

	@OriginalMember(owner = "client!be", name = "l", descriptor = "I")
	public int baseY = 0;

	@OriginalMember(owner = "client!be", name = "Bb", descriptor = "Z")
	public boolean objDrawText = true;

	@OriginalMember(owner = "client!be", name = "Kc", descriptor = "I")
	public int seqFrame = 0;

	@OriginalMember(owner = "client!be", name = "mb", descriptor = "I")
	public int updatedVarpsReaderIndex = 0;

	@OriginalMember(owner = "client!be", name = "rb", descriptor = "I")
	public int color = 0;

	@OriginalMember(owner = "client!be", name = "xd", descriptor = "I")
	public int buttonType = 0;

	@OriginalMember(owner = "client!ec", name = "a", descriptor = "(II)V")
	public static void clean() {
		sprites.clean(50);
		models.clean(50);
		fonts.clean(50);
	}

	@OriginalMember(owner = "client!lh", name = "d", descriptor = "(B)V")
	public static void clear() {
		sprites.clear();
		models.clear();
		fonts.clear();
	}

	@OriginalMember(owner = "client!da", name = "h", descriptor = "(B)V")
	public static void removeSoft() {
		sprites.removeSoft();
		models.removeSoft();
		fonts.removeSoft();
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(IIB)V")
	public final void setDragTarget(@OriginalArg(0) int index, @OriginalArg(1) int target) {
		if (this.dragTargets == null || this.dragTargets.length <= index) {
			@Pc(18) int[] newTargets = new int[index + 1];
			if (this.dragTargets != null) {
				@Pc(24) int i;
				for (i = 0; i < this.dragTargets.length; i++) {
					newTargets[i] = this.dragTargets[i];
				}
				for (i = this.dragTargets.length; i < index; i++) {
					newTargets[i] = -1;
				}
			}
			this.dragTargets = newTargets;
		}
		this.dragTargets[index] = target;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(I)Z")
	public final boolean buildClickMask() {
		if (this.clickMaskStart != null) {
			return true;
		}
		@Pc(18) SoftwareIndexedSprite sprite = SpriteLoader.loadSoftwareIndexedSprite(this.spriteId, InterfaceList.spriteProvider);
		if (sprite == null) {
			return false;
		}
		sprite.trim();
		this.clickMaskStart = new int[sprite.height];
		this.clickMaskWidth = new int[sprite.height];
		for (@Pc(37) int y = 0; y < sprite.height; y++) {
			@Pc(47) int startX = 0;
			@Pc(50) int endX = sprite.width;
			@Pc(52) int x;
			for (x = 0; x < sprite.width; x++) {
				if (sprite.pixels[sprite.width * y + x] != 0) {
					startX = x;
					break;
				}
			}
			for (x = startX; x < sprite.width; x++) {
				if (sprite.pixels[y * sprite.width + x] == 0) {
					endX = x;
					break;
				}
			}
			this.clickMaskStart[y] = startX;
			this.clickMaskWidth[y] = endX - startX;
		}
		return true;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(BLclient!na;I)V")
	public final void setOp(@OriginalArg(1) JagString op, @OriginalArg(2) int index) {
		if (this.ops == null || this.ops.length <= index) {
			@Pc(23) JagString[] newOps = new JagString[index + 1];
			if (this.ops != null) {
				for (@Pc(30) int i = 0; i < this.ops.length; i++) {
					newOps[i] = this.ops[i];
				}
			}
			this.ops = newOps;
		}
		this.ops[index] = op;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(ILclient!wa;)V")
	public final void decodeIf1(@OriginalArg(1) Buffer buffer) {
		this.if3 = false;
		this.type = buffer.g1();
		this.buttonType = buffer.g1();
		this.clientCode = buffer.g2();
		this.baseX = buffer.g2b();
		this.baseY = buffer.g2b();
		this.baseWidth = buffer.g2();
		this.baseHeight = buffer.g2();
		this.dynamicWidthValue = 0;
		this.dynamicHeightValue = 0;
		this.yMode = 0;
		this.xMode = 0;
		this.alpha = buffer.g1();
		this.overlayer = buffer.g2();
		if (this.overlayer == 65535) {
			this.overlayer = -1;
		} else {
			this.overlayer += this.id & 0xFFFF0000;
		}
		this.hoverOverlayer = buffer.g2();
		if (this.hoverOverlayer == 65535) {
			this.hoverOverlayer = -1;
		}
		@Pc(109) int numComparisons = buffer.g1();
		@Pc(125) int numScripts;
		if (numComparisons > 0) {
			this.cs1ComparisonOperands = new int[numComparisons];
			this.cs1ComparisonOpcodes = new int[numComparisons];
			for (numScripts = 0; numScripts < numComparisons; numScripts++) {
				this.cs1ComparisonOpcodes[numScripts] = buffer.g1();
				this.cs1ComparisonOperands[numScripts] = buffer.g2();
			}
		}
		numScripts = buffer.g1();
		@Pc(164) int scriptIdx;
		@Pc(175) int scriptLen;
		@Pc(183) int j;
		if (numScripts > 0) {
			this.cs1Scripts = new int[numScripts][];
			for (scriptIdx = 0; scriptIdx < numScripts; scriptIdx++) {
				scriptLen = buffer.g2();
				this.cs1Scripts[scriptIdx] = new int[scriptLen];
				for (j = 0; j < scriptLen; j++) {
					this.cs1Scripts[scriptIdx][j] = buffer.g2();
					if (this.cs1Scripts[scriptIdx][j] == 65535) {
						this.cs1Scripts[scriptIdx][j] = -1;
					}
				}
			}
		}
		if (this.type == 0) {
			this.scrollMaxV = buffer.g2();
			this.hidden = buffer.g1() == 1;
		}
		if (this.type == 1) {
			buffer.g2();
			buffer.g1();
		}
		scriptIdx = 0;
		if (this.type == 2) {
			this.dynamicHeightValue = 3;
			this.objCounts = new int[this.baseWidth * this.baseHeight];
			this.objTypes = new int[this.baseHeight * this.baseWidth];
			this.dynamicWidthValue = 3;
			scriptLen = buffer.g1();
			j = buffer.g1();
			if (scriptLen == 1) {
				scriptIdx = 268435456;
			}
			@Pc(312) int usable = buffer.g1();
			if (j == 1) {
				scriptIdx |= 0x40000000;
			}
			if (usable == 1) {
				scriptIdx |= Integer.MIN_VALUE;
			}
			@Pc(333) int usableOnSelf = buffer.g1();
			if (usableOnSelf == 1) {
				scriptIdx |= 0x20000000;
			}
			this.invMarginX = buffer.g1();
			this.invMarginY = buffer.g1();
			this.invOffsetY = new int[20];
			this.invOffsetX = new int[20];
			this.invSprite = new int[20];
			@Pc(364) int i;
			for (i = 0; i < 20; i++) {
				@Pc(371) int hasSprite = buffer.g1();
				if (hasSprite == 1) {
					this.invOffsetX[i] = buffer.g2b();
					this.invOffsetY[i] = buffer.g2b();
					this.invSprite[i] = buffer.g4();
				} else {
					this.invSprite[i] = -1;
				}
			}
			this.invOptions = new JagString[5];
			for (i = 0; i < 5; i++) {
				@Pc(418) JagString invOption = buffer.gjstr();
				if (invOption.length() > 0) {
					this.invOptions[i] = invOption;
					scriptIdx |= 0x1 << i + 23;
				}
			}
		}
		if (this.type == 3) {
			this.filled = buffer.g1() == 1;
		}
		if (this.type == 4 || this.type == 1) {
			this.halign = buffer.g1();
			this.valign = buffer.g1(); // this is an educated guess, follow arg8 in Font.renderParagraphAlpha
			this.vpadding = buffer.g1(); // this is an educated guess, follow arg9 in Font.renderParagraphAlpha
			this.font = buffer.g2();
			if (this.font == 65535) {
				this.font = -1;
			}
			this.shadowed = buffer.g1() == 1;
		}
		if (this.type == 4) {
			this.text = buffer.gjstr();
			this.activeText = buffer.gjstr();
		}
		if (this.type == 1 || this.type == 3 || this.type == 4) {
			this.color = buffer.g4();
		}
		if (this.type == 3 || this.type == 4) {
			this.activeColor = buffer.g4();
			this.overColor = buffer.g4();
			this.activeOverColor = buffer.g4();
		}
		if (this.type == 5) {
			this.spriteId = buffer.g4();
			this.activeSpriteId = buffer.g4();
		}
		if (this.type == 6) {
			this.modelType = 1;
			this.modelId = buffer.g2();
			if (this.modelId == 65535) {
				this.modelId = -1;
			}
			this.activeModelType = 1;
			this.activeModelId = buffer.g2();
			if (this.activeModelId == 65535) {
				this.activeModelId = -1;
			}
			this.modelSeqId = buffer.g2();
			if (this.modelSeqId == 65535) {
				this.modelSeqId = -1;
			}
			this.activeModelSeqId = buffer.g2();
			if (this.activeModelSeqId == 65535) {
				this.activeModelSeqId = -1;
			}
			this.modelZoom = buffer.g2();
			this.modelXAngle = buffer.g2();
			this.modelYAngle = buffer.g2();
		}
		if (this.type == 7) {
			this.dynamicHeightValue = 3;
			this.dynamicWidthValue = 3;
			this.objCounts = new int[this.baseHeight * this.baseWidth];
			this.objTypes = new int[this.baseWidth * this.baseHeight];
			this.halign = buffer.g1();
			this.font = buffer.g2();
			if (this.font == 65535) {
				this.font = -1;
			}
			this.shadowed = buffer.g1() == 1;
			this.color = buffer.g4();
			this.invMarginX = buffer.g2b();
			this.invMarginY = buffer.g2b();
			int invHasOptions = buffer.g1();
			if (invHasOptions == 1) {
				scriptIdx |= 0x40000000;
			}
			this.invOptions = new JagString[5];
			for (int i = 0; i < 5; i++) {
				@Pc(756) JagString invOption = buffer.gjstr();
				if (invOption.length() > 0) {
					this.invOptions[i] = invOption;
					scriptIdx |= 0x1 << i + 23;
				}
			}
		}
		if (this.type == 8) {
			this.text = buffer.gjstr();
		}
		if (this.buttonType == 2 || this.type == 2) {
			this.optionCircumfix = buffer.gjstr();
			this.optionSuffix = buffer.gjstr();
			scriptLen = buffer.g2() & 0x3F;
			scriptIdx |= scriptLen << 11;
		}
		if (this.buttonType == 1 || this.buttonType == 4 || this.buttonType == 5 || this.buttonType == 6) {
			this.option = buffer.gjstr();
			if (this.option.length() == 0) {
				if (this.buttonType == 1) {
					this.option = LocalizedText.OK;
				}
				if (this.buttonType == 4) {
					this.option = LocalizedText.SELECT;
				}
				if (this.buttonType == 5) {
					this.option = LocalizedText.SELECT;
				}
				if (this.buttonType == 6) {
					this.option = LocalizedText.CONTINUE;
				}
			}
		}
		if (this.buttonType == 1 || this.buttonType == 4 || this.buttonType == 5) {
			scriptIdx |= 0x400000;
		}
		if (this.buttonType == 6) {
			scriptIdx |= 0x1;
		}
		this.properties = new ServerActiveProperties(scriptIdx, -1);
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(ZI)Lclient!qf;")
	public final Sprite getInvSprite(@OriginalArg(1) int index) {
		loadFailed = false;
		if (index < 0 || index >= this.invSprite.length) {
			return null;
		}
		@Pc(29) int spriteId = this.invSprite[index];
		if (spriteId == -1) {
			return null;
		}
		@Pc(43) Sprite sprite = (Sprite) sprites.get(spriteId);
		if (sprite != null) {
			return sprite;
		}
		sprite = SpriteLoader.loadSprites(spriteId, InterfaceList.spriteProvider);
		if (sprite == null) {
			loadFailed = true;
		} else {
			sprites.put(sprite, spriteId);
		}
		return sprite;
	}

	@OriginalMember(owner = "client!be", name = "b", descriptor = "(ILclient!wa;)[Ljava/lang/Object;")
	private Object[] decodeEventHandler(@OriginalArg(1) Buffer buffer) {
		@Pc(11) int count = buffer.g1();
		if (count == 0) {
			return null;
		}
		@Pc(26) Object[] result = new Object[count];
		for (@Pc(28) int i = 0; i < count; i++) {
			@Pc(35) int valueType = buffer.g1();
			if (valueType == 0) {
				result[i] = Integer.valueOf(buffer.g4());
			} else if (valueType == 1) {
				result[i] = buffer.gjstr();
			}
		}
		this.hasEventHandlers = true;
		return result;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(Lclient!wa;Z)[I")
	private int[] decodeTriggers(@OriginalArg(0) Buffer buffer) {
		@Pc(9) int count = buffer.g1();
		if (count == 0) {
			return null;
		}
		@Pc(19) int[] triggers = new int[count];
		for (@Pc(26) int i = 0; i < count; i++) {
			triggers[i] = buffer.g4();
		}
		return triggers;
	}

	@OriginalMember(owner = "client!be", name = "b", descriptor = "(III)V")
	public final void swapObjs(@OriginalArg(0) int oldIndex, @OriginalArg(1) int newIndex) {
		@Pc(8) int tmpObj = this.objTypes[newIndex];
		this.objTypes[newIndex] = this.objTypes[oldIndex];
		this.objTypes[oldIndex] = tmpObj;

		@Pc(34) int tmpCount = this.objCounts[newIndex];
		this.objCounts[newIndex] = this.objCounts[oldIndex];
		this.objCounts[oldIndex] = tmpCount;
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(ILclient!tk;IIIZLclient!hh;)Lclient!ak;")
	public final Model getModel(@OriginalArg(0) int frame, @OriginalArg(1) SeqType seq, @OriginalArg(2) int nextFrame, @OriginalArg(4) int tweenDelta, @OriginalArg(5) boolean active, @OriginalArg(6) PlayerAppearance appearance) {
		loadFailed = false;
		@Pc(10) int mType;
		@Pc(13) int mId;
		if (active) {
			mType = this.activeModelType;
			mId = this.activeModelId;
		} else {
			mId = this.modelId;
			mType = this.modelType;
		}
		if (mType == 0) {
			return null;
		} else if (mType == 1 && mId == -1) {
			return null;
		} else {
			@Pc(61) Model model;
			if (mType == 1) {
				model = (Model) models.get((mType << 16) + mId);
				if (model == null) {
					@Pc(69) RawModel rawModel = RawModel.create(InterfaceList.modelProvider, mId);
					if (rawModel == null) {
						loadFailed = true;
						return null;
					}
					model = rawModel.createModel(64, 768, -50, -10, -50);
					models.put(model, mId + (mType << 16));
				}
				if (seq != null) {
					model = seq.animateEntity(model, frame, tweenDelta, nextFrame);
				}
				return model;
			} else if (mType == 2) {
				model = NpcTypeList.get(mId).getHeadModel(seq, tweenDelta, frame, nextFrame);
				if (model == null) {
					loadFailed = true;
					return null;
				} else {
					return model;
				}
			} else if (mType == 3) {
				if (appearance == null) {
					return null;
				}
				model = appearance.getHeadModel(tweenDelta, seq, nextFrame, frame);
				if (model == null) {
					loadFailed = true;
					return null;
				} else {
					return model;
				}
			} else if (mType == 4) {
				@Pc(164) ObjType objType = ObjTypeList.get(mId);
				@Pc(173) Model objModel = objType.getModel(frame, tweenDelta, seq, 10, nextFrame);
				if (objModel == null) {
					loadFailed = true;
					return null;
				} else {
					return objModel;
				}
			} else if (mType == 6) {
				model = NpcTypeList.get(mId).getBodyModel(null, 0, 0, frame, tweenDelta, nextFrame, null, 0, seq);
				if (model == null) {
					loadFailed = true;
					return null;
				} else {
					return model;
				}
			} else if (mType != 7) {
				return null;
			} else if (appearance == null) {
				return null;
			} else {
				@Pc(227) int upper = this.modelId >>> 16;
				@Pc(232) int lower = this.modelId & 0xFFFF;
				@Pc(235) int frameCycle = this.modelFrameCycle;
				@Pc(246) Model partialModel = appearance.getPartialHeadModel(frame, frameCycle, upper, tweenDelta, seq, nextFrame, lower);
				if (partialModel == null) {
					loadFailed = true;
					return null;
				} else {
					return partialModel;
				}
			}
		}
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(BZ)Lclient!qf;")
	public final Sprite getSprite(@OriginalArg(1) boolean active) {
		loadFailed = false;
		@Pc(12) int spriteId;
		if (active) {
			spriteId = this.activeSpriteId;
		} else {
			spriteId = this.spriteId;
		}
		if (spriteId == -1) {
			return null;
		}
		@Pc(66) long hash = ((this.vFlip ? 1L : 0L) << 38) + ((this.hasAlpha ? 1L : 0L) << 35) + (long) spriteId + ((long) this.outlineThickness << 36) + ((this.hFlip ? 1L : 0L) << 39) + ((long) this.shadowColor << 40);
		@Pc(72) Sprite sprite = (Sprite) sprites.get(hash);
		if (sprite != null) {
			return sprite;
		}
		@Pc(85) SoftwareSprite swSprite;
		if (this.hasAlpha) {
			swSprite = SoftwareSprite.loadSoftwareAlphaSprite(InterfaceList.spriteProvider, spriteId);
		} else {
			swSprite = SpriteLoader.loadSoftwareSprite(0, InterfaceList.spriteProvider, spriteId);
		}
		if (swSprite == null) {
			loadFailed = true;
			return null;
		}
		if (this.vFlip) {
			swSprite.flipVertical();
		}
		if (this.hFlip) {
			swSprite.flipHorizontal();
		}
		if (this.outlineThickness > 0) {
			swSprite.pad(this.outlineThickness);
		}
		if (this.outlineThickness >= 1) {
			swSprite.drawOutline(1);
		}
		if (this.outlineThickness >= 2) {
			swSprite.drawOutline(16777215);
		}
		if (this.shadowColor != 0) {
			swSprite.drawShadow(this.shadowColor);
		}
		if (!GlRenderer.enabled) {
			sprite = swSprite;
		} else if (swSprite instanceof SoftwareAlphaSprite) {
			sprite = new GlAlphaSprite(swSprite);
		} else {
			sprite = new GlSprite(swSprite);
		}
		sprites.put(sprite, hash);
		return sprite;
	}

	@OriginalMember(owner = "client!be", name = "c", descriptor = "(ILclient!wa;)V")
	public final void decodeIf3(@OriginalArg(1) Buffer buffer) {
		this.if3 = true;
		buffer.offset++;
		this.type = buffer.g1();
		if ((this.type & 0x80) != 0) {
			this.type &= 0x7F;
			buffer.gjstr();
		}
		this.clientCode = buffer.g2();
		this.baseX = buffer.g2b();
		this.baseY = buffer.g2b();
		this.baseWidth = buffer.g2();
		this.baseHeight = buffer.g2();
		this.dynamicWidthValue = buffer.g1b();
		this.dynamicHeightValue = buffer.g1b();
		this.yMode = buffer.g1b();
		this.xMode = buffer.g1b();
		this.overlayer = buffer.g2();
		if (this.overlayer == 65535) {
			this.overlayer = -1;
		} else {
			this.overlayer = (this.id & 0xFFFF0000) + this.overlayer;
		}
		this.hidden = buffer.g1() == 1;
		if (this.type == 0) {
			this.scrollMaxH = buffer.g2();
			this.scrollMaxV = buffer.g2();
			this.noClickThrough = buffer.g1() == 1;
		}
		@Pc(175) int flags;
		if (this.type == 5) {
			this.spriteId = buffer.g4();
			this.angle2d = buffer.g2();
			flags = buffer.g1();
			this.hasAlpha = (flags & 0x2) != 0;
			this.spriteTiling = (flags & 0x1) != 0;
			this.alpha = buffer.g1();
			this.outlineThickness = buffer.g1();
			this.shadowColor = buffer.g4();
			this.vFlip = buffer.g1() == 1;
			this.hFlip = buffer.g1() == 1;
		}
		if (this.type == 6) {
			this.modelType = 1;
			this.modelId = buffer.g2();
			if (this.modelId == 65535) {
				this.modelId = -1;
			}
			this.modelOriginX = buffer.g2b();
			this.modelOriginY = buffer.g2b();
			this.modelXAngle = buffer.g2();
			this.modelYAngle = buffer.g2();
			this.modelYOffset = buffer.g2();
			this.modelZoom = buffer.g2();
			this.modelSeqId = buffer.g2();
			if (this.modelSeqId == 65535) {
				this.modelSeqId = -1;
			}
			this.modelOrtho = buffer.g1() == 1;
			this.modelViewAngle = (short) buffer.g2();
			this.modelNearClip = (short) buffer.g2();
			this.modelTransparent = buffer.g1() == 1;
			if (this.dynamicWidthValue != 0) {
				this.modelViewportWidth = buffer.g2();
			}
			if (this.dynamicHeightValue != 0) {
				this.modelViewportHeight = buffer.g2();
			}
		}
		if (this.type == 4) {
			this.font = buffer.g2();
			if (this.font == 65535) {
				this.font = -1;
			}
			this.text = buffer.gjstr();
			this.vpadding = buffer.g1();
			this.halign = buffer.g1();
			this.valign = buffer.g1();
			this.shadowed = buffer.g1() == 1;
			this.color = buffer.g4();
		}
		if (this.type == 3) {
			this.color = buffer.g4();
			this.filled = buffer.g1() == 1;
			this.alpha = buffer.g1();
		}
		if (this.type == 9) {
			this.lineWidth = buffer.g1();
			this.color = buffer.g4();
			this.lineFlipped = buffer.g1() == 1;
		}
		flags = buffer.g3();
		@Pc(471) int keyData = buffer.g1();
		@Pc(497) int opFlags;
		if (keyData != 0) {
			this.keyRepeatDelays = new int[10];
			this.keyCodes = new byte[10];
			this.keyModifiers = new byte[10];
			while (keyData != 0) {
				opFlags = (keyData >> 4) - 1;
				keyData = buffer.g1() | keyData << 8;
				keyData &= 0xFFF;
				if (keyData == 4095) {
					this.keyRepeatDelays[opFlags] = -1;
				} else {
					this.keyRepeatDelays[opFlags] = keyData;
				}
				this.keyCodes[opFlags] = buffer.g1b();
				this.keyModifiers[opFlags] = buffer.g1b();
				keyData = buffer.g1();
			}
		}
		this.optionBase = buffer.gjstr();
		opFlags = buffer.g1();
		@Pc(557) int opCount = opFlags & 0xF;
		@Pc(567) int targetOverride;
		if (opCount > 0) {
			this.ops = new JagString[opCount];
			for (targetOverride = 0; targetOverride < opCount; targetOverride++) {
				this.ops[targetOverride] = buffer.gjstr();
			}
		}
		@Pc(584) int dragOpCount = opFlags >> 4;
		if (dragOpCount > 0) {
			targetOverride = buffer.g1();
			this.dragTargets = new int[targetOverride + 1];
			for (@Pc(599) int i = 0; i < this.dragTargets.length; i++) {
				this.dragTargets[i] = -1;
			}
			this.dragTargets[targetOverride] = buffer.g2();
		}
		if (dragOpCount > 1) {
			targetOverride = buffer.g1();
			this.dragTargets[targetOverride] = buffer.g2();
		}
		this.dragDeadzone = buffer.g1();
		this.dragDeadtime = buffer.g1();
		this.dragRenderBehavior = buffer.g1() == 1;
		targetOverride = -1;
		this.optionCircumfix = buffer.gjstr();
		if (ServerActiveProperties.getTargetMask(flags) != 0) {
			targetOverride = buffer.g2();
			this.targetCursor = buffer.g2();
			if (targetOverride == 65535) {
				targetOverride = -1;
			}
			if (this.targetCursor == 65535) {
				this.targetCursor = -1;
			}
			this.defaultTargetCursor = buffer.g2();
			if (this.defaultTargetCursor == 65535) {
				this.defaultTargetCursor = -1;
			}
		}
		this.properties = new ServerActiveProperties(flags, targetOverride);
		this.onLoad = this.decodeEventHandler(buffer);
		this.onMouseOver = this.decodeEventHandler(buffer);
		this.onMouseLeave = this.decodeEventHandler(buffer);
		this.onUseWith = this.decodeEventHandler(buffer);
		this.onUse = this.decodeEventHandler(buffer);
		this.onVarpTransmit = this.decodeEventHandler(buffer);
		this.onInvTransmit = this.decodeEventHandler(buffer);
		this.onStatTransmit = this.decodeEventHandler(buffer);
		this.onTimer = this.decodeEventHandler(buffer);
		this.onOptionClick = this.decodeEventHandler(buffer);
		this.onMouseRepeat = this.decodeEventHandler(buffer);
		this.onClickRepeat = this.decodeEventHandler(buffer);
		this.onDrag = this.decodeEventHandler(buffer);
		this.onRelease = this.decodeEventHandler(buffer);
		this.onHold = this.decodeEventHandler(buffer);
		this.onDragStart = this.decodeEventHandler(buffer);
		this.onDragRelease = this.decodeEventHandler(buffer);
		this.onScroll = this.decodeEventHandler(buffer);
		this.onVarcTransmit = this.decodeEventHandler(buffer);
		this.onVarcstrTransmit = this.decodeEventHandler(buffer);
		this.varpTriggers = this.decodeTriggers(buffer);
		this.inventoryTriggers = this.decodeTriggers(buffer);
		this.statTriggers = this.decodeTriggers(buffer);
		this.varcTriggers = this.decodeTriggers(buffer);
		this.varcstrTriggers = this.decodeTriggers(buffer);
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "([Lclient!ok;I)Lclient!rk;")
	public final Font getFont(@OriginalArg(0) IndexedSprite[] nameIcons) {
		loadFailed = false;
		if (this.font == -1) {
			return null;
		}
		@Pc(21) Font cachedFont = (Font) fonts.get(this.font);
		if (cachedFont != null) {
			return cachedFont;
		}
		cachedFont = Font.load(this.font, InterfaceList.spriteProvider, InterfaceList.fontProvider);
		if (cachedFont == null) {
			loadFailed = true;
		} else {
			cachedFont.setNameIcons(nameIcons, null);
			fonts.put(cachedFont, this.font);
		}
		return cachedFont;
	}
}
