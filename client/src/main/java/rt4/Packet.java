package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!i")
public final class Packet extends Buffer {

	@OriginalMember(owner = "client!bh", name = "G", descriptor = "[I")
	public static final int[] BIT_MASKS = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};

	@OriginalMember(owner = "client!i", name = "Xb", descriptor = "Lclient!ij;")
	private IsaacRandom isaac;

	@OriginalMember(owner = "client!i", name = "fc", descriptor = "I")
	private int bitOffset;

	@OriginalMember(owner = "client!i", name = "<init>", descriptor = "(I)V")
	public Packet(@OriginalArg(0) int size) {
		super(size);
	}

	@OriginalMember(owner = "client!i", name = "q", descriptor = "(B)V")
	public final void accessBits() {
		this.bitOffset = this.offset * 8;
	}

	@OriginalMember(owner = "client!i", name = "a", descriptor = "(BI[BI)V")
	public final void gBytesIsaac(@OriginalArg(2) byte[] dest, @OriginalArg(3) int len) {
		for (@Pc(17) int i = 0; i < len; i++) {
			dest[i] = (byte) (this.data[this.offset++] - this.isaac.getNextKey());
		}
	}

	@OriginalMember(owner = "client!i", name = "f", descriptor = "(BI)I")
	public final int gBits(@OriginalArg(1) int numBits) {
		@Pc(6) int byteIndex = this.bitOffset >> 3;
		@Pc(14) int bitsRemaining = 8 - (this.bitOffset & 0x7);
		@Pc(16) int value = 0;
		this.bitOffset += numBits;
		while (bitsRemaining < numBits) {
			value += (BIT_MASKS[bitsRemaining] & this.data[byteIndex++]) << numBits - bitsRemaining;
			numBits -= bitsRemaining;
			bitsRemaining = 8;
		}
		if (bitsRemaining == numBits) {
			value += this.data[byteIndex] & BIT_MASKS[bitsRemaining];
		} else {
			value += this.data[byteIndex] >> bitsRemaining - numBits & BIT_MASKS[numBits];
		}
		return value;
	}

	@OriginalMember(owner = "client!i", name = "a", descriptor = "([IZ)V")
	public final void setKey(@OriginalArg(0) int[] key) {
		this.isaac = new IsaacRandom(key);
	}

	@OriginalMember(owner = "client!i", name = "q", descriptor = "(II)I")
	public final int availableBits(@OriginalArg(0) int byteLength) {
		return byteLength * 8 - this.bitOffset;
	}

	@OriginalMember(owner = "client!i", name = "r", descriptor = "(II)V")
	public final void p1isaac(@OriginalArg(1) int value) {
		this.data[this.offset++] = (byte) (value + this.isaac.getNextKey());
	}

	@OriginalMember(owner = "client!i", name = "s", descriptor = "(I)I")
	public final int g1isaac() {
		return this.data[this.offset++] - this.isaac.getNextKey() & 0xFF;
	}

	@OriginalMember(owner = "client!i", name = "h", descriptor = "(Z)V")
	public final void accessBytes() {
		this.offset = (this.bitOffset + 7) / 8;
	}
}
