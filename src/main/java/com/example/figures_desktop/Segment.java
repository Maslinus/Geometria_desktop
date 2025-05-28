package com.example.figures_desktop;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Segment extends OpenFigure {
    private Point2D start;
    private Point2D finish;

    private boolean red;

    @Override
    public boolean getRed() {
        return red;
    }

    @Override
    public void setRed(boolean value) {
        red = value;
    }

    public Segment(Point2D s, Point2D f) {
        start = s;
        finish = f;
    }

    public Point2D getStart() {
        return start;
    }

    public void setStart(Point2D a) {
        start = a;
    }

    public Point2D getFinish() {
        return finish;
    }

    public void setFinish(Point2D a) {
        finish = a;
    }

    @Override
    public double length() {
        return Point.sub(finish, start).abs();
    }

    @Override
    public IShape shift(Point2D a) {
        Point2D newStart = new Point2D(new double[]{start.getX(0) + a.getX(0), start.getX(1) + a.getX(1)});
        Point2D newFinish = new Point2D(new double[]{finish.getX(0) + a.getX(0), finish.getX(1) + a.getX(1)});
        return new Segment(newStart, newFinish);
    }

    @Override
    public IShape rot(double phi) {
        Point2D newStart = start.rot(phi);
        Point2D newFinish = finish.rot(phi);
        return new Segment(newStart, newFinish);
    }

    @Override
    public IShape symAxis(int i) {
        Point2D newStart = (Point2D) start.symAxis(i);
        Point2D newFinish = (Point2D) finish.symAxis(i);
        return new Segment(newStart, newFinish);
    }

    @Override
    public boolean cross(IShape i) {
        if (!(i instanceof Segment)) {
            throw new IllegalArgumentException("Аргумент должен быть экземпляром класса Segment");
        }

        Segment other = (Segment) i;

        var a = start;
        var b = finish;
        var c = other.start;
        var d = other.finish;

        if (a.getX(0) == c.getX(0) && a.getX(1) == c.getX(1) && b.getX(0) == d.getX(0) && b.getX(1) == d.getX(1)) return true;

        var ua = (d.getX(0) - c.getX(0)) * (a.getX(1) - c.getX(1)) - (d.getX(1) - c.getX(1)) * (a.getX(0) - c.getX(0));
        var ub = (b.getX(0) - a.getX(0)) * (a.getX(1) - c.getX(1)) - (b.getX(1) - a.getX(1)) * (a.getX(0) - c.getX(0));
        var denom = (d.getX(1) - c.getX(1)) * (b.getX(0) - a.getX(0)) - (d.getX(0) - c.getX(0)) * (b.getX(1) - a.getX(1));

        if (denom == 0) {
            return false;
        }

        ua /= denom;
        ub /= denom;

        if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        return "[" + start + "; " + finish + "]";
    }

    public void draw(GraphicsContext gc, boolean red) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        double adjustedStartX = canvasWidth / 2 + start.getX(0);
        double adjustedStartY = canvasHeight / 2 -  start.getX(1);
        double adjustedFinishX = canvasWidth / 2 + finish.getX(0);
        double adjustedFinishY = canvasHeight / 2 - finish.getX(1);

        if (red) gc.setStroke(Color.RED);
        else gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.strokeLine(adjustedStartX, adjustedStartY, adjustedFinishX, adjustedFinishY);
    }
}
