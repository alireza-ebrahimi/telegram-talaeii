package com.google.android.gms.internal.firebase_auth;

public final class zzp extends zzgn<zzp> {
    private String zzaf;
    public String zzah;
    public String zzbh;
    public String zzbi;
    private boolean zzbk;
    private String zzbm;
    private String zzbn;
    private String zzbq;
    private String zzbr;
    private boolean zzbz;
    private String zzu;
    private long zzv;

    public zzp() {
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbi = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbn = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbk = false;
        this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbz = false;
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
                    this.zzbh = zzgk.readString();
                    continue;
                case 34:
                    this.zzbm = zzgk.readString();
                    continue;
                case 42:
                    this.zzbn = zzgk.readString();
                    continue;
                case 50:
                    this.zzbq = zzgk.readString();
                    continue;
                case 58:
                    this.zzaf = zzgk.readString();
                    continue;
                case 64:
                    this.zzbk = zzgk.zzci();
                    continue;
                case 74:
                    this.zzbr = zzgk.readString();
                    continue;
                case 80:
                    this.zzbz = zzgk.zzci();
                    continue;
                case 106:
                    this.zzu = zzgk.readString();
                    continue;
                case 112:
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
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzbh);
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzbn);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzbq);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzaf);
        }
        if (this.zzbk) {
            zzgl.zzb(8, this.zzbk);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(9, this.zzbr);
        }
        if (this.zzbz) {
            zzgl.zzb(10, this.zzbz);
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(13, this.zzu);
        }
        if (this.zzv != 0) {
            zzgl.zza(14, this.zzv);
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
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzbh);
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzbn);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzbq);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzaf);
        }
        if (this.zzbk) {
            zzb += zzgl.zzaa(8) + 1;
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(9, this.zzbr);
        }
        if (this.zzbz) {
            zzb += zzgl.zzaa(10) + 1;
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(13, this.zzu);
        }
        return this.zzv != 0 ? zzb + zzgl.zze(14, this.zzv) : zzb;
    }
}
