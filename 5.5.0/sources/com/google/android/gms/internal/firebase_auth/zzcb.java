package com.google.android.gms.internal.firebase_auth;

import java.nio.charset.Charset;

class zzcb extends zzca {
    protected final byte[] zzmp;

    zzcb(byte[] bArr) {
        this.zzmp = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbu)) {
            return false;
        }
        if (size() != ((zzbu) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzcb)) {
            return obj.equals(this);
        }
        zzcb zzcb = (zzcb) obj;
        int zzby = zzby();
        int zzby2 = zzcb.zzby();
        return (zzby == 0 || zzby2 == 0 || zzby == zzby2) ? zza((zzcb) obj, 0, size()) : false;
    }

    public int size() {
        return this.zzmp.length;
    }

    protected final int zza(int i, int i2, int i3) {
        return zzdd.zza(i, this.zzmp, zzbz(), i3);
    }

    public final zzbu zza(int i, int i2) {
        int zzb = zzbu.zzb(0, i2, size());
        return zzb == 0 ? zzbu.zzmi : new zzbx(this.zzmp, zzbz(), zzb);
    }

    protected final String zza(Charset charset) {
        return new String(this.zzmp, zzbz(), size(), charset);
    }

    final void zza(zzbt zzbt) {
        zzbt.zza(this.zzmp, zzbz(), size());
    }

    final boolean zza(zzbu zzbu, int i, int i2) {
        if (i2 > zzbu.size()) {
            throw new IllegalArgumentException("Length too large: " + i2 + size());
        } else if (i2 > zzbu.size()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + i2 + ", " + zzbu.size());
        } else if (!(zzbu instanceof zzcb)) {
            return zzbu.zza(0, i2).equals(zza(0, i2));
        } else {
            zzcb zzcb = (zzcb) zzbu;
            byte[] bArr = this.zzmp;
            byte[] bArr2 = zzcb.zzmp;
            int zzbz = zzbz() + i2;
            int zzbz2 = zzbz();
            int zzbz3 = zzcb.zzbz();
            while (zzbz2 < zzbz) {
                if (bArr[zzbz2] != bArr2[zzbz3]) {
                    return false;
                }
                zzbz2++;
                zzbz3++;
            }
            return true;
        }
    }

    public final boolean zzbx() {
        int zzbz = zzbz();
        return zzfx.zzf(this.zzmp, zzbz, size() + zzbz);
    }

    protected int zzbz() {
        return 0;
    }

    public byte zzk(int i) {
        return this.zzmp[i];
    }
}
