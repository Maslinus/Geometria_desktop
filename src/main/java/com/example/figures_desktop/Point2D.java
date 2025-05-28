package com.example.figures_desktop;
public class Point2D extends Point {
    public Point2D() {
        super(2);
    }

    public Point2D(double[] x) {
        super(2, x);
    }

    public static Point2D rot(Point2D a, double phi) {
        double[] res = new double[2];
        res[0] = a.x[0] * Math.cos(phi) - a.x[1] * Math.sin(phi);
        res[1] = a.x[0] * Math.sin(phi) + a.x[1] * Math.cos(phi);
        return new Point2D(res);
    }

    public Point2D rot(double phi) {
        double[] res = new double[2];
        res[0] = x[0] * Math.cos(phi) - x[1] * Math.sin(phi);
        res[1] = x[0] * Math.sin(phi) + x[1] * Math.cos(phi);
        return new Point2D(res);
    }

    @Override
    public Point symAxis(int i) {
        double[] res = new double[2];
        System.arraycopy(x, 0, res, 0, dim);
        if (i < 0 || i >= 2) {
            throw new IllegalArgumentException("Неверный номер оси.");
        }

        if (i == 0)
            res[1] = -res[1];
        else
            res[0] = -res[0];

        return new Point2D(res);
    }
}
