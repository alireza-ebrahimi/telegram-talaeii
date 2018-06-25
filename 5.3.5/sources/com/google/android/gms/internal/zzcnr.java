package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnr extends zzflm<zzcnr> {
    private static volatile zzcnr[] zzjss;
    public Integer zzjst;
    public zzcnv[] zzjsu;
    public zzcns[] zzjsv;

    public zzcnr() {
        this.zzjst = null;
        this.zzjsu = zzcnv.zzbcv();
        this.zzjsv = zzcns.zzbct();
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcnr[] zzbcs() {
        if (zzjss == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjss == null) {
                    zzjss = new zzcnr[0];
                }
            }
        }
        return zzjss;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnr)) {
            return false;
        }
        zzcnr zzcnr = (zzcnr) obj;
        if (this.zzjst == null) {
            if (zzcnr.zzjst != null) {
                return false;
            }
        } else if (!this.zzjst.equals(zzcnr.zzjst)) {
            return false;
        }
        return !zzflq.equals(this.zzjsu, zzcnr.zzjsu) ? false : !zzflq.equals(this.zzjsv, zzcnr.zzjsv) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcnr.zzpvl == null || zzcnr.zzpvl.isEmpty() : this.zzpvl.equals(zzcnr.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((this.zzjst == null ? 0 : this.zzjst.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzflq.hashCode(this.zzjsu)) * 31) + zzflq.hashCode(this.zzjsv)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            int zzb;
            Object obj;
            switch (zzcxx) {
                case 0:
                    break;
                case 8:
                    this.zzjst = Integer.valueOf(zzflj.zzcym());
                    continue;
                case 18:
                    zzb = zzflv.zzb(zzflj, 18);
                    zzcxx = this.zzjsu == null ? 0 : this.zzjsu.length;
                    obj = new zzcnv[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjsu, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcnv();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcnv();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjsu = obj;
                    continue;
                case 26:
                    zzb = zzflv.zzb(zzflj, 26);
                    zzcxx = this.zzjsv == null ? 0 : this.zzjsv.length;
                    obj = new zzcns[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjsv, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcns();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcns();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjsv = obj;
                    continue;
                default:
                    if (!super.zza(zzflj, zzcxx)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        int i = 0;
        if (this.zzjst != null) {
            zzflk.zzad(1, this.zzjst.intValue());
        }
        if (this.zzjsu != null && this.zzjsu.length > 0) {
            for (zzfls zzfls : this.zzjsu) {
                if (zzfls != null) {
                    zzflk.zza(2, zzfls);
                }
            }
        }
        if (this.zzjsv != null && this.zzjsv.length > 0) {
            while (i < this.zzjsv.length) {
                zzfls zzfls2 = this.zzjsv[i];
                if (zzfls2 != null) {
                    zzflk.zza(3, zzfls2);
                }
                i++;
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int i = 0;
        int zzq = super.zzq();
        if (this.zzjst != null) {
            zzq += zzflk.zzag(1, this.zzjst.intValue());
        }
        if (this.zzjsu != null && this.zzjsu.length > 0) {
            int i2 = zzq;
            for (zzfls zzfls : this.zzjsu) {
                if (zzfls != null) {
                    i2 += zzflk.zzb(2, zzfls);
                }
            }
            zzq = i2;
        }
        if (this.zzjsv != null && this.zzjsv.length > 0) {
            while (i < this.zzjsv.length) {
                zzfls zzfls2 = this.zzjsv[i];
                if (zzfls2 != null) {
                    zzq += zzflk.zzb(3, zzfls2);
                }
                i++;
            }
        }
        return zzq;
    }
}
