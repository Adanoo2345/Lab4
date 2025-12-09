package se.kth.adanoo.lab4.model;

/**
 * Applies a Window/Level contrast enhancement to a grayscale image.
 *
 * Window/Level is used to stretch a specific intensity range to full 0–255 contrast.
 * Pixels below the window range become black, above become white, and values inside
 * the range are linearly scaled to 0–255.
 *
 * NOTE: This transformer assumes the image is already in grayscale.
 */
public class WindowLevelTransformer implements IPixelTransformer {

    private final int level;   // center intensity
    private final int window;  // width of intensity range

    /**
     * Creates a WindowLevelTransformer with user-defined level and window.
     *
     * @param level  midpoint of the contrast range (0–255)
     * @param window width of the contrast range (typically 1–255)
     */
    public WindowLevelTransformer(int level, int window) {
        this.level = level;
        this.window = window;  // "window" controls how wide the accepted range is
    }

    @Override
    public int[][] processImage(int[][] originalPixels) {

        int height = originalPixels.length;
        int width = originalPixels[0].length;

        int[][] result = new int[height][width];

        // Determine the lower and upper intensity thresholds
        int low = level - window / 2;   // lower bound of contrast window
        int high = level + window / 2;  // upper bound of contrast window

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int p = originalPixels[y][x];

                // Extract grayscale intensity from the ARGB pixel (R = G = B)
                int r = (p >> 16) & 0xFF;
                int g = (p >> 8) & 0xFF;
                int b = p & 0xFF;

                // Average the channels to get a single intensity value
                int intensity = (r + g + b) / 3;

                int newVal;

                // Case 1: Intensity below lower bound → make pixel black
                if (intensity < low)
                    newVal = 0;

                    // Case 2: Intensity above upper bound → make pixel white
                else if (intensity > high)
                    newVal = 255;

                else {
                    // Case 3: Linearly stretch values inside [low..high] to [0..255]
                    newVal = (int) (255.0 * (intensity - low) / (high - low));
                }

                // Rebuild a grayscale pixel (same value in R, G, B channels)
                int grayPixel = (0xFF << 24) | (newVal << 16) | (newVal << 8) | newVal;

                result[y][x] = grayPixel;
            }
        }

        return result;
    }
}
