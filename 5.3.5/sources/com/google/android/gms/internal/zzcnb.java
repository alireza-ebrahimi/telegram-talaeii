package com.google.android.gms.internal;

final class zzcnb implements Runnable {
    private /* synthetic */ zzckj zzjhl;
    private /* synthetic */ Runnable zzjsa;

    zzcnb(zzcmy zzcmy, zzckj zzckj, Runnable runnable) {
        this.zzjhl = zzckj;
        this.zzjsa = runnable;
    }

    public final void run() {
        this.zzjhl.zzbcc();
        this.zzjhl.zzj(this.zzjsa);
        this.zzjhl.zzbby();
    }
}
