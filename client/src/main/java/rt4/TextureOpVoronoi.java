package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!hm")
public final class TextureOpVoronoi extends TextureOp {

	@OriginalMember(owner = "client!ec", name = "d", descriptor = "Lclient!gn;")
	public static final LruHashTable permutationTableCache = new LruHashTable(16);
	@OriginalMember(owner = "client!ui", name = "Q", descriptor = "I")
	public static int fourthNearest;
	@OriginalMember(owner = "client!uf", name = "p", descriptor = "I")
	public static int thirdNearest;
	@OriginalMember(owner = "client!cl", name = "X", descriptor = "I")
	public static int nearestDistance;
	@OriginalMember(owner = "client!jg", name = "g", descriptor = "I")
	public static int secondNearest;
	@OriginalMember(owner = "client!hm", name = "S", descriptor = "I")
	private int outputType = 2;

	@OriginalMember(owner = "client!hm", name = "U", descriptor = "I")
	private int jitter = 2048;

	@OriginalMember(owner = "client!hm", name = "Z", descriptor = "I")
	private int seed = 0;

	@OriginalMember(owner = "client!hm", name = "W", descriptor = "I")
	private int distanceMetric = 1;

	@OriginalMember(owner = "client!hm", name = "jb", descriptor = "I")
	private int cellCountY = 5;

	@OriginalMember(owner = "client!hm", name = "X", descriptor = "[B")
	private byte[] permutationTable = new byte[512];

	@OriginalMember(owner = "client!hm", name = "ib", descriptor = "I")
	private int cellCountX = 5;

	@OriginalMember(owner = "client!hm", name = "eb", descriptor = "[S")
	private short[] cellOffsets = new short[512];

	@OriginalMember(owner = "client!hm", name = "<init>", descriptor = "()V")
	public TextureOpVoronoi() {
		super(0, true);
	}

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "(II)[B")
	public static byte[] getPermutationTable(@OriginalArg(1) int seed) {
		@Pc(10) ByteArrayNodeSecondary cached = (ByteArrayNodeSecondary) permutationTableCache.get(seed);
		if (cached == null) {
			@Pc(24) Random rng = new Random(seed);
			@Pc(27) byte[] table = new byte[512];
			@Pc(29) int i;
			for (i = 0; i < 255; i++) {
				table[i] = (byte) i;
			}
			for (i = 0; i < 255; i++) {
				@Pc(53) int remaining = 255 - i;
				@Pc(58) int swapIdx = RandomUtils.nextInt(remaining, rng);
				@Pc(62) byte tmp = table[swapIdx];
				table[swapIdx] = table[remaining];
				table[remaining] = table[511 - i] = tmp;
			}
			cached = new ByteArrayNodeSecondary(table);
			permutationTableCache.put(cached, seed);
		}
		return cached.value;
	}

	@OriginalMember(owner = "client!hm", name = "f", descriptor = "(B)V")
	private void initRandomOffsets() {
		@Pc(12) Random rng = new Random(this.seed);
		this.cellOffsets = new short[512];
		if (this.jitter > 0) {
			for (@Pc(26) int i = 0; i < 512; i++) {
				this.cellOffsets[i] = (short) RandomUtils.nextInt(this.jitter, rng);
			}
		}
	}

	@OriginalMember(owner = "client!hm", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.permutationTable = getPermutationTable(this.seed);
		this.initRandomOffsets();
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(32) int scaledY = this.cellCountY * Texture.heightFractions[row] + 2048;
			@Pc(36) int cellY = scaledY >> 12;
			@Pc(40) int cellYMax = cellY + 1;
			for (@Pc(42) int col = 0; col < Texture.width; col++) {
				fourthNearest = Integer.MAX_VALUE;
				thirdNearest = Integer.MAX_VALUE;
				secondNearest = Integer.MAX_VALUE;
				nearestDistance = Integer.MAX_VALUE;
				@Pc(62) int scaledX = this.cellCountX * Texture.widthFractions[col] + 2048;
				@Pc(66) int cellX = scaledX >> 12;
				@Pc(70) int cellXMax = cellX + 1;
				@Pc(165) int type;
				for (@Pc(74) int cy = cellY - 1; cy <= cellYMax; cy++) {
					@Pc(104) int permY = this.permutationTable[(this.cellCountY <= cy ? cy - this.cellCountY : cy) & 0xFF] & 0xFF;
					for (@Pc(108) int cx = cellX - 1; cx <= cellXMax; cx++) {
						@Pc(138) int permIdx = (this.permutationTable[(this.cellCountX <= cx ? cx - this.cellCountX : cx) + permY & 0xFF] & 0xFF) * 2;
						@Pc(142) int negCellX = -(cx << 12);
						@Pc(146) int permIdxY = permIdx + 1;
						@Pc(151) int dx = negCellX + scaledX - this.cellOffsets[permIdx];
						@Pc(162) int dy = scaledY - this.cellOffsets[permIdxY] - (cy << 12);
						type = this.distanceMetric;
						@Pc(201) int dist;
						if (type == 1) {
							dist = dy * dy + dx * dx >> 12;
						} else if (type == 3) {
							dx = dx < 0 ? -dx : dx;
							dy = dy >= 0 ? dy : -dy;
							dist = dy >= dx ? dy : dx;
						} else if (type == 4) {
							dx = (int) (Math.sqrt((float) (dx < 0 ? -dx : dx) / 4096.0F) * 4096.0D);
							dy = (int) (Math.sqrt((float) (dy >= 0 ? dy : -dy) / 4096.0F) * 4096.0D);
							dist = dy + dx;
							dist = dist * dist >> 12;
						} else if (type == 5) {
							dx *= dx;
							dy *= dy;
							dist = (int) (Math.sqrt(Math.sqrt((float) (dy + dx) / 1.6777216E7F)) * 4096.0D);
						} else if (type == 2) {
							dist = (dx >= 0 ? dx : -dx) + (dy < 0 ? -dy : dy);
						} else {
							dist = (int) (Math.sqrt((float) (dy * dy + dx * dx) / 1.6777216E7F) * 4096.0D);
						}
						if (dist < nearestDistance) {
							fourthNearest = thirdNearest;
							thirdNearest = secondNearest;
							secondNearest = nearestDistance;
							nearestDistance = dist;
						} else if (dist < secondNearest) {
							fourthNearest = thirdNearest;
							thirdNearest = secondNearest;
							secondNearest = dist;
						} else if (thirdNearest > dist) {
							fourthNearest = thirdNearest;
							thirdNearest = dist;
						} else if (dist < fourthNearest) {
							fourthNearest = dist;
						}
					}
				}
				type = this.outputType;
				if (type == 0) {
					output[col] = nearestDistance;
				} else if (type == 1) {
					output[col] = secondNearest;
				} else if (type == 3) {
					output[col] = thirdNearest;
				} else if (type == 4) {
					output[col] = fourthNearest;
				} else if (type == 2) {
					output[col] = secondNearest - nearestDistance;
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.cellCountX = this.cellCountY = buf.g1();
		} else if (opcode == 1) {
			this.seed = buf.g1();
		} else if (opcode == 2) {
			this.jitter = buf.g2();
		} else if (opcode == 3) {
			this.outputType = buf.g1();
		} else if (opcode == 4) {
			this.distanceMetric = buf.g1();
		} else if (opcode == 5) {
			this.cellCountX = buf.g1();
		} else if (opcode == 6) {
			this.cellCountY = buf.g1();
		}
	}
}
