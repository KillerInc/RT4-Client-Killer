package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!fe")
public abstract class PathingEntity extends Entity {

	@OriginalMember(owner = "client!ef", name = "a", descriptor = "[I")
	public static final int[] ANGLES = new int[]{768, 1024, 1280, 512, 1536, 256, 0, 1792};

	@OriginalMember(owner = "client!hn", name = "Y", descriptor = "I")
	public static int terrainRollAngle = 0;

	@OriginalMember(owner = "client!hm", name = "ab", descriptor = "I")
	public static int terrainPitchAngle = 0;

	@OriginalMember(owner = "client!f", name = "V", descriptor = "I")
	public static int terrainYOffset = 0;

	@OriginalMember(owner = "client!fe", name = "A", descriptor = "I")
	public int spotAnimStart;

	@OriginalMember(owner = "client!fe", name = "S", descriptor = "I")
	public int hitpointsBar;

	@OriginalMember(owner = "client!fe", name = "U", descriptor = "I")
	public int attachmentY0;

	@OriginalMember(owner = "client!fe", name = "Z", descriptor = "I")
	public int attachmentXFine;

	@OriginalMember(owner = "client!fe", name = "bb", descriptor = "I")
	public int forceMoveStartX;

	@OriginalMember(owner = "client!fe", name = "cb", descriptor = "I")
	public int currentAngle;

	@OriginalMember(owner = "client!fe", name = "fb", descriptor = "I")
	public int atachmentX0;

	@OriginalMember(owner = "client!fe", name = "hb", descriptor = "I")
	public int forceMoveCyclesToDest;

	@OriginalMember(owner = "client!fe", name = "nb", descriptor = "Ljava/lang/Object;")
	public Object attachment;

	@OriginalMember(owner = "client!fe", name = "rb", descriptor = "I")
	public int forceMoveDestY;

	@OriginalMember(owner = "client!fe", name = "tb", descriptor = "I")
	public int spotAnimY;

	@OriginalMember(owner = "client!fe", name = "ub", descriptor = "I")
	public int forceMoveCyclesToStart;

	@OriginalMember(owner = "client!fe", name = "vb", descriptor = "Lclient!ga;")
	protected ParticleSystem particleSystem;

	@OriginalMember(owner = "client!fe", name = "Ab", descriptor = "I")
	public int targetAngle;

	@OriginalMember(owner = "client!fe", name = "Jb", descriptor = "I")
	public int attachmentY;

	@OriginalMember(owner = "client!fe", name = "Ob", descriptor = "I")
	public int attachmentY1;

	@OriginalMember(owner = "client!fe", name = "Pb", descriptor = "I")
	public int attachmentX1;

	@OriginalMember(owner = "client!fe", name = "Qb", descriptor = "I")
	public int xFine;

	@OriginalMember(owner = "client!fe", name = "Ub", descriptor = "I")
	public int forceMoveDestX;

	@OriginalMember(owner = "client!fe", name = "ac", descriptor = "I")
	public int yFine;

	@OriginalMember(owner = "client!fe", name = "dc", descriptor = "I")
	public int tileHeight;

	@OriginalMember(owner = "client!fe", name = "fc", descriptor = "I")
	public int attachmentYFine;

	@OriginalMember(owner = "client!fe", name = "hc", descriptor = "I")
	public int forceMoveStartY;

	@OriginalMember(owner = "client!fe", name = "mc", descriptor = "I")
	public int forceMoveDirection;

	@OriginalMember(owner = "client!fe", name = "w", descriptor = "[I")
	public final int[] movementQueueY = new int[10];

	@OriginalMember(owner = "client!fe", name = "z", descriptor = "I")
	public int movementSpeed = 0;

	@OriginalMember(owner = "client!fe", name = "J", descriptor = "I")
	private int pitchTarget = 0;

	@OriginalMember(owner = "client!fe", name = "L", descriptor = "[I")
	public final int[] hitVisibleUntil = new int[4];

	@OriginalMember(owner = "client!fe", name = "K", descriptor = "[I")
	public final int[] movementQueueX = new int[10];

	@OriginalMember(owner = "client!fe", name = "B", descriptor = "I")
	public int seqDelayClock = 0;

	@OriginalMember(owner = "client!fe", name = "O", descriptor = "I")
	public int seqId = -1;

	@OriginalMember(owner = "client!fe", name = "ab", descriptor = "Z")
	private boolean pitchAccelerating = false;

	@OriginalMember(owner = "client!fe", name = "M", descriptor = "Z")
	public boolean yawAccelerating = false;

	@OriginalMember(owner = "client!fe", name = "db", descriptor = "I")
	public int faceX = 0;

	@OriginalMember(owner = "client!fe", name = "Q", descriptor = "I")
	public int seqReplayCount = 0;

	@OriginalMember(owner = "client!fe", name = "E", descriptor = "I")
	public int faceY = 0;

	@OriginalMember(owner = "client!fe", name = "yb", descriptor = "I")
	private int rollMidpoint = 0;

	@OriginalMember(owner = "client!fe", name = "G", descriptor = "I")
	public int basTypeId = -1;

	@OriginalMember(owner = "client!fe", name = "gb", descriptor = "I")
	public int standingTurnCounter = 0;

	@OriginalMember(owner = "client!fe", name = "pb", descriptor = "I")
	public int attachmentSetAt = 0;

	@OriginalMember(owner = "client!fe", name = "mb", descriptor = "[B")
	public final byte[] movementQueueSpeed = new byte[10];

	@OriginalMember(owner = "client!fe", name = "W", descriptor = "I")
	public int turnSpeed = 32;

	@OriginalMember(owner = "client!fe", name = "P", descriptor = "I")
	public int faceEntity = -1;

	@OriginalMember(owner = "client!fe", name = "Bb", descriptor = "Z")
	private boolean rollAccelerating = false;

	@OriginalMember(owner = "client!fe", name = "eb", descriptor = "I")
	private int rollAngle = 0;

	@OriginalMember(owner = "client!fe", name = "ib", descriptor = "I")
	public int yawDistanceTraveled = 0;

	@OriginalMember(owner = "client!fe", name = "X", descriptor = "I")
	public int preciseYaw = 0;

	@OriginalMember(owner = "client!fe", name = "Eb", descriptor = "[Lclient!ub;")
	public final SlotAnimation[] slotAnimations = new SlotAnimation[12];

	@OriginalMember(owner = "client!fe", name = "Lb", descriptor = "I")
	public int chatLoops = 100;

	@OriginalMember(owner = "client!fe", name = "V", descriptor = "I")
	public int attachmentResetAt = 0;

	@OriginalMember(owner = "client!fe", name = "Y", descriptor = "I")
	public int hitpointsBarVisibleUntil = -1000;

	@OriginalMember(owner = "client!fe", name = "Rb", descriptor = "I")
	protected int minY = -32768;

	@OriginalMember(owner = "client!fe", name = "Nb", descriptor = "I")
	public int movementQueueSize = 0;

	@OriginalMember(owner = "client!fe", name = "Zb", descriptor = "I")
	public int seqDelay = 0;

	@OriginalMember(owner = "client!fe", name = "cc", descriptor = "I")
	private int pitchVelocity = 0;

	@OriginalMember(owner = "client!fe", name = "C", descriptor = "I")
	public int spotAnimDelayClock = 0;

	@OriginalMember(owner = "client!fe", name = "Yb", descriptor = "I")
	private int rollTarget = 0;

	@OriginalMember(owner = "client!fe", name = "v", descriptor = "I")
	private int rollVelocity = 0;

	@OriginalMember(owner = "client!fe", name = "Mb", descriptor = "[I")
	public final int[] hitTypes = new int[4];

	@OriginalMember(owner = "client!fe", name = "zb", descriptor = "I")
	public int spotAnimFrame = 0;

	@OriginalMember(owner = "client!fe", name = "Db", descriptor = "I")
	public int preciseTargetYaw = 0;

	@OriginalMember(owner = "client!fe", name = "Vb", descriptor = "I")
	public int movementCatchupTicks = 0;

	@OriginalMember(owner = "client!fe", name = "s", descriptor = "I")
	public int chatEffect = 0;

	@OriginalMember(owner = "client!fe", name = "T", descriptor = "I")
	public int seqNextFrame = -1;

	@OriginalMember(owner = "client!fe", name = "Fb", descriptor = "Z")
	public boolean seqStretches = false;

	@OriginalMember(owner = "client!fe", name = "xb", descriptor = "I")
	public int yawDecelThreshold = 0;

	@OriginalMember(owner = "client!fe", name = "kb", descriptor = "I")
	public int movementSeqNextFrame = -1;

	@OriginalMember(owner = "client!fe", name = "Sb", descriptor = "I")
	public int yawVelocity = 0;

	@OriginalMember(owner = "client!fe", name = "H", descriptor = "I")
	public int movementSeqId = -1;

	@OriginalMember(owner = "client!fe", name = "gc", descriptor = "I")
	private int pitchAngle = 0;

	@OriginalMember(owner = "client!fe", name = "Tb", descriptor = "I")
	private int size = 1;

	@OriginalMember(owner = "client!fe", name = "Kb", descriptor = "I")
	public int movementSeqFrame = 0;

	@OriginalMember(owner = "client!fe", name = "ic", descriptor = "[I")
	public final int[] hitDamages = new int[4];

	@OriginalMember(owner = "client!fe", name = "Ib", descriptor = "I")
	public int seqMovementSteps = 0;

	@OriginalMember(owner = "client!fe", name = "y", descriptor = "I")
	private int pitchMidpoint = 0;

	@OriginalMember(owner = "client!fe", name = "wb", descriptor = "I")
	public int movementSeqDelayClock = 0;

	@OriginalMember(owner = "client!fe", name = "jc", descriptor = "I")
	public int chatColor = 0;

	@OriginalMember(owner = "client!fe", name = "kc", descriptor = "I")
	public int lastSeenLoop = 0;

	@OriginalMember(owner = "client!fe", name = "ec", descriptor = "I")
	public int seqFrame = 0;

	@OriginalMember(owner = "client!fe", name = "Wb", descriptor = "Lclient!na;")
	public JagString chatMessage = null;

	@OriginalMember(owner = "client!fe", name = "oc", descriptor = "I")
	public int spotAnimId = -1;

	@OriginalMember(owner = "client!fe", name = "Xb", descriptor = "I")
	public int spotAnimNextFrame = -1;

	@OriginalMember(owner = "client!fe", name = "b", descriptor = "(Z)Lclient!ck;")
	public final BasType getBasType() {
		@Pc(7) int basId = this.getBasId();
		return basId == -1 ? BasType.DEFAULT : BasTypeList.get(basId);
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(B)Z")
	public boolean isVisible() {
		return false;
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(IIIIZ)V")
	public final void teleport(@OriginalArg(1) int sizeOffset, @OriginalArg(2) int tileX, @OriginalArg(3) int tileY, @OriginalArg(4) boolean teleporting) {
		if (this.seqId != -1 && SeqTypeList.get(this.seqId).movetype == 1) {
			this.seqId = -1;
		}
		if (!teleporting) {
			@Pc(32) int dx = tileX - this.movementQueueX[0];
			@Pc(40) int dy = tileY - this.movementQueueY[0];
			if (dx >= -8 && dx <= 8 && dy >= -8 && dy <= 8) {
				if (this.movementQueueSize < 9) {
					this.movementQueueSize++;
				}
				for (@Pc(72) int i = this.movementQueueSize; i > 0; i--) {
					this.movementQueueX[i] = this.movementQueueX[i - 1];
					this.movementQueueY[i] = this.movementQueueY[i - 1];
					this.movementQueueSpeed[i] = this.movementQueueSpeed[i - 1];
				}
				this.movementQueueSpeed[0] = 1;
				this.movementQueueX[0] = tileX;
				this.movementQueueY[0] = tileY;
				return;
			}
		}
		this.movementCatchupTicks = 0;
		this.movementQueueX[0] = tileX;
		this.movementQueueY[0] = tileY;
		this.movementQueueSize = 0;
		this.seqMovementSteps = 0;
		this.yFine = sizeOffset * 64 + this.movementQueueY[0] * 128;
		this.xFine = sizeOffset * 64 + this.movementQueueX[0] * 128;
		if (GlRenderer.enabled && PlayerList.self == this) {
			FogManager.setInstantFade();
		}
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(IBI)V")
	public final void move(@OriginalArg(0) int speed, @OriginalArg(2) int direction) {
		@Pc(10) int tileX = this.movementQueueX[0];
		@Pc(15) int tileY = this.movementQueueY[0];
		if (direction == 0) {
			tileX--;
			tileY++;
		}
		if (this.seqId != -1 && SeqTypeList.get(this.seqId).movetype == 1) {
			this.seqId = -1;
		}
		if (this.movementQueueSize < 9) {
			this.movementQueueSize++;
		}
		for (@Pc(50) int i = this.movementQueueSize; i > 0; i--) {
			this.movementQueueX[i] = this.movementQueueX[i - 1];
			this.movementQueueY[i] = this.movementQueueY[i - 1];
			this.movementQueueSpeed[i] = this.movementQueueSpeed[i - 1];
		}
		if (direction == 1) {
			tileY++;
		}
		this.movementQueueSpeed[0] = (byte) speed;
		if (direction == 2) {
			tileY++;
			tileX++;
		}
		if (direction == 3) {
			tileX--;
		}
		if (direction == 4) {
			tileX++;
		}
		if (direction == 5) {
			tileY--;
			tileX--;
		}
		if (direction == 6) {
			tileY--;
		}
		if (direction == 7) {
			tileY--;
			tileX++;
		}
		this.movementQueueX[0] = tileX;
		this.movementQueueY[0] = tileY;
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(BLclient!ak;I)V")
	protected final void alignToTerrain(@OriginalArg(1) Model model, @OriginalArg(2) int yaw) {
		terrainRollAngle = 0;
		terrainPitchAngle = 0;
		terrainYOffset = 0;
		@Pc(21) BasType type = this.getBasType();
		@Pc(24) int alignWidth = type.terrainAlignWidth;
		@Pc(27) int alignDepth = type.terrainAlignDepth;
		if (alignWidth == 0 || alignDepth == 0) {
			return;
		}
		@Pc(39) int sin = MathUtils.sin[yaw];
		@Pc(43) int cos = MathUtils.cos[yaw];
		@Pc(48) int halfWidthNeg = -alignWidth / 2;
		@Pc(53) int halfDepthNeg = -alignDepth / 2;
		@Pc(64) int dy0 = halfDepthNeg * cos - halfWidthNeg * sin >> 16;
		@Pc(75) int dx0 = sin * halfDepthNeg + cos * halfWidthNeg >> 16;
		@Pc(87) int h0 = SceneGraph.getTileHeight(Player.plane, dx0 + this.xFine, this.yFine + dy0);
		@Pc(91) int halfWidth = alignWidth / 2;
		@Pc(96) int halfDepthNeg2 = -alignDepth / 2;
		@Pc(106) int dx1 = halfWidth * cos + halfDepthNeg2 * sin >> 16;
		@Pc(110) int halfDepth = alignDepth / 2;
		@Pc(121) int dy1 = halfDepthNeg2 * cos - halfWidth * sin >> 16;
		@Pc(134) int h1 = SceneGraph.getTileHeight(Player.plane, dx1 + this.xFine, this.yFine - -dy1);
		@Pc(139) int halfWidthNeg2 = -alignWidth / 2;
		@Pc(150) int dy2 = halfDepth * cos - sin * halfWidthNeg2 >> 16;
		@Pc(154) int halfDepth2 = alignDepth / 2;
		@Pc(158) int halfWidth2 = alignWidth / 2;
		@Pc(169) int dx2 = sin * halfDepth + cos * halfWidthNeg2 >> 16;
		@Pc(179) int dy3 = halfDepth2 * cos - sin * halfWidth2 >> 16;
		@Pc(189) int dx3 = sin * halfDepth2 + cos * halfWidth2 >> 16;
		@Pc(201) int h2 = SceneGraph.getTileHeight(Player.plane, this.xFine + dx2, dy2 + this.yFine);
		@Pc(212) int minFront = h1 > h0 ? h0 : h1;
		@Pc(224) int h3 = SceneGraph.getTileHeight(Player.plane, dx3 + this.xFine, dy3 + this.yFine);
		@Pc(231) int minBack = h3 > h2 ? h2 : h3;
		@Pc(238) int minRight = h3 > h1 ? h1 : h3;
		@Pc(245) int minLeft = h2 <= h0 ? h2 : h0;
		terrainPitchAngle = (int) (Math.atan2(minFront - minBack, alignDepth) * 325.95D) & 0x7FF;
		if (terrainPitchAngle != 0) {
			model.rotateX(terrainPitchAngle);
		}
		terrainRollAngle = (int) (Math.atan2(minLeft - minRight, alignWidth) * 325.95D) & 0x7FF;
		if (terrainRollAngle != 0) {
			model.rotateZ(terrainRollAngle);
		}
		terrainYOffset = h3 + h0;
		if (h2 + h1 < terrainYOffset) {
			terrainYOffset = h2 + h1;
		}
		terrainYOffset = (terrainYOffset >> 1) - this.tileHeight;
		if (terrainYOffset != 0) {
			model.translate(0, terrainYOffset, 0);
		}
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(IIII)V")
	public final void addHit(@OriginalArg(0) int type, @OriginalArg(2) int loop, @OriginalArg(3) int damage) {
		for (@Pc(11) int i = 0; i < 4; i++) {
			if (loop >= this.hitVisibleUntil[i]) {
				this.hitDamages[i] = damage;
				this.hitTypes[i] = type;
				this.hitVisibleUntil[i] = loop + 70;
				return;
			}
		}
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(Lclient!ak;B)V")
	protected final void applyBodyLean(@OriginalArg(0) Model model) {
		@Pc(16) BasType basType = this.getBasType();
		if (basType.rollTargetAngle == 0 && basType.pitchTargetAngle == 0) {
			return;
		}
		@Pc(26) int targetRoll = 0;
		@Pc(28) int targetPitch = 0;
		if (this.yawAccelerating && this.yawVelocity != 0) {
			targetPitch = basType.pitchTargetAngle;
			if (this.yawVelocity >= 0) {
				targetRoll = basType.rollTargetAngle;
			} else {
				targetRoll = -basType.rollTargetAngle;
			}
		}
		@Pc(101) int stoppingDist;
		@Pc(106) int delta;
		@Pc(134) int maxSpeedDist;
		@Pc(138) int limit;
		if (this.rollTarget != targetRoll) {
			this.rollTarget = targetRoll;
			if (this.rollVelocity > 0 && this.rollAngle < targetRoll) {
				stoppingDist = this.rollVelocity * this.rollVelocity / (basType.rollAcceleration * 2);
				delta = targetRoll - this.rollAngle;
				if (stoppingDist <= delta) {
					this.rollAccelerating = true;
					this.rollMidpoint = (targetRoll + this.rollAngle - stoppingDist) / 2;
					maxSpeedDist = basType.rollMaxSpeed * basType.rollMaxSpeed / (basType.rollAcceleration * 2);
					limit = targetRoll - maxSpeedDist;
					if (this.rollMidpoint < limit) {
						this.rollMidpoint = limit;
					}
				} else {
					this.rollAccelerating = false;
				}
			} else if (this.rollVelocity < 0 && this.rollAngle > targetRoll) {
				stoppingDist = this.rollVelocity * this.rollVelocity / (basType.rollAcceleration * 2);
				delta = targetRoll - this.rollAngle;
				if (delta >= stoppingDist) {
					this.rollAccelerating = true;
					this.rollMidpoint = (this.rollAngle + stoppingDist + targetRoll) / 2;
					maxSpeedDist = basType.rollMaxSpeed * basType.rollMaxSpeed / (basType.rollAcceleration * 2);
					limit = maxSpeedDist + targetRoll;
					if (this.rollMidpoint > limit) {
						this.rollMidpoint = limit;
					}
				} else {
					this.rollAccelerating = false;
				}
			} else {
				this.rollAccelerating = false;
			}
		}
		if (this.rollVelocity == 0) {
			stoppingDist = this.rollTarget - this.rollAngle;
			if (-basType.rollAcceleration < stoppingDist && basType.rollAcceleration > stoppingDist) {
				this.rollAngle = this.rollTarget;
			} else {
				this.rollAccelerating = true;
				delta = basType.rollMaxSpeed * basType.rollMaxSpeed / (basType.rollAcceleration * 2);
				this.rollMidpoint = (this.rollTarget + this.rollAngle) / 2;
				if (stoppingDist >= 0) {
					maxSpeedDist = this.rollTarget - delta;
					this.rollVelocity = basType.rollAcceleration;
					if (maxSpeedDist > this.rollMidpoint) {
						this.rollMidpoint = maxSpeedDist;
					}
				} else {
					this.rollVelocity = -basType.rollAcceleration;
					maxSpeedDist = delta + this.rollTarget;
					if (this.rollMidpoint > maxSpeedDist) {
						this.rollMidpoint = maxSpeedDist;
					}
				}
			}
		} else if (this.rollVelocity > 0) {
			if (this.rollAngle >= this.rollMidpoint) {
				this.rollAccelerating = false;
			}
			if (!this.rollAccelerating) {
				this.rollVelocity -= basType.rollAcceleration;
				if (this.rollVelocity < 0) {
					this.rollVelocity = 0;
				}
			} else if (basType.rollMaxSpeed > this.rollVelocity) {
				this.rollVelocity += basType.rollAcceleration;
			}
		} else {
			if (this.rollMidpoint >= this.rollAngle) {
				this.rollAccelerating = false;
			}
			if (!this.rollAccelerating) {
				this.rollVelocity += basType.rollAcceleration;
				if (this.rollVelocity > 0) {
					this.rollVelocity = 0;
				}
			} else if (this.rollVelocity > -basType.rollMaxSpeed) {
				this.rollVelocity -= basType.rollAcceleration;
			}
		}
		this.rollAngle += this.rollVelocity;
		if (this.rollAngle != 0) {
			stoppingDist = this.rollAngle >> 5 & 0x7FF;
			delta = model.getMinY() / 2;
			model.translate(0, -delta, 0);
			model.rotateZ(stoppingDist);
			model.translate(0, delta, 0);
		}
		if (targetPitch != this.pitchTarget) {
			this.pitchTarget = targetPitch;
			if (this.pitchVelocity > 0 && this.pitchAngle < targetPitch) {
				stoppingDist = this.pitchVelocity * this.pitchVelocity / (basType.pitchAcceleration * 2);
				delta = targetPitch - this.pitchAngle;
				if (stoppingDist > delta) {
					this.pitchAccelerating = false;
				} else {
					this.pitchMidpoint = (this.pitchAngle + targetPitch - stoppingDist) / 2;
					this.pitchAccelerating = true;
					maxSpeedDist = basType.pitchMaxSpeed * basType.pitchMaxSpeed / (basType.pitchAcceleration * 2);
					limit = targetPitch - maxSpeedDist;
					if (this.pitchMidpoint < limit) {
						this.pitchMidpoint = limit;
					}
				}
			} else if (this.pitchVelocity < 0 && this.pitchAngle > targetPitch) {
				delta = targetPitch - this.pitchAngle;
				stoppingDist = this.pitchVelocity * this.pitchVelocity / (basType.pitchAcceleration * 2);
				if (delta >= stoppingDist) {
					this.pitchMidpoint = (stoppingDist + this.pitchAngle + targetPitch) / 2;
					this.pitchAccelerating = true;
					maxSpeedDist = basType.pitchMaxSpeed * basType.pitchMaxSpeed / (basType.pitchAcceleration * 2);
					limit = maxSpeedDist + targetPitch;
					if (limit < this.pitchMidpoint) {
						this.pitchMidpoint = limit;
					}
				} else {
					this.pitchAccelerating = false;
				}
			} else {
				this.pitchAccelerating = false;
			}
		}
		if (this.pitchVelocity == 0) {
			stoppingDist = this.pitchTarget - this.pitchAngle;
			if (stoppingDist > -basType.pitchAcceleration && basType.pitchAcceleration > stoppingDist) {
				this.pitchAngle = this.pitchTarget;
			} else {
				this.pitchMidpoint = (this.pitchTarget + this.pitchAngle) / 2;
				this.pitchAccelerating = true;
				delta = basType.pitchMaxSpeed * basType.pitchMaxSpeed / (basType.pitchAcceleration * 2);
				if (stoppingDist < 0) {
					this.pitchVelocity = -basType.pitchAcceleration;
					maxSpeedDist = delta + this.pitchTarget;
					if (this.pitchMidpoint > maxSpeedDist) {
						this.pitchMidpoint = maxSpeedDist;
					}
				} else {
					this.pitchVelocity = basType.pitchAcceleration;
					maxSpeedDist = this.pitchTarget - delta;
					if (this.pitchMidpoint < maxSpeedDist) {
						this.pitchMidpoint = maxSpeedDist;
					}
				}
			}
		} else if (this.pitchVelocity > 0) {
			if (this.pitchAngle >= this.pitchMidpoint) {
				this.pitchAccelerating = false;
			}
			if (!this.pitchAccelerating) {
				this.pitchVelocity -= basType.pitchAcceleration;
				if (this.pitchVelocity < 0) {
					this.pitchVelocity = 0;
				}
			} else if (this.pitchVelocity < basType.pitchMaxSpeed) {
				this.pitchVelocity += basType.pitchAcceleration;
			}
		} else {
			if (this.pitchMidpoint >= this.pitchAngle) {
				this.pitchAccelerating = false;
			}
			if (!this.pitchAccelerating) {
				this.pitchVelocity += basType.pitchAcceleration;
				if (this.pitchVelocity > 0) {
					this.pitchVelocity = 0;
				}
			} else if (-basType.pitchMaxSpeed < this.pitchVelocity) {
				this.pitchVelocity -= basType.pitchAcceleration;
			}
		}
		this.pitchAngle += this.pitchVelocity;
		if (this.pitchAngle != 0) {
			stoppingDist = this.pitchAngle >> 5 & 0x7FF;
			delta = model.getMinY() / 2;
			model.translate(0, -delta, 0);
			model.rotateX(stoppingDist);
			model.translate(0, delta, 0);
		}
	}

	@OriginalMember(owner = "client!fe", name = "b", descriptor = "(I)I")
	public abstract int getBasId();

	@OriginalMember(owner = "client!fe", name = "c", descriptor = "(I)V")
	public final void resetMovementQueue() {
		this.movementQueueSize = 0;
		this.seqMovementSteps = 0;
	}

	@OriginalMember(owner = "client!fe", name = "d", descriptor = "(I)I")
	public final int getModelHeight() {
		return this.minY == -32768 ? 200 : -this.minY;
	}

	@OriginalMember(owner = "client!fe", name = "a", descriptor = "(II)V")
	public final void setSize(@OriginalArg(0) int size) {
		this.size = size;
	}

	@OriginalMember(owner = "client!fe", name = "c", descriptor = "(B)I")
	public int getSize() {
		return this.size;
	}
}
