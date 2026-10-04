package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.Random;

@OriginalClass("client!rk")
public abstract class Font extends SecondaryNode {

	@OriginalMember(owner = "client!rk", name = "K", descriptor = "Lclient!na;")
	public static final JagString TAG_GT = JagString.parse("gt");
	@OriginalMember(owner = "client!rk", name = "L", descriptor = "Lclient!na;")
	public static final JagString TAG_LT = JagString.parse("lt");
	@OriginalMember(owner = "client!rk", name = "M", descriptor = "Lclient!na;")
	public static final JagString TAG_SHAD1 = JagString.parse("shad=");
	@OriginalMember(owner = "client!rk", name = "N", descriptor = "Lclient!na;")
	public static final JagString TAB_SHY = JagString.parse("shy");
	@OriginalMember(owner = "client!rk", name = "O", descriptor = "Lclient!na;")
	public static final JagString TAG_TRANS1 = JagString.parse("trans=");
	@OriginalMember(owner = "client!rk", name = "P", descriptor = "Lclient!na;")
	public static final JagString TAG_U1 = JagString.parse("u=");
	@OriginalMember(owner = "client!rk", name = "Q", descriptor = "Lclient!na;")
	public static final JagString TAG_STR1 = JagString.parse("str=");
	@OriginalMember(owner = "client!rk", name = "R", descriptor = "Lclient!na;")
	public static final JagString TAB_EURO = JagString.parse("euro");
	@OriginalMember(owner = "client!rk", name = "T", descriptor = "Lclient!na;")
	public static final JagString TAG_COL2 = JagString.parse(")4col");
	@OriginalMember(owner = "client!rk", name = "X", descriptor = "Lclient!na;")
	public static final JagString TAG_SHAD3 = JagString.parse(")4shad");
	@OriginalMember(owner = "client!rk", name = "Y", descriptor = "Lclient!na;")
	public static final JagString TAG_COL1 = JagString.parse("col=");
	@OriginalMember(owner = "client!rk", name = "bb", descriptor = "Lclient!na;")
	public static final JagString TAG_U2 = JagString.parse("u");
	@OriginalMember(owner = "client!rk", name = "cb", descriptor = "Lclient!na;")
	public static final JagString TAB_TIMES = JagString.parse("times");
	@OriginalMember(owner = "client!rk", name = "eb", descriptor = "Lclient!na;")
	public static final JagString TAG_TRANS2 = JagString.parse(")4trans");
	@OriginalMember(owner = "client!rk", name = "fb", descriptor = "Lclient!na;")
	public static final JagString TAG_NBSP = JagString.parse("nbsp");
	@OriginalMember(owner = "client!rk", name = "ib", descriptor = "Lclient!na;")
	public static final JagString TAG_U3 = JagString.parse(")4u");
	@OriginalMember(owner = "client!rk", name = "kb", descriptor = "Lclient!na;")
	public static final JagString TAG_BR = JagString.parse("br");
	@OriginalMember(owner = "client!rk", name = "lb", descriptor = "Lclient!na;")
	public static final JagString TAG_SHAD2 = JagString.parse("shad");
	@OriginalMember(owner = "client!rk", name = "mb", descriptor = "Lclient!na;")
	public static final JagString TAG_IMG = JagString.parse("img=");
	@OriginalMember(owner = "client!rk", name = "ob", descriptor = "Lclient!na;")
	public static final JagString TAB_COPY = JagString.parse("copy");
	@OriginalMember(owner = "client!rk", name = "pb", descriptor = "Lclient!na;")
	public static final JagString TAG_STR3 = JagString.parse(")4str");
	@OriginalMember(owner = "client!rk", name = "qb", descriptor = "Lclient!na;")
	public static final JagString TAG_REG = JagString.parse("reg");
	@OriginalMember(owner = "client!rk", name = "rb", descriptor = "Lclient!na;")
	public static final JagString TAG_STR2 = JagString.parse("str");
	@OriginalMember(owner = "client!rk", name = "sb", descriptor = "Lclient!na;")
	public static final JagString buffer = JagString.allocate(100);
	@OriginalMember(owner = "client!rk", name = "Ab", descriptor = "[Lclient!na;")
	public static final JagString[] lines = new JagString[100];
	@OriginalMember(owner = "client!rk", name = "Z", descriptor = "Lclient!na;")
	private static final JagString ESCAPED_GT = JagString.parse("<gt>");
	@OriginalMember(owner = "client!rk", name = "hb", descriptor = "Lclient!na;")
	private static final JagString ESCAPED_LT = JagString.parse("<lt>");
	@OriginalMember(owner = "client!rk", name = "tb", descriptor = "I")
	public static int strikethroughColor = -1;
	@OriginalMember(owner = "client!rk", name = "ub", descriptor = "I")
	public static int underlineColor = -1;
	@OriginalMember(owner = "client!rk", name = "vb", descriptor = "I")
	public static int extraSpaceWidth = 0;
	@OriginalMember(owner = "client!rk", name = "wb", descriptor = "I")
	public static int color = 0;
	@OriginalMember(owner = "client!rk", name = "xb", descriptor = "I")
	public static int alphaOverride = 256;
	@OriginalMember(owner = "client!rk", name = "yb", descriptor = "I")
	public static int alpha = 256;
	@OriginalMember(owner = "client!rk", name = "zb", descriptor = "I")
	public static int shadowColor = -1;
	@OriginalMember(owner = "client!rk", name = "Bb", descriptor = "I")
	public static int shadowColorOverride = -1;
	@OriginalMember(owner = "client!rk", name = "Cb", descriptor = "I")
	public static int colorOverride = 0;
	@OriginalMember(owner = "client!rk", name = "Db", descriptor = "I")
	public static int spaceWidth = 0;
	@OriginalMember(owner = "client!rk", name = "W", descriptor = "[I")
	private int[] nameIconHeights;

	@OriginalMember(owner = "client!rk", name = "gb", descriptor = "[B")
	private byte[] kerning;

	@OriginalMember(owner = "client!rk", name = "jb", descriptor = "[I")
	private int[] glyphWidths;

	@OriginalMember(owner = "client!rk", name = "nb", descriptor = "[Lclient!ok;")
	private IndexedSprite[] nameIcons;

	@OriginalMember(owner = "client!rk", name = "ab", descriptor = "I")
	public int lineHeight = 0;

	@OriginalMember(owner = "client!rk", name = "S", descriptor = "[I")
	private int[] spriteXOffsets;

	@OriginalMember(owner = "client!rk", name = "db", descriptor = "[I")
	private int[] spriteYOffsets;

	@OriginalMember(owner = "client!rk", name = "I", descriptor = "[I")
	protected int[] spriteInnerWidths;

	@OriginalMember(owner = "client!rk", name = "U", descriptor = "[I")
	protected int[] spriteInnerHeights;

	@OriginalMember(owner = "client!rk", name = "V", descriptor = "I")
	private int paragraphTopPadding;

	@OriginalMember(owner = "client!rk", name = "J", descriptor = "I")
	private int paragraphBottomPadding;

	@OriginalMember(owner = "client!rk", name = "<init>", descriptor = "([B[I[I[I[I)V")
	protected Font(@OriginalArg(0) byte[] data, @OriginalArg(1) int[] xOffsets, @OriginalArg(2) int[] yOffsets, @OriginalArg(3) int[] innerWidths, @OriginalArg(4) int[] innerHeights) {
		this.spriteXOffsets = xOffsets;
		this.spriteYOffsets = yOffsets;
		this.spriteInnerWidths = innerWidths;
		this.spriteInnerHeights = innerHeights;
		this.decode(data);
		@Pc(21) int minTop = Integer.MAX_VALUE;
		@Pc(23) int maxBottom = Integer.MIN_VALUE;
		for (@Pc(25) int i = 0; i < 256; i++) {
			if (this.spriteYOffsets[i] < minTop && this.spriteInnerHeights[i] != 0) {
				minTop = this.spriteYOffsets[i];
			}
			if (this.spriteYOffsets[i] + this.spriteInnerHeights[i] > maxBottom) {
				maxBottom = this.spriteYOffsets[i] + this.spriteInnerHeights[i];
			}
		}
		this.paragraphTopPadding = this.lineHeight - minTop;
		this.paragraphBottomPadding = maxBottom - this.lineHeight;
	}

	@OriginalMember(owner = "client!rk", name = "<init>", descriptor = "([B)V")
	public Font(@OriginalArg(0) byte[] data) {
		this.decode(data);
	}

	@OriginalMember(owner = "client!rk", name = "c", descriptor = "(Lclient!na;)Lclient!na;")
	public static JagString escape(@OriginalArg(0) JagString str) {
		@Pc(3) int len = str.length();
		@Pc(5) int extra = 0;
		@Pc(15) int i;
		for (@Pc(7) int j = 0; j < len; j++) {
			i = str.charAt(j);
			if (i == 60 || i == 62) {
				extra += 3;
			}
		}
		@Pc(30) JagString result = JagString.allocate(len + extra);
		for (i = 0; i < len; i++) {
			@Pc(40) int ch = str.charAt(i);
			if (ch == 60) {
				result.appendString(ESCAPED_LT);
			} else if (ch == 62) {
				result.appendString(ESCAPED_GT);
			} else {
				result.append(ch);
			}
		}
		return result;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "([[B[[B[I[I[III)I")
	public static int calculateKerning(@OriginalArg(0) byte[][] leftGlyphs, @OriginalArg(1) byte[][] rightGlyphs, @OriginalArg(2) int[] offsets, @OriginalArg(3) int[] widths, @OriginalArg(4) int[] heights, @OriginalArg(5) int leftIdx, @OriginalArg(6) int rightIdx) {
		@Pc(3) int leftTop = offsets[leftIdx];
		@Pc(9) int leftBottom = leftTop + heights[leftIdx];
		@Pc(13) int rightTop = offsets[rightIdx];
		@Pc(19) int rightBottom = rightTop + heights[rightIdx];
		@Pc(21) int top = leftTop;
		if (rightTop > leftTop) {
			top = rightTop;
		}
		@Pc(28) int bottom = leftBottom;
		if (rightBottom < leftBottom) {
			bottom = rightBottom;
		}
		@Pc(37) int minKern = widths[leftIdx];
		if (widths[rightIdx] < minKern) {
			minKern = widths[rightIdx];
		}
		@Pc(50) byte[] leftRow = rightGlyphs[leftIdx];
		@Pc(54) byte[] rightRow = leftGlyphs[rightIdx];
		@Pc(58) int leftOff = top - leftTop;
		@Pc(62) int rightOff = top - rightTop;
		for (@Pc(64) int y = top; y < bottom; y++) {
			@Pc(77) int kern = leftRow[leftOff++] + rightRow[rightOff++];
			if (kern < minKern) {
				minKern = kern;
			}
		}
		return -minKern;
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(I[B)Lclient!rk;")
	public static Font createFont(@OriginalArg(1) byte[] data) {
		if (data == null) {
			return null;
		}
		@Pc(27) Font font;
		if (GlRenderer.enabled) {
			font = new GlFont(data, SpriteLoader.xOffsets, SpriteLoader.yOffsets, SpriteLoader.innerWidths, SpriteLoader.innerHeights, SpriteLoader.pixels);
		} else {
			font = new SoftwareFont(data, SpriteLoader.xOffsets, SpriteLoader.yOffsets, SpriteLoader.innerWidths, SpriteLoader.innerHeights, SpriteLoader.pixels);
		}
		SpriteLoader.clear();
		return font;
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(IIBLclient!ve;Lclient!ve;)Lclient!rk;")
	public static Font load(@OriginalArg(1) int fontId, @OriginalArg(3) Js5 spriteJs5, @OriginalArg(4) Js5 fontJs5) {
		return SpriteLoader.decode(spriteJs5, 0, fontId) ? createFont(fontJs5.fetchFile(fontId, 0)) : null;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIIIIIII)I")
	public final int drawInterfaceText(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int w, @OriginalArg(4) int h, @OriginalArg(5) int color, @OriginalArg(6) int shadow, @OriginalArg(7) int alpha, @OriginalArg(8) int valign, @OriginalArg(9) int halign) {
		return this.renderParagraphAlpha(text, x, y, w, h, color, shadow, alpha, valign, halign);
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;II)V")
	private void render(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y) {
		@Pc(4) int baseY = y - this.lineHeight;
		@Pc(6) int tagStart = -1;
		@Pc(8) int prevChar = 0;
		@Pc(12) int len = text.length();
		for (@Pc(14) int i = 0; i < len; i++) {
			@Pc(22) int ch = text.charAt(i);
			if (ch == 60) {
				tagStart = i;
			} else {
				@Pc(120) int iconIdx;
				if (ch == 62 && tagStart != -1) {
					@Pc(42) JagString tag = text.substring(i, tagStart + 1);
					tagStart = -1;
					if (tag.strEquals(TAG_LT)) {
						ch = 60;
					} else if (tag.strEquals(TAG_GT)) {
						ch = 62;
					} else if (tag.strEquals(TAG_NBSP)) {
						ch = 160;
					} else if (tag.strEquals(TAB_SHY)) {
						ch = 173;
					} else if (tag.strEquals(TAB_TIMES)) {
						ch = 215;
					} else if (tag.strEquals(TAB_EURO)) {
						ch = 128;
					} else if (tag.strEquals(TAB_COPY)) {
						ch = 169;
					} else {
						if (!tag.strEquals(TAG_REG)) {
							if (tag.startsWith(TAG_IMG)) {
								try {
									iconIdx = tag.substring(4).parseInt();
									@Pc(125) IndexedSprite icon = this.nameIcons[iconIdx];
									@Pc(136) int iconHeight = this.nameIconHeights == null ? icon.innerHeight : this.nameIconHeights[iconIdx];
									if (alphaOverride == 256) {
										icon.renderTransparent(x, baseY + this.lineHeight - iconHeight);
									} else {
										icon.renderAlpha(x, baseY + this.lineHeight - iconHeight, alphaOverride);
									}
									x += icon.innerWidth;
									prevChar = 0;
								} catch (@Pc(168) Exception ex) {
								}
							} else {
								this.parseTag(tag);
							}
							continue;
						}
						ch = 174;
					}
				}
				if (tagStart == -1) {
					if (this.kerning != null && prevChar != 0) {
						x += this.kerning[(prevChar << 8) + ch];
					}
					@Pc(197) int glyphW = this.spriteInnerWidths[ch];
					iconIdx = this.spriteInnerHeights[ch];
					if (ch == 32) {
						if (spaceWidth > 0) {
							extraSpaceWidth += spaceWidth;
							x += extraSpaceWidth >> 8;
							extraSpaceWidth &= 0xFF;
						}
					} else if (alphaOverride == 256) {
						if (shadowColorOverride != -1) {
							this.renderGlyph(ch, x + this.spriteXOffsets[ch] + 1, baseY + this.spriteYOffsets[ch] + 1, glyphW, iconIdx, shadowColorOverride);
						}
						this.renderGlyph(ch, x + this.spriteXOffsets[ch], baseY + this.spriteYOffsets[ch], glyphW, iconIdx, colorOverride);
					} else {
						if (shadowColorOverride != -1) {
							this.renderGlyphTransparent(ch, x + this.spriteXOffsets[ch] + 1, baseY + this.spriteYOffsets[ch] + 1, glyphW, iconIdx, shadowColorOverride, alphaOverride);
						}
						this.renderGlyphTransparent(ch, x + this.spriteXOffsets[ch], baseY + this.spriteYOffsets[ch], glyphW, iconIdx, colorOverride, alphaOverride);
					}
					@Pc(323) int advanceW = this.glyphWidths[ch];
					if (strikethroughColor != -1) {
						if (GlRenderer.enabled) {
							GlRaster.drawHorizontalLine(x, baseY + (int) ((double) this.lineHeight * 0.7D), advanceW, strikethroughColor);
						} else {
							SoftwareRaster.drawHorizontalLine(x, baseY + (int) ((double) this.lineHeight * 0.7D), advanceW, strikethroughColor);
						}
					}
					if (underlineColor != -1) {
						if (GlRenderer.enabled) {
							GlRaster.drawHorizontalLine(x, baseY + this.lineHeight + 1, advanceW, underlineColor);
						} else {
							SoftwareRaster.drawHorizontalLine(x, baseY + this.lineHeight + 1, advanceW, underlineColor);
						}
					}
					x += advanceW;
					prevChar = ch;
				}
			}
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(IIIIIIZ)V")
	protected abstract void renderGlyph(@OriginalArg(0) int glyph, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int w, @OriginalArg(4) int h, @OriginalArg(5) int color);

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(IIIIIIIZ)V")
	protected abstract void renderGlyphTransparent(@OriginalArg(0) int glyph, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int w, @OriginalArg(4) int h, @OriginalArg(5) int color, @OriginalArg(6) int alpha);

	@OriginalMember(owner = "client!rk", name = "b", descriptor = "(Lclient!na;I)I")
	public final int getMaxLineWidth(@OriginalArg(0) JagString text, @OriginalArg(1) int maxWidth) {
		@Pc(10) int lineCount = this.splitParagraph(text, new int[]{maxWidth}, lines);
		@Pc(12) int maxW = 0;
		for (@Pc(14) int i = 0; i < lineCount; i++) {
			@Pc(23) int w = this.getStringWidth(lines[i]);
			if (w > maxW) {
				maxW = w;
			}
		}
		return maxW;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIII)V")
	public final void renderLeft(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) int shadow) {
		if (text != null) {
			this.setColors(color, shadow);
			this.render(text, x, y);
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;)I")
	public final int getStringWidth(@OriginalArg(0) JagString text) {
		if (text == null) {
			return 0;
		}
		@Pc(5) int tagStart = -1;
		@Pc(7) int prevChar = 0;
		@Pc(9) int totalWidth = 0;
		@Pc(13) int len = text.length();
		for (@Pc(15) int i = 0; i < len; i++) {
			@Pc(23) int ch = text.charAt(i);
			if (ch == 60) {
				tagStart = i;
			} else {
				if (ch == 62 && tagStart != -1) {
					@Pc(43) JagString tag = text.substring(i, tagStart + 1);
					tagStart = -1;
					if (tag.strEquals(TAG_LT)) {
						ch = 60;
					} else if (tag.strEquals(TAG_GT)) {
						ch = 62;
					} else if (tag.strEquals(TAG_NBSP)) {
						ch = 160;
					} else if (tag.strEquals(TAB_SHY)) {
						ch = 173;
					} else if (tag.strEquals(TAB_TIMES)) {
						ch = 215;
					} else if (tag.strEquals(TAB_EURO)) {
						ch = 128;
					} else if (tag.strEquals(TAB_COPY)) {
						ch = 169;
					} else {
						if (!tag.strEquals(TAG_REG)) {
							if (tag.startsWith(TAG_IMG)) {
								try {
									@Pc(121) int iconIdx = tag.substring(4).parseInt();
									totalWidth += this.nameIcons[iconIdx].innerWidth;
									prevChar = 0;
								} catch (@Pc(133) Exception ex) {
								}
							}
							continue;
						}
						ch = 174;
					}
				}
				if (tagStart == -1) {
					totalWidth += this.glyphWidths[ch];
					if (this.kerning != null && prevChar != 0) {
						totalWidth += this.kerning[(prevChar << 8) + ch];
					}
					prevChar = ch;
				}
			}
		}
		return totalWidth;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIILjava/util/Random;I)I")
	public final int renderWavyText(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(5) Random random, @OriginalArg(6) int seed) {
		if (text == null) {
			return 0;
		}
		random.setSeed(seed);
		this.setColors(16777215, 0, (random.nextInt() & 0x1F) + 192);
		@Pc(21) int len = text.length();
		@Pc(24) int[] yOffsets = new int[len];
		@Pc(26) int maxOffset = 0;
		for (@Pc(28) int i = 0; i < len; i++) {
			yOffsets[i] = maxOffset;
			if ((random.nextInt() & 0x3) == 0) {
				maxOffset++;
			}
		}
		this.renderOffset(text, x, y, yOffsets, null);
		return maxOffset;
	}

	@OriginalMember(owner = "client!rk", name = "c", descriptor = "(Lclient!na;I)I")
	public final int getParagraphLineCount(@OriginalArg(0) JagString text, @OriginalArg(1) int width) {
		return this.splitParagraph(text, new int[]{width}, lines);
	}

	@OriginalMember(owner = "client!rk", name = "b", descriptor = "(Lclient!na;)V")
	private void parseTag(@OriginalArg(0) JagString tag) {
		try {
			if (tag.startsWith(TAG_COL1)) {
				colorOverride = tag.substring(4).parseHexString(16);
			} else if (tag.strEquals(TAG_COL2)) {
				colorOverride = color;
			} else if (tag.startsWith(TAG_TRANS1)) {
				alphaOverride = tag.substring(6).parseInt();
			} else if (tag.strEquals(TAG_TRANS2)) {
				alphaOverride = alpha;
			} else if (tag.startsWith(TAG_STR1)) {
				strikethroughColor = tag.substring(4).parseHexString(16);
			} else if (tag.strEquals(TAG_STR2)) {
				strikethroughColor = 0x800000;
			} else if (tag.strEquals(TAG_STR3)) {
				strikethroughColor = -1;
			} else if (tag.startsWith(TAG_U1)) {
				underlineColor = tag.substring(2).parseHexString(16);
			} else if (tag.strEquals(TAG_U2)) {
				underlineColor = 0;
			} else if (tag.strEquals(TAG_U3)) {
				underlineColor = -1;
			} else if (tag.startsWith(TAG_SHAD1)) {
				shadowColorOverride = tag.substring(5).parseHexString(16);
			} else if (tag.strEquals(TAG_SHAD2)) {
				shadowColorOverride = 0;
			} else if (tag.strEquals(TAG_SHAD3)) {
				shadowColorOverride = shadowColor;
			} else if (tag.strEquals(TAG_BR)) {
				this.setColors(color, shadowColor, alpha);
			}
		} catch (@Pc(144) Exception ex) {
		}
	}

	@OriginalMember(owner = "client!rk", name = "d", descriptor = "(I)I")
	private int getGlyphWidth(@OriginalArg(0) int ch) {
		return this.glyphWidths[ch & 0xFF];
	}

	@OriginalMember(owner = "client!rk", name = "b", descriptor = "(Lclient!na;IIII)V")
	public final void renderRight(@OriginalArg(0) JagString string, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) int shadow) {
		if (string != null) {
			this.setColors(color, shadow);
			this.render(string, x - this.getStringWidth(string), y);
		}
	}

	@OriginalMember(owner = "client!rk", name = "d", descriptor = "(Lclient!na;I)V")
	private void justify(@OriginalArg(0) JagString text, @OriginalArg(1) int lineWidth) {
		@Pc(1) int spaceCount = 0;
		@Pc(3) boolean inTag = false;
		@Pc(7) int len = text.length();
		for (@Pc(9) int i = 0; i < len; i++) {
			@Pc(17) int ch = text.charAt(i);
			if (ch == 60) {
				inTag = true;
			} else if (ch == 62) {
				inTag = false;
			} else if (!inTag && ch == 32) {
				spaceCount++;
			}
		}
		if (spaceCount > 0) {
			spaceWidth = (lineWidth - this.getStringWidth(text) << 8) / spaceCount;
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;[I[Lclient!na;)I")
	public final int splitParagraph(@OriginalArg(0) JagString text, @OriginalArg(1) int[] widths, @OriginalArg(2) JagString[] result) {
		if (text == null) {
			return 0;
		}
		buffer.setLength(0);
		@Pc(9) int lineWidth = 0;
		@Pc(11) int lineStart = 0;
		@Pc(13) int breakPos = -1;
		@Pc(15) int breakWidth = 0;
		@Pc(17) byte breakTrim = 0;
		@Pc(19) int tagStart = -1;
		@Pc(21) int prevChar = 0;
		@Pc(23) int lineIdx = 0;
		@Pc(27) int len = text.length();
		for (@Pc(29) int i = 0; i < len; i++) {
			@Pc(37) int ch = text.charAt(i);
			if (ch == 60) {
				tagStart = i;
			} else {
				if (ch == 62 && tagStart != -1) {
					@Pc(57) JagString tag = text.substring(i, tagStart + 1);
					tagStart = -1;
					buffer.append(60);
					buffer.appendString(tag);
					buffer.append(62);
					if (tag.strEquals(TAG_BR)) {
						if (result[lineIdx] == null) {
							result[lineIdx] = buffer.asString().substring(buffer.length(), lineStart);
						} else {
							result[lineIdx].setLength(0);
							result[lineIdx].appendSubstring(buffer, lineStart, buffer.length());
						}
						lineIdx++;
						lineStart = buffer.length();
						lineWidth = 0;
						breakPos = -1;
						prevChar = 0;
					} else if (tag.strEquals(TAG_LT)) {
						lineWidth += this.getGlyphWidth(60);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 60];
						}
						prevChar = 60;
					} else if (tag.strEquals(TAG_GT)) {
						lineWidth += this.getGlyphWidth(62);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 62];
						}
						prevChar = 62;
					} else if (tag.strEquals(TAG_NBSP)) {
						lineWidth += this.getGlyphWidth(160);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 160];
						}
						prevChar = 160;
					} else if (tag.strEquals(TAB_SHY)) {
						lineWidth += this.getGlyphWidth(173);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 173];
						}
						prevChar = 173;
					} else if (tag.strEquals(TAB_TIMES)) {
						lineWidth += this.getGlyphWidth(215);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 215];
						}
						prevChar = 215;
					} else if (tag.strEquals(TAB_EURO)) {
						lineWidth += this.getGlyphWidth(128);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 128];
						}
						prevChar = 128;
					} else if (tag.strEquals(TAB_COPY)) {
						lineWidth += this.getGlyphWidth(169);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 169];
						}
						prevChar = 169;
					} else if (tag.strEquals(TAG_REG)) {
						lineWidth += this.getGlyphWidth(174);
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + 174];
						}
						prevChar = 174;
					} else if (tag.startsWith(TAG_IMG)) {
						try {
							@Pc(377) int iconIdx = tag.substring(4).parseInt();
							lineWidth += this.nameIcons[iconIdx].innerWidth;
							prevChar = 0;
						} catch (@Pc(389) Exception ex) {
						}
					}
					ch = 0;
				}
				if (tagStart == -1) {
					if (ch != 0) {
						buffer.append(ch);
						lineWidth += this.glyphWidths[ch];
						if (this.kerning != null && prevChar != 0) {
							lineWidth += this.kerning[(prevChar << 8) + ch];
						}
						prevChar = ch;
					}
					if (ch == 32) {
						breakPos = buffer.length();
						breakWidth = lineWidth;
						breakTrim = 1;
					}
					if (widths != null && lineWidth > widths[lineIdx < widths.length ? lineIdx : widths.length - 1] && breakPos >= 0) {
						if (result[lineIdx] == null) {
							result[lineIdx] = buffer.asString().substring(breakPos - breakTrim, lineStart);
						} else {
							result[lineIdx].setLength(0);
							result[lineIdx] = result[lineIdx].appendSubstring(buffer, lineStart, breakPos - breakTrim);
						}
						lineIdx++;
						lineStart = breakPos;
						breakPos = -1;
						lineWidth -= breakWidth;
						prevChar = 0;
					}
					if (ch == 45) {
						breakPos = buffer.length();
						breakWidth = lineWidth;
						breakTrim = 0;
					}
				}
			}
		}
		if (buffer.length() > lineStart) {
			if (result[lineIdx] == null) {
				result[lineIdx] = buffer.asString().substring(buffer.length(), lineStart);
			} else {
				result[lineIdx].setLength(0);
				result[lineIdx] = result[lineIdx].appendSubstring(buffer, lineStart, buffer.length());
			}
			lineIdx++;
		}
		return lineIdx;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIIII)V")
	public final void renderShake(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(5) int tick, @OriginalArg(6) int amplitude) {
		if (text == null) {
			return;
		}
		this.setColors(color, 0);
		@Pc(13) double wave = 7.0D - (double) amplitude / 8.0D;
		if (wave < 0.0D) {
			wave = 0.0D;
		}
		@Pc(23) int len = text.length();
		@Pc(26) int[] yOffsets = new int[len];
		for (@Pc(28) int i = 0; i < len; i++) {
			yOffsets[i] = (int) (Math.sin((double) i / 1.5D + (double) tick / 1.0D) * wave);
		}
		this.renderOffset(text, x - this.getStringWidth(text) / 2, y, null, yOffsets);
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIIIIIIII)I")
	public final int renderParagraphAlpha(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int w, @OriginalArg(4) int h, @OriginalArg(5) int color, @OriginalArg(6) int shadow, @OriginalArg(8) int halign, @OriginalArg(9) int valign, @OriginalArg(10) int lineSpacing) {
		if (text == null) {
			return 0;
		}
		this.setColors(color, shadow, 256);
		if (lineSpacing == 0) {
			lineSpacing = this.lineHeight;
		}
		@Pc(20) int[] lineWidths = new int[]{w};
		if (h < this.paragraphTopPadding + this.paragraphBottomPadding + lineSpacing && h < lineSpacing + lineSpacing) {
			lineWidths = null;
		}
		@Pc(42) int lineCount = this.splitParagraph(text, lineWidths, lines);
		if (valign == 3 && lineCount == 1) {
			valign = 1;
		}
		@Pc(57) int curY;
		@Pc(118) int i;
		if (valign == 0) {
			curY = y + this.paragraphTopPadding;
		} else if (valign == 1) {
			curY = y + this.paragraphTopPadding + (h - this.paragraphTopPadding - this.paragraphBottomPadding - (lineCount - 1) * lineSpacing) / 2;
		} else if (valign == 2) {
			curY = y + h - this.paragraphBottomPadding - (lineCount - 1) * lineSpacing;
		} else {
			i = (h - this.paragraphTopPadding - this.paragraphBottomPadding - (lineCount - 1) * lineSpacing) / (lineCount + 1);
			if (i < 0) {
				i = 0;
			}
			curY = y + this.paragraphTopPadding + i;
			lineSpacing += i;
		}
		for (i = 0; i < lineCount; i++) {
			if (halign == 0) {
				this.render(lines[i], x, curY);
			} else if (halign == 1) {
				this.render(lines[i], x + (w - this.getStringWidth(lines[i])) / 2, curY);
			} else if (halign == 2) {
				this.render(lines[i], x + w - this.getStringWidth(lines[i]), curY);
			} else if (i == lineCount - 1) {
				this.render(lines[i], x, curY);
			} else {
				this.justify(lines[i], w);
				this.render(lines[i], x, curY);
				spaceWidth = 0;
			}
			curY += lineSpacing;
		}
		return lineCount;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIII)V")
	public final void renderWave2(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(5) int tick) {
		if (text == null) {
			return;
		}
		this.setColors(color, 0);
		@Pc(10) int len = text.length();
		@Pc(13) int[] xOffsets = new int[len];
		@Pc(16) int[] yOffsets = new int[len];
		for (@Pc(18) int i = 0; i < len; i++) {
			xOffsets[i] = (int) (Math.sin((double) i / 5.0D + (double) tick / 5.0D) * 5.0D);
			yOffsets[i] = (int) (Math.sin((double) i / 3.0D + (double) tick / 5.0D) * 5.0D);
		}
		this.renderOffset(text, x - this.getStringWidth(text) / 2, y, xOffsets, yOffsets);
	}

	@OriginalMember(owner = "client!rk", name = "b", descriptor = "(Lclient!na;IIIII)V")
	public final void renderWave(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(5) int tick) {
		if (text == null) {
			return;
		}
		this.setColors(color, 0);
		@Pc(10) int len = text.length();
		@Pc(13) int[] yOffsets = new int[len];
		for (@Pc(15) int i = 0; i < len; i++) {
			yOffsets[i] = (int) (Math.sin((double) i / 2.0D + (double) tick / 5.0D) * 5.0D);
		}
		this.renderOffset(text, x - this.getStringWidth(text) / 2, y, null, yOffsets);
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "([Lclient!ok;[I)V")
	public final void setNameIcons(@OriginalArg(0) IndexedSprite[] icons, @OriginalArg(1) int[] heights) {
		if (heights != null && heights.length != icons.length) {
			throw new IllegalArgumentException();
		}
		this.nameIcons = icons;
		this.nameIconHeights = heights;
	}

	@OriginalMember(owner = "client!rk", name = "c", descriptor = "(II)V")
	private void setColors(@OriginalArg(0) int rgb, @OriginalArg(1) int shadow) {
		strikethroughColor = -1;
		underlineColor = -1;
		shadowColor = shadow;
		shadowColorOverride = shadow;
		color = rgb;
		colorOverride = rgb;
		alpha = 256;
		alphaOverride = 256;
		spaceWidth = 0;
		extraSpaceWidth = 0;
	}

	@OriginalMember(owner = "client!rk", name = "c", descriptor = "(Lclient!na;IIII)V")
	public final void renderCenter(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) int shadow) {
		if (text != null) {
			this.setColors(color, shadow);
			this.render(text, x - this.getStringWidth(text) / 2, y);
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "([B)V")
	private void decode(@OriginalArg(0) byte[] data) {
		this.glyphWidths = new int[256];
		@Pc(9) int pos;
		if (data.length == 257) {
			for (pos = 0; pos < this.glyphWidths.length; pos++) {
				this.glyphWidths[pos] = data[pos] & 0xFF;
			}
			this.lineHeight = data[256] & 0xFF;
			return;
		}
		pos = 0;
		for (@Pc(37) int i = 0; i < 256; i++) {
			this.glyphWidths[i] = data[pos++] & 0xFF;
		}
		@Pc(55) int[] glyphHeights = new int[256];
		@Pc(58) int[] glyphWidthArr = new int[256];
		@Pc(60) int i;
		for (i = 0; i < 256; i++) {
			glyphHeights[i] = data[pos++] & 0xFF;
		}
		for (i = 0; i < 256; i++) {
			glyphWidthArr[i] = data[pos++] & 0xFF;
		}
		@Pc(93) byte[][] rightGlyphs = new byte[256][];
		@Pc(109) int k;
		for (@Pc(95) int j = 0; j < 256; j++) {
			rightGlyphs[j] = new byte[glyphHeights[j]];
			@Pc(107) byte acc = 0;
			for (k = 0; k < rightGlyphs[j].length; k++) {
				acc += data[pos++];
				rightGlyphs[j][k] = acc;
			}
		}
		@Pc(136) byte[][] leftGlyphs = new byte[256][];
		@Pc(138) int m;
		for (m = 0; m < 256; m++) {
			leftGlyphs[m] = new byte[glyphHeights[m]];
			@Pc(150) byte acc = 0;
			for (@Pc(152) int n = 0; n < leftGlyphs[m].length; n++) {
				acc += data[pos++];
				leftGlyphs[m][n] = acc;
			}
		}
		this.kerning = new byte[65536];
		for (m = 0; m < 256; m++) {
			if (m != 32 && m != 160) {
				for (k = 0; k < 256; k++) {
					if (k != 32 && k != 160) {
						this.kerning[(m << 8) + k] = (byte) calculateKerning(rightGlyphs, leftGlyphs, glyphWidthArr, this.glyphWidths, glyphHeights, m, k);
					}
				}
			}
		}
		this.lineHeight = glyphWidthArr[32] + glyphHeights[32];
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(III)V")
	private void setColors(@OriginalArg(0) int rgb, @OriginalArg(1) int shadow, @OriginalArg(2) int a) {
		strikethroughColor = -1;
		underlineColor = -1;
		shadowColor = shadow;
		shadowColorOverride = shadow;
		color = rgb;
		colorOverride = rgb;
		alpha = a;
		alphaOverride = a;
		spaceWidth = 0;
		extraSpaceWidth = 0;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;IIIIIIIILjava/util/Random;I[I)I")
	public final int renderWavyTextAligned(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int w, @OriginalArg(4) int h, @OriginalArg(5) int color, @OriginalArg(6) int shadow, @OriginalArg(7) int halign, @OriginalArg(8) int valign, @OriginalArg(9) Random random, @OriginalArg(10) int seed, @OriginalArg(11) int[] bounds) {
		if (text == null) {
			return 0;
		}
		random.setSeed(seed);
		this.setColors(color, shadow, (random.nextInt() & 0x1F) + 192);
		@Pc(21) int len = text.length();
		@Pc(24) int[] xOffsets = new int[len];
		@Pc(26) int maxOffset = 0;
		@Pc(28) int i;
		for (i = 0; i < len; i++) {
			xOffsets[i] = maxOffset;
			if ((random.nextInt() & 0x3) == 0) {
				maxOffset++;
			}
		}
		i = x;
		@Pc(50) int curY = y + this.paragraphTopPadding;
		@Pc(52) int totalW = -1;
		if (valign == 1) {
			curY += (h - this.paragraphTopPadding - this.paragraphBottomPadding) / 2;
		} else if (valign == 2) {
			curY = y + h - this.paragraphBottomPadding;
		}
		if (halign == 1) {
			totalW = this.getStringWidth(text) + maxOffset;
			i = x + (w - totalW) / 2;
		} else if (halign == 2) {
			totalW = this.getStringWidth(text) + maxOffset;
			i = x + w - totalW;
		}
		this.renderOffset(text, i, curY, xOffsets, null);
		if (bounds != null) {
			if (totalW == -1) {
				totalW = this.getStringWidth(text) + maxOffset;
			}
			bounds[0] = i;
			bounds[1] = curY - this.paragraphTopPadding;
			bounds[2] = totalW;
			bounds[3] = this.paragraphTopPadding + this.paragraphBottomPadding;
		}
		return maxOffset;
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!na;II[I[I)V")
	private void renderOffset(@OriginalArg(0) JagString text, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int[] xOffsets, @OriginalArg(4) int[] yOffsets) {
		@Pc(4) int baseY = y - this.lineHeight;
		@Pc(6) int tagStart = -1;
		@Pc(8) int prevChar = 0;
		@Pc(10) int charIdx = 0;
		@Pc(14) int len = text.length();
		for (@Pc(16) int i = 0; i < len; i++) {
			@Pc(24) int ch = text.charAt(i);
			if (ch == 60) {
				tagStart = i;
			} else {
				@Pc(121) int xOff;
				@Pc(130) int yOff;
				@Pc(141) int iconIdx;
				if (ch == 62 && tagStart != -1) {
					@Pc(44) JagString tag = text.substring(i, tagStart + 1);
					tagStart = -1;
					if (tag.strEquals(TAG_LT)) {
						ch = 60;
					} else if (tag.strEquals(TAG_GT)) {
						ch = 62;
					} else if (tag.strEquals(TAG_NBSP)) {
						ch = 160;
					} else if (tag.strEquals(TAB_SHY)) {
						ch = 173;
					} else if (tag.strEquals(TAB_TIMES)) {
						ch = 215;
					} else if (tag.strEquals(TAB_EURO)) {
						ch = 128;
					} else if (tag.strEquals(TAB_COPY)) {
						ch = 169;
					} else {
						if (!tag.strEquals(TAG_REG)) {
							if (tag.startsWith(TAG_IMG)) {
								try {
									if (xOffsets == null) {
										xOff = 0;
									} else {
										xOff = xOffsets[charIdx];
									}
									if (yOffsets == null) {
										yOff = 0;
									} else {
										yOff = yOffsets[charIdx];
									}
									charIdx++;
									iconIdx = tag.substring(4).parseInt();
									@Pc(146) IndexedSprite icon = this.nameIcons[iconIdx];
									@Pc(157) int iconHeight = this.nameIconHeights == null ? icon.innerHeight : this.nameIconHeights[iconIdx];
									if (alphaOverride == 256) {
										icon.renderTransparent(x + xOff, baseY + this.lineHeight - iconHeight + yOff);
									} else {
										icon.renderAlpha(x + xOff, baseY + this.lineHeight - iconHeight + yOff, alphaOverride);
									}
									x += icon.innerWidth;
									prevChar = 0;
								} catch (@Pc(197) Exception ex) {
								}
							} else {
								this.parseTag(tag);
							}
							continue;
						}
						ch = 174;
					}
				}
				if (tagStart == -1) {
					if (this.kerning != null && prevChar != 0) {
						x += this.kerning[(prevChar << 8) + ch];
					}
					@Pc(226) int glyphW = this.spriteInnerWidths[ch];
					xOff = this.spriteInnerHeights[ch];
					if (xOffsets == null) {
						yOff = 0;
					} else {
						yOff = xOffsets[charIdx];
					}
					if (yOffsets == null) {
						iconIdx = 0;
					} else {
						iconIdx = yOffsets[charIdx];
					}
					charIdx++;
					if (ch == 32) {
						if (spaceWidth > 0) {
							extraSpaceWidth += spaceWidth;
							x += extraSpaceWidth >> 8;
							extraSpaceWidth &= 0xFF;
						}
					} else if (alphaOverride == 256) {
						if (shadowColorOverride != -1) {
							this.renderGlyph(ch, x + this.spriteXOffsets[ch] + yOff + 1, baseY + this.spriteYOffsets[ch] + 1 + iconIdx, glyphW, xOff, shadowColorOverride);
						}
						this.renderGlyph(ch, x + this.spriteXOffsets[ch] + yOff, baseY + this.spriteYOffsets[ch] + iconIdx, glyphW, xOff, colorOverride);
					} else {
						if (shadowColorOverride != -1) {
							this.renderGlyphTransparent(ch, x + this.spriteXOffsets[ch] + yOff + 1, baseY + this.spriteYOffsets[ch] + 1 + iconIdx, glyphW, xOff, shadowColorOverride, alphaOverride);
						}
						this.renderGlyphTransparent(ch, x + this.spriteXOffsets[ch] + yOff, baseY + this.spriteYOffsets[ch] + iconIdx, glyphW, xOff, colorOverride, alphaOverride);
					}
					@Pc(387) int advanceW = this.glyphWidths[ch];
					if (strikethroughColor != -1) {
						if (GlRenderer.enabled) {
							GlRaster.drawHorizontalLine(x, baseY + (int) ((double) this.lineHeight * 0.7D), advanceW, strikethroughColor);
						} else {
							SoftwareRaster.drawHorizontalLine(x, baseY + (int) ((double) this.lineHeight * 0.7D), advanceW, strikethroughColor);
						}
					}
					if (underlineColor != -1) {
						if (GlRenderer.enabled) {
							GlRaster.drawHorizontalLine(x, baseY + this.lineHeight, advanceW, underlineColor);
						} else {
							SoftwareRaster.drawHorizontalLine(x, baseY + this.lineHeight, advanceW, underlineColor);
						}
					}
					x += advanceW;
					prevChar = ch;
				}
			}
		}
	}
}
