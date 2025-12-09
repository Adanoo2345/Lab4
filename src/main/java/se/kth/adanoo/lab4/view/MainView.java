package se.kth.adanoo.lab4.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import se.kth.adanoo.lab4.controller.ImageController;

public class MainView {

    private final BorderPane root = new BorderPane();

    private final ImageView imageView = new ImageView();
    private final HistogramView histogramView = new HistogramView();

    // FILE MENU
    private final MenuItem miOpen = new MenuItem("Open…");
    private final MenuItem miSaveAs = new MenuItem("Save As…");
    private final MenuItem miExit = new MenuItem("Exit");

    // PROCESS MENU
    private final MenuItem miGray = new MenuItem("Gray scale");
    private final MenuItem miBlur = new MenuItem("Soft blur");
    private final MenuItem miSharpen = new MenuItem("Sharpen");
    private final MenuItem miInvert = new MenuItem("Invert colors");
    private final MenuItem miRestore = new MenuItem("Restore original");
    private final MenuItem miWindow = new MenuItem("Window/Level");

    private final Label status = new Label("Ingen bild inläst.");

    public MainView() {

        // ---------------- FILE MENU ----------------
        Menu fileMenu = new Menu("File");
        miSaveAs.setDisable(true);
        fileMenu.getItems().addAll(miOpen, miSaveAs, new SeparatorMenuItem(), miExit);

        // ---------------- PROCESS MENU ----------------
        Menu processMenu = new Menu("Process");
        disableProcessMenu();
        processMenu.getItems().addAll(
                miGray, miBlur, miSharpen, miInvert,
                new SeparatorMenuItem(), miWindow,
                miRestore
        );

        MenuBar bar = new MenuBar(fileMenu, processMenu);

        // ---------------- IMAGE AREA ----------------
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(900);
        imageView.setFitHeight(750);

        HBox center = new HBox(15, histogramView, imageView);
        center.setPadding(new Insets(8));

        // ---------------- LAYOUT ----------------
        root.setTop(bar);
        root.setCenter(center);
        root.setBottom(status);
    }

    // ------------------------------------------------
    // CONTROLLER-KOPPLINGAR
    // ------------------------------------------------
    public void setController(ImageController c) {
        miOpen.setOnAction(e -> c.onLoadImage());
        miSaveAs.setOnAction(e -> c.onSaveImage());
        miExit.setOnAction(e -> c.onExit());

        miGray.setOnAction(e -> c.onGrayScaleSelected());
        miBlur.setOnAction(e -> c.onSoftBlurSelected());
        miSharpen.setOnAction(e -> c.onSharpenSelected());
        miInvert.setOnAction(e -> c.onInvertSelected());
        miWindow.setOnAction(e -> c.onWindowLevelSelected());
        miRestore.setOnAction(e -> c.onRestoreOriginal());
    }

    // ------------------------------------------------
    // PUBLIC FÖR CONTROLLER
    // ------------------------------------------------
    public BorderPane getRoot() { return root; }

    public void showImage(Image img, String text) {
        imageView.setImage(img);
        status.setText(text);
    }

    public void updateHistogram(int[][] hist) {
        if (hist == null) histogramView.clear();
        else histogramView.updateView(hist);
    }

    public void setSaveEnabled(boolean enabled) {
        miSaveAs.setDisable(!enabled);
    }

    public void enableProcessMenu() {
        miGray.setDisable(false);
        miBlur.setDisable(false);
        miSharpen.setDisable(false);
        miInvert.setDisable(false);
        miWindow.setDisable(false);
        miRestore.setDisable(false);
    }

    public void disableProcessMenu() {
        miGray.setDisable(true);
        miBlur.setDisable(true);
        miSharpen.setDisable(true);
        miInvert.setDisable(true);
        miWindow.setDisable(true);
        miRestore.setDisable(true);
    }
}
