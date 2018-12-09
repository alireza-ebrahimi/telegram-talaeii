package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

public class FirebaseInstanceIdService extends C1926w {
    /* renamed from: a */
    protected final Intent mo3045a(Intent intent) {
        return (Intent) C1945q.m8872a().f5745a.poll();
    }

    /* renamed from: a */
    public void mo3490a() {
    }

    /* renamed from: b */
    public final void mo3046b(Intent intent) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(intent.getAction())) {
            mo3490a();
            return;
        }
        String stringExtra = intent.getStringExtra("CMD");
        if (stringExtra != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                Log.d("FirebaseInstanceId", new StringBuilder((String.valueOf(stringExtra).length() + 21) + String.valueOf(valueOf).length()).append("Received command: ").append(stringExtra).append(" - ").append(valueOf).toString());
            }
            if ("RST".equals(stringExtra) || "RST_FULL".equals(stringExtra)) {
                FirebaseInstanceId.m8755a().m8779h();
            } else if ("SYNC".equals(stringExtra)) {
                FirebaseInstanceId.m8755a().m8780i();
            }
        }
    }
}
