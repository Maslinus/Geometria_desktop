package controllers;

import com.example.figures_desktop.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class DeleteFigure {
    public ComboBox<String> figureChoice;
    public Button deleteButton;
    public Button cancelButton;

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
                case "Segment" -> "Орезок";
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
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            if (figureChoice.getSelectionModel().isEmpty()) return;
            int selectedIndex = figureChoice.getSelectionModel().getSelectedIndex();
            HelloApplication.getInstance().getAppModel().figures.remove(selectedIndex);
            Refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Удаление фигуры успешно завершено");
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
