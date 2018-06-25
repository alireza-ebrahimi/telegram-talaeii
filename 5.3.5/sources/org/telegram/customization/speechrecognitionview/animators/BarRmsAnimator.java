package org.telegram.customization.speechrecognitionview.animators;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Random;
import org.telegram.customization.speechrecognitionview.RecognitionBar;

public class BarRmsAnimator implements BarParamsAnimator {
    private static final long BAR_ANIMATION_DOWN_DURATION = 500;
    private static final long BAR_ANIMATION_UP_DURATION = 130;
    private static final float MEDIUM_RMSDB_MAX = 5.5f;
    private static final float QUIT_RMSDB_MAX = 2.0f;
    private final RecognitionBar bar;
    private float fromHeightPart;
    private boolean isPlaying;
    private boolean isUpAnimation;
    private long startTimestamp;
    private float toHeightPart;

    public BarRmsAnimator(RecognitionBar bar) {
        this.bar = bar;
    }

    public void start() {
        this.isPlaying = true;
    }

    public void stop() {
        this.isPlaying = false;
    }

    public void animate() {
        if (this.isPlaying) {
            update();
        }
    }

    public void onRmsChanged(float rmsdB) {
        float newHeightPart;
        if (rmsdB < 2.0f) {
            newHeightPart = 0.2f;
        } else if (rmsdB < 2.0f || rmsdB > MEDIUM_RMSDB_MAX) {
            newHeightPart = 0.7f + new Random().nextFloat();
            if (newHeightPart > 1.0f) {
                newHeightPart = 1.0f;
            }
        } else {
            newHeightPart = 0.3f + new Random().nextFloat();
            if (newHeightPart > 0.6f) {
                newHeightPart = 0.6f;
            }
        }
        if (!newHeightIsSmallerCurrent(newHeightPart)) {
            this.fromHeightPart = ((float) this.bar.getHeight()) / ((float) this.bar.getMaxHeight());
            this.toHeightPart = newHeightPart;
            this.startTimestamp = System.currentTimeMillis();
            this.isUpAnimation = true;
            this.isPlaying = true;
        }
    }

    private boolean newHeightIsSmallerCurrent(float newHeightPart) {
        return ((float) this.bar.getHeight()) / ((float) this.bar.getMaxHeight()) > newHeightPart;
    }

    private void update() {
        long delta = System.currentTimeMillis() - this.startTimestamp;
        if (this.isUpAnimation) {
            animateUp(delta);
        } else {
            animateDown(delta);
        }
    }

    private void animateUp(long delta) {
        boolean finished = false;
        int minHeight = (int) (this.fromHeightPart * ((float) this.bar.getMaxHeight()));
        int toHeight = (int) (((float) this.bar.getMaxHeight()) * this.toHeightPart);
        int height = minHeight + ((int) (new AccelerateInterpolator().getInterpolation(((float) delta) / 130.0f) * ((float) (toHeight - minHeight))));
        if (height >= this.bar.getHeight()) {
            if (height >= toHeight) {
                height = toHeight;
                finished = true;
            }
            this.bar.setHeight(height);
            this.bar.update();
            if (finished) {
                this.isUpAnimation = false;
                this.startTimestamp = System.currentTimeMillis();
            }
        }
    }

    private void animateDown(long delta) {
        int minHeight = this.bar.getRadius() * 2;
        float timePart = ((float) delta) / 500.0f;
        int height = minHeight + ((int) ((1.0f - new DecelerateInterpolator().getInterpolation(timePart)) * ((float) (((int) (((float) this.bar.getMaxHeight()) * this.toHeightPart)) - minHeight))));
        if (height <= this.bar.getHeight()) {
            if (height <= minHeight) {
                finish();
                return;
            }
            this.bar.setHeight(height);
            this.bar.update();
        }
    }

    private void finish() {
        this.bar.setHeight(this.bar.getRadius() * 2);
        this.bar.update();
        this.isPlaying = false;
    }
}
