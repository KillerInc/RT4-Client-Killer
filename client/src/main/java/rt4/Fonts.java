package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.*;

public class Fonts {
	@OriginalMember(owner = "client!j", name = "x", descriptor = "Lclient!rk;")
	public static Font p11Full;
	@OriginalMember(owner = "client!rh", name = "h", descriptor = "Lclient!rk;")
	public static Font p12Full;
	@OriginalMember(owner = "client!wl", name = "q", descriptor = "Lclient!rk;")
	public static Font b12Full;
	@OriginalMember(owner = "client!vj", name = "j", descriptor = "Lclient!dd;")
	public static SoftwareFont p11FullSoftware;

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(Lclient!ve;Lclient!ve;Z)I")
	public static int getReady(@OriginalArg(0) Js5 fontArchive, @OriginalArg(1) Js5 metricsArchive) {
		@Pc(5) int ready = 0;
		if (fontArchive.isFileReady(Sprites.p11FullId)) {
			ready++;
		}
		if (fontArchive.isFileReady(Sprites.p12FullId)) {
			ready++;
		}
		if (fontArchive.isFileReady(Sprites.b12FullId)) {
			ready++;
		}
		if (metricsArchive.isFileReady(Sprites.p11FullId)) {
			ready++;
		}
		if (metricsArchive.isFileReady(Sprites.p12FullId)) {
			ready++;
		}
		if (metricsArchive.isFileReady(Sprites.b12FullId)) {
			ready++;
		}
		return ready;
	}

	@OriginalMember(owner = "client!ld", name = "a", descriptor = "(B)I")
	public static int getTotal() {
		return 6;
	}

	@OriginalMember(owner = "client!hn", name = "a", descriptor = "(Lclient!ve;ILclient!ve;)V")
	public static void load(@OriginalArg(0) Js5 fontArchive, @OriginalArg(2) Js5 metricsArchive) {
		p11Full = Font.load(Sprites.p11FullId, metricsArchive, fontArchive);
		if (GlRenderer.enabled) {
			p11FullSoftware = SoftwareFont.load(Sprites.p11FullId, fontArchive, metricsArchive);
		} else {
			p11FullSoftware = (SoftwareFont) p11Full;
		}
		p12Full = Font.load(Sprites.p12FullId, metricsArchive, fontArchive);
		b12Full = Font.load(Sprites.b12FullId, metricsArchive, fontArchive);
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(BZLclient!na;)V")
	public static void drawTextOnScreen(@OriginalArg(1) boolean swapBuffers, @OriginalArg(2) JagString text) {
		@Pc(24) int maxWidth = p12Full.getMaxLineWidth(text, 250);
		@Pc(31) int textHeight = p12Full.getParagraphLineCount(text, 250) * 13;
		if (GlRenderer.enabled) {
			GlRaster.fillRect(6, 6, maxWidth + 4 + 4, textHeight + 8, 0);
			GlRaster.drawRect(6, 6, maxWidth + 4 + 4, textHeight + 4 + 4, 16777215);
		} else {
			SoftwareRaster.fillRect(6, 6, maxWidth + 4 + 4, textHeight + 8, 0);
			SoftwareRaster.drawRect(6, 6, maxWidth + 8, 4 + 4 + textHeight, 16777215);
		}
		p12Full.drawInterfaceText(text, 10, 10, maxWidth, textHeight, 16777215, -1, 1, 1, 0);
		InterfaceList.redrawScreen(6, maxWidth + 8, 6, textHeight + 4 + 4);
		if (!swapBuffers) {
			InterfaceList.forceRedrawScreen(10, 10, textHeight, maxWidth);
		} else if (GlRenderer.enabled) {
			GlRenderer.swapBuffers();
		} else {
			try {
				@Pc(159) Graphics graphics = GameShell.canvas.getGraphics();
				SoftwareRaster.frameBuffer.draw(graphics);
			} catch (@Pc(167) Exception ex) {
				GameShell.canvas.repaint();
			}
		}
	}
}
