package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzgn extends zzgm<Status> {
    public zzgn(zzn<Status> zzn) {
        super(zzn);
    }

    public final void zza(zzbt zzbt) {
        zzav(new Status(zzbt.statusCode));
    }
}
