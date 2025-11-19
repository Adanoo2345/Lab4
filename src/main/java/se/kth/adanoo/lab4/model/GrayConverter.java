package se.kth.adanoo.lab4.model;

/**
 * The {@code GrayConverter} class is responsible for converting a color image
 * into a grayscale version. It processes a 2D array of pixels where each pixel
 * is represented as a packed ARGB integer.
 * <p>
 * Each pixel's red, green, and blue components are averaged to compute a single
 * intensity value, which is then applied equally to all three color channels.
 * The alpha (transparency) channel remains unchanged.
 * </p>
 *
 * This class implements the {@link IPixelTransformer} interface.
 */

public class GrayConverter implements IPixelTransformer {

    /**
     * Converts the input pixel matrix to grayscale.
     *
     * @param originalPixels a 2D array representing the original image pixels
     *                       in ARGB format (int-packed).
     * @return a new 2D pixel array where each pixel has equal red, green, and blue
     *         components, creating a grayscale image.
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {
        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] grayPixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = originalPixels[y][x];

                int red = PixelTools.getRed(pixel);
                int green = PixelTools.getGreen(pixel);
                int blue = PixelTools.getBlue(pixel);
                int alpha = PixelTools.getAlpha(pixel);

                // Calculate the average of RGB components
                int average = (int) Math.round((red + green + blue) / 3.0);

                // Reconstruct pixel with grayscale value
                grayPixels[y][x] = PixelTools.createPixel(alpha, average, average, average);
            }
        }

        return grayPixels;
    }
}

