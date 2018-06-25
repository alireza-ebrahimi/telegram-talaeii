package android.support.v4.p021e;

import android.annotation.TargetApi;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Locale;

@TargetApi(14)
/* renamed from: android.support.v4.e.c */
class C0448c {
    /* renamed from: a */
    private static Method f1199a;
    /* renamed from: b */
    private static Method f1200b;

    static {
        try {
            Class cls = Class.forName("libcore.icu.ICU");
            if (cls != null) {
                f1199a = cls.getMethod("getScript", new Class[]{String.class});
                f1200b = cls.getMethod("addLikelySubtags", new Class[]{String.class});
            }
        } catch (Throwable e) {
            f1199a = null;
            f1200b = null;
            Log.w("ICUCompatIcs", e);
        }
    }

    /* renamed from: a */
    private static String m1931a(String str) {
        try {
            if (f1199a != null) {
                return (String) f1199a.invoke(null, new Object[]{str});
            }
        } catch (Throwable e) {
            Log.w("ICUCompatIcs", e);
        } catch (Throwable e2) {
            Log.w("ICUCompatIcs", e2);
        }
        return null;
    }

    /* renamed from: a */
    public static String m1932a(Locale locale) {
        String b = C0448c.m1933b(locale);
        return b != null ? C0448c.m1931a(b) : null;
    }

    /* renamed from: b */
    private static String m1933b(Locale locale) {
        String locale2 = locale.toString();
        try {
            if (f1200b != null) {
                return (String) f1200b.invoke(null, new Object[]{locale2});
            }
        } catch (Throwable e) {
            Log.w("ICUCompatIcs", e);
        } catch (Throwable e2) {
            Log.w("ICUCompatIcs", e2);
        }
        return locale2;
    }
}
