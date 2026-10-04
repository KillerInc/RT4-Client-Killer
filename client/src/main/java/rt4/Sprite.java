package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!qf")
public abstract class Sprite extends SecondaryNode {

	@OriginalMember(owner = "client!qf", name = "I", descriptor = "I")
	public int height;

	@OriginalMember(owner = "client!qf", name = "J", descriptor = "I")
	public int innerWidth;

	@OriginalMember(owner = "client!qf", name = "K", descriptor = "I")
	protected int yOffset;

	@OriginalMember(owner = "client!qf", name = "P", descriptor = "I")
	protected int xOffset;

	@OriginalMember(owner = "client!qf", name = "V", descriptor = "I")
	public int innerHeight;

	@OriginalMember(owner = "client!qf", name = "W", descriptor = "I")
	public int width;

	@OriginalMember(owner = "client!qf", name = "<init>", descriptor = "()V")
	protected Sprite() {
	}

	@OriginalMember(owner = "client!qf", name = "c", descriptor = "(II)V")
	public abstract void drawPixels(@OriginalArg(0) int x, @OriginalArg(1) int y);

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(IIIIII)V")
	protected abstract void drawRotatedScaled(@OriginalArg(0) int w, @OriginalArg(1) int h, @OriginalArg(2) int centerX, @OriginalArg(3) int centerY, @OriginalArg(4) int angle, @OriginalArg(5) int zoom);

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(III)V")
	public abstract void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int alpha);

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(IIII)V")
	public abstract void renderResized(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h);

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(IIIII)V")
	public final void renderAngled(@OriginalArg(0) int y, @OriginalArg(1) int angle, @OriginalArg(2) int zoom, @OriginalArg(3) int x) {
		@Pc(8) int scaledW = this.innerWidth << 3;
		@Pc(17) int scaledH = this.innerHeight << 3;
		@Pc(25) int centerX = (x << 4) + (scaledW & 0xF);
		@Pc(33) int centerY = (y << 4) + (scaledH & 0xF);
		this.drawRotatedScaled(scaledW, scaledH, centerX, centerY, angle, zoom);
	}

	@OriginalMember(owner = "client!qf", name = "d", descriptor = "(II)V")
	public abstract void renderHorizontalFlip(@OriginalArg(0) int x, @OriginalArg(1) int y);

	@OriginalMember(owner = "client!qf", name = "b", descriptor = "(IIIII)V")
	public abstract void renderAlpha(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int w, @OriginalArg(3) int h, @OriginalArg(4) int alpha);

	@OriginalMember(owner = "client!qf", name = "e", descriptor = "(II)V")
	public abstract void render(@OriginalArg(0) int x, @OriginalArg(1) int y);
}
