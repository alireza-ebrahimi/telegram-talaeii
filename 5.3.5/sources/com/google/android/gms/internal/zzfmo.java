package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmo extends zzflm<zzfmo> implements Cloneable {
    private String[] zzpyk;
    private String[] zzpyl;
    private int[] zzpym;
    private long[] zzpyn;
    private long[] zzpyo;

    public zzfmo() {
        this.zzpyk = zzflv.EMPTY_STRING_ARRAY;
        this.zzpyl = zzflv.EMPTY_STRING_ARRAY;
        this.zzpym = zzflv.zzpvy;
        this.zzpyn = zzflv.zzpvz;
        this.zzpyo = zzflv.zzpvz;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    private zzfmo zzddb() {
        try {
            zzfmo zzfmo = (zzfmo) super.zzdck();
            if (this.zzpyk != null && this.zzpyk.length > 0) {
                zzfmo.zzpyk = (String[]) this.zzpyk.clone();
            }
            if (this.zzpyl != null && this.zzpyl.length > 0) {
                zzfmo.zzpyl = (String[]) this.zzpyl.clone();
            }
            if (this.zzpym != null && this.zzpym.length > 0) {
                zzfmo.zzpym = (int[]) this.zzpym.clone();
            }
            if (this.zzpyn != null && this.zzpyn.length > 0) {
                zzfmo.zzpyn = (long[]) this.zzpyn.clone();
            }
            if (this.zzpyo != null && this.zzpyo.length > 0) {
                zzfmo.zzpyo = (long[]) this.zzpyo.clone();
            }
            return zzfmo;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzddb();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmo)) {
            return false;
        }
        zzfmo zzfmo = (zzfmo) obj;
        return !zzflq.equals(this.zzpyk, zzfmo.zzpyk) ? false : !zzflq.equals(this.zzpyl, zzfmo.zzpyl) ? false : !zzflq.equals(this.zzpym, zzfmo.zzpym) ? false : !zzflq.equals(this.zzpyn, zzfmo.zzpyn) ? false : !zzflq.equals(this.zzpyo, zzfmo.zzpyo) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzfmo.zzpvl == null || zzfmo.zzpvl.isEmpty() : this.zzpvl.equals(zzfmo.zzpvl);
    }

    public final int hashCode() {
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.hashCode(this.zzpyk)) * 31) + zzflq.hashCode(this.zzpyl)) * 31) + zzflq.hashCode(this.zzpym)) * 31) + zzflq.hashCode(this.zzpyn)) * 31) + zzflq.hashCode(this.zzpyo)) * 31;
        int hashCode2 = (this.zzpvl == null || this.zzpvl.isEmpty()) ? 0 : this.zzpvl.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            int zzb;
            Object obj;
            int zzli;
            Object obj2;
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    zzb = zzflv.zzb(zzflj, 10);
                    zzcxx = this.zzpyk == null ? 0 : this.zzpyk.length;
                    obj = new String[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpyk, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.readString();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.readString();
                    this.zzpyk = obj;
                    continue;
                case 18:
                    zzb = zzflv.zzb(zzflj, 18);
                    zzcxx = this.zzpyl == null ? 0 : this.zzpyl.length;
                    obj = new String[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpyl, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.readString();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.readString();
                    this.zzpyl = obj;
                    continue;
                case 24:
                    zzb = zzflv.zzb(zzflj, 24);
                    zzcxx = this.zzpym == null ? 0 : this.zzpym.length;
                    obj = new int[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpym, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcya();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcya();
                    this.zzpym = obj;
                    continue;
                case 26:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcya();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzpym == null ? 0 : this.zzpym.length;
                    obj2 = new int[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzpym, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcya();
                        zzb++;
                    }
                    this.zzpym = obj2;
                    zzflj.zzlj(zzli);
                    continue;
                case 32:
                    zzb = zzflv.zzb(zzflj, 32);
                    zzcxx = this.zzpyn == null ? 0 : this.zzpyn.length;
                    obj = new long[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpyn, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcxz();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcxz();
                    this.zzpyn = obj;
                    continue;
                case 34:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcxz();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzpyn == null ? 0 : this.zzpyn.length;
                    obj2 = new long[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzpyn, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcxz();
                        zzb++;
                    }
                    this.zzpyn = obj2;
                    zzflj.zzlj(zzli);
                    continue;
                case 40:
                    zzb = zzflv.zzb(zzflj, 40);
                    zzcxx = this.zzpyo == null ? 0 : this.zzpyo.length;
                    obj = new long[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpyo, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcxz();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcxz();
                    this.zzpyo = obj;
                    continue;
                case 42:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcxz();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzpyo == null ? 0 : this.zzpyo.length;
                    obj2 = new long[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzpyo, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcxz();
                        zzb++;
                    }
                    this.zzpyo = obj2;
                    zzflj.zzlj(zzli);
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
        if (this.zzpyk != null && this.zzpyk.length > 0) {
            for (String str : this.zzpyk) {
                if (str != null) {
                    zzflk.zzp(1, str);
                }
            }
        }
        if (this.zzpyl != null && this.zzpyl.length > 0) {
            for (String str2 : this.zzpyl) {
                if (str2 != null) {
                    zzflk.zzp(2, str2);
                }
            }
        }
        if (this.zzpym != null && this.zzpym.length > 0) {
            for (int zzad : this.zzpym) {
                zzflk.zzad(3, zzad);
            }
        }
        if (this.zzpyn != null && this.zzpyn.length > 0) {
            for (long zzf : this.zzpyn) {
                zzflk.zzf(4, zzf);
            }
        }
        if (this.zzpyo != null && this.zzpyo.length > 0) {
            while (i < this.zzpyo.length) {
                zzflk.zzf(5, this.zzpyo[i]);
                i++;
            }
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzdck() throws CloneNotSupportedException {
        return (zzfmo) clone();
    }

    public final /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzfmo) clone();
    }

    protected final int zzq() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        int zzq = super.zzq();
        if (this.zzpyk == null || this.zzpyk.length <= 0) {
            i = zzq;
        } else {
            i2 = 0;
            i3 = 0;
            for (String str : this.zzpyk) {
                if (str != null) {
                    i3++;
                    i2 += zzflk.zztx(str);
                }
            }
            i = (zzq + i2) + (i3 * 1);
        }
        if (this.zzpyl != null && this.zzpyl.length > 0) {
            i3 = 0;
            zzq = 0;
            for (String str2 : this.zzpyl) {
                if (str2 != null) {
                    zzq++;
                    i3 += zzflk.zztx(str2);
                }
            }
            i = (i + i3) + (zzq * 1);
        }
        if (this.zzpym != null && this.zzpym.length > 0) {
            i3 = 0;
            for (int zzq2 : this.zzpym) {
                i3 += zzflk.zzlx(zzq2);
            }
            i = (i + i3) + (this.zzpym.length * 1);
        }
        if (this.zzpyn != null && this.zzpyn.length > 0) {
            i3 = 0;
            for (long zzdj : this.zzpyn) {
                i3 += zzflk.zzdj(zzdj);
            }
            i = (i + i3) + (this.zzpyn.length * 1);
        }
        if (this.zzpyo == null || this.zzpyo.length <= 0) {
            return i;
        }
        i2 = 0;
        while (i4 < this.zzpyo.length) {
            i2 += zzflk.zzdj(this.zzpyo[i4]);
            i4++;
        }
        return (i + i2) + (this.zzpyo.length * 1);
    }
}
