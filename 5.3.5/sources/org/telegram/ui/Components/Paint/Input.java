package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;
import android.view.MotionEvent;
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

    public Input(RenderView render) {
        this.renderView = render;
    }

    public void setMatrix(Matrix m) {
        this.invertMatrix = new Matrix();
        m.invert(this.invertMatrix);
    }

    public void process(MotionEvent event) {
        int action = event.getActionMasked();
        float y = ((float) this.renderView.getHeight()) - event.getY();
        this.tempPoint[0] = event.getX();
        this.tempPoint[1] = y;
        this.invertMatrix.mapPoints(this.tempPoint);
        Point location = new Point((double) this.tempPoint[0], (double) this.tempPoint[1], 1.0d);
        switch (action) {
            case 0:
            case 2:
                if (!this.beganDrawing) {
                    this.beganDrawing = true;
                    this.hasMoved = false;
                    this.isFirst = true;
                    this.lastLocation = location;
                    this.points[0] = location;
                    this.pointsCount = 1;
                    this.clearBuffer = true;
                    return;
                } else if (location.getDistanceTo(this.lastLocation) >= ((float) AndroidUtilities.dp(5.0f))) {
                    if (!this.hasMoved) {
                        this.renderView.onBeganDrawing();
                        this.hasMoved = true;
                    }
                    this.points[this.pointsCount] = location;
                    this.pointsCount++;
                    if (this.pointsCount == 3) {
                        smoothenAndPaintPoints(false);
                    }
                    this.lastLocation = location;
                    return;
                } else {
                    return;
                }
            case 1:
                if (!this.hasMoved) {
                    if (this.renderView.shouldDraw()) {
                        location.edge = true;
                        paintPath(new Path(location));
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

    private void reset() {
        this.pointsCount = 0;
    }

    private void smoothenAndPaintPoints(boolean ended) {
        if (this.pointsCount > 2) {
            Vector<Point> points = new Vector();
            Point prev2 = this.points[0];
            Point prev1 = this.points[1];
            Point cur = this.points[2];
            if (cur != null && prev1 != null && prev2 != null) {
                Point midPoint1 = prev1.multiplySum(prev2, 0.5d);
                Point midPoint2 = cur.multiplySum(prev1, 0.5d);
                int numberOfSegments = (int) Math.min(48.0d, Math.max(Math.floor((double) (midPoint1.getDistanceTo(midPoint2) / ((float) 1))), 24.0d));
                float t = 0.0f;
                float step = 1.0f / ((float) numberOfSegments);
                for (int j = 0; j < numberOfSegments; j++) {
                    Point point = smoothPoint(midPoint1, midPoint2, prev1, t);
                    if (this.isFirst) {
                        point.edge = true;
                        this.isFirst = false;
                    }
                    points.add(point);
                    t += step;
                }
                if (ended) {
                    midPoint2.edge = true;
                }
                points.add(midPoint2);
                Point[] result = new Point[points.size()];
                points.toArray(result);
                paintPath(new Path(result));
                System.arraycopy(this.points, 1, this.points, 0, 2);
                if (ended) {
                    this.pointsCount = 0;
                    return;
                } else {
                    this.pointsCount = 2;
                    return;
                }
            }
            return;
        }
        result = new Point[this.pointsCount];
        System.arraycopy(this.points, 0, result, 0, this.pointsCount);
        paintPath(new Path(result));
    }

    private Point smoothPoint(Point midPoint1, Point midPoint2, Point prev1, float t) {
        double a1 = Math.pow((double) (1.0f - t), 2.0d);
        double a2 = (double) ((2.0f * (1.0f - t)) * t);
        double a3 = (double) (t * t);
        return new Point(((midPoint1.f101x * a1) + (prev1.f101x * a2)) + (midPoint2.f101x * a3), ((midPoint1.f102y * a1) + (prev1.f102y * a2)) + (midPoint2.f102y * a3), 1.0d);
    }

    private void paintPath(final Path path) {
        path.setup(this.renderView.getCurrentColor(), this.renderView.getCurrentWeight(), this.renderView.getCurrentBrush());
        if (this.clearBuffer) {
            this.lastRemainder = 0.0d;
        }
        path.remainder = this.lastRemainder;
        this.renderView.getPainting().paintStroke(path, this.clearBuffer, new Runnable() {

            /* renamed from: org.telegram.ui.Components.Paint.Input$1$1 */
            class C26301 implements Runnable {
                C26301() {
                }

                public void run() {
                    Input.this.lastRemainder = path.remainder;
                    Input.this.clearBuffer = false;
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C26301());
            }
        });
    }
}
