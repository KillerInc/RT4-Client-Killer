package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;

@OriginalClass("client!m")
public interface TextureProvider {

	@OriginalMember(owner = "client!m", name = "a", descriptor = "(BI)Z")
	boolean isOpaque(@OriginalArg(1) int textureId);

	@OriginalMember(owner = "client!m", name = "a", descriptor = "(IZ)V")
	void bindTexture(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "b", descriptor = "(IZ)I")
	int getAnimationType(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "a", descriptor = "(II)I")
	int getTextureSpeed(@OriginalArg(1) int textureId);

	@OriginalMember(owner = "client!m", name = "b", descriptor = "(II)Z")
	boolean isTextureLoaded(@OriginalArg(1) int textureId);

	@OriginalMember(owner = "client!m", name = "c", descriptor = "(II)Z")
	boolean isTextureFlipped(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "a", descriptor = "(IZF)[I")
	int[] getAnimatedPixels(@OriginalArg(0) int textureId, @OriginalArg(2) float progress);

	@OriginalMember(owner = "client!m", name = "b", descriptor = "(BI)Z")
	boolean isLowDetail(@OriginalArg(1) int textureId);

	@OriginalMember(owner = "client!m", name = "d", descriptor = "(II)I")
	int getAverageColor(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "e", descriptor = "(II)[I")
	int[] getPixels(@OriginalArg(1) int textureId);

	@OriginalMember(owner = "client!m", name = "f", descriptor = "(II)Z")
	boolean isTextureRepeating(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "g", descriptor = "(II)I")
	int getMaterialType(@OriginalArg(0) int textureId);

	@OriginalMember(owner = "client!m", name = "h", descriptor = "(II)I")
	int getTextureBrightness(@OriginalArg(1) int textureId);
}
