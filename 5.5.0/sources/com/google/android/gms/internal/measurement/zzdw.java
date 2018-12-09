package com.google.android.gms.internal.measurement;

final class zzdw implements Runnable {
    private final /* synthetic */ String zzadi;
    private final /* synthetic */ long zzadj;
    private final /* synthetic */ zzdu zzadk;

    zzdw(zzdu zzdu, String str, long j) {
        this.zzadk = zzdu;
        this.zzadi = str;
        this.zzadj = j;
    }

    public final void run() {
        this.zzadk.zzb(this.zzadi, this.zzadj);
    }
}
