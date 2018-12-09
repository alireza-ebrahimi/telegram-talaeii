package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.lang.reflect.Method;

@TargetApi(17)
/* renamed from: android.support.v4.b.a.e */
class C0379e {
    /* renamed from: a */
    private static Method f1143a;
    /* renamed from: b */
    private static boolean f1144b;

    /* renamed from: a */
    public static boolean m1793a(Drawable drawable, int i) {
        if (!f1144b) {
            try {
                f1143a = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[]{Integer.TYPE});
                f1143a.setAccessible(true);
            } catch (Throwable e) {
                Log.i("DrawableCompatJellybeanMr1", "Failed to retrieve setLayoutDirection(int) method", e);
            }
            f1144b = true;
        }
        if (f1143a != null) {
            try {
                f1143a.invoke(drawable, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Throwable e2) {
                Log.i("DrawableCompatJellybeanMr1", "Failed to invoke setLayoutDirection(int) via reflection", e2);
                f1143a = null;
            }
        }
        return false;
    }
}
