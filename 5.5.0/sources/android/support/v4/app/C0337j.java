package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.app.j */
public final class C0337j {
    /* renamed from: a */
    private static final C0335b f1030a;

    /* renamed from: android.support.v4.app.j$b */
    private static class C0335b {
        C0335b() {
        }

        /* renamed from: a */
        public int mo242a(Context context, String str, String str2) {
            return 1;
        }

        /* renamed from: a */
        public String mo243a(String str) {
            return null;
        }
    }

    /* renamed from: android.support.v4.app.j$a */
    private static class C0336a extends C0335b {
        C0336a() {
        }

        /* renamed from: a */
        public int mo242a(Context context, String str, String str2) {
            return C0338k.m1456a(context, str, str2);
        }

        /* renamed from: a */
        public String mo243a(String str) {
            return C0338k.m1457a(str);
        }
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            f1030a = new C0336a();
        } else {
            f1030a = new C0335b();
        }
    }

    /* renamed from: a */
    public static int m1454a(Context context, String str, String str2) {
        return f1030a.mo242a(context, str, str2);
    }

    /* renamed from: a */
    public static String m1455a(String str) {
        return f1030a.mo243a(str);
    }
}
