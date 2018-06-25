package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;

final class zzgx extends zzgm<GetFdForAssetResult> {
    public zzgx(zzn<GetFdForAssetResult> zzn) {
        super(zzn);
    }

    public final void zza(zzee zzee) {
        zzav(new zzci(zzgd.zzdg(zzee.statusCode), zzee.zzjwp));
    }
}
