package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!kf")
public abstract class TextureOpShape {

	@OriginalMember(owner = "client!kf", name = "e", descriptor = "I")
	public final int outlineColor;

	@OriginalMember(owner = "client!kf", name = "g", descriptor = "I")
	protected final int lineWidth;

	@OriginalMember(owner = "client!kf", name = "b", descriptor = "I")
	public final int fillColor;

	@OriginalMember(owner = "client!kf", name = "<init>", descriptor = "(III)V")
	protected TextureOpShape(@OriginalArg(0) int fillColor, @OriginalArg(1) int outlineColor, @OriginalArg(2) int lineWidth) {
		this.outlineColor = outlineColor;
		this.lineWidth = lineWidth;
		this.fillColor = fillColor;
	}

	@OriginalMember(owner = "client!kf", name = "a", descriptor = "(III)V")
	public abstract void renderBorderedShape(@OriginalArg(0) int x, @OriginalArg(1) int y);

	@OriginalMember(owner = "client!kf", name = "a", descriptor = "(IZI)V")
	public abstract void renderOutlinedShape(@OriginalArg(0) int x, @OriginalArg(2) int y);

	@OriginalMember(owner = "client!kf", name = "c", descriptor = "(III)V")
	public abstract void renderFilledShape(@OriginalArg(1) int x, @OriginalArg(2) int y);
}
