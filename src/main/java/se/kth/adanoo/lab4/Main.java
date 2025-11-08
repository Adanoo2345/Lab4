package se.kth.adanoo.lab4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.adanoo.lab4.controller.ImageController;
import se.kth.adanoo.lab4.model.ImageModel;
import se.kth.adanoo.lab4.view.MainView;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        ImageModel model = new ImageModel();
        MainView view = new MainView();
        ImageController controller = new ImageController(stage, model, view);
        view.setController(controller);

        Scene scene = new Scene(view.getRoot(), 1100, 700);
        stage.setTitle("Lab 4 – Bildbehandling (File/Open)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}
