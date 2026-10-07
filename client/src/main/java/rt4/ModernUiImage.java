package rt4;

import com.jogamp.opengl.GL2;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * RGBA image owned by the Modern UI pipeline. Unlike legacy GlSprite, alpha
 * from source artwork is preserved.
 */
public final class ModernUiImage {
    private final int width;
    private final int height;
    private final int[] argb;

    private int textureId = -1;
    private int textureContextId = -1;

    public ModernUiImage(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.argb = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(int x, int y) {
        if (GlRenderer.enabled) {
            renderGl(x, y);
        } else {
            renderSoftware(x, y);
        }
    }

    public void dispose() {
        if (textureId != -1 && GlRenderer.enabled && textureContextId == GlCleaner.contextId) {
            int[] ids = {textureId};
            GlRenderer.gl.glDeleteTextures(1, ids, 0);
        }
        textureId = -1;
        textureContextId = -1;
    }

    private void renderGl(int x, int y) {
        ensureTexture();

        GL2 gl = GlRenderer.gl;
        GlRenderer.begin2DModulateAlt();
        GlRenderer.setTextureId(textureId);
        gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float top = GlRenderer.canvasHeight - y;
        float bottom = top - height;
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glTexCoord2f(1.0F, 0.0F);
        gl.glVertex2f(x + width, top);
        gl.glTexCoord2f(0.0F, 0.0F);
        gl.glVertex2f(x, top);
        gl.glTexCoord2f(0.0F, 1.0F);
        gl.glVertex2f(x, bottom);
        gl.glTexCoord2f(1.0F, 1.0F);
        gl.glVertex2f(x + width, bottom);
        gl.glEnd();
    }

    private void ensureTexture() {
        if (textureId != -1 && textureContextId == GlCleaner.contextId) {
            return;
        }

        GL2 gl = GlRenderer.gl;
        int[] ids = new int[1];
        gl.glGenTextures(1, ids, 0);
        textureId = ids[0];
        textureContextId = GlCleaner.contextId;

        ByteBuffer rgba = ByteBuffer.allocateDirect(width * height * 4);
        for (int pixel : argb) {
            rgba.put((byte) (pixel >> 16));
            rgba.put((byte) (pixel >> 8));
            rgba.put((byte) pixel);
            rgba.put((byte) (pixel >>> 24));
        }
        rgba.flip();

        GlRenderer.setTextureId(textureId);
        gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexImage2D(
            GL2.GL_TEXTURE_2D,
            0,
            GL2.GL_RGBA,
            width,
            height,
            0,
            GL2.GL_RGBA,
            GL2.GL_UNSIGNED_BYTE,
            rgba
        );
    }

    private void renderSoftware(int x, int y) {
        for (int sy = 0; sy < height; sy++) {
            int dy = y + sy;
            if (dy < SoftwareRaster.clipTop || dy >= SoftwareRaster.clipBottom) {
                continue;
            }
            for (int sx = 0; sx < width; sx++) {
                int dx = x + sx;
                if (dx < SoftwareRaster.clipLeft || dx >= SoftwareRaster.clipRight) {
                    continue;
                }

                int source = argb[sx + sy * width];
                int alpha = source >>> 24;
                if (alpha == 0) {
                    continue;
                }

                int index = dx + dy * SoftwareRaster.width;
                if (alpha == 255) {
                    SoftwareRaster.pixels[index] = source & 0xFFFFFF;
                    continue;
                }

                int dest = SoftwareRaster.pixels[index];
                int inv = 255 - alpha;
                int r = ((source >> 16 & 0xFF) * alpha + (dest >> 16 & 0xFF) * inv) / 255;
                int g = ((source >> 8 & 0xFF) * alpha + (dest >> 8 & 0xFF) * inv) / 255;
                int b = ((source & 0xFF) * alpha + (dest & 0xFF) * inv) / 255;
                SoftwareRaster.pixels[index] = r << 16 | g << 8 | b;
            }
        }
    }
}
