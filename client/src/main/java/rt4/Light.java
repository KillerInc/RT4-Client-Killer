package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!gi")
public final class Light {

	@OriginalMember(owner = "client!f", name = "P", descriptor = "[I")
	public static int[] NOISE;
	@OriginalMember(owner = "client!gi", name = "a", descriptor = "Z")
	public final boolean extendsDown;

	@OriginalMember(owner = "client!gi", name = "d", descriptor = "I")
	private int alphaMax;

	@OriginalMember(owner = "client!gi", name = "e", descriptor = "I")
	private int flickerType;

	@OriginalMember(owner = "client!gi", name = "i", descriptor = "I")
	public int z;

	@OriginalMember(owner = "client!gi", name = "j", descriptor = "I")
	public final int radius;

	@OriginalMember(owner = "client!gi", name = "p", descriptor = "I")
	private int alphaMin;

	@OriginalMember(owner = "client!gi", name = "q", descriptor = "I")
	public int x;

	@OriginalMember(owner = "client!gi", name = "r", descriptor = "I")
	public int level;

	@OriginalMember(owner = "client!gi", name = "t", descriptor = "I")
	public final int animationPreset;

	@OriginalMember(owner = "client!gi", name = "u", descriptor = "[S")
	public final short[] spanProfile;

	@OriginalMember(owner = "client!gi", name = "w", descriptor = "F")
	public float alpha;

	@OriginalMember(owner = "client!gi", name = "x", descriptor = "I")
	public int y;

	@OriginalMember(owner = "client!gi", name = "y", descriptor = "I")
	private int flickerSpeed;

	@OriginalMember(owner = "client!gi", name = "z", descriptor = "F")
	public float quadraticAttenuation;

	@OriginalMember(owner = "client!gi", name = "A", descriptor = "Z")
	public final boolean extendsUp;

	@OriginalMember(owner = "client!gi", name = "B", descriptor = "Lclient!fj;")
	public LightMesh mesh;

	@OriginalMember(owner = "client!gi", name = "C", descriptor = "I")
	public final int color;

	@OriginalMember(owner = "client!gi", name = "E", descriptor = "I")
	private final int animationPhase;

	@OriginalMember(owner = "client!gi", name = "g", descriptor = "Z")
	public boolean onBridge = false;

	public boolean matchesStaticLightOverride = false;

	@OriginalMember(owner = "client!gi", name = "I", descriptor = "[F")
	public final float[] diffuse = new float[4];

	@OriginalMember(owner = "client!gi", name = "<init>", descriptor = "(Lclient!wa;)V")
	public Light(@OriginalArg(0) Buffer buf) {
		if (NOISE == null) {
			init();
		}
		this.level = buf.g1();
		this.extendsDown = (this.level & 0x10) != 0;
		this.extendsUp = (this.level & 0x8) != 0;
		this.level &= 0x7;
		this.x = buf.g2();
		this.y = buf.g2();
		this.z = buf.g2();
		this.radius = buf.g1();
		this.computeAttenuation();
		this.spanProfile = new short[this.radius * 2 + 1];
		@Pc(87) int i;
		for (i = 0; i < this.spanProfile.length; i++) {
			this.spanProfile[i] = (short) buf.g2();
		}
		this.color = Rasteriser.palette[buf.g2()];
		@Pc(87) int packed = buf.g1();
		this.animationPhase = (packed & 0xE0) << 3;
		this.animationPreset = packed & 0x1F;
		if (this.animationPreset != 31) {
			this.initAnimationPreset();
		}
	}

	@OriginalMember(owner = "client!gk", name = "b", descriptor = "(B)V")
	public static void init() {
		NOISE = craeteNoise(0.4F);
	}

	@OriginalMember(owner = "client!qk", name = "a", descriptor = "(ZIIIIFII)[I")
	public static int[] craeteNoise(@OriginalArg(5) float persistence) {
		@Pc(11) int[] noise = new int[2048];
		@Pc(15) TextureOpPerlinNoise perlin = new TextureOpPerlinNoise();
		perlin.frequencyX = 8;
		perlin.octaveCount = 4;
		perlin.seed = 35;
		perlin.frequencyY = 8;
		perlin.persistence = (int) (persistence * 4096.0F);
		perlin.normalizeOutput = true;
		perlin.postDecode();
		Texture.setSize(1, 2048);
		perlin.getNoiseRow(0, noise);
		return noise;
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(BIIII)V")
	public final void setFlickerParams(@OriginalArg(1) int type, @OriginalArg(2) int speed, @OriginalArg(3) int min, @OriginalArg(4) int max) {
		this.flickerType = type;
		this.alphaMin = min;
		this.alphaMax = max;
		this.flickerSpeed = speed;
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(I)V")
	private void computeAttenuation() {
		@Pc(10) int range = (this.radius << 7) + 64;
		this.quadraticAttenuation = 1.0F / (float) (range * range);
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(ZII)V")
	public final void updateAnimation(@OriginalArg(0) boolean disableFlicker, @OriginalArg(1) int time) {
		@Pc(26) int t = this.animationPhase + time * this.flickerSpeed / 50 & 0x7FF;
		@Pc(29) int type = this.flickerType;
		@Pc(62) int alpha;
		if (type == 1) {
			alpha = (MathUtils.sin[t] >> 6) + 1024;
		} else if (type == 3) {
			alpha = NOISE[t] >> 1;
		} else if (type == 4) {
			alpha = t >> 10 << 11;
		} else if (type == 2) {
			alpha = t;
		} else if (type == 5) {
			alpha = (t < 1024 ? t : 2048 - t) << 1;
		} else {
			alpha = 2048;
		}
		if (disableFlicker || matchesStaticLightOverride) {
			alpha = 2048;
		}
		this.alpha = (float) (this.alphaMax + (alpha * this.alphaMin >> 11)) / 2048.0F;
		@Pc(123) float colorScale = this.alpha / 255.0F;
		this.diffuse[0] = (float) (this.color >> 16 & 0xFF) * colorScale;
		this.diffuse[2] = colorScale * (float) (this.color & 0xFF);
		this.diffuse[1] = (float) (this.color >> 8 & 0xFF) * colorScale;
	}

	@OriginalMember(owner = "client!gi", name = "c", descriptor = "(I)V")
	private void initAnimationPreset() {
		@Pc(4) int preset = this.animationPreset;
		if (preset == 2) {
			this.alphaMin = 2048;
			this.alphaMax = 0;
			this.flickerType = 1;
			this.flickerSpeed = 2048;
		} else if (preset == 3) {
			this.alphaMax = 0;
			this.flickerSpeed = 4096;
			this.flickerType = 1;
			this.alphaMin = 2048;
		} else if (preset == 4) {
			this.alphaMax = 0;
			this.alphaMin = 2048;
			this.flickerType = 4;
			this.flickerSpeed = 2048;
		} else if (preset == 5) {
			this.flickerType = 4;
			this.alphaMin = 2048;
			this.flickerSpeed = 8192;
			this.alphaMax = 0;
		} else if (preset == 12) {
			this.alphaMin = 2048;
			this.flickerType = 2;
			this.flickerSpeed = 2048;
			this.alphaMax = 0;
		} else if (preset == 13) {
			this.flickerSpeed = 8192;
			this.alphaMin = 2048;
			this.flickerType = 2;
			this.alphaMax = 0;
		} else if (preset == 10) {
			this.alphaMin = 512;
			this.flickerType = 3;
			this.alphaMax = 1536;
			this.flickerSpeed = 2048;
		} else if (preset == 11) {
			this.flickerType = 3;
			this.flickerSpeed = 4096;
			this.alphaMin = 512;
			this.alphaMax = 1536;
		} else if (preset == 6) {
			this.alphaMin = 768;
			this.alphaMax = 1280;
			this.flickerType = 3;
			this.flickerSpeed = 2048;
		} else if (preset == 7) {
			this.alphaMin = 768;
			this.alphaMax = 1280;
			this.flickerSpeed = 4096;
			this.flickerType = 3;
		} else if (preset == 8) {
			this.flickerSpeed = 2048;
			this.flickerType = 3;
			this.alphaMin = 1024;
			this.alphaMax = 1024;
		} else if (preset == 9) {
			this.flickerSpeed = 4096;
			this.alphaMax = 1024;
			this.alphaMin = 1024;
			this.flickerType = 3;
		} else if (preset == 14) {
			this.flickerSpeed = 2048;
			this.alphaMax = 1280;
			this.flickerType = 1;
			this.alphaMin = 768;
		} else if (preset == 15) {
			this.alphaMin = 512;
			this.flickerSpeed = 4096;
			this.alphaMax = 1536;
			this.flickerType = 1;
		} else if (preset == 16) {
			this.flickerSpeed = 8192;
			this.alphaMax = 1792;
			this.flickerType = 1;
			this.alphaMin = 256;
		} else {
			this.flickerSpeed = 2048;
			this.alphaMax = 0;
			this.alphaMin = 2048;
			this.flickerType = 0;
		}
	}
}
