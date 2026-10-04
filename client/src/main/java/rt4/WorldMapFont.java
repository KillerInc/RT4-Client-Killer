package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.Component;
import java.awt.Font;
import java.awt.*;
import java.awt.image.PixelGrabber;

@OriginalClass("client!fd")
public final class WorldMapFont {

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "Ljava/lang/String;")
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| " + 'Ä' + 'Ë' + 'Ï' + 'Ö' + 'Ü' + 'ä' + 'ë' + 'ï' + 'ö' + 'ü' + 'ÿ' + 'ß' + 'Á' + 'À' + 'É' + 'È' + 'Í' + 'Ì' + 'Ó' + 'Ò' + 'Ú' + 'Ù' + 'á' + 'à' + 'é' + 'è' + 'í' + 'ì' + 'ó' + 'ò' + 'ú' + 'ù' + 'Â' + 'Ê' + 'Î' + 'Ô' + 'Û' + 'â' + 'ê' + 'î' + 'ô' + 'û' + 'Æ' + 'æ';

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "I")
	public static final int ALPHABET_SIZE = ALPHABET.length();

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "[I")
	private static final int[] CHAR_INDEXES = new int[256];

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "I")
	private int dataIndex;

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "Z")
	private boolean grayscale;

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "[B")
	private byte[] data = new byte[100000];

	static {
		for (@Pc(146) int i = 0; i < 256; i++) {
			@Pc(153) int charIndex = ALPHABET.indexOf(i);
			if (charIndex == -1) {
				charIndex = 74;
			}
			CHAR_INDEXES[i] = charIndex * 9;
		}
	}

	@OriginalMember(owner = "client!fd", name = "<init>", descriptor = "(IZLjava/awt/Component;)V")
	public WorldMapFont(@OriginalArg(0) int size, @OriginalArg(1) boolean bold, @OriginalArg(2) Component component) {
		this.dataIndex = ALPHABET_SIZE * 9;
		this.grayscale = false;
		@Pc(30) Font font = new Font("Helvetica", Font.BOLD, size);
		@Pc(34) FontMetrics boldMetrics = component.getFontMetrics(font);
		@Pc(36) int i;
		for (i = 0; i < ALPHABET_SIZE; i++) {
			this.preRenderGlyph(font, boldMetrics, ALPHABET.charAt(i), i, false);
		}
		if (this.grayscale) {
			this.dataIndex = ALPHABET_SIZE * 9;
			this.grayscale = false;
			font = new Font("Helvetica", Font.PLAIN, size);
			boldMetrics = component.getFontMetrics(font);
			for (i = 0; i < ALPHABET_SIZE; i++) {
				this.preRenderGlyph(font, boldMetrics, ALPHABET.charAt(i), i, false);
			}
			if (!this.grayscale) {
				this.dataIndex = ALPHABET_SIZE * 9;
				this.grayscale = false;
				for (i = 0; i < ALPHABET_SIZE; i++) {
					this.preRenderGlyph(font, boldMetrics, ALPHABET.charAt(i), i, true);
				}
			}
		}
		@Pc(121) byte[] trimmedData = new byte[this.dataIndex];
		for (@Pc(123) int j = 0; j < this.dataIndex; j++) {
			trimmedData[j] = this.data[j];
		}
		this.data = trimmedData;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!na;IIIZ)V")
	private void renderString(@OriginalArg(0) JagString s, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) boolean shadow) {
		if (this.grayscale || color == 0) {
			shadow = false;
		}
		for (@Pc(8) int i = 0; i < s.length(); i++) {
			@Pc(20) int index = CHAR_INDEXES[s.charAt(i)];
			if (shadow) {
				this.renderGlyph(index, x + 1, y, 1, this.data);
				this.renderGlyph(index, x, y + 1, 1, this.data);
			}
			this.renderGlyph(index, x, y, color, this.data);
			x += this.data[index + 7];
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "()I")
	public final int getLineHeight() {
		return this.data[8] - 1;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "([I[BIIIIIII)V")
	private void renderGlyphGrayscale(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int color, @OriginalArg(3) int srcIndex, @OriginalArg(4) int destIndex, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int destStride, @OriginalArg(8) int srcStride) {
		for (@Pc(2) int y = -h; y < 0; y++) {
			for (@Pc(7) int x = -w; x < 0; x++) {
				@Pc(16) int intensity = src[srcIndex++] & 0xFF;
				if (intensity <= 30) {
					destIndex++;
				} else if (intensity >= 230) {
					dest[destIndex++] = color;
				} else {
					@Pc(32) int backgroundColor = dest[destIndex];
					dest[destIndex++] = ((color & 0xFF00FF) * intensity + (backgroundColor & 0xFF00FF) * (256 - intensity) & 0xFF00FF00) + ((color & 0xFF00) * intensity + (backgroundColor & 0xFF00) * (256 - intensity) & 0xFF0000) >> 8;
				}
			}
			destIndex += destStride;
			srcIndex += srcStride;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIII[B)V")
	private void renderGlyph(@OriginalArg(0) int index, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) byte[] src) {
		@Pc(7) int drawX = x + src[index + 5];
		@Pc(15) int drawY = y - src[index + 6];
		@Pc(21) int width = src[index + 3];
		@Pc(27) int height = src[index + 4];
		@Pc(47) int srcIndex = src[index] * 16384 + src[index + 1] * 128 + src[index + 2];
		@Pc(53) int destIndex = drawX + drawY * SoftwareRaster.width;
		@Pc(57) int destStride = SoftwareRaster.width - width;
		@Pc(59) int srcStride = 0;
		@Pc(66) int clip;
		if (drawY < SoftwareRaster.clipTop) {
			clip = SoftwareRaster.clipTop - drawY;
			height -= clip;
			drawY = SoftwareRaster.clipTop;
			srcIndex += clip * width;
			destIndex += clip * SoftwareRaster.width;
		}
		if (drawY + height >= SoftwareRaster.clipBottom) {
			height -= drawY + height + 1 - SoftwareRaster.clipBottom;
		}
		if (drawX < SoftwareRaster.clipLeft) {
			clip = SoftwareRaster.clipLeft - drawX;
			width -= clip;
			drawX = SoftwareRaster.clipLeft;
			srcIndex += clip;
			destIndex += clip;
			srcStride = clip;
			destStride += clip;
		}
		if (drawX + width >= SoftwareRaster.clipRight) {
			clip = drawX + width + 1 - SoftwareRaster.clipRight;
			width -= clip;
			srcStride += clip;
			destStride += clip;
		}
		if (width <= 0 || height <= 0) {
			return;
		}
		if (this.grayscale) {
			this.renderGlyphGrayscale(SoftwareRaster.pixels, src, color, srcIndex, destIndex, width, height, destStride, srcStride);
		} else {
			this.renderGlyphMono(SoftwareRaster.pixels, src, color, srcIndex, destIndex, width, height, destStride, srcStride);
		}
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "([I[BIIIIIII)V")
	private void renderGlyphMono(@OriginalArg(0) int[] dest, @OriginalArg(1) byte[] src, @OriginalArg(2) int color, @OriginalArg(3) int srcIndex, @OriginalArg(4) int destIndex, @OriginalArg(5) int w, @OriginalArg(6) int h, @OriginalArg(7) int destStride, @OriginalArg(8) int srcStride) {
		@Pc(4) int widthQuads = -(w >> 2);
		@Pc(9) int widthRemainder = -(w & 0x3);
		for (@Pc(12) int y = -h; y < 0; y++) {
			@Pc(16) int q;
			for (q = widthQuads; q < 0; q++) {
				if (src[srcIndex++] == 0) {
					destIndex++;
				} else {
					dest[destIndex++] = color;
				}
				if (src[srcIndex++] == 0) {
					destIndex++;
				} else {
					dest[destIndex++] = color;
				}
				if (src[srcIndex++] == 0) {
					destIndex++;
				} else {
					dest[destIndex++] = color;
				}
				if (src[srcIndex++] == 0) {
					destIndex++;
				} else {
					dest[destIndex++] = color;
				}
			}
			for (q = widthRemainder; q < 0; q++) {
				if (src[srcIndex++] == 0) {
					destIndex++;
				} else {
					dest[destIndex++] = color;
				}
			}
			destIndex += destStride;
			srcIndex += srcStride;
		}
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(Lclient!na;IIIZ)V")
	public final void renderStringCenter(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color) {
		@Pc(5) int halfWidth = this.getStringWidth(text) / 2;
		@Pc(8) int ascent = this.getAscent();
		if (x - halfWidth <= SoftwareRaster.clipRight && (x + halfWidth >= SoftwareRaster.clipLeft && (y - ascent <= SoftwareRaster.clipBottom && y >= 0))) {
			this.renderString(text, x - halfWidth, y, color, true);
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Ljava/awt/Font;Ljava/awt/FontMetrics;CIZ)V")
	private void preRenderGlyph(@OriginalArg(0) Font font, @OriginalArg(1) FontMetrics metrics, @OriginalArg(2) char ch, @OriginalArg(3) int glyphIndex, @OriginalArg(4) boolean doubleRender) {
		@Pc(3) int charWidth = metrics.charWidth(ch);
		@Pc(5) int advanceWidth = charWidth;
		if (doubleRender) {
			try {
				if (ch == '/') {
					doubleRender = false;
				}
				if (ch == 'f' || ch == 't' || ch == 'w' || ch == 'v' || ch == 'k' || ch == 'x' || ch == 'y' || ch == 'A' || ch == 'V' || ch == 'W') {
					charWidth++;
				}
			} catch (@Pc(45) Exception ignored) {
			}
		}
		@Pc(48) int maxAscent = metrics.getMaxAscent();
		@Pc(54) int totalHeight = metrics.getMaxAscent() + metrics.getMaxDescent();
		@Pc(57) int lineHeight = metrics.getHeight();
		@Pc(62) Image img = GameShell.canvas.createImage(charWidth, totalHeight);
		@Pc(65) Graphics g = img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, charWidth, totalHeight);
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(ch + "", 0, maxAscent);
		if (doubleRender) {
			g.drawString(ch + "", 1, maxAscent);
		}
		@Pc(111) int[] pixels = new int[charWidth * totalHeight];
		@Pc(123) PixelGrabber grabber = new PixelGrabber(img, 0, 0, charWidth, totalHeight, pixels, 0, charWidth);
		try {
			grabber.grabPixels();
		} catch (@Pc(128) Exception ignored) {
		}
		img.flush();
		@Pc(134) int minX = 0;
		@Pc(136) int minY = 0;
		@Pc(138) int maxX = charWidth;
		@Pc(140) int maxY = totalHeight;
		@Pc(142) int row;
		@Pc(147) int col;
		@Pc(158) int pixel;
		findMinY:
		for (row = 0; row < totalHeight; row++) {
			for (col = 0; col < charWidth; col++) {
				pixel = pixels[col + row * charWidth];
				if ((pixel & 0xFFFFFF) != 0) {
					minY = row;
					break findMinY;
				}
			}
		}
		findMinX:
		for (row = 0; row < charWidth; row++) {
			for (col = 0; col < totalHeight; col++) {
				pixel = pixels[row + col * charWidth];
				if ((pixel & 0xFFFFFF) != 0) {
					minX = row;
					break findMinX;
				}
			}
		}
		findMaxY:
		for (row = totalHeight - 1; row >= 0; row--) {
			for (col = 0; col < charWidth; col++) {
				pixel = pixels[col + row * charWidth];
				if ((pixel & 0xFFFFFF) != 0) {
					maxY = row + 1;
					break findMaxY;
				}
			}
		}
		findMaxX:
		for (row = charWidth - 1; row >= 0; row--) {
			for (col = 0; col < totalHeight; col++) {
				pixel = pixels[row + col * charWidth];
				if ((pixel & 0xFFFFFF) != 0) {
					maxX = row + 1;
					break findMaxX;
				}
			}
		}
		this.data[glyphIndex * 9] = (byte) (this.dataIndex / 16384);
		this.data[glyphIndex * 9 + 1] = (byte) (this.dataIndex / 128 & 0x7F);
		this.data[glyphIndex * 9 + 2] = (byte) (this.dataIndex & 0x7F);
		this.data[glyphIndex * 9 + 3] = (byte) (maxX - minX);
		this.data[glyphIndex * 9 + 4] = (byte) (maxY - minY);
		this.data[glyphIndex * 9 + 5] = (byte) minX;
		this.data[glyphIndex * 9 + 6] = (byte) (maxAscent - minY);
		this.data[glyphIndex * 9 + 7] = (byte) advanceWidth;
		this.data[glyphIndex * 9 + 8] = (byte) lineHeight;
		for (row = minY; row < maxY; row++) {
			for (col = minX; col < maxX; col++) {
				pixel = pixels[col + row * charWidth] & 0xFF;
				if (pixel > 30 && pixel < 230) {
					this.grayscale = true;
				}
				this.data[this.dataIndex++] = (byte) pixel;
			}
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!na;)I")
	private int getStringWidth(@OriginalArg(0) JagString text) {
		@Pc(1) int width = 0;
		for (@Pc(3) int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == 64 && i + 4 < text.length() && text.charAt(i + 4) == 64) {
				i += 4;
			} else if (text.charAt(i) == 126 && i + 4 < text.length() && text.charAt(i + 4) == 126) {
				i += 4;
			} else {
				width += this.data[CHAR_INDEXES[text.charAt(i)] + 7];
			}
		}
		return width;
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "()I")
	public final int getAscent() {
		return this.data[6];
	}
}
