package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

final class zzgw implements Callable<List<zzee>> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;

    zzgw(zzgo zzgo, zzdz zzdz, String str, String str2) {
        this.zzanp = zzgo;
        this.zzano = zzdz;
        this.zzanr = str;
        this.zzans = str2;
    }

    public final /* synthetic */ Object call() {
        this.zzanp.zzajy.zzlg();
        return this.zzanp.zzajy.zzje().zzc(this.zzano.packageName, this.zzanr, this.zzans);
    }
}
