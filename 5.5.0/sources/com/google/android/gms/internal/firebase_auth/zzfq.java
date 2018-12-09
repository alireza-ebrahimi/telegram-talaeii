package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzdb.zze;
import java.util.Arrays;

public final class zzfq {
    private static final zzfq zzvd = new zzfq(0, new int[0], new Object[0], false);
    private int count;
    private boolean zzmd;
    private int zzqy;
    private Object[] zztl;
    private int[] zzve;

    private zzfq() {
        this(0, new int[8], new Object[8], true);
    }

    private zzfq(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzqy = -1;
        this.count = i;
        this.zzve = iArr;
        this.zztl = objArr;
        this.zzmd = z;
    }

    static zzfq zza(zzfq zzfq, zzfq zzfq2) {
        int i = zzfq.count + zzfq2.count;
        Object copyOf = Arrays.copyOf(zzfq.zzve, i);
        System.arraycopy(zzfq2.zzve, 0, copyOf, zzfq.count, zzfq2.count);
        Object copyOf2 = Arrays.copyOf(zzfq.zztl, i);
        System.arraycopy(zzfq2.zztl, 0, copyOf2, zzfq.count, zzfq2.count);
        return new zzfq(i, copyOf, copyOf2, true);
    }

    private static void zzb(int i, Object obj, zzgj zzgj) {
        int i2 = i >>> 3;
        switch (i & 7) {
            case 0:
                zzgj.zzi(i2, ((Long) obj).longValue());
                return;
            case 1:
                zzgj.zzc(i2, ((Long) obj).longValue());
                return;
            case 2:
                zzgj.zza(i2, (zzbu) obj);
                return;
            case 3:
                if (zzgj.zzdf() == zze.zzrp) {
                    zzgj.zzaj(i2);
                    ((zzfq) obj).zzb(zzgj);
                    zzgj.zzak(i2);
                    return;
                }
                zzgj.zzak(i2);
                ((zzfq) obj).zzb(zzgj);
                zzgj.zzaj(i2);
                return;
            case 5:
                zzgj.zzf(i2, ((Integer) obj).intValue());
                return;
            default:
                throw new RuntimeException(zzdh.zzei());
        }
    }

    public static zzfq zzfz() {
        return zzvd;
    }

    static zzfq zzga() {
        return new zzfq();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzfq)) {
            return false;
        }
        zzfq zzfq = (zzfq) obj;
        if (this.count == zzfq.count) {
            int i;
            boolean z;
            int[] iArr = this.zzve;
            int[] iArr2 = zzfq.zzve;
            int i2 = this.count;
            for (i = 0; i < i2; i++) {
                if (iArr[i] != iArr2[i]) {
                    z = false;
                    break;
                }
            }
            z = true;
            if (z) {
                Object[] objArr = this.zztl;
                Object[] objArr2 = zzfq.zztl;
                i2 = this.count;
                for (i = 0; i < i2; i++) {
                    if (!objArr[i].equals(objArr2[i])) {
                        z = false;
                        break;
                    }
                }
                z = true;
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int i2 = 17;
        int i3 = 0;
        int i4 = (this.count + 527) * 31;
        int[] iArr = this.zzve;
        int i5 = 17;
        for (i = 0; i < this.count; i++) {
            i5 = (i5 * 31) + iArr[i];
        }
        i = (i4 + i5) * 31;
        Object[] objArr = this.zztl;
        while (i3 < this.count) {
            i2 = (i2 * 31) + objArr[i3].hashCode();
            i3++;
        }
        return i + i2;
    }

    final void zza(zzgj zzgj) {
        int i;
        if (zzgj.zzdf() == zze.zzrq) {
            for (i = this.count - 1; i >= 0; i--) {
                zzgj.zza(this.zzve[i] >>> 3, this.zztl[i]);
            }
            return;
        }
        for (i = 0; i < this.count; i++) {
            zzgj.zza(this.zzve[i] >>> 3, this.zztl[i]);
        }
    }

    final void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            zzek.zza(stringBuilder, i, String.valueOf(this.zzve[i2] >>> 3), this.zztl[i2]);
        }
    }

    final void zzb(int i, Object obj) {
        if (this.zzmd) {
            if (this.count == this.zzve.length) {
                int i2 = (this.count < 4 ? 8 : this.count >> 1) + this.count;
                this.zzve = Arrays.copyOf(this.zzve, i2);
                this.zztl = Arrays.copyOf(this.zztl, i2);
            }
            this.zzve[this.count] = i;
            this.zztl[this.count] = obj;
            this.count++;
            return;
        }
        throw new UnsupportedOperationException();
    }

    public final void zzb(zzgj zzgj) {
        if (this.count != 0) {
            int i;
            if (zzgj.zzdf() == zze.zzrp) {
                for (i = 0; i < this.count; i++) {
                    zzb(this.zzve[i], this.zztl[i], zzgj);
                }
                return;
            }
            for (i = this.count - 1; i >= 0; i--) {
                zzb(this.zzve[i], this.zztl[i], zzgj);
            }
        }
    }

    public final void zzbs() {
        this.zzmd = false;
    }

    public final int zzdq() {
        int i = this.zzqy;
        if (i == -1) {
            i = 0;
            for (int i2 = 0; i2 < this.count; i2++) {
                int i3 = this.zzve[i2];
                int i4 = i3 >>> 3;
                switch (i3 & 7) {
                    case 0:
                        i += zzci.zze(i4, ((Long) this.zztl[i2]).longValue());
                        break;
                    case 1:
                        i += zzci.zzg(i4, ((Long) this.zztl[i2]).longValue());
                        break;
                    case 2:
                        i += zzci.zzc(i4, (zzbu) this.zztl[i2]);
                        break;
                    case 3:
                        i += ((zzfq) this.zztl[i2]).zzdq() + (zzci.zzaa(i4) << 1);
                        break;
                    case 5:
                        i += zzci.zzj(i4, ((Integer) this.zztl[i2]).intValue());
                        break;
                    default:
                        throw new IllegalStateException(zzdh.zzei());
                }
            }
            this.zzqy = i;
        }
        return i;
    }

    public final int zzgb() {
        int i = this.zzqy;
        if (i == -1) {
            i = 0;
            for (int i2 = 0; i2 < this.count; i2++) {
                i += zzci.zzd(this.zzve[i2] >>> 3, (zzbu) this.zztl[i2]);
            }
            this.zzqy = i;
        }
        return i;
    }
}
