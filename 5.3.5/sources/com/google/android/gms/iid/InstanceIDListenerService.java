package com.google.android.gms.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;

public class InstanceIDListenerService extends zzb {
    @Hide
    static void zza(Context context, zzaf zzaf) {
        zzaf.zzawz();
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.putExtra("CMD", "RST");
        intent.setClassName(context, "com.google.android.gms.gcm.GcmReceiver");
        context.sendBroadcast(intent);
    }

    @Hide
    public void handleIntent(Intent intent) {
        if ("com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
            Bundle bundle = null;
            String stringExtra = intent.getStringExtra("subtype");
            if (stringExtra != null) {
                bundle = new Bundle();
                bundle.putString("subtype", stringExtra);
            }
            InstanceID instance = InstanceID.getInstance(this, bundle);
            String stringExtra2 = intent.getStringExtra("CMD");
            if (Log.isLoggable("InstanceID", 3)) {
                Log.d("InstanceID", new StringBuilder((String.valueOf(stringExtra).length() + 34) + String.valueOf(stringExtra2).length()).append("Service command. subtype:").append(stringExtra).append(" command:").append(stringExtra2).toString());
            }
            if ("RST".equals(stringExtra2)) {
                instance.zzawq();
                onTokenRefresh();
            } else if ("RST_FULL".equals(stringExtra2)) {
                if (!InstanceID.zzawr().isEmpty()) {
                    InstanceID.zzawr().zzawz();
                    onTokenRefresh();
                }
            } else if ("SYNC".equals(stringExtra2)) {
                InstanceID.zzawr().zzih(String.valueOf(stringExtra).concat("|T|"));
                onTokenRefresh();
            }
        }
    }

    public void onTokenRefresh() {
    }
}
