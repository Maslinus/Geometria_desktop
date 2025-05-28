package com.example.figures_desktop;

import javafx.scene.canvas.GraphicsContext;

public interface IShape {
    boolean getRed();
    void setRed(boolean value);
    double square();
    double length();
    IShape shift(Point2D a);
    IShape rot(double phi);
    IShape symAxis(int i);
    boolean cross(IShape i);
    void draw(GraphicsContext gc, boolean red);
}
