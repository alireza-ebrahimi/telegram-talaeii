package com.github.ksoichiro.android.observablescrollview;

import android.os.Build.VERSION;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public final class ScrollUtils {
    private ScrollUtils() {
    }

    public static float getFloat(float value, float minValue, float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

    public static int getColorWithAlpha(float alpha, int baseColor) {
        return (Math.min(255, Math.max(0, (int) (255.0f * alpha))) << 24) + (16777215 & baseColor);
    }

    public static void addOnGlobalLayoutListener(final View view, final Runnable runnable) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (VERSION.SDK_INT < 16) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                runnable.run();
            }
        });
    }

    public static int mixColors(int fromColor, int toColor, float toAlpha) {
        float[] fromCmyk = cmykFromRgb(fromColor);
        float[] toCmyk = cmykFromRgb(toColor);
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = Math.min(1.0f, (fromCmyk[i] * (1.0f - toAlpha)) + (toCmyk[i] * toAlpha));
        }
        return -16777216 + (16777215 & rgbFromCmyk(result));
    }

    public static float[] cmykFromRgb(int rgbColor) {
        int red = (16711680 & rgbColor) >> 16;
        int green = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & rgbColor) >> 8;
        int blue = rgbColor & 255;
        float black = Math.min(1.0f - (((float) red) / 255.0f), Math.min(1.0f - (((float) green) / 255.0f), 1.0f - (((float) blue) / 255.0f)));
        float cyan = 1.0f;
        float magenta = 1.0f;
        float yellow = 1.0f;
        if (black != 1.0f) {
            cyan = ((1.0f - (((float) red) / 255.0f)) - black) / (1.0f - black);
            magenta = ((1.0f - (((float) green) / 255.0f)) - black) / (1.0f - black);
            yellow = ((1.0f - (((float) blue) / 255.0f)) - black) / (1.0f - black);
        }
        return new float[]{cyan, magenta, yellow, black};
    }

    public static int rgbFromCmyk(float[] cmyk) {
        float cyan = cmyk[0];
        float magenta = cmyk[1];
        float yellow = cmyk[2];
        float black = cmyk[3];
        return (((((int) ((1.0f - Math.min(1.0f, ((1.0f - black) * cyan) + black)) * 255.0f)) & 255) << 16) + ((((int) ((1.0f - Math.min(1.0f, ((1.0f - black) * magenta) + black)) * 255.0f)) & 255) << 8)) + (((int) ((1.0f - Math.min(1.0f, ((1.0f - black) * yellow) + black)) * 255.0f)) & 255);
    }
}
