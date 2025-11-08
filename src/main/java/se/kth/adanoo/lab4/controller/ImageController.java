package se.kth.adanoo.lab4.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.adanoo.lab4.model.ImageModel;
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

    public void onLoadImage() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Image");
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files",
                            "*.png","*.jpg","*.jpeg","*.bmp","*.gif")
            );
            File file = fc.showOpenDialog(stage);
            if (file == null) return;

            Image fxImage = FileIO.loadImage(file);
            int[][] pixels = ImagePixelsConverter.imageToPixels(fxImage);
            model.setOriginalAndCurrent(pixels);

            Image show = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
            view.showImage(show, "Inläst: " + file.getName() +
                    "  (" + (int)fxImage.getWidth() + "×" + (int)fxImage.getHeight() + ")");
            view.setSaveEnabled(true);
        } catch (Exception ex) {
            showError("Kunde inte läsa bilden.", ex);
        }
    }

    public void onSaveImage() {
        try {
            if (model.getCurrentPixels() == null) return;
            FileChooser fc = new FileChooser();
            fc.setTitle("Save Image As");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fc.showSaveDialog(stage);
            if (file == null) return;

            Image toSave = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
            FileIO.saveImage(toSave, file);
        } catch (Exception ex) {
            showError("Kunde inte spara bilden.", ex);
        }
    }

    public void onExit() { stage.close(); }

    private void showError(String msg, Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg + "\n" + ex.getMessage(), ButtonType.OK);
        a.initOwner(stage);
        a.setHeaderText("Fel");
        a.showAndWait();
    }


}
