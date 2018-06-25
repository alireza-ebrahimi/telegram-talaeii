package android.support.v4.content.p020a;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.util.DisplayMetrics;

@TargetApi(9)
/* renamed from: android.support.v4.content.a.b */
class C0403b {
    /* renamed from: a */
    static int m1863a(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
    }

    /* renamed from: b */
    static int m1864b(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }

    /* renamed from: c */
    static int m1865c(Resources resources) {
        return Math.min(C0403b.m1864b(resources), C0403b.m1863a(resources));
    }
}
