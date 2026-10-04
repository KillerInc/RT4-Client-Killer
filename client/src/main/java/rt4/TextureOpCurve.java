package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!wi")
public final class TextureOpCurve extends TextureOp {

	@OriginalMember(owner = "client!wi", name = "Q", descriptor = "[I")
	private int[] lastMarker;

	@OriginalMember(owner = "client!wi", name = "gb", descriptor = "[I")
	private int[] firstMarker;

	@OriginalMember(owner = "client!wi", name = "ib", descriptor = "[[I")
	private int[][] markers;

	@OriginalMember(owner = "client!wi", name = "cb", descriptor = "[S")
	private final short[] lookupTable = new short[257];

	@OriginalMember(owner = "client!wi", name = "Z", descriptor = "I")
	private int interpolationMode = 0;

	@OriginalMember(owner = "client!wi", name = "<init>", descriptor = "()V")
	public TextureOpCurve() {
		super(1, true);
	}

	@OriginalMember(owner = "client!wi", name = "h", descriptor = "(I)V")
	private void buildLookupTable() {
		@Pc(8) int mode = this.interpolationMode;
		@Pc(29) int markerIdx;
		@Pc(27) int value;
		@Pc(59) int[] prev;
		@Pc(52) int[] cur;
		@Pc(68) int t;
		@Pc(76) int weight;
		@Pc(72) int result;
		@Pc(89) int nextVal;
		if (mode == 2) {
			for (mode = 0; mode < 257; mode++) {
				value = mode << 4;
				for (markerIdx = 1; this.markers.length - 1 > markerIdx && this.markers[markerIdx][0] <= value; markerIdx++) {
				}
				cur = this.markers[markerIdx];
				prev = this.markers[markerIdx - 1];
				t = this.getMarker(markerIdx - 2)[1];
				result = cur[1];
				weight = prev[1];
				@Pc(80) int diff = result - t;
				nextVal = this.getMarker(markerIdx + 1)[1];
				@Pc(107) int frac = (value - prev[0] << 12) / (cur[0] - prev[0]);
				@Pc(117) int a = nextVal + weight - result - t;
				@Pc(123) int fracSq = frac * frac >> 12;
				@Pc(130) int b = t - weight - a;
				@Pc(136) int c = diff * frac >> 12;
				@Pc(142) int bTerm = fracSq * b >> 12;
				@Pc(154) int aTerm = fracSq * (frac * a >> 12) >> 12;
				@Pc(162) int interp = weight + bTerm + aTerm + c;
				if (interp <= -32768) {
					interp = -32767;
				}
				if (interp >= 32768) {
					interp = 32767;
				}
				this.lookupTable[mode] = (short) interp;
			}
		} else if (mode == 1) {
			for (mode = 0; mode < 257; mode++) {
				value = mode << 4;
				for (markerIdx = 1; markerIdx < this.markers.length - 1 && this.markers[markerIdx][0] <= value; markerIdx++) {
				}
				prev = this.markers[markerIdx - 1];
				cur = this.markers[markerIdx];
				t = (value - prev[0] << 12) / (cur[0] - prev[0]);
				weight = 4096 - TextureOp.COSINE[t >> 5 & 0xFF] >> 1;
				result = 4096 - weight;
				nextVal = weight * cur[1] + prev[1] * result >> 12;
				if (nextVal <= -32768) {
					nextVal = -32767;
				}
				if (nextVal >= 32768) {
					nextVal = 32767;
				}
				this.lookupTable[mode] = (short) nextVal;
			}
		} else {
			for (mode = 0; mode < 257; mode++) {
				value = mode << 4;
				for (markerIdx = 1; this.markers.length - 1 > markerIdx && this.markers[markerIdx][0] <= value; markerIdx++) {
				}
				cur = this.markers[markerIdx];
				prev = this.markers[markerIdx - 1];
				t = (value - prev[0] << 12) / (cur[0] - prev[0]);
				weight = 4096 - t;
				result = cur[1] * t + weight * prev[1] >> 12;
				if (result <= -32768) {
					result = -32767;
				}
				if (result >= 32768) {
					result = 32767;
				}
				this.lookupTable[mode] = (short) result;
			}
		}
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(29) int[] input = this.getChildMonochromeOutput(0, row);
			for (@Pc(31) int i = 0; i < Texture.width; i++) {
				@Pc(44) int idx = input[i] >> 4;
				if (idx < 0) {
					idx = 0;
				}
				if (idx > 256) {
					idx = 256;
				}
				output[i] = this.lookupTable[idx];
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!wi", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		if (this.markers == null) {
			this.markers = new int[][]{{0, 0}, {4096, 4096}};
		}
		if (this.markers.length < 2) {
			throw new RuntimeException("Curve operation requires at least two markers");
		}
		if (this.interpolationMode == 2) {
			this.initExtrapolationMarkers();
		}
		TextureOp.createTrigonometryTables();
		this.buildLookupTable();
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(Z)V")
	private void initExtrapolationMarkers() {
		@Pc(8) int[] first = this.markers[0];
		@Pc(20) int[] second = this.markers[1];
		@Pc(29) int[] secondLast = this.markers[this.markers.length - 2];
		@Pc(38) int[] last = this.markers[this.markers.length - 1];
		this.lastMarker = new int[]{first[0] + first[0] - second[0], -second[1] + first[1] + first[1]};
		this.firstMarker = new int[]{secondLast[0] + secondLast[0] - last[0], -last[1] - -secondLast[1] + secondLast[1]};
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode != 0) {
			return;
		}
		this.interpolationMode = buf.g1();
		this.markers = new int[buf.g1()][2];
		for (@Pc(23) int i = 0; i < this.markers.length; i++) {
			this.markers[i][0] = buf.g2();
			this.markers[i][1] = buf.g2();
		}
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(BI)[I")
	private int[] getMarker(@OriginalArg(1) int index) {
		if (index >= 0) {
			return index >= this.markers.length ? this.firstMarker : this.markers[index];
		} else {
			return this.lastMarker;
		}
	}
}
