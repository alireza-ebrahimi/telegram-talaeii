package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzcyw;

final class zzcx implements Runnable {
    private /* synthetic */ zzcyw zzfyb;
    private /* synthetic */ zzcv zzgbe;

    zzcx(zzcv zzcv, zzcyw zzcyw) {
        this.zzgbe = zzcv;
        this.zzfyb = zzcyw;
    }

    public final void run() {
        this.zzgbe.zzc(this.zzfyb);
    }
}
