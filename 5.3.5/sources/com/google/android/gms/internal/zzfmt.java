package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmt extends zzflm<zzfmt> implements Cloneable {
    private int zzpzp;
    private int zzpzq;

    public zzfmt() {
        this.zzpzp = -1;
        this.zzpzq = 0;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzfmt zzbo(com.google.android.gms.internal.zzflj r7) throws java.io.IOException {
        /*
        r6 = this;
    L_0x0000:
        r0 = r7.zzcxx();
        switch(r0) {
            case 0: goto L_0x000d;
            case 8: goto L_0x000e;
            case 16: goto L_0x0040;
            default: goto L_0x0007;
        };
    L_0x0007:
        r0 = super.zza(r7, r0);
        if (r0 != 0) goto L_0x0000;
    L_0x000d:
        return r6;
    L_0x000e:
        r1 = r7.getPosition();
        r2 = r7.zzcya();	 Catch:{ IllegalArgumentException -> 0x0035 }
        switch(r2) {
            case -1: goto L_0x003d;
            case 0: goto L_0x003d;
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
            case 16: goto L_0x003d;
            case 17: goto L_0x003d;
            default: goto L_0x0019;
        };	 Catch:{ IllegalArgumentException -> 0x0035 }
    L_0x0019:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = 43;
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r2 = r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = " is not a valid enum NetworkType";
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
        r6.zzpzp = r2;	 Catch:{ IllegalArgumentException -> 0x0035 }
        goto L_0x0000;
    L_0x0040:
        r1 = r7.getPosition();
        r2 = r7.zzcya();	 Catch:{ IllegalArgumentException -> 0x0067 }
        switch(r2) {
            case 0: goto L_0x006f;
            case 1: goto L_0x006f;
            case 2: goto L_0x006f;
            case 3: goto L_0x006f;
            case 4: goto L_0x006f;
            case 5: goto L_0x006f;
            case 6: goto L_0x006f;
            case 7: goto L_0x006f;
            case 8: goto L_0x006f;
            case 9: goto L_0x006f;
            case 10: goto L_0x006f;
            case 11: goto L_0x006f;
            case 12: goto L_0x006f;
            case 13: goto L_0x006f;
            case 14: goto L_0x006f;
            case 15: goto L_0x006f;
            case 16: goto L_0x006f;
            case 100: goto L_0x006f;
            default: goto L_0x004b;
        };	 Catch:{ IllegalArgumentException -> 0x0067 }
    L_0x004b:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x0067 }
        r4 = 45;
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x0067 }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x0067 }
        r2 = r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x0067 }
        r4 = " is not a valid enum MobileSubtype";
        r2 = r2.append(r4);	 Catch:{ IllegalArgumentException -> 0x0067 }
        r2 = r2.toString();	 Catch:{ IllegalArgumentException -> 0x0067 }
        r3.<init>(r2);	 Catch:{ IllegalArgumentException -> 0x0067 }
        throw r3;	 Catch:{ IllegalArgumentException -> 0x0067 }
    L_0x0067:
        r2 = move-exception;
        r7.zzmw(r1);
        r6.zza(r7, r0);
        goto L_0x0000;
    L_0x006f:
        r6.zzpzq = r2;	 Catch:{ IllegalArgumentException -> 0x0067 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfmt.zzbo(com.google.android.gms.internal.zzflj):com.google.android.gms.internal.zzfmt");
    }

    private zzfmt zzddh() {
        try {
            return (zzfmt) super.zzdck();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzddh();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmt)) {
            return false;
        }
        zzfmt zzfmt = (zzfmt) obj;
        return this.zzpzp != zzfmt.zzpzp ? false : this.zzpzq != zzfmt.zzpzq ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzfmt.zzpvl == null || zzfmt.zzpvl.isEmpty() : this.zzpvl.equals(zzfmt.zzpvl);
    }

    public final int hashCode() {
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + this.zzpzp) * 31) + this.zzpzq) * 31;
        int hashCode2 = (this.zzpvl == null || this.zzpvl.isEmpty()) ? 0 : this.zzpvl.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzbo(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzpzp != -1) {
            zzflk.zzad(1, this.zzpzp);
        }
        if (this.zzpzq != 0) {
            zzflk.zzad(2, this.zzpzq);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzdck() throws CloneNotSupportedException {
        return (zzfmt) clone();
    }

    public final /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzfmt) clone();
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzpzp != -1) {
            zzq += zzflk.zzag(1, this.zzpzp);
        }
        return this.zzpzq != 0 ? zzq + zzflk.zzag(2, this.zzpzq) : zzq;
    }
}
