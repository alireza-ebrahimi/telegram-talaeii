package com.google.android.gms.dynamic;

import java.util.Iterator;

final class zza implements OnDelegateCreatedListener<T> {
    private final /* synthetic */ DeferredLifecycleHelper zzabg;

    zza(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zzabg = deferredLifecycleHelper;
    }

    public final void onDelegateCreated(T t) {
        this.zzabg.zzabc = t;
        Iterator it = this.zzabg.zzabe.iterator();
        while (it.hasNext()) {
            ((zza) it.next()).zza(this.zzabg.zzabc);
        }
        this.zzabg.zzabe.clear();
        this.zzabg.zzabd = null;
    }
}
