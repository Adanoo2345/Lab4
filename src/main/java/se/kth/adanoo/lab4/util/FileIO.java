package se.kth.adanoo.lab4.util;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * FileIO hanterar in- och utläsning av bildfiler.
 * Kräver endast java.desktop (AWT) – ingen javafx.embed.swing.
 */
public class FileIO {

    /**
     * Läser in en bild från fil och returnerar den som ett JavaFX Image-objekt.
     */
    public static Image loadImage(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("Filen finns inte: " + file);
        }
        // JavaFX Image klarar direkt att läsa via URI
        return new Image(file.toURI().toString());
    }

    /**
     * Sparar en JavaFX Image till en fil i PNG-format.
     * Vi gör detta genom att läsa pixeldata från Image och
     * skriva till en BufferedImage med ImageIO – utan javafx.embed.swing.
     */
    public static void saveImage(Image image, File file) throws IOException {
        if (image == null) {
            throw new IOException("Ingen bild att spara.");
        }
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        javafx.scene.image.PixelReader reader = image.getPixelReader();
        if (reader == null) {
            throw new IOException("Kunde inte läsa pixeldata från bilden.");
        }

        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] buffer = new int[width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = reader.getArgb(x, y);
                bimg.setRGB(x, y, argb);
            }
        }

        if (!ImageIO.write(bimg, "png", file)) {
            throw new IOException("Kunde inte skriva PNG (ImageIO returnerade false).");
        }
    }
}
