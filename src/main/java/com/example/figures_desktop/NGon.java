package com.example.figures_desktop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NGon implements IShape, IPolyPoint {
    protected int n;
    protected Point2D[] p;
    private boolean red;

    @Override
    public boolean getRed() {
        return red;
    }

    @Override
    public void setRed(boolean value) {
        red = value;
    }

    public NGon(Point2D[] p) {
        if (p.length < 3) {
            throw new IllegalArgumentException("Неверное количество углов.");
        }
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
        if (p.length < 3) {
            throw new IllegalArgumentException("Неверное количество углов.");
        }
        this.p = p;
    }

    public void setP(Point2D p, int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Неверный индекс координаты.");
        }
        this.p[i] = p;
    }

    public double square() {
        double res = 0;
        double ox = p[0].getX(0);
        double oy = p[0].getX(1);
        Point2D[] t = new Point2D[n + 1];
        for (int i = 0; i < n; i++) {
            t[i] = p[i];
        }
        t[n] = p[0];

        for (int i = 1; i < n + 1; i++) {
            double x = t[i].getX(0);
            double y = t[i].getX(1);
            res += (x * oy - y * ox);
            ox = x;
            oy = y;
        }

        return res / 2;
    }

    public double length() {
        double res = 0;
        for (int i = 0; i < n - 1; i++) {
            res += Point.sub(p[i + 1], p[i]).abs();
        }
        res += Point.sub(p[n - 1], p[0]).abs();
        return res;
    }

    public IShape shift(Point2D a) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = new Point2D(new double[]{p[i].getX(0) + a.getX(0), p[i].getX(1) + a.getX(1)});
        }
        return new NGon(res);
    }

    public IShape rot(double phi) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[i].rot(phi);
        }
        return new NGon(res);
    }

    public IShape symAxis(int i) {
        Point2D[] res = new Point2D[n];
        for (int j = 0; j < n; j++) {
            res[j] = (Point2D) p[j].symAxis(i);
        }
        return new NGon(res);
    }

    public boolean cross(IShape i) {
        if (!(i instanceof NGon)) {
            throw new IllegalArgumentException("Аргумент должен быть экземпляром класса NGon или наследуемого от него класса");
        }

        NGon other = (NGon) i;
        for (int j = 0; j < other.getN(); j++) {
            for (int k = 0; k < n; k++) {
                var a = getP(k);
                var b = getP((k + 1) % n);
                var c = other.getP(j);
                var d = other.getP((j + 1) % other.getN());

                if (new Segment(a, b).cross(new Segment(c, d))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[" + p[0].toString());
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

        double startX = p[n - 1].getX(0);
        double startY = p[n - 1].getX(1);
        double finishX = p[0].getX(0);
        double finishY = p[0].getX(1);

        double adjustedStartX = canvasWidth / 2 + startX;
        double adjustedStartY = canvasHeight / 2 - startY;
        double adjustedFinishX = canvasWidth / 2 + finishX;
        double adjustedFinishY = canvasHeight / 2 - finishY;

        gc.strokeLine(adjustedStartX, adjustedStartY, adjustedFinishX, adjustedFinishY);
    }
}
