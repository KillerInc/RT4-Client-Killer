package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class Base37 {
	@OriginalMember(owner = "client!ch", name = "v", descriptor = "[B")
	public static final byte[] DECODE_CHARS = new byte[]{95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57};

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(IJ)Lclient!na;")
	public static JagString decode37(@OriginalArg(1) long value) {
		if (value <= 0L || value >= 6582952005840035281L) {
			return null;
		} else if (value % 37L == 0L) {
			return null;
		} else {
			@Pc(32) int len = 0;
			@Pc(34) long temp = value;
			while (temp != 0L) {
				temp /= 37L;
				len++;
			}
			@Pc(48) byte[] chars = new byte[len];
			while (value != 0L) {
				@Pc(65) long prev = value;
				value /= 37L;
				len--;
				chars[len] = DECODE_CHARS[(int) (prev - value * 37L)];
			}
			@Pc(88) JagString result = new JagString();
			result.chars = chars;
			result.length = chars.length;
			return result;
		}
	}
}
