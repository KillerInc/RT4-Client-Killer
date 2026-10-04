package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class StringUtils {
	@OriginalMember(owner = "client!ag", name = "ab", descriptor = "Lclient!na;")
	public static final JagString ZERO_STRING = JagString.parse("0");
	@OriginalMember(owner = "client!ca", name = "cb", descriptor = "Lclient!na;")
	public static final JagString nonBreakingSpace = JagString.getNbsp();
	@OriginalMember(owner = "client!rm", name = "i", descriptor = "Lclient!na;")
	public static final JagString ASTERISK = JagString.parse("(Z");
	@OriginalMember(owner = "client!fm", name = "W", descriptor = "Lclient!na;")
	public static final JagString MINUS_SIGN = JagString.parse(")2");
	@OriginalMember(owner = "client!cg", name = "h", descriptor = "Lclient!na;")
	public static final JagString PERIOD = JagString.parse(")3");
	@OriginalMember(owner = "client!tl", name = "i", descriptor = "Lclient!na;")
	public static final JagString COMMA = JagString.parse(")1");

	@OriginalMember(owner = "client!vf", name = "a", descriptor = "(IB)Lclient!na;")
	public static JagString toString(@OriginalArg(0) int value) {
		return value >= 999999999 ? ASTERISK : JagString.parseInt(value);
	}

	@OriginalMember(owner = "client!oj", name = "a", descriptor = "(IZIJI)Lclient!na;")
	public static JagString formatNumber(@OriginalArg(0) int locale, @OriginalArg(1) boolean useGrouping, @OriginalArg(2) int decimalPlaces, @OriginalArg(3) long value) {
		@Pc(9) JagString sign = JagString.allocate(0);
		if (value < 0L) {
			value = -value;
			sign.appendString(MINUS_SIGN);
		}
		@Pc(26) JagString groupSep = COMMA;
		@Pc(28) JagString decimalSep = PERIOD;
		if (locale == 1) {
			groupSep = PERIOD;
			decimalSep = COMMA;
		}
		if (locale == 2) {
			decimalSep = COMMA;
			groupSep = nonBreakingSpace;
		}
		if (locale == 3) {
			groupSep = PERIOD;
			decimalSep = COMMA;
		}
		@Pc(59) JagString fractionalPart = JagString.allocate(0);
		@Pc(61) int digitCount;
		for (digitCount = 0; digitCount < decimalPlaces; digitCount++) {
			fractionalPart.appendString(JagString.parseInt((int) (value % 10L)));
			value /= 10L;
		}
		digitCount = 0;
		@Pc(137) JagString integerPart;
		if (value == 0L) {
			integerPart = ZERO_STRING;
		} else {
			@Pc(95) JagString digits = JagString.allocate(0);
			while (value > 0L) {
				if (useGrouping && digitCount != 0 && digitCount % 3 == 0) {
					digits.appendString(groupSep);
				}
				digits.appendString(JagString.parseInt((int) (value % 10L)));
				digitCount++;
				value /= 10L;
			}
			integerPart = digits;
		}
		if (fractionalPart.length() > 0) {
			fractionalPart.appendString(decimalSep);
		}
		return JagString.concatenate(new JagString[]{sign, integerPart.reverse(), fractionalPart.reverse()});
	}
}
