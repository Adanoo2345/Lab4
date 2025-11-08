module se.kth.adanoo.lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens se.kth.adanoo.lab4 to javafx.fxml;
    exports se.kth.adanoo.lab4;
}