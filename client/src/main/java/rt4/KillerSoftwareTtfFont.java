package rt4;

public final class KillerSoftwareTtfFont extends Font {
    private final byte[][] alphaPixels;

    public KillerSoftwareTtfFont(KillerTtfFontData data) {
        super(
            data.glyphWidths,
            data.kerning,
            data.lineHeight,
            data.xOffsets,
            data.yOffsets,
            data.innerWidths,
            data.innerHeights
        );
        this.alphaPixels = data.alphaPixels;
        KillerTtfFontData.logRenderer("SUCCESS", "Software DIRECT_TTF renderer initialized");
    }

    @Override
    protected void renderGlyphTransparent(int glyphId, int x, int y, int width, int height, int color, int alpha) {
        int destOff = x + y * SoftwareRaster.width;
        int destStep = SoftwareRaster.width - width;
        int srcStep = 0;
        int srcOff = 0;
        int clip;

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
            blitAlpha(
                this.alphaPixels[glyphId],
                color,
                srcOff,
                destOff,
                width,
                height,
                destStep,
                srcStep,
                alpha
            );
        }
    }

    @Override
    protected void renderGlyph(int glyphId, int x, int y, int width, int height, int color) {
        int destOff = x + y * SoftwareRaster.width;
        int destStep = SoftwareRaster.width - width;
        int srcStep = 0;
        int srcOff = 0;
        int clip;

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
            blitAlpha(
                this.alphaPixels[glyphId],
                color,
                srcOff,
                destOff,
                width,
                height,
                destStep,
                srcStep,
                256
            );
        } else {
            blitMaskedAlpha(
                this.alphaPixels[glyphId],
                x,
                y,
                width,
                height,
                color,
                srcOff,
                destOff,
                destStep,
                srcStep,
                SoftwareRaster.lineMaskStarts,
                SoftwareRaster.lineMaskWidths
            );
        }
    }

    private static void blitAlpha(
        byte[] glyphPixels,
        int color,
        int srcOff,
        int destOff,
        int width,
        int height,
        int destStep,
        int srcStep,
        int globalAlpha
    ) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int coverage = glyphPixels[srcOff++] & 0xFF;
                if (coverage == 0) {
                    destOff++;
                    continue;
                }
                int alpha256 = (coverage * globalAlpha + 127) / 255;
                SoftwareRaster.pixels[destOff] = blend(
                    SoftwareRaster.pixels[destOff],
                    color,
                    alpha256
                );
                destOff++;
            }
            srcOff += srcStep;
            destOff += destStep;
        }
    }

    private static void blitMaskedAlpha(
        byte[] glyphPixels,
        int glyphX,
        int glyphY,
        int width,
        int height,
        int color,
        int srcOff,
        int destOff,
        int destStep,
        int srcStep,
        int[] maskStarts,
        int[] maskWidths
    ) {
        int relX = glyphX - SoftwareRaster.clipLeft;
        int relY = glyphY - SoftwareRaster.clipTop;

        for (int row = relY; row < relY + height; row++) {
            int maskStart = maskStarts[row];
            int maskWidth = maskWidths[row];
            int rowWidth = width;
            int skip;

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

            for (int col = 0; col < maskWidth; col++) {
                int coverage = glyphPixels[srcOff++] & 0xFF;
                if (coverage != 0) {
                    int alpha256 = (coverage * 256 + 127) / 255;
                    SoftwareRaster.pixels[destOff] = blend(
                        SoftwareRaster.pixels[destOff],
                        color,
                        alpha256
                    );
                }
                destOff++;
            }

            srcOff += skip + srcStep;
            destOff += skip + destStep;
        }
    }

    private static int blend(int destColor, int sourceColor, int alpha256) {
        if (alpha256 <= 0) {
            return destColor;
        }
        if (alpha256 >= 256) {
            return sourceColor;
        }

        int inverse = 256 - alpha256;
        return ((((sourceColor & 0xFF00FF) * alpha256 + (destColor & 0xFF00FF) * inverse) & 0xFF00FF00)
            + (((sourceColor & 0x00FF00) * alpha256 + (destColor & 0x00FF00) * inverse) & 0x00FF0000)) >> 8;
    }
}
