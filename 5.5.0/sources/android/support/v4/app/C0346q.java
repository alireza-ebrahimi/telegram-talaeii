package android.support.v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(9)
/* renamed from: android.support.v4.app.q */
class C0346q {
    /* renamed from: a */
    private static Method f1060a;
    /* renamed from: b */
    private static boolean f1061b;
    /* renamed from: c */
    private static Method f1062c;
    /* renamed from: d */
    private static boolean f1063d;

    /* renamed from: a */
    public static IBinder m1486a(Bundle bundle, String str) {
        if (!f1061b) {
            try {
                f1060a = Bundle.class.getMethod("getIBinder", new Class[]{String.class});
                f1060a.setAccessible(true);
            } catch (Throwable e) {
                Throwable e2;
                Log.i("BundleCompatGingerbread", "Failed to retrieve getIBinder method", e2);
            }
            f1061b = true;
        }
        if (f1060a != null) {
            try {
                return (IBinder) f1060a.invoke(bundle, new Object[]{str});
            } catch (InvocationTargetException e3) {
                e2 = e3;
            } catch (IllegalAccessException e4) {
                e2 = e4;
            } catch (IllegalArgumentException e5) {
                e2 = e5;
            }
        }
        return null;
        Log.i("BundleCompatGingerbread", "Failed to invoke getIBinder via reflection", e2);
        f1060a = null;
        return null;
    }

    /* renamed from: a */
    public static void m1487a(Bundle bundle, String str, IBinder iBinder) {
        if (!f1063d) {
            try {
                f1062c = Bundle.class.getMethod("putIBinder", new Class[]{String.class, IBinder.class});
                f1062c.setAccessible(true);
            } catch (Throwable e) {
                Throwable e2;
                Log.i("BundleCompatGingerbread", "Failed to retrieve putIBinder method", e2);
            }
            f1063d = true;
        }
        if (f1062c != null) {
            try {
                f1062c.invoke(bundle, new Object[]{str, iBinder});
                return;
            } catch (InvocationTargetException e3) {
                e2 = e3;
            } catch (IllegalAccessException e4) {
                e2 = e4;
            } catch (IllegalArgumentException e5) {
                e2 = e5;
            }
        } else {
            return;
        }
        Log.i("BundleCompatGingerbread", "Failed to invoke putIBinder via reflection", e2);
        f1062c = null;
    }
}
