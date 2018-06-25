package android.support.v4.p021e;

import android.os.Build.VERSION;
import java.util.Locale;

/* renamed from: android.support.v4.e.a */
public final class C0446a {
    /* renamed from: a */
    private static final C0442a f1197a;

    /* renamed from: android.support.v4.e.a$a */
    interface C0442a {
        /* renamed from: a */
        String mo318a(Locale locale);
    }

    /* renamed from: android.support.v4.e.a$b */
    static class C0443b implements C0442a {
        C0443b() {
        }

        /* renamed from: a */
        public String mo318a(Locale locale) {
            return null;
        }
    }

    /* renamed from: android.support.v4.e.a$c */
    static class C0444c implements C0442a {
        C0444c() {
        }

        /* renamed from: a */
        public String mo318a(Locale locale) {
            return C0448c.m1932a(locale);
        }
    }

    /* renamed from: android.support.v4.e.a$d */
    static class C0445d implements C0442a {
        C0445d() {
        }

        /* renamed from: a */
        public String mo318a(Locale locale) {
            return C0447b.m1930a(locale);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            f1197a = new C0445d();
        } else if (i >= 14) {
            f1197a = new C0444c();
        } else {
            f1197a = new C0443b();
        }
    }

    /* renamed from: a */
    public static String m1929a(Locale locale) {
        return f1197a.mo318a(locale);
    }
}
