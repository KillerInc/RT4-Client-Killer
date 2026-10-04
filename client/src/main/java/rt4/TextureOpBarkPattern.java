package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!ol")
public final class TextureOpBarkPattern extends TextureOp {

	@OriginalMember(owner = "client!ol", name = "gb", descriptor = "I")
	private int cornerSize;

	@OriginalMember(owner = "client!ol", name = "R", descriptor = "I")
	private int maxHeight = 819;

	@OriginalMember(owner = "client!ol", name = "ab", descriptor = "I")
	private int cornerMode = 0;

	@OriginalMember(owner = "client!ol", name = "Z", descriptor = "I")
	private int maxWidth = 2048;

	@OriginalMember(owner = "client!ol", name = "T", descriptor = "I")
	private int roundness = 1024;

	@OriginalMember(owner = "client!ol", name = "Q", descriptor = "I")
	private int minWidth = 1024;

	@OriginalMember(owner = "client!ol", name = "cb", descriptor = "I")
	private int minHeight = 409;

	@OriginalMember(owner = "client!ol", name = "kb", descriptor = "I")
	private int brightnessVariation = 1024;

	@OriginalMember(owner = "client!ol", name = "P", descriptor = "I")
	private int roundnessVariation = 1024;

	@OriginalMember(owner = "client!ol", name = "eb", descriptor = "I")
	private int seed = 0;

	@OriginalMember(owner = "client!ol", name = "<init>", descriptor = "()V")
	public TextureOpBarkPattern() {
		super(0, true);
	}

	@OriginalMember(owner = "client!ol", name = "e", descriptor = "(I)V")
	@Override
	public final void postDecode() {
	}

	@OriginalMember(owner = "client!ol", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(19) int[] output = this.monochromeImageCache.get(row);
		if (!this.monochromeImageCache.invalid) {
			return output;
		}
		@Pc(29) int[][] allRows = this.monochromeImageCache.getAll();
		@Pc(31) int curX = 0;
		@Pc(33) int prevRowOffset = 0;
		@Pc(35) int rowDelta = 0;
		@Pc(37) int rowOffset = 0;
		@Pc(39) int prevBandIdx = 0;
		@Pc(41) boolean lastRow = true;
		@Pc(43) boolean firstRow = true;
		@Pc(45) int prevBandCount = 0;
		@Pc(47) int curBandIdx = 0;
		@Pc(54) int minW = Texture.width * this.minWidth >> 12;
		@Pc(61) int maxW = Texture.width * this.maxWidth >> 12;
		@Pc(68) int maxH = this.maxHeight * Texture.height >> 12;
		@Pc(75) int minH = Texture.height * this.minHeight >> 12;
		if (maxH <= 1) {
			return allRows[row];
		}
		@Pc(88) int maxBands = Texture.width / minW + 1;
		this.cornerSize = Texture.width / 8 * this.roundness >> 12;
		@Pc(102) int[][] prevBands = new int[maxBands][3];
		@Pc(106) int[][] curBands = new int[maxBands][3];
		@Pc(113) Random rng = new Random(this.seed);
		while (true) {
			while (true) {
				@Pc(123) int width = minW + RandomUtils.nextInt(maxW - minW, rng);
				@Pc(133) int height = RandomUtils.nextInt(maxH - minH, rng) + minH;
				@Pc(137) int endX = curX + width;
				if (Texture.width < endX) {
					endX = Texture.width;
					width = Texture.width - curX;
				}
				@Pc(158) int startY;
				@Pc(160) int spanCount;
				if (firstRow) {
					startY = 0;
				} else {
					@Pc(150) int scanIdx = prevBandIdx;
					@Pc(154) int[] band = prevBands[prevBandIdx];
					startY = band[2];
					spanCount = 0;
					@Pc(164) int wrappedEnd = rowDelta + endX;
					if (wrappedEnd < 0) {
						wrappedEnd += Texture.width;
					}
					if (Texture.width < wrappedEnd) {
						wrappedEnd -= Texture.width;
					}
					while (true) {
						@Pc(186) int[] scanBand = prevBands[scanIdx];
						if (wrappedEnd >= scanBand[0] && scanBand[1] >= wrappedEnd) {
							if (prevBandIdx != scanIdx) {
								@Pc(224) int wrappedStart = rowDelta + curX;
								if (wrappedStart < 0) {
									wrappedStart += Texture.width;
								}
								if (wrappedStart > Texture.width) {
									wrappedStart -= Texture.width;
								}
								@Pc(243) int j;
								@Pc(258) int[] gapBand;
								for (j = 1; j <= spanCount; j++) {
									gapBand = prevBands[(prevBandIdx + j) % prevBandCount];
									startY = Math.max(startY, gapBand[2]);
								}
								for (j = 0; j <= spanCount; j++) {
									gapBand = prevBands[(prevBandIdx + j) % prevBandCount];
									@Pc(285) int gapY = gapBand[2];
									if (gapY != startY) {
										@Pc(297) int gapStart = gapBand[0];
										@Pc(301) int gapEnd = gapBand[1];
										@Pc(312) int fillStart;
										@Pc(316) int fillEnd;
										if (wrappedEnd > wrappedStart) {
											fillStart = Math.max(wrappedStart, gapStart);
											fillEnd = Math.min(wrappedEnd, gapEnd);
										} else if (gapStart == 0) {
											fillEnd = Math.min(wrappedEnd, gapEnd);
											fillStart = 0;
										} else {
											fillStart = Math.max(wrappedStart, gapStart);
											fillEnd = Texture.width;
										}
										this.renderBranch(gapY, rng, prevRowOffset + fillStart, -fillStart + fillEnd, startY - gapY, allRows);
									}
								}
							}
							prevBandIdx = scanIdx;
							break;
						}
						spanCount++;
						scanIdx++;
						if (scanIdx >= prevBandCount) {
							scanIdx = 0;
						}
					}
				}
				if (Texture.height >= startY + height) {
					lastRow = false;
				} else {
					height = Texture.height - startY;
				}
				@Pc(407) int[] entry;
				if (endX == Texture.width) {
					this.renderBranch(startY, rng, rowOffset + curX, width, height, allRows);
					if (lastRow) {
						return output;
					}
					lastRow = true;
					prevRowOffset = rowOffset;
					firstRow = false;
					entry = curBands[curBandIdx++];
					entry[0] = curX;
					prevBandIdx = 0;
					prevBandCount = curBandIdx;
					curBandIdx = 0;
					entry[2] = height + startY;
					entry[1] = endX;
					rowOffset = RandomUtils.nextInt(Texture.width, rng);
					rowDelta = rowOffset - prevRowOffset;
					@Pc(439) int[][] tmp = prevBands;
					curX = 0;
					prevBands = curBands;
					spanCount = rowDelta;
					if (rowDelta < 0) {
						spanCount = rowDelta + Texture.width;
					}
					curBands = tmp;
					if (Texture.width < spanCount) {
						spanCount -= Texture.width;
					}
					while (true) {
						@Pc(469) int[] prevEntry = prevBands[prevBandIdx];
						if (prevEntry[0] <= spanCount && prevEntry[1] >= spanCount) {
							break;
						}
						prevBandIdx++;
						if (prevBandCount <= prevBandIdx) {
							prevBandIdx = 0;
						}
					}
				} else {
					entry = curBands[curBandIdx++];
					entry[1] = endX;
					entry[2] = height + startY;
					entry[0] = curX;
					this.renderBranch(startY, rng, curX + rowOffset, width, height, allRows);
					curX = endX;
				}
			}
		}
	}

	@OriginalMember(owner = "client!ol", name = "a", descriptor = "(ILjava/util/Random;IIBI[[I)V")
	private void renderBranch(@OriginalArg(0) int startY, @OriginalArg(1) Random rng, @OriginalArg(2) int x, @OriginalArg(3) int width, @OriginalArg(5) int height, @OriginalArg(6) int[][] allRows) {
		@Pc(26) int brightness = this.brightnessVariation > 0 ? 4096 - RandomUtils.nextInt(this.brightnessVariation, rng) : 4096;
		@Pc(34) int cornerVariation = this.roundnessVariation * this.cornerSize >> 12;
		@Pc(47) int corner = this.cornerSize - (cornerVariation > 0 ? RandomUtils.nextInt(cornerVariation, rng) : 0);
		if (Texture.width <= x) {
			x -= Texture.width;
		}
		@Pc(68) int i;
		@Pc(72) int j;
		if (corner > 0) {
			if (height <= 0 || width <= 0) {
				return;
			}
			i = width / 2;
			j = height / 2;
			@Pc(79) int cornerW = i < corner ? i : corner;
			@Pc(90) int cornerH = j >= corner ? corner : j;
			@Pc(97) int innerWidth = width - cornerW * 2;
			@Pc(101) int innerX = cornerW + x;
			for (@Pc(103) int row = 0; row < height; row++) {
				@Pc(112) int[] rowData = allRows[startY + row];
				@Pc(125) int fade;
				@Pc(133) int col;
				@Pc(142) int colFade;
				if (row < cornerH) {
					fade = row * brightness / cornerH;
					if (this.cornerMode == 0) {
						for (col = 0; col < cornerW; col++) {
							colFade = col * brightness / cornerW;
							rowData[Texture.widthMask & col + x] = rowData[width + x - col - 1 & Texture.widthMask] = colFade * fade >> 12;
						}
					} else {
						for (col = 0; col < cornerW; col++) {
							colFade = col * brightness / cornerW;
							rowData[Texture.widthMask & x + col] = rowData[Texture.widthMask & width + x - col - 1] = fade <= colFade ? fade : colFade;
						}
					}
					if (Texture.width >= innerWidth + innerX) {
						ArrayUtils.fill(rowData, innerX, innerWidth, fade);
					} else {
						col = Texture.width - innerX;
						ArrayUtils.fill(rowData, innerX, col, fade);
						ArrayUtils.fill(rowData, 0, innerWidth - col, fade);
					}
				} else {
					fade = height - row - 1;
					if (fade >= cornerH) {
						for (col = 0; col < cornerW; col++) {
							rowData[Texture.widthMask & x + col] = rowData[x + width - col - 1 & Texture.widthMask] = brightness * col / cornerW;
						}
						if (innerX + innerWidth > Texture.width) {
							col = Texture.width - innerX;
							ArrayUtils.fill(rowData, innerX, col, brightness);
							ArrayUtils.fill(rowData, 0, innerWidth - col, brightness);
						} else {
							ArrayUtils.fill(rowData, innerX, innerWidth, brightness);
						}
					} else {
						col = fade * brightness / cornerH;
						@Pc(288) int edgeFade;
						if (this.cornerMode == 0) {
							for (colFade = 0; colFade < cornerW; colFade++) {
								edgeFade = brightness * colFade / cornerW;
								rowData[Texture.widthMask & x + colFade] = rowData[Texture.widthMask & x + width - colFade - 1] = edgeFade * col >> 12;
							}
						} else {
							for (colFade = 0; colFade < cornerW; colFade++) {
								edgeFade = colFade * brightness / cornerW;
								rowData[x + colFade & Texture.widthMask] = rowData[width + x - colFade - 1 & Texture.widthMask] = col <= edgeFade ? col : edgeFade;
							}
						}
						if (innerWidth + innerX > Texture.width) {
							colFade = Texture.width - innerX;
							ArrayUtils.fill(rowData, innerX, colFade, col);
							ArrayUtils.fill(rowData, 0, innerWidth - colFade, col);
						} else {
							ArrayUtils.fill(rowData, innerX, innerWidth, col);
						}
					}
				}
			}
		} else if (Texture.width >= x + width) {
			for (i = 0; i < height; i++) {
				ArrayUtils.fill(allRows[startY + i], x, width, brightness);
			}
		} else {
			i = Texture.width - x;
			for (j = 0; j < height; j++) {
				@Pc(522) int[] rowData = allRows[j + startY];
				ArrayUtils.fill(rowData, x, i, brightness);
				ArrayUtils.fill(rowData, 0, width - i, brightness);
			}
		}
	}

	@OriginalMember(owner = "client!ol", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.seed = buf.g1();
		} else if (opcode == 1) {
			this.minWidth = buf.g2();
		} else if (opcode == 2) {
			this.maxWidth = buf.g2();
		} else if (opcode == 3) {
			this.minHeight = buf.g2();
		} else if (opcode == 4) {
			this.maxHeight = buf.g2();
		} else if (opcode == 5) {
			this.roundness = buf.g2();
		} else if (opcode == 6) {
			this.cornerMode = buf.g1();
		} else if (opcode == 7) {
			this.roundnessVariation = buf.g2();
		} else if (opcode == 8) {
			this.brightnessVariation = buf.g2();
		}
	}
}
