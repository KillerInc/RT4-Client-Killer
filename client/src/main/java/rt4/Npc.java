package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!km")
public final class Npc extends PathingEntity {

	@OriginalMember(owner = "client!km", name = "rc", descriptor = "Lclient!me;")
	public NpcType type;

	@OriginalMember(owner = "client!ij", name = "a", descriptor = "(Lclient!km;I)I")
	public static int getSound(@OriginalArg(0) Npc npc) {
		@Pc(13) NpcType npcType = npc.type;
		if (npcType.multiNpcs != null) {
			npcType = npcType.getMultiNpc();
			if (npcType == null) {
				return -1;
			}
		}
		@Pc(29) int sound = npcType.walkSound;
		@Pc(33) BasType npcBas = npc.getBasType();
		if (npcBas.idleAnimationId == npc.movementSeqId) {
			sound = npcType.idleSound;
		} else if (npc.movementSeqId == npcBas.runAnimationId || npcBas.runFullTurnAnimationId == npc.movementSeqId || npc.movementSeqId == npcBas.runCWTurnAnimationId || npc.movementSeqId == npcBas.runCCWTurnAnimationId) {
			sound = npcType.runSound;
		} else if (npcBas.slowWalkAnimationId == npc.movementSeqId || npc.movementSeqId == npcBas.slowWalkFullTurnAnimationId || npc.movementSeqId == npcBas.slowWalkCWTurnAnimationId || npc.movementSeqId == npcBas.slowWalkCCWTurnAnimationId) {
			sound = npcType.crawlSound;
		}
		return sound;
	}

	@OriginalMember(owner = "client!km", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() {
	}

	@OriginalMember(owner = "client!km", name = "b", descriptor = "()I")
	@Override
	public final int getMinY() {
		return this.minY;
	}

	@OriginalMember(owner = "client!km", name = "a", descriptor = "(IIIIIIIIJILclient!ga;)V")
	@Override
	public final void render(@OriginalArg(0) int orientation, @OriginalArg(1) int sinPitch, @OriginalArg(2) int cosPitch, @OriginalArg(3) int sinYaw, @OriginalArg(4) int cosYaw, @OriginalArg(5) int x, @OriginalArg(6) int z, @OriginalArg(7) int y, @OriginalArg(8) long key, @OriginalArg(9) int bitset, @OriginalArg(10) ParticleSystem particles) {
		if (this.type == null) {
			return;
		}

		@Pc(29) SeqType seq = this.seqId != -1 && this.seqDelay == 0 ? SeqTypeList.get(this.seqId) : null;
		@Pc(53) SeqType movementSeq = this.movementSeqId == -1 || this.movementSeqId == this.getBasType().idleAnimationId && seq != null ? null : SeqTypeList.get(this.movementSeqId);
		@Pc(74) Model body = this.type.getBodyModel(this.slotAnimations, this.movementSeqNextFrame, this.movementSeqFrame, this.seqNextFrame, this.seqDelayClock, this.seqFrame, movementSeq, this.movementSeqDelayClock, seq);
		if (body == null) {
			return;
		}

		this.minY = body.getMinY();
		@Pc(84) NpcType resolvedType = this.type;
		if (resolvedType.multiNpcs != null) {
			resolvedType = resolvedType.getMultiNpc();
		}

		@Pc(140) Model model;
		if (Preferences.characterShadowsOn && resolvedType.hasshadow) {
			model = ShadowModelList.getOrCreateShadow(this.type.shadowcolormodifier1, this.seqStretches, movementSeq == null ? seq : movementSeq, this.xFine, this.type.shadowcolor2, this.yFine, this.type.shadowcolor1, this.type.size, body, orientation, movementSeq == null ? this.seqFrame : this.movementSeqFrame, this.tileHeight, this.type.shadowcolormodifier2);
			if (GlRenderer.enabled) {
				@Pc(144) float projDist = GlRenderer.getProjectionDistance();
				@Pc(146) float depthBias = GlRenderer.getDepthBias();
				GlRenderer.disableDepthMask();
				GlRenderer.setDepthBias(projDist, depthBias - 150.0F);
				model.render(0, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, -1L, bitset, this.particleSystem);
				GlRenderer.enableDepthMask();
				GlRenderer.setDepthBias(projDist, depthBias);
			} else {
				model.render(0, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, -1L, bitset, this.particleSystem);
			}
		}

		this.applyBodyLean(body);
		this.alignToTerrain(body, orientation);

		model = null;
		if (this.spotAnimId != -1 && this.spotAnimFrame != -1) {
			@Pc(211) SpotAnimType spotAnimType = SpotAnimTypeList.get(this.spotAnimId);
			model = spotAnimType.constructModel(this.spotAnimNextFrame, this.spotAnimFrame, this.spotAnimDelayClock);
			if (model != null) {
				model.translate(0, -this.spotAnimY, 0);

				if (spotAnimType.alignToTerrain) {
					if (PathingEntity.terrainPitchAngle != 0) {
						model.rotateX(PathingEntity.terrainPitchAngle);
					}
					if (PathingEntity.terrainRollAngle != 0) {
						model.rotateZ(PathingEntity.terrainRollAngle);
					}
					if (PathingEntity.terrainYOffset != 0) {
						model.translate(0, PathingEntity.terrainYOffset, 0);
					}
				}
			}
		}

		if (!GlRenderer.enabled) {
			if (model != null) {
				body = ((SoftwareModel) body).mergeWith(model);
			}

			if (this.type.size == 1) {
				body.pickable = true;
			}

			body.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, this.particleSystem);
			return;
		}

		if (this.type.size == 1) {
			body.pickable = true;
		}

		body.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, this.particleSystem);
		if (model != null) {
			if (this.type.size == 1) {
				model.pickable = true;
			}

			model.render(orientation, sinPitch, cosPitch, sinYaw, cosYaw, x, z, y, key, bitset, this.particleSystem);
		}
	}

	@OriginalMember(owner = "client!km", name = "b", descriptor = "(I)I")
	@Override
	public final int getBasId() {
		if (client.game != 0 && this.type.multiNpcs != null) {
			@Pc(17) NpcType multiNpc = this.type.getMultiNpc();
			if (multiNpc != null && multiNpc.bastypeid != -1) {
				return multiNpc.bastypeid;
			}
		}
		return this.basTypeId;
	}

	@OriginalMember(owner = "client!km", name = "a", descriptor = "(IIIII)V")
	@Override
	public final void updateModel(@OriginalArg(0) int x, @OriginalArg(1) int z, @OriginalArg(2) int y, @OriginalArg(3) int yaw, @OriginalArg(4) int pitch) {
		if (this.type == null) {
		}
	}

	@OriginalMember(owner = "client!km", name = "a", descriptor = "(B)Z")
	@Override
	public final boolean isVisible() {
		return this.type != null;
	}

	@OriginalMember(owner = "client!km", name = "a", descriptor = "(ILclient!me;)V")
	public final void setNpcType(@OriginalArg(1) NpcType npcType) {
		this.type = npcType;
		if (this.particleSystem != null) {
			this.particleSystem.update();
		}
	}
}
