package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!dd")
public final class SoftwareFont extends Font {

	@OriginalMember(owner = "client!dd", name = "Eb", descriptor = "[[B")
	private byte[][] pixels = new byte[256][];

	@OriginalMember(owner = "client!dd", name = "<init>", descriptor = "([B)V")
	public SoftwareFont(@OriginalArg(0) byte[] data) {
		super(data);
	}

	@OriginalMember(owner = "client!dd", name = "<init>", descriptor = "([B[I[I[I[I[[B)V")
	public SoftwareFont(@OriginalArg(0) byte[] data, @OriginalArg(1) int[] xOffsets, @OriginalArg(2) int[] yOffsets, @OriginalArg(3) int[] innerWidths, @OriginalArg(4) int[] innerHeights, @OriginalArg(5) byte[][] pixels) {
		super(data, xOffsets, yOffsets, innerWidths, innerHeights);
		this.pixels = pixels;
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "([I[BIIIIIII)V")
	public static void blit(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] glyphPixels, @OriginalArg(2) int color, @OriginalArg(3) int srcOff, @OriginalArg(4) int destOff, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int destStep, @OriginalArg(8) int srcStep) {
		@Pc(4) int widthQuads = -(width >> 2);
		@Pc(9) int widthRemainder = -(width & 0x3);
		for (@Pc(12) int y = -height; y < 0; y++) {
			@Pc(16) int q;
			for (q = widthQuads; q < 0; q++) {
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					dest[destOff++] = color;
				}
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					dest[destOff++] = color;
				}
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					dest[destOff++] = color;
				}
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					dest[destOff++] = color;
				}
			}
			for (q = widthRemainder; q < 0; q++) {
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					dest[destOff++] = color;
				}
			}
			destOff += destStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "([I[BIIIIIIIII[I[I)V")
	public static void blitMasked(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] glyphPixels, @OriginalArg(2) int glyphX, @OriginalArg(3) int glyphY, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int color, @OriginalArg(7) int srcOff, @OriginalArg(8) int destOff, @OriginalArg(9) int destStep, @OriginalArg(10) int srcStep, @OriginalArg(11) int[] maskStarts, @OriginalArg(12) int[] maskWidths) {
		@Pc(3) int relX = glyphX - SoftwareRaster.clipLeft;
		@Pc(7) int relY = glyphY - SoftwareRaster.clipTop;
		for (@Pc(9) int row = relY; row < relY + height; row++) {
			@Pc(18) int maskStart = maskStarts[row];
			@Pc(22) int maskWidth = maskWidths[row];
			@Pc(24) int rowWidth = width;
			@Pc(31) int skip;
			if (relX > maskStart) {
				skip = relX - maskStart;
				if (skip >= maskWidth) {
					srcOff += width + srcStep;
					destOff += width + destStep;
					continue;
				}
				maskWidth -= skip;
			} else {
				skip = maskStart - relX;
				if (skip >= width) {
					srcOff += width + srcStep;
					destOff += width + destStep;
					continue;
				}
				srcOff += skip;
				rowWidth = width - skip;
				destOff += skip;
			}
			skip = 0;
			if (rowWidth < maskWidth) {
				maskWidth = rowWidth;
			} else {
				skip = rowWidth - maskWidth;
			}
			for (@Pc(99) int col = -maskWidth; col < 0; col++) {
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					SoftwareRaster.pixels[destOff++] = color;
				}
			}
			srcOff += skip + srcStep;
			destOff += skip + destStep;
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "([I[BIIIIIIII)V")
	public static void blitTransparent(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] glyphPixels, @OriginalArg(2) int color, @OriginalArg(3) int srcOff, @OriginalArg(4) int destOff, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int destStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int alpha) {
		@Pc(17) int srcColor = ((color & 0xFF00FF) * alpha & 0xFF00FF00) + ((color & 0xFF00) * alpha & 0xFF0000) >> 8;
		@Pc(21) int invAlpha = 256 - alpha;
		for (@Pc(24) int y = -height; y < 0; y++) {
			for (@Pc(29) int x = -width; x < 0; x++) {
				if (glyphPixels[srcOff++] == 0) {
					destOff++;
				} else {
					@Pc(40) int destColor = dest[destOff];
					dest[destOff++] = (((destColor & 0xFF00FF) * invAlpha & 0xFF00FF00) + ((destColor & 0xFF00) * invAlpha & 0xFF0000) >> 8) + srcColor;
				}
			}
			destOff += destStep;
			srcOff += srcStep;
		}
	}

	@OriginalMember(owner = "client!jh", name = "a", descriptor = "(IILclient!ve;Lclient!ve;I)Lclient!dd;")
	public static SoftwareFont load(@OriginalArg(0) int fileId, @OriginalArg(2) Js5 fontJs5, @OriginalArg(3) Js5 spriteJs5) {
		return SpriteLoader.decode(spriteJs5, 0, fileId) ? createFont(fontJs5.fetchFile(fileId, 0)) : null;
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "([BI)Lclient!dd;")
	public static SoftwareFont createFont(@OriginalArg(0) byte[] data) {
		if (data == null) {
			return null;
		} else {
			@Pc(22) SoftwareFont font = new SoftwareFont(data, SpriteLoader.xOffsets, SpriteLoader.yOffsets, SpriteLoader.innerWidths, SpriteLoader.innerHeights, SpriteLoader.pixels);
			SpriteLoader.clear();
			return font;
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IIIIIIIZ)V")
	@Override
	protected final void renderGlyphTransparent(@OriginalArg(0) int glyphId, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int width, @OriginalArg(4) int height, @OriginalArg(5) int color, @OriginalArg(6) int alpha) {
		@Pc(5) int destOff = x + y * SoftwareRaster.width;
		@Pc(9) int destStep = SoftwareRaster.width - width;
		@Pc(11) int srcStep = 0;
		@Pc(13) int srcOff = 0;
		@Pc(20) int clip;
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
			srcStep = clip;
			destStep += clip;
		}
		if (x + width > SoftwareRaster.clipRight) {
			clip = x + width - SoftwareRaster.clipRight;
			width -= clip;
			srcStep += clip;
			destStep += clip;
		}
		if (width > 0 && height > 0) {
			blitTransparent(SoftwareRaster.pixels, this.pixels[glyphId], color, srcOff, destOff, width, height, destStep, srcStep, alpha);
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IIIIIIZ)V")
	@Override
	protected final void renderGlyph(@OriginalArg(0) int glyphId, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int width, @OriginalArg(4) int height, @OriginalArg(5) int color) {
		@Pc(5) int destOff = x + y * SoftwareRaster.width;
		@Pc(9) int destStep = SoftwareRaster.width - width;
		@Pc(11) int srcStep = 0;
		@Pc(13) int srcOff = 0;
		@Pc(20) int clip;
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
			srcStep = clip;
			destStep += clip;
		}
		if (x + width > SoftwareRaster.clipRight) {
			clip = x + width - SoftwareRaster.clipRight;
			width -= clip;
			srcStep += clip;
			destStep += clip;
		}
		if (width <= 0 || height <= 0) {
			return;
		}
		if (SoftwareRaster.lineMaskStarts == null) {
			blit(SoftwareRaster.pixels, this.pixels[glyphId], color, srcOff, destOff, width, height, destStep, srcStep);
		} else {
			blitMasked(SoftwareRaster.pixels, this.pixels[glyphId], x, y, width, height, color, srcOff, destOff, destStep, srcStep, SoftwareRaster.lineMaskStarts, SoftwareRaster.lineMaskWidths);
		}
	}
}
