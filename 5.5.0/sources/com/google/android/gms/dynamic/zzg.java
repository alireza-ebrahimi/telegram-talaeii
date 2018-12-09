package com.google.android.gms.dynamic;

final class zzg implements zza {
    private final /* synthetic */ DeferredLifecycleHelper zzabg;

    zzg(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zzabg = deferredLifecycleHelper;
    }

    public final int getState() {
        return 5;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zzabg.zzabc.onResume();
    }
}
