package controllers;

import com.example.figures_desktop.*;
import controllers.CrossFigures;
import controllers.PerimeterFigure;
import controllers.SquareFigure;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class HelloController {
    public Button addButton;
    public Button saveButton;
    public Button loadButton;
    public Button imageButton;
    public Button moveButton;
    public Button deleteButton;
    public Button clearButton;
    public Button squareButton;
    public Button perimeterButton;
    public Button crossButton;
    public Canvas canvas;
    public TextField infoField;
    public Label label;

    @FXML
    public void initialize() {
        double scaleX = 3.0;
        double scaleY = 3.0;

        double centerX = canvas.getGraphicsContext2D().getCanvas().getWidth() / 2;
        double centerY = canvas.getGraphicsContext2D().getCanvas().getHeight() / 2;

        canvas.getGraphicsContext2D().save();

        canvas.getGraphicsContext2D().translate(centerX, centerY);

        canvas.getGraphicsContext2D().setLineWidth(0.5);

        canvas.getGraphicsContext2D().scale(scaleX, scaleY);

        canvas.getGraphicsContext2D().translate(-centerX, -centerY);

        drawCoordinateAxes(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        scheduleAdditionalInitialization();
    }

    private void performAdditionalInitialization() {
        HelloApplication.getInstance().getAppModel().figures.addListener((ListChangeListener<IShape>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Refresh();
                } else if (change.wasRemoved()) {
                    Refresh();
                }
            }
        });
    }

    private void scheduleAdditionalInitialization() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                performAdditionalInitialization();
            }
        }, 1000);
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_figures.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Добавление фигуры");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
        String textToSave = HelloApplication.getInstance().getAppModel().figures.size() + "\r\n";
        for (int i = 0; i < HelloApplication.getInstance().getAppModel().figures.size(); i++) {
            textToSave += HelloApplication.getInstance().getAppModel().figures.get(i).getClass().toString().split("\\.")[3] + "\r\n";
            textToSave += HelloApplication.getInstance().getAppModel().figures.get(i).toString() + "\r\n";
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(saveButton.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(textToSave);
                writer.flush();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Файл успешно сохранен: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Ошибка при сохранении текста в файл");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    public void onLoadButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(saveButton.getScene().getWindow());

        if (selectedFile == null) return;

        String fileContent = readFromFile(selectedFile);
        HelloApplication.getInstance().getAppModel().figures.clear();
        String[] lines = fileContent.split("\\n");
        try {
            for (int i = 1; i < Integer.parseInt(lines[0]) * 2; i += 2) {
                String regex = "\\((.*?)\\)";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(lines[i + 1]);

                List<String> matchesList = new ArrayList<>();

                while (matcher.find()) {
                    matchesList.add(matcher.group(1));
                }
                String[] matchesArray = matchesList.toArray(new String[0]);

                switch(lines[i]){
                    case "Circle":
                        Double x = Double.parseDouble(matchesArray[0].split(", ")[0]);
                        Double y = Double.parseDouble(matchesArray[0].split(", ")[1]);

                        String rad = "rad:\\s*([0-9.]+)";
                        Pattern pat = Pattern.compile(rad);
                        Matcher match = pat.matcher(lines[i + 1]);
                        double r = 0;
                        if (match.find()) {
                            String radiusValue = match.group(1);
                            r = Double.parseDouble(radiusValue);
                        }
                        HelloApplication.getInstance().getAppModel().figures.add(
                                new Circle(new Point2D(new double[] { x, y }), r));
                        break;
                    case "Segment":
                        Double x1 = Double.parseDouble(matchesArray[0].split(", ")[0]);
                        Double y1 = Double.parseDouble(matchesArray[0].split(", ")[1]);
                        Double x2 = Double.parseDouble(matchesArray[1].split(", ")[0]);
                        Double y2 = Double.parseDouble(matchesArray[1].split(", ")[1]);
                        HelloApplication.getInstance().getAppModel().figures.add(
                                new Segment(new Point2D(new double[] { x1, y1 }), new Point2D(new double[] { x2, y2 })));
                        break;
                    case "Polyline":
                    case "NGon":
                    case "TGon":
                    case "QGon":
                    case "Rectangle":
                    case "Trapeze":
                        int points_count = matchesArray.length;
                        Point2D[] pr = new Point2D[points_count];

                        for (int j = 0; j < points_count; j++) {
                            Double x_ = Double.parseDouble(matchesArray[j].split(", ")[0]);
                            Double y_ = Double.parseDouble(matchesArray[j].split(", ")[1]);
                            pr[j] = new Point2D(new double[] { x_, y_ });
                        }

                        String type = lines[i];
                        if (Objects.equals(type, "Polyline")) HelloApplication.getInstance().getAppModel().figures.add(new Polyline(pr));
                        if (Objects.equals(type, "NGon")) HelloApplication.getInstance().getAppModel().figures.add(new NGon(pr));
                        if (Objects.equals(type, "QGon")) HelloApplication.getInstance().getAppModel().figures.add(new QGon(pr));
                        if (Objects.equals(type, "TGon")) HelloApplication.getInstance().getAppModel().figures.add(new TGon(pr));
                        if (Objects.equals(type, "Trapeze")) HelloApplication.getInstance().getAppModel().figures.add(new Trapeze(pr));
                        if (Objects.equals(type, "Rectangle")) HelloApplication.getInstance().getAppModel().figures.add(new Rectangle(pr));
                        break;
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Файл успешно загружен");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Ошибка");
            alert.setContentText("Файл имеет неверную структуру");
            alert.showAndWait();
        }
    }

    private String readFromFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Ошибка");
            alert.setContentText("Ошибка при чтении текста из файла");
            alert.showAndWait();
        }
        return content.toString();
    }

    public void onImageButtonClick(ActionEvent actionEvent) {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                System.out.println("Canvas сохранен как изображение: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onMoveButtonClick(ActionEvent actionEvent) {
        try {
            if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("move_figure.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Движение фигуры");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete_figure.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Удаление фигуры");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        HelloApplication.getInstance().getAppModel().figures.clear();
    }

    public void onSquareButtonClick(ActionEvent actionEvent) {
        try {
            if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("square_figure.fxml"));
            Parent root = loader.load();
            SquareFigure controller = loader.getController();
            Scene parentScene = squareButton.getScene();
            controller.setParentScene(parentScene);
            Stage stage = new Stage();
            stage.setTitle("Площадь фигуры");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPerimeterButtonClick(ActionEvent actionEvent) {
        try {
            if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("perimeter_figure.fxml"));
            Parent root = loader.load();
            PerimeterFigure controller = loader.getController();
            Scene parentScene = squareButton.getScene();
            controller.setParentScene(parentScene);
            Stage stage = new Stage();
            stage.setTitle("Периметр фигуры");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCrossButtonClick(ActionEvent actionEvent) {
        try {
            if (HelloApplication.getInstance().getAppModel().figures.isEmpty()) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cross_figures.fxml"));
            Parent root = loader.load();
            CrossFigures controller = loader.getController();
            Scene parentScene = squareButton.getScene();
            controller.setParentScene(parentScene);
            Stage stage = new Stage();
            stage.setTitle("Пересечение фигур");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Refresh() {
        drawCoordinateAxes(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < (long)HelloApplication.getInstance().getAppModel().figures.size(); i++) {
            HelloApplication.getInstance().getAppModel().figures.get(i).draw(canvas.getGraphicsContext2D(), HelloApplication.getInstance().getAppModel().figures.get(i).getRed());
        }
    }

    private void drawCoordinateAxes(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);

        gc.strokeLine(canvasWidth / 2, 0, canvasWidth / 2, canvasHeight);
        gc.strokeLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2);
    }


}