package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ic")
public final class LightType {

	@OriginalMember(owner = "client!ic", name = "g", descriptor = "I")
	public int alphaMin = 2048;

	@OriginalMember(owner = "client!ic", name = "c", descriptor = "I")
	public int flickerType = 0;

	@OriginalMember(owner = "client!ic", name = "o", descriptor = "I")
	public int alphaMax = 0;

	@OriginalMember(owner = "client!ic", name = "p", descriptor = "I")
	public int flickerSpeed = 2048;

	@OriginalMember(owner = "client!ic", name = "a", descriptor = "(ILclient!wa;I)V")
	public final void decode(@OriginalArg(1) Buffer buf, @OriginalArg(2) int id) {
		while (true) {
			@Pc(5) int opcode = buf.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(opcode, buf, id);
		}
	}

	@OriginalMember(owner = "client!ic", name = "a", descriptor = "(ILclient!wa;IZ)V")
	private void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf, @OriginalArg(2) int id) {
		if (opcode == 1) {
			this.flickerType = buf.g1();
		} else if (opcode == 2) {
			this.flickerSpeed = buf.g2();
		} else if (opcode == 3) {
			this.alphaMin = buf.g2();
		} else if (opcode == 4) {
			this.alphaMax = buf.g2b();
		}
	}
}
