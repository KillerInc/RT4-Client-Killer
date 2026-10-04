package rt4;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class PlayerSkillXpTable {

	@OriginalMember(owner = "client!ud", name = "T", descriptor = "[I")
	public static final int[] updatedStats = new int[32];

	@OriginalMember(owner = "client!lb", name = "p", descriptor = "[I")
	public static final int[] baseLevels = new int[25];

	@OriginalMember(owner = "client!hk", name = "fb", descriptor = "[I")
	public static final int[] boostedLevels = new int[25];

	@OriginalMember(owner = "client!sg", name = "b", descriptor = "[I")
	public static final int[] experience = new int[25];

	@OriginalMember(owner = "client!h", name = "S", descriptor = "[I")
	public static final int[] xpLevelLookup = new int[99];

	@OriginalMember(owner = "client!oj", name = "z", descriptor = "[Z")
	public static final boolean[] ENABLED_SKILLS = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false};

	@OriginalMember(owner = "client!ha", name = "m", descriptor = "I")
	public static int updatedStatsWriterIndex = 0;

	static {
		@Pc(4) int totalXp = 0;
		for (@Pc(6) int level = 0; level < 99; level++) {
			@Pc(13) int nextLevel = level + 1;
			@Pc(26) int xpForLevel = (int) (Math.pow(2.0D, (double) nextLevel / 7.0D) * 300.0D + (double) nextLevel);
			totalXp += xpForLevel;
			PlayerSkillXpTable.xpLevelLookup[level] = totalXp / 4;
		}
	}

}
