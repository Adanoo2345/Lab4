package se.kth.adanoo.lab4.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import se.kth.adanoo.lab4.controller.ImageController;

public class MainView {
    private final BorderPane root = new BorderPane();
    private final ImageView imageView = new ImageView();

    private final MenuItem miOpen = new MenuItem("Open…");
    private final MenuItem miSaveAs = new MenuItem("Save As…");
    private final MenuItem miExit = new MenuItem("Exit");
    private final Label status = new Label("Ingen bild inläst.");

    public MainView() {
        Menu menuFile = new Menu("File");
        miSaveAs.setDisable(true);
        menuFile.getItems().addAll(miOpen, miSaveAs, new SeparatorMenuItem(), miExit);
        MenuBar bar = new MenuBar(menuFile);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(600);

        StackPane center = new StackPane(imageView);
        center.setPadding(new Insets(8));

        root.setTop(bar);
        root.setCenter(center);
        status.setPadding(new Insets(6, 10, 6, 10));
        root.setBottom(status);
    }

    public void setController(ImageController c) {
        miOpen.setOnAction(e -> c.onLoadImage());
        miSaveAs.setOnAction(e -> c.onSaveImage());
        miExit.setOnAction(e -> c.onExit());
    }

    public BorderPane getRoot() { return root; }

    public void showImage(Image image, String infoText) {
        imageView.setImage(image);
        status.setText(infoText);
    }

    public void setSaveEnabled(boolean enabled) {
        miSaveAs.setDisable(!enabled);
    }
}
