package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;
import plugin.api.API;
import plugin.api.MiniMenuEntry;

public class MiniMenu {
	@OriginalMember(owner = "client!df", name = "l", descriptor = "Lclient!na;")
	public static final JagString COLOR_GREEN = JagString.parse("<col=00ff00>");
	@OriginalMember(owner = "client!sc", name = "g", descriptor = "Lclient!na;")
	public static final JagString ARROW_ORANGE = JagString.parse(" )2> <col=ff9040>");
	@OriginalMember(owner = "client!uj", name = "C", descriptor = "[Lclient!na;")
	public static final JagString[] ops = new JagString[500];
	@OriginalMember(owner = "client!t", name = "v", descriptor = "[Lclient!na;")
	public static final JagString[] opBases = new JagString[500];
	@OriginalMember(owner = "client!d", name = "eb", descriptor = "[S")
	public static final short[] actions = new short[500];
	@OriginalMember(owner = "client!pf", name = "r", descriptor = "[I")
	public static final int[] cursors = new int[500];
	@OriginalMember(owner = "client!mi", name = "U", descriptor = "[J")
	public static final long[] keys = new long[500];
	@OriginalMember(owner = "client!pl", name = "e", descriptor = "[I")
	public static final int[] intArgs1 = new int[500];
	@OriginalMember(owner = "client!ef", name = "c", descriptor = "[I")
	public static final int[] intArgs2 = new int[500];
	@OriginalMember(owner = "client!nm", name = "bb", descriptor = "Lclient!na;")
	public static final JagString COLOR_RED = JagString.parse("<col=ff0000>");
	@OriginalMember(owner = "client!fb", name = "p", descriptor = "Lclient!na;")
	public static final JagString COLOR_LIGHT_RED = JagString.parse("<col=ff3000>");
	@OriginalMember(owner = "client!sc", name = "D", descriptor = "Lclient!na;")
	public static final JagString COLOR_ORANGE = JagString.parse("<col=ff7000>");
	@OriginalMember(owner = "client!si", name = "Z", descriptor = "Lclient!na;")
	public static final JagString COLOR_LIGHT_ORANGE = JagString.parse("<col=ffb000>");
	@OriginalMember(owner = "client!ag", name = "bb", descriptor = "Lclient!na;")
	public static final JagString COLOR_LIGHT_GREEN = JagString.parse("<col=40ff00>");
	@OriginalMember(owner = "client!dc", name = "v", descriptor = "Lclient!na;")
	public static final JagString COLOR_YELLOW_GREEN = JagString.parse("<col=c0ff00>");
	@OriginalMember(owner = "client!sf", name = "g", descriptor = "Lclient!na;")
	public static final JagString COLOR_YELLOW = JagString.parse("<col=ffff00>");
	@OriginalMember(owner = "client!vg", name = "f", descriptor = "Lclient!na;")
	public static final JagString COLOR_BRIGHT_GREEN = JagString.parse("<col=80ff00>");
	@OriginalMember(owner = "client!r", name = "d", descriptor = "Z")
	public static final boolean DEBUG_OPS = false;
	@OriginalMember(owner = "client!se", name = "m", descriptor = "Lclient!na;")
	public static final JagString ARROW_SUFFIX = JagString.parse(" )2>");
	@OriginalMember(owner = "client!a", name = "j", descriptor = "Lclient!na;")
	public static final JagString MORE_OPTIONS_PREFIX = JagString.parse("<col=ffffff> )4 ");
	@OriginalMember(owner = "client!cb", name = "fb", descriptor = "Lclient!na;")
	public static final JagString COLON_SEPARATOR = JagString.parse(": ");
	@OriginalMember(owner = "client!qf", name = "Q", descriptor = "Lclient!na;")
	public static final JagString ARROW_YELLOW = JagString.parse(" )2> <col=ffff00>");
	@OriginalMember(owner = "client!qf", name = "R", descriptor = "Lclient!na;")
	public static final JagString ARROW_PLAIN = JagString.parse(" )2> ");
	@OriginalMember(owner = "client!aj", name = "R", descriptor = "Lclient!na;")
	public static final JagString COLOR_ITEM_ORANGE = JagString.parse("<col=ff9040>");
	@OriginalMember(owner = "client!fl", name = "V", descriptor = "Lclient!na;")
	public static final JagString CLOSE_PARENTHESIS = JagString.parse("(Y");
	@OriginalMember(owner = "client!gd", name = "c", descriptor = "Lclient!na;")
	public static final JagString PLUS = JagString.parse(")0");
	@OriginalMember(owner = "client!jj", name = "g", descriptor = "Lclient!na;")
	public static final JagString OPEN_PARENTHESIS = JagString.parse(" (X");
	@OriginalMember(owner = "client!qi", name = "B", descriptor = "Lclient!na;")
	public static final JagString COLOR_WHITE = JagString.parse("<col=ffffff>");
	@OriginalMember(owner = "client!cb", name = "ab", descriptor = "Lclient!na;")
	public static final JagString ARROW_CYAN = JagString.parse(" )2> <col=00ffff>");
	@OriginalMember(owner = "client!ud", name = "Q", descriptor = "Lclient!na;")
	public static final JagString QUANTITY_SEPARATOR = JagString.parse(" x ");
	@OriginalMember(owner = "client!hd", name = "l", descriptor = "Lclient!na;")
	public static final JagString NULL = JagString.parse("null");
	@OriginalMember(owner = "client!ib", name = "k", descriptor = "Lclient!na;")
	public static final JagString ARROW_WHITE = JagString.parse(" )2> <col=ffffff>");
	@OriginalMember(owner = "client!tg", name = "e", descriptor = "Lclient!na;")
	public static final JagString COLOR_CYAN = JagString.parse("<col=00ffff>");
	@OriginalMember(owner = "client!uf", name = "q", descriptor = "Lclient!na;")
	public static final JagString NULL_TEXT = JagString.parse("Null");
	@OriginalMember(owner = "client!e", name = "pc", descriptor = "[I")
	public static final int[] tooltipBounds = new int[4];
	@OriginalMember(owner = "client!af", name = "l", descriptor = "[S")
	public static final short[] PLAYER_OPTION_ACTION_CODES = new short[]{30, 6, 31, 29, 10, 44, 37, 57};
	@OriginalMember(owner = "client!ck", name = "D", descriptor = "Lclient!na;")
	public static JagString selectedObjText = null;
	@OriginalMember(owner = "client!hn", name = "W", descriptor = "Lclient!na;")
	public static JagString targetVerb = null;
	@OriginalMember(owner = "client!sk", name = "kb", descriptor = "I")
	public static int size = 0;
	@OriginalMember(owner = "client!v", name = "b", descriptor = "Lclient!be;")
	public static Component pressedInventoryComponent;
	@OriginalMember(owner = "client!gd", name = "i", descriptor = "Lclient!na;")
	public static JagString targetOpBase = null;
	@OriginalMember(owner = "client!vd", name = "C", descriptor = "I")
	public static int itemTargetMode = 0;
	@OriginalMember(owner = "client!wf", name = "f", descriptor = "I")
	public static int targetMask;
	@OriginalMember(owner = "client!wf", name = "d", descriptor = "I")
	public static int selectedObjId;
	@OriginalMember(owner = "client!be", name = "Ec", descriptor = "I")
	public static int targetChildId = -1;
	@OriginalMember(owner = "client!fl", name = "P", descriptor = "I")
	public static int itemInteractionTick = 0;
	@OriginalMember(owner = "client!hj", name = "e", descriptor = "I")
	public static int targetInterfaceId;
	@OriginalMember(owner = "client!mh", name = "Y", descriptor = "Z")
	public static boolean walkPending = false;
	@OriginalMember(owner = "client!mj", name = "i", descriptor = "I")
	public static int walkDestPlane = 0;
	@OriginalMember(owner = "client!ha", name = "q", descriptor = "I")
	public static int walkDestTileX = 0;
	@OriginalMember(owner = "client!kd", name = "zb", descriptor = "I")
	public static int walkDestTileY = 0;
	@OriginalMember(owner = "client!ef", name = "g", descriptor = "I")
	public static int clickTileX = -1;
	@OriginalMember(owner = "client!jb", name = "p", descriptor = "I")
	public static int clickTileY = -1;
	@OriginalMember(owner = "client!id", name = "k", descriptor = "I")
	public static int clickOffsetY;
	@OriginalMember(owner = "client!u", name = "i", descriptor = "I")
	public static int targetCursorId;
	@OriginalMember(owner = "client!uf", name = "t", descriptor = "I")
	public static int pressedSlotIndex = 0;
	@OriginalMember(owner = "client!pk", name = "bb", descriptor = "Lclient!na;")
	public static JagString walkText;
	@OriginalMember(owner = "client!jl", name = "v", descriptor = "I")
	public static int minimapWalkState = 0;
	@OriginalMember(owner = "client!aa", name = "a", descriptor = "I")
	public static int pickResultCount = 0;
	@OriginalMember(owner = "client!cl", name = "Y", descriptor = "I")
	public static int defaultCursorId = -1;
	@OriginalMember(owner = "client!p", name = "e", descriptor = "I")
	public static int selectedObjSlot;
	@OriginalMember(owner = "client!jg", name = "b", descriptor = "I")
	public static int targetParamId;
	@OriginalMember(owner = "client!th", name = "n", descriptor = "Z")
	public static boolean isTargeting = false;
	@OriginalMember(owner = "client!bh", name = "t", descriptor = "I")
	public static int clickedInventoryIndex = 0;
	@OriginalMember(owner = "client!em", name = "D", descriptor = "I")
	public static int gregorianDateSeed;
	@OriginalMember(owner = "client!ml", name = "Q", descriptor = "I")
	public static int clickProcessingState = 0;

	/***********************
	 *   Action Constants  *
	 ***********************/
	/* Tiles */
	public static final int WALK_HERE = 60;
	/* NPCs */
	public static final int NPC_ACTION_1 = 17;
	public static final int NPC_ACTION_2 = 16;
	public static final int NPC_ACTION_3 = 4;
	public static final int NPC_ACTION_4 = 19;
	public static final int NPC_ACTION_5 = 2;
	public static final int NPC_EXAMINE = 1007;
	/* Players */
	public static final int PLAYER_ACTION_1 = 30;
	public static final int PLAYER_ACTION_BLOCK = 34;
	public static final int PLAYER_ACTION_TRADE = 29;
	public static final int PLAYER_REQ_ASSIST_ACTION = 37;
	public static final int PLAYER_FOLLOW_ACTION = 31;
	public static final int PLAYER_ACTION_5 = 57;
	/* Objects */
	public static final int OBJ_ACTION_1 = 47;
	public static final int OBJ_EQUIP_ACTION = 5;
	public static final int OBJ_ACTION_4 = 35;
	public static final int OBJ_OPERATE_ACTION = 23;
	public static final int OBJ_ACTION_5 = 58;
	public static final int OBJ_EXAMINE = 1002;
	public static final int OBJ_PLAYER_ACTION = 1;
	public static final int OBJ_OBJSTACK_ACTION = 33;
	public static final int OBJ_NPC_ACTION = 26;
	public static final int OBJ_LOC_ACTION = 14;
	public static final int OBJ_OBJ_ACTION = 40;
	/* Object Stacks */
	public static final int OBJSTACK_ACTION_1 = 18;
	public static final int OBJSTACK_ACTION_2 = 20;
	/* Locations */
	public static final int LOC_ACTION_1 = 42;
	public static final int LOC_ACTION_2 = 50;
	public static final int LOC_ACTION_3 = 49;
	public static final int LOC_ACTION_4 = 46;
	public static final int LOC_ACTION_5 = 1001;
	public static final int LOC_ACTION_EXAMINE = 1004;
	/* Components */
	public static final int COMPONENT_ACTION_CLOSE = 28;
	public static final int COMPONENT_OBJSTACK_ACTION = 39;
	public static final int OBJ_IN_COMPONENT_ACTION_4 = 43;
	public static final int COMPONENT_LOC_ACTION = 38;
	public static final int LOGOUT_ACTION_2 = 59;
	public static final int LOGOUT_ACTION = 51;
	public static final int OBJ_IN_COMPONENT_ACTION_1 = 25;
	public static final int COMPONENT_PLAYER_ACTION = 15;
	public static final int COMPONENT_OBJ_ACTION = 3;
	public static final int COMPONENT_NPC_ACTION = 45;
	public static final int OBJ_EXAMINE_IN_COMPONENT = 1006;
	/* Unknown/Unidentified */
	public static final int UNKNOWN_13 = 13;
	public static final int UNKNOWN_22 = 22;
	public static final int UNKNOWN_48 = 48;
	public static final int UNKNOWN_12 = 12;
	public static final int UNKNOWN_36 = 36;
	public static final int UNKNOWN_6 = 6;
	public static final int UNKNOWN_24 = 24;
	public static final int UNKNOWN_7 = 7;
	public static final int UNKNOWN_8 = 8;
	public static final int UNKNOWN_11 = 11;
	public static final int UNKNOWN_32 = 32;
	public static final int UNKNOWN_21 = 21;
	public static final int UNKNOWN_9 = 9;
	public static final int UNKNOWN_1003 = 1003;
	public static final int UNKNOWN_41 = 41;
	public static final int UNKNOWN_10 = 10;
	public static final int UNKNOWN_44 = 44;


	@OriginalMember(owner = "client!va", name = "a", descriptor = "(IZILclient!be;)V")
	public static void addComponentEntries(@OriginalArg(0) int mouseY, @OriginalArg(2) int mouseX, @OriginalArg(3) Component component) {
		if (component.buttonType == 1) {
			add(-1, 0L, JagString.EMPTY, 0, (short) 8, component.option, component.id);
		}
		@Pc(47) JagString verb;
		if (component.buttonType == 2 && !isTargeting) {
			verb = MiniMap.getTargetVerb(component);
			if (verb != null) {
				add(-1, 0L, JagString.concatenate(new JagString[]{COLOR_GREEN, component.optionSuffix}), -1, (short) 32, verb, component.id);
			}
		}
		if (component.buttonType == 3) {
			add(-1, 0L, JagString.EMPTY, 0, (short) 28, LocalizedText.CLOSE, component.id);
		}
		if (component.buttonType == 4) {
			add(-1, 0L, JagString.EMPTY, 0, (short) 59, component.option, component.id);
		}
		if (component.buttonType == 5) {
			add(-1, 0L, JagString.EMPTY, 0, (short) 51, component.option, component.id);
		}
		if (component.buttonType == 6 && Cs1ScriptRunner.pleaseWaitComponent == null) {
			add(-1, 0L, JagString.EMPTY, -1, (short) 41, component.option, component.id);
		}
		@Pc(173) int row;
		@Pc(171) int slotIndex;
		if (component.type == 2) {
			slotIndex = 0;
			for (row = 0; row < component.baseHeight; row++) {
				for (@Pc(183) int col = 0; col < component.baseWidth; col++) {
					@Pc(195) int slotX = (component.invMarginX + 32) * col;
					@Pc(202) int slotY = (component.invMarginY + 32) * row;
					if (slotIndex < 20) {
						slotY += component.invOffsetY[slotIndex];
						slotX += component.invOffsetX[slotIndex];
					}
					if (mouseX >= slotX && slotY <= mouseY && slotX + 32 > mouseX && slotY + 32 > mouseY) {
						InterfaceList.mouseOverInventoryInterface = component;
						clickedInventoryIndex = slotIndex;
						if (component.objTypes[slotIndex] > 0) {
							@Pc(267) ServerActiveProperties serverProps = InterfaceList.getServerActiveProperties(component);
							@Pc(276) ObjType objType = ObjTypeList.get(component.objTypes[slotIndex] - 1);
							if (itemTargetMode == 1 && serverProps.isObjOpsEnabled()) {
								if (MiniMap.selectedComponentId != component.id || selectedObjSlot != slotIndex) {
									add(-1, objType.id, JagString.concatenate(new JagString[]{selectedObjText, ARROW_ORANGE, objType.name}), slotIndex, (short) 40, LocalizedText.USE, component.id);
								}
							} else if (isTargeting && serverProps.isObjOpsEnabled()) {
								@Pc(596) ParamType paramType = targetParamId == -1 ? null : ParamTypeList.get(targetParamId);
								if ((targetMask & 0x10) != 0 && (paramType == null || objType.getParam(paramType.defaultInt, targetParamId) != paramType.defaultInt)) {
									add(targetCursorId, objType.id, JagString.concatenate(new JagString[]{targetOpBase, ARROW_ORANGE, objType.name}), slotIndex, (short) 3, targetVerb, component.id);
								}
							} else {
								@Pc(296) JagString[] options = objType.iops;
								if (DEBUG_OPS) {
									options = annotateOps(options);
								}
								@Pc(309) int i;
								@Pc(334) byte actionId;
								if (serverProps.isObjOpsEnabled()) {
									for (i = 4; i >= 3; i--) {
										if (options != null && options[i] != null) {
											if (i == 3) {
												actionId = 35;
											} else {
												actionId = 58;
											}
											add(-1, objType.id, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), slotIndex, actionId, options[i], component.id);
										}
									}
								}
								if (serverProps.isObjUseEnabled()) {
									add(MiniMap.useCursor, objType.id, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), slotIndex, (short) 22, LocalizedText.USE, component.id);
								}
								if (serverProps.isObjOpsEnabled() && options != null) {
									for (i = 2; i >= 0; i--) {
										if (options[i] != null) {
											actionId = 0;
											if (i == 0) {
												actionId = 47;
											}
											if (i == 1) {
												actionId = 5;
											}
											if (i == 2) {
												actionId = 43;
											}
											add(-1, objType.id, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), slotIndex, actionId, options[i], component.id);
										}
									}
								}
								options = component.invOptions;
								if (DEBUG_OPS) {
									options = annotateOps(options);
								}
								if (options != null) {
									for (i = 4; i >= 0; i--) {
										if (options[i] != null) {
											actionId = 0;
											if (i == 0) {
												actionId = 25;
											}
											if (i == 1) {
												actionId = 23;
											}
											if (i == 2) {
												actionId = 48;
											}
											if (i == 3) {
												actionId = 7;
											}
											if (i == 4) {
												actionId = 13;
											}
											add(-1, objType.id, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), slotIndex, actionId, options[i], component.id);
										}
									}
								}
								add(MiniMap.examineCursor, objType.id, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), slotIndex, (short) 1006, LocalizedText.EXAMINE, component.id);
							}
						}
					}
					slotIndex++;
				}
			}
		}
		if (!component.if3) {
			return;
		}
		if (!isTargeting) {
			for (slotIndex = 9; slotIndex >= 5; slotIndex--) {
				@Pc(765) JagString opText = InterfaceList.getOp(component, slotIndex);
				if (opText != null) {
					add(getOpCursor(slotIndex, component), slotIndex + 1, component.optionBase, component.createdComponentId, (short) 1003, opText, component.id);
				}
			}
			verb = MiniMap.getTargetVerb(component);
			if (verb != null) {
				add(-1, 0L, component.optionBase, component.createdComponentId, (short) 32, verb, component.id);
			}
			for (row = 4; row >= 0; row--) {
				@Pc(828) JagString opText = InterfaceList.getOp(component, row);
				if (opText != null) {
					add(getOpCursor(row, component), row + 1, component.optionBase, component.createdComponentId, (short) 9, opText, component.id);
				}
			}
			if (InterfaceList.getServerActiveProperties(component).isResumePauseButtonEnabled()) {
				add(-1, 0L, JagString.EMPTY, component.createdComponentId, (short) 41, LocalizedText.CONTINUE, component.id);
			}
		} else if (InterfaceList.getServerActiveProperties(component).isUseTarget() && (targetMask & 0x20) != 0) {
			add(targetCursorId, 0L, JagString.concatenate(new JagString[]{targetOpBase, ARROW_PLAIN, component.optionBase}), component.createdComponentId, (short) 12, targetVerb, component.id);
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(IJBLclient!na;ISLclient!na;I)V")
	public static void add(@OriginalArg(0) int cursor, @OriginalArg(1) long key, @OriginalArg(3) JagString opName, @OriginalArg(4) int intArg1, @OriginalArg(5) short action, @OriginalArg(6) JagString op, @OriginalArg(7) int intArg2) {
		if (Cs1ScriptRunner.isMenuOpen || size >= 500) {
			return;
		}
		ops[size] = op;
		opBases[size] = opName;
		cursors[size] = cursor == -1 ? defaultCursorId : cursor;
		actions[size] = action;
		keys[size] = key;
		intArgs1[size] = intArg1;
		intArgs2[size] = intArg2;
		PluginRepository.DrawMiniMenu(new MiniMenuEntry(size));
		size++;
	}

	@OriginalMember(owner = "client!wl", name = "b", descriptor = "(I)V")
	public static void sort() {
		@Pc(3) boolean sorted = false;
		while (!sorted) {
			sorted = true;
			for (@Pc(13) int i = 0; i < size - 1; i++) {
				if ((actions[i] < 1000 && actions[i + 1] > 1000) || (actions[i] > 7000 && actions[i + 1] > actions[i])) {
					@Pc(41) JagString swapOpBase = opBases[i];
					sorted = false;
					opBases[i] = opBases[i + 1];
					opBases[i + 1] = swapOpBase;
					@Pc(61) JagString swapOp = ops[i];
					ops[i] = ops[i + 1];
					ops[i + 1] = swapOp;
					@Pc(79) int swapIntArg1 = intArgs1[i];
					intArgs1[i] = intArgs1[i + 1];
					intArgs1[i + 1] = swapIntArg1;
					@Pc(97) int swapIntArg2 = intArgs2[i];
					intArgs2[i] = intArgs2[i + 1];
					intArgs2[i + 1] = swapIntArg2;
					@Pc(115) int swapCursor = cursors[i];
					cursors[i] = cursors[i + 1];
					cursors[i + 1] = swapCursor;
					@Pc(133) short swapAction = actions[i];
					actions[i] = actions[i + 1];
					actions[i + 1] = swapAction;
					@Pc(151) long swapKey = keys[i];
					keys[i] = keys[i + 1];
					keys[i + 1] = swapKey;
				}
			}
		}
	}

	@OriginalMember(owner = "client!qe", name = "b", descriptor = "(II)V")
	public static void remove(@OriginalArg(1) int i) {
		size--;
		if (size == i) {
			return;
		}
		ArrayUtils.copy(ops, i + 1, ops, i, size - i);
		ArrayUtils.copy(opBases, i + 1, opBases, i, size - i);
		ArrayUtils.copy(cursors, i + 1, cursors, i, size - i);
		ArrayUtils.copy(actions, i + 1, actions, i, size - i);
		ArrayUtils.copy(keys, i + 1, keys, i, size - i);
		ArrayUtils.copy(intArgs1, i + 1, intArgs1, i, size - i);
		ArrayUtils.copy(intArgs2, i + 1, intArgs2, i, size - i);
	}

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "(IZ)Lclient!na;")
	public static JagString getOp(@OriginalArg(0) int i) {
		try {
			return opBases[i].length() > 0 ? JagString.concatenate(new JagString[]{ops[i], LocalizedText.MINISEPARATOR, opBases[i]}) : ops[i];
		} catch (NullPointerException npe) {
			int ui = 4;
			return JagString.EMPTY;
		}
	}

	@OriginalMember(owner = "client!i", name = "p", descriptor = "(II)V")
	public static void doAction(@OriginalArg(1) int actionIndex) {
		if (actionIndex < 0) {
			return;
		}
		@Pc(15) int menuArg1 = intArgs1[actionIndex];
		@Pc(19) int menuArg2 = intArgs2[actionIndex];
		@Pc(23) int actionCode = actions[actionIndex];
		if (actionCode >= 2000) {
			actionCode -= 2000;
		}
		@Pc(31) long key = keys[actionIndex];
		@Pc(36) int keyInt = (int) keys[actionIndex];
		@Pc(43) Player player;
		if (actionCode == PLAYER_FOLLOW_ACTION) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.type = 2;
				Cross.milliseconds = 0;
				Cross.x = Mouse.clickX;
				Cross.y = Mouse.clickY;
				Protocol.outboundBuffer.p1isaac(71);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode == LOC_ACTION_4) {
			PathFinder.findPathToLoc(key, menuArg2, menuArg1);
			Protocol.outboundBuffer.p1isaac(247);
			Protocol.outboundBuffer.ip2(Camera.originY + menuArg2);
			Protocol.outboundBuffer.ip2add(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.p2(Integer.MAX_VALUE & (int) (key >>> 32));
		}
		if (actionCode == OBJ_OBJ_ACTION) {
			Protocol.outboundBuffer.p1isaac(27);
			Protocol.outboundBuffer.p2(selectedObjSlot);
			Protocol.outboundBuffer.ip4(menuArg2);
			Protocol.outboundBuffer.ip2(menuArg1);
			Protocol.outboundBuffer.ip4(MiniMap.selectedComponentId);
			Protocol.outboundBuffer.ip2add(selectedObjId);
			Protocol.outboundBuffer.ip2add(keyInt);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		@Pc(192) Npc npc;
		if (actionCode == NPC_ACTION_4) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.x = Mouse.clickX;
				Cross.type = 2;
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Protocol.outboundBuffer.p1isaac(30);
				Protocol.outboundBuffer.p2(keyInt);
			}
		}
		if (actionCode == NPC_ACTION_1) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.x = Mouse.clickX;
				Cross.milliseconds = 0;
				Cross.type = 2;
				Cross.y = Mouse.clickY;
				Protocol.outboundBuffer.p1isaac(78);
				Protocol.outboundBuffer.ip2(keyInt);
			}
		}
		if (actionCode == UNKNOWN_44) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.x = Mouse.clickX;
				Cross.type = 2;
				Cross.y = Mouse.clickY;
				Cross.milliseconds = 0;
				Protocol.outboundBuffer.p1isaac(133);
				Protocol.outboundBuffer.ip2(keyInt);
			}
		}
		if (actionCode == OBJ_ACTION_5) {
			Protocol.outboundBuffer.p1isaac(135);
			Protocol.outboundBuffer.p2add(keyInt);
			Protocol.outboundBuffer.p2add(menuArg1);
			Protocol.outboundBuffer.mp4(menuArg2);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == LOC_ACTION_1) {
			PathFinder.findPathToLoc(key, menuArg2, menuArg1);
			Protocol.outboundBuffer.p1isaac(254);
			Protocol.outboundBuffer.ip2(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.p2add((int) (key >>> 32) & Integer.MAX_VALUE);
			Protocol.outboundBuffer.p2(menuArg2 + Camera.originY);
		}
		if (actionCode == COMPONENT_ACTION_CLOSE) {
			ClientProt.closeWidget();
		}
		if (actionCode == COMPONENT_NPC_ACTION) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.x = Mouse.clickX;
				Cross.type = 2;
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Protocol.outboundBuffer.p1isaac(239);
				Protocol.outboundBuffer.ip4(targetInterfaceId);
				Protocol.outboundBuffer.p2add(targetChildId);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		@Pc(560) boolean pathFound;
		if (actionCode == OBJSTACK_ACTION_1) {
			if (client.game == 1) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			} else {
				pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				if (!pathFound) {
					PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				}
			}
			Cross.x = Mouse.clickX;
			Cross.milliseconds = 0;
			Cross.type = 2;
			Cross.y = Mouse.clickY;
			Protocol.outboundBuffer.p1isaac(66);
			Protocol.outboundBuffer.ip2(Camera.originX + menuArg1);
			Protocol.outboundBuffer.p2(keyInt);
			Protocol.outboundBuffer.ip2add(menuArg2 + Camera.originY);
		}
		if (actionCode == LOC_ACTION_5) {
			PathFinder.findPathToLoc(key, menuArg2, menuArg1);
			Protocol.outboundBuffer.p1isaac(170);
			Protocol.outboundBuffer.ip2add(Integer.MAX_VALUE & (int) (key >>> 32));
			Protocol.outboundBuffer.ip2add(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.ip2add(menuArg2 + Camera.originY);
		}
		if (actionCode == OBJ_EXAMINE) {
			Cross.type = 2;
			Cross.x = Mouse.clickX;
			Cross.y = Mouse.clickY;
			Cross.milliseconds = 0;
			Protocol.outboundBuffer.p1isaac(92);
			Protocol.outboundBuffer.ip2add(keyInt);
		}
		@Pc(693) Component component;
		if (actionCode == OBJ_EXAMINE_IN_COMPONENT) {
			component = InterfaceList.getComponent(menuArg2);
			if (component == null || component.objCounts[menuArg1] < 100000) {
				Protocol.outboundBuffer.p1isaac(92);
				Protocol.outboundBuffer.ip2add(keyInt);
			} else {
				Chat.add(JagString.EMPTY, 0, JagString.concatenate(new JagString[]{JagString.parseInt(component.objCounts[menuArg1]), QUANTITY_SEPARATOR, ObjTypeList.get(keyInt).name}));
			}
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == WALK_HERE) {
			if (keyInt == 0) {
				if (API.IsRoofVisibilityActive()) {
					API.ClearDestinationRoofTarget();
				}
				setWalkDestination(Player.plane, menuArg1, menuArg2);
			} else if (keyInt == 1) {
				if (LoginManager.staffModLevel > 0 && Keyboard.pressedKeys[Keyboard.KEY_CTRL] && Keyboard.pressedKeys[Keyboard.KEY_SHIFT]) {
					Cheat.teleport(Camera.originX + menuArg1, Camera.originY + menuArg2, Player.plane);
				} else if (PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, true, 0, menuArg1, 0, 0, 1, menuArg2, PlayerList.self.movementQueueX[0])) {
					if (API.IsRoofVisibilityActive()) {
						API.SetDestinationRoofTarget(menuArg1, menuArg2);
					}
					Protocol.outboundBuffer.p1(InterfaceList.clickOffsetX);
					Protocol.outboundBuffer.p1(clickOffsetY);
					Protocol.outboundBuffer.p2((int) Camera.yawTarget);
					Protocol.outboundBuffer.p1(57);
					Protocol.outboundBuffer.p1(MiniMap.compassAngleOffset);
					Protocol.outboundBuffer.p1(MiniMap.zoomOffset);
					Protocol.outboundBuffer.p1(89);
					Protocol.outboundBuffer.p2(PlayerList.self.xFine);
					Protocol.outboundBuffer.p2(PlayerList.self.yFine);
					Protocol.outboundBuffer.p1(PathFinder.approximateDestination);
					Protocol.outboundBuffer.p1(63);
				}
			}
		}
		if (actionCode == NPC_EXAMINE) {
			Cross.milliseconds = 0;
			Cross.type = 2;
			Cross.y = Mouse.clickY;
			Cross.x = Mouse.clickX;
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				@Pc(884) NpcType npcType = npc.type;
				if (npcType.multiNpcs != null) {
					npcType = npcType.getMultiNpc();
				}
				if (npcType != null) {
					Protocol.outboundBuffer.p1isaac(72);
					Protocol.outboundBuffer.p2(npcType.id);
				}
			}
		}
		if (actionCode == OBJ_ACTION_1) {
			Protocol.outboundBuffer.p1isaac(156);
			Protocol.outboundBuffer.ip2add(menuArg1);
			Protocol.outboundBuffer.p2add(keyInt);
			Protocol.outboundBuffer.ip4(menuArg2);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == COMPONENT_OBJ_ACTION) {
			Protocol.outboundBuffer.p1isaac(253);
			Protocol.outboundBuffer.ip4(targetInterfaceId);
			Protocol.outboundBuffer.ip2add(menuArg1);
			Protocol.outboundBuffer.ip4(menuArg2);
			Protocol.outboundBuffer.p2add(keyInt);
			Protocol.outboundBuffer.ip2(targetChildId);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == UNKNOWN_10) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.type = 2;
				Cross.y = Mouse.clickY;
				Cross.x = Mouse.clickX;
				Cross.milliseconds = 0;
				Protocol.outboundBuffer.p1isaac(4);
				Protocol.outboundBuffer.ip2(keyInt);
			}
		}
		if (actionCode == UNKNOWN_41 && Cs1ScriptRunner.pleaseWaitComponent == null) {
			sendComponentClickPacket(menuArg1, menuArg2);
			Cs1ScriptRunner.pleaseWaitComponent = InterfaceList.getComponent(menuArg2, menuArg1);
			InterfaceList.redraw(Cs1ScriptRunner.pleaseWaitComponent);
		}
		if (actionCode == LOC_ACTION_3) {
			PathFinder.findPathToLoc(key, menuArg2, menuArg1);
			Protocol.outboundBuffer.p1isaac(84);
			Protocol.outboundBuffer.ip2add(Integer.MAX_VALUE & (int) (key >>> 32));
			Protocol.outboundBuffer.ip2add(Camera.originY + menuArg2);
			Protocol.outboundBuffer.ip2(menuArg1 + Camera.originX);
		}
		if (actionCode == OBJ_OPERATE_ACTION) {
			Protocol.outboundBuffer.p1isaac(206);
			Protocol.outboundBuffer.p2add(keyInt);
			Protocol.outboundBuffer.ip2(menuArg1);
			Protocol.outboundBuffer.ip4(menuArg2);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == OBJ_LOC_ACTION && PathFinder.findPathToLoc(key, menuArg2, menuArg1)) {
			Protocol.outboundBuffer.p1isaac(134);
			Protocol.outboundBuffer.p2add(Camera.originX + menuArg1);
			Protocol.outboundBuffer.p2(selectedObjId);
			Protocol.outboundBuffer.ip2(menuArg2 + Camera.originY);
			Protocol.outboundBuffer.p2(selectedObjSlot);
			Protocol.outboundBuffer.mp4(MiniMap.selectedComponentId);
			Protocol.outboundBuffer.p2add((int) (key >>> 32) & Integer.MAX_VALUE);
		}
		if (actionCode == PLAYER_REQ_ASSIST_ACTION) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.milliseconds = 0;
				Cross.type = 2;
				Cross.y = Mouse.clickY;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(114);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode == UNKNOWN_9 || actionCode == UNKNOWN_1003) {
			ClientProt.sendButtonClick(opBases[actionIndex], menuArg1, keyInt, menuArg2);
		}
		if (actionCode == OBJ_EQUIP_ACTION) {
			Protocol.outboundBuffer.p1isaac(55);
			Protocol.outboundBuffer.ip2(keyInt);
			Protocol.outboundBuffer.p2add(menuArg1);
			Protocol.outboundBuffer.imp4(menuArg2);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == UNKNOWN_21) {
			if (client.game == 1) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			} else {
				pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				if (!pathFound) {
					PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				}
			}
			Cross.type = 2;
			Cross.x = Mouse.clickX;
			Cross.milliseconds = 0;
			Cross.y = Mouse.clickY;
			Protocol.outboundBuffer.p1isaac(228);
			Protocol.outboundBuffer.p2(keyInt);
			Protocol.outboundBuffer.ip2(Camera.originX + menuArg1);
			Protocol.outboundBuffer.ip2add(Camera.originY + menuArg2);
		}
		if (actionCode == NPC_ACTION_3) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.type = 2;
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(148);
				Protocol.outboundBuffer.p2add(keyInt);
			}
		}
		if (actionCode == UNKNOWN_32) {
			component = InterfaceList.getComponent(menuArg2, menuArg1);
			if (component != null) {
				cancelTargeting();
				@Pc(1493) ServerActiveProperties serverProps = InterfaceList.getServerActiveProperties(component);
				startTargeting(menuArg2, menuArg1, serverProps.getTargetMask(), serverProps.targetParam, component.targetCursor, component.defaultTargetCursor);
				itemTargetMode = 0;
				targetVerb = MiniMap.getTargetVerb(component);
				if (targetVerb == null) {
					targetVerb = NULL_TEXT;
				}
				if (component.if3) {
					targetOpBase = JagString.concatenate(new JagString[]{component.optionBase, COLOR_WHITE});
				} else {
					targetOpBase = JagString.concatenate(new JagString[]{COLOR_GREEN, component.optionSuffix, COLOR_WHITE});
				}
			}
			return;
		}
		if (actionCode == PLAYER_ACTION_TRADE) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.y = Mouse.clickY;
				Cross.milliseconds = 0;
				Cross.type = 2;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(180);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode == OBJ_ACTION_4) {
			Protocol.outboundBuffer.p1isaac(161);
			Protocol.outboundBuffer.ip4(menuArg2);
			Protocol.outboundBuffer.ip2add(keyInt);
			Protocol.outboundBuffer.ip2add(menuArg1);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == COMPONENT_PLAYER_ACTION) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.milliseconds = 0;
				Cross.type = 2;
				Cross.x = Mouse.clickX;
				Cross.y = Mouse.clickY;
				Protocol.outboundBuffer.p1isaac(195);
				Protocol.outboundBuffer.p2add(targetChildId);
				Protocol.outboundBuffer.ip4(targetInterfaceId);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode == PLAYER_ACTION_BLOCK) {
			if (client.game == 1) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			} else {
				pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				if (!pathFound) {
					PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				}
			}
			Cross.x = Mouse.clickX;
			Cross.type = 2;
			Cross.y = Mouse.clickY;
			Cross.milliseconds = 0;
			Protocol.outboundBuffer.p1isaac(109);
			Protocol.outboundBuffer.ip2(menuArg2 + Camera.originY);
			Protocol.outboundBuffer.p2(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.ip2add(keyInt);
		}
		if (actionCode == OBJ_IN_COMPONENT_ACTION_1) {
			Protocol.outboundBuffer.p1isaac(81);
			Protocol.outboundBuffer.p2add(menuArg1);
			Protocol.outboundBuffer.p2(keyInt);
			Protocol.outboundBuffer.imp4(menuArg2);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == NPC_ACTION_5) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.y = Mouse.clickY;
				Cross.type = 2;
				Cross.x = Mouse.clickX;
				Cross.milliseconds = 0;
				Protocol.outboundBuffer.p1isaac(218);
				Protocol.outboundBuffer.ip2(keyInt);
			}
		}
		@Pc(1955) int varpIndex;
		if (actionCode == LOGOUT_ACTION) {
			Protocol.outboundBuffer.p1isaac(10);
			Protocol.outboundBuffer.p4(menuArg2);
			component = InterfaceList.getComponent(menuArg2);
			if (component.cs1Scripts != null && component.cs1Scripts[0][0] == 5) {
				varpIndex = component.cs1Scripts[0][1];
				if (VarpDomain.activeVarps[varpIndex] != component.cs1ComparisonOperands[0]) {
					VarpDomain.activeVarps[varpIndex] = component.cs1ComparisonOperands[0];
					VarpDomain.refreshMagicVarp(varpIndex);
				}
			}
		}
		if (actionCode == OBJ_NPC_ACTION) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.type = 2;
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(115);
				Protocol.outboundBuffer.mp4(MiniMap.selectedComponentId);
				Protocol.outboundBuffer.ip2(selectedObjSlot);
				Protocol.outboundBuffer.ip2(keyInt);
				Protocol.outboundBuffer.ip2add(selectedObjId);
			}
		}
		if (actionCode == LOGOUT_ACTION_2) {
			Protocol.outboundBuffer.p1isaac(10);
			Protocol.outboundBuffer.p4(menuArg2);
			component = InterfaceList.getComponent(menuArg2);
			if (component.cs1Scripts != null && component.cs1Scripts[0][0] == 5) {
				varpIndex = component.cs1Scripts[0][1];
				VarpDomain.activeVarps[varpIndex] = 1 - VarpDomain.activeVarps[varpIndex];
				VarpDomain.refreshMagicVarp(varpIndex);
			}
		}
		if (actionCode == OBJ_OBJSTACK_ACTION) {
			pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			if (!pathFound) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			}
			Cross.x = Mouse.clickX;
			Cross.milliseconds = 0;
			Cross.y = Mouse.clickY;
			Cross.type = 2;
			Protocol.outboundBuffer.p1isaac(101);
			Protocol.outboundBuffer.ip2add(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.ip2(selectedObjSlot);
			Protocol.outboundBuffer.ip2(selectedObjId);
			Protocol.outboundBuffer.ip2(keyInt);
			Protocol.outboundBuffer.ip2add(Camera.originY + menuArg2);
			Protocol.outboundBuffer.mp4(MiniMap.selectedComponentId);
		}
		if (actionCode == LOC_ACTION_EXAMINE) {
			Cross.milliseconds = 0;
			Cross.x = Mouse.clickX;
			Cross.type = 2;
			Cross.y = Mouse.clickY;
			Protocol.outboundBuffer.p1isaac(94);
			Protocol.outboundBuffer.ip2add(keyInt);
		}
		if (actionCode == UNKNOWN_11) {
			if (keyInt == 0) {
				minimapWalkState = 1;
				setWalkDestination(Player.plane, menuArg1, menuArg2);
			} else if (keyInt == 1) {
				Protocol.outboundBuffer.p1isaac(131);
				Protocol.outboundBuffer.mp4(targetInterfaceId);
				Protocol.outboundBuffer.p2add(Camera.originX + menuArg1);
				Protocol.outboundBuffer.ip2add(targetChildId);
				Protocol.outboundBuffer.p2add(menuArg2 + Camera.originY);
			}
		}
		if (actionCode == UNKNOWN_8) {
			component = InterfaceList.getComponent(menuArg2);
			@Pc(2287) boolean shouldSend = true;
			if (component.clientCode > 0) {
				shouldSend = handleSpecialButtonAction(component);
			}
			if (shouldSend) {
				Protocol.outboundBuffer.p1isaac(10);
				Protocol.outboundBuffer.p4(menuArg2);
			}
		}
		if (actionCode == OBJ_PLAYER_ACTION) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Cross.type = 2;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(248);
				Protocol.outboundBuffer.ip2add(keyInt);
				Protocol.outboundBuffer.p2(selectedObjId);
				Protocol.outboundBuffer.p2(selectedObjSlot);
				Protocol.outboundBuffer.mp4(MiniMap.selectedComponentId);
			}
		}
		if (actionCode == UNKNOWN_7) {
			Protocol.outboundBuffer.p1isaac(85);
			Protocol.outboundBuffer.imp4(menuArg2);
			Protocol.outboundBuffer.p2(menuArg1);
			Protocol.outboundBuffer.p2add(keyInt);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == UNKNOWN_24) {
			if (client.game == 1) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			} else {
				pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				if (!pathFound) {
					PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				}
			}
			Cross.type = 2;
			Cross.y = Mouse.clickY;
			Cross.x = Mouse.clickX;
			Cross.milliseconds = 0;
			Protocol.outboundBuffer.p1isaac(48);
			Protocol.outboundBuffer.p2add(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.ip2add(keyInt);
			Protocol.outboundBuffer.ip2(Camera.originY + menuArg2);
		}
		if (actionCode == COMPONENT_LOC_ACTION && PathFinder.findPathToLoc(key, menuArg2, menuArg1)) {
			Protocol.outboundBuffer.p1isaac(233);
			Protocol.outboundBuffer.ip2add(menuArg2 + Camera.originY);
			Protocol.outboundBuffer.p2add(Camera.originX + menuArg1);
			Protocol.outboundBuffer.ip2add(targetChildId);
			Protocol.outboundBuffer.imp4(targetInterfaceId);
			Protocol.outboundBuffer.p2add((int) (key >>> 32) & Integer.MAX_VALUE);
		}
		if (actionCode == UNKNOWN_13) {
			Protocol.outboundBuffer.p1isaac(6);
			Protocol.outboundBuffer.p4(menuArg2);
			Protocol.outboundBuffer.p2add(menuArg1);
			Protocol.outboundBuffer.ip2(keyInt);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == PLAYER_ACTION_5) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.type = 2;
				Cross.y = Mouse.clickY;
				Cross.x = Mouse.clickX;
				Cross.milliseconds = 0;
				Protocol.outboundBuffer.p1isaac(175);
				Protocol.outboundBuffer.p2add(keyInt);
			}
		}
		if (actionCode == UNKNOWN_22) {
			cancelTargeting();
			component = InterfaceList.getComponent(menuArg2);
			MiniMap.selectedComponentId = menuArg2;
			selectedObjSlot = menuArg1;
			itemTargetMode = 1;
			selectedObjId = keyInt;
			InterfaceList.redraw(component);
			selectedObjText = JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, ObjTypeList.get(keyInt).name, COLOR_WHITE});
			if (selectedObjText == null) {
				selectedObjText = NULL;
			}
			return;
		}
		if (actionCode == LOC_ACTION_2) {
			PathFinder.findPathToLoc(key, menuArg2, menuArg1);
			Protocol.outboundBuffer.p1isaac(194);
			Protocol.outboundBuffer.ip2add(menuArg2 + Camera.originY);
			Protocol.outboundBuffer.ip2(Camera.originX + menuArg1);
			Protocol.outboundBuffer.p2((int) (key >>> 32) & Integer.MAX_VALUE);
		}
		if (actionCode == UNKNOWN_48) {
			Protocol.outboundBuffer.p1isaac(154);
			Protocol.outboundBuffer.ip2(menuArg1);
			Protocol.outboundBuffer.imp4(menuArg2);
			Protocol.outboundBuffer.ip2add(keyInt);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == PLAYER_ACTION_1) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.milliseconds = 0;
				Cross.x = Mouse.clickX;
				Cross.y = Mouse.clickY;
				Cross.type = 2;
				Protocol.outboundBuffer.p1isaac(68);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode == OBJ_IN_COMPONENT_ACTION_4) {
			Protocol.outboundBuffer.p1isaac(153);
			Protocol.outboundBuffer.ip4(menuArg2);
			Protocol.outboundBuffer.ip2(menuArg1);
			Protocol.outboundBuffer.ip2(keyInt);
			itemInteractionTick = 0;
			pressedInventoryComponent = InterfaceList.getComponent(menuArg2);
			pressedSlotIndex = menuArg1;
		}
		if (actionCode == COMPONENT_OBJSTACK_ACTION) {
			pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			if (!pathFound) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			}
			Cross.y = Mouse.clickY;
			Cross.x = Mouse.clickX;
			Cross.type = 2;
			Cross.milliseconds = 0;
			Protocol.outboundBuffer.p1isaac(73);
			Protocol.outboundBuffer.imp4(targetInterfaceId);
			Protocol.outboundBuffer.p2(Camera.originY + menuArg2);
			Protocol.outboundBuffer.ip2add(keyInt);
			Protocol.outboundBuffer.ip2add(menuArg1 + Camera.originX);
			Protocol.outboundBuffer.ip2(targetChildId);
		}
		if (actionCode == UNKNOWN_12) {
			Protocol.outboundBuffer.p1isaac(82);
			Protocol.outboundBuffer.p2(targetChildId);
			Protocol.outboundBuffer.imp4(menuArg2);
			Protocol.outboundBuffer.p4(targetInterfaceId);
			Protocol.outboundBuffer.ip2add(menuArg1);
		}
		if (actionCode == UNKNOWN_36) {
			if (keyInt == 0) {
				Protocol.viewportWalkState = 1;
				setWalkDestination(Player.plane, menuArg1, menuArg2);
			} else if (LoginManager.staffModLevel > 0 && Keyboard.pressedKeys[Keyboard.KEY_CTRL] && Keyboard.pressedKeys[Keyboard.KEY_SHIFT]) {
				Cheat.teleport(menuArg1 + Camera.originX, Camera.originY - -menuArg2, Player.plane);
			} else {
				Protocol.outboundBuffer.p1isaac(179);
				Protocol.outboundBuffer.p2(menuArg2 + Camera.originY);
				Protocol.outboundBuffer.p2(menuArg1 + Camera.originX);
			}
		}
		if (actionCode == UNKNOWN_6) {
			player = PlayerList.players[keyInt];
			if (player != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, player.movementQueueX[0], 1, 0, 2, player.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.y = Mouse.clickY;
				Cross.milliseconds = 0;
				Cross.type = 2;
				Cross.x = Mouse.clickX;
				Protocol.outboundBuffer.p1isaac(106);
				Protocol.outboundBuffer.p2(keyInt);
			}
		}
		if (actionCode == OBJSTACK_ACTION_2) {
			if (client.game == 1) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
			} else {
				pathFound = PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 0, false, 0, menuArg1, 0, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				if (!pathFound) {
					PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, menuArg1, 1, 0, 2, menuArg2, PlayerList.self.movementQueueX[0]);
				}
			}
			Cross.y = Mouse.clickY;
			Cross.milliseconds = 0;
			Cross.x = Mouse.clickX;
			Cross.type = 2;
			Protocol.outboundBuffer.p1isaac(33);
			Protocol.outboundBuffer.p2(keyInt);
			Protocol.outboundBuffer.p2(Camera.originX + menuArg1);
			Protocol.outboundBuffer.ip2(Camera.originY + menuArg2);
		}
		if (actionCode == NPC_ACTION_2) {
			npc = NpcList.npcs[keyInt];
			if (npc != null) {
				PathFinder.findPath(PlayerList.self.movementQueueY[0], 0, 1, false, 0, npc.movementQueueX[0], 1, 0, 2, npc.movementQueueY[0], PlayerList.self.movementQueueX[0]);
				Cross.x = Mouse.clickX;
				Cross.milliseconds = 0;
				Cross.y = Mouse.clickY;
				Cross.type = 2;
				Protocol.outboundBuffer.p1isaac(3);
				Protocol.outboundBuffer.ip2add(keyInt);
			}
		}
		if (actionCode >= 7990 && actionCode <= 7999) {
			int index = actionCode - 7990;
			API.miniMenuCustomActions[index].run();
		}
		if (itemTargetMode != 0) {
			itemTargetMode = 0;
			InterfaceList.redraw(InterfaceList.getComponent(MiniMap.selectedComponentId));
		}
		if (isTargeting) {
			cancelTargeting();
		}
		if (pressedInventoryComponent != null && itemInteractionTick == 0) {
			InterfaceList.redraw(pressedInventoryComponent);
		}
	}

	@OriginalMember(owner = "client!jj", name = "a", descriptor = "(IBI)Lclient!na;")
	public static JagString getCombatLevelColor(@OriginalArg(0) int otherLevel, @OriginalArg(2) int selfLevel) {
		@Pc(4) int delta = selfLevel - otherLevel;
		if (delta < -9) {
			return COLOR_RED;
		} else if (delta < -6) {
			return COLOR_LIGHT_RED;
		} else if (delta < -3) {
			return COLOR_ORANGE;
		} else if (delta < 0) {
			return COLOR_LIGHT_ORANGE;
		} else if (delta > 9) {
			return COLOR_GREEN;
		} else if (delta > 6) {
			return COLOR_LIGHT_GREEN;
		} else if (delta <= 3) {
			return delta > 0 ? COLOR_YELLOW_GREEN : COLOR_YELLOW;
		} else {
			return COLOR_BRIGHT_GREEN;
		}
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(IIIIIIB)V")
	public static void addEntries(@OriginalArg(0) int screenY, @OriginalArg(1) int width, @OriginalArg(2) int height, @OriginalArg(3) int screenX, @OriginalArg(4) int mouseY, @OriginalArg(5) int mouseX) {
		@Pc(15) int i;
		@Pc(47) int x;
		if (itemTargetMode == 0) {
			@Pc(13) int upperY = Rasteriser.screenUpperY;
			i = Rasteriser.screenLowerY;
			@Pc(17) int upperX = Rasteriser.screenUpperX;
			@Pc(19) int lowerX = Rasteriser.screenLowerX;
			@Pc(33) int worldX = (mouseX - screenX) * (upperX - lowerX) / width + lowerX;
			x = i + (upperY - i) * (mouseY - screenY) / height;
			if (isTargeting && (targetMask & 0x40) != 0) {
				@Pc(61) Component targetComponent = InterfaceList.getComponent(targetInterfaceId, targetChildId);
				if (targetComponent == null) {
					cancelTargeting();
				} else {
					add(targetCursorId, 0L, ARROW_SUFFIX, worldX, (short) 11, targetVerb, x);
				}
			} else {
				if (client.game == 1) {
					add(-1, 0L, JagString.EMPTY, worldX, (short) 36, LocalizedText.FACEHERE, x);
				}
				add(-1, 0L, JagString.EMPTY, worldX, (short) 60, walkText, x);
			}
		}
		@Pc(112) long lastKey = -1L;
		for (i = 0; i < pickResultCount; i++) {
			@Pc(121) long pickKey = Model.pickResults[i];
			x = (int) pickKey & 0x7F;
			@Pc(133) int entityType = (int) pickKey >> 29 & 0x3;
			@Pc(140) int entityId = (int) (pickKey >>> 32) & Integer.MAX_VALUE;
			@Pc(147) int y = (int) pickKey >> 7 & 0x7F;
			if (pickKey != lastKey) {
				lastKey = pickKey;
				@Pc(240) int j;
				if (entityType == 2 && SceneGraph.isLocValid(Player.plane, x, y, pickKey)) {
					@Pc(172) LocType locType = LocTypeList.get(entityId);
					if (locType.multiLocs != null) {
						locType = locType.getMultiLoc();
					}
					if (locType == null) {
						continue;
					}
					if (itemTargetMode == 1) {
						add(MiniMap.useCursor, pickKey, JagString.concatenate(new JagString[]{selectedObjText, ARROW_CYAN, locType.name}), x, (short) 14, LocalizedText.USE, y);
					} else if (isTargeting) {
						@Pc(363) ParamType paramType = targetParamId == -1 ? null : ParamTypeList.get(targetParamId);
						if ((targetMask & 0x4) != 0 && (paramType == null || locType.getParam(paramType.defaultInt, targetParamId) != paramType.defaultInt)) {
							add(targetCursorId, pickKey, JagString.concatenate(new JagString[]{targetOpBase, ARROW_CYAN, locType.name}), x, (short) 38, targetVerb, y);
						}
					} else {
						@Pc(228) JagString[] locOps = locType.ops;
						if (DEBUG_OPS) {
							locOps = annotateOps(locOps);
						}
						if (locOps != null) {
							for (j = 4; j >= 0; j--) {
								if (locOps[j] != null) {
									@Pc(254) short actionId = 0;
									if (j == 0) {
										actionId = 42;
									}
									if (j == 1) {
										actionId = 50;
									}
									@Pc(268) int cursorId = -1;
									if (j == 2) {
										actionId = 49;
									}
									if (locType.cursor1Op == j) {
										cursorId = locType.cursor1;
									}
									if (j == 3) {
										actionId = 46;
									}
									if (j == locType.cursor2Op) {
										cursorId = locType.cursor2;
									}
									if (j == 4) {
										actionId = 1001;
									}
									add(cursorId, pickKey, JagString.concatenate(new JagString[]{COLOR_CYAN, locType.name}), x, actionId, locOps[j], y);
								}
							}
						}
						add(MiniMap.examineCursor, locType.id, JagString.concatenate(new JagString[]{COLOR_CYAN, locType.name}), x, (short) 1004, LocalizedText.EXAMINE, y);
					}
				}
				@Pc(514) int otherFineX;
				@Pc(526) int otherFineY;
				@Pc(479) int minFineX;
				@Pc(493) int k;
				@Pc(502) Npc otherNpc;
				@Pc(597) Player otherPlayer;
				if (entityType == 1) {
					@Pc(421) Npc npc = NpcList.npcs[entityId];
					if ((npc.type.size & 0x1) == 0 && (npc.xFine & 0x7F) == 0 && (npc.yFine & 0x7F) == 0 || (npc.type.size & 0x1) == 1 && (npc.xFine & 0x7F) == 64 && (npc.yFine & 0x7F) == 64) {
						minFineX = npc.xFine + 64 - npc.type.size * 64;
						j = npc.yFine - (npc.type.size - 1) * 64;
						for (k = 0; k < NpcList.size; k++) {
							otherNpc = NpcList.npcs[NpcList.ids[k]];
							otherFineX = otherNpc.xFine + 64 - otherNpc.type.size * 64;
							otherFineY = otherNpc.yFine + 64 - otherNpc.type.size * 64;
							if (otherNpc != null && npc != otherNpc && otherFineX >= minFineX && npc.type.size - (otherFineX - minFineX >> 7) >= otherNpc.type.size && j <= otherFineY && otherNpc.type.size <= npc.type.size - (otherFineY - j >> 7)) {
								addNpcEntries(otherNpc.type, x, NpcList.ids[k], y);
							}
						}
						for (k = 0; k < PlayerList.size; k++) {
							otherPlayer = PlayerList.players[PlayerList.ids[k]];
							otherFineX = otherPlayer.xFine + 64 - otherPlayer.getSize() * 64;
							otherFineY = otherPlayer.yFine + 64 - otherPlayer.getSize() * 64;
							if (otherPlayer != null && otherFineX >= minFineX && otherPlayer.getSize() <= npc.type.size - (otherFineX - minFineX >> 7) && otherFineY >= j && otherPlayer.getSize() <= npc.type.size - (otherFineY - j >> 7)) {
								addPlayerEntries(PlayerList.ids[k], y, otherPlayer, x);
							}
						}
					}
					addNpcEntries(npc.type, x, entityId, y);
				}
				if (entityType == 0) {
					@Pc(688) Player player = PlayerList.players[entityId];
					if ((player.xFine & 0x7F) == 64 && (player.yFine & 0x7F) == 64) {
						minFineX = player.xFine - (player.getSize() - 1) * 64;
						j = player.yFine + 64 - player.getSize() * 64;
						for (k = 0; k < NpcList.size; k++) {
							otherNpc = NpcList.npcs[NpcList.ids[k]];
							otherFineX = otherNpc.xFine + 64 - otherNpc.type.size * 64;
							otherFineY = otherNpc.yFine + 64 - otherNpc.type.size * 64;
							if (otherNpc != null && otherFineX >= minFineX && otherNpc.type.size <= player.getSize() - (otherFineX - minFineX >> 7) && otherFineY >= j && otherNpc.type.size <= player.getSize() - (otherFineY - j >> 7)) {
								addNpcEntries(otherNpc.type, x, NpcList.ids[k], y);
							}
						}
						for (k = 0; k < PlayerList.size; k++) {
							otherPlayer = PlayerList.players[PlayerList.ids[k]];
							otherFineX = otherPlayer.xFine - (otherPlayer.getSize() - 1) * 64;
							otherFineY = otherPlayer.yFine + 64 - otherPlayer.getSize() * 64;
							if (otherPlayer != null && otherPlayer != player && minFineX <= otherFineX && otherPlayer.getSize() <= player.getSize() - (otherFineX - minFineX >> 7) && otherFineY >= j && otherPlayer.getSize() <= player.getSize() - (otherFineY - j >> 7)) {
								addPlayerEntries(PlayerList.ids[k], y, otherPlayer, x);
							}
						}
					}
					addPlayerEntries(entityId, y, player, x);
				}
				if (entityType == 3) {
					@Pc(931) LinkedList objStacks = SceneGraph.objStacks[Player.plane][x][y];
					if (objStacks != null) {
						for (@Pc(940) ObjStackNode node = (ObjStackNode) objStacks.tail(); node != null; node = (ObjStackNode) objStacks.prev()) {
							j = node.value.type;
							@Pc(951) ObjType objType = ObjTypeList.get(j);
							if (itemTargetMode == 1) {
								add(MiniMap.useCursor, j, JagString.concatenate(new JagString[]{selectedObjText, ARROW_ORANGE, objType.name}), x, (short) 33, LocalizedText.USE, y);
							} else if (isTargeting) {
								@Pc(1142) ParamType paramType = targetParamId == -1 ? null : ParamTypeList.get(targetParamId);
								if ((targetMask & 0x1) != 0 && (paramType == null || objType.getParam(paramType.defaultInt, targetParamId) != paramType.defaultInt)) {
									add(targetCursorId, j, JagString.concatenate(new JagString[]{targetOpBase, ARROW_ORANGE, objType.name}), x, (short) 39, targetVerb, y);
								}
							} else {
								@Pc(997) JagString[] objOps = objType.ops;
								if (DEBUG_OPS) {
									objOps = annotateOps(objOps);
								}
								for (otherFineX = 4; otherFineX >= 0; otherFineX--) {
									if (objOps != null && objOps[otherFineX] != null) {
										@Pc(1025) byte actionCode = 0;
										if (otherFineX == 0) {
											actionCode = 21;
										}
										if (otherFineX == 1) {
											actionCode = 34;
										}
										@Pc(1041) int cursorId = -1;
										if (otherFineX == objType.cursor1Op) {
											cursorId = objType.cursor1;
										}
										if (otherFineX == 2) {
											actionCode = 18;
										}
										if (objType.cursor2Op == otherFineX) {
											cursorId = objType.cursor2;
										}
										if (otherFineX == 3) {
											actionCode = 20;
										}
										if (otherFineX == 4) {
											actionCode = 24;
										}
										add(cursorId, j, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), x, actionCode, objOps[otherFineX], y);
									}
								}
								add(MiniMap.examineCursor, j, JagString.concatenate(new JagString[]{COLOR_ITEM_ORANGE, objType.name}), x, (short) 1002, LocalizedText.EXAMINE, y);
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!bc", name = "f", descriptor = "(B)Lclient!na;")
	public static JagString getTooltipText() {
		@Pc(32) JagString tooltipText;
		if (itemTargetMode == 1 && size < 2) {
			tooltipText = JagString.concatenate(new JagString[]{LocalizedText.USE, LocalizedText.MINISEPARATOR, selectedObjText, ARROW_SUFFIX});
		} else if (isTargeting && size < 2) {
			tooltipText = JagString.concatenate(new JagString[]{targetVerb, LocalizedText.MINISEPARATOR, targetOpBase, ARROW_SUFFIX});
		} else if (Cheat.shiftClick && Keyboard.pressedKeys[Keyboard.KEY_SHIFT] && size > 2) {
			tooltipText = getOp(size - 2);
		} else {
			tooltipText = getOp(size - 1);
		}
		if (size > 2) {
			tooltipText = JagString.concatenate(new JagString[]{tooltipText, MORE_OPTIONS_PREFIX, JagString.parseInt(size - 2), LocalizedText.MOREOPTIONS});
		}
		return tooltipText;
	}

	@OriginalMember(owner = "client!wk", name = "a", descriptor = "(I[Lclient!na;)[Lclient!na;")
	public static JagString[] annotateOps(@OriginalArg(1) JagString[] ops) {
		@Pc(8) JagString[] annotatedOps = new JagString[5];
		for (@Pc(15) int i = 0; i < 5; i++) {
			annotatedOps[i] = JagString.concatenate(new JagString[]{JagString.parseInt(i), COLON_SEPARATOR});
			if (ops != null && ops[i] != null) {
				annotatedOps[i] = JagString.concatenate(new JagString[]{annotatedOps[i], ops[i]});
			}
		}
		return annotatedOps;
	}

	@OriginalMember(owner = "client!u", name = "a", descriptor = "(Lclient!me;IIII)V")
	public static void addNpcEntries(@OriginalArg(0) NpcType npcType, @OriginalArg(1) int npcIndex, @OriginalArg(3) int key, @OriginalArg(4) int intArg2) {
		if (size >= 400) {
			return;
		}
		if (npcType.multiNpcs != null) {
			npcType = npcType.getMultiNpc();
		}
		if (npcType == null || !npcType.interactive) {
			return;
		}
		@Pc(35) JagString npcName = npcType.name;
		if (npcType.combatLevel != 0) {
			@Pc(47) JagString levelLabel = client.game == 1 ? LocalizedText.RATING : LocalizedText.LEVEL;
			npcName = JagString.concatenate(new JagString[]{npcName, getCombatLevelColor(npcType.combatLevel, PlayerList.self.combatLevel), OPEN_PARENTHESIS, levelLabel, JagString.parseInt(npcType.combatLevel), CLOSE_PARENTHESIS});
		}
		if (itemTargetMode == 1) {
			add(MiniMap.useCursor, key, JagString.concatenate(new JagString[]{selectedObjText, ARROW_YELLOW, npcName}), npcIndex, (short) 26, LocalizedText.USE, intArg2);
		} else if (isTargeting) {
			@Pc(378) ParamType paramType = targetParamId == -1 ? null : ParamTypeList.get(targetParamId);
			if ((targetMask & 0x2) != 0 && (paramType == null || npcType.getParam(targetParamId, paramType.defaultInt) != paramType.defaultInt)) {
				add(targetCursorId, key, JagString.concatenate(new JagString[]{targetOpBase, ARROW_YELLOW, npcName}), npcIndex, (short) 45, targetVerb, intArg2);
			}
		} else {
			@Pc(129) JagString[] npcOps = npcType.ops;
			if (DEBUG_OPS) {
				npcOps = annotateOps(npcOps);
			}
			@Pc(140) int opIndex;
			if (npcOps != null) {
				for (opIndex = 4; opIndex >= 0; opIndex--) {
					if (npcOps[opIndex] != null && (client.game != 0 || !npcOps[opIndex].equalsIgnoreCase(LocalizedText.ATTACK))) {
						@Pc(161) byte actionCode = 0;
						if (opIndex == 0) {
							actionCode = 17;
						}
						if (opIndex == 1) {
							actionCode = 16;
						}
						@Pc(176) int cursorId = -1;
						if (opIndex == 2) {
							actionCode = 4;
						}
						if (opIndex == 3) {
							actionCode = 19;
						}
						if (npcType.cursor1Op == opIndex) {
							cursorId = npcType.cursor1;
						}
						if (opIndex == npcType.cursor2Op) {
							cursorId = npcType.cursor2;
						}
						if (opIndex == 4) {
							actionCode = 2;
						}
						add(cursorId, key, JagString.concatenate(new JagString[]{ COLOR_YELLOW, npcName}), npcIndex, actionCode, npcOps[opIndex], intArg2);
					}
				}
			}
			if (client.game == 0 && npcOps != null) {
				for (opIndex = 4; opIndex >= 0; opIndex--) {
					if (npcOps[opIndex] != null && npcOps[opIndex].equalsIgnoreCase(LocalizedText.ATTACK)) {
						@Pc(271) short priority = 0;
						if (npcType.combatLevel > PlayerList.self.combatLevel) {
							priority = 2000; //THIS iS FOR LEFT CLICK ATTACK
						}
						@Pc(281) short attackActionCode = 0;
						if (opIndex == 0) {
							attackActionCode = 17;
						}
						if (opIndex == 1) {
							attackActionCode = 16;
						}
						if (opIndex == 2) {
							attackActionCode = 4;
						}
						if (opIndex == 3) {
							attackActionCode = 19;
						}
						if (opIndex == 4) {
							attackActionCode = 2;
						}
						if (attackActionCode != 0) {
							attackActionCode += priority;
						}
						add(npcType.attackCursor, key, JagString.concatenate(new JagString[]{ COLOR_YELLOW, npcName}), npcIndex, attackActionCode, npcOps[opIndex], intArg2);
					}
				}
			}
			add(MiniMap.examineCursor, key, JagString.concatenate(new JagString[]{ COLOR_YELLOW, npcName}), npcIndex, (short) 1007, LocalizedText.EXAMINE, intArg2);
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(IIILclient!e;I)V")
	public static void addPlayerEntries(@OriginalArg(0) int playerIndex, @OriginalArg(2) int key, @OriginalArg(3) Player other, @OriginalArg(4) int intArg2) {
		if (PlayerList.self == other || size >= 400) {
			return;
		}
		@Pc(158) JagString string;
		if (other.skill == 0) {
			@Pc(22) boolean withinCombatRange = true;
			if (PlayerList.self.combatRange != -1 && other.combatRange != -1) {
				//combat range calculation for PvP worlds
				@Pc(43) int highestCombatLevel = Math.max(other.combatLevel, PlayerList.self.combatLevel);
				@Pc(58) int highestCombatRange = Math.min(other.combatRange, PlayerList.self.combatRange);
				@Pc(69) int calc = (highestCombatLevel * 10) / 100 + highestCombatRange + 5;
				@Pc(76) int combatDelta = PlayerList.self.combatLevel - other.combatLevel;
				if (combatDelta < 0) {
					combatDelta = -combatDelta;
				}
				if (calc < combatDelta) {
					withinCombatRange = false;
				}
			}
			@Pc(95) JagString levelLabel = client.game == 1 ? LocalizedText.RATING : LocalizedText.LEVEL;
			if (other.combatLevelWithSummoning > other.combatLevel) {
				string = JagString.concatenate(new JagString[]{other.getName(), withinCombatRange ? getCombatLevelColor(other.combatLevel, PlayerList.self.combatLevel) : COLOR_WHITE, OPEN_PARENTHESIS, levelLabel, JagString.parseInt(other.combatLevel), PLUS, JagString.parseInt(other.combatLevelWithSummoning - other.combatLevel), CLOSE_PARENTHESIS});
			} else {
				string = JagString.concatenate(new JagString[]{other.getName(), withinCombatRange ? getCombatLevelColor(other.combatLevel, PlayerList.self.combatLevel) : COLOR_WHITE, OPEN_PARENTHESIS, levelLabel, JagString.parseInt(other.combatLevel), CLOSE_PARENTHESIS});
			}
		} else {
			string = JagString.concatenate(new JagString[]{other.getName(), OPEN_PARENTHESIS, LocalizedText.SKILL, JagString.parseInt(other.skill), CLOSE_PARENTHESIS});
		}
		@Pc(275) int i;
		if (itemTargetMode == 1) {
			add(MiniMap.useCursor, playerIndex, JagString.concatenate(new JagString[]{selectedObjText, ARROW_WHITE, string}), intArg2, (short) 1, LocalizedText.USE, key);
		} else if (!isTargeting) {
			for (i = 7; i >= 0; i--) {
				if (Player.options[i] != null) {
					@Pc(291) short priority = 0;
					if (client.game == 0 && Player.options[i].equalsIgnoreCase(LocalizedText.ATTACK)) {
						if (other.combatLevel > PlayerList.self.combatLevel) {
							priority = 2000;
						}
						if (PlayerList.self.team != 0 && other.team != 0) {
							if (PlayerList.self.team == other.team) {
								priority = 2000;
							} else {
								priority = 0;
							}
						}
					} else if (Player.secondaryOptions[i]) {
						priority = 2000;
					}
					@Pc(353) short baseActionCode = PLAYER_OPTION_ACTION_CODES[i];
					@Pc(358) short actionCode = (short) (baseActionCode + priority);
					add(Player.cursors[i], playerIndex, JagString.concatenate(new JagString[]{COLOR_WHITE, string}), intArg2, actionCode, Player.options[i], key);
				}
			}
		} else if ((targetMask & 0x8) != 0) {
			add(targetCursorId, playerIndex, JagString.concatenate(new JagString[]{targetOpBase, ARROW_WHITE, string}), intArg2, (short) 15, targetVerb, key);
		}
		for (i = 0; i < size; i++) {
			if (actions[i] == 60) {
				opBases[i] = JagString.concatenate(new JagString[]{COLOR_WHITE, string});
				break;
			}
		}
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(BILclient!be;)I")
	public static int getOpCursor(@OriginalArg(1) int opIndex, @OriginalArg(2) Component component) {
		if (!InterfaceList.getServerActiveProperties(component).isButtonEnabled(opIndex) && component.onOptionClick == null) {
			return -1;
		} else if (component.dragTargets == null || opIndex >= component.dragTargets.length) {
			return -1;
		} else {
			return component.dragTargets[opIndex];
		}
	}

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(III)V")
	public static void setWalkDestination(@OriginalArg(0) int plane, @OriginalArg(1) int tileX, @OriginalArg(2) int tileY) {
		walkPending = true;
		walkDestPlane = plane;
		walkDestTileX = tileX;
		walkDestTileY = tileY;
		clickTileX = -1;
		clickTileY = -1;
	}

	@OriginalMember(owner = "client!wi", name = "c", descriptor = "(II)Z")
	public static boolean isComponentOptionAction(@OriginalArg(0) int index) {
		if (index < 0) {
			return false;
		}
		@Pc(12) int action = actions[index];
		if (action >= 2000) {
			action -= 2000;
		}
		return action == 1003;
	}

	@OriginalMember(owner = "client!ud", name = "a", descriptor = "(ILclient!be;)Z")
	public static boolean handleSpecialButtonAction(@OriginalArg(1) Component component) {
		if (component.clientCode == 205) {
			Protocol.logoutOnDisconnectTimer = 250;
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!ec", name = "a", descriptor = "(B)V")
	public static void cancelTargeting() {
		if (!isTargeting) {
			return;
		}
		@Pc(19) Component component = InterfaceList.getComponent(targetInterfaceId, targetChildId);
		if (component != null && component.onUseWith != null) {
			@Pc(29) HookRequest hookRequest = new HookRequest();
			hookRequest.arguments = component.onUseWith;
			hookRequest.source = component;
			ScriptRunner.run(hookRequest);
		}
		isTargeting = false;
		defaultCursorId = -1;
		InterfaceList.redraw(component);
	}

	@OriginalMember(owner = "client!ub", name = "b", descriptor = "(IIIIIII)V")
	public static void startTargeting(@OriginalArg(0) int interfaceId, @OriginalArg(1) int childId, @OriginalArg(2) int mask, @OriginalArg(3) int paramId, @OriginalArg(4) int cursorId, @OriginalArg(6) int defaultCursor) {
		@Pc(8) Component component = InterfaceList.getComponent(interfaceId, childId);
		if (component != null && component.onUse != null) {
			@Pc(19) HookRequest hookRequest = new HookRequest();
			hookRequest.source = component;
			hookRequest.arguments = component.onUse;
			ScriptRunner.run(hookRequest);
		}
		targetChildId = childId;
		targetParamId = paramId;
		targetInterfaceId = interfaceId;
		targetMask = mask;
		isTargeting = true;
		targetCursorId = cursorId;
		defaultCursorId = defaultCursor;
		InterfaceList.redraw(component);
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(Lclient!be;III)V")
	public static void renderTooltip(@OriginalArg(0) Component component, @OriginalArg(2) int y, @OriginalArg(3) int x) {
		if (size < 2 && itemTargetMode == 0 && !isTargeting) {
			return;
		}
		@Pc(24) JagString tooltipText = getTooltipText();
		if (component == null) {
			@Pc(40) int wavyWidth = Fonts.b12Full.renderWavyText(tooltipText, x + 4, y - -15, client.aRandom1, gregorianDateSeed);
			InterfaceList.redrawScreen(x + 4, Fonts.b12Full.getStringWidth(tooltipText) + wavyWidth, y, 15);
			return;
		}
		@Pc(59) Font font = component.getFont(Sprites.nameIcons);
		if (font == null) {
			font = Fonts.b12Full;
		}
		font.renderWavyTextAligned(tooltipText, x, y, component.width, component.height, component.color, component.shadowColor, component.halign, component.valign, client.aRandom1, gregorianDateSeed, tooltipBounds);
		InterfaceList.redrawScreen(tooltipBounds[0], tooltipBounds[2], tooltipBounds[1], tooltipBounds[3]);
	}

	@OriginalMember(owner = "client!ej", name = "h", descriptor = "(I)V")
	public static void processClick() {
		if (clickProcessingState == 2) {
			if (ScriptRunner.interfaceMouseX == Mouse.lastHandledClickX && ScriptRunner.interfaceMouseY == Mouse.lastHandledClickY) {
				clickProcessingState = 0;
				if (Cheat.shiftClick && Keyboard.pressedKeys[Keyboard.KEY_SHIFT] && size > 2) {
					doAction(size - 2);
				} else {
					doAction(size - 1);
				}
			}
		} else if (ScriptRunner.interfaceMouseX == Mouse.clickX && ScriptRunner.interfaceMouseY == Mouse.clickY) {
			clickProcessingState = 0;
			if (Cheat.shiftClick && Keyboard.pressedKeys[Keyboard.KEY_SHIFT] && size > 2) {
				doAction(size - 2);
			} else {
				doAction(size - 1);
			}
		} else {
			Mouse.lastHandledClickY = Mouse.clickY;
			clickProcessingState = 2;
			Mouse.lastHandledClickX = Mouse.clickX;
		}
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(IZI)V")
	public static void sendComponentClickPacket(@OriginalArg(0) int slot, @OriginalArg(2) int componentId) {
		Protocol.outboundBuffer.p1isaac(132);
		Protocol.outboundBuffer.imp4(componentId);
		Protocol.outboundBuffer.ip2(slot);
	}

	@OriginalMember(owner = "client!lf", name = "b", descriptor = "(I)V")
	public static void drawA() {
		@Pc(3) int menuY = InterfaceList.menuY;
		@Pc(9) int menuWidth = InterfaceList.menuWidth;
		@Pc(11) int menuX = InterfaceList.menuX;
		@Pc(15) int menuHeight = InterfaceList.menuHeight;
		if (GlRenderer.enabled) {
			GlRaster.fillRect(menuX, menuY, menuWidth, menuHeight, 6116423);
			GlRaster.fillRect(menuX + 1, menuY + 1, menuWidth - 2, 16, 0);
			GlRaster.drawRect(menuX + 1, menuY + 18, menuWidth - 2, menuHeight + -19, 0);
		} else {
			SoftwareRaster.fillRect(menuX, menuY, menuWidth, menuHeight, 6116423);
			SoftwareRaster.fillRect(menuX + 1, menuY + 1, menuWidth - 2, 16, 0);
			SoftwareRaster.drawRect(menuX + 1, menuY + 18, menuWidth - 2, menuHeight + -19, 0);
		}
		Fonts.b12Full.renderLeft(LocalizedText.CHOOSE_OPTION, menuX + 3, menuY + 14, 6116423, -1);
		@Pc(96) int mouseY = Mouse.lastMouseY;
		@Pc(98) int mouseX = Mouse.lastMouseX;
		for (@Pc(107) int i = 0; i < size; i++) {
			@Pc(127) int entryY = (size - i - 1) * 15 + menuY + 31;
			@Pc(129) int color = 16777215; //WHITE
			if (menuX < mouseX && mouseX < menuX + menuWidth && entryY - 13 < mouseY && mouseY < entryY + 3) {
				color = 16776960; //YELLOW
			}
			Fonts.b12Full.renderLeft(getOp(i), menuX + 3, entryY, color, 0);
		}
		InterfaceList.forceRedrawScreen(InterfaceList.menuX, InterfaceList.menuY, InterfaceList.menuHeight, InterfaceList.menuWidth);
	}

	@OriginalMember(owner = "client!ij", name = "a", descriptor = "(B)V")
	public static void drawB() {
		@Pc(3) int menuX = InterfaceList.menuX;
		@Pc(9) int menuY = InterfaceList.menuY;
		@Pc(11) int menuHeight = InterfaceList.menuHeight;
		@Pc(13) int menuWidth = InterfaceList.menuWidth;
		if (LoginManager.menuHeaderFillSprite == null || LoginManager.menuHeaderEdgeSprite == null) {
			if (client.js5Archive8.isFileReady(LoginManager.menuHeaderFillSpriteId) && client.js5Archive8.isFileReady(LoginManager.menuHeaderEdgeSpriteId)) {
				LoginManager.menuHeaderFillSprite = SoftwareSprite.loadSoftwareAlphaSprite(client.js5Archive8, LoginManager.menuHeaderFillSpriteId);
				LoginManager.menuHeaderEdgeSprite = SoftwareSprite.loadSoftwareAlphaSprite(client.js5Archive8, LoginManager.menuHeaderEdgeSpriteId);
				if (GlRenderer.enabled) {
					if (LoginManager.menuHeaderFillSprite instanceof SoftwareAlphaSprite) {
						LoginManager.menuHeaderFillSprite = new GlAlphaSprite((SoftwareSprite) LoginManager.menuHeaderFillSprite);
					} else {
						LoginManager.menuHeaderFillSprite = new GlSprite((SoftwareSprite) LoginManager.menuHeaderFillSprite);
					}
					if (LoginManager.menuHeaderEdgeSprite instanceof SoftwareAlphaSprite) {
						LoginManager.menuHeaderEdgeSprite = new GlAlphaSprite((SoftwareSprite) LoginManager.menuHeaderEdgeSprite);
					} else {
						LoginManager.menuHeaderEdgeSprite = new GlSprite((SoftwareSprite) LoginManager.menuHeaderEdgeSprite);
					}
				}
			} else if (GlRenderer.enabled) {
				GlRaster.fillRectAlpha(menuX, menuY, menuWidth, 20, LoginManager.menuFillColor, 256 - LoginManager.menuFillTransparency);
			} else {
				SoftwareRaster.fillRectAlpha(menuX, menuY, menuWidth, 20, LoginManager.menuFillColor, 256 - LoginManager.menuFillTransparency);
			}
		}
		@Pc(112) int mouseX;
		@Pc(114) int mouseY;
		if (LoginManager.menuHeaderFillSprite != null && LoginManager.menuHeaderEdgeSprite != null) {
			mouseX = menuWidth / LoginManager.menuHeaderFillSprite.width;
			for (mouseY = 0; mouseY < mouseX; mouseY++) {
				LoginManager.menuHeaderFillSprite.render(mouseY * LoginManager.menuHeaderFillSprite.width + menuX, menuY);
			}
			LoginManager.menuHeaderEdgeSprite.render(menuX, menuY);
			LoginManager.menuHeaderEdgeSprite.renderHorizontalFlip(menuX + menuWidth - LoginManager.menuHeaderEdgeSprite.width, menuY);
		}
		Fonts.b12Full.renderLeft(LocalizedText.CHOOSE_OPTION, menuX + 3, menuY + 14, LoginManager.menuTextColor, -1);
		if (GlRenderer.enabled) {
			GlRaster.fillRectAlpha(menuX, menuY + 20, menuWidth, menuHeight - 20, LoginManager.menuFillColor, 256 - LoginManager.menuFillTransparency);
		} else {
			SoftwareRaster.fillRectAlpha(menuX, menuY + 20, menuWidth, menuHeight - 20, LoginManager.menuFillColor, 256 - LoginManager.menuFillTransparency);
		}
		mouseY = Mouse.lastMouseY;
		mouseX = Mouse.lastMouseX;
		@Pc(203) int i;
		@Pc(219) int entryY;
		for (i = 0; i < size; i++) {
			entryY = (size - i - 1) * 15 + menuY + 35;
			if (menuX < mouseX && mouseX < menuX + menuWidth && mouseY > entryY - 13 && mouseY < entryY + 3) {
				if (GlRenderer.enabled) {
					GlRaster.fillRectAlpha(menuX, entryY - 13, menuWidth, 16, LoginManager.menuHighlightColor, 256 - LoginManager.menuHighlightTransparency);
				} else {
					SoftwareRaster.fillRectAlpha(menuX, entryY - 13, menuWidth, 16, LoginManager.menuHighlightColor, 256 - LoginManager.menuHighlightTransparency);
				}
			}
		}
		if ((LoginManager.menuBottomFillSprite == null || LoginManager.menuSideFillSprite == null || LoginManager.menuBottomEdgeSprite == null) && client.js5Archive8.isFileReady(LoginManager.menuBottomFillSpriteId) && client.js5Archive8.isFileReady(LoginManager.menuSideFillSpriteId) && client.js5Archive8.isFileReady(LoginManager.menuBottomEdgeSpriteId)) {
			LoginManager.menuBottomFillSprite = SoftwareSprite.loadSoftwareAlphaSprite(client.js5Archive8, LoginManager.menuBottomFillSpriteId);
			LoginManager.menuSideFillSprite = SoftwareSprite.loadSoftwareAlphaSprite(client.js5Archive8, LoginManager.menuSideFillSpriteId);
			LoginManager.menuBottomEdgeSprite = SoftwareSprite.loadSoftwareAlphaSprite(client.js5Archive8, LoginManager.menuBottomEdgeSpriteId);
			if (GlRenderer.enabled) {
				if (LoginManager.menuBottomFillSprite instanceof SoftwareAlphaSprite) {
					LoginManager.menuBottomFillSprite = new GlAlphaSprite((SoftwareSprite) LoginManager.menuBottomFillSprite);
				} else {
					LoginManager.menuBottomFillSprite = new GlSprite((SoftwareSprite) LoginManager.menuBottomFillSprite);
				}
				if (LoginManager.menuSideFillSprite instanceof SoftwareAlphaSprite) {
					LoginManager.menuSideFillSprite = new GlAlphaSprite((SoftwareSprite) LoginManager.menuSideFillSprite);
				} else {
					LoginManager.menuSideFillSprite = new GlSprite((SoftwareSprite) LoginManager.menuSideFillSprite);
				}
				if (LoginManager.menuBottomEdgeSprite instanceof SoftwareAlphaSprite) {
					LoginManager.menuBottomEdgeSprite = new GlAlphaSprite((SoftwareSprite) LoginManager.menuBottomEdgeSprite);
				} else {
					LoginManager.menuBottomEdgeSprite = new GlSprite((SoftwareSprite) LoginManager.menuBottomEdgeSprite);
				}
			}
		}
		@Pc(418) int textColor;
		if (LoginManager.menuBottomFillSprite != null && LoginManager.menuSideFillSprite != null && LoginManager.menuBottomEdgeSprite != null) {
			i = menuWidth / LoginManager.menuBottomFillSprite.width;
			for (entryY = 0; entryY < i; entryY++) {
				LoginManager.menuBottomFillSprite.render(menuX + LoginManager.menuBottomFillSprite.width * entryY, menuHeight + menuY + -LoginManager.menuBottomFillSprite.height);
			}
			entryY = (menuHeight - 20) / LoginManager.menuSideFillSprite.height;
			for (textColor = 0; textColor < entryY; textColor++) {
				LoginManager.menuSideFillSprite.render(menuX, menuY + textColor * LoginManager.menuSideFillSprite.height + 20);
				LoginManager.menuSideFillSprite.renderHorizontalFlip(menuX + menuWidth - LoginManager.menuSideFillSprite.width, menuY + 20 + textColor * LoginManager.menuSideFillSprite.height);
			}
			LoginManager.menuBottomEdgeSprite.render(menuX, menuHeight + menuY - LoginManager.menuBottomEdgeSprite.height);
			LoginManager.menuBottomEdgeSprite.renderHorizontalFlip(menuX + menuWidth - LoginManager.menuBottomEdgeSprite.width, menuY - -menuHeight + -LoginManager.menuBottomEdgeSprite.height);
		}
		for (i = 0; i < size; i++) {
			entryY = (size - i - 1) * 15 + menuY + 35;
			textColor = LoginManager.menuTextColor;
			if (menuX < mouseX && menuWidth + menuX > mouseX && entryY - 13 < mouseY && mouseY < entryY + 3) {
				textColor = LoginManager.menuHighlightTextColor;
			}
			Fonts.b12Full.renderLeft(getOp(i), menuX + 3, entryY, textColor, 0);
		}
		InterfaceList.forceRedrawScreen(InterfaceList.menuX, InterfaceList.menuY, InterfaceList.menuHeight, InterfaceList.menuWidth);
	}
}
