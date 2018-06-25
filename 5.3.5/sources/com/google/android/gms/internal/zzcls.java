package com.google.android.gms.internal;

import android.os.Bundle;

final class zzcls implements Runnable {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zziuw;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ zzclk zzjpy;
    private /* synthetic */ long zzjqd;
    private /* synthetic */ Bundle zzjqe;
    private /* synthetic */ boolean zzjqf;
    private /* synthetic */ boolean zzjqg;
    private /* synthetic */ boolean zzjqh;

    zzcls(zzclk zzclk, String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        this.zzjpy = zzclk;
        this.zzjpm = str;
        this.val$name = str2;
        this.zzjqd = j;
        this.zzjqe = bundle;
        this.zzjqf = z;
        this.zzjqg = z2;
        this.zzjqh = z3;
        this.zziuw = str3;
    }

    public final void run() {
        this.zzjpy.zzb(this.zzjpm, this.val$name, this.zzjqd, this.zzjqe, this.zzjqf, this.zzjqg, this.zzjqh, this.zziuw);
    }
}
