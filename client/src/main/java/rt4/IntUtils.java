package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class IntUtils {
	@OriginalMember(owner = "client!ra", name = "b", descriptor = "(III)I")
	public static int pow(@OriginalArg(0) int exponent, @OriginalArg(1) int base) {
		@Pc(15) int result = 1;
		while (exponent > 1) {
			if ((exponent & 0x1) != 0) {
				result *= base;
			}
			base *= base;
			exponent >>= 0x1;
		}
		if (exponent == 1) {
			return result * base;
		} else {
			return result;
		}
	}

	@OriginalMember(owner = "client!nb", name = "a", descriptor = "(BI)I")
	public static int clp2(@OriginalArg(1) int value) {
		@Pc(0) int n = value - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return n + 1;
	}

	@OriginalMember(owner = "client!qi", name = "b", descriptor = "(II)I")
	public static int bitCount(@OriginalArg(0) int value) {
		@Pc(5) int count = 0;
		if (value < 0 || value >= 65536) {
			count += 16;
			value >>>= 0x10;
		}
		if (value >= 256) {
			count += 8;
			value >>>= 0x8;
		}
		if (value >= 16) {
			count += 4;
			value >>>= 0x4;
		}
		if (value >= 4) {
			value >>>= 0x2;
			count += 2;
		}
		if (value >= 1) {
			value >>>= 0x1;
			count++;
		}
		return value + count;
	}

	@OriginalMember(owner = "client!gd", name = "a", descriptor = "(IIBI)I")
	public static int clamp(@OriginalArg(0) int max, @OriginalArg(1) int value, @OriginalArg(3) int min) {
		return min > value ? min : value > max ? max : value;
	}

	@OriginalMember(owner = "client!bn", name = "a", descriptor = "(III)I")
	public static int bitReverse(@OriginalArg(0) int bits, @OriginalArg(2) int value) {
		@Pc(8) int result = 0;
		while (bits > 0) {
			result = result << 1 | value & 0x1;
			value >>>= 0x1;
			bits--;
		}
		return result;
	}

	@OriginalMember(owner = "client!uc", name = "a", descriptor = "(II)I")
	public static int bitCountFast(@OriginalArg(0) int v) {
		@Pc(9) int a = (v >>> 1 & 0xD5555555) + (v & 0x55555555);
		@Pc(19) int b = (a >>> 2 & 0x33333333) + (a & 0x33333333);
		@Pc(31) int c = (b >>> 4) + b & 0xF0F0F0F;
		@Pc(37) int d = c + (c >>> 8);
		@Pc(43) int e = d + (d >>> 16);
		return e & 0xFF;
	}

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(BI)Z")
	public static boolean isPowerOfTwo(@OriginalArg(1) int v) {
		return v == (-v & v);
	}
}
