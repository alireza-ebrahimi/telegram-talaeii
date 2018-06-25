package com.google.android.gms.internal.firebase_auth;

public final class zzgz extends zzgn<zzgz> {
    public String zzai;
    private String zzgu;
    public String zzjm;
    public String zzjo;
    private String zzyl;

    public zzgz() {
        this.zzjm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzgu = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzai = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzjo = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzyl = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzjm = zzgk.readString();
                    continue;
                case 18:
                    this.zzgu = zzgk.readString();
                    continue;
                case 26:
                    this.zzai = zzgk.readString();
                    continue;
                case 34:
                    this.zzjo = zzgk.readString();
                    continue;
                case 42:
                    this.zzyl = zzgk.readString();
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
        if (!(this.zzjm == null || this.zzjm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzjm);
        }
        if (!(this.zzgu == null || this.zzgu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzgu);
        }
        if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzai);
        }
        if (!(this.zzjo == null || this.zzjo.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzjo);
        }
        if (!(this.zzyl == null || this.zzyl.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzyl);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzjm == null || this.zzjm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzjm);
        }
        if (!(this.zzgu == null || this.zzgu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzgu);
        }
        if (!(this.zzai == null || this.zzai.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzai);
        }
        if (!(this.zzjo == null || this.zzjo.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzjo);
        }
        return (this.zzyl == null || this.zzyl.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(5, this.zzyl);
    }
}
