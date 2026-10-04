package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!oh")
public final class GlIndexedSprite extends IndexedSprite {

	@OriginalMember(owner = "client!oh", name = "r", descriptor = "I")
	private int contextId;

	@OriginalMember(owner = "client!oh", name = "s", descriptor = "I")
	private int powerOfTwoHeight;

	@OriginalMember(owner = "client!oh", name = "t", descriptor = "I")
	private int powerOfTwoWidth;

	@OriginalMember(owner = "client!oh", name = "n", descriptor = "I")
	private int textureId = -1;

	@OriginalMember(owner = "client!oh", name = "p", descriptor = "I")
	private int filterMode = 0;

	@OriginalMember(owner = "client!oh", name = "o", descriptor = "I")
	private int displayListId = -1;

	@OriginalMember(owner = "client!oh", name = "q", descriptor = "I")
	private int textureDataSize = 0;

	public byte[] pixels;

	public int[] pallet;

	@OriginalMember(owner = "client!oh", name = "<init>", descriptor = "(IIIIII[B[I)V")
	public GlIndexedSprite(@OriginalArg(0) int innerWidth, @OriginalArg(1) int innerHeight, @OriginalArg(2) int offsetX, @OriginalArg(3) int offsetY, @OriginalArg(4) int width, @OriginalArg(5) int height, @OriginalArg(6) byte[] pixels, @OriginalArg(7) int[] palette) {
		this.innerWidth = innerWidth;
		this.innerHeight = innerHeight;
		this.xOffset = offsetX;
		this.yOffset = offsetY;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.pallet = palette;
		this.uploadPixels(pixels, palette);
		this.compileDisplayList();
	}

	@OriginalMember(owner = "client!oh", name = "a", descriptor = "([B[I)V")
	private void uploadPixels(@OriginalArg(0) byte[] pixels, @OriginalArg(1) int[] palette) {
		this.powerOfTwoWidth = IntUtils.clp2(this.width);
		this.powerOfTwoHeight = IntUtils.clp2(this.height);
		@Pc(20) byte[] rgba = new byte[this.powerOfTwoWidth * this.powerOfTwoHeight * 4];
		@Pc(22) int destIdx = 0;
		@Pc(24) int srcIdx = 0;
		for (@Pc(26) int y = 0; y < this.height; y++) {
			for (@Pc(32) int x = 0; x < this.width; x++) {
				@Pc(41) byte paletteIdx = pixels[srcIdx++];
				if (paletteIdx == 0) {
					destIdx += 4;
				} else {
					@Pc(47) int color = palette[paletteIdx];
					rgba[destIdx++] = (byte) (color >> 16);
					rgba[destIdx++] = (byte) (color >> 8);
					rgba[destIdx++] = (byte) color;
					rgba[destIdx++] = -1;
				}
			}
			destIdx += (this.powerOfTwoWidth - this.width) * 4;
		}
		@Pc(93) ByteBuffer buf = ByteBuffer.wrap(rgba);
		@Pc(95) GL2 gl = GlRenderer.gl;
		if (this.textureId == -1) {
			@Pc(102) int[] texIds = new int[1];
			gl.glGenTextures(1, texIds, 0);
			this.textureId = texIds[0];
			this.contextId = GlCleaner.contextId;
		}
		GlRenderer.setTextureId(this.textureId);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, this.powerOfTwoWidth, this.powerOfTwoHeight, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buf);
		GlCleaner.onCard2d += buf.limit() - this.textureDataSize;
		this.textureDataSize = buf.limit();
	}

	@OriginalMember(owner = "client!oh", name = "a", descriptor = "(III)V")
	@Override
	public final void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha) {
		GlRenderer.begin2DModulateAlt();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setNearestFilter();
		gl.glColor4f(1.0F, 1.0F, 1.0F, (float) alpha / 256.0F);
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!oh", name = "b", descriptor = "(I)V")
	private void setNearestFilter() {
		if (this.filterMode != 1) {
			this.filterMode = 1;
			@Pc(9) GL2 gl = GlRenderer.gl;
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		}
	}

	@OriginalMember(owner = "client!oh", name = "a", descriptor = "(II)V")
	@Override
	public final void renderTransparent(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		GlRenderer.begin2DReplace();
		@Pc(5) int drawX = x + this.xOffset;
		@Pc(10) int drawY = y + this.yOffset;
		@Pc(12) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		this.setNearestFilter();
		gl.glTranslatef((float) drawX, (float) (GlRenderer.canvasHeight - drawY), 0.0F);
		gl.glCallList(this.displayListId);
		gl.glLoadIdentity();
	}

	@OriginalMember(owner = "client!oh", name = "finalize", descriptor = "()V")
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

	@OriginalMember(owner = "client!oh", name = "a", descriptor = "()V")
	private void compileDisplayList() {
		@Pc(7) float maxU = (float) this.width / (float) this.powerOfTwoWidth;
		@Pc(15) float maxV = (float) this.height / (float) this.powerOfTwoHeight;
		@Pc(17) GL2 gl = GlRenderer.gl;
		if (this.displayListId == -1) {
			this.displayListId = gl.glGenLists(1);
			this.contextId = GlCleaner.contextId;
		}
		gl.glNewList(this.displayListId, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(maxU, 0.0F);
		gl.glVertex2f((float) this.width, 0.0F);
		gl.glTexCoord2f(0.0F, 0.0F);
		gl.glVertex2f(0.0F, 0.0F);
		gl.glTexCoord2f(0.0F, maxV);
		gl.glVertex2f(0.0F, (float) -this.height);
		gl.glTexCoord2f(maxU, maxV);
		gl.glVertex2f((float) this.width, (float) -this.height);
		gl.glEnd();
		gl.glEndList();
	}
}
