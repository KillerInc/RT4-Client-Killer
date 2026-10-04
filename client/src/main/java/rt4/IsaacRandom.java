package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ij")
public final class IsaacRandom {

	@OriginalMember(owner = "client!ij", name = "b", descriptor = "I")
	private int c;

	@OriginalMember(owner = "client!ij", name = "d", descriptor = "I")
	private int b;

	@OriginalMember(owner = "client!ij", name = "f", descriptor = "I")
	private int count;

	@OriginalMember(owner = "client!ij", name = "i", descriptor = "[I")
	private final int[] rsl = new int[256];

	@OriginalMember(owner = "client!ij", name = "k", descriptor = "[I")
	private final int[] mem = new int[256];

	@OriginalMember(owner = "client!ij", name = "l", descriptor = "I")
	private int a;

	@OriginalMember(owner = "client!ij", name = "<init>", descriptor = "([I)V")
	public IsaacRandom(@OriginalArg(0) int[] seed) {
		for (@Pc(13) int i = 0; i < seed.length; i++) {
			this.rsl[i] = seed[i];
		}
		this.init();
	}

	public static final int GOLDEN_RATIO = 0x9e3779b9;

	@OriginalMember(owner = "client!ij", name = "a", descriptor = "(Z)V")
	private void init() {
		@Pc(14) int s0 = GOLDEN_RATIO;
		@Pc(16) int s1 = GOLDEN_RATIO;
		@Pc(18) int s2 = GOLDEN_RATIO;
		@Pc(20) int s3 = GOLDEN_RATIO;
		@Pc(22) int s4 = GOLDEN_RATIO;
		@Pc(24) int s5 = GOLDEN_RATIO;
		@Pc(26) int s6 = GOLDEN_RATIO;
		@Pc(27) int s7 = GOLDEN_RATIO;
		@Pc(29) int i;
		for (i = 0; i < 4; i++) {
			s7 ^= s6 << 11;
			s4 += s7;
			s6 += s5;
			s6 ^= s5 >>> 2;
			s5 += s4;
			s5 ^= s4 << 8;
			s2 += s5;
			s3 += s6;
			s4 += s3;
			s4 ^= s3 >>> 16;
			s3 += s2;
			s1 += s4;
			s3 ^= s2 << 10;
			s0 += s3;
			s2 += s1;
			s2 ^= s1 >>> 4;
			s1 += s0;
			s1 ^= s0 << 8;
			s6 += s1;
			s7 += s2;
			s0 += s7;
			s0 ^= s7 >>> 9;
			s5 += s0;
			s7 += s6;
		}
		for (i = 0; i < 256; i += 8) {
			s4 += this.rsl[i + 3];
			s3 += this.rsl[i + 4];
			s1 += this.rsl[i + 6];
			s7 += this.rsl[i];
			s5 += this.rsl[i + 2];
			s2 += this.rsl[i + 5];
			s0 += this.rsl[i + 7];
			s6 += this.rsl[i + 1];
			s7 ^= s6 << 11;
			s6 += s5;
			s6 ^= s5 >>> 2;
			s4 += s7;
			s5 += s4;
			s5 ^= s4 << 8;
			s3 += s6;
			s4 += s3;
			s4 ^= s3 >>> 16;
			s2 += s5;
			s3 += s2;
			s3 ^= s2 << 10;
			s0 += s3;
			s1 += s4;
			s2 += s1;
			s2 ^= s1 >>> 4;
			s1 += s0;
			s1 ^= s0 << 8;
			s7 += s2;
			s0 += s7;
			s6 += s1;
			s0 ^= s7 >>> 9;
			s7 += s6;
			this.mem[i] = s7;
			this.mem[i + 1] = s6;
			s5 += s0;
			this.mem[i + 2] = s5;
			this.mem[i + 3] = s4;
			this.mem[i + 4] = s3;
			this.mem[i + 5] = s2;
			this.mem[i + 6] = s1;
			this.mem[i + 7] = s0;
		}
		for (i = 0; i < 256; i += 8) {
			s1 += this.mem[i + 6];
			s2 += this.mem[i + 5];
			s3 += this.mem[i + 4];
			s6 += this.mem[i + 1];
			s5 += this.mem[i + 2];
			s7 += this.mem[i];
			s7 ^= s6 << 11;
			s4 += this.mem[i + 3];
			s0 += this.mem[i + 7];
			s4 += s7;
			s6 += s5;
			s6 ^= s5 >>> 2;
			s5 += s4;
			s3 += s6;
			s5 ^= s4 << 8;
			s2 += s5;
			s4 += s3;
			s4 ^= s3 >>> 16;
			s3 += s2;
			s3 ^= s2 << 10;
			s1 += s4;
			s2 += s1;
			s2 ^= s1 >>> 4;
			s0 += s3;
			s1 += s0;
			s7 += s2;
			s1 ^= s0 << 8;
			s6 += s1;
			s0 += s7;
			s0 ^= s7 >>> 9;
			s5 += s0;
			s7 += s6;
			this.mem[i] = s7;
			this.mem[i + 1] = s6;
			this.mem[i + 2] = s5;
			this.mem[i + 3] = s4;
			this.mem[i + 4] = s3;
			this.mem[i + 5] = s2;
			this.mem[i + 6] = s1;
			this.mem[i + 7] = s0;
		}
		this.isaac();
		this.count = 256;
	}

	@OriginalMember(owner = "client!ij", name = "a", descriptor = "(I)I")
	public final int getNextKey() {
		if (GlobalConfig.USE_ISAAC) {
			if (this.count-- == 0) {
				this.isaac();
				this.count = 255;
			}
			return this.rsl[this.count];
		} else {
			return 0;
		}
	}

	@OriginalMember(owner = "client!ij", name = "b", descriptor = "(I)V")
	private void isaac() {
		this.b += ++this.c;
		for (@Pc(17) int i = 0; i < 256; i++) {
			@Pc(33) int x = this.mem[i];
			if ((i & 0x2) == 0) {
				if ((i & 0x1) == 0) {
					this.a ^= this.a << 13;
				} else {
					this.a ^= this.a >>> 6;
				}
			} else if ((i & 0x1) == 0) {
				this.a ^= this.a << 2;
			} else {
				this.a ^= this.a >>> 16;
			}
			this.a += this.mem[i + 128 & 0xFF];
			@Pc(119) int y;
			this.mem[i] = y = this.b + this.a + this.mem[x >> 2 & 0xFF];
			this.rsl[i] = this.b = x + this.mem[y >> 8 >> 2 & 0xFF];
		}
	}
}
