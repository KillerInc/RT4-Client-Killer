package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!pk")
public final class TextureOpBumpMap extends TextureOp {

	@OriginalMember(owner = "client!pk", name = "U", descriptor = "[I")
	private final int[] lightDirection = new int[3];

	@OriginalMember(owner = "client!pk", name = "S", descriptor = "I")
	private int lightAzimuth = 3216;

	@OriginalMember(owner = "client!pk", name = "X", descriptor = "I")
	private int bumpStrength = 4096;

	@OriginalMember(owner = "client!pk", name = "db", descriptor = "I")
	private int lightElevation = 3216;

	@OriginalMember(owner = "client!pk", name = "<init>", descriptor = "()V")
	public TextureOpBumpMap() {
		super(1, true);
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.bumpStrength = buf.g2();
		} else if (opcode == 1) {
			this.lightAzimuth = buf.g2();
		} else if (opcode == 2) {
			this.lightElevation = buf.g2();
		}
	}

	@OriginalMember(owner = "client!pk", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.computeDirectionVector();
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(30) int bumpScale = Texture.widthScale * this.bumpStrength >> 12;
			@Pc(40) int[] prevRow = this.getChildMonochromeOutput(0, Texture.heightMask & row - 1);
			@Pc(46) int[] curRow = this.getChildMonochromeOutput(0, row);
			@Pc(56) int[] nextRow = this.getChildMonochromeOutput(0, row + 1 & Texture.heightMask);
			for (@Pc(58) int i = 0; i < Texture.width; i++) {
				@Pc(81) int dx = (curRow[Texture.widthMask & i - 1] - curRow[i + 1 & Texture.widthMask]) * bumpScale >> 12;
				@Pc(94) int dy = bumpScale * (nextRow[i] - prevRow[i]) >> 12;
				@Pc(98) int absDx = dx >> 4;
				if (absDx < 0) {
					absDx = -absDx;
				}
				if (absDx > 255) {
					absDx = 255;
				}
				@Pc(113) int absDy = dy >> 4;
				if (absDy < 0) {
					absDy = -absDy;
				}
				if (absDy > 255) {
					absDy = 255;
				}
				@Pc(142) int normalZ = MonochromeImageCache.normalBrightnessLookup[(absDy * (absDy + 1) >> 1) + absDx] & 0xFF;
				@Pc(148) int normalY = dy * normalZ >> 8;
				@Pc(154) int normalX = normalZ * dx >> 8;
				@Pc(163) int dotY = normalY * this.lightDirection[1] >> 12;
				@Pc(172) int dotX = this.lightDirection[0] * normalX >> 12;
				@Pc(178) int scaledZ = normalZ * 4096 >> 8;
				@Pc(187) int dotZ = scaledZ * this.lightDirection[2] >> 12;
				output[i] = dotZ + dotY + dotX;
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!pk", name = "g", descriptor = "(B)V")
	private void computeDirectionVector() {
		@Pc(7) double cosElev = Math.cos((float) this.lightElevation / 4096.0F);
		this.lightDirection[0] = (int) (cosElev * 4096.0D * Math.sin((float) this.lightAzimuth / 4096.0F));
		this.lightDirection[1] = (int) (Math.cos((float) this.lightAzimuth / 4096.0F) * cosElev * 4096.0D);
		this.lightDirection[2] = (int) (Math.sin((float) this.lightElevation / 4096.0F) * 4096.0D);
		@Pc(73) int zSq = this.lightDirection[2] * this.lightDirection[2] >> 12;
		@Pc(85) int ySq = this.lightDirection[1] * this.lightDirection[1] >> 12;
		@Pc(97) int xSq = this.lightDirection[0] * this.lightDirection[0] >> 12;
		@Pc(111) int magnitude = (int) (Math.sqrt(xSq + ySq + zSq >> 12) * 4096.0D);
		if (magnitude != 0) {
			this.lightDirection[2] = (this.lightDirection[2] << 12) / magnitude;
			this.lightDirection[0] = (this.lightDirection[0] << 12) / magnitude;
			this.lightDirection[1] = (this.lightDirection[1] << 12) / magnitude;
		}
	}
}
