package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class Bzip2Decompressor {

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "Lclient!bb;")
	private static final Bzip2DState state = new Bzip2DState();

	@OriginalMember(owner = "client!s", name = "a", descriptor = "[I")
	public static int[] tt;

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(ILclient!bb;)I")
	private static int getBits(@OriginalArg(0) int nBits, @OriginalArg(1) Bzip2DState state) {
		while (state.bsLive < nBits) {
			state.bsBuff = state.bsBuff << 8 | state.strmNextIn[state.strmNextInPtr] & 0xFF;
			state.bsLive += 8;
			state.strmNextInPtr++;
			state.strmTotalInLo32++;
			if (state.strmTotalInLo32 == 0) {
			}
		}
		@Pc(17) int result = state.bsBuff >> state.bsLive - nBits & (0x1 << nBits) - 1;
		state.bsLive -= nBits;
		return result;
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(Lclient!bb;)V")
	private static void unRleObufToOutputFast(@OriginalArg(0) Bzip2DState state) {
		@Pc(2) byte ch = state.stateOutCh;
		@Pc(5) int len = state.stateOutLen;
		@Pc(8) int nblockused = state.nblockused;
		@Pc(11) int k0 = state.k0;
		@Pc(13) int[] ttArr = tt;
		@Pc(16) int tPos = state.tPos;
		@Pc(19) byte[] outBuf = state.strmNextOut;
		@Pc(22) int outPtr = state.strmNextOutPtr;
		@Pc(25) int availOut = state.strmAvailOut;
		@Pc(27) int origAvailOut = availOut;
		@Pc(32) int nblockMax = state.saveNblock + 1;
		returnNotr:
		while (true) {
			if (len > 0) {
				while (true) {
					if (availOut == 0) {
						break returnNotr;
					}
					if (len == 1) {
						if (availOut == 0) {
							len = 1;
							break returnNotr;
						}
						outBuf[outPtr] = ch;
						outPtr++;
						availOut--;
						break;
					}
					outBuf[outPtr] = ch;
					len--;
					outPtr++;
					availOut--;
				}
			}
			@Pc(62) boolean continueLoop = true;
			@Pc(84) byte nextByte;
			while (continueLoop) {
				continueLoop = false;
				if (nblockused == nblockMax) {
					len = 0;
					break returnNotr;
				}
				ch = (byte) k0;
				tPos = ttArr[tPos];
				nextByte = (byte) (tPos & 0xFF);
				tPos >>= 0x8;
				nblockused++;
				if (nextByte != k0) {
					k0 = nextByte;
					if (availOut == 0) {
						len = 1;
						break returnNotr;
					}
					outBuf[outPtr] = ch;
					outPtr++;
					availOut--;
					continueLoop = true;
				} else if (nblockused == nblockMax) {
					if (availOut == 0) {
						len = 1;
						break returnNotr;
					}
					outBuf[outPtr] = ch;
					outPtr++;
					availOut--;
					continueLoop = true;
				}
			}
			len = 2;
			tPos = ttArr[tPos];
			nextByte = (byte) (tPos & 0xFF);
			tPos >>= 0x8;
			nblockused++;
			if (nblockused != nblockMax) {
				if (nextByte == k0) {
					len = 3;
					tPos = ttArr[tPos];
					nextByte = (byte) (tPos & 0xFF);
					tPos >>= 0x8;
					nblockused++;
					if (nblockused != nblockMax) {
						if (nextByte == k0) {
							tPos = ttArr[tPos];
							nextByte = (byte) (tPos & 0xFF);
							tPos >>= 0x8;
							nblockused++;
							len = (nextByte & 0xFF) + 4;
							tPos = ttArr[tPos];
							k0 = (byte) (tPos & 0xFF);
							tPos >>= 0x8;
							nblockused++;
						} else {
							k0 = nextByte;
						}
					}
				} else {
					k0 = nextByte;
				}
			}
		}
		@Pc(215) int prevTotalOut = state.strmTotalOutLo32;
		state.strmTotalOutLo32 += origAvailOut - availOut;
		if (state.strmTotalOutLo32 < prevTotalOut) {
		}
		state.stateOutCh = ch;
		state.stateOutLen = len;
		state.nblockused = nblockused;
		state.k0 = k0;
		tt = ttArr;
		state.tPos = tPos;
		state.strmNextOut = outBuf;
		state.strmNextOutPtr = outPtr;
		state.strmAvailOut = availOut;
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "([I[I[I[BIII)V")
	private static void hbCreateDecodeTables(@OriginalArg(0) int[] limit, @OriginalArg(1) int[] base, @OriginalArg(2) int[] perm, @OriginalArg(3) byte[] len, @OriginalArg(4) int minLen, @OriginalArg(5) int maxLen, @OriginalArg(6) int alphaSize) {
		@Pc(1) int pp = 0;
		@Pc(3) int i;
		for (i = minLen; i <= maxLen; i++) {
			for (@Pc(8) int j = 0; j < alphaSize; j++) {
				if (len[j] == i) {
					perm[pp] = j;
					pp++;
				}
			}
		}
		for (i = 0; i < 23; i++) {
			base[i] = 0;
		}
		for (i = 0; i < alphaSize; i++) {
			base[len[i] + 1]++;
		}
		for (i = 1; i < 23; i++) {
			base[i] += base[i - 1];
		}
		for (i = 0; i < 23; i++) {
			limit[i] = 0;
		}
		@Pc(85) int vec = 0;
		for (i = minLen; i <= maxLen; i++) {
			vec += base[i + 1] - base[i];
			limit[i] = vec - 1;
			vec <<= 0x1;
		}
		for (i = minLen + 1; i <= maxLen; i++) {
			base[i] = (limit[i - 1] + 1 << 1) - base[i];
		}
	}

	@OriginalMember(owner = "client!oc", name = "b", descriptor = "(Lclient!bb;)V")
	private static void makeMapsD(@OriginalArg(0) Bzip2DState state) {
		state.nInUse = 0;
		for (@Pc(4) int i = 0; i < 256; i++) {
			if (state.inUse[i]) {
				state.seqToUnseq[state.nInUse] = (byte) i;
				state.nInUse++;
			}
		}
	}

	@OriginalMember(owner = "client!oc", name = "c", descriptor = "(Lclient!bb;)B")
	private static byte getUchar(@OriginalArg(0) Bzip2DState state) {
		return (byte) getBits(8, state);
	}

	@OriginalMember(owner = "client!oc", name = "d", descriptor = "(Lclient!bb;)V")
	private static void decompress(@OriginalArg(0) Bzip2DState state) {
		state.blockSize100k = 1;
		if (tt == null) {
			tt = new int[state.blockSize100k * 100000];
		}
		@Pc(56) boolean newBlock = true;
		while (true) {
			while (newBlock) {
				@Pc(61) byte uc = getUchar(state);
				if (uc == 23) {
					return;
				}
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getUchar(state);
				uc = getBit(state);
				if (uc != 0) {
				}
				state.origPtr = 0;
				uc = getUchar(state);
				state.origPtr = state.origPtr << 8 | uc & 0xFF;
				uc = getUchar(state);
				state.origPtr = state.origPtr << 8 | uc & 0xFF;
				uc = getUchar(state);
				state.origPtr = state.origPtr << 8 | uc & 0xFF;
				@Pc(141) int i;
				for (i = 0; i < 16; i++) {
					uc = getBit(state);
					state.inUse16[i] = uc == 1;
				}
				for (i = 0; i < 256; i++) {
					state.inUse[i] = false;
				}
				@Pc(187) int j;
				for (i = 0; i < 16; i++) {
					if (state.inUse16[i]) {
						for (j = 0; j < 16; j++) {
							uc = getBit(state);
							if (uc == 1) {
								state.inUse[i * 16 + j] = true;
							}
						}
					}
				}
				makeMapsD(state);
				@Pc(216) int alphaSize = state.nInUse + 2;
				@Pc(220) int nGroups = getBits(3, state);
				@Pc(224) int nSelectors = getBits(15, state);
				for (i = 0; i < nSelectors; i++) {
					j = 0;
					while (true) {
						uc = getBit(state);
						if (uc == 0) {
							state.selectorMtf[i] = (byte) j;
							break;
						}
						j++;
					}
				}
				@Pc(250) byte[] pos = new byte[6];
				@Pc(252) byte v = 0;
				while (v < nGroups) {
					pos[v] = v++;
				}
				for (i = 0; i < nSelectors; i++) {
					v = state.selectorMtf[i];
					@Pc(279) byte tmp = pos[v];
					while (v > 0) {
						pos[v] = pos[v - 1];
						v--;
					}
					pos[0] = tmp;
					state.selector[i] = tmp;
				}
				@Pc(308) int t;
				for (t = 0; t < nGroups; t++) {
					@Pc(315) int curr = getBits(5, state);
					for (i = 0; i < alphaSize; i++) {
						while (true) {
							uc = getBit(state);
							if (uc == 0) {
								state.len[t][i] = (byte) curr;
								break;
							}
							uc = getBit(state);
							if (uc == 0) {
								curr++;
							} else {
								curr--;
							}
						}
					}
				}
				for (t = 0; t < nGroups; t++) {
					@Pc(354) byte minLen = 32;
					@Pc(356) byte maxLen = 0;
					for (i = 0; i < alphaSize; i++) {
						if (state.len[t][i] > maxLen) {
							maxLen = state.len[t][i];
						}
						if (state.len[t][i] < minLen) {
							minLen = state.len[t][i];
						}
					}
					hbCreateDecodeTables(state.limit[t], state.base[t], state.perm[t], state.len[t], minLen, maxLen, alphaSize);
					state.minLens[t] = minLen;
				}
				@Pc(425) int eob = state.nInUse + 1;
				@Pc(427) byte initGroupNo = -1;
				for (i = 0; i <= 255; i++) {
					state.unfztab[i] = 0;
				}
				@Pc(443) int kk = 4095;
				@Pc(445) int ii;
				@Pc(449) int jj;
				for (ii = 15; ii >= 0; ii--) {
					for (jj = 15; jj >= 0; jj--) {
						state.mfta[kk] = (byte) (ii * 16 + jj);
						kk--;
					}
					state.mtfbase[ii] = kk + 1;
				}
				@Pc(475) int nblock = 0;
				@Pc(478) int groupNo = initGroupNo + 1;
				@Pc(480) byte groupSize = 50;
				@Pc(485) byte gSel = state.selector[0];
				@Pc(490) int gMinlen = state.minLens[gSel];
				@Pc(495) int[] gLimit = state.limit[gSel];
				@Pc(500) int[] gPerm = state.perm[gSel];
				@Pc(505) int[] gBase = state.base[gSel];
				@Pc(506) int groupPos = groupSize - 1;
				@Pc(508) int zn = gMinlen;
				@Pc(512) int zvec;
				@Pc(522) byte zj;
				for (zvec = getBits(gMinlen, state); zvec > gLimit[zn]; zvec = zvec << 1 | zj) {
					zn++;
					zj = getBit(state);
				}
				@Pc(537) int nextSym = gPerm[zvec - gBase[zn]];
				while (true) {
					while (nextSym != eob) {
						if (nextSym == 0 || nextSym == 1) {
							@Pc(548) int es = -1;
							@Pc(550) int N = 1;
							do {
								if (nextSym == 0) {
									es += N;
								} else if (nextSym == 1) {
									es += N * 2;
								}
								N *= 2;
								if (groupPos == 0) {
									groupNo++;
									groupPos = 50;
									gSel = state.selector[groupNo];
									gMinlen = state.minLens[gSel];
									gLimit = state.limit[gSel];
									gPerm = state.perm[gSel];
									gBase = state.base[gSel];
								}
								groupPos--;
								zn = gMinlen;
								for (zvec = getBits(gMinlen, state); zvec > gLimit[zn]; zvec = zvec << 1 | zj) {
									zn++;
									zj = getBit(state);
								}
								nextSym = gPerm[zvec - gBase[zn]];
							} while (nextSym == 0 || nextSym == 1);
							es++;
							uc = state.seqToUnseq[state.mfta[state.mtfbase[0]] & 0xFF];
							state.unfztab[uc & 0xFF] += es;
							while (es > 0) {
								tt[nblock] = uc & 0xFF;
								nblock++;
								es--;
							}
						} else {
							@Pc(678) int nn = nextSym - 1;
							@Pc(686) int pp;
							if (nn < 16) {
								pp = state.mtfbase[0];
								uc = state.mfta[pp + nn];
								while (nn > 3) {
									@Pc(700) int z = pp + nn;
									state.mfta[z] = state.mfta[z - 1];
									state.mfta[z - 1] = state.mfta[z - 2];
									state.mfta[z - 2] = state.mfta[z - 3];
									state.mfta[z - 3] = state.mfta[z - 4];
									nn -= 4;
								}
								while (nn > 0) {
									state.mfta[pp + nn] = state.mfta[pp + nn - 1];
									nn--;
								}
								state.mfta[pp] = uc;
							} else {
								@Pc(776) int lno = nn / 16;
								@Pc(780) int off = nn % 16;
								pp = state.mtfbase[lno] + off;
								uc = state.mfta[pp];
								while (pp > state.mtfbase[lno]) {
									state.mfta[pp] = state.mfta[pp - 1];
									pp--;
								}
								@Pc(815) int unused = state.mtfbase[lno]++;
								while (lno > 0) {
									unused = state.mtfbase[lno]--;
									state.mfta[state.mtfbase[lno]] = state.mfta[state.mtfbase[lno - 1] + 16 - 1];
									lno--;
								}
								unused = state.mtfbase[0]--;
								state.mfta[state.mtfbase[0]] = uc;
								if (state.mtfbase[0] == 0) {
									kk = 4095;
									for (ii = 15; ii >= 0; ii--) {
										for (jj = 15; jj >= 0; jj--) {
											state.mfta[kk] = state.mfta[state.mtfbase[ii] + jj];
											kk--;
										}
										state.mtfbase[ii] = kk + 1;
									}
								}
							}
							state.unfztab[state.seqToUnseq[uc & 0xFF] & 0xFF]++;
							tt[nblock] = state.seqToUnseq[uc & 0xFF] & 0xFF;
							nblock++;
							if (groupPos == 0) {
								groupNo++;
								groupPos = 50;
								gSel = state.selector[groupNo];
								gMinlen = state.minLens[gSel];
								gLimit = state.limit[gSel];
								gPerm = state.perm[gSel];
								gBase = state.base[gSel];
							}
							groupPos--;
							zn = gMinlen;
							for (zvec = getBits(gMinlen, state); zvec > gLimit[zn]; zvec = zvec << 1 | zj) {
								zn++;
								zj = getBit(state);
							}
							nextSym = gPerm[zvec - gBase[zn]];
						}
					}
					state.stateOutLen = 0;
					state.stateOutCh = 0;
					state.cftab[0] = 0;
					for (i = 1; i <= 256; i++) {
						state.cftab[i] = state.unfztab[i - 1];
					}
					for (i = 1; i <= 256; i++) {
						state.cftab[i] += state.cftab[i - 1];
					}
					for (i = 0; i < nblock; i++) {
						uc = (byte) (tt[i] & 0xFF);
						tt[state.cftab[uc & 0xFF]] |= i << 8;
						state.cftab[uc & 0xFF]++;
					}
					state.tPos = tt[state.origPtr] >> 8;
					state.nblockused = 0;
					state.tPos = tt[state.tPos];
					state.k0 = (byte) (state.tPos & 0xFF);
					state.tPos >>= 0x8;
					state.nblockused++;
					state.saveNblock = nblock;
					unRleObufToOutputFast(state);
					if (state.nblockused == state.saveNblock + 1 && state.stateOutLen == 0) {
						newBlock = true;
						break;
					}
					newBlock = false;
					break;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!oc", name = "e", descriptor = "(Lclient!bb;)B")
	private static byte getBit(@OriginalArg(0) Bzip2DState state) {
		return (byte) getBits(1, state);
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "([BI[BII)I")
	public static int bunzip2(@OriginalArg(0) byte[] outBuf, @OriginalArg(1) int outLen, @OriginalArg(2) byte[] inBuf, @OriginalArg(3) int inLen) {
		@Pc(2) Bzip2DState sync = state;
		synchronized (state) {
			state.strmNextIn = inBuf;
			state.strmNextInPtr = 9;
			state.strmNextOut = outBuf;
			state.strmNextOutPtr = 0;
			state.strmAvailOut = outLen;
			state.bsLive = 0;
			state.bsBuff = 0;
			state.strmTotalInLo32 = 0;
			state.strmTotalOutLo32 = 0;
			decompress(state);
			@Pc(37) int bytesWritten = outLen - state.strmAvailOut;
			state.strmNextIn = null;
			state.strmNextOut = null;
			return bytesWritten;
		}
	}
}
