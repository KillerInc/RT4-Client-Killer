package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class SpriteLoader {
	@OriginalMember(owner = "client!uj", name = "x", descriptor = "[I")
	public static int[] innerWidths;
	@OriginalMember(owner = "client!wa", name = "z", descriptor = "[I")
	public static int[] yOffsets;
	@OriginalMember(owner = "client!ca", name = "Y", descriptor = "[I")
	public static int[] innerHeights;
	@OriginalMember(owner = "client!ah", name = "l", descriptor = "[[B")
	public static byte[][] pixels;
	@OriginalMember(owner = "client!wf", name = "b", descriptor = "[I")
	public static int[] xOffsets;
	@OriginalMember(owner = "client!vc", name = "R", descriptor = "[I")
	public static int[] palette;
	@OriginalMember(owner = "client!nb", name = "h", descriptor = "I")
	public static int frames;
	@OriginalMember(owner = "client!fb", name = "n", descriptor = "[[B")
	public static byte[][] alpha;
	@OriginalMember(owner = "client!mi", name = "W", descriptor = "[Z")
	public static boolean[] hasAlpha;
	@OriginalMember(owner = "client!jk", name = "x", descriptor = "I")
	public static int width;
	@OriginalMember(owner = "client!sg", name = "h", descriptor = "I")
	public static int height;

	@OriginalMember(owner = "client!cg", name = "a", descriptor = "(ZILclient!ve;I)[Lclient!ek;")
	static SoftwareIndexedSprite[] loadSoftwareIndexedSprites(@OriginalArg(1) int fileId, @OriginalArg(2) Js5 js5) {
		return decode(js5, 0, fileId) ? createSoftwareIndexedSprites() : null;
	}

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(Lclient!ve;III)Z")
	public static boolean decode(@OriginalArg(0) Js5 js5, @OriginalArg(1) int archive, @OriginalArg(2) int fileId) {
		@Pc(9) byte[] data = js5.fetchFile(fileId, archive);
		if (data == null) {
			return false;
		} else {
			decode(data);
			return true;
		}
	}

	@OriginalMember(owner = "client!jg", name = "a", descriptor = "(I)[Lclient!ek;")
	public static SoftwareIndexedSprite[] createSoftwareIndexedSprites() {
		@Pc(2) SoftwareIndexedSprite[] sprites = new SoftwareIndexedSprite[frames];
		for (@Pc(8) int i = 0; i < frames; i++) {
			sprites[i] = new SoftwareIndexedSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], pixels[i], palette);
		}
		clear();
		return sprites;
	}

	@OriginalMember(owner = "client!gk", name = "a", descriptor = "([BI)V")
	public static void decode(@OriginalArg(0) byte[] data) {
		@Pc(4) Buffer buffer = new Buffer(data);
		buffer.offset = data.length - 2;
		frames = buffer.g2();
		innerHeights = new int[frames];
		innerWidths = new int[frames];
		xOffsets = new int[frames];
		hasAlpha = new boolean[frames];
		alpha = new byte[frames][];
		yOffsets = new int[frames];
		pixels = new byte[frames][];
		buffer.offset = data.length - frames * 8 - 7;
		width = buffer.g2();
		height = buffer.g2();
		@Pc(66) int paletteSize = (buffer.g1() & 0xFF) + 1;
		@Pc(68) int i;
		for (i = 0; i < frames; i++) {
			xOffsets[i] = buffer.g2();
		}
		for (i = 0; i < frames; i++) {
			yOffsets[i] = buffer.g2();
		}
		for (i = 0; i < frames; i++) {
			innerWidths[i] = buffer.g2();
		}
		for (i = 0; i < frames; i++) {
			innerHeights[i] = buffer.g2();
		}
		buffer.offset = data.length + 3 - frames * 8 - paletteSize * 3 - 7;
		palette = new int[paletteSize];
		for (i = 1; i < paletteSize; i++) {
			palette[i] = buffer.g3();
			if (palette[i] == 0) {
				palette[i] = 1;
			}
		}
		buffer.offset = 0;
		for (i = 0; i < frames; i++) {
			@Pc(195) int innerWidth = innerWidths[i];
			@Pc(199) int innerHeight = innerHeights[i];
			@Pc(203) int pixelCount = innerWidth * innerHeight;
			@Pc(206) byte[] framePixels = new byte[pixelCount];
			@Pc(208) boolean frameHasAlpha = false;
			pixels[i] = framePixels;
			@Pc(215) byte[] frameAlpha = new byte[pixelCount];
			alpha[i] = frameAlpha;
			@Pc(223) int flags = buffer.g1();
			@Pc(232) int j;
			if ((flags & 0x1) == 0) {
				for (j = 0; j < pixelCount; j++) {
					framePixels[j] = buffer.g1b();
				}
				if ((flags & 0x2) != 0) {
					for (j = 0; j < pixelCount; j++) {
						@Pc(343) byte a = frameAlpha[j] = buffer.g1b();
						frameHasAlpha |= a != -1;
					}
				}
			} else {
				j = 0;
				readColumns:
				while (true) {
					@Pc(241) int k;
					if (j >= innerWidth) {
						if ((flags & 0x2) == 0) {
							break;
						}
						j = 0;
						while (true) {
							if (j >= innerWidth) {
								break readColumns;
							}
							for (k = 0; k < innerHeight; k++) {
								@Pc(291) byte a = frameAlpha[innerWidth * k + j] = buffer.g1b();
								frameHasAlpha |= a != -1;
							}
							j++;
						}
					}
					for (k = 0; k < innerHeight; k++) {
						framePixels[j + k * innerWidth] = buffer.g1b();
					}
					j++;
				}
			}
			hasAlpha[i] = frameHasAlpha;
		}
	}

	@OriginalMember(owner = "client!g", name = "a", descriptor = "(B)V")
	public static void clear() {
		innerWidths = null;
		yOffsets = null;
		innerHeights = null;
		pixels = null;
		xOffsets = null;
		palette = null;
	}

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(IBILclient!ve;)[Lclient!qf;")
	public static Sprite[] loadAlphaSprites(@OriginalArg(2) int fileId, @OriginalArg(3) Js5 js5) {
		return decode(js5, 0, fileId) ? createAlphaSprites() : null;
	}

	@OriginalMember(owner = "client!vj", name = "a", descriptor = "(IIILclient!ve;)[Lclient!mm;")
	public static SoftwareSprite[] loadSoftwareSprites(@OriginalArg(2) int fileId, @OriginalArg(3) Js5 js5) {
		return decode(js5, 0, fileId) ? createSoftwareSprites() : null;
	}

	@OriginalMember(owner = "client!gd", name = "a", descriptor = "(ILclient!ve;II)Lclient!mm;")
	public static SoftwareSprite loadSoftwareSprite(@OriginalArg(0) int archive, @OriginalArg(1) Js5 js5, @OriginalArg(3) int fileId) {
		return decode(js5, archive, fileId) ? createSingleSoftwareSprite() : null;
	}

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "(Lclient!ve;BII)[Lclient!ok;")
	public static IndexedSprite[] loadIndexedSprites(@OriginalArg(0) Js5 js5, @OriginalArg(3) int fileId) {
		return decode(js5, 0, fileId) ? createIndexedSprites() : null;
	}

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(IIILclient!ve;)[Lclient!qf;")
	public static Sprite[] loadOpaqueSprites(@OriginalArg(2) int fileId, @OriginalArg(3) Js5 js5) {
		return decode(js5, 0, fileId) ? createOpaqueSprites() : null;
	}

	@OriginalMember(owner = "client!lk", name = "a", descriptor = "(IIBLclient!ve;)Lclient!qf;")
	public static Sprite loadSprites(@OriginalArg(1) int fileId, @OriginalArg(3) Js5 js5) {
		return decode(js5, 0, fileId) ? createSingleSprite() : null;
	}

	@OriginalMember(owner = "client!re", name = "b", descriptor = "(I)[Lclient!qf;")
	public static Sprite[] createAlphaSprites() {
		@Pc(14) Sprite[] sprites = new Sprite[frames];
		for (@Pc(16) int i = 0; i < frames; i++) {
			@Pc(23) byte[] framePixels = pixels[i];
			@Pc(31) int pixelCount = innerHeights[i] * innerWidths[i];
			if (hasAlpha[i]) {
				@Pc(38) int[] rgbaPixels = new int[pixelCount];
				@Pc(42) byte[] frameAlpha = alpha[i];
				for (@Pc(44) int j = 0; j < pixelCount; j++) {
					rgbaPixels[j] = palette[framePixels[j] & 0xFF] | (frameAlpha[j] & 0xFF) << 24;
				}
				if (GlRenderer.enabled) {
					sprites[i] = new GlAlphaSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbaPixels);
				} else {
					sprites[i] = new SoftwareAlphaSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbaPixels);
				}
			} else {
				@Pc(119) int[] rgbPixels = new int[pixelCount];
				for (@Pc(121) int j = 0; j < pixelCount; j++) {
					rgbPixels[j] = palette[framePixels[j] & 0xFF];
				}
				if (GlRenderer.enabled) {
					sprites[i] = new GlSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbPixels);
				} else {
					sprites[i] = new SoftwareSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbPixels);
				}
			}
		}
		clear();
		return sprites;
	}

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "(Z)[Lclient!mm;")
	public static SoftwareSprite[] createSoftwareSprites() {
		@Pc(4) SoftwareSprite[] sprites = new SoftwareSprite[frames];
		for (@Pc(12) int i = 0; i < frames; i++) {
			@Pc(27) int pixelCount = innerHeights[i] * innerWidths[i];
			@Pc(31) byte[] framePixels = pixels[i];
			@Pc(34) int[] rgbPixels = new int[pixelCount];
			for (@Pc(36) int j = 0; j < pixelCount; j++) {
				rgbPixels[j] = palette[framePixels[j] & 0xFF];
			}
			sprites[i] = new SoftwareSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbPixels);
		}
		clear();
		return sprites;
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(I)Lclient!mm;")
	public static SoftwareSprite createSingleSoftwareSprite() {
		@Pc(13) int pixelCount = innerWidths[0] * innerHeights[0];
		@Pc(17) byte[] framePixels = pixels[0];
		@Pc(20) int[] rgbPixels = new int[pixelCount];
		for (@Pc(22) int i = 0; i < pixelCount; i++) {
			rgbPixels[i] = palette[framePixels[i] & 0xFF];
		}
		@Pc(57) SoftwareSprite sprite = new SoftwareSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], rgbPixels);
		clear();
		return sprite;
	}

	@OriginalMember(owner = "client!ui", name = "h", descriptor = "(I)[Lclient!ok;")
	public static IndexedSprite[] createIndexedSprites() {
		@Pc(8) IndexedSprite[] sprites = new IndexedSprite[frames];
		for (@Pc(10) int i = 0; i < frames; i++) {
			if (GlRenderer.enabled) {
				sprites[i] = new GlIndexedSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], pixels[i], palette);
			} else {
				sprites[i] = new SoftwareIndexedSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], pixels[i], palette);
			}
		}
		clear();
		return sprites;
	}

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(I)[Lclient!qf;")
	public static Sprite[] createOpaqueSprites() {
		@Pc(6) Sprite[] sprites = new Sprite[frames];
		for (@Pc(15) int i = 0; i < frames; i++) {
			@Pc(30) int pixelCount = innerWidths[i] * innerHeights[i];
			@Pc(34) byte[] framePixels = pixels[i];
			@Pc(37) int[] rgbPixels = new int[pixelCount];
			for (@Pc(39) int j = 0; j < pixelCount; j++) {
				rgbPixels[j] = palette[framePixels[j] & 0xFF];
			}
			if (GlRenderer.enabled) {
				sprites[i] = new GlSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbPixels);
			} else {
				sprites[i] = new SoftwareSprite(width, height, xOffsets[i], yOffsets[i], innerWidths[i], innerHeights[i], rgbPixels);
			}
		}
		clear();
		return sprites;
	}

	@OriginalMember(owner = "client!gi", name = "b", descriptor = "(I)Lclient!qf;")
	public static Sprite createSingleSprite() {
		@Pc(9) byte[] framePixels = pixels[0];
		@Pc(17) int pixelCount = innerWidths[0] * innerHeights[0];
		@Pc(20) int[] rgbPixels = new int[pixelCount];
		for (@Pc(28) int i = 0; i < pixelCount; i++) {
			rgbPixels[i] = palette[framePixels[i] & 0xFF];
		}
		@Pc(69) Sprite sprite;
		if (GlRenderer.enabled) {
			sprite = new GlSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], rgbPixels);
		} else {
			sprite = new SoftwareSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], rgbPixels);
		}
		clear();
		return sprite;
	}

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(BLclient!ve;I)Z")
	public static boolean decode(@OriginalArg(1) Js5 js5, @OriginalArg(2) int fileId) {
		@Pc(13) byte[] data = js5.fetchFile(fileId);
		if (data == null) {
			return false;
		} else {
			decode(data);
			return true;
		}
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(BILclient!ve;)[Lclient!ek;")
	public static SoftwareIndexedSprite[] loadSoftwareIndexedSpritesAutoDetect(@OriginalArg(1) int fileId, @OriginalArg(2) Js5 js5) {
		return decode(js5, fileId) ? createSoftwareIndexedSprites() : null;
	}

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(ILclient!ve;Z)Lclient!ok;")
	public static IndexedSprite loadIndexedSpriteAutoDetect(@OriginalArg(0) int fileId, @OriginalArg(1) Js5 js5) {
		return decode(js5, fileId) ? createSingleIndexedSprite() : null;
	}

	@OriginalMember(owner = "client!kc", name = "a", descriptor = "(ILclient!ve;I)Lclient!qf;")
	public static Sprite loadSpriteAutoDetect(@OriginalArg(1) Js5 js5, @OriginalArg(2) int fileId) {
		return decode(js5, fileId) ? createSingleSprite() : null;
	}

	@OriginalMember(owner = "client!wh", name = "b", descriptor = "(B)Lclient!ok;")
	public static IndexedSprite createSingleIndexedSprite() {
		@Pc(27) IndexedSprite sprite;
		if (GlRenderer.enabled) {
			sprite = new GlIndexedSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], pixels[0], palette);
		} else {
			sprite = new SoftwareIndexedSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], pixels[0], palette);
		}
		clear();
		return sprite;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(IZILclient!ve;)Lclient!ek;")
	public static SoftwareIndexedSprite loadSoftwareIndexedSprite(@OriginalArg(2) int fileId, @OriginalArg(3) Js5 js5) {
		return decode(js5, 0, fileId) ? createSingleSoftwareIndexedSprite() : null;
	}

	@OriginalMember(owner = "client!na", name = "a", descriptor = "(Lclient!ve;IZ)Lclient!mm;")
	public static SoftwareSprite loadSoftwareSpriteAutoDetect(@OriginalArg(0) Js5 js5, @OriginalArg(1) int fileId) {
		return decode(js5, fileId) ? createSingleSoftwareSprite() : null;
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(B)Lclient!ek;")
	public static SoftwareIndexedSprite createSingleSoftwareIndexedSprite() {
		@Pc(25) SoftwareIndexedSprite sprite = new SoftwareIndexedSprite(width, height, xOffsets[0], yOffsets[0], innerWidths[0], innerHeights[0], pixels[0], palette);
		clear();
		return sprite;
	}
}
