package com.google.android.gms.dynamic;

import android.os.Bundle;

final class zzd implements zzi {
    private /* synthetic */ Bundle zzaik;
    private /* synthetic */ zza zzhct;

    zzd(zza zza, Bundle bundle) {
        this.zzhct = zza;
        this.zzaik = bundle;
    }

    public final int getState() {
        return 1;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzhct.zzhcp.onCreate(this.zzaik);
    }
}
