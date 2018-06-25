package com.google.android.gms.internal;

final class zzcnk extends zzcip {
    private /* synthetic */ zzckj zzjhl;
    private /* synthetic */ zzcnj zzjsh;

    zzcnk(zzcnj zzcnj, zzckj zzckj, zzckj zzckj2) {
        this.zzjsh = zzcnj;
        this.zzjhl = zzckj2;
        super(zzckj);
    }

    public final void run() {
        this.zzjsh.cancel();
        this.zzjsh.zzayp().zzbba().log("Starting upload from DelayedRunnable");
        this.zzjhl.zzbby();
    }
}
