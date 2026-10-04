package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!kj")
public final class PcmSound extends Sound {

	@OriginalMember(owner = "client!kj", name = "r", descriptor = "Z")
	public boolean pingPongLoop;

	@OriginalMember(owner = "client!kj", name = "u", descriptor = "I")
	public int rate;

	@OriginalMember(owner = "client!kj", name = "q", descriptor = "[B")
	public byte[] samples;

	@OriginalMember(owner = "client!kj", name = "t", descriptor = "I")
	public int start;

	@OriginalMember(owner = "client!kj", name = "s", descriptor = "I")
	public int end;

	@OriginalMember(owner = "client!kj", name = "<init>", descriptor = "(I[BII)V")
	public PcmSound(@OriginalArg(0) int rate, @OriginalArg(1) byte[] samples, @OriginalArg(2) int start, @OriginalArg(3) int end) {
		this.rate = rate;
		this.samples = samples;
		this.start = start;
		this.end = end;
	}

	@OriginalMember(owner = "client!kj", name = "<init>", descriptor = "(I[BIIZ)V")
	public PcmSound(@OriginalArg(0) int rate, @OriginalArg(1) byte[] samples, @OriginalArg(2) int start, @OriginalArg(3) int end, @OriginalArg(4) boolean pingPongLoop) {
		this.rate = rate;
		this.samples = samples;
		this.start = start;
		this.end = end;
		this.pingPongLoop = pingPongLoop;
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(Lclient!vj;)Lclient!kj;")
	public final PcmSound resample(@OriginalArg(0) PcmResampler resampler) {
		this.samples = resampler.resample(this.samples);
		this.rate = resampler.scaleRate(this.rate);
		if (this.start == this.end) {
			this.start = this.end = resampler.scalePosition(this.start);
		} else {
			this.start = resampler.scalePosition(this.start);
			this.end = resampler.scalePosition(this.end);
			if (this.start == this.end) {
				this.start--;
			}
		}
		return this;
	}
}
