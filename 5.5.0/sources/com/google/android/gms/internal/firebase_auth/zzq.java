package com.google.android.gms.internal.firebase_auth;

public final class zzq extends zzgn<zzq> {
    private long zzae;
    public String zzaf;
    private String zzbq;
    public boolean zzbt;
    public String zzca;
    public String zzcb;
    private String zzcc;
    private boolean zzcd;
    private boolean zzce;
    public boolean zzcf;
    public String zzr;
    private String zzu;
    private long zzv;

    public zzq() {
        this.zzca = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcb = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcc = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcd = false;
        this.zzr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzae = 0;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbt = false;
        this.zzce = false;
        this.zzcf = true;
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
                    this.zzca = zzgk.readString();
                    continue;
                case 18:
                    this.zzcb = zzgk.readString();
                    continue;
                case 26:
                    this.zzcc = zzgk.readString();
                    continue;
                case 32:
                    this.zzcd = zzgk.zzci();
                    continue;
                case 42:
                    this.zzr = zzgk.readString();
                    continue;
                case 50:
                    this.zzbq = zzgk.readString();
                    continue;
                case 56:
                    this.zzae = zzgk.zzcv();
                    continue;
                case 66:
                    this.zzaf = zzgk.readString();
                    continue;
                case 72:
                    this.zzbt = zzgk.zzci();
                    continue;
                case 80:
                    this.zzce = zzgk.zzci();
                    continue;
                case 88:
                    this.zzcf = zzgk.zzci();
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
        if (!(this.zzca == null || this.zzca.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzca);
        }
        if (!(this.zzcb == null || this.zzcb.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzcb);
        }
        if (!(this.zzcc == null || this.zzcc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzcc);
        }
        if (this.zzcd) {
            zzgl.zzb(4, this.zzcd);
        }
        if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzr);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzbq);
        }
        if (this.zzae != 0) {
            zzgl.zzi(7, this.zzae);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(8, this.zzaf);
        }
        if (this.zzbt) {
            zzgl.zzb(9, this.zzbt);
        }
        if (this.zzce) {
            zzgl.zzb(10, this.zzce);
        }
        if (!this.zzcf) {
            zzgl.zzb(11, this.zzcf);
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
        if (!(this.zzca == null || this.zzca.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzca);
        }
        if (!(this.zzcb == null || this.zzcb.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzcb);
        }
        if (!(this.zzcc == null || this.zzcc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzcc);
        }
        if (this.zzcd) {
            zzb += zzgl.zzaa(4) + 1;
        }
        if (!(this.zzr == null || this.zzr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzr);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzbq);
        }
        if (this.zzae != 0) {
            zzb += zzgl.zzd(7, this.zzae);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(8, this.zzaf);
        }
        if (this.zzbt) {
            zzb += zzgl.zzaa(9) + 1;
        }
        if (this.zzce) {
            zzb += zzgl.zzaa(10) + 1;
        }
        if (!this.zzcf) {
            zzb += zzgl.zzaa(11) + 1;
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(13, this.zzu);
        }
        return this.zzv != 0 ? zzb + zzgl.zze(14, this.zzv) : zzb;
    }
}
