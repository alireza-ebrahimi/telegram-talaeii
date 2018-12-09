package com.google.android.gms.internal.firebase_auth;

public final class zzs extends zzgn<zzs> {
    private long zzae;
    private String zzaf;
    public String zzah;
    public String zzbi;
    private String zzbm;
    private String zzbn;
    private String zzbq;
    public boolean zzbt;
    private String zzcc;
    private String zzdi;
    private String zzu;
    private long zzv;

    public zzs() {
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbi = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcc = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbn = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzdi = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzae = 0;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbt = false;
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
                    this.zzah = zzgk.readString();
                    continue;
                case 18:
                    this.zzbi = zzgk.readString();
                    continue;
                case 26:
                    this.zzcc = zzgk.readString();
                    continue;
                case 34:
                    this.zzbm = zzgk.readString();
                    continue;
                case 42:
                    this.zzbn = zzgk.readString();
                    continue;
                case 50:
                    this.zzdi = zzgk.readString();
                    continue;
                case 58:
                    this.zzbq = zzgk.readString();
                    continue;
                case 64:
                    this.zzae = zzgk.zzcv();
                    continue;
                case 74:
                    this.zzaf = zzgk.readString();
                    continue;
                case 80:
                    this.zzbt = zzgk.zzci();
                    continue;
                case 90:
                    this.zzu = zzgk.readString();
                    continue;
                case 96:
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
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzah);
        }
        if (!(this.zzbi == null || this.zzbi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzbi);
        }
        if (!(this.zzcc == null || this.zzcc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzcc);
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzbn);
        }
        if (!(this.zzdi == null || this.zzdi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzdi);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzbq);
        }
        if (this.zzae != 0) {
            zzgl.zzi(8, this.zzae);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(9, this.zzaf);
        }
        if (this.zzbt) {
            zzgl.zzb(10, this.zzbt);
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(11, this.zzu);
        }
        if (this.zzv != 0) {
            zzgl.zza(12, this.zzv);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzah);
        }
        if (!(this.zzbi == null || this.zzbi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzbi);
        }
        if (!(this.zzcc == null || this.zzcc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzcc);
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzbn);
        }
        if (!(this.zzdi == null || this.zzdi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzdi);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzbq);
        }
        if (this.zzae != 0) {
            zzb += zzgl.zzd(8, this.zzae);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(9, this.zzaf);
        }
        if (this.zzbt) {
            zzb += zzgl.zzaa(10) + 1;
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(11, this.zzu);
        }
        return this.zzv != 0 ? zzb + zzgl.zze(12, this.zzv) : zzb;
    }
}
