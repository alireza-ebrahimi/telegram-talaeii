package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class zzdnl extends zzflm<zzdnl> {
    public byte[] zzlwr;
    public String zzlws;
    public double zzlwt;
    public float zzlwu;
    public long zzlwv;
    public int zzlww;
    public int zzlwx;
    public boolean zzlwy;
    public zzdnj[] zzlwz;
    public zzdnk[] zzlxa;
    public String[] zzlxb;
    public long[] zzlxc;
    public float[] zzlxd;
    public long zzlxe;

    public zzdnl() {
        this.zzlwr = zzflv.zzpwe;
        this.zzlws = "";
        this.zzlwt = 0.0d;
        this.zzlwu = 0.0f;
        this.zzlwv = 0;
        this.zzlww = 0;
        this.zzlwx = 0;
        this.zzlwy = false;
        this.zzlwz = zzdnj.zzbmb();
        this.zzlxa = zzdnk.zzbmc();
        this.zzlxb = zzflv.EMPTY_STRING_ARRAY;
        this.zzlxc = zzflv.zzpvz;
        this.zzlxd = zzflv.zzpwa;
        this.zzlxe = 0;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdnl)) {
            return false;
        }
        zzdnl zzdnl = (zzdnl) obj;
        if (!Arrays.equals(this.zzlwr, zzdnl.zzlwr)) {
            return false;
        }
        if (this.zzlws == null) {
            if (zzdnl.zzlws != null) {
                return false;
            }
        } else if (!this.zzlws.equals(zzdnl.zzlws)) {
            return false;
        }
        return Double.doubleToLongBits(this.zzlwt) != Double.doubleToLongBits(zzdnl.zzlwt) ? false : Float.floatToIntBits(this.zzlwu) != Float.floatToIntBits(zzdnl.zzlwu) ? false : this.zzlwv != zzdnl.zzlwv ? false : this.zzlww != zzdnl.zzlww ? false : this.zzlwx != zzdnl.zzlwx ? false : this.zzlwy != zzdnl.zzlwy ? false : !zzflq.equals(this.zzlwz, zzdnl.zzlwz) ? false : !zzflq.equals(this.zzlxa, zzdnl.zzlxa) ? false : !zzflq.equals(this.zzlxb, zzdnl.zzlxb) ? false : !zzflq.equals(this.zzlxc, zzdnl.zzlxc) ? false : !zzflq.equals(this.zzlxd, zzdnl.zzlxd) ? false : this.zzlxe != zzdnl.zzlxe ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzdnl.zzpvl == null || zzdnl.zzpvl.isEmpty() : this.zzpvl.equals(zzdnl.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzlws == null ? 0 : this.zzlws.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzlwr)) * 31);
        long doubleToLongBits = Double.doubleToLongBits(this.zzlwt);
        hashCode = ((((((((((((((this.zzlwy ? 1231 : 1237) + (((((((((((hashCode * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + Float.floatToIntBits(this.zzlwu)) * 31) + ((int) (this.zzlwv ^ (this.zzlwv >>> 32)))) * 31) + this.zzlww) * 31) + this.zzlwx) * 31)) * 31) + zzflq.hashCode(this.zzlwz)) * 31) + zzflq.hashCode(this.zzlxa)) * 31) + zzflq.hashCode(this.zzlxb)) * 31) + zzflq.hashCode(this.zzlxc)) * 31) + zzflq.hashCode(this.zzlxd)) * 31) + ((int) (this.zzlxe ^ (this.zzlxe >>> 32)))) * 31;
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
            int zzli;
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    this.zzlwr = zzflj.readBytes();
                    continue;
                case 18:
                    this.zzlws = zzflj.readString();
                    continue;
                case 25:
                    this.zzlwt = Double.longBitsToDouble(zzflj.zzcyt());
                    continue;
                case 37:
                    this.zzlwu = Float.intBitsToFloat(zzflj.zzcys());
                    continue;
                case 40:
                    this.zzlwv = zzflj.zzcyr();
                    continue;
                case 48:
                    this.zzlww = zzflj.zzcym();
                    continue;
                case 56:
                    zzcxx = zzflj.zzcym();
                    this.zzlwx = (-(zzcxx & 1)) ^ (zzcxx >>> 1);
                    continue;
                case 64:
                    this.zzlwy = zzflj.zzcyd();
                    continue;
                case 74:
                    zzb = zzflv.zzb(zzflj, 74);
                    zzcxx = this.zzlwz == null ? 0 : this.zzlwz.length;
                    obj = new zzdnj[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlwz, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzdnj();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzdnj();
                    zzflj.zza(obj[zzcxx]);
                    this.zzlwz = obj;
                    continue;
                case 82:
                    zzb = zzflv.zzb(zzflj, 82);
                    zzcxx = this.zzlxa == null ? 0 : this.zzlxa.length;
                    obj = new zzdnk[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlxa, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzdnk();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzdnk();
                    zzflj.zza(obj[zzcxx]);
                    this.zzlxa = obj;
                    continue;
                case 90:
                    zzb = zzflv.zzb(zzflj, 90);
                    zzcxx = this.zzlxb == null ? 0 : this.zzlxb.length;
                    obj = new String[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlxb, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.readString();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.readString();
                    this.zzlxb = obj;
                    continue;
                case 96:
                    zzb = zzflv.zzb(zzflj, 96);
                    zzcxx = this.zzlxc == null ? 0 : this.zzlxc.length;
                    obj = new long[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlxc, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcyr();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcyr();
                    this.zzlxc = obj;
                    continue;
                case 98:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcyr();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzlxc == null ? 0 : this.zzlxc.length;
                    Object obj2 = new long[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzlxc, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcyr();
                        zzb++;
                    }
                    this.zzlxc = obj2;
                    zzflj.zzlj(zzli);
                    continue;
                case 104:
                    this.zzlxe = zzflj.zzcyr();
                    continue;
                case 114:
                    zzcxx = zzflj.zzcym();
                    zzb = zzflj.zzli(zzcxx);
                    zzli = zzcxx / 4;
                    zzcxx = this.zzlxd == null ? 0 : this.zzlxd.length;
                    Object obj3 = new float[(zzli + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlxd, 0, obj3, 0, zzcxx);
                    }
                    while (zzcxx < obj3.length) {
                        obj3[zzcxx] = Float.intBitsToFloat(zzflj.zzcys());
                        zzcxx++;
                    }
                    this.zzlxd = obj3;
                    zzflj.zzlj(zzb);
                    continue;
                case 117:
                    zzb = zzflv.zzb(zzflj, 117);
                    zzcxx = this.zzlxd == null ? 0 : this.zzlxd.length;
                    obj = new float[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlxd, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = Float.intBitsToFloat(zzflj.zzcys());
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = Float.intBitsToFloat(zzflj.zzcys());
                    this.zzlxd = obj;
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
        if (!Arrays.equals(this.zzlwr, zzflv.zzpwe)) {
            zzflk.zzc(1, this.zzlwr);
        }
        if (!(this.zzlws == null || this.zzlws.equals(""))) {
            zzflk.zzp(2, this.zzlws);
        }
        if (Double.doubleToLongBits(this.zzlwt) != Double.doubleToLongBits(0.0d)) {
            zzflk.zza(3, this.zzlwt);
        }
        if (Float.floatToIntBits(this.zzlwu) != Float.floatToIntBits(0.0f)) {
            zzflk.zzd(4, this.zzlwu);
        }
        if (this.zzlwv != 0) {
            zzflk.zzf(5, this.zzlwv);
        }
        if (this.zzlww != 0) {
            zzflk.zzad(6, this.zzlww);
        }
        if (this.zzlwx != 0) {
            int i2 = this.zzlwx;
            zzflk.zzac(7, 0);
            zzflk.zzmy(zzflk.zzme(i2));
        }
        if (this.zzlwy) {
            zzflk.zzl(8, this.zzlwy);
        }
        if (this.zzlwz != null && this.zzlwz.length > 0) {
            for (zzfls zzfls : this.zzlwz) {
                if (zzfls != null) {
                    zzflk.zza(9, zzfls);
                }
            }
        }
        if (this.zzlxa != null && this.zzlxa.length > 0) {
            for (zzfls zzfls2 : this.zzlxa) {
                if (zzfls2 != null) {
                    zzflk.zza(10, zzfls2);
                }
            }
        }
        if (this.zzlxb != null && this.zzlxb.length > 0) {
            for (String str : this.zzlxb) {
                if (str != null) {
                    zzflk.zzp(11, str);
                }
            }
        }
        if (this.zzlxc != null && this.zzlxc.length > 0) {
            for (long zzf : this.zzlxc) {
                zzflk.zzf(12, zzf);
            }
        }
        if (this.zzlxe != 0) {
            zzflk.zzf(13, this.zzlxe);
        }
        if (this.zzlxd != null && this.zzlxd.length > 0) {
            while (i < this.zzlxd.length) {
                zzflk.zzd(14, this.zzlxd[i]);
                i++;
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int i;
        int i2 = 0;
        int zzq = super.zzq();
        if (!Arrays.equals(this.zzlwr, zzflv.zzpwe)) {
            zzq += zzflk.zzd(1, this.zzlwr);
        }
        if (!(this.zzlws == null || this.zzlws.equals(""))) {
            zzq += zzflk.zzq(2, this.zzlws);
        }
        if (Double.doubleToLongBits(this.zzlwt) != Double.doubleToLongBits(0.0d)) {
            zzq += zzflk.zzlw(3) + 8;
        }
        if (Float.floatToIntBits(this.zzlwu) != Float.floatToIntBits(0.0f)) {
            zzq += zzflk.zzlw(4) + 4;
        }
        if (this.zzlwv != 0) {
            zzq += zzflk.zzc(5, this.zzlwv);
        }
        if (this.zzlww != 0) {
            zzq += zzflk.zzag(6, this.zzlww);
        }
        if (this.zzlwx != 0) {
            zzq += zzflk.zzmf(zzflk.zzme(this.zzlwx)) + zzflk.zzlw(7);
        }
        if (this.zzlwy) {
            zzq += zzflk.zzlw(8) + 1;
        }
        if (this.zzlwz != null && this.zzlwz.length > 0) {
            i = zzq;
            for (zzfls zzfls : this.zzlwz) {
                if (zzfls != null) {
                    i += zzflk.zzb(9, zzfls);
                }
            }
            zzq = i;
        }
        if (this.zzlxa != null && this.zzlxa.length > 0) {
            i = zzq;
            for (zzfls zzfls2 : this.zzlxa) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(10, zzfls2);
                }
            }
            zzq = i;
        }
        if (this.zzlxb != null && this.zzlxb.length > 0) {
            int i3 = 0;
            int i4 = 0;
            for (String str : this.zzlxb) {
                if (str != null) {
                    i4++;
                    i3 += zzflk.zztx(str);
                }
            }
            zzq = (zzq + i3) + (i4 * 1);
        }
        if (this.zzlxc != null && this.zzlxc.length > 0) {
            i = 0;
            while (i2 < this.zzlxc.length) {
                i += zzflk.zzdj(this.zzlxc[i2]);
                i2++;
            }
            zzq = (zzq + i) + (this.zzlxc.length * 1);
        }
        if (this.zzlxe != 0) {
            zzq += zzflk.zzc(13, this.zzlxe);
        }
        return (this.zzlxd == null || this.zzlxd.length <= 0) ? zzq : (zzq + (this.zzlxd.length * 4)) + (this.zzlxd.length * 1);
    }
}
