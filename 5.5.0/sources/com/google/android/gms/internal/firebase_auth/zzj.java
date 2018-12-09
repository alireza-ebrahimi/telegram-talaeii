package com.google.android.gms.internal.firebase_auth;

public final class zzj extends zzgn<zzj> {
    public String zzaf;
    public String zzag;
    public String zzah;
    private String zzu;
    private long zzv;

    public zzj() {
        this.zzag = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
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
                    this.zzag = zzgk.readString();
                    continue;
                case 18:
                    this.zzah = zzgk.readString();
                    continue;
                case 26:
                    this.zzaf = zzgk.readString();
                    continue;
                case 50:
                    this.zzu = zzgk.readString();
                    continue;
                case 56:
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
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzag);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzah);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzaf);
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzu);
        }
        if (this.zzv != 0) {
            zzgl.zza(7, this.zzv);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzag);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzah);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzaf);
        }
        if (!(this.zzu == null || this.zzu.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzu);
        }
        return this.zzv != 0 ? zzb + zzgl.zze(7, this.zzv) : zzb;
    }
}
