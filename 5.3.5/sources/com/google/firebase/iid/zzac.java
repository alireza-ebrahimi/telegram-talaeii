package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import java.io.IOException;

final class zzac implements Runnable {
    private final zzw zzokq;
    private final long zzolp;
    private final WakeLock zzolq = ((PowerManager) getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzolr;

    zzac(FirebaseInstanceId firebaseInstanceId, zzw zzw, long j) {
        this.zzolr = firebaseInstanceId;
        this.zzokq = zzw;
        this.zzolp = j;
        this.zzolq.setReferenceCounted(false);
    }

    private final boolean zzclt() {
        String zzcld;
        Exception e;
        String str;
        String valueOf;
        zzab zzclc = this.zzolr.zzclc();
        if (zzclc != null && !zzclc.zzru(this.zzokq.zzclm())) {
            return true;
        }
        try {
            zzcld = this.zzolr.zzcld();
            if (zzcld == null) {
                Log.e("FirebaseInstanceId", "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Token successfully retrieved");
            }
            if (zzclc != null && (zzclc == null || zzcld.equals(zzclc.zzlnm))) {
                return true;
            }
            Context context = getContext();
            Parcelable intent = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
            intent2.setClass(context, FirebaseInstanceIdReceiver.class);
            intent2.putExtra("wrapped_intent", intent);
            context.sendBroadcast(intent2);
            return true;
        } catch (IOException e2) {
            e = e2;
            str = "FirebaseInstanceId";
            zzcld = "Token retrieval failed: ";
            valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() == 0 ? zzcld.concat(valueOf) : new String(zzcld));
            return false;
        } catch (SecurityException e3) {
            e = e3;
            str = "FirebaseInstanceId";
            zzcld = "Token retrieval failed: ";
            valueOf = String.valueOf(e.getMessage());
            if (valueOf.length() == 0) {
            }
            Log.e(str, valueOf.length() == 0 ? zzcld.concat(valueOf) : new String(zzcld));
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzclu() {
        /*
        r3 = this;
    L_0x0000:
        r1 = r3.zzolr;
        monitor-enter(r1);
        r0 = com.google.firebase.iid.FirebaseInstanceId.zzcle();	 Catch:{ all -> 0x0022 }
        r0 = r0.zzcls();	 Catch:{ all -> 0x0022 }
        if (r0 != 0) goto L_0x0019;
    L_0x000d:
        r0 = "FirebaseInstanceId";
        r2 = "topic sync succeeded";
        android.util.Log.d(r0, r2);	 Catch:{ all -> 0x0022 }
        r0 = 1;
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
    L_0x0018:
        return r0;
    L_0x0019:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        r1 = r3.zzrv(r0);
        if (r1 != 0) goto L_0x0025;
    L_0x0020:
        r0 = 0;
        goto L_0x0018;
    L_0x0022:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        throw r0;
    L_0x0025:
        r1 = com.google.firebase.iid.FirebaseInstanceId.zzcle();
        r1.zzro(r0);
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzac.zzclu():boolean");
    }

    private final boolean zzrv(String str) {
        String str2;
        String valueOf;
        String[] split = str.split("!");
        if (split.length != 2) {
            return true;
        }
        String str3 = split[0];
        String str4 = split[1];
        int i = -1;
        try {
            switch (str3.hashCode()) {
                case 83:
                    if (str3.equals("S")) {
                        i = 0;
                        break;
                    }
                    break;
                case 85:
                    if (str3.equals("U")) {
                        boolean z = true;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    this.zzolr.zzrm(str4);
                    if (!FirebaseInstanceId.zzclf()) {
                        return true;
                    }
                    Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                    return true;
                case 1:
                    this.zzolr.zzrn(str4);
                    if (!FirebaseInstanceId.zzclf()) {
                        return true;
                    }
                    Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
                    return true;
                default:
                    return true;
            }
        } catch (IOException e) {
            str2 = "FirebaseInstanceId";
            str3 = "Topic sync failed: ";
            valueOf = String.valueOf(e.getMessage());
            Log.e(str2, valueOf.length() == 0 ? new String(str3) : str3.concat(valueOf));
            return false;
        }
        str2 = "FirebaseInstanceId";
        str3 = "Topic sync failed: ";
        valueOf = String.valueOf(e.getMessage());
        if (valueOf.length() == 0) {
        }
        Log.e(str2, valueOf.length() == 0 ? new String(str3) : str3.concat(valueOf));
        return false;
    }

    final Context getContext() {
        return this.zzolr.getApp().getApplicationContext();
    }

    public final void run() {
        Object obj = 1;
        this.zzolq.acquire();
        try {
            this.zzolr.zzcy(true);
            if (this.zzokq.zzcll() == 0) {
                obj = null;
            }
            if (obj == null) {
                this.zzolr.zzcy(false);
            } else if (zzclv()) {
                if (zzclt() && zzclu()) {
                    this.zzolr.zzcy(false);
                } else {
                    this.zzolr.zzcd(this.zzolp);
                }
                this.zzolq.release();
            } else {
                new zzad(this).zzclw();
                this.zzolq.release();
            }
        } finally {
            this.zzolq.release();
        }
    }

    final boolean zzclv() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
