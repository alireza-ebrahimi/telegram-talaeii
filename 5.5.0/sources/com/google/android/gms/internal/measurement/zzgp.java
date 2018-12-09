package com.google.android.gms.internal.measurement;

final class zzgp implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;

    zzgp(zzgo zzgo, zzdz zzdz) {
        this.zzanp = zzgo;
        this.zzano = zzdz;
    }

    public final void run() {
        this.zzanp.zzajy.zzlg();
        this.zzanp.zzajy.zze(this.zzano);
    }
}
