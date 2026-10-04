package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!cb")
public final class QuickChatPhraseType extends SecondaryNode {

	@OriginalMember(owner = "client!ld", name = "a", descriptor = "[I")
	public static final int[] DYNAMIC_COMMAND_ENCODE_BYTES = new int[]{2, 2, 4, 0, 1, 8, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0};
	@OriginalMember(owner = "client!rc", name = "I", descriptor = "Lclient!na;")
	public static final JagString DYNAMIC_VALUE_PLACEHOLDER = JagString.parse(")3)3)3");
	@OriginalMember(owner = "client!bf", name = "C", descriptor = "[I")
	public static final int[] DYNAMIC_COMMAND_DECODE_BYTES = new int[]{2, 2, 4, 2, 1, 8, 4, 1, 4, 4, 2, 1, 1, 1, 4, 1};
	@OriginalMember(owner = "client!qg", name = "U", descriptor = "Lclient!na;")
	public static final JagString EMPTY_TEXT = JagString.parse("");
	@OriginalMember(owner = "client!vh", name = "p", descriptor = "[I")
	public static final int[] DYNAMIC_COMMAND_PARAM_COUNTS = new int[]{1, 0, 0, 0, 1, 0, 2, 1, 1, 1, 0, 2, 0, 0, 1, 0};
	@OriginalMember(owner = "client!cb", name = "L", descriptor = "[I")
	private int[] dynamicCommandTypes;

	@OriginalMember(owner = "client!cb", name = "M", descriptor = "[I")
	public int[] automaticResponses;

	@OriginalMember(owner = "client!cb", name = "U", descriptor = "[[I")
	private int[][] dynamicCommandParams;

	@OriginalMember(owner = "client!cb", name = "V", descriptor = "[Lclient!na;")
	private JagString[] textSegments;

	@OriginalMember(owner = "client!cb", name = "O", descriptor = "Z")
	public boolean searchable = true;

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(ILclient!wa;)Lclient!bd;")
	public static QuickChatPhrase decodePhrase(@OriginalArg(1) Buffer buf) {
		@Pc(3) QuickChatPhrase phrase = new QuickChatPhrase();
		phrase.id = buf.g2();
		phrase.type = QuickChatPhraseTypeList.get(phrase.id);
		return phrase;
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(Lclient!wa;[IZ)V")
	public final void encodeMessage(@OriginalArg(0) Buffer buf, @OriginalArg(1) int[] values) {
		if (this.dynamicCommandTypes == null) {
			return;
		}
		for (@Pc(14) int i = 0; this.dynamicCommandTypes.length > i && i < values.length; i++) {
			@Pc(38) int byteCount = DYNAMIC_COMMAND_ENCODE_BYTES[this.getDynamicCommand(i)];
			if (byteCount > 0) {
				buf.pVarLong(byteCount, values[i]);
			}
		}
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(Lclient!wa;I)V")
	public final void decode(@OriginalArg(0) Buffer buffer) {
		while (true) {
			@Pc(9) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(buffer, opcode);
		}
	}

	@OriginalMember(owner = "client!cb", name = "e", descriptor = "(I)V")
	public final void postDecode() {
		if (this.automaticResponses != null) {
			for (@Pc(7) int i = 0; i < this.automaticResponses.length; i++) {
				this.automaticResponses[i] |= 0x8000;
			}
		}
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(III)I")
	public final int getDynamicCommandParam(@OriginalArg(1) int paramIndex, @OriginalArg(2) int commandIndex) {
		if (this.dynamicCommandTypes == null || commandIndex < 0 || commandIndex > this.dynamicCommandTypes.length) {
			return -1;
		} else if (this.dynamicCommandParams[commandIndex] == null || paramIndex < 0 || paramIndex > this.dynamicCommandParams[commandIndex].length) {
			return -1;
		} else {
			return this.dynamicCommandParams[commandIndex][paramIndex];
		}
	}

	@OriginalMember(owner = "client!cb", name = "c", descriptor = "(II)I")
	public final int getDynamicCommand(@OriginalArg(1) int index) {
		return this.dynamicCommandTypes == null || index < 0 || index > this.dynamicCommandTypes.length ? -1 : this.dynamicCommandTypes[index];
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(Z)I")
	public final int getDynamicCommandCount() {
		return this.dynamicCommandTypes == null ? 0 : this.dynamicCommandTypes.length;
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(Lclient!wa;II)V")
	private void decode(@OriginalArg(0) Buffer buffer, @OriginalArg(1) int opcode) {
		if (opcode == 1) {
			this.textSegments = buffer.gjstr().split(60);
			return;
		}
		@Pc(32) int count;
		@Pc(42) int i;
		if (opcode == 2) {
			count = buffer.g1();
			this.automaticResponses = new int[count];
			for (i = 0; i < count; i++) {
				this.automaticResponses[i] = buffer.g2();
			}
		} else if (opcode == 3) {
			count = buffer.g1();
			this.dynamicCommandTypes = new int[count];
			this.dynamicCommandParams = new int[count][];
			for (i = 0; i < count; i++) {
				@Pc(49) int commandType = buffer.g2();
				this.dynamicCommandTypes[i] = commandType;
				this.dynamicCommandParams[i] = new int[DYNAMIC_COMMAND_PARAM_COUNTS[commandType]];
				for (@Pc(64) int j = 0; j < DYNAMIC_COMMAND_PARAM_COUNTS[commandType]; j++) {
					this.dynamicCommandParams[i][j] = buffer.g2();
				}
			}
		} else if (opcode == 4) {
			this.searchable = false;
		}
	}

	@OriginalMember(owner = "client!cb", name = "f", descriptor = "(I)Lclient!na;")
	public final JagString getText() {
		@Pc(15) JagString result = JagString.allocate(80);
		if (this.textSegments == null) {
			return EMPTY_TEXT;
		}
		result.appendString(this.textSegments[0]);
		for (@Pc(31) int i = 1; i < this.textSegments.length; i++) {
			result.appendString(DYNAMIC_VALUE_PLACEHOLDER);
			result.appendString(this.textSegments[i]);
		}
		return result.compact();
	}

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(ILclient!wa;)Lclient!na;")
	public final JagString decodeMessage(@OriginalArg(1) Buffer buf) {
		@Pc(17) JagString result = JagString.allocate(80);
		if (this.dynamicCommandTypes != null) {
			for (@Pc(22) int i = 0; i < this.dynamicCommandTypes.length; i++) {
				result.appendString(this.textSegments[i]);
				result.appendString(QuickChatPhraseTypeList.formatDynamicValue(this.dynamicCommandParams[i], buf.gVarLong(DYNAMIC_COMMAND_DECODE_BYTES[this.dynamicCommandTypes[i]]), this.dynamicCommandTypes[i]));
			}
		}
		result.appendString(this.textSegments[this.textSegments.length - 1]);
		return result.compact();
	}
}
