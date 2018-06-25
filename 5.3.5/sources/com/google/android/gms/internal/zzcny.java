package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcny extends zzflm<zzcny> {
    public String zzjfl;
    public Long zzjtx;
    private Integer zzjty;
    public zzcnz[] zzjtz;
    public zzcnx[] zzjua;
    public zzcnr[] zzjub;

    public zzcny() {
        this.zzjtx = null;
        this.zzjfl = null;
        this.zzjty = null;
        this.zzjtz = zzcnz.zzbcx();
        this.zzjua = zzcnx.zzbcw();
        this.zzjub = zzcnr.zzbcs();
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcny)) {
            return false;
        }
        zzcny zzcny = (zzcny) obj;
        if (this.zzjtx == null) {
            if (zzcny.zzjtx != null) {
                return false;
            }
        } else if (!this.zzjtx.equals(zzcny.zzjtx)) {
            return false;
        }
        if (this.zzjfl == null) {
            if (zzcny.zzjfl != null) {
                return false;
            }
        } else if (!this.zzjfl.equals(zzcny.zzjfl)) {
            return false;
        }
        if (this.zzjty == null) {
            if (zzcny.zzjty != null) {
                return false;
            }
        } else if (!this.zzjty.equals(zzcny.zzjty)) {
            return false;
        }
        return !zzflq.equals(this.zzjtz, zzcny.zzjtz) ? false : !zzflq.equals(this.zzjua, zzcny.zzjua) ? false : !zzflq.equals(this.zzjub, zzcny.zzjub) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcny.zzpvl == null || zzcny.zzpvl.isEmpty() : this.zzpvl.equals(zzcny.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.zzjty == null ? 0 : this.zzjty.hashCode()) + (((this.zzjfl == null ? 0 : this.zzjfl.hashCode()) + (((this.zzjtx == null ? 0 : this.zzjtx.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzflq.hashCode(this.zzjtz)) * 31) + zzflq.hashCode(this.zzjua)) * 31) + zzflq.hashCode(this.zzjub)) * 31;
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
                    this.zzjtx = Long.valueOf(zzflj.zzcyr());
                    continue;
                case 18:
                    this.zzjfl = zzflj.readString();
                    continue;
                case 24:
                    this.zzjty = Integer.valueOf(zzflj.zzcym());
                    continue;
                case 34:
                    zzb = zzflv.zzb(zzflj, 34);
                    zzcxx = this.zzjtz == null ? 0 : this.zzjtz.length;
                    obj = new zzcnz[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjtz, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcnz();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcnz();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjtz = obj;
                    continue;
                case 42:
                    zzb = zzflv.zzb(zzflj, 42);
                    zzcxx = this.zzjua == null ? 0 : this.zzjua.length;
                    obj = new zzcnx[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjua, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcnx();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcnx();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjua = obj;
                    continue;
                case 50:
                    zzb = zzflv.zzb(zzflj, 50);
                    zzcxx = this.zzjub == null ? 0 : this.zzjub.length;
                    obj = new zzcnr[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjub, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcnr();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcnr();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjub = obj;
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
        if (this.zzjtx != null) {
            zzflk.zzf(1, this.zzjtx.longValue());
        }
        if (this.zzjfl != null) {
            zzflk.zzp(2, this.zzjfl);
        }
        if (this.zzjty != null) {
            zzflk.zzad(3, this.zzjty.intValue());
        }
        if (this.zzjtz != null && this.zzjtz.length > 0) {
            for (zzfls zzfls : this.zzjtz) {
                if (zzfls != null) {
                    zzflk.zza(4, zzfls);
                }
            }
        }
        if (this.zzjua != null && this.zzjua.length > 0) {
            for (zzfls zzfls2 : this.zzjua) {
                if (zzfls2 != null) {
                    zzflk.zza(5, zzfls2);
                }
            }
        }
        if (this.zzjub != null && this.zzjub.length > 0) {
            while (i < this.zzjub.length) {
                zzfls zzfls3 = this.zzjub[i];
                if (zzfls3 != null) {
                    zzflk.zza(6, zzfls3);
                }
                i++;
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int i;
        int i2 = 0;
        int zzq = super.zzq();
        if (this.zzjtx != null) {
            zzq += zzflk.zzc(1, this.zzjtx.longValue());
        }
        if (this.zzjfl != null) {
            zzq += zzflk.zzq(2, this.zzjfl);
        }
        if (this.zzjty != null) {
            zzq += zzflk.zzag(3, this.zzjty.intValue());
        }
        if (this.zzjtz != null && this.zzjtz.length > 0) {
            i = zzq;
            for (zzfls zzfls : this.zzjtz) {
                if (zzfls != null) {
                    i += zzflk.zzb(4, zzfls);
                }
            }
            zzq = i;
        }
        if (this.zzjua != null && this.zzjua.length > 0) {
            i = zzq;
            for (zzfls zzfls2 : this.zzjua) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(5, zzfls2);
                }
            }
            zzq = i;
        }
        if (this.zzjub != null && this.zzjub.length > 0) {
            while (i2 < this.zzjub.length) {
                zzfls zzfls3 = this.zzjub[i2];
                if (zzfls3 != null) {
                    zzq += zzflk.zzb(6, zzfls3);
                }
                i2++;
            }
        }
        return zzq;
    }
}
