package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nm")
public final class TextureOpBoxBlur extends TextureOp {

	@OriginalMember(owner = "client!nm", name = "V", descriptor = "I")
	private int radiusX = 1;

	@OriginalMember(owner = "client!nm", name = "Z", descriptor = "I")
	private int radiusY = 1;

	@OriginalMember(owner = "client!nm", name = "<init>", descriptor = "()V")
	public TextureOpBoxBlur() {
		super(1, false);
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(31) int kernelH = this.radiusY + this.radiusY + 1;
			@Pc(35) int invKernelH = 65536 / kernelH;
			@Pc(43) int kernelW = this.radiusX + this.radiusX + 1;
			@Pc(47) int invKernelW = 65536 / kernelW;
			@Pc(50) int[][] blurredRows = new int[kernelH][];
			@Pc(56) int y;
			for (y = row - this.radiusY; y <= row + this.radiusY; y++) {
				@Pc(75) int[] srcRow = this.getChildMonochromeOutput(0, y & Texture.heightMask);
				@Pc(78) int[] destRow = new int[Texture.width];
				@Pc(80) int acc = 0;
				@Pc(84) int x;
				for (x = -this.radiusX; x <= this.radiusX; x++) {
					acc += srcRow[x & Texture.widthMask];
				}
				x = 0;
				while (Texture.width > x) {
					destRow[x] = invKernelW * acc >> 16;
					acc -= srcRow[Texture.widthMask & x - this.radiusX];
					x++;
					acc += srcRow[Texture.widthMask & this.radiusX + x];
				}
				blurredRows[this.radiusY + y - row] = destRow;
			}
			for (y = 0; y < Texture.width; y++) {
				@Pc(169) int sum = 0;
				for (@Pc(171) int j = 0; j < kernelH; j++) {
					sum += blurredRows[j][y];
				}
				output[y] = invKernelH * sum >> 16;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!nm", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(13) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(30) int kernelW = this.radiusX + this.radiusX + 1;
			@Pc(34) int invKernelW = 65536 / kernelW;
			@Pc(42) int kernelH = this.radiusY + this.radiusY + 1;
			@Pc(46) int invKernelH = 65536 / kernelH;
			@Pc(49) int[][][] blurredRows = new int[kernelH][][];
			@Pc(70) int accR;
			@Pc(72) int accG;
			@Pc(78) int accB;
			for (@Pc(54) int y = row - this.radiusY; y <= this.radiusY + row; y++) {
				@Pc(68) int[][] srcRow = this.getChildColorOutput(Texture.heightMask & y, 0);
				accR = 0;
				accG = 0;
				@Pc(76) int[][] destRow = new int[3][Texture.width];
				accB = 0;
				@Pc(82) int[] srcR = srcRow[0];
				@Pc(86) int[] srcG = srcRow[1];
				@Pc(90) int[] srcB = srcRow[2];
				for (@Pc(94) int k = -this.radiusX; k <= this.radiusX; k++) {
					@Pc(102) int idx = k & Texture.widthMask;
					accG += srcG[idx];
					accR += srcR[idx];
					accB += srcB[idx];
				}
				@Pc(127) int[] rowB = destRow[2];
				@Pc(131) int[] rowR = destRow[0];
				@Pc(135) int[] rowG = destRow[1];
				@Pc(137) int x = 0;
				while (Texture.width > x) {
					rowR[x] = accR * invKernelW >> 16;
					rowG[x] = accG * invKernelW >> 16;
					rowB[x] = invKernelW * accB >> 16;
					@Pc(172) int trailIdx = Texture.widthMask & x - this.radiusX;
					accB -= srcB[trailIdx];
					x++;
					accR -= srcR[trailIdx];
					accG -= srcG[trailIdx];
					@Pc(198) int leadIdx = this.radiusX + x & Texture.widthMask;
					accB += srcB[leadIdx];
					accG += srcG[leadIdx];
					accR += srcR[leadIdx];
				}
				blurredRows[this.radiusY + y - row] = destRow;
			}
			@Pc(235) int[] outR = output[0];
			@Pc(239) int[] outG = output[1];
			@Pc(243) int[] outB = output[2];
			for (accR = 0; accR < Texture.width; accR++) {
				accB = 0;
				accG = 0;
				@Pc(258) int sumB = 0;
				for (@Pc(260) int j = 0; j < kernelH; j++) {
					@Pc(271) int[][] blurRow = blurredRows[j];
					sumB += blurRow[2][accR];
					accB += blurRow[1][accR];
					accG += blurRow[0][accR];
				}
				outR[accR] = invKernelH * accG >> 16;
				outG[accR] = invKernelH * accB >> 16;
				outB[accR] = sumB * invKernelH >> 16;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!nm", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.radiusX = buf.g1();
		} else if (opcode == 1) {
			this.radiusY = buf.g1();
		} else if (opcode == 2) {
			this.monochrome = buf.g1() == 1;
		}
	}
}
