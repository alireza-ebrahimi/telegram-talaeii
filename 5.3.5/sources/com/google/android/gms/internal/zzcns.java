package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcns extends zzflm<zzcns> {
    private static volatile zzcns[] zzjsw;
    public Integer zzjsx;
    public String zzjsy;
    public zzcnt[] zzjsz;
    private Boolean zzjta;
    public zzcnu zzjtb;

    public zzcns() {
        this.zzjsx = null;
        this.zzjsy = null;
        this.zzjsz = zzcnt.zzbcu();
        this.zzjta = null;
        this.zzjtb = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcns[] zzbct() {
        if (zzjsw == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjsw == null) {
                    zzjsw = new zzcns[0];
                }
            }
        }
        return zzjsw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcns)) {
            return false;
        }
        zzcns zzcns = (zzcns) obj;
        if (this.zzjsx == null) {
            if (zzcns.zzjsx != null) {
                return false;
            }
        } else if (!this.zzjsx.equals(zzcns.zzjsx)) {
            return false;
        }
        if (this.zzjsy == null) {
            if (zzcns.zzjsy != null) {
                return false;
            }
        } else if (!this.zzjsy.equals(zzcns.zzjsy)) {
            return false;
        }
        if (!zzflq.equals(this.zzjsz, zzcns.zzjsz)) {
            return false;
        }
        if (this.zzjta == null) {
            if (zzcns.zzjta != null) {
                return false;
            }
        } else if (!this.zzjta.equals(zzcns.zzjta)) {
            return false;
        }
        if (this.zzjtb == null) {
            if (zzcns.zzjtb != null) {
                return false;
            }
        } else if (!this.zzjtb.equals(zzcns.zzjtb)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcns.zzpvl == null || zzcns.zzpvl.isEmpty() : this.zzpvl.equals(zzcns.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzjta == null ? 0 : this.zzjta.hashCode()) + (((((this.zzjsy == null ? 0 : this.zzjsy.hashCode()) + (((this.zzjsx == null ? 0 : this.zzjsx.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + zzflq.hashCode(this.zzjsz)) * 31);
        zzcnu zzcnu = this.zzjtb;
        hashCode = ((zzcnu == null ? 0 : zzcnu.hashCode()) + (hashCode * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 8:
                    this.zzjsx = Integer.valueOf(zzflj.zzcym());
                    continue;
                case 18:
                    this.zzjsy = zzflj.readString();
                    continue;
                case 26:
                    int zzb = zzflv.zzb(zzflj, 26);
                    zzcxx = this.zzjsz == null ? 0 : this.zzjsz.length;
                    Object obj = new zzcnt[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjsz, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcnt();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcnt();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjsz = obj;
                    continue;
                case 32:
                    this.zzjta = Boolean.valueOf(zzflj.zzcyd());
                    continue;
                case 42:
                    if (this.zzjtb == null) {
                        this.zzjtb = new zzcnu();
                    }
                    zzflj.zza(this.zzjtb);
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
        if (this.zzjsx != null) {
            zzflk.zzad(1, this.zzjsx.intValue());
        }
        if (this.zzjsy != null) {
            zzflk.zzp(2, this.zzjsy);
        }
        if (this.zzjsz != null && this.zzjsz.length > 0) {
            for (zzfls zzfls : this.zzjsz) {
                if (zzfls != null) {
                    zzflk.zza(3, zzfls);
                }
            }
        }
        if (this.zzjta != null) {
            zzflk.zzl(4, this.zzjta.booleanValue());
        }
        if (this.zzjtb != null) {
            zzflk.zza(5, this.zzjtb);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjsx != null) {
            zzq += zzflk.zzag(1, this.zzjsx.intValue());
        }
        if (this.zzjsy != null) {
            zzq += zzflk.zzq(2, this.zzjsy);
        }
        if (this.zzjsz != null && this.zzjsz.length > 0) {
            int i = zzq;
            for (zzfls zzfls : this.zzjsz) {
                if (zzfls != null) {
                    i += zzflk.zzb(3, zzfls);
                }
            }
            zzq = i;
        }
        if (this.zzjta != null) {
            this.zzjta.booleanValue();
            zzq += zzflk.zzlw(4) + 1;
        }
        return this.zzjtb != null ? zzq + zzflk.zzb(5, this.zzjtb) : zzq;
    }
}
