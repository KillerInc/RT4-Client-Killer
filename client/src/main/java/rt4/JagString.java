package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@OriginalClass("client!na")
public final class JagString implements StringInterface {

	@OriginalMember(owner = "client!pa", name = "O", descriptor = "Lclient!na;")
	public static final JagString EMPTY = parse("");
	@OriginalMember(owner = "client!pi", name = "Q", descriptor = "Lclient!na;")
	public static final JagString NULL_STRING = parse("null");
	@OriginalMember(owner = "client!t", name = "C", descriptor = "Lclient!na;")
	public static final JagString PERIOD = parse(")3");
	@OriginalMember(owner = "client!vk", name = "a", descriptor = "[I")
	public static final int[] CHAR_SORT_ORDER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 73, 74, 76, 78, 83, 84, 85, 86, 91, 92, 93, 94, 95, 97, 103, 104, 105, 106, 107, 108, 113, 114, 115, 116, 118, 119, 120, 121, 122, 123, 124, 125, 133, 134, 136, 138, 143, 144, 145, 146, 151, 152, 153, 154, 155, 157, 163, 164, 165, 166, 168, 169, 174, 175, 176, 177, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 97, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 157, 215, 216, 117, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 66, 66, 66, 66, 66, 66, 65, 75, 79, 79, 79, 79, 87, 87, 87, 87, 77, 96, 98, 98, 98, 98, 98, 250, 251, 109, 109, 109, 109, 117, 252, 167, 126, 126, 126, 126, 126, 126, 125, 135, 139, 139, 139, 139, 147, 147, 147, 147, 137, 156, 158, 158, 158, 158, 158, 253, 254, 170, 170, 170, 170, 178, 255, 178};
	@OriginalMember(owner = "client!sh", name = "e", descriptor = "Lclient!na;")
	public static final JagString EMPTY_NAME = parse("");
	@OriginalMember(owner = "client!dm", name = "n", descriptor = "Lclient!na;")
	public static final JagString PERCENT_SIGN = parse("(U");
	@OriginalMember(owner = "client!wa", name = "pb", descriptor = "Lclient!na;")
	public static final JagString LINE_BREAK = parse("<br>");
	@OriginalMember(owner = "client!ed", name = "H", descriptor = "Lclient!na;")
	public static final JagString CLOSE_COLOR_TIMES = parse("<)4col> x");
	@OriginalMember(owner = "client!je", name = "db", descriptor = "Lclient!na;")
	public static final JagString WHITE_COLOR_PREFIX = parse(" <col=ffffff>");
	@OriginalMember(owner = "client!uf", name = "s", descriptor = "Lclient!na;")
	public static final JagString GREEN_COLOR_PREFIX = parse(" <col=00ff80>");
	@OriginalMember(owner = "client!wj", name = "b", descriptor = "Lclient!na;")
	public static final JagString YELLOW_COLOR_PREFIX = parse(" <col=ffff00>");
	@OriginalMember(owner = "client!mi", name = "R", descriptor = "Lclient!na;")
	public static final JagString COMMA = parse(")1");
	@OriginalMember(owner = "client!sj", name = "w", descriptor = "Lclient!na;")
	public static final JagString CLOSE_COLOR = parse("<)4col>");
	@OriginalMember(owner = "client!jb", name = "c", descriptor = "Lclient!na;")
	public static final JagString CLOSE_PAREN_CLOSE_COLOR = parse("(Y<)4col>");
	@OriginalMember(owner = "client!fn", name = "Z", descriptor = "Lclient!na;")
	public static final JagString COMMA_SPACE = parse(")1 ");
	@OriginalMember(owner = "client!wb", name = "a", descriptor = "Lclient!na;")
	public static final JagString HYPHEN = parse(")2");
	@OriginalMember(owner = "client!dm", name = "j", descriptor = "Lclient!na;")
	public static final JagString SPACE = parse(" ");
	@OriginalMember(owner = "client!vh", name = "c", descriptor = "Lclient!na;")
	public static final JagString COLON = parse(":");
	@OriginalMember(owner = "client!ee", name = "a", descriptor = "Lclient!na;")
	public static final JagString TIMEZONE = parse(" GMT");
	@OriginalMember(owner = "client!li", name = "w", descriptor = "Lclient!sc;")
	public static HashTable internCache;
	@OriginalMember(owner = "client!na", name = "T", descriptor = "[B")
	public byte[] chars;

	@OriginalMember(owner = "client!na", name = "lb", descriptor = "I")
	public int length;

	@OriginalMember(owner = "client!na", name = "N", descriptor = "Z")
	private boolean mutable = true;

	@OriginalMember(owner = "client!ck", name = "a", descriptor = "([Lclient!na;B)Lclient!na;")
	public static JagString concatenate(@OriginalArg(0) JagString[] parts) {
		if (parts.length < 2) {
			throw new IllegalArgumentException();
		}
		return concatenateRange(0, parts.length, parts);
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(II[Lclient!na;I)Lclient!na;")
	public static JagString concatenateRange(@OriginalArg(0) int start, @OriginalArg(1) int count, @OriginalArg(2) JagString[] parts) {
		@Pc(5) int totalLen = 0;
		for (@Pc(7) int i = 0; i < count; i++) {
			if (parts[start + i] == null) {
				parts[i + start] = NULL_STRING;
			}
			totalLen += parts[i + start].length;
		}
		@Pc(39) byte[] buf = new byte[totalLen];
		@Pc(41) int pos = 0;
		for (@Pc(43) int i = 0; i < count; i++) {
			@Pc(52) JagString part = parts[i + start];
			copy(part.chars, 0, buf, pos, part.length);
			pos += part.length;
		}
		@Pc(71) JagString result = new JagString();
		result.length = totalLen;
		result.chars = buf;
		return result;
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "([BI[BII)V")
	public static void copy(@OriginalArg(0) byte[] src, @OriginalArg(1) int srcOff, @OriginalArg(2) byte[] dst, @OriginalArg(3) int dstOff, @OriginalArg(4) int len) {
		@Pc(15) int end;
		if (src == dst) {
			if (srcOff == dstOff) {
				return;
			}
			if (dstOff > srcOff && dstOff < srcOff + len) {
				end = len - 1;
				@Pc(19) int srcEnd = srcOff + end;
				@Pc(23) int dstEnd = dstOff + end;
				end = srcEnd - end;
				end += 7;
				while (srcEnd >= end) {
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
					dst[dstEnd--] = src[srcEnd--];
				}
				end -= 7;
				while (srcEnd >= end) {
					dst[dstEnd--] = src[srcEnd--];
				}
				return;
			}
		}
		end = len + srcOff;
		@Pc(115) int unrolledEnd = end - 7;
		while (srcOff < unrolledEnd) {
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
			dst[dstOff++] = src[srcOff++];
		}
		end = unrolledEnd + 7;
		while (srcOff < end) {
			dst[dstOff++] = src[srcOff++];
		}
	}

	@OriginalMember(owner = "client!cd", name = "a", descriptor = "(Ljava/lang/String;B)Lclient!na;")
	public static JagString parse(@OriginalArg(0) String str) {
		@Pc(6) byte[] bytes = str.getBytes();
		@Pc(9) int len = bytes.length;
		@Pc(13) JagString result = new JagString();
		@Pc(15) int pos = 0;
		result.chars = new byte[len];
		while (len > pos) {
			@Pc(29) int b = bytes[pos++] & 0xFF;
			if (b <= 45 && b >= 40) {
				if (pos >= len) {
					break;
				}
				@Pc(51) int b2 = bytes[pos++] & 0xFF;
				result.chars[result.length++] = (byte) (b2 + (b + -40) * 43 - 48);
			} else if (b != 0) {
				result.chars[result.length++] = (byte) b;
			}
		}
		result.compact();
		return result.intern();
	}

	@OriginalMember(owner = "client!jj", name = "b", descriptor = "(BI)Lclient!na;")
	public static JagString parseInt(@OriginalArg(1) int value) {
		return parseInt(false, value);
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(BZII)Lclient!na;")
	public static JagString parseInt(@OriginalArg(1) boolean showSign, @OriginalArg(3) int value) {
		@Pc(23) int digitCount = 1;
		@Pc(27) int tmp = value / 10;
		while (tmp != 0) {
			tmp /= 10;
			digitCount++;
		}
		@Pc(38) int bufLen = digitCount;
		if (value < 0 || showSign) {
			bufLen = digitCount + 1;
		}
		@Pc(46) byte[] buf = new byte[bufLen];
		if (value < 0) {
			buf[0] = 45;
		} else if (showSign) {
			buf[0] = 43;
		}
		for (@Pc(61) int i = 0; i < digitCount; i++) {
			@Pc(68) int digit = value % 10;
			if (digit < 0) {
				digit = -digit;
			}
			if (digit > 9) {
				digit += 39;
			}
			buf[bufLen - i - 1] = (byte) (digit + 48);
			value /= 10;
		}
		@Pc(112) JagString result = new JagString();
		result.chars = buf;
		result.length = bufLen;
		return result;
	}

	@OriginalMember(owner = "client!an", name = "a", descriptor = "([BIII)Lclient!na;")
	public static JagString decodeString(@OriginalArg(0) byte[] data, @OriginalArg(2) int len, @OriginalArg(3) int off) {
		@Pc(7) JagString result = new JagString();
		result.chars = new byte[len];
		result.length = 0;
		for (@Pc(22) int i = off; i < len + off; i++) {
			if (data[i] != 0) {
				result.chars[result.length++] = data[i];
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!bg", name = "d", descriptor = "(II)Z")
	public static boolean isLigature(@OriginalArg(0) int ch) {
		return ch == 198 || ch == 230 || ch == 156 || ch == 140 || ch == 223;
	}

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "(ZII)Lclient!na;")
	public static JagString parseIntTrue(@OriginalArg(2) int value) {
		return parseInt(true, value);
	}

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(II)Lclient!na;")
	public static JagString formatIp(@OriginalArg(0) int ip) {
		return concatenate(new JagString[]{parseInt(ip >> 24 & 0xFF), PERIOD, parseInt(ip >> 16 & 0xFF), PERIOD, parseInt(ip >> 8 & 0xFF), PERIOD, parseInt(ip & 0xFF)});
	}

	@OriginalMember(owner = "client!nb", name = "a", descriptor = "(II)Lclient!na;")
	public static JagString getNbsp() {
		@Pc(21) JagString str = new JagString();
		str.length = 1;
		str.chars = new byte[1];
		str.chars[0] = -96; // 0xA0 - NBSP
		return str;
	}

	/**
	 * @return A JagString consisting of the actual bytes in the provided string.
	 */
	@OriginalMember(owner = "client!sj", name = "a", descriptor = "(Ljava/lang/String;I)Lclient!na;")
	public static JagString of(@OriginalArg(0) String string) {
		@Pc(14) byte[] bytes = string.getBytes(StandardCharsets.ISO_8859_1);
		@Pc(23) JagString js = new JagString();
		js.chars = bytes;
		js.length = 0;
		for (@Pc(31) int i = 0; i < bytes.length; i++) {
			if (bytes[i] != 0) {
				bytes[js.length++] = bytes[i];
			}
		}
		return js;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(BI)Lclient!na;")
	public static JagString allocate(@OriginalArg(1) int capacity) {
		@Pc(13) JagString result = new JagString();
		result.length = 0;
		result.chars = new byte[capacity];
		return result;
	}

	@OriginalMember(owner = "client!md", name = "a", descriptor = "(JB)Lclient!na;")
	public static JagString parseLong(@OriginalArg(0) long value) {
		return parseLongImpl(value);
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(IZIJ)Lclient!na;")
	public static JagString parseLongImpl(@OriginalArg(3) long value) {
		@Pc(35) long tmp = value / (long) 10;
		@Pc(37) int digitCount = 1;
		while (tmp != 0L) {
			digitCount++;
			tmp /= 10;
		}
		@Pc(51) int bufLen = digitCount;
		if (value < 0L) {
			bufLen = digitCount + 1;
		}
		@Pc(61) byte[] buf = new byte[bufLen];
		if (value < 0L) {
			buf[0] = 45;
		}
		for (@Pc(79) int i = 0; i < digitCount; i++) {
			@Pc(92) int digit = (int) (value % (long) 10);
			value /= 10;
			if (digit < 0) {
				digit = -digit;
			}
			if (digit > 9) {
				digit += 39;
			}
			buf[bufLen - i - 1] = (byte) (digit + 48);
		}
		@Pc(126) JagString result = new JagString();
		result.chars = buf;
		result.length = bufLen;
		return result;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Z)Ljava/net/URL;")
	public final URL toUrl() throws MalformedURLException {
		return new URL(new String(this.chars, 0, this.length));
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(BLclient!na;)Z")
	public final boolean strEquals(@OriginalArg(1) JagString other) {
		if (other == null) {
			return false;
		} else if (other == this) {
			return true;
		} else if (this.length == other.length) {
			@Pc(29) byte[] otherChars = other.chars;
			@Pc(32) byte[] thisChars = this.chars;
			for (@Pc(34) int i = 0; i < this.length; i++) {
				if (thisChars[i] != otherChars[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(BI)I")
	public final int parseHexString(@OriginalArg(1) int radix) {
		@Pc(14) boolean negative = false;
		@Pc(20) boolean hasDigit = false;
		@Pc(22) int result = 0;
		for (@Pc(24) int i = 0; i < this.length; i++) {
			@Pc(43) int digit = this.chars[i] & 0xFF;
			if (i == 0) {
				if (digit == 45) {
					negative = true;
					continue;
				}
				if (digit == 43) {
					continue;
				}
			}
			if (digit >= 48 && digit <= 57) {
				digit -= 48;
			} else if (digit >= 65 && digit <= 90) {
				digit -= 55;
			} else if (digit >= 97 && digit <= 122) {
				digit -= 87;
			} else {
				throw new NumberFormatException();
			}
			if (radix <= digit) {
				throw new NumberFormatException();
			}
			if (negative) {
				digit = -digit;
			}
			@Pc(117) int next = digit + result * radix;
			if (next / radix != result) {
				throw new NumberFormatException();
			}
			result = next;
			hasDigit = true;
		}
		if (!hasDigit) {
			throw new NumberFormatException();
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ILclient!na;)Z")
	public final boolean equalsIgnoreCase(@OriginalArg(1) JagString other) {
		if (other == null) {
			return false;
		} else if (this.length == other.length) {
			for (@Pc(28) int i = 0; i < this.length; i++) {
				@Pc(41) byte c1 = this.chars[i];
				if (c1 >= 65 && c1 <= 90 || c1 >= -64 && c1 <= -34 && c1 != -41) {
					c1 = (byte) (c1 + 32);
				}
				@Pc(75) byte c2 = other.chars[i];
				if (c2 >= 65 && c2 <= 90 || c2 >= -64 && c2 <= -34 && c2 != -41) {
					c2 = (byte) (c2 + 32);
				}
				if (c1 != c2) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IILjava/awt/Graphics;B)V")
	public final void drawString(@OriginalArg(0) int y, @OriginalArg(1) int x, @OriginalArg(2) Graphics g) {
		@Pc(17) String str = new String(this.chars, 0, this.length, StandardCharsets.ISO_8859_1);
		g.drawString(str, x, y);
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!na;Z)Lclient!na;")
	public final JagString appendString(@OriginalArg(0) JagString other) {
		if (!this.mutable) {
			throw new IllegalArgumentException();
		}
		if (other.length + this.length > this.chars.length) {
			@Pc(31) int newCapacity;
			for (newCapacity = 1; newCapacity < other.length + this.length; newCapacity += newCapacity) {
			}
			@Pc(51) byte[] newChars = new byte[newCapacity];
			copy(this.chars, 0, newChars, 0, this.length);
			this.chars = newChars;
		}
		copy(other.chars, 0, this.chars, this.length, other.length);
		this.length += other.length;
		return this;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(I)Lclient!na;")
	public final JagString toLowerCase() {
		@Pc(14) JagString result = new JagString();
		result.length = this.length;
		result.chars = new byte[this.length];
		for (@Pc(29) int i = 0; i < this.length; i++) {
			@Pc(42) byte c = this.chars[i];
			if (c >= 65 && c <= 90 || c >= -64 && c <= -34 && c != -41) {
				c = (byte) (c + 32);
			}
			result.chars[i] = c;
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(I)Lclient!na;")
	public final JagString encodeMessage() {
		@Pc(7) byte state = 2;
		@Pc(11) JagString result = new JagString();
		result.length = this.length;
		result.chars = new byte[this.length];
		for (@Pc(28) int i = 0; i < this.length; i++) {
			@Pc(41) byte c = this.chars[i];
			if (c >= 97 && c <= 122 || !(c < -32 || c > -2 || c == -9)) {
				if (state == 2) {
					c = (byte) (c - 32);
				}
				state = 0;
			} else if (c >= 65 && c <= 90 || !(c < -64 || c > -34 || c == -41)) {
				if (state == 0) {
					c = (byte) (c + 32);
				}
				state = 0;
			} else if (c == 46 || c == 33 || c == 63) {
				state = 2;
			} else if (c != 32) {
				state = 1;
			} else if (state != 2) {
				state = 1;
			}
			result.chars[i] = c;
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(I)J")
	public final long longHashCode() {
		@Pc(1) long hash = 0L;
		for (@Pc(9) int i = 0; i < this.length; i++) {
			hash = (long) (this.chars[i] & 0xFF) + (hash << 5) - hash;
		}
		return hash;
	}

	@OriginalMember(owner = "client!na", name = "d", descriptor = "(I)I")
	public final int length() {
		return this.length;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ILclient!na;II)Lclient!na;")
	public final JagString appendSubstring(@OriginalArg(1) JagString src, @OriginalArg(2) int from, @OriginalArg(3) int to) {
		if (!this.mutable) {
			throw new IllegalArgumentException();
		} else if (from >= 0 && from <= to && to <= src.length) {
			if (this.length + to - from > this.chars.length) {
				@Pc(43) int newCapacity;
				for (newCapacity = 1; newCapacity < this.length + src.length; newCapacity += newCapacity) {
				}
				@Pc(63) byte[] newChars = new byte[newCapacity];
				copy(this.chars, 0, newChars, 0, this.length);
				this.chars = newChars;
			}
			copy(src.chars, from, this.chars, this.length, to - from);
			this.length += to - from;
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!na", name = "f", descriptor = "(I)Z")
	public final boolean isInt() {
		return this.isValidInt();
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(Z)Lclient!na;")
	public final JagString reverse() {
		@Pc(7) JagString result = new JagString();
		result.length = this.length;
		result.chars = new byte[result.length];
		for (@Pc(24) int i = 0; i < this.length; i++) {
			result.chars[this.length - i - 1] = this.chars[i];
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(B)Lclient!na;")
	public final JagString toTitleCase() {
		@Pc(9) JagString result = new JagString();
		result.length = this.length;
		result.chars = new byte[this.length];
		@Pc(20) boolean capitalize = true;
		for (@Pc(22) int i = 0; i < this.length; i++) {
			@Pc(41) byte c = this.chars[i];
			if (c == 95) {
				capitalize = true;
				result.chars[i] = 32;
			} else if (c >= 97 && c <= 122 && capitalize) {
				capitalize = false;
				result.chars[i] = (byte) (c - 32);
			} else {
				result.chars[i] = c;
				capitalize = false;
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(BLclient!na;)I")
	public final int compare(@OriginalArg(1) JagString other) {
		@Pc(12) int c1 = 0;
		@Pc(14) int c2 = 0;
		@Pc(17) int otherLen = other.length;
		@Pc(20) int thisLen = this.length;
		@Pc(23) int thisTotal = this.length;
		@Pc(26) int otherTotal = other.length;
		@Pc(28) int thisIdx = 0;
		@Pc(30) int otherIdx = 0;
		while (thisLen != 0 && otherLen != 0) {
			if (c1 == 156 || c1 == 230) {
				c1 = 101;
			} else if (c1 == 140 || c1 == 198) {
				c1 = 69;
			} else if (c1 == 223) {
				c1 = 115;
			} else {
				c1 = this.chars[thisIdx] & 0xFF;
				thisIdx++;
			}
			if (isLigature(c1)) {
				thisTotal++;
			} else {
				thisLen--;
			}
			if (c2 == 156 || c2 == 230) {
				c2 = 101;
			} else if (c2 == 140 || c2 == 198) {
				c2 = 69;
			} else if (c2 == 223) {
				c2 = 115;
			} else {
				c2 = other.chars[otherIdx] & 0xFF;
				otherIdx++;
			}
			if (isLigature(c2)) {
				otherTotal++;
			} else {
				otherLen--;
			}
			if (CHAR_SORT_ORDER[c1] < CHAR_SORT_ORDER[c2]) {
				return -1;
			}
			if (CHAR_SORT_ORDER[c2] < CHAR_SORT_ORDER[c1]) {
				return 1;
			}
		}
		if (otherTotal <= thisTotal) {
			return thisTotal > otherTotal ? 1 : 0;
		} else {
			return -1;
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Ljava/net/URL;Z)Ljava/net/URL;")
	public final URL toRelativeUrl(@OriginalArg(0) URL baseUrl) throws MalformedURLException {
		return new URL(baseUrl, new String(this.chars, 0, this.length));
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ZI)Lclient!na;")
	public final JagString concatChar(@OriginalArg(1) int ch) {
		if (ch <= 0 || ch > 255) {
			throw new IllegalArgumentException("invalid char");
		}
		@Pc(23) JagString result = new JagString();
		result.chars = new byte[this.length + 1];
		result.length = this.length + 1;
		copy(this.chars, 0, result.chars, 0, this.length);
		result.chars[this.length] = (byte) ch;
		return result;
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(Z)V")
	public final void print() {
		@Pc(16) String str = new String(this.chars, 0, this.length, StandardCharsets.ISO_8859_1);
		System.out.println(str);
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(BLclient!na;)Z")
	public final boolean endsWith(@OriginalArg(1) JagString suffix) {
		if (suffix.length > this.length) {
			return false;
		}
		@Pc(19) int start = this.length - suffix.length;
		for (@Pc(27) int i = 0; i < suffix.length; i++) {
			if (this.chars[start + i] != suffix.chars[i]) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!na;I)I")
	public final int indexOf(@OriginalArg(0) JagString needle) {
		return this.indexOf(needle, 0);
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(B)I")
	public final int parseInt() {
		return this.parseHexString(10);
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IZ)V")
	public final void setLength(@OriginalArg(0) int newLength) {
		if (!this.mutable) {
			throw new IllegalArgumentException();
		} else if (newLength < 0) {
			throw new IllegalArgumentException();
		} else {
			@Pc(30) int i;
			if (newLength > this.chars.length) {
				for (i = 1; i < newLength; i += i) {
				}
				@Pc(45) byte[] newChars = new byte[i];
				copy(this.chars, 0, newChars, 0, this.length);
				this.chars = newChars;
			}
			for (i = this.length; i < newLength; i++) {
				this.chars[i] = 32;
			}
			this.length = newLength;
		}
	}

	@OriginalMember(owner = "client!na", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		return new String(this.chars, 0, this.length, StandardCharsets.ISO_8859_1);
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ZLjava/applet/Applet;)V")
	public final void evalInBrowser(@OriginalArg(1) Applet applet) throws Throwable {
		@Pc(16) String str = new String(this.chars, 0, this.length);
		BrowserControl.eval(applet, str);
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(III)I")
	public final int indexOf(@OriginalArg(0) int ch, @OriginalArg(1) int fromIndex) {
		@Pc(4) byte b = (byte) ch;
		for (@Pc(15) int i = fromIndex; i < this.length; i++) {
			if (this.chars[i] == b) {
				return i;
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IB)Lclient!na;")
	public final JagString substring(@OriginalArg(0) int from) {
		return this.substring(this.length, from);
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(III)Lclient!na;")
	public final JagString substring(@OriginalArg(0) int to, @OriginalArg(2) int from) {
		@Pc(7) JagString result = new JagString();
		result.length = to - from;
		result.chars = new byte[to - from];
		copy(this.chars, from, result.chars, 0, result.length);
		return result;
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(Lclient!na;I)Z")
	public final boolean startsWith(@OriginalArg(0) JagString prefix) {
		if (this.length < prefix.length) {
			return false;
		}
		for (@Pc(19) int i = 0; i < prefix.length; i++) {
			if (this.chars[i] != prefix.chars[i]) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!na", name = "equals", descriptor = "(Ljava/lang/Object;)Z")
	@Override
	public final boolean equals(@OriginalArg(0) Object obj) {
		if (!(obj instanceof JagString)) {
			throw new IllegalArgumentException();
		}
		return this.strEquals((JagString) obj);
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(Lclient!na;I)I")
	public final int compareTo(@OriginalArg(0) JagString other) {
		@Pc(20) int minLen;
		if (other.length < this.length) {
			minLen = other.length;
		} else {
			minLen = this.length;
		}
		for (@Pc(27) int i = 0; i < minLen; i++) {
			if ((this.chars[i] & 0xFF) < (other.chars[i] & 0xFF)) {
				return -1;
			}
			if ((other.chars[i] & 0xFF) < (this.chars[i] & 0xFF)) {
				return 1;
			}
		}
		if (other.length > this.length) {
			return -1;
		} else if (other.length >= this.length) {
			return 0;
		} else {
			return 1;
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!na;ZLclient!na;)Lclient!na;")
	public final JagString replaceAll(@OriginalArg(0) JagString replacement, @OriginalArg(2) JagString search) {
		@Pc(8) int newLen = this.length;
		@Pc(14) int lenDiff = replacement.length - search.length;
		@Pc(16) int pos = 0;
		while (true) {
			@Pc(22) int idx = this.indexOf(search, pos);
			if (idx < 0) {
				pos = 0;
				@Pc(45) JagString result = allocate(newLen);
				while (true) {
					@Pc(51) int foundIdx = this.indexOf(search, pos);
					if (foundIdx < 0) {
						while (pos < this.length) {
							result.append(this.chars[pos++] & 0xFF);
						}
						return result;
					}
					while (pos < foundIdx) {
						result.append(this.chars[pos++] & 0xFF);
					}
					result.appendString(replacement);
					pos += search.length;
				}
			}
			pos = idx + search.length;
			newLen += lenDiff;
		}
	}

	@OriginalMember(owner = "client!na", name = "hashCode", descriptor = "()I")
	@Override
	public final int hashCode() {
		return this.getHash();
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(IZ)Z")
	private boolean isValidInt() {
		@Pc(18) boolean hasDigit = false;
		@Pc(24) boolean negative = false;
		@Pc(26) int result = 0;
		for (@Pc(28) int i = 0; i < this.length; i++) {
			@Pc(39) int digit = this.chars[i] & 0xFF;
			if (i == 0) {
				if (digit == 45) {
					negative = true;
					continue;
				}
				if (digit == 43) {
					continue;
				}
			}
			if (digit >= 48 && digit <= 57) {
				digit -= 48;
			} else if (digit >= 65 && digit <= 90) {
				digit -= 55;
			} else if (digit >= 97 && digit <= 122) {
				digit -= 87;
			} else {
				return false;
			}
			if (digit >= 10) {
				return false;
			}
			if (negative) {
				digit = -digit;
			}
			@Pc(110) int next = digit + result * 10;
			if (result != next / 10) {
				return false;
			}
			result = next;
			hasDigit = true;
		}
		return hasDigit;
	}

	@OriginalMember(owner = "client!na", name = "d", descriptor = "(BLclient!na;)Z")
	public final boolean startsWithIgnoreCase(@OriginalArg(1) JagString prefix) {
		if (this.length < prefix.length) {
			return false;
		}
		for (@Pc(21) int i = 0; i < prefix.length; i++) {
			@Pc(30) byte c1 = this.chars[i];
			@Pc(35) byte c2 = prefix.chars[i];
			if (c2 >= 65 && c2 <= 90 || c2 >= -64 && c2 <= -34 && c2 != -41) {
				c2 = (byte) (c2 + 32);
			}
			if (c1 >= 65 && c1 <= 90 || c1 >= -64 && c1 <= -34 && c1 != -41) {
				c1 = (byte) (c1 + 32);
			}
			if (c1 != c2) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!na", name = "g", descriptor = "(I)Lclient!na;")
	public final JagString asString() {
		return this;
	}

	@OriginalMember(owner = "client!na", name = "h", descriptor = "(I)Lclient!na;")
	public final JagString trim() {
		@Pc(17) int start;
		for (start = 0; start < this.length && (this.chars[start] >= 0 && this.chars[start] <= 32 || (this.chars[start] & 0xFF) == 160); start++) {
		}
		@Pc(53) int end;
		for (end = this.length; end > start && (this.chars[end - 1] >= 0 && this.chars[end - 1] <= 32 || (this.chars[end - 1] & 0xFF) == 160); end--) {
		}
		if (start == 0 && this.length == end) {
			return this;
		}
		@Pc(111) JagString result = new JagString();
		result.length = end - start;
		result.chars = new byte[result.length];
		for (@Pc(124) int i = 0; i < result.length; i++) {
			result.chars[i] = this.chars[start + i];
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(III)Lclient!na;")
	public final JagString replaceSlashWithSpace() {
		@Pc(8) JagString str = new JagString();
		str.length = this.length;
		str.chars = new byte[this.length];
		for (@Pc(31) int i = 0; i < this.length; i++) {
			@Pc(44) byte c = this.chars[i];
			if (c == 47) {
				str.chars[i] = 32;
			} else {
				str.chars[i] = c;
			}
		}
		return str;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!na;II)I")
	public final int indexOf(@OriginalArg(0) JagString needle, @OriginalArg(1) int fromIndex) {
		@Pc(8) int needleLen = needle.length;
		if (fromIndex >= this.length) {
			return needleLen == 0 ? this.length : -1;
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (needleLen == 0) {
			return fromIndex;
		}
		@Pc(41) int lastStart = this.length - needleLen;
		@Pc(44) byte[] needleChars = needle.chars;
		@Pc(48) byte firstChar = needleChars[0];
		for (@Pc(50) int i = fromIndex; i <= lastStart; i++) {
			if (firstChar != this.chars[i]) {
				do {
					i++;
					if (i > lastStart) {
						return -1;
					}
				} while (firstChar != this.chars[i]);
			}
			@Pc(88) boolean matched = true;
			@Pc(92) int j = i + 1;
			for (@Pc(94) int k = 1; k < needleLen; k++) {
				if (needleChars[k] != this.chars[j]) {
					matched = false;
					break;
				}
				j++;
			}
			if (matched) {
				return i;
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!na", name = "b", descriptor = "(IB)[Lclient!na;")
	public final JagString[] split(@OriginalArg(0) int delim) {
		@Pc(7) int matches = 0;
		for (@Pc(9) int i = 0; i < this.length; i++) {
			if (delim == this.chars[i]) {
				matches++;
			}
		}
		@Pc(37) JagString[] parts = new JagString[matches + 1];
		if (matches == 0) {
			parts[0] = this;
			return parts;
		}
		@Pc(47) int part = 0;
		@Pc(49) int start = 0;
		for (@Pc(51) int i = 0; i < matches; i++) {
			@Pc(68) int end;
			for (end = 0; delim != this.chars[end + start]; end++) {
			}
			parts[part++] = this.substring(start + end, start);
			start += end + 1;
		}
		parts[matches] = this.substring(this.length, start);
		return parts;
	}

	@OriginalMember(owner = "client!na", name = "i", descriptor = "(I)[B")
	public final byte[] toByteArray() {
		@Pc(7) byte[] bytes = new byte[this.length];
		copy(this.chars, 0, bytes, 0, this.length);
		return bytes;
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(IB)I")
	public final int charAt(@OriginalArg(0) int index) {
		return this.chars[index] & 0xFF;
	}

	@OriginalMember(owner = "client!na", name = "c", descriptor = "(B)Lclient!na;")
	public final JagString intern() {
		@Pc(9) long hash = this.longHashCode();
		@Pc(19) Class lock = JagString.class;
		synchronized (lock) {
			@Pc(30) StringNode node;
			if (internCache == null) {
				internCache = new HashTable(4096);
			} else {
				for (node = (StringNode) internCache.get(hash); node != null; node = (StringNode) internCache.nextWithKey()) {
					if (this.strEquals(node.value)) {
						return node.value;
					}
				}
			}
			node = new StringNode();
			node.value = this;
			this.mutable = false;
			internCache.put(node, hash);
			return this;
		}
	}

	@OriginalMember(owner = "client!na", name = "d", descriptor = "(IB)Lclient!na;")
	public final JagString append(@OriginalArg(0) int ch) {
		if (ch <= 0 || ch > 255) {
			throw new IllegalArgumentException("invalid char:" + ch);
		} else if (this.mutable) {
			if (this.length == this.chars.length) {
				@Pc(44) int newCapacity;
				for (newCapacity = 1; newCapacity <= this.length; newCapacity += newCapacity) {
				}
				@Pc(61) byte[] newChars = new byte[newCapacity];
				copy(this.chars, 0, newChars, 0, this.length);
				this.chars = newChars;
			}
			this.chars[this.length++] = (byte) ch;
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(BLjava/applet/Applet;)Lclient!na;")
	public final JagString fromParameters(@OriginalArg(1) Applet applet) {
		@Pc(19) String key = new String(this.chars, 0, this.length);
		@Pc(23) String value = applet.getParameter(key);
		return value == null ? null : of(value);
	}

	@OriginalMember(owner = "client!na", name = "d", descriptor = "(Z)I")
	public final int getHash() {
		@Pc(7) int hash = 0;
		for (@Pc(14) int c = 0; c < this.length; c++) {
			hash = (this.chars[c] & 0xFF) + (hash << 5) - hash;
		}
		return hash;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ILjava/awt/FontMetrics;)I")
	public final int stringWidth(@OriginalArg(1) FontMetrics metrics) {
		@Pc(14) String str = new String(this.chars, 0, this.length, StandardCharsets.ISO_8859_1);
		return metrics.stringWidth(str);
	}

	@OriginalMember(owner = "client!na", name = "d", descriptor = "(B)Lclient!na;")
	public final JagString compact() {
		if (!this.mutable) {
			throw new IllegalArgumentException();
		}
		if (this.chars.length != this.length) {
			@Pc(26) byte[] newChars = new byte[this.length];
			copy(this.chars, 0, newChars, 0, this.length);
			this.chars = newChars;
		}
		return this;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(ILjava/applet/Applet;)Ljava/lang/Object;")
	public final Object browserControlCall(@OriginalArg(1) Applet applet) throws Throwable {
		@Pc(12) String str = new String(this.chars, 0, this.length);
		@Pc(17) Object result = BrowserControl.call(str, applet);
		if (result instanceof String) {
			@Pc(24) byte[] bytes = ((String) result).getBytes();
			result = decodeString(bytes, bytes.length, 0);
		}
		return result;
	}

	@OriginalMember(owner = "client!na", name = "j", descriptor = "(I)J")
	public final long encode37() {
		@Pc(7) long hash = 0L;
		for (@Pc(14) int i = 0; this.length > i && i < 12; i++) {
			@Pc(32) byte c = this.chars[i];
			hash *= 37L;
			if (c >= 65 && c <= 90) {
				hash += c + 1 - 65;
			} else if (c >= 97 && c <= 122) {
				hash += c + 1 - 97;
			} else if (c >= 48 && c <= 57) {
				hash += c + 27 - 48;
			}
		}
		while (hash % 37L == 0L && hash != 0L) {
			hash /= 37L;
		}
		return hash;
	}

	@OriginalMember(owner = "client!na", name = "k", descriptor = "(I)Lclient!na;")
	public final JagString toBase37Name() {
		@Pc(9) JagString name = Base37.decode37(this.encode37());
		return name == null ? EMPTY_NAME : name;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Z[BIII)I")
	public final int encodeString(@OriginalArg(1) byte[] dest, @OriginalArg(2) int destOff, @OriginalArg(4) int len) {
		copy(this.chars, 0, dest, destOff, len);
		return len;
	}
}
