package com.google.android.gms.internal;

import java.io.IOException;

public final class zzdnk extends zzflm<zzdnk> {
    private static volatile zzdnk[] zzlwp;
    public int type;
    public zzdnl zzlwq;

    public zzdnk() {
        this.type = 1;
        this.zzlwq = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzdnk zzae(com.google.android.gms.internal.zzflj r7) throws java.io.IOException {
        /*
        r6 = this;
    L_0x0000:
        r0 = r7.zzcxx();
        switch(r0) {
            case 0: goto L_0x000d;
            case 8: goto L_0x000e;
            case 18: goto L_0x0040;
            default: goto L_0x0007;
        };
    L_0x0007:
        r0 = super.zza(r7, r0);
        if (r0 != 0) goto L_0x0000;
    L_0x000d:
        return r6;
    L_0x000e:
        r1 = r7.getPosition();
        r2 = r7.zzcym();	 Catch:{ IllegalArgumentException -> 0x0035 }
        switch(r2) {
            case 1: goto L_0x003d;
            case 2: goto L_0x003d;
            case 3: goto L_0x003d;
            case 4: goto L_0x003d;
            case 5: goto L_0x003d;
            case 6: goto L_0x003d;
            case 7: goto L_0x003d;
            case 8: goto L_0x003d;
            case 9: goto L_0x003d;
            case 10: goto L_0x003d;
            case 11: goto L_0x003d;
            case 12: goto L_0x003d;
            case 13: goto L_0x003d;
            case 14: goto L_0x003d;
            case 15: goto L_0x003d;
            default: goto L_0x0019;
        };	 Catch:{ IllegalArgumentException -> 0x0035 }
    L_0x0019:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = 36;
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r2 = r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = " is not a valid enum Type";
        r2 = r2.append(r4);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r2 = r2.toString();	 Catch:{ IllegalArgumentException -> 0x0035 }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x0035 }
        throw r3;	 Catch:{ IllegalArgumentException -> 0x0035 }
    L_0x0035:
        r2 = move-exception;
        r7.zzmw(r1);
        r6.zza(r7, r0);
        goto L_0x0000;
    L_0x003d:
        r6.type = r2;	 Catch:{ IllegalArgumentException -> 0x0035 }
        goto L_0x0000;
    L_0x0040:
        r0 = r6.zzlwq;
        if (r0 != 0) goto L_0x004b;
    L_0x0044:
        r0 = new com.google.android.gms.internal.zzdnl;
        r0.<init>();
        r6.zzlwq = r0;
    L_0x004b:
        r0 = r6.zzlwq;
        r7.zza(r0);
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdnk.zzae(com.google.android.gms.internal.zzflj):com.google.android.gms.internal.zzdnk");
    }

    public static zzdnk[] zzbmc() {
        if (zzlwp == null) {
            synchronized (zzflq.zzpvt) {
                if (zzlwp == null) {
                    zzlwp = new zzdnk[0];
                }
            }
        }
        return zzlwp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdnk)) {
            return false;
        }
        zzdnk zzdnk = (zzdnk) obj;
        if (this.type != zzdnk.type) {
            return false;
        }
        if (this.zzlwq == null) {
            if (zzdnk.zzlwq != null) {
                return false;
            }
        } else if (!this.zzlwq.equals(zzdnk.zzlwq)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzdnk.zzpvl == null || zzdnk.zzpvl.isEmpty() : this.zzpvl.equals(zzdnk.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + this.type;
        zzdnl zzdnl = this.zzlwq;
        hashCode = ((zzdnl == null ? 0 : zzdnl.hashCode()) + (hashCode * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzae(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        zzflk.zzad(1, this.type);
        if (this.zzlwq != null) {
            zzflk.zza(2, this.zzlwq);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq() + zzflk.zzag(1, this.type);
        return this.zzlwq != null ? zzq + zzflk.zzb(2, this.zzlwq) : zzq;
    }
}
