package se.kth.adanoo.lab4.model;

/**
 * The {@code PixelTools} class provides utility methods for extracting and composing
 * ARGB (Alpha, Red, Green, Blue) values from pixel data represented as 32-bit integers.
 * <p>
 * Pixels are encoded in the format: {@code 0xAARRGGBB}, where each component occupies 8 bits.
 * These methods allow manipulation of individual color components, as well as safe value
 * clamping and transparency checks.
 * </p>
 */

public class PixelTools {
    /**
     * Extracts the red color component (0–255) from the given ARGB pixel.
     *
     * @param pixel the ARGB pixel as an int
     * @return the red component value (0–255)
     */
    public static int getRed(int pixel) {
        return (pixel >> 16) & 0xFF;
    }

    /**
     * Extracts the green color component (0–255) from the given ARGB pixel.
     *
     * @param pixel the ARGB pixel as an int
     * @return the green component value (0–255)
     */
    public static int getGreen(int pixel) {
        return (pixel >> 8) & 0xFF;
    }

    /**
     * Extracts the blue color component (0–255) from the given ARGB pixel.
     *
     * @param pixel the ARGB pixel as an int
     * @return the blue component value (0–255)
     */
    public static int getBlue(int pixel) {
        return pixel & 0xFF;
    }

    /**
     * Extracts the alpha (transparency) component (0–255) from the given ARGB pixel.
     *
     * @param pixel the ARGB pixel as an int
     * @return the alpha component value (0–255)
     */
    public static int getAlpha(int pixel) {
        return (pixel >> 24) & 0xFF;
    }

    /**
     * Creates a single ARGB pixel from individual component values.
     * <p>
     * Each component (alpha, red, green, blue) should be in the 0–255 range.
     * </p>
     *
     * @param alpha the alpha component (0–255)
     * @param red the red component (0–255)
     * @param green the green component (0–255)
     * @param blue the blue component (0–255)
     * @return the combined ARGB pixel as an int
     */
    public static int createPixel(int alpha, int red, int green, int blue) {
        return ((alpha & 0xFF) << 24)
                | ((red & 0xFF) << 16)
                | ((green & 0xFF) << 8)
                | (blue & 0xFF);
    }

    /**
     * Clamps a given value between the specified minimum and maximum.
     * <p>
     * Useful for ensuring that color values remain within the 0–255 range.
     * </p>
     *
     * @param value the value to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return the clamped value
     */
    public static int clamp(int value, int min, int max) {
        return (value < min) ? min : (Math.min(value, max));
    }

    /**
     * Checks whether the given pixel is fully transparent.
     *
     * @param pixel the ARGB pixel to check
     * @return {@code true} if the alpha component is 0, otherwise {@code false}
     */
    public static boolean isTransparent(int pixel) {
        return getAlpha(pixel) == 0;
    }
}
