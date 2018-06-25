package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzhc extends zzgm<Status> {
    public zzhc(zzn<Status> zzn) {
        super(zzn);
    }

    public final void zza(zzbp zzbp) {
        zzav(new Status(zzbp.statusCode));
    }
}
