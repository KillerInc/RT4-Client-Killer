package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!lj")
public final class MillisTimer extends Timer {

	@OriginalMember(owner = "client!lj", name = "o", descriptor = "I")
	private int sampleIndex;

	@OriginalMember(owner = "client!lj", name = "x", descriptor = "[J")
	private final long[] sampleTimes = new long[10];

	@OriginalMember(owner = "client!lj", name = "r", descriptor = "I")
	private int tickRate = 256;

	@OriginalMember(owner = "client!lj", name = "u", descriptor = "I")
	private int sleepTime = 1;

	@OriginalMember(owner = "client!lj", name = "v", descriptor = "I")
	private int tickAccumulator = 0;

	@OriginalMember(owner = "client!lj", name = "k", descriptor = "J")
	private long currentTime = MonotonicClock.currentTimeMillis();

	@OriginalMember(owner = "client!lj", name = "<init>", descriptor = "()V")
	public MillisTimer() {
		for (@Pc(22) int i = 0; i < 10; i++) {
			this.sampleTimes[i] = this.currentTime;
		}
	}

	@OriginalMember(owner = "client!lj", name = "b", descriptor = "(I)V")
	@Override
	public final void reset() {
		for (@Pc(7) int i = 0; i < 10; i++) {
			this.sampleTimes[i] = 0L;
		}
	}

	@OriginalMember(owner = "client!lj", name = "a", descriptor = "(III)I")
	@Override
	public final int sleep(@OriginalArg(1) int minSleep, @OriginalArg(2) int maxSleep) {
		@Pc(17) int prevSleepTime = this.sleepTime;
		@Pc(20) int prevTickRate = this.tickRate;
		this.tickRate = 300;
		this.sleepTime = 1;
		this.currentTime = MonotonicClock.currentTimeMillis();
		if (this.sampleTimes[this.sampleIndex] == 0L) {
			this.tickRate = prevTickRate;
			this.sleepTime = prevSleepTime;
		} else if (this.sampleTimes[this.sampleIndex] < this.currentTime) {
			this.tickRate = (int) ((long) (maxSleep * 2560) / (this.currentTime - this.sampleTimes[this.sampleIndex]));
		}
		if (this.tickRate < 25) {
			this.tickRate = 25;
		}
		if (this.tickRate > 256) {
			this.tickRate = 256;
			this.sleepTime = (int) ((long) maxSleep - (this.currentTime - this.sampleTimes[this.sampleIndex]) / 10L);
		}
		if (maxSleep < this.sleepTime) {
			this.sleepTime = maxSleep;
		}
		this.sampleTimes[this.sampleIndex] = this.currentTime;
		this.sampleIndex = (this.sampleIndex + 1) % 10;
		@Pc(139) int i;
		if (this.sleepTime > 1) {
			for (i = 0; i < 10; i++) {
				if (this.sampleTimes[i] != 0L) {
					this.sampleTimes[i] += this.sleepTime;
				}
			}
		}
		if (minSleep > this.sleepTime) {
			this.sleepTime = minSleep;
		}
		ThreadUtils.sleep(this.sleepTime);
		i = 0;
		while (this.tickAccumulator < 256) {
			this.tickAccumulator += this.tickRate;
			i++;
		}
		this.tickAccumulator &= 0xFF;
		return i;
	}

	public int count(int minSleep, int maxSleep) {
		return 1;
	}
}
