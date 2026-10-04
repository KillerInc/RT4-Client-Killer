package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!lc")
public final class Texture {

	@OriginalMember(owner = "client!i", name = "ac", descriptor = "[I")
	public static final int[] brightnessMap = new int[256];
	@OriginalMember(owner = "client!rh", name = "g", descriptor = "I")
	public static final int spriteGroupId = -1;
	@OriginalMember(owner = "client!jh", name = "f", descriptor = "D")
	public static double brightness = -1.0D;
	@OriginalMember(owner = "client!cm", name = "a", descriptor = "Lclient!m;")
	public static TextureProvider provider;
	@OriginalMember(owner = "client!pe", name = "r", descriptor = "I")
	public static int width;
	@OriginalMember(owner = "client!ob", name = "f", descriptor = "Lclient!ve;")
	public static Js5 spritesArchive;
	@OriginalMember(owner = "client!nj", name = "k", descriptor = "I")
	public static int height;
	@OriginalMember(owner = "client!nk", name = "d", descriptor = "[I")
	public static int[] widthFractions;
	@OriginalMember(owner = "client!lb", name = "z", descriptor = "I")
	public static int heightMask;
	@OriginalMember(owner = "client!ck", name = "X", descriptor = "I")
	public static int widthMask;
	@OriginalMember(owner = "client!nb", name = "o", descriptor = "I")
	public static int widthScale;
	@OriginalMember(owner = "client!fb", name = "h", descriptor = "[I")
	public static int[] heightFractions;
	@OriginalMember(owner = "client!lc", name = "k", descriptor = "[I")
	private final int[] requiredTextureIds;

	@OriginalMember(owner = "client!lc", name = "b", descriptor = "[I")
	private final int[] requiredSpriteIds;

	@OriginalMember(owner = "client!lc", name = "d", descriptor = "Lclient!j;")
	private final TextureOp colorOp;

	@OriginalMember(owner = "client!lc", name = "i", descriptor = "Lclient!j;")
	private final TextureOp alphaOp;

	@OriginalMember(owner = "client!lc", name = "g", descriptor = "[Lclient!j;")
	private final TextureOp[] operations;

	@OriginalMember(owner = "client!lc", name = "<init>", descriptor = "(Lclient!wa;)V")
	public Texture(@OriginalArg(0) Buffer buf) {
		@Pc(7) int opCount = buf.g1();
		this.operations = new TextureOp[opCount];
		@Pc(14) int[][] inputIndices = new int[opCount][];
		@Pc(16) int textureCount = 0;
		@Pc(18) int spriteCount = 0;
		@Pc(20) int i;
		@Pc(27) TextureOp op;
		@Pc(45) int inputCount;
		@Pc(52) int j;
		for (i = 0; i < opCount; i++) {
			op = decodeTextureOp(buf);
			if (op.getRequiredSpriteId() >= 0) {
				spriteCount++;
			}
			if (op.getRequiredTextureId() >= 0) {
				textureCount++;
			}
			inputCount = op.inputs.length;
			inputIndices[i] = new int[inputCount];
			for (j = 0; j < inputCount; j++) {
				inputIndices[i][j] = buf.g1();
			}
			this.operations[i] = op;
		}
		this.requiredSpriteIds = new int[spriteCount];
		this.requiredTextureIds = new int[textureCount];
		spriteCount = 0;
		textureCount = 0;
		for (i = 0; i < opCount; i++) {
			op = this.operations[i];
			inputCount = op.inputs.length;
			for (j = 0; j < inputCount; j++) {
				op.inputs[j] = this.operations[inputIndices[i][j]];
			}
			j = op.getRequiredSpriteId();
			@Pc(136) int requiredTextureId = op.getRequiredTextureId();
			if (j > 0) {
				this.requiredSpriteIds[spriteCount++] = j;
			}
			if (requiredTextureId > 0) {
				this.requiredTextureIds[textureCount++] = requiredTextureId;
			}
			inputIndices[i] = null;
		}
		this.colorOp = this.operations[buf.g1()];
		inputIndices = null;
		this.alphaOp = this.operations[buf.g1()];
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(DI)V")
	public static void setBrightness(@OriginalArg(0) double value) {
		if (brightness == value) {
			return;
		}
		for (@Pc(12) int i = 0; i < 256; i++) {
			@Pc(25) int mapped = (int) (Math.pow((double) i / 255.0D, value) * 255.0D);
			brightnessMap[i] = mapped > 255 ? 255 : mapped;
		}
		brightness = value;
	}

	@OriginalMember(owner = "client!qk", name = "a", descriptor = "(BLclient!wa;)Lclient!j;")
	public static TextureOp decodeTextureOp(@OriginalArg(1) Buffer buf) {
		buf.g1();
		@Pc(13) int type = buf.g1();
		@Pc(17) TextureOp op = create(type);
		op.cacheHeight = buf.g1();
		@Pc(26) int opcodeCount = buf.g1();
		for (@Pc(34) int i = 0; i < opcodeCount; i++) {
			@Pc(41) int opcode = buf.g1();
			op.decode(opcode, buf);
		}
		op.postDecode();
		return op;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(IZ)Lclient!j;")
	public static TextureOp create(@OriginalArg(0) int type) {
		if (type == 0) {
			return new TextureOpMonochromeFill();
		} else if (type == 1) {
			return new TextureOpColorFill();
		} else if (type == 2) {
			return new TextureOpHorizontalGradient();
		} else if (type == 3) {
			return new TextureOpVerticalGradient();
		} else if (type == 4) {
			return new TextureOpBrickwork();
		} else if (type == 5) {
			return new TextureOpBoxBlur();
		} else if (type == 6) {
			return new TextureOpClamp();
		} else if (type == 7) {
			return new TextureOpCombine();
		} else if (type == 8) {
			return new TextureOpCurve();
		} else if (type == 9) {
			return new TextureOpFlip();
		} else if (type == 10) {
			return new TextureOpColorGradient();
		} else if (type == 11) {
			return new TextureOpColorMultiply();
		} else if (type == 12) {
			return new TextureOpWaveform();
		} else if (type == 13) {
			return new TextureOpNoise();
		} else if (type == 14) {
			return new TextureOpDiamond();
		} else if (type == 15) {
			return new TextureOpVoronoi();
		} else if (type == 16) {
			return new TextureOpSquareGrid();
		} else if (type == 17) {
			return new TextureOpHslAdjust();
		} else if (type == 18) {
			return new TextureOpTiledSprite();
		} else if (type == 19) {
			return new TextureOpPolarDistort();
		} else if (type == 20) {
			return new TextureOpTile();
		} else if (type == 21) {
			return new TextureOpInterpolate();
		} else if (type == 22) {
			return new TextureOpInvert();
		} else if (type == 23) {
			return new TextureOpKaleidoscope();
		} else if (type == 24) {
			return new TextureOpMonochrome();
		} else if (type == 25) {
			return new TextureOpColorReplace();
		} else if (type == 26) {
			return new TextureOpBinary();
		} else if (type == 27) {
			return new TextureOpStripes();
		} else if (type == 28) {
			return new TextureOpBarkPattern();
		} else if (type == 29) {
			return new TextureOpShapeRasterizer();
		} else if (type == 30) {
			return new TextureOpRange();
		} else if (type == 31) {
			return new TextureOpMandelbrot();
		} else if (type == 32) {
			return new TextureOpBumpMap();
		} else if (type == 33) {
			return new TextureOpNormalMap();
		} else if (type == 34) {
			return new TextureOpPerlinNoise();
		} else if (type == 35) {
			return new TextureOpEmboss();
		} else if (type == 36) {
			return new TextureOpTexture();
		} else if (type == 37) {
			return new TextureOpWaveInterference();
		} else if (type == 38) {
			return new TextureOpLineScatter();
		} else if (type == 39) {
			return new TextureOpSprite();
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!an", name = "c", descriptor = "(III)V")
	public static void setSize(@OriginalArg(1) int height, @OriginalArg(2) int newWidth) {
		if (width != newWidth) {
			widthFractions = new int[newWidth];
			for (@Pc(10) int x = 0; x < newWidth; x++) {
				widthFractions[x] = (x << 12) / newWidth;
			}
			widthScale = newWidth == 64 ? 2048 : 4096;
			widthMask = newWidth - 1;
			width = newWidth;
		}
		if (Texture.height == height) {
			return;
		}
		if (width == height) {
			heightFractions = widthFractions;
		} else {
			heightFractions = new int[height];
			for (@Pc(61) int y = 0; y < height; y++) {
				heightFractions[y] = (y << 12) / height;
			}
		}
		Texture.height = height;
		heightMask = height - 1;
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(IZIDILclient!ve;Lclient!m;Z)[I")
	public final int[] generateArgbPixels(@OriginalArg(0) int texHeight, @OriginalArg(1) boolean transpose, @OriginalArg(2) int texWidth, @OriginalArg(3) double brightnessValue, @OriginalArg(5) Js5 sprites, @OriginalArg(6) TextureProvider texProvider, @OriginalArg(7) boolean flipX) {
		setBrightness(brightnessValue);
		provider = texProvider;
		spritesArchive = sprites;
		setSize(texHeight, texWidth);
		@Pc(20) int i;
		for (i = 0; i < this.operations.length; i++) {
			this.operations[i].allocateImageCache(texHeight, texWidth);
		}
		@Pc(56) int[] pixels = new int[texHeight * texWidth];
		@Pc(64) int xEnd;
		@Pc(60) byte xStep;
		if (flipX) {
			xStep = -1;
			xEnd = -1;
			i = texWidth - 1;
		} else {
			xStep = 1;
			i = 0;
			xEnd = texWidth;
		}
		@Pc(76) int destOff = 0;
		@Pc(78) int y;
		for (y = 0; y < texHeight; y++) {
			if (transpose) {
				destOff = y;
			}
			@Pc(101) int[] red;
			@Pc(103) int[] green;
			@Pc(105) int[] blue;
			if (this.colorOp.monochrome) {
				@Pc(99) int[] mono = this.colorOp.getMonochromeOutput(y);
				red = mono;
				green = mono;
				blue = mono;
			} else {
				@Pc(113) int[][] colorOutput = this.colorOp.getColorOutput(y);
				red = colorOutput[0];
				blue = colorOutput[2];
				green = colorOutput[1];
			}
			for (@Pc(127) int x = i; x != xEnd; x += xStep) {
				@Pc(136) int r = red[x] >> 4;
				if (r > 255) {
					r = 255;
				}
				if (r < 0) {
					r = 0;
				}
				r = brightnessMap[r];
				@Pc(159) int b = blue[x] >> 4;
				@Pc(165) int g = green[x] >> 4;
				if (g > 255) {
					g = 255;
				}
				if (g < 0) {
					g = 0;
				}
				if (b > 255) {
					b = 255;
				}
				g = brightnessMap[g];
				if (b < 0) {
					b = 0;
				}
				b = brightnessMap[b];
				pixels[destOff++] = (r << 16) + (g << 8) + b;
				if (transpose) {
					destOff += texWidth - 1;
				}
			}
		}
		for (y = 0; y < this.operations.length; y++) {
			this.operations[y].clearImageCache();
		}
		return pixels;
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(IIZLclient!m;DILclient!ve;)[B")
	public final byte[] generateRgbaBytes(@OriginalArg(0) int texHeight, @OriginalArg(1) int texWidth, @OriginalArg(2) boolean transpose, @OriginalArg(3) TextureProvider texProvider, @OriginalArg(4) double brightnessValue, @OriginalArg(6) Js5 sprites) {
		@Pc(8) byte[] bytes = new byte[texWidth * 4 * texHeight];
		setBrightness(brightnessValue);
		spritesArchive = sprites;
		provider = texProvider;
		setSize(texHeight, texWidth);
		@Pc(31) int i;
		for (i = 0; i < this.operations.length; i++) {
			this.operations[i].allocateImageCache(texHeight, texWidth);
		}
		i = 0;
		@Pc(53) int y;
		for (y = 0; y < texHeight; y++) {
			if (transpose) {
				i = y << 2;
			}
			@Pc(79) int[] red;
			@Pc(81) int[] green;
			@Pc(83) int[] blue;
			@Pc(77) int[] alpha;
			if (this.colorOp.monochrome) {
				alpha = this.colorOp.getMonochromeOutput(y);
				red = alpha;
				green = alpha;
				blue = alpha;
			} else {
				@Pc(91) int[][] colorOutput = this.colorOp.getColorOutput(y);
				red = colorOutput[0];
				green = colorOutput[1];
				blue = colorOutput[2];
			}
			if (this.alphaOp.monochrome) {
				alpha = this.alphaOp.getMonochromeOutput(y);
			} else {
				alpha = this.alphaOp.getColorOutput(y)[0];
			}
			for (@Pc(127) int x = texWidth - 1; x >= 0; x--) {
				@Pc(138) int r = red[x] >> 4;
				if (r > 255) {
					r = 255;
				}
				if (r < 0) {
					r = 0;
				}
				@Pc(154) int g = green[x] >> 4;
				if (g > 255) {
					g = 255;
				}
				@Pc(167) int b = blue[x] >> 4;
				if (b > 255) {
					b = 255;
				}
				r = brightnessMap[r];
				if (b < 0) {
					b = 0;
				}
				if (g < 0) {
					g = 0;
				}
				g = brightnessMap[g];
				b = brightnessMap[b];
				@Pc(220) int a;
				if (r == 0 && g == 0 && b == 0) {
					a = 0;
				} else {
					a = alpha[x] >> 4;
					if (a > 255) {
						a = 255;
					}
					if (a < 0) {
						a = 0;
					}
				}
				bytes[i++] = (byte) r;
				bytes[i++] = (byte) g;
				bytes[i++] = (byte) b;
				bytes[i++] = (byte) a;
				if (transpose) {
					i += (texWidth << 2) - 4;
				}
			}
		}
		for (y = 0; y < this.operations.length; y++) {
			this.operations[y].clearImageCache();
		}
		return bytes;
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(ZLclient!m;Lclient!ve;)Z")
	public final boolean isReady(@OriginalArg(1) TextureProvider texProvider, @OriginalArg(2) Js5 sprites) {
		@Pc(10) int i;
		if (spriteGroupId > 0) {
			for (i = 0; i < this.requiredSpriteIds.length; i++) {
				if (!sprites.isFileReady(this.requiredSpriteIds[i], spriteGroupId)) {
					return false;
				}
			}
		} else {
			for (i = 0; i < this.requiredSpriteIds.length; i++) {
				if (!sprites.isFileReady(this.requiredSpriteIds[i])) {
					return false;
				}
			}
		}
		for (i = 0; i < this.requiredTextureIds.length; i++) {
			if (!texProvider.isTextureLoaded(this.requiredTextureIds[i])) {
				return false;
			}
		}
		return true;
	}
}
