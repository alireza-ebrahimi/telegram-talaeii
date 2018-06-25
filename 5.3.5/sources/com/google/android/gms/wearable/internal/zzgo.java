package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzgo extends zzgm<Status> {
    public zzgo(zzn<Status> zzn) {
        super(zzn);
    }

    public final void zzb(zzbt zzbt) {
        zzav(new Status(zzbt.statusCode));
    }
}
