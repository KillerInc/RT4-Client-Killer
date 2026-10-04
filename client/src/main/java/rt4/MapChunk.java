package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!lh")
public final class MapChunk extends Node {

	@OriginalMember(owner = "client!lh", name = "y", descriptor = "I")
	public final int displayMinZ;

	@OriginalMember(owner = "client!lh", name = "v", descriptor = "I")
	public final int displayMaxX;

	@OriginalMember(owner = "client!lh", name = "w", descriptor = "I")
	public final int displayMaxZ;

	@OriginalMember(owner = "client!lh", name = "t", descriptor = "I")
	public final int displayMinX;

	@OriginalMember(owner = "client!lh", name = "<init>", descriptor = "(IIII)V")
	public MapChunk(@OriginalArg(0) int minX, @OriginalArg(1) int maxX, @OriginalArg(2) int maxZ, @OriginalArg(3) int minZ) {
		this.displayMinZ = minZ;
		this.displayMaxX = maxX;
		this.displayMaxZ = maxZ;
		this.displayMinX = minX;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(BII)Z")
	public final boolean containsDisplay(@OriginalArg(1) int x, @OriginalArg(2) int z) {
		return z >= this.displayMinX && this.displayMaxZ >= z && this.displayMaxX <= x && x <= this.displayMinZ;
	}
}
