package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!j")
public abstract class TextureOp extends Node {

	@OriginalMember(owner = "client!kc", name = "p", descriptor = "[I")
	public static int[] COSINE;
	@OriginalMember(owner = "client!je", name = "eb", descriptor = "[I")
	public static int[] SINE;
	@OriginalMember(owner = "client!j", name = "t", descriptor = "Lclient!nd;")
	protected MonochromeImageCache colorImageCache;

	@OriginalMember(owner = "client!j", name = "G", descriptor = "I")
	public int cacheHeight;

	@OriginalMember(owner = "client!j", name = "H", descriptor = "Lclient!pf;")
	protected ColorImageCache monochromeImageCache;

	@OriginalMember(owner = "client!j", name = "u", descriptor = "[Lclient!j;")
	public final TextureOp[] inputs;

	@OriginalMember(owner = "client!j", name = "p", descriptor = "Z")
	public boolean monochrome;

	@OriginalMember(owner = "client!j", name = "<init>", descriptor = "(IZ)V")
	protected TextureOp(@OriginalArg(0) int inputCount, @OriginalArg(1) boolean monochrome) {
		this.inputs = new TextureOp[inputCount];
		this.monochrome = monochrome;
	}

	@OriginalMember(owner = "client!al", name = "b", descriptor = "(B)V")
	public static void createTrigonometryTables() {
		if (SINE != null && COSINE != null) {
			return;
		}
		SINE = new int[256];
		COSINE = new int[256];
		for (@Pc(26) int i = 0; i < 256; i++) {
			@Pc(36) double radians = (double) i / 255.0D * 6.283185307179586D;
			SINE[i] = (int) (Math.sin(radians) * 4096.0D);
			COSINE[i] = (int) (Math.cos(radians) * 4096.0D);
		}
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(III)[I")
	protected final int[] getChildMonochromeOutput(@OriginalArg(0) int inputIndex, @OriginalArg(1) int row) {
		return this.inputs[inputIndex].monochrome ? this.inputs[inputIndex].getMonochromeOutput(row) : this.inputs[inputIndex].getColorOutput(row)[0];
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(IB)[I")
	public int[] getMonochromeOutput(@OriginalArg(0) int row) {
		throw new IllegalStateException("This operation does not have a monochrome output");
	}

	@OriginalMember(owner = "client!j", name = "d", descriptor = "(B)I")
	public int getRequiredTextureId() {
		return -1;
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(ILclient!wa;Z)V")
	public void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
	}

	@OriginalMember(owner = "client!j", name = "e", descriptor = "(I)V")
	public void postDecode() {
	}

	@OriginalMember(owner = "client!j", name = "f", descriptor = "(I)I")
	public int getRequiredSpriteId() {
		return -1;
	}

	@OriginalMember(owner = "client!j", name = "b", descriptor = "(III)V")
	public final void allocateImageCache(@OriginalArg(0) int width, @OriginalArg(1) int height) {
		@Pc(15) int effectiveHeight = this.cacheHeight == 255 ? width : this.cacheHeight;
		if (this.monochrome) {
			this.monochromeImageCache = new ColorImageCache(effectiveHeight, width, height);
		} else {
			this.colorImageCache = new MonochromeImageCache(effectiveHeight, width, height);
		}
	}

	@OriginalMember(owner = "client!j", name = "e", descriptor = "(B)V")
	public void clearImageCache() {
		if (this.monochrome) {
			this.monochromeImageCache.clear();
			this.monochromeImageCache = null;
		} else {
			this.colorImageCache.clear();
			this.colorImageCache = null;
		}
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(IIB)[[I")
	protected final int[][] getChildColorOutput(@OriginalArg(0) int row, @OriginalArg(1) int inputIndex) {
		if (this.inputs[inputIndex].monochrome) {
			@Pc(32) int[] mono = this.inputs[inputIndex].getMonochromeOutput(row);
			return new int[][]{mono, mono, mono};
		} else {
			return this.inputs[inputIndex].getColorOutput(row);
		}
	}

	@OriginalMember(owner = "client!j", name = "b", descriptor = "(II)[[I")
	public int[][] getColorOutput(@OriginalArg(1) int row) {
		throw new IllegalStateException("This operation does not have a colour output");
	}
}
