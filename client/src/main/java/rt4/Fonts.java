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
		KillerUiText.notifyLegacyMetricsReady();
	}

	@OriginalMember(owner = "client!j", name = "a", descriptor = "(BZLclient!na;)V")
	public static void drawTextOnScreen(@OriginalArg(1) boolean swapBuffers, @OriginalArg(2) JagString text) {
		KillerUiLog.once("screen-message-text", "ROUTE screenMessageText=KillerUiText");
		int wrapWidth = KillerUi.px(250);
		int maxWidth = KillerUiText.getMaxLineWidth(Sprites.p12FullId, text, wrapWidth);
		int textHeight = KillerUiText.getParagraphLineCount(Sprites.p12FullId, text, wrapWidth)
			* KillerUiText.lineHeight(KillerUiText.PLAIN_12);
		int outerPad = KillerUi.px(4);

		if (GlRenderer.enabled) {
			GlRaster.fillRect(KillerUi.px(6), KillerUi.px(6), maxWidth + outerPad * 2, textHeight + outerPad * 2, 0);
			GlRaster.drawRect(KillerUi.px(6), KillerUi.px(6), maxWidth + outerPad * 2, textHeight + outerPad * 2, 16777215);
		} else {
			SoftwareRaster.fillRect(KillerUi.px(6), KillerUi.px(6), maxWidth + outerPad * 2, textHeight + outerPad * 2, 0);
			SoftwareRaster.drawRect(KillerUi.px(6), KillerUi.px(6), maxWidth + outerPad * 2, textHeight + outerPad * 2, 16777215);
		}

		KillerUiText.draw(
			text,
			KillerUiText.PLAIN_12,
			KillerUi.px(10),
			KillerUi.px(10),
			Math.max(1, maxWidth),
			Math.max(1, textHeight),
			16777215,
			-1,
			256,
			1,
			1,
			0,
			KillerUiText.EFFECT_NONE
		);

		InterfaceList.redrawScreen(KillerUi.px(6), maxWidth + outerPad * 2, KillerUi.px(6), textHeight + outerPad * 2);
		if (!swapBuffers) {
			InterfaceList.forceRedrawScreen(KillerUi.px(10), KillerUi.px(10), textHeight, maxWidth);
		} else if (GlRenderer.enabled) {
			GlRenderer.swapBuffers();
		} else {
			try {
				Graphics graphics = GameShell.canvas.getGraphics();
				SoftwareRaster.frameBuffer.draw(graphics);
			} catch (Exception ex) {
				GameShell.canvas.repaint();
			}
		}
	}
}
