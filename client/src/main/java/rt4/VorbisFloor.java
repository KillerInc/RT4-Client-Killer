package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ie")
public final class VorbisFloor {

	@OriginalMember(owner = "client!ie", name = "k", descriptor = "[I")
	public static final int[] RANGES = new int[]{256, 128, 86, 64};
	@OriginalMember(owner = "client!ie", name = "l", descriptor = "[F")
	public static final float[] INVERSE_DB_TABLE = new float[]{1.0649863E-7F, 1.1341951E-7F, 1.2079015E-7F, 1.2863978E-7F, 1.369995E-7F, 1.459025E-7F, 1.5538409E-7F, 1.6548181E-7F, 1.7623574E-7F, 1.8768856E-7F, 1.998856E-7F, 2.128753E-7F, 2.2670913E-7F, 2.4144197E-7F, 2.5713223E-7F, 2.7384212E-7F, 2.9163792E-7F, 3.1059022E-7F, 3.307741E-7F, 3.5226967E-7F, 3.7516213E-7F, 3.995423E-7F, 4.255068E-7F, 4.5315863E-7F, 4.8260745E-7F, 5.1397E-7F, 5.4737063E-7F, 5.829419E-7F, 6.208247E-7F, 6.611694E-7F, 7.041359E-7F, 7.4989464E-7F, 7.98627E-7F, 8.505263E-7F, 9.057983E-7F, 9.646621E-7F, 1.0273513E-6F, 1.0941144E-6F, 1.1652161E-6F, 1.2409384E-6F, 1.3215816E-6F, 1.4074654E-6F, 1.4989305E-6F, 1.5963394E-6F, 1.7000785E-6F, 1.8105592E-6F, 1.9282195E-6F, 2.053526E-6F, 2.1869757E-6F, 2.3290977E-6F, 2.4804558E-6F, 2.6416496E-6F, 2.813319E-6F, 2.9961443E-6F, 3.1908505E-6F, 3.39821E-6F, 3.619045E-6F, 3.8542307E-6F, 4.1047006E-6F, 4.371447E-6F, 4.6555283E-6F, 4.958071E-6F, 5.280274E-6F, 5.623416E-6F, 5.988857E-6F, 6.3780467E-6F, 6.7925284E-6F, 7.2339453E-6F, 7.704048E-6F, 8.2047E-6F, 8.737888E-6F, 9.305725E-6F, 9.910464E-6F, 1.0554501E-5F, 1.1240392E-5F, 1.1970856E-5F, 1.2748789E-5F, 1.3577278E-5F, 1.4459606E-5F, 1.5399271E-5F, 1.6400005E-5F, 1.7465769E-5F, 1.8600793E-5F, 1.9809577E-5F, 2.1096914E-5F, 2.2467912E-5F, 2.3928002E-5F, 2.5482977E-5F, 2.7139005E-5F, 2.890265E-5F, 3.078091E-5F, 3.2781227E-5F, 3.4911533E-5F, 3.718028E-5F, 3.9596467E-5F, 4.2169668E-5F, 4.491009E-5F, 4.7828602E-5F, 5.0936775E-5F, 5.424693E-5F, 5.7772202E-5F, 6.152657E-5F, 6.552491E-5F, 6.9783084E-5F, 7.4317984E-5F, 7.914758E-5F, 8.429104E-5F, 8.976875E-5F, 9.560242E-5F, 1.0181521E-4F, 1.0843174E-4F, 1.1547824E-4F, 1.2298267E-4F, 1.3097477E-4F, 1.3948625E-4F, 1.4855085E-4F, 1.5820454E-4F, 1.6848555E-4F, 1.7943469E-4F, 1.9109536E-4F, 2.0351382E-4F, 2.167393E-4F, 2.3082423E-4F, 2.4582449E-4F, 2.6179955E-4F, 2.7881275E-4F, 2.9693157E-4F, 3.1622787E-4F, 3.3677815E-4F, 3.5866388E-4F, 3.8197188E-4F, 4.0679457E-4F, 4.3323037E-4F, 4.613841E-4F, 4.913675E-4F, 5.2329927E-4F, 5.573062E-4F, 5.935231E-4F, 6.320936E-4F, 6.731706E-4F, 7.16917E-4F, 7.635063E-4F, 8.1312325E-4F, 8.6596457E-4F, 9.2223985E-4F, 9.821722E-4F, 0.0010459992F, 0.0011139743F, 0.0011863665F, 0.0012634633F, 0.0013455702F, 0.0014330129F, 0.0015261382F, 0.0016253153F, 0.0017309374F, 0.0018434235F, 0.0019632196F, 0.0020908006F, 0.0022266726F, 0.0023713743F, 0.0025254795F, 0.0026895993F, 0.0028643848F, 0.0030505287F, 0.003248769F, 0.0034598925F, 0.0036847359F, 0.0039241905F, 0.0041792067F, 0.004450795F, 0.004740033F, 0.005048067F, 0.0053761187F, 0.005725489F, 0.0060975635F, 0.0064938175F, 0.0069158226F, 0.0073652514F, 0.007843887F, 0.008353627F, 0.008896492F, 0.009474637F, 0.010090352F, 0.01074608F, 0.011444421F, 0.012188144F, 0.012980198F, 0.013823725F, 0.014722068F, 0.015678791F, 0.016697686F, 0.017782796F, 0.018938422F, 0.020169148F, 0.021479854F, 0.022875736F, 0.02436233F, 0.025945531F, 0.027631618F, 0.029427277F, 0.031339627F, 0.03337625F, 0.035545226F, 0.037855156F, 0.0403152F, 0.042935107F, 0.045725275F, 0.048696756F, 0.05186135F, 0.05523159F, 0.05882085F, 0.062643364F, 0.06671428F, 0.07104975F, 0.075666964F, 0.08058423F, 0.08582105F, 0.09139818F, 0.097337745F, 0.1036633F, 0.11039993F, 0.11757434F, 0.12521498F, 0.13335215F, 0.14201812F, 0.15124726F, 0.16107617F, 0.1715438F, 0.18269168F, 0.19456401F, 0.20720787F, 0.22067343F, 0.23501402F, 0.25028655F, 0.26655158F, 0.28387362F, 0.3023213F, 0.32196787F, 0.34289113F, 0.36517414F, 0.3889052F, 0.41417846F, 0.44109413F, 0.4697589F, 0.50028646F, 0.53279793F, 0.5674221F, 0.6042964F, 0.64356697F, 0.6853896F, 0.72993004F, 0.777365F, 0.8278826F, 0.88168305F, 0.9389798F, 1.0F};
	@OriginalMember(owner = "client!ie", name = "b", descriptor = "[Z")
	public static boolean[] step2Flag;
	@OriginalMember(owner = "client!ie", name = "h", descriptor = "[I")
	public static int[] currentXList;
	@OriginalMember(owner = "client!ie", name = "j", descriptor = "[I")
	public static int[] y;
	@OriginalMember(owner = "client!ie", name = "e", descriptor = "[I")
	private final int[] classList;

	@OriginalMember(owner = "client!ie", name = "i", descriptor = "[I")
	private final int[] classDimensions;

	@OriginalMember(owner = "client!ie", name = "g", descriptor = "[I")
	private final int[] subclasses;

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "[I")
	private final int[] classMasterBooks;

	@OriginalMember(owner = "client!ie", name = "c", descriptor = "[[I")
	private final int[][] subclassBooks;

	@OriginalMember(owner = "client!ie", name = "f", descriptor = "I")
	private final int multiplier;

	@OriginalMember(owner = "client!ie", name = "d", descriptor = "[I")
	private final int[] xList;

	@OriginalMember(owner = "client!ie", name = "<init>", descriptor = "()V")
	public VorbisFloor() {
		@Pc(4) int type = VorbisSound.readBits(16);
		if (type != 1) {
			throw new RuntimeException();
		}
		@Pc(14) int partitionCount = VorbisSound.readBits(5);
		@Pc(16) int maxClass = 0;
		this.classList = new int[partitionCount];
		@Pc(22) int i;
		@Pc(28) int classNum;
		for (i = 0; i < partitionCount; i++) {
			classNum = VorbisSound.readBits(4);
			this.classList[i] = classNum;
			if (classNum >= maxClass) {
				maxClass = classNum + 1;
			}
		}
		this.classDimensions = new int[maxClass];
		this.subclasses = new int[maxClass];
		this.classMasterBooks = new int[maxClass];
		this.subclassBooks = new int[maxClass][];
		@Pc(101) int j;
		for (i = 0; i < maxClass; i++) {
			this.classDimensions[i] = VorbisSound.readBits(3) + 1;
			classNum = this.subclasses[i] = VorbisSound.readBits(2);
			if (classNum != 0) {
				this.classMasterBooks[i] = VorbisSound.readBits(8);
			}
			classNum = 0x1 << classNum;
			@Pc(94) int[] books = new int[classNum];
			this.subclassBooks[i] = books;
			for (j = 0; j < classNum; j++) {
				books[j] = VorbisSound.readBits(8) - 1;
			}
		}
		this.multiplier = VorbisSound.readBits(2) + 1;
		i = VorbisSound.readBits(4);
		classNum = 2;
		@Pc(128) int partition;
		for (partition = 0; partition < partitionCount; partition++) {
			classNum += this.classDimensions[this.classList[partition]];
		}
		this.xList = new int[classNum];
		this.xList[0] = 0;
		this.xList[1] = 0x1 << i;
		classNum = 2;
		for (partition = 0; partition < partitionCount; partition++) {
			j = this.classList[partition];
			for (@Pc(173) int dim = 0; dim < this.classDimensions[j]; dim++) {
				this.xList[classNum++] = VorbisSound.readBits(i);
			}
		}
		if (currentXList == null || currentXList.length < classNum) {
			currentXList = new int[classNum];
			y = new int[classNum];
			step2Flag = new boolean[classNum];
		}
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "([II)I")
	public static int lowNeighbour(@OriginalArg(0) int[] xList, @OriginalArg(1) int index) {
		@Pc(3) int targetX = xList[index];
		@Pc(5) int result = -1;
		@Pc(7) int bestX = Integer.MIN_VALUE;
		for (@Pc(9) int i = 0; i < index; i++) {
			@Pc(16) int x = xList[i];
			if (x < targetX && x > bestX) {
				result = i;
				bestX = x;
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!ie", name = "b", descriptor = "([II)I")
	public static int highNeighbour(@OriginalArg(0) int[] xList, @OriginalArg(1) int index) {
		@Pc(3) int targetX = xList[index];
		@Pc(5) int result = -1;
		@Pc(7) int bestX = Integer.MAX_VALUE;
		for (@Pc(9) int i = 0; i < index; i++) {
			@Pc(16) int x = xList[i];
			if (x > targetX && x < bestX) {
				result = i;
				bestX = x;
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "(IIII[FI)V")
	private void renderLine(@OriginalArg(0) int x0, @OriginalArg(1) int y0, @OriginalArg(2) int x1, @OriginalArg(3) int y1, @OriginalArg(4) float[] output, @OriginalArg(5) int limit) {
		@Pc(3) int dy = y1 - y0;
		@Pc(7) int dx = x1 - x0;
		@Pc(14) int absDy = dy < 0 ? -dy : dy;
		@Pc(18) int slope = dy / dx;
		@Pc(20) int currentY = y0;
		@Pc(22) int error = 0;
		@Pc(32) int slopeAdj = dy < 0 ? slope - 1 : slope + 1;
		@Pc(43) int errorIncrement = absDy - (slope < 0 ? -slope : slope) * dx;
		output[x0] *= INVERSE_DB_TABLE[y0];
		if (x1 > limit) {
			x1 = limit;
		}
		for (@Pc(61) int x = x0 + 1; x < x1; x++) {
			error += errorIncrement;
			if (error >= dx) {
				error -= dx;
				currentY += slopeAdj;
			} else {
				currentY += slope;
			}
			output[x] *= INVERSE_DB_TABLE[currentY];
		}
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "(II)V")
	private void sort(@OriginalArg(0) int low, @OriginalArg(1) int high) {
		if (low >= high) {
			return;
		}
		@Pc(5) int pivotIdx = low;
		@Pc(9) int pivotX = currentXList[low];
		@Pc(13) int pivotY = y[low];
		@Pc(17) boolean pivotFlag = step2Flag[low];
		for (@Pc(21) int i = low + 1; i <= high; i++) {
			@Pc(28) int x = currentXList[i];
			if (x < pivotX) {
				currentXList[pivotIdx] = x;
				y[pivotIdx] = y[i];
				step2Flag[pivotIdx] = step2Flag[i];
				pivotIdx++;
				currentXList[i] = currentXList[pivotIdx];
				y[i] = y[pivotIdx];
				step2Flag[i] = step2Flag[pivotIdx];
			}
		}
		currentXList[pivotIdx] = pivotX;
		y[pivotIdx] = pivotY;
		step2Flag[pivotIdx] = pivotFlag;
		this.sort(low, pivotIdx - 1);
		this.sort(pivotIdx + 1, high);
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "(IIIII)I")
	private int renderPoint(@OriginalArg(0) int x0, @OriginalArg(1) int y0, @OriginalArg(2) int x1, @OriginalArg(3) int y1, @OriginalArg(4) int x) {
		@Pc(3) int dy = y1 - y0;
		@Pc(7) int dx = x1 - x0;
		@Pc(14) int absDy = dy < 0 ? -dy : dy;
		@Pc(20) int scaled = absDy * (x - x0);
		@Pc(24) int interpolated = scaled / dx;
		return dy < 0 ? y0 - interpolated : y0 + interpolated;
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "([FI)V")
	public final void synthesize(@OriginalArg(0) float[] output, @OriginalArg(1) int blockSize) {
		@Pc(3) int xCount = this.xList.length;
		@Pc(10) int range = RANGES[this.multiplier - 1];
		step2Flag[0] = step2Flag[1] = true;
		@Pc(20) int i;
		@Pc(27) int lowIdx;
		@Pc(31) int highIdx;
		@Pc(49) int predicted;
		@Pc(53) int actual;
		for (i = 2; i < xCount; i++) {
			lowIdx = lowNeighbour(currentXList, i);
			highIdx = highNeighbour(currentXList, i);
			predicted = this.renderPoint(currentXList[lowIdx], y[lowIdx], currentXList[highIdx], y[highIdx], currentXList[i]);
			actual = y[i];
			@Pc(57) int hiRoom = range - predicted;
			@Pc(68) int loRoom = (hiRoom < predicted ? hiRoom : predicted) << 1;
			if (actual == 0) {
				step2Flag[i] = false;
				y[i] = predicted;
			} else {
				step2Flag[lowIdx] = step2Flag[highIdx] = true;
				step2Flag[i] = true;
				if (actual >= loRoom) {
					y[i] = hiRoom > predicted ? actual + predicted - predicted : predicted - actual + hiRoom - 1;
				} else {
					y[i] = (actual & 0x1) == 0 ? predicted + actual / 2 : predicted - (actual + 1) / 2;
				}
			}
		}
		this.sort(0, xCount - 1);
		i = 0;
		lowIdx = y[0] * this.multiplier;
		for (highIdx = 1; highIdx < xCount; highIdx++) {
			if (step2Flag[highIdx]) {
				predicted = currentXList[highIdx];
				actual = y[highIdx] * this.multiplier;
				this.renderLine(i, lowIdx, predicted, actual, output, blockSize);
				if (predicted >= blockSize) {
					return;
				}
				i = predicted;
				lowIdx = actual;
			}
		}
		@Pc(193) float lastValue = INVERSE_DB_TABLE[lowIdx];
		for (predicted = i; predicted < blockSize; predicted++) {
			output[predicted] *= lastValue;
		}
	}

	@OriginalMember(owner = "client!ie", name = "b", descriptor = "()Z")
	public final boolean decodePacket() {
		@Pc(5) boolean nonzero = VorbisSound.readBit() != 0;
		if (!nonzero) {
			return false;
		}
		@Pc(13) int xCount = this.xList.length;
		@Pc(15) int i;
		for (i = 0; i < xCount; i++) {
			currentXList[i] = this.xList[i];
		}
		i = RANGES[this.multiplier - 1];
		@Pc(40) int bitsPerY = IntUtils.bitCount(i - 1);
		y[0] = VorbisSound.readBits(bitsPerY);
		y[1] = VorbisSound.readBits(bitsPerY);
		@Pc(52) int offset = 2;
		for (@Pc(54) int partition = 0; partition < this.classList.length; partition++) {
			@Pc(64) int classId = this.classList[partition];
			@Pc(69) int classDim = this.classDimensions[classId];
			@Pc(74) int numSubclasses = this.subclasses[classId];
			@Pc(80) int subclassMask = (0x1 << numSubclasses) - 1;
			@Pc(82) int codeword = 0;
			if (numSubclasses > 0) {
				codeword = VorbisSound.codebooks[this.classMasterBooks[classId]].decodeScalar();
			}
			for (@Pc(94) int dim = 0; dim < classDim; dim++) {
				@Pc(106) int bookNum = this.subclassBooks[classId][codeword & subclassMask];
				codeword >>>= numSubclasses;
				y[offset++] = bookNum >= 0 ? VorbisSound.codebooks[bookNum].decodeScalar() : 0;
			}
		}
		return true;
	}
}
