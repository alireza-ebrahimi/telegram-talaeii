package org.telegram.customization.util;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class ZoomOutPageTransformer implements PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @SuppressLint({"NewApi"})
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1.0f) {
            view.setAlpha(0.0f);
        } else if (position <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        } else if (position <= 1.0f) {
            view.setAlpha(1.0f - position);
            view.setTranslationX(((float) pageWidth) * (-position));
            view.setScaleY(0.75f + (0.25f * (1.0f - Math.abs(position))));
        } else {
            view.setAlpha(0.0f);
        }
    }
}
