package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zzim implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    zzim(zzij zzij, AtomicReference atomicReference, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzapo = atomicReference;
        this.zzano = zzdz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r4 = this;
        r1 = r4.zzapo;
        monitor-enter(r1);
        r0 = r4.zzapn;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzaph;	 Catch:{ RemoteException -> 0x005a }
        if (r0 != 0) goto L_0x0022;
    L_0x000b:
        r0 = r4.zzapn;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzgf();	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzis();	 Catch:{ RemoteException -> 0x005a }
        r2 = "Failed to get app instance id";
        r0.log(r2);	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzapo;	 Catch:{ all -> 0x0057 }
        r0.notify();	 Catch:{ all -> 0x0057 }
        monitor-exit(r1);	 Catch:{ all -> 0x0057 }
    L_0x0021:
        return;
    L_0x0022:
        r2 = r4.zzapo;	 Catch:{ RemoteException -> 0x005a }
        r3 = r4.zzano;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.zzc(r3);	 Catch:{ RemoteException -> 0x005a }
        r2.set(r0);	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzapo;	 Catch:{ RemoteException -> 0x005a }
        r0 = r0.get();	 Catch:{ RemoteException -> 0x005a }
        r0 = (java.lang.String) r0;	 Catch:{ RemoteException -> 0x005a }
        if (r0 == 0) goto L_0x004b;
    L_0x0037:
        r2 = r4.zzapn;	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzfv();	 Catch:{ RemoteException -> 0x005a }
        r2.zzbq(r0);	 Catch:{ RemoteException -> 0x005a }
        r2 = r4.zzapn;	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzgg();	 Catch:{ RemoteException -> 0x005a }
        r2 = r2.zzakk;	 Catch:{ RemoteException -> 0x005a }
        r2.zzbr(r0);	 Catch:{ RemoteException -> 0x005a }
    L_0x004b:
        r0 = r4.zzapn;	 Catch:{ RemoteException -> 0x005a }
        r0.zzcu();	 Catch:{ RemoteException -> 0x005a }
        r0 = r4.zzapo;	 Catch:{ all -> 0x0057 }
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
        r2 = r4.zzapn;	 Catch:{ all -> 0x0071 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x0071 }
        r2 = r2.zzis();	 Catch:{ all -> 0x0071 }
        r3 = "Failed to get app instance id";
        r2.zzg(r3, r0);	 Catch:{ all -> 0x0071 }
        r0 = r4.zzapo;	 Catch:{ all -> 0x0057 }
        r0.notify();	 Catch:{ all -> 0x0057 }
        goto L_0x0055;
    L_0x0071:
        r0 = move-exception;
        r2 = r4.zzapo;	 Catch:{ all -> 0x0057 }
        r2.notify();	 Catch:{ all -> 0x0057 }
        throw r0;	 Catch:{ all -> 0x0057 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzim.run():void");
    }
}
