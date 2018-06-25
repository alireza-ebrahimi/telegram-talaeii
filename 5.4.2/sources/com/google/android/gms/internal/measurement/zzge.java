package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;

final class zzge implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzgm zzalk;
    private final /* synthetic */ zzfh zzall;
    private final /* synthetic */ long zzalm;
    private final /* synthetic */ Bundle zzaln;
    private final /* synthetic */ PendingResult zzqu;

    zzge(zzgc zzgc, zzgm zzgm, long j, Bundle bundle, Context context, zzfh zzfh, PendingResult pendingResult) {
        this.zzalk = zzgm;
        this.zzalm = j;
        this.zzaln = bundle;
        this.val$context = context;
        this.zzall = zzfh;
        this.zzqu = pendingResult;
    }

    public final void run() {
        long j = this.zzalk.zzgg().zzaki.get();
        long j2 = this.zzalm;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzaln.putLong("click_timestamp", j2);
        }
        this.zzaln.putString("_cis", "referrer broadcast");
        AppMeasurement.getInstance(this.val$context).logEventInternal("auto", "_cmp", this.zzaln);
        this.zzall.zziz().log("Install campaign recorded");
        if (this.zzqu != null) {
            this.zzqu.finish();
        }
    }
}
