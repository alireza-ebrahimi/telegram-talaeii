package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.zzbq;

public final class zzbr extends zzej {
    private final Object mLock = new Object();
    private zzav zzltp;
    private zzbs zzltt;

    public final void zza(zzbs zzbs) {
        synchronized (this.mLock) {
            this.zzltt = (zzbs) zzbq.checkNotNull(zzbs);
            zzav zzav = this.zzltp;
        }
        if (zzav != null) {
            zzbs.zzb(zzav);
        }
    }

    public final void zzs(int i, int i2) {
        synchronized (this.mLock) {
            zzbs zzbs = this.zzltt;
            zzav zzav = new zzav(i, i2);
            this.zzltp = zzav;
        }
        if (zzbs != null) {
            zzbs.zzb(zzav);
        }
    }
}
