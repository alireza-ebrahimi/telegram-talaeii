package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzcgk extends zzcgs {
    private final zzn<Status> zzgbf;

    public zzcgk(zzn<Status> zzn) {
        this.zzgbf = zzn;
    }

    public final void zza(zzcgl zzcgl) {
        this.zzgbf.setResult(zzcgl.getStatus());
    }
}
