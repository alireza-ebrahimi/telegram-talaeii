package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

final class zzad extends BroadcastReceiver {
    private zzac zzols;

    public zzad(zzac zzac) {
        this.zzols = zzac;
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.zzols != null && this.zzols.zzclv()) {
            if (FirebaseInstanceId.zzclf()) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.zzb(this.zzols, 0);
            this.zzols.getContext().unregisterReceiver(this);
            this.zzols = null;
        }
    }

    public final void zzclw() {
        if (FirebaseInstanceId.zzclf()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.zzols.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
