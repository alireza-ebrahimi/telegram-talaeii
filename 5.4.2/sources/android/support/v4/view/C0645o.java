package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

/* renamed from: android.support.v4.view.o */
public final class C0645o {
    /* renamed from: a */
    static final C0642a f1395a;

    /* renamed from: android.support.v4.view.o$a */
    interface C0642a {
        /* renamed from: a */
        int mo558a(MarginLayoutParams marginLayoutParams);

        /* renamed from: b */
        int mo559b(MarginLayoutParams marginLayoutParams);
    }

    /* renamed from: android.support.v4.view.o$b */
    static class C0643b implements C0642a {
        C0643b() {
        }

        /* renamed from: a */
        public int mo558a(MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.leftMargin;
        }

        /* renamed from: b */
        public int mo559b(MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.rightMargin;
        }
    }

    /* renamed from: android.support.v4.view.o$c */
    static class C0644c implements C0642a {
        C0644c() {
        }

        /* renamed from: a */
        public int mo558a(MarginLayoutParams marginLayoutParams) {
            return C0646p.m3164a(marginLayoutParams);
        }

        /* renamed from: b */
        public int mo559b(MarginLayoutParams marginLayoutParams) {
            return C0646p.m3165b(marginLayoutParams);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            f1395a = new C0644c();
        } else {
            f1395a = new C0643b();
        }
    }

    /* renamed from: a */
    public static int m3162a(MarginLayoutParams marginLayoutParams) {
        return f1395a.mo558a(marginLayoutParams);
    }

    /* renamed from: b */
    public static int m3163b(MarginLayoutParams marginLayoutParams) {
        return f1395a.mo559b(marginLayoutParams);
    }
}
