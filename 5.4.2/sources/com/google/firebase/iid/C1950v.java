package com.google.firebase.iid;

import android.support.annotation.GuardedBy;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

/* renamed from: com.google.firebase.iid.v */
final class C1950v {
    @GuardedBy("this")
    /* renamed from: a */
    private int f5763a = 0;
    @GuardedBy("this")
    /* renamed from: b */
    private final Map<Integer, TaskCompletionSource<Void>> f5764b = new C0464a();
    @GuardedBy("itself")
    /* renamed from: c */
    private final C1946r f5765c;

    C1950v(C1946r c1946r) {
        this.f5765c = c1946r;
    }

    /* renamed from: a */
    private static boolean m8892a(FirebaseInstanceId firebaseInstanceId, String str) {
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
                    firebaseInstanceId.m8769a(str4);
                    if (!FirebaseInstanceId.m8760g()) {
                        return true;
                    }
                    Log.d("FirebaseInstanceId", "subscribe operation succeeded");
                    return true;
                case 1:
                    firebaseInstanceId.m8774b(str4);
                    if (!FirebaseInstanceId.m8760g()) {
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

    /* renamed from: a */
    private final synchronized boolean m8893a(String str) {
        boolean z;
        synchronized (this.f5765c) {
            String a = this.f5765c.m8879a();
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (a.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                valueOf = String.valueOf(",");
                valueOf2 = String.valueOf(str);
                this.f5765c.m8880a(a.substring((valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).length()));
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    @GuardedBy("this")
    /* renamed from: b */
    private final String m8894b() {
        synchronized (this.f5765c) {
            Object a = this.f5765c.m8879a();
        }
        if (!TextUtils.isEmpty(a)) {
            String[] split = a.split(",");
            if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                return split[1];
            }
        }
        return null;
    }

    /* renamed from: a */
    final synchronized boolean m8895a() {
        return m8894b() != null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    final boolean m8896a(com.google.firebase.iid.FirebaseInstanceId r4) {
        /*
        r3 = this;
    L_0x0000:
        monitor-enter(r3);
        r1 = r3.m8894b();	 Catch:{ all -> 0x001c }
        if (r1 != 0) goto L_0x0013;
    L_0x0007:
        r0 = "FirebaseInstanceId";
        r1 = "topic sync succeeded";
        android.util.Log.d(r0, r1);	 Catch:{ all -> 0x001c }
        r0 = 1;
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
    L_0x0012:
        return r0;
    L_0x0013:
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
        r0 = com.google.firebase.iid.C1950v.m8892a(r4, r1);
        if (r0 != 0) goto L_0x001f;
    L_0x001a:
        r0 = 0;
        goto L_0x0012;
    L_0x001c:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001c }
        throw r0;
    L_0x001f:
        monitor-enter(r3);
        r0 = r3.f5764b;	 Catch:{ all -> 0x003f }
        r2 = r3.f5763a;	 Catch:{ all -> 0x003f }
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ all -> 0x003f }
        r0 = r0.remove(r2);	 Catch:{ all -> 0x003f }
        r0 = (com.google.android.gms.tasks.TaskCompletionSource) r0;	 Catch:{ all -> 0x003f }
        r3.m8893a(r1);	 Catch:{ all -> 0x003f }
        r1 = r3.f5763a;	 Catch:{ all -> 0x003f }
        r1 = r1 + 1;
        r3.f5763a = r1;	 Catch:{ all -> 0x003f }
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        if (r0 == 0) goto L_0x0000;
    L_0x003a:
        r1 = 0;
        r0.setResult(r1);
        goto L_0x0000;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.v.a(com.google.firebase.iid.FirebaseInstanceId):boolean");
    }
}
