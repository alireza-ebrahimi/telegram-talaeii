package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

final class zzal implements ConnectionProgressReportCallbacks {
    private final Api<?> mApi;
    private final boolean zzfo;
    private final WeakReference<zzaj> zzhw;

    public zzal(zzaj zzaj, Api<?> api, boolean z) {
        this.zzhw = new WeakReference(zzaj);
        this.mApi = api;
        this.zzfo = z;
    }

    public final void onReportServiceBinding(ConnectionResult connectionResult) {
        boolean z = false;
        zzaj zzaj = (zzaj) this.zzhw.get();
        if (zzaj != null) {
            if (Looper.myLooper() == zzaj.zzhf.zzfq.getLooper()) {
                z = true;
            }
            Preconditions.checkState(z, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzaj.zzga.lock();
            try {
                if (zzaj.zze(0)) {
                    if (!connectionResult.isSuccess()) {
                        zzaj.zzb(connectionResult, this.mApi, this.zzfo);
                    }
                    if (zzaj.zzar()) {
                        zzaj.zzas();
                    }
                    zzaj.zzga.unlock();
                }
            } finally {
                zzaj.zzga.unlock();
            }
        }
    }
}
