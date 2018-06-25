package com.google.android.gms.internal.firebase_auth;

public final class zzl extends zzgn<zzl> {
    public String zzaf;
    public String zzah;
    public int zzao;
    private String zzap;
    private String zzaq;
    private String zzar;
    private String zzas;
    public String zzat;
    public String zzau;
    public String zzav;
    public String zzaw;
    public boolean zzax;
    public String zzay;
    public boolean zzaz;

    public zzl() {
        this.zzao = 0;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzap = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzar = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzas = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzat = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzau = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzav = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaw = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzax = false;
        this.zzay = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzaz = false;
        this.zzya = -1;
    }

    private final zzl zzb(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            switch (zzcc) {
                case 0:
                    break;
                case 8:
                    int position = zzgk.getPosition();
                    try {
                        this.zzao = zzgx.zzbe(zzgk.zzcu());
                        continue;
                    } catch (IllegalArgumentException e) {
                        zzgk.zzay(position);
                        zza(zzgk, zzcc);
                        break;
                    }
                case 18:
                    this.zzah = zzgk.readString();
                    continue;
                case 26:
                    this.zzap = zzgk.readString();
                    continue;
                case 34:
                    this.zzaq = zzgk.readString();
                    continue;
                case 42:
                    this.zzar = zzgk.readString();
                    continue;
                case 50:
                    this.zzas = zzgk.readString();
                    continue;
                case 58:
                    this.zzaf = zzgk.readString();
                    continue;
                case 66:
                    this.zzat = zzgk.readString();
                    continue;
                case 74:
                    this.zzau = zzgk.readString();
                    continue;
                case 82:
                    this.zzav = zzgk.readString();
                    continue;
                case 90:
                    this.zzaw = zzgk.readString();
                    continue;
                case 96:
                    this.zzax = zzgk.zzci();
                    continue;
                case 106:
                    this.zzay = zzgk.readString();
                    continue;
                case 112:
                    this.zzaz = zzgk.zzci();
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

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        return zzb(zzgk);
    }

    public final void zza(zzgl zzgl) {
        if (this.zzao != 0) {
            zzgl.zzc(1, this.zzao);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzah);
        }
        if (!(this.zzap == null || this.zzap.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzap);
        }
        if (!(this.zzaq == null || this.zzaq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzaq);
        }
        if (!(this.zzar == null || this.zzar.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzar);
        }
        if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzas);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(7, this.zzaf);
        }
        if (!(this.zzat == null || this.zzat.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(8, this.zzat);
        }
        if (!(this.zzau == null || this.zzau.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(9, this.zzau);
        }
        if (!(this.zzav == null || this.zzav.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(10, this.zzav);
        }
        if (!(this.zzaw == null || this.zzaw.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(11, this.zzaw);
        }
        if (this.zzax) {
            zzgl.zzb(12, this.zzax);
        }
        if (!(this.zzay == null || this.zzay.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(13, this.zzay);
        }
        if (this.zzaz) {
            zzgl.zzb(14, this.zzaz);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int zzb = super.zzb();
        if (this.zzao != 0) {
            zzb += zzgl.zzg(1, this.zzao);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzah);
        }
        if (!(this.zzap == null || this.zzap.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzap);
        }
        if (!(this.zzaq == null || this.zzaq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzaq);
        }
        if (!(this.zzar == null || this.zzar.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzar);
        }
        if (!(this.zzas == null || this.zzas.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzas);
        }
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(7, this.zzaf);
        }
        if (!(this.zzat == null || this.zzat.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(8, this.zzat);
        }
        if (!(this.zzau == null || this.zzau.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(9, this.zzau);
        }
        if (!(this.zzav == null || this.zzav.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(10, this.zzav);
        }
        if (!(this.zzaw == null || this.zzaw.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(11, this.zzaw);
        }
        if (this.zzax) {
            zzb += zzgl.zzaa(12) + 1;
        }
        if (!(this.zzay == null || this.zzay.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(13, this.zzay);
        }
        return this.zzaz ? zzb + (zzgl.zzaa(14) + 1) : zzb;
    }
}
