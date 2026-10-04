package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!re")
public final class TextureOpShapeBezier extends TextureOpShape {

	@OriginalMember(owner = "client!re", name = "x", descriptor = "I")
	private final int startY;

	@OriginalMember(owner = "client!re", name = "A", descriptor = "I")
	private final int endX;

	@OriginalMember(owner = "client!re", name = "v", descriptor = "I")
	private final int controlX2;

	@OriginalMember(owner = "client!re", name = "s", descriptor = "I")
	private final int startX;

	@OriginalMember(owner = "client!re", name = "n", descriptor = "I")
	private final int endY;

	@OriginalMember(owner = "client!re", name = "q", descriptor = "I")
	private final int controlY1;

	@OriginalMember(owner = "client!re", name = "m", descriptor = "I")
	private final int controlY2;

	@OriginalMember(owner = "client!re", name = "C", descriptor = "I")
	private final int controlX1;

	@OriginalMember(owner = "client!re", name = "<init>", descriptor = "(IIIIIIIIII)V")
	public TextureOpShapeBezier(@OriginalArg(0) int startX, @OriginalArg(1) int startY, @OriginalArg(2) int controlX1, @OriginalArg(3) int controlY2, @OriginalArg(4) int controlX2, @OriginalArg(5) int controlY1, @OriginalArg(6) int endX, @OriginalArg(7) int endY, @OriginalArg(8) int outlineColor, @OriginalArg(9) int lineWidth) {
		super(-1, outlineColor, lineWidth);
		this.startY = startY;
		this.endX = endX;
		this.controlX2 = controlX2;
		this.startX = startX;
		this.endY = endY;
		this.controlY1 = controlY1;
		this.controlY2 = controlY2;
		this.controlX1 = controlX1;
	}

	@OriginalMember(owner = "client!bk", name = "a", descriptor = "(BLclient!wa;)Lclient!re;")
	public static TextureOpShapeBezier create(@OriginalArg(1) Buffer buf) {
		return new TextureOpShapeBezier(buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g2b(), buf.g3(), buf.g1());
	}

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void drawCurve(@OriginalArg(0) int cx1, @OriginalArg(1) int ex, @OriginalArg(2) int cy1, @OriginalArg(3) int ey, @OriginalArg(4) int sy, @OriginalArg(5) int sx, @OriginalArg(6) int cx2, @OriginalArg(7) int cy2, @OriginalArg(8) int color) {
		if (sx >= TextureOpShapeRasterizer.clipLeft && sx <= TextureOpShapeRasterizer.clipRight && cx1 >= TextureOpShapeRasterizer.clipLeft && cx1 <= TextureOpShapeRasterizer.clipRight && cx2 >= TextureOpShapeRasterizer.clipLeft && TextureOpShapeRasterizer.clipRight >= cx2 && TextureOpShapeRasterizer.clipLeft <= ex && ex <= TextureOpShapeRasterizer.clipRight && TextureOpShapeRasterizer.clipTop <= sy && sy <= TextureOpShapeRasterizer.clipBottom && cy2 >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= cy2 && cy1 >= TextureOpShapeRasterizer.clipTop && TextureOpShapeRasterizer.clipBottom >= cy1 && ey >= TextureOpShapeRasterizer.clipTop && ey <= TextureOpShapeRasterizer.clipBottom) {
			drawCurveFast(cy1, color, cy2, cx2, ex, ey, sy, cx1, sx);
		} else {
			drawCurveClipped(sx, cx1, cy2, color, ey, cy1, ex, cx2, sy);
		}
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void drawCurveFast(@OriginalArg(1) int cy1, @OriginalArg(2) int color, @OriginalArg(3) int cy2, @OriginalArg(4) int cx2, @OriginalArg(5) int ex, @OriginalArg(6) int ey, @OriginalArg(7) int sy, @OriginalArg(8) int cx1, @OriginalArg(9) int sx) {
		if (sx == cx1 && cy2 == sy && ex == cx2 && cy1 == ey) {
			TextureOpShapeLine.plotLine(color, ey, sy, ex, sx);
			return;
		}
		@Pc(37) int prevY = sy;
		@Pc(39) int prevX = sx;
		@Pc(43) int sx3 = sx * 3;
		@Pc(47) int sy3 = sy * 3;
		@Pc(51) int cx1_3 = cx1 * 3;
		@Pc(55) int cy2_3 = cy2 * 3;
		@Pc(59) int cx2_3 = cx2 * 3;
		@Pc(63) int cy1_3 = cy1 * 3;
		@Pc(73) int aX = ex + cx1_3 - cx2_3 - sx;
		@Pc(83) int aY = ey + cy2_3 - sy - cy1_3;
		@Pc(93) int bX = sx3 + cx2_3 - cx1_3 - cx1_3;
		@Pc(103) int bY = sy3 + cy1_3 - cy2_3 - cy2_3;
		@Pc(108) int cX = cx1_3 - sx3;
		@Pc(113) int cY = cy2_3 - sy3;
		for (@Pc(115) int t = 128; t <= 4096; t += 128) {
			@Pc(126) int tSq = t * t >> 12;
			@Pc(132) int tCube = t * tSq >> 12;
			@Pc(136) int aYtCube = aY * tCube;
			@Pc(140) int bXtSq = tSq * bX;
			@Pc(144) int aXtCube = aX * tCube;
			@Pc(148) int bYtSq = tSq * bY;
			@Pc(152) int cXt = cX * t;
			@Pc(156) int cYt = cY * t;
			@Pc(167) int curX = (cXt + aXtCube + bXtSq >> 12) + sx;
			@Pc(177) int curY = sy + (cYt + aYtCube + bYtSq >> 12);
			TextureOpShapeLine.plotLine(color, curY, prevY, curX, prevX);
			prevX = curX;
			prevY = curY;
		}
	}

	@OriginalMember(owner = "client!nb", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void drawCurveClipped(@OriginalArg(0) int sx, @OriginalArg(1) int cx1, @OriginalArg(2) int cy2, @OriginalArg(4) int color, @OriginalArg(5) int ey, @OriginalArg(6) int cy1, @OriginalArg(7) int ex, @OriginalArg(8) int cx2, @OriginalArg(9) int sy) {
		if (sx == cx1 && sy == cy2 && cx2 == ex && ey == cy1) {
			TextureOpShapeRasterizer.drawLine(color, ey, sx, sy, ex);
			return;
		}
		@Pc(32) int prevX = sx;
		@Pc(36) int sy3 = sy * 3;
		@Pc(40) int sx3 = sx * 3;
		@Pc(42) int prevY = sy;
		@Pc(46) int cx1_3 = cx1 * 3;
		@Pc(50) int cy2_3 = cy2 * 3;
		@Pc(54) int cx2_3 = cx2 * 3;
		@Pc(58) int cy1_3 = cy1 * 3;
		@Pc(66) int aX = cx1_3 + ex - cx2_3 - sx;
		@Pc(76) int bX = cx2_3 + sx3 - cx1_3 - cx1_3;
		@Pc(85) int aY = cy2_3 + ey - cy1_3 - sy;
		@Pc(95) int bY = sy3 + cy1_3 - cy2_3 - cy2_3;
		@Pc(100) int cX = cx1_3 - sx3;
		@Pc(105) int cY = cy2_3 - sy3;
		for (@Pc(107) int t = 128; t <= 4096; t += 128) {
			@Pc(118) int tSq = t * t >> 12;
			@Pc(124) int tCube = t * tSq >> 12;
			@Pc(128) int bYtSq = bY * tSq;
			@Pc(132) int aYtCube = tCube * aY;
			@Pc(136) int bXtSq = tSq * bX;
			@Pc(140) int aXtCube = aX * tCube;
			@Pc(144) int cXt = cX * t;
			@Pc(156) int curX = sx + (bXtSq + aXtCube + cXt >> 12);
			@Pc(160) int cYt = t * cY;
			@Pc(172) int curY = sy + (cYt + aYtCube + bYtSq >> 12);
			TextureOpShapeRasterizer.drawLine(color, curY, prevX, prevY, curX);
			prevX = curX;
			prevY = curY;
		}
	}

	@OriginalMember(owner = "client!re", name = "a", descriptor = "(IZI)V")
	@Override
	public final void renderOutlinedShape(@OriginalArg(0) int h, @OriginalArg(2) int w) {
		@Pc(6) int sx = w * this.startX >> 12;
		@Pc(17) int cx1 = w * this.controlX1 >> 12;
		@Pc(24) int cy2 = this.controlY2 * h >> 12;
		@Pc(31) int sy = this.startY * h >> 12;
		@Pc(38) int cx2 = this.controlX2 * w >> 12;
		@Pc(45) int cy1 = h * this.controlY1 >> 12;
		@Pc(52) int ex = this.endX * w >> 12;
		@Pc(65) int ey = h * this.endY >> 12;
		drawCurve(cx1, ex, cy1, ey, sy, sx, cx2, cy2, this.outlineColor);
	}

	@OriginalMember(owner = "client!re", name = "c", descriptor = "(III)V")
	@Override
	public final void renderFilledShape(@OriginalArg(1) int h, @OriginalArg(2) int w) {
	}

	@OriginalMember(owner = "client!re", name = "a", descriptor = "(III)V")
	@Override
	public final void renderBorderedShape(@OriginalArg(0) int h, @OriginalArg(1) int w) {
	}
}
