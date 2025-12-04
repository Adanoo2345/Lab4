package se.kth.adanoo.lab4.model;

public class ImageModel {
    private int[][] originalPixels;
    private int[][] currentPixels;

    // Sätt original + current när en bild laddas
    public void setOriginalAndCurrent(int[][] pixels) {
        this.originalPixels = deepCopy(pixels);
        this.currentPixels = deepCopy(pixels);
    }

    // Returnerar nuvarande pixelmatris
    public int[][] getCurrentPixels() {
        return currentPixels;
    }

    // Uppdaterar currentPixels (används efter filter)
    public void setCurrentPixels(int[][] pixels) {
        this.currentPixels = deepCopy(pixels);
    }

    // Återställ bilden till original
    public int[][] restoreOriginal() {
        if (originalPixels == null) return null;
        this.currentPixels = deepCopy(originalPixels);
        return currentPixels;
    }

    public int[][] getHistogram() {
        if (currentPixels == null) return null;

        int[][] hist = new int[256][3]; // [intensity][colorChannel]

        for (int y = 0; y < currentPixels.length; y++) {
            for (int x = 0; x < currentPixels[0].length; x++) {
                int pixel = currentPixels[y][x];

                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = pixel & 0xFF;

                hist[r][0]++;
                hist[g][1]++;
                hist[b][2]++;
            }
        }
        return hist;
    }

    // Utility: deepCopy av pixelmatris
    private static int[][] deepCopy(int[][] src) {
        if (src == null) return null;
        int[][] dst = new int[src.length][];
        for (int y = 0; y < src.length; y++)
            dst[y] = src[y].clone();
        return dst;
    }

    // Extra valbart: kan användas i controller om du vill
    public boolean hasImage() {
        return currentPixels != null;
    }
}
