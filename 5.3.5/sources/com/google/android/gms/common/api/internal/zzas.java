package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzas extends zzbj {
    private /* synthetic */ ConnectionResult zzfxw;
    private /* synthetic */ zzar zzfxx;

    zzas(zzar zzar, zzbh zzbh, ConnectionResult connectionResult) {
        this.zzfxx = zzar;
        this.zzfxw = connectionResult;
        super(zzbh);
    }

    public final void zzajj() {
        this.zzfxx.zzfxt.zze(this.zzfxw);
    }
}
