package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ni")
public final class FluType {

	@OriginalMember(owner = "client!ni", name = "d", descriptor = "I")
	public int saturation;

	@OriginalMember(owner = "client!ni", name = "i", descriptor = "I")
	public int weightedHue;

	@OriginalMember(owner = "client!ni", name = "r", descriptor = "I")
	public int lightness;

	@OriginalMember(owner = "client!ni", name = "u", descriptor = "I")
	public int chroma;

	@OriginalMember(owner = "client!ni", name = "b", descriptor = "I")
	private int color = 0;

	@OriginalMember(owner = "client!ni", name = "m", descriptor = "I")
	public int texture = -1;

	@OriginalMember(owner = "client!ni", name = "o", descriptor = "I")
	public int textureScale = 128;

	@OriginalMember(owner = "client!ni", name = "l", descriptor = "Z")
	public boolean blockShadow = true;

	@OriginalMember(owner = "client!ni", name = "a", descriptor = "(IB)V")
	private void rgbToHsl(@OriginalArg(0) int rgb) {
		@Pc(8) double red = (double) (rgb >> 16 & 0xFF) / 256.0D;
		@Pc(21) double green = (double) (rgb >> 8 & 0xFF) / 256.0D;
		@Pc(23) double min = red;
		@Pc(30) double blue = (double) (rgb & 0xFF) / 256.0D;
		if (green < red) {
			min = green;
		}
		if (blue < min) {
			min = blue;
		}
		@Pc(44) double max = red;
		@Pc(54) double hue = 0.0D;
		if (green > red) {
			max = green;
		}
		if (blue > max) {
			max = blue;
		}
		@Pc(68) double sat = 0.0D;
		@Pc(74) double lum = (max + min) / 2.0D;
		if (min != max) {
			if (lum < 0.5D) {
				sat = (max - min) / (max + min);
			}
			if (max == red) {
				hue = (green - blue) / (-min + max);
			} else if (green == max) {
				hue = (blue - red) / (max - min) + 2.0D;
			} else if (blue == max) {
				hue = (red - green) / (-min + max) + 4.0D;
			}
			if (lum >= 0.5D) {
				sat = (max - min) / ((2.0D - max) - min);
			}
		}
		if (lum > 0.5D) {
			this.chroma = (int) (sat * (1.0D - lum) * 512.0D);
		} else {
			this.chroma = (int) (sat * lum * 512.0D);
		}
		if (this.chroma < 1) {
			this.chroma = 1;
		}
		this.saturation = (int) (sat * 256.0D);
		this.lightness = (int) (lum * 256.0D);
		if (this.lightness < 0) {
			this.lightness = 0;
		} else if (this.lightness > 255) {
			this.lightness = 255;
		}
		hue /= 6.0D;
		this.weightedHue = (int) ((double) this.chroma * hue);
		if (this.saturation < 0) {
			this.saturation = 0;
		} else if (this.saturation > 255) {
			this.saturation = 255;
		}
	}

	@OriginalMember(owner = "client!ni", name = "a", descriptor = "(ILclient!wa;I)V")
	public final void decode(@OriginalArg(0) int id, @OriginalArg(1) Buffer buffer) {
		while (true) {
			@Pc(7) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(opcode, buffer, id);
		}
	}

	@OriginalMember(owner = "client!ni", name = "a", descriptor = "(BILclient!wa;I)V")
	private void decode(@OriginalArg(1) int opcode, @OriginalArg(2) Buffer buffer, @OriginalArg(3) int id) {
		if (opcode == 1) {
			this.color = buffer.g3();
			this.rgbToHsl(this.color);
		} else if (opcode == 2) {
			this.texture = buffer.g2();
			if (this.texture == 65535) {
				this.texture = -1;
			}
		} else if (opcode == 3) {
			this.textureScale = buffer.g2();
		} else if (opcode == 4) {
			this.blockShadow = false;
		}
	}
}
