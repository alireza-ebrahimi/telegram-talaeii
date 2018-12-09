package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;
import android.view.MotionEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Vector;
import org.telegram.messenger.AndroidUtilities;

public class Input {
    private boolean beganDrawing;
    private boolean clearBuffer;
    private boolean hasMoved;
    private Matrix invertMatrix;
    private boolean isFirst;
    private Point lastLocation;
    private double lastRemainder;
    private Point[] points = new Point[3];
    private int pointsCount;
    private RenderView renderView;
    private float[] tempPoint = new float[2];

    public Input(RenderView renderView) {
        this.renderView = renderView;
    }

    private void paintPath(final Path path) {
        path.setup(this.renderView.getCurrentColor(), this.renderView.getCurrentWeight(), this.renderView.getCurrentBrush());
        if (this.clearBuffer) {
            this.lastRemainder = 0.0d;
        }
        path.remainder = this.lastRemainder;
        this.renderView.getPainting().paintStroke(path, this.clearBuffer, new Runnable() {

            /* renamed from: org.telegram.ui.Components.Paint.Input$1$1 */
            class C44681 implements Runnable {
                C44681() {
                }

                public void run() {
                    Input.this.lastRemainder = path.remainder;
                    Input.this.clearBuffer = false;
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C44681());
            }
        });
    }

    private void reset() {
        this.pointsCount = 0;
    }

    private Point smoothPoint(Point point, Point point2, Point point3, float f) {
        double pow = Math.pow((double) (1.0f - f), 2.0d);
        double d = (double) ((2.0f * (1.0f - f)) * f);
        double d2 = (double) (f * f);
        return new Point(((point.f10180x * pow) + (point3.f10180x * d)) + (point2.f10180x * d2), ((pow * point.f10181y) + (d * point3.f10181y)) + (point2.f10181y * d2), 1.0d);
    }

    private void smoothenAndPaintPoints(boolean z) {
        if (this.pointsCount > 2) {
            Vector vector = new Vector();
            Point point = this.points[0];
            Point point2 = this.points[1];
            Point point3 = this.points[2];
            if (point3 != null && point2 != null && point != null) {
                Point multiplySum = point2.multiplySum(point, 0.5d);
                Point multiplySum2 = point3.multiplySum(point2, 0.5d);
                int min = (int) Math.min(48.0d, Math.max(Math.floor((double) (multiplySum.getDistanceTo(multiplySum2) / ((float) 1))), 24.0d));
                float f = BitmapDescriptorFactory.HUE_RED;
                float f2 = 1.0f / ((float) min);
                for (int i = 0; i < min; i++) {
                    Point smoothPoint = smoothPoint(multiplySum, multiplySum2, point2, f);
                    if (this.isFirst) {
                        smoothPoint.edge = true;
                        this.isFirst = false;
                    }
                    vector.add(smoothPoint);
                    f += f2;
                }
                if (z) {
                    multiplySum2.edge = true;
                }
                vector.add(multiplySum2);
                Point[] pointArr = new Point[vector.size()];
                vector.toArray(pointArr);
                paintPath(new Path(pointArr));
                System.arraycopy(this.points, 1, this.points, 0, 2);
                if (z) {
                    this.pointsCount = 0;
                    return;
                } else {
                    this.pointsCount = 2;
                    return;
                }
            }
            return;
        }
        pointArr = new Point[this.pointsCount];
        System.arraycopy(this.points, 0, pointArr, 0, this.pointsCount);
        paintPath(new Path(pointArr));
    }

    public void process(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        float height = ((float) this.renderView.getHeight()) - motionEvent.getY();
        this.tempPoint[0] = motionEvent.getX();
        this.tempPoint[1] = height;
        this.invertMatrix.mapPoints(this.tempPoint);
        Point point = new Point((double) this.tempPoint[0], (double) this.tempPoint[1], 1.0d);
        switch (actionMasked) {
            case 0:
            case 2:
                if (!this.beganDrawing) {
                    this.beganDrawing = true;
                    this.hasMoved = false;
                    this.isFirst = true;
                    this.lastLocation = point;
                    this.points[0] = point;
                    this.pointsCount = 1;
                    this.clearBuffer = true;
                    return;
                } else if (point.getDistanceTo(this.lastLocation) >= ((float) AndroidUtilities.dp(5.0f))) {
                    if (!this.hasMoved) {
                        this.renderView.onBeganDrawing();
                        this.hasMoved = true;
                    }
                    this.points[this.pointsCount] = point;
                    this.pointsCount++;
                    if (this.pointsCount == 3) {
                        smoothenAndPaintPoints(false);
                    }
                    this.lastLocation = point;
                    return;
                } else {
                    return;
                }
            case 1:
                if (!this.hasMoved) {
                    if (this.renderView.shouldDraw()) {
                        point.edge = true;
                        paintPath(new Path(point));
                    }
                    reset();
                } else if (this.pointsCount > 0) {
                    smoothenAndPaintPoints(true);
                }
                this.pointsCount = 0;
                this.renderView.getPainting().commitStroke(this.renderView.getCurrentColor());
                this.beganDrawing = false;
                this.renderView.onFinishedDrawing(this.hasMoved);
                return;
            default:
                return;
        }
    }

    public void setMatrix(Matrix matrix) {
        this.invertMatrix = new Matrix();
        matrix.invert(this.invertMatrix);
    }
}
