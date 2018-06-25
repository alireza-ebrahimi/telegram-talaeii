package com.google.android.gms.internal.firebase_auth;

public final class zzi extends zzgn<zzi> {
    private String zzad;
    private long zzae;
    public String zzaf;

    public zzi() {
        this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzae = 0;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzad = zzgk.readString();
                    continue;
                case 16:
                    this.zzae = zzgk.zzcv();
                    continue;
                case 26:
                    this.zzaf = zzgk.readString();
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
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzad);
        }
        if (this.zzae != 0) {
            zzgl.zzi(2, this.zzae);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzaf);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzad);
        }
        if (this.zzae != 0) {
            zzb += zzgl.zzd(2, this.zzae);
        }
        return (this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(3, this.zzaf);
    }
}
