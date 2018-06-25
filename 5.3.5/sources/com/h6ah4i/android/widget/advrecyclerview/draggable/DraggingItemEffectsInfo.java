package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.view.animation.Interpolator;

class DraggingItemEffectsInfo {
    float alpha = 1.0f;
    Interpolator alphaInterpolator = null;
    int durationMillis;
    float rotation = 0.0f;
    Interpolator rotationInterpolator = null;
    float scale = 1.0f;
    Interpolator scaleInterpolator = null;

    DraggingItemEffectsInfo() {
    }
}
