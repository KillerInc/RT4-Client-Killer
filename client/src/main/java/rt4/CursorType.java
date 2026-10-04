package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ia")
public final class CursorType {

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "I")
	public int hotSpotY;

	@OriginalMember(owner = "client!ia", name = "c", descriptor = "I")
	public int hotSpotX;

	@OriginalMember(owner = "client!ia", name = "i", descriptor = "I")
	private int spriteId;

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "(B)Lclient!mm;")
	public final SoftwareSprite getSprite() {
		@Pc(7) SoftwareSprite sprite = (SoftwareSprite) CursorTypeList.sprites.get(this.spriteId);
		if (sprite != null) {
			return sprite;
		}
		sprite = SoftwareSprite.loadSoftwareAlphaSprite(CursorTypeList.spritesArchive, this.spriteId);
		if (sprite != null) {
			CursorTypeList.sprites.put(sprite, this.spriteId);
		}
		return sprite;
	}

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "(Lclient!wa;IB)V")
	public final void decode(@OriginalArg(0) Buffer buf, @OriginalArg(1) int id) {
		while (true) {
			@Pc(18) int opcode = buf.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(id, opcode, buf);
		}
	}

	@OriginalMember(owner = "client!ia", name = "a", descriptor = "(IIILclient!wa;)V")
	private void decode(@OriginalArg(1) int id, @OriginalArg(2) int opcode, @OriginalArg(3) Buffer buf) {
		if (opcode == 1) {
			this.spriteId = buf.g2();
		} else if (opcode == 2) {
			this.hotSpotX = buf.g1();
			this.hotSpotY = buf.g1();
		}
	}
}
