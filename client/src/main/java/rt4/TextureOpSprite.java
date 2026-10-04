package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nh")
public class TextureOpSprite extends TextureOp {

	@OriginalMember(owner = "client!nh", name = "U", descriptor = "I")
	protected int width;

	@OriginalMember(owner = "client!nh", name = "X", descriptor = "I")
	protected int height;

	@OriginalMember(owner = "client!nh", name = "Y", descriptor = "[I")
	protected int[] pixels;

	@OriginalMember(owner = "client!nh", name = "P", descriptor = "I")
	private int spriteId = -1;

	@OriginalMember(owner = "client!nh", name = "<init>", descriptor = "()V")
	public TextureOpSprite() {
		super(0, false);
	}

	@OriginalMember(owner = "client!nh", name = "h", descriptor = "(I)Z")
	protected final boolean loadSprite() {
		if (this.pixels != null) {
			return true;
		} else if (this.spriteId < 0) {
			return false;
		} else {
			@Pc(43) SoftwareSprite sprite = Texture.spriteGroupId < 0 ? SpriteLoader.loadSoftwareSpriteAutoDetect(Texture.spritesArchive, this.spriteId) : SpriteLoader.loadSoftwareSprite(this.spriteId, Texture.spritesArchive, Texture.spriteGroupId);
			sprite.trim();
			this.height = sprite.height;
			this.width = sprite.width;
			this.pixels = sprite.pixels;
			return true;
		}
	}

	@OriginalMember(owner = "client!nh", name = "f", descriptor = "(I)I")
	@Override
	public final int getRequiredSpriteId() {
		return this.spriteId;
	}

	@OriginalMember(owner = "client!nh", name = "b", descriptor = "(II)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(18) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid && this.loadSprite()) {
			@Pc(31) int[] redChannel = output[0];
			@Pc(35) int[] greenChannel = output[1];
			@Pc(39) int[] blueChannel = output[2];
			@Pc(59) int srcRowOffset = (this.height == Texture.height ? row : this.height * row / Texture.height) * this.width;
			@Pc(65) int i;
			@Pc(78) int pixel;
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
					@Pc(127) int color = this.pixels[srcRowOffset + pixel];
					blueChannel[i] = (color & 0xFF) << 4;
					greenChannel[i] = color >> 4 & 0xFF0;
					redChannel[i] = color >> 12 & 0xFF0;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.spriteId = buffer.g2();
		}
	}

	@OriginalMember(owner = "client!nh", name = "e", descriptor = "(B)V")
	@Override
	public final void clearImageCache() {
		super.clearImageCache();
		this.pixels = null;
	}
}
