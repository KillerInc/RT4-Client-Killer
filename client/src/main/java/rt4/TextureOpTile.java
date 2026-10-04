package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!gg")
public final class TextureOpTile extends TextureOp {

	@OriginalMember(owner = "client!gg", name = "V", descriptor = "I")
	private int horizontalTiles = 4;

	@OriginalMember(owner = "client!gg", name = "R", descriptor = "I")
	private int verticalTiles = 4;

	@OriginalMember(owner = "client!gg", name = "<init>", descriptor = "()V")
	public TextureOpTile() {
		super(1, false);
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.horizontalTiles = buf.g1();
		} else if (opcode == 1) {
			this.verticalTiles = buf.g1();
		}
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(28) int tileWidth = Texture.width / this.horizontalTiles;
			@Pc(33) int tileHeight = Texture.height / this.verticalTiles;
			@Pc(44) int[] input;
			@Pc(50) int i;
			if (tileHeight <= 0) {
				input = this.getChildMonochromeOutput(0, 0);
			} else {
				i = row % tileHeight;
				input = this.getChildMonochromeOutput(0, Texture.height * i / tileHeight);
			}
			for (i = 0; i < Texture.width; i++) {
				if (tileWidth <= 0) {
					output[i] = input[0];
				} else {
					@Pc(80) int tileCol = i % tileWidth;
					output[i] = input[Texture.width * tileCol / tileWidth];
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!gg", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(18) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(28) int tileWidth = Texture.width / this.horizontalTiles;
			@Pc(33) int tileHeight = Texture.height / this.verticalTiles;
			@Pc(49) int[][] input;
			if (tileHeight > 0) {
				@Pc(39) int tileRow = row % tileHeight;
				input = this.getChildColorOutput(tileRow * Texture.height / tileHeight, 0);
			} else {
				input = this.getChildColorOutput(0, 0);
			}
			@Pc(61) int[] srcR = input[0];
			@Pc(65) int[] srcB = input[2];
			@Pc(69) int[] destR = output[0];
			@Pc(73) int[] srcG = input[1];
			@Pc(77) int[] destG = output[1];
			@Pc(81) int[] destB = output[2];
			for (@Pc(83) int i = 0; i < Texture.width; i++) {
				@Pc(94) int srcIdx;
				if (tileWidth <= 0) {
					srcIdx = 0;
				} else {
					@Pc(100) int tileCol = i % tileWidth;
					srcIdx = tileCol * Texture.width / tileWidth;
				}
				destR[i] = srcR[srcIdx];
				destG[i] = srcG[srcIdx];
				destB[i] = srcB[srcIdx];
			}
		}
		return output;
	}
}
