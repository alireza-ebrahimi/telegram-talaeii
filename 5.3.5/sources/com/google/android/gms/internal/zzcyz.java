package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzaa;
import com.google.android.gms.common.util.zzw;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Hide
public final class zzcyz {
    private static boolean DEBUG = false;
    private static String TAG = "WakeLock";
    private static ScheduledExecutorService zzimq;
    private static String zzkma = "*gcore*:";
    private final Context mContext;
    private final String zzgjx;
    private final String zzgjz;
    private final WakeLock zzkmb;
    private WorkSource zzkmc;
    private final int zzkmd;
    private final String zzkme;
    private boolean zzkmf;
    private final Map<String, Integer[]> zzkmg;
    private int zzkmh;
    private AtomicInteger zzkmi;

    public zzcyz(Context context, int i, String str) {
        this(context, 1, str, null, context == null ? null : context.getPackageName());
    }

    @Hide
    @SuppressLint({"UnwrappedWakeLock"})
    private zzcyz(Context context, int i, String str, String str2, String str3) {
        this(context, 1, str, null, str3, null);
    }

    @Hide
    @SuppressLint({"UnwrappedWakeLock"})
    private zzcyz(Context context, int i, String str, String str2, String str3, String str4) {
        this.zzkmf = true;
        this.zzkmg = new HashMap();
        this.zzkmi = new AtomicInteger(0);
        zzbq.zzh(str, "Wake lock name can NOT be empty");
        this.zzkmd = i;
        this.zzkme = null;
        this.zzgjz = null;
        this.mContext = context.getApplicationContext();
        if ("com.google.android.gms".equals(context.getPackageName())) {
            this.zzgjx = str;
        } else {
            String valueOf = String.valueOf(zzkma);
            String valueOf2 = String.valueOf(str);
            this.zzgjx = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        this.zzkmb = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzaa.zzda(this.mContext)) {
            if (zzw.zzhb(str3)) {
                str3 = context.getPackageName();
            }
            this.zzkmc = zzaa.zzw(context, str3);
            WorkSource workSource = this.zzkmc;
            if (workSource != null && zzaa.zzda(this.mContext)) {
                if (this.zzkmc != null) {
                    this.zzkmc.add(workSource);
                } else {
                    this.zzkmc = workSource;
                }
                try {
                    this.zzkmb.setWorkSource(this.zzkmc);
                } catch (IllegalArgumentException e) {
                    Log.wtf(TAG, e.toString());
                }
            }
        }
        if (zzimq == null) {
            zzimq = zzbhg.zzanc().newSingleThreadScheduledExecutor();
        }
    }

    private final void zzew(int i) {
        if (this.zzkmb.isHeld()) {
            try {
                this.zzkmb.release();
            } catch (RuntimeException e) {
                if (e.getClass().equals(RuntimeException.class)) {
                    Log.e(TAG, String.valueOf(this.zzgjx).concat("was already released!"), new IllegalStateException());
                    return;
                }
                throw e;
            }
        }
    }

    private final String zzlf(String str) {
        return this.zzkmf ? !TextUtils.isEmpty(str) ? str : this.zzkme : this.zzkme;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void acquire(long r13) {
        /*
        r12 = this;
        r3 = 0;
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r1 = 1;
        r2 = 0;
        r0 = r12.zzkmi;
        r0.incrementAndGet();
        r4 = r12.zzlf(r3);
        monitor-enter(r12);
        r0 = r12.zzkmg;	 Catch:{ all -> 0x00a1 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00a1 }
        if (r0 == 0) goto L_0x001b;
    L_0x0017:
        r0 = r12.zzkmh;	 Catch:{ all -> 0x00a1 }
        if (r0 <= 0) goto L_0x002b;
    L_0x001b:
        r0 = r12.zzkmb;	 Catch:{ all -> 0x00a1 }
        r0 = r0.isHeld();	 Catch:{ all -> 0x00a1 }
        if (r0 != 0) goto L_0x002b;
    L_0x0023:
        r0 = r12.zzkmg;	 Catch:{ all -> 0x00a1 }
        r0.clear();	 Catch:{ all -> 0x00a1 }
        r0 = 0;
        r12.zzkmh = r0;	 Catch:{ all -> 0x00a1 }
    L_0x002b:
        r0 = r12.zzkmf;	 Catch:{ all -> 0x00a1 }
        if (r0 == 0) goto L_0x004c;
    L_0x002f:
        r0 = r12.zzkmg;	 Catch:{ all -> 0x00a1 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x00a1 }
        r0 = (java.lang.Integer[]) r0;	 Catch:{ all -> 0x00a1 }
        if (r0 != 0) goto L_0x008f;
    L_0x0039:
        r0 = r12.zzkmg;	 Catch:{ all -> 0x00a1 }
        r2 = 1;
        r2 = new java.lang.Integer[r2];	 Catch:{ all -> 0x00a1 }
        r3 = 0;
        r5 = 1;
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x00a1 }
        r2[r3] = r5;	 Catch:{ all -> 0x00a1 }
        r0.put(r4, r2);	 Catch:{ all -> 0x00a1 }
        r0 = r1;
    L_0x004a:
        if (r0 != 0) goto L_0x0054;
    L_0x004c:
        r0 = r12.zzkmf;	 Catch:{ all -> 0x00a1 }
        if (r0 != 0) goto L_0x0076;
    L_0x0050:
        r0 = r12.zzkmh;	 Catch:{ all -> 0x00a1 }
        if (r0 != 0) goto L_0x0076;
    L_0x0054:
        com.google.android.gms.common.stats.zze.zzanp();	 Catch:{ all -> 0x00a1 }
        r0 = r12.mContext;	 Catch:{ all -> 0x00a1 }
        r1 = r12.zzkmb;	 Catch:{ all -> 0x00a1 }
        r1 = com.google.android.gms.common.stats.zzc.zza(r1, r4);	 Catch:{ all -> 0x00a1 }
        r2 = 7;
        r3 = r12.zzgjx;	 Catch:{ all -> 0x00a1 }
        r5 = 0;
        r6 = r12.zzkmd;	 Catch:{ all -> 0x00a1 }
        r7 = r12.zzkmc;	 Catch:{ all -> 0x00a1 }
        r7 = com.google.android.gms.common.util.zzaa.zzb(r7);	 Catch:{ all -> 0x00a1 }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x00a1 }
        r0 = r12.zzkmh;	 Catch:{ all -> 0x00a1 }
        r0 = r0 + 1;
        r12.zzkmh = r0;	 Catch:{ all -> 0x00a1 }
    L_0x0076:
        monitor-exit(r12);	 Catch:{ all -> 0x00a1 }
        r0 = r12.zzkmb;
        r0.acquire();
        r0 = 0;
        r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x008e;
    L_0x0082:
        r0 = zzimq;
        r1 = new com.google.android.gms.internal.zzcza;
        r1.<init>(r12);
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r0.schedule(r1, r10, r2);
    L_0x008e:
        return;
    L_0x008f:
        r1 = 0;
        r3 = 0;
        r3 = r0[r3];	 Catch:{ all -> 0x00a1 }
        r3 = r3.intValue();	 Catch:{ all -> 0x00a1 }
        r3 = r3 + 1;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x00a1 }
        r0[r1] = r3;	 Catch:{ all -> 0x00a1 }
        r0 = r2;
        goto L_0x004a;
    L_0x00a1:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x00a1 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcyz.acquire(long):void");
    }

    public final boolean isHeld() {
        return this.zzkmb.isHeld();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void release() {
        /*
        r9 = this;
        r3 = 0;
        r1 = 1;
        r8 = 0;
        r0 = r9.zzkmi;
        r0 = r0.decrementAndGet();
        if (r0 >= 0) goto L_0x0013;
    L_0x000b:
        r0 = TAG;
        r2 = "release without a matched acquire!";
        android.util.Log.e(r0, r2);
    L_0x0013:
        r4 = r9.zzlf(r3);
        monitor-enter(r9);
        r0 = r9.zzkmf;	 Catch:{ all -> 0x0079 }
        if (r0 == 0) goto L_0x0029;
    L_0x001c:
        r0 = r9.zzkmg;	 Catch:{ all -> 0x0079 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x0079 }
        r0 = (java.lang.Integer[]) r0;	 Catch:{ all -> 0x0079 }
        if (r0 != 0) goto L_0x0057;
    L_0x0026:
        r0 = r8;
    L_0x0027:
        if (r0 != 0) goto L_0x0031;
    L_0x0029:
        r0 = r9.zzkmf;	 Catch:{ all -> 0x0079 }
        if (r0 != 0) goto L_0x0052;
    L_0x002d:
        r0 = r9.zzkmh;	 Catch:{ all -> 0x0079 }
        if (r0 != r1) goto L_0x0052;
    L_0x0031:
        com.google.android.gms.common.stats.zze.zzanp();	 Catch:{ all -> 0x0079 }
        r0 = r9.mContext;	 Catch:{ all -> 0x0079 }
        r1 = r9.zzkmb;	 Catch:{ all -> 0x0079 }
        r1 = com.google.android.gms.common.stats.zzc.zza(r1, r4);	 Catch:{ all -> 0x0079 }
        r2 = 8;
        r3 = r9.zzgjx;	 Catch:{ all -> 0x0079 }
        r5 = 0;
        r6 = r9.zzkmd;	 Catch:{ all -> 0x0079 }
        r7 = r9.zzkmc;	 Catch:{ all -> 0x0079 }
        r7 = com.google.android.gms.common.util.zzaa.zzb(r7);	 Catch:{ all -> 0x0079 }
        com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0079 }
        r0 = r9.zzkmh;	 Catch:{ all -> 0x0079 }
        r0 = r0 + -1;
        r9.zzkmh = r0;	 Catch:{ all -> 0x0079 }
    L_0x0052:
        monitor-exit(r9);	 Catch:{ all -> 0x0079 }
        r9.zzew(r8);
        return;
    L_0x0057:
        r2 = 0;
        r2 = r0[r2];	 Catch:{ all -> 0x0079 }
        r2 = r2.intValue();	 Catch:{ all -> 0x0079 }
        if (r2 != r1) goto L_0x0067;
    L_0x0060:
        r0 = r9.zzkmg;	 Catch:{ all -> 0x0079 }
        r0.remove(r4);	 Catch:{ all -> 0x0079 }
        r0 = r1;
        goto L_0x0027;
    L_0x0067:
        r2 = 0;
        r3 = 0;
        r3 = r0[r3];	 Catch:{ all -> 0x0079 }
        r3 = r3.intValue();	 Catch:{ all -> 0x0079 }
        r3 = r3 + -1;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x0079 }
        r0[r2] = r3;	 Catch:{ all -> 0x0079 }
        r0 = r8;
        goto L_0x0027;
    L_0x0079:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0079 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcyz.release():void");
    }

    public final void setReferenceCounted(boolean z) {
        this.zzkmb.setReferenceCounted(false);
        this.zzkmf = false;
    }
}
