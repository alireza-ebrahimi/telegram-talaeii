package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzdna extends zzdmx {
    private final zzn<BooleanResult> zzgbf;

    public zzdna(zzn<BooleanResult> zzn) {
        this.zzgbf = zzn;
    }

    public final void zza(Status status, boolean z, Bundle bundle) {
        this.zzgbf.setResult(new BooleanResult(status, z));
    }
}
