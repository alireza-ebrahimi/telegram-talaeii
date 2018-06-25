package com.google.android.gms.iid;

import android.util.Log;

final class zzg implements Runnable {
    private /* synthetic */ zzd zzimn;
    private /* synthetic */ zzf zzimo;

    zzg(zzf zzf, zzd zzd) {
        this.zzimo = zzf;
        this.zzimn = zzd;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzimo.zzimm.handleIntent(this.zzimn.intent);
        this.zzimn.finish();
    }
}
