package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!of")
public interface QuickChatCommandDecoder {

	@OriginalMember(owner = "client!of", name = "a", descriptor = "(I[IIJ)Lclient!na;")
	JagString decode(@OriginalArg(0) int commandId, @OriginalArg(1) int[] args, @OriginalArg(3) long value);
}
