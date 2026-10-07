package rt4;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Build-time smoke test for the exact Modern UI SVG rasterization path.
 *
 * This deliberately uses ModernSvgRasterizer (Apache Batik), not a separate
 * image tool, so CI proves the same parser/scaler used by the live client can
 * handle the shipped vector artwork.
 */
public final class ModernSvgAssetVerifier {
    private ModernSvgAssetVerifier() {
    }

    public static void main(String[] args) throws Exception {
        String resource =
            args.length == 0
                ? "/ui/killer-modern/main-menu/scroll.svg"
                : args[0];

        byte[] data = readResource(resource);
        String source =
            new String(data, StandardCharsets.UTF_8).toLowerCase();

        // This scroll intentionally exercises normal modern SVG features that
        // our renderer must support as a single scalable source file.
        require(source.contains("<svg"), "missing SVG root");
        require(source.contains("viewbox="), "missing viewBox");
        require(source.contains("<lineargradient"), "missing linear gradients");
        require(source.contains("<radialgradient"), "missing radial gradients");
        require(source.contains("<filter"), "missing SVG filters");
        require(source.contains("fegaussianblur"), "missing Gaussian blur");
        require(source.contains("transform="), "missing transforms");
        require(
            source.contains("xlink:href=\"#"),
            "missing internal xlink references"
        );

        verifyRaster(data, 236, 292);
        verifyRaster(data, 472, 584);
        verifyRaster(data, 535, 505);

        System.out.println(
            "Modern SVG verification passed: "
                + resource
                + " (gradients, filters, transforms, internal references)"
        );
    }

    private static void verifyRaster(
        byte[] data,
        int width,
        int height
    ) throws Exception {
        BufferedImage image =
            ModernSvgRasterizer.rasterize(data, width, height);

        require(image.getWidth() == width, "wrong raster width");
        require(image.getHeight() == height, "wrong raster height");

        int visible = 0;
        int total = width * height;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    visible++;
                }
            }
        }

        require(
            visible > total / 20,
            "SVG rendered effectively blank at "
                + width + "x" + height
        );

        System.out.println(
            "  raster "
                + width + "x" + height
                + " visiblePixels=" + visible
        );
    }

    private static byte[] readResource(String name) throws Exception {
        try (InputStream input =
                 ModernSvgAssetVerifier.class.getResourceAsStream(name)) {
            if (input == null) {
                throw new IllegalStateException(
                    "Missing SVG resource: " + name
                );
            }

            ByteArrayOutputStream output =
                new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            return output.toByteArray();
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
