package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;

public final class zzdob<T> {
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzaiq = null;
    private static boolean zzciw = false;
    private static final Object zzkgs = new Object();
    private static Boolean zzkgt = null;

    public static void maybeInit(Context context) {
        if (zzaiq == null) {
            zzch(context);
        }
    }

    public static void zzch(Context context) {
        synchronized (zzkgs) {
            if (VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
            }
            if (zzaiq != context) {
                zzkgt = null;
            }
            zzaiq = context;
        }
        zzciw = false;
    }
}
