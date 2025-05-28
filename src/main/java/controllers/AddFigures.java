package controllers;

import com.example.figures_desktop.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.Objects;


public class AddFigures {
    @FXML
    public ComboBox<String> figureTypeBox;
    public Button addButton;
    public Button cancelButton;
    public Spinner<Integer> anglesCount;
    public AnchorPane addScene;
    public AnchorPane pointInput1;
    public AnchorPane pointInput2;
    public AnchorPane pointInput3;
    public AnchorPane pointInput4;
    public AnchorPane pointInput5;
    public AnchorPane pointInput6;
    public AnchorPane pointInput7;
    public AnchorPane pointInput8;
    public AnchorPane pointInput9;
    public AnchorPane pointInput10;
    public AnchorPane pointInput11;
    public AnchorPane pointInput12;
    public AnchorPane pointInput13;
    public AnchorPane pointInput14;
    public AnchorPane pointInput15;
    public AnchorPane center;
    public AnchorPane radius;

    @FXML
    public void initialize() {
        ObservableList<String> figureTypes = FXCollections.observableArrayList(
                "Отрезок",
                "Ломаная",
                "Окружность",
                "Многоугольник",
                "Треугольник",
                "Четырёхугольник",
                "Прямоугольник",
                "Трапеция"
        );
        figureTypeBox.setItems(figureTypes);

        int initialValue = 1;
        int minValue = 1;
        int maxValue = 15;
        int step = 1;

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue, step);
        anglesCount.setValueFactory(valueFactory);

        for (int i = 1; i <= 15; i++) {
            String name = "pointInput" + i;
            AnchorPane panel = (AnchorPane) addScene.lookup("#" + name);
            panel.setVisible(false);
        }
        center.setVisible(false);
        radius.setVisible(false);

        figureTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            center.setVisible(false);
            radius.setVisible(false);
            for (int i = 1; i <= anglesCount.getValue(); i++) {
                String name = "pointInput" + i;
                TextField x = (TextField) addScene.lookup("#" + name).lookupAll(".text-field").toArray()[0];
                TextField y = (TextField) addScene.lookup("#" + name).lookupAll(".text-field").toArray()[1];
                x.clear();
                y.clear();
            }
            switch (newValue) {
                case "Отрезок" -> {
                    anglesCount.getValueFactory().setValue(2);
                    anglesCount.setDisable(true);
                    return;
                }
                case "Окружность" -> {
                    anglesCount.getValueFactory().setValue(1);
                    anglesCount.setDisable(true);
                    pointInput1.setVisible(false);
                    center.setVisible(true);
                    radius.setVisible(true);
                    return;
                }
                case "Треугольник" -> {
                    anglesCount.getValueFactory().setValue(3);
                    anglesCount.setDisable(true);
                    return;
                }
                case "Прямоугольник", "Четырёхугольник", "Трапеция" -> {
                    anglesCount.getValueFactory().setValue(4);
                    anglesCount.setDisable(true);
                    return;
                }
                case "Многоугольник", "Ломаная" -> {
                    anglesCount.getValueFactory().setValue(3);
                    anglesCount.setDisable(false);
                    return;
                }
            }
            if(anglesCount.isDisabled()) anglesCount.setDisable(false);;
        });

        anglesCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            for (int i = 1; i <= 15; i++) {
                String name = "pointInput" + i;
                AnchorPane panel = (AnchorPane) addScene.lookup("#" + name);
                panel.setVisible(i <= newValue);
            }
        });
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            switch(figureTypeBox.getValue()){
                case "Окружность":
                    TextField x_field = (TextField) center.lookupAll(".text-field").toArray()[0];
                    TextField y_field = (TextField) center.lookupAll(".text-field").toArray()[1];
                    TextField r_field = (TextField) radius.lookup(".text-field");
                    HelloApplication.getInstance().getAppModel().figures.add(
                    new Circle(new Point2D(new double[] { Integer.parseInt(x_field.getText()), Integer.parseInt(y_field.getText()) }), Double.parseDouble(r_field.getText())));
                    break;
                case "Отрезок":
                    TextField x1_field = (TextField) pointInput1.lookupAll(".text-field").toArray()[0];
                    TextField y1_field = (TextField) pointInput1.lookupAll(".text-field").toArray()[1];
                    TextField x2_field = (TextField) pointInput2.lookupAll(".text-field").toArray()[0];
                    TextField y2_field = (TextField) pointInput2.lookupAll(".text-field").toArray()[1];
                    HelloApplication.getInstance().getAppModel().figures.add(
                    new Segment(new Point2D(new double[] { Double.parseDouble(x1_field.getText()), Double.parseDouble(y1_field.getText()) }), new Point2D(new double[] { Double.parseDouble(x2_field.getText()), Double.parseDouble(y2_field.getText()) })));
                    break;
                case "Ломаная":
                case "Многоугольник":
                case "Треугольник":
                case "Четырёхугольник":
                case "Прямоугольник":
                case "Трапеция":
                    int points_count = anglesCount.getValue();
                    Point2D[] pr = new Point2D[points_count];

                    for (int i = 1; i <= points_count; i++) {
                        String name = "pointInput" + i;
                        TextField x = (TextField) addScene.lookup("#" + name).lookupAll(".text-field").toArray()[0];
                        TextField y = (TextField) addScene.lookup("#" + name).lookupAll(".text-field").toArray()[1];
                        pr[i - 1] = new Point2D(new double[] { Double.parseDouble(x.getText()), Double.parseDouble(y.getText()) });
                    }

                    String type = figureTypeBox.getValue();
                    if (Objects.equals(type, "Ломаная")) HelloApplication.getInstance().getAppModel().figures.add(new Polyline(pr));
                    if (Objects.equals(type, "Многоугольник")) HelloApplication.getInstance().getAppModel().figures.add(new NGon(pr));
                    if (Objects.equals(type, "Четырёхугольник")) HelloApplication.getInstance().getAppModel().figures.add(new QGon(pr));
                    if (Objects.equals(type, "Треугольник")) HelloApplication.getInstance().getAppModel().figures.add(new TGon(pr));
                    if (Objects.equals(type, "Трапеция")) HelloApplication.getInstance().getAppModel().figures.add(new Trapeze(pr));
                    if (Objects.equals(type, "Прямоугольник")) HelloApplication.getInstance().getAppModel().figures.add(new Rectangle(pr));
                    break;
            }
            showSuccessAlert("Добавление фигуры успешно завершено", "Успех");
        }
        catch (Exception e) {
            showErrorAlert("Возникла ошибка", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
        Scene scene = cancelButton.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
