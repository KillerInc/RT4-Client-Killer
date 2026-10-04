package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!cf")
public class GlSprite extends Sprite {

	@OriginalMember(owner = "client!cf", name = "O", descriptor = "I")
	public int powerOfTwoWidth;

	@OriginalMember(owner = "client!cf", name = "cb", descriptor = "I")
	public int powerOfTwoHeight;

	@OriginalMember(owner = "client!cf", name = "db", descriptor = "I")
	private int contextId;

	@OriginalMember(owner = "client!cf", name = "L", descriptor = "I")
	protected int textureDataSize = 0;

	@OriginalMember(owner = "client!cf", name = "ab", descriptor = "I")
	public int textureId = -1;

	public int[] pixels;

	@OriginalMember(owner = "client!cf", name = "Z", descriptor = "I")
	private int displayListId = -1;

	@OriginalMember(owner = "client!cf", name = "bb", descriptor = "I")
	private int filterMode = 0;

	@OriginalMember(owner = "client!cf", name = "<init>", descriptor = "(IIIIII[I)V")
	public GlSprite(@OriginalArg(0) int innerWidth, @OriginalArg(1) int innerHeight, @OriginalArg(2) int xOffset, @OriginalArg(3) int yOffset, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) int[] pixels) {
		this.innerWidth = innerWidth;
		this.innerHeight = innerHeight;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.uploadPixels(pixels);
		this.compileDisplayList();
	}

	@OriginalMember(owner = "client!cf", name = "<init>", descriptor = "(Lclient!mm;)V")
	public GlSprite(@OriginalArg(0) SoftwareSprite src) {
		this.innerWidth = src.innerWidth;
		this.innerHeight = src.innerHeight;
		this.xOffset = src.xOffset;
		this.yOffset = src.yOffset;
		this.width = src.width;
		this.height = src.height;
		this.pixels = src.pixels;
		this.uploadPixels(src.pixels);
		this.compileDisplayList();
	}

	@OriginalMember(owner = "client!cf", name = "d", descriptor = "(I)V")
	private void setTextureFilter(@OriginalArg(0) int mode) {
		if (this.filterMode == mode) {
			return;
		}
		this.filterMode = mode;
		@Pc(9) GL2 gl = GlRenderer.gl;
		if (mode == 2) {
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		} else {
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		}
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(IILclient!cf;)V")
	public final void renderClipped(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) GlSprite clipMask) {
		if (clipMask == null) {
			return;
		}
		GlRenderer.begin2DReplace();
		GlRenderer.setTextureId(clipMask.textureId);
		clipMask.setTextureFilter(1);
		@Pc(11) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, clipMask.textureId);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_REPLACE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_PREVIOUS);
		@Pc(47) float clipU0 = (float) (x - GlRaster.clipLeft) / (float) clipMask.powerOfTwoWidth;
		@Pc(56) float clipV0 = (float) (y - GlRaster.clipTop) / (float) clipMask.powerOfTwoHeight;
		@Pc(68) float clipU1 = (float) (x + this.width - GlRaster.clipLeft) / (float) clipMask.powerOfTwoWidth;
		@Pc(80) float clipV1 = (float) (y + this.height - GlRaster.clipTop) / (float) clipMask.powerOfTwoHeight;
		@Pc(85) int drawX = x + this.xOffset;
		@Pc(90) int drawY = y + this.yOffset;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		@Pc(107) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(115) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, clipU1, clipV0);
		gl.glTexCoord2f(texU, 0.0F);
		gl.glVertex2f((float) (drawX + this.width), (float) (GlRenderer.canvasHeight - drawY));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, clipU0, clipV0);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f((float) drawX, (float) (GlRenderer.canvasHeight - drawY));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, clipU0, clipV1);
		gl.glTexCoord2f(0.0F, texV);
		gl.glVertex2f((float) drawX, (float) (GlRenderer.canvasHeight - drawY - this.height));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, clipU1, clipV1);
		gl.glTexCoord2f(texU, texV);
		gl.glVertex2f((float) (drawX + this.width), (float) (GlRenderer.canvasHeight - drawY - this.height));
		gl.glEnd();
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
	}

	@OriginalMember(owner = "client!cf", name = "c", descriptor = "(IIIII)V")
	public final void renderTiledAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha, @OriginalArg(3) int tilesX, @OriginalArg(4) int tilesY) {
		GlRenderer.begin2DModulateAlt();
		@Pc(2) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		@Pc(16) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(24) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		@Pc(29) float totalU = texU * (float) tilesX;
		@Pc(34) float totalV = texV * (float) tilesY;
		@Pc(39) int left = x + this.xOffset;
		@Pc(46) int right = left + this.width * tilesX;
		@Pc(53) int top = GlRenderer.canvasHeight - y - this.yOffset;
		@Pc(60) int bottom = top - this.height * tilesY;
		@Pc(65) float alphaF = (float) alpha / 256.0F;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4f(1.0F, 1.0F, 1.0F, alphaF);
		gl.glTexCoord2f(totalU, 0.0F);
		gl.glVertex2f((float) right, (float) top);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f((float) left, (float) top);
		gl.glTexCoord2f(0.0F, totalV);
		gl.glVertex2f((float) left, (float) bottom);
		gl.glTexCoord2f(totalU, totalV);
		gl.glVertex2f((float) right, (float) bottom);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(IIIIIIIILclient!cf;)V")
	public final void renderRotatedTransparent(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int destWidth, @OriginalArg(3) int destHeight, @OriginalArg(4) int srcX, @OriginalArg(5) int srcY, @OriginalArg(6) int angle, @OriginalArg(7) int zoom, @OriginalArg(8) GlSprite clipMask) {
		if (clipMask == null) {
			return;
		}
		GlRenderer.begin2DReplace();
		GlRenderer.setTextureId(clipMask.textureId);
		clipMask.setTextureFilter(1);
		@Pc(11) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		if (GlobalConfig.BILINEAR_MINIMAP) {
			this.setTextureFilter(2);
		} else {
			this.setTextureFilter(1);
		}
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, clipMask.textureId);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_REPLACE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_PREVIOUS);
		@Pc(43) int halfW = -destWidth / 2;
		@Pc(48) int halfH = -destHeight / 2;
		@Pc(51) int halfWPos = -halfW;
		@Pc(54) int halfHPos = -halfH;
		@Pc(63) int sinAngle = (int) (Math.sin((double) angle / 326.11D) * 65536.0D);
		@Pc(72) int cosAngle = (int) (Math.cos((double) angle / 326.11D) * 65536.0D);
		@Pc(78) int scaledSin = sinAngle * zoom >> 8;
		@Pc(84) int scaledCos = cosAngle * zoom >> 8;
		@Pc(96) int tlX = (srcX << 16) + halfH * scaledSin + halfW * scaledCos;
		@Pc(108) int tlY = (srcY << 16) + (halfH * scaledCos - halfW * scaledSin);
		@Pc(120) int trX = (srcX << 16) + halfH * scaledSin + halfWPos * scaledCos;
		@Pc(132) int trY = (srcY << 16) + (halfH * scaledCos - halfWPos * scaledSin);
		@Pc(144) int blX = (srcX << 16) + halfHPos * scaledSin + halfW * scaledCos;
		@Pc(156) int blY = (srcY << 16) + (halfHPos * scaledCos - halfW * scaledSin);
		@Pc(168) int brX = (srcX << 16) + halfHPos * scaledSin + halfWPos * scaledCos;
		@Pc(180) int brY = (srcY << 16) + (halfHPos * scaledCos - halfWPos * scaledSin);
		@Pc(188) float maskU = (float) clipMask.width / (float) clipMask.powerOfTwoWidth;
		@Pc(196) float maskV = (float) clipMask.height / (float) clipMask.powerOfTwoHeight;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		@Pc(211) float texWidthF = (float) this.powerOfTwoWidth * 65536.0F;
		@Pc(217) float texHeightF = (float) (this.powerOfTwoHeight * 65536);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskU, 0.0F);
		gl.glTexCoord2f((float) trX / texWidthF, (float) trY / texHeightF);
		gl.glVertex2f((float) (x + destWidth), (float) (GlRenderer.canvasHeight - y));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0.0F, 0.0F);
		gl.glTexCoord2f((float) tlX / texWidthF, (float) tlY / texHeightF);
		gl.glVertex2f((float) x, (float) (GlRenderer.canvasHeight - y));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0.0F, maskV);
		gl.glTexCoord2f((float) blX / texWidthF, (float) blY / texHeightF);
		gl.glVertex2f((float) x, (float) (GlRenderer.canvasHeight - y - destHeight));
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, maskU, maskV);
		gl.glTexCoord2f((float) brX / texWidthF, (float) brY / texHeightF);
		gl.glVertex2f((float) (x + destWidth), (float) (GlRenderer.canvasHeight - y - destHeight));
		gl.glEnd();
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
	}

	@OriginalMember(owner = "client!cf", name = "d", descriptor = "(II)V")
	@Override
	public final void renderHorizontalFlip(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		GlRenderer.begin2DReplace();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		@Pc(35) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(43) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f((float) this.width, 0.0F);
		gl.glTexCoord2f(texU, 0.0F);
		gl.glVertex2f(0.0F, 0.0F);
		gl.glTexCoord2f(texU, texV);
		gl.glVertex2f(0.0F, (float) -this.height);
		gl.glTexCoord2f(0.0F, texV);
		gl.glVertex2f((float) this.width, (float) -this.height);
		gl.glEnd();
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "e", descriptor = "(II)V")
	@Override
	public final void render(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		GlRenderer.begin2DReplace();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "b", descriptor = "(IIIIII)V")
	public final void renderRotatedFixed(@OriginalArg(2) int centerX, @OriginalArg(3) int centerY, @OriginalArg(4) int angle) {
		GlRenderer.begin2DReplace();
		@Pc(2) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(2);
		@Pc(15) int originX = 240 - (this.xOffset << 4);
		@Pc(22) int originY = 240 - (this.yOffset << 4);
		gl.glTranslatef((float) centerX / 16.0F, (float) GlRenderer.canvasHeight - (float) centerY / 16.0F, 0.0F);
		gl.glRotatef((float) -angle * 0.005493164F, 0.0F, 0.0F, 1.0F);
		gl.glTranslatef((float) -originX / 16.0F, (float) originY / 16.0F, 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(IIII)V")
	@Override
	public final void renderResized(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int destWidth, @OriginalArg(3) int destHeight) {
		if (destWidth <= 0 || destHeight <= 0) {
			return;
		}
		GlRenderer.begin2DReplace();
		@Pc(8) int srcWidth = this.width;
		@Pc(11) int srcHeight = this.height;
		@Pc(13) int srcOffX = 0;
		@Pc(15) int srcOffY = 0;
		@Pc(18) int totalWidth = this.innerWidth;
		@Pc(21) int totalHeight = this.innerHeight;
		@Pc(27) int scaleX = (totalWidth << 16) / destWidth;
		@Pc(33) int scaleY = (totalHeight << 16) / destHeight;
		@Pc(47) int adj;
		if (this.xOffset > 0) {
			adj = ((this.xOffset << 16) + scaleX - 1) / scaleX;
			x += adj;
			srcOffX = adj * scaleX - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			adj = ((this.yOffset << 16) + scaleY - 1) / scaleY;
			y += adj;
			srcOffY = adj * scaleY - (this.yOffset << 16);
		}
		if (srcWidth < totalWidth) {
			destWidth = ((srcWidth << 16) + scaleX - srcOffX - 1) / scaleX;
		}
		if (srcHeight < totalHeight) {
			destHeight = ((srcHeight << 16) + scaleY - srcOffY - 1) / scaleY;
		}
		@Pc(123) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(2);
		@Pc(132) float left = (float) x;
		@Pc(137) float right = left + (float) destWidth;
		@Pc(142) float top = (float) (GlRenderer.canvasHeight - y);
		@Pc(147) float bottom = top - (float) destHeight;
		@Pc(155) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(163) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(texU, 0.0F);
		gl.glVertex2f(right, top);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f(left, top);
		gl.glTexCoord2f(0.0F, texV);
		gl.glVertex2f(left, bottom);
		gl.glTexCoord2f(texU, texV);
		gl.glVertex2f(right, bottom);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!cf", name = "c", descriptor = "(II)V")
	@Override
	public final void drawPixels(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		GlRenderer.begin2DReplace();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() throws Throwable {
		if (this.textureId != -1) {
			GlCleaner.deleteTexture2d(this.textureId, this.textureDataSize, this.contextId);
			this.textureId = -1;
			this.textureDataSize = 0;
		}
		if (this.displayListId != -1) {
			GlCleaner.deleteList(this.displayListId, this.contextId);
			this.displayListId = -1;
		}
		super.finalize();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(IIIIII)V")
	@Override
	protected final void drawRotatedScaled(@OriginalArg(0) int pivotX, @OriginalArg(1) int pivotY, @OriginalArg(2) int centerX, @OriginalArg(3) int centerY, @OriginalArg(4) int angle, @OriginalArg(5) int scale) {
		GlRenderer.begin2DReplace();
		@Pc(2) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		@Pc(15) int originX = pivotX - (this.xOffset << 4);
		@Pc(22) int originY = pivotY - (this.yOffset << 4);
		gl.glTranslatef((float) centerX / 16.0F, (float) GlRenderer.canvasHeight - (float) centerY / 16.0F, 0.0F);
		gl.glRotatef((float) angle * 0.005493164F, 0.0F, 0.0F, 1.0F);
		if (scale != 4096) {
			gl.glScalef((float) scale / 4096.0F, (float) scale / 4096.0F, 0.0F);
		}
		gl.glTranslatef((float) -originX / 16.0F, (float) originY / 16.0F, 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "b", descriptor = "(IIIII)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int destWidth, @OriginalArg(3) int destHeight, @OriginalArg(4) int alpha) {
		if (destWidth <= 0 || destHeight <= 0) {
			return;
		}
		GlRenderer.begin2DModulateAlt();
		@Pc(8) int srcWidth = this.width;
		@Pc(11) int srcHeight = this.height;
		@Pc(13) int srcOffX = 0;
		@Pc(15) int srcOffY = 0;
		@Pc(18) int totalWidth = this.innerWidth;
		@Pc(21) int totalHeight = this.innerHeight;
		@Pc(27) int scaleX = (totalWidth << 16) / destWidth;
		@Pc(33) int scaleY = (totalHeight << 16) / destHeight;
		@Pc(47) int adj;
		if (this.xOffset > 0) {
			adj = ((this.xOffset << 16) + scaleX - 1) / scaleX;
			x += adj;
			srcOffX = adj * scaleX - (this.xOffset << 16);
		}
		if (this.yOffset > 0) {
			adj = ((this.yOffset << 16) + scaleY - 1) / scaleY;
			y += adj;
			srcOffY = adj * scaleY - (this.yOffset << 16);
		}
		if (srcWidth < totalWidth) {
			destWidth = ((srcWidth << 16) + scaleX - srcOffX - 1) / scaleX;
		}
		if (srcHeight < totalHeight) {
			destHeight = ((srcHeight << 16) + scaleY - srcOffY - 1) / scaleY;
		}
		@Pc(123) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		@Pc(132) float left = (float) x;
		@Pc(137) float right = left + (float) destWidth;
		@Pc(142) float top = (float) (GlRenderer.canvasHeight - y);
		@Pc(147) float bottom = top - (float) destHeight;
		@Pc(155) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(163) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		@Pc(168) float alphaF = (float) alpha / 256.0F;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4f(1.0F, 1.0F, 1.0F, alphaF);
		gl.glTexCoord2f(texU, 0.0F);
		gl.glVertex2f(right, top);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f(left, top);
		gl.glTexCoord2f(0.0F, texV);
		gl.glVertex2f(left, bottom);
		gl.glTexCoord2f(texU, texV);
		gl.glVertex2f(right, bottom);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!cf", name = "b", descriptor = "(IIII)V")
	public final void renderTiled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int tilesX, @OriginalArg(3) int tilesY) {
		GlRenderer.begin2DReplace();
		@Pc(2) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		@Pc(16) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(24) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		@Pc(29) float totalU = texU * (float) tilesX;
		@Pc(34) float totalV = texV * (float) tilesY;
		@Pc(39) int left = x + this.xOffset;
		@Pc(46) int right = left + this.width * tilesX;
		@Pc(53) int top = GlRenderer.canvasHeight - y - this.yOffset;
		@Pc(60) int bottom = top - this.height * tilesY;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(totalU, 0.0F);
		gl.glVertex2f((float) right, (float) top);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f((float) left, (float) top);
		gl.glTexCoord2f(0.0F, totalV);
		gl.glVertex2f((float) left, (float) bottom);
		gl.glTexCoord2f(totalU, totalV);
		gl.glVertex2f((float) right, (float) bottom);
		gl.glEnd();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "([I)V")
	protected void uploadPixels(@OriginalArg(0) int[] srcPixels) {
		this.powerOfTwoWidth = IntUtils.clp2(this.width);
		this.powerOfTwoHeight = IntUtils.clp2(this.height);
		@Pc(20) byte[] rgba = new byte[this.powerOfTwoWidth * this.powerOfTwoHeight * 4];
		@Pc(22) int destOff = 0;
		@Pc(24) int srcOff = 0;
		@Pc(32) int rowPadding = (this.powerOfTwoWidth - this.width) * 4;
		for (@Pc(34) int y = 0; y < this.height; y++) {
			for (@Pc(40) int x = 0; x < this.width; x++) {
				@Pc(49) int pixel = srcPixels[srcOff++];
				if (pixel == 0) {
					destOff += 4;
				} else {
					rgba[destOff++] = (byte) (pixel >> 16);
					rgba[destOff++] = (byte) (pixel >> 8);
					rgba[destOff++] = (byte) pixel;
					rgba[destOff++] = -1;
				}
			}
			destOff += rowPadding;
		}
		@Pc(91) ByteBuffer buffer = ByteBuffer.wrap(rgba);
		@Pc(93) GL2 gl = GlRenderer.gl;
		if (this.textureId == -1) {
			@Pc(100) int[] texIds = new int[1];
			gl.glGenTextures(1, texIds, 0);
			this.textureId = texIds[0];
			this.contextId = GlCleaner.contextId;
		}
		GlRenderer.setTextureId(this.textureId);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, this.powerOfTwoWidth, this.powerOfTwoHeight, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buffer);
		GlCleaner.onCard2d += buffer.limit() - this.textureDataSize;
		this.textureDataSize = buffer.limit();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(III)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha) {
		GlRenderer.begin2DModulateAlt();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setTextureFilter(1);
		gl.glColor4f(1.0F, 1.0F, 1.0F, (float) alpha / 256.0F);
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "()V")
	private void compileDisplayList() {
		@Pc(7) float texU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(15) float texV = (float) this.height / (float) this.powerOfTwoHeight;
		@Pc(17) GL2 gl = GlRenderer.gl;
		if (this.displayListId == -1) {
			this.displayListId = gl.glGenLists(1);
			this.contextId = GlCleaner.contextId;
		}
		gl.glNewList(this.displayListId, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(texU, 0.0F);
		gl.glVertex2f((float) this.width, 0.0F);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f(0.0F, 0.0F);
		gl.glTexCoord2f(0.0F, texV);
		gl.glVertex2f(0.0F, (float) -this.height);
		gl.glTexCoord2f(texU, texV);
		gl.glVertex2f((float) this.width, (float) -this.height);
		gl.glEnd();
		gl.glEndList();
	}
}