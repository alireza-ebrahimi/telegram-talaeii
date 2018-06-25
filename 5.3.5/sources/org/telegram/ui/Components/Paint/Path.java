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

    public Path(Point[] points) {
        this.points.addAll(Arrays.asList(points));
    }

    public int getLength() {
        if (this.points == null) {
            return 0;
        }
        return this.points.size();
    }

    public Point[] getPoints() {
        Point[] points = new Point[this.points.size()];
        this.points.toArray(points);
        return points;
    }

    public int getColor() {
        return this.color;
    }

    public float getBaseWeight() {
        return this.baseWeight;
    }

    public Brush getBrush() {
        return this.brush;
    }

    public void setup(int color, float baseWeight, Brush brush) {
        this.color = color;
        this.baseWeight = baseWeight;
        this.brush = brush;
    }
}
