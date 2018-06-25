package com.google.android.gms.internal.firebase_auth;

public final class zzr extends zzgn<zzr> {
    private long zzae;
    private String zzbq;
    public boolean zzbt;
    public String zzdh;

    public zzr() {
        this.zzdh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbt = false;
        this.zzae = 0;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzdh = zzgk.readString();
                    continue;
                case 18:
                    this.zzbq = zzgk.readString();
                    continue;
                case 24:
                    this.zzbt = zzgk.zzci();
                    continue;
                case 32:
                    this.zzae = zzgk.zzcv();
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
        if (!(this.zzdh == null || this.zzdh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzdh);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzbq);
        }
        if (this.zzbt) {
            zzgl.zzb(3, this.zzbt);
        }
        if (this.zzae != 0) {
            zzgl.zzi(4, this.zzae);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzdh == null || this.zzdh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzdh);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzbq);
        }
        if (this.zzbt) {
            zzb += zzgl.zzaa(3) + 1;
        }
        return this.zzae != 0 ? zzb + zzgl.zzd(4, this.zzae) : zzb;
    }
}
