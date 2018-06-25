package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.Display;
import android.view.View;

@TargetApi(17)
class ao {
    /* renamed from: a */
    public static int m2899a(View view) {
        return view.getLayoutDirection();
    }

    /* renamed from: a */
    public static void m2900a(View view, int i, int i2, int i3, int i4) {
        view.setPaddingRelative(i, i2, i3, i4);
    }

    /* renamed from: b */
    public static int m2901b(View view) {
        return view.getPaddingStart();
    }

    /* renamed from: c */
    public static int m2902c(View view) {
        return view.getPaddingEnd();
    }

    /* renamed from: d */
    public static int m2903d(View view) {
        return view.getWindowSystemUiVisibility();
    }

    /* renamed from: e */
    public static boolean m2904e(View view) {
        return view.isPaddingRelative();
    }

    /* renamed from: f */
    public static Display m2905f(View view) {
        return view.getDisplay();
    }
}
