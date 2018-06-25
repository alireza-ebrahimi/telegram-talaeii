package com.google.android.gms.internal.firebase_auth;

public final class zzh extends zzgn<zzh> {
    public String zzg;
    public String zzh;
    private String zzi;
    private String zzj;
    private String zzk;
    private String zzl;
    private String zzm;
    private String zzn;
    private String zzo;
    private String zzp;
    private String zzq;
    private String zzr;
    private String zzs;
    private zzf[] zzt;
    private String zzu;
    private long zzv;

    public zzh() {
        this.zzg = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzi = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzj = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzk = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzl = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzn = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzo = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzp = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzs = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzt = zzf.zza();
        this.zzu = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzv = 0;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzg = zzgk.readString();
                    continue;
                case 18:
                    this.zzh = zzgk.readString();
                    continue;
                case 26:
                    this.zzi = zzgk.readString();
                    continue;
                case 34:
                    this.zzj = zzgk.readString();
                    continue;
                case 42:
                    this.zzk = zzgk.readString();
                    continue;
                case 50:
                    this.zzl = zzgk.readString();
                    continue;
                case 58:
                    this.zzm = zzgk.readString();
                    continue;
                case 66:
                    this.zzn = zzgk.readString();
                    continue;
                case 74:
                    this.zzo = zzgk.readString();
                    continue;
                case 82:
                    this.zzp = zzgk.readString();
                    continue;
                case 90:
                    this.zzq = zzgk.readString();
                    continue;
                case 98:
                    this.zzr = zzgk.readString();
                    continue;
                case 106:
                    this.zzs = zzgk.readString();
                    continue;
                case 114:
                    int zzb = zzgw.zzb(zzgk, 114);
                    zzcc = this.zzt == null ? 0 : this.zzt.length;
                    Object obj = new zzf[(zzb + zzcc)];
                    if (zzcc != 0) {
                        System.arraycopy(this.zzt, 0, obj, 0, zzcc);
                    }
                    while (zzcc < obj.length - 1) {
                        obj[zzcc] = new zzf();
                        zzgk.zzb(obj[zzcc]);
                        zzgk.zzcc();
                        zzcc++;
                    }
                    obj[zzcc] = new zzf();
                    zzgk.zzb(obj[zzcc]);
                    this.zzt = obj;
                    continue;
                case 122:
                    this.zzu = zzgk.readString();
                    continue;
                case 128:
                    this.zzv = zzgk.zzcv();
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
        if (!(this.zzg == null || this.zzg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzg);
        }
        if (!(this.zzh == null || this.zzh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzh);
        }
        if (!(this.zzi == null || this.zzi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzi);
        }
        if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzj);
        }
        if (!(this.zzk == null || this.zzk.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzk);
        }
        if (!(this.zzl == null || this.zzl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzl);
        }
        if (!(this.zzm == null || this.zzm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzm);
        }
        if (!(this.zzn == null || this.zzn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(8, this.zzn);
        }
        if (!(this.zzo == null || this.zzo.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(9, this.zzo);
        }
        if (!(this.zzp == null || this.zzp.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(10, this.zzp);
        }
        if (!(this.zzq == null || this.zzq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(11, this.zzq);
        }
        if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(12, this.zzr);
        }
        if (!(this.zzs == null || this.zzs.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(13, this.zzs);
        }
        if (this.zzt != null && this.zzt.length > 0) {
            for (zzgt zzgt : this.zzt) {
                if (zzgt != null) {
                    zzgl.zza(14, zzgt);
                }
            }
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(15, this.zzu);
        }
        if (this.zzv != 0) {
            zzgl.zza(16, this.zzv);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzg == null || this.zzg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzg);
        }
        if (!(this.zzh == null || this.zzh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzh);
        }
        if (!(this.zzi == null || this.zzi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzi);
        }
        if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzj);
        }
        if (!(this.zzk == null || this.zzk.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzk);
        }
        if (!(this.zzl == null || this.zzl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzl);
        }
        if (!(this.zzm == null || this.zzm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzm);
        }
        if (!(this.zzn == null || this.zzn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(8, this.zzn);
        }
        if (!(this.zzo == null || this.zzo.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(9, this.zzo);
        }
        if (!(this.zzp == null || this.zzp.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(10, this.zzp);
        }
        if (!(this.zzq == null || this.zzq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(11, this.zzq);
        }
        if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(12, this.zzr);
        }
        if (!(this.zzs == null || this.zzs.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(13, this.zzs);
        }
        if (this.zzt != null && this.zzt.length > 0) {
            int i = zzb;
            for (zzgt zzgt : this.zzt) {
                if (zzgt != null) {
                    i += zzgl.zzb(14, zzgt);
                }
            }
            zzb = i;
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(15, this.zzu);
        }
        return this.zzv != 0 ? zzb + zzgl.zze(16, this.zzv) : zzb;
    }
}
