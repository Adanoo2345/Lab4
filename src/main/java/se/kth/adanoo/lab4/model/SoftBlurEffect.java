package se.kth.adanoo.lab4.model;

/**
 * The {@code SoftBlurEffect} class applies a weighted blur filter to an image
 * by averaging the color values of each pixel and its eight neighbors.
 * <p>
 * A Gaussian-like kernel is used to give more weight to the center and
 * adjacent pixels:
 *
 * <pre>
 * 1 2 1
 * 2 4 2
 * 1 2 1
 * </pre>
 *
 * The resulting effect is a softened, slightly blurred image. The alpha
 * (transparency) channel is left unchanged.
 */
public class SoftBlurEffect implements IPixelTransformer {

    private static final int[][] WEIGHTS = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };

    private static final int KERNEL_SUM = 16;

    /**
     * Applies a weighted blur to the input pixel matrix.
     *
     * @param originalPixels the original ARGB image as a 2D array
     * @return a blurred version of the image
     */
    @Override
    public int[][] processImage(int[][] originalPixels) {
        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] blurredPixels = new int[height][width];

        for (int row = 1; row < height - 1; row++) {
            for (int col = 1; col < width - 1; col++) {

                double sumRed = 0;
                double sumGreen = 0;
                double sumBlue = 0;

                int alpha = PixelTools.getAlpha(originalPixels[row][col]);

                // Apply 3x3 kernel
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int neighborRow = row + dy;
                        int neighborCol = col + dx;

                        int pixel = originalPixels[neighborRow][neighborCol];
                        int weight = WEIGHTS[dy + 1][dx + 1];

                        sumRed   += PixelTools.getRed(pixel) * weight;
                        sumGreen += PixelTools.getGreen(pixel) * weight;
                        sumBlue  += PixelTools.getBlue(pixel) * weight;
                    }
                }

                int red   = (int) Math.round(sumRed / KERNEL_SUM);
                int green = (int) Math.round(sumGreen / KERNEL_SUM);
                int blue  = (int) Math.round(sumBlue / KERNEL_SUM);

                blurredPixels[row][col] = PixelTools.createPixel(alpha, red, green, blue);
            }
        }

        // Copy border pixels unchanged
        for (int x = 0; x < width; x++) {
            blurredPixels[0][x] = originalPixels[0][x];
            blurredPixels[height - 1][x] = originalPixels[height - 1][x];
        }
        for (int y = 0; y < height; y++) {
            blurredPixels[y][0] = originalPixels[y][0];
            blurredPixels[y][width - 1] = originalPixels[y][width - 1];
        }

        return blurredPixels;
    }
}
