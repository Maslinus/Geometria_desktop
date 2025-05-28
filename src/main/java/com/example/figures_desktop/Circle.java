package com.example.figures_desktop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements IShape {
    private double r;
    private Point2D p;
    private boolean red;

    @Override
    public boolean getRed() {
        return red;
    }

    @Override
    public void setRed(boolean value) {
        red = value;
    }

    public Circle(Point2D p, double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("Неверный радиус.");
        }
        this.p = p;
        this.r = r;
    }

    public Point2D getP() {
        return p;
    }

    public void setP(Point2D p) {
        this.p = p;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("Неверный радиус.");
        }
        this.r = r;
    }

    public double square() {
        return Math.PI * r * r;
    }

    public double length() {
        return 2 * Math.PI * r;
    }

    public IShape shift(Point2D a) {
        return new Circle(new Point2D(new double[]{p.getX(0) + a.getX(0), p.getX(1) + a.getX(1)}), r);
    }

    public IShape rot(double phi) {
        return new Circle(p.rot(phi), r);
    }

    public IShape symAxis(int i) {
        return new Circle((Point2D) p.symAxis(i), r);
    }

    public boolean cross(IShape i) {
        if (i instanceof Circle) {
            Circle c = (Circle) i;
            double r1 = r;
            double r2 = c.r;

            if (r1 > r2) {
                double t = r1;
                r1 = r2;
                r2 = t;
            }
            double d = Point.sub(p, c.p).abs();

            if (d + r1 < r2 || d > r1 + r2)
                return false;
            if (d < r1 + r2 || d == r1 + r2)
                return true;
            return false;
        } else {
            throw new IllegalArgumentException("Аргумент должен быть экземпляром класса Circle");
        }
    }

    @Override
    public String toString() {
        return "[center:" + p + ", rad: " + r + "]";
    }

    public void draw(GraphicsContext gc, boolean red) {
        double adjustedCenterX = gc.getCanvas().getWidth() / 2 + p.getX(0);
        double adjustedCenterY = gc.getCanvas().getHeight() / 2 - p.getX(1);
        if (red) gc.setStroke(Color.RED);
        else gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.strokeOval(adjustedCenterX - r, adjustedCenterY - r, 2 * r, 2 * r);
    }

}