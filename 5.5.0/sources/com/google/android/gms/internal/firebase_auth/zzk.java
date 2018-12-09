package com.google.android.gms.internal.firebase_auth;

public final class zzk extends zzgn<zzk> {
    private long zzae;
    public String zzaf;
    private String[] zzal;
    private String[] zzam;

    public zzk() {
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzal = zzgw.EMPTY_STRING_ARRAY;
        this.zzam = zzgw.EMPTY_STRING_ARRAY;
        this.zzae = 0;
        this.zzya = -1;
    }

    public final /* synthetic */ zzgt zza(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            int zzb;
            Object obj;
            switch (zzcc) {
                case 0:
                    break;
                case 10:
                    this.zzaf = zzgk.readString();
                    continue;
                case 18:
                    zzb = zzgw.zzb(zzgk, 18);
                    zzcc = this.zzal == null ? 0 : this.zzal.length;
                    obj = new String[(zzb + zzcc)];
                    if (zzcc != 0) {
                        System.arraycopy(this.zzal, 0, obj, 0, zzcc);
                    }
                    while (zzcc < obj.length - 1) {
                        obj[zzcc] = zzgk.readString();
                        zzgk.zzcc();
                        zzcc++;
                    }
                    obj[zzcc] = zzgk.readString();
                    this.zzal = obj;
                    continue;
                case 26:
                    zzb = zzgw.zzb(zzgk, 26);
                    zzcc = this.zzam == null ? 0 : this.zzam.length;
                    obj = new String[(zzb + zzcc)];
                    if (zzcc != 0) {
                        System.arraycopy(this.zzam, 0, obj, 0, zzcc);
                    }
                    while (zzcc < obj.length - 1) {
                        obj[zzcc] = zzgk.readString();
                        zzgk.zzcc();
                        zzcc++;
                    }
                    obj[zzcc] = zzgk.readString();
                    this.zzam = obj;
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
        int i = 0;
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(1, this.zzaf);
        }
        if (this.zzal != null && this.zzal.length > 0) {
            for (String str : this.zzal) {
                if (str != null) {
                    zzgl.zza(2, str);
                }
            }
        }
        if (this.zzam != null && this.zzam.length > 0) {
            while (i < this.zzam.length) {
                String str2 = this.zzam[i];
                if (str2 != null) {
                    zzgl.zza(3, str2);
                }
                i++;
            }
        }
        if (this.zzae != 0) {
            zzgl.zzi(4, this.zzae);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int i;
        int i2;
        int i3 = 0;
        int zzb = super.zzb();
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(1, this.zzaf);
        }
        if (this.zzal != null && this.zzal.length > 0) {
            i = 0;
            int i4 = 0;
            for (String str : this.zzal) {
                if (str != null) {
                    i4++;
                    i += zzgl.zzam(str);
                }
            }
            zzb = (zzb + i) + (i4 * 1);
        }
        if (this.zzam != null && this.zzam.length > 0) {
            i2 = 0;
            i = 0;
            while (i3 < this.zzam.length) {
                String str2 = this.zzam[i3];
                if (str2 != null) {
                    i++;
                    i2 += zzgl.zzam(str2);
                }
                i3++;
            }
            zzb = (zzb + i2) + (i * 1);
        }
        return this.zzae != 0 ? zzb + zzgl.zzd(4, this.zzae) : zzb;
    }
}
