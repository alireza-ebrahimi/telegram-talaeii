package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.LayoutInflater;

/* renamed from: android.support.v4.view.j */
public final class C0636j {
    /* renamed from: a */
    static final C0632a f1391a;

    /* renamed from: android.support.v4.view.j$a */
    interface C0632a {
        /* renamed from: a */
        C0365n mo556a(LayoutInflater layoutInflater);

        /* renamed from: a */
        void mo557a(LayoutInflater layoutInflater, C0365n c0365n);
    }

    /* renamed from: android.support.v4.view.j$b */
    static class C0633b implements C0632a {
        C0633b() {
        }

        /* renamed from: a */
        public C0365n mo556a(LayoutInflater layoutInflater) {
            return C0638k.m3151a(layoutInflater);
        }

        /* renamed from: a */
        public void mo557a(LayoutInflater layoutInflater, C0365n c0365n) {
            C0638k.m3152a(layoutInflater, c0365n);
        }
    }

    /* renamed from: android.support.v4.view.j$c */
    static class C0634c extends C0633b {
        C0634c() {
        }

        /* renamed from: a */
        public void mo557a(LayoutInflater layoutInflater, C0365n c0365n) {
            C0640l.m3153a(layoutInflater, c0365n);
        }
    }

    /* renamed from: android.support.v4.view.j$d */
    static class C0635d extends C0634c {
        C0635d() {
        }

        /* renamed from: a */
        public void mo557a(LayoutInflater layoutInflater, C0365n c0365n) {
            C0641m.m3155a(layoutInflater, c0365n);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            f1391a = new C0635d();
        } else if (i >= 11) {
            f1391a = new C0634c();
        } else {
            f1391a = new C0633b();
        }
    }

    /* renamed from: a */
    public static C0365n m3149a(LayoutInflater layoutInflater) {
        return f1391a.mo556a(layoutInflater);
    }

    /* renamed from: a */
    public static void m3150a(LayoutInflater layoutInflater, C0365n c0365n) {
        f1391a.mo557a(layoutInflater, c0365n);
    }
}
