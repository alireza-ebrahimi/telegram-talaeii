package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzi implements zze {
    private static zzi zzgkp = new zzi();

    private zzi() {
    }

    public static zze zzanq() {
        return zzgkp;
    }

    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public final long nanoTime() {
        return System.nanoTime();
    }
}
