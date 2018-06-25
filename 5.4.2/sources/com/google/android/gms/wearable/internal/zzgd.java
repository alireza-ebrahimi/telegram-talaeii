package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.WearableStatusCodes;

public final class zzgd {
    public static Status zzb(int i) {
        return new Status(i, WearableStatusCodes.getStatusCodeString(i));
    }
}
