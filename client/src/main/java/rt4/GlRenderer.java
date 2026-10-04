package rt4;

import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.awt.JAWTWindow;
import com.jogamp.opengl.*;
import jogamp.newt.awt.NewtFactoryAWT;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

public final class GlRenderer {

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "Ljava/lang/String;")
	private static String vendor;

	@OriginalMember(owner = "client!tf", name = "b", descriptor = "Ljava/lang/String;")
	private static String renderer;
	public static float vFOV = 0;
	public static float hFOV = 0;



	private static ByteBuffer pixelByteBuffer;
	private static IntBuffer pixelIntBuffer;
	public static int[] pixelData;

	@OriginalMember(owner = "client!tf", name = "c", descriptor = "F")
	private static float projectionZScale;

	@OriginalMember(owner = "client!tf", name = "e", descriptor = "I")
	public static int maxTextureUnits;

	@OriginalMember(owner = "client!tf", name = "f", descriptor = "Z")
	public static boolean bigEndian;

	@OriginalMember(owner = "client!tf", name = "k", descriptor = "F")
	private static float projectionZOffset;

	@OriginalMember(owner = "client!tf", name = "p", descriptor = "Lgl!javax/media/opengl/GLContext;")
	private static GLContext context;

	@OriginalMember(owner = "client!tf", name = "r", descriptor = "Z")
	public static boolean extTexture3dSupported;

	@OriginalMember(owner = "client!tf", name = "t", descriptor = "Lgl!javax/media/opengl/GL;")
	public static GL2 gl;

	@OriginalMember(owner = "client!tf", name = "v", descriptor = "I")
	private static int maxTextureImageUnits;

	@OriginalMember(owner = "client!tf", name = "y", descriptor = "Z")
	public static boolean arbMultisampleSupported;

	@OriginalMember(owner = "client!tf", name = "z", descriptor = "I")
	public static int defaultTextureId;

	@OriginalMember(owner = "client!tf", name = "A", descriptor = "I")
	public static int canvasHeight;

	@OriginalMember(owner = "client!tf", name = "B", descriptor = "I")
	private static int version;

	@OriginalMember(owner = "client!tf", name = "C", descriptor = "Z")
	public static boolean arbVboSupported;

	@OriginalMember(owner = "client!tf", name = "D", descriptor = "I")
	private static int maxTextureCoords;

	@OriginalMember(owner = "client!tf", name = "E", descriptor = "Lgl!javax/media/opengl/GLDrawable;")
	private static GLDrawable drawable;

	@OriginalMember(owner = "client!tf", name = "H", descriptor = "Z")
	public static boolean arbVertexProgramSupported;

	@OriginalMember(owner = "client!tf", name = "J", descriptor = "I")
	public static int canvasWidth;

	@OriginalMember(owner = "client!tf", name = "K", descriptor = "Z")
	public static boolean arbTextureCubeMapSupported;

	@OriginalMember(owner = "client!tf", name = "d", descriptor = "Z")
	private static boolean textureMatrixModified = false;

	@OriginalMember(owner = "client!tf", name = "g", descriptor = "I")
	public static int animationClock = 0;

	@OriginalMember(owner = "client!tf", name = "h", descriptor = "I")
	private static int textureCombineAlphaMode = 0;

	@OriginalMember(owner = "client!tf", name = "i", descriptor = "I")
	private static int textureCombineRgbMode = 0;

	@OriginalMember(owner = "client!tf", name = "j", descriptor = "F")
	private static float depthBias = 0.0F;

	@OriginalMember(owner = "client!tf", name = "l", descriptor = "Z")
	private static boolean lightingEnabled = true;

	@OriginalMember(owner = "client!tf", name = "m", descriptor = "F")
	private static float projectionDistance = 0.0F;

	@OriginalMember(owner = "client!tf", name = "n", descriptor = "Z")
	public static boolean normalArrayEnabled = true;

	@OriginalMember(owner = "client!tf", name = "o", descriptor = "Z")
	private static boolean orthographicActive = false;

	@OriginalMember(owner = "client!tf", name = "q", descriptor = "F")
	private static final float NEAR_PLANE_SCALE = 0.09765625F;

	@OriginalMember(owner = "client!tf", name = "s", descriptor = "I")
	private static int textureId = -1;

	@OriginalMember(owner = "client!tf", name = "u", descriptor = "Z")
	private static boolean depthTestEnabled = true;

	@OriginalMember(owner = "client!tf", name = "w", descriptor = "Z")
	public static boolean enabled = false;

	@OriginalMember(owner = "client!tf", name = "x", descriptor = "[F")
	private static final float[] matrix = new float[16];

	@OriginalMember(owner = "client!tf", name = "F", descriptor = "Z")
	private static boolean fogEnabled = true;

	@OriginalMember(owner = "client!tf", name = "I", descriptor = "Lclient!na;")
	private static final JagString RADEON = JagString.parse("radeon");

	private static JAWTWindow window;

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(Ljava/lang/String;)Lclient!na;")
	private static JagString toJagString(@OriginalArg(0) String str) {
		@Pc(3) byte[] bytes;
		bytes = str.getBytes(StandardCharsets.ISO_8859_1);
		return JagString.decodeString(bytes, bytes.length, 0);
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(IIII)V")
	public static void setFullscreenCamera(@OriginalArg(0) int nearClip, @OriginalArg(1) int farClip, @OriginalArg(2) int viewportWidth, @OriginalArg(3) int viewportHeight) {
		setupPerspectiveView(0, 0, canvasWidth, canvasHeight, nearClip, farClip, 0.0F, 0.0F, viewportWidth, viewportHeight);
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "()V")
	public static void begin2DReplace() {
		MaterialManager.setMaterial(0, 0);
		setupOrthographic();
		setTextureCombineRgbMode(1);
		setTextureCombineAlphaMode(1);
		setLightingEnabled(false);
		setDepthTestEnabled(false);
		setFogEnabled(false);
		resetTextureMatrix();
	}

	@OriginalMember(owner = "client!tf", name = "b", descriptor = "()V")
	public static void resetTextureMatrix() {
		if (textureMatrixModified) {
			gl.glMatrixMode(GL2.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			textureMatrixModified = false;
		}
	}

	@OriginalMember(owner = "client!tf", name = "c", descriptor = "()V")
	public static void begin2DModulate() {
		MaterialManager.setMaterial(0, 0); // MaterialManager
		setupOrthographic();
		setTextureCombineRgbMode(0);
		setTextureCombineAlphaMode(0);
		setLightingEnabled(false);
		setDepthTestEnabled(false);
		setFogEnabled(false);
		resetTextureMatrix();
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(FF)V")
	public static void setDepthBias(@OriginalArg(0) float distance, @OriginalArg(1) float bias) {
		if (orthographicActive || distance == projectionDistance && bias == depthBias) {
			return;
		}
		projectionDistance = distance;
		depthBias = bias;
		if (bias == 0.0F) {
			matrix[10] = projectionZScale;
			matrix[14] = projectionZOffset;
		} else {
			@Pc(25) float scale = distance / (bias + distance);
			@Pc(29) float scaleSq = scale * scale;
			@Pc(42) float zAdjust = -projectionZOffset * (1.0F - scale) * (1.0F - scale) / bias;
			matrix[10] = projectionZScale + zAdjust;
			matrix[14] = projectionZOffset * scaleSq;
		}
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadMatrixf(matrix, 0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	@OriginalMember(owner = "client!tf", name = "d", descriptor = "()V")
	public static void swapBuffers() {
		try {
			drawable.swapBuffers();
			readPixels();
		} catch (@Pc(3) Exception ex) {
		}
	}

	public static void initializePixelBuffer(int width, int height) {
		// Allocate ByteBuffer for BGRA pixels (4 bytes per pixel)
		pixelByteBuffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder());
		pixelIntBuffer = pixelByteBuffer.asIntBuffer();
		pixelData = new int[width * height];
	}

	public static void readPixels() {
		// Ensure the pixel buffer is initialized with the correct size
		if (pixelByteBuffer == null || pixelIntBuffer.capacity() != canvasWidth * canvasHeight) {
			initializePixelBuffer(canvasWidth, canvasHeight);
		}

		// Read pixels into the direct ByteBuffer
		gl.glReadPixels(0, 0, canvasWidth, canvasHeight, GL2.GL_BGRA,
				GlRenderer.bigEndian ? GL2.GL_UNSIGNED_INT_8_8_8_8_REV : GL2.GL_UNSIGNED_BYTE,
				pixelByteBuffer);

		// Convert to int array if needed
		pixelIntBuffer.rewind(); // Prepare the IntBuffer for reading
		pixelIntBuffer.get(pixelData, 0, pixelData.length); // Transfer to pixelData array if necessary
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(Z)V")
	public static void setFogEnabled(@OriginalArg(0) boolean enabled) {
		if (enabled == fogEnabled) {
			return;
		}
		if (enabled) {
			gl.glEnable(GL2.GL_FOG);
		} else {
			gl.glDisable(GL2.GL_FOG);
		}
		fogEnabled = enabled;
	}

	@OriginalMember(owner = "client!tf", name = "e", descriptor = "()V")
	public static void begin2DModulateAlt() {
		MaterialManager.setMaterial(0, 0);
		setupOrthographic();
		setTextureCombineRgbMode(0);
		setTextureCombineAlphaMode(0);
		setLightingEnabled(false);
		setDepthTestEnabled(false);
		setFogEnabled(false);
		resetTextureMatrix();
	}

	@OriginalMember(owner = "client!tf", name = "f", descriptor = "()V")
	private static void initGlState() {
		orthographicActive = false;
		gl.glDisable(GL2.GL_TEXTURE_2D);
		textureId = -1;
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_COMBINE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		textureCombineRgbMode = 0;
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
		textureCombineAlphaMode = 0;
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_FOG);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		lightingEnabled = true;
		depthTestEnabled = true;
		fogEnabled = true;
		resetMaterial();
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_COMBINE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.setSwapInterval(0);
		gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearDepth(1.0D);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		enableDepthMask();
		gl.glMatrixMode(GL2.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_BLEND);										// Enable the OpenGL Blending functionality
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);	// Set the blend mode to blend our current RGBA with what is already in the buffer
		gl.glEnable(GL2.GL_ALPHA_TEST);
		gl.glAlphaFunc(GL2.GL_GREATER, 0.0F);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		normalArrayEnabled = true;
		gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		FogManager.setup();
		LightingManager.initGlLightParameters();
	}

	@OriginalMember(owner = "client!tf", name = "g", descriptor = "()V")
	public static void enableDepthMask() {
		gl.glDepthMask(true);
	}

	@OriginalMember(owner = "client!tf", name = "b", descriptor = "(Z)V")
	public static void setDepthTestEnabled(@OriginalArg(0) boolean enabled) {
		if (enabled == depthTestEnabled) {
			return;
		}
		if (enabled) {
			gl.glEnable(GL2.GL_DEPTH_TEST);
		} else {
			gl.glDisable(GL2.GL_DEPTH_TEST);
		}
		depthTestEnabled = enabled;
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(F)V")
	public static void setDepthLayer(@OriginalArg(0) float layer) {
		setDepthBias(3000.0F, layer * 1.5F);
	}

	@OriginalMember(owner = "client!tf", name = "h", descriptor = "()V")
	public static void draw() {
		@Pc(2) int[] buffers = new int[2];
		gl.glGetIntegerv(GL2.GL_DRAW_BUFFER, buffers, 0);
		gl.glGetIntegerv(GL2.GL_READ_BUFFER, buffers, 1);
		gl.glDrawBuffer(GL2.GL_BACK_LEFT);
		gl.glReadBuffer(GL2.GL_FRONT_LEFT);
		setTextureId(-1);
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glDisable(GL2.GL_FOG);
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		gl.glDisable(GL2.GL_ALPHA_TEST);
		gl.glRasterPos2i(0, 0);
		gl.glCopyPixels(0, 0, canvasWidth, canvasHeight, GL2.GL_COLOR);
		gl.glPopAttrib();
		gl.glDrawBuffer(buffers[0]);
		gl.glReadBuffer(buffers[1]);
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(Ljava/awt/Canvas;)V")
	public static void createAndDestroyContext(@OriginalArg(0) Canvas canvas) {
		try {
			if (!canvas.isDisplayable()) {
				return;
			}
			GLProfile profile = GLProfile.getDefault();

			GLCapabilities glCaps = new GLCapabilities(profile);
			AWTGraphicsConfiguration config = AWTGraphicsConfiguration.create(canvas.getGraphicsConfiguration(), glCaps, glCaps);
			JAWTWindow jawtWindow = NewtFactoryAWT.getNativeWindow(canvas, config);
			@Pc(5) GLDrawableFactory glDrawableFactory = GLDrawableFactory.getFactory(profile);
			@Pc(11) GLDrawable glDrawable = glDrawableFactory.createGLDrawable(jawtWindow);

			glDrawable.setRealized(true);
			@Pc(18) GLContext glContext = glDrawable.createContext(null);
			glContext.makeCurrent();
			glContext.release();
			glContext.destroy();
			glDrawable.setRealized(false);
		} catch (@Pc(30) Throwable ex) {
		}
	}

	@OriginalMember(owner = "client!tf", name = "i", descriptor = "()V")
	public static void begin2DNoTexture() {
		MaterialManager.setMaterial(0, 0);
		setupOrthographic();
		setTextureId(-1);
		setLightingEnabled(false);
		setDepthTestEnabled(false);
		setFogEnabled(false);
		resetTextureMatrix();
	}

	@OriginalMember(owner = "client!tf", name = "j", descriptor = "()V")
	private static void setupOrthographic() {
		if (orthographicActive) {
			return;
		}
		gl.glMatrixMode(GL2.GL_PROJECTION);		// Switch to the projection matrix so that we can manipulate how our scene is viewed
		gl.glLoadIdentity();					// Reset the projection matrix to the identity matrix so that we don't get any artifacts (cleaning up)
		gl.glOrtho(0, canvasWidth, 0, canvasHeight, -1.0D, 1.0D);
		setViewportBounds(0, 0, canvasWidth, canvasHeight);
		gl.glMatrixMode(GL2.GL_MODELVIEW);		// Switch back to the model view matrix, so that we can start drawing shapes correctly
		gl.glLoadIdentity();					// Reset the projection matrix to the identity matrix so that we don't get any artifacts (cleaning up)
		orthographicActive = true;
	}

	@OriginalMember(owner = "client!tf", name = "c", descriptor = "(Z)V")
	public static void setLightingEnabled(@OriginalArg(0) boolean enabled) {
		if (enabled == lightingEnabled) {
			return;
		}
		if (enabled) {
			gl.glEnable(GL2.GL_LIGHTING);
		} else {
			gl.glDisable(GL2.GL_LIGHTING);
		}
		lightingEnabled = enabled;
	}

	@OriginalMember(owner = "client!tf", name = "l", descriptor = "()F")
	public static float getDepthBias() {
		return depthBias;
	}

	@OriginalMember(owner = "client!tf", name = "m", descriptor = "()I")
	private static int checkContext() {
		@Pc(1) int result = 0;
		vendor = gl.glGetString(GL2.GL_VENDOR);
		renderer = gl.glGetString(GL2.GL_RENDERER);
		@Pc(12) String vendor = GlRenderer.vendor.toLowerCase();
		if (vendor.contains("microsoft")) {
			result = 1;
		}
		if (vendor.contains("brian paul") || vendor.contains("mesa")) {
			result |= 0x1;
		}
		@Pc(39) String version = gl.glGetString(GL2.GL_VERSION);
		@Pc(43) String[] versionParts = version.split("[. ]");
		if (versionParts.length >= 2) {
			try {
				@Pc(52) int major = Integer.parseInt(versionParts[0]);
				@Pc(57) int minor = Integer.parseInt(versionParts[1]);
				GlRenderer.version = major * 10 + minor;
			} catch (@Pc(65) NumberFormatException ex) {
				result |= 0x4;
			}
		} else {
			result |= 0x4;
		}
		if (GlRenderer.version < 12) {
			result |= 0x2;
		}
		if (!gl.isExtensionAvailable("GL_ARB_multitexture")) {
			result |= 0x8;
		}
		if (!gl.isExtensionAvailable("GL_ARB_texture_env_combine")) {
			result |= 0x20;
		}
		@Pc(100) int[] data = new int[1];
		gl.glGetIntegerv(GL2.GL_MAX_TEXTURE_UNITS, data, 0);
		maxTextureUnits = data[0];
		gl.glGetIntegerv(GL2.GL_MAX_TEXTURE_COORDS, data, 0);
		maxTextureCoords = data[0];
		gl.glGetIntegerv(GL2.GL_MAX_TEXTURE_IMAGE_UNITS, data, 0);
		maxTextureImageUnits = data[0];
		if (maxTextureUnits < 2 || maxTextureCoords < 2 || maxTextureImageUnits < 2) {
			result |= 0x10;
		}
		if (result != 0) {
			return result;
		}
		bigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
		arbVboSupported = gl.isExtensionAvailable("GL_ARB_vertex_buffer_object");
		arbMultisampleSupported = gl.isExtensionAvailable("GL_ARB_multisample");
		arbTextureCubeMapSupported = gl.isExtensionAvailable("GL_ARB_texture_cube_map");
		arbVertexProgramSupported = gl.isExtensionAvailable("GL_ARB_vertex_program");
		extTexture3dSupported = gl.isExtensionAvailable("GL_EXT_texture3D");
		@Pc(176) JagString renderer = toJagString(GlRenderer.renderer).toLowerCase();
		if (renderer.indexOf(RADEON) != -1) {
			@Pc(184) int v = 0;
			@Pc(193) JagString[] rendererParts = renderer.replaceSlashWithSpace().split(32);
			for (@Pc(195) int i = 0; i < rendererParts.length; i++) {
				@Pc(203) JagString part = rendererParts[i];
				if (part.length() >= 4 && part.substring(4, 0).isInt()) {
					v = part.substring(4, 0).parseInt();
					break;
				}
			}
			if (v >= 7000 && v <= 7999) {
				arbVboSupported = false;
			}
			if (v >= 7000 && v <= 9250) {
				extTexture3dSupported = false;
			}
			GlModel.arbVboSupported = arbVboSupported;
		}
		if (arbVboSupported) {
			try {
				@Pc(250) int[] temp = new int[1];
				gl.glGenBuffers(1, temp, 0);
			} catch (@Pc(257) Throwable ex) {
				return -4;
			}
		}
		return 0;
	}

	@OriginalMember(owner = "client!tf", name = "n", descriptor = "()V")
	public static void clearDepthBuffer() {
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
	}

	@OriginalMember(owner = "client!tf", name = "o", descriptor = "()V")
	public static void quit() {
		if (gl != null) {
			try {
				MaterialManager.quit(); // MaterialManager
			} catch (@Pc(5) Throwable ex) {
			}
		}

		if (window != null) {
			if (!window.getLock().isLocked()) {
				window.lockSurface();
			}

			if (context != null) {
				GlCleaner.clear(); // GlCleaner
				try {
					if (GLContext.getCurrent() == context) {
						context.release();
					}
				} catch (@Pc(17) Throwable ex) {
				}
				try {
					context.destroy();
				} catch (@Pc(21) Throwable ex) {
				}
			}
		}

		if (drawable != null) {
			try {
				drawable.setRealized(false);
			} catch (@Pc(30) Throwable ex) {
			}
		}
		window = null;
		gl = null;
		context = null;
		drawable = null;
		LightingManager.destroy(); // LightingManager
		enabled = false;
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(FFF)V")
	public static void translateTextureMatrix(@OriginalArg(0) float x, @OriginalArg(1) float y, @OriginalArg(2) float z) {
		gl.glMatrixMode(GL2.GL_TEXTURE);
		if (textureMatrixModified) {
			gl.glLoadIdentity();
		}
		gl.glTranslatef(x, y, z);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		textureMatrixModified = true;
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(IIIIIIFFII)V")
	public static void setupPerspectiveView(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int centerX, @OriginalArg(5) int centerY, @OriginalArg(6) float pitchRotation, @OriginalArg(7) float yawRotation, @OriginalArg(8) int scaleX, @OriginalArg(9) int scaleY) {
		@Pc(7) int screenLeft = (x - centerX << 8) / scaleX;
		@Pc(17) int screenRight = (x + width - centerX << 8) / scaleX;
		@Pc(25) int screenTop = (y - centerY << 8) / scaleY;
		@Pc(35) int screenBottom = (y + height - centerY << 8) / scaleY;
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		setPerspectiveFrustum((float) screenLeft * NEAR_PLANE_SCALE, (float) screenRight * NEAR_PLANE_SCALE, (float) -screenBottom * NEAR_PLANE_SCALE, (float) -screenTop * NEAR_PLANE_SCALE, 50.0F, (float) GlobalConfig.VIEW_DISTANCE);
		setViewportBounds(x, canvasHeight - y - height, width, height);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		if (pitchRotation != 0.0F) {
			gl.glRotatef(pitchRotation, 1.0F, 0.0F, 0.0F);
		}
		if (yawRotation != 0.0F) {
			gl.glRotatef(yawRotation, 0.0F, 1.0F, 0.0F);
		}
		orthographicActive = false;
		Rasteriser.screenLowerX = screenLeft;
		Rasteriser.screenUpperX = screenRight;
		Rasteriser.screenLowerY = screenTop;
		Rasteriser.screenUpperY = screenBottom;
	}

	@OriginalMember(owner = "client!tf", name = "d", descriptor = "(Z)V")
	private static void setNormalArrayEnabled(@OriginalArg(0) boolean enabled) {
		if (enabled == normalArrayEnabled) {
			return;
		}
		if (enabled) {
			gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		} else {
			gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
		}
		normalArrayEnabled = enabled;
	}

	@OriginalMember(owner = "client!tf", name = "p", descriptor = "()V")
	public static void restoreLighting() {
		if (Preferences.highDetailLighting) {
			setLightingEnabled(true);
			setNormalArrayEnabled(true);
		} else {
			setLightingEnabled(false);
			setNormalArrayEnabled(false);
		}
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(I)V")
	public static void setTextureCombineAlphaMode(@OriginalArg(0) int mode) {
		if (mode == textureCombineAlphaMode) {
			return;
		}
		if (mode == 0) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_MODULATE);
		}
		if (mode == 1) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_REPLACE);
		}
		if (mode == 2) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_ALPHA, GL2.GL_ADD);
		}
		textureCombineAlphaMode = mode;
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(FFFFFF)V")
	private static void setPerspectiveFrustum(@OriginalArg(0) float xMin, @OriginalArg(1) float xMax, @OriginalArg(2) float yMin, @OriginalArg(3) float yMax, @OriginalArg(4) float nearClip, @OriginalArg(5) float farClip) {
		float width = xMax - xMin;
		float height = yMax - yMin;

		hFOV = 2 * (float)Math.atan(width / (2 * nearClip));
		vFOV = 2 * (float)Math.atan(height / (2 * nearClip));
		hFOV = (float)Math.toDegrees(hFOV);
		vFOV = (float)Math.toDegrees(vFOV);

		@Pc(3) float nearClip2 = nearClip * 2.0F;
		matrix[0] = nearClip2 / (xMax - xMin);
		matrix[1] = 0.0F;
		matrix[2] = 0.0F;
		matrix[3] = 0.0F;
		matrix[4] = 0.0F;
		matrix[5] = nearClip2 / (yMax - yMin);
		matrix[6] = 0.0F;
		matrix[7] = 0.0F;
		matrix[8] = (xMax + xMin) / (xMax - xMin);
		matrix[9] = (yMax + yMin) / (yMax - yMin);
		matrix[10] = projectionZScale = -(farClip + nearClip) / (farClip - nearClip);
		matrix[11] = -1.0F;
		matrix[12] = 0.0F;
		matrix[13] = 0.0F;
		matrix[14] = projectionZOffset = -(nearClip2 * farClip) / (farClip - nearClip);
		matrix[15] = 0.0F;
		gl.glLoadMatrixf(matrix, 0);
		projectionDistance = 0.0F;
		depthBias = 0.0F;
	}

	@OriginalMember(owner = "client!tf", name = "b", descriptor = "(I)V")
	public static void clearColorAndDepthBuffers(@OriginalArg(0) int rgb) {
		gl.glClearColor((float) (rgb >> 16 & 0xFF) / 255.0F, (float) (rgb >> 8 & 0xFF) / 255.0F, (float) (rgb & 0xFF) / 255.0F, 0.0F);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
	}

	@OriginalMember(owner = "client!tf", name = "c", descriptor = "(I)V")
	public static void setTextureId(@OriginalArg(0) int id) {
		if (id == textureId) {
			return;
		}
		if (id == -1) {
			gl.glDisable(GL2.GL_TEXTURE_2D);
		} else {
			if (textureId == -1) {
				gl.glEnable(GL2.GL_TEXTURE_2D);
			}
			gl.glBindTexture(GL2.GL_TEXTURE_2D, id);
		}
		textureId = id;
	}

	@OriginalMember(owner = "client!tf", name = "q", descriptor = "()V")
	public static void disableDepthMask() {
		gl.glDepthMask(false);
	}

	@OriginalMember(owner = "client!tf", name = "r", descriptor = "()F")
	public static float getProjectionDistance() {
		return projectionDistance;
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(Ljava/awt/Canvas;I)I")
	public static int init(@OriginalArg(0) Canvas canvas, @OriginalArg(1) int numSamples) {
		try {
			if (!canvas.isDisplayable()) {
				return -1;
			}
			GLProfile profile = GLProfile.get(GLProfile.GL3bc);
			@Pc(8) GLCapabilities capabilities = new GLCapabilities(profile);
			if (numSamples > 0) {
				capabilities.setSampleBuffers(true);
				capabilities.setNumSamples(numSamples * 4);
			}
			@Pc(18) GLDrawableFactory factory = GLDrawableFactory.getFactory(profile);
			AWTGraphicsConfiguration config = AWTGraphicsConfiguration.create(canvas.getGraphicsConfiguration(), capabilities, capabilities);
			window = NewtFactoryAWT.getNativeWindow(canvas, config);
			if (!window.getLock().isLocked()) {
				window.lockSurface();
			}
			try {
				drawable = factory.createGLDrawable(window);
				drawable.setRealized(true);
			} finally {
				window.unlockSurface();
			}
			@Pc(29) int swapBuffersAttempts = 0;
			@Pc(36) int result;
			while (true) {
				context = drawable.createContext(null);
				try {
					result = context.makeCurrent();
					if (result != 0) {
						break;
					}
				} catch (@Pc(41) Exception ex) {
				}
				if (swapBuffersAttempts++ > 5) {
					return -2;
				}
				ThreadUtils.sleep(1000L);
			}
			if (window.getLock().isLocked()) {
				window.unlockSurface();
			}
			gl = GLContext.getCurrentGL().getGL2();
			gl.glLineWidth((float) GameShell.canvasScale);
			enabled = true;
			canvasWidth = canvas.getSize().width;
			canvasHeight = canvas.getSize().height;
			result = checkContext();
			if (result != 0) {
				quit();
				return result;
			}
			initDefaultTexture();
			initGlState();
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
			swapBuffersAttempts = 0;
			while (true) {
				try {
					drawable.swapBuffers();
					break;
				} catch (@Pc(86) Exception ex) {
					if (swapBuffersAttempts++ > 5) {
						quit();
						return -3;
					}
					ThreadUtils.sleep(100L);
				}
			}
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
			return 0;
		} catch (@Pc(103) Throwable ex) {
			quit();
			return -5;
		}
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(II)V")
	public static void setCanvasSize(@OriginalArg(0) int width, @OriginalArg(1) int height) {
		canvasWidth = width;
		canvasHeight = height;
		orthographicActive = false;
	}

	public static int leftMargin;

	public static int topMargin;

	public static int viewportWidth;

	public static int viewportHeight;

	public static void setViewportBounds(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		leftMargin = x;
		topMargin = y;
		viewportWidth = width;
		viewportHeight = height;
		resizeViewport();
	}

	@OriginalMember(owner = "client!gi", name = "b", descriptor = "()V")
	private static void resizeViewport() {
		gl.glViewport((int) (leftMargin * GameShell.canvasScale + GameShell.subpixelX), (int) (topMargin * GameShell.canvasScale + GameShell.subpixelY),
			(int) (viewportWidth * GameShell.canvasScale + GameShell.subpixelX), (int) (viewportHeight * GameShell.canvasScale + GameShell.subpixelY));
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(IIIIII)V")
	public static void setupModelPreview(@OriginalArg(0) int centerX, @OriginalArg(1) int centerY, @OriginalArg(2) int zoom, @OriginalArg(3) int depthOffset, @OriginalArg(4) int scaleX, @OriginalArg(5) int scaleY) {
		@Pc(2) int left = -centerX;
		@Pc(6) int right = canvasWidth - centerX;
		@Pc(9) int top = -centerY;
		@Pc(13) int bottom = canvasHeight - centerY;
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		@Pc(23) float scale = (float) zoom / 512.0F;
		@Pc(30) float orthoScaleX = scale * (256.0F / (float) scaleX);
		@Pc(37) float orthoScaleY = scale * (256.0F / (float) scaleY);
		gl.glOrtho((float) left * orthoScaleX, (float) right * orthoScaleX, (float) -bottom * orthoScaleY, (float) -top * orthoScaleY, 50 - depthOffset, GlobalConfig.VIEW_DISTANCE - depthOffset);
		setViewportBounds(0, 0, canvasWidth, canvasHeight);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		orthographicActive = false;
	}

	@OriginalMember(owner = "client!tf", name = "d", descriptor = "(I)V")
	public static void setTextureCombineRgbMode(@OriginalArg(0) int mode) {
		if (mode == textureCombineRgbMode) {
			return;
		}
		if (mode == 0) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_MODULATE);
		}
		if (mode == 1) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_REPLACE);
		}
		if (mode == 2) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_ADD);
		}
		if (mode == 3) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_SUBTRACT);
		}
		if (mode == 4) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_ADD_SIGNED);
		}
		if (mode == 5) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_COMBINE_RGB, GL2.GL_INTERPOLATE);
		}
		textureCombineRgbMode = mode;
	}

	@OriginalMember(owner = "client!tf", name = "s", descriptor = "()V")
	private static void initDefaultTexture() {
		@Pc(2) int[] texId = new int[1];
		gl.glGenTextures(1, texId, 0);
		defaultTextureId = texId[0];
		gl.glBindTexture(GL2.GL_TEXTURE_2D, defaultTextureId);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, 4, 1, 1, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, IntBuffer.wrap(new int[]{-1}));
		LightingManager.allocateLightArrays();
		MaterialManager.init();
	}

	@OriginalMember(owner = "client!gj", name = "b", descriptor = "(I)V")
	public static void resetMaterial() {
		MaterialManager.setMaterial(0, 0);
	}
}
