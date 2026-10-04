package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!uh")
public final class GlTexture extends SecondaryNode {

	@OriginalMember(owner = "client!oj", name = "t", descriptor = "[I")
	public static int[] scrollBuffer;
	@OriginalMember(owner = "client!uh", name = "K", descriptor = "F")
	private float animationProgress;

	@OriginalMember(owner = "client!uh", name = "X", descriptor = "I")
	private int contextId;

	@OriginalMember(owner = "client!uh", name = "Z", descriptor = "[I")
	private int[] animatedPixels;

	@OriginalMember(owner = "client!uh", name = "bb", descriptor = "I")
	private int textureId = -1;

	@OriginalMember(owner = "client!uh", name = "eb", descriptor = "Z")
	public boolean animationDirty = false;

	@OriginalMember(owner = "client!uh", name = "db", descriptor = "I")
	private int textureSize = 0;

	@OriginalMember(owner = "client!uh", name = "W", descriptor = "Lclient!lc;")
	private final Texture texture;

	@OriginalMember(owner = "client!uh", name = "U", descriptor = "Z")
	private final boolean blurred;

	@OriginalMember(owner = "client!uh", name = "jb", descriptor = "Z")
	private final boolean mirrored;

	@OriginalMember(owner = "client!uh", name = "Q", descriptor = "Z")
	private final boolean wrapS;

	@OriginalMember(owner = "client!uh", name = "J", descriptor = "Z")
	private final boolean wrapT;

	@OriginalMember(owner = "client!uh", name = "L", descriptor = "I")
	private final int scrollSpeedU;

	@OriginalMember(owner = "client!uh", name = "hb", descriptor = "I")
	private final int scrollSpeedV;

	@OriginalMember(owner = "client!uh", name = "M", descriptor = "I")
	private final int combineRgbMode;

	@OriginalMember(owner = "client!uh", name = "S", descriptor = "I")
	private final int mipmapMode;

	@OriginalMember(owner = "client!uh", name = "<init>", descriptor = "(Lclient!wa;)V")
	public GlTexture(@OriginalArg(0) Buffer buf) {
		this.texture = new Texture(buf);
		this.blurred = buf.g1() == 1; 		// Unsure
		this.mirrored = buf.g1() == 1; 		// Wrap Horizontal?
		this.wrapS = buf.g1() == 1; 		// Wrap Horizontal?
		this.wrapT = buf.g1() == 1; 		// Wrap Vertical?
		@Pc(68) int blendMode = buf.g1() & 0x3;  // Material Brightness
		this.scrollSpeedU = buf.g1b();			// Speed U
		this.scrollSpeedV = buf.g1b();			// Speed V
		@Pc(82) int mipmap = buf.g1();		// Mipmap related? how texture behaves the further out it is from the camera
		buf.g1();
		if (blendMode == 1) {
			this.combineRgbMode = 2;
		} else if (blendMode == 2) {
			this.combineRgbMode = 3;
		} else if (blendMode == 3) {
			this.combineRgbMode = 4;
		} else {
			this.combineRgbMode = 0;
		}
		this.mipmapMode = mipmap >> 4 & 0xF;
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(Lclient!m;IFLclient!ve;Z)[I")
	public final int[] getAnimatedPixels(@OriginalArg(0) TextureProvider provider, @OriginalArg(2) float progress, @OriginalArg(3) Js5 archive, @OriginalArg(4) boolean lowDetail) {
		if (this.animatedPixels == null || this.animationProgress != progress) {
			if (!this.texture.isReady(provider, archive)) {
				return null;
			}
			@Pc(36) int size = lowDetail ? 64 : 128;
			this.animatedPixels = this.texture.generateArgbPixels(size, this.mirrored, size, progress, archive, provider, true);
			this.animationProgress = progress;
			if (this.blurred) {
				@Pc(62) int[] redSums = new int[size];
				@Pc(67) int[] blurredPixels = new int[size * size];
				@Pc(70) int[] greenSums = new int[size];
				@Pc(73) int[] blueSums = new int[size];
				@Pc(80) int leadPos;
				@Pc(81) int trailPos = leadPos = size;
				@Pc(87) int totalPixels = size * size;
				@Pc(91) int lastCol = size - 1;
				@Pc(95) int lastRow = size - 1;
				@Pc(101) int col;
				@Pc(97) int i;
				for (i = 2; i >= 0; i--) {
					for (col = lastRow; col >= 0; col--) {
						leadPos--;
						@Pc(109) int pixel = this.animatedPixels[leadPos];
						redSums[col] += pixel >> 16 & 0xFF;
						greenSums[col] += pixel >> 8 & 0xFF;
						blueSums[col] += pixel & 0xFF;
					}
					if (leadPos == 0) {
						leadPos = totalPixels;
					}
				}
				@Pc(152) int destPos = totalPixels;
				for (@Pc(154) int row = lastCol; row >= 0; row--) {
					@Pc(162) int greenAcc = 0;
					@Pc(164) int blueAcc = 0;
					@Pc(165) int redAcc = 0;
					@Pc(167) int trailCol = 1;
					@Pc(169) int leadCol = 1;
					for (col = 2; col >= 0; col--) {
						leadCol--;
						greenAcc += greenSums[leadCol];
						blueAcc += blueSums[leadCol];
						redAcc += redSums[leadCol];
						if (leadCol == 0) {
							leadCol = size;
						}
					}
					for (col = lastRow; col >= 0; col--) {
						trailCol--;
						@Pc(215) int g = greenAcc / 9;
						@Pc(219) int b = blueAcc / 9;
						leadCol--;
						i = redAcc / 9;
						destPos--;
						blurredPixels[destPos] = b | i << 16 | g << 8;
						redAcc += redSums[leadCol] - redSums[trailCol];
						blueAcc += blueSums[leadCol] - blueSums[trailCol];
						greenAcc += greenSums[leadCol] - greenSums[trailCol];
						if (trailCol == 0) {
							trailCol = size;
						}
						if (leadCol == 0) {
							leadCol = size;
						}
					}
					for (col = lastRow; col >= 0; col--) {
						trailPos--;
						@Pc(300) int removedPixel = this.animatedPixels[trailPos];
						leadPos--;
						@Pc(306) int addedPixel = this.animatedPixels[leadPos];
						redSums[col] += (addedPixel >> 16 & 0xFF) - (removedPixel >> 16 & 0xFF);
						greenSums[col] += (addedPixel >> 8 & 0xFF) - (removedPixel >> 8 & 0xFF);
						blueSums[col] += (addedPixel & 0xFF) - (removedPixel & 0xFF);
					}
					if (trailPos == 0) {
						trailPos = totalPixels;
					}
					if (leadPos == 0) {
						leadPos = totalPixels;
					}
				}
				this.animatedPixels = blurredPixels;
			}
		}
		return this.animatedPixels;
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(Lclient!ve;Lclient!m;IZ)Z")
	public final boolean bind(@OriginalArg(0) Js5 archive, @OriginalArg(1) TextureProvider provider, @OriginalArg(3) boolean lowDetail) {
		if (!this.texture.isReady(provider, archive)) {
			return false;
		}
		@Pc(22) GL2 gl = GlRenderer.gl;
		@Pc(28) int size = lowDetail ? 64 : 128;
		@Pc(31) int flags = MaterialManager.getFlags();
		if ((flags & 0x1) == 0) {
			if (this.textureId == -1) {
				@Pc(53) int[] temp = new int[1];
				gl.glGenTextures(1, temp, 0);
				this.contextId = GlCleaner.contextId;
				this.textureId = temp[0];
				GlRenderer.setTextureId(this.textureId);
				@Pc(82) ByteBuffer pixels = ByteBuffer.wrap(this.texture.generateRgbaBytes(size, size, this.mirrored, provider, 0.7D, archive));
				if (this.mipmapMode == 2) {
					// Old GLU code:
//					 @Pc(201) GLUgl2es1 local201 = new GLUgl2es1();
//					 local201.gluBuild2DMipmaps(3553, 6408, size, size, 6408, 5121, pixels);
//					 gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
//					 gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

					// New code (OpenGL 4+?) w/ texStorage
					int num_mipmaps = 4;
					gl.glTexStorage2D(GL2.GL_TEXTURE_2D, num_mipmaps, GL2.GL_RGBA8, size, size);
					gl.glTexSubImage2D(GL2.GL_TEXTURE_2D, 0, 0, 0, size, size, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);
					gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);

					// New code (OpenGL 3.0+) w/o texStorage
//					gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA8, size, size, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);
//					gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);

					// New code (OpenGL 1.4):
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
//					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_GENERATE_MIPMAP, GL2.GL_TRUE);
//					gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA8, size, size, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);

					GlCleaner.onCardTexture += pixels.limit() * 4 / 3 - this.textureSize;
					this.textureSize = pixels.limit() * 4 / 3;
				} else if (this.mipmapMode == 1) {
					@Pc(129) int level = 0;
					while (true) {
						gl.glTexImage2D(GL2.GL_TEXTURE_2D, level++, GL2.GL_RGBA, size, size, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);
						size >>= 0x1;
						if (size == 0) {
							gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
							gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
							GlCleaner.onCardTexture += pixels.limit() * 4 / 3 - this.textureSize;
							this.textureSize = pixels.limit() * 4 / 3;
							break;
						}
						pixels = ByteBuffer.wrap(this.texture.generateRgbaBytes(size, size, this.mirrored, provider, 0.7D, archive));
					}
				} else {
					gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, size, size, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
					gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
					GlCleaner.onCardTexture += pixels.limit() - this.textureSize;
					this.textureSize = pixels.limit();
				}
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, this.wrapS ? GL2.GL_REPEAT : GL2.GL_CLAMP_TO_EDGE);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, this.wrapT ? GL2.GL_REPEAT : GL2.GL_CLAMP_TO_EDGE);
			} else {
				GlRenderer.setTextureId(this.textureId);
			}
		}
		if ((flags & 0x2) == 0) {
			GlRenderer.setTextureCombineRgbMode(this.combineRgbMode);
		}
		if ((flags & 0x4) == 0) {
			GlRenderer.setTextureCombineAlphaMode(0);
		}
		if ((flags & 0x8) == 0) {
			if (this.scrollSpeedV == 0 && this.scrollSpeedU == 0) {
				GlRenderer.resetTextureMatrix();
			} else {
				@Pc(303) float scrollV = (float) (this.scrollSpeedV * GlRenderer.animationClock) / (float) size;
				@Pc(312) float scrollU = (float) (this.scrollSpeedU * GlRenderer.animationClock) / (float) size;
				GlRenderer.translateTextureMatrix(scrollU, scrollV, 0.0F);
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(ZZLclient!m;Lclient!ve;)[I")
	public final int[] getPixels(@OriginalArg(1) boolean lowDetail, @OriginalArg(2) TextureProvider provider, @OriginalArg(3) Js5 archive) {
		if (this.texture.isReady(provider, archive)) {
			@Pc(24) int size = lowDetail ? 64 : 128;
			return this.texture.generateArgbPixels(size, this.mirrored, size, 1.0D, archive, provider, false);
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(ILclient!m;Lclient!ve;)Z")
	public final boolean isReady(@OriginalArg(1) TextureProvider provider, @OriginalArg(2) Js5 archive) {
		return this.texture.isReady(provider, archive);
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(IB)V")
	public final void scrollAnimation(@OriginalArg(0) int clock) {
		if (this.animatedPixels == null || this.scrollSpeedV == 0 && this.scrollSpeedU == 0) {
			return;
		}
		if (scrollBuffer == null || scrollBuffer.length < this.animatedPixels.length) {
			scrollBuffer = new int[this.animatedPixels.length];
		}
		@Pc(47) int scrollU = clock * this.scrollSpeedU;
		@Pc(58) int size = this.animatedPixels.length == 4096 ? 64 : 128;
		@Pc(62) int totalPixels = this.animatedPixels.length;
		@Pc(66) int sizeMask = size - 1;
		@Pc(73) int scrollVOffset = this.scrollSpeedV * clock * size;
		@Pc(77) int totalMask = totalPixels - 1;
		for (@Pc(79) int rowStart = 0; rowStart < totalPixels; rowStart += size) {
			@Pc(88) int srcRow = scrollVOffset + rowStart & totalMask;
			for (@Pc(90) int col = 0; col < size; col++) {
				@Pc(102) int srcIdx = (sizeMask & col + scrollU) + srcRow;
				@Pc(106) int destIdx = col + rowStart;
				scrollBuffer[destIdx] = this.animatedPixels[srcIdx];
			}
		}
		@Pc(125) int[] temp = this.animatedPixels;
		this.animatedPixels = scrollBuffer;
		scrollBuffer = temp;
	}

	@OriginalMember(owner = "client!uh", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() throws Throwable {
		if (this.textureId != -1) {
			GlCleaner.deleteTexture(this.textureId, this.textureSize, this.contextId);
			this.textureSize = 0;
			this.textureId = -1;
		}
		super.finalize();
	}
}
