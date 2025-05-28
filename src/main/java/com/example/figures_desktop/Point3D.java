package com.example.figures_desktop;

public class Point3D extends Point {
    public Point3D() {
        super(3);
    }

    public Point3D(double[] x) {
        super(3, x);
    }

    public static Point3D cross_prod(Point3D p1, Point3D p2) {
        double[] res = new double[3];
        res[0] = p1.x[1] * p2.x[2] - p1.x[2] * p2.x[1];
        res[1] = p1.x[2] * p2.x[0] - p1.x[0] * p2.x[2];
        res[2] = p1.x[0] * p2.x[1] - p1.x[1] * p2.x[0];
        return new Point3D(res);
    }

    public Point3D cross_prod(Point3D p) {
        double[] res = new double[3];
        res[0] = x[1] * p.x[2] - x[2] * p.x[1];
        res[1] = x[2] * p.x[0] - x[0] * p.x[2];
        res[2] = x[0] * p.x[1] - x[1] * p.x[0];
        return new Point3D(res);
    }

    public static double mix_prod(Point3D p1, Point3D p2, Point3D p3) {
        return p1.x[0] * (p2.x[1] * p3.x[2] - p3.x[1] * p2.x[2])
                + p1.x[1] * (p2.x[2] * p3.x[0] - p3.x[2] * p2.x[0])
                + p1.x[2] * (p2.x[0] * p3.x[1] - p3.x[0] * p2.x[1]);
    }

    public double mix_prod(Point3D p1, Point3D p2) {
        return x[0] * (p1.x[1] * p2.x[2] - p2.x[1] * p1.x[2])
                + x[1] * (p1.x[2] * p2.x[0] - p2.x[2] * p1.x[0])
                + x[2] * (p1.x[0] * p2.x[1] - p2.x[0] * p1.x[1]);
    }
}
