package controllers;

import com.example.figures_desktop.HelloApplication;
import com.example.figures_desktop.Point2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MoveFigure {
    @FXML
    public ComboBox<String> figureChoice;
    public Button moveButton;
    public Button cancelButton;
    public ComboBox<String> moveType;
    public AnchorPane shiftVector;
    public AnchorPane rotAngle;
    public AnchorPane symAxis;
    public Spinner<String> axis;

    @FXML
    public void initialize() {
        shiftVector.setVisible(false);
        symAxis.setVisible(false);
        rotAngle.setVisible(false);

        ObservableList<String> figureTypes = FXCollections.observableArrayList(
                "Сдвиг",
                "Поворот",
                "Симметрия"
        );
        moveType.setItems(figureTypes);

        String[] stringValues = {"x", "y"};
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(stringValues));
        axis.setValueFactory(valueFactory);

        Refresh();

        moveType.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Сдвиг" -> {
                    shiftVector.setVisible(true);
                    rotAngle.setVisible(false);
                    symAxis.setVisible(false);
                    TextField x_field = (TextField) shiftVector.lookupAll(".text-field").toArray()[0];
                    TextField y_field = (TextField) shiftVector.lookupAll(".text-field").toArray()[1];
                    x_field.clear();
                    y_field.clear();
                }
                case "Поворот" -> {
                    shiftVector.setVisible(false);
                    rotAngle.setVisible(true);
                    symAxis.setVisible(false);
                    TextField angle = (TextField) rotAngle.lookup(".text-field");
                    angle.clear();
                }
                case "Симметрия" -> {
                    shiftVector.setVisible(false);
                    rotAngle.setVisible(false);
                    symAxis.setVisible(true);
                }
            }
        });
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

    public void onMoveButtonClick(ActionEvent actionEvent) {
        try {
            if (figureChoice.getSelectionModel().isEmpty() || moveType.getSelectionModel().isEmpty()) return;

            int selectedIndex = figureChoice.getSelectionModel().getSelectedIndex();

            switch(moveType.getValue()) {
                case "Сдвиг":
                    TextField x_field = (TextField) shiftVector.lookupAll(".text-field").toArray()[0];
                    TextField y_field = (TextField) shiftVector.lookupAll(".text-field").toArray()[1];
                    var v = HelloApplication.getInstance().getAppModel().figures.get(selectedIndex).shift(new Point2D(new double[] { Double.parseDouble(x_field.getText()), Double.parseDouble(y_field.getText()) }));
                    HelloApplication.getInstance().getAppModel().figures.set(selectedIndex, v);
                    break;
                case "Поворот":
                    TextField angle = (TextField) rotAngle.lookup(".text-field");
                    var a = HelloApplication.getInstance().getAppModel().figures.get(selectedIndex).rot(Double.parseDouble(angle.getText()));
                    HelloApplication.getInstance().getAppModel().figures.set(selectedIndex, a);
                    break;
                case "Симметрия":
                    int ax = 0;
                    if (Objects.equals(axis.getValue(), "y")) ax = 1;
                    var s = HelloApplication.getInstance().getAppModel().figures.get(selectedIndex).symAxis(ax);
                    HelloApplication.getInstance().getAppModel().figures.set(selectedIndex, s);
                    break;
            }
            Refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Движение фигуры успешно завершено");
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
