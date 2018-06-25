package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

final class zzhe implements Callable<List<zzkb>> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;

    zzhe(zzgo zzgo, zzdz zzdz) {
        this.zzanp = zzgo;
        this.zzano = zzdz;
    }

    public final /* synthetic */ Object call() {
        this.zzanp.zzajy.zzlg();
        return this.zzanp.zzajy.zzje().zzba(this.zzano.packageName);
    }
}
