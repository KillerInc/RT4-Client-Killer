package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!rh")
public final class PlainTile {

	@OriginalMember(owner = "client!rh", name = "m", descriptor = "Z")
	public boolean flat = true;

	@OriginalMember(owner = "client!rh", name = "f", descriptor = "I")
	public final int colorSE;

	@OriginalMember(owner = "client!rh", name = "c", descriptor = "I")
	public final int colorNE;

	@OriginalMember(owner = "client!rh", name = "n", descriptor = "I")
	public final int colorSW;

	@OriginalMember(owner = "client!rh", name = "l", descriptor = "I")
	public final int flatColor;

	@OriginalMember(owner = "client!rh", name = "b", descriptor = "I")
	public final int colorNW;

	@OriginalMember(owner = "client!rh", name = "i", descriptor = "I")
	public final int textureId;

	@OriginalMember(owner = "client!rh", name = "<init>", descriptor = "(IIIIIIZ)V")
	public PlainTile(@OriginalArg(0) int colorSW, @OriginalArg(1) int colorSE, @OriginalArg(2) int colorNE, @OriginalArg(3) int colorNW, @OriginalArg(4) int textureId, @OriginalArg(5) int flatColor, @OriginalArg(6) boolean flat) {
		this.colorSE = colorSE;
		this.colorNE = colorNE;
		this.colorSW = colorSW;
		this.flatColor = flatColor;
		this.flat = flat;
		this.colorNW = colorNW;
		this.textureId = textureId;
	}
}
