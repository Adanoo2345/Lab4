package se.kth.adanoo.lab4.controller;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
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
    // LOAD IMAGE
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
    // SAVE IMAGE
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
    // EXIT APP
    // ---------------------------------------------------------
    public void onExit() {
        stage.close();
    }

    // ---------------------------------------------------------
    // FILTERS
    // ---------------------------------------------------------
    public void onGrayScaleSelected() {
        applyTransformer(new GrayConverter(), "Gray scale applied");
    }

    public void onSoftBlurSelected() {
        applyTransformer(new SoftBlurEffect(), "Soft blur applied");
    }

    public void onSharpenSelected() {
        applyTransformer(new Sharpener(), "Sharpen applied");
    }

    public void onInvertSelected() {
        applyTransformer(new ColorInverter(), "Inverted colors");
    }

    // ---------------------------------------------------------
    // WINDOW / LEVEL FILTER
    // ---------------------------------------------------------
    public void onWindowLevelSelected() {
        int level = askUserForLevel();
        if (level < 0) return;   // avbrutet

        int window = askUserForWindow();
        if (window < 0) return;  // avbrutet

        applyTransformer(
                new WindowLevelTransformer(level, window),
                "Window/Level (L=" + level + ", W=" + window + ")"
        );
    }

    // ---------------------------------------------------------
    // RESTORE ORIGINAL
    // ---------------------------------------------------------
    public void onRestoreOriginal() {
        int[][] restored = model.restoreOriginal();
        if (restored == null) return;

        Image fx = ImagePixelsConverter.pixelsToImage(restored);
        view.showImage(fx, "Original restored");
        view.updateHistogram(model.getHistogram());
    }

    // ---------------------------------------------------------
    // ASK USER: LEVEL (slider)
    // ---------------------------------------------------------
    private int askUserForLevel() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Select Level");
        dialog.setHeaderText("Choose LEVEL (0–255)");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        Slider slider = new Slider(0, 255, 75);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        Label valueLabel = new Label("Level: 75");

        slider.valueProperty().addListener((obs, oldVal, newVal) ->
                valueLabel.setText("Level: " + newVal.intValue()));

        VBox box = new VBox(10, valueLabel, slider);
        box.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(box);

        dialog.setResultConverter(btn -> {
            if (btn == okButton)
                return (int) slider.getValue();
            return null;
        });

        return dialog.showAndWait().orElse(-1);
    }

    // ---------------------------------------------------------
    // ASK USER: WINDOW (slider)
    // ---------------------------------------------------------
    private int askUserForWindow() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Select Window");
        dialog.setHeaderText("Choose WINDOW (1–255)");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        Slider slider = new Slider(1, 255, 35);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        Label valueLabel = new Label("Window: 35");

        slider.valueProperty().addListener((obs, oldVal, newVal) ->
                valueLabel.setText("Window: " + newVal.intValue()));

        VBox box = new VBox(10, valueLabel, slider);
        box.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(box);

        dialog.setResultConverter(btn -> {
            if (btn == okButton)
                return (int) slider.getValue();
            return null;
        });

        return dialog.showAndWait().orElse(-1);
    }

    // ---------------------------------------------------------
    // APPLY ANY TRANSFORMER
    // ---------------------------------------------------------
    private void applyTransformer(IPixelTransformer t, String label) {
        int[][] result = t.processImage(model.getCurrentPixels());
        model.setCurrentPixels(result);

        Image img = ImagePixelsConverter.pixelsToImage(result);
        view.showImage(img, label);
        view.updateHistogram(model.getHistogram());
    }

    // ---------------------------------------------------------
    // ERROR HANDLING
    // ---------------------------------------------------------
    private void showError(String msg, Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg + "\n" + ex.getMessage(), ButtonType.OK);
        a.initOwner(stage);
        a.setHeaderText("Error");
        a.showAndWait();
    }
}
