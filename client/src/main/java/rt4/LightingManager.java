package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class LightingManager {
	@OriginalMember(owner = "client!jf", name = "b", descriptor = "[F")
	private static final float[] lightPositionTemp = new float[]{0.0F, 0.0F, 0.0F, 1.0F};
	@OriginalMember(owner = "client!jf", name = "l", descriptor = "I")
	public static int lightCount = 0;
	@OriginalMember(owner = "client!jf", name = "a", descriptor = "[Lclient!gi;")
	public static Light[] lights;
	@OriginalMember(owner = "client!rh", name = "d", descriptor = "I")
	public static int visibleMaxY;
	@OriginalMember(owner = "client!aa", name = "m", descriptor = "I")
	public static int visibleMaxX;
	@OriginalMember(owner = "client!gf", name = "M", descriptor = "I")
	public static int visibleMinY;
	@OriginalMember(owner = "client!ch", name = "w", descriptor = "I")
	public static int visibleMinX;
	@OriginalMember(owner = "client!id", name = "b", descriptor = "I")
	public static int minimapRenderedPlane = -1;
	@OriginalMember(owner = "client!jf", name = "c", descriptor = "[I")
	private static int[] candidateLightIndices;
	@OriginalMember(owner = "client!jf", name = "d", descriptor = "I")
	private static int cachedStartTileY;
	@OriginalMember(owner = "client!jf", name = "e", descriptor = "I")
	private static int cachedEndTileY;
	@OriginalMember(owner = "client!jf", name = "f", descriptor = "[Z")
	private static boolean[] enabledLights;
	@OriginalMember(owner = "client!jf", name = "g", descriptor = "[[[I")
	private static int[][][] lightGrid;
	@OriginalMember(owner = "client!jf", name = "h", descriptor = "[I")
	private static int[] activeLightIndices;
	@OriginalMember(owner = "client!jf", name = "i", descriptor = "I")
	private static int cachedPlane;
	@OriginalMember(owner = "client!jf", name = "j", descriptor = "I")
	private static int planeCount;
	@OriginalMember(owner = "client!jf", name = "k", descriptor = "I")
	private static int cachedStartTileX;
	@OriginalMember(owner = "client!jf", name = "m", descriptor = "[Z")
	private static boolean[] lightSlotRetained;
	@OriginalMember(owner = "client!jf", name = "n", descriptor = "I")
	private static int cachedEndTileX;
	@OriginalMember(owner = "client!jf", name = "o", descriptor = "I")
	private static int length;
	@OriginalMember(owner = "client!jf", name = "p", descriptor = "I")
	private static int width;

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IIIIIII)V")
	public static void updateLightsForWallPiece(@OriginalArg(0) int wallFlags, @OriginalArg(1) int cameraX, @OriginalArg(2) int cameraY, @OriginalArg(3) int cameraZ, @OriginalArg(4) int plane, @OriginalArg(5) int tileX, @OriginalArg(6) int tileY) {
		if (!Preferences.highDetailLighting) {
			return;
		}
		if (wallFlags == 1 && tileX > 0) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX - 1, tileY);
		} else if (wallFlags == 4 && tileX < width - 1) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX + 1, tileY);
		} else if (wallFlags == 8 && tileY > 0) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX, tileY - 1);
		} else if (wallFlags == 2 && tileY < length - 1) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX, tileY + 1);
		} else if (wallFlags == 16 && tileX > 0 && tileY < length - 1) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX - 1, tileY + 1);
		} else if (wallFlags == 32 && tileX < width - 1 && tileY < length - 1) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX + 1, tileY + 1);
		} else if (wallFlags == 128 && tileX > 0 && tileY > 0) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX - 1, tileY - 1);
		} else if (wallFlags == 64 && tileX < width - 1 && tileY > 0) {
			updateLightsForTile(cameraX, cameraY, cameraZ, plane, tileX + 1, tileY - 1);
		}
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(Lclient!gi;)V")
	public static void addLight(@OriginalArg(0) Light light) {
		if (lightCount >= 255) {
			System.out.println("Number of lights added exceeds maximum!");
		} else {
			lights[lightCount++] = light;
		}
	}

	private static final int[][] STATIC_LIGHT_OVERRIDES = {
			{ 8005, 3063, 3046, 2 }
	};

	public static boolean matchesStaticLightOverride(int regionId, int x, int y, int plane) {
		for (int[] override : STATIC_LIGHT_OVERRIDES) {
			if (override[0] == regionId
			&& override[1] == x
			&& override[2] == y
			&& override[3] == plane) {
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "()V")
	public static void resetActiveLights() {
		for (@Pc(1) int i = 0; i < 4; i++) {
			activeLightIndices[i] = -1;
			disableLight(i);
		}
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IIIIIIII)V")
	public static void updateLightsForRegion(@OriginalArg(0) int cameraX, @OriginalArg(1) int cameraY, @OriginalArg(2) int cameraZ, @OriginalArg(3) int plane, @OriginalArg(4) int startTileX, @OriginalArg(5) int startTileY, @OriginalArg(6) int endTileX, @OriginalArg(7) int endTileY) {
		if (!Preferences.highDetailLighting || cachedPlane == plane && cachedStartTileX == startTileX && cachedStartTileY == startTileY && cachedEndTileX == endTileX && cachedEndTileY == endTileY) {
			return;
		}
		@Pc(20) int matchCount;
		for (matchCount = 0; matchCount < 4; matchCount++) {
			lightSlotRetained[matchCount] = false;
		}
		matchCount = 0;
		@Pc(33) int candidateCount = 0;
		@Pc(35) int tileX;
		@Pc(40) int tileY;
		scanTiles:
		for (tileX = startTileX; tileX <= endTileX; tileX++) {
			nextTileY:
			for (tileY = startTileY; tileY <= endTileY; tileY++) {
				@Pc(51) int packed = lightGrid[plane][tileX][tileY];
				while (true) {
					while (true) {
						checkCandidate:
						while (true) {
							if (packed == 0) {
								continue nextTileY;
							}
							@Pc(59) int lightIdx = (packed & 0xFF) - 1;
							packed >>>= 0x8;
							@Pc(65) int j;
							for (j = 0; j < candidateCount; j++) {
								if (lightIdx == candidateLightIndices[j]) {
									continue checkCandidate;
								}
							}
							for (j = 0; j < 4; j++) {
								if (lightIdx == activeLightIndices[j]) {
									if (!lightSlotRetained[j]) {
										lightSlotRetained[j] = true;
										matchCount++;
										if (matchCount == 4) {
											break scanTiles;
										}
									}
									continue checkCandidate;
								}
							}
							candidateLightIndices[candidateCount++] = lightIdx;
							matchCount++;
							if (matchCount == 4) {
								break scanTiles;
							}
						}
					}
				}
			}
		}
		for (tileX = 0; tileX < candidateCount; tileX++) {
			for (tileY = 0; tileY < 4; tileY++) {
				if (!lightSlotRetained[tileY]) {
					activeLightIndices[tileY] = candidateLightIndices[tileX];
					lightSlotRetained[tileY] = true;
					enableGlLight(tileY, lights[candidateLightIndices[tileX]], cameraX, cameraY, cameraZ);
					break;
				}
			}
		}
		for (tileX = 0; tileX < 4; tileX++) {
			if (!lightSlotRetained[tileX]) {
				activeLightIndices[tileX] = -1;
				disableLight(tileX);
			}
		}
		cachedPlane = plane;
		cachedStartTileX = startTileX;
		cachedStartTileY = startTileY;
		cachedEndTileX = endTileX;
		cachedEndTileY = endTileY;
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(III)V")
	public static void initLightGrid() {
		planeCount = 4;
		width = 104;
		length = 104;
		lightGrid = new int[planeCount][width][length];
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IIIIII)V")
	public static void updateLightsForTile(@OriginalArg(0) int cameraX, @OriginalArg(1) int cameraY, @OriginalArg(2) int cameraZ, @OriginalArg(3) int plane, @OriginalArg(4) int tileX, @OriginalArg(5) int tileY) {
		if (!Preferences.highDetailLighting || cachedPlane == plane && cachedStartTileX == tileX && cachedStartTileY == tileY && cachedEndTileX == tileX && cachedEndTileY == tileY) {
			return;
		}
		@Pc(20) int candidateCount;
		for (candidateCount = 0; candidateCount < 4; candidateCount++) {
			lightSlotRetained[candidateCount] = false;
		}
		candidateCount = 0;
		@Pc(39) int packed = lightGrid[plane][tileX][tileY];
		while (true) {
			@Pc(47) int lightIdx;
			@Pc(53) int slot;
			checkLight:
			while (packed != 0) {
				lightIdx = (packed & 0xFF) - 1;
				packed >>>= 0x8;
				for (slot = 0; slot < 4; slot++) {
					if (lightIdx == activeLightIndices[slot]) {
						lightSlotRetained[slot] = true;
						continue checkLight;
					}
				}
				candidateLightIndices[candidateCount++] = lightIdx;
			}
			for (lightIdx = 0; lightIdx < candidateCount; lightIdx++) {
				for (slot = 0; slot < 4; slot++) {
					if (!lightSlotRetained[slot]) {
						activeLightIndices[slot] = candidateLightIndices[lightIdx];
						lightSlotRetained[slot] = true;
						enableGlLight(slot, lights[candidateLightIndices[lightIdx]], cameraX, cameraY, cameraZ);
						break;
					}
				}
			}
			for (lightIdx = 0; lightIdx < 4; lightIdx++) {
				if (!lightSlotRetained[lightIdx]) {
					activeLightIndices[lightIdx] = -1;
					disableLight(lightIdx);
				}
			}
			cachedPlane = plane;
			cachedStartTileX = tileX;
			cachedStartTileY = tileY;
			cachedEndTileX = tileX;
			cachedEndTileY = tileY;
			return;
		}
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IZ)V")
	public static void updateAllLightAnimations(@OriginalArg(0) int cycle, @OriginalArg(1) boolean reset) {
		for (@Pc(1) int i = 0; i < lightCount; i++) {
			lights[i].updateAnimation(reset, cycle);
		}
		cachedPlane = -1;
		cachedStartTileX = -1;
		cachedStartTileY = -1;
		cachedEndTileX = -1;
		cachedEndTileY = -1;
	}

	@OriginalMember(owner = "client!jf", name = "b", descriptor = "()V")
	public static void buildLightGrid() {
		for (@Pc(1) int i = 0; i < lightCount; i++) {
			@Pc(8) Light light = lights[i];
			@Pc(11) int startLevel = light.level;
			if (light.extendsDown) {
				startLevel = 0;
			}
			@Pc(19) int endLevel = light.level;
			if (light.extendsUp) {
				endLevel = 3;
			}
			for (@Pc(26) int level = startLevel; level <= endLevel; level++) {
				@Pc(31) int profileOffset = 0;
				@Pc(39) int startRow = (light.y >> 7) - light.radius;
				if (startRow < 0) {
					profileOffset = -startRow;
					startRow = 0;
				}
				@Pc(55) int endRow = (light.y >> 7) + light.radius;
				if (endRow > length - 1) {
					endRow = length - 1;
				}
				for (@Pc(66) int row = startRow; row <= endRow; row++) {
					@Pc(75) short span = light.spanProfile[profileOffset++];
					@Pc(87) int startCol = (light.x >> 7) + (span >> 8) - light.radius;
					@Pc(95) int endCol = startCol + (span & 0xFF) - 1;
					if (startCol < 0) {
						startCol = 0;
					}
					if (endCol > width - 1) {
						endCol = width - 1;
					}
					for (@Pc(110) int col = startCol; col <= endCol; col++) {
						@Pc(121) int packed = lightGrid[level][col][row];
						if ((packed & 0xFF) == 0) {
							lightGrid[level][col][row] = packed | i + 1;
						} else if ((packed & 0xFF00) == 0) {
							lightGrid[level][col][row] = packed | i + 1 << 8;
						} else if ((packed & 0xFF0000) == 0) {
							lightGrid[level][col][row] = packed | i + 1 << 16;
						} else if ((packed & 0xFF000000) == 0) {
							lightGrid[level][col][row] = packed | i + 1 << 24;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(I)V")
	private static void disableLight(@OriginalArg(0) int i) {
		if (enabledLights[i]) {
			enabledLights[i] = false;
			@Pc(14) int light = i + 16384 + 4;
			@Pc(16) GL2 gl = GlRenderer.gl;
			gl.glDisable(light);
		}
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IIIII)V")
	public static void pruneInactiveLights(@OriginalArg(0) int plane, @OriginalArg(1) int tileX1, @OriginalArg(2) int tileY1, @OriginalArg(3) int tileX2, @OriginalArg(4) int tileY2) {
		if (!Preferences.highDetailLighting) {
			return;
		}
		nextSlot:
		for (@Pc(4) int slot = 0; slot < 4; slot++) {
			if (activeLightIndices[slot] != -1) {
				@Pc(20) int packed = lightGrid[plane][tileX1][tileY1];
				@Pc(28) int lightIdx;
				while (packed != 0) {
					lightIdx = (packed & 0xFF) - 1;
					packed >>>= 0x8;
					if (lightIdx == activeLightIndices[slot]) {
						continue nextSlot;
					}
				}
				packed = lightGrid[plane][tileX2][tileY2];
				while (packed != 0) {
					lightIdx = (packed & 0xFF) - 1;
					packed >>>= 0x8;
					if (lightIdx == activeLightIndices[slot]) {
						continue nextSlot;
					}
				}
			}
			activeLightIndices[slot] = -1;
			disableLight(slot);
		}
	}

	@OriginalMember(owner = "client!jf", name = "c", descriptor = "()V")
	public static void destroy() {
		lights = null;
		activeLightIndices = null;
		enabledLights = null;
		candidateLightIndices = null;
		lightSlotRetained = null;
		lightGrid = null;
	}

	@OriginalMember(owner = "client!jf", name = "e", descriptor = "()V")
	public static void initGlLightParameters() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		@Pc(3) int i;
		for (i = 0; i < 4; i++) {
			@Pc(10) int glLightId = i + 16388;
			gl.glLightfv(glLightId, GL2.GL_AMBIENT, new float[]{0.0F, 0.0F, 0.0F, 1.0F}, 0);
			gl.glLightf(glLightId, GL2.GL_LINEAR_ATTENUATION, 0.0F);
			gl.glLightf(glLightId, GL2.GL_CONSTANT_ATTENUATION, 0.0F);
		}
		for (i = 0; i < 4; i++) {
			activeLightIndices[i] = -1;
			disableLight(i);
		}
	}

	@OriginalMember(owner = "client!jf", name = "f", descriptor = "()V")
	public static void allocateLightArrays() {
		lights = new Light[255];
		activeLightIndices = new int[4];
		enabledLights = new boolean[4];
		candidateLightIndices = new int[4];
		lightSlotRetained = new boolean[4];
		lightGrid = new int[planeCount][width][length];
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(II[[[Lclient!bj;)V")
	public static void renderLightMeshes(@OriginalArg(0) int plane, @OriginalArg(1) int cycle, @OriginalArg(2) Tile[][][] tiles) {
		if (!Preferences.highDetailLighting) {
			return;
		}
		@Pc(4) GL2 gl = GlRenderer.gl;
		MaterialManager.setMaterial(0, 0);
		GlRenderer.setTextureCombineRgbMode(0);
		GlRenderer.resetTextureMatrix();
		GlRenderer.setTextureId(GlRenderer.defaultTextureId);
		gl.glDepthMask(false);
		GlRenderer.setLightingEnabled(false);
		gl.glBlendFunc(GL2.GL_DST_COLOR, GL2.GL_ONE);
		gl.glFogfv(GL2.GL_FOG_COLOR, new float[]{0.0F, 0.0F, 0.0F, 0.0F}, 0);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_CONSTANT);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND0_RGB, GL2.GL_SRC_ALPHA);
		nextLight:
		for (@Pc(56) int i = 0; i < lightCount; i++) {
			@Pc(63) Light light = lights[i];
			@Pc(66) int effectiveLevel = light.level;
			if (light.onBridge) {
				effectiveLevel--;
			}
			if (light.mesh != null) {
				@Pc(76) int profileOffset = 0;
				@Pc(84) int startRow = (light.y >> 7) - light.radius;
				@Pc(92) int endRow = (light.y >> 7) + light.radius;
				if (endRow >= visibleMaxY) {
					endRow = visibleMaxY - 1;
				}
				if (startRow < visibleMinY) {
					profileOffset = visibleMinY - startRow;
					startRow = visibleMinY;
				}
				for (@Pc(112) int row = startRow; row <= endRow; row++) {
					@Pc(121) short span = light.spanProfile[profileOffset++];
					@Pc(133) int startCol = (light.x >> 7) + (span >> 8) - light.radius;
					@Pc(141) int endCol = startCol + (span & 0xFF) - 1;
					if (startCol < visibleMinX) {
						startCol = visibleMinX;
					}
					if (endCol >= visibleMaxX) {
						endCol = visibleMaxX - 1;
					}
					for (@Pc(155) int col = startCol; col <= endCol; col++) {
						@Pc(160) Tile tile = null;
						if (effectiveLevel >= 0) {
							tile = tiles[effectiveLevel][col][row];
						}
						if (effectiveLevel < 0 || tile != null && tile.visible) {
							GlRenderer.setDepthLayer(201.5F - (float) light.level * 50.0F - 1.5F);
							gl.glTexEnvfv(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_COLOR, new float[]{0.0F, 0.0F, 0.0F, light.alpha}, 0);
							light.mesh.draw();
							continue nextLight;
						}
					}
				}
			}
		}
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_SRC0_RGB, GL2.GL_TEXTURE);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_OPERAND0_RGB, GL2.GL_SRC_COLOR);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthMask(true);
		gl.glFogfv(GL2.GL_FOG_COLOR, FogManager.fogColor, 0);
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		GlRenderer.restoreLighting();
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(ILclient!gi;III)V")
	private static void enableGlLight(@OriginalArg(0) int slot, @OriginalArg(1) Light light, @OriginalArg(2) int cameraX, @OriginalArg(3) int cameraY, @OriginalArg(4) int cameraZ) {
		@Pc(5) int glLightId = slot + 16384 + 4;
		@Pc(7) GL2 gl = GlRenderer.gl;
		if (!enabledLights[slot]) {
			gl.glEnable(glLightId);
			enabledLights[slot] = true;
		}
		gl.glLightf(glLightId, GL2.GL_QUADRATIC_ATTENUATION, light.quadraticAttenuation);
		gl.glLightfv(glLightId, GL2.GL_DIFFUSE, light.diffuse, 0);
		lightPositionTemp[0] = light.x - cameraX;
		lightPositionTemp[1] = light.z - cameraY;
		lightPositionTemp[2] = light.y - cameraZ;
		gl.glLightfv(glLightId, GL2.GL_POSITION, lightPositionTemp, 0);
	}

	@OriginalMember(owner = "client!jf", name = "g", descriptor = "()V")
	public static void clearLightGrid() {
		lightCount = 0;
		for (@Pc(3) int level = 0; level < planeCount; level++) {
			for (@Pc(8) int x = 0; x < width; x++) {
				for (@Pc(13) int y = 0; y < length; y++) {
					lightGrid[level][x][y] = 0;
				}
			}
		}
	}

}
