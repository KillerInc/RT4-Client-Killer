package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fi")
public final class HuffmanCodec {

	@OriginalMember(owner = "client!fi", name = "b", descriptor = "[I")
	private int[] symbolTree;

	@OriginalMember(owner = "client!fi", name = "h", descriptor = "[I")
	private final int[] codewords;

	@OriginalMember(owner = "client!fi", name = "e", descriptor = "[B")
	private final byte[] bits;

	@OriginalMember(owner = "client!fi", name = "<init>", descriptor = "([B)V")
	public HuffmanCodec(@OriginalArg(0) byte[] bits) {
		@Pc(6) int[] nextCodewords = new int[33];
		@Pc(9) int symbols = bits.length;
		this.symbolTree = new int[8];
		this.codewords = new int[symbols];
		this.bits = bits;
		@Pc(22) int nextNode = 0;
		for (@Pc(24) int symbol = 0; symbol < symbols; symbol++) {
			@Pc(35) byte codewordBits = bits[symbol];
			if (codewordBits != 0) {
				@Pc(49) int bit = 0x1 << 32 - codewordBits;
				@Pc(53) int codeword = nextCodewords[codewordBits];
				this.codewords[symbol] = codeword;
				@Pc(121) int nextCodeword;
				@Pc(67) int i;
				@Pc(76) int parentCodeword;
				@Pc(92) int bitMask;
				if ((codeword & bit) == 0) {
					for (i = codewordBits - 1; i >= 1; i--) {
						parentCodeword = nextCodewords[i];
						if (codeword != parentCodeword) {
							break;
						}
						bitMask = 0x1 << 32 - i;
						if ((parentCodeword & bitMask) != 0) {
							nextCodewords[i] = nextCodewords[i - 1];
							break;
						}
						nextCodewords[i] = bitMask | parentCodeword;
					}
					nextCodeword = codeword | bit;
				} else {
					nextCodeword = nextCodewords[codewordBits - 1];
				}
				nextCodewords[codewordBits] = nextCodeword;
				for (i = codewordBits + 1; i <= 32; i++) {
					if (codeword == nextCodewords[i]) {
						nextCodewords[i] = nextCodeword;
					}
				}
				i = 0;
				for (parentCodeword = 0; parentCodeword < codewordBits; parentCodeword++) {
					bitMask = Integer.MIN_VALUE >>> parentCodeword;
					if ((codeword & bitMask) == 0) {
						i++;
					} else {
						if (this.symbolTree[i] == 0) {
							this.symbolTree[i] = nextNode;
						}
						i = this.symbolTree[i];
					}
					if (this.symbolTree.length <= i) {
						@Pc(206) int[] newTree = new int[this.symbolTree.length * 2];
						for (@Pc(208) int k = 0; k < this.symbolTree.length; k++) {
							newTree[k] = this.symbolTree[k];
						}
						this.symbolTree = newTree;
					}
				}
				this.symbolTree[i] = ~symbol;
				if (i >= nextNode) {
					nextNode = i + 1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(II[B[BII)I")
	public final int encode(@OriginalArg(0) int srcEnd, @OriginalArg(2) byte[] dest, @OriginalArg(3) byte[] src, @OriginalArg(4) int srcOff, @OriginalArg(5) int destOff) {
		@Pc(5) int end = srcEnd;
		@Pc(11) int carry = 0;
		@Pc(15) int bitPos = destOff << 3;
		while (end > srcOff) {
			@Pc(24) int value = src[srcOff] & 0xFF;
			@Pc(29) int codeword = this.codewords[value];
			@Pc(34) byte codeLen = this.bits[value];
			if (codeLen == 0) {
				throw new RuntimeException("No codeword for data value " + value);
			}
			@Pc(54) int bytePos = bitPos >> 3;
			@Pc(58) int bitShift = bitPos & 0x7;
			bitPos += codeLen;
			@Pc(72) int endBytePos = bytePos + (bitShift + codeLen - 1 >> 3);
			carry &= -bitShift >> 31;
			@Pc(80) int shift = bitShift + 24;
			dest[bytePos] = (byte) (carry |= codeword >>> shift);
			if (bytePos < endBytePos) {
				bytePos++;
				bitShift = shift - 8;
				dest[bytePos] = (byte) (carry = codeword >>> bitShift);
				if (bytePos < endBytePos) {
					bitShift -= 8;
					bytePos++;
					dest[bytePos] = (byte) (carry = codeword >>> bitShift);
					if (endBytePos > bytePos) {
						bitShift -= 8;
						bytePos++;
						dest[bytePos] = (byte) (carry = codeword >>> bitShift);
						if (endBytePos > bytePos) {
							bytePos++;
							bitShift -= 8;
							dest[bytePos] = (byte) (carry = codeword << -bitShift);
						}
					}
				}
			}
			srcOff++;
		}
		return (bitPos + 7 >> 3) - destOff;
	}

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(II[BI[BI)I")
	public final int decode(@OriginalArg(0) int destOff, @OriginalArg(1) int count, @OriginalArg(2) byte[] dest, @OriginalArg(4) byte[] src, @OriginalArg(5) int srcOff) {
		if (count == 0) {
			return 0;
		}
		@Pc(15) int node = 0;
		@Pc(19) int destEnd = count;
		@Pc(21) int srcPos = srcOff;
		while (true) {
			@Pc(25) byte bits = src[srcPos];
			if (bits < 0) {
				node = this.symbolTree[node];
			} else {
				node++;
			}
			@Pc(41) int sym0;
			if ((sym0 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym0;
				if (destOff >= destEnd) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x40) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(78) int sym1;
			if ((sym1 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym1;
				if (destEnd <= destOff) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x20) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(118) int sym2;
			if ((sym2 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym2;
				if (destEnd <= destOff) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x10) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(156) int sym3;
			if ((sym3 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym3;
				if (destOff >= destEnd) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x8) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(195) int sym4;
			if ((sym4 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym4;
				if (destEnd <= destOff) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x4) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(233) int sym5;
			if ((sym5 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym5;
				if (destOff >= destEnd) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x2) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(276) int sym6;
			if ((sym6 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym6;
				if (destOff >= destEnd) {
					break;
				}
				node = 0;
			}
			if ((bits & 0x1) == 0) {
				node++;
			} else {
				node = this.symbolTree[node];
			}
			@Pc(318) int sym7;
			if ((sym7 = this.symbolTree[node]) < 0) {
				dest[destOff++] = (byte) ~sym7;
				if (destOff >= destEnd) {
					break;
				}
				node = 0;
			}
			srcPos++;
		}
		return srcPos + 1 - srcOff;
	}
}
