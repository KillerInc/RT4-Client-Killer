package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class ColorUtils {
	@OriginalMember(owner = "client!ug", name = "b", descriptor = "[F")
	public static final float[] rgbaBuffer = new float[4];

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(II)I")
	public static int rgbToHsl(@OriginalArg(1) int rgb) {
		@Pc(10) double r = (double) (rgb >> 16 & 0xFF) / 256.0D;
		@Pc(19) double g = (double) (rgb >> 8 & 0xFF) / 256.0D;
		@Pc(25) double hue = 0.0D;
		@Pc(32) double b = (double) (rgb & 0xFF) / 256.0D;
		@Pc(34) double min = r;
		@Pc(37) double saturation = 0;
		@Pc(39) double max = r;
		if (r > g) {
			min = g;
		}
		if (b < min) {
			min = b;
		}
		if (g > r) {
			max = g;
		}
		if (b > max) {
			max = b;
		}
		@Pc(72) double lightness = (min + max) / 2.0D;
		if (min != max) {
			if (lightness < 0.5D) {
				saturation = (max - min) / (min + max);
			}
			if (lightness >= 0.5D) {
				saturation = (max - min) / (2.0D - max - min);
			}
			if (max == r) {
				hue = (g - b) / (max - min);
			} else if (g == max) {
				hue = (b - r) / (max - min) + 2.0D;
			} else if (max == b) {
				hue = (r - g) / (-min + max) + 4.0D;
			}
		}
		@Pc(159) int sat = (int) (saturation * 256.0D);
		@Pc(164) int light = (int) (lightness * 256.0D);
		hue /= 6.0D;
		if (light < 0) {
			light = 0;
		} else if (light > 255) {
			light = 255;
		}
		@Pc(188) int h = (int) (hue * 256.0D);
		if (sat < 0) {
			sat = 0;
		} else if (sat > 255) {
			sat = 255;
		}
		if (light > 243) {
			sat >>= 0x4;
		} else if (light > 217) {
			sat >>= 0x3;
		} else if (light > 192) {
			sat >>= 0x2;
		} else if (light > 179) {
			sat >>= 0x1;
		}
		return (h >> 2 << 10) + (sat >> 5 << 7) + (light >> 1);
	}

	@OriginalMember(owner = "client!pf", name = "a", descriptor = "(II)[F")
	public static float[] getRgbFloat(@OriginalArg(0) int color) {
		@Pc(7) float totalLight = FogManager.getLightingModelAmbient() + FogManager.getLight0Diffuse();
		@Pc(9) int lightColor = FogManager.getLightColor();
		@Pc(11) float scale = 0.58823526F;
		rgbaBuffer[3] = 1.0F;
		@Pc(24) float lightR = (float) (lightColor >> 16 & 0xFF) / 255.0F;
		@Pc(33) float lightG = (float) (lightColor >> 8 & 0xFF) / 255.0F;
		rgbaBuffer[1] = totalLight * (float) (color >> 8 & 0xFF) / 255.0F * lightG * scale;
		rgbaBuffer[0] = totalLight * scale * lightR * ((float) (color >> 16 & 0xFF) / 255.0F);
		@Pc(74) float lightB = (float) (lightColor & 0xFF) / 255.0F;
		rgbaBuffer[2] = (float) (color & 0xFF) / 255.0F * lightB * scale * totalLight;
		return rgbaBuffer;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(IZI)I")
	public static int multiplyLightnessSafe(@OriginalArg(0) int multiplier, @OriginalArg(2) int hsl) {
		if (hsl == -1) {
			return 12345678;
		}
		multiplier = multiplier * (hsl & 0x7F) >> 7;
		if (multiplier < 2) {
			multiplier = 2;
		} else if (multiplier > 126) {
			multiplier = 126;
		}
		return multiplier + (hsl & 0xFF80);
	}

	@OriginalMember(owner = "client!sj", name = "a", descriptor = "(BII)I")
	public static int multiplyLightnessGrayscale(@OriginalArg(1) int hsl, @OriginalArg(2) int lightness) {
		if (hsl == -2) {
			return 12345678;
		} else if (hsl == -1) {
			if (lightness < 2) {
				lightness = 2;
			} else if (lightness > 126) {
				lightness = 126;
			}
			return lightness;
		} else {
			lightness = (hsl & 0x7F) * lightness >> 7;
			if (lightness < 2) {
				lightness = 2;
			} else if (lightness > 126) {
				lightness = 126;
			}
			return (hsl & 0xFF80) + lightness;
		}
	}

	@OriginalMember(owner = "client!w", name = "f", descriptor = "(I)I")
	public static int clampLightness(@OriginalArg(0) int lightness) {
		if (lightness < 2) {
			lightness = 2;
		} else if (lightness > 126) {
			lightness = 126;
		}
		return lightness;
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(II)I")
	public static int multiplyLightness(@OriginalArg(0) int a, @OriginalArg(1) int b) {
		b = b * (a & 0x7F) >> 7;
		if (b < 2) {
			b = 2;
		} else if (b > 126) {
			b = 126;
		}
		return (a & 0xFF80) + b;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(II)I")
	public static int multiplyLightness2(@OriginalArg(0) int a, @OriginalArg(1) int b) {
		b = b * (a & 0x7F) >> 7;
		if (b < 2) {
			b = 2;
		} else if (b > 126) {
			b = 126;
		}
		return (a & 0xFF80) + b;
	}

	@OriginalMember(owner = "client!ri", name = "a", descriptor = "(II)I")
	public static int multiplyLightness3(@OriginalArg(0) int a, @OriginalArg(1) int b) {
		b = b * (a & 0x7F) >> 7;
		if (b < 2) {
			b = 2;
		} else if (b > 126) {
			b = 126;
		}
		return (a & 0xFF80) + b;
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(IIII)I")
	public static int packHsl(@OriginalArg(0) int lightness, @OriginalArg(2) int saturation, @OriginalArg(3) int hue) {
		if (lightness > 243) {
			saturation >>= 0x4;
		} else if (lightness > 217) {
			saturation >>= 0x3;
		} else if (lightness > 192) {
			saturation >>= 0x2;
		} else if (lightness > 179) {
			saturation >>= 0x1;
		}
		return (lightness >> 1) + (saturation >> 5 << 7) + (hue >> 2 << 10);
	}
}
