package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ra")
public final class ProjAnim extends Entity {

	@OriginalMember(owner = "client!ra", name = "u", descriptor = "D")
	private double velocityX;

	@OriginalMember(owner = "client!ra", name = "A", descriptor = "D")
	public double y;

	@OriginalMember(owner = "client!ra", name = "N", descriptor = "D")
	private double accelerationZ;

	@OriginalMember(owner = "client!ra", name = "Q", descriptor = "Lclient!ga;")
	private ParticleSystem particleSystem;

	@OriginalMember(owner = "client!ra", name = "U", descriptor = "D")
	private double velocityZ;

	@OriginalMember(owner = "client!ra", name = "X", descriptor = "D")
	public double z;

	@OriginalMember(owner = "client!ra", name = "ab", descriptor = "I")
	private int pitch;

	@OriginalMember(owner = "client!ra", name = "bb", descriptor = "D")
	private double velocityY;

	@OriginalMember(owner = "client!ra", name = "db", descriptor = "D")
	public double x;

	@OriginalMember(owner = "client!ra", name = "eb", descriptor = "D")
	private double velocity;

	@OriginalMember(owner = "client!ra", name = "hb", descriptor = "I")
	public int yaw;

	@OriginalMember(owner = "client!ra", name = "v", descriptor = "I")
	private int frameCycle = 0;

	@OriginalMember(owner = "client!ra", name = "I", descriptor = "Z")
	private boolean isMobile = false;

	@OriginalMember(owner = "client!ra", name = "x", descriptor = "I")
	private int seqNextFrame = -1;

	@OriginalMember(owner = "client!ra", name = "fb", descriptor = "I")
	private int seqFrame = 0;

	@OriginalMember(owner = "client!ra", name = "T", descriptor = "I")
	private int minY = -32768;

	@OriginalMember(owner = "client!ra", name = "z", descriptor = "I")
	public final int lastCycle;

	@OriginalMember(owner = "client!ra", name = "E", descriptor = "I")
	private final int sourceX;

	@OriginalMember(owner = "client!ra", name = "cb", descriptor = "I")
	public final int targetIndex;

	@OriginalMember(owner = "client!ra", name = "Y", descriptor = "I")
	private final int spotanimId;

	@OriginalMember(owner = "client!ra", name = "M", descriptor = "I")
	public final int currentPlane;

	@OriginalMember(owner = "client!ra", name = "mb", descriptor = "I")
	private final int elevationPitch;

	@OriginalMember(owner = "client!ra", name = "Z", descriptor = "I")
	private final int sourceY;

	@OriginalMember(owner = "client!ra", name = "F", descriptor = "I")
	public final int baseZ;

	@OriginalMember(owner = "client!ra", name = "w", descriptor = "I")
	private final int arcScale;

	@OriginalMember(owner = "client!ra", name = "ib", descriptor = "I")
	public final int firstCycle;

	@OriginalMember(owner = "client!ra", name = "S", descriptor = "I")
	private final int sourceZ;

	@OriginalMember(owner = "client!ra", name = "gb", descriptor = "Lclient!tk;")
	private final SeqType seq;

	@OriginalMember(owner = "client!ra", name = "<init>", descriptor = "(IIIIIIIIIII)V")
	public ProjAnim(@OriginalArg(0) int spotanimId, @OriginalArg(1) int plane, @OriginalArg(2) int sourceX, @OriginalArg(3) int sourceY, @OriginalArg(4) int sourceZ, @OriginalArg(5) int firstCycle, @OriginalArg(6) int lastCycle, @OriginalArg(7) int elevationPitch, @OriginalArg(8) int arcScale, @OriginalArg(9) int targetIndex, @OriginalArg(10) int baseZ) {
		this.lastCycle = lastCycle;
		this.sourceX = sourceX;
		this.targetIndex = targetIndex;
		this.spotanimId = spotanimId;
		this.currentPlane = plane;
		this.elevationPitch = elevationPitch;
		this.sourceY = sourceY;
		this.isMobile = false;
		this.baseZ = baseZ;
		this.arcScale = arcScale;
		this.firstCycle = firstCycle;
		this.sourceZ = sourceZ;
		@Pc(58) int seqId = SpotAnimTypeList.get(this.spotanimId).seqId;
		if (seqId == -1) {
			this.seq = null;
		} else {
			this.seq = SeqTypeList.get(seqId);
		}
	}

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int x, @OriginalArg(1) int z, @OriginalArg(2) int y, @OriginalArg(3) int yaw, @OriginalArg(4) int pitch) {
	}

	@OriginalMember(owner = "client!ra", name = "b", descriptor = "(I)Lclient!ak;")
	private Model getModel() {
		@Pc(14) SpotAnimType seq = SpotAnimTypeList.get(this.spotanimId);
		@Pc(24) Model model = seq.constructModel(this.seqNextFrame, this.seqFrame, this.frameCycle);
		if (model == null) {
			return null;
		} else {
			model.rotateX(this.pitch);
			return model;
		}
	}

	@OriginalMember(owner = "client!ra", name = "b", descriptor = "(BI)V")
	public final void update(@OriginalArg(1) int delta) {
		this.x += this.velocityX * (double) delta;
		this.y += this.velocityY * (double) delta;
		this.isMobile = true;
		if (this.elevationPitch == -1) {
			this.z += this.velocityZ * (double) delta;
		} else {
			this.z += (double) delta * this.accelerationZ * 0.5D * (double) delta + (double) delta * this.velocityZ;
			this.velocityZ += this.accelerationZ * (double) delta;
		}
		this.yaw = (int) (Math.atan2(this.velocityX, this.velocityY) * 325.949D) + 1024 & 0x7FF;
		this.pitch = (int) (Math.atan2(this.velocityZ, this.velocity) * 325.949D) & 0x7FF;
		if (this.seq == null) {
			return;
		}
		this.frameCycle += delta;
		while (true) {
			do {
				do {
					if (this.frameCycle <= this.seq.frameDelay[this.seqFrame]) {
						return;
					}
					this.frameCycle -= this.seq.frameDelay[this.seqFrame];
					this.seqFrame++;
					if (this.seqFrame >= this.seq.frames.length) {
						this.seqFrame -= this.seq.replayoff;
						if (this.seqFrame < 0 || this.seq.frames.length <= this.seqFrame) {
							this.seqFrame = 0;
						}
					}
					this.seqNextFrame = this.seqFrame + 1;
				} while (this.seq.frames.length > this.seqNextFrame);
				this.seqNextFrame -= this.seq.replayoff;
			} while (this.seqNextFrame >= 0 && this.seqNextFrame < this.seq.frames.length);
			this.seqNextFrame = -1;
		}
	}

	@OriginalMember(owner = "client!ra", name = "b", descriptor = "(IIIII)V")
	public final void setTarget(@OriginalArg(0) int targetY, @OriginalArg(2) int cycle, @OriginalArg(3) int targetZ, @OriginalArg(4) int targetX) {
		@Pc(17) double remaining;
		if (!this.isMobile) {
			@Pc(10) double dy = targetY - this.sourceY;
			remaining = targetX - this.sourceX;
			@Pc(26) double dist = Math.sqrt(remaining * remaining + dy * dy);
			this.z = this.sourceZ;
			this.y = dy * (double) this.arcScale / dist + (double) this.sourceY;
			this.x = (double) this.arcScale * remaining / dist + (double) this.sourceX;
		}
		remaining = this.lastCycle + 1 - cycle;
		this.velocityY = ((double) targetY - this.y) / remaining;
		this.velocityX = ((double) targetX - this.x) / remaining;
		this.velocity = Math.sqrt(this.velocityY * this.velocityY + this.velocityX * this.velocityX);
		if (this.elevationPitch == -1) {
			this.velocityZ = ((double) targetZ - this.z) / remaining;
		} else {
			if (!this.isMobile) {
				this.velocityZ = -this.velocity * Math.tan((double) this.elevationPitch * 0.02454369D);
			}
			this.accelerationZ = ((double) targetZ - this.z - this.velocityZ * remaining) * 2.0D / (remaining * remaining);
		}
	}

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int orientation, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int sinYaw, @OriginalArg(4) int cosYaw, @OriginalArg(5) int x, @OriginalArg(6) int z, @OriginalArg(7) int y, @OriginalArg(8) long key, @OriginalArg(9) int bitset, @OriginalArg(10) ParticleSystem particles) {
		@Pc(3) Model model = this.getModel();
		if (model != null) {
			model.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, this.particleSystem);
			this.minY = model.getMinY();
		}
	}

	@OriginalMember(owner = "client!ra", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.minY;
	}
}
