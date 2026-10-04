package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!el")
public final class GlAlphaSprite extends GlSprite {

	@OriginalMember(owner = "client!el", name = "<init>", descriptor = "(IIIIII[I)V")
	public GlAlphaSprite(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(2) int offsetX, @OriginalArg(3) int offsetY, @OriginalArg(4) int innerWidth, @OriginalArg(5) int innerHeight, @OriginalArg(6) int[] pixels) {
		super(width, height, offsetX, offsetY, innerWidth, innerHeight, pixels);
	}

	@OriginalMember(owner = "client!el", name = "<init>", descriptor = "(Lclient!mm;)V")
	public GlAlphaSprite(@OriginalArg(0) SoftwareSprite sprite) {
		super(sprite);
	}

	@OriginalMember(owner = "client!el", name = "a", descriptor = "([I)V")
	@Override
	protected final void uploadPixels(@OriginalArg(0) int[] pixels) {
		this.powerOfTwoWidth = IntUtils.clp2(this.width);
		this.powerOfTwoHeight = IntUtils.clp2(this.height);
		@Pc(20) byte[] rgba = new byte[this.powerOfTwoWidth * this.powerOfTwoHeight * 4];
		@Pc(22) int destIdx = 0;
		@Pc(24) int srcIdx = 0;
		@Pc(32) int rowPad = (this.powerOfTwoWidth - this.width) * 4;
		for (@Pc(34) int y = 0; y < this.height; y++) {
			for (@Pc(40) int x = 0; x < this.width; x++) {
				@Pc(49) int pixel = pixels[srcIdx++];
				if (pixel == 0) {
					destIdx += 4;
				} else {
					rgba[destIdx++] = (byte) (pixel >> 16);
					rgba[destIdx++] = (byte) (pixel >> 8);
					rgba[destIdx++] = (byte) pixel;
					rgba[destIdx++] = (byte) (pixel >> 24);
				}
			}
			destIdx += rowPad;
		}
		@Pc(94) ByteBuffer buf = ByteBuffer.wrap(rgba);
		@Pc(96) GL2 gl = GlRenderer.gl;
		if (this.textureId == -1) {
			@Pc(103) int[] texId = new int[1];
			gl.glGenTextures(1, texId, 0);
			this.textureId = texId[0];
		}
		GlRenderer.setTextureId(this.textureId);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, this.powerOfTwoWidth, this.powerOfTwoHeight, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buf);
		GlCleaner.onCard2d += buf.limit() - this.textureDataSize;
		this.textureDataSize = buf.limit();
	}
}
