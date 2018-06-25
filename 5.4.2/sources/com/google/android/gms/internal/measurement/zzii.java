package com.google.android.gms.internal.measurement;

final class zzii implements Runnable {
    private final /* synthetic */ zzig zzape;
    private final /* synthetic */ zzif zzapf;

    zzii(zzig zzig, zzif zzif) {
        this.zzape = zzig;
        this.zzapf = zzif;
    }

    public final void run() {
        this.zzape.zza(this.zzapf);
        this.zzape.zzaov = null;
        this.zzape.zzfy().zzb(null);
    }
}
