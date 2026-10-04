package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ta")
public final class TextureOpShapeLine extends TextureOpShape {

	@OriginalMember(owner = "client!fk", name = "j", descriptor = "[[I")
	public static int[][] canvas;

	@OriginalMember(owner = "client!ta", name = "D", descriptor = "I")
	private final int top;

	@OriginalMember(owner = "client!ta", name = "s", descriptor = "I")
	private final int right;

	@OriginalMember(owner = "client!ta", name = "C", descriptor = "I")
	private final int left;

	@OriginalMember(owner = "client!ta", name = "A", descriptor = "I")
	private final int bottom;

	@OriginalMember(owner = "client!ta", name = "<init>", descriptor = "(IIIIIII)V")
	public TextureOpShapeLine(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom, @OriginalArg(4) int fillColor, @OriginalArg(5) int outlineColor, @OriginalArg(6) int lineWidth) {
		super(fillColor, outlineColor, lineWidth);
		this.top = top;
		this.right = right;
		this.left = left;
		this.bottom = bottom;
	}

	@OriginalMember(owner = "client!bl", name = "a", descriptor = "(IIIIIIII)V")
	public static void drawFramedRect(@OriginalArg(0) int lineWidth, @OriginalArg(1) int bottom, @OriginalArg(2) int fillColor, @OriginalArg(3) int outlineColor, @OriginalArg(4) int top, @OriginalArg(6) int right, @OriginalArg(7) int left) {
		if (TextureOpShapeRasterizer.clipLeft <= left && TextureOpShapeRasterizer.clipRight >= right && top >= TextureOpShapeRasterizer.clipTop && bottom <= TextureOpShapeRasterizer.clipBottom) {
			drawFramedRectUnclamped(outlineColor, top, bottom, fillColor, lineWidth, right, left);
		} else {
			drawFramedRectClipped(right, fillColor, bottom, lineWidth, outlineColor, top, left);
		}
	}

	@OriginalMember(owner = "client!mc", name = "a", descriptor = "(IIIIIIII)V")
	public static void drawFramedRectUnclamped(@OriginalArg(0) int outlineColor, @OriginalArg(2) int top, @OriginalArg(3) int bottom, @OriginalArg(4) int fillColor, @OriginalArg(5) int lineWidth, @OriginalArg(6) int right, @OriginalArg(7) int left) {
		@Pc(9) int topBorderEnd = lineWidth + top;
		@Pc(13) int innerLeft = lineWidth + left;
		@Pc(15) int y;
		for (y = top; y < topBorderEnd; y++) {
			ArrayUtils.fillRange(canvas[y], left, right, outlineColor);
		}
		@Pc(34) int bottomBorderStart = bottom - lineWidth;
		@Pc(39) int innerRight = right - lineWidth;
		for (y = bottom; y > bottomBorderStart; y--) {
			ArrayUtils.fillRange(canvas[y], left, right, outlineColor);
		}
		for (y = topBorderEnd; y <= bottomBorderStart; y++) {
			@Pc(72) int[] row = canvas[y];
			ArrayUtils.fillRange(row, left, innerLeft, outlineColor);
			ArrayUtils.fillRange(row, innerLeft, innerRight, fillColor);
			ArrayUtils.fillRange(row, innerRight, right, outlineColor);
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(IIIIIIII)V")
	public static void drawFramedRectClipped(@OriginalArg(0) int right, @OriginalArg(1) int fillColor, @OriginalArg(3) int bottom, @OriginalArg(4) int lineWidth, @OriginalArg(5) int outlineColor, @OriginalArg(6) int top, @OriginalArg(7) int left) {
		@Pc(11) int clampedTop = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, top, TextureOpShapeRasterizer.clipTop);
		@Pc(17) int clampedBottom = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, bottom, TextureOpShapeRasterizer.clipTop);
		@Pc(23) int clampedLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, left, TextureOpShapeRasterizer.clipLeft);
		@Pc(29) int clampedRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, right, TextureOpShapeRasterizer.clipLeft);
		@Pc(37) int topBorderEnd = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, lineWidth + top, TextureOpShapeRasterizer.clipTop);
		@Pc(46) int bottomBorderStart = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, bottom - lineWidth, TextureOpShapeRasterizer.clipTop);
		@Pc(48) int y;
		for (y = clampedTop; y < topBorderEnd; y++) {
			ArrayUtils.fillRange(canvas[y], clampedLeft, clampedRight, outlineColor);
		}
		for (y = clampedBottom; y > bottomBorderStart; y--) {
			ArrayUtils.fillRange(canvas[y], clampedLeft, clampedRight, outlineColor);
		}
		@Pc(94) int innerLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, lineWidth + left, TextureOpShapeRasterizer.clipLeft);
		@Pc(103) int innerRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, right - lineWidth, TextureOpShapeRasterizer.clipLeft);
		for (y = topBorderEnd; y <= bottomBorderStart; y++) {
			@Pc(122) int[] row = canvas[y];
			ArrayUtils.fillRange(row, clampedLeft, innerLeft, outlineColor);
			ArrayUtils.fillRange(row, innerLeft, innerRight, fillColor);
			ArrayUtils.fillRange(row, innerRight, clampedRight, outlineColor);
		}
	}

	@OriginalMember(owner = "client!sj", name = "a", descriptor = "(IIBIII)V")
	public static void fillSolidRect(@OriginalArg(0) int left, @OriginalArg(1) int color, @OriginalArg(3) int bottom, @OriginalArg(4) int right, @OriginalArg(5) int top) {
		if (left >= TextureOpShapeRasterizer.clipLeft && right <= TextureOpShapeRasterizer.clipRight && TextureOpShapeRasterizer.clipTop <= top && TextureOpShapeRasterizer.clipBottom >= bottom) {
			fillSolidRectUnclamped(bottom, right, top, left, color);
		} else {
			fillSolidRectClipped(color, right, top, left, bottom);
		}
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(IIIIII)V")
	public static void fillSolidRectUnclamped(@OriginalArg(1) int bottom, @OriginalArg(2) int right, @OriginalArg(3) int top, @OriginalArg(4) int left, @OriginalArg(5) int color) {
		for (@Pc(8) int y = top; y <= bottom; y++) {
			ArrayUtils.fillRange(canvas[y], left, right, color);
		}
	}

	@OriginalMember(owner = "client!n", name = "a", descriptor = "(IIIIII)V")
	public static void fillSolidRectClipped(@OriginalArg(1) int color, @OriginalArg(2) int right, @OriginalArg(3) int top, @OriginalArg(4) int left, @OriginalArg(5) int bottom) {
		@Pc(11) int clampedTop = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, top, TextureOpShapeRasterizer.clipTop);
		@Pc(17) int clampedBottom = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, bottom, TextureOpShapeRasterizer.clipTop);
		@Pc(23) int clampedLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, left, TextureOpShapeRasterizer.clipLeft);
		@Pc(35) int clampedRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, right, TextureOpShapeRasterizer.clipLeft);
		for (@Pc(37) int y = clampedTop; y <= clampedBottom; y++) {
			ArrayUtils.fillRange(canvas[y], clampedLeft, clampedRight, color);
		}
	}

	@OriginalMember(owner = "client!mf", name = "a", descriptor = "(BLclient!wa;)Lclient!ta;")
	public static TextureOpShapeLine create(@OriginalArg(1) Buffer buf) {
		return new TextureOpShapeLine(buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g3(), buf.g3(), buf.g1());
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(IBIII)V")
	public static void fillCircle(@OriginalArg(0) int cx, @OriginalArg(2) int radius, @OriginalArg(3) int color, @OriginalArg(4) int cy) {
		@Pc(15) int x = 0;
		ArrayUtils.fillRange(canvas[cy], cx - radius, cx - -radius, color);
		@Pc(32) int err = -radius;
		@Pc(34) int y = radius;
		@Pc(36) int delta = -1;
		while (x < y) {
			x++;
			delta += 2;
			err += delta;
			if (err >= 0) {
				y--;
				err -= y << 1;
				@Pc(65) int[] rowTop = canvas[cy + y];
				@Pc(71) int[] rowBottom = canvas[cy - y];
				@Pc(76) int xRight = cx + x;
				@Pc(81) int xLeft = cx - x;
				ArrayUtils.fillRange(rowTop, xLeft, xRight, color);
				ArrayUtils.fillRange(rowBottom, xLeft, xRight, color);
			}
			@Pc(97) int yRight = y + cx;
			@Pc(102) int yLeft = cx - y;
			@Pc(109) int[] rowPlus = canvas[cy + x];
			@Pc(116) int[] rowMinus = canvas[cy - x];
			ArrayUtils.fillRange(rowPlus, yLeft, yRight, color);
			ArrayUtils.fillRange(rowMinus, yLeft, yRight, color);
		}
	}

	@OriginalMember(owner = "client!sa", name = "a", descriptor = "(IIIBI)V")
	public static void fillRange(@OriginalArg(0) int color, @OriginalArg(1) int row, @OriginalArg(2) int x2, @OriginalArg(4) int x1) {
		if (x1 <= x2) {
			ArrayUtils.fillRange(canvas[row], x1, x2, color);
		} else {
			ArrayUtils.fillRange(canvas[row], x2, x1, color);
		}
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(IIIII)V")
	public static void fillVerticalLine(@OriginalArg(1) int y1, @OriginalArg(2) int x, @OriginalArg(3) int y2, @OriginalArg(4) int color) {
		@Pc(8) int y;
		if (y1 <= y2) {
			for (y = y1; y < y2; y++) {
				canvas[y][x] = color;
			}
		} else {
			for (y = y2; y < y1; y++) {
				canvas[y][x] = color;
			}
		}
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(BIIIII)V")
	public static void plotLine(@OriginalArg(1) int color, @OriginalArg(2) int y2, @OriginalArg(3) int y1, @OriginalArg(4) int x2, @OriginalArg(5) int x1) {
		@Pc(9) int dy = y2 - y1;
		@Pc(14) int dx = x2 - x1;
		if (dx == 0) {
			if (dy != 0) {
				fillVerticalLine(y1, x1, y2, color);
			}
		} else if (dy == 0) {
			fillRange(color, y1, x2, x1);
		} else {
			if (dy < 0) {
				dy = -dy;
			}
			if (dx < 0) {
				dx = -dx;
			}
			@Pc(70) boolean steep = dx < dy;
			@Pc(74) int tmp;
			@Pc(78) int tmp2;
			if (steep) {
				tmp = x1;
				x1 = y1;
				tmp2 = x2;
				y1 = tmp;
				x2 = y2;
				y2 = tmp2;
			}
			if (x2 < x1) {
				tmp = x1;
				x1 = x2;
				x2 = tmp;
				tmp2 = y1;
				y1 = y2;
				y2 = tmp2;
			}
			tmp = y1;
			tmp2 = x2 - x1;
			@Pc(111) int deltaY = y2 - y1;
			@Pc(116) int error = -(tmp2 >> 1);
			@Pc(123) int yStep = y2 <= y1 ? -1 : 1;
			if (deltaY < 0) {
				deltaY = -deltaY;
			}
			@Pc(136) int i;
			if (steep) {
				for (i = x1; i <= x2; i++) {
					canvas[i][tmp] = color;
					error += deltaY;
					if (error > 0) {
						tmp += yStep;
						error -= tmp2;
					}
				}
			} else {
				for (i = x1; i <= x2; i++) {
					error += deltaY;
					canvas[tmp][i] = color;
					if (error > 0) {
						tmp += yStep;
						error -= tmp2;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ub", name = "a", descriptor = "(IIIIIII)V")
	public static void drawRectOutline(@OriginalArg(0) int color, @OriginalArg(1) int left, @OriginalArg(2) int top, @OriginalArg(3) int right, @OriginalArg(4) int bottom, @OriginalArg(5) int lineWidth) {
		if (TextureOpShapeRasterizer.clipLeft <= left && TextureOpShapeRasterizer.clipRight >= right && TextureOpShapeRasterizer.clipTop <= top && bottom <= TextureOpShapeRasterizer.clipBottom) {
			if (lineWidth == 1) {
				drawRectOutline1pxFast(color, right, top, bottom, left);
			} else {
				drawThickRectOutlineFast(right, top, color, bottom, lineWidth, left);
			}
		} else if (lineWidth == 1) {
			drawRectOutline1pxClipped(color, left, bottom, right, top);
		} else {
			drawThickRectOutlineClipped(bottom, lineWidth, right, left, color, top);
		}
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(IIIBII)V")
	public static void drawRectOutline1pxFast(@OriginalArg(0) int color, @OriginalArg(1) int right, @OriginalArg(2) int top, @OriginalArg(4) int bottom, @OriginalArg(5) int left) {
		@Pc(8) int topNext = top + 1;
		ArrayUtils.fillRange(canvas[top], left, right, color);
		@Pc(17) int bottomPrev = bottom - 1;
		ArrayUtils.fillRange(canvas[bottom], left, right, color);
		for (@Pc(29) int y = topNext; y <= bottomPrev; y++) {
			@Pc(40) int[] row = canvas[y];
			row[left] = row[right] = color;
		}
	}

	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(IIIIIII)V")
	public static void drawThickRectOutlineFast(@OriginalArg(1) int right, @OriginalArg(2) int top, @OriginalArg(3) int color, @OriginalArg(4) int bottom, @OriginalArg(5) int lineWidth, @OriginalArg(6) int left) {
		@Pc(5) int topBorderEnd = lineWidth + top;
		@Pc(14) int bottomBorderStart = bottom - lineWidth;
		@Pc(29) int innerLeft = lineWidth + left;
		@Pc(31) int y;
		for (y = top; y < topBorderEnd; y++) {
			ArrayUtils.fillRange(canvas[y], left, right, color);
		}
		for (y = bottom; y > bottomBorderStart; y--) {
			ArrayUtils.fillRange(canvas[y], left, right, color);
		}
		@Pc(70) int innerRight = right - lineWidth;
		for (y = topBorderEnd; y <= bottomBorderStart; y++) {
			@Pc(83) int[] row = canvas[y];
			ArrayUtils.fillRange(row, left, innerLeft, color);
			ArrayUtils.fillRange(row, innerRight, right, color);
		}
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(IIIIIB)V")
	public static void drawRectOutline1pxClipped(@OriginalArg(0) int color, @OriginalArg(1) int left, @OriginalArg(2) int bottom, @OriginalArg(3) int right, @OriginalArg(4) int top) {
		if (top > TextureOpShapeRasterizer.clipBottom || bottom < TextureOpShapeRasterizer.clipTop) {
			return;
		}
		@Pc(24) boolean leftVisible;
		if (TextureOpShapeRasterizer.clipLeft > left) {
			leftVisible = false;
			left = TextureOpShapeRasterizer.clipLeft;
		} else if (TextureOpShapeRasterizer.clipRight >= left) {
			leftVisible = true;
		} else {
			leftVisible = false;
			left = TextureOpShapeRasterizer.clipRight;
		}
		@Pc(43) boolean rightVisible;
		if (right < TextureOpShapeRasterizer.clipLeft) {
			right = TextureOpShapeRasterizer.clipLeft;
			rightVisible = false;
		} else if (right > TextureOpShapeRasterizer.clipRight) {
			right = TextureOpShapeRasterizer.clipRight;
			rightVisible = false;
		} else {
			rightVisible = true;
		}
		if (TextureOpShapeRasterizer.clipTop > top) {
			top = TextureOpShapeRasterizer.clipTop;
		} else {
			ArrayUtils.fillRange(canvas[top++], left, right, color);
		}
		if (bottom <= TextureOpShapeRasterizer.clipBottom) {
			ArrayUtils.fillRange(canvas[bottom--], left, right, color);
		} else {
			bottom = TextureOpShapeRasterizer.clipBottom;
		}
		@Pc(98) int y;
		if (leftVisible && rightVisible) {
			for (y = top; y <= bottom; y++) {
				@Pc(105) int[] row = canvas[y];
				row[left] = row[right] = color;
			}
		} else if (leftVisible) {
			for (y = top; y <= bottom; y++) {
				canvas[y][left] = color;
			}
		} else if (rightVisible) {
			for (y = top; y <= bottom; y++) {
				canvas[y][right] = color;
			}
		}
	}

	@OriginalMember(owner = "client!an", name = "a", descriptor = "(IIIIIII)V")
	public static void drawThickRectOutlineClipped(@OriginalArg(0) int bottom, @OriginalArg(1) int lineWidth, @OriginalArg(2) int right, @OriginalArg(3) int left, @OriginalArg(5) int color, @OriginalArg(6) int top) {
		@Pc(11) int clampedTop = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, top, TextureOpShapeRasterizer.clipTop);
		@Pc(17) int clampedBottom = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, bottom, TextureOpShapeRasterizer.clipTop);
		@Pc(23) int clampedLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, left, TextureOpShapeRasterizer.clipLeft);
		@Pc(29) int clampedRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, right, TextureOpShapeRasterizer.clipLeft);
		@Pc(42) int topBorderEnd = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, top + lineWidth, TextureOpShapeRasterizer.clipTop);
		@Pc(51) int bottomBorderStart = IntUtils.clamp(TextureOpShapeRasterizer.clipBottom, bottom - lineWidth, TextureOpShapeRasterizer.clipTop);
		@Pc(53) int y;
		for (y = clampedTop; y < topBorderEnd; y++) {
			ArrayUtils.fillRange(canvas[y], clampedLeft, clampedRight, color);
		}
		for (y = clampedBottom; y > bottomBorderStart; y--) {
			ArrayUtils.fillRange(canvas[y], clampedLeft, clampedRight, color);
		}
		@Pc(95) int innerLeft = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, lineWidth + left, TextureOpShapeRasterizer.clipLeft);
		@Pc(104) int innerRight = IntUtils.clamp(TextureOpShapeRasterizer.clipRight, right - lineWidth, TextureOpShapeRasterizer.clipLeft);
		for (y = topBorderEnd; y <= bottomBorderStart; y++) {
			@Pc(117) int[] row = canvas[y];
			ArrayUtils.fillRange(row, clampedLeft, innerLeft, color);
			ArrayUtils.fillRange(row, innerRight, clampedRight, color);
		}
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "([[IZ)V")
	public static void setCanvas(@OriginalArg(0) int[][] data) {
		canvas = data;
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IZI)V")
	@Override
	public final void renderOutlinedShape(@OriginalArg(0) int h, @OriginalArg(2) int w) {
		@Pc(10) int r = w * this.right >> 12;
		@Pc(17) int b = this.bottom * h >> 12;
		@Pc(24) int l = this.left * w >> 12;
		@Pc(31) int t = this.top * h >> 12;
		drawRectOutline(this.outlineColor, l, t, r, b, this.lineWidth);
	}

	@OriginalMember(owner = "client!ta", name = "c", descriptor = "(III)V")
	@Override
	public final void renderFilledShape(@OriginalArg(1) int w, @OriginalArg(2) int h) {
		@Pc(10) int l = this.left * w >> 12;
		@Pc(17) int r = w * this.right >> 12;
		@Pc(24) int t = h * this.top >> 12;
		@Pc(31) int b = h * this.bottom >> 12;
		fillSolidRect(l, this.fillColor, b, r, t);
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(III)V")
	@Override
	public final void renderBorderedShape(@OriginalArg(0) int h, @OriginalArg(1) int w) {
		@Pc(14) int l = w * this.left >> 12;
		@Pc(21) int t = this.top * h >> 12;
		@Pc(28) int r = w * this.right >> 12;
		@Pc(35) int b = this.bottom * h >> 12;
		drawFramedRect(this.lineWidth, b, this.fillColor, this.outlineColor, t, r, l);
	}
}
