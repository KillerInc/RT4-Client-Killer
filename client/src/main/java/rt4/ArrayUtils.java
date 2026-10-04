package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class ArrayUtils {

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([JI[JII)V")
	public static void copy(@OriginalArg(0) long[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) long[] dest, @OriginalArg(3) int destOff, @OriginalArg(4) int length) {
		@Pc(15) int end;
		if (src == dest) {
			if (srcOff == destOff) {
				return;
			}
			if (destOff > srcOff && destOff < srcOff + length) {
				end = length - 1;
				@Pc(19) int si = srcOff + end;
				@Pc(23) int di = destOff + end;
				end = si - end;
				end += 3;
				while (si >= end) {
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
				}
				end -= 3;
				while (si >= end) {
					dest[di--] = src[si--];
				}
				return;
			}
		}
		end = length + srcOff;
		@Pc(83) int unrolledEnd = end - 3;
		while (srcOff < unrolledEnd) {
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
		}
		end = unrolledEnd + 3;
		while (srcOff < end) {
			dest[destOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([II[III)V")
	public static void copy(@OriginalArg(0) int[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) int[] dest, @OriginalArg(3) int destOff, @OriginalArg(4) int length) {
		@Pc(15) int end;
		if (src == dest) {
			if (srcOff == destOff) {
				return;
			}
			if (destOff > srcOff && destOff < srcOff + length) {
				end = length - 1;
				@Pc(19) int si = srcOff + end;
				@Pc(23) int di = destOff + end;
				end = si - end;
				end += 7;
				while (si >= end) {
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
				}
				end -= 7;
				while (si >= end) {
					dest[di--] = src[si--];
				}
				return;
			}
		}
		end = length + srcOff;
		@Pc(115) int unrolledEnd = end - 7;
		while (srcOff < unrolledEnd) {
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
		}
		end = unrolledEnd + 7;
		while (srcOff < end) {
			dest[destOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([IIII)V")
	public static void fill(@OriginalArg(0) int[] dest, @OriginalArg(1) int off, @OriginalArg(2) int length, @OriginalArg(3) int value) {
		@Pc(5) int unrolledEnd = off + length - 7;
		while (off < unrolledEnd) {
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
			dest[off++] = value;
		}
		unrolledEnd += 7;
		while (off < unrolledEnd) {
			dest[off++] = value;
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([FI[FII)V")
	public static void copy(@OriginalArg(0) float[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) float[] dest, @OriginalArg(3) int destOff, @OriginalArg(4) int length) {
		if (src == dest) {
			return;
		}
		@Pc(114) int end = length;
		@Pc(115) int unrolledEnd = end - 7;
		while (srcOff < unrolledEnd) {
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
		}
		end = unrolledEnd + 7;
		while (srcOff < end) {
			dest[destOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([SI[SII)V")
	public static void copy(@OriginalArg(0) short[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) short[] dest, @OriginalArg(3) int destOff, @OriginalArg(4) int length) {
		@Pc(15) int end;
		if (src == dest) {
			if (srcOff == destOff) {
				return;
			}
			if (destOff > srcOff && destOff < srcOff + length) {
				end = length - 1;
				@Pc(19) int si = srcOff + end;
				@Pc(23) int di = destOff + end;
				end = si - end;
				end += 7;
				while (si >= end) {
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
				}
				end -= 7;
				while (si >= end) {
					dest[di--] = src[si--];
				}
				return;
			}
		}
		end = length + srcOff;
		@Pc(115) int unrolledEnd = end - 7;
		while (srcOff < unrolledEnd) {
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
		}
		end = unrolledEnd + 7;
		while (srcOff < end) {
			dest[destOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([Ljava/lang/Object;I[Ljava/lang/Object;II)V")
	public static void copy(@OriginalArg(0) Object[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) Object[] dest, @OriginalArg(3) int destOff, @OriginalArg(4) int length) {
		@Pc(15) int end;
		if (src == dest) {
			if (srcOff == destOff) {
				return;
			}
			if (destOff > srcOff && destOff < srcOff + length) {
				end = length - 1;
				@Pc(19) int si = srcOff + end;
				@Pc(23) int di = destOff + end;
				end = si - end;
				end += 7;
				while (si >= end) {
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
					dest[di--] = src[si--];
				}
				end -= 7;
				while (si >= end) {
					dest[di--] = src[si--];
				}
				return;
			}
		}
		end = length + srcOff;
		@Pc(115) int unrolledEnd = end - 7;
		while (srcOff < unrolledEnd) {
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
			dest[destOff++] = src[srcOff++];
		}
		end = unrolledEnd + 7;
		while (srcOff < end) {
			dest[destOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([III)V")
	public static void clear(@OriginalArg(0) int[] dest, @OriginalArg(1) int off, @OriginalArg(2) int length) {
		@Pc(5) int len = length - 7;
		while (off < len) {
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
			dest[off++] = 0;
		}
		len += 7;
		while (off < len) {
			dest[off++] = 0;
		}
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(Z[J[I)V")
	public static void sort(@OriginalArg(1) long[] keys, @OriginalArg(2) int[] values) {
		sort(keys, 0, keys.length - 1, values);
	}

	@OriginalMember(owner = "client!gj", name = "a", descriptor = "([JII[II)V")
	public static void sort(@OriginalArg(0) long[] keys, @OriginalArg(1) int lo, @OriginalArg(2) int hi, @OriginalArg(3) int[] values) {
		if (lo >= hi) {
			return;
		}
		@Pc(20) int i = lo;
		@Pc(26) int mid = (hi + lo) / 2;
		@Pc(30) long pivotKey = keys[mid];
		keys[mid] = keys[hi];
		keys[hi] = pivotKey;
		@Pc(44) int pivotValue = values[mid];
		values[mid] = values[hi];
		values[hi] = pivotValue;
		for (@Pc(56) int j = lo; j < hi; j++) {
			if (keys[j] < pivotKey + (long) (j & 0x1)) {
				@Pc(76) long key = keys[j];
				keys[j] = keys[i];
				keys[i] = key;
				@Pc(90) int value = values[j];
				values[j] = values[i];
				values[i++] = value;
			}
		}
		keys[hi] = keys[i];
		keys[i] = pivotKey;
		values[hi] = values[i];
		values[i] = pivotValue;
		sort(keys, lo, i - 1, values);
		sort(keys, i + 1, hi, values);
	}

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "([II)[I")
	public static int[] copyOfNullable(@OriginalArg(0) int[] src) {
		if (src == null) {
			return null;
		} else {
			@Pc(18) int[] copy = new int[src.length];
			copy(src, 0, copy, 0, src.length);
			return copy;
		}
	}

	@OriginalMember(owner = "client!vg", name = "a", descriptor = "(I[S)[S")
	public static short[] copyOfNullable(@OriginalArg(1) short[] src) {
		if (src == null) {
			return null;
		} else {
			@Pc(19) short[] copy = new short[src.length];
			copy(src, 0, copy, 0, src.length);
			return copy;
		}
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "([SI)[S")
	public static short[] copyOf(@OriginalArg(0) short[] src, @OriginalArg(1) int length) {
		@Pc(2) short[] copy = new short[length];
		copy(src, 0, copy, 0, length);
		return copy;
	}

	@OriginalMember(owner = "client!td", name = "a", descriptor = "([FI)[F")
	public static float[] copyOf(@OriginalArg(0) float[] src, @OriginalArg(1) int length) {
		@Pc(2) float[] copy = new float[length];
		copy(src, 0, copy, 0, length);
		return copy;
	}

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "([IIIII)V")
	public static void fillRange(@OriginalArg(0) int[] dest, @OriginalArg(1) int from, @OriginalArg(3) int to, @OriginalArg(4) int value) {
		from--;
		@Pc(14) int end = to - 1;
		@Pc(17) int unrolledEnd = end - 7;
		while (unrolledEnd > from) {
			@Pc(22) int i0 = from + 1;
			dest[i0] = value;
			@Pc(27) int i1 = i0 + 1;
			dest[i1] = value;
			@Pc(32) int i2 = i1 + 1;
			dest[i2] = value;
			@Pc(37) int i3 = i2 + 1;
			dest[i3] = value;
			@Pc(42) int i4 = i3 + 1;
			dest[i4] = value;
			@Pc(47) int i5 = i4 + 1;
			dest[i5] = value;
			@Pc(52) int i6 = i5 + 1;
			dest[i6] = value;
			from = i6 + 1;
			dest[from] = value;
		}
		while (end > from) {
			from++;
			dest[from] = value;
		}
	}

	@OriginalMember(owner = "client!ec", name = "a", descriptor = "([Ljava/lang/Object;I[III)V")
	public static void sort(@OriginalArg(0) Object[] values, @OriginalArg(1) int hi, @OriginalArg(2) int[] keys, @OriginalArg(4) int lo) {
		if (lo >= hi) {
			return;
		}
		@Pc(11) int mid = (lo + hi) / 2;
		@Pc(15) int pivotKey = keys[mid];
		@Pc(17) int i = lo;
		keys[mid] = keys[hi];
		keys[hi] = pivotKey;
		@Pc(31) Object pivotValue = values[mid];
		values[mid] = values[hi];
		values[hi] = pivotValue;
		for (@Pc(43) int j = lo; j < hi; j++) {
			if ((j & 0x1) + pivotKey > keys[j]) {
				@Pc(67) int key = keys[j];
				keys[j] = keys[i];
				keys[i] = key;
				@Pc(81) Object value = values[j];
				values[j] = values[i];
				values[i++] = value;
			}
		}
		keys[hi] = keys[i];
		keys[i] = pivotKey;
		values[hi] = values[i];
		values[i] = pivotValue;
		sort(values, i - 1, keys, lo);
		sort(values, hi, keys, i + 1);
	}

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(I[I[Ljava/lang/Object;)V")
	public static void sort(@OriginalArg(1) int[] keys, @OriginalArg(2) Object[] values) {
		sort(values, keys.length - 1, keys, 0);
	}

	@OriginalMember(owner = "client!nj", name = "a", descriptor = "(IIZ[I[I)V")
	public static void sort(@OriginalArg(0) int lo, @OriginalArg(1) int hi, @OriginalArg(3) int[] keys, @OriginalArg(4) int[] values) {
		if (lo >= hi) {
			return;
		}
		@Pc(22) int mid = (hi + lo) / 2;
		@Pc(24) int i = lo;
		@Pc(28) int pivotKey = keys[mid];
		keys[mid] = keys[hi];
		keys[hi] = pivotKey;
		@Pc(42) int pivotValue = values[mid];
		values[mid] = values[hi];
		values[hi] = pivotValue;
		for (@Pc(54) int j = lo; j < hi; j++) {
			if (keys[j] > (j & 0x1) + pivotKey) {
				@Pc(79) int key = keys[j];
				keys[j] = keys[i];
				keys[i] = key;
				@Pc(93) int value = values[j];
				values[j] = values[i];
				values[i++] = value;
			}
		}
		keys[hi] = keys[i];
		keys[i] = pivotKey;
		values[hi] = values[i];
		values[i] = pivotValue;
		sort(lo, i - 1, keys, values);
		sort(i + 1, hi, keys, values);
	}

	@OriginalMember(owner = "client!sh", name = "a", descriptor = "(I[JII[Ljava/lang/Object;)V")
	public static void sort(@OriginalArg(0) int hi, @OriginalArg(1) long[] keys, @OriginalArg(3) int lo, @OriginalArg(4) Object[] values) {
		if (lo >= hi) {
			return;
		}
		@Pc(16) int i = lo;
		@Pc(23) int mid = (lo + hi) / 2;
		@Pc(27) long pivotKey = keys[mid];
		keys[mid] = keys[hi];
		keys[hi] = pivotKey;
		@Pc(41) Object pivotValue = values[mid];
		values[mid] = values[hi];
		values[hi] = pivotValue;
		for (@Pc(53) int j = lo; j < hi; j++) {
			if (pivotKey + (long) (j & 0x1) > keys[j]) {
				@Pc(72) long key = keys[j];
				keys[j] = keys[i];
				keys[i] = key;
				@Pc(86) Object value = values[j];
				values[j] = values[i];
				values[i++] = value;
			}
		}
		keys[hi] = keys[i];
		keys[i] = pivotKey;
		values[hi] = values[i];
		values[i] = pivotValue;
		sort(i - 1, keys, lo, values);
		sort(hi, keys, i + 1, values);
	}

	@OriginalMember(owner = "client!ac", name = "a", descriptor = "([J[Ljava/lang/Object;I)V")
	public static void sort(@OriginalArg(0) long[] keys, @OriginalArg(1) Object[] values) {
		sort(keys.length - 1, keys, 0, values);
	}
}
