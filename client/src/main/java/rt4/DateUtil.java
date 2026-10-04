package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtil {

	@OriginalMember(owner = "client!cl", name = "K", descriptor = "Ljava/util/Calendar;")
	public static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

	@OriginalMember(owner = "client!km", name = "tc", descriptor = "Lclient!na;")
	private static final JagString DEC = JagString.parse("Dec");
	@OriginalMember(owner = "client!km", name = "vc", descriptor = "Lclient!na;")
	private static final JagString JUL = JagString.parse("Jul");
	@OriginalMember(owner = "client!km", name = "xc", descriptor = "Lclient!na;")
	private static final JagString MAY = JagString.parse("May");
	@OriginalMember(owner = "client!km", name = "yc", descriptor = "Lclient!na;")
	private static final JagString NOV = JagString.parse("Nov");
	@OriginalMember(owner = "client!km", name = "zc", descriptor = "Lclient!na;")
	private static final JagString MAR = JagString.parse("Mar");
	@OriginalMember(owner = "client!km", name = "Gc", descriptor = "Lclient!na;")
	private static final JagString JAN = JagString.parse("Jan");
	@OriginalMember(owner = "client!km", name = "Hc", descriptor = "Lclient!na;")
	private static final JagString FEB = JagString.parse("Feb");
	@OriginalMember(owner = "client!km", name = "Tc", descriptor = "Lclient!na;")
	private static final JagString APR = JagString.parse("Apr");
	@OriginalMember(owner = "client!km", name = "Wc", descriptor = "Lclient!na;")
	private static final JagString JUN = JagString.parse("Jun");
	@OriginalMember(owner = "client!km", name = "Qc", descriptor = "Lclient!na;")
	private static final JagString AUG = JagString.parse("Aug");
	@OriginalMember(owner = "client!km", name = "cd", descriptor = "Lclient!na;")
	private static final JagString SEP = JagString.parse("Sep");
	@OriginalMember(owner = "client!km", name = "dd", descriptor = "Lclient!na;")
	private static final JagString OCT = JagString.parse("Oct");
	@OriginalMember(owner = "client!km", name = "Ac", descriptor = "[Lclient!na;")
	public static final JagString[] MONTHS = new JagString[]{JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};

	@OriginalMember(owner = "client!rl", name = "T", descriptor = "Lclient!na;")
	private static final JagString SAT = JagString.parse("Sat");
	@OriginalMember(owner = "client!rl", name = "U", descriptor = "Lclient!na;")
	private static final JagString MON = JagString.parse("Mon");
	@OriginalMember(owner = "client!rl", name = "W", descriptor = "Lclient!na;")
	private static final JagString FRI = JagString.parse("Fri");
	@OriginalMember(owner = "client!rl", name = "db", descriptor = "Lclient!na;")
	private static final JagString SUN = JagString.parse("Sun");
	@OriginalMember(owner = "client!rl", name = "cb", descriptor = "Lclient!na;")
	private static final JagString TUE = JagString.parse("Tue");
	@OriginalMember(owner = "client!rl", name = "ab", descriptor = "Lclient!na;")
	private static final JagString WED = JagString.parse("Wed");
	@OriginalMember(owner = "client!rl", name = "bb", descriptor = "Lclient!na;")
	private static final JagString THU = JagString.parse("Thu");
	@OriginalMember(owner = "client!rl", name = "Y", descriptor = "[Lclient!na;")
	public static final JagString[] DAYS = new JagString[]{SUN, MON, TUE, WED, THU, FRI, SAT};

	@OriginalMember(owner = "client!ck", name = "T", descriptor = "Lclient!na;")
	private static final JagString JAN_2 = JagString.parse("Jan");
	@OriginalMember(owner = "client!ck", name = "hb", descriptor = "Lclient!na;")
	private static final JagString FEB_2 = JagString.parse("Feb");
	@OriginalMember(owner = "client!ck", name = "gb", descriptor = "Lclient!na;")
	private static final JagString MAR_2 = JagString.parse("Mar");
	@OriginalMember(owner = "client!ck", name = "db", descriptor = "Lclient!na;")
	private static final JagString APR_2 = JagString.parse("Apr");
	@OriginalMember(owner = "client!ck", name = "n", descriptor = "Lclient!na;")
	private static final JagString MAY_2 = JagString.parse("May");
	@OriginalMember(owner = "client!ck", name = "L", descriptor = "Lclient!na;")
	private static final JagString JUN_2 = JagString.parse("Jun");
	@OriginalMember(owner = "client!ck", name = "B", descriptor = "Lclient!na;")
	private static final JagString JUL_2 = JagString.parse("Jul");
	@OriginalMember(owner = "client!ck", name = "l", descriptor = "Lclient!na;")
	private static final JagString AUG_2 = JagString.parse("Aug");
	@OriginalMember(owner = "client!ck", name = "V", descriptor = "Lclient!na;")
	private static final JagString SEP_2 = JagString.parse("Sep");
	@OriginalMember(owner = "client!ck", name = "S", descriptor = "Lclient!na;")
	private static final JagString OCT_2 = JagString.parse("Oct");
	@OriginalMember(owner = "client!ck", name = "Y", descriptor = "Lclient!na;")
	private static final JagString NOV_2 = JagString.parse("Nov");
	@OriginalMember(owner = "client!ck", name = "O", descriptor = "Lclient!na;")
	private static final JagString DEC_2 = JagString.parse("Dec");
	@OriginalMember(owner = "client!ck", name = "f", descriptor = "[Lclient!na;")
	public static final JagString[] SCRIPT_MONTHS = new JagString[]{JAN_2, FEB_2, MAR_2, APR_2, MAY_2, JUN_2, JUL_2, AUG_2, SEP_2, OCT_2, NOV_2, DEC_2};

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(JB)Lclient!na;")
	public static JagString getDateString(@OriginalArg(0) long date) {
		calendar.setTime(new Date(date));
		@Pc(13) int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		@Pc(17) int day = calendar.get(Calendar.DATE);
		@Pc(21) int month = calendar.get(Calendar.MONTH);
		@Pc(32) int year = calendar.get(Calendar.YEAR);
		@Pc(36) int hour = calendar.get(Calendar.HOUR_OF_DAY);
		@Pc(40) int minute = calendar.get(Calendar.MINUTE);
		@Pc(44) int second = calendar.get(Calendar.SECOND);
		return JagString.concatenate(new JagString[]{DAYS[dayOfWeek - 1], JagString.COMMA_SPACE, JagString.parseInt(day / 10), JagString.parseInt(day % 10), JagString.HYPHEN, MONTHS[month], JagString.HYPHEN, JagString.parseInt(year), JagString.SPACE, JagString.parseInt(hour / 10), JagString.parseInt(hour % 10), JagString.COLON, JagString.parseInt(minute / 10), JagString.parseInt(minute % 10), JagString.COLON, JagString.parseInt(second / 10), JagString.parseInt(second % 10), JagString.TIMEZONE});
	}
}
