package org.telegram.customization.easyvideoplayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* renamed from: org.telegram.customization.easyvideoplayer.c */
class C2726c {
    /* renamed from: a */
    static int m12630a(int i, float f) {
        return Color.argb(Math.round(((float) Color.alpha(i)) * f), Color.red(i), Color.green(i), Color.blue(i));
    }

    /* renamed from: a */
    static int m12631a(Context context, int i) {
        return C2726c.m12632a(context, i, 0);
    }

    /* renamed from: a */
    private static int m12632a(Context context, int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            int color = obtainStyledAttributes.getColor(0, i2);
            return color;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    private static Drawable m12633a(Context context, int i, Drawable drawable) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i});
        try {
            Drawable drawable2 = obtainStyledAttributes.getDrawable(0);
            if (drawable2 != null || drawable == null) {
                drawable = drawable2;
            }
            obtainStyledAttributes.recycle();
            return drawable;
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    static String m12634a(long j, boolean z) {
        Locale locale = Locale.getDefault();
        String str = "%s%02d:%02d";
        Object[] objArr = new Object[3];
        objArr[0] = z ? "-" : TtmlNode.ANONYMOUS_REGION_ID;
        objArr[1] = Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j));
        objArr[2] = Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)));
        return String.format(locale, str, objArr);
    }

    /* renamed from: a */
    static boolean m12635a(int i) {
        return 1.0d - ((((0.299d * ((double) Color.red(i))) + (0.587d * ((double) Color.green(i)))) + (0.114d * ((double) Color.blue(i)))) / 255.0d) >= 0.5d;
    }

    /* renamed from: b */
    static Drawable m12636b(Context context, int i) {
        return C2726c.m12633a(context, i, null);
    }
}
