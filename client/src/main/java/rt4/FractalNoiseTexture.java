package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!sb")
public final class FractalNoiseTexture extends FractalNoiseGenerator {

	@OriginalMember(owner = "client!sb", name = "I", descriptor = "[B")
	private byte[] textureData;

	@OriginalMember(owner = "client!sb", name = "<init>", descriptor = "()V")
	public FractalNoiseTexture() {
		super(12, 5, 16, 2, 2, 0.45F);
	}

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "(IB)V")
	@Override
	protected final void writeSample(@OriginalArg(0) int index, @OriginalArg(1) byte sample) {
		@Pc(3) int offset = index * 2;
		@Pc(12) byte value = (byte) ((sample >> 1 & 0x7F) + 127);
		@Pc(15) int even = offset;
		@Pc(16) int odd = offset + 1;
		this.textureData[even] = value;
		this.textureData[odd] = value;
	}

	@OriginalMember(owner = "client!sb", name = "b", descriptor = "(III)[B")
	public final byte[] generateTexture() {
		this.textureData = new byte[524288];
		this.generate();
		return this.textureData;
	}
}
