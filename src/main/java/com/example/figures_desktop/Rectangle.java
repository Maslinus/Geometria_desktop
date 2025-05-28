package com.example.figures_desktop;

public class Rectangle extends QGon {
    public Rectangle(Point2D[] p) {
        super(p);
    }

    @Override
    public double square() {
        return Point.sub(p[1], p[0]).abs() * Point.sub(p[2], p[1]).abs();
    }

    @Override
    public IShape shift(Point2D a) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = new Point2D(new double[]{p[i].getX(0) + a.getX(0), p[i].getX(1) + a.getX(1)});
        }
        return new Rectangle(res);
    }

    @Override
    public IShape rot(double phi) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[i].rot(phi);
        }
        return new Rectangle(res);
    }

    @Override
    public IShape symAxis(int i) {
        Point2D[] res = new Point2D[n];
        for (int j = 0; j < n; j++) {
            res[j] = (Point2D) p[j].symAxis(i);
        }
        return new Rectangle(res);
    }
}
