package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!gm")
public final class TextureOpMandelbrot extends TextureOp {

	@OriginalMember(owner = "client!gm", name = "Z", descriptor = "I")
	private int zoom = 1365;

	@OriginalMember(owner = "client!gm", name = "ab", descriptor = "I")
	private int offsetY = 0;

	@OriginalMember(owner = "client!gm", name = "V", descriptor = "I")
	private int offsetX = 0;

	@OriginalMember(owner = "client!gm", name = "Y", descriptor = "I")
	private int maxIterations = 20;

	@OriginalMember(owner = "client!gm", name = "<init>", descriptor = "()V")
	public TextureOpMandelbrot() {
		super(0, true);
	}

	@OriginalMember(owner = "client!gm", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.zoom = buffer.g2();
		} else if (opcode == 1) {
			this.maxIterations = buffer.g2();
		} else if (opcode == 2) {
			this.offsetX = buffer.g2();
		} else if (opcode == 3) {
			this.offsetY = buffer.g2();
		}
	}

	@OriginalMember(owner = "client!gm", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			for (@Pc(26) int i = 0; i < Texture.width; i++) {
				@Pc(45) int cy = this.offsetY + (Texture.heightFractions[row] << 12) / this.zoom;
				@Pc(57) int cx = this.offsetX + (Texture.widthFractions[i] << 12) / this.zoom;
				@Pc(61) int zx = cx;
				@Pc(65) int zy = cy;
				@Pc(67) int iteration = 0;
				@Pc(73) int zxSq = cx * cx >> 12;
				@Pc(79) int zySq = cy * cy >> 12;
				while (zxSq + zySq < 16384 && this.maxIterations > iteration) {
					zy = (zx * zy >> 12) * 2 + cy;
					iteration++;
					zx = zxSq + cx - zySq;
					zySq = zy * zy >> 12;
					zxSq = zx * zx >> 12;
				}
				output[i] = iteration >= this.maxIterations - 1 ? 0 : (iteration << 12) / this.maxIterations;
			}
		}
		return output;
	}
}
