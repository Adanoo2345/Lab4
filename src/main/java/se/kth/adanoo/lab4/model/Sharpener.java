package se.kth.adanoo.lab4.model;

/**
 * The {@code Sharpener} class enhances edges in a grayscale image by applying
 * a simplified unsharp mask algorithm.
 * <p>
 * It creates a blurred version of the original image and calculates the
 * difference between the original and the blurred image. This difference
 * (the high-frequency component) is then added back to the original image
 * to increase contrast and highlight edges.
 * </p>
 * <p>
 * This processor is intended for grayscale images only.
 * </p>
 */
public class Sharpener implements IPixelTransformer {

    private final SoftBlurEffect blurProcessor = new SoftBlurEffect();

    /**
     * Enhances edges in a grayscale image using a subtractive method.
     *
     * @param originalPixels a 2D array of grayscale pixels in ARGB format
     * @return a sharpened version of the image
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {
        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] blurredPixels = blurProcessor.processImage(originalPixels);
        int[][] sharpenedPixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int original = originalPixels[y][x];
                int blurred = blurredPixels[y][x];

                int alpha = PixelTools.getAlpha(original);

                // Since image is in grayscale, R = G = B
                int origGray = PixelTools.getRed(original);   // Any channel works
                int blurGray = PixelTools.getRed(blurred);

                int detail = origGray - blurGray;
                int enhancedGray = PixelTools.clamp(origGray + detail, 0, 255);

                sharpenedPixels[y][x] = PixelTools.createPixel(alpha, enhancedGray, enhancedGray, enhancedGray);
            }
        }

        return sharpenedPixels;
    }
}
