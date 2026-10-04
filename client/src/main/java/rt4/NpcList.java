package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class NpcList {
	@OriginalMember(owner = "client!nm", name = "S", descriptor = "[Lclient!km;")
	public static final Npc[] npcs = new Npc[32768];
	@OriginalMember(owner = "client!cj", name = "i", descriptor = "[I")
	public static final int[] ids = new int[32768];
	@OriginalMember(owner = "client!wd", name = "g", descriptor = "I")
	public static int size = 0;

	@OriginalMember(owner = "client!ig", name = "a", descriptor = "(I)V")
	public static void processAllNpcs() {
		for (@Pc(7) int i = 0; i < size; i++) {
			@Pc(18) int npcId = ids[i];
			@Pc(22) Npc npc = npcs[npcId];
			if (npc != null) {
				processEntity(npc.type.size, npc);
			}
		}
	}

	@OriginalMember(owner = "client!vg", name = "a", descriptor = "(IILclient!fe;)V")
	public static void processEntity(@OriginalArg(0) int entitySize, @OriginalArg(2) PathingEntity entity) {
		if (client.loop < entity.forceMoveCyclesToStart) {
			lerpToForceMoveStart(entity);
		} else if (entity.forceMoveCyclesToDest >= client.loop) {
			lerpToForceMoveDest(entity);
		} else {
			processMovement(entity);
		}
		if (entity.xFine < 128 || entity.yFine < 128 || entity.xFine >= 13184 || entity.yFine >= 13184) {
			entity.seqId = -1;
			entity.spotAnimId = -1;
			entity.forceMoveCyclesToStart = 0;
			entity.forceMoveCyclesToDest = 0;
			entity.xFine = entity.movementQueueX[0] * 128 + entity.getSize() * 64;
			entity.yFine = entity.movementQueueY[0] * 128 + entity.getSize() * 64;
			entity.resetMovementQueue();
		}
		if (entity == PlayerList.self && (entity.xFine < 1536 || entity.yFine < 1536 || entity.xFine >= 11776 || entity.yFine >= 11776)) {
			entity.spotAnimId = -1;
			entity.forceMoveCyclesToStart = 0;
			entity.forceMoveCyclesToDest = 0;
			entity.seqId = -1;
			entity.xFine = entity.movementQueueX[0] * 128 + entity.getSize() * 64;
			entity.yFine = entity.movementQueueY[0] * 128 + entity.getSize() * 64;
			entity.resetMovementQueue();
		}
		processFacing(entity);
		processAnimations(entity);
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(Lclient!fe;Z)V")
	public static void lerpToForceMoveStart(@OriginalArg(0) PathingEntity entity) {
		@Pc(8) int toStartCyclesLeft = entity.forceMoveCyclesToStart - client.loop;
		@Pc(20) int startXFine = entity.forceMoveStartX * 128 + entity.getSize() * 64;
		@Pc(36) int startYFine = entity.forceMoveStartY * 128 + entity.getSize() * 64;
		if (entity.forceMoveDirection == 0) {
			entity.targetAngle = 1024;
		}
		entity.xFine += (startXFine - entity.xFine) / toStartCyclesLeft;
		entity.yFine += (startYFine - entity.yFine) / toStartCyclesLeft;
		if (entity.forceMoveDirection == 1) {
			entity.targetAngle = 1536;
		}
		entity.movementCatchupTicks = 0;
		if (entity.forceMoveDirection == 2) {
			entity.targetAngle = 0;
		}
		if (entity.forceMoveDirection == 3) {
			entity.targetAngle = 512;
		}
	}

	@OriginalMember(owner = "client!wl", name = "a", descriptor = "(Lclient!fe;B)V")
	public static void lerpToForceMoveDest(@OriginalArg(0) PathingEntity entity) {
		if (client.loop == entity.forceMoveCyclesToDest || entity.seqId == -1 || entity.seqDelay != 0 || entity.seqDelayClock + 1 > SeqTypeList.get(entity.seqId).frameDelay[entity.seqFrame]) {
			@Pc(35) int cyclesDiff = entity.forceMoveCyclesToDest - entity.forceMoveCyclesToStart;
			@Pc(41) int lerpCyclesRemaining = client.loop - entity.forceMoveCyclesToStart;
			@Pc(52) int startXFine = entity.forceMoveStartX * 128 + entity.getSize() * 64;
			@Pc(64) int startYFine = entity.forceMoveStartY * 128 + entity.getSize() * 64;
			@Pc(75) int destXFine = entity.forceMoveDestX * 128 + entity.getSize() * 64;
			@Pc(86) int destYFine = entity.forceMoveDestY * 128 + entity.getSize() * 64;
			entity.xFine = (lerpCyclesRemaining * destXFine + startXFine * (cyclesDiff - lerpCyclesRemaining)) / cyclesDiff;
			entity.yFine = (destYFine * lerpCyclesRemaining + startYFine * (cyclesDiff - lerpCyclesRemaining)) / cyclesDiff;
		}
		entity.movementCatchupTicks = 0;
		if (entity.forceMoveDirection == 0) {
			entity.targetAngle = 1024;
		}
		if (entity.forceMoveDirection == 1) {
			entity.targetAngle = 1536;
		}
		if (entity.forceMoveDirection == 2) {
			entity.targetAngle = 0;
		}
		if (entity.forceMoveDirection == 3) {
			entity.targetAngle = 512;
		}
		entity.currentAngle = entity.targetAngle;
	}

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "(BLclient!fe;)V")
	public static void processMovement(@OriginalArg(1) PathingEntity entity) {
		@Pc(9) BasType bas = entity.getBasType();
		entity.movementSeqId = bas.idleAnimationId;
		if (entity.movementQueueSize == 0) {
			entity.movementCatchupTicks = 0;
			return;
		}
		if (entity.seqId != -1 && entity.seqDelay == 0) {
			@Pc(40) SeqType seq = SeqTypeList.get(entity.seqId);
			if (entity.seqMovementSteps > 0 && seq.looptype == 0) {
				entity.movementCatchupTicks++;
				return;
			}
			if (entity.seqMovementSteps <= 0 && seq.movetype == 0) {
				entity.movementCatchupTicks++;
				return;
			}
		}
		@Pc(79) int startX = entity.xFine;
		@Pc(82) int startY = entity.yFine;
		@Pc(99) int destX = entity.movementQueueX[entity.movementQueueSize - 1] * 128 + entity.getSize() * 64;
		@Pc(116) int destY = entity.movementQueueY[entity.movementQueueSize - 1] * 128 + entity.getSize() * 64;
		if (destX - startX > 256 || destX - startX < -256 || destY - startY > 256 || destY - startY < -256) {
			entity.xFine = destX;
			entity.yFine = destY;
			return;
		}
		if (destX <= startX) {
			if (startX <= destX) {
				if (destY > startY) {
					entity.targetAngle = 1024;
				} else if (startY > destY) {
					entity.targetAngle = 0;
				}
			} else if (destY > startY) {
				entity.targetAngle = 768;
			} else if (destY < startY) {
				entity.targetAngle = 256;
			} else {
				entity.targetAngle = 512;
			}
		} else if (destY > startY) {
			entity.targetAngle = 1280;
		} else if (startY > destY) {
			entity.targetAngle = 1792;
		} else {
			entity.targetAngle = 1536;
		}
		@Pc(224) int angleDiff = entity.targetAngle - entity.currentAngle & 0x7FF;
		@Pc(227) int walkAnimId = bas.walkFullTurnAnimationId;
		if (angleDiff > 1024) {
			angleDiff -= 2048;
		}
		@Pc(233) boolean rotateOnMove = true;
		@Pc(235) byte speedMultiplier = 1;
		if (angleDiff >= -256 && angleDiff <= 256) {
			walkAnimId = bas.walkAnimation;
		} else if (angleDiff >= 256 && angleDiff < 768) {
			walkAnimId = bas.walkCWTurnAnimationId;
		} else if (angleDiff >= -768 && angleDiff <= -256) {
			walkAnimId = bas.walkCCWTurnAnimationId;
		}
		@Pc(273) int speed = 4;
		if (walkAnimId == -1) {
			walkAnimId = bas.walkAnimation;
		}
		entity.movementSeqId = walkAnimId;
		if (entity instanceof Npc) {
			rotateOnMove = ((Npc) entity).type.rotationflag;
		}
		if (rotateOnMove) {
			if (entity.currentAngle != entity.targetAngle && entity.faceEntity == -1 && entity.turnSpeed != 0) {
				speed = 2;
			}
			if (entity.movementQueueSize > 2) {
				speed = 6;
			}
			if (entity.movementQueueSize > 3) {
				speed = 8;
			}
			if (entity.movementCatchupTicks > 0 && entity.movementQueueSize > 1) {
				speed = 8;
				entity.movementCatchupTicks--;
			}
		} else {
			if (entity.movementQueueSize > 1) {
				speed = 6;
			}
			if (entity.movementQueueSize > 2) {
				speed = 8;
			}
			if (entity.movementCatchupTicks > 0 && entity.movementQueueSize > 1) {
				entity.movementCatchupTicks--;
				speed = 8;
			}
		}
		if (entity.movementQueueSpeed[entity.movementQueueSize - 1] == 2) {
			speed <<= 0x1;
			speedMultiplier = 2;
		} else if (entity.movementQueueSpeed[entity.movementQueueSize - 1] == 0) {
			speedMultiplier = 0;
			speed >>= 0x1;
		}
		if (speed < 8 || bas.runAnimationId == -1) {
			if (bas.slowWalkAnimationId != -1 && speedMultiplier == 0) {
				if (bas.walkFullTurnAnimationId == entity.movementSeqId && bas.slowWalkFullTurnAnimationId != -1) {
					entity.movementSeqId = bas.slowWalkFullTurnAnimationId;
				} else if (bas.walkCCWTurnAnimationId == entity.movementSeqId && bas.slowWalkCCWTurnAnimationId != -1) {
					entity.movementSeqId = bas.slowWalkCCWTurnAnimationId;
				} else if (bas.walkCWTurnAnimationId == entity.movementSeqId && bas.slowWalkCWTurnAnimationId != -1) {
					entity.movementSeqId = bas.slowWalkCWTurnAnimationId;
				} else {
					entity.movementSeqId = bas.slowWalkAnimationId;
				}
			}
		} else if (bas.walkFullTurnAnimationId == entity.movementSeqId && bas.runFullTurnAnimationId != -1) {
			entity.movementSeqId = bas.runFullTurnAnimationId;
		} else if (entity.movementSeqId == bas.walkCCWTurnAnimationId && bas.runCCWTurnAnimationId != -1) {
			entity.movementSeqId = bas.runCCWTurnAnimationId;
		} else if (entity.movementSeqId == bas.walkCWTurnAnimationId && bas.runCWTurnAnimationId != -1) {
			entity.movementSeqId = bas.runCWTurnAnimationId;
		} else {
			entity.movementSeqId = bas.runAnimationId;
		}
		if (bas.movementAcceleration != -1) {
			speed <<= 0x7;
			if (entity.movementQueueSize == 1) {
				@Pc(594) int distX = (destX >= entity.xFine ? destX - entity.xFine : -destX + entity.xFine) << 7;
				@Pc(600) int speedSq = entity.movementSpeed * entity.movementSpeed;
				@Pc(622) int distY = (destY < entity.yFine ? entity.yFine - destY : -entity.yFine + destY) << 7;
				@Pc(629) int maxDist = distX > distY ? distX : distY;
				@Pc(636) int decelDist = bas.movementAcceleration * 2 * maxDist;
				if (decelDist < speedSq) {
					entity.movementSpeed /= 2;
				} else if (maxDist < speedSq / 2) {
					entity.movementSpeed -= bas.movementAcceleration;
					if (entity.movementSpeed < 0) {
						entity.movementSpeed = 0;
					}
				} else if (entity.movementSpeed < speed) {
					entity.movementSpeed += bas.movementAcceleration;
					if (entity.movementSpeed > speed) {
						entity.movementSpeed = speed;
					}
				}
			} else if (speed > entity.movementSpeed) {
				entity.movementSpeed += bas.movementAcceleration;
				if (speed < entity.movementSpeed) {
					entity.movementSpeed = speed;
				}
			} else if (entity.movementSpeed > 0) {
				entity.movementSpeed -= bas.movementAcceleration;
				if (entity.movementSpeed < 0) {
					entity.movementSpeed = 0;
				}
			}
			speed = entity.movementSpeed >> 7;
			if (speed < 1) {
				speed = 1;
			}
		}
		if (startX < destX) {
			entity.xFine += speed;
			if (destX < entity.xFine) {
				entity.xFine = destX;
			}
		} else if (startX > destX) {
			entity.xFine -= speed;
			if (destX > entity.xFine) {
				entity.xFine = destX;
			}
		}
		if (startY < destY) {
			entity.yFine += speed;
			if (entity.yFine > destY) {
				entity.yFine = destY;
			}
		} else if (destY < startY) {
			entity.yFine -= speed;
			if (destY > entity.yFine) {
				entity.yFine = destY;
			}
		}
		if (entity.xFine == destX && destY == entity.yFine) {
			entity.movementQueueSize--;
			if (entity.seqMovementSteps > 0) {
				entity.seqMovementSteps--;
			}
		}
	}

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "(ILclient!fe;)V")
	public static void processFacing(@OriginalArg(1) PathingEntity entity) {
		if (entity.turnSpeed == 0) {
			return;
		}
		@Pc(13) BasType bas = entity.getBasType();
		@Pc(43) int dx;
		@Pc(36) int dy;
		if (entity.faceEntity != -1 && entity.faceEntity < 32768) {
			@Pc(26) Npc npc = npcs[entity.faceEntity];
			if (npc != null) {
				dy = entity.yFine - npc.yFine;
				dx = entity.xFine - npc.xFine;
				if (dx != 0 || dy != 0) {
					entity.targetAngle = (int) (Math.atan2(dx, dy) * 325.949D) & 0x7FF;
				}
			}
		}
		@Pc(94) int dy2;
		@Pc(70) int idx;
		if (entity.faceEntity >= 32768) {
			idx = entity.faceEntity - 32768;
			if (idx == PlayerList.selfId) {
				idx = 2047;
			}
			@Pc(83) Player player = PlayerList.players[idx];
			if (player != null) {
				dy2 = entity.yFine - player.yFine;
				dy = entity.xFine - player.xFine;
				if (dy != 0 || dy2 != 0) {
					entity.targetAngle = (int) (Math.atan2(dy, dy2) * 325.949D) & 0x7FF;
				}
			}
		}
		if ((entity.faceX != 0 || entity.faceY != 0) && (entity.movementQueueSize == 0 || entity.movementCatchupTicks > 0)) {
			idx = entity.xFine - (entity.faceX - Camera.originX - Camera.originX) * 64;
			dx = entity.yFine - (entity.faceY - Camera.originY - Camera.originY) * 64;
			if (idx != 0 || dx != 0) {
				entity.targetAngle = (int) (Math.atan2(idx, dx) * 325.949D) & 0x7FF;
			}
			entity.faceY = 0;
			entity.faceX = 0;
		}
		idx = entity.targetAngle - entity.currentAngle & 0x7FF;
		if (idx == 0) {
			entity.standingTurnCounter = 0;
			entity.yawVelocity = 0;
		} else if (bas.yawAcceleration == 0) {
			entity.standingTurnCounter++;
			@Pc(226) boolean turning;
			if (idx > 1024) {
				entity.currentAngle -= entity.turnSpeed;
				turning = true;
				if (idx < entity.turnSpeed || idx > 2048 - entity.turnSpeed) {
					entity.currentAngle = entity.targetAngle;
					turning = false;
				}
				if (bas.idleAnimationId == entity.movementSeqId && (entity.standingTurnCounter > 25 || turning)) {
					if (bas.standingCCWTurn == -1) {
						entity.movementSeqId = bas.walkAnimation;
					} else {
						entity.movementSeqId = bas.standingCCWTurn;
					}
				}
			} else {
				turning = true;
				entity.currentAngle += entity.turnSpeed;
				if (entity.turnSpeed > idx || idx > 2048 - entity.turnSpeed) {
					turning = false;
					entity.currentAngle = entity.targetAngle;
				}
				if (bas.idleAnimationId == entity.movementSeqId && (entity.standingTurnCounter > 25 || turning)) {
					if (bas.standingCWTurn == -1) {
						entity.movementSeqId = bas.walkAnimation;
					} else {
						entity.movementSeqId = bas.standingCWTurn;
					}
				}
			}
			entity.currentAngle &= 0x7FF;
		} else {
			if (bas.idleAnimationId == entity.movementSeqId && entity.standingTurnCounter > 25) {
				if (bas.standingCWTurn == -1) {
					entity.movementSeqId = bas.walkAnimation;
				} else {
					entity.movementSeqId = bas.standingCWTurn;
				}
			}
			dx = entity.targetAngle << 5;
			if (dx != entity.preciseTargetYaw) {
				entity.yawDistanceTraveled = 0;
				entity.preciseTargetYaw = dx;
				dy = dx - entity.preciseYaw & 0xFFFF;
				dy2 = entity.yawVelocity * entity.yawVelocity / (bas.yawAcceleration * 2);
				@Pc(471) int maxDecel;
				if (entity.yawVelocity > 0 && dy >= dy2 && dy - dy2 < 32768) {
					entity.yawDecelThreshold = dy / 2;
					entity.yawAccelerating = true;
					maxDecel = bas.yawMaxSpeed * bas.yawMaxSpeed / (bas.yawAcceleration * 2);
					if (maxDecel > 32767) {
						maxDecel = 32767;
					}
					if (maxDecel < entity.yawDecelThreshold) {
						entity.yawDecelThreshold = dy - maxDecel;
					}
				} else if (entity.yawVelocity < 0 && dy2 <= 65536 - dy && 65536 - dy - dy2 < 32768) {
					entity.yawDecelThreshold = (65536 - dy) / 2;
					entity.yawAccelerating = true;
					maxDecel = bas.yawMaxSpeed * bas.yawMaxSpeed / (bas.yawAcceleration * 2);
					if (maxDecel > 32767) {
						maxDecel = 32767;
					}
					if (maxDecel < entity.yawDecelThreshold) {
						entity.yawDecelThreshold = 65536 - dy - maxDecel;
					}
				} else {
					entity.yawAccelerating = false;
				}
			}
			if (entity.yawVelocity == 0) {
				dy = entity.preciseTargetYaw - entity.preciseYaw & 0xFFFF;
				if (dy < bas.yawAcceleration) {
					entity.preciseYaw = entity.preciseTargetYaw;
				} else {
					entity.yawDistanceTraveled = 0;
					dy2 = bas.yawMaxSpeed * bas.yawMaxSpeed / (bas.yawAcceleration * 2);
					entity.yawAccelerating = true;
					if (dy2 > 32767) {
						dy2 = 32767;
					}
					if (dy >= 32768) {
						entity.yawVelocity = -bas.yawAcceleration;
						entity.yawDecelThreshold = (65536 - dy) / 2;
						if (dy2 < entity.yawDecelThreshold) {
							entity.yawDecelThreshold = 65536 - dy - dy2;
						}
					} else {
						entity.yawVelocity = bas.yawAcceleration;
						entity.yawDecelThreshold = dy / 2;
						if (dy2 < entity.yawDecelThreshold) {
							entity.yawDecelThreshold = dy - dy2;
						}
					}
				}
			} else if (entity.yawVelocity <= 0) {
				if (entity.yawDistanceTraveled >= entity.yawDecelThreshold) {
					entity.yawAccelerating = false;
				}
				if (!entity.yawAccelerating) {
					entity.yawVelocity += bas.yawAcceleration;
					if (entity.yawVelocity > 0) {
						entity.yawVelocity = 0;
					}
				} else if (-bas.yawMaxSpeed < entity.yawVelocity) {
					entity.yawVelocity -= bas.yawAcceleration;
				}
			} else {
				if (entity.yawDecelThreshold <= entity.yawDistanceTraveled) {
					entity.yawAccelerating = false;
				}
				if (!entity.yawAccelerating) {
					entity.yawVelocity -= bas.yawAcceleration;
					if (entity.yawVelocity < 0) {
						entity.yawVelocity = 0;
					}
				} else if (entity.yawVelocity < bas.yawMaxSpeed) {
					entity.yawVelocity += bas.yawAcceleration;
				}
			}
			entity.preciseYaw += entity.yawVelocity;
			entity.preciseYaw &= 0xFFFF;
			if (entity.yawVelocity <= 0) {
				entity.yawDistanceTraveled -= entity.yawVelocity;
			} else {
				entity.yawDistanceTraveled += entity.yawVelocity;
			}
			entity.currentAngle = entity.preciseYaw >> 5;
		}
	}

	@OriginalMember(owner = "client!ck", name = "a", descriptor = "(Lclient!fe;I)V")
	public static void processAnimations(@OriginalArg(0) PathingEntity entity) {
		entity.seqStretches = false;
		@Pc(18) SeqType seq;
		if (entity.movementSeqId != -1) {
			seq = SeqTypeList.get(entity.movementSeqId);
			if (seq == null || seq.frames == null) {
				entity.movementSeqId = -1;
			} else {
				entity.movementSeqDelayClock++;
				if (seq.frames.length > entity.movementSeqFrame && entity.movementSeqDelayClock > seq.frameDelay[entity.movementSeqFrame]) {
					entity.movementSeqDelayClock = 1;
					entity.movementSeqFrame++;
					entity.movementSeqNextFrame++;
					SoundPlayer.playSeqSound(entity.yFine, seq, entity.xFine, entity == PlayerList.self, entity.movementSeqFrame);
				}
				if (entity.movementSeqFrame >= seq.frames.length) {
					entity.movementSeqFrame = 0;
					entity.movementSeqDelayClock = 0;
					SoundPlayer.playSeqSound(entity.yFine, seq, entity.xFine, PlayerList.self == entity, entity.movementSeqFrame);
				}
				entity.movementSeqNextFrame = entity.movementSeqFrame + 1;
				if (entity.movementSeqNextFrame >= seq.frames.length) {
					entity.movementSeqNextFrame = 0;
				}
			}
		}
		@Pc(156) int i;
		if (entity.spotAnimId != -1 && client.loop >= entity.spotAnimStart) {
			i = SpotAnimTypeList.get(entity.spotAnimId).seqId;
			if (i == -1) {
				entity.spotAnimId = -1;
			} else {
				@Pc(165) SeqType spotSeq = SeqTypeList.get(i);
				if (spotSeq == null || spotSeq.frames == null) {
					entity.spotAnimId = -1;
				} else {
					if (entity.spotAnimFrame < 0) {
						entity.spotAnimFrame = 0;
						SoundPlayer.playSeqSound(entity.yFine, spotSeq, entity.xFine, PlayerList.self == entity, 0);
					}
					entity.spotAnimDelayClock++;
					if (entity.spotAnimFrame < spotSeq.frames.length && spotSeq.frameDelay[entity.spotAnimFrame] < entity.spotAnimDelayClock) {
						entity.spotAnimFrame++;
						entity.spotAnimDelayClock = 1;
						SoundPlayer.playSeqSound(entity.yFine, spotSeq, entity.xFine, PlayerList.self == entity, entity.spotAnimFrame);
					}
					if (entity.spotAnimFrame >= spotSeq.frames.length) {
						entity.spotAnimId = -1;
					}
					entity.spotAnimNextFrame = entity.spotAnimFrame + 1;
					if (spotSeq.frames.length <= entity.spotAnimNextFrame) {
						entity.spotAnimNextFrame = -1;
					}
				}
			}
		}
		if (entity.seqId != -1 && entity.seqDelay <= 1) {
			seq = SeqTypeList.get(entity.seqId);
			if (seq.looptype == 1 && entity.seqMovementSteps > 0 && client.loop >= entity.forceMoveCyclesToStart && client.loop > entity.forceMoveCyclesToDest) {
				entity.seqDelay = 1;
				return;
			}
		}
		if (entity.seqId != -1 && entity.seqDelay == 0) {
			seq = SeqTypeList.get(entity.seqId);
			if (seq == null || seq.frames == null) {
				entity.seqId = -1;
			} else {
				entity.seqDelayClock++;
				if (entity.seqFrame < seq.frames.length && entity.seqDelayClock > seq.frameDelay[entity.seqFrame]) {
					entity.seqDelayClock = 1;
					entity.seqFrame++;
					SoundPlayer.playSeqSound(entity.yFine, seq, entity.xFine, entity == PlayerList.self, entity.seqFrame);
				}
				if (seq.frames.length <= entity.seqFrame) {
					entity.seqFrame -= seq.replayoff;
					entity.seqReplayCount++;
					if (entity.seqReplayCount >= seq.replaycount) {
						entity.seqId = -1;
					} else if (entity.seqFrame >= 0 && seq.frames.length > entity.seqFrame) {
						SoundPlayer.playSeqSound(entity.yFine, seq, entity.xFine, PlayerList.self == entity, entity.seqFrame);
					} else {
						entity.seqId = -1;
					}
				}
				entity.seqNextFrame = entity.seqFrame + 1;
				if (entity.seqNextFrame >= seq.frames.length) {
					entity.seqNextFrame -= seq.replayoff;
					if (seq.replaycount <= entity.seqReplayCount + 1) {
						entity.seqNextFrame = -1;
					} else if (entity.seqNextFrame < 0 || entity.seqNextFrame >= seq.frames.length) {
						entity.seqNextFrame = -1;
					}
				}
				entity.seqStretches = seq.stretches;
			}
		}
		if (entity.seqDelay > 0) {
			entity.seqDelay--;
		}

		for (i = 0; i < entity.slotAnimations.length; i++) {
			@Pc(545) SlotAnimation slotAnim = entity.slotAnimations[i];
			if (slotAnim != null) {
				if (slotAnim.startDelay > 0) {
					slotAnim.startDelay--;
				} else {
					@Pc(570) SeqType slotSeq = SeqTypeList.get(slotAnim.seqId);
					if (slotSeq == null || slotSeq.frames == null) {
						entity.slotAnimations[i] = null;
					} else {
						slotAnim.delayClock++;
						if (slotAnim.currentFrame < slotSeq.frames.length && slotAnim.delayClock > slotSeq.frameDelay[slotAnim.currentFrame]) {
							slotAnim.currentFrame++;
							slotAnim.delayClock = 1;
							SoundPlayer.playSeqSound(entity.yFine, slotSeq, entity.xFine, entity == PlayerList.self, slotAnim.currentFrame);
						}
						if (slotSeq.frames.length <= slotAnim.currentFrame) {
							slotAnim.replayCount++;
							slotAnim.currentFrame -= slotSeq.replayoff;
							if (slotSeq.replaycount <= slotAnim.replayCount) {
								entity.slotAnimations[i] = null;
							} else if (slotAnim.currentFrame >= 0 && slotAnim.currentFrame < slotSeq.frames.length) {
								SoundPlayer.playSeqSound(entity.yFine, slotSeq, entity.xFine, PlayerList.self == entity, slotAnim.currentFrame);
							} else {
								entity.slotAnimations[i] = null;
							}
						}
						slotAnim.nextFrame = slotAnim.currentFrame + 1;
						if (slotSeq.frames.length <= slotAnim.nextFrame) {
							slotAnim.nextFrame -= slotSeq.replayoff;
							if (slotAnim.replayCount + 1 >= slotSeq.replaycount) {
								slotAnim.nextFrame = -1;
							} else if (slotAnim.nextFrame < 0 || slotSeq.frames.length <= slotAnim.nextFrame) {
								slotAnim.nextFrame = -1;
							}
						}
					}
				}
			}
		}
	}
}
