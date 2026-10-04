package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ek")
public final class SoftwareIndexedSprite extends IndexedSprite {

	@OriginalMember(owner = "client!ek", name = "o", descriptor = "[B")
	public byte[] pixels;

	@OriginalMember(owner = "client!ek", name = "n", descriptor = "[I")
	public final int[] pallet;

	@OriginalMember(owner = "client!ek", name = "<init>", descriptor = "(IIIIII[B[I)V")
	public SoftwareIndexedSprite(@OriginalArg(0) int innerWidth, @OriginalArg(1) int innerHeight, @OriginalArg(2) int xOffset, @OriginalArg(3) int yOffset, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) byte[] pixels, @OriginalArg(7) int[] pallet) {
		this.innerWidth = innerWidth;
		this.innerHeight = innerHeight;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.pallet = pallet;
	}

	@OriginalMember(owner = "client!ek", name = "<init>", descriptor = "(III)V")
	public SoftwareIndexedSprite(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(2) int palletSize) {
		this.innerWidth = this.width = width;
		this.innerHeight = this.height = height;
		this.xOffset = this.yOffset = 0;
		this.pixels = new byte[width * height];
		this.pallet = new int[palletSize];
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "([I[B[IIIIIIIIII)V")
	public static void blitScaledIndexed(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int[] pallet, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int destOff, @OriginalArg(6) int destRowPad, @OriginalArg(7) int scaledWidth, @OriginalArg(8) int scaledHeight, @OriginalArg(9) int srcXStep, @OriginalArg(10) int srcYStep, @OriginalArg(11) int srcWidth) {
		@Pc(1) int srcXReset = srcX;
		for (@Pc(4) int row = -scaledHeight; row < 0; row++) {
			@Pc(12) int srcRowOff = (srcY >> 16) * srcWidth;
			for (@Pc(15) int col = -scaledWidth; col < 0; col++) {
				@Pc(25) byte pixel = src[(srcX >> 16) + srcRowOff];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
				srcX += srcXStep;
			}
			srcY += srcYStep;
			srcX = srcXReset;
			destOff += destRowPad;
		}
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "([I[B[IIIIIIII)V")
	public static void blitIndexed(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int[] pallet, @OriginalArg(4) int srcOff, @OriginalArg(5) int destOff, @OriginalArg(6) int width, @OriginalArg(7) int height, @OriginalArg(8) int destRowPad, @OriginalArg(9) int srcRowPad) {
		@Pc(4) int quads = -(width >> 2);
		@Pc(9) int remainder = -(width & 0x3);
		for (@Pc(12) int row = -height; row < 0; row++) {
			@Pc(16) int q;
			@Pc(23) byte pixel;
			for (q = quads; q < 0; q++) {
				pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
				pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
			}
			for (q = remainder; q < 0; q++) {
				pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					dest[destOff++] = pallet[pixel & 0xFF];
				}
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "([I[B[IIIIIIIIIII)V")
	public static void blitScaledIndexedTinted(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int[] pallet, @OriginalArg(3) int srcX, @OriginalArg(4) int srcY, @OriginalArg(5) int destOff, @OriginalArg(6) int destRowPad, @OriginalArg(7) int scaledWidth, @OriginalArg(8) int scaledHeight, @OriginalArg(9) int srcXStep, @OriginalArg(10) int srcYStep, @OriginalArg(11) int srcWidth, @OriginalArg(12) int tint) {
		@Pc(1) int srcXReset = srcX;
		@Pc(7) int tintR = tint >> 16 & 0xFF;
		@Pc(13) int tintG = tint >> 8 & 0xFF;
		@Pc(17) int tintB = tint & 0xFF;
		for (@Pc(20) int row = -scaledHeight; row < 0; row++) {
			@Pc(28) int srcRowOff = (srcY >> 16) * srcWidth;
			for (@Pc(31) int col = -scaledWidth; col < 0; col++) {
				@Pc(41) byte pixel = src[(srcX >> 16) + srcRowOff];
				if (pixel == 0) {
					destOff++;
				} else {
					@Pc(49) int color = pallet[pixel & 0xFF];
					@Pc(55) int r = color >> 16 & 0xFF;
					@Pc(61) int g = color >> 8 & 0xFF;
					@Pc(65) int b = color & 0xFF;
					dest[destOff++] = (r * tintR >> 8 << 16) + (g * tintG >> 8 << 8) + (b * tintB >> 8);
				}
				srcX += srcXStep;
			}
			srcY += srcYStep;
			srcX = srcXReset;
			destOff += destRowPad;
		}
	}

	@OriginalMember(owner = "client!ek", name = "b", descriptor = "([I[B[IIIIIIII)V")
	public static void blitAlphaBlended(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int[] pallet, @OriginalArg(3) int srcOff, @OriginalArg(4) int destOff, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int destRowPad, @OriginalArg(8) int srcRowPad, @OriginalArg(9) int alpha) {
		@Pc(3) int invAlpha = 256 - alpha;
		for (@Pc(6) int row = -height; row < 0; row++) {
			for (@Pc(11) int col = -width; col < 0; col++) {
				@Pc(18) byte pixel = src[srcOff++];
				if (pixel == 0) {
					destOff++;
				} else {
					@Pc(26) int srcColor = pallet[pixel & 0xFF];
					@Pc(30) int destColor = dest[destOff];
					dest[destOff++] = ((srcColor & 0xFF00FF) * alpha + (destColor & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((srcColor & 0xFF00) * alpha + (destColor & 0xFF00) * invAlpha & 0xFF0000) >> 8;
				}
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!ek", name = "b", descriptor = "(III)V")
	public final void adjustPalette(@OriginalArg(0) int redAdj, @OriginalArg(1) int greenAdj, @OriginalArg(2) int blueAdj) {
		for (@Pc(1) int i = 0; i < this.pallet.length; i++) {
			@Pc(15) int r = this.pallet[i] >> 16 & 0xFF;
			r += redAdj;
			if (r < 0) {
				r = 0;
			} else if (r > 255) {
				r = 255;
			}
			@Pc(38) int g = this.pallet[i] >> 8 & 0xFF;
			g += greenAdj;
			if (g < 0) {
				g = 0;
			} else if (g > 255) {
				g = 255;
			}
			@Pc(59) int b = this.pallet[i] & 0xFF;
			b += blueAdj;
			if (b < 0) {
				b = 0;
			} else if (b > 255) {
				b = 255;
			}
			this.pallet[i] = (r << 16) + (g << 8) + b;
		}
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "(IIIII)V")
	public final void renderScaledTinted(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int scaledWidth, @OriginalArg(3) int scaledHeight, @OriginalArg(4) int tint) {
		@Pc(2) int spriteWidth = this.width;
		@Pc(5) int spriteHeight = this.height;
		@Pc(7) int srcX = 0;
		@Pc(9) int srcY = 0;
		@Pc(12) int innerW = this.innerWidth;
		@Pc(15) int innerH = this.innerHeight;
		@Pc(21) int srcXStep = (innerW << 16) / scaledWidth;
		@Pc(27) int srcYStep = (innerH << 16) / scaledHeight;
		@Pc(41) int clip;
		if (this.xOffset > 0) {
			clip = ((this.xOffset << 16) + srcXStep - 1) / srcXStep;
			x += clip;
			srcX = clip * srcXStep - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			clip = ((this.yOffset << 16) + srcYStep - 1) / srcYStep;
			y += clip;
			srcY = clip * srcYStep - (this.yOffset << 16);
		}
		if (spriteWidth < innerW) {
			scaledWidth = ((spriteWidth << 16) + srcXStep - srcX - 1) / srcXStep;
		}
		if (spriteHeight < innerH) {
			scaledHeight = ((spriteHeight << 16) + srcYStep - srcY - 1) / srcYStep;
		}
		clip = x + y * SoftwareRaster.width;
		@Pc(125) int destRowPad = SoftwareRaster.width - scaledWidth;
		if (y + scaledHeight > SoftwareRaster.clipBottom) {
			scaledHeight -= y + scaledHeight - SoftwareRaster.clipBottom;
		}
		@Pc(145) int clipped;
		if (y < SoftwareRaster.clipTop) {
			clipped = SoftwareRaster.clipTop - y;
			scaledHeight -= clipped;
			clip += clipped * SoftwareRaster.width;
			srcY += srcYStep * clipped;
		}
		if (x + scaledWidth > SoftwareRaster.clipRight) {
			clipped = x + scaledWidth - SoftwareRaster.clipRight;
			scaledWidth -= clipped;
			destRowPad += clipped;
		}
		if (x < SoftwareRaster.clipLeft) {
			clipped = SoftwareRaster.clipLeft - x;
			scaledWidth -= clipped;
			clip += clipped;
			srcX += srcXStep * clipped;
			destRowPad += clipped;
		}
		blitScaledIndexedTinted(SoftwareRaster.pixels, this.pixels, this.pallet, srcX, srcY, clip, destRowPad, scaledWidth, scaledHeight, srcXStep, srcYStep, spriteWidth, tint);
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "()V")
	public final void clear() {
		@Pc(1) int i = 0;
		@Pc(7) int limit = this.pixels.length - 7;
		while (i < limit) {
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
			this.pixels[i++] = 0;
		}
		limit += 7;
		while (i < limit) {
			this.pixels[i++] = 0;
		}
	}

	@OriginalMember(owner = "client!ek", name = "b", descriptor = "()V")
	public final void flipVertical() {
		@Pc(6) byte[] rotated = new byte[this.width * this.height];
		@Pc(8) int destOff = 0;
		@Pc(10) int col;
		for (col = 0; col < this.width; col++) {
			for (@Pc(19) int row = this.height - 1; row >= 0; row--) {
				rotated[destOff++] = this.pixels[col + row * this.width];
			}
		}
		this.pixels = rotated;
		col = this.yOffset;
		this.yOffset = this.xOffset;
		this.xOffset = this.innerHeight - this.height - col;
		col = this.height;
		this.height = this.width;
		this.width = col;
		col = this.innerHeight;
		this.innerHeight = this.innerWidth;
		this.innerWidth = col;
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "(III)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha) {
		x += this.xOffset;
		y += this.yOffset;
		@Pc(15) int destOff = x + y * SoftwareRaster.width;
		@Pc(17) int srcOff = 0;
		@Pc(20) int height = this.height;
		@Pc(23) int width = this.width;
		@Pc(27) int destRowPad = SoftwareRaster.width - width;
		@Pc(29) int srcRowPad = 0;
		@Pc(36) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			height -= clip;
			y = SoftwareRaster.clipTop;
			srcOff = clip * width;
			destOff += clip * SoftwareRaster.width;
		}
		if (y + height > SoftwareRaster.clipBottom) {
			height -= y + height - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			width -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff += clip;
			destOff += clip;
			srcRowPad = clip;
			destRowPad += clip;
		}
		if (x + width > SoftwareRaster.clipRight) {
			clip = x + width - SoftwareRaster.clipRight;
			width -= clip;
			srcRowPad += clip;
			destRowPad += clip;
		}
		if (width > 0 && height > 0) {
			blitAlphaBlended(SoftwareRaster.pixels, this.pixels, this.pallet, srcOff, destOff, width, height, destRowPad, srcRowPad, alpha);
		}
	}

	@OriginalMember(owner = "client!ek", name = "c", descriptor = "()V")
	public final void trim() {
		if (this.width == this.innerWidth && this.height == this.innerHeight) {
			return;
		}
		@Pc(17) byte[] trimmed = new byte[this.innerWidth * this.innerHeight];
		@Pc(19) int srcOff = 0;
		for (@Pc(21) int row = 0; row < this.height; row++) {
			for (@Pc(27) int col = 0; col < this.width; col++) {
				trimmed[col + this.xOffset + (row + this.yOffset) * this.innerWidth] = this.pixels[srcOff++];
			}
		}
		this.pixels = trimmed;
		this.width = this.innerWidth;
		this.height = this.innerHeight;
		this.xOffset = 0;
		this.yOffset = 0;
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "(IIII)V")
	public final void renderScaled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int scaledWidth, @OriginalArg(3) int scaledHeight) {
		@Pc(2) int spriteWidth = this.width;
		@Pc(5) int spriteHeight = this.height;
		@Pc(7) int srcX = 0;
		@Pc(9) int srcY = 0;
		@Pc(12) int innerW = this.innerWidth;
		@Pc(15) int innerH = this.innerHeight;
		@Pc(21) int srcXStep = (innerW << 16) / scaledWidth;
		@Pc(27) int srcYStep = (innerH << 16) / scaledHeight;
		@Pc(41) int clip;
		if (this.xOffset > 0) {
			clip = ((this.xOffset << 16) + srcXStep - 1) / srcXStep;
			x += clip;
			srcX = clip * srcXStep - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			clip = ((this.yOffset << 16) + srcYStep - 1) / srcYStep;
			y += clip;
			srcY = clip * srcYStep - (this.yOffset << 16);
		}
		if (spriteWidth < innerW) {
			scaledWidth = ((spriteWidth << 16) + srcXStep - srcX - 1) / srcXStep;
		}
		if (spriteHeight < innerH) {
			scaledHeight = ((spriteHeight << 16) + srcYStep - srcY - 1) / srcYStep;
		}
		clip = x + y * SoftwareRaster.width;
		@Pc(125) int destRowPad = SoftwareRaster.width - scaledWidth;
		if (y + scaledHeight > SoftwareRaster.clipBottom) {
			scaledHeight -= y + scaledHeight - SoftwareRaster.clipBottom;
		}
		@Pc(145) int clipped;
		if (y < SoftwareRaster.clipTop) {
			clipped = SoftwareRaster.clipTop - y;
			scaledHeight -= clipped;
			clip += clipped * SoftwareRaster.width;
			srcY += srcYStep * clipped;
		}
		if (x + scaledWidth > SoftwareRaster.clipRight) {
			clipped = x + scaledWidth - SoftwareRaster.clipRight;
			scaledWidth -= clipped;
			destRowPad += clipped;
		}
		if (x < SoftwareRaster.clipLeft) {
			clipped = SoftwareRaster.clipLeft - x;
			scaledWidth -= clipped;
			clip += clipped;
			srcX += srcXStep * clipped;
			destRowPad += clipped;
		}
		blitScaledIndexed(SoftwareRaster.pixels, this.pixels, this.pallet, srcX, srcY, clip, destRowPad, scaledWidth, scaledHeight, srcXStep, srcYStep, spriteWidth);
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "(II)V")
	@Override
	public final void renderTransparent(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		x += this.xOffset;
		y += this.yOffset;
		@Pc(15) int destOff = x + y * SoftwareRaster.width;
		@Pc(17) int srcOff = 0;
		@Pc(20) int height = this.height;
		@Pc(23) int width = this.width;
		@Pc(27) int destRowPad = SoftwareRaster.width - width;
		@Pc(29) int srcRowPad = 0;
		@Pc(36) int clip;
		if (y < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - y;
			height -= clip;
			y = SoftwareRaster.clipTop;
			srcOff = clip * width;
			destOff += clip * SoftwareRaster.width;
		}
		if (y + height > SoftwareRaster.clipBottom) {
			height -= y + height - SoftwareRaster.clipBottom;
		}
		if (x < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - x;
			width -= clip;
			x = SoftwareRaster.clipLeft;
			srcOff += clip;
			destOff += clip;
			srcRowPad = clip;
			destRowPad += clip;
		}
		if (x + width > SoftwareRaster.clipRight) {
			clip = x + width - SoftwareRaster.clipRight;
			width -= clip;
			srcRowPad += clip;
			destRowPad += clip;
		}
		if (width > 0 && height > 0) {
			blitIndexed(SoftwareRaster.pixels, this.pixels, this.pallet, srcOff, destOff, width, height, destRowPad, srcRowPad);
		}
	}
}
