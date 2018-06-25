package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.WorkerThread;

final class zzcne extends zzcip {
    private /* synthetic */ zzcnd zzjse;

    zzcne(zzcnd zzcnd, zzckj zzckj) {
        this.zzjse = zzcnd;
        super(zzckj);
    }

    @WorkerThread
    public final void run() {
        zzclh zzclh = this.zzjse;
        zzclh.zzwj();
        zzclh.zzayp().zzbba().zzj("Session started, time", Long.valueOf(zzclh.zzxx().elapsedRealtime()));
        zzclh.zzayq().zzjmc.set(false);
        zzclh.zzayd().zzd("auto", "_s", new Bundle());
        zzclh.zzayq().zzjmd.set(zzclh.zzxx().currentTimeMillis());
    }
}
