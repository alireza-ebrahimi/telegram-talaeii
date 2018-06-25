package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.view.animation.Interpolator;

public class BasicSwapTargetTranslationInterpolator implements Interpolator {
    private final float mHalfValidRange;
    private final float mInvValidRange;
    private final float mThreshold;

    public BasicSwapTargetTranslationInterpolator() {
        this(0.3f);
    }

    public BasicSwapTargetTranslationInterpolator(float threshold) {
        if (threshold < 0.0f || threshold >= 0.5f) {
            throw new IllegalArgumentException("Invalid threshold range: " + threshold);
        }
        float validRange = 1.0f - (2.0f * threshold);
        this.mThreshold = threshold;
        this.mHalfValidRange = validRange * 0.5f;
        this.mInvValidRange = 1.0f / validRange;
    }

    public float getInterpolation(float input) {
        if (Math.abs(input - 0.5f) < this.mHalfValidRange) {
            return (input - this.mThreshold) * this.mInvValidRange;
        }
        return input < 0.5f ? 0.0f : 1.0f;
    }
}
