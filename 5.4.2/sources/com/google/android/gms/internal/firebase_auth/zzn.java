package com.google.android.gms.internal.firebase_auth;

public final class zzn extends zzgn<zzn> {
    public String zzbd;
    private String zzbe;
    private String zzbf;
    private String zzbg;

    public zzn() {
        this.zzbd = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbe = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbg = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzbd = zzgk.readString();
                    continue;
                case 18:
                    this.zzbe = zzgk.readString();
                    continue;
                case 26:
                    this.zzbf = zzgk.readString();
                    continue;
                case 34:
                    this.zzbg = zzgk.readString();
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
        if (!(this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzbd);
        }
        if (!(this.zzbe == null || this.zzbe.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzbe);
        }
        if (!(this.zzbf == null || this.zzbf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzbf);
        }
        if (!(this.zzbg == null || this.zzbg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzbg);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzbd);
        }
        if (!(this.zzbe == null || this.zzbe.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzbe);
        }
        if (!(this.zzbf == null || this.zzbf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzbf);
        }
        return (this.zzbg == null || this.zzbg.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(4, this.zzbg);
    }
}
