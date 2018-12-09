package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.content.i */
public final class C0418i {
    /* renamed from: a */
    private static final C0414a f1164a;

    /* renamed from: android.support.v4.content.i$a */
    interface C0414a {
        /* renamed from: a */
        Intent mo317a(ComponentName componentName);
    }

    /* renamed from: android.support.v4.content.i$b */
    static class C0415b implements C0414a {
        C0415b() {
        }

        /* renamed from: a */
        public Intent mo317a(ComponentName componentName) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(componentName);
            intent.addCategory("android.intent.category.LAUNCHER");
            return intent;
        }
    }

    /* renamed from: android.support.v4.content.i$c */
    static class C0416c extends C0415b {
        C0416c() {
        }

        /* renamed from: a */
        public Intent mo317a(ComponentName componentName) {
            return C0419j.m1886a(componentName);
        }
    }

    /* renamed from: android.support.v4.content.i$d */
    static class C0417d extends C0416c {
        C0417d() {
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 15) {
            f1164a = new C0417d();
        } else if (i >= 11) {
            f1164a = new C0416c();
        } else {
            f1164a = new C0415b();
        }
    }

    /* renamed from: a */
    public static Intent m1885a(ComponentName componentName) {
        return f1164a.mo317a(componentName);
    }
}
