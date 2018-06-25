package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import javax.annotation.Nullable;

@VisibleForTesting
/* renamed from: com.google.firebase.iid.u */
final class C1949u extends BroadcastReceiver {
    @Nullable
    /* renamed from: a */
    private C1948t f5762a;

    public C1949u(C1948t c1948t) {
        this.f5762a = c1948t;
    }

    /* renamed from: a */
    public final void m8891a() {
        if (FirebaseInstanceId.m8760g()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.f5762a.m8889a().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.f5762a != null && this.f5762a.m8890b()) {
            if (FirebaseInstanceId.m8760g()) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.m8758a(this.f5762a, 0);
            this.f5762a.m8889a().unregisterReceiver(this);
            this.f5762a = null;
        }
    }
}
