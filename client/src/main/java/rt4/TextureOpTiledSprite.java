package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!kd")
public final class TextureOpTiledSprite extends TextureOpSprite {

	@OriginalMember(owner = "client!kd", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(11) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid && this.loadSprite()) {
			@Pc(24) int[] redChannel = output[0];
			@Pc(32) int srcRowOffset = row % this.height * this.height;
			@Pc(36) int[] greenChannel = output[1];
			@Pc(40) int[] blueChannel = output[2];
			for (@Pc(42) int i = 0; i < Texture.width; i++) {
				@Pc(55) int pixel = this.pixels[srcRowOffset + i % this.width];
				blueChannel[i] = (pixel & 0xFF) << 4;
				greenChannel[i] = pixel >> 4 & 0xFF0;
				redChannel[i] = pixel >> 12 & 0xFF0;
			}
		}
		return output;
	}
}
