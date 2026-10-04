package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!eg")
public final class SpotAnimType {

	@OriginalMember(owner = "client!eg", name = "f", descriptor = "[S")
	private short[] recol_s;

	@OriginalMember(owner = "client!eg", name = "g", descriptor = "[S")
	private short[] retex_s;

	@OriginalMember(owner = "client!eg", name = "h", descriptor = "[S")
	private short[] retex_d;

	@OriginalMember(owner = "client!eg", name = "m", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!eg", name = "o", descriptor = "I")
	private int modelId;

	@OriginalMember(owner = "client!eg", name = "s", descriptor = "[S")
	private short[] recol_d;

	@OriginalMember(owner = "client!eg", name = "i", descriptor = "Z")
	public boolean alignToTerrain = false;

	@OriginalMember(owner = "client!eg", name = "j", descriptor = "I")
	private int contrast = 0;

	@OriginalMember(owner = "client!eg", name = "k", descriptor = "I")
	private int ambient = 0;

	@OriginalMember(owner = "client!eg", name = "p", descriptor = "I")
	public int seqId = -1;

	@OriginalMember(owner = "client!eg", name = "n", descriptor = "I")
	private int resizeY = 128;

	@OriginalMember(owner = "client!eg", name = "b", descriptor = "I")
	private int resizeXZ = 128;

	@OriginalMember(owner = "client!eg", name = "q", descriptor = "I")
	private int angle = 0;

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(Lclient!wa;B)V")
	public final void decode(@OriginalArg(0) Buffer buffer) {
		while (true) {
			@Pc(17) int opcode = buffer.g1();
			if (opcode == 0) {
				return;
			}
			this.decode(buffer, opcode);
		}
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(Lclient!wa;II)V")
	private void decode(@OriginalArg(0) Buffer buffer, @OriginalArg(1) int opcode) {
		if (opcode == 1) {
			this.modelId = buffer.g2();
		} else if (opcode == 2) {
			this.seqId = buffer.g2();
		} else if (opcode == 4) {
			this.resizeXZ = buffer.g2();
		} else if (opcode == 5) {
			this.resizeY = buffer.g2();
		} else if (opcode == 6) {
			this.angle = buffer.g2();
		} else if (opcode == 7) {
			this.ambient = buffer.g1();
		} else if (opcode == 8) {
			this.contrast = buffer.g1();
		} else if (opcode == 9) {
			this.alignToTerrain = true;
		} else {
			@Pc(78) int size;
			@Pc(88) int count;
			if (opcode == 40) {
				size = buffer.g1();
				this.recol_s = new short[size];
				this.recol_d = new short[size];
				for (count = 0; count < size; count++) {
					this.recol_s[count] = (short) buffer.g2();
					this.recol_d[count] = (short) buffer.g2();
				}
			} else if (opcode == 41) {
				size = buffer.g1();
				this.retex_s = new short[size];
				this.retex_d = new short[size];
				for (count = 0; count < size; count++) {
					this.retex_s[count] = (short) buffer.g2();
					this.retex_d[count] = (short) buffer.g2();
				}
			}
		}
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(IBII)Lclient!ak;")
	public final Model constructModel(@OriginalArg(0) int nextFrame, @OriginalArg(2) int frame, @OriginalArg(3) int frameClock) {
		@Pc(13) Model cachedModel = (Model) SpotAnimTypeList.models.get(this.id);
		if (cachedModel == null) {
			@Pc(28) RawModel rawModel = RawModel.create(SpotAnimTypeList.modelsArchive, this.modelId);
			if (rawModel == null) {
				return null;
			}
			@Pc(40) int i;
			if (this.recol_s != null) {
				for (i = 0; i < this.recol_s.length; i++) {
					rawModel.recolor(this.recol_s[i], this.recol_d[i]);
				}
			}
			if (this.retex_s != null) {
				for (i = 0; i < this.retex_s.length; i++) {
					rawModel.retexture(this.retex_s[i], this.retex_d[i]);
				}
			}
			cachedModel = rawModel.createModel(this.ambient + 64, this.contrast + 850, -30, -50, -30);
			SpotAnimTypeList.models.put(cachedModel, this.id);
		}
		@Pc(118) Model model;
		if (this.seqId == -1 || frame == -1) {
			model = cachedModel.copyForAnimation(true, true, true);
		} else {
			model = SeqTypeList.get(this.seqId).animateSpotAnim(nextFrame, frameClock, frame, cachedModel);
		}
		if (this.resizeXZ != 128 || this.resizeY != 128) {
			model.resize(this.resizeXZ, this.resizeY, this.resizeXZ);
		}
		if (this.angle != 0) {
			if (this.angle == 90) {
				model.rotateCounterClockwise();
			}
			if (this.angle == 180) {
				model.rotate180();
			}
			if (this.angle == 270) {
				model.rotateClockwise();
			}
		}
		return model;
	}
}
