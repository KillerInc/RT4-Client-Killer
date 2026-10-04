package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ui")
public final class TextureOpTexture extends TextureOp {

	@OriginalMember(owner = "client!ui", name = "U", descriptor = "[I")
	private int[] pixels;

	@OriginalMember(owner = "client!ui", name = "gb", descriptor = "I")
	private int width;

	@OriginalMember(owner = "client!ui", name = "ib", descriptor = "I")
	private int height;

	@OriginalMember(owner = "client!ui", name = "lb", descriptor = "I")
	private int textureId = -1;

	@OriginalMember(owner = "client!ui", name = "<init>", descriptor = "()V")
	public TextureOpTexture() {
		super(0, false);
	}

	@OriginalMember(owner = "client!ui", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(17) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid && this.loadTexture()) {
			@Pc(42) int srcRowOffset = (this.height == Texture.height ? row : this.height * row / Texture.height) * this.width;
			@Pc(46) int[] redChannel = output[0];
			@Pc(50) int[] greenChannel = output[1];
			@Pc(54) int[] blueChannel = output[2];
			@Pc(64) int i;
			@Pc(73) int pixel;
			if (Texture.width == this.width) {
				for (i = 0; i < Texture.width; i++) {
					pixel = this.pixels[srcRowOffset++];
					blueChannel[i] = (pixel & 0xFF) << 4;
					greenChannel[i] = pixel >> 4 & 0xFF0;
					redChannel[i] = pixel >> 12 & 0xFF0;
				}
			} else {
				for (i = 0; i < Texture.width; i++) {
					pixel = this.width * i / Texture.width;
					@Pc(122) int color = this.pixels[srcRowOffset + pixel];
					blueChannel[i] = (color & 0xFF) << 4;
					greenChannel[i] = color >> 4 & 0xFF0;
					redChannel[i] = color >> 12 & 0xFF0;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!ui", name = "a", descriptor = "(Z)Z")
	private boolean loadTexture() {
		if (this.pixels != null) {
			return true;
		} else if (this.textureId < 0) {
			return false;
		} else {
			@Pc(22) int prevWidth = Texture.width;
			@Pc(24) int prevHeight = Texture.height;
			@Pc(34) int size = Texture.provider.isLowDetail(this.textureId) ? 64 : 128;
			this.pixels = Texture.provider.getPixels(this.textureId);
			this.height = size;
			this.width = size;
			Texture.setSize(prevHeight, prevWidth);
			return this.pixels != null;
		}
	}

	@OriginalMember(owner = "client!ui", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.textureId = buffer.g2();
		}
	}

	@OriginalMember(owner = "client!ui", name = "e", descriptor = "(B)V")
	@Override
	public final void clearImageCache() {
		super.clearImageCache();
		this.pixels = null;
	}

	@OriginalMember(owner = "client!ui", name = "d", descriptor = "(B)I")
	@Override
	public final int getRequiredTextureId() {
		return this.textureId;
	}
}
