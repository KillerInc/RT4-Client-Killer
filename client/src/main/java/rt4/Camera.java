package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class Camera {
	@OriginalMember(owner = "client!id", name = "d", descriptor = "[[[I")
	public static final int[][][] cameraPathData = new int[2][][];
	@OriginalMember(owner = "client!sa", name = "Q", descriptor = "[I")
	public static final int[] cameraJitter = new int[5];
	@OriginalMember(owner = "client!wh", name = "m", descriptor = "[I")
	public static final int[] cameraAmplitude = new int[5];
	@OriginalMember(owner = "client!qg", name = "Y", descriptor = "[I")
	public static final int[] cameraFrequency = new int[5];
	@OriginalMember(owner = "client!ob", name = "a", descriptor = "[Z")
	public static final boolean[] customCameraActive = new boolean[5];
	@OriginalMember(owner = "client!fl", name = "s", descriptor = "I")
	public static double pitchTarget = 128;

	@OriginalMember(owner = "client!eg", name = "d", descriptor = "I")
	public static double yawTarget = 0;

	@OriginalMember(owner = "client!ef", name = "i", descriptor = "I")
	public static double pitchAccel = 0;

	@OriginalMember(owner = "client!ii", name = "a", descriptor = "I")
	public static int cameraY;
	@OriginalMember(owner = "client!gg", name = "bb", descriptor = "I")
	public static int cameraX;
	@OriginalMember(owner = "client!tg", name = "b", descriptor = "I")
	public static int cameraPitch;
	@OriginalMember(owner = "client!ol", name = "ib", descriptor = "I")
	public static int cameraYaw;
	@OriginalMember(owner = "client!sg", name = "o", descriptor = "I")
	public static int cameraType;
	@OriginalMember(owner = "client!g", name = "d", descriptor = "I")
	public static int lookAtSplineIndex = 0;
	@OriginalMember(owner = "client!eb", name = "t", descriptor = "I")
	public static int lookAtPathId = -1;
	@OriginalMember(owner = "client!j", name = "K", descriptor = "I")
	public static int moveSplineSpeedEnd = 0;
	@OriginalMember(owner = "client!sh", name = "c", descriptor = "I")
	public static int moveSplineSpeedStart = 0;
	@OriginalMember(owner = "client!t", name = "z", descriptor = "I")
	public static int splineProgress = 0;
	@OriginalMember(owner = "client!k", name = "i", descriptor = "I")
	public static int moveSplineIndex = 0;
	@OriginalMember(owner = "client!me", name = "k", descriptor = "I")
	public static int movePathId = -1;
	@OriginalMember(owner = "client!lc", name = "n", descriptor = "I")
	public static int originY;
	@OriginalMember(owner = "client!se", name = "a", descriptor = "I")
	public static int originX;
	@OriginalMember(owner = "client!bc", name = "I", descriptor = "Z")
	public static boolean splineJustFinished = false;
	@OriginalMember(owner = "client!km", name = "Pc", descriptor = "I")
	public static int renderX;
	@OriginalMember(owner = "client!kh", name = "f", descriptor = "I")
	public static int renderY;
	@OriginalMember(owner = "client!uc", name = "f", descriptor = "I")
	public static int lockedTargetY;
	@OriginalMember(owner = "client!ug", name = "h", descriptor = "I")
	public static int lockedLookAtX;
	@OriginalMember(owner = "client!vj", name = "d", descriptor = "I")
	public static int lockedLookAtY;
	@OriginalMember(owner = "client!tm", name = "g", descriptor = "I")
	public static int lockedTargetX;
	@OriginalMember(owner = "client!pa", name = "K", descriptor = "Z")
	public static boolean splineFinished = false;
	@OriginalMember(owner = "client!gk", name = "d", descriptor = "F")
	public static float yawRadians;
	public static int ZOOM = 600;
	@OriginalMember(owner = "client!il", name = "O", descriptor = "I")
	public static int lockedMoveSpeed;
	@OriginalMember(owner = "client!eg", name = "a", descriptor = "I")
	public static int lockedLookAtHeight;
	@OriginalMember(owner = "client!kf", name = "f", descriptor = "I")
	public static int lockedMinStep;
	@OriginalMember(owner = "client!ke", name = "U", descriptor = "I")
	public static int cameraOffsetX = 0;
	@OriginalMember(owner = "client!uc", name = "a", descriptor = "I")
	public static int offsetXDelta = 2;
	@OriginalMember(owner = "client!ta", name = "B", descriptor = "I")
	public static int maxPitchDistance = 0;
	@OriginalMember(owner = "client!qk", name = "h", descriptor = "I")
	public static int cameraOffsetY = 0;
	@OriginalMember(owner = "client!sj", name = "H", descriptor = "I")
	public static int yawDrift = 0;
	@OriginalMember(owner = "client!af", name = "d", descriptor = "I")
	public static int renderZ;
	@OriginalMember(owner = "client!lg", name = "d", descriptor = "F")
	public static float pitchRadians;
	@OriginalMember(owner = "client!sk", name = "jb", descriptor = "I")
	public static int lockedTargetHeight;
	@OriginalMember(owner = "client!t", name = "o", descriptor = "I")
	public static int lockedAngleSpeed;
	@OriginalMember(owner = "client!t", name = "A", descriptor = "I")
	public static int lockedMinMoveStep;

	public static double mod(double a, double b) {
		return ((a % b) + b) % b;
	}

	@OriginalMember(owner = "client!gn", name = "b", descriptor = "(B)V")
	public static void clampCameraAngle() {
		if (pitchTarget < 128) {
			pitchTarget = 128;
		}
		if (pitchTarget > 383) {
			pitchTarget = 383;
		}
		yawTarget = mod(yawTarget, 2047.0d);
		@Pc(33) int tileX = cameraX >> 7;
		@Pc(37) int tileY = cameraY >> 7;
		@Pc(43) int groundHeight = SceneGraph.getTileHeight(Player.plane, cameraX, cameraY);
		@Pc(45) int maxHeight = 0;
		@Pc(64) int i;
		if (tileX > 3 && tileY > 3 && tileX < 100 && tileY < 100) {
			for (i = tileX - 4; i <= tileX + 4; i++) {
				for (@Pc(73) int j = tileY - 4; j <= tileY + 4; j++) {
					@Pc(80) int plane = Player.plane;
					if (plane < 3 && (SceneGraph.renderFlags[1][i][j] & 0x2) == 2) {
						plane++;
					}
					@Pc(117) int heightDiff = (SceneGraph.occlusionHeights[plane][i][j] & 0xFF) * 8 + groundHeight - SceneGraph.tileHeights[plane][i][j];
					if (heightDiff > maxHeight) {
						maxHeight = heightDiff;
					}
				}
			}
		}
		i = maxHeight * 192;
		if (i > 98048) {
			i = 98048;
		}
		if (i < 32768) {
			i = 32768;
		}
		if (maxPitchDistance < i) {
			maxPitchDistance += (i - maxPitchDistance) / 24;
		} else if (i < maxPitchDistance) {
			maxPitchDistance += (i - maxPitchDistance) / 80;
		}
	}

	@OriginalMember(owner = "client!jl", name = "c", descriptor = "(I)V")
	public static void updateLockedCamera() {
		@Pc(9) int targetFineY = lockedTargetY * 128 + 64;
		@Pc(15) int targetFineX = lockedTargetX * 128 + 64;
		@Pc(23) int targetZ = SceneGraph.getTileHeight(Player.plane, targetFineX, targetFineY) - lockedTargetHeight;
		if (lockedMoveSpeed >= 100) {
			renderX = lockedTargetX * 128 + 64;
			renderY = lockedTargetY * 128 + 64;
			renderZ = SceneGraph.getTileHeight(Player.plane, renderX, renderY) - lockedTargetHeight;
		} else {
			if (renderX < targetFineX) {
				renderX += lockedMinMoveStep + lockedMoveSpeed * (targetFineX - renderX) / 1000;
				if (renderX > targetFineX) {
					renderX = targetFineX;
				}
			}
			if (renderZ < targetZ) {
				renderZ += (targetZ - renderZ) * lockedMoveSpeed / 1000 + lockedMinMoveStep;
				if (renderZ > targetZ) {
					renderZ = targetZ;
				}
			}
			if (renderX > targetFineX) {
				renderX -= lockedMinMoveStep + (renderX - targetFineX) * lockedMoveSpeed / 1000;
				if (renderX < targetFineX) {
					renderX = targetFineX;
				}
			}
			if (renderY < targetFineY) {
				renderY += lockedMinMoveStep + lockedMoveSpeed * (targetFineY - renderY) / 1000;
				if (targetFineY < renderY) {
					renderY = targetFineY;
				}
			}
			if (targetZ < renderZ) {
				renderZ -= (renderZ - targetZ) * lockedMoveSpeed / 1000 + lockedMinMoveStep;
				if (targetZ > renderZ) {
					renderZ = targetZ;
				}
			}
			if (renderY > targetFineY) {
				renderY -= lockedMinMoveStep + (renderY - targetFineY) * lockedMoveSpeed / 1000;
				if (targetFineY > renderY) {
					renderY = targetFineY;
				}
			}
		}
		targetFineY = lockedLookAtY * 128 + 64;
		targetFineX = lockedLookAtX * 128 + 64;
		targetZ = SceneGraph.getTileHeight(Player.plane, targetFineX, targetFineY) - lockedLookAtHeight;
		@Pc(236) int dz = targetZ - renderZ;
		@Pc(241) int dy = targetFineY - renderY;
		@Pc(246) int dx = targetFineX - renderX;
		@Pc(257) int horizontalDist = (int) Math.sqrt(dx * dx + dy * dy);
		@Pc(268) int targetPitch = (int) (Math.atan2(dz, horizontalDist) * 325.949D) & 0x7FF;
		if (targetPitch < 128) {
			targetPitch = 128;
		}
		if (targetPitch > 383) {
			targetPitch = 383;
		}
		@Pc(292) int targetYaw = (int) (-325.949D * Math.atan2(dx, dy)) & 0x7FF;
		if (cameraPitch < targetPitch) {
			cameraPitch += lockedMinStep + lockedAngleSpeed * (targetPitch - cameraPitch) / 1000;
			if (cameraPitch > targetPitch) {
				cameraPitch = targetPitch;
			}
		}
		if (cameraPitch > targetPitch) {
			cameraPitch -= (cameraPitch - targetPitch) * lockedAngleSpeed / 1000 + lockedMinStep;
			if (cameraPitch < targetPitch) {
				cameraPitch = targetPitch;
			}
		}
		@Pc(350) int yawDelta = targetYaw - cameraYaw;
		if (yawDelta > 1024) {
			yawDelta -= 2048;
		}
		if (yawDelta < -1024) {
			yawDelta += 2048;
		}
		if (yawDelta > 0) {
			cameraYaw += yawDelta * lockedAngleSpeed / 1000 + lockedMinStep;
			cameraYaw &= 0x7FF;
		}
		if (yawDelta < 0) {
			cameraYaw -= lockedAngleSpeed * -yawDelta / 1000 + lockedMinStep;
			cameraYaw &= 0x7FF;
		}
		@Pc(404) int yawCheck = targetYaw - cameraYaw;
		if (yawCheck > 1024) {
			yawCheck -= 2048;
		}
		if (yawCheck < -1024) {
			yawCheck += 2048;
		}
		if (yawCheck < 0 && yawDelta > 0 || yawCheck > 0 && yawDelta < 0) {
			cameraYaw = targetYaw;
		}
	}

	@OriginalMember(owner = "client!da", name = "d", descriptor = "(I)V")
	public static void updateLoginScreenCamera() {
		if (movePathId == -1 || lookAtPathId == -1) {
			return;
		}
		@Pc(27) int speed = (splineProgress * (moveSplineSpeedEnd - moveSplineSpeedStart) >> 16) + moveSplineSpeedStart;
		@Pc(30) float[] renderCoordinates = new float[3];
		splineProgress += speed;
		if (splineProgress >= 65535) {
			splineProgress = 65535;
			splineJustFinished = !splineFinished;
			splineFinished = true;
		} else {
			splineFinished = false;
			splineJustFinished = false;
		}
		@Pc(66) float t = (float) splineProgress / 65535.0F;
		@Pc(70) int moveOffset = moveSplineIndex * 2;
		@Pc(141) int c0;
		@Pc(131) int c1;
		@Pc(111) int c2;
		@Pc(119) int c3;
		@Pc(146) int c4;
		@Pc(155) int c5;
		@Pc(173) int c6;
		for (@Pc(72) int axis = 0; axis < 3; axis++) {
			c2 = (cameraPathData[movePathId][moveOffset + 2][axis] + cameraPathData[movePathId][moveOffset + 2][axis] - cameraPathData[movePathId][moveOffset + 3][axis]) * 3;
			c3 = cameraPathData[movePathId][moveOffset][axis];
			c1 = cameraPathData[movePathId][moveOffset + 1][axis] * 3;
			c0 = cameraPathData[movePathId][moveOffset][axis] * 3;
			c4 = c1 - c0;
			c5 = c2 + c0 - c1 * 2;
			c6 = cameraPathData[movePathId][moveOffset + 2][axis] + c1 - c3 - c2;
			renderCoordinates[axis] = (float) c3 + (((float) c6 * t + (float) c5) * t + (float) c4) * t;
		}
		renderZ = (int) renderCoordinates[1] * -1;
		renderX = (int) renderCoordinates[0] - originX * 128;
		renderY = (int) renderCoordinates[2] - originY * 128;
		@Pc(226) float[] lookAtCoords = new float[3];
		c0 = lookAtSplineIndex * 2;
		for (c1 = 0; c1 < 3; c1++) {
			c2 = cameraPathData[lookAtPathId][c0][c1] * 3;
			c4 = (cameraPathData[lookAtPathId][c0 + 2][c1] + cameraPathData[lookAtPathId][c0 + 2][c1] - cameraPathData[lookAtPathId][c0 + 3][c1]) * 3;
			c5 = cameraPathData[lookAtPathId][c0][c1];
			c3 = cameraPathData[lookAtPathId][c0 + 1][c1] * 3;
			c6 = c3 - c2;
			@Pc(313) int quadCoeff = c4 + c2 - c3 * 2;
			@Pc(331) int cubicCoeff = cameraPathData[lookAtPathId][c0 + 2][c1] + c3 - c4 - c5;
			lookAtCoords[c1] = (float) c5 + t * (t * (t * (float) cubicCoeff + (float) quadCoeff) + (float) c6);
		}
		@Pc(363) float dx = lookAtCoords[0] - renderCoordinates[0];
		@Pc(371) float dz = lookAtCoords[2] - renderCoordinates[2];
		@Pc(382) float dy = (lookAtCoords[1] - renderCoordinates[1]) * -1.0F;
		@Pc(392) double horizontalDist = Math.sqrt(dz * dz + dx * dx);
		pitchRadians = (float) Math.atan2(dy, horizontalDist);
		yawRadians = -((float) Math.atan2(dx, dz));
		cameraPitch = (int) ((double) pitchRadians * 325.949D) & 0x7FF;
		cameraYaw = (int) ((double) yawRadians * 325.949D) & 0x7FF;
	}

	@OriginalMember(owner = "client!vd", name = "a", descriptor = "(IIIIBI)V")
	public static void setLockedLookAt(@OriginalArg(0) int height, @OriginalArg(1) int tileY, @OriginalArg(2) int minStep, @OriginalArg(3) int tileX, @OriginalArg(5) int angleSpeed) {
		lockedMinStep = minStep;
		lockedLookAtY = tileY;
		lockedAngleSpeed = angleSpeed;
		lockedLookAtX = tileX;
		lockedLookAtHeight = height;
		if (lockedAngleSpeed >= 100) {
			@Pc(30) int fineX = lockedLookAtX * 128 + 64;
			@Pc(36) int fineY = lockedLookAtY * 128 + 64;
			@Pc(44) int fineZ = SceneGraph.getTileHeight(Player.plane, fineX, fineY) - lockedLookAtHeight;
			@Pc(49) int dz = fineZ - renderZ;
			@Pc(54) int dx = fineX - renderX;
			@Pc(59) int dy = fineY - renderY;
			@Pc(70) int horizontalDist = (int) Math.sqrt(dy * dy + dx * dx);
			cameraPitch = (int) (Math.atan2(dz, horizontalDist) * 325.949D) & 0x7FF;
			cameraYaw = (int) (Math.atan2(dx, dy) * -325.949D) & 0x7FF;
			if (cameraPitch < 128) {
				cameraPitch = 128;
			}
			if (cameraPitch > 383) {
				cameraPitch = 383;
			}
		}
		cameraType = 2;
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "(ZIIIBII)V")
	public static void setLockedPosition(@OriginalArg(0) boolean initialize, @OriginalArg(1) int minMoveStep, @OriginalArg(2) int height, @OriginalArg(3) int moveSpeed, @OriginalArg(5) int tileY, @OriginalArg(6) int tileX) {
		lockedMoveSpeed = moveSpeed;
		lockedTargetHeight = height;
		lockedTargetX = tileX;
		lockedMinMoveStep = minMoveStep;
		lockedTargetY = tileY;
		if (initialize && lockedMoveSpeed >= 100) {
			renderX = lockedTargetX * 128 + 64;
			renderY = lockedTargetY * 128 + 64;
			renderZ = SceneGraph.getTileHeight(Player.plane, renderX, renderY) - lockedTargetHeight;
		}
		cameraType = 2;
	}

	@OriginalMember(owner = "client!cl", name = "e", descriptor = "(I)V")
	public static void resetCameraEffects() {
		for (@Pc(3) int i = 0; i < 5; i++) {
			customCameraActive[i] = false;
		}
		lockedMinStep = 0;
		lockedAngleSpeed = 0;
		movePathId = -1;
		lookAtPathId = -1;
		cameraType = 1;
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(B)V")
	public static void updatePlayerCamera() {
		@Pc(14) int playerX = PlayerList.self.xFine + cameraOffsetX;
		@Pc(20) int playerY = PlayerList.self.yFine + cameraOffsetY;
		if (cameraX - playerX < -500 || cameraX - playerX > 500 || cameraY - playerY < -500 || cameraY - playerY > 500) {
			cameraX = playerX;
			cameraY = playerY;
		}
		if (cameraY != playerY) {
			cameraY += (playerY - cameraY) / 16;
		}
		if (cameraX != playerX) {
			cameraX += (playerX - cameraX) / 16;
		}
		if (Preferences.keyboardCameraEnabled) {
			for (@Pc(93) int i = 0; i < InterfaceList.keyQueueSize; i++) {
				@Pc(104) int code = InterfaceList.keyCodes[i];
				if (code == Keyboard.KEY_UP) {
					pitchTarget += 47;
				} else if (code == Keyboard.KEY_DOWN) {
					pitchTarget -= 17;
				} else if (code == Keyboard.KEY_LEFT) {
					yawTarget -= 65;
				} else if (code == Keyboard.KEY_RIGHT) {
					yawTarget += 191;
				}
			}
			clampCameraAngle();
		}
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(IIIIIIII)V")
	public static void calculateRenderPosition(@OriginalArg(0) int x, @OriginalArg(2) int pitch, @OriginalArg(3) int z, @OriginalArg(4) int distance, @OriginalArg(5) int yaw, @OriginalArg(6) int y, @OriginalArg(7) int camPitch) {
		@Pc(5) int scaledPitch;
		@Pc(29) int viewDist;
		if (GlRenderer.enabled) {
			scaledPitch = pitch - 334;
			if (scaledPitch < 0) {
				scaledPitch = 0;
			} else if (scaledPitch > 100) {
				scaledPitch = 100;
			}
			viewDist = scaledPitch * (ScriptRunner.maxViewDistance - ScriptRunner.minViewDistance) / 100 + ScriptRunner.minViewDistance;
			distance = viewDist * distance >> 8;
		}
		scaledPitch = 2048 - camPitch & 0x7FF;
		viewDist = 2048 - yaw & 0x7FF;
		@Pc(55) int offsetX = 0;
		@Pc(57) int offsetZ = distance;
		@Pc(59) int offsetY = 0;
		@Pc(72) int sin;
		@Pc(68) int cos;
		if (scaledPitch != 0) {
			cos = MathUtils.cos[scaledPitch];
			sin = MathUtils.sin[scaledPitch];
			offsetY = sin * -distance >> 16;
			offsetZ = cos * distance >> 16;
		}
		if (viewDist != 0) {
			sin = MathUtils.sin[viewDist];
			cos = MathUtils.cos[viewDist];
			offsetX = sin * offsetZ >> 16;
			offsetZ = offsetZ * cos >> 16;
		}
		cameraPitch = camPitch;
		cameraYaw = yaw;
		renderY = y - offsetZ;
		renderX = x - offsetX;
		renderZ = z - offsetY;
	}
}
