package rt4;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Build-time smoke tests for the exact Modern UI SVG rasterization path.
 */
public final class ModernSvgAssetVerifier {
    private ModernSvgAssetVerifier() {
    }

    public static void main(String[] args) throws Exception {
        String[] resources =
            args.length == 0
                ? new String[] {
                    "/ui/killer-modern/main-menu/scroll.svg",
                    "/ui/killer-modern/main-menu/logo.svg"
                }
                : args;

        for (String resource : resources) {
            verifyResource(resource);
        }
    }

    private static void verifyResource(String resource) throws Exception {
        byte[] data = readResource(resource);
        String source =
            new String(data, StandardCharsets.UTF_8).toLowerCase(Locale.ROOT);

        require(source.contains("<svg"), resource + ": missing SVG root");
        require(source.contains("viewbox="), resource + ": missing viewBox");
        require(!source.contains("<image"), resource + ": embedded raster image is not allowed");

        if (resource.endsWith("/scroll.svg")) {
            require(source.contains("<lineargradient"), "scroll: missing linear gradients");
            require(source.contains("<radialgradient"), "scroll: missing radial gradients");
            require(source.contains("<filter"), "scroll: missing SVG filters");
            require(source.contains("fegaussianblur"), "scroll: missing Gaussian blur");
            require(source.contains("transform="), "scroll: missing transforms");
            require(source.contains("xlink:href=\"#"), "scroll: missing internal references");
            verifyRaster(data, 320, 340);
            verifyRaster(data, 640, 680);
        } else if (resource.endsWith("/logo.svg")) {
            require(count(source, "<path") >= 20, "logo: expected detailed vector paths");
            verifyRaster(data, 480, 166);
            verifyRaster(data, 960, 332);
        } else {
            verifyRaster(data, 320, 240);
        }

        System.out.println(
            "Modern SVG verification passed: "
                + resource
                + " bytes="
                + data.length
        );
    }

    private static int count(String source, String token) {
        int count = 0;
        int offset = 0;
        while ((offset = source.indexOf(token, offset)) != -1) {
            count++;
            offset += token.length();
        }
        return count;
    }

    private static void verifyRaster(byte[] data, int width, int height)
        throws Exception {
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
            visible > total / 200,
            "SVG rendered effectively blank at "
                + width + "x" + height
        );

        double coverage =
            total == 0 ? 0.0 : (visible * 100.0) / total;

        System.out.println(
            "  raster "
                + width + "x" + height
                + " visiblePixels=" + visible
                + " coverage="
                + String.format(Locale.ROOT, "%.2f%%", coverage)
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
