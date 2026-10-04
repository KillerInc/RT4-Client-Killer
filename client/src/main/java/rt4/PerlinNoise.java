package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class PerlinNoise {
	@OriginalMember(owner = "client!fc", name = "a", descriptor = "(III)I")
	public static int getTileHeight(@OriginalArg(0) int x, @OriginalArg(2) int y) {
		@Pc(36) int height = interpolatedNoise(4, y + 45365, x - -91923) + (interpolatedNoise(2, y + 10294, x + 37821) - 128 >> 1) + (interpolatedNoise(1, y, x) + -128 >> 2) - 128;
		height = (int) ((double) height * 0.3D) + 35;
		if (height < 10) {
			height = 10;
		} else if (height > 60) {
			height = 60;
		}
		return height;
	}

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(IIII)I")
	public static int interpolatedNoise(@OriginalArg(0) int frequency, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(7) int gridX = x / frequency;
		@Pc(11) int gridY = y / frequency;
		@Pc(17) int fracY = y & frequency - 1;
		@Pc(23) int fracX = frequency - 1 & x;
		@Pc(28) int n00 = smoothNoise(gridX, gridY);
		@Pc(35) int n10 = smoothNoise(gridX + 1, gridY);
		@Pc(42) int n01 = smoothNoise(gridX, gridY + 1);
		@Pc(56) int n11 = smoothNoise(gridX + 1, gridY + 1);
		@Pc(63) int interpX0 = interpolate(n00, n10, fracX, frequency);
		@Pc(70) int interpX1 = interpolate(n01, n11, fracX, frequency);
		return interpolate(interpX0, interpX1, fracY, frequency);
	}

	@OriginalMember(owner = "client!bn", name = "a", descriptor = "(IIB)I")
	public static int smoothNoise(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		@Pc(47) int corners = noise(x - 1, y + -1) + noise(x + 1, y + -1) + noise(x + -1, y - -1) + noise(x + 1, y - -1);
		@Pc(76) int sides = noise(x - 1, y) + noise(x + 1, y) + noise(x, y + -1) + noise(x, y + 1);
		@Pc(81) int center = noise(x, y);
		return sides / 8 + corners / 16 + center / 4;
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(IIIII)I")
	public static int interpolate(@OriginalArg(0) int a, @OriginalArg(1) int b, @OriginalArg(2) int t, @OriginalArg(4) int scale) {
		@Pc(22) int weight = 65536 - MathUtils.cos[t * 1024 / scale] >> 1;
		return (a * (65536 - weight) >> 16) + (b * weight >> 16);
	}

	@OriginalMember(owner = "client!km", name = "b", descriptor = "(III)I")
	public static int noise(@OriginalArg(0) int x, @OriginalArg(2) int y) {
		@Pc(14) int n = y * 57 + x;
		@Pc(20) int hash = n ^ n << 13;
		@Pc(34) int result = Integer.MAX_VALUE & (hash * hash * 15731 + 789221) * hash + 1376312589;
		return result >> 19 & 0xFF;
	}
}
