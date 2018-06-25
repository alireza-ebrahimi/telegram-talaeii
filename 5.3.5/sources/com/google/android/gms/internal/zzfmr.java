package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class zzfmr extends zzflm<zzfmr> implements Cloneable {
    private String tag;
    private int zzaky;
    private boolean zznet;
    private zzfmt zzorb;
    public long zzpyu;
    public long zzpyv;
    private long zzpyw;
    private int zzpyx;
    private zzfms[] zzpyy;
    private byte[] zzpyz;
    private zzfmp zzpza;
    public byte[] zzpzb;
    private String zzpzc;
    private String zzpzd;
    private zzfmo zzpze;
    private String zzpzf;
    public long zzpzg;
    private zzfmq zzpzh;
    public byte[] zzpzi;
    private String zzpzj;
    private int zzpzk;
    private int[] zzpzl;
    private long zzpzm;
    private boolean zzpzn;

    public zzfmr() {
        this.zzpyu = 0;
        this.zzpyv = 0;
        this.zzpyw = 0;
        this.tag = "";
        this.zzpyx = 0;
        this.zzaky = 0;
        this.zznet = false;
        this.zzpyy = zzfms.zzddf();
        this.zzpyz = zzflv.zzpwe;
        this.zzpza = null;
        this.zzpzb = zzflv.zzpwe;
        this.zzpzc = "";
        this.zzpzd = "";
        this.zzpze = null;
        this.zzpzf = "";
        this.zzpzg = 180000;
        this.zzpzh = null;
        this.zzpzi = zzflv.zzpwe;
        this.zzpzj = "";
        this.zzpzk = 0;
        this.zzpzl = zzflv.zzpvy;
        this.zzpzm = 0;
        this.zzorb = null;
        this.zzpzn = false;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.zzfmr zzbn(com.google.android.gms.internal.zzflj r8) throws java.io.IOException {
        /*
        r7 = this;
        r1 = 0;
    L_0x0001:
        r0 = r8.zzcxx();
        switch(r0) {
            case 0: goto L_0x000e;
            case 8: goto L_0x000f;
            case 18: goto L_0x0016;
            case 26: goto L_0x001d;
            case 34: goto L_0x005c;
            case 50: goto L_0x0063;
            case 58: goto L_0x006a;
            case 66: goto L_0x007b;
            case 74: goto L_0x0082;
            case 80: goto L_0x0094;
            case 88: goto L_0x009c;
            case 96: goto L_0x00a4;
            case 106: goto L_0x00ac;
            case 114: goto L_0x00b4;
            case 120: goto L_0x00bc;
            case 130: goto L_0x00c4;
            case 136: goto L_0x00d6;
            case 146: goto L_0x00de;
            case 152: goto L_0x00e6;
            case 160: goto L_0x011a;
            case 162: goto L_0x014e;
            case 168: goto L_0x0190;
            case 176: goto L_0x0198;
            case 186: goto L_0x01a0;
            case 194: goto L_0x01b2;
            case 200: goto L_0x01ba;
            default: goto L_0x0008;
        };
    L_0x0008:
        r0 = super.zza(r8, r0);
        if (r0 != 0) goto L_0x0001;
    L_0x000e:
        return r7;
    L_0x000f:
        r2 = r8.zzcxz();
        r7.zzpyu = r2;
        goto L_0x0001;
    L_0x0016:
        r0 = r8.readString();
        r7.tag = r0;
        goto L_0x0001;
    L_0x001d:
        r0 = 26;
        r2 = com.google.android.gms.internal.zzflv.zzb(r8, r0);
        r0 = r7.zzpyy;
        if (r0 != 0) goto L_0x0049;
    L_0x0027:
        r0 = r1;
    L_0x0028:
        r2 = r2 + r0;
        r2 = new com.google.android.gms.internal.zzfms[r2];
        if (r0 == 0) goto L_0x0032;
    L_0x002d:
        r3 = r7.zzpyy;
        java.lang.System.arraycopy(r3, r1, r2, r1, r0);
    L_0x0032:
        r3 = r2.length;
        r3 = r3 + -1;
        if (r0 >= r3) goto L_0x004d;
    L_0x0037:
        r3 = new com.google.android.gms.internal.zzfms;
        r3.<init>();
        r2[r0] = r3;
        r3 = r2[r0];
        r8.zza(r3);
        r8.zzcxx();
        r0 = r0 + 1;
        goto L_0x0032;
    L_0x0049:
        r0 = r7.zzpyy;
        r0 = r0.length;
        goto L_0x0028;
    L_0x004d:
        r3 = new com.google.android.gms.internal.zzfms;
        r3.<init>();
        r2[r0] = r3;
        r0 = r2[r0];
        r8.zza(r0);
        r7.zzpyy = r2;
        goto L_0x0001;
    L_0x005c:
        r0 = r8.readBytes();
        r7.zzpyz = r0;
        goto L_0x0001;
    L_0x0063:
        r0 = r8.readBytes();
        r7.zzpzb = r0;
        goto L_0x0001;
    L_0x006a:
        r0 = r7.zzpze;
        if (r0 != 0) goto L_0x0075;
    L_0x006e:
        r0 = new com.google.android.gms.internal.zzfmo;
        r0.<init>();
        r7.zzpze = r0;
    L_0x0075:
        r0 = r7.zzpze;
        r8.zza(r0);
        goto L_0x0001;
    L_0x007b:
        r0 = r8.readString();
        r7.zzpzc = r0;
        goto L_0x0001;
    L_0x0082:
        r0 = r7.zzpza;
        if (r0 != 0) goto L_0x008d;
    L_0x0086:
        r0 = new com.google.android.gms.internal.zzfmp;
        r0.<init>();
        r7.zzpza = r0;
    L_0x008d:
        r0 = r7.zzpza;
        r8.zza(r0);
        goto L_0x0001;
    L_0x0094:
        r0 = r8.zzcyd();
        r7.zznet = r0;
        goto L_0x0001;
    L_0x009c:
        r0 = r8.zzcya();
        r7.zzpyx = r0;
        goto L_0x0001;
    L_0x00a4:
        r0 = r8.zzcya();
        r7.zzaky = r0;
        goto L_0x0001;
    L_0x00ac:
        r0 = r8.readString();
        r7.zzpzd = r0;
        goto L_0x0001;
    L_0x00b4:
        r0 = r8.readString();
        r7.zzpzf = r0;
        goto L_0x0001;
    L_0x00bc:
        r2 = r8.zzcyl();
        r7.zzpzg = r2;
        goto L_0x0001;
    L_0x00c4:
        r0 = r7.zzpzh;
        if (r0 != 0) goto L_0x00cf;
    L_0x00c8:
        r0 = new com.google.android.gms.internal.zzfmq;
        r0.<init>();
        r7.zzpzh = r0;
    L_0x00cf:
        r0 = r7.zzpzh;
        r8.zza(r0);
        goto L_0x0001;
    L_0x00d6:
        r2 = r8.zzcxz();
        r7.zzpyv = r2;
        goto L_0x0001;
    L_0x00de:
        r0 = r8.readBytes();
        r7.zzpzi = r0;
        goto L_0x0001;
    L_0x00e6:
        r2 = r8.getPosition();
        r3 = r8.zzcya();	 Catch:{ IllegalArgumentException -> 0x010d }
        switch(r3) {
            case 0: goto L_0x0116;
            case 1: goto L_0x0116;
            case 2: goto L_0x0116;
            default: goto L_0x00f1;
        };	 Catch:{ IllegalArgumentException -> 0x010d }
    L_0x00f1:
        r4 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x010d }
        r5 = 45;
        r6 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x010d }
        r6.<init>(r5);	 Catch:{ IllegalArgumentException -> 0x010d }
        r3 = r6.append(r3);	 Catch:{ IllegalArgumentException -> 0x010d }
        r5 = " is not a valid enum InternalEvent";
        r3 = r3.append(r5);	 Catch:{ IllegalArgumentException -> 0x010d }
        r3 = r3.toString();	 Catch:{ IllegalArgumentException -> 0x010d }
        r4.<init>(r3);	 Catch:{ IllegalArgumentException -> 0x010d }
        throw r4;	 Catch:{ IllegalArgumentException -> 0x010d }
    L_0x010d:
        r3 = move-exception;
        r8.zzmw(r2);
        r7.zza(r8, r0);
        goto L_0x0001;
    L_0x0116:
        r7.zzpzk = r3;	 Catch:{ IllegalArgumentException -> 0x010d }
        goto L_0x0001;
    L_0x011a:
        r0 = 160; // 0xa0 float:2.24E-43 double:7.9E-322;
        r2 = com.google.android.gms.internal.zzflv.zzb(r8, r0);
        r0 = r7.zzpzl;
        if (r0 != 0) goto L_0x0140;
    L_0x0124:
        r0 = r1;
    L_0x0125:
        r2 = r2 + r0;
        r2 = new int[r2];
        if (r0 == 0) goto L_0x012f;
    L_0x012a:
        r3 = r7.zzpzl;
        java.lang.System.arraycopy(r3, r1, r2, r1, r0);
    L_0x012f:
        r3 = r2.length;
        r3 = r3 + -1;
        if (r0 >= r3) goto L_0x0144;
    L_0x0134:
        r3 = r8.zzcya();
        r2[r0] = r3;
        r8.zzcxx();
        r0 = r0 + 1;
        goto L_0x012f;
    L_0x0140:
        r0 = r7.zzpzl;
        r0 = r0.length;
        goto L_0x0125;
    L_0x0144:
        r3 = r8.zzcya();
        r2[r0] = r3;
        r7.zzpzl = r2;
        goto L_0x0001;
    L_0x014e:
        r0 = r8.zzcym();
        r3 = r8.zzli(r0);
        r2 = r8.getPosition();
        r0 = r1;
    L_0x015b:
        r4 = r8.zzcyo();
        if (r4 <= 0) goto L_0x0167;
    L_0x0161:
        r8.zzcya();
        r0 = r0 + 1;
        goto L_0x015b;
    L_0x0167:
        r8.zzmw(r2);
        r2 = r7.zzpzl;
        if (r2 != 0) goto L_0x0185;
    L_0x016e:
        r2 = r1;
    L_0x016f:
        r0 = r0 + r2;
        r0 = new int[r0];
        if (r2 == 0) goto L_0x0179;
    L_0x0174:
        r4 = r7.zzpzl;
        java.lang.System.arraycopy(r4, r1, r0, r1, r2);
    L_0x0179:
        r4 = r0.length;
        if (r2 >= r4) goto L_0x0189;
    L_0x017c:
        r4 = r8.zzcya();
        r0[r2] = r4;
        r2 = r2 + 1;
        goto L_0x0179;
    L_0x0185:
        r2 = r7.zzpzl;
        r2 = r2.length;
        goto L_0x016f;
    L_0x0189:
        r7.zzpzl = r0;
        r8.zzlj(r3);
        goto L_0x0001;
    L_0x0190:
        r2 = r8.zzcxz();
        r7.zzpyw = r2;
        goto L_0x0001;
    L_0x0198:
        r2 = r8.zzcxz();
        r7.zzpzm = r2;
        goto L_0x0001;
    L_0x01a0:
        r0 = r7.zzorb;
        if (r0 != 0) goto L_0x01ab;
    L_0x01a4:
        r0 = new com.google.android.gms.internal.zzfmt;
        r0.<init>();
        r7.zzorb = r0;
    L_0x01ab:
        r0 = r7.zzorb;
        r8.zza(r0);
        goto L_0x0001;
    L_0x01b2:
        r0 = r8.readString();
        r7.zzpzj = r0;
        goto L_0x0001;
    L_0x01ba:
        r0 = r8.zzcyd();
        r7.zzpzn = r0;
        goto L_0x0001;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfmr.zzbn(com.google.android.gms.internal.zzflj):com.google.android.gms.internal.zzfmr");
    }

    private final zzfmr zzdde() {
        try {
            zzfmr zzfmr = (zzfmr) super.zzdck();
            if (this.zzpyy != null && this.zzpyy.length > 0) {
                zzfmr.zzpyy = new zzfms[this.zzpyy.length];
                for (int i = 0; i < this.zzpyy.length; i++) {
                    if (this.zzpyy[i] != null) {
                        zzfmr.zzpyy[i] = (zzfms) this.zzpyy[i].clone();
                    }
                }
            }
            if (this.zzpza != null) {
                zzfmr.zzpza = (zzfmp) this.zzpza.clone();
            }
            if (this.zzpze != null) {
                zzfmr.zzpze = (zzfmo) this.zzpze.clone();
            }
            if (this.zzpzh != null) {
                zzfmr.zzpzh = (zzfmq) this.zzpzh.clone();
            }
            if (this.zzpzl != null && this.zzpzl.length > 0) {
                zzfmr.zzpzl = (int[]) this.zzpzl.clone();
            }
            if (this.zzorb != null) {
                zzfmr.zzorb = (zzfmt) this.zzorb.clone();
            }
            return zzfmr;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzdde();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmr)) {
            return false;
        }
        zzfmr zzfmr = (zzfmr) obj;
        if (this.zzpyu != zzfmr.zzpyu) {
            return false;
        }
        if (this.zzpyv != zzfmr.zzpyv) {
            return false;
        }
        if (this.zzpyw != zzfmr.zzpyw) {
            return false;
        }
        if (this.tag == null) {
            if (zzfmr.tag != null) {
                return false;
            }
        } else if (!this.tag.equals(zzfmr.tag)) {
            return false;
        }
        if (this.zzpyx != zzfmr.zzpyx) {
            return false;
        }
        if (this.zzaky != zzfmr.zzaky) {
            return false;
        }
        if (this.zznet != zzfmr.zznet) {
            return false;
        }
        if (!zzflq.equals(this.zzpyy, zzfmr.zzpyy)) {
            return false;
        }
        if (!Arrays.equals(this.zzpyz, zzfmr.zzpyz)) {
            return false;
        }
        if (this.zzpza == null) {
            if (zzfmr.zzpza != null) {
                return false;
            }
        } else if (!this.zzpza.equals(zzfmr.zzpza)) {
            return false;
        }
        if (!Arrays.equals(this.zzpzb, zzfmr.zzpzb)) {
            return false;
        }
        if (this.zzpzc == null) {
            if (zzfmr.zzpzc != null) {
                return false;
            }
        } else if (!this.zzpzc.equals(zzfmr.zzpzc)) {
            return false;
        }
        if (this.zzpzd == null) {
            if (zzfmr.zzpzd != null) {
                return false;
            }
        } else if (!this.zzpzd.equals(zzfmr.zzpzd)) {
            return false;
        }
        if (this.zzpze == null) {
            if (zzfmr.zzpze != null) {
                return false;
            }
        } else if (!this.zzpze.equals(zzfmr.zzpze)) {
            return false;
        }
        if (this.zzpzf == null) {
            if (zzfmr.zzpzf != null) {
                return false;
            }
        } else if (!this.zzpzf.equals(zzfmr.zzpzf)) {
            return false;
        }
        if (this.zzpzg != zzfmr.zzpzg) {
            return false;
        }
        if (this.zzpzh == null) {
            if (zzfmr.zzpzh != null) {
                return false;
            }
        } else if (!this.zzpzh.equals(zzfmr.zzpzh)) {
            return false;
        }
        if (!Arrays.equals(this.zzpzi, zzfmr.zzpzi)) {
            return false;
        }
        if (this.zzpzj == null) {
            if (zzfmr.zzpzj != null) {
                return false;
            }
        } else if (!this.zzpzj.equals(zzfmr.zzpzj)) {
            return false;
        }
        if (this.zzpzk != zzfmr.zzpzk) {
            return false;
        }
        if (!zzflq.equals(this.zzpzl, zzfmr.zzpzl)) {
            return false;
        }
        if (this.zzpzm != zzfmr.zzpzm) {
            return false;
        }
        if (this.zzorb == null) {
            if (zzfmr.zzorb != null) {
                return false;
            }
        } else if (!this.zzorb.equals(zzfmr.zzorb)) {
            return false;
        }
        return this.zzpzn != zzfmr.zzpzn ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzfmr.zzpvl == null || zzfmr.zzpvl.isEmpty() : this.zzpvl.equals(zzfmr.zzpvl);
    }

    public final int hashCode() {
        int i = 1231;
        int i2 = 0;
        int hashCode = (((((this.zznet ? 1231 : 1237) + (((((((this.tag == null ? 0 : this.tag.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzpyu ^ (this.zzpyu >>> 32)))) * 31) + ((int) (this.zzpyv ^ (this.zzpyv >>> 32)))) * 31) + ((int) (this.zzpyw ^ (this.zzpyw >>> 32)))) * 31)) * 31) + this.zzpyx) * 31) + this.zzaky) * 31)) * 31) + zzflq.hashCode(this.zzpyy)) * 31) + Arrays.hashCode(this.zzpyz);
        zzfmp zzfmp = this.zzpza;
        hashCode = (this.zzpzd == null ? 0 : this.zzpzd.hashCode()) + (((this.zzpzc == null ? 0 : this.zzpzc.hashCode()) + (((((zzfmp == null ? 0 : zzfmp.hashCode()) + (hashCode * 31)) * 31) + Arrays.hashCode(this.zzpzb)) * 31)) * 31);
        zzfmo zzfmo = this.zzpze;
        hashCode = (((this.zzpzf == null ? 0 : this.zzpzf.hashCode()) + (((zzfmo == null ? 0 : zzfmo.hashCode()) + (hashCode * 31)) * 31)) * 31) + ((int) (this.zzpzg ^ (this.zzpzg >>> 32)));
        zzfmq zzfmq = this.zzpzh;
        hashCode = (((((((this.zzpzj == null ? 0 : this.zzpzj.hashCode()) + (((((zzfmq == null ? 0 : zzfmq.hashCode()) + (hashCode * 31)) * 31) + Arrays.hashCode(this.zzpzi)) * 31)) * 31) + this.zzpzk) * 31) + zzflq.hashCode(this.zzpzl)) * 31) + ((int) (this.zzpzm ^ (this.zzpzm >>> 32)));
        zzfmt zzfmt = this.zzorb;
        hashCode = ((zzfmt == null ? 0 : zzfmt.hashCode()) + (hashCode * 31)) * 31;
        if (!this.zzpzn) {
            i = 1237;
        }
        hashCode = (hashCode + i) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i2 = this.zzpvl.hashCode();
        }
        return hashCode + i2;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzbn(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        int i = 0;
        if (this.zzpyu != 0) {
            zzflk.zzf(1, this.zzpyu);
        }
        if (!(this.tag == null || this.tag.equals(""))) {
            zzflk.zzp(2, this.tag);
        }
        if (this.zzpyy != null && this.zzpyy.length > 0) {
            for (zzfls zzfls : this.zzpyy) {
                if (zzfls != null) {
                    zzflk.zza(3, zzfls);
                }
            }
        }
        if (!Arrays.equals(this.zzpyz, zzflv.zzpwe)) {
            zzflk.zzc(4, this.zzpyz);
        }
        if (!Arrays.equals(this.zzpzb, zzflv.zzpwe)) {
            zzflk.zzc(6, this.zzpzb);
        }
        if (this.zzpze != null) {
            zzflk.zza(7, this.zzpze);
        }
        if (!(this.zzpzc == null || this.zzpzc.equals(""))) {
            zzflk.zzp(8, this.zzpzc);
        }
        if (this.zzpza != null) {
            zzflk.zza(9, this.zzpza);
        }
        if (this.zznet) {
            zzflk.zzl(10, this.zznet);
        }
        if (this.zzpyx != 0) {
            zzflk.zzad(11, this.zzpyx);
        }
        if (this.zzaky != 0) {
            zzflk.zzad(12, this.zzaky);
        }
        if (!(this.zzpzd == null || this.zzpzd.equals(""))) {
            zzflk.zzp(13, this.zzpzd);
        }
        if (!(this.zzpzf == null || this.zzpzf.equals(""))) {
            zzflk.zzp(14, this.zzpzf);
        }
        if (this.zzpzg != 180000) {
            zzflk.zzg(15, this.zzpzg);
        }
        if (this.zzpzh != null) {
            zzflk.zza(16, this.zzpzh);
        }
        if (this.zzpyv != 0) {
            zzflk.zzf(17, this.zzpyv);
        }
        if (!Arrays.equals(this.zzpzi, zzflv.zzpwe)) {
            zzflk.zzc(18, this.zzpzi);
        }
        if (this.zzpzk != 0) {
            zzflk.zzad(19, this.zzpzk);
        }
        if (this.zzpzl != null && this.zzpzl.length > 0) {
            while (i < this.zzpzl.length) {
                zzflk.zzad(20, this.zzpzl[i]);
                i++;
            }
        }
        if (this.zzpyw != 0) {
            zzflk.zzf(21, this.zzpyw);
        }
        if (this.zzpzm != 0) {
            zzflk.zzf(22, this.zzpzm);
        }
        if (this.zzorb != null) {
            zzflk.zza(23, this.zzorb);
        }
        if (!(this.zzpzj == null || this.zzpzj.equals(""))) {
            zzflk.zzp(24, this.zzpzj);
        }
        if (this.zzpzn) {
            zzflk.zzl(25, this.zzpzn);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzdck() throws CloneNotSupportedException {
        return (zzfmr) clone();
    }

    public final /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzfmr) clone();
    }

    protected final int zzq() {
        int i;
        int i2 = 0;
        int zzq = super.zzq();
        if (this.zzpyu != 0) {
            zzq += zzflk.zzc(1, this.zzpyu);
        }
        if (!(this.tag == null || this.tag.equals(""))) {
            zzq += zzflk.zzq(2, this.tag);
        }
        if (this.zzpyy != null && this.zzpyy.length > 0) {
            i = zzq;
            for (zzfls zzfls : this.zzpyy) {
                if (zzfls != null) {
                    i += zzflk.zzb(3, zzfls);
                }
            }
            zzq = i;
        }
        if (!Arrays.equals(this.zzpyz, zzflv.zzpwe)) {
            zzq += zzflk.zzd(4, this.zzpyz);
        }
        if (!Arrays.equals(this.zzpzb, zzflv.zzpwe)) {
            zzq += zzflk.zzd(6, this.zzpzb);
        }
        if (this.zzpze != null) {
            zzq += zzflk.zzb(7, this.zzpze);
        }
        if (!(this.zzpzc == null || this.zzpzc.equals(""))) {
            zzq += zzflk.zzq(8, this.zzpzc);
        }
        if (this.zzpza != null) {
            zzq += zzflk.zzb(9, this.zzpza);
        }
        if (this.zznet) {
            zzq += zzflk.zzlw(10) + 1;
        }
        if (this.zzpyx != 0) {
            zzq += zzflk.zzag(11, this.zzpyx);
        }
        if (this.zzaky != 0) {
            zzq += zzflk.zzag(12, this.zzaky);
        }
        if (!(this.zzpzd == null || this.zzpzd.equals(""))) {
            zzq += zzflk.zzq(13, this.zzpzd);
        }
        if (!(this.zzpzf == null || this.zzpzf.equals(""))) {
            zzq += zzflk.zzq(14, this.zzpzf);
        }
        if (this.zzpzg != 180000) {
            zzq += zzflk.zzh(15, this.zzpzg);
        }
        if (this.zzpzh != null) {
            zzq += zzflk.zzb(16, this.zzpzh);
        }
        if (this.zzpyv != 0) {
            zzq += zzflk.zzc(17, this.zzpyv);
        }
        if (!Arrays.equals(this.zzpzi, zzflv.zzpwe)) {
            zzq += zzflk.zzd(18, this.zzpzi);
        }
        if (this.zzpzk != 0) {
            zzq += zzflk.zzag(19, this.zzpzk);
        }
        if (this.zzpzl != null && this.zzpzl.length > 0) {
            i = 0;
            while (i2 < this.zzpzl.length) {
                i += zzflk.zzlx(this.zzpzl[i2]);
                i2++;
            }
            zzq = (zzq + i) + (this.zzpzl.length * 2);
        }
        if (this.zzpyw != 0) {
            zzq += zzflk.zzc(21, this.zzpyw);
        }
        if (this.zzpzm != 0) {
            zzq += zzflk.zzc(22, this.zzpzm);
        }
        if (this.zzorb != null) {
            zzq += zzflk.zzb(23, this.zzorb);
        }
        if (!(this.zzpzj == null || this.zzpzj.equals(""))) {
            zzq += zzflk.zzq(24, this.zzpzj);
        }
        return this.zzpzn ? zzq + (zzflk.zzlw(25) + 1) : zzq;
    }
}
