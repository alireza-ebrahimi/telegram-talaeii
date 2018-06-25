package android.support.v4.view.p023a;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v4.view.a.a */
public final class C0510a {
    /* renamed from: a */
    private static final C0505e f1297a;

    /* renamed from: android.support.v4.view.a.a$e */
    interface C0505e {
        /* renamed from: a */
        int mo361a(AccessibilityEvent accessibilityEvent);

        /* renamed from: a */
        void mo362a(AccessibilityEvent accessibilityEvent, int i);
    }

    /* renamed from: android.support.v4.view.a.a$d */
    static class C0506d implements C0505e {
        C0506d() {
        }

        /* renamed from: a */
        public int mo361a(AccessibilityEvent accessibilityEvent) {
            return 0;
        }

        /* renamed from: a */
        public void mo362a(AccessibilityEvent accessibilityEvent, int i) {
        }
    }

    /* renamed from: android.support.v4.view.a.a$a */
    static class C0507a extends C0506d {
        C0507a() {
        }
    }

    /* renamed from: android.support.v4.view.a.a$b */
    static class C0508b extends C0507a {
        C0508b() {
        }
    }

    /* renamed from: android.support.v4.view.a.a$c */
    static class C0509c extends C0508b {
        C0509c() {
        }

        /* renamed from: a */
        public int mo361a(AccessibilityEvent accessibilityEvent) {
            return C0511b.m2135a(accessibilityEvent);
        }

        /* renamed from: a */
        public void mo362a(AccessibilityEvent accessibilityEvent, int i) {
            C0511b.m2136a(accessibilityEvent, i);
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            f1297a = new C0509c();
        } else if (VERSION.SDK_INT >= 16) {
            f1297a = new C0508b();
        } else if (VERSION.SDK_INT >= 14) {
            f1297a = new C0507a();
        } else {
            f1297a = new C0506d();
        }
    }

    /* renamed from: a */
    public static C0556o m2132a(AccessibilityEvent accessibilityEvent) {
        return new C0556o(accessibilityEvent);
    }

    /* renamed from: a */
    public static void m2133a(AccessibilityEvent accessibilityEvent, int i) {
        f1297a.mo362a(accessibilityEvent, i);
    }

    /* renamed from: b */
    public static int m2134b(AccessibilityEvent accessibilityEvent) {
        return f1297a.mo361a(accessibilityEvent);
    }
}
