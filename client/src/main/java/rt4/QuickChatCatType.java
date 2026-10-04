package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!bc")
public final class QuickChatCatType extends SecondaryNode {

	@OriginalMember(owner = "client!bc", name = "O", descriptor = "[I")
	public int[] phraseShortcuts;

	@OriginalMember(owner = "client!bc", name = "P", descriptor = "[I")
	public int[] subcategories;

	@OriginalMember(owner = "client!bc", name = "T", descriptor = "[I")
	public int[] subcategoryShortcuts;

	@OriginalMember(owner = "client!bc", name = "Y", descriptor = "Lclient!na;")
	public JagString description;

	@OriginalMember(owner = "client!bc", name = "ab", descriptor = "[I")
	public int[] phrases;

	@OriginalMember(owner = "client!si", name = "a", descriptor = "(ZB)I")
	public static int unsignedByte(@OriginalArg(1) byte value) {
		return value & 0xFF;
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(I)V")
	public final void postDecode() {
		@Pc(8) int i;
		if (this.phrases != null) {
			for (i = 0; i < this.phrases.length; i++) {
				this.phrases[i] |= 0x8000;
			}
		}
		if (this.subcategories != null) {
			for (i = 0; i < this.subcategories.length; i++) {
				this.subcategories[i] |= 0x8000;
			}
		}
	}

	@OriginalMember(owner = "client!bc", name = "c", descriptor = "(II)I")
	public final int getPhraseByShortcut(@OriginalArg(0) int shortcut) {
		if (this.phrases == null) {
			return -1;
		}
		for (@Pc(13) int i = 0; i < this.phrases.length; i++) {
			if (shortcut == this.phraseShortcuts[i]) {
				return this.phrases[i];
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!wa;II)V")
	private void decode(@OriginalArg(0) Buffer buffer, @OriginalArg(2) int opcode) {
		if (opcode == 1) {
			this.description = buffer.gjstr();
			return;
		}
		@Pc(28) int count;
		@Pc(38) int i;
		if (opcode == 2) {
			count = buffer.g1();
			this.subcategories = new int[count];
			this.subcategoryShortcuts = new int[count];
			for (i = 0; i < count; i++) {
				this.subcategories[i] = buffer.g2();
				this.subcategoryShortcuts[i] = unsignedByte(buffer.g1b());
			}
		} else if (opcode == 3) {
			count = buffer.g1();
			this.phrases = new int[count];
			this.phraseShortcuts = new int[count];
			for (i = 0; i < count; i++) {
				this.phrases[i] = buffer.g2();
				this.phraseShortcuts[i] = unsignedByte(buffer.g1b());
			}
		} else if (opcode == 4) {
		}
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(BI)I")
	public final int getSubcategoryByShortcut(@OriginalArg(1) int shortcut) {
		if (this.subcategories == null) {
			return -1;
		}
		for (@Pc(21) int i = 0; i < this.subcategories.length; i++) {
			if (this.subcategoryShortcuts[i] == shortcut) {
				return this.subcategories[i];
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!wa;B)V")
	public final void decode(@OriginalArg(0) Buffer buffer) {
		while (true) {
			@Pc(12) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(buffer, opcode);
		}
	}
}
