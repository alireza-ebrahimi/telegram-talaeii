package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmv extends zzflm<zzfmv> {
    public long zzgoc;
    public String zzpzs;
    public String zzpzt;
    public long zzpzu;
    public String zzpzv;
    public long zzpzw;
    public String zzpzx;
    public String zzpzy;
    public String zzpzz;
    public String zzqaa;
    public String zzqab;
    public int zzqac;
    public zzfmu[] zzqad;

    public zzfmv() {
        this.zzpzs = "";
        this.zzpzt = "";
        this.zzpzu = 0;
        this.zzpzv = "";
        this.zzpzw = 0;
        this.zzgoc = 0;
        this.zzpzx = "";
        this.zzpzy = "";
        this.zzpzz = "";
        this.zzqaa = "";
        this.zzqab = "";
        this.zzqac = 0;
        this.zzqad = zzfmu.zzddi();
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzfmv zzbi(byte[] bArr) throws zzflr {
        return (zzfmv) zzfls.zza(new zzfmv(), bArr);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    this.zzpzs = zzflj.readString();
                    continue;
                case 18:
                    this.zzpzt = zzflj.readString();
                    continue;
                case 24:
                    this.zzpzu = zzflj.zzcxz();
                    continue;
                case 34:
                    this.zzpzv = zzflj.readString();
                    continue;
                case 40:
                    this.zzpzw = zzflj.zzcxz();
                    continue;
                case 48:
                    this.zzgoc = zzflj.zzcxz();
                    continue;
                case 58:
                    this.zzpzx = zzflj.readString();
                    continue;
                case 66:
                    this.zzpzy = zzflj.readString();
                    continue;
                case 74:
                    this.zzpzz = zzflj.readString();
                    continue;
                case 82:
                    this.zzqaa = zzflj.readString();
                    continue;
                case 90:
                    this.zzqab = zzflj.readString();
                    continue;
                case 96:
                    this.zzqac = zzflj.zzcya();
                    continue;
                case 106:
                    int zzb = zzflv.zzb(zzflj, 106);
                    zzcxx = this.zzqad == null ? 0 : this.zzqad.length;
                    Object obj = new zzfmu[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzqad, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzfmu();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzfmu();
                    zzflj.zza(obj[zzcxx]);
                    this.zzqad = obj;
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
        if (!(this.zzpzs == null || this.zzpzs.equals(""))) {
            zzflk.zzp(1, this.zzpzs);
        }
        if (!(this.zzpzt == null || this.zzpzt.equals(""))) {
            zzflk.zzp(2, this.zzpzt);
        }
        if (this.zzpzu != 0) {
            zzflk.zzf(3, this.zzpzu);
        }
        if (!(this.zzpzv == null || this.zzpzv.equals(""))) {
            zzflk.zzp(4, this.zzpzv);
        }
        if (this.zzpzw != 0) {
            zzflk.zzf(5, this.zzpzw);
        }
        if (this.zzgoc != 0) {
            zzflk.zzf(6, this.zzgoc);
        }
        if (!(this.zzpzx == null || this.zzpzx.equals(""))) {
            zzflk.zzp(7, this.zzpzx);
        }
        if (!(this.zzpzy == null || this.zzpzy.equals(""))) {
            zzflk.zzp(8, this.zzpzy);
        }
        if (!(this.zzpzz == null || this.zzpzz.equals(""))) {
            zzflk.zzp(9, this.zzpzz);
        }
        if (!(this.zzqaa == null || this.zzqaa.equals(""))) {
            zzflk.zzp(10, this.zzqaa);
        }
        if (!(this.zzqab == null || this.zzqab.equals(""))) {
            zzflk.zzp(11, this.zzqab);
        }
        if (this.zzqac != 0) {
            zzflk.zzad(12, this.zzqac);
        }
        if (this.zzqad != null && this.zzqad.length > 0) {
            for (zzfls zzfls : this.zzqad) {
                if (zzfls != null) {
                    zzflk.zza(13, zzfls);
                }
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (!(this.zzpzs == null || this.zzpzs.equals(""))) {
            zzq += zzflk.zzq(1, this.zzpzs);
        }
        if (!(this.zzpzt == null || this.zzpzt.equals(""))) {
            zzq += zzflk.zzq(2, this.zzpzt);
        }
        if (this.zzpzu != 0) {
            zzq += zzflk.zzc(3, this.zzpzu);
        }
        if (!(this.zzpzv == null || this.zzpzv.equals(""))) {
            zzq += zzflk.zzq(4, this.zzpzv);
        }
        if (this.zzpzw != 0) {
            zzq += zzflk.zzc(5, this.zzpzw);
        }
        if (this.zzgoc != 0) {
            zzq += zzflk.zzc(6, this.zzgoc);
        }
        if (!(this.zzpzx == null || this.zzpzx.equals(""))) {
            zzq += zzflk.zzq(7, this.zzpzx);
        }
        if (!(this.zzpzy == null || this.zzpzy.equals(""))) {
            zzq += zzflk.zzq(8, this.zzpzy);
        }
        if (!(this.zzpzz == null || this.zzpzz.equals(""))) {
            zzq += zzflk.zzq(9, this.zzpzz);
        }
        if (!(this.zzqaa == null || this.zzqaa.equals(""))) {
            zzq += zzflk.zzq(10, this.zzqaa);
        }
        if (!(this.zzqab == null || this.zzqab.equals(""))) {
            zzq += zzflk.zzq(11, this.zzqab);
        }
        if (this.zzqac != 0) {
            zzq += zzflk.zzag(12, this.zzqac);
        }
        if (this.zzqad == null || this.zzqad.length <= 0) {
            return zzq;
        }
        int i = zzq;
        for (zzfls zzfls : this.zzqad) {
            if (zzfls != null) {
                i += zzflk.zzb(13, zzfls);
            }
        }
        return i;
    }
}
