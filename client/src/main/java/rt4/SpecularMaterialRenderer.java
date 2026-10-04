package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;

@OriginalClass("client!vm")
public final class SpecularMaterialRenderer implements MaterialRenderer {

	@OriginalMember(owner = "client!vm", name = "a", descriptor = "I")
	private int displayListBase = -1;

	@OriginalMember(owner = "client!vm", name = "c", descriptor = "[I")
	private int[] cubeMapTextureIds = null;

	@OriginalMember(owner = "client!vm", name = "b", descriptor = "Z")
	private boolean limitedTextureUnits = false;

	@OriginalMember(owner = "client!vm", name = "<init>", descriptor = "()V")
	public SpecularMaterialRenderer() {
		if (GlRenderer.arbTextureCubeMapSupported && GlRenderer.maxTextureUnits >= 2) {
			this.createCubeMapTextures();
			@Pc(19) GL2 gl = GlRenderer.gl;
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[0]);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[1]);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[2]);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
			this.limitedTextureUnits = GlRenderer.maxTextureUnits < 3;
		}
		this.initDisplayLists();
	}

	@OriginalMember(owner = "client!vm", name = "d", descriptor = "()V")
	private void initDisplayLists() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		this.displayListBase = gl.glGenLists(2);
		gl.glNewList(this.displayListBase, GL2.GL_COMPILE);
		if (this.cubeMapTextureIds == null) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_PRIMARY_COLOR);
		} else {
			gl.glActiveTexture(GL2.GL_TEXTURE1);
			gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_NORMAL_MAP);
			gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_NORMAL_MAP);
			gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_NORMAL_MAP);
			gl.glEnable(GL2.GL_TEXTURE_GEN_S);
			gl.glEnable(GL2.GL_TEXTURE_GEN_T);
			gl.glEnable(GL2.GL_TEXTURE_GEN_R);
			gl.glEnable(GL2.GL_TEXTURE_CUBE_MAP);
			gl.glMatrixMode(GL2.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glRotatef(22.5F, 1.0F, 0.0F, 0.0F);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			if (this.limitedTextureUnits) {
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_ADD);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND0_RGB, GL2.GL_SRC_ALPHA);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_REPLACE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_PRIMARY_COLOR);
			} else {
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_REPLACE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_PREVIOUS);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
				gl.glActiveTexture(GL2.GL_TEXTURE2);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_COMBINE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_ADD);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_PREVIOUS);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC1_RGB, GL2.GL_PREVIOUS);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND1_RGB, GL2.GL_SRC_ALPHA);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_REPLACE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_PRIMARY_COLOR);
				gl.glBindTexture(GL2.GL_TEXTURE_2D, GlRenderer.defaultTextureId);
				gl.glEnable(GL2.GL_TEXTURE_2D);
			}
			gl.glActiveTexture(GL2.GL_TEXTURE0);
		}
		gl.glEndList();
		gl.glNewList(this.displayListBase + 1, GL2.GL_COMPILE);
		if (this.cubeMapTextureIds == null) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_TEXTURE);
		} else {
			gl.glActiveTexture(GL2.GL_TEXTURE1);
			gl.glDisable(GL2.GL_TEXTURE_GEN_S);
			gl.glDisable(GL2.GL_TEXTURE_GEN_T);
			gl.glDisable(GL2.GL_TEXTURE_GEN_R);
			gl.glDisable(GL2.GL_TEXTURE_CUBE_MAP);
			gl.glMatrixMode(GL2.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			if (this.limitedTextureUnits) {
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND0_RGB, GL2.GL_SRC_COLOR);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_TEXTURE);
			} else {
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
				gl.glActiveTexture(GL2.GL_TEXTURE2);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND1_RGB, GL2.GL_SRC_COLOR);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
				gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_TEXTURE);
				gl.glDisable(GL2.GL_TEXTURE_2D);
			}
			gl.glActiveTexture(GL2.GL_TEXTURE0);
		}
		gl.glEndList();
	}

	@OriginalMember(owner = "client!vm", name = "a", descriptor = "()V")
	@Override
	public final void unbind() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		if (Preferences.highDetailLighting) {
			gl.glCallList(this.displayListBase + 1);
		} else {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_TEXTURE);
		}
	}

	@OriginalMember(owner = "client!vm", name = "c", descriptor = "()I")
	@Override
	public final int getFlags() {
		return 4;
	}

	@OriginalMember(owner = "client!vm", name = "b", descriptor = "()V")
	@Override
	public final void bind() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		GlRenderer.setTextureCombineAlphaMode(1);
		if (Preferences.highDetailLighting) {
			gl.glCallList(this.displayListBase);
		} else {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_PRIMARY_COLOR);
		}
	}

	@OriginalMember(owner = "client!vm", name = "a", descriptor = "(I)V")
	@Override
	public final void setArgument(@OriginalArg(0) int level) {
		@Pc(1) GL2 gl = GlRenderer.gl;
		if (Preferences.highDetailLighting && this.cubeMapTextureIds != null) {
			gl.glActiveTexture(GL2.GL_TEXTURE1);
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[level - 1]);
			gl.glActiveTexture(GL2.GL_TEXTURE0);
		}
	}

	@OriginalMember(owner = "client!vm", name = "e", descriptor = "()V")
	private void createCubeMapTextures() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		if (this.cubeMapTextureIds == null) {
			this.cubeMapTextureIds = new int[3];
			gl.glGenTextures(3, this.cubeMapTextureIds, 0);
		}
		@Pc(19) byte[] lowSpecular = new byte[4096];
		@Pc(22) byte[] highSpecular = new byte[4096];
		@Pc(25) byte[] medSpecular = new byte[4096];
		for (@Pc(27) int face = 0; face < 6; face++) {
			@Pc(32) int pixelIdx = 0;
			for (@Pc(34) int row = 0; row < 64; row++) {
				for (@Pc(39) int col = 0; col < 64; col++) {
					@Pc(51) float u = (float) col * 2.0F / 64.0F - 1.0F;
					@Pc(60) float v = (float) row * 2.0F / 64.0F - 1.0F;
					@Pc(75) float invLen = (float) (1.0D / Math.sqrt(u * u + v * v + 1.0F));
					@Pc(79) float normalU = u * invLen;
					@Pc(83) float normalV = v * invLen;
					@Pc(88) float specular;
					if (face == 0) {
						specular = -normalU;
					} else if (face == 1) {
						specular = normalU;
					} else if (face == 2) {
						specular = normalV;
					} else if (face == 3) {
						specular = -normalV;
					} else if (face == 4) {
						specular = invLen;
					} else {
						specular = -invLen;
					}
					@Pc(129) int highVal;
					@Pc(137) int medVal;
					@Pc(145) int lowVal;
					if (specular > 0.0F) {
						highVal = (int) (Math.pow(specular, 96.0D) * 255.0D);
						medVal = (int) (Math.pow(specular, 36.0D) * 255.0D);
						lowVal = (int) (Math.pow(specular, 12.0D) * 255.0D);
					} else {
						lowVal = 0;
						medVal = 0;
						highVal = 0;
					}
					if (GlRenderer.maxTextureUnits < 3) {
						highVal /= 5;
						medVal /= 5;
						lowVal /= 5;
					} else {
						highVal /= 2;
						medVal /= 2;
						lowVal /= 2;
					}
					highSpecular[pixelIdx] = (byte) highVal;
					medSpecular[pixelIdx] = (byte) medVal;
					lowSpecular[pixelIdx] = (byte) lowVal;
					pixelIdx++;
				}
			}
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[0]);
			gl.glTexImage2D(face + GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL2.GL_ALPHA, 64, 64, 0, GL2.GL_ALPHA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(highSpecular));
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[1]);
			gl.glTexImage2D(face + GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL2.GL_ALPHA, 64, 64, 0, GL2.GL_ALPHA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(medSpecular));
			gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, this.cubeMapTextureIds[2]);
			gl.glTexImage2D(face + GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, GL2.GL_ALPHA, 64, 64, 0, GL2.GL_ALPHA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(lowSpecular));
			GlCleaner.onCardTexture += 12288;
		}
	}
}
