package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class WordPack {
	@OriginalMember(owner = "client!vl", name = "f", descriptor = "Lclient!na;")
	public static final JagString CABBAGE = JagString.parse("Cabbage");
	@OriginalMember(owner = "client!f", name = "W", descriptor = "Lclient!fi;")
	public static HuffmanCodec codec;

	@OriginalMember(owner = "client!a", name = "a", descriptor = "(Lclient!fi;I)V")
	public static void init(@OriginalArg(0) HuffmanCodec huffmanCodec) {
		codec = huffmanCodec;
	}

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "(ZLclient!wa;Lclient!na;)I")
	public static int encode(@OriginalArg(1) Buffer buffer, @OriginalArg(2) JagString string) {
		@Pc(6) int startOffset = buffer.offset;
		@Pc(14) byte[] bytes = string.toByteArray();
		buffer.psmarts(bytes.length);
		buffer.offset += codec.encode(bytes.length, buffer.data, bytes, 0, buffer.offset);
		return buffer.offset - startOffset;
	}
}
