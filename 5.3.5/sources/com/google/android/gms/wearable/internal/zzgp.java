package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final class zzgp extends zzgm<DeleteDataItemsResult> {
    public zzgp(zzn<DeleteDataItemsResult> zzn) {
        super(zzn);
    }

    public final void zza(zzdg zzdg) {
        zzav(new zzch(zzgd.zzdg(zzdg.statusCode), zzdg.zzlug));
    }
}
