package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

final class zzan extends zzbe {
    private final /* synthetic */ ConnectionResult zzhy;
    private final /* synthetic */ zzam zzhz;

    zzan(zzam zzam, zzbc zzbc, ConnectionResult connectionResult) {
        this.zzhz = zzam;
        this.zzhy = connectionResult;
        super(zzbc);
    }

    @GuardedBy("mLock")
    public final void zzaq() {
        this.zzhz.zzhv.zze(this.zzhy);
    }
}
