package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;

class zzgm<T> extends zza {
    private zzn<T> zzegn;

    public zzgm(zzn<T> zzn) {
        this.zzegn = zzn;
    }

    public final void zzav(T t) {
        zzn zzn = this.zzegn;
        if (zzn != null) {
            zzn.setResult(t);
            this.zzegn = null;
        }
    }
}
