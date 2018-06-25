package android.support.v4.view.p023a;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;

/* renamed from: android.support.v4.view.a.c */
public final class C0516c {
    /* renamed from: a */
    private static final C0512d f1298a;

    /* renamed from: android.support.v4.view.a.c$d */
    interface C0512d {
        /* renamed from: a */
        boolean mo363a(AccessibilityManager accessibilityManager);
    }

    /* renamed from: android.support.v4.view.a.c$c */
    static class C0513c implements C0512d {
        C0513c() {
        }

        /* renamed from: a */
        public boolean mo363a(AccessibilityManager accessibilityManager) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.a.c$a */
    static class C0514a extends C0513c {
        C0514a() {
        }

        /* renamed from: a */
        public boolean mo363a(AccessibilityManager accessibilityManager) {
            return C0517d.m2141a(accessibilityManager);
        }
    }

    /* renamed from: android.support.v4.view.a.c$b */
    static class C0515b extends C0514a {
        C0515b() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            f1298a = new C0515b();
        } else if (VERSION.SDK_INT >= 14) {
            f1298a = new C0514a();
        } else {
            f1298a = new C0513c();
        }
    }

    /* renamed from: a */
    public static boolean m2140a(AccessibilityManager accessibilityManager) {
        return f1298a.mo363a(accessibilityManager);
    }
}
