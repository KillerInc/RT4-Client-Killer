package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!fj")
public final class LightMesh {

	@OriginalMember(owner = "client!fj", name = "a", descriptor = "Lclient!vi;")
	private GlVertexBufferObject vertexVbo;

	@OriginalMember(owner = "client!fj", name = "b", descriptor = "[I")
	private int[] indices;

	@OriginalMember(owner = "client!fj", name = "c", descriptor = "[B")
	private byte[] colorG;

	@OriginalMember(owner = "client!fj", name = "d", descriptor = "[I")
	private int[] vertexX;

	@OriginalMember(owner = "client!fj", name = "e", descriptor = "Lclient!vi;")
	private GlVertexBufferObject indexVbo;

	@OriginalMember(owner = "client!fj", name = "f", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer vertexBuffer;

	@OriginalMember(owner = "client!fj", name = "g", descriptor = "[I")
	private int[] vertexZ;

	@OriginalMember(owner = "client!fj", name = "h", descriptor = "[I")
	private int[] vertexY;

	@OriginalMember(owner = "client!fj", name = "i", descriptor = "[B")
	private byte[] colorB;

	@OriginalMember(owner = "client!fj", name = "j", descriptor = "I")
	public int indexCapacity;

	@OriginalMember(owner = "client!fj", name = "k", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer indexBuffer;

	@OriginalMember(owner = "client!fj", name = "l", descriptor = "I")
	public int vertexCount;

	@OriginalMember(owner = "client!fj", name = "m", descriptor = "[B")
	private byte[] colorR;

	@OriginalMember(owner = "client!fj", name = "n", descriptor = "I")
	public int indexCount;

	@OriginalMember(owner = "client!fj", name = "o", descriptor = "Lclient!sc;")
	private HashTable vertexCache;

	@OriginalMember(owner = "client!fj", name = "p", descriptor = "I")
	public int vertexCapacity;

	@OriginalMember(owner = "client!fj", name = "a", descriptor = "(Lclient!gi;IIIFFF)I")
	public final int addVertex(@OriginalArg(0) Light light, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) float normalX, @OriginalArg(5) float normalY, @OriginalArg(6) float normalZ) {
		@Pc(1) long key = 0L;
		if ((x & 0x7F) == 0 || (z & 0x7F) == 0) {
			key = x + (z << 16);
			@Pc(23) IntNode cached = (IntNode) this.vertexCache.get(key);
			if (cached != null) {
				return cached.value;
			}
		}
		@Pc(31) int color = light.color;
		@Pc(37) float dx = (float) (light.x - x);
		@Pc(43) float dy = (float) (light.y - y);
		@Pc(49) float dz = (float) (light.z - z);
		@Pc(64) float dist = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		@Pc(68) float invDist = 1.0F / dist;
		@Pc(72) float dirX = dx * invDist;
		@Pc(76) float dirY = dy * invDist;
		@Pc(80) float dirZ = dz * invDist;
		@Pc(90) float attenuation = dist / (float) ((light.radius << 7) + 64);
		@Pc(96) float falloff = 1.0F - attenuation * attenuation;
		if (falloff < 0.0F) {
			falloff = 0.0F;
		}
		@Pc(114) float dot = dirX * normalX + dirY * normalY + dirZ * normalZ;
		if (dot < 0.0F) {
			dot = 0.0F;
		}
		@Pc(126) float intensity = dot * falloff * 2.0F;
		if (intensity > 1.0F) {
			intensity = 1.0F;
		}
		@Pc(142) int r = (int) (intensity * (float) (color >> 16 & 0xFF));
		if (r > 255) {
			r = 255;
		}
		@Pc(157) int g = (int) (intensity * (float) (color >> 8 & 0xFF));
		if (g > 255) {
			g = 255;
		}
		@Pc(170) int b = (int) (intensity * (float) (color & 0xFF));
		if (b > 255) {
			b = 255;
		}
		this.colorR[this.vertexCount] = (byte) r;
		this.colorG[this.vertexCount] = (byte) g;
		this.colorB[this.vertexCount] = (byte) b;
		this.vertexX[this.vertexCount] = x;
		this.vertexY[this.vertexCount] = y;
		this.vertexZ[this.vertexCount] = z;
		this.vertexCache.put(new IntNode(this.vertexCount), key);
		return this.vertexCount++;
	}

	@OriginalMember(owner = "client!fj", name = "a", descriptor = "()V")
	public final void upload() {
		@Pc(7) Buffer idxBuf = new Buffer(this.indexCount * 4);
		@Pc(15) Buffer vtxBuf = new Buffer(this.vertexCount * 16);
		@Pc(19) int i;
		if (GlRenderer.bigEndian) {
			for (i = 0; i < this.vertexCount; i++) {
				vtxBuf.p1(this.colorR[i]);
				vtxBuf.p1(this.colorG[i]);
				vtxBuf.p1(this.colorB[i]);
				vtxBuf.p1(255);
				vtxBuf.pFloat((float) this.vertexX[i]);
				vtxBuf.pFloat((float) this.vertexY[i]);
				vtxBuf.pFloat((float) this.vertexZ[i]);
			}
			for (i = 0; i < this.indexCount; i++) {
				idxBuf.p4(this.indices[i]);
			}
		} else {
			for (i = 0; i < this.vertexCount; i++) {
				vtxBuf.p1(this.colorR[i]);
				vtxBuf.p1(this.colorG[i]);
				vtxBuf.p1(this.colorB[i]);
				vtxBuf.p1(255);
				vtxBuf.gFloat((float) this.vertexX[i]);
				vtxBuf.gFloat((float) this.vertexY[i]);
				vtxBuf.gFloat((float) this.vertexZ[i]);
			}
			for (i = 0; i < this.indexCount; i++) {
				idxBuf.ip4(this.indices[i]);
			}
		}
		if (GlRenderer.arbVboSupported) {
			this.vertexVbo = new GlVertexBufferObject();
			@Pc(173) ByteBuffer vtxData = ByteBuffer.wrap(vtxBuf.data);
			this.vertexVbo.setArrayBuffer(vtxData);
			this.indexVbo = new GlVertexBufferObject();
			@Pc(186) ByteBuffer idxData = ByteBuffer.wrap(idxBuf.data);
			this.indexVbo.setElementArrayBuffer(idxData);
		} else {
			this.vertexBuffer = ByteBuffer.allocateDirect(vtxBuf.offset);
			this.vertexBuffer.put(vtxBuf.data);
			this.vertexBuffer.flip();
			this.indexBuffer = ByteBuffer.allocateDirect(idxBuf.offset);
			this.indexBuffer.put(idxBuf.data);
			this.indexBuffer.flip();
		}
		this.vertexX = null;
		this.vertexY = null;
		this.vertexZ = null;
		this.colorR = null;
		this.colorG = null;
		this.colorB = null;
		this.indices = null;
		this.vertexCache = null;
	}

	@OriginalMember(owner = "client!fj", name = "b", descriptor = "()V")
	public final void allocate() {
		this.indices = new int[this.indexCapacity];
		this.vertexX = new int[this.vertexCapacity];
		this.vertexY = new int[this.vertexCapacity];
		this.vertexZ = new int[this.vertexCapacity];
		this.colorR = new byte[this.vertexCapacity];
		this.colorG = new byte[this.vertexCapacity];
		this.colorB = new byte[this.vertexCapacity];
		this.vertexCache = new HashTable(IntUtils.clp2(this.vertexCapacity));
	}

	@OriginalMember(owner = "client!fj", name = "c", descriptor = "()V")
	public final void draw() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		if (GlRenderer.arbVboSupported) {
			this.vertexVbo.bindArray();
			gl.glInterleavedArrays(GL2.GL_C4UB_V3F, 16, 0L);
			GlRenderer.normalArrayEnabled = false;
			this.indexVbo.bindElementArray();
			gl.glDrawElements(GL2.GL_TRIANGLES, this.indexCount, GL2.GL_UNSIGNED_INT, 0L);
			return;
		}
		if (GlRenderer.arbVboSupported) {
			gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		gl.glInterleavedArrays(GL2.GL_C4UB_V3F, 16, this.vertexBuffer);
		GlRenderer.normalArrayEnabled = false;
		gl.glDrawElements(GL2.GL_TRIANGLES, this.indexCount, GL2.GL_UNSIGNED_INT, this.indexBuffer);
	}

	@OriginalMember(owner = "client!fj", name = "a", descriptor = "([I)V")
	public final void addFan(@OriginalArg(0) int[] fan) {
		for (@Pc(1) int i = 1; i < fan.length - 1; i++) {
			this.indices[this.indexCount++] = fan[0];
			this.indices[this.indexCount++] = fan[i];
			this.indices[this.indexCount++] = fan[i + 1];
		}
	}
}
