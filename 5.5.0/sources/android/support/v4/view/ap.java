package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.View;

@TargetApi(19)
class ap {
    /* renamed from: a */
    public static void m2906a(View view, int i) {
        view.setAccessibilityLiveRegion(i);
    }

    /* renamed from: a */
    public static boolean m2907a(View view) {
        return view.isLaidOut();
    }

    /* renamed from: b */
    public static boolean m2908b(View view) {
        return view.isAttachedToWindow();
    }
}
