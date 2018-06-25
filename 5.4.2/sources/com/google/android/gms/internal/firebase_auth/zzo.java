package com.google.android.gms.internal.firebase_auth;

import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public final class zzo extends zzgn<zzo> {
    private String zzad;
    private long zzae;
    public String zzaf;
    public String zzag;
    public String zzah;
    public String zzbh;
    public String zzbi;
    private String[] zzbj;
    private boolean zzbk;
    private boolean zzbl;
    private String zzbm;
    private String zzbn;
    private zzfm zzbo;
    private boolean zzbp;
    private String zzbq;
    public String zzbr;
    public int[] zzbs;
    public boolean zzbt;
    public String[] zzbu;
    private long zzbv;
    private long zzbw;

    public zzo() {
        this.zzaf = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzad = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbh = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzah = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbi = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbj = zzgw.EMPTY_STRING_ARRAY;
        this.zzag = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbk = false;
        this.zzbl = false;
        this.zzbm = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbn = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbp = false;
        this.zzbq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzae = 0;
        this.zzbr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbs = zzgw.zzti;
        this.zzbt = false;
        this.zzbu = zzgw.EMPTY_STRING_ARRAY;
        this.zzbv = 0;
        this.zzbw = 0;
        this.zzya = -1;
    }

    private final zzo zzd(zzgk zzgk) {
        while (true) {
            int zzcc = zzgk.zzcc();
            int zzb;
            int length;
            Object obj;
            int zzb2;
            switch (zzcc) {
                case 0:
                    break;
                case 18:
                    this.zzaf = zzgk.readString();
                    continue;
                case 26:
                    this.zzad = zzgk.readString();
                    continue;
                case 34:
                    this.zzbh = zzgk.readString();
                    continue;
                case 42:
                    this.zzah = zzgk.readString();
                    continue;
                case 50:
                    this.zzbi = zzgk.readString();
                    continue;
                case 58:
                    zzb = zzgw.zzb(zzgk, 58);
                    length = this.zzbj == null ? 0 : this.zzbj.length;
                    obj = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbj, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzgk.readString();
                        zzgk.zzcc();
                        length++;
                    }
                    obj[length] = zzgk.readString();
                    this.zzbj = obj;
                    continue;
                case 66:
                    this.zzag = zzgk.readString();
                    continue;
                case 72:
                    this.zzbk = zzgk.zzci();
                    continue;
                case 80:
                    this.zzbl = zzgk.zzci();
                    continue;
                case 90:
                    this.zzbm = zzgk.readString();
                    continue;
                case 98:
                    this.zzbn = zzgk.readString();
                    continue;
                case 106:
                    this.zzbo = (zzfm) zzgk.zza(zzfm.zzfw());
                    continue;
                case 112:
                    this.zzbp = zzgk.zzci();
                    continue;
                case 122:
                    this.zzbq = zzgk.readString();
                    continue;
                case 128:
                    this.zzae = zzgk.zzcv();
                    continue;
                case TsExtractor.TS_STREAM_TYPE_DTS /*138*/:
                    this.zzbr = zzgk.readString();
                    continue;
                case 144:
                    zzb2 = zzgw.zzb(zzgk, 144);
                    Object obj2 = new int[zzb2];
                    length = 0;
                    for (zzb = 0; zzb < zzb2; zzb++) {
                        if (zzb != 0) {
                            zzgk.zzcc();
                        }
                        int position = zzgk.getPosition();
                        try {
                            obj2[length] = zze.zza(zzgk.zzcu());
                            length++;
                        } catch (IllegalArgumentException e) {
                            zzgk.zzay(position);
                            zza(zzgk, zzcc);
                        }
                    }
                    if (length != 0) {
                        zzb = this.zzbs == null ? 0 : this.zzbs.length;
                        if (zzb != 0 || length != obj2.length) {
                            Object obj3 = new int[(zzb + length)];
                            if (zzb != 0) {
                                System.arraycopy(this.zzbs, 0, obj3, 0, zzb);
                            }
                            System.arraycopy(obj2, 0, obj3, zzb, length);
                            this.zzbs = obj3;
                            break;
                        }
                        this.zzbs = obj2;
                        break;
                    }
                    continue;
                case 146:
                    zzcc = zzgk.zzp(zzgk.zzcu());
                    zzb = zzgk.getPosition();
                    length = 0;
                    while (zzgk.zzgl() > 0) {
                        try {
                            zze.zza(zzgk.zzcu());
                            length++;
                        } catch (IllegalArgumentException e2) {
                        }
                    }
                    if (length != 0) {
                        zzgk.zzay(zzb);
                        zzb = this.zzbs == null ? 0 : this.zzbs.length;
                        Object obj4 = new int[(length + zzb)];
                        if (zzb != 0) {
                            System.arraycopy(this.zzbs, 0, obj4, 0, zzb);
                        }
                        while (zzgk.zzgl() > 0) {
                            zzb2 = zzgk.getPosition();
                            try {
                                obj4[zzb] = zze.zza(zzgk.zzcu());
                                zzb++;
                            } catch (IllegalArgumentException e3) {
                                zzgk.zzay(zzb2);
                                zza(zzgk, 144);
                            }
                        }
                        this.zzbs = obj4;
                    }
                    zzgk.zzq(zzcc);
                    continue;
                case 152:
                    this.zzbt = zzgk.zzci();
                    continue;
                case 162:
                    zzb = zzgw.zzb(zzgk, 162);
                    length = this.zzbu == null ? 0 : this.zzbu.length;
                    obj = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbu, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzgk.readString();
                        zzgk.zzcc();
                        length++;
                    }
                    obj[length] = zzgk.readString();
                    this.zzbu = obj;
                    continue;
                case 168:
                    this.zzbv = zzgk.zzcv();
                    continue;
                case 176:
                    this.zzbw = zzgk.zzcv();
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
        return zzd(zzgk);
    }

    public final void zza(zzgl zzgl) {
        int i = 0;
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(2, this.zzaf);
        }
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(3, this.zzad);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(4, this.zzbh);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(5, this.zzah);
        }
        if (!(this.zzbi == null || this.zzbi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(6, this.zzbi);
        }
        if (this.zzbj != null && this.zzbj.length > 0) {
            for (String str : this.zzbj) {
                if (str != null) {
                    zzgl.zza(7, str);
                }
            }
        }
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(8, this.zzag);
        }
        if (this.zzbk) {
            zzgl.zzb(9, this.zzbk);
        }
        if (this.zzbl) {
            zzgl.zzb(10, this.zzbl);
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(11, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(12, this.zzbn);
        }
        if (this.zzbo != null) {
            zzgl.zze(13, this.zzbo);
        }
        if (this.zzbp) {
            zzgl.zzb(14, this.zzbp);
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(15, this.zzbq);
        }
        if (this.zzae != 0) {
            zzgl.zzi(16, this.zzae);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzgl.zza(17, this.zzbr);
        }
        if (this.zzbs != null && this.zzbs.length > 0) {
            for (int zzc : this.zzbs) {
                zzgl.zzc(18, zzc);
            }
        }
        if (this.zzbt) {
            zzgl.zzb(19, this.zzbt);
        }
        if (this.zzbu != null && this.zzbu.length > 0) {
            while (i < this.zzbu.length) {
                String str2 = this.zzbu[i];
                if (str2 != null) {
                    zzgl.zza(20, str2);
                }
                i++;
            }
        }
        if (this.zzbv != 0) {
            zzgl.zzi(21, this.zzbv);
        }
        if (this.zzbw != 0) {
            zzgl.zzi(22, this.zzbw);
        }
        super.zza(zzgl);
    }

    protected final int zzb() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        int zzb = super.zzb();
        if (!(this.zzaf == null || this.zzaf.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(2, this.zzaf);
        }
        if (!(this.zzad == null || this.zzad.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(3, this.zzad);
        }
        if (!(this.zzbh == null || this.zzbh.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(4, this.zzbh);
        }
        if (!(this.zzah == null || this.zzah.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(5, this.zzah);
        }
        if (!(this.zzbi == null || this.zzbi.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(6, this.zzbi);
        }
        if (this.zzbj != null && this.zzbj.length > 0) {
            i = 0;
            i2 = 0;
            for (String str : this.zzbj) {
                if (str != null) {
                    i2++;
                    i += zzgl.zzam(str);
                }
            }
            zzb = (zzb + i) + (i2 * 1);
        }
        if (!(this.zzag == null || this.zzag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(8, this.zzag);
        }
        if (this.zzbk) {
            zzb += zzgl.zzaa(9) + 1;
        }
        if (this.zzbl) {
            zzb += zzgl.zzaa(10) + 1;
        }
        if (!(this.zzbm == null || this.zzbm.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(11, this.zzbm);
        }
        if (!(this.zzbn == null || this.zzbn.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(12, this.zzbn);
        }
        if (this.zzbo != null) {
            zzb += zzci.zzc(13, this.zzbo);
        }
        if (this.zzbp) {
            zzb += zzgl.zzaa(14) + 1;
        }
        if (!(this.zzbq == null || this.zzbq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(15, this.zzbq);
        }
        if (this.zzae != 0) {
            zzb += zzgl.zzd(16, this.zzae);
        }
        if (!(this.zzbr == null || this.zzbr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzb += zzgl.zzb(17, this.zzbr);
        }
        if (this.zzbs != null && this.zzbs.length > 0) {
            i = 0;
            for (int i22 : this.zzbs) {
                i += zzgl.zzab(i22);
            }
            zzb = (zzb + i) + (this.zzbs.length * 2);
        }
        if (this.zzbt) {
            zzb += zzgl.zzaa(19) + 1;
        }
        if (this.zzbu != null && this.zzbu.length > 0) {
            i3 = 0;
            i = 0;
            while (i4 < this.zzbu.length) {
                String str2 = this.zzbu[i4];
                if (str2 != null) {
                    i++;
                    i3 += zzgl.zzam(str2);
                }
                i4++;
            }
            zzb = (zzb + i3) + (i * 2);
        }
        if (this.zzbv != 0) {
            zzb += zzgl.zzd(21, this.zzbv);
        }
        return this.zzbw != 0 ? zzb + zzgl.zzd(22, this.zzbw) : zzb;
    }
}
