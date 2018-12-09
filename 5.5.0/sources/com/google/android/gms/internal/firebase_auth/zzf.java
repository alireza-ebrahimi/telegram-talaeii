package com.google.android.gms.internal.firebase_auth;

public final class zzf extends zzgn<zzf> {
    private static volatile zzf[] zze;
    private String value;
    private String zzf;

    public zzf() {
        this.zzf = TtmlNode.ANONYMOUS_REGION_ID;
        this.value = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public static zzf[] zza() {
        if (zze == null) {
            synchronized (zzgr.zzxz) {
                if (zze == null) {
                    zze = new zzf[0];
                }
            }
        }
        return zze;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzf = zzgk.readString();
                    continue;
                case 18:
                    this.value = zzgk.readString();
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
        if (!(this.zzf == null || this.zzf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzf);
        }
        if (!(this.value == null || this.value.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.value);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzf == null || this.zzf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzf);
        }
        return (this.value == null || this.value.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(2, this.value);
    }
}
