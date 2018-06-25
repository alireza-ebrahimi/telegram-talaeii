package com.google.android.gms.internal.measurement;

import android.content.Intent;

final /* synthetic */ class zzje implements Runnable {
    private final int zzabp;
    private final zzjd zzapy;
    private final zzfh zzapz;
    private final Intent zzaqa;

    zzje(zzjd zzjd, int i, zzfh zzfh, Intent intent) {
        this.zzapy = zzjd;
        this.zzabp = i;
        this.zzapz = zzfh;
        this.zzaqa = intent;
    }

    public final void run() {
        this.zzapy.zza(this.zzabp, this.zzapz, this.zzaqa);
    }
}
