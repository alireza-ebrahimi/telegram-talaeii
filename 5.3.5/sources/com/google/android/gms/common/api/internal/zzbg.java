package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

final class zzbg extends zzby {
    private WeakReference<zzba> zzfyv;

    zzbg(zzba zzba) {
        this.zzfyv = new WeakReference(zzba);
    }

    public final void zzaio() {
        zzba zzba = (zzba) this.zzfyv.get();
        if (zzba != null) {
            zzba.resume();
        }
    }
}
