package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!jc")
public final class VorbisSound extends Node {

	@OriginalMember(owner = "client!jc", name = "p", descriptor = "[F")
	public static float[] trigB1;
	@OriginalMember(owner = "client!jc", name = "q", descriptor = "[F")
	public static float[] trigC1;
	@OriginalMember(owner = "client!jc", name = "t", descriptor = "[I")
	public static int[] bitReverse0;
	@OriginalMember(owner = "client!jc", name = "u", descriptor = "[Lclient!vb;")
	public static VorbisResidue[] residues;
	@OriginalMember(owner = "client!jc", name = "v", descriptor = "[Z")
	public static boolean[] modeBlockFlags;
	@OriginalMember(owner = "client!jc", name = "y", descriptor = "I")
	public static int blockSize1;
	@OriginalMember(owner = "client!jc", name = "A", descriptor = "[Lclient!uk;")
	public static VorbisMapping[] mappings;
	@OriginalMember(owner = "client!jc", name = "B", descriptor = "[F")
	public static float[] trigA1;
	@OriginalMember(owner = "client!jc", name = "C", descriptor = "[F")
	public static float[] trigA0;
	@OriginalMember(owner = "client!jc", name = "E", descriptor = "I")
	public static int blockSize0;
	@OriginalMember(owner = "client!jc", name = "F", descriptor = "[F")
	public static float[] vector;
	@OriginalMember(owner = "client!jc", name = "I", descriptor = "[Lclient!ji;")
	public static VorbisCodebook[] codebooks;
	@OriginalMember(owner = "client!jc", name = "J", descriptor = "[Lclient!ie;")
	public static VorbisFloor[] floors;
	@OriginalMember(owner = "client!jc", name = "L", descriptor = "[I")
	public static int[] bitReverse1;
	@OriginalMember(owner = "client!jc", name = "P", descriptor = "[F")
	public static float[] trigB0;
	@OriginalMember(owner = "client!jc", name = "S", descriptor = "[F")
	public static float[] trigC0;
	@OriginalMember(owner = "client!jc", name = "W", descriptor = "[I")
	public static int[] modeMappings;
	@OriginalMember(owner = "client!jc", name = "G", descriptor = "I")
	private static int position;
	@OriginalMember(owner = "client!jc", name = "Q", descriptor = "I")
	private static int bitPosition;
	@OriginalMember(owner = "client!jc", name = "T", descriptor = "[B")
	private static byte[] bytes;
	@OriginalMember(owner = "client!jc", name = "H", descriptor = "Z")
	private static boolean codebooksLoaded = false;
	@OriginalMember(owner = "client!jc", name = "r", descriptor = "I")
	private int prevN;

	@OriginalMember(owner = "client!jc", name = "s", descriptor = "I")
	private int samplesLen;

	@OriginalMember(owner = "client!jc", name = "w", descriptor = "[F")
	private float[] prevVector;

	@OriginalMember(owner = "client!jc", name = "x", descriptor = "Z")
	private boolean pingPongLoop;

	@OriginalMember(owner = "client!jc", name = "z", descriptor = "I")
	private int start;

	@OriginalMember(owner = "client!jc", name = "D", descriptor = "I")
	private int end;

	@OriginalMember(owner = "client!jc", name = "K", descriptor = "I")
	private int rate;

	@OriginalMember(owner = "client!jc", name = "M", descriptor = "[[B")
	private byte[][] packets;

	@OriginalMember(owner = "client!jc", name = "N", descriptor = "Z")
	private boolean prevNoResidue;

	@OriginalMember(owner = "client!jc", name = "O", descriptor = "I")
	private int prevQuarter;

	@OriginalMember(owner = "client!jc", name = "R", descriptor = "[B")
	private byte[] samples;

	@OriginalMember(owner = "client!jc", name = "U", descriptor = "I")
	private int packetIndex;

	@OriginalMember(owner = "client!jc", name = "V", descriptor = "I")
	private int sampleIndex;

	@OriginalMember(owner = "client!jc", name = "<init>", descriptor = "([B)V")
	public VorbisSound(@OriginalArg(0) byte[] bytes) {
		this.decode(bytes);
	}

	@OriginalMember(owner = "client!jc", name = "c", descriptor = "(I)F")
	public static float float32Unpack(@OriginalArg(0) int x) {
		@Pc(3) int mantissa = x & 0x1FFFFF;
		@Pc(7) int sign = x & Integer.MIN_VALUE;
		@Pc(13) int exponent = x >> 21 & 0x3FF;
		if (sign != 0) {
			mantissa = -mantissa;
		}
		return (float) ((double) mantissa * Math.pow(2.0D, exponent - 788));
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "(Lclient!ve;)Z")
	private static boolean ensureCodebooksLoaded(@OriginalArg(0) Js5 js5) {
		if (!codebooksLoaded) {
			@Pc(7) byte[] data = js5.fetchFile(0, 0);
			if (data == null) {
				return false;
			}
			decodeCodebooks(data);
			codebooksLoaded = true;
		}
		return true;
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "(Lclient!ve;II)Lclient!jc;")
	public static VorbisSound create(@OriginalArg(0) Js5 js5, @OriginalArg(1) int archive, @OriginalArg(2) int file) {
		if (ensureCodebooksLoaded(js5)) {
			@Pc(16) byte[] data = js5.fetchFile(archive, file);
			return data == null ? null : new VorbisSound(data);
		} else {
			js5.isFileReady(file, archive);
			return null;
		}
	}

	@OriginalMember(owner = "client!jc", name = "b", descriptor = "()I")
	public static int readBit() {
		@Pc(7) int bit = bytes[position] >> bitPosition & 0x1;
		bitPosition++;
		position += bitPosition >> 3;
		bitPosition &= 0x7;
		return bit;
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "([BI)V")
	public static void setBytes(@OriginalArg(0) byte[] data) {
		bytes = data;
		position = 0;
		bitPosition = 0;
	}

	@OriginalMember(owner = "client!jc", name = "b", descriptor = "([B)V")
	private static void decodeCodebooks(@OriginalArg(0) byte[] data) {
		setBytes(data);
		blockSize0 = 0x1 << readBits(4);
		blockSize1 = 0x1 << readBits(4);
		vector = new float[blockSize1];
		@Pc(17) int count;
		@Pc(26) int blockSize;
		@Pc(30) int halfBlock;
		@Pc(34) int quarterBlock;
		@Pc(38) int eighthBlock;
		for (count = 0; count < 2; count++) {
			blockSize = count == 0 ? blockSize0 : blockSize1;
			halfBlock = blockSize >> 1;
			quarterBlock = blockSize >> 2;
			eighthBlock = blockSize >> 3;
			@Pc(41) float[] trigA = new float[halfBlock];
			for (@Pc(43) int i = 0; i < quarterBlock; i++) {
				trigA[i * 2] = (float) Math.cos((double) (i * 4) * 3.141592653589793D / (double) blockSize);
				trigA[i * 2 + 1] = -((float) Math.sin((double) (i * 4) * 3.141592653589793D / (double) blockSize));
			}
			@Pc(86) float[] trigB = new float[halfBlock];
			for (@Pc(88) int i = 0; i < quarterBlock; i++) {
				trigB[i * 2] = (float) Math.cos((double) (i * 2 + 1) * 3.141592653589793D / (double) (blockSize * 2));
				trigB[i * 2 + 1] = (float) Math.sin((double) (i * 2 + 1) * 3.141592653589793D / (double) (blockSize * 2));
			}
			@Pc(138) float[] trigC = new float[quarterBlock];
			for (@Pc(140) int i = 0; i < eighthBlock; i++) {
				trigC[i * 2] = (float) Math.cos((double) (i * 4 + 2) * 3.141592653589793D / (double) blockSize);
				trigC[i * 2 + 1] = -((float) Math.sin((double) (i * 4 + 2) * 3.141592653589793D / (double) blockSize));
			}
			@Pc(187) int[] bitRev = new int[eighthBlock];
			@Pc(193) int logN = IntUtils.bitCount(eighthBlock - 1);
			for (@Pc(195) int i = 0; i < eighthBlock; i++) {
				bitRev[i] = IntUtils.bitReverse(logN, i);
			}
			if (count == 0) {
				trigA0 = trigA;
				trigB0 = trigB;
				trigC0 = trigC;
				bitReverse0 = bitRev;
			} else {
				trigA1 = trigA;
				trigB1 = trigB;
				trigC1 = trigC;
				bitReverse1 = bitRev;
			}
		}
		count = readBits(8) + 1;
		codebooks = new VorbisCodebook[count];
		for (blockSize = 0; blockSize < count; blockSize++) {
			codebooks[blockSize] = new VorbisCodebook();
		}
		blockSize = readBits(6) + 1;
		for (halfBlock = 0; halfBlock < blockSize; halfBlock++) {
			readBits(16);
		}
		blockSize = readBits(6) + 1;
		floors = new VorbisFloor[blockSize];
		for (halfBlock = 0; halfBlock < blockSize; halfBlock++) {
			floors[halfBlock] = new VorbisFloor();
		}
		halfBlock = readBits(6) + 1;
		residues = new VorbisResidue[halfBlock];
		for (quarterBlock = 0; quarterBlock < halfBlock; quarterBlock++) {
			residues[quarterBlock] = new VorbisResidue();
		}
		quarterBlock = readBits(6) + 1;
		mappings = new VorbisMapping[quarterBlock];
		for (eighthBlock = 0; eighthBlock < quarterBlock; eighthBlock++) {
			mappings[eighthBlock] = new VorbisMapping();
		}
		eighthBlock = readBits(6) + 1;
		modeBlockFlags = new boolean[eighthBlock];
		modeMappings = new int[eighthBlock];
		for (@Pc(340) int i = 0; i < eighthBlock; i++) {
			modeBlockFlags[i] = readBit() != 0;
			readBits(16);
			readBits(16);
			modeMappings[i] = readBits(8);
		}
	}

	@OriginalMember(owner = "client!jc", name = "e", descriptor = "(I)I")
	public static int readBits(@OriginalArg(0) int len) {
		@Pc(1) int result = 0;
		@Pc(3) int shift = 0;
		@Pc(12) int bitsAvail;
		while (len >= 8 - bitPosition) {
			bitsAvail = 8 - bitPosition;
			@Pc(18) int mask = (0x1 << bitsAvail) - 1;
			result += (bytes[position] >> bitPosition & mask) << shift;
			bitPosition = 0;
			position++;
			shift += bitsAvail;
			len -= bitsAvail;
		}
		if (len > 0) {
			bitsAvail = (0x1 << len) - 1;
			result += (bytes[position] >> bitPosition & bitsAvail) << shift;
			bitPosition += len;
		}
		return result;
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "([I)Lclient!kj;")
	public final PcmSound toPcmSound(@OriginalArg(0) int[] budget) {
		if (budget != null && budget[0] <= 0) {
			return null;
		}
		if (this.samples == null) {
			this.prevN = 0;
			this.prevVector = new float[blockSize1];
			this.samples = new byte[this.samplesLen];
			this.sampleIndex = 0;
			this.packetIndex = 0;
		}
		while (this.packetIndex < this.packets.length) {
			if (budget != null && budget[0] <= 0) {
				return null;
			}
			@Pc(47) float[] decoded = this.decodePacket(this.packetIndex);
			if (decoded != null) {
				@Pc(52) int writePos = this.sampleIndex;
				@Pc(55) int sampleCount = decoded.length;
				if (sampleCount > this.samplesLen - writePos) {
					sampleCount = this.samplesLen - writePos;
				}
				for (@Pc(68) int i = 0; i < sampleCount; i++) {
					@Pc(80) int sample = (int) (decoded[i] * 128.0F + 128.0F);
					if ((sample & 0xFFFFFF00) != 0) {
						sample = ~sample >> 31;
					}
					this.samples[writePos++] = (byte) (sample - 128);
				}
				if (budget != null) {
					budget[0] -= writePos - this.sampleIndex;
				}
				this.sampleIndex = writePos;
			}
			this.packetIndex++;
		}
		this.prevVector = null;
		@Pc(129) byte[] finalSamples = this.samples;
		this.samples = null;
		return new PcmSound(this.rate, finalSamples, this.start, this.end, this.pingPongLoop);
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "([B)V")
	private void decode(@OriginalArg(0) byte[] data) {
		@Pc(4) Buffer buffer = new Buffer(data);
		this.rate = buffer.g4();
		this.samplesLen = buffer.g4();
		this.start = buffer.g4();
		this.end = buffer.g4();
		if (this.end < 0) {
			this.end = ~this.end;
			this.pingPongLoop = true;
		}
		@Pc(40) int packetsLen = buffer.g4();
		this.packets = new byte[packetsLen][];
		for (@Pc(46) int i = 0; i < packetsLen; i++) {
			@Pc(51) int len = 0;
			@Pc(55) int n;
			do {
				n = buffer.g1();
				len += n;
			} while (n >= 255);
			@Pc(67) byte[] packet = new byte[len];
			buffer.gdata(len, packet);
			this.packets[i] = packet;
		}
	}

	@OriginalMember(owner = "client!jc", name = "d", descriptor = "(I)[F")
	private float[] decodePacket(@OriginalArg(0) int index) {
		setBytes(this.packets[index]);
		readBit();
		@Pc(15) int modeNumber = readBits(IntUtils.bitCount(modeMappings.length - 1));
		@Pc(19) boolean modeBlockFlag = modeBlockFlags[modeNumber];
		@Pc(25) int n = modeBlockFlag ? blockSize1 : blockSize0;
		@Pc(27) boolean previousWindowFlag = false;
		@Pc(29) boolean nextWindowFlag = false;
		if (modeBlockFlag) {
			previousWindowFlag = readBit() != 0;
			nextWindowFlag = readBit() != 0;
		}
		@Pc(47) int windowCenter = n >> 1;
		@Pc(59) int leftWindowStart;
		@Pc(67) int leftWindowEnd;
		@Pc(71) int leftN;
		if (modeBlockFlag && !previousWindowFlag) {
			leftWindowStart = (n >> 2) - (blockSize0 >> 2);
			leftWindowEnd = (n >> 2) + (blockSize0 >> 2);
			leftN = blockSize0 >> 1;
		} else {
			leftWindowStart = 0;
			leftWindowEnd = windowCenter;
			leftN = n >> 1;
		}
		@Pc(94) int rightWindowStart;
		@Pc(104) int rightWindowEnd;
		@Pc(108) int rightN;
		if (modeBlockFlag && !nextWindowFlag) {
			rightWindowStart = n - (n >> 2) - (blockSize0 >> 2);
			rightWindowEnd = n + (blockSize0 >> 2) - (n >> 2);
			rightN = blockSize0 >> 1;
		} else {
			rightWindowStart = windowCenter;
			rightWindowEnd = n;
			rightN = n >> 1;
		}
		@Pc(123) VorbisMapping mapping = mappings[modeMappings[modeNumber]];
		@Pc(126) int submapNumber = mapping.mux;
		@Pc(131) int floorNumber = mapping.submapFloor[submapNumber];
		@Pc(140) boolean noResidue = !floors[floorNumber].decodePacket();
		for (@Pc(144) int i = 0; i < mapping.submaps; i++) {
			@Pc(155) VorbisResidue residue = residues[mapping.submapResidue[i]];
			@Pc(157) float[] vec = VorbisSound.vector;
			residue.synthesize(vec, n >> 1, noResidue);
		}
		@Pc(176) int floorIdx;
		if (!noResidue) {
			floorNumber = mapping.mux;
			floorIdx = mapping.submapFloor[floorNumber];
			floors[floorIdx].synthesize(vector, n >> 1);
		}
		@Pc(212) int i;
		if (noResidue) {
			for (floorNumber = n >> 1; floorNumber < n; floorNumber++) {
				vector[floorNumber] = 0.0F;
			}
		} else {
			int n2 = n >> 1;
			int n4 = n >> 2;
			int n8 = n >> 3;
			@Pc(214) float[] vec = VorbisSound.vector;
			for (int k = 0; k < n2; k++) {
				vec[k] *= 0.5F;
			}
			for (int k = n2; k < n; k++) {
				vec[k] = -vec[n - k - 1];
			}
			@Pc(252) float[] trigA = modeBlockFlag ? trigA1 : trigA0;
			@Pc(258) float[] trigB = modeBlockFlag ? trigB1 : trigB0;
			@Pc(264) float[] trigC = modeBlockFlag ? trigC1 : trigC0;
			@Pc(270) int[] bitRev = modeBlockFlag ? bitReverse1 : bitReverse0;
			@Pc(272) int k;
			@Pc(291) float a;
			@Pc(309) float b;
			@Pc(315) float c;
			@Pc(323) float d;
			for (k = 0; k < n4; k++) {
				a = vec[k * 4] - vec[n - k * 4 - 1];
				b = vec[k * 4 + 2] - vec[n - k * 4 - 3];
				c = trigA[k * 2];
				d = trigA[k * 2 + 1];
				vec[n - k * 4 - 1] = a * c - b * d;
				vec[n - k * 4 - 3] = a * d + b * c;
			}
			@Pc(432) float e;
			@Pc(442) float f;
			for (k = 0; k < n8; k++) {
				a = vec[n2 + k * 4 + 3];
				b = vec[n2 + k * 4 + 1];
				c = vec[k * 4 + 3];
				d = vec[k * 4 + 1];
				vec[n2 + k * 4 + 3] = a + c;
				vec[n2 + k * 4 + 1] = b + d;
				e = trigA[n2 - k * 4 - 4];
				f = trigA[n2 - k * 4 - 3];
				vec[k * 4 + 3] = (a - c) * e - (b - d) * f;
				vec[k * 4 + 1] = (b - d) * e + (a - c) * f;
			}
			int logN = IntUtils.bitCount(n - 1);
			@Pc(488) int stage;
			@Pc(499) int stride;
			@Pc(503) int halfStride;
			@Pc(505) int block;
			for (stage = 0; stage < logN - 3; stage++) {
				stride = n >> stage + 2;
				halfStride = 0x8 << stage;
				for (block = 0; block < 0x2 << stage; block++) {
					@Pc(518) int off1 = n - stride * 2 * block;
					@Pc(528) int off2 = n - stride * (block * 2 + 1);
					for (@Pc(530) int j = 0; j < n >> stage + 4; j++) {
						@Pc(541) int idx = j * 4;
						@Pc(549) float p = vec[off1 - idx - 1];
						@Pc(557) float q = vec[off1 - idx - 3];
						@Pc(565) float r = vec[off2 - idx - 1];
						@Pc(573) float t = vec[off2 - idx - 3];
						vec[off1 - idx - 1] = p + r;
						vec[off1 - idx - 3] = q + t;
						@Pc(599) float tw1 = trigA[j * halfStride];
						@Pc(607) float tw2 = trigA[j * halfStride + 1];
						vec[off2 - idx - 1] = (p - r) * tw1 - (q - t) * tw2;
						vec[off2 - idx - 3] = (q - t) * tw1 + (p - r) * tw2;
					}
				}
			}
			for (stage = 1; stage < n8 - 1; stage++) {
				stride = bitRev[stage];
				if (stage < stride) {
					halfStride = stage * 8;
					block = stride * 8;
					e = vec[halfStride + 1];
					vec[halfStride + 1] = vec[block + 1];
					vec[block + 1] = e;
					e = vec[halfStride + 3];
					vec[halfStride + 3] = vec[block + 3];
					vec[block + 3] = e;
					e = vec[halfStride + 5];
					vec[halfStride + 5] = vec[block + 5];
					vec[block + 5] = e;
					e = vec[halfStride + 7];
					vec[halfStride + 7] = vec[block + 7];
					vec[block + 7] = e;
				}
			}
			for (stage = 0; stage < n2; stage++) {
				vec[stage] = vec[stage * 2 + 1];
			}
			for (stage = 0; stage < n8; stage++) {
				vec[n - stage * 2 - 1] = vec[stage * 4];
				vec[n - stage * 2 - 2] = vec[stage * 4 + 1];
				vec[n - n4 - stage * 2 - 1] = vec[stage * 4 + 2];
				vec[n - n4 - stage * 2 - 2] = vec[stage * 4 + 3];
			}
			for (stage = 0; stage < n8; stage++) {
				b = trigC[stage * 2];
				c = trigC[stage * 2 + 1];
				d = vec[n2 + stage * 2];
				e = vec[n2 + stage * 2 + 1];
				f = vec[n - stage * 2 - 2];
				@Pc(908) float g = vec[n - stage * 2 - 1];
				@Pc(920) float cross1 = c * (d - f) + b * (e + g);
				vec[n2 + stage * 2] = (d + f + cross1) * 0.5F;
				vec[n - stage * 2 - 2] = (d + f - cross1) * 0.5F;
				@Pc(962) float cross2 = c * (e + g) - b * (d - f);
				vec[n2 + stage * 2 + 1] = (e + cross2 - g) * 0.5F;
				vec[n - stage * 2 - 1] = (g + cross2 - e) * 0.5F;
			}
			for (stage = 0; stage < n4; stage++) {
				vec[stage] = vec[stage * 2 + n2] * trigB[stage * 2] + vec[stage * 2 + n2 + 1] * trigB[stage * 2 + 1];
				vec[n2 - stage - 1] = vec[stage * 2 + n2] * trigB[stage * 2 + 1] - vec[stage * 2 + n2 + 1] * trigB[stage * 2];
			}
			for (stage = 0; stage < n4; stage++) {
				vec[n + stage - n4] = -vec[stage];
			}
			for (stage = 0; stage < n4; stage++) {
				vec[stage] = vec[n4 + stage];
			}
			for (stage = 0; stage < n4; stage++) {
				vec[n4 + stage] = -vec[n4 - stage - 1];
			}
			for (stage = 0; stage < n4; stage++) {
				vec[n2 + stage] = vec[n - stage - 1];
			}
			for (stage = leftWindowStart; stage < leftWindowEnd; stage++) {
				b = (float) Math.sin(((double) (stage - leftWindowStart) + 0.5D) / (double) leftN * 0.5D * 3.141592653589793D);
				VorbisSound.vector[stage] *= (float) Math.sin((double) b * 1.5707963267948966D * (double) b);
			}
			for (stage = rightWindowStart; stage < rightWindowEnd; stage++) {
				b = (float) Math.sin(((double) (stage - rightWindowStart) + 0.5D) / (double) rightN * 0.5D * 3.141592653589793D + 1.5707963267948966D);
				VorbisSound.vector[stage] *= (float) Math.sin((double) b * 1.5707963267948966D * (double) b);
			}
		}
		@Pc(1228) float[] output = null;
		if (this.prevN > 0) {
			floorIdx = this.prevN + n >> 2;
			output = new float[floorIdx];
			@Pc(1257) int j;
			if (!this.prevNoResidue) {
				for (i = 0; i < this.prevQuarter; i++) {
					j = (this.prevN >> 1) + i;
					output[i] += this.prevVector[j];
				}
			}
			if (!noResidue) {
				for (i = leftWindowStart; i < n >> 1; i++) {
					j = output.length + i - (n >> 1);
					output[j] += vector[i];
				}
			}
		}
		@Pc(1301) float[] temp = this.prevVector;
		this.prevVector = vector;
		vector = temp;
		this.prevN = n;
		this.prevQuarter = rightWindowEnd - (n >> 1);
		this.prevNoResidue = noResidue;
		return output;
	}
}
