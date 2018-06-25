package com.google.android.gms.internal;

import java.io.IOException;

class zzfgz extends zzfgy {
    protected final byte[] zzjwl;

    zzfgz(byte[] bArr) {
        this.zzjwl = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfgs)) {
            return false;
        }
        if (size() != ((zzfgs) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzfgz)) {
            return obj.equals(this);
        }
        zzfgz zzfgz = (zzfgz) obj;
        int zzcxt = zzcxt();
        int zzcxt2 = zzfgz.zzcxt();
        return (zzcxt == 0 || zzcxt2 == 0 || zzcxt == zzcxt2) ? zza((zzfgz) obj, 0, size()) : false;
    }

    public int size() {
        return this.zzjwl.length;
    }

    final void zza(zzfgr zzfgr) throws IOException {
        zzfgr.zze(this.zzjwl, zzcxu(), size());
    }

    final boolean zza(zzfgs zzfgs, int i, int i2) {
        if (i2 > zzfgs.size()) {
            throw new IllegalArgumentException("Length too large: " + i2 + size());
        } else if (i + i2 > zzfgs.size()) {
            throw new IllegalArgumentException("Ran off end of other: " + i + ", " + i2 + ", " + zzfgs.size());
        } else if (!(zzfgs instanceof zzfgz)) {
            return zzfgs.zzaa(i, i + i2).equals(zzaa(0, i2));
        } else {
            zzfgz zzfgz = (zzfgz) zzfgs;
            byte[] bArr = this.zzjwl;
            byte[] bArr2 = zzfgz.zzjwl;
            int zzcxu = zzcxu() + i2;
            int zzcxu2 = zzcxu();
            int zzcxu3 = zzfgz.zzcxu() + i;
            while (zzcxu2 < zzcxu) {
                if (bArr[zzcxu2] != bArr2[zzcxu3]) {
                    return false;
                }
                zzcxu2++;
                zzcxu3++;
            }
            return true;
        }
    }

    public final zzfgs zzaa(int i, int i2) {
        int zzh = zzfgs.zzh(i, i2, size());
        return zzh == 0 ? zzfgs.zzpnw : new zzfgv(this.zzjwl, zzcxu() + i, zzh);
    }

    protected void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzjwl, i, bArr, i2, i3);
    }

    public final zzfhb zzcxq() {
        return zzfhb.zzb(this.zzjwl, zzcxu(), size(), true);
    }

    protected int zzcxu() {
        return 0;
    }

    protected final int zzg(int i, int i2, int i3) {
        return zzfhz.zza(i, this.zzjwl, zzcxu() + i2, i3);
    }

    public byte zzld(int i) {
        return this.zzjwl[i];
    }
}
