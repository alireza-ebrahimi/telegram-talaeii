package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;

final class zzgj implements UncaughtExceptionHandler {
    private final String zzamh;
    private final /* synthetic */ zzgh zzami;

    public zzgj(zzgh zzgh, String str) {
        this.zzami = zzgh;
        Preconditions.checkNotNull(str);
        this.zzamh = str;
    }

    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzami.zzgf().zzis().zzg(this.zzamh, th);
    }
}
