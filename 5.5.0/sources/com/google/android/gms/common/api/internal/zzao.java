package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import javax.annotation.concurrent.GuardedBy;

final class zzao extends zzbe {
    private final /* synthetic */ ConnectionProgressReportCallbacks zzia;

    zzao(zzam zzam, zzbc zzbc, ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzia = connectionProgressReportCallbacks;
        super(zzbc);
    }

    @GuardedBy("mLock")
    public final void zzaq() {
        this.zzia.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
