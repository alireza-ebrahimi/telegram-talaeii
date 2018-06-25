package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzhf extends zzgm<Status> {
    public zzhf(zzn<Status> zzn) {
        super(zzn);
    }

    public final void zza(zzbn zzbn) {
        zzav(new Status(zzbn.statusCode));
    }
}
