package com.google.android.gms.internal.firebase_auth;

public final class zzt extends zzgn<zzt> {
    private static volatile zzt[] zzdj;
    public String zzah;
    public String zzbd;
    public String zzbh;
    public String zzbr;
    public String zzcg;
    private String zzde;
    private String zzdk;
    public String zzj;

    public zzt() {
        this.zzj = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzcg = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzdk = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzde = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbd = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzya = -1;
    }

    public static zzt[] zzc() {
        if (zzdj == null) {
            synchronized (zzgr.zzxz) {
                if (zzdj == null) {
                    zzdj = new zzt[0];
                }
            }
        }
        return zzdj;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzj = zzgk.readString();
                    continue;
                case 18:
                    this.zzbh = zzgk.readString();
                    continue;
                case 26:
                    this.zzbr = zzgk.readString();
                    continue;
                case 34:
                    this.zzcg = zzgk.readString();
                    continue;
                case 42:
                    this.zzah = zzgk.readString();
                    continue;
                case 50:
                    this.zzdk = zzgk.readString();
                    continue;
                case 58:
                    this.zzde = zzgk.readString();
                    continue;
                case 74:
                    this.zzbd = zzgk.readString();
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
        if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzj);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzbh);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzbr);
        }
        if (!(this.zzcg == null || this.zzcg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzcg);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzah);
        }
        if (!(this.zzdk == null || this.zzdk.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzdk);
        }
        if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzde);
        }
        if (!(this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(9, this.zzbd);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (!(this.zzj == null || this.zzj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzj);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzbh);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzbr);
        }
        if (!(this.zzcg == null || this.zzcg.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzcg);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzah);
        }
        if (!(this.zzdk == null || this.zzdk.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzdk);
        }
        if (!(this.zzde == null || this.zzde.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzde);
        }
        return (this.zzbd == null || this.zzbd.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzb : zzb + zzgl.zzb(9, this.zzbd);
    }
}
