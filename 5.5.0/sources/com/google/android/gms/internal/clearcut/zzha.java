package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzge.zzd;
import com.google.android.gms.internal.clearcut.zzge.zzs;
import java.util.Arrays;

public final class zzha extends zzfu<zzha> implements Cloneable {
    private String tag;
    public long zzbjf;
    public long zzbjg;
    private long zzbjh;
    public int zzbji;
    private String zzbjj;
    private int zzbjk;
    private boolean zzbjl;
    private zzhb[] zzbjm;
    private byte[] zzbjn;
    private zzd zzbjo;
    public byte[] zzbjp;
    private String zzbjq;
    private String zzbjr;
    private zzgy zzbjs;
    private String zzbjt;
    public long zzbju;
    private zzgz zzbjv;
    public byte[] zzbjw;
    private String zzbjx;
    private int zzbjy;
    private int[] zzbjz;
    private long zzbka;
    private zzs zzbkb;
    public boolean zzbkc;

    public zzha() {
        this.zzbjf = 0;
        this.zzbjg = 0;
        this.zzbjh = 0;
        this.tag = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbji = 0;
        this.zzbjj = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbjk = 0;
        this.zzbjl = false;
        this.zzbjm = zzhb.zzge();
        this.zzbjn = zzgb.zzse;
        this.zzbjo = null;
        this.zzbjp = zzgb.zzse;
        this.zzbjq = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbjr = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbjs = null;
        this.zzbjt = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbju = 180000;
        this.zzbjv = null;
        this.zzbjw = zzgb.zzse;
        this.zzbjx = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbjy = 0;
        this.zzbjz = zzgb.zzrx;
        this.zzbka = 0;
        this.zzbkb = null;
        this.zzbkc = false;
        this.zzrj = null;
        this.zzrs = -1;
    }

    private final zzha zzgd() {
        try {
            zzha zzha = (zzha) super.zzeo();
            if (this.zzbjm != null && this.zzbjm.length > 0) {
                zzha.zzbjm = new zzhb[this.zzbjm.length];
                for (int i = 0; i < this.zzbjm.length; i++) {
                    if (this.zzbjm[i] != null) {
                        zzha.zzbjm[i] = (zzhb) this.zzbjm[i].clone();
                    }
                }
            }
            if (this.zzbjo != null) {
                zzha.zzbjo = this.zzbjo;
            }
            if (this.zzbjs != null) {
                zzha.zzbjs = (zzgy) this.zzbjs.clone();
            }
            if (this.zzbjv != null) {
                zzha.zzbjv = (zzgz) this.zzbjv.clone();
            }
            if (this.zzbjz != null && this.zzbjz.length > 0) {
                zzha.zzbjz = (int[]) this.zzbjz.clone();
            }
            if (this.zzbkb != null) {
                zzha.zzbkb = this.zzbkb;
            }
            return zzha;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzgd();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzha)) {
            return false;
        }
        zzha zzha = (zzha) obj;
        if (this.zzbjf != zzha.zzbjf) {
            return false;
        }
        if (this.zzbjg != zzha.zzbjg) {
            return false;
        }
        if (0 != 0) {
            return false;
        }
        if (this.tag == null) {
            if (zzha.tag != null) {
                return false;
            }
        } else if (!this.tag.equals(zzha.tag)) {
            return false;
        }
        if (this.zzbji != zzha.zzbji) {
            return false;
        }
        if (this.zzbjj == null) {
            if (zzha.zzbjj != null) {
                return false;
            }
        } else if (!this.zzbjj.equals(zzha.zzbjj)) {
            return false;
        }
        if (!zzfy.equals(this.zzbjm, zzha.zzbjm)) {
            return false;
        }
        if (!Arrays.equals(this.zzbjn, zzha.zzbjn)) {
            return false;
        }
        if (this.zzbjo == null) {
            if (zzha.zzbjo != null) {
                return false;
            }
        } else if (!this.zzbjo.equals(zzha.zzbjo)) {
            return false;
        }
        if (!Arrays.equals(this.zzbjp, zzha.zzbjp)) {
            return false;
        }
        if (this.zzbjq == null) {
            if (zzha.zzbjq != null) {
                return false;
            }
        } else if (!this.zzbjq.equals(zzha.zzbjq)) {
            return false;
        }
        if (this.zzbjr == null) {
            if (zzha.zzbjr != null) {
                return false;
            }
        } else if (!this.zzbjr.equals(zzha.zzbjr)) {
            return false;
        }
        if (this.zzbjs == null) {
            if (zzha.zzbjs != null) {
                return false;
            }
        } else if (!this.zzbjs.equals(zzha.zzbjs)) {
            return false;
        }
        if (this.zzbjt == null) {
            if (zzha.zzbjt != null) {
                return false;
            }
        } else if (!this.zzbjt.equals(zzha.zzbjt)) {
            return false;
        }
        if (this.zzbju != zzha.zzbju) {
            return false;
        }
        if (this.zzbjv == null) {
            if (zzha.zzbjv != null) {
                return false;
            }
        } else if (!this.zzbjv.equals(zzha.zzbjv)) {
            return false;
        }
        if (!Arrays.equals(this.zzbjw, zzha.zzbjw)) {
            return false;
        }
        if (this.zzbjx == null) {
            if (zzha.zzbjx != null) {
                return false;
            }
        } else if (!this.zzbjx.equals(zzha.zzbjx)) {
            return false;
        }
        if (!zzfy.equals(this.zzbjz, zzha.zzbjz)) {
            return false;
        }
        if (0 != 0) {
            return false;
        }
        if (this.zzbkb == null) {
            if (zzha.zzbkb != null) {
                return false;
            }
        } else if (!this.zzbkb.equals(zzha.zzbkb)) {
            return false;
        }
        return this.zzbkc != zzha.zzbkc ? false : (this.zzrj == null || this.zzrj.isEmpty()) ? zzha.zzrj == null || zzha.zzrj.isEmpty() : this.zzrj.equals(zzha.zzrj);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.zzbjj == null ? 0 : this.zzbjj.hashCode()) + (((((this.tag == null ? 0 : this.tag.hashCode()) + (((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzbjf ^ (this.zzbjf >>> 32)))) * 31) + ((int) (this.zzbjg ^ (this.zzbjg >>> 32)))) * 31) * 31)) * 31) + this.zzbji) * 31)) * 31) * 31) + 1237) * 31) + zzfy.hashCode(this.zzbjm)) * 31) + Arrays.hashCode(this.zzbjn);
        zzcg zzcg = this.zzbjo;
        hashCode = (this.zzbjr == null ? 0 : this.zzbjr.hashCode()) + (((this.zzbjq == null ? 0 : this.zzbjq.hashCode()) + (((((zzcg == null ? 0 : zzcg.hashCode()) + (hashCode * 31)) * 31) + Arrays.hashCode(this.zzbjp)) * 31)) * 31);
        zzgy zzgy = this.zzbjs;
        hashCode = (((this.zzbjt == null ? 0 : this.zzbjt.hashCode()) + (((zzgy == null ? 0 : zzgy.hashCode()) + (hashCode * 31)) * 31)) * 31) + ((int) (this.zzbju ^ (this.zzbju >>> 32)));
        zzgz zzgz = this.zzbjv;
        hashCode = (((((this.zzbjx == null ? 0 : this.zzbjx.hashCode()) + (((((zzgz == null ? 0 : zzgz.hashCode()) + (hashCode * 31)) * 31) + Arrays.hashCode(this.zzbjw)) * 31)) * 31) * 31) + zzfy.hashCode(this.zzbjz)) * 31;
        zzcg = this.zzbkb;
        hashCode = ((this.zzbkc ? 1231 : 1237) + (((zzcg == null ? 0 : zzcg.hashCode()) + (hashCode * 31)) * 31)) * 31;
        if (!(this.zzrj == null || this.zzrj.isEmpty())) {
            i = this.zzrj.hashCode();
        }
        return hashCode + i;
    }

    public final void zza(zzfs zzfs) {
        int i = 0;
        if (this.zzbjf != 0) {
            zzfs.zzi(1, this.zzbjf);
        }
        if (!(this.tag == null || this.tag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(2, this.tag);
        }
        if (this.zzbjm != null && this.zzbjm.length > 0) {
            for (zzfz zzfz : this.zzbjm) {
                if (zzfz != null) {
                    zzfs.zza(3, zzfz);
                }
            }
        }
        if (!Arrays.equals(this.zzbjn, zzgb.zzse)) {
            zzfs.zza(4, this.zzbjn);
        }
        if (!Arrays.equals(this.zzbjp, zzgb.zzse)) {
            zzfs.zza(6, this.zzbjp);
        }
        if (this.zzbjs != null) {
            zzfs.zza(7, this.zzbjs);
        }
        if (!(this.zzbjq == null || this.zzbjq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(8, this.zzbjq);
        }
        if (this.zzbjo != null) {
            zzfs.zze(9, this.zzbjo);
        }
        if (this.zzbji != 0) {
            zzfs.zzc(11, this.zzbji);
        }
        if (!(this.zzbjr == null || this.zzbjr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(13, this.zzbjr);
        }
        if (!(this.zzbjt == null || this.zzbjt.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(14, this.zzbjt);
        }
        if (this.zzbju != 180000) {
            long j = this.zzbju;
            zzfs.zzb(15, 0);
            zzfs.zzn(zzfs.zzj(j));
        }
        if (this.zzbjv != null) {
            zzfs.zza(16, this.zzbjv);
        }
        if (this.zzbjg != 0) {
            zzfs.zzi(17, this.zzbjg);
        }
        if (!Arrays.equals(this.zzbjw, zzgb.zzse)) {
            zzfs.zza(18, this.zzbjw);
        }
        if (this.zzbjz != null && this.zzbjz.length > 0) {
            while (i < this.zzbjz.length) {
                zzfs.zzc(20, this.zzbjz[i]);
                i++;
            }
        }
        if (0 != 0) {
            zzfs.zzi(21, 0);
        }
        if (0 != 0) {
            zzfs.zzi(22, 0);
        }
        if (this.zzbkb != null) {
            zzfs.zze(23, this.zzbkb);
        }
        if (!(this.zzbjx == null || this.zzbjx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(24, this.zzbjx);
        }
        if (this.zzbkc) {
            zzfs.zzb(25, this.zzbkc);
        }
        if (!(this.zzbjj == null || this.zzbjj.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(26, this.zzbjj);
        }
        super.zza(zzfs);
    }

    protected final int zzen() {
        int i;
        int i2 = 0;
        int zzen = super.zzen();
        if (this.zzbjf != 0) {
            zzen += zzfs.zzd(1, this.zzbjf);
        }
        if (!(this.tag == null || this.tag.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(2, this.tag);
        }
        if (this.zzbjm != null && this.zzbjm.length > 0) {
            i = zzen;
            for (zzfz zzfz : this.zzbjm) {
                if (zzfz != null) {
                    i += zzfs.zzb(3, zzfz);
                }
            }
            zzen = i;
        }
        if (!Arrays.equals(this.zzbjn, zzgb.zzse)) {
            zzen += zzfs.zzb(4, this.zzbjn);
        }
        if (!Arrays.equals(this.zzbjp, zzgb.zzse)) {
            zzen += zzfs.zzb(6, this.zzbjp);
        }
        if (this.zzbjs != null) {
            zzen += zzfs.zzb(7, this.zzbjs);
        }
        if (!(this.zzbjq == null || this.zzbjq.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(8, this.zzbjq);
        }
        if (this.zzbjo != null) {
            zzen += zzbn.zzc(9, this.zzbjo);
        }
        if (this.zzbji != 0) {
            zzen += zzfs.zzr(11) + zzfs.zzs(this.zzbji);
        }
        if (!(this.zzbjr == null || this.zzbjr.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(13, this.zzbjr);
        }
        if (!(this.zzbjt == null || this.zzbjt.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(14, this.zzbjt);
        }
        if (this.zzbju != 180000) {
            zzen += zzfs.zzo(zzfs.zzj(this.zzbju)) + zzfs.zzr(15);
        }
        if (this.zzbjv != null) {
            zzen += zzfs.zzb(16, this.zzbjv);
        }
        if (this.zzbjg != 0) {
            zzen += zzfs.zzd(17, this.zzbjg);
        }
        if (!Arrays.equals(this.zzbjw, zzgb.zzse)) {
            zzen += zzfs.zzb(18, this.zzbjw);
        }
        if (this.zzbjz != null && this.zzbjz.length > 0) {
            i = 0;
            while (i2 < this.zzbjz.length) {
                i += zzfs.zzs(this.zzbjz[i2]);
                i2++;
            }
            zzen = (zzen + i) + (this.zzbjz.length * 2);
        }
        if (0 != 0) {
            zzen += zzfs.zzd(21, 0);
        }
        if (0 != 0) {
            zzen += zzfs.zzd(22, 0);
        }
        if (this.zzbkb != null) {
            zzen += zzbn.zzc(23, this.zzbkb);
        }
        if (!(this.zzbjx == null || this.zzbjx.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(24, this.zzbjx);
        }
        if (this.zzbkc) {
            zzen += zzfs.zzr(25) + 1;
        }
        return (this.zzbjj == null || this.zzbjj.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzen : zzen + zzfs.zzb(26, this.zzbjj);
    }

    public final /* synthetic */ zzfu zzeo() {
        return (zzha) clone();
    }

    public final /* synthetic */ zzfz zzep() {
        return (zzha) clone();
    }
}
