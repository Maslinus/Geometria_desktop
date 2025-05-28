package com.example.figures_desktop;

public class Trapeze extends QGon {
    public Trapeze(Point2D[] p) {
        super(p);
    }

    @Override
    public double square() {
        double k1 = (p[2].getX(1) - p[0].getX(1)) / (p[2].getX(0) - p[0].getX(0));
        double k2 = (p[3].getX(1) - p[1].getX(1)) / (p[3].getX(0) - p[1].getX(0));

        if (Double.isInfinite(k1) || Double.isInfinite(k2) || Double.isNaN(k1) || Double.isNaN(k2)) {
            return super.square();
        }

        double phi = Math.atan((k2 - k1) / (1 + k1 * k2));
        double a = Point.sub(p[2], p[0]).abs();
        double b = Point.sub(p[3], p[1]).abs();

        return 0.5 * a * b * Math.abs(Math.sin(phi));
    }

    @Override
    public IShape shift(Point2D a) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = new Point2D(new double[]{p[i].getX(0) + a.getX(0), p[i].getX(1) + a.getX(1)});
        }
        return new Trapeze(res);
    }

    @Override
    public IShape rot(double phi) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[i].rot(phi);
        }
        return new Trapeze(res);
    }

    @Override
    public IShape symAxis(int i) {
        Point2D[] res = new Point2D[n];
        for (int j = 0; j < n; j++) {
            res[j] = (Point2D) p[j].symAxis(i);
        }
        return new Trapeze(res);
    }
}
