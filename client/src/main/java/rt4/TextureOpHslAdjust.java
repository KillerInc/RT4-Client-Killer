package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!hk")
public final class TextureOpHslAdjust extends TextureOp {

	@OriginalMember(owner = "client!hk", name = "Q", descriptor = "I")
	private int green;

	@OriginalMember(owner = "client!hk", name = "Z", descriptor = "I")
	private int hue;

	@OriginalMember(owner = "client!hk", name = "cb", descriptor = "I")
	private int blue;

	@OriginalMember(owner = "client!hk", name = "gb", descriptor = "I")
	private int red;

	@OriginalMember(owner = "client!hk", name = "ib", descriptor = "I")
	private int lightness;

	@OriginalMember(owner = "client!hk", name = "jb", descriptor = "I")
	private int saturation;

	@OriginalMember(owner = "client!hk", name = "T", descriptor = "I")
	private int hueShift = 0;

	@OriginalMember(owner = "client!hk", name = "U", descriptor = "I")
	private int lightnessShift = 0;

	@OriginalMember(owner = "client!hk", name = "X", descriptor = "I")
	private int saturationShift = 0;

	@OriginalMember(owner = "client!hk", name = "<init>", descriptor = "()V")
	public TextureOpHslAdjust() {
		super(1, false);
	}

	@OriginalMember(owner = "client!hk", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.hueShift = buf.g2b();
		} else if (opcode == 1) {
			this.saturationShift = (buf.g1b() << 12) / 100;
		} else if (opcode == 2) {
			this.lightnessShift = (buf.g1b() << 12) / 100;
		}
	}

	@OriginalMember(owner = "client!hk", name = "a", descriptor = "(IIII)V")
	private void hslToRgb(@OriginalArg(0) int l, @OriginalArg(1) int s, @OriginalArg(3) int h) {
		@Pc(35) int q = l <= 2048 ? l * (s + 4096) >> 12 : l + s - (l * s >> 12);
		if (q <= 0) {
			this.red = this.green = this.blue = l;
			return;
		}
		@Pc(44) int hue6 = h * 6;
		@Pc(51) int p = l + l - q;
		@Pc(55) int sector = hue6 >> 12;
		@Pc(64) int chroma = (q - p << 12) / q;
		@Pc(70) int frac = hue6 - (sector << 12);
		@Pc(78) int chromaScaled = q * chroma >> 12;
		@Pc(84) int ramp = chromaScaled * frac >> 12;
		@Pc(88) int ascending = ramp + p;
		@Pc(93) int descending = q - ramp;
		if (sector == 0) {
			this.blue = p;
			this.red = q;
			this.green = ascending;
		} else if (sector == 1) {
			this.blue = p;
			this.green = q;
			this.red = descending;
		} else if (sector == 2) {
			this.red = p;
			this.green = q;
			this.blue = ascending;
		} else if (sector == 3) {
			this.green = descending;
			this.blue = q;
			this.red = p;
		} else if (sector == 4) {
			this.blue = q;
			this.red = ascending;
			this.green = p;
		} else if (sector == 5) {
			this.green = p;
			this.red = q;
			this.blue = descending;
		}
	}

	@OriginalMember(owner = "client!hk", name = "a", descriptor = "(BIII)V")
	private void rgbToHsl(@OriginalArg(1) int r, @OriginalArg(2) int g, @OriginalArg(3) int b) {
		@Pc(12) int rgMax = r > g ? r : g;
		@Pc(31) int max = b <= rgMax ? rgMax : b;
		@Pc(38) int rgMin = g > r ? r : g;
		@Pc(49) int min = b >= rgMin ? rgMin : b;
		@Pc(54) int delta = max - min;
		if (delta > 0) {
			@Pc(65) int dg = (max - g << 12) / delta;
			@Pc(74) int dr = (max - r << 12) / delta;
			@Pc(83) int db = (max - b << 12) / delta;
			if (r == max) {
				this.hue = min == g ? db + 20480 : -dg + 4096;
			} else if (max == g) {
				this.hue = min == b ? dr + 4096 : -db + 12288;
			} else {
				this.hue = min == r ? dg + 12288 : -dr + 20480;
			}
			this.hue /= 6;
		} else {
			this.hue = 0;
		}
		this.lightness = (min + max) / 2;
		if (this.lightness > 0 && this.lightness < 4096) {
			this.saturation = (delta << 12) / (this.lightness > 2048 ? 8192 - this.lightness * 2 : this.lightness * 2);
		} else {
			this.saturation = 0;
		}
	}

	@OriginalMember(owner = "client!hk", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(17) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(28) int[][] input = this.getChildColorOutput(row, 0);
			@Pc(32) int[] srcR = input[0];
			@Pc(36) int[] srcG = input[1];
			@Pc(40) int[] srcB = input[2];
			@Pc(44) int[] destG = output[1];
			@Pc(48) int[] destB = output[2];
			@Pc(52) int[] destR = output[0];
			for (@Pc(54) int i = 0; i < Texture.width; i++) {
				this.rgbToHsl(srcR[i], srcG[i], srcB[i]);
				this.lightness += this.lightnessShift;
				if (this.lightness < 0) {
					this.lightness = 0;
				}
				this.saturation += this.saturationShift;
				if (this.lightness > 4096) {
					this.lightness = 4096;
				}
				if (this.saturation < 0) {
					this.saturation = 0;
				}
				if (this.saturation > 4096) {
					this.saturation = 4096;
				}
				for (this.hue += this.hueShift; this.hue < 0; this.hue += 4096) {
				}
				while (this.hue > 4096) {
					this.hue -= 4096;
				}
				this.hslToRgb(this.lightness, this.saturation, this.hue);
				destR[i] = this.red;
				destG[i] = this.green;
				destB[i] = this.blue;
			}
		}
		return output;
	}
}
