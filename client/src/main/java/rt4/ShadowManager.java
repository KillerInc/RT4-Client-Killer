package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class ShadowManager {

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "Lclient!ek;")
	public static SoftwareIndexedSprite shadowMapImage;

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "[[Lclient!wm;")
	public static Shadow[][] shadows;

	@OriginalMember(owner = "client!tj", name = "c", descriptor = "I")
	private static int gridRows;

	@OriginalMember(owner = "client!tj", name = "e", descriptor = "I")
	private static int gridColumns;

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(Lclient!ek;Lclient!ek;II)V")
	private static void addShadowSprite(@OriginalArg(0) SoftwareIndexedSprite src, @OriginalArg(1) SoftwareIndexedSprite dest, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		x += src.xOffset;
		y += src.yOffset;
		@Pc(16) int destOff = x + y * dest.width;
		@Pc(18) int srcOff = 0;
		@Pc(21) int height = src.height;
		@Pc(24) int width = src.width;
		@Pc(29) int destRowPad = dest.width - width;
		@Pc(31) int srcRowPad = 0;
		@Pc(37) int clip;
		if (y <= 0) {
			clip = 1 - y;
			height -= clip;
			srcOff = clip * width;
			destOff += clip * dest.width;
			y = 1;
		}
		if (y + height >= dest.height) {
			clip = y + height + 1 - dest.height;
			height -= clip;
		}
		if (x <= 0) {
			clip = 1 - x;
			width -= clip;
			srcOff += clip;
			destOff += clip;
			srcRowPad = clip;
			destRowPad += clip;
			x = 1;
		}
		if (x + width >= dest.width) {
			clip = x + width + 1 - dest.width;
			width -= clip;
			srcRowPad += clip;
			destRowPad += clip;
		}
		if (width > 0 && height > 0) {
			addPixels(dest.pixels, src.pixels, srcOff, destOff, width, height, destRowPad, srcRowPad);
			markDirtyRegion(x, y, width, height);
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "([B[BIIIIII)V")
	private static void subtractPixels(@OriginalArg(0) byte[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int srcOff, @OriginalArg(3) int destOff, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int destRowPad, @OriginalArg(7) int srcRowPad) {
		@Pc(4) int quads = -(width >> 2);
		@Pc(9) int remainder = -(width & 0x3);
		for (@Pc(12) int row = -height; row < 0; row++) {
			@Pc(16) int q;
			@Pc(20) int idx;
			for (q = quads; q < 0; q++) {
				idx = destOff++;
				dest[idx] -= src[srcOff++];
				@Pc(32) int idx2 = destOff++;
				dest[idx2] -= src[srcOff++];
				@Pc(44) int idx3 = destOff++;
				dest[idx3] -= src[srcOff++];
				@Pc(56) int idx4 = destOff++;
				dest[idx4] -= src[srcOff++];
			}
			for (q = remainder; q < 0; q++) {
				idx = destOff++;
				dest[idx] -= src[srcOff++];
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(IIII)V")
	private static void markDirtyRegion(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		@Pc(5) int startCol = x - 1 >> 7;
		@Pc(15) int endCol = x + width - 1 - 1 >> 7;
		@Pc(21) int startRow = y - 1 >> 7;
		@Pc(31) int endRow = y + height - 1 - 1 >> 7;
		for (@Pc(33) int col = startCol; col <= endCol; col++) {
			for (@Pc(38) int row = startRow; row <= endRow; row++) {
				shadows[col][row].outputToSprite = true;
			}
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(IIZZIIIIII)V")
	public static void addFloorShadow(@OriginalArg(0) int shape, @OriginalArg(1) int orientation, @OriginalArg(2) boolean flag1, @OriginalArg(3) boolean flag2, @OriginalArg(4) int tileX, @OriginalArg(5) int tileY, @OriginalArg(6) int heightSW, @OriginalArg(7) int heightSE, @OriginalArg(8) int heightNE, @OriginalArg(9) int heightNW) {
		if (flag1 && flag2 || (flag1 && shape == 1 || flag2 && shape == 0)) {
			return;
		}
		@Pc(19) int fineX = tileX << 7;
		@Pc(29) int avgHeight = heightSW + heightSE + heightNE + heightNW >> 2;
		@Pc(33) int fineY = tileY << 7;
		@Pc(43) int shadowX = fineX - (avgHeight * FogManager.lightX >> 8) >> 3;
		@Pc(53) int shadowY = fineY - (avgHeight * FogManager.lightZ >> 8) >> 3;
		if (shape != 0 && shape != 1 && (flag1 || flag2)) {
			addRotatedFloorShadow(Sprites.floorShadows[shape], shadowMapImage, shadowX + 1, shadowY + 1, orientation, flag1);
		} else {
			addShadowSprite(Sprites.floorShadows[1], shadowMapImage, shadowX + 1, shadowY + 1);
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(IIII[[Z[[I)V")
	public static void renderShadowMapGL(@OriginalArg(0) int centerX, @OriginalArg(1) int centerY, @OriginalArg(2) int radius, @OriginalArg(3) int unused, @OriginalArg(4) boolean[][] visibility, @OriginalArg(5) int[][] heightmap) {
		@Pc(1) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureCombineRgbMode(1);
		GlRenderer.setTextureCombineAlphaMode(1);
		GlRenderer.resetTextureMatrix();
		GlRenderer.setLightingEnabled(false);
		MaterialManager.setMaterial(0, 0);
		gl.glDepthMask(false);
		for (@Pc(17) int col = 0; col < gridColumns; col++) {
			nextRow:
			for (@Pc(22) int row = 0; row < gridRows; row++) {
				for (@Pc(29) int tileX = col * 8; tileX < col * 8 + 8; tileX++) {
					if (tileX - centerX >= -radius && tileX - centerX <= radius) {
						for (@Pc(51) int tileY = row * 8; tileY < row * 8 + 8; tileY++) {
							if (tileY - centerY >= -radius && tileY - centerY <= radius && visibility[tileX + radius - centerX][tileY + radius - centerY]) {
								@Pc(89) Shadow shadow = shadows[col][row];
								if (shadow.outputToSprite) {
									shadow.updateShadowTexture(shadowMapImage, col, row);
									shadow.outputToSprite = false;
								}
								gl.glPushMatrix();
								gl.glTranslatef((float) (col * 1024), 0.0F, (float) (row * 1024));
								shadow.renderShadow();
								gl.glPopMatrix();
								continue nextRow;
							}
						}
					}
				}
			}
		}
		gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
		gl.glDepthMask(true);
		GlRenderer.restoreLighting();
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "([BIIIII)Z")
	private static boolean hasShadowPixel(@OriginalArg(0) byte[] pixels, @OriginalArg(1) int offset, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int rowPad) {
		@Pc(3) int align = width % 8;
		@Pc(9) int alignPad;
		if (align == 0) {
			alignPad = 0;
		} else {
			alignPad = 8 - align;
		}
		@Pc(21) int rowQuads = -((height + 8 - 1) / 8);
		@Pc(30) int colQuads = -((width + 8 - 1) / 8);
		for (@Pc(32) int row = rowQuads; row < 0; row++) {
			for (@Pc(36) int col = colQuads; col < 0; col++) {
				if (pixels[offset] == 0) {
					return true;
				}
				offset += 8;
			}
			offset -= alignPad;
			if (pixels[offset - 1] == 0) {
				return true;
			}
			offset += rowPad;
		}
		return false;
	}

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "(Lclient!ek;Lclient!ek;II)Z")
	private static boolean testSpriteOverlap(@OriginalArg(0) SoftwareIndexedSprite src, @OriginalArg(1) SoftwareIndexedSprite dest, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		x += src.xOffset;
		y += src.yOffset;
		@Pc(16) int destOff = x + y * dest.width;
		@Pc(19) int height = src.height;
		@Pc(22) int width = src.width;
		@Pc(27) int destRowPad = dest.width - width;
		@Pc(33) int clip;
		if (y <= 0) {
			clip = 1 - y;
			height -= clip;
			destOff += clip * dest.width;
			y = 1;
		}
		if (y + height >= dest.height) {
			clip = y + height + 1 - dest.height;
			height -= clip;
		}
		if (x <= 0) {
			clip = 1 - x;
			width -= clip;
			destOff += clip;
			destRowPad += clip;
			x = 1;
		}
		if (x + width >= dest.width) {
			clip = x + width + 1 - dest.width;
			width -= clip;
			destRowPad += clip;
		}
		if (width > 0 && height > 0) {
			destRowPad += dest.width * 7;
			markDirtyRegion(x, y, width, height);
			return hasShadowPixel(dest.pixels, destOff, width, height, destRowPad);
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(II)V")
	public static void init() {
		gridColumns = 13;
		gridRows = 13;
		shadowMapImage = new SoftwareIndexedSprite(gridColumns * 128 + 2, gridRows * 128 + 2, 0);
		shadows = new Shadow[gridColumns][gridRows];
		for (@Pc(32) int col = 0; col < gridColumns; col++) {
			for (@Pc(37) int row = 0; row < gridRows; row++) {
				shadows[col][row] = new Shadow();
			}
		}
	}

	@OriginalMember(owner = "client!tj", name = "c", descriptor = "(Lclient!ek;Lclient!ek;II)V")
	private static void removeShadowSprite(@OriginalArg(0) SoftwareIndexedSprite src, @OriginalArg(1) SoftwareIndexedSprite dest, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		x += src.xOffset;
		y += src.yOffset;
		@Pc(16) int destOff = x + y * dest.width;
		@Pc(18) int srcOff = 0;
		@Pc(21) int height = src.height;
		@Pc(24) int width = src.width;
		@Pc(29) int destRowPad = dest.width - width;
		@Pc(31) int srcRowPad = 0;
		@Pc(37) int clip;
		if (y <= 0) {
			clip = 1 - y;
			height -= clip;
			srcOff = clip * width;
			destOff += clip * dest.width;
			y = 1;
		}
		if (y + height >= dest.height) {
			clip = y + height + 1 - dest.height;
			height -= clip;
		}
		if (x <= 0) {
			clip = 1 - x;
			width -= clip;
			srcOff += clip;
			destOff += clip;
			srcRowPad = clip;
			destRowPad += clip;
			x = 1;
		}
		if (x + width >= dest.width) {
			clip = x + width + 1 - dest.width;
			width -= clip;
			srcRowPad += clip;
			destRowPad += clip;
		}
		if (width > 0 && height > 0) {
			subtractPixels(dest.pixels, src.pixels, srcOff, destOff, width, height, destRowPad, srcRowPad);
			markDirtyRegion(x, y, width, height);
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "()V")
	public static void destroy() {
		shadowMapImage = null;
		Sprites.floorShadows = null;
		shadows = null;
	}

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "([B[BIIIIII)V")
	private static void addPixels(@OriginalArg(0) byte[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int srcOff, @OriginalArg(3) int destOff, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int destRowPad, @OriginalArg(7) int srcRowPad) {
		@Pc(4) int quads = -(width >> 2);
		@Pc(9) int remainder = -(width & 0x3);
		for (@Pc(12) int row = -height; row < 0; row++) {
			@Pc(16) int q;
			@Pc(20) int idx;
			for (q = quads; q < 0; q++) {
				idx = destOff++;
				dest[idx] += src[srcOff++];
				@Pc(32) int idx2 = destOff++;
				dest[idx2] += src[srcOff++];
				@Pc(44) int idx3 = destOff++;
				dest[idx3] += src[srcOff++];
				@Pc(56) int idx4 = destOff++;
				dest[idx4] += src[srcOff++];
			}
			for (q = remainder; q < 0; q++) {
				idx = destOff++;
				dest[idx] += src[srcOff++];
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "([B[BIIIII)V")
	private static void subtractRotatedPixels(@OriginalArg(0) byte[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int srcOff, @OriginalArg(3) int destOff, @OriginalArg(4) int destRowPad, @OriginalArg(5) int srcStride, @OriginalArg(6) int srcRowPad) {
		for (@Pc(1) int row = -16; row < 0; row++) {
			for (@Pc(5) int q = -4; q < 0; q++) {
				@Pc(9) int idx = destOff++;
				dest[idx] = (byte) (dest[idx] + 1 - src[srcOff]);
				srcOff += srcStride;
				@Pc(26) int idx2 = destOff++;
				dest[idx2] = (byte) (dest[idx2] + 1 - src[srcOff]);
				srcOff += srcStride;
				@Pc(43) int idx3 = destOff++;
				dest[idx3] = (byte) (dest[idx3] + 1 - src[srcOff]);
				srcOff += srcStride;
				@Pc(60) int idx4 = destOff++;
				dest[idx4] = (byte) (dest[idx4] + 1 - src[srcOff]);
				srcOff += srcStride;
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(Lclient!ek;III)V")
	public static void removeObjectShadow(@OriginalArg(0) SoftwareIndexedSprite sprite, @OriginalArg(1) int fineX, @OriginalArg(2) int height, @OriginalArg(3) int fineZ) {
		if (sprite != null) {
			@Pc(12) int shadowX = fineX - (height * FogManager.lightX >> 8) >> 3;
			@Pc(22) int shadowZ = fineZ - (height * FogManager.lightZ >> 8) >> 3;
			removeShadowSprite(sprite, shadowMapImage, shadowX + 1, shadowZ + 1);
		}
	}

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "([B[BIIIII)V")
	private static void addRotatedPixels(@OriginalArg(0) byte[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int srcOff, @OriginalArg(3) int destOff, @OriginalArg(4) int destRowPad, @OriginalArg(5) int srcStride, @OriginalArg(6) int srcRowPad) {
		for (@Pc(1) int row = -16; row < 0; row++) {
			for (@Pc(5) int q = -4; q < 0; q++) {
				@Pc(9) int idx = destOff++;
				dest[idx] += src[srcOff];
				srcOff += srcStride;
				@Pc(24) int idx2 = destOff++;
				dest[idx2] += src[srcOff];
				srcOff += srcStride;
				@Pc(39) int idx3 = destOff++;
				dest[idx3] += src[srcOff];
				srcOff += srcStride;
				@Pc(54) int idx4 = destOff++;
				dest[idx4] += src[srcOff];
				srcOff += srcStride;
			}
			destOff += destRowPad;
			srcOff += srcRowPad;
		}
	}

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "(Lclient!ek;III)Z")
	public static boolean isObjectInShadow(@OriginalArg(0) SoftwareIndexedSprite sprite, @OriginalArg(1) int fineX, @OriginalArg(2) int height, @OriginalArg(3) int fineZ) {
		if (sprite == null) {
			return false;
		} else {
			@Pc(13) int shadowX = fineX - (height * FogManager.lightX >> 8) >> 3;
			@Pc(23) int shadowZ = fineZ - (height * FogManager.lightZ >> 8) >> 3;
			return testSpriteOverlap(sprite, shadowMapImage, shadowX + 1, shadowZ + 1);
		}
	}

	@OriginalMember(owner = "client!tj", name = "a", descriptor = "(Lclient!ek;Lclient!ek;IIIZ)V")
	private static void addRotatedFloorShadow(@OriginalArg(0) SoftwareIndexedSprite src, @OriginalArg(1) SoftwareIndexedSprite dest, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int rotation, @OriginalArg(5) boolean subtract) {
		if (x <= 0 || y <= 0 || x + 16 >= dest.width || y + 16 >= dest.height) {
			return;
		}
		@Pc(23) int destOff = x + y * dest.width;
		@Pc(28) int destRowPad = dest.width - 16;
		@Pc(32) short srcStart;
		@Pc(34) byte srcStride;
		@Pc(41) short srcRowPad;
		if (rotation == 0) {
			srcStart = 240;
			srcStride = 1;
			srcRowPad = -32;
		} else if (rotation == 1) {
			srcStart = 255;
			srcStride = -16;
			srcRowPad = 255;
		} else if (rotation == 2) {
			srcStart = 15;
			srcStride = -1;
			srcRowPad = 32;
		} else {
			srcStart = 0;
			srcStride = 16;
			srcRowPad = -255;
		}
		if (subtract) {
			subtractRotatedPixels(dest.pixels, src.pixels, srcStart, destOff, destRowPad, srcStride, srcRowPad);
		} else {
			addRotatedPixels(dest.pixels, src.pixels, srcStart, destOff, destRowPad, srcStride, srcRowPad);
		}
		markDirtyRegion(x, y, 16, 16);
	}

	@OriginalMember(owner = "client!tj", name = "c", descriptor = "(Lclient!ek;III)V")
	public static void addObjectShadow(@OriginalArg(0) SoftwareIndexedSprite sprite, @OriginalArg(1) int fineX, @OriginalArg(2) int height, @OriginalArg(3) int fineZ) {
		if (sprite != null) {
			@Pc(12) int shadowX = fineX - (height * FogManager.lightX >> 8) >> 3;
			@Pc(22) int shadowZ = fineZ - (height * FogManager.lightZ >> 8) >> 3;
			addShadowSprite(sprite, shadowMapImage, shadowX + 1, shadowZ + 1);
		}
	}
}
