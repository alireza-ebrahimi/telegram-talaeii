package com.google.android.gms.internal;

final class zzclt implements Runnable {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ long zzjqd;
    private /* synthetic */ Object zzjqi;

    zzclt(zzclk zzclk, String str, String str2, Object obj, long j) {
        this.zzjpy = zzclk;
        this.zzjpm = str;
        this.val$name = str2;
        this.zzjqi = obj;
        this.zzjqd = j;
    }

    public final void run() {
        this.zzjpy.zza(this.zzjpm, this.val$name, this.zzjqi, this.zzjqd);
    }
}
