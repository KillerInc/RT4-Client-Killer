package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!om")
public final class NanoTimer extends Timer {

	@OriginalMember(owner = "client!om", name = "k", descriptor = "J")
	private long nextTickNano = System.nanoTime();

	@OriginalMember(owner = "client!om", name = "b", descriptor = "(I)V")
	@Override
	public final void reset() {
		this.nextTickNano = System.nanoTime();
	}

	@OriginalMember(owner = "client!om", name = "a", descriptor = "(III)I")
	@Override
	public final int sleep(@OriginalArg(1) int minSleep, @OriginalArg(2) int tickInterval) {
		@Pc(9) long minSleepNano = (long) minSleep * 1000000L;
		@Pc(14) long sleepNano = this.nextTickNano - System.nanoTime();
		if (minSleepNano > sleepNano) {
			sleepNano = minSleepNano;
		}
		ThreadUtils.sleep(sleepNano / 1000000L);
		@Pc(31) int ticks = 0;
		@Pc(33) long now = System.nanoTime();
		while (ticks < 10 && (ticks < 1 || this.nextTickNano < now)) {
			ticks++;
			this.nextTickNano += (long) tickInterval * 1000000L;
		}
		if (now > this.nextTickNano) {
			this.nextTickNano = now;
		}
		return ticks;
	}

	@Override
	public int count(int minSleep, int tickInterval) {
		@Pc(9) long minSleepNano = (long) minSleep * 1000000L;
		@Pc(14) long sleepNano = this.nextTickNano - System.nanoTime();
		if (minSleepNano > sleepNano) {
			sleepNano = minSleepNano;
		}
		@Pc(31) int ticks = 0;
		@Pc(33) long now = System.nanoTime();
		while (ticks < 10 && (ticks < 1 || this.nextTickNano < now)) {
			ticks++;
			this.nextTickNano += (long) tickInterval * 1000000L;
		}
		if (now > this.nextTickNano) {
			this.nextTickNano = now;
		}
		return ticks;
	}
}
