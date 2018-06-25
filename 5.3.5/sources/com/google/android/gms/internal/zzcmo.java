package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzcmo implements Runnable {
    private /* synthetic */ String zziuw;
    private /* synthetic */ zzcif zzjpj;
    private /* synthetic */ String zzjpm;
    private /* synthetic */ String zzjpn;
    private /* synthetic */ zzcme zzjri;
    private /* synthetic */ AtomicReference zzjrj;

    zzcmo(zzcme zzcme, AtomicReference atomicReference, String str, String str2, String str3, zzcif zzcif) {
        this.zzjri = zzcme;
        this.zzjrj = atomicReference;
        this.zziuw = str;
        this.zzjpm = str2;
        this.zzjpn = str3;
        this.zzjpj = zzcif;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r6 = this;
        r1 = r6.zzjrj;
        monitor-enter(r1);
        r0 = r6.zzjri;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzjrc;	 Catch:{ RemoteException -> 0x006b }
        if (r0 != 0) goto L_0x0035;
    L_0x000b:
        r0 = r6.zzjri;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzayp();	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzbau();	 Catch:{ RemoteException -> 0x006b }
        r2 = "Failed to get conditional properties";
        r3 = r6.zziuw;	 Catch:{ RemoteException -> 0x006b }
        r3 = com.google.android.gms.internal.zzcjj.zzjs(r3);	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzjpm;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzjpn;	 Catch:{ RemoteException -> 0x006b }
        r0.zzd(r2, r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzjrj;	 Catch:{ RemoteException -> 0x006b }
        r2 = java.util.Collections.emptyList();	 Catch:{ RemoteException -> 0x006b }
        r0.set(r2);	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzjrj;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
    L_0x0034:
        return;
    L_0x0035:
        r2 = r6.zziuw;	 Catch:{ RemoteException -> 0x006b }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ RemoteException -> 0x006b }
        if (r2 == 0) goto L_0x005b;
    L_0x003d:
        r2 = r6.zzjrj;	 Catch:{ RemoteException -> 0x006b }
        r3 = r6.zzjpm;	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzjpn;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzjpj;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zza(r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006b }
    L_0x004c:
        r0 = r6.zzjri;	 Catch:{ RemoteException -> 0x006b }
        r0.zzyw();	 Catch:{ RemoteException -> 0x006b }
        r0 = r6.zzjrj;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
    L_0x0056:
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        goto L_0x0034;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        throw r0;
    L_0x005b:
        r2 = r6.zzjrj;	 Catch:{ RemoteException -> 0x006b }
        r3 = r6.zziuw;	 Catch:{ RemoteException -> 0x006b }
        r4 = r6.zzjpm;	 Catch:{ RemoteException -> 0x006b }
        r5 = r6.zzjpn;	 Catch:{ RemoteException -> 0x006b }
        r0 = r0.zzk(r3, r4, r5);	 Catch:{ RemoteException -> 0x006b }
        r2.set(r0);	 Catch:{ RemoteException -> 0x006b }
        goto L_0x004c;
    L_0x006b:
        r0 = move-exception;
        r2 = r6.zzjri;	 Catch:{ all -> 0x0093 }
        r2 = r2.zzayp();	 Catch:{ all -> 0x0093 }
        r2 = r2.zzbau();	 Catch:{ all -> 0x0093 }
        r3 = "Failed to get conditional properties";
        r4 = r6.zziuw;	 Catch:{ all -> 0x0093 }
        r4 = com.google.android.gms.internal.zzcjj.zzjs(r4);	 Catch:{ all -> 0x0093 }
        r5 = r6.zzjpm;	 Catch:{ all -> 0x0093 }
        r2.zzd(r3, r4, r5, r0);	 Catch:{ all -> 0x0093 }
        r0 = r6.zzjrj;	 Catch:{ all -> 0x0093 }
        r2 = java.util.Collections.emptyList();	 Catch:{ all -> 0x0093 }
        r0.set(r2);	 Catch:{ all -> 0x0093 }
        r0 = r6.zzjrj;	 Catch:{ all -> 0x0058 }
        r0.notify();	 Catch:{ all -> 0x0058 }
        goto L_0x0056;
    L_0x0093:
        r0 = move-exception;
        r2 = r6.zzjrj;	 Catch:{ all -> 0x0058 }
        r2.notify();	 Catch:{ all -> 0x0058 }
        throw r0;	 Catch:{ all -> 0x0058 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcmo.run():void");
    }
}
