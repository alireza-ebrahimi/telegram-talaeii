package com.google.android.gms.internal.measurement;

import android.os.Bundle;

final class zzjj extends zzeo {
    private final /* synthetic */ zzji zzaqg;

    zzjj(zzji zzji, zzhj zzhj) {
        this.zzaqg = zzji;
        super(zzhj);
    }

    public final void run() {
        zzhh zzhh = this.zzaqg;
        zzhh.zzab();
        zzhh.zzgf().zziz().zzg("Session started, time", Long.valueOf(zzhh.zzbt().elapsedRealtime()));
        zzhh.zzgg().zzakt.set(false);
        zzhh.zzfv().zza("auto", "_s", new Bundle());
        zzhh.zzgg().zzaku.set(zzhh.zzbt().currentTimeMillis());
    }
}
