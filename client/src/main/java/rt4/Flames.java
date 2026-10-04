package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class Flames {
	@OriginalMember(owner = "client!se", name = "t", descriptor = "[I")
	public static final int[] flameGradientSource = new int[]{0xbf6428, 0xec963a, 0xf2d766, 0xfff2ba};

	@OriginalMember(owner = "client!bb", name = "m", descriptor = "Lclient!na;")
	public static final JagString RUNES = JagString.parse("runes");

	@OriginalMember(owner = "client!cl", name = "ab", descriptor = "[I")
	public static final int[] flameShiftX = new int[256];

	@OriginalMember(owner = "client!km", name = "Yc", descriptor = "I")
	public static int runesId;

	@OriginalMember(owner = "client!uf", name = "e", descriptor = "I")
	public static int flameCycle = 0;

	@OriginalMember(owner = "client!wb", name = "b", descriptor = "[I")
	public static int[] flameBuffer1;

	@OriginalMember(owner = "client!t", name = "G", descriptor = "[Lclient!ek;")
	public static SoftwareIndexedSprite[] runes;

	@OriginalMember(owner = "client!hh", name = "r", descriptor = "[I")
	public static int[] flameGradient;

	@OriginalMember(owner = "client!vg", name = "e", descriptor = "[I")
	public static int[] flameBuffer2;

	@OriginalMember(owner = "client!ri", name = "d", descriptor = "[I")
	public static int[] flameIntensityBuffer;

	@OriginalMember(owner = "client!nd", name = "y", descriptor = "Lclient!mm;")
	public static SoftwareSprite imageFlames;

	@OriginalMember(owner = "client!i", name = "ec", descriptor = "[I")
	public static int[] flameIntensity;

	@OriginalMember(owner = "client!jj", name = "e", descriptor = "I")
	public static int flameOffset = 0;

	@OriginalMember(owner = "client!a", name = "i", descriptor = "I")
	public static int flameSinePhase = 0;

	@OriginalMember(owner = "client!sf", name = "i", descriptor = "I")
	public static int pendingSparks = 0;

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(ILclient!ve;)V")
	public static void init(@OriginalArg(1) Js5 archive) {
		runesId = archive.getGroupId(RUNES);
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!ve;I)Z")
	public static boolean isReady(@OriginalArg(0) Js5 archive) {
		return archive.isFileReady(runesId);
	}

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "(Z)V")
	public static void update() {
		flameCycle++;
	}

	@OriginalMember(owner = "client!lf", name = "a", descriptor = "(ILclient!ve;)V")
	public static void load(@OriginalArg(1) Js5 archive) {
		runes = SpriteLoader.loadSoftwareIndexedSpritesAutoDetect(runesId, archive);
		flameGradient = new int[256];
		@Pc(15) int i;
		for (i = 0; i < 3; i++) {
			@Pc(30) int nextRed = flameGradientSource[i + 1] >> 16 & 0xFF;
			@Pc(39) float red = (float) (flameGradientSource[i] >> 16 & 0xFF);
			@Pc(48) float green = (float) (flameGradientSource[i] >> 8 & 0xFF);
			@Pc(55) float blue = (float) (flameGradientSource[i] & 0xFF);
			@Pc(62) float redStep = ((float) nextRed - red) / 64.0F;
			@Pc(72) int nextGreen = flameGradientSource[i + 1] >> 8 & 0xFF;
			@Pc(80) float greenStep = ((float) nextGreen - green) / 64.0F;
			@Pc(88) int nextBlue = flameGradientSource[i + 1] & 0xFF;
			@Pc(95) float blueStep = ((float) nextBlue - blue) / 64.0F;
			for (@Pc(97) int j = 0; j < 64; j++) {
				flameGradient[j + i * 64] = (int) blue | (int) green << 8 | (int) red << 16;
				green += greenStep;
				blue += blueStep;
				red += redStep;
			}
		}
		for (i = 192; i < 255; i++) {
			flameGradient[i] = flameGradientSource[3];
		}
		flameBuffer1 = new int[32768];
		flameBuffer2 = new int[32768];
		setRune(null);
		flameIntensity = new int[32768];
		flameIntensityBuffer = new int[32768];
		imageFlames = new SoftwareSprite(128, 254);
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(II)V")
	public static void updateFlames(@OriginalArg(0) int delta) {
		if (delta > 256) {
			delta = 256;
		}
		if (delta > 10) {
			delta = 10;
		}
		flameOffset += delta * 128;
		@Pc(40) int i;
		if (flameBuffer1.length < flameOffset) {
			flameOffset -= flameBuffer1.length;
			i = (int) (Math.random() * 12.0D);
			setRune(runes[i]);
		}
		i = 0;
		@Pc(54) int decayLen = (256 - delta) * 128;
		@Pc(58) int decayOffset = delta * 128;
		@Pc(60) int row;
		@Pc(89) int intensity;
		for (row = 0; row < decayLen; row++) {
			intensity = flameIntensityBuffer[i + decayOffset] - delta * flameBuffer1[flameBuffer1.length - 1 & flameOffset + i] / 6;
			if (intensity < 0) {
				intensity = 0;
			}
			flameIntensityBuffer[i++] = intensity;
		}
		@Pc(117) int x;
		@Pc(125) int rand;
		for (row = 256 - delta; row < 256; row++) {
			intensity = row * 128;
			for (x = 0; x < 128; x++) {
				rand = (int) (Math.random() * 100.0D);
				if (rand < 50 && x > 10 && x < 118) {
					flameIntensityBuffer[x + intensity] = 255;
				} else {
					flameIntensityBuffer[x + intensity] = 0;
				}
			}
		}
		for (row = 0; row < 256 - delta; row++) {
			flameShiftX[row] = flameShiftX[row + delta];
		}
		for (row = 256 - delta; row < 256; row++) {
			flameShiftX[row] = (int) (Math.sin((double) flameSinePhase / 14.0D) * 16.0D + Math.sin((double) flameSinePhase / 15.0D) * 14.0D + Math.sin((double) flameSinePhase / 16.0D) * 12.0D);
			flameSinePhase++;
		}
		pendingSparks += delta;
		row = (delta + (client.loop & 0x1)) / 2;
		if (row <= 0) {
			return;
		}
		for (intensity = 0; intensity < pendingSparks; intensity++) {
			x = (int) (Math.random() * 124.0D) + 2;
			rand = (int) (Math.random() * 128.0D) + 128;
			flameIntensityBuffer[x + (rand << 7)] = 192;
		}
		pendingSparks = 0;
		@Pc(290) int j;
		for (intensity = 0; intensity < 256; intensity++) {
			rand = intensity * 128;
			x = 0;
			for (j = -row; j < 128; j++) {
				if (row + j < 128) {
					x += flameIntensityBuffer[rand + j + row];
				}
				if (j - row - 1 >= 0) {
					x -= flameIntensityBuffer[j + rand - row - 1];
				}
				if (j >= 0) {
					flameIntensity[j + rand] = x / (row * 2 + 1);
				}
			}
		}
		for (intensity = 0; intensity < 128; intensity++) {
			x = 0;
			for (rand = -row; rand < 256; rand++) {
				j = rand * 128;
				if (rand + row < 256) {
					x += flameIntensity[row * 128 + intensity + j];
				}
				if (rand - row - 1 >= 0) {
					x -= flameIntensity[intensity + j - (row + 1) * 128];
				}
				if (rand >= 0) {
					flameIntensityBuffer[j + intensity] = x / (row * 2 + 1);
				}
			}
		}
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(BLclient!ek;)V")
	public static void setRune(@OriginalArg(1) SoftwareIndexedSprite sprite) {
		@Pc(5) int i;
		for (i = 0; i < flameBuffer1.length; i++) {
			flameBuffer1[i] = 0;
		}
		@Pc(36) int offset;
		for (i = 0; i < 5000; i++) {
			offset = (int) ((double) 256 * Math.random() * 128.0D);
			flameBuffer1[offset] = (int) (Math.random() * 284.0D);
		}
		@Pc(66) int x;
		@Pc(76) int index;
		for (i = 0; i < 20; i++) {
			for (offset = 1; offset < 255; offset++) {
				for (x = 1; x < 127; x++) {
					index = x + (offset << 7);
					flameBuffer2[index] = (flameBuffer1[index + 128] + flameBuffer1[index - 1] + flameBuffer1[index + 1] + flameBuffer1[index + -128]) / 4;
				}
			}
			@Pc(113) int[] temp = flameBuffer1;
			flameBuffer1 = flameBuffer2;
			flameBuffer2 = temp;
		}
		if (sprite == null) {
			return;
		}
		i = 0;
		for (offset = 0; offset < sprite.height; offset++) {
			for (x = 0; x < sprite.width; x++) {
				if (sprite.pixels[i++] != 0) {
					index = sprite.xOffset + x + 16;
					@Pc(162) int y = sprite.yOffset + offset + 16;
					@Pc(169) int idx = index + (y << 7);
					flameBuffer1[idx] = 0;
				}
			}
		}
	}

	@OriginalMember(owner = "client!s", name = "b", descriptor = "(III)V")
	public static void render(@OriginalArg(0) int x, @OriginalArg(2) int y) {
		if (flameCycle > 0) {
			updateFlames(flameCycle);
			flameCycle = 0;
		}
		@Pc(20) int srcIndex = 0;
		@Pc(24) int rasterOffset = SoftwareRaster.width * y;
		@Pc(26) int destIndex = 0;
		for (@Pc(28) int row = 1; row < 255; row++) {
			@Pc(43) int shift = (256 - row) * flameShiftX[row] / 256;
			if (shift < 0) {
				shift = 0;
			}
			srcIndex += shift;
			@Pc(55) int col;
			for (col = shift; col < 128; col++) {
				@Pc(65) int bg = SoftwareRaster.pixels[rasterOffset++ + x];
				@Pc(70) int intensity = flameIntensityBuffer[srcIndex++];
				if (intensity == 0) {
					imageFlames.pixels[destIndex++] = bg;
				} else {
					@Pc(76) int bright = intensity + 18;
					if (bright > 255) {
						bright = 255;
					}
					@Pc(89) int fade = 256 - intensity - 18;
					if (fade > 255) {
						fade = 255;
					}
					intensity = flameGradient[intensity];
					imageFlames.pixels[destIndex++] = (fade * (bg & 0xFF00FF) + (intensity & 0xFF00FF) * bright & 0xFF00FF00) + ((intensity & 0xFF00) * bright + ((bg & 0xFF00) * fade) & 0xFF0000) >> 8;
				}
			}
			for (col = 0; col < shift; col++) {
				imageFlames.pixels[destIndex++] = SoftwareRaster.pixels[x + rasterOffset++];
			}
			rasterOffset += SoftwareRaster.width - 128;
		}
		if (GlRenderer.enabled) {
			GlRaster.drawPixels(imageFlames.pixels, x, y, imageFlames.width, imageFlames.height);
		} else {
			imageFlames.drawPixels(x, y);
		}
	}
}
