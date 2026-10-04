package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class FontMetricsList {
	@OriginalMember(owner = "client!l", name = "f", descriptor = "Lclient!n;")
	public static final SoftLruHashTable fontMetrics = new SoftLruHashTable(4);

	@OriginalMember(owner = "client!li", name = "a", descriptor = "(II)Lclient!dd;")
	public static SoftwareFont get(@OriginalArg(1) int id) {
		@Pc(16) SoftwareFont font = (SoftwareFont) fontMetrics.get(id);
		if (font != null) {
			return font;
		}
		@Pc(26) byte[] data = client.js5Archive13.fetchFile(id, 0);
		font = new SoftwareFont(data);
		font.setNameIcons(Sprites.nameIcons, null);
		fontMetrics.put(font, id);
		return font;
	}
}
