package com.google.firebase.iid;

import android.util.Log;

final class zzg implements Runnable {
    private /* synthetic */ zzd zzokk;
    private /* synthetic */ zzf zzokl;

    zzg(zzf zzf, zzd zzd) {
        this.zzokl = zzf;
        this.zzokk = zzd;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzokl.zzokj.handleIntent(this.zzokk.intent);
        this.zzokk.finish();
    }
}
