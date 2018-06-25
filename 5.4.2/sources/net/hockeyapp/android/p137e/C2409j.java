package net.hockeyapp.android.p137e;

import android.content.Context;

/* renamed from: net.hockeyapp.android.e.j */
public class C2409j {
    /* renamed from: a */
    private static String f8102a = "versionInfo";

    /* renamed from: a */
    public static String m11888a(Context context) {
        return context != null ? context.getSharedPreferences("HockeyApp", 0).getString(f8102a, "[]") : "[]";
    }
}
