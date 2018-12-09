package android.support.v4.p021e;

import android.annotation.TargetApi;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Locale;

@TargetApi(23)
/* renamed from: android.support.v4.e.b */
class C0447b {
    /* renamed from: a */
    private static Method f1198a;

    static {
        try {
            f1198a = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    /* renamed from: a */
    public static String m1930a(Locale locale) {
        try {
            return ((Locale) f1198a.invoke(null, new Object[]{locale})).getScript();
        } catch (Throwable e) {
            Log.w("ICUCompatIcs", e);
            return locale.getScript();
        } catch (Throwable e2) {
            Log.w("ICUCompatIcs", e2);
            return locale.getScript();
        }
    }
}
