package org.telegram.customization.easyvideoplayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

class Util {
    Util() {
    }

    static String getDurationString(long durationMs, boolean negativePrefix) {
        Locale locale = Locale.getDefault();
        String str = "%s%02d:%02d";
        Object[] objArr = new Object[3];
        objArr[0] = negativePrefix ? "-" : "";
        objArr[1] = Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(durationMs));
        objArr[2] = Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(durationMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationMs)));
        return String.format(locale, str, objArr);
    }

    static boolean isColorDark(int color) {
        return 1.0d - ((((0.299d * ((double) Color.red(color))) + (0.587d * ((double) Color.green(color)))) + (0.114d * ((double) Color.blue(color)))) / 255.0d) >= 0.5d;
    }

    static int adjustAlpha(int color, float factor) {
        return Color.argb(Math.round(((float) Color.alpha(color)) * factor), Color.red(color), Color.green(color), Color.blue(color));
    }

    static int resolveColor(Context context, @AttrRes int attr) {
        return resolveColor(context, attr, 0);
    }

    private static int resolveColor(Context context, @AttrRes int attr, int fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            int color = a.getColor(0, fallback);
            return color;
        } finally {
            a.recycle();
        }
    }

    static Drawable resolveDrawable(Context context, @AttrRes int attr) {
        return resolveDrawable(context, attr, null);
    }

    private static Drawable resolveDrawable(Context context, @AttrRes int attr, Drawable fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            Drawable d = a.getDrawable(0);
            if (d == null && fallback != null) {
                d = fallback;
            }
            a.recycle();
            return d;
        } catch (Throwable th) {
            a.recycle();
        }
    }
}
