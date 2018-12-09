package com.google.android.gms.internal.firebase_auth;

public final class zzm extends zzgn<zzm> {
    public String zzag;
    private String zzah;
    public String zzba;
    private String zzbb;

    public zzm() {
        this.zzag = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzba = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbb = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzag = zzgk.readString();
                    continue;
                case 18:
                    this.zzba = zzgk.readString();
                    continue;
                case 26:
                    this.zzbb = zzgk.readString();
                    continue;
                case 34:
                    this.zzah = zzgk.readString();
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
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzag);
        }
        if (!(this.zzba == null || this.zzba.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzba);
        }
        if (!(this.zzbb == null || this.zzbb.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzbb);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzah);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzag);
        }
        if (!(this.zzba == null || this.zzba.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzba);
        }
        if (!(this.zzbb == null || this.zzbb.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzbb);
        }
        return (this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(4, this.zzah);
    }
}
