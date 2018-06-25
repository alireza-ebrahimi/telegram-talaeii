package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.lang.Thread.UncaughtExceptionHandler;

final class zzckg implements UncaughtExceptionHandler {
    private final String zzjnl;
    private /* synthetic */ zzcke zzjnm;

    public zzckg(zzcke zzcke, String str) {
        this.zzjnm = zzcke;
        zzbq.checkNotNull(str);
        this.zzjnl = str;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzjnm.zzayp().zzbau().zzj(this.zzjnl, th);
    }
}
