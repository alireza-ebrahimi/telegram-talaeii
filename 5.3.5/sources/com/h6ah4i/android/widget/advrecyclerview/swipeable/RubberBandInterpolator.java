package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.view.animation.Interpolator;

class RubberBandInterpolator implements Interpolator {
    private final float mLimit;

    public RubberBandInterpolator(float limit) {
        this.mLimit = limit;
    }

    public float getInterpolation(float input) {
        float t = 1.0f - input;
        return this.mLimit * (1.0f - (t * t));
    }
}
