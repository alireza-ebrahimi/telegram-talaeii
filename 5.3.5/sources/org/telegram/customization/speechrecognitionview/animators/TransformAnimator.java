package org.telegram.customization.speechrecognitionview.animators;

import android.graphics.Point;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.speechrecognitionview.RecognitionBar;

public class TransformAnimator implements BarParamsAnimator {
    private static final long DURATION = 300;
    private final List<RecognitionBar> bars;
    private final int centerX;
    private final int centerY;
    private final List<Point> finalPositions = new ArrayList();
    private boolean isPlaying;
    private OnInterpolationFinishedListener listener;
    private final int radius;
    private long startTimestamp;

    public interface OnInterpolationFinishedListener {
        void onFinished();
    }

    public TransformAnimator(List<RecognitionBar> bars, int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.bars = bars;
        this.radius = radius;
    }

    public void start() {
        this.isPlaying = true;
        this.startTimestamp = System.currentTimeMillis();
        initFinalPositions();
    }

    public void stop() {
        this.isPlaying = false;
        if (this.listener != null) {
            this.listener.onFinished();
        }
    }

    public void animate() {
        if (this.isPlaying) {
            long delta = System.currentTimeMillis() - this.startTimestamp;
            if (delta > DURATION) {
                delta = DURATION;
            }
            for (int i = 0; i < this.bars.size(); i++) {
                RecognitionBar bar = (RecognitionBar) this.bars.get(i);
                int y = bar.getStartY() + ((int) (((float) (((Point) this.finalPositions.get(i)).y - bar.getStartY())) * (((float) delta) / 300.0f)));
                bar.setX(bar.getStartX() + ((int) (((float) (((Point) this.finalPositions.get(i)).x - bar.getStartX())) * (((float) delta) / 300.0f))));
                bar.setY(y);
                bar.update();
            }
            if (delta == DURATION) {
                stop();
            }
        }
    }

    private void initFinalPositions() {
        Point startPoint = new Point();
        startPoint.x = this.centerX;
        startPoint.y = this.centerY - this.radius;
        for (int i = 0; i < 5; i++) {
            Point point = new Point(startPoint);
            rotate(72.0d * ((double) i), point);
            this.finalPositions.add(point);
        }
    }

    private void rotate(double degrees, Point point) {
        double angle = Math.toRadians(degrees);
        int y = this.centerY + ((int) ((((double) (point.x - this.centerX)) * Math.sin(angle)) + (((double) (point.y - this.centerY)) * Math.cos(angle))));
        point.x = this.centerX + ((int) ((((double) (point.x - this.centerX)) * Math.cos(angle)) - (((double) (point.y - this.centerY)) * Math.sin(angle))));
        point.y = y;
    }

    public void setOnInterpolationFinishedListener(OnInterpolationFinishedListener listener) {
        this.listener = listener;
    }
}
