package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

public class RandomUtils {
	@OriginalMember(owner = "client!d", name = "c", descriptor = "(III)I")
	public static int shuffle(@OriginalArg(0) int value, @OriginalArg(1) int range) {
		@Pc(11) int correction = range - 1 & value >> 31;
		return correction + (value + (value >>> 31)) % range;
	}

	@OriginalMember(owner = "client!ni", name = "a", descriptor = "(BILjava/util/Random;)I")
	public static int nextInt(@OriginalArg(1) int bound, @OriginalArg(2) Random random) {
		if (bound <= 0) {
			throw new IllegalArgumentException();
		} else if (IntUtils.isPowerOfTwo(bound)) {
			return (int) (((long) random.nextInt() & 0xFFFFFFFFL) * (long) bound >> 32);
		} else {
			@Pc(38) int threshold = Integer.MIN_VALUE - (int) (0x100000000L % (long) bound);
			@Pc(41) int sample;
			do {
				sample = random.nextInt();
			} while (threshold <= sample);
			return shuffle(sample, bound);
		}
	}
}
