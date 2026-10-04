package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class SoftwareRaster {

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "I")
	public static int width;

	@OriginalMember(owner = "client!kb", name = "c", descriptor = "I")
	public static int height;

	@OriginalMember(owner = "client!kb", name = "f", descriptor = "[I")
	public static int[] lineMaskStarts;

	@OriginalMember(owner = "client!kb", name = "g", descriptor = "[I")
	public static int[] lineMaskWidths;

	@OriginalMember(owner = "client!kb", name = "i", descriptor = "[I")
	public static int[] pixels;

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "I")
	public static int clipLeft = 0;

	@OriginalMember(owner = "client!kb", name = "d", descriptor = "I")
	public static int clipTop = 0;

	@OriginalMember(owner = "client!kb", name = "e", descriptor = "I")
	public static int clipRight = 0;

	@OriginalMember(owner = "client!kb", name = "h", descriptor = "I")
	public static int clipBottom = 0;

	@OriginalMember(owner = "client!vd", name = "w", descriptor = "Lclient!vk;")
	public static FrameBuffer frameBuffer;

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "()V")
	public static void clearLineMasks() {
		lineMaskStarts = null;
		lineMaskWidths = null;
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(IIIII)V")
	public static void drawRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int color) {
		drawHorizontalLine(x, y, w, color);
		drawHorizontalLine(x, y + h - 1, w, color);
		drawVerticalLine(x, y, h, color);
		drawVerticalLine(x + w - 1, y, h, color);
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(IIIIII)V")
	public static void fillRectAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int color, @OriginalArg(5) int alpha) {
		if (x < clipLeft) {
			w -= clipLeft - x;
			x = clipLeft;
		}
		if (y < clipTop) {
			h -= clipTop - y;
			y = clipTop;
		}
		if (x + w > clipRight) {
			w = clipRight - x;
		}
		if (y + h > clipBottom) {
			h = clipBottom - y;
		}
		@Pc(59) int srcColor = ((color & 0xFF00FF) * alpha >> 8 & 0xFF00FF) + ((color & 0xFF00) * alpha >> 8 & 0xFF00);
		@Pc(63) int invAlpha = 256 - alpha;
		@Pc(67) int stride = width - w;
		@Pc(73) int offset = x + y * width;
		for (@Pc(75) int row = 0; row < h; row++) {
			for (@Pc(81) int col = -w; col < 0; col++) {
				@Pc(87) int dstColor = pixels[offset];
				@Pc(107) int blendedDst = ((dstColor & 0xFF00FF) * invAlpha >> 8 & 0xFF00FF) + ((dstColor & 0xFF00) * invAlpha >> 8 & 0xFF00);
				pixels[offset++] = srcColor + blendedDst;
			}
			offset += stride;
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(III)V")
	private static void drawDot(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		if (x >= clipLeft && y >= clipTop && x < clipRight && y < clipBottom) {
			pixels[x + y * width] = 16776960;
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "([I[I)V")
	public static void setLineMasks(@OriginalArg(0) int[] starts, @OriginalArg(1) int[] widths) {
		if (starts.length != clipBottom - clipTop || widths.length != clipBottom - clipTop) {
			throw new IllegalArgumentException();
		}
		lineMaskStarts = starts;
		lineMaskWidths = widths;
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "(IIIIII)V")
	public static void drawRectAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int color, @OriginalArg(5) int alpha) {
		drawHorizontalLineAlpha(x, y, w, color, alpha);
		drawHorizontalLineAlpha(x, y + h - 1, w, color, alpha);
		if (h >= 3) {
			drawVerticalLineAlpha(x, y + 1, h - 2, color, alpha);
			drawVerticalLineAlpha(x + w - 1, y + 1, h - 2, color, alpha);
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "([I)V")
	public static void restoreClip(@OriginalArg(0) int[] clip) {
		clipLeft = clip[0];
		clipTop = clip[1];
		clipRight = clip[2];
		clipBottom = clip[3];
		clearLineMasks();
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(IIII)V")
	public static void drawHorizontalLine(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int length, @OriginalArg(3) int color) {
		if (y < clipTop || y >= clipBottom) {
			return;
		}
		if (x < clipLeft) {
			length -= clipLeft - x;
			x = clipLeft;
		}
		if (x + length > clipRight) {
			length = clipRight - x;
		}
		@Pc(32) int offset = x + y * width;
		for (@Pc(34) int i = 0; i < length; i++) {
			pixels[offset + i] = color;
		}
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "(IIII)V")
	public static void drawVerticalLine(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int length, @OriginalArg(3) int color) {
		if (x < clipLeft || x >= clipRight) {
			return;
		}
		if (y < clipTop) {
			length -= clipTop - y;
			y = clipTop;
		}
		if (y + length > clipBottom) {
			length = clipBottom - y;
		}
		@Pc(32) int offset = x + y * width;
		for (@Pc(34) int i = 0; i < length; i++) {
			pixels[offset + i * width] = color;
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "([III)V")
	public static void setSize(@OriginalArg(0) int[] pixelData, @OriginalArg(1) int w, @OriginalArg(2) int h) {
		pixels = pixelData;
		width = w;
		height = h;
		setClip(0, 0, w, h);
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "()V")
	public static void clear() {
		@Pc(1) int i = 0;
		@Pc(7) int end = width * height - 7;
		while (i < end) {
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
			pixels[i++] = 0;
		}
		end += 7;
		while (i < end) {
			pixels[i++] = 0;
		}
	}

	public static void clear(int color) {
		for (int i = 0; i < width * height; ++i) {
			pixels[i] = color;
		}
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "(IIIII)V")
	private static void drawHorizontalLineAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int length, @OriginalArg(3) int color, @OriginalArg(4) int alpha) {
		if (y < clipTop || y >= clipBottom) {
			return;
		}
		if (x < clipLeft) {
			length -= clipLeft - x;
			x = clipLeft;
		}
		if (x + length > clipRight) {
			length = clipRight - x;
		}
		@Pc(30) int invAlpha = 256 - alpha;
		@Pc(38) int srcR = (color >> 16 & 0xFF) * alpha;
		@Pc(46) int srcG = (color >> 8 & 0xFF) * alpha;
		@Pc(52) int srcB = (color & 0xFF) * alpha;
		@Pc(58) int offset = x + y * width;
		for (@Pc(60) int i = 0; i < length; i++) {
			@Pc(73) int dstR = (pixels[offset] >> 16 & 0xFF) * invAlpha;
			@Pc(83) int dstG = (pixels[offset] >> 8 & 0xFF) * invAlpha;
			@Pc(91) int dstB = (pixels[offset] & 0xFF) * invAlpha;
			@Pc(113) int blended = (srcR + dstR >> 8 << 16) + (srcG + dstG >> 8 << 8) + (srcB + dstB >> 8);
			pixels[offset++] = blended;
		}
	}

	@OriginalMember(owner = "client!kb", name = "c", descriptor = "(IIIIII)V")
	public static void drawThickLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int color, @OriginalArg(5) int thickness) {
		@Pc(3) int dx = x2 - x1;
		@Pc(7) int dy = y2 - y1;
		@Pc(14) int absDx = dx >= 0 ? dx : -dx;
		@Pc(21) int absDy = dy >= 0 ? dy : -dy;
		@Pc(23) int steps = absDx;
		if (absDx < absDy) {
			steps = absDy;
		}
		if (steps == 0) {
			return;
		}
		@Pc(37) int xStep = (dx << 16) / steps;
		@Pc(43) int yStep = (dy << 16) / steps;
		if (yStep <= xStep) {
			xStep = -xStep;
		} else {
			yStep = -yStep;
		}
		@Pc(59) int perpX1 = thickness * yStep >> 17;
		@Pc(67) int perpX2 = thickness * yStep + 1 >> 17;
		@Pc(73) int perpY1 = thickness * xStep >> 17;
		@Pc(81) int perpY2 = thickness * xStep + 1 >> 17;
		@Pc(85) int originX = x1 - Rasteriser.getOffsetRemainder();
		@Pc(89) int originY = y1 - Rasteriser.getOffset();
		@Pc(93) int ax = originX + perpX1;
		@Pc(97) int bx = originX - perpX2;
		@Pc(103) int cx = originX + dx - perpX2;
		@Pc(109) int dx2 = originX + dx + perpX1;
		@Pc(113) int ay = originY + perpY1;
		@Pc(117) int by = originY - perpY2;
		@Pc(123) int cy = originY + dy - perpY2;
		@Pc(129) int dy2 = originY + dy + perpY1;
		Rasteriser.testPoints(ax, bx, cx);
		Rasteriser.fillTriangle(ay, by, cy, ax, bx, cx, color);
		Rasteriser.testPoints(ax, cx, dx2);
		Rasteriser.fillTriangle(ay, cy, dy2, ax, cx, dx2, color);
	}

	@OriginalMember(owner = "client!kb", name = "c", descriptor = "(IIIII)V")
	public static void fillRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int color) {
		if (x < clipLeft) {
			w -= clipLeft - x;
			x = clipLeft;
		}
		if (y < clipTop) {
			h -= clipTop - y;
			y = clipTop;
		}
		if (x + w > clipRight) {
			w = clipRight - x;
		}
		if (y + h > clipBottom) {
			h = clipBottom - y;
		}
		@Pc(43) int stride = width - w;
		@Pc(49) int offset = x + y * width;
		for (@Pc(52) int row = -h; row < 0; row++) {
			for (@Pc(57) int col = -w; col < 0; col++) {
				pixels[offset++] = color;
			}
			offset += stride;
		}
	}

	@OriginalMember(owner = "client!kb", name = "c", descriptor = "(IIII)V")
	public static void setClip(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (left < 0) {
			left = 0;
		}
		if (top < 0) {
			top = 0;
		}
		if (right > width) {
			right = width;
		}
		if (bottom > height) {
			bottom = height;
		}
		clipLeft = left;
		clipTop = top;
		clipRight = right;
		clipBottom = bottom;
		clearLineMasks();
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "([I)V")
	public static void saveClip(@OriginalArg(0) int[] clip) {
		clip[0] = clipLeft;
		clip[1] = clipTop;
		clip[2] = clipRight;
		clip[3] = clipBottom;
	}

	@OriginalMember(owner = "client!kb", name = "d", descriptor = "(IIII)V")
	public static void shrinkClip(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (clipLeft < left) {
			clipLeft = left;
		}
		if (clipTop < top) {
			clipTop = top;
		}
		if (clipRight > right) {
			clipRight = right;
		}
		if (clipBottom > bottom) {
			clipBottom = bottom;
		}
		clearLineMasks();
	}

	@OriginalMember(owner = "client!kb", name = "d", descriptor = "(IIIII)V")
	private static void drawVerticalLineAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int length, @OriginalArg(3) int color, @OriginalArg(4) int alpha) {
		if (x < clipLeft || x >= clipRight) {
			return;
		}
		if (y < clipTop) {
			length -= clipTop - y;
			y = clipTop;
		}
		if (y + length > clipBottom) {
			length = clipBottom - y;
		}
		@Pc(30) int invAlpha = 256 - alpha;
		@Pc(38) int srcR = (color >> 16 & 0xFF) * alpha;
		@Pc(46) int srcG = (color >> 8 & 0xFF) * alpha;
		@Pc(52) int srcB = (color & 0xFF) * alpha;
		@Pc(58) int offset = x + y * width;
		for (@Pc(60) int i = 0; i < length; i++) {
			@Pc(73) int dstR = (pixels[offset] >> 16 & 0xFF) * invAlpha;
			@Pc(83) int dstG = (pixels[offset] >> 8 & 0xFF) * invAlpha;
			@Pc(91) int dstB = (pixels[offset] & 0xFF) * invAlpha;
			@Pc(113) int blended = (srcR + dstR >> 8 << 16) + (srcG + dstG >> 8 << 8) + (srcB + dstB >> 8);
			pixels[offset] = blended;
			offset += width;
		}
	}

	@OriginalMember(owner = "client!kb", name = "e", descriptor = "(IIIII)V")
	public static void drawLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int color) {
		x2 -= x1;
		y2 -= y1;
		if (y2 == 0) {
			if (x2 >= 0) {
				drawHorizontalLine(x1, y1, x2 + 1, color);
			} else {
				drawHorizontalLine(x1 + x2, y1, 1 - x2, color);
			}
		} else if (x2 != 0) {
			if (x2 + y2 < 0) {
				x1 += x2;
				x2 = -x2;
				y1 += y2;
				y2 = -y2;
			}
			@Pc(96) int step;
			@Pc(127) int coord;
			if (x2 > y2) {
				y1 <<= 0x10;
				y1 += 32768;
				@Pc(86) int scaledDy = y2 << 16;
				step = (int) Math.floor((double) scaledDy / (double) x2 + 0.5D);
				x2 += x1;
				if (x1 < clipLeft) {
					y1 += step * (clipLeft - x1);
					x1 = clipLeft;
				}
				if (x2 >= clipRight) {
					x2 = clipRight - 1;
				}
				while (x1 <= x2) {
					coord = y1 >> 16;
					if (coord >= clipTop && coord < clipBottom) {
						pixels[x1 + coord * width] = color;
					}
					y1 += step;
					x1++;
				}
			} else {
				x1 <<= 0x10;
				x1 += 32768;
				@Pc(160) int scaledDx = x2 << 16;
				step = (int) Math.floor((double) scaledDx / (double) y2 + 0.5D);
				y2 += y1;
				if (y1 < clipTop) {
					x1 += step * (clipTop - y1);
					y1 = clipTop;
				}
				if (y2 >= clipBottom) {
					y2 = clipBottom - 1;
				}
				while (y1 <= y2) {
					coord = x1 >> 16;
					if (coord >= clipLeft && coord < clipRight) {
						pixels[coord + y1 * width] = color;
					}
					x1 += step;
					y1++;
				}
			}
		} else if (y2 >= 0) {
			drawVerticalLine(x1, y1, y2 + 1, color);
		} else {
			drawVerticalLine(x1, y1 + y2, -y2 + 1, color);
		}
	}

	@OriginalMember(owner = "client!kb", name = "e", descriptor = "(IIII)V")
	private static void fillCircle(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int radius) {
		if (radius == 0) {
			drawDot(x, y);
			return;
		}
		if (radius < 0) {
			radius = -radius;
		}
		@Pc(15) int startY = y - radius;
		if (startY < clipTop) {
			startY = clipTop;
		}
		@Pc(26) int endY = y + radius + 1;
		if (endY > clipBottom) {
			endY = clipBottom;
		}
		@Pc(33) int curY = startY;
		@Pc(37) int radiusSq = radius * radius;
		@Pc(39) int halfWidth = 0;
		@Pc(43) int dy = y - startY;
		@Pc(47) int dySq = dy * dy;
		@Pc(51) int innerDySq = dySq - dy;
		if (y > endY) {
			y = endY;
		}
		@Pc(85) int leftX;
		@Pc(94) int rightX;
		@Pc(105) int offset;
		@Pc(107) int px;
		while (curY < y) {
			while (innerDySq <= radiusSq || dySq <= radiusSq) {
				dySq += halfWidth + halfWidth;
				innerDySq += halfWidth++ + halfWidth;
			}
			leftX = x + 1 - halfWidth;
			if (leftX < clipLeft) {
				leftX = clipLeft;
			}
			rightX = x + halfWidth;
			if (rightX > clipRight) {
				rightX = clipRight;
			}
			offset = leftX + curY * width;
			for (px = leftX; px < rightX; px++) {
				pixels[offset++] = 16776960;
			}
			curY++;
			dySq -= dy-- + dy;
			innerDySq -= dy + dy;
		}
		halfWidth = radius;
		dy = curY - y;
		innerDySq = dy * dy + radiusSq;
		dySq = innerDySq - radius;
		innerDySq -= dy;
		while (curY < endY) {
			while (innerDySq > radiusSq && dySq > radiusSq) {
				innerDySq -= halfWidth-- + halfWidth;
				dySq -= halfWidth + halfWidth;
			}
			leftX = x - halfWidth;
			if (leftX < clipLeft) {
				leftX = clipLeft;
			}
			rightX = x + halfWidth;
			if (rightX > clipRight - 1) {
				rightX = clipRight - 1;
			}
			offset = leftX + curY * width;
			for (px = leftX; px <= rightX; px++) {
				pixels[offset++] = 16776960;
			}
			curY++;
			innerDySq += dy + dy;
			dySq += dy++ + dy;
		}
	}

	@OriginalMember(owner = "client!kb", name = "f", descriptor = "(IIIII)V")
	public static void fillCircleAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int radius, @OriginalArg(4) int alpha) {
		if (alpha == 0) {
			return;
		}
		if (alpha == 256) {
			fillCircle(x, y, radius);
			return;
		}
		@Pc(20) int invAlpha = 256 - alpha;
		@Pc(28) int srcR = alpha * 255;
		@Pc(36) int srcG = alpha * 255;
		@Pc(42) int srcB = alpha * 0;
		@Pc(46) int startY = y - radius;
		if (startY < clipTop) {
			startY = clipTop;
		}
		@Pc(57) int endY = y + radius + 1;
		if (endY > clipBottom) {
			endY = clipBottom;
		}
		@Pc(64) int curY = startY;
		@Pc(68) int radiusSq = radius * radius;
		@Pc(70) int halfWidth = 0;
		@Pc(74) int dy = y - startY;
		@Pc(78) int dySq = dy * dy;
		@Pc(82) int innerDySq = dySq - dy;
		if (y > endY) {
			y = endY;
		}
		@Pc(151) int dstR;
		@Pc(161) int dstG;
		@Pc(169) int dstB;
		@Pc(116) int leftX;
		@Pc(125) int rightX;
		@Pc(136) int offset;
		@Pc(138) int px;
		@Pc(191) int blended;
		while (curY < y) {
			while (innerDySq <= radiusSq || dySq <= radiusSq) {
				dySq += halfWidth + halfWidth;
				innerDySq += halfWidth++ + halfWidth;
			}
			leftX = x + 1 - halfWidth;
			if (leftX < clipLeft) {
				leftX = clipLeft;
			}
			rightX = x + halfWidth;
			if (rightX > clipRight) {
				rightX = clipRight;
			}
			offset = leftX + curY * width;
			for (px = leftX; px < rightX; px++) {
				dstR = (pixels[offset] >> 16 & 0xFF) * invAlpha;
				dstG = (pixels[offset] >> 8 & 0xFF) * invAlpha;
				dstB = (pixels[offset] & 0xFF) * invAlpha;
				blended = (srcR + dstR >> 8 << 16) + (srcG + dstG >> 8 << 8) + (srcB + dstB >> 8);
				pixels[offset++] = blended;
			}
			curY++;
			dySq -= dy-- + dy;
			innerDySq -= dy + dy;
		}
		halfWidth = radius;
		dy = -dy;
		innerDySq = dy * dy + radiusSq;
		dySq = innerDySq - radius;
		innerDySq -= dy;
		while (curY < endY) {
			while (innerDySq > radiusSq && dySq > radiusSq) {
				innerDySq -= halfWidth-- + halfWidth;
				dySq -= halfWidth + halfWidth;
			}
			leftX = x - halfWidth;
			if (leftX < clipLeft) {
				leftX = clipLeft;
			}
			rightX = x + halfWidth;
			if (rightX > clipRight - 1) {
				rightX = clipRight - 1;
			}
			offset = leftX + curY * width;
			for (px = leftX; px <= rightX; px++) {
				dstR = (pixels[offset] >> 16 & 0xFF) * invAlpha;
				dstG = (pixels[offset] >> 8 & 0xFF) * invAlpha;
				dstB = (pixels[offset] & 0xFF) * invAlpha;
				blended = (srcR + dstR >> 8 << 16) + (srcG + dstG >> 8 << 8) + (srcB + dstB >> 8);
				pixels[offset++] = blended;
			}
			curY++;
			innerDySq += dy + dy;
			dySq += dy++ + dy;
		}
	}

	@OriginalMember(owner = "client!kb", name = "c", descriptor = "()V")
	public static void resetClip() {
		clipLeft = 0;
		clipTop = 0;
		clipRight = width;
		clipBottom = height;
		clearLineMasks();
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(III[I[I)V")
	public static void clearMaskedRegion(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(3) int[] starts, @OriginalArg(4) int[] widths) {
		@Pc(5) int rowOffset = x + y * width;
		for (@Pc(7) int i = 0; i < starts.length; i++) {
			@Pc(17) int offset = rowOffset + starts[i];
			for (@Pc(22) int j = -widths[i]; j < 0; j++) {
				pixels[offset++] = 0;
			}
			rowOffset += width;
		}
	}
}
