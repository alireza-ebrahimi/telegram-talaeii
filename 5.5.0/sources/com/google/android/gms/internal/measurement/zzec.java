package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Looper;

public final class zzec {
    private final boolean zzaep = false;

    zzec(Context context) {
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
