package com.google.android.gms.internal.measurement;

final class zzhd implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzjz zzanv;

    zzhd(zzgo zzgo, zzjz zzjz, zzdz zzdz) {
        this.zzanp = zzgo;
        this.zzanv = zzjz;
        this.zzano = zzdz;
    }

    public final void run() {
        this.zzanp.zzajy.zzlg();
        this.zzanp.zzajy.zzb(this.zzanv, this.zzano);
    }
}
