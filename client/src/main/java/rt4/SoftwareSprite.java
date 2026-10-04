package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mm")
public class SoftwareSprite extends Sprite {

	@OriginalMember(owner = "client!mm", name = "L", descriptor = "[I")
	public int[] pixels;

	@OriginalMember(owner = "client!mm", name = "<init>", descriptor = "(IIIIII[I)V")
	public SoftwareSprite(@OriginalArg(0) int innerWidth, @OriginalArg(1) int innerHeight, @OriginalArg(2) int xOffset, @OriginalArg(3) int yOffset, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int[] pixels) {
		this.innerWidth = innerWidth;
		this.innerHeight = innerHeight;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	@OriginalMember(owner = "client!mm", name = "<init>", descriptor = "(II)V")
	public SoftwareSprite(@OriginalArg(0) int width, @OriginalArg(1) int height) {
		this.pixels = new int[width * height];
		this.width = this.innerWidth = width;
		this.height = this.innerHeight = height;
		this.xOffset = this.yOffset = 0;
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "([I[IIIIIIII)V")
	public static void blitOpaque(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(4) int loopCount = -(w >> 2);
		@Pc(9) int remainder = -(w & 0x3);
		for (@Pc(12) int row = -h; row < 0; row++) {
			@Pc(16) int i;
			@Pc(23) int pixel;
			for (i = loopCount; i < 0; i++) {
				pixel = src[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
			}
			for (i = remainder; i < 0; i++) {
				pixel = src[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "b", descriptor = "([I[IIIIIIII)V")
	public static void blitOpaqueReverse(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(4) int loopCount = -(w >> 2);
		@Pc(9) int remainder = -(w & 0x3);
		for (@Pc(12) int row = -h; row < 0; row++) {
			@Pc(16) int i;
			@Pc(23) int pixel;
			for (i = loopCount; i < 0; i++) {
				pixel = src[srcOff--];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff--];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff--];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
				pixel = src[srcOff--];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
			}
			for (i = remainder; i < 0; i++) {
				pixel = src[srcOff--];
				if (pixel == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = pixel;
				}
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "([I[IIIIIII)V")
	public static void blitAllPixels(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(2) int srcOff, @OriginalArg(3) int dstOff, @OriginalArg(4) int w, @OriginalArg(5) int h, @OriginalArg(6) int dstStep, @OriginalArg(7) int srcStep) {
		for (@Pc(2) int row = -h; row < 0; row++) {
			@Pc(10) int end = dstOff + w - 3;
			while (dstOff < end) {
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
				dst[dstOff++] = src[srcOff++];
			}
			end += 3;
			while (dstOff < end) {
				dst[dstOff++] = src[srcOff++];
			}
			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "([I[IIIIIIIIIIII)V")
	public static void blitResizedAlpha(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int dstOff, @OriginalArg(6) int dstStep, @OriginalArg(7) int w, @OriginalArg(8) int h, @OriginalArg(9) int xStep, @OriginalArg(10) int yStep, @OriginalArg(11) int srcWidth, @OriginalArg(12) int alpha) {
		@Pc(3) int invAlpha = 256 - alpha;
		@Pc(5) int startSrcX = srcX;
		for (@Pc(8) int row = -h; row < 0; row++) {
			@Pc(16) int srcRow = (srcY >> 16) * srcWidth;
			for (@Pc(19) int col = -w; col < 0; col++) {
				@Pc(29) int pixel = src[(srcX >> 16) + srcRow];
				if (pixel == 0) {
					dstOff++;
				} else {
					@Pc(35) int dstPixel = dst[dstOff];
					dst[dstOff++] = ((pixel & 0xFF00FF) * alpha + (dstPixel & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((pixel & 0xFF00) * alpha + (dstPixel & 0xFF00) * invAlpha & 0xFF0000) >> 8;
				}
				srcX += xStep;
			}
			srcY += yStep;
			srcX = startSrcX;
			dstOff += dstStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "([I[IIIIIIIII)V")
	public static void drawAlpha(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcOff, @OriginalArg(4) int dstOff, @OriginalArg(5) int startX, @OriginalArg(6) int startY, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int alpha) {
		@Pc(3) int invAlpha = 256 - alpha;
		for (@Pc(6) int y = -startY; y < 0; y++) {
			for (@Pc(11) int x = -startX; x < 0; x++) {
				@Pc(18) int color = src[srcOff++];
				if (color == 0) {
					dstOff++;
				} else {
					@Pc(24) int rgb = dst[dstOff];
					dst[dstOff++] = ((color & 0xFF00FF) * alpha + (rgb & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((color & 0xFF00) * alpha + (rgb & 0xFF00) * invAlpha & 0xFF0000) >> 8;
				}
			}

			dstOff += dstStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "([I[IIIIIIIIIII)V")
	public static void drawResized(@OriginalArg(0) int[] dst, @OriginalArg(1) int[] src, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int dstOff, @OriginalArg(6) int dstStep, @OriginalArg(7) int startX, @OriginalArg(8) int startY, @OriginalArg(9) int xStep, @OriginalArg(10) int yStep, @OriginalArg(11) int srcWidth) {
		@Pc(1) int startSrcX = srcX;
		for (@Pc(4) int y = -startY; y < 0; y++) {
			@Pc(12) int srcRow = (srcY >> 16) * srcWidth;
			for (@Pc(15) int x = -startX; x < 0; x++) {
				@Pc(25) int color = src[(srcX >> 16) + srcRow];
				if (color == 0) {
					dstOff++;
				} else {
					dst[dstOff++] = color;
				}
				srcX += xStep;
			}
			srcY += yStep;
			srcX = startSrcX;
			dstOff += dstStep;
		}
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Lclient!ve;IIB)Lclient!mm;")
	public static SoftwareSprite loadSoftwareAlphaSprite(@OriginalArg(0) Js5 js5, @OriginalArg(2) int id) {
		return SpriteLoader.decode(js5, 0, id) ? createSoftwareAlphaSprite() : null;
	}

	@OriginalMember(owner = "client!hn", name = "d", descriptor = "(I)Lclient!mm;")
	public static SoftwareSprite createSoftwareAlphaSprite() {
		@Pc(13) int pixelCount = SpriteLoader.innerHeights[0] * SpriteLoader.innerWidths[0];
		@Pc(17) byte[] indexedPixels = SpriteLoader.pixels[0];
		@Pc(78) SoftwareSprite sprite;
		if (SpriteLoader.hasAlpha[0]) {
			@Pc(30) byte[] alphaData = SpriteLoader.alpha[0];
			@Pc(33) int[] rgbaPixels = new int[pixelCount];
			for (@Pc(35) int i = 0; i < pixelCount; i++) {
				rgbaPixels[i] = (alphaData[i] & 0xFF) << 24 | SpriteLoader.palette[indexedPixels[i] & 0xFF];
			}
			sprite = new SoftwareAlphaSprite(SpriteLoader.width, SpriteLoader.height, SpriteLoader.xOffsets[0], SpriteLoader.yOffsets[0], SpriteLoader.innerWidths[0], SpriteLoader.innerHeights[0], rgbaPixels);
		} else {
			@Pc(83) int[] rgbPixels = new int[pixelCount];
			for (@Pc(85) int i = 0; i < pixelCount; i++) {
				rgbPixels[i] = SpriteLoader.palette[indexedPixels[i] & 0xFF];
			}
			sprite = new SoftwareSprite(SpriteLoader.width, SpriteLoader.height, SpriteLoader.xOffsets[0], SpriteLoader.yOffsets[0], SpriteLoader.innerWidths[0], SpriteLoader.innerHeights[0], rgbPixels);
		}
		SpriteLoader.clear();
		return sprite;
	}

	@OriginalMember(owner = "client!mm", name = "d", descriptor = "(I)V")
	public final void pad(@OriginalArg(0) int padding) {
		if (this.width == this.innerWidth && this.height == this.innerHeight) {
			return;
		}
		@Pc(12) int leftPad = padding;
		if (padding > this.xOffset) {
			leftPad = this.xOffset;
		}
		@Pc(21) int rightPad = padding;
		if (padding + this.xOffset + this.width > this.innerWidth) {
			rightPad = this.innerWidth - this.xOffset - this.width;
		}
		@Pc(42) int topPad = padding;
		if (padding > this.yOffset) {
			topPad = this.yOffset;
		}
		@Pc(51) int bottomPad = padding;
		if (padding + this.yOffset + this.height > this.innerHeight) {
			bottomPad = this.innerHeight - this.yOffset - this.height;
		}
		@Pc(77) int newWidth = this.width + leftPad + rightPad;
		@Pc(84) int newHeight = this.height + topPad + bottomPad;
		@Pc(89) int[] newPixels = new int[newWidth * newHeight];
		for (@Pc(91) int y = 0; y < this.height; y++) {
			for (@Pc(97) int x = 0; x < this.width; x++) {
				newPixels[(y + topPad) * newWidth + x + leftPad] = this.pixels[y * this.width + x];
			}
		}
		this.pixels = newPixels;
		this.width = newWidth;
		this.height = newHeight;
		this.xOffset -= leftPad;
		this.yOffset -= topPad;
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "()V")
	public final void flipHorizontal() {
		@Pc(6) int[] flipped = new int[this.width * this.height];
		@Pc(8) int dstOff = 0;
		for (@Pc(10) int y = 0; y < this.height; y++) {
			for (@Pc(19) int x = this.width - 1; x >= 0; x--) {
				flipped[dstOff++] = this.pixels[x + y * this.width];
			}
		}
		this.pixels = flipped;
		this.xOffset = this.innerWidth - this.width - this.xOffset;
	}

	@OriginalMember(owner = "client!mm", name = "c", descriptor = "(II)V")
	@Override
	public void drawPixels(@OriginalArg(0) int x, @OriginalArg(1) int y) {
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
			blitAllPixels(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!mm", name = "b", descriptor = "()[I")
	public final int[] toFullImage() {
		@Pc(6) int[] image = new int[this.innerWidth * this.innerHeight];
		for (@Pc(8) int y = 0; y < this.height; y++) {
			@Pc(17) int srcOff = y * this.width;
			@Pc(28) int dstOff = this.xOffset + (y + this.yOffset) * this.innerWidth;
			for (@Pc(30) int x = 0; x < this.width; x++) {
				@Pc(40) int pixel = this.pixels[srcOff++];
				image[dstOff++] = pixel == 0 ? 0 : pixel | 0xFF000000;
			}
		}
		return image;
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(IIIIII)V")
	@Override
	protected void drawRotatedScaled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int pivotX, @OriginalArg(3) int pivotY, @OriginalArg(4) int angle, @OriginalArg(5) int zoom) {
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
		@Pc(468) int curV;
		@Pc(394) int col;
		@Pc(432) int pixel;
		@Pc(496) int skip;
		if (cosStep != 0) {
			@Pc(694) int curU;
			if (cosStep < 0) {
				if (sinStep == 0) {
					row = maxY;
					while (row < 0) {
						dst = dstOff;
						curU = u + (pixelOffX * cosStep >> 4);
						col = maxX;
						if (v >= 0 && v - (this.height << 12) < 0) {
							@Pc(717) int uEdge;
							if ((uEdge = curU - (this.width << 12)) >= 0) {
								skip = (cosStep - uEdge) / cosStep;
								col = maxX + skip;
								curU += cosStep * skip;
								dst = dstOff + skip;
							}
							@Pc(745) int uLimit;
							if ((uLimit = (curU - cosStep) / cosStep) > col) {
								col = uLimit;
							}
							while (col < 0) {
								pixel = this.pixels[(v >> 12) * this.width + (curU >> 12)];
								if (pixel == 0) {
									dst++;
								} else {
									SoftwareRaster.pixels[dst++] = pixel;
								}
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
						@Pc(825) int uEdge;
						if ((uEdge = curU - (this.width << 12)) >= 0) {
							skip = (cosStep - uEdge) / cosStep;
							col = maxX + skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst = dstOff + skip;
						}
						@Pc(859) int uLimit;
						if ((uLimit = (curU - cosStep) / cosStep) > col) {
							col = uLimit;
						}
						@Pc(871) int vEdge;
						if ((vEdge = curV - (this.height << 12)) >= 0) {
							skip = (sinStep - vEdge) / sinStep;
							col += skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst += skip;
						}
						@Pc(905) int vLimit;
						if ((vLimit = (curV - sinStep) / sinStep) > col) {
							col = vLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
							if (pixel == 0) {
								dst++;
							} else {
								SoftwareRaster.pixels[dst++] = pixel;
							}
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
						@Pc(991) int uEdge;
						if ((uEdge = curU - (this.width << 12)) >= 0) {
							skip = (cosStep - uEdge) / cosStep;
							col = maxX + skip;
							curU += cosStep * skip;
							curV += sinStep * skip;
							dst = dstOff + skip;
						}
						@Pc(1025) int uLimit;
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
						@Pc(1073) int vLimit;
						if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
							col = vLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
							if (pixel == 0) {
								dst++;
							} else {
								SoftwareRaster.pixels[dst++] = pixel;
							}
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
						@Pc(1196) int uLimit;
						if ((uLimit = (curU + 1 - (this.width << 12) - cosStep) / cosStep) > col) {
							col = uLimit;
						}
						while (col < 0) {
							pixel = this.pixels[(v >> 12) * this.width + (curU >> 12)];
							if (pixel == 0) {
								dst++;
							} else {
								SoftwareRaster.pixels[dst++] = pixel;
							}
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
					@Pc(1312) int uLimit;
					if ((uLimit = (curU + 1 - (this.width << 12) - cosStep) / cosStep) > col) {
						col = uLimit;
					}
					@Pc(1324) int vEdge;
					if ((vEdge = curV - (this.height << 12)) >= 0) {
						skip = (sinStep - vEdge) / sinStep;
						col += skip;
						curU += cosStep * skip;
						curV += sinStep * skip;
						dst += skip;
					}
					@Pc(1358) int vLimit;
					if ((vLimit = (curV - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
						if (pixel == 0) {
							dst++;
						} else {
							SoftwareRaster.pixels[dst++] = pixel;
						}
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
					@Pc(1480) int uLimit;
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
					@Pc(1528) int vLimit;
					if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (curU >> 12)];
						if (pixel == 0) {
							dst++;
						} else {
							SoftwareRaster.pixels[dst++] = pixel;
						}
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
						if (pixel == 0) {
							dst++;
						} else {
							SoftwareRaster.pixels[dst++] = pixel;
						}
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
					@Pc(489) int vEdge;
					if ((vEdge = curV - (this.height << 12)) >= 0) {
						skip = (sinStep - vEdge) / sinStep;
						col = maxX + skip;
						curV += sinStep * skip;
						dst = dstOff + skip;
					}
					@Pc(517) int vLimit;
					if ((vLimit = (curV - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (u >> 12)];
						if (pixel == 0) {
							dst++;
						} else {
							SoftwareRaster.pixels[dst++] = pixel;
						}
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
					@Pc(630) int vLimit;
					if ((vLimit = (curV + 1 - (this.height << 12) - sinStep) / sinStep) > col) {
						col = vLimit;
					}
					while (col < 0) {
						pixel = this.pixels[(curV >> 12) * this.width + (u >> 12)];
						if (pixel == 0) {
							dst++;
						} else {
							SoftwareRaster.pixels[dst++] = pixel;
						}
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

	@OriginalMember(owner = "client!mm", name = "e", descriptor = "(I)V")
	public final void drawOutline(@OriginalArg(0) int rgb) {
		@Pc(6) int[] dest = new int[this.width * this.height];
		@Pc(8) int destOff = 0;
		for (@Pc(10) int y = 0; y < this.height; y++) {
			for (@Pc(16) int x = 0; x < this.width; x++) {
				@Pc(25) int src = this.pixels[destOff];
				if (src == 0) {
					if (x > 0 && this.pixels[destOff - 1] != 0) {
						src = rgb;
					} else if (y > 0 && this.pixels[destOff - this.width] != 0) {
						src = rgb;
					} else if (x < this.width - 1 && this.pixels[destOff + 1] != 0) {
						src = rgb;
					} else if (y < this.height - 1 && this.pixels[destOff + this.width] != 0) {
						src = rgb;
					}
				}
				dest[destOff++] = src;
			}
		}
		this.pixels = dest;
	}

	@OriginalMember(owner = "client!mm", name = "c", descriptor = "()V")
	public final void makeTarget() {
		SoftwareRaster.setSize(this.pixels, this.width, this.height);
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(IIIIIIDI)V")
	public void drawRotatedFixed(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(6) double theta) {
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
					if (pixel == 0) {
						dst++;
					} else {
						SoftwareRaster.pixels[dst++] = pixel;
					}
					curU += cosStep;
					curV -= sinStep;
				}
				u += sinStep;
				v += cosStep;
				dstOff += SoftwareRaster.width;
			}
		} catch (@Pc(128) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!mm", name = "d", descriptor = "(II)V")
	@Override
	public void renderHorizontalFlip(@OriginalArg(0) int x, @OriginalArg(1) int y) {
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
			blitOpaqueReverse(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(III)V")
	@Override
	public void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha) {
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
			drawAlpha(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep, alpha);
		}
	}

	@OriginalMember(owner = "client!mm", name = "e", descriptor = "(II)V")
	@Override
	public void render(@OriginalArg(0) int x, @OriginalArg(1) int y) {
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
			blitOpaque(SoftwareRaster.pixels, this.pixels, srcOff, dstOff, w, h, dstStep, srcStep);
		}
	}

	@OriginalMember(owner = "client!mm", name = "d", descriptor = "()V")
	public final void flipVertical() {
		@Pc(6) int[] flipped = new int[this.width * this.height];
		@Pc(8) int dstOff = 0;
		for (@Pc(13) int y = this.height - 1; y >= 0; y--) {
			for (@Pc(17) int x = 0; x < this.width; x++) {
				flipped[dstOff++] = this.pixels[x + y * this.width];
			}
		}
		this.pixels = flipped;
		this.yOffset = this.innerHeight - this.height - this.yOffset;
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(IIIIIIII[I[I)V")
	public void renderRotated(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int anchorX, @OriginalArg(5) int anchorY, @OriginalArg(6) int theta, @OriginalArg(7) int zoom, @OriginalArg(8) int[] lineStarts, @OriginalArg(9) int[] lineWidths) {
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
					if (GlobalConfig.BILINEAR_MINIMAP) {
						int x1 = dstX >> 16;
						int y1 = dstY >> 16;
						int x2 = x1 + 1;
						int y2 = y1 + 1;
						int sampleColor1 = this.pixels[x1 + y1 * this.width];
						int sampleColor2 = this.pixels[x2 + y1 * this.width];
						int sampleColor3 = this.pixels[x1 + y2 * this.width];
						int sampleColor4 = this.pixels[x2 + y2 * this.width];
						int x1Distance = (dstX >> 8) - (x1 << 8);
						int y1Distance = (dstY >> 8) - (y1 << 8);
						int x2Distance = (x2 << 8) - (dstX >> 8);
						int y2Distance = (y2 << 8) - (dstY >> 8);
						int sampleAlpha1 = x2Distance * y2Distance;
						int sampleAlpha2 = x1Distance * y2Distance;
						int sampleAlpha3 = x2Distance * y1Distance;
						int sampleAlpha4 = x1Distance * y1Distance;
						int red = (sampleColor1 >> 16 & 0xff) * sampleAlpha1 + (sampleColor2 >> 16 & 0xff) * sampleAlpha2 + (sampleColor3 >> 16 & 0xff) * sampleAlpha3 + (sampleColor4 >> 16 & 0xff) * sampleAlpha4 & 0xff0000;
						int green = (sampleColor1 >> 8 & 0xff) * sampleAlpha1 + (sampleColor2 >> 8 & 0xff) * sampleAlpha2 + (sampleColor3 >> 8 & 0xff) * sampleAlpha3 + (sampleColor4 >> 8 & 0xff) * sampleAlpha4 >> 8 & 0xff00;
						int blue = (sampleColor1 & 0xff) * sampleAlpha1 + (sampleColor2 & 0xff) * sampleAlpha2 + (sampleColor3 & 0xff) * sampleAlpha3 + (sampleColor4 & 0xff) * sampleAlpha4 >> 16;
						SoftwareRaster.pixels[dstOff++] = red | green | blue;
					} else {
						SoftwareRaster.pixels[dstOff++] = this.pixels[(dstX >> 16) + (dstY >> 16) * this.width];
					}
					dstX += cos;
					dstY -= sin;
				}
				originX += sin;
				originY += cos;
				origin += SoftwareRaster.width;
			}
		} catch (@Pc(144) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!mm", name = "e", descriptor = "()V")
	public final void trim() {
		if (this.width == this.innerWidth && this.height == this.innerHeight) {
			return;
		}
		@Pc(17) int[] trimmed = new int[this.innerWidth * this.innerHeight];
		for (@Pc(19) int y = 0; y < this.height; y++) {
			for (@Pc(25) int x = 0; x < this.width; x++) {
				trimmed[(y + this.yOffset) * this.innerWidth + x + this.xOffset] = this.pixels[y * this.width + x];
			}
		}
		this.pixels = trimmed;
		this.width = this.innerWidth;
		this.height = this.innerHeight;
		this.xOffset = 0;
		this.yOffset = 0;
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(II[I[I)V")
	public final void drawClipped(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int[] clipStarts, @OriginalArg(3) int[] clipWidths) {
		if (SoftwareRaster.clipBottom - SoftwareRaster.clipTop != clipStarts.length) {
			throw new IllegalStateException();
		}
		x += this.xOffset;
		y += this.yOffset;
		@Pc(21) int srcOff = 0;
		@Pc(24) int h = this.height;
		@Pc(27) int w = this.width;
		@Pc(31) int dstStep = SoftwareRaster.width - w;
		@Pc(33) int srcStep = 0;
		@Pc(39) int dstOff = x + y * SoftwareRaster.width;
		@Pc(46) int clip;
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
		if (w <= 0 || h <= 0) {
			return;
		}
		clip = x - SoftwareRaster.clipLeft;
		@Pc(142) int startRow = y - SoftwareRaster.clipTop;
		for (@Pc(144) int row = startRow; row < startRow + h; row++) {
			@Pc(153) int start = clipStarts[row];
			@Pc(157) int visibleWidth = clipWidths[row];
			@Pc(159) int remaining = w;
			@Pc(166) int offset;
			if (clip > start) {
				offset = clip - start;
				if (offset >= visibleWidth) {
					srcOff += w + srcStep;
					dstOff += w + dstStep;
					continue;
				}
				visibleWidth -= offset;
			} else {
				offset = start - clip;
				if (offset >= w) {
					srcOff += w + srcStep;
					dstOff += w + dstStep;
					continue;
				}
				srcOff += offset;
				remaining = w - offset;
				dstOff += offset;
			}
			offset = 0;
			if (remaining < visibleWidth) {
				visibleWidth = remaining;
			} else {
				offset = remaining - visibleWidth;
			}
			for (@Pc(234) int i = -visibleWidth; i < 0; i++) {
				@Pc(242) int pixel = this.pixels[srcOff++];
				if (pixel == 0) {
					dstOff++;
				} else {
					SoftwareRaster.pixels[dstOff++] = pixel;
				}
			}
			srcOff += offset + srcStep;
			dstOff += offset + dstStep;
		}
	}

	@OriginalMember(owner = "client!mm", name = "b", descriptor = "(IIIIIIII[I[I)V")
	public void renderRotated(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int anchorX, @OriginalArg(5) int anchorY, @OriginalArg(6) int theta, @OriginalArg(8) int[] lineStart, @OriginalArg(9) int[] lineWidth) {
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
					@Pc(115) int color = this.pixels[(dstX >> 16) + (dstY >> 16) * this.width];
					if (color == 0) {
						dstOff++;
					} else {
						if (GlobalConfig.BILINEAR_MINIMAP) {
							int x1 = dstX >> 16;
							int y1 = dstY >> 16;
							int x2 = x1 + 1;
							int y2 = y1 + 1;
							int sampleColor1 = this.pixels[x1 + y1 * this.width];
							int sampleColor2 = this.pixels[x2 + y1 * this.width];
							int sampleColor3 = this.pixels[x1 + y2 * this.width];
							int sampleColor4 = this.pixels[x2 + y2 * this.width];
							int x1Distance = (dstX >> 8) - (x1 << 8);
							int y1Distance = (dstY >> 8) - (y1 << 8);
							int x2Distance = (x2 << 8) - (dstX >> 8);
							int y2Distance = (y2 << 8) - (dstY >> 8);
							int sampleAlpha1 = x2Distance * y2Distance;
							int sampleAlpha2 = x1Distance * y2Distance;
							int sampleAlpha3 = x2Distance * y1Distance;
							int sampleAlpha4 = x1Distance * y1Distance;
							int red = (sampleColor1 >> 16 & 0xff) * sampleAlpha1 + (sampleColor2 >> 16 & 0xff) * sampleAlpha2 + (sampleColor3 >> 16 & 0xff) * sampleAlpha3 + (sampleColor4 >> 16 & 0xff) * sampleAlpha4 & 0xff0000;
							int green = (sampleColor1 >> 8 & 0xff) * sampleAlpha1 + (sampleColor2 >> 8 & 0xff) * sampleAlpha2 + (sampleColor3 >> 8 & 0xff) * sampleAlpha3 + (sampleColor4 >> 8 & 0xff) * sampleAlpha4 >> 8 & 0xff00;
							int blue = (sampleColor1 & 0xff) * sampleAlpha1 + (sampleColor2 & 0xff) * sampleAlpha2 + (sampleColor3 & 0xff) * sampleAlpha3 + (sampleColor4 & 0xff) * sampleAlpha4 >> 16;
							SoftwareRaster.pixels[dstOff++] = red | green | blue;
						} else {
							SoftwareRaster.pixels[dstOff++] = color;
						}
					}
					dstX += cos;
					dstY -= sin;
				}
				originX += sin;
				originY += cos;
				origin += SoftwareRaster.width;
			}
		} catch (@Pc(150) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!mm", name = "f", descriptor = "(I)V")
	public final void drawShadow(@OriginalArg(0) int rgb) {
		for (@Pc(4) int y = this.height - 1; y > 0; y--) {
			@Pc(11) int row = y * this.width;
			for (@Pc(16) int x = this.width - 1; x > 0; x--) {
				if (this.pixels[x + row] == 0 && this.pixels[x + row - this.width - 1] != 0) {
					this.pixels[x + row] = rgb;
				}
			}
		}
	}

	@OriginalMember(owner = "client!mm", name = "b", descriptor = "(IIIII)V")
	@Override
	public void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int scaledW, @OriginalArg(3) int scaledH, @OriginalArg(4) int alpha) {
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
		blitResizedAlpha(SoftwareRaster.pixels, this.pixels, srcX, srcY, dstOff, dstStep, scaledW, scaledH, xStep, yStep, srcW, alpha);
	}

	@OriginalMember(owner = "client!mm", name = "b", descriptor = "(III)V")
	public final void adjustRgb(@OriginalArg(0) int rAdj, @OriginalArg(1) int gAdj, @OriginalArg(2) int bAdj) {
		for (@Pc(1) int i = 0; i < this.pixels.length; i++) {
			@Pc(11) int pixel = this.pixels[i];
			if (pixel != 0) {
				@Pc(19) int r = pixel >> 16 & 0xFF;
				r += rAdj;
				if (r < 1) {
					r = 1;
				} else if (r > 255) {
					r = 255;
				}
				@Pc(40) int g = pixel >> 8 & 0xFF;
				g += gAdj;
				if (g < 1) {
					g = 1;
				} else if (g > 255) {
					g = 255;
				}
				@Pc(59) int b = pixel & 0xFF;
				b += bAdj;
				if (b < 1) {
					b = 1;
				} else if (b > 255) {
					b = 255;
				}
				this.pixels[i] = (r << 16) + (g << 8) + b;
			}
		}
	}

	@OriginalMember(owner = "client!mm", name = "a", descriptor = "(IIII)V")
	@Override
	public void renderResized(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
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

		drawResized(SoftwareRaster.pixels, this.pixels, srcX, srcY, dstOff, dstStep, width, height, xStep, yStep, srcW);
	}
}
