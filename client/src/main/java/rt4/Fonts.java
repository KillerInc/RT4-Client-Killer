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
		KillerTargetFontGenerator.GeneratedFont p11Generated =
			KillerTargetFontGenerator.generate(
				KillerTargetFontGenerator.PLAIN_11,
				11,
				"p11_full"
			);
		KillerTargetFontGenerator.GeneratedFont p12Generated =
			KillerTargetFontGenerator.generate(
				KillerTargetFontGenerator.PLAIN_12,
				12,
				"p12_full"
			);
		KillerTargetFontGenerator.GeneratedFont b12Generated =
			KillerTargetFontGenerator.generate(
				KillerTargetFontGenerator.BOLD_12,
				12,
				"b12_full"
			);

		if (p11Generated != null) {
			p11Full = GlRenderer.enabled
				? new GlFont(
					p11Generated.metrics,
					p11Generated.xOffsets,
					p11Generated.yOffsets,
					p11Generated.innerWidths,
					p11Generated.innerHeights,
					p11Generated.pixels
				)
				: new SoftwareFont(
					p11Generated.metrics,
					p11Generated.xOffsets,
					p11Generated.yOffsets,
					p11Generated.innerWidths,
					p11Generated.innerHeights,
					p11Generated.pixels
				);
			p11FullSoftware = new SoftwareFont(
				p11Generated.metrics,
				p11Generated.xOffsets,
				p11Generated.yOffsets,
				p11Generated.innerWidths,
				p11Generated.innerHeights,
				p11Generated.pixels
			);
			KillerTargetFontGenerator.logInstall(
				"SUCCESS",
				"p11_full install=GENERATED_P" + p11Generated.targetSize
					+ " sourceRaster=" + p11Generated.rasterSize
					+ " renderer=RT4_STOCK parserEffects=RT4_STOCK"
			);
		} else {
			p11Full = Font.load(Sprites.p11FullId, metricsArchive, fontArchive);
			if (GlRenderer.enabled) {
				p11FullSoftware = SoftwareFont.load(Sprites.p11FullId, fontArchive, metricsArchive);
			} else {
				p11FullSoftware = (SoftwareFont) p11Full;
			}
			KillerTargetFontGenerator.logInstall(
				"FALLBACK",
				"p11_full install=CACHE_P11"
			);
		}

		if (p12Generated != null) {
			p12Full = GlRenderer.enabled
				? new GlFont(
					p12Generated.metrics,
					p12Generated.xOffsets,
					p12Generated.yOffsets,
					p12Generated.innerWidths,
					p12Generated.innerHeights,
					p12Generated.pixels
				)
				: new SoftwareFont(
					p12Generated.metrics,
					p12Generated.xOffsets,
					p12Generated.yOffsets,
					p12Generated.innerWidths,
					p12Generated.innerHeights,
					p12Generated.pixels
				);
			KillerTargetFontGenerator.logInstall(
				"SUCCESS",
				"p12_full install=GENERATED_P" + p12Generated.targetSize
					+ " sourceRaster=" + p12Generated.rasterSize
					+ " renderer=RT4_STOCK parserEffects=RT4_STOCK"
			);
		} else {
			p12Full = Font.load(Sprites.p12FullId, metricsArchive, fontArchive);
			KillerTargetFontGenerator.logInstall(
				"FALLBACK",
				"p12_full install=CACHE_P12"
			);
		}

		if (b12Generated != null) {
			b12Full = GlRenderer.enabled
				? new GlFont(
					b12Generated.metrics,
					b12Generated.xOffsets,
					b12Generated.yOffsets,
					b12Generated.innerWidths,
					b12Generated.innerHeights,
					b12Generated.pixels
				)
				: new SoftwareFont(
					b12Generated.metrics,
					b12Generated.xOffsets,
					b12Generated.yOffsets,
					b12Generated.innerWidths,
					b12Generated.innerHeights,
					b12Generated.pixels
				);
			KillerTargetFontGenerator.logInstall(
				"SUCCESS",
				"b12_full install=GENERATED_P" + b12Generated.targetSize
					+ " sourceRaster=" + b12Generated.rasterSize
					+ " renderer=RT4_STOCK parserEffects=RT4_STOCK"
			);
		} else {
			b12Full = Font.load(Sprites.b12FullId, metricsArchive, fontArchive);
			KillerTargetFontGenerator.logInstall(
				"FALLBACK",
				"b12_full install=CACHE_B12"
			);
		}
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
