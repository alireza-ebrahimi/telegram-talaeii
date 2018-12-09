package com.google.android.gms.dynamic;

import android.os.Bundle;

final class zzc implements zza {
    private final /* synthetic */ DeferredLifecycleHelper zzabg;
    private final /* synthetic */ Bundle zzabi;

    zzc(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        this.zzabg = deferredLifecycleHelper;
        this.zzabi = bundle;
    }

    public final int getState() {
        return 1;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zzabg.zzabc.onCreate(this.zzabi);
    }
}
