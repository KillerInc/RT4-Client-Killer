package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.Component;
import java.awt.*;

@OriginalClass("client!vk")
public abstract class FrameBuffer {

	@OriginalMember(owner = "client!vk", name = "e", descriptor = "[I")
	public int[] pixels;

	@OriginalMember(owner = "client!vk", name = "g", descriptor = "Ljava/awt/Image;")
	protected Image image;

	@OriginalMember(owner = "client!vk", name = "i", descriptor = "I")
	protected int height;

	@OriginalMember(owner = "client!vk", name = "k", descriptor = "I")
	protected int width;

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(IIZLjava/awt/Component;)Lclient!vk;")
	public static FrameBuffer create(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(3) Component component) {
		try {
			@Pc(16) FrameBuffer frameBuffer = new BufferedImageFrameBuffer();
			frameBuffer.init(width, height, component);
			return frameBuffer;
		} catch (@Pc(25) Throwable ex) {
			@Pc(29) ImageProducerFrameBuffer fallback = new ImageProducerFrameBuffer();
			fallback.init(width, height, component);
			return fallback;
		}
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(IILjava/awt/Graphics;I)V")
	public abstract void draw(@OriginalArg(2) Graphics graphics);

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(I)V")
	public final void makeTarget() {
		SoftwareRaster.setSize(this.pixels, this.width, this.height);
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(IIIILjava/awt/Graphics;I)V")
	public abstract void drawAt(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(3) int width, @OriginalArg(4) Graphics graphics, @OriginalArg(5) int height);

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(IZILjava/awt/Component;)V")
	public abstract void init(@OriginalArg(0) int width, @OriginalArg(2) int height, @OriginalArg(3) Component component);
}
