package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!th")
public abstract class Entity {

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(III)Lclient!th;")
	public Entity createModel() {
		return this;
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "()Z")
	public boolean canMerge() {
		return false;
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(Lclient!th;IIIZ)V")
	public void mergeNormals(@OriginalArg(0) Entity other, @OriginalArg(1) int dx, @OriginalArg(2) int dy, @OriginalArg(3) int dz, @OriginalArg(4) boolean hideShared) {
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(IIIII)V")
	public abstract void updateModel(@OriginalArg(0) int pitch, @OriginalArg(1) int yaw, @OriginalArg(2) int roll, @OriginalArg(3) int x, @OriginalArg(4) int z);

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	public abstract void render(@OriginalArg(0) int yaw, @OriginalArg(1) int sinCameraPitch, @OriginalArg(2) int cosCameraPitch, @OriginalArg(3) int sinCameraYaw, @OriginalArg(4) int cosCameraYaw, @OriginalArg(5) int sceneX, @OriginalArg(6) int sceneZ, @OriginalArg(7) int sceneY, @OriginalArg(8) long key, @OriginalArg(9) int plane, @OriginalArg(10) ParticleSystem particleSystem);

	@OriginalMember(owner = "client!th", name = "b", descriptor = "()I")
	public abstract int getMinY();
}
