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

public class SquareFigure {
    public ComboBox<String> figureChoice;
    public Button countButton;
    public Button cancelButton;
    private Scene parentScene;

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
    }

    @FXML
    public void initialize() {
        Refresh();
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
            figures.add(name + " " + v.toString());
        }
        figureChoice.setItems(figures);
    }

    public void onCountButtonClick(ActionEvent actionEvent) {
        try {
            if (figureChoice.getSelectionModel().isEmpty()) return;
            int selectedIndex = figureChoice.getSelectionModel().getSelectedIndex();
            for (var v: HelloApplication.getInstance().getAppModel().figures) {
                v.setRed(false);
            }

            Label label = (Label) parentScene.lookup("#label");
            TextField infoField = (TextField) parentScene.lookup("#infoField");

            label.setText("Площадь:");
            infoField.setText(HelloApplication.getInstance().getAppModel().figures.get(selectedIndex).square() + "");
            HelloApplication.getInstance().getAppModel().figures.get(selectedIndex).setRed(true);
            HelloApplication.getInstance().getAppModel().figures.add(new Segment(new Point2D(new double[]{0, 0}), new Point2D(new double[]{0, 0})));
            HelloApplication.getInstance().getAppModel().figures.remove(HelloApplication.getInstance().getAppModel().figures.size() - 1);

            Refresh();

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
