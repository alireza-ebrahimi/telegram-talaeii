package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzcmh implements Runnable {
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ AtomicReference zzjrj;

    zzcmh(zzcme zzcme, AtomicReference atomicReference, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjrj = atomicReference;
        this.zzjpj = zzcif;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r4 = this;
        r1 = r4.zzjrj;
        monitor-enter(r1);
        r0 = r4.zzjri;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzjrc;	 Catch:{ RemoteException -> 0x005a }
        if (r0 != 0) goto L_0x0022;
    L_0x000b:
        r0 = r4.zzjri;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzayp();	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzbau();	 Catch:{ RemoteException -> 0x005a }
        r2 = "Failed to get app instance id";
        r0.log(r2);	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzjrj;	 Catch:{ all -> 0x0057 }
        r0.notify();	 Catch:{ all -> 0x0057 }
        monitor-exit(r1);	 Catch:{ all -> 0x0057 }
    L_0x0021:
        return;
    L_0x0022:
        r2 = r4.zzjrj;	 Catch:{ RemoteException -> 0x005a }
        r3 = r4.zzjpj;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzc(r3);	 Catch:{ RemoteException -> 0x005a }
        r2.set(r0);	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzjrj;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.get();	 Catch:{ RemoteException -> 0x005a }
        r0 = (java.lang.String) r0;	 Catch:{ RemoteException -> 0x005a }
        if (r0 == 0) goto L_0x004b;
    L_0x0037:
        r2 = r4.zzjri;	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzayd();	 Catch:{ RemoteException -> 0x005a }
        r2.zzjx(r0);	 Catch:{ RemoteException -> 0x005a }
        r2 = r4.zzjri;	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzayq();	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzjlt;	 Catch:{ RemoteException -> 0x005a }
        r2.zzjy(r0);	 Catch:{ RemoteException -> 0x005a }
    L_0x004b:
        r0 = r4.zzjri;	 Catch:{ RemoteException -> 0x005a }
        r0.zzyw();	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzjrj;	 Catch:{ all -> 0x0057 }
        r0.notify();	 Catch:{ all -> 0x0057 }
    L_0x0055:
        monitor-exit(r1);	 Catch:{ all -> 0x0057 }
        goto L_0x0021;
    L_0x0057:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0057 }
        throw r0;
    L_0x005a:
        r0 = move-exception;
        r2 = r4.zzjri;	 Catch:{ all -> 0x0071 }
        r2 = r2.zzayp();	 Catch:{ all -> 0x0071 }
        r2 = r2.zzbau();	 Catch:{ all -> 0x0071 }
        r3 = "Failed to get app instance id";
        r2.zzj(r3, r0);	 Catch:{ all -> 0x0071 }
        r0 = r4.zzjrj;	 Catch:{ all -> 0x0057 }
        r0.notify();	 Catch:{ all -> 0x0057 }
        goto L_0x0055;
    L_0x0071:
        r0 = move-exception;
        r2 = r4.zzjrj;	 Catch:{ all -> 0x0057 }
        r2.notify();	 Catch:{ all -> 0x0057 }
        throw r0;	 Catch:{ all -> 0x0057 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcmh.run():void");
    }
}
