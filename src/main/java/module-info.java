module com.example.figures_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.figures_desktop to javafx.fxml;
    exports com.example.figures_desktop;
    exports controllers;
    opens controllers to javafx.fxml;
}