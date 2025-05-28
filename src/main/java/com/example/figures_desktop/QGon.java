package com.example.figures_desktop;

public class QGon extends NGon {
    public QGon(Point2D[] p) {
        super(p);
    }

    @Override
    public double square() {
        double a = Point.sub(p[1], p[0]).abs();
        double b = Point.sub(p[2], p[1]).abs();
        double c = Point.sub(p[0], p[2]).abs();

        double pr = (a + b + c) / 2;
        double pr1 = Math.sqrt(pr * (pr - a) * (pr - b) * (pr - c));

        a = Point.sub(p[0], p[3]).abs();
        b = Point.sub(p[3], p[2]).abs();

        pr = (a + b + c) / 2;
        double pr2 = Math.sqrt(pr * (pr - a) * (pr - b) * (pr - c));

        return pr1 + pr2;
    }

    @Override
    public IShape shift(Point2D a) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = new Point2D(new double[]{p[i].getX(0) + a.getX(0), p[i].getX(1) + a.getX(1)});
        }
        return new QGon(res);
    }

    @Override
    public IShape rot(double phi) {
        Point2D[] res = new Point2D[n];
        for (int i = 0; i < n; i++) {
            res[i] = p[i].rot(phi);
        }
        return new QGon(res);
    }

    @Override
    public IShape symAxis(int i) {
        Point2D[] res = new Point2D[n];
        for (int j = 0; j < n; j++) {
            res[j] = (Point2D) p[j].symAxis(i);
        }
        return new QGon(res);
    }
}
