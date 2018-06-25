package com.google.android.gms.internal;

final class zzs implements Runnable {
    private /* synthetic */ String zzas;
    private /* synthetic */ long zzat;
    private /* synthetic */ zzr zzau;

    zzs(zzr zzr, String str, long j) {
        this.zzau = zzr;
        this.zzas = str;
        this.zzat = j;
    }

    public final void run() {
        this.zzau.zzae.zza(this.zzas, this.zzat);
        this.zzau.zzae.zzc(toString());
    }
}
