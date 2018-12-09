package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.WindowInsets;

@TargetApi(20)
class bf {
    /* renamed from: a */
    public static int m3082a(Object obj) {
        return ((WindowInsets) obj).getSystemWindowInsetBottom();
    }

    /* renamed from: a */
    public static Object m3083a(Object obj, int i, int i2, int i3, int i4) {
        return ((WindowInsets) obj).replaceSystemWindowInsets(i, i2, i3, i4);
    }

    /* renamed from: b */
    public static int m3084b(Object obj) {
        return ((WindowInsets) obj).getSystemWindowInsetLeft();
    }

    /* renamed from: c */
    public static int m3085c(Object obj) {
        return ((WindowInsets) obj).getSystemWindowInsetRight();
    }

    /* renamed from: d */
    public static int m3086d(Object obj) {
        return ((WindowInsets) obj).getSystemWindowInsetTop();
    }
}
