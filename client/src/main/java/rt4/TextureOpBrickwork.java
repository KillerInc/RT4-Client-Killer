package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!mc")
public final class TextureOpBrickwork extends TextureOp {

	@OriginalMember(owner = "client!mc", name = "U", descriptor = "I")
	private int rowHeight;

	@OriginalMember(owner = "client!mc", name = "V", descriptor = "I")
	private int halfMortarSize;

	@OriginalMember(owner = "client!mc", name = "X", descriptor = "[[I")
	private int[][] columnPositions;

	@OriginalMember(owner = "client!mc", name = "cb", descriptor = "[I")
	private int[] rowPositions;

	@OriginalMember(owner = "client!mc", name = "jb", descriptor = "I")
	private int columnWidth;

	@OriginalMember(owner = "client!mc", name = "sb", descriptor = "[[I")
	private int[][] cellBrightness;

	@OriginalMember(owner = "client!mc", name = "P", descriptor = "I")
	private int rowJitter = 204;

	@OriginalMember(owner = "client!mc", name = "bb", descriptor = "I")
	private int colorVariation = 1024;

	@OriginalMember(owner = "client!mc", name = "eb", descriptor = "I")
	private int scrollOffsetY = 0;

	@OriginalMember(owner = "client!mc", name = "lb", descriptor = "I")
	private int columnJitter = 409;

	@OriginalMember(owner = "client!mc", name = "hb", descriptor = "I")
	private int rows = 8;

	@OriginalMember(owner = "client!mc", name = "W", descriptor = "I")
	private int mortarSize = 81;

	@OriginalMember(owner = "client!mc", name = "ub", descriptor = "I")
	private int columns = 4;

	@OriginalMember(owner = "client!mc", name = "ib", descriptor = "I")
	private int columnOffset = 1024;

	@OriginalMember(owner = "client!mc", name = "<init>", descriptor = "()V")
	public TextureOpBrickwork() {
		super(0, true);
	}

	@OriginalMember(owner = "client!mc", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
		this.buildLookupTable();
	}

	@OriginalMember(owner = "client!mc", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.columns = buf.g1();
		} else if (opcode == 1) {
			this.rows = buf.g1();
		} else if (opcode == 2) {
			this.columnJitter = buf.g2();
		} else if (opcode == 3) {
			this.rowJitter = buf.g2();
		} else if (opcode == 4) {
			this.columnOffset = buf.g2();
		} else if (opcode == 5) {
			this.scrollOffsetY = buf.g2();
		} else if (opcode == 6) {
			this.mortarSize = buf.g2();
		} else if (opcode == 7) {
			this.colorVariation = buf.g2();
		}
	}

	@OriginalMember(owner = "client!mc", name = "i", descriptor = "(I)V")
	private void buildLookupTable() {
		@Pc(8) Random rng = new Random(this.rows);
		this.rowHeight = 4096 / this.rows;
		this.halfMortarSize = this.mortarSize / 2;
		this.columnPositions = new int[this.rows][this.columns + 1];
		@Pc(34) int halfRowHeight = this.rowHeight / 2;
		this.rowPositions = new int[this.rows + 1];
		this.cellBrightness = new int[this.rows][this.columns];
		this.columnWidth = 4096 / this.columns;
		this.rowPositions[0] = 0;
		@Pc(64) int halfColWidth = this.columnWidth / 2;
		for (@Pc(66) int r = 0; r < this.rows; r++) {
			@Pc(82) int height;
			@Pc(94) int jitter;
			if (r > 0) {
				height = this.rowHeight;
				jitter = (RandomUtils.nextInt(4096, rng) - 2048) * this.rowJitter >> 12;
				@Pc(102) int adjustedHeight = height + (jitter * halfRowHeight >> 12);
				this.rowPositions[r] = this.rowPositions[r - 1] + adjustedHeight;
			}
			this.columnPositions[r][0] = 0;
			for (height = 0; height < this.columns; height++) {
				if (height > 0) {
					jitter = this.columnWidth;
					@Pc(150) int colJitter = (RandomUtils.nextInt(4096, rng) - 2048) * this.columnJitter >> 12;
					jitter += halfColWidth * colJitter >> 12;
					this.columnPositions[r][height] = this.columnPositions[r][height - 1] + jitter;
				}
				this.cellBrightness[r][height] = this.colorVariation <= 0 ? 4096 : 4096 - RandomUtils.nextInt(this.colorVariation, rng);
			}
			this.columnPositions[r][this.columns] = 4096;
		}
		this.rowPositions[this.rows] = 4096;
	}

	@OriginalMember(owner = "client!mc", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(11) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(18) int rowIdx = 0;
			@Pc(25) int y;
			for (y = Texture.heightFractions[row] + this.scrollOffsetY; y < 0; y += 4096) {
			}
			while (y > 4096) {
				y -= 4096;
			}
			while (this.rows > rowIdx && y >= this.rowPositions[rowIdx]) {
				rowIdx++;
			}
			@Pc(60) int brickRow = rowIdx - 1;
			@Pc(65) int rowEnd = this.rowPositions[rowIdx];
			@Pc(74) boolean evenRow = (rowIdx & 0x1) == 0;
			@Pc(81) int rowStart = this.rowPositions[rowIdx - 1];
			if (rowStart + this.halfMortarSize < y && rowEnd - this.halfMortarSize > y) {
				for (@Pc(100) int col = 0; col < Texture.width; col++) {
					@Pc(105) int colIdx = 0;
					@Pc(114) int offset = evenRow ? this.columnOffset : -this.columnOffset;
					@Pc(126) int x;
					for (x = Texture.widthFractions[col] + (this.columnWidth * offset >> 12); x < 0; x += 4096) {
					}
					while (x > 4096) {
						x -= 4096;
					}
					while (this.columns > colIdx && x >= this.columnPositions[brickRow][colIdx]) {
						colIdx++;
					}
					@Pc(172) int colEnd = this.columnPositions[brickRow][colIdx];
					@Pc(176) int brickCol = colIdx - 1;
					@Pc(183) int colStart = this.columnPositions[brickRow][brickCol];
					if (colStart + this.halfMortarSize < x && colEnd - this.halfMortarSize > x) {
						output[col] = this.cellBrightness[brickRow][brickCol];
					} else {
						output[col] = 0;
					}
				}
			} else {
				ArrayUtils.fill(output, 0, Texture.width, 0);
			}
		}
		return output;
	}
}
