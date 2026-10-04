package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!tk")
public final class SeqType {

	@OriginalMember(owner = "client!qi", name = "v", descriptor = "Z")
	public static boolean applyTweening = GlobalConfig.USE_TWEENING;

	@OriginalMember(owner = "client!tk", name = "g", descriptor = "[I")
	public int[] frames;

	@OriginalMember(owner = "client!tk", name = "n", descriptor = "[Z")
	public boolean[] framegroup;

	@OriginalMember(owner = "client!tk", name = "y", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!tk", name = "C", descriptor = "[[I")
	public int[][] soundeffect;

	@OriginalMember(owner = "client!tk", name = "G", descriptor = "[I")
	public int[] frameDelay;

	@OriginalMember(owner = "client!tk", name = "I", descriptor = "[I")
	private int[] frameset;

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "I")
	public int exactmove = 2;

	@OriginalMember(owner = "client!tk", name = "b", descriptor = "Z")
	public boolean tween = false;

	@OriginalMember(owner = "client!tk", name = "f", descriptor = "I")
	public int movetype = -1;

	@OriginalMember(owner = "client!tk", name = "e", descriptor = "I")
	public int offhand = -1;

	@OriginalMember(owner = "client!tk", name = "d", descriptor = "Z")
	public boolean hasModelTransforms = false;

	@OriginalMember(owner = "client!tk", name = "t", descriptor = "I")
	public int replaycount = 99;

	@OriginalMember(owner = "client!tk", name = "z", descriptor = "I")
	public int replayoff = -1;

	@OriginalMember(owner = "client!tk", name = "B", descriptor = "I")
	public int looptype = -1;

	@OriginalMember(owner = "client!tk", name = "p", descriptor = "I")
	public int priority = 5;

	@OriginalMember(owner = "client!tk", name = "r", descriptor = "Z")
	public boolean stretches = false;

	@OriginalMember(owner = "client!tk", name = "l", descriptor = "I")
	public int mainhand = -1;

	@OriginalMember(owner = "client!tk", name = "L", descriptor = "Z")
	public boolean updateShadows = false;

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(Lclient!wa;B)V")
	public void decode(@OriginalArg(0) Buffer buffer) {
		while (true) {
			@Pc(19) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(opcode, buffer);
		}
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(IIILclient!ak;II)Lclient!ak;")
	public Model animateLocSoftware(@OriginalArg(1) int frame, @OriginalArg(2) int nextFrame, @OriginalArg(3) Model model, @OriginalArg(4) int locInfo, @OriginalArg(5) int tweenDelta) {
		@Pc(10) int delay = this.frameDelay[frame];
		@Pc(15) int frameId = this.frames[frame];
		@Pc(23) AnimFrameset animFrameset = SeqTypeList.getAnimFrameset(frameId >> 16);
		@Pc(27) int frameIdx = frameId & 0xFFFF;
		if (animFrameset == null) {
			return model.copyForLoc(true, true, true);
		}
		@Pc(39) int rotation = locInfo & 0x3;
		@Pc(41) AnimFrameset nextAnimFrameset = null;
		if ((this.tween || applyTweening) && nextFrame != -1 && this.frames.length > nextFrame) {
			@Pc(69) int nextFrameId = this.frames[nextFrame];
			nextAnimFrameset = SeqTypeList.getAnimFrameset(nextFrameId >> 16);
			nextFrame = nextFrameId & 0xFFFF;
		}
		@Pc(124) Model copy;
		if (nextAnimFrameset == null) {
			copy = model.copyForLoc(!animFrameset.isAlphaTransformed(frameIdx), !animFrameset.isColorTransformed(frameIdx), !this.hasModelTransforms);
		} else {
			copy = model.copyForLoc(!animFrameset.isAlphaTransformed(frameIdx) & !nextAnimFrameset.isAlphaTransformed(nextFrame), !animFrameset.isColorTransformed(frameIdx) & !nextAnimFrameset.isColorTransformed(nextFrame), !this.hasModelTransforms);
		}
		if (GlRenderer.enabled && this.hasModelTransforms) {
			if (rotation == 1) {
				((GlModel) copy).rotateClockwiseAll();
			} else if (rotation == 2) {
				((GlModel) copy).rotate180All();
			} else if (rotation == 3) {
				((GlModel) copy).rotateCounterClockwiseAll();
			}
		} else if (rotation == 1) {
			copy.rotateClockwise();
		} else if (rotation == 2) {
			copy.rotate180();
		} else if (rotation == 3) {
			copy.rotateCounterClockwise();
		}
		copy.applyAnimation(animFrameset, frameIdx, nextAnimFrameset, nextFrame, tweenDelta - 1, delay, this.hasModelTransforms);
		if (GlRenderer.enabled && this.hasModelTransforms) {
			if (rotation == 1) {
				((GlModel) copy).rotateCounterClockwiseAll();
			} else if (rotation == 2) {
				((GlModel) copy).rotate180All();
			} else if (rotation == 3) {
				((GlModel) copy).rotateClockwiseAll();
			}
		} else if (rotation == 1) {
			copy.rotateCounterClockwise();
		} else if (rotation == 2) {
			copy.rotate180();
		} else if (rotation == 3) {
			copy.rotateClockwise();
		}
		return copy;
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(Lclient!ak;BIII)Lclient!ak;")
	public Model animateEntity(@OriginalArg(0) Model model, @OriginalArg(2) int nextFrame, @OriginalArg(3) int tweenDelta, @OriginalArg(4) int frame) {
		@Pc(8) int frameId = this.frames[frame];
		@Pc(13) int delay = this.frameDelay[frame];
		@Pc(19) AnimFrameset animFrameset = SeqTypeList.getAnimFrameset(frameId >> 16);
		@Pc(23) int frameIdx = frameId & 0xFFFF;
		if (animFrameset == null) {
			return model.copyForEntity(true, true, true);
		}
		@Pc(34) AnimFrameset nextAnimFrameset = null;
		if ((this.tween || applyTweening) && nextFrame != -1 && nextFrame < this.frames.length) {
			@Pc(59) int nextFrameId = this.frames[nextFrame];
			nextAnimFrameset = SeqTypeList.getAnimFrameset(nextFrameId >> 16);
			nextFrame = nextFrameId & 0xFFFF;
		}
		@Pc(71) AnimFrameset secondaryFrameset = null;
		@Pc(81) AnimFrameset secondaryNextFrameset = null;
		@Pc(83) int secondaryFrameIdx = 0;
		@Pc(85) int secondaryNextFrameIdx = 0;
		if (this.frameset != null) {
			if (this.frameset.length > frame) {
				secondaryFrameIdx = this.frameset[frame];
				if (secondaryFrameIdx != 65535) {
					secondaryFrameset = SeqTypeList.getAnimFrameset(secondaryFrameIdx >> 16);
					secondaryFrameIdx &= 0xFFFF;
				}
			}
			if ((this.tween || applyTweening) && nextFrame != -1 && this.frameset.length > nextFrame) {
				secondaryNextFrameIdx = this.frameset[nextFrame];
				if (secondaryNextFrameIdx != 65535) {
					secondaryNextFrameset = SeqTypeList.getAnimFrameset(secondaryNextFrameIdx >> 16);
					secondaryNextFrameIdx &= 0xFFFF;
				}
			}
		}
		@Pc(163) boolean noAlpha = !animFrameset.isAlphaTransformed(frameIdx);
		@Pc(172) boolean noColor = !animFrameset.isColorTransformed(frameIdx);
		if (secondaryFrameset != null) {
			noAlpha &= !secondaryFrameset.isAlphaTransformed(secondaryFrameIdx);
			noColor &= !secondaryFrameset.isColorTransformed(secondaryFrameIdx);
		}
		if (nextAnimFrameset != null) {
			noAlpha &= !nextAnimFrameset.isAlphaTransformed(nextFrame);
			noColor &= !nextAnimFrameset.isColorTransformed(nextFrame);
		}
		if (secondaryNextFrameset != null) {
			noAlpha &= !secondaryNextFrameset.isAlphaTransformed(secondaryNextFrameIdx);
			noColor &= !secondaryNextFrameset.isColorTransformed(secondaryNextFrameIdx);
		}
		@Pc(258) Model copy = model.copyForEntity(noAlpha, noColor, !this.hasModelTransforms);
		copy.applyAnimation(animFrameset, frameIdx, nextAnimFrameset, nextFrame, tweenDelta - 1, delay, this.hasModelTransforms);
		if (secondaryFrameset != null) {
			copy.applyAnimation(secondaryFrameset, secondaryFrameIdx, secondaryNextFrameset, secondaryNextFrameIdx, tweenDelta - 1, delay, this.hasModelTransforms);
		}
		return copy;
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(IIIILclient!ak;I)Lclient!ak;")
	public Model animateLocGl(@OriginalArg(0) int nextFrame, @OriginalArg(1) int frame, @OriginalArg(2) int tweenDelta, @OriginalArg(3) int locInfo, @OriginalArg(4) Model model) {
		@Pc(6) int delay = this.frameDelay[frame];
		@Pc(11) int frameId = this.frames[frame];
		@Pc(19) AnimFrameset animFrameset = SeqTypeList.getAnimFrameset(frameId >> 16);
		@Pc(27) int frameIdx = frameId & 0xFFFF;
		if (animFrameset == null) {
			return model.copyForEntity(true, true, true);
		}
		@Pc(40) int rotation = locInfo & 0x3;
		@Pc(42) AnimFrameset nextAnimFrameset = null;
		if ((this.tween || applyTweening) && nextFrame != -1 && nextFrame < this.frames.length) {
			@Pc(66) int nextFrameId = this.frames[nextFrame];
			nextAnimFrameset = SeqTypeList.getAnimFrameset(nextFrameId >> 16);
			nextFrame = nextFrameId & 0xFFFF;
		}
		@Pc(106) Model copy;
		if (nextAnimFrameset == null) {
			copy = model.copyForEntity(!animFrameset.isAlphaTransformed(frameIdx), !animFrameset.isColorTransformed(frameIdx), !this.hasModelTransforms);
		} else {
			copy = model.copyForEntity(!animFrameset.isAlphaTransformed(frameIdx) & !nextAnimFrameset.isAlphaTransformed(nextFrame), !animFrameset.isColorTransformed(frameIdx) & !nextAnimFrameset.isColorTransformed(nextFrame), !this.hasModelTransforms);
		}
		if (this.hasModelTransforms && GlRenderer.enabled) {
			if (rotation == 1) {
				((GlModel) copy).rotateClockwiseAll();
			} else if (rotation == 2) {
				((GlModel) copy).rotate180All();
			} else if (rotation == 3) {
				((GlModel) copy).rotateCounterClockwiseAll();
			}
		} else if (rotation == 1) {
			copy.rotateClockwise();
		} else if (rotation == 2) {
			copy.rotate180();
		} else if (rotation == 3) {
			copy.rotateCounterClockwise();
		}
		copy.applyAnimation(animFrameset, frameIdx, nextAnimFrameset, nextFrame, tweenDelta - 1, delay, this.hasModelTransforms);
		if (this.hasModelTransforms && GlRenderer.enabled) {
			if (rotation == 1) {
				((GlModel) copy).rotateCounterClockwiseAll();
			} else if (rotation == 2) {
				((GlModel) copy).rotate180All();
			} else if (rotation == 3) {
				((GlModel) copy).rotateClockwiseAll();
			}
		} else if (rotation == 1) {
			copy.rotateCounterClockwise();
		} else if (rotation == 2) {
			copy.rotate180();
		} else if (rotation == 3) {
			copy.rotateClockwise();
		}
		return copy;
	}

	@OriginalMember(owner = "client!tk", name = "b", descriptor = "(B)V")
	public void postDecode() {
		if (this.looptype == -1) {
			if (this.framegroup == null) {
				this.looptype = 0;
			} else {
				this.looptype = 2;
			}
		}

		if (this.movetype == -1) {
			if (this.framegroup == null) {
				this.movetype = 0;
			} else {
				this.movetype = 2;
			}
		}
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(IIIBLclient!ak;)Lclient!ak;")
	public Model animateSpotAnim(@OriginalArg(0) int nextFrame, @OriginalArg(1) int tweenDelta, @OriginalArg(2) int frame, @OriginalArg(4) Model model) {
		@Pc(16) int delay = this.frameDelay[frame];
		@Pc(21) int frameId = this.frames[frame];
		@Pc(27) AnimFrameset animFrameset = SeqTypeList.getAnimFrameset(frameId >> 16);
		@Pc(31) int frameIdx = frameId & 0xFFFF;
		if (animFrameset == null) {
			return model.copyForAnimation(true, true, true);
		}
		@Pc(42) AnimFrameset nextAnimFrameset = null;
		if ((this.tween || applyTweening) && nextFrame != -1 && this.frames.length > nextFrame) {
			@Pc(65) int nextFrameId = this.frames[nextFrame];
			nextAnimFrameset = SeqTypeList.getAnimFrameset(nextFrameId >> 16);
			nextFrame = nextFrameId & 0xFFFF;
		}
		@Pc(103) Model copy;
		if (nextAnimFrameset == null) {
			copy = model.copyForAnimation(!animFrameset.isAlphaTransformed(frameIdx), !animFrameset.isColorTransformed(frameIdx), !this.hasModelTransforms);
		} else {
			copy = model.copyForAnimation(!animFrameset.isAlphaTransformed(frameIdx) & !nextAnimFrameset.isAlphaTransformed(nextFrame), !animFrameset.isColorTransformed(frameIdx) & !nextAnimFrameset.isColorTransformed(nextFrame), !this.hasModelTransforms);
		}
		copy.applyAnimation(animFrameset, frameIdx, nextAnimFrameset, nextFrame, tweenDelta - 1, delay, this.hasModelTransforms);
		return copy;
	}

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "(IBLclient!wa;)V")
	private void decode(@OriginalArg(0) int opcode, @OriginalArg(2) Buffer buffer) {
		@Pc(8) int count;
		@Pc(14) int i;

		if (opcode == 1) {
			count = buffer.g2();
			this.frameDelay = new int[count];
			for (i = 0; i < count; i++) {
				this.frameDelay[i] = buffer.g2();
			}
			this.frames = new int[count];
			for (i = 0; i < count; i++) {
				this.frames[i] = buffer.g2();
			}
			for (i = 0; i < count; i++) {
				this.frames[i] += buffer.g2() << 16;
			}
		} else if (opcode == 2) {
			this.replayoff = buffer.g2();
		} else if (opcode == 3) {
			this.framegroup = new boolean[256];
			count = buffer.g1();
			for (i = 0; i < count; i++) {
				this.framegroup[buffer.g1()] = true;
			}
		} else if (opcode == 4) {
			this.stretches = true;
		} else if (opcode == 5) {
			this.priority = buffer.g1();
		} else if (opcode == 6) {
			this.mainhand = buffer.g2();
		} else if (opcode == 7) {
			this.offhand = buffer.g2();
		} else if (opcode == 8) {
			this.replaycount = buffer.g1();
		} else if (opcode == 9) { // TODO: confirm
			this.looptype = buffer.g1();
		} else if (opcode == 10) { // TODO: confirm
			this.movetype = buffer.g1();
		} else if (opcode == 11) { // TODO: confirm
			this.exactmove = buffer.g1();
		} else if (opcode == 12) { // TODO: confirm
			count = buffer.g1();
			this.frameset = new int[count];
			for (i = 0; i < count; i++) {
				this.frameset[i] = buffer.g2();
			}
			for (i = 0; i < count; i++) {
				this.frameset[i] += buffer.g2() << 16;
			}
		} else if (opcode == 13) {
			count = buffer.g2();
			this.soundeffect = new int[count][];
			for (i = 0; i < count; i++) {
				@Pc(163) int soundCount = buffer.g1();
				if (soundCount > 0) {
					this.soundeffect[i] = new int[soundCount];
					this.soundeffect[i][0] = buffer.g3();
					for (@Pc(182) int j = 1; j < soundCount; j++) {
						this.soundeffect[i][j] = buffer.g2();
					}
				}
			}
		} else if (opcode == 14) {
			this.hasModelTransforms = true;
		} else if (opcode == 15) { // TODO: (probably) not an authentic name
			this.tween = true;
		} else if (opcode == 16) {
			this.updateShadows = true;
		}
	}
}
