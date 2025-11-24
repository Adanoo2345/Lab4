package se.kth.adanoo.lab4.util;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {

    private Controller controller;
    private final Stage stage;

    private final ImageView imageView = new ImageView();

    private final MenuItem loadItem = new MenuItem("Load Image");
    private final MenuItem saveItem = new MenuItem("Save Image");
    private final MenuItem revertItem = new MenuItem("Revert");

    private final MenuItem grayItem = new MenuItem("Grayscale");
    private final MenuItem blurItem = new MenuItem("Blur");
    private final MenuItem sharpenItem = new MenuItem("Sharpen");

    public View(Stage stage) {
        this.stage = stage;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        connectActions();
    }

    private void connectActions() {
        loadItem.setOnAction(e -> controller.onLoadImage());
        saveItem.setOnAction(e -> controller.onSaveImage());
        revertItem.setOnAction(e -> controller.onRevert());

        grayItem.setOnAction(e -> controller.onGrayScale());
        blurItem.setOnAction(e -> controller.onBlur());
        sharpenItem.setOnAction(e -> controller.onSharpen());
    }

    public void show() {
        BorderPane root = new BorderPane();

        Menu fileMenu = new Menu("File", null, loadItem, saveItem, revertItem);
        Menu processMenu = new Menu("Process", null, grayItem, blurItem, sharpenItem);

        MenuBar menuBar = new MenuBar(fileMenu, processMenu);

        root.setTop(menuBar);
        root.setCenter(imageView);

        stage.setScene(new Scene(root, 900, 600));
        stage.setTitle("Lab 4");
        stage.show();
    }

    public void displayImage(Image img) {
        imageView.setImage(img);
    }
}
