package com.google.android.gms.internal.measurement;

final class zzgn implements Runnable {
    private final /* synthetic */ zzhk zzank;
    private final /* synthetic */ zzgm zzanl;

    zzgn(zzgm zzgm, zzhk zzhk) {
        this.zzanl = zzgm;
        this.zzank = zzhk;
    }

    public final void run() {
        this.zzanl.zza(this.zzank);
        this.zzanl.start();
    }
}
