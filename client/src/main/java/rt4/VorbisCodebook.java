package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ji")
public final class VorbisCodebook {

	@OriginalMember(owner = "client!ji", name = "f", descriptor = "[I")
	private int[] entryTree;

	@OriginalMember(owner = "client!ji", name = "c", descriptor = "I")
	public final int dimensions;

	@OriginalMember(owner = "client!ji", name = "e", descriptor = "I")
	private final int entries;

	@OriginalMember(owner = "client!ji", name = "a", descriptor = "[I")
	private final int[] lengths;

	@OriginalMember(owner = "client!ji", name = "d", descriptor = "[I")
	private int[] multiplicands;

	@OriginalMember(owner = "client!ji", name = "b", descriptor = "[[F")
	private float[][] valueVector;

	@OriginalMember(owner = "client!ji", name = "<init>", descriptor = "()V")
	public VorbisCodebook() {
		VorbisSound.readBits(24);
		this.dimensions = VorbisSound.readBits(16);
		this.entries = VorbisSound.readBits(24);
		this.lengths = new int[this.entries];
		@Pc(23) boolean ordered = VorbisSound.readBit() != 0;
		@Pc(27) int currentEntry;
		@Pc(32) int currentLength;
		@Pc(46) int j;
		if (ordered) {
			currentEntry = 0;
			currentLength = VorbisSound.readBits(5) + 1;
			while (currentEntry < this.entries) {
				@Pc(44) int count = VorbisSound.readBits(IntUtils.bitCount(this.entries - currentEntry));
				for (j = 0; j < count; j++) {
					this.lengths[currentEntry++] = currentLength;
				}
				currentLength++;
			}
		} else {
			@Pc(66) boolean sparse = VorbisSound.readBit() != 0;
			for (currentLength = 0; currentLength < this.entries; currentLength++) {
				if (sparse && VorbisSound.readBit() == 0) {
					this.lengths[currentLength] = 0;
				} else {
					this.lengths[currentLength] = VorbisSound.readBits(5) + 1;
				}
			}
		}
		this.createEntryTree();
		currentEntry = VorbisSound.readBits(4);
		if (currentEntry > 0) {
			@Pc(103) float minimumValue = VorbisSound.float32Unpack(VorbisSound.readBits(32));
			@Pc(107) float deltaValue = VorbisSound.float32Unpack(VorbisSound.readBits(32));
			j = VorbisSound.readBits(4) + 1;
			@Pc(118) boolean sequenceP = VorbisSound.readBit() != 0;
			@Pc(127) int lookupValues;
			if (currentEntry == 1) {
				lookupValues = lookup1Values(this.entries, this.dimensions);
			} else {
				lookupValues = this.entries * this.dimensions;
			}
			this.multiplicands = new int[lookupValues];
			@Pc(140) int i;
			for (i = 0; i < lookupValues; i++) {
				this.multiplicands[i] = VorbisSound.readBits(j);
			}
			this.valueVector = new float[this.entries][this.dimensions];
			@Pc(169) float last;
			@Pc(171) int indexDivisor;
			@Pc(173) int dim;
			if (currentEntry == 1) {
				for (i = 0; i < this.entries; i++) {
					last = 0.0F;
					indexDivisor = 1;
					for (dim = 0; dim < this.dimensions; dim++) {
						@Pc(183) int lookupOffset = i / indexDivisor % lookupValues;
						@Pc(195) float value = (float) this.multiplicands[lookupOffset] * deltaValue + minimumValue + last;
						this.valueVector[i][dim] = value;
						if (sequenceP) {
							last = value;
						}
						indexDivisor *= lookupValues;
					}
				}
			} else {
				for (i = 0; i < this.entries; i++) {
					last = 0.0F;
					indexDivisor = i * this.dimensions;
					for (dim = 0; dim < this.dimensions; dim++) {
						@Pc(246) float value = (float) this.multiplicands[indexDivisor] * deltaValue + minimumValue + last;
						this.valueVector[i][dim] = value;
						if (sequenceP) {
							last = value;
						}
						indexDivisor++;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ji", name = "a", descriptor = "(II)I")
	public static int lookup1Values(@OriginalArg(0) int entries, @OriginalArg(1) int dimensions) {
		@Pc(10) int returnValue;
		for (returnValue = (int) Math.pow(entries, 1.0D / (double) dimensions) + 1; IntUtils.pow(dimensions, returnValue) > entries; returnValue--) {
		}
		return returnValue;
	}

	@OriginalMember(owner = "client!ji", name = "a", descriptor = "()[F")
	public final float[] decodeVq() {
		return this.valueVector[this.decodeScalar()];
	}

	@OriginalMember(owner = "client!ji", name = "b", descriptor = "()V")
	private void createEntryTree() {
		@Pc(3) int[] codewords = new int[this.entries];
		@Pc(6) int[] currentCodes = new int[33];
		@Pc(8) int i;
		@Pc(17) int length;
		@Pc(26) int highBit;
		@Pc(30) int code;
		@Pc(44) int nextCode;
		@Pc(53) int depth;
		@Pc(69) int bit;
		for (i = 0; i < this.entries; i++) {
			length = this.lengths[i];
			if (length != 0) {
				highBit = 0x1 << 32 - length;
				code = currentCodes[length];
				codewords[i] = code;
				@Pc(60) int prevCode;
				if ((code & highBit) == 0) {
					nextCode = code | highBit;
					for (depth = length - 1; depth >= 1; depth--) {
						prevCode = currentCodes[depth];
						if (prevCode != code) {
							break;
						}
						bit = 0x1 << 32 - depth;
						if ((prevCode & bit) != 0) {
							currentCodes[depth] = currentCodes[depth - 1];
							break;
						}
						currentCodes[depth] = prevCode | bit;
					}
				} else {
					nextCode = currentCodes[length - 1];
				}
				currentCodes[length] = nextCode;
				for (depth = length + 1; depth <= 32; depth++) {
					prevCode = currentCodes[depth];
					if (prevCode == code) {
						currentCodes[depth] = nextCode;
					}
				}
			}
		}
		this.entryTree = new int[8];
		@Pc(122) int treeSize = 0;
		for (i = 0; i < this.entries; i++) {
			length = this.lengths[i];
			if (length != 0) {
				highBit = codewords[i];
				code = 0;
				for (nextCode = 0; nextCode < length; nextCode++) {
					depth = Integer.MIN_VALUE >>> nextCode;
					if ((highBit & depth) == 0) {
						code++;
					} else {
						if (this.entryTree[code] == 0) {
							this.entryTree[code] = treeSize;
						}
						code = this.entryTree[code];
					}
					if (code >= this.entryTree.length) {
						@Pc(184) int[] expanded = new int[this.entryTree.length * 2];
						for (bit = 0; bit < this.entryTree.length; bit++) {
							expanded[bit] = this.entryTree[bit];
						}
						this.entryTree = expanded;
					}
				}
				this.entryTree[code] = ~i;
				if (code >= treeSize) {
					treeSize = code + 1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!ji", name = "c", descriptor = "()I")
	public final int decodeScalar() {
		@Pc(1) int node;
		for (node = 0; this.entryTree[node] >= 0; node = VorbisSound.readBit() == 0 ? node + 1 : this.entryTree[node]) {
		}
		return ~this.entryTree[node];
	}
}
