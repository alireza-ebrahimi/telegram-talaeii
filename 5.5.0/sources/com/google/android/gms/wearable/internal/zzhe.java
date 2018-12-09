package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

final class zzhe extends zzgm<SendMessageResult> {
    public zzhe(ResultHolder<SendMessageResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzga zzga) {
        zza(new zzey(zzgd.zzb(zzga.statusCode), zzga.zzeh));
    }
}
