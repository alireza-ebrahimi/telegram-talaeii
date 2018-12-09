package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;

/* renamed from: com.google.firebase.iid.t */
final class C1948t implements Runnable {
    /* renamed from: a */
    private final long f5757a;
    /* renamed from: b */
    private final WakeLock f5758b = ((PowerManager) m8889a().getSystemService("power")).newWakeLock(1, "fiid-sync");
    /* renamed from: c */
    private final FirebaseInstanceId f5759c;
    /* renamed from: d */
    private final C1938i f5760d;
    /* renamed from: e */
    private final C1950v f5761e;

    @VisibleForTesting
    C1948t(FirebaseInstanceId firebaseInstanceId, C1938i c1938i, C1950v c1950v, long j) {
        this.f5759c = firebaseInstanceId;
        this.f5760d = c1938i;
        this.f5761e = c1950v;
        this.f5757a = j;
        this.f5758b.setReferenceCounted(false);
    }

    @VisibleForTesting
    /* renamed from: c */
    private final boolean m8888c() {
        String f;
        Exception e;
        String str;
        String valueOf;
        C1947s e2 = this.f5759c.m8777e();
        if (e2 != null && !e2.m8887b(this.f5760d.m8855b())) {
            return true;
        }
        try {
            f = this.f5759c.m8778f();
            if (f == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (e2 != null && (e2 == null || f.equals(e2.f5754a))) {
                return true;
            }
            Context a = m8889a();
            Parcelable intent = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
            intent2.setClass(a, FirebaseInstanceIdReceiver.class);
            intent2.putExtra("wrapped_intent", intent);
            a.sendBroadcast(intent2);
            return true;
        } catch (IOException e3) {
            e = e3;
            str = "FirebaseInstanceId";
            f = "Token retrieval failed: ";
            valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() == 0 ? f.concat(valueOf) : new String(f));
            return false;
        } catch (SecurityException e4) {
            e = e4;
            str = "FirebaseInstanceId";
            f = "Token retrieval failed: ";
            valueOf = String.valueOf(e.getMessage());
            if (valueOf.length() == 0) {
            }
            Log.e(str, valueOf.length() == 0 ? f.concat(valueOf) : new String(f));
            return false;
        }
    }

    /* renamed from: a */
    final Context m8889a() {
        return this.f5759c.m8773b().m8690a();
    }

    /* renamed from: b */
    final boolean m8890b() {
        ConnectivityManager connectivityManager = (ConnectivityManager) m8889a().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public final void run() {
        Object obj = 1;
        this.f5758b.acquire();
        try {
            this.f5759c.m8772a(true);
            if (this.f5760d.m8854a() == 0) {
                obj = null;
            }
            if (obj == null) {
                this.f5759c.m8772a(false);
            } else if (m8890b()) {
                if (m8888c() && this.f5761e.m8896a(this.f5759c)) {
                    this.f5759c.m8772a(false);
                } else {
                    this.f5759c.m8768a(this.f5757a);
                }
                this.f5758b.release();
            } else {
                new C1949u(this).m8891a();
                this.f5758b.release();
            }
        } finally {
            this.f5758b.release();
        }
    }
}
