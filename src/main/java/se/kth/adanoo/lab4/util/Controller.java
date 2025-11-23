package se.kth.adanoo.lab4.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.adanoo.lab4.model.ImageModel;
import se.kth.adanoo.lab4.util.FileIO;
import se.kth.adanoo.lab4.util.ImagePixelsConverter;

import java.io.File;

public class Controller {

    private final Stage stage;
    private final ImageModel model;
    private final View view;

    public Controller(Stage stage, ImageModel model, View view) {
        this.stage = stage;
        this.model = model;
        this.view = view;
    }

    public void onLoadImage() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Image");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image files",
                            "*.png", "*.jpg", "*.jpeg", "*.bmp")
            );

            File file = fc.showOpenDialog(stage);
            if (file == null) return;

            Image img = FileIO.loadImage(file);
            int[][] px = ImagePixelsConverter.imageToPixels(img);

            model.setOriginalAndCurrent(px);
            view.displayImage(img);

        } catch (Exception e) {
            showError("Could not load image\n" + e.getMessage());
        }
    }

    public void onSaveImage() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save Image");

            File file = fc.showSaveDialog(stage);
            if (file == null) return;

            Image img = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
            FileIO.saveImage(img, file);

        } catch (Exception e) {
            showError("Could not save image\n" + e.getMessage());
        }
    }

    public void onRevert() {
        model.setOriginalAndCurrent(model.getCurrentPixels());
        updateView();
    }

    public void onGrayScale() { updateView(); }
    public void onBlur()      { updateView(); }
    public void onSharpen()   { updateView(); }

    private void updateView() {
        Image img = ImagePixelsConverter.pixelsToImage(model.getCurrentPixels());
        view.displayImage(img);
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).show();
    }
}
