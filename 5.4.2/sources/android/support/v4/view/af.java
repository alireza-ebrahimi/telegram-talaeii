package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.VelocityTracker;

public final class af {
    /* renamed from: a */
    static final C0563c f1316a;

    /* renamed from: android.support.v4.view.af$c */
    interface C0563c {
        /* renamed from: a */
        float mo439a(VelocityTracker velocityTracker, int i);

        /* renamed from: b */
        float mo440b(VelocityTracker velocityTracker, int i);
    }

    /* renamed from: android.support.v4.view.af$a */
    static class C0564a implements C0563c {
        C0564a() {
        }

        /* renamed from: a */
        public float mo439a(VelocityTracker velocityTracker, int i) {
            return velocityTracker.getXVelocity();
        }

        /* renamed from: b */
        public float mo440b(VelocityTracker velocityTracker, int i) {
            return velocityTracker.getYVelocity();
        }
    }

    /* renamed from: android.support.v4.view.af$b */
    static class C0565b implements C0563c {
        C0565b() {
        }

        /* renamed from: a */
        public float mo439a(VelocityTracker velocityTracker, int i) {
            return ag.m2518a(velocityTracker, i);
        }

        /* renamed from: b */
        public float mo440b(VelocityTracker velocityTracker, int i) {
            return ag.m2519b(velocityTracker, i);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            f1316a = new C0565b();
        } else {
            f1316a = new C0564a();
        }
    }

    /* renamed from: a */
    public static float m2516a(VelocityTracker velocityTracker, int i) {
        return f1316a.mo439a(velocityTracker, i);
    }

    /* renamed from: b */
    public static float m2517b(VelocityTracker velocityTracker, int i) {
        return f1316a.mo440b(velocityTracker, i);
    }
}
