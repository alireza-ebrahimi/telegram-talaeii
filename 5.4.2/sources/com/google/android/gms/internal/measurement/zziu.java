package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zziu implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ boolean zzaoj;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    zziu(zzij zzij, AtomicReference atomicReference, String str, String str2, String str3, boolean z, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzapo = atomicReference;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
        this.zzaoj = z;
        this.zzano = zzdz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r7 = this;
        r1 = r7.zzapo;
        monitor-enter(r1);
        r0 = r7.zzapn;	 Catch:{ RemoteException -> 0x006f }
        r0 = r0.zzaph;	 Catch:{ RemoteException -> 0x006f }
        if (r0 != 0) goto L_0x0035;
    L_0x000b:
        r0 = r7.zzapn;	 Catch:{ RemoteException -> 0x006f }
        r0 = r0.zzgf();	 Catch:{ RemoteException -> 0x006f }
        r0 = r0.zzis();	 Catch:{ RemoteException -> 0x006f }
        r2 = "Failed to get user properties";
        r3 = r7.zzant;	 Catch:{ RemoteException -> 0x006f }
        r3 = com.google.android.gms.internal.measurement.zzfh.zzbl(r3);	 Catch:{ RemoteException -> 0x006f }
        r4 = r7.zzanr;	 Catch:{ RemoteException -> 0x006f }
        r5 = r7.zzans;	 Catch:{ RemoteException -> 0x006f }
        r0.zzd(r2, r3, r4, r5);	 Catch:{ RemoteException -> 0x006f }
        r0 = r7.zzapo;	 Catch:{ RemoteException -> 0x006f }
        r2 = java.util.Collections.emptyList();	 Catch:{ RemoteException -> 0x006f }
        r0.set(r2);	 Catch:{ RemoteException -> 0x006f }
        r0 = r7.zzapo;	 Catch:{ all -> 0x005a }
        r0.notify();	 Catch:{ all -> 0x005a }
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
    L_0x0034:
        return;
    L_0x0035:
        r2 = r7.zzant;	 Catch:{ RemoteException -> 0x006f }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ RemoteException -> 0x006f }
        if (r2 == 0) goto L_0x005d;
    L_0x003d:
        r2 = r7.zzapo;	 Catch:{ RemoteException -> 0x006f }
        r3 = r7.zzanr;	 Catch:{ RemoteException -> 0x006f }
        r4 = r7.zzans;	 Catch:{ RemoteException -> 0x006f }
        r5 = r7.zzaoj;	 Catch:{ RemoteException -> 0x006f }
        r6 = r7.zzano;	 Catch:{ RemoteException -> 0x006f }
        r0 = r0.zza(r3, r4, r5, r6);	 Catch:{ RemoteException -> 0x006f }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006f }
    L_0x004e:
        r0 = r7.zzapn;	 Catch:{ RemoteException -> 0x006f }
        r0.zzcu();	 Catch:{ RemoteException -> 0x006f }
        r0 = r7.zzapo;	 Catch:{ all -> 0x005a }
        r0.notify();	 Catch:{ all -> 0x005a }
    L_0x0058:
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
        goto L_0x0034;
    L_0x005a:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005a }
        throw r0;
    L_0x005d:
        r2 = r7.zzapo;	 Catch:{ RemoteException -> 0x006f }
        r3 = r7.zzant;	 Catch:{ RemoteException -> 0x006f }
        r4 = r7.zzanr;	 Catch:{ RemoteException -> 0x006f }
        r5 = r7.zzans;	 Catch:{ RemoteException -> 0x006f }
        r6 = r7.zzaoj;	 Catch:{ RemoteException -> 0x006f }
        r0 = r0.zza(r3, r4, r5, r6);	 Catch:{ RemoteException -> 0x006f }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006f }
        goto L_0x004e;
    L_0x006f:
        r0 = move-exception;
        r2 = r7.zzapn;	 Catch:{ all -> 0x0097 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x0097 }
        r2 = r2.zzis();	 Catch:{ all -> 0x0097 }
        r3 = "Failed to get user properties";
        r4 = r7.zzant;	 Catch:{ all -> 0x0097 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x0097 }
        r5 = r7.zzanr;	 Catch:{ all -> 0x0097 }
        r2.zzd(r3, r4, r5, r0);	 Catch:{ all -> 0x0097 }
        r0 = r7.zzapo;	 Catch:{ all -> 0x0097 }
        r2 = java.util.Collections.emptyList();	 Catch:{ all -> 0x0097 }
        r0.set(r2);	 Catch:{ all -> 0x0097 }
        r0 = r7.zzapo;	 Catch:{ all -> 0x005a }
        r0.notify();	 Catch:{ all -> 0x005a }
        goto L_0x0058;
    L_0x0097:
        r0 = move-exception;
        r2 = r7.zzapo;	 Catch:{ all -> 0x005a }
        r2.notify();	 Catch:{ all -> 0x005a }
        throw r0;	 Catch:{ all -> 0x005a }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zziu.run():void");
    }
}
