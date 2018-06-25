package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Status;

final class zzaf implements zza {
    private /* synthetic */ BasePendingResult zzfwy;
    private /* synthetic */ zzae zzfwz;

    zzaf(zzae zzae, BasePendingResult basePendingResult) {
        this.zzfwz = zzae;
        this.zzfwy = basePendingResult;
    }

    public final void zzr(Status status) {
        this.zzfwz.zzfww.remove(this.zzfwy);
    }
}
