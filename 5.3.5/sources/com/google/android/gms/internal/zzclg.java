package com.google.android.gms.internal;

final class zzclg implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ String zzjpq;
    private /* synthetic */ String zzjpr;
    private /* synthetic */ long zzjps;

    zzclg(zzcko zzcko, String str, String str2, String str3, long j) {
        this.zzjpk = zzcko;
        this.zzjpq = str;
        this.zziuw = str2;
        this.zzjpr = str3;
        this.zzjps = j;
    }

    public final void run() {
        if (this.zzjpq == null) {
            this.zzjpk.zzjev.zzayh().zza(this.zziuw, null);
            return;
        }
        zzclz zzclz = new zzclz();
        zzclz.zzjqj = this.zzjpr;
        zzclz.zzjqk = this.zzjpq;
        zzclz.zzjql = this.zzjps;
        this.zzjpk.zzjev.zzayh().zza(this.zziuw, zzclz);
    }
}
