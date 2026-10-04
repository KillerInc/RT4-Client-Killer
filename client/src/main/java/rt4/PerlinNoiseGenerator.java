package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!wf")
public abstract class PerlinNoiseGenerator {

	@OriginalMember(owner = "client!wf", name = "c", descriptor = "[S")
	private short[] amplitudes;

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "[S")
	private final short[] permutations = new short[512];

	@OriginalMember(owner = "client!wf", name = "i", descriptor = "I")
	private int seed = 0;

	@OriginalMember(owner = "client!wf", name = "m", descriptor = "I")
	private int scaleX = 4;

	@OriginalMember(owner = "client!wf", name = "l", descriptor = "I")
	private int scaleY = 4;

	@OriginalMember(owner = "client!wf", name = "r", descriptor = "I")
	private int scaleZ = 4;

	@OriginalMember(owner = "client!wf", name = "t", descriptor = "I")
	protected int octaveCount = 4;

	@OriginalMember(owner = "client!wf", name = "<init>", descriptor = "(IIIII)V")
	protected PerlinNoiseGenerator(@OriginalArg(0) int seed, @OriginalArg(1) int octaveCount, @OriginalArg(2) int scaleX, @OriginalArg(3) int scaleY, @OriginalArg(4) int scaleZ) {
		this.scaleY = scaleY;
		this.octaveCount = octaveCount;
		this.seed = seed;
		this.scaleZ = scaleZ;
		this.scaleX = scaleX;
		this.initAmplitudes();
		this.initPermutations();
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(IIIIZ)I")
	public static int gradient(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int hash) {
		@Pc(8) int h = hash & 0xF;
		@Pc(29) int v = h >= 4 ? (h == 12 || h == 14 ? x : y) : z;
		@Pc(42) int u = h < 8 ? x : z;
		return ((h & 0x1) == 0 ? u : -u) + ((h & 0x2) == 0 ? v : -v);
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(IIII)V")
	protected final void generate() {
		@Pc(8) int[] xCoords = new int[64];
		@Pc(11) int[] yCoords = new int[64];
		@Pc(14) int[] zCoords = new int[64];
		@Pc(16) int i;
		for (i = 0; i < 64; i++) {
			xCoords[i] = (i << 12) / 64;
		}
		for (i = 0; i < 64; i++) {
			yCoords[i] = (i << 12) / 64;
		}
		for (i = 0; i < 64; i++) {
			zCoords[i] = (i << 12) / 64;
		}
		this.resetState();
		for (@Pc(77) int iz = 0; iz < 64; iz++) {
			for (@Pc(82) int iy = 0; iy < 64; iy++) {
				for (@Pc(91) int ix = 0; ix < 64; ix++) {
					for (@Pc(96) int octave = 0; octave < this.octaveCount; octave++) {
						i = this.amplitudes[octave] << 12;
						@Pc(118) int xBound = this.scaleX * i >> 12;
						@Pc(126) int scaledZ = i * zCoords[iz] >> 12;
						@Pc(133) int yBound = i * this.scaleY >> 12;
						@Pc(138) int zProduct = scaledZ * this.scaleZ;
						@Pc(145) int zBound = i * this.scaleZ >> 12;
						@Pc(153) int scaledX = xCoords[ix] * i >> 12;
						@Pc(158) int xProduct = scaledX * this.scaleX;
						@Pc(162) int gridX = xProduct >> 12;
						@Pc(166) int fracX = xProduct & 0xFFF;
						@Pc(170) int gridZ = zProduct >> 12;
						@Pc(174) int fracXm = fracX - 4096;
						@Pc(178) int gridX1 = gridX + 1;
						@Pc(182) int gridZ1 = gridZ + 1;
						@Pc(190) int scaledY = yCoords[iy] * i >> 12;
						@Pc(194) int smoothX = MonochromeImageCache.smoothstepLookup[fracX];
						@Pc(199) int yProduct = scaledY * this.scaleY;
						@Pc(203) int permZ = gridZ & 0xFF;
						@Pc(207) int permX = gridX & 0xFF;
						if (zBound <= gridZ1) {
							gridZ1 = 0;
						} else {
							gridZ1 &= 0xFF;
						}
						@Pc(222) int gridY = yProduct >> 12;
						@Pc(227) short permPZ = this.permutations[permZ];
						@Pc(232) short permPZ1 = this.permutations[gridZ1];
						@Pc(236) int gridY1 = gridY + 1;
						if (yBound > gridY1) {
							gridY1 &= 0xFF;
						} else {
							gridY1 = 0;
						}
						scaledY = yProduct & 0xFFF;
						@Pc(259) short permPZ1Y1 = this.permutations[gridY1 + permPZ1];
						scaledZ = zProduct & 0xFFF;
						gridY &= 0xFF;
						@Pc(271) int smoothZ = MonochromeImageCache.smoothstepLookup[scaledZ];
						@Pc(278) short permPZ1Y = this.permutations[gridY + permPZ1];
						@Pc(285) short permPZY = this.permutations[permPZ + gridY];
						if (xBound <= gridX1) {
							gridX1 = 0;
						} else {
							gridX1 &= 0xFF;
						}
						@Pc(300) int fracYm = scaledY - 4096;
						@Pc(304) int smoothY = MonochromeImageCache.smoothstepLookup[scaledY];
						@Pc(308) int fracZm = scaledZ - 4096;
						@Pc(315) short permPZY1 = this.permutations[permPZ + gridY1];
						@Pc(327) int g000 = gradient(fracX, scaledZ, scaledY, this.permutations[permPZY + permX]);
						@Pc(340) int g100 = gradient(fracXm, scaledZ, scaledY, this.permutations[gridX1 + permPZY]);
						@Pc(351) int interpX0 = g000 + (smoothX * (g100 - g000) >> 12);
						@Pc(363) int g010 = gradient(fracX, scaledZ, fracYm, this.permutations[permPZY1 + permX]);
						@Pc(375) int g110 = gradient(fracXm, scaledZ, fracYm, this.permutations[gridX1 + permPZY1]);
						@Pc(386) int interpX1 = g010 + (smoothX * (g110 - g010) >> 12);
						@Pc(397) int interpXY0 = ((interpX1 - interpX0) * smoothY >> 12) + interpX0;
						@Pc(409) int g001 = gradient(fracX, fracZm, scaledY, this.permutations[permX + permPZ1Y]);
						@Pc(421) int g101 = gradient(fracXm, fracZm, scaledY, this.permutations[gridX1 + permPZ1Y]);
						@Pc(432) int interpX2 = (smoothX * (g101 - g001) >> 12) + g001;
						@Pc(445) int g011 = gradient(fracX, fracZm, fracYm, this.permutations[permX + permPZ1Y1]);
						@Pc(457) int g111 = gradient(fracXm, fracZm, fracYm, this.permutations[gridX1 + permPZ1Y1]);
						@Pc(468) int interpX3 = g011 + ((g111 - g011) * smoothX >> 12);
						@Pc(480) int interpXY1 = interpX2 + ((interpX3 - interpX2) * smoothY >> 12);
						this.accumulate(((interpXY1 - interpXY0) * smoothZ >> 12) + interpXY0, octave);
					}
					this.finishSample();
				}
			}
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(B)V")
	protected abstract void finishSample();

	@OriginalMember(owner = "client!wf", name = "b", descriptor = "(B)V")
	private void initAmplitudes() {
		this.amplitudes = new short[this.octaveCount];
		for (@Pc(12) int i = 0; i < this.octaveCount; i++) {
			this.amplitudes[i] = (short) Math.pow(2.0D, i);
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(I)V")
	protected abstract void resetState();

	@OriginalMember(owner = "client!wf", name = "c", descriptor = "(I)V")
	private void initPermutations() {
		@Pc(12) Random rng = new Random(this.seed);
		@Pc(14) int i;
		for (i = 0; i < 255; i++) {
			this.permutations[i] = (short) i;
		}
		for (i = 0; i < 255; i++) {
			@Pc(41) int remaining = 255 - i;
			@Pc(46) int swapIdx = RandomUtils.nextInt(remaining, rng);
			@Pc(51) short temp = this.permutations[swapIdx];
			this.permutations[swapIdx] = this.permutations[remaining];
			this.permutations[remaining] = this.permutations[remaining + 256] = temp;
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(III)V")
	protected abstract void accumulate(@OriginalArg(0) int value, @OriginalArg(1) int octave);
}
