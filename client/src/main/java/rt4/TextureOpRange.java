package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fh")
public final class TextureOpRange extends TextureOp {

	@OriginalMember(owner = "client!fh", name = "jb", descriptor = "I")
	private int rangeMin = 1024;

	@OriginalMember(owner = "client!fh", name = "db", descriptor = "I")
	private int rangeSpan = 2048;

	@OriginalMember(owner = "client!fh", name = "eb", descriptor = "I")
	private int rangeMax = 3072;

	@OriginalMember(owner = "client!fh", name = "<init>", descriptor = "()V")
	public TextureOpRange() {
		super(1, false);
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buffer) {
		if (opcode == 0) {
			this.rangeMin = buffer.g2();
		} else if (opcode == 1) {
			this.rangeMax = buffer.g2();
		} else if (opcode == 2) {
			this.monochrome = buffer.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(30) int[] childOutput = this.getChildMonochromeOutput(0, row);
			for (@Pc(32) int i = 0; i < Texture.width; i++) {
				output[i] = this.rangeMin + (childOutput[i] * this.rangeSpan >> 12);
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!fh", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(7) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(17) int[][] childOutput = this.getChildColorOutput(row, 0);
			@Pc(21) int[] srcGreen = childOutput[1];
			@Pc(25) int[] srcBlue = childOutput[2];
			@Pc(29) int[] srcRed = childOutput[0];
			@Pc(33) int[] dstRed = output[0];
			@Pc(37) int[] dstGreen = output[1];
			@Pc(41) int[] dstBlue = output[2];
			for (@Pc(43) int i = 0; i < Texture.width; i++) {
				dstRed[i] = this.rangeMin + (this.rangeSpan * srcRed[i] >> 12);
				dstGreen[i] = (this.rangeSpan * srcGreen[i] >> 12) + this.rangeMin;
				dstBlue[i] = this.rangeMin + (this.rangeSpan * srcBlue[i] >> 12);
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!fh", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.rangeSpan = this.rangeMax - this.rangeMin;
	}
}
