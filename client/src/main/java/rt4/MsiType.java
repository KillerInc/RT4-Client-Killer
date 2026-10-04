package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!aa")
public final class MsiType {

	@OriginalMember(owner = "client!aa", name = "f", descriptor = "I")
	public int tintColor;

	@OriginalMember(owner = "client!aa", name = "i", descriptor = "I")
	public int spriteId;

	@OriginalMember(owner = "client!aa", name = "s", descriptor = "Z")
	public boolean stretchToTile = false;

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(BLclient!wa;I)V")
	public final void decode(@OriginalArg(1) Buffer buf, @OriginalArg(2) int id) {
		while (true) {
			@Pc(5) int opcode = buf.g1();
			if (opcode == 0) {
				return;
			}
			this.decodeOpcode(opcode, buf, id);
		}
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(IB)Lclient!ek;")
	public final SoftwareIndexedSprite getSprite(@OriginalArg(0) int rotation) {
		@Pc(17) SoftwareIndexedSprite sprite = (SoftwareIndexedSprite) MsiTypeList.sprites.get(rotation << 16 | this.spriteId);
		if (sprite != null) {
			return sprite;
		}
		MsiTypeList.spritesArchive.isFileReady(this.spriteId);
		sprite = SpriteLoader.loadSoftwareIndexedSprite(this.spriteId, MsiTypeList.spritesArchive);
		if (sprite != null) {
			sprite.adjustPalette(MsiTypeList.redDelta, MsiTypeList.greenDelta, MsiTypeList.blueDelta);
			sprite.innerWidth = sprite.width;
			sprite.innerHeight = sprite.height;
			for (@Pc(59) int i = 0; i < rotation; i++) {
				sprite.flipVertical();
			}
			MsiTypeList.sprites.put(sprite, rotation << 16 | this.spriteId);
		}
		return sprite;
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(ILclient!wa;II)V")
	private void decodeOpcode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf, @OriginalArg(2) int id) {
		if (opcode == 1) {
			this.spriteId = buf.g2();
		} else if (opcode == 2) {
			this.tintColor = buf.g3();
		} else if (opcode == 3) {
			this.stretchToTile = true;
		} else if (opcode == 4) {
			this.spriteId = -1;
		}
	}
}
