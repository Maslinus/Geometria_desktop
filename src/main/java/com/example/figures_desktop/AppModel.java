package com.example.figures_desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private String sharedData;
    public ObservableList<IShape> figures = FXCollections.observableArrayList();
}

