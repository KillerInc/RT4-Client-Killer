package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ob")
public final class WaterfallMaterialRenderer implements MaterialRenderer {

	@OriginalMember(owner = "client!ob", name = "g", descriptor = "I")
	private int displayListId;

	@OriginalMember(owner = "client!ob", name = "n", descriptor = "[F")
	private final float[] texGenParams = new float[4];

	@OriginalMember(owner = "client!ob", name = "<init>", descriptor = "()V")
	public WaterfallMaterialRenderer() {
		this.initDisplayLists();
	}

	@OriginalMember(owner = "client!ob", name = "c", descriptor = "()I")
	@Override
	public final int getFlags() {
		return 0;
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(I)V")
	@Override
	public final void setArgument(@OriginalArg(0) int flags) {
		@Pc(7) GL2 gl = GlRenderer.gl;
		@Pc(18) float animSpeed = (float) ((flags >> 3 & 0x3) + 1) * 0.01F;
		@Pc(27) float scrollSpeed = -0.01F * (float) ((flags & 0x3) + 1);
		@Pc(36) float texScale = (flags & 0x40) == 0 ? 4.8828125E-4F : 9.765625E-4F;
		@Pc(47) boolean horizontal = (flags & 0x80) != 0;
		if (horizontal) {
			this.texGenParams[0] = texScale;
			this.texGenParams[1] = 0.0F;
			this.texGenParams[2] = 0.0F;
			this.texGenParams[3] = 0.0F;
		} else {
			this.texGenParams[2] = texScale;
			this.texGenParams[1] = 0.0F;
			this.texGenParams[3] = 0.0F;
			this.texGenParams[0] = 0.0F;
		}
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		gl.glRotatef((float) MaterialManager.cameraPitch * 360.0F / 2048.0F, 1.0F, 0.0F, 0.0F);
		gl.glRotatef((float) MaterialManager.cameraYaw * 360.0F / 2048.0F, 0.0F, 1.0F, 0.0F);
		gl.glTranslatef((float) -MaterialManager.cameraRenderX, (float) -MaterialManager.cameraRenderZ, (float) -MaterialManager.cameraRenderY);
		gl.glTexGenfv(GL2.GL_S, GL2.GL_EYE_PLANE, this.texGenParams, 0);
		this.texGenParams[3] = scrollSpeed * (float) GlRenderer.animationClock;
		this.texGenParams[0] = 0.0F;
		this.texGenParams[2] = 0.0F;
		this.texGenParams[1] = texScale;
		gl.glTexGenfv(GL2.GL_T, GL2.GL_EYE_PLANE, this.texGenParams, 0);
		gl.glPopMatrix();
		if (MaterialManager.allows3DTextureMapping) {
			this.texGenParams[3] = (float) GlRenderer.animationClock * animSpeed;
			this.texGenParams[1] = 0.0F;
			this.texGenParams[0] = 0.0F;
			this.texGenParams[2] = 0.0F;
			gl.glTexGenfv(GL2.GL_R, GL2.GL_OBJECT_PLANE, this.texGenParams, 0);
		} else {
			@Pc(189) int frameIndex = (int) ((float) GlRenderer.animationClock * animSpeed * 64.0F);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, MaterialManager.waterfallTextures[frameIndex % 64]);
		}
		gl.glActiveTexture(GL2.GL_TEXTURE0);
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "()V")
	@Override
	public final void unbind() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		gl.glCallList(this.displayListId + 1);
	}

	@OriginalMember(owner = "client!ob", name = "b", descriptor = "()V")
	@Override
	public final void bind() {
		@Pc(5) GL2 gl = GlRenderer.gl;
		gl.glCallList(this.displayListId);
	}

	@OriginalMember(owner = "client!ob", name = "b", descriptor = "(I)V")
	private void initDisplayLists() {
		@Pc(3) GL2 gl = GlRenderer.gl;
		this.displayListId = gl.glGenLists(2);
		gl.glNewList(this.displayListId, GL2.GL_COMPILE);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		if (MaterialManager.allows3DTextureMapping) {
			gl.glBindTexture(GL2.GL_TEXTURE_3D, MaterialManager.waterfallTextureId);
			gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_OBJECT_LINEAR);
			gl.glEnable(GL2.GL_TEXTURE_GEN_R);
			gl.glEnable(GL2.GL_TEXTURE_3D);
		} else {
			gl.glEnable(GL2.GL_TEXTURE_2D);
		}
		gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glEnable(GL2.GL_TEXTURE_GEN_S);
		gl.glEnable(GL2.GL_TEXTURE_GEN_T);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glEndList();
		gl.glNewList(this.displayListId + 1, GL2.GL_COMPILE);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		if (MaterialManager.allows3DTextureMapping) {
			gl.glDisable(GL2.GL_TEXTURE_3D);
			gl.glDisable(GL2.GL_TEXTURE_GEN_R);
		} else {
			gl.glDisable(GL2.GL_TEXTURE_2D);
		}
		gl.glDisable(GL2.GL_TEXTURE_GEN_S);
		gl.glDisable(GL2.GL_TEXTURE_GEN_T);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glEndList();
	}
}
