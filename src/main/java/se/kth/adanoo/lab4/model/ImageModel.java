package se.kth.adanoo.lab4.model;

public class ImageModel {
    private int[][] originalPixels;
    private int[][] currentPixels;

    public void setOriginalAndCurrent(int[][] pixels) {
        this.originalPixels = deepCopy(pixels);
        this.currentPixels = deepCopy(pixels);
    }

    public int[][] getCurrentPixels() { return currentPixels; }

    private static int[][] deepCopy(int[][] src) {
        if (src == null) return null;
        int[][] dst = new int[src.length][];
        for (int y = 0; y < src.length; y++) dst[y] = src[y].clone();
        return dst;
    }
}
