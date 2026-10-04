package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@OriginalClass("client!hg")
public final class GlTile extends Node {

	@OriginalMember(owner = "client!hg", name = "J", descriptor = "Ljava/nio/ByteBuffer;")
	public static ByteBuffer blendByteBuffer;

	@OriginalMember(owner = "client!hg", name = "U", descriptor = "Lclient!wa;")
	public static Buffer opaqueIndexBuffer;

	@OriginalMember(owner = "client!hg", name = "K", descriptor = "Lclient!wa;")
	public static Buffer blendIndexBuffer;

	@OriginalMember(owner = "client!hg", name = "Q", descriptor = "Ljava/nio/ByteBuffer;")
	public static ByteBuffer opaqueByteBuffer;

	@OriginalMember(owner = "client!hg", name = "s", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer vertexByteBuffer;

	@OriginalMember(owner = "client!hg", name = "t", descriptor = "[F")
	private float[] vertexUnderwaterCoord;

	@OriginalMember(owner = "client!hg", name = "v", descriptor = "[I")
	private int[] vertexColor;

	@OriginalMember(owner = "client!hg", name = "w", descriptor = "[I")
	private int[] faceTileY;

	@OriginalMember(owner = "client!hg", name = "x", descriptor = "[I")
	private int[] faceTileX;

	@OriginalMember(owner = "client!hg", name = "z", descriptor = "[I")
	private int[] vertexZ;

	@OriginalMember(owner = "client!hg", name = "A", descriptor = "Lclient!sc;")
	private HashTable vertexDedup;

	@OriginalMember(owner = "client!hg", name = "B", descriptor = "[F")
	private float[] vertexNormalZ;

	@OriginalMember(owner = "client!hg", name = "E", descriptor = "[[I")
	private int[][] faceVertexIndices;

	@OriginalMember(owner = "client!hg", name = "F", descriptor = "[I")
	private int[] vertexY;

	@OriginalMember(owner = "client!hg", name = "I", descriptor = "[[I")
	private int[][] faceBlendIndices;

	@OriginalMember(owner = "client!hg", name = "L", descriptor = "Lclient!vi;")
	private GlVertexBufferObject vertexVbo;

	@OriginalMember(owner = "client!hg", name = "N", descriptor = "[F")
	private float[] vertexNormalY;

	@OriginalMember(owner = "client!hg", name = "P", descriptor = "[I")
	private int[] facePlane;

	@OriginalMember(owner = "client!hg", name = "R", descriptor = "[F")
	private float[] vertexNormalX;

	@OriginalMember(owner = "client!hg", name = "S", descriptor = "[Z")
	private boolean[] faceBlend;

	@OriginalMember(owner = "client!hg", name = "T", descriptor = "[I")
	private int[] vertexX;

	@OriginalMember(owner = "client!hg", name = "p", descriptor = "I")
	public int vertexCapacity = 0;

	@OriginalMember(owner = "client!hg", name = "D", descriptor = "I")
	private int blendIndexCount = 0;

	@OriginalMember(owner = "client!hg", name = "G", descriptor = "I")
	private int opaqueIndexCount = 0;

	@OriginalMember(owner = "client!hg", name = "q", descriptor = "I")
	public int vertexCount = 0;

	@OriginalMember(owner = "client!hg", name = "r", descriptor = "I")
	public int faceCapacity = 0;

	@OriginalMember(owner = "client!hg", name = "O", descriptor = "I")
	private int faceCount = 0;

	@OriginalMember(owner = "client!hg", name = "y", descriptor = "I")
	public final int texture;

	@OriginalMember(owner = "client!hg", name = "V", descriptor = "F")
	private final float textureScale;

	@OriginalMember(owner = "client!hg", name = "M", descriptor = "Z")
	public final boolean blend;

	@OriginalMember(owner = "client!hg", name = "u", descriptor = "Z")
	private final boolean underwater;

	@OriginalMember(owner = "client!hg", name = "C", descriptor = "I")
	public final int underwaterColor;

	@OriginalMember(owner = "client!hg", name = "<init>", descriptor = "(IFZZI)V")
	public GlTile(@OriginalArg(0) int texture, @OriginalArg(1) float textureScale, @OriginalArg(2) boolean blend, @OriginalArg(3) boolean underwater, @OriginalArg(4) int underwaterColor) {
		this.texture = texture;
		this.textureScale = textureScale;
		this.blend = blend;
		this.underwater = underwater;
		this.underwaterColor = underwaterColor;
	}

	@OriginalMember(owner = "client!hg", name = "a", descriptor = "()V")
	public static void resetStaticBuffers() {
		opaqueIndexBuffer = null;
		blendIndexBuffer = null;
		opaqueByteBuffer = null;
		blendByteBuffer = null;
	}

	@OriginalMember(owner = "client!hg", name = "b", descriptor = "()V")
	public final void allocateBuffers() {
		this.vertexX = new int[this.vertexCapacity];
		this.vertexY = new int[this.vertexCapacity];
		this.vertexZ = new int[this.vertexCapacity];
		if (this.underwater) {
			this.vertexUnderwaterCoord = new float[this.vertexCapacity];
		}
		this.vertexColor = new int[this.vertexCapacity];
		this.vertexNormalX = new float[this.vertexCapacity];
		this.vertexNormalY = new float[this.vertexCapacity];
		this.vertexNormalZ = new float[this.vertexCapacity];
		this.faceTileX = new int[this.faceCapacity];
		this.faceTileY = new int[this.faceCapacity];
		this.facePlane = new int[this.faceCapacity];
		this.faceVertexIndices = new int[this.faceCapacity][];
		this.vertexDedup = new HashTable(IntUtils.clp2(this.vertexCapacity));
		if (this.blend) {
			this.faceBlendIndices = new int[this.faceCapacity][];
			this.faceBlend = new boolean[this.faceCapacity];
		}
	}

	@OriginalMember(owner = "client!hg", name = "a", descriptor = "(IIIFFFIF)I")
	public final int addVertex(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) float normalX, @OriginalArg(4) float normalY, @OriginalArg(5) float normalZ, @OriginalArg(6) int color, @OriginalArg(7) float underwaterCoord) {
		@Pc(1) long dedupKey = 0L;
		if ((x & 0x7F) == 0 || (z & 0x7F) == 0) {
			dedupKey = (long) (x + (z << 16)) + ((long) color << 32);
			@Pc(28) IntNode existing = (IntNode) this.vertexDedup.get(dedupKey);
			if (existing != null) {
				if (y < this.vertexY[existing.value]) {
					this.vertexY[existing.value] = y;
				}
				return existing.value;
			}
		}
		this.vertexX[this.vertexCount] = x;
		this.vertexY[this.vertexCount] = y;
		this.vertexZ[this.vertexCount] = z;
		if (this.underwater) {
			this.vertexUnderwaterCoord[this.vertexCount] = underwaterCoord;
		}
		this.vertexNormalX[this.vertexCount] = normalX;
		this.vertexNormalY[this.vertexCount] = normalY;
		this.vertexNormalZ[this.vertexCount] = normalZ;
		this.vertexColor[this.vertexCount] = color;
		if (dedupKey != 0L) {
			this.vertexDedup.put(new IntNode(this.vertexCount), dedupKey);
		}
		return this.vertexCount++;
	}

	@OriginalMember(owner = "client!hg", name = "d", descriptor = "()V")
	public final void uploadVertexData() {
		@Pc(12) Buffer buf = new Buffer((this.underwater ? 40 : 36) * this.vertexCount);
		for (@Pc(14) int i = 0; i < this.vertexCount; i++) {
			if (GlRenderer.bigEndian) {
				buf.pFloat((float) this.vertexX[i]);
				buf.pFloat((float) this.vertexY[i]);
				buf.pFloat((float) this.vertexZ[i]);
				buf.p4(this.vertexColor[i]);
				buf.pFloat(this.vertexNormalX[i]);
				buf.pFloat(this.vertexNormalY[i]);
				buf.pFloat(this.vertexNormalZ[i]);
				buf.pFloat((float) this.vertexX[i] / this.textureScale);
				buf.pFloat((float) this.vertexZ[i] / this.textureScale);
				if (this.underwater) {
					buf.pFloat(this.vertexUnderwaterCoord[i]);
				}
			} else {
				buf.gFloat((float) this.vertexX[i]);
				buf.gFloat((float) this.vertexY[i]);
				buf.gFloat((float) this.vertexZ[i]);
				buf.p4(this.vertexColor[i]);
				buf.gFloat(this.vertexNormalX[i]);
				buf.gFloat(this.vertexNormalY[i]);
				buf.gFloat(this.vertexNormalZ[i]);
				buf.gFloat((float) this.vertexX[i] / this.textureScale);
				buf.gFloat((float) this.vertexZ[i] / this.textureScale);
				if (this.underwater) {
					buf.gFloat(this.vertexUnderwaterCoord[i]);
				}
			}
		}
		if (GlRenderer.arbVboSupported) {
			@Pc(200) ByteBuffer wrapped = ByteBuffer.wrap(buf.data, 0, buf.offset);
			this.vertexVbo = new GlVertexBufferObject();
			this.vertexVbo.setArrayBuffer(wrapped);
		} else {
			this.vertexByteBuffer = ByteBuffer.allocateDirect(buf.offset).order(ByteOrder.nativeOrder());
			this.vertexByteBuffer.put(buf.data, 0, buf.offset);
			this.vertexByteBuffer.flip();
		}
		this.vertexX = null;
		this.vertexY = null;
		this.vertexZ = null;
		this.vertexColor = null;
		this.vertexNormalX = null;
		this.vertexNormalY = null;
		this.vertexNormalZ = null;
		this.vertexDedup = null;
		this.vertexUnderwaterCoord = null;
	}

	@OriginalMember(owner = "client!hg", name = "a", descriptor = "([[[Lclient!bj;FZ)V")
	public final void renderTiles(@OriginalArg(0) Tile[][][] tiles, @OriginalArg(1) float depthLayer, @OriginalArg(2) boolean depthOnly) {
		if (opaqueIndexBuffer == null || opaqueIndexBuffer.data.length < this.opaqueIndexCount * 4) {
			opaqueIndexBuffer = new Buffer(this.opaqueIndexCount * 4);
		} else {
			opaqueIndexBuffer.offset = 0;
		}
		if (blendIndexBuffer == null || blendIndexBuffer.data.length < this.blendIndexCount * 4) {
			blendIndexBuffer = new Buffer(this.blendIndexCount * 4);
		} else {
			blendIndexBuffer.offset = 0;
		}
		@Pc(47) int f;
		@Pc(68) Tile tile;
		@Pc(111) Buffer targetBuffer;
		@Pc(78) int[] vertexIndices;
		@Pc(86) int[] blendIndices;
		@Pc(90) int j;
		@Pc(116) int v;
		if (GlRenderer.bigEndian) {
			for (f = 0; f < this.faceCount; f++) {
				tile = tiles[this.facePlane[f]][this.faceTileX[f]][this.faceTileY[f]];
				if (tile != null && tile.visible) {
					vertexIndices = this.faceVertexIndices[f];
					if (this.blend) {
						blendIndices = this.faceBlendIndices[f];
						if (blendIndices != null) {
							for (j = 0; j < blendIndices.length; j++) {
								blendIndexBuffer.p4(blendIndices[j]);
							}
						}
						targetBuffer = this.faceBlend[f] ? blendIndexBuffer : opaqueIndexBuffer;
					} else {
						targetBuffer = opaqueIndexBuffer;
					}
					for (v = 1; v < vertexIndices.length - 1; v++) {
						targetBuffer.p4(vertexIndices[0]);
						targetBuffer.p4(vertexIndices[v]);
						targetBuffer.p4(vertexIndices[v + 1]);
					}
				}
			}
		} else {
			for (f = 0; f < this.faceCount; f++) {
				tile = tiles[this.facePlane[f]][this.faceTileX[f]][this.faceTileY[f]];
				if (tile != null && tile.visible) {
					vertexIndices = this.faceVertexIndices[f];
					if (this.blend) {
						blendIndices = this.faceBlendIndices[f];
						if (blendIndices != null) {
							for (j = 0; j < blendIndices.length; j++) {
								blendIndexBuffer.ip4(blendIndices[j]);
							}
						}
						targetBuffer = this.faceBlend[f] ? blendIndexBuffer : opaqueIndexBuffer;
					} else {
						targetBuffer = opaqueIndexBuffer;
					}
					for (v = 1; v < vertexIndices.length - 1; v++) {
						targetBuffer.ip4(vertexIndices[0]);
						targetBuffer.ip4(vertexIndices[v]);
						targetBuffer.ip4(vertexIndices[v + 1]);
					}
				}
			}
		}
		if (opaqueIndexBuffer.offset == 0 && blendIndexBuffer.offset == 0) {
			return;
		}
		@Pc(257) GL2 gl = GlRenderer.gl;
		if (this.texture == -1 || depthOnly) {
			GlRenderer.setTextureId(-1);
			MaterialManager.setMaterial(0, 0);
		} else {
			Rasteriser.textureProvider.bindTexture(this.texture);
		}
		@Pc(282) int stride = this.underwater ? 40 : 36;
		if (this.vertexVbo == null) {
			if (GlRenderer.arbVboSupported) {
				gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
			}
			this.vertexByteBuffer.position(0);
			gl.glVertexPointer(3, GL2.GL_FLOAT, stride, this.vertexByteBuffer);
			this.vertexByteBuffer.position(12);
			gl.glColorPointer(4, GL2.GL_UNSIGNED_BYTE, stride, this.vertexByteBuffer);
			if (Preferences.highDetailLighting) {
				this.vertexByteBuffer.position(16);
				gl.glNormalPointer(GL2.GL_FLOAT, stride, this.vertexByteBuffer);
			}
			this.vertexByteBuffer.position(28);
			gl.glTexCoordPointer(2, GL2.GL_FLOAT, stride, this.vertexByteBuffer);
			if (this.underwater) {
				gl.glClientActiveTexture(UnderwaterMaterialRenderer.getSecondaryTextureUnit());
				this.vertexByteBuffer.position(36);
				gl.glTexCoordPointer(1, GL2.GL_FLOAT, stride, this.vertexByteBuffer);
				gl.glClientActiveTexture(GL2.GL_TEXTURE0);
			}
		} else {
			this.vertexVbo.bindArray();
			gl.glVertexPointer(3, GL2.GL_FLOAT, stride, 0L);
			gl.glColorPointer(4, GL2.GL_UNSIGNED_BYTE, stride, 12L);
			if (Preferences.highDetailLighting) {
				gl.glNormalPointer(GL2.GL_FLOAT, stride, 16L);
			}
			gl.glTexCoordPointer(2, GL2.GL_FLOAT, stride, 28L);
			if (this.underwater) {
				gl.glClientActiveTexture(UnderwaterMaterialRenderer.getSecondaryTextureUnit());
				gl.glTexCoordPointer(1, GL2.GL_FLOAT, stride, 36L);
				gl.glClientActiveTexture(GL2.GL_TEXTURE0);
			}
		}
		if (GlRenderer.arbVboSupported) {
			gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
		if (opaqueIndexBuffer.offset != 0) {
			if (opaqueByteBuffer == null || opaqueByteBuffer.capacity() < opaqueIndexBuffer.offset) {
				opaqueByteBuffer = ByteBuffer.allocateDirect(opaqueIndexBuffer.offset).order(ByteOrder.nativeOrder());
			} else {
				opaqueByteBuffer.clear();
			}
			opaqueByteBuffer.put(opaqueIndexBuffer.data, 0, opaqueIndexBuffer.offset);
			opaqueByteBuffer.flip();
			GlRenderer.setDepthLayer(depthLayer);
			gl.glDrawElements(GL2.GL_TRIANGLES, opaqueIndexBuffer.offset / 4, GL2.GL_UNSIGNED_INT, opaqueByteBuffer);
		}
		if (blendIndexBuffer.offset == 0) {
			return;
		}
		if (blendByteBuffer == null || blendByteBuffer.capacity() < blendIndexBuffer.offset) {
			blendByteBuffer = ByteBuffer.allocateDirect(blendIndexBuffer.offset).order(ByteOrder.nativeOrder());
		} else {
			blendByteBuffer.clear();
		}
		blendByteBuffer.put(blendIndexBuffer.data, 0, blendIndexBuffer.offset);
		blendByteBuffer.flip();
		GlRenderer.setDepthLayer(depthLayer - 100.0F);
		GlRenderer.disableDepthMask();
		gl.glDrawElements(GL2.GL_TRIANGLES, blendIndexBuffer.offset / 4, GL2.GL_UNSIGNED_INT, blendByteBuffer);
		GlRenderer.enableDepthMask();
	}

	@OriginalMember(owner = "client!hg", name = "a", descriptor = "(III[I[IZ)I")
	public final int addFace(@OriginalArg(0) int plane, @OriginalArg(1) int tileX, @OriginalArg(2) int tileY, @OriginalArg(3) int[] vertexIndices, @OriginalArg(4) int[] blendIndices, @OriginalArg(5) boolean isBlend) {
		if (this.blend) {
			this.faceBlendIndices[this.faceCount] = blendIndices;
			this.faceBlend[this.faceCount] = isBlend;
			if (blendIndices != null) {
				this.blendIndexCount += blendIndices.length;
			}
			if (isBlend) {
				this.blendIndexCount += (vertexIndices.length - 2) * 3;
			} else {
				this.opaqueIndexCount += (vertexIndices.length - 2) * 3;
			}
		} else {
			this.opaqueIndexCount += (vertexIndices.length - 2) * 3;
		}
		this.facePlane[this.faceCount] = plane;
		this.faceTileX[this.faceCount] = tileX;
		this.faceTileY[this.faceCount] = tileY;
		this.faceVertexIndices[this.faceCount] = vertexIndices;
		return this.faceCount++;
	}
}
