package com.google.android.gms.common.api.internal;

final class zzak implements Runnable {
    private final /* synthetic */ zzaj zzhv;

    zzak(zzaj zzaj) {
        this.zzhv = zzaj;
    }

    public final void run() {
        this.zzhv.zzgk.cancelAvailabilityErrorNotifications(this.zzhv.mContext);
    }
}
