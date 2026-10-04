package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.zip.Inflater;

@OriginalClass("client!ha")
public final class GzipDecompressor {

	@OriginalMember(owner = "client!ha", name = "j", descriptor = "Ljava/util/zip/Inflater;")
	private Inflater anInflater1;

	@OriginalMember(owner = "client!ha", name = "<init>", descriptor = "()V")
	public GzipDecompressor() {
		this(-1, 1000000, 1000000);
	}

	@OriginalMember(owner = "client!ha", name = "<init>", descriptor = "(III)V")
	private GzipDecompressor(@OriginalArg(0) int windowBits, @OriginalArg(1) int inputBufferSize, @OriginalArg(2) int outputBufferSize) {
	}

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "([BLclient!wa;Z)V")
	public final void decompress(@OriginalArg(0) byte[] output, @OriginalArg(1) Buffer input) {
		if (input.data[input.offset] != 31 || input.data[input.offset + 1] != -117) {
			throw new RuntimeException("Invalid GZIP header!");
		}
		if (this.anInflater1 == null) {
			this.anInflater1 = new Inflater(true);
		}
		try {
			this.anInflater1.setInput(input.data, input.offset + 10, -8 - (input.offset + 10) + input.data.length);
			this.anInflater1.inflate(output);
		} catch (@Pc(64) Exception ex) {
			this.anInflater1.reset();
			throw new RuntimeException("Invalid GZIP compressed data!");
		}
		this.anInflater1.reset();
	}
}
