package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

@OriginalClass("client!rd")
public final class LiquidMaterialRenderer implements MaterialRenderer {

	@OriginalMember(owner = "client!rd", name = "d", descriptor = "[F")
	public static final float[] ambientLightTemp = new float[4];
	@OriginalMember(owner = "client!rd", name = "a", descriptor = "I")
	private int lastNoiseUpdateClock = -1;

	@OriginalMember(owner = "client!rd", name = "e", descriptor = "I")
	private int displayListId = -1;

	@OriginalMember(owner = "client!rd", name = "c", descriptor = "I")
	private int vertexProgramId;

	@OriginalMember(owner = "client!rd", name = "b", descriptor = "Ljava/nio/FloatBuffer;")
	private FloatBuffer noiseBuffer;

	@OriginalMember(owner = "client!rd", name = "<init>", descriptor = "()V")
	public LiquidMaterialRenderer() {
		if (this.displayListId < 0 && (GlRenderer.arbVertexProgramSupported && GlRenderer.maxTextureUnits >= 2)) {
			@Pc(19) int[] programIds = new int[1];
			@Pc(21) GL2 gl = GlRenderer.gl;
			gl.glGenProgramsARB(1, programIds, 0);
			this.vertexProgramId = programIds[0];
			@Pc(42) int[][] noiseTableX = generateNoiseTable(0.4F);
			@Pc(53) int[][] noiseTableY = generateNoiseTable(0.4F);
			@Pc(58) Buffer buf = new Buffer(262144);
			for (@Pc(60) int row = 0; row < 256; row++) {
				@Pc(67) int[] rowX = noiseTableX[row];
				@Pc(71) int[] rowY = noiseTableY[row];
				for (@Pc(73) int col = 0; col < 64; col++) {
					if (GlRenderer.bigEndian) {
						buf.pFloat((float) rowX[col] / 4096.0F);
						buf.pFloat((float) rowY[col] / 4096.0F);
						buf.pFloat(1.0F);
						buf.pFloat(1.0F);
					} else {
						buf.gFloat((float) rowX[col] / 4096.0F);
						buf.gFloat((float) rowY[col] / 4096.0F);
						buf.gFloat(1.0F);
						buf.gFloat(1.0F);
					}
				}
			}
			@Pc(141) ByteBuffer byteBuf = ByteBuffer.allocateDirect(buf.offset).order(ByteOrder.nativeOrder());
			byteBuf.put(buf.data, 0, buf.offset);
			byteBuf.flip();
			this.noiseBuffer = byteBuf.asFloatBuffer().asReadOnlyBuffer();
			this.initDisplayLists();
			this.uploadVertexProgram();
		}
	}

	@OriginalMember(owner = "client!cj", name = "a", descriptor = "(ZIIIIIIFB)[[I")
	public static int[][] generateNoiseTable(@OriginalArg(7) float persistence) {
		@Pc(15) int[][] table = new int[256][64];
		@Pc(19) TextureOpPerlinNoise perlinNoise = new TextureOpPerlinNoise();
		perlinNoise.persistence = (int) (persistence * 4096.0F);
		perlinNoise.octaveCount = 3;
		perlinNoise.frequencyY = 4;
		perlinNoise.normalizeOutput = false;
		perlinNoise.frequencyX = 8;
		perlinNoise.postDecode();
		Texture.setSize(256, 64);
		for (@Pc(46) int row = 0; row < 256; row++) {
			perlinNoise.getNoiseRow(row, table[row]);
		}
		return table;
	}

	@OriginalMember(owner = "client!rd", name = "a", descriptor = "()V")
	@Override
	public final void unbind() {
		if (this.displayListId >= 0) {
			@Pc(5) GL2 gl = GlRenderer.gl;
			gl.glCallList(this.displayListId + 1);
		}
	}

	@OriginalMember(owner = "client!rd", name = "c", descriptor = "()I")
	@Override
	public final int getFlags() {
		return 0;
	}

	@OriginalMember(owner = "client!rd", name = "b", descriptor = "()V")
	@Override
	public final void bind() {
		if (this.displayListId < 0) {
			return;
		}
		@Pc(5) GL2 gl = GlRenderer.gl;
		gl.glCallList(this.displayListId);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glMatrixMode(GL2.GL_TEXTURE);
		gl.glTranslatef((float) MaterialManager.cameraRenderX, (float) MaterialManager.cameraRenderZ, (float) MaterialManager.cameraRenderY);
		gl.glRotatef(-((float) MaterialManager.cameraYaw * 360.0F) / 2048.0F, 0.0F, 1.0F, 0.0F);
		gl.glRotatef(-((float) MaterialManager.cameraPitch * 360.0F) / 2048.0F, 1.0F, 0.0F, 0.0F);
		gl.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		if (!MaterialManager.allows3DTextureMapping) {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, MaterialManager.noise2DTextures[(int) ((float) (GlRenderer.animationClock * 64) * 0.005F) % 64]);
		}
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		if (this.lastNoiseUpdateClock == GlRenderer.animationClock) {
			return;
		}
		@Pc(85) int bufOffset = (GlRenderer.animationClock & 0xFF) * 256;
		for (@Pc(87) int i = 0; i < 64; i++) {
			this.noiseBuffer.position(bufOffset);
			gl.glProgramLocalParameter4fvARB(GL2.GL_VERTEX_PROGRAM_ARB, i, this.noiseBuffer);
			bufOffset += 4;
		}
		if (MaterialManager.allows3DTextureMapping) {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 65, (float) GlRenderer.animationClock * 0.005F, 0.0F, 0.0F, 1.0F);
		} else {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 65, 0.0F, 0.0F, 0.0F, 1.0F);
		}
		this.lastNoiseUpdateClock = GlRenderer.animationClock;
	}

	@OriginalMember(owner = "client!rd", name = "e", descriptor = "()V")
	private void initDisplayLists() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		this.displayListId = gl.glGenLists(2);
		gl.glNewList(this.displayListId, GL2.GL_COMPILE);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		if (MaterialManager.allows3DTextureMapping) {
			gl.glBindTexture(GL2.GL_TEXTURE_3D, MaterialManager.texture3D);
		}
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_ADD);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_REPLACE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_PREVIOUS);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glBindProgramARB(GL2.GL_VERTEX_PROGRAM_ARB, this.vertexProgramId);
		gl.glEnable(GL2.GL_VERTEX_PROGRAM_ARB);
		gl.glEndList();
		gl.glNewList(this.displayListId + 1, GL2.GL_COMPILE);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glMatrixMode(GL2.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_ALPHA, GL2.GL_TEXTURE);
		gl.glDisable(MaterialManager.allows3DTextureMapping ? GL2.GL_TEXTURE_3D : GL2.GL_TEXTURE_2D);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glBindProgramARB(GL2.GL_VERTEX_PROGRAM_ARB, 0);
		gl.glDisable(GL2.GL_VERTEX_PROGRAM_ARB);
		gl.glDisable(GL2.GL_FRAGMENT_PROGRAM_ARB);
		gl.glEndList();
	}

	@OriginalMember(owner = "client!rd", name = "f", descriptor = "()V")
	private void uploadVertexProgram() {
		if (this.displayListId < 0) {
			return;
		}
		@Pc(4) GL2 gl = GlRenderer.gl;
		@Pc(7) int[] errorPos = new int[1];
		gl.glBindProgramARB(GL2.GL_VERTEX_PROGRAM_ARB, this.vertexProgramId);
		gl.glProgramStringARB(GL2.GL_VERTEX_PROGRAM_ARB, GL2.GL_PROGRAM_FORMAT_ASCII_ARB, "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oTexCoord1   = result.texcoord[1];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   time         = program.local[65];\nPARAM   turbulence   = program.local[64];\nPARAM   lightAmbient = program.local[66]; \nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   ivMatrix[4]  = { state.matrix.texture[1] };\nPARAM   fNoise[64]   = { program.local[0..63] };\nTEMP    noise, clipPos, viewPos, worldPos;\nADDRESS noiseAddr;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nDP4   worldPos.x, ivMatrix[0], viewPos;\nDP4   worldPos.y, ivMatrix[1], viewPos;\nDP4   worldPos.z, ivMatrix[2], viewPos;\nDP4   worldPos.w, ivMatrix[3], viewPos;\nADD   noise.x, worldPos.x, worldPos.z;SUB   noise.y, worldPos.z, worldPos.x;MUL   noise, noise, 0.0001220703125;\nFRC   noise, noise;\nMUL   noise, noise, 64;\nARL   noiseAddr.x, noise.x;\nMOV   noise.x, fNoise[noiseAddr.x].x;\nARL   noiseAddr.x, noise.y;\nMOV   noise.y, fNoise[noiseAddr.x].y;\nMUL   noise, noise, turbulence.x;\nMAD   oTexCoord0, worldPos.xzww, 0.0078125, noise;\nMOV   oTexCoord0.w, 1;\nMUL   oTexCoord1.xy, worldPos.xzww, 0.0009765625;\nMOV   oTexCoord1.zw, time.xxxw;\nDP4   clipPos.x, pMatrix[0], viewPos;\nDP4   clipPos.y, pMatrix[1], viewPos;\nDP4   clipPos.z, pMatrix[2], viewPos;\nDP4   clipPos.w, pMatrix[3], viewPos;\nMUL   oColour.xyz, iColour, lightAmbient;\nMOV   oColour.w, 1;\nMOV   oFogCoord.x, clipPos.z;\nMOV   oPos, clipPos; \nEND".length(), "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oTexCoord1   = result.texcoord[1];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   time         = program.local[65];\nPARAM   turbulence   = program.local[64];\nPARAM   lightAmbient = program.local[66]; \nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   ivMatrix[4]  = { state.matrix.texture[1] };\nPARAM   fNoise[64]   = { program.local[0..63] };\nTEMP    noise, clipPos, viewPos, worldPos;\nADDRESS noiseAddr;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nDP4   worldPos.x, ivMatrix[0], viewPos;\nDP4   worldPos.y, ivMatrix[1], viewPos;\nDP4   worldPos.z, ivMatrix[2], viewPos;\nDP4   worldPos.w, ivMatrix[3], viewPos;\nADD   noise.x, worldPos.x, worldPos.z;SUB   noise.y, worldPos.z, worldPos.x;MUL   noise, noise, 0.0001220703125;\nFRC   noise, noise;\nMUL   noise, noise, 64;\nARL   noiseAddr.x, noise.x;\nMOV   noise.x, fNoise[noiseAddr.x].x;\nARL   noiseAddr.x, noise.y;\nMOV   noise.y, fNoise[noiseAddr.x].y;\nMUL   noise, noise, turbulence.x;\nMAD   oTexCoord0, worldPos.xzww, 0.0078125, noise;\nMOV   oTexCoord0.w, 1;\nMUL   oTexCoord1.xy, worldPos.xzww, 0.0009765625;\nMOV   oTexCoord1.zw, time.xxxw;\nDP4   clipPos.x, pMatrix[0], viewPos;\nDP4   clipPos.y, pMatrix[1], viewPos;\nDP4   clipPos.z, pMatrix[2], viewPos;\nDP4   clipPos.w, pMatrix[3], viewPos;\nMUL   oColour.xyz, iColour, lightAmbient;\nMOV   oColour.w, 1;\nMOV   oFogCoord.x, clipPos.z;\nMOV   oPos, clipPos; \nEND");
		gl.glGetIntegerv(GL2.GL_PROGRAM_ERROR_POSITION_ARB, errorPos, 0);
		if (errorPos[0] != -1) {
			return;
		}
	}

	@OriginalMember(owner = "client!rd", name = "a", descriptor = "(I)V")
	@Override
	public final void setArgument(@OriginalArg(0) int flags) {
		if (this.displayListId < 0) {
			return;
		}
		@Pc(5) GL2 gl = GlRenderer.gl;
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		if ((flags & 0x80) == 0) {
			gl.glEnable(MaterialManager.allows3DTextureMapping ? GL2.GL_TEXTURE_3D : GL2.GL_TEXTURE_2D);
		} else {
			gl.glDisable(MaterialManager.allows3DTextureMapping ? GL2.GL_TEXTURE_3D : GL2.GL_TEXTURE_2D);
		}
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		if ((flags & 0x40) == 0) {
			gl.glGetFloatv(GL2.GL_LIGHT_MODEL_AMBIENT, ambientLightTemp, 0);
			gl.glProgramLocalParameter4fvARB(GL2.GL_VERTEX_PROGRAM_ARB, 66, ambientLightTemp, 0);
		} else {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 66, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		@Pc(58) int turbulence = flags & 0x3;
		if (turbulence == 2) {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 64, 0.05F, 1.0F, 1.0F, 1.0F);
		} else if (turbulence == 3) {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 64, 0.1F, 1.0F, 1.0F, 1.0F);
		} else {
			gl.glProgramLocalParameter4fARB(GL2.GL_VERTEX_PROGRAM_ARB, 64, 0.025F, 1.0F, 1.0F, 1.0F);
		}
	}
}
