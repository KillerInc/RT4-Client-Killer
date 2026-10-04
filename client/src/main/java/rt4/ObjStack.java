package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!uj")
public final class ObjStack extends Entity {

	@OriginalMember(owner = "client!uj", name = "v", descriptor = "I")
	public int amount;

	@OriginalMember(owner = "client!uj", name = "D", descriptor = "I")
	public int type;

	@OriginalMember(owner = "client!uj", name = "z", descriptor = "I")
	private int minY = -32768;

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int x, @OriginalArg(1) int z, @OriginalArg(2) int y, @OriginalArg(3) int yaw, @OriginalArg(4) int pitch) {
	}

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int orientation, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int sinYaw, @OriginalArg(4) int cosYaw, @OriginalArg(5) int x, @OriginalArg(6) int z, @OriginalArg(7) int y, @OriginalArg(8) long key, @OriginalArg(9) int bitset, @OriginalArg(10) ParticleSystem particles) {
		@Pc(16) Model model = ObjTypeList.get(this.type).getModel(-1, 0, null, this.amount, 0);
		if (model != null) {
			model.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, particles);
			this.minY = model.getMinY();
		}
	}

	@OriginalMember(owner = "client!uj", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.minY;
	}
}
