package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzda;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.zzbq;

public final class zzd {
    private Looper zzalj;
    private zzda zzfsp;

    public final zzd zza(Looper looper) {
        zzbq.checkNotNull(looper, "Looper must not be null.");
        this.zzalj = looper;
        return this;
    }

    public final zzd zza(zzda zzda) {
        zzbq.checkNotNull(zzda, "StatusExceptionMapper must not be null.");
        this.zzfsp = zzda;
        return this;
    }

    public final zza zzahy() {
        if (this.zzfsp == null) {
            this.zzfsp = new zzg();
        }
        if (this.zzalj == null) {
            this.zzalj = Looper.getMainLooper();
        }
        return new zza(this.zzfsp, this.zzalj);
    }
}
