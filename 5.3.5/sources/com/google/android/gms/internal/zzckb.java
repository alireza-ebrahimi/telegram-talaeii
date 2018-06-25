package com.google.android.gms.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

final class zzckb implements Runnable {
    private /* synthetic */ Context val$context;
    private /* synthetic */ PendingResult zzdue;
    private /* synthetic */ zzckj zzjhl;
    private /* synthetic */ long zzjmq;
    private /* synthetic */ Bundle zzjmr;
    private /* synthetic */ zzcjj zzjms;

    zzckb(zzcka zzcka, zzckj zzckj, long j, Bundle bundle, Context context, zzcjj zzcjj, PendingResult pendingResult) {
        this.zzjhl = zzckj;
        this.zzjmq = j;
        this.zzjmr = bundle;
        this.val$context = context;
        this.zzjms = zzcjj;
        this.zzdue = pendingResult;
    }

    public final void run() {
        long j = this.zzjhl.zzayq().zzjls.get();
        long j2 = this.zzjmq;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzjmr.putLong("click_timestamp", j2);
        }
        AppMeasurement.getInstance(this.val$context).logEventInternal("auto", "_cmp", this.zzjmr);
        this.zzjms.zzbba().log("Install campaign recorded");
        if (this.zzdue != null) {
            this.zzdue.finish();
        }
    }
}
