package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;

final class zzgn extends zzgm<Status> {
    public zzgn(ResultHolder<Status> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzbt zzbt) {
        zza(new Status(zzbt.statusCode));
    }
}
