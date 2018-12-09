package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;

public final class zzu extends zzgn<zzu> {
    private static volatile zzu[] zzdl;
    private int version;
    public String zzad;
    public String zzah;
    public String zzbd;
    public String zzbh;
    private String[] zzbj;
    public boolean zzbk;
    public String zzbr;
    public long zzbv;
    public long zzbw;
    public zzt[] zzbx;
    private boolean zzbz;
    private String zzcl;
    private String zzcm;
    private String zzcn;
    private String zzde;
    private byte[] zzdm;
    private byte[] zzdn;
    private long zzdo;
    private long zzdp;
    private boolean zzdq;
    public String zzdr;

    public zzu() {
        this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbj = zzgw.EMPTY_STRING_ARRAY;
        this.zzcl = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcn = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzdm = zzgw.zzyk;
        this.zzdn = zzgw.zzyk;
        this.version = 0;
        this.zzbk = false;
        this.zzdo = 0;
        this.zzbx = zzt.zzc();
        this.zzdp = 0;
        this.zzbz = false;
        this.zzbv = 0;
        this.zzbw = 0;
        this.zzde = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzdq = false;
        this.zzdr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbd = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public static zzu[] zzd() {
        if (zzdl == null) {
            synchronized (zzgr.zzxz) {
                if (zzdl == null) {
                    zzdl = new zzu[0];
                }
            }
        }
        return zzdl;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            int zzb;
            Object obj;
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzad = zzgk.readString();
                    continue;
                case 18:
                    this.zzah = zzgk.readString();
                    continue;
                case 26:
                    this.zzbh = zzgk.readString();
                    continue;
                case 34:
                    zzb = zzgw.zzb(zzgk, 34);
                    zzcc = this.zzbj == null ? 0 : this.zzbj.length;
                    obj = new String[(zzb + zzcc)];
                    if (zzcc != 0) {
                        System.arraycopy(this.zzbj, 0, obj, 0, zzcc);
                    }
                    while (zzcc < obj.length - 1) {
                        obj[zzcc] = zzgk.readString();
                        zzgk.zzcc();
                        zzcc++;
                    }
                    obj[zzcc] = zzgk.readString();
                    this.zzbj = obj;
                    continue;
                case 42:
                    this.zzcl = zzgk.readString();
                    continue;
                case 50:
                    this.zzbr = zzgk.readString();
                    continue;
                case 58:
                    this.zzcm = zzgk.readString();
                    continue;
                case 66:
                    this.zzcn = zzgk.readString();
                    continue;
                case 74:
                    this.zzdm = zzgk.readBytes();
                    continue;
                case 82:
                    this.zzdn = zzgk.readBytes();
                    continue;
                case 88:
                    this.version = zzgk.zzcu();
                    continue;
                case 96:
                    this.zzbk = zzgk.zzci();
                    continue;
                case 104:
                    this.zzdo = zzgk.zzcv();
                    continue;
                case 114:
                    zzb = zzgw.zzb(zzgk, 114);
                    zzcc = this.zzbx == null ? 0 : this.zzbx.length;
                    obj = new zzt[(zzb + zzcc)];
                    if (zzcc != 0) {
                        System.arraycopy(this.zzbx, 0, obj, 0, zzcc);
                    }
                    while (zzcc < obj.length - 1) {
                        obj[zzcc] = new zzt();
                        zzgk.zzb(obj[zzcc]);
                        zzgk.zzcc();
                        zzcc++;
                    }
                    obj[zzcc] = new zzt();
                    zzgk.zzb(obj[zzcc]);
                    this.zzbx = obj;
                    continue;
                case 120:
                    this.zzdp = zzgk.zzcv();
                    continue;
                case 128:
                    this.zzbz = zzgk.zzci();
                    continue;
                case 136:
                    this.zzbv = zzgk.zzcv();
                    continue;
                case 144:
                    this.zzbw = zzgk.zzcv();
                    continue;
                case 154:
                    this.zzde = zzgk.readString();
                    continue;
                case 160:
                    this.zzdq = zzgk.zzci();
                    continue;
                case 170:
                    this.zzdr = zzgk.readString();
                    continue;
                case 178:
                    this.zzbd = zzgk.readString();
                    continue;
                default:
                    if (!super.zza(zzgk, zzcc)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public final void zza(zzgl zzgl) {
        int i = 0;
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzad);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzah);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzbh);
        }
        if (this.zzbj != null && this.zzbj.length > 0) {
            for (String str : this.zzbj) {
                if (str != null) {
                    zzgl.zza(4, str);
                }
            }
        }
        if (!(this.zzcl == null || this.zzcl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzcl);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzbr);
        }
        if (!(this.zzcm == null || this.zzcm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzcm);
        }
        if (!(this.zzcn == null || this.zzcn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(8, this.zzcn);
        }
        if (!Arrays.equals(this.zzdm, zzgw.zzyk)) {
            zzgl.zza(9, this.zzdm);
        }
        if (!Arrays.equals(this.zzdn, zzgw.zzyk)) {
            zzgl.zza(10, this.zzdn);
        }
        if (this.version != 0) {
            zzgl.zzc(11, this.version);
        }
        if (this.zzbk) {
            zzgl.zzb(12, this.zzbk);
        }
        if (this.zzdo != 0) {
            zzgl.zzi(13, this.zzdo);
        }
        if (this.zzbx != null && this.zzbx.length > 0) {
            while (i < this.zzbx.length) {
                zzgt zzgt = this.zzbx[i];
                if (zzgt != null) {
                    zzgl.zza(14, zzgt);
                }
                i++;
            }
        }
        if (this.zzdp != 0) {
            zzgl.zzi(15, this.zzdp);
        }
        if (this.zzbz) {
            zzgl.zzb(16, this.zzbz);
        }
        if (this.zzbv != 0) {
            zzgl.zzi(17, this.zzbv);
        }
        if (this.zzbw != 0) {
            zzgl.zzi(18, this.zzbw);
        }
        if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(19, this.zzde);
        }
        if (this.zzdq) {
            zzgl.zzb(20, this.zzdq);
        }
        if (!(this.zzdr == null || this.zzdr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(21, this.zzdr);
        }
        if (!(this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(22, this.zzbd);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int i = 0;
        int zzb = super.zzb();
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzad);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzah);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzbh);
        }
        if (this.zzbj != null && this.zzbj.length > 0) {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzbj) {
                if (str != null) {
                    i3++;
                    i2 += zzgl.zzam(str);
                }
            }
            zzb = (zzb + i2) + (i3 * 1);
        }
        if (!(this.zzcl == null || this.zzcl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzcl);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzbr);
        }
        if (!(this.zzcm == null || this.zzcm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzcm);
        }
        if (!(this.zzcn == null || this.zzcn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(8, this.zzcn);
        }
        if (!Arrays.equals(this.zzdm, zzgw.zzyk)) {
            zzb += zzgl.zzb(9, this.zzdm);
        }
        if (!Arrays.equals(this.zzdn, zzgw.zzyk)) {
            zzb += zzgl.zzb(10, this.zzdn);
        }
        if (this.version != 0) {
            zzb += zzgl.zzg(11, this.version);
        }
        if (this.zzbk) {
            zzb += zzgl.zzaa(12) + 1;
        }
        if (this.zzdo != 0) {
            zzb += zzgl.zzd(13, this.zzdo);
        }
        if (this.zzbx != null && this.zzbx.length > 0) {
            while (i < this.zzbx.length) {
                zzgt zzgt = this.zzbx[i];
                if (zzgt != null) {
                    zzb += zzgl.zzb(14, zzgt);
                }
                i++;
            }
        }
        if (this.zzdp != 0) {
            zzb += zzgl.zzd(15, this.zzdp);
        }
        if (this.zzbz) {
            zzb += zzgl.zzaa(16) + 1;
        }
        if (this.zzbv != 0) {
            zzb += zzgl.zzd(17, this.zzbv);
        }
        if (this.zzbw != 0) {
            zzb += zzgl.zzd(18, this.zzbw);
        }
        if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(19, this.zzde);
        }
        if (this.zzdq) {
            zzb += zzgl.zzaa(20) + 1;
        }
        if (!(this.zzdr == null || this.zzdr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(21, this.zzdr);
        }
        return (this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(22, this.zzbd);
    }
}
