package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ci")
public final class TextureOpShapeRect extends TextureOpShape {

	@OriginalMember(owner = "client!ci", name = "s", descriptor = "I")
	private final int endY;

	@OriginalMember(owner = "client!ci", name = "o", descriptor = "I")
	private final int startY;

	@OriginalMember(owner = "client!ci", name = "m", descriptor = "I")
	private final int startX;

	@OriginalMember(owner = "client!ci", name = "v", descriptor = "I")
	private final int endX;

	@OriginalMember(owner = "client!ci", name = "<init>", descriptor = "(IIIIII)V")
	public TextureOpShapeRect(@OriginalArg(0) int startX, @OriginalArg(1) int startY, @OriginalArg(2) int endX, @OriginalArg(3) int endY, @OriginalArg(4) int outlineColor, @OriginalArg(5) int lineWidth) {
		super(-1, outlineColor, lineWidth);
		this.endY = endY;
		this.startY = startY;
		this.startX = startX;
		this.endX = endX;
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(ILclient!wa;)Lclient!ci;")
	public static TextureOpShapeRect create(@OriginalArg(1) Buffer buf) {
		return new TextureOpShapeRect(buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g3(), buf.g1());
	}

	@OriginalMember(owner = "client!ci", name = "a", descriptor = "(III)V")
	@Override
	public final void renderBorderedShape(@OriginalArg(0) int h, @OriginalArg(1) int w) {
	}

	@OriginalMember(owner = "client!ci", name = "c", descriptor = "(III)V")
	@Override
	public final void renderFilledShape(@OriginalArg(1) int w, @OriginalArg(2) int h) {
	}

	@OriginalMember(owner = "client!ci", name = "a", descriptor = "(IZI)V")
	@Override
	public final void renderOutlinedShape(@OriginalArg(0) int h, @OriginalArg(2) int w) {
		@Pc(10) int x1 = w * this.startX >> 12;
		@Pc(25) int x2 = this.endX * w >> 12;
		@Pc(32) int y1 = h * this.startY >> 12;
		@Pc(39) int y2 = h * this.endY >> 12;
		TextureOpShapeRasterizer.drawLine(this.outlineColor, y2, x1, y1, x2);
	}
}
