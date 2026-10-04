package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!pe")
public final class Occluder {

	@OriginalMember(owner = "client!pe", name = "b", descriptor = "I")
	public int minZ;

	@OriginalMember(owner = "client!pe", name = "c", descriptor = "I")
	public int maxX;

	@OriginalMember(owner = "client!pe", name = "e", descriptor = "I")
	public int maxTileX;

	@OriginalMember(owner = "client!pe", name = "f", descriptor = "I")
	public int maxZ;

	@OriginalMember(owner = "client!pe", name = "g", descriptor = "I")
	public int farXSlope;

	@OriginalMember(owner = "client!pe", name = "h", descriptor = "I")
	public int maxY;

	@OriginalMember(owner = "client!pe", name = "i", descriptor = "I")
	public int farYSlope;

	@OriginalMember(owner = "client!pe", name = "k", descriptor = "I")
	public int minTileX;

	@OriginalMember(owner = "client!pe", name = "l", descriptor = "I")
	public int type;

	@OriginalMember(owner = "client!pe", name = "m", descriptor = "I")
	public int nearYSlope;

	@OriginalMember(owner = "client!pe", name = "p", descriptor = "I")
	public int nearXSlope;

	@OriginalMember(owner = "client!pe", name = "s", descriptor = "I")
	public int minY;

	@OriginalMember(owner = "client!pe", name = "t", descriptor = "I")
	public int nearZSlope;

	@OriginalMember(owner = "client!pe", name = "u", descriptor = "I")
	public int minX;

	@OriginalMember(owner = "client!pe", name = "v", descriptor = "I")
	public int minTileY;

	@OriginalMember(owner = "client!pe", name = "w", descriptor = "I")
	public int direction;

	@OriginalMember(owner = "client!pe", name = "x", descriptor = "I")
	public int farZSlope;

	@OriginalMember(owner = "client!pe", name = "y", descriptor = "I")
	public int maxTileY;

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(IIIIIII)V")
	public static void add(@OriginalArg(0) int type, @OriginalArg(1) int minX, @OriginalArg(2) int maxX, @OriginalArg(3) int minY, @OriginalArg(4) int maxY, @OriginalArg(5) int minZ, @OriginalArg(6) int maxZ) {
		@Pc(3) Occluder occluder = new Occluder();
		occluder.minTileX = minX / 128;
		occluder.maxTileX = maxX / 128;
		occluder.minTileY = minY / 128;
		occluder.maxTileY = maxY / 128;
		occluder.type = type;
		occluder.minX = minX;
		occluder.maxX = maxX;
		occluder.minY = minY;
		occluder.maxY = maxY;
		occluder.minZ = minZ;
		occluder.maxZ = maxZ;
		SceneGraph.occluders[SceneGraph.occluderCount++] = occluder;
	}
}
