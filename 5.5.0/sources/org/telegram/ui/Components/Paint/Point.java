package org.telegram.ui.Components.Paint;

import android.graphics.PointF;

public class Point {
    public boolean edge;
    /* renamed from: x */
    public double f10180x;
    /* renamed from: y */
    public double f10181y;
    /* renamed from: z */
    public double f10182z;

    public Point(double d, double d2, double d3) {
        this.f10180x = d;
        this.f10181y = d2;
        this.f10182z = d3;
    }

    public Point(Point point) {
        this.f10180x = point.f10180x;
        this.f10181y = point.f10181y;
        this.f10182z = point.f10182z;
    }

    private double getMagnitude() {
        return Math.sqrt(((this.f10180x * this.f10180x) + (this.f10181y * this.f10181y)) + (this.f10182z * this.f10182z));
    }

    Point add(Point point) {
        return new Point(this.f10180x + point.f10180x, this.f10181y + point.f10181y, this.f10182z + point.f10182z);
    }

    void alteringAddMultiplication(Point point, double d) {
        this.f10180x += point.f10180x * d;
        this.f10181y += point.f10181y * d;
        this.f10182z += point.f10182z * d;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point point = (Point) obj;
        if (!(this.f10180x == point.f10180x && this.f10181y == point.f10181y && this.f10182z == point.f10182z)) {
            z = false;
        }
        return z;
    }

    float getDistanceTo(Point point) {
        return (float) Math.sqrt((Math.pow(this.f10180x - point.f10180x, 2.0d) + Math.pow(this.f10181y - point.f10181y, 2.0d)) + Math.pow(this.f10182z - point.f10182z, 2.0d));
    }

    Point getNormalized() {
        return multiplyByScalar(1.0d / getMagnitude());
    }

    Point multiplyAndAdd(double d, Point point) {
        return new Point((this.f10180x * d) + point.f10180x, (this.f10181y * d) + point.f10181y, (this.f10182z * d) + point.f10182z);
    }

    Point multiplyByScalar(double d) {
        return new Point(this.f10180x * d, this.f10181y * d, this.f10182z * d);
    }

    Point multiplySum(Point point, double d) {
        return new Point((this.f10180x + point.f10180x) * d, (this.f10181y + point.f10181y) * d, (this.f10182z + point.f10182z) * d);
    }

    Point substract(Point point) {
        return new Point(this.f10180x - point.f10180x, this.f10181y - point.f10181y, this.f10182z - point.f10182z);
    }

    PointF toPointF() {
        return new PointF((float) this.f10180x, (float) this.f10181y);
    }
}
