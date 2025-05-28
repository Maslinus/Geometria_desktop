package com.example.figures_desktop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polyline extends OpenFigure implements IPolyPoint {
    private int n;
    private Point2D[] p;

    private boolean red;

    @Override
    public boolean getRed() {
        return red;
    }

    @Override
    public void setRed(boolean value) {
        red = value;
    }

    public Polyline(Point2D[] p) {
        n = p.length;
        this.p = p;
    }

    public int getN() {
        return n;
    }

    public Point2D[] getP() {
        return p;
    }

    public Point2D getP(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Неверный индекс координаты.");
        }
        return p[i];
    }

    public void setP(Point2D[] p) {
        this.p = p;
    }

    public void setP(Point2D p, int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Неверный индекс координаты.");
        }
        this.p[i] = p;
    }

    @Override
    public double length() {
        double res = 0;
        for (int i = 0; i < n - 1; i++) {
            res += Point.sub(p[i], p[i + 1]).abs();
        }
        return res;
    }

    @Override
    public IShape shift(Point2D a) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = new Point2D(new double[]{p[i].getX(0) + a.getX(0), p[i].getX(1) + a.getX(1)});
        }
        return new Polyline(res);
    }

    @Override
    public IShape rot(double phi) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[i].rot(phi);
        }
        return new Polyline(res);
    }

    @Override
    public IShape symAxis(int i) {
        Point2D[] res = new Point2D[n];
        for (int j = 0; j < n; j++) {
            res[j] = (Point2D) p[j].symAxis(i);
        }
        return new Polyline(res);
    }

    @Override
    public boolean cross(IShape i) {
        if (!(i instanceof Polyline)) {
            throw new IllegalArgumentException("Аргумент должен быть экземпляром класса Polyline");
        }

        Polyline other = (Polyline) i;
        for (int j = 1; j < other.getN() - 1; j++) {
            for (int k = 1; k < n - 1; k++) {
                var a = getP(k - 1);
                var b = getP(k);
                var c = other.getP(j - 1);
                var d = other.getP(j);

                if (new Segment(a, b).cross(new Segment(c, d))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[");
        res.append(p[0].toString());
        for (int i = 1; i < n; i++) {
            res.append(", ").append(p[i].toString());
        }
        res.append("]");
        return res.toString();
    }

    public void draw(GraphicsContext gc, boolean red) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        if (red) gc.setStroke(Color.RED);
        else gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);

        for (int i = 0; i < n - 1; i++) {
            double startX = p[i].getX(0);
            double startY = p[i].getX(1);
            double finishX = p[i + 1].getX(0);
            double finishY = p[i + 1].getX(1);

            double adjustedStartX = canvasWidth / 2 + startX;
            double adjustedStartY = canvasHeight / 2 - startY;
            double adjustedFinishX = canvasWidth / 2 + finishX;
            double adjustedFinishY = canvasHeight / 2 - finishY;

            gc.strokeLine(adjustedStartX, adjustedStartY, adjustedFinishX, adjustedFinishY);
        }
    }
}
