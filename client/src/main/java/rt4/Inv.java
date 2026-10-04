package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!qe")
public final class Inv extends Node {

	@OriginalMember(owner = "client!cb", name = "I", descriptor = "[I")
	public static final int[] updatedInventories = new int[32];
	@OriginalMember(owner = "client!kl", name = "u", descriptor = "Lclient!na;")
	public static final JagString COL_WHITE = JagString.parse("<col=ffffff>");
	@OriginalMember(owner = "client!ol", name = "Y", descriptor = "Lclient!na;")
	public static final JagString COL_GREEN = JagString.parse("<col=00ff80>");
	@OriginalMember(owner = "client!ib", name = "g", descriptor = "Lclient!na;")
	public static final JagString COL_YELLOW = JagString.parse("<col=ffff00>");
	@OriginalMember(owner = "client!jj", name = "m", descriptor = "Lclient!na;")
	public static final JagString COL_END = JagString.parse("<)4col>");
	@OriginalMember(owner = "client!ii", name = "c", descriptor = "I")
	public static int updatedInventoriesWriterIndex = 0;
	@OriginalMember(owner = "client!bj", name = "v", descriptor = "Lclient!sc;")
	public static HashTable objectContainerCache = new HashTable(32);
	@OriginalMember(owner = "client!qe", name = "p", descriptor = "[I")
	public int[] objectIds = new int[]{-1};

	@OriginalMember(owner = "client!qe", name = "u", descriptor = "[I")
	public int[] objectStackSizes = new int[]{0};

	@OriginalMember(owner = "client!ba", name = "a", descriptor = "(IB)I")
	public static int getFreeSpace(@OriginalArg(0) int invId) {
		if (invId < 0) {
			return 0;
		}
		@Pc(17) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			return InvTypeList.get(invId).size;
		}
		@Pc(31) int freeSpaces = 0;
		for (@Pc(33) int id = 0; id < inv.objectIds.length; id++) {
			if (inv.objectIds[id] == -1) {
				freeSpaces++;
			}
		}
		return freeSpaces + InvTypeList.get(invId).size - inv.objectIds.length;
	}

	@OriginalMember(owner = "client!od", name = "a", descriptor = "(IZII)I")
	public static int getTotalParam(@OriginalArg(1) boolean multiplyByStack, @OriginalArg(2) int invId, @OriginalArg(3) int paramId) {
		@Pc(19) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			return 0;
		}
		@Pc(27) int total = 0;
		for (@Pc(29) int i = 0; i < inv.objectIds.length; i++) {
			if (inv.objectIds[i] >= 0 && ObjTypeList.capacity > inv.objectIds[i]) {
				@Pc(56) ObjType objType = ObjTypeList.get(inv.objectIds[i]);
				if (objType.params != null) {
					@Pc(68) IntNode param = (IntNode) objType.params.get(paramId);
					if (param != null) {
						if (multiplyByStack) {
							total += inv.objectStackSizes[i] * param.value;
						} else {
							total += param.value;
						}
					}
				}
			}
		}
		return total;
	}

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(BII)I")
	public static int getSlotTotal(@OriginalArg(1) int invId, @OriginalArg(2) int objId) {
		@Pc(8) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			return 0;
		} else if (objId == -1) {
			return 0;
		} else {
			@Pc(25) int total = 0;
			for (@Pc(27) int i = 0; i < inv.objectStackSizes.length; i++) {
				if (objId == inv.objectIds[i]) {
					total += inv.objectStackSizes[i];
				}
			}
			return total;
		}
	}

	@OriginalMember(owner = "client!bm", name = "a", descriptor = "(III)I")
	public static int getItemCount(@OriginalArg(1) int invId, @OriginalArg(2) int slot) {
		@Pc(10) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			return 0;
		} else if (slot >= 0 && slot < inv.objectStackSizes.length) {
			return inv.objectStackSizes[slot];
		} else {
			return 0;
		}
	}

	@OriginalMember(owner = "client!be", name = "a", descriptor = "(III)I")
	public static int getItemType(@OriginalArg(0) int invId, @OriginalArg(2) int slot) {
		@Pc(10) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			return -1;
		} else if (slot >= 0 && slot < inv.objectIds.length) {
			return inv.objectIds[slot];
		} else {
			return -1;
		}
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(II)V")
	public static void delete(@OriginalArg(0) int invId) {
		@Pc(14) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv != null) {
			inv.unlink();
		}
	}

	@OriginalMember(owner = "client!hn", name = "f", descriptor = "(B)V")
	public static void clear() {
		objectContainerCache = new HashTable(32);
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(IIIIB)V")
	public static void updateContainer(@OriginalArg(0) int objId, @OriginalArg(1) int slot, @OriginalArg(2) int count, @OriginalArg(3) int invId) {
		@Pc(12) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv == null) {
			inv = new Inv();
			objectContainerCache.put(inv, invId);
		}
		if (slot >= inv.objectIds.length) {
			@Pc(39) int[] newIds = new int[slot + 1];
			@Pc(44) int[] newCounts = new int[slot + 1];
			@Pc(46) int i;
			for (i = 0; i < inv.objectIds.length; i++) {
				newIds[i] = inv.objectIds[i];
				newCounts[i] = inv.objectStackSizes[i];
			}
			for (i = inv.objectIds.length; i < slot; i++) {
				newIds[i] = -1;
				newCounts[i] = 0;
			}
			inv.objectIds = newIds;
			inv.objectStackSizes = newCounts;
		}
		inv.objectIds[slot] = objId;
		inv.objectStackSizes[slot] = count;
	}

	@OriginalMember(owner = "client!pf", name = "a", descriptor = "(IIZIII)Lclient!qf;")
	public static Sprite getObjectSprite(@OriginalArg(0) int selected, @OriginalArg(1) int id, @OriginalArg(2) boolean drawText, @OriginalArg(3) int count, @OriginalArg(4) int shadow) {
		@Pc(27) int key = (drawText ? 65536 : 0) + id + (selected << 17) + (shadow << 19);
		@Pc(37) long uid = (long) key * 3849834839L + (long) count * 3147483667L;
		@Pc(43) Sprite sprite = (Sprite) ObjTypeList.objectSpriteCache.get(uid);
		if (sprite != null) {
			return sprite;
		}

		Rasteriser.textureHasTransparency = false;
		sprite = renderObjectSprite(shadow, false, id, drawText, selected, count, false);
		if (sprite != null && !Rasteriser.textureHasTransparency) {
			ObjTypeList.objectSpriteCache.put(sprite, uid);
		}

		return sprite;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IBZIZIIZ)Lclient!qf;")
	public static Sprite renderObjectSprite(@OriginalArg(0) int shadow, @OriginalArg(2) boolean linked, @OriginalArg(3) int id, @OriginalArg(4) boolean drawText, @OriginalArg(5) int state, @OriginalArg(6) int stack, @OriginalArg(7) boolean cert) {
		@Pc(5) ObjType objType = ObjTypeList.get(id);
		if (stack > 1 && objType.countobj != null) {
			@Pc(15) int stackId = -1;
			for (@Pc(17) int i = 0; i < 10; i++) {
				if (stack >= objType.countco[i] && objType.countco[i] != 0) {
					stackId = objType.countobj[i];
				}
			}
			if (stackId != -1) {
				objType = ObjTypeList.get(stackId);
			}
		}

		@Pc(60) SoftwareModel model = objType.getInvModel();
		if (model == null) {
			return null;
		}

		@Pc(71) SoftwareSprite linkedSprite = null;
		if (objType.certtemplate != -1) {
			linkedSprite = (SoftwareSprite) renderObjectSprite(0, true, objType.certlink, false, 1, 10, true);
			if (linkedSprite == null) {
				return null;
			}
		} else if (objType.lentTemplate != -1) {
			linkedSprite = (SoftwareSprite) renderObjectSprite(shadow, true, objType.lentLink, false, state, stack, false);
			if (linkedSprite == null) {
				return null;
			}
		}

		@Pc(118) int[] pixels = SoftwareRaster.pixels;
		@Pc(120) int width = SoftwareRaster.width;
		@Pc(122) int height = SoftwareRaster.height;
		@Pc(125) int[] clip = new int[4];
		SoftwareRaster.saveClip(clip);

		final int canvasWidth = 36;
		final int canvasHeight = 32;

		@Pc(133) SoftwareSprite canvas = new SoftwareSprite(canvasWidth, canvasHeight);
		SoftwareRaster.setSize(canvas.pixels, canvasWidth, canvasHeight);
		Rasteriser.prepare();
		Rasteriser.setBounds(16, 16); // canvasWidth / 2, canvasHeight / 2);
		Rasteriser.jagged = false;

		@Pc(145) int zoom = objType.zoom2d; // / 4;
		if (cert) {
			zoom = (int) ((double) zoom * 1.5D);
		} else if (state == 2) {
			zoom = (int) ((double) zoom * 1.04D);
		}

		@Pc(176) int pitchcos = MathUtils.cos[objType.xAngle2D] * zoom >> 16;
		@Pc(185) int pitchsin = MathUtils.sin[objType.xAngle2D] * zoom >> 16;
		model.setCamera(objType.yAngle2D, objType.zAngle2D, objType.xAngle2D, objType.xOffset2D, pitchsin + objType.yOffset2D - model.getMinY() / 2, objType.yOffset2D + pitchcos, -1L);

		if (state >= 1) {
			canvas.drawOutline(1);
			if (state >= 2) {
				canvas.drawOutline(16777215);
			}
			SoftwareRaster.setSize(canvas.pixels, canvasWidth, canvasHeight);
		}

		if (shadow != 0) {
			canvas.drawShadow(shadow);
		}

		if (objType.certtemplate != -1) {
			linkedSprite.render(0, 0);
		} else if (objType.lentTemplate != -1) {
			SoftwareRaster.setSize(linkedSprite.pixels, canvasWidth, canvasHeight);
			canvas.render(0, 0);
			canvas = linkedSprite;
		}

		if (drawText && (objType.stackable == 1 || stack != 1) && stack != -1) {
			ObjTypeList.font.renderLeft(formatObjAmount(stack), 0, 9, 16776960, 1);
		}

		SoftwareRaster.setSize(pixels, width, height);
		SoftwareRaster.restoreClip(clip);
		Rasteriser.prepare();
		Rasteriser.jagged = true;
		return GlRenderer.enabled && !linked ? new GlSprite(canvas) : canvas;
	}

	@OriginalMember(owner = "client!eb", name = "b", descriptor = "(II)Lclient!na;")
	public static JagString formatObjAmount(@OriginalArg(1) int amount) {
		if (amount < 100000) {
			return JagString.concatenate(new JagString[]{COL_YELLOW, JagString.parseInt(amount), COL_END});
		} else if (amount >= 10000000) {
			return JagString.concatenate(new JagString[]{COL_GREEN, JagString.parseInt(amount / 1000000), LocalizedText.MILLION, COL_END});
		} else {
			return JagString.concatenate(new JagString[]{COL_WHITE, JagString.parseInt(amount / 1000), LocalizedText.THOUSAND, COL_END});
		}
	}

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "(BI)V")
	public static void clearContainer(@OriginalArg(1) int invId) {
		@Pc(8) Inv inv = (Inv) objectContainerCache.get(invId);
		if (inv != null) {
			for (@Pc(24) int i = 0; i < inv.objectIds.length; i++) {
				inv.objectIds[i] = -1;
				inv.objectStackSizes[i] = 0;
			}
		}
	}
}
