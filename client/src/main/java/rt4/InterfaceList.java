package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.*;

public class InterfaceList {
	@OriginalMember(owner = "client!p", name = "c", descriptor = "Lclient!ih;")
	public static final LinkedList lowPriorityRequests = new LinkedList();
	@OriginalMember(owner = "client!ch", name = "y", descriptor = "[Z")
	public static final boolean[] rectangleRedraw = new boolean[100];
	@OriginalMember(owner = "client!qj", name = "i", descriptor = "[I")
	public static final int[] keyChars = new int[128];
	@OriginalMember(owner = "client!sg", name = "q", descriptor = "[I")
	public static final int[] keyCodes = new int[128];
	@OriginalMember(owner = "client!rb", name = "b", descriptor = "Lclient!sc;")
	public static final HashTable properties = new HashTable(512);
	@OriginalMember(owner = "client!vh", name = "b", descriptor = "[I")
	public static final int[] rectangleX = new int[100];
	@OriginalMember(owner = "client!e", name = "sc", descriptor = "[I")
	public static final int[] rectangleY = new int[100];
	@OriginalMember(owner = "client!sd", name = "V", descriptor = "[I")
	public static final int[] rectangleWidth = new int[100];
	@OriginalMember(owner = "client!fe", name = "lb", descriptor = "[I")
	public static final int[] rectangleHeight = new int[100];
	@OriginalMember(owner = "client!pa", name = "R", descriptor = "[Z")
	public static final boolean[] rectangleDirty = new boolean[100];
	@OriginalMember(owner = "client!ql", name = "h", descriptor = "Lclient!na;")
	public static final JagString HIDDEN_OPTION_PREFIX = JagString.parse("Hidden)2");
	@OriginalMember(owner = "client!ja", name = "f", descriptor = "Lclient!ih;")
	public static final LinkedList mediumPriorityRequests = new LinkedList();
	@OriginalMember(owner = "client!ac", name = "i", descriptor = "Lclient!ih;")
	public static final LinkedList highPriorityRequests = new LinkedList();
	@OriginalMember(owner = "client!sc", name = "z", descriptor = "[Z")
	public static final boolean[] rectangleDirtySnapshot = new boolean[100];
	@OriginalMember(owner = "client!bn", name = "V", descriptor = "I")
	public static int rectangles = 0;
	@OriginalMember(owner = "client!md", name = "W", descriptor = "I")
	public static int topLevelInterface = -1;
	@OriginalMember(owner = "client!bm", name = "f", descriptor = "Lclient!ve;")
	public static Js5 spriteProvider;
	@OriginalMember(owner = "client!nd", name = "v", descriptor = "Lclient!ve;")
	public static Js5 fontProvider;
	@OriginalMember(owner = "client!qg", name = "ab", descriptor = "Lclient!ve;")
	public static Js5 interfaceProvider;
	@OriginalMember(owner = "client!qh", name = "g", descriptor = "Lclient!ve;")
	public static Js5 modelProvider;
	@OriginalMember(owner = "client!th", name = "j", descriptor = "[[Lclient!be;")
	public static Component[][] components;
	@OriginalMember(owner = "client!sc", name = "m", descriptor = "[Z")
	public static boolean[] interfaceLoaded;
	@OriginalMember(owner = "client!oj", name = "y", descriptor = "I")
	public static int keyQueueSize = 0;
	@OriginalMember(owner = "client!je", name = "fb", descriptor = "I")
	public static int transmitTimer = 1;
	@OriginalMember(owner = "client!ra", name = "J", descriptor = "I")
	public static int miscTransmitAt = 0;
	@OriginalMember(owner = "client!je", name = "T", descriptor = "Lclient!sc;")
	public static HashTable openInterfaces = new HashTable(8);
	@OriginalMember(owner = "client!wl", name = "h", descriptor = "Lclient!be;")
	public static Component gameViewportComponent = null;
	@OriginalMember(owner = "client!jd", name = "i", descriptor = "Lclient!be;")
	public static Component clickedInventoryComponent;
	@OriginalMember(owner = "client!nf", name = "h", descriptor = "Lclient!be;")
	public static Component mouseOverInventoryInterface;
	@OriginalMember(owner = "client!qk", name = "f", descriptor = "I")
	public static int clickedInventoryComponentY = 0;
	@OriginalMember(owner = "client!ef", name = "r", descriptor = "Lclient!be;")
	public static Component dragTargetComponent = null;
	@OriginalMember(owner = "client!bn", name = "O", descriptor = "I")
	public static int menuWidth;
	@OriginalMember(owner = "client!bc", name = "X", descriptor = "I")
	public static int menuHeight;
	@OriginalMember(owner = "client!si", name = "ab", descriptor = "I")
	public static int menuY;
	@OriginalMember(owner = "client!sh", name = "f", descriptor = "I")
	public static int dragParentY = -1;
	@OriginalMember(owner = "client!gd", name = "j", descriptor = "I")
	public static int clickedInventoryComponentCycle = 0;
	@OriginalMember(owner = "client!lj", name = "w", descriptor = "I")
	public static int clickedInventoryComponentX = 0;
	@OriginalMember(owner = "client!og", name = "e", descriptor = "Lclient!be;")
	public static Component hoveredComponent;
	@OriginalMember(owner = "client!ok", name = "b", descriptor = "I")
	public static int menuX;
	@OriginalMember(owner = "client!lg", name = "b", descriptor = "Z")
	public static boolean dragParentFound = false;
	@OriginalMember(owner = "client!ac", name = "n", descriptor = "I")
	public static int mouseOverInventoryObjectIndex = 0;
	@OriginalMember(owner = "client!jj", name = "j", descriptor = "Z")
	public static boolean draggingClickedInventoryObject = false;
	@OriginalMember(owner = "client!rg", name = "s", descriptor = "I")
	public static int viewportX = -1;
	@OriginalMember(owner = "client!oj", name = "v", descriptor = "I")
	public static int currentRenderLoop = -2;
	@OriginalMember(owner = "client!pm", name = "hb", descriptor = "I")
	public static int worldMapDragStartX;
	@OriginalMember(owner = "client!fk", name = "e", descriptor = "I")
	public static int worldMapDragStartY;
	@OriginalMember(owner = "client!bj", name = "s", descriptor = "I")
	public static int dragSourceY = -1;
	@OriginalMember(owner = "client!kl", name = "s", descriptor = "I")
	public static int worldMapDragState = 0;
	@OriginalMember(owner = "client!jk", name = "p", descriptor = "I")
	public static int dragSourceX = -1;
	@OriginalMember(owner = "client!df", name = "n", descriptor = "I")
	public static int scrollbarDragMargin = 0;
	@OriginalMember(owner = "client!di", name = "H", descriptor = "Z")
	public static boolean scrollbarDragging = false;
	@OriginalMember(owner = "client!dh", name = "a", descriptor = "Z")
	public static boolean dragSourceFound = false;
	@OriginalMember(owner = "client!ja", name = "r", descriptor = "I")
	public static int currentCursorId = -1;
	@OriginalMember(owner = "client!a", name = "h", descriptor = "I")
	public static int clickOffsetX;
	@OriginalMember(owner = "client!ve", name = "w", descriptor = "Z")
	public static boolean useStyledMenu = false;

	@OriginalMember(owner = "client!ab", name = "a", descriptor = "(ZLclient!ve;Lclient!ve;Lclient!ve;Lclient!ve;)V")
	public static void init(@OriginalArg(1) Js5 fontProvider, @OriginalArg(2) Js5 spriteProvider, @OriginalArg(3) Js5 interfaceProvider, @OriginalArg(4) Js5 modelProvider) {
		InterfaceList.spriteProvider = spriteProvider;
		InterfaceList.fontProvider = fontProvider;
		InterfaceList.interfaceProvider = interfaceProvider;
		InterfaceList.modelProvider = modelProvider;
		components = new Component[interfaceProvider.capacity()][];
		interfaceLoaded = new boolean[interfaceProvider.capacity()];
	}

	@OriginalMember(owner = "client!ig", name = "a", descriptor = "(BI)V")
	public static void unload(@OriginalArg(1) int interfaceId) {
		if (interfaceId == -1 || !interfaceLoaded[interfaceId]) {
			return;
		}
		interfaceProvider.discardUnpacked(interfaceId);
		if (components[interfaceId] == null) {
			return;
		}
		@Pc(27) boolean canUnload = true;
		for (@Pc(29) int i = 0; i < components[interfaceId].length; i++) {
			if (components[interfaceId][i] != null) {
				if (components[interfaceId][i].type == 2) {
					canUnload = false;
				} else {
					components[interfaceId][i] = null;
				}
			}
		}
		if (canUnload) {
			components[interfaceId] = null;
		}
		interfaceLoaded[interfaceId] = false;
	}

	@OriginalMember(owner = "client!tm", name = "b", descriptor = "(II)Z")
	public static boolean load(@OriginalArg(0) int interfaceId) {
		if (interfaceLoaded[interfaceId]) {
			return true;
		}

		if (interfaceProvider.isGroupReady(interfaceId)) {
			@Pc(25) int fileCount = interfaceProvider.getGroupCapacity(interfaceId);
			if (fileCount == 0) {
				interfaceLoaded[interfaceId] = true;
				return true;
			}
			if (components[interfaceId] == null) {
				components[interfaceId] = new Component[fileCount];
			}
			for (@Pc(46) int i = 0; i < fileCount; i++) {
				if (components[interfaceId][i] == null) {
					@Pc(62) byte[] data = interfaceProvider.fetchFile(interfaceId, i);
					if (data != null) {
						@Pc(74) Component component = components[interfaceId][i] = new Component();
						component.id = i + (interfaceId << 16);
						if (data[0] == -1) {
							component.decodeIf3(new Buffer(data));
						} else {
							component.decodeIf1(new Buffer(data));
						}
					}
				}
			}
			interfaceLoaded[interfaceId] = true;
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!eb", name = "d", descriptor = "(I)V")
	public static void reset() {
		components = new Component[interfaceProvider.capacity()][];
		interfaceLoaded = new boolean[interfaceProvider.capacity()];
	}

	@OriginalMember(owner = "client!i", name = "i", descriptor = "(Z)V")
	public static void redrawActiveInterfaces() {
		for (@Pc(6) ComponentPointer ptr = (ComponentPointer) openInterfaces.head(); ptr != null; ptr = (ComponentPointer) openInterfaces.next()) {
			@Pc(14) int interfaceId = ptr.interfaceId;
			if (load(interfaceId)) {
				@Pc(21) boolean isIf3 = true;
				@Pc(25) Component[] comps = components[interfaceId];
				@Pc(27) int parentId;
				for (parentId = 0; parentId < comps.length; parentId++) {
					if (comps[parentId] != null) {
						isIf3 = comps[parentId].if3;
						break;
					}
				}
				if (!isIf3) {
					parentId = (int) ptr.key;
					@Pc(60) Component parent = getComponent(parentId);
					if (parent != null) {
						redraw(parent);
					}
				}
			}
		}
	}

	public static void fullRedrawAllInterfaces() {
		// Loop over all the possible interfaces
		for (int i = 0; i < components.length; i++) {
			if (components[i] == null) {
				load(i);
			}
			if (components[i] == null) {
				continue;
			}
			for (int j = 0; j < components[i].length; j++) {
				if (components[i][j] != null) {
					redraw(components[i][j]);
				}
			}
		}
	}

	@OriginalMember(owner = "client!af", name = "a", descriptor = "(BI)Lclient!be;")
	public static Component getComponent(@OriginalArg(1) int id) {
		try {
			@Pc(7) int parent = id >> 16;
			@Pc(18) int child = id & 0xFFFF;
			if (components.length <= parent || parent < 0) {
				return null;
			}
			if (components[parent] == null || components[parent].length <= child || components[parent][child] == null) {
				@Pc(33) boolean success = load(parent);
				if (!success) {
					return null;
				}
			}
			if (components[parent].length <= child) {
				return null;
			}
			return components[parent][child];
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@OriginalMember(owner = "client!client", name = "b", descriptor = "(Lclient!be;)Lclient!bf;")
	public static ServerActiveProperties getServerActiveProperties(@OriginalArg(0) Component component) {
		@Pc(13) ServerActiveProperties props = (ServerActiveProperties) properties.get(((long) component.id << 32) + (long) component.createdComponentId);
		return props == null ? component.properties : props;
	}

	@OriginalMember(owner = "client!dg", name = "a", descriptor = "(ILclient!be;)V")
	public static void redraw(@OriginalArg(1) Component component) {
		if (currentRenderLoop == component.rectangleLoop) {
			rectangleDirty[component.rectangle] = true;
		}
	}

	@OriginalMember(owner = "client!qj", name = "a", descriptor = "(Lclient!be;BI)Lclient!na;")
	public static JagString getOp(@OriginalArg(0) Component component, @OriginalArg(2) int opIndex) {
		if (!getServerActiveProperties(component).isButtonEnabled(opIndex) && component.onOptionClick == null) {
			return null;
		} else if (component.ops == null || component.ops.length <= opIndex || component.ops[opIndex] == null || component.ops[opIndex].trim().length() == 0) {
			return Cheat.qaOpTest ? JagString.concatenate(new JagString[]{HIDDEN_OPTION_PREFIX, JagString.parseInt(opIndex)}) : null;
		} else {
			return component.ops[opIndex];
		}
	}

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(BII)Lclient!be;")
	public static Component getComponent(@OriginalArg(1) int parentId, @OriginalArg(2) int childId) {
		@Pc(7) Component parent = getComponent(parentId);
		if (childId == -1) {
			return parent;
		} else if (parent == null || parent.createdComponents == null || parent.createdComponents.length <= childId) {
			return null;
		} else {
			return parent.createdComponents[childId];
		}
	}

	@OriginalMember(owner = "client!gg", name = "e", descriptor = "(II)V")
	public static void resetAnimations(@OriginalArg(0) int interfaceId) {
		if (!load(interfaceId)) {
			return;
		}
		@Pc(15) Component[] children = components[interfaceId];
		for (@Pc(17) int i = 0; i < children.length; i++) {
			@Pc(29) Component child = children[i];
			if (child != null) {
				child.seqNextFrame = 1;
				child.seqFrame = 0;
				child.seqCycle = 0;
			}
		}
	}

	@OriginalMember(owner = "client!rb", name = "a", descriptor = "(ZB)V")
	public static void layoutTopLevel(@OriginalArg(0) boolean forceResize) {
		layoutInterface(GameShell.canvasHeight, forceResize, topLevelInterface, GameShell.canvasWidth);
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IZIII)V")
	public static void layoutInterface(@OriginalArg(0) int height, @OriginalArg(1) boolean forceResize, @OriginalArg(3) int interfaceId, @OriginalArg(4) int width) {
		if (load(interfaceId)) {
			layoutComponents(-1, forceResize, width, height, components[interfaceId]);
		}
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(Lclient!be;ZI)V")
	public static void layoutComponent(@OriginalArg(0) Component component, @OriginalArg(1) boolean forceResize) {
		@Pc(20) int scrollW = component.scrollMaxH == 0 ? component.width : component.scrollMaxH;
		@Pc(32) int scrollH = component.scrollMaxV == 0 ? component.height : component.scrollMaxV;
		layoutComponents(component.id, forceResize, scrollW, scrollH, components[component.id >> 16]);
		if (component.createdComponents != null) {
			layoutComponents(component.id, forceResize, scrollW, scrollH, component.createdComponents);
		}
		@Pc(66) ComponentPointer ptr = (ComponentPointer) openInterfaces.get(component.id);
		if (ptr != null) {
			layoutInterface(scrollH, forceResize, ptr.interfaceId, scrollW);
		}
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(IZIII[Lclient!be;)V")
	public static void layoutComponents(@OriginalArg(0) int overlayerId, @OriginalArg(1) boolean forceResize, @OriginalArg(2) int width, @OriginalArg(4) int height, @OriginalArg(5) Component[] children) {
		for (@Pc(3) int i = 0; i < children.length; i++) {
			@Pc(19) Component child = children[i];
			if (child != null && child.overlayer == overlayerId) {
				calculateSize(height, width, child, forceResize);
				calculatePosition(child, height, width);
				if (child.scrollMaxH - child.width < child.scrollX) {
					child.scrollX = child.scrollMaxH - child.width;
				}
				if (child.scrollY > child.scrollMaxV - child.height) {
					child.scrollY = child.scrollMaxV - child.height;
				}
				if (child.scrollY < 0) {
					child.scrollY = 0;
				}
				if (child.scrollX < 0) {
					child.scrollX = 0;
				}
				if (child.type == 0) {
					layoutComponent(child, forceResize);
				}
			}
		}
	}

	@OriginalMember(owner = "client!fn", name = "c", descriptor = "(II)V")
	public static void runOnLoadScripts(@OriginalArg(0) int interfaceId) {
		if (interfaceId == -1 || !load(interfaceId)) {
			return;
		}
		@Pc(31) Component[] children = components[interfaceId];
		for (@Pc(33) int i = 0; i < children.length; i++) {
			@Pc(41) Component child = children[i];
			if (child.onLoad != null) {
				@Pc(50) HookRequest hookRequest = new HookRequest();
				hookRequest.arguments = child.onLoad;
				hookRequest.source = child;
				ScriptRunner.run(2000000, hookRequest);
			}
		}
	}

	@OriginalMember(owner = "client!ke", name = "a", descriptor = "(ZLclient!wk;Z)V")
	public static void closeInterface(@OriginalArg(0) boolean shouldUnload, @OriginalArg(1) ComponentPointer pointer) {
		@Pc(9) int componentId = (int) pointer.key;
		@Pc(16) int interfaceId = pointer.interfaceId;
		pointer.unlink();
		if (shouldUnload) {
			unload(interfaceId);
		}
		removeProperties(interfaceId);
		@Pc(32) Component component = getComponent(componentId);
		if (component != null) {
			redraw(component);
		}
		@Pc(41) int menuSize = MiniMenu.size;
		@Pc(43) int maxWidth;
		for (maxWidth = 0; maxWidth < menuSize; maxWidth++) {
			if (isInterfaceAction(MiniMenu.actions[maxWidth])) {
				MiniMenu.remove(maxWidth);
			}
		}
		if (MiniMenu.size == 1) {
			Cs1ScriptRunner.isMenuOpen = false;
			redrawScreen(menuX, menuWidth, menuY, menuHeight);
		} else {
			redrawScreen(menuX, menuWidth, menuY, menuHeight);
			maxWidth = Fonts.b12Full.getStringWidth(LocalizedText.CHOOSE_OPTION);
			for (@Pc(75) int i = 0; i < MiniMenu.size; i++) {
				@Pc(88) int opWidth = Fonts.b12Full.getStringWidth(MiniMenu.getOp(i));
				if (maxWidth < opWidth) {
					maxWidth = opWidth;
				}
			}
			menuHeight = MiniMenu.size * 15 + (useStyledMenu ? 26 : 22);
			menuWidth = maxWidth + 8;
		}
		if (topLevelInterface != -1) {
			runScripts(1, topLevelInterface);
		}
	}

	@OriginalMember(owner = "client!ii", name = "a", descriptor = "(Lclient!be;III)V")
	public static void calculatePosition(@OriginalArg(0) Component component, @OriginalArg(2) int parentH, @OriginalArg(3) int parentW) {
		if (component.xMode == 0) {
			component.y = component.baseY;
		} else if (component.xMode == 1) {
			component.y = (parentH - component.height) / 2 + component.baseY;
		} else if (component.xMode == 2) {
			component.y = parentH - component.height - component.baseY;
		} else if (component.xMode == 3) {
			component.y = component.baseY * parentH >> 14;
		} else if (component.xMode == 4) {
			component.y = (parentH * component.baseY >> 14) + (parentH - component.height) / 2;
		} else {
			component.y = parentH - (parentH * component.baseY >> 14) - component.height;
		}
		if (component.yMode == 0) {
			component.x = component.baseX;
		} else if (component.yMode == 1) {
			component.x = component.baseX + (parentW - component.width) / 2;
		} else if (component.yMode == 2) {
			component.x = parentW - component.baseX - component.width;
		} else if (component.yMode == 3) {
			component.x = component.baseX * parentW >> 14;
		} else if (component.yMode == 4) {
			component.x = (component.baseX * parentW >> 14) + (parentW - component.width) / 2;
		} else {
			component.x = parentW - (parentW * component.baseX >> 14) - component.width;
		}
		if (!Cheat.qaOpTest || getServerActiveProperties(component).events == 0 && component.type != 0) {
			return;
		}
		if (component.y < 0) {
			component.y = 0;
		} else if (component.height + component.y > parentH) {
			component.y = parentH - component.height;
		}
		if (component.x < 0) {
			component.x = 0;
		} else if (parentW < component.x + component.width) {
			component.x = parentW - component.width;
		}
	}

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(ILclient!be;)V")
	public static void update(@OriginalArg(1) Component component) {
		@Pc(7) Component parent = getParent(component);
		@Pc(19) int parentW;
		@Pc(17) int parentH;
		if (parent == null) {
			parentH = GameShell.canvasHeight;
			parentW = GameShell.canvasWidth;
		} else {
			parentH = parent.height;
			parentW = parent.width;
		}
		calculateSize(parentH, parentW, component, false);
		calculatePosition(component, parentH, parentW);
	}

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(IIILclient!be;Z)V")
	public static void calculateSize(@OriginalArg(0) int parentH, @OriginalArg(2) int parentW, @OriginalArg(3) Component component, @OriginalArg(4) boolean triggerResize) {
		@Pc(4) int oldW = component.width;
		@Pc(7) int oldH = component.height;
		if (component.dynamicWidthValue == 0) {
			component.width = component.baseWidth;
		} else if (component.dynamicWidthValue == 1) {
			component.width = parentW - component.baseWidth;
		} else if (component.dynamicWidthValue == 2) {
			component.width = component.baseWidth * parentW >> 14;
		} else if (component.dynamicWidthValue == 3) {
			if (component.type == 2) {
				component.width = component.baseWidth * 32 + (component.baseWidth - 1) * component.invMarginX;
			} else if (component.type == 7) {
				component.width = component.baseWidth * 115 + component.invMarginX * (component.baseWidth - 1);
			}
		}
		if (component.dynamicHeightValue == 0) {
			component.height = component.baseHeight;
		} else if (component.dynamicHeightValue == 1) {
			component.height = parentH - component.baseHeight;
		} else if (component.dynamicHeightValue == 2) {
			component.height = parentH * component.baseHeight >> 14;
		} else if (component.dynamicHeightValue == 3) {
			if (component.type == 2) {
				component.height = (component.baseHeight - 1) * component.invMarginY + component.baseHeight * 32;
			} else if (component.type == 7) {
				component.height = component.baseHeight * 12 + (component.baseHeight - 1) * component.invMarginY;
			}
		}
		if (component.dynamicWidthValue == 4) {
			component.width = component.aspectWidth * component.height / component.aspectHeight;
		}
		if (component.dynamicHeightValue == 4) {
			component.height = component.aspectHeight * component.width / component.aspectWidth;
		}
		if (Cheat.qaOpTest && (getServerActiveProperties(component).events != 0 || component.type == 0)) {
			if (component.height < 5 && component.width < 5) {
				component.height = 5;
				component.width = 5;
			} else {
				if (component.width <= 0) {
					component.width = 5;
				}
				if (component.height <= 0) {
					component.height = 5;
				}
			}
		}
		if (component.clientCode == 1337) {
			gameViewportComponent = component;
		}
		if (triggerResize && component.onResize != null && (oldW != component.width || component.height != oldH)) {
			@Pc(305) HookRequest hookRequest = new HookRequest();
			hookRequest.arguments = component.onResize;
			hookRequest.source = component;
			lowPriorityRequests.addTail(hookRequest);
		}
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(Lclient!be;I)Lclient!be;")
	public static Component getParent(@OriginalArg(0) Component component) {
		if (component.overlayer != -1) {
			return getComponent(component.overlayer);
		}
		@Pc(28) int interfaceId = component.id >>> 16;
		@Pc(33) HashTableIterator iter = new HashTableIterator(openInterfaces);
		for (@Pc(38) ComponentPointer ptr = (ComponentPointer) iter.first(); ptr != null; ptr = (ComponentPointer) iter.next()) {
			if (interfaceId == ptr.interfaceId) {
				return getComponent((int) ptr.key);
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!kf", name = "a", descriptor = "(IIBII)V")
	public static void redrawScreen(@OriginalArg(0) int x, @OriginalArg(1) int w, @OriginalArg(3) int y, @OriginalArg(4) int h) {
		for (@Pc(12) int i = 0; i < rectangles; i++) {
			if (rectangleWidth[i] + rectangleX[i] > x && w + x > rectangleX[i] && y < rectangleHeight[i] + rectangleY[i] && rectangleY[i] < y + h) {
				rectangleDirty[i] = true;
			}
		}
	}

	@OriginalMember(owner = "client!we", name = "b", descriptor = "(BI)V")
	public static void removeProperties(@OriginalArg(1) int interfaceId) {
		for (@Pc(11) Node node = properties.head(); node != null; node = properties.next()) {
			if ((node.key >> 48 & 0xFFFFL) == (long) interfaceId) {
				node.unlink();
			}
		}
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(III)V")
	public static void runScripts(@OriginalArg(1) int scriptId, @OriginalArg(2) int interfaceId) {
		if (load(interfaceId)) {
			runScriptsRecursive(components[interfaceId], scriptId);
		}
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "([Lclient!be;ZI)V")
	public static void runScriptsRecursive(@OriginalArg(0) Component[] children, @OriginalArg(2) int scriptId) {
		for (@Pc(11) int i = 0; i < children.length; i++) {
			@Pc(23) Component child = children[i];
			if (child != null) {
				if (child.type == 0) {
					if (child.createdComponents != null) {
						runScriptsRecursive(child.createdComponents, scriptId);
					}
					@Pc(49) ComponentPointer ptr = (ComponentPointer) openInterfaces.get(child.id);
					if (ptr != null) {
						runScripts(scriptId, ptr.interfaceId);
					}
				}
				@Pc(72) HookRequest hookRequest;
				if (scriptId == 0 && child.onDialogAbort != null) {
					hookRequest = new HookRequest();
					hookRequest.arguments = child.onDialogAbort;
					hookRequest.source = child;
					ScriptRunner.run(hookRequest);
				}
				if (scriptId == 1 && child.onWidgetsOpenClose != null) {
					if (child.createdComponentId >= 0) {
						@Pc(103) Component parent = getComponent(child.id);
						if (parent == null || parent.createdComponents == null || child.createdComponentId >= parent.createdComponents.length || parent.createdComponents[child.createdComponentId] != child) {
							continue;
						}
					}
					hookRequest = new HookRequest();
					hookRequest.arguments = child.onWidgetsOpenClose;
					hookRequest.source = child;
					ScriptRunner.run(hookRequest);
				}
			}
		}
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(IIIIIIII)V")
	public static void processSubInterface(@OriginalArg(0) int scrollY, @OriginalArg(1) int y, @OriginalArg(3) int scrollX, @OriginalArg(4) int h, @OriginalArg(5) int interfaceId, @OriginalArg(6) int x, @OriginalArg(7) int w) {
		if (load(interfaceId)) {
			processComponents(components[interfaceId], -1, x, y, h, w, scrollY, scrollX);
		}
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "([Lclient!be;IIIIIII)V")
	public static void processComponents(@OriginalArg(0) Component[] children, @OriginalArg(1) int overlayerId, @OriginalArg(2) int clipLeft, @OriginalArg(3) int clipTop, @OriginalArg(4) int clipRight, @OriginalArg(5) int clipBottom, @OriginalArg(6) int parentX, @OriginalArg(7) int parentY) {
		for (@Pc(1) int i = 0; i < children.length; i++) {
			@Pc(9) Component component = children[i];
			if (component != null && component.overlayer == overlayerId && (!component.if3 || component.type == 0 || component.hasEventHandlers || getServerActiveProperties(component).events != 0 || component == Cs1ScriptRunner.dragParentComponent || component.clientCode == 1338) && (!component.if3 || !isHidden(component))) {
				@Pc(50) int absX = component.x + parentX;
				@Pc(55) int absY = component.y + parentY;
				@Pc(61) int left;
				@Pc(63) int top;
				@Pc(65) int right;
				@Pc(67) int bottom;
				if (component.type == 2) {
					left = clipLeft;
					top = clipTop;
					right = clipRight;
					bottom = clipBottom;
				} else {
					@Pc(73) int compRight = absX + component.width;
					@Pc(78) int compBottom = absY + component.height;
					if (component.type == 9) {
						compRight++;
						compBottom++;
					}
					left = absX > clipLeft ? absX : clipLeft;
					top = absY > clipTop ? absY : clipTop;
					right = compRight < clipRight ? compRight : clipRight;
					bottom = compBottom < clipBottom ? compBottom : clipBottom;
				}
				if (component == Cs1ScriptRunner.draggedComponent) {
					dragSourceFound = true;
					dragSourceX = absX;
					dragSourceY = absY;
				}
				if (!component.if3 || left < right && top < bottom) {
					if (component.type == 0) {
						if (!component.if3 && isHidden(component) && hoveredComponent != component) {
							continue;
						}
						if (component.noClickThrough && Mouse.lastMouseX >= left && Mouse.lastMouseY >= top && Mouse.lastMouseX < right && Mouse.lastMouseY < bottom) {
							for (@Pc(164) HookRequest req = (HookRequest) lowPriorityRequests.head(); req != null; req = (HookRequest) lowPriorityRequests.next()) {
								if (req.cancelOnMouseExit) {
									req.unlink();
									req.source.mouseOver = false;
								}
							}
							if (Cs1ScriptRunner.dragElapsedTicks == 0) {
								Cs1ScriptRunner.draggedComponent = null;
								Cs1ScriptRunner.dragParentComponent = null;
							}
							worldMapDragState = 0;
						}
					}
					if (component.if3) {
						@Pc(207) boolean isHovered;
						isHovered = Mouse.lastMouseX >= left && Mouse.lastMouseY >= top && Mouse.lastMouseX < right && Mouse.lastMouseY < bottom;
						@Pc(212) boolean isPressed = Mouse.pressedButton == 1 && isHovered;
						@Pc(221) boolean isClicked = Mouse.clickButton == 1 && Mouse.clickX >= left && Mouse.clickY >= top && Mouse.clickX < right && Mouse.clickY < bottom;
						@Pc(243) int j;
						@Pc(322) int k;
						if (component.keyCodes != null) {
							for (j = 0; j < component.keyCodes.length; j++) {
								if (Keyboard.pressedKeys[component.keyCodes[j]]) {
									if (component.keyRepeatTimers == null || client.loop >= component.keyRepeatTimers[j]) {
										@Pc(279) byte modifier = component.keyModifiers[j];
										if (modifier == 0 || ((modifier & 0x2) == 0 || Keyboard.pressedKeys[Keyboard.KEY_ALT]) && ((modifier & 0x1) == 0 || Keyboard.pressedKeys[Keyboard.KEY_CTRL]) && ((modifier & 0x4) == 0 || Keyboard.pressedKeys[Keyboard.KEY_SHIFT])) {
											ClientProt.sendButtonClick(JagString.EMPTY, -1, j + 1, component.id);
											k = component.keyRepeatDelays[j];
											if (component.keyRepeatTimers == null) {
												component.keyRepeatTimers = new int[component.keyCodes.length];
											}
											if (k == 0) {
												component.keyRepeatTimers[j] = Integer.MAX_VALUE;
											} else {
												component.keyRepeatTimers[j] = client.loop + k;
											}
										}
									}
								} else if (component.keyRepeatTimers != null) {
									component.keyRepeatTimers[j] = 0;
								}
							}
						}
						if (isClicked) {
							Cs1ScriptRunner.startComponentDrag(Mouse.clickY - absY, Mouse.clickX - absX, component);
						}
						if (Cs1ScriptRunner.draggedComponent != null && Cs1ScriptRunner.draggedComponent != component && isHovered && getServerActiveProperties(component).isDragTarget()) {
							dragTargetComponent = component;
						}
						if (component == Cs1ScriptRunner.dragParentComponent) {
							dragParentFound = true;
							Cs1ScriptRunner.dragBoundsMinX = absX;
							dragParentY = absY;
						}
						if (component.hasEventHandlers || component.clientCode != 0) {
							@Pc(399) HookRequest request;
							if (isHovered && MouseWheel.wheelRotation != 0 && component.onScroll != null) {
								request = new HookRequest();
								request.cancelOnMouseExit = true;
								request.source = component;
								request.mouseY = MouseWheel.wheelRotation;
								request.arguments = component.onScroll;
								lowPriorityRequests.addTail(request);
							}
							if (Cs1ScriptRunner.draggedComponent != null || clickedInventoryComponent != null || Cs1ScriptRunner.isMenuOpen || component.clientCode != 1400 && worldMapDragState > 0) {
								isClicked = false;
								isPressed = false;
								isHovered = false;
							}
							@Pc(508) int skill;
							if (component.clientCode != 0) {
								if (component.clientCode == 1337) {
									gameViewportComponent = component;
									redraw(component);
									continue;
								}
								if (component.clientCode == 1338) {
									if (isClicked) {
										clickOffsetX = Mouse.clickX - absX;
										MiniMenu.clickOffsetY = Mouse.clickY - absY;
									}
									continue;
								}
								if (component.clientCode == 1400) {
									WorldMap.component = component;
									if (isClicked) {
										if (Keyboard.pressedKeys[Keyboard.KEY_CTRL] && LoginManager.staffModLevel > 0) {
											j = (int) ((double) (Mouse.clickX - absX - component.width / 2) * 2.0D / (double) WorldMap.zoom);
											skill = (int) ((double) (Mouse.clickY - absY - component.height / 2) * 2.0D / (double) WorldMap.zoom);
											k = WorldMap.viewX + j;
											@Pc(516) int mapY = WorldMap.viewY + skill;
											@Pc(520) int tileX = k + WorldMap.originX;
											@Pc(528) int tileY = WorldMap.length + WorldMap.originY - mapY - 1;
											Cheat.teleport(tileX, tileY, 0);
											ClientProt.closeWidget();
											continue;
										}
										worldMapDragState = 1;
										Cs1ScriptRunner.dragStartMouseX = Mouse.lastMouseX;
										Cs1ScriptRunner.dragStartMouseY = Mouse.lastMouseY;
										continue;
									}
									if (isPressed && worldMapDragState > 0) {
										if (worldMapDragState == 1 && (Cs1ScriptRunner.dragStartMouseX != Mouse.lastMouseX || Cs1ScriptRunner.dragStartMouseY != Mouse.lastMouseY)) {
											worldMapDragStartX = WorldMap.viewX;
											worldMapDragStartY = WorldMap.viewY;
											worldMapDragState = 2;
										}
										if (worldMapDragState == 2) {
											WorldMap.setViewX(worldMapDragStartX + (int) ((double) (Cs1ScriptRunner.dragStartMouseX - Mouse.lastMouseX) * 2.0D / (double) WorldMap.targetZoom));
											WorldMap.setViewY(worldMapDragStartY + (int) ((double) (Cs1ScriptRunner.dragStartMouseY - Mouse.lastMouseY) * 2.0D / (double) WorldMap.targetZoom));
										}
										continue;
									}
									worldMapDragState = 0;
									continue;
								}
								if (component.clientCode == 1401) {
									if (isPressed) {
										WorldMap.setViewFromMousePosition(component.width, Mouse.lastMouseY - absY, Mouse.lastMouseX - absX, component.height);
									}
									continue;
								}
								if (component.clientCode == 1402) {
									if (!GlRenderer.enabled) {
										redraw(component);
									}
									continue;
								}
							}
							if (!component.dragging && isClicked) {
								component.dragging = true;
								if (component.onClickRepeat != null) {
									request = new HookRequest();
									request.cancelOnMouseExit = true;
									request.source = component;
									request.mouseX = Mouse.clickX - absX;
									request.mouseY = Mouse.clickY - absY;
									request.arguments = component.onClickRepeat;
									lowPriorityRequests.addTail(request);
								}
							}
							if (component.dragging && isPressed && component.onDrag != null) {
								request = new HookRequest();
								request.cancelOnMouseExit = true;
								request.source = component;
								request.mouseX = Mouse.lastMouseX - absX;
								request.mouseY = Mouse.lastMouseY - absY;
								request.arguments = component.onDrag;
								lowPriorityRequests.addTail(request);
							}
							if (component.dragging && !isPressed) {
								component.dragging = false;
								if (component.onRelease != null) {
									request = new HookRequest();
									request.cancelOnMouseExit = true;
									request.source = component;
									request.mouseX = Mouse.lastMouseX - absX;
									request.mouseY = Mouse.lastMouseY - absY;
									request.arguments = component.onRelease;
									mediumPriorityRequests.addTail(request);
								}
							}
							if (isPressed && component.onHold != null) {
								request = new HookRequest();
								request.cancelOnMouseExit = true;
								request.source = component;
								request.mouseX = Mouse.lastMouseX - absX;
								request.mouseY = Mouse.lastMouseY - absY;
								request.arguments = component.onHold;
								lowPriorityRequests.addTail(request);
							}
							if (!component.mouseOver && isHovered) {
								component.mouseOver = true;
								if (component.onMouseOver != null) {
									request = new HookRequest();
									request.cancelOnMouseExit = true;
									request.source = component;
									request.mouseX = Mouse.lastMouseX - absX;
									request.mouseY = Mouse.lastMouseY - absY;
									request.arguments = component.onMouseOver;
									lowPriorityRequests.addTail(request);
								}
							}
							if (component.mouseOver && isHovered && component.onMouseRepeat != null) {
								request = new HookRequest();
								request.cancelOnMouseExit = true;
								request.source = component;
								request.mouseX = Mouse.lastMouseX - absX;
								request.mouseY = Mouse.lastMouseY - absY;
								request.arguments = component.onMouseRepeat;
								lowPriorityRequests.addTail(request);
							}
							if (component.mouseOver && !isHovered) {
								component.mouseOver = false;
								if (component.onMouseLeave != null) {
									request = new HookRequest();
									request.cancelOnMouseExit = true;
									request.source = component;
									request.mouseX = Mouse.lastMouseX - absX;
									request.mouseY = Mouse.lastMouseY - absY;
									request.arguments = component.onMouseLeave;
									mediumPriorityRequests.addTail(request);
								}
							}
							if (component.onTimer != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onTimer;
								highPriorityRequests.addTail(request);
							}
							@Pc(966) HookRequest request2;
							if (component.onVarcTransmit != null && VarcDomain.updatedVarcsWriterIndex > component.updatedVarcsReaderIndex) {
								if (component.varcTriggers == null || VarcDomain.updatedVarcsWriterIndex - component.updatedVarcsReaderIndex > 32) {
									request = new HookRequest();
									request.source = component;
									request.arguments = component.onVarcTransmit;
									lowPriorityRequests.addTail(request);
								} else {
									checkVarcs:
									for (j = component.updatedVarcsReaderIndex; j < VarcDomain.updatedVarcsWriterIndex; j++) {
										skill = VarcDomain.updatedVarcs[j & 0x1F];
										for (k = 0; k < component.varcTriggers.length; k++) {
											if (component.varcTriggers[k] == skill) {
												request2 = new HookRequest();
												request2.source = component;
												request2.arguments = component.onVarcTransmit;
												lowPriorityRequests.addTail(request2);
												break checkVarcs;
											}
										}
									}
								}
								component.updatedVarcsReaderIndex = VarcDomain.updatedVarcsWriterIndex;
							}
							if (component.onVarcstrTransmit != null && VarcDomain.updatedVarcstrsWriterIndex > component.updatedVarcstrsReaderIndex) {
								if (component.varcstrTriggers == null || VarcDomain.updatedVarcstrsWriterIndex - component.updatedVarcstrsReaderIndex > 32) {
									request = new HookRequest();
									request.source = component;
									request.arguments = component.onVarcstrTransmit;
									lowPriorityRequests.addTail(request);
								} else {
									checkVarcstrs:
									for (j = component.updatedVarcstrsReaderIndex; j < VarcDomain.updatedVarcstrsWriterIndex; j++) {
										skill = VarcDomain.updatedVarcstrs[j & 0x1F];
										for (k = 0; k < component.varcstrTriggers.length; k++) {
											if (component.varcstrTriggers[k] == skill) {
												request2 = new HookRequest();
												request2.source = component;
												request2.arguments = component.onVarcstrTransmit;
												lowPriorityRequests.addTail(request2);
												break checkVarcstrs;
											}
										}
									}
								}
								component.updatedVarcstrsReaderIndex = VarcDomain.updatedVarcstrsWriterIndex;
							}
							if (component.onVarpTransmit != null && VarpDomain.updatedVarpsWriterIndex > component.updatedVarpsReaderIndex) {
								if (component.varpTriggers == null || VarpDomain.updatedVarpsWriterIndex - component.updatedVarpsReaderIndex > 32) {
									request = new HookRequest();
									request.source = component;
									request.arguments = component.onVarpTransmit;
									lowPriorityRequests.addTail(request);
								} else {
									checkVarps:
									for (j = component.updatedVarpsReaderIndex; j < VarpDomain.updatedVarpsWriterIndex; j++) {
										skill = VarpDomain.updatedVarps[j & 0x1F];
										for (k = 0; k < component.varpTriggers.length; k++) {
											if (component.varpTriggers[k] == skill) {
												request2 = new HookRequest();
												request2.source = component;
												request2.arguments = component.onVarpTransmit;
												lowPriorityRequests.addTail(request2);
												break checkVarps;
											}
										}
									}
								}
								component.updatedVarpsReaderIndex = VarpDomain.updatedVarpsWriterIndex;
							}
							if (component.onInvTransmit != null && Inv.updatedInventoriesWriterIndex > component.updatedInventoriesReaderIndex) {
								if (component.inventoryTriggers == null || Inv.updatedInventoriesWriterIndex - component.updatedInventoriesReaderIndex > 32) {
									request = new HookRequest();
									request.source = component;
									request.arguments = component.onInvTransmit;
									lowPriorityRequests.addTail(request);
								} else {
									outer:
									for (j = component.updatedInventoriesReaderIndex; j < Inv.updatedInventoriesWriterIndex; j++) {
										skill = Inv.updatedInventories[j & 0x1F];
										for (k = 0; k < component.inventoryTriggers.length; k++) {
											if (component.inventoryTriggers[k] == skill) {
												request2 = new HookRequest();
												request2.source = component;
												request2.arguments = component.onInvTransmit;
												lowPriorityRequests.addTail(request2);
												break outer;
											}
										}
									}
								}
								component.updatedInventoriesReaderIndex = Inv.updatedInventoriesWriterIndex;
							}
							if (component.onStatTransmit != null && PlayerSkillXpTable.updatedStatsWriterIndex > component.updatedStatsReaderIndex) {
								if (component.statTriggers == null || PlayerSkillXpTable.updatedStatsWriterIndex - component.updatedStatsReaderIndex > 32) {
									request = new HookRequest();
									request.source = component;
									request.arguments = component.onStatTransmit;
									lowPriorityRequests.addTail(request);
								} else {
									outer:
									for (j = component.updatedStatsReaderIndex; j < PlayerSkillXpTable.updatedStatsWriterIndex; j++) {
										skill = PlayerSkillXpTable.updatedStats[j & 0x1F];
										for (k = 0; k < component.statTriggers.length; k++) {
											if (component.statTriggers[k] == skill) {
												request2 = new HookRequest();
												request2.source = component;
												request2.arguments = component.onStatTransmit;
												lowPriorityRequests.addTail(request2);
												break outer;
											}
										}
									}
								}
								component.updatedStatsReaderIndex = PlayerSkillXpTable.updatedStatsWriterIndex;
							}
							if (Chat.transmitAt > component.lastTransmitTimer && component.onMsg != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onMsg;
								lowPriorityRequests.addTail(request);
							}
							if (FriendsList.transmitAt > component.lastTransmitTimer && component.onFriendTransmit != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onFriendTransmit;
								lowPriorityRequests.addTail(request);
							}
							if (ClanChat.transmitAt > component.lastTransmitTimer && component.onClanTransmit != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onClanTransmit;
								lowPriorityRequests.addTail(request);
							}
							if (StockMarketManager.transmitAt > component.lastTransmitTimer && component.onStockTransmit != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onStockTransmit;
								lowPriorityRequests.addTail(request);
							}
							if (miscTransmitAt > component.lastTransmitTimer && component.onMiscTransmit != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onMiscTransmit;
								lowPriorityRequests.addTail(request);
							}
							component.lastTransmitTimer = transmitTimer;
							if (component.onKey != null) {
								for (j = 0; j < keyQueueSize; j++) {
									@Pc(1430) HookRequest keyRequest = new HookRequest();
									keyRequest.source = component;
									keyRequest.keyCode = keyCodes[j];
									keyRequest.keyChar = keyChars[j];
									keyRequest.arguments = component.onKey;
									lowPriorityRequests.addTail(keyRequest);
								}
							}
							if (Camera.splineJustFinished && component.onMinimapUnlock != null) {
								request = new HookRequest();
								request.source = component;
								request.arguments = component.onMinimapUnlock;
								lowPriorityRequests.addTail(request);
							}
						}
					}
					if (!component.if3 && Cs1ScriptRunner.draggedComponent == null && clickedInventoryComponent == null && !Cs1ScriptRunner.isMenuOpen) {
						if ((component.hoverOverlayer >= 0 || component.overColor != 0) && Mouse.lastMouseX >= left && Mouse.lastMouseY >= top && Mouse.lastMouseX < right && Mouse.lastMouseY < bottom) {
							if (component.hoverOverlayer >= 0) {
								hoveredComponent = children[component.hoverOverlayer];
							} else {
								hoveredComponent = component;
							}
						}
						if (component.type == 8 && Mouse.lastMouseX >= left && Mouse.lastMouseY >= top && Mouse.lastMouseX < right && Mouse.lastMouseY < bottom) {
							Protocol.tooltipComponent = component;
						}
						if (component.scrollMaxV > component.height) {
							handleScrollbar(Mouse.lastMouseY, component.height, component, Mouse.lastMouseX, absX + component.width, absY, component.scrollMaxV);
						}
					}
					if (component.type == 0) {
						processComponents(children, component.id, left, top, right, bottom, absX - component.scrollX, absY - component.scrollY);
						if (component.createdComponents != null) {
							processComponents(component.createdComponents, component.id, left, top, right, bottom, absX - component.scrollX, absY - component.scrollY);
						}
						@Pc(1595) ComponentPointer subPtr = (ComponentPointer) openInterfaces.get(component.id);
						if (subPtr != null) {
							processSubInterface(absX, top, absY, right, subPtr.interfaceId, left, bottom);
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(SI)Z")
	public static boolean isInterfaceAction(@OriginalArg(0) short actionCode) {
		if (actionCode == 47 || actionCode == 5 || actionCode == 43 || actionCode == 35 || actionCode == 58 || actionCode == 22 || actionCode == 40 || actionCode == 3) {
			return true;
		} else if (actionCode == 9 || actionCode == 12 || actionCode == 1006 || actionCode == 1003) {
			return true;
		} else if (actionCode == 25 || actionCode == 23 || actionCode == 48 || actionCode == 7 || actionCode == 13) {
			return true;
		} else {
			return actionCode == 8 || actionCode == 32 || actionCode == 28 || actionCode == 59 || actionCode == 51 || actionCode == 41;
		}
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(ZI)V")
	public static void resetToLoginScreen(@OriginalArg(0) boolean fullReset) {
		if (fullReset) {
			if (topLevelInterface != -1) {
				unload(topLevelInterface);
			}
			for (@Pc(18) ComponentPointer ptr = (ComponentPointer) openInterfaces.head(); ptr != null; ptr = (ComponentPointer) openInterfaces.next()) {
				closeInterface(true, ptr);
			}
			topLevelInterface = -1;
			openInterfaces = new HashTable(8);
			reset();
			topLevelInterface = LoginManager.loginScreenId;
			layoutTopLevel(false);
			ScriptRunner.forceRedrawAllRectangles();
			runOnLoadScripts(topLevelInterface);
		}
		MiniMenu.defaultCursorId = -1;
		setCursor(ScriptRunner.scriptCursorId);
		PlayerList.self = new Player();
		PlayerList.self.yFine = 3000;
		PlayerList.self.xFine = 3000;
		if (!GlRenderer.enabled) {
			Flames.load(client.js5Archive8);
			client.setGameState(10);
			return;
		}
		if (Camera.cameraType == 2) {
			Camera.renderX = Camera.lockedTargetX << 7;
			Camera.renderY = Camera.lockedTargetY << 7;
		} else {
			Camera.updateLoginScreenCamera();
		}
		FogManager.setInstantFade();
		LoginManager.setupLoadingScreenRegion();
		client.setGameState(28);
	}

	@OriginalMember(owner = "client!jg", name = "a", descriptor = "(IBIII)V")
	public static void forceRedrawScreen(@OriginalArg(0) int x, @OriginalArg(2) int y, @OriginalArg(3) int h, @OriginalArg(4) int w) {
		for (@Pc(3) int i = 0; i < rectangles; i++) {
			if (x < rectangleX[i] + rectangleWidth[i] && x + w > rectangleX[i] && rectangleY[i] + rectangleHeight[i] > y && rectangleY[i] < h + y) {
				rectangleRedraw[i] = true;
			}
		}
	}

	@OriginalMember(owner = "client!jm", name = "a", descriptor = "(Z)V")
	public static void updateLoginScreen() {
		if (topLevelInterface != -1) {
			updateAnimations(topLevelInterface);
		}
		for (@Pc(15) int i = 0; i < rectangles; i++) {
			if (rectangleDirty[i]) {
				rectangleRedraw[i] = true;
			}
			rectangleDirtySnapshot[i] = rectangleDirty[i];
			rectangleDirty[i] = false;
		}
		Cs1ScriptRunner.gameSceneTooltipX = -1;
		mouseOverInventoryInterface = null;
		currentRenderLoop = client.loop;
		if (GlRenderer.enabled) {
			ScriptRunner.glSceneNeedsRender = true;
		}
		viewportX = -1;
		if (topLevelInterface != -1) {
			rectangles = 0;
			Cs1ScriptRunner.renderTopLevelInterface();
		}
		if (GlRenderer.enabled) {
			GlRaster.resetClipRegion();
		} else {
			SoftwareRaster.resetClip();
		}
		Protocol.sceneDelta = 0;
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(Lclient!be;)Lclient!be;")
	public static Component getDragRenderParent(@OriginalArg(0) Component component) {
		@Pc(4) int depth = getServerActiveProperties(component).getDragDepth();
		if (depth == 0) {
			return null;
		}
		for (@Pc(10) int i = 0; i < depth; i++) {
			component = getComponent(component.overlayer);
			if (component == null) {
				return null;
			}
		}
		return component;
	}

	@OriginalMember(owner = "client!client", name = "c", descriptor = "(Lclient!be;)Z")
	public static boolean isHidden(@OriginalArg(0) Component component) {
		if (Cheat.qaOpTest) {
			if (getServerActiveProperties(component).events != 0) {
				return false;
			}
			if (component.type == 0) {
				return false;
			}
		}
		return component.hidden;
	}

	@OriginalMember(owner = "client!tc", name = "a", descriptor = "(IILclient!be;BIIII)V")
	public static void handleScrollbar(@OriginalArg(0) int mouseY, @OriginalArg(1) int scrollbarH, @OriginalArg(2) Component component, @OriginalArg(4) int mouseX, @OriginalArg(5) int barX, @OriginalArg(6) int barY, @OriginalArg(7) int scrollRange) {
		if (scrollbarDragging) {
			scrollbarDragMargin = 32;
		} else {
			scrollbarDragMargin = 0;
		}
		scrollbarDragging = false;
		@Pc(139) int thumbSize;
		if (Mouse.pressedButton != 0) {
			if (barX <= mouseX && barX + 16 > mouseX && mouseY >= barY && barY + 16 > mouseY) {
				component.scrollY -= 4;
				redraw(component);
			} else if (mouseX >= barX && mouseX < barX + 16 && mouseY >= scrollbarH + barY - 16 && scrollbarH + barY > mouseY) {
				component.scrollY += 4;
				redraw(component);
			} else if (mouseX >= barX - scrollbarDragMargin && mouseX < barX + scrollbarDragMargin + 16 && mouseY >= barY + 16 && scrollbarH + barY - 16 > mouseY) {
				thumbSize = scrollbarH * (scrollbarH - 32) / scrollRange;
				if (thumbSize < 8) {
					thumbSize = 8;
				}
				@Pc(150) int trackRange = scrollbarH - thumbSize - 32;
				@Pc(162) int thumbPos = mouseY - thumbSize / 2 - barY - 16;
				component.scrollY = (scrollRange - scrollbarH) * thumbPos / trackRange;
				redraw(component);
				scrollbarDragging = true;
			}
		}
		if (MouseWheel.wheelRotation == 0) {
			return;
		}
		thumbSize = component.width;
		if (barX - thumbSize <= mouseX && barY <= mouseY && mouseX < barX + 16 && scrollbarH + barY >= mouseY) {
			component.scrollY += MouseWheel.wheelRotation * 45;
			redraw(component);
		}
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(II)V")
	public static void updateAnimations(@OriginalArg(1) int interfaceId) {
		if (load(interfaceId)) {
			updateAnimationsRecursive(-1, components[interfaceId]);
		}
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(II[Lclient!be;)V")
	public static void updateAnimationsRecursive(@OriginalArg(1) int overlayerId, @OriginalArg(2) Component[] children) {
		for (@Pc(7) int i = 0; i < children.length; i++) {
			@Pc(15) Component child = children[i];
			if (child != null && child.overlayer == overlayerId && (!child.if3 || !isHidden(child))) {
				if (child.type == 0) {
					if (!child.if3 && isHidden(child) && child != hoveredComponent) {
						continue;
					}
					updateAnimationsRecursive(child.id, children);
					if (child.createdComponents != null) {
						updateAnimationsRecursive(child.id, child.createdComponents);
					}
					@Pc(73) ComponentPointer ptr = (ComponentPointer) openInterfaces.get(child.id);
					if (ptr != null) {
						updateAnimations(ptr.interfaceId);
					}
				}
				if (child.type == 6) {
					@Pc(105) int seqId;
					if (child.modelSeqId != -1 || child.activeModelSeqId != -1) {
						@Pc(100) boolean active = Cs1ScriptRunner.isTrue(child);
						if (active) {
							seqId = child.activeModelSeqId;
						} else {
							seqId = child.modelSeqId;
						}
						if (seqId != -1) {
							@Pc(118) SeqType seq = SeqTypeList.get(seqId);
							if (seq != null) {
								child.seqCycle += Protocol.sceneDelta;
								while (child.seqCycle > seq.frameDelay[child.seqFrame]) {
									child.seqCycle -= seq.frameDelay[child.seqFrame];
									child.seqFrame++;
									if (seq.frames.length <= child.seqFrame) {
										child.seqFrame -= seq.replayoff;
										if (child.seqFrame < 0 || seq.frames.length <= child.seqFrame) {
											child.seqFrame = 0;
										}
									}
									child.seqNextFrame = child.seqFrame + 1;
									if (seq.frames.length <= child.seqNextFrame) {
										child.seqNextFrame -= seq.replayoff;
										if (child.seqNextFrame < 0 || seq.frames.length <= child.seqNextFrame) {
											child.seqNextFrame = -1;
										}
									}
									redraw(child);
								}
							}
						}
					}
					if (child.modelRotationSpeed != 0 && !child.if3) {
						@Pc(239) int xSpeed = child.modelRotationSpeed >> 16;
						@Pc(243) int xDelta = xSpeed * Protocol.sceneDelta;
						seqId = child.modelRotationSpeed << 16 >> 16;
						child.modelXAngle = xDelta + child.modelXAngle & 0x7FF;
						seqId *= Protocol.sceneDelta;
						child.modelYAngle = child.modelYAngle + seqId & 0x7FF;
						redraw(child);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!gg", name = "c", descriptor = "(II)V")
	public static void setCursor(@OriginalArg(0) int cursorId) {
		if (!Preferences.cursorsEnabled) {
			cursorId = -1;
		}
		if (cursorId == currentCursorId) {
			return;
		}
		if (cursorId != -1) {
			@Pc(24) CursorType cursorType = CursorTypeList.get(cursorId);
			@Pc(28) SoftwareSprite sprite = cursorType.getSprite();
			if (sprite == null) {
				cursorId = -1;
			} else {
				GameShell.signLink.setCursor(sprite.toFullImage(), sprite.innerWidth, GameShell.canvas, new Point(cursorType.hotSpotX, cursorType.hotSpotY), sprite.innerHeight);
				currentCursorId = cursorId;
			}
		}
		if (cursorId == -1 && currentCursorId != -1) {
			GameShell.signLink.setCursor(null, -1, GameShell.canvas, new Point(), -1);
			currentCursorId = -1;
		}
	}
}
