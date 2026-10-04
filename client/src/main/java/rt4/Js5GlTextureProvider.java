package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nk")
public final class Js5GlTextureProvider implements TextureProvider {

	@OriginalMember(owner = "client!nk", name = "z", descriptor = "Z")
	private boolean lowDetail = false;

	@OriginalMember(owner = "client!nk", name = "J", descriptor = "I")
	private int capacity = 50;

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "Lclient!ve;")
	private final Js5 sprites;

	@OriginalMember(owner = "client!nk", name = "i", descriptor = "Lclient!ve;")
	private final Js5 textureDefs;

	@OriginalMember(owner = "client!nk", name = "P", descriptor = "Lclient!gn;")
	private LruHashTable glTextures;

	@OriginalMember(owner = "client!nk", name = "G", descriptor = "Lclient!gn;")
	private LruHashTable solidColorSprites;

	@OriginalMember(owner = "client!nk", name = "Q", descriptor = "[B")
	private final byte[] animationTypes;

	@OriginalMember(owner = "client!nk", name = "l", descriptor = "[B")
	private final byte[] brightnessValues;

	@OriginalMember(owner = "client!nk", name = "h", descriptor = "[B")
	private final byte[] scrollSpeeds;

	@OriginalMember(owner = "client!nk", name = "F", descriptor = "[S")
	private final short[] averageColors;

	@OriginalMember(owner = "client!nk", name = "R", descriptor = "[B")
	private final byte[] materialTypes;

	@OriginalMember(owner = "client!nk", name = "k", descriptor = "[Z")
	private final boolean[] opaque;

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "[Z")
	private final boolean[] forceLowDetail;

	@OriginalMember(owner = "client!nk", name = "c", descriptor = "[Z")
	private final boolean[] repeating;

	@OriginalMember(owner = "client!nk", name = "s", descriptor = "[Z")
	private final boolean[] defined;

	@OriginalMember(owner = "client!nk", name = "D", descriptor = "[Z")
	private final boolean[] flipped;

	@OriginalMember(owner = "client!nk", name = "<init>", descriptor = "(Lclient!ve;Lclient!ve;Lclient!ve;IZ)V")
	public Js5GlTextureProvider(@OriginalArg(0) Js5 textureDefs, @OriginalArg(1) Js5 textureConfig, @OriginalArg(2) Js5 sprites, @OriginalArg(3) int capacity, @OriginalArg(4) boolean lowDetail) {
		this.sprites = sprites;
		this.lowDetail = lowDetail;
		this.capacity = capacity;
		this.textureDefs = textureDefs;
		this.glTextures = new LruHashTable(this.capacity);
		if (GlRenderer.enabled) {
			this.solidColorSprites = new LruHashTable(this.capacity);
		} else {
			this.solidColorSprites = null;
		}

		@Pc(51) Buffer buffer = new Buffer(textureConfig.fetchFile(0, 0));
		@Pc(55) int textureCount = buffer.g2();
		this.animationTypes = new byte[textureCount];
		this.brightnessValues = new byte[textureCount];
		this.scrollSpeeds = new byte[textureCount];
		this.averageColors = new short[textureCount];
		this.materialTypes = new byte[textureCount];
		this.opaque = new boolean[textureCount];
		this.forceLowDetail = new boolean[textureCount];
		this.repeating = new boolean[textureCount];
		this.defined = new boolean[textureCount];
		this.flipped = new boolean[textureCount];
		@Pc(97) int i;
		for (i = 0; i < textureCount; i++) {
			this.defined[i] = buffer.g1() == 1;
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.repeating[i] = buffer.g1() == 1;
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.opaque[i] = buffer.g1() == 1;
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.forceLowDetail[i] = buffer.g1() == 1;
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.flipped[i] = buffer.g1() == 1;
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.scrollSpeeds[i] = buffer.g1b();
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.brightnessValues[i] = buffer.g1b();
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.materialTypes[i] = buffer.g1b();
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.animationTypes[i] = buffer.g1b();
			}
		}
		for (i = 0; i < textureCount; i++) {
			if (this.defined[i]) {
				this.averageColors[i] = (short) buffer.g2();
			}
		}
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(ZI)V")
	public final void resetAnimatedTextures(@OriginalArg(1) int deltaTime) {
		for (@Pc(19) GlTexture texture = (GlTexture) this.glTextures.head(); texture != null; texture = (GlTexture) this.glTextures.next()) {
			if (texture.animationDirty) {
				texture.scrollAnimation(deltaTime);
				texture.animationDirty = false;
			}
		}
	}

	@OriginalMember(owner = "client!nk", name = "e", descriptor = "(II)[I")
	@Override
	public final int[] getPixels(@OriginalArg(1) int textureId) {
		@Pc(16) GlTexture texture = this.getOrLoadGlTexture(textureId);
		return texture == null ? null : texture.getPixels(this.lowDetail || this.forceLowDetail[textureId], this, this.sprites);
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(IZ)V")
	@Override
	public final void bindTexture(@OriginalArg(0) int textureId) {
		MaterialManager.setMaterial(this.animationTypes[textureId] & 0xFF, this.materialTypes[textureId] & 0xFF);
		@Pc(23) boolean bound = false;
		@Pc(28) GlTexture texture = this.getOrLoadGlTexture(textureId);
		if (texture != null) {
			bound = texture.bind(this.sprites, this, this.lowDetail || this.forceLowDetail[textureId]);
		}
		if (!bound) {
			@Pc(56) GlSolidColorTexture fallback = this.getOrCreateSolidColorTexture(textureId);
			fallback.bind();
		}
	}

	@OriginalMember(owner = "client!nk", name = "i", descriptor = "(II)Lclient!uh;")
	private GlTexture getOrLoadGlTexture(@OriginalArg(0) int textureId) {
		@Pc(14) GlTexture texture = (GlTexture) this.glTextures.get(textureId);
		if (texture != null) {
			return texture;
		}
		@Pc(30) byte[] data = this.textureDefs.fetchFile(textureId, 0);
		if (data == null) {
			return null;
		} else {
			@Pc(41) Buffer buffer = new Buffer(data);
			texture = new GlTexture(buffer);
			this.glTextures.put(texture, textureId);
			return texture;
		}
	}

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "(IZ)I")
	@Override
	public final int getAnimationType(@OriginalArg(0) int textureId) {
		return this.animationTypes[textureId] & 0xFF;
	}

	@OriginalMember(owner = "client!nk", name = "g", descriptor = "(II)I")
	@Override
	public final int getMaterialType(@OriginalArg(0) int textureId) {
		return this.materialTypes[textureId] & 0xFF;
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(II)I")
	@Override
	public final int getTextureSpeed(@OriginalArg(1) int textureId) {
		return this.scrollSpeeds[textureId] & 0xFF;
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(BI)Z")
	@Override
	public final boolean isOpaque(@OriginalArg(1) int textureId) {
		return this.opaque[textureId];
	}

	@OriginalMember(owner = "client!nk", name = "j", descriptor = "(II)Lclient!sd;")
	private GlSolidColorTexture getOrCreateSolidColorTexture(@OriginalArg(1) int textureId) {
		@Pc(19) GlSolidColorTexture texture = (GlSolidColorTexture) this.solidColorSprites.get(textureId);
		if (texture == null) {
			texture = new GlSolidColorTexture(this.averageColors[textureId] & 0xFFFF);
			this.solidColorSprites.put(texture, textureId);
			return texture;
		} else {
			return texture;
		}
	}

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "(II)Z")
	@Override
	public final boolean isTextureLoaded(@OriginalArg(1) int textureId) {
		@Pc(15) GlTexture texture = this.getOrLoadGlTexture(textureId);
		return texture != null && texture.isReady(this, this.sprites);
	}

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "(ZI)V")
	public final void setLowDetail(@OriginalArg(0) boolean lowDetail) {
		this.lowDetail = lowDetail;
		this.clear();
	}

	@OriginalMember(owner = "client!nk", name = "c", descriptor = "(II)Z")
	@Override
	public final boolean isTextureFlipped(@OriginalArg(0) int textureId) {
		return this.flipped[textureId];
	}

	@OriginalMember(owner = "client!nk", name = "h", descriptor = "(II)I")
	@Override
	public final int getTextureBrightness(@OriginalArg(1) int textureId) {
		return this.brightnessValues[textureId] & 0xFF;
	}

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "(I)V")
	public final void clear() {
		this.glTextures.clear();
		if (this.solidColorSprites != null) {
			this.solidColorSprites.clear();
		}
	}

	@OriginalMember(owner = "client!nk", name = "d", descriptor = "(II)I")
	@Override
	public final int getAverageColor(@OriginalArg(0) int textureId) {
		return this.averageColors[textureId] & 0xFFFF;
	}

	@OriginalMember(owner = "client!nk", name = "b", descriptor = "(BI)Z")
	@Override
	public final boolean isLowDetail(@OriginalArg(1) int textureId) {
		return this.lowDetail || this.forceLowDetail[textureId];
	}

	@OriginalMember(owner = "client!nk", name = "k", descriptor = "(II)V")
	public final void setCapacity(@OriginalArg(0) int capacity) {
		this.capacity = capacity;
		this.glTextures = new LruHashTable(this.capacity);
		if (GlRenderer.enabled) {
			this.solidColorSprites = new LruHashTable(this.capacity);
		} else {
			this.solidColorSprites = null;
		}
	}

	@OriginalMember(owner = "client!nk", name = "f", descriptor = "(II)Z")
	@Override
	public final boolean isTextureRepeating(@OriginalArg(0) int textureId) {
		return this.repeating[textureId];
	}

	@OriginalMember(owner = "client!nk", name = "a", descriptor = "(IZF)[I")
	@Override
	public final int[] getAnimatedPixels(@OriginalArg(0) int textureId, @OriginalArg(2) float animationProgress) {
		@Pc(8) GlTexture texture = this.getOrLoadGlTexture(textureId);
		if (texture == null) {
			return null;
		} else {
			texture.animationDirty = true;
			return texture.getAnimatedPixels(this, animationProgress, this.sprites, this.lowDetail || this.forceLowDetail[textureId]);
		}
	}
}
