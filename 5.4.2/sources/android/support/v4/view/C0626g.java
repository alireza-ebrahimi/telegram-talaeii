package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.view.Gravity;

@TargetApi(17)
/* renamed from: android.support.v4.view.g */
class C0626g {
    /* renamed from: a */
    public static int m3122a(int i, int i2) {
        return Gravity.getAbsoluteGravity(i, i2);
    }

    /* renamed from: a */
    public static void m3123a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        Gravity.apply(i, i2, i3, rect, rect2, i4);
    }
}
