package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!bi")
public final class TextureOpPerlinNoise extends TextureOp {

	@OriginalMember(owner = "client!bi", name = "ib", descriptor = "[S")
	private short[] octaveFrequencies;

	@OriginalMember(owner = "client!bi", name = "lb", descriptor = "[S")
	private short[] octaveAmplitudes;

	@OriginalMember(owner = "client!bi", name = "X", descriptor = "I")
	public int frequencyY = 4;

	@OriginalMember(owner = "client!bi", name = "eb", descriptor = "I")
	public int frequencyX = 4;

	@OriginalMember(owner = "client!bi", name = "kb", descriptor = "Z")
	public boolean normalizeOutput = true;

	@OriginalMember(owner = "client!bi", name = "gb", descriptor = "I")
	public int persistence = 1638;

	@OriginalMember(owner = "client!bi", name = "ab", descriptor = "[B")
	private byte[] permutationTable = new byte[512];

	@OriginalMember(owner = "client!bi", name = "Z", descriptor = "I")
	public int octaveCount = 4;

	@OriginalMember(owner = "client!bi", name = "mb", descriptor = "I")
	public int seed = 0;

	@OriginalMember(owner = "client!bi", name = "<init>", descriptor = "()V")
	public TextureOpPerlinNoise() {
		super(0, true);
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ZI[I)V")
	public final void getNoiseRow(@OriginalArg(1) int row, @OriginalArg(2) int[] output) {
		@Pc(12) int scaledY = this.frequencyY * Texture.heightFractions[row];
		@Pc(115) int rawX;
		@Pc(129) int noise;
		@Pc(40) int scale;
		@Pc(27) short amplitude;
		@Pc(105) int col;
		@Pc(60) int yPos;
		@Pc(54) int scaledFreqX;
		@Pc(47) int scaledFreqY;
		@Pc(85) int smoothY;
		@Pc(64) int cellY;
		@Pc(68) int cellYNext;
		@Pc(77) int permY;
		@Pc(103) int permYNext;
		if (this.octaveCount == 1) {
			amplitude = this.octaveAmplitudes[0];
			scale = this.octaveFrequencies[0] << 12;
			yPos = scaledY * scale >> 12;
			scaledFreqX = this.frequencyX * scale >> 12;
			scaledFreqY = scale * this.frequencyY >> 12;
			cellY = yPos >> 12;
			permY = this.permutationTable[cellY & 0xFF] & 0xFF;
			yPos &= 0xFFF;
			smoothY = MonochromeImageCache.smoothstepLookup[yPos];
			cellYNext = cellY + 1;
			if (scaledFreqY <= cellYNext) {
				cellYNext = 0;
			}
			permYNext = this.permutationTable[cellYNext & 0xFF] & 0xFF;
			if (this.normalizeOutput) {
				for (col = 0; col < Texture.width; col++) {
					rawX = this.frequencyX * Texture.widthFractions[col];
					noise = this.sampleNoise(scale * rawX >> 12, permYNext, permY, scaledFreqX, yPos, smoothY);
					noise = amplitude * noise >> 12;
					output[col] = (noise >> 1) + 2048;
				}
			} else {
				for (col = 0; col < Texture.width; col++) {
					rawX = this.frequencyX * Texture.widthFractions[col];
					noise = this.sampleNoise(scale * rawX >> 12, permYNext, permY, scaledFreqX, yPos, smoothY);
					output[col] = amplitude * noise >> 12;
				}
			}
			return;
		}
		amplitude = this.octaveAmplitudes[0];
		if (amplitude > 8 || amplitude < -8) {
			scale = this.octaveFrequencies[0] << 12;
			scaledFreqY = scale * this.frequencyY >> 12;
			scaledFreqX = this.frequencyX * scale >> 12;
			yPos = scaledY * scale >> 12;
			cellY = yPos >> 12;
			cellYNext = cellY + 1;
			permY = this.permutationTable[cellY & 0xFF] & 0xFF;
			@Pc(81) int yFrac = yPos & 0xFFF;
			smoothY = MonochromeImageCache.smoothstepLookup[yFrac];
			if (cellYNext >= scaledFreqY) {
				cellYNext = 0;
			}
			permYNext = this.permutationTable[cellYNext & 0xFF] & 0xFF;
			for (col = 0; col < Texture.width; col++) {
				rawX = Texture.widthFractions[col] * this.frequencyX;
				noise = this.sampleNoise(rawX * scale >> 12, permYNext, permY, scaledFreqX, yFrac, smoothY);
				output[col] = noise * amplitude >> 12;
			}
		}
		for (@Pc(142) int octave = 1; octave < this.octaveCount; octave++) {
			amplitude = this.octaveAmplitudes[octave];
			if (amplitude > 8 || amplitude < -8) {
				scale = this.octaveFrequencies[octave] << 12;
				yPos = scale * scaledY >> 12;
				cellY = yPos >> 12;
				permY = this.permutationTable[cellY & 0xFF] & 0xFF;
				scaledFreqX = this.frequencyX * scale >> 12;
				cellYNext = cellY + 1;
				yPos &= 0xFFF;
				smoothY = MonochromeImageCache.smoothstepLookup[yPos];
				scaledFreqY = this.frequencyY * scale >> 12;
				if (cellYNext >= scaledFreqY) {
					cellYNext = 0;
				}
				permYNext = this.permutationTable[cellYNext & 0xFF] & 0xFF;
				if (this.normalizeOutput && octave == this.octaveCount - 1) {
					for (col = 0; col < Texture.width; col++) {
						rawX = Texture.widthFractions[col] * this.frequencyX;
						noise = this.sampleNoise(scale * rawX >> 12, permYNext, permY, scaledFreqX, yPos, smoothY);
						noise = (amplitude * noise >> 12) + output[col];
						output[col] = (noise >> 1) + 2048;
					}
				} else {
					for (col = 0; col < Texture.width; col++) {
						rawX = Texture.widthFractions[col] * this.frequencyX;
						noise = this.sampleNoise(rawX * scale >> 12, permYNext, permY, scaledFreqX, yPos, smoothY);
						output[col] += noise * amplitude >> 12;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!bi", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.permutationTable = TextureOpVoronoi.getPermutationTable(this.seed);
		this.initOctaves();
		for (@Pc(15) int i = this.octaveCount - 1; i >= 1; i--) {
			@Pc(23) short amp = this.octaveAmplitudes[i];
			if (amp > 8 || amp < -8) {
				break;
			}
			this.octaveCount--;
		}
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.normalizeOutput = buf.g1() == 1;
		} else if (opcode == 1) {
			this.octaveCount = buf.g1();
		} else if (opcode == 2) {
			this.persistence = buf.g2b();
			if (this.persistence < 0) {
				this.octaveAmplitudes = new short[this.octaveCount];
				for (@Pc(93) int i = 0; i < this.octaveCount; i++) {
					this.octaveAmplitudes[i] = (short) buf.g2b();
				}
			}
		} else if (opcode == 3) {
			this.frequencyX = this.frequencyY = buf.g1();
		} else if (opcode == 4) {
			this.seed = buf.g1();
		} else if (opcode == 5) {
			this.frequencyX = buf.g1();
		} else if (opcode == 6) {
			this.frequencyY = buf.g1();
		}
	}

	@OriginalMember(owner = "client!bi", name = "b", descriptor = "(Z)V")
	private void initOctaves() {
		@Pc(21) int i;
		if (this.persistence > 0) {
			this.octaveAmplitudes = new short[this.octaveCount];
			this.octaveFrequencies = new short[this.octaveCount];
			for (i = 0; i < this.octaveCount; i++) {
				this.octaveAmplitudes[i] = (short) (Math.pow((float) this.persistence / 4096.0F, i) * 4096.0D);
				this.octaveFrequencies[i] = (short) Math.pow(2.0D, i);
			}
		} else if (this.octaveAmplitudes != null && this.octaveAmplitudes.length == this.octaveCount) {
			this.octaveFrequencies = new short[this.octaveCount];
			for (i = 0; i < this.octaveCount; i++) {
				this.octaveFrequencies[i] = (short) Math.pow(2.0D, i);
			}
		}
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IIIIIII)I")
	private int sampleNoise(@OriginalArg(0) int xPos, @OriginalArg(1) int permYNext, @OriginalArg(2) int permY, @OriginalArg(3) int scaledFreqX, @OriginalArg(5) int yFrac, @OriginalArg(6) int smoothY) {
		@Pc(15) int yFracNeg = yFrac - 4096;
		@Pc(19) int cellX = xPos >> 12;
		@Pc(23) int cellXNext = cellX + 1;
		@Pc(27) int cellXMasked = cellX & 0xFF;
		if (cellXNext >= scaledFreqX) {
			cellXNext = 0;
		}
		@Pc(40) int xFrac = xPos & 0xFFF;
		@Pc(50) int grad = this.permutationTable[cellXMasked + permY] & 0x3;
		@Pc(54) int smoothX = MonochromeImageCache.smoothstepLookup[xFrac];
		@Pc(70) int dot0;
		if (grad > 1) {
			dot0 = grad == 2 ? xFrac - yFrac : -xFrac + -yFrac;
		} else {
			dot0 = grad == 0 ? yFrac + xFrac : -xFrac + yFrac;
		}
		cellXNext &= 0xFF;
		@Pc(92) int xFracNeg = xFrac - 4096;
		grad = this.permutationTable[permY + cellXNext] & 0x3;
		@Pc(118) int dot1;
		if (grad <= 1) {
			dot1 = grad == 0 ? yFrac + xFracNeg : -xFracNeg + yFrac;
		} else {
			dot1 = grad == 2 ? xFracNeg - yFrac : -xFracNeg + -yFrac;
		}
		grad = this.permutationTable[cellXMasked + permYNext] & 0x3;
		@Pc(155) int interpTop = dot0 + ((dot1 - dot0) * smoothX >> 12);
		if (grad > 1) {
			dot0 = grad == 2 ? xFrac - yFracNeg : -xFrac - yFracNeg;
		} else {
			dot0 = grad == 0 ? xFrac + yFracNeg : yFracNeg + -xFrac;
		}
		grad = this.permutationTable[permYNext + cellXNext] & 0x3;
		if (grad > 1) {
			dot1 = grad == 2 ? xFracNeg - yFracNeg : -yFracNeg + -xFracNeg;
		} else {
			dot1 = grad == 0 ? xFracNeg + yFracNeg : yFracNeg + -xFracNeg;
		}
		@Pc(237) int interpBottom = dot0 + ((dot1 - dot0) * smoothX >> 12);
		return interpTop + (smoothY * (interpBottom - interpTop) >> 12);
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(17) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			this.getNoiseRow(row, output);
		}
		return output;
	}
}
