package com.p118i.p119a.p120a;

import android.util.Log;

/* renamed from: com.i.a.a.a */
public final class C1996a {
    /* renamed from: a */
    private static String f5870a = "ThinDownloadManager";
    /* renamed from: b */
    private static boolean f5871b = false;

    /* renamed from: a */
    public static int m9021a(String str) {
        return f5871b ? Log.v(f5870a, str) : 0;
    }

    /* renamed from: a */
    public static int m9022a(String str, String str2) {
        return f5871b ? Log.v(str, str2) : 0;
    }

    /* renamed from: a */
    public static void m9023a(boolean z) {
        f5871b = z;
    }

    /* renamed from: b */
    public static int m9024b(String str) {
        return f5871b ? Log.d(f5870a, str) : 0;
    }
}
