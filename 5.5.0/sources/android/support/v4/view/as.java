package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public final class as {
    /* renamed from: a */
    static final C0581d f1330a;

    /* renamed from: android.support.v4.view.as$d */
    interface C0581d {
        /* renamed from: a */
        boolean mo520a(ViewConfiguration viewConfiguration);
    }

    /* renamed from: android.support.v4.view.as$a */
    static class C0582a implements C0581d {
        C0582a() {
        }

        /* renamed from: a */
        public boolean mo520a(ViewConfiguration viewConfiguration) {
            return true;
        }
    }

    /* renamed from: android.support.v4.view.as$b */
    static class C0583b extends C0582a {
        C0583b() {
        }

        /* renamed from: a */
        public boolean mo520a(ViewConfiguration viewConfiguration) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.as$c */
    static class C0584c extends C0583b {
        C0584c() {
        }

        /* renamed from: a */
        public boolean mo520a(ViewConfiguration viewConfiguration) {
            return at.m2935a(viewConfiguration);
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            f1330a = new C0584c();
        } else if (VERSION.SDK_INT >= 11) {
            f1330a = new C0583b();
        } else {
            f1330a = new C0582a();
        }
    }

    /* renamed from: a */
    public static boolean m2934a(ViewConfiguration viewConfiguration) {
        return f1330a.mo520a(viewConfiguration);
    }
}
