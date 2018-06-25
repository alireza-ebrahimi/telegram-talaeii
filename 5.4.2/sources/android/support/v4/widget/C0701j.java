package android.support.v4.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

@TargetApi(14)
/* renamed from: android.support.v4.widget.j */
class C0701j {
    /* renamed from: a */
    public static Object m3427a(Context context) {
        return new EdgeEffect(context);
    }

    /* renamed from: a */
    public static void m3428a(Object obj, int i, int i2) {
        ((EdgeEffect) obj).setSize(i, i2);
    }

    /* renamed from: a */
    public static boolean m3429a(Object obj) {
        return ((EdgeEffect) obj).isFinished();
    }

    /* renamed from: a */
    public static boolean m3430a(Object obj, float f) {
        ((EdgeEffect) obj).onPull(f);
        return true;
    }

    /* renamed from: a */
    public static boolean m3431a(Object obj, int i) {
        ((EdgeEffect) obj).onAbsorb(i);
        return true;
    }

    /* renamed from: a */
    public static boolean m3432a(Object obj, Canvas canvas) {
        return ((EdgeEffect) obj).draw(canvas);
    }

    /* renamed from: b */
    public static void m3433b(Object obj) {
        ((EdgeEffect) obj).finish();
    }

    /* renamed from: c */
    public static boolean m3434c(Object obj) {
        EdgeEffect edgeEffect = (EdgeEffect) obj;
        edgeEffect.onRelease();
        return edgeEffect.isFinished();
    }
}
