package org.telegram.customization.speechrecognitionview.animators;

import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.speechrecognitionview.RecognitionBar;

public class RotatingAnimator implements BarParamsAnimator {
    private static final long ACCELERATE_ROTATION_DURATION = 1000;
    private static final float ACCELERATION_ROTATION_DEGREES = 40.0f;
    private static final long DECELERATE_ROTATION_DURATION = 1000;
    private static final long DURATION = 2000;
    private static final float ROTATION_DEGREES = 720.0f;
    private final List<RecognitionBar> bars;
    private final int centerX;
    private final int centerY;
    private boolean isPlaying;
    private final List<Point> startPositions = new ArrayList();
    private long startTimestamp;

    public RotatingAnimator(List<RecognitionBar> bars, int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.bars = bars;
        for (RecognitionBar bar : bars) {
            this.startPositions.add(new Point(bar.getX(), bar.getY()));
        }
    }

    public void start() {
        this.isPlaying = true;
        this.startTimestamp = System.currentTimeMillis();
    }

    public void stop() {
        this.isPlaying = false;
    }

    public void animate() {
        if (this.isPlaying) {
            long currTimestamp = System.currentTimeMillis();
            if (currTimestamp - this.startTimestamp > 2000) {
                this.startTimestamp += 2000;
            }
            long delta = currTimestamp - this.startTimestamp;
            float angle = (((float) delta) / 2000.0f) * ROTATION_DEGREES;
            int i = 0;
            for (RecognitionBar bar : this.bars) {
                float finalAngle = angle;
                if (i > 0 && delta > 1000) {
                    finalAngle += decelerate(delta, this.bars.size() - i);
                } else if (i > 0) {
                    finalAngle += accelerate(delta, this.bars.size() - i);
                }
                rotate(bar, (double) finalAngle, (Point) this.startPositions.get(i));
                i++;
            }
        }
    }

    private float decelerate(long delta, int scale) {
        return (((float) scale) * ACCELERATION_ROTATION_DEGREES) + ((-new AccelerateDecelerateInterpolator().getInterpolation(((float) (delta - 1000)) / 1000.0f)) * (((float) scale) * ACCELERATION_ROTATION_DEGREES));
    }

    private float accelerate(long delta, int scale) {
        return new AccelerateDecelerateInterpolator().getInterpolation(((float) delta) / 1000.0f) * (ACCELERATION_ROTATION_DEGREES * ((float) scale));
    }

    private void rotate(RecognitionBar bar, double degrees, Point startPosition) {
        double angle = Math.toRadians(degrees);
        int y = this.centerY + ((int) ((((double) (startPosition.x - this.centerX)) * Math.sin(angle)) + (((double) (startPosition.y - this.centerY)) * Math.cos(angle))));
        bar.setX(this.centerX + ((int) ((((double) (startPosition.x - this.centerX)) * Math.cos(angle)) - (((double) (startPosition.y - this.centerY)) * Math.sin(angle)))));
        bar.setY(y);
        bar.update();
    }
}
