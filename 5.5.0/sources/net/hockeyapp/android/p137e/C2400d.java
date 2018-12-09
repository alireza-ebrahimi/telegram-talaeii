package net.hockeyapp.android.p137e;

import android.util.Log;

/* renamed from: net.hockeyapp.android.e.d */
public class C2400d {
    /* renamed from: a */
    private static int f8082a = 6;

    /* renamed from: a */
    public static void m11840a(String str) {
        C2400d.m11845b(null, str);
    }

    /* renamed from: a */
    public static void m11841a(String str, String str2) {
        String d = C2400d.m11848d(str);
        if (f8082a <= 2) {
            Log.v(d, str2);
        }
    }

    /* renamed from: a */
    public static void m11842a(String str, String str2, Throwable th) {
        String d = C2400d.m11848d(str);
        if (f8082a <= 6) {
            Log.e(d, str2, th);
        }
    }

    /* renamed from: a */
    public static void m11843a(String str, Throwable th) {
        C2400d.m11842a(null, str, th);
    }

    /* renamed from: b */
    public static void m11844b(String str) {
        C2400d.m11847c(null, str);
    }

    /* renamed from: b */
    public static void m11845b(String str, String str2) {
        String d = C2400d.m11848d(str);
        if (f8082a <= 3) {
            Log.d(d, str2);
        }
    }

    /* renamed from: c */
    public static void m11846c(String str) {
        C2400d.m11849d(null, str);
    }

    /* renamed from: c */
    public static void m11847c(String str, String str2) {
        String d = C2400d.m11848d(str);
        if (f8082a <= 5) {
            Log.w(d, str2);
        }
    }

    /* renamed from: d */
    static String m11848d(String str) {
        return (str == null || str.length() == 0 || str.length() > 23) ? "HockeyApp" : str;
    }

    /* renamed from: d */
    public static void m11849d(String str, String str2) {
        String d = C2400d.m11848d(str);
        if (f8082a <= 6) {
            Log.e(d, str2);
        }
    }
}
