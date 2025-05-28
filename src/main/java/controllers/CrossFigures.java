package controllers;

import com.example.figures_desktop.HelloApplication;
import com.example.figures_desktop.Point2D;
import com.example.figures_desktop.Segment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class CrossFigures {
    public ComboBox<String> figureChoice;
    public Button countButton;
    public Button cancelButton;
    public ComboBox<String> figureChoice1;
    public ComboBox<String> figureChoice2;
    public ComboBox<String> figureChoice3;
    private Scene parentScene;

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
    }

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
        figureChoice.setItems(figureTypes);

        figureChoice.valueProperty().addListener((observable, oldValue, newValue) -> {Refresh(); });

        ObservableList<String> figures = FXCollections.observableArrayList();
        for (var v: HelloApplication.getInstance().getAppModel().figures) {
            String name = v.getClass().toString().split("\\.")[3];
            name = switch (name) {
                case "Circle" -> "Окружность";
                case "Segment" -> "Отрезок";
                case "Polyline" -> "Ломаная";
                case "NGon" -> "Многоугольник";
                case "TGon" -> "Треугольник";
                case "QGon" -> "Четырёхугольник";
                case "Rectangle" -> "Прямоугольник";
                case "Trapeze" -> "Трапеция";
                default -> v.getClass().toString().split("\\.")[3];
            };
            figures.add(name + " " + v.toString());
        }
        figureChoice3.setItems(figures);
    }

    private void Refresh() {
        ObservableList<String> figures = FXCollections.observableArrayList();
        for (var v: HelloApplication.getInstance().getAppModel().figures) {
            String name = v.getClass().toString().split("\\.")[3];
            name = switch (name) {
                case "Circle" -> "Окружность";
                case "Segment" -> "Отрезок";
                case "Polyline" -> "Ломаная";
                case "NGon" -> "Многоугольник";
                case "TGon" -> "Треугольник";
                case "QGon" -> "Четырёхугольник";
                case "Rectangle" -> "Прямоугольник";
                case "Trapeze" -> "Трапеция";
                default -> v.getClass().toString().split("\\.")[3];
            };
            if (!Objects.equals(name, figureChoice.getValue())) continue;
            figures.add(name + " " + v.toString());
        }
        figureChoice1.setItems(figures);
        figureChoice2.setItems(figures);
    }

    public void onCountButtonClick(ActionEvent actionEvent) {
        try {
            if (figureChoice.getSelectionModel().isEmpty()) return;

            int selectedIndex1 = figureChoice3.getItems().indexOf(figureChoice1.getValue());
            int selectedIndex2 = figureChoice3.getItems().indexOf(figureChoice2.getValue());
            for (var v: HelloApplication.getInstance().getAppModel().figures) {
                v.setRed(false);
            }

            Label label = (Label) parentScene.lookup("#label");
            TextField infoField = (TextField) parentScene.lookup("#infoField");

            label.setText("Пересечение:");
            if (HelloApplication.getInstance().getAppModel().figures.get(selectedIndex1).cross(HelloApplication.getInstance().getAppModel().figures.get(selectedIndex2)))
                infoField.setText("Пересекаются");
            else infoField.setText("Не пересекаются");
            HelloApplication.getInstance().getAppModel().figures.get(selectedIndex1).setRed(true);
            HelloApplication.getInstance().getAppModel().figures.get(selectedIndex2).setRed(true);
            HelloApplication.getInstance().getAppModel().figures.add(new Segment(new Point2D(new double[]{0, 0}), new Point2D(new double[]{0, 0})));
            HelloApplication.getInstance().getAppModel().figures.remove(HelloApplication.getInstance().getAppModel().figures.size() - 1);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Подсчёт площади фигуры успешно завершен");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Возникла ошибка");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
        Scene scene = cancelButton.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
}
