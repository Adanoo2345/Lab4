package se.kth.adanoo.lab4.model;

/**
 * Interface for image transformation operations.
 * <p>
 * Implementing classes define a specific algorithm that processes a two-dimensional
 * pixel matrix representing an image. Each transformation produces a new matrix
 * without modifying the original.
 * </p>
 */
public interface IPixelTransformer {

    /**
     * Applies a transformation to the input pixel matrix and returns a new matrix.
     *
     * @param originalPixels the original image represented as a 2D array of ARGB pixels
     * @return a 2D array of pixels after the transformation has been applied
     */
   abstract int[][] processImage(int[][] originalPixels);
}
