package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!vb")
public final class VorbisResidue {

	@OriginalMember(owner = "client!vb", name = "c", descriptor = "I")
	private final int type = VorbisSound.readBits(16);

	@OriginalMember(owner = "client!vb", name = "b", descriptor = "I")
	private final int begin = VorbisSound.readBits(24);

	@OriginalMember(owner = "client!vb", name = "d", descriptor = "I")
	private final int end = VorbisSound.readBits(24);

	@OriginalMember(owner = "client!vb", name = "e", descriptor = "I")
	private final int partitionSize = VorbisSound.readBits(24) + 1;

	@OriginalMember(owner = "client!vb", name = "g", descriptor = "I")
	private final int classifications = VorbisSound.readBits(6) + 1;

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "I")
	private final int classBook = VorbisSound.readBits(8);

	@OriginalMember(owner = "client!vb", name = "f", descriptor = "[I")
	private final int[] books;

	@OriginalMember(owner = "client!vb", name = "<init>", descriptor = "()V")
	public VorbisResidue() {
		@Pc(33) int[] cascades = new int[this.classifications];
		@Pc(35) int i;
		for (i = 0; i < this.classifications; i++) {
			@Pc(41) int highBits = 0;
			@Pc(44) int lowBits = VorbisSound.readBits(3);
			@Pc(50) boolean hasHighBits = VorbisSound.readBit() != 0;
			if (hasHighBits) {
				highBits = VorbisSound.readBits(5);
			}
			cascades[i] = highBits << 3 | lowBits;
		}
		this.books = new int[this.classifications * 8];
		for (i = 0; i < this.classifications * 8; i++) {
			this.books[i] = (cascades[i >> 3] & 0x1 << (i & 0x7)) == 0 ? -1 : VorbisSound.readBits(8);
		}
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "([FIZ)V")
	public final void synthesize(@OriginalArg(0) float[] output, @OriginalArg(1) int size, @OriginalArg(2) boolean doNotDecode) {
		@Pc(1) int i;
		for (i = 0; i < size; i++) {
			output[i] = 0.0F;
		}
		if (doNotDecode) {
			return;
		}
		i = VorbisSound.codebooks[this.classBook].dimensions;
		@Pc(25) int residueSize = this.end - this.begin;
		@Pc(30) int partitionCount = residueSize / this.partitionSize;
		@Pc(33) int[] classificationData = new int[partitionCount];
		for (@Pc(35) int pass = 0; pass < 8; pass++) {
			@Pc(40) int partitionIdx = 0;
			while (partitionIdx < partitionCount) {
				@Pc(51) int codeword;
				@Pc(55) int j;
				if (pass == 0) {
					codeword = VorbisSound.codebooks[this.classBook].decodeScalar();
					for (j = i - 1; j >= 0; j--) {
						if (partitionIdx + j < partitionCount) {
							classificationData[partitionIdx + j] = codeword % this.classifications;
						}
						codeword /= this.classifications;
					}
				}
				for (codeword = 0; codeword < i; codeword++) {
					j = classificationData[partitionIdx];
					@Pc(96) int bookId = this.books[j * 8 + pass];
					if (bookId >= 0) {
						@Pc(106) int offset = this.begin + partitionIdx * this.partitionSize;
						@Pc(110) VorbisCodebook codebook = VorbisSound.codebooks[bookId];
						@Pc(119) int k;
						if (this.type == 0) {
							k = this.partitionSize / codebook.dimensions;
							for (@Pc(121) int step = 0; step < k; step++) {
								@Pc(127) float[] vector = codebook.decodeVq();
								for (@Pc(129) int dim = 0; dim < codebook.dimensions; dim++) {
									output[offset + step + dim * k] += vector[dim];
								}
							}
						} else {
							k = 0;
							while (k < this.partitionSize) {
								@Pc(162) float[] vector = codebook.decodeVq();
								for (@Pc(164) int dim = 0; dim < codebook.dimensions; dim++) {
									output[offset + k] += vector[dim];
									k++;
								}
							}
						}
					}
					partitionIdx++;
					if (partitionIdx >= partitionCount) {
						break;
					}
				}
			}
		}
	}
}
