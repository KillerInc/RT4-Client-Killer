package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@OriginalClass("client!wm")
public final class Shadow {

	@OriginalMember(owner = "client!wm", name = "g", descriptor = "[B")
	public static final byte[] pixels = new byte[16384];
	@OriginalMember(owner = "client!wm", name = "a", descriptor = "Lclient!vi;")
	private GlVertexBufferObject indexVbo;

	@OriginalMember(owner = "client!wm", name = "c", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer indexBuffer;

	@OriginalMember(owner = "client!wm", name = "e", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer vertexBuffer;

	@OriginalMember(owner = "client!wm", name = "f", descriptor = "Lclient!vi;")
	private GlVertexBufferObject vertexVbo;

	@OriginalMember(owner = "client!wm", name = "b", descriptor = "Z")
	public boolean outputToSprite = true;

	@OriginalMember(owner = "client!wm", name = "h", descriptor = "I")
	private int pixelHash = -1;

	@OriginalMember(owner = "client!wm", name = "d", descriptor = "I")
	private final int textureId;

	@OriginalMember(owner = "client!wm", name = "<init>", descriptor = "()V")
	public Shadow() {
		@Pc(9) GL2 gl = GlRenderer.gl;
		@Pc(12) int[] texIds = new int[1];
		gl.glGenTextures(1, texIds, 0);
		this.textureId = texIds[0];
		GlCleaner.onCardTexture += 16384;
		GlRenderer.setTextureId(this.textureId);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
	}

	@OriginalMember(owner = "client!wm", name = "a", descriptor = "([[III)V")
	public final void buildShadowMesh(@OriginalArg(0) int[][] heightmap, @OriginalArg(1) int tileX, @OriginalArg(2) int tileY) {
		@Pc(4) Buffer vtxBuf = new Buffer(1620);
		@Pc(11) int x;
		for (@Pc(6) int y = 0; y <= 8; y++) {
			for (x = 0; x <= 8; x++) {
				if (GlRenderer.bigEndian) {
					vtxBuf.pFloat((float) x / 8.0F);
					vtxBuf.pFloat((float) y / 8.0F);
					vtxBuf.pFloat((float) (x * 128));
					vtxBuf.pFloat((float) heightmap[x + tileX][y + tileY]);
					vtxBuf.pFloat((float) (y * 128));
				} else {
					vtxBuf.gFloat((float) x / 8.0F);
					vtxBuf.gFloat((float) y / 8.0F);
					vtxBuf.gFloat((float) (x * 128));
					vtxBuf.gFloat((float) heightmap[x + tileX][y + tileY]);
					vtxBuf.gFloat((float) (y * 128));
				}
			}
		}
		if (GlRenderer.arbVboSupported) {
			@Pc(112) ByteBuffer vtxData = ByteBuffer.wrap(vtxBuf.data, 0, vtxBuf.offset);
			this.vertexVbo = new GlVertexBufferObject();
			this.vertexVbo.setArrayBuffer(vtxData);
		} else {
			this.vertexBuffer = ByteBuffer.allocateDirect(vtxBuf.offset).order(ByteOrder.nativeOrder());
			this.vertexBuffer.put(vtxBuf.data, 0, vtxBuf.offset);
			this.vertexBuffer.flip();
		}
		@Pc(147) Buffer idxBuf = new Buffer(1536);
		for (x = 0; x < 8; x++) {
			for (@Pc(154) int col = 0; col < 8; col++) {
				if (GlRenderer.bigEndian) {
					idxBuf.p4(col + (x + 1) * 9);
					idxBuf.p4(col + x * 9);
					idxBuf.p4(col + x * 9 + 1);
					idxBuf.p4(col + (x + 1) * 9);
					idxBuf.p4(col + x * 9 + 1);
					idxBuf.p4(col + (x + 1) * 9 + 1);
				} else {
					idxBuf.ip4(col + (x + 1) * 9);
					idxBuf.ip4(col + x * 9);
					idxBuf.ip4(col + x * 9 + 1);
					idxBuf.ip4(col + (x + 1) * 9);
					idxBuf.ip4(col + x * 9 + 1);
					idxBuf.ip4(col + (x + 1) * 9 + 1);
				}
			}
		}
		if (GlRenderer.arbVboSupported) {
			@Pc(293) ByteBuffer idxData = ByteBuffer.wrap(idxBuf.data, 0, idxBuf.offset);
			this.indexVbo = new GlVertexBufferObject();
			this.indexVbo.setElementArrayBuffer(idxData);
		} else {
			this.indexBuffer = ByteBuffer.allocateDirect(idxBuf.offset).order(ByteOrder.nativeOrder());
			this.indexBuffer.put(idxBuf.data, 0, idxBuf.offset);
			this.indexBuffer.flip();
		}
	}

	@OriginalMember(owner = "client!wm", name = "a", descriptor = "(Lclient!ek;II)Z")
	public final boolean updateShadowTexture(@OriginalArg(0) SoftwareIndexedSprite sprite, @OriginalArg(1) int offsetX, @OriginalArg(2) int offsetY) {
		@Pc(2) byte[] src = sprite.pixels;
		@Pc(5) int stride = sprite.width;
		@Pc(19) int idx = offsetX * 128 + (offsetY * 128 + 1) * stride + 1;
		@Pc(21) int hash = 0;
		@Pc(23) int row;
		@Pc(33) int col;
		for (row = -128; row < 0; row++) {
			hash = (hash << 8) - hash;
			for (col = -128; col < 0; col++) {
				if (src[idx++] != 0) {
					hash++;
				}
			}
			idx += stride - 128;
		}
		if (hash == this.pixelHash) {
			return false;
		}
		this.pixelHash = hash;
		idx = offsetX * 128 + (offsetY * 128 + 1) * stride + 1;
		row = 0;
		for (col = -128; col < 0; col++) {
			for (@Pc(82) int x = -128; x < 0; x++) {
				if (src[idx] == 0) {
					@Pc(96) int neighbors = 0;
					if (src[idx - 1] != 0) {
						neighbors++;
					}
					if (src[idx + 1] != 0) {
						neighbors++;
					}
					if (src[idx - stride] != 0) {
						neighbors++;
					}
					if (src[idx + stride] != 0) {
						neighbors++;
					}
					pixels[row++] = (byte) (neighbors * 17);
				} else {
					pixels[row++] = 68;
				}
				idx++;
			}
			idx += stride - 128;
		}
		@Pc(145) GL2 gl = GlRenderer.gl;
		@Pc(148) ByteBuffer texData = ByteBuffer.wrap(pixels);
		texData.limit(16384);
		GlRenderer.setTextureId(this.textureId);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_ALPHA, 128, 128, 0, GL2.GL_ALPHA, GL2.GL_UNSIGNED_BYTE, texData);
		return true;
	}

	@OriginalMember(owner = "client!wm", name = "b", descriptor = "()V")
	public final void renderShadow() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureId(this.textureId);
		if (this.vertexVbo == null) {
			if (GlRenderer.arbVboSupported) {
				gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			}
			gl.glInterleavedArrays(GL2.GL_T2F_V3F, 20, this.vertexBuffer);
			GlRenderer.normalArrayEnabled = false;
		} else {
			this.vertexVbo.bindArray();
			gl.glInterleavedArrays(GL2.GL_T2F_V3F, 20, 0L);
			GlRenderer.normalArrayEnabled = false;
		}
		if (this.indexVbo == null) {
			if (GlRenderer.arbVboSupported) {
				gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
			}
			gl.glDrawElements(GL2.GL_TRIANGLES, 384, GL2.GL_UNSIGNED_INT, this.indexBuffer);
		} else {
			this.indexVbo.bindElementArray();
			gl.glDrawElements(GL2.GL_TRIANGLES, 384, GL2.GL_UNSIGNED_INT, 0L);
		}
	}
}
