package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.Gravity;

/* renamed from: android.support.v4.view.f */
public final class C0625f {
    /* renamed from: a */
    static final C0622a f1389a;

    /* renamed from: android.support.v4.view.f$a */
    interface C0622a {
        /* renamed from: a */
        int mo550a(int i, int i2);

        /* renamed from: a */
        void mo551a(int i, int i2, int i3, Rect rect, Rect rect2, int i4);
    }

    /* renamed from: android.support.v4.view.f$b */
    static class C0623b implements C0622a {
        C0623b() {
        }

        /* renamed from: a */
        public int mo550a(int i, int i2) {
            return -8388609 & i;
        }

        /* renamed from: a */
        public void mo551a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            Gravity.apply(i, i2, i3, rect, rect2);
        }
    }

    /* renamed from: android.support.v4.view.f$c */
    static class C0624c implements C0622a {
        C0624c() {
        }

        /* renamed from: a */
        public int mo550a(int i, int i2) {
            return C0626g.m3122a(i, i2);
        }

        /* renamed from: a */
        public void mo551a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            C0626g.m3123a(i, i2, i3, rect, rect2, i4);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            f1389a = new C0624c();
        } else {
            f1389a = new C0623b();
        }
    }

    /* renamed from: a */
    public static int m3120a(int i, int i2) {
        return f1389a.mo550a(i, i2);
    }

    /* renamed from: a */
    public static void m3121a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        f1389a.mo551a(i, i2, i3, rect, rect2, i4);
    }
}
