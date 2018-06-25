package com.google.android.gms.internal.measurement;

final class zzgs implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzee zzanq;

    zzgs(zzgo zzgo, zzee zzee) {
        this.zzanp = zzgo;
        this.zzanq = zzee;
    }

    public final void run() {
        this.zzanp.zzajy.zzlg();
        zzjs zza = this.zzanp.zzajy;
        zzee zzee = this.zzanq;
        zzdz zzca = zza.zzca(zzee.packageName);
        if (zzca != null) {
            zza.zzc(zzee, zzca);
        }
    }
}
