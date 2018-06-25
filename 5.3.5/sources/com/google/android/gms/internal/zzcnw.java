package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnw extends zzflm<zzcnw> {
    public Integer zzjtp;
    public String zzjtq;
    public Boolean zzjtr;
    public String[] zzjts;

    public zzcnw() {
        this.zzjtp = null;
        this.zzjtq = null;
        this.zzjtr = null;
        this.zzjts = zzflv.EMPTY_STRING_ARRAY;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzcnw zzad(com.google.android.gms.internal.zzflj r8) throws java.io.IOException {
        /*
        r7 = this;
        r1 = 0;
    L_0x0001:
        r0 = r8.zzcxx();
        switch(r0) {
            case 0: goto L_0x000e;
            case 8: goto L_0x000f;
            case 18: goto L_0x0045;
            case 24: goto L_0x004c;
            case 34: goto L_0x0057;
            default: goto L_0x0008;
        };
    L_0x0008:
        r0 = super.zza(r8, r0);
        if (r0 != 0) goto L_0x0001;
    L_0x000e:
        return r7;
    L_0x000f:
        r2 = r8.getPosition();
        r3 = r8.zzcym();	 Catch:{ IllegalArgumentException -> 0x0036 }
        switch(r3) {
            case 0: goto L_0x003e;
            case 1: goto L_0x003e;
            case 2: goto L_0x003e;
            case 3: goto L_0x003e;
            case 4: goto L_0x003e;
            case 5: goto L_0x003e;
            case 6: goto L_0x003e;
            default: goto L_0x001a;
        };	 Catch:{ IllegalArgumentException -> 0x0036 }
    L_0x001a:
        r4 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x0036 }
        r5 = 41;
        r6 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x0036 }
        r6.<init>(r5);	 Catch:{ IllegalArgumentException -> 0x0036 }
        r3 = r6.append(r3);	 Catch:{ IllegalArgumentException -> 0x0036 }
        r5 = " is not a valid enum MatchType";
        r3 = r3.append(r5);	 Catch:{ IllegalArgumentException -> 0x0036 }
        r3 = r3.toString();	 Catch:{ IllegalArgumentException -> 0x0036 }
        r4.<init>(r3);	 Catch:{ IllegalArgumentException -> 0x0036 }
        throw r4;	 Catch:{ IllegalArgumentException -> 0x0036 }
    L_0x0036:
        r3 = move-exception;
        r8.zzmw(r2);
        r7.zza(r8, r0);
        goto L_0x0001;
    L_0x003e:
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ IllegalArgumentException -> 0x0036 }
        r7.zzjtp = r3;	 Catch:{ IllegalArgumentException -> 0x0036 }
        goto L_0x0001;
    L_0x0045:
        r0 = r8.readString();
        r7.zzjtq = r0;
        goto L_0x0001;
    L_0x004c:
        r0 = r8.zzcyd();
        r0 = java.lang.Boolean.valueOf(r0);
        r7.zzjtr = r0;
        goto L_0x0001;
    L_0x0057:
        r0 = 34;
        r2 = com.google.android.gms.internal.zzflv.zzb(r8, r0);
        r0 = r7.zzjts;
        if (r0 != 0) goto L_0x007d;
    L_0x0061:
        r0 = r1;
    L_0x0062:
        r2 = r2 + r0;
        r2 = new java.lang.String[r2];
        if (r0 == 0) goto L_0x006c;
    L_0x0067:
        r3 = r7.zzjts;
        java.lang.System.arraycopy(r3, r1, r2, r1, r0);
    L_0x006c:
        r3 = r2.length;
        r3 = r3 + -1;
        if (r0 >= r3) goto L_0x0081;
    L_0x0071:
        r3 = r8.readString();
        r2[r0] = r3;
        r8.zzcxx();
        r0 = r0 + 1;
        goto L_0x006c;
    L_0x007d:
        r0 = r7.zzjts;
        r0 = r0.length;
        goto L_0x0062;
    L_0x0081:
        r3 = r8.readString();
        r2[r0] = r3;
        r7.zzjts = r2;
        goto L_0x0001;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcnw.zzad(com.google.android.gms.internal.zzflj):com.google.android.gms.internal.zzcnw");
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnw)) {
            return false;
        }
        zzcnw zzcnw = (zzcnw) obj;
        if (this.zzjtp == null) {
            if (zzcnw.zzjtp != null) {
                return false;
            }
        } else if (!this.zzjtp.equals(zzcnw.zzjtp)) {
            return false;
        }
        if (this.zzjtq == null) {
            if (zzcnw.zzjtq != null) {
                return false;
            }
        } else if (!this.zzjtq.equals(zzcnw.zzjtq)) {
            return false;
        }
        if (this.zzjtr == null) {
            if (zzcnw.zzjtr != null) {
                return false;
            }
        } else if (!this.zzjtr.equals(zzcnw.zzjtr)) {
            return false;
        }
        return !zzflq.equals(this.zzjts, zzcnw.zzjts) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcnw.zzpvl == null || zzcnw.zzpvl.isEmpty() : this.zzpvl.equals(zzcnw.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzjtr == null ? 0 : this.zzjtr.hashCode()) + (((this.zzjtq == null ? 0 : this.zzjtq.hashCode()) + (((this.zzjtp == null ? 0 : this.zzjtp.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzflq.hashCode(this.zzjts)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzad(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzjtp != null) {
            zzflk.zzad(1, this.zzjtp.intValue());
        }
        if (this.zzjtq != null) {
            zzflk.zzp(2, this.zzjtq);
        }
        if (this.zzjtr != null) {
            zzflk.zzl(3, this.zzjtr.booleanValue());
        }
        if (this.zzjts != null && this.zzjts.length > 0) {
            for (String str : this.zzjts) {
                if (str != null) {
                    zzflk.zzp(4, str);
                }
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int i = 0;
        int zzq = super.zzq();
        if (this.zzjtp != null) {
            zzq += zzflk.zzag(1, this.zzjtp.intValue());
        }
        if (this.zzjtq != null) {
            zzq += zzflk.zzq(2, this.zzjtq);
        }
        if (this.zzjtr != null) {
            this.zzjtr.booleanValue();
            zzq += zzflk.zzlw(3) + 1;
        }
        if (this.zzjts == null || this.zzjts.length <= 0) {
            return zzq;
        }
        int i2 = 0;
        int i3 = 0;
        while (i < this.zzjts.length) {
            String str = this.zzjts[i];
            if (str != null) {
                i3++;
                i2 += zzflk.zztx(str);
            }
            i++;
        }
        return (zzq + i2) + (i3 * 1);
    }
}
