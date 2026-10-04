package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!f")
public final class TextureOpSquareGrid extends TextureOp {

	@OriginalMember(owner = "client!f", name = "R", descriptor = "I")
	private int borderSize = 204;

	@OriginalMember(owner = "client!f", name = "Y", descriptor = "I")
	private int repeatY = 1;

	@OriginalMember(owner = "client!f", name = "Q", descriptor = "I")
	private int repeatX = 1;

	@OriginalMember(owner = "client!f", name = "<init>", descriptor = "()V")
	public TextureOpSquareGrid() {
		super(0, true);
	}

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.repeatX = buffer.g1();
		} else if (opcode == 1) {
			this.repeatY = buffer.g1();
		} else if (opcode == 2) {
			this.borderSize = buffer.g2();
		}
	}

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			for (@Pc(25) int i = 0; i < Texture.width; i++) {
				@Pc(32) int colFraction = Texture.widthFractions[i];
				@Pc(36) int rowFraction = Texture.heightFractions[row];
				@Pc(43) int cellX = this.repeatX * colFraction >> 12;
				@Pc(50) int cellY = rowFraction * this.repeatY >> 12;
				@Pc(60) int fracX = this.repeatX * (colFraction % (4096 / this.repeatX));
				@Pc(70) int fracY = rowFraction % (4096 / this.repeatY) * this.repeatY;
				if (this.borderSize > fracY) {
					for (cellX -= cellY; cellX < 0; cellX += 4) {
					}
					while (cellX > 3) {
						cellX -= 4;
					}
					if (cellX != 1) {
						output[i] = 0;
						continue;
					}
					if (this.borderSize > fracX) {
						output[i] = 0;
						continue;
					}
				}
				if (fracX < this.borderSize) {
					for (cellX -= cellY; cellX < 0; cellX += 4) {
					}
					while (cellX > 3) {
						cellX -= 4;
					}
					if (cellX > 0) {
						output[i] = 0;
						continue;
					}
				}
				output[i] = 4096;
			}
		}
		return output;
	}
}
