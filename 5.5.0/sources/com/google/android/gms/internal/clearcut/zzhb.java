package com.google.android.gms.internal.clearcut;

public final class zzhb extends zzfu<zzhb> implements Cloneable {
    private static volatile zzhb[] zzbkd;
    private String value;
    private String zzbke;

    public zzhb() {
        this.zzbke = TtmlNode.ANONYMOUS_REGION_ID;
        this.value = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzrj = null;
        this.zzrs = -1;
    }

    public static zzhb[] zzge() {
        if (zzbkd == null) {
            synchronized (zzfy.zzrr) {
                if (zzbkd == null) {
                    zzbkd = new zzhb[0];
                }
            }
        }
        return zzbkd;
    }

    private final zzhb zzgf() {
        try {
            return (zzhb) super.zzeo();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzgf();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhb)) {
            return false;
        }
        zzhb zzhb = (zzhb) obj;
        if (this.zzbke == null) {
            if (zzhb.zzbke != null) {
                return false;
            }
        } else if (!this.zzbke.equals(zzhb.zzbke)) {
            return false;
        }
        if (this.value == null) {
            if (zzhb.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzhb.value)) {
            return false;
        }
        return (this.zzrj == null || this.zzrj.isEmpty()) ? zzhb.zzrj == null || zzhb.zzrj.isEmpty() : this.zzrj.equals(zzhb.zzrj);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.zzbke == null ? 0 : this.zzbke.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (!(this.zzrj == null || this.zzrj.isEmpty())) {
            i = this.zzrj.hashCode();
        }
        return hashCode + i;
    }

    public final void zza(zzfs zzfs) {
        if (!(this.zzbke == null || this.zzbke.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(1, this.zzbke);
        }
        if (!(this.value == null || this.value.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(2, this.value);
        }
        super.zza(zzfs);
    }

    protected final int zzen() {
        int zzen = super.zzen();
        if (!(this.zzbke == null || this.zzbke.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzen += zzfs.zzb(1, this.zzbke);
        }
        return (this.value == null || this.value.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzen : zzen + zzfs.zzb(2, this.value);
    }

    public final /* synthetic */ zzfu zzeo() {
        return (zzhb) clone();
    }

    public final /* synthetic */ zzfz zzep() {
        return (zzhb) clone();
    }
}
