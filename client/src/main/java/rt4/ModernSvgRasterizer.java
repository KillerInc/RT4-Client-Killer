package rt4;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * SVG -> BufferedImage adapter for UI vectors.
 */
public final class ModernSvgRasterizer {
    private static final int MAX_SVG_BYTES = 4 * 1024 * 1024;

    private ModernSvgRasterizer() {
    }

    public static BufferedImage rasterize(byte[] data, int width, int height) throws Exception {
        if (data == null || data.length == 0 || data.length > MAX_SVG_BYTES) {
            throw new IllegalArgumentException("invalid SVG size");
        }
        if (width < 1 || height < 1 || width > 4096 || height > 4096) {
            throw new IllegalArgumentException("invalid SVG render dimensions");
        }

        // Style packs are local mods, but they should still be self-contained.
        // Reject external-resource/script constructs before handing data to Batik.
        String source = new String(data, StandardCharsets.UTF_8).toLowerCase(Locale.ROOT);
        if (source.contains("<!doctype")
            || source.contains("<!entity")
            || source.contains("<script")
            || source.contains("http://")
            || source.contains("https://")
            || source.contains("file:")) {
            throw new IllegalArgumentException("SVG contains external or executable content");
        }

        BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
        transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, (float) width);
        transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, (float) height);
        transcoder.transcode(new TranscoderInput(new ByteArrayInputStream(data)), null);
        BufferedImage image = transcoder.getImage();
        if (image == null) {
            throw new IllegalArgumentException("SVG produced no image");
        }
        return image;
    }

    private static final class BufferedImageTranscoder extends ImageTranscoder {
        private BufferedImage image;

        @Override
        public BufferedImage createImage(int width, int height) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        @Override
        public void writeImage(BufferedImage image, TranscoderOutput output) {
            this.image = image;
        }

        private BufferedImage getImage() {
            return image;
        }
    }
}
