package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcle implements Callable<List<zzcnn>> {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcko zzjpk;

    zzcle(zzcko zzcko, zzcif zzcif) {
        this.zzjpk = zzcko;
        this.zzjpj = zzcif;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzjpk.zzjev.zzbcc();
        return this.zzjpk.zzjev.zzayj().zzji(this.zzjpj.packageName);
    }
}
