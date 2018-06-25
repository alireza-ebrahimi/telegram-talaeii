package com.google.android.gms.internal.clearcut;

import java.util.Arrays;

public final class zzgz extends zzfu<zzgz> implements Cloneable {
    private byte[] zzbjb;
    private String zzbjc;
    private byte[][] zzbjd;
    private boolean zzbje;

    public zzgz() {
        this.zzbjb = zzgb.zzse;
        this.zzbjc = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzbjd = zzgb.zzsd;
        this.zzbje = false;
        this.zzrj = null;
        this.zzrs = -1;
    }

    private final zzgz zzgc() {
        try {
            zzgz zzgz = (zzgz) super.zzeo();
            if (this.zzbjd != null && this.zzbjd.length > 0) {
                zzgz.zzbjd = (byte[][]) this.zzbjd.clone();
            }
            return zzgz;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() {
        return zzgc();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgz)) {
            return false;
        }
        zzgz zzgz = (zzgz) obj;
        if (!Arrays.equals(this.zzbjb, zzgz.zzbjb)) {
            return false;
        }
        if (this.zzbjc == null) {
            if (zzgz.zzbjc != null) {
                return false;
            }
        } else if (!this.zzbjc.equals(zzgz.zzbjc)) {
            return false;
        }
        return !zzfy.zza(this.zzbjd, zzgz.zzbjd) ? false : (this.zzrj == null || this.zzrj.isEmpty()) ? zzgz.zzrj == null || zzgz.zzrj.isEmpty() : this.zzrj.equals(zzgz.zzrj);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((this.zzbjc == null ? 0 : this.zzbjc.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzbjb)) * 31)) * 31) + zzfy.zza(this.zzbjd)) * 31) + 1237) * 31;
        if (!(this.zzrj == null || this.zzrj.isEmpty())) {
            i = this.zzrj.hashCode();
        }
        return hashCode + i;
    }

    public final void zza(zzfs zzfs) {
        if (!Arrays.equals(this.zzbjb, zzgb.zzse)) {
            zzfs.zza(1, this.zzbjb);
        }
        if (this.zzbjd != null && this.zzbjd.length > 0) {
            for (byte[] bArr : this.zzbjd) {
                if (bArr != null) {
                    zzfs.zza(2, bArr);
                }
            }
        }
        if (!(this.zzbjc == null || this.zzbjc.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            zzfs.zza(4, this.zzbjc);
        }
        super.zza(zzfs);
    }

    protected final int zzen() {
        int i = 0;
        int zzen = super.zzen();
        if (!Arrays.equals(this.zzbjb, zzgb.zzse)) {
            zzen += zzfs.zzb(1, this.zzbjb);
        }
        if (this.zzbjd != null && this.zzbjd.length > 0) {
            int i2 = 0;
            int i3 = 0;
            while (i < this.zzbjd.length) {
                byte[] bArr = this.zzbjd[i];
                if (bArr != null) {
                    i3++;
                    i2 += zzfs.zzh(bArr);
                }
                i++;
            }
            zzen = (zzen + i2) + (i3 * 1);
        }
        return (this.zzbjc == null || this.zzbjc.equals(TtmlNode.ANONYMOUS_REGION_ID)) ? zzen : zzen + zzfs.zzb(4, this.zzbjc);
    }

    public final /* synthetic */ zzfu zzeo() {
        return (zzgz) clone();
    }

    public final /* synthetic */ zzfz zzep() {
        return (zzgz) clone();
    }
}
