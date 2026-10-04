package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!vc")
public final class TextureOpLineScatter extends TextureOp {

	@OriginalMember(owner = "client!vc", name = "P", descriptor = "I")
	private int baseAngle = 0;

	@OriginalMember(owner = "client!vc", name = "U", descriptor = "I")
	private int lineCount = 2000;

	@OriginalMember(owner = "client!vc", name = "Z", descriptor = "I")
	private int lineLength = 16;

	@OriginalMember(owner = "client!vc", name = "Y", descriptor = "I")
	private int angleSpread = 4096;

	@OriginalMember(owner = "client!vc", name = "cb", descriptor = "I")
	private int seed = 0;

	@OriginalMember(owner = "client!vc", name = "<init>", descriptor = "()V")
	public TextureOpLineScatter() {
		super(0, true);
	}

	@OriginalMember(owner = "client!vc", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		TextureOp.createTrigonometryTables();
	}

	@OriginalMember(owner = "client!vc", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.seed = buf.g1();
		} else if (opcode == 1) {
			this.lineCount = buf.g2();
		} else if (opcode == 2) {
			this.lineLength = buf.g1();
		} else if (opcode == 3) {
			this.baseAngle = buf.g2();
		} else if (opcode == 4) {
			this.angleSpread = buf.g2();
		}
	}

	@OriginalMember(owner = "client!vc", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(28) int halfSpread = this.angleSpread >> 1;
			@Pc(33) int[][] allRows = this.monochromeImageCache.getAll();
			@Pc(40) Random rng = new Random(this.seed);
			for (@Pc(42) int i = 0; i < this.lineCount; i++) {
				@Pc(64) int angle = this.angleSpread > 0 ? this.baseAngle + RandomUtils.nextInt(this.angleSpread, rng) - halfSpread : this.baseAngle;
				@Pc(69) int x0 = RandomUtils.nextInt(Texture.width, rng);
				@Pc(75) int trigIdx = angle >> 4 & 0xFF;
				@Pc(80) int y0 = RandomUtils.nextInt(Texture.height, rng);
				@Pc(92) int x1 = x0 + (this.lineLength * TextureOp.COSINE[trigIdx] >> 12);
				@Pc(103) int y1 = y0 + (TextureOp.SINE[trigIdx] * this.lineLength >> 12);
				@Pc(107) int dx = x1 - x0;
				@Pc(112) int dy = y1 - y0;
				if (dx != 0 || dy != 0) {
					if (dx < 0) {
						dx = -dx;
					}
					if (dy < 0) {
						dy = -dy;
					}
					@Pc(146) boolean steep = dy > dx;
					@Pc(150) int tmp;
					@Pc(152) int tmp2;
					if (steep) {
						tmp = x0;
						tmp2 = x1;
						x1 = y1;
						y1 = tmp2;
						x0 = y0;
						y0 = tmp;
					}
					if (x0 > x1) {
						tmp = x0;
						tmp2 = y0;
						x0 = x1;
						y0 = y1;
						y1 = tmp2;
						x1 = tmp;
					}
					tmp2 = x1 - x0;
					@Pc(190) int deltaY = y1 - y0;
					tmp = y0;
					if (deltaY < 0) {
						deltaY = -deltaY;
					}
					@Pc(206) int error = -tmp2 / 2;
					@Pc(216) int baseIntensity = 1024 - (RandomUtils.nextInt(4096, rng) >> 2);
					@Pc(227) int yStep = y1 <= y0 ? -1 : 1;
					@Pc(231) int intensityStep = 2048 / tmp2;
					for (@Pc(233) int x = x0; x < x1; x++) {
						error += deltaY;
						@Pc(251) int intensity = intensityStep * (x - x0) + baseIntensity + 1024;
						@Pc(255) int maskedY = tmp & Texture.heightMask;
						if (error > 0) {
							error += -tmp2;
							tmp += yStep;
						}
						@Pc(271) int maskedX = Texture.widthMask & x;
						if (steep) {
							allRows[maskedY][maskedX] = intensity;
						} else {
							allRows[maskedX][maskedY] = intensity;
						}
					}
				}
			}
		}
		return output;
	}
}
