package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcku implements Callable<List<zzcnn>> {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ String zzjpn;

    zzcku(zzcko zzcko, zzcif zzcif, String str, String str2) {
        this.zzjpk = zzcko;
        this.zzjpj = zzcif;
        this.zzjpm = str;
        this.zzjpn = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzjpk.zzjev.zzbcc();
        return this.zzjpk.zzjev.zzayj().zzh(this.zzjpj.packageName, this.zzjpm, this.zzjpn);
    }
}
