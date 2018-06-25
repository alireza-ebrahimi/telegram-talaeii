package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnu extends zzflm<zzcnu> {
    public Integer zzjth;
    public Boolean zzjti;
    public String zzjtj;
    public String zzjtk;
    public String zzjtl;

    public zzcnu() {
        this.zzjth = null;
        this.zzjti = null;
        this.zzjtj = null;
        this.zzjtk = null;
        this.zzjtl = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzcnu zzac(com.google.android.gms.internal.zzflj r7) throws java.io.IOException {
        /*
        r6 = this;
    L_0x0000:
        r0 = r7.zzcxx();
        switch(r0) {
            case 0: goto L_0x000d;
            case 8: goto L_0x000e;
            case 16: goto L_0x0044;
            case 26: goto L_0x004f;
            case 34: goto L_0x0056;
            case 42: goto L_0x005d;
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
            case 0: goto L_0x003d;
            case 1: goto L_0x003d;
            case 2: goto L_0x003d;
            case 3: goto L_0x003d;
            case 4: goto L_0x003d;
            default: goto L_0x0019;
        };	 Catch:{ IllegalArgumentException -> 0x0035 }
    L_0x0019:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = 46;
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x0035 }
        r5.<init>(r4);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r2 = r5.append(r2);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r4 = " is not a valid enum ComparisonType";
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
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ IllegalArgumentException -> 0x0035 }
        r6.zzjth = r2;	 Catch:{ IllegalArgumentException -> 0x0035 }
        goto L_0x0000;
    L_0x0044:
        r0 = r7.zzcyd();
        r0 = java.lang.Boolean.valueOf(r0);
        r6.zzjti = r0;
        goto L_0x0000;
    L_0x004f:
        r0 = r7.readString();
        r6.zzjtj = r0;
        goto L_0x0000;
    L_0x0056:
        r0 = r7.readString();
        r6.zzjtk = r0;
        goto L_0x0000;
    L_0x005d:
        r0 = r7.readString();
        r6.zzjtl = r0;
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcnu.zzac(com.google.android.gms.internal.zzflj):com.google.android.gms.internal.zzcnu");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnu)) {
            return false;
        }
        zzcnu zzcnu = (zzcnu) obj;
        if (this.zzjth == null) {
            if (zzcnu.zzjth != null) {
                return false;
            }
        } else if (!this.zzjth.equals(zzcnu.zzjth)) {
            return false;
        }
        if (this.zzjti == null) {
            if (zzcnu.zzjti != null) {
                return false;
            }
        } else if (!this.zzjti.equals(zzcnu.zzjti)) {
            return false;
        }
        if (this.zzjtj == null) {
            if (zzcnu.zzjtj != null) {
                return false;
            }
        } else if (!this.zzjtj.equals(zzcnu.zzjtj)) {
            return false;
        }
        if (this.zzjtk == null) {
            if (zzcnu.zzjtk != null) {
                return false;
            }
        } else if (!this.zzjtk.equals(zzcnu.zzjtk)) {
            return false;
        }
        if (this.zzjtl == null) {
            if (zzcnu.zzjtl != null) {
                return false;
            }
        } else if (!this.zzjtl.equals(zzcnu.zzjtl)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcnu.zzpvl == null || zzcnu.zzpvl.isEmpty() : this.zzpvl.equals(zzcnu.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzjtl == null ? 0 : this.zzjtl.hashCode()) + (((this.zzjtk == null ? 0 : this.zzjtk.hashCode()) + (((this.zzjtj == null ? 0 : this.zzjtj.hashCode()) + (((this.zzjti == null ? 0 : this.zzjti.hashCode()) + (((this.zzjth == null ? 0 : this.zzjth.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzac(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzjth != null) {
            zzflk.zzad(1, this.zzjth.intValue());
        }
        if (this.zzjti != null) {
            zzflk.zzl(2, this.zzjti.booleanValue());
        }
        if (this.zzjtj != null) {
            zzflk.zzp(3, this.zzjtj);
        }
        if (this.zzjtk != null) {
            zzflk.zzp(4, this.zzjtk);
        }
        if (this.zzjtl != null) {
            zzflk.zzp(5, this.zzjtl);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjth != null) {
            zzq += zzflk.zzag(1, this.zzjth.intValue());
        }
        if (this.zzjti != null) {
            this.zzjti.booleanValue();
            zzq += zzflk.zzlw(2) + 1;
        }
        if (this.zzjtj != null) {
            zzq += zzflk.zzq(3, this.zzjtj);
        }
        if (this.zzjtk != null) {
            zzq += zzflk.zzq(4, this.zzjtk);
        }
        return this.zzjtl != null ? zzq + zzflk.zzq(5, this.zzjtl) : zzq;
    }
}
