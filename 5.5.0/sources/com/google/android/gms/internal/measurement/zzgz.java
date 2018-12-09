package com.google.android.gms.internal.measurement;

final class zzgz implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzew zzanu;

    zzgz(zzgo zzgo, zzew zzew, zzdz zzdz) {
        this.zzanp = zzgo;
        this.zzanu = zzew;
        this.zzano = zzdz;
    }

    public final void run() {
        this.zzanp.zzajy.zzlg();
        this.zzanp.zzajy.zzb(this.zzanu, this.zzano);
    }
}
