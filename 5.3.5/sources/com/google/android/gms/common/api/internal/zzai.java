package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzai<L> implements zzcl<L> {
    private final DataHolder zzfxb;

    protected zzai(DataHolder dataHolder) {
        this.zzfxb = dataHolder;
    }

    protected abstract void zza(L l, DataHolder dataHolder);

    public final void zzajh() {
        if (this.zzfxb != null) {
            this.zzfxb.close();
        }
    }

    public final void zzu(L l) {
        zza(l, this.zzfxb);
    }
}
