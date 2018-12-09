package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zziw implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ boolean zzaoj;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    zziw(zzij zzij, AtomicReference atomicReference, zzdz zzdz, boolean z) {
        this.zzapn = zzij;
        this.zzapo = atomicReference;
        this.zzano = zzdz;
        this.zzaoj = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r5 = this;
        r1 = r5.zzapo;
        monitor-enter(r1);
        r0 = r5.zzapn;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzaph;	 Catch:{ RemoteException -> 0x003e }
        if (r0 != 0) goto L_0x0022;
    L_0x000b:
        r0 = r5.zzapn;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzgf();	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzis();	 Catch:{ RemoteException -> 0x003e }
        r2 = "Failed to get user properties";
        r0.log(r2);	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzapo;	 Catch:{ all -> 0x003b }
        r0.notify();	 Catch:{ all -> 0x003b }
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
    L_0x0021:
        return;
    L_0x0022:
        r2 = r5.zzapo;	 Catch:{ RemoteException -> 0x003e }
        r3 = r5.zzano;	 Catch:{ RemoteException -> 0x003e }
        r4 = r5.zzaoj;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zza(r3, r4);	 Catch:{ RemoteException -> 0x003e }
        r2.set(r0);	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzapn;	 Catch:{ RemoteException -> 0x003e }
        r0.zzcu();	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzapo;	 Catch:{ all -> 0x003b }
        r0.notify();	 Catch:{ all -> 0x003b }
    L_0x0039:
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
        goto L_0x0021;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
        throw r0;
    L_0x003e:
        r0 = move-exception;
        r2 = r5.zzapn;	 Catch:{ all -> 0x0055 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x0055 }
        r2 = r2.zzis();	 Catch:{ all -> 0x0055 }
        r3 = "Failed to get user properties";
        r2.zzg(r3, r0);	 Catch:{ all -> 0x0055 }
        r0 = r5.zzapo;	 Catch:{ all -> 0x003b }
        r0.notify();	 Catch:{ all -> 0x003b }
        goto L_0x0039;
    L_0x0055:
        r0 = move-exception;
        r2 = r5.zzapo;	 Catch:{ all -> 0x003b }
        r2.notify();	 Catch:{ all -> 0x003b }
        throw r0;	 Catch:{ all -> 0x003b }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zziw.run():void");
    }
}
