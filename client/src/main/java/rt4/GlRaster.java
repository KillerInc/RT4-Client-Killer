package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.IntBuffer;

public final class GlRaster {

	@OriginalMember(owner = "client!dj", name = "b", descriptor = "I")
	public static int clipTop = 0;

	@OriginalMember(owner = "client!dj", name = "c", descriptor = "I")
	public static int clipLeft = 0;

	@OriginalMember(owner = "client!dj", name = "d", descriptor = "I")
	private static int clipRight = 0;

	@OriginalMember(owner = "client!dj", name = "e", descriptor = "I")
	public static int clipBottom = 0;

	@OriginalMember(owner = "client!dj", name = "a", descriptor = "(IIII)V")
	public static void drawHorizontalLine(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int length, @OriginalArg(3) int color) {
		GlRenderer.begin2DNoTexture();
		@Pc(5) float xCoordinateStart = (float) x + 0.3F;
		@Pc(10) float xCoordinateEnd = xCoordinateStart + (float) length;
		@Pc(18) float yCoordinate = (float) GlRenderer.canvasHeight - (float) y - 0.3F;
		@Pc(20) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glVertex2f(xCoordinateStart, yCoordinate);
		gl.glVertex2f(xCoordinateEnd, yCoordinate);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "b", descriptor = "(IIII)V")
	public static void drawVerticalLine(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int height, @OriginalArg(3) int color) {
		GlRenderer.begin2DNoTexture();
		@Pc(5) float xCoordinate = (float) x + 0.3F;
		@Pc(13) float yCoordinateStart = (float) GlRenderer.canvasHeight - (float) y - 0.3F;
		@Pc(18) float yCoordinateEnd = yCoordinateStart - (float) height;
		@Pc(20) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glVertex2f(xCoordinate, yCoordinateStart);
		gl.glVertex2f(xCoordinate, yCoordinateEnd);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "c", descriptor = "()V")
	public static void resetClipRegion() {
		clipLeft = 0;
		clipTop = 0;
		clipRight = GlRenderer.canvasWidth;
		clipBottom = GlRenderer.canvasHeight;
		@Pc(9) GL2 gl = GlRenderer.gl;
		gl.glDisable(GL2.GL_SCISSOR_TEST);
		GlFont.clearLineMask();
	}

	@OriginalMember(owner = "client!dj", name = "a", descriptor = "([IIIII)V")
	public static void drawPixels(@OriginalArg(0) int[] pixels, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int width, @OriginalArg(4) int height) {
		GlRenderer.begin2DNoTexture();
		@Pc(2) GL2 gl = GlRenderer.gl;
		gl.glRasterPos2i(x, GlRenderer.canvasHeight - y);
		gl.glPixelZoom((float) GameShell.canvasScale, (float) -GameShell.canvasScale);
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_ALPHA_TEST);
		gl.glDrawPixels(width, height, GL2.GL_BGRA, GlRenderer.bigEndian ? GL2.GL_UNSIGNED_INT_8_8_8_8_REV : GL2.GL_UNSIGNED_BYTE, IntBuffer.wrap(pixels));
		gl.glPixelZoom(1.0F, 1.0F);
		gl.glEnable(GL2.GL_ALPHA_TEST);
		gl.glEnable(GL2.GL_BLEND);
	}

	@OriginalMember(owner = "client!dj", name = "a", descriptor = "(IIIII)V")
	public static void drawRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int color) {
		GlRenderer.begin2DNoTexture();
		@Pc(5) float left = (float) x + 0.3F;
		@Pc(12) float right = left + (float) (width - 1);
		@Pc(20) float top = (float) GlRenderer.canvasHeight - (float) y - 0.3F;
		@Pc(27) float bottom = top - (float) (height - 1);
		@Pc(29) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glVertex2f(left, top);
		gl.glVertex2f(left, bottom);
		gl.glVertex2f(right, bottom);
		gl.glVertex2f(right, top);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "a", descriptor = "(IIIIII)V")
	public static void drawRectAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int color, @OriginalArg(5) int alpha) {
		GlRenderer.begin2DNoTexture();
		@Pc(5) float left = (float) x + 0.3F;
		@Pc(12) float right = left + (float) (width - 1);
		@Pc(20) float top = (float) GlRenderer.canvasHeight - (float) y - 0.3F;
		@Pc(27) float bottom = top - (float) (height - 1);
		@Pc(29) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor4ub((byte) (color >> 16), (byte) (color >> 8), (byte) color, alpha > 255 ? -1 : (byte) alpha);
		gl.glVertex2f(left, top);
		gl.glVertex2f(left, bottom);
		gl.glVertex2f(right, bottom);
		gl.glVertex2f(right, top);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "b", descriptor = "(IIIIII)V")
	public static void drawThickLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int color, @OriginalArg(5) int lineWidth) {
		@Pc(3) int dx = x2 - x1;
		@Pc(7) int dy = y2 - y1;
		@Pc(14) int absDx = dx >= 0 ? dx : -dx;
		@Pc(21) int absDy = dy >= 0 ? dy : -dy;
		@Pc(23) int maxDist = absDx;
		if (absDx < absDy) {
			maxDist = absDy;
		}
		if (maxDist == 0) {
			return;
		}
		@Pc(37) int stepX = (dx << 16) / maxDist;
		@Pc(43) int stepY = (dy << 16) / maxDist;
		if (stepY <= stepX) {
			stepX = -stepX;
		} else {
			stepY = -stepY;
		}
		@Pc(59) int perpX1 = lineWidth * stepY >> 17;
		@Pc(67) int perpX2 = lineWidth * stepY + 1 >> 17;
		@Pc(73) int perpY1 = lineWidth * stepX >> 17;
		@Pc(81) int perpY2 = lineWidth * stepX + 1 >> 17;
		@Pc(85) int cornerAx = x1 + perpX1;
		@Pc(89) int cornerBx = x1 - perpX2;
		@Pc(95) int cornerCx = x1 + dx - perpX2;
		@Pc(101) int cornerDx = x1 + dx + perpX1;
		@Pc(105) int cornerAy = y1 + perpY1;
		@Pc(109) int cornerBy = y1 - perpY2;
		@Pc(115) int cornerCy = y1 + dy - perpY2;
		@Pc(121) int cornerDy = y1 + dy + perpY1;
		GlRenderer.begin2DNoTexture();
		@Pc(124) GL2 gl = GlRenderer.gl;
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		if (stepY <= stepX) {
			gl.glVertex2f((float) cornerDx, (float) (GlRenderer.canvasHeight - cornerDy));
			gl.glVertex2f((float) cornerCx, (float) (GlRenderer.canvasHeight - cornerCy));
			gl.glVertex2f((float) cornerBx, (float) (GlRenderer.canvasHeight - cornerBy));
			gl.glVertex2f((float) cornerAx, (float) (GlRenderer.canvasHeight - cornerAy));
		} else {
			gl.glVertex2f((float) cornerAx, (float) (GlRenderer.canvasHeight - cornerAy));
			gl.glVertex2f((float) cornerBx, (float) (GlRenderer.canvasHeight - cornerBy));
			gl.glVertex2f((float) cornerCx, (float) (GlRenderer.canvasHeight - cornerCy));
			gl.glVertex2f((float) cornerDx, (float) (GlRenderer.canvasHeight - cornerDy));
		}
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "c", descriptor = "(IIIIII)V")
	public static void fillRectAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int color, @OriginalArg(5) int alpha) {
		GlRenderer.begin2DNoTexture();
		@Pc(3) float left = (float) x;
		@Pc(8) float right = left + (float) width;
		@Pc(13) float top = (float) (GlRenderer.canvasHeight - y);
		@Pc(18) float bottom = top - (float) height;
		@Pc(20) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4ub((byte) (color >> 16), (byte) (color >> 8), (byte) color, alpha > 255 ? -1 : (byte) alpha);
		gl.glVertex2f(left, top);
		gl.glVertex2f(left, bottom);
		gl.glVertex2f(right, bottom);
		gl.glVertex2f(right, top);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "c", descriptor = "(IIII)V")
	public static void setClipRegion(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (clipLeft < left) {
			clipLeft = left;
		}
		if (clipTop < top) {
			clipTop = top;
		}
		if (clipRight > right) {
			clipRight = right;
		}
		if (clipBottom > bottom) {
			clipBottom = bottom;
		}
		@Pc(21) GL2 gl = GlRenderer.gl;
		gl.glEnable(GL2.GL_SCISSOR_TEST);
		if (clipLeft <= clipRight && clipTop <= clipBottom) {
			gl.glScissor((int) (clipLeft * GameShell.canvasScale + GameShell.subpixelX), (int) ((GlRenderer.canvasHeight - clipBottom) * GameShell.canvasScale + GameShell.subpixelY), (int) ((clipRight - clipLeft) * GameShell.canvasScale + GameShell.subpixelX), (int) ((clipBottom - clipTop) * GameShell.canvasScale + GameShell.subpixelY));
		} else {
			gl.glScissor(0, 0, 0, 0);
		}
		GlFont.clearLineMask();
	}

	@OriginalMember(owner = "client!dj", name = "d", descriptor = "()V")
	public static void clear() {
		GlRenderer.gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_COLOR_BUFFER_BIT);
	}

	@OriginalMember(owner = "client!dj", name = "b", descriptor = "(IIIII)V")
	public static void drawLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int color) {
		GlRenderer.begin2DNoTexture();
		@Pc(5) float glX1 = (float) x1 + 0.3F;
		@Pc(10) float glX2 = (float) x2 + 0.3F;
		@Pc(18) float glY1 = (float) GlRenderer.canvasHeight - (float) y1 - 0.3F;
		@Pc(26) float glY2 = (float) GlRenderer.canvasHeight - (float) y2 - 0.3F;
		@Pc(28) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glVertex2f(glX1, glY1);
		gl.glVertex2f(glX2, glY2);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "c", descriptor = "(IIIII)V")
	public static void fillRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int color) {
		GlRenderer.begin2DNoTexture();
		@Pc(3) float left = (float) x;
		@Pc(8) float right = left + (float) width;
		@Pc(13) float top = (float) (GlRenderer.canvasHeight - y);
		@Pc(18) float bottom = top - (float) height;
		@Pc(20) GL2 gl = GlRenderer.gl;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3ub((byte) (color >> 16), (byte) (color >> 8), (byte) color);
		gl.glVertex2f(left, top);
		gl.glVertex2f(left, bottom);
		gl.glVertex2f(right, bottom);
		gl.glVertex2f(right, top);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!dj", name = "d", descriptor = "(IIII)V")
	public static void setClip(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (left < 0) {
			left = 0;
		}
		if (top < 0) {
			top = 0;
		}
		if (right > GlRenderer.canvasWidth) {
			right = GlRenderer.canvasWidth;
		}
		if (bottom > GlRenderer.canvasHeight) {
			bottom = GlRenderer.canvasHeight;
		}
		clipLeft = left;
		clipTop = top;
		clipRight = right;
		clipBottom = bottom;
		@Pc(27) GL2 gl = GlRenderer.gl;
		gl.glEnable(GL2.GL_SCISSOR_TEST);
		if (clipLeft <= clipRight && clipTop <= clipBottom) {
			gl.glScissor((int) (clipLeft * GameShell.canvasScale + GameShell.subpixelX), (int) ((GlRenderer.canvasHeight - clipBottom) * GameShell.canvasScale + GameShell.subpixelY), (int) ((clipRight - clipLeft) * GameShell.canvasScale + GameShell.subpixelX), (int) ((clipBottom - clipTop) * GameShell.canvasScale + GameShell.subpixelY));
		} else {
			gl.glScissor(0, 0, 0, 0);
		}
		GlFont.clearLineMask();
	}

}
