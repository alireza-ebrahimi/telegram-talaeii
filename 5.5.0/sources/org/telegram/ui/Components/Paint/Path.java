package org.telegram.ui.Components.Paint;

import java.util.Arrays;
import java.util.Vector;

public class Path {
    private float baseWeight;
    private Brush brush;
    private int color;
    private Vector<Point> points = new Vector();
    public double remainder;

    public Path(Point point) {
        this.points.add(point);
    }

    public Path(Point[] pointArr) {
        this.points.addAll(Arrays.asList(pointArr));
    }

    public float getBaseWeight() {
        return this.baseWeight;
    }

    public Brush getBrush() {
        return this.brush;
    }

    public int getColor() {
        return this.color;
    }

    public int getLength() {
        return this.points == null ? 0 : this.points.size();
    }

    public Point[] getPoints() {
        Point[] pointArr = new Point[this.points.size()];
        this.points.toArray(pointArr);
        return pointArr;
    }

    public void setup(int i, float f, Brush brush) {
        this.color = i;
        this.baseWeight = f;
        this.brush = brush;
    }
}
