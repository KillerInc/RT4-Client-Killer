package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ng")
public final class RidgedNoiseTexture extends RidgedNoiseGenerator {

	@OriginalMember(owner = "client!ng", name = "O", descriptor = "[B")
	private byte[] textureData;

	@OriginalMember(owner = "client!ng", name = "<init>", descriptor = "()V")
	public RidgedNoiseTexture() {
		super(8, 5, 8, 8, 2, 0.1F, 0.55F, 3.0F);
	}

	@OriginalMember(owner = "client!ng", name = "b", descriptor = "(III)[B")
	public final byte[] generateTexture() {
		this.textureData = new byte[524288];
		this.generate();
		return this.textureData;
	}

	@OriginalMember(owner = "client!ng", name = "a", descriptor = "(IB)V")
	@Override
	protected final void writeSample(@OriginalArg(0) int index, @OriginalArg(1) byte sample) {
		@Pc(3) int offset = index * 2;
		@Pc(7) int value = sample & 0xFF;
		@Pc(10) int even = offset;
		@Pc(11) int odd = offset + 1;
		this.textureData[even] = (byte) (value * 3 >> 5);
		this.textureData[odd] = (byte) (value >> 2);
	}
}
