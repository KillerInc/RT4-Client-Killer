package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!si")
public final class TextureOpShapeRasterizer extends TextureOp {

	@OriginalMember(owner = "client!sd", name = "S", descriptor = "I")
	public static int clipRight = 100;
	@OriginalMember(owner = "client!ic", name = "j", descriptor = "I")
	public static int clipBottom = 100;
	@OriginalMember(owner = "client!nj", name = "i", descriptor = "I")
	public static int clipLeft = 0;
	@OriginalMember(owner = "client!vl", name = "h", descriptor = "I")
	public static int clipTop = 0;
	@OriginalMember(owner = "client!si", name = "U", descriptor = "[Lclient!kf;")
	private TextureOpShape[] shapes;

	@OriginalMember(owner = "client!si", name = "<init>", descriptor = "()V")
	public TextureOpShapeRasterizer() {
		super(0, true);
	}

	@OriginalMember(owner = "client!id", name = "a", descriptor = "(IIBII)V")
	public static void setClipBounds(@OriginalArg(3) int bottom, @OriginalArg(4) int right) {
		clipRight = right;
		clipLeft = 0;
		clipTop = 0;
		clipBottom = bottom;
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(ZIIII)V")
	public static void drawVerticalLineClamped(@OriginalArg(1) int y1, @OriginalArg(2) int y2, @OriginalArg(3) int color, @OriginalArg(4) int x) {
		if (x >= clipLeft && x <= clipRight) {
			@Pc(22) int clampedY2 = IntUtils.clamp(clipBottom, y2, clipTop);
			@Pc(28) int clampedY1 = IntUtils.clamp(clipBottom, y1, clipTop);
			TextureOpShapeLine.fillVerticalLine(clampedY2, x, clampedY1, color);
		}
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IIZII)V")
	public static void drawHorizontalLineClamped(@OriginalArg(0) int x1, @OriginalArg(1) int color, @OriginalArg(3) int x2, @OriginalArg(4) int y) {
		if (y >= clipTop && y <= clipBottom) {
			@Pc(15) int clampedX1 = IntUtils.clamp(clipRight, x1, clipLeft);
			@Pc(21) int clampedX2 = IntUtils.clamp(clipRight, x2, clipLeft);
			TextureOpShapeLine.fillRange(color, y, clampedX2, clampedX1);
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(IIIIII)V")
	public static void drawLine(@OriginalArg(0) int color, @OriginalArg(2) int y2, @OriginalArg(3) int x1, @OriginalArg(4) int y1, @OriginalArg(5) int x2) {
		@Pc(15) int dx = x2 - x1;
		@Pc(19) int dy = y2 - y1;
		if (dx == 0) {
			if (dy != 0) {
				drawVerticalLineClamped(y2, y1, color, x1);
			}
		} else if (dy == 0) {
			drawHorizontalLineClamped(x1, color, x2, y1);
		} else {
			@Pc(50) int slope = (dy << 12) / dx;
			@Pc(59) int yIntercept = y1 - (slope * x1 >> 12);
			@Pc(68) int clippedX1;
			@Pc(76) int clippedY1;
			if (clipLeft > x1) {
				clippedX1 = clipLeft;
				clippedY1 = (clipLeft * slope >> 12) + yIntercept;
			} else if (x1 > clipRight) {
				clippedY1 = (clipRight * slope >> 12) + yIntercept;
				clippedX1 = clipRight;
			} else {
				clippedX1 = x1;
				clippedY1 = y1;
			}
			@Pc(109) int clippedX2;
			@Pc(118) int clippedY2;
			if (x2 < clipLeft) {
				clippedX2 = clipLeft;
				clippedY2 = yIntercept + (slope * clipLeft >> 12);
			} else if (clipRight < x2) {
				clippedX2 = clipRight;
				clippedY2 = yIntercept + (slope * clipRight >> 12);
			} else {
				clippedY2 = y2;
				clippedX2 = x2;
			}
			if (clipTop > clippedY2) {
				clippedX2 = (clipTop - yIntercept << 12) / slope;
				clippedY2 = clipTop;
			} else if (clippedY2 > clipBottom) {
				clippedY2 = clipBottom;
				clippedX2 = (clipBottom - yIntercept << 12) / slope;
			}
			if (clippedY1 < clipTop) {
				clippedY1 = clipTop;
				clippedX1 = (clipTop - yIntercept << 12) / slope;
			} else if (clipBottom < clippedY1) {
				clippedY1 = clipBottom;
				clippedX1 = (clipBottom - yIntercept << 12) / slope;
			}
			TextureOpShapeLine.plotLine(color, clippedY2, clippedY1, clippedX2, clippedX1);
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(IIIIBIII)V")
	public static void drawEllipseBorderedClipped(@OriginalArg(0) int outerRadiusX, @OriginalArg(1) int fillColor, @OriginalArg(2) int borderColor, @OriginalArg(3) int borderWidth, @OriginalArg(5) int cy, @OriginalArg(6) int cx, @OriginalArg(7) int radiusY) {
		@Pc(7) int outerX = 0;
		@Pc(12) int innerRadiusX = outerRadiusX - borderWidth;
		@Pc(14) int innerX = 0;
		@Pc(16) int y = radiusY;
		@Pc(20) int rySq = radiusY * radiusY;
		@Pc(25) int innerRadiusY = radiusY - borderWidth;
		@Pc(29) int outerRxSq = outerRadiusX * outerRadiusX;
		@Pc(33) int rySq2 = rySq << 1;
		@Pc(37) int innerRySq = innerRadiusY * innerRadiusY;
		@Pc(41) int innerRxSq = innerRadiusX * innerRadiusX;
		@Pc(45) int outerRxSq2 = outerRxSq << 1;
		@Pc(49) int innerRySq2 = innerRySq << 1;
		@Pc(53) int innerRxSq2 = innerRxSq << 1;
		@Pc(57) int innerRy2 = innerRadiusY << 1;
		@Pc(61) int ry2 = radiusY << 1;
		@Pc(70) int outerErr1 = rySq2 + outerRxSq * (1 - ry2);
		@Pc(79) int outerErr2 = rySq - (ry2 - 1) * outerRxSq2;
		@Pc(88) int innerErr1 = innerRySq2 + innerRxSq * (1 - innerRy2);
		@Pc(96) int innerErr2 = innerRySq - innerRxSq2 * (innerRy2 - 1);
		@Pc(100) int rySq4 = rySq << 2;
		@Pc(104) int outerRxSq4 = outerRxSq << 2;
		@Pc(108) int innerRySq4 = innerRySq << 2;
		@Pc(112) int outerInc1 = rySq2 * 3;
		@Pc(118) int outerDec1 = (ry2 - 3) * outerRxSq2;
		@Pc(130) int innerRxSq4 = innerRxSq << 2;
		@Pc(134) int innerInc1 = innerRySq2 * 3;
		@Pc(140) int innerDec1 = (innerRy2 - 3) * innerRxSq2;
		@Pc(146) int outerDec2 = (radiusY - 1) * outerRxSq4;
		@Pc(148) int innerInc2 = innerRySq4;
		@Pc(150) int outerInc2 = rySq4;
		@Pc(156) int innerDec2 = (innerRadiusY - 1) * innerRxSq4;
		@Pc(174) int yTop;
		@Pc(183) int yBottom;
		@Pc(192) int xRight;
		@Pc(201) int xLeft;
		if (cy >= clipTop && clipBottom >= cy) {
			@Pc(166) int[] row = TextureOpShapeLine.canvas[cy];
			yTop = IntUtils.clamp(clipRight, cx - outerRadiusX, clipLeft);
			yBottom = IntUtils.clamp(clipRight, cx + outerRadiusX, clipLeft);
			xRight = IntUtils.clamp(clipRight, cx - innerRadiusX, clipLeft);
			xLeft = IntUtils.clamp(clipRight, cx + innerRadiusX, clipLeft);
			ArrayUtils.fillRange(row, yTop, xRight, borderColor);
			ArrayUtils.fillRange(row, xRight, xLeft, fillColor);
			ArrayUtils.fillRange(row, xLeft, yBottom, borderColor);
		}
		while (y > 0) {
			if (outerErr1 < 0) {
				while (outerErr1 < 0) {
					outerErr1 += outerInc1;
					outerInc1 += rySq4;
					outerX++;
					outerErr2 += outerInc2;
					outerInc2 += rySq4;
				}
			}
			@Pc(255) boolean hasInner = y <= innerRadiusY;
			if (outerErr2 < 0) {
				outerErr2 += outerInc2;
				outerErr1 += outerInc1;
				outerX++;
				outerInc2 += rySq4;
				outerInc1 += rySq4;
			}
			if (hasInner) {
				if (innerErr1 < 0) {
					while (innerErr1 < 0) {
						innerX++;
						innerErr2 += innerInc2;
						innerInc2 += innerRySq4;
						innerErr1 += innerInc1;
						innerInc1 += innerRySq4;
					}
				}
				if (innerErr2 < 0) {
					innerX++;
					innerErr2 += innerInc2;
					innerErr1 += innerInc1;
					innerInc2 += innerRySq4;
					innerInc1 += innerRySq4;
				}
				innerErr1 += -innerDec2;
				innerDec2 -= innerRxSq4;
				innerErr2 += -innerDec1;
				innerDec1 -= innerRxSq4;
			}
			outerErr2 += -outerDec1;
			outerErr1 += -outerDec2;
			outerDec2 -= outerRxSq4;
			outerDec1 -= outerRxSq4;
			y--;
			yBottom = cy + y;
			yTop = cy - y;
			if (clipTop <= yBottom && clipBottom >= yTop) {
				xRight = IntUtils.clamp(clipRight, cx + outerX, clipLeft);
				xLeft = IntUtils.clamp(clipRight, cx - outerX, clipLeft);
				if (hasInner) {
					@Pc(404) int innerRight = IntUtils.clamp(clipRight, cx + innerX, clipLeft);
					@Pc(412) int innerLeft = IntUtils.clamp(clipRight, cx - innerX, clipLeft);
					@Pc(420) int[] scanline;
					if (clipTop <= yTop) {
						scanline = TextureOpShapeLine.canvas[yTop];
						ArrayUtils.fillRange(scanline, xLeft, innerLeft, borderColor);
						ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
						ArrayUtils.fillRange(scanline, innerRight, xRight, borderColor);
					}
					if (yBottom <= clipBottom) {
						scanline = TextureOpShapeLine.canvas[yBottom];
						ArrayUtils.fillRange(scanline, xLeft, innerLeft, borderColor);
						ArrayUtils.fillRange(scanline, innerLeft, innerRight, fillColor);
						ArrayUtils.fillRange(scanline, innerRight, xRight, borderColor);
					}
				} else {
					if (clipTop <= yTop) {
						ArrayUtils.fillRange(TextureOpShapeLine.canvas[yTop], xLeft, xRight, borderColor);
					}
					if (clipBottom >= yBottom) {
						ArrayUtils.fillRange(TextureOpShapeLine.canvas[yBottom], xLeft, xRight, borderColor);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!si", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(13) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			this.rasterizeShapes(this.monochromeImageCache.getAll());
		}
		return output;
	}

	@OriginalMember(owner = "client!si", name = "a", descriptor = "(I[[I)V")
	private void rasterizeShapes(@OriginalArg(1) int[][] canvas) {
		@Pc(7) int h = Texture.height;
		@Pc(9) int w = Texture.width;
		TextureOpShapeLine.setCanvas(canvas);
		setClipBounds(Texture.heightMask, Texture.widthMask);
		if (this.shapes == null) {
			return;
		}
		for (@Pc(23) int i = 0; i < this.shapes.length; i++) {
			@Pc(33) TextureOpShape shape = this.shapes[i];
			@Pc(36) int fill = shape.fillColor;
			@Pc(39) int outline = shape.outlineColor;
			if (fill >= 0) {
				if (outline < 0) {
					shape.renderFilledShape(w, h);
				} else {
					shape.renderBorderedShape(h, w);
				}
			} else if (outline >= 0) {
				shape.renderOutlinedShape(h, w);
			}
		}
	}

	@OriginalMember(owner = "client!si", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.shapes = new TextureOpShape[buf.g1()];
			for (@Pc(11) int i = 0; i < this.shapes.length; i++) {
				@Pc(24) int type = buf.g1();
				if (type == 0) {
					this.shapes[i] = TextureOpShapeRect.create(buf);
				} else if (type == 1) {
					this.shapes[i] = TextureOpShapeBezier.create(buf);
				} else if (type == 2) {
					this.shapes[i] = TextureOpShapeLine.create(buf);
				} else if (type == 3) {
					this.shapes[i] = TextureOpShapeEllipse.create(buf);
				}
			}
		} else if (opcode == 1) {
			this.monochrome = buf.g1() == 1;
		}
	}

	@OriginalMember(owner = "client!si", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(14) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(20) int w = Texture.width;
			@Pc(22) int h = Texture.height;
			@Pc(26) int[][] packedCanvas = new int[h][w];
			@Pc(31) int[][][] colorCache = this.colorImageCache.get();
			this.rasterizeShapes(packedCanvas);
			for (@Pc(37) int y = 0; y < Texture.height; y++) {
				@Pc(44) int[] packed = packedCanvas[y];
				@Pc(48) int[][] channels = colorCache[y];
				@Pc(52) int[] destR = channels[0];
				@Pc(56) int[] destG = channels[1];
				@Pc(60) int[] destB = channels[2];
				for (@Pc(62) int x = 0; x < Texture.width; x++) {
					@Pc(73) int color = packed[x];
					destB[x] = (color & 0xFF) << 4;
					destG[x] = color >> 4 & 0xFF0;
					destR[x] = color >> 12 & 0xFF0;
				}
			}
		}
		return output;
	}
}
