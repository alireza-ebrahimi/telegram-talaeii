package com.google.android.gms.internal;

final class zzciq implements Runnable {
    private /* synthetic */ zzckj zzjhl;
    private /* synthetic */ zzcip zzjhm;

    zzciq(zzcip zzcip, zzckj zzckj) {
        this.zzjhm = zzcip;
        this.zzjhl = zzckj;
    }

    public final void run() {
        this.zzjhl.zzayo();
        if (zzcke.zzas()) {
            this.zzjhm.zzjev.zzayo().zzh(this);
            return;
        }
        boolean zzea = this.zzjhm.zzea();
        this.zzjhm.zzhhl = 0;
        if (zzea && this.zzjhm.enabled) {
            this.zzjhm.run();
        }
    }
}
