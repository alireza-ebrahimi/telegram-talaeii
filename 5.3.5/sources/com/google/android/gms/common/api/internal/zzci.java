package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;

public final class zzci<L> {
    private final zzcj zzgas;
    private volatile L zzgat;
    private final zzck<L> zzgau;

    zzci(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zzgas = new zzcj(this, looper);
        this.zzgat = zzbq.checkNotNull(l, "Listener must not be null");
        this.zzgau = new zzck(l, zzbq.zzgv(str));
    }

    public final void clear() {
        this.zzgat = null;
    }

    public final void zza(zzcl<? super L> zzcl) {
        zzbq.checkNotNull(zzcl, "Notifier must not be null");
        this.zzgas.sendMessage(this.zzgas.obtainMessage(1, zzcl));
    }

    public final boolean zzafr() {
        return this.zzgat != null;
    }

    @NonNull
    public final zzck<L> zzakx() {
        return this.zzgau;
    }

    final void zzb(zzcl<? super L> zzcl) {
        Object obj = this.zzgat;
        if (obj == null) {
            zzcl.zzajh();
            return;
        }
        try {
            zzcl.zzu(obj);
        } catch (RuntimeException e) {
            zzcl.zzajh();
            throw e;
        }
    }
}
