package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;

@TargetApi(19)
/* renamed from: android.support.v4.b.a.f */
class C0380f {
    /* renamed from: a */
    public static void m1794a(Drawable drawable, boolean z) {
        drawable.setAutoMirrored(z);
    }

    /* renamed from: a */
    public static boolean m1795a(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    /* renamed from: b */
    public static Drawable m1796b(Drawable drawable) {
        return !(drawable instanceof C0006m) ? new C0389k(drawable) : drawable;
    }

    /* renamed from: c */
    public static int m1797c(Drawable drawable) {
        return drawable.getAlpha();
    }
}
