package se.kth.adanoo.lab4.model;

/**
 * The {@code ColorInverter} class performs a color inversion transformation
 * on an image represented as a 2D pixel matrix.
 * <p>
 * Inversion is done by subtracting each color component (red, green, blue)
 * from 255, effectively producing a photographic negative. The alpha channel
 * remains unchanged.
 * </p>
 */
public class ColorInverter implements IPixelTransformer {

    /**
     * Inverts the colors of the provided image.
     *
     * @param originalPixels the original image in ARGB format
     * @return a new 2D array of pixels with inverted colors
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {
        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] inverted = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int pixel = originalPixels[row][col];

                int red   = PixelTools.getRed(pixel);
                int green = PixelTools.getGreen(pixel);
                int blue  = PixelTools.getBlue(pixel);
                int alpha = PixelTools.getAlpha(pixel);

                int invRed   = 255 - red;
                int invGreen = 255 - green;
                int invBlue  = 255 - blue;

                inverted[row][col] = PixelTools.createPixel(alpha, invRed, invGreen, invBlue);
            }
        }

        return inverted;
    }
}
