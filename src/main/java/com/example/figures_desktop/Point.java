package com.example.figures_desktop;
import java.util.Arrays;

public class Point {
    protected int dim;
    protected double[] x;

    public Point(int dim) {
        this.dim = dim;
        x = new double[dim];
    }

    public Point(int dim, double[] x) {
        if (dim != x.length) {
            throw new IllegalArgumentException("Размерность массива не совпадает с заданной размерностью пространства.");
        }
        this.dim = dim;
        this.x = Arrays.copyOf(x, dim);
    }

    public int getDim() {
        return dim;
    }

    public double[] getX() {
        return Arrays.copyOf(x, dim);
    }

    public double getX(int i) {
        if (i < 0 || i >= dim) {
            throw new IllegalArgumentException("Неверный индекс координаты.");
        }
        return x[i];
    }

    public void setX(double[] x) {
        if (dim != x.length) {
            throw new IllegalArgumentException("Размерность массива не совпадает с заданной размерностью пространства.");
        }
        this.x = Arrays.copyOf(x, dim);
    }

    public void setX(double x, int i) {
        if (i < 0 || i >= dim) {
            throw new IllegalArgumentException("Неверный индекс координаты.");
        }
        this.x[i] = x;
    }

    public double abs() {
        double sum = 0;
        for (int i = 0; i < dim; i++) {
            sum += x[i] * x[i];
        }
        return Math.sqrt(sum);
    }

    public static Point add(Point a, Point b) {
        if (a.dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double[] res = new double[a.dim];
        for (int i = 0; i < a.dim; i++) {
            res[i] = a.x[i] + b.x[i];
        }
        return new Point(a.dim, res);
    }

    public Point add(Point b) {
        if (dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double[] res = new double[dim];
        for (int i = 0; i < dim; i++) {
            res[i] = x[i] + b.x[i];
        }
        return new Point(dim, res);
    }

    public static Point sub(Point a, Point b) {
        if (a.dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double[] res = new double[a.dim];
        for (int i = 0; i < a.dim; i++) {
            res[i] = a.x[i] - b.x[i];
        }
        return new Point(a.dim, res);
    }

    public Point sub(Point b) {
        if (dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double[] res = new double[dim];
        for (int i = 0; i < dim; i++) {
            res[i] = x[i] - b.x[i];
        }
        return new Point(dim, res);
    }

    public static Point mult(Point a, double r) {
        double[] res = new double[a.dim];
        for (int i = 0; i < a.dim; i++) {
            res[i] = a.x[i] * r;
        }
        return new Point(a.dim, res);
    }

    public Point mult(double r) {
        double[] res = new double[dim];
        for (int i = 0; i < dim; i++) {
            res[i] = x[i] * r;
        }
        return new Point(dim, res);
    }

    public static double mult(Point a, Point b) {
        if (a.dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double res = 0;
        for (int i = 0; i < a.dim; i++) {
            res += a.x[i] * b.x[i];
        }
        return res;
    }

    public double mult(Point b) {
        if (dim != b.dim) {
            throw new IllegalArgumentException("Размерности точек не совпадают.");
        }
        double res = 0;
        for (int i = 0; i < dim; i++) {
            res += x[i] * b.x[i];
        }
        return res;
    }

    public static Point symAxis(Point a, int i) {
        double[] res = new double[a.dim];
        System.arraycopy(a.x, 0, res, 0, a.dim);

        if (i < 0 || i >= a.dim) {
            throw new IllegalArgumentException("Неверный номер оси.");
        }

        for (int k = 0; k < a.dim; k++) {
            if (k != i) {
                res[k] = -res[k];
            }
        }
        return new Point(a.dim, res);
    }

    public Point symAxis(int i) {
        double[] res = new double[dim];
        System.arraycopy(x, 0, res, 0, dim);

        if (i < 0 || i >= dim) {
            throw new IllegalArgumentException("Неверный номер оси.");
        }

        for (int k = 0; k < dim; k++) {
            if (k != i) {
                res[k] = -res[k];
            }
        }

        return new Point(dim, res);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("(").append(x[0]);
        for (int i = 1; i < dim; i++) {
            res.append(", ").append(x[i]);
        }
        res.append(")");
        return res.toString();
    }
}
