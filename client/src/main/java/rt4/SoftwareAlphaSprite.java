package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!am")
public final class SoftwareAlphaSprite extends SoftwareSprite {

	@OriginalMember(owner = "client!am", name = "<init>", descriptor = "(IIIIII[I)V")
	public SoftwareAlphaSprite(@OriginalArg(0) int innerWidth, @OriginalArg(1) int innerHeight, @OriginalArg(2) int xOffset, @OriginalArg(3) int yOffset, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int[] pixels) {
		super(innerWidth, innerHeight, xOffset, yOffset, width, height, pixels);
	}

	@OriginalMember(owner = "client!am", name = "b", descriptor = "([I[IIIIIIIIIIII)V")
	public static void blitAlphaScaledWithOpacity(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int dstOff, @OriginalArg(6) int dstStep, @OriginalArg(7) int w, @OriginalArg(8) int h, @OriginalArg(9) int xStep, @OriginalArg(10) int yStep, @OriginalArg(11) int srcWidth, @OriginalArg(12) int opacity) {
		@Pc(1) int startSrcX = srcX;
		for (@Pc(4) int row = -h; row < 0; row++) {
			@Pc(12) int srcRow = (srcY >> 16) * srcWidth;
			for (@Pc(15) int col = -w; col < 0; col++) {
				@Pc(25) int pixel = src[(srcX >> 16) + srcRow];
				@Pc(29) int dstPixel = dst[dstOff];
				@Pc(37) int alpha = (pixel >>> 24) * opacity >> 8;
				@Pc(41) int invAlpha = 256 - alpha;
				dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
				srcX += xStep;
			}
			srcY += yStep;
			srcX = startSrcX;
			dstOff += dstStep;
		}
	}

	@OriginalMember(owner = "client!am", name = "c", descriptor = "([I[IIIIIIII)V")
	public static void blitAlphaFlipped(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(2) int negW = -w;
		for (@Pc(5) int row = -h; row < 0; row++) {
			for (@Pc(9) int col = negW; col < 0; col++) {
				@Pc(16) int pixel = src[srcOff--];
				@Pc(20) int alpha = pixel >>> 24;
				if (alpha == 0) {
					dstOff++;
				} else {
					@Pc(26) int invAlpha = 256 - alpha;
					@Pc(30) int dstPixel = dst[dstOff];
					dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
				}
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!am", name = "d", descriptor = "([I[IIIIIIII)V")
	public static void blitAlpha(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(2) int negW = -w;
		for (@Pc(5) int row = -h; row < 0; row++) {
			for (@Pc(9) int col = negW; col < 0; col++) {
				@Pc(16) int pixel = src[srcOff++];
				@Pc(20) int alpha = pixel >>> 24;
				if (alpha == 0) {
					dstOff++;
				} else {
					@Pc(26) int invAlpha = 256 - alpha;
					@Pc(30) int dstPixel = dst[dstOff];
					dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
				}
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!am", name = "b", descriptor = "([I[IIIIIIIII)V")
	public static void blitAlphaWithOpacity(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int opacity) {
		for (@Pc(2) int row = -h; row < 0; row++) {
			for (@Pc(7) int col = -w; col < 0; col++) {
				@Pc(19) int alpha = (src[srcOff] >>> 24) * opacity >> 8;
				@Pc(23) int invAlpha = 256 - alpha;
				@Pc(28) int pixel = src[srcOff++];
				@Pc(32) int dstPixel = dst[dstOff];
				dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!am", name = "b", descriptor = "([I[IIIIIIIIIII)V")
	public static void blitAlphaScaled(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int dstOff, @OriginalArg(6) int dstStep, @OriginalArg(7) int w, @OriginalArg(8) int h, @OriginalArg(9) int xStep, @OriginalArg(10) int yStep, @OriginalArg(11) int srcWidth) {
		@Pc(1) int startSrcX = srcX;
		for (@Pc(4) int row = -h; row < 0; row++) {
			@Pc(12) int srcRow = (srcY >> 16) * srcWidth;
			for (@Pc(15) int col = -w; col < 0; col++) {
				@Pc(25) int pixel = src[(srcX >> 16) + srcRow];
				@Pc(29) int alpha = pixel >>> 24;
				if (alpha == 0) {
					dstOff++;
				} else {
					@Pc(35) int invAlpha = 256 - alpha;
					@Pc(39) int dstPixel = dst[dstOff];
					dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
				}
				srcX += xStep;
			}
			srcY += yStep;
			srcX = startSrcX;
			dstOff += dstStep;
		}
	}

	@OriginalMember(owner = "client!am", name = "a", descriptor = "(IIIIIIDI)V")
	@Override
	public final void drawRotatedFixed(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(6) double theta) {
		try {
			@Pc(15) int sinTheta = (int) (Math.sin(theta) * 65536.0D);
			@Pc(21) int cosTheta = (int) (Math.cos(theta) * 65536.0D);
			@Pc(27) int sinStep = sinTheta * 256 >> 8;
			@Pc(33) int cosStep = cosTheta * 256 >> 8;
			@Pc(45) int u = sinStep * -10 + cosStep * -10 + 983040;
			@Pc(57) int v = cosStep * -10 + 983040 - sinStep * -10;
			@Pc(63) int dstOff = x + y * SoftwareRaster.width;
			for (@Pc(65) int row = 0; row < 20; row++) {
				@Pc(70) int dst = dstOff;
				@Pc(72) int curU = u;
				@Pc(74) int curV = v;
				for (@Pc(77) int col = -20; col < 0; col++) {
					@Pc(93) int pixel = this.pixels[(curU >> 16) + (curV >> 16) * this.width];
					@Pc(97) int dstPixel = SoftwareRaster.pixels[dst];
					@Pc(101) int alpha = pixel >>> 24;
					@Pc(105) int invAlpha = 256 - alpha;
					SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
					curU += cosStep;
					curV -= sinStep;
				}
				u += sinStep;
				v += cosStep;
				dstOff += SoftwareRaster.width;
			}
		} catch (@Pc(164) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!am", name = "e", descriptor = "(II)V")
	@Override
	public final void render(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		x += this.xOffset;
		y += this.yOffset;
		@Pc(15) int dstOff = x + y * SoftwareRaster.width;
		@Pc(17) int srcOff = 0;
		@Pc(20) int h = this.height;
		@Pc(23) int w = this.width;
		@Pc(27) int dstStep = SoftwareRaster.width - w;
		@Pc(29) int srcStep = 0;
		@Pc(36) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			h -= clip;
			y = SoftwareRaster.clipTop;
			srcOff = clip * w;
			dstOff += clip * SoftwareRaster.width;
		}
		if (y + h > SoftwareRaster.clipBottom) {
			h -= y + h - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			w -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff += clip;
			dstOff += clip;
			srcStep = clip;
			dstStep += clip;
		}
		if (x + w > SoftwareRaster.clipRight) {
			clip = x + w - SoftwareRaster.clipRight;
			w -= clip;
			srcStep += clip;
			dstStep += clip;
		}
		if (w > 0 && h > 0) {
			blitAlpha(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!am", name = "c", descriptor = "(II)V")
	@Override
	public final void drawPixels(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		x += this.xOffset;
		y += this.yOffset;
		@Pc(15) int dstOff = x + y * SoftwareRaster.width;
		@Pc(17) int srcOff = 0;
		@Pc(20) int h = this.height;
		@Pc(23) int w = this.width;
		@Pc(27) int dstStep = SoftwareRaster.width - w;
		@Pc(29) int srcStep = 0;
		@Pc(36) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			h -= clip;
			y = SoftwareRaster.clipTop;
			srcOff = clip * w;
			dstOff += clip * SoftwareRaster.width;
		}
		if (y + h > SoftwareRaster.clipBottom) {
			h -= y + h - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			w -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff += clip;
			dstOff += clip;
			srcStep = clip;
			dstStep += clip;
		}
		if (x + w > SoftwareRaster.clipRight) {
			clip = x + w - SoftwareRaster.clipRight;
			w -= clip;
			srcStep += clip;
			dstStep += clip;
		}
		if (w > 0 && h > 0) {
			blitAlpha(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!am", name = "a", descriptor = "(IIIIII)V")
	@Override
	protected final void drawRotatedScaled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int pivotX, @OriginalArg(3) int pivotY, @OriginalArg(4) int angle, @OriginalArg(5) int zoom) {
		if (zoom == 0) {
			return;
		}
		@Pc(9) int adjX = x - (this.xOffset << 4);
		@Pc(16) int adjY = y - (this.yOffset << 4);
		@Pc(23) double theta = (double) (angle & 0xFFFF) * 9.587379924285257E-5D;
		@Pc(33) int sinTheta = (int) Math.floor(Math.sin(theta) * (double) zoom + 0.5D);
		@Pc(43) int cosTheta = (int) Math.floor(Math.cos(theta) * (double) zoom + 0.5D);
		@Pc(53) int ulX = -adjX * cosTheta + -adjY * sinTheta;
		@Pc(64) int ulY = --adjX * sinTheta + -adjY * cosTheta;
		@Pc(78) int urX = ((this.width << 4) - adjX) * cosTheta + -adjY * sinTheta;
		@Pc(93) int urY = -((this.width << 4) - adjX) * sinTheta + -adjY * cosTheta;
		@Pc(107) int llX = -adjX * cosTheta + ((this.height << 4) - adjY) * sinTheta;
		@Pc(122) int llY = --adjX * sinTheta + ((this.height << 4) - adjY) * cosTheta;
		@Pc(140) int lrX = ((this.width << 4) - adjX) * cosTheta + ((this.height << 4) - adjY) * sinTheta;
		@Pc(159) int lrY = -((this.width << 4) - adjX) * sinTheta + ((this.height << 4) - adjY) * cosTheta;
		@Pc(164) int minX;
		@Pc(166) int maxX;
		if (ulX < urX) {
			minX = ulX;
			maxX = urX;
		} else {
			minX = urX;
			maxX = ulX;
		}
		if (llX < minX) {
			minX = llX;
		}
		if (lrX < minX) {
			minX = lrX;
		}
		if (llX > maxX) {
			maxX = llX;
		}
		if (lrX > maxX) {
			maxX = lrX;
		}
		@Pc(196) int minY;
		@Pc(198) int maxY;
		if (ulY < urY) {
			minY = ulY;
			maxY = urY;
		} else {
			minY = urY;
			maxY = ulY;
		}
		if (llY < minY) {
			minY = llY;
		}
		if (lrY < minY) {
			minY = lrY;
		}
		if (llY > maxY) {
			maxY = llY;
		}
		if (lrY > maxY) {
			maxY = lrY;
		}
		minX >>= 0xC;
		maxX = maxX + 4095 >> 12;
		minY >>= 0xC;
		maxY = maxY + 4095 >> 12;
		minX += pivotX;
		maxX += pivotX;
		minY += pivotY;
		maxY += pivotY;
		minX >>= 0x4;
		maxX = maxX + 15 >> 4;
		minY >>= 0x4;
		maxY = maxY + 15 >> 4;
		if (minX < SoftwareRaster.clipLeft) {
			minX = SoftwareRaster.clipLeft;
		}
		if (maxX > SoftwareRaster.clipRight) {
			maxX = SoftwareRaster.clipRight;
		}
		if (minY < SoftwareRaster.clipTop) {
			minY = SoftwareRaster.clipTop;
		}
		if (maxY > SoftwareRaster.clipBottom) {
			maxY = SoftwareRaster.clipBottom;
		}
		maxX = minX - maxX;
		if (maxX >= 0) {
			return;
		}
		maxY = minY - maxY;
		if (maxY >= 0) {
			return;
		}
		@Pc(319) int dstOff = minY * SoftwareRaster.width + minX;
		@Pc(324) double invZoom = 1.6777216E7D / (double) zoom;
		@Pc(333) int sinStep = (int) Math.floor(Math.sin(theta) * invZoom + 0.5D);
		@Pc(342) int cosStep = (int) Math.floor(Math.cos(theta) * invZoom + 0.5D);
		@Pc(350) int pixelOffX = (minX << 4) + 8 - pivotX;
		@Pc(358) int pixelOffY = (minY << 4) + 8 - pivotY;
		@Pc(368) int u = (adjX << 8) - (pixelOffY * sinStep >> 4);
		@Pc(378) int v = (adjY << 8) + (pixelOffY * cosStep >> 4);
		@Pc(384) int row;
		@Pc(388) int dst;
		@Pc(504) int curV;
		@Pc(394) int col;
		@Pc(432) int pixel;
		@Pc(436) int dstPixel;
		@Pc(440) int alpha;
		@Pc(444) int invAlpha;
		@Pc(532) int skip;
		if (cosStep != 0) {
			@Pc(802) int curU;
			if (cosStep < 0) {
				if (sinStep == 0) {
					row = maxY;
					while (row < 0) {
						dst = dstOff;
						curU = u + (pixelOffX * cosStep >> 4);
						col = maxX;
						if (v >= 0 && v - (this.height << 12) < 0) {
							@Pc(825) int uEdge;
							if ((uEdge = curU - (this.width << 12)) >= 0) {
								skip = (cosStep - uEdge) / cosStep;
								col = maxX + skip;
								curU += cosStep * skip;
								dst = dstOff + skip;
							}
							@Pc(853) int uLimit;
							if ((uLimit = (curU - cosStep) / cosStep) > col) {
								col = uLimit;
							}
							while (col < 0) {
								pixel = this.pixels[(v >> 12) * this.width + (curU >> 12)];
								dstPixel = SoftwareRaster.pixels[dst];
								alpha = pixel >>> 24;
								invAlpha = 256 - alpha;
								SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
								curU += cosStep;
								col++;
							}
						}
						row++;
						v += cosStep;
						dstOff += SoftwareRaster.width;
					}
				} else if (sinStep < 0) {
					row = maxY;
					while (row < 0) {
						dst = dstOff;
						curU = u + (pixelOffX * cosStep >> 4);
						curV = v + (pixelOffX * sinStep >> 4);
						col = maxX;
						@Pc(969) int uEdge;
						if ((uEdge = curU - (this.width << 12)) >= 0) {
							skip = (cosStep - uEdge) / cosStep;
							col = maxX + skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst = dstOff + skip;
						}
						@Pc(1003) int uLimit;
						if ((uLimit = (curU - cosStep) / cosStep) > col) {
							col = uLimit;
						}
						@Pc(1015) int vEdge;
						if ((vEdge = curV - (this.height << 12)) >= 0) {
							skip = (sinStep - vEdge) / sinStep;
							col += skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst += skip;
						}
						@Pc(1049) int vLimit;
						if ((vLimit = (curV - sinStep) / sinStep) > col) {
							col = vLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
							dstPixel = SoftwareRaster.pixels[dst];
							alpha = pixel >>> 24;
							invAlpha = 256 - alpha;
							SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
							curU += cosStep;
							curV += sinStep;
							col++;
						}
						row++;
						u -= sinStep;
						v += cosStep;
						dstOff += SoftwareRaster.width;
					}
				} else {
					row = maxY;
					while (row < 0) {
						dst = dstOff;
						curU = u + (pixelOffX * cosStep >> 4);
						curV = v + (pixelOffX * sinStep >> 4);
						col = maxX;
						@Pc(1171) int uEdge;
						if ((uEdge = curU - (this.width << 12)) >= 0) {
							skip = (cosStep - uEdge) / cosStep;
							col = maxX + skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst = dstOff + skip;
						}
						@Pc(1205) int uLimit;
						if ((uLimit = (curU - cosStep) / cosStep) > col) {
							col = uLimit;
						}
						if (curV < 0) {
							skip = (sinStep - curV - 1) / sinStep;
							col += skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst += skip;
						}
						@Pc(1253) int vLimit;
						if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
							col = vLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
							dstPixel = SoftwareRaster.pixels[dst];
							alpha = pixel >>> 24;
							invAlpha = 256 - alpha;
							SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
							curU += cosStep;
							curV += sinStep;
							col++;
						}
						row++;
						u -= sinStep;
						v += cosStep;
						dstOff += SoftwareRaster.width;
					}
				}
			} else if (sinStep == 0) {
				row = maxY;
				while (row < 0) {
					dst = dstOff;
					curU = u + (pixelOffX * cosStep >> 4);
					col = maxX;
					if (v >= 0 && v - (this.height << 12) < 0) {
						if (curU < 0) {
							skip = (cosStep - curU - 1) / cosStep;
							col = maxX + skip;
							curU += cosStep * skip;
							dst = dstOff + skip;
						}
						@Pc(1412) int uLimit;
						if ((uLimit = (curU + 1 - (this.width << 12) - cosStep) / cosStep) > col) {
							col = uLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(v >> 12) * this.width + (curU >> 12)];
							dstPixel = SoftwareRaster.pixels[dst];
							alpha = pixel >>> 24;
							invAlpha = 256 - alpha;
							SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
							curU += cosStep;
							col++;
						}
					}
					row++;
					v += cosStep;
					dstOff += SoftwareRaster.width;
				}
			} else if (sinStep < 0) {
				row = maxY;
				while (row < 0) {
					dst = dstOff;
					curU = u + (pixelOffX * cosStep >> 4);
					curV = v + (pixelOffX * sinStep >> 4);
					col = maxX;
					if (curU < 0) {
						skip = (cosStep - curU - 1) / cosStep;
						col = maxX + skip;
						curU += cosStep * skip;
						curV += sinStep * skip;
						dst = dstOff + skip;
					}
					@Pc(1564) int uLimit;
					if ((uLimit = (curU + 1 - (this.width << 12) - cosStep) / cosStep) > col) {
						col = uLimit;
					}
					@Pc(1576) int vEdge;
					if ((vEdge = curV - (this.height << 12)) >= 0) {
						skip = (sinStep - vEdge) / sinStep;
						col += skip;
						curU += cosStep * skip;
						curV += sinStep * skip;
						dst += skip;
					}
					@Pc(1610) int vLimit;
					if ((vLimit = (curV - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
						dstPixel = SoftwareRaster.pixels[dst];
						alpha = pixel >>> 24;
						invAlpha = 256 - alpha;
						SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
						curU += cosStep;
						curV += sinStep;
						col++;
					}
					row++;
					u -= sinStep;
					v += cosStep;
					dstOff += SoftwareRaster.width;
				}
			} else {
				row = maxY;
				while (row < 0) {
					dst = dstOff;
					curU = u + (pixelOffX * cosStep >> 4);
					curV = v + (pixelOffX * sinStep >> 4);
					col = maxX;
					if (curU < 0) {
						skip = (cosStep - curU - 1) / cosStep;
						col = maxX + skip;
						curU += cosStep * skip;
						curV += sinStep * skip;
						dst = dstOff + skip;
					}
					@Pc(1768) int uLimit;
					if ((uLimit = (curU + 1 - (this.width << 12) - cosStep) / cosStep) > col) {
						col = uLimit;
					}
					if (curV < 0) {
						skip = (sinStep - curV - 1) / sinStep;
						col += skip;
						curU += cosStep * skip;
						curV += sinStep * skip;
						dst += skip;
					}
					@Pc(1816) int vLimit;
					if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
						dstPixel = SoftwareRaster.pixels[dst];
						alpha = pixel >>> 24;
						invAlpha = 256 - alpha;
						SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
						curU += cosStep;
						curV += sinStep;
						col++;
					}
					row++;
					u -= sinStep;
					v += cosStep;
					dstOff += SoftwareRaster.width;
				}
			}
		} else if (sinStep == 0) {
			row = maxY;
			while (row < 0) {
				dst = dstOff;
				col = maxX;
				if (u >= 0 && v >= 0 && u - (this.width << 12) < 0 && v - (this.height << 12) < 0) {
					while (col < 0) {
						pixel = this.pixels[(v >> 12) * this.width + (u >> 12)];
						dstPixel = SoftwareRaster.pixels[dst];
						alpha = pixel >>> 24;
						invAlpha = 256 - alpha;
						SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
						col++;
					}
				}
				row++;
				dstOff += SoftwareRaster.width;
			}
		} else if (sinStep < 0) {
			row = maxY;
			while (row < 0) {
				dst = dstOff;
				curV = v + (pixelOffX * sinStep >> 4);
				col = maxX;
				if (u >= 0 && u - (this.width << 12) < 0) {
					@Pc(525) int vEdge;
					if ((vEdge = curV - (this.height << 12)) >= 0) {
						skip = (sinStep - vEdge) / sinStep;
						col = maxX + skip;
						curV += sinStep * skip;
						dst = dstOff + skip;
					}
					@Pc(553) int vLimit;
					if ((vLimit = (curV - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (u >> 12)];
						dstPixel = SoftwareRaster.pixels[dst];
						alpha = pixel >>> 24;
						invAlpha = 256 - alpha;
						SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
						curV += sinStep;
						col++;
					}
				}
				row++;
				u -= sinStep;
				dstOff += SoftwareRaster.width;
			}
		} else {
			row = maxY;
			while (row < 0) {
				dst = dstOff;
				curV = v + (pixelOffX * sinStep >> 4);
				col = maxX;
				if (u >= 0 && u - (this.width << 12) < 0) {
					if (curV < 0) {
						skip = (sinStep - curV - 1) / sinStep;
						col = maxX + skip;
						curV += sinStep * skip;
						dst = dstOff + skip;
					}
					@Pc(702) int vLimit;
					if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (u >> 12)];
						dstPixel = SoftwareRaster.pixels[dst];
						alpha = pixel >>> 24;
						invAlpha = 256 - alpha;
						SoftwareRaster.pixels[dst++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
						curV += sinStep;
						col++;
					}
				}
				row++;
				u -= sinStep;
				dstOff += SoftwareRaster.width;
			}
		}
	}

	@OriginalMember(owner = "client!am", name = "a", descriptor = "(IIIIIIII[I[I)V")
	@Override
	public final void renderRotated(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int anchorX, @OriginalArg(5) int anchorY, @OriginalArg(6) int theta, @OriginalArg(7) int zoom, @OriginalArg(8) int[] lineStarts, @OriginalArg(9) int[] lineWidths) {
		try {
			@Pc(4) int centerX = -w / 2;
			@Pc(9) int centerY = -h / 2;
			@Pc(18) int sin1 = (int) (Math.sin((double) theta / 326.11D) * 65536.0D);
			@Pc(27) int cos1 = (int) (Math.cos((double) theta / 326.11D) * 65536.0D);
			@Pc(33) int sin = sin1 * zoom >> 8;
			@Pc(39) int cos = cos1 * zoom >> 8;
			@Pc(51) int originX = (anchorX << 16) + centerY * sin + centerX * cos;
			@Pc(63) int originY = (anchorY << 16) + (centerY * cos - centerX * sin);
			@Pc(69) int origin = x + y * SoftwareRaster.width;
			for (@Pc(71) int iy = 0; iy < h; iy++) {
				@Pc(78) int start = lineStarts[iy];
				@Pc(82) int dstOff = origin + start;
				@Pc(88) int dstX = originX + cos * start;
				@Pc(94) int dstY = originY - sin * start;
				for (@Pc(99) int ix = -lineWidths[iy]; ix < 0; ix++) {
					@Pc(115) int pixel = this.pixels[(dstX >> 16) + (dstY >> 16) * this.width];
					@Pc(119) int dstPixel = SoftwareRaster.pixels[dstOff];
					@Pc(123) int alpha = pixel >>> 24;
					@Pc(127) int invAlpha = 256 - alpha;
					SoftwareRaster.pixels[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
					dstX += cos;
					dstY -= sin;
				}
				originX += sin;
				originY += cos;
				origin += SoftwareRaster.width;
			}
		} catch (@Pc(186) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!am", name = "b", descriptor = "(IIIIIIII[I[I)V")
	@Override
	public final void renderRotated(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int anchorX, @OriginalArg(5) int anchorY, @OriginalArg(6) int theta, @OriginalArg(8) int[] lineStart, @OriginalArg(9) int[] lineWidth) {
		try {
			@Pc(4) int centerX = -w / 2;
			@Pc(9) int centerY = -h / 2;
			@Pc(18) int sin1 = (int) (Math.sin((double) theta / 326.11D) * 65536.0D);
			@Pc(27) int cos1 = (int) (Math.cos((double) theta / 326.11D) * 65536.0D);
			@Pc(33) int sin = sin1 * 256 >> 8;
			@Pc(39) int cos = cos1 * 256 >> 8;
			@Pc(51) int originX = (anchorX << 16) + centerY * sin + centerX * cos;
			@Pc(63) int originY = (anchorY << 16) + (centerY * cos - centerX * sin);
			@Pc(69) int origin = x + y * SoftwareRaster.width;
			for (@Pc(71) int iy = 0; iy < h; iy++) {
				@Pc(78) int start = lineStart[iy];
				@Pc(82) int dstOff = origin + start;
				@Pc(88) int dstX = originX + cos * start;
				@Pc(94) int dstY = originY - sin * start;
				for (@Pc(99) int ix = -lineWidth[iy]; ix < 0; ix++) {
					@Pc(115) int pixel = this.pixels[(dstX >> 16) + (dstY >> 16) * this.width];
					@Pc(119) int dstPixel = SoftwareRaster.pixels[dstOff];
					@Pc(123) int alpha = pixel >>> 24;
					@Pc(127) int invAlpha = 256 - alpha;
					SoftwareRaster.pixels[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >>> 8;
					dstX += cos;
					dstY -= sin;
				}
				originX += sin;
				originY += cos;
				origin += SoftwareRaster.width;
			}
		} catch (@Pc(186) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!am", name = "b", descriptor = "(IIIII)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int scaledW, @OriginalArg(3) int scaledH, @OriginalArg(4) int opacity) {
		if (scaledW <= 0 || scaledH <= 0) {
			return;
		}
		@Pc(7) int srcW = this.width;
		@Pc(10) int srcH = this.height;
		@Pc(12) int srcX = 0;
		@Pc(14) int srcY = 0;
		@Pc(17) int fullW = this.innerWidth;
		@Pc(20) int fullH = this.innerHeight;
		@Pc(26) int xStep = (fullW << 16) / scaledW;
		@Pc(32) int yStep = (fullH << 16) / scaledH;
		@Pc(46) int dstOff;
		if (this.xOffset > 0) {
			dstOff = ((this.xOffset << 16) + xStep - 1) / xStep;
			x += dstOff;
			srcX = dstOff * xStep - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			dstOff = ((this.yOffset << 16) + yStep - 1) / yStep;
			y += dstOff;
			srcY = dstOff * yStep - (this.yOffset << 16);
		}
		if (srcW < fullW) {
			scaledW = ((srcW << 16) + xStep - srcX - 1) / xStep;
		}
		if (srcH < fullH) {
			scaledH = ((srcH << 16) + yStep - srcY - 1) / yStep;
		}
		dstOff = x + y * SoftwareRaster.width;
		@Pc(130) int dstStep = SoftwareRaster.width - scaledW;
		if (y + scaledH > SoftwareRaster.clipBottom) {
			scaledH -= y + scaledH - SoftwareRaster.clipBottom;
		}
		@Pc(150) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			scaledH -= clip;
			dstOff += clip * SoftwareRaster.width;
			srcY += yStep * clip;
		}
		if (x + scaledW > SoftwareRaster.clipRight) {
			clip = x + scaledW - SoftwareRaster.clipRight;
			scaledW -= clip;
			dstStep += clip;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			scaledW -= clip;
			dstOff += clip;
			srcX += xStep * clip;
			dstStep += clip;
		}
		blitAlphaScaledWithOpacity(SoftwareRaster.pixels, this.pixels, srcX, srcY, dstOff, dstStep, scaledW, scaledH, xStep, yStep, srcW, opacity);
	}

	@OriginalMember(owner = "client!am", name = "a", descriptor = "(IIII)V")
	@Override
	public final void renderResized(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		if (width <= 0 || height <= 0) {
			return;
		}
		@Pc(7) int srcW = this.width;
		@Pc(10) int srcH = this.height;
		@Pc(12) int srcX = 0;
		@Pc(14) int srcY = 0;
		@Pc(17) int fullW = this.innerWidth;
		@Pc(20) int fullH = this.innerHeight;
		@Pc(26) int xStep = (fullW << 16) / width;
		@Pc(32) int yStep = (fullH << 16) / height;
		@Pc(46) int dstOff;
		if (this.xOffset > 0) {
			dstOff = ((this.xOffset << 16) + xStep - 1) / xStep;
			x += dstOff;
			srcX = dstOff * xStep - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			dstOff = ((this.yOffset << 16) + yStep - 1) / yStep;
			y += dstOff;
			srcY = dstOff * yStep - (this.yOffset << 16);
		}
		if (srcW < fullW) {
			width = ((srcW << 16) + xStep - srcX - 1) / xStep;
		}
		if (srcH < fullH) {
			height = ((srcH << 16) + yStep - srcY - 1) / yStep;
		}
		dstOff = x + y * SoftwareRaster.width;
		@Pc(130) int dstStep = SoftwareRaster.width - width;
		if (y + height > SoftwareRaster.clipBottom) {
			height -= y + height - SoftwareRaster.clipBottom;
		}
		@Pc(150) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			height -= clip;
			dstOff += clip * SoftwareRaster.width;
			srcY += yStep * clip;
		}
		if (x + width > SoftwareRaster.clipRight) {
			clip = x + width - SoftwareRaster.clipRight;
			width -= clip;
			dstStep += clip;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			width -= clip;
			dstOff += clip;
			srcX += xStep * clip;
			dstStep += clip;
		}
		blitAlphaScaled(SoftwareRaster.pixels, this.pixels, srcX, srcY, dstOff, dstStep, width, height, xStep, yStep, srcW);
	}

	@OriginalMember(owner = "client!am", name = "d", descriptor = "(II)V")
	@Override
	public final void renderHorizontalFlip(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		x += this.innerWidth - this.width - this.xOffset;
		y += this.yOffset;
		@Pc(21) int dstOff = x + y * SoftwareRaster.width;
		@Pc(26) int srcOff = this.width - 1;
		@Pc(29) int h = this.height;
		@Pc(32) int w = this.width;
		@Pc(36) int dstStep = SoftwareRaster.width - w;
		@Pc(40) int srcStep = w + w;
		@Pc(47) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			h -= clip;
			y = SoftwareRaster.clipTop;
			srcOff += clip * w;
			dstOff += clip * SoftwareRaster.width;
		}
		if (y + h > SoftwareRaster.clipBottom) {
			h -= y + h - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			w -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff -= clip;
			dstOff += clip;
			srcStep -= clip;
			dstStep += clip;
		}
		if (x + w > SoftwareRaster.clipRight) {
			clip = x + w - SoftwareRaster.clipRight;
			w -= clip;
			srcStep -= clip;
			dstStep += clip;
		}
		if (w > 0 && h > 0) {
			blitAlphaFlipped(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!am", name = "a", descriptor = "(III)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha) {
		x += this.xOffset;
		y += this.yOffset;
		@Pc(15) int dstOff = x + y * SoftwareRaster.width;
		@Pc(17) int srcOff = 0;
		@Pc(20) int h = this.height;
		@Pc(23) int w = this.width;
		@Pc(27) int dstStep = SoftwareRaster.width - w;
		@Pc(29) int srcStep = 0;
		@Pc(36) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			h -= clip;
			y = SoftwareRaster.clipTop;
			srcOff = clip * w;
			dstOff += clip * SoftwareRaster.width;
		}
		if (y + h > SoftwareRaster.clipBottom) {
			h -= y + h - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			w -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff += clip;
			dstOff += clip;
			srcStep = clip;
			dstStep += clip;
		}
		if (x + w > SoftwareRaster.clipRight) {
			clip = x + w - SoftwareRaster.clipRight;
			w -= clip;
			srcStep += clip;
			dstStep += clip;
		}
		if (w > 0 && h > 0) {
			blitAlphaWithOpacity(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep, alpha);
		}
	}
}
