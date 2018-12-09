package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

final class zzjw implements Callable<String> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzjs zzarf;

    zzjw(zzjs zzjs, zzdz zzdz) {
        this.zzarf = zzjs;
        this.zzano = zzdz;
    }

    public final /* synthetic */ Object call() {
        zzdy zza = this.zzarf.zzgh().zzay(this.zzano.packageName) ? this.zzarf.zzg(this.zzano) : this.zzarf.zzje().zzbb(this.zzano.packageName);
        if (zza != null) {
            return zza.getAppInstanceId();
        }
        this.zzarf.zzgf().zziv().log("App info was null when attempting to get app instance id");
        return null;
    }
}
