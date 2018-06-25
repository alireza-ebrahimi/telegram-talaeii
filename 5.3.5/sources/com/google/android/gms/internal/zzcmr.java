package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzcmr implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ boolean zzjqb;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ AtomicReference zzjrj;

    zzcmr(zzcme zzcme, AtomicReference atomicReference, zzcif zzcif, boolean z) {
        this.zzjri = zzcme;
        this.zzjrj = atomicReference;
        this.zzjpj = zzcif;
        this.zzjqb = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r5 = this;
        r1 = r5.zzjrj;
        monitor-enter(r1);
        r0 = r5.zzjri;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzjrc;	 Catch:{ RemoteException -> 0x003e }
        if (r0 != 0) goto L_0x0022;
    L_0x000b:
        r0 = r5.zzjri;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzayp();	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zzbau();	 Catch:{ RemoteException -> 0x003e }
        r2 = "Failed to get user properties";
        r0.log(r2);	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzjrj;	 Catch:{ all -> 0x003b }
        r0.notify();	 Catch:{ all -> 0x003b }
        monitor-exit(r1);	 Catch:{ all -> 0x003b }
    L_0x0021:
        return;
    L_0x0022:
        r2 = r5.zzjrj;	 Catch:{ RemoteException -> 0x003e }
        r3 = r5.zzjpj;	 Catch:{ RemoteException -> 0x003e }
        r4 = r5.zzjqb;	 Catch:{ RemoteException -> 0x003e }
        r0 = r0.zza(r3, r4);	 Catch:{ RemoteException -> 0x003e }
        r2.set(r0);	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzjri;	 Catch:{ RemoteException -> 0x003e }
        r0.zzyw();	 Catch:{ RemoteException -> 0x003e }
        r0 = r5.zzjrj;	 Catch:{ all -> 0x003b }
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
        r2 = r5.zzjri;	 Catch:{ all -> 0x0055 }
        r2 = r2.zzayp();	 Catch:{ all -> 0x0055 }
        r2 = r2.zzbau();	 Catch:{ all -> 0x0055 }
        r3 = "Failed to get user properties";
        r2.zzj(r3, r0);	 Catch:{ all -> 0x0055 }
        r0 = r5.zzjrj;	 Catch:{ all -> 0x003b }
        r0.notify();	 Catch:{ all -> 0x003b }
        goto L_0x0039;
    L_0x0055:
        r0 = move-exception;
        r2 = r5.zzjrj;	 Catch:{ all -> 0x003b }
        r2.notify();	 Catch:{ all -> 0x003b }
        throw r0;	 Catch:{ all -> 0x003b }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcmr.run():void");
    }
}
