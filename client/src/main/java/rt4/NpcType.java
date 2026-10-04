package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!me")
public final class NpcType {

	@OriginalMember(owner = "client!id", name = "e", descriptor = "[I")
	public static final int[] slotCurrentFrames = new int[14];
	@OriginalMember(owner = "client!lg", name = "c", descriptor = "[Lclient!tk;")
	public static final SeqType[] slotSeqTypes = new SeqType[14];
	@OriginalMember(owner = "client!ag", name = "cb", descriptor = "[Lclient!cl;")
	public static final AnimFrameset[] slotCurrentFramesets = new AnimFrameset[14];
	@OriginalMember(owner = "client!fk", name = "k", descriptor = "[I")
	public static final int[] slotFrameDelays = new int[14];
	@OriginalMember(owner = "client!rg", name = "A", descriptor = "[I")
	public static final int[] slotDelayClocks = new int[14];
	@OriginalMember(owner = "client!kd", name = "mb", descriptor = "[Lclient!cl;")
	public static final AnimFrameset[] slotTweenFramesets = new AnimFrameset[14];
	@OriginalMember(owner = "client!en", name = "t", descriptor = "[I")
	public static final int[] slotTweenFrames = new int[14];
	@OriginalMember(owner = "client!me", name = "b", descriptor = "[S")
	private short[] retex_d;

	@OriginalMember(owner = "client!me", name = "c", descriptor = "[B")
	private byte[] recol_p;

	@OriginalMember(owner = "client!me", name = "d", descriptor = "[S")
	private short[] recol_s;

	@OriginalMember(owner = "client!me", name = "g", descriptor = "[I")
	private int[] headmodels;

	@OriginalMember(owner = "client!me", name = "o", descriptor = "[S")
	private short[] recol_d;

	@OriginalMember(owner = "client!me", name = "t", descriptor = "[[I")
	private int[][] modelTransformCache;

	@OriginalMember(owner = "client!me", name = "z", descriptor = "[[I")
	private int[][] modeloffsets;

	@OriginalMember(owner = "client!me", name = "J", descriptor = "[S")
	private short[] retex_s;

	@OriginalMember(owner = "client!me", name = "K", descriptor = "Lclient!sc;")
	private HashTable params;

	@OriginalMember(owner = "client!me", name = "X", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!me", name = "cb", descriptor = "[I")
	private int[] modelIndices;

	@OriginalMember(owner = "client!me", name = "hb", descriptor = "[I")
	public int[] multiNpcs;

	@OriginalMember(owner = "client!me", name = "A", descriptor = "I")
	public int idleSound = -1;

	@OriginalMember(owner = "client!me", name = "I", descriptor = "Z")
	public boolean interactive = true;

	@OriginalMember(owner = "client!me", name = "a", descriptor = "I")
	public int size = 1;

	@OriginalMember(owner = "client!me", name = "q", descriptor = "Z")
	public boolean rotationflag = true;

	@OriginalMember(owner = "client!me", name = "O", descriptor = "I")
	public int runSound = -1;

	@OriginalMember(owner = "client!me", name = "r", descriptor = "S")
	public short shadowcolor2 = 0;

	@OriginalMember(owner = "client!vk", name = "d", descriptor = "Lclient!na;")
	public static final JagString DEFAULT_NAME = JagString.parse("null");
	@OriginalMember(owner = "client!me", name = "L", descriptor = "Lclient!na;")
	public JagString name = DEFAULT_NAME;

	@OriginalMember(owner = "client!me", name = "x", descriptor = "I")
	public int combatLevel = -1;

	@OriginalMember(owner = "client!me", name = "Z", descriptor = "Z")
	public boolean minimapdisplay = true;

	@OriginalMember(owner = "client!me", name = "l", descriptor = "I")
	public int cursor1 = -1;

	@OriginalMember(owner = "client!me", name = "U", descriptor = "I")
	private int contrast = 0;

	@OriginalMember(owner = "client!me", name = "V", descriptor = "I")
	public int minimapmarkerobjectentry = -1;

	@OriginalMember(owner = "client!me", name = "C", descriptor = "I")
	private int resizeX = 128;

	@OriginalMember(owner = "client!me", name = "N", descriptor = "B")
	public byte shadowcolormodifier2 = -16;

	@OriginalMember(owner = "client!me", name = "H", descriptor = "I")
	public int headicon = -1;

	@OriginalMember(owner = "client!me", name = "F", descriptor = "B")
	public byte loginscreenproperties = 0;

	@OriginalMember(owner = "client!me", name = "e", descriptor = "Z")
	public boolean hasshadow = true;

	@OriginalMember(owner = "client!me", name = "ab", descriptor = "S")
	public short shadowcolor1 = 0;

	@OriginalMember(owner = "client!me", name = "db", descriptor = "I")
	public int cursor2Op = -1;

	@OriginalMember(owner = "client!me", name = "R", descriptor = "I")
	public int hitBarId = -1;

	@OriginalMember(owner = "client!me", name = "h", descriptor = "I")
	private int ambient = 0;

	@OriginalMember(owner = "client!me", name = "B", descriptor = "Z")
	public boolean toprenderpriority = false;

	@OriginalMember(owner = "client!me", name = "M", descriptor = "I")
	public int rotationspeed = 32;

	@OriginalMember(owner = "client!me", name = "u", descriptor = "[Lclient!na;")
	public final JagString[] ops = new JagString[5];

	@OriginalMember(owner = "client!me", name = "ib", descriptor = "I")
	public int walkSound = -1;

	@OriginalMember(owner = "client!me", name = "bb", descriptor = "B")
	public byte shadowcolormodifier1 = -96;

	@OriginalMember(owner = "client!me", name = "G", descriptor = "B")
	public byte spawndirection = 7;

	@OriginalMember(owner = "client!me", name = "S", descriptor = "I")
	public int bastypeid = -1;

	@OriginalMember(owner = "client!me", name = "mb", descriptor = "I")
	public int cursor1Op = -1;

	@OriginalMember(owner = "client!me", name = "gb", descriptor = "I")
	public int soundRadius = 0;

	@OriginalMember(owner = "client!me", name = "E", descriptor = "I")
	private int resizeY = 128;

	@OriginalMember(owner = "client!me", name = "s", descriptor = "I")
	public int multiNpcVarbit = -1;

	@OriginalMember(owner = "client!me", name = "fb", descriptor = "I")
	public int crawlSound = -1;

	@OriginalMember(owner = "client!me", name = "D", descriptor = "I")
	public int iconHeight = -1;

	@OriginalMember(owner = "client!me", name = "Q", descriptor = "I")
	public int cursor2 = -1;

	@OriginalMember(owner = "client!me", name = "lb", descriptor = "I")
	private int multiNpcVarp = -1;

	@OriginalMember(owner = "client!me", name = "ob", descriptor = "I")
	public int attackCursor = -1;

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(B)Lclient!me;")
	public final NpcType getMultiNpc() {
		@Pc(5) int varpValue = -1;
		if (this.multiNpcVarbit != -1) {
			varpValue = VarpDomain.getVarbit(this.multiNpcVarbit);
		} else if (this.multiNpcVarp != -1) {
			varpValue = VarpDomain.activeVarps[this.multiNpcVarp];
		}
		if (varpValue < 0 || varpValue >= this.multiNpcs.length - 1 || this.multiNpcs[varpValue] == -1) {
			@Pc(55) int defaultNpcId = this.multiNpcs[this.multiNpcs.length - 1];
			return defaultNpcId == -1 ? null : NpcTypeList.get(defaultNpcId);
		} else {
			return NpcTypeList.get(this.multiNpcs[varpValue]);
		}
	}

	@OriginalMember(owner = "client!me", name = "b", descriptor = "(B)Z")
	public final boolean isMultiNpcValid() {
		if (this.multiNpcs == null) {
			return true;
		}
		@Pc(16) int varpValue = -1;
		if (this.multiNpcVarbit != -1) {
			varpValue = VarpDomain.getVarbit(this.multiNpcVarbit);
		} else if (this.multiNpcVarp != -1) {
			varpValue = VarpDomain.activeVarps[this.multiNpcVarp];
		}
		if (varpValue < 0 || varpValue >= this.multiNpcs.length - 1 || this.multiNpcs[varpValue] == -1) {
			@Pc(62) int defaultNpcId = this.multiNpcs[this.multiNpcs.length - 1];
			return defaultNpcId != -1;
		} else {
			return true;
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(I)Z")
	public final boolean hasAreaSound() {
		if (this.multiNpcs == null) {
			return this.idleSound != -1 || this.walkSound != -1 || this.runSound != -1;
		}
		for (@Pc(35) int i = 0; i < this.multiNpcs.length; i++) {
			if (this.multiNpcs[i] != -1) {
				@Pc(60) NpcType npcType = NpcTypeList.get(this.multiNpcs[i]);
				if (npcType.idleSound != -1 || npcType.walkSound != -1 || npcType.runSound != -1) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(III)I")
	public final int getParam(@OriginalArg(0) int paramId, @OriginalArg(2) int defaultValue) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(18) IntNode node = (IntNode) this.params.get(paramId);
			return node == null ? defaultValue : node.value;
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "([Lclient!ub;IBIIIILclient!tk;ILclient!tk;)Lclient!ak;")
	public final Model getBodyModel(@OriginalArg(0) SlotAnimation[] seqs, @OriginalArg(1) int tweenFrame1, @OriginalArg(3) int currentFrame1, @OriginalArg(4) int tweenFrame2, @OriginalArg(5) int delayClock2, @OriginalArg(6) int currentFrame2, @OriginalArg(7) SeqType primarySeqType, @OriginalArg(8) int delayClock1, @OriginalArg(9) SeqType secondarySeqType) {
		if (this.multiNpcs != null) {
			@Pc(13) NpcType multiNpc = this.getMultiNpc();
			return multiNpc == null ? null : multiNpc.getBodyModel(seqs, tweenFrame1, currentFrame1, tweenFrame2, delayClock2, currentFrame2, primarySeqType, delayClock1, secondarySeqType);
		}
		@Pc(40) Model model = (Model) NpcTypeList.models.get(this.id);
		@Pc(46) boolean hasSlotAnim;
		@Pc(173) int i;
		@Pc(235) int frame;
		@Pc(221) int tweenFrame;
		@Pc(200) int translateZ;
		@Pc(207) int rotateX;
		@Pc(214) int rotateY;
		@Pc(228) int frameGroupId;
		@Pc(300) int frameDelay;
		@Pc(324) int groupId;
		@Pc(318) int slot;
		@Pc(330) int slotMask;
		if (model == null) {
			hasSlotAnim = false;
			for (@Pc(48) int j = 0; j < this.modelIndices.length; j++) {
				if (this.modelIndices[j] != -1 && !NpcTypeList.modelsArchive.isFileReady(0, this.modelIndices[j])) {
					hasSlotAnim = true;
				}
			}
			if (hasSlotAnim) {
				return null;
			}
			@Pc(84) RawModel[] rawModels = new RawModel[this.modelIndices.length];
			for (@Pc(86) int j = 0; j < this.modelIndices.length; j++) {
				if (this.modelIndices[j] != -1) {
					rawModels[j] = RawModel.create(NpcTypeList.modelsArchive, this.modelIndices[j]);
					if (this.modeloffsets != null && this.modeloffsets[j] != null && rawModels[j] != null) {
						rawModels[j].translate(this.modeloffsets[j][0], this.modeloffsets[j][1], this.modeloffsets[j][2]);
					}
				}
			}
			@Pc(156) BasType basType = null;
			if (this.bastypeid != -1) {
				basType = BasTypeList.get(this.bastypeid);
			}
			if (basType != null && basType.modelRotateTranslate != null) {
				for (i = 0; i < basType.modelRotateTranslate.length; i++) {
					if (basType.modelRotateTranslate[i] != null && rawModels.length > i && rawModels[i] != null) {
						translateZ = basType.modelRotateTranslate[i][2];
						rotateX = basType.modelRotateTranslate[i][3];
						rotateY = basType.modelRotateTranslate[i][4];
						tweenFrame = basType.modelRotateTranslate[i][1];
						frameGroupId = basType.modelRotateTranslate[i][5];
						frame = basType.modelRotateTranslate[i][0];
						if (this.modelTransformCache == null) {
							this.modelTransformCache = new int[basType.modelRotateTranslate.length][];
						}
						if (this.modelTransformCache[i] == null) {
							@Pc(259) int[] matrix = this.modelTransformCache[i] = new int[15];
							if (rotateX == 0 && rotateY == 0 && frameGroupId == 0) {
								matrix[13] = -tweenFrame;
								matrix[14] = -translateZ;
								matrix[0] = matrix[4] = matrix[8] = 32768;
								matrix[12] = -frame;
							} else {
								frameDelay = MathUtils.cos[rotateX] >> 1;
								@Pc(306) int sinRX = MathUtils.sin[rotateX] >> 1;
								@Pc(312) int cosRY = MathUtils.cos[rotateY] >> 1;
								slot = MathUtils.cos[frameGroupId] >> 1;
								groupId = MathUtils.sin[rotateY] >> 1;
								slotMask = MathUtils.sin[frameGroupId] >> 1;
								matrix[3] = frameDelay * slotMask + 16384 >> 15;
								matrix[8] = frameDelay * cosRY + 16384 >> 15;
								matrix[5] = -sinRX;
								@Pc(363) int sinRXsinRZ = sinRX * slotMask + 16384 >> 15;
								@Pc(371) int cosRZsinRX = slot * sinRX + 16384 >> 15;
								matrix[1] = -slotMask * cosRY + cosRZsinRX * groupId + 16384 >> 15;
								matrix[2] = groupId * frameDelay + 16384 >> 15;
								matrix[6] = -groupId * slot + sinRXsinRZ * cosRY + 16384 >> 15;
								matrix[14] = matrix[8] * -translateZ + -tweenFrame * matrix[5] + matrix[2] * -frame + 16384 >> 15;
								matrix[4] = frameDelay * slot + 16384 >> 15;
								matrix[7] = -groupId * -slotMask + cosRZsinRX * cosRY + 16384 >> 15;
								matrix[0] = groupId * sinRXsinRZ + cosRY * slot + 16384 >> 15;
								matrix[12] = matrix[6] * -translateZ + matrix[3] * -tweenFrame + -frame * matrix[0] + 16384 >> 15;
								matrix[13] = -translateZ * matrix[7] + -frame * matrix[1] + -tweenFrame * matrix[4] + 16384 >> 15;
							}
							matrix[10] = tweenFrame;
							matrix[9] = frame;
							matrix[11] = translateZ;
						}
						if (rotateX != 0 || rotateY != 0 || frameGroupId != 0) {
							rawModels[i].rotateXYZ(rotateX, rotateY, frameGroupId);
						}
						if (frame != 0 || tweenFrame != 0 || translateZ != 0) {
							rawModels[i].translate(frame, tweenFrame, translateZ);
						}
					}
				}
			}
			@Pc(593) RawModel rawModel;
			if (rawModels.length == 1) {
				rawModel = rawModels[0];
			} else {
				rawModel = new RawModel(rawModels, rawModels.length);
			}
			if (this.recol_s != null) {
				for (i = 0; i < this.recol_s.length; i++) {
					if (this.recol_p == null || this.recol_p.length <= i) {
						rawModel.recolor(this.recol_s[i], this.recol_d[i]);
					} else {
						rawModel.recolor(this.recol_s[i], client.npcRecolorPalette[this.recol_p[i] & 0xFF]);
					}
				}
			}
			if (this.retex_s != null) {
				for (i = 0; i < this.retex_s.length; i++) {
					rawModel.retexture(this.retex_s[i], this.retex_d[i]);
				}
			}
			model = rawModel.createModel(this.ambient + 64, this.contrast + 850, -30, -50, -30);
			if (GlRenderer.enabled) {
				((GlModel) model).uploadBuffers(false, false, false, false, false, true);
			}
			NpcTypeList.models.put(model, this.id);
		}
		hasSlotAnim = false;
		@Pc(721) boolean hasAlphaTransform = false;
		@Pc(723) boolean hasColorTransform = false;
		@Pc(725) boolean hasModelTransform = false;
		i = seqs == null ? 0 : seqs.length;
		for (frame = 0; frame < i; frame++) {
			if (seqs[frame] != null) {
				@Pc(753) SeqType slotSeqType = SeqTypeList.get(seqs[frame].seqId);
				if (slotSeqType.frames != null) {
					slotSeqTypes[frame] = slotSeqType;
					rotateX = seqs[frame].nextFrame;
					hasSlotAnim = true;
					translateZ = seqs[frame].currentFrame;
					rotateY = slotSeqType.frames[translateZ];
					slotCurrentFramesets[frame] = SeqTypeList.getAnimFrameset(rotateY >>> 16);
					rotateY &= 0xFFFF;
					slotCurrentFrames[frame] = rotateY;
					if (slotCurrentFramesets[frame] != null) {
						hasColorTransform |= slotCurrentFramesets[frame].isColorTransformed(rotateY);
						hasAlphaTransform |= slotCurrentFramesets[frame].isAlphaTransformed(rotateY);
						hasModelTransform |= slotSeqType.hasModelTransforms;
					}
					if ((slotSeqType.tween || SeqType.applyTweening) && rotateX != -1 && slotSeqType.frames.length > rotateX) {
						slotFrameDelays[frame] = slotSeqType.frameDelay[translateZ];
						slotDelayClocks[frame] = seqs[frame].delayClock;
						frameGroupId = slotSeqType.frames[rotateX];
						slotTweenFramesets[frame] = SeqTypeList.getAnimFrameset(frameGroupId >>> 16);
						frameGroupId &= 0xFFFF;
						slotTweenFrames[frame] = frameGroupId;
						if (slotTweenFramesets[frame] != null) {
							hasColorTransform |= slotTweenFramesets[frame].isColorTransformed(frameGroupId);
							hasAlphaTransform |= slotTweenFramesets[frame].isAlphaTransformed(frameGroupId);
						}
					} else {
						slotFrameDelays[frame] = 0;
						slotDelayClocks[frame] = 0;
						slotTweenFramesets[frame] = null;
						slotTweenFrames[frame] = -1;
					}
				}
			}
		}
		if (!hasSlotAnim && secondarySeqType == null && primarySeqType == null) {
			@Pc(933) Model copy = model.copyForEntity(true, true, true);
			if (this.resizeX != 128 || this.resizeY != 128) {
				copy.resize(this.resizeX, this.resizeY, this.resizeX);
			}
			return copy;
		}
		tweenFrame = -1;
		frame = -1;
		translateZ = 0;
		@Pc(962) AnimFrameset secondaryFrameset = null;
		@Pc(964) AnimFrameset secondaryTweenFrameset = null;
		@Pc(1040) int primaryFrame;
		if (secondarySeqType != null) {
			frame = secondarySeqType.frames[currentFrame2];
			frameGroupId = frame >>> 16;
			frame &= 0xFFFF;
			secondaryFrameset = SeqTypeList.getAnimFrameset(frameGroupId);
			if (secondaryFrameset != null) {
				hasColorTransform |= secondaryFrameset.isColorTransformed(frame);
				hasAlphaTransform |= secondaryFrameset.isAlphaTransformed(frame);
				hasModelTransform |= secondarySeqType.hasModelTransforms;
			}
			if ((secondarySeqType.tween || SeqType.applyTweening) && tweenFrame2 != -1 && secondarySeqType.frames.length > tweenFrame2) {
				translateZ = secondarySeqType.frameDelay[currentFrame2];
				tweenFrame = secondarySeqType.frames[tweenFrame2];
				primaryFrame = tweenFrame >>> 16;
				tweenFrame &= 0xFFFF;
				if (frameGroupId == primaryFrame) {
					secondaryTweenFrameset = secondaryFrameset;
				} else {
					secondaryTweenFrameset = SeqTypeList.getAnimFrameset(tweenFrame >>> 16);
				}
				if (secondaryTweenFrameset != null) {
					hasColorTransform |= secondaryTweenFrameset.isColorTransformed(tweenFrame);
					hasAlphaTransform |= secondaryTweenFrameset.isAlphaTransformed(tweenFrame);
				}
			}
		}
		frameGroupId = -1;
		primaryFrame = -1;
		@Pc(1088) AnimFrameset primaryFrameset = null;
		frameDelay = 0;
		@Pc(1092) AnimFrameset primaryTweenFrameset = null;
		if (primarySeqType != null) {
			frameGroupId = primarySeqType.frames[currentFrame1];
			groupId = frameGroupId >>> 16;
			frameGroupId &= 0xFFFF;
			primaryFrameset = SeqTypeList.getAnimFrameset(groupId);
			if (primaryFrameset != null) {
				hasColorTransform |= primaryFrameset.isColorTransformed(frameGroupId);
				hasAlphaTransform |= primaryFrameset.isAlphaTransformed(frameGroupId);
				hasModelTransform |= primarySeqType.hasModelTransforms;
			}
			if ((primarySeqType.tween || SeqType.applyTweening) && tweenFrame1 != -1 && tweenFrame1 < primarySeqType.frames.length) {
				frameDelay = primarySeqType.frameDelay[currentFrame1];
				primaryFrame = primarySeqType.frames[tweenFrame1];
				slot = primaryFrame >>> 16;
				primaryFrame &= 0xFFFF;
				if (slot == groupId) {
					primaryTweenFrameset = primaryFrameset;
				} else {
					primaryTweenFrameset = SeqTypeList.getAnimFrameset(primaryFrame >>> 16);
				}
				if (primaryTweenFrameset != null) {
					hasColorTransform |= primaryTweenFrameset.isColorTransformed(primaryFrame);
					hasAlphaTransform |= primaryTweenFrameset.isAlphaTransformed(primaryFrame);
				}
			}
		}
		@Pc(1218) Model animated = model.copyForEntity(!hasAlphaTransform, !hasColorTransform, !hasModelTransform);
		slotMask = 1;
		for (slot = 0; slot < i; slot++) {
			if (slotCurrentFramesets[slot] != null) {
				animated.applyMaskedAnimation(slotCurrentFramesets[slot], slotCurrentFrames[slot], slotTweenFramesets[slot], slotTweenFrames[slot], slotDelayClocks[slot] - 1, slotFrameDelays[slot], slotMask, slotSeqTypes[slot].hasModelTransforms, this.modelTransformCache[slot]);
			}
			slotMask <<= 0x1;
		}
		if (secondaryFrameset != null && primaryFrameset != null) {
			animated.applyDualAnimation(secondaryFrameset, frame, secondaryTweenFrameset, tweenFrame, delayClock2 - 1, translateZ, primaryFrameset, frameGroupId, primaryTweenFrameset, primaryFrame, delayClock1 - 1, frameDelay, secondarySeqType.framegroup, secondarySeqType.hasModelTransforms | primarySeqType.hasModelTransforms);
		} else if (secondaryFrameset != null) {
			animated.applyAnimation(secondaryFrameset, frame, secondaryTweenFrameset, tweenFrame, delayClock2 - 1, translateZ, secondarySeqType.hasModelTransforms);
		} else if (primaryFrameset != null) {
			animated.applyAnimation(primaryFrameset, frameGroupId, primaryTweenFrameset, primaryFrame, delayClock1 - 1, frameDelay, primarySeqType.hasModelTransforms);
		}
		for (slot = 0; slot < i; slot++) {
			slotCurrentFramesets[slot] = null;
			slotTweenFramesets[slot] = null;
			slotSeqTypes[slot] = null;
		}
		if (this.resizeX != 128 || this.resizeY != 128) {
			animated.resize(this.resizeX, this.resizeY, this.resizeX);
		}
		return animated;
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(ILclient!na;Z)Lclient!na;")
	public final JagString getParam(@OriginalArg(0) int paramId, @OriginalArg(1) JagString defaultValue) {
		if (this.params == null) {
			return defaultValue;
		} else {
			@Pc(18) StringNode node = (StringNode) this.params.get(paramId);
			return node == null ? defaultValue : node.value;
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(Lclient!wa;I)V")
	public final void decode(@OriginalArg(0) Buffer buffer) {
		while (true) {
			@Pc(9) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(opcode, buffer);
		}
	}

	@OriginalMember(owner = "client!me", name = "b", descriptor = "(I)V")
	public final void postDecode() {
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(Lclient!tk;IIII)Lclient!ak;")
	public final Model getHeadModel(@OriginalArg(0) SeqType seqType, @OriginalArg(1) int tweenFrame, @OriginalArg(2) int currentFrame, @OriginalArg(4) int delayClock) {
		if (this.multiNpcs != null) {
			@Pc(13) NpcType multiNpc = this.getMultiNpc();
			return multiNpc == null ? null : multiNpc.getHeadModel(seqType, tweenFrame, currentFrame, delayClock);
		} else if (this.headmodels == null) {
			return null;
		} else {
			@Pc(41) Model model = (Model) NpcTypeList.headModels.get(this.id);
			if (model == null) {
				@Pc(46) boolean notReady = false;
				for (@Pc(48) int i = 0; i < this.headmodels.length; i++) {
					if (!NpcTypeList.modelsArchive.isFileReady(0, this.headmodels[i])) {
						notReady = true;
					}
				}
				if (notReady) {
					return null;
				}
				@Pc(82) RawModel[] rawModels = new RawModel[this.headmodels.length];
				for (@Pc(84) int i = 0; i < this.headmodels.length; i++) {
					rawModels[i] = RawModel.create(NpcTypeList.modelsArchive, this.headmodels[i]);
				}
				@Pc(119) RawModel rawModel;
				if (rawModels.length == 1) {
					rawModel = rawModels[0];
				} else {
					rawModel = new RawModel(rawModels, rawModels.length);
				}
				@Pc(130) int i;
				if (this.recol_s != null) {
					for (i = 0; i < this.recol_s.length; i++) {
						if (this.recol_p == null || i >= this.recol_p.length) {
							rawModel.recolor(this.recol_s[i], this.recol_d[i]);
						} else {
							rawModel.recolor(this.recol_s[i], client.npcRecolorPalette[this.recol_p[i] & 0xFF]);
						}
					}
				}
				if (this.retex_s != null) {
					for (i = 0; i < this.retex_s.length; i++) {
						rawModel.retexture(this.retex_s[i], this.retex_d[i]);
					}
				}
				model = rawModel.createModel(64, 768, -50, -10, -50);
				NpcTypeList.headModels.put(model, this.id);
			}
			if (seqType != null) {
				model = seqType.animateEntity(model, currentFrame, tweenFrame, delayClock);
			}
			return model;
		}
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(IILclient!wa;)V")
	private void decode(@OriginalArg(1) int opcode, @OriginalArg(2) Buffer buffer) {
		@Pc(12) int count;
		@Pc(18) int i;
		if (opcode == 1) {
			count = buffer.g1();
			this.modelIndices = new int[count];
			for (i = 0; i < count; i++) {
				this.modelIndices[i] = buffer.g2();
				if (this.modelIndices[i] == 65535) {
					this.modelIndices[i] = -1;
				}
			}
		} else if (opcode == 2) {
			this.name = buffer.gjstr();
		} else if (opcode == 12) {
			this.size = buffer.g1();
		} else if (opcode >= 30 && opcode < 35) {
			this.ops[opcode - 30] = buffer.gjstr();
			if (this.ops[opcode - 30].equalsIgnoreCase(LocalizedText.HIDDEN)) {
				this.ops[opcode - 30] = null;
			}
		} else if (opcode == 40) {
			count = buffer.g1();
			this.recol_d = new short[count];
			this.recol_s = new short[count];
			for (i = 0; i < count; i++) {
				this.recol_s[i] = (short) buffer.g2();
				this.recol_d[i] = (short) buffer.g2();
			}
		} else if (opcode == 41) {
			count = buffer.g1();
			this.retex_d = new short[count];
			this.retex_s = new short[count];
			for (i = 0; i < count; i++) {
				this.retex_s[i] = (short) buffer.g2();
				this.retex_d[i] = (short) buffer.g2();
			}
		} else if (opcode == 42) {
			count = buffer.g1();
			this.recol_p = new byte[count];
			for (i = 0; i < count; i++) {
				this.recol_p[i] = buffer.g1b();
			}
		} else if (opcode == 60) {
			count = buffer.g1();
			this.headmodels = new int[count];
			for (i = 0; i < count; i++) {
				this.headmodels[i] = buffer.g2();
			}
		} else if (opcode == 93) {
			this.minimapdisplay = false;
		} else if (opcode == 95) {
			this.combatLevel = buffer.g2();
		} else if (opcode == 97) {
			this.resizeX = buffer.g2();
		} else if (opcode == 98) {
			this.resizeY = buffer.g2();
		} else if (opcode == 99) {
			this.toprenderpriority = true;
		} else if (opcode == 100) {
			this.ambient = buffer.g1b();
		} else if (opcode == 101) {
			this.contrast = buffer.g1b() * 5;
		} else if (opcode == 102) {
			this.headicon = buffer.g2();
		} else if (opcode == 103) {
			this.rotationspeed = buffer.g2();
		} else {
			@Pc(297) int j;
			if (opcode == 106 || opcode == 118) {
				this.multiNpcVarbit = buffer.g2();
				count = -1;
				if (this.multiNpcVarbit == 65535) {
					this.multiNpcVarbit = -1;
				}
				this.multiNpcVarp = buffer.g2();
				if (this.multiNpcVarp == 65535) {
					this.multiNpcVarp = -1;
				}
				if (opcode == 118) {
					count = buffer.g2();
					if (count == 65535) {
						count = -1;
					}
				}
				i = buffer.g1();
				this.multiNpcs = new int[i + 2];
				for (j = 0; j <= i; j++) {
					this.multiNpcs[j] = buffer.g2();
					if (this.multiNpcs[j] == 65535) {
						this.multiNpcs[j] = -1;
					}
				}
				this.multiNpcs[i + 1] = count;
			} else if (opcode == 107) {
				this.interactive = false;
			} else if (opcode == 109) {
				this.rotationflag = false;
			} else if (opcode == 111) {
				this.hasshadow = false;
			} else if (opcode == 113) {
				this.shadowcolor1 = (short) buffer.g2();
				this.shadowcolor2 = (short) buffer.g2();
			} else if (opcode == 114) {
				this.shadowcolormodifier1 = buffer.g1b();
				this.shadowcolormodifier2 = buffer.g1b();
			} else if (opcode == 115) {
				buffer.g1();
				buffer.g1();
			} else if (opcode == 119) {
				this.loginscreenproperties = buffer.g1b();
			} else if (opcode == 121) {
				this.modeloffsets = new int[this.modelIndices.length][];
				count = buffer.g1();
				for (i = 0; i < count; i++) {
					j = buffer.g1();
					@Pc(439) int[] tempArray = this.modeloffsets[j] = new int[3];
					tempArray[0] = buffer.g1b();
					tempArray[1] = buffer.g1b();
					tempArray[2] = buffer.g1b();
				}
			} else if (opcode == 122) {
				this.hitBarId = buffer.g2();
			} else if (opcode == 123) {
				this.iconHeight = buffer.g2();
			} else if (opcode == 125) {
				this.spawndirection = buffer.g1b();
			} else if (opcode == 126) {
				this.minimapmarkerobjectentry = buffer.g2();
			} else if (opcode == 127) {
				this.bastypeid = buffer.g2();
			} else if (opcode == 128) {
				buffer.g1();
			} else if (opcode == 134) {
				this.idleSound = buffer.g2();
				if (this.idleSound == 65535) {
					this.idleSound = -1;
				}
				this.crawlSound = buffer.g2();
				if (this.crawlSound == 65535) {
					this.crawlSound = -1;
				}
				this.walkSound = buffer.g2();
				if (this.walkSound == 65535) {
					this.walkSound = -1;
				}
				this.runSound = buffer.g2();
				if (this.runSound == 65535) {
					this.runSound = -1;
				}
				this.soundRadius = buffer.g1();
			} else if (opcode == 135) {
				this.cursor1Op = buffer.g1();
				this.cursor1 = buffer.g2();
			} else if (opcode == 136) {
				this.cursor2Op = buffer.g1();
				this.cursor2 = buffer.g2();
			} else if (opcode == 137) {
				this.attackCursor = buffer.g2();
			} else if (opcode == 249) {
				count = buffer.g1();
				if (this.params == null) {
					i = IntUtils.clp2(count);
					this.params = new HashTable(i);
				}
				for (i = 0; i < count; i++) {
					@Pc(592) boolean isString = buffer.g1() == 1;
					@Pc(596) int key = buffer.g3();
					@Pc(605) Node value;
					if (isString) {
						value = new StringNode(buffer.gjstr());
					} else {
						value = new IntNode(buffer.g4());
					}
					this.params.put(value, key);
				}
			}
		}
	}
}
