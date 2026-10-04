package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!bh")
public final class SpotAnim extends Entity {

	@OriginalMember(owner = "client!bh", name = "y", descriptor = "Lclient!ga;")
	private ParticleSystem particleSystem;

	@OriginalMember(owner = "client!bh", name = "B", descriptor = "I")
	private int seqFrame = 0;

	@OriginalMember(owner = "client!bh", name = "O", descriptor = "I")
	private final int seqNextFrame = -1;

	@OriginalMember(owner = "client!bh", name = "P", descriptor = "I")
	private int minY = -32768;

	@OriginalMember(owner = "client!bh", name = "T", descriptor = "Z")
	public boolean finished = false;

	@OriginalMember(owner = "client!bh", name = "U", descriptor = "I")
	private int frameClock = 0;

	@OriginalMember(owner = "client!bh", name = "I", descriptor = "I")
	public final int posY;

	@OriginalMember(owner = "client!bh", name = "Q", descriptor = "I")
	public final int posX;

	@OriginalMember(owner = "client!bh", name = "S", descriptor = "I")
	public final int plane;

	@OriginalMember(owner = "client!bh", name = "w", descriptor = "I")
	public final int endLoop;

	@OriginalMember(owner = "client!bh", name = "F", descriptor = "I")
	private final int spotAnimTypeId;

	@OriginalMember(owner = "client!bh", name = "K", descriptor = "I")
	public final int z;

	@OriginalMember(owner = "client!bh", name = "J", descriptor = "Lclient!tk;")
	private SeqType seq;

	@OriginalMember(owner = "client!bh", name = "<init>", descriptor = "(IIIIIII)V")
	public SpotAnim(@OriginalArg(0) int spotAnimTypeId, @OriginalArg(1) int plane, @OriginalArg(2) int posX, @OriginalArg(3) int posY, @OriginalArg(4) int z, @OriginalArg(5) int startLoop, @OriginalArg(6) int duration) {
		this.posY = posY;
		this.posX = posX;
		this.plane = plane;
		this.endLoop = duration + startLoop;
		this.spotAnimTypeId = spotAnimTypeId;
		this.z = z;
		@Pc(42) int seqId = SpotAnimTypeList.get(this.spotAnimTypeId).seqId;
		if (seqId == -1) {
			this.finished = true;
		} else {
			this.finished = false;
			this.seq = SeqTypeList.get(seqId);
		}
	}

	@OriginalMember(owner = "client!bh", name = "b", descriptor = "(Z)Lclient!ak;")
	private Model getModel() {
		@Pc(8) SpotAnimType type = SpotAnimTypeList.get(this.spotAnimTypeId);
		@Pc(26) Model model;
		if (this.finished) {
			model = type.constructModel(-1, -1, 0);
		} else {
			model = type.constructModel(this.seqNextFrame, this.seqFrame, this.frameClock);
		}
		return model;
	}

	@OriginalMember(owner = "client!bh", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.minY;
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int orientation, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int sinYaw, @OriginalArg(4) int cosYaw, @OriginalArg(5) int x, @OriginalArg(6) int z, @OriginalArg(7) int y, @OriginalArg(8) long key, @OriginalArg(9) int bitset, @OriginalArg(10) ParticleSystem particles) {
		@Pc(7) Model model = this.getModel();
		if (model != null) {
			model.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, this.particleSystem);
			this.minY = model.getMinY();
		}
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(ZI)V")
	public final void advanceAnimation(@OriginalArg(1) int delta) {
		if (this.finished) {
			return;
		}
		this.frameClock += delta;
		while (this.frameClock > this.seq.frameDelay[this.seqFrame]) {
			this.frameClock -= this.seq.frameDelay[this.seqFrame];
			this.seqFrame++;
			if (this.seq.frames.length <= this.seqFrame) {
				this.finished = true;
				break;
			}
		}
	}

	@OriginalMember(owner = "client!bh", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int x, @OriginalArg(1) int z, @OriginalArg(2) int y, @OriginalArg(3) int yaw, @OriginalArg(4) int pitch) {
	}
}
