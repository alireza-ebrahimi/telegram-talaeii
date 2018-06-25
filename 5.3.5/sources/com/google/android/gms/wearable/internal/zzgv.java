package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi.DataItemResult;

final class zzgv extends zzgm<DataItemResult> {
    public zzgv(zzn<DataItemResult> zzn) {
        super(zzn);
    }

    public final void zza(zzec zzec) {
        zzav(new zzcg(zzgd.zzdg(zzec.statusCode), zzec.zzluq));
    }
}
