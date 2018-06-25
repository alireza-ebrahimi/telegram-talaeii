package org.telegram.customization.speechrecognitionview.animators;

import java.util.List;
import org.telegram.customization.speechrecognitionview.RecognitionBar;

public class IdleAnimator implements BarParamsAnimator {
    private static final long IDLE_DURATION = 1500;
    private final List<RecognitionBar> bars;
    private final int floatingAmplitude;
    private boolean isPlaying;
    private long startTimestamp;

    public IdleAnimator(List<RecognitionBar> bars, int floatingAmplitude) {
        this.floatingAmplitude = floatingAmplitude;
        this.bars = bars;
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
            update(this.bars);
        }
    }

    public void update(List<RecognitionBar> bars) {
        long currTimestamp = System.currentTimeMillis();
        if (currTimestamp - this.startTimestamp > IDLE_DURATION) {
            this.startTimestamp += IDLE_DURATION;
        }
        long delta = currTimestamp - this.startTimestamp;
        int i = 0;
        for (RecognitionBar bar : bars) {
            updateCirclePosition(bar, delta, i);
            i++;
        }
    }

    private void updateCirclePosition(RecognitionBar bar, long delta, int num) {
        bar.setY(((int) (Math.sin(Math.toRadians((double) (((((float) delta) / 1500.0f) * 360.0f) + (120.0f * ((float) num))))) * ((double) this.floatingAmplitude))) + bar.getStartY());
        bar.update();
    }
}
