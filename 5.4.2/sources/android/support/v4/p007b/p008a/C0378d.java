package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;

@TargetApi(11)
/* renamed from: android.support.v4.b.a.d */
class C0378d {
    /* renamed from: a */
    public static void m1791a(Drawable drawable) {
        drawable.jumpToCurrentState();
    }

    /* renamed from: b */
    public static Drawable m1792b(Drawable drawable) {
        return !(drawable instanceof C0006m) ? new C0387j(drawable) : drawable;
    }
}
