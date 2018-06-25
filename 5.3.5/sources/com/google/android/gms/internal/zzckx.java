package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzckx implements Callable<List<zzcii>> {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ String zzjpn;

    zzckx(zzcko zzcko, String str, String str2, String str3) {
        this.zzjpk = zzcko;
        this.zziuw = str;
        this.zzjpm = str2;
        this.zzjpn = str3;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzjpk.zzjev.zzbcc();
        return this.zzjpk.zzjev.zzayj().zzi(this.zziuw, this.zzjpm, this.zzjpn);
    }
}
