package rt4;

import com.jogamp.opengl.GL2;

import java.nio.ByteBuffer;

public final class KillerGlTtfFont extends Font {
    private int[] listIds;
    private int contextId;
    private int powerOfTwoSize;
    private int textureId = -1;
    private int size = 0;

    public KillerGlTtfFont(KillerTtfFontData data) {
        super(
            data.glyphWidths,
            data.kerning,
            data.lineHeight,
            data.xOffsets,
            data.yOffsets,
            data.innerWidths,
            data.innerHeights
        );
        this.createTexture(data.alphaPixels);
        this.createLists();
        KillerTtfFontData.logRenderer("SUCCESS", "OpenGL DIRECT_TTF renderer initialized");
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.textureId != -1) {
            GlCleaner.deleteTexture2d(this.textureId, this.size, this.contextId);
            this.textureId = -1;
            this.size = 0;
        }
        if (this.listIds != null) {
            for (int i = 0; i < this.listIds.length; i++) {
                GlCleaner.deleteList(this.listIds[i], this.contextId);
            }
            this.listIds = null;
        }
        super.finalize();
    }

    @Override
    protected void renderGlyph(int glyph, int x, int y, int width, int height, int color) {
        GL2 gl;
        if (GlFont.masked == null) {
            GlRenderer.begin2DModulate();
            gl = GlRenderer.gl;
            GlRenderer.setTextureId(this.textureId);
            gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
            gl.glTranslatef((float) x, (float) (GlRenderer.canvasHeight - y), 0.0F);
            gl.glCallList(this.listIds[glyph]);
            gl.glLoadIdentity();
            return;
        }

        GlRenderer.begin2DModulate();
        gl = GlRenderer.gl;
        gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
        gl.glTranslatef((float) x, (float) (GlRenderer.canvasHeight - y), 0.0F);

        float s0 = (float) (glyph % 16) / 16.0F;
        float t0 = (float) (glyph / 16) / 16.0F;
        float s1 = s0 + (float) this.spriteInnerWidths[glyph] / (float) this.powerOfTwoSize;
        float t1 = t0 + (float) this.spriteInnerHeights[glyph] / (float) this.powerOfTwoSize;

        GlRenderer.setTextureId(this.textureId);
        GlSprite mask = GlFont.masked;

        gl.glActiveTexture(GL2.GL_TEXTURE1);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, mask.textureId);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_REPLACE);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_PREVIOUS);

        float maskX0 = (float) (x - GlRaster.clipLeft) / (float) mask.powerOfTwoWidth;
        float maskY0 = (float) (y - GlRaster.clipTop) / (float) mask.powerOfTwoHeight;
        float maskX1 = (float) (x + width - GlRaster.clipLeft) / (float) mask.powerOfTwoWidth;
        float maskY1 = (float) (y + height - GlRaster.clipTop) / (float) mask.powerOfTwoHeight;

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskX1, maskY0);
        gl.glTexCoord2f(s1, t0);
        gl.glVertex2f((float) this.spriteInnerWidths[glyph], 0.0F);

        gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskX0, maskY0);
        gl.glTexCoord2f(s0, t0);
        gl.glVertex2f(0.0F, 0.0F);

        gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskX0, maskY1);
        gl.glTexCoord2f(s0, t1);
        gl.glVertex2f(0.0F, (float) -this.spriteInnerHeights[glyph]);

        gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskX1, maskY1);
        gl.glTexCoord2f(s1, t1);
        gl.glVertex2f((float) this.spriteInnerWidths[glyph], (float) -this.spriteInnerHeights[glyph]);
        gl.glEnd();

        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glLoadIdentity();
    }

    @Override
    protected void renderGlyphTransparent(int glyph, int x, int y, int width, int height, int color, int alpha) {
        GlRenderer.begin2DModulate();
        GL2 gl = GlRenderer.gl;
        GlRenderer.setTextureId(this.textureId);
        gl.glColor4ub(
            (byte) (color >> 16),
            (byte) (color >> 8),
            (byte) color,
            alpha > 255 ? (byte) -1 : (byte) alpha
        );
        gl.glTranslatef((float) x, (float) (GlRenderer.canvasHeight - y), 0.0F);
        gl.glCallList(this.listIds[glyph]);
        gl.glLoadIdentity();
    }

    private void createLists() {
        if (this.listIds != null) {
            return;
        }

        this.listIds = new int[256];
        GL2 gl = GlRenderer.gl;

        for (int i = 0; i < 256; i++) {
            float s0 = (float) (i % 16) / 16.0F;
            float t0 = (float) (i / 16) / 16.0F;
            float s1 = s0 + (float) this.spriteInnerWidths[i] / (float) this.powerOfTwoSize;
            float t1 = t0 + (float) this.spriteInnerHeights[i] / (float) this.powerOfTwoSize;

            this.listIds[i] = gl.glGenLists(1);
            gl.glNewList(this.listIds[i], GL2.GL_COMPILE);
            gl.glBegin(GL2.GL_TRIANGLE_FAN);

            gl.glTexCoord2f(s1, t0);
            gl.glVertex2f((float) this.spriteInnerWidths[i], 0.0F);

            gl.glTexCoord2f(s0, t0);
            gl.glVertex2f(0.0F, 0.0F);

            gl.glTexCoord2f(s0, t1);
            gl.glVertex2f(0.0F, (float) -this.spriteInnerHeights[i]);

            gl.glTexCoord2f(s1, t1);
            gl.glVertex2f((float) this.spriteInnerWidths[i], (float) -this.spriteInnerHeights[i]);

            gl.glEnd();
            gl.glEndList();
        }

        this.contextId = GlCleaner.contextId;
    }

    private void createTexture(byte[][] alphaPixels) {
        if (this.textureId != -1) {
            return;
        }

        this.powerOfTwoSize = 0;
        for (int i = 0; i < 256; i++) {
            if (this.spriteInnerHeights[i] > this.powerOfTwoSize) {
                this.powerOfTwoSize = this.spriteInnerHeights[i];
            }
            if (this.spriteInnerWidths[i] > this.powerOfTwoSize) {
                this.powerOfTwoSize = this.spriteInnerWidths[i];
            }
        }

        this.powerOfTwoSize = Math.max(16, this.powerOfTwoSize * 16);
        this.powerOfTwoSize = IntUtils.clp2(this.powerOfTwoSize);
        int glyphSize = this.powerOfTwoSize / 16;

        byte[] dest = new byte[this.powerOfTwoSize * this.powerOfTwoSize * 2];

        for (int i = 0; i < 256; i++) {
            int s = i % 16 * glyphSize;
            int t = i / 16 * glyphSize;
            int destIndex = (t * this.powerOfTwoSize + s) * 2;
            int srcIndex = 0;
            int height = this.spriteInnerHeights[i];
            int width = this.spriteInnerWidths[i];
            byte[] src = alphaPixels[i];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int coverage = src[srcIndex++] & 0xFF;
                    dest[destIndex++] = (byte) 0xFF;
                    dest[destIndex++] = (byte) coverage;
                }
                destIndex += (this.powerOfTwoSize - width) * 2;
            }
        }

        ByteBuffer buffer = ByteBuffer.wrap(dest);
        GL2 gl = GlRenderer.gl;

        int[] temp = new int[1];
        gl.glGenTextures(1, temp, 0);
        this.textureId = temp[0];
        this.contextId = GlCleaner.contextId;

        GlRenderer.setTextureId(this.textureId);
        gl.glTexImage2D(
            GL2.GL_TEXTURE_2D,
            0,
            GL2.GL_LUMINANCE_ALPHA,
            this.powerOfTwoSize,
            this.powerOfTwoSize,
            0,
            GL2.GL_LUMINANCE_ALPHA,
            GL2.GL_UNSIGNED_BYTE,
            buffer
        );

        GlCleaner.onCard2d += buffer.limit() - this.size;
        this.size = buffer.limit();

        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
    }
}
