package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class HintArrowManager {
	@OriginalMember(owner = "client!ih", name = "l", descriptor = "Lclient!n;")
	public static final SoftLruHashTable models = new SoftLruHashTable(4);

	@OriginalMember(owner = "client!mh", name = "c", descriptor = "(II)V")
	public static void clean() {
		models.clean(5);
	}

	@OriginalMember(owner = "client!og", name = "a", descriptor = "(I)V")
	public static void removeSoft() {
		models.removeSoft();
	}

	@OriginalMember(owner = "client!oi", name = "b", descriptor = "(I)V")
	public static void clear() {
		models.clear();
	}

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "(ZIIIILclient!ak;I)Lclient!ak;")
	public static Model getModel(@OriginalArg(1) int rotation, @OriginalArg(2) int y, @OriginalArg(3) int modelId, @OriginalArg(4) int x, @OriginalArg(5) Model baseModel, @OriginalArg(6) int baseHeight) {
		@Pc(4) long cacheKey = modelId;
		@Pc(10) Model model = (Model) models.get(cacheKey);
		if (model == null) {
			@Pc(22) RawModel rawModel = RawModel.create(client.js5Archive7, modelId);
			if (rawModel == null) {
				return null;
			}
			model = rawModel.createModel(64, 768, -50, -10, -50);
			models.put(model, cacheKey);
		}
		@Pc(42) int minX = baseModel.getMinX();
		@Pc(45) int maxX = baseModel.getMaxX();
		@Pc(48) int minZ = baseModel.getMinZ();
		@Pc(51) int maxZ = baseModel.getMaxZ();
		model = model.copyForAnimation(true, true, true);
		if (rotation != 0) {
			model.rotateY(rotation);
		}
		@Pc(94) int i;
		if (GlRenderer.enabled) {
			@Pc(68) GlModel glModel = (GlModel) model;
			if (baseHeight != SceneGraph.getTileHeight(Player.plane, x + minX, y + minZ) || baseHeight != SceneGraph.getTileHeight(Player.plane, x + maxX, maxZ + y)) {
				for (i = 0; i < glModel.vertexCount; i++) {
					glModel.vertexY[i] += SceneGraph.getTileHeight(Player.plane, glModel.vertexX[i] + x, glModel.vertexZ[i] + y) - baseHeight;
				}
				glModel.vertexBuffer.valid = false;
				glModel.bounds.valid = false;
			}
		} else {
			@Pc(142) SoftwareModel swModel = (SoftwareModel) model;
			if (baseHeight != SceneGraph.getTileHeight(Player.plane, minX + x, minZ + y) || baseHeight != SceneGraph.getTileHeight(Player.plane, x + maxX, maxZ + y)) {
				for (i = 0; i < swModel.vertexCount; i++) {
					swModel.vertexY[i] += SceneGraph.getTileHeight(Player.plane, x + swModel.vertexX[i], swModel.vertexZ[i] + y) - baseHeight;
				}
				swModel.boundsValid = false;
			}
		}
		return model;
	}
}
