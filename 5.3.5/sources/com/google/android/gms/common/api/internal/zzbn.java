package com.google.android.gms.common.api.internal;

final class zzbn implements zzl {
    private /* synthetic */ zzbm zzfzq;

    zzbn(zzbm zzbm) {
        this.zzfzq = zzbm;
    }

    public final void zzbj(boolean z) {
        this.zzfzq.mHandler.sendMessage(this.zzfzq.mHandler.obtainMessage(1, Boolean.valueOf(z)));
    }
}
