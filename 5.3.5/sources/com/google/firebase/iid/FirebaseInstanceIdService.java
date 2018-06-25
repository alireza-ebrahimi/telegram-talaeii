package com.google.firebase.iid;

import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;

public class FirebaseInstanceIdService extends zzb {
    @Hide
    public final void handleIntent(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            onTokenRefresh();
            return;
        }
        String stringExtra = intent.getStringExtra("CMD");
        if (stringExtra != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                Log.d("FirebaseInstanceId", new StringBuilder((String.valueOf(stringExtra).length() + 21) + String.valueOf(valueOf).length()).append("Received command: ").append(stringExtra).append(" - ").append(valueOf).toString());
            }
            if ("RST".equals(stringExtra) || "RST_FULL".equals(stringExtra)) {
                FirebaseInstanceId.getInstance().zzclg();
            } else if ("SYNC".equals(stringExtra)) {
                FirebaseInstanceId.getInstance().zzclh();
            }
        }
    }

    @WorkerThread
    public void onTokenRefresh() {
    }

    @Hide
    protected final Intent zzp(Intent intent) {
        return (Intent) zzz.zzclq().zzolm.poll();
    }
}
