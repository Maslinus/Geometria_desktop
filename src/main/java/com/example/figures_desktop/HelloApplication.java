package com.example.figures_desktop;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final AppModel appModel = new AppModel();
    private static HelloApplication instance;

    public static HelloApplication getInstance() {
        return instance;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 711, 452);
        stage.setTitle("Геометрические фигуры");
        stage.setScene(scene);
        stage.show();
        instance = this;
    }

    public AppModel getAppModel() {
        return appModel;
    }

    public static void main(String[] args) {
        launch();
    }
}