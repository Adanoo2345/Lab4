package se.kth.adanoo.lab4.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.adanoo.lab4.model.*;
import se.kth.adanoo.lab4.util.FileIO;
import se.kth.adanoo.lab4.util.ImagePixelsConverter;
import se.kth.adanoo.lab4.view.MainView;

import java.io.File;

public class ImageController {

    private final Stage stage;
    private final ImageModel model;
    private final MainView view;

    public ImageController(Stage stage, ImageModel model, MainView view) {
        this.stage = stage;
        this.model = model;
        this.view = view;
    }

    // ---------------------------------------------------------
    // LOAD
    // ---------------------------------------------------------
    public void onLoadImage() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Image");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image files",
                            "*.png","*.jpg","*.jpeg","*.bmp","*.gif")
            );

            File file = fc.showOpenDialog(stage);
            if (file == null) return;

            Image fxImg = FileIO.loadImage(file);
            int[][] pixels = ImagePixelsConverter.imageToPixels(fxImg);

            model.setOriginalAndCurrent(pixels);

            view.showImage(fxImg, "Loaded: " + file.getName());
            view.updateHistogram(model.getHistogram());

            view.setSaveEnabled(true);
            view.enableProcessMenu();

        } catch (Exception ex) {
            showError("Could not load image", ex);
        }
    }

    // ---------------------------------------------------------
    // SAVE
    // ---------------------------------------------------------
    public void onSaveImage() {
        try {
            if (model.getCurrentPixels() == null) return;

            FileChooser fc = new FileChooser();
            fc.setTitle("Save Image As");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));

            File file = fc.showSaveDialog(stage);
            if (file == null) return;

            Image fxImg = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
            FileIO.saveImage(fxImg, file);

        } catch (Exception ex) {
            showError("Could not save image", ex);
        }
    }

    // ---------------------------------------------------------
    // EXIT
    // ---------------------------------------------------------
    public void onExit() {
        stage.close();
    }

    // ---------------------------------------------------------
    // FILTER: Gray scale
    // ---------------------------------------------------------
    public void onGrayScaleSelected() {
        applyTransformer(new GrayConverter(), "Gray scale applied");
    }

    // ---------------------------------------------------------
    // FILTER: Soft blur
    // ---------------------------------------------------------
    public void onSoftBlurSelected() {
        applyTransformer(new SoftBlurEffect(), "Soft blur applied");
    }

    // ---------------------------------------------------------
    // FILTER: Sharpen
    // ---------------------------------------------------------
    public void onSharpenSelected() {
        applyTransformer(new Sharpener(), "Sharpen applied");
    }

    // ---------------------------------------------------------
    // FILTER: Invert colors
    // ---------------------------------------------------------
    public void onInvertSelected() {
        applyTransformer(new ColorInverter(), "Inverted colors");
    }

    // ---------------------------------------------------------
    // RESTORE ORIGINAL
    // ---------------------------------------------------------
    public void onRestoreOriginal() {
        int[][] restored = model.restoreOriginal();
        Image fx = ImagePixelsConverter.pixelsToImage(restored);
        view.showImage(fx, "Original restored");
        view.updateHistogram(model.getHistogram());
    }

    // ---------------------------------------------------------
    // GEMENSAM TRANSFORMER-METOD
    // ---------------------------------------------------------
    private void applyTransformer(IPixelTransformer t, String label) {
        int[][] result = t.processImage(model.getCurrentPixels());
        model.setCurrentPixels(result);

        Image img = ImagePixelsConverter.pixelsToImage(result);
        view.showImage(img, label);
        view.updateHistogram(model.getHistogram());
    }

    // ---------------------------------------------------------
    // ERROR
    // ---------------------------------------------------------
    private void showError(String msg, Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg + "\n" + ex.getMessage(), ButtonType.OK);
        a.initOwner(stage);
        a.setHeaderText("Error");
        a.showAndWait();
    }
}
