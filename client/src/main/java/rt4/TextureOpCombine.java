package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!pi")
public final class TextureOpCombine extends TextureOp {

	@OriginalMember(owner = "client!pi", name = "Z", descriptor = "I")
	private int function = 6;

	@OriginalMember(owner = "client!pi", name = "<init>", descriptor = "()V")
	public TextureOpCombine() {
		super(2, false);
	}

	@OriginalMember(owner = "client!pi", name = "a", descriptor = "(IB)[I")
	@Override
	public final int[] getMonochromeOutput(@OriginalArg(0) int row) {
		@Pc(11) int[] output = this.monochromeImageCache.get(row);
		if (this.monochromeImageCache.invalid) {
			@Pc(22) int[] inputA = this.getChildMonochromeOutput(0, row);
			@Pc(28) int[] inputB = this.getChildMonochromeOutput(1, row);
			@Pc(31) int func = this.function;
			if (func == 1) {
				for (func = 0; func < Texture.width; func++) {
					output[func] = inputB[func] + inputA[func];
				}
			} else if (func == 2) {
				for (func = 0; func < Texture.width; func++) {
					output[func] = inputA[func] - inputB[func];
				}
			} else if (func == 3) {
				for (func = 0; func < Texture.width; func++) {
					output[func] = inputB[func] * inputA[func] >> 12;
				}
			} else {
				@Pc(180) int b;
				if (func == 4) {
					for (func = 0; func < Texture.width; func++) {
						b = inputB[func];
						output[func] = b == 0 ? 4096 : (inputA[func] << 12) / b;
					}
				} else if (func == 5) {
					for (func = 0; func < Texture.width; func++) {
						output[func] = 4096 - ((4096 - inputA[func]) * (-inputB[func] + 4096) >> 12);
					}
				} else if (func == 6) {
					for (func = 0; func < Texture.width; func++) {
						b = inputB[func];
						output[func] = b >= 2048 ? 4096 - ((4096 - inputA[func]) * (-b + 4096) >> 11) : b * inputA[func] >> 11;
					}
				} else {
					@Pc(295) int a;
					if (func == 7) {
						for (func = 0; func < Texture.width; func++) {
							a = inputA[func];
							output[func] = a == 4096 ? 4096 : (inputB[func] << 12) / (4096 - a);
						}
					} else if (func == 8) {
						for (func = 0; func < Texture.width; func++) {
							a = inputA[func];
							output[func] = a == 0 ? 0 : 4096 - (4096 - inputB[func] << 12) / a;
						}
					} else if (func == 9) {
						for (func = 0; func < Texture.width; func++) {
							b = inputB[func];
							a = inputA[func];
							output[func] = a < b ? a : b;
						}
					} else if (func == 10) {
						for (func = 0; func < Texture.width; func++) {
							b = inputB[func];
							a = inputA[func];
							output[func] = a > b ? a : b;
						}
					} else if (func == 11) {
						for (func = 0; func < Texture.width; func++) {
							a = inputA[func];
							b = inputB[func];
							output[func] = b < a ? a - b : b - a;
						}
					} else if (func == 12) {
						for (func = 0; func < Texture.width; func++) {
							a = inputA[func];
							b = inputB[func];
							output[func] = b + a - (a * b >> 11);
						}
					}
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!pi", name = "b", descriptor = "(II)[[I")
	@Override
	public final int[][] getColorOutput(@OriginalArg(1) int row) {
		@Pc(20) int[][] output = this.colorImageCache.get(row);
		if (this.colorImageCache.invalid) {
			@Pc(30) int[][] inputA = this.getChildColorOutput(row, 0);
			@Pc(36) int[][] inputB = this.getChildColorOutput(row, 1);
			@Pc(40) int[] destR = output[0];
			@Pc(44) int[] destG = output[1];
			@Pc(48) int[] destB = output[2];
			@Pc(52) int[] srcAR = inputA[0];
			@Pc(56) int[] srcAG = inputA[1];
			@Pc(60) int[] srcAB = inputA[2];
			@Pc(64) int[] srcBR = inputB[0];
			@Pc(68) int[] srcBG = inputB[1];
			@Pc(72) int[] srcBB = inputB[2];
			@Pc(75) int func = this.function;
			if (func == 1) {
				for (func = 0; func < Texture.width; func++) {
					destR[func] = srcBR[func] + srcAR[func];
					destG[func] = srcBG[func] + srcAG[func];
					destB[func] = srcAB[func] + srcBB[func];
				}
			} else if (func == 2) {
				for (func = 0; func < Texture.width; func++) {
					destR[func] = srcAR[func] - srcBR[func];
					destG[func] = srcAG[func] - srcBG[func];
					destB[func] = srcAB[func] - srcBB[func];
				}
			} else if (func == 3) {
				for (func = 0; func < Texture.width; func++) {
					destR[func] = srcBR[func] * srcAR[func] >> 12;
					destG[func] = srcAG[func] * srcBG[func] >> 12;
					destB[func] = srcBB[func] * srcAB[func] >> 12;
				}
			} else {
				@Pc(286) int bR;
				@Pc(282) int bG;
				@Pc(278) int bB;
				if (func == 4) {
					for (func = 0; func < Texture.width; func++) {
						bB = srcBB[func];
						bG = srcBG[func];
						bR = srcBR[func];
						destR[func] = bR == 0 ? 4096 : (srcAR[func] << 12) / bR;
						destG[func] = bG == 0 ? 4096 : (srcAG[func] << 12) / bG;
						destB[func] = bB == 0 ? 4096 : (srcAB[func] << 12) / bB;
					}
				} else if (func == 5) {
					for (func = 0; func < Texture.width; func++) {
						destR[func] = 4096 - ((4096 - srcBR[func]) * (4096 - srcAR[func]) >> 12);
						destG[func] = 4096 - ((4096 - srcBG[func]) * (-srcAG[func] + 4096) >> 12);
						destB[func] = 4096 - ((4096 - srcBB[func]) * (-srcAB[func] + 4096) >> 12);
					}
				} else if (func == 6) {
					for (func = 0; func < Texture.width; func++) {
						bB = srcBB[func];
						bR = srcBR[func];
						bG = srcBG[func];
						destR[func] = bR >= 2048 ? 4096 - ((4096 - bR) * (-srcAR[func] + 4096) >> 11) : bR * srcAR[func] >> 11;
						destG[func] = bG < 2048 ? bG * srcAG[func] >> 11 : 4096 - ((4096 - bG) * (-srcAG[func] + 4096) >> 11);
						destB[func] = bB >= 2048 ? 4096 - ((4096 - bB) * (-srcAB[func] + 4096) >> 11) : srcAB[func] * bB >> 11;
					}
				} else {
					@Pc(539) int aR;
					@Pc(543) int aG;
					@Pc(535) int aB;
					if (func == 7) {
						for (func = 0; func < Texture.width; func++) {
							aB = srcAB[func];
							aR = srcAR[func];
							aG = srcAG[func];
							destR[func] = aR == 4096 ? 4096 : (srcBR[func] << 12) / (4096 - aR);
							destG[func] = aG == 4096 ? 4096 : (srcBG[func] << 12) / (4096 - aG);
							destB[func] = aB == 4096 ? 4096 : (srcBB[func] << 12) / (4096 - aB);
						}
					} else if (func == 8) {
						for (func = 0; func < Texture.width; func++) {
							aR = srcAR[func];
							aG = srcAG[func];
							aB = srcAB[func];
							destR[func] = aR == 0 ? 0 : 4096 - (4096 - srcBR[func] << 12) / aR;
							destG[func] = aG == 0 ? 0 : 4096 - (4096 - srcBG[func] << 12) / aG;
							destB[func] = aB == 0 ? 0 : 4096 - (4096 - srcBB[func] << 12) / aB;
						}
					} else if (func == 9) {
						for (func = 0; func < Texture.width; func++) {
							aB = srcAB[func];
							bB = srcBB[func];
							bG = srcBG[func];
							aG = srcAG[func];
							bR = srcBR[func];
							aR = srcAR[func];
							destR[func] = aR >= bR ? bR : aR;
							destG[func] = aG >= bG ? bG : aG;
							destB[func] = aB < bB ? aB : bB;
						}
					} else if (func == 10) {
						for (func = 0; func < Texture.width; func++) {
							bB = srcBB[func];
							aB = srcAB[func];
							bG = srcBG[func];
							aR = srcAR[func];
							aG = srcAG[func];
							bR = srcBR[func];
							destR[func] = bR < aR ? aR : bR;
							destG[func] = aG > bG ? aG : bG;
							destB[func] = bB < aB ? aB : bB;
						}
					} else if (func == 11) {
						for (func = 0; func < Texture.width; func++) {
							bG = srcBG[func];
							bR = srcBR[func];
							aG = srcAG[func];
							aR = srcAR[func];
							aB = srcAB[func];
							bB = srcBB[func];
							destR[func] = aR > bR ? aR - bR : -aR + bR;
							destG[func] = aG > bG ? aG - bG : -aG + bG;
							destB[func] = bB < aB ? aB - bB : -aB + bB;
						}
					} else if (func == 12) {
						for (func = 0; func < Texture.width; func++) {
							aR = srcAR[func];
							bB = srcBB[func];
							bR = srcBR[func];
							bG = srcBG[func];
							aB = srcAB[func];
							aG = srcAG[func];
							destR[func] = bR + aR - (bR * aR >> 11);
							destG[func] = bG + aG - (aG * bG >> 11);
							destB[func] = bB + aB - (aB * bB >> 11);
						}
					}
				}
			}
		}
		return output;
	}

	@OriginalMember(owner = "client!pi", name = "a", descriptor = "(ILclient!wa;Z)V")
	@Override
	public final void decode(@OriginalArg(0) int opcode, @OriginalArg(1) Buffer buf) {
		if (opcode == 0) {
			this.function = buf.g1();
		} else if (opcode == 1) {
			this.monochrome = buf.g1() == 1;
		}
	}
}
