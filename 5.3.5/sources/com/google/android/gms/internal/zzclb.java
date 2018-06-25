package com.google.android.gms.internal;

import java.util.concurrent.Callable;

final class zzclb implements Callable<byte[]> {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ zzcix zzjpo;

    zzclb(zzcko zzcko, zzcix zzcix, String str) {
        this.zzjpk = zzcko;
        this.zzjpo = zzcix;
        this.zziuw = str;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzjpk.zzjev.zzbcc();
        return this.zzjpk.zzjev.zza(this.zzjpo, this.zziuw);
    }
}
