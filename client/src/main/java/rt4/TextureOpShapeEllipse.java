package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!kc")
public final class TextureOpShapeEllipse extends TextureOpShape {

	@OriginalMember(owner = "client!th", name = "o", descriptor = "[I")
	public static int[] scratchBuffer;
	@OriginalMember(owner = "client!kc", name = "B", descriptor = "I")
	private final int radiusY;

	@OriginalMember(owner = "client!kc", name = "y", descriptor = "I")
	private final int centerX;

	@OriginalMember(owner = "client!kc", name = "v", descriptor = "I")
	private final int centerY;

	@OriginalMember(owner = "client!kc", name = "m", descriptor = "I")
	private final int radiusX;

	@OriginalMember(owner = "client!kc", name = "<init>", descriptor = "(IIIIIII)V")
	public TextureOpShapeEllipse(@OriginalArg(0) int centerX, @OriginalArg(1) int centerY, @OriginalArg(2) int radiusX, @OriginalArg(3) int radiusY, @OriginalArg(4) int fillColor, @OriginalArg(5) int outlineColor, @OriginalArg(6) int lineWidth) {
		super(fillColor, outlineColor, lineWidth);
		this.radiusY = radiusY;
		this.centerX = centerX;
		this.centerY = centerY;
		this.radiusX = radiusX;
	}

	@OriginalMember(owner = "client!kl", name = "a", descriptor = "(Lclient!wa;B)Lclient!kc;")
	public static TextureOpShapeEllipse create(@OriginalArg(0) Buffer buf) {
		return new TextureOpShapeEllipse(buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g3(), buf.g3(), buf.g1());
	}

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(ZIIIIIII)V")
	public static void drawEllipseBordered(@OriginalArg(1) int borderWidth, @OriginalArg(2) int cx, @OriginalArg(3) int cy, @OriginalArg(4) int ry, @OriginalArg(5) int borderColor, @OriginalArg(6) int fillColor, @OriginalArg(7) int rx) {
		if (ry == rx) {
			drawCircleBordered(borderWidth, cy, fillColor, rx, cx, borderColor);
		} else if (cx - rx >= TextureOpShapeRasterizer.clipLeft && rx + cx <= TextureOpShapeRasterizer.clipRight && TextureOpShapeRasterizer.clipTop <= cy - ry && TextureOpShapeRasterizer.clipBottom >= cy + ry) {
			drawEllipseBorderedFast(fillColor, cx, cy, borderColor, rx, ry, borderWidth);
		} else {
			TextureOpShapeRasterizer.drawEllipseBorderedClipped(rx, fillColor, borderColor, borderWidth, cy, cx, ry);
		}
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(IIIIIIII)V")
	public static void drawEllipseBorderedFast(@OriginalArg(0) int fillColor, @OriginalArg(1) int cx, @OriginalArg(2) int cy, @OriginalArg(3) int borderColor, @OriginalArg(4) int rx, @OriginalArg(6) int ry, @OriginalArg(7) int borderWidth) {
		@Pc(7) int outerX = 0;
		@Pc(9) int y = ry;
		@Pc(11) int innerX = 0;
		@Pc(16) int innerRx = rx - borderWidth;
		@Pc(20) int rxSq = rx * rx;
		@Pc(25) int innerRy = ry - borderWidth;
		@Pc(29) int rySq = ry * ry;
		@Pc(33) int innerRySq = innerRy * innerRy;
		@Pc(37) int rySq2 = rySq << 1;
		@Pc(41) int rxSq2 = rxSq << 1;
		@Pc(45) int innerRySq2 = innerRySq << 1;
		@Pc(49) int innerRxSq = innerRx * innerRx;
		@Pc(53) int innerRxSq2 = innerRxSq << 1;
		@Pc(57) int ry2 = ry << 1;
		@Pc(61) int innerRy2 = innerRy << 1;
		@Pc(70) int outerErr2 = rySq - (ry2 - 1) * rxSq2;
		@Pc(80) int innerErr1 = innerRxSq * (1 - innerRy2) + innerRySq2;
		@Pc(89) int outerErr1 = (1 - ry2) * rxSq + rySq2;
		@Pc(93) int rxSq4 = rxSq << 2;
		@Pc(102) int innerErr2 = innerRySq - innerRxSq2 * (innerRy2 - 1);
		@Pc(106) int innerRxSq4 = innerRxSq << 2;
		@Pc(110) int rySq4 = rySq << 2;
		@Pc(114) int innerRySq4 = innerRySq << 2;
		@Pc(120) int outerDec1 = (ry2 - 3) * rxSq2;
		@Pc(124) int outerInc1 = rySq2 * 3;
		@Pc(130) int innerDec1 = (innerRy2 - 3) * innerRxSq2;
		@Pc(134) int innerInc1 = innerRySq2 * 3;
		@Pc(136) int outerInc2 = rySq4;
		@Pc(138) int innerInc2 = innerRySq4;
		@Pc(144) int innerDec2 = (innerRy - 1) * innerRxSq4;
		@Pc(162) int outerDec2 = (ry - 1) * rxSq4;
		@Pc(166) int[] centerRow = TextureOpShapeLine.canvas[cy];
		ArrayUtils.fillRange(centerRow, cx - rx, -innerRx + cx, borderColor);
		ArrayUtils.fillRange(centerRow, cx - innerRx, cx - -innerRx, fillColor);
		ArrayUtils.fillRange(centerRow, cx + innerRx, rx + cx, borderColor);
		while (y > 0) {
			if (outerErr1 < 0) {
				while (outerErr1 < 0) {
					outerErr2 += outerInc2;
					outerErr1 += outerInc1;
					outerX++;
					outerInc2 += rySq4;
					outerInc1 += rySq4;
				}
			}
			if (outerErr2 < 0) {
				outerErr1 += outerInc1;
				outerX++;
				outerInc1 += rySq4;
				outerErr2 += outerInc2;
				outerInc2 += rySq4;
			}
			@Pc(258) boolean hasInner = y <= innerRy;
			if (hasInner) {
				if (innerErr1 < 0) {
					while (innerErr1 < 0) {
						innerErr1 += innerInc1;
						innerInc1 += innerRySq4;
						innerX++;
						innerErr2 += innerInc2;
						innerInc2 += innerRySq4;
					}
				}
				if (innerErr2 < 0) {
					innerErr1 += innerInc1;
					innerX++;
					innerInc1 += innerRySq4;
					innerErr2 += innerInc2;
					innerInc2 += innerRySq4;
				}
				innerErr2 += -innerDec1;
				innerDec1 -= innerRxSq4;
				innerErr1 += -innerDec2;
				innerDec2 -= innerRxSq4;
			}
			outerErr1 += -outerDec2;
			outerErr2 += -outerDec1;
			outerDec1 -= rxSq4;
			y--;
			@Pc(348) int yTop = cy - y;
			@Pc(352) int xRight = outerX + cx;
			outerDec2 -= rxSq4;
			@Pc(360) int yBottom = y + cy;
			@Pc(365) int xLeft = cx - outerX;
			if (hasInner) {
				@Pc(371) int innerRight = cx + innerX;
				@Pc(376) int innerLeft = cx - innerX;
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], xLeft, innerLeft, borderColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], innerLeft, innerRight, fillColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], innerRight, xRight, borderColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], xLeft, innerLeft, borderColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], innerLeft, innerRight, fillColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], innerRight, xRight, borderColor);
			} else {
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], xLeft, xRight, borderColor);
				ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], xLeft, xRight, borderColor);
			}
		}
	}

	@OriginalMember(owner = "client!ma", name = "a", descriptor = "(IIBIIII)V")
	public static void drawCircleBordered(@OriginalArg(0) int borderWidth, @OriginalArg(1) int cy, @OriginalArg(3) int fillColor, @OriginalArg(4) int radius, @OriginalArg(5) int cx, @OriginalArg(6) int borderColor) {
		if (cx - radius >= TextureOpShapeRasterizer.clipLeft && TextureOpShapeRasterizer.clipRight >= cx + radius && TextureOpShapeRasterizer.clipTop <= cy - radius && TextureOpShapeRasterizer.clipBottom >= radius + cy) {
			drawCircleBorderedFast(borderColor, borderWidth, cy, fillColor, radius, cx);
		} else {
			drawCircleBorderedClipped(fillColor, cy, radius, borderColor, borderWidth, cx);
		}
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(IIIZIII)V")
	public static void drawCircleBorderedFast(@OriginalArg(0) int borderColor, @OriginalArg(1) int borderWidth, @OriginalArg(2) int cy, @OriginalArg(4) int fillColor, @OriginalArg(5) int radius, @OriginalArg(6) int cx) {
		ensureScratchBufferCapacity(radius);
		@Pc(8) int innerRadius = radius - borderWidth;
		@Pc(10) int x = 0;
		if (innerRadius < 0) {
			innerRadius = 0;
		}
		@Pc(21) int outerErr = -radius;
		@Pc(23) int outerY = radius;
		@Pc(25) int innerY = innerRadius;
		@Pc(28) int innerErr = -innerRadius;
		@Pc(30) int innerStep = -1;
		@Pc(34) int[] centerRow = TextureOpShapeLine.canvas[cy];
		@Pc(39) int rightInnerEdge = cx + innerRadius;
		@Pc(41) int outerStep = -1;
		@Pc(46) int leftInnerEdge = cx - innerRadius;
		ArrayUtils.fillRange(centerRow, cx - radius, leftInnerEdge, borderColor);
		ArrayUtils.fillRange(centerRow, leftInnerEdge, rightInnerEdge, fillColor);
		ArrayUtils.fillRange(centerRow, rightInnerEdge, cx + radius, borderColor);
		while (x < outerY) {
			innerStep += 2;
			innerErr += innerStep;
			if (innerErr >= 0 && innerY >= 1) {
				scratchBuffer[innerY] = x;
				innerY--;
				innerErr -= innerY << 1;
			}
			x++;
			outerStep += 2;
			outerErr += outerStep;
			@Pc(142) int[] rowA;
			@Pc(149) int[] rowB;
			@Pc(162) int innerXAtY;
			@Pc(158) int outerRight;
			@Pc(154) int outerLeft;
			@Pc(171) int innerRight;
			@Pc(167) int innerLeft;
			if (outerErr >= 0) {
				outerY--;
				outerErr -= outerY << 1;
				if (innerRadius > outerY) {
					rowA = TextureOpShapeLine.canvas[outerY + cy];
					rowB = TextureOpShapeLine.canvas[cy - outerY];
					outerLeft = cx - x;
					outerRight = x + cx;
					innerXAtY = scratchBuffer[outerY];
					innerLeft = cx - innerXAtY;
					innerRight = innerXAtY + cx;
					ArrayUtils.fillRange(rowA, outerLeft, innerLeft, borderColor);
					ArrayUtils.fillRange(rowA, innerLeft, innerRight, fillColor);
					ArrayUtils.fillRange(rowA, innerRight, outerRight, borderColor);
					ArrayUtils.fillRange(rowB, outerLeft, innerLeft, borderColor);
					ArrayUtils.fillRange(rowB, innerLeft, innerRight, fillColor);
					ArrayUtils.fillRange(rowB, innerRight, outerRight, borderColor);
				} else {
					rowA = TextureOpShapeLine.canvas[cy + outerY];
					rowB = TextureOpShapeLine.canvas[cy - outerY];
					innerXAtY = x + cx;
					outerRight = cx - x;
					ArrayUtils.fillRange(rowA, outerRight, innerXAtY, borderColor);
					ArrayUtils.fillRange(rowB, outerRight, innerXAtY, borderColor);
				}
			}
			rowA = TextureOpShapeLine.canvas[cy + x];
			rowB = TextureOpShapeLine.canvas[cy - x];
			innerXAtY = outerY + cx;
			outerRight = cx - outerY;
			if (innerRadius <= x) {
				ArrayUtils.fillRange(rowA, outerRight, innerXAtY, borderColor);
				ArrayUtils.fillRange(rowB, outerRight, innerXAtY, borderColor);
			} else {
				outerLeft = x <= innerY ? innerY : scratchBuffer[x];
				innerRight = outerLeft + cx;
				innerLeft = cx - outerLeft;
				ArrayUtils.fillRange(rowA, outerRight, innerLeft, borderColor);
				ArrayUtils.fillRange(rowA, innerLeft, innerRight, fillColor);
				ArrayUtils.fillRange(rowA, innerRight, innerXAtY, borderColor);
				ArrayUtils.fillRange(rowB, outerRight, innerLeft, borderColor);
				ArrayUtils.fillRange(rowB, innerLeft, innerRight, fillColor);
				ArrayUtils.fillRange(rowB, innerRight, innerXAtY, borderColor);
			}
		}
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(IBIIIII)V")
	public static void drawCircleBorderedClipped(@OriginalArg(0) int fillColor, @OriginalArg(2) int cy, @OriginalArg(3) int radius, @OriginalArg(4) int borderColor, @OriginalArg(5) int borderWidth, @OriginalArg(6) int cx) {
		@Pc(18) int innerRadius = radius - borderWidth;
		ensureScratchBufferCapacity(radius);
		@Pc(23) int x = 0;
		if (innerRadius < 0) {
			innerRadius = 0;
		}
		@Pc(32) int outerY = radius;
		@Pc(35) int outerErr = -radius;
		@Pc(38) int innerErr = -innerRadius;
		@Pc(40) int innerY = innerRadius;
		@Pc(42) int outerStep = -1;
		@Pc(61) int yBottom;
		@Pc(69) int xRight;
		@Pc(78) int xLeft;
		@Pc(87) int xInner;
		if (cy >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= cy) {
			@Pc(52) int[] centerRow = TextureOpShapeLine.canvas[cy];
			yBottom = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - radius, TextureOpShapeRasterizer.clipLeft);
			xRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, radius + cx, TextureOpShapeRasterizer.clipLeft);
			xLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - innerRadius, TextureOpShapeRasterizer.clipLeft);
			xInner = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx + innerRadius, TextureOpShapeRasterizer.clipLeft);
			ArrayUtils.fillRange(centerRow, yBottom, xLeft, borderColor);
			ArrayUtils.fillRange(centerRow, xLeft, xInner, fillColor);
			ArrayUtils.fillRange(centerRow, xInner, xRight, borderColor);
		}
		@Pc(107) int innerStep = -1;
		while (x < outerY) {
			outerStep += 2;
			innerStep += 2;
			innerErr += innerStep;
			outerErr += outerStep;
			if (innerErr >= 0 && innerY >= 1) {
				innerY--;
				scratchBuffer[innerY] = x;
				innerErr -= innerY << 1;
			}
			x++;
			@Pc(264) int innerRight;
			@Pc(273) int innerLeft;
			@Pc(280) int[] scanline;
			@Pc(161) int yTop;
			if (outerErr >= 0) {
				outerY--;
				outerErr -= outerY << 1;
				yTop = cy - outerY;
				yBottom = cy + outerY;
				if (TextureOpShapeRasterizer.clipTop <= yBottom && TextureOpShapeRasterizer.clipBottom >= yTop) {
					if (outerY >= innerRadius) {
						xRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, x + cx, TextureOpShapeRasterizer.clipLeft);
						xLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - x, TextureOpShapeRasterizer.clipLeft);
						if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
							ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], xLeft, xRight, borderColor);
						}
						if (yTop >= TextureOpShapeRasterizer.clipTop) {
							ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], xLeft, xRight, borderColor);
						}
					} else {
						xRight = scratchBuffer[outerY];
						xLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, x + cx, TextureOpShapeRasterizer.clipLeft);
						xInner = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - x, TextureOpShapeRasterizer.clipLeft);
						innerRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx + xRight, TextureOpShapeRasterizer.clipLeft);
						innerLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - xRight, TextureOpShapeRasterizer.clipLeft);
						if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
							scanline = TextureOpShapeLine.canvas[yBottom];
							ArrayUtils.fillRange(scanline, xInner, innerLeft, borderColor);
							ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
							ArrayUtils.fillRange(scanline, innerRight, xLeft, borderColor);
						}
						if (yTop >= TextureOpShapeRasterizer.clipTop) {
							scanline = TextureOpShapeLine.canvas[yTop];
							ArrayUtils.fillRange(scanline, xInner, innerLeft, borderColor);
							ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
							ArrayUtils.fillRange(scanline, innerRight, xLeft, borderColor);
						}
					}
				}
			}
			yTop = cy - x;
			yBottom = cy + x;
			if (TextureOpShapeRasterizer.clipTop <= yBottom && TextureOpShapeRasterizer.clipBottom >= yTop) {
				xRight = cx + outerY;
				xLeft = cx - outerY;
				if (xRight >= TextureOpShapeRasterizer.clipLeft && TextureOpShapeRasterizer.clipRight >= xLeft) {
					xRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, xRight, TextureOpShapeRasterizer.clipLeft);
					xLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, xLeft, TextureOpShapeRasterizer.clipLeft);
					if (x < innerRadius) {
						xInner = innerY >= x ? innerY : scratchBuffer[x];
						innerRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, xInner + cx, TextureOpShapeRasterizer.clipLeft);
						innerLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - xInner, TextureOpShapeRasterizer.clipLeft);
						if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
							scanline = TextureOpShapeLine.canvas[yBottom];
							ArrayUtils.fillRange(scanline, xLeft, innerLeft, borderColor);
							ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
							ArrayUtils.fillRange(scanline, innerRight, xRight, borderColor);
						}
						if (yTop >= TextureOpShapeRasterizer.clipTop) {
							scanline = TextureOpShapeLine.canvas[yTop];
							ArrayUtils.fillRange(scanline, xLeft, innerLeft, borderColor);
							ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
							ArrayUtils.fillRange(scanline, innerRight, xRight, borderColor);
						}
					} else {
						if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
							ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], xLeft, xRight, borderColor);
						}
						if (yTop >= TextureOpShapeRasterizer.clipTop) {
							ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], xLeft, xRight, borderColor);
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(II)V")
	public static void ensureScratchBufferCapacity(@OriginalArg(0) int capacity) {
		if (scratchBuffer == null || scratchBuffer.length < capacity) {
			scratchBuffer = new int[capacity];
		}
	}

	@OriginalMember(owner = "client!nb", name = "a", descriptor = "(IIIIII)V")
	public static void fillEllipseSingleColor(@OriginalArg(0) int color, @OriginalArg(1) int ry, @OriginalArg(2) int cx, @OriginalArg(3) int cy, @OriginalArg(5) int rx) {
		if (ry == rx) {
			fillCircleSingleColor(rx, cx, color, cy);
		} else if (TextureOpShapeRasterizer.clipLeft <= cx - rx && cx + rx <= TextureOpShapeRasterizer.clipRight && cy - ry >= TextureOpShapeRasterizer.clipTop && cy + ry <= TextureOpShapeRasterizer.clipBottom) {
			fillEllipseSingleColorFast(cy, cx, rx, ry, color);
		} else {
			fillEllipseSingleColorClipped(color, ry, rx, cx, cy);
		}
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(IIIIB)V")
	public static void fillCircleSingleColor(@OriginalArg(0) int radius, @OriginalArg(1) int cx, @OriginalArg(2) int color, @OriginalArg(3) int cy) {
		if (cx - radius >= TextureOpShapeRasterizer.clipLeft && TextureOpShapeRasterizer.clipRight >= radius + cx && cy - radius >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= radius + cy) {
			TextureOpShapeLine.fillCircle(cx, radius, color, cy);
		} else {
			fillCircleSingleColorClipped(color, cy, radius, cx);
		}
	}

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "(IIIII)V")
	public static void fillCircleSingleColorClipped(@OriginalArg(0) int color, @OriginalArg(1) int cy, @OriginalArg(2) int radius, @OriginalArg(4) int cx) {
		@Pc(7) int x = 0;
		@Pc(9) int y = radius;
		@Pc(12) int err = -radius;
		@Pc(14) int step = -1;
		@Pc(22) int clampedRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, radius + cx, TextureOpShapeRasterizer.clipLeft);
		@Pc(30) int clampedLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - radius, TextureOpShapeRasterizer.clipLeft);
		ArrayUtils.fillRange(TextureOpShapeLine.canvas[cy], clampedLeft, clampedRight, color);
		while (x < y) {
			step += 2;
			err += step;
			@Pc(58) int yTop;
			@Pc(68) int yBottom;
			@Pc(84) int right;
			@Pc(93) int left;
			if (err > 0) {
				y--;
				yTop = cy - y;
				err -= y << 1;
				yBottom = cy + y;
				if (yBottom >= TextureOpShapeRasterizer.clipTop && yTop <= TextureOpShapeRasterizer.clipBottom) {
					right = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx + x, TextureOpShapeRasterizer.clipLeft);
					left = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - x, TextureOpShapeRasterizer.clipLeft);
					if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
						ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], left, right, color);
					}
					if (TextureOpShapeRasterizer.clipTop <= yTop) {
						ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], left, right, color);
					}
				}
			}
			x++;
			yTop = cy - x;
			yBottom = x + cy;
			if (yBottom >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= yTop) {
				right = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx + y, TextureOpShapeRasterizer.clipLeft);
				left = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - y, TextureOpShapeRasterizer.clipLeft);
				if (yBottom <= TextureOpShapeRasterizer.clipBottom) {
					ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], left, right, color);
				}
				if (yTop >= TextureOpShapeRasterizer.clipTop) {
					ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], left, right, color);
				}
			}
		}
	}

	@OriginalMember(owner = "client!fl", name = "a", descriptor = "(IIIIII)V")
	public static void fillEllipseSingleColorFast(@OriginalArg(0) int cy, @OriginalArg(1) int cx, @OriginalArg(2) int rx, @OriginalArg(4) int ry, @OriginalArg(5) int color) {
		ArrayUtils.fillRange(TextureOpShapeLine.canvas[cy], cx - rx, rx + cx, color);
		@Pc(17) int x = 0;
		@Pc(21) int rxSq = rx * rx;
		@Pc(25) int rySq = ry * ry;
		@Pc(27) int y = ry;
		@Pc(31) int rySq2 = rySq << 1;
		@Pc(35) int rxSq2 = rxSq << 1;
		@Pc(39) int ry2 = ry << 1;
		@Pc(48) int err1 = rySq2 + (1 - ry2) * rxSq;
		@Pc(56) int err2 = rySq - rxSq2 * (ry2 - 1);
		@Pc(60) int rxSq4 = rxSq << 2;
		@Pc(70) int rySq4 = rySq << 2;
		@Pc(78) int inc1 = rySq2 * 3;
		@Pc(86) int dec1 = rxSq2 * ((ry << 1) - 3);
		@Pc(92) int inc2 = rySq4;
		@Pc(98) int dec2 = rxSq4 * (ry - 1);
		while (y > 0) {
			y--;
			@Pc(105) int yBottom = y + cy;
			@Pc(109) int yTop = cy - y;
			if (err1 < 0) {
				while (err1 < 0) {
					x++;
					err1 += inc1;
					err2 += inc2;
					inc2 += rySq4;
					inc1 += rySq4;
				}
			}
			if (err2 < 0) {
				err1 += inc1;
				inc1 += rySq4;
				err2 += inc2;
				x++;
				inc2 += rySq4;
			}
			@Pc(160) int right = x + cx;
			err2 += -dec1;
			dec1 -= rxSq4;
			err1 += -dec2;
			@Pc(179) int left = cx - x;
			dec2 -= rxSq4;
			ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], left, right, color);
			ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], left, right, color);
		}
	}

	@OriginalMember(owner = "client!cl", name = "a", descriptor = "(IIIBII)V")
	public static void fillEllipseSingleColorClipped(@OriginalArg(0) int color, @OriginalArg(1) int ry, @OriginalArg(2) int rx, @OriginalArg(4) int cx, @OriginalArg(5) int cy) {
		@Pc(13) int rxSq = rx * rx;
		@Pc(15) int x = 0;
		@Pc(17) int y = ry;
		@Pc(21) int rxSq2 = rxSq << 1;
		@Pc(25) int rySq = ry * ry;
		@Pc(29) int ry2 = ry << 1;
		@Pc(33) int rySq2 = rySq << 1;
		@Pc(42) int err1 = rxSq * (1 - ry2) + rySq2;
		@Pc(51) int err2 = rySq - rxSq2 * (ry2 - 1);
		@Pc(55) int rySq4 = rySq << 2;
		@Pc(63) int inc1 = rySq2 * 3;
		@Pc(67) int rxSq4 = rxSq << 2;
		@Pc(75) int dec1 = ((ry << 1) - 3) * rxSq2;
		@Pc(81) int inc2 = rySq4;
		@Pc(95) int yTop;
		@Pc(104) int yBottom;
		if (cy >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= cy) {
			yTop = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, rx + cx, TextureOpShapeRasterizer.clipLeft);
			yBottom = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - rx, TextureOpShapeRasterizer.clipLeft);
			ArrayUtils.fillRange(TextureOpShapeLine.canvas[cy], yBottom, yTop, color);
		}
		@Pc(118) int dec2 = rxSq4 * (ry - 1);
		while (y > 0) {
			y--;
			if (err1 < 0) {
				while (err1 < 0) {
					x++;
					err1 += inc1;
					err2 += inc2;
					inc2 += rySq4;
					inc1 += rySq4;
				}
			}
			yTop = cy - y;
			if (err2 < 0) {
				err2 += inc2;
				err1 += inc1;
				inc1 += rySq4;
				inc2 += rySq4;
				x++;
			}
			err2 += -dec1;
			dec1 -= rxSq4;
			err1 += -dec2;
			yBottom = y + cy;
			if (TextureOpShapeRasterizer.clipTop <= yBottom && TextureOpShapeRasterizer.clipBottom >= yTop) {
				@Pc(213) int right = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, x + cx, TextureOpShapeRasterizer.clipLeft);
				@Pc(222) int left = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, cx - x, TextureOpShapeRasterizer.clipLeft);
				if (yTop >= TextureOpShapeRasterizer.clipTop) {
					ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], left, right, color);
				}
				if (TextureOpShapeRasterizer.clipBottom >= yBottom) {
					ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], left, right, color);
				}
			}
			dec2 -= rxSq4;
		}
	}

	@OriginalMember(owner = "client!kc", name = "c", descriptor = "(III)V")
	@Override
	public final void renderFilledShape(@OriginalArg(1) int w, @OriginalArg(2) int h) {
		@Pc(10) int cx = this.centerX * w >> 12;
		@Pc(17) int rx = w * this.radiusX >> 12;
		@Pc(24) int cy = this.centerY * h >> 12;
		@Pc(31) int ry = this.radiusY * h >> 12;
		fillEllipseSingleColor(this.fillColor, ry, cx, cy, rx);
	}

	@OriginalMember(owner = "client!kc", name = "a", descriptor = "(IZI)V")
	@Override
	public final void renderOutlinedShape(@OriginalArg(0) int h, @OriginalArg(2) int w) {
	}

	@OriginalMember(owner = "client!kc", name = "a", descriptor = "(III)V")
	@Override
	public final void renderBorderedShape(@OriginalArg(0) int h, @OriginalArg(1) int w) {
		@Pc(6) int cx = w * this.centerX >> 12;
		@Pc(23) int ry = this.radiusY * h >> 12;
		@Pc(30) int cy = this.centerY * h >> 12;
		@Pc(37) int rx = this.radiusX * w >> 12;
		drawEllipseBordered(this.lineWidth, cx, cy, ry, this.outlineColor, this.fillColor, rx);
	}
}
