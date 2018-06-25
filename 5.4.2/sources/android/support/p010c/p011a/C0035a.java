package android.support.p010c.p011a;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build.VERSION;
import android.support.v4.p014d.C0432c;

@TargetApi(13)
/* renamed from: android.support.c.a.a */
public class C0035a {
    /* renamed from: a */
    static final C0029b f99a;

    /* renamed from: android.support.c.a.a$b */
    interface C0029b {
        /* renamed from: a */
        void mo23a(Fragment fragment, boolean z);

        /* renamed from: b */
        void mo24b(Fragment fragment, boolean z);
    }

    /* renamed from: android.support.c.a.a$a */
    static class C0030a implements C0029b {
        C0030a() {
        }

        /* renamed from: a */
        public void mo23a(Fragment fragment, boolean z) {
        }

        /* renamed from: b */
        public void mo24b(Fragment fragment, boolean z) {
        }
    }

    /* renamed from: android.support.c.a.a$c */
    static class C0031c extends C0030a {
        C0031c() {
        }

        /* renamed from: a */
        public void mo23a(Fragment fragment, boolean z) {
            C0037c.m114a(fragment, z);
        }
    }

    /* renamed from: android.support.c.a.a$d */
    static class C0032d extends C0031c {
        C0032d() {
        }

        /* renamed from: b */
        public void mo24b(Fragment fragment, boolean z) {
            C0038d.m115a(fragment, z);
        }
    }

    /* renamed from: android.support.c.a.a$e */
    static class C0033e extends C0032d {
        C0033e() {
        }
    }

    /* renamed from: android.support.c.a.a$f */
    static class C0034f extends C0033e {
        C0034f() {
        }

        /* renamed from: b */
        public void mo24b(Fragment fragment, boolean z) {
            C0036b.m113a(fragment, z);
        }
    }

    static {
        if (C0432c.m1912a()) {
            f99a = new C0034f();
        } else if (VERSION.SDK_INT >= 23) {
            f99a = new C0033e();
        } else if (VERSION.SDK_INT >= 15) {
            f99a = new C0032d();
        } else if (VERSION.SDK_INT >= 14) {
            f99a = new C0031c();
        } else {
            f99a = new C0030a();
        }
    }

    /* renamed from: a */
    public static void m111a(Fragment fragment, boolean z) {
        f99a.mo23a(fragment, z);
    }

    /* renamed from: b */
    public static void m112b(Fragment fragment, boolean z) {
        f99a.mo24b(fragment, z);
    }
}
