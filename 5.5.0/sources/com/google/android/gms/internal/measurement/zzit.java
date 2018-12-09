package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zzit implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    zzit(zzij zzij, AtomicReference atomicReference, String str, String str2, String str3, zzdz zzdz) {
        this.zzapn = zzij;
        this.zzapo = atomicReference;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
        this.zzano = zzdz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r6 = this;
        r1 = r6.zzapo;
        monitor-enter(r1);
        r0 = r6.zzapn;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzaph;	 Catch:{ RemoteException -> 0x006b }
        if (r0 != 0) goto L_0x0035;
    L_0x000b:
        r0 = r6.zzapn;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzgf();	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzis();	 Catch:{ RemoteException -> 0x006b }
        r2 = "Failed to get conditional properties";
        r3 = r6.zzant;	 Catch:{ RemoteException -> 0x006b }
        r3 = com.google.android.gms.internal.measurement.zzfh.zzbl(r3);	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzanr;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzans;	 Catch:{ RemoteException -> 0x006b }
        r0.zzd(r2, r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzapo;	 Catch:{ RemoteException -> 0x006b }
        r2 = java.util.Collections.emptyList();	 Catch:{ RemoteException -> 0x006b }
        r0.set(r2);	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzapo;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
    L_0x0034:
        return;
    L_0x0035:
        r2 = r6.zzant;	 Catch:{ RemoteException -> 0x006b }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ RemoteException -> 0x006b }
        if (r2 == 0) goto L_0x005b;
    L_0x003d:
        r2 = r6.zzapo;	 Catch:{ RemoteException -> 0x006b }
        r3 = r6.zzanr;	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzans;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzano;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zza(r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006b }
    L_0x004c:
        r0 = r6.zzapn;	 Catch:{ RemoteException -> 0x006b }
        r0.zzcu();	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzapo;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
    L_0x0056:
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        goto L_0x0034;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        throw r0;
    L_0x005b:
        r2 = r6.zzapo;	 Catch:{ RemoteException -> 0x006b }
        r3 = r6.zzant;	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzanr;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzans;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zze(r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006b }
        goto L_0x004c;
    L_0x006b:
        r0 = move-exception;
        r2 = r6.zzapn;	 Catch:{ all -> 0x0093 }
        r2 = r2.zzgf();	 Catch:{ all -> 0x0093 }
        r2 = r2.zzis();	 Catch:{ all -> 0x0093 }
        r3 = "Failed to get conditional properties";
        r4 = r6.zzant;	 Catch:{ all -> 0x0093 }
        r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4);	 Catch:{ all -> 0x0093 }
        r5 = r6.zzanr;	 Catch:{ all -> 0x0093 }
        r2.zzd(r3, r4, r5, r0);	 Catch:{ all -> 0x0093 }
        r0 = r6.zzapo;	 Catch:{ all -> 0x0093 }
        r2 = java.util.Collections.emptyList();	 Catch:{ all -> 0x0093 }
        r0.set(r2);	 Catch:{ all -> 0x0093 }
        r0 = r6.zzapo;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
        goto L_0x0056;
    L_0x0093:
        r0 = move-exception;
        r2 = r6.zzapo;	 Catch:{ all -> 0x0058 }
        r2.notify();	 Catch:{ all -> 0x0058 }
        throw r0;	 Catch:{ all -> 0x0058 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzit.run():void");
    }
}
