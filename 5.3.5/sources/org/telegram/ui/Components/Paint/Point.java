package org.telegram.ui.Components.Paint;

import android.graphics.PointF;

public class Point {
    public boolean edge;
    /* renamed from: x */
    public double f101x;
    /* renamed from: y */
    public double f102y;
    /* renamed from: z */
    public double f103z;

    public Point(double x, double y, double z) {
        this.f101x = x;
        this.f102y = y;
        this.f103z = z;
    }

    public Point(Point point) {
        this.f101x = point.f101x;
        this.f102y = point.f102y;
        this.f103z = point.f103z;
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
        Point other = (Point) obj;
        if (!(this.f101x == other.f101x && this.f102y == other.f102y && this.f103z == other.f103z)) {
            z = false;
        }
        return z;
    }

    Point multiplySum(Point point, double scalar) {
        return new Point((this.f101x + point.f101x) * scalar, (this.f102y + point.f102y) * scalar, (this.f103z + point.f103z) * scalar);
    }

    Point multiplyAndAdd(double scalar, Point point) {
        return new Point((this.f101x * scalar) + point.f101x, (this.f102y * scalar) + point.f102y, (this.f103z * scalar) + point.f103z);
    }

    void alteringAddMultiplication(Point point, double scalar) {
        this.f101x += point.f101x * scalar;
        this.f102y += point.f102y * scalar;
        this.f103z += point.f103z * scalar;
    }

    Point add(Point point) {
        return new Point(this.f101x + point.f101x, this.f102y + point.f102y, this.f103z + point.f103z);
    }

    Point substract(Point point) {
        return new Point(this.f101x - point.f101x, this.f102y - point.f102y, this.f103z - point.f103z);
    }

    Point multiplyByScalar(double scalar) {
        return new Point(this.f101x * scalar, this.f102y * scalar, this.f103z * scalar);
    }

    Point getNormalized() {
        return multiplyByScalar(1.0d / getMagnitude());
    }

    private double getMagnitude() {
        return Math.sqrt(((this.f101x * this.f101x) + (this.f102y * this.f102y)) + (this.f103z * this.f103z));
    }

    float getDistanceTo(Point point) {
        return (float) Math.sqrt((Math.pow(this.f101x - point.f101x, 2.0d) + Math.pow(this.f102y - point.f102y, 2.0d)) + Math.pow(this.f103z - point.f103z, 2.0d));
    }

    PointF toPointF() {
        return new PointF((float) this.f101x, (float) this.f102y);
    }
}
